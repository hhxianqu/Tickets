package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.*;

import java.util.List;

public interface ManagerService {

    ResponseResult<List<VenueDifferentInfo>> getAuditedChangedVenue();

    ResponseResult<VenueVO> passChangedVenue(Boolean pass, Integer id);

    ResponseResult<List<VenueVO>> getAuditedVenue(Boolean audited);

    ResponseResult<VenueVO> passVenue(Boolean pass, Integer id);

    ResponseResult<List<PerformVO>> getAuditedPerform(Boolean audited);

    ResponseResult<PerformVO> passPerform(Boolean pass, Integer id);

    ResponseResult<List<VenueVO>> getAllVenues();

    ResponseResult<AccountVO> pay(Integer performID, int divide, int pay);

    ResponseResult<List<Integer>> getMemberLevel();

    ResponseResult<Integer> getManagerBalance();
}
