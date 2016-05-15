/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tsuyogoro.sugorokuon.server.model.Station;
import tsuyogoro.sugorokuon.server.service.StationManagerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("station/nhk")
public class StationListController {

    @Autowired
    StationManagerService stationService;

    @RequestMapping(method = RequestMethod.GET)
    List<Station> getStations() {
        return stationService.getStationList();
    }

}
