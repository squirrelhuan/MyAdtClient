package com.huan.mylog;

import com.huan.aidl.MyAdtHelper;
import com.huan.bean.MyLogBean;
import com.huan.handler.CrashHandler;
import com.huan.handler.CrashHandler.onErrorCatchedListener;
import com.huan.utils.AppUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 
 * @author CGQ
 * @time 2016.11.18
 */
public class MyLog {
	private static String TAG_DEFAULT = "CGQ";// 默认Tag标签

	private static Context context;

	private static MyAdtHelper logHelper;
	private CrashHandler crashHandler;
	private static LogHelperConnection conn;
	private static PackageInfo packageInfo;
	private static String appName;
	private static PrintType printType = PrintType.All;
	private static MyLog myLog = new MyLog();
	private onErrorCatchedListener errorCatchedListener;
	private String ErrorToast = "很抱歉,程序出现异常,即将退出...";

	/**
	 * MyLog 只打印自定义的log,不打印系统日志 SysLog 只打印系统日志,不打印自定义的log All 打印系统日志和自定义的log
	 * default
	 */
	public enum PrintType {
		/** 特定的log只会输出到MyAdt客户端 */
		MyLog,
		/** 特定的log作为系统输出，不会输出到MyAdt客户端 */
		SysLog,
		/** 会作为系统日志输出，也会输出到MyAdt客户端 */
		All,
		/** 不会有任何log输出 */
		None
	}

	public static MyLog getInstance() {
		return myLog;
	}

	public void initialization() {
		/**************** log *******************************************************/
		conn = new LogHelperConnection();
		Intent service = new Intent("com.huan.myadt.log.deal");
		service.setPackage("com.huan.myadt"); // 兼容Android 5.0
		//Bundle bundle = new Bundle();
		//bundle.putString("AppName", appName);
		//service.putExtras(bundle); // com.example.myadt.service
		context.bindService(service, conn, context.BIND_AUTO_CREATE);
		/**************** error *******************************************************/
		crashHandler = CrashHandler.getInstance();
		crashHandler.init(context);
		crashHandler.setErrorToast(ErrorToast);
		crashHandler.setErrorCatchedListener(errorCatchedListener);
	}

	public MyLog() {
		if (context != null) {
			Intent service = new Intent("com.huan.myadt.log.deal");
			service.setPackage("com.huan.myadt"); // 兼容Android 5.0
			context.bindService(service, conn, context.BIND_AUTO_CREATE);
		}
	}

	public MyLog(Context context) {
		this.context = context;
		try {
			PackageInfo pkg;
			pkg = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
			appName = pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();
			packageInfo = AppUtils.getPackageInfo(context);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void v(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Verbose, TAG_DEFAULT, text);
	}

	public static void d(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Debug, TAG_DEFAULT, text);
	}

	public static void i(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Info, TAG_DEFAULT, text);
	}

	public static void w(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Warn, TAG_DEFAULT, text);
	}

	public static void e(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Error, TAG_DEFAULT, text);
	}

	public static void a(Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Assert, TAG_DEFAULT, text);
	}

	public static void v(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Verbose, tag, text);
	}

	public static void d(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Debug, tag, text);
	}

	public static void i(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Info, tag, text);
	}

	public static void w(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Warn, tag, text);
	}

	public static void e(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Error, tag, text);
	}

	public static void a(String tag, Object obj) {
		String text = "null";
		if (obj != null) {
			text = obj.toString();
		}
		LogC.sendLog(LEVEL_Log.Assert, tag, text);
	}

	public static class LogC // 静态类
	{
		public static void sendLog(int level, String tag, String msg) {
			if (printType == PrintType.All || printType == PrintType.SysLog) {
				switch (level) {
				case LEVEL_Log.Verbose:
					Log.v(tag, msg);
					break;
				case LEVEL_Log.Debug:
					Log.d(tag, msg);
					break;
				case LEVEL_Log.Info:
					Log.i(tag, msg);
					break;
				case LEVEL_Log.Warn:
					Log.w(tag, msg);
					break;
				case LEVEL_Log.Error:
					Log.e(tag, msg);
					break;
				case LEVEL_Log.Assert:
					Log.wtf(tag, msg);
					break;
				default:
					Log.d(tag, msg);
					break;
				}
			}
			if (printType == PrintType.All || printType == PrintType.MyLog) {
				try {
					if (logHelper != null) {
						MyLogBean myLogBean = new MyLogBean(level, tag, msg, context.getPackageName(),
								packageInfo.versionCode, packageInfo.versionName);
						logHelper.sendMessage(myLogBean);
					} else {
						if (context != null) {
							MyLog.getInstance().initialization();
							if (logHelper != null) {
								LogC.sendLog(level, tag, msg);
							}
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * public static class MLog //静态类 { public static void
	 * getLog(ReadLogListener listener) { //
	 * System.out.println("--------func start--------"); // 方法启动
	 * ArrayList<String> cmdLine=new ArrayList<String>(); //设置命令 logcat -d 读取日志
	 * try { cmdLine.add("logcat"); cmdLine.add("-d");
	 * 
	 * ArrayList<String> clearLog=new ArrayList<String>(); //设置命令 logcat -c 清除日志
	 * clearLog.add("logcat"); clearLog.add("-c");
	 * 
	 * Process process=Runtime.getRuntime().exec(cmdLine.toArray(new
	 * String[cmdLine.size()])); //捕获日志 BufferedReader bufferedReader=new
	 * BufferedReader(new InputStreamReader(process.getInputStream()));
	 * //将捕获内容转换为BufferedReader
	 * 
	 * 
	 * // Runtime.runFinalizersOnExit(true); String str=null;
	 * while((str=bufferedReader.readLine())!=null) //开始读取日志，每次读取一行 {
	 * Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
	 * //清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
	 * listener.onNewLine(str); System.out.println(str);
	 * //输出，在logcat中查看效果，也可以是其他操作，比如发送给服务器.. } if(str==null) {
	 * //System.out.println("--   is null   --"); } } catch(Exception e) {
	 * e.printStackTrace(); } // System.out.println("--------func end--------");
	 * } }
	 */

	public static interface ReadLogListener {
		void onNewLine(String str);
	}

	private class LogHelperConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName name, IBinder service) {
			logHelper = MyAdtHelper.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName name) {
			logHelper = null;
		}
	}

	public interface LEVEL_Log {
		int All = 0;
		int Verbose = 1;
		int Debug = 2;
		int Info = 3;
		int Warn = 4;
		int Error = 5;
		int Assert = 6;
	}

	public static PrintType getPrintType() {
		return printType;
	}

	/**
	 * 设置打印模式
	 */
	public static void setPrintType(PrintType printType) {
		MyLog.printType = printType;
	}

	public void setErrorCatchedListener(onErrorCatchedListener errorCatchedListener) {
		this.errorCatchedListener = errorCatchedListener;
	}

	public void setErrorToast(String errorToast) {
		ErrorToast = errorToast;
	}
    
}
