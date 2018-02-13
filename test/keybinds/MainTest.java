package keybinds;

import static org.junit.Assert.*;

import org.junit.Test;

import util.log.LogUtil;

public class MainTest {

	@Test
	public void test() {

		LogUtil.setDebugMode(true);

		Main.startKeyBind();

		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		assertTrue(true);

	}

}
