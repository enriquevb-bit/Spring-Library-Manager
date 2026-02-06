CREATE TABLE book_genre (
                            book_id VARCHAR(36) NOT NULL,
                            genre_id VARCHAR(36) NOT NULL,
                            PRIMARY KEY (book_id, genre_id),
                            CONSTRAINT fk_book_genre_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                            CONSTRAINT fk_book_genre_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;