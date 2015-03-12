package com.example.mytime;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;


public class FirstPage extends Activity implements
	ActionBar.TabListener
{
	private static final String SELECTED_ITEM = "selected_item";
	public static String Sname;
	
	private FragmentMain fragmentMain;
	private FragmentStat fragmentStat;
	private FragmentSetting fragmentSetting;
	public FragmentSetting getFragmentSetting() {
		return fragmentSetting;
	}

	public FragmentStat getFragmentStat() {
		return fragmentStat;
	}

	public FragmentMain getFragmentMain() {
		return fragmentMain;
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page1);
		Sname = this.getIntent().getStringExtra("username");
		final ActionBar actionBar = getActionBar();
		// 设置ActionBar的导航方式：Tab导航
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 依次添加3个Tab页，并为3个Tab标签添加事件监听器
		actionBar.addTab(actionBar.newTab().setText("首  页")
			.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("统  计")
			.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("设  置")
			.setTabListener(this));

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (savedInstanceState.containsKey(SELECTED_ITEM))
		{
			// 选中前面保存的索引对应的Fragment页
			getActionBar().setSelectedNavigationItem(
				savedInstanceState.getInt(SELECTED_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// 将当前选中的Fragment页的索引保存到Bundle中
		outState.putInt(SELECTED_ITEM, 
			getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
		FragmentTransaction fragmentTransaction)
	{
	}
	// 当指定Tab被选中时激发该方法
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch (tab.getPosition()) {
		case 0:      
			fragmentMain=new FragmentMain();
			ft.replace(R.id.content ,fragmentMain);

			break;

		case 1:
			fragmentStat =new FragmentStat();
			ft.replace(R.id.content, fragmentStat);		
			break;
			
		case 2:
			fragmentSetting=new FragmentSetting();
			ft.replace(R.id.content,fragmentSetting );
			
			break;

		}

		// 提交事务
		ft.commit();
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
	}
}