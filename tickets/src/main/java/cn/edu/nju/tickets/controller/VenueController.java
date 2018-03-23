package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.VenueService;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VenueController {
    @Autowired
    private VenueService venueService;

    @GetMapping("/getPerformanceByVenueNum")
    public ResponseResult<List<PerformVO>> getPerform(@RequestParam String venueNum) {
        return venueService.getPerformanceByVenueNum(venueNum);
    }

    @PostMapping("/apply")
    public ResponseResult<PerformVO> applyForPerform (@RequestBody PerformRegisterVO perform) {
        return venueService.apply(perform);
    }

    @GetMapping("/getVenueInfo")
    public ResponseResult<VenueVO> getVenueInfo(@RequestParam String venueNum) {
        return venueService.getVenueInfo(venueNum);
    }

    @PostMapping("/changeVenueInfo")
    public ResponseResult<Boolean> changeVenueInfo(@RequestBody VenueChangeVO venue) {
        return venueService.changeVenueInfo(venue);
    }

    @PostMapping("/getNotStartPerform")
    public ResponseResult<List<PerformVO>> getNotStartPerform(
                                @CookieValue(name = "username", required = false) String username) {
        return venueService.getNotStartPerform(username);
    }
}
