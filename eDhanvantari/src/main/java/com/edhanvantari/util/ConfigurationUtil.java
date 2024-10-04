package com.edhanvantari.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.edhanvantari.form.ConfigurationForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.itextpdf.styledxmlparser.util.WhiteSpaceUtil;

public class ConfigurationUtil extends DAOConnection {

	Connection connection;

	PreparedStatement preparedStatement;
	ResultSet resultSet;

	PreparedStatement preparedStatement1;
	ResultSet resultSet1;

	PreparedStatement preparedStatement2;
	ResultSet resultSet2;
	HttpServletRequest request = ServletActionContext.getRequest();

	HttpSession session = request.getSession();

	LoginForm form = (LoginForm) session.getAttribute("USER");
	int practiceID = form.getPracticeID();
	int clinicID = form.getClinicID();

	/**
	 * 
	 * @return
	 */
	public String getPracticeSuffix() {

		String practiceSuffix = null;

		try {
			connection = getConnection();

			String getSessionTimeOutQuery = QueryMaker.RETRIEVE_PRACTICE_SUFFIX;

			preparedStatement = connection.prepareStatement(getSessionTimeOutQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				practiceSuffix = resultSet.getString("suffix");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return practiceSuffix;
	}

	/**
	 * 
	 * @return
	 */
	public String getSessionTimeOut() {

		String sessionTimeout = null;

		try {
			connection = getConnection();

			String getSessionTimeOutQuery = QueryMaker.RETRIEVE_SESSION_TIMEOUT;

			preparedStatement = connection.prepareStatement(getSessionTimeOutQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				sessionTimeout = resultSet.getString("sessionTimeOut");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return sessionTimeout;
	}

	/**
	 * 
	 * @return
	 */
	public String getInvalidAttempts() {

		String invalidAttempts = null;

		try {
			connection = getConnection();

			String getInvalidAttemptsQuery = QueryMaker.RETRIEVE_INVALID_ATTEMPTS;

			preparedStatement = connection.prepareStatement(getInvalidAttemptsQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				invalidAttempts = resultSet.getString("invalidAttempts");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return invalidAttempts;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmailTo() {

		String emailTo = null;

		try {
			connection = getConnection();

			String getEmailToQuery = QueryMaker.RETRIEVE_EMAIL_TO;

			preparedStatement = connection.prepareStatement(getEmailToQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emailTo = resultSet.getString("emailTo");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailTo;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmailFrom() {

		String emailFrom = null;

		try {
			connection = getConnection();

			String getEmailFromQuery = QueryMaker.RETRIEVE_EMAIL_FROM;

			preparedStatement = connection.prepareStatement(getEmailFromQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emailFrom = resultSet.getString("emailFrom");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailFrom;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmailFromPass() {

		String emailFromPass = null;

		try {
			connection = getConnection();

			String getEmailFromPassQuery = QueryMaker.RETRIEVE_EMAIL_FROM_PASS;

			preparedStatement = connection.prepareStatement(getEmailFromPassQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emailFromPass = resultSet.getString("emailFromPass");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailFromPass;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrency() {

		String currency = null;

		try {
			connection = getConnection();

			String getCurrencyQuery = QueryMaker.RETRIEVE_CURRENCY;

			preparedStatement = connection.prepareStatement(getCurrencyQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				currency = resultSet.getString("currency1");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return currency;

	}

	/**
	 * 
	 * @return
	 */
	public String getCurrency1() {

		String currency1 = null;

		try {
			connection = getConnection();

			String getCurrency1Query = QueryMaker.RETRIEVE_CURRENCY1;

			preparedStatement = connection.prepareStatement(getCurrency1Query);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				currency1 = resultSet.getString("currency2");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return currency1;

	}

	/**
	 * 
	 * @return
	 */
	public String getClinicSuffix() {

		String clinicSuffix = null;

		try {
			connection = getConnection();

			String getClinicSuffixQuery = QueryMaker.RETRIEVE_CLINIC_SUFFIX;

			preparedStatement = connection.prepareStatement(getClinicSuffixQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicSuffix = resultSet.getString("suffix");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return clinicSuffix;

	}

	/**
	 * 
	 * @return
	 */
	public String getLetterHeadImage() {

		String lettrHeadImage = null;

		try {
			connection = getConnection();

			String getLetterHeadImageQuery = QueryMaker.RETRIEVE_LETTER_HEAD_IMAGE;

			preparedStatement = connection.prepareStatement(getLetterHeadImageQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				lettrHeadImage = resultSet.getString("letterHeadImage");
				System.out.println("LETTER HEAD IMG ---- IN METHOD" + lettrHeadImage);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return lettrHeadImage;

	}

	/**
	 * 
	 * @return
	 */
	public String getPageSize() {

		String pageSize = null;

		try {
			connection = getConnection();

			String getPageSizeQuery = QueryMaker.RETRIEVE_PAGE_SIZE;

			preparedStatement = connection.prepareStatement(getPageSizeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				pageSize = resultSet.getString("pageSize");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return pageSize;

	}

	/**
	 * 
	 * @return
	 */
	public String getConsentDocumentPath() {

		String consentDocumentPath = null;

		try {
			connection = getConnection();

			String getConsentDocumentPathQuery = QueryMaker.RETRIEVE_CONSENT_DOCUMENT_PATH;

			preparedStatement = connection.prepareStatement(getConsentDocumentPathQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				consentDocumentPath = resultSet.getString("consentDocumentPath");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return consentDocumentPath;

	}

	/**
	 * 
	 * @return
	 */
	public String getLogo() {

		String logo = null;

		try {
			connection = getConnection();

			String getLogoQuery = QueryMaker.RETRIEVE_LOGO;

			preparedStatement = connection.prepareStatement(getLogoQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				logo = resultSet.getString("logo");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return logo;

	}

	/**
	 * 
	 * @return
	 */
	public String getWebsite() {

		String website = null;

		try {
			connection = getConnection();

			String getWebsiteQuery = QueryMaker.RETRIEVE_WEBSITE;

			preparedStatement = connection.prepareStatement(getWebsiteQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				website = resultSet.getString("website");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return website;

	}

	/**
	 * 
	 * @return
	 */
	public String getTagline() {

		String tagline = null;

		try {
			connection = getConnection();

			String getTaglineQuery = QueryMaker.RETRIEVE_TAGLINE;

			preparedStatement = connection.prepareStatement(getTaglineQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				tagline = resultSet.getString("tagline");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tagline;
	}

	public String getReviewFormURL() {

		String reviewFormURL = null;

		try {
			connection = getConnection();

			String getReviewFormURLQuery = QueryMaker.RETRIEVE_REVIEW_FORM_URL;

			preparedStatement = connection.prepareStatement(getReviewFormURLQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reviewFormURL = resultSet.getString("reviewForm");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return reviewFormURL;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getDiagnoseList() {
		HashMap<String, String> diagnoseMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getDiagnoseListQuery = QueryMaker.RETRIEVE_DIAGNOSES_LIST;

			preparedStatement = connection.prepareStatement(getDiagnoseListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String diagnosis = resultSet.getString("diagnosis");

				diagnoseMap.put(diagnosis, diagnosis);
			}
			System.out.println("successfully retrived diagnoseList");
			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return diagnoseMap;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getTradeNameList() {
		HashMap<String, String> tradeNameMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getTradeNameListQuery = QueryMaker.RETRIEVE_TRADE_NAME_LIST;

			preparedStatement = connection.prepareStatement(getTradeNameListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("tradeName");

				tradeNameMap.put(tradeName, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("trade name List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tradeNameMap;
	}

	public HashMap<String, String> getTradeNameList(String tradeName) {
		HashMap<String, String> tradeNameMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getTradeNameListQuery = QueryMaker.RETRIEVE_TRADE_NAME_LIST_BY_TRADE_NAME;

			preparedStatement = connection.prepareStatement(getTradeNameListQuery);

			if (tradeName.matches("\\s+")) {
				tradeName = tradeName.replace("\\s+", "%");
			}

			preparedStatement.setString(1, "%" + tradeName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String name = resultSet.getString("tradeName");

				tradeNameMap.put(name, name);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tradeNameMap;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getCategoryList() {
		HashMap<Integer, String> categoryMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getTradeNameListQuery = QueryMaker.RETRIEVE_CATEGORY_LIST;

			preparedStatement = connection.prepareStatement(getTradeNameListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("name");
				int id = resultSet.getInt("id");

				categoryMap.put(id, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Category List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return categoryMap;
	}

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public LinkedHashMap<String, String> getFrequencyList(int practiceID) {

		LinkedHashMap<String, String> frequencyMap = new LinkedHashMap<String, String>();

		try {
			connection = getConnection();

			String getFrequencyListQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST;

			preparedStatement = connection.prepareStatement(getFrequencyListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String frequency = resultSet.getString("frequencyValues");

				frequencyMap.put(frequency, frequency);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("frequeny List retrieved succesfully..");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;

	}

	public LinkedHashMap<String, String> getFrequencySortedListList(int practiceID) {

		LinkedHashMap<String, String> frequencyMap = new LinkedHashMap<String, String>();

		try {
			connection = getConnection();

			String getFrequencyListQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST;

			preparedStatement = connection.prepareStatement(getFrequencyListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String frequency = resultSet.getString("frequencyValues");

				frequencyMap.put(frequency, frequency);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;

	}

	/**
	 * 
	 * @param tradeName
	 * @return
	 */
	public JSONObject valuesOnBehalfOfDrugName(String tradeName) {
		JSONObject values = null;
		JSONArray array = new JSONArray();
		values = new JSONObject();

		try {
			connection = getConnection();

			String valuesOnBehalfOfDrugNameQuery = QueryMaker.RETRIEVE_DRUGNAME_DOSE;

			preparedStatement = connection.prepareStatement(valuesOnBehalfOfDrugNameQuery);

			preparedStatement.setString(1, tradeName);

			resultSet = preparedStatement.executeQuery();

			JSONObject object = new JSONObject();

			while (resultSet.next()) {
				String dose = resultSet.getString("dose");
				String doseUnit = resultSet.getString("drugName");

				object.put("Dose", dose);
				object.put("DoseUnit", doseUnit);
				array.add(object);

				values.put("Release", array);
				return values;
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			return values;
		}
		return values;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getChargeTypeList() {

		HashMap<String, String> chargeTypeMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getChargeTypeListQuery = QueryMaker.RETRIEVE_CHARGE_TYPE_LIST;

			preparedStatement = connection.prepareStatement(getChargeTypeListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String chargeType = resultSet.getString("chargeType");

				chargeTypeMap.put(chargeType, chargeType);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return chargeTypeMap;

	}

	/**
	 * 
	 * @param chargeType
	 * @return
	 */
	public JSONObject valuesOnBehalfOfChargeType(String chargeType) {
		JSONObject values = null;
		JSONArray array = new JSONArray();
		values = new JSONObject();

		try {
			connection = getConnection();

			String valuesOnBehalfOfChargeTypeQuery = QueryMaker.RETRIEVE_CHARGE_BY_CHARGE_TYPE;

			preparedStatement = connection.prepareStatement(valuesOnBehalfOfChargeTypeQuery);

			preparedStatement.setString(1, chargeType);

			resultSet = preparedStatement.executeQuery();

			JSONObject object = new JSONObject();

			while (resultSet.next()) {
				String charge = resultSet.getString("charge");

				object.put("Charge", charge);
				array.add(object);

				values.put("Release", array);
				return values;
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			return values;
		}
		return values;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getReferringDoctorList(int practiceID) {

		HashMap<String, String> referringDoctorMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getReferringDoctorListQuery = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(getReferringDoctorListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				referringDoctorMap.put(resultSet.getString("doctorName"), resultSet.getString("doctorName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Reffering dr List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return referringDoctorMap;

	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getClinicTypeList() {

		HashMap<String, String> getClinicTypeListrMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getClinicTypeListQuery = QueryMaker.RETRIEVE_CLINIC_TYPE_LIST;

			preparedStatement = connection.prepareStatement(getClinicTypeListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				getClinicTypeListrMap.put(resultSet.getString("clinicType"), resultSet.getString("clinicType"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return getClinicTypeListrMap;

	}

	/**
	 * 
	 * @return
	 */
	public List<String> getReferralDoctorList() {
		List<String> doctorList = new ArrayList<String>();

		try {
			connection = getConnection();

			String getReferralDoctorListQuery = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(getReferralDoctorListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				doctorList.add(resultSet.getString("doctorName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return doctorList;

	}

	/**
	 * 
	 * @param columnName
	 * @return
	 */
	public boolean verifyCommunicationCheck(String columnName) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyCommunicationCheckQuery = "SELECT " + columnName + " FROM Communication WHERE practiceID = ?";

			preparedStatement = connection.prepareStatement(verifyCommunicationCheckQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getBoolean(columnName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getInstructionList() {

		HashMap<String, String> instructionMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getInstructionListQuery = QueryMaker.RETRIEVE_ALL_INSTRUCTION_LIST;

			preparedStatement = connection.prepareStatement(getInstructionListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String instruction = resultSet.getString("instructions");

				instructionMap.put(instruction, instruction);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return instructionMap;

	}

	/**
	 * 
	 * @param categoryID
	 * @return
	 */
	public List<String> getFrequencyListByCategory(int categoryID) {

		List<String> frequencyMap = new ArrayList<String>();

		try {
			connection = getConnection();

			String getFrequencyListByCategoryQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST_BY_CATEGORY;

			preparedStatement = connection.prepareStatement(getFrequencyListByCategoryQuery);

			preparedStatement.setInt(1, categoryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String frequency = resultSet.getString("frequencyValues");

				frequencyMap.add(frequency);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;

	}

	/**
	 * 
	 * @param category
	 * @return
	 */
	public List<String> getOtherFrequencyListByCategory(String category) {

		List<String> frequencyMap = new ArrayList<String>();

		try {
			connection = getConnection();

			String getFrequencyListByCategoryQuery = "SELECT distinct(frequencyValues) FROM PVFrequency WHERE categoryID NOT IN ("
					+ category + ") ORDER BY frequencyValues ASC";

			preparedStatement = connection.prepareStatement(getFrequencyListByCategoryQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String frequency = resultSet.getString("frequencyValues");

				frequencyMap.add(frequency);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;

	}

	/**
	 * 
	 * @param otherCategory
	 * @return
	 */
	public LinkedHashMap<Integer, String> getOtherCategoryList(String otherCategory) {
		HashMap<Integer, String> categoryMap = new HashMap<Integer, String>();

		LinkedHashMap<Integer, String> otherCatMap = new LinkedHashMap<Integer, String>();

		try {
			connection = getConnection();

			String getOtherCategoryListQuery = "SELECT id, name FROM Category WHERE id NOT IN (" + otherCategory
					+ ") ORDER BY name";

			preparedStatement = connection.prepareStatement(getOtherCategoryListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("name");
				int id = resultSet.getInt("id");

				categoryMap.put(id, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			List<Integer> mapKeys = new ArrayList<Integer>(categoryMap.keySet());
			List<String> mapValues = new ArrayList<String>(categoryMap.values());
			Collections.sort(mapValues);
			Collections.sort(mapKeys);

			// Sorting hashmap by values in alphabetical order
			Iterator<String> valueIt = mapValues.iterator();
			while (valueIt.hasNext()) {
				String val = valueIt.next();
				Iterator<Integer> keyIt = mapKeys.iterator();

				while (keyIt.hasNext()) {
					Integer key = keyIt.next();
					String comp1 = categoryMap.get(key);
					String comp2 = val;

					if (comp1.equals(comp2)) {
						keyIt.remove();
						otherCatMap.put(key, val);
						break;
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return otherCatMap;
	}

	/**
	 * 
	 * @param categoryID
	 * @return
	 */
	public LinkedHashMap<Integer, String> getRasayanProductList(int categoryID, int clinicID) {
		LinkedHashMap<Integer, String> rasayanProductMap = new LinkedHashMap<Integer, String>();

		HashMap<Integer, String> productMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getRasayanProductListQuery = QueryMaker.RETRIEVE_RASAYAN_PRODUCT_LIST;

			preparedStatement = connection.prepareStatement(getRasayanProductListQuery);

			preparedStatement.setInt(1, categoryID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("tradeName");
				int id = resultSet.getInt("id");

				productMap.put(id, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			List<Integer> mapKeys = new ArrayList<Integer>(productMap.keySet());
			List<String> mapValues = new ArrayList<String>(productMap.values());
			Collections.sort(mapValues);
			Collections.sort(mapKeys);

			// Sorting hashmap by values in alphabetical order
			Iterator<String> valueIt = mapValues.iterator();
			while (valueIt.hasNext()) {
				String val = valueIt.next();
				Iterator<Integer> keyIt = mapKeys.iterator();

				while (keyIt.hasNext()) {
					Integer key = keyIt.next();
					String comp1 = productMap.get(key);
					String comp2 = val;

					if (comp1.equals(comp2)) {
						keyIt.remove();
						rasayanProductMap.put(key, val);
						break;
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return rasayanProductMap;
	}

	public LinkedHashMap<Integer, String> sortHashMap(HashMap<Integer, String> map) {

		LinkedHashMap<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();

		List<Integer> mapKeys = new ArrayList<Integer>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		// Sorting hashmap by values in alphabetical order
		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next();
			Iterator<Integer> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Integer key = keyIt.next();
				String comp1 = map.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}

		return sortedMap;

	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public LinkedHashMap<String, String> sortHashMapByValue(HashMap<String, String> map) {

		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<String, String>();

		List<String> mapKeys = new ArrayList<String>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		// Sorting hashmap by values in alphabetical order
		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next();
				String comp1 = map.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}

		return sortedMap;

	}
	
	public HashMap<String, String> getDiagnosticList1(){
		
		HashMap<String, String> diagnosticMap = new HashMap<String, String>();

        // Add the values to the map
        diagnosticMap.put("OCT macula", "OCT macula");
        diagnosticMap.put("OCT optic disc", "OCT optic disc");
        diagnosticMap.put("Cornea OCT", "Cornea OCT");
        diagnosticMap.put("Anterior segment OCT for vault", "Anterior segment OCT for vault");
        diagnosticMap.put("Pachymetry in OCT", "Pachymetry in OCT");
        diagnosticMap.put("OCT angle", "OCT angle");
        diagnosticMap.put("Biometry/iol master 700", "Biometry/iol master 700");
        diagnosticMap.put("Perimetry", "Perimetry");
        diagnosticMap.put("Topography", "Topography");
        diagnosticMap.put("B scan", "B scan");
        diagnosticMap.put("Fundus photo", "Fundus photo");
		
		return diagnosticMap;	
	}
	
	public HashMap<String, String> getProceduresList(){
		
		HashMap<String, String> procedureMap = new HashMap<String, String>();

        // Add the values to the map
        procedureMap.put("Sac syringing", "Sac syringing");
        procedureMap.put("Caliper for ICL", "Caliper for ICL");
        procedureMap.put("Yag capsulotomy", "Yag capsulotomy");
        procedureMap.put("Yag PI", "Yag PI");
		
		return procedureMap; 
	}

	public HashMap<String, String> getInvestigationList() {

		HashMap<String, String> testNameMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getInvestigationListQuery = QueryMaker.RETRIEVE_Investigation_TEST_NAME_LIST;

			preparedStatement = connection.prepareStatement(getInvestigationListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String testName = resultSet.getString("test");

				testNameMap.put(testName, testName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
			System.out.println("investigation List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return testNameMap;
	}

	public boolean verifyDiagnosisExits(String diagnosis) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyDiagnosisExitsQuery = QueryMaker.VERIFY_DIAGNOSIS_EXISTS;

			preparedStatement = connection.prepareStatement(verifyDiagnosisExitsQuery);

			preparedStatement.setString(1, diagnosis);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public HashMap<Integer, String> getCategoryWiseTradeNameList(int clinicID, String category) {
		HashMap<Integer, String> InjectionMap = new HashMap<Integer, String>();
		// System.out.println("Values : "+clinicID+" - "+ category);
		try {
			connection = getConnection();

			String getCategoryWiseTradeNameListQuery = QueryMaker.RETRIEVE_Injection_LIST;

			preparedStatement = connection.prepareStatement(getCategoryWiseTradeNameListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, category);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("tradeName");
				int id = resultSet.getInt("id");

				InjectionMap.put(id, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return InjectionMap;
	}

	public LinkedHashMap<String, String> getFrequencyList() {

		LinkedHashMap<String, String> frequencyMap = new LinkedHashMap<String, String>();
		;

		try {
			connection = getConnection();

			String getFrequencyListQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST1;

			preparedStatement = connection.prepareStatement(getFrequencyListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String frequency = resultSet.getString("frequencyValues");

				frequencyMap.put(frequency, frequency);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;

	}

	public HashMap<String, String> getDiagnosisList() {
		HashMap<String, String> DiagnosisMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getDiagnosisListQuery = QueryMaker.RETRIEVE_DIAGNOSIS_LIST;

			preparedStatement = connection.prepareStatement(getDiagnosisListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String diagnosis = resultSet.getString("diagnosis");
				int id = resultSet.getInt("id");

				DiagnosisMap.put(diagnosis, diagnosis);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return DiagnosisMap;
	}

	public LinkedHashMap<Integer, String> sortHashMapByValue1(HashMap<Integer, String> map) {

		LinkedHashMap<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();

		List<Integer> mapKeys = new ArrayList<Integer>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		// Sorting hashmap by values in alphabetical order
		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next();
			Iterator<Integer> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Integer key = keyIt.next();
				String comp1 = map.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();

					sortedMap.put(key, val);
					break;
				}
			}
		}

		return sortedMap;

	}

	public HashMap<Integer, String> getPatientDetailsList(String searchName, int practiceID, int clinicID) {
		HashMap<Integer, String> searchNameMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getPatientDetailsListQuery = QueryMaker.RETRIEVE_Patient_Details_LIST_BY_Patient_NAME;

			preparedStatement = connection.prepareStatement(getPatientDetailsListQuery);

			if (searchName.matches("\\s+")) {
				searchName = searchName.replace("\\s+", "%");
			}

			preparedStatement.setString(1, "%" + searchName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String name = resultSet.getString("patientDetails");
				int patientID = resultSet.getInt("id");
				searchNameMap.put(patientID, name);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return searchNameMap;
	}

	/**
	 * 
	 * @return
	 */
	public String getS3BucketName() {

		String bucketName = null;

		try {
			connection = getConnection();

			String getBucketNameQuery = QueryMaker.RETRIEVE_BUCKET_NAME;

			preparedStatement = connection.prepareStatement(getBucketNameQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				bucketName = resultSet.getString("bucketName");
				System.out.println("bucket name is::" + bucketName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketName;
	}

	public String getS3BucketNameForPractice(int practiceIDNew) {

		String bucketName = null;

		try {
			connection = getConnection();

			String getBucketNameQuery = QueryMaker.RETRIEVE_BUCKET_NAME;

			preparedStatement = connection.prepareStatement(getBucketNameQuery);

			preparedStatement.setInt(1, practiceIDNew);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				bucketName = resultSet.getString("bucketName");
				System.out.println("bucket name is::" + bucketName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketName;
	}

	public HashMap<Integer, String> getMDDoctorList(int practiceID) {

		HashMap<Integer, String> MDDoctorMap = new HashMap<Integer, String>();

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date currentDate = new Date();

		try {
			connection = getConnection();

			String getMDDoctorListQuery = QueryMaker.RETRIEVE_PRACTICE_MD_DETAILS;

			preparedStatement = connection.prepareStatement(getMDDoctorListQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, dateToBeFormatted.format(currentDate));
			System.out.println(preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				MDDoctorMap.put(resultSet.getInt("id"), resultSet.getString("MDName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("MD Doctor List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return MDDoctorMap;

	}

	public HashMap<String, String> getLabInvestigationsValueList(int visitID) {

		HashMap<String, String> LabInvestigationMap = new HashMap<String, String>();

		double finalRateIs = 0D;
		try {
			connection = getConnection();

			String getLabInvestigationsValueListQuery = QueryMaker.RETRIEVE_LabInvestigations_GROUP_Value_DETAILS;

			preparedStatement = connection.prepareStatement(getLabInvestigationsValueListQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("isGroup") != null) {

					if (resultSet.getString("isGroup").equals("1")) {

						finalRateIs = finalRateIs + resultSet.getDouble("groupRate");

						LabInvestigationMap.put(resultSet.getString("groupValues"),
								resultSet.getString("groupName") + " - " + resultSet.getDouble("groupRate"));
					}
				}
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("LabInvestigations details List retrieved succesfully..");
			
			System.out.println("*********************************************************************************");
			System.out.println(LabInvestigationMap);
			System.out.println("*********************************************************************************");


		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return LabInvestigationMap;

	}

	public HashMap<Integer, String> getDoctorList(int practiceID2) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> referringDoctorMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();
			System.out.println("Practice ID :: " + practiceID2);
			String getDoctorListQuery = QueryMaker.RETRIEVE_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(getDoctorListQuery);
			preparedStatement.setInt(1, practiceID2);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("doctor name ::: " + resultSet.getString("doctorName"));
				referringDoctorMap.put(resultSet.getInt("id"), resultSet.getString("doctorName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Reffering dr List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return referringDoctorMap;
	}

	public HashMap<Integer, String> getRepostList(int visitTypeID) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveVisitDetails = QueryMaker.RETREIVE_VISIT_DETAILS;

			preparedStatement1 = connection.prepareStatement(retrieveVisitDetails);

			preparedStatement1.setInt(1, visitTypeID);

			resultSet1 = preparedStatement1.executeQuery();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETRIEVE_REPORT_TYPE_BY_TEMPREPORTTYPE;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			int check = 0;

			while (resultSet1.next()) {
				if (resultSet1.getString("careType").equals("CTScan")) {
					check++;

					preparedStatement.setString(1, "CT SCAN");

					resultSet = preparedStatement.executeQuery();
				} else if (resultSet1.getString("careType").equals("XRay")) {
					check++;

					preparedStatement.setString(1, "X-RAY");

					resultSet = preparedStatement.executeQuery();
				} else if (resultSet1.getString("careType").equals("USG")) {
					check++;

					preparedStatement.setString(1, "USG");

					resultSet = preparedStatement.executeQuery();
				}
			}

			if (check > 0) {
				while (resultSet.next()) {
					System.out.println("TempReportType ::: " + resultSet.getString("TempReportType"));
					map.put(resultSet.getInt("id"), resultSet.getString("TempName"));

				}

				resultSet.close();
				preparedStatement.close();
			}

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());

		}
		return map;
	}

	public HashMap<String, String> getDiagnosisList1() {
		// TODO Auto-generated method stub
		HashMap<String, String> DiagnosisMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getDiagnosisListQuery = QueryMaker.RETRIEVE_DIAGNOSIS_LIST;

			preparedStatement = connection.prepareStatement(getDiagnosisListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String diagnosis = resultSet.getString("diagnosis");
				int id = resultSet.getInt("id");

				DiagnosisMap.put(diagnosis, diagnosis);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return DiagnosisMap;
	}

    public HashMap<String, String> getLabInvestigationsValueList1(int visitID) {
        // TODO Auto-generated method stub
        HashMap<String, String> LabInvestigationMap = new HashMap<String, String>();

        double finalRateIs = 0D;
        try {
            connection = getConnection();

            String getLabInvestigationsValueListQuery = QueryMaker.RETRIEVE_LabInvestigations_Value_DETAILS;

            preparedStatement = connection.prepareStatement(getLabInvestigationsValueListQuery);

            preparedStatement.setInt(1, visitID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (resultSet.getString("isGroup").equals("0")) {

                    finalRateIs = finalRateIs + resultSet.getDouble("groupRate");

                    LabInvestigationMap.put(resultSet.getString("groupValues"), resultSet.getString("test") + " - "
                            + resultSet.getDouble("rate") + " - " + resultSet.getString("testType"));
                }
            }

            resultSet.close();
            preparedStatement.close();

            connection.close();

            System.out.println("LabInvestigations details List retrieved succesfully..");

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return LabInvestigationMap;

    }

	public HashMap<Integer, String> getReferringDoctorList1(int practiceID) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> referringDoctorMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getReferringDoctorListQuery = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(getReferringDoctorListQuery);
			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				referringDoctorMap.put(resultSet.getInt("id"), resultSet.getString("doctorName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Reffering dr List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return referringDoctorMap;

	}

	public HashMap<String, String> getGroupTestList(int practiceID, String columName) {
		// TODO Auto-generated method stub
		HashMap<String, String> groupTestList = new HashMap<String, String>();

		try {

			connection = getConnection();

			String getDiagnoseListQuery = QueryMaker.RETRIEVE_GROUP_TEST_LIST;
			String RetriveTestID = QueryMaker.RETRIEVE_TEST_ID;
			preparedStatement = connection.prepareStatement(getDiagnoseListQuery);
			preparedStatement.setInt(1, practiceID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String testName = "";
				String testRate = "";
				String rangeValue = "";
				String subGroup = "";
				String testType = "";
				String groupValues = "";
				System.out.println("test id iss :: " + resultSet.getInt("id"));

				preparedStatement2 = connection.prepareStatement(RetriveTestID);

				preparedStatement2.setInt(1, resultSet.getInt("id"));

				resultSet2 = preparedStatement2.executeQuery();

				while (resultSet2.next()) {
					System.out.println("test ID " + resultSet2.getInt("testID"));
					String retrieveLabTests = QueryMaker.RETRIEVE_TEST_LIST_BY_ID;

					preparedStatement1 = connection.prepareStatement(retrieveLabTests);

					preparedStatement1.setInt(1, resultSet2.getInt("testID"));

					resultSet1 = preparedStatement1.executeQuery();

					while (resultSet1.next()) {
						testName = resultSet1.getString("test") + "," + testName;
						testRate = resultSet1.getString("testRate") + "," + testRate;
						testType = resultSet1.getString("type") + "," + testType;
						rangeValue = resultSet1.getString(columName) + "," + rangeValue;
						subGroup = resultSet1.getString("subgroup") + "," + subGroup;
					}

				}
				groupValues = resultSet.getString("groupName") + "$" + resultSet.getString("groupRate") + "$"
						+ testName.substring(0, testName.length() - 1) + "$"
						+ rangeValue.substring(0, rangeValue.length() - 1) + "$"
						+ subGroup.substring(0, subGroup.length() - 1) + "$"
						+ testType.substring(0, testType.length() - 1) + "$"
						+ testRate.substring(0, testRate.length() - 1) + "$1";

				System.out.println("groupValues " + groupValues);
				groupTestList.put(groupValues, resultSet.getString("groupName"));
			}

			if (resultSet1 != null) {
				try {
					resultSet1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (preparedStatement1 != null) {
				try {
					preparedStatement1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return groupTestList;
	}

	public HashMap<String, String> getSingleTestList(int practiceID, String columName) {
		// TODO Auto-generated method stub
		HashMap<String, String> groupSingleTestList = new HashMap<String, String>();

		try {

			connection = getConnection();

			String getDiagnoseListQuery = QueryMaker.RETRIEVE_SINGLE_TEST_LIST;
			String retrieveGroupNameTests = QueryMaker.RETRIEVE_GROUPNAME_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(getDiagnoseListQuery);
			preparedStatement.setInt(1, practiceID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String groupValues = "";

				preparedStatement1 = connection.prepareStatement(retrieveGroupNameTests);

				preparedStatement1.setInt(1, resultSet.getInt("testID"));

				resultSet1 = preparedStatement1.executeQuery();

				String groupDetails = "$0";

				while (resultSet1.next()) {
					groupDetails = resultSet1.getString("groupDetails");
				}

				groupValues = groupDetails + "$" + resultSet.getString("testDetail") + "$"
						+ resultSet.getString("testRate") + "$0";

				groupSingleTestList.put(groupValues, resultSet.getString("testName"));
			}

			resultSet1.close();
			preparedStatement1.close();

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return groupSingleTestList;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getPECTradeNameList() {
		HashMap<String, String> tradeNameMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getTradeNameListQuery = QueryMaker.RETRIEVE_PEC_TRADE_NAME_LIST;

			preparedStatement = connection.prepareStatement(getTradeNameListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("tradeName");

				tradeNameMap.put(tradeName, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("trade name List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tradeNameMap;
	}

	public HashMap<String, String> getPECTradeNameList(String tradeName) {
		HashMap<String, String> tradeNameMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getPECTradeNameListQuery = QueryMaker.RETRIEVE_PEC_TRADE_NAME_LIST_BY_TRADE_NAME;

			preparedStatement = connection.prepareStatement(getPECTradeNameListQuery);

			if (tradeName.matches("\\s+")) {
				tradeName = tradeName.replace("\\s+", "%");
			}

			preparedStatement.setString(1, "%" + tradeName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String name = resultSet.getString("tradeName");

				tradeNameMap.put(name, name);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tradeNameMap;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getPECCategoryList() {
		HashMap<Integer, String> categoryMap = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getPECCategoryList = QueryMaker.RETRIEVE_PEC_CATEGORY_LIST;

			preparedStatement = connection.prepareStatement(getPECCategoryList);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String tradeName = resultSet.getString("name");
				int id = resultSet.getInt("id");

				categoryMap.put(id, tradeName);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Category List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return categoryMap;
	}

	/*
	 * Check if Referred By doctor exists
	 * 
	 */
	public boolean checkReferredByDoctorExists(String name, int practiceID) {

		boolean check = false;

		if (name == null) {
			name = "";
		}

		try {

			connection = getConnection();

			String referringDoctorList = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(referringDoctorList);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (name.equals(resultSet.getString("doctorName"))) {
					check = true;
					break;
				}
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public HashMap<String, String> getRefDocList(int practiceID) {
		// TODO Auto-generated method stub
		HashMap<String, String> referringDoctorMap = new HashMap<String, String>();

		try {
			connection = getConnection();

			String getReferringDoctorListQuery = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(getReferringDoctorListQuery);
			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				referringDoctorMap.put(resultSet.getString("doctorName"), resultSet.getString("doctorName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Reffering dr List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return referringDoctorMap;

	}

	public HashMap<Integer, String> getRepostListByType(String reportType, String testName) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();
			
            // Step 2: Retrieve the primary key (pvLabtestID) based on reportType
            String retrievePVLabtestIDQuery = QueryMaker.RETRIEVE_PVLABTEST_ID;
            preparedStatement = connection.prepareStatement(retrievePVLabtestIDQuery);
            preparedStatement.setString(1, testName);

            resultSet = preparedStatement.executeQuery();

            int pvLabtestID = -1;
            if (resultSet.next()) {
                pvLabtestID = resultSet.getInt("id");
            }
            
            resultSet.close();
            preparedStatement.close();

            // Step 3: Use pvLabtestID to retrieve an array of template IDs
            if (pvLabtestID != -1) {
                String retrieveTemplateIDsQuery = QueryMaker.RETRIEVE_TEMPLATE_IDS;
                preparedStatement = connection.prepareStatement(retrieveTemplateIDsQuery);
                preparedStatement.setInt(1, pvLabtestID);

                resultSet = preparedStatement.executeQuery();
                List<Integer> templateIDs = new ArrayList<>();

                while (resultSet.next()) {
                    templateIDs.add(resultSet.getInt("templateID"));
                }

                resultSet.close();
                preparedStatement.close();

                // Step 4: Use the retrieved template IDs to fetch template names and store them in the map
                String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_REPORT_TYPE;

                for (Integer templateID : templateIDs) {
                    preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);
                    preparedStatement.setInt(1, templateID);

                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        map.put(resultSet.getInt("id"), resultSet.getString("TempName"));
                    }

                    resultSet.close();
                    preparedStatement.close();
                }
            }

            // Step 5: Close the connection
            connection.close();


		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());

		}
		return map;
	}

}
