package jp.co.sony.ppog.utils;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 共通ページングクラス
 *
 * @param <T>
 * @author Administrator
 */
@Getter
@Setter
public final class Pagination<T> {

	/**
	 * 毎ページのレコード
	 */
	private List<T> records;

	/**
	 * 当ページ
	 */
	private int pageNum;

	/**
	 * ページサイズ
	 */
	private int pageSize;

	/**
	 * すべてのページ数
	 */
	private int totalPages;

	/**
	 * すべてのレコード数
	 */
	private int totalRecords;

	/**
	 * 前のページはあるか
	 */
	private boolean hasPreviousPage = false;

	/**
	 * 次のページはあるか
	 */
	private boolean hasNextPage = false;

	/**
	 * 前のページ
	 */
	private int previousPage;

	/**
	 * 次のページ
	 */
	private int nextPage;

	/**
	 * ナビゲーションのページ数
	 */
	private int navigatePages;

	/**
	 * ナビゲーションの最初のページ
	 */
	private int naviFirstPage;

	/**
	 * ナビゲーションの最後のページ
	 */
	private int naviLastPage;

	/**
	 * ナビゲーションページの数の集合
	 */
	private int[] navigatePageNums;

	/**
	 * Paginationを取得する
	 *
	 * @param records      レコード
	 * @param totalRecords すべてのレコード数
	 * @param pageNum      当ページ
	 */
	public static <T> Pagination<T> of(final List<T> records, final int totalRecords, final int pageNum) {
		return new Pagination<>(records, totalRecords, pageNum, 17, 5);
	}

	/**
	 * Paginationを取得する
	 *
	 * @param records      レコード
	 * @param totalRecords すべてのレコード数
	 * @param pageNum      当ページ
	 * @param pageSize     ページサイズ
	 */
	public static <T> Pagination<T> of(final List<T> records, final int totalRecords, final int pageNum,
			final int pageSize) {
		return new Pagination<>(records, totalRecords, pageNum, pageSize, 5);
	}

	/**
	 * Paginationを取得する
	 *
	 * @param records       レコード
	 * @param totalRecords  すべてのレコード数
	 * @param pageNum       当ページ
	 * @param pageSize      ページサイズ
	 * @param navigatePages ナビゲーションのページ数
	 */
	public static <T> Pagination<T> of(final List<T> records, final int totalRecords, final int pageNum,
			final int pageSize, final int navigatePages) {
		return new Pagination<>(records, totalRecords, pageNum, pageSize, navigatePages);
	}

	/**
	 * コンストラクタ
	 *
	 * @param records       レコード
	 * @param totalRecords  すべてのレコード数
	 * @param pageNum       当ページ
	 * @param pageSize      ページサイズ
	 * @param navigatePages ナビゲーションのページ数
	 */
	private Pagination(final List<T> records, final int totalRecords, final int pageNum, final int pageSize,
			final int navigatePages) {
		if (records != null && !records.isEmpty()) {
			this.pageNum = pageNum;
			this.records = records;
			this.pageSize = records.size();
			this.totalRecords = totalRecords;
			final int ape = this.totalRecords / pageSize;
			this.totalPages = this.totalRecords % pageSize == 0 ? ape : ape + 1;
		} else if (records != null) {
			this.pageNum = 1;
			this.records = null;
			this.pageSize = 0;
			this.totalRecords = 0;
			this.totalPages = 1;
		} else {
			throw new PaginationException("データのコレクションは間違いました。");
		}
		this.calcByNavigatePages(navigatePages);
	}

	/**
	 * ナビゲーションのページ数によって色んな計算処理を行う
	 *
	 * @param navigatePages ナビゲーションのページ数
	 */
	private void calcByNavigatePages(final int navigatePages) {
		// ナビゲーションのページ数を設定する
		this.setNavigatePages(navigatePages);
		// ナビゲーションページの数の集合を取得する
		this.calcNavigatePageNums();
		// 前のページ、次のページ、最初及び最後のページを取得する
		this.calcPage();
		// ページングエッジを判断する
		this.discernPageBoundary();
	}

	/**
	 * ナビゲーションページの数の集合を取得する
	 */
	private void calcNavigatePageNums() {
		if (this.totalPages <= this.navigatePages) {
			this.navigatePageNums = new int[this.totalPages];
			for (int i = 0; i < this.totalPages; i++) {
				this.navigatePageNums[i] = i + 1;
			}
			return;
		}
		this.navigatePageNums = new int[this.navigatePages];
		int startNum = this.pageNum - this.navigatePages / 2;
		int endNum = this.pageNum + this.navigatePages / 2;
		if (endNum > this.totalPages && startNum >= 1) {
			endNum = this.totalPages;
			// 最後のナビゲーションページ
			for (int i = this.navigatePages - 1; i >= 0; i--) {
				this.navigatePageNums[i] = endNum--;
			}
		} else {
			if (startNum < 1) {
				startNum = 1;
			}
			// 他のナビゲーションページ
			for (int i = 0; i < this.navigatePages; i++) {
				this.navigatePageNums[i] = startNum++;
			}
		}
	}

	/**
	 * 前のページ、次のページ、最初及び最後のページを取得する
	 */
	private void calcPage() {
		if (this.navigatePageNums != null && this.navigatePageNums.length > 0) {
			this.naviFirstPage = this.navigatePageNums[0];
			this.naviLastPage = this.navigatePageNums[this.navigatePageNums.length - 1];
			if (this.pageNum > 1) {
				this.previousPage = this.pageNum - 1;
			}
			if (this.pageNum < this.totalPages) {
				this.nextPage = this.pageNum + 1;
			}
		}
	}

	/**
	 * ページングエッジを判断する
	 */
	private void discernPageBoundary() {
		this.hasPreviousPage = this.pageNum > 1;
		this.hasNextPage = this.pageNum < this.totalPages;
	}

	/**
	 * 内容はあるかどうかを判断する
	 */
	public boolean hasContent() {
		return !this.records.isEmpty();
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "Pagination [records=" + this.records + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize
				+ ", totalPages=" + this.totalPages + ", totalRecords=" + this.totalRecords + ", hasPrePage="
				+ this.hasPreviousPage + ", hasNextPage=" + this.hasNextPage + ", previousPage=" + this.previousPage
				+ ", nextPage=" + this.nextPage + ", navigatePages=" + this.navigatePages + ", naviFirstPage="
				+ this.naviFirstPage + ", naviLastPage=" + this.naviLastPage + ", navigatePageNums="
				+ Arrays.toString(this.navigatePageNums) + "]";
	}

	/**
	 * 例外
	 */
	static class PaginationException extends RuntimeException {

		private static final long serialVersionUID = 4906724781325178457L;

		public PaginationException(final String message) {
			super(message);
		}
	}
}
