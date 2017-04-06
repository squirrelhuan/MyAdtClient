package com.huan.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {

	// 获取本地的版本号
	public static PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			// versionCode = packageInfo.versionCode;
			return packageInfo;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getCurrentActivityName(Context context) {  
		  ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);  
		  
		  
		  // get the info from the currently running task  
		  List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);  
		  
		  
		  ComponentName componentInfo = taskInfo.get(0).topActivity;  
		  return componentInfo.getClassName();  
		}  
}
