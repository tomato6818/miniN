package com.mini.log;

public class LOG {

	public void println(String log) {
		System.out.println(log);
	}

	public void printf(String logformat, Object ... args) {
		System.out.printf(logformat,args);
	}
}
