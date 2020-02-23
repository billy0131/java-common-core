package com.ecwalk.common.other.thread.safe;

/**
 * 
 * @author billy
 * 不共享或者不修改 1.无成员变量的类 2.成员变量不可变 3ThreadLocal （类似map key是线程id，value是线程值）
 * 共享但加锁 1.普通加锁 2cas (compare and swap 乐观锁)
 * sync上下文切换慢，等待的时候会被cpu移出当前线程（3～5的微秒）和cas没有
 *
 */
public class Stateless {

	private  final int i=0;
	
	public void add(int x,int y){
		
	}
}
