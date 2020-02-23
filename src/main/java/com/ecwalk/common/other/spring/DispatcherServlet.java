package com.ecwalk.common.other.spring;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecwalk.common.other.spring.annotation.SpringAutowired;
import com.ecwalk.common.other.spring.annotation.SpringController;
import com.ecwalk.common.other.spring.annotation.SpringRequestMapping;
import com.ecwalk.common.other.spring.annotation.SpringRequestParam;
import com.ecwalk.common.other.spring.annotation.SpringService;
import com.ecwalk.common.other.spring.controller.HandWriteSpringController;

public class DispatcherServlet extends HttpServlet{

	List<String> classUrls=new ArrayList<>();//保存待实例化所有类的路径
	Map<String, Object> ioc=new HashMap<String, Object>();//ioc容器
	Map<String, Object> urlHandlers=new HashMap<String, Object>();//地址映射
	
	
	public void init(ServletConfig config ) throws ServletException{
		doScanPackage("com.ecwalk.common.other.spring");
		doInstance();
		doAutowired();
		doUrlMapping();
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String uri=request.getRequestURI();// /mvc/billy/query 
		String context=request.getContextPath();// /mvc
		String path=uri.replace(context, "");// path=/billy/query 
		
		Method method=(Method)urlHandlers.get(path);
		HandWriteSpringController instance=(HandWriteSpringController)ioc.get("/"+path.split("/")[1]);
		Object[] args=hand(request, response, method);
		try {
			method.invoke(instance, args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doScanPackage(String basePackage){
		URL url=this.getClass().getClassLoader().getResource("/"+basePackage.replaceAll("\\.", "/"));
		String fileStr=url.getFile();
		File file=new File(fileStr);
		
		String[] filesStr=file.list();
		
		for(String path:filesStr){
			File filePath=new File(fileStr+path);
			if(filePath.isDirectory()){
				doScanPackage(basePackage+"."+path);
			}else{
				classUrls.add(basePackage+"."+filePath.getName().replace(".class", ""));
			}
		}
	}
	
	public void doInstance(){
		for(String classUrl:classUrls){
			try {
				Class<?> clazz=Class.forName(classUrl);
				if(clazz.isAnnotationPresent(SpringController.class)){
					Object instanceController=clazz.getInterfaces();
					SpringRequestMapping mapl=clazz.getAnnotation(SpringRequestMapping.class);
					String key=mapl.value();
					ioc.put(key, instanceController);
				}else if(clazz.isAnnotationPresent(SpringService.class)){
					Object instanceService=clazz.getInterfaces();
					SpringService es=clazz.getAnnotation(SpringService.class);
					String key=es.value();
					ioc.put(key, instanceService);
				}else{
					continue;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doAutowired(){
		for(Map.Entry<String, Object> entry:ioc.entrySet()){
			Object instance=entry.getValue();
			Class<?> clazz=instance.getClass();
			if(clazz.isAnnotationPresent(SpringController.class)){
				Field[] fields=clazz.getFields();
				for(Field field:fields){
					if(field.isAnnotationPresent(SpringAutowired.class)){
						SpringAutowired ea=field.getAnnotation(SpringAutowired.class);
						String key=ea.value();
						Object  ins= ioc.get(key);
						field.setAccessible(true);
						try {
							field.set(instance,ins);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void doUrlMapping(){
		for(Map.Entry<String, Object> entry:ioc.entrySet()){
			Object instance=entry.getValue();
			Class<?> clazz=instance.getClass();
			if(clazz.isAnnotationPresent(SpringController.class)){
				SpringRequestMapping map1=clazz.getAnnotation(SpringRequestMapping.class);
				String classPath=map1.value();
				Method[] methods=clazz.getMethods();
				for(Method method:methods){
					if(method.isAnnotationPresent(SpringRequestMapping.class)){
						SpringRequestMapping map2=method.getAnnotation(SpringRequestMapping.class);
						String methodPath=map2.value();
						urlHandlers.put(classPath+methodPath, method);
					}
				}
			}
		}
	}
	
	
	private static Object[] hand(HttpServletRequest request,HttpServletResponse response,Method method){
		//拿到当前待执行的方法有哪些参数
		Class<?>[] paramClazzs=method.getParameterTypes();
		//根据参数的个数，new一个参数数组
		Object[] args=new Object[paramClazzs.length];
		
		int args_i=0;
		int index =0;
		for(Class<?> paramClazz:paramClazzs){
			if(ServletRequest.class.isAssignableFrom(paramClazz)){
				args[args_i++]=request;
			}
			if(ServletResponse.class.isAssignableFrom(paramClazz)){
				args[args_i++]=response;
			}
			
			Annotation[] paramAns=method.getParameterAnnotations()[index];
			if(paramAns.length>0){
				for(Annotation paramAn:paramAns){
					if(SpringRequestParam.class.isAssignableFrom(paramAn.getClass())){
						SpringRequestParam rParam=(SpringRequestParam)paramAn;
						args[args_i++]=request.getParameter(rParam.value());
					}
				}
			}
			index++;
		}
		return args;
	}
}
