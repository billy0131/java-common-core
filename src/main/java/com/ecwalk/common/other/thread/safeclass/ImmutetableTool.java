package com.ecwalk.common.other.thread.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 不可变类，线程安全，没有可修改位置
 * @author billy
 *
 */
public class ImmutetableTool {
	
	private List<Integer> list=new ArrayList<Integer>(3);

	public ImmutetableTool() {
		list.add(1);
		list.add(2);
		list.add(3);
	}
	
	public boolean isContains(int i){
		return list.contains(i);
	}
}
