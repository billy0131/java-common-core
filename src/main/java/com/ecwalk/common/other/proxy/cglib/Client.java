package com.ecwalk.common.other.proxy.cglib;

import com.ecwalk.common.other.proxy.User;

import net.sf.cglib.proxy.Enhancer;

public class Client {
	public static void main(String[] args) {
		User user=new User();
		user.setAddress("address");
		user.setAge(20);
		user.setName("00");
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(UserServiceImpl.class);
		enhancer.setCallback(new UserServiceInterceptor());
		UserServiceImpl usi1=(UserServiceImpl)enhancer.create();
		usi1.addUser(user);
	}
}
