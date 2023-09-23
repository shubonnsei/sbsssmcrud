package jp.co.sony.ppog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 共通メッセージクラス
 *
 * @author ArcaHozota
 * @since 5.32
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {

	public static final String MSG001 = "拡張メッセージコンバーターの設置は完了しました。";

	public static final String MSG002 = "静的リソースのマッピングが開始しました。";

	public static final String MSG003 = "アプリは正常に起動しました!";

	public static final String MSG004 = "入力した都市名が重複する。";

	public static final String MSG005 = "入力した都市名は4桁から23桁までのローマ字にしなければなりません。";

	public static final String MSG006 = "^[a-zA-Z-\\\\p{IsWhiteSpace}]{4,17}$";

	public static final String MSG007 = "visible";

	public static final String MSG008 = "removed";
}
