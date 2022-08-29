package project.moseup.service.myPage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Member;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;
import project.moseup.repository.myPage.MyPageRepository;
import project.moseup.service.member.MemberService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
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

    @Test
    @Rollback(false)
    public void findBankbookPaging() throws Exception {
        // given
        Member member = memberInterfaceRepository.findById(35L).orElse(null);


        // when

        // then
    }
}