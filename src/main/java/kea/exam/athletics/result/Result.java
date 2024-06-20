package kea.exam.athletics.result;

import jakarta.persistence.*;
import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.enums.ResultType;
import kea.exam.athletics.participant.Participant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    private String result;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    public Result(ResultType resultType, String result, Participant participant, Discipline discipline) {
        this.resultType = resultType;
        this.result = result;
        this.participant = participant;
        this.discipline = discipline;
    }
}
