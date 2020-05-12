package com.ecwalk.common.other.redis.split;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RedisProxy {
	

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(19000);
		Socket socket;
		if((socket=serverSocket.accept())!=null){
			while(true){
				InputStream inputStream=socket.getInputStream();
				byte[] request=new byte[1024];
				inputStream.read(request);
				String req=new String(request);
				
				String[] params=req.split("\r\n");
				
				String param=params[2];
				String[] serverInfo=null;
				if("SET".equals(param)){
					
				}
			}
		}
	}
}
