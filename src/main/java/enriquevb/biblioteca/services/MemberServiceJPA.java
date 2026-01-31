package enriquevb.biblioteca.services;

import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class MemberServiceJPA implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Optional<MemberDTO> getMemberById(UUID uuid) {
        return Optional.ofNullable(memberMapper
                .memberToMemberDto(memberRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::memberToMemberDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO saveNewMember(MemberDTO member) {
        return memberMapper.memberToMemberDto(memberRepository
                .save(memberMapper.memberDtoToMember(member)));
    }

    @Override
    public Optional<MemberDTO> updateMemberById(UUID memberId, MemberDTO member) {
        AtomicReference<Optional<MemberDTO>> atomicReference = new AtomicReference<>();

        memberRepository.findById(memberId).ifPresentOrElse(foundMember -> {
            foundMember.setName(member.getName());
            foundMember.setEmail(member.getEmail());
            foundMember.setMemberState(member.getMemberState());
            atomicReference.set(Optional.of(memberMapper
                    .memberToMemberDto(memberRepository.save(foundMember))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteMemberById(UUID memberId) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MemberDTO> patchMemberById(UUID memberId, MemberDTO member) {
        AtomicReference<Optional<MemberDTO>> atomicReference = new AtomicReference<>();

        memberRepository.findById(memberId).ifPresentOrElse(foundMember -> {
            if (StringUtils.hasText(member.getName())) {
                foundMember.setName(member.getName());
            }
            if (StringUtils.hasText(member.getEmail())) {
                foundMember.setEmail(member.getEmail());
            }
            if (member.getMemberState() != null) {
                foundMember.setMemberState(member.getMemberState());
            }
            atomicReference.set(Optional.of(memberMapper
                    .memberToMemberDto(memberRepository.save(foundMember))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
