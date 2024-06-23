package kea.exam.athletics.config;

import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
import kea.exam.athletics.enums.AgeGroup;
import kea.exam.athletics.enums.Gender;
import kea.exam.athletics.enums.ResultType;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.ParticipantRepository;
import kea.exam.athletics.result.Result;
import kea.exam.athletics.result.ResultRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements ApplicationRunner {

    private final List<Participant> participants = new ArrayList<>();
    private final List<Discipline> disciplines = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();

    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public InitData(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Initializing data...");

        init();

    }


    private void init() {
        System.out.println("Creating items in database...");

        createParticipants();
        createDisciplines();
        assignDisciplinesToParticipants();
        assignResultsToParticipants();


    }


    private void createParticipants() {

        participants.add(new Participant("John", Gender.MALE, 25, "Lions Club", AgeGroup.ADULT));
        participants.add(new Participant("Emma", Gender.FEMALE, 22, "Tigers Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Liam", Gender.MALE, 30, "Lions Club", AgeGroup.ADULT));
        participants.add(new Participant("Olivia", Gender.FEMALE, 27, "Bears Club", AgeGroup.ADULT));
        participants.add(new Participant("Noah", Gender.MALE, 24, "Wolves Club", AgeGroup.ADULT));
        participants.add(new Participant("Ava", Gender.FEMALE, 29, "Tigers Club", AgeGroup.ADULT));
        participants.add(new Participant("William", Gender.MALE, 28, "Hawks Club", AgeGroup.ADULT));
        participants.add(new Participant("Sophia", Gender.FEMALE, 26, "Bears Club", AgeGroup.ADULT));
        participants.add(new Participant("James", Gender.MALE, 23, "Eagles Club", AgeGroup.ADULT));
        participants.add(new Participant("Isabella", Gender.FEMALE, 25, "Hawks Club", AgeGroup.ADULT));
        participants.add(new Participant("Benjamin", Gender.MALE, 31, "Wolves Club", AgeGroup.ADULT));
        participants.add(new Participant("Mia", Gender.FEMALE, 22, "Tigers Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Lucas", Gender.MALE, 18, "Lions Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Charlotte", Gender.FEMALE, 15, "Eagles Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Mason", Gender.MALE, 14, "Bears Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Amelia", Gender.FEMALE, 13, "Hawks Club", AgeGroup.JUNIOR));
        participants.add(new Participant("Ethan", Gender.MALE, 6, "Lions Club", AgeGroup.CHILD));
        participants.add(new Participant("Harper", Gender.FEMALE, 30, "Wolves Club", AgeGroup.ADULT));
        participants.add(new Participant("Alexander", Gender.MALE, 25, "Tigers Club", AgeGroup.ADULT));
        participants.add(new Participant("Evelyn", Gender.FEMALE, 23, "Bears Club", AgeGroup.ADULT));
        participants.add(new Participant("Jacob", Gender.MALE, 28, "Eagles Club", AgeGroup.ADULT));
        participants.add(new Participant("Abigail", Gender.FEMALE, 27, "Hawks Club", AgeGroup.ADULT));
        participants.add(new Participant("Michael", Gender.MALE, 8, "Tigers Club", AgeGroup.CHILD));
        participants.add(new Participant("Ella", Gender.FEMALE, 9, "Wolves Club", AgeGroup.CHILD));
        participants.add(new Participant("Daniel", Gender.MALE, 10, "Lions Club", AgeGroup.YOUTH));
        participants.add(new Participant("Scarlett", Gender.FEMALE, 29, "Hawks Club", AgeGroup.ADULT));
        participants.add(new Participant("Henry", Gender.MALE, 25, "Bears Club", AgeGroup.ADULT));
        participants.add(new Participant("Grace", Gender.FEMALE, 22, "Eagles Club", AgeGroup.ADULT));
        participants.add(new Participant("Jackson", Gender.MALE, 31, "Wolves Club", AgeGroup.ADULT));
        participants.add(new Participant("Zoe", Gender.FEMALE, 23, "Tigers Club", AgeGroup.ADULT));
        participants.add(new Participant("Charlie", Gender.MALE, 6, "Kittens Club", AgeGroup.CHILD));
        participants.add(new Participant("Lucy", Gender.FEMALE, 7, "Puppies Club", AgeGroup.CHILD));
        participants.add(new Participant("Oscar", Gender.MALE, 8, "Kittens Club", AgeGroup.CHILD));
        participants.add(new Participant("Lily", Gender.FEMALE, 9, "Puppies Club", AgeGroup.CHILD));
        participants.add(new Participant("George", Gender.MALE, 10, "Kittens Club", AgeGroup.YOUTH));
        participants.add(new Participant("Hannah", Gender.FEMALE, 11, "Puppies Club", AgeGroup.YOUTH));
        participants.add(new Participant("Jack", Gender.MALE, 12, "Kittens Club", AgeGroup.YOUTH));
        participants.add(new Participant("Sophie", Gender.FEMALE, 13, "Puppies Club", AgeGroup.YOUTH));
        participants.add(new Participant("Ryan", Gender.MALE, 40, "Old Guards Club", AgeGroup.SENIOR));
        participants.add(new Participant("Emily", Gender.FEMALE, 42, "Old Guards Club", AgeGroup.SENIOR));
        participants.add(new Participant("Ethan", Gender.MALE, 44, "Old Guards Club", AgeGroup.SENIOR));


        participantRepository.saveAll(participants);
    }

    private void createDisciplines() {

        disciplines.add(new Discipline("100m Sprint", ResultType.TIME));
        disciplines.add(new Discipline("200m Sprint", ResultType.TIME));
        disciplines.add(new Discipline("400m Sprint", ResultType.TIME));
        disciplines.add(new Discipline("Long Jump", ResultType.DISTANCE));
        disciplines.add(new Discipline("Triple Jump", ResultType.DISTANCE));
        disciplines.add(new Discipline("Shot Put", ResultType.DISTANCE));
        disciplines.add(new Discipline("Decathlon", ResultType.POINTS));
        disciplines.add(new Discipline("Heptathlon", ResultType.POINTS));
        disciplines.add(new Discipline("Pentathlon", ResultType.POINTS));

        disciplineRepository.saveAll(disciplines);
    }

    private void assignDisciplinesToParticipants() {
        participants.get(0)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(0)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(1)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(1)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(2)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(2)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(3)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(3)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(4)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(4)
                .getDisciplines()
                .add(disciplines.get(0));

        participants.get(5)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(5)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(6)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(6)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(7)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(7)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(8)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(8)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(9)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(9)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(10)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(10)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(11)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(11)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(12)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(12)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(13)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(13)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(14)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(14)
                .getDisciplines()
                .add(disciplines.get(2));

        participants.get(15)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(15)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(16)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(16)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(17)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(17)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(18)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(18)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(19)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(19)
                .getDisciplines()
                .add(disciplines.get(3));

        participants.get(20)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(20)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(21)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(21)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(22)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(22)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(23)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(23)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(24)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(24)
                .getDisciplines()
                .add(disciplines.get(4));

        participants.get(25)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(25)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(26)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(26)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(27)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(27)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(28)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(28)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(29)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(29)
                .getDisciplines()
                .add(disciplines.get(5));

        participants.get(30)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(30)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(31)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(31)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(32)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(32)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(33)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(33)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(34)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(34)
                .getDisciplines()
                .add(disciplines.get(6));

        participants.get(35)
                .getDisciplines()
                .add(disciplines.get(7));
        participants.get(35)
                .getDisciplines()
                .add(disciplines.get(8));
        participants.get(36)
                .getDisciplines()
                .add(disciplines.get(0));
        participants.get(36)
                .getDisciplines()
                .add(disciplines.get(1));
        participants.get(37)
                .getDisciplines()
                .add(disciplines.get(2));
        participants.get(37)
                .getDisciplines()
                .add(disciplines.get(3));
        participants.get(38)
                .getDisciplines()
                .add(disciplines.get(4));
        participants.get(38)
                .getDisciplines()
                .add(disciplines.get(5));
        participants.get(39)
                .getDisciplines()
                .add(disciplines.get(6));
        participants.get(39)
                .getDisciplines()
                .add(disciplines.get(7));

        participantRepository.saveAll(participants);

    }

    private void assignResultsToParticipants() {
        results.add(new Result(ResultType.TIME, "10.500", LocalDateTime.of(2024, 1, 1, 0, 0), participants.get(0), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "1200", LocalDateTime.of(2024, 2, 5, 0, 0), participants.get(1), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "25", LocalDateTime.of(2024, 3, 10, 0, 0), participants.get(3), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "20.500", LocalDateTime.of(2024, 4, 15, 0, 0), participants.get(0), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "1500", LocalDateTime.of(2024, 5, 20, 0, 0), participants.get(2), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "30", LocalDateTime.of(2024, 6, 25, 0, 0), participants.get(3), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "30.500", LocalDateTime.of(2024, 7, 30, 0, 0), participants.get(1), disciplines.get(2)));
        results.add(new Result(ResultType.DISTANCE, "1800", LocalDateTime.of(2024, 8, 5, 0, 0), participants.get(2), disciplines.get(4)));
        results.add(new Result(ResultType.POINTS, "35", LocalDateTime.of(2024, 9, 10, 0, 0), participants.get(3), disciplines.get(8)));
        results.add(new Result(ResultType.TIME, "40.500", LocalDateTime.of(2024, 10, 15, 0, 0), participants.get(0), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "2000", LocalDateTime.of(2024, 11, 20, 0, 0), participants.get(6), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "40", LocalDateTime.of(2024, 12, 25, 0, 0), participants.get(3), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "50.500", LocalDateTime.of(2025, 1, 1, 0, 0), participants.get(5), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "2500", LocalDateTime.of(2025, 2, 5, 0, 0), participants.get(7), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "45", LocalDateTime.of(2025, 3, 10, 0, 0), participants.get(3), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "60.500", LocalDateTime.of(2025, 4, 15, 0, 0), participants.get(1), disciplines.get(2)));
        results.add(new Result(ResultType.POINTS, "10", LocalDateTime.of(2025, 5, 20, 0, 0), participants.get(3), disciplines.get(6)));
        results.add(new Result(ResultType.POINTS, "50", LocalDateTime.of(2025, 6, 25, 0, 0), participants.get(3), disciplines.get(8)));
        results.add(new Result(ResultType.TIME, "70.500", LocalDateTime.of(2025, 7, 30, 0, 0), participants.get(0), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "3500", LocalDateTime.of(2025, 8, 5, 0, 0), participants.get(6), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "55", LocalDateTime.of(2025, 9, 10, 0, 0), participants.get(3), disciplines.get(7)));

        results.add(new Result(ResultType.TIME, "15.200", LocalDateTime.of(2024, 1, 10, 0, 0), participants.get(4), disciplines.get(8)));
        results.add(new Result(ResultType.DISTANCE, "1300", LocalDateTime.of(2024, 2, 20, 0, 0), participants.get(5), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "20", LocalDateTime.of(2024, 3, 15, 0, 0), participants.get(6), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "25.300", LocalDateTime.of(2024, 4, 18, 0, 0), participants.get(7), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "1100", LocalDateTime.of(2024, 5, 22, 0, 0), participants.get(8), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "45", LocalDateTime.of(2024, 6, 25, 0, 0), participants.get(9), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "35.400", LocalDateTime.of(2024, 7, 28, 0, 0), participants.get(10), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "1700", LocalDateTime.of(2024, 8, 30, 0, 0), participants.get(11), disciplines.get(4)));
        results.add(new Result(ResultType.POINTS, "50", LocalDateTime.of(2024, 9, 10, 0, 0), participants.get(12), disciplines.get(8)));
        results.add(new Result(ResultType.TIME, "45.600", LocalDateTime.of(2024, 10, 12, 0, 0), participants.get(13), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "2200", LocalDateTime.of(2024, 11, 14, 0, 0), participants.get(14), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "55", LocalDateTime.of(2024, 12, 16, 0, 0), participants.get(15), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "55.700", LocalDateTime.of(2025, 1, 18, 0, 0), participants.get(16), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "2400", LocalDateTime.of(2025, 2, 20, 0, 0), participants.get(17), disciplines.get(4)));
        results.add(new Result(ResultType.POINTS, "60", LocalDateTime.of(2025, 3, 22, 0, 0), participants.get(18), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "65.800", LocalDateTime.of(2025, 4, 24, 0, 0), participants.get(19), disciplines.get(2)));
        results.add(new Result(ResultType.POINTS, "15", LocalDateTime.of(2025, 5, 26, 0, 0), participants.get(20), disciplines.get(6)));
        results.add(new Result(ResultType.POINTS, "65", LocalDateTime.of(2025, 6, 28, 0, 0), participants.get(21), disciplines.get(8)));
        results.add(new Result(ResultType.TIME, "75.900", LocalDateTime.of(2025, 7, 30, 0, 0), participants.get(22), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "3600", LocalDateTime.of(2025, 8, 5, 0, 0), participants.get(23), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "70", LocalDateTime.of(2025, 9, 10, 0, 0), participants.get(24), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "85.100", LocalDateTime.of(2025, 10, 12, 0, 0), participants.get(25), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "3700", LocalDateTime.of(2025, 11, 14, 0, 0), participants.get(26), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "75", LocalDateTime.of(2025, 12, 16, 0, 0), participants.get(27), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "95.200", LocalDateTime.of(2026, 1, 18, 0, 0), participants.get(28), disciplines.get(2)));
        results.add(new Result(ResultType.POINTS, "20", LocalDateTime.of(2026, 2, 20, 0, 0), participants.get(29), disciplines.get(6)));
        results.add(new Result(ResultType.POINTS, "80", LocalDateTime.of(2026, 3, 22, 0, 0), participants.get(30), disciplines.get(8)));
        results.add(new Result(ResultType.TIME, "105.300", LocalDateTime.of(2026, 4, 24, 0, 0), participants.get(31), disciplines.get(0)));
        results.add(new Result(ResultType.DISTANCE, "3800", LocalDateTime.of(2026, 5, 26, 0, 0), participants.get(32), disciplines.get(3)));
        results.add(new Result(ResultType.POINTS, "85", LocalDateTime.of(2026, 6, 28, 0, 0), participants.get(33), disciplines.get(7)));
        results.add(new Result(ResultType.TIME, "115.400", LocalDateTime.of(2026, 7, 30, 0, 0), participants.get(34), disciplines.get(1)));
        results.add(new Result(ResultType.DISTANCE, "3900", LocalDateTime.of(2026, 8, 5, 0, 0), participants.get(35), disciplines.get(5)));
        results.add(new Result(ResultType.POINTS, "90", LocalDateTime.of(2026, 9, 10, 0, 0), participants.get(36), disciplines.get(6)));
        results.add(new Result(ResultType.TIME, "125.500", LocalDateTime.of(2026, 10, 12, 0, 0), participants.get(37), disciplines.get(2)));
        results.add(new Result(ResultType.POINTS, "25", LocalDateTime.of(2026, 11, 14, 0, 0), participants.get(38), disciplines.get(6)));
        results.add(new Result(ResultType.POINTS, "95", LocalDateTime.of(2026, 12, 16, 0, 0), participants.get(39), disciplines.get(8)));


        resultRepository.saveAll(results);
    }
}
