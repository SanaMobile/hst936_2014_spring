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

public class ShowdetailsA extends Activity implements OnClickListener,
		OnItemSelectedListener {
	Button edit;
	TextView curp, phone, email, zip, colony,  name, createdby,
			createdon, temperature, weight, height, waist, bp, symptoms, notes,
			updatedon, updatedby;
	TextView gender, cancer, diabetes, hypertension, pregnancy;
	String Curp, Name, Phone, Email, Zip, Colony, Parent, Gender, Cancer,
			Diabetes, Hypertension, Uname, Date_created, Temp, Weight, Height,
			Waist, Bp, Pregnancy, Symptoms, Notes, Updatedon, Updatedby;
	Spinner pe1, pe2, pe3, d1, d2, d3, t1, t2, t3, td1, td2, td3, tv1, tv2,
			tv3, tp1, tp2, tp3, lr1, lr2, lr3, lr4, lr5;
	EditText ped1, ped2, ped3, dd1, dd2, dd3, dnotes, tde1, tde2, tde3;

	CheckBox imp, rec, agg;

	TabHost th;
	TabSpec specs;
	String Physical1, Physical2, Physical3, Pd1, Pd2, Pd3, Diagnosis1,
			Diagnosis2, Diagnosis3, Dd1, Dd2, Dd3, Dnotes, Treatment1,
			Treatment2, Treatment3, Td1, Td2, Td3, Tv1, Tv2, Tv3, Tp1, Tp2,
			Tp3, Tdetails1, Tdetails2, Tdetails3, Lr1, Lr2, Lr3, Lr4, Lr5, Imp,
			Rec, Agg;
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/editdoctordetailsA.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.showdetailsa);
	
		update();
		
		th = (TabHost) findViewById(R.id.tabhosta);
		th.setup();
		specs = th.newTabSpec("tab111");
		specs.setContent(R.id.tab1a);
		specs.setIndicator("Patient Details");
		th.addTab(specs);
		specs = th.newTabSpec("tab211");
		specs.setContent(R.id.tab2a);
		specs.setIndicator("Enter Details");
		th.addTab(specs);
		Curp = PatientA.getLicence();

	}

	private void update() {
		// TODO Auto-generated method stub
		
		imp = (CheckBox) findViewById(R.id.impa);
		
		rec = (CheckBox) findViewById(R.id.recova);
		agg = (CheckBox) findViewById(R.id.agga);
		ped1 = (EditText) findViewById(R.id.epe1a);
		ped2 = (EditText) findViewById(R.id.epe2a);
		ped3 = (EditText) findViewById(R.id.epe3a);
	
		dd1 = (EditText) findViewById(R.id.dd1a);
		dd2 = (EditText) findViewById(R.id.dd2a);
		dd3 = (EditText) findViewById(R.id.dd3a);
		dnotes = (EditText) findViewById(R.id.dnotesa);
		tde1 = (EditText) findViewById(R.id.tdetails1a);
		tde2 = (EditText) findViewById(R.id.tdetails2a);
		tde3 = (EditText) findViewById(R.id.tdetails3a);
	
        edit = (Button) findViewById(R.id.dbta);
		edit.setOnClickListener(this);
		temperature = (TextView) findViewById(R.id.etvtempa);
		updatedon = (TextView) findViewById(R.id.etvupdatedona);
		updatedby = (TextView) findViewById(R.id.etvupdatedbya);
		height = (TextView) findViewById(R.id.etvheighta);
		weight = (TextView) findViewById(R.id.etvweighta);
		waist = (TextView) findViewById(R.id.etvwaista);
		notes = (TextView) findViewById(R.id.etvnotesa);
		symptoms = (TextView) findViewById(R.id.etvppsa);
		
		bp = (TextView) findViewById(R.id.etvblooda);
		pregnancy = (TextView) findViewById(R.id.etvpregnanta);
		createdby = (TextView) findViewById(R.id.etvcreatedbya);
		createdon = (TextView) findViewById(R.id.etvcreatedona);
		name = (TextView) findViewById(R.id.etvnamea);
		curp = (TextView) findViewById(R.id.etvcurpa);
		phone = (TextView) findViewById(R.id.etvphonea);
		email = (TextView) findViewById(R.id.etvemaila);
		zip = (TextView) findViewById(R.id.etvzipa);
		colony = (TextView) findViewById(R.id.etvcolonya);
		gender = (TextView) findViewById(R.id.etvgendera);
		cancer = (TextView) findViewById(R.id.etvcancera);
		diabetes = (TextView) findViewById(R.id.etvdiabetesa);
		hypertension = (TextView) findViewById(R.id.etvhypertensiona);

		pe1 = (Spinner) findViewById(R.id.spe1a);
		pe2 = (Spinner) findViewById(R.id.spe2a);
		pe3 = (Spinner) findViewById(R.id.spe3a);
		d1 = (Spinner) findViewById(R.id.d1a);
		d2 = (Spinner) findViewById(R.id.d2a);
		d3 = (Spinner) findViewById(R.id.d3a);
		t1 = (Spinner) findViewById(R.id.t1a);
		t2 = (Spinner) findViewById(R.id.t2a);
		t3 = (Spinner) findViewById(R.id.t3a);
		td1 = (Spinner) findViewById(R.id.td1a);
		td2 = (Spinner) findViewById(R.id.td2a);
		td3 = (Spinner) findViewById(R.id.td3a);
		tv1 = (Spinner) findViewById(R.id.tv1a);
		tv2 = (Spinner) findViewById(R.id.tv2a);
		tv3 = (Spinner) findViewById(R.id.tv3a);
		tp1 = (Spinner) findViewById(R.id.tp1a);
		tp2 = (Spinner) findViewById(R.id.tp2a);
		tp3 = (Spinner) findViewById(R.id.tp3a);
		lr1 = (Spinner) findViewById(R.id.lr1a);
		lr2 = (Spinner) findViewById(R.id.lr2a);
		lr3 = (Spinner) findViewById(R.id.lr3a);
		lr4 = (Spinner) findViewById(R.id.lr4a);
		lr5 = (Spinner) findViewById(R.id.lr5a);
	
		pe1.setOnItemSelectedListener(this);
		pe2.setOnItemSelectedListener(this);
		pe3.setOnItemSelectedListener(this);
		d1.setOnItemSelectedListener(this);
		d2.setOnItemSelectedListener(this);
		d3.setOnItemSelectedListener(this);
		t1.setOnItemSelectedListener(this);
		t2.setOnItemSelectedListener(this);
		t3.setOnItemSelectedListener(this);
		td1.setOnItemSelectedListener(this);
		td2.setOnItemSelectedListener(this);
		td3.setOnItemSelectedListener(this);
		tv1.setOnItemSelectedListener(this);
		tv2.setOnItemSelectedListener(this);
		tv3.setOnItemSelectedListener(this);
		tp1.setOnItemSelectedListener(this);
		tp2.setOnItemSelectedListener(this);
		tp3.setOnItemSelectedListener(this);
		lr1.setOnItemSelectedListener(this);
		lr2.setOnItemSelectedListener(this);
		lr3.setOnItemSelectedListener(this);
		lr4.setOnItemSelectedListener(this);
		lr5.setOnItemSelectedListener(this);
		
		curp.setText(JSONParser.getCurp());
		name.setText(JSONParser.getName());
		phone.setText(JSONParser.getPhone());
		email.setText(JSONParser.getEmail());
		zip.setText(JSONParser.getZip());
		colony.setText(JSONParser.getColony());
		createdby.setText(JSONParser.getCreated_by());
		createdon.setText(JSONParser.getCreated_on());
		temperature.setText(JSONParser.getTemp());
		height.setText(JSONParser.getHeight());
		weight.setText(JSONParser.getWeight());
		pregnancy.setText(JSONParser.getPregnancy());
		waist.setText(JSONParser.getWaist());
		notes.setText(JSONParser.getNotes());
		symptoms.setText(JSONParser.getSymptoms());
		bp.setText(JSONParser.getBp());
	
	
		
		
		gender.setText(JSONParser.getGender());
		diabetes.setText(JSONParser.getDiabetes());
		cancer.setText(JSONParser.getCancer());
		hypertension.setText(JSONParser.getHypertension());
	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		if (imp.isChecked())
			Imp = "Yes";
		else
			Imp = "No";
		if (rec.isChecked())
			Rec = "Yes";
		else
			Rec = "No";
		if (agg.isChecked())
			Agg = "Yes";
		else
			Agg = "No";
		Pd1 = ped1.getText().toString();
		Pd2 = ped2.getText().toString();
		Pd3 = ped3.getText().toString();
		Dd1 = dd1.getText().toString();
		Dd2 = dd2.getText().toString();
		Dd3 = dd3.getText().toString();
		Dnotes = dnotes.getText().toString();
		Tdetails1 = tde1.getText().toString();
		Tdetails2 = tde2.getText().toString();
		Tdetails3 = tde3.getText().toString();
		new AttemptLogin().execute();
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowdetailsA.this);
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
				params.add(new BasicNameValuePair("Physical1", Physical1));
				params.add(new BasicNameValuePair("Physical2", Physical2));
				params.add(new BasicNameValuePair("Physical3", Physical3));
				params.add(new BasicNameValuePair("Pd1", Pd1));
				params.add(new BasicNameValuePair("Pd2", Pd2));
				params.add(new BasicNameValuePair("Pd3", Pd3));
				params.add(new BasicNameValuePair("Diagnosis1", Diagnosis1));
				params.add(new BasicNameValuePair("Diagnosis2", Diagnosis2));
				params.add(new BasicNameValuePair("Diagnosis3", Diagnosis3));
				params.add(new BasicNameValuePair("Dd1", Dd1));
				params.add(new BasicNameValuePair("Dd2", Dd2));
				params.add(new BasicNameValuePair("Dd3", Dd3));
				params.add(new BasicNameValuePair("Dnotes", Dnotes));
				params.add(new BasicNameValuePair("Treatment1", Treatment1));
				params.add(new BasicNameValuePair("Treatment2", Treatment2));
				params.add(new BasicNameValuePair("Treatment3", Treatment3));
				params.add(new BasicNameValuePair("Td1", Td1));
				params.add(new BasicNameValuePair("Td2", Td2));
				params.add(new BasicNameValuePair("Td3", Td3));
				params.add(new BasicNameValuePair("Tv1", Tv1));
				params.add(new BasicNameValuePair("Tv2", Tv2));
				params.add(new BasicNameValuePair("Tv3", Tv3));
				params.add(new BasicNameValuePair("Tp1", Tp1));
				params.add(new BasicNameValuePair("Tp2", Tp2));
				params.add(new BasicNameValuePair("Tp3", Tp3));
				params.add(new BasicNameValuePair("Tdetails1", Tdetails1));
				params.add(new BasicNameValuePair("Tdetails2", Tdetails2));
				params.add(new BasicNameValuePair("Tdetails3", Tdetails3));
				params.add(new BasicNameValuePair("Lr1", Lr1));
				params.add(new BasicNameValuePair("Lr2", Lr2));
				params.add(new BasicNameValuePair("Lr3", Lr3));
				params.add(new BasicNameValuePair("Lr4", Lr4));
				params.add(new BasicNameValuePair("Lr5", Lr5));
				params.add(new BasicNameValuePair("Imp", Imp));
				params.add(new BasicNameValuePair("Rec", Rec));
				params.add(new BasicNameValuePair("Agg", Agg));
				params.add(new BasicNameValuePair("Curp", Curp));

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
				Toast.makeText(ShowdetailsA.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		switch (parent.getId()) {
		case R.id.spe1a:
			Physical1 = parent.getSelectedItem().toString();
			break;
		case R.id.spe2a:
			Physical2 = parent.getSelectedItem().toString();
			break;
		case R.id.spe3a:
			Physical3 = parent.getSelectedItem().toString();
			break;
		case R.id.d1a:
			Diagnosis1 = parent.getSelectedItem().toString();
			break;
		case R.id.d2a:
			Diagnosis2 = parent.getSelectedItem().toString();
			break;
		case R.id.d3a:
			Diagnosis3 = parent.getSelectedItem().toString();
			break;
		case R.id.t1a:
			Treatment1 = parent.getSelectedItem().toString();
			break;
		case R.id.t2a:
			Treatment2 = parent.getSelectedItem().toString();
			break;
		case R.id.t3a:
			Treatment3 = parent.getSelectedItem().toString();
			break;
		case R.id.td1a:
			Td1 = parent.getSelectedItem().toString();
			break;
		case R.id.td2a:
			Td2 = parent.getSelectedItem().toString();
			break;
		case R.id.td3a:
			Td3 = parent.getSelectedItem().toString();
			break;
		case R.id.tv1a:
			Tv1 = parent.getSelectedItem().toString();
			break;
		case R.id.tv2a:
			Tv2 = parent.getSelectedItem().toString();
			break;
		case R.id.tv3a:
			Tv3 = parent.getSelectedItem().toString();
			break;
		case R.id.tp1a:
			Tp1 = parent.getSelectedItem().toString();
			break;
		case R.id.tp2a:
			Tp2 = parent.getSelectedItem().toString();
			break;
		case R.id.tp3a:
			Tp3 = parent.getSelectedItem().toString();
			break;
		case R.id.lr1a:
			Lr1 = parent.getSelectedItem().toString();
			break;
		case R.id.lr2a:
			Lr2 = parent.getSelectedItem().toString();
			break;
		case R.id.lr3a:
			Lr3 = parent.getSelectedItem().toString();
			break;
		case R.id.lr4a:
			Lr4 = parent.getSelectedItem().toString();
			break;
		case R.id.lr5a:
			Lr5 = parent.getSelectedItem().toString();
			break;

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}