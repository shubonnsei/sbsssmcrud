package jp.co.sbsssmcrud.ppog.utils;

/**
 * 共通業務エラー
 *
 * @author ArkamaHozota
 * @since 3.09
 */
public final class CommonException extends RuntimeException {

	private static final long serialVersionUID = -7417217150801804715L;

	public CommonException() {
		super();
	}

	public CommonException(final String message) {
		super(message);
	}
}
