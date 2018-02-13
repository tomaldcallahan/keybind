package keybinds;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import util.ormapper.HinaOrmapper;

public class OrmapperInsertTest {

	@Test
	public void test() {

		String kind = null;
		String key = null;
		String path = null;
		String name = null;

		try (Scanner scanner = new Scanner(System.in)) {

			System.out.println("kind : e->explorer   p->program   o->other");
			System.out.print("kind : ");
			kind = scanner.nextLine();

			System.out.print("key : ");
			key = scanner.nextLine();

			System.out.print("path : ");
			path = scanner.nextLine();

			System.out.print("name : ");
			name = scanner.nextLine();

		}

		try (HinaOrmapper ho = new HinaOrmapper()) {

			String sql = "insert into keybinds values( ?, ?, ?, ?) ";

			ho.excecutedUpdate(sql, kind, key, path, name);

		}

		assertTrue(true);

	}

}
