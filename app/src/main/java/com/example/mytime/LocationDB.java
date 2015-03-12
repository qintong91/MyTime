package com.example.mytime;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.mytime.FragmentMain.Site;

import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 

public class LocationDB extends SQLiteOpenHelper { 
	private final static String DATABASENAME = "locationDB1.db"; 
	private final static int DATABASE_VERSION = 1; 
	private String TABLENAME = "location_table"; 
	private String TABLENAME2 = "site_table"; 
	public final static String Location_ID = "location_id"; 
	public final static String Location_TIME = "location_time"; 
	public final static String Location_LAT = "location_lat"; 
	public final static String Location_LON = "location_lon"; 
	public final static String Location_INFO = "location_info";
  
	public final static String Site_ID = "site_id"; 
	 
	public final static String Site_LAT = "site_lat"; 
	public final static String Site_LON = "site_lon"; 
	public final static String Site_NAME = "site_info";
	public static String Sname;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LocationDB(Context context) { 
	// TODO Auto-generated constructor stub 
	super(context, DATABASENAME, null, DATABASE_VERSION); 
	} 
	
	public LocationDB(Context context, String username){
		super(context,username+".db",null,DATABASE_VERSION);
		Sname = username;
	}

	//create table 
	@Override 
	public void onCreate(SQLiteDatabase db) { 
	String sql = "CREATE TABLE " + TABLENAME + " (" + Location_ID 
	+ " INTEGER primary key autoincrement, " + Location_TIME + " text, "+ Location_LAT + " text, "+ Location_LON + " text, "+ Location_INFO +" text);"; 
	db.execSQL(sql); 
	ContentValues cv = new ContentValues(); 
	long time=System.currentTimeMillis()-60000*61*8;
	String TIME = formatter.format((new Date(System.currentTimeMillis()-60000*60*8)));
 for(int i=0;i<13;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*60*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "学10"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }
 for(int i=0;i<8;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*63*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "图书馆"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }
 for(int i=0;i<4;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*62*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "教四"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }
 for(int i=0;i<4;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*58*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "教三"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }
 for(int i=0;i<7;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*59*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "学生餐厅"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }

 for(int i=0;i<9;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*62*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "操场"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
 }
 for(int i=0;i<13;i++)
 {
	cv.put(Location_TIME, formatter.format((new Date(time+8*62*1000*i)))); 
	cv.put(Location_LAT, 39.9664760000); 
	cv.put(Location_LON, 116.3638380000); 
	cv.put(Location_INFO, "主楼"); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	  cv.clear();
	  
 }

	} 
	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
	String sql = "DROP TABLE IF EXISTS " + TABLENAME; 
	db.execSQL(sql); 
	onCreate(db); 
	} 

	public Cursor select() { 
	SQLiteDatabase db = this.getReadableDatabase(); 
	
	Cursor cursor = db 
	.query(TABLENAME, null, null, null, null, null, null); 
	return cursor; 
	} 
	//增加操作 
	public long insert(String time,String lat, String lon,SiteInfoDB siteinfodb) 
	{ 
		
	SQLiteDatabase db = this.getWritableDatabase(); 
	Cursor mCursor=siteinfodb.select();
	LatLng p=new LatLng(Double.valueOf(lat), Double.valueOf(lon));
	String adress=null;
 	while(mCursor.moveToNext()) 
 	{
 		 
 		LatLng   pnw = new LatLng(mCursor.getDouble(mCursor.getColumnIndex("nw_lat" )),mCursor.getDouble(mCursor.getColumnIndex("nw_lon" )));
 		LatLng   pne = new LatLng(mCursor.getDouble(mCursor.getColumnIndex("ne_lat" )),mCursor.getDouble(mCursor.getColumnIndex("ne_lon" )));
 		LatLng   pse = new LatLng(mCursor.getDouble(mCursor.getColumnIndex("se_lat" )),mCursor.getDouble(mCursor.getColumnIndex("se_lon" )));
 		LatLng   psw = new LatLng(mCursor.getDouble(mCursor.getColumnIndex("sw_lat" )),mCursor.getDouble(mCursor.getColumnIndex("sw_lon" )));
 		if(isContain(pnw,pne,pse,psw,p))
 		{
 			adress=mCursor.getString(mCursor.getColumnIndex("site_info" ));
 			break;
 		}
         
    
 	}
	
	/* ContentValues */ 
	ContentValues cv = new ContentValues(); 
	cv.put(Location_TIME, time); 
	cv.put(Location_LAT, lat); 
	cv.put(Location_LON, lon);
	cv.put(Location_INFO, adress);
	long row = db.insert(TABLENAME, null, cv); 
	return row; 
	} 
	//删除操作 
	public void delete(int id) 
	{ 
	SQLiteDatabase db = this.getWritableDatabase(); 
	String where = Location_ID + " = ?"; 
	String[] whereValue ={ Integer.toString(id) }; 
	db.delete(TABLENAME, where, whereValue); 
	} 
	//修改操作 
	public void update(int id, String time,String lat, String lon) 
	{ 
	SQLiteDatabase db = this.getWritableDatabase(); 
	String where = Location_ID + " = ?"; 
	String[] whereValue = { Integer.toString(id) }; 

	ContentValues cv = new ContentValues(); 
	cv.put(Location_TIME, time); 
	cv.put(Location_LAT, lat); 
	cv.put(Location_LON, lon);
	db.update(TABLENAME, cv, where, whereValue); 
	} 
 	public Cursor getStep()
	{
 		SQLiteDatabase db = this.getReadableDatabase(); 
		Cursor cursor = db.query (TABLENAME,null, null, null, null, null, Location_TIME);
		return cursor; 
	} 
//	public void deleteTable(){
//		SQLiteDatabase db = this.getWritableDatabase();
//		String sql = "DROP TABLE " + Sname + ".db" + ";";
//		db.execSQL(sql);
//	}
 	public Boolean isContain(LatLng mp1,LatLng mp2,LatLng mp3,LatLng mp4,LatLng mp)
    {
        if (Multiply(mp, mp1, mp2) * Multiply(mp,mp4, mp3) <= 0 && Multiply(mp, mp4, mp1) * Multiply(mp, mp3, mp2) <= 0)
            {
        	return true;
            }

        else  { 
        	return false;
        	}
    }
	  private double Multiply(LatLng p1, LatLng p2, LatLng p0)
      {
          return ((p1.latitude - p0.latitude) * (p2.longitude - p0.longitude) - (p2.latitude - p0.latitude) * (p1.longitude - p0.longitude));
      }	
 
	}


