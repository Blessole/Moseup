package project.moseup.validator;

public abstract class Check<V> {
    public abstract String emailCheck(final V value);
    public abstract String nicknameCheck(final V value);
    public abstract  String passwordCheck(String password1, String password2);
}
