package project.moseup.config;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Role;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class MyEntityListener {

    @PrePersist
    public void prePersist(Object o) { //해당 오브젝트를 파라미터로 받아야하기 때문에 오브젝트를 강제한다
        if (o instanceof Auditable) {  //받아온 오브젝트가 Auditable 객체인지 확인
            ((Auditable) o).setMemberDate(LocalDateTime.now());
            ((Auditable) o).setMemberDelete(DeleteStatus.FALSE);
            ((Auditable) o).setRole(Role.USER);
        }
    }
}
