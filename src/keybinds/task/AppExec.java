package keybinds.task;

import util.exec.AppExecUtil;
import util.file.FileUtil;
import util.robot.RobotUtil;

/**
 * エクスプローラやアプリケーション実行用のクラス
 * @author hina
 *
 */
public class AppExec implements AppExecInterface {

	private static boolean isFirst = false;

	public static void setFirst(boolean isFirst) {
		AppExec.isFirst = isFirst;
	}

	private static boolean isWindowRotate = false;

	public static void setWindowRotate(boolean isWindowRotate) {
		AppExec.isWindowRotate = isWindowRotate;
	}

	@Override
	public void execExplore(String path) {
		// AppExecUtil.execExplorer(path);
		AppExecUtil.execAppWithReturn(FileUtil.getCurrentPath() + "execApp.exe", path);
		if (isFirst || isWindowRotate) {
			RobotUtil.altAndShiftAndTab();
			isFirst = false;
		}
	}

	@Override
	public void execApp(String path) {
		// AppExecUtil.execApp(path, "");
		AppExecUtil.execAppWithReturn(FileUtil.getCurrentPath() + "execApp.exe", path);
		if (isFirst || isWindowRotate) {
			RobotUtil.altAndShiftAndTab();
			isFirst = false;
		}
	}

	@Override
	public void execOther(String path) {
		//AppExecUtil.execExplorer(path);
		AppExecUtil.execAppWithReturn(FileUtil.getCurrentPath() + "execApp.exe", path);
		if (isFirst || isWindowRotate) {
			RobotUtil.altAndShiftAndTab();
			isFirst = false;
		}
	}

}