package project.moseup.repository.teampage;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {

	private final EntityManager em;
	
	// 팀 멤버 저장
	public void save(TeamMember teamMember) {
		em.persist(teamMember);
	}
	
	// 팀 멤버 탈퇴 - 솔 -
	public void merge(TeamMember teamMember){
		em.merge(teamMember);
	}
	
	// 팀 멤버 존재 판단
	public Optional<TeamMember> findMember(Team team, Member member) {
		List<TeamMember> teamMember = em.createQuery("select t from TeamMember t where t.team =: team and t.member =: member", TeamMember.class)
				.setParameter("team", team)
				.setParameter("member", member)
				.getResultList();
		return teamMember.stream().findAny();
	}
	
	// 팀 멤버 조회
	public TeamMember findExistMember(Team team, Member member) {
		return em.createQuery("select t from TeamMember t where t.team =: team and t.member =: member", TeamMember.class)
				.setParameter("team", team)
				.setParameter("member", member)
				.getSingleResult();
	}
}
