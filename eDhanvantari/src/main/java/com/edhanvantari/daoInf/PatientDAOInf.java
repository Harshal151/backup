package com.edhanvantari.daoInf;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONObject;

import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;

public interface PatientDAOInf {

	/**
	 * 
	 * @param patientFirstName
	 * @param patientLastName
	 * @return
	 */
	public String verifyPatientDetail(String patientFirstName, String patientLastName);

	/**
	 * 
	 * @param patientForm
	 * @param patientID
	 * @return
	 */
	public String insertPatientDetails(PatientForm patientForm, int patientID);

	/**
	 * 
	 * @param patientFirstName
	 * @param patientLastName
	 * @return
	 */
	public int retrievePatientID(String patientFirstName, String patientLastName, int practiceID);

	/**
	 * 
	 * @param patientID
	 * @param patientForm
	 * @return
	 */
	public String insertContactInformation(int patientID, PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 * @param patientForm
	 * @return
	 */
	public String insertEmergencyContact(int patientID, PatientForm patientForm);

	/**
	 * 
	 * @param practice
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrievePatientList(int practice, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrivePatientListByID(int patientID, int clinicID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePatientDetail(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateContactInfoDetail(PatientForm patientForm);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String verifyPatientCredential(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyNameDOB(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameLanameDOB(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameMnameLname(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameLname(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameLnameAge(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameLnameGender(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public boolean verifyFnameLnameAgeGender(PatientForm form);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public List<PatientForm> retrievePatientNameDOBList(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateEmergencyContact(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param patientID
	 * @return
	 */
	public String insertIdentification(PatientForm patientForm, int patientID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateIdentification(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param VisitNumber
	 * @return
	 */
	public String insertPatientVisit(PatientForm patientForm, int VisitNumber);

	/**
	 * 
	 * @param patientForm
	 * @param VisitNumber
	 * @param newVisitRef
	 * @param nextVisitDate
	 * @return
	 */
	public String insertPatientVisit(PatientForm patientForm, int VisitNumber, int newVisitRef, String nextVisitDate);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public int retrieveVisitIDbyPatientID(int patientID);

	/**
	 * 
	 * @param form
	 * @param visitID
	 * @return
	 */
	public String insertPrescriptionDetails(PatientForm form, int visitID);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrievePrescriptionList(int patientID, int visitID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveAllPatientPrescriptionList(int patientID, int clinicID);

	/**
	 * 
	 * @param prescID
	 * @return
	 */
	public List<PatientForm> retrievePrescriptionListByID(int prescID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePrescriptionDetails(PatientForm patientForm);

	/**
	 * 
	 * @param prescID
	 * @return
	 */
	public String deletePrescriptionDetails(int prescID);

	/**
	 * 
	 * @param form
	 * @param visitID
	 * @return
	 */
	public String insertBillDetails(PatientForm form, int visitID, int userID);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveBillList(int patientID, int visitID);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveExistingVisitList(int patientID, int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateBill(PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<PatientForm> retrieveLastEnteredVisitDetail(int patientID);

	/**
	 * 
	 * @param patientID
	 * @param lastVisitID
	 * @param clinicID
	 * @param visitType
	 * @param nextVisitDays
	 * @return
	 */
	public List<PatientForm> retrieveLastEnteredVisitDetail(int patientID, int lastVisitID, int clinicID,
			String visitType);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveLastEnteredPrescDetail(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveLastEnteredBillingDetail(int visitID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrievePatientVisitDetail(int patientID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<PatientForm> retrievePatientVisitDetailForStaff(int patientID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public int retrievePatientIDByVisitID(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public JSONObject insertPrescrpitonDetails(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public JSONObject updatePrescrpitonDetails(PatientForm patientForm);

	/**
	 * 
	 * @param prescriptionID
	 * @return
	 */
	public JSONObject deletePrescription(int prescriptionID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public JSONObject updateBilling(PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public int verifyVisitDetails(int patientID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public int retrieveVisitNumber(int patientID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param visitType
	 * @return
	 */
	public int retrievVisitIDForNewVisitType(int patientID, String visitType);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertOPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertDummyOptician(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertDummyOpticianOldGlasses(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public JSONObject retrieveOPDPrscriptionDetail(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePatientVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param VisitNumber
	 * @param nextVisitDate
	 * @return
	 */
	public String updatePatientVisit(PatientForm patientForm, int VisitNumber, String nextVisitDate);

	/**
	 * 
	 * @param patientForm
	 * @param VisitNumber
	 * @param newVisitRef
	 * @return
	 */
	public String updatePatientVisit(PatientForm patientForm, int VisitNumber, int newVisitRef);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String deletePresc(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String deleteVisit(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String deleteOPDVisit(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String deleteOpticianDetails(int visitID);

	/**
	 * 
	 * @param opticianID
	 * @return
	 */
	public String deleteEyewearDetails(int opticianID);

	/**
	 * 
	 * @param opticianID
	 * @return
	 */
	public String deleteOpticianOldGlassesDetails(int opticianID);

	/**
	 * 
	 * @return
	 */
	public int retrievePatientID();

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveLastOpticianDetails(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public int retrieveOpticianID(int visitID);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String updateOpticianDetails(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String updateOpticianOldGlassDetails(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String insertEyewearDetails(PatientForm form);

	/**
	 * 
	 * @param opticianID
	 * @return
	 */
	public JSONObject retrieveLastOLDGlasses(int opticianID);

	/**
	 * 
	 * @param medicalCertiText
	 * @param visitID
	 * @return
	 */
	public String updateMedicalCertificate(String medicalCertiText, int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String insertdummyMedicalDocument(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String deleteDummyMedicalDocument(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String retrieveMedicalCertificate(int visitID);

	/**
	 * 
	 * @param referralLetterText
	 * @param visitID
	 * @return
	 */
	public String insertReferralLetter(String referralLetterText, int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String retrieveReferralLetter(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertIPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertOTNOtes(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param value
	 * @param panelTestvalue
	 * @return
	 */
	public String insertInvestigations(PatientForm patientForm, String panelTestvalue, String value);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertOE(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public List<PatientForm> retrieveIPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveIPDContinuationSheetList(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public JSONObject retrieveIPDComplaints(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public JSONObject insertIPDContinuationSheet(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public JSONObject retrieveIPDContinuationSheet(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public JSONObject deleteIPDComplaints(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public JSONObject deleteIPDContinuationSheet(int visitID);

	/**
	 * 
	 * @param visitNumber
	 * @return
	 */
	public int retrieveVisitIDByVisitNumber(int visitNumber);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @param lastVisitID
	 * @return
	 */
	public List<PatientForm> retrieveLastOPDVisitForIPD(int patientID, int visitID, int lastVisitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateIPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String retrieveCareType(int visitTypeID);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retireveLastEneteredIPDVisit(int patientID, int visitID, int clinicID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateInvestigation(int invastigationID, String value);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOE(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOEIPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOTNotes(PatientForm patientForm);

	/**
	 * 
	 * @param fileNameWithPath
	 * @param visitID
	 * @return
	 */
	public String insertConsentDocument(String fileNameWithPath, int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertConsent(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String retrieveConsent(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateConsent(PatientForm patientForm);

	/**
	 * 
	 * @param patientName
	 * @param practiceID
	 * @param clinicID
	 * @param searchCriteria
	 * @param toDate
	 * @param fromDate
	 * @param patientForm
	 * @return
	 */
	public List<PatientForm> searchPatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria, String fromDate, String toDate);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientFullName(int patientID);

	/**
	 * 
	 * @return
	 */
	public int retrieveAppointmentNumber();

	/**
	 * 
	 * @param patientForm
	 * @param aptNumber
	 * @return
	 */
	public String insertAppointment(PatientForm patientForm, int aptNumber);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentList(int clinicID, int clinicianID);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @param careType
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentList(int clinicID, int clinicianID, String careType);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentWeekList(int clinicID, int clinicianID);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @param careType
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentWeekList(int clinicID, int clinicianID, String careType);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentMonthList(int clinicID, int clinicianID);
	
	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @param monthCount
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentListByCount(int clinicID, int clinicianID, int monthCount, String careType);

	/**
	 * 
	 * @param clinicID
	 * @param clinicianID
	 * @param careType
	 * @return
	 */
	public List<PatientForm> retrieveAppointmentMonthList(int clinicID, int clinicianID, String careType);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<PatientForm> retrieveLastEnteredAppointmentList(int patientID);

	/**
	 * 
	 * @param aptID
	 * @return
	 */
	public String cancelAppointment(int aptID);

	/**
	 * 
	 * @param aptID
	 * @return
	 */
	public String confirmAppointment(int aptID);

	/**
	 * 
	 * @param aptID
	 * @return
	 */
	public String retrieveValueFromAppointment(int aptID);

	/**
	 * 
	 * @param aptID
	 * @return
	 */
	public String completeAppointment(int aptID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateNextVisitDays(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertContinuationSheetDetails(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateContinuationSheetDetails(PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public int retrieveLastOPDVisitIDByPatientID(int patientID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param visitID
	 * @return
	 */
	public int retrieveLastIPDVisitID(int patientID, int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveLastOPDVisitDetails(int visitID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveAllOPDVisitDetails(int patientID, int clinicID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveLastOpthalmologyOPDDetails(int visitID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<Integer> verifyVisitForPatientByPatientID(int patientID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String retrieveApptDurationFromClinicID(int visitTypeID);

	/**
	 * 
	 * @param fileNameList
	 * @return
	 */
	public String insertLabReport(List<String> fileNameList);

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @return
	 */
	public List<File> retrieveLabReportFileName(PatientForm patientForm, String realPath);

	/**
	 * 
	 * @param fromHH
	 * @param fromMM
	 * @param fromAMPM
	 * @param clinicID
	 * @return
	 */
	public JSONObject verifyApointmentTime(int fromHH, int fromMM, String fromAMPM, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public List<String> retrieveAppointmentListByPatientID(int patientID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientNameByPatientID(int patientID);

	/**
	 * 
	 * @param visitNumber
	 * @param patientID
	 * @param visitTypeID
	 * @param clinicID
	 * @return
	 */
	public int retrieveLastEnteredVisitIDByVisitNumber(int visitNumber, int patientID, int visitTypeID, int clinicID);

	/**
	 * 
	 * @param medicalCerti
	 * @param visitID
	 * @return
	 */
	public String insertMedicalCertificate(String medicalCerti, int visitID);

	/**
	 * 
	 * @param patientID
	 * @param lastOPDVisitID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrievePatientDetailsByPatientID(int patientID, int lastOPDVisitID, int clinicID);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String insertOpticianDetails(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String insertOpticianOldGlassDetails(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String updateEyewearDetails(PatientForm form);

	/**
	 * 
	 * @param patientForm
	 * @param visitNumber
	 */
	public void insertDummyVisit(PatientForm patientForm, int visitNumber);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveIPDComplaintsList(int visitID);

	/**
	 * 
	 * @param complaintOD
	 * @param complaintOS
	 * @param visitID
	 * @return
	 */
	public String insertComplaint(String complaintOD, String complaintOS, int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePatientVisitDetails(PatientForm patientForm);

	/**
	 * 
	 * @param practiceSuffixe
	 * @return
	 */
	public String retireveMedicalRegistrationNo(String practiceSuffixe);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertPersonalHistory(PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientAgeByPatientID(int patientID);

	/**
	 * 
	 * @param categoryID
	 * @return
	 */
	public JSONObject retrieveTradeNameByCategory(int categoryID, int clinicID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public JSONObject retrieveProductNameAndCategoryIDByProductID(int productID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String retrieveProductBarcodeByProductID(int productID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String retrieveProductNameByID(int productID);

	/**
	 * 
	 * @param clinicID
	 * @param clinicSuffix
	 * @return
	 */
	public String retrieveReceiptNo(int clinicID, String clinicSuffix);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePersonalHistory(PatientForm patientForm);

	/**
	 * 
	 * @param patientID
	 */
	public void updateConsultationAppointmentStatus(int patientID, int apptID);

	/**
	 * 
	 * @param patientID
	 */
	public void updatePrescriptionAppointmentStatus(int patientID, int apptID);

	/**
	 * 
	 * @param patientID
	 */
	public void updateBillingAppointmentStatus(int patientID, int apptID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public HashMap<String, String> retrieveDiagnosisListForSMS(int practiceID);

	/**
	 * 
	 * @param diagnosis
	 * @param practiceID
	 * @return
	 */
	public List<String> retrievePatientMobileNoBasedOnDiagnosis(String diagnosis, int practiceID);

	/**
	 * 
	 * @param diagnosis
	 * @param clinicID
	 * @return
	 */
	public List<String> retrievePatientMobileNoBasedOnDiagnosisForClinic(String diagnosis, int clinicID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrievePracticeNameByID(int practiceID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicNameByID(int clinicID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientMobileNoByID(int patientID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientFirstLastNameByID(int patientID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientRegistrationNoByID(int patientID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public double retrieveBillAmountByVisitID(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public String retrieveVisitDateAndTime(int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<String> retrievePrescriptionListForSMSByVisitID(int visitID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientEmailByID(int patientID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public int retrieveVisitTypeID(int practiceID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientGenderByPatientID(int patientID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updatePatient(PatientForm patientForm);

	/**
	 * 
	 * @param apptID
	 * @return
	 */
	public HashMap<String, String> retrieveAppointmentTimeById(int apptID);

	/**
	 * 
	 * @param patientID
	 * @param aptID
	 * @return
	 */
	public int retrieveVisitIDByPatientIDAndApptID(int patientID, int aptID);

	/**
	 * 
	 * @param clinicSuffix
	 * @param clinicID
	 * @return
	 */
	public String retirevePatientClinicRegistrationNo(String clinicSuffix, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @param regNo
	 * @return
	 */
	public String insertClinicRegistration(int patientID, int clinicID, String regNo);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public JSONObject verifyPatientExists(int patientID, int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param clinicID
	 * @return
	 */
	public boolean verifyPatientExistsForCurrentClinic(int patientID, int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @param patientID
	 * @return
	 */
	public String retrieveClinicRegNoByClinicID(int clinicID, int patientID);

	/**
	 * 
	 * @param diagnosis
	 * @param practiceID
	 * @param check
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrievePatientListByDiagnosis(String diagnosis, int practiceID, String check, int clinicID);

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @param check
	 * @return
	 */
	public JSONObject retrievePatientListForCredit(int practiceID, int clinicID, String check);

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @param pracCheck
	 * @return
	 */
	public List<Integer> retrieveVisitIDForCredit(int practiceID, int clinicID, String pracCheck);

	/**
	 * 
	 * @param patientID
	 * @param practiceID
	 * @param clinicID
	 * @param check
	 * @return
	 */
	public double retrievePatientCredit(int patientID, int practiceID, int clinicID, String check);

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @param check
	 * @return
	 */
	public List<String> retrieveAllPatientForCredit(int practiceID, int clinicID, String check);

	/**
	 * 
	 * @param frequency
	 * @param practiceID
	 * @return
	 */
	public JSONObject retrieveFrequencyCount(String frequency, int practiceID);

	/**
	 * 
	 * @param patientID
	 * @param status
	 * @return
	 */
	public String disablePatient(int patientID, String status);

	/**
	 * 
	 * @param check
	 * @param practiceID
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveActivePatientList(String check, int practiceID, int clinicID);

	/**
	 * 
	 * @param check
	 * @param practiceID
	 * @param clinicID
	 * @return
	 */
	public List<Integer> retrievePatientIDListForCustomizedSMS(String check, int practiceID, int clinicID);

	/**
	 * 
	 * @param userID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveMyAppointmentList(int userID, int clinicID);

	/**
	 * 
	 * @param userID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveMyAppointmentWeekList(int userID, int clinicID);

	/**
	 * 
	 * @param userID
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveMyAppointmentMonthList(int userID, int clinicID);

	/**
	 * 
	 * @param clinicianID
	 * @return
	 */
	public String retrieveClinicianNameByID(int clinicianID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<Integer> retrieveHeadUserIDList(int clinicID);

	/**
	 * 
	 * @param employeeID
	 * @return
	 */
	public String retrieveEmployeeMailID(int employeeID);

	/**
	 * 
	 * @param employeeID
	 * @return
	 */
	public String retrieveEmployeeMobileNo(int employeeID);

	/**
	 * 
	 * @param receiptID
	 * @return
	 */
	public HashMap<String, String> retrievePaymentDetailsBy(int receiptID);

	/**
	 * 
	 * @param patientID
	 * @param tableName
	 * @return
	 */
	public boolean verifyDataExists(int patientID, String tableName);

	/**
	 * 
	 * @param appointmentID
	 * @return
	 */
	public List<Integer> retrievePatientIDByAppointmentID(String appointmentID);

	/**
	 * 
	 * @param patientID
	 * @param appointmentID
	 * @return
	 */
	public JSONObject verifyVisitExistsForAppointment(int patientID, int appointmentID);

	/**
	 * 
	 * @param password
	 * @param userID
	 * @return
	 */
	public JSONObject verifyUserPassword(String password, int userID);

	/**
	 * 
	 * @param tableName
	 * @param visitID
	 * @return
	 */
	public boolean verifyVisitExists(String tableName, int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public int retrieveVisitTypeIDByVisitID(int visitID);

	/**
	 * 
	 * @param userID
	 * @param appointmentID
	 */
	public void updateAttendedByInAppointmentForPatient(int userID, int appointmentID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePersonalHistroyDiagnosis(int patientID);

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientCountryByID(int patientID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String retrieveJSPPageNameByVisitTypeID(int visitTypeID);

	/**
	 * 
	 * @param patientID
	 * @param lastOPDVisitID
	 * @param clinicID
	 * @param aptID
	 * @param VisitTypeID
	 * @return
	 */
	public List<PatientForm> retrieveDefaultPatientDetails(int patientID, int lastOPDVisitID, int clinicID, int aptID,
			int VisitTypeID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public int retrieveNewVisitTypeIDByClinicID(int clinicID);

	/**
	 * 
	 * @param patientID
	 * @param newVisitTypeID
	 * @return
	 */
	public int retrievVisitIDForNewVisitType(int patientID, int newVisitTypeID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public boolean verifyFollowUpVisitType(int visitTypeID);

	/**
	 * 
	 * @param tradeName
	 * @param dosage
	 * @param frequency
	 * @param noOfPills
	 * @param comment
	 * @param noOfDays
	 * @param visitID
	 * @param categoryID
	 * @return
	 */
	public String insertVisitPrescriptionDetails(String tradeName, double dosage, String frequency, double noOfPills,
			String comment, String noOfDays, int visitID, int categoryID);

	/**
	 * 
	 * @param visitTypeID
	 * @param clinicID
	 * @param clinicSuffix
	 * @param patientID
	 * @return
	 */
	public List<PatientForm> retrieveVisitBillingByVisitTypeID(int visitTypeID, int clinicID, String clinicSuffix,
			int patientID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveVisitBillingByVisitID(int visitID);

	/**
	 * 
	 * @param patientID
	 * @param lastOPDVisitID
	 * @param clinicID
	 * @param nextApptTaken
	 * @return
	 */
	public List<PatientForm> retrievePatientDetails(int patientID, int lastOPDVisitID, int clinicID, int nextApptTaken,
			int aptID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public double retrieveConsultationChargesFromPrescription(int visitID);

	/**
	 * 
	 * @param patientName
	 * @param practiceID
	 * @param clinicID
	 * @param searchCriteria
	 * @return
	 */
	public List<PatientForm> searchInactivePatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria);

	/**
	 * 
	 * @param practice
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> retrieveInactivePatientList(int practice, int clinicID);

	/**
	 * 
	 * @param diagnosis
	 * @return
	 */
	public List<String> retrieveDiagnosis(String diagnosis);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertVitalSignsDetailsForVisit(PatientForm patientForm);

	/**
	 * 
	 * @param symptom
	 * @param value
	 * @param visitID
	 * @return
	 */
	public String insertSymptomCheckDetailsForVisit(String symptom, String value, int visitID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrievesymptomCheckList(int visitID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateVitalSignsDetailsForVisit(PatientForm patientForm);

	/**
	 * 
	 * @param symptomID
	 * @return
	 */
	public JSONObject deleteSymptomDetails(int symptomID);

	public List<PatientForm> retrievelabReportDetailsList(int patientID, int clinicID);

	public String labReportDownload(int reportsID);

	public String insertPaymentDetails(PatientForm patientForm, String paymentType, int receiptID);

	public int retrieveLastEnteredReceiptID(int visitID);

	public String updatePaymentDetails(PatientForm form, String paymentType, int receiptID);

	public boolean verifyhasConsentValue(int visitTypeID);

	public String retrieveConsentDocFileName(int visitTypeID);

	public List<PatientForm> reytrieveLabInvastigationLstByVisitID(int visitID);

	public String updateConsentDocument(String fileName, int visitID);

	public String insertTradeNameInProductTable(String tradeName, int clinicID);

	public String insertOPDVisitForOpticianDetails(PatientForm form, int visitNumber);

	public int retrievVisitID(int patientID, int clinicID);

	public String retrieveClinicRegNoByPatientID(int patientID);

	public String insertInvestigationDetails(String testName, int visitID);

	public String insertTestNameInPVLabTestsTable(String testName);

	public List<PatientForm> retrieveInvestigationTestsList(int visitID);

	public JSONObject deleteInvestigation(int investigationID);
	
	public JSONObject deleteDiagnostic(int diagnosticID);
	
	public JSONObject deleteProcedure(int procedureID);

	public int retrieveNextVisitDays(int visitTypeID);

	public boolean checkEyewearDetails(int opticinID);

	public String updateVisitColumns(int nextVisitDays, String advice, int visitID);

	public int retrieveLastVisitID(int patientID, int clinicID);

	public JSONObject retrieveCategoryWithDrugName(String tradeName, int clinicID);

	public JSONObject retrieveFrequencyValue(int practiceID);

	public int retrievePrescriptionID(String tradeName, int visitID);

	public String insertFrequencyDetails(String frequency, int numberOfDays, int prescriptionID);

	public JSONObject retrieveFrequencyDetailsByPrescriptionID(int prescriptionID);

	public JSONObject deleteFrequencyDetails(int frequencyDetailsID);

	public String updateReferralLetter(String referralLetter, int visitID);

	public String insertComplaints(String string, int i, String string3, int visitID);

	public String insertMedicalHistory(String string, String string2, int patientID);

	public String insertCurrentMedication(String string, int i, String string2, int patientID);

	public List<PatientForm> retrievecomplaintList(int visitID);

	public List<PatientForm> retrieveMedicalHistoryList(int patientID);

	public List<PatientForm> retrieveCurrentMedicationList(int patientID);

	public List<PatientForm> retrievePrescriptionList(int patientID, int visitID);

	public JSONObject deleteComplaintDetails(int complaintID);

	public JSONObject deleteMedicalHistoryDetails(int historyID);

	public JSONObject deleteCurrentMedicationDetails(int medicationID);

	public boolean CheckVisitTypeNewVisitCheck(int visitTypeID);

	public String verifyMedicalCertificate(int visitID);

	public String verifyReferralLetter(int visitID);

	public int retrieveLastOPDVisitID(int patientID, int clinicID);

	public String insertPatientSurvey(PatientForm patientForm, int clinicID);

	public List<PatientForm> retrieveSurveyDetails(PatientForm patientForm, int clinicID, int lastVolunteerID);

	public int retrieveLastVolunteerID(int clinicID);

	public String updateSurvey(PatientForm patientForm);

	public String verifyUserCredentials(LoginForm form);

	public String updateUsernameAndPasswordIntoPatientTableByID(String username, String password, int patientID);

	public String retrievePatientFirstMiddleLastNameByID(int patientID);

	public String retrieveUsernameByID(int patientID);

	public String retrievePasswordByID(int patientID);

	public int retrievePatientID1(PatientForm form, int practiceID);

	public String retrieveAge(PatientForm form);

	public String retrieveUsernameBYuserID(int userID);

	public String retrieveClinicNameByClinicID(int clinicID);

	public int verifyConsentDetailsExist(int userID);

	public String addConsentDetails(PatientForm patientForm, int userID);

	public String insertDiagnosisInPVDiagnosisTable(String cancerType);

	public int retrievePracticeIDByClinicID(int clinicID);

	// public PatientForm getUserPatientDetail(String string);

	public String retrieveProfilePic(int patientID);

	public int retrieveClinicIDByPatientID(int patientID);

	public List<PatientForm> retreivePatientProfileDetails(int patientID);

	public List<PatientForm> retrieveCRFList(int visitID, String formName);

	public String verifyFrameDetailsExist(int visitID, String tableName);

	public List<PatientForm> searchPatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria);

	public String insertPatientVisit(PatientForm patientForm, int visitNumber, int i);

	public int retrieveLastEnteredVisitIDByVisitNumber(int visitNumber, int patientID, int clinicID);

	public String updateMedicalHistory(PatientForm patientForm);

	public String insertMedicalHistory(PatientForm patientForm);

	public String updateFamilyHistory(PatientForm patientForm);

	public String insertFamilyHistory(PatientForm patientForm);

	public boolean verifyOtherComplaintDataExists(String otherComplaint);

	public String insertPVComplaints(String otherComplaint);

	public String insertPresentComplaints(String finalComplaintsNew, int visitID);

	public String insertVitalSigns(PatientForm patientForm);

	public String insertOnExaminationDetails(PatientForm patientForm);

	public String insertInvestigationDetails(String findings, int visitID, String findingDate, String findingType,
			String reportFileName);

	public void insertVisitStatus(int visitID, int aptID, int patientID, String inTime, String outTime, String status,
			int clinicID);

	public List<PatientForm> retrieveGeneralHospitalPatientDetailsForNewVisit(int patientID, int lastVisitID,
			int clinicID, int aptID);

	public List<PatientForm> retrieveVisitBillingByVisitID1(int visitID);

	public List<PatientForm> retrieveVisitBillingByVisitTypeID(int visitTypeID, int clinicID, String clinicSuffix);

	public String retrievePresentComplaintsOfLastVisit(int lastVisitID);

	public HashMap<String, String> getPVComplaints();

	public String retrieveCategoryListOfLastVisit(int lastVisitID);

	public List<PatientForm> retrievemedicalHistoryListOfLastVisit(PatientForm patientForm, int patientID);

	public List<PatientForm> retrieveFamilyHistoryListOfLastVisit(PatientForm patientForm, int patientID);

	public List<PatientForm> retrievePersonalHistoryListOfLastVisit(PatientForm patientForm, int patientID);

	public List<PatientForm> retrieveOnExaminationListByVisitID(int lastVisitID);

	public List<PatientForm> retrieveInjectionPrescriptionList(int patientID, int lastVisitID, String string);

	public boolean verifyDataExistsInvestigation(int visitID, String string);

	public String updateGeneralHospitalVitalSignsWeight(double weight, int visitID);

	public String insertGeneralHospitalVitalSignsWeight(double weight, int visitID);

	public String updateGeneralHospitalPrescribedInvestigatios(PatientForm patientForm, int visitID);

	public String insertGeneralHospitalPrescribedInvestigatios(PatientForm patientForm, int visitID);

	public String updateGeneralHospitalPhysiotherapy(String physiotherapy, int visitID);

	public String insertGeneralHospitalPhysiotherapy(String physiotherapy, int visitID);

	public String insertGeneralHospitalPrescriptionVisitDetail(PatientForm patientForm, int visitID);

	public String insertGeneralHospitalVisitPrescriptionDetails(int parseInt, double dosageBefore, double dosageAfter,
			double duration, int visitID, double dosageAfterDinner, String string);

	public boolean verifyVisitExistsNew(String string, int visitID);

	public String updateBillDetails(PatientForm form, int visitID);

	public boolean verifyTransactionDetailsExists(String string, String string2, int receiptID);

	public String updateTransactionDetails(int receiptID, double chargeQty, double chargeAmt);

	public String insertTransactionDetails(String string, int receiptID, double parseDouble, double parseDouble2,
			double parseDouble3);

	public String inserGeneralHospitalBillDetails(PatientForm form, int visitID);

	public String retrieveReceiptDetailsByVisitID(int visitID);

	public String generalHospitalLabReportDownload(int reportsID);

	public int retrieveRoomTypeByVisitID(int lastVisitID);

	public List<PatientForm> retrieveIPDChargesByVisitID(int visitID, String tariff);

	public List<PatientForm> retrieveOTChargesByVisitID(int visitID);

	public boolean verifyIPDDischargeVisitType(int visitTypeID);

	public int retrieveNewVisitRef(int visitID);

	public String retrievePaymentType(int newVisitID);

	public List<PatientForm> retrieveIPDTransactionsByVisitID(int newVisitID, String tariff);

	public List<PatientForm> retrieveIPDChargesDetailsByNewVisitID(int newVisitID, String tariff);

	public List<PatientForm> retrieveOTChargesDetailsByNewVisitID(int newVisitID);

	public List<PatientForm> retrieveIPDBillCharges(int visitTypeID, int clinicID, String clinicSuffix, int newVisitID);

	public JSONObject insertBillDetails(PatientForm patientForm);

	public JSONObject retrieveBillDetail(int visitID, String serviceCharge);

	public JSONObject retrieveTradeNameForPrescriptionBYProductID(String tradeName, String category);

	public JSONObject deleteGeneralHospitalPrescRow(int prescriptionID);

	public JSONObject deleteBilling(int billID);

	public JSONObject retrievePrscriptionDetail(int visitID);

	public List<PatientForm> retrieveGeneralHospitalPatientDetailsForExistiongVisit(int patientID, int visitID,
			int clinicID);

	public List<PatientForm> retrieveOtherFindingsListByVisitID(int visitID);

	public List<PatientForm> retrieveVitalSignsListByVisitID(int visitID);

	public String updateGeneralHospitalPatientVisit(PatientForm patientForm, int visitNumber, int i);

	public String updatePresentComplaints(String finalComplaintsNew, int visitID);

	public String updateVitalSigns(PatientForm patientForm);

	public String updateOnExaminationDetails(PatientForm patientForm);

	public String updateInvestigationDetails(String biopsyFindings, int visitID, String string);

	public void updateAppointmentVisitType(int aptID, int visitTypeID);

	public JSONObject retrieveOTChargesDisbursementList(int otChargeID);

	public boolean verifyNewVisitType(int visitTypeID);

	public int retrieveLastNewVisitID(int patientID, int clinicID);

	public String insertIPDPatientVisit(PatientForm form);

	public String insertIPDCharges(String chargeType, String chargeName, double parseDouble, double parseDouble2,
			double parseDouble3, int visitID);

	public String insertOTCharges(String operation, String otDateTime, String consultant, String anaesthetist,
			String otAssistant, double parseDouble, int visitID);

	public int retrieveLastOTChargeID(int visitID, String operation);

	public String insertChargeDisbursement(int oTChargeID, String chargeType, double charges);

	public List<PatientForm> retrieveIPDPatientDetails(int patientID, int clinicID, int visitID);

	public String retrieveBillingTypeByVisitTypeID(int visitTypeID);

	public String updateIPDReceipt(PatientForm form);

	public String updateIPDTransactions(double parseDouble, int chargeID, double parseDouble2, double d, double e,
			double parseDouble3, String chargeType, String chargeName, String otDateTime);

	public String insertIPDTransactions(double parseDouble, int receiptID, double parseDouble2, double d, double e,
			double parseDouble3, String chargeType, String chargeName, String otDateTime);

	public boolean verifyPaymentDataExists(int receiptID);

	public String insertIPDReceipt(PatientForm form);

	public HashMap<String, Object> retrieveIPDTarrifDetailsByID(int tarrifChargeID);

	public HashMap<String, Object> retrieveIPDConsultantDetailsByID(int consultantChargeID);

	public HashMap<Integer, String> retrieveIPDTarrifChargesList(String active, int roomTypeID);

	public HashMap<Integer, String> retrieveIPDConsultantChargesList(String active, int roomTypeID);

	public String updateIPDPatientVisit(PatientForm form);

	public String updateIPDCharges(String chargeType, String chargeName, double parseDouble, double parseDouble2,
			double parseDouble3, int parseInt);

	public String updateOTCharges(String operation, String otDateTime, String consultant, String anaesthetist,
			String otAssistant, double parseDouble, int parseInt);

	public String deleteRow(String check, int deleteID);

	public String updateGeneralHospitalPersonalHistory(PatientForm patientForm);

	public String insertGeneralHospitalPersonalHistory(PatientForm patientForm);

	public boolean verifyNewOrFollowUpVisitForPatient(int lastVisitID, int clinicID);

	public JSONObject retrieveIPDVisitTypeExceptNewVisit(int clinicID);

	public JSONObject retrieveNonIPDVisitType(int clinicID);

	public List<PatientForm> retrieveGenaralClinicPatientDetailsForNewVisit(int patientID, int lastVisitID,
			int clinicID);

	public List<PatientForm> retrieveGeneralClinicPatientDetails(int patientID, int visitID, int clinicID);

	public String insertGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef);

	public String updateMedicalHistoryDetails(PatientForm patientForm);

	public String insertMedicalHistoryDetails(PatientForm patientForm);

	public String insertPresentComplaintsDetails(String comments, int visitID);

	public String insertOnExaminationDetails1(String onExamination, int visitID);

	public String updateGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int i);

	public boolean verifyVisitDataExists(int visitID, String tableName);

	public String updatePresentComplaints1(String comments, int visitID);

	public String updateOnExaminationDetails1(String onExamination, int visitID);

	public List<PatientForm> retrieveGenaralClinicPatientDetailsForNewIPDVisit(int patientID, int lastVisitID,
			int clinicID);

	public List<PatientForm> retrieveGenaralClinicPatientDetailsForIPDVisit(int patientID, int visitID, int clinicID);

	public String insertIPDGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef);

	public String updateGenaralClinicPatientIPDVisit(PatientForm patientForm, int visitNumber, int i);

	public int retrieveProductStockID(String productName, int clinicID);

	public double retrieveProductNetStock(String productName, int stockID, int clinicID);

	public int retrieveNewStockID(String productName, int clinicID);

	public String updateNetStock(double netStock, int stockID);

	public double retrieveTotalNetStockByProductName(String productName, int clinicID);

	public String updateNetStockAndStatus(double i, int stockID);

	public String updateNetStockWhenRowDelete(int stockID, int quantity);

	public int retrieveVisitTypeIDByApptID(int aptID);

	public String retrieveAppoDateByApptID(int aptID);

	public JSONObject deleteLabReport(int reportsID);

	public String deleteGeneralHospitalPrescribedInvestigatios(int visitID);

	public HashMap<String, HashMap<String, String>> retrieveGroupLabTestsList(String columnName, String searchTest,
			int practiceID, int groupCheck);

	public List<PatientForm> retrievePVTests(String normalRangeColName);

	public List<String> getLabDefaultValueList(String testName);

	public List<PatientForm> retrieveLabPatientDetails(int patientID, int lastOPDVisitID, int clinicID, int aptID);

	public List<PatientForm> retrieveBillDetail(int visitID, int visitTypeID, int clinicID, String clinicSuffix);

	public List<PatientForm> retrieveTransactionListByVisitID(int visitID);

	public List<PatientForm> retrieveTransactionDetailsByVisitID(int visitID);

	public List<PatientForm> retrieveTransactionDetailsForGroupTestByVisitID(int visitID);

	public String retrieveLabTestByVisitID(int visitID);

	public String insertLabPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef);

	public String insertLabInvestigationDetails(String test, String value, String normalRange, int visitID,
			String investigationDate, double rate, int groupCheck, double groupRate, String groupName, String subGroup,
			String testType);

	public String updatetReceiptDetails(PatientForm patientForm);

	public boolean verifyOtherDataExists(int ID, String tableName, String refColumnName);

	public String insertTransactionDetails(double quantity, int receiptID, double amount, double discount,
			double taxAmount, String activityStatus, int prescriptionID, int productID, String test, double rate);

	public String insertReceiptDetails(PatientForm patientForm);

	public int retrieveLastReceiptID(int visitID);

	public boolean verifyResultAddedForVisit(int visitID);

	public List<PatientForm> retrieveExistingLabTestByVisitID(int visitID);

	public String updateLabPatientVisit(PatientForm patientForm);

	public String updateLabInvestigationDetails(int investigationID, String value);

	public String retrievePatientFNameLNameByID(int patientID);

	public String retrieveToEmailByPractiecID(int practiceID);

	public String retrieveFromEmailByPractiecID(int practiceID);

	public String retrieveFromEmailPassByPractiecID(int practiceID);

	public LinkedHashMap<String, String> retrieveSingleLabTestListByVisitID(int visitID);

	public LinkedHashMap<String, LinkedHashMap<String, String>> retrieveGroupLabTestByVisitID(int visitID, int practiceID);

	public String retrieveTestNormalRangeDesc(String test, String groupName);

	public String retrieveTestRemark(String test, String groupName);

	public boolean isNormalValueExclude(String test, String groupName);

	public boolean showNormalRange(String test, String groupName);

	public HashMap<String, String> getlabTestValueList(String string);

	public JSONObject retrieveGroupValues(String groupName, String columnName, int practiceID);

	public JSONObject retrieveLabDefaultValueList(String test);

	public JSONObject deleteLabInvestigationTest(int testNameID);

	public String insertVisitBDPData(PatientForm patientForm, int visitNumber);

	public String retrieveTemplate(int lastOPDVisitID);

	public int retrieveclinicianID(int visitID);

	public int retrieveReportID(int visitID);

	public int retrieveclinicianIDBYAptID(int aptID);

	public String UpdateVisitBDPData(PatientForm patientForm);

	public List<PatientForm> retrieveTransactionDetailsByVisitID1(int visitID, String careType);

	public String insertVisitUSGData(PatientForm patientForm, int visitNumber);

	public String UpdateUSGVisitBDPData(PatientForm patientForm);

	public JSONObject retrivePatientDetailsByID(int patientID, int visitID, String pcpNDTLocalPath);
	
	public Boolean retrieveNormalBoolVal(int visitID);

	public String UpdatePCPNDTStatus(int patientID, int visitID);

	public String insertSonsDaughtersData(PatientForm patientForm);

	public String getSonAge(int visitID);

	public String getDaughterAge(int visitID);
	
	public String insertIsNormalValue(int visitID, String textNormal);

	/**
	 * 
	 * @param visitId
	 * @return
	 */
	public List<PatientForm> retrieveOrthoDiagnosisList(int visitId);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveOrthoBillingItemList(int visitID);

	/**
	 * 
	 * @param itemName
	 * @param rate
	 * @param amount
	 * @param quantity
	 * @param receiptID
	 * @return
	 */
	public String insertReceiptItemDetails(String itemName, double rate, double amount, int quantity, int receiptID);

	/**
	 * 
	 * @param itemName
	 * @param rate
	 * @param amount
	 * @param quantity
	 * @param receiptItemID
	 * @return
	 */
	public String updateReceiptItemDetails(String itemName, double rate, double amount, int quantity,
			int receiptItemID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicPhoneNo(int clinicID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrievePracticeBucketName(int practiceID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String insertCycloplegicRefraction(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateCycloplegicRefraction(PatientForm patientForm);

	/**
	 * 
	 * @param roomName
	 * @param practiceID
	 * @return
	 */
	public int retrieveIPDRoomTypeID(String roomName, int practiceID);

	/**
	 * 
	 * @param active
	 * @param roomTypeID
	 * @return
	 */
	public HashMap<Integer, String> retrieveIPDTarrifChargesListByRoomType(String active, int roomTypeID);

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> retrieveOPDChargesList(int practiceID);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param clinicID
	 * @param practiceID
	 * @return
	 */
	public JSONObject retrieveRoomTypeForBooking(String startDate, String endDate, int clinicID, int practiceID);

	/**
	 * 
	 * @param receiptItemID
	 * @return
	 */
	public JSONObject deleteReceiptItemByID(int receiptItemID);

	/**
	 * 
	 * @param patientName
	 * @param practiceID
	 * @param clinicID
	 * @param searchCriteria
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<PatientForm> fetchPharmaPatient(String patientName, int practiceID, int clinicID, String searchCriteria,
			String fromDate, String toDate);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, String> retrieveIOLTarrifChargesListByRoomType(int clinicID);

	public String insertReferredByDoctor(String referredBy, int practiceID);

	public int retrieveReferredByDoctorID(String refDoctor);

	public String insertPECMedicalHistory(PatientForm form, int patientID);

	public String updatePECMedicalHistory(PatientForm form);

	public int retrieveProductID(int visitID);

	public String updateIOLChargeStock(int productID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<PatientForm> fetchPharmaPatient(int clinicID);

	/**
	 * 
	 * @param visitID
	 * @param clinicID
	 * @param clinicSuffix
	 * @return
	 */
	public JSONObject retreivePharmaBill(int visitID, int clinicID, String clinicSuffix);

	/**
	 * 
	 * @param clinicID
	 * @param searchName
	 * @return
	 */
	public List<PatientForm> fetchPharmaPatient(int clinicID, String searchName);

	/**
	 * 
	 * @param apptDate
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveNAAppointmentSlots(String apptDate, int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @param apptDate
	 * @param apptFromTime
	 * @return
	 */
	public int retrieveAppointmentNumber(int clinicID, String apptDate, String apptFromTime);

	/**
	 * 
	 * @param patientID
	 */
	public void updateDoneAppointmentStatus(int patientID, int visitID);

	/**
	 * 
	 * @param visitID
	 * @param isConsultationDone
	 */
	public void updateConsultationFlagInVisit(int visitID, int isConsultationDone);

	/**
	 * 
	 * @param patientForm
	 */
	public void insertVisitEditHistoryDetails(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @param careType
	 * @return
	 */
	public String retrieveVisitLastEdittedByDetails(int visitID, String careType);

	/**
	 * 
	 * @param mobile
	 * @param clinicID
	 * @return
	 */
	public boolean verifyOTPVerified(String mobile, int clinicID);

	/**
	 * 
	 * @param OTP
	 * @param mobileNo
	 * @param clinicID
	 * @return
	 */
	public String insertOTPDetails(String OTP, String mobileNo, int clinicID);

	/**
	 * 
	 * @param mobile
	 * @param clinicID
	 * @return
	 */
	public String validateOTP(String mobile, int clinicID);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveDiagnosticList(int visitID);
	
	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveProceduresList(int visitID);

	/**
	 * 
	 * @param diagnostic
	 * @param visitID
	 * @return
	 */
	public String insertDiagnosticDetails(String diagnostic, int visitID);
	
	/**
	 * 
	 * @param procedure
	 * @param visitID
	 * @return
	 */
	public String insertProcedureDetails(String procedure, int visitID);

	/**
	 * 
	 */
	public List<String> retrieveTestTypeForVisitByVisitID(int visitID);

	/**
	 * 
	 * @param templateData
	 * @param temlpateID
	 * @param visitID
	 * @param clinicianID
	 * @param investigationID
	 * @return
	 */
	public String insertVisitTemplateData(String templateData, int temlpateID, int visitID, int clinicianID,
			int investigationID);

	/**
	 * 
	 * @param templateData
	 * @param temlpateID
	 * @param visitID
	 * @param clinicianID
	 * @param investigationID
	 * @param visitTemplateID
	 * @return
	 */
	public String updateVisitTemplateData(String templateData, int temlpateID, int visitID, int clinicianID,
			int investigationID, int visitTemplateID);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateBDPPatient(PatientForm patientForm);

	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public List<PatientForm> retrieveVisitTemplateData(int visitID);

	/**
	 * 
	 * @param investigationID
	 * @return
	 */
	public PatientForm retrieveVisitTemplateDataByInvestigationID(int investigationID);
	
	/**
	 * 
	 * @param investigationID
	 * @return
	 */
	public String updateDispatchedDetails(int investigationID);
	
	/**
	 * 
	 * @param visitID
	 * @return
	 */
	public boolean verifyPathologyTestAvailableForVisit(int visitID);
	
	public int[] retrieveIsDiapatchedDetails(int visitID);
	
	public int retrieveLastOPDVisitIDByCareType(int patientID, int clinicID, String careType);
	
	public int retrieveTestIDByTestName(String testName, int practiceID);
	
	public String retrieveGroupRemark(String groupName);
	
	public TreeMap <String, TreeMap <String, TreeMap <Integer, PatientForm>>>  manupulateSubGroupData(List<PatientForm> labTestListNew, LoginForm loginForm);
	
}
