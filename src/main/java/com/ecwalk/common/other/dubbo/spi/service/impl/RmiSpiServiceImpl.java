package com.ecwalk.common.other.dubbo.spi.service.impl;

import com.ecwalk.common.other.dubbo.spi.service.SpiService;

public class RmiSpiServiceImpl implements SpiService{

	@Override
	public void sayHello() {
		System.out.println("rmi");
		
	}

}
