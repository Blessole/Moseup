package project.moseup.config;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Role;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreatedAt();
    DeleteStatus getMemberDelete();
    Role getRole();

    void setMemberDate(LocalDateTime createdAt);
    void setMemberDelete(DeleteStatus memberDelete);
    void setRole(Role role);
}
