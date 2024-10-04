package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.ClinicDAOInf;
import com.edhanvantari.form.ClinicForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;

public class ClinicDAOImpl extends DAOConnection implements ClinicDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;
	String status = "error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#verifyPracticeExists(java.lang.
	 * String)
	 */
	public JSONObject verifyPracticeExists(String practiceName) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		boolean result = false;

		try {
			connection = getConnection();

			String verifyPracticeExistsQuery = QueryMaker.RETRIEVE_PRACTICE_BY_PRACTICE_NAME;

			preparedStatement = connection.prepareStatement(verifyPracticeExistsQuery);

			preparedStatement.setString(1, practiceName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				result = true;

			}

			/*
			 * Checking whether practice with the same name exists, if result is true then
			 * it exists else does not
			 */
			if (result) {

				object.put("Practice", "exist");

				array.add(object);

				values.put("Release", array);

				resultSet.close();
				preparedStatement.close();

				// return values;

			} else {

				object.put("Practice", "non-exist");

				array.add(object);

				values.put("Release", array);

				resultSet.close();
				preparedStatement.close();

				// return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying practice already exists or not");

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertPractice(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, int)
	 */
	public String insertPractice(String practiceName, String practiceSuffix, String consentDocumentPath,
			String sessionTimeout, String invalidAttempts, String bucketName, int facilityDashboard,
			int thirdPartyAPIIntegration) {

		try {

			connection = getConnection();

			String insertPracticeQuery = QueryMaker.INSERT_PRACTICE;

			preparedStatement = connection.prepareStatement(insertPracticeQuery);

			preparedStatement.setString(1, practiceName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, practiceSuffix);
			preparedStatement.setString(4, consentDocumentPath);
			preparedStatement.setString(5, sessionTimeout);
			preparedStatement.setString(6, invalidAttempts);
			preparedStatement.setString(7, bucketName);
			preparedStatement.setInt(8, facilityDashboard);
			preparedStatement.setInt(9, thirdPartyAPIIntegration);

			preparedStatement.execute();

			System.out.println("Practice details successfully inserted.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while inserting Practice details into Practice table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#retrieveLastEneteredPracticeID(java.
	 * lang.String)
	 */
	public int retrieveLastEneteredPracticeID(String practiceName) {
		int practiceID = 0;

		try {
			connection = getConnection();

			String retrieveLastEneteredPracticeIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveLastEneteredPracticeIDQuery);

			preparedStatement.setString(1, practiceName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				practiceID = resultSet.getInt("id");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving last entered practiceID from Practice table due to:::"
							+ exception.getMessage());
		}
		return practiceID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertPracticeConfigDetails(com.
	 * edhanvantari.form.ClinicForm, int)
	 */
	public String insertPracticeConfigDetails(ClinicForm clinicForm, int practiceID) {
		try {
			connection = getConnection();

			String insertPracticeConfigDetailsQuery = QueryMaker.INSERT_PRACTICE_CONFIGURATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertPracticeConfigDetailsQuery);

			preparedStatement.setString(1, clinicForm.getTagline());
			preparedStatement.setString(2, clinicForm.getWebsite());
			preparedStatement.setString(3, clinicForm.getPathToLogo());
			preparedStatement.setString(4, clinicForm.getConsentDocuments());
			preparedStatement.setString(5, clinicForm.getPageSize());
			preparedStatement.setString(6, clinicForm.getLettrHeadImage());
			preparedStatement.setString(7, clinicForm.getSessionTimeout());
			preparedStatement.setString(8, clinicForm.getInvalidAttempts());
			preparedStatement.setString(9, clinicForm.getCurrencyForBilling());
			preparedStatement.setInt(10, practiceID);
			preparedStatement.setString(11, clinicForm.getPatientForm());
			preparedStatement.setString(12, clinicForm.getCurrencyForBilling1());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted practice Configuration details");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting practice Configuration details into PracticeConfiguration table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertClinic(java.lang.String, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String insertClinic(String clinicName, int practiceID, String clinicSuffix, String pageSize,
			String clinicPatientForm, String clinicPhone) {

		try {

			connection = getConnection();

			String insertClinicQuery = QueryMaker.INSERT_CLINIC;

			preparedStatement = connection.prepareStatement(insertClinicQuery);

			preparedStatement.setString(1, clinicName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setString(4, clinicSuffix);
			preparedStatement.setString(5, pageSize);
			preparedStatement.setString(6, clinicPatientForm);
			preparedStatement.setString(7, clinicPhone);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted clinic details.");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting clinic details into Clinic table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveLastEnteredClinicID(int)
	 */
	public int retrieveLastEnteredClinicID(int practiceID) {

		int clinicID = 0;
		System.out.println("practiceID::" + practiceID);

		try {
			System.out.println("practiceID::" + practiceID);
			connection = getConnection();

			String retrieveLastEnteredClinicID = QueryMaker.RETRIEVE_LAST_CLINIC_ID_FOR_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredClinicID);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entered clinic ID for particular practice ID due to:::"
							+ exception.getMessage());
		}

		return clinicID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#searchPracticeList(java.lang.String)
	 */
	public List<ClinicForm> searchPracticeList(String searchPracticeName, int practiceID) {

		List<ClinicForm> clinicList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String searchPracticeListQuery = QueryMaker.SEARCH_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(searchPracticeListQuery);

			if (searchPracticeName.contains(" ")) {
				searchPracticeName = searchPracticeName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchPracticeName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setPracticeID(resultSet.getInt("id"));
				clinicForm.setPracticeName(resultSet.getString("name"));
				clinicForm.setActicityStatus(resultSet.getString("activityStatus"));
				clinicForm.setPracticeSuffix(resultSet.getString("suffix"));
				clinicForm.setSearchPracticeName(searchPracticeName);
				if (resultSet.getString("url") == null || resultSet.getString("url") == "") {
					clinicForm.setPracticeURL("NA");
				} else if (resultSet.getString("url").isEmpty()) {
					clinicForm.setPracticeURL("NA");
				} else {
					clinicForm.setPracticeURL(resultSet.getString("url"));
				}

				clinicList.add(clinicForm);
			}

			status = "success";
			System.out.println("Successfully retrived practice detail from table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving practice detail from table Practice due to:::"
					+ exception.getMessage());
		}
		return clinicList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrivePracticeList()
	 */
	public List<ClinicForm> retrivePracticeList(int practiceID) {

		List<ClinicForm> clinicList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String retrivePracticeListQuery = QueryMaker.RETRIEVE_ALL_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(retrivePracticeListQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setPracticeID(resultSet.getInt("id"));
				clinicForm.setPracticeName(resultSet.getString("name"));
				clinicForm.setActicityStatus(resultSet.getString("activityStatus"));
				clinicForm.setPracticeSuffix(resultSet.getString("suffix"));

				if (resultSet.getString("url") == null || resultSet.getString("url") == "") {
					clinicForm.setPracticeURL("NA");
				} else if (resultSet.getString("url").isEmpty()) {
					clinicForm.setPracticeURL("NA");
				} else {
					clinicForm.setPracticeURL(resultSet.getString("url"));
				}

				clinicList.add(clinicForm);
			}

			status = "success";
			System.out.println("Successfully retrived practice detail from table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving practice detail from table Practice due to:::"
					+ exception.getMessage());
		}
		return clinicList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrivePracticeByPracticeID(int)
	 */
	public List<ClinicForm> retrivePracticeByPracticeID(int practiceID) {

		List<ClinicForm> list = new ArrayList<ClinicForm>();

		ClinicForm clinicForm = new ClinicForm();

		try {

			connection = getConnection();

			String retrievePracticeDetailsQuery = QueryMaker.RETRIEVE_PRACTICE_DETAILS;
			String retrieveCommunicationIDQuery = QueryMaker.RETRIEVE_COMMUNICATION_ID_BY_PRACTICE_ID;

			/*
			 * Retrieving practice details from Practice table
			 */
			preparedStatement = connection.prepareStatement(retrievePracticeDetailsQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm.setPracticeID(resultSet.getInt("id"));
				clinicForm.setPracticeName(resultSet.getString("name"));
				clinicForm.setPracticeSuffix(resultSet.getString("suffix"));
				clinicForm.setConsentDocuments(resultSet.getString("consentDocumentPath"));
				clinicForm.setSessionTimeout(resultSet.getString("sessionTimeOut"));
				clinicForm.setInvalidAttempts(resultSet.getString("invalidAttempts"));
				clinicForm.setBucketName(resultSet.getString("bucketName"));
				clinicForm.setFacilityDashboard(resultSet.getInt("facilityDashboard"));
				clinicForm.setThirdPartyAPIIntegration(resultSet.getInt("thirdPartyAPIIntegration"));
			}

			/*
			 * Retrieving communication ID based on practiceID from Communication table
			 */
			preparedStatement2 = connection.prepareStatement(retrieveCommunicationIDQuery);

			preparedStatement2.setInt(1, practiceID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				clinicForm.setCommunicationID(resultSet2.getInt("id"));
				clinicForm.setEmailNotificationsTo(resultSet2.getString("emailTo"));
				clinicForm.setSendEmailsFrom(resultSet2.getString("emailFrom"));
				clinicForm.setEmailsFromPassword(resultSet2.getString("emailFromPass"));
				clinicForm.setSmsUsername(resultSet2.getString("smsUsername"));
				clinicForm.setSmsPassword(resultSet2.getString("smsPassword"));
				clinicForm.setSmsURL(resultSet2.getString("smsURL"));
				clinicForm.setSmsSenderID(resultSet2.getString("smsSenderID"));
				clinicForm.setSmsApiKey(resultSet2.getString("smsApiKey"));

			}

			list.add(clinicForm);

			resultSet2.close();
			preparedStatement2.close();

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving practice detail For edit due to:::" + exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveClinicListByPracticeID(int)
	 */
	public List<ClinicForm> retrieveClinicListByPracticeID(int practiceID) {

		List<ClinicForm> list = new ArrayList<ClinicForm>();

		ClinicForm clinicForm = null;

		try {

			connection = getConnection();

			String retrieveClinicListByPracticeIDQuery = QueryMaker.RETRIEVE_CLINIC_ID_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicListByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm = new ClinicForm();

				clinicForm.setClinicID(resultSet.getInt("id"));
				clinicForm.setClinicName(resultSet.getString("name"));
				clinicForm.setClinicSuffixName(resultSet.getString("suffix"));
				clinicForm.setClinicActivityStatus(resultSet.getString("activityStatus"));
				clinicForm.setClinicPhoneNo(resultSet.getString("phoneNo"));

				list.add(clinicForm);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving clinic detail based on practiceID For edit due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#deleteClinicRow(int)
	 */
	public JSONObject deleteClinicRow(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteClinicRowQuery = QueryMaker.DELETE_CLINIC_ROW_BY_ID;

			preparedStatement = connection.prepareStatement(deleteClinicRowQuery);

			preparedStatement.setInt(1, clinicID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();
			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject disablePlanDetails(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String disablePlanRowQuery = QueryMaker.DISABLE_PLAN_ROW_BY_ID;

			preparedStatement = connection.prepareStatement(disablePlanRowQuery);

			preparedStatement.setInt(1, practiceID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#updatePractice(java.lang.String,
	 * int, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, int)
	 */
	public String updatePractice(String practiceName, int practiceID, String practiceSuffix, String consentDocumentPath,
			String sessionTimeout, String invalidAttempts, String bucketName, int facilityDashboard,
			int thirdPartyAPIIntegration) {

		try {

			connection = getConnection();

			String updatePracticeQuery = QueryMaker.UPDATE_PRACTICE;

			preparedStatement = connection.prepareStatement(updatePracticeQuery);

			preparedStatement.setString(1, practiceName);
			preparedStatement.setString(2, practiceSuffix);
			preparedStatement.setString(3, consentDocumentPath);
			preparedStatement.setString(4, sessionTimeout);
			preparedStatement.setString(5, invalidAttempts);
			preparedStatement.setString(6, bucketName);
			preparedStatement.setInt(7, facilityDashboard);
			preparedStatement.setInt(8, thirdPartyAPIIntegration);
			preparedStatement.setInt(9, practiceID);

			preparedStatement.execute();

			System.out.println("Practice details successfully updated.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while updating Practice details from Practice table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	public String updatePracticeConfigDetails(ClinicForm clinicForm, int practiceID) {
		try {
			connection = getConnection();

			String updatePracticeConfigDetailsQuery = QueryMaker.UPDATE_PRACTICE_CONFIGURATION_DETAILS;

			preparedStatement = connection.prepareStatement(updatePracticeConfigDetailsQuery);

			preparedStatement.setString(1, clinicForm.getTagline());
			preparedStatement.setString(2, clinicForm.getWebsite());
			preparedStatement.setString(3, clinicForm.getPathToLogo());
			preparedStatement.setString(4, clinicForm.getConsentDocuments());
			preparedStatement.setString(5, clinicForm.getPageSize());
			preparedStatement.setString(6, clinicForm.getLettrHeadImage());
			preparedStatement.setString(7, clinicForm.getSessionTimeout());
			preparedStatement.setString(8, clinicForm.getInvalidAttempts());
			preparedStatement.setString(9, clinicForm.getCurrencyForBilling());
			preparedStatement.setInt(10, practiceID);
			preparedStatement.setString(11, clinicForm.getPatientForm());
			preparedStatement.setString(12, clinicForm.getCurrencyForBilling1());
			preparedStatement.setInt(13, clinicForm.getPracticeConfID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated practice Configuration details");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating practice Configuration details from PracticeConfiguration table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#insertCalendarDetails(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, int, java.lang.String)
	 */
	public String insertCalendarDetails(String clinicStart, String clinicEnd, String breakStart1, String breakEnd1,
			String breakStart2, String breakEnd2, int clinicID, String workdays) {

		try {

			connection = getConnection();

			String insertCalendarDetailsQuery = QueryMaker.INSERT_CLINIC_CALENDAR_DETAILS;

			preparedStatement = connection.prepareStatement(insertCalendarDetailsQuery);

			preparedStatement.setString(1, clinicStart);
			preparedStatement.setString(2, clinicEnd);
			preparedStatement.setString(3, breakStart1);
			preparedStatement.setString(4, breakEnd1);
			preparedStatement.setString(5, breakStart2);
			preparedStatement.setString(6, breakEnd2);
			preparedStatement.setInt(7, clinicID);
			preparedStatement.setString(8, workdays);

			preparedStatement.execute();

			status = "success";

			System.out.println("Calendar details inserted successfully.");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting calendar details into Calendar table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#deleteCalendarDetails(int)
	 */
	public void deleteCalendarDetails(int clinicID) {

		try {

			connection = getConnection();

			String deleteCalendarDetailsQuery = QueryMaker.DELETE_CALENDAR_DETAILS;

			preparedStatement = connection.prepareStatement(deleteCalendarDetailsQuery);

			preparedStatement.setInt(1, clinicID);

			preparedStatement.executeUpdate();

			System.out.println("Calendar details deleted successfully.");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting calendar details from Calendar table due to:::"
					+ exception.getMessage());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertVisitType(com.edhanvantari.
	 * form.ClinicForm)
	 */
	public String insertVisitType(ClinicForm clinicForm, String finalFileName) {

		try {

			connection = getConnection();

			String insertVisitTypeQuery = QueryMaker.INSERT_VISIT_TYPE_DETAILS;

			preparedStatement = connection.prepareStatement(insertVisitTypeQuery);

			preparedStatement.setString(1, clinicForm.getVisitTypeName());
			preparedStatement.setInt(2, clinicForm.getVisitDuration());
			preparedStatement.setString(3, clinicForm.getBillingType());
			preparedStatement.setInt(4, clinicForm.getClinicID());
			preparedStatement.setInt(5, clinicForm.getNewVisitFlag());
			preparedStatement.setString(6, clinicForm.getWorkflowOPDForm());
			preparedStatement.setDouble(7, clinicForm.getConsultationCharge());
			preparedStatement.setString(8, clinicForm.getCurrency());
			preparedStatement.setString(9, clinicForm.getCareType());
			preparedStatement.setInt(10, clinicForm.getIsDischarge());
			preparedStatement.setInt(11, clinicForm.getHasConsent());
			preparedStatement.setString(12, finalFileName);
			preparedStatement.setInt(13, clinicForm.getNextVisitDays());

			preparedStatement.execute();

			status = "success";

			System.out.println("Visit type inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting visit ttype details into PVVisitType due to:::"
					+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#retrieveVisitTypeIDByName(java.lang.
	 * String)
	 */
	public int retrieveVisitTypeIDByName(String visitTypeName) {

		int visitTypeID = 0;

		try {

			connection = getConnection();

			String retrieveVisitTypeIDByNameQuery = QueryMaker.RETRIEVE_VISIT_TYPE_ID_BY_NAME;

			preparedStatement = connection.prepareStatement(retrieveVisitTypeIDByNameQuery);

			preparedStatement.setString(1, visitTypeName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitTypeID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visit type ID based on visitTypeName due to:::"
					+ exception.getMessage());

		}

		return visitTypeID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#searchVisitType(java.lang.String,
	 * int)
	 */
	public List<ClinicForm> searchVisitType(String searchVisitTypeName, int practiceID) {

		List<ClinicForm> visitTypeListList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String searchVisitTypeQuery = QueryMaker.SEARCH_VISIT_TYPE_LIST;

			preparedStatement = connection.prepareStatement(searchVisitTypeQuery);

			if (searchVisitTypeName.contains(" ")) {
				searchVisitTypeName = searchVisitTypeName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchVisitTypeName + "%");
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, "%" + searchVisitTypeName + "%");
			preparedStatement.setInt(4, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setVisitTypeID(resultSet.getInt("id"));
				clinicForm.setVisitTypeName(resultSet.getString("visitType"));
				clinicForm.setBillingType(resultSet.getString("billingType"));
				clinicForm.setWorkflowOPDForm(resultSet.getString("formName"));
				clinicForm.setPracticeName(resultSet.getString("practice"));
				clinicForm.setSearchPracticeName(searchVisitTypeQuery);

				visitTypeListList.add(clinicForm);
			}

			status = "success";
			// System.out.println("Successfully retrived practice detail from
			// table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Visit type details from PVVisitType table due to:::"
					+ exception.getMessage());
		}
		return visitTypeListList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveAllVisitTypeList(int)
	 */
	public List<ClinicForm> retrieveAllVisitTypeList(int practiceID) {

		List<ClinicForm> visitTypeListList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String retrieveAllVisitTypeListQuery = QueryMaker.RETRIEVE_ALL_VISIT_TYPE_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllVisitTypeListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setVisitTypeID(resultSet.getInt("id"));
				clinicForm.setVisitTypeName(resultSet.getString("visitType"));
				clinicForm.setBillingType(resultSet.getString("billingType"));
				clinicForm.setWorkflowOPDForm(resultSet.getString("formName"));
				clinicForm.setPracticeName(resultSet.getString("practice"));

				visitTypeListList.add(clinicForm);
			}

			status = "success";
			// System.out.println("Successfully retrived practice detail from
			// table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Visit type details from PVVisitType table due to:::"
					+ exception.getMessage());
		}
		return visitTypeListList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveVisitTypeListByVisitTypeID(
	 * int)
	 */
	public List<ClinicForm> retrieveVisitTypeListByVisitTypeID(int visitTypeID) {

		List<ClinicForm> list = new ArrayList<ClinicForm>();

		ClinicForm clinicForm = new ClinicForm();

		try {

			connection = getConnection();

			String retrieveVisitTypeListByVisitTypeIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_VISIT_TYPE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitTypeListByVisitTypeIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm.setVisitTypeID(resultSet.getInt("id"));
				clinicForm.setVisitTypeName(resultSet.getString("name"));
				clinicForm.setVisitDuration(resultSet.getInt("visitDuration"));
				clinicForm.setBillingType(resultSet.getString("billingType"));
				clinicForm.setCareType(resultSet.getString("careType"));
				clinicForm.setNewVisitFlag(resultSet.getInt("newVisit"));
				clinicForm.setClinicID(resultSet.getInt("clinicID"));
				clinicForm.setWorkflowOPDForm(resultSet.getString("formName"));
				clinicForm.setConsultationCharge(resultSet.getDouble("consultationCharges"));
				clinicForm.setCurrency(resultSet.getString("currency"));
				clinicForm.setCareType(resultSet.getString("careType"));
				clinicForm.setIsDischarge(resultSet.getInt("isDischarge"));
				clinicForm.setHasConsent(resultSet.getInt("hasConsent"));
				clinicForm.setConsentDocDBName(resultSet.getString("consentDocument"));
				clinicForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
				clinicForm.setLabVisit(resultSet.getInt("labVisit"));

				list.add(clinicForm);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving visit type details based on visit type ID due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#updateVisitType(com.edhanvantari.
	 * form.ClinicForm)
	 */
	public String updateVisitType(ClinicForm clinicForm, String FileName) {

		try {

			connection = getConnection();

			String updateVisitTypeQuery = QueryMaker.UPDATE_VISIT_TYPE_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitTypeQuery);

			preparedStatement.setString(1, clinicForm.getVisitTypeName());
			preparedStatement.setInt(2, clinicForm.getVisitDuration());
			preparedStatement.setString(3, clinicForm.getBillingType());
			preparedStatement.setString(4, clinicForm.getWorkflowOPDForm());
			preparedStatement.setInt(5, clinicForm.getClinicID());
			preparedStatement.setInt(6, clinicForm.getNewVisitFlag());
			preparedStatement.setDouble(7, clinicForm.getConsultationCharge());
			preparedStatement.setString(8, clinicForm.getCurrency());
			preparedStatement.setString(9, clinicForm.getCareType());
			preparedStatement.setInt(10, clinicForm.getIsDischarge());
			preparedStatement.setInt(11, clinicForm.getHasConsent());
			preparedStatement.setString(12, FileName);
			preparedStatement.setInt(13, clinicForm.getNextVisitDays());
			preparedStatement.setInt(14, clinicForm.getVisitTypeID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Visit type updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while udpating visit ttype details into PVVisitType due to:::"
					+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#verifyVisitExistsForVisitTypeID(int)
	 */
	public boolean verifyVisitExistsForVisitTypeID(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyVisitExistsForVisitTypeIDQuery = QueryMaker.VERIFY_VISIT_EXISTS_FOR_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(verifyVisitExistsForVisitTypeIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying whether visit exists for visitTypeID due to:::"
					+ exception.getMessage());

		}

		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#deleteVisitTypeByVisitTypeID(int)
	 */
	public String deleteVisitTypeByVisitTypeID(int visitTypeID) {

		try {

			connection = getConnection();

			String deleteVisitTypeByVisitTypeIDQuery = QueryMaker.DELETE_VISIT_TYPE_BY_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(deleteVisitTypeByVisitTypeIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Visit type details deleted successsfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting visit type details for particular visittypeID from PVVisitType table due to:::"
							+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveVisitTypeNameByID(int)
	 */
	public String retrieveVisitTypeNameByID(int visitTypeID) {

		String visitTypeName = "";

		try {

			connection = getConnection();

			String retrieveVisitTypeNameByIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_NAME_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitTypeNameByIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitTypeName = resultSet.getString("name");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving visit type name based on id from PVVisitType table due to:::"
							+ exception.getMessage());
		}

		return visitTypeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertCommunicationDetails(com.
	 * edhanvantari.form.ClinicForm, int)
	 */
	public String insertCommunicationDetails(ClinicForm clinicForm, int practiceID) {

		try {

			connection = getConnection();

			String insertCommunicationDetailsQuery = QueryMaker.INSERT_COMMUNICATION;

			preparedStatement = connection.prepareStatement(insertCommunicationDetailsQuery);

			preparedStatement.setInt(1, clinicForm.getApptSchedlSMS());
			preparedStatement.setInt(2, clinicForm.getApptUpdatedSMS());
			preparedStatement.setInt(3, clinicForm.getApptCancelledSMS());
			preparedStatement.setInt(4, clinicForm.getApptSchedlEmail());
			preparedStatement.setInt(5, clinicForm.getApptUpdatedEmail());
			preparedStatement.setInt(6, clinicForm.getApptCancelledEmail());
			preparedStatement.setInt(7, clinicForm.getSendBillEmail());
			preparedStatement.setInt(8, clinicForm.getSendBillSMS());
			preparedStatement.setInt(9, clinicForm.getSendPrescEmail());
			preparedStatement.setString(10, clinicForm.getEmailNotificationsTo());
			preparedStatement.setString(11, clinicForm.getSendEmailsFrom());
			preparedStatement.setString(12, clinicForm.getEmailsFromPassword());
			preparedStatement.setInt(13, clinicForm.getSendPrescSMS());
			preparedStatement.setInt(14, clinicForm.getSendThanksToRefDocSMS());
			preparedStatement.setInt(15, clinicForm.getSendThanksToRefDocEmail());
			preparedStatement.setInt(16, clinicForm.getWelcomeMsgSMS());
			preparedStatement.setInt(17, clinicForm.getWelcomeMsgEmail());
			preparedStatement.setInt(18, practiceID);
			preparedStatement.setInt(19, clinicForm.getEmailReviewForm());
			preparedStatement.setInt(20, clinicForm.getSmsReviewForm());
			preparedStatement.setString(21, clinicForm.getReviewForm());
			preparedStatement.setInt(22, clinicForm.getSmsInventory());
			preparedStatement.setInt(23, clinicForm.getEmailInventory());
			preparedStatement.setInt(24, clinicForm.getSmsDailyAppt());
			preparedStatement.setInt(25, clinicForm.getEmailDailyAppt());
			preparedStatement.setString(26, clinicForm.getSmsUsername());
			preparedStatement.setString(27, clinicForm.getSmsPassword());
			preparedStatement.setString(28, clinicForm.getSmsURL());
			preparedStatement.setString(29, clinicForm.getSmsSenderID());
			preparedStatement.setString(30, clinicForm.getSmsApiKey());
			preparedStatement.setInt(31, clinicForm.getPatLoginCredSMS());
			preparedStatement.setInt(32, clinicForm.getPatLoginCredEmail());

			preparedStatement.execute();

			status = "success";

			System.out.println("Communication details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting commincation details into Communication table due to:::"
							+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#updateCommunicationDetails(com.
	 * edhanvantari.form.ClinicForm)
	 */
	public String updateCommunicationDetails(ClinicForm clinicForm) {

		try {

			connection = getConnection();

			String updateCommunicationDetailsQuery = QueryMaker.UPDATE_COMMUNICATION;

			preparedStatement = connection.prepareStatement(updateCommunicationDetailsQuery);

			preparedStatement.setInt(1, clinicForm.getApptSchedlSMS());
			preparedStatement.setInt(2, clinicForm.getApptUpdatedSMS());
			preparedStatement.setInt(3, clinicForm.getApptCancelledSMS());
			preparedStatement.setInt(4, clinicForm.getApptSchedlEmail());
			preparedStatement.setInt(5, clinicForm.getApptUpdatedEmail());
			preparedStatement.setInt(6, clinicForm.getApptCancelledEmail());
			preparedStatement.setInt(7, clinicForm.getSendBillEmail());
			preparedStatement.setInt(8, clinicForm.getSendBillSMS());
			preparedStatement.setInt(9, clinicForm.getSendPrescEmail());
			preparedStatement.setString(10, clinicForm.getEmailNotificationsTo());
			preparedStatement.setString(11, clinicForm.getSendEmailsFrom());
			preparedStatement.setString(12, clinicForm.getEmailsFromPassword());
			preparedStatement.setInt(13, clinicForm.getSendPrescSMS());
			preparedStatement.setInt(14, clinicForm.getSendThanksToRefDocSMS());
			preparedStatement.setInt(15, clinicForm.getSendThanksToRefDocEmail());
			preparedStatement.setInt(16, clinicForm.getWelcomeMsgSMS());
			preparedStatement.setInt(17, clinicForm.getWelcomeMsgEmail());
			preparedStatement.setInt(18, clinicForm.getPracticeID());
			preparedStatement.setInt(19, clinicForm.getEmailReviewForm());
			preparedStatement.setInt(20, clinicForm.getSmsReviewForm());
			preparedStatement.setString(21, clinicForm.getReviewForm());
			preparedStatement.setInt(22, clinicForm.getSmsInventory());
			preparedStatement.setInt(23, clinicForm.getEmailInventory());
			preparedStatement.setInt(24, clinicForm.getSmsDailyAppt());
			preparedStatement.setInt(25, clinicForm.getEmailDailyAppt());
			preparedStatement.setString(26, clinicForm.getSmsUsername());
			preparedStatement.setString(27, clinicForm.getSmsPassword());
			preparedStatement.setString(28, clinicForm.getSmsURL());
			preparedStatement.setString(29, clinicForm.getSmsSenderID());
			preparedStatement.setString(30, clinicForm.getSmsApiKey());
			preparedStatement.setInt(31, clinicForm.getPatLoginCredSMS());
			preparedStatement.setInt(32, clinicForm.getPatLoginCredEmail());
			preparedStatement.setInt(33, clinicForm.getCommunicationID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Communication details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating commincation details into Communication table due to:::"
							+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveCommunicationVal(java.lang.
	 * String, java.lang.String, int)
	 */
	public String retrieveCommunicationVal(String smsColName, String emailColName, int practiceID) {

		String checkVal = "";

		try {

			connection = getConnection();

			String retrieveCommunicationValQuery = "SELECT " + smsColName + ", " + emailColName
					+ " FROM Communication WHERE practiceID = ?";

			preparedStatement = connection.prepareStatement(retrieveCommunicationValQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString(smsColName).equals("1") && resultSet.getString(emailColName).equals("1")) {

					checkVal = "both";

				} else if (resultSet.getString(smsColName).equals("1")) {

					checkVal = "SMS";

				} else if (resultSet.getString(emailColName).equals("1")) {

					checkVal = "Email";

				}

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving values from Communication table due to:::"
					+ exception.getMessage());

			checkVal = "";

		}

		return checkVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertPatientDetails(com.
	 * edhanvantari.form.ClinicForm)
	 */
	public String insertPatientDetails(ClinicForm clinicForm) {

		try {

			connection = getConnection();

			String insertPatientDetailsQuery = QueryMaker.INSERT_PATIENT_DETAILS1;

			preparedStatement = connection.prepareStatement(insertPatientDetailsQuery);

			preparedStatement.setString(1, clinicForm.getFirstName());
			preparedStatement.setString(2, clinicForm.getMiddleName());
			preparedStatement.setString(3, clinicForm.getLastName());
			preparedStatement.setString(4, clinicForm.getMobileNo());
			preparedStatement.setString(5, clinicForm.getRegNo());
			preparedStatement.setInt(6, clinicForm.getPracticeID());

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting patient details into Patient table due to:::"
					+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#insertApppointmentDetails(com.
	 * edhanvantari.form.ClinicForm, int, int)
	 */
	public JSONObject insertApppointmentDetails(ClinicForm clinicForm, int clinicID, int apptNumber) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertApppointmentDetailsQuery = QueryMaker.INSERT_APPOINTMENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertApppointmentDetailsQuery);

			preparedStatement.setInt(1, apptNumber);

			if (clinicForm.getApptDate().substring(2, 3).equals("-")) {

				preparedStatement.setString(2, dateFormat1.format(dateFormat.parse(clinicForm.getApptDate())));

			} else {

				preparedStatement.setString(2, clinicForm.getApptDate());

			}
			preparedStatement.setString(3, clinicForm.getApptStartTime());
			preparedStatement.setString(4, clinicForm.getApptEndTime());
			preparedStatement.setString(5, ActivityStatus.BOOKED);
			preparedStatement.setInt(6, clinicForm.getPatientID());
			preparedStatement.setInt(7, clinicID);
			preparedStatement.setInt(8, clinicForm.getVisitTypeID());
			preparedStatement.setInt(9, clinicForm.getCliniciaID());
			preparedStatement.setInt(10, clinicForm.getWalkIn());

			preparedStatement.execute();

			object.put("Msg", "Success");

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("Msg", "Error");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#retrieveAppointmentNumber(int)
	 */
	public int retrieveAppointmentNumber(String apptDate) {

		int apptNo = 0;

		try {

			connection = getConnection();

			String retrieveAppointmentNumberQuery = QueryMaker.RETRIEVE_APPOINTMENT_NUMBER;

			preparedStatement = connection.prepareStatement(retrieveAppointmentNumberQuery);

			preparedStatement.setString(1, apptDate);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				apptNo = resultSet.getInt("apptNumber");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return apptNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.ClinicDAOInf#retrievePatientIDByFirstAndLastName(
	 * java.lang.String, java.lang.String)
	 */
	public int retrievePatientIDByFirstAndLastName(String fName, String lName) {

		int patientID = 0;

		try {

			connection = getConnection();

			String retrievePatientIDByFirstAndLastNameQuery = QueryMaker.RETRIEVE_PATIENT_ID_BY_FIRST_AND_LAST_NAME;

			preparedStatement = connection.prepareStatement(retrievePatientIDByFirstAndLastNameQuery);

			preparedStatement.setString(1, fName);
			preparedStatement.setString(2, lName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#
	 * retrievePatientDetailsBasedOnPatientID(int)
	 */
	public JSONObject retrievePatientDetailsBasedOnPatientID(int patientID) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		try {

			connection = getConnection();

			String retrievePatientDetailsBasedOnPatientIDQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsBasedOnPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				object.put("firstName", resultSet.getString("firstName"));
				object.put("lastName", resultSet.getString("lastName"));
				object.put("regNo", resultSet.getString("registrationNumber"));
				object.put("patientID", resultSet.getInt("id"));

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving patient details based on patientID.");

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveTabNameByFormName(String formName) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = null;

		String tabs = "";

		try {

			connection = getConnection();

			String retrieveTabNameByFormNameQuery = QueryMaker.RETRIEVE_TAB_NAME_BY_FORM_NAME;

			preparedStatement = connection.prepareStatement(retrieveTabNameByFormNameQuery);

			preparedStatement.setString(1, formName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				tabs = resultSet.getString("tabs");

			}

			/*
			 * Checking whether tabs string contains comma (,) or not, if yes, then
			 * splitting the string by comma and adding each value into JSONObejct's object
			 * variable else adding single value into JSONObejct's object variable
			 */
			if (tabs.contains(",")) {

				String tabsArray[] = tabs.split(",");

				for (int i = 0; i < tabsArray.length; i++) {

					object = new JSONObject();

					object.put("tabName", tabsArray[i]);

					array.add(object);

					values.put("Release", array);

				}

			} else {

				object = new JSONObject();

				object.put("tabName", tabs);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving tabs based on form name.");

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject updateConfirmedAppointmentStatusByPatientID(int patientID, String startTime, String endTime) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		int check = 0;

		String[] startArray = startTime.split(" ");
		String date = startArray[0];

		String start = startArray[1];

		String[] endArray = endTime.split(" ");

		String end = endArray[1];

		try {

			connection = getConnection();

			String updateConfirmedAppointmentStatusByPatientIDQuery = QueryMaker.UDPATE_APPOINTMENT_STATUS_FOR_PATIENT1;

			preparedStatement = connection.prepareStatement(updateConfirmedAppointmentStatusByPatientIDQuery);

			preparedStatement.setString(1, ActivityStatus.CONFIRMED);
			preparedStatement.setString(2, dateToBeFormatted.format(dateToBeFormatted1.parse(date)));
			preparedStatement.setInt(3, patientID);
			preparedStatement.setString(4, start);
			preparedStatement.setString(5, end);

			preparedStatement.executeUpdate();

			System.out.println("Appointment confirmed successfully.");

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject updateCancelledAppointmentStatusByPatientID(int patientID, String startTime, String endTime) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		int check = 0;

		String[] startArray = startTime.split(" ");
		String date = startArray[0];

		String start = startArray[1];

		String[] endArray = endTime.split(" ");

		String end = endArray[1];

		try {

			connection = getConnection();

			String updateCancelledAppointmentStatusByPatientIDQuery = QueryMaker.UDPATE_APPOINTMENT_STATUS_FOR_PATIENT1;

			preparedStatement = connection.prepareStatement(updateCancelledAppointmentStatusByPatientIDQuery);

			preparedStatement.setString(1, ActivityStatus.CANCELLED);
			preparedStatement.setString(2, dateToBeFormatted.format(dateToBeFormatted1.parse(date)));
			preparedStatement.setInt(3, patientID);
			preparedStatement.setString(4, start);
			preparedStatement.setString(5, end);

			preparedStatement.executeUpdate();

			System.out.println("Appointment cancelled successfully.");

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject updateAppointment(ClinicForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		int check = 0;

		String[] startArray = form.getApptStartTime().split(" ");
		String date = startArray[0];

		String prevStart = startArray[1];

		String[] endArray = form.getApptEndTime().split(" ");

		String prevEnd = endArray[1];

		String startTime = form.getClinicStartHH();

		String endTime = form.getClinicEndHH();

		try {

			connection = getConnection();

			String updateAppointmentQuery = QueryMaker.UPDATE_APPOINTMENT;

			preparedStatement = connection.prepareStatement(updateAppointmentQuery);

			System.out.println("Query: " + dateToBeFormatted.format(dateFormat.parse(form.getStartDate())) + "-"
					+ startTime + "-" + endTime + "-" + form.getPatientID() + "-"
					+ dateToBeFormatted.format(dateToBeFormatted1.parse(date)) + "-" + prevStart + "-" + prevEnd + "-"
					+ form.getVisitTypeID());

			preparedStatement.setString(1, dateToBeFormatted.format(dateFormat.parse(form.getStartDate())));
			preparedStatement.setString(2, startTime);
			preparedStatement.setString(3, endTime);
			preparedStatement.setInt(4, form.getVisitTypeID());
			preparedStatement.setInt(5, form.getPatientID());
			preparedStatement.setString(6, dateToBeFormatted.format(dateToBeFormatted1.parse(date)));
			preparedStatement.setString(7, prevStart);
			preparedStatement.setString(8, prevEnd);

			preparedStatement.executeUpdate();

			System.out.println("Appointment updated successfully.");

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveClinicCalendarList(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String clinicStart = "";
		String clinicEnd = "";
		String beakStart1 = "";
		String breakEnd1 = "";
		String breakStart2 = "";
		String breakEnd2 = "";
		String workDays = "";
		String letterHeadImage1 = "";
		String letterHeadImage = "";
		String pageSize = "";
		String logo = "";
		String patientForm = "";
		// String reportForm = "";

		try {
			connection = getConnection();

			String retrieveClinicCalendarListQuery = QueryMaker.RETRIEVE_CALENDAR_DETAILS_BY_CLINIC_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicCalendarListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicStart = resultSet.getString("clinicStart");
				clinicEnd = resultSet.getString("clinicEnd");
				beakStart1 = resultSet.getString("breakStart1");
				breakEnd1 = resultSet.getString("breakEnd1");
				breakStart2 = resultSet.getString("breakStart2");
				breakEnd2 = resultSet.getString("breakEnd2");
				workDays = resultSet.getString("workDays");
				// letterHeadImage1 = resultSet.getString("letterHeadImage");
				pageSize = resultSet.getString("pageSize");
				// logo = resultSet.getString("logo");
				patientForm = resultSet.getString("patientForm");
				// reportForm = resultSet.getString("reportForm");

			}
			object.put("clinicStart", clinicStart);
			object.put("clinicEnd", clinicEnd);
			object.put("beakStart1", beakStart1);
			object.put("breakEnd1", breakEnd1);
			object.put("breakStart2", breakStart2);
			object.put("breakEnd2", breakEnd2);
			object.put("workDays", workDays);
			// object.put("letterHeadImage", letterHeadImage);
			object.put("pageSize", pageSize);
			// object.put("logo", logo);
			object.put("patientForm", patientForm);
			// object.put("reportForm", reportForm);

			array.add(object);

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();
			connection.close();
			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving calendar details based on clinicID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveClinicImageList(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String letterHeadImage = "";
		String logo = "";

		try {
			connection = getConnection();

			String retrieveLetterHeadLogoQuery = QueryMaker.RETRIEVE_UPLOADED_IMG_BY_CLINIC_ID;

			preparedStatement = connection.prepareStatement(retrieveLetterHeadLogoQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				letterHeadImage = resultSet.getString("letterHeadImage");
				logo = resultSet.getString("logo");
			}

			object.put("letterHeadImage", letterHeadImage);
			object.put("logo", logo);

			array.add(object);

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving calendar details based on clinicID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public String updateClinicCalendar(ClinicForm clinicForm) {

		try {

			connection = getConnection();

			String updateClinicCalendarQuery = QueryMaker.UPDATE_CLINIC_CALENDAR_DETAILS;
			String updateClinicQuery = QueryMaker.UPDATE_CLINIC_PageSize_PatForm_DETAILS;

			preparedStatement = connection.prepareStatement(updateClinicCalendarQuery);

			preparedStatement.setString(1, clinicForm.getClinicStartName());
			preparedStatement.setString(2, clinicForm.getClinicEndName());
			preparedStatement.setString(3, clinicForm.getBreakStart1());
			preparedStatement.setString(4, clinicForm.getBreakEnd1());
			preparedStatement.setString(5, clinicForm.getBreakStart2());
			preparedStatement.setString(6, clinicForm.getBreakEnd2());
			preparedStatement.setString(7, clinicForm.getWorkdaysName());
			preparedStatement.setInt(8, clinicForm.getClinicID());

			preparedStatement.executeUpdate();

			preparedStatement1 = connection.prepareStatement(updateClinicQuery);

			System.out
					.println("pagesixe:" + clinicForm.getPageSize() + "  patient form::" + clinicForm.getPatientForm());
			preparedStatement1.setString(1, clinicForm.getPageSize());

			String patientForm = "";

			System.out.println("Patient form::" + clinicForm.getPatientForm());
			if (clinicForm.getPatientForm().contains("$")) {
				patientForm = clinicForm.getPatientForm().replace("$", "&");
				preparedStatement1.setString(2, patientForm);
			} else {
				preparedStatement1.setString(2, clinicForm.getPatientForm());
			}

			preparedStatement1.setInt(3, clinicForm.getClinicID());

			preparedStatement1.executeUpdate();

			System.out.println("Calendar details updated successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String udpateClinicLog(int clinicID, String logoFileName) {

		try {

			connection = getConnection();

			String udpateClinicLogQuery = QueryMaker.UPDATE_CLINIC_LOGO;

			preparedStatement = connection.prepareStatement(udpateClinicLogQuery);

			if (logoFileName == null || logoFileName == "") {
				preparedStatement.setString(1, null);
			} else if (logoFileName.isEmpty()) {
				preparedStatement.setString(1, null);
			} else {
				preparedStatement.setString(1, logoFileName);
			}

			preparedStatement.setInt(2, clinicID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String updateCommentByApptID(String commentApptID, String updatedComment) {
		System.out.println("Inside updateCommentByApptID method from ClinicDAOImpl");
		System.out.println("ID: " + commentApptID);
		System.out.println("Updated Comment: " + updatedComment);

		String status = "error";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(QueryMaker.UPDATE_COMMENT_BY_APPT_ID)) {

			preparedStatement.setString(1, updatedComment);

			preparedStatement.setInt(2, Integer.parseInt(commentApptID));

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				status = "success";
			} else {
				status = "no rows updated";
			}

		} catch (NumberFormatException e) {
			System.err.println("Invalid ID format: " + commentApptID);
			e.printStackTrace();
		} catch (Exception exception) {
			System.err.println("An error occurred while updating the comment:");
			exception.printStackTrace();
		}

		return status;
	}

	public String retrieveUpdatedCommentByApptID(String commentApptID) {
	    String updatedComment = "";

	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = 
	             connection.prepareStatement(QueryMaker.RETRIEVE_COMMENT_BY_APPT_ID)) {

	        preparedStatement.setInt(1, Integer.parseInt(commentApptID));

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {

	            if (resultSet.next()) {
	                updatedComment = resultSet.getString("comments");
	            }
	        }
	        
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid ID format: " + commentApptID);
	        e.printStackTrace();
	    } catch (Exception exception) {
	        System.err.println("An error occurred while retrieving the comment:");
	        exception.printStackTrace();
	    }

	    return updatedComment;
	}

	@Override
	public String udpateClinicDetails(int clinicID, String logoFile, String letterHeadFile) {

		try {

			connection = getConnection();

			String udpateClinicDetailsQuery = QueryMaker.UPDATE_CLINIC_DETAILS;

			preparedStatement = connection.prepareStatement(udpateClinicDetailsQuery);

			if (logoFile == null || logoFile == "") {
				preparedStatement.setString(1, null);
			} else {
				if (logoFile.isEmpty()) {
					preparedStatement.setString(1, null);
				} else {
					preparedStatement.setString(1, logoFile);
				}

			}

			if (letterHeadFile == null || letterHeadFile == "") {
				preparedStatement.setString(2, null);
			} else {
				if (letterHeadFile.isEmpty()) {
					preparedStatement.setString(2, null);
				} else {
					preparedStatement.setString(2, letterHeadFile);
				}
			}

			preparedStatement.setInt(3, clinicID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String retrieveReviewFormURL(int practiceID) {

		String reviewFormURL = "";

		try {

			connection = getConnection();

			String retrieveReviewFormURLQuery = QueryMaker.RETRIEVE_REVIEW_FOR_URL;

			preparedStatement = connection.prepareStatement(retrieveReviewFormURLQuery);

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

	public boolean verifyClinicLogoWExists(String logoFileName, int clinicID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyClinicLogoWExistsQuery = QueryMaker.VERIFY_CLINIC_LOGO_EXISTS;

			preparedStatement = connection.prepareStatement(verifyClinicLogoWExistsQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, logoFileName);

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

	public JSONObject updateAppointmentStatusDone(int patientID, int apppintmentID) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		int check = 0;

		try {

			connection = getConnection();

			String updateAppointmentStatusDoneQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS_DONE;

			preparedStatement = connection.prepareStatement(updateAppointmentStatusDoneQuery);

			preparedStatement.setString(1, ActivityStatus.DONE);
			preparedStatement.setInt(3, patientID);
			preparedStatement.setInt(2, apppintmentID);

			preparedStatement.executeUpdate();

			System.out.println("Appointment done successfully.");

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public void updateNextAppointmentTaken(int appointmentID, int patientID) {

		try {

			connection = getConnection();

			String updateNextAppointmentTakenQuery = QueryMaker.UPDATE_NEXT_APPOINTMENT_TAKEN;

			preparedStatement = connection.prepareStatement(updateNextAppointmentTakenQuery);

			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(3, patientID);
			preparedStatement.setInt(2, appointmentID);

			preparedStatement.executeUpdate();

			System.out.println("Next appointment taken updated");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public JSONObject retrieveClinicListForPractice(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveClinicListForPracticeQuery = QueryMaker.RETRIEVE_ClinicList_For_Practice_BY_PracticeID;

			preparedStatement = connection.prepareStatement(retrieveClinicListForPracticeQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				if (resultSet.getString("name") == null || resultSet.getString("name") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("name").isEmpty()) {
						System.out.println("Null found.");
					} else {
						object = new JSONObject();

						object.put("clinicID", resultSet.getInt("id"));
						object.put("clinic", resultSet.getString("name"));
						object.put("check", check1);

						array.add(object);

						values.put("Release", array);
					}
				}
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public List<ClinicForm> retrivePracticeListForSuperAdmin() {

		List<ClinicForm> clinicList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String retrivePracticeListQuery = QueryMaker.RETRIEVE_ALL_ACTIVE_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(retrivePracticeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setPracticeID(resultSet.getInt("id"));
				clinicForm.setPracticeName(resultSet.getString("name"));
				clinicForm.setActicityStatus(resultSet.getString("activityStatus"));
				clinicForm.setPracticeSuffix(resultSet.getString("suffix"));
				if (resultSet.getString("url") == null || resultSet.getString("url") == "") {
					clinicForm.setPracticeURL("NA");
				} else if (resultSet.getString("url").isEmpty()) {
					clinicForm.setPracticeURL("NA");
				} else {
					clinicForm.setPracticeURL(resultSet.getString("url"));
				}

				clinicList.add(clinicForm);
			}

			status = "success";
			System.out.println("Successfully retrived practice detail from table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving practice detail from table Practice due to:::"
					+ exception.getMessage());
		}
		return clinicList;
	}

	public List<ClinicForm> searchForSuperAdminPracticeList(String searchPracticeName) {

		List<ClinicForm> clinicList = new ArrayList<ClinicForm>();
		ClinicForm clinicForm = null;

		try {
			connection = getConnection();

			String searchPracticeListQuery = QueryMaker.SEARCH_ALL_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(searchPracticeListQuery);

			if (searchPracticeName.contains(" ")) {
				searchPracticeName = searchPracticeName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchPracticeName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicForm = new ClinicForm();

				clinicForm.setPracticeID(resultSet.getInt("id"));
				clinicForm.setPracticeName(resultSet.getString("name"));
				clinicForm.setActicityStatus(resultSet.getString("activityStatus"));
				clinicForm.setPracticeSuffix(resultSet.getString("suffix"));
				clinicForm.setSearchPracticeName(searchPracticeName);
				if (resultSet.getString("url") == null || resultSet.getString("url") == "") {
					clinicForm.setPracticeURL("NA");
				} else if (resultSet.getString("url").isEmpty()) {
					clinicForm.setPracticeURL("NA");
				} else {
					clinicForm.setPracticeURL(resultSet.getString("url"));
				}

				clinicList.add(clinicForm);
			}

			status = "success";
			System.out.println("Successfully retrived practice detail from table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving practice detail from table Practice due to:::"
					+ exception.getMessage());
		}
		return clinicList;
	}

	@Override
	public boolean verifyDataExists(int practiceID, String tableName) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyDataExistsQuery = "SELECT practiceID FROM " + tableName + " WHERE practiceID = ?";

			preparedStatement1 = connection.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyNewClinic(int clincID, String tableName) {

		boolean check = false;
		System.out.println("Clinic id ::" + clincID);
		try {

			connection = getConnection();

			String verifyDataExistsQuery = "SELECT id FROM " + tableName + " WHERE id = ?";

			preparedStatement1 = connection.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, clincID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	@Override
	public String updateClinic(String logo, String letterHead, String clinicID) {

		try {

			connection = getConnection();

			String updateClinicQuery = QueryMaker.UPDATE_CLINIC_TABLE;

			preparedStatement = connection.prepareStatement(updateClinicQuery);

			preparedStatement.setString(1, logo);

			preparedStatement.setString(2, letterHead);

			preparedStatement.setString(3, clinicID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated clinic details.");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating clinic details into Clinic table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String retrieveAlreadyPresentLetterFile(int clinicID) {

		String letterFile = null;
		try {
			connection = getConnection();

			String retrieveLetterFileByClinicIDQuery = QueryMaker.RETRIEVE_LETTER_IMG_BY_CLINICID;

			preparedStatement = connection.prepareStatement(retrieveLetterFileByClinicIDQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				letterFile = resultSet.getString("letterHeadImage");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving last entered practiceID from Practice table due to:::"
							+ exception.getMessage());
		}
		return letterFile;

	}

	@Override
	public int retrievePracticeIDByClinicID(int clinicID) {
		int practiceID = 0;
		try {
			connection = getConnection();
			String retrievePracticeIDByClinicIDQuery = QueryMaker.RETRIEVE_PRACTICEID_BY_CLINICID;
			preparedStatement = connection.prepareStatement(retrievePracticeIDByClinicIDQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				practiceID = resultSet.getInt("practiceID");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving last entered practiceID from Practice table due to:::"
							+ exception.getMessage());
		}
		return practiceID;
	}

	public int retrieveAllowedVisitByPracticeID(int practiceID) {

		int allowed_visit = 0;
		try {
			connection = getConnection();

			String retrieveAllowedVisitByPracticeIDQuery = QueryMaker.RETRIEVE_ALLOWED_VISIT_BY_PracticeID;

			preparedStatement = connection.prepareStatement(retrieveAllowedVisitByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				allowed_visit = resultSet.getInt("NoOfVisit");
			}
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving NO OF VISITS from PLANDETAILS table due to:::"
					+ exception.getMessage());
		}
		return allowed_visit;

	}

	@Override
	public String insertPlanDetails(String noOfVisits, String dateStart, String dateEnd, int PracticeID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {

			connection = getConnection();

			String insertPlanDetailsQuery = QueryMaker.INSERT_PLAN_DETAILS;

			preparedStatement = connection.prepareStatement(insertPlanDetailsQuery);

			if (noOfVisits == "Unlimited") {
				preparedStatement.setString(1, "1000000");
			} else {
				preparedStatement.setString(1, noOfVisits);
			}
			preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(dateStart)));
			preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(dateEnd)));
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, PracticeID);

			preparedStatement.execute();

			System.out.println("Plan details successfully inserted.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while inserting Plan details into PlanDetails table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	@Override
	public List<ClinicForm> retrievePlanDetailsListByPracticeID(int practiceID) {
		List<ClinicForm> list = new ArrayList<ClinicForm>();

		ClinicForm clinicForm = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrievePlanDetailsListByPracticeIDQuery = QueryMaker.RETRIEVE_PLAN_DETAILS_LIST_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrievePlanDetailsListByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm = new ClinicForm();

				clinicForm.setPlanID(resultSet.getInt("id"));

				if (resultSet.getString("NoOfVisit").equals("1000000")) {

					clinicForm.setPlanNoOfVisits("Unlimited");
				} else {

					clinicForm.setPlanNoOfVisits(resultSet.getString("NoOfVisit"));
				}
				clinicForm
						.setPlanDateStart(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("startDate"))));
				clinicForm.setPlanDateEnd(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("endDate"))));
				clinicForm.setPlanStatus1(resultSet.getString("status"));

				list.add(clinicForm);

			}
			System.out.println("Plan details retrieved successfully");
			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving plan detail based on practiceID For edit due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	@Override
	public boolean verifyPlanIDByPracticeID(int practiceID) {
		System.out.println("pract idd::" + practiceID);
		boolean check = false;

		try {

			connection = getConnection();

			String verifyPlanIDBypracticeIDQuery = QueryMaker.VERIFY_PLAN_ID_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(verifyPlanIDBypracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside check while");
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying whether planid exists for practiceID due to:::"
					+ exception.getMessage());

		}

		return check;
	}

	@Override
	public String retrieveStartDateByPracticeID(int practiceID) {

		String startDate = "";
		try {
			connection = getConnection();
			String retrieveStartDateByPracticeIDQuery = QueryMaker.RETRIEVE_START_DATE_BY_PracticeID;

			preparedStatement = connection.prepareStatement(retrieveStartDateByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				startDate = resultSet.getString("startDate");

			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving start date from plandetails table due to:::"
					+ exception.getMessage());
		}
		return startDate;
	}

	@Override
	public String retrieveEndDateByPracticeID(int practiceID) {

		String endDate = "";
		try {
			connection = getConnection();
			String retrieveEndDateByPracticeIDQuery = QueryMaker.RETRIEVE_END_DATE_BY_PracticeID;

			preparedStatement = connection.prepareStatement(retrieveEndDateByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				endDate = resultSet.getString("endDate");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving end date from plandetails table due to:::"
					+ exception.getMessage());
		}
		return endDate;
	}

	@Override
	public int retrieveVisitCountBetweenDates(String startDate, String endDate) {
		int count = 0;

		try {
			connection = getConnection();
			String retrieveVisitCountBetweenDatesQuery = QueryMaker.RETRIEVE_VISIT_COUNT_BETWEEN_DATES;

			preparedStatement = connection.prepareStatement(retrieveVisitCountBetweenDatesQuery);

			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);

			resultSet = preparedStatement.executeQuery();

			resultSet.next();
			count = resultSet.getInt("rowcount");

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving end date from plandetails table due to:::"
					+ exception.getMessage());
		}
		return count;
	}

	@Override
	public int retrievePlanIDBypracticeID(int practiceID) {
		int planID = 0;
		try {
			connection = getConnection();
			String retrievePlanIDByPracticeIDQuery = QueryMaker.RETRIEVE_PLAN_ID_BY_PracticeID;

			preparedStatement = connection.prepareStatement(retrievePlanIDByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				planID = resultSet.getInt("id");
				System.out.println("plan id is::" + planID);
			}
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving planID from plandetails table due to:::"
					+ exception.getMessage());
		}
		return planID;
	}

	@Override
	public String updatePlanDetails(String noOfVisits, String dateStart, String dateEnd, int practiceID) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {

			connection = getConnection();

			String updatePlanDetailsQuery = QueryMaker.UPDATE_PLAN_DETAILS;

			preparedStatement = connection.prepareStatement(updatePlanDetailsQuery);

			if (noOfVisits == "Unlimited") {
				preparedStatement.setString(1, "1000000");
			} else {
				preparedStatement.setString(1, noOfVisits);
			}
			preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(dateStart)));
			preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(dateEnd)));
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, practiceID);

			preparedStatement.execute();

			System.out.println("Plan details successfully updated.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while updating Plan details into PlanDetails table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	@Override
	public List<ClinicForm> retrieveDisabledPlanRow(int planID) {
		List<ClinicForm> list = new ArrayList<ClinicForm>();
		System.out.println("plan id disable ::" + planID);
		ClinicForm clinicForm = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrievePlanDetailsListByPracticeIDQuery = QueryMaker.RETRIEVE_PLAN_DETAILS_LIST_BY_PLAN_ID;

			preparedStatement = connection.prepareStatement(retrievePlanDetailsListByPracticeIDQuery);

			preparedStatement.setInt(1, planID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm = new ClinicForm();

				clinicForm.setPlanID(resultSet.getInt("id"));

				if (resultSet.getString("NoOfVisit").equals("1000000")) {

					clinicForm.setPlanNoOfVisits("Unlimited");
				} else {

					clinicForm.setPlanNoOfVisits(resultSet.getString("NoOfVisit"));
				}
				clinicForm
						.setPlanDateStart(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("startDate"))));
				clinicForm.setPlanDateEnd(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("endDate"))));
				clinicForm.setPlanStatus1(resultSet.getString("status"));

				list.add(clinicForm);

			}
			System.out.println("Disabled Plan details retrieved successfully");
			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving plan detail based on practiceID For edit due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	@Override
	public String insertMDDetails(String mdName, String mdQualification, String dbFileName, String mdStartDate,
			String mdEndDate, int PracticeID) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {

			connection = getConnection();

			String insertMDDetailsQuery = QueryMaker.INSERT_MD_DETAILS;

			preparedStatement = connection.prepareStatement(insertMDDetailsQuery);

			preparedStatement.setString(1, mdName);
			preparedStatement.setString(2, mdQualification);
			preparedStatement.setString(3, dbFileName);
			preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(mdStartDate)));
			preparedStatement.setString(5, dateToBeFormatted.format(dateFormat.parse(mdEndDate)));
			preparedStatement.setInt(6, PracticeID);

			preparedStatement.execute();

			System.out.println("MD details successfully inserted.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while inserting MD details into PracticeMDDetails table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public List<ClinicForm> retrieveMDDetailsListByPracticeID(int practiceID) {
		List<ClinicForm> list = new ArrayList<ClinicForm>();

		ClinicForm clinicForm = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveMDDetailsListByPracticeIDQuery = QueryMaker.RETRIEVE_MD_DETAILS_LIST_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveMDDetailsListByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicForm = new ClinicForm();

				clinicForm.setMdDetailsID(resultSet.getInt("id"));
				clinicForm.setPracticeMDName(resultSet.getString("MDName"));
				clinicForm.setPracticeMDQualification(resultSet.getString("MDQualification"));
				clinicForm.setPracticeMDSignatureImage(resultSet.getString("MDSignatureImage"));
				clinicForm.setPracticeMDStartDate(
						dateFormat.format(dateToBeFormatted.parse(resultSet.getString("startDate"))));
				clinicForm.setPracticeMDEndDate(
						dateFormat.format(dateToBeFormatted.parse(resultSet.getString("endDate"))));

				list.add(clinicForm);

			}
			System.out.println("MD details retrieved successfully");
			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving MD detail based on practiceID For edit due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	@Override
	public String updateEndDateMDDetails(String mdEndDate, int mdDetailsID, int practiceID) {
		System.out.println("end date:" + mdEndDate + "..mddetailsid:" + mdDetailsID + "..practid:" + practiceID);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {

			connection = getConnection();

			String updateMDDetailsQuery = QueryMaker.UPDATE_MD_DETAILS;

			preparedStatement = connection.prepareStatement(updateMDDetailsQuery);

			preparedStatement.setString(1, dateToBeFormatted.format(dateFormat.parse(mdEndDate)));
			preparedStatement.setInt(2, mdDetailsID);
			preparedStatement.setInt(3, practiceID);

			preparedStatement.execute();

			System.out.println("MD details successfully updated.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println(
					"Exception occured while updating MD details into table due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public String updateClinicPhone(int clinicID, String phone) {

		try {

			connection = getConnection();

			String updateClinicPhoneQuery = QueryMaker.UPDATE_CLINIC_PHONE;

			preparedStatement = connection.prepareStatement(updateClinicPhoneQuery);

			preparedStatement.setString(1, phone);
			preparedStatement.setInt(2, clinicID);

			preparedStatement.execute();

			System.out.println("Clinic Phone updated");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while updating clinic phone due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public String retrieveApptDocName(int patientID, String apptDate, String apptStartTime, String apptEndTime,
			int clinicID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("yyyy-MM-dd");

		String docName = "";

		System.out.println("..." + apptStartTime);

		String prevStart = "";
		String prevEnd = "";

		if (apptStartTime.contains(" ")) {
			String[] startArray = apptStartTime.split(" ");

			prevStart = startArray[1];
		} else {
			prevStart = apptStartTime;
		}

		if (apptEndTime.contains(" ")) {
			String[] endArray = apptEndTime.split(" ");

			prevEnd = endArray[1];
		} else {
			prevEnd = apptEndTime;
		}

		try {

			connection = getConnection();

			String retrieveApptDocNameQuery = QueryMaker.RETRIEVE_APPOINTMENT_DOCTOR_NAME;

			preparedStatement = connection.prepareStatement(retrieveApptDocNameQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(apptDate)));
			preparedStatement.setString(3, prevStart);
			preparedStatement.setString(4, prevEnd);
			preparedStatement.setInt(5, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				docName = resultSet.getString("doctorName");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return docName;

		} catch (Exception exception) {
			exception.printStackTrace();

			return docName;
		}

	}

	@Override
	public int getDashboardType(int practiceID) {

		int dashboardType = 0;

		try {

			connection = getConnection();

			String getDashboardTypeQuery = QueryMaker.RETRIEVE_ALL_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(getDashboardTypeQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				dashboardType = resultSet.getInt("facilityDashboard");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return dashboardType;
	}

	@Override
	public HashMap<String, List<ClinicForm>> retrieveRoomBookingList(int clinicID, int practiceID) {

		HashMap<String, List<ClinicForm>> map = new HashMap<String, List<ClinicForm>>();

		try {

			connection = getConnection();

			String retrieveRoomTypeListQuery = QueryMaker.RETRIEVE_ROOM_TYPE_LIST;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int roomTypeID = resultSet.getInt("id");

				String roomName = resultSet.getString("roomType");

				int vacantCount = resultSet.getInt("roomCapacity");

				List<ClinicForm> list = new ArrayList<ClinicForm>();

				// retrieving appointment details for the room typeid
				String retrieveAppointmentDetailsByRoomTypeIDQuery = QueryMaker.RETRIEVE_APPOINTMENT_BY_ROOM_TYPPE_ID;

				preparedStatement1 = connection.prepareStatement(retrieveAppointmentDetailsByRoomTypeIDQuery);

				preparedStatement1.setInt(1, roomTypeID);
				preparedStatement1.setInt(2, clinicID);

				resultSet1 = preparedStatement1.executeQuery();

				int rowCount = 0;

				while (resultSet1.next()) {

					ClinicForm clinicForm = new ClinicForm();

					clinicForm.setRoomName(resultSet.getString("roomType"));
					clinicForm.setRoomCapacity(resultSet.getInt("roomCapacity"));
					clinicForm.setRoomTypeID(roomTypeID);
					clinicForm.setActicityStatus(resultSet1.getString("status"));
					clinicForm.setPatienName(
							resultSet1.getString("patientName") + " (" + resultSet1.getInt("patientID") + ")");
					clinicForm.setDisplayName(resultSet1.getString("patientName") + " ("
							+ resultSet1.getInt("patientID") + ")<br><b>[" + resultSet1.getString("apptDateFMT")
							+ " to " + resultSet1.getString("apptEndDateFMT") + "]</b>");
					clinicForm.setStartDate(resultSet1.getString("apptDateFMT"));
					clinicForm.setEndDate(resultSet1.getString("apptEndDateFMT"));
					clinicForm.setPatientID(resultSet1.getInt("patientID"));
					clinicForm.setApptID(resultSet1.getInt("id"));
					clinicForm.setVisitTypeID(resultSet1.getInt("visitTypeID"));
					clinicForm.setVisitTypeName(resultSet1.getString("visitType"));

					list.add(clinicForm);

					rowCount++;

				}

				if (rowCount == 0) {

					ClinicForm clinicForm = new ClinicForm();

					clinicForm.setRoomName(resultSet.getString("roomType"));
					clinicForm.setRoomCapacity(resultSet.getInt("roomCapacity"));
					clinicForm.setRoomTypeID(roomTypeID);
					clinicForm.setDisplayName("NA");
					clinicForm.setActicityStatus("Vacant");
					clinicForm.setVisitTypeName("NA");

					list.add(clinicForm);

				}

				resultSet1.close();
				preparedStatement1.close();

				// calculating vacant count of rooms
				vacantCount = vacantCount - rowCount;

				map.put(roomName, list);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ClinicDAOInf#updatePracticeURL(java.lang.String,
	 * int)
	 */
	public String updatePracticeURL(String practiceURL, int practiceID) {

		try {

			connection = getConnection();

			String updatePracticeURLQuery = QueryMaker.UPDATE_PRACTICE_URL;

			preparedStatement = connection.prepareStatement(updatePracticeURLQuery);

			preparedStatement.setString(1, practiceURL);
			preparedStatement.setInt(2, practiceID);

			preparedStatement.execute();

			System.out.println("Practice details successfully inserted.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while inserting Practice details into Practice table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	@Override
	public boolean verifyPracticeURLExists(int practiceID) {

		boolean flag = false;

		try {
			connection = getConnection();

			String verifyPracticeURLExistsQuery = QueryMaker.RETRIEVE_ALL_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(verifyPracticeURLExistsQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString("url") == null || resultSet.getString("url") == "") {
					flag = false;
				} else if (resultSet.getString("url").isEmpty()) {
					flag = false;
				} else {
					flag = true;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			flag = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return flag;
	}

}