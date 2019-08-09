package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IDistrictService;
@Service
public class DistrictServiceImpl implements IDistrictService{
	
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getListByParent(String parent) {
		return findListByParent(parent);
	}

	@Override
	public District getByCode(String code) {
		return findByCode(code);
	}

	/**
	 * 根据父级代号获取所有省/某省的所有市/某市的所有区的列表
	 * @param parent 父级代码，如果获取所有省的列表，则应该使用"86"
	 * @return 匹配的省/市/区的列表
	 */
	private List<District>findListByParent(String parent){
		return districtMapper.findListByParent(parent);
	}
	
	/**
	 * 根据省/市/区的代号获取详情
	 * @param code 省/市/区的代号
	 * @return 匹配的省/市/区的详情，如果没有匹配的数据，则返回null
	 */
	private District findByCode(String code){
		return districtMapper.findByCode(code);
	}
}
