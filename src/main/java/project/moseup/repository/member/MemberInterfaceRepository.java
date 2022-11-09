package project.moseup.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.util.Optional;

public interface MemberInterfaceRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

//    boolean existsByNickname(String nickname);

    Member findByNameAndPhoneAndMemberDelete(String name, String phone, DeleteStatus deleteStatus);

    Member findByEmailAndNameAndMemberDelete(String email, String name, DeleteStatus deleteStatus);

}
