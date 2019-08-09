package cn.tedu.store.mapper;

import java.util.Date;


import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;
/**
 * 处理
 * @author 12482
 *
 */
public interface UserMapper {
	/*
	 * 插入用户数据
	 */
	Integer addnew (User user);
	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
	/**
	 * 更新用户的密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updatePassword(
			@Param("uid")Integer uid,
			@Param("password")String password,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUid(Integer uid);
	
	
	/**
	 * 修改用户资料
	 * @param user 用户资料，必须包含用户的id
	 * @return 受影响的行数
	 */
	Integer updateInfo(User user);
	
	/**
	 * 更新用户的头像
	 * @param uid
	 * @param avatar
	 * @param modifiedUser
	 * @param modifiedTime
	 * @return
	 */
	Integer updateAvatar(
			@Param("uid")Integer uid,
			@Param("avatar")String avatar,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
}

