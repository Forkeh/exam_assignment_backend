package kea.exam.athletics.participant.dto;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.enums.Gender;

import java.util.List;

public record ParticipantResponseDTO(
        Long id,
        String name,
        Gender gender,
        Integer age,
        String club,
        List<String> disciplines,
        String ageGroup
) {
}
