package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.moseup.domain.Likes;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeSaveReqDto {
    private Member member;
    private Team team;

    public Likes toEntity(){
        return Likes.builder()
                .member(member)
                .team(team)
                .build();
    }
}
