package jp.co.sony.ppog.service;

import java.util.List;

import org.springframework.data.domain.Page;

import jp.co.sony.ppog.dto.CityDto;
import jp.co.sony.ppog.entity.City;

/**
 * 中央処理サービスインターフェス
 *
 * @author shubonnsei
 * @since 1.00
 */
public interface CentreLogicService {

	/**
	 * パージング情報を抽出する
	 *
	 * @param pageNum ページングナンバー
	 * @param keyword 検索キーワード
	 * @return Page<CityInfoDto>
	 */
	Page<CityDto> getPageInfo(Integer pageNum, String keyword);

	/**
	 * 都市IDによって情報を抽出する
	 *
	 * @param id 都市ID
	 * @return CityInfoDto
	 */
	CityDto getCityInfoById(Long id);

	/**
	 * 都市IDによって国家名を抽出する
	 *
	 * @param id 都市ID
	 * @return List<String>
	 */
	List<String> getListOfNationsById(Long id);

	/**
	 * 入力した都市情報を保存する
	 *
	 * @param cityInfoDto 都市情報
	 */
	void save(CityDto cityInfoDto);

	/**
	 * 入力した都市情報を更新する
	 *
	 * @param cityInfoDto 都市情報
	 */
	void update(CityDto cityInfoDto);

	/**
	 * 都市IDによって情報を削除する
	 *
	 * @param id 都市ID
	 */
	void removeById(Long id);

	/**
	 * 大陸情報を取得する
	 *
	 * @return List<String>
	 */
	List<String> findAllContinents();

	/**
	 * 指定された大陸に位置するすべての国を取得する
	 *
	 * @param continentVal 大陸名称
	 * @return List<String>
	 */
	List<String> findNationsByCnt(String continentVal);

	/**
	 * 指定された国の公用語を取得する
	 *
	 * @param nationVal 国名
	 * @return List<String>
	 */
	String findLanguageByCty(String nationVal);

	/**
	 * 入力した都市名の重複するかどうかを検証する
	 *
	 * @param cityName 都市名
	 * @return List<City>
	 */
	List<City> checkDuplicate(String cityName);
}
