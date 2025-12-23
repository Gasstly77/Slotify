package by.slotify.core.repository;

import by.slotify.core.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Page<Meeting> findByStatus(Meeting.Status status, Pageable pageable);
    List<Meeting> findByStatus(Meeting.Status status);
    
    @Query("SELECT m FROM Meeting m WHERE m.status IN :statuses")
    Page<Meeting> findByStatusIn(@Param("statuses") List<Meeting.Status> statuses, Pageable pageable);
}

