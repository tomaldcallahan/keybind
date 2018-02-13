package util.exception;

public class HinaOrmapperException extends HinaException {

	/* TODO
	 * 例外クラスは、以下の３つをつくるとよい
	 * １．引数無し
	 * ２．引数　メッセージ
	 * ３．引数　エラー
	 * ４．引数　メッセージ、エラー
	 * ※かならずスーパーのコンストラクタを呼ぶこと
	 */

	public HinaOrmapperException(String message, Throwable e) {
		super(message, e);
	}

	public HinaOrmapperException(String message) {
		super(message);
	}


}
