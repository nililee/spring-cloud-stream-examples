package examples.exception;

public class SomeBusinessException extends Exception {

	private static final long serialVersionUID = 653131147898743165L;

	public SomeBusinessException(String string) {
		super(string);
	}
}
