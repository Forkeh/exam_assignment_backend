package kea.exam.athletics.result;

import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.participant.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByParticipant(Participant participant);

    Page<Result> findAllByDisciplineId(Pageable pageable, Long id);

    Page<Result> findAllByDisciplineIdAndParticipantGender(Pageable pageable, Long disciplineId, Gender gender);
}
