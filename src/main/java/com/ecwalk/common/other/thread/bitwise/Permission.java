package com.ecwalk.common.other.thread.bitwise;

public class Permission {
	
	//是否允许查询，二进制第一位，0表示否，1表示是
	public static final int ALLOW_SELECT=1<<0;//0001 =1
	
	//是否允许新增，二进制第二位，0表示否，1表示是
	public static final int ALLOW_INSERT=1<<1;//0010 =2
	
	//是否允许修改，二进制第三位，0表示否，1表示是
	public static final int ALLOW_UPDATE=1<<2;//0100 =4
	
	//是否允许删除，二进制第四位，0表示否，1表示是
	public static final int ALLOW_DELETE=1<<3;//1000 =8
	
	//存储目前的权限
	private int flag;

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	//增加用户权限（1个或多个）
	public void enable(int per){
		flag=flag|per;
	}
	
	//删除用户的权限（1个或多个）
	public void disable(int per){
		flag=flag&~per;
	}
	
	//判断用户的权限
	public boolean isAllow(int per){
		return ((flag&per)==per);
	}
	
	//判断用户没有的权限
	public boolean isNotAllow(int per){
		return ((flag&per)==0);
	}
	
	public static void main(String[] args) {
		int flag=15;
		Permission permission=new Permission();
		permission.setFlag(flag);
		permission.disable(ALLOW_DELETE|ALLOW_INSERT);
		System.out.println("select ="+permission.isAllow(ALLOW_SELECT));
		System.out.println("update ="+permission.isAllow(ALLOW_UPDATE));
		System.out.println("insert ="+permission.isAllow(ALLOW_INSERT));
		System.out.println("delete ="+permission.isAllow(ALLOW_DELETE));
	}
}
