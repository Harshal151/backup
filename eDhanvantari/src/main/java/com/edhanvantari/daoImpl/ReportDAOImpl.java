package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.ReportDAOInf;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.EmailUtil;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;

public class ReportDAOImpl extends DAOConnection implements ReportDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	Connection connection1 = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	ConfigXMLUtil xmlUtil = null;

	ConfigurationUtil configurationUtil = null;

	String status = "error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ReportDAOInf#retrieveListForMedicalReport(int,
	 * String)
	 */
	public List<PatientForm> retrieveListForMedicalReport(int patientID, String patientName) {
		List<PatientForm> patientVisitDetails = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {
			connection = getConnection();

			String retrieveListForMedicalBilingQuery = QueryMaker.RETRIEVE_PATIENT_VISIT_DETAIL;

			preparedStatement = connection.prepareStatement(retrieveListForMedicalBilingQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, "OPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(resultSet.getString("visitDate"));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setPatientID(patientID);
				patientForm.setPatientName(patientName);

				patientVisitDetails.add(patientForm);
			}
			System.out.println("Successfully retrieved patient's visit for medical billing report details.");

			resultSet.close();
			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient visit details for medical billing report from table due to:::"
							+ exception.getMessage());
		}
		return patientVisitDetails;
	}

	public JSONObject retrieveColumnList(String tableName, String keyValue) {

		JSONObject values = new JSONObject();

		JSONObject object = null;

		JSONArray array = new JSONArray();

		JSONArray array1 = new JSONArray();

		int check = 0;

		try {

			connection = getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tableName.split(" AS ");

			String retrieveColumnListQuery = "DESC " + tagNameArray[0];

			preparedStatement = connection.prepareStatement(retrieveColumnListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
System.out.println("inside while check1");
System.out.println("keyvalue "+keyValue);
System.out.println("field "+keyValue);
				object = new JSONObject();

				object.put("check", check);
				object.put("columns", keyValue + "=" + resultSet.getString("Field"));
				object.put("columnType", resultSet.getString("Type"));
				object.put("tableCheck", "nonVisit");

				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				object.put("tableCheck", "nonVisit");

				array.add(object);

				values.put("Release", array);

			}
			System.out.println("outside while check1");
			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");
			object.put("columns", "");

			array1.add(object);

			values.put("Release1", array1);

		} catch (Exception exception) {
			exception.printStackTrace();

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");

			array.add(object);

			values.put("Release", array);

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");
			object.put("columns", "");

			array1.add(object);

			values.put("Release1", array1);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;

	}

	public String insertSavedQueryParameterValue(String searchValue, int savedQueryParameterID) {

		try {

			connection = getConnection();

			String insertSavedQueryParameterValueQuery = QueryMaker.INSERT_SAVED_QUERY_PARAMETER_VALUE;

			preparedStatement = connection.prepareStatement(insertSavedQueryParameterValueQuery);

			preparedStatement.setString(1, searchValue);
			preparedStatement.setInt(2, savedQueryParameterID);
		
			preparedStatement.execute();

			status = "success";

			System.out.println("Save Query Parameter Value inserted successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query Parameter Values: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}
	
	public JSONObject retrieveReportConfigColumnList(String tableName, String keyValue) {

		JSONObject values = new JSONObject();

		JSONObject object = null;
		
		JSONArray array = new JSONArray();

		JSONArray array1 = new JSONArray();

		int check = 0;

		try {

			connection = getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tableName.split(" AS ");

			String retrieveReportConfigColumnListQuery = "SELECT * FROM ReportConfig WHERE tableName = '"
					+ tagNameArray[0] + "' AND reportFlag = 1";

			preparedStatement = connection.prepareStatement(retrieveReportConfigColumnListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				object = new JSONObject();

				object.put("check", check);
				object.put("columns", keyValue + "=" + tagNameArray[1] + "." + resultSet.getString("columnName"));
				object.put("columnType", resultSet.getString("dataType"));
				object.put("fieldName", resultSet.getString("fieldName"));
				object.put("tableCheck", "nonVisit");

				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				object.put("tableCheck", "nonVisit");

				array.add(object);

				values.put("Release", array);

			}

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");
			object.put("columns", "");

			array1.add(object);

			values.put("Release1", array1);

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve Column List: Exception");

			exception.printStackTrace();

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");

			array.add(object);

			values.put("Release", array);

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "nonVisit");
			object.put("columns", "");

			array1.add(object);

			values.put("Release1", array1);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;

	}
	
	public HashMap<String, String> retrieveReportConfigPatientColumnList(String tableName, String keyValue) {

		HashMap<String, String> map = new HashMap<String, String>();

		configurationUtil = new ConfigurationUtil();

		try {

			connection = getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tableName.split(" AS ");

			String retrieveReportConfigPatientColumnListQuery = "SELECT * FROM ReportConfig WHERE tableName = '"
					+ tagNameArray[0] + "' AND reportFlag = 1";

			preparedStatement = connection.prepareStatement(retrieveReportConfigPatientColumnListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("NA", "No Criteria");

				map.put(resultSet.getString("dataType") + "=" + tagNameArray[1] + "."
						+ resultSet.getString("columnName"), resultSet.getString("fieldName"));

			}

			// sorting hashmap by key
			map = configurationUtil.sortHashMapByValue(map);

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendExceptionInfoEmail(stringWriter.toString(),"Retrieve ReportConfig Patient Column List: Exception");

			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	
	public HashMap<String, String> retrievePatientColumnList(String tableName, String keyValue) {

		HashMap<String, String> map = new HashMap<String, String>();

		configurationUtil = new ConfigurationUtil();

		try {

			connection = getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tableName.split(" AS ");

			String retrieveColumnListQuery = "DESC " + tagNameArray[0];

			preparedStatement = connection.prepareStatement(retrieveColumnListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("Type") + "=" + resultSet.getString("Field"), resultSet.getString("Field"));

			}

			// sorting hashmap by key
			map = configurationUtil.sortHashMapByValue(map);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public String insertSavedQuery(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertSavedQueryQuery = QueryMaker.INSERT_SAVED_QUERY;

			preparedStatement = connection.prepareStatement(insertSavedQueryQuery);

			System.out.println("saved query::"+patientForm.getSavedQuery());
			System.out.println("initial where::"+patientForm.getInitialWhere());
			
			preparedStatement.setString(1, patientForm.getSavedQuery());
			preparedStatement.setString(2, patientForm.getQueryTitle());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, patientForm.getClinicID());
			preparedStatement.setInt(5, patientForm.getUserID());
			preparedStatement.setString(6, patientForm.getInitialWhere());

			preparedStatement.execute();

			status = "success";

			System.out.println("Save query inserted successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	private String retriveSelectedValueList(String tableName) {

		String SelectedValues = "";

		try {

			connection1 = getConnection();

			String retriveSelectedValueListQuery = QueryMaker.RETRIVE_Selected_Value_List;

			preparedStatement1 = connection1.prepareStatement(retriveSelectedValueListQuery);

			preparedStatement1.setString(1, tableName);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				SelectedValues = resultSet1.getString("selectedValues");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return SelectedValues;
	}

	
	
	private String displayColumnName(String tableName, String columnName) {

		String ColumnName = "";

		try {
			
			connection1 = getConnection();

			String displayColumnNameQuery = QueryMaker.RETRIVE_ColumnName;

			preparedStatement1 = connection1.prepareStatement(displayColumnNameQuery);

			preparedStatement1.setString(1, tableName);
			preparedStatement1.setString(2, columnName);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				
				ColumnName = resultSet1.getString("fieldName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return ColumnName;
	}
	
	private String retriveSearchTextValues(int savedQueryParameterID) {
		
		String SearchText = "";

		try {

			connection1 = getConnection();

			String retriveSearchTextValuesQuery = QueryMaker.RETRIVE_SavedQuery_SearchValues_By_SaveQueryParameterID;

			preparedStatement1 = connection1.prepareStatement(retriveSearchTextValuesQuery);

			preparedStatement1.setInt(1, savedQueryParameterID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				SearchText = resultSet1.getString("searchValue");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return SearchText;
	}


	public JSONObject retriveSavedQueryValuesBySaveQueryID(int saveQueryID) {

		JSONObject values = new JSONObject();

		JSONObject object = null;

		JSONArray array = new JSONArray();

		int check = 0;

		try {

			connection = getConnection();
			
			// Split tableName variable by ' AS ' in order to get only table name
			String retriveSavedQueryValuesBySaveQueryIDQuery = QueryMaker.RETRIVE_SavedQuery_Values_By_SaveQueryID;

			preparedStatement = connection.prepareStatement(retriveSavedQueryValuesBySaveQueryIDQuery);

			preparedStatement.setInt(1, saveQueryID);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				object = new JSONObject();

				String[] columnValue =resultSet.getString("columnName").split("\\.");
				String tableStr = resultSet.getString("tableName");

				String SelectedValue = "";
				String columns ="";
				
				if(tableStr.contains(",")) {
				
					String[] tableString = tableStr.split(", ");
					
					System.out.println(", tableString::"+tableString);
					
					String Value = tableString[tableString.length -1];
					
					System.out.println(", Value::"+Value);
					
					String[] tableValue =Value.split(" AS ");
					
					System.out.println(", tableValue::"+tableValue);
					
					SelectedValue = retriveSelectedValueList(tableValue[0]);
					
					System.out.println(", SelectedValue::"+SelectedValue);
					
					columns = displayColumnName(tableValue[0], columnValue[1]);
					
					System.out.println(", columns::"+columns);
				}else {
					String[] tableValue =tableStr.split(" AS ");
					
					System.out.println("tableValue: "+tableValue[1]);
					
					SelectedValue = retriveSelectedValueList(tableValue[0]);
					
					System.out.println("SelectedValue: "+SelectedValue);
					
					columns = displayColumnName(tableValue[0], columnValue[1]);
					
					System.out.println("columns::"+columns);
				}
				
				object.put("check", check);
				object.put("columns", columns);
				object.put("columnsDataType", resultSet.getString("columnDataType"));
				object.put("tableName", tableStr);
				object.put("criteria", resultSet.getString("criteria"));
				object.put("selectValue", resultSet.getString("selectValue"));
				object.put("searchValue", retriveSearchTextValues(resultSet.getInt("id")));
				object.put("savedQueryParameterID", resultSet.getInt("id"));
				object.put("selectedValueList", SelectedValue);
				
				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				
				array.add(object);

				values.put("Release", array);

			}

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
		/*	EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve Saved Query Values: Exception");*/

			exception.printStackTrace();

			object = new JSONObject();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;

	}

	public List<String> retrieveSavedQuerySearchText(int saveQueryID) {

		List<String> list = new ArrayList<String>(); 
		
		try {

			connection = getConnection();

			String retrieveSavedQuerySearchTextQuery = QueryMaker.RETRIVE_SavedQuery_SearchText;

			preparedStatement = connection.prepareStatement(retrieveSavedQuerySearchTextQuery);

			preparedStatement.setInt(1, saveQueryID);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getString("searchValue"));
			}

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve SavedQuery SearchText: Exception");

			exception.printStackTrace();
			
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}
	
	public HashMap<String, String> retrieveSavedQueryDetailsList(int saveQueryID) {

		HashMap<String, String> map = new HashMap<String, String>();

		configurationUtil = new ConfigurationUtil();

		try {

			/*For selected values*/
			connection = getConnection();
			
			String retrieveSavedQuerySelectListQuery = "select group_concat(DISTINCT(selectValue)) AS selectedValues from SavedQueryParameter where "
													+ "savedQueryID = "+saveQueryID+" and selectValue <> ''";

			preparedStatement = connection.prepareStatement(retrieveSavedQuerySelectListQuery);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				map.put("selectedValues", resultSet.getString("selectedValues"));
			}
			
			resultSet.close();
			preparedStatement.close();
			
			
			String retrieveSavedQueryTableNameListQuery = "select group_concat(DISTINCT(tableName) SEPARATOR ', ') AS tableName from SavedQueryParameter where "
													+ "savedQueryID = "+saveQueryID+" and tableName <> ''";
			
			preparedStatement = connection.prepareStatement(retrieveSavedQueryTableNameListQuery);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				map.put("tableNameValues", resultSet.getString("tableName"));
			}
			
			resultSet.close();
			preparedStatement.close();
			
			
			String retrieveSavedQueryColCriteriaListQuery = "select group_concat(CONCAT(columnName,' ', criteria) SEPARATOR ' AND ') AS colCriteria from "
													+ "SavedQueryParameter where savedQueryID = "+saveQueryID+" and columnName <> '' AND criteria <> ''";
			
			preparedStatement = connection.prepareStatement(retrieveSavedQueryColCriteriaListQuery);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				map.put("colCriteriaValues", resultSet.getString("colCriteria"));
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			/*// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve SavedQuery Details List: Exception");*/

			exception.printStackTrace();
		}

		return map;
	}

	
	public List<PatientForm> retrieveSavedQueryList(int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		int srNo = 1;

		try {

			connection = getConnection();

			String retrieveSavedQueryListQuery = QueryMaker.RETRIEVE_SAVED_QUERY;

			preparedStatement = connection.prepareStatement(retrieveSavedQueryListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PatientForm();

				form.setQueryTitle(resultSet.getString("title"));
				form.setFirstName(resultSet.getString("username"));
				form.setSaveQueryID(resultSet.getInt("id"));
				form.setSavedQuery(resultSet.getString("query"));
				form.setInitialWhere(resultSet.getString("joinCondition"));
				form.setSrNo(srNo);

				srNo++;

				list.add(form);

			}

		} catch (Exception exception) {

			/*
			 * StringWriter stringWriter = new StringWriter();
			 * 
			 * exception.printStackTrace(new PrintWriter(stringWriter));
			 * 
			 * // calling exception mail send method to send mail about the exception
			 * details // on info@kovidbioanalytics.com EmailUtil emailUtil = new
			 * EmailUtil(); emailUtil.sendExceptionInfoEmail(stringWriter.toString(),
			 * "retrieve Saved Query List: Exception");
			 */

			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	
	public String updateSavedQueryTitle(int saveQueryID, String queryTitle) {

		try {

			connection = getConnection();

			String updateSavedQueryTitleQuery = QueryMaker.Update_SAVED_QUERY;

			preparedStatement = connection.prepareStatement(updateSavedQueryTitleQuery);

			preparedStatement.setString(1, queryTitle);
			preparedStatement.setInt(2, saveQueryID);
			
			preparedStatement.execute();

			status = "success";

			System.out.println("Save query title updated successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}
	
	public String updateSavedQueryParameterValues(int saveQueryParameterValueID, String searchText) {

		try {

			connection = getConnection();

			String updateSavedQueryParameterValuesQuery = QueryMaker.Update_SAVED_QUERY_PARAMETER_VALUES;

			preparedStatement = connection.prepareStatement(updateSavedQueryParameterValuesQuery);

			preparedStatement.setString(1, searchText);
			preparedStatement.setInt(2, saveQueryParameterValueID);
			
			preparedStatement.execute();

			status = "success";

			System.out.println("Save query parameter values details updated successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}
	
	public String updateSavedQueryParameter(int saveQueryParameterID, String selectedValue) {

		try {

			connection = getConnection();

			String updateSavedQueryParameterQuery = QueryMaker.Update_SAVED_QUERY_PARAMETER;

			preparedStatement = connection.prepareStatement(updateSavedQueryParameterQuery);

			preparedStatement.setString(1, selectedValue);
			preparedStatement.setInt(2, saveQueryParameterID);
			
			preparedStatement.execute();

			status = "success";

			System.out.println("Save query parameter details updated successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}
	
	public List<PatientForm> retrieveProductTotalNetStock(int clinicID, String status) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retrieveProductTotalNetStockQuery = QueryMaker.RETRIEVE_PRODUCT_TOTAL_NET_STOCK_LIST;

			preparedStatement = connection.prepareStatement(retrieveProductTotalNetStockQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, status);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setTradeName(resultSet.getString("tradeName"));
				patientForm.setProductQuantity(resultSet.getDouble("netStock"));
				patientForm.setProductID(resultSet.getInt("productID"));
				patientForm.setActivityStatus(resultSet.getString("status"));

				list.add(patientForm);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveStockReceiptListByProductID(int productID, int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			String retrieveStockReceiptListByProductIDQuery = QueryMaker.RETRIEVE_STOCK_RECEIPT_DETAILS_BY_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retrieveStockReceiptListByProductIDQuery);

			preparedStatement.setInt(3, clinicID);
			preparedStatement.setString(2, ActivityStatus.NOT_EMPTY);
			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setReceiptNo(resultSet.getString("receiptNo"));
				patientForm.setReceiptDate(simpleDateFormat.format(resultSet.getTimestamp("receiptDate")));
				patientForm.setNewDrugQuantityName(resultSet.getDouble("netStock"));

				list.add(patientForm);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public int retrieveSavedQuewryParameterID(String tableName, String columnName, int savedQuewryID) {

		int savedQuewryParameterID = 0;
		
		try {

			connection = getConnection();

			String retrieveSavedQuewryParameterIDQuery = QueryMaker.RETRIVE_SAVED_QUERY_PARAMETER_ID;

			preparedStatement = connection.prepareStatement(retrieveSavedQuewryParameterIDQuery);

			preparedStatement.setString(1, tableName);
			preparedStatement.setString(2, columnName);
			preparedStatement.setInt(3, savedQuewryID);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				savedQuewryParameterID = resultSet.getInt("id");
			}

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve savedQueryParameterID: Exception");

			exception.printStackTrace();
			
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return savedQuewryParameterID;
	}

	public int retrieveSavedQuewryID(PatientForm patientForm) {

		int savedQuewryID = 0;
		
		try {

			connection = getConnection();

			String retrieveSavedQuewryIDQuery = QueryMaker.RETRIVE_SAVED_QUERY_ID;

			preparedStatement = connection.prepareStatement(retrieveSavedQuewryIDQuery);

			preparedStatement.setString(1, patientForm.getQueryTitle());
			preparedStatement.setInt(2, patientForm.getClinicID());
			preparedStatement.setInt(3, patientForm.getUserID());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				savedQuewryID = resultSet.getInt("id");
			}

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
		//	emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve savedQueryID: Exception");

			exception.printStackTrace();
			
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return savedQuewryID;
	}


	public String insertSavedQueryParameter(String tableName, String columnName, String dataType,
			String selectedValues, String criteria, int savedQuewryID) {

		try {

			connection = getConnection();

			String insertSavedQueryParameterQuery = QueryMaker.INSERT_SAVED_QUERY_PARAMETER;

			preparedStatement = connection.prepareStatement(insertSavedQueryParameterQuery);

			preparedStatement.setString(1, tableName);
			preparedStatement.setString(2, columnName);
			preparedStatement.setString(3, dataType);
			preparedStatement.setString(4, selectedValues);
			preparedStatement.setString(5, criteria);
			preparedStatement.setInt(6, savedQuewryID);
		
			preparedStatement.execute();

			status = "success";

			System.out.println("Save query Parameter inserted successfully.");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			//EmailUtil emailUtil = new EmailUtil();
			//emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Insert Saved Query Parameter: Exception");

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}
	
	public JSONObject retriveColumnNamesForSavedQueryValuesBySaveQueryID(String parentTableName) {

		JSONObject values = new JSONObject();

		JSONObject object = null;

		JSONArray array = new JSONArray();

		int check = 0;

		try {

			connection = getConnection();

			String[] tableNameStr = parentTableName.split(",");
			String selectVal = "";
			for (int i = 0; i < tableNameStr.length; i++) {
				String[] tableNameVal = tableNameStr[i].trim().split(" AS ");
				
				// Split tableName variable by ' AS ' in order to get only table name
				String retriveColumnNamesForSavedQueryValuesBySaveQueryIDQuery = QueryMaker.RETRIVE_ColumnNames_Of_Table;

				preparedStatement = connection.prepareStatement(retriveColumnNamesForSavedQueryValuesBySaveQueryIDQuery);

				preparedStatement.setString(1, tableNameVal[1]);
				preparedStatement.setString(2, tableNameVal[0]);
				
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					check = 1;

					object = new JSONObject();
					selectVal = selectVal + ","+ resultSet.getString("selectedValues");
				}
			}
			
			if(selectVal.startsWith(",")) {
				selectVal = selectVal.substring(1);
			}
			
			object.put("check", check);
			object.put("selectedValueList", selectVal);
			
			array.add(object);

			values.put("Release", array);

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				
				array.add(object);

				values.put("Release", array);

			}

		} catch (Exception exception) {

			/*
			 * StringWriter stringWriter = new StringWriter();
			 * 
			 * exception.printStackTrace(new PrintWriter(stringWriter));
			 */
			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
		/*	EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve Saved Query Values: Exception");*/

			exception.printStackTrace();

			object = new JSONObject();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;

	}

}
