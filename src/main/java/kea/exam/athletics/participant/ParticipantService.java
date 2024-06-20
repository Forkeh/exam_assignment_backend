package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineController;
import kea.exam.athletics.discipline.DisciplineService;
import kea.exam.athletics.discipline.utils.DisciplineMapper;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import kea.exam.athletics.result.ResultService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {


    private final ParticipantRepository participantRepository;
    private final DisciplineMapper disciplineMapper;
    private final ResultService resultService;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineMapper disciplineMapper, ResultService resultService) {
        this.participantRepository = participantRepository;
        this.disciplineMapper = disciplineMapper;
        this.resultService = resultService;
    }

    public Page<ParticipantResponseDTO> getAllParticipants(
            Integer pageNum,
            Integer pageSize,
            Optional<String> sortDir,
            Optional<String> sortBy,
            Optional<String> filterBy,
            Optional<String> searchBy
    ) {

        Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );

        return participantRepository.findAll(pageable)
                .map(this::toDTO);

//        return participantRepository.findAll()
//                .stream()
//                .map(this::toDTO)
//                .toList();

    }


    public ParticipantResponseFullDTO getParticipantById(Long participantId) {

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Event", participantId));

        return toFullDTO(participant);

    }

    public Participant getParticipantEntityById(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant", participantId));
    }


    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO participantRequestDTO) {

        String genderString = participantRequestDTO.gender();
        Gender gender = Gender.valueOf(genderString.toUpperCase());

        Participant participant = new Participant(
                participantRequestDTO.name(),
                gender,
                participantRequestDTO.age(),
                participantRequestDTO.club()
        );

        participantRepository.save(participant);

        return toDTO(participant);
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
                        .map(disciplineMapper::toSmallDTO)
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

    public ParticipantResponseDTO deleteParticipantById(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant", participantId));

        // TODO: Better solution?
        // Force initialization of the collection
        participant.getDisciplines()
                .size();

        resultService.deleteResultsByParticipant(participant);

        participantRepository.delete(participant);

        return toDTO(participant);
    }
}
