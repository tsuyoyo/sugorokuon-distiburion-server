/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tsuyogoro.sugorokuon.server.constant.NhkApiConstants;
import tsuyogoro.sugorokuon.server.model.Program;
import tsuyogoro.sugorokuon.server.service.ProgramManagerService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("program/nhk")
public class ProgramController {

    @Autowired
    ProgramManagerService programService;

    @RequestMapping(value = "/today/{areaCode}/{serviceCode}", method = RequestMethod.GET)
    List<Program> getTodayPrograms(@PathVariable String areaCode, @PathVariable String serviceCode) {

        // TODO : アクセスログみたいなものを残せないかしら

        return programService.getTimeTable(ProgramManagerService.ProgramDate.TODAY,
                getArea(areaCode), getService(serviceCode));
    }

    @RequestMapping(value = "/tomorrow/{areaCode}/{serviceCode}", method = RequestMethod.GET)
    List<Program> getTomorrowPrograms(@PathVariable String areaCode, @PathVariable String serviceCode) {

        // TODO : アクセスログみたいなものを残せないかしら

        return programService.getTimeTable(ProgramManagerService.ProgramDate.TOMORROW,
                getArea(areaCode), getService(serviceCode));
    }

    private NhkApiConstants.Area getArea(String areaCode) {
        Optional<NhkApiConstants.Area> area = Arrays.stream(NhkApiConstants.Area.values())
                .filter(a -> a.code.equals(areaCode)).findFirst();
        if (!area.isPresent()) {
            throw new IllegalArgumentException("Invalid area code");
        }
        return area.get();
    }

    private NhkApiConstants.Service getService(String serviceCode) {
        Optional<NhkApiConstants.Service> service = Arrays.stream(NhkApiConstants.Service.values())
                .filter(s -> s.code.equals(serviceCode)).findFirst();
        if (!service.isPresent()) {
            throw new IllegalArgumentException("Invalid service code");
        }
        return service.get();
    }

    @RequestMapping(method = RequestMethod.GET)
    Date getLastUpdateDate() {
        return new Date();
    }

}
