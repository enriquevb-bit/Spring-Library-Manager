CREATE TABLE loan (
                      id VARCHAR(36) NOT NULL,
                      member_id VARCHAR(36) NOT NULL,
                      loan_date DATE NOT NULL,
                      due_date DATE NOT NULL,
                      return_date DATE,
                      loan_status VARCHAR(50) DEFAULT 'ACTIVE',
                      notes VARCHAR(500),
                      created_date DATETIME(6),
                      update_date DATETIME(6),
                      version INTEGER DEFAULT 0,
                      PRIMARY KEY (id),
                      CONSTRAINT fk_loan_member FOREIGN KEY (member_id) REFERENCES member(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE loan_item (
                           id VARCHAR(36) NOT NULL,
                           loan_id VARCHAR(36) NOT NULL,
                           book_id VARCHAR(36) NOT NULL,
                           quantity INTEGER NOT NULL DEFAULT 1,
                           created_date DATETIME(6),
                           update_date DATETIME(6),
                           PRIMARY KEY (id),
                           CONSTRAINT fk_loan_item_loan FOREIGN KEY (loan_id) REFERENCES loan(id) ON DELETE CASCADE,
                           CONSTRAINT fk_loan_item_book FOREIGN KEY (book_id) REFERENCES book(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;