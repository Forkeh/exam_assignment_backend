package kea.exam.athletics.result;

import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("results")
public class ResultController {


    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<ResultResponseDTO>> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }
}
