package com.ecwalk.common.other.zookeeper.zkclient;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorSessionDemo {

	private final static String CONNECTIONSTR="10.3.41.42:2182";


	public static void main(String[] args) throws InterruptedException {
		CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().connectString(CONNECTIONSTR)
				.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		curatorFramework.start();

		try {
			//创建
			curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
			.forPath("/curator/curator1/curator11","123".getBytes());

			//删除
			curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator");

			//更新
			Stat stat2=curatorFramework.setData().forPath("/node1","123".getBytes());

			//查询
			Stat stat=new Stat();
			byte[] bytes=curatorFramework.getData().storingStatIn(stat).forPath("/node1");
			System.out.println(new String(bytes)+"--->stat:"+stat);

			//异步操作
			ExecutorService service=Executors.newFixedThreadPool(1);
			final CountDownLatch countDownLatch=new CountDownLatch(1);
			curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL)
			.inBackground(new BackgroundCallback() {

				@Override
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName()+"->resultCode:"+event.getResultCode()
					+"->"+event.getType());
					countDownLatch.countDown();
				}
			},service).forPath("enjoy","deer".getBytes());
			countDownLatch.await();
			service.shutdown();
			
			//事务
			Collection<CuratorTransactionResult> results=curatorFramework.inTransaction()
					.create().forPath("/billy","123".getBytes()).and()
					.setData().forPath("/billy","111".getBytes()).and()
					.commit();
			for(CuratorTransactionResult result:results){
				System.out.println(result.getForPath()+"->"+result.getType());
			}
			
			//节点变化
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
