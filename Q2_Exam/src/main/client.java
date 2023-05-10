package main;

import ti208.log.Log;
import ti208.log.LogGenerator;
import ti208.log.LogLevel;

public class client {
	public static void main(String[] args) {
		
		while(true) {
		Log log = LogGenerator.readLog();
		System.out.println(log.getDatetime());
		System.out.println(log.getLevel());
		System.out.println(log.getText());
		}
	}
}
