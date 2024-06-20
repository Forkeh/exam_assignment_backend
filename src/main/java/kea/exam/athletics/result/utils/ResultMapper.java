package kea.exam.athletics.result.utils;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.discipline.utils.DisciplineMapper;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.utils.ParticipantMapper;
import kea.exam.athletics.result.Result;
import kea.exam.athletics.result.dto.ResultResponseDTO;

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
}
