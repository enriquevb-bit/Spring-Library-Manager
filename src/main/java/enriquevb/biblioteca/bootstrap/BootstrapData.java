package enriquevb.biblioteca.bootstrap;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.models.MemberState;
import enriquevb.biblioteca.repositories.AuthorRepository;
import enriquevb.biblioteca.repositories.BookRepository;
import enriquevb.biblioteca.repositories.MemberRepository;
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

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBookData();
        loadAuthorData();
        loadMemberData();
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
