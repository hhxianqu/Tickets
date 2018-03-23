package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.Perform;
import cn.edu.nju.tickets.entity.Ticket;
import cn.edu.nju.tickets.entity.Venue;
import cn.edu.nju.tickets.repository.PerformRepository;
import cn.edu.nju.tickets.repository.TicketRepository;
import cn.edu.nju.tickets.repository.VenueRepository;
import cn.edu.nju.tickets.service.PerformService;
import cn.edu.nju.tickets.util.DateUtil;
import cn.edu.nju.tickets.vo.PerformVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PerformServiceImpl implements PerformService {

    @Autowired
    private PerformRepository performRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private TicketRepository ticketRepository;

    private PerformVO convertPerformToVO (Perform perform, Venue venue, Ticket ticket) {
        if (perform == null || venue == null || ticket == null) {
            return null;
        }
        PerformVO vo = new PerformVO();
        vo.setId(perform.getId());
        vo.setPerformName(perform.getPerformName());
        vo.setStartTime(DateUtil.convertDateToString(perform.getStartTime()));
        vo.setEndTime(DateUtil.convertDateToString(perform.getEndTime()));
        vo.setNormalPrice(ticket.getNormalPrice());
        vo.setVipPrice(ticket.getVipPrice());
        vo.setVipNum(ticket.getVipNum());
        vo.setNormalNum(ticket.getNormalNum());
        vo.setVipSale(ticket.getVipSale());
        vo.setNormalSale(ticket.getNormalSale());
        vo.setType(perform.getType());
        vo.setCity(venue.getCity());
        vo.setDetailPosition(venue.getDetailPosition());
        vo.setVenueName(venue.getVenueName());
        vo.setSettled(ticket.getSettled());

        return vo;
    }

    /**
     * 加载对应城市的演出
     * @param type
     * @param city
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getPerformance(String type, String city) {
        List<PerformVO> result = new ArrayList<>();
        List<Perform> performs = performRepository.findAllByType(type);
        for (Perform perform : performs) {
            if (!perform.isPerformAudit() || !perform.isPerformPass()
                    || perform.getEndTime().compareTo(new Date()) < 0){
                continue;
            }
            Venue venue = venueRepository.findByVenueNumber(perform.getVenueNum());
            if (city.equals("all")) {
                result.add(convertPerformToVO(perform, venue, ticketRepository.getOne(perform.getTicketID())));
            } else {
                if (venue.getCity().equals(city)){
                    result.add(convertPerformToVO(perform, venue, ticketRepository.getOne(perform.getTicketID())));
                }
            }
        }
        return new ResponseResult<>(true, "加载成功", result);
    }

    /**
     * 加载城市
     * @param type
     * @return
     */
    @Override
    public ResponseResult<List<String>> getCity(String type) {
        List<Perform> performs = performRepository.findAllByType(type);
        Set<String> citySet = new HashSet<>();
        performs
                .stream()
                .map(p -> venueRepository.findByVenueNumber(p.getVenueNum()))
                .forEach(v -> citySet.add(v.getCity()));
        List<String> cities = new ArrayList<>(citySet);

        return new ResponseResult<>(true, "", cities);
    }

    /**
     * 加载场馆对应的未结算演出
     * @param venueNum
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getUnsettledPerform(String venueNum) {
        List<Perform> performList = performRepository.findAllByVenueNum(venueNum);
        List<PerformVO> result = new ArrayList<>();
        for (Perform perform : performList) {
            Ticket ticket = ticketRepository.findById(perform.getTicketID()).get();
            if (!ticket.getSettled() && perform.getEndTime().compareTo(new Date()) < 0
                    && perform.isPerformPass()) {
                result.add(convertPerformToVO(perform, venueRepository.findByVenueNumber(venueNum), ticket));
            }
        }
        return new ResponseResult<>(true, "", result);
    }

    /**
     * 加载场馆对应的已结算的演出
     * @param venueNum
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getSettledPerform(String venueNum) {
        List<Perform> performList = performRepository.findAllByVenueNum(venueNum);
        List<PerformVO> result = new ArrayList<>();
        for (Perform perform : performList) {
            Ticket ticket = ticketRepository.findById(perform.getTicketID()).get();
            if (ticket.getSettled() && perform.getEndTime().compareTo(new Date()) < 0
                    && perform.isPerformPass()) {
                result.add(convertPerformToVO(perform, venueRepository.findByVenueNumber(venueNum), ticket));
            }
        }
        return new ResponseResult<>(true, "", result);
    }

    /**
     * 加载所有未结算的演出
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getAllUnsettledPerform() {
        List<Perform> performList = performRepository.findAll();
        List<PerformVO> result = new ArrayList<>();
        for (Perform perform : performList) {
            Ticket ticket = ticketRepository.findById(perform.getTicketID()).get();
            Venue venue = venueRepository.findByVenueNumber(perform.getVenueNum());
            if (!ticket.getSettled() && perform.getEndTime().compareTo(new Date()) < 0
                    && perform.isPerformPass()) {
                result.add(convertPerformToVO(perform, venue, ticket));
            }
        }
        return new ResponseResult<>(true, "", result);
    }

    /**
     * 加载所有已结算的演出
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getAllSettledPerform() {
        List<Perform> performList = performRepository.findAll();
        List<PerformVO> result = new ArrayList<>();
        for (Perform perform : performList) {
            Ticket ticket = ticketRepository.findById(perform.getTicketID()).get();
            Venue venue = venueRepository.findByVenueNumber(perform.getVenueNum());
            if (ticket.getSettled() && perform.getEndTime().compareTo(new Date()) < 0
                    && perform.isPerformPass()) {
                result.add(convertPerformToVO(perform, venue, ticket));
            }
        }
        return new ResponseResult<>(true, "", result);
    }

    @Override
    public ResponseResult<PerformVO> getPerformById(Integer id) {
        Perform perform = performRepository.getOne(id);
        return new ResponseResult<>(true, "",
                convertPerformToVO(perform, venueRepository.findByVenueNumber(perform.getVenueNum()),
                                    ticketRepository.getOne(perform.getTicketID())));
    }
}
