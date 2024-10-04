package com.edhanvantari.action;

import java.io.FileReader;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.edhanvantari.form.PatientForm;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			/*
			 * JSONParser parser = new JSONParser();
			 * 
			 * JSONArray a = (JSONArray) parser.parse(new
			 * FileReader("D:\\ortho_charges.json"));
			 * 
			 * for (Object o : a) { JSONObject person = (JSONObject) o;
			 * 
			 * System.out.println("diagnosis::"+person.get("diagnosis")+"...rate..."+person.
			 * get("rate")); }
			 */
			/*
			 * String diagnosisJSONString =
			 * "[{\"diagnosis\":\"Lungs Cancer\",\"rate\":\"100\"},{\"diagnosis\":\"Asthma\",\"rate\":\"250\"},{\"diagnosis\":\"Allergy\",\"rate\":\"200\"}]"
			 * ;
			 * 
			 * if(isValid(null)) { System.out.println("Valid json"); }else {
			 * System.out.println("Invalid json"); }
			 */

			String data = "{\"contactid\":null,\"email\":null,\"firstname\":null,\"lastname\":null,\"birthday\":null,\"mobile\":null,\"mailingstreet\":null,\"mailingcity\":null,\"mailingstate\":null,\"mailingzip\":null,\"mailingcountry\":null,\"cf_938\":null,\"cf_922\":null,\"cf_928\":null,\"cf_936\":null,\"cf_934\":null,\"cf_924\":null,\"cf_930\":null}";

			org.json.JSONArray diagnosisJSONArray = new org.json.JSONArray("["+data+"]");

			for (int i = 0; i < diagnosisJSONArray.length(); i++) {
				org.json.JSONObject diagnosisJSONObject = diagnosisJSONArray.getJSONObject(i);

				System.out.println(diagnosisJSONObject.getString("contactid"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isValid(String json) {
		try {
			if (json == null) {
				return false;
			} else {
				new org.json.JSONObject(json);
			}
		} catch (JSONException e) {
			try {
				new org.json.JSONArray(json);
			} catch (JSONException ne) {
				return false;
			}
		}
		return true;
	}

}
