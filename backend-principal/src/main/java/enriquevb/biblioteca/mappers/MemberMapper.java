package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MemberMapper {

    @Mapping(target = "loans", ignore = true)
    Member memberDtoToMember(MemberDTO memberDTO);

    MemberDTO memberToMemberDto(Member member);

}
