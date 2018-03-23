package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    int countByLevel(int level);

}
