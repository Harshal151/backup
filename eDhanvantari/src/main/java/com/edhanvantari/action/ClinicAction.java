package com.edhanvantari.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoImpl.ClinicDAOImpl;
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoImpl.RegistrationDAOImpl;
import com.edhanvantari.daoInf.ClinicDAOInf;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.form.ClinicForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.EmailUtil;
import com.edhanvantari.util.SMSSender;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ClinicAction extends ActionSupport
		implements ModelDriven<ClinicForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ClinicForm form = new ClinicForm();
	ClinicDAOInf clinicDAOInf = null;
	String message = "error";

	List<ClinicForm> planDetailsList = null;
	List<ClinicForm> clinicList = null;
	List<ClinicForm> searchClinicList = null;
	List<ClinicForm> searchPracticeList = null;
	List<ClinicForm> practiceList = null;
	HashMap<String, String> clinicTypeList = null;
	HashMap<Integer, String> practiceMap = null;
	List<ClinicForm> visitTypeList = null;
	List<ClinicForm> workflowList = null;
	List<ClinicForm> rowList = null;
	
    private String apptID1;
    private String updatedComment1;

	HashMap<String, String> patientJSPList = null;
	HashMap<String, String> reportJSPList = null;
	List<String> OPDJSPList = null;

	List<ClinicForm> mdDetailsList = null;

	eDhanvantariServiceInf serviceInf = null;

	ConfigurationUtil configurationUtil = null;

	ConfigurationUtil util = new ConfigurationUtil();

	HttpServletRequest request;
	HttpServletResponse response;

	private Map<String, Object> session = null;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPractice() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();
		configurationUtil = new ConfigurationUtil();
		serviceInf = new eDhanvantariServiceImpl();

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Setting realPath for logo file to be saved at
		 */
		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/images/");

		patientJSPList = registrationDAOinf.retrievePatientPageList();

		String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

		message = serviceInf.addPractice(form, realPath, url);
		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Successfully inserted practice detail into database.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add Practice", userForm.getUserID());

			return SUCCESS;
		} else {

			addActionError(
					"Error occurred while inserting practice detail into database. Please check server logs for more details.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add Practice exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void verifyPracticeExists() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.verifyPracticeExists(form.getPracticeName());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying practice already exists or not");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editVisitTypeList() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchPractice() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		if (userForm.getUserType().equals("superAdmin")) {
			searchPracticeList = clinicDAOInf.searchForSuperAdminPracticeList(form.getSearchPracticeName());
		} else {
			searchPracticeList = clinicDAOInf.searchPracticeList(form.getSearchPracticeName(),
					userForm.getPracticeID());
		}

		if (searchPracticeList.size() > 0) {

			request.setAttribute("clinicListEnable", "clinicSearchListEnable");

			return SUCCESS;

		} else {

			addActionError("No practice found for name : " + form.getSearchPracticeName() + ".");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllPracticeList() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		if (userForm.getUserType().equals("superAdmin")) {
			practiceList = clinicDAOInf.retrivePracticeListForSuperAdmin();
		} else {
			practiceList = clinicDAOInf.retrivePracticeList(userForm.getPracticeID());
		}

		if (practiceList.size() > 0) {

			request.setAttribute("clinicListEnable", "clinicListEnable");

			return SUCCESS;

		} else {

			addActionError("No Practice found. Please add new Practice.");

			return ERROR;

		}
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditPractice() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();
		configurationUtil = new ConfigurationUtil();

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		patientJSPList = registrationDAOinf.retrievePatientPageList();

		// Retrieving practice details and practice configuration details based on
		// practiceID
		practiceList = clinicDAOInf.retrivePracticeByPracticeID(form.getPracticeID());

		// retrieving plan details list based on practice ID
		planDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

		// retrieving Clinic list based on practice ID
		clinicList = clinicDAOInf.retrieveClinicListByPracticeID(form.getPracticeID());

		mdDetailsList = clinicDAOInf.retrieveMDDetailsListByPracticeID(form.getPracticeID());

		// retrieving values from Communication in order to check the relative
		// checkboxes into Communication table

		// For Appointment Shceduled
		String applScheduled = clinicDAOInf.retrieveCommunicationVal("smsApptSchedule", "emailApptSchedule",
				form.getPracticeID());

		request.setAttribute("applScheduled", applScheduled);

		// For Appointment Updated
		String applUpdated = clinicDAOInf.retrieveCommunicationVal("smsApptUpdate", "emailApptUpdate",
				form.getPracticeID());

		request.setAttribute("applUpdated", applUpdated);

		// For Appointment Cancelled
		String applCancelled = clinicDAOInf.retrieveCommunicationVal("smsApptCancel", "emailApptCancel",
				form.getPracticeID());

		request.setAttribute("applCancelled", applCancelled);

		// For Send bill
		String sendBill = clinicDAOInf.retrieveCommunicationVal("smsBill", "emailBill", form.getPracticeID());

		request.setAttribute("sendBill", sendBill);

		// For send prescription
		String sendPresc = clinicDAOInf.retrieveCommunicationVal("smsPresc", "emailPresc", form.getPracticeID());

		request.setAttribute("sendPresc", sendPresc);

		// For send thanx to ref doc
		String sendThankRefDoc = clinicDAOInf.retrieveCommunicationVal("smsRefThanks", "emailRefThanks",
				form.getPracticeID());

		System.out.println("Ref doct ... " + sendThankRefDoc);

		request.setAttribute("sendThankRefDoc", sendThankRefDoc);

		// For welcome msg
		String welcomeMSg = clinicDAOInf.retrieveCommunicationVal("smsWelcome", "emailWelcome", form.getPracticeID());

		request.setAttribute("welcomeMSg", welcomeMSg);

		// For patient Login credentials
		String patLoginCredMSg = clinicDAOInf.retrieveCommunicationVal("smsPatPortalCredentials",
				"emailPatPortalCredentials", form.getPracticeID());

		request.setAttribute("patLoginCredMSg", patLoginCredMSg);

		// For Review form
		String reviewForm = clinicDAOInf.retrieveCommunicationVal("smsReviewForm", "emailReviewForm",
				form.getPracticeID());

		request.setAttribute("reviewForm", reviewForm);

		// For review form URL
		request.setAttribute("reviewFormURL", clinicDAOInf.retrieveReviewFormURL(form.getPracticeID()));

		// For appointment reminder
		String apptReminder = clinicDAOInf.retrieveCommunicationVal("smsDailyAppt", "emailDailyAppt",
				form.getPracticeID());

		request.setAttribute("apptReminder", apptReminder);

		// For inventory alert
		String inventoryAlert = clinicDAOInf.retrieveCommunicationVal("smsInventory", "emailInventory",
				form.getPracticeID());

		request.setAttribute("inventoryAlert", inventoryAlert);

		return SUCCESS;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteClinicRow() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			// Deleting calendar details for clinicID and then deleting clinic
			// details for the same clinicID
			clinicDAOInf.deleteCalendarDetails(form.getClinicID());

			values = clinicDAOInf.deleteClinicRow(form.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while deleting clinic row based on clinicID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	public void disablePlanRow() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			// Disable plan details for planID

			values = clinicDAOInf.disablePlanDetails(form.getPracticeID());

			rowList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			for (ClinicForm rowlist : rowList) {
				object = new JSONObject();

				object.put("id", rowlist.getPlanID());
				object.put("noOfVisit", rowlist.getPlanNoOfVisits());
				object.put("startDate", rowlist.getPlanDateStart());
				object.put("endDate", rowlist.getPlanDateEnd());
				object.put("status", rowlist.getPlanStatus1());

				System.out.println("visit:" + rowlist.getPlanNoOfVisits() + "  start date:" + rowlist.getPlanDateStart()
						+ " end date:" + rowlist.getPlanDateEnd() + " status:" + rowlist.getPlanStatus1());

				array.add(object);
				values.put("Release1", array);
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while disable plan details row based on planID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		}

	}

	public String editClinicUploadImage() throws Exception {
		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
		serviceInf = new eDhanvantariServiceImpl();
		clinicDAOInf = new ClinicDAOImpl();

		form.setPracticeID(clinicDAOInf.retrievePracticeIDByClinicID(form.getClinicID()));

		System.out.println("inside clinic upload action" + form.getPracticeID());

		patientJSPList = registrationDAOinf.retrievePatientPageList();

		// Retrieving practice details and practice configuration details based
		// on practiceID
		practiceList = clinicDAOInf.retrivePracticeByPracticeID(form.getPracticeID());

		// retrieving Clinic list based on practice ID
		clinicList = clinicDAOInf.retrieveClinicListByPracticeID(form.getPracticeID());

		// retrieving values from Communication in order to check the relative
		// checkboxes into Communication table

		// For Appointment Shceduled
		String applScheduled = clinicDAOInf.retrieveCommunicationVal("smsApptSchedule", "emailApptSchedule",
				form.getPracticeID());

		request.setAttribute("applScheduled", applScheduled);

		// For Appointment Updated
		String applUpdated = clinicDAOInf.retrieveCommunicationVal("smsApptUpdate", "emailApptUpdate",
				form.getPracticeID());

		request.setAttribute("applUpdated", applUpdated);

		// For Appointment Cancelled
		String applCancelled = clinicDAOInf.retrieveCommunicationVal("smsApptCancel", "emailApptCancel",
				form.getPracticeID());

		request.setAttribute("applCancelled", applCancelled);

		// For Send bill
		String sendBill = clinicDAOInf.retrieveCommunicationVal("smsBill", "emailBill", form.getPracticeID());

		request.setAttribute("sendBill", sendBill);

		// For send prescription
		String sendPresc = clinicDAOInf.retrieveCommunicationVal("smsPresc", "emailPresc", form.getPracticeID());

		request.setAttribute("sendPresc", sendPresc);

		// For send thanx to ref doc
		String sendThankRefDoc = clinicDAOInf.retrieveCommunicationVal("smsRefThanks", "emailRefThanks",
				form.getPracticeID());

		System.out.println("Ref doct ... " + sendThankRefDoc);

		request.setAttribute("sendThankRefDoc", sendThankRefDoc);

		// For welcome msg
		String welcomeMSg = clinicDAOInf.retrieveCommunicationVal("smsWelcome", "emailWelcome", form.getPracticeID());

		request.setAttribute("welcomeMSg", welcomeMSg);

		// For patient Login credentials
		String patLoginCredMSg = clinicDAOInf.retrieveCommunicationVal("smsPatPortalCredentials",
				"emailPatPortalCredentials", form.getPracticeID());

		request.setAttribute("patLoginCredMSg", patLoginCredMSg);

		// For Review form
		String reviewForm = clinicDAOInf.retrieveCommunicationVal("smsReviewForm", "emailReviewForm",
				form.getPracticeID());

		request.setAttribute("reviewForm", reviewForm);

		// For review form URL
		request.setAttribute("reviewFormURL", clinicDAOInf.retrieveReviewFormURL(form.getPracticeID()));

		// For appointment reminder
		String apptReminder = clinicDAOInf.retrieveCommunicationVal("smsDailyAppt", "emailDailyAppt",
				form.getPracticeID());

		request.setAttribute("apptReminder", apptReminder);

		// For inventory alert
		String inventoryAlert = clinicDAOInf.retrieveCommunicationVal("smsInventory", "emailInventory",
				form.getPracticeID());

		request.setAttribute("inventoryAlert", inventoryAlert);
		System.out.println("before clinic upload serviceimpl");
		message = serviceInf.editClinicUploadImage(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Successfully udpated Uploaded Image into database.");

			return SUCCESS;
		} else {
			addActionError(
					"Error occurred while updating practice detail into database. Please check server logs for more details.");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editPractice() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();
		configurationUtil = new ConfigurationUtil();
		serviceInf = new eDhanvantariServiceImpl();

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Setting realPath for logo file to be saved at
		 */
		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/images/");

		patientJSPList = registrationDAOinf.retrievePatientPageList();

		String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

		message = serviceInf.editPractice(form, realPath, url);
		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Successfully udpated practice detail into database.");

			// For Appointment Shceduled
			String applScheduled = clinicDAOInf.retrieveCommunicationVal("smsApptSchedule", "emailApptSchedule",
					form.getPracticeID());

			// Retrieving practice details and practice configuration details
			// based
			// on practiceID
			practiceList = clinicDAOInf.retrivePracticeByPracticeID(form.getPracticeID());

			// retrieving plan details list based on practice ID
			planDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			// retrieving Clinic list based on practice ID
			clinicList = clinicDAOInf.retrieveClinicListByPracticeID(form.getPracticeID());

			mdDetailsList = clinicDAOInf.retrieveMDDetailsListByPracticeID(form.getPracticeID());

			request.setAttribute("applScheduled", applScheduled);

			// For Appointment Updated
			String applUpdated = clinicDAOInf.retrieveCommunicationVal("smsApptUpdate", "emailApptUpdate",
					form.getPracticeID());

			request.setAttribute("applUpdated", applUpdated);

			// For Appointment Cancelled
			String applCancelled = clinicDAOInf.retrieveCommunicationVal("smsApptCancel", "emailApptCancel",
					form.getPracticeID());

			request.setAttribute("applCancelled", applCancelled);

			// For Send bill
			String sendBill = clinicDAOInf.retrieveCommunicationVal("smsBill", "emailBill", form.getPracticeID());

			request.setAttribute("sendBill", sendBill);

			// For send prescription
			String sendPresc = clinicDAOInf.retrieveCommunicationVal("smsPresc", "emailPresc", form.getPracticeID());

			request.setAttribute("sendPresc", sendPresc);

			// For send thanx to ref doc
			String sendThankRefDoc = clinicDAOInf.retrieveCommunicationVal("smsRefThanks", "emailRefThanks",
					form.getPracticeID());

			request.setAttribute("sendThankRefDoc", sendThankRefDoc);

			// For welcome msg
			String welcomeMSg = clinicDAOInf.retrieveCommunicationVal("smsWelcome", "emailWelcome",
					form.getPracticeID());

			request.setAttribute("welcomeMSg", welcomeMSg);

			// For patient Login credentials
			String patLoginCredMSg = clinicDAOInf.retrieveCommunicationVal("smsPatPortalCredentials",
					"emailPatPortalCredentials", form.getPracticeID());

			request.setAttribute("patLoginCredMSg", patLoginCredMSg);

			// For Review form
			String reviewForm = clinicDAOInf.retrieveCommunicationVal("smsReviewForm", "emailReviewForm",
					form.getPracticeID());

			request.setAttribute("reviewForm", reviewForm);

			// For review form URL
			request.setAttribute("reviewFormURL", clinicDAOInf.retrieveReviewFormURL(form.getPracticeID()));

			// For appointment reminder
			String apptReminder = clinicDAOInf.retrieveCommunicationVal("smsDailyAppt", "emailDailyAppt",
					form.getPracticeID());

			request.setAttribute("apptReminder", apptReminder);

			// For inventory alert
			String inventoryAlert = clinicDAOInf.retrieveCommunicationVal("smsInventory", "emailInventory",
					form.getPracticeID());

			request.setAttribute("inventoryAlert", inventoryAlert);

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Practice", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionMessage("Successfully udpated practice detail into database. Please add remaining details");

			// retrieving values from Communication in order to check the
			// relative
			// checkboxes into Communication table

			// Retrieving practice details and practice configuration details
			// based
			// on practiceID
			practiceList = clinicDAOInf.retrivePracticeByPracticeID(form.getPracticeID());

			// retrieving plan details list based on practice ID
			planDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			// retrieving Clinic list based on practice ID
			clinicList = clinicDAOInf.retrieveClinicListByPracticeID(form.getPracticeID());

			mdDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			// For Appointment Shceduled
			String applScheduled = clinicDAOInf.retrieveCommunicationVal("smsApptSchedule", "emailApptSchedule",
					form.getPracticeID());

			request.setAttribute("applScheduled", applScheduled);

			// For Appointment Updated
			String applUpdated = clinicDAOInf.retrieveCommunicationVal("smsApptUpdate", "emailApptUpdate",
					form.getPracticeID());

			request.setAttribute("applUpdated", applUpdated);

			// For Appointment Cancelled
			String applCancelled = clinicDAOInf.retrieveCommunicationVal("smsApptCancel", "emailApptCancel",
					form.getPracticeID());

			request.setAttribute("applCancelled", applCancelled);

			// For Send bill
			String sendBill = clinicDAOInf.retrieveCommunicationVal("smsBill", "emailBill", form.getPracticeID());

			request.setAttribute("sendBill", sendBill);

			// For send prescription
			String sendPresc = clinicDAOInf.retrieveCommunicationVal("smsPresc", "emailPresc", form.getPracticeID());

			request.setAttribute("sendPresc", sendPresc);

			// For send thanx to ref doc
			String sendThankRefDoc = clinicDAOInf.retrieveCommunicationVal("smsRefThanks", "emailRefThanks",
					form.getPracticeID());

			request.setAttribute("sendThankRefDoc", sendThankRefDoc);

			// For welcome msg
			String welcomeMSg = clinicDAOInf.retrieveCommunicationVal("smsWelcome", "emailWelcome",
					form.getPracticeID());

			request.setAttribute("welcomeMSg", welcomeMSg);

			// For patient Login credentials
			String patLoginCredMSg = clinicDAOInf.retrieveCommunicationVal("smsPatPortalCredentials",
					"emailPatPortalCredentials", form.getPracticeID());

			request.setAttribute("patLoginCredMSg", patLoginCredMSg);

			// For Review form
			String reviewForm = clinicDAOInf.retrieveCommunicationVal("smsReviewForm", "emailReviewForm",
					form.getPracticeID());

			request.setAttribute("reviewForm", reviewForm);

			// For review form URL
			request.setAttribute("reviewFormURL", clinicDAOInf.retrieveReviewFormURL(form.getPracticeID()));

			// For appointment reminder
			String apptReminder = clinicDAOInf.retrieveCommunicationVal("smsDailyAppt", "emailDailyAppt",
					form.getPracticeID());

			request.setAttribute("apptReminder", apptReminder);

			// For inventory alert
			String inventoryAlert = clinicDAOInf.retrieveCommunicationVal("smsInventory", "emailInventory",
					form.getPracticeID());

			request.setAttribute("inventoryAlert", inventoryAlert);

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Practice Exception Occurred", userForm.getUserID());

			return SUCCESS;
		} else {

			addActionError(
					"Error occurred while updating practice detail into database. Please check server logs for more details.");

			// retrieving values from Communication in order to check the
			// relative
			// checkboxes into Communication table

			// Retrieving practice details and practice configuration details
			// based
			// on practiceID
			practiceList = clinicDAOInf.retrivePracticeByPracticeID(form.getPracticeID());

			// retrieving plan details list based on practice ID
			planDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			// retrieving Clinic list based on practice ID
			clinicList = clinicDAOInf.retrieveClinicListByPracticeID(form.getPracticeID());

			mdDetailsList = clinicDAOInf.retrievePlanDetailsListByPracticeID(form.getPracticeID());

			// For Appointment Shceduled
			String applScheduled = clinicDAOInf.retrieveCommunicationVal("smsApptSchedule", "emailApptSchedule",
					form.getPracticeID());

			request.setAttribute("applScheduled", applScheduled);

			// For Appointment Updated
			String applUpdated = clinicDAOInf.retrieveCommunicationVal("smsApptUpdate", "emailApptUpdate",
					form.getPracticeID());

			request.setAttribute("applUpdated", applUpdated);

			// For Appointment Cancelled
			String applCancelled = clinicDAOInf.retrieveCommunicationVal("smsApptCancel", "emailApptCancel",
					form.getPracticeID());

			request.setAttribute("applCancelled", applCancelled);

			// For Send bill
			String sendBill = clinicDAOInf.retrieveCommunicationVal("smsBill", "emailBill", form.getPracticeID());

			request.setAttribute("sendBill", sendBill);

			// For send prescription
			String sendPresc = clinicDAOInf.retrieveCommunicationVal("smsPresc", "emailPresc", form.getPracticeID());

			request.setAttribute("sendPresc", sendPresc);

			// For send thanx to ref doc
			String sendThankRefDoc = clinicDAOInf.retrieveCommunicationVal("smsRefThanks", "emailRefThanks",
					form.getPracticeID());

			request.setAttribute("sendThankRefDoc", sendThankRefDoc);

			// For welcome msg
			String welcomeMSg = clinicDAOInf.retrieveCommunicationVal("smsWelcome", "emailWelcome",
					form.getPracticeID());

			request.setAttribute("welcomeMSg", welcomeMSg);

			// For patient Login credentials
			String patLoginCredMSg = clinicDAOInf.retrieveCommunicationVal("smsPatPortalCredentials",
					"emailPatPortalCredentials", form.getPracticeID());

			request.setAttribute("patLoginCredMSg", patLoginCredMSg);

			// For Review form
			String reviewForm = clinicDAOInf.retrieveCommunicationVal("smsReviewForm", "emailReviewForm",
					form.getPracticeID());

			request.setAttribute("reviewForm", reviewForm);

			// For review form URL
			request.setAttribute("reviewFormURL", clinicDAOInf.retrieveReviewFormURL(form.getPracticeID()));

			// For appointment reminder
			String apptReminder = clinicDAOInf.retrieveCommunicationVal("smsDailyAppt", "emailDailyAppt",
					form.getPracticeID());

			request.setAttribute("apptReminder", apptReminder);

			// For inventory alert
			String inventoryAlert = clinicDAOInf.retrieveCommunicationVal("smsInventory", "emailInventory",
					form.getPracticeID());

			request.setAttribute("inventoryAlert", inventoryAlert);

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Practice Exception Occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderAddPractice() throws Exception {
		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		patientJSPList = registrationDAOinf.retrievePatientPageList();

		reportJSPList = registrationDAOinf.retrieveReportPageList();

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderAddVisitType() throws Exception {

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

		// Setting logged in user's practiceID into visitTypelist in order to
		// display it as default in Practice field
		form.setPracticeID(userForm.getPracticeID());

		// Setting Consultation as default value for billing type
		form.setBillingType("Consultation");

		// Setting yes as default value for New Visit flag
		form.setNewVisitFlag(1);

		visitTypeList = new ArrayList<ClinicForm>();

		visitTypeList.add(form);

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addVisitType() throws Exception {

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.configureVisitType(form, realPath);

		if (message.equals("success")) {

			addActionMessage("Visit type added successfully.");

			visitTypeList = new ArrayList<ClinicForm>();

			visitTypeList.add(form);

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add Visit Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Visit Type with same name already exists. Please add new Visit Type.");

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			// Setting logged in user's practiceID into visitTypelist in order to
			// display it as default in Practice field
			form.setPracticeID(userForm.getPracticeID());

			// Setting Consultation as default value for billing type
			form.setBillingType("Consultation");

			// Setting yes as default value for New Visit flag
			form.setNewVisitFlag(1);

			visitTypeList = new ArrayList<ClinicForm>();

			visitTypeList.add(form);

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add Visit Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add visit type. Please check server logs for more details.");

			visitTypeList = new ArrayList<ClinicForm>();

			visitTypeList.add(form);

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add Visit Type Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchVisitType() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");
		System.out.println("practiceID: " + userForm.getPracticeID());
		visitTypeList = clinicDAOInf.searchVisitType(form.getSearchVisitTypeName(), userForm.getPracticeID());

		if (userForm.getPracticeID() == 0) {

			addActionError("No clinic is selected. Please select clinic.");

			return ERROR;
		} else if (visitTypeList.size() > 0) {

			request.setAttribute("clinicListEnable", "clinicSearchListEnable");

			return SUCCESS;

		} else {

			addActionError("No visit type found for name : " + form.getSearchVisitTypeName() + ".");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Excpetion
	 */
	public String viewAllVisitTypeList() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		visitTypeList = clinicDAOInf.retrieveAllVisitTypeList(userForm.getPracticeID());

		if (userForm.getPracticeID() == 0) {

			addActionError("No clinic is selected. Please select clinic.");

			return ERROR;
		} else if (visitTypeList.size() > 0) {

			request.setAttribute("clinicListEnable", "clinicListEnable");

			return SUCCESS;

		} else {

			addActionError("No visit type found. Please add new visit type.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditVisitType() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		// Retrieving visit type list based on visit type id
		visitTypeList = clinicDAOInf.retrieveVisitTypeListByVisitTypeID(form.getVisitTypeID());

		practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

		OPDJSPList = registrationDAOinf.retrieveOPDPageList();

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editVisitType() throws Exception {

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.configureEditVisitType(form);
		// message = clinicDAOInf.updateVisitType(form);

		if (message.equals("success")) {

			addActionMessage("Visit type updated successfully.");

			// Retrieving visit type list based on visit type id
			visitTypeList = clinicDAOInf.retrieveVisitTypeListByVisitTypeID(form.getVisitTypeID());

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			OPDJSPList = registrationDAOinf.retrieveOPDPageList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Visit Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Visit Type with same name already exists. Please add new Visit Type.");

			// Retrieving visit type list based on visit type id
			visitTypeList = clinicDAOInf.retrieveVisitTypeListByVisitTypeID(form.getVisitTypeID());

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			OPDJSPList = registrationDAOinf.retrieveOPDPageList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Visit Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update visit type. Please check server logs for more details.");

			// Retrieving visit type list based on visit type id
			visitTypeList = clinicDAOInf.retrieveVisitTypeListByVisitTypeID(form.getVisitTypeID());

			practiceMap = registrationDAOinf.getClinicList(userForm.getPracticeID());

			OPDJSPList = registrationDAOinf.retrieveOPDPageList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Visit Type Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disableVisitType() throws Exception {

		RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

		clinicDAOInf = new ClinicDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.deleteVisitType(form.getVisitTypeID());

		if (message.equals("success")) {

			addActionMessage("Visit type deleted successfully.");

			/*
			 * checking whether searchVisitTypeName string is null or not, if null
			 * displaying all list after deletion else displaying search list after deletion
			 */
			if (form.getSearchVisitTypeName() == null || form.getSearchVisitTypeName() == "") {

				visitTypeList = clinicDAOInf.retrieveAllVisitTypeList(userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicListEnable");

			} else {

				visitTypeList = clinicDAOInf.searchVisitType(form.getSearchVisitTypeName(), userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicSearchListEnable");

			}

			return SUCCESS;

		} else if (message.equals("input")) {

			String visitTypeName = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());

			addActionError("Can not delete visit type as visit already exists for visit type; '" + visitTypeName + "'");

			/*
			 * checking whether searchVisitTypeName string is null or not, if null
			 * displaying all list after deletion else displaying search list after deletion
			 */
			if (form.getSearchVisitTypeName() == null || form.getSearchVisitTypeName() == "") {

				visitTypeList = clinicDAOInf.retrieveAllVisitTypeList(userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicListEnable");

			} else {

				visitTypeList = clinicDAOInf.searchVisitType(form.getSearchVisitTypeName(), userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicSearchListEnable");

			}

			return INPUT;

		} else {

			addActionError("Failed to delete visit type. Please check server logs for more details.");

			/*
			 * checking whether searchVisitTypeName string is null or not, if null
			 * displaying all list after deletion else displaying search list after deletion
			 */
			if (form.getSearchVisitTypeName() == null || form.getSearchVisitTypeName() == "") {

				visitTypeList = clinicDAOInf.retrieveAllVisitTypeList(userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicListEnable");

			} else {

				visitTypeList = clinicDAOInf.searchVisitType(form.getSearchVisitTypeName(), userForm.getPracticeID());

				request.setAttribute("clinicListEnable", "clinicSearchListEnable");

			}

			return ERROR;

		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addAppointment() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		try {

			// Setting practiceID into clinicForm
			form.setPracticeID(userForm.getPracticeID());

			form.setClinicSuffixName(userForm.getClinicSuffix());

			form.setClinicID(userForm.getClinicID());

			form.setPracticeSuffix(userForm.getPracticeSuffix());

			values = serviceInf.addAppointment(form, userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while adding appointment for patient.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrievePatientDetails() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.retrievePatientDetailsBasedOnPatientID(form.getPatientID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving patient details based on patientID.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveTabName() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.retrieveTabNameByFormName(form.getWorkflowOPDForm());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving tabs based on form name");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void updateConfirmAppointmentStatus() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.updateConfirmedAppointmentStatusByPatientID(form.getPatientID(),
					form.getApptStartTime(), form.getApptEndTime());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Error occured while updating appointment status to Confirmed for particular patient by patientID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}
	
	public void updateCommentByID() throws Exception {
	    
	    clinicDAOInf = new ClinicDAOImpl();
	    
	    message = clinicDAOInf.updateCommentByApptID(getApptID1(), getUpdatedComment1());
	    
	    if ("success".equals(message)) {  // Use equals() for string comparison
	        String updatedComment = clinicDAOInf.retrieveUpdatedCommentByApptID(getApptID1());
	        
	        // Corrected printf statement with the format specifier %s
	        System.out.printf("Retrieved comment: %s%n", updatedComment);
	        // Send the updated comment back as the response
	        response.setContentType("text/plain");
	        response.getWriter().write(updatedComment);
	    }
	}

	

	/**
	 * 
	 * @throws Exception
	 */
	public void updateCancelledAppointmentStatus() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		PatientDAOInf patientDAOInf = new PatientDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Getting clinicID from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			values = clinicDAOInf.updateCancelledAppointmentStatusByPatientID(form.getPatientID(),
					form.getApptStartTime(), form.getApptEndTime());

			/*
			 * Check whether appointment udpated flag is on or not, and depending upon that
			 * sending SMS and Email
			 */
			boolean SMSCheck = util.verifyCommunicationCheck("smsApptCancel");

			if (SMSCheck) {

				/*
				 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
				 * welcome message
				 */
				String mobileNo = patientDAOInf.retrievePatientMobileNoByID(form.getPatientID());

				/*
				 * Sending patient a welcome as well as Appointment scheduled message on
				 * checking whether mobile no is available for that patient or not
				 */
				if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
					System.out.println("Mobile no not found for patient.");
				} else {

					String[] startArray = form.getApptStartTime().split(" ");
					String apptDate = startArray[0];

					String apptTime = startArray[1];

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
					String doctorName = clinicDAOInf.retrieveApptDocName(form.getPatientID(), apptDate,
							form.getApptStartTime(), form.getApptEndTime(), loginForm.getClinicID());

					/*
					 * Sending appointment cancelled SMS to patient
					 */
					smsSender.sendAppointmentSMS(form.getPatientID(), mobileNo, loginForm.getPracticeID(),
							loginForm.getClinicID(), apptDate, apptTime, ActivityStatus.CANCELLED, doctorName);
				}

			}

			boolean EmailCheck = util.verifyCommunicationCheck("emailApptCancel");

			if (EmailCheck) {

				/*
				 * Retrieving patient's email ID, if not null, then sending patient a welcome
				 * mail
				 */
				String emailID = patientDAOInf.retrievePatientEmailByID(form.getPatientID());

				if (emailID == null || emailID == "" || emailID.isEmpty()) {
					System.out.println("EmailID no not found for patient.");
				} else {

					String[] startArray = form.getApptStartTime().split(" ");
					String apptDate = startArray[0];

					String apptTime = startArray[1];

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
					emailUtil.sendAppointmentEmail(loginForm.getPracticeID(), loginForm.getClinicID(), emailID,
							form.getPatientID(), apptDate, apptTime, ActivityStatus.CANCELLED);

				}

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Error occured while updating appointment status to cancelled for particular patient by patientID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void updateAppointment() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		PatientDAOInf patientDAOInf = new PatientDAOImpl();

		SMSSender smsSender = new SMSSender();

		EmailUtil emailUtil = new EmailUtil();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting clinicID from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			form.setClinicID(loginForm.getClinicID());

			values = clinicDAOInf.updateAppointment(form);

			/*
			 * Check whether appointment udpated flag is on or not, and depending upon that
			 * sending SMS and Email
			 */
			boolean SMSCheck = util.verifyCommunicationCheck("smsApptUpdate");

			if (SMSCheck) {

				/*
				 * Retrieving patient's mobile no, if mobile is not null, then sending patient a
				 * welcome message
				 */
				String mobileNo = patientDAOInf.retrievePatientMobileNoByID(form.getPatientID());

				/*
				 * Sending patient a welcome as well as Appointment scheduled message on
				 * checking whether mobile no is available for that patient or not
				 */
				if (mobileNo == null || mobileNo == "" || mobileNo.isEmpty()) {
					System.out.println("Mobile no not found for patient.");
				} else {

					String apptDate = form.getStartDate();

					String startTime = form.getClinicStartHH();

					if (apptDate == null || apptDate == "" || apptDate.isEmpty()) {
						apptDate = "";
					} else if (apptDate.substring(2, 3).equals("-")) {
						apptDate = apptDate;
					} else {
						apptDate = dateFormat.format(dateFormat1.parse(apptDate));
					}

					// Retrieving doctor name from the appointmnet
					String doctorName = clinicDAOInf.retrieveApptDocName(form.getPatientID(), apptDate,
							form.getApptStartTime(), form.getApptEndTime(), loginForm.getClinicID());

					/*
					 * Sending patient an appointment update SMS
					 */
					smsSender.sendAppointmentSMS(form.getPatientID(), mobileNo, loginForm.getPracticeID(),
							loginForm.getClinicID(), apptDate, startTime, ActivityStatus.UPDATED, doctorName);

				}

			}

			boolean EmailCheck = util.verifyCommunicationCheck("emailApptUpdate");

			if (EmailCheck) {

				/*
				 * Retrieving patient's email ID, if not null, then sending patient a welcome
				 * mail
				 */
				String emailID = patientDAOInf.retrievePatientEmailByID(form.getPatientID());

				if (emailID == null || emailID == "" || emailID.isEmpty()) {
					System.out.println("EmailID no not found for patient.");
				} else {

					String apptDate = form.getStartDate();

					String startTime = form.getClinicStartHH();

					if (apptDate == null || apptDate == "" || apptDate.isEmpty()) {
						apptDate = "";
					} else if (apptDate.substring(2, 3).equals("-")) {
						apptDate = apptDate;
					} else {
						apptDate = dateFormat.format(dateFormat1.parse(apptDate));
					}

					/*
					 * Sending appointment udpate Email to patient
					 */
					emailUtil.sendAppointmentEmail(loginForm.getPracticeID(), loginForm.getClinicID(), emailID,
							form.getPatientID(), apptDate, startTime, ActivityStatus.UPDATED);

				}

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while updating appointment.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveClinicCalendar() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.retrieveClinicCalendarList(form.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic calendar details based on clinicID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	public void retrieveUploadImage() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.retrieveClinicImageList(form.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic calendar details based on clinicID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void editClinicCalendar() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			// Updating Clinic calendar details based on clinicID
			message = clinicDAOInf.updateClinicCalendar(form);

			if (message.equals("success")) {

				System.out.println("Calendar details udpated successfully.");
				object.put("msg", "Calendar details udpated successfully.");
				object.put("check", "1");

				array.add(object);
				values.put("Release", array);

			} else {
				System.out.println("Failed to update calendar details.");
				object.put("msg", "Failed to update calendar details. Please check server logs for more details.");
				object.put("check", "0");

				array.add(object);

				values.put("Release", array);

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while updating calendar details based on clinicID.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void updateDoneAppointmentStatus() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.updateAppointmentStatusDone(form.getPatientID(), form.getApptID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Error occured while updating appointment status to Done for particular patient by patientID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void updateDoneAppointmentStatusAndRetrievePatientDetails() throws Exception {

		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		PatientDAOInf patientDAOInf = new PatientDAOImpl();

		/*
		 * Getting clinicID from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			clinicDAOInf.updateAppointmentStatusDone(form.getPatientID(), form.getApptID());

			// update next appointment taken flag to yes for current patient and
			// appointmentID
			clinicDAOInf.updateNextAppointmentTaken(form.getApptID(), form.getPatientID());

			object.put("patientID", form.getPatientID());

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Error occured while updating appointment status to Done for particular patient by patientID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 */
	public void retrieveClinicListForPractice() throws Exception {
		clinicDAOInf = new ClinicDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = clinicDAOInf.retrieveClinicListForPractice(form.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Clinic List based on PracticeID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	/**
	 * @return the reportJSPList
	 */
	public HashMap<String, String> getReportJSPList() {
		return reportJSPList;
	}

	/**
	 * @param reportJSPList the reportJSPList to set
	 */
	public void setReportJSPList(HashMap<String, String> reportJSPList) {
		this.reportJSPList = reportJSPList;
	}

	/**
	 * @return the workflowList
	 */
	public List<ClinicForm> getWorkflowList() {
		return workflowList;
	}

	/**
	 * @param workflowList the workflowList to set
	 */
	public void setWorkflowList(List<ClinicForm> workflowList) {
		this.workflowList = workflowList;
	}

	/**
	 * @return the visitTypeList
	 */
	public List<ClinicForm> getVisitTypeList() {
		return visitTypeList;
	}

	/**
	 * @param visitTypeList the visitTypeList to set
	 */
	public void setVisitTypeList(List<ClinicForm> visitTypeList) {
		this.visitTypeList = visitTypeList;
	}

	/**
	 * @return the practiceMap
	 */
	public HashMap<Integer, String> getPracticeMap() {
		return practiceMap;
	}

	/**
	 * @param practiceMap the practiceMap to set
	 */
	public void setPracticeMap(HashMap<Integer, String> practiceMap) {
		this.practiceMap = practiceMap;
	}

	/**
	 * @return the patientJSPList
	 */
	public HashMap<String, String> getPatientJSPList() {
		return patientJSPList;
	}

	/**
	 * @param patientJSPList the patientJSPList to set
	 */
	public void setPatientJSPList(HashMap<String, String> patientJSPList) {
		this.patientJSPList = patientJSPList;
	}

	/**
	 * @return the oPDJSPList
	 */
	public List<String> getOPDJSPList() {
		return OPDJSPList;
	}

	/**
	 * @param oPDJSPList the oPDJSPList to set
	 */
	public void setOPDJSPList(List<String> oPDJSPList) {
		OPDJSPList = oPDJSPList;
	}

	/**
	 * @return the searchPracticeList
	 */
	public List<ClinicForm> getSearchPracticeList() {
		return searchPracticeList;
	}

	/**
	 * @param searchPracticeList the searchPracticeList to set
	 */
	public void setSearchPracticeList(List<ClinicForm> searchPracticeList) {
		this.searchPracticeList = searchPracticeList;
	}

	/**
	 * @return the practiceList
	 */
	public List<ClinicForm> getPracticeList() {
		return practiceList;
	}

	/**
	 * @param practiceList the practiceList to set
	 */
	public void setPracticeList(List<ClinicForm> practiceList) {
		this.practiceList = practiceList;
	}

	/**
	 * @return the searchClinicList
	 */
	public List<ClinicForm> getSearchClinicList() {
		return searchClinicList;
	}

	/**
	 * @param searchClinicList the searchClinicList to set
	 */
	public void setSearchClinicList(List<ClinicForm> searchClinicList) {
		this.searchClinicList = searchClinicList;
	}

	/**
	 * @return the clinicTypeList
	 */
	public HashMap<String, String> getClinicTypeList() {
		return clinicTypeList;
	}

	/**
	 * @param clinicTypeList the clinicTypeList to set
	 */
	public void setClinicTypeList(HashMap<String, String> clinicTypeList) {
		this.clinicTypeList = clinicTypeList;
	}

	/**
	 * @return the clinicList
	 */
	public List<ClinicForm> getClinicList() {
		return clinicList;
	}

	/**
	 * @param clinicList the clinicList to set
	 */
	public void setClinicList(List<ClinicForm> clinicList) {
		this.clinicList = clinicList;
	}

	/**
	 * @return the form
	 */
	public ClinicForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(ClinicForm form) {
		this.form = form;
	}

	public ClinicForm getModel() {
		// TODO Auto-generated method stub
		return form;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	/**
	 * @return the planDetailsList
	 */
	public List<ClinicForm> getPlanDetailsList() {
		return planDetailsList;
	}

	/**
	 * @param planDetailsList the planDetailsList to set
	 */
	public void setPlanDetailsList(List<ClinicForm> planDetailsList) {
		this.planDetailsList = planDetailsList;
	}

	/**
	 * @return the rowList
	 */
	public List<ClinicForm> getRowList() {
		return rowList;
	}

	/**
	 * @param rowList the rowList to set
	 */
	public void setRowList(List<ClinicForm> rowList) {
		this.rowList = rowList;
	}

	public List<ClinicForm> getMdDetailsList() {
		return mdDetailsList;
	}

	public void setMdDetailsList(List<ClinicForm> mdDetailsList) {
		this.mdDetailsList = mdDetailsList;
	}
	
    // Getters and Setters
    public String getApptID1() {
        return apptID1;
    }

    public void setApptID1(String apptID1) {
        this.apptID1 = apptID1;
    }

    public String getUpdatedComment1() {
        return updatedComment1;
    }

    public void setUpdatedComment1(String updatedComment1) {
        this.updatedComment1 = updatedComment1;
    }

}
