package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.Seat;
import cn.edu.nju.tickets.entity.Ticket;
import cn.edu.nju.tickets.repository.PerformRepository;
import cn.edu.nju.tickets.repository.SeatRepository;
import cn.edu.nju.tickets.repository.TicketRepository;
import cn.edu.nju.tickets.service.SeatService;
import cn.edu.nju.tickets.vo.ResponseResult;
import cn.edu.nju.tickets.vo.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PerformRepository performRepository;

    private SeatVO convertSeatToVO (Seat seat) {
        SeatVO vo = new SeatVO();
        vo.setId(seat.getId());
        vo.setPerformID(seat.getPerformID());
        vo.setSeatNum(seat.getSeatNum());
        vo.setChecked(seat.getChecked());

        return vo;
    }

    @Override
    public ResponseResult<List<String>> getVipSelectedSeats(Integer performID) {
        List<SeatVO> seatVOS = seatRepository.findAllByPerformID(performID)
                .stream().map(this::convertSeatToVO).collect(Collectors.toList());
        List<String> vipSelectedSeats = new ArrayList<>();
        Ticket ticket = ticketRepository.getOne(performRepository.getOne(performID).getTicketID());
        int number = ticket.getVipNum();

        for (SeatVO seatVO : seatVOS) {
            if (seatVO.getSeatNum() < number) {
                int seatNum = seatVO.getSeatNum();
                int row = seatNum/20 + 1;
                int vertical = seatNum%20;

                vipSelectedSeats.add(row + "_" + vertical);
            }
        }
        return new ResponseResult<>(true, "", vipSelectedSeats);
    }

    @Override
    public ResponseResult<List<String>> getNormalSelectedSeats(Integer performID) {
        List<SeatVO> seatVOS = seatRepository.findAllByPerformID(performID)
                .stream().map(this::convertSeatToVO).collect(Collectors.toList());
        Ticket ticket = ticketRepository.getOne(performRepository.getOne(performID).getTicketID());
        int number = ticket.getVipNum();
        List<String> normalSelectedSeats = new ArrayList<>();
        for (SeatVO seatVO : seatVOS) {
            if (seatVO.getSeatNum() > number) {
                int seatNum = seatVO.getSeatNum() - number;
                int row = seatNum/20 + 1;
                int vertical = seatNum%20;

                normalSelectedSeats.add(row + "_" + vertical);
            }
        }
        return new ResponseResult<>(true, "", normalSelectedSeats);
    }

    @Override
    public ResponseResult<List<String>> getVipSeats(Integer performID) {
        Ticket ticket = ticketRepository.getOne(performRepository.getOne(performID).getTicketID());
        int number = ticket.getVipNum();
        int rowNum = number/20;
        List<String> vipSeat = new ArrayList<>();
        for (int i = 0; i < rowNum; i++) {
            vipSeat.add("ffffffffffffffffffff");
        }
        return new ResponseResult<>(true, "", vipSeat);
    }

    @Override
    public ResponseResult<List<String>> getNormalSeats(Integer performID) {
        Ticket ticket = ticketRepository.getOne(performRepository.getOne(performID).getTicketID());
        int number = ticket.getNormalNum();
        int start = ticket.getVipNum() + 1;
        int rowNum = number/20;
        List<String> normalSeat = new ArrayList<>();
        normalSeat.add("" + start);
        for (int i = 0; i < rowNum; i++) {
            normalSeat.add("eeeeeeeeeeeeeeeeeeee");
        }
        return new ResponseResult<>(true, "", normalSeat);
    }


}
