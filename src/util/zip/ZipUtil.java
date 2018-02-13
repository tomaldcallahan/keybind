package util.zip;

import static util.log.LogUtil.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import util.exception.HinaException;

public class ZipUtil {

	private ZipUtil(){
		// インスタンス化不可
	}

	/**
	 * Zipファイルを展開する
	 *
	 * @param aZipFile
	 *            zipファイル
	 * @param aOutDir
	 *            出力先ディレクトリ
	 * @throws HinaException
	 * @throws IOException
	 */
	static public void decode(File aZipFile, String aOutDir) throws HinaException {
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;

		try {
			// zipファイルをオープンする
			fileIn = new FileInputStream(aZipFile);
			@SuppressWarnings("resource")
			ZipInputStream zipIn = new ZipInputStream(fileIn);

			ZipEntry entry = null;
			while ((entry = zipIn.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					// ディレクトリでないの場合は出力する
					// 出力先は、現在の aOutDirの下
					String relativePath = entry.getName();
					File outFile = new File(aOutDir, relativePath);

					// 出力先のディレクトリを作成する
					File parentFile = outFile.getParentFile();
					parentFile.mkdirs();

					// ファイルを出力する
					fileOut = new FileOutputStream(outFile);

					byte[] buf = new byte[256];
					int size = 0;
					while ((size = zipIn.read(buf)) > 0) {
						fileOut.write(buf, 0, size);
					}
					fileOut.close();
					fileOut = null;
				} else {
				}
				zipIn.closeEntry();
			}
		} catch (Exception e) {
			log(e);
			throw new HinaException("ZIPの解答に失敗しました。", e);
		} finally {
			// fileInがNullでない場合、fileIn.closeを呼び出す
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (Exception e) {
					log(e);
					throw new HinaException("ZIPファイルのオープンに失敗しました。", e);
				}
			}
			// fileOutがNullでない場合、fileOut.closeを呼び出す
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (Exception e) {
					log(e);
					throw new HinaException("ZIPファイルのクローズに失敗しました。", e);
				}
			}
		}
	}

}
