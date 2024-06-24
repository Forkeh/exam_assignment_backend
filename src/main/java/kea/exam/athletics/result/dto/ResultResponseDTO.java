package kea.exam.athletics.result.dto;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;

public record ResultResponseDTO(

        Long id,
        DisciplineResponseSmallDTO discipline,
        ParticipantResponseDTO participant,
        Long result
) {
}
