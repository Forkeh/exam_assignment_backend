package kea.exam.athletics.result.dto;

public record ResultRequestDTO(

        Long disciplineId,
        Long participantId,
        String result
) {
}
