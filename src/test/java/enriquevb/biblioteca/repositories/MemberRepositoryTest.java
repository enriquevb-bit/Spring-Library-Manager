package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void getListByNameAndEmail() {
        Page<Member> testList = memberRepository.findAllByNameIsLikeIgnoreCaseAndEmail("%a%", "maria.garcia@test.com", null);

        assertThat(testList.getContent().size()).isEqualTo(1);

    }

    @Test
    void getListByEmail() {
        Page<Member> testList = memberRepository.findAllByEmail("maria.garcia@test.com", null);

        assertThat(testList.getContent().size()).isEqualTo(1);
    }

    @Test
    void getListByName() {
        Page<Member> list = memberRepository.findAllByNameIsLikeIgnoreCase("%a%", null);

        assertThat(list.getContent().size()).isEqualTo(3);

    }

    @Test
    void testSaveMember() {
        Member savedMember = memberRepository.save(Member.builder()
                .name("Ana Martínez")
                .email("ana.martinez@example.com")
                .memberState(MemberState.ACTIVE)
                .build());

        memberRepository.flush();

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
    }
}
