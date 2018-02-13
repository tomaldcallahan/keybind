package util.log;

public class LogUtil {

	private LogUtil() {
		// インスタンス不可
	}

	/**
	 * デバッグモード<br>
	 * true：標準出力に出力
	 * false：出力しない
	 */
	static private boolean isDebug = false;

	/**
	 * デーバッグモードセット
	 * @param isDebug
	 */
	static public void setDebugMode(boolean isDebug) {
		LogUtil.isDebug = isDebug;
	}

	/**
	 * 出力メソッド
	 * @param messages
	 */
	static public void log(String... messages) {

		if (!isDebug)
			return;

		String result = "";
		boolean isFirst = true;
		for (String message : messages) {
			result += isFirst ? message : " : " + message;
			isFirst = false;
		}
		result += System.lineSeparator();

		System.out.println(result);

	}

	/**
	 * Exception出力メソッド
	 * @param e
	 */
	static public void log(Throwable e) {

		if (!isDebug)
			return;

		// ログ処理用
		StackTraceElement[] elements = e.getStackTrace();
		for (StackTraceElement element : elements) {
			log(element.toString());
		}

	}

}
