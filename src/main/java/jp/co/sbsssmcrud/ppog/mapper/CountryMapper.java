package jp.co.sbsssmcrud.ppog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 国家マッパー
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
	 * 国名によって国家コードを抽出する
	 *
	 * @param nationVal 国名
	 * @return String
	 */
	String findNationCode(@Param("nation") String nationVal);

	/**
	 * 選択された大陸の上にすべての国の情報を取得する
	 *
	 * @param continent 大陸名
	 * @return List<String>
	 */
	List<String> findNationsByCnt(@Param("continent") String continent);
}
