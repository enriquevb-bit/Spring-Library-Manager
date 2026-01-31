package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.MemberDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberService {

    Optional<MemberDTO> getMemberById(UUID uuid);

    List<MemberDTO> getAllMembers();

    MemberDTO saveNewMember(MemberDTO member);

    Optional<MemberDTO> updateMemberById(UUID memberId, MemberDTO member);

    Boolean deleteMemberById(UUID memberId);

    Optional<MemberDTO> patchMemberById(UUID memberId, MemberDTO member);
}
