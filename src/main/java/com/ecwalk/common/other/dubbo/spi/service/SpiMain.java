package com.ecwalk.common.other.dubbo.spi.service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiMain {
	
	public static void main(String[] args) {
		//把所有SpiService的实现方法loader出来
		ServiceLoader<SpiService> serviceLoader=ServiceLoader.load(SpiService.class);
		Iterator<SpiService> iterable=serviceLoader.iterator();
		while (iterable.hasNext()) {
			SpiService spiService = (SpiService) iterable.next();
			spiService.sayHello();
		}
	}

}
