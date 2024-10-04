package com.edhanvantari.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.edhanvantari.daoImpl.LeaveDAOImpl;
import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoInf.LeaveDAOInf;
import com.edhanvantari.daoInf.ListnerDOAInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.form.PatientForm;

/**
 * 
 * @author kovid bioanalytics
 *
 */
public class SMSSender {

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	String username = configXMLUtil.getSMSUsername();
	String password = configXMLUtil.getSMSPassword();
	String URL = configXMLUtil.getSMSURL();
	String senderID = configXMLUtil.getSMSSenderID();

	String status = "error";

	PatientDAOInf patientDAOInf = null;

	LeaveDAOInf leaveDAOInf = null;

	/**
	 * 
	 * @param diagnosis
	 * @param message
	 * @param practiceID
	 * @param check
	 * @param clinicID
	 * @param mobileNo
	 * @return
	 */
	public String sendSMSToAllPatientBasedOnDiagnosis(String diagnosis, String message, int practiceID, String check,
			int clinicID, String mobileNo) {

		patientDAOInf = new PatientDAOImpl();

		try {

			/*
			 * Check whether mobileNo contains All as a part of it, if so, send message to
			 * all patients
			 */
			if (mobileNo.contains("All")) {

				/*
				 * Check the value of check, if it is practice, search patient based on
				 * diagnosis for a particular practice, and if it is clinic, then search patient
				 * for a clinic
				 */
				if (check.equals("Practice")) {

					List<String> mobileNoList = patientDAOInf.retrievePatientMobileNoBasedOnDiagnosis(diagnosis,
							practiceID);

					String newMobNo = "";

					/*
					 * Checking whether mobile list is null or not, if not, iterating over the
					 * mobile list in order to send msg to all mobile nos.
					 * 
					 */
					if (mobileNoList == null) {
						System.out.println("No patient found for diagnosis..'" + diagnosis + "'");

						status = "success";

						return status;
					} else {

						for (String mobNo : mobileNoList) {
							newMobNo += mobNo + ",";
						}

						if (newMobNo != null && newMobNo.length() > 0
								&& newMobNo.charAt(newMobNo.length() - 1) == ',') {
							newMobNo = newMobNo.substring(0, newMobNo.length() - 1);
						}

						System.out.println("Mobile nos are..." + newMobNo);

						String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
								+ URLEncoder.encode(password, "UTF-8") + "&source="
								+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
								+ URLEncoder.encode(newMobNo, "UTF-8") + "&message="
								+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

						URL url2 = new URL(SMSURL);

						HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

						if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

							System.out.println("SMS sent seuccessfully.");

							status = "success";

							return status;

						} else {

							System.out.println("Failed to send SMS.");

							status = "error";

							return status;

						}

					}

				} else {

					List<String> mobileNoList = patientDAOInf
							.retrievePatientMobileNoBasedOnDiagnosisForClinic(diagnosis, clinicID);

					String newMobNo = "";

					/*
					 * Checking whether mobile list is null or not, if not, iterating over the
					 * mobile list in order to send msg to all mobile nos.
					 * 
					 */
					if (mobileNoList == null) {
						System.out.println("No patient found for diagnosis..'" + diagnosis + "'");

						status = "success";

						return status;
					} else {

						for (String mobNo : mobileNoList) {
							newMobNo += mobNo + ",";
						}

						if (newMobNo != null && newMobNo.length() > 0
								&& newMobNo.charAt(newMobNo.length() - 1) == ',') {
							newMobNo = newMobNo.substring(0, newMobNo.length() - 1);
						}

						System.out.println("Mobile nos are..." + newMobNo);

						String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
								+ URLEncoder.encode(password, "UTF-8") + "&source="
								+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
								+ URLEncoder.encode(newMobNo, "UTF-8") + "&message="
								+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

						URL url2 = new URL(SMSURL);

						HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

						if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

							System.out.println("SMS sent seuccessfully.");

							status = "success";

							return status;

						} else {

							System.out.println("Failed to send SMS.");

							status = "error";

							return status;

						}

					}

				}

			} else {

				System.out.println("Mobile nos are..." + mobileNo);

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send SMS.");

					status = "error";

					return status;

				}

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send SMS To All Patient Based On Diagnosis: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param patientID
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param apptDate
	 * @param aptTime
	 * @param appointmentType
	 * @return
	 */
	public String sendAppointmentSMS(int patientID, String mobileNo, int practiceID, int clinicID, String apptDate,
			String aptTime, String appointmentType) {

		patientDAOInf = new PatientDAOImpl();

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		// Retrieving patinet's registrationNo from ClinicRegistration table
		// based on clinicID and PatientID
		String clinicRegNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID, patientID);

		/*
		 * Checking type of appointment, and according to that setting message text to
		 * be sent to patient
		 */
		if (appointmentType.equals(ActivityStatus.SCHEDULED)) {

			SMSText = "Appointment for " + clinicRegNo + " for " + practiceName + " has been scheduled at " + clinicName
					+ " at " + apptDate + " " + aptTime + ". Please contact our clinic in case of any changes";

			// SMSText = "Your appointment for " + practiceName + " has been
			// scheduled at " + clinicName + " at "
			// + apptDate + " " + aptTime + ". Please contact our clinic in case
			// of any changes.";

		} else if (appointmentType.equals(ActivityStatus.UPDATED)) {

			SMSText = "Appointment for " + clinicRegNo + " for " + practiceName + " has been scheduled at " + clinicName
					+ " has been updated to " + apptDate + " " + aptTime
					+ ". Please contact our clinic in case of any changes.";

			// SMSText = "Your appointment for " + practiceName + " has been
			// scheduled at " + clinicName
			// + " has been updated to " + apptDate + " " + aptTime
			// + ". Please contact our clinic in case of any changes.";

		} else if (appointmentType.equals(ActivityStatus.CANCELLED)) {

			SMSText = "Appointment for " + clinicRegNo + " for " + practiceName + " at " + clinicName + " scheduled at "
					+ apptDate + " " + aptTime
					+ " has been cancelled. Please contact our clinic in case of any changes.";

			// SMSText = "Your appointment for " + practiceName + " at " +
			// clinicName + " scheduled at " + apptDate + " "
			// + aptTime + " has been cancelled. Please contact our clinic in
			// case of any changes.";

		} else if (appointmentType.equals(ActivityStatus.REMINDER)) {

			// Retrieving patient's first name and last name
			String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

			SMSText = "Dear " + patientName + "(" + clinicRegNo + "), you have an appointment scheduled today at  "
					+ practiceName + ", " + clinicName + " clinic at " + aptTime + ".";

			// SMSText = "Dear " + patientName + ", you have an appointment
			// scheduled today at " + practiceName + ", "
			// + clinicName + " clinic at " + aptTime + ".";

		}

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Appointment " + appointmentType + " SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send Appointment " + appointmentType + " SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Appointment SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param visitID
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param type
	 * @return
	 */
	public String sendPrescriptionORBillSMS(int visitID, String mobileNo, int practiceID, int clinicID, String type) {

		patientDAOInf = new PatientDAOImpl();

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic name based on clinicID
		// String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		/*
		 * Checking what type is;i.e.either Bill or Prescription, and according to that
		 * setting message text
		 */

		if (type.equals(ActivityStatus.BILL)) {

			// retrieving bill amount based on visitID from Receipt table
			double billAmt = patientDAOInf.retrieveBillAmountByVisitID(visitID);

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			SMSText = "The bill for your visit with " + practiceName + " at " + dateAndTime + " is " + billAmt + ".";

			try {

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("Bill SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send bill SMS.");

					status = "error";

					return status;

				}

			} catch (Exception exception) {
				
				StringWriter stringWriter = new StringWriter();

	            exception.printStackTrace(new PrintWriter(stringWriter));

	            // calling exception mail send method to send mail about the exception details
	            // on info@kovidbioanalytics.com
	            EmailUtil emailUtil1 = new EmailUtil();
	            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Prescription Bill: Exception");
	            
				exception.printStackTrace();

				status = "error";
			}

		} else {

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			SMSText = "The following medicines have been prescribed for you on this visit " + dateAndTime + " - ";

			/*
			 * Retrieving prescription details list to send as details into message text
			 */
			List<String> prescList = patientDAOInf.retrievePrescriptionListForSMSByVisitID(visitID);

			if (prescList == null) {
				SMSText += "";
			} else {

				for (String str : prescList) {
					SMSText += str;
				}

			}

			System.out.println("Prescription string ... " + SMSText);

			try {

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("Prescription SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send prescription SMS.");

					status = "error";

					return status;

				}

			} catch (Exception exception) {
				
				StringWriter stringWriter = new StringWriter();

	            exception.printStackTrace(new PrintWriter(stringWriter));

	            // calling exception mail send method to send mail about the exception details
	            // on info@kovidbioanalytics.com
	            EmailUtil emailUtil1 = new EmailUtil();
	            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Prescription SMS: Exception");
	            
				exception.printStackTrace();

				status = "error";
			}

		}

		return status;
	}

	/**
	 * 
	 * @param patientID
	 * @param practiceID
	 * @param mobileNo
	 * @param clinicID
	 * @return
	 */
	public String sendWelcomeSMS(int patientID, int practiceID, String mobileNo, int clinicID) {

		patientDAOInf = new PatientDAOImpl();

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// Retrieving patient registration no based on patientID
		String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID, patientID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String SMSText = "Dear " + patientName + ", welcome to " + practiceName + ". Your ID in our system is " + regNo
				+ ". Please ensure that you remember it for easy appointments and record keeping in future.";

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Welcome SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send welcome SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Welcome SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param patientID
	 * @param mobileNo
	 * @param practiceID
	 * @param doctorName
	 * @return
	 */
	public String sendReferringDoctorSMS(int patientID, String mobileNo, int practiceID, String doctorName) {

		patientDAOInf = new PatientDAOImpl();

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String SMSText = "Dear Dr. " + doctorName + ", thank you for referring " + patientName + " to our clinic. - "
				+ practiceName;

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Thanks to referring doctor SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send Thanks to referring doctor  SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Referring Doctor SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param patientID
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param apptDate
	 * @param aptTime
	 * @param contextPath
	 * @return
	 */
	public String sendAppointmentReminderSMS(int patientID, String mobileNo, int practiceID, int clinicID,
			String apptDate, String aptTime, String contextPath) {

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = doaInf.retrievePracticeNameByID(practiceID, contextPath);

		// Retrieving clinic name based on clinicID
		String clinicName = doaInf.retrieveClinicNameByID(clinicID, contextPath);

		// Retrieving patinet's registrationNo from ClinicRegistration table
		// based on clinicID and PatientID
		String clinicRegNo = doaInf.retrieveClinicRegNoByClinicID(clinicID, patientID, contextPath);

		// Retrieving patient's first name and last name
		String patientName = doaInf.retrievePatientFirstLastNameByID(patientID, contextPath);

		SMSText = "Dear " + patientName + "(" + clinicRegNo + "), you have an appointment scheduled today at  "
				+ practiceName + ", " + clinicName + " clinic at " + aptTime + ".";

		// SMSText = "Dear " + patientName + ", you have an appointment
		// scheduled today at " + practiceName + ", "
		// + clinicName + " clinic at " + aptTime + ".";

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Appointment reminder SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send Appointment reminder SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Appointment Reminder SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param mobileNo
	 * @param startDate
	 * @param endDate
	 * @param employeeID
	 * @param leaveTypeID
	 * @param type
	 * @return
	 */
	public String sendLeaveSMS(String mobileNo, String startDate, String endDate, int employeeID, int leaveTypeID,
			String type) {

		leaveDAOInf = new LeaveDAOImpl();

		String SMSText = "";

		/*
		 * Checking type of appointment, and according to that setting message text to
		 * be sent to patient
		 */
		if (type.equals(ActivityStatus.APPLIED)) {

			// Retrieving employee name by ID
			String empName = leaveDAOInf.retrieveEmployeeNameByID(employeeID);

			// Retrieving leave type name by leaveTypeID
			String leaveType = leaveDAOInf.retrieveLeaveTypeByID(leaveTypeID);

			SMSText = empName + " has applied for " + leaveType + ", from " + startDate + " to " + endDate
					+ ". Please process the application by logging into eDhanvantari.";

		} else if (type.equals(ActivityStatus.APPROVED)) {

			// Retrieving employee name by ID
			String empName = leaveDAOInf.retrieveEmployeeNameByID(employeeID);

			SMSText = "Dear " + empName + ", your leave for the period of " + startDate + " to " + endDate
					+ " has been approved.";

		} else if (type.equals(ActivityStatus.REJECTED)) {

			// Retrieving employee name by ID
			String empName = leaveDAOInf.retrieveEmployeeNameByID(employeeID);

			SMSText = "Dear " + empName + ", your leave for the period of " + startDate + " to " + endDate
					+ " has been rejected.";

		} else if (type.equals(ActivityStatus.CANCELLED)) {

			// Retrieving employee name by ID
			String empName = leaveDAOInf.retrieveEmployeeNameByID(employeeID);

			SMSText = "Dear " + empName + ", your leave for the period of " + startDate + " to " + endDate
					+ " has been cancelled.";

		}

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Leave " + type + " SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send leave " + type + " SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Leave SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param patientIDString
	 * @param practiceID
	 * @param clinicID
	 * @param pracCheck
	 * @return
	 */
	public String sendBroadcastSMSForPatientCredit(String patientIDString, int practiceID, int clinicID,
			String pracCheck) {

		patientDAOInf = new PatientDAOImpl();

		String message = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		try {

			/*
			 * Check whether patientIDString contains All as a part of it, if so, send
			 * message to all patients
			 */
			if (patientIDString.contains("All")) {

				List<String> creditDetailsList = patientDAOInf.retrieveAllPatientForCredit(practiceID, clinicID,
						pracCheck);

				for (String detailString : creditDetailsList) {

					String[] array = detailString.split("=");

					int patientID = Integer.parseInt(array[1]);

					double creditAmount = Double.parseDouble(array[0]);

					/*
					 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
					 * welcome message
					 */
					String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

					if (mobileNo == null || mobileNo == "") {
						System.out.println("No mobile no found for the patient.");

						status = "success";

					} else {
						if (mobileNo.isEmpty()) {
							System.out.println("No mobile no found for the patient.");

							status = "success";

						} else {

							// Retrieving patient first and last name by
							// patientID
							String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

							if (pracCheck.equals("Practice")) {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmount
										+ " is due at " + practiceName + ". Please pay at your earliest.";

							} else {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmount
										+ " is due at " + practiceName + "-" + clinicName
										+ ". Please pay at your earliest.";

							}

							String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
									+ URLEncoder.encode(password, "UTF-8") + "&source="
									+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
									+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
									+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

							URL url2 = new URL(SMSURL);

							HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

							if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

								System.out.println("SMS sent seuccessfully.");

								status = "success";

								return status;

							} else {

								System.out.println("Failed to send SMS.");

								status = "error";

								return status;

							}

						}
					}

				}

			} else {

				String patIDArray[] = patientIDString.split(",");

				for (int i = 0; i < patIDArray.length; i++) {

					int patID = Integer.parseInt(patIDArray[i]);

					/*
					 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
					 * welcome message
					 */
					String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patID);

					if (mobileNo == null || mobileNo == "") {
						System.out.println("No mobile no found for the patient.");

						status = "success";

					} else {
						if (mobileNo.isEmpty()) {
							System.out.println("No mobile no found for the patient.");

							status = "success";

						} else {

							// Retrieving patient first and last name by
							// patientID
							String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patID);

							/*
							 * Retrieving patient's available credit amount
							 */
							double creditAmt = patientDAOInf.retrievePatientCredit(patID, practiceID, clinicID,
									pracCheck);

							if (pracCheck.equals("Practice")) {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmt + " is due at "
										+ practiceName + ". Please pay at your earliest.";

							} else {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmt + " is due at "
										+ practiceName + "-" + clinicName + ". Please pay at your earliest.";

							}

							String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
									+ URLEncoder.encode(password, "UTF-8") + "&source="
									+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
									+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
									+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

							URL url2 = new URL(SMSURL);

							HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

							if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

								System.out.println("SMS sent seuccessfully.");

								status = "success";

								return status;

							} else {

								System.out.println("Failed to send SMS.");

								status = "error";

								return status;

							}

						}
					}

				}

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Broadcast SMS For Patient Credit: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param mobileNo
	 * @param patientID
	 * @param practiceID
	 * @return
	 */
	public String sendFeedbackSMS(String mobileNo, int patientID, int practiceID) {

		leaveDAOInf = new LeaveDAOImpl();

		patientDAOInf = new PatientDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving feedback form URL
		String reviewFormURL = configurationUtil.getReviewFormURL();

		SMSText = "Dear " + patientName + ", thank you for visting " + practiceName
				+ ". Please visit the following link to provide your feedback:" + reviewFormURL;

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Feedback SMS sent successfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send feedback SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Feedback SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param patientIDString
	 * @param practiceID
	 * @param clinicID
	 * @param message
	 * @param pracCheck
	 * @return
	 */
	public String sendCustomizedBroadcastSMSToPatient(String patientIDString, int practiceID, int clinicID,
			String message, String pracCheck) {

		patientDAOInf = new PatientDAOImpl();

		try {

			/*
			 * Check whether patientIDString contains All as a part of it, if so, send
			 * message to all patients
			 */
			if (patientIDString.contains("All")) {

				/*
				 * Retrieving patienID list based on pracCheck
				 */
				List<Integer> patientIDList = patientDAOInf.retrievePatientIDListForCustomizedSMS(pracCheck, practiceID,
						clinicID);

				System.out.println("Size..." + patientIDList.size());

				if (patientIDList.size() > 0) {

					for (Integer patientID : patientIDList) {

						/*
						 * Retrieving patient's mobile no baased on patientID
						 */
						String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

						if (mobileNo == null || mobileNo == "") {
							System.out.println("No mobile no found for the patient.");

							status = "noMobile";

						} else {

							if (mobileNo.isEmpty()) {
								System.out.println("No mobile no found for the patient.");

								status = "noMobile";

							} else {

								String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
										+ URLEncoder.encode(password, "UTF-8") + "&source="
										+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
										+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
										+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

								URL url2 = new URL(SMSURL);

								HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

								if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

									System.out.println("Customized SMS sent seuccessfully.");

									status = "success";

								} else {

									System.out.println("Failed to send Customized SMS.");

									status = "error";

								}

							}

						}

					}

				} else {
					System.out.println("No patient found...");

					status = "input";

					return status;
				}

			} else {

				String patIDArray[] = patientIDString.split(",");

				String mobileNo = "";

				for (int i = 0; i < patIDArray.length; i++) {

					int patID = Integer.parseInt(patIDArray[i].trim());

					/*
					 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
					 * welcome message
					 */
					String patMobileNo = patientDAOInf.retrievePatientMobileNoByID(patID);

					if (patMobileNo == null || patMobileNo == "") {
						System.out.println("No mobile no found for the patient.");
					} else {

						if (patMobileNo.isEmpty()) {
							System.out.println("No mobile no found for the patient.");
						} else {
							mobileNo += patMobileNo + ",";
						}

					}

				}

				if (mobileNo != null && mobileNo.length() > 0 && mobileNo.charAt(mobileNo.length() - 1) == ',') {
					mobileNo = mobileNo.substring(0, mobileNo.length() - 1);
				}

				System.out.println("Mobile no for patients are ... " + mobileNo);

				if (mobileNo == "" || mobileNo == null) {
					System.out.println("No mobile no found for the patient.");

					status = "noMobile";

					return status;

				}

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(message, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("Customized SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send Customized SMS.");

					status = "error";

					return status;

				}

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Customized Broadcast SMS To Patient: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param productList
	 * @return
	 */
	public String sendProductAlertSMS(String mobileNo, int practiceID, int clinicID, List<PatientForm> productList) {

		patientDAOInf = new PatientDAOImpl();

		String SMSText = "The following products from the inventory have stock less than minimum threshold. "
				+ "Please order new stock to avoid issues in prescription:\n";

		String productDetailText = "";

		for (PatientForm patientForm : productList) {
			productDetailText = productDetailText.concat("," + patientForm.getTradeName());
		}

		if (productDetailText.startsWith(",")) {
			productDetailText = productDetailText.substring(1);
		}

		SMSText = SMSText.concat(productDetailText);

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Product alert SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send product alert SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Product Alert SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/**
	 * 
	 * @param visitID
	 * @param mobileNo
	 * @param practiceID
	 * @param clinicID
	 * @param type
	 * @return
	 */
	public String sendGeneralisedPrescriptionORBillSMS(int visitID, String mobileNo, int practiceID, int clinicID,
			String type) {

		patientDAOInf = new PatientDAOImpl();

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic name based on clinicID
		// String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		/*
		 * Checking what type is;i.e.either Bill or Prescription, and according to that
		 * setting message text
		 */

		if (type.equals(ActivityStatus.BILL)) {

			// retrieving bill amount based on visitID from Receipt table
			double billAmt = patientDAOInf.retrieveBillAmountByVisitID(visitID);

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			SMSText = "The bill for your visit with " + practiceName + " at " + dateAndTime + " is " + billAmt + ".";

			try {

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("Bill SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send bill SMS.");

					status = "error";

					return status;

				}

			} catch (Exception exception) {
				
				StringWriter stringWriter = new StringWriter();

	            exception.printStackTrace(new PrintWriter(stringWriter));

	            // calling exception mail send method to send mail about the exception details
	            // on info@kovidbioanalytics.com
	            EmailUtil emailUtil1 = new EmailUtil();
	            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Generalised Prescription SMS: Exception");
	            
				exception.printStackTrace();

				status = "error";
			}

		} else {

			int count = 1;

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			SMSText = "The following medicines have been prescribed for you on this visit " + dateAndTime + " - ";

			/*
			 * Retrieving prescription details list to send as details into message text
			 */
			List<PatientForm> prescList = patientDAOInf.retrievePrescriptionList(0, visitID);

			if (prescList == null) {
				SMSText += "";
			} else {

				for (PatientForm form : prescList) {
					SMSText += count + "." + form.getTradeName() + "(" + form.getDosage() + ") " + form.getNoOfPills()
							+ " " + form.getFrequency() + " " + form.getComment() + "\n";

					count++;
				}

			}

			System.out.println("Prescription string ... " + SMSText);

			try {

				String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
						+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
						+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

				URL url2 = new URL(SMSURL);

				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

				if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

					System.out.println("Prescription SMS sent seuccessfully.");

					status = "success";

					return status;

				} else {

					System.out.println("Failed to send prescription SMS.");

					status = "error";

					return status;

				}

			} catch (Exception exception) {
				
				StringWriter stringWriter = new StringWriter();

	            exception.printStackTrace(new PrintWriter(stringWriter));

	            // calling exception mail send method to send mail about the exception details
	            // on info@kovidbioanalytics.com
	            EmailUtil emailUtil1 = new EmailUtil();
	            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Generalised Prescription Bill SMS: Exception");
				
				exception.printStackTrace();

				status = "error";
			}

		}

		return status;
	}

	/**
	 * 
	 * @param patientID
	 * @param practiceID
	 * @param mobileNo
	 * @param clinicID
	 * @return
	 */
	public String sendAnuyashWelcomeSMS(int patientID, int practiceID, String mobileNo, int clinicID) {

		patientDAOInf = new PatientDAOImpl();

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// Retrieving patient registration no based on patientID
		// String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID,
		// patientID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		// String practiceName =
		// patientDAOInf.retrievePracticeNameByID(practiceID);

		/*
		 * retrieving random blood sugar value
		 */
		String RBS = patientDAOInf.retrieveSugarValueByPatientID(patientID);

		String SMSText = "Dear " + patientName
				+ ", thank you for participating in Free Mega Blood Sugar Check Up Camp.\nYour RBS is " + RBS
				+ " mg/dl.\nTeam Anuyash\n*Avail complimentary HbA1c test on NeDCC www.herbayu.com\nFor any doubt/Query please call 9325725793.";

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source=" + URLEncoder.encode(senderID, "UTF-8")
					+ "&destination=" + URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Welcome SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send welcome SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil1 = new EmailUtil();
            emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Anuyash Welcome SMS: Exception");
            
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

}
