package cn.edu.nju.tickets.repository;

import cn.edu.nju.tickets.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Integer> {
    Venue findByVenueNumber (String venueNumber);

    List<Venue> findByAudited(Boolean audited);
}
