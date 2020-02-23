package com.ecwalk.common.other.redis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisClient {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(6379);
		Socket rec=serverSocket.accept();
		byte[] result=new byte[2048];
		rec.getInputStream().read(result);
		System.out.println(new String(result));
	}
}
