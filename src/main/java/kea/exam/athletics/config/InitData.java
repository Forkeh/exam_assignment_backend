package kea.exam.athletics.config;

import kea.exam.athletics.book.Book;
import kea.exam.athletics.book.BookRepository;
import kea.exam.athletics.discipline.Discipline;
import kea.exam.athletics.discipline.DisciplineRepository;
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

    private final BookRepository bookRepository;
    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public InitData(BookRepository bookRepository, ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.bookRepository = bookRepository;
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Initializing data...");

        init();
        createParticipants();
        createDisciplines();
        assignDisciplinesToParticipants();
        assignResultsToParticipants();
    }


    private void init() {
        System.out.println("Creating items in database...");

        List<Book> books = List.of(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925),
                new Book("To Kill a Mockingbird", "Harper Lee", 1960),
                new Book("1984", "George Orwell", 1949),
                new Book("Pride and Prejudice", "Jane Austen", 1813),
                new Book("The Catcher in the Rye", "J.D. Salinger", 1951),
                new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954),
                new Book("Animal Farm", "George Orwell", 1945),
                new Book("The Hobbit", "J.R.R. Tolkien", 1937),
                new Book("The Little Prince", "Antoine de Saint-Exupéry", 1943),
                new Book("The Da Vinci Code", "Dan Brown", 2003)
        );
        bookRepository.saveAll(books);

    }


    private void createParticipants() {

        participants.add(new Participant("John", Gender.MALE, 25, "Lions Club"));
        participants.add(new Participant("Emma", Gender.FEMALE, 22, "Tigers Club"));
        participants.add(new Participant("Liam", Gender.MALE, 30, "Lions Club"));
        participants.add(new Participant("Olivia", Gender.FEMALE, 27, "Bears Club"));
        participants.add(new Participant("Noah", Gender.MALE, 24, "Wolves Club"));
        participants.add(new Participant("Ava", Gender.FEMALE, 29, "Tigers Club"));
        participants.add(new Participant("William", Gender.MALE, 28, "Hawks Club"));
        participants.add(new Participant("Sophia", Gender.FEMALE, 26, "Bears Club"));
        participants.add(new Participant("James", Gender.MALE, 23, "Eagles Club"));
        participants.add(new Participant("Isabella", Gender.FEMALE, 25, "Hawks Club"));
        participants.add(new Participant("Benjamin", Gender.MALE, 31, "Wolves Club"));
        participants.add(new Participant("Mia", Gender.FEMALE, 22, "Tigers Club"));
        participants.add(new Participant("Lucas", Gender.MALE, 27, "Lions Club"));
        participants.add(new Participant("Charlotte", Gender.FEMALE, 28, "Eagles Club"));
        participants.add(new Participant("Mason", Gender.MALE, 26, "Bears Club"));
        participants.add(new Participant("Amelia", Gender.FEMALE, 24, "Hawks Club"));
        participants.add(new Participant("Ethan", Gender.MALE, 29, "Lions Club"));
        participants.add(new Participant("Harper", Gender.FEMALE, 30, "Wolves Club"));
        participants.add(new Participant("Alexander", Gender.MALE, 25, "Tigers Club"));
        participants.add(new Participant("Evelyn", Gender.FEMALE, 23, "Bears Club"));
        participants.add(new Participant("Jacob", Gender.MALE, 28, "Eagles Club"));
        participants.add(new Participant("Abigail", Gender.FEMALE, 27, "Hawks Club"));
        participants.add(new Participant("Michael", Gender.MALE, 26, "Tigers Club"));
        participants.add(new Participant("Ella", Gender.FEMALE, 24, "Wolves Club"));
        participants.add(new Participant("Daniel", Gender.MALE, 30, "Lions Club"));
        participants.add(new Participant("Scarlett", Gender.FEMALE, 29, "Hawks Club"));
        participants.add(new Participant("Henry", Gender.MALE, 25, "Bears Club"));
        participants.add(new Participant("Grace", Gender.FEMALE, 22, "Eagles Club"));
        participants.add(new Participant("Jackson", Gender.MALE, 31, "Wolves Club"));
        participants.add(new Participant("Zoe", Gender.FEMALE, 23, "Tigers Club"));
        participants.add(new Participant("Charlie", Gender.MALE, 6, "Kittens Club"));
        participants.add(new Participant("Lucy", Gender.FEMALE, 7, "Puppies Club"));
        participants.add(new Participant("Oscar", Gender.MALE, 8, "Kittens Club"));
        participants.add(new Participant("Lily", Gender.FEMALE, 9, "Puppies Club"));
        participants.add(new Participant("George", Gender.MALE, 10, "Kittens Club"));
        participants.add(new Participant("Hannah", Gender.FEMALE, 11, "Puppies Club"));
        participants.add(new Participant("Jack", Gender.MALE, 12, "Kittens Club"));
        participants.add(new Participant("Sophie", Gender.FEMALE, 13, "Puppies Club"));
        participants.add(new Participant("Ryan", Gender.MALE, 40, "Old Guards Club"));
        participants.add(new Participant("Emily", Gender.FEMALE, 42, "Old Guards Club"));
        participants.add(new Participant("Ethan", Gender.MALE, 44, "Old Guards Club"));


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

        resultRepository.saveAll(results);
    }
}
