package jp.co.sbsssmcrud.ppog.dto;

/**
 * 都市情報データ交換クラス
 *
 * @author shubonnsei
 * @since 1.00
 */
public record CityDto(Integer id, String name, String continent, String nation, String district, Integer population,
		String language) {
}
