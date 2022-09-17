package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Likes;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Getter
@Setter
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
