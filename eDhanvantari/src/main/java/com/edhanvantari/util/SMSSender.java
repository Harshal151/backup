package com.edhanvantari.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.edhanvantari.daoImpl.ClinicDAOImpl;
import com.edhanvantari.daoImpl.ConfigurationDAOImpl;
import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoInf.ClinicDAOInf;
import com.edhanvantari.daoInf.ConfigurationDAOInf;
import com.edhanvantari.daoInf.ListnerDOAInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.util.EncDescUtil;

/**
 * 
 * @author kovid bioanalytics
 *
 */
public class SMSSender {

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	String status = "error";

	PatientDAOInf patientDAOInf = null;

	ConfigurationDAOInf configDoaInf = null;

	PatientForm patientForm = null;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	ConfigurationUtil util = new ConfigurationUtil();

	/**
	 * 
	 * @param diagnosis
	 * @param message
	 * @param practiceID
	 * @param check
	 * @param clinicID
	 * @param mobileNo
	 * @param string
	 * @return
	 */
	public String sendSMSToAllPatientBasedOnDiagnosis(String diagnosis, String message, int practiceID, String check,
			int clinicID, String mobileNo) {

		patientDAOInf = new PatientDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		try {

			/*
			 * retrieving SMSURLDetails form Communication table by practiceID
			 */

			HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

			// String username = SMSURLDetailsList.get("smsUsername");
			// String password = SMSURLDetailsList.get("smsPassword");
			String URL = SMSURLDetailsList.get("smsURL");
			String senderID = SMSURLDetailsList.get("smsSenderID");
			String apiKey = SMSURLDetailsList.get("smsApiKey");
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

						String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
								+ URLEncoder.encode(senderID, "UTF-8") + "&numbers="
								+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
								+ URLEncoder.encode(message, "UTF-8");

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

						String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
								+ URLEncoder.encode(senderID, "UTF-8") + "&numbers="
								+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
								+ URLEncoder.encode(message, "UTF-8");

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

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(message, "UTF-8");

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
	 * @param doctorName
	 * @return
	 */
	public String sendAppointmentSMS(int patientID, String mobileNo, int practiceID, int clinicID, String apptDate,
			String aptTime, String appointmentType, String doctorName) {

		patientDAOInf = new PatientDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		String SMSText = "";

		// Retrieving patient's first name and last name
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		// Retrieving patinet's registrationNo from ClinicRegistration table
		// based on clinicID and PatientID
		String clinicRegNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID, patientID);

		// Retrieving clinic phone
		String phoneNo = patientDAOInf.retrieveClinicPhoneNo(clinicID);

		/*
		 * Checking type of appointment, and according to that setting message text to
		 * be sent to patient
		 */
		if (appointmentType.equals(ActivityStatus.SCHEDULED)) {

			/*
			 * SMSText = "Appointment for " + clinicRegNo + " for " + practiceName +
			 * " has been scheduled at " + clinicName + " at " + apptDate + " " + aptTime +
			 * ". Please contact our clinic in case of any.";
			 */

			SMSText = "Dear " + patientName + ", your appointment at " + clinicName + " is fixed on " + apptDate + " "
					+ aptTime + " with " + doctorName
					+ " through eDhanvantari (a Kovid BioAnalytics platform). Contact us for further enquiry.";

			// SMSText = "Your appointment for " + practiceName + " has been
			// scheduled at " + clinicName + " at "
			// + apptDate + " " + aptTime + ". Please contact our clinic in case
			// of any changes.";

		} else if (appointmentType.equals(ActivityStatus.UPDATED)) {

			/*
			 * SMSText = "Appointment for " + clinicRegNo + " for " + practiceName +
			 * " has been scheduled at " + clinicName + " has been updated to " + apptDate +
			 * " " + aptTime + ". Please contact our clinic in case of any changes.";
			 */

			SMSText = "Dear " + patientName + ", your appointment at " + clinicName + " is rescheduled on " + apptDate
					+ " " + aptTime + " with " + doctorName
					+ " through eDhanvantari (a Kovid BioAnalytics platform). Contact us for further enquiry.";

			// SMSText = "Your appointment for " + practiceName + " has been
			// scheduled at " + clinicName
			// + " has been updated to " + apptDate + " " + aptTime
			// + ". Please contact our clinic in case of any changes.";

		} else if (appointmentType.equals(ActivityStatus.CANCELLED)) {

			/*
			 * SMSText = "Appointment for " + clinicRegNo + " for " + practiceName + " at "
			 * + clinicName + " scheduled at " + apptDate + " " + aptTime +
			 * " has been cancelled. Please contact our clinic in case of any changes.";
			 */

			SMSText = "Dear " + patientName + ", your appointment at " + clinicName + " on " + apptDate + " " + aptTime
					+ " has been cancelled through eDhanvantari (a Kovid BioAnalytics platform). Contact us for further enquiry.";

			// SMSText = "Your appointment for " + practiceName + " at " +
			// clinicName + " scheduled at " + apptDate + " "
			// + aptTime + " has been cancelled. Please contact our clinic in
			// case of any changes.";

		} else if (appointmentType.equals(ActivityStatus.REMINDER)) {

			// Retrieving patient's first name and last name
			// String patientName =
			// patientDAOInf.retrievePatientFirstLastNameByID(patientID);

			/*
			 * SMSText = "Dear " + patientName + "(" + clinicRegNo +
			 * "), you have an appointment scheduled today at  " + practiceName + ", " +
			 * clinicName + " clinic at " + aptTime + ".";
			 */

			SMSText = "Dear " + patientName + ", this is a reminder for your appointment at " + clinicName + " on "
					+ apptDate + " " + aptTime
					+ " by eDhanvantari (a Kovid BioAnalytics platform). Contact us for further enquiry.";

			// SMSText = "Dear " + patientName + ", you have an appointment
			// scheduled today at " + practiceName + ", "
			// + clinicName + " clinic at " + aptTime + ".";

		}

		try {

			System.out.println("APT Text>>" + SMSText);

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

			URL url2 = new URL(SMSURL);
			System.out.println("---" + SMSURL);

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
	public String sendPrescriptionORBillSMS(int visitID, String mobileNo, int practiceID, int clinicID, String type,
			String fileName) {

		patientDAOInf = new PatientDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3reportFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = util.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

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

			// SMSText = "The bill for your visit with " + practiceName + " at " +
			// dateAndTime + " is " + billAmt + ".";
			SMSText = "Please check and download the bill for your visit with " + practiceName + " at " + dateAndTime
					+ " from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName);

			try {

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

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
				exception.printStackTrace();

				status = "error";
			}

		} else {

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			// SMSText = "The following medicines have been prescribed for you on this visit
			// " + dateAndTime + " - ";

			SMSText = "Please check and download the prescription for you on this visit " + dateAndTime
					+ " from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName);

			/*
			 * Retrieving prescription details list to send as details into message text
			 */
			/*
			 * List<String> prescList =
			 * patientDAOInf.retrievePrescriptionListForSMSByVisitID(visitID);
			 * 
			 * if (prescList == null) { SMSText += ""; } else {
			 * 
			 * for (String str : prescList) { SMSText += str; }
			 * 
			 * }
			 */
			System.out.println("Prescription string ... " + SMSText);

			try {

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

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
		configDoaInf = new ConfigurationDAOImpl();

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving username and password by patientid

		/*
		 * String uname = patientDAOInf.retrieveUsernameByID(patientID); String passw =
		 * patientDAOInf.retrievePasswordByID(patientID); String decryptedPassword =
		 * null; decryptedPassword = EncDescUtil.DecryptText(passw);
		 * System.out.println("Decrypted password is::::::" + decryptedPassword); String
		 * loginURL = "http://localhost:8081/eDhanvantari/PatientLogin";
		 */
		// (while deploying on demo -- put URL of demo server with /Action name)

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		// Retrieving patient registration no based on patientID
		// String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID,
		// patientID);

		// String clinicName = patientDAOInf.retrieveClinicNameByClinicID(clinicID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic phone
		// String phoneNo = patientDAOInf.retrieveClinicPhoneNo(clinicID);

		/*
		 * String SMSText = "Dear " + patientName + ", welcome to " + practiceName +
		 * ". Your ID in our system is " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping."
		 * + System.lineSeparator() + "Your Username = " + uname + " "+
		 * System.lineSeparator() +"Password =  " + decryptedPassword + " "+
		 * System.lineSeparator() + "Login URL is = " + loginURL +
		 * "\n\nThanks,\nClinic Administrator";
		 */

		if (practiceName.length() > 30) {
			practiceName = practiceName.substring(0, 30);
		}

		String SMSText = "Dear " + patientName + ", welcome to " + practiceName
				+ " on eDhanvantari (a Kovid BioAnalytics platfom). You can login to our patient portal at edhanvantari.com for accessing your records.";

		/*
		 * String SMSText = "Dear " + patientName + ", welcome to "
		 * +clinicName+". Your ID in our system is " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping."
		 * + System.lineSeparator() + "Thanks, "+clinicName;
		 */

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			System.out.println("SMSTEXT: " + SMSText + "----" + SMSURL);
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
			exception.printStackTrace();

			status = "error";
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
	public String sendPatientPortalCredentialsSMS(int patientID, int practiceID, String mobileNo, int clinicID) {

		patientDAOInf = new PatientDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving username and password by patientid

		String uname = patientDAOInf.retrieveUsernameByID(patientID);
		String passw = patientDAOInf.retrievePasswordByID(patientID);
		String decryptedPassword = null;
		decryptedPassword = EncDescUtil.DecryptText(passw);
		// System.out.println("Decrypted password is::::::" + decryptedPassword);
		String loginURL = "https://demo.edhanvantari.com/eDhanvantari/PatientLogin";
		// (while deploying on demo -- put URL of demo server with /Action name)

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		// Retrieving patient registration no based on patientID
		/*
		 * String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID,
		 * patientID);
		 * 
		 * String clinicName = patientDAOInf.retrieveClinicNameByClinicID(clinicID);
		 */

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving clinic phone
		// String phoneNo = patientDAOInf.retrieveClinicPhoneNo(clinicID);

		/*
		 * String SMSText = "Dear " + patientName + ", welcome to " + practiceName +
		 * ". Your ID in our system is " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping."
		 * + System.lineSeparator() + "Your Username = " + uname + " "+
		 * System.lineSeparator() +"Password =  " + decryptedPassword + " "+
		 * System.lineSeparator() + "Login URL is = " + loginURL +
		 * "\n\nThanks,\nClinic Administrator";
		 */

		if (practiceName.length() > 30) {
			practiceName = practiceName.substring(0, 30);
		}

		String SMSText = "Dear " + patientName
				+ ", you can now login to the edhanvantari.com (a Kovid BioAnalytics platform) using:\nUsername - "
				+ uname + "\nPassword - " + decryptedPassword;

		/*
		 * String SMSText = "Dear " + patientName + ", welcome to "
		 * +clinicName+". Your ID in our system is " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping."
		 * + System.lineSeparator() + "Thanks, "+clinicName;
		 */

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			System.out.println("SMSTEXT: " + SMSText + "----" + SMSURL);
			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Patient portal credentials SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send Patient portal credentials SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
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

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String SMSText = "Dear Dr. " + doctorName + ", thank you for referring " + patientName + " to our clinic. - "
				+ practiceName;

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

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
			String apptDate, String aptTime, String contextPath, String doctorName) {

		ListnerDOAInf doaInf = new ListnerDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

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

		SMSText = "Dear " + patientName + ", your appointment at " + clinicName + " is fixed on " + apptDate + " "
				+ aptTime + " with " + doctorName
				+ " through eDhanvantari (a Kovid BioAnalytics platform). Contact us for further enquiry.";

		/*
		 * SMSText = "Dear " + patientName + "(" + clinicRegNo +
		 * "), you have an appointment scheduled today at  " + practiceName + ", " +
		 * clinicName + " clinic at " + aptTime + ".";
		 */

		// SMSText = "Dear " + patientName + ", you have an appointment
		// scheduled today at " + practiceName + ", "
		// + clinicName + " clinic at " + aptTime + ".";

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

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
	 * @param string
	 * @return
	 */
	public String sendBroadcastSMSForPatientCredit(String patientIDString, int practiceID, int clinicID,
			String pracCheck) {

		patientDAOInf = new PatientDAOImpl();

		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		String message = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);
		System.out.println("practice name:" + practiceName);

		// Retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);
		System.out.println("practice name:" + clinicName);
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
							System.out.println("practice name:" + patientName);
							if (pracCheck.equals("Practice")) {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmount
										+ " is due at " + practiceName + ". Please pay at your earliest.";

							} else {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmount
										+ " is due at " + practiceName + "-" + clinicName
										+ ". Please pay at your earliest.";

							}

							String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
									+ URLEncoder.encode(senderID, "UTF-8") + "&numbers="
									+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
									+ URLEncoder.encode(message, "UTF-8");

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
					System.out.println("practice name:" + mobileNo);
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
							System.out.println("practice name:" + patientName);
							/*
							 * Retrieving patient's available credit amount
							 */
							double creditAmt = patientDAOInf.retrievePatientCredit(patID, practiceID, clinicID,
									pracCheck);
							System.out.println("practice name:" + creditAmt);
							if (pracCheck.equals("Practice")) {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmt + " is due at "
										+ practiceName + ". Please pay at your earliest.";

							} else {

								message = "Dear " + patientName + ", a total bill of Rs. " + creditAmt + " is due at "
										+ practiceName + "-" + clinicName + ". Please pay at your earliest.";

							}

							String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
									+ URLEncoder.encode(senderID, "UTF-8") + "&numbers="
									+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
									+ URLEncoder.encode(message, "UTF-8");

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

		patientDAOInf = new PatientDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		System.out.println("URL::" + URL + "senderID::" + senderID + "apiKey::" + apiKey);

		String SMSText = "";

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving feedback form URL
		String reviewFormURL = configurationUtil.getReviewFormURL();

		if (practiceName.length() > 30) {
			practiceName = practiceName.substring(0, 30);
		}

		SMSText = "Dear " + patientName + ", you have just finished your consultation at " + practiceName
				+ " through eDhanvantari (a Kovid BioAnalytics platform). Please take timely medicines as advised. Please confirm your next appointment.";

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

			System.out.println("sms url::" + SMSURL);
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
	 * @param string
	 * @return
	 */
	public String sendCustomizedBroadcastSMSToPatient(String patientIDString, int practiceID, int clinicID,
			String message, String pracCheck) {

		patientDAOInf = new PatientDAOImpl();

		configDoaInf = new ConfigurationDAOImpl();

		try {

			/*
			 * retrieving SMSURLDetails form Communication table by practiceID
			 */

			HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

			// String username = SMSURLDetailsList.get("smsUsername");
			// String password = SMSURLDetailsList.get("smsPassword");
			String URL = SMSURLDetailsList.get("smsURL");
			String senderID = SMSURLDetailsList.get("smsSenderID");
			String apiKey = SMSURLDetailsList.get("smsApiKey");

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

								String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
										+ URLEncoder.encode(senderID, "UTF-8") + "&numbers="
										+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
										+ URLEncoder.encode(message, "UTF-8");

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

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(message, "UTF-8");

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
		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

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

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

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
			String type, String fileName) {

		patientDAOInf = new PatientDAOImpl();

		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * String accessKey = xmlUtil.getAccessKey();
		 * 
		 * String secreteKey = xmlUtil.getSecreteKey();
		 * 
		 * AWSS3Connect awss3Connect = new AWSS3Connect();
		 * 
		 * // getting input file location from S3 bucket String s3reportFilePath =
		 * xmlUtil.getS3RDMLReportFilePath();
		 * 
		 * // getting s3 bucket region String bucketRegion =
		 * xmlUtil.getS3BucketRegion();
		 * 
		 * // Set the presigned URL to expire after one hour. java.util.Date expiration
		 * = new java.util.Date(); long expTimeMillis = expiration.getTime();
		 * expTimeMillis += 1000 * 60 * 60; expiration.setTime(expTimeMillis);
		 * 
		 * AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);
		 * 
		 * AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new
		 * AWSStaticCredentialsProvider(credentials)) .withRegion(bucketRegion).build();
		 */

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		String SMSText = "";

		int patientID = patientDAOInf.retrievePatientIDByVisitID(visitID);

		// Retrieving patient's first name and last name
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// Retrieving patinet's registrationNo from ClinicRegistration table
		// based on clinicID and PatientID
		String clinicRegNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID, patientID);

		// String visitDate = patientDAOInf.retrieveVisitDateAndTime(visitID);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String bucketName = patientDAOInf.retrievePracticeBucketName(practiceID);

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

			String billFileURL = "https://demo.edhanvantari.com/eDhanvantari/ViewFiles?pdfOutPath=" + fileName
					+ "&bucketName=" + bucketName;

			SMSText = "Dear " + patientName + ", your bill for visit date " + dateAndTime
					+ " is available on eDhanvantari (a Kovid BioAnalytics platfom).";

			/*
			 * SMSText = "Please check and download the bill for your visit with " +
			 * practiceName + " at " + dateAndTime + " from the below URL.\n" +
			 * s3.getUrl(bucketName + "/" + s3reportFilePath, fileName);
			 * System.out.println("BIll string ... " + SMSText + "  url::" +
			 * s3.getUrl(bucketName + "/" + s3reportFilePath, fileName));
			 */

			try {

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

				System.out.println("SMSURL.." + SMSURL);

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
				exception.printStackTrace();

				status = "error";
			}

		} else {

			int count = 1;

			// retrieving visit date and time based on visitID from Visit table
			String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

			// SMSText = "The following medicines have been prescribed for you on this visit
			// " + dateAndTime + " - ";

			/*
			 * SMSText = "Please check and download the prescription for you on this visit "
			 * + dateAndTime + " from the below URL.\n" + s3.getUrl(bucketName + "/" +
			 * s3reportFilePath, fileName);
			 */

			String prescFileURL = "https://demo.edhanvantari.com/eDhanvantari/ViewFiles?pdfOutPath=" + fileName
					+ "&bucketName=" + bucketName;

			SMSText = "Dear " + patientName + ", your prescription for visit date " + dateAndTime
					+ " is available on eDhanvantari (a Kovid BioAnalytics platform).";

			/*
			 * Retrieving prescription details list to send as details into message text
			 */
			/*
			 * List<PatientForm> prescList = patientDAOInf.retrievePrescriptionList(0,
			 * visitID, clinicID);
			 * 
			 * if (prescList == null) { SMSText += ""; } else {
			 * 
			 * for (PatientForm form : prescList) { SMSText += count + "." +
			 * form.getTradeName() + "(" + form.getDosage() + ") " + form.getNoOfPills() +
			 * " " + form.getFrequency() + " " + form.getComment() + "\n";
			 * 
			 * count++; }
			 * 
			 * }
			 */

			try {

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

				System.out.println("SMSURL.." + SMSURL);

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
				exception.printStackTrace();

				status = "error";
			}

		}

		return status;
	}

	public String sendGeneralisedLabPrescriptionORBillSMS(int visitID, String mobileNo, int practiceID, int clinicID,
			String type) {

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

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

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

				/*
				 * String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") +
				 * "&password=" + URLEncoder.encode(password, "UTF-8") + "&source=" +
				 * URLEncoder.encode(senderID, "UTF-8") + "&destination=" +
				 * URLEncoder.encode(mobileNo, "UTF-8") + "&message=" +
				 * URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";
				 */

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

				String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
						+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

				/*
				 * String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") +
				 * "&password=" + URLEncoder.encode(password, "UTF-8") + "&source=" +
				 * URLEncoder.encode(senderID, "UTF-8") + "&destination=" +
				 * URLEncoder.encode(mobileNo, "UTF-8") + "&message=" +
				 * URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";
				 */

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
				exception.printStackTrace();

				status = "error";
			}

		}

		return status;

	}

	public String sendOTP(String mobileNo, int practiceID, int clinicID, String OTP) {

		patientDAOInf = new PatientDAOImpl();
		configDoaInf = new ConfigurationDAOImpl();

		/*
		 * retrieving SMSURLDetails form Communication table by practiceID
		 */

		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		// String username = SMSURLDetailsList.get("smsUsername");
		// String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");
		String apiKey = SMSURLDetailsList.get("smsApiKey");

		String SMSText = "OTP for verification of mobile number is " + OTP + ".\r\n" + "-- eDhanvantari";

		try {

			String SMSURL = URL + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8") + "&sender="
					+ URLEncoder.encode(senderID, "UTF-8") + "&numbers=" + URLEncoder.encode(mobileNo, "UTF-8")
					+ "&message=" + URLEncoder.encode(SMSText, "UTF-8");

			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("OTP SMS sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send OTP SMS.");

				status = "error";

				return status;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

}
