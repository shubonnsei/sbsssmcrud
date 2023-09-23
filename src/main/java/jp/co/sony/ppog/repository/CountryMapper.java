package jp.co.sony.ppog.repository;

import java.util.List;

/**
 * 国家リポジトリ
 *
 * @author shubonnsei
 * @since 1.00
 */
@Mapper
public interface CountryMapper {

	/**
	 * 大陸の集合を取得する
	 *
	 * @return List<String>
	 */
	List<String> findAllContinents();

	/**
	 * 選択された大陸の上にすべての国の情報を取得する
	 *
	 * @param continent 大陸名
	 * @return List<String>
	 */
	List<String> findNationsByCnt(@Param("continent") String continent);

	/**
	 * 国名によって国家コードを抽出する
	 *
	 * @param nationVal 国名
	 * @return String
	 */
	String findNationCode(@Param("nation") String nationVal);
}
