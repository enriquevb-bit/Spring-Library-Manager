package enriquevb.biblioteca.services;

import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.mappers.BookMapper;
import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BookServiceJPA implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<BookDTO> listBooks(String title, String isbn, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Book> bookPage;

        if (StringUtils.hasText(title) && !StringUtils.hasText(isbn)) {
            bookPage = listBooksByTitle(title, pageRequest);
        } else if (!StringUtils.hasText(title) && StringUtils.hasText(isbn)) {
            bookPage = listBooksByIsbn(isbn, pageRequest);
        } else if (StringUtils.hasText(title) && StringUtils.hasText(isbn)) {
            bookPage = listBooksByTitleAndIsbn(title, isbn, pageRequest);
        } else {
            bookPage = bookRepository.findAll(pageRequest);
        }

        return bookPage.map(bookMapper::bookToBookDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("title"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private Page<Book> listBooksByTitleAndIsbn(String title, String isbn, Pageable pageable) {
        return bookRepository.findAllByTitleIsLikeIgnoreCaseAndIsbn("%" + title + "%", isbn, pageable);
    }

    public Page<Book> listBooksByIsbn(String isbn, Pageable pageable) {
        return bookRepository.findAllByIsbn(isbn, pageable);
    }

    public Page<Book> listBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findAllByTitleIsLikeIgnoreCase("%" + title + "%", pageable);
    }

    @Override
    public Optional<BookDTO> getBookById(UUID id) {
        return Optional.ofNullable(bookMapper.bookToBookDto(bookRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BookDTO saveNewBook(BookDTO book) {
        return bookMapper.bookToBookDto(bookRepository.save(bookMapper.bookDtoToBook(book)));
    }

    @Override
    public Optional<BookDTO> updateBookById(UUID bookId, BookDTO book) {
        AtomicReference<Optional<BookDTO>> atomicReference = new AtomicReference<>();

        bookRepository.findById(bookId).ifPresentOrElse(foundBook -> {
            foundBook.setIsbn(book.getIsbn());
            foundBook.setPrice(book.getPrice());
            foundBook.setTitle(book.getTitle());
            foundBook.setAvailableCopies(book.getAvailableCopies());
            foundBook.setPublishedDate(book.getPublishedDate());
            atomicReference.set(Optional.of(bookMapper
                    .bookToBookDto(bookRepository.save(foundBook))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BookDTO> patchBookById(UUID bookId, BookDTO book) {
        AtomicReference<Optional<BookDTO>> atomicReference = new AtomicReference<>();

        bookRepository.findById(bookId).ifPresentOrElse(foundBook -> {
            if (book.getPrice() != null) {
                foundBook.setPrice(book.getPrice());
            }
            if (StringUtils.hasText(book.getIsbn())) {
                foundBook.setIsbn(book.getIsbn());
            }
            if (StringUtils.hasText(book.getTitle())) {
                foundBook.setTitle(book.getTitle());
            }
            if (book.getAvailableCopies() != null) {
                foundBook.setAvailableCopies(book.getAvailableCopies());
            }
            if (book.getPublishedDate() != null) {
                foundBook.setPublishedDate(book.getPublishedDate());
            }
            atomicReference.set(Optional.of(bookMapper
                    .bookToBookDto(bookRepository.save(foundBook))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
