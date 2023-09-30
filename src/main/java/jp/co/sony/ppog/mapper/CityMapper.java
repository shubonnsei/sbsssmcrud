package jp.co.sony.ppog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sony.ppog.entity.City;

/**
 * 都市マッパー
 *
 * @author shubonnsei
 * @since 1.00
 */
@Mapper
public interface CityMapper {

	/**
	 * 採番を行います
	 *
	 * @return 採番値
	 */
	Integer saiban();

	/**
	 * 保存
	 *
	 * @param city 都市情報
	 */
	void saveById(City city);

	/**
	 * 更新
	 *
	 * @param city 都市エンティティ
	 */
	void updateById(City city);

	/**
	 * 論理削除
	 *
	 * @param id id of the selected city
	 */
	void removeById(@Param("id") Integer id);

	/**
	 * 都市名は重複するかどうかを判断する
	 *
	 * @param cityName 都市名
	 * @return Integer
	 */
	Integer checkDuplicatedName(@Param("cityName") String cityName);
}
