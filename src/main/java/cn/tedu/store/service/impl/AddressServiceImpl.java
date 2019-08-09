package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

@Service
public class AddressServiceImpl implements IAddressService  {

	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	@Override
	public void addnew(Address address, String username) throws InsertException {
		//  确定district的值
		String district=getDistrictByCode(address.getProvince(),address.getCity(),address.getArea());
		address.setDistrict(district);

		//获取当前用户的收货地址数量
		Integer count =getCountByUid(address.getUid());
		//如果数量为0，则isDefault为1，否则，isDefault为0
		address.setIsDefault(count==0?1:0);
		//4项日志
		Date now=new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		//执行插入数据：insert(address)
		insert(address);
	}

	@Override
	@Transactional
	public List<Address> getListByUid(Integer uid) {
		return findListByUid(uid);
	}


	@Override
	public void setDefault(Integer uid, Integer aid, String username)
			throws UpdateException, AddressNotFoundException, AccessDeniedException {
		// 根据aid查询数据：findByAid(aid)
		Address data=findByAid(aid);
		System.err.println(data);
		// 判断查询结果是否为null
		if(data==null){
			// 是：抛出异常：AddressNotFoundException
			throw new AddressNotFoundException("设默认收货地址失败，您访问的数据不存在");
		}

		// 判断查询结果中的uid与当前方法参数中的uid是否不同
		if(!data.getUid().equals(uid)){
			// 是：抛出异常：AccessDeniedException		
			throw new AccessDeniedException("设默认收货地址失败，您的访问权限不足");
		}
		// 创建当前时间对象
		Date now=new Date();
		// 将所有地址设置为非默认：updateNonDefault(uid, modifiedUser, modifiedTime)
		updateNonDefault(uid, username, now);
		// 将指定地址设置为默认：updateDefault(aid, modifiedUser, modifiedTime)
		updateDefault(aid, username, now);
	}

	@Override
	public void deleteByAid(Integer aid, Integer uid, String username)
			throws DeleteException, AddressNotFoundException, AccessDeniedException {
		// 根据aid查询即将删除的数据
		Address data=findByAid(aid);
		System.out.println(data);
		// 判断查询结果是否为null
		if(data==null){
			// 是：抛出异常：AddressNotFoundException
			throw new AddressNotFoundException("删除收货地址失败，您访问的收货地址可能不存在");
		}
		// 判断查询结果的uid是否与参数uid不相同
		if(!data.getUid().equals(uid)){
			// 是：抛出异常：AccessDeniedException
			throw new AccessDeniedException("删除的收货地址失败，您访问的权限不足");
		}
		// 执行删除
		deleteByAid(aid);
		// 判断刚刚删除的数据（最开始找出来的数据）的isDefault是否为1
		if(data.getIsDefault().equals(1)){
			// -- 查询当前用户还有多少条收货地址
			Integer count=getCountByUid(uid);
			// -- 判断数量是否 > 0
			if(count>0){
				// -- 是：找出最后一条收货地址的aid
				Integer lastModifiedAid =findLastModifiedByUid(uid).getAid();
				// -- -- 把找出来的数据设置为默认
				Date now=new Date();
				updateDefault(lastModifiedAid, username, now);
			}
		}
	}







	/**
	 * 根据aid查询用户所有数据
	 * @param aid
	 * @return
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 */
	private void insert (Address address){
		Integer rows=addressMapper.insert(address);
		if(rows!=1){
			throw new InsertException("新增收货地址是出现未知错误！");
		}
	}
	
	/**
	 * 根据用户收货地址的id删除数据
	 * @param aid
	 */
	private void deleteByAid(Integer aid){
		Integer rows=addressMapper.deleteByAid(aid);
		if(rows!=1){
			throw new DeleteException("删除数据时出现未知错误");
		}
	}
	
	/**
	 * 根据用户id查询该用户的收货地址的数量
	 * @param uid 用户id
	 * @return 该用户的收货地址的数量，如果该用户当前没有收货地址数据，则返回0
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}

	/**
	 * 将指定用户的所有收货地址数据全部设置为"非默认"
	 * @param uid
	 * @param modifiedUser
	 * @param modifiedTime
	 */
	private void updateNonDefault(Integer uid,String modifiedUser,Date modifiedTime) {
		Integer rows=addressMapper.updateNonDefault(uid, modifiedUser, modifiedTime);
		if(rows==0){
			throw new UpdateException("更新收货地址时出现未知错误！");
		}
	}


	/**
	 * 将指定收货地址设为默认
	 * @param aid
	 * @param modifiedUser
	 * @param modifiedTim
	 */
	private void updateDefault(Integer aid,String modifiedUser,Date modifiedTim) {
		Integer rows=addressMapper.updateDefault(aid, modifiedUser, modifiedTim);
		if(rows!=1){
			throw new UpdateException("更新收货地址时出现未知错误！"); 
		}

	}

	/**
	 * 查询指定用户最后修改的收货地址的数据
	 * @param uid
	 * @return
	 */
	private Address findLastModifiedByUid(Integer uid){
		return addressMapper.findLastModifiedByUid(uid);
		
	}

	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address>findListByUid(Integer uid){

		return addressMapper.findListByUid(uid);

	}

	/**
	 * 根据省、市、区的代号，获取地区的名称
	 * @param province省的代号
	 * @param city市的代号
	 * @param area区的代号
	 * @return
	 */
	private String getDistrictByCode(String province, String city, String area) {
		String provinceName="NULL";
		String cityName="NULL";
		String areaName="NULL";
		District p=districtService.getByCode(province);
		if(p!=null){
			provinceName= p.getName();
		}
		District c=districtService.getByCode(city);
		if(p!=null){
			cityName= c.getName();
		}
		District a=districtService.getByCode(area);
		if(p!=null){
			areaName= a.getName();
		}
		return provinceName+cityName+areaName;
	}














}
