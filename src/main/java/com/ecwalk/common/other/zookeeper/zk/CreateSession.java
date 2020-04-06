package com.ecwalk.common.other.zookeeper.zk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class CreateSession {
	
	private static CountDownLatch latch=new CountDownLatch(1);
	private final static String CONNECTIONSTR="10.3.41.42:2182";
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		ZooKeeper zooKeeper=new ZooKeeper(CONNECTIONSTR, 5000, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				if(event.getState()==Event.KeeperState.SyncConnected){
					latch.countDown();
					System.out.println(event.getState());
				}else if(event.getType()==Event.EventType.NodeDataChanged){
					System.out.println(event.getPath());
				}
				
			}
			
		});
		latch.await();
		System.out.println(zooKeeper.getState());
		//节点名字
		//节点值
		//acl的
		//节点的类型
		zooKeeper.create("/billy", "jiajia".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		//获取数据
		Stat stat=new Stat();
		byte[] data=zooKeeper.getData("/billy", new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}}, stat);
		String result=new String(data);
		List<String> list=zooKeeper.getChildren("/billy", true);
		zooKeeper.delete("/billy", -1);
		
		
	}

}
