package com.example.mytime;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
//import baidumapsdk.demo.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Dot;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;

public class FragmentMain extends Fragment {
	private TextView StayingTime;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public SiteInfoDB siteinfodb;
	public LocationDB locdb;
	private Cursor mCursor;
	private double x;
	private double y;
	private static final String SELECTED_ITEM = "selected_item";
	public static String Sname;
	private InfoWindow mInfoWindow;
	private ArrayList<Site> siteList=new ArrayList<Site>();
	private LatLng prePos=new LatLng(39.9672990000,116.3650490000);
	 private MyReceiver receiver=null;
	private ArrayList<Step> step;
	boolean isFirstLoc = true;
	private Marker mMarkerPre ;
	public ArrayList<Step> getStep() {
		return step;
	}
	public ArrayList<Site> getSiteList() {
		return siteList;
	}
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	//	return inflater.inflate(R.layout.fragment1_3, null);
	 	super.onCreate(savedInstanceState); 
	 	Sname = getActivity().getIntent().getStringExtra("username");
	 	SDKInitializer.initialize(getActivity().getApplicationContext());  
	 	super.onCreate(savedInstanceState); 
	 	siteinfodb=new SiteInfoDB(getActivity());
		LatLng p = new LatLng(39.9672990000,116.3650490000);
		mMapView = new MapView(getActivity(),
				new BaiduMapOptions().mapStatus(new MapStatus.Builder()
						.target(p).zoom(17).build())); 	
		mBaiduMap = mMapView.getMap();
	 	mCursor = siteinfodb.select(); 	;
	 	BitmapDescriptor bd;
	 	OverlayOptions ooA; 
	 	ArrayList<String> siteName=new ArrayList<String>();
	 	
	 	//注册广播接收器
	 	  receiver=new MyReceiver();
	 	  IntentFilter filter=new IntentFilter();
	 	  filter.addAction("com.example.mytime.MainService");
	 	  getActivity().registerReceiver(receiver,filter);
	 	
	 	//从数据库中读取节点位置，并标注在地图上
	 	while(mCursor.moveToNext()) 
	 	{
	 		LatLng   pt1 = new LatLng(mCursor.getDouble(mCursor.getColumnIndex("site_lat" )),mCursor.getDouble(mCursor.getColumnIndex("site_lon" )));
	 		String name=mCursor.getString(mCursor.getColumnIndex("site_info" ));
             bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
          	 ooA = new MarkerOptions().position(pt1).icon(bd)
         			.zIndex(9);
          	Marker mMarkerA;
         	mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
         	siteList.add(new Site(name,pt1,mMarkerA));
        
	 	}
	 	
	 	//marker监听器
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(getActivity().getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				button.setTextColor(Color.BLACK);
				//使覆盖物偏移固定的像素
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				OnInfoWindowClickListener listener = null;
				for(int i=0;i<siteList.size();i++)
				{
					if (marker == siteList.get(i).getMarker()) 
					{
						final int m=i;
						button.setText(siteList.get(i).getName());
						listener = new OnInfoWindowClickListener() {
							public void onInfoWindowClick() {
								//点击地点名称后的监听器 跳转到该地点停留列表
								Intent intent = new Intent(getActivity(),
										TimeListPage.class);
		//						intent.putExtra("steps","step");
				//				intent.putExtra("site",siteList.get(m));
								ArrayList<SitePageItem> SitePageList=new ArrayList<SitePageItem>();
								for(int j=0;j<step.size();j++)
								{
									if(step.get(j).getSite()==siteList.get(m)&&j<(step.size()-1))
									{
										SitePageList.add(new SitePageItem(step.get(j).getArriveTime(),step.get(j).getLeaveTime()));
									}
									else if(j==(step.size()-1)&&step.get(j).getSite()==siteList.get(m))
									{
										SitePageList.add(new SitePageItem(step.get(j).getArriveTime(),step.get(j).getLeaveTime()));
									}
								}
								intent.putExtra("steps",SitePageList);
								startActivity(intent);
							}
						};
					} 
				}
				
				mInfoWindow = new InfoWindow(button, llInfo, listener);
				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			} 
		});
//	 	Intent intent = getIntent();
		 
			// 当用intent参数时，设置中心点为指定点
		//	Bundle b = intent.getExtras();

		 
 //	mMapView = new MapView(getActivity(), new BaiduMapOptions());
 	
 	
// 	OverlayOptions polygonOption = new DotOptions()  
  //  .center(p)    
  //  .color(0xAAFFFF00);  
//在地图上添加多边形Option，用于显示  
//mBaiduMap.addOverlay(polygonOption);
		
		
		
//得到路径的list		
locdb=new LocationDB(getActivity(),FirstPage.Sname);
 List<LatLng> ptsList = new ArrayList<LatLng>();  
 try {
 	step=getStep(FirstPage.Sname,locdb,siteList);
 
  Iterator<Step> it = step.iterator();
 	
                  while(it.hasNext())
                 {
                       Step a=it.next();
                       ptsList.add(a.getSite().getPt());
                      // System.out.println(a.getLocInfo()+a.getTime()+a.getSiteLat()+a.getSiteLng());
                       
                }
           
                  
 } catch (ParseException e) {
 	// TODO Auto-generated catch block
 	e.printStackTrace();
 }
 
 
 //增加先后到达点的编号
//Point p1;
 LatLng ll1;
	//LatLng llInfo1;
	OverlayOptions ooText;
	for(int i=0;i<step.size();i++)
	{
		ll1 = step.get(i).getSite().getMarker().getPosition();
	//	final Point p1 = mBaiduMap.getProjection().toScreenLocation(ll1);
//	p1.y = p1.y -step.get(i).getSite().getTimes()*10;
		LatLng llInfo1 = new LatLng(ll1.latitude-0.00020*(step.get(i).getTimes()-1),ll1.longitude+0.0003);
		
		 ooText = new TextOptions().bgColor(0xAAFFFF00)
				.fontSize(24).fontColor(0xFFFF00FF).text(String.valueOf(i+1)).position(llInfo1);
		
		mBaiduMap.addOverlay(ooText);
	}
	
	
//标记路径
if(step.size()>=2)
{
OverlayOptions ooPolyline = new PolylineOptions().width(10)
	.color(0xAAFFFF00).points(ptsList);  

//在地图上添加多边形Option，用于显示  
mBaiduMap.addOverlay(ooPolyline);
}


////标记当前位置
//OverlayOptions oo; 
//BitmapDescriptor bd2;
//bd2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
//	 oo = new MarkerOptions().position(prePos).icon(bd2)
//		.zIndex(9);
//	 
//	mBaiduMap.addOverlay(oo);
	
//得到lastStep 最后停留的位置
	
 	 return mMapView;
 	 
	}
	
	
	
private static ArrayList<Step> getStep(String username, LocationDB locdb,ArrayList<Site> siteList) throws ParseException
	{
		ArrayList<Log> list=new ArrayList<Log> ();
		ArrayList<Step> listStep=new ArrayList<Step> ();
		Cursor cursor=locdb.select();
		while(cursor.moveToNext()){
			if(cursor.getString(cursor.getColumnIndex("location_info"))!=null)
			{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = formatter.parse(cursor.getString(cursor.getColumnIndex("location_time")));
            String locInfo=cursor.getString(cursor.getColumnIndex("location_info"));         
            list.add(new Log(time,locInfo,siteList));
			}
 }
		 
		Iterator<Log> it = list.iterator();
		Iterator<Log> it2 = list.iterator();
		String lastloc=" ";
		 Log b=null;
		 Log a=null;
		 Log startLog = null;
		 Log endLog = null;
		 if(it.hasNext())
		 {
		 startLog=it.next();
		 b=startLog;
	                   while(it.hasNext())
	                  {
	                	     a=it.next();
	                	     b=it2.next();
	                	     
	                       
	                         if((!(a.getSite().equals(startLog.getSite())))||((a.getTime().getTime()-b.getTime().getTime())>600000))
	                        {
	                             endLog=b;
	                             if((!(endLog.equals(startLog)))&&(endLog.getSite().equals(startLog.getSite())))
	                             {
	                            	 startLog.getSite().timeslpus();	                         
	                            	 listStep.add(new Step(startLog.getTime(),endLog.getTime(),startLog.getSite(),startLog.getSite().getTimes()));
	                            	 startLog=a;
	                             }
	                             if(endLog.equals(startLog))
	                             {
	                            	 startLog=a;
	                             }
	                             
	                        }	                         	                         
	                  }
	                 endLog=it2.next();
	                  if((!(endLog.equals(startLog)))&&(endLog.getSite().equals(startLog.getSite())))
                      {
                     	 startLog.getSite().timeslpus();	                         
                     	 listStep.add(new Step(startLog.getTime(),endLog.getTime(),startLog.getSite(),startLog.getSite().getTimes()));
                     	 
                      }
		 }            
		return listStep;
	}
	
//private static Step getLastStep(String username, LocationDB locdb,ArrayList<Site> siteList) throws ParseException
//{
//	Step lastStep = null;
//	Cursor cursor=locdb.select();
//	 
//		 cursor.moveToLast();
//		 
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date time = formatter.parse(cursor.getString(cursor.getColumnIndex("location_time")));
//			String locInfo=cursor.getString(cursor.getColumnIndex("location_info"));         
//			lastStep=new Step(time,locInfo,siteList);	
//	return lastStep;
//}

public static class Log
{
	
	public  Log(Date time,String locInfo,ArrayList<Site> siteList)
	{
		 
		this.time=time;
		this.locInfo=locInfo;
	//	Cursor cursor = siteinfodb.select(locInfo);
//		cursor.moveToNext();
	//	this.siteLatLng=new LatLng(cursor.getDouble(cursor.getColumnIndex("site_lat")),cursor.getDouble(cursor.getColumnIndex("site_lon" )));
		for(int i=0;i<siteList.size();i++)
		{
			if (this.locInfo.equals(siteList.get(i).getName()))
			{
				this.site=siteList.get(i);
				 
			 
				break;
			}
		}
	}
	private String locInfo;
	public String getLocInfo() {
		return locInfo;
	}
	public Date getTime() {
		return time;
	}
	public Site getSite() {
		return site;
	}
	private Date time;
	private Site site;
}
	public static class Step{
		public Step(Date arriveTime,Date leaveTime,Site site,int times)
		{
			this.arriveTime=arriveTime;
            this.leaveTime=leaveTime;
            this.site=site;
            this.times=times;
		}
		
		public Date getArriveTime() {
			return arriveTime;
		}
		 

		
		private Date arriveTime;
		private Site site;
		private int times;
		private Date leaveTime;
		public Date getLeaveTime() {
			return leaveTime;
		}

		public void setLeaveTime(Date leaveTime) {
			this.leaveTime = leaveTime;
		}

		public void setTimes(int times) {
			this.times = times;
		}

		public int getTimes() {
			return times;
		}

		public Site getSite() {
			return site;
		}
		
		
	}
	public class Site 
	{
		private String name;
		public String getName() {
			return name;
		}
		public LatLng getPt() {
			return pt;
		}
		public Marker getMarker() {
			return marker;
		}
		private LatLng pt;
		private Marker marker;
		private int times;
		private long everageTime;
		private long everageEveryoneTime;
		public long getEverageEveryoneTime() {
			return everageEveryoneTime;
		}
		public void setEverageEveryoneTime(long everageEveryoneTime) {
			this.everageEveryoneTime = everageEveryoneTime;
		}
		public long getEverageTime() {
			return everageTime;
		}
		public void setEverageTime(long everageTime) {
			this.everageTime = everageTime;
		}
		public int getTimes() {
			return times;
		}
		public void timeslpus()
		{
			times++;
		}
		public Site(String name,LatLng pt,Marker marker)
		{
			this.name=name;
			this.pt=pt;
			this.marker=marker;
			this.times=0;
			everageTime=0;
			everageEveryoneTime=0;
		}
	}
	
	static public class SitePageItem implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Date arriveTime;
		public Date getArriveTime() {
			return arriveTime;
		}
		public Date getLeaveTime() {
			return leaveTime;
		}
		private Date leaveTime;
		public SitePageItem(Date arriveTime,Date leaveTime)
		{
			this.arriveTime=arriveTime;
			this.leaveTime=leaveTime;
		}
		
	}
	public class MyReceiver extends BroadcastReceiver {
	     @Override
	     public void onReceive(Context context, Intent intent) {
	    	
	      Bundle bundle=intent.getExtras();
	       double lat=Double.parseDouble(bundle.getString("lat"));
	       double lon=Double.parseDouble(bundle.getString("lon")); 
	       
	   prePos=new LatLng(lat,lon);
	    
	      System.out.println("Cancel ~~~~~~~~~~~~~~~~~~~");
    System.out.println(lat+""+lon+"");
    if(isFirstLoc) isFirstLoc=false;
    else{
    mMarkerPre.remove();
    }
   OverlayOptions oo; 
   BitmapDescriptor bd2;
    bd2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
 	 oo = new MarkerOptions().position(prePos).icon(bd2)
			.zIndex(9);
 		 
 	mMarkerPre = (Marker) (mBaiduMap.addOverlay(oo)); 
	 
     
	      
	     }

		 
	    }
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(prePos.latitude)
					.longitude(prePos.longitude).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(prePos.latitude,
						prePos.longitude);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

}
 