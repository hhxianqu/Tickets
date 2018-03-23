package cn.edu.nju.tickets.controller;

import cn.edu.nju.tickets.service.UserService;
import cn.edu.nju.tickets.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String identity,
                                   HttpServletResponse response) {
        ResponseResult<?> result = userService.login(username, password, identity);
        if (result.isSucceed()) {
            Cookie usernameCookie = new Cookie("username", username);
            Cookie identityCookie = new Cookie("identity", identity);
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(Integer.MAX_VALUE);
            usernameCookie.setHttpOnly(true);
            response.addCookie(usernameCookie);
            identityCookie.setPath("/");
            identityCookie.setMaxAge(Integer.MAX_VALUE);
            identityCookie.setHttpOnly(true);
            response.addCookie(identityCookie);
        }
        return result;
    }

    @RequestMapping("/check")
    public ResponseResult<?> getUserInfo(@CookieValue(value = "username", required = false) String username,
                                         @CookieValue(value = "identity", required = false) String identity) {
        return userService.getUserInfo(username, identity);
    }

    @GetMapping("/logout")
    public ResponseResult<?> logout (@CookieValue(value = "username", required = false) Cookie usernameCookie,
                                     @CookieValue(value = "identity", required = false) Cookie identityCookie,
                                     HttpServletResponse response) {
        if (usernameCookie == null || identityCookie == null) {
            return new ResponseResult<>(false, "您已退出登录", null);
        }
        usernameCookie.setMaxAge(0);
        identityCookie.setMaxAge(0);
        response.addCookie(usernameCookie);
        response.addCookie(identityCookie);
        return new ResponseResult<>(true, "退出成功", null);
    }

    @GetMapping("/delete")
    public ResponseResult<UserVO> delete (@CookieValue(value = "username", required = false) Cookie usernameCookie,
                                          @CookieValue(value = "identity", required = false) Cookie identityCookie,
                                          HttpServletResponse response) {
        usernameCookie.setMaxAge(0);
        identityCookie.setMaxAge(0);
        response.addCookie(usernameCookie);
        response.addCookie(identityCookie);
        return userService.delete(usernameCookie.getValue());
    }


    @PostMapping("/update")
    public ResponseResult<UserVO> update(@RequestParam String email,
                                         @RequestParam String username) {
        return userService.update(email, username);
    }

    @PostMapping("/sendEmail")
    public ResponseResult<String> sendEmail(@RequestParam String emailAddress) {
        return userService.sendEmail(emailAddress);
    }

    @PostMapping("/checkVerification")
    public Boolean checkVerificationCode(@RequestParam String emailAddress,
                                         @RequestParam String verificationCode) {
        return userService.checkVerificationCode(emailAddress, verificationCode);
    }

    @PostMapping("/userSignIn")
    public ResponseResult<UserVO> signIn(@RequestBody UserRegisterVO userRegisterVO,
                                         HttpServletResponse response) {
        ResponseResult<UserVO> responseResult = userService.signIn(userRegisterVO);
//        if (responseResult.isSucceed()) {
//            Cookie usernameCookie = new Cookie("username", responseResult.data.getUsername());
//            Cookie identityCookie = new Cookie("identity", "user");
//            usernameCookie.setPath("/");
//            usernameCookie.setMaxAge(Integer.MAX_VALUE);
//            usernameCookie.setHttpOnly(true);
//            response.addCookie(usernameCookie);
//            identityCookie.setPath("/");
//            identityCookie.setMaxAge(Integer.MAX_VALUE);
//            identityCookie.setHttpOnly(true);
//            response.addCookie(identityCookie);
//        }
        return responseResult;
    }

    @PostMapping("/venueSignIn")
    public ResponseResult<VenueVO> signIn(@RequestBody VenueRegisterVO venueRegisterVO) {
        return userService.signIn(venueRegisterVO);
    }
}
