package keybinds;

import static org.junit.Assert.*;

import org.junit.Test;

import keybinds.task.AppExec;
import util.exec.AppExecUtil;
import util.file.FileUtil;
import util.log.LogUtil;

public class AppExecUtilTest {

	@Test
	public void test() {

		LogUtil.setDebugMode(true);
		//AppExecUtil.execExplorer("C:\\workspace\\290.PingMan\\workspace\\PingMan\\src\\util");

		//int result1 = AppExecUtil.execAppWithReturn("explorer", "C:\\workspace\\290.PingMan\\workspace\\PingMan\\src\\util");


		// AppExecUtil.execExplorer("http://www.techscore.com/blog/2015/03/27/3%E5%88%86%E3%81%A7%E6%A7%8B%E7%AF%89%EF%BC%81sqlite%E3%82%92%E8%89%B2%E3%80%85%E3%81%AA%E7%92%B0%E5%A2%83%E3%81%A7%E5%8B%95%E3%81%8B%E3%81%97%E3%81%A6%E3%81%BF%E3%82%8B/");
		//int result3 = AppExecUtil.execAppWithReturn("explorer","https://www.yahoo.co.jp/");

		//AppExecUtil.getProssesId();

		// AppExecUtil.execApp("E:\\KTimer4_3_2\\KTimer4.exe", "");

		//System.out.println(FileUtil.getCurrentPath() + "WindowActiveTool.exe");
		// AppExecUtil.execApp(FileUtil.getCurrentPath() +
		// "WindowActiveTool.exe" , "2760");

		AppExecUtil.execAppWithReturn(FileUtil.getCurrentPath() + "WindowActiveTool.exe", "8328");

		//AppExec appExec = new AppExec();
		//appExec.execExplore("C:\\workspace\\290.PingMan\\workspace\\PingMan\\src\\util");
		//appExec.execApp("C:\\Program Files (x86)\\sakura\\sakura.exe");


		System.out.println("-----------------------");
		//System.out.println(result1);
		//System.out.println(result2);
		//System.out.println(result3);

		assertTrue(true);

	}

}
