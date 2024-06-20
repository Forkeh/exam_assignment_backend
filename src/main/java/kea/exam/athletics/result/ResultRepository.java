package kea.exam.athletics.result;

import kea.exam.athletics.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByParticipant(Participant participant);
}
