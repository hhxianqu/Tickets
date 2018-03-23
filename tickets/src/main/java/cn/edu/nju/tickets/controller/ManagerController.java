package cn.edu.nju.tickets.controller;


import cn.edu.nju.tickets.service.ManagerService;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/auditedChangedVenue")
    public ResponseResult<List<VenueDifferentInfo>> getAuditedChangedVenue() {
        return managerService.getAuditedChangedVenue();
    }

    @PostMapping("/passChangedVenue")
    public ResponseResult<VenueVO> passChangedVenue(@RequestParam Boolean pass,
                                                    @RequestParam Integer id) {
        return managerService.passChangedVenue(pass, id);
    }

    @GetMapping("/auditedVenue")
    public ResponseResult<List<VenueVO>> getAuditedVenue(@RequestParam Boolean audited) {
        return managerService.getAuditedVenue(audited);
    }

    @PostMapping("/passVenue")
    public ResponseResult<VenueVO> pass(@RequestParam Boolean pass,
                                        @RequestParam Integer id) {
        return managerService.passVenue(pass, id);
    }

    @GetMapping("/auditedPerform")
    public ResponseResult<List<PerformVO>> getAuditedPerform(@RequestParam Boolean audited) {
        return managerService.getAuditedPerform(audited);
    }

    @PostMapping("/passPerform")
    public ResponseResult<PerformVO> passPerform(@RequestParam Boolean pass,
                                                 @RequestParam Integer id) {
        return managerService.passPerform(pass, id);
    }

    @GetMapping("/allVenues")
    public ResponseResult<List<VenueVO>> getAllVenues () {
        return managerService.getAllVenues();
    }

    @PostMapping("/pay")
    public ResponseResult<AccountVO> pay(Integer performID, int divide, int pay) {
        return managerService.pay(performID, divide, pay);
    }

    @GetMapping("/getMemberLevel")
    public ResponseResult<List<Integer>> getMemberLevel() {
        return managerService.getMemberLevel();
    }

    @GetMapping("/getManagerBalance")
    public ResponseResult<Integer> getManagerBalance(){
        return managerService.getManagerBalance();
    }
}
