package jp.co.sony.ppog.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 国家テーブルのエンティティ
 *
 * @author shubonnsei
 * @since 1.00
 */
@Data
public final class Country implements Serializable {

	private static final long serialVersionUID = 6762395398373991166L;

	/**
	 * This field corresponds to the database column CODE
	 */
	private String code;

	/**
	 * This field corresponds to the database column NAME
	 */
	private String name;

	/**
	 * This field corresponds to the database column CONTINENT
	 */
	private String continent;

	/**
	 * This field corresponds to the database column REGION
	 */
	private String region;

	/**
	 * This field corresponds to the database column SURFACE_AREA
	 */
	private BigDecimal surfaceArea;

	/**
	 * This field corresponds to the database column INDEPENDENCE_YEAR
	 */
	private Long independenceYear;

	/**
	 * This field corresponds to the database column POPULATION
	 */
	private Long population;

	/**
	 * This field corresponds to the database column LIFE_EXPECTANCY
	 */
	private Long lifeExpectancy;

	/**
	 * This field corresponds to the database column GNP
	 */
	private BigDecimal gnp;

	/**
	 * This field corresponds to the database column GNP_OLD
	 */
	private BigDecimal gnpOld;

	/**
	 * This field corresponds to the database column LOCAL_NAME
	 */
	private String localName;

	/**
	 * This field corresponds to the database column GOVERNMENT_FORM
	 */
	private String governmentForm;

	/**
	 * This field corresponds to the database column HEAD_OF_STATE
	 */
	private String headOfState;

	/**
	 * This field corresponds to the database column CAPITAL
	 */
	private Long capital;

	/**
	 * This field corresponds to the database column CODE2
	 */
	private String code2;

	/**
	 * This field corresponds to the database column LOGIC_DELETE_FLG
	 */
	private String deleteFlg;
}
