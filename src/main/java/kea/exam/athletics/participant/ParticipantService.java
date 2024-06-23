package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
import kea.exam.athletics.discipline.DisciplineService;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import kea.exam.athletics.participant.utils.ParticipantMapper;
import kea.exam.athletics.participant.utils.ParticipantUtils;
import kea.exam.athletics.result.ResultService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {


    private final ParticipantRepository participantRepository;
    private final ResultService resultService;
    private final DisciplineService disciplineService;
    private final DisciplineRepository disciplineRepository;

    public ParticipantService(ParticipantRepository participantRepository, ResultService resultService, DisciplineService disciplineService, DisciplineRepository disciplineRepository) {
        this.participantRepository = participantRepository;
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

        ParticipantMapper participantMapper = new ParticipantMapper();

        return participants.map(participantMapper::toDTO);

    }

    public List<ParticipantResponseFullDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        ParticipantMapper participantMapper = new ParticipantMapper();
        return participants.stream()
                .map(participantMapper::toFullDTO)
                .toList();
    }


    public ParticipantResponseFullDTO getParticipantById(Long participantId) {

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Event", participantId));

        ParticipantMapper participantMapper = new ParticipantMapper();

        return participantMapper.toFullDTO(participant);

    }

    public Participant getParticipantEntityById(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant", participantId));
    }


    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO participantRequestDTO) {

        Participant participant = toEntity(participantRequestDTO);

        participantRepository.save(participant);

        participantRequestDTO.disciplines()
                .forEach(disciplineId -> {
                    Discipline discipline = disciplineService.getDisciplineById(disciplineId);
                    participant.getDisciplines()
                            .add(discipline);
                });

        participantRepository.save(participant);

        ParticipantMapper participantMapper = new ParticipantMapper();

        return participantMapper.toDTO(participant);
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

        ParticipantMapper participantMapper = new ParticipantMapper();

        return participantMapper.toDTO(participant);
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

    public ParticipantResponseDTO updateParticipant(ParticipantRequestDTO participantRequestDTO, Long participantId) {
        Participant participantToUpdate = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException(participantRequestDTO.name(), participantId));

        String genderString = participantRequestDTO.gender();
        Gender gender = Gender.valueOf(genderString.toUpperCase());

        participantToUpdate.setName(participantRequestDTO.name());
        participantToUpdate.setGender(gender);
        participantToUpdate.setAge(participantRequestDTO.age());
        participantToUpdate.setClub(participantRequestDTO.club());
        participantToUpdate.getDisciplines()
                .clear();
        participantToUpdate.setAgeGroup(new ParticipantUtils().ageToAgeGroup(participantRequestDTO.age()));

        participantRepository.save(participantToUpdate);

        participantRequestDTO.disciplines()
                .forEach(disciplineId -> {
                    Discipline discipline = disciplineService.getDisciplineById(disciplineId);
                    participantToUpdate.getDisciplines()
                            .add(discipline);
                });

        participantRepository.save(participantToUpdate);

        ParticipantMapper participantMapper = new ParticipantMapper();

        return participantMapper.toDTO(participantToUpdate);


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
