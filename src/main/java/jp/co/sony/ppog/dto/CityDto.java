package jp.co.sony.ppog.dto;

import lombok.Data;

/**
 * 都市情報データ交換クラス
 *
 * @author ArcaHozota
 * @since 3.69
 */
@Data
public final class CityDto {

	/**
	 * 都市ID
	 */
	private Long id;

	/**
	 * 都市名
	 */
	private String name;

	/**
	 * 大陸
	 */
	private String continent;

	/**
	 * 国家
	 */
	private String nation;

	/**
	 * 地域
	 */
	private String district;

	/**
	 * 人口
	 */
	private Long population;

	/**
	 * 公用語
	 */
	private String language;
}
