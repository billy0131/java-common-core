package com.ecwalk.common.other.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.ecwalk.common.other.proxy.User;

public class UserServiceInterceptor implements InvocationHandler{
	
	private Object object;

	public UserServiceInterceptor(Object object) {
		super();
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(args!=null&&args.length>0&&args[0] instanceof User){
			User user=(User) args[0];
			if(user.getName().trim().length()<1){
				throw new RuntimeException("user length must large than 1");
			}
		}
		Object ret 	=method.invoke(object, args);
		return ret;
	}

}
