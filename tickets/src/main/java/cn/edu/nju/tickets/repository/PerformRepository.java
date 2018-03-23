package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.Perform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformRepository extends JpaRepository<Perform, Integer> {

    List<Perform> findAllByType(String type);

    List<Perform> findByPerformAudit(Boolean audit);

    List<Perform> findAllByVenueNum(String venueNum);
}
