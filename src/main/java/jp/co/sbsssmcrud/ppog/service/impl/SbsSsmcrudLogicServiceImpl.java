package jp.co.sbsssmcrud.ppog.service.impl;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import jp.co.sbsssmcrud.ppog.dto.CityDto;
import jp.co.sbsssmcrud.ppog.entity.City;
import jp.co.sbsssmcrud.ppog.entity.CityInfo;
import jp.co.sbsssmcrud.ppog.mapper.CityInfoMapper;
import jp.co.sbsssmcrud.ppog.mapper.CityMapper;
import jp.co.sbsssmcrud.ppog.mapper.CountryMapper;
import jp.co.sbsssmcrud.ppog.mapper.LanguageMapper;
import jp.co.sbsssmcrud.ppog.service.SbsSsmcrudLogicService;
import jp.co.sbsssmcrud.ppog.utils.Messages;
import jp.co.sbsssmcrud.ppog.utils.Pagination;
import jp.co.sbsssmcrud.ppog.utils.RestMsg;
import jp.co.sbsssmcrud.ppog.utils.SecondBeanUtils;
import jp.co.sbsssmcrud.ppog.utils.StringUtils;
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
public class SbsSsmcrudLogicServiceImpl implements SbsSsmcrudLogicService {

	/**
	 * ページングナビゲーションのページ数
	 */
	private static final Integer NAVIGATION_PAGES = 5;

	/**
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = 8;

	/**
	 * デフォルトソート値
	 */
	private static final Integer SORT_NUMBER = 100;

	/**
	 * エラーメッセージ
	 */
	private static final String ERROR_MSG = "errorMsg";

	/**
	 * 都市マッパー
	 */
	private final CityMapper cityMapper;

	/**
	 * 都市情報マッパー
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
	public Integer checkDuplicate(final String cityName) {
		return this.cityMapper.checkDuplicatedName(cityName);
	}

	@Override
	public List<String> findAllContinents() {
		return this.countryMapper.findAllContinents();
	}

	@Override
	public String findLanguageByCty(final String nationVal) {
		final String nationCode = this.countryMapper.findNationCode(StringUtils.toHankaku(nationVal));
		return this.languageMapper.getOfficialLanguageByCountryCode(nationCode);
	}

	@Override
	public List<String> findNationsByCnt(final String continentVal) {
		if (StringUtils.isDigital(continentVal)) {
			final Integer id = Integer.parseInt(continentVal);
			final List<String> list = Lists.newArrayList();
			final CityInfo cityInfo = this.cityInfoMapper.selectById(id);
			final String nation = cityInfo.getNation();
			list.add(nation);
			final List<String> nations = this.countryMapper.findNationsByCnt(cityInfo.getContinent()).stream()
					.filter(item -> StringUtils.isNotEqual(item, nation)).toList();
			list.addAll(nations);
			return list;
		}
		return this.countryMapper.findNationsByCnt(continentVal);
	}

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
				if ((offset + PAGE_SIZE) >= sort) {
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
				if ((offset + PAGE_SIZE) >= sort) {
					return Pagination.of(maximumRanks.subList(offset, sort), maximumRanks.size(), pageNum, PAGE_SIZE,
							NAVIGATION_PAGES);
				}
				return Pagination.of(maximumRanks.subList(offset, offset + PAGE_SIZE), maximumRanks.size(), pageNum,
						PAGE_SIZE, NAVIGATION_PAGES);
			}
			// ページング検索；
			final String nationCode = this.countryMapper.findNationCode(hankakuKeyword);
			if (StringUtils.isNotEmpty(nationCode)) {
				final Integer cityInfosByNationCnt = this.cityInfoMapper.countCityInfosByNation(hankakuKeyword);
				if (cityInfosByNationCnt == 0) {
					return Pagination.of(Lists.newArrayList(), 0, pageNum);
				}
				final List<CityDto> cityInfosByNation = this.cityInfoMapper
						.getCityInfosByNation(hankakuKeyword, offset, PAGE_SIZE).stream()
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
					.getCityInfosByName(hankakuKeyword, offset, PAGE_SIZE).stream()
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
	public void removeById(final Integer id) {
		this.cityMapper.removeById(id);
		this.cityInfoMapper.refresh();
	}

	@Override
	public RestMsg save(final CityDto cityDto) {
		final City city = new City();
		final Integer saiban = this.cityMapper.saiban();
		final String countryCode = this.countryMapper.findNationCode(cityDto.nation());
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		city.setId(saiban);
		city.setCountryCode(countryCode);
		city.setDeleteFlg(Messages.MSG007);
		try {
			this.cityMapper.saveById(city);
			this.cityInfoMapper.refresh();
		} catch (final Exception e) {
			return RestMsg.failure().add(ERROR_MSG, Messages.MSG009);
		}
		return RestMsg.success(Messages.MSG011);
	}

	@Override
	public RestMsg update(final CityDto cityDto) {
		final CityInfo cityInfo = this.cityInfoMapper.selectById(cityDto.id());
		final City city = new City();
		SecondBeanUtils.copyNullableProperties(cityInfo, city);
		final String countryCode = this.countryMapper.findNationCode(cityInfo.getNation());
		city.setCountryCode(countryCode);
		final City originalEntity = new City();
		SecondBeanUtils.copyNullableProperties(city, originalEntity);
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		final String countryCode2 = this.countryMapper.findNationCode(cityDto.nation());
		city.setCountryCode(countryCode2);
		if (originalEntity.equals(city)) {
			return RestMsg.failure().add(ERROR_MSG, Messages.MSG012);
		}
		try {
			this.cityMapper.updateById(city);
			this.cityInfoMapper.refresh();
		} catch (final Exception e) {
			return RestMsg.failure().add(ERROR_MSG, Messages.MSG009);
		}
		return RestMsg.success(Messages.MSG010);
	}
}
