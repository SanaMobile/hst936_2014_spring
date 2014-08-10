package com.example.mexicoehr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class ShowdetailsA extends Activity implements OnClickListener,
		OnItemSelectedListener {
	Button edit;
	TextView curp, phone, email, zip, colony, parent, name, createdby,
			createdon, temperature, weight, height, waist, bp, symptoms, notes,
			updatedon, updatedby;
	TextView pppp;
	TextView gender, cancer, diabetes, hypertension, pregnancy;
	String Curp, Name, Phone, Email, Zip, Colony, Parent, Gender, Cancer,
			Diabetes, Hypertension, Uname, Date_created, Temp, Weight, Height,
			Waist, Bp, Pregnancy, Symptoms, Notes, Updatedon, Updatedby;

	Spinner td1, td2, td3, tv1, tv2, tv3, tp1, tp2, tp3;
	EditText ped1, ped2, ped3, dnotes;

	CheckBox imp, rec, agg;
	CheckBox undw, ovrwt, obs, club, edema;
	EditText ohterpy;
	String Under, Over, Obs, Club, Edema, Otherpy;
	CheckBox viral, infection, head, urine, metal;
	EditText ohterdiagnosis;
	String Viral, Infection, Head, Urine, Metal;
	CheckBox antib, analg, insulin;
	EditText ohtertreat;
	String Antib, Analg, Insulin;
	CheckBox bchem, bchem24, bbio, uri, serum;
	EditText ohterlab;
	String Bchem, Bchem24, Bbio, Uri, Serum;

	String Othertreat, Od, Otherlab;
	TabHost th;
	TabSpec specs;
	String Physical1, Physical2, Physical3, Diagnosis1, Diagnosis2, Diagnosis3,
			Dd1, Dd2, Dd3, Dnotes, Treatment1, Treatment2, Treatment3, Td1,
			Td2, Td3, Tv1, Tv2, Tv3, Tp1, Tp2, Tp3, Tdetails1, Tdetails2,
			Tdetails3, Lr1, Lr2, Lr3, Lr4, Lr5, Imp, Rec, Agg;
	TextView p1, p2, p3, p4, p5, p6, dg1, dg2, dg3, dg4, dg5, dg6, tt1, tt2,
			tt3, tt4, lb1, lb2, lb3, lb4, lb5, lb6, imptv, rectv, aggtv, pre1,
			pre2, pre3;
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/editdoctordetailsa.php";

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
		Curp = PatientA.getLicence();

	}

	private void update() {
		// TODO Auto-generated method stub
		pppp=(TextView) findViewById(R.id.pppp);
		pppp.setVisibility(View.GONE);
		undw = (CheckBox) findViewById(R.id.undw);
		ovrwt = (CheckBox) findViewById(R.id.ovrw);
		obs = (CheckBox) findViewById(R.id.obs);
		club = (CheckBox) findViewById(R.id.club);
		edema = (CheckBox) findViewById(R.id.edema);
		ohterpy = (EditText) findViewById(R.id.otherpy);

		viral = (CheckBox) findViewById(R.id.undw);
		infection = (CheckBox) findViewById(R.id.ovrw);
		head = (CheckBox) findViewById(R.id.obs);
		urine = (CheckBox) findViewById(R.id.club);
		metal = (CheckBox) findViewById(R.id.edema);
		ohterdiagnosis = (EditText) findViewById(R.id.otherpy);

		antib = (CheckBox) findViewById(R.id.antib);
		analg = (CheckBox) findViewById(R.id.analg);
		insulin = (CheckBox) findViewById(R.id.insulin);

		ohtertreat = (EditText) findViewById(R.id.othertreat);

		bchem = (CheckBox) findViewById(R.id.bchem);
		bchem24 = (CheckBox) findViewById(R.id.bchem24);
		bbio = (CheckBox) findViewById(R.id.bbio);
		uri = (CheckBox) findViewById(R.id.uri);
		serum = (CheckBox) findViewById(R.id.serum);
		ohterlab = (EditText) findViewById(R.id.otherlab);

		p1 = (TextView) findViewById(R.id.phy1);
		p2 = (TextView) findViewById(R.id.phy2);
		p3 = (TextView) findViewById(R.id.phy3);
		p4 = (TextView) findViewById(R.id.phy4);
		p5 = (TextView) findViewById(R.id.phy5);
		p6 = (TextView) findViewById(R.id.phy6);
		dg1 = (TextView) findViewById(R.id.diag1tv);
		dg2 = (TextView) findViewById(R.id.diag2tv);
		dg3 = (TextView) findViewById(R.id.diag3tv);
		dg4 = (TextView) findViewById(R.id.diag4tv);
		dg5 = (TextView) findViewById(R.id.diag5tv);
		dg6 = (TextView) findViewById(R.id.diag6tv);
		tt1 = (TextView) findViewById(R.id.pres1tv);
		tt2 = (TextView) findViewById(R.id.pres2tv);
		tt3 = (TextView) findViewById(R.id.pres3tv);
		tt4 = (TextView) findViewById(R.id.pres4tv);
		lb1 = (TextView) findViewById(R.id.lab11tv);
		lb2 = (TextView) findViewById(R.id.lab21tv);
		lb3 = (TextView) findViewById(R.id.lab31tv);
		lb4 = (TextView) findViewById(R.id.lab41tv);
		lb5 = (TextView) findViewById(R.id.lab51tv);
		

		imptv = (TextView) findViewById(R.id.imptv);
		rectv = (TextView) findViewById(R.id.rectv);
		aggtv = (TextView) findViewById(R.id.aggtv);

		imp = (CheckBox) findViewById(R.id.imp);
		rec = (CheckBox) findViewById(R.id.recov);
		agg = (CheckBox) findViewById(R.id.agg);

		dnotes = (EditText) findViewById(R.id.dnotes);

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
		parent.setVisibility(View.GONE);
		gender = (TextView) findViewById(R.id.etvgender);
		cancer = (TextView) findViewById(R.id.etvcancer);
		diabetes = (TextView) findViewById(R.id.etvdiabetes);
		hypertension = (TextView) findViewById(R.id.etvhypertension);

		td1 = (Spinner) findViewById(R.id.td1);
		td2 = (Spinner) findViewById(R.id.td2);
		td3 = (Spinner) findViewById(R.id.td3);
		tv1 = (Spinner) findViewById(R.id.tv1);
		tv2 = (Spinner) findViewById(R.id.tv2);
		tv3 = (Spinner) findViewById(R.id.tv3);
		tp1 = (Spinner) findViewById(R.id.tp1);
		tp2 = (Spinner) findViewById(R.id.tp2);
		tp3 = (Spinner) findViewById(R.id.tp3);

		td1.setOnItemSelectedListener(this);
		td2.setOnItemSelectedListener(this);
		td3.setOnItemSelectedListener(this);
		tv1.setOnItemSelectedListener(this);
		tv2.setOnItemSelectedListener(this);
		tv3.setOnItemSelectedListener(this);
		tp1.setOnItemSelectedListener(this);
		tp2.setOnItemSelectedListener(this);
		tp3.setOnItemSelectedListener(this);

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
		update1();

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

	private void update1() {
		// TODO Auto-generated method stub
		if (JSONParser.getPhy().equalsIgnoreCase("Yes")) {
			p1.setVisibility(View.VISIBLE);
			p1.setText("Under Weight");
		}
		if (JSONParser.getPhy2().equalsIgnoreCase("Yes")) {
			p2.setVisibility(View.VISIBLE);
			p2.setText("Over Weight");
		}
		if (JSONParser.getPhy3().equalsIgnoreCase("Yes")) {
			p3.setVisibility(View.VISIBLE);
			p3.setText("Obesity");
		}
		if (JSONParser.getPhy4().equalsIgnoreCase("Yes")) {
			p4.setVisibility(View.VISIBLE);
			p4.setText("Absent Clubbing");
		}
		if (JSONParser.getPhy5().equalsIgnoreCase("Yes")) {
			p5.setVisibility(View.VISIBLE);
			p5.setText("Edema");
		}

		p6.setVisibility(View.VISIBLE);
		p6.setText(JSONParser.getPhy6());
		System.out.println("HARAMI");

		if (JSONParser.getDiag1().equalsIgnoreCase("Yes")) {
			dg1.setVisibility(View.VISIBLE);
			dg1.setText("Viral Infection");
		}
		if (JSONParser.getDiag2().equalsIgnoreCase("Yes")) {
			dg2.setVisibility(View.VISIBLE);
			dg2.setText("Infection");
		}
		if (JSONParser.getDiag3().equalsIgnoreCase("Yes")) {
			dg3.setVisibility(View.VISIBLE);
			dg3.setText("Headache");
		}
		if (JSONParser.getDiag4().equalsIgnoreCase("Yes")) {
			dg4.setVisibility(View.VISIBLE);
			dg4.setText("Urinary tract infection");
		}
		if (JSONParser.getDiag5().equalsIgnoreCase("Yes")) {
			dg5.setVisibility(View.VISIBLE);
			dg5.setText("Metabolic Syndrome");
		}

		dg6.setVisibility(View.VISIBLE);
		dg6.setText(JSONParser.getDiag6());

		if (JSONParser.getTrt1().equalsIgnoreCase("Yes")) {
			tt1.setVisibility(View.VISIBLE);
			tt1.setText("Antibiotic");
		}
		if (JSONParser.getTrt2().equalsIgnoreCase("Yes")) {
			tt2.setVisibility(View.VISIBLE);
			tt2.setText("Analgesic");
		}
		if (JSONParser.getTrt3().equalsIgnoreCase("Yes")) {
			tt3.setVisibility(View.VISIBLE);
			tt3.setText("Insulin");
		}

		tt4.setVisibility(View.VISIBLE);
		tt4.setText(JSONParser.getTrt4());

		if (JSONParser.getLr1().equalsIgnoreCase("Yes")) {
			lb1.setVisibility(View.VISIBLE);
			lb1.setText("Blood chemistry 35 elements");
		}
		if (JSONParser.getLr2().equalsIgnoreCase("Yes")) {
			lb2.setVisibility(View.VISIBLE);
			lb2.setText("Blood chemistry 24 elements");
		}
		if (JSONParser.getLr3().equalsIgnoreCase("Yes")) {
			lb3.setVisibility(View.VISIBLE);
			lb3.setText("Blood biometry");
		}
		if (JSONParser.getLr4().equalsIgnoreCase("Yes")) {
			lb4.setVisibility(View.VISIBLE);
			lb4.setText("Urinalysis");
		}
		if (JSONParser.getLr5().equalsIgnoreCase("Yes")) {
			lb5.setVisibility(View.VISIBLE);
			lb5.setText("Serum electrolytes");
		}

	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub

		if (undw.isChecked())
			Under = "Yes";
		else
			Under = "No";
		if (ovrwt.isChecked())
			Over = "Yes";
		else
			Over = "No";
		if (obs.isChecked())
			Obs = "Yes";
		else
			Obs = "No";
		if (club.isChecked())
			Club = "Yes";
		else
			Club = "No";
		if (edema.isChecked())
			Edema = "Yes";
		else
			Edema = "No";

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

		if (viral.isChecked())
			Viral = "Yes";
		else
			Viral = "No";
		if (infection.isChecked())
			Infection = "Yes";
		else
			Infection = "No";
		if (head.isChecked())
			Head = "Yes";
		else
			Head = "No";
		if (urine.isChecked())
			Urine = "Yes";
		else
			Urine = "No";
		if (metal.isChecked())
			Metal = "Yes";
		else
			Metal = "No";

		if (antib.isChecked())
			Antib = "Yes";
		else
			Antib = "No";
		if (analg.isChecked())
			Analg = "Yes";
		else
			Analg = "No";
		if (insulin.isChecked())
			Insulin = "Yes";
		else
			Insulin = "No";

		if (bchem.isChecked())
			Bchem = "Yes";
		else
			Bchem = "No";
		if (bchem24.isChecked())
			Bchem24 = "Yes";
		else
			Bchem24 = "No";
		if (bbio.isChecked())
			Bbio = "Yes";
		else
			Bbio = "No";
		if (uri.isChecked())
			Uri = "Yes";
		else
			Uri = "No";
		if (serum.isChecked())
			Serum = "Yes";
		else
			Serum = "No";

		Otherlab = ohterlab.getText().toString();
		Othertreat = ohtertreat.getText().toString();

		Otherpy = ohterpy.getText().toString();
		Od = ohterdiagnosis.getText().toString();

		Dnotes = dnotes.getText().toString();

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
				params.add(new BasicNameValuePair("Physical1", Under));
				params.add(new BasicNameValuePair("Physical2", Over));
				params.add(new BasicNameValuePair("Physical3", Obs));
				params.add(new BasicNameValuePair("Physical4", Club));
				params.add(new BasicNameValuePair("Physical5", Edema));
				params.add(new BasicNameValuePair("Physical6", Otherpy));
				params.add(new BasicNameValuePair("Diagnosis1", Viral));
				params.add(new BasicNameValuePair("Diagnosis2", Infection));
				params.add(new BasicNameValuePair("Diagnosis3", Head));
				params.add(new BasicNameValuePair("Diagnosis4", Urine));
				params.add(new BasicNameValuePair("Diagnosis5", Metal));
				params.add(new BasicNameValuePair("Diagnosis6", Od));

				params.add(new BasicNameValuePair("Dnotes", Dnotes));
				params.add(new BasicNameValuePair("Treatment1", Antib));
				params.add(new BasicNameValuePair("Treatment2", Analg));
				params.add(new BasicNameValuePair("Treatment3", Insulin));
				params.add(new BasicNameValuePair("Treatment4", Othertreat));
				params.add(new BasicNameValuePair("Td1", Td1));
				params.add(new BasicNameValuePair("Td2", Td2));
				params.add(new BasicNameValuePair("Td3", Td3));
				params.add(new BasicNameValuePair("Tv1", Tv1));
				params.add(new BasicNameValuePair("Tv2", Tv2));
				params.add(new BasicNameValuePair("Tv3", Tv3));
				params.add(new BasicNameValuePair("Tp1", Tp1));
				params.add(new BasicNameValuePair("Tp2", Tp2));
				params.add(new BasicNameValuePair("Tp3", Tp3));
				params.add(new BasicNameValuePair("Lr1", Bchem));
				params.add(new BasicNameValuePair("Lr2", Bchem24));
				params.add(new BasicNameValuePair("Lr3", Bbio));
				params.add(new BasicNameValuePair("Lr4", Uri));
				params.add(new BasicNameValuePair("Lr5", Serum));
				params.add(new BasicNameValuePair("Lr6", Otherlab));

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

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}