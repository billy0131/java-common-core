package com.ecwalk.common.other.protocol.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

public class TcpServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(8888);
		System.out.println("TCP服务器已启动");
		while(true){
			//等待客户端请求，如果有请求分配一个socket
			Socket socket=serverSocket.accept();
			//根据标准输入一个构建的BufferedReader
			BufferedReader reader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String buffer=null;
			//循环读取
			while((buffer=reader.readLine())!=null&&StringUtils.isNotBlank(buffer)){
				System.out.println(buffer);
			}
			//通过socket对象得到输出流，构造BufferedWriter
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write("HTTP/1.1 200 OK \r\n Content-Type:text/html \r\n charset=UTF-8 \r\n\r\n ");
			writer.write("<html><head><title></title></head><body><h1>这个是HTTP请求</h1></body></html>");
			//刷新输出流，使得数据立马发送
			writer.flush();
			
			reader.close();
			writer.close();
			socket.close();
		}
	}
}
