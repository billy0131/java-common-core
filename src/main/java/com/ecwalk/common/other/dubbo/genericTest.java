package com.ecwalk.common.other.dubbo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class genericTest implements ApplicationContextAware{

	private ApplicationContext ctx;

	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public HashMap test (HttpServletRequest request,HttpServletResponse response) {
		String[] serviceIds=new String[]{"productService","userService","orderService","payService"};
		HashMap<String, String	> retMap=new HashMap<>();
		Object ret=null;
		for(String id:serviceIds){
			try {
				EchoService echoService=(EchoService)ctx.getBean(id);
				ret=echoService.$echo("ok");
				retMap.put(id, ret.toString());
			} catch (Exception e) {
				retMap.put(id, "not ready");
			}
			
		}
		return retMap;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub

	}
}
