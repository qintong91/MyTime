package com.example.mytime;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;


public class MainService extends Service implements BDLocationListener{
	public LocationClient mLocationClient = null;
	BroadcastReceiver getLocationBR;
	
	LocationClientOption option = new LocationClientOption();
	
	private boolean isOpenGPS = true;
	private String  addrType = "all";
	private String  coorType = "bd09ll";
	private boolean isDisableCache = true;
	private int     scanSpan = 10000;   //30min = 30*60*10^3 = 1800000
	private int     poiNumber = 5;
	private int		poiDistance = 1000;
	private boolean	poiExtraInfo =true;
	private static String Sname;
	
	public void initOption(){
		option.setOpenGps(isOpenGPS);
		option.setAddrType(addrType);
		option.setCoorType(coorType);
		option.setScanSpan(scanSpan);
		option.disableCache(isDisableCache);
		option.setPoiNumber(poiNumber);	
		option.setPoiDistance(poiDistance); 
		option.setPoiExtraInfo(poiExtraInfo); 
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onbind");
		
		
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
		System.out.println("create");
//		Sname = this.getIntent().getStringExtra("username");
		//初始化 LocationClient
		mLocationClient = new LocationClient(getApplicationContext());
		//为LocationClient设置监听器，一旦获取了结果，会回调listener中的onReceiveLocation方法
		mLocationClient.registerLocationListener(this);
		//设置定位的参数，包括坐标编码方式，定位方式等等
		this.initOption();
		mLocationClient.setLocOption(option);
		//启动定位
		this.openLocationTask();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.closeLocationTask();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	public LocationDB mLocationDB;
//	private Cursor mCursor;
	//Time parameter.
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
//		LocationData locationData = new LocationData();
//		locationData.latitude = location.getLatitude();
//		locationData.longitude = location.getLongitude();
//		locationData.direction = location.getDerect();
////		BDlocation中的radius 半径，即精度
//		locationData.accuracy = location.getRadius();
//		message = new Message();
//		message.obj = locationData;
//		message.what = 0x001;
//		//Add data to SQL
////		MainActivity.handler.sendMessage(message);
		//TODO
		Sname = FirstPage.Sname;
		mLocationDB = new LocationDB(this,Sname); 
//		mCursor = mLocationDB.select(); 
		String TIME = formatter.format(new Date(System.currentTimeMillis()));
		String LAT = ""+location.getLatitude();
		String LON = ""+location.getLongitude();
		add(TIME,LAT,LON);
		mLocationDB.close();
		Toast.makeText(this, "Lat " + location.getLatitude() + "Lon " + location.getLongitude() , Toast.LENGTH_SHORT).show(); 
		Intent intent=new Intent();
	     intent.putExtra("lat", LAT);
	     intent.putExtra("lon", LON);
	     intent.setAction("com.example.mytime.MainService");
	     sendBroadcast(intent);
	}
	@Override
	public void onReceivePoi(BDLocation arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void add(String TIME, String LAT, String LON){  
		//不能为空，或者退出 
		if (TIME.equals("") || LAT.equals("") || LON.equals("")){ 
		return; 
		} 
		SiteInfoDB siteinfodb=new SiteInfoDB(this);
		mLocationDB.insert(TIME, LAT,LON,siteinfodb); 
		Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show(); 
	} 
	
//.........................................................................................................
	
	// 是否启动了定位API
	private boolean isOpenLocation = false;
	// 是否启动了定位线程
	private boolean isOpenLocationTask = false;
	// 定时器
	private Timer myLocationTimer = null;
	// 定时线程
	private TimerTask myLocationTimerTask = null;
	// 错误标记
	private static String TAG = "locationApplicationBeanError";
	// 时间间隔
	private static int myTime = 10 * 1000;
	
	public void openLocationTask() {

		try {
			if (!isOpenLocationTask) // /如果不是打开状态，则打开线程
			{
				if(!isOpenLocation)
				mLocationClient.start();
				
				// 开启定时器
				initLocationTimeAndTimeTask(); // 初始化定时器和定时线程
				myLocationTimer.schedule(myLocationTimerTask, myTime, myTime);
				isOpenLocationTask = true; // 标记为打开了定时线程
			} 
		} catch (Exception e) {
			Log.i(TAG, "打开定位定时器线程 异常" + e.toString());
		}
	}

	/***
	 * 初始化 time 对象 和 timetask 对象
	 */
	private void initLocationTimeAndTimeTask() {
		//initLocationTime
		if (myLocationTimer == null) 
			myLocationTimer = new Timer();
		
		// init LocationTimeTask
		myLocationTimerTask = new TimerTask() {
			/***
			 * 定时器线程方法
			 */
			@Override
			public void run() {
				handler.sendEmptyMessage(1); // 发送消息
			}
		};
	}
	
	private Handler handler = new Handler() {
		// 更新的操作
		@Override
		public void handleMessage(Message msg) {
//			mLocationClient.getLocation(); // get coordinate

			super.handleMessage(msg);
		}
	};
	
	/***
	 * 关闭定位定时器线程
	 */
	public void closeLocationTask() {
		try {
			if (isOpenLocationTask) // 如果是打开状态，则关闭
			{
				try {
					mLocationClient.stop(); // 结束定位
					isOpenLocation = false; // 标识为已经结束了定位
				} catch (Exception e) {
					Log.i(TAG, "结束定位异常" + e.toString());
				}
				// 关闭定时器
				myLocationTimer.cancel();

				myLocationTimer = null;
				myLocationTimerTask = null;

				Log.i(TAG, " 关闭了定位定时器线程 ");
				isOpenLocationTask = false; // 标记为关闭了定时线程
			} else {
				Log.i(TAG, " 已经关闭了定位定时器线程 ");
			}

		} catch (Exception e) {
			Log.i(TAG, "关闭定位定时器线程异常: " + e.toString());
		}
	}
}	
//..........................................................................................................
	