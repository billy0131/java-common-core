package com.ecwalk.common.other.spring.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecwalk.common.other.spring.annotation.SpringAutowired;
import com.ecwalk.common.other.spring.annotation.SpringController;
import com.ecwalk.common.other.spring.annotation.SpringRequestMapping;
import com.ecwalk.common.other.spring.annotation.SpringRequestParam;
import com.ecwalk.common.other.spring.service.HandWriteSpringService;

@SpringController
@SpringRequestMapping("spring")
public class HandWriteSpringController {

	@SpringAutowired("HandWriteSpringServiceImpl")
	private HandWriteSpringService handWriteSpringService;
	
	public void query(HttpServletRequest request,HttpServletResponse response,
			@SpringRequestParam("name")String name,
			@SpringRequestParam("age")String age){
		try {
			PrintWriter pWriter=response.getWriter();
			String result=handWriteSpringService.query(name,age);
			pWriter.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
