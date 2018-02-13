package keybinds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import keybinds.dto.KeyBind;
import util.ormapper.HinaOrmapper;

public class OrmapperSelectTest {

	@Test
	public void test() {

		try (HinaOrmapper ho = new HinaOrmapper()) {

			// select all
			System.out.println("--- select all TEST ---");

			String sql = "select * from keybinds";

			List<KeyBind> list = ho.excecuteQuery(sql, KeyBind.class, new String[] {});

			list.forEach(System.out::println);

			// select by primary key
			System.out.println("--- select by primary key TEST ---");

			Scanner scanner = new Scanner(System.in);
			System.out.print("kind : ");
			String kind = scanner.nextLine();
			System.out.print("key : ");
			String key = scanner.nextLine();

			sql = "select * from keybinds where kind = ? and key = ?";

			KeyBind keyBind = ho.executeQueryOne(sql, KeyBind.class, new String[] { kind, key });
			System.out.println(keyBind);

		}

		assertTrue(true);

	}

}
