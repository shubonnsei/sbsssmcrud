package jp.co.sony.ppog.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

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
	@Id
	private String countryCode;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	@Id
	@Column(name = "LANGUAGE")
	private String name;

	/**
	 * This field corresponds to the database column IS_OFFICIAL
	 */
	@Column(nullable = false)
	private String isOfficial;

	/**
	 * This field corresponds to the database column PERCENTAGE
	 */
	@Column(nullable = false)
	private BigDecimal percentage;

	/**
	 * This field corresponds to the database column LOGIC_DELETE_FLG
	 */
	@Column(nullable = false)
	private String deleteFlg;
}
