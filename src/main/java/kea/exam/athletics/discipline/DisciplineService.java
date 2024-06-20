package kea.exam.athletics.discipline;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.exceptions.EntityNotFoundException;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public Discipline getDisciplineById(Long disciplineId) {
        return disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("Discipline", disciplineId));
    }


    public List<DisciplineResponseSmallDTO> getAllDisciplines() {
        return disciplineRepository.findAll()
                .stream()
                .map(this::toSmallDTO)
                .toList();
    }

    public DisciplineResponseSmallDTO toSmallDTO(Discipline discipline) {
        return new DisciplineResponseSmallDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType()
                        .toString()
        );
    }
}
