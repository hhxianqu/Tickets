package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByState(String state);

    List<Order> findByStateAndEmail(String state, String email);

    List<Order> findAllByEmail(String email);
}
