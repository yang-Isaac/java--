package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

public interface DistrictMapper {
	
	/**
	 * 根据父级代号获取所有省/某省的所有市/某市的所有区的列表
	 * @param parent 父级代码，如果获取所有省的列表，则应该使用"86"
	 * @return 匹配的省/市/区的列表
	 */
	List<District>findListByParent(String parent);
	
	/**
	 * 根据省/市/区的代号获取详情
	 * @param code 省/市/区的代号
	 * @return 匹配的省/市/区的详情，如果没有匹配的数据，则返回null
	 */
	District findByCode(String code);
}
