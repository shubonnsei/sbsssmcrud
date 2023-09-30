package jp.co.sony.ppog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.postgresql.util.PSQLException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import jp.co.sony.ppog.dto.CityDto;
import jp.co.sony.ppog.entity.City;
import jp.co.sony.ppog.entity.CityInfo;
import jp.co.sony.ppog.mapper.CityInfoMapper;
import jp.co.sony.ppog.mapper.CityMapper;
import jp.co.sony.ppog.mapper.CountryMapper;
import jp.co.sony.ppog.mapper.LanguageMapper;
import jp.co.sony.ppog.service.SbsSsmCrudLogicService;
import jp.co.sony.ppog.utils.Messages;
import jp.co.sony.ppog.utils.Pagination;
import jp.co.sony.ppog.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 中央処理サービス実装クラス
 *
 * @author shubonnsei
 * @since 1.00
 */
@Service
@Transactional(rollbackFor = PSQLException.class)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SbsSsmCrudLogicServiceImpl implements SbsSsmCrudLogicService {

	/**
	 * ページングナビゲーションのページ数
	 */
	private static final Integer NAVIGATION_PAGES = 7;
	
	/**
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = 8;

	/**
	 * デフォルトソート値
	 */
	private static final Integer SORT_NUMBER = 100;

	/**
	 * 都市マッパー
	 */
	private final CityMapper cityMapper;

	/**
	 * 都市マッパー
	 */
	private final CityInfoMapper cityInfoMapper;

	/**
	 * 国家マッパー
	 */
	private final CountryMapper countryMapper;

	/**
	 * 言語マッパー
	 */
	private final LanguageMapper languageMapper;

	@Override
	public CityDto getCityInfoById(final Integer id) {
		final CityInfo cityInfo = this.cityInfoMapper.selectById(id);
		return new CityDto(cityInfo.getId(), cityInfo.getName(), cityInfo.getContinent(), cityInfo.getNation(),
				cityInfo.getDistrict(), cityInfo.getPopulation(), cityInfo.getLanguage());
	}

	@Override
	public Pagination<CityDto> getPageInfo(final Integer pageNum, final String keyword) {
		int sort = SORT_NUMBER;
		final int offset = PAGE_SIZE * (pageNum - 1);
		// キーワードの属性を判断する；
		if (StringUtils.isNotEmpty(keyword)) {
			final String hankakuKeyword = StringUtils.toHankaku(keyword);
			if (hankakuKeyword.startsWith("min(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量昇順で最初の15個都市の情報を吹き出します；
				final List<CityDto> minimumRanks = this.cityInfoMapper.findMinimumRanks(sort).stream()
						.map(item -> new CityDto(item.getId(), item.getName(), item.getContinent(), item.getNation(),
								item.getDistrict(), item.getPopulation(), item.getLanguage()))
						.toList();
				if (offset + PAGE_SIZE >= sort) {
					return Pagination.of(minimumRanks.subList(offset, sort), minimumRanks.size(), pageNum, PAGE_SIZE,
							NAVIGATION_PAGES);
				}
				return Pagination.of(minimumRanks.subList(offset, offset + PAGE_SIZE), minimumRanks.size(), pageNum,
						PAGE_SIZE, NAVIGATION_PAGES);
			}
			if (hankakuKeyword.startsWith("max(pop)")) {
				final int indexOf = hankakuKeyword.indexOf(")");
				final String keisan = hankakuKeyword.substring(indexOf + 1);
				if (StringUtils.isNotEmpty(keisan)) {
					sort = Integer.parseInt(keisan);
				}
				// 人口数量降順で最初の15個都市の情報を吹き出します；
				final List<CityDto> maximumRanks = this.cityInfoMapper.findMaximumRanks(sort).stream()
						.map(item -> new CityDto(item.getId(), item.getName(), item.getContinent(), item.getNation(),
								item.getDistrict(), item.getPopulation(), item.getLanguage()))
						.toList();
				if (offset + PAGE_SIZE >= sort) {
					return Pagination.of(maximumRanks.subList(offset, sort), maximumRanks.size(), pageNum, PAGE_SIZE,
							NAVIGATION_PAGES);
				}
				return Pagination.of(maximumRanks.subList(offset, offset + PAGE_SIZE), maximumRanks.size(), pageNum,
						PAGE_SIZE, NAVIGATION_PAGES);
			}
			// ページング検索；
			final String nationCode = this.countryMapper.findNationCode(hankakuKeyword);
			if (StringUtils.isNotEmpty(nationCode)) {
				final Integer cityInfosByNationCnt = this.cityInfoMapper.countCityInfosByNation(nationCode);
				if (cityInfosByNationCnt == 0) {
					return Pagination.of(Lists.newArrayList(), 0, pageNum);
				}
				final List<CityDto> cityInfosByNation = this.cityInfoMapper
						.getCityInfosByNation(nationCode, offset, PAGE_SIZE).stream()
						.map(item -> new CityDto(item.getId(), item.getName(), item.getContinent(), item.getNation(),
								item.getDistrict(), item.getPopulation(), item.getLanguage()))
						.toList();
				return Pagination.of(cityInfosByNation, cityInfosByNationCnt, pageNum, PAGE_SIZE, NAVIGATION_PAGES);
			}
			final Integer cityInfosByNameCnt = this.cityInfoMapper.countCityInfosByName(hankakuKeyword);
			if (cityInfosByNameCnt == 0) {
				return Pagination.of(Lists.newArrayList(), 0, pageNum);
			}
			final List<CityDto> cityInfosByName = this.cityInfoMapper
					.getCityInfosByName(hankakuKeyword, offset, PAGE_SIZE)
					.stream()
					.map(item -> new CityDto(item.getId(), item.getName(), item.getContinent(), item.getNation(),
							item.getDistrict(), item.getPopulation(), item.getLanguage()))
					.toList();
			return Pagination.of(cityInfosByName, cityInfosByNameCnt, pageNum, PAGE_SIZE, NAVIGATION_PAGES);
		}
		final Integer cityInfosCnt = this.cityInfoMapper.countCityInfos();
		if (cityInfosCnt == 0) {
			return Pagination.of(Lists.newArrayList(), 0, pageNum);
		}
		// ページング検索；
		final List<CityDto> cityInfos = this.cityInfoMapper.getCityInfos(offset, PAGE_SIZE).stream()
				.map(item -> new CityDto(item.getId(), item.getName(), item.getContinent(), item.getNation(),
						item.getDistrict(), item.getPopulation(), item.getLanguage()))
				.toList();
		return Pagination.of(cityInfos, cityInfosCnt, pageNum, PAGE_SIZE, NAVIGATION_PAGES);
	}

	@Override
	public List<String> getListOfNationsById(final Integer id) {
		final List<String> list = new ArrayList<>();
		final CityInfo cityInfo = this.cityInfoMapper.selectById(id);
		final String nation = cityInfo.getNation();
		list.add(nation);
		final List<String> nations = this.countryMapper.findNationsByCnt(cityInfo.getContinent()).stream()
				.filter(item -> StringUtils.isNotEqual(item, nation)).collect(Collectors.toList());
		list.addAll(nations);
		return list;
	}

	@Override
	public void save(final CityDto cityDto) {
		final City city = new City();
		BeanUtils.copyProperties(cityDto, city, "continent", "nation", "language");
		final Integer saiban = this.cityMapper.saiban();
		final String countryCode = this.countryMapper.findNationCode(cityDto.nation());
		city.setId(saiban);
		city.setCountryCode(countryCode);
		city.setDeleteFlg(Messages.MSG007);
		this.cityMapper.saveById(city);
	}

	@Override
	public void update(final CityDto cityDto) {
		final City city = new City();
		BeanUtils.copyProperties(cityDto, city, "continent", "nation", "language");
		final String countryCode = this.countryMapper.findNationCode(cityDto.nation());
		city.setCountryCode(countryCode);
		this.cityMapper.updateById(city);
	}

	@Override
	public void removeById(final Integer id) {
		this.cityMapper.removeById(id);
	}

	@Override
	public List<String> findAllContinents() {
		return this.countryMapper.findAllContinents();
	}

	@Override
	public List<String> findNationsByCnt(final String continentVal) {
		final String hankaku = StringUtils.toHankaku(continentVal);
		return this.countryMapper.findNationsByCnt(hankaku);
	}

	@Override
	public String findLanguageByCty(final String nationVal) {
		final String nationCode = this.countryMapper.findNationCode(StringUtils.toHankaku(nationVal));
		return this.languageMapper.getOfficialLanguageByCountryCode(nationCode);
	}

	@Override
	public Integer checkDuplicate(final String cityName) {
		return this.cityMapper.checkDuplicatedName(cityName);
	}
}
