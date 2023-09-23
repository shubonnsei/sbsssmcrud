package jp.co.sony.ppog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sony.ppog.entity.City;
import jp.co.sony.ppog.utils.Pagination;

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
	Long saiban();

	/**
	 * 論理削除
	 *
	 * @param id id of the selected city
	 */
	void removeById(@Param("id") Long id);

	/**
	 * 人口数量昇順で都市情報を検索する
	 *
	 * @param sort ソート
	 * @return List<City>
	 */
	List<City> findMinimumRanks(@Param("sortNumber") Integer sort);

	/**
	 * 人口数量降順で都市情報を検索する
	 *
	 * @param sort ソート
	 * @return List<City>
	 */
	List<City> findMaximumRanks(@Param("sortNumber") Integer sort);

	/**
	 * すべての都市情報を取得する
	 *
	 * @param pageable ページングパラメータ
	 * @return Page<City>
	 */
	Pagination<City> getCityInfos();
}
