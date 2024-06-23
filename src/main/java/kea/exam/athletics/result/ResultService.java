package kea.exam.athletics.result;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
import kea.exam.athletics.enums.AgeGroup;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.exceptions.BadRequestException;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.ParticipantRepository;
import kea.exam.athletics.result.dto.ResultRequestDTO;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import kea.exam.athletics.result.utils.ResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {


    private final ResultRepository resultRepository;
    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultMapper resultMapper;

    public ResultService(ResultRepository resultRepository, ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultMapper resultMapper) {
        this.resultRepository = resultRepository;
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultMapper = resultMapper;
    }

    public Page<ResultResponseDTO> getAllResults(
            Integer pageNum,
            Integer pageSize,
            Optional<String> sortDir,
            Optional<String> sortBy,
            Optional<String> filterBy,
            Optional<String> filterByGender,
            Optional<String> filterByAge
    ) {

        System.out.println("filterBy = " + filterBy);

        Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );

        Page<Result> results;

        if (filterBy.isPresent() && filterByGender.isPresent() && filterByAge.isPresent()) {
            Gender gender = Gender.valueOf(filterByGender.get());
            AgeGroup ageGroup = AgeGroup.valueOf(filterByAge.get());

            Long disciplineId = Long.parseLong(filterBy.get());
            results = resultRepository.findAllByDisciplineIdAndParticipantGenderAndParticipantAgeGroup(pageable, disciplineId, gender, ageGroup);

        } else if (filterBy.isPresent() && filterByGender.isPresent()) {
            Gender gender = Gender.valueOf(filterByGender.get());

            Long disciplineId = Long.parseLong(filterBy.get());
            results = resultRepository.findAllByDisciplineIdAndParticipantGender(pageable, disciplineId, gender);

        } else if (filterBy.isPresent() && filterByAge.isPresent()) {
            AgeGroup ageGroup = AgeGroup.valueOf(filterByAge.get());

            Long disciplineId = Long.parseLong(filterBy.get());
            results = resultRepository.findAllByDisciplineIdAndParticipantAgeGroup(pageable, disciplineId, ageGroup);

        } else if (filterBy.isPresent()) {

            Long disciplineId = Long.parseLong(filterBy.get());
            results = resultRepository.findAllByDisciplineId(pageable, disciplineId);

        } else {
            System.out.println("No filterBy");
            results = resultRepository.findAll(pageable);

        }


        return results.map(resultMapper::toDTO);
    }


    public ResultResponseDTO deleteResultById(Long resultId) {

        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result", resultId));

        resultRepository.delete(result);

        return resultMapper.toDTO(result);
    }

    public void deleteResultsByParticipant(Participant participant) {
        List<Result> results = resultRepository.findAllByParticipant(participant);
        resultRepository.deleteAll(results);
    }

    public ResultResponseDTO createResult(ResultRequestDTO resultRequestDTO) {
        Participant participant = participantRepository.findById(resultRequestDTO.participantId())
                .orElseThrow(() -> new EntityNotFoundException("Participant", resultRequestDTO.participantId()));

        Discipline discipline = disciplineRepository.findById(resultRequestDTO.disciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline", resultRequestDTO.disciplineId()));

        //Check if participant is assigned to discipline
        isParticipantAssignedToDiscipline(participant, discipline);

        Result result = resultMapper.toEntity(resultRequestDTO.result(), participant, discipline);
        resultRepository.save(result);
        return resultMapper.toDTO(result);
    }

    public List<ResultResponseDTO> createMultipleResults(List<ResultRequestDTO> resultRequestDTOs) {
        return resultRequestDTOs.stream()
                .map(this::createResult)
                .toList();
    }


    public ResultResponseDTO updateResult(ResultRequestDTO resultRequestDTO, Long resultId) {
        Result resultToUpdate = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result", resultId));

        resultToUpdate.setResult(resultRequestDTO.result());

        resultRepository.save(resultToUpdate);
        return resultMapper.toDTO(resultToUpdate);
    }

    void isParticipantAssignedToDiscipline(Participant participant, Discipline discipline) {
        if (!participant.getDisciplines()
                .contains(discipline)) {
            throw new BadRequestException("Participant is not assigned to discipline");
        }
    }
}
