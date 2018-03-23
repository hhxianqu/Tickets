package cn.edu.nju.tickets.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        mailService.sendMail(
                "151250051@smail.nju.edu.cn",
                "欢迎注册Tickets",
                "您的验证码是1234，请尽快前往验证");
    }
}
