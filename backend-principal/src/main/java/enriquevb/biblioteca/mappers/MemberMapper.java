package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MemberMapper {

    Member memberDtoToMember(MemberDTO memberDTO);

    @Mapping(target = "loans", ignore = true)
    MemberDTO memberToMemberDto(Member member);

}
