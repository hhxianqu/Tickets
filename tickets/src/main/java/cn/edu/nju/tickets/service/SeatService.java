package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.ResponseResult;

import java.util.List;

public interface SeatService {

    ResponseResult<List<String>> getVipSelectedSeats(Integer performID);

    ResponseResult<List<String>> getNormalSelectedSeats(Integer performID);

    ResponseResult<List<String>> getVipSeats(Integer performID);

    ResponseResult<List<String>> getNormalSeats(Integer performID);
}
