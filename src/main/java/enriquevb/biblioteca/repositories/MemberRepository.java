package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
