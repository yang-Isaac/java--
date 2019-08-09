package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("addresses")
public class AddressController
	extends BaseController {
	
	@Autowired
	private IAddressService addressService;
	
	@PostMapping("addnew")
	public ResponseResult<Void> addnew(
		Address address, HttpSession session) {
		// 从session中获取username
		String username = session.getAttribute("username").toString();
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 将uid封装到address中
		address.setUid(uid);
		// 直接调用业务层对象的addnew(address, username);
		addressService.addnew(address, username);
		// 返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("{aid}/set_default")
	public ResponseResult<Void> setDefault(
		@PathVariable("aid") Integer aid,
		HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 从session中获取用户名
		String username = session
			.getAttribute("username").toString();
		// 执行
		addressService.setDefault(uid, aid, username);;
		// 返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("{aid}/delete")
	public ResponseResult<Void> deleteByAid(
			@PathVariable("aid") Integer aid,
			HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 从session中获取用户名
		String username = session
				.getAttribute("username").toString();
		// 执行
		addressService.deleteByAid(aid, uid, username);
		// 返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@GetMapping("/")
	public ResponseResult<List<Address>> 
		getListByUid(HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 通过业务层对象查询数据
		List<Address> data
			= addressService.getListByUid(uid);
		// 返回：成功+数据
		return new ResponseResult<List<Address>>(
				SUCCESS, data);
	}

}





