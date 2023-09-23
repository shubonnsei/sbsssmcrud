package jp.co.sony.ppog.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sony.ppog.entity.City;

/**
 * 都市リポジトリ
 *
 * @author ArcaHozota
 * @since 3.66
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

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
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void removeById(@Param("id") Long id);

	/**
	 * 人口数量昇順で都市情報を検索する
	 *
	 * @param sort ソート
	 * @return List<City>
	 */
	@Query(value = "select cn.id, cn.name, cn.country_code, cn.district, cn.population from city as cn "
			+ "where cn.delete_flg = 'visible' order by cn.population limit :sortNumber", nativeQuery = true)
	List<City> findMinimumRanks(@Param("sortNumber") Integer sort);

	/**
	 * 人口数量降順で都市情報を検索する
	 *
	 * @param sort ソート
	 * @return List<City>
	 */
	@Query(value = "select cn.id, cn.name, cn.country_code, cn.district, cn.population from city as cn "
			+ "where cn.delete_flg = 'visible' order by cn.population desc limit :sortNumber", nativeQuery = true)
	List<City> findMaximumRanks(@Param("sortNumber") Integer sort);

	/**
	 * すべての都市情報を取得する
	 *
	 * @param pageable ページングパラメータ
	 * @return Page<City>
	 */
	Page<City> getCityInfos(Pageable pageable);
}
