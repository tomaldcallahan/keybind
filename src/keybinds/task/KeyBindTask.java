package keybinds.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import keybinds.dao.Dao;
import keybinds.dto.KeyBind;
import util.log.LogUtil;

public class KeyBindTask implements NativeKeyListener {

	private static boolean isDebug = false;

	public void setDebugMode(boolean isDebug) {
		KeyBindTask.isDebug = isDebug;
	}

	/**
	 * キーバインドを記録
	 * <key>
	 */
	Set<String> pressedKeySet = new HashSet<>();

	// キーが押された
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		LogUtil.log(e.paramString());

		String pressedKey = NativeKeyEvent.getKeyText(e.getKeyCode());
		pressedKeySet.add(pressedKey);

		LogUtil.log(pressedKey);

		// 諸条件がそろった場合、エクスプローラやアプリケーションの実行とする。
		KeyBind keyBind = new KeyBind();
		if (isKey(pressedKey)) {

			boolean isCtrl = false;
			boolean isAlt = false;
			for (String key : pressedKeySet) {
				if (key.matches("[1-9]")) {
					keyBind.setKey(key);
				}
				if (key.matches("[a-zA-Z]")) {
					keyBind.setKind(key);
				}
				if (key.equals("Ctrl"))
					isCtrl = true;
				if (key.equals("Alt"))
					isAlt = true;
			}
			if (!(isCtrl && isAlt)) {
				return;
			}
			// kindがnullの場合は、eとする。（エクスプローラを開く）
			if (keyBind.getKind() == null) {
				keyBind.setKind("e");
			}
			ExecuteTask.setDebugMode(isDebug);
			ExecuteTask.execute(keyBind);

			pressedKeySet.remove(keyBind.getKind());
			pressedKeySet.remove(keyBind.getKey());

		}

	}

	// キーが離された
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		LogUtil.log(e.paramString());

		String pressedKey = NativeKeyEvent.getKeyText(e.getKeyCode());

		// kindの場合は削除しない。（ただし、スレッドで削除する。）
		if (isKind(pressedKey)) {
			deletePressedKey(pressedKeySet, pressedKey);
			return;

		}

		// キーバインド削除
		pressedKeySet.remove(pressedKey);

	}

	// キーをタイプした
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		LogUtil.log(e.paramString());
		// 未処理
	}

	private static void deletePressedKey(Set<String> pressedKeySet, String targetDeleteKey) {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, r -> {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		});

		// コマンド処理用のタスク起動
		DeletePressedKeyRunnable runnable = new DeletePressedKeyRunnable();
		runnable.setPressedKeySet(pressedKeySet);
		runnable.setTargetDeleteKey(targetDeleteKey);
		executorService.schedule(runnable, 800, TimeUnit.MILLISECONDS);

	}

	/**
	 * 押されたキーが、登録されているkeyに該当するか判断
	 * @param pressedKey
	 * @return
	 */
	private boolean isKey(String pressedKey) {

		List<KeyBind> keyBindList = Dao.getKeys();

		for (KeyBind keyBind : keyBindList) {

			String key = keyBind.getKey();

			if (key.equalsIgnoreCase(pressedKey)) {
				return true;
			}

		}
		return false;

	}

	/**
	 * 押されたキーがkindに該当するか判断
	 * @param pressedKey
	 * @return
	 */
	private boolean isKind(String pressedKey) {

		List<KeyBind> keyBindList = Dao.getKinds();

		for (KeyBind keyBind : keyBindList) {

			String kind = keyBind.getKind();

			if (kind.equalsIgnoreCase(pressedKey)) {
				return true;
			}

		}
		return false;
	}

}
