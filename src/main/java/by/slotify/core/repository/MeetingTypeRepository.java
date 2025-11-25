package by.slotify.core.repository;

import by.slotify.core.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Integer> {
}

