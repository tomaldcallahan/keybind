package keybinds.task;

import java.util.List;

import keybinds.dao.Dao;
import keybinds.dto.KeyBind;
import util.exec.AppExecUtil;
import util.file.FileUtil;
import util.log.LogUtil;
import util.robot.RobotUtil;

public class ExecuteTask {

	/**
	 * デバッグモード true：デバッグモード
	 */
	private static boolean isDebug = false;

	/**
	 * デバッグモードをセットする。
	 *
	 * @param isDebug
	 */
	public static void setDebugMode(boolean isDebug) {

		ExecuteTask.isDebug = isDebug;

	}

	/**
	 * キーの判定を行うメソッド
	 *
	 * @param pressedKeySet
	 */
	public static void execute(KeyBind pressedKeys) {

		// DBに登録されているキーパターンと比較
		List<KeyBind> keyBindList = Dao.selectAll();
		String kind = null;
		String key = null;
		String path = null;
		String name = null;

		// kindが押されているかの確認
		for (KeyBind keyBind : keyBindList) {

			if (keyBind.equalsIgnoreCase(pressedKeys)) {
				kind = keyBind.getKind();
				key = keyBind.getKey();
				path = keyBind.getPath();
				name = keyBind.getName();
			}

		}

		if (kind == null || key == null || path == null || name == null) {
			return;
		}

		System.out.println();
		System.out.println("execute... name[" + name + "] kind[" + kind + "] key[" + key + "] path[" + path + "]");

		AppExecInterface exec = null;
		if (isDebug) {

			// テスト時のスタブ
			exec = new AppExecInterface() {

				@Override
				public void execOther(String path) {
					System.out.println("execOther");
				}

				@Override
				public void execExplore(String path) {
					System.out.println("execExplore");
				}

				@Override
				public void execApp(String path) {
					System.out.println("execApp");
				}
			};
		} else {

			// 本物
			exec = new AppExec();

		}

		// このプログラムをアクティブにする。
		//AppExecUtil.execAppWithReturn(FileUtil.getCurrentPath() + "WindowActiveTool.exe",
		//		String.valueOf(AppExecUtil.getProssesId()));

		switch (kind) {
		case "e":
			exec.execExplore(path);
			break;
		case "p":
			exec.execApp(path);
			break;
		default:
			exec.execOther(path);
			break;
		}

	}

}
