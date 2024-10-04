package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.edhanvantari.form.ClinicForm;

public interface ClinicDAOInf {

	/**
	 * 
	 * @param practiceName
	 * @return
	 */
	public JSONObject verifyPracticeExists(String practiceName);

	/**
	 * 
	 * @param practiceName
	 * @param practiceSuffix
	 * @param consentDocumentPath
	 * @param sessionTimeout
	 * @param invalidAttempts
	 * @param bucketName
	 * @param facilityDashboard
	 * @param thirdPartyAPIIntegration
	 * @return
	 */
	public String insertPractice(String practiceName, String practiceSuffix, String consentDocumentPath,
			String sessionTimeout, String invalidAttempts, String bucketName, int facilityDashboard,
			int thirdPartyAPIIntegration);

	/**
	 * 
	 * @param practiceName
	 * @return
	 */
	public int retrieveLastEneteredPracticeID(String practiceName);

	/**
	 * 
	 * @param clincForm
	 * @param practiceID
	 * @return
	 */
	public String insertPracticeConfigDetails(ClinicForm clincForm, int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public int retrieveLastEnteredClinicID(int practiceID);

	/**
	 * 
	 * @param searchPracticeName
	 * @param userID
	 * @return
	 */
	public List<ClinicForm> searchPracticeList(String searchPracticeName, int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ClinicForm> retrivePracticeList(int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ClinicForm> retrivePracticeByPracticeID(int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ClinicForm> retrieveClinicListByPracticeID(int practiceID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject deleteClinicRow(int clinicID);

	/**
	 * 
	 * @param practiceName
	 * @param practiceID
	 * @param practiceSuffix
	 * @param consentDocumentPath
	 * @param sessionTimeout
	 * @param invalidAttempts
	 * @param bucketName
	 * @param facilityDashboard
	 * @param thirdPartyAPIIntegration
	 * @return
	 */
	public String updatePractice(String practiceName, int practiceID, String practiceSuffix, String consentDocumentPath,
			String sessionTimeout, String invalidAttempts, String bucketName, int facilityDashboard, int thirdPartyAPIIntegration);

	/**
	 * 
	 * @param clincForm
	 * @param practiceID
	 * @return
	 */
	public String updatePracticeConfigDetails(ClinicForm clincForm, int practiceID);

	/**
	 * 
	 * @param clinicStart
	 * @param clinicEnd
	 * @param breakStart1
	 * @param breakEnd1
	 * @param breakStart2
	 * @param breakEnd2
	 * @param clinicID
	 * @param workdays
	 * @return
	 */
	public String insertCalendarDetails(String clinicStart, String clinicEnd, String breakStart1, String breakEnd1,
			String breakStart2, String breakEnd2, int clinicID, String workdays);

	/**
	 * 
	 * @param clinicID
	 */
	public void deleteCalendarDetails(int clinicID);

	/**
	 * 
	 * @param clinicForm
	 * @return
	 */
	public String insertVisitType(ClinicForm clinicForm, String finalFileName);

	/**
	 * 
	 * @param visitTypeName
	 * @return
	 */
	public int retrieveVisitTypeIDByName(String visitTypeName);

	/**
	 * 
	 * @param searchVisitTypeName
	 * @param practiceID
	 * @return
	 */
	public List<ClinicForm> searchVisitType(String searchVisitTypeName, int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ClinicForm> retrieveAllVisitTypeList(int practiceID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public List<ClinicForm> retrieveVisitTypeListByVisitTypeID(int visitTypeID);

	/**
	 * 
	 * @param clinicForm
	 * @param FileName
	 * @return
	 */
	public String updateVisitType(ClinicForm clinicForm, String FileName);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public boolean verifyVisitExistsForVisitTypeID(int visitTypeID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String deleteVisitTypeByVisitTypeID(int visitTypeID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String retrieveVisitTypeNameByID(int visitTypeID);

	/**
	 * 
	 * @param clinicForm
	 * @param practiceID
	 * @return
	 */
	public String insertCommunicationDetails(ClinicForm clinicForm, int practiceID);

	/**
	 * 
	 * @param clinicForm
	 * @return
	 */
	public String updateCommunicationDetails(ClinicForm clinicForm);

	/**
	 * 
	 * @param smsColName
	 * @param emailColName
	 * @param practiceID
	 * @return
	 */
	public String retrieveCommunicationVal(String smsColName, String emailColName, int practiceID);

	/**
	 * 
	 * @param clinicForm
	 * @return
	 */
	public String insertPatientDetails(ClinicForm clinicForm);

	/**
	 * 
	 * @param clinicForm
	 * @param clinicID
	 * @param apptNumber
	 * @return
	 */
	public JSONObject insertApppointmentDetails(ClinicForm clinicForm, int clinicID, int apptNumber);

	/**
	 * 
	 * @param apptDate
	 * @return
	 */
	public int retrieveAppointmentNumber(String apptDate);

	/**
	 * 
	 * @param fName
	 * @param lName
	 * @return
	 */
	public int retrievePatientIDByFirstAndLastName(String fName, String lName);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public JSONObject retrievePatientDetailsBasedOnPatientID(int patientID);

	/**
	 * 
	 * @param formName
	 * @return
	 */
	public JSONObject retrieveTabNameByFormName(String formName);

	/**
	 * 
	 * @param patientID
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public JSONObject updateConfirmedAppointmentStatusByPatientID(int patientID, String startTime, String endTime);

	/**
	 * 
	 * @param patientID
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public JSONObject updateCancelledAppointmentStatusByPatientID(int patientID, String startTime, String endTime);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public JSONObject updateAppointment(ClinicForm form);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveClinicCalendarList(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @param logoFileName
	 * @return
	 */
	public String udpateClinicLog(int clinicID, String logoFileName);
	
	/**
	 * 
	 * @param commentApptID
	 * @param updatedComment
	 * @return
	 */
	public String updateCommentByApptID(String commentApptID, String updatedComment);
	
	/**
	 * 
	 * @param commentApptID
	 * @return
	 */
	public String retrieveUpdatedCommentByApptID(String commentApptID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrieveReviewFormURL(int practiceID);

	/**
	 * 
	 * @param logoFileName
	 * @param clinicID
	 * @return
	 */
	public boolean verifyClinicLogoWExists(String logoFileName, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param appointmentID
	 * @return
	 */
	public JSONObject updateAppointmentStatusDone(int patientID, int appointmentID);

	/**
	 * 
	 * @param appointmentID
	 * @param patientID
	 */
	public void updateNextAppointmentTaken(int appointmentID, int patientID);

	public JSONObject retrieveClinicListForPractice(int practiceID);

	public List<ClinicForm> retrivePracticeListForSuperAdmin();

	public List<ClinicForm> searchForSuperAdminPracticeList(String searchPracticeName);

	public boolean verifyDataExists(int patientID, String string);

	public boolean verifyNewClinic(int i, String string);

	public String updateClinicCalendar(ClinicForm form);

	public String retrieveAlreadyPresentLetterFile(int clinicID);

	// public String updateClinic(String string, int practiceID, String string2,
	// String clinicSettingLogoFileArrDBName, String
	// clinicSettingLetterHeadArrDBName);

	public String updateClinic(String clinicSettingLogoFileArrDBName, String clinicSettingLetterHeadArrDBName,
			String string);

	public String insertClinic(String string, int lastEnteredPracticeID, String string2, String clinicPageSize,
			String clinicPatientForm, String clinicPhone);

	public JSONObject retrieveClinicImageList(int clinicID);

	public String udpateClinicDetails(int clinicID, String logoFileDBName, String letterHeadFileDBName);

	public int retrievePracticeIDByClinicID(int clinicID);

	public int retrieveAllowedVisitByPracticeID(int practiceID);

	public String insertPlanDetails(String noOfVisits, String dateStart, String dateEnd, int lastEnteredPracticeID);

	public List<ClinicForm> retrievePlanDetailsListByPracticeID(int practiceID);

	public JSONObject disablePlanDetails(int planID);

	public boolean verifyPlanIDByPracticeID(int practiceID);

	public String retrieveStartDateByPracticeID(int practiceID);

	public String retrieveEndDateByPracticeID(int practiceID);

	public int retrieveVisitCountBetweenDates(String startDate, String endDate);

	public int retrievePlanIDBypracticeID(int practiceID);

	public String updatePlanDetails(String noOfVisits, String dateStart, String dateEnd, int practiceID);

	public List<ClinicForm> retrieveDisabledPlanRow(int planID);

	public String insertMDDetails(String mdName, String mdQualification, String dbFileName, String mdStartDate,
			String mdEndDate, int lastEnteredPracticeID);

	public List<ClinicForm> retrieveMDDetailsListByPracticeID(int practiceID);

	public String updateEndDateMDDetails(String mdEndDate, int mdDetailsID, int practiceID);

	public String updateClinicPhone(int clinicID, String phone);

	public String retrieveApptDocName(int patientID, String apptDate, String apptStartTime, String apptEndTime,
			int clinicID);

	public int getDashboardType(int practiceID);

	/**
	 * 
	 * @param clinicID
	 * @param practiceID
	 * @return
	 */
	public HashMap<String, List<ClinicForm>> retrieveRoomBookingList(int clinicID, int practiceID);

	public String updatePracticeURL(String practiceURL, int lastEnteredPracticeID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public boolean verifyPracticeURLExists(int practiceID);

}
