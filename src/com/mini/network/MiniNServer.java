package com.mini.network;

import com.mini.config.Configuration;

public class MiniNServer {
	
	public MiniNServer(){
		
	}
	
	public void start(String[] args){
		Configuration conf=new Configuration(args);
		
		String any=conf.get(conf.TEST_CONF);
	}
	
	public static void main(String[] args){
		MiniNServer n=new MiniNServer();
		n.start(args);
	}

}
