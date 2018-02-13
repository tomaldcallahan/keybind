package util.exception;

public class HinaException extends RuntimeException {

	public HinaException(String message) {
		super(message);
	}

	public HinaException(String message, Throwable e) {
		super(message, e);
	}

}
