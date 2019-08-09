package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileContentTypeException;
import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ResponseResult;

/**
 * 处理用户数据请求的控制器类
 */
@RestController
@RequestMapping("users")
public class UserController
	extends BaseController {
	
	@Autowired
	private IUserService userService;
	/**
	 * 储存上传的文件的文件夹名称
	 */
	public static final String UPLOAD_DIR="upload";
	
	/**
	 * 上传文件时允许上传的最大大小
	 */
	public static final long UPLOAD_MAX_SIZE=1*1024*1024;
	
	/**
	 * 允许上传的文件类型的集合
	 */
	public static final List<String>UPLOAD_CONTENT_TYPES=new ArrayList<String>();
	/**
	 * 添加允许上传的文件类型
	 */
	static{
		UPLOAD_CONTENT_TYPES.add("image/jpeg");
		UPLOAD_CONTENT_TYPES.add("image/png");
		UPLOAD_CONTENT_TYPES.add("image/gif");
		UPLOAD_CONTENT_TYPES.add("image/bmp");
		
	}
	
	@PostMapping("reg")
	public ResponseResult<Void> 
		handleReg(User user) {
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}
	@PostMapping("login")
	public ResponseResult<User>handleLongin(@RequestParam("username")String username,
	@RequestParam("password")String password,
	HttpSession session){
		
		User user=userService.login(username, password);
		session.setAttribute("uid", user.getUid());
		session.setAttribute("username", user.getUsername());
		return new ResponseResult<User>(SUCCESS,user);
	} 
	
	@PostMapping("change_password")
	public ResponseResult<Void>changePassword(
			@RequestParam("old_password")String oldPassword,
			@RequestParam("new_password")String newPassword,
			HttpSession session){
		Integer uid=getUidFromSession(session);
				userService.changePassword(uid, oldPassword, newPassword);
		return new ResponseResult<Void>(SUCCESS);
		
	}
	
	@PostMapping("change_info")
	public ResponseResult<Void>changeInfo(User user,HttpSession session){
		Integer uid=getUidFromSession(session);
		user.setUid(uid);
		userService.changenInfo(user);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	
	@PostMapping("change_avatar")
	public ResponseResult<String> changeAvatar(
		HttpServletRequest request, 
		@RequestParam("avatar") MultipartFile avatar) {
		// 判断上传的文件是否为空：avatar.isEmpty()
		System.err.println(avatar.getSize());
		if (avatar.isEmpty()) {
			// 是：抛出异常：FileEmptyException
			throw new FileEmptyException(
				"上传文件失败！没有选择文件，或上传的文件为空！");
		}
				
		// 判断文件类型是否不在允许的范围内：avatar.getContentType() / list.contains(contentType)
		String contentType = avatar.getContentType();
		if (!UPLOAD_CONTENT_TYPES.contains(contentType)) {
			// 是：抛出异常：FileContentTypeException
			throw new FileContentTypeException(
				"上传文件失败！不支持上传" + contentType + "类型的文件！");
		}

		// 判断文件大小是否超出了限制：avatar.getSize()
		long size = avatar.getSize();
		if (size > UPLOAD_MAX_SIZE) {
			// 是：抛出异常：FileSizeException
			throw new FileSizeException(
				"上传文件失败！尝试上传的文件大小超出了限制！仅允许上传不超过" + UPLOAD_MAX_SIZE/1024/1024 + "M的文件！");
		}

		// 确定上传的文件夹
		String parentPath = request.getServletContext().getRealPath(UPLOAD_DIR);
		File parent = new File(parentPath);
		if (!parent.exists()) {
			parent.mkdirs();
		}
		
		// 确定上传的文件名
		String orginalFilename = avatar.getOriginalFilename();
		String suffix = "";
		int beginIndex = orginalFilename.lastIndexOf(".");
		if (beginIndex != -1) {
			suffix = orginalFilename.substring(beginIndex);
		}
		String filename = UUID.randomUUID() + suffix; 
		
		// 执行存储
		File dest = new File(parent, filename);
		try {
			avatar.transferTo(dest);
		} catch (IOException e) {
			// e.printStackTrace();
			throw new FileUploadIOException(
				"上传文件失败！出现读写错误，请联系系统管理员，或稍后再次尝试！");
		}

		// 将上传的文件路径存储到数据库：service.changeAvatar(uid, avatar)
		Integer uid 
			= getUidFromSession(request.getSession());
		String avatarUrl 
			= "/" + UPLOAD_DIR + "/" + filename;
		userService.changeAvatar(uid, avatarUrl);
		
		// 返回
		ResponseResult<String> rr
			= new ResponseResult<String>();
		rr.setState(SUCCESS);
		rr.setData(avatarUrl);
		return rr;
	}
	
	@PostMapping("get_info")
	public ResponseResult<User> getInfo(HttpSession session){
		Integer uid=getUidFromSession(session);
		User user=userService.getByUid(uid);
		return new ResponseResult<User>(SUCCESS,user);
		
	}
	
}






