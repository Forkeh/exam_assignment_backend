package kea.exam.athletics.result;

import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.Participant;
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

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
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


    public Result deleteResultById(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result", resultId));
        resultRepository.delete(result);
        return result;
    }

    public void deleteResultsByParticipant(Participant participant) {
        List<Result> results = resultRepository.findAllByParticipant(participant);
        resultRepository.deleteAll(results);

    }
}
