/**
 * 
 */
package com.edhanvantari.util;

import org.json.JSONException;

/**
 * @author roshan
 *
 *         This class is to validate the string as a JSON object or array
 */
public class JSONValidator {

	/**
	 * 
	 * @param json
	 * @return
	 */
	public boolean isValid(String json) {
		try {
			if(json == null) {
				return false;
			}else {
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
