package com.ecwalk.common.other.protocol.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String msg="hello 13号技师";
		//创建一个socket，和本级的8888端口进行连接
		Socket socket=new Socket("127.0.0.1",8888);
		//使用socket创建一个PrintWriter
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream());
		//发送数据
		printWriter.println(msg);
		//刷新以下，让服务器立马收到信息
		printWriter.flush();
		
		printWriter.close();
		socket.close();
	}

}
