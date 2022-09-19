package project.moseup.service.teampage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Likes;
import project.moseup.dto.LikeSaveReqDto;
import project.moseup.repository.LikesRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

	private final LikesRepository likesRepository;
	
	// 좋아요 등록
	@Transactional
	public void insert(LikeSaveReqDto likeSaveReqDto) {
		Likes likes = likeSaveReqDto.toEntity();
		likesRepository.save(likes);
	}
}
