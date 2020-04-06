package com.ecwalk.common.other.zookeeper.zk;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class AuthControlDemo implements Watcher{

	private static CountDownLatch latch=new CountDownLatch(1);
	private final static String CONNECTIONSTR="10.3.41.42:2182";
	private static ZooKeeper zooKeeper;
	private static Stat stat=new Stat();

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NoSuchAlgorithmException {

		zooKeeper=new ZooKeeper(CONNECTIONSTR, 5000, new AuthControlDemo());
		latch.await();
		System.out.println(zooKeeper.getState());
		
		ACL acl=new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
		ACL acl1=new ACL(ZooDefs.Perms.CREATE, new Id("ip", "192.168.0.156"));
		List<ACL> acls=new ArrayList<ACL>();
		
		acls.add(acl1);
		acls.add(acl);
		
		zooKeeper.create("/auth1", "123".getBytes(), acls, CreateMode.PERSISTENT);
		zooKeeper.addAuthInfo("digest", "root:root".getBytes());
		
		zooKeeper.create("/auth1/auth1-1", "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		
		ZooKeeper zooKeeper1=new ZooKeeper(CONNECTIONSTR, 5000, new AuthControlDemo());
		latch.await();
		
		zooKeeper1.addAuthInfo("digest", "root:root".getBytes());
		zooKeeper1.delete("/auth1/auth1-1", -1);
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

	}

}
