package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONObject;

import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;

public interface LoginDAOInf {

	/**
	 * 
	 * @param loginForm
	 * @return
	 */
	public String verifyUserCredentials(LoginForm loginForm);

	/**
	 * 
	 * @param ipAddress
	 * @param actionName
	 * @param userID
	 * @return
	 */
	public String insertAudit(String ipAddress, String actionName, int userID);

	/**
	 * 
	 * @param username
	 * @return
	 */
	public LoginForm getUserDetail(String username);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String calculateTotalNoOfPatients(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String calculateTotalNoOfOPDVisits(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String calculateTotalNoOfIPDVisits(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public Double calculateTotalBilling(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String calculateTotalNoOfOpticianVisit(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public Double calculateTotalOpticianBilling(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String calculateTotalNoOfAppointments(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicName(int clinicID);

	/**
	 * 
	 * @return
	 */
	public String calculateTotalNoOfPatients();

	/**
	 * 
	 * @return
	 */
	public String calculateTotalNoOfOPDVisits();

	/**
	 * 
	 * @return
	 */
	public String calculateTotalNoOfIPDVisits();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfActiveUsers();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfAdminUsers();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfClinicians();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfStaffs();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfClinics();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfClinicTypes();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfDrugs();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfDiagnoses();

	/**
	 * 
	 * @return
	 */
	public String calculateNoOfReferringDoctor();

	/**
	 * 
	 * @param PIN
	 * @param userID
	 * @return
	 */
	public JSONObject verifyUserPIN(String PIN, int userID);

	/**
	 * 
	 * @param newPass
	 * @param newPIN
	 * @param userID
	 * @return
	 */
	public JSONObject updateUserSecurityCredentials(String newPass, String newPIN, int userID);

	/**
	 * 
	 * @param oldPassword
	 * @param userID
	 * @return
	 */
	public JSONObject verifyUserOldPassword(String oldPassword, int userID);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public String insertPasswordHistory(int userID, String password);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public boolean verifyPasswordHistory(int userID, String password);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicLogo(int clinicID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveProfilePic(int userID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveLastLogin(int userID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieverLastPasswordChange(int userID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveLastPINChange(int userID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicStartTime(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicEndTime(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<String> retrieveClinicBreakTime(int clinicID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrieveVisitDuration(int practiceID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveClinicStartEndTime(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveClinicBreak1Time(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveClinicBreak2Time(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveSlot1NameAndTime(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveSlot2NameAndTime(int clinicID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public JSONObject retrieveClinicVisitDuration(int practiceID);

	/**
	 * 
	 * @param fName
	 * @param mName
	 * @param lName
	 * @param mobileNo
	 * @param clinicID
	 * @param practiceID
	 * @return
	 */
	public JSONObject verifyPatientDetails(String fName, String mName, String lName, int mobileNo, int clinicID,
			int practiceID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, String> retrieveVisitTypesList(int clinicID);

	public HashMap<Integer, String> retrieveVisitTypesListForAppo(int clinicID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public JSONObject retrieveAppointmentDuration(int visitTypeID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveUserFullNameByUserID(int userID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public HashMap<Integer, String> retrieveClinicList(int practiceID);

	public HashMap<Integer, String> retrievePatientClinicList(int patientID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public HashMap<Integer, String> retrievepracticeList();

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public String retrievePatientClinicRegistrationNo(int patientID, int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveOPDFormName(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, String> retrieveClinicianList(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicSuffix(int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveDaysOtherThanClinicWorkingDays(int clinicID);

	public int retrievePracticeIDByuserID(int userID);

	public String retrievePatientFormName(int clinicID);

	public String retrievePracticeSuffix(int practiceID);

	public List<LoginForm> retrieveConsentDetailsByUserID(int userID);

	public List<LoginForm> retrieveCircularByActivityStatus();

	public LoginForm getUserPatientDetail(String username);

	public String verifyPatientCredentials(LoginForm form);

	public List<PatientForm> retrievePatientVisitList(int practiceID, int clinicID);

	public String retrieveVisitTypeNameByID(int visitTypeID);

	public String retrieveUserFullNameWithQualification(int userID);

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, String> retrieveRoomTypeListByPracticeID(int practiceID, int clinicID);

	/**
	 * 
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param fName
	 * @param mName
	 * @param lName
	 * @return
	 */
	public JSONObject verifyPatientIntakeDetails(String mobile, int practiceID, int clinicID, String fName, String mName, String lName);

	/**
	 * 
	 * @param mobileNo
	 * @param practiceID
	 * @return
	 */
	public JSONObject redirectPatientIntakeDetails(int patID, int practiceID, int clinicID);

	public JSONObject retrievePIAppointmentType(int clinicID);

	public String insertPIPatientDetails(LoginForm form);
	
	public String updatePIPatientDetails(LoginForm form);

	public String insertPIAppointmentDetails(LoginForm form);
	
	public String updatePIAppointmentDetails(LoginForm form);

	public int retrievePIPatientIDByFirstAndLastName(String firstName, String lastName);
	
	public int retrievePIPatientIDByName(LoginForm form);
	
	public int retrievePIAppointmentID(int patientID, String apptTimeFrom, String apptTimeTo,String apptDate);

	public String insertPIMedicalHistory(LoginForm form);

	public String updatePIMedicalHistory(LoginForm form);

	public JSONObject retrievePIAppointmentSlots(int clinicID, String apptDate);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveUserMedRegistrationNumber(int userID);

	public int retrievePIPatientIDByRegNumber(String registrationNo);

	public String insertClinicRegistration(String registrationNo, int clinicID, int patientID);

	public String insertEmergencyContact(int patientID, LoginForm form);
	
	public String updatePIEmergencyContact(int patientID, LoginForm form);
	
	public String insertIdentification(LoginForm form, int patientID);
	
	public String updatePIIdentification(LoginForm form, int patientID);
	
	public int retrieveClinicianIDByName(LoginForm form);

	public int retrieveAppointmentNumber(String appDate, int clinicID);
}
