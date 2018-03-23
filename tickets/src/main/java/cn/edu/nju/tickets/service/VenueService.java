package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.*;

import java.util.List;

public interface VenueService {

    ResponseResult<List<PerformVO>> getPerformanceByVenueNum(String venueNum);

    ResponseResult<PerformVO> apply(PerformRegisterVO perform);

    ResponseResult<VenueVO> getVenueInfo(String venueNum);

    ResponseResult<Boolean> changeVenueInfo(VenueChangeVO venue);

    ResponseResult<List<PerformVO>> getNotStartPerform(String venueNum);
}
