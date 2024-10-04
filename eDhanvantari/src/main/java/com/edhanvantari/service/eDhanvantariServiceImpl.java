package com.edhanvantari.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.edhanvantari.daoImpl.ApptCalendarDAOImpl;
import com.edhanvantari.daoImpl.ClinicDAOImpl;
import com.edhanvantari.daoImpl.ConfigurationDAOImpl;
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoImpl.PrescriptionManagementDAOImpl;
import com.edhanvantari.daoImpl.RegistrationDAOImpl;
import com.edhanvantari.daoImpl.ReportDAOImpl;
import com.edhanvantari.daoInf.ApptCalendarDAOinf;
import com.edhanvantari.daoInf.ClinicDAOInf;
import com.edhanvantari.daoInf.ConfigurationDAOInf;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.PrescriptionManagementDAOInf;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.daoInf.ReportDAOInf;
import com.edhanvantari.form.ClinicForm;
import com.edhanvantari.form.ConfigurationForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.form.RegistrationForm;
import com.edhanvantari.util.AWSS3Connect;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.ConvertToPDFUtil;
import com.edhanvantari.util.EmailUtil;
import com.edhanvantari.util.ExcelUtil;
import com.edhanvantari.util.JSONValidator;
import com.edhanvantari.util.SMSSender;

public class eDhanvantariServiceImpl implements eDhanvantariServiceInf {

	String statusMessage = "error";
	RegistrationDAOinf registrationDAOinf = null;
	PatientDAOInf patientDAOInf = null;
	ClinicDAOInf clinicDAOInf = null;

	ApptCalendarDAOinf calendarDAOinf = null;

	ConfigurationUtil configXMLUtil = new ConfigurationUtil();

	ConfigurationUtil util = new ConfigurationUtil();

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	PrescriptionManagementDAOInf prescriptionManagementDAOInf = null;

	ConfigurationDAOInf configurationDAOInf = null;

	ReportDAOInf reportDAOInf = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#editUserDetail(com.
	 * edhanvantari.form.RegistrationForm)
	 */
	public String editUserDetail(RegistrationForm registrationForm, String realPath, HttpServletRequest request) {

		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		String fileNameToBeStored = "";

		try {

			/*
			 * Verify whether changed username already exist with other userID, if yes, then
			 * give error else proceed further
			 */
			boolean check = false;

			check = registrationDAOinf.verifyUsernameWithUserID(registrationForm.getUsername(),
					registrationForm.getUserID(), registrationForm.getPracticeID());

			if (check) {

				/*
				 * Checking whether PIN has been changed by user, if so, then insert the PIN
				 * changed log into Audit table
				 */
				check = registrationDAOinf.verifyPINChange(registrationForm.getUserID(), registrationForm.getPIN());
				if (!check) {

					/*
					 * Inserting PIN change activity into Audit table
					 */
					daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", registrationForm.getUserID());

				}

				/*
				 * Check whether password from table for userID is same as that entered by user
				 * for the same userID, then proceed for updation else check for last five
				 * password existence from PasswordHistory table
				 */
				check = registrationDAOinf.verifyPassword(registrationForm.getUserID(), registrationForm.getPassword());

				if (check) {

					// Setting signatureDBname as NULL
					// registrationForm.setSignatureDBName(null);

					/*
					 * Storing user uploaded profile pic file into image/profilePic directory of
					 * Project, only if the profilePic variable is not null
					 */
					if (registrationForm.getSignature() != null) {

						String[] array = registrationForm.getSignatureFileName().split("\\.");
						String fileExtension = array[1];
						// Setting content type according to file extension
						if (fileExtension.equalsIgnoreCase("jpg")) {
							registrationForm.setSignatureContentType("image/jpg");
						} else if (fileExtension.equalsIgnoreCase("jpeg")) {
							registrationForm.setSignatureContentType("image/jpeg");
						} else if (fileExtension.equalsIgnoreCase("png")) {
							registrationForm.setSignatureContentType("image/png");
						} else if (fileExtension.equalsIgnoreCase("bmp")) {
							registrationForm.setSignatureContentType("image/bmp");
						}

						fileNameToBeStored = registrationForm.getUsername() + "_Signature." + fileExtension;
						System.out.println("Original File name is ::::" + registrationForm.getSignatureFileName());
						System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
						/*
						 * Setting file name to be inserted into AppUser table into profilePicDBName
						 * variable of UserForm
						 */

						/*
						 * Setting file name with S3 bucket path to be inserted into AppUser table into
						 * SignatureDBName
						 */

						registrationForm.setSignatureDBName(fileNameToBeStored);
						System.out.println("signature file db name::" + registrationForm.getSignatureDBName());

						// Storing file to S3 RDML INPUT FILE location
						statusMessage = awss3Connect.pushFile(registrationForm.getSignature(), fileNameToBeStored,
								bucketName, bucketRegion, s3rdmlFilePath);
						System.out.println("statusmsg::" + statusMessage);
					}

					/*
					 * Storing user uploaded profile pic file into image/profilePic directory of
					 * Project, only if the profilePic variable is not null
					 */
					if (registrationForm.getProfilePic() != null) {

						String[] array = registrationForm.getProfilePicFileName().split("\\.");

						String fileExtension = array[1];

						// Setting content type according to file extension
						if (fileExtension.equalsIgnoreCase("jpg")) {

							registrationForm.setProfilePicContentType("image/jpg");

						} else if (fileExtension.equalsIgnoreCase("jpeg")) {

							registrationForm.setProfilePicContentType("image/jpeg");

						} else if (fileExtension.equalsIgnoreCase("png")) {

							registrationForm.setProfilePicContentType("image/png");

						} else if (fileExtension.equalsIgnoreCase("bmp")) {

							registrationForm.setProfilePicContentType("image/bmp");

						}

						fileNameToBeStored = registrationForm.getUsername() + "." + fileExtension;

						System.out.println("Original File name is ::::" + registrationForm.getProfilePicFileName());

						System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

						/*
						 * Setting file name with S3 bucket path to be inserted into AppUser table into
						 * SignatureDBName
						 */

						registrationForm.setProfilePicDBName(fileNameToBeStored);
						System.out.println("signature file db name::" + registrationForm.getProfilePicDBName());

						// Storing file to S3 RDML INPUT FILE location
						statusMessage = awss3Connect.pushFile(registrationForm.getProfilePic(), fileNameToBeStored,
								bucketName, bucketRegion, s3rdmlFilePath);
						System.out.println("statusmsg::" + statusMessage);

					}

					/*
					 * Updating user details from User table
					 */
					statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

					if (statusMessage.equalsIgnoreCase("success")) {

						return statusMessage;

					} else {

						System.out.println("Failed to update user details into User table");

						statusMessage = "error";
						return statusMessage;

					}

					/*
					 * Password match check; checking whether entered password matches last five
					 * passwords from Password History, then give error message else proceed further
					 */
				} else {

					check = registrationDAOinf.verifyPasswordHistory(registrationForm.getUserID(),
							registrationForm.getPassword());

					if (check) {

						System.out.println("Entered password lies within last 5 passwords.");

						statusMessage = "input";

						return statusMessage;

					} else {

						/*
						 * Storing user uploaded profile pic file into image/profilePic directory of
						 * Project, only if the profilePic variable is not null
						 */
						if (registrationForm.getSignature() != null) {

							String[] array = registrationForm.getSignatureFileName().split("\\.");
							String fileExtension = array[1];
							// Setting content type according to file extension
							if (fileExtension.equalsIgnoreCase("jpg")) {
								registrationForm.setSignatureContentType("image/jpg");
							} else if (fileExtension.equalsIgnoreCase("jpeg")) {
								registrationForm.setSignatureContentType("image/jpeg");
							} else if (fileExtension.equalsIgnoreCase("png")) {
								registrationForm.setSignatureContentType("image/png");
							} else if (fileExtension.equalsIgnoreCase("bmp")) {
								registrationForm.setSignatureContentType("image/bmp");
							}

							fileNameToBeStored = registrationForm.getUsername() + "_Signature." + fileExtension;
							System.out.println("Original File name is ::::" + registrationForm.getSignatureFileName());
							System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
							/*
							 * Setting file name to be inserted into AppUser table into profilePicDBName
							 * variable of UserForm
							 */

							/*
							 * Setting file name with S3 bucket path to be inserted into AppUser table into
							 * SignatureDBName
							 */

							registrationForm.setSignatureDBName(fileNameToBeStored);
							System.out.println("signature file db name::" + registrationForm.getSignatureDBName());

							// Storing file to S3 RDML INPUT FILE location
							statusMessage = awss3Connect.pushFile(registrationForm.getSignature(), fileNameToBeStored,
									bucketName, bucketRegion, s3rdmlFilePath);
							System.out.println("statusmsg::" + statusMessage);
						}

						/*
						 * Storing user uploaded profile pic file into image/profilePic directory of
						 * Project, only if the profilePic variable is not null
						 */
						if (registrationForm.getProfilePic() != null) {

							String[] array = registrationForm.getProfilePicFileName().split("\\.");

							String fileExtension = array[1];

							// Setting content type according to file extension
							if (fileExtension.equalsIgnoreCase("jpg")) {

								registrationForm.setProfilePicContentType("image/jpg");

							} else if (fileExtension.equalsIgnoreCase("jpeg")) {

								registrationForm.setProfilePicContentType("image/jpeg");

							} else if (fileExtension.equalsIgnoreCase("png")) {

								registrationForm.setProfilePicContentType("image/png");

							} else if (fileExtension.equalsIgnoreCase("bmp")) {

								registrationForm.setProfilePicContentType("image/bmp");

							}

							fileNameToBeStored = registrationForm.getUsername() + "." + fileExtension;

							System.out.println("Original File name is ::::" + registrationForm.getProfilePicFileName());

							System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
							/*
							 * Setting file name to be inserted into AppUser table into profilePicDBName
							 * variable of UserForm
							 */

							/*
							 * Setting file name with S3 bucket path to be inserted into AppUser table into
							 * SignatureDBName
							 */

							registrationForm.setProfilePicDBName(fileNameToBeStored);
							System.out.println("signature file db name::" + registrationForm.getProfilePicDBName());

							// Storing file to S3 RDML INPUT FILE location
							statusMessage = awss3Connect.pushFile(registrationForm.getProfilePic(), fileNameToBeStored,
									bucketName, bucketRegion, s3rdmlFilePath);
							System.out.println("statusmsg::" + statusMessage);

						}

						/*
						 * Updating user details from User table
						 */
						statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * Inserting password into PasswordHistroy table
							 */
							registrationDAOinf.insertPasswordHistory(registrationForm.getUserID(),
									registrationForm.getPassword());

							// Inserting values into Audit table for password
							daoInf.insertAudit(request.getRemoteAddr(), "Password changed",
									registrationForm.getUserID());

							return statusMessage;

						} else {

							System.out.println("Failed to update user details into User table");

							statusMessage = "error";
							return statusMessage;

						}

					}

				}

			} else {
				System.out.println("Username already exist for another user.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#registerUser(com.
	 * edhanvantari .form.RegistrationForm)
	 */
	public String registerUser(RegistrationForm registrationForm, String realPath, HttpServletRequest request) {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		int status = 0;

		try {

			// Setting signatureDBname as NULL
			registrationForm.setSignatureDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (registrationForm.getSignature() != null) {

				String[] array = registrationForm.getSignatureFileName().split("\\.");
				String fileExtension = array[1];
				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {
					registrationForm.setSignatureContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {
					registrationForm.setSignatureContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {
					registrationForm.setSignatureContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {
					registrationForm.setSignatureContentType("image/bmp");
				}

				String fileNameToBeStored = registrationForm.getUsername() + "_Signature." + fileExtension;
				System.out.println("Original File name is ::::" + registrationForm.getSignatureFileName());
				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name with S3 bucket path to be inserted into AppUser table into
				 * SignatureDBName
				 */

				registrationForm.setSignatureDBName(fileNameToBeStored);
				System.out.println("signature file db name::" + registrationForm.getSignatureDBName());

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(registrationForm.getSignature(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
				System.out.println("statusmsg::" + statusMessage);
			}

			registrationForm.setProfilePicDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (registrationForm.getProfilePic() != null) {

				String[] array = registrationForm.getProfilePicFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {

					registrationForm.setProfilePicContentType("image/jpg");

				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					registrationForm.setProfilePicContentType("image/jpeg");

				} else if (fileExtension.equalsIgnoreCase("png")) {

					registrationForm.setProfilePicContentType("image/png");

				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					registrationForm.setProfilePicContentType("image/bmp");

				}

				String fileNameToBeStored = registrationForm.getUsername() + "." + fileExtension;

				System.out.println("Original File name is ::::" + registrationForm.getProfilePicFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name with S3 bucket path to be inserted into AppUser table into
				 * SignatureDBName
				 */

				registrationForm.setProfilePicDBName(fileNameToBeStored);
				System.out.println("signature file db name::" + registrationForm.getProfilePicDBName());

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(registrationForm.getProfilePic(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
				System.out.println("statusmsg::" + statusMessage);

			}

			/*
			 * Verifying whether user with same username already exist into User table or
			 * not, if not then only proceed for insertion else give error msg saying User
			 * with sme username already exists.
			 */
			status = registrationDAOinf.verifyUsername(registrationForm.getUsername(),
					registrationForm.getPracticeID());

			if (status != 1) {

				if (registrationForm.getClinicID() != -1) {
					/*
					 * Inserting user detail into User table
					 */
					statusMessage = registrationDAOinf.insertUser(registrationForm);
					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * Retrieving userID from username from User table
						 */
						int userID = registrationDAOinf.retrieveUserIDByUsername(registrationForm.getUsername());

						System.out.println("Successfully inserted user detail into User Table.");

						/*
						 * Inserting password into PasswordHistroy table
						 */
						registrationDAOinf.insertPasswordHistory(userID, registrationForm.getPassword());

						// Inserting values into Audit table for password
						daoInf.insertAudit(request.getRemoteAddr(), "Password changed", userID);

						// Inserting values into Audit table for PIN
						daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", userID);

						return statusMessage;

					} else {
						System.out.println("Failed to insert user detail into User table.");
						statusMessage = "error";
						return statusMessage;
					}
				} else {
					System.out.println("No clinic type selected");
					statusMessage = "clnicTypeSelect";
					return statusMessage;
				}
			} else {
				System.out.println("Username already exists into User table...");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addPatient(com.
	 * edhanvantari.form.PatientForm, int)
	 */
	public String addPatient(PatientForm form, int practiceID) {
		patientDAOInf = new PatientDAOImpl();

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		form.setPracticeID(practiceID);

		String username = "";
		String password = "";
		String status = "";
		/*
		 * Checking whether patient firstName middleName lastName and DOB already exists
		 * or not, if not then proceed for insert otherwise ask user to add similar
		 * match
		 */
		if (patientDAOInf.verifyNameDOB(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName lastName and DOB already exists or not, if
			 * not then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameLanameDOB(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName middleName lastName already exists or not,
			 * if not then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameMnameLname(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName lastName age and gender already exists or
			 * not, if not then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameLnameAgeGender(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName lastName and age already exists or not, if
			 * not then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameLnameAge(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName lastName and gender already exists or not,
			 * if not then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameLnameGender(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * Checking whether patient firstName lastName already exists or not, if not
			 * then proceed for insert otherwise ask user to add similar match
			 */
		} else if (patientDAOInf.verifyFnameLname(form)) {
			statusMessage = "input";
			return statusMessage;

			/*
			 * if above all conditions fails, insert patient detail into Patient,
			 * ContactInformation and EmergencyContact table
			 */
		} else {

			System.out.println("patient FN,LN::" + form.getFirstName() + "," + form.getLastName());
			username = form.getFirstName() + "." + form.getLastName();

			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			System.out.println("randomUUID::" + randomUUIDString);

			password = randomUUIDString.split("-")[0];

			System.out.println("username=::" + username);
			System.out.println("password=::" + password);

			boolean refByDoc = configXMLUtil.checkReferredByDoctorExists(form.getRefDoctor(), practiceID);

			if (!refByDoc) {
				status = patientDAOInf.insertReferredByDoctor(form.getRefDoctor(), practiceID);
				if (status.equalsIgnoreCase("success")) {
					int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
					form.setRefDoctorID(refDocID);

				}
			} else {
				int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
				form.setRefDoctorID(refDocID);
			}

			/*
			 * Inserting Patient details into Patient table
			 */
			statusMessage = patientDAOInf.insertPatientDetails(form, practiceID);

			if (statusMessage.equalsIgnoreCase("success")) {
				/*
				 * Retrieving patientID from Patient table on the basis of firstname lastname
				 * and dateOfBirth
				 */
				int patientID = patientDAOInf.retrievePatientID(form.getFirstName(), form.getLastName(), practiceID);
				System.out.println("patient ID is ::: " + patientID);
				if (patientID != 0) {
					System.out.println("patient ID is ::: " + patientID);

					/*
					 * Inserting a record with patientID and clinicID along with clinicRegNo into
					 * ClincRegistration table
					 */
					String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(form.getClinicSuffix(),
							form.getClinicID());

					statusMessage = patientDAOInf.insertClinicRegistration(patientID, form.getClinicID(), cilincRegNo);

					/*
					 * Inserting patient's identification details into ContactInformation table
					 */
					statusMessage = patientDAOInf.insertIdentification(form, patientID);

					if (statusMessage.equalsIgnoreCase("success")) {

						statusMessage = patientDAOInf.insertPECMedicalHistory(form, patientID);

						/*
						 * inserting emergency contact information into EmergencyContact table
						 */
						statusMessage = patientDAOInf.insertEmergencyContact(patientID, form);
						System.out.println("before insert emergency contcat ::: " + statusMessage);
						if (statusMessage.equalsIgnoreCase("success")) {

							patientDAOInf.updateUsernameAndPasswordIntoPatientTableByID(username, password, patientID);

							/*
							 * Check whether country is India, if so, then only send Welcome SMS to patient
							 * else do not send SMS
							 */
							if (form.getCountry().equals("India")) {

								/*
								 * Check whether appointment udpated flag is on or not, and depending upon that
								 * sending SMS and Email
								 */
								System.out.println("practice id is:::" + practiceID);
								boolean SMSCheck = util.verifyCommunicationCheck("smsWelcome");

								System.out.println("SMSCheck: " + SMSCheck);

								if (SMSCheck) {
									System.out.println("inside");
									/*
									 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
									 * welcome message
									 */
									System.out.println("patientID: " + patientID);

									String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

									/*
									 * Sending patient a welcome as well as Appointment scheduled message on
									 * checking whether mobile no is available for that patient or not
									 */
									if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
										System.out.println("inside if");
										System.out.println("Mobile no not found for patient.");
									} else {
										System.out.println("inside else");
										/*
										 * Sending patient a welcome SMS
										 */
										smsSender.sendWelcomeSMS(patientID, practiceID, mobileNo, form.getClinicID());

									}
								}

								boolean LoginSMSCheck1 = util.verifyCommunicationCheck("smsPatPortalCredentials");

								if (LoginSMSCheck1) {

									String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

									/*
									 * Sending patient a welcome as well as Appointment scheduled message on
									 * checking whether mobile no is available for that patient or not
									 */
									if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
										System.out.println("inside if");
										System.out.println("Mobile no not found for patient.");
									} else {
										System.out.println("inside else");
										/*
										 * Sending patient a login credentials SMS
										 */
										smsSender.sendPatientPortalCredentialsSMS(patientID, practiceID, mobileNo,
												form.getClinicID());

									}

								}
							} else {
								System.out.println("Patient's country is not India.");
							}

							boolean EmailCheck = util.verifyCommunicationCheck("emailWelcome");

							if (EmailCheck) {

								/*
								 * Retrieving patient's email ID, if not null, then sending patient a welcome
								 * mail
								 */
								String emailID = patientDAOInf.retrievePatientEmailByID(patientID);

								if (emailID == null || emailID == "" || emailID.isEmpty()) {
									System.out.println("EmailID no not found for patient.");
								} else {

									/*
									 * Sending welcome mail to patient
									 */
									emailUtil.sendWelcomeMail(patientID, practiceID, emailID, form.getClinicID());

								}

							}

							boolean credEmailCheck1 = util.verifyCommunicationCheck("emailPatPortalCredentials");

							if (credEmailCheck1) {

								/*
								 * Retrieving patient's email ID, if not null, then sending patient a welcome
								 * mail
								 */
								String emailID = patientDAOInf.retrievePatientEmailByID(patientID);

								if (emailID == null || emailID == "" || emailID.isEmpty()) {
									System.out.println("EmailID no not found for patient.");
								} else {

									/*
									 * Sending welcome mail to patient
									 */
									emailUtil.sendPatientPortalCredentialsMail(patientID, practiceID, emailID,
											form.getClinicID());

								}

							}

							return statusMessage;
						} else {
							System.out.println(
									"Failed to insert  patient's emergency cotact information detail into EmergencyContact table");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to insert  identification detail into Identification table");
						statusMessage = "error";
						return statusMessage;
					}
				} else {
					System.out.println("Failed to retrieve patientID from database...");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Failed to insert patient Details into Patient Table");
				statusMessage = "error";
				return statusMessage;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#editPatientDetail(com
	 * .edhanvantari.form.PatientForm)
	 */
	public String editPatientDetail(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		String status = "";

		/*
		 * Checking whether first name and last name and date of birth already exists
		 * into table or not. If yes, update the record
		 */
		statusMessage = patientDAOInf.verifyPatientCredential(form);

		if (statusMessage.equalsIgnoreCase("success")) {

			boolean refByDoc = configXMLUtil.checkReferredByDoctorExists(form.getRefDoctor(), form.getPracticeID());

			if (!refByDoc) {
				status = patientDAOInf.insertReferredByDoctor(form.getRefDoctor(), form.getPracticeID());
				if (status.equalsIgnoreCase("success")) {
					int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
					form.setRefDoctorID(refDocID);
				}
			} else {
				int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
				form.setRefDoctorID(refDocID);
			}

			/*
			 * updating patient details into Patient table
			 */
			statusMessage = patientDAOInf.updatePatientDetail(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				statusMessage = patientDAOInf.updatePECMedicalHistory(form);

				/*
				 * check whether data exists into Identification table corresponding to
				 * patientID, if exists, then udpate the details else insert new details with
				 * current patientID
				 */
				boolean identificationCheck = patientDAOInf.verifyDataExists(form.getPatientID(), "Identification");

				if (identificationCheck) {
					/*
					 * Updating identification details
					 */
					statusMessage = patientDAOInf.updateIdentification(form);
				} else {
					/*
					 * Inserting identification details
					 */
					statusMessage = patientDAOInf.insertIdentification(form, form.getPatientID());
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * check whether data exists into Identification table corresponding to
					 * patientID, if exists, then udpate the details else insert new details with
					 * current patientID
					 */
					boolean emergencyContactCheck = patientDAOInf.verifyDataExists(form.getPatientID(),
							"EmergencyContact");

					if (emergencyContactCheck) {

						/*
						 * Updating Emergency contact information
						 */
						statusMessage = patientDAOInf.updateEmergencyContact(form);

					} else {

						/*
						 * Updating Emergency contact information
						 */
						statusMessage = patientDAOInf.insertEmergencyContact(form.getPatientID(), form);

					}

					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * Check whether patient with patientID already exist for the current clinic, if
						 * not, add new entry with to ClinicRegistraion table with current clinicID and
						 * new ClinicRegNo, else proceed further
						 */
						boolean check = patientDAOInf.verifyPatientExistsForCurrentClinic(form.getPatientID(),
								form.getClinicID());

						if (check) {
							System.out.println("Patient already registered with the current clinic");
						} else {

							/*
							 * Inserting a record with patientID and clinicID along with clinicRegNo into
							 * ClincRegistration table
							 */
							String cilincRegNo = patientDAOInf
									.retirevePatientClinicRegistrationNo(form.getClinicSuffix(), form.getClinicID());

							statusMessage = patientDAOInf.insertClinicRegistration(form.getPatientID(),
									form.getClinicID(), cilincRegNo);

							if (statusMessage.equals("success")) {
								System.out.println("Clinic registration details inserted successfully.");
							} else {
								System.out.println(
										"Failed to insert clinic registration details into ClinicRegistration table.");

							}

						}

						return statusMessage;
					} else {
						System.out.println("Failed to update Emergency Contact information details.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println("Failed to update identification details.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Failed to update patient details.");
				statusMessage = "error";
				return statusMessage;
			}
		} else {
			/*
			 * Verifying whether firstName and lastName and DateOfBirth already exists into
			 * database or not. If not then only proceed further else give error message
			 */
			statusMessage = patientDAOInf.verifyPatientDetail(form.getFirstName().trim(), form.getLastName().trim());

			if (!statusMessage.equals("success")) {
				System.out.println("Patient does not exists.");

				boolean refByDoc = configXMLUtil.checkReferredByDoctorExists(form.getRefDoctor(), form.getPracticeID());

				if (!refByDoc) {
					status = patientDAOInf.insertReferredByDoctor(form.getRefDoctor(), form.getPracticeID());
					if (status.equalsIgnoreCase("success")) {
						int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
						form.setRefDoctorID(refDocID);
					}
				} else {
					int refDocID = patientDAOInf.retrieveReferredByDoctorID(form.getRefDoctor());
					form.setRefDoctorID(refDocID);
				}

				/*
				 * updating patient details into Patient table
				 */
				statusMessage = patientDAOInf.updatePatientDetail(form);

				if (statusMessage.equalsIgnoreCase("success")) {

					statusMessage = patientDAOInf.updatePECMedicalHistory(form);

					/*
					 * check whether data exists into Identification table corresponding to
					 * patientID, if exists, then udpate the details else insert new details with
					 * current patientID
					 */
					boolean identificationCheck = patientDAOInf.verifyDataExists(form.getPatientID(), "Identification");

					if (identificationCheck) {
						/*
						 * Updating identification details
						 */
						statusMessage = patientDAOInf.updateIdentification(form);
					} else {
						/*
						 * Inserting identification details
						 */
						statusMessage = patientDAOInf.insertIdentification(form, form.getPatientID());
					}
					if (statusMessage.equalsIgnoreCase("success")) {
						/*
						 * check whether data exists into Identification table corresponding to
						 * patientID, if exists, then udpate the details else insert new details with
						 * current patientID
						 */
						boolean emergencyContactCheck = patientDAOInf.verifyDataExists(form.getPatientID(),
								"EmergencyContact");

						if (emergencyContactCheck) {

							/*
							 * Updating Emergency contact information
							 */
							statusMessage = patientDAOInf.updateEmergencyContact(form);

						} else {

							/*
							 * Updating Emergency contact information
							 */
							statusMessage = patientDAOInf.insertEmergencyContact(form.getPatientID(), form);

						}
						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * Check whether patient with patientID already exist for the current clinic, if
							 * not, add new entry with to ClinicRegistraion table with current clinicID and
							 * new ClinicRegNo, else proceed further
							 */
							boolean check = patientDAOInf.verifyPatientExistsForCurrentClinic(form.getPatientID(),
									form.getClinicID());

							if (check) {
								System.out.println("Patient already registered with the current clinic");
							} else {

								/*
								 * Inserting a record with patientID and clinicID along with clinicRegNo into
								 * ClincRegistration table
								 */
								String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(
										form.getClinicSuffix(), form.getClinicID());

								statusMessage = patientDAOInf.insertClinicRegistration(form.getPatientID(),
										form.getClinicID(), cilincRegNo);

								if (statusMessage.equals("success")) {
									System.out.println("Clinic registration details inserted successfully.");
								} else {
									System.out.println(
											"Failed to insert clinic registration details into ClinicRegistration table.");

								}

							}

							return statusMessage;
						} else {
							System.out.println("Failed to update Emergency Contact information details.");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to update identification details.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println("Failed to update patient details.");
					statusMessage = "error";
					return statusMessage;
				}
			} else {
				System.out.println("Patient with same firstName and lastName and dateOfbirth already exists..");
				statusMessage = "input";
				return statusMessage;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#insertPatient(com.
	 * edhanvantari.form.PatientForm, int)
	 */
	public String insertPatient(PatientForm form, int practiceID) {
		patientDAOInf = new PatientDAOImpl();

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		form.setPracticeID(practiceID);

		String username = "";
		String password = "";

		// check if FN,MN(first letter),LN by patientID
		boolean check1 = patientDAOInf.verifyFnameMnameLname(form);
		System.out.println("check1 value::" + check1);
		if (!check1) {
			System.out.println("inside ifif");
			System.out.println("patient FN,MN,LN:" + form.getMiddleName());

			username = form.getFirstName() + "." + form.getMiddleName().charAt(0) + "." + form.getLastName();

			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			System.out.println("randomUUID::" + randomUUIDString);

			password = randomUUIDString.split("-")[0];

			System.out.println("username=::" + username);
			System.out.println("password=::" + password);

		} else {
			System.out.println("inside else else");
			String age = patientDAOInf.retrieveAge(form);
			System.out.println(
					"patient FN,MN,LN:" + form.getFirstName() + "," + form.getMiddleName() + "," + form.getLastName());

			if (form.getMiddleName() == null || form.getMiddleName() == "") {
				username = form.getFirstName() + "." + form.getLastName() + age;
			} else if (form.getMiddleName().isEmpty()) {
				username = form.getFirstName() + "." + form.getLastName() + age;
			} else {
				username = form.getFirstName() + "." + form.getMiddleName().charAt(0) + "." + form.getLastName() + age;
			}

			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			System.out.println("randomUUID::" + randomUUIDString);

			password = randomUUIDString.split("-")[0];

			System.out.println("username=::" + username);
			System.out.println("password=::" + password);

		}

		/*
		 * Inserting Patient details into Patient table
		 */
		statusMessage = patientDAOInf.insertPatientDetails(form, practiceID);

		if (statusMessage.equalsIgnoreCase("success")) {
			/*
			 * Retrieving patientID from Patient table on the basis of firstname lastname
			 * and dateOfBirth
			 */
			int patientID = patientDAOInf.retrievePatientID(form.getFirstName(), form.getLastName(), practiceID);

			form.setPatientID(patientID);

			/*
			 * Inserting a record with patientID and clinicID along with clinicRegNo into
			 * ClincRegistration table
			 */
			String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(form.getClinicSuffix(),
					form.getClinicID());

			statusMessage = patientDAOInf.insertClinicRegistration(patientID, form.getClinicID(), cilincRegNo);

			if (patientID != 0) {
				System.out.println("patient ID is ::: " + patientID);
				/*
				 * Inserting identification details into Identification table
				 */
				statusMessage = patientDAOInf.insertIdentification(form, patientID);
				if (statusMessage.equalsIgnoreCase("success")) {
					/*
					 * inserting emergency contact information into EmergencyContact table
					 */
					statusMessage = patientDAOInf.insertEmergencyContact(patientID, form);
					if (statusMessage.equalsIgnoreCase("success")) {

						patientDAOInf.updateUsernameAndPasswordIntoPatientTableByID(username, password, patientID);

						/*
						 * Check whether country is India, if so, then only send Welcome SMS to patient
						 * else do not send SMS
						 */
						if (form.getCountry().equals("India")) {

							/*
							 * Check whether appointment udpated flag is on or not, and depending upon that
							 * sending SMS and Email
							 */
							boolean SMSCheck = util.verifyCommunicationCheck("smsWelcome");

							if (SMSCheck) {

								/*
								 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
								 * welcome message
								 */
								String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

								/*
								 * Sending patient a welcome as well as Appointment scheduled message on
								 * checking whether mobile no is available for that patient or not
								 */
								if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
									System.out.println("Mobile no not found for patient.");
								} else {
									/*
									 * Sending patient a welcome SMS
									 */
									smsSender.sendWelcomeSMS(patientID, practiceID, mobileNo, form.getClinicID());

								}

							}

							boolean LoginSMSCheck1 = util.verifyCommunicationCheck("smsPatPortalCredentials");

							if (LoginSMSCheck1) {

								/*
								 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
								 * welcome message
								 */
								String mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientID);

								/*
								 * Sending patient a welcome as well as Appointment scheduled message on
								 * checking whether mobile no is available for that patient or not
								 */
								if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
									System.out.println("Mobile no not found for patient.");
								} else {
									/*
									 * Sending patient a login credentials SMS
									 */
									smsSender.sendPatientPortalCredentialsSMS(patientID, practiceID, mobileNo,
											form.getClinicID());

								}

							}

						} else {
							System.out.println("Patient's country is not India.");
						}

						boolean EmailCheck = util.verifyCommunicationCheck("emailWelcome");

						if (EmailCheck) {

							/*
							 * Retrieving patient's email ID, if not null, then sending patient a welcome
							 * mail
							 */
							String emailID = patientDAOInf.retrievePatientEmailByID(patientID);

							if (emailID == null || emailID == "" || emailID.isEmpty()) {
								System.out.println("EmailID no not found for patient.");
							} else {

								/*
								 * Sending welcome mail to patient
								 */
								emailUtil.sendWelcomeMail(patientID, practiceID, emailID, form.getClinicID());

							}

						}

						boolean credEmailCheck1 = util.verifyCommunicationCheck("emailPatPortalCredentials");

						if (credEmailCheck1) {

							/*
							 * Retrieving patient's email ID, if not null, then sending patient a welcome
							 * mail
							 */
							String emailID = patientDAOInf.retrievePatientEmailByID(patientID);

							if (emailID == null || emailID == "" || emailID.isEmpty()) {
								System.out.println("EmailID no not found for patient.");
							} else {

								/*
								 * Sending welcome mail to patient
								 */
								emailUtil.sendPatientPortalCredentialsMail(patientID, practiceID, emailID,
										form.getClinicID());

							}

						}

						return statusMessage;
					} else {
						System.out.println(
								"Failed to insert  patient's emergency cotact information detail into EmergencyContact table");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println("Failed to insert  identification detail into Identification table");
					statusMessage = "error";
					return statusMessage;
				}
			} else {
				System.out.println("Failed to retrieve patientID from database...");
				statusMessage = "error";
				return statusMessage;
			}

		} else {
			System.out.println("Failed to insert patient Details into Patient Table");
			statusMessage = "error";
			return statusMessage;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addNewPrescription(com
	 * .edhanvantari.form.PatientForm)
	 */
	public String addNewPrescription(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Check whether visit added before adding prescription details, if added then
		 * only proceed further to add prescription details into Prescription table else
		 * give error message saying visit not added
		 */
		if (form.getVisitID() == 0) {

			System.out.println("No visit added.");

			statusMessage = "noVisit";

			return statusMessage;

		} else {

			statusMessage = patientDAOInf.updateVisitColumns(form.getNextVisitDays(), form.getAdvice(),
					form.getVisitID());

			/*
			 * check whether prescription rows added or not and depending upon that
			 * proceeding further to insert records into Prescription table
			 */
			if (form.getNewDrugID() == null) {
				System.out.println("No drugs added into prescription");

				statusMessage = "success";
			} else {

				for (int i = 0; i < form.getNewDrugID().length; i++) {

					String tradeName = form.getNewDrugID()[i];
					String comment = form.getNewDrugComment()[i];
					String noOfDaysStr = form.getNewDrugNoOfDays()[i];
					String frequency = form.getNewDrugFrequency()[i];
					int categoryID = 0;

					if (frequency.startsWith(",")) {
						frequency = frequency.substring(1);
					}

					String noOfPillsStr = form.getNewDrugQuantity()[i];
					double noOfPills = 0;
					if (noOfPillsStr == null || noOfPillsStr == "") {
						noOfPills = 0;
					} else if (noOfPillsStr.isEmpty()) {
						noOfPills = 0;
					} else {
						noOfPills = Double.parseDouble(noOfPillsStr);
					}

					if (form.getNewDrugCategoryID() != null) {

						if (form.getNewDrugCategoryID()[i] == null || form.getNewDrugCategoryID()[i] == "") {
							categoryID = 0;
						} else if (form.getNewDrugCategoryID()[i].isEmpty()) {
							categoryID = 0;
						} else {
							categoryID = Integer.parseInt(form.getNewDrugCategoryID()[i]);
						}

					}

					double dosage = 0;

					statusMessage = patientDAOInf.insertVisitPrescriptionDetails(tradeName, dosage, frequency,
							noOfPills, comment, noOfDaysStr, form.getVisitID(), categoryID);

					if (statusMessage.equals("success")) {

						int prescriptionID = patientDAOInf.retrievePrescriptionID(tradeName, form.getVisitID());

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyProductExits(tradeName,
						 * form.getClinicID());
						 * 
						 * if (!check) { patientDAOInf.insertTradeNameInProductTable(tradeName,
						 * form.getClinicID()); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println("Drug details added successfully into Prescription table");

				} else {

					System.out.println("Failed to add drug details into prescription table.");

					statusMessage = "error";
				}
			}

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (form.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < form.getTest().length; i++) {

					String testName = form.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, form.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}
			
			
			// Code for adding diagonstic details 
			if(form.getDiagnosticArray() == null) {
				System.out.println("No diagnostic added into Diagnostics");

				statusMessage = "success";
			} else {
				for (int i = 0; i < form.getDiagnosticArray().length; i++) {
					
					String diagnostic = form.getDiagnosticArray()[i];
					
					statusMessage = patientDAOInf.insertDiagnosticDetails(diagnostic, form.getVisitID());
					
					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}
				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}
			
			// Code for adding procedures details
			if(form.getProcedureArray() == null) {
				System.out.println("No Procedure added into Procedures");

				statusMessage = "success";
			} else {
				
				for(int i=0; i<form.getProcedureArray().length; i++) {
					
					String procedure = form.getProcedureArray()[i];
					
					statusMessage = patientDAOInf.insertProcedureDetails(procedure, form.getVisitID());
					
					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}	
				}
				
				if (statusMessage.equals("success")) {

					System.out.println(
							"Procedure details added successfully into PrescribedProcedures table");

				} else {

					System.out.println("Failed to add procedure details into PrescribedProcedures table.");

					statusMessage = "error";
				}
				
			}
			
			return statusMessage;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addNewBill(com.
	 * edhanvantari .form.PatientForm)
	 */
	/*
	 * public String addNewBill(PatientForm form) { patientDAOInf = new
	 * PatientDAOImpl();
	 * 
	 * if (form.getVisitID() == 0) { System.out.println("No vist added");
	 * 
	 * statusMessage = "noVisit";
	 * 
	 * return statusMessage; } else {
	 * 
	 * Inserting Billing details into Billing table
	 * 
	 * statusMessage = patientDAOInf.insertBillDetails(form, form.getVisitID()); if
	 * (statusMessage.equalsIgnoreCase("success")) { return statusMessage; } else {
	 * System.out.println("Failed to insert billing details into table");
	 * statusMessage = "error"; return statusMessage; } }
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addOptician(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String addOptician(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		/*
		 * Inserting Optician details into Optician table
		 */
		statusMessage = patientDAOInf.insertOpticianDetails(form);
		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * Retrieving last entered optician ID from visitID
			 */
			int opticianID = patientDAOInf.retrieveOpticianID(form.getVisitID());

			// Setting above retrieved optician ID into patientForm's opticinID
			// variable
			form.setOpticinID(opticianID);

			/*
			 * Inserting Cycloplegic Refraction details into CycloplegicRefraction table
			 */
			String status = patientDAOInf.insertCycloplegicRefraction(form);

			if (status.equals("success")) {
				System.out.println("Cycloplegic Refraction details added successfully");
			} else {
				System.out.println("Failed to add Cycloplegic Refraction details.");
			}
			/*
			 * Inserting optician Old glasses detials into OldGlases table
			 */
			statusMessage = patientDAOInf.insertOpticianOldGlassDetails(form);
			if (statusMessage.equalsIgnoreCase("success")) {
				/*
				 * Insreting eyewear details into Eyewear table
				 */
				System.out.println("Value of Tint is: " + form.getTint());
				/*
				 * if (form.getTint() == "" || form.getTint() == null) { return statusMessage; }
				 * else if (form.getTint().isEmpty()) { return statusMessage; } else {
				 * statusMessage = patientDAOInf.insertEyewearDetails(form); if
				 * (statusMessage.equalsIgnoreCase("success")) {
				 * 
				 * } else { System.out.println("Failed to insert Eyewear details");
				 * statusMessage = "error"; } }
				 */

				statusMessage = patientDAOInf.insertEyewearDetails(form);
				if (statusMessage.equalsIgnoreCase("success")) {
					if (form.getTint() == "" || form.getTint() == null) {
						return statusMessage;
					} else if (form.getTint().isEmpty()) {
						return statusMessage;
					}
				} else {
					System.out.println("Failed to insert Eyewear details");
					statusMessage = "error";
				}

			} else {
				System.out.println("Failed to insert Optician Old glasses details");
				statusMessage = "error";
			}
		} else {
			System.out.println("Failed to insert Optician details");
			statusMessage = "error";
		}

		return statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#updateOPDVisit(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String updateOPDVisit(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int nextVisitDays = patientDAOInf.retrieveNextVisitDays(patientForm.getVisitTypeID());

		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c.setTime(dateFormat.parse(patientForm.getVisitDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		c.add(Calendar.DAY_OF_MONTH, nextVisitDays);
		String nextVisitDate = dateFormat.format(c.getTime());

		/*
		 * Updating PatientVisit details
		 */
		statusMessage = patientDAOInf.updatePatientVisit(patientForm, patientForm.getVisitNumber(), nextVisitDate);
		if (statusMessage.equalsIgnoreCase("success")) {

			boolean check1 = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

			if (!check1) {
				patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
			}

			/*
			 * Updating OphthalmologyOPD details
			 */
			boolean check = patientDAOInf.verifyVisitExists("OphthalmologyOPD", patientForm.getVisitID());
			if (check) {
				statusMessage = patientDAOInf.updateOPDVisit(patientForm);
				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully udpated Ophtholmology OPD details.");
				} else {

					System.out.println("Failed to udpate Ophtholmology OPD details.");
					statusMessage = "error";
				}
			} else {
				/*
				 * Inserting OPD details into OphthaolmologyOPD table
				 */
				statusMessage = patientDAOInf.insertOPDVisit(patientForm);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted Ophtholmology OPD details.");

				} else {

					System.out.println("Failed to insert OPD details into OphthalmologyOPD table.");

				}
			}
		} else {
			System.out.println("Failed to insert visit details.");
			statusMessage = "error";

		}

		return statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addNewIPDVisit(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String addNewIPDVisit(PatientForm patientForm, String realPath) {
		patientDAOInf = new PatientDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		try {

			/*
			 * Checking whether visitID is 0 or not, if 0, then insert into Visit table else
			 * update the visit table
			 */
			if (patientForm.getVisitID() == 0) {

				/*
				 * Retrieving last entered visit's visitNumber and adding one to it in order to
				 * insert new visitNumber into Patient table
				 */
				int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(),
						patientForm.getClinicID());

				// visitNumber += 1;

				/*
				 * Inserting into Visit table
				 */
				statusMessage = patientDAOInf.insertPatientVisit(patientForm, visitNumber, 0, "");

				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * retrieving last entered visit ID based on new visitNumber and setting it into
					 * patientForm's visitID variable
					 */
					patientForm.setVisitID(patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber,
							patientForm.getPatientID(), patientForm.getVisitTypeID(), patientForm.getClinicID()));

					System.out.println("VisitID : " + patientForm.getVisitID());
					if (patientForm.getOSLt() == null) {
						System.out.println("No complaint added into OpthalmologyComplaints");

						statusMessage = "success";

					} else {
						/*
						 * Inserting complaint details into OpthalmologyComplaints table
						 */
						for (int i = 0; i < patientForm.getOSLt().length; i++) {

							statusMessage = patientDAOInf.insertComplaint(patientForm.getODRt()[i],
									patientForm.getOSLt()[i], patientForm.getVisitID());

						}
					}

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Inside.....");

						/*
						 * Adding IPD details into OphthalmologyIPD table
						 */
						statusMessage = patientDAOInf.insertIPDVisit(patientForm);
						if (statusMessage.equalsIgnoreCase("success")) {
							/*
							 * INserting Continuation sheet details
							 */
							statusMessage = patientDAOInf.insertContinuationSheetDetails(patientForm);
							if (statusMessage.equalsIgnoreCase("success")) {

								/*
								 * Inserting OT notes
								 */
								statusMessage = patientDAOInf.insertOTNOtes(patientForm);
								if (statusMessage.equalsIgnoreCase("success")) {

									/*
									 * Inserting into VitalSigns table
									 */
									statusMessage = patientDAOInf.insertOE(patientForm);

									if (statusMessage.equalsIgnoreCase("success")) {

										/*
										 * Inserting into Investigation table
										 */

										for (int i = 0; i < patientForm.getValues().length; i++) {

											if (patientForm.getValues()[i] == null
													|| patientForm.getValues()[i] == "") {
												continue;
											} else if (patientForm.getValues()[i].isEmpty()) {
												System.out.println(
														"patientForm.getValues : " + patientForm.getValues()[i]);
												continue;
											} else {

												patientDAOInf.insertInvestigations(patientForm,
														patientForm.getPanel()[i], patientForm.getValues()[i]);
											}
										}

										if (statusMessage.equalsIgnoreCase("success")) {

											// Setting setConsentTextDB as NULL
											patientForm.setConsentFileDBName(null);

											/*
											 * Storing user uploaded Consent doc file into directory of Project
											 */
											if (patientForm.getConsentFile() != null) {

												String[] array = patientForm.getConsentFileFileName().split("\\.");

												String fileExtension = array[1];

												String ConsentFileName = patientForm.getFirstName() + "_"
														+ patientForm.getLastName() + "_" + patientForm.getPatientID()
														+ "_" + patientForm.getVisitID() + "_IPDConset";

												String fileNameToBeStored = ConsentFileName + "." + fileExtension;

												System.out.println("Original File name is ::::"
														+ patientForm.getConsentFileFileName());

												System.out.println(
														"File Name to be stored into DB is ::: " + fileNameToBeStored);

												/**
												 * Setting file name with S3 bucket path to be inserted into AppUser
												 * table into SignatureDBName
												 */
												patientForm.setConsentFileDBName(fileNameToBeStored);
												// Storing file to S3 RDML INPUT FILE location
												statusMessage = awss3Connect.pushFile(patientForm.getConsentFile(),
														fileNameToBeStored, bucketName, bucketRegion, s3rdmlFilePath);
											}

											/*
											 * Inserting into Consent table
											 */
											patientDAOInf.insertConsentDocument(patientForm.getConsentFileDBName(),
													patientForm.getVisitID());

											if (statusMessage.equalsIgnoreCase("success")) {

												return statusMessage;
											} else {
												System.out.println("Failed to insert Consent details.");
												statusMessage = "error";
												return statusMessage;
											}

										} else {
											System.out.println("Failed to insert Investigation details.");
											statusMessage = "error";
											return statusMessage;
										}

									} else {
										System.out.println("Failed to insert Vital signs details.");
										statusMessage = "error";
										return statusMessage;
									}
								} else {
									System.out.println("Failed to insert OT Notes details.");
									statusMessage = "error";
									return statusMessage;
								}
							} else {
								System.out.println("Failed to insert IPD Continuation Sheet details.");
								statusMessage = "error";
								return statusMessage;
							}
						} else {
							System.out.println("Failed to insert Ophtholmology IPD details.");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to insert IPD complaint details.");
						statusMessage = "error";
						return statusMessage;

					}

				} else {
					System.out.println("Failed to insert patient visit details.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {

				/*
				 * Updating patient visit details
				 */
				statusMessage = patientDAOInf.updatePatientVisitDetails(patientForm);

				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Inserting complaint details into OpthalmologyComplaints table
					 */
					if (patientForm.getOSLt() == null) {
						System.out.println("No complaint added into OpthalmologyComplaints");

						statusMessage = "success";

					} else {
						/*
						 * Inserting complaint details into OpthalmologyComplaints table
						 */
						for (int i = 0; i < patientForm.getOSLt().length; i++) {

							statusMessage = patientDAOInf.insertComplaint(patientForm.getODRt()[i],
									patientForm.getOSLt()[i], patientForm.getVisitID());

						}
					}

					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * Adding/Updating IPD details into OphthalmologyIPD table
						 */
						boolean OphthalmologyIPDCheck = patientDAOInf.verifyVisitExists("OphthalmologyIPD",
								patientForm.getVisitID());

						if (OphthalmologyIPDCheck) {
							statusMessage = patientDAOInf.updateOEIPDVisit(patientForm);
						} else {
							statusMessage = patientDAOInf.insertIPDVisit(patientForm);
						}

						if (statusMessage.equalsIgnoreCase("success")) {
							/*
							 * INserting/Updating Continuation sheet details
							 */
							boolean IPDContinuationSheetCheck = patientDAOInf.verifyVisitExists("IPDContinuationSheet",
									patientForm.getVisitID());

							if (IPDContinuationSheetCheck) {
								statusMessage = patientDAOInf.updateContinuationSheetDetails(patientForm);
							} else {
								statusMessage = patientDAOInf.insertContinuationSheetDetails(patientForm);
							}

							if (statusMessage.equalsIgnoreCase("success")) {

								/*
								 * Inserting/Updating OT notes
								 */
								boolean OTNotesCheck = patientDAOInf.verifyVisitExists("OTNotes",
										patientForm.getVisitID());

								if (OTNotesCheck) {
									statusMessage = patientDAOInf.updateOTNotes(patientForm);
								} else {
									statusMessage = patientDAOInf.insertOTNOtes(patientForm);
								}

								if (statusMessage.equalsIgnoreCase("success")) {

									/*
									 * Inserting/Updating into VitalSigns table
									 */

									boolean VitalSignsCheck = patientDAOInf.verifyVisitExists("VitalSigns",
											patientForm.getVisitID());

									if (VitalSignsCheck) {
										statusMessage = patientDAOInf.updateOE(patientForm);
									} else {
										statusMessage = patientDAOInf.insertOE(patientForm);
									}

									if (statusMessage.equalsIgnoreCase("success")) {

										boolean LabInvestigationsCheck = patientDAOInf
												.verifyVisitExists("LabInvestigations", patientForm.getVisitID());

										if (LabInvestigationsCheck) {
											for (int i = 0; i < patientForm.getEditValues().length; i++) {
												statusMessage = patientDAOInf.updateInvestigation(
														Integer.parseInt(
																patientForm.getEditInvestigationDetailsID()[i]),
														patientForm.getEditValues()[i]);
											}

										} else {

											for (int i = 0; i < patientForm.getValues().length; i++) {

												if (patientForm.getValues()[i] == null
														|| patientForm.getValues()[i] == "") {
													continue;
												} else if (patientForm.getValues()[i].isEmpty()) {

													continue;
												} else {

													patientDAOInf.insertInvestigations(patientForm,
															patientForm.getPanel()[i], patientForm.getValues()[i]);
												}
											}
										}

										if (statusMessage.equalsIgnoreCase("success")) {

											return statusMessage;

											// Setting setConsentTextDB as NULL
											/*
											 * patientForm.setConsentFileDBName(null);
											 * 
											 * 
											 * Storing user uploaded Consent doc file into directory of Project
											 * 
											 * 
											 * String[] array = patientForm.getConsentFileFileName().split("\\.");
											 * 
											 * String fileExtension = array[1];
											 * 
											 * String ConsentFileName = patientForm.getFirstName() + "_" +
											 * patientForm.getLastName() + "_" + patientForm.getPatientID() + "_" +
											 * patientForm.getVisitID() + "_IPDConset";
											 * 
											 * String fileNameToBeStored = ConsentFileName + "." + fileExtension;
											 * 
											 * System.out.println("Original File name is ::::" +
											 * patientForm.getConsentFileFileName());
											 * 
											 * System.out.println( "File Name to be stored into DB is ::: " +
											 * fileNameToBeStored);
											 * 
											 *//**
												 * Setting file name with S3 bucket path to be inserted into AppUser
												 * table into SignatureDBName
												 *//*
													 * patientForm.setConsentFileDBName(fileNameToBeStored); // Storing
													 * file to S3 RDML INPUT FILE location statusMessage =
													 * awss3Connect.pushFile(patientForm.getConsentFile(),
													 * fileNameToBeStored, bucketName, bucketRegion, s3rdmlFilePath);
													 * 
													 * 
													 * Inserting into Consent table
													 * 
													 * patientDAOInf.insertConsentDocument(patientForm.
													 * getConsentFileDBName(), patientForm.getVisitID());
													 * 
													 * if (statusMessage.equalsIgnoreCase("success")) {
													 * 
													 * return statusMessage; } else {
													 * System.out.println("Failed to insert Consent details.");
													 * statusMessage = "error"; return statusMessage; }
													 */

										} else {
											System.out.println("Failed to configure Investigation details.");
											statusMessage = "error";
											return statusMessage;
										}

									} else {
										System.out.println("Failed to configure Vital signs details.");
										statusMessage = "error";
										return statusMessage;
									}
								} else {
									System.out.println("Failed to configure OT Notes details.");
									statusMessage = "error";
									return statusMessage;
								}
							} else {
								System.out.println("Failed to configure IPD Continuation Sheet details.");
								statusMessage = "error";
								return statusMessage;
							}
						} else {
							System.out.println("Failed to configure Ophtholmology IPD details.");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to configure IPD complaint details.");
						statusMessage = "error";
						return statusMessage;

					}

				} else {
					System.out.println("Failed to update patient visit details.");
					statusMessage = "error";
					return statusMessage;
				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while copyin file to temp directory due to:::" + exception.getMessage());
			statusMessage = "error";
			return statusMessage;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#updateIPDVisit(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String updateIPDVisit(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		try {

			patientForm.setDiagnosis(patientForm.getCancerType());

			/*
			 * UPdating visit for IPD
			 */
			statusMessage = patientDAOInf.updateIPDVisit(patientForm);
			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Inserting complaint details into OpthalmologyComplaints table
				 */

				if (patientForm.getOSLt() == null) {
					System.out.println("No complaint added into OpthalmologyComplaints");

					statusMessage = "success";

				} else {
					/*
					 * Inserting complaint details into OpthalmologyComplaints table
					 */
					for (int i = 0; i < patientForm.getOSLt().length; i++) {

						statusMessage = patientDAOInf.insertComplaint(patientForm.getODRt()[i],
								patientForm.getOSLt()[i], patientForm.getVisitID());

					}
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Adding/Editing IPD details into OphthalmologyIPD table
					 */
					boolean OphthalmologyIPDCheck = patientDAOInf.verifyVisitExists("OphthalmologyIPD",
							patientForm.getVisitID());

					if (OphthalmologyIPDCheck) {
						statusMessage = patientDAOInf.updateOEIPDVisit(patientForm);
					} else {
						statusMessage = patientDAOInf.insertIPDVisit(patientForm);
					}

					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * Adding/Updating Continuation sheet details
						 */
						boolean IPDContinuationSheetCheck = patientDAOInf.verifyVisitExists("IPDContinuationSheet",
								patientForm.getVisitID());

						if (IPDContinuationSheetCheck) {
							statusMessage = patientDAOInf.updateContinuationSheetDetails(patientForm);
						} else {
							statusMessage = patientDAOInf.insertContinuationSheetDetails(patientForm);
						}

						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * Adding/Updating OT notes
							 */

							boolean OTNotesCheck = patientDAOInf.verifyVisitExists("OTNotes", patientForm.getVisitID());

							if (OTNotesCheck) {
								statusMessage = patientDAOInf.updateOTNotes(patientForm);
							} else {
								statusMessage = patientDAOInf.insertOTNOtes(patientForm);
							}

							if (statusMessage.equalsIgnoreCase("success")) {

								/*
								 * Adding/Updating Vital signs
								 */

								boolean VitalSignsCheck = patientDAOInf.verifyVisitExists("VitalSigns",
										patientForm.getVisitID());

								if (VitalSignsCheck) {
									statusMessage = patientDAOInf.updateOE(patientForm);
								} else {
									statusMessage = patientDAOInf.insertOE(patientForm);
								}

								if (statusMessage.equalsIgnoreCase("success")) {

									/*
									 * Adding/Updating Investigation details
									 */
									boolean LabInvestigationsCheck = patientDAOInf
											.verifyVisitExists("LabInvestigations", patientForm.getVisitID());

									if (LabInvestigationsCheck) {
										for (int i = 0; i < patientForm.getEditValues().length; i++) {
											statusMessage = patientDAOInf.updateInvestigation(
													Integer.parseInt(patientForm.getEditInvestigationDetailsID()[i]),
													patientForm.getEditValues()[i]);
										}

									} else {

										for (int i = 0; i < patientForm.getValues().length; i++) {

											if (patientForm.getValues()[i] == null
													|| patientForm.getValues()[i] == "") {
												continue;
											} else if (patientForm.getValues()[i].isEmpty()) {
												System.out.println(
														"patientForm.getValues : " + patientForm.getValues()[i]);
												continue;
											} else {

												patientDAOInf.insertInvestigations(patientForm,
														patientForm.getPanel()[i], patientForm.getValues()[i]);
											}
										}

									}

									if (statusMessage.equalsIgnoreCase("success")) {

										// patientForm.setConsentFileDBName(null);

										/*
										 * Storing user uploaded Consent doc file into directory of Project
										 */

										if (patientForm.getConsentFile() != null) {

											String[] array = patientForm.getConsentFileFileName().split("\\.");

											String fileExtension = array[1];

											String ConsentFileName = patientForm.getFirstName() + "_"
													+ patientForm.getLastName() + "_" + patientForm.getPatientID() + "_"
													+ patientForm.getVisitID() + "_IPDConset";

											String fileNameToBeStored = ConsentFileName + "." + fileExtension;

											System.out.println("Original File name is ::::"
													+ patientForm.getConsentFileFileName());

											System.out.println(
													"File Name to be stored into DB is ::: " + fileNameToBeStored);

											/**
											 * Setting file name with S3 bucket path to be inserted into AppUser table
											 * into SignatureDBName
											 */
											patientForm.setConsentFileDBName(fileNameToBeStored);
											// Storing file to S3 RDML INPUT FILE location
											statusMessage = awss3Connect.pushFile(patientForm.getConsentFile(),
													fileNameToBeStored, bucketName, bucketRegion, s3rdmlFilePath);

										}

										/*
										 * Inserting into Consent table
										 */
										patientDAOInf.updateConsentDocument(patientForm.getConsentFileDBName(),
												patientForm.getVisitID());

										if (statusMessage.equalsIgnoreCase("success")) {

											return statusMessage;
										} else {
											System.out.println("Failed to configure Consent details.");
											statusMessage = "error";
											return statusMessage;
										}

									} else {
										System.out.println("Failed to configure investigation details.");
										statusMessage = "error";
										return statusMessage;
									}

								} else {
									System.out.println("Failed to configure Vital Signs details.");
									statusMessage = "error";
									return statusMessage;
								}

							} else {
								System.out.println("Failed to configure OT Notes details.");
								statusMessage = "error";
								return statusMessage;
							}
						} else {
							System.out.println("Failed to configure Continuation sheet details.");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to configure Ophtholmology IPD details.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println("Failed to configure IPD Complaint details.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Failed to update IPD Visit details.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while copyin file to temp directory due to:::" + exception.getMessage());
			statusMessage = "error";
			return statusMessage;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addNewAppointment(com
	 * .edhanvantari.form.PatientForm)
	 */
	public String addNewAppointment(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		String username = "";
		String password = "";

		int aptNumber = 0;

		String apptFromTime = patientForm.getAptTimeFrom();

		if (patientForm.getFacilityDashboard() == 2) {

			aptNumber = patientDAOInf.retrieveAppointmentNumber(patientForm.getClinicID(),
					patientForm.getAppointmentDate(), patientForm.getApptSlot());

			apptFromTime = patientForm.getApptSlot();

		} else {
			/*
			 * Retrieve last entered aptNumber for the current date and add 1 to it
			 */
			aptNumber = patientDAOInf.retrieveAppointmentNumber();

			aptNumber = aptNumber + 1;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		String mobileNo = "";

		String emailID = "";

		/*
		 * Checking whether patientID is 0, if 0, add new patient into Patient table
		 * else proceed to add appointment for the particular patientID
		 */
		if (patientForm.getPatientID() == 0) {

			if (patientForm.getFirstName() == null || patientForm.getLastName() == null) {

				statusMessage = "empty";

				System.out.println("Null patient entry found.");

				return statusMessage;

			} else if (patientForm.getFirstName().isEmpty() || patientForm.getLastName().isEmpty()) {

				statusMessage = "empty";

				System.out.println("Empty patient entry found.");

				return statusMessage;

			} else {
				System.out.println("patient FN,LN::" + patientForm.getFirstName() + "," + patientForm.getLastName());
				username = patientForm.getFirstName() + "." + patientForm.getLastName();

				UUID uuid = UUID.randomUUID();
				String randomUUIDString = uuid.toString();
				System.out.println("randomUUID::" + randomUUIDString);

				password = randomUUIDString.split("-")[0];

				System.out.println("username=::" + username);
				System.out.println("password=::" + password);

				boolean refByDoc = configXMLUtil.checkReferredByDoctorExists(patientForm.getRefDoctor(),
						patientForm.getPracticeID());

				String status = "";

				if (!refByDoc) {
					status = patientDAOInf.insertReferredByDoctor(patientForm.getRefDoctor(),
							patientForm.getPracticeID());
					if (status.equalsIgnoreCase("success")) {
						int refDocID = patientDAOInf.retrieveReferredByDoctorID(patientForm.getRefDoctor());
						patientForm.setRefDoctorID(refDocID);

					}
				} else {
					int refDocID = patientDAOInf.retrieveReferredByDoctorID(patientForm.getRefDoctor());
					patientForm.setRefDoctorID(refDocID);
				}

				statusMessage = patientDAOInf.insertPatientDetails(patientForm, patientForm.getPracticeID());

				if (statusMessage.equalsIgnoreCase("success")) {
					int patientID = patientDAOInf.retrievePatientID(patientForm.getFirstName(),
							patientForm.getLastName(), patientForm.getPracticeID());

					/*
					 * retrieving last entered patient ID by firstName and lastName of patient
					 */
					patientForm.setPatientID(clinicDAOInf.retrievePatientIDByFirstAndLastName(
							patientForm.getFirstName(), patientForm.getLastName()));

					/*
					 * Inserting a record with patientID and clinicID along with clinicRegNo into
					 * ClincRegistration table
					 */
					String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(
							patientForm.getClinicSuffix(), patientForm.getClinicID());

					statusMessage = patientDAOInf.insertClinicRegistration(patientForm.getPatientID(),
							patientForm.getClinicID(), cilincRegNo);

					if (statusMessage.equals("success")) {
						System.out.println("Clinic registration details inserted successfully.");

						/*
						 * Inserting patient's identification details into ContactInformation table
						 */
						statusMessage = patientDAOInf.insertIdentification(patientForm, patientForm.getPatientID());

						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * inserting emergency contact information into EmergencyContact table
							 */
							statusMessage = patientDAOInf.insertEmergencyContact(patientForm.getPatientID(),
									patientForm);
							if (statusMessage.equalsIgnoreCase("success")) {

								/*
								 * INserting appointment details into Appointment table
								 */
								statusMessage = patientDAOInf.insertAppointment(patientForm, aptNumber);

								if (statusMessage.equals("success")) {

									patientDAOInf.updateUsernameAndPasswordIntoPatientTableByID(username, password,
											patientID);

									if (patientForm.getCountry() == null || patientForm.getCountry() == "") {

										/*
										 * Check whether appointment udpated flag is on or not, and depending upon that
										 * sending SMS and Email
										 */
										boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

										if (SMSCheck) {

											/*
											 * Retrieving patient's mobile no, if mobile is not null, then sending
											 * patient a welcome message
											 */
											mobileNo = patientDAOInf
													.retrievePatientMobileNoByID(patientForm.getPatientID());

											/*
											 * Sending patient a welcome as well as Appointment scheduled message on
											 * checking whether mobile no is available for that patient or not
											 */
											if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
												System.out.println("Mobile no not found for patient.");
											} else {

												boolean SMSCheck1 = util.verifyCommunicationCheck("smsWelcome");

												if (SMSCheck1) {
													/*
													 * Sending patient a welcome SMS
													 */
													smsSender.sendWelcomeSMS(patientForm.getPatientID(),
															patientForm.getPracticeID(), mobileNo,
															patientForm.getClinicID());

												}

												boolean LoginSMSCheck1 = util
														.verifyCommunicationCheck("smsPatPortalCredentials");

												if (LoginSMSCheck1) {

													/*
													 * Sending patient a login credentials SMS
													 */
													smsSender.sendPatientPortalCredentialsSMS(
															patientForm.getPatientID(), patientForm.getPracticeID(),
															mobileNo, patientForm.getClinicID());

												}

												String apptDate = patientForm.getAppointmentDate();

												if (apptDate == null || apptDate == "") {
													apptDate = "";
												} else {
													try {

														if (apptDate.isEmpty()) {
															apptDate = "";
														} else {
															if (apptDate.substring(2, 3).equals("-")) {
																apptDate = apptDate;
															} else {
																apptDate = dateFormat
																		.format(dateFormat1.parse(apptDate));
															}
														}

													} catch (Exception exception) {
														exception.printStackTrace();
													}
												}

												// Retrieving doctor name from the appointmnet
												String doctorName = clinicDAOInf.retrieveApptDocName(
														patientForm.getPatientID(), apptDate, apptFromTime,
														patientForm.getAptTimeTo(), patientForm.getClinicID());

												/*
												 * Sending appointment scheduled SMS to patient
												 */
												smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
														patientForm.getPracticeID(), patientForm.getClinicID(),
														apptDate, apptFromTime, ActivityStatus.SCHEDULED, doctorName);

											}

										}

									} else if (patientForm.getCountry().isEmpty()) {

										/*
										 * Check whether appointment udpated flag is on or not, and depending upon that
										 * sending SMS and Email
										 */
										boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

										if (SMSCheck) {

											/*
											 * Retrieving patient's mobile no, if mobile is not null, then sending
											 * patient a welcome message
											 */
											mobileNo = patientDAOInf
													.retrievePatientMobileNoByID(patientForm.getPatientID());

											/*
											 * Sending patient a welcome as well as Appointment scheduled message on
											 * checking whether mobile no is available for that patient or not
											 */
											if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
												System.out.println("Mobile no not found for patient.");
											} else {

												boolean SMSCheck1 = util.verifyCommunicationCheck("smsWelcome");

												if (SMSCheck1) {
													/*
													 * Sending patient a welcome SMS
													 */
													smsSender.sendWelcomeSMS(patientForm.getPatientID(),
															patientForm.getPracticeID(), mobileNo,
															patientForm.getClinicID());

												}

												boolean LoginSMSCheck1 = util
														.verifyCommunicationCheck("smsPatPortalCredentials");

												if (LoginSMSCheck1) {

													/*
													 * Sending patient a login credentials SMS
													 */
													smsSender.sendPatientPortalCredentialsSMS(
															patientForm.getPatientID(), patientForm.getPracticeID(),
															mobileNo, patientForm.getClinicID());

												}

												String apptDate = patientForm.getAppointmentDate();

												if (apptDate == null || apptDate == "") {
													apptDate = "";
												} else {
													try {

														if (apptDate.isEmpty()) {
															apptDate = "";
														} else {
															if (apptDate.substring(2, 3).equals("-")) {
																apptDate = apptDate;
															} else {
																apptDate = dateFormat
																		.format(dateFormat1.parse(apptDate));
															}
														}

													} catch (Exception exception) {
														exception.printStackTrace();
													}
												}

												// Retrieving doctor name from the appointmnet
												String doctorName = clinicDAOInf.retrieveApptDocName(
														patientForm.getPatientID(), apptDate, apptFromTime,
														apptFromTime, patientForm.getClinicID());

												/*
												 * Sending appointment scheduled SMS to patient
												 */
												smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
														patientForm.getPracticeID(), patientForm.getClinicID(),
														apptDate, apptFromTime, ActivityStatus.SCHEDULED, doctorName);
											}

										}

									} else if (patientForm.getCountry().equals("India")) {

										/*
										 * Check whether appointment udpated flag is on or not, and depending upon that
										 * sending SMS and Email
										 */
										boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

										if (SMSCheck) {

											/*
											 * Retrieving patient's mobile no, if mobile is not null, then sending
											 * patient a welcome message
											 */
											mobileNo = patientDAOInf
													.retrievePatientMobileNoByID(patientForm.getPatientID());

											/*
											 * Sending patient a welcome as well as Appointment scheduled message on
											 * checking whether mobile no is available for that patient or not
											 */
											if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
												System.out.println("Mobile no not found for patient.");
											} else {

												boolean SMSCheck1 = util.verifyCommunicationCheck("smsWelcome");

												if (SMSCheck1) {
													/*
													 * Sending patient a welcome SMS
													 */
													smsSender.sendWelcomeSMS(patientForm.getPatientID(),
															patientForm.getPracticeID(), mobileNo,
															patientForm.getClinicID());

												}

												boolean LoginSMSCheck1 = util
														.verifyCommunicationCheck("smsPatPortalCredentials");

												if (LoginSMSCheck1) {

													/*
													 * Sending patient a login credentials SMS
													 */
													smsSender.sendPatientPortalCredentialsSMS(
															patientForm.getPatientID(), patientForm.getPracticeID(),
															mobileNo, patientForm.getClinicID());

												}

												String apptDate = patientForm.getAppointmentDate();

												if (apptDate == null || apptDate == "") {
													apptDate = "";
												} else {
													try {

														if (apptDate.isEmpty()) {
															apptDate = "";
														} else {
															if (apptDate.substring(2, 3).equals("-")) {
																apptDate = apptDate;
															} else {
																apptDate = dateFormat
																		.format(dateFormat1.parse(apptDate));
															}
														}

													} catch (Exception exception) {
														exception.printStackTrace();
													}
												}

												// Retrieving doctor name from the appointmnet
												String doctorName = clinicDAOInf.retrieveApptDocName(
														patientForm.getPatientID(), apptDate, apptFromTime,
														apptFromTime, patientForm.getClinicID());

												/*
												 * Sending appointment scheduled SMS to patient
												 */
												smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
														patientForm.getPracticeID(), patientForm.getClinicID(),
														apptDate, apptFromTime, ActivityStatus.SCHEDULED, doctorName);
											}

										}

									} else {
										System.out.println("Patient's country is not India");
									}

									boolean EmailCheck = util.verifyCommunicationCheck("emailApptSchedule");

									if (EmailCheck) {

										/*
										 * Retrieving patient's email ID, if not null, then sending patient a welcome
										 * mail
										 */
										emailID = patientDAOInf.retrievePatientEmailByID(patientForm.getPatientID());

										if (emailID == null || emailID == "" || emailID.isEmpty()) {
											System.out.println("EmailID not found for patient.");
										} else {

											boolean SMSCheck1 = util.verifyCommunicationCheck("emailWelcome");

											if (SMSCheck1) {

												/*
												 * Sending welcome mail to patient
												 */
												emailUtil.sendWelcomeMail(patientForm.getPatientID(),
														patientForm.getPracticeID(), emailID,
														patientForm.getClinicID());

											}

											boolean credEmailCheck1 = util
													.verifyCommunicationCheck("emailPatPortalCredentials");

											if (credEmailCheck1) {

												/*
												 * Sending login credentials to patient
												 */
												emailUtil.sendPatientPortalCredentialsMail(patientForm.getPatientID(),
														patientForm.getPracticeID(), emailID,
														patientForm.getClinicID());

											}

											String apptDate = patientForm.getAppointmentDate();

											if (apptDate == null || apptDate == "") {
												apptDate = "";
											} else {
												try {

													if (apptDate.isEmpty()) {
														apptDate = "";
													} else {
														if (apptDate.substring(2, 3).equals("-")) {
															apptDate = apptDate;
														} else {
															apptDate = dateFormat.format(dateFormat1.parse(apptDate));
														}
													}

												} catch (Exception exception) {
													exception.printStackTrace();
												}
											}

											/*
											 * Sending appointment scheduled Email to patient
											 */
											emailUtil.sendAppointmentEmail(patientForm.getPracticeID(),
													patientForm.getClinicID(), emailID, patientForm.getPatientID(),
													apptDate, apptFromTime, ActivityStatus.SCHEDULED);

										}

									}

									return statusMessage;

								} else {

									System.out.println("Failed to add appointment.");

									statusMessage = "error";

									return statusMessage;
								}

							} else {
								System.out.println(
										"Failed to insert emergenct contact details of patient into EmergencyContact table");

								statusMessage = "error";

								return statusMessage;

							}

						} else {
							System.out.println(
									"Failed to insert identification details of patient into Identification table");

							statusMessage = "error";

							return statusMessage;

						}

					} else {
						System.out
								.println("Failed to insert clinic registration details into ClinicRegistration table.");

						statusMessage = "error";

						return statusMessage;

					}

				} else {

					System.out.println("Failed to add patient details.");

					statusMessage = "error";

					return statusMessage;
				}

			}

		} else {

			/*
			 * INserting appointment details into Appointment table
			 */
			statusMessage = patientDAOInf.insertAppointment(patientForm, aptNumber);

			if (statusMessage.equals("success")) {

				/*
				 * retrieving patient's country by patientID and checking whether country equals
				 * to India, is so, then only sending SMS of appt schedule to patient else do
				 * nothing
				 */
				String country = patientDAOInf.retrievePatientCountryByID(patientForm.getPatientID());

				if (country == "" || country == null) {

					/*
					 * Check whether appointment udpated flag is on or not, and depending upon that
					 * sending SMS and Email
					 */
					boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

					if (SMSCheck) {

						/*
						 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
						 * welcome message
						 */
						mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientForm.getPatientID());

						/*
						 * Sending patient a welcome as well as Appointment scheduled message on
						 * checking whether mobile no is available for that patient or not
						 */
						if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
							System.out.println("Mobile no not found for patient.");
						} else {

							String apptDate = patientForm.getAppointmentDate();

							if (apptDate == null || apptDate == "") {
								apptDate = "";
							} else {
								try {

									if (apptDate.isEmpty()) {
										apptDate = "";
									} else {
										if (apptDate.substring(2, 3).equals("-")) {
											apptDate = apptDate;
										} else {
											apptDate = dateFormat.format(dateFormat1.parse(apptDate));
										}
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}

							// Retrieving doctor name from the appointmnet
							String doctorName = clinicDAOInf.retrieveApptDocName(patientForm.getPatientID(), apptDate,
									apptFromTime, patientForm.getAptTimeTo(), patientForm.getClinicID());

							/*
							 * Sending appointment scheduled SMS to patient
							 */
							smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
									patientForm.getPracticeID(), patientForm.getClinicID(), apptDate, apptFromTime,
									ActivityStatus.SCHEDULED, doctorName);
						}

					}

				} else if (country.isEmpty()) {

					/*
					 * Check whether appointment udpated flag is on or not, and depending upon that
					 * sending SMS and Email
					 */
					boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

					if (SMSCheck) {

						/*
						 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
						 * welcome message
						 */
						mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientForm.getPatientID());

						/*
						 * Sending patient a welcome as well as Appointment scheduled message on
						 * checking whether mobile no is available for that patient or not
						 */
						if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
							System.out.println("Mobile no not found for patient.");
						} else {

							String apptDate = patientForm.getAppointmentDate();

							if (apptDate == null || apptDate == "") {
								apptDate = "";
							} else {
								try {

									if (apptDate.isEmpty()) {
										apptDate = "";
									} else {
										if (apptDate.substring(2, 3).equals("-")) {
											apptDate = apptDate;
										} else {
											apptDate = dateFormat.format(dateFormat1.parse(apptDate));
										}
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}

							// Retrieving doctor name from the appointmnet
							String doctorName = clinicDAOInf.retrieveApptDocName(patientForm.getPatientID(), apptDate,
									apptFromTime, patientForm.getAptTimeTo(), patientForm.getClinicID());

							/*
							 * Sending appointment scheduled SMS to patient
							 */
							smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
									patientForm.getPracticeID(), patientForm.getClinicID(), apptDate, apptFromTime,
									ActivityStatus.SCHEDULED, doctorName);
						}

					}

				} else if (country.equals("India")) {

					/*
					 * Check whether appointment udpated flag is on or not, and depending upon that
					 * sending SMS and Email
					 */
					boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

					if (SMSCheck) {

						/*
						 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
						 * welcome message
						 */
						mobileNo = patientDAOInf.retrievePatientMobileNoByID(patientForm.getPatientID());

						/*
						 * Sending patient a welcome as well as Appointment scheduled message on
						 * checking whether mobile no is available for that patient or not
						 */
						if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
							System.out.println("Mobile no not found for patient.");
						} else {

							String apptDate = patientForm.getAppointmentDate();

							if (apptDate == null || apptDate == "") {
								apptDate = "";
							} else {
								try {

									if (apptDate.isEmpty()) {
										apptDate = "";
									} else {
										if (apptDate.substring(2, 3).equals("-")) {
											apptDate = apptDate;
										} else {
											apptDate = dateFormat.format(dateFormat1.parse(apptDate));
										}
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}

							// Retrieving doctor name from the appointmnet
							String doctorName = clinicDAOInf.retrieveApptDocName(patientForm.getPatientID(), apptDate,
									apptFromTime, patientForm.getAptTimeTo(), patientForm.getClinicID());

							/*
							 * Sending appointment scheduled SMS to patient
							 */
							smsSender.sendAppointmentSMS(patientForm.getPatientID(), mobileNo,
									patientForm.getPracticeID(), patientForm.getClinicID(), apptDate, apptFromTime,
									ActivityStatus.SCHEDULED, doctorName);
						}

					}

				} else {
					System.out.println("Patient's country is not India");
				}

				boolean EmailCheck = util.verifyCommunicationCheck("emailApptSchedule");

				if (EmailCheck) {

					/*
					 * Retrieving patient's email ID, if not null, then sending patient a welcome
					 * mail
					 */
					emailID = patientDAOInf.retrievePatientEmailByID(patientForm.getPatientID());

					if (emailID == null || emailID == "" || emailID.isEmpty()) {
						System.out.println("EmailID not found for patient.");
					} else {

						String apptDate = patientForm.getAppointmentDate();

						if (apptDate == null || apptDate == "" || apptDate.isEmpty()) {
							apptDate = "";
						} else {
							try {
								apptDate = dateFormat.format(dateFormat1.parse(apptDate));
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}

						/*
						 * Sending appointment scheduled Email to patient
						 */
						emailUtil.sendAppointmentEmail(patientForm.getPracticeID(), patientForm.getClinicID(), emailID,
								patientForm.getPatientID(), apptDate, apptFromTime, ActivityStatus.SCHEDULED);
					}

				}

				return statusMessage;

			} else {

				System.out.println("Failed to add appointment.");

				statusMessage = "error";

				return statusMessage;
			}

		}

	}

	public String addLabReport(PatientForm patientForm, String realPath) {
		patientDAOInf = new PatientDAOImpl();

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

		List<String> fileNameList = new ArrayList<String>();

		try {

			/*
			 * Check whether file uploaded by user or not i.e., check whether labReport
			 * contains null or nor, of not then only proceed further else give error
			 */
			if (patientForm.getLabReport() != null) {

				int i = 0;
				/*
				 * Iterating file in order to copy them into directory and also creating
				 * finalFilename for each file
				 */
				for (File file : patientForm.getLabReport()) {

					UUID uuid = UUID.randomUUID();
					String randomUUIDString = uuid.toString();

					// Storing filename into one variable in order to get the
					// extension of file
					String tempFileName = patientForm.getLabReportFileName().get(i);

					System.out.println("File name is ::: " + tempFileName);

					// Checking the last occurrence of '.' in file and according
					// to that getting file extension
					int lastIndex = tempFileName.lastIndexOf(".");
					String extension = tempFileName.substring(lastIndex + 1);
					if (lastIndex == -1 || !extension.matches("\\w+")) {
						System.out.println("File has no extension");
					}

					// Creating new file name to be inserted into LabReport
					// table
					String finalFileName = patientForm.getFirstName() + patientForm.getLastName()
							+ patientForm.getPatientID() + "_" + patientForm.getVisitID() + "report"
							+ randomUUIDString.split("-")[1] + "." + extension;

					System.out.println("Final file name is ::: " + finalFileName);

					/*
					 * adding finalFileName and visitID into a one String and adding that string
					 * into fileNameList in order to insert those records into LabReport table
					 */
					String finalFileNameString = finalFileName + "=" + patientForm.getVisitID();

					System.out.println("Final string to be added into fileNameList :: " + finalFileNameString);

					fileNameList.add(finalFileNameString);
					System.out.println("lab reports file list::" + patientForm.getLabReport());

					// Storing file to S3 RDML INPUT FILE location
					String message = awss3Connect.pushFile(file, finalFileName, bucketName, bucketRegion,
							s3reportFilePath);

					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, finalFileName))
							.getObjectContent();

					i++;
				}

				/*
				 * Inserting fileNameList one by one into LabReport table
				 */
				statusMessage = patientDAOInf.insertLabReport(fileNameList);
				if (statusMessage.equalsIgnoreCase("success")) {
					System.out.println("Succesfully inserted lab report...");
					statusMessage = "success";
					return statusMessage;
				} else {
					System.out.println("Failed to insert lab report into table");
					statusMessage = "error";

					return statusMessage;
				}

			} else {
				System.out.println("File not selected by user");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while copying lab report file into directory due to:::"
					+ exception.getMessage());
			statusMessage = "error";
			return statusMessage;
		}

	}

	public String downloadLabReport(PatientForm patientForm, String realPath) {
		patientDAOInf = new PatientDAOImpl();

		List<File> fileNameList = null;

		String outFileName = null;
		String outFilePath = null;

		try {
			/*
			 * Retrieving lab report file name into list and iterating over the list in
			 * order to get file names from database based on visitID
			 */
			fileNameList = patientDAOInf.retrieveLabReportFileName(patientForm, realPath);
			if (fileNameList != null) {
				// Setting zip file path and name
				outFileName = patientForm.getFirstName() + patientForm.getLastName() + patientForm.getPatientID() + "_"
						+ patientForm.getVisitID() + "report.zip";
				System.out.println("Out file name is ::: " + outFileName);

				outFilePath = realPath + outFileName;
				System.out.println("Out File path is ::: " + outFilePath);

				File outFile = new File(outFilePath);

				// Declaring for zip file
				OutputStream outputStream = new FileOutputStream(outFile);

				ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream));

				/*
				 * Iterating over file fileNamelist and also checking whether fileNameList size
				 * is one or not, if size is 1 then downloading that particular file else adding
				 * all files into zip and downloading zip file.
				 */
				if (fileNameList.size() == 1) {
					// Iterating through fileNameList in order to get file name
					for (File file : fileNameList) {
						File singleOutFile = new File(file.getAbsolutePath());

						/*
						 * Setting input Stream variable of PatientForm in order to get file while
						 * downloading
						 */
						patientForm.setFileInputStream(new FileInputStream(singleOutFile));

						patientForm.setFileName(singleOutFile.getName());

					}
					zos.close();
					statusMessage = "success";
					return statusMessage;
				} else {
					for (File file : fileNameList) {

						System.out.println("Adding " + file.getName());
						zos.putNextEntry(new ZipEntry(file.getName()));

						// Get the file
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(file);

						} catch (FileNotFoundException fnfe) {
							// If the file does not exists, write an error entry
							// instead
							// of
							// file
							// contents
							zos.write(("ERRORld not find file " + file.getName()).getBytes());
							zos.closeEntry();
							System.out.println("Couldfind file " + file.getAbsolutePath());
							continue;
						}

						BufferedInputStream fif = new BufferedInputStream(fis);

						// Write the contents of the file
						int data = 0;
						while ((data = fif.read()) != -1) {
							zos.write(data);
						}
						fif.close();

						zos.closeEntry();
						System.out.println("Finish sending file " + file.getName());
					}

					zos.close();

					/*
					 * Setting input Stream variable of PatientForm in order to get file while
					 * downloading
					 */
					patientForm.setFileInputStream(new FileInputStream(outFile));

					/*
					 * Setting zipped file name in order to get that file name while downloading the
					 * file
					 */
					patientForm.setFileName(outFileName);

					statusMessage = "success";
					return statusMessage;
				}
			} else {
				System.out.println("No file found");
				statusMessage = "input";
				return statusMessage;

			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while downloading lab report file from directory due to:::"
					+ exception.getMessage());
			statusMessage = "error";
			return statusMessage;
		}
	}

	public String addNewSurvey(PatientForm patientForm, int clinicID) {
		patientDAOInf = new PatientDAOImpl();

		/*
		 * Insert survey details into Volunteer table.
		 */
		statusMessage = patientDAOInf.insertPatientSurvey(patientForm, clinicID);
		if (statusMessage.equalsIgnoreCase("success")) {

			statusMessage = "success";
		} else {

			statusMessage = "error";
			System.out.println("Failed to add Survey Details");

		}
		return statusMessage;
	}

	public String addNewGenPhyVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		int lastVisitID = 0;

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Retrieving new visit type id from PVVisitType table based on clinicID and
		 * newVisit flag as 1
		 */
		int newVisitTypeID = patientDAOInf.retrieveNewVisitTypeIDByClinicID(patientForm.getClinicID());

		System.out.println("New visit type ID is ..." + newVisitTypeID);

		/*
		 * Retrieving last visitID for visit Type 'New' to insert it as the value of
		 * newVisitRef if visitType selected by user is 'Follow-up'
		 */
		int visitID = patientDAOInf.retrievVisitIDForNewVisitType(patientForm.getPatientID(), newVisitTypeID);

		int newVisitRef = 0;

		if (patientDAOInf.verifyFollowUpVisitType(patientForm.getVisitTypeID())) {
			newVisitRef = visitID;
		}

		patientForm.setDiagnosis(patientForm.getCancerType());

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertPatientVisit(patientForm, visitNumber, newVisitRef, "");

		if (statusMessage.equalsIgnoreCase("success")) {

			// Check if the value of cancerType is of type json if not then only proceed for
			// the verifying whether diagnosis exists or not
			if (!new JSONValidator().isValid(patientForm.getCancerType())) {
				boolean check = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

				if (!check) {
					patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
				}
			}

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getVisitTypeID(), patientForm.getClinicID());

			patientForm.setVisitID(lastVisitID);

			/*
			 * Insert Vital Signs Details For Visit into VitalSigns table.
			 */
			statusMessage = patientDAOInf.insertVitalSignsDetailsForVisit(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Insert Symptom Check Details For Visit into SymptomCheck table.
				 */

				if (patientForm.getVisitID() == 0) {

					System.out.println("No visit added.");

					statusMessage = "noVisit";

					return statusMessage;
				}

			} else {
				statusMessage = "error";
			}

			/*
			 * Insert Personal History Details into Personalhistory table.
			 */

			statusMessage = patientDAOInf.insertPersonalHistory(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {
				System.out.println("Personal History added Successfully");

			} else {

				statusMessage = "error";
				System.out.println("Failed to add Personal History");
			}

			/*
			 * check whether complaint rows added or not and depending upon that proceeding
			 * further to insert records into presentcomplaints table
			 */
			if (patientForm.getNewSymptom() == null) {
				System.out.println("No complaint added into Present Complaint");

				statusMessage = "success";

			} else {
				System.out.println("complaint list length::" + patientForm.getNewSymptom().length);
				for (int i = 0; i < patientForm.getNewSymptom().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertComplaints(patientForm.getNewSymptom()[i], duration,
							patientForm.getPnewComments()[i], patientForm.getVisitID());
				}

			}

			/*
			 * check whether medical history rows added or not and depending upon that
			 * proceeding further to insert records into medicalhistory table
			 */
			if (patientForm.getNewDiagnosis() == null) {
				System.out.println("No history added into medical history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDiagnosis().length; i++) {

					statusMessage = patientDAOInf.insertMedicalHistory(patientForm.getNewDiagnosis()[i],
							patientForm.getNewDescription()[i], patientForm.getPatientID());
				}

			}

			/*
			 * check whether current medication rows added or not and depending upon that
			 * proceeding further to insert records into prescriptionhistory table
			 */
			if (patientForm.getNewDrugname() == null) {
				System.out.println("No medication details added into prescription history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDrugname().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertCurrentMedication(patientForm.getNewDrugname()[i], duration,
							patientForm.getCnewComments()[i], patientForm.getPatientID());
				}
			}

			System.out.println("Last entered visit ID is : " + lastVisitID);

			return statusMessage;

		} else {

			statusMessage = "error";
			return statusMessage;
		}

	}

	public String editSurvey(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		statusMessage = patientDAOInf.updateSurvey(patientForm);

		if (statusMessage.equalsIgnoreCase("success")) {
			System.out.println("Survey updated successfully");

		} else {
			statusMessage = "error";
		}
		return statusMessage;

	}

	public String editGenPhyVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		/*
		 * Insert personal history into personalhistory table.
		 */
		statusMessage = patientDAOInf.updatePersonalHistory(patientForm);

		if (statusMessage.equalsIgnoreCase("success")) {
			System.out.println("Personal History updated successfully");

		} else {
			statusMessage = "error";
		}

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateVisit(patientForm);

		if (statusMessage.equalsIgnoreCase("success")) {

			boolean check = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

			if (!check) {
				patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
			}

			/*
			 * Updating Vital Signs Details For Visit into VitalSigns table.
			 */
			statusMessage = patientDAOInf.updateVitalSignsDetailsForVisit(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {
				System.out.println("Vital signs details updated successfully");
			} else {
				statusMessage = "error";
			}

			/*
			 * check whether complaint rows added or not and depending upon that proceeding
			 * further to insert records into presentcomplaints table
			 */
			if (patientForm.getNewSymptom() == null) {
				System.out.println("No complaint added into Present Complaint");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewSymptom().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertComplaints(patientForm.getNewSymptom()[i], duration,
							patientForm.getPnewComments()[i], patientForm.getVisitID());
				}

			}

			/*
			 * check whether medical history rows added or not and depending upon that
			 * proceeding further to insert records into medicalhistory table
			 */
			if (patientForm.getNewDiagnosis() == null) {
				System.out.println("No history added into medical history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDiagnosis().length; i++) {

					statusMessage = patientDAOInf.insertMedicalHistory(patientForm.getNewDiagnosis()[i],
							patientForm.getNewDescription()[i], patientForm.getPatientID());
				}

			}

			/*
			 * check whether current medication rows added or not and depending upon that
			 * proceeding further to insert records into prescriptionhistory table
			 */
			if (patientForm.getNewDrugname() == null) {
				System.out.println("No medication details added into prescription history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDrugname().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}
					statusMessage = patientDAOInf.insertCurrentMedication(patientForm.getNewDrugname()[i], duration,
							patientForm.getCnewComments()[i], patientForm.getPatientID());
				}
			}

			return statusMessage;

		} else {

			statusMessage = "error";
			return statusMessage;

		}

	}

	public int addNewGenPhyVisit1(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		int lastVisitID = 0;

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Retrieving last visitID for visit Type 'New' to insert it as the value of
		 * newVisitRef if visitType selected by user is 'Follow-up'
		 */
		int visitID = patientDAOInf.retrievVisitIDForNewVisitType(patientForm.getPatientID(),
				patientForm.getVisitType());

		int newVisitRef = 0;

		if (patientForm.getVisitType().equals("Follow-up")) {
			newVisitRef = visitID;
		}

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertPatientVisit(patientForm, visitNumber, newVisitRef, "");

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getVisitTypeID(), patientForm.getClinicID());

			System.out.println("Last entered visit ID is : " + lastVisitID);

			return lastVisitID;

		} else {

			lastVisitID = 0;
			return lastVisitID;

		}

	}

	public List<PatientForm> addNewOPDVisit(PatientForm patientForm, String visitType) {

		patientDAOInf = new PatientDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		List<PatientForm> list = new ArrayList<PatientForm>();

		int lastVisitID = 0;

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Retrieving new visit type id from PVVisitType table based on clinicID and
		 * newVisit flag as 1
		 */
		int newVisitTypeID = patientDAOInf.retrieveNewVisitTypeIDByClinicID(patientForm.getClinicID());

		/*
		 * Retrieving last visitID for visit Type 'New' to insert it as the value of
		 * newVisitRef if visitType selected by user is 'Follow-up'
		 */
		int visitID = patientDAOInf.retrievVisitIDForNewVisitType(patientForm.getPatientID(), newVisitTypeID);

		int newVisitRef = 0;

		if (patientDAOInf.verifyFollowUpVisitType(patientForm.getVisitTypeID())) {
			newVisitRef = visitID;
		}

		int nextVisitDays = patientDAOInf.retrieveNextVisitDays(patientForm.getVisitTypeID());

		System.out.println("nextVisitDays: " + nextVisitDays + "--" + patientForm.getVisitDate());

		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c.setTime(dateFormat1.parse(patientForm.getVisitDate()));

			System.out.println("Date before Addition: " + c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, nextVisitDays);
		// Date after adding the days to the given date
		String nextVisitDate = dateFormat.format(c.getTime());
		// Displaying the new Date after addition of Days
		System.out.println("Date after Addition: " + nextVisitDate);

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertPatientVisit(patientForm, visitNumber, newVisitRef, nextVisitDate);

		if (statusMessage.equalsIgnoreCase("success")) {

			boolean check = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

			if (!check) {
				patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
			}

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getVisitTypeID(), patientForm.getClinicID());

			System.out.println("Last entered visit ID is : " + lastVisitID);

			/*
			 * Setting above retrieved visitID into patientForm's visitID variable
			 */
			patientForm.setVisitID(lastVisitID);

			/*
			 * Inserting OPD details into OphthaolmologyOPD table
			 */
			statusMessage = patientDAOInf.insertOPDVisit(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Retrieving last entered visit and OPD details
				 */
				list = patientDAOInf.retrieveLastEnteredVisitDetail(patientForm.getPatientID(),
						patientForm.getLastVisitID(), patientForm.getClinicID(), visitType);

				return list;

			} else {

				System.out.println("Failed to add OPD details into OphthalmologyOPD table.");

				return list;

			}

		} else {

			System.out.println("falied to add visit details into Visit table.");

			return list;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#updateOptician(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String updateOptician(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		// Check if opticianID exists, if not then retrieve it from Optometry table
		// based on visitID

		if (patientForm.getOpticinID() == 0) {
			/*
			 * Retrieving last entered optician ID from visitID
			 */
			int opticianID = patientDAOInf.retrieveOpticianID(patientForm.getVisitID());

			patientForm.setOpticinID(opticianID);
		}

		/*
		 * Updating Optician details into Optician table
		 */
		statusMessage = patientDAOInf.updateOpticianDetails(patientForm);
		if (statusMessage.equalsIgnoreCase("success")) {

			// Check if data existing into table CycloplegicRefraction or not if not then
			// insert new record else update existing one
			boolean cycloplegicRefractionCheck = patientDAOInf.verifyVisitExists("CycloplegicRefraction",
					patientForm.getVisitID());

			if (cycloplegicRefractionCheck) {

				/*
				 * Updating Cycloplegic Refraction details into CycloplegicRefraction table
				 */
				String status = patientDAOInf.updateCycloplegicRefraction(patientForm);

				if (status.equals("success")) {
					System.out.println("Cycloplegic Refraction details updated successfully");
				} else {
					System.out.println("Failed to update Cycloplegic Refraction details.");
				}

			} else {

				/*
				 * Inserting Cycloplegic Refraction details into CycloplegicRefraction table
				 */
				String status = patientDAOInf.insertCycloplegicRefraction(patientForm);

				if (status.equals("success")) {
					System.out.println("Cycloplegic Refraction details added successfully");
				} else {
					System.out.println("Failed to add Cycloplegic Refraction details.");
				}

			}

			/*
			 * Updating optician Old glasses detials into OldGlases table
			 */
			statusMessage = patientDAOInf.updateOpticianOldGlassDetails(patientForm);
			if (statusMessage.equalsIgnoreCase("success")) {
				/*
				 * updating eyewear details into Eyewear table
				 */

				boolean EyewearCheck = patientDAOInf.checkEyewearDetails(patientForm.getOpticinID());

				if (EyewearCheck) {
					statusMessage = patientDAOInf.updateEyewearDetails(patientForm);
				} else {
					statusMessage = patientDAOInf.insertEyewearDetails(patientForm);
					if (patientForm.getTint() == "" || patientForm.getTint() == null) {
						return statusMessage;
					} else if (patientForm.getTint().isEmpty()) {
						return statusMessage;
					}
				}

				if (statusMessage.equalsIgnoreCase("success")) {
					return statusMessage;
				} else {
					System.out.println("Failed to update Eyewear details");
					statusMessage = "error";
					return statusMessage;
				}
			} else {
				System.out.println("Failed to udpate Optician Old glasses details");
				statusMessage = "error";
				return statusMessage;
			}
		} else {
			System.out.println("Failed to udpate Optician details");
			statusMessage = "error";
			return statusMessage;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addPractice(com.
	 * edhanvantari.form.ClinicForm, java.lang.String)
	 */
	public String addPractice(ClinicForm clinicForm, String realPath, String url) throws IOException {
		clinicDAOInf = new ClinicDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3LogoFilePath = xmlUtil.getS3RDMLFilePath();

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

		System.out.println("Real file path is ..." + realPath);

		/*
		 * Inserting practice details into Practice table
		 */
		statusMessage = clinicDAOInf.insertPractice(clinicForm.getPracticeName(), clinicForm.getPracticeSuffix(),
				clinicForm.getConsentDocuments(), clinicForm.getSessionTimeout(), clinicForm.getInvalidAttempts(),
				clinicForm.getBucketName(), clinicForm.getFacilityDashboard(),
				clinicForm.getThirdPartyAPIIntegration());
		if (statusMessage.equalsIgnoreCase("success")) {
			/*
			 * Retrieving last entered practice id based on practiceName
			 */
			int lastEnteredPracticeID = clinicDAOInf.retrieveLastEneteredPracticeID(clinicForm.getPracticeName());

			System.out.println("Last entered practiceID is :: " + lastEnteredPracticeID);
			String qeuryParam = "id=" + String.valueOf(lastEnteredPracticeID);
			Map<String, String> requestParams = new HashMap<>();
			requestParams.put("id", qeuryParam);

			String encodeValue = URLEncoder.encode(requestParams.get("id"), StandardCharsets.UTF_8.toString());
			String practiceURL = url + "/PatientIntake?" + encodeValue;

			clinicDAOInf.updatePracticeURL(practiceURL, lastEnteredPracticeID);

			// getting s3 bucket name
			String bucketName = util.getS3BucketNameForPractice(lastEnteredPracticeID);

			if (lastEnteredPracticeID != 0) {

				/*
				 * Inserting communication details into Communication table
				 */
				statusMessage = clinicDAOInf.insertCommunicationDetails(clinicForm, lastEnteredPracticeID);
				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Inserting Plan details into PlanDetails table
					 */

					statusMessage = clinicDAOInf.insertPlanDetails(clinicForm.getNoOfVisits(),
							clinicForm.getDateStart(), clinicForm.getDateEnd(), lastEnteredPracticeID);
					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * checking whether clinic added, if not return success else proceed to iterate
						 * and inserting clinic details into Clinic table
						 */
						if (clinicForm.getPracticeClinicName() == null) {
							System.out.println("No clinic is added");

							statusMessage = "success";
						} else {

							/*
							 * Iterating over clinic details in order to insert clinics one by one for the
							 * same practiceID
							 */

							System.out.println("ARRAYSIZE..." + clinicForm.getDummySettingTextArr().length);
							for (int i = 0; i < clinicForm.getPracticeClinicName().length; i++) {

								if (clinicForm.getPracticeClinicName()[i] == null
										|| clinicForm.getPracticeClinicName()[i].equals(" ")
										|| clinicForm.getPracticeClinicName()[i].isEmpty()) {
									System.out.println("BLANK ROW added..");
									continue;

								} else {

									String[] dummySettingTextArr = clinicForm.getDummySettingTextArr()[i].split("\\$");
									System.out.println("ARRAYSIZE11..." + dummySettingTextArr.length);

									String clinicStart = dummySettingTextArr[0];
									String clinicEnd = dummySettingTextArr[1];
									String clinicBreakStart1 = dummySettingTextArr[2];
									String clinicBreakEnd1 = dummySettingTextArr[3];
									String clinicBreakStart2 = dummySettingTextArr[4];
									String clinicBreakEnd2 = dummySettingTextArr[5];
									String workdays = dummySettingTextArr[6];
									String clinicPageSize = dummySettingTextArr[7];
									String clinicPatientForm = dummySettingTextArr[8];

									// Inserting Clinic details into Clinic table
									statusMessage = clinicDAOInf.insertClinic(clinicForm.getPracticeClinicName()[i],
											lastEnteredPracticeID, clinicForm.getPracticeClinicSuffix()[i],
											clinicPageSize, clinicPatientForm, clinicForm.getClinicPhoneNoArray()[i]);

									if (statusMessage.equals("success")) {
										System.out.println("Clinic details inserted successfully into Clinic table.");
									} else {
										System.out.println("Failed to insert clinic details into Clinic table.");
									}

									// retrieving last entered clinicID for particular practice ID
									int clinicID = clinicDAOInf.retrieveLastEnteredClinicID(lastEnteredPracticeID);

									// Inserting calendar details into Calendar table
									statusMessage = clinicDAOInf.insertCalendarDetails(clinicStart, clinicEnd,
											clinicBreakStart1, clinicBreakEnd1, clinicBreakStart2, clinicBreakEnd2,
											clinicID, workdays);

									if (statusMessage.equalsIgnoreCase("success")) {

										statusMessage = "success";

									} else {
										System.out.println("Failed to insert calendar details into Calendar tables.");
										statusMessage = "error";

									}
								}
							}

							if (statusMessage.equalsIgnoreCase("success")) {
								System.out.println(
										"Successfully inserted Clinic with calendar details into Clinic and Calendar tables.");
								statusMessage = "success";

							} else {
								System.out.println(
										"Failed to insert Clinic with calendar details into Clinic and Calendar tables.");
								statusMessage = "error";

							}

						}

						if (clinicForm.getMdNameArr() == null) {
							System.out.println("No MD details were added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < clinicForm.getMdNameArr().length; i++) {

								File file = clinicForm.getMdSignatureImage().get(i);

								UUID uuid = UUID.randomUUID();
								String randomUUIDString = uuid.toString();

								// Storing filename into one variable in order to get the
								// extension of file
								String tempFileName = clinicForm.getMdSignatureImageFileName().get(i);

								System.out.println("File name is ::: " + tempFileName);

								// Checking the last occurrence of '.' in file and according
								// to that getting file extension
								int lastIndex = tempFileName.lastIndexOf(".");
								String extension = tempFileName.substring(lastIndex + 1);

								if (lastIndex == -1 || !extension.matches("\\w+")) {
									System.out.println("File has no extension");
								}

								// table
								String finalFileName = clinicForm.getMdNameArr() + clinicForm.getMdQualificationArr()[i]
										+ randomUUIDString.split("-")[1] + "." + extension;

								System.out.println("Final file name is ::: " + finalFileName);

								// Storing file to S3 RDML INPUT FILE location
								String message = awss3Connect.pushFile(file, finalFileName, bucketName, bucketRegion,
										s3LogoFilePath);

								S3ObjectInputStream s3ObjectInputStream = s3
										.getObject(
												new GetObjectRequest(bucketName + "/" + s3LogoFilePath, finalFileName))
										.getObjectContent();

								statusMessage = clinicDAOInf.insertMDDetails(clinicForm.getMdNameArr()[i],
										clinicForm.getMdQualificationArr()[i], finalFileName,
										clinicForm.getMdStartDateArr()[i], clinicForm.getMdEndDateArr()[i],
										lastEnteredPracticeID);
							}

							if (statusMessage.equalsIgnoreCase("success")) {
								System.out.println("Successfully inserted MD details into PracticeMDDetails table.");
								statusMessage = "success";

							} else {
								System.out.println("Failed to insert MD details into PracticeMDDetails table.");
								statusMessage = "error";
							}
						}
					} else {
						System.out.println("Failed to insert plan details into plandetails table.");
						statusMessage = "error";
					}
				} else {
					System.out.println("Failed to insert communication details into Communication table");
					statusMessage = "error";
				}

			} else {
				System.out.println("Failed to retrieve practice ID");
				statusMessage = "error";
			}
		} else {
			System.out.println("Failed to insert practice details.");
			statusMessage = "error";
		}

		return statusMessage;

	}

	public String editClinicUploadImage(ClinicForm clinicForm) {
		System.out.println("inside clinic upload serviceimpl");
		clinicDAOInf = new ClinicDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = util.getS3BucketNameForPractice(clinicForm.getPracticeID());

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		System.out.println("bucketRegion: " + bucketRegion + "--" + bucketName + "--" + s3rdmlFilePath);

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Retrieving report file path from edhanvantari.xml in order to store all
		 * reports file into it
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");

		System.out.println("realpath is::" + realPath);

		try {

			String clinicLogoFileName = clinicForm.getLogoFileFileName();
			System.out.println("CLINIC LOGO :::" + clinicForm.getLogoFileDBName());

			try {

				// File logoFileEx = new File(clinicForm.getLogoFile());
				if (clinicForm.getLogoFile() == null) {
					clinicForm.setLogoFileDBName(clinicForm.getLogoFileDBName());
				} else {

					// if (clinicForm.getLogoFileDBName() != null) {

					String[] array1 = clinicForm.getLogoFileFileName().split("\\.");
					String fileExtension1 = array1[1];
					// Setting content type according to file extension
					if (fileExtension1.equalsIgnoreCase("jpg")) {
						clinicForm.setLogoFileContentType("image/jpg");
					} else if (fileExtension1.equalsIgnoreCase("jpeg")) {
						clinicForm.setLogoFileContentType("image/jpeg");
					} else if (fileExtension1.equalsIgnoreCase("png")) {
						clinicForm.setLogoFileContentType("image/png");
					} else if (fileExtension1.equalsIgnoreCase("bmp")) {
						clinicForm.setLogoFileContentType("image/bmp");
					}

					String fileNameToBeStored1 = "Clinic_Logo_" + clinicForm.getClinicID() + "." + fileExtension1;
					System.out.println("Original Logo File name is ::::" + clinicForm.getLogoFile());
					System.out.println("Logo File Name to be stored into DB is ::: " + fileNameToBeStored1);

					/**
					 * Setting file name with S3 bucket path to be inserted into AppUser table into
					 * SignatureDBName
					 */
					clinicForm.setLogoFileDBName(fileNameToBeStored1);
					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(clinicForm.getLogoFile(), fileNameToBeStored1, bucketName,
							bucketRegion, s3rdmlFilePath);
				}

				/*
				 * Checking whether letterHead file path entered by user exists or not, if not
				 * give error msg else proceed further
				 */

				if (clinicForm.getLetterHeadFile() == null) {
					clinicForm.setLetterHeadFileDBName(clinicForm.getLetterHeadFileDBName());
				} else {

					String[] array1 = clinicForm.getLetterHeadFileFileName().split("\\.");
					String fileExtension1 = array1[1];
					// Setting content type according to file extension
					if (fileExtension1.equalsIgnoreCase("jpg")) {
						clinicForm.setLetterHeadFileContentType("image/jpg");
					} else if (fileExtension1.equalsIgnoreCase("jpeg")) {
						clinicForm.setLetterHeadFileContentType("image/jpeg");
					} else if (fileExtension1.equalsIgnoreCase("png")) {
						clinicForm.setLetterHeadFileContentType("image/png");
					} else if (fileExtension1.equalsIgnoreCase("bmp")) {
						clinicForm.setLetterHeadFileContentType("image/bmp");
					}

					String fileNameToBeStored1 = "Clinic_LetterHead_" + clinicForm.getClinicID() + "." + fileExtension1;
					System.out.println("Original Logo File name is ::::" + clinicForm.getLetterHeadFile());
					System.out.println("Logo File Name to be stored into DB is ::: " + fileNameToBeStored1);

					clinicForm.setLetterHeadFileDBName(fileNameToBeStored1);
					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(clinicForm.getLetterHeadFile(), fileNameToBeStored1,
							bucketName, bucketRegion, s3rdmlFilePath);

				}

			} catch (Exception exception) {
				exception.printStackTrace();
				System.out.println("Failed to copy image file into project directory");
			}

			// logo, letterHeadImage, details into Clinic table based on clinicID
			statusMessage = clinicDAOInf.udpateClinicDetails(clinicForm.getClinicID(), clinicForm.getLogoFileDBName(),
					clinicForm.getLetterHeadFileDBName());

			if (statusMessage.equals("success")) {

				System.out.println("Image details udpated successfully.");
				statusMessage = "success";

			} else {
				System.out.println("Failed to update Image details.");
				statusMessage = "error";

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
		}
		return statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#editPractice(com.
	 * edhanvantari.form.ClinicForm, java.lang.String)
	 */
	public String editPractice(ClinicForm clinicForm, String realPath, String url) throws UnsupportedEncodingException {
		clinicDAOInf = new ClinicDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3LogoFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = util.getS3BucketNameForPractice(clinicForm.getPracticeID());

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
		 * Updating practice details from Practice table
		 */

		statusMessage = clinicDAOInf.updatePractice(clinicForm.getPracticeName(), clinicForm.getPracticeID(),
				clinicForm.getPracticeSuffix(), clinicForm.getConsentDocuments(), clinicForm.getSessionTimeout(),
				clinicForm.getInvalidAttempts(), clinicForm.getBucketName(), clinicForm.getFacilityDashboard(),
				clinicForm.getThirdPartyAPIIntegration());
		if (statusMessage.equalsIgnoreCase("success")) {

			// Check if the practiceURL exists, is not then create new one and update it
			// into practice table for the corresponding practiceID
			if (!clinicDAOInf.verifyPracticeURLExists(clinicForm.getPracticeID())) {
				// creating practice URL and updating it into practice table

				String qeuryParam = "id=" + String.valueOf(clinicForm.getPracticeID());
				Map<String, String> requestParams = new HashMap<>();
				requestParams.put("id", qeuryParam);

				String encodeValue = URLEncoder.encode(requestParams.get("id"), StandardCharsets.UTF_8.toString());
				String practiceURL = url + "/PatientIntake?" + encodeValue;

				clinicDAOInf.updatePracticeURL(practiceURL, clinicForm.getPracticeID());
			}

			// Updating the clinic phone no if found
			for (int ci = 0; ci < clinicForm.getEditClinicPhoneNoArray().length; ci++) {
				int clinicID = Integer.parseInt(clinicForm.getClinicIDArr()[ci]);
				String phone = clinicForm.getEditClinicPhoneNoArray()[ci];

				if (phone == null || phone == "") {
					continue;
				} else if (phone.isEmpty()) {
					continue;
				} else {
					clinicDAOInf.updateClinicPhone(clinicID, phone);
				}
			}

			/*
			 * check whether data exists into Communication table corresponding to
			 * patientID, if exists, then udpate the details else insert new details with
			 * current patientID
			 */
			System.out.println("practID AT COmmu::" + clinicForm.getPracticeID());
			boolean communicationCheck = clinicDAOInf.verifyDataExists(clinicForm.getPracticeID(), "Communication");

			if (communicationCheck) {
				/*
				 * Updating communication details into Communication table
				 */
				statusMessage = clinicDAOInf.updateCommunicationDetails(clinicForm);
			} else {
				/*
				 * Inserting communication details into Communication table
				 */
				statusMessage = clinicDAOInf.insertCommunicationDetails(clinicForm, clinicForm.getPracticeID());
			}

			if (statusMessage.equalsIgnoreCase("success")) {

				boolean planIDCheck = clinicDAOInf.verifyPlanIDByPracticeID(clinicForm.getPracticeID());

				/*
				 * Inserting Plan details into PlanDetails table
				 */
				if (planIDCheck) {
					System.out.println("inside update plan");
					statusMessage = "success";
				} else {
					System.out.println("inside insert plan");
					if (clinicForm.getDateStart().isEmpty()) {
						System.out.println("Please add plan details.");

						statusMessage = "input.";
						// return statusMessage;
					} else {
						statusMessage = clinicDAOInf.insertPlanDetails(clinicForm.getNoOfVisits(),
								clinicForm.getDateStart(), clinicForm.getDateEnd(), clinicForm.getPracticeID());
					}

					if (statusMessage.equals("success")) {
						System.out.println("Plan details inserted successfully into PlanDetails table.");
					} else {
						System.out.println("Failed to update plan details into PlanDetails table.");
					}
				}
				/*
				 * checking whether clinic added, if not return success else proceed to iterate
				 * and inserting clinic details into Clinic table
				 */
				System.out.println("clinic name INSERT::" + clinicForm.getPracticeClinicName());
				if (clinicForm.getPracticeClinicName() == null) {
					System.out.println("No clinic is added....INSERT");

					statusMessage = "input";
					// return statusMessage;
				} else {
					System.out.println("Inside INSERT..");
					/*
					 * Iterating over clinic details in order to insert clinics one by one for the
					 * same practiceID
					 */

					System.out.println("ARRAYSIZE..." + clinicForm.getDummySettingTextArr().length);
					for (int i = 0; i < clinicForm.getPracticeClinicName().length; i++) {

						if (clinicForm.getPracticeClinicName()[i] == null
								|| clinicForm.getPracticeClinicName()[i].equals(" ")
								|| clinicForm.getPracticeClinicName()[i].isEmpty()) {
							System.out.println("BLANK ROW added..");
							continue;

						} else {

							String[] dummySettingTextArr = clinicForm.getDummySettingTextArr()[i].split("\\$");
							System.out.println("ARRAYSIZE11..." + dummySettingTextArr.length);

							String clinicStart = dummySettingTextArr[0];
							String clinicEnd = dummySettingTextArr[1];
							String clinicBreakStart1 = dummySettingTextArr[2];
							String clinicBreakEnd1 = dummySettingTextArr[3];
							String clinicBreakStart2 = dummySettingTextArr[4];
							String clinicBreakEnd2 = dummySettingTextArr[5];
							String workdays = dummySettingTextArr[6];
							String clinicPageSize = dummySettingTextArr[7];
							String clinicPatientForm = dummySettingTextArr[8];

							// Inserting Clinic details into Clinic table
							statusMessage = clinicDAOInf.insertClinic(clinicForm.getPracticeClinicName()[i],
									clinicForm.getPracticeID(), clinicForm.getPracticeClinicSuffix()[i], clinicPageSize,
									clinicPatientForm, clinicForm.getClinicPhoneNoArray()[i]);

							if (statusMessage.equals("success")) {
								System.out.println(
										"Clinic logo & letteraHead file path updated successfully into Clinic table.");
							} else {
								System.out.println(
										"Failed to udpate clinic logo & letteraHead file name into Clinic table.");
							}

							int clinicID = clinicDAOInf.retrieveLastEnteredClinicID(clinicForm.getPracticeID());
							// Inserting calendar details into Calendar table
							statusMessage = clinicDAOInf.insertCalendarDetails(clinicStart, clinicEnd,
									clinicBreakStart1, clinicBreakEnd1, clinicBreakStart2, clinicBreakEnd2, clinicID,
									workdays);

							if (statusMessage.equalsIgnoreCase("success")) {
								System.out.println("Failed to insert calendar details into Calendar tables.");
								statusMessage = "success";

							} else {
								System.out.println("Failed to insert calendar details into Calendar tables.");
								statusMessage = "error";

							}
						}
					}

				}

				if (clinicForm.getMdNameArr() == null) {
					System.out.println("No MD details were added.");

					statusMessage = "input";
					// return statusMessage;
				} else {

					for (int i = 0; i < clinicForm.getMdNameArr().length; i++) {

						System.out.println("inside image for....." + clinicForm.getMdSignatureImage().get(i));

						File file = clinicForm.getMdSignatureImage().get(i);

						UUID uuid = UUID.randomUUID();
						String randomUUIDString = uuid.toString();

						// Storing filename into one variable in order to get the
						// extension of file
						String tempFileName = clinicForm.getMdSignatureImageFileName().get(i);

						System.out.println("File name is ::: " + tempFileName);

						// Checking the last occurrence of '.' in file and according
						// to that getting file extension
						int lastIndex = tempFileName.lastIndexOf(".");
						String extension = tempFileName.substring(lastIndex + 1);

						if (lastIndex == -1 || !extension.matches("\\w+")) {
							System.out.println("File has no extension");
						}

						// table
						String finalFileName = clinicForm.getMdNameArr()[i].replaceAll(" ", "_")
								+ randomUUIDString.split("-")[1] + "." + extension;

						System.out.println("Final file name is ::: " + finalFileName);

						System.out.println("bucket details ::: " + file + "-" + finalFileName + "-" + bucketName + "-"
								+ bucketRegion + "-" + s3LogoFilePath);

						// Storing file to S3 RDML INPUT FILE location
						String statusMessage = awss3Connect.pushFile(file, finalFileName, bucketName, bucketRegion,
								s3LogoFilePath);

						statusMessage = clinicDAOInf.insertMDDetails(clinicForm.getMdNameArr()[i],
								clinicForm.getMdQualificationArr()[i], finalFileName, clinicForm.getMdStartDateArr()[i],
								clinicForm.getMdEndDateArr()[i], clinicForm.getPracticeID());
					}

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted MD details into PracticeMDDetails table.");
						statusMessage = "error";
					} else {
						System.out.println("Failed to insert MD details into PracticeMDDetails table.");
						statusMessage = "error";
					}
				}

				if (clinicForm.getMdDetailsIDEdit() == null) {
					System.out.println("No MD details were added.");

					statusMessage = "success";
					// return statusMessage;
				} else {
					for (int i = 0; i < clinicForm.getMdDetailsIDEdit().length; i++) {
						statusMessage = clinicDAOInf.updateEndDateMDDetails(clinicForm.getPracticeMDEndDateEdit()[i],
								clinicForm.getMdDetailsIDEdit()[i], clinicForm.getPracticeID());
					}
				}

			} else {
				System.out.println("Failed to udpate communication details into Communication table");
				statusMessage = "error";

			}

		} else {
			System.out.println("Failed to udpate practice details.");
			statusMessage = "error";

		}
		return statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#deleteVisitType(int)
	 */
	public String deleteVisitType(int visitTypeID) {

		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Checking whether visit is available for the visit typeID, if available, do
		 * not delete visit type else delete visit type
		 */
		boolean check = clinicDAOInf.verifyVisitExistsForVisitTypeID(visitTypeID);

		if (check) {

			System.out.println("Visit exists for visitTypeID " + visitTypeID + ". Can't delete visit type.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Deleting visit type detials based on visitTypeID
			 */
			statusMessage = clinicDAOInf.deleteVisitTypeByVisitTypeID(visitTypeID);

			if (statusMessage.equals("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to delete visit type details based on visitTypeID from VisitType table.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#addAppointment(com.
	 * edhanvantari.form.ClinicForm, int)
	 */
	public JSONObject addAppointment(ClinicForm clinicForm, int clinicID) {

		clinicDAOInf = new ClinicDAOImpl();

		patientDAOInf = new PatientDAOImpl();

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		patientDAOInf = new PatientDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		PatientForm form = new PatientForm();

		String mobileNo = "";

		String emailID = "";

		String username = "";
		String password = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Checking whether patientID is 0, if 0, add new patient into Patient table
		 * else proceed to add appointment for the particular patientID
		 */
		if (clinicForm.getPatientID() == 0) {
			System.out.println("inside pid = 0");
			System.out.println("patient FN,LN::" + clinicForm.getFirstName() + "," + clinicForm.getLastName());
			username = clinicForm.getFirstName() + "." + clinicForm.getLastName();

			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			System.out.println("randomUUID::" + randomUUIDString);

			password = randomUUIDString.split("-")[0];

			System.out.println("username=::" + username);
			System.out.println("password=::" + password);

			clinicForm.setRegNo(patientDAOInf.retireveMedicalRegistrationNo(clinicForm.getPracticeSuffix()));

			statusMessage = clinicDAOInf.insertPatientDetails(clinicForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * retrieving last entered patient ID by firstName and lastName of patient
				 */
				clinicForm.setPatientID(clinicDAOInf.retrievePatientIDByFirstAndLastName(clinicForm.getFirstName(),
						clinicForm.getLastName()));

				patientDAOInf.updateUsernameAndPasswordIntoPatientTableByID(username, password,
						clinicForm.getPatientID());
				/*
				 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
				 * welcome message
				 */
				mobileNo = patientDAOInf.retrievePatientMobileNoByID(clinicForm.getPatientID());

				/*
				 * Retrieving last added appointment number for today and adding 1 to it in
				 * order to get new appointment number for current date
				 */

				int apptNo = clinicDAOInf.retrieveAppointmentNumber(clinicForm.getApptDate());

				apptNo = apptNo + 1;

				/*
				 * Inserting appointment details into Appointment table
				 */
				values = clinicDAOInf.insertApppointmentDetails(clinicForm, clinicID, apptNo);

				/*
				 * Inserting a record with patientID and clinicID along with clinicRegNo into
				 * ClincRegistration table
				 */
				String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(clinicForm.getClinicSuffixName(),
						clinicForm.getClinicID());

				statusMessage = patientDAOInf.insertClinicRegistration(clinicForm.getPatientID(),
						clinicForm.getClinicID(), cilincRegNo);

				if (statusMessage.equals("success")) {
					System.out.println("Clinic registration details inserted successfully.");

					/*
					 * Check whether appointment udpated flag is on or not, and depending upon that
					 * sending SMS and Email
					 */
					boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

					if (SMSCheck) {

						/*
						 * Sending patient a welcome as well as Appointment scheduled message on
						 * checking whether mobile no is available for that patient or not
						 */
						if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
							System.out.println("Mobile no not found for patient.");
						} else {

							boolean SMSCheck1 = util.verifyCommunicationCheck("smsWelcome");

							if (SMSCheck1) {

								/*
								 * Sending patient a welcome SMS
								 */
								smsSender.sendWelcomeSMS(clinicForm.getPatientID(), clinicForm.getPracticeID(),
										mobileNo, clinicID);

							}

							boolean LoginSMSCheck1 = util.verifyCommunicationCheck("smsPatPortalCredentials");

							if (LoginSMSCheck1) {

								/*
								 * Sending patient a login credentials SMS
								 */
								smsSender.sendPatientPortalCredentialsSMS(clinicForm.getPatientID(),
										clinicForm.getPracticeID(), mobileNo, clinicID);

							}

							String apptDate = clinicForm.getApptDate();

							if (apptDate == null || apptDate == "") {
								apptDate = "";
							} else {
								try {

									if (apptDate.isEmpty()) {
										apptDate = "";
									} else {
										if (apptDate.substring(2, 3).equals("-")) {
											apptDate = apptDate;
										} else {
											apptDate = dateFormat.format(dateFormat1.parse(apptDate));
										}
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}

							// Retrieving doctor name from the appointmnet
							String doctorName = clinicDAOInf.retrieveApptDocName(clinicForm.getPatientID(), apptDate,
									clinicForm.getApptStartTime(), clinicForm.getApptEndTime(), clinicID);

							/*
							 * Sending appointment scheduled SMS to patient
							 */
							smsSender.sendAppointmentSMS(clinicForm.getPatientID(), mobileNo,
									clinicForm.getPracticeID(), clinicID, apptDate, clinicForm.getApptStartTime(),
									ActivityStatus.SCHEDULED, doctorName);
						}

					}

					boolean EmailCheck = util.verifyCommunicationCheck("emailApptSchedule");

					if (EmailCheck) {

						/*
						 * Retrieving patient's email ID, if not null, then sending patient a welcome
						 * mail
						 */
						emailID = patientDAOInf.retrievePatientEmailByID(clinicForm.getPatientID());

						if (emailID == null || emailID == "" || emailID.isEmpty()) {
							System.out.println("EmailID not found for patient.");
						} else {

							boolean SMSCheck1 = util.verifyCommunicationCheck("emailWelcome");

							if (SMSCheck1) {

								/*
								 * Sending welcome mail to patient
								 */
								emailUtil.sendWelcomeMail(clinicForm.getPatientID(), clinicForm.getPracticeID(),
										emailID, clinicID);

							}

							boolean credEmailCheck1 = util.verifyCommunicationCheck("emailPatPortalCredentials");

							if (credEmailCheck1) {

								/*
								 * Sending login credentials to patient
								 */
								emailUtil.sendPatientPortalCredentialsMail(clinicForm.getPatientID(),
										clinicForm.getPracticeID(), emailID, clinicID);

							}

							String apptDate = clinicForm.getApptDate();

							if (apptDate == null || apptDate == "") {
								apptDate = "";
							} else {
								try {

									if (apptDate.isEmpty()) {
										apptDate = "";
									} else {
										if (apptDate.substring(2, 3).equals("-")) {
											apptDate = apptDate;
										} else {
											apptDate = dateFormat.format(dateFormat1.parse(apptDate));
										}
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}

							/*
							 * Sending appointment scheduled Email to patient
							 */
							emailUtil.sendAppointmentEmail(clinicForm.getPracticeID(), clinicID, emailID,
									clinicForm.getPatientID(), apptDate, clinicForm.getApptStartTime(),
									ActivityStatus.SCHEDULED);
						}

					}

				} else {
					System.out.println("Failed to insert clinic registration details into ClinicRegistration table.");

				}

				return values;

			} else {

				System.out.println("Failed to add patient details.");

				object.put("Msg", "Error");

				array.add(object);

				values.put("Release", array);

				return values;

			}

		} else {

			System.out.println("inside pid not 0");
			/*
			 * Check whether patient with patientID already exist for the current clinic, if
			 * not, add new entry with to ClinicRegistraion table with current clinicID and
			 * new ClinicRegNo, else proceed further
			 */
			boolean check = patientDAOInf.verifyPatientExistsForCurrentClinic(clinicForm.getPatientID(),
					clinicForm.getClinicID());
			System.out.println("check val:" + check);
			if (check) {
				System.out.println("Patient already registered with the current clinic");
			} else {
				System.out.println("inside else of check");
				/*
				 * Inserting a record with patientID and clinicID along with clinicRegNo into
				 * ClincRegistration table
				 */
				String cilincRegNo = patientDAOInf.retirevePatientClinicRegistrationNo(clinicForm.getClinicSuffixName(),
						clinicForm.getClinicID());

				statusMessage = patientDAOInf.insertClinicRegistration(clinicForm.getPatientID(),
						clinicForm.getClinicID(), cilincRegNo);

				if (statusMessage.equals("success")) {
					System.out.println("Clinic registration details inserted successfully.");
				} else {
					System.out.println("Failed to insert clinic registration details into ClinicRegistration table.");

				}

			}

			/*
			 * Retrieving last added appointment number for today and adding 1 to it in
			 * order to get new appointment number for current date
			 */

			int apptNo = clinicDAOInf.retrieveAppointmentNumber(clinicForm.getApptDate());

			apptNo = apptNo + 1;

			/*
			 * Inserting appointment details into Appointment table
			 */
			values = clinicDAOInf.insertApppointmentDetails(clinicForm, clinicID, apptNo);

			/*
			 * Check whether appointment udpated flag is on or not, and depending upon that
			 * sending SMS and Email
			 */
			boolean SMSCheck = util.verifyCommunicationCheck("smsApptSchedule");

			if (SMSCheck) {

				/*
				 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
				 * welcome message
				 */
				mobileNo = patientDAOInf.retrievePatientMobileNoByID(clinicForm.getPatientID());

				/*
				 * Sending patient a welcome as well as Appointment scheduled message on
				 * checking whether mobile no is available for that patient or not
				 */
				if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
					System.out.println("Mobile no not found for patient.");
				} else {

					String apptDate = clinicForm.getApptDate();

					if (apptDate == null || apptDate == "") {
						apptDate = "";
					} else {
						try {

							if (apptDate.isEmpty()) {
								apptDate = "";
							} else {
								if (apptDate.substring(2, 3).equals("-")) {
									apptDate = apptDate;
								} else {
									apptDate = dateFormat.format(dateFormat1.parse(apptDate));
								}
							}

						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}

					// Retrieving doctor name from the appointmnet
					String doctorName = clinicDAOInf.retrieveApptDocName(clinicForm.getPatientID(), apptDate,
							clinicForm.getApptStartTime(), clinicForm.getApptEndTime(), clinicID);

					/*
					 * Sending appointment scheduled SMS to patient
					 */
					smsSender.sendAppointmentSMS(clinicForm.getPatientID(), mobileNo, clinicForm.getPracticeID(),
							clinicID, apptDate, clinicForm.getApptStartTime(), ActivityStatus.SCHEDULED, doctorName);
				}

			}

			boolean EmailCheck = util.verifyCommunicationCheck("emailApptSchedule");

			if (EmailCheck) {

				/*
				 * Retrieving patient's email ID, if not null, then sending patient a welcome
				 * mail
				 */
				emailID = patientDAOInf.retrievePatientEmailByID(clinicForm.getPatientID());

				if (emailID == null || emailID == "" || emailID.isEmpty()) {
					System.out.println("EmailID not found for patient.");
				} else {

					String apptDate = clinicForm.getApptDate();

					if (apptDate == null || apptDate == "") {
						apptDate = "";
					} else {
						try {

							if (apptDate.isEmpty()) {
								apptDate = "";
							} else {
								if (apptDate.substring(2, 3).equals("-")) {
									apptDate = apptDate;
								} else {
									apptDate = dateFormat.format(dateFormat1.parse(apptDate));
								}
							}

						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}

					/*
					 * Sending appointment scheduled Email to patient
					 */
					emailUtil.sendAppointmentEmail(clinicForm.getPracticeID(), clinicID, emailID,
							clinicForm.getPatientID(), apptDate, clinicForm.getApptStartTime(),
							ActivityStatus.SCHEDULED);

				}

			}

			return values;

		}

	}

	public String addProduct(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether product with same tradename already exists into Product table
		 * or not, if so, then give error msg else proceed further to add product
		 * details into Product table
		 */
		boolean check = prescriptionManagementDAOInf.verifyProductExits(form.getTradeName(), form.getClinicID());

		if (check) {

			System.out.println("Trade Name already exists into Product table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting product details into Product table
			 */
			statusMessage = prescriptionManagementDAOInf.insertProduct(form);
			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * retrieving last entered product ID based on barcode
				 */
				int productID = prescriptionManagementDAOInf.retrieveLastEnteredProductId(form.getBarcode());

				return statusMessage;

			} else {

				System.out.println("Failed to insert product details.");

				statusMessage = "error";

				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {

					System.out.println("Failed to udpate product details.");

					statusMessage = "error";

					return statusMessage;

				}
			}

		}

	}

	public String editProduct(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		System.out.println("Id's: " + form.getTradeName() + "-" + form.getProductID());
		boolean check = prescriptionManagementDAOInf.verifyEditTradeNameAlreadyExists(form.getTradeName(),
				form.getProductID());

		if (check) {

			System.out.println("Product with same Trade name already exists into Product table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Product details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.updateProduct(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Product details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String editProductPrice(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();
		/*
		 * Inactive all previous price for product ID by setting activity status to
		 * Inactive
		 */
		prescriptionManagementDAOInf.inactiveProductPrive(form.getProductID());

		/*
		 * Inserting new value into Price with activitystatus as Active
		 */
		statusMessage = prescriptionManagementDAOInf.insertProductPrice(form, form.getProductID());

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * Update active price of Product by setting selling Price value from Price
			 * table whose activityStatus is Active for that productID
			 */
			statusMessage = prescriptionManagementDAOInf.updateActiveProductPrice(form.getProductID(),
					form.getSellingPrice());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to udpate activeprice for Product");

				statusMessage = "error";

				return statusMessage;

			}

		} else {

			System.out.println("Failed to insert product price details.");

			statusMessage = "error";

			return statusMessage;

		}
	}

	public String addStockReceipt(PrescriptionManagementForm managementForm, int userID) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Checking whether products are selected or not, if not, give message saying
		 * Products not selected. Please select at least one product, else proceed
		 * further
		 */
		if (managementForm.getProductCompID() == null) {

			System.out.println("No product selected.");

			statusMessage = "noProductSelected";

			return statusMessage;

		} else {

			/*
			 * Inserting stock receipt details into StockReceipt table
			 */
			statusMessage = prescriptionManagementDAOInf.insertStockReceipt(managementForm, userID);

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Stock receipt inserted successfully.");

				/*
				 * Retrieving previously inserted stock receipt ID
				 */
				int stockReceiptID = prescriptionManagementDAOInf.retrieveLastStockReceiptID();

				System.out.println("previously entered stockreceipt ID is :: " + stockReceiptID);

				managementForm.setStockReceiptID(stockReceiptID);

				/*
				 * Iterating over product list and inserting each product details into Stock
				 * table
				 */
				for (int i = 0; i < managementForm.getProductCompID().length; i++) {

					String taxPer = managementForm.getProductTaxPercent()[i];

					double taxPercent = 0D;

					if (taxPer == null || taxPer == "") {
						taxPercent = 0D;
					} else {
						if (taxPer.isEmpty()) {
							taxPercent = 0D;
						} else {
							taxPercent = Double.parseDouble(taxPer);
						}
					}

					statusMessage = prescriptionManagementDAOInf.insertStock(
							Double.parseDouble(managementForm.getProductQuantity()[i]),
							Integer.parseInt(managementForm.getProductCompID()[i]),
							Double.parseDouble(managementForm.getProductAmount()[i]), taxPercent, stockReceiptID,
							managementForm.getProductExpiryDate()[i], ActivityStatus.NOT_EMPTY,
							Double.parseDouble(managementForm.getProductCostPrice()[i]),
							Double.parseDouble(managementForm.getProductSellingPrice()[i]),
							Integer.parseInt(managementForm.getProductTaxInclusive()[i]),
							managementForm.getProductTaxName()[i], managementForm.getClinicID());

					if (statusMessage.equals("success")) {

						System.out.println("Stock details inserted successfully into Stock table.");

					} else {

						System.out.println("Failed to insert stock details into Stock table.");

					}

					// Retrieving last inserted stockID based on stockReceiptID
					// and productID
					int stockID = prescriptionManagementDAOInf.retrieveLastEnteredStockID(stockReceiptID,
							Integer.parseInt(managementForm.getProductCompID()[i]));

					/*
					 * Inserting a record into StockTransaction table
					 */
					statusMessage = prescriptionManagementDAOInf.insertStockTransaction(stockID,
							Double.parseDouble(managementForm.getProductQuantity()[i]), ActivityStatus.ADD);

					if (statusMessage.equals("success")) {

						System.out.println(
								"Stock transaction details inserted successfully into StockTransaction table.");

					} else {

						System.out.println("Failed to insert stock transaction details into StockTransaction table.");

					}
				}

				if (statusMessage.equals("success")) {

					/*
					 * Retrieving total tax amount based on stockReceiptID from Stock table and
					 * Updating tax amount into StockReceipt table based on fetched stockReceiptID
					 */
					double totalTax = prescriptionManagementDAOInf.retrieveTotalTaxAmount(stockReceiptID);

					statusMessage = prescriptionManagementDAOInf.updateTaxAmount(stockReceiptID, totalTax);

					if (statusMessage.equals("success")) {

						System.out.println("Successfully updated tax amount into StockReceipt table.");

						return statusMessage;

					} else {

						System.out.println("Failed to update tax amount into StockReceipt table.");

						statusMessage = "error";

						return statusMessage;

					}

				} else {

					System.out.println("Failed to add product into stock.");

					statusMessage = "error";

					return statusMessage;

				}

			} else {

				System.out.println("Failed to insert stock receipt details into StockReceipt table.");

				return statusMessage;

			}

		}
	}

	public String editStockReceipt(PrescriptionManagementForm managementForm, int userID) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Updating stock receipt details into StockReceipt table
		 */
		statusMessage = prescriptionManagementDAOInf.updateStockReceipt(managementForm, userID);

		if (statusMessage.equalsIgnoreCase("success")) {

			System.out.println("Stock receipt udpated successfully.");

			/*
			 * Updating clinicID for all Stock based on stockReceiptID
			 */
			statusMessage = prescriptionManagementDAOInf.updateStockClinic(managementForm.getStockReceiptID(),
					managementForm.getClinicID());

			if (statusMessage.equals("success")) {

				System.out.println("Clinic updated successfully for stock.");

				/*
				 * Checking whether products added or not, if added iterating over it and
				 * inserting each product details into Stock table, else proceeding further
				 */
				if (managementForm.getProductCompID() == null) {

					System.out.println("No product selected");

					statusMessage = "success";

					return statusMessage;

				} else {

					/*
					 * Iterating over product list and inserting each product details into Stock
					 * table
					 */
					for (int i = 0; i < managementForm.getProductCompID().length; i++) {

						String taxPer = managementForm.getProductTaxPercent()[i];

						double taxPercent = 0D;

						if (taxPer == null || taxPer == "") {
							taxPercent = 0D;
						} else {
							if (taxPer.isEmpty()) {
								taxPercent = 0D;
							} else {
								taxPercent = Double.parseDouble(taxPer);
							}
						}

						statusMessage = prescriptionManagementDAOInf.insertStock(
								Double.parseDouble(managementForm.getProductQuantity()[i]),
								Integer.parseInt(managementForm.getProductCompID()[i]),
								Double.parseDouble(managementForm.getProductAmount()[i]), taxPercent,
								managementForm.getStockReceiptID(), managementForm.getProductExpiryDate()[i],
								ActivityStatus.NOT_EMPTY, Double.parseDouble(managementForm.getProductCostPrice()[i]),
								Double.parseDouble(managementForm.getProductSellingPrice()[i]),
								Integer.parseInt(managementForm.getProductTaxInclusive()[i]),
								managementForm.getProductTaxName()[i], managementForm.getClinicID());

						if (statusMessage.equals("success")) {

							System.out.println("Stock details inserted successfully into Stock table.");

						} else {

							System.out.println("Failed to insert stock details into Stock table.");

						}

						// Retrieving last inserted stockID based on
						// stockReceiptID
						// and productID
						int stockID = prescriptionManagementDAOInf.retrieveLastEnteredStockID(
								managementForm.getStockReceiptID(),
								Integer.parseInt(managementForm.getProductCompID()[i]));

						/*
						 * Inserting a record into StockTransaction table
						 */
						statusMessage = prescriptionManagementDAOInf.insertStockTransaction(stockID,
								Double.parseDouble(managementForm.getProductQuantity()[i]), ActivityStatus.ADD);

						if (statusMessage.equals("success")) {

							System.out.println(
									"Stock transaction details inserted successfully into StockTransaction table.");

						} else {

							System.out
									.println("Failed to insert stock transaction details into StockTransaction table.");

						}
					}

					if (statusMessage.equals("success")) {

						/*
						 * Retrieving total tax amount based on stockReceiptID from Stock table and
						 * Updating tax amount into StockReceipt table based on fetched stockReceiptID
						 */
						double totalTax = prescriptionManagementDAOInf
								.retrieveTotalTaxAmount(managementForm.getStockReceiptID());

						statusMessage = prescriptionManagementDAOInf.updateTaxAmount(managementForm.getStockReceiptID(),
								totalTax);

						if (statusMessage.equals("success")) {

							System.out.println("Successfully updated tax amount into StockReceipt table.");

							return statusMessage;

						} else {

							System.out.println("Failed to update tax amount into StockReceipt table.");

							statusMessage = "error";

							return statusMessage;

						}

					} else {

						System.out.println("Failed to add product into stock.");

						statusMessage = "error";

						return statusMessage;

					}

				}

			} else {

				System.out.println("Failed to clinicID for stock based on stockReceiptID from Stock table.");

				return statusMessage;

			}

		} else {

			System.out.println("Failed to update stock receipt details into StockReceipt table.");

			return statusMessage;

		}

	}

	public String removeStock(PrescriptionManagementForm managementForm, int userID) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * check whether any product is selected to remove by checking if productStockID
		 * is null or not, if found null then return success with message as no product
		 * selected to remove else iterate over the total products selected to remove
		 * and do the further processing
		 */
		if (managementForm.getProductStockID() == null) {

			System.out.println("No product selected.");

			statusMessage = "noProductSelected";

			return statusMessage;

		} else {

			/*
			 * Iterating over product list and inserting each product details into
			 * StockTransaction table
			 */
			for (int i = 0; i < managementForm.getProductStockID().length; i++) {

				String quantity = managementForm.getProductQuantity()[i];

				double prodQnty = 0;

				int stockID = Integer.parseInt(managementForm.getProductStockID()[i]);

				String reason = managementForm.getRemoveReason()[i];

				if (quantity == null || quantity == "") {
					prodQnty = 0;
				} else if (quantity.isEmpty()) {
					prodQnty = 0;
				} else {
					prodQnty = Double.parseDouble(quantity);
				}

				statusMessage = prescriptionManagementDAOInf.insertStockTransaction(stockID, prodQnty, reason);

				if (statusMessage.equals("success")) {

					System.out.println("Stock transaction details inserted successfully into StockTransaction table.");

					/*
					 * Checking what the reason is, if it is, 'Remove-Damaged', then retrieving
					 * available netStock from Stock table corresponding to the stockID and
					 * subtracting quantity to be removed from it, if difference is 0, then updating
					 * netStock as well as status as Empty into Stock table for that particular
					 * stockID else if reason is other than 'Remove-Damaged', then updating netStock
					 * as 0 and status as Empty for that particular stockID
					 */
					if (reason.equals("Remove-Damaged")) {

						// retrieving netStock from Stock table based on stockID
						double netStock = prescriptionManagementDAOInf.retrieveProductNetStockByStockID(stockID);

						// subtracting quantity from net stock
						double qntyDifference = netStock - prodQnty;

						System.out.println(
								"Quantity difference is ..." + qntyDifference + ".. check..." + (qntyDifference == 0));

						// checking whther qntyDifference is 0, if so, then udpating netStock and status
						// as 0 and Empty respectively into Stock table corresponding to the stockID
						// else udpating only qntyDifference into Stock table
						if (qntyDifference == 0) {

							String status = prescriptionManagementDAOInf.updateStockByStockID(stockID,
									ActivityStatus.EMPTY, qntyDifference);

							if (!status.equals("success")) {
								System.out
										.println("Failed to update net stock for the stock for the reason: " + reason);
							}

						} else {

							String status = prescriptionManagementDAOInf.updateStockByStockID(stockID,
									ActivityStatus.NOT_EMPTY, qntyDifference);

							if (!status.equals("success")) {
								System.out
										.println("Failed to update net stock for the stock for the reason: " + reason);
							}

						}

					} else {
						// Updating netstock and status as 0 and empty respectively for the
						// corresponding stockID if reason is either 'Remove-Expire' and 'Remove-Price
						// Changed'
						String status = prescriptionManagementDAOInf.updateStockByStockID(stockID, ActivityStatus.EMPTY,
								0);

						if (!status.equals("success")) {
							System.out.println("Failed to update net stock for the stock for the reason: " + reason);
						}
					}

				} else {

					System.out.println("Failed to insert stock details into StockTransaction table.");

				}

			}

			if (statusMessage.equals("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to remove product from stock.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editUserProfile(RegistrationForm registrationForm, String realPath, HttpServletRequest request) {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			boolean check = false;

			/*
			 * Checking whether PIN has been changed by user, if so, then insert the PIN
			 * changed log into Audit table
			 */
			check = registrationDAOinf.verifyPINChange(registrationForm.getUserID(), registrationForm.getPIN());
			if (!check) {

				/*
				 * Inserting PIN change activity into Audit table
				 */
				daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", registrationForm.getUserID());

			}

			/*
			 * Check whether password from table for userID is same as that entered by user
			 * for the same userID, then proceed for updation else check for last five
			 * password existence from PasswordHistory table
			 */
			check = registrationDAOinf.verifyPassword(registrationForm.getUserID(), registrationForm.getPassword());

			if (check) {

				/*
				 * Updating user details from User table
				 */
				statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated user details into User table");

					return statusMessage;

				} else {

					System.out.println("Failed to update user details into User table");

					statusMessage = "error";
					return statusMessage;

				}

				/*
				 * Password match check; checking whether entered password matches last five
				 * passwords from Password History, then give error message else proceed further
				 */
			} else {

				check = registrationDAOinf.verifyPasswordHistory(registrationForm.getUserID(),
						registrationForm.getPassword());

				if (check) {

					System.out.println("Entered password lies within last 5 passwords.");

					statusMessage = "input";

					return statusMessage;

				} else {

					/*
					 * Updating user details from User table
					 */
					statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully updated user details into User table");

						/*
						 * Inserting password into PasswordHistroy table
						 */
						registrationDAOinf.insertPasswordHistory(registrationForm.getUserID(),
								registrationForm.getPassword());

						// Inserting values into Audit table for password
						daoInf.insertAudit(request.getRemoteAddr(), "Password changed", registrationForm.getUserID());

						return statusMessage;

					} else {

						System.out.println("Failed to update user details into User table");

						statusMessage = "error";
						return statusMessage;

					}

				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}

	}

	public String editUserProfileDetail(RegistrationForm registrationForm, String realPath,
			HttpServletRequest request) {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		try {

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (registrationForm.getSignature() != null) {

				String[] array = registrationForm.getSignatureFileName().split("\\.");
				String fileExtension = array[1];
				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {
					registrationForm.setSignatureContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {
					registrationForm.setSignatureContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {
					registrationForm.setSignatureContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {
					registrationForm.setSignatureContentType("image/bmp");
				}

				String fileNameToBeStored = registrationForm.getUsername() + "_Signature." + fileExtension;
				System.out.println("Original File name is ::::" + registrationForm.getSignatureFileName());
				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */

				/**
				 * Setting file name with S3 bucket path to be inserted into AppUser table into
				 * SignatureDBName
				 */
				registrationForm.setSignatureDBName(fileNameToBeStored);
				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(registrationForm.getSignature(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);

			}

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (registrationForm.getProfilePic() != null) {

				String[] array = registrationForm.getProfilePicFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {

					registrationForm.setProfilePicContentType("image/jpg");

				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					registrationForm.setProfilePicContentType("image/jpeg");

				} else if (fileExtension.equalsIgnoreCase("png")) {

					registrationForm.setProfilePicContentType("image/png");

				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					registrationForm.setProfilePicContentType("image/bmp");

				}

				String fileNameToBeStored = registrationForm.getUsername() + "." + fileExtension;

				System.out.println("Original File name is ::::" + registrationForm.getProfilePicFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */

				/**
				 * Setting file name with S3 bucket path to be inserted into AppUser table into
				 * SignatureDBName
				 */
				registrationForm.setProfilePicDBName(fileNameToBeStored);
				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(registrationForm.getProfilePic(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);

			}

			/*
			 * Verify whether changed username already exist with other userID, if yes, then
			 * give error else proceed further
			 */
			boolean check = false;

			check = registrationDAOinf.verifyUsernameWithUserID(registrationForm.getUsername(),
					registrationForm.getUserID(), registrationForm.getPracticeID());

			if (check) {

				/*
				 * Checking whether PIN has been changed by user, if so, then insert the PIN
				 * changed log into Audit table
				 */
				check = registrationDAOinf.verifyPINChange(registrationForm.getUserID(), registrationForm.getPIN());
				if (!check) {

					/*
					 * Inserting PIN change activity into Audit table
					 */
					daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", registrationForm.getUserID());

				}

				/*
				 * Check whether password from table for userID is same as that entered by user
				 * for the same userID, then proceed for updation else check for last five
				 * password existence from PasswordHistory table
				 */
				check = registrationDAOinf.verifyPassword(registrationForm.getUserID(), registrationForm.getPassword());

				if (check) {

					/*
					 * Updating user details from User table
					 */
					statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

					if (statusMessage.equalsIgnoreCase("success")) {

						statusMessage = "success";
						return statusMessage;

					} else {

						System.out.println("Failed to update user details into User table");

						statusMessage = "error";
						return statusMessage;

					}

					/*
					 * Password match check; checking whether entered password matches last five
					 * passwords from Password History, then give error message else proceed further
					 */
				} else {

					check = registrationDAOinf.verifyPasswordHistory(registrationForm.getUserID(),
							registrationForm.getPassword());

					if (check) {

						System.out.println("Entered password lies within last 5 passwords.");

						statusMessage = "input";

						return statusMessage;

					} else {

						/*
						 * Updating user details from User table
						 */
						statusMessage = registrationDAOinf.updateUserDetail(registrationForm);

						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * Inserting password into PasswordHistroy table
							 */
							registrationDAOinf.insertPasswordHistory(registrationForm.getUserID(),
									registrationForm.getPassword());

							// Inserting values into Audit table for
							// password
							daoInf.insertAudit(request.getRemoteAddr(), "Password changed",
									registrationForm.getUserID());

							return statusMessage;

						} else {

							System.out.println("Failed to update user details into User table");

							statusMessage = "error";
							return statusMessage;
						}
					}

				}

			} else {
				System.out.println("Username already exist for another user.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}
	}

	public String configureBill(PatientForm form, int userID) {
		patientDAOInf = new PatientDAOImpl();

		/*
		 * Check whether details added for the current visitID into receipt table if
		 * added then update it else insert new re3cord into Receipt for new bill
		 */
		boolean visitCheck1 = patientDAOInf.verifyVisitExists("Receipt", form.getVisitID());

		if (visitCheck1) {

			System.out.println("record already exists for the current visit into receipt table");

			/*
			 * Updating into Receipt details corresponds to current visitID
			 */
			statusMessage = patientDAOInf.updateBill(form);
			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Retrieve previously entered receiptID
				 */
				int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

				if (receiptID == 0) {

					System.out.println("Failed to retrieve previosuly entered receiptID.");

					statusMessage = "error";

				} else {

					statusMessage = patientDAOInf.updatePaymentDetails(form, form.getPaymentType(), receiptID);

					if (statusMessage.equals("success")) {
						System.out.println("Payment details updated successfully.");
					} else {
						System.out.println("Failed to updated Payment details into PaymentDetails table.");
					}

					/*
					 * Updating the receipt item details
					 */
					if (form.getItemIDArr() == null) {
						System.out.println("No billing items are found..");
						statusMessage = "success";
					} else {
						for (int i = 0; i < form.getItemIDArr().length; i++) {
							double rate = 0D;
							if (form.getItemRateArr()[i] == null) {
								rate = 0D;
							} else {
								rate = Double.parseDouble(form.getItemRateArr()[i]);
							}

							int receiptItemID = 0;

							if (form.getItemIDArr()[i] == "0" || form.getItemIDArr()[i] == null) {
								receiptItemID = 0;
							} else {
								receiptItemID = Integer.parseInt(form.getItemIDArr()[i]);
							}

							int quantity = 1;

							if (form.getItemQuantityArr() == null) {
								quantity = 1;
							} else {
								if (form.getItemQuantityArr()[i] == null) {
									quantity = 1;
								} else {
									quantity = Integer.parseInt(form.getItemQuantityArr()[i]);
								}
							}

							if (receiptItemID == 0) {
								// Inserting the details newly into ReceiptItem table for the correspodig
								// receiptID
								statusMessage = patientDAOInf.insertReceiptItemDetails(form.getItemNameArr()[i], rate,
										rate, quantity, receiptID);

								if (statusMessage.equals("success")) {

									System.out.println("Receipt item details addedd successfully");

								} else {

									System.out.println("Failed to insert reciept item details.");

								}

							} else {
								// Updating the existing details
								statusMessage = patientDAOInf.updateReceiptItemDetails(form.getItemNameArr()[i], rate,
										rate, 1, receiptItemID);

								if (statusMessage.equals("success")) {

									System.out.println("Receipt item details updated successfully");

								} else {

									System.out.println("Failed to update reciept item details.");

								}
							}

						}
					}

				}

				return statusMessage;
			} else {
				System.out.println("Failed to udpate billing details into table");
				statusMessage = "error";
				return statusMessage;
			}

		} else {

			System.out.println("No record added into receipt for current visit");

			/*
			 * Inserting Billing details into Billing table
			 */
			statusMessage = patientDAOInf.insertBillDetails(form, form.getVisitID(), userID);
			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Retrieve previously entered receiptID
				 */
				int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

				if (receiptID == 0) {

					System.out.println("Failed to retrieve previosuly entered receiptID.");

					statusMessage = "error";

				} else {

					statusMessage = patientDAOInf.insertPaymentDetails(form, form.getPaymentType(), receiptID);

					if (statusMessage.equals("success")) {
						System.out.println("Payment details added successfully.");
					} else {
						System.out.println("Failed to add Payment details into PaymentDetails table.");
						statusMessage = "error";
					}

					/*
					 * Inserting receipt item details into ReceiptItem table against the receiptID
					 */
					if (form.getItemNameArr() == null) {
						System.out.println("No billing items are found..");
						statusMessage = "success";
					} else {
						for (int i = 0; i < form.getItemNameArr().length; i++) {
							double rate = 0D;
							int quantity = 0;

							if (form.getItemRateArr()[i] == null) {
								rate = 0D;
							} else {
								rate = Double.parseDouble(form.getItemRateArr()[i]);
							}

							if (form.getItemQuantityArr() == null) {
								quantity = 1;
							} else {
								if (form.getItemQuantityArr()[i] == null) {
									quantity = 1;
								} else {
									quantity = Integer.parseInt(form.getItemQuantityArr()[i]);
								}
							}

							statusMessage = patientDAOInf.insertReceiptItemDetails(form.getItemNameArr()[i], rate, rate,
									quantity, receiptID);

							if (statusMessage.equals("success")) {

								System.out.println("Receipt item details addedd successfully");

							} else {

								System.out.println("Failed to insert reciept item details.");

							}
						}
					}

				}

				return statusMessage;
			} else {
				System.out.println("Failed to insert billing details into table");
				statusMessage = "error";
				return statusMessage;
			}

		}

	}

	public String geterateClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath) {

		reportDAOInf = new ReportDAOImpl();

		ExcelUtil excelUtil = new ExcelUtil();

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
		 * checking whether query is saved or not, if yes, then inserting it into
		 * SavedQuery table else proceeding further to generate excel sheet for the
		 * report
		 */
		if (patientForm.getQueryTitle() == null || patientForm.getQueryTitle() == "") {

			System.out.println("Query not saved");
			statusMessage = "success";

			statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

			if (statusMessage.equals("success")) {

				// patientForm.setFileName(excelFileName);
				File inputFile = new File(realPath + "/" + excelFileName);

				try {

					// Storing file to S3 RDML INPUT FILE location
					String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
							s3reportFilePath);

					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
							.getObjectContent();

					patientForm.setFileInputStream(s3ObjectInputStream);

					patientForm.setFileName(excelFileName);

				} catch (Exception e) {

					statusMessage = "exception";

					e.printStackTrace();
				}

			} else {

				System.out.println("Failed to generate excel file for clinica data report.");

				statusMessage = "error";

			}

		} else if (patientForm.getQueryTitle().isEmpty()) {

			System.out.println("Query not saved");
			statusMessage = "success";

			statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

			if (statusMessage.equals("success")) {

				File inputFile = new File(realPath + "/" + excelFileName);

				try {

					// Storing file to S3 RDML INPUT FILE location
					String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
							s3reportFilePath);

					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
							.getObjectContent();

					patientForm.setFileInputStream(s3ObjectInputStream);

					patientForm.setFileName(excelFileName);

				} catch (Exception e) {

					statusMessage = "exception";

					e.printStackTrace();
				}

			} else {

				System.out.println("Failed to generate excel file for clinica data report.");

				statusMessage = "error";

			}

		} else {

			statusMessage = reportDAOInf.insertSavedQuery(patientForm);

			if (statusMessage.equals("success")) {

				statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

				if (statusMessage.equals("success")) {

					File inputFile = new File(realPath + "/" + excelFileName);

					try {

						// Storing file to S3 RDML INPUT FILE location
						String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
								s3reportFilePath);

						S3ObjectInputStream s3ObjectInputStream = s3
								.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
								.getObjectContent();

						patientForm.setFileInputStream(s3ObjectInputStream);

						patientForm.setFileName(excelFileName);

					} catch (Exception e) {

						statusMessage = "exception";

						e.printStackTrace();
					}

				} else {

					System.out.println("Failed to generate excel file for clinica data report.");

					statusMessage = "error";

				}

				return statusMessage;

			} else {

				System.out.println("Failed to insert saved query into SaveQuery table.");

				statusMessage = "error";

				return statusMessage;

			}

		}

		return statusMessage;

	}

	public String disableProduct(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Disabling product by setting activityStatu of that product to Inactive into
		 * Product table
		 */
		statusMessage = prescriptionManagementDAOInf.disableProduct(form.getProductID());

		if (statusMessage.equals("success")) {

			/*
			 * Retrieving not empty stock list of a particular product by productID from
			 * Stock table and then update each stock record with status as Empty and
			 * netStock as 0 into Stock table corresponds to stockID
			 */
			HashMap<Integer, Double> map = prescriptionManagementDAOInf.retrieveProductStockList(form.getProductID(),
					form.getClinicID());

			/*
			 * Checking whether map size is greater then 0, if so, then only iterating over
			 * the map and udpating netStock and status into Stock table else, returing from
			 * the message with statusMessage as success
			 */
			if (map.size() > 0) {

				for (Integer stockID : map.keySet()) {
					double quantity = map.get(stockID);

					/*
					 * updating netStock to 0, status to Empty for the related stockID into Stock
					 * table
					 */
					statusMessage = prescriptionManagementDAOInf.updateStockByStockID(stockID, ActivityStatus.EMPTY, 0);

					if (statusMessage.equals("success")) {

						/*
						 * inserting a record into StockTransaction table for the stockID with available
						 * netStock with action as disabled
						 */
						statusMessage = prescriptionManagementDAOInf.insertStockTransaction(stockID, quantity,
								ActivityStatus.DISABLE);

						if (statusMessage.equals("success")) {

							System.out.println("StockTransaction with action as disabled is inserted successfully.");

						} else {
							System.out.println("Failed to insert record for stockID into StockTransaction table.");

							statusMessage = "error";
						}

					} else {
						System.out.println("failed to empty stock.");
						statusMessage = "error";

					}

				}

				return statusMessage;

			} else {

				System.out.println("No stock added for the product");

				statusMessage = "success";

				return statusMessage;

			}

		} else {

			System.out.println("Failed to disable product.");

			statusMessage = "error";

			return statusMessage;

		}

	}

	public String addCategory(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyCategoryNameAlreadyExists(form.getCategory());

		if (check) {

			System.out.println("Category Name already exists into Category table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			// statusMessage =
			// prescriptionManagementDAOInf.insertCategory(form.getCategory(),
			// form.getCategoryType());

			statusMessage = prescriptionManagementDAOInf.insertCategoryWithOT(form.getCategory(),
					form.getCategoryType(), form.getOtCategory());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Category details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editCategory(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyEditCategoryNameAlreadyExists(form.getCategory(),
				form.getCategoryID());

		if (check) {

			System.out.println("Category Name already exists into Category table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			// statusMessage =
			// prescriptionManagementDAOInf.updateCategory(form.getCategory(),
			// form.getCategoryID(), form.getCategoryType());

			statusMessage = prescriptionManagementDAOInf.updateCategoryWithOT(form.getCategory(), form.getCategoryID(),
					form.getCategoryType(), form.getOtCategory());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Category details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addSupplier(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether VatNumber with same name already exists into Supplier table or
		 * not, if so, then give error msg else proceed further to add Supplier details
		 * into Supplier table
		 */
		boolean check = prescriptionManagementDAOInf.verifyVatNumberAlreadyExists(form.getVatNumber());

		if (check) {

			System.out.println("Vat Number already exists into Supplier table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Supplier details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.insertSupplier(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Supplier details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editSupplier(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyEditVatNumberAlreadyExists(form.getVatNumber(),
				form.getSupplierID());

		if (check) {

			System.out.println("Supplier with same VatNumber already exists into Supplier table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.updateSupplier(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Supplier details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addInstruction(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = configurationDAOInf.verifyInstructionAlreadyExists(configurationForm.getInstruction());

		if (check) {

			System.out.println("Instruction Name already exists into PVInstructions table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = configurationDAOInf.insertInstruction(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Instructions.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editInstruction(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = configurationDAOInf.verifyEditInstructionsAlreadyExists(configurationForm.getInstruction(),
				configurationForm.getInstructionID());

		if (check) {

			System.out.println("Instructions with same name already exists into PVInstructions table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = configurationDAOInf.updateInstruction(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Instructions.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addTax(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether VatNumber with same name already exists into Supplier table or
		 * not, if so, then give error msg else proceed further to add Supplier details
		 * into Supplier table
		 */
		boolean check = prescriptionManagementDAOInf.verifyTaxDetailsAlreadyExists(form.getTaxName(),
				form.getTaxPercent());

		if (check) {

			System.out.println("Tax details already exists into Tax table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Supplier details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.insertTax(form.getTaxName(), form.getTaxPercent());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Tax details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editTax(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyEditTaxDetailsAlreadyExists(form.getTaxName(),
				form.getTaxPercent(), form.getTaxID());

		if (check) {

			System.out.println("Tax with same details already exists into Tax table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.updateTax(form.getTaxName(), form.getTaxPercent(),
					form.getTaxID());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Tax details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addFrequency(ConfigurationForm configurationForm, int practiceID) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether VatNumber with same name already exists into Supplier table or
		 * not, if so, then give error msg else proceed further to add Supplier details
		 * into Supplier table
		 */
		boolean check = configurationDAOInf.verifyFrequencyDetailsAlreadyExists(configurationForm.getFrequency(),
				configurationForm.getSortOrder(), practiceID);

		if (check) {

			System.out.println("Frequency with same Category already exists into PVFrequency table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Supplier details into Category table
			 */
			statusMessage = configurationDAOInf.insertFrequency(configurationForm, practiceID);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Frequency details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editFrequency(ConfigurationForm configurationForm, int practiceID) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = configurationDAOInf.verifyEditFrequencyAlreadyExists(configurationForm.getFrequency(),
				configurationForm.getFrequencyID(), configurationForm.getSortOrder(), practiceID);

		if (check) {

			System.out.println("Frequency with same Category already exists into PVFrequency table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = configurationDAOInf.updateFrequency(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Frequency Details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String configureVisitType(ClinicForm form, String realPath) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		try {

			/*
			 * verify whether VisitType with same name already exists into PVVisitType table
			 * or not, if so, then give error msg else proceed further to add VisitType
			 * details into PVVisitType table
			 */
			boolean check = prescriptionManagementDAOInf.verifyVisitTypeNameAlreadyExists(form.getVisitTypeName(),
					form.getClinicID(), form.getWorkflowOPDForm());

			if (check) {

				System.out.println("VisitType Name already exists into PVVisitType table.");

				statusMessage = "input";

				return statusMessage;

			} else {

				// Setting ConsentDocDBname as NULL
				form.setConsentDocDBName(null);

				/*
				 * Storing user uploaded profile pic file into image/profilePic directory of
				 * Project, only if the profilePic variable is not null
				 */
				if (form.getConsentDoc() != null) {

					String[] array = form.getConsentDocFileName().split("\\.");

					String fileExtension = array[1];

					String VisitTypeName = form.getVisitTypeName();

					String VisitType = VisitTypeName.replaceAll(" ", "_");

					String fileNameToBeStored = VisitType + "." + fileExtension;

					System.out.println("Original File name is ::::" + form.getConsentDocFileName());

					System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

					/**
					 * Setting file name with S3 bucket path to be inserted into AppUser table into
					 * SignatureDBName
					 */
					form.setConsentDocDBName(fileNameToBeStored);
					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(form.getConsentDoc(), fileNameToBeStored, bucketName,
							bucketRegion, s3rdmlFilePath);

				}

				clinicDAOInf = new ClinicDAOImpl();

				/*
				 * Inserting Category details into Category table
				 */
				statusMessage = clinicDAOInf.insertVisitType(form, form.getConsentDocDBName());

				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {

					System.out.println("Failed to insert Category details.");

					statusMessage = "error";

					return statusMessage;

				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}
	}

	public String configureEditVisitType(ClinicForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

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

		try {

			/*
			 * verify whether VisitType with same name already exists into PVVisitType table
			 * or not, if so, then give error msg else proceed further to add VisitType
			 * details into PVVisitType table
			 */
			boolean check = prescriptionManagementDAOInf.verifyEditVisitTypeNameAlreadyExists(form.getVisitTypeName(),
					form.getClinicID(), form.getWorkflowOPDForm(), form.getVisitTypeID());

			if (check) {

				System.out.println("VisitType Name already exists into PVVisitType table.");

				statusMessage = "input";

				return statusMessage;

			} else {

				// Setting ConsentDocDBname as NULL
				form.setConsentDocDBName(null);

				/*
				 * Storing user uploaded profile pic file into image/profilePic directory of
				 * Project, only if the profilePic variable is not null
				 */
				if (form.getConsentDoc() != null) {

					String[] array = form.getConsentDocFileName().split("\\.");

					String fileExtension = array[1];

					String VisitTypeName = form.getVisitTypeName();

					String VisitType = VisitTypeName.replaceAll(" ", "_");

					String fileNameToBeStored = VisitType + "." + fileExtension;

					System.out.println("Original File name is ::::" + form.getConsentDocFileName());

					System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

					/**
					 * Setting file name with S3 bucket path to be inserted into AppUser table into
					 * SignatureDBName
					 */
					form.setConsentDocDBName(fileNameToBeStored);
					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(form.getConsentDoc(), fileNameToBeStored, bucketName,
							bucketRegion, s3rdmlFilePath);
				}

				clinicDAOInf = new ClinicDAOImpl();

				/*
				 * Inserting Category details into Category table
				 */
				statusMessage = clinicDAOInf.updateVisitType(form, form.getConsentDocDBName());

				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {

					System.out.println("Failed to insert Category details.");

					statusMessage = "error";

					return statusMessage;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}
	}

	public String addFrequencyDetails(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		String[] frequency = patientForm.getNewEditFrequency();
		String[] noOfDays = patientForm.getNewEditNoOfDays();

		if (frequency == null) {
			System.out.println("No frequency details added.");

			statusMessage = "success";
		} else {

			for (int j = 0; j < frequency.length; j++) {

				statusMessage = patientDAOInf.insertFrequencyDetails(frequency[j], Integer.parseInt(noOfDays[j]),
						patientForm.getPrescriptionID());
			}

			if (statusMessage.equals("success")) {
				System.out.println("Frequency details inserted successfully into Frequency table");

				statusMessage = "success";
			} else {
				System.out.println("Failed to insert frequency details into Frequency table.");

				statusMessage = "error";
			}
		}

		return statusMessage;
	}

	public String geterateClinicalDataReportForAyurved(PatientForm patientForm, String excelFileName, String realPath) {

		reportDAOInf = new ReportDAOImpl();

		ExcelUtil excelUtil = new ExcelUtil();

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

		HashMap<String, String> selectMap = new HashMap<String, String>();

		/*
		 * checking whether query is saved or not, if yes, then inserting it into
		 * SavedQuery table else proceeding further to generate excel sheet for the
		 * report
		 */
		if (patientForm.getQueryTitle() == null || patientForm.getQueryTitle() == "") {

			System.out.println("Query not saved");
			statusMessage = "success";

			int savedQuewryID;

			String finalQuery = "SELECT ";

			String selectValues = "";
			// sleect column name loop

			String[] selectNameArr = patientForm.getSelectNameArr();
			String[] tableNameArr = patientForm.getTableNameArr();
			String initialWhere = patientForm.getInitialWhere();
			String[] colNameArr = patientForm.getColNameArr();
			String[] criteriaNameArr = patientForm.getCriteriaNameArr();
			String[] searchTextArr = patientForm.getSearchTextArr();

			for (int i = 0; i < selectNameArr.length; i++) {
				String[] array = selectNameArr[i].split("==");

				String tableName = array[0];
				String selectValue = array[1];

				selectMap.put(tableName, selectValue);

				if (selectValue.trim() == "" || selectValue.trim().isEmpty()) {
					continue;
				} else {
					selectValues += "," + selectValue;
				}

			}

			if (selectValues.startsWith(",")) {
				selectValues = selectValues.substring(1);
			}

			String totalTableName = "";

			for (int i = 0; i < tableNameArr.length; i++) {
				totalTableName = totalTableName + ", " + tableNameArr[i];
			}

			if (totalTableName.startsWith(",")) {
				totalTableName = totalTableName.substring(2);
			}

			tableNameArr = totalTableName.split(", ");

			tableNameArr = new HashSet<String>(Arrays.asList(tableNameArr)).toArray(new String[0]);

			String initialWhereCondition = "";

			String[] initialWhereArr = initialWhere.split(" AND ");
			String[] uniqueVal = new HashSet<String>(Arrays.asList(initialWhereArr)).toArray(new String[0]);
			for (int i = 0; i < uniqueVal.length; i++) {

				initialWhereCondition = initialWhereCondition + uniqueVal + " AND ";
			}

			System.out.println("initialWhereCondition: " + initialWhereCondition);

			if (initialWhere.contains("bt.")) {

				finalQuery += selectValues + " FROM "
						+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
						+ ", BaselineTreatment AS bt WHERE " + initialWhere;
			} else {

				finalQuery += selectValues + " FROM "
						+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
						+ " WHERE " + initialWhere;
			}

			String whereConditionStr = "";

			// column , table, criteria loop
			for (int i = 0; i < colNameArr.length; i++) {

				String[] columnNameArray = colNameArr[i].split("=");

				String columnName = columnNameArray[1];
				String dataTyepe = columnNameArray[0];

				String[] array = columnName.split("\\.");
				String alias = array[0];

				String[] criteriaArray = criteriaNameArr[i].split("___");

				String criteria = criteriaArray[0];
				String fkeyAttribute = "";

				if (criteriaArray.length > 1) {
					fkeyAttribute = criteriaArray[1];
				}

				String tableName = "";

				Set<String> keySet = selectMap.keySet();

				for (String tableNameKey : keySet) {
					if (tableNameKey.endsWith(alias)) {
						tableName = tableNameKey;
						break;
					}
				}

				String selectedValues = selectMap.get(tableName);

				if (fkeyAttribute == "" || fkeyAttribute.isEmpty()) {
					whereConditionStr += " AND " + columnName + " " + criteria;
				} else {

					String fkeyAttributeArray[] = fkeyAttribute.split("=");

					String fkeyColName = fkeyAttributeArray[1];
					String fkeyTableName = fkeyAttributeArray[0];

					String skeyTableAlias = fkeyTableName.split(" AS ")[1];

					whereConditionStr += " AND " + columnName + " IN (SELECT " + skeyTableAlias + ".id FROM "
							+ fkeyTableName + " WHERE " + skeyTableAlias + "." + fkeyColName + " " + criteria + ")";
				}
			}

			if (whereConditionStr.startsWith(" AND ")) {
				whereConditionStr = whereConditionStr.substring(5);
			}

			finalQuery += whereConditionStr;

			for (int i = 0; i < searchTextArr.length; i++) {

				String array[] = searchTextArr[i].split("---");
				String tableName = array[0];
				String colyumnname = array[1];
				String searchValue = array[2];

				int placeholderIndex = finalQuery.indexOf("?");

				finalQuery = finalQuery.substring(0, placeholderIndex) + searchValue
						+ finalQuery.substring(placeholderIndex + 1);
			}

			finalQuery = finalQuery + " AND pt.practiceID = " + patientForm.getPracticeID();

			if (finalQuery.contains("Visit AS vt")) {

				finalQuery = finalQuery + " AND vt.clinicID = " + patientForm.getClinicID();
			}

			System.out.println("finalQuery: " + finalQuery);

			patientForm.setSavedQuery(finalQuery);

			statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

			if (statusMessage.equals("success")) {

				File inputFile = new File(realPath + "/" + excelFileName);

				try {

					// Storing file to S3 RDML INPUT FILE location
					String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
							s3reportFilePath);
					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
							.getObjectContent();

					patientForm.setFileInputStream(s3ObjectInputStream);

					patientForm.setFileName(excelFileName);

				} catch (Exception e) {
					statusMessage = "exception";

					e.printStackTrace();
				}

			} else {

				System.out.println("Failed to generate excel file for clinica data report.");

				statusMessage = "error";

			}

		} else if (patientForm.getQueryTitle().isEmpty()) {

			System.out.println("Query not saved");
			statusMessage = "success";

			int savedQuewryID;

			String finalQuery = "SELECT ";

			String selectValues = "";
			// sleect column name loop

			String[] selectNameArr = patientForm.getSelectNameArr();
			String[] tableNameArr = patientForm.getTableNameArr();
			String initialWhere = patientForm.getInitialWhere();
			String[] colNameArr = patientForm.getColNameArr();
			String[] criteriaNameArr = patientForm.getCriteriaNameArr();
			String[] searchTextArr = patientForm.getSearchTextArr();

			for (int i = 0; i < selectNameArr.length; i++) {
				String[] array = selectNameArr[i].split("==");

				String tableName = array[0];
				String selectValue = array[1];

				selectMap.put(tableName, selectValue);

				if (selectValue.trim() == "" || selectValue.trim().isEmpty()) {
					continue;
				} else {
					selectValues += "," + selectValue;
				}
			}

			if (selectValues.startsWith(",")) {
				selectValues = selectValues.substring(1);
			}

			String totalTableName = "";

			for (int i = 0; i < tableNameArr.length; i++) {
				totalTableName = totalTableName + ", " + tableNameArr[i];
			}

			if (totalTableName.startsWith(",")) {
				totalTableName = totalTableName.substring(2);
			}

			tableNameArr = totalTableName.split(", ");

			tableNameArr = new HashSet<String>(Arrays.asList(tableNameArr)).toArray(new String[0]);

			String initialWhereCondition = "";

			String[] initialWhereArr = initialWhere.split(" AND ");
			String[] uniqueVal = new HashSet<String>(Arrays.asList(initialWhereArr)).toArray(new String[0]);
			for (int i = 0; i < uniqueVal.length; i++) {

				initialWhereCondition = initialWhereCondition + uniqueVal + " AND ";
			}

			System.out.println("initialWhereCondition: " + initialWhereCondition);

			if (initialWhere.contains("bt.")) {

				finalQuery += selectValues + " FROM "
						+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
						+ ", BaselineTreatment AS bt WHERE " + initialWhere;
			} else {

				finalQuery += selectValues + " FROM "
						+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
						+ " WHERE " + initialWhere;
			}

			String whereConditionStr = "";

			// column , table, criteria loop
			for (int i = 0; i < colNameArr.length; i++) {

				String[] columnNameArray = colNameArr[i].split("=");

				String columnName = columnNameArray[1];
				String dataTyepe = columnNameArray[0];

				String[] arrayVal = columnName.split("\\.");

				String alias = arrayVal[0];

				String[] criteriaArray = criteriaNameArr[i].split("___");

				String criteria = criteriaArray[0];
				String fkeyAttribute = "";

				if (criteriaArray.length > 1) {
					fkeyAttribute = criteriaArray[1];
				}

				String tableName = "";

				Set<String> keySet = selectMap.keySet();

				for (String tableNameKey : keySet) {
					if (tableNameKey.endsWith(alias)) {
						tableName = tableNameKey;
						break;
					}
				}

				String selectedValues = selectMap.get(tableName);

				if (fkeyAttribute == "" || fkeyAttribute.isEmpty()) {
					whereConditionStr += " AND " + columnName + " " + criteria;
				} else {

					String fkeyAttributeArray[] = fkeyAttribute.split("=");

					String fkeyColName = fkeyAttributeArray[1];
					String fkeyTableName = fkeyAttributeArray[0];

					String skeyTableAlias = fkeyTableName.split(" AS ")[1];

					whereConditionStr += " AND " + columnName + " IN (SELECT " + skeyTableAlias + ".id FROM "
							+ fkeyTableName + " WHERE " + skeyTableAlias + "." + fkeyColName + " " + criteria + ")";
				}

			}

			if (whereConditionStr.startsWith(" AND ")) {
				whereConditionStr = whereConditionStr.substring(5);
			}

			finalQuery += whereConditionStr;

			for (int i = 0; i < searchTextArr.length; i++) {

				String array[] = searchTextArr[i].split("---");
				String tableName = array[0];
				String colyumnname = array[1];
				String searchValue = array[2];

				int placeholderIndex = finalQuery.indexOf("?");

				finalQuery = finalQuery.substring(0, placeholderIndex) + searchValue
						+ finalQuery.substring(placeholderIndex + 1);
			}

			finalQuery = finalQuery + " AND pt.practiceID = " + patientForm.getPracticeID();

			if (finalQuery.contains("Visit AS vt")) {

				finalQuery = finalQuery + " AND vt.clinicID = " + patientForm.getClinicID();
			}

			System.out.println("finalQuery: " + finalQuery);

			patientForm.setSavedQuery(finalQuery);

			statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

			if (statusMessage.equals("success")) {

				// patientForm.setFileName(excelFileName);
				File inputFile = new File(realPath + "/" + excelFileName);

				try {

					// patientForm.setFileInputStream(new FileInputStream(new File(reportFilePath +
					// excelFileName)));
					// Storing file to S3 RDML INPUT FILE location
					String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
							s3reportFilePath);
					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
							.getObjectContent();
					patientForm.setFileInputStream(s3ObjectInputStream);

					patientForm.setFileName(excelFileName);

				} catch (Exception e) {

					statusMessage = "exception";

					e.printStackTrace();
				}

			} else {

				System.out.println("Failed to generate excel file for clinica data report.");

				statusMessage = "error";

			}

		} else {

			System.out.println("Inside else");

			statusMessage = reportDAOInf.insertSavedQuery(patientForm);

			if (statusMessage.equals("success")) {

				int savedQuewryID = reportDAOInf.retrieveSavedQuewryID(patientForm);

				String finalQuery = "SELECT ";

				String selectValues = "";

				String[] selectNameArr = patientForm.getSelectNameArr();
				String[] tableNameArr = patientForm.getTableNameArr();
				String initialWhere = patientForm.getInitialWhere();
				String[] colNameArr = patientForm.getColNameArr();
				String[] criteriaNameArr = patientForm.getCriteriaNameArr();
				String[] searchTextArr = patientForm.getSearchTextArr();

				System.out.println("initial where 1" + patientForm.getInitialWhere());

				// sleect column name loop
				for (int i = 0; i < selectNameArr.length; i++) {
					String[] array = selectNameArr[i].split("==");

					String tableName = array[0];
					String selectValue = array[1];

					selectMap.put(tableName, selectValue);

					if (selectValue.trim() == "" || selectValue.trim().isEmpty()) {
						continue;
					} else {
						selectValues += "," + selectValue;
					}

				}

				if (selectValues.startsWith(",")) {
					selectValues = selectValues.substring(1);
				}

				String totalTableName = "";

				for (int i = 0; i < tableNameArr.length; i++) {
					totalTableName = totalTableName + ", " + tableNameArr[i];
				}

				if (totalTableName.startsWith(",")) {
					totalTableName = totalTableName.substring(2);
				}

				tableNameArr = totalTableName.split(", ");

				tableNameArr = new HashSet<String>(Arrays.asList(tableNameArr)).toArray(new String[0]);

				String initialWhereCondition = "";

				String[] initialWhereArr = initialWhere.split(" AND ");
				String[] uniqueVal = new HashSet<String>(Arrays.asList(initialWhereArr)).toArray(new String[0]);
				for (int i = 0; i < uniqueVal.length; i++) {

					initialWhereCondition = initialWhereCondition + uniqueVal + " AND ";
				}

				System.out.println("initialWhereCondition: " + initialWhereCondition);

				if (initialWhere.contains("bt.")) {

					finalQuery += selectValues + " FROM "
							+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
							+ ", BaselineTreatment AS bt WHERE " + initialWhere;
				} else {

					finalQuery += selectValues + " FROM "
							+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
							+ " WHERE " + initialWhere;
					System.out.println("final query::" + finalQuery);
				}

				String whereConditionStr = "";

				// column , table, criteria loop
				for (int i = 0; i < colNameArr.length; i++) {

					String[] columnNameArray = colNameArr[i].split("=");

					String columnName = columnNameArray[1];
					String dataType = columnNameArray[0];

					if (dataType.equals("NA")) {
						String tableName = columnName;
						columnName = dataType;

						String selectedValues = selectMap.get(tableName);

						/*
						 * insert tablename, columnname, datatype, selectedvalue, criteria, savedQueryID
						 * into SAvedQueryParameter
						 */

						statusMessage = reportDAOInf.insertSavedQueryParameter(tableName, columnName, null,
								selectedValues, null, savedQuewryID);

						if (statusMessage.equals("success")) {

							System.out.println("Successfully inserted values to SavedQueryParameter table.");

						} else {

							System.out.println("Failed to insert values to SavedQueryParameter table.");

							statusMessage = "error";
						}

					} else {

						String[] array = columnName.split("\\.");
						String alias = array[0];

						System.out.println("criteriaNameArr: " + criteriaNameArr);
						String[] criteriaArray = criteriaNameArr[i].split("___");

						String criteria = criteriaArray[0];
						String fkeyAttribute = "";

						if (criteriaArray.length > 1) {
							fkeyAttribute = criteriaArray[1];
						}

						String tableName = "";

						Set<String> keySet = selectMap.keySet();

						for (String tableNameKey : keySet) {
							if (tableNameKey.endsWith("AS " + alias)) {
								tableName = tableNameKey;
								break;
							}
						}

						String selectedValues = selectMap.get(tableName);

						/*
						 * insert tablename, columnname, datatype, selectedvalue, criteria, savedQueryID
						 * into SAvedQueryParameter
						 */

						statusMessage = reportDAOInf.insertSavedQueryParameter(tableName, columnName, dataType,
								selectedValues, criteria, savedQuewryID);

						if (statusMessage.equals("success")) {

							System.out.println("Successfully inserted values to SavedQueryParameter table.");

						} else {

							System.out.println("Failed to insert values to SavedQueryParameter table.");

							statusMessage = "error";
						}

						if (fkeyAttribute == "" || fkeyAttribute.isEmpty()) {
							whereConditionStr += " AND " + columnName + " " + criteria;
						} else {

							String fkeyAttributeArray[] = fkeyAttribute.split("=");

							String fkeyColName = fkeyAttributeArray[1];
							String fkeyTableName = fkeyAttributeArray[0];

							String skeyTableAlias = fkeyTableName.split(" AS ")[1];

							whereConditionStr += " AND " + columnName + " IN (SELECT " + skeyTableAlias + ".id FROM "
									+ fkeyTableName + " WHERE " + skeyTableAlias + "." + fkeyColName + " " + criteria
									+ ")";
						}

					}

				}

				if (whereConditionStr.startsWith(" AND ")) {
					whereConditionStr = whereConditionStr.substring(5);
				}

				finalQuery += whereConditionStr;

				for (int i = 0; i < searchTextArr.length; i++) {

					String array[] = searchTextArr[i].split("---");
					String tableName = array[0];
					String columnName = array[1];
					String searchValue = array[2];

					System.out.println("tableName: " + tableName + "==" + columnName + "==" + savedQuewryID);

					/*
					 * retrieve savedQueryParameterID by tableName and columnName and last inserted
					 * savedqueryID
					 */
					int savedQueryParameterID = reportDAOInf.retrieveSavedQuewryParameterID(tableName, columnName,
							savedQuewryID);

					System.out.println("savedQueryParameterID: " + savedQueryParameterID + "==" + searchValue);
					/* insert into SavedQueryParameterValue */

					statusMessage = reportDAOInf.insertSavedQueryParameterValue(searchValue, savedQueryParameterID);

					if (statusMessage.equals("success")) {

						System.out.println("Successfully inserted values to SavedQueryParameterValue table.");

					} else {

						System.out.println("Failed to insert values to SavedQueryParameterValue table.");

						statusMessage = "error";
					}

					int placeholderIndex = finalQuery.indexOf("?");

					finalQuery = finalQuery.substring(0, placeholderIndex) + searchValue
							+ finalQuery.substring(placeholderIndex + 1);
					System.out.println("Query: " + finalQuery);
				}

				finalQuery = finalQuery + " AND pt.practiceID = " + patientForm.getPracticeID();

				if (finalQuery.contains("Visit AS vt")) {

					finalQuery = finalQuery + " AND vt.clinicID = " + patientForm.getClinicID();
				}

				System.out.println("finalQuery: " + finalQuery);

				patientForm.setSavedQuery(finalQuery);

				statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

				if (statusMessage.equals("success")) {

					// patientForm.setFileName(excelFileName);
					File inputFile = new File(realPath + "/" + excelFileName);

					try {

						// patientForm.setFileInputStream(new FileInputStream(new File(reportFilePath +
						// excelFileName)));
						// Storing file to S3 RDML INPUT FILE location
						String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
								s3reportFilePath);
						S3ObjectInputStream s3ObjectInputStream = s3
								.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
								.getObjectContent();
						patientForm.setFileInputStream(s3ObjectInputStream);

						patientForm.setFileName(excelFileName);

					} catch (Exception e) {

						StringWriter stringWriter = new StringWriter();

						e.printStackTrace(new PrintWriter(stringWriter));

						// calling exception mail send method to send mail about the exception details
						// on info@kovidbioanalytics.com
						EmailUtil emailUtil1 = new EmailUtil();
						emailUtil1.sendExceptionInfoEmail(stringWriter.toString(),
								"Geterate Clinical Data Report: Exception");

						statusMessage = "exception";

						e.printStackTrace();
					}

				} else {

					System.out.println("Failed to generate excel file for clinica data report.");

					statusMessage = "error";

				}

				return statusMessage;

			} else {

				System.out.println("Failed to insert saved query into SaveQuery table.");

				statusMessage = "error";

				return statusMessage;

			}

		}

		return statusMessage;

	}

	public String executeClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath) {

		reportDAOInf = new ReportDAOImpl();

		ExcelUtil excelUtil = new ExcelUtil();

		ConfigurationUtil util = new ConfigurationUtil();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

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

		HashMap<String, String> saveQueryDetails = null;

		System.out.println("...savedQueryID.." + patientForm.getSaveQueryID());

		String initialWhereCondition = patientForm.getInitialWhere();

		System.out.println("..gane.." + initialWhereCondition);

		saveQueryDetails = reportDAOInf.retrieveSavedQueryDetailsList(patientForm.getSaveQueryID());

		List<String> SearchTextList = reportDAOInf.retrieveSavedQuerySearchText(patientForm.getSaveQueryID());

		String SelectValue = saveQueryDetails.get("selectedValues");
		String TableValue = saveQueryDetails.get("tableNameValues");

		String[] tableNameArr = TableValue.split(", ");
		tableNameArr = new HashSet<String>(Arrays.asList(tableNameArr)).toArray(new String[0]);

		String ColCriteriaValue = saveQueryDetails.get("colCriteriaValues");

		String finalQuery = "SELECT ";

		if (initialWhereCondition.contains("bt.")) {
			finalQuery += SelectValue + " FROM "
					+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1)
					+ ", BaselineTreatment AS bt WHERE " + initialWhereCondition + " " + ColCriteriaValue;
		} else {
			finalQuery += SelectValue + " FROM "
					+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1) + " WHERE "
					+ initialWhereCondition + " " + ColCriteriaValue;
		}

		for (String searchValue : SearchTextList) {

			int placeholderIndex = finalQuery.indexOf("?");

			finalQuery = finalQuery.substring(0, placeholderIndex) + searchValue
					+ finalQuery.substring(placeholderIndex + 1);
		}

		finalQuery = finalQuery + " AND pt.practiceID = " + patientForm.getPracticeID();

		if (finalQuery.contains("Visit AS vt")) {

			finalQuery = finalQuery + " AND vt.clinicID = " + patientForm.getClinicID();
		}

		patientForm.setSavedQuery(finalQuery);

		statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

		if (statusMessage.equals("success")) {

			// patientForm.setFileName(excelFileName);
			File inputFile = new File(realPath + "/" + excelFileName);

			try {

				// Storing file to S3 RDML INPUT FILE location
				String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
						s3reportFilePath);

				S3ObjectInputStream s3ObjectInputStream = s3
						.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
						.getObjectContent();

				patientForm.setFileInputStream(s3ObjectInputStream);

				patientForm.setFileName(excelFileName);

			} catch (Exception e) {

				statusMessage = "exception";

				e.printStackTrace();
			}

		} else {

			System.out.println("Failed to generate excel file for clinica data report.");

			statusMessage = "error";

		}

		return statusMessage;
	}

	public String updateClinicalDataReport(PatientForm patientForm, String excelFileName, String realPath) {

		reportDAOInf = new ReportDAOImpl();

		ExcelUtil excelUtil = new ExcelUtil();

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

		HashMap<String, String> saveQueryDetails = null;

		System.out.println("SavedQuery: " + patientForm.getSaveQueryID() + "--" + patientForm.getQueryTitle());

		/* Update query title in SavedQuery table */
		statusMessage = reportDAOInf.updateSavedQueryTitle(patientForm.getSaveQueryID(), patientForm.getQueryTitle());
		if (statusMessage.equals("success")) {

			System.out.println("Successfully updated SavedQuery title for clinical data report.");

		} else {

			System.out.println("Failed to update SavedQuery title for clinical data report.");

			statusMessage = "error";
		}

		/* Update selectValues in SavedQueryParameter table */
		if (patientForm.getSavedQueryParameterIDArr() == null) {

			System.out.println("No SavedQueryParameter data found");
			statusMessage = "success";

			return statusMessage;
		} else {

			for (int i = 0; i < patientForm.getSavedQueryParameterIDArr().length; i++) {

				statusMessage = reportDAOInf.updateSavedQueryParameter(
						Integer.parseInt(patientForm.getSavedQueryParameterIDArr()[i]),
						patientForm.getSelectNameArr()[i]);

				if (statusMessage.equals("success")) {

					System.out.println("Successfully updated SavedQueryParameter details for clinical data report.");

				} else {

					System.out.println("Failed to update SavedQueryParameter details for clinical data report.");

					statusMessage = "error";
				}
			}
		}

		/* Update searchText values in SavedQueryParameterValues table */
		if (patientForm.getSavedQueryParameterValueIDArr() == null) {

			System.out.println("No SavedQueryParameterValue data found");
			statusMessage = "success";

			return statusMessage;
		} else {

			for (int i = 0; i < patientForm.getSavedQueryParameterValueIDArr().length; i++) {

				statusMessage = reportDAOInf.updateSavedQueryParameterValues(
						Integer.parseInt(patientForm.getSavedQueryParameterValueIDArr()[i]),
						patientForm.getSearchTextArr()[i]);

				if (statusMessage.equals("success")) {

					System.out.println("Successfully updated SavedQueryParameter details for clinical data report.");

				} else {

					System.out.println("Failed to update SavedQueryParameter details for clinical data report.");

					statusMessage = "error";
				}
			}
		}

		String initialWhereCondition = patientForm.getInitialWhere();

		System.out.println("..savedQueryID.." + patientForm.getSaveQueryID() + "---" + initialWhereCondition);

		saveQueryDetails = reportDAOInf.retrieveSavedQueryDetailsList(patientForm.getSaveQueryID());

		List<String> SearchTextList = reportDAOInf.retrieveSavedQuerySearchText(patientForm.getSaveQueryID());

		String SelectValue = saveQueryDetails.get("selectedValues");
		String TableValue = saveQueryDetails.get("tableNameValues");
		String ColCriteriaValue = saveQueryDetails.get("colCriteriaValues");

		String[] tableNameArr = TableValue.split(", ");
		tableNameArr = new HashSet<String>(Arrays.asList(tableNameArr)).toArray(new String[0]);

		String finalQuery = "SELECT ";

		if (initialWhereCondition.contains("bt.")) {
			finalQuery += SelectValue + " FROM "
					+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1) + ""
					+ ", BaselineTreatment AS bt WHERE " + initialWhereCondition + " " + ColCriteriaValue;
		} else {
			finalQuery += SelectValue + " FROM "
					+ Arrays.toString(tableNameArr).substring(1, Arrays.toString(tableNameArr).length() - 1) + ""
					+ " WHERE " + initialWhereCondition + " " + ColCriteriaValue;
		}

		for (String searchValue : SearchTextList) {

			int placeholderIndex = finalQuery.indexOf("?");

			finalQuery = finalQuery.substring(0, placeholderIndex) + searchValue
					+ finalQuery.substring(placeholderIndex + 1);
		}

		finalQuery = finalQuery + " AND pt.practiceID = " + patientForm.getPracticeID();

		if (finalQuery.contains("Visit AS vt")) {

			finalQuery = finalQuery + " AND vt.clinicID = " + patientForm.getClinicID();
		}

		System.out.println("finalQueryNew : " + finalQuery);

		patientForm.setSavedQuery(finalQuery);

		statusMessage = excelUtil.generateClinicalReport(patientForm, realPath, excelFileName);

		if (statusMessage.equals("success")) {

			// patientForm.setFileName(excelFileName);
			File inputFile = new File(realPath + "/" + excelFileName);

			try {

				// patientForm.setFileInputStream(new FileInputStream(new File(reportFilePath +
				// excelFileName)));
				// Storing file to S3 RDML INPUT FILE location
				String message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion,
						s3reportFilePath);
				S3ObjectInputStream s3ObjectInputStream = s3
						.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName))
						.getObjectContent();
				patientForm.setFileInputStream(s3ObjectInputStream);

				patientForm.setFileName(excelFileName);

			} catch (Exception e) {

				statusMessage = "exception";

				e.printStackTrace();
			}

		} else {

			System.out.println("Failed to generate excel file for clinica data report.");

			statusMessage = "error";

		}

		return statusMessage;
	}

	public String configureConsentDetails(PatientForm patientForm, int userID) {

		patientDAOInf = new PatientDAOImpl();

		int status = 0;
		/*
		 * Verifying whether userID is already exist into UsageConsent table or not, if
		 * not then only proceed for insertion else update.
		 */
		status = patientDAOInf.verifyConsentDetailsExist(userID);

		if (status == 0) {

			statusMessage = patientDAOInf.addConsentDetails(patientForm, userID);

			if (statusMessage.equals("success")) {
				System.out.println("Consent details inserted successfully into UsageConsent table");

				statusMessage = "success";
			} else {
				System.out.println("Failed to insert Consent details into UsageConsent table.");

				statusMessage = "error";
			}

		} else {

			statusMessage = "success";

		}

		return statusMessage;

	}

	public String addRoomType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether room type with same name already exists or not, if exists
		// then giving error message else proceeding further to add the new room type
		boolean check = prescriptionManagementDAOInf.verifyRoomTypeExists(form.getRoomType(), form.getPracticeID());

		if (check) {

			System.out.println("Same room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVIPDROomType table
			statusMessage = prescriptionManagementDAOInf.insertRoomType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert room type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	public String editRoomType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether room type with same name already exists or not for the
		// other roomTypeID, if exists
		// then giving error message else proceeding further to add the new room type
		boolean check = prescriptionManagementDAOInf.verifyRoomTypeExists(form.getRoomType(), form.getRoomTypeID(),
				form.getPracticeID());

		if (check) {

			System.out.println("Same room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVIPDROomType table
			statusMessage = prescriptionManagementDAOInf.updateRoomType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update room type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	public String addIPDCharges(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether IPD charges with same itemName and roomTypeID already
		// exists into
		// PVIPDCharges table if yes then give error message else proceed further to add
		// the details into PVIPDCharges table
		boolean check = prescriptionManagementDAOInf.verifyIPDChargesExists(form.getIPDItems(), form.getRoomTypeID(),
				form.getPracticeID());

		if (check) {

			System.out.println("IPD tarrif charges with same item and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVIPDROomType table
			statusMessage = prescriptionManagementDAOInf.insertIPDCharges(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert ipd tarrif charges Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	public String editIPDCharges(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// verifying whether ipd tarrif charges with same item name and roomType already
		// exists for the other available ipd charges if yes then give error message
		// else proceed further to update the details in PVIPDCharges table for the
		// corresponding chargeID
		boolean check = prescriptionManagementDAOInf.verifyIPDChargesExists(form.getIPDItems(), form.getRoomTypeID(),
				form.getIPDChargeID(), form.getPracticeID());

		if (check) {

			System.out.println("IPD tarrif charges with same item and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVIPDROomType table
			statusMessage = prescriptionManagementDAOInf.updateIPDCharges(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update IPD charges Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	public String addIPDConsultantCharges(PrescriptionManagementForm form, int practiceID) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether IPD charges with same doctor name and roomTypeID already
		// exists into
		// PVIPDConsultantCharges table if yes then give error message else proceed
		// further to add
		// the details into PVIPDConsultantCharges table
		boolean check = prescriptionManagementDAOInf.verifyIPDConsultantChargesExists(form.getDoctorName(),
				form.getRoomTypeID(), practiceID);

		if (check) {

			System.out.println("IPD consultant charges with same doctor name and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			statusMessage = prescriptionManagementDAOInf.insertIPDConsultantCharges(form, practiceID);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert ipd cosultant charges Details.");

				statusMessage = "error";

				return statusMessage;
			}
		}
	}

	public String editIPDConsultantCharges(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// verifying whether ipd consultant charges with same doctor name and roomType
		// already
		// exists for the other available ipd consultant charges if yes then give error
		// message
		// else proceed further to update the details in PVIPDConsultantCharges table
		// for the
		// corresponding consultantID
		boolean check = prescriptionManagementDAOInf.verifyIPDConsultantChargesExists(form.getDoctorName(),
				form.getRoomTypeID(), form.getConsultationChargeID(), form.getPracticeID());

		if (check) {

			System.out.println("IPD consultant charges with same doctor name and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			statusMessage = prescriptionManagementDAOInf.updateIPDConsultantCharges(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update IPD consultant charges Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	public String addTestDetails(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether test with same name already exists into PVLabTest table or
		 * not, if so, then give error msg else proceed further to add PVLabTest details
		 * into PVLabTest table
		 */

		boolean check = configurationDAOInf.verifyTestAlreadyExists(configurationForm.getTest());

		if (check) {

			System.out.println("Test name already exists into PVLabTest table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting test details into PVLabTest table
			 */
			statusMessage = configurationDAOInf.insertTestDetails(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert Test details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editTestDetails(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether test with same name already exists into PVLabTest table or
		 * not, if so, then give error msg else proceed further to add PVLabTest details
		 * into PVLabTest table
		 */

		boolean check = configurationDAOInf.verifyEditTestAlreadyExists(configurationForm.getTest(),
				configurationForm.getTestID());

		if (check) {

			System.out.println("Test name already exists into PVLabTest table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting test details into PVLabTest table
			 */
			statusMessage = configurationDAOInf.updateTestsDetails(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update Test Details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addOPDCharges(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyChargeTypeNameAlreadyExists(form.getChargeType(),
				form.getPracticeID());

		if (check) {

			System.out.println("ChargeType Name already exists into PVOPDCharges table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.insertOPDChargeDetails(form.getChargeType(), form.getCharges(),
					form.getPracticeID());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert OPDCharges details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editOPDChargesDetails(PrescriptionManagementForm form) {

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = prescriptionManagementDAOInf.verifyEditChargeTypeAlreadyExists(form.getChargeType(),
				form.getChargesID(), form.getPracticeID());

		if (check) {

			System.out.println("ChargeType Name already exists into PVOPDCharges table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = prescriptionManagementDAOInf.updateOPDChargesDateils(form.getChargeType(),
					form.getCharges(), form.getChargesID());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update OPDCharges details.");

				statusMessage = "error";

				return statusMessage;

			}
		}

	}

	public String addGeneralHospitalVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertPatientVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			int lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber,
					patientForm.getPatientID(), patientForm.getClinicID());

			// Setting lastVisitID into visitID variable of PatientForm
			patientForm.setVisitID(lastVisitID);

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean medHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "MedicalHistory");

			if (medHistCheck) {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updateMedicalHistory(patientForm);

			} else {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.insertMedicalHistory(patientForm);

			}
			if (statusMessage.equals("success")) {

				/*
				 * Check whether data with same patientID already exists into MedicalHistory
				 * table, if, so then update it, else insert it
				 */
				boolean famHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "FamilyHistory");

				if (famHistCheck) {

					/*
					 * inserting family history details into FamilyHistory table
					 */
					statusMessage = patientDAOInf.updateFamilyHistory(patientForm);

				} else {

					/*
					 * inserting family history details into FamilyHistory table
					 */
					statusMessage = patientDAOInf.insertFamilyHistory(patientForm);

				}

				if (statusMessage.equals("success")) {

					/*
					 * Check whether data with same patientID already exists into MedicalHistory
					 * table, if, so then update it, else insert it
					 */
					boolean persHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(),
							"PersonalHistory");

					if (persHistCheck) {

						// inserting habit details into PersonalHistory table
						statusMessage = patientDAOInf.updateGeneralHospitalPersonalHistory(patientForm);

					} else {

						// inserting habit details into PersonalHistory table
						statusMessage = patientDAOInf.insertGeneralHospitalPersonalHistory(patientForm);

					}

					if (statusMessage.equals("success")) {

						// splitting complaints string using , (comma) and iterating over each complaint
						// and insert each into PresentComplaints table
						String complaints = patientForm.getComplaints();
						String finalComplaints = "";
						String finalComplaintsNew = "";

						// check if present complaints added or not, if not then proceed further else
						// split using comma and iterate over it to insert in PresentComplaints table
						if (complaints == null || complaints == "") {

							System.out.println("No complaints selected");

							statusMessage = "success";

							// check whether other complaints added or not, if yes then iterating over
							// otherComplaints array and inserting each into PresentComplaints table
							if (patientForm.getOtherComplaint() == null) {

								System.out.println("No complaints selected");

								statusMessage = "success";

							} else {

								for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

									String otherComplaint = patientForm.getOtherComplaint()[i];

									finalComplaints = complaints + "," + otherComplaint;

									boolean OtherComplaintCheck = patientDAOInf
											.verifyOtherComplaintDataExists(otherComplaint);

									if (OtherComplaintCheck) {
										System.out.println("Other Complaints already exist.");
										statusMessage = "success";
									} else {
										// inserting present complaints details into PresentComplaints
										statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

									}
								}

								if (finalComplaints.startsWith(",")) {
									finalComplaintsNew = finalComplaints.substring(1);
								}
								statusMessage = patientDAOInf.insertPresentComplaints(finalComplaintsNew,
										patientForm.getVisitID());

								if (!statusMessage.equals("success")) {
									System.out.println(
											"Failed to add present complaints details into PresentComplaints table");

									statusMessage = "error";
								}

							}

						} else if (complaints.isEmpty()) {

							System.out.println("No complaints selected");

							statusMessage = "success";

							// check whether other complaints added or not, if yes then iterating over
							// otherComplaints array and inserting each into PresentComplaints table
							if (patientForm.getOtherComplaint() == null) {

								System.out.println("No complaints selected");

								statusMessage = "success";

							} else {

								for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

									String otherComplaint = patientForm.getOtherComplaint()[i];

									finalComplaints = complaints + "," + otherComplaint;

									boolean OtherComplaintCheck = patientDAOInf
											.verifyOtherComplaintDataExists(otherComplaint);

									if (OtherComplaintCheck) {
										System.out.println("Other Complaints already exist.");
										statusMessage = "success";
									} else {
										// inserting present complaints details into PresentComplaints
										statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

									}
								}

								if (finalComplaints.startsWith(",")) {
									finalComplaintsNew = finalComplaints.substring(1);
								}
								statusMessage = patientDAOInf.insertPresentComplaints(finalComplaintsNew,
										patientForm.getVisitID());

								if (!statusMessage.equals("success")) {
									System.out.println(
											"Failed to add present complaints details into PresentComplaints table");

									statusMessage = "error";
								}

							}

						} else {

							// check whether other complaints added or not, if yes then iterating over
							// otherComplaints array and inserting each into PresentComplaints table
							if (patientForm.getOtherComplaint() == null) {

								System.out.println("No complaints selected");

								statusMessage = patientDAOInf.insertPresentComplaints(complaints,
										patientForm.getVisitID());

								statusMessage = "success";

							} else {

								for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

									String otherComplaint = patientForm.getOtherComplaint()[i];

									finalComplaints = complaints + "," + otherComplaint;

									boolean OtherComplaintCheck = patientDAOInf
											.verifyOtherComplaintDataExists(otherComplaint);

									if (OtherComplaintCheck) {
										System.out.println("Other Complaints already exist.");
										statusMessage = "success";
									} else {
										// inserting present complaints details into PresentComplaints
										statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

									}
								}

								statusMessage = patientDAOInf.insertPresentComplaints(finalComplaints,
										patientForm.getVisitID());

								if (!statusMessage.equals("success")) {
									System.out.println(
											"Failed to add present complaints details into PresentComplaints table");

									statusMessage = "error";
								}

							}

							if (!statusMessage.equals("success")) {

								System.out.println(
										"Failed to add present complaints details into PresentComplaints table");
								statusMessage = "error";
							}

						}

						if (statusMessage.equals("success")) {

							// inserting vitals details into VitalSigns table
							statusMessage = patientDAOInf.insertVitalSigns(patientForm);

							if (statusMessage.equals("success")) {

								// inserting rs, cvs, cns and abdomen details into OnExamination table
								statusMessage = patientDAOInf.insertOnExaminationDetails(patientForm);

								if (statusMessage.equals("success")) {

									// inserting significant findings details into Investigation table
									statusMessage = patientDAOInf.insertInvestigationDetails(
											patientForm.getBiopsyFindings(), patientForm.getVisitID(), null,
											"Significant Findings", null);

									if (statusMessage.equals("success")) {

										return statusMessage;

									} else {

										System.out.println(
												"Failed to add significant findings details into Investigation table");

										statusMessage = "error";

										return statusMessage;

									}

								} else {

									System.out.println("Failed to add examination details into OnExamination table");

									statusMessage = "error";

									return statusMessage;

								}

							} else {

								System.out.println("Failed to add vital details into VitalSigns table.");

								statusMessage = "error";

								return statusMessage;

							}

						} else {

							System.out.println("Failed to insert present complaints details into PresentComplaints.");

							statusMessage = "error";

							return statusMessage;

						}

					} else {

						System.out.println("Failed to insert habit details into PersonalHistory table.");

						statusMessage = "error";

						return statusMessage;

					}

				} else {

					System.out.println("Failed to insert family history details into FamilyHistory table.");

					statusMessage = "error";

					return statusMessage;

				}

			} else {

				System.out.println("Failed to insert past history details into MedicalHistory table.");

				statusMessage = "error";

				return statusMessage;

			}

		} else {

			System.out.println("Failed to insert visit details into Visit table.");

			statusMessage = "error";

			return statusMessage;

		}

	}

	public String addNewGeneralHospitalPrescription(PatientForm patientForm) {
		patientDAOInf = new PatientDAOImpl();

		System.out.println("inside addNewGeneralHospitalPrescription.........");

		/*
		 * Check whether visit added before adding prescription details, if added then
		 * only proceed further to add prescription details into Prescription table else
		 * give error message saying visit not added
		 */
		if (patientForm.getVisitID() == 0) {

			System.out.println("No visit added.");

			statusMessage = "noVisit";

			return statusMessage;

		} else {

			boolean weightCheck = patientDAOInf.verifyDataExistsInvestigation(patientForm.getVisitID(), "VitalSigns");
			System.out.println("weightCheck" + weightCheck);
			if (weightCheck) {

				statusMessage = patientDAOInf.updateGeneralHospitalVitalSignsWeight(patientForm.getWeight(),
						patientForm.getVisitID());
			} else {
				statusMessage = patientDAOInf.insertGeneralHospitalVitalSignsWeight(patientForm.getWeight(),
						patientForm.getVisitID());
			}

			if (statusMessage.equals("success")) {
				System.out.println("Weight added into VitalSighns table");

				boolean PrescribedInvestigatiosCheck = patientDAOInf
						.verifyDataExistsInvestigation(patientForm.getVisitID(), "PrescribedInvestigations");

				if (PrescribedInvestigatiosCheck) {

					if (patientForm.getInvestigation() == null || patientForm.getInvestigation() == "") {
						System.out.println("-------inside update if.......");
						statusMessage = patientDAOInf
								.deleteGeneralHospitalPrescribedInvestigatios(patientForm.getVisitID());
					} else if (patientForm.getInvestigation().isEmpty()) {
						System.out.println("...........inside update else.......");
						statusMessage = patientDAOInf
								.deleteGeneralHospitalPrescribedInvestigatios(patientForm.getVisitID());
					} else {
						statusMessage = patientDAOInf.updateGeneralHospitalPrescribedInvestigatios(patientForm,
								patientForm.getVisitID());
					}
				} else {

					if (patientForm.getInvestigation() == null || patientForm.getInvestigation() == "") {
						System.out.println("-------inside if.......");
					} else if (patientForm.getInvestigation().isEmpty()) {
						System.out.println("...........inside else.......");
					} else {
						statusMessage = patientDAOInf.insertGeneralHospitalPrescribedInvestigatios(patientForm,
								patientForm.getVisitID());
					}
				}

				if (statusMessage.equals("success")) {
					System.out.println(
							"Prescribed Investigatio details added successfully into PrescribedInvestigatios table");

					boolean PhysiotherapyCheck = patientDAOInf.verifyDataExistsInvestigation(patientForm.getVisitID(),
							"Physiotherapy");
					if (PhysiotherapyCheck) {
						statusMessage = patientDAOInf.updateGeneralHospitalPhysiotherapy(patientForm.getPhysiotherapy(),
								patientForm.getVisitID());
					} else {
						statusMessage = patientDAOInf.insertGeneralHospitalPhysiotherapy(patientForm.getPhysiotherapy(),
								patientForm.getVisitID());
					}

					if (statusMessage.equals("success")) {
						System.out.println("Physiotherapy details added successfully into Physiotherapy table");

						statusMessage = patientDAOInf.insertGeneralHospitalPrescriptionVisitDetail(patientForm,
								patientForm.getVisitID());

						if (statusMessage.equals("success")) {
							System.out.println("Prescription Visit details added successfully into Visit table");

							/*
							 * check whether prescription rows added or not and depending upon that
							 * proceeding further to insert records into Prescription table
							 */
							if (patientForm.getNewDrugID() == null) {
								System.out.println("No drugs added into prescription");

								statusMessage = "success";

								return statusMessage;
							} else {

								for (int i = 0; i < patientForm.getNewDrugID().length; i++) {

									String productID = patientForm.getNewDrugID()[i];
									String durationstr = patientForm.getNewDrugDuration()[i];

									double duration = 0;
									if (durationstr == null || durationstr == "") {
										duration = 0;
									} else if (durationstr.isEmpty()) {
										duration = 0;
									} else {
										duration = Double.parseDouble(durationstr);
									}

									String dosageBeforeStr = patientForm.getNewDrugDosageBefore()[i];
									double dosageBefore = 0;
									if (dosageBeforeStr == null || dosageBeforeStr == "") {
										dosageBefore = 0;
									} else if (dosageBeforeStr.isEmpty()) {
										dosageBefore = 0;
									} else {
										dosageBefore = Double.parseDouble(dosageBeforeStr);
									}

									String dosageAfterStr = patientForm.getNewDrugDosageAfter()[i];
									double dosageAfter = 0;
									if (dosageAfterStr == null || dosageAfterStr == "") {
										dosageAfter = 0;
									} else if (dosageAfterStr.isEmpty()) {
										dosageAfter = 0;
									} else {
										dosageAfter = Double.parseDouble(dosageAfterStr);
									}

									String dosageAfterDinnerStr = patientForm.getNewDrugDosageAfterDinner()[i];
									double dosageAfterDinner = 0;
									if (dosageAfterDinnerStr == null || dosageAfterDinnerStr == "") {
										dosageAfterDinner = 0;
									} else if (dosageAfterDinnerStr.isEmpty()) {
										dosageAfterDinner = 0;
									} else {
										dosageAfterDinner = Double.parseDouble(dosageAfterDinnerStr);
									}

									System.out.println("dosageBefore : " + dosageBefore + "--" + dosageAfter + "--"
											+ duration + "--" + dosageAfterDinner + "--"
											+ patientForm.getNewDrugDosageComment()[i] + "--"
											+ Integer.parseInt(productID));

									statusMessage = patientDAOInf.insertGeneralHospitalVisitPrescriptionDetails(
											Integer.parseInt(productID), dosageBefore, dosageAfter, duration,
											patientForm.getVisitID(), dosageAfterDinner,
											patientForm.getNewDrugDosageComment()[i]);
								}

								if (statusMessage.equals("success")) {
									System.out.println("Drug details added successfully into Prescription table");

									return statusMessage;

								} else {

									System.out.println("Failed to add drug details into prescription table.");

									statusMessage = "error";

									return statusMessage;

								}
							}

						} else {
							System.out.println("Failed to add Prescription Visit details into Visit table");
							statusMessage = "error";

							return statusMessage;
						}

					} else {
						System.out.println("Failed to add Physiotherapy details into Physiotherapy table");
						statusMessage = "error";

						return statusMessage;
					}
				} else {
					System.out.println(
							"Failed to add Prescribed Investigatio details into PrescribedInvestigatios table");
					statusMessage = "error";

					return statusMessage;
				}

			} else {
				System.out.println("Failed to add Weight into VitalSighns table");
				statusMessage = "error";

				return statusMessage;
			}

		}

	}

	public String configureGeneralHospitalBill(PatientForm form) {

		patientDAOInf = new PatientDAOImpl();

		if (form.getVisitID() == 0) {

			System.out.println("No visit added.");

			statusMessage = "noVisit";

			return statusMessage;

		} else {

			boolean visitCheck1 = patientDAOInf.verifyVisitExists("Receipt", form.getVisitID());

			if (visitCheck1) {

				/*
				 * update Billing details into Billing table
				 */

				statusMessage = patientDAOInf.updateBillDetails(form, form.getVisitID());
				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Retrieve previously entered receiptID
					 */
					int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

					if (receiptID == 0) {

						System.out.println("Failed to retrieve previosuly entered receiptID.");

						statusMessage = "error";

					} else {

						statusMessage = patientDAOInf.updatePaymentDetails(form, form.getPaymentType(), receiptID);

						if (statusMessage.equals("success")) {
							System.out.println("Payment details updated successfully.");
						} else {
							System.out.println("Failed to updated Payment details into PaymentDetails table.");
						}
					}

					for (int i = 0; i < form.getChargeID().length; i++) {

						boolean TransactionCheck = patientDAOInf.verifyTransactionDetailsExists("Transactions",
								form.getChargeType()[i], receiptID);

						if (TransactionCheck) {

							statusMessage = patientDAOInf.updateTransactionDetails(
									Integer.parseInt(form.getChargeID()[i]),
									Integer.parseInt(form.getChargeQuantity()[i]),
									Double.parseDouble(form.getChargeAmount()[i]));

							if (statusMessage.equals("success")) {
								System.out.println("Transaction details added successfully.");
							} else {
								System.out.println("Failed to add transaction details into Transactions table.");
							}

						} else {

							statusMessage = patientDAOInf.insertTransactionDetails(form.getChargeType()[i], receiptID,
									Double.parseDouble(form.getChargeQuantity()[i]),
									Double.parseDouble(form.getChargeRate()[i]),
									Double.parseDouble(form.getChargeAmount()[i]));

							if (statusMessage.equals("success")) {
								System.out.println("Transaction details added successfully.");
							} else {
								System.out.println("Failed to add transaction details into Transactions table.");
								statusMessage = "error";
							}
						}

					}

					return statusMessage;

				} else {

					System.out.println("Failed to update billing details");
					statusMessage = "error";
					return statusMessage;
				}

			} else {

				/*
				 * Inserting Billing details into Billing table
				 */

				statusMessage = patientDAOInf.inserGeneralHospitalBillDetails(form, form.getVisitID());
				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Retrieve previously entered receiptID
					 */
					int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

					if (receiptID == 0) {

						System.out.println("Failed to retrieve previosuly entered receiptID.");

						statusMessage = "error";

					} else {

						statusMessage = patientDAOInf.insertPaymentDetails(form, form.getPaymentType(), receiptID);

						if (statusMessage.equals("success")) {
							System.out.println("Payment details added successfully.");
						} else {
							System.out.println("Failed to add Payment details into PaymentDetails table.");
							statusMessage = "error";
						}

					}

					for (int i = 0; i < form.getChargeID().length; i++) {

						boolean TransactionCheck = patientDAOInf.verifyTransactionDetailsExists("Transactions",
								form.getChargeType()[i], receiptID);

						if (TransactionCheck) {

							statusMessage = patientDAOInf.updateTransactionDetails(
									Integer.parseInt(form.getChargeID()[i]),
									Double.parseDouble(form.getChargeQuantity()[i]),
									Double.parseDouble(form.getChargeAmount()[i]));

							if (statusMessage.equals("success")) {
								System.out.println("Transaction details added successfully.");
							} else {
								System.out.println("Failed to add transaction details into Transactions table.");
							}

						} else {

							statusMessage = patientDAOInf.insertTransactionDetails(form.getChargeType()[i], receiptID,
									Double.parseDouble(form.getChargeQuantity()[i]),
									Double.parseDouble(form.getChargeRate()[i]),
									Double.parseDouble(form.getChargeAmount()[i]));

							if (statusMessage.equals("success")) {
								System.out.println("Transaction details added successfully.");
							} else {
								System.out.println("Failed to add transaction details into Transactions table.");
								statusMessage = "error";
							}
						}

					}

					return statusMessage;

				} else {

					System.out.println("Failed to insert billing details");
					statusMessage = "error";
					return statusMessage;
				}

			}

		}

	}

	public String updateGeneralHospitalVisit(PatientForm patientForm) {

		System.out.println("patientForm.getCategory():" + patientForm.getVisitID());

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateGeneralHospitalPatientVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean medHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "MedicalHistory");

			if (medHistCheck) {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updateMedicalHistory(patientForm);

			} else {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.insertMedicalHistory(patientForm);

			}
			if (statusMessage.equals("success")) {

				/*
				 * Check whether data with same patientID already exists into MedicalHistory
				 * table, if, so then update it, else insert it
				 */
				boolean famHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "FamilyHistory");

				if (famHistCheck) {

					/*
					 * inserting family history details into FamilyHistory table
					 */
					statusMessage = patientDAOInf.updateFamilyHistory(patientForm);

				} else {

					/*
					 * inserting family history details into FamilyHistory table
					 */
					statusMessage = patientDAOInf.insertFamilyHistory(patientForm);

				}

				if (statusMessage.equals("success")) {

					/*
					 * Check whether data with same patientID already exists into MedicalHistory
					 * table, if, so then update it, else insert it
					 */
					boolean persHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(),
							"PersonalHistory");

					if (persHistCheck) {

						// inserting habit details into PersonalHistory table
						statusMessage = patientDAOInf.updatePersonalHistory(patientForm);

					} else {

						// inserting habit details into PersonalHistory table
						statusMessage = patientDAOInf.insertPersonalHistory(patientForm);

					}

					if (statusMessage.equals("success")) {

						// splitting complaints string using , (comma) and iterating over each complaint
						// and insert each into PresentComplaints table
						String complaints = patientForm.getComplaints();
						String finalComplaints = "";
						String finalComplaintsNew = "";

						boolean ComplaintCheck = patientDAOInf.verifyDataExistsInvestigation(patientForm.getVisitID(),
								"PresentComplaints");

						if (ComplaintCheck) {

							// check if present complaints added or not, if not then proceed further else
							// split using comma and iterate over it to insert in PresentComplaints table
							if (complaints == null || complaints == "") {

								System.out.println("No complaints selected");

								statusMessage = "success";

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									if (finalComplaints.startsWith(",")) {
										finalComplaintsNew = finalComplaints.substring(1);
									}
									statusMessage = patientDAOInf.updatePresentComplaints(finalComplaintsNew,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

							} else if (complaints.isEmpty()) {

								System.out.println("No complaints selected");

								statusMessage = "success";

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									if (finalComplaints.startsWith(",")) {
										finalComplaintsNew = finalComplaints.substring(1);
									}
									statusMessage = patientDAOInf.updatePresentComplaints(finalComplaintsNew,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

							} else {

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");
									System.out.println("complaints : " + complaints);
									statusMessage = patientDAOInf.updatePresentComplaints(complaints,
											patientForm.getVisitID());

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									statusMessage = patientDAOInf.updatePresentComplaints(finalComplaints,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

								if (!statusMessage.equals("success")) {

									System.out.println(
											"Failed to add present complaints details into PresentComplaints table");
									statusMessage = "error";
								}

							}

						} else {

							// check if present complaints added or not, if not then proceed further else
							// split using comma and iterate over it to insert in PresentComplaints table
							if (complaints == null || complaints == "") {

								System.out.println("No complaints selected");

								statusMessage = "success";

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									if (finalComplaints.startsWith(",")) {
										finalComplaintsNew = finalComplaints.substring(1);
									}
									statusMessage = patientDAOInf.insertPresentComplaints(finalComplaintsNew,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

							} else if (complaints.isEmpty()) {

								System.out.println("No complaints selected");

								statusMessage = "success";

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									if (finalComplaints.startsWith(",")) {
										finalComplaintsNew = finalComplaints.substring(1);
									}
									statusMessage = patientDAOInf.insertPresentComplaints(finalComplaintsNew,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

							} else {

								// check whether other complaints added or not, if yes then iterating over
								// otherComplaints array and inserting each into PresentComplaints table
								if (patientForm.getOtherComplaint() == null) {

									System.out.println("No complaints selected");

									statusMessage = patientDAOInf.insertPresentComplaints(complaints,
											patientForm.getVisitID());

									statusMessage = "success";

								} else {

									for (int i = 0; i < patientForm.getOtherComplaint().length; i++) {

										String otherComplaint = patientForm.getOtherComplaint()[i];

										finalComplaints = complaints + "," + otherComplaint;

										boolean OtherComplaintCheck = patientDAOInf
												.verifyOtherComplaintDataExists(otherComplaint);

										if (OtherComplaintCheck) {
											System.out.println("Other Complaints already exist.");
											statusMessage = "success";
										} else {
											// inserting present complaints details into PresentComplaints
											statusMessage = patientDAOInf.insertPVComplaints(otherComplaint);

										}
									}

									statusMessage = patientDAOInf.insertPresentComplaints(finalComplaints,
											patientForm.getVisitID());

									if (!statusMessage.equals("success")) {
										System.out.println(
												"Failed to add present complaints details into PresentComplaints table");

										statusMessage = "error";
									}

								}

								if (!statusMessage.equals("success")) {

									System.out.println(
											"Failed to add present complaints details into PresentComplaints table");
									statusMessage = "error";
								}

							}
						}

						if (statusMessage.equals("success")) {

							// inserting vitals details into VitalSigns table
							statusMessage = patientDAOInf.updateVitalSigns(patientForm);

							if (statusMessage.equals("success")) {

								boolean onExamCheck = patientDAOInf
										.verifyDataExistsInvestigation(patientForm.getVisitID(), "OnExamination");

								if (onExamCheck) {

									// inserting rs, cvs, cns and abdomen details into OnExamination table
									statusMessage = patientDAOInf.updateOnExaminationDetails(patientForm);

								} else {

									statusMessage = patientDAOInf.insertOnExaminationDetails(patientForm);
								}

								if (statusMessage.equals("success")) {

									boolean investigationCheck = patientDAOInf
											.verifyDataExistsInvestigation(patientForm.getVisitID(), "Investigations");

									if (investigationCheck) {
										statusMessage = patientDAOInf.updateInvestigationDetails(
												patientForm.getBiopsyFindings(), patientForm.getVisitID(),
												"Significant Findings");
									} else {
										statusMessage = patientDAOInf.insertInvestigationDetails(
												patientForm.getBiopsyFindings(), patientForm.getVisitID(), null,
												"Significant Findings", null);
									}
									// inserting significant findings details into Investigation table

									if (statusMessage.equals("success")) {

										return statusMessage;

									} else {

										System.out.println(
												"Failed to update significant findings details into Investigation table");

										statusMessage = "error";

										return statusMessage;

									}

								} else {

									System.out.println("Failed to update examination details into OnExamination table");

									statusMessage = "error";

									return statusMessage;

								}

							} else {

								System.out.println("Failed to update vital details into VitalSigns table.");

								statusMessage = "error";

								return statusMessage;

							}

						} else {

							System.out.println("Failed to update present complaints details into PresentComplaints.");

							statusMessage = "error";

							return statusMessage;

						}

					} else {

						System.out.println("Failed to update habit details into PersonalHistory table.");

						statusMessage = "error";

						return statusMessage;

					}

				} else {

					System.out.println("Failed to update family history details into FamilyHistory table.");

					statusMessage = "error";

					return statusMessage;

				}

			} else {

				System.out.println("Failed to update past history details into MedicalHistory table.");

				statusMessage = "error";

				return statusMessage;

			}

		} else {

			System.out.println("Failed to update visit details into Visit table.");

			statusMessage = "error";

			return statusMessage;

		}

	}

	public String addGeneralHospitalIPDVisit(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(form.getPatientID(), form.getClinicID());

		// visitNumber += 1;

		form.setVisitNumber(visitNumber);

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		form.setCareType("IPD");

		/*
		 * verifying whether current visit type is new visit or not, if yes then
		 * inserting the details into Visit table else retrieving patient's last new
		 * visit ID and setting newVisitRef as that visitID
		 */
		boolean newVisitCheck = patientDAOInf.verifyNewVisitType(form.getVisitTypeID());

		if (newVisitCheck) {
			form.setNewVisitRef(0);
		} else {
			// retrieving patient's last new visit ID
			int newVisitID = patientDAOInf.retrieveLastNewVisitID(form.getPatientID(), form.getClinicID());

			form.setNewVisitRef(newVisitID);
			form.setVisitFromTime(null);
		}

		// inserting details into Visit table
		statusMessage = patientDAOInf.insertIPDPatientVisit(form);

		if (statusMessage.equals("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			int lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, form.getPatientID(),
					form.getClinicID());

			form.setVisitID(lastVisitID);

			if (form.getChargeName() == null) {
				System.out.println("No IPD charges added");

				statusMessage = "success";

			} else {

				/*
				 * Inserting tariff charges and consultant charges into IPDCharges table for the
				 * respective visitID
				 */
				for (int i = 0; i < form.getChargeName().length; i++) {
					String chargeName = form.getChargeName()[i];
					String chargeType = form.getChargeType()[i];
					String rate = form.getChargeRate()[i];
					String quantity = form.getChargeQuantity()[i];
					String amount = form.getChargeAmount()[i];

					// inserting IPD charges details into IPDCharge table
					statusMessage = patientDAOInf.insertIPDCharges(chargeType, chargeName, Double.parseDouble(rate),
							Double.parseDouble(quantity), Double.parseDouble(amount), form.getVisitID());

					if (!statusMessage.equals("success")) {
						System.out.println("Failed to add IPD charge details into IPDCharge table");

						statusMessage = "error";
					}
				}

			}

			if (form.getOperation() == null) {
				System.out.println("No OT charges added");

				statusMessage = "success";

			} else {
				/*
				 * Inserting ot charges details into OTCharges table for the corresponding
				 * visitID
				 */
				for (int i = 0; i < form.getOperation().length; i++) {
					System.out.println("ot charge length:" + form.getOperation().length);
					String operation = form.getOperation()[i];
					String consultant = form.getConsultant()[i];
					String rate = form.getOTRate()[i];
					String anaesthetist = form.getAnaesthetist()[i];
					String otAssistant = form.getOTAssistant()[i];
					String otDateTime = form.getOtDateTimeArr()[i];

					statusMessage = patientDAOInf.insertOTCharges(operation, otDateTime, consultant, anaesthetist,
							otAssistant, Double.parseDouble(rate), form.getVisitID());

					if (statusMessage.equals("success")) {
						// Retrieving last inserted OT Charge ID by visitID and operationName from
						// OTCharges table
						int OTChargeID = patientDAOInf.retrieveLastOTChargeID(form.getVisitID(), operation);

						String chargeDisbursementStr = form.getChargeDisbursementArr()[i];

						String chargeDisbursementArr[] = chargeDisbursementStr.split("\\$");

						for (int j = 0; j < chargeDisbursementArr.length; j++) {
							String[] array1 = chargeDisbursementArr[j].split("==");

							String chargeType = array1[0];

							double charges = Double.parseDouble(array1[1]);

							// inserting details into OTChargeDisbursement table for the corresponding
							// OTChargeID
							statusMessage = patientDAOInf.insertChargeDisbursement(OTChargeID, chargeType, charges);

							if (!statusMessage.equals("success")) {
								System.out.println(
										"Failed to add OT charge disbursement details into OTChargeDisbursement table");

								statusMessage = "error";
							}
						}

					} else {
						System.out.println("Failed to add OT charge details into OTCharges table");

						statusMessage = "error";
					}

				}
			}

			return statusMessage;

		} else {

			System.out.println("Failed to add IPD visit details");

			statusMessage = "error";

			return statusMessage;

		}

	}

	public String addGeneralHospitalIPDBill(PatientForm form) {

		patientDAOInf = new PatientDAOImpl();

		// verify whether visitID is 0 if yes then give error message as visit not added
		// else proceed further to add/update the details
		if (form.getVisitID() == 0) {

			System.out.println("No visit added");

			statusMessage = "noVisit";

			return statusMessage;

		} else {
			// retrieving patient's last new visit ID
			int newVisitID = patientDAOInf.retrieveNewVisitRef(form.getVisitID());

			System.out.println("newVisitID..." + newVisitID);

			if (newVisitID != 0) {
				form.setVisitID(newVisitID);
			}

			// verify whether receipt exists for the newVisitID if so then update the
			// receipt details else add new details
			boolean receiptCheck = patientDAOInf.verifyVisitExists("Receipt", form.getVisitID());

			if (receiptCheck) {

				// retrieving billing type from the PVVisiType table based on visitTYpeID
				String billingType = patientDAOInf.retrieveBillingTypeByVisitTypeID(form.getVisitTypeID());

				form.setBillingType(billingType);

				// udpating receipt details into Receipt table for the corresponding visitID
				statusMessage = patientDAOInf.updateIPDReceipt(form);

				if (statusMessage.equals("success")) {

					// retrieving last inserted receiptID based on visitID from Receipt table
					int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

					form.setReceiptID(receiptID);

					// inserting details into Transactions table for the corresponding receiptID
					if (form.getChargeName() == null) {

						System.out.println("No IPD charges found.");

						statusMessage = "input";

					} else {

						for (int i = 0; i < form.getChargeName().length; i++) {

							String chargeName = form.getChargeName()[i];
							String chargeType = form.getChargeType()[i];
							String rate = form.getBillingProdRate()[i];
							String quantity = form.getBillingProdQuantity()[i];
							String amount = form.getBillingProdAmount()[i];
							String otDateTime = form.getOtDateTimeArr()[i];
							int chargeID = Integer.parseInt(form.getChargeID()[i]);

							// verify whether data exists into Transaction table for the transactionID if
							// yes then udpate the details else add new details into Transaction table
							boolean transactionCheck = patientDAOInf.verifyVisitExistsNew("Transactions", chargeID);

							if (transactionCheck) {

								statusMessage = patientDAOInf.updateIPDTransactions(Double.parseDouble(quantity),
										chargeID, Double.parseDouble(amount), 0D, 0D, Double.parseDouble(rate),
										chargeType, chargeName, otDateTime);

								if (!statusMessage.equals("success")) {
									System.out.println("Failed to update ipd transactions details.");
								}

							} else {

								statusMessage = patientDAOInf.insertIPDTransactions(Double.parseDouble(quantity),
										receiptID, Double.parseDouble(amount), 0D, 0D, Double.parseDouble(rate),
										chargeType, chargeName, otDateTime);

								if (!statusMessage.equals("success")) {
									System.out.println("Failed to add ipd transactions details.");
								}

							}

						}

					}

					// verify whether data exists into PaymentDetails table for the correpoding
					// receiptID, if exists then udpate the details else add new details into it
					boolean paymentCheck = patientDAOInf.verifyPaymentDataExists(receiptID);

					if (paymentCheck) {

						// updating paymentDetails into PaymentDetails table for the corresponding
						// receiptID
						statusMessage = patientDAOInf.updatePaymentDetails(form, form.getPaymentType(), receiptID);

						if (!statusMessage.equals("success")) {
							System.out.println("Failed to update payment details.");
						}

						return statusMessage;

					} else {

						// inserting paymentDetails into PaymentDetails table for the corresponding
						// receiptID
						statusMessage = patientDAOInf.insertPaymentDetails(form, form.getPaymentType(), receiptID);

						if (!statusMessage.equals("success")) {
							System.out.println("Failed to add payment details.");
						}

						return statusMessage;

					}

				} else {

					System.out.println("Failed to update receipt details");

					statusMessage = "error";

					return statusMessage;

				}

			} else {

				// retrieving billing type from the PVVisiType table based on visitTYpeID
				String billingType = patientDAOInf.retrieveBillingTypeByVisitTypeID(form.getVisitTypeID());

				form.setBillingType(billingType);

				// inserting receipt details into Receipt table for the corresponding visitID
				statusMessage = patientDAOInf.insertIPDReceipt(form);

				if (statusMessage.equals("success")) {

					// retrieving last inserted receiptID based on visitID from Receipt table
					int receiptID = patientDAOInf.retrieveLastEnteredReceiptID(form.getVisitID());

					if (receiptID == 0) {

						System.out.println("Failed to retrieve last entered receiptID ");

						statusMessage = "error";

						return statusMessage;

					} else {

						form.setReceiptID(receiptID);

						// inserting details into Transactions table for the corresponding receiptID
						if (form.getChargeName() == null) {

							System.out.println("No IPD charges found.");

							statusMessage = "input";

						} else {

							for (int i = 0; i < form.getChargeName().length; i++) {

								String chargeName = form.getChargeName()[i];
								String chargeType = form.getChargeType()[i];
								String rate = form.getBillingProdRate()[i];
								String quantity = form.getBillingProdQuantity()[i];
								String amount = form.getBillingProdAmount()[i];
								String otDateTime = form.getOtDateTimeArr()[i];

								statusMessage = patientDAOInf.insertIPDTransactions(Double.parseDouble(quantity),
										receiptID, Double.parseDouble(amount), 0D, 0D, Double.parseDouble(rate),
										chargeType, chargeName, otDateTime);

								if (!statusMessage.equals("success")) {
									System.out.println("Failed to add ipd transactions details.");
								}

							}

						}

						// inserting paymentDetails into PaymentDetails table for the corresponding
						// receiptID
						statusMessage = patientDAOInf.insertPaymentDetails(form, form.getPaymentType(), receiptID);

						if (!statusMessage.equals("success")) {
							System.out.println("Failed to add payment details.");
						}

						return statusMessage;

					}

				} else {

					System.out.println("Failed to add receipt details");

					statusMessage = "error";

					return statusMessage;

				}

			}

		}
	}

	public String editGeneralHospitalIPDVisit(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		// inserting details into Visit table
		statusMessage = patientDAOInf.updateIPDPatientVisit(form);

		if (statusMessage.equals("success")) {

			if (form.getChargeName() == null) {
				System.out.println("No IPD charges added");

				statusMessage = "success";

			} else {
				/*
				 * Inserting tariff charges and consultant charges into IPDCharges table for the
				 * respective visitID
				 */
				for (int i = 0; i < form.getChargeName().length; i++) {
					String chargeName = form.getChargeName()[i];
					String chargeType = form.getChargeType()[i];
					String rate = form.getChargeRate()[i];
					String quantity = form.getChargeQuantity()[i];
					String amount = form.getChargeAmount()[i];

					// inserting IPD charges details into IPDCharge table
					statusMessage = patientDAOInf.insertIPDCharges(chargeType, chargeName, Double.parseDouble(rate),
							Double.parseDouble(quantity), Double.parseDouble(amount), form.getVisitID());

					if (!statusMessage.equals("success")) {
						System.out.println("Failed to add IPD charge details into IPDCharge table");

						statusMessage = "error";
					}
				}
			}

			if (form.getEditChargeName() == null) {
				System.out.println("No IPD edit charges found");

				statusMessage = "success";

			} else {
				/*
				 * Updating tariff and consultation charges
				 */
				for (int i = 0; i < form.getEditChargeName().length; i++) {
					String chargeName = form.getEditChargeName()[i];
					String chargeID = form.getEditChargeID()[i];
					String chargeType = form.getEditChargeType()[i];
					String rate = form.getEditChargeRate()[i];
					String quantity = form.getEditChargeQuantity()[i];
					String amount = form.getEditChargeAmount()[i];

					// inserting IPD charges details into IPDCharge table
					statusMessage = patientDAOInf.updateIPDCharges(chargeType, chargeName, Double.parseDouble(rate),
							Double.parseDouble(quantity), Double.parseDouble(amount), Integer.parseInt(chargeID));

					if (!statusMessage.equals("success")) {
						System.out.println("Failed to update IPD charge details into IPDCharge table");

						statusMessage = "error";
					}
				}
			}

			if (form.getOperation() == null) {
				System.out.println("No OT charges added");

				statusMessage = "success";

			} else {
				/*
				 * Inserting ot charges details into OTCharges table for the corresponding
				 * visitID
				 */
				for (int i = 0; i < form.getOperation().length; i++) {

					String operation = form.getOperation()[i];
					String consultant = form.getConsultant()[i];
					String rate = form.getOTRate()[i];
					String anaesthetist = form.getAnaesthetist()[i];
					String otAssistant = form.getOTAssistant()[i];
					String otDateTime = form.getOtDateTimeArr()[i];

					statusMessage = patientDAOInf.insertOTCharges(operation, otDateTime, consultant, anaesthetist,
							otAssistant, Double.parseDouble(rate), form.getVisitID());

					if (statusMessage.equals("success")) {
						// Retrieving last inserted OT Charge ID by visitID and operationName from
						// OTCharges table
						int OTChargeID = patientDAOInf.retrieveLastOTChargeID(form.getVisitID(), operation);

						String chargeDisbursementStr = form.getChargeDisbursementArr()[i];

						String chargeDisbursementArr[] = chargeDisbursementStr.split("\\$");

						for (int j = 0; j < chargeDisbursementArr.length; j++) {
							String[] array1 = chargeDisbursementArr[j].split("==");

							String chargeType = array1[0];

							double charges = Double.parseDouble(array1[1]);

							// inserting details into OTChargeDisbursement table for the corresponding
							// OTChargeID
							statusMessage = patientDAOInf.insertChargeDisbursement(OTChargeID, chargeType, charges);

							if (!statusMessage.equals("success")) {
								System.out.println(
										"Failed to add OT charge disbursement details into OTChargeDisbursement table");

								statusMessage = "error";
							}
						}
					} else {
						System.out.println("Failed to add OT charge details into OTCharges table");

						statusMessage = "error";
					}

				}
			}

			if (form.getEditOperation() == null) {
				System.out.println("No OT edit charges found");

				statusMessage = "success";

			} else {
				/*
				 * Udpating OT charges
				 */
				for (int i = 0; i < form.getEditOperation().length; i++) {

					String operation = form.getEditOperation()[i];
					String otChargeID = form.getEditOTChargeID()[i];
					String consultant = form.getEditConsultant()[i];
					String rate = form.getEditOTRate()[i];
					String anaesthetist = form.getEditAnaesthetist()[i];
					String otAssistant = form.getEditOTAssistant()[i];
					String otDateTime = form.getEditOtDateTimeArr()[i];

					statusMessage = patientDAOInf.updateOTCharges(operation, otDateTime, consultant, anaesthetist,
							otAssistant, Double.parseDouble(rate), Integer.parseInt(otChargeID));

					if (!statusMessage.equals("success")) {
						System.out.println("Failed to update OT charge details into OTCharges table");

						statusMessage = "error";
					}

				}
			}

			return statusMessage;

		} else {

			System.out.println("Failed to add IPD visit details");

			statusMessage = "error";

			return statusMessage;

		}

	}

	public String verifyIPDVisitType(int patientID, int clinicID) {

		String status = "";

		patientDAOInf = new PatientDAOImpl();

		// retrieving last visit ID for the patient from Visit table
		int lastVisitID = patientDAOInf.retrieveLastVisitID(patientID, clinicID);

		if (lastVisitID == 0) {

			System.out.println("No visit added for this patient");

			status = "NoVisitFound";

			return status;

		} else {

			System.out
					.println("lastVisitID..." + lastVisitID + "...clinicID..." + clinicID + "..patientID:" + patientID);

			boolean visitCheck = patientDAOInf.verifyNewOrFollowUpVisitForPatient(lastVisitID, clinicID);

			if (visitCheck) {

				status = "VisitFound";

				return status;

			} else {

				status = "DischargeVisitFound";

				return status;

			}

		}

	}

	@Override
	public String addNewGenaralClinicVisitDetails(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertGenaralClinicPatientVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			int lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber,
					patientForm.getPatientID(), patientForm.getClinicID());

			// Setting lastVisitID into visitID variable of PatientForm
			patientForm.setVisitID(lastVisitID);

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean medHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "MedicalHistory");

			if (medHistCheck) {

				/*
				 * updating past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updateMedicalHistoryDetails(patientForm);

			} else {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.insertMedicalHistoryDetails(patientForm);
			}

			// inserting data into PresentComplaints table
			statusMessage = patientDAOInf.insertPresentComplaintsDetails(patientForm.getComments(),
					patientForm.getVisitID());

			// inserting rs, cvs, cns and abdomen details into OnExamination table
			statusMessage = patientDAOInf.insertOnExaminationDetails1(patientForm.getOnExamination(),
					patientForm.getVisitID());

			/*
			 * check whether prescription rows added or not and depending upon that
			 * proceeding further to insert records into Prescription table
			 */
			if (patientForm.getNewDrugID() == null) {
				System.out.println("No drugs added into prescription");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getNewDrugID().length; i++) {

					String tradeName = patientForm.getNewDrugID()[i];
					String comment = patientForm.getNewDrugComment()[i];
					String noOfDaysStr = patientForm.getNewDrugNoOfDays()[i];
					String frequency = patientForm.getNewDrugFrequency()[i];

					if (frequency.startsWith(",")) {
						frequency = frequency.substring(1);
					}

					String noOfPillsStr = patientForm.getNewDrugQuantity()[i];
					double noOfPills = 0;
					if (noOfPillsStr == null || noOfPillsStr == "") {
						noOfPills = 0;
					} else if (noOfPillsStr.isEmpty()) {
						noOfPills = 0;
					} else {
						noOfPills = Double.parseDouble(noOfPillsStr);
					}

					double dosage = 0;

					int categoryID = 0;

					if (patientForm.getNewDrugCategoryID() != null) {
						if (patientForm.getNewDrugCategoryID()[i] == null
								|| patientForm.getNewDrugCategoryID()[i] == "") {
							categoryID = 0;
						} else if (patientForm.getNewDrugCategoryID()[i].isEmpty()) {
							categoryID = 0;
						} else {
							categoryID = Integer.parseInt(patientForm.getNewDrugCategoryID()[i]);
						}
					}

					statusMessage = patientDAOInf.insertVisitPrescriptionDetails(tradeName, dosage, frequency,
							noOfPills, comment, noOfDaysStr, patientForm.getVisitID(), categoryID);

					if (statusMessage.equals("success")) {

						int prescriptionID = patientDAOInf.retrievePrescriptionID(tradeName, patientForm.getVisitID());

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyProductExits(tradeName,
						 * patientForm.getClinicID());
						 * 
						 * if (!check) { patientDAOInf.insertTradeNameInProductTable(tradeName,
						 * patientForm.getClinicID()); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println("Drug details added successfully into Prescription table");

				} else {

					System.out.println("Failed to add drug details into prescription table.");

					statusMessage = "error";
				}
			}

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (patientForm.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String testName = patientForm.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, patientForm.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}

		} else {

			System.out.println("Failed to insert visit details into Visit table.");

			statusMessage = "error";
		}

		return statusMessage;

	}

	@Override
	public String editNewGenaralClinicVisitDetails(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateGenaralClinicPatientVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean medHistCheck = patientDAOInf.verifyDataExists(patientForm.getPatientID(), "MedicalHistory");

			if (medHistCheck) {

				System.out.println("inside medical history");
				/*
				 * updating past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updateMedicalHistoryDetails(patientForm);

			} else {

				/*
				 * inserting past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.insertMedicalHistoryDetails(patientForm);
			}

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean PresentComplaintsCheck = patientDAOInf.verifyVisitDataExists(patientForm.getVisitID(),
					"PresentComplaints");

			if (PresentComplaintsCheck) {

				System.out.println("inside PresentComplaints");
				/*
				 * updating past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updatePresentComplaints1(patientForm.getComments(),
						patientForm.getVisitID());

			} else {

				// inserting data into PresentComplaints table
				statusMessage = patientDAOInf.insertPresentComplaintsDetails(patientForm.getComments(),
						patientForm.getVisitID());
			}

			/*
			 * Check whether data with same patientID already exists into MedicalHistory
			 * table, if, so then update it, else insert it
			 */
			boolean OnExaminationCheck = patientDAOInf.verifyVisitDataExists(patientForm.getVisitID(), "OnExamination");

			if (OnExaminationCheck) {
				System.out.println("inside OnExaminationCheck");
				/*
				 * updating past history details into MedicalHistory table
				 */
				statusMessage = patientDAOInf.updateOnExaminationDetails1(patientForm.getOnExamination(),
						patientForm.getVisitID());

			} else {

				// inserting rs, cvs, cns and abdomen details into OnExamination table
				statusMessage = patientDAOInf.insertOnExaminationDetails1(patientForm.getOnExamination(),
						patientForm.getVisitID());
			}

			/*
			 * check whether prescription rows added or not and depending upon that
			 * proceeding further to insert records into Prescription table
			 */
			if (patientForm.getNewDrugID() == null) {
				System.out.println("No drugs added into prescription");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getNewDrugID().length; i++) {

					String tradeName = patientForm.getNewDrugID()[i];
					String comment = patientForm.getNewDrugComment()[i];
					String noOfDaysStr = patientForm.getNewDrugNoOfDays()[i];
					String frequency = patientForm.getNewDrugFrequency()[i];

					if (frequency.startsWith(",")) {
						frequency = frequency.substring(1);
					}

					String noOfPillsStr = patientForm.getNewDrugQuantity()[i];
					double noOfPills = 0;
					if (noOfPillsStr == null || noOfPillsStr == "") {
						noOfPills = 0;
					} else if (noOfPillsStr.isEmpty()) {
						noOfPills = 0;
					} else {
						noOfPills = Double.parseDouble(noOfPillsStr);
					}

					double dosage = 0;

					int categoryID = 0;

					if (patientForm.getNewDrugCategoryID() != null) {

						if (patientForm.getNewDrugCategoryID()[i] == null
								|| patientForm.getNewDrugCategoryID()[i] == "") {
							categoryID = 0;
						} else if (patientForm.getNewDrugCategoryID()[i].isEmpty()) {
							categoryID = 0;
						} else {
							categoryID = Integer.parseInt(patientForm.getNewDrugCategoryID()[i]);
						}
					}

					statusMessage = patientDAOInf.insertVisitPrescriptionDetails(tradeName, dosage, frequency,
							noOfPills, comment, noOfDaysStr, patientForm.getVisitID(), categoryID);

					if (statusMessage.equals("success")) {

						int prescriptionID = patientDAOInf.retrievePrescriptionID(tradeName, patientForm.getVisitID());

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyProductExits(tradeName,
						 * patientForm.getClinicID());
						 * 
						 * if (!check) { patientDAOInf.insertTradeNameInProductTable(tradeName,
						 * patientForm.getClinicID()); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println("Drug details added successfully into Prescription table");

				} else {

					System.out.println("Failed to add drug details into prescription table.");

					statusMessage = "error";
				}
			}

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (patientForm.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String testName = patientForm.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, patientForm.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}

		} else {

			System.out.println("Failed to insert visit details into Visit table.");

			statusMessage = "error";
		}

		return statusMessage;

	}

	@Override
	public String addNewIPDGenaralClinicVisitDetails(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertIPDGenaralClinicPatientVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			int lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber,
					patientForm.getPatientID(), patientForm.getClinicID());

			// Setting lastVisitID into visitID variable of PatientForm
			patientForm.setVisitID(lastVisitID);

			/*
			 * check whether prescription rows added or not and depending upon that
			 * proceeding further to insert records into Prescription table
			 */
			if (patientForm.getNewDrugID() == null) {
				System.out.println("No drugs added into prescription");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getNewDrugID().length; i++) {

					String tradeName = patientForm.getNewDrugID()[i];
					String comment = patientForm.getNewDrugComment()[i];
					String noOfDaysStr = patientForm.getNewDrugNoOfDays()[i];
					String frequency = patientForm.getNewDrugFrequency()[i];

					if (frequency.startsWith(",")) {
						frequency = frequency.substring(1);
					}

					String noOfPillsStr = patientForm.getNewDrugQuantity()[i];
					double noOfPills = 0;
					if (noOfPillsStr == null || noOfPillsStr == "") {
						noOfPills = 0;
					} else if (noOfPillsStr.isEmpty()) {
						noOfPills = 0;
					} else {
						noOfPills = Double.parseDouble(noOfPillsStr);
					}

					double dosage = 0;

					int categoryID = 0;

					if (patientForm.getNewDrugCategoryID() != null) {
						if (patientForm.getNewDrugCategoryID()[i] == null
								|| patientForm.getNewDrugCategoryID()[i] == "") {
							categoryID = 0;
						} else if (patientForm.getNewDrugCategoryID()[i].isEmpty()) {
							categoryID = 0;
						} else {
							categoryID = Integer.parseInt(patientForm.getNewDrugCategoryID()[i]);
						}
					}

					statusMessage = patientDAOInf.insertVisitPrescriptionDetails(tradeName, dosage, frequency,
							noOfPills, comment, noOfDaysStr, patientForm.getVisitID(), categoryID);

					if (statusMessage.equals("success")) {

						int prescriptionID = patientDAOInf.retrievePrescriptionID(tradeName, patientForm.getVisitID());

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyProductExits(tradeName,
						 * patientForm.getClinicID());
						 * 
						 * if (!check) { patientDAOInf.insertTradeNameInProductTable(tradeName,
						 * patientForm.getClinicID()); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println("Drug details added successfully into Prescription table");

				} else {

					System.out.println("Failed to add drug details into prescription table.");

					statusMessage = "error";
				}
			}

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (patientForm.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String testName = patientForm.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, patientForm.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}

		} else {

			System.out.println("Failed to insert visit details into Visit table.");

			statusMessage = "error";
		}

		return statusMessage;

	}

	@Override
	public String editIPDGenaralClinicVisitDetails(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Setting careType as OPD in patientForm's careType variable
		 */
		patientForm.setCareType("OPD");

		/*
		 * Retrieving visitType name by visitTypeID from PVVisitType table
		 */
		String visitType = clinicDAOInf.retrieveVisitTypeNameByID(patientForm.getVisitTypeID());

		/*
		 * Setting visit type to New
		 */
		patientForm.setVisitType(visitType);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateGenaralClinicPatientIPDVisit(patientForm, visitNumber, 0);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * check whether prescription rows added or not and depending upon that
			 * proceeding further to insert records into Prescription table
			 */
			if (patientForm.getNewDrugID() == null) {
				System.out.println("No drugs added into prescription");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getNewDrugID().length; i++) {

					String tradeName = patientForm.getNewDrugID()[i];
					String comment = patientForm.getNewDrugComment()[i];
					String noOfDaysStr = patientForm.getNewDrugNoOfDays()[i];
					String frequency = patientForm.getNewDrugFrequency()[i];

					if (frequency.startsWith(",")) {
						frequency = frequency.substring(1);
					}

					String noOfPillsStr = patientForm.getNewDrugQuantity()[i];
					double noOfPills = 0;
					if (noOfPillsStr == null || noOfPillsStr == "") {
						noOfPills = 0;
					} else if (noOfPillsStr.isEmpty()) {
						noOfPills = 0;
					} else {
						noOfPills = Double.parseDouble(noOfPillsStr);
					}

					double dosage = 0;

					int categoryID = 0;

					if (patientForm.getNewDrugCategoryID() != null) {
						if (patientForm.getNewDrugCategoryID()[i] == null
								|| patientForm.getNewDrugCategoryID()[i] == "") {
							categoryID = 0;
						} else if (patientForm.getNewDrugCategoryID()[i].isEmpty()) {
							categoryID = 0;
						} else {
							categoryID = Integer.parseInt(patientForm.getNewDrugCategoryID()[i]);
						}
					}

					statusMessage = patientDAOInf.insertVisitPrescriptionDetails(tradeName, dosage, frequency,
							noOfPills, comment, noOfDaysStr, patientForm.getVisitID(), categoryID);

					if (statusMessage.equals("success")) {

						int prescriptionID = patientDAOInf.retrievePrescriptionID(tradeName, patientForm.getVisitID());

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyProductExits(tradeName,
						 * patientForm.getClinicID());
						 * 
						 * if (!check) { patientDAOInf.insertTradeNameInProductTable(tradeName,
						 * patientForm.getClinicID()); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println("Drug details added successfully into Prescription table");

				} else {

					System.out.println("Failed to add drug details into prescription table.");

					statusMessage = "error";
				}
			}

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (patientForm.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String testName = patientForm.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, patientForm.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */

					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}

		} else {

			System.out.println("Failed to insert visit details into Visit table.");

			statusMessage = "error";
		}

		return statusMessage;

	}

	public String addSMSTemplate(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = configurationDAOInf.verifySMSTemplateNameAlreadyExists(configurationForm.getSMSTemplateTitle());

		if (check) {

			System.out.println("SMS Template Name already exists into SMSTemplate table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = configurationDAOInf.insertSMSTemplate(configurationForm.getSMSTemplateTitle(),
					configurationForm.getSMSTemplateText());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to insert SMS Template details.");

				statusMessage = "error";

				return statusMessage;

			}

		}
	}

	public String editSMSTemplate(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		boolean check = configurationDAOInf.verifyEditSMSTemplateNameAlreadyExists(
				configurationForm.getSMSTemplateTitle(), configurationForm.getSMSTemplateID());

		if (check) {

			System.out.println("SMS Template Name already exists into SMSTemplate table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			/*
			 * Inserting Category details into Category table
			 */
			statusMessage = configurationDAOInf.updateSMSTemplate(configurationForm.getSMSTemplateTitle(),
					configurationForm.getSMSTemplateID(), configurationForm.getSMSTemplateText());

			if (statusMessage.equalsIgnoreCase("success")) {

				return statusMessage;

			} else {

				System.out.println("Failed to update SMS Template details.");

				statusMessage = "error";

				return statusMessage;
			}
		}
	}

	@Override
	public String addNewInvestigation(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Check whether visit added before adding prescription details, if added then
		 * only proceed further to add prescription details into Prescription table else
		 * give error message saying visit not added
		 */
		if (form.getVisitID() == 0) {

			System.out.println("No visit added.");

			statusMessage = "noVisit";

			return statusMessage;

		} else {

			/*
			 * check whether Investigation rows added or not and depending upon that
			 * proceeding further to insert records into PrescribedInvestigation table
			 */
			if (form.getTest() == null) {
				System.out.println("No Investigation test added into Investigation");

				statusMessage = "success";
			} else {

				for (int i = 0; i < form.getTest().length; i++) {

					String testName = form.getTest()[i];

					statusMessage = patientDAOInf.insertInvestigationDetails(testName, form.getVisitID());

					if (statusMessage.equals("success")) {

						/*
						 * boolean check = prescriptionManagementDAOInf.verifyTestNameExits(testName);
						 * 
						 * if (!check) { patientDAOInf.insertTestNameInPVLabTestsTable(testName); }
						 */
					} else {

						statusMessage = "error";
					}
				}

				if (statusMessage.equals("success")) {

					System.out.println(
							"Investigation test details added successfully into PrescribedInvestigation table");

				} else {

					System.out.println("Failed to add Investigation test details into PrescribedInvestigation table.");

					statusMessage = "error";
				}
			}
			return statusMessage;
		}
	}

	@Override
	public String addLabTest(ConfigurationForm configurationForm, int practiceID) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether lab test with same panel and test already exists into
		 * PVLabTest table or not, if so, then give error msg else proceed further to
		 * add lab test details into PVLabTest table
		 */
		boolean check = configurationDAOInf.verifyLabTestAlreadyExists(configurationForm.getTest(),
				configurationForm.getSubgroup(), practiceID);

		if (check) {

			System.out.println("Panel and test Name already exists into PVLabTest table.");

			statusMessage = "input";

			return statusMessage;

		} else {

			configurationForm.setPracticeID(practiceID);

			/*
			 * Inserting LabTest details into PVLabTests table
			 */
			statusMessage = configurationDAOInf.insertLabTest(configurationForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				int labTestID = configurationDAOInf.retrieveLabTestID(configurationForm.getTest());
				
				

				/*
				 * Inserting Default Values details into PVLabTestDefaultValues table
				 */

				if (configurationForm.getDefaultValueArr() == null) {
					System.out.println("No value default values added for lab test.");
					statusMessage = "success";
					return statusMessage;
				} else {

					for (int i = 0; i < configurationForm.getDefaultValueArr().length; i++) {

						statusMessage = configurationDAOInf
								.insertLabTestDefaultValues(configurationForm.getDefaultValueArr()[i], labTestID);
					}

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted default values for lab test.");

					} else {

						System.out.println("Failed to insert default values for lab test.");

						statusMessage = "error";

					}
					return statusMessage;
				}

			} else {

				System.out.println("Failed to insert lab test.");

				statusMessage = "error";
				return statusMessage;
			}

		}
	}

	@Override
	public String editLabTest(ConfigurationForm configurationForm, int practiceID) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Inserting lab test details into PVLabTest table
		 */
		statusMessage = configurationDAOInf.updateLabTest(configurationForm);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * Inserting Default Values details into PVLabTestDefaultValues table
			 */

			if (configurationForm.getDefaultValueArr() == null) {
				System.out.println("No value default values added for lab test.");
				statusMessage = "success";

			} else {

				for (int i = 0; i < configurationForm.getDefaultValueArr().length; i++) {

					statusMessage = configurationDAOInf.insertLabTestDefaultValues(
							configurationForm.getDefaultValueArr()[i], configurationForm.getLabTestID());
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted default values for lab test.");

				} else {

					System.out.println("Failed to insert default values for lab test.");

					statusMessage = "error";
				}
			}

			/*
			 * updating Default Values details into PVLabTestDefaultValues table
			 */

			if (configurationForm.getEditdefaultValue() == null) {
				System.out.println("No default values added for lab test.");
				statusMessage = "success";

			} else {

				for (int i = 0; i < configurationForm.getEditdefaultValue().length; i++) {

					statusMessage = configurationDAOInf.updateLabTestDefaultValues(
							configurationForm.getEditdefaultValue()[i],
							Integer.parseInt(configurationForm.getEditdefaultValueID()[i]));
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated default values for lab test.");

				} else {

					System.out.println("Failed to update default values for lab test.");

					statusMessage = "error";
				}
			}

		} else {

			System.out.println("Failed to update lab test.");

			statusMessage = "error";
		}

		return statusMessage;

	}

	@Override
	public String addNewLabVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		int lastVisitID = 0;

		String labTestval = "";
		/*
		 * Retrieving last entered visit's visitNumber and adding one to it in order to
		 * insert new visitNumber into Patient table
		 */
		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		/*
		 * Retrieving new visit type id from PVVisitType table based on clinicID and
		 * newVisit flag as 1
		 */
		int newVisitTypeID = patientDAOInf.retrieveNewVisitTypeIDByClinicID(patientForm.getClinicID());

		System.out.println("New visit type ID is ..." + newVisitTypeID);

		/*
		 * Retrieving last visitID for visit Type 'New' to insert it as the value of
		 * newVisitRef if visitType selected by user is 'Follow-up'
		 */
		int visitID = patientDAOInf.retrievVisitIDForNewVisitType(patientForm.getPatientID(), newVisitTypeID);

		int newVisitRef = 0;

		if (patientDAOInf.verifyFollowUpVisitType(patientForm.getVisitTypeID())) {
			newVisitRef = visitID;
		}

		patientForm.setDiagnosis(patientForm.getCancerType());

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.insertLabPatientVisit(patientForm, visitNumber, newVisitRef);

		if (statusMessage.equalsIgnoreCase("success")) {

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getClinicID());

			patientForm.setVisitID(lastVisitID);

			System.out.println("Last entered visit ID is : " + lastVisitID);

			if (patientForm.getLabTestDetails() == null) {
				System.out.println("No tests added");

				statusMessage = "success";

				return statusMessage;
			} else {
				System.out.println("hidden details" + patientForm.getLabTestDetails());
//				if(patientForm.getLabTestDetails().startsWith("*")) {
//					
//					labTestval = patientForm.getLabTestDetails().substring(1);
//					System.out.println("....:"+labTestval);
//				}

				String[] labTest = patientForm.getLabTestDetails().split("\\*");

				for (int i = 0; i < labTest.length; i++) {

					System.out.println("labTest1111 : " + labTest[i] + "--" + patientForm.getLabTestDetails());

					String testsValue = labTest[i];

					if (!testsValue.isEmpty()) {
						String[] testArray = testsValue.split("\\$");

						int groupCheck = 0;
						System.out.println("test ::: " + testArray[7]);
						groupCheck = Integer.parseInt(testArray[7]);

						if (groupCheck == 1) {
							String groupName = testArray[0];
							double groupRate = Double.parseDouble(testArray[1]);
							String[] test = testArray[2].split(",");

							String[] normalRange = testArray[3].split(",");

							String subGroup = testArray[4];
							
							String[] stringArray = subGroup.split(",", -1);

					        // Iterate over the array and replace empty or whitespace strings with null
					        for (int i1 = 0; i1 < stringArray.length; i1++) {
					            if (stringArray[i1].trim().isEmpty()) {
					                stringArray[i1] = null;
					            }
					        }

							int length = stringArray.length;

							String[] testType = testArray[5].split(",");

							String[] rate = testArray[6].split(",");
							for (int j = 0; j < test.length; j++) {
								double rate1 = Double.parseDouble(rate[j]);
								if(length == 0) {
									statusMessage = patientDAOInf.insertLabInvestigationDetails(test[j], null,
											normalRange[j], lastVisitID, null, rate1, groupCheck, groupRate, groupName,
											"null", testType[j]);
								}else {
									statusMessage = patientDAOInf.insertLabInvestigationDetails(test[j], null,
											normalRange[j], lastVisitID, null, rate1, groupCheck, groupRate, groupName,
											stringArray[j], testType[j]);
								}

							}

						} else {
							String groupName = testArray[0];
							double groupRate = Double.parseDouble(testArray[1]);
							String test = testArray[2];

							String normalRange = testArray[3];

							String subGroup = "";

							subGroup = testArray[4];

							String testType = testArray[5];

							double rate = Double.parseDouble(testArray[6]);
							statusMessage = patientDAOInf.insertLabInvestigationDetails(test, null, normalRange,
									lastVisitID, null, rate, groupCheck, groupRate, groupName, subGroup, testType);
						}
					}

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						System.out.println("Failed to add lab tests.");
					}
				}

			}

			return statusMessage;

		} else {

			statusMessage = "error";
			return statusMessage;

		}

	}

	@Override
	public String addLabBill(PatientForm form) {
		patientDAOInf = new PatientDAOImpl();

		if (form.getVisitID() == 0) {
			System.out.println("No vist added");

			statusMessage = "noVisit";

			return statusMessage;
		} else {

			/*
			 * Verify whether visit ID exists into Receipt table or not, if not then only
			 * proceed further to add data into Receipt and relative table else return from
			 * here with success message
			 */
			boolean dataCheck = patientDAOInf.verifyVisitDataExists(form.getVisitID(), "Receipt");

			if (dataCheck) {

				statusMessage = patientDAOInf.updatetReceiptDetails(form);

				if (statusMessage.equals("success")) {

					boolean transasctionCheck = patientDAOInf.verifyOtherDataExists(form.getReceiptID(), "Transactions",
							"receiptID");

					if (transasctionCheck) {
						System.out.println("Data already exists into Transactions table");
					} else {
						for (int i = 0; i < form.getTest().length; i++) {

							String rate = form.getBillingProdRate()[i];

							String test = form.getTest()[i];

							statusMessage = patientDAOInf.insertTransactionDetails(0, form.getReceiptID(),
									Double.parseDouble(rate), 0, 0, ActivityStatus.ACTIVE, 0, 0, test,
									Double.parseDouble(rate));

							if (statusMessage.equals("success")) {
								statusMessage = "success";
							} else {
								statusMessage = "error";
								System.out.println("Failed to add transaction details.");
							}
						}
					}

					if (form.getTestName() == null) {
						System.out.println("No other test added");

						statusMessage = "success";
					} else {
						for (int i = 0; i < form.getTestName().length; i++) {

							String rate = form.getTestRate()[i];

							String test = form.getTestName()[i];

							statusMessage = patientDAOInf.insertTransactionDetails(0, form.getReceiptID(),
									Double.parseDouble(rate), 0, 0, ActivityStatus.ACTIVE, 0, 0, test,
									Double.parseDouble(rate));

							if (statusMessage.equals("success")) {
								statusMessage = "success";
							} else {
								statusMessage = "error";
								System.out.println("Failed to add transaction details.");
							}
						}
					}

					// updating payment details
					statusMessage = patientDAOInf.updatePaymentDetails(form, form.getPaymentType(),
							form.getReceiptID());

					if (statusMessage.equals("success")) {
						return statusMessage;
					} else {
						System.out.println("Failed to update payment details");

						statusMessage = "error";

						return statusMessage;

					}

				} else {
					System.out.println("Failed to udpate billing details into Receipt table");
					statusMessage = "error";
					return statusMessage;
				}

			} else {

				/*
				 * Inserting Billing details into Billing table
				 */
				statusMessage = patientDAOInf.insertReceiptDetails(form);

				if (statusMessage.equalsIgnoreCase("success")) {

					// Retrievint last inserted receiptID from Receipt table
					int receiptID = patientDAOInf.retrieveLastReceiptID(form.getVisitID());

					form.setReceiptID(receiptID);

					for (int i = 0; i < form.getTest().length; i++) {

						String rate = form.getBillingProdRate()[i];

						String test = form.getTest()[i];

						statusMessage = patientDAOInf.insertTransactionDetails(0, receiptID, Double.parseDouble(rate),
								0, 0, ActivityStatus.ACTIVE, 0, 0, test, Double.parseDouble(rate));

						if (statusMessage.equals("success")) {
							statusMessage = "success";
						} else {
							statusMessage = "error";
							System.out.println("Failed to add transaction details.");
						}
					}

					if (form.getTestName() == null) {
						System.out.println("No other test added");

						statusMessage = "success";
					} else {
						for (int i = 0; i < form.getTestName().length; i++) {

							String rate = form.getTestRate()[i];

							String test = form.getTestName()[i];

							statusMessage = patientDAOInf.insertTransactionDetails(0, form.getReceiptID(),
									Double.parseDouble(rate), 0, 0, ActivityStatus.ACTIVE, 0, 0, test,
									Double.parseDouble(rate));

							if (statusMessage.equals("success")) {
								statusMessage = "success";
							} else {
								statusMessage = "error";
								System.out.println("Failed to add transaction details.");
							}
						}
					}

					/*
					 * inserting payment details into PaymentDetails table for the corresponding the
					 * receiptID
					 */
					statusMessage = patientDAOInf.insertPaymentDetails(form, form.getPaymentType(),
							form.getReceiptID());

					if (statusMessage.equals("success")) {

						return statusMessage;

					} else {

						System.out.println("Failed to insert payment details");

						statusMessage = "error";

						return statusMessage;
					}
				} else {
					System.out.println("Failed to insert billing details into Receipt table");
					statusMessage = "error";
					return statusMessage;
				}

			}

		}

	}

	@Override
	public String addReferringDoctor(ConfigurationForm configurationForm, int practiceID) {

		configurationDAOInf = new ConfigurationDAOImpl();

		// verify whether details exists with the same doctorName or not, if yes then
		// give error msg else proceed further to add referring doctor
		boolean verify = configurationDAOInf.verifyReferringDocExist(configurationForm.getReferringDoctName(),
				practiceID);

		if (verify) {

			System.out.println("Referring doc with same name already exists");

			statusMessage = "input";

			return statusMessage;
		} else {

			/*
			 * INserting referring doctor into PVReferringDoctors table
			 */
			statusMessage = configurationDAOInf.insertReferringDoctor(configurationForm, practiceID);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to added referring doctor.");

				statusMessage = "error";

				return statusMessage;
			}

		}

	}

	public String edirReferringDoctor(ConfigurationForm configurationForm) {
		configurationDAOInf = new ConfigurationDAOImpl();

		boolean check = configurationDAOInf.verifyEditReferringDocExist(configurationForm.getReferringDoctName(),
				configurationForm.getReferringDoctID());

		if (check) {

			System.out.println("Referring doc with same name already exists");

			statusMessage = "input";

			return statusMessage;
		} else {

			/*
			 * Updating referring doctor into PVReferringDoctors table
			 */
			statusMessage = configurationDAOInf.updateRefDoc(configurationForm);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update referring doctor.");

				statusMessage = "error";

				return statusMessage;
			}

		}
	}

	public String editLabVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		String labTestval = "";

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateLabPatientVisit(patientForm);

		if (statusMessage.equalsIgnoreCase("success")) {

			if (patientForm.getTest() == null) {
				// System.out.println("No tests added");

				statusMessage = "success";

			} else {

				// System.out.println("labTest length:
				// "+patientForm.getTest().length+"-"+patientForm.getTest());

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String normalValue = patientForm.getNormalVal()[i];

					// System.out.println("normalValue: "+normalValue);

					/*
					 * Inserting lab tests details into LabInvestigation details table
					 */
					statusMessage = patientDAOInf.insertLabInvestigationDetails(patientForm.getTest()[i],
							patientForm.getObservationsValue()[i], normalValue, patientForm.getVisitID(), null,
							Double.parseDouble(patientForm.getRateVal()[i]),
							Integer.parseInt(patientForm.getIsGroupVal()[i]),
							Double.parseDouble(patientForm.getGroupRateVal()[i]), patientForm.getGroupNameVal()[i],
							patientForm.getSubGroupVal()[i], patientForm.getTestTypeArr()[i]);

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						// System.out.println("Failed to add lab tests.");
					}
				}

			}

			if (patientForm.getInvestigationDetails() == null) {
				// System.out.println("No tests added");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getInvestigationDetails().length; i++) {
					int investigationID = Integer.parseInt(patientForm.getInvestigationDetails()[i]);

					// System.out.println("ObservationsValue:
					// "+patientForm.getObservationsValue()[i]+"--"+patientForm.getInvestigationDetails().length);
					String value = patientForm.getObservationsValue()[i];
					/*
					 * Inserting lab tests details into LabInvestigation details table
					 */
					statusMessage = patientDAOInf.updateLabInvestigationDetails(investigationID, value);

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						// System.out.println("Failed to update lab tests.");
					}
				}

			}

			return statusMessage;

		} else {

			statusMessage = "error";
			return statusMessage;

		}

	}

	public String editBDPVisit(PatientForm patientForm) {

		patientDAOInf = new PatientDAOImpl();

		String labTestval = "";

		// Updating patient details
		patientDAOInf.updateBDPPatient(patientForm);

		/*
		 * Insert visit details into Visit table.
		 */
		statusMessage = patientDAOInf.updateLabPatientVisit(patientForm);

		if (statusMessage.equalsIgnoreCase("success")) {

			if (patientForm.getLabTestDetails() == null) {
				System.out.println("No tests added");

				statusMessage = "success";
			} else {
				System.out.println("hidden details" + patientForm.getLabTestDetails());
//				if(patientForm.getLabTestDetails().startsWith("*")) {
//					
//					labTestval = patientForm.getLabTestDetails().substring(1);
//					System.out.println("....:"+labTestval);
//				}
				
				// Updating the isDispatched status to true for those checkbox is checked.
				/*
				 * if(patientForm.getIsDispatched().length != 0) { int []ids =
				 * patientForm.getIsDispatched(); for(int i = 0; i < ids.length; i++) {
				 * patientDAOInf.updateDispatchedDetails(ids[i]); } }
				 */

				String[] labTest = patientForm.getLabTestDetails().split("\\*");

				for (int i = 0; i < labTest.length; i++) {

					System.out.println("labTest1111 : " + labTest[i] + "--" + patientForm.getLabTestDetails());

					String testsValue = labTest[i];

					if (!testsValue.isEmpty()) {
						String[] testArray = testsValue.split("\\$");

						int groupCheck = 0;
						System.out.println("test ::: " + testArray[7]);
						groupCheck = Integer.parseInt(testArray[7]);

						if (groupCheck == 1) {
							String groupName = testArray[0];
							double groupRate = Double.parseDouble(testArray[1]);
							String[] test = testArray[2].split(",");

							String[] normalRange = testArray[3].split(",");
							String subGroupValues = "";

							String[] subGroup = testArray[4].split(",");
							for (int k = 0; k < subGroup.length; k++) {
								subGroupValues = subGroup[k];
							}
							String[] testType = testArray[5].split(",");

							String[] rate = testArray[6].split(",");
							for (int j = 0; j < test.length; j++) {
								double rate1 = Double.parseDouble(rate[j]);

								statusMessage = patientDAOInf.insertLabInvestigationDetails(test[j], null,
										normalRange[j], patientForm.getVisitID(), null, rate1, groupCheck, groupRate,
										groupName, subGroupValues, testType[j]);
							}

						} else {
							String groupName = testArray[0];
							double groupRate = Double.parseDouble(testArray[1]);
							String test = testArray[2];

							String normalRange = testArray[3];

							String subGroup = "";

							subGroup = testArray[4];

							String testType = testArray[5];

							double rate = Double.parseDouble(testArray[6]);
							statusMessage = patientDAOInf.insertLabInvestigationDetails(test, null, normalRange,
									patientForm.getVisitID(), null, rate, groupCheck, groupRate, groupName, subGroup,
									testType);
						}
					}

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						System.out.println("Failed to add lab tests.");
					}
				}

			}

			if (patientForm.getTest() == null) {
				// System.out.println("No tests added");

				statusMessage = "success";

			} else {

				// System.out.println("labTest length:
				// "+patientForm.getTest().length+"-"+patientForm.getTest());

				for (int i = 0; i < patientForm.getTest().length; i++) {

					String normalValue = patientForm.getNormalVal()[i];

					// System.out.println("normalValue: "+normalValue);

					/*
					 * Inserting lab tests details into LabInvestigation details table
					 */
					statusMessage = patientDAOInf.insertLabInvestigationDetails(patientForm.getTest()[i],
							patientForm.getObservationsValue()[i], normalValue, patientForm.getVisitID(), null,
							Double.parseDouble(patientForm.getRateVal()[i]),
							Integer.parseInt(patientForm.getIsGroupVal()[i]),
							Double.parseDouble(patientForm.getGroupRateVal()[i]), patientForm.getGroupNameVal()[i],
							patientForm.getSubGroupVal()[i], patientForm.getTestTypeArr()[i]);

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						// System.out.println("Failed to add lab tests.");
					}
				}

			}

			if (patientForm.getInvestigationDetails() == null) {
				// System.out.println("No tests added");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getInvestigationDetails().length; i++) {
					int investigationID = Integer.parseInt(patientForm.getInvestigationDetails()[i]);

					// System.out.println("ObservationsValue:
					// "+patientForm.getObservationsValue()[i]+"--"+patientForm.getInvestigationDetails().length);
					String value = patientForm.getObservationsValue()[i];
					/*
					 * Inserting lab tests details into LabInvestigation details table
					 */
					statusMessage = patientDAOInf.updateLabInvestigationDetails(investigationID, value);

					if (statusMessage.equalsIgnoreCase("success")) {
						statusMessage = "success";
					} else {
						statusMessage = "error";

						// System.out.println("Failed to update lab tests.");
					}
				}

			}

		} else {

			statusMessage = "error";

		}

		// Saving data for templates
		if (patientForm.getTemplateArr() != null) {

			for (int i = 0; i < patientForm.getReportIDArr().length; i++) {

				int visitTemplateID = Integer.parseInt(patientForm.getVisitTemplateIDArr()[i]);

				if (visitTemplateID > 0) {
					int clinicianID = 0;

					if (patientForm.getClinicianIDArr()[i] == null || patientForm.getClinicianIDArr()[i] == "") {
						clinicianID = 0;
					} else if (patientForm.getClinicianIDArr()[i].isEmpty()) {
						clinicianID = 0;
					} else {
						clinicianID = Integer.parseInt(patientForm.getClinicianIDArr()[i]);
					}

					statusMessage = patientDAOInf.updateVisitTemplateData(patientForm.getTemplateArr()[i],
							Integer.parseInt(patientForm.getReportIDArr()[i]), patientForm.getVisitID(), clinicianID,
							Integer.parseInt(patientForm.getInvestigationIDArr()[i]), visitTemplateID);

				} else {
					int clinicianID = 0;

					if (patientForm.getClinicianIDArr()[i] == null || patientForm.getClinicianIDArr()[i] == "") {
						clinicianID = 0;
					} else if (patientForm.getClinicianIDArr()[i].isEmpty()) {
						clinicianID = 0;
					} else {
						clinicianID = Integer.parseInt(patientForm.getClinicianIDArr()[i]);
					}

					statusMessage = patientDAOInf.insertVisitTemplateData(patientForm.getTemplateArr()[i],
							Integer.parseInt(patientForm.getReportIDArr()[i]), patientForm.getVisitID(), clinicianID,
							Integer.parseInt(patientForm.getInvestigationIDArr()[i]));
				}

			}

		}

		return statusMessage;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.service.eDhanvantariServiceInf#
	 * retrievePrescriptionBillForEdit(int, double, int, java.lang.String,
	 * java.lang.String, int)
	 */
	public JSONObject retrieveLabBillDetails(int visitID, double consultationCharge, int clinicID, String clinicSuffix,
			int visitTypeID, String careType) {

		JSONObject values = new JSONObject();

		JSONObject object = null;

		JSONArray array = new JSONArray();

		patientDAOInf = new PatientDAOImpl();

		try {

			/*
			 * Retrieving consultation charges from Prescription based on visitID
			 */
			consultationCharge = patientDAOInf.retrieveConsultationChargesFromPrescription(visitID);

			// System.out.println("Consultation charges from prescription is ..." +
			// consultationCharge);

			List<PatientForm> billList = patientDAOInf.retrieveBillDetail(visitID, visitTypeID, clinicID, clinicSuffix);

			List<PatientForm> labTestList = patientDAOInf.retrieveTransactionDetailsByVisitID1(visitID, careType);

			/*
			 * retrieving patientID based on visitID
			 */
			int patientID = patientDAOInf.retrievePatientIDByVisitID(visitID);

			String patientNameArr[] = patientDAOInf.retrievePatientFNameLNameByID(patientID).split("=");

			String fName = patientNameArr[0];

			String lName = patientNameArr[1];

			for (PatientForm form : billList) {

				for (PatientForm patientForm : labTestList) {

					object = new JSONObject();

					object.put("patientID", patientID);
					object.put("visitID", visitID);
					object.put("receiptID", form.getReceiptID());
					object.put("receiptNo", form.getReceiptNo());
					object.put("receiptDate", form.getReceiptDate());
					object.put("fistName", fName);
					object.put("lastName", lName);
					object.put("billingType", form.getBillingType());
					object.put("totalAmt", form.getTotalAmount());
					object.put("netAmount", form.getNetAmount());
					object.put("totalDiscount", form.getTotalDiscount());
					object.put("advPayment", form.getAdvPayment());
					object.put("balPayment", form.getBalPayment());
					object.put("paymentType", form.getPaymentType());
					object.put("cashPaid", form.getCashPaid());
					object.put("cashToReturn", form.getCashToReturn());
					object.put("cheqIssueBy", form.getChequeIssuedBy());
					object.put("cheqNo", form.getChequeNo());
					object.put("cheqDate", form.getChequeDate());
					object.put("cheqBankName", form.getChequeBankName());
					object.put("cheqBankBranch", form.getChequeBankBranch());
					object.put("cheqAmt", form.getChequeAmt());
					object.put("cardAmt", form.getCardAmount());
					object.put("cardNo", form.getCardMobileNo());
					object.put("cardMobileNo", form.getCMobileNo());
					object.put("otherType", form.getOtherType());
					object.put("otherAmt", form.getOtherAmount());
					if (careType == "PathologyLab") {
						object.put("testName", patientForm.getCBCProfileTest());
						object.put("rate", patientForm.getRate());
						object.put("normalValues", patientForm.getCBCProfileNormalValue());
					} else {
						object.put("testName", patientForm.getVisitType());
						object.put("rate", patientForm.getVisitRate());
					}
					array.add(object);

					values.put("Release", array);

				}

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving bill details based visitID for edit bill");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public String addTemplate(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * verify whether Category with same name already exists into Category table or
		 * not, if so, then give error msg else proceed further to add Category details
		 * into Category table
		 */
		// boolean check =
		// configurationDAOInf.verifyTemplateNameAlreadyExists(configurationForm.
		// getTempReportType());

		// if (check) {

		// System.out.println("Template Name already exists into Template table.");

		// statusMessage = "input";

		// return statusMessage;

		// } else {

		/*
		 * Inserting Category details into Category table
		 */
		statusMessage = configurationDAOInf.insertTemplate(configurationForm.getTempReportType(),
				configurationForm.getTempName(), configurationForm.getTemplate());

		System.out.println("------------------------------------------" + configurationForm.getTemplate());
		if (statusMessage.equalsIgnoreCase("success")) {

			return statusMessage;

		} else {

			System.out.println("Failed to insert Template details.");

			statusMessage = "error";

			return statusMessage;

		}

//		}
	}

	public String editTemplate(ConfigurationForm configurationForm) {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Inserting Category details into Category table
		 */
		statusMessage = configurationDAOInf.updateTemplate(configurationForm.getTempReportType(),
				configurationForm.getTemplateID(), configurationForm.getTempName(), configurationForm.getTemplate());

		if (statusMessage.equalsIgnoreCase("success")) {

			return statusMessage;

		} else {

			System.out.println("Failed to update Template details.");

			statusMessage = "error";

			return statusMessage;
		}

	}

	@Override
	public String addBDPReportData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		String statusMessage = "";
		patientDAOInf = new PatientDAOImpl();

		int lastVisitID = 0;

		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		statusMessage = patientDAOInf.insertVisitBDPData(patientForm, visitNumber);

		if (statusMessage == "success") {

			boolean check = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

			if (!check) {
				patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
			}

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getVisitTypeID(), patientForm.getClinicID());

			patientForm.setVisitID(lastVisitID);

			/*
			 * Insert Vital Signs Details For Visit into VitalSigns table.
			 */
			statusMessage = patientDAOInf.insertVitalSignsDetailsForVisit(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Insert Symptom Check Details For Visit into SymptomCheck table.
				 */

				if (patientForm.getVisitID() == 0) {

					System.out.println("No visit added.");

					statusMessage = "noVisit";

					return statusMessage;
				}

			} else {
				statusMessage = "error";
			}

			/*
			 * Insert Personal History Details into Personalhistory table.
			 */

			statusMessage = patientDAOInf.insertPersonalHistory(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {
				System.out.println("Personal History added Successfully");

			} else {

				statusMessage = "error";
				System.out.println("Failed to add Personal History");
			}

			/*
			 * check whether complaint rows added or not and depending upon that proceeding
			 * further to insert records into presentcomplaints table
			 */
			if (patientForm.getNewSymptom() == null) {
				System.out.println("No complaint added into Present Complaint");

				statusMessage = "success";

			} else {
				System.out.println("complaint list length::" + patientForm.getNewSymptom().length);
				for (int i = 0; i < patientForm.getNewSymptom().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertComplaints(patientForm.getNewSymptom()[i], duration,
							patientForm.getPnewComments()[i], patientForm.getVisitID());
				}

			}

			/*
			 * check whether medical history rows added or not and depending upon that
			 * proceeding further to insert records into medicalhistory table
			 */
			if (patientForm.getNewDiagnosis() == null) {
				System.out.println("No history added into medical history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDiagnosis().length; i++) {

					statusMessage = patientDAOInf.insertMedicalHistory(patientForm.getNewDiagnosis()[i],
							patientForm.getNewDescription()[i], patientForm.getPatientID());
				}

			}

			/*
			 * check whether current medication rows added or not and depending upon that
			 * proceeding further to insert records into prescriptionhistory table
			 */
			if (patientForm.getNewDrugname() == null) {
				System.out.println("No medication details added into prescription history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDrugname().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertCurrentMedication(patientForm.getNewDrugname()[i], duration,
							patientForm.getCnewComments()[i], patientForm.getPatientID());
				}
			}

			return statusMessage;

		} else {
			statusMessage = "error";
			return statusMessage;
		}

	}

	@Override
	public String addUSGReportData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		String statusMessage = "";
		patientDAOInf = new PatientDAOImpl();

		int lastVisitID = 0;

		int visitNumber = patientDAOInf.retrieveVisitNumber(patientForm.getPatientID(), patientForm.getClinicID());

		// visitNumber += 1;

		statusMessage = patientDAOInf.insertVisitUSGData(patientForm, visitNumber);

		if (statusMessage == "success") {
			boolean check = configXMLUtil.verifyDiagnosisExits(patientForm.getCancerType());

			if (!check) {
				patientDAOInf.insertDiagnosisInPVDiagnosisTable(patientForm.getCancerType());
			}

			/*
			 * retrieving last entered visit ID based on new visitNumber
			 */
			lastVisitID = patientDAOInf.retrieveLastEnteredVisitIDByVisitNumber(visitNumber, patientForm.getPatientID(),
					patientForm.getVisitTypeID(), patientForm.getClinicID());

			patientForm.setVisitID(lastVisitID);

			statusMessage = patientDAOInf.insertSonsDaughtersData(patientForm);
			/*
			 * Insert Vital Signs Details For Visit into VitalSigns table.
			 */
			statusMessage = patientDAOInf.insertVitalSignsDetailsForVisit(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {

				/*
				 * Insert Symptom Check Details For Visit into SymptomCheck table.
				 */

				if (patientForm.getVisitID() == 0) {

					System.out.println("No visit added.");

					statusMessage = "noVisit";

					return statusMessage;
				}

			} else {
				statusMessage = "error";
			}

			/*
			 * Insert Personal History Details into Personalhistory table.
			 */

			statusMessage = patientDAOInf.insertPersonalHistory(patientForm);

			if (statusMessage.equalsIgnoreCase("success")) {
				System.out.println("Personal History added Successfully");

			} else {

				statusMessage = "error";
				System.out.println("Failed to add Personal History");
			}

			/*
			 * check whether complaint rows added or not and depending upon that proceeding
			 * further to insert records into presentcomplaints table
			 */
			if (patientForm.getNewSymptom() == null) {
				System.out.println("No complaint added into Present Complaint");

				statusMessage = "success";

			} else {
				System.out.println("complaint list length::" + patientForm.getNewSymptom().length);
				for (int i = 0; i < patientForm.getNewSymptom().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertComplaints(patientForm.getNewSymptom()[i], duration,
							patientForm.getPnewComments()[i], patientForm.getVisitID());
				}

			}

			/*
			 * check whether medical history rows added or not and depending upon that
			 * proceeding further to insert records into medicalhistory table
			 */
			if (patientForm.getNewDiagnosis() == null) {
				System.out.println("No history added into medical history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDiagnosis().length; i++) {

					statusMessage = patientDAOInf.insertMedicalHistory(patientForm.getNewDiagnosis()[i],
							patientForm.getNewDescription()[i], patientForm.getPatientID());
				}

			}

			/*
			 * check whether current medication rows added or not and depending upon that
			 * proceeding further to insert records into prescriptionhistory table
			 */
			if (patientForm.getNewDrugname() == null) {
				System.out.println("No medication details added into prescription history");

				statusMessage = "success";

			} else {

				for (int i = 0; i < patientForm.getNewDrugname().length; i++) {

					String newDuration = patientForm.getNewDuration()[i];

					int duration = 0;
					if (newDuration == null || newDuration == "") {
						duration = 0;
					} else if (newDuration.isEmpty()) {
						duration = 0;
					} else {
						duration = Integer.parseInt(newDuration);
					}

					statusMessage = patientDAOInf.insertCurrentMedication(patientForm.getNewDrugname()[i], duration,
							patientForm.getCnewComments()[i], patientForm.getPatientID());
				}
			}

			return statusMessage;

		} else {
			statusMessage = "error";
			return statusMessage;
		}

	}

	public String UpdateBDPReportData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		patientDAOInf = new PatientDAOImpl();
		String statusMessage = "";
		statusMessage = patientDAOInf.UpdateVisitBDPData(patientForm);

		return statusMessage;
	}

	public String UpdateUSGBDPReportData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		patientDAOInf = new PatientDAOImpl();
		String statusMessage = "";
		statusMessage = patientDAOInf.UpdateUSGVisitBDPData(patientForm);

		return statusMessage;
	}

	@Override
	public String addOTType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether OT type with same name already exists or not, if exists
		// then giving error message else proceeding further to add the new OT type
		boolean check = prescriptionManagementDAOInf.verifyOTTypeExists(form.getOTType(), form.getPracticeID());

		if (check) {

			System.out.println("Same OT type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting OT type details into PVOTTypes table
			statusMessage = prescriptionManagementDAOInf.insertOTType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert OT type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	@Override
	public String editOTType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether OT type with same name already exists or not for the
		// other OTTypeID, if exists
		// then giving error message else proceeding further to edit OT type
		boolean check = prescriptionManagementDAOInf.verifyOTTypeExists(form.getOTType(), form.getOTTypeID(),
				form.getPracticeID());

		if (check) {

			System.out.println("Same OT type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting OT type details into PVOTType table
			statusMessage = prescriptionManagementDAOInf.updateOTType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update OT type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	@Override
	public String addServiceType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether service type with same name already exists or not, if
		// exists
		// then giving error message else proceeding further to add the service OT type
		boolean check = prescriptionManagementDAOInf.verifyServiceTypeExists(form.getServiceType(),
				form.getPracticeID());

		if (check) {

			System.out.println("Same Service type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting service type details into PVServiceTypes table
			statusMessage = prescriptionManagementDAOInf.insertServiceType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert Service type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	@Override
	public String editServiceType(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether service type with same name already exists or not for the
		// other serviceTypeID, if exists
		// then giving error message else proceeding further to edit service type
		boolean check = prescriptionManagementDAOInf.verifyServiceTypeExists(form.getServiceType(),
				form.getServiceTypeID(), form.getPracticeID());

		if (check) {

			System.out.println("Same Service type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting Service type details into PVServiceType table
			statusMessage = prescriptionManagementDAOInf.updateServiceType(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update Service type Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	@Override
	public String addRoomCharges(PrescriptionManagementForm form, int practiceID) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// Verifying whether Room charges with same room name and roomTypeID already
		// exists into
		// PVRoomCharges table if yes then give error message else proceed further to
		// add
		// the details into PVRoomCharges table
		boolean check = prescriptionManagementDAOInf.verifyRoomChargesExists(form.getName(), form.getRoomTypeID(),
				practiceID);

		if (check) {

			System.out.println("Room charges with same room name and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVRoomCharges table
			statusMessage = prescriptionManagementDAOInf.insertRoomCharges(form, practiceID);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to insert room charges Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

	@Override
	public String editRoomCharges(PrescriptionManagementForm form) {
		prescriptionManagementDAOInf = new PrescriptionManagementDAOImpl();

		// verifying whether room charges with same room name and roomType already
		// exists for the other available room charges if yes then give error message
		// else proceed further to update the details in PVRoomCharges table for the
		// corresponding chargeID
		boolean check = prescriptionManagementDAOInf.verifyRoomChargesExists(form.getName(), form.getRoomTypeID(),
				form.getRoomChargesID(), form.getPracticeID());

		if (check) {

			System.out.println("Room charges with same room name and room type already exists");

			statusMessage = "input";

			return statusMessage;

		} else {

			// inserting room type details into PVRoomCharges table
			statusMessage = prescriptionManagementDAOInf.updateRoomCharges(form);

			if (statusMessage.equals("success")) {
				return statusMessage;
			} else {

				System.out.println("Failed to update Room charges Details.");

				statusMessage = "error";

				return statusMessage;

			}

		}

	}

}