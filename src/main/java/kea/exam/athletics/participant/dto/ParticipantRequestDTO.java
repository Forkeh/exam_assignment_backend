package kea.exam.athletics.participant.dto;

import java.util.List;

public record ParticipantRequestDTO(

        String name,
        String gender,
        Integer age,
        String club,
        List<Long> disciplines

) {
}
