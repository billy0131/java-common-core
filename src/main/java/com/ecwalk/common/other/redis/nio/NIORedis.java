package com.ecwalk.common.other.redis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NIORedis {
	
	static ArrayList<SocketChannel> socketList=new ArrayList<>();
	static ByteBuffer byteBuffer=ByteBuffer.allocate(512);
	
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocket=ServerSocketChannel.open();
		SocketAddress socketAddress=new InetSocketAddress("127.0.0.1",6379);
		serverSocket.bind(socketAddress);
		serverSocket.configureBlocking(false);//非阻塞
		while(true){
			for(SocketChannel socketChannel:socketList){
				int read =socketChannel.read(byteBuffer);
				if(read>0){
					System.out.println("read---"+read);
					byteBuffer.flip();
					
					byte[] bs=new byte[read];
					byteBuffer.get(bs);
					String content=new String(bs);
					System.out.println(content);
					byteBuffer.flip();
				}else if(read==-1){
					socketList.remove(socketChannel);
				}
			}
			
			SocketChannel accept=serverSocket.accept();
			if(accept!=null){
				System.out.println("connect ");
				accept.configureBlocking(false);
				socketList.add(accept);
			}
		}
	}

}
