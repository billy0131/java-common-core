package com.ecwalk.common.other.protocol.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP发送
 * @author billy
 *
 */
public class SendDemo {
	public static void main(String[] args) throws IOException {
		//创建一个DatagramSocket实例
		DatagramSocket datagramSocket=new DatagramSocket();
		//使用键盘输入构建一个BufferedReader
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
		String line=null;
		while((line=bufferedReader.readLine())!=null){
			//转出byte
			byte[] bytes=line.getBytes();
			//创建一个用于发送的datagramPacket
			DatagramPacket datagramPacket=new DatagramPacket(bytes, bytes.length,InetAddress.getByName("127.0.0.1"),10005);
			//发送数据包
			datagramSocket.send(datagramPacket);
			if("88".equals(line)){
				break;
			}
		}
		datagramSocket.close();
	}
}
