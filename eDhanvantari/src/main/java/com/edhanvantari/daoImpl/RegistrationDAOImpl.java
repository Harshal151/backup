package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.form.RegistrationForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.EncDescUtil;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;
import com.edhanvantari.util.EmailUtil;

public class RegistrationDAOImpl extends DAOConnection implements RegistrationDAOinf {

	Connection connection = null;
	Connection connection1 = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	ResultSet resultSet2 = null;
	ResultSet resultSet3 = null;
	ResultSet resultSet4 = null;
	String status = "error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertUser(com.edhanvantari
	 * .form.RegistrationForm)
	 */
	public String insertUser(RegistrationForm registrationForm) {

		try {
			connection = getConnection();

			// username, password, userType, activityStatus, specialization,
			// defaultClinicID, pin, practiceID

			String insertUserQuery = QueryMaker.INSERT_USER_DETAIL;

			preparedStatement = connection.prepareStatement(insertUserQuery);

			preparedStatement.setString(1, registrationForm.getUsername());

			/*
			 * Encrypt the password before inserting into database
			 */
			String ecryptedPassword = EncDescUtil.EncryptText(registrationForm.getPassword());

			/*
			 * Encrypt PIN before inserting into User table
			 */
			String encryptedPIN = EncDescUtil.EncryptText(registrationForm.getPIN());

			preparedStatement.setString(2, ecryptedPassword);
			preparedStatement.setString(3, registrationForm.getUserType());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setString(8, registrationForm.getFirstName());
			preparedStatement.setString(9, registrationForm.getMiddleName());
			preparedStatement.setString(10, registrationForm.getLastName());
			preparedStatement.setInt(5, registrationForm.getClinicID());
			preparedStatement.setString(11, registrationForm.getAddress());
			preparedStatement.setString(12, registrationForm.getCity());
			preparedStatement.setString(13, registrationForm.getState());
			preparedStatement.setString(14, registrationForm.getCountry());
			preparedStatement.setString(15, registrationForm.getPhoneNo());
			preparedStatement.setString(16, registrationForm.getMobile());
			preparedStatement.setString(17, registrationForm.getEmailID());
			preparedStatement.setString(6, encryptedPIN);
			preparedStatement.setString(18, registrationForm.getProfilePicDBName());
			preparedStatement.setString(19, registrationForm.getSignatureDBName());
			preparedStatement.setInt(7, registrationForm.getPracticeID());
			preparedStatement.setString(20, registrationForm.getQualification());
			preparedStatement.setString(21, registrationForm.getClinicianRegNo());
			
			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting user detail into table due to:::" + exception.getMessage());
			status = "input";
		}

		return status;
	}

	public String retrieveSignature(int userID) {
		String signatureName = null;

		try {

			connection = getConnection();

			String retrieveSignLogoQuery = QueryMaker.RETRIEVE_USER_SIGNATURE_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveSignLogoQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				signatureName = resultSet.getString("signImage");

			}

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving profile pic name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return signatureName;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertSignUpUserDetail(com
	 * .edhanvantari.form.RegistrationForm)
	 */
	public String insertSignUpUserDetail(RegistrationForm registrationForm) {
		try {

			connection = getConnection();

			String insertSignUpUserDetailQuery = QueryMaker.INSERT_SIGN_UP_USER_DETAIL;

			preparedStatement = connection.prepareStatement(insertSignUpUserDetailQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setString(4, registrationForm.getEmailID());
			preparedStatement.setString(5, registrationForm.getUsername());
			preparedStatement.setString(6, registrationForm.getComments());
			preparedStatement.setString(7, ActivityStatus.PENDING);

			preparedStatement.execute();

			status = "success";
			System.out.println("SignUp user detail inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting user detail into table due to:::" + exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retreiveSignedUpUserList()
	 */
	public List<RegistrationForm> retriveEditUSerList(int practiceID) {

		List<RegistrationForm> list = new ArrayList<RegistrationForm>();
		RegistrationForm registrationForm = null;

		try {

			connection = getConnection();

			String retriveEditUSerListQuery = QueryMaker.RETREIVE_EDIT_USER_LIST;

			preparedStatement = connection.prepareStatement(retriveEditUSerListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				registrationForm = new RegistrationForm();

				registrationForm.setUserID(resultSet.getInt("id"));
				registrationForm.setUsername(resultSet.getString("username"));
				registrationForm.setUserType(resultSet.getString("userType"));
				registrationForm.setActivityStatus(resultSet.getString("activityStatus"));
				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {
					registrationForm
							.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				} else if (resultSet.getString("middleName").isEmpty()) {
					registrationForm
							.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				} else {
					registrationForm.setFullName(resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName") + " " + resultSet.getString("lastName"));
				}

				list.add(registrationForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving edit user list from database due to:::"
					+ exception.getMessage());

		}
		return list;
	}

	public HashMap<String, String> retrieveUserDetails(int practiceID, String username) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveMDDetailsQuery = QueryMaker.RETRIEVE_USER_NAME_SIGN;

			preparedStatement = connection.prepareStatement(retrieveMDDetailsQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, username);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				map.put("FirstName", resultSet.getString("firstName"));
				map.put("LastName", resultSet.getString("lastName"));
				map.put("Signature", resultSet.getString("signImage"));
			
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retreiveUserDetailByUserID
	 * (int)
	 */
	public List<RegistrationForm> retreiveUserDetailByUserID(int userID) {

		List<RegistrationForm> userList = new ArrayList<RegistrationForm>();
		RegistrationForm registrationForm = new RegistrationForm();

		try {
			connection = getConnection();

			String retreiveUserDetailByUserIDQuery = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement = connection.prepareStatement(retreiveUserDetailByUserIDQuery);
			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				registrationForm.setUserID(resultSet.getInt("id"));
				registrationForm.setUsername(resultSet.getString("username"));
				String decryPass = EncDescUtil.DecryptText(resultSet.getString("password"));
				registrationForm.setPassword(decryPass);
				registrationForm.setUserType(resultSet.getString("userType"));
				registrationForm.setActivityStatus(resultSet.getString("activityStatus"));
				registrationForm.setFirstName(resultSet.getString("firstName"));
				registrationForm.setMiddleName(resultSet.getString("middleName"));
				registrationForm.setLastName(resultSet.getString("lastName"));

				int clinicID = resultSet.getInt("defaultClinicID");
				int practiceID = resultSet.getInt("practiceID");

				registrationForm.setClinicID(clinicID);
				registrationForm.setClinicName(retrieveClinicNameByClinicID(clinicID));
				registrationForm.setAddress(resultSet.getString("address"));
				registrationForm.setCity(resultSet.getString("city"));
				registrationForm.setState(resultSet.getString("state"));
				registrationForm.setCountry(resultSet.getString("country"));
				registrationForm.setMobile(resultSet.getString("mobile"));
				registrationForm.setPhoneNo(resultSet.getString("phone"));
				registrationForm.setEmailID(resultSet.getString("email"));
				registrationForm.setPracticeID(practiceID);
				registrationForm.setPracticeName(retrievePracticeNameByPracticeID(practiceID));
				registrationForm.setClinicianRegNo(resultSet.getString("clinicianRegNo"));
				registrationForm.setQualification(resultSet.getString("qualification"));
				
				String one = resultSet.getString("profilePic");
				
				if(one =="" || one ==null) {
					registrationForm.setProfilePicDBName("");
				}else if(one.isEmpty()) {
					registrationForm.setProfilePicDBName("");
				}else {
					String[] bits = one.split("/");
					String profPicName = bits[bits.length-1];
					registrationForm.setProfilePicDBName(profPicName);
				}
				
				String two = resultSet.getString("signImage");
				
				if(two =="" || two ==null) {
					registrationForm.setSignatureDBName("");
				}else if(two.isEmpty()) {
					registrationForm.setSignatureDBName("");
				}else {
					
					String[] bits1 = two.split("/");
					String signImgName = bits1[bits1.length-1];
					registrationForm.setSignatureDBName(signImgName);
				}
				
				if (resultSet.getString("pin") == null || resultSet.getString("pin") == "") {

					registrationForm.setPIN(resultSet.getString("pin"));

				} else {

					String decryPIN = EncDescUtil.DecryptText(resultSet.getString("pin"));
					registrationForm.setPIN(decryPIN);

				}

				userList.add(registrationForm);
			}

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retreiving user detail based on user id from database due to:::"
					+ exception.getMessage());
		}
		return userList;
	}

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public String retrieveClinicNameByClinicID(int clinicID) {

		String clinicName = "";

		try {

			connection = getConnection();

			String retrieveClinicNameByClinicIDQuery = QueryMaker.RETRIEVE_CLINIC_NAME_BY_CLINIC_ID;

			preparedStatement1 = connection.prepareStatement(retrieveClinicNameByClinicIDQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				clinicName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retreiving clinic name based on clinic id due to:::"
					+ exception.getMessage());
		}

		return clinicName;
	}

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrievePracticeNameByPracticeID(int practiceID) {

		String practiceName = "";

		try {

			connection = getConnection();

			String retrievePracticeNameByPracticeIDQuery = QueryMaker.RETRIEVE_PRACTICE_NAME_BY_PRACTICE_ID;

			preparedStatement1 = connection.prepareStatement(retrievePracticeNameByPracticeIDQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				practiceName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retreiving practice name based on practice id due to:::"
					+ exception.getMessage());
		}

		return practiceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateUserDetail(com.
	 * edhanvantari .form.RegistrationForm)
	 */
	public String updateUserDetail(RegistrationForm registrationForm) {
		try {
			System.out.println("sign img::"+registrationForm.getSignatureDBName());
			System.out.println("usertype::"+registrationForm.getUserType());
			connection = getConnection();

			String updateUserDetailQuery = QueryMaker.UPDATE_USER_DETAIL;

			preparedStatement = connection.prepareStatement(updateUserDetailQuery);

			/*
			 * Encrypt the password before inserting into database
			 */
			String ecryptedPassword = EncDescUtil.EncryptText(registrationForm.getPassword());

			/*
			 * Encrypt PIN before inserting into User table
			 */
			String encryptedPIN = EncDescUtil.EncryptText(registrationForm.getPIN());

			preparedStatement.setString(1, ecryptedPassword);
			preparedStatement.setString(2, registrationForm.getUserType());
			preparedStatement.setInt(3, registrationForm.getClinicID());
			preparedStatement.setString(4, encryptedPIN);
			preparedStatement.setInt(5, registrationForm.getPracticeID());
			preparedStatement.setString(6, registrationForm.getActivityStatus());
			preparedStatement.setString(7, registrationForm.getFirstName());
			preparedStatement.setString(8, registrationForm.getMiddleName());
			preparedStatement.setString(9, registrationForm.getLastName());
			preparedStatement.setString(10, registrationForm.getAddress());
			preparedStatement.setString(11, registrationForm.getCity());
			preparedStatement.setString(12, registrationForm.getState());
			preparedStatement.setString(13, registrationForm.getCountry());
			preparedStatement.setString(14, registrationForm.getPhoneNo());
			preparedStatement.setString(15, registrationForm.getMobile());
			preparedStatement.setString(16, registrationForm.getEmailID());
			preparedStatement.setString(17, registrationForm.getProfilePicDBName());
			preparedStatement.setString(18, registrationForm.getSignatureDBName());
			preparedStatement.setString(19, registrationForm.getQualification());
			preparedStatement.setString(20, registrationForm.getClinicianRegNo());
			preparedStatement.setString(21, registrationForm.getUsername());
			preparedStatement.setInt(22, registrationForm.getUserID());
			preparedStatement.execute();

			System.out.println("User detail updated successfully.");
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail into database due to:::" + exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#rejectUser(com.edhanvantari
	 * .form.RegistrationForm)
	 */
	public String rejectUser(RegistrationForm registrationForm) {

		try {
			connection = getConnection();

			String rejectUserQuery = QueryMaker.REJECT_USER;

			preparedStatement = connection.prepareStatement(rejectUserQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, registrationForm.getUserID());

			preparedStatement.execute();

			System.out.println("User detail updated (For disable user) successfully.");
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail for disabling activity status of user from database due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#approveUserRequest(int)
	 */
	public String approveUserRequest(int userID) {

		try {
			connection = getConnection();

			String approveUserRequestQuery = QueryMaker.UPDATE_USER_REQUEST_TABLE;

			preparedStatement = connection.prepareStatement(approveUserRequestQuery);
			preparedStatement.setString(1, ActivityStatus.APPROVED);
			preparedStatement.setInt(2, userID);

			preparedStatement.execute();
			System.out.println("approval status from UserRequest table successfully changed to Approve");

			status = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail while approving request into database due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retreiveUserId(int)
	 */
	public int retreiveUserId(int userRequestID) {
		System.out.println("Requested user ID is::::" + userRequestID);
		int userID = 0;
		try {

			connection = getConnection();

			String retreiveUserIdQuery = QueryMaker.RETREIVE_USER_ID;

			preparedStatement = connection.prepareStatement(retreiveUserIdQuery);
			preparedStatement.setInt(1, userRequestID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userID = resultSet.getInt("id");
			}

			System.out.println("User ID of User table is:::" + userID);
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retreiving user ID on the basis of userRequestId from table User due to:::"
							+ exception.getMessage());

		}
		return userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertClinicianDetail(int,
	 * com.edhanvantari.form.RegistrationForm)
	 */
	public String insertClinicianDetail(int userID, RegistrationForm registrationForm) {

		try {

			connection = getConnection();

			String insertClinicianDetailQuery = QueryMaker.INSERT_CLINICIAN_DETAIL;

			preparedStatement = connection.prepareStatement(insertClinicianDetailQuery);
			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, registrationForm.getClinicID());

			preparedStatement.execute();

			System.out.println("Successfully inserted clinician detail into Clinician table");
			status = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting clinicians detail while approving user request into database due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertPatientDetail(int,
	 * com.edhanvantari.form.RegistrationForm)
	 */
	public String insertPatientDetail(int userID, RegistrationForm registrationForm) {

		try {

			connection = getConnection();

			String insertPatientDetailQuery = QueryMaker.INSERT_PATIENT_DETAIL;

			preparedStatement = connection.prepareStatement(insertPatientDetailQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, registrationForm.getClinicID());

			preparedStatement.execute();

			System.out.println("Successfully inserted patient detail into Patient table");
			status = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting patient detail while approving user request into database due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertContactInfo(int,
	 * com.edhanvantari.form.RegistrationForm)
	 */
	public String insertContactInfo(int userID, RegistrationForm registrationForm) {

		try {
			connection = getConnection();

			String insertContactInfoQuery = QueryMaker.INSERT_CONTACT_INFO;

			preparedStatement = connection.prepareStatement(insertContactInfoQuery);

			preparedStatement.setString(1, registrationForm.getAddress());
			preparedStatement.setString(2, registrationForm.getCity());
			preparedStatement.setString(3, registrationForm.getState());
			preparedStatement.setString(4, registrationForm.getCountry());
			preparedStatement.setString(5, registrationForm.getPhoneNo());
			preparedStatement.setString(6, registrationForm.getMobile());
			preparedStatement.setString(7, registrationForm.getEmailID());
			preparedStatement.setInt(8, userID);

			preparedStatement.execute();

			System.out.println("Successfully inserted contact information into ContactInformation table.");

			status = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting contact information into ContactInformation table due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retrieveUserIDByUsername(java
	 * .lang.String)
	 */
	public int retrieveUserIDByUsername(String username) {
		int userId = 0;

		try {
			connection = getConnection();

			String retrieveUserIDByUsernameQuery = QueryMaker.RETRIEVE_USERID;

			preparedStatement = connection.prepareStatement(retrieveUserIDByUsernameQuery);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userId = resultSet.getInt("id");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());
		}
		return userId;
	}
	
	public int retrievePatientIDByUsername(String username) {
		int patientId = 0;

		try {
			connection = getConnection();

			String retrieveUserIDByUsernameQuery = QueryMaker.RETRIEVE_PATIENTID;

			preparedStatement = connection.prepareStatement(retrieveUserIDByUsernameQuery);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientId = resultSet.getInt("id");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving patient ID from Patient table due to:::" + exception.getMessage());
		}
		return patientId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#verifyUsername(java.lang.
	 * String )
	 */
	public int verifyUsername(String username, int practiceID) {
		int status = 0;

		try {
			connection = getConnection();

			String verifyUsernameQuery = QueryMaker.VERIFY_USERNAME;

			preparedStatement = connection.prepareStatement(verifyUsernameQuery);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, practiceID);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateAdminUserActivitySatus
	 * (com.edhanvantari.form.RegistrationForm)
	 */
	public String updateAdminUserType(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updateAdminUserActivitySatusQuery = QueryMaker.UPDATE_ADMIN_USER_STATUS;

			preparedStatement = connection.prepareStatement(updateAdminUserActivitySatusQuery);

			preparedStatement.setString(1, registrationForm.getUserType());
			preparedStatement.setInt(2, registrationForm.getUserID());

			preparedStatement.executeUpdate();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating user type to administrator into User table due to:::"
					+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateClinician(com.
	 * edhanvantari .form.RegistrationForm)
	 */
	public String updateClinician(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updateClinicianQuery = QueryMaker.UPDATE_CLINICIAN_DETAIL;

			preparedStatement = connection.prepareStatement(updateClinicianQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, registrationForm.getClinicID());
			preparedStatement.setInt(5, registrationForm.getUserID());

			preparedStatement.executeUpdate();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating clinician details into Clinician table due to:::"
					+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateStaff(com.edhanvantari
	 * .form.RegistrationForm)
	 */
	public String updateStaff(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updateStaffQuery = QueryMaker.UPDATE_STAFF_DETAIL;

			preparedStatement = connection.prepareStatement(updateStaffQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, registrationForm.getClinicID());
			preparedStatement.setInt(5, registrationForm.getUserID());

			preparedStatement.executeUpdate();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating staff details into Staff table due to:::"
					+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.RegistrationDAOinf#updatePatient(com.edhanvantari
	 * .form.RegistrationForm)
	 */
	public String updatePatient(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updatePatientQuery = QueryMaker.UPDATE_PATIENT_DETAIL;

			preparedStatement = connection.prepareStatement(updatePatientQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, registrationForm.getClinicID());
			preparedStatement.setInt(5, registrationForm.getUserID());

			preparedStatement.executeUpdate();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating patient details into Patient table due to:::"
					+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateContactInfo(com.
	 * edhanvantari .form.RegistrationForm)
	 */
	public String updateContactInfo(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updateContactInfoQuery = QueryMaker.UPDATE_CONTACT_INFO;

			preparedStatement = connection.prepareStatement(updateContactInfoQuery);

			preparedStatement.setString(1, registrationForm.getAddress());
			preparedStatement.setString(2, registrationForm.getCity());
			preparedStatement.setString(3, registrationForm.getState());
			preparedStatement.setString(4, registrationForm.getCountry());
			preparedStatement.setString(5, registrationForm.getPhoneNo());
			preparedStatement.setString(6, registrationForm.getMobile());
			preparedStatement.setString(7, registrationForm.getEmailID());
			preparedStatement.setInt(8, registrationForm.getUserID());

			preparedStatement.executeUpdate();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating contact information details into ContactInformation table due to:::"
							+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertAdministrator(com.
	 * edhanvantari.form.RegistrationForm)
	 */
	public String insertAdministrator(RegistrationForm registrationForm, int userID) {
		try {
			connection = getConnection();

			String insertAdministratorQuery = QueryMaker.INSERT_ADMINISTRATOR;

			preparedStatement = connection.prepareStatement(insertAdministratorQuery);

			preparedStatement.setString(1, registrationForm.getFirstName());
			preparedStatement.setString(2, registrationForm.getMiddleName());
			preparedStatement.setString(3, registrationForm.getLastName());
			preparedStatement.setInt(4, userID);

			preparedStatement.execute();
			status = "success";
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Administrator details into ADministrator table due to:::"
							+ exception.getMessage());
			status = "input";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#updateActivityStatusToActive
	 * (int)
	 */
	public String updateActivityStatusToActive(RegistrationForm registrationForm) {
		try {
			connection = getConnection();

			String updateActivityStatusToActiveQuery = QueryMaker.REJECT_USER;

			preparedStatement = connection.prepareStatement(updateActivityStatusToActiveQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, registrationForm.getUserID());

			preparedStatement.execute();

			System.out.println("User detail updated (For enable user) successfully.");
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail for enabling activity status of user from database due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#getClinicList()
	 */
	public HashMap<Integer, String> getClinicList(int practiceID) {
		HashMap<Integer, String> clinicList = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String getClinicListQuery = QueryMaker.RETRIEVE_CLINIC_LIST;

			preparedStatement = connection.prepareStatement(getClinicListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int clinicID = resultSet.getInt("id");
				String clinicName = resultSet.getString("name");

				clinicList.put(clinicID, clinicName);
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while getting Clinic List from database due to:::" + exception.getMessage());
		}
		return clinicList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#
	 * retrieveClinicianSpecialisationDetail(java.lang.String, int)
	 */
	public JSONObject retrieveClinicianSpecialisationDetail(String userType, int userID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();
		try {
			connection = getConnection();

			String retrieveClinicianSpecialisationDetailQuery = QueryMaker.RETRIEVE_CLINICIAN_SPECIALISATION_DETAIL;

			preparedStatement = connection.prepareStatement(retrieveClinicianSpecialisationDetailQuery);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, userType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String specialsation = resultSet.getString("specialization");

				String[] specArray = specialsation.split(",");
				int count = 1;
				for (int i = 0; i < specArray.length; i++) {
					object.put(count, specArray[i]);
					count++;
				}
				array.add(object);
				values.put("Release", array);
			}

			preparedStatement.close();
			resultSet.close();
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving  clinician specialisation details  due to:::"
					+ exception.getMessage());
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#
	 * retrieveStaffSpecialisationDetail (java.lang.String, int)
	 */
	public JSONObject retrieveStaffSpecialisationDetail(String userType, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();
		try {
			connection = getConnection();

			String retrieveStaffSpecialisationDetailQuery = QueryMaker.RETRIEVE_STAFF_SPECIALISATION_DETAIL;

			preparedStatement = connection.prepareStatement(retrieveStaffSpecialisationDetailQuery);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, userType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String specialsation = resultSet.getString("specialization");

				String[] specArray = specialsation.split(",");
				int count = 1;
				for (int i = 0; i < specArray.length; i++) {
					object.put(count, specArray[i]);
					count++;
				}
				array.add(object);
				values.put("Release", array);
			}

			preparedStatement.close();
			resultSet.close();
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving  clinician specialisation details  due to:::"
					+ exception.getMessage());
		}
		return values;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retrieveSpecialisation(int)
	 */
	public String retrieveUserType(int userID) {
		String userType = null;
		try {
			connection = getConnection();

			String retrieveSpecialisationQuery = QueryMaker.RETRIEVE_USER_TYPE;

			preparedStatement = connection.prepareStatement(retrieveSpecialisationQuery);
			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userType = resultSet.getString("userType");
			}	
			
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving  user Type details  due to:::" + exception.getMessage());
		}
		return userType;
	}

	public HashMap<String, String> retrieveOPDJSPList() {
		HashMap<String, String> OPDJSPList = new HashMap<String, String>();

		try {
			connection = getConnection();

			String retrieveOPDJSPListQuery = QueryMaker.RETRIEVE_OPD_JSP_LIST;

			preparedStatement = connection.prepareStatement(retrieveOPDJSPListQuery);
			preparedStatement.setString(1, "OPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String OPDSJSPName = resultSet.getString("jspPageName");

				String formName = resultSet.getString("formName");

				OPDJSPList.put(OPDSJSPName, formName);
			}
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while getting OPD JSP page list from database due to:::"
					+ exception.getMessage());
		}
		return OPDJSPList;
	}

	public HashMap<String, String> retrieveIPDJSPList() {
		HashMap<String, String> IPDJSPList = new HashMap<String, String>();

		try {
			connection = getConnection();

			String retrieveOPDJSPListQuery = QueryMaker.RETRIEVE_IPD_JSP_LIST;

			preparedStatement = connection.prepareStatement(retrieveOPDJSPListQuery);
			preparedStatement.setString(1, "IPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String IPDJSPName = resultSet.getString("jspPageName");

				String formName = resultSet.getString("formName");

				IPDJSPList.put(IPDJSPName, formName);
			}
			
			connection.close();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while getting IPD JSP page list from database due to:::"
					+ exception.getMessage());
		}
		return IPDJSPList;
	}

	public List<RegistrationForm> searchUser(String searchUserName, int practiceID) {

		List<RegistrationForm> list = new ArrayList<RegistrationForm>();

		RegistrationForm form = null;

		try {

			connection = getConnection();

			String searchUserQuery = QueryMaker.SEARCH_USER_BY_USER_NAME;

			preparedStatement = connection.prepareStatement(searchUserQuery);

			if (searchUserName.contains(" ")) {
				searchUserName = searchUserName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchUserName + "%");
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new RegistrationForm();

				form.setUserID(resultSet.getInt("id"));
				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {
					form.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				} else if (resultSet.getString("middleName").isEmpty()) {
					form.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				} else {
					form.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " "
							+ resultSet.getString("lastName"));
				}
				form.setUsername(resultSet.getString("username"));
				form.setActivityStatus(resultSet.getString("activityStatus"));
				form.setUserType(resultSet.getString("userType"));
				form.setSearchUserName(searchUserName);

				list.add(form);

			}
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public void insertPasswordHistory(int userID, String password) {

		String encryptedPass = EncDescUtil.EncryptText(password);

		try {

			connection = getConnection();

			String insertPasswordHistoryQuery = QueryMaker.INSERT_PASSWORD_HISTORY;

			preparedStatement = connection.prepareStatement(insertPasswordHistoryQuery);

			preparedStatement.setString(1, encryptedPass);
			preparedStatement.setInt(2, userID);

			preparedStatement.execute();

			System.out.println("Successfully insertd password into PasswordHistory table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public boolean verifyUsernameWithUserID(String username, int userID, int practiceID) {

		boolean status = true;

		try {
			connection = getConnection();

			String verifyUsernameWithUserIDQuery = QueryMaker.VERIFY_USERNAME_WITH_USER_ID;

			preparedStatement = connection.prepareStatement(verifyUsernameWithUserIDQuery);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setInt(3, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = false;
			}
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());

			status = false;
		}
		return status;
	}

	public boolean verifyPINChange(int userID, String pin) {

		boolean result = false;
		// Encrypting PIN
		String encryptedPIN = EncDescUtil.EncryptText(pin);

		try {

			connection = getConnection();

			String verifyPINChangeQuery = QueryMaker.VERIFY_PIN_CHANGE;

			preparedStatement = connection.prepareStatement(verifyPINChangeQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, encryptedPIN);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		}
		return result;
	}

	public boolean verifyPassword(int userID, String password) {

		boolean result = false;
		// Encrypting password
		String encryptedPass = EncDescUtil.EncryptText(password);

		try {

			connection = getConnection();

			String verifyPasswordQuery = QueryMaker.VERIFY_PASSWORD;

			preparedStatement = connection.prepareStatement(verifyPasswordQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, encryptedPass);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		}
		return result;
	}

	public boolean verifyPasswordHistory(int userID, String password) {

		// Encrypting password
		String encryptedPass = EncDescUtil.EncryptText(password);

		boolean result = false;

		try {

			connection = getConnection();

			String verifyPasswordHistoryQuery = QueryMaker.VERIFY_PASSWORD_HISTORY;

			preparedStatement = connection.prepareStatement(verifyPasswordHistoryQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, encryptedPass);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		}
		return result;
	}

	public HashMap<Integer, String> getPracticeList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getPracticeListQuery = QueryMaker.RETRIEVE_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(getPracticeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public JSONObject retrieveClinicByPracticeID(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveClinicByPracticeIDQuery = QueryMaker.RETRIEVE_CLINIC_DETAILS_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("clinicVal", resultSet.getInt("id") + "=" + resultSet.getString("name"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic list based on practice ID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public HashMap<String, String> retrievePatientPageList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrievePatientPageListQuery = QueryMaker.RETRIEVE_PATIENT_PAGE_LIST1;

			preparedStatement = connection.prepareStatement(retrievePatientPageListQuery);

			preparedStatement.setString(1, "Patient");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getString("jspPageName"), resultSet.getString("formName"));
				System.out.println("map key is::"+resultSet.getString("jspPageName"));
			}
			
			System.out.println("map size is::"+map.size());

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public List<String> retrieveOPDPageList() {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrieveOPDPageListQuery = QueryMaker.RETRIEVE_PATIENT_PAGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveOPDPageListQuery);

			preparedStatement.setString(1, "Patient");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getString("formName"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public JSONObject verifyUsernameExists(String username) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		int check = 0;

		try {

			connection = getConnection();

			String verifyUsernameExistsQuery = QueryMaker.VERIFY_USERNAME_EXISTS;

			preparedStatement = connection.prepareStatement(verifyUsernameExistsQuery);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				object.put("check", check);
				object.put("userID", resultSet.getInt("id"));
				object.put("username", resultSet.getString("username"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object.put("check", check);
				object.put("userID", 0);
				object.put("username", "");

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying username exists or not.");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public String retrieveUsernameByUserID(int userID) {

		String userName = "";

		try {

			connection1 = getConnection();

			String retrieveUsernameByUserIDQuery = QueryMaker.RETRIEVE_USERNAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveUsernameByUserIDQuery);

			preparedStatement1.setInt(1, userID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				userName = resultSet1.getString("username");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return userName;
	}

	public String retrieveEmployeeNameByID(int empID) {

		String empName = "";

		try {

			connection1 = getConnection();

			String retrieveEmployeeNameByIDQuery = QueryMaker.RETRIEVE_EMPLOYEE_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveEmployeeNameByIDQuery);

			preparedStatement1.setInt(1, empID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				if (resultSet1.getString("middleName") == null || resultSet1.getString("middleName") == "") {
					empName = resultSet1.getString("firstName") + " " + resultSet1.getString("firstName");
				} else {

					if (resultSet1.getString("middleName").isEmpty()) {
						empName = resultSet1.getString("firstName") + " " + resultSet1.getString("firstName");
					} else {
						empName = resultSet1.getString("firstName") + " " + resultSet1.getString("middleName") + " "
								+ resultSet1.getString("lastName");
					}

				}

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return empName;
	}

	public List<RegistrationForm> retreiveUserProfileDetails(int userID) {

		List<RegistrationForm> userList = new ArrayList<RegistrationForm>();
		RegistrationForm registrationForm = new RegistrationForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String retreiveUserDetailByUserIDQuery = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement = connection.prepareStatement(retreiveUserDetailByUserIDQuery);
			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				registrationForm.setUserID(resultSet.getInt("id"));
				registrationForm.setUsername(resultSet.getString("username"));
				String decryPass = EncDescUtil.DecryptText(resultSet.getString("password"));
				registrationForm.setPassword(decryPass);
				registrationForm.setUserType(resultSet.getString("userType"));
				registrationForm.setActivityStatus(resultSet.getString("activityStatus"));
				// registrationForm.setSpecialsation(resultSet.getString("specialization"));

				int clinicID = resultSet.getInt("defaultClinicID");
				int practiceID = resultSet.getInt("practiceID");

				registrationForm.setClinicID(clinicID);
				registrationForm.setClinicName(retrieveClinicNameByClinicID(clinicID));
				registrationForm.setPracticeID(practiceID);
				registrationForm.setPracticeName(retrievePracticeNameByPracticeID(practiceID));
				// registrationForm.setProfilePicDBName(resultSet.getString("profilePic"));
				registrationForm.setFirstName(resultSet.getString("firstName"));
				registrationForm.setMiddleName(resultSet.getString("middleName"));
				registrationForm.setLastName(resultSet.getString("lastName"));
				registrationForm.setAddress(resultSet.getString("address"));
				registrationForm.setCity(resultSet.getString("city"));
				registrationForm.setState(resultSet.getString("state"));
				registrationForm.setCountry(resultSet.getString("country"));
				registrationForm.setMobile(resultSet.getString("mobile"));
				registrationForm.setPhone(resultSet.getString("phone"));
				registrationForm.setEmailID(resultSet.getString("email"));
				
				registrationForm.setQualification(resultSet.getString("qualification"));
				
				String one = resultSet.getString("profilePic");
				
				if(one =="" || one ==null) {
					registrationForm.setProfilePicDBName("");
				}else if(one.isEmpty()) {
					registrationForm.setProfilePicDBName("");
				}else {
					String[] bits = one.split("/");
					String profPicName = bits[bits.length-1];
					registrationForm.setProfilePicDBName(profPicName);
				}
				
				String two = resultSet.getString("signImage");
				
				if(two =="" || two ==null) {
					registrationForm.setSignatureDBName("");
				}else if(two.isEmpty()) {
					registrationForm.setSignatureDBName("");
				}else {
					
					String[] bits1 = two.split("/");
					String signImgName = bits1[bits1.length-1];
					registrationForm.setSignatureDBName(signImgName);
				}
				
				if (resultSet.getString("pin") == null || resultSet.getString("pin") == "") {

					registrationForm.setPIN(resultSet.getString("pin"));

				} else {

					String decryPIN = EncDescUtil.DecryptText(resultSet.getString("pin"));
					registrationForm.setPIN(decryPIN);

				}
			}

			userList.add(registrationForm);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retreiving user detail based on user id from database due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return userList;
	}

	public List<String> retrieveStartHourListByClinicStartEndHour(int clinicID) {

		List<String> list = new ArrayList<String>();

		int startValue = 0;

		int endValue = 0;

		try {

			connection1 = getConnection();

			String retrieveStartHourListByClinicStartEndHourQuery = QueryMaker.RETRIEVE_START_END_HOUR_BY_CLINIC_TIME;

			preparedStatement1 = connection1.prepareStatement(retrieveStartHourListByClinicStartEndHourQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				startValue = resultSet1.getInt("startHour");
				endValue = resultSet1.getInt("endHour");
			}

			for (int i = startValue; endValue >= i; i++) {

				String hour = String.valueOf(i);

				if (hour.length() == 1) {
					hour = "0" + hour;
				}

				list.add(hour);
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

			System.out.println("Hour list retrieved succesfully..");
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public HashMap<String, String> retrieveReportPageList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveReportPageListQuery = QueryMaker.RETRIEVE_REPORT_PAGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveReportPageListQuery);

			preparedStatement.setString(1, "Report");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getString("jspPageName"), resultSet.getString("formName"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<Integer, String> getPracticeListForAdmin(int userID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getPracticeListQuery = QueryMaker.RETRIEVE_PRACTICE_LIST_For_Admin;

			preparedStatement = connection.prepareStatement(getPracticeListQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public int checkOpenLeaveRegister(int practiceID) {

		int check = 0;

		try {

			connection = getConnection();

			String checkOpenLeaveRegisterQuery = QueryMaker.VERIFY_OPEN_LEAVE_REGISTER;

			preparedStatement = connection.prepareStatement(checkOpenLeaveRegisterQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			
			StringWriter stringWriter = new StringWriter();

            exception.printStackTrace(new PrintWriter(stringWriter));

            // calling exception mail send method to send mail about the exception details
            // on info@kovidbioanalytics.com
            EmailUtil emailUtil = new EmailUtil();
            emailUtil.sendExceptionInfoEmail(stringWriter.toString(), "Check Open Leave Register: Exception");
            
			exception.printStackTrace();

			check = 0;
		}

		return check;
	}

	public HashMap<String, String> retrieveMDDetails(int mdDoctorID, String collectionDate) {

		HashMap<String, String> map = new HashMap<String, String>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveMDDetailsQuery = QueryMaker.RETRIEVE_PRACTICE_MD_DETAILS_By_ID;

			preparedStatement = connection.prepareStatement(retrieveMDDetailsQuery);

			preparedStatement.setInt(1, mdDoctorID);
			//preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(collectionDate)));

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				map.put("MDName", resultSet.getString("MDName"));
				map.put("MDQualification", resultSet.getString("MDQualification"));
				map.put("MDSignature", resultSet.getString("MDSignatureImage"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}
	
}
