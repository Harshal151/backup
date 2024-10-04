package com.edhanvantari.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.edhanvantari.daoImpl.ConfigurationDAOImpl;
import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoInf.ConfigurationDAOInf;
import com.edhanvantari.daoInf.ListnerDOAInf;
import com.edhanvantari.util.SchedulerListnerUtil.MyRunnable;

public class AppointmentSchedulerJob implements Job {

	private static String contextPath = "";

	private static final int MYTHREADS = 30;

	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		contextPath = dataMap.getString("contextPath");

		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

		System.out.println("Appointment reminder scheduler started");

		String[] hostList = { updateAppointmentStatuses(contextPath), sendAppointmentSMSToPatient(contextPath), sendAppointmentMailToClinician(contextPath),
				sendAppointmentSMSToAllDoctor(contextPath) };

		for (int i = 0; i < hostList.length; i++) {

			String url = hostList[i];
			Runnable worker = new MyRunnable(url);
			executor.execute(worker);
		}

		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}

	}

	public static String method1(String contextPath) {
		System.out.println("Inside m1 " + contextPath);

		return "success";

	}

	public static String method2(String contextPath) {
		System.out.println("Inside m2 " + contextPath);

		return "success";
	}

	public static String sendAppointmentSMSToPatient(String contextPath) {

		// System.out.println("inside m1...");

		String message = "error";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Retrieving appointment list containing patientID, apptDate, apptTime,
		 * clinicID, practiceID
		 */
		List<String> appointmentList = doaInf.retrieveAppointmentListForReminderSMS(contextPath);

		// System.out.println("Appt list ... " + appointmentList);

		if (appointmentList == null || appointmentList.size() == 0) {
			System.out.println(
					"No patient's appointment is scheduled for today. Date - " + dateFormat.format(new Date()));

			message = "success";

			return message;
		} else {

			/*
			 * Iterating over appointmentList in order to get the appointment
			 * details scheduled for today
			 */
			for (String apptDetails : appointmentList) {

				String array[] = apptDetails.split("=");

				int patientID = Integer.parseInt(array[1]);

				int practiceID = Integer.parseInt(array[4]);

				int clinicID = Integer.parseInt(array[3]);

				/*
				 * Check whether daily appointment reminder SMS flag is on or
				 * not for current clinicID, if yes, then proceed further to
				 * send SMS else do nothing
				 */
				boolean SMSCheck = doaInf.verifyCommunication("smsDailyAppt", clinicID, contextPath);

				if (SMSCheck) {

					String mobileNo = doaInf.retrievePatientMobileNoByID(patientID, contextPath);

					if (mobileNo == null || mobileNo == "") {
						System.out.println("No mobile found for patient");
					} else {
						if (mobileNo.isEmpty()) {
							System.out.println("No mobile found for patient");
						} else {

							try {

								message = sendAppointmentReminderSMS(patientID, mobileNo, practiceID, clinicID,
										dateFormat.format(dateFormat1.parse(array[0])), array[2], contextPath);

							} catch (Exception exception) {
								exception.printStackTrace();

								message = "exception";
							}

						}
					}

				} else {
					System.out.println("Appointment reminder SMS flag is off");

					message = "success";
				}

			}

			return message;
		}

	}

	public static String sendAppointmentMailToClinician(String contextPath) {

		// System.out.println("inside m1...");

		String message = "error";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		EmailUtil emailUtil = new EmailUtil();

		/*
		 * Retrieving clinician list containing practiceID, mobile no, firstName
		 * and lastName
		 */
		List<String> clinicianList = doaInf.retrieveClinicianUserID(contextPath);

		if (clinicianList == null || clinicianList.size() == 0) {
			System.out.println("No clinicians found.");
		} else {

			/*
			 * Iterating over appointmentList in order to get the appointment
			 * details scheduled for today
			 */
			for (String clinicianDetails : clinicianList) {

				String array[] = clinicianDetails.split("=");

				String email = array[0];

				String clinicianName = array[2] + " " + array[3];

				int practiceID = Integer.parseInt(array[1]);

				// System.out.println("practiceID.." + practiceID);

				/*
				 * Checking daily email flag is on or not, if on then proceeding
				 * further to send email else do nothing
				 */
				boolean mailCheck = doaInf.verifyCommunicationByPracticeID("emailDailyAppt", practiceID, contextPath);

				if (mailCheck) {

					/*
					 * Check whether appointments exists for the particular
					 * practiceID, if exists then proceed further else do not
					 * proceed further
					 */
					boolean check = doaInf.verifyAppointmentExistsForPractice(practiceID, contextPath);

					if (check) {

						if (email == null || email == "") {
							System.out.println("No email found for clinician");
						} else {
							if (email.isEmpty()) {
								System.out.println("No email found for clinician");
							} else {

								try {

									message = emailUtil.sendAppointmentReminderEmail(practiceID, email, clinicianName,
											contextPath);

								} catch (Exception exception) {
									exception.printStackTrace();

									message = "exception";
								}

							}
						}

					} else {
						System.out.println("No apppintments found for practiceID: " + practiceID);
					}

				} else {
					System.out.println("Appointment reminder MAIL to doctor flag is off");

					message = "success";
				}

			}
		}

		return message;

	}

	/**
	 * Method to send SMS to patient about appointment
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
	public static String sendAppointmentReminderSMS(int patientID, String mobileNo, int practiceID, int clinicID,
			String apptDate, String aptTime, String contextPath) {

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		String status = "error";

		ConfigListenerUtil configListenerUtil = new ConfigListenerUtil();
		
		ConfigurationDAOInf configDoaInf = new ConfigurationDAOImpl();
		
		/*
		 * retrieving SMSURLDetails form Communication table  by practiceID
		 */
		
		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		String username =  SMSURLDetailsList.get("smsUsername");
		String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");

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
					+ URLEncoder.encode(password, "UTF-8") + "&source="
					+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
					+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
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
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public static String sendAppointmentSMSToAllDoctor(String contextPath) {

		System.out.println("inside m1...");

		String message = "error";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		//
		// SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		String SMSText = "You have following appointments at your clinic today: ";

		/*
		 * Retrieving distinct of defaultClinicID from AppUser table
		 */
		List<String> clinicIDList = doaInf.retrieveDefaultClinicIDList(contextPath);

		if (clinicIDList == null || clinicIDList.size() == 0) {
			System.out.println("No default clinic list found");

			message = "success";
		} else {

			for (String clinicID : clinicIDList) {

				String[] Value = clinicID.split("$");
						
				/*
				 * Check whether daily appointment reminder SMS flag is on or
				 * not for current clinicID, if yes, then proceed further to
				 * send SMS else do nothing
				 */
				boolean SMSCheck = doaInf.verifyCommunication("smsDailyAppt", Integer.parseInt(Value[0]), contextPath);

				if (SMSCheck) {

					/*
					 * Check whether appointment exists for clinicID , if
					 * exists, proceed further else do nothing
					 */
					boolean check = doaInf.verifyAppointmentExistsForClinic(Integer.parseInt(Value[0]), contextPath);
					if (check) {

						/*
						 * Retrieving clinicianID list from Appointment table
						 * based on clinicID
						 */
						List<Integer> clinicainIDList = doaInf.retrieveDistinctClinicianIDFromAppointment(contextPath,
								Integer.parseInt(Value[0]));

						if (clinicainIDList == null || clinicainIDList.size() == 0) {
							System.out.println("No clinician id details found");

							message = "success";
						} else {
							/*
							 * Again retrieving clinicianID list from
							 * Appointment table based on clinicID in order to
							 * create SMS text
							 */
							List<Integer> clinicainIDList1 = doaInf
									.retrieveDistinctClinicianIDFromAppointment(contextPath, Integer.parseInt(Value[0]));

							for (Integer clinicianID : clinicainIDList1) {
								// Retrieving clinician Name from Employee
								// table

								if (clinicianID == 0 || clinicianID == -1) {
									System.out.println("No clinician details found...");
								} else {

									String clinicianName = doaInf.retrieveClinicianNameByID(clinicianID, contextPath);

									// Retrieving appointment count for
									// retrieved clinicianID
									int apptCount = doaInf.retrieveAppointmentCountForClinician(clinicianID, Integer.parseInt(Value[0]),
											contextPath);

									// Retrieving patient name from Appointment
									// table based on clinicianID and clinicID

									String patientDetails = doaInf.retrievePatientDetailsForAppointment(Integer.parseInt(Value[0]),
											clinicianID, contextPath);

									// Appending all these values to form a
									// final SMS text
									SMSText = SMSText.concat(clinicianName + " - " + apptCount
											+ " appointments - Patients: " + patientDetails + "\n");

								}
							}

							for (Integer clinicainID : clinicainIDList) {

								System.out.println("Final text sms :: " + SMSText);

								if (clinicainID == 0 || clinicainID == -1) {
									System.out.println("No clinician details found...");
								} else {

									// Retrieving mobile no of clinician
									String mobileNo = doaInf.retrieveClinicianMobileNoByID(clinicainID, contextPath);

									if (mobileNo == null || mobileNo == "") {
										System.out.println("No mobile no found for clinician");

										message = "success";
									} else if (mobileNo.isEmpty()) {
										System.out.println("No mobile no found for clinician");

										message = "success";
									} else {

										try {

											message = sendAppointmentReminderSMSToDoctor(mobileNo, Integer.parseInt(Value[0]), Integer.parseInt(Value[1]), SMSText,
													contextPath);

										} catch (Exception exception) {
											exception.printStackTrace();

											message = "exception";
										}

									}

								}

							}
						}
					} else {

						System.out.println("No appointment exists for clinicID: " + clinicID);

						message = "success";

					}

				} else {
					System.out.println("Appointment reminder SMS to doctor flag is off");

					message = "success";
				}

			}

		}

		return message;
	}

	/**
	 * 
	 * @param mobileNo
	 * @param clinicID
	 * @param practiceID 
	 * @param SMSText
	 * @param contextPath
	 * @return
	 */
	public static String sendAppointmentReminderSMSToDoctor(String mobileNo, int clinicID, int practiceID, String SMSText,
			String contextPath) {

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		String status = "error";

		ConfigListenerUtil configListenerUtil = new ConfigListenerUtil();

		ConfigurationDAOInf configDoaInf = new ConfigurationDAOImpl();
		
		/*
		 * retrieving SMSURLDetails form Communication table  by practiceID
		 */
		
		HashMap<String, String> SMSURLDetailsList = configDoaInf.retrieveSMSURLDetailsByPracticeID(practiceID);

		String username =  SMSURLDetailsList.get("smsUsername");
		String password = SMSURLDetailsList.get("smsPassword");
		String URL = SMSURLDetailsList.get("smsURL");
		String senderID = SMSURLDetailsList.get("smsSenderID");

		// SMSText = "Dear " + patientName + ", you have an appointment
		// scheduled today at " + practiceName + ", "
		// + clinicName + " clinic at " + aptTime + ".";

		try {

			String SMSURL = URL + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&source="
					+ URLEncoder.encode(senderID, "UTF-8") + "&destination="
					+ URLEncoder.encode(mobileNo, "UTF-8") + "&message="
					+ URLEncoder.encode(SMSText, "UTF-8") + "&type=0&dlr=1";
			
			URL url2 = new URL(SMSURL);

			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

			if (connection.getResponseCode() == 200 || connection.getResponseMessage() == "OK") {

				System.out.println("Appointment reminder SMS to doctor sent seuccessfully.");

				status = "success";

				return status;

			} else {

				System.out.println("Failed to send Appointment reminder SMS to doctors.");

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
	 * @param contextPath
	 * @return
	 */
	public static String updateAppointmentStatuses(String contextPath) {

		String message = "error";
		
		System.out.println("Appointment booked scheduler");

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		// updating yesterday's booked appointment statuses to no show
		
		message = doaInf.updateBookedAppointmentStatus(contextPath);

		if (!message.equals("success")) {
			System.out.println("Failed to update yesterday's booked appointments to no show");
		}

		// updating yesterday's dispensed appointment statuses to done
		message = doaInf.updateDispensedAppointmentStatus(contextPath);

		if (!message.equals("success")) {
			System.out.println("Failed to update yesterday's dispensed appointments to done");
		}

		return message;

	}

}
