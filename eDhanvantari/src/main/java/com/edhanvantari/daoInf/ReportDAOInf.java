package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.edhanvantari.form.PatientForm;

public interface ReportDAOInf {

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<PatientForm> retrieveListForMedicalReport(int patientID, String patientName);

	/**
	 * 
	 * @param tableName
	 * @param keyValue
	 * @return
	 */
	public JSONObject retrieveColumnList(String tableName, String keyValue);

	/**
	 * 
	 * @param tableName
	 * @param keyValue
	 * @return
	 */
	public HashMap<String, String> retrievePatientColumnList(String tableName, String keyValue);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertSavedQuery(PatientForm patientForm);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveSavedQueryList(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @param status
	 * @return
	 */
	public List<PatientForm> retrieveProductTotalNetStock(int clinicID, String status);

	/**
	 * 
	 * @param productID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveStockReceiptListByProductID(int productID, int clinicID);

	public HashMap<String, String> retrieveReportConfigPatientColumnList(String string, String string2);

	public int retrieveSavedQuewryID(PatientForm patientForm);

	public String insertSavedQueryParameter(String tableName, String columnName, String dataType,
			String selectedValues, String criteria, int savedQuewryID);

	public int retrieveSavedQuewryParameterID(String tableName, String columnName, int savedQuewryID);

	public String insertSavedQueryParameterValue(String searchValue, int savedQueryParameterID);

	public JSONObject retrieveReportConfigColumnList(String check, String keyValue);

	public JSONObject retriveSavedQueryValuesBySaveQueryID(int saveQueryID);

	public HashMap<String, String> retrieveSavedQueryDetailsList(int saveQueryID);

	public List<String> retrieveSavedQuerySearchText(int saveQueryID);

	public String updateSavedQueryTitle(int saveQueryID, String queryTitle);

	public String updateSavedQueryParameter(int parseInt, String string);

	public String updateSavedQueryParameterValues(int parseInt, String string);

	public JSONObject retriveColumnNamesForSavedQueryValuesBySaveQueryID(String parentTableName);
}
