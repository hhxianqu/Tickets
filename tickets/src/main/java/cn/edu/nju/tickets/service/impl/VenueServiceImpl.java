package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.*;
import cn.edu.nju.tickets.repository.*;
import cn.edu.nju.tickets.service.VenueService;
import cn.edu.nju.tickets.util.DateUtil;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PerformRepository performRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ChangedVenueRepository changedVenueRepository;

    private VenueVO converVenueToVO (Venue venue) {
        if (venue == null){
            return null;
        }
        VenueVO venueVO = new VenueVO();
        Account account = accountRepository.getOne(venue.getAccountID());

        venueVO.setCity(venue.getCity());
        venueVO.setDetailPosition(venue.getDetailPosition());
        venueVO.setSeat(venue.getSeat());
        venueVO.setVenueName(venue.getVenueName());
        venueVO.setVenueNumber(venue.getVenueNumber());
        venueVO.setId(venue.getId());
        venueVO.setVenueEmail(venue.getVenueEmail());
        venueVO.setAccountID(venue.getAccountID());
        venueVO.setBalance(account.getBalance());

        return venueVO;
    }

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
     * 申请演出
     * @param vo
     * @return
     */
    @Override
    public ResponseResult<PerformVO> apply(PerformRegisterVO vo) {

        Ticket ticket = new Ticket();
        ticket.setVipNum(vo.getVipNum());
        ticket.setVipPrice(vo.getVipPrice());
        ticket.setNormalNum(vo.getNormalNum());
        ticket.setNormalPrice(vo.getNormalPrice());
        ticket.setNormalSale(0);
        ticket.setVipSale(0);
        ticket.setSettled(false);
        Integer ticketID = ticketRepository.save(ticket).getId();

        Perform perform = new Perform();
        perform.setPerformPass(false);
        perform.setPerformAudit(false);
        perform.setPerformName(vo.getPerformName());
        perform.setType(vo.getPerformType());
        perform.setVenueNum(vo.getVenueNum());
        perform.setTicketID(ticketID);
        perform.setStartTime(DateUtil.convertStringToDate(vo.getStartDate()));
        perform.setEndTime(DateUtil.convertStringToDate(vo.getEndDate()));
        performRepository.save(perform);

        return new ResponseResult<>(true, "",
                convertPerformToVO(perform, venueRepository.findByVenueNumber(perform.getVenueNum()), ticket));

    }

    /**
     * 加载场馆信息
     * @param venueNum
     * @return
     */
    @Override
    public ResponseResult<VenueVO> getVenueInfo(String venueNum) {
        return new ResponseResult<>(true, "",
                                converVenueToVO(venueRepository.findByVenueNumber(venueNum)));
    }

    /**
     * 将场馆修改信息提交审核
     * @param venueChangeVO
     * @return
     */
    @Override
    public ResponseResult<Boolean> changeVenueInfo(VenueChangeVO venueChangeVO) {
        String venueNum = venueChangeVO.getVenueNum();
        ChangedVenue changedVenue = new ChangedVenue();
        if (changedVenueRepository.findByVenueNum(venueNum) != null) {
            changedVenue = changedVenueRepository.findByVenueNum(venueNum);
        }
        changedVenue.setAudit(false);
        changedVenue.setPass(false);
        changedVenue.setCity(venueChangeVO.getCity());
        changedVenue.setDetailPosition(venueChangeVO.getDetailPosition());
        changedVenue.setEmail(venueChangeVO.getEmail());
        changedVenue.setSeat(venueChangeVO.getSeat());
        changedVenue.setVenueName(venueChangeVO.getVenueName());
        changedVenue.setVenueNum(venueChangeVO.getVenueNum());

        changedVenueRepository.save(changedVenue);

        return new ResponseResult<>(true, "已提交审核", true);
    }

    /**
     * 获取未开场演出
     * @param venueNum
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getNotStartPerform(String venueNum) {
        Venue venue = venueRepository.findByVenueNumber(venueNum);
        List<Perform> performs = performRepository.findAllByVenueNum(venueNum);
        List<PerformVO> performVOS = new ArrayList<>();
        for (Perform perform : performs) {
            if (perform.isPerformAudit() && perform.isPerformPass() && perform.getStartTime().compareTo(new Date()) > 0) {
                performVOS.add(convertPerformToVO(perform, venue, ticketRepository.getOne(perform.getTicketID())));
            }
        }
        return new ResponseResult<>(true, "", performVOS);
    }

    /**
     * 加载对应场馆的演出
     * @param venueNum
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getPerformanceByVenueNum(String venueNum) {
        Venue venue = venueRepository.findByVenueNumber(venueNum);
        List<Perform> performs = performRepository.findAllByVenueNum(venueNum);
        List<PerformVO> performVOS = new ArrayList<>();
        for (Perform perform : performs) {
            if (perform.isPerformAudit() && perform.isPerformPass()) {
                performVOS.add(convertPerformToVO(perform, venue, ticketRepository.getOne(perform.getTicketID())));
            }
        }
        return new ResponseResult<>(true, "", performVOS);
    }


}
