package com.example.mytime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.mytime.FragmentMain.SitePageItem;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TimeListPage extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setContentView(R.layout.timelist);
		Intent intent=getIntent();//在另外activity中得到intent
		@SuppressWarnings("unchecked")
		ArrayList<SitePageItem> SitePageList=(ArrayList<SitePageItem>) intent.getSerializableExtra("steps");//得到值
		ListView list2 = (ListView)findViewById(R.id.listTime);
		String[] arr;
		//定义一个数组
		arr=new String[SitePageList.size()];
		for(int i=0;i<SitePageList.size();i++)
		{
			arr[i] = formatter.format(SitePageList.get(i).getArriveTime())+"至"+formatter.format(SitePageList.get(i).getLeaveTime());
			 
		}
		//将数组包装ArrayAdapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
			this , android.R.layout.simple_list_item_1 , arr);
		list2.setAdapter(new TimeListAdapter(this,SitePageList));
		//为ListView设置Adapter
	//	list2.setAdapter(arrayAdapter);	
	}
	public class TimeListAdapter extends BaseAdapter{
		private Context mContext; 
		private ArrayList<SitePageItem> SitePageList;		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public TimeListAdapter(Context context,ArrayList<SitePageItem> SitePageList){
			mContext = context; 
			this.SitePageList = SitePageList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SitePageList.size(); 
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView mTextView = new TextView(mContext); 
			SitePageItem Item=SitePageList.get(position);
			position++;
			mTextView.setText("时段"+position+":  "+formatter.format(Item.getArriveTime()) + "  ——  " +  formatter.format(Item.getLeaveTime()) ); 
			return mTextView; 
		}
		
	}

}
