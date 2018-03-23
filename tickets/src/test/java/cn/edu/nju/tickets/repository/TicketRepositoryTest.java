package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketRepositoryTest {

    @Autowired
    public TicketRepository ticketRepository;

    @Test
    public void testGetOne() {
        Ticket ticket = ticketRepository.findById(1).get();

        System.out.println(ticket.getNormalNum());
    }
}
