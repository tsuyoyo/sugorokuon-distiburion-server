/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.service;

import org.springframework.stereotype.Service;
import tsuyogoro.sugorokuon.server.model.Station;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationManagerService {

    private List<Station> stationList;

    @PostConstruct
    public void init() {
        stationList = new ArrayList<>();

        Station r1 = new Station();
        r1.id = "r1";
        r1.ascii_name = "NHK_R1";
        r1.name = "NHKラジオ第1";
        r1.logoUrl = "http://www.nhk.or.jp/common/img/media/r1-200x200.png";
        r1.bannerUrl = "";
        r1.siteUrl = "http://www.nhk.or.jp/r1/";

        Station r2 = new Station();
        r2.id = "r2";
        r2.ascii_name = "NHK_R2";
        r2.name = "NHKラジオ第2";
        r2.logoUrl = "http://www.nhk.or.jp/common/img/media/r2-200x200.png";
        r2.bannerUrl = "";
        r2.siteUrl = "http://www.nhk.or.jp/r2/";

        Station rFm = new Station();
        rFm.id = "r3";
        rFm.ascii_name = "NHK_FM";
        rFm.name = "NHK FM";
        rFm.logoUrl = "http://www.nhk.or.jp/common/img/media/fm-200x200.png";
        rFm.bannerUrl = "";
        rFm.siteUrl = "http://www.nhk.or.jp/fm/";

        stationList.add(r1);
        stationList.add(r2);
        stationList.add(rFm);
    }

    public List<Station> getStationList() {
        return stationList;
    }

}
