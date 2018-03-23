package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.PerformService;
import cn.edu.nju.tickets.vo.PerformVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PerformController {

    @Autowired
    private PerformService performService;

    @GetMapping("/perform")
    public ResponseResult<List<PerformVO>> getPerformances (@RequestParam String type,
                                                            @RequestParam String city){
        return performService.getPerformance(type, city);
    }

    @GetMapping("/getCity")
    public ResponseResult<List<String>> getCity(@RequestParam String type) {
        return performService.getCity(type);
    }

    @GetMapping("/unsettledPerform")
    public ResponseResult<List<PerformVO>> getUnsettledPerform(@RequestParam String venueNum) {
        return performService.getUnsettledPerform(venueNum);
    }

    @GetMapping("/settledPerform")
    public ResponseResult<List<PerformVO>> getSettledPerform(@RequestParam String venueNum) {
        return performService.getSettledPerform(venueNum);
    }

    @GetMapping("/unsettledPerformAll")
    public ResponseResult<List<PerformVO>> getAllUnsettledPerform(){
        return performService.getAllUnsettledPerform();
    }

    @GetMapping("/settledPerformAll")
    public ResponseResult<List<PerformVO>> getAllSettledPerform(){
        return performService.getAllSettledPerform();
    }

    @GetMapping("/getPerformById")
    public ResponseResult<PerformVO> getPerformById(@RequestParam Integer id) {
        return performService.getPerformById(id);
    }


}
