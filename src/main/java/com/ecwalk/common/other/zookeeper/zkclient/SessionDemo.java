package com.ecwalk.common.other.zookeeper.zkclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class SessionDemo {
	
	private final static String CONNECTIONSTR="10.3.41.42:2182";
	
	private static ZkClient getInstance(){
		return new ZkClient(CONNECTIONSTR,10000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkClient=getInstance();
		zkClient.createPersistent("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1",true);
		System.out.println("success");
		
		List<String> list=zkClient.getChildren("/zkclient");
		System.out.println(list);
		
		zkClient.deleteRecursive("/zkclient");
		
		//内容变化
		zkClient.subscribeDataChanges("/demoenjoy", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				
				
			}
			
			@Override
			public void handleDataChange(String s, Object o) throws Exception {
				System.out.println("节点名称："+s+"->节点修改后的值️+o");
				
			}
		});
		
		zkClient.writeData("/demoenjoy", "node");
		TimeUnit.SECONDS.sleep(2);
		
		//内容变化
		zkClient.subscribeDataChanges("/demoenjoy", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				
				
			}
			
			@Override
			public void handleDataChange(String s, Object o) throws Exception {
				System.out.println("节点名称："+s+"->节点修改后的值️+o");
				
			}
		});
		
		zkClient.delete("/demoenjoy/enjoy1");
		TimeUnit.SECONDS.sleep(2);
	}

}
