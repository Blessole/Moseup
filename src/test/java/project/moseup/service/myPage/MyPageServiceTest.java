package project.moseup.service.myPage;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Likes;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.LikeSaveReqDto;
import project.moseup.repository.likes.LikesRepository;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;
import project.moseup.repository.myPage.MyPageRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;
import project.moseup.service.member.MemberService;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class MyPageServiceTest {

    @Autowired
    MyPageService myPageService;
    @Autowired
    MyPageRepository myPageRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberInterfaceRepository memberInterfaceRepository;
    @Autowired
    TeamInterfaceRepository  teamInterfaceRepository;
    @Autowired
    LikesRepository likesRepository;

    @Test
    public void 찜목록추가() throws Exception {
        // given
        Optional<Member> memberOP =  memberInterfaceRepository.findById(1L);
        Optional<Team> teamOP = teamInterfaceRepository.findById(2L);

        if (memberOP.isPresent() && teamOP.isPresent()) {
            Member member = memberOP.get();
            Team team = teamOP.get();

            LikeSaveReqDto dto = new LikeSaveReqDto();
            dto.setMember(member);
            dto.setTeam(team);

            Likes likes = likesRepository.save(dto.toEntity());

            assertThat(likes.getMember().getMno()).isEqualTo(member.getMno());
            assertThat(likes.getTeam().getTno()).isEqualTo(team.getTno());

        } else {
            log.info("에러");
        }


        // when

        // then
    }
}