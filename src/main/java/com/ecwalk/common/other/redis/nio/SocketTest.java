package com.ecwalk.common.other.redis.nio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {
	
	public static void main(String[] args) throws  IOException {
		Socket socket=new Socket("127.0.0.1",6379);
		Scanner scanner=new Scanner(System.in);
		String next=scanner.next();
		socket.getOutputStream().write(next.getBytes());
		socket.close();
	}

}
