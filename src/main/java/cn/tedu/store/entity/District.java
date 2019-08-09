package cn.tedu.store.entity;

import java.io.Serializable;
/**
 * 省市区数据的实体类
 * @author 12482
 *
 */
public class District implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String parent;
	private String code;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
