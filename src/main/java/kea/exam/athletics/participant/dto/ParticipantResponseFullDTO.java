package kea.exam.athletics.participant.dto;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.enums.AgeGroup;
import kea.exam.athletics.enums.Gender;

import java.util.List;

public record ParticipantResponseFullDTO(
        Long id,
        String name,
        Gender gender,
        Integer age,
        String club,
        List<DisciplineResponseSmallDTO> disciplines,
        String ageGroup
) {
}
