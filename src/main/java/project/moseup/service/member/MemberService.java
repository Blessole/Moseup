package project.moseup.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberInterfaceRepository memberInterfaceRepository;
	private final PasswordEncoder passwordEncoder;

	// 파일 업로드 경로
	@Value("${moseup.upload.path}") //application.properties의 변수
	private String uploadPath;

	/** principal 조회 **/
	public Member getPrincipal(Principal principal) {
		return this.getMember(principal.getName());
	}

	/** 회원 정보 조회 **/
	public Member getMember(String email) {
		Optional<Member> member = this.memberInterfaceRepository.findByEmail(email);
		if (member.isPresent()) {
			return member.get();
		} else {
			throw new UsernameNotFoundException("member not found");
		}
	}

	/** 회원가입 **/
	@Transactional // 값을 넣어야하는 곳에는 읽기만 하면 안되니 따로 @Transactional를 사용
	public void join(MemberSaveReqDto joinForm) {

		Member member = joinForm.toEntity();

		// password 암호화
		member.encodePassword(passwordEncoder);

		// DB 저장
		memberRepository.save(member);
	}

	/** 회원 전체 조회 **/
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	/** 회원 단건 조회 **/
	public Member findOne(Long memberId) {
		return memberRepository.findOneMno(memberId);
	}

	/** 아이디 찾기 **/
	public Member findByName(MemberSaveReqDto memberSaveReqDto) {
		Member member = memberSaveReqDto.toFindID();
		return memberInterfaceRepository.findByNameAndPhoneAndMemberDelete(member.getName(), member.getPhone(), DeleteStatus.FALSE);
	}

	/** 비밀번호 찾기 **/
	public Member findByEmail(MemberSaveReqDto memberSaveReqDto) {
		Member member = memberSaveReqDto.toFindPW();
		return memberInterfaceRepository.findByEmailAndNameAndMemberDelete(member.getEmail(), member.getName(), DeleteStatus.FALSE);
	}
	/** 임시 비밀번호 생성 **/
	public String getTmpPassword() {
		char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String pw = "";

		int idx = 0;
		for(int i=0; i<10; i++){
			idx = (int) (charSet.length * Math.random());
			pw += charSet[idx];
		}
		log.info("임시 비밀번호 생성");

		return pw;
	}
	/** 임시 비밀번호로 업데이트 **/
	public void updatePassword(String tmpPw, String email) {
		String encryptPassword = passwordEncoder.encode(tmpPw);
		Member member = memberRepository.findOneEmail(email);
		member.updatePassword(encryptPassword);
		log.info("임시 비밀번호 업데이트");
	}

	/** 회원 정보 수정 **/
	@Transactional
	public void update(MemberSaveReqDto memberDto, Long mno) {
		Member member = memberInterfaceRepository.findById(mno).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
		Member member1 = memberDto.toUpdate();
		member1.encodePassword(passwordEncoder);	// 도메인에 있는 updatePassword 메소드도 사용 가능할듯?

//		memberRepository.save(member1);
		member.infoUpdate(member1);
		log.info("회원 정보 수정 완료");
	}

	/** 카카오 로그인 **/
	@Transactional
	public Member loginWithKakao(Member member) {
		Member savedMember = memberRepository.findOneEmail(member.getEmail());
		if (savedMember == null) {
			memberRepository.save(member);
		}
		return savedMember;
	}

	/** 회원 탈퇴 **/
	@Transactional
	public void delete(String loginId) {
		Member member = memberInterfaceRepository.findByEmail(loginId).orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
		member.deleteUpdate(DeleteStatus.TRUE);
		memberRepository.save(member);
	}

	/** 회원정보 확인 전 비밀번호 확인 **/
	public Boolean checkPassword(@NotEmpty String password, String loginId) {
		Member member = memberRepository.findOneEmail(loginId);
		return passwordEncoder.matches(password, member.getPassword());
	}

	/** 마이페이지 사진, 닉네임 불러오기 메소드 **/
	public Member getPhotoAndNickname(Principal principal, Model model) {
		Member member = this.getMember(principal.getName());
		model.addAttribute("member", member);

		String realPhoto = "";
		if (member.getPhoto()==null || member.getPhoto().equals("")){
			realPhoto = "/images/profile.png";
		} else {
			// 사진 경로 local에서 project용으로 변경
			String photo = member.getPhoto();
			int index = photo.indexOf("images");
			realPhoto = photo.substring(index - 1);
		}
		model.addAttribute("photoPath", realPhoto);

		return member;
	}

	/** 파일 등록 시 폴더 생성  및 파일 경로 저장 **/
	public String makeFolderAndFileName(MultipartFile file, String folderPath){
		// 사용 브라우저에 따라 파일이름/경로 다름
		String originalName = file.getOriginalFilename();
		String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

		File uploadPathFolder = new File(uploadPath, folderPath);
		if(!uploadPathFolder.exists()){
			try{
				uploadPathFolder.mkdirs(); //폴더 생성
			} catch (Exception e){
				e.getStackTrace(); // 에러 발생
			}
		}
		// 파일 경로 저장하기
		String uuid = UUID.randomUUID().toString();
		String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName; // 경로 + 폴더명
		Path savePath = Paths.get(saveName);
		try{
			file.transferTo(savePath);
		} catch (IOException e){
			e.printStackTrace();
		}

		return saveName;
	}

}
