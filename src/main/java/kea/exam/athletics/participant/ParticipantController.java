package kea.exam.athletics.participant;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponseDTO>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantResponseFullDTO> getParticipantById(@PathVariable Long participantId) {
        return ResponseEntity.ok(participantService.getParticipantById(participantId));
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> createParticipant(@RequestBody ParticipantRequestDTO participantRequestDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantRequestDTO));
    }
}
