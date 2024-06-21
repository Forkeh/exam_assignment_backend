package kea.exam.athletics.result;

import kea.exam.athletics.result.dto.ResultRequestDTO;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam Optional<String> filterBy
    ) {
        return ResponseEntity.ok(resultService.getAllResults(pageIndex, pageSize, sortDir, sortBy, filterBy));
    }

    @PostMapping
    public ResponseEntity<ResultResponseDTO> createResult(@RequestBody ResultRequestDTO resultRequestDTO) {
        return ResponseEntity.status(201)
                .body(resultService.createResult(resultRequestDTO));
    }

    @PutMapping("/{resultId}")
    public ResponseEntity<ResultResponseDTO> updateResult(@RequestBody ResultRequestDTO resultRequestDTO, @PathVariable Long resultId) {
        return ResponseEntity.ok(resultService.updateResult(resultRequestDTO, resultId));
    }

    @DeleteMapping("/{resultId}")
    public ResponseEntity<ResultResponseDTO> deleteResultById(@PathVariable Long resultId) {
        return ResponseEntity.ok(resultService.deleteResultById(resultId));
    }
}
