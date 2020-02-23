package com.ecwalk.common.other.thread.cas;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {

	static AtomicReference<UserInfo> userReference=new AtomicReference<UserInfo>();

	public static void main (String[] args){
		UserInfo userInfo=new UserInfo("Billy", 29);//要修改的实体
		userReference.set(userInfo);
		UserInfo updateUserInfo=new UserInfo("Jia", 28);//要变化的实体
		userReference.compareAndSet(userInfo, updateUserInfo);//这里已经变化了
		
		System.out.println(userReference.get().getName());
		System.out.println(userReference.get().getAge());
		
		
		System.out.println(userInfo.getName());
		System.out.println(userInfo.getAge());
	}

	static class UserInfo{
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public int getAge() {
			return age;
		}
		public UserInfo(String name,int age){
			this.age=age;
			this.name=name;
		}
	}

}
