package project.moseup.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class LikesId implements Serializable {

    @Column(name = "member_no")
    private Long mno;

    @Column(name = "team_no")
    private Long tno;


}
