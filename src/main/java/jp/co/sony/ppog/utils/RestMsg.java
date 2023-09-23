package jp.co.sony.ppog.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JSONData返信用クラス
 *
 * @author ArcaHozota
 * @since 2.44
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestMsg {

	/**
	 * data returned to browsers
	 */
	private final Map<String, Object> extend = new HashMap<>();

	/**
	 * status code
	 */
	private Integer code;

	/**
	 * the message of status
	 */
	private String message;

	/**
	 * retrieve successfully
	 *
	 * @return result including data
	 */
	public static RestMsg success() {
		final RestMsg result = new RestMsg();
		result.setCode(200);
		result.setMessage("Retrieve success.");
		return result;
	}

	/**
	 * retrieve failed
	 *
	 * @return result including error message
	 */
	public static RestMsg failure() {
		final RestMsg result = new RestMsg();
		result.setCode(400);
		result.setMessage("Retrieve failed.");
		return result;
	}

	/**
	 * add values with messages
	 *
	 * @param key   the name pattern of value
	 * @param value value
	 * @return RestMsg
	 */
	public RestMsg add(final String key, final Object value) {
		this.getExtend().put(key, value);
		return this;
	}
}