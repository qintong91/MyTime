package com.example.mytime;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends ActionBarActivity {

	private Button buttonCancel=null;
	private Button buttonSignUp=null;
	private EditText username=null;
	private EditText password=null;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://iotmytime.duapp.com/sign_up.php";
	private EditText mail=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign_up);
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		buttonSignUp=(Button)findViewById(R.id.button3);
		buttonSignUp.setOnClickListener( new SignUpClickListener());
		buttonCancel=(Button)findViewById(R.id.button4);
		buttonCancel.setOnClickListener( new CancelClickListener());

	}	 
		 
	class SignUpClickListener implements View.OnClickListener
	{	
		//实现监听器类必须实现的方法，该方法将会作为事件处理器
		@Override
		public void onClick(View sdw)
		{
			username = (EditText)findViewById(R.id.editText3);    
            mail = (EditText)findViewById(R.id.editText4);   
            password = (EditText)findViewById(R.id.editText5);
            
            new SignUpTask().execute();	
		}		
	}
	class CancelClickListener implements View.OnClickListener
	{	
		//实现监听器类必须实现的方法，该方法将会作为事件处理器
		@Override
		public void onClick(View sdw)
		{
			System.out.println("Cancel ~~~~~~~~~~~~~~~~~~~");
			 finish();
		}		
	}
	class SignUpTask extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUp.this);
			pDialog.setMessage("注册中..");
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
			String Mail = mail.getText().toString(); 

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", Sname + ""));
			params.add(new BasicNameValuePair("password", Spassword + ""));
			params.add(new BasicNameValuePair("mail", Mail + "")); 

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"GET", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Message msg = msgHandler.obtainMessage();
					msg.what = 0x34;
					msgHandler.sendMessage(msg);
					// closing this screen
					finish();
				} else if(success ==2){
					Message msg = msgHandler.obtainMessage();
					msg.what = 0x35;
					msgHandler.sendMessage(msg);
					System.out.println("~~~~~~~~~~~~~~~~");
					
					// failed to create product
				}
				 else {
					Message msg = msgHandler.obtainMessage();
					msg.what = 0x36;
					msgHandler.sendMessage(msg);
					System.out.println("~~~~~~~~~~~~~~~~");
					
					// failed to create product
				}
			} catch (JSONException e) {
				System.out.println("~~~~??~~~~~~~");
				e.printStackTrace();
				
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
	private final Handler msgHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
                switch (msg.what) {
                case 0x34:
                	Toast.makeText(SignUp.this, "创建成功", Toast.LENGTH_SHORT).show();
                        break;
                case 0x35:
                	Toast.makeText(SignUp.this, "该用户名已存在！", Toast.LENGTH_SHORT).show();
                        break;
                case 0x36:
                	Toast.makeText(SignUp.this, "创建失败", Toast.LENGTH_SHORT).show();
                        break;
                default:
                        break;
                }
        }
};
}
