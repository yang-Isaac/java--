 package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private ICartService cartService;
	
	@PostMapping("add")
	public ResponseResult<Void> addToCart(
		@RequestParam("gid") Long gid,
		@RequestParam("num") Integer num,
		HttpSession session) {
		// 从Session中获取uid, username
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		// 创建新的Cart对象
		Cart cart = new Cart();
		// 向Cart对象中封装gid, num, uid
		cart.setUid(uid);
		cart.setGid(gid);
		cart.setNum(num);
		// 调用业务层对象的addToCart(Cart cart, String username)方法
		cartService.addToCart(cart, username);
		// 返回成功
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("{cid}/add_num")
	public ResponseResult<Integer> addNum(
		@PathVariable("cid") Integer cid,
		HttpSession session){
		// 从Session中获取当前登录的用户的信息
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		// 执行
		Integer num = cartService.addNum(cid, uid, username);
		// 返回
		return new ResponseResult<Integer>(SUCCESS, num);
	}
	
	// http://localhost:8080/carts/ > 获取当前用户所有的购物车数据
	// http://localhost:8080/carts/?by=cids&cids=6&cids=4&cids=3 > 获取指定的购物车数据
	@GetMapping("/")
	public ResponseResult<List<CartVO>> 
		getListByUid(HttpSession session, String by, Integer[] cids) {
		Integer uid = getUidFromSession(session);
		List<CartVO> data;
		if ("cids".equals(by)) {
			data = cartService.getListByCids(cids);
		} else {
			data = cartService.getListByUid(uid);
		}
		return new ResponseResult<List<CartVO>>(SUCCESS, data);
	}
	
}










