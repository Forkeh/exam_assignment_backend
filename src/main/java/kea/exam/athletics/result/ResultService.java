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
            Optional<String> filterBy
    ) {

        System.out.println("filterBy = " + filterBy);

        Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );

        Page<Result> results;


        if (filterBy.isPresent()) {
            Long disciplineId = Long.parseLong(filterBy.get());
            results = resultRepository.findAllByDisciplineId(pageable, disciplineId);
        } else {
            results = resultRepository.findAll(pageable);
        }


        ResultMapper resultMapper = new ResultMapper();

        return results.map(resultMapper::toDTO);

//        return resultRepository.findAll(pageable)
//                .map(resultMapper::toDTO);

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
        isParticipantAssignedToDiscipline(participant, discipline);

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

    void isParticipantAssignedToDiscipline(Participant participant, Discipline discipline) {
        if (!participant.getDisciplines()
                .contains(discipline)) {
            throw new BadRequestException("Participant is not assigned to discipline");
        }
    }
}
