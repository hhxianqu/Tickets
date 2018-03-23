package cn.edu.nju.tickets.service.impl;


import cn.edu.nju.tickets.entity.DiscountMarket;
import cn.edu.nju.tickets.entity.DiscountUser;
import cn.edu.nju.tickets.repository.DiscountMarketRepository;
import cn.edu.nju.tickets.repository.DiscountUserRepository;
import cn.edu.nju.tickets.service.DiscountService;
import cn.edu.nju.tickets.util.DateUtil;
import cn.edu.nju.tickets.vo.DiscountMarketVO;
import cn.edu.nju.tickets.vo.DiscountUserVO;
import cn.edu.nju.tickets.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountMarketRepository marketRepository;

    @Autowired
    private DiscountUserRepository userRepository;

    private DiscountMarketVO convertDiscountMarketToVO(DiscountMarket market) {
        DiscountMarketVO vo = new DiscountMarketVO();

        vo.setId(market.getId());
        vo.setStartDate(DateUtil.convertDateToString(market.getStartDate()));
        vo.setEndDate(DateUtil.convertDateToString(market.getEndDate()));
        vo.setDiscountNum(market.getDiscountNum());
        vo.setScoreToDeduct(market.getScoreToDeduct());

        return vo;
    }

    private DiscountUserVO convertDiscountUserToVO(DiscountUser user, DiscountMarket market) {
        DiscountUserVO userVO = new DiscountUserVO();

        userVO.setId(user.getId());
        userVO.setDiscountID(user.getDiscountID());
        userVO.setEmail(user.getEmail());
        userVO.setOwnNum(user.getOwnNum());

        userVO.setDiscountNum(market.getDiscountNum());
        userVO.setStartDate(DateUtil.convertDateToString(market.getStartDate()));
        userVO.setEndDate(DateUtil.convertDateToString(market.getEndDate()));

        return userVO;
    }
    @Override
    public ResponseResult<List<DiscountMarketVO>> getAllDiscount() {
        return new ResponseResult<>(true, "",
                marketRepository.findAll()
                        .stream()
                        .map(this::convertDiscountMarketToVO)
                        .collect(Collectors.toList()));
    }

    /**
     * 加载用户自有优惠券
     * @param email
     * @return
     */
    @Override
    public ResponseResult<List<DiscountUserVO>> getSelfDiscount(String email) {
        return new ResponseResult<>(true, "",
                userRepository.findAllByEmail(email)
                        .stream()
                        .map(user -> convertDiscountUserToVO(user, marketRepository.getOne(user.getDiscountID())))
                        .collect(Collectors.toList()));
    }

    /**
     * 兑换优惠券
     * @param email
     * @param discountID
     * @return
     */
    @Override
    public ResponseResult<DiscountUserVO> convertDiscount(String email, Integer discountID) {
        DiscountUser user = new DiscountUser();
        if (userRepository.findByEmailAndDiscountID(email, discountID) != null) {
            user = userRepository.findByEmailAndDiscountID(email, discountID);
            user.setOwnNum(user.getOwnNum() + 1);
        } else {
            user.setDiscountID(discountID);
            user.setEmail(email);
            user.setOwnNum(1);
        }
        userRepository.save(user);
        return new ResponseResult<>(true, "兑换成功",
                convertDiscountUserToVO(user, marketRepository.getOne(user.getDiscountID())));
    }
}
