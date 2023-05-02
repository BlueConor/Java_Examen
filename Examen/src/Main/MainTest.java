package Main;

import java.time.LocalDateTime;

import ti208.log.Log;
import ti208.log.LogGenerator;
import ti208.log.LogLevel;

public class MainTest {

	public static void main(String[] args) {
		while(true) {
			Log log = LogGenerator.readLog();
			LocalDateTime time = log.getDatetime();
			LogLevel level = log.getLevel();
			String message = log.getText();
			
			System.out.println(log + "\n- " + time + "\n- " + level + "\n- " + message);
		}
	}

}
