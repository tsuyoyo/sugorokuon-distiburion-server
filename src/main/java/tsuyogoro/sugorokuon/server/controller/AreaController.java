package tsuyogoro.sugorokuon.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tsuyogoro.sugorokuon.server.constant.NhkApiConstants;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("area/nhk")
public class AreaController {

    public static class AreaModel {
        public AreaModel(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }
        public String code;
        public String displayName;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<AreaModel> getAreas() {
        List<AreaModel> areas = new ArrayList<>();
        for (NhkApiConstants.Area a : NhkApiConstants.Area.values()) {
            areas.add(new AreaModel(a.code, a.displayName));
        }
        return areas;
    }
}
