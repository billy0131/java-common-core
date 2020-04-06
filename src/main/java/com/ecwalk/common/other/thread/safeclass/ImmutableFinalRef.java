package com.ecwalk.common.other.thread.safeclass;

public class ImmutableFinalRef {
	
	private final int a;
	private final int b;
	private final User user;

	public ImmutableFinalRef(int a, int b) {
		super();
		this.a = a;
		this.b = b;
		this.user=new User();
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
	
	public User getUser() {
		return user;
	}

	public static  class User{
		private int age;//这里线程不安全
//		private final int age;//这里线程安全

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
	
	public static void main(String[] args) {
		ImmutableFinalRef ref=new ImmutableFinalRef(1, 2);
		User user=ref.getUser();
		user.setAge(35);//这里线程不安全
	}

}
