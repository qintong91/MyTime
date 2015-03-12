package com.example.mytime;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends ActionBarActivity
{
	private Button buttonSignIn=null;
	private Button buttonSignUp=null;
    public EditText username=null;   
    //密码文本编辑框   
    private EditText password=null;  
    //登录按钮   
    private static String url_create_product = "http://iotmytime.duapp.com/sign_in.php";
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    
    protected void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign_in);
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		buttonSignIn = (Button) findViewById(R.id.button1);
		buttonSignIn.setOnClickListener( new SignInClickListener());
		buttonSignUp = (Button) findViewById(R.id.button2);
		buttonSignUp.setOnClickListener( new SignUpClickListener());
    }
    
    class SignUpClickListener implements View.OnClickListener
	{	
		//实现监听器类必须实现的方法，该方法将会作为事件处理器
		@Override
		public void onClick(View sdw)
		{
			Intent intent =new Intent();
			System.out.println("Sign up ~~~~~~~~~~~~~~~~~~~");
			
			intent.setClass(SignIn.this,SignUp.class);	
			SignIn.this.startActivity(intent);
		}		
	}
	class SignInClickListener implements View.OnClickListener
	{	
		//实现监听器类必须实现的方法，该方法将会作为事件处理器
		@Override
		public void onClick(View sdw)
		{
            username = (EditText) findViewById(R.id.editText1);   
            password = (EditText) findViewById(R.id.editText2); 
                
            new SignInTask().execute();
		}		
	}
	
	class SignInTask extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignIn.this);
			pDialog.setMessage("登录中..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String Sname = username.getText().toString();
			String Spassword = password.getText().toString();
			//TODO
			 

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", Sname + ""));
			params.add(new BasicNameValuePair("password", Spassword + ""));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json=null;
			json = jsonParser.makeHttpRequest(url_create_product,
					"GET", params);

			// check log cat fro response
			if(json==null)
			{
				Message msg = msgHandler.obtainMessage();
				msg.what = 0x38;
				msgHandler.sendMessage(msg);
			}
			//Log.d("Create Response", json.toString());

			// check for success tag
			else
			{
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(),
							FirstPage.class);
					i.putExtra("username",Sname);
					startActivity(i);

					// closing this screen
					finish();
				} else {
					Message msg = msgHandler.obtainMessage();
					msg.what = 0x34;
					msgHandler.sendMessage(msg);
					System.out.println("~~~~~~~~~~~~~~~~");
					
					// failed to create product
				}
			} catch (JSONException e) {
				System.out.println("~~~~??~~~~~~~");
				e.printStackTrace();
				
			}
			}

			return null;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
	private final Handler msgHandler = new Handler()
	{
        @SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) 
        {
                switch (msg.what) {
                case 0x34:
                	Toast.makeText(SignIn.this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        break;
                case 0x38:
                	Toast.makeText(SignIn.this, "无法获取网络连接", Toast.LENGTH_SHORT).show();
                        break;
                default:
                        break;
                }
        }
    };
}
