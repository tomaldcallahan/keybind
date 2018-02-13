package keybinds;

import static org.junit.Assert.*;

import org.junit.Test;

import util.file.FileUtil;

public class FileUtilTest {

	@Test
	public void test() {

		String currentPath = FileUtil.getCurrentPath();
		System.out.println(currentPath);

		assertTrue(true);

	}

}
