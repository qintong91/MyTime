package com.example.mytime;
import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 

public class SiteInfoDB extends SQLiteOpenHelper { 
	private final static String DATABASENAME = "SystemInfo.db"; 
	private final static int DATABASE_VERSION = 1; 
	private String TABLENAME = "LocInfo"; 
	public final static String Site_ID = "site_id"; 	 
	public final static String Site_LAT = "site_lat"; 
	public final static String Site_LON = "site_lon"; 
	public final static String Site_NAME = "site_info";
	public final static String NW_LAT = "nw_lat"; 
	public final static String NW_LON = "nw_lon"; 
	public final static String NE_LAT = "ne_lat"; 
	public final static String NE_LON = "ne_lon"; 
	public final static String SE_LAT = "se_lat"; 
	public final static String SE_LON = "se_lon"; 
	public final static String SW_LAT = "sw_lat"; 
	public final static String SW_LON = "sw_lon"; 
	
	public static String Sname;

	public SiteInfoDB (Context context) { 
	// TODO Auto-generated constructor stub 
	super(context, DATABASENAME, null, DATABASE_VERSION); 
	} 
	
 

	//create table 
	@Override 
	public void onCreate(SQLiteDatabase db) { 
	String sql = "CREATE TABLE " + TABLENAME + " (" + Site_ID 
	+ " INTEGER primary key autoincrement, " + Site_LAT + " double, "+ Site_LON + " double, "+ Site_NAME +" text,"+ NW_LAT + " double, "+ NW_LON + " double, "+ NE_LAT + " double, "+ NE_LON + " double, "+SE_LAT + " double, "+ SE_LON + " double, "+SW_LAT + " double, "+ SW_LON + " double"+");"; 
	db.execSQL(sql); 
	ContentValues cv = new ContentValues(); 
	cv.put(Site_LAT, 39.9661860000); 
	cv.put(Site_LON, 116.3630300000); 
	cv.put(Site_NAME, "教三");
	cv.put(NW_LAT, 39.9664210000); 
	cv.put(NW_LON, 116.3622030000); 
	cv.put(NE_LAT, 39.9664760000); 
	cv.put(NE_LON, 116.3638380000); 
	cv.put(SE_LAT, 39.9651940000); 
	cv.put(SE_LON, 116.3638290000); 
	cv.put(SW_LAT, 39.9651940000); 
	cv.put(SW_LON, 116.3622300000); 
	long row;
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9664970000); 
	cv.put(Site_LON, 116.3667760000); 
	cv.put(Site_NAME, "操场");
	cv.put(NW_LAT, 39.9671750000); 
	cv.put(NW_LON, 116.3662370000); 
	cv.put(NE_LAT, 39.9671950000); 
	cv.put(NE_LON, 116.3674850000); 
	cv.put(SE_LAT, 39.9656540000); 
	cv.put(SE_LON, 116.3675840000); 
	cv.put(SW_LAT, 39.9656820000); 
	cv.put(SW_LON, 116.3664080000); 
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9700780000); 
	cv.put(Site_LON, 116.3633260000); 
	cv.put(Site_NAME, "学10");
	cv.put(NW_LAT, 39.9702780000); 
	cv.put(NW_LON, 116.3627600000); 
	cv.put(NE_LAT, 39.9703130000); 
	cv.put(NE_LON, 116.3636410000);  
	cv.put(SE_LAT, 39.9699670000); 
	cv.put(SE_LON, 116.3636950000); 
	cv.put(SW_LAT, 39.9699260000); 
	cv.put(SW_LON, 116.3627960000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9686120000); 
	cv.put(Site_LON, 116.3643950000); 
	cv.put(Site_NAME, "图书馆");
	cv.put(NW_LAT, 39.9689510000); 
	cv.put(NW_LON, 116.3638110000); 
	cv.put(NE_LAT, 39.9690130000); 
	cv.put(NE_LON, 116.3652040000);  
	cv.put(SE_LAT, 39.9681560000); 
	cv.put(SE_LON, 116.3652580000); 
	cv.put(SW_LAT, 39.9680940000); 
	cv.put(SW_LON, 116.3638200000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9664000000); 
	cv.put(Site_LON, 116.3647100000); 
	cv.put(Site_NAME, "教二");
	cv.put(NW_LAT, 39.9664830000); 
	cv.put(NW_LON, 116.3639370000); 
	cv.put(NE_LAT, 39.9665180000); 
	cv.put(NE_LON, 116.3651950000);  
	cv.put(SE_LAT, 39.9659860000); 
	cv.put(SE_LON, 116.3651860000); 
	cv.put(SW_LAT, 39.9659580000); 
	cv.put(SW_LON, 116.3639370000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9701260000); 
	cv.put(Site_LON, 116.3656350000); 
	cv.put(Site_NAME, "科研楼");
	cv.put(NW_LAT, 39.9702300000); 
	cv.put(NW_LON,116.3653650000); 
	cv.put(NE_LAT, 39.9702640000); 
	cv.put(NE_LON, 116.3660120000);  
	cv.put(SE_LAT, 39.9696280000); 
	cv.put(SE_LON, 116.3660480000); 
	cv.put(SW_LAT, 39.9696210000); 
	cv.put(SW_LON, 116.3653210000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9691720000); 
	cv.put(Site_LON, 116.3657160000); 
	cv.put(Site_NAME, "学生餐厅");
	cv.put(NW_LAT, 39.9693860000); 
	cv.put(NW_LON,116.3653650000); 
	cv.put(NE_LAT, 39.9694000000); 
	cv.put(NE_LON, 116.3660390000);  
	cv.put(SE_LAT, 39.9689370000); 
	cv.put(SE_LON, 116.3660840000); 
	cv.put(SW_LAT, 39.9689300000); 
	cv.put(SW_LON, 116.3653740000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9676380000); 
	cv.put(Site_LON, 116.3628230000); 
	cv.put(Site_NAME, "教四");
	cv.put(NW_LAT, 39.9679420000); 
	cv.put(NW_LON,116.3621590000); 
	cv.put(NE_LAT, 39.9679900000); 
	cv.put(NE_LON, 116.3636500000);  
	cv.put(SE_LAT, 39.9675060000); 
	cv.put(SE_LON, 116.3637310000); 
	cv.put(SW_LAT, 39.9674580000); 
	cv.put(SW_LON, 116.3621770000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9671750000); 
	cv.put(Site_LON,116.3649700000); 
	cv.put(Site_NAME, "主楼");
	cv.put(NW_LAT, 39.9674440000); 
	cv.put(NW_LON,116.3644760000); 
	cv.put(NE_LAT, 39.9674860000); 
	cv.put(NE_LON,116.3652310000);  
	cv.put(SE_LAT,39.9667320000); 
	cv.put(SE_LON,116.3652670000); 
	cv.put(SW_LAT, 39.9667390000); 
	cv.put(SW_LON,116.3645480000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9692550000); 
	cv.put(Site_LON,116.3632990000); 
	cv.put(Site_NAME, "学生公寓8");
	cv.put(NW_LAT,39.9694000000); 
	cv.put(NW_LON,116.3628860000); 
	cv.put(NE_LAT, 39.9694280000); 
	cv.put(NE_LON,116.3636680000);  
	cv.put(SE_LAT, 39.9690690000); 
	cv.put(SE_LON,116.3637130000); 
	cv.put(SW_LAT, 39.9689930000); 
	cv.put(SW_LON, 116.3628590000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
	cv.put(Site_LAT, 39.9693100000); 
	cv.put(Site_LON,116.3623380000); 
	cv.put(Site_NAME, "学生公寓5");
	cv.put(NW_LAT, 39.9693660000); 
	cv.put(NW_LON,116.3620780000); 
	cv.put(NE_LAT, 39.9693930000); 
	cv.put(NE_LON,116.3627330000);  
	cv.put(SE_LAT, 39.9689930000); 
	cv.put(SE_LON,116.3627780000); 
	cv.put(SW_LAT, 39.9689580000); 
	cv.put(SW_LON,116.3620690000);
	  row = db.insert(TABLENAME, null, cv); 
	cv.clear();
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
	public Cursor select(String sitename) { 
		SQLiteDatabase db = this.getReadableDatabase(); 
		Cursor cursor = db 
		.query(TABLENAME, new String[]{Site_LAT,Site_LON},"site_info=?",new String[]{sitename}, null, null, null); 
		return cursor; 
		} 

	//增加操作 
//	public long insert(String time,String lat, String lon) 
//	{ 
//	SQLiteDatabase db = this.getWritableDatabase(); 
//	/* ContentValues */ 
//	ContentValues cv = new ContentValues(); 
//	cv.put(Location_TIME, time); 
//	cv.put(Location_LAT, lat); 
//	cv.put(Location_LON, lon);
//	cv.put(Location_INFO, "xxx");
//	long row = db.insert(TABLENAME, null, cv); 
//	return row; 
//	} 
	//删除操作 
//	public void delete(int id) 
//	{ 
//	SQLiteDatabase db = this.getWritableDatabase(); 
//	String where = Location_ID + " = ?"; 
//	String[] whereValue ={ Integer.toString(id) }; 
//	db.delete(TABLENAME, where, whereValue); 
//	} 
	//修改操作 
//	public void update(int id, String time,String lat, String lon) 
//	{ 
//	SQLiteDatabase db = this.getWritableDatabase(); 
//	String where = Location_ID + " = ?"; 
//	String[] whereValue = { Integer.toString(id) }; 
//
//	ContentValues cv = new ContentValues(); 
//	cv.put(Location_TIME, time); 
//	cv.put(Location_LAT, lat); 
//	cv.put(Location_LON, lon);
//	db.update(TABLENAME, cv, where, whereValue); 
//	} 
	
//	public void deleteTable(){
//		SQLiteDatabase db = this.getWritableDatabase();
//		String sql = "DROP TABLE " + Sname + ".db" + ";";
//		db.execSQL(sql);
//	}
	
	}

