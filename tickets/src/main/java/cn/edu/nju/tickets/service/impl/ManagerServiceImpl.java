package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.*;
import cn.edu.nju.tickets.repository.*;
import cn.edu.nju.tickets.service.ManagerService;
import cn.edu.nju.tickets.util.DateUtil;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private PerformRepository performRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ChangedVenueRepository changedVenueRepository;
    @Autowired
    private UserRepository userRepository;

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

    private  AccountVO convertAccountToVO (Account account) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccount(account.getAccountName());
        accountVO.setBalance(account.getBalance());
        accountVO.setId(account.getId());
        accountVO.setUsername(account.getUsername());
        accountVO.setIdentity(account.getIdentity());
        return accountVO;
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

    private VenueDifferentInfo convertDifferentInfoToVO(ChangedVenue changedVenue, Venue venue) {
        VenueDifferentInfo differentInfo = new VenueDifferentInfo();

        differentInfo.setId(venue.getId());
        differentInfo.setCity(venue.getCity());
        differentInfo.setDetailPosition(venue.getDetailPosition());
        differentInfo.setEmail(venue.getVenueEmail());
        differentInfo.setVenueName(venue.getVenueName());
        differentInfo.setSeat(venue.getSeat());

        differentInfo.setSeatChange(changedVenue.getSeat());
        differentInfo.setCityChange(changedVenue.getCity());
        differentInfo.setDetailPositionChange(changedVenue.getDetailPosition());
        differentInfo.setVenueNameChange(changedVenue.getVenueName());
        differentInfo.setEmailChange(changedVenue.getEmail());

        return differentInfo;
    }
    /**
     * 加载未审核的场馆修改信息
     * @return
     */
    @Override
    public ResponseResult<List<VenueDifferentInfo>> getAuditedChangedVenue() {
        List<VenueDifferentInfo> venueVOs = changedVenueRepository.findAllByAudit(false)
                                .stream()
                                .map(c -> convertDifferentInfoToVO(c, venueRepository.findByVenueNumber(c.getVenueNum())))
                                .collect(Collectors.toList());
        return new ResponseResult<>(true, "加载成功", venueVOs);
    }

    /**
     * 审核场馆的修改信息
     * @param pass
     * @param id
     * @return
     */
    @Override
    public ResponseResult<VenueVO> passChangedVenue(Boolean pass, Integer id) {
        Venue venue = venueRepository.getOne(id);
        ChangedVenue changedVenue = changedVenueRepository.findByVenueNum(venue.getVenueNumber());
        changedVenue.setAudit(true);
        changedVenue.setPass(pass);
        changedVenueRepository.save(changedVenue);
        if (pass) {
            venue.setVenueEmail(changedVenue.getEmail());
            venue.setDetailPosition(changedVenue.getDetailPosition());
            venue.setCity(changedVenue.getCity());
            venue.setVenueName(changedVenue.getVenueName());
            venue.setSeat(changedVenue.getSeat());
            venueRepository.save(venue);
        }

        return new ResponseResult<>(true, "修改成功", converVenueToVO(venue));
    }

    /**
     * 加载未审核场馆
     * @param audited
     * @return
     */
    @Override
    public ResponseResult<List<VenueVO>> getAuditedVenue(Boolean audited) {
        List<VenueVO> venueVOs = venueRepository.findByAudited(audited)
                                .stream().map(this::converVenueToVO).collect(Collectors.toList());
        return new ResponseResult<>(true, "加载成功", venueVOs);
    }

    /**
     * 通过场馆
     * @param pass
     * @param id
     * @return
     */
    public ResponseResult<VenueVO> passVenue(Boolean pass, Integer id) {
        Venue venue = venueRepository.getOne(id);
        venue.setAudited(true);
        venue.setPassed(pass);
        if (pass) {
            venue.setVenueNumber(String.valueOf(10000000 + id).substring(1));
        }
        venueRepository.save(venue);

        Account account = accountRepository.getOne(venue.getId());
        account.setUsername(venue.getVenueNumber());
        accountRepository.save(account);

        return new ResponseResult<>(true, "已审核", converVenueToVO(venue));
    }

    /**
     * 加载未审核演出
     * @param audited
     * @return
     */
    @Override
    public ResponseResult<List<PerformVO>> getAuditedPerform(Boolean audited) {
        List<PerformVO> performVOS = performRepository.findByPerformAudit(audited)
                                    .stream().map(p -> convertPerformToVO(p,
                                                    venueRepository.findByVenueNumber(p.getVenueNum()),
                                                    ticketRepository.getOne(p.getTicketID())))
                                    .collect(Collectors.toList());
        return new ResponseResult<>(true, "加载成功", performVOS);
    }

    /**
     * 通过演出
     * @param pass
     * @param id
     * @return
     */
    @Override
    public ResponseResult<PerformVO> passPerform(Boolean pass, Integer id) {
        Perform perform = performRepository.getOne(id);
        perform.setPerformAudit(true);
        perform.setPerformPass(pass);
        performRepository.save(perform);
        return new ResponseResult<>(true, "已审核", convertPerformToVO(perform,
                                                    venueRepository.findByVenueNumber(perform.getVenueNum()),
                                                    ticketRepository.getOne(perform.getTicketID())));
    }

    /**
     * 加载所有场馆
     * @return
     */
    @Override
    public ResponseResult<List<VenueVO>> getAllVenues() {
        return new ResponseResult<>(true, "",
                venueRepository.findAll().stream().map(this::converVenueToVO).collect(Collectors.toList()));
    }

    /**
     * 结算
     * @param performID
     * @param divide
     * @param pay
     * @return
     */
    @Override
    public ResponseResult<AccountVO> pay(Integer performID, int divide, int pay) {

        Ticket ticket = ticketRepository.getOne(performRepository.getOne(performID).getTicketID());
        ticket.setSettled(true);
        ticketRepository.save(ticket);

        Venue venue = venueRepository.findByVenueNumber(performRepository.getOne(performID).getVenueNum());
        Manager manager = managerRepository.getOne(1);
        Account venueAccount = accountRepository.getOne(venue.getAccountID());
        Account managerAccount = accountRepository.getOne(manager.getAccountID());

        int venueBalance = venueAccount.getBalance() + pay;

        venueAccount.setBalance(venueBalance);
        managerAccount.setBalance(managerAccount.getBalance() + divide);

        accountRepository.save(venueAccount);
        accountRepository.save(managerAccount);

        return new ResponseResult<>(true, "分配成功", convertAccountToVO(managerAccount));
    }

    @Override
    public ResponseResult<List<Integer>> getMemberLevel() {
        List<Integer> levelNumber = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            levelNumber.add(userRepository.countByLevel(i));
        }
        return new ResponseResult<>(true, "", levelNumber);
    }

    @Override
    public ResponseResult<Integer> getManagerBalance() {
        return new ResponseResult<>(true, "",accountRepository.getOne(managerRepository.findAll().get(0).getAccountID()).getBalance());
    }

}
