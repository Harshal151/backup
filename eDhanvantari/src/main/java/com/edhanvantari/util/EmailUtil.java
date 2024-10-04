package com.edhanvantari.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.StringWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoImpl.PrescriptionManagementDAOImpl;
import com.edhanvantari.daoInf.ListnerDOAInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.PrescriptionManagementDAOInf;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.daoImpl.RegistrationDAOImpl;
import com.edhanvantari.form.LoginForm;

public class EmailUtil extends DAOConnection {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	String status = "error";

	PrescriptionManagementDAOInf managementDAOInf = new PrescriptionManagementDAOImpl();

	PatientDAOInf patientDAOInf = null;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	ConfigurationUtil util = new ConfigurationUtil();

	/**
	 * 
	 * @param fromEmailID
	 * @param toEmailID
	 * @param fromEmailPassword
	 * @param clinicName
	 * @param supplierName
	 * @param realPath
	 * @param fileName
	 * @param orderByName
	 * @param orderNo
	 * @return
	 */
	public String sendOrderMail(String fromEmailID, String toEmailID, String fromEmailPassword, String clinicName,
			String supplierName, String realPath, String fileName, String orderByName, String orderNo) {

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

		final String fromEmail = fromEmailID; // Valid email id of Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = fromEmailPassword; // valid password for
														// fromEmail

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, clinicName));

			msg.setSubject("New Order", "UTF-8");

			msg.setSentDate(new Date());

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			/*
			 * messageBodyPart.setText("Dear " + supplierName + "," + "\n\tAn order #" +
			 * orderNo + " for your products created by " + orderByName + " from " +
			 * clinicName +
			 * " has been attached to this email. Please process the order on priority.\n\tContact us in case of any queries.\n\nThanks,\nClinic Administrator"
			 * );
			 */
			messageBodyPart.setText("Dear " + supplierName + "," + "\n\t Please check and download an order #" + orderNo
					+ " for your products created by " + orderByName + " from " + clinicName + " from the below URL.\n"
					+ s3.getUrl(bucketName + "/" + s3reportFilePath, fileName)
					+ "\nPlease process the order on priority. Contact us in case of any queries."
					+ "\n\nThanks,\nClinic Administrator");

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			// messageBodyPart = new MimeBodyPart();
			// filename = fileName;
			// String filePath = realPath + fileName;
			// DataSource source = new FileDataSource(filePath);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			// multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Order mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public void sendExceptionInfoEmail(String exceptionMessage, String actionName) {

		RegistrationDAOinf daOinf = new RegistrationDAOImpl();

		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session1 = request.getSession();

		LoginForm loginForm = (LoginForm) session1.getAttribute("USER");

		patientDAOInf = new PatientDAOImpl();

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(loginForm.getPracticeID()); // Valid
		// email
		// id
		// of
		// Gmail

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(loginForm.getPracticeID()); // valid
		// password
		// for

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID());

		// retrieving username from logged in user's ID
		String userName = daOinf.retrieveUsernameByUserID(loginForm.getUserID());

		String emailToID = "info@kovidbioanalytics.com";

		String subject = actionName;

		String emailBody = "Hello,\nThis is the exception/error occured while performing action: " + actionName
				+ " by user: " + userName + ".\n\nException\n" + exceptionMessage;

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailToID, false));

			Transport.send(msg);

			System.out.println("Exception info mail sent successfully to info@kovidbioanalytics.com");

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			EmailUtil emailUtil1 = new EmailUtil();
			emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Send Exception Info Email: Exception");

			exception.printStackTrace();
		}

	}

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @param toEmailID
	 * @param patientID
	 * @param apptDate
	 * @param aptTime
	 * @param appointmentType
	 * @return
	 */
	public String sendAppointmentEmail(int practiceID, int clinicID, String toEmailID, int patientID, String apptDate,
			String aptTime, String appointmentType) {

		patientDAOInf = new PatientDAOImpl();

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
																								// email
																								// id
																								// of
																								// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
																										// password
																										// for
		// fromEmail

		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		String emailBody = "";

		if (appointmentType.equals(ActivityStatus.SCHEDULED)) {

			emailBody = "Dear " + patientName + ",\n\tYour appointment has been scheduled for " + practiceName + " at "
					+ clinicName + " at " + apptDate + " " + aptTime
					+ ". Please contact our clinic in case of any changes.\n\nThanks,\nClinic Administrator";

		} else if (appointmentType.equals(ActivityStatus.UPDATED)) {

			emailBody = "Dear " + patientName + ",\n\tYour appointment for " + practiceName + " at " + clinicName
					+ " has been updated to " + apptDate + " " + aptTime
					+ ". Please contact our clinic in case of any changes.\n\nThanks,\nClinic Administrator";

		} else if (appointmentType.equals(ActivityStatus.CANCELLED)) {

			emailBody = "Dear " + patientName + ",\n\tYour appointment for " + practiceName + " at " + clinicName
					+ " scheduled at " + apptDate + " " + aptTime
					+ " has been cancelled. Please contact our clinic in case of any changes.\n\nThanks,\nClinic Administrator";

		}

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Appointment " + appointmentType, "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Appointment " + appointmentType + " mail sent successfully.");

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
	 * @param clinicID
	 * @param visitID
	 * @param realPath
	 * @param fileName
	 * @param toEmailID
	 * @param type
	 * @return
	 */
	public String sendPrescriptionMail(int patientID, int practiceID, int clinicID, int visitID, String realPath,
			String fileName, String toEmailID) {

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

		System.out.println("s3 url::" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName));

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail

		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		String emailBody = "";

		// retrieving visit date and time based on visitID from Visit table
		String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

		String prescFileURL = "https://demo.edhanvantari.com/eDhanvantari/ViewFiles?pdfOutPath=" + fileName
				+ "&bucketName=" + bucketName;

		/*
		 * emailBody = "Dear " + patientName +
		 * ",\n\t Please check and download your prescription for the visit with " +
		 * practiceName + " at " + clinicName + " on " + dateAndTime +
		 * " from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath,
		 * fileName) + "\n\nThanks,\nClinic Administrator";
		 */

		emailBody = "Dear " + patientName + ",<br><br>Please check and download your prescription for the visit with "
				+ practiceName + " at " + clinicName + " on " + dateAndTime + " from the below URL.<br><a href='"
				+ prescFileURL + "'>Click here to view your prescription.</a><br><br>Thanks,<br>Clinic Administrator";

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Prescription", "UTF-8");

			msg.setSentDate(new Date());

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(emailBody);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			// multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = fileName;
			// String filePath = fileName;
			// DataSource source = new FileDataSource(filePath);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(emailBody, "text/html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Prescription mail sent successfully.");

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
	 * @param clinicID
	 * @param visitID
	 * @param realPath
	 * @param fileName
	 * @param toEmailID
	 * @return
	 */
	public String sendBillingMail(int patientID, int practiceID, int clinicID, int visitID, String fileName,
			String toEmailID) {

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

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail

		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		String emailBody = "";

		// retrieving visit date and time based on visitID from Visit table
		String dateAndTime = patientDAOInf.retrieveVisitDateAndTime(visitID);

		// retrieving bill amount based on visitID from Receipt table
		double billAmt = patientDAOInf.retrieveBillAmountByVisitID(visitID);

		String prescFileURL = "https://demo.edhanvantari.com/eDhanvantari/ViewFiles?pdfOutPath=" + fileName
				+ "&bucketName=" + bucketName;

		/*
		 * emailBody = "Dear " + patientName +
		 * ",\n\tPlease check and download your bill for the visit with " + practiceName
		 * + " at " + clinicName + " on " + dateAndTime + " from the below URL." +
		 * s3.getUrl(bucketName + "/" + s3reportFilePath, fileName) +
		 * "\n\nThanks,\nClinic Administrator";
		 */

		emailBody = "Dear " + patientName + ",<br><br>Please check and download your bill for the visit with "
				+ practiceName + " at " + clinicName + " on " + dateAndTime + " from the below URL.<br><a href='"
				+ prescFileURL + "'>Click here to view your bill.</a><br><br>Thanks,<br>Clinic Administrator";

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Consultation Bill", "UTF-8");

			msg.setSentDate(new Date());

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(emailBody);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			// multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = fileName;
			// String filePath = realPath + fileName;
			// DataSource source = new FileDataSource(filePath);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(emailBody, "text/html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("bill mail sent successfully.");

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
	 * @param emailToID
	 * @return
	 */
	public String sendWelcomeMail(int patientID, int practiceID, String emailToID, int clinicID) {

		patientDAOInf = new PatientDAOImpl();

		// retrieving username and password by patientid

		/*
		 * String uname = patientDAOInf.retrieveUsernameByID(patientID); String passw =
		 * patientDAOInf.retrievePasswordByID(patientID); String decryptedPassword =
		 * null; decryptedPassword =EncDescUtil.DecryptText(passw);
		 * System.out.println("Decrypted password is::::::" + decryptedPassword);
		 * 
		 * String loginURL = "https://demo.edhanvantari.com/PatientLogin";
		 */ /* "http://localhost:8081/eDhanvantari/PatientLogin"; */
		// (while deploying on demo -- put URL of demo server with /Action name)

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = emailToID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail
		System.out.println("from email:" + fromEmail + " to email:" + toEmail + " pass:" + fromEmailPass);
		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// Retrieving patient registration no based on patientID
		// String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID,
		// patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String emailBody = "";

		/*
		 * emailBody = "Dear " + patientName + ",\n\tWelcome to " + practiceName +
		 * ". Your ID in our system is  " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping in future."
		 * + System.lineSeparator() + "Your Username = " + uname + " "+
		 * System.lineSeparator() +"Password =  " + decryptedPassword + " "+
		 * System.lineSeparator() + "Login URL is = " + loginURL +
		 * "\n\nThanks,\nClinic Administrator";
		 */

		emailBody = "Dear " + patientName + ", welcome to " + practiceName
				+ " on eDhanvantari (a Kovid BioAnalytics platfom). You can login to our patient portal at edhanvantari.com for accessing your records.";

		/*
		 * emailBody = "Dear " + patientName + ",\n\tWelcome to " + practiceName +
		 * ". Your ID in our system is  " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping in future.\n\nThanks,\nClinic Administrator"
		 * ;
		 */

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details

			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Welcome to " + practiceName, "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Welcome mail sent successfully.");

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
	 * @param emailToID
	 * @param clinicID
	 * @return
	 */
	public String sendPatientPortalCredentialsMail(int patientID, int practiceID, String emailToID, int clinicID) {

		patientDAOInf = new PatientDAOImpl();

		// retrieving username and password by patientid

		String uname = patientDAOInf.retrieveUsernameByID(patientID);
		String passw = patientDAOInf.retrievePasswordByID(patientID);
		String decryptedPassword = null;
		decryptedPassword = EncDescUtil.DecryptText(passw);
		System.out.println("Decrypted password is::::::" + decryptedPassword);

		String loginURL = "https://demo.edhanvantari.com/eDhanvantari/PatientLogin";
		/* "http://localhost:8081/eDhanvantari/PatientLogin"; */
		// (while deploying on demo -- put URL of demo server with /Action name)

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = emailToID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail
		System.out.println("from email:" + fromEmail + " to email:" + toEmail + " pass:" + fromEmailPass);
		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// Retrieving patient registration no based on patientID
		// String regNo = patientDAOInf.retrieveClinicRegNoByClinicID(clinicID,
		// patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String emailBody = "";

		/*
		 * emailBody = "Dear " + patientName + ",\n\tWelcome to " + practiceName +
		 * ". Your ID in our system is  " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping in future."
		 * + System.lineSeparator() + "Your Username = " + uname + " "+
		 * System.lineSeparator() +"Password =  " + decryptedPassword + " "+
		 * System.lineSeparator() + "Login URL is = " + loginURL +
		 * "\n\nThanks,\nClinic Administrator";
		 */

		emailBody = "Dear " + patientName
				+ ", you can now login to the edhanvantari.com (a Kovid BioAnalytics platform) using:\nUsername - "
				+ uname + "\nPassword - " + decryptedPassword;

		/*
		 * emailBody = "Dear " + patientName + ",\n\tWelcome to " + practiceName +
		 * ". Your ID in our system is  " + regNo +
		 * ". Please ensure that you remember it for easy appointments and record keeping in future.\n\nThanks,\nClinic Administrator"
		 * ;
		 */

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details

			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Welcome to " + practiceName, "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Welcome mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param patientID
	 * @param toEmailID
	 * @param practiceID
	 * @param doctorName
	 * @return
	 */
	public String sendReferringDoctorMail(int patientID, String toEmailID, int practiceID, String doctorName) {

		patientDAOInf = new PatientDAOImpl();

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail

		// Retrieving patient first and last name based on patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		String emailBody = "";

		emailBody = "Dear Dr. " + doctorName + ",\n\tThank you for referring " + patientName
				+ " to our clinic.\n\nThanks,\n" + practiceName;

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Thanks for referring", "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Thanks to referring doctor mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param practiceID
	 * @param toEmailID
	 * @param clinicianName
	 * @param contextPath
	 * @return
	 */
	public String sendAppointmentReminderEmail(int practiceID, String toEmailID, String clinicianName,
			String contextPath) {

		ListnerDOAInf listnerDOAInf = new ListnerDAOImpl();

		// System.out.println(">>>>" + practiceID + "<<<<");

		final String fromEmail = listnerDOAInf.retrieveEmailFromByPracticeID(practiceID, contextPath); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		System.out.println("to email ID.." + toEmail);

		final String fromEmailPass = listnerDOAInf.retrieveEmailFromPassByPracticeID(practiceID, contextPath); // valid
		// password
		// for
		// fromEmail

		// System.out.println("Email credentials..." + fromEmail + " ... " +
		// fromEmailPass);

		String emailBody = "Dear Dr. " + clinicianName + ",\nPlease find the list of appointments scheduled today:\n";

		/*
		 * retrieving distinct of clinicID from Appointment table for todays appointment
		 * scheduled
		 */
		List<Integer> clinicIDList = listnerDOAInf.retrieveClinicIDForTodaysAppt(contextPath);

		if (clinicIDList == null || clinicIDList.size() == 0) {
			emailBody = emailBody.concat("");
		} else {

			for (Integer clinicID : clinicIDList) {

				String clinicName = "";

				emailBody = emailBody.concat("\n");

				/*
				 * Retrieving appointment list for clinicID for todays appointment
				 */
				List<String> appointmentList = listnerDOAInf.retrieveAppointmentListForClinicID(clinicID, contextPath);

				String apptString = "\nAppointment Details:";

				if (appointmentList == null) {
					apptString = apptString.concat("");
				} else {

					// Iterating over appointmentList in order to get apt start
					// and
					// end
					// time, clinic name, visit type
					for (String apptDetails : appointmentList) {

						apptString = apptString.concat("\n");

						/*
						 * Splitting apptDetails by = in order to get all details.
						 */
						String array[] = apptDetails.split("=");

						String aptFrom = array[0];
						String aptTo = array[1];

						int patientID = Integer.parseInt(array[2]);

						clinicName = array[3];

						String visitType = array[4];

						// Retrieving patient first and last name based on
						// patientID
						String patientName = listnerDOAInf.retrievePatientFirstLastNameRegNoByID(patientID, contextPath,
								clinicID);

						apptString = apptString
								.concat(aptFrom + " to " + aptTo + " - " + patientName + ", " + visitType);

					}

				}

				emailBody = emailBody.concat(clinicName + "" + apptString);
			}

		}

		emailBody = emailBody.concat("\nThanks,\nClinic Administrator");

		System.out.println("final msg body is ::: " + emailBody);

		/*
		 * Retrieving practice name based on practiceID
		 */
		String practiceName = listnerDOAInf.retrievePracticeNameByID(practiceID, contextPath);

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Appointment Reminder", "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Appointment reminder mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";

			System.out.println("Failed to send appointment reminder mail.");
		}

		return status;
	}

	/**
	 * 
	 * @param patientID
	 * @param toEmailID
	 * @param practiceID
	 * @return
	 */
	public String sendFeedbackMail(int patientID, String toEmailID, int practiceID) {

		patientDAOInf = new PatientDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// Retrieving patient first and last name by patientID
		String patientName = patientDAOInf.retrievePatientFirstLastNameByID(patientID);

		// retrieving feedback form URL
		String reviewFormURL = configurationUtil.getReviewFormURL();

		String emailBody = "";

		try {

			emailBody = "Dear " + patientName + ",\n\tYou have just finished your consultation at " + practiceName
					+ " through eDhanvantari (a Kovid BioAnalytics platform). Please take timely medicines as advised. Please confirm your next appointment\n\nThanks,\nClinic Administrator.";

			/*
			 * emailBody = "Dear " + patientName + ",\n\tThank you for visiting " +
			 * practiceName +
			 * ". Please provide your valuable feedback by visiting the following link:" +
			 * reviewFormURL + "\n\nThanks,\nClinic Administrator";
			 */

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject(practiceName + " | Thank you for your visit", "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Feedback mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param clinicianID
	 * @param practiceID
	 * @param clinicID
	 * @param realPath
	 * @param fileName
	 * @param toEmailID
	 * @param check
	 * @return
	 */
	public String sendAppointmentReportMailToHead(int clinicianID, int practiceID, int clinicID, String realPath,
			String fileName, String toEmailID, String check) {
		System.out.println("isnide send appo email");
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

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
		// email
		// id
		// of
		// Gmail

		final String toEmail = toEmailID; // any mail id

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
		// password
		// for
		// fromEmail

		// Retrieving clinician name based on clinicianID
		String cliniciaName = patientDAOInf.retrieveClinicianNameByID(clinicianID);

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		String emailBody = "";

		/*
		 * if (check.equals("Day")) { emailBody = "Dear Dr. " + cliniciaName +
		 * ",\n\tPlease find today's appointment report, scheduled " + "at " +
		 * clinicName + ", attached here.\n\nThanks,\nClinic Administrator"; } else if
		 * (check.equals("Week")) { emailBody = "Dear Dr. " + cliniciaName +
		 * ",\n\tPlease find this week's appointment report, scheduled " + "at " +
		 * clinicName + ", attached here.\n\nThanks,\nClinic Administrator"; } else {
		 * emailBody = "Dear Dr. " + cliniciaName +
		 * ",\n\tPlease find this month's appointment report, scheduled " + "at " +
		 * clinicName + ", attached here.\n\nThanks,\nClinic Administrator"; }
		 */

		if (check.equals("Day")) {
			emailBody = "Dear Dr. " + cliniciaName
					+ ",\n\t Please check and download today's appointment report, scheduled " + "at " + clinicName
					+ ", from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName)
					+ "\n\nThanks,\nClinic Administrator";
		} else if (check.equals("Week")) {
			emailBody = "Dear Dr. " + cliniciaName
					+ ",\n\t Please check and download this week's appointment report, scheduled " + "at " + clinicName
					+ ", from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName)
					+ "\n\nThanks,\nClinic Administrator";
		} else {
			emailBody = "Dear Dr. " + cliniciaName
					+ ",\n\t Please check and download this month's appointment report, scheduled " + "at " + clinicName
					+ ", from the below URL.\n" + s3.getUrl(bucketName + "/" + s3reportFilePath, fileName)
					+ "\n\nThanks,\nClinic Administrator";
		}

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			if (check.equals("Day")) {
				msg.setSubject("Today's Appointment Report", "UTF-8");
			} else if (check.equals("Week")) {
				msg.setSubject("This week's Appointment Report", "UTF-8");
			} else {
				msg.setSubject("This month's Appointment Report", "UTF-8");
			}

			msg.setSentDate(new Date());

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(emailBody);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = fileName;
			// String filePath = realPath + fileName;
			// DataSource source = new FileDataSource(filePath);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			// multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Appointment report mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param practiceID
	 * @param clinicID
	 * @param toEmailIDList
	 * @param productList
	 * @return
	 */
	public String sendProductAlertEmail(int practiceID, int clinicID, List<String> toEmailIDList,
			List<PatientForm> productList) {

		patientDAOInf = new PatientDAOImpl();

		final String fromEmail = managementDAOInf.retrieveEmailFromByPracticeID(practiceID); // Valid
																								// email
																								// id
																								// of
																								// Gmail

		final String fromEmailPass = managementDAOInf.retrieveEmailFromPassByPracticeID(practiceID); // valid
																										// password
																										// for

		// retrieving practiceName based on practiceID
		String practiceName = patientDAOInf.retrievePracticeNameByID(practiceID);

		// retrieving clinic name based on clinicID
		String clinicName = patientDAOInf.retrieveClinicNameByID(clinicID);

		int srNo = 1;

		String emailBody = "Hello,\n\tThe following products from the inventory have stock less than the minimum threshold. "
				+ "Please order new stock to avoid issues in prescription:\n\n";

		for (PatientForm patientForm : productList) {

			emailBody = emailBody.concat("" + srNo + ". Product Name: " + patientForm.getTradeName()
					+ ", Min. Quantity: " + patientForm.getMinQuantity() + ", Net Stock: " + patientForm.getQuantity()
					+ ", Supplier Name: " + patientForm.getSupplierName() + ", Clinic Name: " + patientForm.getClinic()
					+ "\n");

			srNo++;
		}

		emailBody = emailBody.concat("\nThanks,\nClinic Administrator");

		System.out.println("Product alert mail body:::" + emailBody);

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromEmail, practiceName));

			msg.setSubject("Quantity Alert for Inventory", "UTF-8");

			msg.setSentDate(new Date());

			msg.setText(emailBody);

			/*
			 * Creating array of InternetAddress in order to add multiple recipients
			 */
			InternetAddress[] address = new InternetAddress[toEmailIDList.size()];

			for (int i = 0; i < toEmailIDList.size(); i++) {
				address[i] = new InternetAddress(toEmailIDList.get(i));
			}

			msg.setRecipients(Message.RecipientType.TO, address);

			Transport.send(msg);

			status = "success";

			System.out.println("Product alert mail sent successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String sendReportMail(String toEmailID, String fromEmailID, String fromEmailPassword, String clinicName,
			String realPath, String fileName, String[] patientNameArr) {

		try {

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

			AmazonS3 s3 = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, fileName)).getObjectContent();

			IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" + fileName)));

			final String fromEmail = fromEmailID; // Valid email id of Gmail
			final String toEmail = toEmailID; // any mail id
			final String fromEmailPass = fromEmailPassword; // valid password for fromEmail

			String fName = patientNameArr[0];
			String lName = patientNameArr[1];

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); // SMTP Port
			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};
			// Creating mail session with authentication and host,port details
			Session session = Session.getInstance(props, auth);
			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("samarthlabs@edhanvantari.com", clinicName));
			msg.setSubject("Lab Report: " + fName + " " + lName, "UTF-8");
			msg.setSentDate(new Date());
			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText("Hello, " + "\n\tPlease find the attached lab report of " + fName + " " + lName
					+ "." + "\n\nThank You,\n" + clinicName + ".");

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = realPath + "images/" + fileName;
			// String filePath = realPath + fileName;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			// Send the complete message parts
			msg.setContent(multipart);
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
			status = "success";
			System.out.println("Report mail sent successfully.");
		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;
	}

}
