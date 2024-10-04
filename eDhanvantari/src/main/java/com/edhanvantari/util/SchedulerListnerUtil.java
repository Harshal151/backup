package com.edhanvantari.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoInf.ListnerDOAInf;

/**
 * Application Lifecycle Listener implementation class SchedulerListnerUtil
 * 
 */
public class SchedulerListnerUtil implements ServletContextListener {

	private static String contextPath = "";

	static String schedulerTime = "";

	static String schedulerTime1 = "";

	/**
	 * Default constructor.
	 */
	public SchedulerListnerUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("Inside Listner....");

		/*
		 * Getting realPaht from ServletContextEvent object
		 */
		contextPath = servletContextEvent.getServletContext().getRealPath("/");

		System.out.println("Context path from Listener ::: " + contextPath);

		// Creating object of Scheduler which is created as Task
		TimerTask LBSTimer = new ConcurrTask();

		// Creating object of java.util.Time class in order to run Scheduler
		// after a period of time mentioned in it's scheduler() method
		Timer timer = new Timer();

		// Setting task to be run after a period of time span.
		timer.schedule(LBSTimer, 0, 5000 * 60); // This scheduler will
												// run
												// every 5 mins

		/*
		 * Creating Quartz scheduler for Appointment reminder SMS and MAIL to patient
		 * and doctor respectively
		 */
		JobDetail appointmentJob = JobBuilder.newJob(AppointmentSchedulerJob.class)
				.usingJobData("contextPath", contextPath).withIdentity("apptJob", "apptGroup").build();

		// Trigger class is to execute the particular task for the mentioned
		// time interval(every morning 7AM), interval is given as
		// CronScheduleBuilder's
		// cronSchedule method, which is similar to crontab in linux
		Trigger apptTrigger = TriggerBuilder.newTrigger().withIdentity("apptTrigger", "apptGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 7 * * ?")).build();

		System.out.println(".....Appointment reminder.." + apptTrigger.getFireTimeAfter(apptTrigger.getStartTime()));

		try {

			// Scheduling appointment job and apptTrigger
			Scheduler apptScheduler = new StdSchedulerFactory().getScheduler();
			apptScheduler.start();
			apptScheduler.scheduleJob(appointmentJob, apptTrigger);

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	// this task class is to check the diagnosis values from MedicalHistory and
	// Visit table and add new values into PVDiagnosis table
	static class ConcurrTask extends TimerTask {
		String schedulerTimeString = null;
		String currentTimeString = null;

		private static final int MYTHREADS = 30;

		@Override
		public void run() {

			System.out.println("Into CurrentTask");

			System.out.println("Context path from ConcurrentTask ::: " + contextPath);

			ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

			String[] hostList = { // checkTradeName(contextPath),
					checkVisitDiagnosis(contextPath), // checkMedicalHistoryDiagnosis(contextPath),
					checkPersonalHistoryDiagnosis(contextPath) };

			for (int i = 0; i < hostList.length; i++) {

				String url = hostList[i];
				Runnable worker = new MyRunnable(url);
				executor.execute(worker);
			}
			executor.shutdown();
			// Wait until all threads are finish
			while (!executor.isTerminated()) {

			}
			System.out.println("\nFinished all threads");

			/*
			 * Once all process are finished, delete fetched record from Hive
			 */

		}

	}

	/**
	 * 
	 * @return
	 */
	public static String checkTradeName(String contextPath) {

		String message = "";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		/*
		 * Checking for new tradeName value in Prescription table
		 */
		message = doaInf.checkTradeNameFromPrescription(contextPath);

		if (message.equalsIgnoreCase("success")) {

			System.out.println("Successfully inserted newly found tradeName into PVPrescription table.");

			return message;

		} else if (message.equalsIgnoreCase("input")) {

			return message;

		} else if (message.equalsIgnoreCase("error")) {

			System.out.println("No records found..");

			return message;

		} else {

			System.out.println("Exception occured.");

			return message;

		}

	}

	/**
	 * 
	 * @return
	 */
	public static String checkVisitDiagnosis(String contextPath) {

		String message = "";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		/*
		 * Checking for new diagnosis value in Visit table
		 */
		message = doaInf.checkDiagnosisFromVisit(contextPath);

		if (message.equalsIgnoreCase("success")) {

			System.out.println("Successfully inserted newly found diagnosis into PVDiagnoses table.");

			return message;

		} else if (message.equalsIgnoreCase("input")) {

			return message;

		} else if (message.equalsIgnoreCase("error")) {

			System.out.println("No records found..");

			return message;

		} else {

			System.out.println("Exception occured.");

			return message;

		}

	}

	/**
	 * 
	 * @param contextPath
	 * @return
	 */
	public static String checkPersonalHistoryDiagnosis(String contextPath) {

		String message = "";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		/*
		 * Checking for new diagnosis value in MedicalHistory table
		 */
		message = doaInf.checkDiagnosisFromPersonalHistory(contextPath);

		if (message.equalsIgnoreCase("success")) {

			System.out.println("Successfully inserted newly found diagnosis into PVDiagnoses table.");

			return message;

		} else if (message.equalsIgnoreCase("input")) {

			return message;

		} else if (message.equalsIgnoreCase("error")) {

			System.out.println("No records found..");

			return message;

		} else {

			System.out.println("Exception occured.");

			return message;

		}

	}

	public static String sendAppointmentSMSToPatient(String contextPath) {

		String message = "error";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		SMSSender sender = new SMSSender();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Retrieving appointment list containing patientID, apptDate, apptTime,
		 * clinicID, practiceID
		 */
		List<String> appointmentList = doaInf.retrieveAppointmentListForReminderSMS(contextPath);

		if (appointmentList == null) {
			System.out.println(
					"No patient's appointment is scheduled for today. Date - " + dateFormat.format(new Date()));
		} else {

			/*
			 * Iterating over appointmentList in order to get the appointment details
			 * scheduled for today
			 */
			for (String apptDetails : appointmentList) {

				String array[] = apptDetails.split("=");

				int patientID = Integer.parseInt(array[1]);

				int practiceID = Integer.parseInt(array[4]);
						
				String mobileNo = doaInf.retrievePatientMobileNoByID(patientID, contextPath);

				if (mobileNo == null || mobileNo == "") {
					System.out.println("No mobile found for patient");
				} else {
					if (mobileNo.isEmpty()) {
						System.out.println("No mobile found for patient");
					} else {

						try {

							message = sender.sendAppointmentReminderSMS(patientID, mobileNo, Integer.parseInt(array[4]),
									Integer.parseInt(array[3]), dateFormat.format(dateFormat1.parse(array[0])),
									array[2], contextPath, array[5]);

						} catch (Exception exception) {
							exception.printStackTrace();

							message = "exception";
						}

					}
				}

			}
		}

		return message;

	}

	public static String sendAppointmentMailToClinician(String contextPath) {

		String message = "error";

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		EmailUtil emailUtil = new EmailUtil();

		/*
		 * Retrieving clinician list containing practiceID, mobile no, firstName and
		 * lastName
		 */
		List<String> clinicianList = doaInf.retrieveClinicianUserID(contextPath);

		if (clinicianList == null) {
			System.out.println("No clinicians found.");
		} else {

			/*
			 * Iterating over appointmentList in order to get the appointment details
			 * scheduled for today
			 */
			for (String clinicianDetails : clinicianList) {

				String array[] = clinicianDetails.split("=");

				String email = array[0];

				String clinicianName = array[2] + " " + array[3];

				if (email == null || email == "") {
					System.out.println("No email found for clinician");
				} else {
					if (email.isEmpty()) {
						System.out.println("No email found for clinician");
					} else {

						try {

							message = emailUtil.sendAppointmentReminderEmail(Integer.parseInt(array[1]), email,
									clinicianName, contextPath);

						} catch (Exception exception) {
							exception.printStackTrace();

							message = "exception";
						}

					}
				}

			}
		}

		return message;

	}

	/**
	 * 
	 * @author roshany
	 * 
	 */
	public static class MyRunnable implements Runnable {
		private final String url;

		MyRunnable(String url) {
			this.url = url;
		}

		public void run() {

			System.out.println(url + ": Result returned...");
		}
	}

}
