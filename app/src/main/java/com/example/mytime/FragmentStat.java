package com.example.mytime;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytime.FragmentMain.Site;
import com.example.mytime.FragmentMain.Step;
import com.example.mytime.SignIn.SignInTask;
 
public class FragmentStat extends Fragment {
	private Button statButton[]=new Button[4];
	private TextView statTextview[]=new TextView[4];
	private Cursor mCursor;
	private GraphicalView mChartView;
	private GraphicalView mChartView2;
	private ArrayList<View> pageViews;
	private ViewGroup main;
	private ViewGroup group;
	private ViewPager viewPager;
	private ImageView[] imageViews;
	private ImageView imageView;
	private CategorySeries mSeries = new CategorySeries("时间分布");
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private XYMultipleSeriesRenderer mRenderer2 ;
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN ,Color.YELLOW};
	 private ArrayList<Step> step;
	 private ArrayList<Site> siteList;
	 private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 private View statView;
	 JSONObject jsonObject2=new JSONObject();  
	 private static String url = "http://iotmytime.duapp.com/query.php";
	 JSONParser jsonParser = new JSONParser();
//	private int loc_id = 0;
	  int[] m=new int[4];
	 String[] ButtonInfo[]=new String[4][3];
	static Handler handler;
	private long everageTime[];
	private boolean done;
	private boolean sucess;
	@SuppressWarnings("null")
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		handler = new Handler(){
//			@Override
//			public void handleMessage(Message msg) {
////				mLocationDB.update(0,null,null, null);
//				super.handleMessage(msg);
//				mCursor.requery();
//				stepView.invalidateViews();
//			}
//		};
		
      FirstPage firstPage=(FirstPage)getActivity();
        step=firstPage.getFragmentMain().getStep();
      siteList=firstPage.getFragmentMain().getSiteList();
      long[] time=new long[siteList.size()];
      long[] time2=new long[siteList.size()];
      String[] name=new String[siteList.size()];
    
      for(int i=0;i<step.size();i++)
      {
    	  for(int j=0;j<siteList.size();j++)
    	  {
    		  if(step.get(i).getSite().equals(siteList.get(j)))
        	  {
        		  time[j]=time[j]+step.get(i).getLeaveTime().getTime()-step.get(i).getArriveTime().getTime();
        		  
        	  }
    	  }
    	  
      }
      for(int i=0;i<siteList.size();i++)
      {
    	  name[i]=siteList.get(i).getName();
      }

     //前三统计
      		for(int i=0;i<siteList.size();i++)
      		{
      			time2[i]=time[i];
      		}
      	   
		   pageViews = new ArrayList<View>();
		   statView=inflater.inflate(R.layout.stat_button_page, null);		   
		   statButton[1]=(Button) statView.findViewById(R.id.statButton1);
		   statButton[2]=(Button) statView.findViewById(R.id.statButton2);
		   statButton[3]=(Button) statView.findViewById(R.id.statButton3);
		   statTextview[1]=(TextView)statView.findViewById(R.id.statEditText1);
		   statTextview[2]=(TextView)statView.findViewById(R.id.statEditText2);
		   statTextview[3]=(TextView)statView.findViewById(R.id.statEditText3);
		   
		   int maxNum[]=new int[4];
		   long maxTime[] = new long[4];
		   for(int i=1;i<4;i++)
		   {
			   maxTime[i]=0;
		   }
		   for(int i=1;i<4;i++)
		   {
			   for(int j=0;j<siteList.size();j++)
			   {
				    
				   if(time2[j]>maxTime[i])
				   {
					   maxNum[i]=j;
					   maxTime[i]=time2[j];					   
				   }
			   }
			   time2[maxNum[i]]=0;
			   
			   statTextview[i].setText(siteList.get(maxNum[i]).getName());
			   long hour=time[maxNum[i]]/(1000 * 60 * 60);
			   long minute=(time[maxNum[i]]-hour*(1000 * 60 * 60))/(1000 * 60);
			   long second=(time[maxNum[i]]-hour*(1000 * 60 * 60)-minute*(1000*60))/1000;
			   ButtonInfo[i][0]=hour+"小时"+minute+"分钟"+second+"秒";
			   ButtonInfo[i][1]=siteList.get(maxNum[i]).getTimes()+"次";
			   float pertime=(float)((float)(time[maxNum[i]])/(1000*60*siteList.get(maxNum[i]).getTimes()));
			   DecimalFormat decimalFormat=new DecimalFormat("0.00");
			   ButtonInfo[i][2]=decimalFormat.format(pertime)+"分/次";			   
		   }
		   
	 
		  
		   for(int i=1;i<4;i++)
		   {
			   m[i]=0;
			   statButton[i].setText(ButtonInfo[i][0]);
			   final int num=i;
			   statButton[i].setOnClickListener(new OnClickListener(){  
					    @Override  
					    public void onClick(View v)
					    {
					    	m[num]++;
					    	m[num]=m[num]%3;
					    	statButton[num].setText(ButtonInfo[num][m[num]]);
					    }         
					     });  
		   }
		   
		   
		   
		   
		   
		   pageViews.add(statView);
		//饼状图 
		    mRenderer.setChartTitle("总时间分布");
		    mRenderer.setZoomButtonsVisible(false);
		    mRenderer.setStartAngle(0);
		    mRenderer.setDisplayValues(false);
		    mRenderer.setClickEnabled(true);
		    mRenderer.setLabelsTextSize(25); // 设置轴标签文本大小
		    mRenderer. setLegendTextSize(25); // 设置图例文本大小
		    for(int i=0;i<siteList.size();i++)
		    {
		    	mSeries.add(name[i], time[i]);
		    	SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		        renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
		        mRenderer.addSeriesRenderer(renderer);		    	
		    }
	        mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
	        mChartView.repaint(); 	        
			
	        pageViews.add(mChartView);
			
		//平均每日各位置停留时间统计
			String day=null;
			int days=0;
			for(int i=0;i<step.size();i++)
			{
				 if(!(sdf.format(step.get(i).getArriveTime()).equals(day)))
				 {
					 days++;
					 day=sdf.format(step.get(i).getArriveTime());
				 }
				
				
			}
 if(days!=0)
 {
		 for(int i=0;i<siteList.size();i++)
		 {
			 siteList.get(i).setEverageTime(time[i]/days);
		 }
 }
		  
		  StringBuilder builder = new StringBuilder();   
		  JSONArray array=new JSONArray();  
          for(int i=0;i<siteList.size();i++){  
              JSONObject jsonObject=new JSONObject();  
              jsonObject.put("location", siteList.get(i).getName());  
              jsonObject.put("time", siteList.get(i).getEverageTime());         
              array.add(jsonObject);  
          }  
         
          jsonObject2.put("username", getActivity().getIntent().getStringExtra("username"));
          jsonObject2.put("locations",array);
          done=false;
          new GetAveTask().execute();
          sucess=false;
          while(!done){};
        	if(sucess)  
        	{
        		mRenderer2 = new XYMultipleSeriesRenderer();
        		mRenderer2.setBackgroundColor(Color.WHITE);
        		mRenderer2.setMarginsColor(Color.WHITE);
				mRenderer2.setAxisTitleTextSize(25);
			    mRenderer2.setChartTitleTextSize(25);
			    mRenderer2.setLabelsTextSize(20);
			    mRenderer2.setLegendTextSize(20);
			    mRenderer2.setMargins(new int[] {20, 30, 20, 0});
			    mRenderer2.setFitLegend(true);
			    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			    r.setColor(Color.BLUE);
			    mRenderer2.addSeriesRenderer(r);
			    r = new SimpleSeriesRenderer();
			    r.setColor(Color.GREEN);
			    mRenderer2.addSeriesRenderer(r);
			    
				mRenderer2.setChartTitle("每日各地平均时长");
				mRenderer2.setXTitle("地点");
				mRenderer2.setYTitle("时长(分钟)");
				mRenderer2.setXAxisMin(0);
				//mRenderer2.setFitLegend(true);
				//mRenderer2.setYAxisMin(0);
				//mRenderer2.setYAxisMax(210);
				XYSeries series1 = new XYSeries("你的平均时长");
				 XYSeries series2 = new XYSeries("所有用户平均时长");
				 mRenderer2.addXTextLabel(0,"");
				 XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				for(int i=0;i<siteList.size();i++){
					
					
			 		mRenderer2.addXTextLabel(i+1,siteList.get(i).getName());
					series1.add(i+1, (siteList.get(i).getEverageTime()/60000));
					series2.add(i+1, (siteList.get(i).getEverageEveryoneTime()/60000));
			    }
				dataset.addSeries(series1);
				dataset.addSeries(series2);
				mChartView2=ChartFactory.getBarChartView(getActivity(), dataset, mRenderer2, Type.DEFAULT);
				 mChartView2.repaint(); 	        
				 pageViews.add(mChartView2);
        	}
        	 
 
		 		 
		main = (ViewGroup) inflater.inflate(R.layout.fragment1_2, null);
		group = (ViewGroup) main.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) main.findViewById(R.id.guidePages);
		//初始化导航图表
		imageViews = new ImageView[pageViews.size()];
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(getActivity());	
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(20, 0, 20, 0);
			imageViews[i] = imageView;
			if (i == 0) {
				// 默认选中第一张图片
				imageViews[i]
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.page_indicator);
			}
			group.addView(imageViews[i]);
		}
		
		
		viewPager.setAdapter(new GuidePageAdapter());
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	 	return main;
		
	}
	
	class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.page_indicator_focused);

				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}
	 protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
		      List<double[]> yValues) {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    addXYSeries(dataset, titles, xValues, yValues, 0);
		    return dataset;
		  }

		  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
		      List<double[]> yValues, int scale) {
		    int length = titles.length;
		    for (int i = 0; i < length; i++) {
		      XYSeries series = new XYSeries(titles[i], scale);
		      double[] xV = xValues.get(i);
		      double[] yV = yValues.get(i);
		      int seriesLength = xV.length;
		      for (int k = 0; k < seriesLength; k++) {
		        series.add(xV[k], yV[k]);
		      }
		      dataset.addSeries(series);
		    }
		  }
			class GetAveTask extends AsyncTask<String, String, String> {

				/**
				 * Before starting background thread Show Progress Dialog
				 * */
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
	
				}

				/**
				 * Creating product
				 * */
				protected String doInBackground(String... args) {
					HttpClient httpClient = new DefaultHttpClient();  
			          HttpPost httpPost = new HttpPost(url);  
			          List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("data", jsonObject2 + ""));
						org.json.JSONArray jsonArray=null;
						jsonArray= jsonParser.makeHttpRequestArray(url
								,"GET",params); 
					 
			      //    jsonArray.
					// check log cat fro response
					if(jsonArray==null)
					{
						Message msg = msgHandler.obtainMessage();
						msg.what = 0x38;
						msgHandler.sendMessage(msg);
						sucess=false;
						done=true;
					}
					else
					{
						for(int i=0;i<siteList.size();i++)
						{
							for(int j=0;j<jsonArray.length();j++)
							{
								try {
									if(siteList.get(i).getName().equals(jsonArray.getJSONObject(j).getString("location")))
									{
										siteList.get(i).setEverageEveryoneTime(jsonArray.getJSONObject(j).getLong("avg"));
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						sucess=true;
						done=true;
					}
					
					
					//Log.d("Create Response", json.toString());

					// check for success tag
					 
					 

					return null;
				}
				
				/**
				 * After completing background task Dismiss the progress dialog
				 * **/
				protected void onPostExecute(String file_url) {
					// dismiss the dialog once done
					 
				}

			}
			private final Handler msgHandler = new Handler()
			{
		        @SuppressLint("HandlerLeak")
				public void handleMessage(Message msg) 
		        {
		                switch (msg.what) {
		                
		                case 0x38:
		                	Toast.makeText(getActivity(), "无法获取网络连接", Toast.LENGTH_SHORT).show();
		                        break;
		                default:
		                        break;
		                }
		        }
		    };

	 
	
}