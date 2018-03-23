package cn.edu.nju.tickets.repository;


import cn.edu.nju.tickets.entity.ChangedVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangedVenueRepository extends JpaRepository<ChangedVenue, Integer> {

    List<ChangedVenue> findAllByAudit(Boolean audit);

    ChangedVenue findByVenueNum(String venueNum);

}
