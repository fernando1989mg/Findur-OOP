package cl.vmetrix.findurreflection.exception;

public class RowGenerateException extends RuntimeException {

	public RowGenerateException(CreateDynamicInstanceException e) {
		super(e);
	}

	private static final long serialVersionUID = 1L;

}