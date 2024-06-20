package kea.exam.athletics.result;

import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import kea.exam.athletics.result.utils.ResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {


    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
    
    public List<ResultResponseDTO> getAllResults() {

        ResultMapper resultMapper = new ResultMapper();

        return resultRepository.findAll()
                .stream()
                .map(resultMapper::toDTO)
                .toList();
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
