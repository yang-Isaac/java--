 package cn.tedu.store.service.ex;

/**
 * 用户名冲突异常
 */
public class UserConflictException 
	extends ServiceException {

	private static final long serialVersionUID = 2892557817982230970L;

	public UserConflictException() {
		super();
	}
	

	public UserConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserConflictException(String message) {
		super(message);
	}

	public UserConflictException(Throwable cause) {
		super(cause);
	}

}
