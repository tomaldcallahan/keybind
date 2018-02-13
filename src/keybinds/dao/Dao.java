package keybinds.dao;

import java.util.ArrayList;
import java.util.List;

import keybinds.dto.KeyBind;
import util.exception.HinaException;
import util.exception.HinaOrmapperException;
import util.ormapper.HinaOrmapper;

public class Dao {

	/**
	 * 削除メソッド
	 * @param kind
	 * @param key
	 * @return
	 */
	public static int delete(String kind, String key) {

		int result = 0;
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "delete from keybinds where kind = ? and key = ?";

			result = ho.excecutedUpdate(sql, kind, key);

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in delete sql.", e);
		}

		return result;
	}

	/**
	 * インサートメソッド
	 * @param kind
	 * @param key
	 * @param name
	 * @param path
	 * @return
	 */
	public static int insert(String kind, String key, String name, String path) {

		insertTime = System.currentTimeMillis();

		int result = 0;
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "insert into keybinds values( ?, ?, ?, ?) ";

			ho.excecutedUpdate(sql, kind, key, path, name);

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in insert sql.", e);
		}
		return result;

	}

	/**
	 * インサートした時間を保持
	 */
	private static long insertTime = 0;

	/**
	 * kindを条件に検索するメソッド
	 * @param kind
	 * @return
	 */
	public static List<KeyBind> selectByKindl(String kind) {

		List<KeyBind> keyBindList = new ArrayList<>();
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "select * from keybinds where kind = ?";

			keyBindList = ho.excecuteQuery(sql, KeyBind.class, kind);

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in selectByKind sql.", e);
		}
		return keyBindList;
	}

	public static KeyBind selectByKindlAndKey(String kind, String key) {

		KeyBind keyBind = null;
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "select * from keybinds where kind = ? and key = ?";

			keyBind = ho.executeQueryOne(sql, KeyBind.class, kind, key);

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in selectByKind sql.", e);
		}
		return keyBind;
	}

	public static List<KeyBind> selectAll() {

		List<KeyBind> keyBindList = new ArrayList<>();
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "select * from keybinds order by kind, key";

			keyBindList = ho.excecuteQuery(sql, KeyBind.class, new String[] {});

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in selectByKind sql.", e);
		}
		return keyBindList;
	}

	/**
	 * 現在のkeyのリストを返却する
	 * @return
	 */
	public static List<KeyBind> getKeys() {

		if (getKeysList != null && insertTime == getKeysTime)
			return getKeysList;

		getKeysList = new ArrayList<>();
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "select distinct key from keybinds";

			getKeysList = ho.excecuteQuery(sql, KeyBind.class, new String[] {});

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in getKeys sql.", e);
		}

		return getKeysList;
	}

	private static long getKeysTime = 0;
	private static List<KeyBind> getKeysList = null;

	/**
	 * 現在のkindのリストを返却する
	 * @return
	 */
	public static List<KeyBind> getKinds() {

		if (getKindsList != null && insertTime == getKindsTime)
			return getKindsList;

		getKindsList = new ArrayList<>();
		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "select distinct kind from keybinds";

			getKindsList = ho.excecuteQuery(sql, KeyBind.class, new String[] {});

		} catch (HinaOrmapperException e) {
			throw new HinaException("exception in getKinds sql.", e);
		}
		return getKindsList;
	}

	private static long getKindsTime = 0;
	private static List<KeyBind> getKindsList = null;

}
