package project.moseup.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.repository.MemberRepository;
import project.moseup.service.MemberService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Rollback(false)
    public void 멤버저장업데이트() throws Exception{

        // 저장 Test
        Member member = new Member(
                "jcw@naver.com",
                "1234",
                "찬",
                "정찬우",
                MemberGender.MALE,
                "경기도 안양시",
                "010-0000-0000",
                "사진Url",
                DeleteStatus.FALSE,
                LocalDateTime.now());
        Member saveMember = memberRepository.save(member);

        // 중복 Test



        // 업데이트 Test
        Member member1 = new Member(
                "jcw@naver.com",
                "1234",
                "찬",
                "찬우",
                MemberGender.MALE,
                "안양시 동안구",
                "010-3333-3333",
                "사진Url",
                DeleteStatus.FALSE,
                LocalDateTime.now());
        member1.setMno(member.getMno());
        Member updateMember = memberRepository.save(member1);





    }



}

