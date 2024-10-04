package com.edhanvantari.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.edhanvantari.form.ClinicForm;
import com.edhanvantari.form.ConfigurationForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.form.RegistrationForm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface eDhanvantariServiceInf {

	/**
	 * 
	 * @param registrationForm
	 * @return
	 */
	public String editUserDetail(RegistrationForm registrationForm, String realPath, HttpServletRequest request);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String registerUser(RegistrationForm form, String realPath, HttpServletRequest request);

	/**
	 * 
	 * @param form
	 * @param practiceID
	 * @return
	 */
	public String addPatient(PatientForm form, int practiceID);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String editPatientDetail(PatientForm form);

	/**
	 * 
	 * @param form
	 * @param practiceID
	 * @return
	 */
	public String insertPatient(PatientForm form, int practiceID);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String addNewPrescription(PatientForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	// public String addNewBill(PatientForm form);

	/**
	 * 
	 * @param form
	 * @param loginForm
	 * @return
	 */
	public String addOptician(PatientForm form);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String addNewIPDVisit(PatientForm patientForm, String realPath);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateIPDVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String addNewAppointment(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @return
	 */
	public String addLabReport(PatientForm patientForm, String realPath);

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @return
	 */
	public String downloadLabReport(PatientForm patientForm, String realPath);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String addNewGenPhyVisit(PatientForm patientForm);

	/**
	 * 
	 * @param patientForm
	 * @param visitType
	 * @return
	 */
	public List<PatientForm> addNewOPDVisit(PatientForm patientForm, String visitType);

	/**
	 * 
	 * @param patientForm
	 * @return
	 */
	public String updateOptician(PatientForm patientForm);

	/**
	 * 
	 * @param clinicForm
	 * @param realPath
	 * @param url
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String addPractice(ClinicForm clinicForm, String realPath, String url)
			throws FileNotFoundException, IOException;

	/**
	 * 
	 * @param clinicForm
	 * @param realPath
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String editPractice(ClinicForm clinicForm, String realPath, String url) throws UnsupportedEncodingException;

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String deleteVisitType(int visitTypeID);

	/**
	 * 
	 * @param clinicForm
	 * @param clinicID
	 * @return
	 */
	public JSONObject addAppointment(ClinicForm clinicForm, int clinicID);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String addProduct(PrescriptionManagementForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String editProduct(PrescriptionManagementForm form);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String editProductPrice(PrescriptionManagementForm form);

	/**
	 * 
	 * @param managementForm
	 * @param userID
	 * @return
	 */
	public String addStockReceipt(PrescriptionManagementForm managementForm, int userID);

	/**
	 * 
	 * @param managementForm
	 * @param userID
	 * @return
	 */
	public String editStockReceipt(PrescriptionManagementForm managementForm, int userID);

	/**
	 * 
	 * @param managementForm
	 * @param userID
	 * @return
	 */
	public String removeStock(PrescriptionManagementForm managementForm, int userID);

	/**
	 * 
	 * @param registrationForm
	 * @param realPath
	 * @param request
	 * @return
	 */
	public String editUserProfile(RegistrationForm registrationForm, String realPath, HttpServletRequest request);

	/**
	 * 
	 * @param registrationForm
	 * @param realPath
	 * @param request
	 * @return
	 */
	public String editUserProfileDetail(RegistrationForm registrationForm, String realPath, HttpServletRequest request);

	/**
	 * 
	 * @param form
	 * @param userID
	 * @return
	 */
	public String configureBill(PatientForm form, int userID);

	/**
	 * 
	 * @param patientForm
	 * @param excelFileName
	 * @param realPath
	 * @return
	 */
	public String geterateClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String disableProduct(PrescriptionManagementForm form);

	public String addCategory(PrescriptionManagementForm form);

	public String editCategory(PrescriptionManagementForm form);

	public String addSupplier(PrescriptionManagementForm form);

	public String editSupplier(PrescriptionManagementForm form);

	public String addInstruction(ConfigurationForm configurationForm);

	public String editInstruction(ConfigurationForm configurationForm);

	public String addTax(PrescriptionManagementForm form);

	public String editTax(PrescriptionManagementForm form);

	public String addFrequency(ConfigurationForm configurationForm, int practiceID);

	public String editFrequency(ConfigurationForm configurationForm, int practiceID);

	public String editGenPhyVisit(PatientForm patientForm);

	public String configureVisitType(ClinicForm form, String realPath);

	public String configureEditVisitType(ClinicForm form);

	public String addFrequencyDetails(PatientForm patientForm);

	public String geterateClinicalDataReportForAyurved(PatientForm patientForm, String excelFileName, String realPath);

	public String executeClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath);

	public String updateClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath);

	public String addNewSurvey(PatientForm patientForm, int clinicID);

	public String editSurvey(PatientForm patientForm);

	public String configureConsentDetails(PatientForm patientForm, int userID);

	public String editClinicUploadImage(ClinicForm form);

	public String addRoomType(PrescriptionManagementForm form);

	public String editRoomType(PrescriptionManagementForm form);

	public String addIPDCharges(PrescriptionManagementForm form);

	public String editIPDCharges(PrescriptionManagementForm form);

	public String addIPDConsultantCharges(PrescriptionManagementForm form, int practiceID);

	public String editIPDConsultantCharges(PrescriptionManagementForm form);

	public String addTestDetails(ConfigurationForm configurationForm);

	public String editTestDetails(ConfigurationForm configurationForm);

	public String addOPDCharges(PrescriptionManagementForm form);

	public String editOPDChargesDetails(PrescriptionManagementForm form);

	public String addGeneralHospitalVisit(PatientForm patientForm);

	public String addNewGeneralHospitalPrescription(PatientForm patientForm);

	public String configureGeneralHospitalBill(PatientForm patientForm);

	public String updateGeneralHospitalVisit(PatientForm patientForm);

	public String addGeneralHospitalIPDVisit(PatientForm patientForm);

	public String addGeneralHospitalIPDBill(PatientForm patientForm);

	public String editGeneralHospitalIPDVisit(PatientForm patientForm);

	public String verifyIPDVisitType(int patientID, int clinicID);

	public String addNewGenaralClinicVisitDetails(PatientForm patientForm);

	public String editNewGenaralClinicVisitDetails(PatientForm patientForm);

	public String addNewIPDGenaralClinicVisitDetails(PatientForm patientForm);

	public String editIPDGenaralClinicVisitDetails(PatientForm patientForm);

	public String addSMSTemplate(ConfigurationForm configurationForm);

	public String editSMSTemplate(ConfigurationForm configurationForm);

	public String addNewInvestigation(PatientForm form);

	public String addLabTest(ConfigurationForm configurationForm, int practiceID);

	public String editLabTest(ConfigurationForm configurationForm, int practiceID);

	public String addNewLabVisit(PatientForm patientForm);

	public String addLabBill(PatientForm form);

	public String addReferringDoctor(ConfigurationForm configurationForm, int practiceID);

	public String edirReferringDoctor(ConfigurationForm configurationForm);

	public String editLabVisit(PatientForm patientForm);
	
	public String editBDPVisit(PatientForm patientForm);

	public JSONObject retrieveLabBillDetails(int visitID, double consultationCharge, int clinicID, String clinicSuffix,
			int visitTypeID, String careType);

	public String addTemplate(ConfigurationForm configurationForm);

	public String editTemplate(ConfigurationForm configurationForm);

	public String addBDPReportData(PatientForm patientForm);

	public String UpdateBDPReportData(PatientForm patientForm);

	public String addUSGReportData(PatientForm patientForm);

	public String UpdateUSGBDPReportData(PatientForm patientForm);

	public String addOTType(PrescriptionManagementForm form);

	public String editOTType(PrescriptionManagementForm form);

	public String addServiceType(PrescriptionManagementForm form);

	public String editServiceType(PrescriptionManagementForm form);

	public String addRoomCharges(PrescriptionManagementForm form, int practiceID);

	public String editRoomCharges(PrescriptionManagementForm form);

}
