package enriquevb.biblioteca.services;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class MemberServiceJPA implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Optional<MemberDTO> getMemberById(UUID uuid) {
        return Optional.ofNullable(memberMapper
                .memberToMemberDto(memberRepository.findById(uuid).orElse(null)));
    }

    @Override
    public Page<MemberDTO> listMembers(String name, String email, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Member> memberPage;

        if (StringUtils.hasText(name) && !StringUtils.hasText(email)) {
            memberPage = listMembersByName(name, pageRequest);
        } else if (!StringUtils.hasText(name) && StringUtils.hasText(email)) {
            memberPage = listMembersByEmail(email, pageRequest);
        } else if (StringUtils.hasText(name) && StringUtils.hasText(email)) {
            memberPage = listMembersByNameAndEmail(name, email, pageRequest);
        } else {
            memberPage = memberRepository.findAll(pageRequest);
        }

        return memberPage.map(memberMapper::memberToMemberDto);
    }

    private Page<Member> listMembersByNameAndEmail(String name, String email, PageRequest pageRequest) {
        return memberRepository.findAllByNameIsLikeIgnoreCaseAndEmail(name,email,pageRequest);
    }

    private Page<Member> listMembersByEmail(String email, PageRequest pageRequest) {
        return memberRepository.findAllByEmail(email, pageRequest);
    }

    private Page<Member> listMembersByName(String name, PageRequest pageRequest) {
        return memberRepository.findAllByNameIsLikeIgnoreCase("%" + name + "%",pageRequest);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize != null && pageSize > 0){
            if (pageSize > 1000){
                queryPageSize = 1000;
            }else {
                queryPageSize = pageSize;
            }
        }else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(Sort.Order.asc("name"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);


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
