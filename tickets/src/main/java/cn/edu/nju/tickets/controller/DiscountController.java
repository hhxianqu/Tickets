package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.DiscountService;
import cn.edu.nju.tickets.vo.DiscountMarketVO;
import cn.edu.nju.tickets.vo.DiscountUserVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/getSelfDiscount")
    public ResponseResult<List<DiscountUserVO>> getSelfDiscount(
                    @CookieValue(name = "username", required = false) String username) {
        return discountService.getSelfDiscount(username);
    }

    @PostMapping("/getAllDiscount")
    public ResponseResult<List<DiscountMarketVO>> getAllDiscount() {
        return discountService.getAllDiscount();
    }

    @PostMapping("/convert")
    public ResponseResult<DiscountUserVO> convertDiscount(@CookieValue(name = "username", required = false) String username,
                                                          @RequestParam Integer discountID) {
        return discountService.convertDiscount(username, discountID);
    }
}
