package jp.co.sony.ppog.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 言語テーブルのエンティティ
 *
 * @author shubonnsei
 * @since 1.00
 */
@Data
public final class Language implements Serializable {

	private static final long serialVersionUID = -8085659909634431823L;

	/**
	 * This field corresponds to the database column COUNTRY_CODE
	 */
	private String countryCode;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	private String name;

	/**
	 * This field corresponds to the database column IS_OFFICIAL
	 */
	private String isOfficial;

	/**
	 * This field corresponds to the database column PERCENTAGE
	 */
	private BigDecimal percentage;

	/**
	 * This field corresponds to the database column DELETE_FLG
	 */
	private String deleteFlg;
}
