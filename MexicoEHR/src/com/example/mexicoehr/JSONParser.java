package com.example.mexicoehr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static String lastLine = "", Curp, Relation, Phone, Zip, Colony, Gender,
			Cancer, Diabetes, Hypertension, Created_by, Created_on, Email,
			Name, Updatedon, Updatedby, Weight, Height, Waist, Temp, Pregnancy,
			Notes, Symptoms, Bp, Lr1, Lr2, Lr3,Lr4,Lr5,Lr6, L1, L2, L3, L4, L5,Imn;
	
	public static String getImn() {
		return Imn;
	}

	public static void setImn(String imn) {
		Imn = imn;
	}

	static String trt1, trt2, trt3, trt4, trt5, phy,phy2,phy3,phy4,phy5,phy6, tt1, tt2, tt3, diag1,
			diag2, diag3,diag4,diag5,diag6,rec, imp, agg;

	public static String getPhy2() {
		return phy2;
	}

	public static void setPhy2(String phy2) {
		JSONParser.phy2 = phy2;
	}

	public static String getPhy3() {
		return phy3;
	}

	public static void setPhy3(String phy3) {
		JSONParser.phy3 = phy3;
	}

	public static String getPhy4() {
		return phy4;
	}

	public static void setPhy4(String phy4) {
		JSONParser.phy4 = phy4;
	}

	public static String getPhy5() {
		return phy5;
	}

	public static void setPhy5(String phy5) {
		JSONParser.phy5 = phy5;
	}

	public static String getPhy6() {
		return phy6;
	}

	public static void setPhy6(String phy6) {
		JSONParser.phy6 = phy6;
	}

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {

		// Making HTTP request
		try {

			// check for request method
			if (method == "POST") {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} else if (method == "GET") {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			  while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            
	            // Close the input stream.
	            is.close();
	            // Convert the string builder data to an actual string.
	            json = sb.toString();
	            System.out.println(json);
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);

			Curp = jObj.getString("curp");
			Relation = jObj.getString("relation");
			Name = jObj.getString("name");
			Email = jObj.getString("email");
			Phone = jObj.getString("phone");
			Zip = jObj.getString("zip");
			Colony = jObj.getString("colony");
			Created_by = jObj.getString("created_by");
			Created_on = jObj.getString("created_on");
			Gender = jObj.getString("gender");
			Cancer = jObj.getString("cancer");
			Diabetes = jObj.getString("diabetes");
			Hypertension = jObj.getString("hypertension");
			Updatedon = jObj.getString("updated_on");
			Updatedby = jObj.getString("updated_by");
			Weight = jObj.getString("weight");
			Height = jObj.getString("height");
			Temp = jObj.getString("temperature");
			Waist = jObj.getString("waist_circumference");
			Bp = jObj.getString("blood_pressure");
			Imn =jObj.getString("immunization");
			Symptoms = jObj.getString("symptoms");
			Notes = jObj.getString("notes");
			Lr1 = jObj.getString("labrequest1");
			Lr2 = jObj.getString("labrequest2");
			Lr3 = jObj.getString("labrequest3");
			Lr4 = jObj.getString("labrequest4");
			Lr5 = jObj.getString("labrequest5");
			
			L1 = jObj.getString("labresult1");
			L2 = jObj.getString("labresult2");
			L3 = jObj.getString("labresult3");
			L4 = jObj.getString("labresult4");
			L5 = jObj.getString("labresult5");
			trt1 = jObj.getString("treatement1");
			trt2 = jObj.getString("treatement2");
			trt3 = jObj.getString("treatement3");
			trt4 = jObj.getString("treatement4");

			phy = jObj.getString("pysical_exploration1");
			phy2 = jObj.getString("pysical_exploration2");
			phy3= jObj.getString("pysical_exploration3");
			phy4 = jObj.getString("pysical_exploration4");
			phy5= jObj.getString("pysical_exploration5");
			phy6= jObj.getString("pysical_exploration6");
			diag1 = jObj.getString("diagnosis1");
			diag2 = jObj.getString("diagnosis2");
			diag3 = jObj.getString("diagnosis3");
			diag4 = jObj.getString("diagnosis4");
			diag5 = jObj.getString("diagnosis5");
			diag6 = jObj.getString("diagnosis6");
			rec = jObj.getString("recovery");
			agg = jObj.getString("aggravate");
			imp = jObj.getString("improvement");

		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	public static String getLr6() {
		return Lr6;
	}

	public static void setLr6(String lr6) {
		Lr6 = lr6;
	}

	public static String getTrt4() {
		return trt4;
	}

	public static void setTrt4(String trt4) {
		JSONParser.trt4 = trt4;
	}

	public static String getDiag3() {
		return diag3;
	}

	public static void setDiag3(String diag3) {
		JSONParser.diag3 = diag3;
	}

	public static String getDiag4() {
		return diag4;
	}

	public static void setDiag4(String diag4) {
		JSONParser.diag4 = diag4;
	}

	public static String getDiag5() {
		return diag5;
	}

	public static void setDiag5(String diag5) {
		JSONParser.diag5 = diag5;
	}

	public static String getDiag6() {
		return diag6;
	}

	public static void setDiag6(String diag6) {
		JSONParser.diag6 = diag6;
	}

	public static String getAgg() {
		return agg;
	}

	public static void setAgg(String agg) {
		JSONParser.agg = agg;
	}

	public static String getPhy() {
		return phy;
	}

	public static void setPhy(String phy) {
		JSONParser.phy = phy;
	}

	public static String getTt1() {
		return tt1;
	}

	public static void setTt1(String tt1) {
		JSONParser.tt1 = tt1;
	}

	public static String getTt2() {
		return tt2;
	}

	public static void setTt2(String tt2) {
		JSONParser.tt2 = tt2;
	}

	public static String getTt3() {
		return tt3;
	}

	public static void setTt3(String tt3) {
		JSONParser.tt3 = tt3;
	}

	public static String getDiag1() {
		return diag1;
	}

	public static void setDiag1(String diag1) {
		JSONParser.diag1 = diag1;
	}

	public static String getDiag2() {
		return diag2;
	}

	public static void setDiag2(String diag2) {
		JSONParser.diag2 = diag2;
	}

	public static String getRec() {
		return rec;
	}

	public static void setRec(String rec) {
		JSONParser.rec = rec;
	}

	public static String getImp() {
		return imp;
	}

	public static void setImp(String imp) {
		JSONParser.imp = imp;
	}

	public static String getTrt1() {
		return trt1;
	}

	public static void setTrt1(String trt1) {
		JSONParser.trt1 = trt1;
	}

	public static String getTrt2() {
		return trt2;
	}

	public static void setTrt2(String trt2) {
		JSONParser.trt2 = trt2;
	}

	public static String getTrt3() {
		return trt3;
	}

	public static void setTrt3(String trt3) {
		JSONParser.trt3 = trt3;
	}

	public static String getL1() {
		return L1;
	}

	public static void setL1(String l1) {
		L1 = l1;
	}

	public static String getL2() {
		return L2;
	}

	public static void setL2(String l2) {
		L2 = l2;
	}

	public static String getL3() {
		return L3;
	}

	public static void setL3(String l3) {
		L3 = l3;
	}

	public static String getL4() {
		return L4;
	}

	public static void setL4(String l4) {
		L4 = l4;
	}

	public static String getL5() {
		return L5;
	}

	public static void setL5(String l5) {
		L5 = l5;
	}

	public static String getLr1() {
		return Lr1;
	}

	public static void setLr1(String lr1) {
		Lr1 = lr1;
	}

	public static String getLr2() {
		return Lr2;
	}

	public static void setLr2(String lr2) {
		Lr2 = lr2;
	}

	public static String getLr3() {
		return Lr3;
	}

	public static void setLr3(String lr3) {
		Lr3 = lr3;
	}

	public static String getLr4() {
		return Lr4;
	}

	public static void setLr4(String lr4) {
		Lr4 = lr4;
	}

	public static String getLr5() {
		return Lr5;
	}

	public static void setLr5(String lr5) {
		Lr5 = lr5;
	}

	public static String getBp() {
		return Bp;
	}

	public static void setBp(String bp) {
		Bp = bp;
	}

	public static String getWeight() {
		return Weight;
	}

	public static void setWeight(String weight) {
		Weight = weight;
	}

	public static String getHeight() {
		return Height;
	}

	public static void setHeight(String height) {
		Height = height;
	}

	public static String getWaist() {
		return Waist;
	}

	public static void setWaist(String waist) {
		Waist = waist;
	}

	public static String getTemp() {
		return Temp;
	}

	public static void setTemp(String temp) {
		Temp = temp;
	}

	public static String getPregnancy() {
		return Pregnancy;
	}

	public static void setPregnancy(String pregnancy) {
		Pregnancy = pregnancy;
	}

	public static String getNotes() {
		return Notes;
	}

	public static void setNotes(String notes) {
		Notes = notes;
	}

	public static String getSymptoms() {
		return Symptoms;
	}

	public static void setSymptoms(String symptoms) {
		Symptoms = symptoms;
	}
	
	public static String getUpdatedon() {
		return Updatedon;
	}

	public static void setUpdatedon(String updatedon) {
		Updatedon = updatedon;
	}

	public static String getUpdatedby() {
		return Updatedby;
	}

	public static void setUpdatedby(String updatedby) {
		Updatedby = updatedby;
	}

	public static String getEmail() {
		return Email;
	}

	public static void setEmail(String email) {
		Email = email;
	}

	public static String getName() {
		return Name;
	}

	public static void setName(String name) {
		Name = name;
	}

	public static String getCurp() {
		return Curp;
	}

	public static String getRelation() {
		return Relation;
	}

	public static void setRelation(String relation) {
		Relation = relation;
	}

	public static String getPhone() {
		return Phone;
	}

	public static void setPhone(String phone) {
		Phone = phone;
	}

	public static String getZip() {
		return Zip;
	}

	public static void setZip(String zip) {
		Zip = zip;
	}

	public static String getColony() {
		return Colony;
	}

	public static void setColony(String colony) {
		Colony = colony;
	}

	public static String getGender() {
		return Gender;
	}

	public static void setGender(String gender) {
		Gender = gender;
	}

	public static String getCancer() {
		return Cancer;
	}

	public static void setCancer(String cancer) {
		Cancer = cancer;
	}

	public static String getDiabetes() {
		return Diabetes;
	}

	public static void setDiabetes(String diabetes) {
		Diabetes = diabetes;
	}

	public static String getHypertension() {
		return Hypertension;
	}

	public static void setHypertension(String hypertension) {
		Hypertension = hypertension;
	}

	public static String getCreated_by() {
		return Created_by;
	}

	public static void setCreated_by(String created_by) {
		Created_by = created_by;
	}

	public static String getCreated_on() {
		return Created_on;
	}

	public static void setCreated_on(String created_on) {
		Created_on = created_on;
	}
}
