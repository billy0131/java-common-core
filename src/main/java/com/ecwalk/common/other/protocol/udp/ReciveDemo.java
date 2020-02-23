package com.ecwalk.common.other.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP接收
 * @author billy
 *
 */
public class ReciveDemo {
	public static void main(String[] args) throws IOException {
		//创建一个DatagramSocket，并且把实例绑定到本级地址，端口10005
		DatagramSocket datagramSocket=new DatagramSocket(10005);
		byte bytes[]=new byte[1024];
		//以空数组创建DatagramPacket，用作接受DatagramSocket的数据
		DatagramPacket datagramPacket=new DatagramPacket(bytes, bytes.length);
		//无限循环，监听数据什么时候来
		while(true){
			//接收数据包
			datagramSocket.receive(datagramPacket);
			//获取接收的数据
			byte[] data=datagramPacket.getData();
			String str=new String(data,0,datagramPacket.getLength());
			if("88".equals(str)){
				break;
			}
			System.out.println("接收到数据为："+str);
		}
		datagramSocket.close();
	}
}
