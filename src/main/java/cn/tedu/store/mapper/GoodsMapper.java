package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 处理商品数据的持久层接口
 * @author 12482
 *
 */
public interface GoodsMapper {
	/**
	 * 获取热销商品的列表
	 * @param count需要获取的商品的数量
	 * @return热销商品的列表
	 */
	List<Goods>findHotGoods(Integer count);
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	Goods findById(Long id);
}
