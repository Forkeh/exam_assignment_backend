package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineService;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ParticipantService {


    private final ParticipantRepository participantRepository;
    private final DisciplineService disciplineService;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineService disciplineService) {
        this.participantRepository = participantRepository;
        this.disciplineService = disciplineService;
    }

    public List<ParticipantResponseDTO> getAllParticipants() {
        return participantRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

    }


    public ParticipantResponseFullDTO getParticipantById(Long participantId) {

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Event", participantId));

        return toFullDTO(participant);

    }

    private ParticipantResponseFullDTO toFullDTO(Participant participant) {
        return new ParticipantResponseFullDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClub(),
                participant.getDisciplines()
                        .stream()
                        .map(disciplineService::toSmallDTO)
                        .toList()
        );
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
