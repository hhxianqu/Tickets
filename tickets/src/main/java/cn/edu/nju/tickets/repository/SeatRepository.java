package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findAllByPerformID(int performID);

    List<Seat> findByEmailAndPerformID(String email, int performID);

    Seat findByEmailAndPerformIDAndSeatNum(String email, int performID, int seatNum);
}
