package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.OrderService;
import cn.edu.nju.tickets.vo.OrderRegisterVO;
import cn.edu.nju.tickets.vo.OrderVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/cancelOrder")
    public Boolean cancelOrder(@RequestParam Integer orderID,
                                @CookieValue(name = "username", required = false) String username){
        return orderService.cancelOrder(orderID, username);
    }

    @PostMapping("/makeOrder")
    public Boolean makeOrder(@RequestBody OrderRegisterVO vo,
                             @CookieValue(value = "username", required = false) String username) {
        return orderService.makeOrder(vo, username);
    }

    @PostMapping("/payOrder")
    public Boolean pay(@RequestParam Integer orderID,
                       @CookieValue(name = "username", required = false) String username) {
        return orderService.pay(orderID, username);
    }


    @GetMapping("/getAllOrder")
    public ResponseResult<List<OrderVO>> getAllOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getAllOrder(username);
    }

    @GetMapping("/getCancelOrder")
    public ResponseResult<List<OrderVO>> getCancelOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getCancelOrder(username);
    }

    @GetMapping("/getFinishedOrder")
    public ResponseResult<List<OrderVO>> getFinishedOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getFinishedOrder(username);
    }

    @GetMapping("/getRefundOrder")
    public ResponseResult<List<OrderVO>> getRefundOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getRefundOrder(username);
    }

    @GetMapping("/getNotPaidOrder")
    public ResponseResult<List<OrderVO>> getNotPaidOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getNotPaidOrder(username);
    }

    @GetMapping("/getNotAllocateOrder")
    public ResponseResult<List<OrderVO>> getNotAllocateOrder(@CookieValue(name = "username", required = false) String username){
        return orderService.getNotAllocateOrder(username);
    }

    @PostMapping("/checkSeat")
    public Boolean checkSeat(@RequestParam Integer orderID, @RequestParam int seatNum) {
        return orderService.checkSeat(orderID, seatNum);
    }
}
