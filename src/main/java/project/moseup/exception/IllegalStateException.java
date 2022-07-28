package project.moseup.exception;

@SuppressWarnings("serial")
public class IllegalStateException extends Exception{
	
	public IllegalStateException() {
		super();
	}
	
	public IllegalStateException(String message) {
		super(message);
	}
	
	public IllegalStateException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IllegalStateException(Throwable cause) {
		super(cause);
	}

}