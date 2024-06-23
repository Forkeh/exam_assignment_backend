package kea.exam.athletics.participant.utils;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.utils.DisciplineMapper;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantResponseFullDTO toFullDTO(Participant participant) {

        DisciplineMapper disciplineMapper = new DisciplineMapper();

        return new ParticipantResponseFullDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClub(),
                participant.getDisciplines()
                        .stream()
                        .map(disciplineMapper::toSmallDTO)
                        .toList(),
                participant.getAgeGroup()
                        .toString()
        );
    }

    public ParticipantResponseDTO toDTO(Participant participant) {
        return new ParticipantResponseDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClub(),
                participant.getDisciplines()
                        .stream()
                        .map(Discipline::getName)
                        .toList(),
                participant.getAgeGroup()
                        .toString()
        );
    }

    public Participant toEntity(ParticipantRequestDTO participantRequestDTO) {
        String genderString = participantRequestDTO.gender();
        Gender gender = Gender.valueOf(genderString.toUpperCase());
        ParticipantUtils ParticipantUtils = new ParticipantUtils();

        return new Participant(
                participantRequestDTO.name(),
                gender,
                participantRequestDTO.age(),
                participantRequestDTO.club(),
                ParticipantUtils.ageToAgeGroup(participantRequestDTO.age())
        );


    }
}
