package kea.exam.athletics.result;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.enums.ResultType;
import kea.exam.athletics.exceptions.BadRequestException;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.ParticipantRepository;
import kea.exam.athletics.result.dto.ResultRequestDTO;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ResultServiceUnitTest {

    @Mock
    private ResultRepository resultRepository;
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private DisciplineRepository disciplineRepository;
    @InjectMocks
    private ResultService resultService;

    @Test
    void createResult_success() {
        ResultRequestDTO requestDTO = new ResultRequestDTO(1L, 1L, "100");
        Participant participant = new Participant("John", Gender.MALE, 25, "Lions Club");
        Discipline discipline = new Discipline("100m Sprint", ResultType.TIME);
        Result result = new Result(ResultType.TIME, requestDTO.result(), LocalDateTime.of(2024, 1, 1, 0, 0), participant, discipline);

        participant.getDisciplines()
                .add(discipline);

        when(participantRepository.findById(requestDTO.participantId())).thenReturn(Optional.of(participant));
        when(disciplineRepository.findById(requestDTO.disciplineId())).thenReturn(Optional.of(discipline));
        when(resultRepository.save(any(Result.class))).thenReturn(result);

        ResultResponseDTO resultDTO = resultService.createResult(requestDTO);

        assertNotNull(resultDTO);
        verify(participantRepository).findById(requestDTO.participantId());
        verify(disciplineRepository).findById(requestDTO.disciplineId());
        verify(resultRepository).save(any(Result.class));
    }

    @Test
    void createResult_participantNotFound() {
        ResultRequestDTO requestDTO = new ResultRequestDTO(1L, 2L, "100");

        when(participantRepository.findById(requestDTO.participantId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resultService.createResult(requestDTO));
        verify(participantRepository).findById(requestDTO.participantId());
    }

    @Test
    void createResult_disciplineNotFound() {
        ResultRequestDTO requestDTO = new ResultRequestDTO(1L, 2L, "100");
        Participant participant = new Participant();

        when(participantRepository.findById(requestDTO.participantId())).thenReturn(Optional.of(participant));
        when(disciplineRepository.findById(requestDTO.disciplineId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resultService.createResult(requestDTO));
        verify(participantRepository).findById(requestDTO.participantId());
        verify(disciplineRepository).findById(requestDTO.disciplineId());
    }

    @Test
    void createResult_participantNotAssignedToDiscipline() {
        ResultRequestDTO requestDTO = new ResultRequestDTO(1L, 2L, "100");
        Participant participant = new Participant();
        Discipline discipline = new Discipline();

        when(participantRepository.findById(requestDTO.participantId())).thenReturn(Optional.of(participant));
        when(disciplineRepository.findById(requestDTO.disciplineId())).thenReturn(Optional.of(discipline));

        assertThrows(BadRequestException.class, () -> resultService.createResult(requestDTO));
        verify(participantRepository).findById(requestDTO.participantId());
        verify(disciplineRepository).findById(requestDTO.disciplineId());
    }
}