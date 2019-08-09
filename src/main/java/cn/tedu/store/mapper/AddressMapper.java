 package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;
/**
 * 处理收货地址的持久层接口
 * @author 12482
 *
 */
public interface AddressMapper {
	
	/**
	 * 插入收货地址
	 * @param address
	 * @return
	 */
	 Integer insert(Address address) ;

	 /**
	  * 根据用户id查询该用户的收货地址的数量
	  * @param uid
	  * @return
	  */
	Integer getCountByUid(Integer uid) ;
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param Uid
	 * @return
	 */
	List<Address> findListByUid(Integer Uid);
	
	/**
	 * 将指定用户的所有收货地址数据全部设置为"非默认"
	 * @param uid用户的id
	 * @param modifiedUser修改执行人
	 * @param modifiedTime修改时间
	 * @return受影响的行数
	 */
	Integer updateNonDefault(
			@Param("uid")Integer uid,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	
	/**
	 * 将指定的收货地址数据设置为"默认"
	 * @param aid收货地址的id
	 * @param modifiedUser修改执行人
	 * @param modifiedTim修改时间
	 * @return受影响的行数
	 */
	Integer updateDefault(
			@Param("aid")Integer aid,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTim);
	
	
	/**
	 * 根据收货地址数据的id查询收货地址详情
	 * @param aid收货地址数据的详情
	 * @return匹配的收货地址数据，如果没有匹配的数据，则返回null
	 */
	Address findByAid(Integer aid);
	
	/**
	 * 根据用户的收货地址aid删除数据
	 * @param aid
	 * @return
	 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * 查询指定用户的最后修改的收货地址数据
	 * @param uid
	 * @return
	 */
	Address findLastModifiedByUid(Integer uid);
	
	
	
	
}
