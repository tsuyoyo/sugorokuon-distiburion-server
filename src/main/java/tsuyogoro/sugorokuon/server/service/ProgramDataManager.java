/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tsuyogoro.sugorokuon.server.constant.NhkApiConstants;
import tsuyogoro.sugorokuon.server.model.Program;
import tsuyogoro.sugorokuon.server.utils.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class ProgramDataManager {

    static private Logger logger = Logger.getLogger(ProgramDataManager.class);

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseProgram {
        public String id;
        public String event_id;
        public Date start_time;
        public Date end_time;
        public String title;
        public String subtitle;
    }

    private static final String API = "http://api.nhk.or.jp/v1/pg/list/";

    private static final String PROGRAM_CACHE_DIR = "nhk_testdata";

    @Value("${apikey.nhk}")
    String apiKey; // "-DnhkApikey=aaaaaaaa" みたいに起動パラメータを渡すようにするとここに値が入る

    @Autowired
    ObjectMapper objectMapper;

    public List<Program> load(Date date, NhkApiConstants.Area area, NhkApiConstants.Service service) {

        String year = DateUtils.getSimpleDateFormatInJst("yyyy").format(date);
        String month = DateUtils.getSimpleDateFormatInJst("MM").format(date);
        String day = DateUtils.getSimpleDateFormatInJst("dd").format(date);

        File cachedFile = new File(getProgramCachePath(year, month, day, area, service));

        try {
            if (!cachedFile.exists()) {
                fetchProgramJson(year, month, day, area, service);
            } else {
                logger.info("Cache is found : " +  year + month + day
                        + " - " + area.displayName + " " + service.displayName);
            }
            return loadFromCacheFile(cachedFile, service);

        } catch (Exception e) {
            logger.info("Exception was thrown at load : " + year + month + day
                    + " area = " + area.displayName + " service = " + service.displayName
                    + " errorMessage : " + e.getMessage());

            return new ArrayList<>();
        }
    }

    public void clearCache(Date date) {
        String year = DateUtils.getSimpleDateFormatInJst("yyyy").format(date);
        String month = DateUtils.getSimpleDateFormatInJst("MM").format(date);
        String day = DateUtils.getSimpleDateFormatInJst("dd").format(date);

        File cacheDir = new File(getProgramCacheDirPath(year, month, day));

        if (cacheDir.exists() && cacheDir.isDirectory()) {
            if (cacheDir.listFiles() != null) {
                Arrays.stream(cacheDir.listFiles()).forEach(File::delete);
            }
            cacheDir.delete();
        }
    }

    private void fetchProgramJson(String year, String month, String day,
                                  NhkApiConstants.Area area, NhkApiConstants.Service service)
            throws RestClientException, IOException {

        String date = year + "-" + month + "-" + day;
        String url = API + area.code + "/" + service.code + "/" + date + ".json?key=" + apiKey;

        logger.info("Fetch program data from : " + url);

        String responseInJson = new RestTemplate().getForObject(url, String.class);
        JSONArray programsInJson = new JSONObject(responseInJson).getJSONObject("list").getJSONArray(service.code);

        String cacheFilePath = getProgramCachePath(year, month, day, area, service);

        File cacheFile = new File(cacheFilePath);

        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(programsInJson.toString());
        fileWriter.close();
    }

    private String getProgramCachePath(String year, String month, String day,
                                       NhkApiConstants.Area area, NhkApiConstants.Service service) {

        File cacheDir = new File(getProgramCacheDirPath(year, month, day));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        return cacheDir + "/" + area.code + "_" + service.code + "_program.json";
    }

    private String getProgramCacheDirPath(String year, String month, String day) {
        return "./" + PROGRAM_CACHE_DIR + "/" + year + month + day + "/";
    }

    private List<Program> loadFromCacheFile(File cacheFile, NhkApiConstants.Service service) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(cacheFile);

        TypeReference<List<ResponseProgram>> type = new TypeReference<List<ResponseProgram>>() {
        };
        List<ResponseProgram> responsePrograms = objectMapper.readValue(fileInputStream, type);

        List<Program> loadedPrograms = new ArrayList<>();

        responsePrograms.stream().forEach(p -> {
            Program program = new Program();
            program.stationId = service.code;
            program.startTime = p.start_time;
            program.endTime = p.end_time;
            program.title = p.title;
            program.description = p.subtitle;

            loadedPrograms.add(program);
        });

        return loadedPrograms;
    }
}
