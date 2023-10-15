package jp.co.sbsssmcrud.ppog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.sbsssmcrud.ppog.entity.CityInfo;

public interface CityInfoMapper {

	/**
	 * すべての都市情報のレコード数を取得する
	 *
	 * @return Integer
	 */
	Integer countCityInfos();

	/**
	 * 都市名によって都市情報のレコード数を取得する
	 *
	 * @param name 都市名
	 * @return Integer
	 */
	Integer countCityInfosByName(@Param("name") String name);

	/**
	 * 国名によって都市情報のレコード数を取得する
	 *
	 * @param nationCode
	 * @return Integer
	 */
	Integer countCityInfosByNation(@Param("nation") String nationCode);

	/**
	 * 人口数量降順で都市情報を検索する
	 *
	 * @param sortNumber ソート
	 * @return List<City>
	 */
	List<CityInfo> findMaximumRanks(@Param("sort") Integer sortNumber);

	/**
	 * 人口数量昇順で都市情報を検索する
	 *
	 * @param sortNumber ソート
	 * @return List<City>
	 */
	List<CityInfo> findMinimumRanks(@Param("sort") Integer sortNumber);

	/**
	 * すべての都市情報を取得する
	 *
	 * @return List<City>
	 */
	List<CityInfo> getCityInfos(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

	/**
	 * 都市名によって都市情報を取得する
	 *
	 * @param name 都市名
	 * @return List<City>
	 */
	List<CityInfo> getCityInfosByName(@Param("name") String name, @Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	/**
	 * 国名によって都市情報を取得する
	 *
	 * @param nationCode
	 * @return Integer
	 */
	List<CityInfo> getCityInfosByNation(@Param("nation") String nationCode, @Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	/**
	 * 都市IDによって都市情報を取得する
	 *
	 * @param id 都市ID
	 * @return City
	 */
	CityInfo selectById(@Param("id") Integer id);
}
