package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineController;
import kea.exam.athletics.discipline.DisciplineRepository;
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
    private final DisciplineService disciplineService;
    private final DisciplineRepository disciplineRepository;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineMapper disciplineMapper, ResultService resultService, DisciplineService disciplineService, DisciplineRepository disciplineRepository) {
        this.participantRepository = participantRepository;
        this.disciplineMapper = disciplineMapper;
        this.resultService = resultService;
        this.disciplineService = disciplineService;
        this.disciplineRepository = disciplineRepository;
    }

    public Page<ParticipantResponseDTO> getAllParticipants(
            Integer pageNum,
            Integer pageSize,
            Optional<String> sortDir,
            Optional<String> sortBy,
            Optional<String> filterBy,
            Optional<String> filterValue,
            Optional<String> searchBy
    ) {

        Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );

        Page<Participant> participants;

        if (filterBy.isPresent() && filterValue.isPresent()) {
            participants = switch (filterBy.get()
                    .toLowerCase()) {
                case "gender" -> participantRepository.findAllByGender(
                        pageable,
                        Gender.valueOf(filterValue.get()
                                .toUpperCase())
                );
                case "club" -> participantRepository.findAllByClubContainsIgnoreCase(
                        pageable,
                        filterValue.get()
                );
                case "discipline" -> participantRepository.findAllByDisciplinesId(
                        pageable,
                        Long.parseLong(filterValue.get())
                );
                default -> throw new IllegalArgumentException("Invalid filterBy value: " + filterBy.get());
            };
        } else if (searchBy.isPresent()) {
            participants = participantRepository.findAllByNameContainsIgnoreCase(pageable, searchBy.get());
        } else {
            participants = participantRepository.findAll(pageable);
        }

        return participants.map(this::toDTO);

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

        participantRequestDTO.disciplines()
                .forEach(disciplineId -> {
                    Discipline discipline = disciplineService.getDisciplineById(disciplineId);
                    participant.getDisciplines()
                            .add(discipline);
                });

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

    public Discipline addParticipantToDiscipline(Long disciplineId, Long participantId) {
        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        Participant participant = getParticipantEntityById(participantId);

        discipline.getParticipants()
                .add(participant);

        return disciplineRepository.save(discipline);
    }

    public Discipline removeParticipantFromDiscipline(Long disciplineId, Long participantId) {
        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        Participant participant = getParticipantEntityById(participantId);

        discipline.getParticipants()
                .remove(participant);

        return disciplineRepository.save(discipline);
    }
}
