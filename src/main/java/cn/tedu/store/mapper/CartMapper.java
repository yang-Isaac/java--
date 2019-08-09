package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {

	/**
	 * 新增购物车数据
	 * @param cart 购物车数据
	 * @return 受影响的行数
	 */
	Integer insert(Cart cart);

	/**
	 * 修改购物车中的商品的数量
	 * @param cid 购物车数据的id
	 * @param num 新的数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateNum(
		@Param("cid") Integer cid, 
		@Param("num") Integer num, 
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime);

	/**
	 * 根据用户id和商品id查询购物车数据
	 * @param uid 用户id
	 * @param gid 商品id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	Cart findByUidAndGid(
		@Param("uid") Integer uid, 
		@Param("gid") Long gid);
	
	/**
	 * 根据购物车数据id查询购物车数据
	 * @param cid 购物车数据id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	Cart findByCid(Integer cid);
	
	/**
	 * 查询指定用户的购物车数据
	 * @param uid 用户的id
	 * @return 指定用户的购物车数据
	 */
	List<CartVO> findListByUid(Integer uid);
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	List<CartVO> findListByCids(Integer[] cids);
	
}






