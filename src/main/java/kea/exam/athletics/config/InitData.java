package kea.exam.athletics.config;

import kea.exam.athletics.book.Book;
import kea.exam.athletics.book.BookRepository;
import kea.exam.athletics.participant.Gender;
import kea.exam.athletics.participant.Participant;
import kea.exam.athletics.participant.ParticipantRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements ApplicationRunner {

    private final BookRepository bookRepository;

    private final ParticipantRepository participantRepository;

    public InitData(BookRepository bookRepository, ParticipantRepository participantRepository) {
        this.bookRepository = bookRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Initializing data...");

        init();
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

        List<Participant> participants = List.of(
                new Participant("John", Gender.MALE, 25, "Lions Club"),
                new Participant("Emma", Gender.FEMALE, 22, "Tigers Club"),
                new Participant("Liam", Gender.MALE, 30, "Lions Club"),
                new Participant("Olivia", Gender.FEMALE, 27, "Bears Club"),
                new Participant("Noah", Gender.MALE, 24, "Wolves Club"),
                new Participant("Ava", Gender.FEMALE, 29, "Tigers Club"),
                new Participant("William", Gender.MALE, 28, "Hawks Club"),
                new Participant("Sophia", Gender.FEMALE, 26, "Bears Club"),
                new Participant("James", Gender.MALE, 23, "Eagles Club"),
                new Participant("Isabella", Gender.FEMALE, 25, "Hawks Club"),
                new Participant("Benjamin", Gender.MALE, 31, "Wolves Club"),
                new Participant("Mia", Gender.FEMALE, 22, "Tigers Club"),
                new Participant("Lucas", Gender.MALE, 27, "Lions Club"),
                new Participant("Charlotte", Gender.FEMALE, 28, "Eagles Club"),
                new Participant("Mason", Gender.MALE, 26, "Bears Club"),
                new Participant("Amelia", Gender.FEMALE, 24, "Hawks Club"),
                new Participant("Ethan", Gender.MALE, 29, "Lions Club"),
                new Participant("Harper", Gender.FEMALE, 30, "Wolves Club"),
                new Participant("Alexander", Gender.MALE, 25, "Tigers Club"),
                new Participant("Evelyn", Gender.FEMALE, 23, "Bears Club"),
                new Participant("Jacob", Gender.MALE, 28, "Eagles Club"),
                new Participant("Abigail", Gender.FEMALE, 27, "Hawks Club"),
                new Participant("Michael", Gender.MALE, 26, "Tigers Club"),
                new Participant("Ella", Gender.FEMALE, 24, "Wolves Club"),
                new Participant("Daniel", Gender.MALE, 30, "Lions Club"),
                new Participant("Scarlett", Gender.FEMALE, 29, "Hawks Club"),
                new Participant("Henry", Gender.MALE, 25, "Bears Club"),
                new Participant("Grace", Gender.FEMALE, 22, "Eagles Club"),
                new Participant("Jackson", Gender.MALE, 31, "Wolves Club"),
                new Participant("Zoe", Gender.FEMALE, 23, "Tigers Club"),
                new Participant("Charlie", Gender.MALE, 6, "Kittens Club"),
                new Participant("Lucy", Gender.FEMALE, 7, "Puppies Club"),
                new Participant("Oscar", Gender.MALE, 8, "Kittens Club"),
                new Participant("Lily", Gender.FEMALE, 9, "Puppies Club"),
                new Participant("George", Gender.MALE, 10, "Kittens Club"),
                new Participant("Hannah", Gender.FEMALE, 11, "Puppies Club"),
                new Participant("Jack", Gender.MALE, 12, "Kittens Club"),
                new Participant("Sophie", Gender.FEMALE, 13, "Puppies Club"),
                new Participant("Ryan", Gender.MALE, 40, "Old Guards Club"),
                new Participant("Emily", Gender.FEMALE, 42, "Old Guards Club"),
                new Participant("Ethan", Gender.MALE, 44, "Old Guards Club"),
                new Participant("Ella", Gender.FEMALE, 46, "Old Guards Club"),
                new Participant("Daniel", Gender.MALE, 48, "Old Guards Club"),
                new Participant("Scarlett", Gender.FEMALE, 50, "Old Guards Club")
        );

        participantRepository.saveAll(participants);
    }
}
