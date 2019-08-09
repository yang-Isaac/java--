package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
/**
 *处理收货地址的接口类
 * @author 12482
 *
 */
public interface IAddressService {
	/**
	 * 添加收货地址
	 * @param address当前操作执行人
	 * @throws InsertException插入数据异常
	 */
	void addnew (Address address,String username)throws InsertException;
	
	/**
	 * 将指定的收货地址数据设置为“默认收货地址”
	 * @param uid 当前登录的用户的id
	 * @param aid 被设置为默认的收货地址的id
	 * @param username 当前登录的用户名
	 * @throws UpdateException 更新数据异常
	 * @throws AddressNotFoundException 尝试访问的收货地址数据不存在时的异常
	 * @throws AccessDeniedException 尝试修改他人的收货地址数据时的拒绝访问异常
	 */
	void setDefault(Integer uid, Integer aid, String username) 
		    throws UpdateException, AddressNotFoundException, AccessDeniedException;
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid用户的id
	 * @return该用户的收货列表
	 */
	List<Address>getListByUid(Integer uid);
	
	/**
	 * 删除指定的收货地址数据
	 * @param uid 当前登录的用户的id
	 * @param aid 被删除的收货地址数据的id
	 * @param username 当前登录的用户名
	 * @throws DeleteException 删除数据异常
	 * @throws AddressNotFoundException 尝试删除的用户数据不存在
	 * @throws AccessDeniedException 尝试删除他人的收货地址数据时的拒绝访问异常
	 */
	void deleteByAid(Integer aid,Integer uid,String username)
			throws DeleteException,AddressNotFoundException,AccessDeniedException;
}
