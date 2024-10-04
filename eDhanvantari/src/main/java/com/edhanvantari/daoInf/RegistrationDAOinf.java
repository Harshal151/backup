package com.edhanvantari.daoInf;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.edhanvantari.form.RegistrationForm;

public interface RegistrationDAOinf {

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String insertUser(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String insertSignUpUserDetail(RegistrationForm registrationForm);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<RegistrationForm> retriveEditUSerList(int practiceID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public List<RegistrationForm> retreiveUserDetailByUserID(int userID);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updateUserDetail(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String rejectUser(RegistrationForm registrationForm);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String approveUserRequest(int userID);

	/**
	 * 
	 * @param userRequestID
	 * @return
	 */
	public int retreiveUserId(int userRequestID);

	/**
	 * 
	 * @param userID
	 * @param registrationForm
	 * @return
	 */
	public String insertClinicianDetail(int userID, RegistrationForm registrationForm);

	/**
	 * 
	 * @param userID
	 * @param registrationForm
	 * @return
	 */
	public String insertPatientDetail(int userID, RegistrationForm registrationForm);

	/**
	 * 
	 * @param userID
	 * @param registrationForm
	 * @return
	 */
	public String insertContactInfo(int userID, RegistrationForm registrationForm);

	/**
	 * 
	 * @param username
	 * @return
	 */
	public int retrieveUserIDByUsername(String username);

	/**
	 * 
	 * @param username
	 * @param practiceID 
	 * @return
	 */
	public int verifyUsername(String username, int practiceID);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updateAdminUserType(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updateClinician(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updateStaff(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updatePatient(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String updateContactInfo(RegistrationForm registrationForm);

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String insertAdministrator(RegistrationForm registrationForm, int userID);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String updateActivityStatusToActive(RegistrationForm registrationForm);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public HashMap<Integer, String> getClinicList(int practiceID);

	/**
	 * 
	 * @param userType
	 * @param userID
	 * @return
	 */
	public JSONObject retrieveClinicianSpecialisationDetail(String userType, int userID);

	/**
	 * 
	 * @param userType
	 * @param userID
	 * @return
	 */
	public JSONObject retrieveStaffSpecialisationDetail(String userType, int userID);

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> retrieveOPDJSPList();

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> retrieveIPDJSPList();

	/**
	 * 
	 * @param searchUserName
	 * @param practiceID
	 * @return
	 */
	public List<RegistrationForm> searchUser(String searchUserName, int practiceID);

	/**
	 * 
	 * @param userID
	 * @param password
	 */
	public void insertPasswordHistory(int userID, String password);

	/**
	 * 
	 * @param username
	 * @param userID
	 * @param practiceID 
	 * @return
	 */
	public boolean verifyUsernameWithUserID(String username, int userID, int practiceID);

	/**
	 * 
	 * @param userID
	 * @param pin
	 * @return
	 */
	public boolean verifyPINChange(int userID, String pin);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public boolean verifyPassword(int userID, String password);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public boolean verifyPasswordHistory(int userID, String password);

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getPracticeList();

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public JSONObject retrieveClinicByPracticeID(int practiceID);

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> retrievePatientPageList();

	/**
	 * 
	 * @return
	 */
	public List<String> retrieveOPDPageList();

	/**
	 * 
	 * @param username
	 * @return
	 */
	public JSONObject verifyUsernameExists(String username);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveUsernameByUserID(int userID);

	/**
	 * 
	 * @param empID
	 * @return
	 */
	public String retrieveEmployeeNameByID(int empID);
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public List<RegistrationForm> retreiveUserProfileDetails(int userID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<String> retrieveStartHourListByClinicStartEndHour(int clinicID);

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> retrieveReportPageList();

	public HashMap<Integer, String> getPracticeListForAdmin(int userID);

	public int checkOpenLeaveRegister(int practiceID);

	public int retrievePatientIDByUsername(String username);

	public HashMap<String, String> retrieveUserDetails(int practiceID,String username);
	
	public String retrieveSignature(int userID);

	public HashMap<String, String> retrieveMDDetails(int practiceID, String collectionDate);

}
