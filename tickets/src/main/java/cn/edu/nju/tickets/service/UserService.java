package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.*;

public interface UserService {

    ResponseResult<?> login(String username, String password, String identity);

    ResponseResult<?> getUserInfo(String username, String identity);

    ResponseResult<UserVO> delete (String email);

    ResponseResult<UserVO> update(String email, String username);

    ResponseResult<UserVO> signIn(UserRegisterVO userRegisterVO);

    ResponseResult<VenueVO> signIn(VenueRegisterVO venueRegisterVO);

    ResponseResult<String> sendEmail(String emailAddress);

    Boolean checkVerificationCode(String email, String verificationCode);

}
