package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConflictException;
import cn.tedu.store.service.ex.UserNotFoundException;
/**
 * 处理用户数据的业务层实现类
 * @author 12482
 *
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	@Override
	public void reg(User user)throws UserConflictException,InsertException {
		// 根据user.getUsername()获取用户名匹配的数据
		String username = user.getUsername();
		User data = findByUsername(username);
		// 检查数据是否为null
		if (data == null) {
			// 是：为null，用户名未被占用，则应该补全参数中的属性值
			// - 1. 密码加密，并封装
			String salt = UUID.randomUUID().toString().toUpperCase();
			String md5Password
			= getMd5Password(user.getPassword(), salt);
			user.setPassword(md5Password);
			// - 2. 封装salt
			user.setSalt(salt);
			// - 3. 封装isDelete，固定为0
			user.setIsDelete(0);
			// - 4. 封装4项日志数据
			Date now = new Date();
			user.setCreatedTime(now);
			user.setModifiedTime(now);
			user.setCreatedUser(username);
			user.setModifiedUser(username);
			// - 执行注册：addnew(user)
			addnew(user);
		} else {
			// 否：非null，用户名被占用，则抛出UserConflictException
			throw new UserConflictException(
					"注册失败！您尝试注册的用户名(" + username + ")已经被占用！");
		}

	}

	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// 根据username查询用户数据
		User data=userMapper.findByUsername(username);
		System.err.println(data);
		// 判断用户数据是否为null
		if (data==null) {
			System.err.println(data);
			// 是：为null，即用户数据不存在，则抛出UserNotFoundException
			throw new UserNotFoundException("登录失败！用户名不存在！");

		} else {
			// 否：非null，即用户数据存在，判断是否已删除：isDelete.eqauls(1)
			if (data.getIsDelete().equals(1)) {
				System.err.println(data.getIsDelete());
				// 是：已删除，则抛出UserNotFoundException
				throw new UserNotFoundException("登录失败！用户名不存在！");
			} else {
				// 否：未删除，先取出salt
				String salt=data.getSalt();
				// 将参数password结合salt执行加密
				String md5Password = getMd5Password(password, salt);
				// 判断密码是否匹配
				//System.err.println(salt);
				//System.err.println(md5Password);
				if(data.getPassword().equals(md5Password)){
					// 是：先将查询到的用户数据中的password和salt属性设置为null再返回
					data.setPassword(null);
					data.setSalt(null);
					data.setIsDelete(null);
					return data;

				}else{
					// 否：则抛出PasswordNotMatchException
					throw new PasswordNotMatchException("登录失败！密码错误！");
				}
			}
		}
	}

	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		//根据uid查询用户信息
		User data=findByUid(uid);
		System.err.println(data);
		//判断查询结果是否为null
		if(data==null){
			//是:用户数据不存在，抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败！尝试访问的用户数据不存在!");
		}
		//判断isDelete是否为1
		if(data.getIsDelete()==1){
			//是:用户已标记为删除，抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败！尝试访问的用户数据不存在！");
		}
		//从查询结果中取出盐值salt
		String salt=data.getSalt();
		//将oldPassword和salt进行加密，得到oldMd5Passwod
		String oldMd5Password = getMd5Password(oldPassword, salt);
		//判断oldMd5Password和查询到的密码是否匹配
		if(data.getPassword().equals(oldMd5Password)){
			//是:原密码正确，将newPassword和salt进行加密，得到newMd5Password
			String newMd5Password=getMd5Password(newPassword, salt);
			//创建Date对象表示modifiedTime
			Date now=new Date();
			//执行更新密码
			updatePassword(uid,newMd5Password,data.getUsername(),now);
		}else{
			//否:原密码错误，抛出PasswordNotMatchException
			throw new PasswordNotMatchException("修改密码失败！原密码错误");
		}
	}

	@Override
	public void changenInfo(User user) throws UpdateException, UserNotFoundException {
		//通过user.getUid()查询用户信息
		User data=findByUid(user.getUid());
		//判断查询结果是否为null
		if(data==null){
			//是:抛出UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败！尝试访问的用户数据不存在！");
		}

		//判断查询结果中的isDelete是否为1
		if(data.getIsDelete().equals(1)){
			//是:抛出UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败！尝试访问数据不存在！");
		}

		//在user中封装modified_??
		Date now =new Date();
		user.setModifiedTime(now);
		user.setModifiedUser(data.getUsername());
		//调用私有方法更新用户资料updateInfo(user)
		updateInfo(user);
	}

	@Override
	public User getByUid(Integer uid) {
		//查询数据
		User data =findByUid(uid);
		//判断用户数据是否存在
		if(data==null){
			throw new UserNotFoundException("尝试访问的用户数据不存在！");
		}
		//判断用户数据是否被标记为删除
		if(data.getIsDelete().equals(1)){
			throw new UserNotFoundException("尝试访问的用户数据不存在！");
		}
		//清除不希望对外暴露的数据
		data.setPassword(null);
		data.setSalt(null);
		data.setIsDelete(null);
		return data;
	}

	@Override
	public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
		// 根据uid查询用户信息：User findByUid(Integer uid);
		User data=findByUid(uid);
		// 判断查询结果是否为null
		if(data==null){
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户头像失败！尝试访问数据不存在！");
		}
		// 判断查询结果isDelete是否为1
		if(data.getIsDelete().equals(1)){
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户头像失败！尝试访问数据不存在！");
		}
		// 从查询结果中获取用户名
		String username=data.getUsername();
		// 创建当前时间对象
		Date now =new Date();
		// 更新头像：updateAvatar(Integer uid, String avatar, String modifiedUser, Date modifiedTime)
		updateAvatar(uid, avatar, username, now);


	}

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if (rows != 1) {
			throw new InsertException("增加用户数据时出现未知错误！请联系系统管理员！");
		}
	}
	/**
	 * 更新用户的密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updatePassword(
			Integer uid, String password, 
			String modifiedUser, Date modifiedTime) {
		Integer rows = userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("更新密码时出现未知错误！");
		}
	}

	/**
	 * 修改用户资料
	 * @param user 用户资料，必须包含用户的id
	 */
	private void updateInfo(User user) {
		Integer rows=userMapper.updateInfo(user);
		if(rows!=1){
			throw new UpdateException("更新用户资料时出现未知错误！");
		}
	}


	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);

	}
	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUid(Integer uid) {
		return userMapper.findByUid(uid);
	}


	/**
	 * 使用MD5算法执行密码加密
	 * @param password 密码原文
	 * @param salt 盐值
	 * @return加密后的密文
	 */

	private String getMd5Password(String password,String salt){
		//加密规则
		//1 将盐值添加到原文的左侧
		//2 执行加密10
		String str=salt+password;
		for (int i = 0; i <10; i++) {
			str=DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;


	}

	/**
	 * 
	 * @param uid
	 * @param avatar
	 * @param modifiedUser
	 * @param modifiedTime
	 */
	private void updateAvatar(
			Integer uid,
			String avatar,
			String modifiedUser,
			Date modifiedTime){
		Integer rows=userMapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		if(rows!=1){
			throw new UpdateException("更新用户头像失败");
		}
	}









}
