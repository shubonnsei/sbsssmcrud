package jp.co.sony.ppog.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 共通ストリング判断ツール
 *
 * @author ArcaHozota
 * @since 3.40
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

	/**
	 * 全角半角変換マップ
	 */
	private static final BiMap<String, String> HALF_FULL_CONVERTAR = HashBiMap.create(200);

	/**
	 * UTF-8キャラセット
	 */
	public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

	/**
	 * 空のストリング
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * 全角から半角へ変換
	 *
	 * @param zenkaku 全角文字
	 * @return 半角文字
	 */
	public static String toHankaku(@Nullable final String zenkaku) {
		if (isEmpty(zenkaku)) {
			return EMPTY_STRING;
		}
		final StringBuilder builder = new StringBuilder();
		final List<String> zenkakuList = new ArrayList<>(HALF_FULL_CONVERTAR.keySet());
		for (int i = 0; i < zenkaku.length(); i++) {
			final String charAtString = String.valueOf(zenkaku.charAt(i));
			if (zenkakuList.contains(charAtString)) {
				builder.append(HALF_FULL_CONVERTAR.get(charAtString));
			} else {
				builder.append(charAtString);
			}
		}
		return builder.toString();
	}

	/**
	 * 半角から全角へ変換
	 *
	 * @param hankaku 半角文字
	 * @return 全角文字
	 */
	public static String toZenkaku(@Nullable final String hankaku) {
		if (isEmpty(hankaku)) {
			return EMPTY_STRING;
		}
		final StringBuilder builder = new StringBuilder();
		final List<String> hankakuList = new ArrayList<>(HALF_FULL_CONVERTAR.values());
		for (int i = 0; i < hankaku.length(); i++) {
			final String charAtString = String.valueOf(hankaku.charAt(i));
			if (hankakuList.contains(charAtString)) {
				builder.append(HALF_FULL_CONVERTAR.inverse().get(charAtString));
			} else {
				builder.append(charAtString);
			}
		}
		return builder.toString();
	}

	/**
	 * 当ストリングは空かどうかを判断する
	 *
	 * @param str ストリング
	 * @return true: 空, false: 空ではない
	 */
	public static boolean isEmpty(@Nullable final String str) {
		return str == null || str.length() == 0 || str.isBlank();
	}

	/**
	 * 当ストリングは空ではないかどうかを判断する
	 *
	 * @param str ストリング
	 * @return true: 空ではない, false: 空
	 */
	public static boolean isNotEmpty(@Nullable final String str) {
		return !isEmpty(str);
	}

	/**
	 * 二つのストリングはイコールすることを判断する
	 *
	 * @param str1 ストリング1
	 * @param str2 ストリング2
	 * @return true: イコール, false: イコールしない
	 */
	public static boolean isEqual(@Nullable final String str1, @Nullable final String str2) {
		if (str1 == null && str2 == null) {
			return true;
		}
		if (str1 == null || str2 == null || str1.length() != str2.length()) {
			return false;
		}
		return str1.trim().equals(str2.trim());
	}

	/**
	 * 二つのストリングはイコールしないことを判断する
	 *
	 * @param str1 ストリング1
	 * @param str2 ストリング2
	 * @return true: イコールしない, false: イコール
	 */
	public static boolean isNotEqual(@Nullable final String str1, @Nullable final String str2) {
		return !isEqual(str1, str2);
	}

	static {
		HALF_FULL_CONVERTAR.put("！", "!");
		HALF_FULL_CONVERTAR.put("”", "\"");
		HALF_FULL_CONVERTAR.put("＃", "#");
		HALF_FULL_CONVERTAR.put("＄", "$");
		HALF_FULL_CONVERTAR.put("％", "%");
		HALF_FULL_CONVERTAR.put("＆", "&");
		HALF_FULL_CONVERTAR.put("’", "'");
		HALF_FULL_CONVERTAR.put("（", "(");
		HALF_FULL_CONVERTAR.put("）", ")");
		HALF_FULL_CONVERTAR.put("＊", "*");
		HALF_FULL_CONVERTAR.put("＋", "+");
		HALF_FULL_CONVERTAR.put("，", ",");
		HALF_FULL_CONVERTAR.put("－", "-");
		HALF_FULL_CONVERTAR.put("．", ".");
		HALF_FULL_CONVERTAR.put("／", "/");
		HALF_FULL_CONVERTAR.put("０", "0");
		HALF_FULL_CONVERTAR.put("１", "1");
		HALF_FULL_CONVERTAR.put("２", "2");
		HALF_FULL_CONVERTAR.put("３", "3");
		HALF_FULL_CONVERTAR.put("４", "4");
		HALF_FULL_CONVERTAR.put("５", "5");
		HALF_FULL_CONVERTAR.put("６", "6");
		HALF_FULL_CONVERTAR.put("７", "7");
		HALF_FULL_CONVERTAR.put("８", "8");
		HALF_FULL_CONVERTAR.put("９", "9");
		HALF_FULL_CONVERTAR.put("：", ":");
		HALF_FULL_CONVERTAR.put("；", ";");
		HALF_FULL_CONVERTAR.put("＜", "<");
		HALF_FULL_CONVERTAR.put("＝", "=");
		HALF_FULL_CONVERTAR.put("＞", ">");
		HALF_FULL_CONVERTAR.put("？", "?");
		HALF_FULL_CONVERTAR.put("＠", "@");
		HALF_FULL_CONVERTAR.put("Ａ", "A");
		HALF_FULL_CONVERTAR.put("Ｂ", "B");
		HALF_FULL_CONVERTAR.put("Ｃ", "C");
		HALF_FULL_CONVERTAR.put("Ｄ", "D");
		HALF_FULL_CONVERTAR.put("Ｅ", "E");
		HALF_FULL_CONVERTAR.put("Ｆ", "F");
		HALF_FULL_CONVERTAR.put("Ｇ", "G");
		HALF_FULL_CONVERTAR.put("Ｈ", "H");
		HALF_FULL_CONVERTAR.put("Ｉ", "I");
		HALF_FULL_CONVERTAR.put("Ｊ", "J");
		HALF_FULL_CONVERTAR.put("Ｋ", "K");
		HALF_FULL_CONVERTAR.put("Ｌ", "L");
		HALF_FULL_CONVERTAR.put("Ｍ", "M");
		HALF_FULL_CONVERTAR.put("Ｎ", "N");
		HALF_FULL_CONVERTAR.put("Ｏ", "O");
		HALF_FULL_CONVERTAR.put("Ｐ", "P");
		HALF_FULL_CONVERTAR.put("Ｑ", "Q");
		HALF_FULL_CONVERTAR.put("Ｒ", "R");
		HALF_FULL_CONVERTAR.put("Ｓ", "S");
		HALF_FULL_CONVERTAR.put("Ｔ", "T");
		HALF_FULL_CONVERTAR.put("Ｕ", "U");
		HALF_FULL_CONVERTAR.put("Ｖ", "V");
		HALF_FULL_CONVERTAR.put("Ｗ", "W");
		HALF_FULL_CONVERTAR.put("Ｘ", "X");
		HALF_FULL_CONVERTAR.put("Ｙ", "Y");
		HALF_FULL_CONVERTAR.put("Ｚ", "Z");
		HALF_FULL_CONVERTAR.put("［", "[");
		HALF_FULL_CONVERTAR.put("￥", "\\");
		HALF_FULL_CONVERTAR.put("］", "]");
		HALF_FULL_CONVERTAR.put("＾", "^");
		HALF_FULL_CONVERTAR.put("＿", "_");
		HALF_FULL_CONVERTAR.put("｀", "`");
		HALF_FULL_CONVERTAR.put("ａ", "a");
		HALF_FULL_CONVERTAR.put("ｂ", "b");
		HALF_FULL_CONVERTAR.put("ｃ", "c");
		HALF_FULL_CONVERTAR.put("ｄ", "d");
		HALF_FULL_CONVERTAR.put("ｅ", "e");
		HALF_FULL_CONVERTAR.put("ｆ", "f");
		HALF_FULL_CONVERTAR.put("ｇ", "g");
		HALF_FULL_CONVERTAR.put("ｈ", "h");
		HALF_FULL_CONVERTAR.put("ｉ", "i");
		HALF_FULL_CONVERTAR.put("ｊ", "j");
		HALF_FULL_CONVERTAR.put("ｋ", "k");
		HALF_FULL_CONVERTAR.put("ｌ", "l");
		HALF_FULL_CONVERTAR.put("ｍ", "m");
		HALF_FULL_CONVERTAR.put("ｎ", "n");
		HALF_FULL_CONVERTAR.put("ｏ", "o");
		HALF_FULL_CONVERTAR.put("ｐ", "p");
		HALF_FULL_CONVERTAR.put("ｑ", "q");
		HALF_FULL_CONVERTAR.put("ｒ", "r");
		HALF_FULL_CONVERTAR.put("ｓ", "s");
		HALF_FULL_CONVERTAR.put("ｔ", "t");
		HALF_FULL_CONVERTAR.put("ｕ", "u");
		HALF_FULL_CONVERTAR.put("ｖ", "v");
		HALF_FULL_CONVERTAR.put("ｗ", "w");
		HALF_FULL_CONVERTAR.put("ｘ", "x");
		HALF_FULL_CONVERTAR.put("ｙ", "y");
		HALF_FULL_CONVERTAR.put("ｚ", "z");
		HALF_FULL_CONVERTAR.put("｛", "{");
		HALF_FULL_CONVERTAR.put("｜", "|");
		HALF_FULL_CONVERTAR.put("｝", "}");
		HALF_FULL_CONVERTAR.put("\uff5e", "~");
		HALF_FULL_CONVERTAR.put("。", "｡");
		HALF_FULL_CONVERTAR.put("「", "｢");
		HALF_FULL_CONVERTAR.put("」", "｣");
		HALF_FULL_CONVERTAR.put("、", "､");
		HALF_FULL_CONVERTAR.put("・", "･");
		HALF_FULL_CONVERTAR.put("ァ", "ｧ");
		HALF_FULL_CONVERTAR.put("ィ", "ｨ");
		HALF_FULL_CONVERTAR.put("ゥ", "ｩ");
		HALF_FULL_CONVERTAR.put("ェ", "ｪ");
		HALF_FULL_CONVERTAR.put("ォ", "ｫ");
		HALF_FULL_CONVERTAR.put("ャ", "ｬ");
		HALF_FULL_CONVERTAR.put("ュ", "ｭ");
		HALF_FULL_CONVERTAR.put("ョ", "ｮ");
		HALF_FULL_CONVERTAR.put("ッ", "ｯ");
		HALF_FULL_CONVERTAR.put("ー", "ｰ");
		HALF_FULL_CONVERTAR.put("ア", "ｱ");
		HALF_FULL_CONVERTAR.put("イ", "ｲ");
		HALF_FULL_CONVERTAR.put("ウ", "ｳ");
		HALF_FULL_CONVERTAR.put("エ", "ｴ");
		HALF_FULL_CONVERTAR.put("オ", "ｵ");
		HALF_FULL_CONVERTAR.put("カ", "ｶ");
		HALF_FULL_CONVERTAR.put("キ", "ｷ");
		HALF_FULL_CONVERTAR.put("ク", "ｸ");
		HALF_FULL_CONVERTAR.put("ケ", "ｹ");
		HALF_FULL_CONVERTAR.put("コ", "ｺ");
		HALF_FULL_CONVERTAR.put("サ", "ｻ");
		HALF_FULL_CONVERTAR.put("シ", "ｼ");
		HALF_FULL_CONVERTAR.put("ス", "ｽ");
		HALF_FULL_CONVERTAR.put("セ", "ｾ");
		HALF_FULL_CONVERTAR.put("ソ", "ｿ");
		HALF_FULL_CONVERTAR.put("タ", "ﾀ");
		HALF_FULL_CONVERTAR.put("チ", "ﾁ");
		HALF_FULL_CONVERTAR.put("ツ", "ﾂ");
		HALF_FULL_CONVERTAR.put("テ", "ﾃ");
		HALF_FULL_CONVERTAR.put("ト", "ﾄ");
		HALF_FULL_CONVERTAR.put("ナ", "ﾅ");
		HALF_FULL_CONVERTAR.put("ニ", "ﾆ");
		HALF_FULL_CONVERTAR.put("ヌ", "ﾇ");
		HALF_FULL_CONVERTAR.put("ネ", "ﾈ");
		HALF_FULL_CONVERTAR.put("ノ", "ﾉ");
		HALF_FULL_CONVERTAR.put("ハ", "ﾊ");
		HALF_FULL_CONVERTAR.put("ヒ", "ﾋ");
		HALF_FULL_CONVERTAR.put("フ", "ﾌ");
		HALF_FULL_CONVERTAR.put("ヘ", "ﾍ");
		HALF_FULL_CONVERTAR.put("ホ", "ﾎ");
		HALF_FULL_CONVERTAR.put("マ", "ﾏ");
		HALF_FULL_CONVERTAR.put("ミ", "ﾐ");
		HALF_FULL_CONVERTAR.put("ム", "ﾑ");
		HALF_FULL_CONVERTAR.put("メ", "ﾒ");
		HALF_FULL_CONVERTAR.put("モ", "ﾓ");
		HALF_FULL_CONVERTAR.put("ヤ", "ﾔ");
		HALF_FULL_CONVERTAR.put("ユ", "ﾕ");
		HALF_FULL_CONVERTAR.put("ヨ", "ﾖ");
		HALF_FULL_CONVERTAR.put("ラ", "ﾗ");
		HALF_FULL_CONVERTAR.put("リ", "ﾘ");
		HALF_FULL_CONVERTAR.put("ル", "ﾙ");
		HALF_FULL_CONVERTAR.put("レ", "ﾚ");
		HALF_FULL_CONVERTAR.put("ロ", "ﾛ");
		HALF_FULL_CONVERTAR.put("ワ", "ﾜ");
		HALF_FULL_CONVERTAR.put("ヲ", "ｦ");
		HALF_FULL_CONVERTAR.put("ン", "ﾝ");
		HALF_FULL_CONVERTAR.put("ガ", "ｶﾞ");
		HALF_FULL_CONVERTAR.put("ギ", "ｷﾞ");
		HALF_FULL_CONVERTAR.put("グ", "ｸﾞ");
		HALF_FULL_CONVERTAR.put("ゲ", "ｹﾞ");
		HALF_FULL_CONVERTAR.put("ゴ", "ｺﾞ");
		HALF_FULL_CONVERTAR.put("ザ", "ｻﾞ");
		HALF_FULL_CONVERTAR.put("ジ", "ｼﾞ");
		HALF_FULL_CONVERTAR.put("ズ", "ｽﾞ");
		HALF_FULL_CONVERTAR.put("ゼ", "ｾﾞ");
		HALF_FULL_CONVERTAR.put("ゾ", "ｿﾞ");
		HALF_FULL_CONVERTAR.put("ダ", "ﾀﾞ");
		HALF_FULL_CONVERTAR.put("ヂ", "ﾁﾞ");
		HALF_FULL_CONVERTAR.put("ヅ", "ﾂﾞ");
		HALF_FULL_CONVERTAR.put("デ", "ﾃﾞ");
		HALF_FULL_CONVERTAR.put("ド", "ﾄﾞ");
		HALF_FULL_CONVERTAR.put("バ", "ﾊﾞ");
		HALF_FULL_CONVERTAR.put("ビ", "ﾋﾞ");
		HALF_FULL_CONVERTAR.put("ブ", "ﾌﾞ");
		HALF_FULL_CONVERTAR.put("ベ", "ﾍﾞ");
		HALF_FULL_CONVERTAR.put("ボ", "ﾎﾞ");
		HALF_FULL_CONVERTAR.put("パ", "ﾊﾟ");
		HALF_FULL_CONVERTAR.put("ピ", "ﾋﾟ");
		HALF_FULL_CONVERTAR.put("プ", "ﾌﾟ");
		HALF_FULL_CONVERTAR.put("ペ", "ﾍﾟ");
		HALF_FULL_CONVERTAR.put("ポ", "ﾎﾟ");
		HALF_FULL_CONVERTAR.put("ヴ", "ｳﾞ");
		HALF_FULL_CONVERTAR.put("\u30f7", "ﾜﾞ");
		HALF_FULL_CONVERTAR.put("\u30fa", "ｦﾞ");
		HALF_FULL_CONVERTAR.put("゛", "ﾞ");
		HALF_FULL_CONVERTAR.put("゜", "ﾟ");
		HALF_FULL_CONVERTAR.put("\u3000", " ");
	}
}