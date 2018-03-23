package cn.edu.nju.tickets.service;


import cn.edu.nju.tickets.vo.DiscountMarketVO;
import cn.edu.nju.tickets.vo.DiscountUserVO;
import cn.edu.nju.tickets.vo.ResponseResult;

import java.util.List;

public interface DiscountService {

    ResponseResult<List<DiscountMarketVO>> getAllDiscount();

    ResponseResult<List<DiscountUserVO>> getSelfDiscount(String username);

    ResponseResult<DiscountUserVO> convertDiscount(String email, Integer discountID);
}
