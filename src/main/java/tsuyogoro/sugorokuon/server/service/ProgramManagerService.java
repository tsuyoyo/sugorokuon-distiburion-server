/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.service;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tsuyogoro.sugorokuon.server.constant.NhkApiConstants;
import tsuyogoro.sugorokuon.server.model.Program;
import tsuyogoro.sugorokuon.server.utils.DateUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j
public class ProgramManagerService {

    static private Logger logger = Logger.getLogger(ProgramManagerService.class);

    private static class OneDayAreaTable {
        final NhkApiConstants.Area area;
        List<Program> programs = new ArrayList<>();

        OneDayAreaTable(NhkApiConstants.Area area) {
            this.area = area;
        }
    }

    private static class OneDayServiceTable {
        final NhkApiConstants.Service service;
        List<OneDayAreaTable> serviceTimeTable = new ArrayList<>();

        OneDayServiceTable(NhkApiConstants.Service service) {
            this.service = service;
        }
    }

    @Autowired
    private ProgramDataManager programDataManager;

    private List<OneDayServiceTable> todayTimetable = new ArrayList<>();

    private List<OneDayServiceTable> tomorrowTimetable = new ArrayList<>();

    @PostConstruct
    public void init() {
        update();
    }

    @Scheduled(cron = "0 10 5 * * *", zone = "Asia/Tokyo")
    public void update() {

        // 動いた証拠にログとして出す
        SimpleDateFormat format = DateUtils.getSimpleDateFormatInJst("yyyy/MM/dd - HH:mm:ss");
        logger.info("Update timetable : " + format.format(Calendar.getInstance().getTime()));

        // 前日のキャッシュは要らないので消す
        logger.info("  yesterday : " + format.format(getDateForYesterday()));
        programDataManager.clearCache(getDateForYesterday());

        // 明日分の番組を今日の番組にずらす
        if (!tomorrowTimetable.isEmpty()) {
            todayTimetable.clear();
            todayTimetable = new ArrayList<>(tomorrowTimetable);

            tomorrowTimetable.clear();
        } else {
            // 明日分の番組がなければ今日の分をloadする
            Date today = getDateForToday();
            logger.info("  today : " + format.format(today));

            Arrays.stream(NhkApiConstants.Service.values()).forEach(service -> {
                OneDayServiceTable oneDayServiceTable = new OneDayServiceTable(service);

                Arrays.stream(NhkApiConstants.Area.values()).forEach(area -> {
                    OneDayAreaTable oneDayAreaTable = new OneDayAreaTable(area);
                    oneDayAreaTable.programs = programDataManager.load(today, area, service);
                    oneDayServiceTable.serviceTimeTable.add(oneDayAreaTable);
                });

                todayTimetable.add(oneDayServiceTable);
            });
        }

        // 明日の番組をloadし直す
        Date tomorrow = getDateForTomorrow();
        logger.info("  tomorrow : " + format.format(tomorrow));

        Arrays.stream(NhkApiConstants.Service.values()).forEach(service -> {
            OneDayServiceTable oneDayServiceTable = new OneDayServiceTable(service);

            Arrays.stream(NhkApiConstants.Area.values()).forEach(area -> {
                OneDayAreaTable oneDayAreaTable = new OneDayAreaTable(area);
                oneDayAreaTable.programs = programDataManager.load(tomorrow, area, service);
                oneDayServiceTable.serviceTimeTable.add(oneDayAreaTable);
            });

            tomorrowTimetable.add(oneDayServiceTable);
        });
    }

    public enum ProgramDate {
        TODAY,
        TOMORROW;
    }

    public List<Program> getTimeTable(ProgramDate whichDate,
                                      NhkApiConstants.Area area, NhkApiConstants.Service service) {
        switch (whichDate) {
            case TODAY:
                return searchTimeTable(ProgramDate.TODAY, area, service);
            case TOMORROW:
                return searchTimeTable(ProgramDate.TOMORROW, area, service);
            default:
                // 対応外のリクエストが来たら空のリストを返す
                return new ArrayList<>();
        }
    }

    private List<Program> searchTimeTable(ProgramDate date, NhkApiConstants.Area area, NhkApiConstants.Service service) {

        List<OneDayServiceTable> targetTimetables;
        if (date.equals(ProgramDate.TODAY)) {
            targetTimetables = todayTimetable;
        } else {
            targetTimetables = tomorrowTimetable;
        }

        final List<Program> res = new ArrayList<>();

        targetTimetables.stream()
                .filter(timeTable -> timeTable.service.code.equals(service.code))
                .findFirst()
                .ifPresent(serviceTimeTable -> {
                    serviceTimeTable.serviceTimeTable.stream()
                            .filter(oneDayAreaTable -> oneDayAreaTable.area.code.equals(area.code))
                            .findFirst()
                            .ifPresent(t -> {
                                res.addAll(t.programs);
                            });
                });
        return res;
    }

    private Date getDateForToday() {
        Calendar c = DateUtils.getCalendarInJst();

        if (c.get(Calendar.HOUR_OF_DAY) < 5) {
            c.add(Calendar.DATE, -1);
        }

        return c.getTime();
    }

    private Date getDateForYesterday() {
        Calendar c = DateUtils.getCalendarInJst();

        c.setTime(getDateForToday());
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    private Date getDateForTomorrow() {
        Calendar c = DateUtils.getCalendarInJst();

        c.setTime(getDateForToday());
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

}
