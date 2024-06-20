package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ParticipantService {


    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<ParticipantResponseDTO> getAllParticipants() {
        return participantRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

    }

    private ParticipantResponseDTO toDTO(Participant participant) {
        return new ParticipantResponseDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClub(),
                participant.getDisciplines()
                        .stream()
                        .map(Discipline::getName)
                        .toList()
        );
    }
}
