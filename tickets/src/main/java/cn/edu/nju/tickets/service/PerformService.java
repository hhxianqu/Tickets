package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.PerformVO;
import cn.edu.nju.tickets.vo.ResponseResult;

import java.util.List;

public interface PerformService {

    ResponseResult<List<PerformVO>> getPerformance(String type, String city);

    ResponseResult<List<String>> getCity(String type);

    ResponseResult<List<PerformVO>> getUnsettledPerform(String venueNum);

    ResponseResult<List<PerformVO>> getSettledPerform(String venueNum);

    ResponseResult<List<PerformVO>> getAllUnsettledPerform();

    ResponseResult<List<PerformVO>> getAllSettledPerform();

    ResponseResult<PerformVO> getPerformById(Integer id);


}
