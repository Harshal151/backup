package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

public interface ListnerDOAInf {

	/**
	 * 
	 * @return
	 */
	public String checkTradeNameFromPrescription(String contextPath);

	/**
	 * 
	 * @return
	 */
	public String checkDiagnosisFromVisit(String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public String checkDiagnosisFromPersonalHistory(String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public List<String> retrieveAppointmentListForReminderSMS(String contextPath);

	/**
	 * 
	 * @param patientID
	 * @param contextPath
	 * @return
	 */
	public String retrievePatientMobileNoByID(int patientID, String contextPath);

	/**
	 * 
	 * @param practiceID
	 * @param contextPath
	 * @return
	 */
	public String retrievePracticeNameByID(int practiceID, String contextPath);

	/**
	 * 
	 * @param clinicID
	 * @param contextPath
	 * @return
	 */
	public String retrieveClinicNameByID(int clinicID, String contextPath);

	/**
	 * 
	 * @param patientID
	 * @param contextPath
	 * @return
	 */
	public String retrievePatientFirstLastNameByID(int patientID, String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public List<String> retrieveClinicianUserID(String contextPath);

	/**
	 * 
	 * @param practiceID
	 * @param contextPath
	 * @return
	 */
	public String retrieveEmailFromByPracticeID(int practiceID, String contextPath);

	/**
	 * 
	 * @param practiceID
	 * @param contextPath
	 * @return
	 */
	public String retrieveEmailFromPassByPracticeID(int practiceID, String contextPath);

	/**
	 * 
	 * @param patientID
	 * @param contextPath
	 * @param clinicID
	 * @return
	 */
	public String retrievePatientFirstLastNameRegNoByID(int patientID, String contextPath, int clinicID);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public List<Integer> retrieveClinicIDForTodaysAppt(String contextPath);

	/**
	 * 
	 * @param clinicID
	 * @param contextPath
	 * @return
	 */
	public List<String> retrieveAppointmentListForClinicID(int clinicID, String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public List<Integer> retrievePracticeIDList(String contextPath);

	/**
	 * 
	 * @param clinicID
	 * @param patientID
	 * @param contextPath
	 * @return
	 */
	public String retrieveClinicRegNoByClinicID(int clinicID, int patientID, String contextPath);

	/**
	 * 
	 * @param practiceID
	 * @param contextPath
	 * @return
	 */
	public boolean verifyAppointmentExistsForPractice(int practiceID, String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public List<String> retrieveDefaultClinicIDList(String contextPath);

	/**
	 * 
	 * @param columnName
	 * @param clinicID
	 * @param contextPath
	 * @return
	 */
	public boolean verifyCommunication(String columnName, int clinicID, String contextPath);

	/**
	 * 
	 * @param clinicID
	 * @param contextPath
	 * @return
	 */
	public boolean verifyAppointmentExistsForClinic(int clinicID, String contextPath);

	/**
	 * 
	 * @param contextPath
	 * @param clinicID
	 * @return
	 */
	public List<Integer> retrieveDistinctClinicianIDFromAppointment(String contextPath, int clinicID);

	/**
	 * 
	 * @param clinicianID
	 * @param contextPath
	 * @return
	 */
	public String retrieveClinicianNameByID(int clinicianID, String contextPath);

	/**
	 * 
	 * @param clinicianID
	 * @param clinicID
	 * @param contextPath
	 * @return
	 */
	public int retrieveAppointmentCountForClinician(int clinicianID, int clinicID, String contextPath);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @param contextPath
	 * @return
	 */
	public String retrievePatientDetailsForAppointment(int clinicID, int clinicianID, String contextPath);

	/**
	 * 
	 * @param clinicianID
	 * @param contextPath
	 * @return
	 */
	public String retrieveClinicianMobileNoByID(int clinicianID, String contextPath);

	/**
	 * 
	 * @param columnName
	 * @param practiceID
	 * @param contextPath
	 * @return
	 */
	public boolean verifyCommunicationByPracticeID(String columnName, int practiceID, String contextPath);

	public String updateBookedAppointmentStatus(String contextPath);

	public String updateDispensedAppointmentStatus(String contextPath);
}
