package com.ecwalk.common.other.thread.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 不可变类，线程安全，没有可修改位置
 * @author billy
 *
 */
public class UnsafePublish {
	
	private List<Integer> list=new ArrayList<Integer>(3);

	public UnsafePublish() {
		list.add(1);
		list.add(2);
		list.add(3);
	}

	public List<Integer> getList() {//不安全发布
		return list;
	}
	
	public synchronized int getList(int index) {
		return list.get(index);
	}

	public synchronized void setList(int index,int val) {
		list.set(index, val);
	}
	
}
