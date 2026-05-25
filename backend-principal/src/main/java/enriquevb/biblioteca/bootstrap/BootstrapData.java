package enriquevb.biblioteca.bootstrap;

import enriquevb.biblioteca.entities.*;
import enriquevb.biblioteca.models.*;
import enriquevb.biblioteca.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final GenreRepository genreRepository;
    private final LoanRepository loanRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<Author> authors = loadAuthorData();
        List<Genre> genres = loadGenreData();
        List<Book> books = loadBookData(authors, genres);
        List<Member> members = loadMemberData();
        loadLoanData(members, books);
    }

    private List<Author> loadAuthorData() {
        if (authorRepository.count() > 0) {
            return authorRepository.findAll();
        }

        Author author1 = Author.builder()
                .fullName("John Ronald Reuel Tolkien")
                .birthDate(LocalDate.of(1892, 1, 3))
                .country("British")
                .build();

        Author author2 = Author.builder()
                .fullName("George Raymond Richard Martin")
                .birthDate(LocalDate.of(1948, 9, 20))
                .country("American")
                .build();

        Author author3 = Author.builder()
                .fullName("Suzanne Collins")
                .birthDate(LocalDate.of(1962, 8, 10))
                .country("American")
                .build();

        return List.of(
                authorRepository.save(author1),
                authorRepository.save(author2),
                authorRepository.save(author3)
        );
    }

    private List<Genre> loadGenreData() {
        if (genreRepository.count() > 0) {
            return genreRepository.findAll();
        }

        Genre genre1 = Genre.builder()
                .name("Fantasy")
                .description("Worlds of magic, mythical creatures, and epic quests")
                .build();

        Genre genre2 = Genre.builder()
                .name("Science Fiction")
                .description("Futuristic technology, space exploration, and speculative science")
                .build();

        Genre genre3 = Genre.builder()
                .name("Dystopian")
                .description("Oppressive societies, survival, and rebellion against authoritarian regimes")
                .build();

        return List.of(
                genreRepository.save(genre1),
                genreRepository.save(genre2),
                genreRepository.save(genre3)
        );
    }

    private List<Member> loadMemberData() {
        if (memberRepository.count() > 0) {
            return memberRepository.findAll();
        }

        Member member1 = Member.builder()
                .name("Juan Pérez")
                .email("miembro@gmail.com")
                .memberState(MemberState.ACTIVE)
                .build();

        Member member2 = Member.builder()
                .name("María García")
                .email("maria.garcia@test.com")
                .memberState(MemberState.PENDING)
                .build();

        Member member3 = Member.builder()
                .name("Carlos López")
                .email("carlos.lopez@demo.com")
                .memberState(MemberState.INACTIVE)
                .build();

        return List.of(
                memberRepository.save(member1),
                memberRepository.save(member2),
                memberRepository.save(member3)
        );
    }

    private List<Book> loadBookData(List<Author> authors, List<Genre> genres) {
        if (bookRepository.count() > 0) {
            return bookRepository.findAll();
        }

        Book book1 = Book.builder()
                .price(new BigDecimal("11.99"))
                .title("The Fellowship of the Ring")
                .availableCopies(5)
                .publishedDate(LocalDate.of(1954, 7, 29))
                .isbn("978-0132350884")
                .build();
        book1.getAuthors().add(authors.get(0));
        book1.getGenres().add(genres.get(0));

        Book book2 = Book.builder()
                .price(new BigDecimal("14.99"))
                .title("A Song Of Ice and Fire")
                .availableCopies(7)
                .publishedDate(LocalDate.of(1996, 8, 1))
                .isbn("978-1617292545")
                .build();
        book2.getAuthors().add(authors.get(1));
        book2.getGenres().add(genres.get(0));

        Book book3 = Book.builder()
                .price(new BigDecimal("12.99"))
                .title("The Hunger Games")
                .availableCopies(3)
                .publishedDate(LocalDate.of(2008, 9, 14))
                .isbn("978-0321125217")
                .build();
        book3.getAuthors().add(authors.get(2));
        book3.getGenres().add(genres.get(1));
        book3.getGenres().add(genres.get(2));

        return List.of(
                bookRepository.save(book1),
                bookRepository.save(book2),
                bookRepository.save(book3)
        );
    }

    private void loadLoanData(List<Member> members, List<Book> books) {
        if (loanRepository.count() > 0) {
            return;
        }

        Member activeMember = members.stream()
                .filter(m -> m.getMemberState() == MemberState.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Bootstrap requires at least one ACTIVE member"));

        LocalDateTime now = LocalDateTime.now();

        Loan loan1 = Loan.builder()
                .loanState(LoanState.ACTIVE)
                .loanDate(now.minusDays(5))
                .expiringDate(now.plusDays(25))
                .build();
        loan1.setLoanLines(new HashSet<>());
        loan1.setMember(activeMember);
        LoanLine line1 = LoanLine.builder()
                .orderedQuantity(1)
                .book(books.get(0))
                .loan(loan1)
                .build();
        loan1.getLoanLines().add(line1);
        Book book1 = books.get(0);
        book1.setAvailableCopies(book1.getAvailableCopies() - line1.getOrderedQuantity());
        bookRepository.save(book1);

        Loan loan2 = Loan.builder()
                .loanState(LoanState.RETURNED)
                .loanDate(now.minusDays(30))
                .expiringDate(now.minusDays(1))
                .dueDate(now.minusDays(3))
                .build();
        loan2.setLoanLines(new HashSet<>());
        loan2.setMember(activeMember);
        LoanLine line2 = LoanLine.builder()
                .orderedQuantity(2)
                .returnedQuantity(2)
                .book(books.get(1))
                .loan(loan2)
                .build();
        loan2.getLoanLines().add(line2);

        Loan loan3 = Loan.builder()
                .loanState(LoanState.OVERDUE)
                .loanDate(now.minusDays(45))
                .expiringDate(now.minusDays(15))
                .build();
        loan3.setLoanLines(new HashSet<>());
        loan3.setMember(activeMember);
        LoanLine line3 = LoanLine.builder()
                .orderedQuantity(1)
                .book(books.get(2))
                .loan(loan3)
                .build();
        loan3.getLoanLines().add(line3);
        Book book3 = books.get(2);
        book3.setAvailableCopies(book3.getAvailableCopies() - line3.getOrderedQuantity());
        bookRepository.save(book3);

        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);
    }

}
