package kea.exam.athletics.discipline;

import jakarta.persistence.*;
import kea.exam.athletics.enums.ResultType;
import kea.exam.athletics.participant.Participant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "participant_discipline",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants = new HashSet<>();

    public Discipline(String name, ResultType resultType) {
        this.name = name;
        this.resultType = resultType;
    }
}
