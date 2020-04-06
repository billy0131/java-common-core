package com.ecwalk.common.other.dubbo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.rpc.service.EchoService;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class echoTest implements ApplicationContextAware{

	private ApplicationContext ctx;

	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public String test (HttpServletRequest request,HttpServletResponse response) {
		GenericService genericService=(GenericService)ctx.getBean("bravesService");
		Object ret=genericService.$invoke("getDetail", new String[]{"java.lang.String"}, new Object[]{"name"});
		return ret.toString();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub

	}
}
