package kea.exam.athletics.participant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.enums.AgeGroup;
import kea.exam.athletics.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private String club;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "participant_discipline",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines = new ArrayList<>();


    public Participant(String name, Gender gender, Integer age, String club, AgeGroup ageGroup) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.club = club;
        this.ageGroup = ageGroup;
    }
}
