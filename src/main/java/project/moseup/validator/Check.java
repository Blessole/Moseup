package project.moseup.validator;

import java.security.Principal;

public abstract class Check<V> {
    public abstract String emailCheck(final V value);
    public abstract String nicknameCheck(final V value);
    public abstract  String passwordCheck(String password1, String password2);
    public abstract  String nicknameCheckInMyPage(String value, Principal principal);
}
