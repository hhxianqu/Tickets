package cn.edu.nju.tickets.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VenueServiceTest {

    @Autowired
    private VenueService venueService;

    @Test
    public void test() {
//        PerformRegisterVO vo = new PerformRegisterVO();
//        vo.setPerformName("gh");
//        vo.setPerformType("演唱会");
//        vo.setStartDate("2018-03-19");
//        vo.setEndDate("2018-03-20");
//        vo.setVipNum(100);
//        vo.setVipPrice(100);
//        vo.setNormalNum(100);
//        vo.setNormalPrice(100);
//        vo.setVenueNum("0000001");
//        ResponseResult<PerformVO> responseResult = venueService.apply(vo);
//        System.out.println(responseResult.getData());

        String venueNum = "0000001";
        venueService.getPerformanceByVenueNum(venueNum);
    }

}
