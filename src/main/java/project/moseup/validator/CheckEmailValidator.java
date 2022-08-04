package project.moseup.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberSaveReqDto>{

    private final AdminMemberRepository adminMemberRepository;

    @Override
    protected void doValidate(MemberSaveReqDto dto, Errors errors) {
        if(adminMemberRepository.existsByEmail(dto.toEntity().getEmail())){
            // 중복인 경우
            errors.rejectValue("email", "아이디 중복 오류", "이미 사용중인 이메일입니다.");
        }

    }
}
