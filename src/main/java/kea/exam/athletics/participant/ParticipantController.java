package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }


    @GetMapping
    public ResponseEntity<Page<ParticipantResponseDTO>> getAllParticipants(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> filterBy,
            @RequestParam Optional<String> filterValue,
            @RequestParam Optional<String> searchBy
    ) {
        return ResponseEntity.ok(participantService.getAllParticipants(pageIndex, pageSize, sortDir, sortBy, filterBy, filterValue, searchBy));
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantResponseFullDTO> getParticipantById(@PathVariable Long participantId) {
        return ResponseEntity.ok(participantService.getParticipantById(participantId));
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> createParticipant(@RequestBody ParticipantRequestDTO participantRequestDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantRequestDTO));
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<ParticipantResponseDTO> deleteParticipantById(@PathVariable Long participantId) {
        return ResponseEntity.ok(participantService.deleteParticipantById(participantId));
    }
}
