package util.ormapper;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import util.exception.HinaException;
import util.exception.HinaOrmapperException;
import util.xml.XmlFileReader;

public class SqlManager {

	static private Map<String, String> sqlMap = new HashMap<>();

	/**
	 * ファクトリメソッド
	 * @param file
	 * @return
	 * @throws HinaException
	 */
	static public void create(String file) throws HinaException {

		// SQLファイルの取得
		try {

			XmlFileReader reader = new XmlFileReader();
			Document doc = reader.readXml(file);

			int i = 1;
			String sql = reader.getXmlValue("/sqls/sql[" + i + "]/text()", doc);
			String id = reader.getXmlValue("/sqls/sql[" + i + "]/@id", doc);
			i++;
			while (!id.isEmpty()) {

				sqlMap.put(id, sql);
				sql = reader.getXmlValue("/sqls/sql[" + i + "]/text()", doc);
				id = reader.getXmlValue("/sqls/sql[" + i + "]/@id", doc);
				i++;

			}

		} catch (HinaException e) {

			throw new HinaOrmapperException("ファイルからsqlの取得に失敗しました。", e);

		}

	}

	/**
	 * idからSQLを取得する。
	 * @param id
	 * @return
	 */
	static public String getSql(String id) {
		return sqlMap.get(id);
	}

}
