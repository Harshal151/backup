package com.edhanvantari.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.RegistrationDAOImpl;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.RegistrationForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.edhanvantari.util.ActivityStatus;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegistrationAction extends ActionSupport
		implements ModelDriven<RegistrationForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5415950492098762233L;

	RegistrationForm form = new RegistrationForm();
	RegistrationDAOinf registrationDAOinf = null;
	eDhanvantariServiceInf serviceInf = null;
	List<RegistrationForm> signedUpUserList = null;
	List<RegistrationForm> searchUserList = null;
	HashMap<Integer, String> clinicList = null;
	HashMap<Integer, String> practiceList = null;
	String message = null;

	HashMap<String, String> OPDJSPList;
	HashMap<String, String> IPDJSPList;

	List<RegistrationForm> leaveTypeList = null;
	List<RegistrationForm> leaveTypeEditList = null;

	HttpServletRequest request;
	HttpServletResponse response;

	private Map<String, Object> session = null;

	/**
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception {
		serviceInf = new eDhanvantariServiceImpl();
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.registerUser(form, realPath, request);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("New Sign Up user registered successfully. Record inserted successfully into database.");
			addActionMessage("Registered Successfully. Now Login with your username and password.");

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
			
			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add User", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {
			System.out.println("the entered username already exists into User table. Try with different username.");
			addActionError("Username already exists. Please use different username.");

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
			
			// Inserting values into Audit table for add user errors
			daoInf.insertAudit(request.getRemoteAddr(), "Add User error: username already exists.",
					userForm.getUserID());

			return INPUT;
		} else if (message.equalsIgnoreCase("clnicTypeSelect")) {
			addActionError("No clinic type selected. Please select Clinic type");

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
			
			// Inserting values into Audit table for add user error
			daoInf.insertAudit(request.getRemoteAddr(), "Add User error: clinic type not selected.",
					userForm.getUserID());

			return "clnicTypeSelect";
		} else {
			System.out.println("Error while inserting record into database.");
			addActionError("Failed to register user.Please check server logs for more details.");

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
			
			// Inserting values into Audit table for add user exception
			daoInf.insertAudit(request.getRemoteAddr(), "Add User Exception occurred.", userForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String render() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");
		
		System.out.println("...."+userForm.getPracticeID());
		
		if(userForm.getUserType().equals("superAdmin")) {
			
			practiceList = registrationDAOinf.getPracticeList();
		}else {
			
			practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
		}
		
		System.out.println("userForm.getUserID():"+userForm.getUserID()+"-"+userForm.getPracticeID());
		
		clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
		
		System.out.println("Clinic ID.." + userForm.getClinicID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderAddUser() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");
		
		System.out.println("...."+userForm.getPracticeID());
		
		if(userForm.getUserType().equals("superAdmin")) {
			
			practiceList = registrationDAOinf.getPracticeList();
		}else {
			
			practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
		}
		
		System.out.println("userForm.getUserID():"+userForm.getUserID()+"-"+userForm.getPracticeID());
		
		clinicList = registrationDAOinf.getClinicList(userForm.getPracticeID());
		
		System.out.println("Clinic ID.." + userForm.getClinicID());

		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userSignUp() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		message = registrationDAOinf.insertSignUpUserDetail(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("New user registered successfully. Record inserted successfully into database.");
			addActionMessage(
					"You are successfully signed up. Administration will reset your password for further process.");
			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {
			System.out
					.println("exception occurred while signing up user. Problem while inserting record into database.");
			addActionError("Exception occurred while signing up user.");
			return INPUT;
		} else {
			System.out.println("Error while inserting record into database.");
			addActionError("Failed to sign up user.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUserList() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		signedUpUserList = registrationDAOinf.retriveEditUSerList(userForm.getPracticeID());

		if(userForm.getUserType().equals("superAdmin")) {
			
			practiceList = registrationDAOinf.getPracticeList();
		}else {
			
			practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
		}

		int PracticeID = daoInf.retrievePracticeIDByuserID(userForm.getUserID());
		
		clinicList = registrationDAOinf.getClinicList(PracticeID);
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditUserList() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		System.out.println("userForm.getPracticeID():"+userForm.getPracticeID());
		signedUpUserList = registrationDAOinf.retriveEditUSerList(userForm.getPracticeID());

		if (signedUpUserList.size() > 0) {

			request.setAttribute("userListEnable", "userListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No user found. Please add new user.";

			addActionError(errorMsg);

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditUser() throws Exception {

		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();
		
		signedUpUserList = registrationDAOinf.retreiveUserDetailByUserID(form.getUserID());

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("activityStatus", signedUpUserList);

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");
		
		if(userForm.getUserType().equals("superAdmin")) {
			
			practiceList = registrationDAOinf.getPracticeList();
		}else {
			
			practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
		}

		int PracticeID = daoInf.retrievePracticeIDByuserID(userForm.getUserID());
		
		clinicList = registrationDAOinf.getClinicList(PracticeID);
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditProfile() throws Exception {

		registrationDAOinf = new RegistrationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		signedUpUserList = registrationDAOinf.retreiveUserProfileDetails(userForm.getUserID());

		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();
		System.out.println("User ID to be disabled..." + form.getUserID());

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = registrationDAOinf.rejectUser(form);
		if (message.equalsIgnoreCase("success")) {
			System.out.println("User disabled successfully.Activity Status changed to Inactive");
			addActionMessage("User disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "User Disabled", userForm.getUserID());

			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchUserName() == null || form.getSearchUserName() == "") {

				signedUpUserList = registrationDAOinf.retriveEditUSerList(userForm.getPracticeID());

				if (signedUpUserList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No user found. Please add new user.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchUserList = registrationDAOinf.searchUser(form.getSearchUserName(), userForm.getPracticeID());

				if (searchUserList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "User with name '" + form.getSearchUserName() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating user activity status to Inactive into database.");
			addActionError("Failed to disable user.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enableUser() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();
		System.out.println("User ID to be enabled..." + form.getUserID());

		message = registrationDAOinf.updateActivityStatusToActive(form);
		if (message.equalsIgnoreCase("success")) {
			System.out.println("User request rejected successfully.Activity Status changed to Reject");
			addActionMessage("User request rejected successfully.");
			return SUCCESS;

		} else {
			System.out.println("Error while updating user activity status to Rejected into database.");
			addActionError("Failed to reject user request.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUser() throws Exception {
		serviceInf = new eDhanvantariServiceImpl();
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editUserDetail(form, realPath, request);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Successfully updated user details.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			if(userForm.getUserType().equals("superAdmin")) {			
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			int PracticeID = daoInf.retrievePracticeIDByuserID(userForm.getUserID());
			
			clinicList = registrationDAOinf.getClinicList(PracticeID);
			
			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Password cannot repeat any of your previous 5 passwords. Please try different password.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			int PracticeID = daoInf.retrievePracticeIDByuserID(userForm.getUserID());
			
			clinicList = registrationDAOinf.getClinicList(PracticeID);
			
			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User error: password history check failed",
					userForm.getUserID());

			return INPUT;

		} else {

			addActionError("Failed to update user details. Please check logs for more details.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			if(userForm.getUserType().equals("superAdmin")) {
				
				practiceList = registrationDAOinf.getPracticeList();
			}else {
				
				practiceList = registrationDAOinf.getPracticeListForAdmin(userForm.getUserID());
			}

			int PracticeID = daoInf.retrievePracticeIDByuserID(userForm.getUserID());
			
			clinicList = registrationDAOinf.getClinicList(PracticeID);
			
			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User exception occurred.", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUserProfile() throws Exception {
		serviceInf = new eDhanvantariServiceImpl();
		registrationDAOinf = new RegistrationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editUserProfileDetail(form, realPath, request);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Profile updated successfully.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			// clinicList = registrationDAOinf.getClinicList();

			OPDJSPList = registrationDAOinf.retrieveOPDJSPList();

			IPDJSPList = registrationDAOinf.retrieveIPDJSPList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Password cannot repeat any of your previous 5 passwords. Please try different password.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			// clinicList = registrationDAOinf.getClinicList();

			OPDJSPList = registrationDAOinf.retrieveOPDJSPList();

			IPDJSPList = registrationDAOinf.retrieveIPDJSPList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile error: password history check failed",
					userForm.getUserID());

			return INPUT;

		} else {

			addActionError("Failed to update profile details. Please check logs for more details.");

			signedUpUserList = new ArrayList<RegistrationForm>();

			signedUpUserList.add(form);

			// clinicList = registrationDAOinf.getClinicList();

			OPDJSPList = registrationDAOinf.retrieveOPDJSPList();

			IPDJSPList = registrationDAOinf.retrieveIPDJSPList();

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile exception occurred.", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void changeSpecialisation() throws Exception {
		String userType = request.getParameter("userType");

		System.out.println("USer types is ::: " + userType);

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		if (userType.equals(ActivityStatus.ADMINISTRATOR)) {
			PrintWriter out = response.getWriter();

			out.print(values);
		} else if (userType.equals(ActivityStatus.CLINICIAN)) {
			object.put("Ophthalmologist", "Ophthalmologist");
			object.put("Orthopaedic", "Orthopaedic");
			object.put("Gynaecologist", "Gynaecologist");
			object.put("GeneralPhysician", "General Physician");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} else if (userType.equals(ActivityStatus.STAFF)) {
			object.put("Receptionist", "Receptionist");
			object.put("Optician", "Optician");

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
	public void changeSpecialisationByUserID() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		String userType = request.getParameter("userType");
		int userID = Integer.parseInt(request.getParameter("userID"));

		System.out.println("User id and user type is ::: " + userID + "::" + userType);

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		if (userType.equals(ActivityStatus.ADMINISTRATOR)) {
			PrintWriter out = response.getWriter();

			out.print(values);
		} else if (userType.equals(ActivityStatus.CLINICIAN)) {
			values = registrationDAOinf.retrieveClinicianSpecialisationDetail(userType, userID);

			PrintWriter out = response.getWriter();

			out.print(values);

		} else if (userType.equals(ActivityStatus.STAFF)) {
			values = registrationDAOinf.retrieveClinicianSpecialisationDetail(userType, userID);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchUser() throws Exception {
		registrationDAOinf = new RegistrationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		searchUserList = registrationDAOinf.searchUser(form.getSearchUserName(), userForm.getPracticeID());

		/*
		 * Checking whether userList is empty or not, if empty give error message saying
		 * User with name not found
		 */
		if (searchUserList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "User with name '" + form.getSearchUserName() + "' not found.";

			addActionError(errorMsg);

			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void getPracticeClinic() throws Exception {

		registrationDAOinf = new RegistrationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = registrationDAOinf.retrieveClinicByPracticeID(form.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic list based on practiceID");

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
	public void verifyUsername() throws Exception {

		registrationDAOinf = new RegistrationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			System.out.println("Username is ::: " + form.getUsername());

			values = registrationDAOinf.verifyUsernameExists(form.getUsername());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying username exists or not.");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * @return the leaveTypeList
	 */
	public List<RegistrationForm> getLeaveTypeList() {
		return leaveTypeList;
	}

	/**
	 * @param leaveTypeList
	 *            the leaveTypeList to set
	 */
	public void setLeaveTypeList(List<RegistrationForm> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	/**
	 * @return the leaveTypeEditList
	 */
	public List<RegistrationForm> getLeaveTypeEditList() {
		return leaveTypeEditList;
	}

	/**
	 * @param leaveTypeEditList
	 *            the leaveTypeEditList to set
	 */
	public void setLeaveTypeEditList(List<RegistrationForm> leaveTypeEditList) {
		this.leaveTypeEditList = leaveTypeEditList;
	}

	/**
	 * @return the practiceList
	 */
	public HashMap<Integer, String> getPracticeList() {
		return practiceList;
	}

	/**
	 * @param practiceList
	 *            the practiceList to set
	 */
	public void setPracticeList(HashMap<Integer, String> practiceList) {
		this.practiceList = practiceList;
	}

	/**
	 * @return the searchUserList
	 */
	public List<RegistrationForm> getSearchUserList() {
		return searchUserList;
	}

	/**
	 * @param searchUserList
	 *            the searchUserList to set
	 */
	public void setSearchUserList(List<RegistrationForm> searchUserList) {
		this.searchUserList = searchUserList;
	}

	/**
	 * @return the oPDJSPList
	 */
	public HashMap<String, String> getOPDJSPList() {
		return OPDJSPList;
	}

	/**
	 * @param oPDJSPList
	 *            the oPDJSPList to set
	 */
	public void setOPDJSPList(HashMap<String, String> oPDJSPList) {
		OPDJSPList = oPDJSPList;
	}

	/**
	 * @return the iPDJSPList
	 */
	public HashMap<String, String> getIPDJSPList() {
		return IPDJSPList;
	}

	/**
	 * @param iPDJSPList
	 *            the iPDJSPList to set
	 */
	public void setIPDJSPList(HashMap<String, String> iPDJSPList) {
		IPDJSPList = iPDJSPList;
	}

	/**
	 * @return the clinicList
	 */
	public HashMap<Integer, String> getClinicList() {
		return clinicList;
	}

	/**
	 * @param clinicList
	 *            the clinicList to set
	 */
	public void setClinicList(HashMap<Integer, String> clinicList) {
		this.clinicList = clinicList;
	}

	/**
	 * @return the signedUpUserList
	 */
	public List<RegistrationForm> getSignedUpUserList() {
		return signedUpUserList;
	}

	/**
	 * @param signedUpUserList
	 *            the signedUpUserList to set
	 */
	public void setSignedUpUserList(List<RegistrationForm> signedUpUserList) {
		this.signedUpUserList = signedUpUserList;
	}

	/**
	 * @return the form
	 */
	public RegistrationForm getForm() {
		return form;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm(RegistrationForm form) {
		this.form = form;
	}

	public RegistrationForm getModel() {
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

}
