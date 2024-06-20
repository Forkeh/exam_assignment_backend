package kea.exam.athletics.participant;

import kea.exam.athletics.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Page<Participant> findAllByNameContainsIgnoreCase(Pageable pageable, String name);

    Page<Participant> findAllByGender(Pageable pageable, Gender gender);

    Page<Participant> findAllByClubContainsIgnoreCase(Pageable pageable, String s);

    Page<Participant> findAllByDisciplinesId(Pageable pageable, long l);
}
