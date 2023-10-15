package jp.co.sbsssmcrud.ppog.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 都市情報マテリアライズドビューのエンティティ
 *
 * @author shubonnsei
 * @since 2.68
 */
@Data
public final class CityInfo implements Serializable {

	private static final long serialVersionUID = 3849030013295718968L;

	/**
	 * This field corresponds to the database column ID
	 */
	private Integer id;

	/**
	 * This field corresponds to the database column NAME
	 */
	private String name;

	/**
	 * This field corresponds to the database column CONTINENT
	 */
	private String continent;

	/**
	 * This field corresponds to the database column NATION
	 */
	private String nation;

	/**
	 * This field corresponds to the database column DISTRICT
	 */
	private String district;

	/**
	 * This field corresponds to the database column POPULATION
	 */
	private Integer population;

	/**
	 * This field corresponds to the database column LANGUAGE
	 */
	private String language;
}
