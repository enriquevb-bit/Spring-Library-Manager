package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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
