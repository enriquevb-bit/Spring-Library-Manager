package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Page<Member> findAllByNameIsLikeIgnoreCase(String name, Pageable pageable);

    Page<Member> findAllByEmail(String email, Pageable pageable);

    Page<Member> findAllByNameIsLikeIgnoreCaseAndEmail(String name, String email, Pageable pageable);

    Page<Member> findAllByMemberState(MemberState memberState, Pageable pageable);

    Page<Member> findAllByNameIsLikeIgnoreCaseAndMemberState(String name, MemberState memberState, Pageable pageable);

    Optional<Member> findFirstByEmail(String email);
}
