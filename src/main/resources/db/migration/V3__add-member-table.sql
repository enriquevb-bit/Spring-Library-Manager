CREATE TABLE member (
                        id VARCHAR(36) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        phone VARCHAR(20),
                        address VARCHAR(500),
                        membership_date DATE NOT NULL,
                        member_status VARCHAR(50) DEFAULT 'ACTIVE',
                        created_date DATETIME(6),
                        update_date DATETIME(6),
                        version INTEGER DEFAULT 0,
                        PRIMARY KEY (id),
                        UNIQUE KEY uk_member_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;