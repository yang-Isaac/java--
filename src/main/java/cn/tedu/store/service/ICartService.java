package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {
	
	/**
	 * 将商品添加到购物车
	 * @param cart 购物车数据，至少需要包括：用户id，商品id，商品数量
	 * @param username 当前登录的用户的用户名
	 * @throws InsertException 插入数据异常
	 * @throws UpdateException 更新数据异常
	 */
	void addToCart(Cart cart, String username) 
		throws InsertException, 
			UpdateException;
	
	/**
	 * 将购物车数据中商品的数量增加1
	 * @param cid 购物车数据的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户的用户名
	 * @return 购物车中商品的新的数量
	 * @throws CartNotFoundException 购物车数据不存在的异常
	 * @throws AccessDeniedException 非法访问异常
	 * @throws UpdateException 更新数据异常
	 */
	Integer addNum(Integer cid, Integer uid, String username) 
		throws CartNotFoundException, 
			AccessDeniedException, UpdateException;
	
	/**
	 * 查询指定用户的购物车数据
	 * @param uid 用户的id
	 * @return 指定用户的购物车数据
	 */
	List<CartVO> getListByUid(Integer uid);
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	List<CartVO> getListByCids(Integer[] cids);

}







