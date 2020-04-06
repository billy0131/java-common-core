package com.ecwalk.common.other.proxy.jdk;

import com.ecwalk.common.other.proxy.User;

public class UserServiceImpl implements UserService{

	@Override
	public void addUser(User user) {
		System.out.println("user"+user.toString());
		
	}

}
