package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.MemberDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface MemberService {

    Page<MemberDTO> listMembers(String name, String email, Integer pageNumber, Integer pageSize);

    Optional<MemberDTO> getMemberById(UUID uuid);

    MemberDTO saveNewMember(MemberDTO member);

    Optional<MemberDTO> updateMemberById(UUID memberId, MemberDTO member);

    Boolean deleteMemberById(UUID memberId);

    Optional<MemberDTO> patchMemberById(UUID memberId, MemberDTO member);
}
