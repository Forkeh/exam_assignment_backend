package kea.exam.athletics.discipline;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
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
