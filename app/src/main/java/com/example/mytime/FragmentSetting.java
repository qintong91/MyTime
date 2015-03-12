package com.example.mytime;

 

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.mytime.view.*;

public class FragmentSetting extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment1_1, null);
		RelativeLayout layoutState= (RelativeLayout) view.findViewById(R.id.state);
		RelativeLayout layoutInfo= (RelativeLayout) view.findViewById(R.id.info);
		CheckSwitchButton mCheckSwithcButton = (CheckSwitchButton)view.findViewById(R.id.mCheckSwithcButton);
		 Boolean worked=isWorked(getActivity()); 
		 if(worked)
		 {
			 mCheckSwithcButton.setChecked(true);
		 }
		 else
		 {
			 mCheckSwithcButton.setChecked(false); 
		 }
		layoutState.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("okkk");
 			Intent intent = new Intent();
 				intent.setClass(getActivity(), InfoActivity.class);
////				intent.putExtra("username", FirstPage.Sname);  //TODO
 				getActivity().startActivity(intent);
			}
		});
		layoutInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("okkk22");
 			Intent intent = new Intent();
 			intent.setClass(getActivity(), StateActivity.class);
////				intent.putExtra("username", FirstPage.Sname);  //TODO
 			getActivity().startActivity(intent);
			}
		});
		 
mCheckSwithcButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					Intent intent = new Intent();
					
 					intent.setClass(getActivity(), MainService.class);
 //					intent.putExtra("username", FirstPage.Sname);  //TODO
 					getActivity().startService(intent);
				}else{
					Intent intent = new Intent();
					intent.setClass(getActivity(), MainService.class);
					getActivity().stopService(intent);
				}
			}
		});
//		Button PresentLoc = (Button) view.findViewById(R.id.button1);
//		Button TodaysLoc = (Button) view.findViewById(R.id.button2);
//		Button startSer = (Button) view.findViewById(R.id.startButton);
//		Button stopSer = (Button) view.findViewById(R.id.stopButton);
//		PresentLoc.setOnClickListener(new LocationCheckedListener());
//		TodaysLoc.setOnClickListener(new LocationCheckedListener());
//		
//		startSer.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(getActivity(), MainService.class);
////				intent.putExtra("username", FirstPage.Sname);  //TODO
//				getActivity().startService(intent);
//			}
//		});
//		
//		stopSer.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(getActivity(), MainService.class);
//				getActivity().stopService(intent);
//			}
//		});
		
		return view;
	}
	
	class LocationCheckedListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), LocationPage.class);
			getActivity().startActivity(intent);
		}
	}
	public static boolean isWorked(Context context)  
	 {  
	  ActivityManager myManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
	  ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);  
	  for(int i = 0 ; i<runningService.size();i++)  
	  {  
	   if(runningService.get(i).service.getClassName().toString().equals("com.example.mytime.MainService"))  
	   {  
	    return true;  
	   }  
	  }  
	  return false;  
	 }  
}
