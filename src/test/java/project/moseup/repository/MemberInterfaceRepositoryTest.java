package project.moseup.repository;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Member;
import project.moseup.domain.Role;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
public class MemberInterfaceRepositoryTest {
    @Autowired
    MemberInterfaceRepository memberInterfaceRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @AfterEach
    private void after(){
        em.clear();
    }
}