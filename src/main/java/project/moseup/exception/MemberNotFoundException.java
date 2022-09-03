package project.moseup.exception;

public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException(Long id){
        super(String.format("Member with Id %d not found", id));
    }
}
