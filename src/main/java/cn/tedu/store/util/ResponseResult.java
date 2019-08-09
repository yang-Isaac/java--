package cn.tedu.store.util;

import java.io.Serializable;

/**
 * 响应结果类型
 * @param <T> 服务器向客户端响应的数据的类型
 */
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = -1626793180717240861L;

	private Integer state;
	private String message;
	private T data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Integer state) {
		super();
		this.state = state;
	}

	public ResponseResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}

	public ResponseResult(Integer state, Exception e) {
		super();
		this.state = state;
		this.message = e.getMessage();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

}
