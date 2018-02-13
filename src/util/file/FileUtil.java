package util.file;

import static util.log.LogUtil.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import util.exception.HinaException;

/**
 * ファイル操作ユーティル
 * @author hina
 *
 */
public class FileUtil {

	private FileUtil() {
		// インスタンス化不可
	}

	/**
	 * カレントパスを取得する。
	 * @return
	 */
	public static String getCurrentPath() {

		 String path = new File(".").getAbsoluteFile().getParent() + File.separator;
		 return path;
	}

	/**
	 * 作業フォルダパスを取得する。
	 * @return
	 */
	public static String getWorktPath() {

		 return getCurrentPath() + "ContractManageSystem" + File.separator;
	}

	/**
	 * フォルダを作成する
	 *
	 * @param file
	 * @throws IOException
	 */
	public static void createDir(File file) {

		// ファイルが既に存在する場合は、削除する
		if (file.exists()) {
			clearFile(file);
		}
		// ファイルを作成する
		file.mkdirs();

	}

	/**
	 * ファイルを作成する
	 * @param file
	 * @throws HinaException
	 */
	public static void createFile(File file) throws HinaException {

		// ファイルが既に存在する場合は、削除する
		if (file.exists()) {
			clearFile(file);
		}
		// ファイルを作成する
		try {
			file.createNewFile();
		} catch (IOException e) {
			log(e.getMessage());
			throw new HinaException("ファイルの作成ができませんでした。", e);
		}
	}


	/**
	 * フォルダを削除する
	 *
	 * @param file
	 */
	private static void clearFile(File file) {
		try {
			if (file.isFile()) {
				// ファイル削除
				if (file.exists() && !file.delete()) {
					file.delete();
				}
			} else {
				// ディレクトリの場合、再帰する
				File[] list = file.listFiles();
				for (int i = 0; i < list.length; i++) {
					clearFile(list[i]);
				}
				// ファイル削除
				if (file.exists() && !file.delete()) {
					file.delete();
				}
			}
		} catch (NullPointerException e) {
			return;
		}
	}

	static public FileManager createFileManager() {
		return new FileManager();
	}

	/**
	 * ファイルの入出力用のクラス
	 * @author hina
	 *
	 */
	public static class FileManager {

		private PrintWriter pw = null;

		private BufferedReader br = null;

		public void write(String data, String fileName) throws HinaException {

			String path = new File(".").getAbsoluteFile().getParent() + "\\ReportFolderOfKBS\\報告書\\";
			write(data, path, fileName);

		}

		public void write(String data, String path, String fileName) throws HinaException {

			try {
				if (pw == null) {

					pw = new PrintWriter(new BufferedWriter(new FileWriter(path + fileName)));

				}

				if (data == null) {
					pw.close();
					pw = null;
					return;
				}

				pw.println(data);
				pw.flush();

			} catch (IOException e) {
				throw new HinaException("ファイルのオープンに失敗しました。", e);
			}

		}

		public String read(String path, String fileName) throws HinaException {

			try {
				String line = null;
				if (br == null) {
					br = new BufferedReader(new FileReader(path + fileName));
					return read(path, fileName);
				} else {
					line = br.readLine();
					log("ファイル読み込み", line);
					if (line == null) {
						br.close();
						br = null;
					}
				}
				return line;
			} catch (IOException e) {
				log(e);
				if (br != null) {
					try {
						br.close();
						br = null;
					} catch (IOException e1) {
						throw new HinaException("ファイルのクローズに失敗しました。");
					}
				}
				throw new HinaException("ファイルの書き込みに失敗しました。");
			}

		}
	}

}
