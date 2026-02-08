package enriquevb.biblioteca.bootstrap;

import enriquevb.biblioteca.entities.*;
import enriquevb.biblioteca.models.*;
import enriquevb.biblioteca.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
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
        loadBookData();
        loadAuthorData();
        loadMemberData();
        loadGenreData();
        loadLoanData();
    }

    private void loadLoanData() {
        if (loanRepository.count() == 0) {
            Loan loan1 = Loan.builder()
                    .loanState(LoanState.ACTIVE)
                    .loanDate(LocalDateTime.now().minusDays(5))
                    .expiringDate(LocalDateTime.now().plusDays(25))
                    .build();

            Loan loan2 = Loan.builder()
                    .loanState(LoanState.RETURNED)
                    .loanDate(LocalDateTime.now().minusDays(30))
                    .expiringDate(LocalDateTime.now().minusDays(1))
                    .dueDate(LocalDateTime.now().minusDays(3))
                    .build();

            Loan loan3 = Loan.builder()
                    .loanState(LoanState.OVERDUE)
                    .loanDate(LocalDateTime.now().minusDays(45))
                    .expiringDate(LocalDateTime.now().minusDays(15))
                    .build();

            loanRepository.save(loan1);
            loanRepository.save(loan2);
            loanRepository.save(loan3);
        }
    }

    private void loadGenreData() {
        if ( genreRepository.count() == 0){
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

            genreRepository.save(genre1);
            genreRepository.save(genre2);
            genreRepository.save(genre3);
        }
    }

    private void loadMemberData() {
        if (memberRepository.count() == 0){
            Member member1 = Member.builder()
                    .name("Juan Pérez")
                    .email("juan.perez@example.com")
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

            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
        }
    }

    private void loadAuthorData() {
        if (authorRepository.count() == 0){
            Author author1 = Author.builder()
                    .fullName("John Ronald Reuel Tolkien")
                    .birthDate(LocalDate.of(1892,1,3))
                    .nationality("British")
                    .build();

            Author author2 = Author.builder()
                    .fullName("George Raymond Richard Martin")
                    .birthDate(LocalDate.of(1948,9,20))
                    .nationality("American")
                    .build();

            Author author3 = Author.builder()
                    .fullName("Suzanne Collins")
                    .birthDate(LocalDate.of(1962,8,10))
                    .nationality("American")
                    .build();

            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);
        }
    }

    private void loadBookData() {
        if (bookRepository.count() == 0){
            Book book1 = Book.builder()
                    .price(new BigDecimal(11.99))
                    .title("The Fellowship of the Ring")
                    .availableCopies(5)
                    .publishedDate(LocalDate.of(1954, 7, 29))
                    .isbn("978-0132350884")
                    .build();

            Book book2 = Book.builder()
                    .price(new BigDecimal(14.99))
                    .title("A Song Of Ice and Fire")
                    .availableCopies(7)
                    .publishedDate(LocalDate.of(1996,8,1))
                    .isbn("978-1617292545")
                    .build();

            Book book3 = Book.builder()
                    .price(new BigDecimal(12.99))
                    .title("The Hunger Games")
                    .availableCopies(3)
                    .publishedDate(LocalDate.of(2008,9,14))
                    .isbn("978-0321125217")
                    .build();

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
        }

    }

}
