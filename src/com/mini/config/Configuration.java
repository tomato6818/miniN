package com.mini.config;

import java.util.HashMap;

import com.mini.log.LOG;

public class Configuration {

	private static final HashMap<String,String> map=new HashMap<String,String>();
	public static final String TEST_CONF = "";
	public static final int NIO_SERVER_PORT_NUMBER = 9891;
	public static final String NIO_SERVER_PORT_NUMBER_GET = "NIO_SERVER_PORT_NUMBER";
	public static final LOG log= new LOG();
	
	
	public Configuration(){
		map.put(NIO_SERVER_PORT_NUMBER_GET, NIO_SERVER_PORT_NUMBER+"");
	}
	
	public Configuration(String[] args) {
		this();
	}

	public Configuration(String confPath) {
		this();
	}

	public String get(String key) {

		return map.get(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(map.get(key));
	}

}
