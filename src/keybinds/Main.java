package keybinds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import keybinds.task.CommandTask;
import keybinds.task.KeyBindTask;
import util.log.LogUtil;
import util.robot.RobotUtil;

public class Main {

	/**
	 * 全体のデバッグモードを変更するフィールド<br>
	 * ここを変更するとアプリケーション全体のデバッグモードが変更される。
	 */
	private static boolean isDebug = false;

	/**
	 * メイン<br>
	 * ここから実行
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// ロボット初期化
			RobotUtil.init();

			// ログユーティルにデバッグモードセット
			LogUtil.setDebugMode(isDebug);

			// コマンドライン開始
			startCommandTask();

			// キーバインド開始
			startKeyBind();

		} catch (Exception e) {
			System.out.println("I'm sorry. caouse...");
			e.printStackTrace();
		}

	}

	/**
	 * キーバインドセット用メソッド
	 */
	public static void startKeyBind() {

		// ライブラリのログを抑制
		suppressLog();

		// フックしてなかったらフック
		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		// キーバインド開始
		KeyBindTask keyBindTask = new KeyBindTask();
		keyBindTask.setDebugMode(isDebug);
		GlobalScreen.addNativeKeyListener(keyBindTask);
	}

	/**
	 * ログを出力するスレッド
	 */
	private static void suppressLog() {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, r -> {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		});

		// ログレベル変更（ライブラリが一回、設定した後に、再度、ログレベルを変更する。）
		executorService.schedule(() -> {
			final Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			//	if (jNativeHookLogger.getLevel() != Level.WARNING) {
			//		synchronized (jNativeHookLogger) {
			//			jNativeHookLogger.setLevel(Level.WARNING);
			//		}
			//	}
			if (jNativeHookLogger.getLevel() != Level.OFF) {
				synchronized (jNativeHookLogger) {
					jNativeHookLogger.setLevel(Level.OFF);
				}
			}
		}, 100, TimeUnit.MILLISECONDS);
		executorService.shutdown();


	}

	/**
	 * コマンドラインを担当するスレッド
	 */
	private static void startCommandTask() {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, r -> {
			Thread thread = new Thread(r);
			thread.setDaemon(false);
			return thread;
		});

		// コマンド処理用のタスク起動
		executorService.schedule(new CommandTask(), 1, TimeUnit.SECONDS);
		executorService.shutdown();

	}

}
