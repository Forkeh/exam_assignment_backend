package kea.exam.athletics.discipline.utils;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import org.springframework.stereotype.Service;

public class DisciplineMapper {


    public DisciplineResponseSmallDTO toSmallDTO(Discipline discipline) {
        return new DisciplineResponseSmallDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType()
                        .toString()
        );
    }
}
