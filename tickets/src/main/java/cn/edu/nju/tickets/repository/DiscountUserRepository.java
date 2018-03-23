package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.DiscountUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountUserRepository extends JpaRepository<DiscountUser, Integer> {

    List<DiscountUser> findAllByEmail(String email);

    DiscountUser findByEmailAndDiscountID(String email, int discountID);
}
