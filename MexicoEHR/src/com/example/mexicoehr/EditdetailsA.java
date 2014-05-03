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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class EditdetailsA extends Activity implements OnClickListener,OnCheckedChangeListener {
	Button edit,final1;
	TextView curp, phone, email, zip, colony,parent, name, createdby,
			createdon, updatedon, updatedby;
	EditText temperature, weight, height, waist, bp, symptoms, notes;
	RadioGroup gender, cancer, diabetes, hypertension, pregnancy;
	RadioGroup rr1, rr2, rr3;
	String Curp, Name, Phone, Email, Zip, Colony,  Gender, Cancer,
			Diabetes, Hypertension, Uname, Date_created, Temp, Weight, Height,
			Waist, Bp, Pregnancy, Symptoms, Notes, Updatedon, Updatedby, T1,
			T2, T3,s,s1,t,t1;
	int mm,yy,dd,dd1,mm1,yy1,h1,m1,h2,m2;
	TextView c1, c2, c3,and;
	DatePicker dp1,dp2;
	TimePicker tp1,tp2;
	private ProgressDialog pDialog;

	TabHost th;
	TabSpec specs;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/editdetailsA.php";
	private static final String LOGIN_URL1 = "http://www.techcrunch.net.in/ehr/final1a.php";
	private static final String LOGIN_URL5 = "http://www.techcrunch.net.in/ehr/final2a.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editdetails);
		update();
		
		th = (TabHost) findViewById(R.id.tabhost);
		
		th.setup();
		specs = th.newTabSpec("tab1");
		specs.setContent(R.id.tag1);
		specs.setIndicator("Patient Record");
		th.addTab(specs);
		specs = th.newTabSpec("tab2");
		specs.setContent(R.id.tag2);
		specs.setIndicator("Edit Details 1");
		th.addTab(specs);
		specs = th.newTabSpec("tab3");
		specs.setContent(R.id.tag3);
		specs.setIndicator("Edit Details 2");
		th.addTab(specs);
		
		Date date = new Date();
		Updatedon = date.toString();
		new AttemptLogin1().execute();
	}

	private void update() {
		// TODO Auto-generated method stub
		and =(TextView) findViewById(R.id.parent6);
		and.setVisibility(View.GONE);
		edit = (Button) findViewById(R.id.etsubmit);
		final1 = (Button) findViewById(R.id.final1);
		final1.setOnClickListener(this);
		dp1=(DatePicker)findViewById(R.id.datePicker1);
		dp2=(DatePicker)findViewById(R.id.datePicker2);
		tp1=(TimePicker)findViewById(R.id.timePicker1);
		tp2=(TimePicker)findViewById(R.id.timePicker2);
		
		
		c1 = (TextView) findViewById(R.id.trt1);
		c2 = (TextView) findViewById(R.id.trt2);
		c3 = (TextView) findViewById(R.id.trt3);
		c1.setText(JSONParser.getTrt1());
		c2.setText(JSONParser.getTrt2());
		c3.setText(JSONParser.getTrt3());
		updatedon = (TextView) findViewById(R.id.etupdatedon);
		updatedby = (TextView) findViewById(R.id.etupdatedby);
		createdby = (TextView) findViewById(R.id.etcreatedby);
		createdon = (TextView) findViewById(R.id.etcreatedon);
		name = (TextView) findViewById(R.id.etname);
		curp = (TextView) findViewById(R.id.etcurp);
		phone = (TextView) findViewById(R.id.etphone);
		email = (TextView) findViewById(R.id.etemail);
		zip = (TextView) findViewById(R.id.etzip);
		colony = (TextView) findViewById(R.id.etcolony);
		parent = (TextView) findViewById(R.id.etparenthood);
		parent.setVisibility(View.GONE);
		height = (EditText) findViewById(R.id.etheight);
		weight = (EditText) findViewById(R.id.etweight);
		waist = (EditText) findViewById(R.id.etwaist);
		notes = (EditText) findViewById(R.id.etnotes);
		symptoms = (EditText) findViewById(R.id.etpps);
		bp = (EditText) findViewById(R.id.etblood);
		temperature = (EditText) findViewById(R.id.ettemp);
		rr1 = (RadioGroup) findViewById(R.id.tradio1);
		rr2 = (RadioGroup) findViewById(R.id.tradio2);
		rr3 = (RadioGroup) findViewById(R.id.tradio3);
		gender = (RadioGroup) findViewById(R.id.etgender);
		cancer = (RadioGroup) findViewById(R.id.etcancer);
		diabetes = (RadioGroup) findViewById(R.id.etdiabetes);
		hypertension = (RadioGroup) findViewById(R.id.ethypertension);
		edit.setOnClickListener(this);
		rr1.setOnCheckedChangeListener(this);
		rr3.setOnCheckedChangeListener(this);
		rr2.setOnCheckedChangeListener(this);
		gender.setOnCheckedChangeListener(this);
		cancer.setOnCheckedChangeListener(this);
		diabetes.setOnCheckedChangeListener(this);
		hypertension.setOnCheckedChangeListener(this);
		curp.setText(JSONParser.getCurp());
		name.setText(JSONParser.getName());
		phone.setText(JSONParser.getPhone());
		email.setText(JSONParser.getEmail());
		zip.setText(JSONParser.getZip());
		colony.setText(JSONParser.getColony());
		parent.setText(JSONParser.getRelation());
		createdby.setText(JSONParser.getCreated_by());
		createdon.setText(JSONParser.getCreated_on());
		
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		
		case R.id.final1:
			dd=dp1.getDayOfMonth();
			yy=dp1.getYear();
			dd1=dp2.getDayOfMonth();
			mm=dp1.getMonth();
			mm1=dp2.getMonth();
			yy1=dp2.getYear();
			h1=tp1.getCurrentHour();
			m1=tp1.getCurrentMinute();
			h2=tp2.getCurrentHour();
			m2=tp2.getCurrentMinute();
		 s = "" + dd + "-" + mm + "-" + yy;
			 s1 = "" + dd1 + "-" + mm1 + "-" + yy1;
			 t = "" + h1 + ":" + m1;
			 t1 = "" + h2 + ":" + m2 ;
			
			
		
		
			new AttemptLogin3().execute();
			break;
			
			
		case R.id.etsubmit:
			Temp = temperature.getText().toString();
			Weight = weight.getText().toString();
			Height = height.getText().toString();
			Waist = waist.getText().toString();
			Bp = bp.getText().toString();
			Notes = notes.getText().toString();
			Symptoms = symptoms.getText().toString();
			Updatedby = Nurse.getName();
			new AttemptLogin().execute();
			break;
		}
		
	
		
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditdetailsA.this);
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
			Temp = temperature.getText().toString();
			Weight = weight.getText().toString();
			Height = height.getText().toString();
			Waist = waist.getText().toString();
			Bp = bp.getText().toString();
			Notes = notes.getText().toString();
			Symptoms = symptoms.getText().toString();
			Curp = curp.getText().toString();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Temp", Temp));
				params.add(new BasicNameValuePair("Weight", Weight));
				params.add(new BasicNameValuePair("Height", Height));
				params.add(new BasicNameValuePair("Waist", Waist));
				params.add(new BasicNameValuePair("Bp", Bp));
				params.add(new BasicNameValuePair("Notes", Notes));
				params.add(new BasicNameValuePair("Symptoms", Symptoms));
				params.add(new BasicNameValuePair("Pragnancy", Pregnancy));
				params.add(new BasicNameValuePair("Updatedby", Updatedby));
				params.add(new BasicNameValuePair("Updatedon", Updatedon));
				params.add(new BasicNameValuePair("Curp", Curp));
				params.add(new BasicNameValuePair("T1", T1));
				params.add(new BasicNameValuePair("T2", T2));
				params.add(new BasicNameValuePair("T3", T3));

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
				Toast.makeText(EditdetailsA.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	
	
	
	
	
	
	class AttemptLogin3 extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditdetailsA.this);
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
			Temp = temperature.getText().toString();
			Weight = weight.getText().toString();
			Height = height.getText().toString();
			Waist = waist.getText().toString();
			Bp = bp.getText().toString();
			Notes = notes.getText().toString();
			Symptoms = symptoms.getText().toString();
			Curp = curp.getText().toString();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("s", s));
				params.add(new BasicNameValuePair("s1", s1));
				params.add(new BasicNameValuePair("t", t));
				params.add(new BasicNameValuePair("t1", t1));
				
				
				params.add(new BasicNameValuePair("Curp", Curp));
				

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL5, "POST",
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
				Toast.makeText(EditdetailsA.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	class AttemptLogin1 extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditdetailsA.this);
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
			Temp = temperature.getText().toString();
			Weight = weight.getText().toString();
			Height = height.getText().toString();
			Waist = waist.getText().toString();
			Bp = bp.getText().toString();
			Notes = notes.getText().toString();
			Symptoms = symptoms.getText().toString();
			Curp = curp.getText().toString();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Curp", Curp));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL1,
						"POST", params);

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
				Toast.makeText(EditdetailsA.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.etpregnant:
			int id = pregnancy.getCheckedRadioButtonId();
			if (id == R.id.etpyes)
				Pregnancy = "Yes";
			else
				Pregnancy = "No";

			break;

		case R.id.tradio1:
			int id1 = rr1.getCheckedRadioButtonId();
			if (id1 == R.id.t1yes)
				T1 = "Took";
			else
				T1 = "Did'nt take";
			break;
		case R.id.tradio2:
			int id2 = rr2.getCheckedRadioButtonId();
			if (id2 == R.id.t2yes)
				T2 = "Took";
			else
				T2 = "Did'nt take";
			break;
		case R.id.tradio3:
			int id3 = rr3.getCheckedRadioButtonId();
			if (id3 == R.id.t3yes)
				T3 = "Took";
			else
				T3 = "Did'nt take";
		}

	}
}