package com.ecwalk.common.other.proxy.jdk;

import java.lang.reflect.Proxy;

import com.ecwalk.common.other.proxy.User;

public class Client {
	public static void main(String[] args) {
		User user=new User();
		user.setAddress("address");
		user.setAge(20);
		user.setName("");
		UserService service=new UserServiceImpl();
		UserServiceInterceptor usi=new UserServiceInterceptor(service);
		UserService proxy=(UserService)Proxy.newProxyInstance(service.getClass().getClassLoader(), 
				service.getClass().getInterfaces(), usi);
		proxy.addUser(user);
	}
}
