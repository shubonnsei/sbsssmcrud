package jp.co.sony.ppog.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 国家テーブルのエンティティ
 *
 * @author ArcaHozota
 * @since 1.00beta
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "country")
@Proxy(lazy = false)
@NamedQuery(name = "Country.findNationCode", query = "select n.code from Country as n where n.deleteFlg = 'visible' and n.name =:nation")
@NamedQuery(name = "Country.findAllContinents", query = "select distinct n.continent from Country as n where n.deleteFlg = 'visible' order by n.continent asc")
@NamedQuery(name = "Country.findNationsByCnt", query = "select distinct n.name from Country as n where n.deleteFlg = 'visible' and n.continent =:continent order by n.name asc")
public final class Country implements Serializable {

	private static final long serialVersionUID = 6762395398373991166L;

	/**
	 * This field corresponds to the database column CODE
	 */
	@Id
	private String code;

	/**
	 * This field corresponds to the database column NAME
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * This field corresponds to the database column CONTINENT
	 */
	@Column(nullable = false)
	private String continent;

	/**
	 * This field corresponds to the database column REGION
	 */
	@Column(nullable = false)
	private String region;

	/**
	 * This field corresponds to the database column SURFACE_AREA
	 */
	@Column(nullable = false)
	private BigDecimal surfaceArea;

	/**
	 * This field corresponds to the database column INDEPENDENCE_YEAR
	 */
	private Long independenceYear;

	/**
	 * This field corresponds to the database column POPULATION
	 */
	@Column(nullable = false)
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
	@Column(nullable = false)
	private String localName;

	/**
	 * This field corresponds to the database column GOVERNMENT_FORM
	 */
	@Column(nullable = false)
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
	@Column(nullable = false)
	private String code2;

	/**
	 * This field corresponds to the database column LOGIC_DELETE_FLG
	 */
	@Column(nullable = false)
	private String deleteFlg;

	/**
	 * This field corresponds to the database table city
	 */
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private List<City> cities;
}
