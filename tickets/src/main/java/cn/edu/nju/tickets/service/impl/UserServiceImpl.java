package cn.edu.nju.tickets.service.impl;

import cn.edu.nju.tickets.entity.Account;
import cn.edu.nju.tickets.entity.Manager;
import cn.edu.nju.tickets.entity.User;
import cn.edu.nju.tickets.entity.Venue;
import cn.edu.nju.tickets.repository.AccountRepository;
import cn.edu.nju.tickets.repository.ManagerRepository;
import cn.edu.nju.tickets.repository.UserRepository;
import cn.edu.nju.tickets.repository.VenueRepository;
import cn.edu.nju.tickets.service.MailService;
import cn.edu.nju.tickets.service.UserService;
import cn.edu.nju.tickets.util.Identity;
import cn.edu.nju.tickets.util.RandomUtil;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    private ConcurrentHashMap<String, String> EMAIL_CODE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private AccountRepository accountRepository;

    private UserVO convertUserToVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setEmail(user.getEmail());
        vo.setLevel(user.getLevel());
        vo.setScore(user.getScore());
        vo.setUsername(user.getUsername());
        vo.setDelete(user.isDelete());
        vo.setAccountID(user.getAccountID());

        return vo;
    }

    private ManagerVO convertManagerToVO(Manager manager) {
        if (manager == null) {
            return null;
        }
        ManagerVO managerVO = new ManagerVO();
        managerVO.setManagerNumber(manager.getManagerNumber());
        managerVO.setAccountID(manager.getAccountID());
        return managerVO;
    }

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

    /**
     * 会员、场馆和经理的登录
     * @param username
     * @param password
     * @param identity
     * @return
     */
    @Override
    public ResponseResult<?> login(String username, String password, String identity) {
        User user = null;
        Manager manager = null;
        Venue venue = null;
        switch (identity) {
            case Identity.USER:
                user = userRepository.findByEmail(username);
                if (user == null) {
                    return new ResponseResult<>(false, "用户名不存在", null);
                }
                if (!password.equals(user.getPassword())) {
                    return new ResponseResult<>(false, "密码错误", null);
                }
                if (user.isDelete()) {
                    return new ResponseResult<>(false, "您的会员已被注销,无法登录",null);
                }
                return new ResponseResult<>(true, "登录成功", convertUserToVO(user));
            case Identity.MANAGER:
                manager = managerRepository.findByManagerNumber(username);
                if (manager == null) {
                    return new ResponseResult<>(false, "用户名不存在", null);
                }
                if (!password.equals(manager.getManagerPassword())) {
                    return new ResponseResult<>(false, "密码错误", null);
                }
                return new ResponseResult<>(true, "登录成功", convertManagerToVO(manager));
            default:
                venue = venueRepository.findByVenueNumber(username);
                if (venue == null) {
                    return new ResponseResult<>(false, "", null);
                }
                if (!password.equals(venue.getVenuePassword())) {
                    return new ResponseResult<>(false, "密码错误", null);
                }
                if (!venue.isPassed() || !venue.isAudited()) {
                    return new ResponseResult<>(false, "未通过审核，不可以登录", null);
                }
                return new ResponseResult<>(true, "登录成功", converVenueToVO(venue));
        }

    }

    /**
     * 加载个人信息
     * @param username
     * @param identity
     * @return
     */
    @Override
    public ResponseResult<?> getUserInfo(String username, String identity) {
        if (username == null || identity ==null) {
            return new ResponseResult<>(false, "", null);
        }
        if (identity.equals(Identity.USER)) {
            User user = userRepository.findByEmail(username);
            if (user == null) {
                return new ResponseResult<>(false, "", null);
            }
            if (user.isDelete()) {
                return new ResponseResult<>(false, "您的会员已被注销,无法登录",null);
            }
            return new ResponseResult<>(true, "user", convertUserToVO(user));
        }
        else if (identity.equals(Identity.MANAGER)) {
            Manager manager = managerRepository.findByManagerNumber(username);
            if (manager == null) {
                return new ResponseResult<>(false, "", null);
            }
            return new ResponseResult<>(true, "manager", convertManagerToVO(manager));
        }
        else {
            Venue venue = venueRepository.findByVenueNumber(username);
            if (venue == null) {
                return new ResponseResult<>(false, "", null);
            }
            return new ResponseResult<>(true, "venue", converVenueToVO(venue));
        }
    }

    /**
     * 注销会员的账号，但是不删除信息
     * @param email
     * @return
     */
    @Override
    public ResponseResult<UserVO> delete(String email) {
        User user = userRepository.findByEmail(email);
        user.setDelete(true);
        userRepository.save(user);
        return new ResponseResult<>(true, "", convertUserToVO(user));
    }

    /**
     * 更新个人信息
     * @param email
     * @param username
     * @return
     */
    @Override
    public ResponseResult<UserVO> update(String email, String username) {
        if (username.equals("")) {
            return new ResponseResult<>(false, "请输入姓名", null);
        }
        User user = userRepository.findByEmail(email);
        user.setUsername(username);
        userRepository.save(user);
        return new ResponseResult<>(true, "修改成功", convertUserToVO(user));
    }

    /**
     * 用户注册
     * @param userRegisterVO
     * @return
     */
    @Override
    public ResponseResult<UserVO> signIn(UserRegisterVO userRegisterVO) {
        User user = new User();
        user.setUsername(userRegisterVO.getUsername());
        user.setPassword(userRegisterVO.getPassword());
        user.setEmail(userRegisterVO.getEmail());
        user.setAccountID(0);
        user.setLevel(0);
        user.setScore(0);
        user.setDelete(false);

        userRepository.save(user);
        return new ResponseResult<>(true, "注册成功", convertUserToVO(user));
    }

    /**
     * 场馆注册
     * @param venueRegisterVO
     * @return
     */
    @Override
    public ResponseResult<VenueVO> signIn(VenueRegisterVO venueRegisterVO) {
        Account account = new Account();
        account.setBalance(0);
        account.setAccountName("支付宝");
        account.setIdentity("venue");
        account.setUsername(null);
        accountRepository.save(account);

        Venue venue = new Venue();
        venue.setVenueName(venueRegisterVO.getVenueName());
        venue.setSeat(venueRegisterVO.getSeat());
        venue.setPassed(false);
        venue.setVenueNumber(null);
        venue.setAudited(false);
        venue.setCity(venueRegisterVO.getCity());
        venue.setDetailPosition(venueRegisterVO.getDetailPosition());
        venue.setVenueEmail(venueRegisterVO.getEmail());
        venue.setVenuePassword(venueRegisterVO.getVenuePassword());
        venue.setAccountID(account.getId());

        venueRepository.save(venue);
        return new ResponseResult<>(true, "注册成功", converVenueToVO(venue));
    }

    /**
     * 邮箱发送验证码
     * @param emailAddress
     * @return
     */
    @Override
    public ResponseResult<String> sendEmail(String emailAddress) {
        String verificationCode = RandomUtil.generateCode(4);
        mailService.sendMail(emailAddress, "感谢您注册使用TicketSystem",
                            "您的验证码是：" + verificationCode);
        EMAIL_CODE_MAP.put(emailAddress, verificationCode);
        return new ResponseResult<>(true, "发送成功", verificationCode);
    }

    /**
     * 检查验证码是否正确
     * @param email
     * @param verificationCode
     * @return
     */
    @Override
    public Boolean checkVerificationCode(String email, String verificationCode) {
        return EMAIL_CODE_MAP.containsKey(email) && EMAIL_CODE_MAP.get(email).equals(verificationCode);
    }

}
