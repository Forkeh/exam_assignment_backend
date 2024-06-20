package kea.exam.athletics.result;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
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

    public ResultService(ResultRepository resultRepository, ParticipantRepository participantRepository, DisciplineRepository disciplineRepository) {
        this.resultRepository = resultRepository;
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public Page<ResultResponseDTO> getAllResults(
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

        ResultMapper resultMapper = new ResultMapper();

        return resultRepository.findAll(pageable)
                .map(resultMapper::toDTO);

    }


    public ResultResponseDTO deleteResultById(Long resultId) {
        ResultMapper resultMapper = new ResultMapper();

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
        if (!participant.getDisciplines()
                .contains(discipline)) {
            throw new BadRequestException("Participant is not assigned to discipline");
        }

        ResultMapper resultMapper = new ResultMapper();
        Result result = resultMapper.toEntity(resultRequestDTO.result(), participant, discipline);
        resultRepository.save(result);
        return resultMapper.toDTO(result);
    }

    public ResultResponseDTO updateResult(ResultRequestDTO resultRequestDTO, Long resultId) {
        Result resultToUpdate = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result", resultId));

        resultToUpdate.setResult(resultRequestDTO.result());

        ResultMapper resultMapper = new ResultMapper();
        resultRepository.save(resultToUpdate);
        return resultMapper.toDTO(resultToUpdate);
    }
}
