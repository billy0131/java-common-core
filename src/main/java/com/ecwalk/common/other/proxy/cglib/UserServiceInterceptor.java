package com.ecwalk.common.other.proxy.cglib;

import java.lang.reflect.Method;

import com.ecwalk.common.other.proxy.User;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UserServiceInterceptor implements MethodInterceptor{

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(args!=null&&args.length>0&&args[0] instanceof User){
			User user=(User) args[0];
			if(user.getName().trim().length()<1){
				throw new RuntimeException("user length must large than 1");
			}
		}
		Object ret 	=proxy.invokeSuper(obj, args);
		return ret;
	}

}
