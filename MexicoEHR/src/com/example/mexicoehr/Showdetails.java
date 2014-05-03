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

public class Showdetails extends Activity implements OnClickListener,
		OnItemSelectedListener {
	Button edit;
	TextView curp, phone, email, zip, colony, parent, name, createdby,
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
	TextView p,dg1,dg2,tt1,tt2,tt3,lb1,lb2,lb3,imptv,rectv,aggtv,pre1,pre2,pre3;
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/editdoctordetails.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdtails);
		update();
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		specs = th.newTabSpec("tab1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Patient Details");
		th.addTab(specs);
		specs = th.newTabSpec("tab2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Enter Details");
		th.addTab(specs);
		Curp = PatientC.getLicence();

	}

	private void update() {
		// TODO Auto-generated method stub
	
		p =(TextView)findViewById(R.id.phy1);
		 dg1=(TextView)findViewById(R.id.diag1tv);
		 dg2=(TextView)findViewById(R.id.diag2tv);
		 tt1=(TextView)findViewById(R.id.pres1tv);
		tt2 =(TextView)findViewById(R.id.pres2tv);
		 tt3=(TextView)findViewById(R.id.pres3tv);
		 lb1=(TextView)findViewById(R.id.lab1tv);
		lb2 =(TextView)findViewById(R.id.lab2tv);
		lb3 =(TextView)findViewById(R.id.lab3tv);
		imptv =(TextView)findViewById(R.id.imptv);
		rectv =(TextView)findViewById(R.id.rectv);
		aggtv =(TextView)findViewById(R.id.aggtv);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		imp = (CheckBox) findViewById(R.id.imp);
		rec = (CheckBox) findViewById(R.id.recov);
		agg = (CheckBox) findViewById(R.id.agg);
		ped1 = (EditText) findViewById(R.id.epe1);
		ped2 = (EditText) findViewById(R.id.epe2);
		ped3 = (EditText) findViewById(R.id.epe3);
		dd1 = (EditText) findViewById(R.id.dd1);
		dd2 = (EditText) findViewById(R.id.dd2);
		dd3 = (EditText) findViewById(R.id.dd3);
		dnotes = (EditText) findViewById(R.id.dnotes);
		tde1 = (EditText) findViewById(R.id.tdetails1);
		tde2 = (EditText) findViewById(R.id.tdetails2);
		tde3 = (EditText) findViewById(R.id.tdetails3);

		edit = (Button) findViewById(R.id.dbt);
		edit.setOnClickListener(this);
		temperature = (TextView) findViewById(R.id.etvtemp);
		updatedon = (TextView) findViewById(R.id.etvupdatedon);
		updatedby = (TextView) findViewById(R.id.etvupdatedby);
		height = (TextView) findViewById(R.id.etvheight);
		weight = (TextView) findViewById(R.id.etvweight);
		waist = (TextView) findViewById(R.id.etvwaist);
		notes = (TextView) findViewById(R.id.etvnotes);
		symptoms = (TextView) findViewById(R.id.etvpps);
		bp = (TextView) findViewById(R.id.etvblood);
		pregnancy = (TextView) findViewById(R.id.etvpregnant);
		createdby = (TextView) findViewById(R.id.etvcreatedby);
		createdon = (TextView) findViewById(R.id.etvcreatedon);
		name = (TextView) findViewById(R.id.etvname);
		curp = (TextView) findViewById(R.id.etvcurp);
		phone = (TextView) findViewById(R.id.etvphone);
		email = (TextView) findViewById(R.id.etvemail);
		zip = (TextView) findViewById(R.id.etvzip);
		colony = (TextView) findViewById(R.id.etvcolony);
		parent = (TextView) findViewById(R.id.etvparenthood);
		gender = (TextView) findViewById(R.id.etvgender);
		cancer = (TextView) findViewById(R.id.etvcancer);
		diabetes = (TextView) findViewById(R.id.etvdiabetes);
		hypertension = (TextView) findViewById(R.id.etvhypertension);
		pe1 = (Spinner) findViewById(R.id.spe1);
		pe2 = (Spinner) findViewById(R.id.spe2);
		pe3 = (Spinner) findViewById(R.id.spe3);
		d1 = (Spinner) findViewById(R.id.d1);
		d2 = (Spinner) findViewById(R.id.d2);
		d3 = (Spinner) findViewById(R.id.d3);
		t1 = (Spinner) findViewById(R.id.t1);
		t2 = (Spinner) findViewById(R.id.t2);
		t3 = (Spinner) findViewById(R.id.t3);
		td1 = (Spinner) findViewById(R.id.td1);
		td2 = (Spinner) findViewById(R.id.td2);
		td3 = (Spinner) findViewById(R.id.td3);
		tv1 = (Spinner) findViewById(R.id.tv1);
		tv2 = (Spinner) findViewById(R.id.tv2);
		tv3 = (Spinner) findViewById(R.id.tv3);
		tp1 = (Spinner) findViewById(R.id.tp1);
		tp2 = (Spinner) findViewById(R.id.tp2);
		tp3 = (Spinner) findViewById(R.id.tp3);
		lr1 = (Spinner) findViewById(R.id.lr1);
		lr2 = (Spinner) findViewById(R.id.lr2);
		lr3 = (Spinner) findViewById(R.id.lr3);
		lr4 = (Spinner) findViewById(R.id.lr4);
		lr5 = (Spinner) findViewById(R.id.lr5);
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
		parent.setText(JSONParser.getRelation());
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
		
		p.setText(JSONParser.getPhy());
		dg1.setText(JSONParser.getDiag1());
		dg2.setText(JSONParser.getDiag2());
		tt1.setText(JSONParser.getTrt1());
		tt2.setText(JSONParser.getTrt2());
		tt3.setText(JSONParser.getTrt3());
		lb1.setText(JSONParser.getLr1());
		lb2.setText(JSONParser.getLr2());
		lb3.setText(JSONParser.getLr3());
		imptv.setText(JSONParser.getImp());
		rectv.setText(JSONParser.getRec());
		aggtv.setText(JSONParser.getAgg());
		
		
		
		
		
		
		

		if (JSONParser.getUpdatedby().equals("")) {
			updatedby.setText("NOT yet Updated..");
		} else
			updatedby.setText((JSONParser.getUpdatedby()));
		if (JSONParser.getUpdatedon().equals("")) {
			updatedon.setText("NOT yet Updated..");
		} else
			updatedon.setText((JSONParser.getUpdatedon()));

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
			pDialog = new ProgressDialog(Showdetails.this);
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
				Toast.makeText(Showdetails.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		switch (parent.getId()) {
		case R.id.spe1:
			Physical1 = parent.getSelectedItem().toString();
			break;
		case R.id.spe2:
			Physical2 = parent.getSelectedItem().toString();
			break;
		case R.id.spe3:
			Physical3 = parent.getSelectedItem().toString();
			break;
		case R.id.d1:
			Diagnosis1 = parent.getSelectedItem().toString();
			break;
		case R.id.d2:
			Diagnosis2 = parent.getSelectedItem().toString();
			break;
		case R.id.d3:
			Diagnosis3 = parent.getSelectedItem().toString();
			break;
		case R.id.t1:
			Treatment1 = parent.getSelectedItem().toString();
			break;
		case R.id.t2:
			Treatment2 = parent.getSelectedItem().toString();
			break;
		case R.id.t3:
			Treatment3 = parent.getSelectedItem().toString();
			break;
		case R.id.td1:
			Td1 = parent.getSelectedItem().toString();
			break;
		case R.id.td2:
			Td2 = parent.getSelectedItem().toString();
			break;
		case R.id.td3:
			Td3 = parent.getSelectedItem().toString();
			break;
		case R.id.tv1:
			Tv1 = parent.getSelectedItem().toString();
			break;
		case R.id.tv2:
			Tv2 = parent.getSelectedItem().toString();
			break;
		case R.id.tv3:
			Tv3 = parent.getSelectedItem().toString();
			break;
		case R.id.tp1:
			Tp1 = parent.getSelectedItem().toString();
			break;
		case R.id.tp2:
			Tp2 = parent.getSelectedItem().toString();
			break;
		case R.id.tp3:
			Tp3 = parent.getSelectedItem().toString();
			break;
		case R.id.lr1:
			Lr1 = parent.getSelectedItem().toString();
			break;
		case R.id.lr2:
			Lr2 = parent.getSelectedItem().toString();
			break;
		case R.id.lr3:
			Lr3 = parent.getSelectedItem().toString();
			break;
		case R.id.lr4:
			Lr4 = parent.getSelectedItem().toString();
			break;
		case R.id.lr5:
			Lr5 = parent.getSelectedItem().toString();
			break;

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}