package com.mini.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.mini.config.Configuration;

public class MiniNServer {

	Configuration conf;
	Listener listener;
	public MiniNServer() {

	}

	public void start(String[] args) {
		conf = new Configuration(args);

		String any = conf.get(conf.TEST_CONF);
		try {
			listener = new Listener();
			listener.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MiniNServer n = new MiniNServer();
		n.start(args);
	}

	public class Listener extends Thread {
		ServerSocketChannel acceptChannel = null;
		Selector selector=null;
		public Listener() throws IOException {
			acceptChannel = ServerSocketChannel.open();
			acceptChannel.socket().bind(new InetSocketAddress(conf.getInt(Configuration.NIO_SERVER_PORT_NUMBER_GET)));
			acceptChannel.configureBlocking(false);

			selector = Selector.open();
			acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			this.setDaemon(true);
			this.setName("Server listener on port:" + conf.getInt(Configuration.NIO_SERVER_PORT_NUMBER_GET));
			
			System.out.println("Listener created");
		}

		public void run() { 
			try{
				
				conf.log.println("Server :: waiting for accept");
				
				while(true){
					selector.select(3000);
					conf.log.println("Server :: select");

					Iterator<SelectionKey> selectionKey = selector.selectedKeys().iterator();
					
					while(selectionKey.hasNext()){
						SelectionKey key = selectionKey.next();
						selectionKey.remove();
						if(key.isValid()){
							if(key.isAcceptable()){
								doAccept(key);
							}else if(key.isReadable()){
								doRead(key);
							}
						}
						
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		public void doAccept(SelectionKey key)throws IOException{
			ServerSocketChannel server = (ServerSocketChannel)key.channel();
			SocketChannel client= server.accept();
			client.configureBlocking(false);
			
			SelectionKey readKey = client.register(selector, SelectionKey.OP_READ);
			
			readKey.attach(new Connection(client,readKey));
		}

		public void doRead(SelectionKey key)throws IOException{
			Connection c = (Connection)key.attachment();
			c.readAndProcess();
		}
	}
	
	
	public class Connection{
		SocketChannel client;
		SelectionKey key;
		
		Connection(SocketChannel client,SelectionKey key){
			this.client=client;
			this.key=key;
		}
		
		public int readAndProcess() throws IOException{
			ByteBuffer buff=ByteBuffer.allocateDirect(Byte.MAX_VALUE);
			int count = client.read(buff);
			String receivedMsg=null;
			if(buff.remaining()==0){
				buff.flip();
				receivedMsg=buff.toString();
				System.out.println("good reveice: "+receivedMsg);
			}
			
			ByteBuffer outBuff=ByteBuffer.wrap(("i was received messages : " +receivedMsg).getBytes() );
			client.write(outBuff);
			
			client.close();
			return count;
		}
		
	}

}
