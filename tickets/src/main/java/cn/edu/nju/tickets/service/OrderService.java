package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.OrderRegisterVO;
import cn.edu.nju.tickets.vo.OrderVO;
import cn.edu.nju.tickets.vo.ResponseResult;

import java.util.List;

public interface OrderService {

    void autoCancel();

    Boolean cancelOrder(Integer orderID, String email);

    Boolean makeOrder(OrderRegisterVO vo, String username);

    Boolean pay(Integer orderID, String email);

    ResponseResult<List<OrderVO>> getNotPaidOrder (String email);

    ResponseResult<List<OrderVO>> getCancelOrder (String email);

    ResponseResult<List<OrderVO>> getFinishedOrder (String email);

    ResponseResult<List<OrderVO>> getRefundOrder (String email);

    ResponseResult<List<OrderVO>> getNotAllocateOrder (String email);

    ResponseResult<List<OrderVO>> getAllOrder (String email);

    Boolean checkSeat(Integer orderID, int seatNum);
}
