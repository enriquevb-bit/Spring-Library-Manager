package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    Map<UUID, BookDTO> bookMap;

    public BookServiceImpl() {
        this.bookMap = new HashMap<>();

        BookDTO book1 = BookDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .price(new BigDecimal("11.99"))
                .title("The Fellowship of the Ring")
                .availableCopies(5)
                .lastModifiedDate(LocalDateTime.now())
                .publishedDate(LocalDate.of(1954, 7, 29))
                .isbn("978-0132350884")
                .build();

        BookDTO book2 = BookDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .price(new BigDecimal("14.99"))
                .title("A Song Of Ice and Fire")
                .availableCopies(7)
                .lastModifiedDate(LocalDateTime.now())
                .publishedDate(LocalDate.of(1996, 8, 1))
                .isbn("978-1617292545")
                .build();

        BookDTO book3 = BookDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .price(new BigDecimal("12.99"))
                .title("The Hunger Games")
                .availableCopies(3)
                .lastModifiedDate(LocalDateTime.now())
                .publishedDate(LocalDate.of(2008, 9, 14))
                .isbn("978-0321125217")
                .build();

        bookMap.put(book1.getId(), book1);
        bookMap.put(book2.getId(), book2);
        bookMap.put(book3.getId(), book3);
    }

    @Override
    public Page<BookDTO> listBooks(String title, String isbn, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(bookMap.values()));
    }

    @Override
    public Optional<BookDTO> getBookById(UUID id) {
        return Optional.ofNullable(bookMap.get(id));
    }

    @Override
    public BookDTO saveNewBook(BookDTO book) {
        BookDTO savedBook = BookDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .availableCopies(book.getAvailableCopies())
                .publishedDate(book.getPublishedDate())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        bookMap.put(savedBook.getId(), savedBook);
        return savedBook;
    }

    @Override
    public Optional<BookDTO> updateBookById(UUID bookId, BookDTO book) {
        BookDTO existing = bookMap.get(bookId);

        if (existing != null) {
            existing.setTitle(book.getTitle());
            existing.setPrice(book.getPrice());
            existing.setAvailableCopies(book.getAvailableCopies());
            existing.setIsbn(book.getIsbn());
            existing.setPublishedDate(book.getPublishedDate());
            existing.setLastModifiedDate(LocalDateTime.now());
            return Optional.of(existing);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID bookId) {
        if (bookMap.containsKey(bookId)) {
            bookMap.remove(bookId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BookDTO> patchBookById(UUID bookId, BookDTO book) {
        BookDTO existing = bookMap.get(bookId);

        if (existing != null) {
            if (StringUtils.hasText(book.getTitle())) {
                existing.setTitle(book.getTitle());
            }
            if (StringUtils.hasText(book.getIsbn())) {
                existing.setIsbn(book.getIsbn());
            }
            if (book.getPrice() != null) {
                existing.setPrice(book.getPrice());
            }
            if (book.getAvailableCopies() != null) {
                existing.setAvailableCopies(book.getAvailableCopies());
            }
            if (book.getPublishedDate() != null) {
                existing.setPublishedDate(book.getPublishedDate());
            }
            existing.setLastModifiedDate(LocalDateTime.now());
            return Optional.of(existing);
        }

        return Optional.empty();
    }
}
