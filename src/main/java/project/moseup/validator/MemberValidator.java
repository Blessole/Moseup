package project.moseup.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.moseup.repository.admin.AdminMemberRepository;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final AdminMemberRepository adminMemberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
