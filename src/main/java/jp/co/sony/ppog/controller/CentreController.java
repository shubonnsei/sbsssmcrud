package jp.co.sony.ppog.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.sony.ppog.dto.CityDto;
import jp.co.sony.ppog.entity.City;
import jp.co.sony.ppog.service.CentreLogicService;
import jp.co.sony.ppog.utils.Messages;
import jp.co.sony.ppog.utils.RestMsg;
import jp.co.sony.ppog.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 中央処理コントローラ
 *
 * @author ArcaHozota
 * @since 1.00beta
 */
@Controller
@RequestMapping("/ssmcrud")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CentreController {

	/**
	 * 中央処理サービスインターフェス
	 */
	private final CentreLogicService centreLogicService;

	/**
	 * 都市情報を検索する
	 *
	 * @return modelAndView
	 */
	@GetMapping(value = "/city")
	public ModelAndView getCityInfo(@RequestParam(value = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(value = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		// ページング検索結果を吹き出します；
		final Page<CityDto> pageInfo = this.centreLogicService.getPageInfo(pageNum, keyword);
		// modelAndViewオブジェクトを宣言する；
		final ModelAndView modelAndView = new ModelAndView("index");
		// 前のページを取得する；
		final int current = pageInfo.getNumber();
		// ページングナビゲーションの数を定義する；
		final int naviNums = 7;
		// ページングナビの最初と最後の数を取得する；
		final int pageFirstIndex = current / naviNums * naviNums;
		int pageLastIndex = (current / naviNums + 1) * naviNums - 1;
		if (pageLastIndex > pageInfo.getTotalPages() - 1) {
			pageLastIndex = pageInfo.getTotalPages() - 1;
		} else {
			pageLastIndex = (current / naviNums + 1) * naviNums - 1;
		}
		modelAndView.addObject("pageInfo", pageInfo);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("pageFirstIndex", pageFirstIndex);
		modelAndView.addObject("pageLastIndex", pageLastIndex);
		modelAndView.addObject("title", "CityList");
		return modelAndView;
	}

	/**
	 * 指定された都市の情報を取得する
	 *
	 * @param id 都市ID
	 * @return 都市情報
	 */
	@GetMapping(value = "/city/{id}")
	@ResponseBody
	public RestMsg getCityInfo(@PathVariable("id") final Long id) {
		final CityDto cityDto = this.centreLogicService.getCityInfoById(id);
		return RestMsg.success().add("citySelected", cityDto);
	}

	/**
	 * 指定された都市の大陸に位置するすべての国を取得する
	 *
	 * @param id 都市ID
	 * @return 国のリスト
	 */
	@GetMapping(value = "/nations/{id}")
	@ResponseBody
	public RestMsg getListOfNationsById(@PathVariable("id") final Long id) {
		final List<String> nations = this.centreLogicService.getListOfNationsById(id);
		return RestMsg.success().add("nationsWithName", nations);
	}

	/**
	 * 入力した都市情報を変更する
	 *
	 * @param cityDto 都市情報DTO
	 * @return 処理成功のメッセージ
	 */
	@PutMapping(value = "/city/{id}")
	@ResponseBody
	public RestMsg updateCityInfo(@RequestBody final CityDto cityDto) {
		this.centreLogicService.update(cityDto);
		return RestMsg.success();
	}

	/**
	 * 入力した都市情報を保存する
	 *
	 * @param cityDto 都市情報DTO
	 * @return 処理成功のメッセージ
	 */
	@PostMapping(value = "/city")
	@ResponseBody
	public RestMsg saveCityInfo(@RequestBody final CityDto cityDto) {
		this.centreLogicService.save(cityDto);
		return RestMsg.success();
	}

	/**
	 * 選択された都市情報を削除する
	 *
	 * @param id 都市ID
	 * @return 処理成功のメッセージ
	 */
	@DeleteMapping(value = "/city/{id}")
	@ResponseBody
	public RestMsg deleteCityInfo(@PathVariable("id") final Long id) {
		this.centreLogicService.removeById(id);
		return RestMsg.success();
	}

	/**
	 * 大陸情報を取得する
	 *
	 * @return 大陸名称のリスト
	 */
	@GetMapping(value = "/continents")
	@ResponseBody
	public RestMsg getContinents() {
		final List<String> continents = this.centreLogicService.findAllContinents();
		return RestMsg.success().add("continentList", continents);
	}

	/**
	 * 指定された大陸に位置するすべての国を取得する
	 *
	 * @param continentVal 大陸名称
	 * @return 国のリスト
	 */
	@GetMapping(value = "/nations")
	@ResponseBody
	public RestMsg getListOfNationsById(@RequestParam("continentVal") final String continentVal) {
		final List<String> nations = this.centreLogicService.findNationsByCnt(continentVal);
		return RestMsg.success().add("nationList", nations);
	}

	/**
	 * 指定された国の公用語を取得する
	 *
	 * @param nationVal 国名
	 * @return 言語のリスト
	 */
	@GetMapping(value = "/languages")
	@ResponseBody
	public RestMsg getListOfLanguages(@RequestParam("nationVal") final String nationVal) {
		final String language = this.centreLogicService.findLanguageByCty(nationVal);
		return RestMsg.success().add("languages", language);
	}

	/**
	 * 入力した都市名を重複かどうかをチェックする
	 *
	 * @param cityName 都市名称
	 * @return 処理成功のメッセージ
	 */
	@GetMapping(value = "/check")
	@ResponseBody
	public RestMsg checkName(@RequestParam("cityName") final String cityName) {
		if (!cityName.matches(Messages.MSG006)) {
			return RestMsg.failure().add("validatedMsg", Messages.MSG005);
		}
		final List<City> lists = this.centreLogicService.checkDuplicate(cityName);
		if (!lists.isEmpty()) {
			return RestMsg.failure().add("validatedMsg", Messages.MSG004);
		}
		return RestMsg.success();
	}
}