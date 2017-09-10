package com.mini.config;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;

import com.mini.log.LOG;

public class Configuration {

	private static final HashMap<String,String> map=new HashMap<String,String>();
	public static final String TEST_CONF = "";
	public static final int NIO_SERVER_PORT_NUMBER = 9891;
	public static final String NIO_SERVER_PORT_NUMBER_GET = "NIO_SERVER_PORT_NUMBER";
	public static final LOG log= new LOG();
	
	public static final String UTF_8 = "UTF-8";
	public static final CharsetEncoder charEncoder = Charset.forName(UTF_8).newEncoder();
	public static final CharsetDecoder charDecoder = Charset.forName(UTF_8).newDecoder();
	
	
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
