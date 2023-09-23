package jp.co.sony.ppog.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.sony.ppog.dto.CityDto;
import jp.co.sony.ppog.entity.City;
import jp.co.sony.ppog.entity.Country;
import jp.co.sony.ppog.entity.Language;
import jp.co.sony.ppog.repository.CityRepository;
import jp.co.sony.ppog.repository.CountryRepository;
import jp.co.sony.ppog.repository.LanguageRepository;
import jp.co.sony.ppog.service.CentreLogicService;
import jp.co.sony.ppog.utils.Messages;
import jp.co.sony.ppog.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 中央処理サービス実装クラス
 *
 * @author ArcaHozota
 * @since 4.40
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CentreLogicServiceImpl implements CentreLogicService {

	/**
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = 12;

	/**
	 * デフォルトソート値
	 */
	private static final Integer SORT_NUMBER = 100;

	/**
	 * 都市リポジトリ
	 */
	private final CityRepository cityRepository;

	/**
	 * 国家リポジトリ
	 */
	private final CountryRepository countryRepository;

	/**
	 * 言語リポジトリ
	 */
	private final LanguageRepository languageRepository;

	@Override
	public CityDto getCityInfoById(final Long id) {
		final CityDto cityDto = new CityDto();
		final City city = this.cityRepository.findById(id).orElseGet(City::new);
		final String language = this.getLanguage(city.getCountryCode());
		BeanUtils.copyProperties(city, cityDto);
		cityDto.setContinent(city.getCountry().getContinent());
		cityDto.setNation(city.getCountry().getName());
		cityDto.setLanguage(language);
		return cityDto;
	}

	@Override
	public Page<CityDto> getPageInfo(final Integer pageNum, final String keyword) {
		// ページングコンストラクタを宣言する；
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_SIZE, Sort.by(Direction.ASC, "id"));
		// キーワードの属性を判断する；
		if (StringUtils.isNotEmpty(keyword)) {
			final String hankakuKeyword = StringUtils.toHankaku(keyword);
			final int pageMin = PAGE_SIZE * (pageNum - 1);
			final int pageMax = PAGE_SIZE * pageNum;
			int sort = SORT_NUMBER;
			if (hankakuKeyword.startsWith("min(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量昇順で最初の15個都市の情報を吹き出します；
				final List<CityDto> minimumRanks = this.cityRepository.findMinimumRanks(sort).stream().map(item -> {
					final CityDto cityDto = new CityDto();
					BeanUtils.copyProperties(item, cityDto);
					final City city = this.cityRepository.findById(item.getId()).orElseGet(City::new);
					final Country country = this.countryRepository.findById(city.getCountryCode())
							.orElseGet(Country::new);
					final String language = this.getLanguage(item.getCountryCode());
					cityDto.setContinent(country.getContinent());
					cityDto.setNation(country.getName());
					cityDto.setLanguage(language);
					return cityDto;
				}).collect(Collectors.toList());
				if (pageMax >= sort) {
					return new PageImpl<>(minimumRanks.subList(pageMin, sort), pageRequest, minimumRanks.size());
				}
				return new PageImpl<>(minimumRanks.subList(pageMin, pageMax), pageRequest, minimumRanks.size());
			}
			if (hankakuKeyword.startsWith("max(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量降順で最初の15個都市の情報を吹き出します；
				final List<CityDto> maximumRanks = this.cityRepository.findMaximumRanks(sort).stream().map(item -> {
					final CityDto cityDto = new CityDto();
					BeanUtils.copyProperties(item, cityDto);
					final City city = this.cityRepository.findById(item.getId()).orElseGet(City::new);
					final Country country = this.countryRepository.findById(city.getCountryCode())
							.orElseGet(Country::new);
					final String language = this.getLanguage(item.getCountryCode());
					cityDto.setContinent(country.getContinent());
					cityDto.setNation(country.getName());
					cityDto.setLanguage(language);
					return cityDto;
				}).collect(Collectors.toList());
				if (pageMax >= sort) {
					return new PageImpl<>(maximumRanks.subList(pageMin, sort), pageRequest, maximumRanks.size());
				}
				return new PageImpl<>(maximumRanks.subList(pageMin, pageMax), pageRequest, maximumRanks.size());
			}
			// ページング検索；
			final City city = new City();
			final String nationCode = this.countryRepository.findNationCode(hankakuKeyword);
			if (StringUtils.isNotEmpty(nationCode)) {
				city.setCountryCode(nationCode);
				city.setDeleteFlg(Messages.MSG007);
				final Example<City> example = Example.of(city, ExampleMatcher.matchingAll());
				final Page<City> pages = this.cityRepository.findAll(example, pageRequest);
				return this.getCityInfoDtos(pages, pageRequest, pages.getTotalElements());
			}
			city.setName(hankakuKeyword);
			city.setDeleteFlg(Messages.MSG007);
			final ExampleMatcher matcher = ExampleMatcher.matching()
					.withMatcher("name", GenericPropertyMatchers.contains())
					.withMatcher("deleteFlg", GenericPropertyMatchers.exact());
			final Example<City> example = Example.of(city, matcher);
			final Page<City> pages = this.cityRepository.findAll(example, pageRequest);
			return this.getCityInfoDtos(pages, pageRequest, pages.getTotalElements());
		}
		// ページング検索；
		final Page<City> pages = this.cityRepository.getCityInfos(pageRequest);
		return this.getCityInfoDtos(pages, pageRequest, pages.getTotalElements());
	}

	@Override
	public List<String> getListOfNationsById(final Long id) {
		final List<String> list = new ArrayList<>();
		final City city = this.cityRepository.findById(id).orElseGet(City::new);
		final String nationName = city.getCountry().getName();
		list.add(nationName);
		final List<String> nations = this.countryRepository.findNationsByCnt(city.getCountry().getContinent()).stream()
				.filter(item -> StringUtils.isNotEqual(item, nationName)).collect(Collectors.toList());
		list.addAll(nations);
		return list;
	}

	@Override
	public void update(final CityDto cityDto) {
		final City city = this.cityRepository.findById(cityDto.getId()).orElseGet(City::new);
		final String countryCode = this.countryRepository.findNationCode(cityDto.getNation());
		city.setCountryCode(countryCode);
		city.setName(cityDto.getName());
		city.setDistrict(cityDto.getDistrict());
		city.setPopulation(cityDto.getPopulation());
		this.cityRepository.save(city);
	}

	@Override
	public void save(final CityDto cityDto) {
		final City city = new City();
		BeanUtils.copyProperties(cityDto, city, "continent", "nation", "language");
		final Long saiban = this.cityRepository.saiban();
		final String countryCode = this.countryRepository.findNationCode(cityDto.getNation());
		city.setId(saiban);
		city.setCountryCode(countryCode);
		city.setDeleteFlg(Messages.MSG007);
		this.cityRepository.save(city);
	}

	@Override
	public void removeById(final Long id) {
		this.cityRepository.removeById(id);
	}

	@Override
	public List<String> findAllContinents() {
		return this.countryRepository.findAllContinents();
	}

	@Override
	public List<String> findNationsByCnt(final String continentVal) {
		final String hankaku = StringUtils.toHankaku(continentVal);
		return this.countryRepository.findNationsByCnt(hankaku);
	}

	@Override
	public String findLanguageByCty(final String nationVal) {
		final String nationCode = this.countryRepository.findNationCode(StringUtils.toHankaku(nationVal));
		return this.getLanguage(nationCode);
	}

	@Override
	public List<City> checkDuplicate(final String cityName) {
		final City city = new City();
		city.setName(StringUtils.toHankaku(cityName));
		city.setDeleteFlg(Messages.MSG007);
		final Example<City> example = Example.of(city, ExampleMatcher.matchingAll());
		return this.cityRepository.findAll(example);
	}

	private Page<CityDto> getCityInfoDtos(final Page<City> pages, final Pageable pageable, final Long total) {
		final List<CityDto> cityDtos = pages.getContent().stream().map(item -> {
			final CityDto cityDto = new CityDto();
			BeanUtils.copyProperties(item, cityDto);
			final String language = this.getLanguage(item.getCountryCode());
			cityDto.setContinent(item.getCountry().getContinent());
			cityDto.setNation(item.getCountry().getName());
			cityDto.setLanguage(language);
			return cityDto;
		}).collect(Collectors.toList());
		return new PageImpl<>(cityDtos, pageable, total);
	}

	private String getLanguage(final String nationCode) {
		final Specification<Language> specification1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("countryCode"), nationCode);
		final Specification<Language> specification2 = (root, query, criteriaBuilder) -> {
			query.orderBy(criteriaBuilder.desc(root.get("percentage")));
			return criteriaBuilder.equal(root.get("deleteFlg"), Messages.MSG007);
		};
		final Specification<Language> languageSpecification = Specification.where(specification1).and(specification2);
		final List<Language> languages = this.languageRepository.findAll(languageSpecification);
		if (languages.size() == 1) {
			return languages.get(0).getName();
		}
		final List<Language> officialLanguages = languages.stream()
				.filter(al -> StringUtils.isEqual("T", al.getIsOfficial())).collect(Collectors.toList());
		final List<Language> typicalLanguages = languages.stream()
				.filter(al -> StringUtils.isEqual("F", al.getIsOfficial())).collect(Collectors.toList());
		if (officialLanguages.isEmpty() && !typicalLanguages.isEmpty()) {
			return typicalLanguages.get(0).getName();
		}
		if (!officialLanguages.isEmpty() && typicalLanguages.isEmpty()) {
			return officialLanguages.get(0).getName();
		}
		final Language language1 = officialLanguages.get(0);
		final Language language2 = typicalLanguages.get(0);
		if (language2.getPercentage().subtract(language1.getPercentage()).compareTo(BigDecimal.valueOf(35L)) <= 0) {
			return language1.getName();
		}
		return language2.getName();
	}
}
