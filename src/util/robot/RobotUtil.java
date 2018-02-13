package util.robot;

import static java.awt.event.KeyEvent.*;

import java.awt.AWTException;
import java.awt.Robot;

import util.exception.HinaException;

public class RobotUtil {

	private static Robot robot = null;

	private static int delayTime = 200;

	public static void setDelayTime(int delayTime) {
		RobotUtil.delayTime = delayTime;
	}

	public static void init() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new HinaException("I can't make robot.", e);
		}
	}

	public static void keybordClick(int... args) {

		delay();

		for (int key : args) {
			robot.keyPress(key);
		}
		//同時押しの場合はShift等は後に離したいので逆順
		for (int i = args.length - 1; i >= 0; i--) {
			robot.keyRelease(args[i]);
		}

		delay();

	}

	public static void altAndShiftAndTab() {
		keybordClick(VK_ALT, VK_SHIFT, VK_TAB);
		keybordClick(VK_TAB);
	}

	public static void windowsKeyAnd1() {
		keybordClick(VK_WINDOWS, VK_1);

	}

	public static void delay(){
		robot.delay(delayTime);
	}

}
