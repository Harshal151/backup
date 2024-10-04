package com.edhanvantari.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.edhanvantari.daoImpl.ClinicDAOImpl;
import com.edhanvantari.daoInf.ClinicDAOInf;
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.form.ClinicForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.edhanvantari.util.AWSS3Connect;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.ConvertToPDFUtil;
import com.edhanvantari.util.SMSSender;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<LoginForm>, SessionAware, ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519200889250806020L;

	String message = null;
	LoginForm form = new LoginForm();
	private Map<String, Object> sessionAttriute = null;

	List<LoginForm> UsageConsentList = null;
	List<PatientForm> patientVisitList = null;
	List<PatientForm> visitList = null;

	PatientDAOInf patientDAOInf = new PatientDAOImpl();
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	List<PatientForm> appointmentList = null;
	List<PatientForm> appointmentWeekList = null;
	List<PatientForm> appointmentMonthList = null;

	// List<LoginForm> circularList = null;

	PatientForm patientForm = new PatientForm();

	HttpServletRequest request = ServletActionContext.getRequest();

	HttpServletResponse response;

	LoginDAOInf daoInf = null;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	eDhanvantariServiceInf serviceInf = null;

	/**
	 * @return the visitList
	 */
	public List<PatientForm> getVisitList() {
		return visitList;
	}

	/**
	 * @param visitList the visitList to set
	 */
	public void setVisitList(List<PatientForm> visitList) {
		this.visitList = visitList;
	}

	/**
	 * @return the patientVisitList
	 */
	public List<PatientForm> getPatientVisitList() {
		return patientVisitList;
	}

	/**
	 * @param patientVisitList the patientVisitList to set
	 */
	public void setPatientVisitList(List<PatientForm> patientVisitList) {
		this.patientVisitList = patientVisitList;
	}

	/**
	 * @return the form
	 */
	public LoginForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(LoginForm form) {
		this.form = form;
	}

	/**
	 * @return the patientForm
	 */
	public PatientForm getPatientForm() {
		return patientForm;
	}

	/**
	 * @param patientForm the patientForm to set
	 */
	public void setPatientForm(PatientForm patientForm) {
		this.patientForm = patientForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		// Setting method name as useraction to be insert into Audit table
		String actionName = "Login";

		daoInf = new LoginDAOImpl();

		message = daoInf.verifyUserCredentials(form);

		if (message.equals("success")) {
			// System.out.println("Succcessfully Logged in...");

			form = daoInf.getUserDetail(form.getUsername());

			sessionAttriute.put("USER", form);

			patientDAOInf = new PatientDAOImpl();

			appointmentList = patientDAOInf.retrieveAppointmentList(form.getClinicID(), 0);

			appointmentWeekList = patientDAOInf.retrieveAppointmentWeekList(form.getClinicID(), 0);

			appointmentMonthList = patientDAOInf.retrieveAppointmentMonthList(form.getClinicID(), 0);

			/*
			 * Setting string in order to display the appointment table on dashboard
			 */
			String patientCheck = "Appointment";

			request.setAttribute("patientCheck", patientCheck);

			// request.setAttribute("appointmentList", appointmentList);

			// request.setAttribute("appointmentWeekList", appointmentWeekList);

			// request.setAttribute("appointmentMonthList", appointmentMonthList);

			if (appointmentList.size() == 0) {

				System.out.println("No Appointment found for today");

				String todayApptMsg = "No appointment available for today. Please add new appointment.";

				request.setAttribute("todayApptMsg", todayApptMsg);
			}

			if (appointmentWeekList.size() == 0) {

				System.out.println("No Appointment found for this week");

				String weekApptMsg = "No appointment available for this week. Please add new appointment.";

				request.setAttribute("weekApptMsg", weekApptMsg);
			}

			if (appointmentMonthList.size() == 0) {

				System.out.println("No Appointment found for this month");

				String monthApptMsg = "No appointment available for this month. Please add new appointment.";

				request.setAttribute("monthApptMsg", monthApptMsg);
			}

			if (form.getUserType().equals("clinician")) {

				UsageConsentList = daoInf.retrieveConsentDetailsByUserID(form.getUserID());

				sessionAttriute.put("UsageConsentList", UsageConsentList);

				request.setAttribute("consentCheck", "No");
			} else {
				// System.out.println("Inside else of LOGINACTION");
				/*
				 * UsageConsentList = new ArrayList<LoginForm>();
				 * 
				 * UsageConsentList.add(form); sessionAttriute.put("UsageConsentList", null);
				 */
				UsageConsentList = daoInf.retrieveConsentDetailsByUserID(form.getUserID());

				sessionAttriute.put("UsageConsentList", UsageConsentList);
				request.setAttribute("consentCheck", "No");
			}

			// System.out.println("UsageConsentList: " + UsageConsentList.size());

			ServletContext context = request.getServletContext();

			String realPath = context.getRealPath("/");

			// System.out.println("realpath is::" + realPath + File.separator);
			/*
			 * Checking whether clinic log file exists into images folder of context
			 * directory, is yes, do nothing, else, copy file from log file directory,
			 * mentioned in edhanvantari.xml file, into realPath's images directory
			 */
			String clincLogoFile = daoInf.retrieveClinicLogo(form.getClinicID());

			// File clinicLogo = new File(realPath + File.separator + clincLogoFile);
			// File clinicLogo = new File("images\\Clinic_Logo_3.png");

			System.out.println("CLINIC LOGO FILE PATH:::" + clincLogoFile);

			/*
			 * Checking whether user log file exists into images/profilePics folder of
			 * context directory, is yes, do nothing, else, copy file from log file
			 * directory, mentioned in edhanvantari.xml file, into realPath's
			 * images/profilePics directory
			 */
			String userLogoFile = daoInf.retrieveProfilePic(form.getUserID());

			// File userLogo = new File(realPath + File.separator + userLogoFile);
			System.out.println("USER LOGO FILE PATH:::" + userLogoFile);

			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), actionName, form.getUserID());

			/*
			 * Checking whether user type is Surveyor, if so, then show add patient page
			 * instead of welcome page, else show welcome page
			 */
			if (form.getUserType().equals("surveyor")) {
				return "SurveyorSuccess";
			} else if (form.getUserType().equals("pharmacist")) {
				return "PharmacistSuccess";
			} else {
				return SUCCESS;
			}
		} else if (message.equals("error")) {
			System.out.println("User is not authorised to login as LOGIN_FLAG is 0. Contact Admin.");
			addActionError("You are not authorised to login. Please contact adminstrator for further detail.");
			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), "Login Exception: Unauthorised user", form.getUserID());
			return ERROR;
		} else if (message.equals("input")) {
			System.out.println("Please check the user credetials. Wrong credentials entered.");
			addActionError("Enter valid username and password");
			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), "Login Exception: Invalid username and password",
					form.getUserID());
			return INPUT;
		} else if (message.equals("login")) {
			System.out.println("User is locked successfully.");
			addActionError(
					"You have exceeded the maximum limit of login attempts. Your account is now locked. Please contact your administrator to unlock it.");
			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), "Login Exception: Login attempts exceeded", form.getUserID());
			return LOGIN;
		} else if (message.equals("exception")) {
			System.out.println("Exception occured while logging in.");
			addActionError("Exception occurred while logging in. Please check logs for more details.");
			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), "Login Exception Occurred", form.getUserID());
			return "exception";
		} else {
			System.out.println("User activityStatus is Locked. Not authorised to login.");
			addActionError("Your account is either locked or inactive. Please contact your administrator.");
			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), "Login Exception: User account locked", form.getUserID());
			return "locked";
		}
	}
	
    public static String validateDateTime(String userDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        try {

            LocalDateTime inputDateTime = LocalDateTime.parse(userDateTime, formatter);

            LocalDateTime currentDateTime = LocalDateTime.now();

            if (inputDateTime.isBefore(currentDateTime)) {
                return "The provided date and time have already passed.";
            } else {
                return "The provided date and time are in the future.";
            }
        } catch (Exception e) {
        	
            return "Invalid date-time format. Please provide in 'dd-MM-yyyy HH:mm:ss' format.";
        }
    }

	/*
	 * public String patientLogin() throws Exception {
	 * System.out.println("inside patientlogin action..."); return SUCCESS; }
	 */

	public String patientLogin() throws Exception {
		System.out.println("inside patient login..." + form.getUsername() + "  " + form.getPatientID());
		String actionName = "PatientP";

		daoInf = new LoginDAOImpl();

		message = daoInf.verifyPatientCredentials(form);

		if (message.equals("success")) {
			System.out.println("Succcessfully Logged in...");

			form = daoInf.getUserPatientDetail(form.getUsername());

			sessionAttriute.put("USER", form);

			/*
			 * Getting clinicID & patientID from session
			 */
			LoginForm Form = (LoginForm) sessionAttriute.get("USER");

			patientVisitList = daoInf.retrievePatientVisitList(Form.getPatientID(), Form.getClinicID());

			if (patientVisitList.size() > 0) {

				request.setAttribute("patientListEnable", "patientListEnable");

			} else {

				addActionError("No Visit found. Please add new visit.");

			}

			return SUCCESS;
		} else if (message.equals("error")) {
			System.out.println("User is not authorised to login as LOGIN_FLAG is 0. Contact Admin.");
			addActionError("You are not authorised to login. Please contact adminstrator for further detail.");

			return ERROR;
		} else if (message.equals("input")) {
			System.out.println("Please check the user credetials. Wrong credentials entered.");
			addActionError("Enter valid username and password");

			return INPUT;
		} else if (message.equals("login")) {
			System.out.println("User is locked successfully.");
			addActionError(
					"You have exceeded the maximum limit of login attempts. Your account is now locked. Please contact your administrator to unlock it.");

			return LOGIN;
		} else if (message.equals("exception")) {
			System.out.println("Exception occured while logging in.");
			addActionError("Exception occurred while logging in. Please check logs for more details.");

			return "exception";
		} else {
			System.out.println("User activityStatus is Locked. Not authorised to login.");
			addActionError("Your account is either locked or inactive. Please contact your administrator.");

			return "locked";
		}
	}

	public String downloadCircular() throws Exception {
		daoInf = new LoginDAOImpl();
		ConfigurationUtil util = new ConfigurationUtil();

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

		File fileToDownload = new File(form.getCircularFileName());
		// Storing file to S3 RDML INPUT FILE location
		message = awss3Connect.pushFile(fileToDownload, form.getCircularFileName(), bucketName, bucketRegion,
				s3reportFilePath);

		S3ObjectInputStream s3ObjectInputStream = s3
				.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, form.getCircularFileName()))
				.getObjectContent();

		form.setFileInputStream(s3ObjectInputStream);

		/*
		 * InputStream fileInputStream; String fileName;
		 * 
		 * long contentLength;
		 * 
		 * File fileToDownload = new File(reportFilePath + form.getCircularFileName());
		 * fileInputStream = new FileInputStream(fileToDownload); fileName =
		 * fileToDownload.getName(); contentLength = fileToDownload.length();
		 * 
		 * form.setFileInputStream(fileInputStream);
		 */

		// Setting fileName to be given to downloaded zip file
		// name
		form.setFileName(fileToDownload.getName());

		return SUCCESS;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void verifyUnlockPIN() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			String lockPIN = request.getParameter("lockPIN");

			System.out.println("User entered PIN is ::: " + lockPIN);

			values = daoInf.verifyUserPIN(lockPIN, userForm.getUserID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying credentials.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	public void changeClinicForVisitList() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		System.out.println("changedclinicID in actiomn:" + form.getClinicID());

		int changedClinicID = form.getClinicID();

		userForm.setClinicID(changedClinicID);

		sessionAttriute.put("USER", userForm);

		int check = 0;

		try {
			visitList = daoInf.retrievePatientVisitList(form.getPatientID(), form.getClinicID());

			for (PatientForm rowlist : visitList) {
				object = new JSONObject();

				object.put("id", rowlist.getVisitID());
				object.put("visitDate", rowlist.getVisitDate());
				object.put("visitType", rowlist.getVisitType());
				object.put("diagnosis", rowlist.getDiagnosis());
				object.put("formName", rowlist.getFormName());
				object.put("visitTypeID", rowlist.getVisitTypeID());
				object.put("aptID", rowlist.getAptID());

				check = 1;
				object.put("check", check);

				array.add(object);
				values.put("Release1", array);
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while changing clinic");

			check = 0;

			object.put("check", check);

			array.add(object);

			values.put("Release1", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void verifyOldPass() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			String oldPass = request.getParameter("oldPass");

			System.out.println("User entered Old password is ::: " + oldPass);

			values = daoInf.verifyUserOldPassword(oldPass, userForm.getUserID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying old password.");

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
	public void updateSecurityCredentials() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			String newPass = request.getParameter("newPass");
			String newPIN = request.getParameter("newPIN");

			values = daoInf.updateUserSecurityCredentials(newPass, newPIN, userForm.getUserID());

			// Inserting new password into PasswordHistory table
			daoInf.insertPasswordHistory(userForm.getUserID(), newPass);

			if (newPass == null || newPass == "" || newPass.isEmpty()) {

				// Inserting into Audit
				daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", form.getUserID());

			} else if (newPIN == null || newPIN == "" || newPIN.isEmpty()) {

				// Inserting into Audit
				daoInf.insertAudit(request.getRemoteAddr(), "Password changed", form.getUserID());

			} else {

				// Inserting into Audit
				daoInf.insertAudit(request.getRemoteAddr(), "Password changed", form.getUserID());

				// Inserting into Audit
				daoInf.insertAudit(request.getRemoteAddr(), "PIN changed", form.getUserID());

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while updating security credentials.");

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
	public void newPassCheck() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			String newPass = request.getParameter("newPass");

			/*
			 * Password match check; checking whether entered password matches last five
			 * passwords from Password History, then give error message else proceed further
			 */
			boolean check = daoInf.verifyPasswordHistory(userForm.getUserID(), newPass);

			if (check) {

				System.out.println("Password already exists into password history");

				object.put("newPassCheck", "false");

				array.add(object);

				values.put("Release", array);

				PrintWriter out = response.getWriter();

				out.print(values);

			} else {

				System.out.println("New password encountered");

				object.put("newPassCheck", "true");

				array.add(object);

				values.put("Release", array);

				PrintWriter out = response.getWriter();

				out.print(values);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying new password with password history.");

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
	public String logoutUser() throws Exception {

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		// Inserting values into Audit table for user action
		daoInf.insertAudit(request.getRemoteAddr(), "Logout", userForm.getUserID());

		sessionAttriute.remove("USER");
		addActionMessage("You are successfully logged out");
		if (sessionAttriute.get("USER") == "" || sessionAttriute.get("USER") == null) {
			System.out.println("session removed");
		} else {
			System.out.println("error removing session object");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void getClinicStartEndTime() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveClinicStartEndTime(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic start and end time.");

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
	public void getClinicBreak1Time() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveClinicBreak1Time(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic break1 time.");

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
	public void getClinicBreak2Time() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveClinicBreak2Time(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic break2 time.");

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
	public void getDaysOtherThanClinicWorkingDays() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			System.out.println("clinicID.." + userForm.getClinicID());

			values = daoInf.retrieveDaysOtherThanClinicWorkingDays(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving days other than clinic working days");

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
	public void getClinicSlot1TimeAndName() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveSlot1NameAndTime(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving slot1 name and time.");

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
	public void getClinicSlot2TimeAndName() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveSlot2NameAndTime(userForm.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving slot2 name and time.");

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
	public void getVisitDuration() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrieveClinicVisitDuration(userForm.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving visit duration time.");

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
	public void verifyPatientDetails() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.verifyPatientDetails(form.getFirstName(), form.getMiddleName(), form.getLastName(),
					form.getMobileNo(), userForm.getClinicID(), userForm.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

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
	public void getAppointmentDuration() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveAppointmentDuration(form.getVisitTypeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving appointment duration based on visitTypeID.");

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
	public String changeClinic() throws Exception {

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		int changedPracticeID = form.getChangedPracticeID();

		userForm.setPracticeID(changedPracticeID);

		System.out.println("changedPracticeID:" + changedPracticeID + "-" + userForm.getPracticeID());

		int changedClinicID = form.getChangedClinicID();

		userForm.setClinicID(changedClinicID);

		// Retrieving new opd jsp page name based on newly changes clinicID
		String OPDJSPName = daoInf.retrieveOPDFormName(changedClinicID);

		// Also retrieving clinicName based on newly set clinicID and setting it
		// into session variable
		userForm.setClinicName(daoInf.retrieveClinicName(changedClinicID));

		userForm.setOPDJSPName(OPDJSPName);

		userForm.setPatientJSPName(daoInf.retrievePatientFormName(changedClinicID));

		userForm.setPracticeSuffix(daoInf.retrievePracticeSuffix(changedPracticeID));

		// Retrieving clinic suffix based on changed clinicID
		userForm.setClinicSuffix(daoInf.retrieveClinicSuffix(changedClinicID));

		sessionAttriute.put("USER", userForm);

		patientDAOInf = new PatientDAOImpl();

		appointmentList = patientDAOInf.retrieveAppointmentList(userForm.getClinicID(), 0);

		appointmentWeekList = patientDAOInf.retrieveAppointmentWeekList(userForm.getClinicID(), 0);

		appointmentMonthList = patientDAOInf.retrieveAppointmentMonthList(userForm.getClinicID(), 0);

		System.out.println("Clinic chagned successfully.");

		return SUCCESS;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void verifyPatientIntakeDetails() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		// LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			if (form.getFirstName() == null || form.getLastName() == null || form.getMobile() == null
					|| form.getPracticeID() == 0 || form.getClinicID() == 0) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				object.put("Warning", "please provide firstName, lastName, mobile, clinicID and practiceID");

				array.add(object);

				values.put("Release", array);

				PrintWriter out = response.getWriter();

				out.print(values);

				return;

			}

			values = daoInf.verifyPatientIntakeDetails(form.getMobile(), form.getPracticeID(), form.getClinicID(),
					form.getFirstName(), form.getMiddleName(), form.getLastName());
			if (values.isEmpty()) {
				addActionError("No patient found.");
			}
			PrintWriter out = response.getWriter();

			out.print(values);

			String qeuryParam = "id=" + String.valueOf(11);
			Map<String, String> requestParams = new HashMap<>();
			requestParams.put("id", qeuryParam);

			String encodeValue = URLEncoder.encode(requestParams.get("id"), StandardCharsets.UTF_8.toString());
			System.out.println(encodeValue);
			String practiceURL = "" + encodeValue;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient intake details.");

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
	public void sendOTPForVerification() throws Exception {
		patientDAOInf = new PatientDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		Random rand = new Random();

		String otp = String.format("%04d", rand.nextInt(10000));

		try {

			SMSSender sender = new SMSSender();

			// Check if mobile no is already verified for the current clinic, if not then
			// only send OTP else return relative message
			boolean otpStatus = patientDAOInf.verifyOTPVerified(form.getMobile(), form.getClinicID());

			if (otpStatus) {
				object.put("verifyCheck", "verified");
				object.put("otpStatus", "success");
			} else {
				object.put("verifyCheck", "not-verified");
				// Sending an OTP to mobile no
				message = sender.sendOTP(form.getMobile(), form.getPracticeID(), form.getClinicID(), otp);

				if (message.equals("success")) {
					object.put("otpStatus", "success");

					patientDAOInf.insertOTPDetails(otp, form.getMobile(), form.getClinicID());

				} else {
					object.put("otpStatus", "failed");
				}
			}

			object.put("otp", otp);

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("historyData", "");
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
	public void validateOTPForPatientIntake() throws Exception {
		patientDAOInf = new PatientDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		Random rand = new Random();

		String otp = String.format("%04d", rand.nextInt(10000));

		try {

			object.put("validateOTP", patientDAOInf.validateOTP(form.getMobile(), form.getClinicID()));

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("validateOTP", "error");

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
	public void redirectPatientIntakeDetails() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		// LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.redirectPatientIntakeDetails(form.getPatientID(), form.getPracticeID(), form.getClinicID());
			if (values.isEmpty()) {
				addActionError("No patient found.");
			}
			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient intake details.");

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
	public void getPIAppointmentType() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		// LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrievePIAppointmentType(form.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient intake details.");

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
	public void updatePatientDetails() throws Exception {
		System.out.println("Inside updatePatientDetails");
		daoInf = new LoginDAOImpl();

		JSONObject object = new JSONObject();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		String statusMessage = "";

		String medicalHisMessage = "";

		String identificationMessage = "";

		String ecMessage = "";
		
		if(form.getRegistrationNo() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			object.put("ErrMsg", "please provide patient registration number");
			array.add(object);
			values.put("Release", array);
			PrintWriter out = response.getWriter();

			out.print(values);
			return;
		}

		int pID = daoInf.retrievePIPatientIDByRegNumber(form.getRegistrationNo());
		
		if (pID == 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			object.put("ErrMsg", "please provide valid patient registration number");
			array.add(object);
			values.put("Release", array);
		} else {
			
			form.setPatientID(pID);

			statusMessage = daoInf.updatePIPatientDetails(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				if (form.getAsthema() != null || form.getHypertension() != null || form.getOtherDetails() != null
						|| form.getIschemicHeartDisease() != null || form.getIsDiabetes() != null) {
					medicalHisMessage = daoInf.updatePIMedicalHistory(form);
					if (medicalHisMessage.equalsIgnoreCase("success")) {
						object.put("updateMedicalHistoryStatus", medicalHisMessage);
					} else {
						object.put("updateMedicalHistoryStatus", medicalHisMessage);
					}
				}

				if (form.getEmFname() != null || form.getEmMname() != null || form.getEmLname() != null
						|| form.getEmRelation() != null || form.getEmEmailID() != null || form.getEmMobile() != null
						|| form.getEmPhone() != null || form.getEmCountry() != null || form.getEmState() != null
						|| form.getEmCity() != null || form.getEmAdd() != null) {
					ecMessage = daoInf.updatePIEmergencyContact(form.getPatientID(), form);

					if (ecMessage.equalsIgnoreCase("success")) {
						object.put("updateEcDetailsStatus", ecMessage);
					} else {
						object.put("updateEcDetailsStatus", ecMessage);
					}
				}

				if (form.getAadhaarNo() != null) {
					identificationMessage = daoInf.updatePIIdentification(form, form.getPatientID());

					if (identificationMessage.equalsIgnoreCase("success")) {
						object.put("updateIdentificationStatus", identificationMessage);
					} else {
						object.put("updateIdentificationStatus", identificationMessage);
					}
				}

				System.out.println("Patient Intake patient details updated successfully.");
				object.put("Msg", "Success");
				array.add(object);
				values.put("Release", array);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				object.put("ErrMsg", "error while updating patient details");
				array.add(object);
				values.put("Release", array);
			}
		}
		PrintWriter out = response.getWriter();

		out.print(values);
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void updateAppoitmentDetails() throws Exception {
		System.out.println("Inside updateAppoitmentDetails");
		daoInf = new LoginDAOImpl();

		JSONObject object = new JSONObject();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		String statusMessage = "";

		if (form.getUpdatedApptTimeFrom() != null) {
			if (form.getUpdatedApptTimeTo() == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				object.put("ErrMsg", "please provide required field (updatedApptTimeTo)");
				array.add(object);
				values.put("Release", array);
				PrintWriter out = response.getWriter();

				out.print(values);
				return;
			}
		} else {
			if (form.getUpdatedApptTimeTo() != null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				object.put("ErrMsg", "please provide required field (updatedApptTimeFrom)");
				array.add(object);
				values.put("Release", array);
				PrintWriter out = response.getWriter();

				out.print(values);
				return;
			}
		}

		if (form.getApptDate() == null || form.getApptTimeFrom() == null || form.getApptTimeTo() == null
				|| form.getRegistrationNo() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			object.put("ErrMsg", "please provide required fields (patientID, apptTimeFrom and apptDate)");
			array.add(object);
			values.put("Release", array);
			PrintWriter out = response.getWriter();

			out.print(values);
			return;
		} else {
			
			String msg = validateDateTime(form.getApptDate() + " " + form.getApptTimeFrom());
			
			if(msg.equals("The provided date and time have already passed.")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				object.put("ErrMsg",
						"the provided date and time have already passed");
				array.add(object);
				values.put("Release", array);
				PrintWriter out = response.getWriter();

				out.print(values);
				return;
			}
			
			if(msg.equals("Invalid date-time format. Please provide in 'dd-MM-yyyy HH:mm:ss' format.")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				object.put("ErrMsg",
						"invalid date-time format. Please provide in 'dd-MM-yyyy HH:mm:ss' format");
				array.add(object);
				values.put("Release", array);
				PrintWriter out = response.getWriter();

				out.print(values);
				return;
			} 

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			String appDate = dateFormat1.format(dateFormat.parse(form.getApptDate()));
			
			int patientID = daoInf.retrievePIPatientIDByRegNumber(form.getRegistrationNo());
			
			if(patientID == 0) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				String errMsg = "patient not found with registration number: " + form.getRegistrationNo();
				object.put("ErrMsg", errMsg);
				array.add(object);
				values.put("Release", array);
				
				PrintWriter out = response.getWriter();
				out.print(values);
				
				return;
			}
			
			form.setPatientID(patientID);
			
			int appID = daoInf.retrievePIAppointmentID(form.getPatientID(), form.getApptTimeFrom(),
					form.getApptTimeTo(), appDate);
			
			if(appID == 0) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				String errMsg = "patient with registration number " + form.getRegistrationNo() + 
		                " doesn't have any appointment on " + form.getApptDate() + 
		                " from " + form.getApptTimeFrom() + " to " + form.getApptTimeTo();
				object.put("ErrMsg", errMsg);
				array.add(object);
				values.put("Release", array);
				
				PrintWriter out = response.getWriter();
				out.print(values);
				
				return;
			}
			
			form.setApptID(appID);

			statusMessage = daoInf.updatePIAppointmentDetails(form);

			if (statusMessage.equalsIgnoreCase("success")) {
				System.out.println("Updated appointment details");
				object.put("Msg", "Success");
				array.add(object);
				values.put("Release", array);
			} else {
				object.put("ErrMsg", "error while updating appointment details");
				array.add(object);
				values.put("Release", array);
			}
			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addNewPatientIntakeAppointment() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		String statusMessage = "";

		String ecMessage = "";

		String clinicRegMessage = "";

		String medicalHisMessage = "";

		String identificationMessage = "";

		System.out.println(form.getRequestType());

		if (form.getRequestType() == null || form.getRequestType().equalsIgnoreCase("both")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			object.put("ErrMsg", "please provide valid request type (addPatient or addAppointment)");
			array.add(object);
			values.put("Release", array);
			PrintWriter out = response.getWriter();

			out.print(values);
		} else {

			try {

				if (form.getRequestType().equalsIgnoreCase("addPatient")) {

					if (form.getFirstName() == null || form.getLastName() == null || form.getGender() == null
							|| form.getAge() == 0 || form.getMobile() == null || form.getClinicID() == 0
							|| form.getPracticeID() == 0) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg",
								"please provide mandatory fields: firstName, lastName, gender, age, mobile, practiceID and clinicID");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);

						return;
					}

					int patientID = daoInf.retrievePIPatientIDByName(form);

					if (patientID != 0) {
						response.setStatus(HttpServletResponse.SC_CONFLICT);
						String outputMsg;
						if (form.getMiddleName() == null) {
							outputMsg = "patient with name " + form.getFirstName() + " " + form.getLastName()
									+ " already exist";
							object.put("ErrMsg", outputMsg);
						} else {
							outputMsg = "patient with name " + form.getFirstName() + " " + form.getMiddleName() + " "
									+ form.getLastName() + " already exist";
							object.put("ErrMsg", outputMsg);
						}

						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);

						return;
					}

					String clinicSuffix = daoInf.retrieveClinicSuffix(form.getClinicID());

					form.setRegistrationNo(
							patientDAOInf.retirevePatientClinicRegistrationNo(clinicSuffix, form.getClinicID()));

					statusMessage = daoInf.insertPIPatientDetails(form);

					if (statusMessage.equalsIgnoreCase("success")) {

						form.setPatientID(daoInf.retrievePIPatientIDByRegNumber(form.getRegistrationNo()));

						clinicRegMessage = daoInf.insertClinicRegistration(form.getRegistrationNo(), form.getClinicID(),
								form.getPatientID());

						ecMessage = daoInf.insertEmergencyContact(form.getPatientID(), form);

						identificationMessage = daoInf.insertIdentification(form, form.getPatientID());

						if (form.getPracticeID() != 29) {

							System.out.println("Patient Intake patient details inserted successfully.");
							object.put("insertClinicRegistrationStatus", clinicRegMessage);
							object.put("insertEmergencyContactStatus", ecMessage);
							object.put("insertIdentificationDetailsStatus", identificationMessage);
							object.put("registrationNo", form.getRegistrationNo());
							array.add(object);
							values.put("Release", array);
							PrintWriter out = response.getWriter();
							out.print(values);

							return;
						}

						medicalHisMessage = daoInf.insertPIMedicalHistory(form);

						if (medicalHisMessage.equalsIgnoreCase("success")) {
							System.out.println("Patient Intake patient details inserted successfully.");
							object.put("insertClinicRegistrationStatus", clinicRegMessage);
							object.put("insertEmergencyContactStatus", ecMessage);
							object.put("insertMedicalHistoryStatus", medicalHisMessage);
							object.put("insertIdentificationDetailsStatus", identificationMessage);
							object.put("registrationNo", form.getRegistrationNo());
							array.add(object);
							values.put("Release", array);
						} else {
							response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
							object.put("insertClinicRegistrationStatus", clinicRegMessage);
							object.put("insertEmergencyContactStatus", ecMessage);
							object.put("insertMedicalHistoryStatus", medicalHisMessage);
							object.put("insertIdentificationDetailsStatus", identificationMessage);
							object.put("registrationNo", form.getRegistrationNo());
							array.add(object);
							values.put("Release", array);
						}

					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						object.put("ErrMsg", "error while adding patient.");
						array.add(object);
						values.put("Release", array);
					}

				} else if (form.getRequestType().equalsIgnoreCase("addAppointment")) {

					if (form.getRegistrationNo() == null || form.getPracticeID() == 0 || form.getApptDate() == null
							|| form.getClinicID() == 0 || form.getApptTimeFrom() == null || form.getApptTimeTo() == null
							|| form.getRefDocFName() == null || form.getRefDocLName() == null
							|| form.getApptType() == 0) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg",
								"please provide mandatory fields: registrationNo, practiceID, clinicID, apptType, apptTimeFrom, apptTimeTo, refDocFName, refDocLName");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);
						return;
					}
					
					String msg = validateDateTime(form.getApptDate() + " " + form.getApptTimeFrom());
					
					if(msg.equals("The provided date and time have already passed.")) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg",
								"the provided date and time have already passed");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);
						return;
					}
					
					if(msg.equals("Invalid date-time format. Please provide in 'dd-MM-yyyy HH:mm:ss' format.")) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg",
								"invalid date-time format. Please provide in 'dd-MM-yyyy HH:mm:ss' format");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);
						return;
					} 
					

					int pID = daoInf.retrievePIPatientIDByRegNumber(form.getRegistrationNo());
					
					if(pID == 0) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg",
								"please provide valid registration number");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);
						return;
					}
					
					form.setPatientID(pID);

					int clinicianID = daoInf.retrieveClinicianIDByName(form);
					
					if(clinicianID == 0) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						object.put("ErrMsg", "please provide valid clinician name");
						array.add(object);
						values.put("Release", array);
						PrintWriter out = response.getWriter();

						out.print(values);
						return;
					}
					
					form.setClinicianID(clinicianID);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					
					String appDate = dateFormat1.format(dateFormat.parse(form.getApptDate()));
					
					int apptNumber = daoInf.retrieveAppointmentNumber(appDate, form.getClinicID());
					
					form.setApptNumber(apptNumber + 1);

					statusMessage = daoInf.insertPIAppointmentDetails(form);

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Patient Intake appointment inserted successfully.");
						object.put("Msg", "Success");
						array.add(object);
						values.put("Release", array);

					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						object.put("ErrMsg", "error while adding appointment.");
						array.add(object);
						values.put("Release", array);
					}
				} else if (form.getRequestType().equals("both")) {
					statusMessage = daoInf.insertPIPatientDetails(form);

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Patient Intake patient details inserted successfully.");

						form.setPatientID(
								daoInf.retrievePIPatientIDByFirstAndLastName(form.getFirstName(), form.getLastName()));

						statusMessage = daoInf.insertPIAppointmentDetails(form);

						if (statusMessage.equalsIgnoreCase("success")) {

							System.out.println("Patient Intake appointment inserted successfully.");

							statusMessage = daoInf.insertPIMedicalHistory(form);

							if (statusMessage.equalsIgnoreCase("success")) {

								System.out.println("Patient Intake medical history inserted successfully.");
								object.put("Msg", "Success");

								array.add(object);

								values.put("Release", array);
							} else {
								System.out.println("Patient Intake medical history insertion failed");
							}
						} else {
							System.out.println("Patient Intake appointment insertion failed.");
						}

					} else {
						System.out.println("Patient Intake patient details insertion failed.");
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						object.put("ErrMsg", "patient Intake patient details insertion failed");
						array.add(object);
						values.put("Release", array);
					}
				} else {

					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					object.put("ErrMsg", "please give valid request type (addPatient, addAppointment or all)");
					array.add(object);
					values.put("Release", array);

				}
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

	}

	public void searchPatientCRM() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		String statusMessage = "";

		patientDAOInf = new PatientDAOImpl();

		String patientName = request.getParameter("patientName");
		String mobileNum = request.getParameter("mobileNum");

		System.out.printf("name: %s", patientName);
		System.out.printf("mobile: %s", mobileNum);
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addPatientIntakeAppointment() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		String statusMessage = "";

		try {

			form.setPatientID(daoInf.retrievePIPatientIDByFirstAndLastName(form.getFirstName(), form.getLastName()));

			statusMessage = daoInf.insertPIAppointmentDetails(form);

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Patient Intake appointment inserted successfully.");

				if (form.getIsDiabetes() != "" || form.getHypertension() != "" || form.getAsthema() != ""
						|| form.getIschemicHeartDisease() != "" || form.getOtherDetails() != "") {

					statusMessage = daoInf.updatePIMedicalHistory(form);

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Patient Intake medical history inserted successfully.");

					}
				}

				object.put("Msg", "Success");

				array.add(object);

				values.put("Release", array);

			} else {
				System.out.println("Patient Intake appointment insertion failed");
			}

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
	public void getPIAppointmentSlots() throws Exception {

		daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		// LoginForm userForm = (LoginForm) sessionAttriute.get("USER");

		try {

			values = daoInf.retrievePIAppointmentSlots(form.getClinicID(), form.getApptDate());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient intake details.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * @return the appointmentList
	 */
	public List<PatientForm> getAppointmentList() {
		return appointmentList;
	}

	/**
	 * @return the appointmentWeekList
	 */
	public List<PatientForm> getAppointmentWeekList() {
		return appointmentWeekList;
	}

	/**
	 * @return the appointmentMonthList
	 */
	public List<PatientForm> getAppointmentMonthList() {
		return appointmentMonthList;
	}

	/**
	 * @param appointmentList the appointmentList to set
	 */
	public void setAppointmentList(List<PatientForm> appointmentList) {
		this.appointmentList = appointmentList;
	}

	/**
	 * @param appointmentWeekList the appointmentWeekList to set
	 */
	public void setAppointmentWeekList(List<PatientForm> appointmentWeekList) {
		this.appointmentWeekList = appointmentWeekList;
	}

	/**
	 * @param appointmentMonthList the appointmentMonthList to set
	 */
	public void setAppointmentMonthList(List<PatientForm> appointmentMonthList) {
		this.appointmentMonthList = appointmentMonthList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public LoginForm getModel() {
		return form;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSession(Map<String, Object> map) {

		this.sessionAttriute = map;

	}

	/**
	 * @return the usageConsentList
	 */
	public List<LoginForm> getUsageConsentList() {
		return UsageConsentList;
	}

	/**
	 * @param usageConsentList the usageConsentList to set
	 */
	public void setUsageConsentList(List<LoginForm> usageConsentList) {
		UsageConsentList = usageConsentList;
	}

}
