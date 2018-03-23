package cn.edu.nju.tickets.service;

import cn.edu.nju.tickets.vo.PerformVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformServiceTest {
    @Autowired
    private PerformService performService;

    @Test
    public void testGetCity() {
        List<String> cities = performService.getCity("concert").getData();
        cities.forEach(System.out::println);
    }

    @Test
    public void testGetAllUnsettledPerform() {
        List<PerformVO> list =  performService.getAllUnsettledPerform().getData();
        System.out.println(list.get(0).getPerformName());
    }
}
