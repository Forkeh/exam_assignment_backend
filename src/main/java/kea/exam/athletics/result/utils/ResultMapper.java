package kea.exam.athletics.result.utils;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.discipline.utils.DisciplineMapper;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.utils.ParticipantMapper;
import kea.exam.athletics.result.Result;
import kea.exam.athletics.result.dto.ResultRequestDTO;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResultMapper {

    public ResultResponseDTO toDTO(Result result) {
        DisciplineMapper disciplineMapper = new DisciplineMapper();
        ParticipantMapper participantMapper = new ParticipantMapper();

        DisciplineResponseSmallDTO discipline = disciplineMapper.toSmallDTO(result.getDiscipline());
        ParticipantResponseDTO participant = participantMapper.toDTO(result.getParticipant());


        return new ResultResponseDTO(
                result.getId(),
                discipline,
                participant,
                result.getResult()
        );
    }

    public Result toEntity(String result, Participant participant, Discipline discipline) {

        return new Result(
                discipline.getResultType(),
                result,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                participant,
                discipline
        );
    }
}
