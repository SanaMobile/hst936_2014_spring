package com.example.mexicoehr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mexicoehr.CreateA.AttemptLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ShowTechA extends Activity implements OnClickListener {
	Button edit;
	TextView lr1, lr2, lr3, lr4, lr5, t1, t2, t3, t4, t5,f;
	EditText rl1, rl2, rl3, rl4, rl5;
	Button rbr;

	TabHost th;
	TabSpec specs;
	public static String Lr1, Lr2, Lr3, Lr4, Lr5, Licence, lk1, lk2, lk3, lk4,
			lk5;
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/editlabrequesta.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request);
		update();
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		specs = th.newTabSpec("tab1");
		specs.setContent(R.id.tk1);
		specs.setIndicator("Lab Request");
		th.addTab(specs);
		specs = th.newTabSpec("tab2");
		specs.setContent(R.id.tk2);
		specs.setIndicator("Record Results");
		th.addTab(specs);

	}

	private void update() {
		// TODO Auto-generated method stub

		lr1 = (TextView) findViewById((R.id.lk1));
		f = (TextView) findViewById((R.id.View1));
		lr2 = (TextView) findViewById((R.id.lk2));
		lr3 = (TextView) findViewById((R.id.lk3));
		lr4 = (TextView) findViewById((R.id.lk4));
		lr5 = (TextView) findViewById((R.id.lk5));

		t1 = (TextView) findViewById((R.id.lk11));
		t2 = (TextView) findViewById((R.id.lk21));
		t3 = (TextView) findViewById((R.id.lk31));
		t4 = (TextView) findViewById((R.id.lk41));
		t5 = (TextView) findViewById((R.id.lk51));
		
		rl1 = (EditText) findViewById((R.id.rlk1));
		rl2 = (EditText) findViewById((R.id.rlk2));
		rl3 = (EditText) findViewById((R.id.rlk3));
		rl4 = (EditText) findViewById((R.id.rlk4));
		rl5 = (EditText) findViewById((R.id.rlk5));
		rbr = (Button) findViewById(R.id.rbr);
		rbr.setOnClickListener(this);
	
		lk1=JSONParser.getLr1();
		lk2=JSONParser.getLr2();
		lk3=JSONParser.getLr3();
		lk4=JSONParser.getLr4();
		lk5=JSONParser.getLr5();
		if(lk1.equalsIgnoreCase("None") && lk2.equalsIgnoreCase("None") && lk3.equalsIgnoreCase("None") && lk4.equalsIgnoreCase("None") && lk5.equalsIgnoreCase("None"))
		{
			f.setVisibility(View.VISIBLE);
			f.setText("NOT AVAILABLE");
		}
		if(!(lk1.equalsIgnoreCase("None")))
		{rl1.setVisibility(View.VISIBLE);
			lr1.setVisibility(View.VISIBLE);
			t1.setVisibility(View.VISIBLE);
			rbr.setVisibility(View.VISIBLE);
			lr1.setText(lk1);
			t1.setText(lk1);
			
		}
		if(!(lk2.equalsIgnoreCase("None")))
		{rl2.setVisibility(View.VISIBLE);
			lr2.setVisibility(View.VISIBLE);
			lr2.setText(lk2);
			t2.setText(lk2);
			t2.setVisibility(View.VISIBLE);
			rbr.setVisibility(View.VISIBLE);
		}
		if(!(lk3.equalsIgnoreCase("None")))
		{rl3.setVisibility(View.VISIBLE);
			lr3.setVisibility(View.VISIBLE);
			lr3.setText(lk3);
			t3.setText(lk3);
			t3.setVisibility(View.VISIBLE);
			rbr.setVisibility(View.VISIBLE);
		}
		if(!(lk4.equalsIgnoreCase("None")))
		{rl4.setVisibility(View.VISIBLE);
			lr4.setVisibility(View.VISIBLE);
			lr4.setText(lk4);
			t4.setText(lk4);
			t4.setVisibility(View.VISIBLE);
			rbr.setVisibility(View.VISIBLE);
		}
		if(!(lk5.equalsIgnoreCase("None")))
		{rl5.setVisibility(View.VISIBLE);
			lr5.setVisibility(View.VISIBLE);
			lr5.setText(lk5);
			t5.setText(lk5);
			t5.setVisibility(View.VISIBLE);
			rbr.setVisibility(View.VISIBLE);
		}
		
		
		
		
		
		
		
	}

	@Override
	public void onClick(View v) {
		Licence = TechdetailsC.getLicence();
		Lr1 = rl1.getText().toString();
		Lr2 = rl2.getText().toString();
		Lr3 = rl3.getText().toString();
		Lr4 = rl4.getText().toString();
		Lr5 = rl5.getText().toString();

		new AttemptLogin().execute();
		// TODO Auto-generated method stub

	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowTechA.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Lab1", Lr1));
				params.add(new BasicNameValuePair("Lab2", Lr2));
				params.add(new BasicNameValuePair("Lab3", Lr3));
				params.add(new BasicNameValuePair("Lab4", Lr4));
				params.add(new BasicNameValuePair("Lab5", Lr5));
				params.add(new BasicNameValuePair("Curp", Licence));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// check your log for json response
				Log.d("insert attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("insert Successful!", json.toString());

					finish();

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Insert Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(ShowTechA.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

}