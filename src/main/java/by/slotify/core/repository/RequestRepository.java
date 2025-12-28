package by.slotify.core.repository;

import by.slotify.core.entity.Request;
import by.slotify.core.entity.TimeSlot;
import by.slotify.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Page<Request> findByUser(User user, Pageable pageable);
    List<Request> findByUserAndStatus(User user, Request.Status status);
    Page<Request> findByTimeSlot_Meeting_MeetingId(Integer meetingId, Pageable pageable);
    List<Request> findByStatus(Request.Status status);
    List<Request> findByTimeSlot(TimeSlot timeSlot);
    List<Request> findByTimeSlotAndStatus(TimeSlot timeSlot, Request.Status status);
}

