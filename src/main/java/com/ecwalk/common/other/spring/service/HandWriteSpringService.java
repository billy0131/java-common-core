package com.ecwalk.common.other.spring.service;

import com.ecwalk.common.other.spring.annotation.SpringService;

@SpringService
public interface HandWriteSpringService {

	public String query(String name,String age);
}
