package jp.co.sony.ppog.mapper;

import java.util.List;

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
	 * 都市IDによって都市情報を取得する
	 *
	 * @param id 都市ID
	 * @return City
	 */
	City selectById(@Param("id") Integer id);

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
	 * 人口数量降順で都市情報を検索する
	 *
	 * @param sortNumber ソート
	 * @return List<City>
	 */
	List<City> findMaximumRanks(@Param("sort") Integer sortNumber);

	/**
	 * 人口数量昇順で都市情報を検索する
	 *
	 * @param sortNumber ソート
	 * @return List<City>
	 */
	List<City> findMinimumRanks(@Param("sort") Integer sortNumber);

	/**
	 * 国名によって都市情報を取得する
	 *
	 * @param nationCode
	 * @return Integer
	 */
	List<City> getCityInfosByNation(@Param("nation") String nationCode, @Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	/**
	 * 国名によって都市情報のレコード数を取得する
	 *
	 * @param nationCode
	 * @return Integer
	 */
	Integer countCityInfosByNation(@Param("nation") String nationCode);

	/**
	 * 都市名によって都市情報を取得する
	 *
	 * @param name 都市名
	 * @return List<City>
	 */
	List<City> getCityInfosByName(@Param("name") String name, @Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	/**
	 * 都市名によって都市情報のレコード数を取得する
	 *
	 * @param name 都市名
	 * @return Integer
	 */
	Integer countCityInfosByName(@Param("name") String name);

	/**
	 * すべての都市情報を取得する
	 *
	 * @return List<City>
	 */
	List<City> getCityInfos(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

	/**
	 * すべての都市情報のレコード数を取得する
	 *
	 * @return Integer
	 */
	Integer countCityInfos();

	/**
	 * 都市名は重複するかどうかを判断する
	 *
	 * @param cityName 都市名
	 * @return Integer
	 */
	Integer checkDuplicatedName(@Param("cityName") String cityName);

}
