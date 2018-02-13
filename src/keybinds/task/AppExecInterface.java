package keybinds.task;

/**
 * エクスプローラやアプリケーション実行用のインタフェース
 * @author hina
 *
 */
interface AppExecInterface {

	void execExplore(String path);

	void execApp(String path);

	void execOther(String path);

}