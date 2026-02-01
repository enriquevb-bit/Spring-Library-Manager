package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.models.MemberState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {

    Map<UUID, MemberDTO> memberMap;

    public MemberServiceImpl() {
        this.memberMap = new HashMap<>();

        MemberDTO member1 = MemberDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Juan Perez")
                .email("juan.perez@example.com")
                .memberState(MemberState.ACTIVE)
                .registerDate(LocalDateTime.now())
                .build();

        MemberDTO member2 = MemberDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Maria Garcia")
                .email("maria.garcia@test.com")
                .memberState(MemberState.PENDING)
                .registerDate(LocalDateTime.now())
                .build();

        MemberDTO member3 = MemberDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Carlos Lopez")
                .email("carlos.lopez@demo.com")
                .memberState(MemberState.INACTIVE)
                .registerDate(LocalDateTime.now())
                .build();

        memberMap.put(member1.getId(), member1);
        memberMap.put(member2.getId(), member2);
        memberMap.put(member3.getId(), member3);
    }

    @Override
    public Optional<MemberDTO> getMemberById(UUID uuid) {
        return Optional.ofNullable(memberMap.get(uuid));
    }

    @Override
    public Page<MemberDTO> listMembers(String name, String email, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(memberMap.values()));
    }

    @Override
    public MemberDTO saveNewMember(MemberDTO member) {
        MemberDTO savedMember = MemberDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name(member.getName())
                .email(member.getEmail())
                .memberState(member.getMemberState())
                .registerDate(LocalDateTime.now())
                .build();

        memberMap.put(savedMember.getId(), savedMember);
        return savedMember;
    }

    @Override
    public Optional<MemberDTO> updateMemberById(UUID memberId, MemberDTO member) {
        MemberDTO existing = memberMap.get(memberId);

        if (existing != null) {
            existing.setName(member.getName());
            existing.setEmail(member.getEmail());
            existing.setMemberState(member.getMemberState());
            return Optional.of(existing);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteMemberById(UUID memberId) {
        if (memberMap.containsKey(memberId)) {
            memberMap.remove(memberId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MemberDTO> patchMemberById(UUID memberId, MemberDTO member) {
        MemberDTO existing = memberMap.get(memberId);

        if (existing != null) {
            if (StringUtils.hasText(member.getName())) {
                existing.setName(member.getName());
            }
            if (StringUtils.hasText(member.getEmail())) {
                existing.setEmail(member.getEmail());
            }
            if (member.getMemberState() != null) {
                existing.setMemberState(member.getMemberState());
            }
            return Optional.of(existing);
        }

        return Optional.empty();
    }
}
