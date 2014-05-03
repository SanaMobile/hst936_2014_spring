package com.example.mexicoehr;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientA extends Activity implements OnClickListener{
EditText licence;
Button licencesubmit;
static String Licence="";
private ProgressDialog pDialog;

JSONParser jsonParser = new JSONParser();
private static final String LOGIN_URL = "http://www.techcrunch.net.in/ehr/doctorshowA.php";

private static final String TAG_SUCCESS = "success";
private static final String TAG_MESSAGE = "message";
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		licence=(EditText)findViewById(R.id.elicence);
		licencesubmit=(Button)findViewById(R.id.licencesubmit);
		licencesubmit.setOnClickListener(this);
	}
	public static String getLicence() {
		return Licence;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Licence=licence.getText().toString();
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
			pDialog = new ProgressDialog(PatientA.this);
			pDialog.setMessage("Attempting login...");
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
				params.add(new BasicNameValuePair("Curp", Licence));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// check your log for json response
				Log.d("Edit attempt", json.toString());

				// json success tag
				
				if (Licence.equals(JSONParser.getCurp())) {
					Log.d("Edit Successful!", json.toString());

					Intent i = new Intent(getApplicationContext(), ShowdetailsA.class);

					finish();
					startActivity(i);

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("No such EMR Exists!", json.getString(TAG_MESSAGE));
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
				Toast.makeText(PatientA.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}

}
