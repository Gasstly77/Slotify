package by.slotify.core.repository;

import by.slotify.core.entity.ParticipationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationTypeRepository extends JpaRepository<ParticipationType, Integer> {
}

