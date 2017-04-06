package com.huan.myadtclient.activity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.huan.myadtclient.R;
import com.huan.myadtclient.R.id;
import com.huan.myadtclient.R.layout;
import com.huan.aidl.MyAdtHelper;
import com.huan.mylog.MyLog;
import com.huan.mylog.MyLog.LEVEL_Log;
import com.huan.mylog.MyLog.ReadLogListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	private EditText numberText;
	private TextView resultView;/*
								 * private StudentConnection conn = new
								 * StudentConnection();
								 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		numberText = (EditText) this.findViewById(R.id.number);
		
	}

	public void testANR(View v){
		String s = null;
		System.out.println(s.equals("any string"));  
	}
	
	public void testcommon(View v) {
		int i = (int) (Math.random() * 100 % 5);
		switch (i+1) {
		case LEVEL_Log.Verbose:
			MyLog.v("服务已断开！");
			break;
		case LEVEL_Log.Debug:
			MyLog.d("服务已断开！");
			break;
		case LEVEL_Log.Info:
			MyLog.i("服务已断开！");
			break;
		case LEVEL_Log.Warn:
			MyLog.w("服务已断开！");
			break;
		case LEVEL_Log.Assert:
			MyLog.a("服务已断开！");
			break;
		default:
			MyLog.e("服务已断开！");
			break;
		}
		//Toast.makeText(this, "服务已断开！", Toast.LENGTH_SHORT).show();
		return;
		// try {
		/*
		 * MLog.getLog(new ReadLogListener() {
		 * 
		 * @Override public void onNewLine(String str) { try {
		 * studentQuery.sendMessage(str); } catch (RemoteException e) {
		 * e.printStackTrace(); } } });
		 */
		// studentQuery.sendMessage("test"+Math.random()*100);
		// resultView.setText(""+Math.random()*100);
		/*
		 * } catch (RemoteException e) { e.printStackTrace(); }
		 */
	}

	/*@Override
	protected void onDestroy() {
		// unbindService(conn);
		super.onDestroy();
	}*/

	/*
	 * private final class StudentConnection implements ServiceConnection {
	 * public void onServiceConnected(ComponentName name, IBinder service) {
	 * studentQuery = StudentQuery.Stub.asInterface(service); } public void
	 * onServiceDisconnected(ComponentName name) { studentQuery = null; } }
	 */

}