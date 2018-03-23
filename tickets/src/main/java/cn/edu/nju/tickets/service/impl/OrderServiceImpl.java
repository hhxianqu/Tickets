package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.*;
import cn.edu.nju.tickets.repository.*;
import cn.edu.nju.tickets.service.OrderService;
import cn.edu.nju.tickets.util.DateUtil;
import cn.edu.nju.tickets.util.OrderState;
import cn.edu.nju.tickets.vo.OrderRegisterVO;
import cn.edu.nju.tickets.vo.OrderVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PerformRepository performRepository;

    @Autowired
    private DiscountUserRepository discountUserRepository;

    @Autowired
    private DiscountMarketRepository discountMarketRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private OrderVO convertOrderToVO(Order order, Perform perform) {
        OrderVO vo = new OrderVO();

        vo.setCreateTime(DateUtil.convertDateToString(order.getCreateTime()));
        vo.setId(order.getId());
        vo.setNormalNum(order.getNormalNum());
        vo.setPerformName(perform.getPerformName());
        vo.setPerformStartTime(DateUtil.convertDateToString(perform.getStartTime()));
        vo.setPrice(order.getPrice());
        vo.setState(order.getState());
        vo.setVipNum(order.getVipNum());
        vo.setPerformVenue(venueRepository.findByVenueNumber(perform.getVenueNum()).getVenueName());

        List<Integer> seatNum = seatRepository.findByEmailAndPerformID(order.getEmail(), perform.getId())
                            .stream().map(Seat::getSeatNum).collect(Collectors.toList());

        List<String> seatStr = new ArrayList<>();
        for (Integer i : seatNum) {
            int row = i / 20 + 1;
            int vertical = i % 20;
            seatStr.add(row + "排" + vertical + "座");
        }

        vo.setSeat(seatStr);
        return vo;
    }

    @Override
    public Boolean makeOrder(OrderRegisterVO vo, String username) {
        Ticket ticket = ticketRepository.getOne(performRepository.getOne(vo.getPerformID()).getTicketID());
        ticket.setVipSale(ticket.getVipSale() + vo.getVipNum());
        ticket.setNormalSale(ticket.getNormalSale() + vo.getNormalNum());

        ticketRepository.save(ticket);

        List<Integer> seatList = vo.getSeat();
        for (Integer num : seatList) {
            Seat seat = new Seat();
            seat.setEmail(username);
            seat.setPerformID(vo.getPerformID());
            seat.setSeatNum(num);
            seat.setChecked(false);
            seatRepository.save(seat);
        }

        int discountMoney = 0;

        if (vo.getDiscountID() != 0) {
            DiscountUser discount = discountUserRepository.getOne(vo.getDiscountID());
            DiscountMarket discountMarket = discountMarketRepository.getOne(discount.getDiscountID());
            discountMoney = discountMarket.getDiscountNum();
            discount.setOwnNum(discount.getOwnNum() - 1);
        }

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setEmail(username);
        order.setNormalNum(vo.getNormalNum());
        order.setVipNum(vo.getVipNum());
        order.setPerformID(vo.getPerformID());
        order.setPrice(vo.getTotalPrice() - discountMoney);
        order.setState(OrderState.NOT_PAID);
        orderRepository.save(order);

        return true;
    }

    @Override
    public Boolean pay(Integer orderID, String email) {
        Order order = orderRepository.getOne(orderID);
        order.setState(OrderState.FINISHED);
        orderRepository.save(order);

        Account userAccount = accountRepository.findByUsername(email);
        userAccount.setBalance(userAccount.getBalance() - order.getPrice());
        accountRepository.save(userAccount);

        User user = userRepository.findByEmail(email);
        user.setScore(user.getScore() + order.getPrice());
        user.setLevel(user.getLevel() / 2000);
        userRepository.save(user);

        return true;
    }

    @Override
    public ResponseResult<List<OrderVO>> getNotPaidOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findByStateAndEmail(OrderState.NOT_PAID, email)
                                .stream()
                                .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                                .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<OrderVO>> getCancelOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findByStateAndEmail(OrderState.CANCEL, email)
                        .stream()
                        .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<OrderVO>> getFinishedOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findByStateAndEmail(OrderState.FINISHED, email)
                        .stream()
                        .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<OrderVO>> getRefundOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findByStateAndEmail(OrderState.REFUND, email)
                        .stream()
                        .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<OrderVO>> getNotAllocateOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findByStateAndEmail(OrderState.NOT_ALLOCATED, email)
                        .stream()
                        .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<OrderVO>> getAllOrder(String email) {
        return new ResponseResult<>(true, "",
                orderRepository.findAllByEmail(email)
                        .stream()
                        .map(order -> convertOrderToVO(order, performRepository.getOne(order.getPerformID())))
                        .collect(Collectors.toList()));
    }

    @Override
    public Boolean checkSeat(Integer orderID, int seatNum) {
        Order order = orderRepository.getOne(orderID);
        Seat seat = seatRepository.findByEmailAndPerformIDAndSeatNum(order.getEmail(), order.getPerformID(), seatNum);
        if (!seat.getChecked() && order.getState().equals(OrderState.FINISHED)) {
            seat.setChecked(true);
            seatRepository.save(seat);
            return true;
        }
        return false;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    @Override
    public void autoCancel() {
        System.out.println(new Date());
        List<Order> orders = orderRepository.findByState(OrderState.NOT_PAID);
        orders.stream()
                .filter(order -> (new Date().getTime() - order.getCreateTime().getTime()) > 5 * 60 * 1000)
                .forEach(order -> {
                    order.setState(OrderState.CANCEL);
                    orderRepository.save(order);
                    System.out.println(order.getId() + "已被取消");
                });
    }

    @Override
    public Boolean cancelOrder(Integer orderID, String email) {
        Order order = orderRepository.getOne(orderID);
        order.setState(OrderState.REFUND);
        orderRepository.save(order);

        User user = userRepository.findByEmail(email);
        user.setScore(user.getScore() - order.getPrice());
        user.setLevel(user.getLevel() / 2000);
        userRepository.save(user);

        return true;
    }


}
