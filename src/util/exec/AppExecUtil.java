package util.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.exception.HinaException;
import util.log.LogUtil;

public class AppExecUtil {

	private AppExecUtil() {

	}

	private static Map<String, Process> map = new HashMap<String, Process>();

	static public void execApp(String targetApplication, String path) throws HinaException {

		if (path != null) {
			Runtime runtime = Runtime.getRuntime();
			String cmd = targetApplication + " " + path;
			try {

				Process p = runtime.exec(cmd);
				// try(BufferedReader br = new BufferedReader(new
				// InputStreamReader(p.getInputStream()));){
				// String line = br.readLine();
				// LogUtil.log(line);
				// }
				map.put(targetApplication + path, p);

			} catch (IOException e) {
				throw new HinaException("アプリケーションの起動に失敗しました。[" + targetApplication + "]", e);
			}
		}
	}

	/**
	 * 結果を取得して、アプリケーションが停止するまで待つ。<br>
	 * 全終了には対応しない。
	 *
	 * @param targetApplication
	 * @param param
	 * @return result
	 */
	static public int execAppWithReturn(String targetApplication, String param) {

		Runtime runtime = Runtime.getRuntime();
		String cmd = targetApplication + " " + "\"" + param + "\"";

		int result = 0;
		try {
			Process p = runtime.exec(cmd);

			/*
			 * try { Field field = p.getClass().getDeclaredField("pid");
			 * field.setAccessible(true); pid = field.getInt(p); } catch
			 * (NoSuchFieldException|IllegalAccessException ex) {
			 * ex.printStackTrace(); }
			 */

			/*
			 * InputStream inputstream = p.getInputStream(); InputStreamReader
			 * inputstreamreader = new InputStreamReader(inputstream);
			 * BufferedReader bufferedreader = new
			 * BufferedReader(inputstreamreader); String line; while ((line =
			 * bufferedreader.readLine()) != null) { System.out.println(line); }
			 */

			result = p.waitFor();

			p = null;
			// map.put(targetApplication + param, p );

		} catch (IOException e) {
			throw new HinaException("アプリケーションの起動に失敗しました。[" + targetApplication + "]", e);
		} catch (InterruptedException e) {
			LogUtil.log(e);
		}
		return result;
	}

	static public void destroy(String targetApplication, String path) {
		String key = targetApplication + path;
		Process p = map.get(key);
		p.destroy();
		p = null;
		map.remove(key);
	}

	static public void kill(String processName) throws HinaException {

		List<String> pIdList = ProcessWatch.getProsessId(processName);
		for (String id : pIdList) {
			execApp("cmd /c", "taskkill /pid " + id + " /f");
		}

	}

	/**
	 * エクスプローラの起動とブラウザの起動に使用できる<br>
	 * 例）<br>
	 * AppExecUtil.execExplorer("C:\\workspace\\290.PingMan\\workspace\\PingMan\\src\\util");<br>
	 * AppExecUtil.execExplorer("https://www.yahoo.co.jp/");
	 *
	 * @param path
	 * @throws HinaException
	 */
	static public void execExplorer(String path) throws HinaException {
		execApp("explorer", path);
	}

	static public void execSakura(String path) throws HinaException {
		execApp("C:\\Program Files (x86)\\sakura\\sakura.exe", "\"" + path + "\"");
	}

	static public long getProssesId() {

		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		String vmName = bean.getName();
		long pid = Long.valueOf(vmName.split("@")[0]);
		LogUtil.log("VM Name : " + vmName);
		LogUtil.log("PID : " + pid);

		return pid;
	}
}
