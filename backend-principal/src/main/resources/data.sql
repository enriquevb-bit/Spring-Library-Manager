-- =====================================================
-- data.sql - Datos de prueba para Biblioteca
-- Copiar a: backend-principal/src/main/resources/data.sql
-- =====================================================

-- =====================================================
-- AUTORES (15)
-- =====================================================
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000001', 0, 'Miguel de Cervantes', 'Española', '1547-09-29');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000002', 0, 'J.R.R. Tolkien', 'Británica', '1892-01-03');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000003', 0, 'George Orwell', 'Británica', '1903-06-25');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000004', 0, 'Gabriel García Márquez', 'Colombiana', '1927-03-06');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000005', 0, 'Isaac Asimov', 'Estadounidense', '1920-01-02');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000006', 0, 'Frank Herbert', 'Estadounidense', '1920-10-08');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000007', 0, 'Ray Bradbury', 'Estadounidense', '1920-08-22');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000008', 0, 'Carlos Ruiz Zafón', 'Española', '1964-09-25');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000009', 0, 'Umberto Eco', 'Italiana', '1932-01-05');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000010', 0, 'Stephen King', 'Estadounidense', '1947-09-21');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000011', 0, 'Bram Stoker', 'Irlandesa', '1847-11-08');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000012', 0, 'Jane Austen', 'Británica', '1775-12-16');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000013', 0, 'Fiódor Dostoievski', 'Rusa', '1821-11-11');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000014', 0, 'Federico García Lorca', 'Española', '1898-06-05');
INSERT INTO author (id, version, full_name, nationality, birth_date) VALUES
('a0000001-0000-0000-0000-000000000015', 0, 'Julio Cortázar', 'Argentina', '1914-08-26');

-- =====================================================
-- GÉNEROS (6)
-- =====================================================
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000001', 0, 'Fantasía', 'Mundos imaginarios, magia y criaturas fantásticas');
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000002', 0, 'Ciencia Ficción', 'Futuros posibles, tecnología y exploración espacial');
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000003', 0, 'Novela', 'Narrativa extensa con tramas y personajes complejos');
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000004', 0, 'Terror', 'Historias de miedo, suspenso y lo sobrenatural');
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000005', 0, 'Historia', 'Obras basadas en hechos y épocas históricas');
INSERT INTO genre (id, version, name, description) VALUES
('g0000001-0000-0000-0000-000000000006', 0, 'Poesía', 'Expresión literaria en verso y prosa poética');

-- =====================================================
-- LIBROS (25) - Títulos máx. 30 caracteres
-- available_copies ya descuenta préstamos activos
-- =====================================================
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000001', 0, '978-84-376-0494-7', 'El Quijote', 5, 18.50, '1605-01-16', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000002', 0, '978-84-450-7023-3', 'El Hobbit', 2, 15.99, '1937-09-21', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000003', 0, '978-84-450-7461-3', 'El Señor de los Anillos', 2, 29.99, '1954-07-29', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000004', 0, '978-84-233-4648-8', '1984', 3, 12.50, '1949-06-08', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000005', 0, '978-84-233-4647-1', 'Rebelión en la granja', 3, 10.99, '1945-08-17', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000006', 0, '978-84-376-0495-4', 'Cien años de soledad', 4, 16.75, '1967-05-30', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000007', 0, '978-84-376-0496-1', 'El amor en tiempos de cólera', 3, 15.25, '1985-09-05', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000008', 0, '978-84-663-0025-0', 'Fundación', 2, 14.90, '1951-06-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000009', 0, '978-84-663-0026-7', 'Yo, robot', 3, 13.50, '1950-12-02', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000010', 0, '978-84-450-7930-4', 'Dune', 2, 22.00, '1965-08-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000011', 0, '978-84-663-0027-4', 'Fahrenheit 451', 4, 11.99, '1953-10-19', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000012', 0, '978-84-663-0028-1', 'Crónicas marcianas', 3, 12.75, '1950-05-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000013', 0, '978-84-08-16327-0', 'La sombra del viento', 3, 19.90, '2001-04-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000014', 0, '978-84-08-16328-7', 'El juego del ángel', 2, 18.50, '2008-04-17', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000015', 0, '978-84-264-1668-9', 'El nombre de la rosa', 2, 17.25, '1980-01-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000016', 0, '978-84-01-35332-9', 'It', 3, 24.99, '1986-09-15', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000017', 0, '978-84-01-35333-6', 'El resplandor', 1, 16.50, '1977-01-28', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000018', 0, '978-84-01-35334-3', 'Misery', 3, 14.99, '1987-06-08', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000019', 0, '978-84-376-0497-8', 'Drácula', 4, 13.25, '1897-05-26', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000020', 0, '978-84-376-0498-5', 'Orgullo y prejuicio', 3, 12.99, '1813-01-28', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000021', 0, '978-84-376-0499-2', 'Emma', 1, 11.50, '1815-12-23', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000022', 0, '978-84-264-1669-6', 'Crimen y castigo', 3, 14.50, '1866-01-01', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000023', 0, '978-84-376-0500-5', 'La casa de Bernarda Alba', 2, 9.99, '1945-03-08', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000024', 0, '978-84-204-8183-7', 'Rayuela', 2, 16.00, '1963-06-28', '2026-01-15 10:00:00');
INSERT INTO book (id, version, isbn, title, available_copies, price, published_date, last_modified_date) VALUES
('b0000001-0000-0000-0000-000000000025', 0, '978-84-204-8184-4', 'Bestiario', 3, 11.25, '1951-01-01', '2026-01-15 10:00:00');

-- =====================================================
-- RELACIONES AUTOR-LIBRO
-- =====================================================
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000001', 'a0000001-0000-0000-0000-000000000001');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000002', 'a0000001-0000-0000-0000-000000000002');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000003', 'a0000001-0000-0000-0000-000000000002');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000004', 'a0000001-0000-0000-0000-000000000003');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000005', 'a0000001-0000-0000-0000-000000000003');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000006', 'a0000001-0000-0000-0000-000000000004');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000007', 'a0000001-0000-0000-0000-000000000004');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000008', 'a0000001-0000-0000-0000-000000000005');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000009', 'a0000001-0000-0000-0000-000000000005');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000010', 'a0000001-0000-0000-0000-000000000006');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000011', 'a0000001-0000-0000-0000-000000000007');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000012', 'a0000001-0000-0000-0000-000000000007');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000013', 'a0000001-0000-0000-0000-000000000008');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000014', 'a0000001-0000-0000-0000-000000000008');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000015', 'a0000001-0000-0000-0000-000000000009');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000016', 'a0000001-0000-0000-0000-000000000010');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000017', 'a0000001-0000-0000-0000-000000000010');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000018', 'a0000001-0000-0000-0000-000000000010');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000019', 'a0000001-0000-0000-0000-000000000011');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000020', 'a0000001-0000-0000-0000-000000000012');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000021', 'a0000001-0000-0000-0000-000000000012');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000022', 'a0000001-0000-0000-0000-000000000013');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000023', 'a0000001-0000-0000-0000-000000000014');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000024', 'a0000001-0000-0000-0000-000000000015');
INSERT INTO authors_books (book_id, author_id) VALUES
('b0000001-0000-0000-0000-000000000025', 'a0000001-0000-0000-0000-000000000015');

-- =====================================================
-- RELACIONES GÉNERO-LIBRO
-- =====================================================
-- El Quijote -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000001', 'g0000001-0000-0000-0000-000000000003');
-- El Hobbit -> Fantasía
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000002', 'g0000001-0000-0000-0000-000000000001');
-- El Señor de los Anillos -> Fantasía
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000003', 'g0000001-0000-0000-0000-000000000001');
-- 1984 -> Ciencia Ficción
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000004', 'g0000001-0000-0000-0000-000000000002');
-- Rebelión en la granja -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000005', 'g0000001-0000-0000-0000-000000000003');
-- Cien años de soledad -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000006', 'g0000001-0000-0000-0000-000000000003');
-- El amor en tiempos de cólera -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000007', 'g0000001-0000-0000-0000-000000000003');
-- Fundación -> Ciencia Ficción
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000008', 'g0000001-0000-0000-0000-000000000002');
-- Yo, robot -> Ciencia Ficción
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000009', 'g0000001-0000-0000-0000-000000000002');
-- Dune -> Ciencia Ficción + Fantasía
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000010', 'g0000001-0000-0000-0000-000000000002');
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000010', 'g0000001-0000-0000-0000-000000000001');
-- Fahrenheit 451 -> Ciencia Ficción
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000011', 'g0000001-0000-0000-0000-000000000002');
-- Crónicas marcianas -> Ciencia Ficción
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000012', 'g0000001-0000-0000-0000-000000000002');
-- La sombra del viento -> Novela + Terror
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000013', 'g0000001-0000-0000-0000-000000000003');
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000013', 'g0000001-0000-0000-0000-000000000004');
-- El juego del ángel -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000014', 'g0000001-0000-0000-0000-000000000003');
-- El nombre de la rosa -> Historia + Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000015', 'g0000001-0000-0000-0000-000000000005');
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000015', 'g0000001-0000-0000-0000-000000000003');
-- It -> Terror
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000016', 'g0000001-0000-0000-0000-000000000004');
-- El resplandor -> Terror
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000017', 'g0000001-0000-0000-0000-000000000004');
-- Misery -> Terror
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000018', 'g0000001-0000-0000-0000-000000000004');
-- Drácula -> Terror
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000019', 'g0000001-0000-0000-0000-000000000004');
-- Orgullo y prejuicio -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000020', 'g0000001-0000-0000-0000-000000000003');
-- Emma -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000021', 'g0000001-0000-0000-0000-000000000003');
-- Crimen y castigo -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000022', 'g0000001-0000-0000-0000-000000000003');
-- La casa de Bernarda Alba -> Poesía
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000023', 'g0000001-0000-0000-0000-000000000006');
-- Rayuela -> Novela
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000024', 'g0000001-0000-0000-0000-000000000003');
-- Bestiario -> Fantasía
INSERT INTO genres_books (book_id, genre_id) VALUES
('b0000001-0000-0000-0000-000000000025', 'g0000001-0000-0000-0000-000000000001');

-- =====================================================
-- MIEMBROS (10)
-- =====================================================
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000001', 0, 'María López García', 'maria.lopez@email.com', 'ACTIVE', '2025-09-01 09:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000002', 0, 'Carlos Fernández Ruiz', 'carlos.fernandez@email.com', 'ACTIVE', '2025-09-15 10:30:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000003', 0, 'Ana Martínez Díaz', 'ana.martinez@email.com', 'ACTIVE', '2025-10-01 11:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000004', 0, 'Pedro Sánchez Morales', 'pedro.sanchez@email.com', 'PENDING', '2026-03-20 14:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000005', 0, 'Laura García Navarro', 'laura.garcia@email.com', 'ACTIVE', '2025-10-10 09:30:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000006', 0, 'Jorge Rodríguez Blanco', 'jorge.rodriguez@email.com', 'SUSPENDED', '2025-08-01 08:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000007', 0, 'Isabel Torres Vega', 'isabel.torres@email.com', 'ACTIVE', '2025-11-05 12:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000008', 0, 'Miguel Ángel Ruiz', 'miguel.ruiz@email.com', 'BLOCKED', '2025-07-15 10:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000009', 0, 'Sofía Hernández Prieto', 'sofia.hernandez@email.com', 'ACTIVE', '2025-12-01 16:00:00');
INSERT INTO member (id, version, name, email, member_state, register_date) VALUES
('m0000001-0000-0000-0000-000000000010', 0, 'David Moreno Gil', 'david.moreno@email.com', 'INACTIVE', '2025-06-01 09:00:00');

-- =====================================================
-- PRÉSTAMOS (10)
-- =====================================================
-- 1. María López - ACTIVE (El Hobbit + 1984)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000001', 0, 'ACTIVE', '2026-03-20 10:00:00', '2026-04-20 10:00:00', NULL, 'm0000001-0000-0000-0000-000000000001');
-- 2. Carlos Fernández - RETURNED (El Quijote)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000002', 0, 'RETURNED', '2026-02-01 09:00:00', '2026-03-01 09:00:00', '2026-02-25 11:00:00', 'm0000001-0000-0000-0000-000000000002');
-- 3. Ana Martínez - ACTIVE (Fundación)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000003', 0, 'ACTIVE', '2026-03-25 14:00:00', '2026-04-25 14:00:00', NULL, 'm0000001-0000-0000-0000-000000000003');
-- 4. Laura García - RETURNED (Drácula)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000004', 0, 'RETURNED', '2026-01-10 10:00:00', '2026-02-10 10:00:00', '2026-02-05 15:00:00', 'm0000001-0000-0000-0000-000000000005');
-- 5. Jorge Rodríguez - OVERDUE (El resplandor)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000005', 0, 'OVERDUE', '2026-02-15 11:00:00', '2026-03-15 11:00:00', NULL, 'm0000001-0000-0000-0000-000000000006');
-- 6. Sofía Hernández - ACTIVE (Rayuela + Emma)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000006', 0, 'ACTIVE', '2026-03-28 16:00:00', '2026-04-28 16:00:00', NULL, 'm0000001-0000-0000-0000-000000000009');
-- 7. Carlos Fernández - ACTIVE (Dune)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000007', 0, 'ACTIVE', '2026-04-01 09:30:00', '2026-05-01 09:30:00', NULL, 'm0000001-0000-0000-0000-000000000002');
-- 8. María López - RETURNED (Crimen y castigo)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000008', 0, 'RETURNED', '2025-12-01 10:00:00', '2026-01-01 10:00:00', '2025-12-28 14:00:00', 'm0000001-0000-0000-0000-000000000001');
-- 9. Isabel Torres - CANCELLED (Misery)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000009', 0, 'CANCELLED', '2026-03-10 12:00:00', '2026-04-10 12:00:00', NULL, 'm0000001-0000-0000-0000-000000000007');
-- 10. David Moreno - RETURNED (El Quijote + Cien años de soledad)
INSERT INTO loan (id, version, loan_state, loan_date, expiring_date, due_date, member_id) VALUES
('l0000001-0000-0000-0000-000000000010', 0, 'RETURNED', '2026-01-15 09:00:00', '2026-02-15 09:00:00', '2026-02-10 17:00:00', 'm0000001-0000-0000-0000-000000000010');

-- =====================================================
-- LÍNEAS DE PRÉSTAMO
-- =====================================================
-- Préstamo 1: El Hobbit (1) + 1984 (1)
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000001', 0, 1, 0, 'l0000001-0000-0000-0000-000000000001', 'b0000001-0000-0000-0000-000000000002');
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000002', 0, 1, 0, 'l0000001-0000-0000-0000-000000000001', 'b0000001-0000-0000-0000-000000000004');
-- Préstamo 2: El Quijote (1) - devuelto
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000003', 0, 1, 1, 'l0000001-0000-0000-0000-000000000002', 'b0000001-0000-0000-0000-000000000001');
-- Préstamo 3: Fundación (1)
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000004', 0, 1, 0, 'l0000001-0000-0000-0000-000000000003', 'b0000001-0000-0000-0000-000000000008');
-- Préstamo 4: Drácula (1) - devuelto
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000005', 0, 1, 1, 'l0000001-0000-0000-0000-000000000004', 'b0000001-0000-0000-0000-000000000019');
-- Préstamo 5: El resplandor (1) - vencido
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000006', 0, 1, 0, 'l0000001-0000-0000-0000-000000000005', 'b0000001-0000-0000-0000-000000000017');
-- Préstamo 6: Rayuela (1) + Emma (1)
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000007', 0, 1, 0, 'l0000001-0000-0000-0000-000000000006', 'b0000001-0000-0000-0000-000000000024');
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000008', 0, 1, 0, 'l0000001-0000-0000-0000-000000000006', 'b0000001-0000-0000-0000-000000000021');
-- Préstamo 7: Dune (1)
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000009', 0, 1, 0, 'l0000001-0000-0000-0000-000000000007', 'b0000001-0000-0000-0000-000000000010');
-- Préstamo 8: Crimen y castigo (1) - devuelto
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000010', 0, 1, 1, 'l0000001-0000-0000-0000-000000000008', 'b0000001-0000-0000-0000-000000000022');
-- Préstamo 9: Misery (1) - cancelado
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000011', 0, 1, 0, 'l0000001-0000-0000-0000-000000000009', 'b0000001-0000-0000-0000-000000000018');
-- Préstamo 10: El Quijote (1) + Cien años de soledad (1) - devuelto
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000012', 0, 1, 1, 'l0000001-0000-0000-0000-000000000010', 'b0000001-0000-0000-0000-000000000001');
INSERT INTO loan_line (id, version, ordered_quantity, returned_quantity, loan_id, book_id) VALUES
('ll000001-0000-0000-0000-000000000013', 0, 1, 1, 'l0000001-0000-0000-0000-000000000010', 'b0000001-0000-0000-0000-000000000006');
