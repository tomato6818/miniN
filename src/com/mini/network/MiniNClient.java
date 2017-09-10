package com.mini.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;

import com.mini.config.Configuration;

public class MiniNClient {
	
	public static void main(String[] args) throws IOException{
		Configuration conf=new Configuration("");
		SocketChannel channel=SocketChannel.open();
		channel.configureBlocking(false);
		System.out.println(conf.getInt(conf.NIO_SERVER_PORT_NUMBER_GET));
		channel.connect(new InetSocketAddress("127.0.0.1",conf.getInt(conf.NIO_SERVER_PORT_NUMBER_GET)));
		
		Selector selector=Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);	
		while(true){
			
			selector.select();
			
			Iterator<SelectionKey> SelectionKeys=selector.selectedKeys().iterator();
			
			while(SelectionKeys.hasNext()){
				SelectionKey key= SelectionKeys.next();
				SelectionKeys.remove();
				if(key.isConnectable()){
					if(channel.isConnectionPending()){
						if(channel.finishConnect()){
							ByteBuffer buff=conf.charEncoder.encode(CharBuffer.wrap("Hello world"));
							System.out.println(" client write length: " +channel.write(buff)); 
							channel.register(selector, SelectionKey.OP_READ);
							
						}else{
							System.out.println("key cancel");
							key.cancel();
						}
					}
				}else if(key.isReadable()){
					ByteBuffer buff=ByteBuffer.allocateDirect(1024);
					channel.read(buff);
					buff.flip();
					
					CharBuffer charBuffer=conf.charDecoder.decode(buff);
					
					System.out.println(" client read ::"+charBuffer.toString());
					
					return;
				}
			}
		}
		
		
		
		
	}
}
