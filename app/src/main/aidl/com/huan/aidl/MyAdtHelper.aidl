package com.huan.aidl;

import com.huan.bean.MyLogBean;
//AIDL
interface MyAdtHelper {

	//boolean sendMessage(String logtext);
	boolean sendMessage(in MyLogBean myLogBean);
}
