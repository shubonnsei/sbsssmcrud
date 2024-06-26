package jp.co.sbsssmcrud.ppog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sbsssmcrud.ppog.dto.CityDto;
import jp.co.sbsssmcrud.ppog.service.SbsSsmcrudLogicService;
import jp.co.sbsssmcrud.ppog.utils.Messages;
import jp.co.sbsssmcrud.ppog.utils.Pagination;
import jp.co.sbsssmcrud.ppog.utils.RestMsg;
import jp.co.sbsssmcrud.ppog.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 中央処理コントローラ
 *
 * @author shubonnsei
 * @since 1.00
 */
@RestController
@RequestMapping("/public/ssmcrud")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SbsSsmcrudController {

	/**
	 * 中央処理サービスインターフェス
	 */
	private final SbsSsmcrudLogicService sbsSsmcrudLogicService;

	/**
	 * 入力した都市名を重複かどうかをチェックする
	 *
	 * @param cityName 都市名称
	 * @return 処理成功のメッセージ
	 */
	@GetMapping(value = "/check")
	public RestMsg checkName(@RequestParam("cityName") final String cityName) {
		if (!cityName.matches(Messages.MSG006)) {
			return RestMsg.failure().add("validatedMsg", Messages.MSG005);
		}
		final Integer checkInteger = this.sbsSsmcrudLogicService.checkDuplicate(cityName);
		if (checkInteger > 0) {
			return RestMsg.failure().add("validatedMsg", Messages.MSG004);
		}
		return RestMsg.success();
	}

	/**
	 * 選択された都市情報を削除する
	 *
	 * @param id 都市ID
	 * @return 処理成功のメッセージ
	 */
	@DeleteMapping(value = "/city/{id}")
	public RestMsg deleteCityInfo(@PathVariable("id") final Integer id) {
		this.sbsSsmcrudLogicService.removeById(id);
		return RestMsg.success();
	}

	/**
	 * 指定された都市の情報を取得する
	 *
	 * @param id 都市ID
	 * @return 都市情報
	 */
	@GetMapping(value = "/city/{id}")
	public RestMsg getCityInfo(@PathVariable("id") final Integer id) {
		final CityDto cityDto = this.sbsSsmcrudLogicService.getCityInfoById(id);
		return RestMsg.success().add("citySelected", cityDto);
	}

	/**
	 * 都市情報を検索する
	 *
	 * @return modelAndView
	 */
	@GetMapping(value = "/city")
	public RestMsg getCityInfo(@RequestParam(value = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(value = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		// ページング検索結果を吹き出します；
		final Pagination<CityDto> pageInfo = this.sbsSsmcrudLogicService.getPageInfo(pageNum, keyword);
		return RestMsg.success().add("pageInfo", pageInfo);
	}

	/**
	 * 大陸情報を取得する
	 *
	 * @return 大陸名称のリスト
	 */
	@GetMapping(value = "/continents")
	public RestMsg getContinents() {
		final List<String> continents = this.sbsSsmcrudLogicService.findAllContinents();
		return RestMsg.success().add("continents", continents);
	}

	/**
	 * 指定された国の公用語を取得する
	 *
	 * @param nationVal 国名
	 * @return 言語のリスト
	 */
	@GetMapping(value = "/languages")
	public RestMsg getListOfLanguages(@RequestParam("nationVal") final String nationVal) {
		final String language = this.sbsSsmcrudLogicService.findLanguageByCty(nationVal);
		return RestMsg.success().add("languages", language);
	}

	/**
	 * 指定された大陸に位置するすべての国を取得する
	 *
	 * @param continentVal 大陸名称
	 * @return 国のリスト
	 */
	@GetMapping(value = "/nations")
	public RestMsg getListOfNationsById(@RequestParam("continentVal") final String continentVal) {
		final List<String> nations = this.sbsSsmcrudLogicService.findNationsByCnt(continentVal);
		return RestMsg.success().add("nations", nations);
	}

	/**
	 * 入力した都市情報を保存する
	 *
	 * @param cityDto 都市情報DTO
	 * @return 処理成功のメッセージ
	 */
	@PostMapping(value = "/city")
	public RestMsg saveCityInfo(@RequestBody final CityDto cityDto) {
		return this.sbsSsmcrudLogicService.save(cityDto);
	}

	/**
	 * 入力した都市情報を変更する
	 *
	 * @param cityDto 都市情報DTO
	 * @return 処理成功のメッセージ
	 */
	@PutMapping(value = "/city/{id}")
	public RestMsg updateCityInfo(@RequestBody final CityDto cityDto) {
		return this.sbsSsmcrudLogicService.update(cityDto);
	}
}