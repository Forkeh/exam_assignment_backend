package kea.exam.athletics.result;

import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("results")
public class ResultController {


    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<Page<ResultResponseDTO>> getAllResults(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> filterBy,
            @RequestParam Optional<String> filterValue,
            @RequestParam Optional<String> searchBy
    ) {
        return ResponseEntity.ok(resultService.getAllResults(pageIndex, pageSize, sortDir, sortBy, filterBy, filterValue, searchBy));
    }
}
