package keybinds.task;

import java.util.HashSet;
import java.util.Set;

/**
 * キーを削除するクラス
 * @author hina
 *
 */
public class DeletePressedKeyRunnable implements DeletePressedKeyRunnableInterface {

	private Set<String> pressedKeySet = new HashSet<>();

	private String targetDeleteKey = null;

	public void setPressedKeySet(Set<String> pressedKeySet) {
		this.pressedKeySet = pressedKeySet;
	}

	public void setTargetDeleteKey(String targetDeleteKey) {
		this.targetDeleteKey = targetDeleteKey;
	}

	@Override
	public void run() {

		pressedKeySet.remove(targetDeleteKey);

	}

}

/**
 * キーを削除するインタフェース
 * @author hina
 *
 */
interface DeletePressedKeyRunnableInterface extends Runnable {
	public void setPressedKeySet(Set<String> pressedKeySet);

	public void setTargetDeleteKey(String targetDeleteKey);
}
