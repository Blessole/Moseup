package project.moseup.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.Likes;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.LikeSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;
    @Autowired
    TeamInterfaceRepository teamInterfaceRepository;


    @Test
    public void 찜목록추가(){
        Optional<Member> memberOP = adminMemberRepository.findById(1L);
        Optional<Team> teamOP = teamInterfaceRepository.findById(1L);

        if(memberOP.isPresent() && teamOP.isPresent()){
            Member member = memberOP.get();
            Team team = teamOP.get();

            LikeSaveReqDto dto = new LikeSaveReqDto();
            dto.setMember(member);
            dto.setTeam(team);

            Likes likes = likesRepository.save(dto.toEntity());

            assertThat(likes.getMember().getMno()).isEqualTo(member.getMno());
            assertThat(likes.getTeam().getTno()).isEqualTo(team.getTno());

        }else{
            log.info("에러~");
        }
    }

    @Test
    public void 찜목록삭제(){

    }




}