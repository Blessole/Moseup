package project.moseup.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class TeamMemberId  implements Serializable {

    @Column(name="member_no")
    private Long mno;

    @Column(name="team_no")
    private Long tno;
}
