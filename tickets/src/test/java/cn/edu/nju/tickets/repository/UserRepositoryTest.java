package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User();
        user.setEmail("123");
        user.setDelete(false);
        user.setLevel(0);
        user.setScore(0);
        user.setPassword("123");
        user.setUsername("123");

        Integer id = userRepository.save(user).getId();
        System.out.println(id);
    }

}
