package com.huan.myadtclient;

import com.huan.handler.CrashHandler;
import com.huan.mylog.MyLog;
import com.huan.mylog.MyLog.PrintType;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		MyLog myLog = new MyLog(this);
		myLog.initialization();
		myLog.setPrintType(PrintType.All);//设置打印类型
		myLog.setErrorToast("对不起程序崩溃了");//设置崩溃提示
		//mylog.setErrorCatchedListener(new one);
	}

}
