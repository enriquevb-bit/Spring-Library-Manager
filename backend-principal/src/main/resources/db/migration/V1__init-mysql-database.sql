DROP TABLE IF EXISTS book_genre;
DROP TABLE IF EXISTS loan_item;
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS member;

CREATE TABLE author (
                        id VARCHAR(36) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        nationality VARCHAR(100),
                        birth_date DATE,
                        created_date DATETIME(6),
                        update_date DATETIME(6),
                        version INTEGER DEFAULT 0,
                        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE genre (
                       id VARCHAR(36) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(500),
                       created_date DATETIME(6),
                       update_date DATETIME(6),
                       PRIMARY KEY (id),
                       UNIQUE KEY uk_genre_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE book (
                      id VARCHAR(36) NOT NULL,
                      isbn VARCHAR(20) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      publication_year INTEGER,
                      copies_available INTEGER NOT NULL DEFAULT 0,
                      price DECIMAL(10,2),
                      book_status VARCHAR(50) DEFAULT 'AVAILABLE',
                      author_id VARCHAR(36),
                      created_date DATETIME(6),
                      update_date DATETIME(6),
                      version INTEGER DEFAULT 0,
                      PRIMARY KEY (id),
                      UNIQUE KEY uk_book_isbn (isbn),
                      CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;