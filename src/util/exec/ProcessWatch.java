package util.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import util.exception.HinaException;

public class ProcessWatch {

	private static final String TASKLIST = "tasklist";

    /**
     * プロセスが起動しているかを問い合わせる。
     *
     * @param processName プロセス名
     * @return
     * @throws HinaException
     */
    public static boolean isRun(String processName) throws HinaException {
        try {

        	Process p = new ProcessBuilder(ProcessWatch.TASKLIST).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            try {

            	while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    if(line.toUpperCase().startsWith(processName.toUpperCase())) {
                        return true;
                    }
                }

            } finally {
                br.close();
            }
        } catch (IOException e) {

            throw new HinaException("プロセスの取得に失敗しました。", e);
        }

        return false;
    }

    public static List<String> getProsessId(String processName) throws HinaException {

    	List<String> pIdList = new ArrayList<String>();

        try {

        	Process p = new ProcessBuilder(ProcessWatch.TASKLIST).start();
        	BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        	try {

        		while (true) {
        			String line = br.readLine();
        			if (line == null) {
        				break;
        			}
        			if(line.toUpperCase().startsWith(processName.toUpperCase())) {
        				String[] row = line.replaceAll("\\s+", ",").split(",", -1);
        				//System.out.println("イメージ名：" + row[0] + "\tPID:" + row[1]);
        				pIdList.add(row[1]);
        			}
        		}

        	} finally {
        		br.close();
        	}
        } catch (IOException e) {

        	throw new HinaException("プロセスの取得に失敗しました。", e);
        }

        return pIdList;
    }

}
