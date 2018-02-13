package util.ormapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.exception.HinaOrmapperException;
import util.file.FileUtil;

public class ConnectionFactory {

	private static Connection connection = null;

	/**
	 * シングルトンモード<br>
	 * true：コネクションはシングルトンとなる。
	 */
	private static boolean isSingleton = true;

	private ConnectionFactory() {
		// インスタンスを作りたくないクラスは、コンストラクタをprivateにする。
	}

	public void setSinglton(boolean isSingleton){
		ConnectionFactory.isSingleton = isSingleton;
	}

	/**
	 * コネクションを取得する。<br>
	 * プロジェクトによりここを変更する。
	 * @return
	 */
	static public Connection getConnection() {

		// derby
		// String driver = "org.apache.derby.jdbc.ClientDriver";
		// String url = "jdbc:derby://localhost:1527/db/db;create=true";
		// String user = "vipworks";
		// String password = "vipworks";

		// mariadb
		// String driver = "org.mariadb.jdbc.Driver;
		// String url = "jdbc:mariadb://localhost:3306/vipw";
		// String urser = "vipw";
		// String password = "vipw5";

		String driver = "org.sqlite.JDBC";
		String url = "jdbc:sqlite:" + FileUtil.getCurrentPath() +  "keybinds.db";
		String user = null;
		String password = null;

		return getConnection(driver, url, user, password);
	}

	static public Connection getConnection(String driver, String url, String user, String password) throws HinaOrmapperException {

		if (connection != null && isSingleton) {
			return connection;
		}

		try {

			Class.forName(driver); // 最近は必要なくなった。
			if (user == null || password == null) {
				connection = DriverManager.getConnection(url, user, password);
			} else {
				connection = DriverManager.getConnection(url);
			}

		} catch (ClassNotFoundException | SQLException e) {

			throw new HinaOrmapperException("コネクションの生成に失敗しました。", e);

		}

		return connection;

	}

}
