package project.moseup.exception;


public class NoLoginException extends RuntimeException{

    public NoLoginException(){
        super("로그인 후 이용해주세요");
    }
}
