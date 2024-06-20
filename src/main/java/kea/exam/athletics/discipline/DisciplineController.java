package kea.exam.athletics.discipline;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineResponseSmallDTO>> getAllDisciplines() {
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @GetMapping("/{disciplineId}")
    public ResponseEntity<Discipline> getDisciplineById(@PathVariable Long disciplineId) {
        return ResponseEntity.ok(disciplineService.getDisciplineById(disciplineId));
    }


}
