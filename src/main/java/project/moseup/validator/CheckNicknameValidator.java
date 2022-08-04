package project.moseup.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<MemberSaveReqDto>{

    private final AdminMemberRepository adminMemberRepository;

    @Override
    protected void doValidate(MemberSaveReqDto dto, Errors errors) {
        if(adminMemberRepository.existsByNickname(dto.toEntity().getNickname())){
            // 중복인 경우
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임입니다.");
        }
    }
}
