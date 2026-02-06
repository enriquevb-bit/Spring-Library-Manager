package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberDTO;
import org.mapstruct.Mapper;

@Mapper
public interface MemberMapper {

    Member memberDtoToMember(MemberDTO memberDTO);

    MemberDTO memberToMemberDto(Member member);

}
