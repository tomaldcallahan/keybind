package keybinds.task;

import java.util.List;
import java.util.Scanner;

import keybinds.dao.Dao;
import keybinds.dto.KeyBind;
import util.exec.AppExecUtil;
import util.robot.RobotUtil;

public class CommandTask implements Runnable {

	@Override
	public void run() {

		while (true) {

			String menu = ""
					+ "--- menu ---\n"
					+ "* pid : " + AppExecUtil.getProssesId() + "\n"
					+ "1 : list by kind\n"
					+ "2 : list all\n"
					+ "3 : add\n"
					+ "4 : delete\n"
					+ "5 : set delay time\n"
					+ "6 : set window rotate\n"
					+ "7 : reset\n"
					+ "exit : end";
			System.out.println(menu);

			// プロンプト
			System.out.print("menu > ");

			// コマンド受付
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			switch (input) {
			case "1":
				list();
				break;
			case "2":
				listAll();
				break;
			case "3": // add
				add();
				break;
			case "4":
				delete();
				break;
			case "5":
				setDelayTime();
				break;
			case "6":
				setWindowRotate();
				break;
			case "7":
				reset();
				break;
			case "exit":
				// システム終了
				System.out.println("Thank you. bye.");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("I can't find the comman.");
				System.out.println(menu);
				break;
			}

		}

	}

	void add() {

		String kind = null;
		String key = null;
		String path = null;
		String name = null;

		Scanner scanner = new Scanner(System.in);
		System.out.println("--- add ---");

		printKind();

		kind = scanner.nextLine();

		System.out.print("key > ");
		key = scanner.nextLine();

		KeyBind keyBind = Dao.selectByKindlAndKey(kind, key);
		if (keyBind != null) {
			System.out.println("The keyBind exists. Can I update keyBind ?");
			System.out.print("y or n(other) > ");
			if (!scanner.nextLine().equals("y")) {
				return;
			}
			Dao.delete(kind, key);
		}

		System.out.print("path > ");
		path = scanner.nextLine();

		System.out.print("name > ");
		name = scanner.nextLine();

		Dao.insert(kind, key, name, path);

	}

	public void delete() {

		String kind = null;
		String key = null;

		Scanner scanner = new Scanner(System.in);
		System.out.println("--- delete ---");

		printKind();
		kind = scanner.nextLine();

		System.out.print("key : ");
		key = scanner.nextLine();

		Dao.delete(kind, key);
	}

	public void list() {

		System.out.println("--- list ---");

		printKind();
		Scanner scanner = new Scanner(System.in);
		String kind = scanner.nextLine();

		List<KeyBind> keyBindList = Dao.selectByKindl(kind);
		for (KeyBind keyBind : keyBindList) {
			System.out.println(keyBind);
		}

	}

	public void listAll() {

		System.out.println("--- list all ---");

		List<KeyBind> keyBindList = Dao.selectAll();
		for (KeyBind keyBind : keyBindList) {
			System.out.println(keyBind);
		}
	}

	private void printKind() {
		System.out.println(""
				+ "e : explorer\n"
				+ "p : program\n"
				+ "o : other");
		System.out.print("kind > ");
	}

	private void setDelayTime() {
		System.out.println("--- set delay time ---");

		Scanner scanner = new Scanner(System.in);
		try {

			System.out.print("delay time (mill second) > ");
			int delayTime = scanner.nextInt();
			RobotUtil.setDelayTime(delayTime);

		} catch (Exception e) {
			System.out.println("you can input number.");
		}

	}

	private void setWindowRotate() {
		System.out.println("--- set window rotate ---");

		Scanner scanner = new Scanner(System.in);
		try {

			System.out.print("y or n(other) > ");
			String input = scanner.nextLine();
			if ("y".equalsIgnoreCase(input)) {
				AppExec.setWindowRotate(true);
			} else {
				AppExec.setWindowRotate(false);
			}

		} catch (Exception e) {
			System.out.println("you can input number.");
		}
	}

	private void reset() {
		System.out.println("--- reset ---");

		AppExec.setFirst(true);
	}

}
