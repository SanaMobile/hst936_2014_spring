package com.example.mexicoehr;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mexicoehr.Nurse.AttemptLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class Create extends Activity implements OnClickListener,
		OnCheckedChangeListener,
		android.widget.CompoundButton.OnCheckedChangeListener {
	Button submit;
	EditText curp, phone, email, zip, colony, parent, name;
	EditText a, b, c, d;
	EditText auto;
	RadioGroup gender, cancer, diabetes, hypertension, immune;
	public static String Curp, Name, Phone, Email, Zip, Colony, Parent, Gender,
			Cancer, Pass, Diabetes, Hypertension, Uname, Date_created, Cname,
			Cno, Nno, Caddress,Auto;
	CheckBox dust, med, metal, animal, food;
	public static String du, me, mt, an, fo, Imm, Other, Height, Weight, Waist,
			Bp, Temp, Pre;
	private ProgressDialog pDialog;
	EditText other, height, weight, waist, bp, temp, pre;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/child.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new1);
		Uname = Nurse.getName();
		Pass = Nurse.getPwd();

		update();
	}

	private void update() {
		// TODO Auto-generated method stub
		// a = (EditText) findViewById(R.id.lno);

		other = (EditText) findViewById(R.id.otheralergies1);
		height = (EditText) findViewById(R.id.heightv1);
		weight = (EditText) findViewById(R.id.weightv1);
		waist = (EditText) findViewById(R.id.Waist_circumferencev1);
		bp = (EditText) findViewById(R.id.blodd_pressurev1);
		temp = (EditText) findViewById(R.id.tempreturev1);
auto=(EditText)findViewById(R.id.autocomplete_country);
		b = (EditText) findViewById(R.id.cname);
		c = (EditText) findViewById(R.id.caddk);
		// d= (EditText) findViewById(R.id.nnum);
		dust = (CheckBox) findViewById(R.id.dust1);
		med = (CheckBox) findViewById(R.id.medicine1);
		animal = (CheckBox) findViewById(R.id.animals1);
		food = (CheckBox) findViewById(R.id.food1);
		metal = (CheckBox) findViewById(R.id.metals1);


		

		submit = (Button) findViewById(R.id.submit);
		name = (EditText) findViewById(R.id.name);
		curp = (EditText) findViewById(R.id.curp);
		phone = (EditText) findViewById(R.id.phone);
		email = (EditText) findViewById(R.id.email);
		zip = (EditText) findViewById(R.id.zip);
		colony = (EditText) findViewById(R.id.colony);
		parent = (EditText) findViewById(R.id.parenthood);
		gender = (RadioGroup) findViewById(R.id.gender);
		cancer = (RadioGroup) findViewById(R.id.cancer);
		diabetes = (RadioGroup) findViewById(R.id.diabetes);
		hypertension = (RadioGroup) findViewById(R.id.hypertension);
		immune = (RadioGroup) findViewById(R.id.immunization1);
		immune.setOnCheckedChangeListener(this);

		submit.setOnClickListener(this);
		gender.setOnCheckedChangeListener(this);
		cancer.setOnCheckedChangeListener(this);
		diabetes.setOnCheckedChangeListener(this);
		hypertension.setOnCheckedChangeListener(this);

		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		// Get the string array
		String[] countries = getResources().getStringArray(
				R.array.pre_diagnosis);
		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, countries);
		textView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (dust.isChecked())

			du = "yes";
		else
			du = "no";

		if (med.isChecked())

			me = "yes";
		else
			me = "no";
		if (metal.isChecked())

			mt = "yes";
		else
			mt = "no";
		if (animal.isChecked())

			an = "yes";
		else
			an = "no";
		if (food.isChecked())

			fo = "yes";
		else
			fo = "no";
		Curp = curp.getText().toString();
		Phone = phone.getText().toString();
		Email = email.getText().toString();
		Zip = zip.getText().toString();
		Colony = colony.getText().toString();
		Parent = parent.getText().toString();
		
		Name = name.getText().toString();
		Cname = b.getText().toString();
		Caddress=c.getText().toString();
		Auto=auto.getText().toString();
		Date date = new Date();
		
		Date_created = date.toString();
		Other = other.getText().toString();
		Height = height.getText().toString();
		Weight = weight.getText().toString();
		Waist = waist.getText().toString();
		Bp = bp.getText().toString();
		Temp = temp.getText().toString();
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
			pDialog = new ProgressDialog(Create.this);
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
				System.out.println(du);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Curp", Curp));
				params.add(new BasicNameValuePair("Name", Name));
				params.add(new BasicNameValuePair("Phone", Phone));
				params.add(new BasicNameValuePair("Email", Email));
				params.add(new BasicNameValuePair("Relation", Parent));
				params.add(new BasicNameValuePair("Zip", Zip));
				params.add(new BasicNameValuePair("Colony", Colony));
				params.add(new BasicNameValuePair("Gender", Gender));
				params.add(new BasicNameValuePair("Cancer", Cancer));
				params.add(new BasicNameValuePair("Diabetes", Diabetes));
				params.add(new BasicNameValuePair("Hypertension", Hypertension));
				params.add(new BasicNameValuePair("Created_by", Uname));
				params.add(new BasicNameValuePair("Created_on", Date_created));
				
				params.add(new BasicNameValuePair("Cname", Cname));
		
				
				
				params.add(new BasicNameValuePair("Dust", du));
				params.add(new BasicNameValuePair("Med", me));
				params.add(new BasicNameValuePair("Metal", mt));
				params.add(new BasicNameValuePair("Animal", an));
				params.add(new BasicNameValuePair("Food", fo));
				params.add(new BasicNameValuePair("Imm", Imm));
				params.add(new BasicNameValuePair("Other", Other));
				params.add(new BasicNameValuePair("Height", Height));
				params.add(new BasicNameValuePair("Weight", Weight));
				params.add(new BasicNameValuePair("Waist", Waist));
				params.add(new BasicNameValuePair("Bp", Bp));

				params.add(new BasicNameValuePair("Temp", Temp));

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

					Intent i = new Intent(getApplicationContext(), Next.class);

					finish();
					startActivity(i);

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
				Toast.makeText(Create.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (group.getId()) {
		case R.id.gender:
			int id = gender.getCheckedRadioButtonId();
			if (id == R.id.male)
				Gender = "Male";
			else
				Gender = "Femaale";

			break;

		case R.id.cancer:
			int id1 = cancer.getCheckedRadioButtonId();
			if (id1 == R.id.cyes)
				Cancer = "Yes";
			else
				Cancer = "No";
			break;

		case R.id.diabetes:
			int id2 = diabetes.getCheckedRadioButtonId();
			if (id2 == R.id.dyes)
				Diabetes = "Yes";
			else
				Diabetes = "No";
			break;

		case R.id.hypertension:
			int id3 = hypertension.getCheckedRadioButtonId();
			if (id3 == R.id.hyes)
				Hypertension = "Yes";
			else
				Hypertension = "No";
			break;
		case R.id.immunization1:
			int im = immune.getCheckedRadioButtonId();
			if (im == R.id.immune_yes1)
				Imm = "ON TIME";
			else
				Imm = "LATE";

		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

	}

}
