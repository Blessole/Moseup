package project.moseup.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

@RequiredArgsConstructor
@Component
public class CheckPasswordValidator extends AbstractValidator<MemberSaveReqDto>{

    private final AdminMemberRepository adminMemberRepository;

    @Override
    protected void doValidate(MemberSaveReqDto dto, Errors errors) {
        if(!dto.getPassword().equals(dto.getPassword2())){
            // 비밀번호가 같지 않음
            errors.rejectValue("password2", "비밀번호 불일치 오류", "비밀번호가 일치하지 않습니다.");
        }
    }
}
