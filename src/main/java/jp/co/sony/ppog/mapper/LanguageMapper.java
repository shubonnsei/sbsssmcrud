package jp.co.sony.ppog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 言語マッパー
 *
 * @author shubonnsei
 * @since 1.00
 */
@Mapper
public interface LanguageMapper {

	/**
	 * 国家コードによって公用語を取得する
	 * 
	 * @param nationCode 国家コード
	 * @return String
	 */
	String getOfficialLanguageByCountryCode(@Param("countryCode") String nationCode);
}
