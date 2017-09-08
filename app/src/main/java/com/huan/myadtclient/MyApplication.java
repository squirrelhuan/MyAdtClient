package com.huan.myadtclient;

import android.app.Application;

import com.huan.mylog.BreakPoint;
import com.huan.mylog.MyLog;

public class MyApplication extends Application {

	@BreakPoint()
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		MyLog myLog = new MyLog(this);
		myLog.initialization();
		myLog.setPrintType(MyLog.PrintType.All);//设置打印类型
		myLog.setErrorToast("对不起程序崩溃了");//设置崩溃提示
		//mylog.setErrorCatchedListener(new one);

	}

}
