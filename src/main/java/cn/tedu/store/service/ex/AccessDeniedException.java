package cn.tedu.store.service.ex;

/**
 * 拒绝访问的异常，通常是访问权限不足，或尝试执行非法访问时抛出。
 */
public class AccessDeniedException extends ServiceException {

	private static final long serialVersionUID = 3849396365637118465L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

}
