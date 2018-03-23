package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.SeatService;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/getVipSelectedSeats")
    public ResponseResult<List<String>> getVipSelectedSeats(@RequestParam Integer performID){
        return seatService.getVipSelectedSeats(performID);
    }

    @PostMapping("/getNormalSelectedSeats")
    public ResponseResult<List<String>> getNormalSelectedSeats(@RequestParam Integer performID){
        return seatService.getNormalSelectedSeats(performID);
    }

    @PostMapping("/getVipSeats")
    public ResponseResult<List<String>> getVipSeats(Integer performID) {
        return seatService.getVipSeats(performID);
    }

    @PostMapping("/getNormalSeats")
    public ResponseResult<List<String>> getNormalSeats(Integer performID) {
        return seatService.getNormalSeats(performID);
    }

}
