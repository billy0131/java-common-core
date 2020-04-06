package com.ecwalk.common.other.dubbo.spi.service.impl;

import com.ecwalk.common.other.dubbo.spi.service.SpiService;

public class DubboSpiServiceImpl implements SpiService{

	@Override
	public void sayHello() {
		System.out.println("dubbo");
		
	}

}
