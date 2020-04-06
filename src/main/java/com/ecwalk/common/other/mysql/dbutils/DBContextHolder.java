package com.ecwalk.common.other.mysql.dbutils;

import com.ecwalk.common.other.mysql.vo.DBTypeEnum;

/**
 * 数据源持有
 * @author billy
 *
 */
public class DBContextHolder {
	private static final ThreadLocal<DBTypeEnum> contextHolder=new ThreadLocal<DBTypeEnum>();
	
	public static void set(DBTypeEnum dbTypeEnum){
		contextHolder.set(dbTypeEnum);
	}
	
	public static DBTypeEnum get(){
		return contextHolder.get();
	}
	
	public static void master(){
		set(DBTypeEnum.MASTER);
	}
	
	public static void slave(){
		set(DBTypeEnum.SLAVE);
	}
}
