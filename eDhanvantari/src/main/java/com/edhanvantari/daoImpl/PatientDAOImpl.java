package com.edhanvantari.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.omg.CORBA.Current;
import org.json.*;

import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.LoginStatus;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.EncDescUtil;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;

public class PatientDAOImpl extends DAOConnection implements PatientDAOInf {

	Connection connection = null;
	Connection connection1 = null;
	Connection connection2 = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	PreparedStatement preparedStatement5 = null;
	PreparedStatement preparedStatement6 = null;
	PreparedStatement preparedStatement7 = null;
	PreparedStatement preparedStatement8 = null;
	PreparedStatement preparedStatement9 = null;
	PreparedStatement preparedStatement10 = null;
	PreparedStatement preparedStatement11 = null;
	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	ResultSet resultSet2 = null;
	ResultSet resultSet3 = null;
	ResultSet resultSet4 = null;
	ResultSet resultSet5 = null;
	ResultSet resultSet6 = null;
	ResultSet resultSet7 = null;
	ResultSet resultSet8 = null;
	ResultSet resultSet9 = null;
	ResultSet resultSet10 = null;
	ResultSet resultSet11 = null;
	String status = "error";

	static int count = 1;
	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	RegistrationDAOinf registrationDAOinf = null;
	ConfigurationUtil configurationUtil = null;

	public String verifyUserCredentials(LoginForm form) {
		// System.out.println("inside verify patient..");
		try {

			if (retrieveLockedUser(form.getUsername()).equals(ActivityStatus.LOCKED)) {
				status = "locked";
				return status;
			} else {
				System.out.println("inside verify else..." + form.getUsername());
				ResultSet resultSet1 = null;

				connection = getConnection();

				String verifyUserCredentialsQuery = QueryMaker.RETRIEVE_PATIENT_CREDENTIALS;

				preparedStatement = connection.prepareStatement(verifyUserCredentialsQuery);
				preparedStatement.setString(1, form.getUsername());

				resultSet1 = preparedStatement.executeQuery();

				while (resultSet1.next()) {

					// decrypt the password from database and store it in one string variable

					String decryptedPassword = null;
					decryptedPassword = EncDescUtil.DecryptText(resultSet1.getString("password").trim());

					System.out.println("Decrypted password is::::::" + decryptedPassword);

					System.out.println("User credentials are:::::" + form.getUsername() + " " + form.getPassword());

					if ((form.getUsername().equals("") || form.getUsername().equals(null))
							&& (form.getPassword().equals("") || form.getPassword().equals(null))) {
						status = "input";
						return status;
					} else if (((resultSet1.getString("username")).equals(form.getUsername()))
							&& (decryptedPassword.equals(form.getPassword()))) {
						System.out.println("Credetials Matched.....");
						status = "success";
						return status;
					}
					if (((resultSet1.getString("username")).equals(form.getUsername()))
							&& (decryptedPassword != (form.getPassword()))) {

						// Retrieving patientID from Patient table

						registrationDAOinf = new RegistrationDAOImpl();
						int patientId = registrationDAOinf.retrievePatientIDByUsername(form.getUsername());

						// Setting patient id into session
						form.setPatientID(resultSet1.getInt("id"));
						String result = null;

						if (patientId != 0) {
							System.out.println("Patient id from Patient table for Plogin attempt ::: " + patientId);

							// Verifying whether username that entered by patient and username from
							// PloginAttempt table matches

							int patientIdForPLoginAttempt = retrievePatientIdFromPLoginAttempt(patientId);

							System.out.println("patientId from PLoginAttempt is ::" + patientIdForPLoginAttempt);

							if (patientId == patientIdForPLoginAttempt) {
								System.out.println("updating PLoginAttempt table");

								/*
								 * Retrieving date from PLoginAttempt. If current date is equal to PloginAttempt
								 * date then update the record in PloginAttempt table with incremented
								 * attemptCounter value . If date value from Ploginattempt table is not equal to
								 * current date or time then insert new value into PLoginattempt table with same
								 * PatientID. And If Ploginattempt date value matches current date and time
								 * value, then after 10 min of time interval update the counterAttempt value of
								 * that PatientID to 1
								 */

								String PloginAttemptDate = retrievePLoginAttemptTimestamp(patientId);

								String[] timeStampArray = PloginAttemptDate.split(" ");

								String date = timeStampArray[0];

								String time = timeStampArray[1];

								// Splitting time in order to get hour and minutes
								String[] timeArray = time.split(":");

								String hour = timeArray[0];

								String minute = timeArray[1];

								// Fetching current date and time
								Date currentDate = new Date();

								SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

								String currentDateTime = currentDateFormat.format(currentDate);

								// Splitting Current date and time value
								String currentTimeArray[] = currentDateTime.split(" ");

								String currDate = currentTimeArray[0];

								String currTimeArray[] = currentTimeArray[1].split(":");

								String currHour = currTimeArray[0];

								String currMin = currTimeArray[1];

								// Comparing current date time with Ploginattempt date time
								int timeCheck = date.compareTo(currDate);

								System.out.println("date compare check is :: " + timeCheck);

								// adding 10 to current minute in order to get interval of 10 minute
								if (timeCheck == 0 && hour.equals(currHour)
										&& Integer.parseInt(currMin) >= Integer.parseInt(minute)) {

									extraMinutes = 0;
									check = false;

									int tempMinute = Integer.parseInt(minute) + 10;

									System.out.println("Temp minute with 10 minute interval is :::: " + tempMinute);

									if (tempMinute > Integer.parseInt(currMin)) {

										if (tempMinute >= 60) {

											System.out.println("temp minute is greater than 60");

											check = true;

											// finding extra minutes by substracting 60 from tempMinute
											extraMinutes = tempMinute - 60;
										}

										// updating attempt counter value of patient by incrementing it by 1

										// Retrieving max login attempt value from ClinicConfiguration table based on
										// username

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) { // incrementing counter by 1
											counter++;
											System.out.println("counter value in if::: " + counter);

											// updating attemptCounter from LoginAttempt table

											updatePLoginAttempt(patientId, counter, PloginAttemptDate);

											status = "input";

										} else {
											System.out.println("Login attempt exceeded the limit.");
											System.out.println("counter value in else::: " + counter);

											counter = 1;

											/*
											 * Updating activity status of pLoginAttempt to Disable for that particular
											 * patientID
											 */
											updatePLoginAttemptStatus(patientId);

											/*
											 * Updating activityStatus of patient From patient table to Locked
											 */
											updatePatientStatus(patientId);
											System.out.println(
													"Patient is locked by updating his/her acticityStatus to Locked");

											status = "login";

										}
									} else {

										/*
										 * Setting static variables extraMinutes and check to its default value
										 */
										extraMinutes = 0;
										check = false;

										System.out.println("inserting new value with counter as 1");

										/*
										 * Updating activity status of PLoginAttempt to Disable for that particular
										 * patientID
										 */
										updatePLoginAttemptStatus(patientId);

										counter = 1;

										/*
										 * Inserting new record into LoginAttempt with counter value as 1
										 */
										insertPLoginAttempt(patientId, counter, form.getUsername().trim());

										status = "input";

									}
								} else if (check) {

									System.out.println("Inside check trueee");
									/*
									 * Inside this loop means, if current time is 12:55, so after adding 10 minute
									 * to it, the time becomes 13.05. If this is the case, then update Plogin
									 * attempt till the converted time exceeds
									 */
									if ((Integer.parseInt(currMin) > extraMinutes)) {

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) {
											// incrementing counter by 1
											counter++;
											System.out.println("counter value in if::: " + counter);

											/*
											 * updating attemptCounter from LoginAttempt table
											 */
											updatePLoginAttempt(patientId, counter, PloginAttemptDate);

											status = "input";

										} else {
											System.out.println("P Login attempt exceeded the limit.");
											System.out.println("counter value in else::: " + counter);

											counter = 1;

											/*
											 * Updating activity status of LoginAttempt to Disable for that particular
											 * userID
											 */
											updatePLoginAttemptStatus(patientId);

											/*
											 * Updating activityStatus of User From AppUser table to Locked
											 */
											updatePatientStatus(patientId);
											System.out.println(
													"patient is locked by updating his/her acticityStatus to Locked");

											status = "login";

										}
									} else if ((Integer.parseInt(currMin) <= extraMinutes)) {

										if ((Integer.parseInt(currMin) == extraMinutes)) {
											check = false;
										}

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) {
											// incrementing counter by 1
											counter++;
											System.out.println("counter value in if::: " + counter);

											/*
											 * updating attemptCounter from LoginAttempt table
											 */
											updatePLoginAttempt(patientId, counter, PloginAttemptDate);

											status = "input";

										} else {
											System.out.println("P Login attempt exceeded the limit.");
											System.out.println("counter value in else::: " + counter);

											counter = 1;

											/*
											 * Updating activity status of LoginAttempt to Disable for that particular
											 * userID
											 */
											updatePLoginAttemptStatus(patientId);

											/*
											 * Updating activityStatus of User From AppUser table to Locked
											 */
											updatePatientStatus(patientId);
											System.out.println(
													"patient is locked by updating his/her acticityStatus to Locked");

											status = "login";
										}
									} else {

										/*
										 * Setting static variables extraMinutes and check to its default value
										 */
										extraMinutes = 0;
										check = false;

										/*
										 * Updating activity status of PLoginAttempt to Disable for that particular
										 * patientID
										 */
										updatePLoginAttemptStatus(patientId);

										counter = 1;

										// inserting new value with same userID
										insertPLoginAttempt(patientId, counter, form.getUsername().trim());

										status = "input";

									}
								} else {

									/*
									 * Setting static variables extraMinutes and check to its default value
									 */
									extraMinutes = 0;
									check = false;

									/*
									 * Updating activity status of LoginAttempt to Disable for that particular
									 * userID
									 */
									updatePLoginAttemptStatus(patientId);

									counter = 1;

									// inserting new value with same userID
									insertPLoginAttempt(patientId, counter, form.getUsername().trim());

									status = "input";
								}
							} else {

								/*
								 * Setting static variables extraMinutes and check to its default value
								 */
								extraMinutes = 0;
								check = false;

								System.out.println("inserting into PLoginAttempt table");

								counter = 1;

								/*
								 * Updating activity status of LoginAttempt to Disable for that particular
								 * userID
								 */
								updatePLoginAttemptStatus(patientId);

								/*
								 * Inserting values into LoginAttempt table
								 */
								insertPLoginAttempt(patientId, counter, form.getUsername().trim());

								status = "input";
							}
						} else {
							status = "input";
						}

					} else {
						status = "input";
					}
				}

				// resultSet.close();
				// preparedStatement.close();
				// connection.close();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while checking user credentials while login due to:::" + exception.getMessage());
			status = "exception";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String insertPLoginAttempt(int patientId, int counter, String username) {
		String result = "error";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date today = new Date();

		try {
			connection = getConnection();

			String insertPLoginAttemptQuery = QueryMaker.INSERT_INTO_P_LOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(insertPLoginAttemptQuery);

			preparedStatement.setInt(1, counter);
			preparedStatement.setInt(2, patientId);
			preparedStatement.setString(3, sdf.format(today));
			preparedStatement.setString(4, ActivityStatus.ENABLE);

			preparedStatement.execute();

			result = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting into LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;
	}

	public String updatePatientStatus(int patientId) {
		String result = "error";
		try {
			connection = getConnection();

			String updatePatientStatusQuery = QueryMaker.UPDATE_PATIENT_STATUS;

			preparedStatement = connection.prepareStatement(updatePatientStatusQuery);
			preparedStatement.setString(1, ActivityStatus.LOCKED);
			preparedStatement.setInt(2, patientId);

			preparedStatement.executeUpdate();

			result = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating status to Locked from LoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return result;
	}

	public String updatePLoginAttemptStatus(int patientId) {

		String result = "error";
		try {
			connection = getConnection();

			String updatePLoginAttemptStatusQuery = QueryMaker.UPDATE_P_LOGIN_ATTEMPT_STATUS;

			preparedStatement = connection.prepareStatement(updatePLoginAttemptStatusQuery);

			preparedStatement.setString(1, ActivityStatus.DISABLE);
			preparedStatement.setInt(2, patientId);

			preparedStatement.executeUpdate();

			result = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();

			result = "error";
		}
		return result;
	}

	public String updatePLoginAttempt(int patientID, int counter, String PloginAttemptDate) {

		String result = "error";
		try {
			connection = getConnection();

			String updatePLoginAttemptQuery = QueryMaker.UPDATE_P_LOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(updatePLoginAttemptQuery);

			preparedStatement.setInt(1, counter);
			preparedStatement.setInt(2, patientID);
			preparedStatement.setString(3, PloginAttemptDate);

			preparedStatement.executeUpdate();

			result = "success";
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating PLoginAttempt table due to:::" + exception.getMessage());
		}
		return result;
	}

	public int retrieveInvalideAttempt(String username) {
		int attemptCount = 0;

		try {

			connection = getConnection();

			String retrieveInvalideAttemptQuery = QueryMaker.RETRIEVE_INVALIDATE_ATTEMPT_FROM_PATIENT;

			preparedStatement = connection.prepareStatement(retrieveInvalideAttemptQuery);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String count = resultSet.getString("invalidAttempts");

				if (count == null || count == "") {
					count = "5";
				}

				attemptCount = Integer.parseInt(count);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return attemptCount;
	}

	public String retrievePLoginAttemptTimestamp(int patientID) {
		String PloginAttemptDate = null;

		try {

			connection = getConnection();

			String retrievePLoginAttemptTimestampQuery = QueryMaker.RETRIEVE_P_LOGIN_ATTEMPT_DATE;

			preparedStatement = connection.prepareStatement(retrievePLoginAttemptTimestampQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				PloginAttemptDate = resultSet.getString("dateAndTime");
			}

			System.out.println(
					"Date and time from PLogin Attempt for patientID : " + patientID + " is :: " + PloginAttemptDate);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return PloginAttemptDate;
	}

	public int retrievePatientIdFromPLoginAttempt(int patientID) {
		int patientId = 0;
		try {
			connection = getConnection();

			String retrievepatientIdFromPLoginAttemptQuery = QueryMaker.RETRIEVE_PATIENT_ID_FROM_PLOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(retrievepatientIdFromPLoginAttemptQuery);

			preparedStatement.setInt(1, patientId);
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientId = resultSet.getInt("patientID");
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patientId from PLoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return patientId;
	}

	public String retrieveLockedUser(String username) {
		String userStatus = "dummy";
		try {
			connection = getConnection();

			String retrieveLockedUserQuery = QueryMaker.RETRIVE_LOCKED_PATIENT_STATUS;

			preparedStatement = connection.prepareStatement(retrieveLockedUserQuery);
			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userStatus = resultSet.getString("activityStatus");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying patient status as Locked from Patient table due to:::"
					+ exception.getMessage());
		}
		return userStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyPatientDetail(java.lang.
	 * String , java.lang.String)
	 */
	public String verifyPatientDetail(String patientFirstName, String patientLastName) {
		try {
			connection = getConnection();

			String verifyPatientDetailQuery = QueryMaker.VERIFY_PATIENT_DETAIL;

			preparedStatement = connection.prepareStatement(verifyPatientDetailQuery);

			preparedStatement.setString(1, patientFirstName);
			preparedStatement.setString(2, patientLastName);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = "success";
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient detail from table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String updateUsernameAndPasswordIntoPatientTableByID(String uname, String password, int patientID) {

		String encryptedPass = EncDescUtil.EncryptText(password);

		try {
			connection = getConnection();

			String updateUsernamAndPasswordQuery = QueryMaker.UPDATE_USERNAME_PASSWORD_LOGINSTATUS;

			preparedStatement = connection.prepareStatement(updateUsernamAndPasswordQuery);

			preparedStatement.setString(1, uname);
			preparedStatement.setString(2, encryptedPass);
			preparedStatement.setString(3, LoginStatus.ENABLE);
			preparedStatement.setInt(4, patientID);

			preparedStatement.execute();

			status = "success";
			System.out.println("username ,password and login status detail updated successfully into table Patient.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updateing username,password & loginstatus detail into table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertPatientDetails(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertPatientDetails(PatientForm patientForm, int practiceID) {
		/*
		 * ClinicID from Session
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertPatientDetailsQuery = QueryMaker.INSERT_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertPatientDetailsQuery);

			preparedStatement.setString(1, StringUtils.capitalize(patientForm.getFirstName()));
			preparedStatement.setString(2, StringUtils.capitalize(patientForm.getMiddleName()));
			preparedStatement.setString(3, StringUtils.capitalize(patientForm.getLastName()));
			preparedStatement.setString(4, patientForm.getGender());
			preparedStatement.setString(5, patientForm.getRhFactor());
			if (patientForm.getDateOfBirth() == null || patientForm.getDateOfBirth() == "") {
				preparedStatement.setString(6, null);
			} else {
				if (patientForm.getDateOfBirth().isEmpty()) {
					preparedStatement.setString(6, null);
				} else {
					preparedStatement.setString(6,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getDateOfBirth())));
				}
			}
			preparedStatement.setString(7, patientForm.getBloodGroup());
			if (patientForm.getAge().isEmpty()) {
				preparedStatement.setInt(8, 0);
			} else {
				preparedStatement.setString(8, patientForm.getAge());
			}

			preparedStatement.setString(9, patientForm.getMobile());
			preparedStatement.setString(10, patientForm.getEmailID());
			preparedStatement.setString(11, patientForm.getPhone());
			preparedStatement.setString(12, patientForm.getAddress());
			preparedStatement.setString(13, patientForm.getCity());
			preparedStatement.setString(14, patientForm.getState());
			preparedStatement.setString(15, patientForm.getCountry());
			preparedStatement.setInt(16, practiceID);
			preparedStatement.setString(17, patientForm.getOccupation());
			preparedStatement.setString(18, patientForm.getEC());
			preparedStatement.setString(19, patientForm.getRegistrationNo());
			preparedStatement.setString(20, ActivityStatus.ACTIVE);
			preparedStatement.setInt(21, patientForm.getRefDoctorID());

			preparedStatement.execute();

			status = "success";
			System.out.println("patient detail inserted successfully into table Patient.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertPatientDetails(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertPatientDetails_bk(PatientForm patientForm, int practiceID) {
		/*
		 * ClinicID from Session
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertPatientDetailsQuery = QueryMaker.INSERT_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertPatientDetailsQuery);

			preparedStatement.setString(1, StringUtils.capitalize(patientForm.getFirstName()));
			preparedStatement.setString(2, StringUtils.capitalize(patientForm.getMiddleName()));
			preparedStatement.setString(3, StringUtils.capitalize(patientForm.getLastName()));
			preparedStatement.setString(4, patientForm.getGender());
			preparedStatement.setString(5, patientForm.getRhFactor());
			if (patientForm.getDateOfBirth() == null || patientForm.getDateOfBirth() == "") {
				preparedStatement.setString(6, null);
			} else {
				if (patientForm.getDateOfBirth().isEmpty()) {
					preparedStatement.setString(6, null);
				} else {
					preparedStatement.setString(6,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getDateOfBirth())));
				}
			}
			preparedStatement.setString(7, patientForm.getBloodGroup());
			if (patientForm.getAge().isEmpty()) {
				preparedStatement.setInt(8, 0);
			} else {
				preparedStatement.setString(8, patientForm.getAge());
			}

			preparedStatement.setString(9, patientForm.getMobile());
			preparedStatement.setString(10, patientForm.getEmailID());
			preparedStatement.setString(11, patientForm.getPhone());
			preparedStatement.setString(12, patientForm.getAddress());
			preparedStatement.setString(13, patientForm.getCity());
			preparedStatement.setString(14, patientForm.getState());
			preparedStatement.setString(15, patientForm.getCountry());
			preparedStatement.setInt(16, practiceID);
			preparedStatement.setString(17, patientForm.getOccupation());
			preparedStatement.setString(18, patientForm.getEC());
			preparedStatement.setString(19, patientForm.getRegistrationNo());
			preparedStatement.setString(20, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("patient detail inserted successfully into table Patient.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#retrievePatientID(java.lang.String)
	 */
	public int retrievePatientID(String patientFirstName, String patientLastName, int practiceID) {
		int patientID = 0;
		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			preparedStatement.setString(1, patientFirstName.trim());
			preparedStatement.setString(2, patientLastName.trim());
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientID = resultSet.getInt("id");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient id from Patient table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return patientID;
	}

	public int retrievePatientID1(PatientForm form, int practiceID) {
		int patientID = 0;
		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			preparedStatement.setString(1, form.getFirstName().trim());
			preparedStatement.setString(2, form.getLastName().trim());
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientID = resultSet.getInt("id");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient id from Patient table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return patientID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertDemographicDetails(int,
	 * com.edhanvantari.form.PatientForm)
	 */
	public String insertContactInformation(int patientID, PatientForm patientForm) {
		try {
			connection = getConnection();

			String insertDemographicDetailsQuery = QueryMaker.INSERT_CONTACT_INFORMATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertDemographicDetailsQuery);

			preparedStatement.setString(1, patientForm.getAddress());
			preparedStatement.setString(2, patientForm.getCity());
			preparedStatement.setString(3, patientForm.getState());
			preparedStatement.setString(4, patientForm.getCountry());
			preparedStatement.setString(5, patientForm.getPhone());
			preparedStatement.setString(6, patientForm.getMobile());
			preparedStatement.setString(7, patientForm.getEmailID());
			preparedStatement.setInt(8, patientID);

			preparedStatement.execute();

			status = "success";
			System.out.println("contact information detail inserted successfully into table PatientContact.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting patient contact detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientList(int, int)
	 */
	public List<PatientForm> retrievePatientList(int practiceID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retrievePatientListQuery = QueryMaker.RETREIVE_PATIENT_LIST;

			preparedStatement = connection.prepareStatement(retrievePatientListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setRegistrationNo(resultSet.getString("practiceRegNumber"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving patient list from database due to:::" + exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrivePatientListByID(int)
	 */
	public List<PatientForm> retrivePatientListByID(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrivePatientListByIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_ID;
			String retrieveIdentificationByIDQuery = QueryMaker.RETREIVE_IDENTIFICATION_BY_ID;
			String retrieveEmergencyContactByIDQuery = QueryMaker.RETREIVE_EMERGENCY_CONTACT_LIST_BY_ID;
			String retrieveMedicalHistoryByIDQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;
			String retrieveReferredByDoctorQuery = QueryMaker.RETRIEVE_REFERRED_BY_ID;

			// For retrieving patient details base on patientID
			preparedStatement = connection.prepareStatement(retrivePatientListByIDQuery);
			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setGender(resultSet.getString("gender"));

				String DOB = resultSet.getString("dateOfBirth");

				if (DOB == null || DOB == "" || DOB.isEmpty()) {
					patientForm.setDobDate("");
					patientForm.setDobMonth("");
					patientForm.setDobYear("");

					patientForm.setDob(DOB);
				} else {

					patientForm.setDob(dateFormat.format(dateToBeFormatted.parse(DOB)));

					String[] dobArray = DOB.split("-");

					patientForm.setDobMonth(dobArray[1]);
					patientForm.setDobDate(dobArray[2]);
					patientForm.setDobYear(dobArray[0]);
				}

				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
				patientForm.setRhFactor(resultSet.getString("rhFactor"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setPhone(resultSet.getString("phone"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setCity(resultSet.getString("city"));
				patientForm.setState(resultSet.getString("state"));
				patientForm.setCountry(resultSet.getString("country"));
				patientForm.setPracticeID(resultSet.getInt("practiceID"));
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setOccupation(resultSet.getString("occupation"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}

				if (resultSet.getInt("referredBy") != 0) {
					preparedStatement5 = connection.prepareStatement(retrieveReferredByDoctorQuery);
					preparedStatement5.setInt(1, resultSet.getInt("referredBy"));

					resultSet5 = preparedStatement5.executeQuery();

					while (resultSet5.next()) {
						patientForm.setRefDoctor(resultSet5.getString("doctorName"));
					}
					resultSet5.close();
					preparedStatement5.close();
				}

				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));
				// patientForm.setIdentificationFileName(identificationFileName);
				patientForm.setProfilePicFileName(resultSet.getString("profilePic"));

			}

			// For retrieving identification detials based on patient iD
			preparedStatement3 = connection.prepareStatement(retrieveIdentificationByIDQuery);
			preparedStatement3.setInt(1, patientID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				patientForm.setAadhaarNo(resultSet3.getString("idNumber"));
				patientForm.setIdentificationFileName(resultSet3.getString("idDocumentPath"));
				patientForm.setDocumentType(resultSet3.getString("idDocument"));

			}

			// For retrieving emergency contact details based on patient ID
			preparedStatement2 = connection.prepareStatement(retrieveEmergencyContactByIDQuery);
			preparedStatement2.setInt(1, patientID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				patientForm.setEmFname(resultSet2.getString("firstName"));
				patientForm.setEmLname(resultSet2.getString("lastName"));
				patientForm.setEmMname(resultSet2.getString("middleName"));
				patientForm.setEmAdd(resultSet2.getString("address"));
				patientForm.setEmCity(resultSet2.getString("city"));
				patientForm.setEmState(resultSet2.getString("state"));
				patientForm.setEmCountry(resultSet2.getString("country"));
				patientForm.setEmPhone(resultSet2.getString("phone"));
				patientForm.setEmMobile(resultSet2.getString("mobile"));
				patientForm.setEmEmailID(resultSet2.getString("email"));
				patientForm.setEmRelation(resultSet2.getString("relationToPatient"));

			}

			// For retrieving medical history details based on patient ID

			preparedStatement4 = connection.prepareStatement(retrieveMedicalHistoryByIDQuery);
			preparedStatement4.setInt(1, patientID);

			resultSet4 = preparedStatement4.executeQuery();

			while (resultSet4.next()) {

				patientForm.setIsDiabetes(resultSet4.getString("isDiabetes"));
				patientForm.setAsthema(resultSet4.getString("asthema"));
				patientForm.setHypertension(resultSet4.getString("hypertension"));
				patientForm.setIschemicHeartDisease(resultSet4.getString("ischemicHeartDisease"));
				patientForm.setOtherDetails(resultSet4.getString("otherDetails"));

			}

			resultSet2.close();
			preparedStatement2.close();

			list.add(patientForm);

			resultSet3.close();
			preparedStatement3.close();

			resultSet.close();
			preparedStatement.close();

			resultSet4.close();
			preparedStatement4.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving patient list based on patient Id from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrivePatientListByID(int)
	 */
	public List<PatientForm> retrivePatientListByID_bk(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrivePatientListByIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_ID;
			String retrieveIdentificationByIDQuery = QueryMaker.RETREIVE_IDENTIFICATION_BY_ID;
			String retrieveEmergencyContactByIDQuery = QueryMaker.RETREIVE_EMERGENCY_CONTACT_LIST_BY_ID;

			// For retrieving patient details base on patientID
			preparedStatement = connection.prepareStatement(retrivePatientListByIDQuery);
			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setGender(resultSet.getString("gender"));

				String DOB = resultSet.getString("dateOfBirth");

				if (DOB == null || DOB == "" || DOB.isEmpty()) {
					patientForm.setDobDate("");
					patientForm.setDobMonth("");
					patientForm.setDobYear("");

					patientForm.setDob(DOB);
				} else {

					patientForm.setDob(dateFormat.format(dateToBeFormatted.parse(DOB)));

					String[] dobArray = DOB.split("-");

					patientForm.setDobMonth(dobArray[1]);
					patientForm.setDobDate(dobArray[2]);
					patientForm.setDobYear(dobArray[0]);
				}

				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
				patientForm.setRhFactor(resultSet.getString("rhFactor"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setPhone(resultSet.getString("phone"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setCity(resultSet.getString("city"));
				patientForm.setState(resultSet.getString("state"));
				patientForm.setCountry(resultSet.getString("country"));
				patientForm.setPracticeID(resultSet.getInt("practiceID"));
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setOccupation(resultSet.getString("occupation"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}

				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));
				// patientForm.setIdentificationFileName(identificationFileName);
				patientForm.setProfilePicFileName(resultSet.getString("profilePic"));

			}

			// For retrieving identification detials based on patient iD
			preparedStatement3 = connection.prepareStatement(retrieveIdentificationByIDQuery);
			preparedStatement3.setInt(1, patientID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				patientForm.setAadhaarNo(resultSet3.getString("idNumber"));
				patientForm.setIdentificationFileName(resultSet3.getString("idDocumentPath"));
				patientForm.setDocumentType(resultSet3.getString("idDocument"));

			}

			// For retrieving emergency contact details based on patient ID
			preparedStatement2 = connection.prepareStatement(retrieveEmergencyContactByIDQuery);
			preparedStatement2.setInt(1, patientID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				patientForm.setEmFname(resultSet2.getString("firstName"));
				patientForm.setEmLname(resultSet2.getString("lastName"));
				patientForm.setEmMname(resultSet2.getString("middleName"));
				patientForm.setEmAdd(resultSet2.getString("address"));
				patientForm.setEmCity(resultSet2.getString("city"));
				patientForm.setEmState(resultSet2.getString("state"));
				patientForm.setEmCountry(resultSet2.getString("country"));
				patientForm.setEmPhone(resultSet2.getString("phone"));
				patientForm.setEmMobile(resultSet2.getString("mobile"));
				patientForm.setEmEmailID(resultSet2.getString("email"));
				patientForm.setEmRelation(resultSet2.getString("relationToPatient"));

			}
			resultSet2.close();
			preparedStatement2.close();

			list.add(patientForm);

			resultSet3.close();
			preparedStatement3.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving patient list based on patient Id from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updatePatientDetail(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updatePatientDetail(PatientForm patientForm) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {
			connection = getConnection();

			String updatePatientDetailQuery = QueryMaker.UPDATE_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(updatePatientDetailQuery);

			preparedStatement.setString(1, StringUtils.capitalize(patientForm.getFirstName()));
			preparedStatement.setString(2, StringUtils.capitalize(patientForm.getMiddleName()));
			preparedStatement.setString(3, StringUtils.capitalize(patientForm.getLastName()));
			preparedStatement.setString(4, patientForm.getGender());
			preparedStatement.setString(5, patientForm.getRhFactor());
			if (patientForm.getDateOfBirth() == null || patientForm.getDateOfBirth() == "") {
				preparedStatement.setString(6, null);
			} else {
				if (patientForm.getDateOfBirth().isEmpty()) {
					preparedStatement.setString(6, null);
				} else {
					preparedStatement.setString(6,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getDateOfBirth())));
				}
			}
			preparedStatement.setString(7, patientForm.getBloodGroup());
			preparedStatement.setString(8, patientForm.getAge());
			preparedStatement.setString(9, patientForm.getMobile());
			preparedStatement.setString(10, patientForm.getEmailID());
			preparedStatement.setString(11, patientForm.getPhone());
			preparedStatement.setString(12, patientForm.getAddress());
			preparedStatement.setString(13, patientForm.getCity());
			preparedStatement.setString(14, patientForm.getState());
			preparedStatement.setString(15, patientForm.getCountry());
			preparedStatement.setInt(16, patientForm.getPracticeID());
			preparedStatement.setString(17, patientForm.getEC());
			preparedStatement.setString(18, patientForm.getOccupation());
			preparedStatement.setString(19, patientForm.getRegistrationNo());
			preparedStatement.setInt(20, patientForm.getRefDoctorID());
			preparedStatement.setInt(21, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("patient details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updatePatientDetail(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updatePatientDetail_bk(PatientForm patientForm) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		try {
			connection = getConnection();

			String verifyPatientDetailQuery = QueryMaker.UPDATE_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(verifyPatientDetailQuery);

			preparedStatement.setString(1, StringUtils.capitalize(patientForm.getFirstName()));
			preparedStatement.setString(2, StringUtils.capitalize(patientForm.getMiddleName()));
			preparedStatement.setString(3, StringUtils.capitalize(patientForm.getLastName()));
			preparedStatement.setString(4, patientForm.getGender());
			preparedStatement.setString(5, patientForm.getRhFactor());
			if (patientForm.getDateOfBirth() == null || patientForm.getDateOfBirth() == "") {
				preparedStatement.setString(6, null);
			} else {
				if (patientForm.getDateOfBirth().isEmpty()) {
					preparedStatement.setString(6, null);
				} else {
					preparedStatement.setString(6,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getDateOfBirth())));
				}
			}
			preparedStatement.setString(7, patientForm.getBloodGroup());
			preparedStatement.setString(8, patientForm.getAge());
			preparedStatement.setString(9, patientForm.getMobile());
			preparedStatement.setString(10, patientForm.getEmailID());
			preparedStatement.setString(11, patientForm.getPhone());
			preparedStatement.setString(12, patientForm.getAddress());
			preparedStatement.setString(13, patientForm.getCity());
			preparedStatement.setString(14, patientForm.getState());
			preparedStatement.setString(15, patientForm.getCountry());
			preparedStatement.setInt(16, patientForm.getPracticeID());
			preparedStatement.setString(17, patientForm.getEC());
			preparedStatement.setString(18, patientForm.getOccupation());
			preparedStatement.setString(19, patientForm.getRegistrationNo());
			preparedStatement.setInt(20, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("patient details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateContactInfoDetail(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String updateContactInfoDetail(PatientForm patientForm) {
		try {
			connection = getConnection();

			String updateDemographicsDetailQuery = QueryMaker.UPDATE_CONTACT_INFO_DETAILS;

			preparedStatement = connection.prepareStatement(updateDemographicsDetailQuery);

			preparedStatement.setString(1, patientForm.getAddress());
			preparedStatement.setString(2, patientForm.getCity());
			preparedStatement.setString(3, patientForm.getState());
			preparedStatement.setString(4, patientForm.getCountry());
			preparedStatement.setString(5, patientForm.getPhone());
			preparedStatement.setString(6, patientForm.getMobile());
			preparedStatement.setString(7, patientForm.getEmailID());
			preparedStatement.setInt(8, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("contact information details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating contact information detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyPatientCredential(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String verifyPatientCredential(PatientForm form) {
		try {
			connection = getConnection();

			String verifyPatientCredentialQuery = QueryMaker.VERFIY_PATIENT_CREDENTIALS;

			preparedStatement = connection.prepareStatement(verifyPatientCredentialQuery);
			preparedStatement.setInt(1, form.getPatientID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");

				if (firstName.equals(form.getFirstName()) && lastName.equals(form.getLastName())) {
					status = "success";
					return status;
				} else {
					status = "error";
					return status;
				}
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient detail from table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyNameDOB(com.edhanvantari.
	 * form.PatientForm)
	 */
	public boolean verifyNameDOB(PatientForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String verifyNameDOBQuery = QueryMaker.VERIFY_NAME_DOB;

			preparedStatement = connection.prepareStatement(verifyNameDOBQuery);
			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getMiddleName());
			preparedStatement.setString(3, form.getLastName());

			if (form.getDateOfBirth() == null || form.getDateOfBirth() == "") {
				preparedStatement.setString(4, null);
			} else if (form.getDateOfBirth().isEmpty()) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setString(4, dateFormat.format(dateFormat1.parse(form.getDateOfBirth())));
			}
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.setInt(6, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, mname, lname and dob from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyFnameLanameDOB(com.
	 * edhanvantari .form.PatientForm)
	 */
	public boolean verifyFnameLanameDOB(PatientForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String verifyFnameLanameDOBQuery = QueryMaker.VERIFY_FNAME_LNAME_DOB;

			preparedStatement = connection.prepareStatement(verifyFnameLanameDOBQuery);
			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getLastName());
			if (form.getDateOfBirth() == null || form.getDateOfBirth() == "") {
				preparedStatement.setString(3, null);
			} else if (form.getDateOfBirth().isEmpty()) {
				preparedStatement.setString(3, null);
			} else {
				preparedStatement.setString(3, dateFormat.format(dateFormat1.parse(form.getDateOfBirth())));
			}
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, lname and dob from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyFnameMnameLname(com.
	 * edhanvantari .form.PatientForm)
	 */
	public boolean verifyFnameMnameLname(PatientForm form) {
		try {
			connection = getConnection();

			String verifyFnameMnameLnameQuery = QueryMaker.VERIFY_FNAME_MNAME_LNAME;

			preparedStatement = connection.prepareStatement(verifyFnameMnameLnameQuery);
			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getMiddleName());
			preparedStatement.setString(3, form.getLastName());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, mname, lname from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyFnameLname(com.edhanvantari
	 * .form.PatientForm)
	 */
	public boolean verifyFnameLname(PatientForm form) {
		try {
			connection = getConnection();

			String verifyFnameLnameQuery = QueryMaker.VERIFY_FNAME_LNAME;

			preparedStatement = connection.prepareStatement(verifyFnameLnameQuery);
			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getLastName());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying patient fname, lname from table Patient due to:::"
					+ exception.getMessage());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientNameDOBList()
	 */
	public List<PatientForm> retrievePatientNameDOBList(PatientForm form) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String retrievePatientNameDOBListQuery = QueryMaker.RETREIVE_PATIENT_NAME_DOB_LIST;

			preparedStatement = connection.prepareStatement(retrievePatientNameDOBListQuery);

			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getMiddleName());
			preparedStatement.setString(3, form.getLastName());
			if (form.getDateOfBirth() == null || form.getDateOfBirth() == "") {
				preparedStatement.setString(4, null);
			} else if (form.getDateOfBirth().isEmpty()) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setString(4, dateFormat.format(dateFormat1.parse(form.getDateOfBirth())));
			}
			preparedStatement.setString(5, form.getFirstName());
			preparedStatement.setString(6, form.getLastName());
			if (form.getDateOfBirth() == null || form.getDateOfBirth() == "") {
				preparedStatement.setString(7, null);
			} else if (form.getDateOfBirth().isEmpty()) {
				preparedStatement.setString(7, null);
			} else {
				preparedStatement.setString(7, dateFormat.format(dateFormat1.parse(form.getDateOfBirth())));
			}
			preparedStatement.setString(8, form.getFirstName());
			preparedStatement.setString(9, form.getMiddleName());
			preparedStatement.setString(10, form.getLastName());
			preparedStatement.setString(11, form.getFirstName());
			preparedStatement.setString(12, form.getLastName());
			preparedStatement.setString(13, form.getFirstName());
			preparedStatement.setString(14, form.getLastName());
			preparedStatement.setString(15, form.getAge());
			preparedStatement.setString(16, form.getFirstName());
			preparedStatement.setString(17, form.getLastName());
			preparedStatement.setString(18, form.getGender());
			preparedStatement.setString(19, form.getFirstName());
			preparedStatement.setString(20, form.getLastName());
			preparedStatement.setString(21, form.getAge());
			preparedStatement.setString(22, form.getGender());
			preparedStatement.setInt(23, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth(resultSet.getString("dateOfBirth"));
				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					patientForm.setDateOfBirth(resultSet.getString("dateOfBirth"));
				} else {
					patientForm
							.setDateOfBirth(dateFormat1.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
				}
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving patient firstname middlename lastname dob list from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertEmergencyContact(int,
	 * com.edhanvantari.form.PatientForm)
	 */
	public String insertEmergencyContact(int patientID, PatientForm patientForm) {
		try {
			connection = getConnection();

			String insertEmergencyContactQuery = QueryMaker.INSERT_EMERGENCY_CONTACT_INFORMATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertEmergencyContactQuery);

			preparedStatement.setString(1, patientForm.getEmFname());
			preparedStatement.setString(2, patientForm.getEmMname());
			preparedStatement.setString(3, patientForm.getEmLname());
			preparedStatement.setString(4, patientForm.getEmAdd());
			preparedStatement.setString(5, patientForm.getEmCity());
			preparedStatement.setString(6, patientForm.getEmState());
			preparedStatement.setString(7, patientForm.getEmCountry());
			preparedStatement.setString(8, patientForm.getEmPhone());
			preparedStatement.setString(9, patientForm.getEmMobile());
			preparedStatement.setString(10, patientForm.getEmEmailID());
			preparedStatement.setInt(11, patientID);
			preparedStatement.setString(12, patientForm.getEmRelation());

			preparedStatement.execute();

			status = "success";
			System.out.println("emergency contact detail inserted successfully into table EmergenycContact.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting EmergencyContact detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateEmergencyContact(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updateEmergencyContact(PatientForm patientForm) {
		try {
			connection = getConnection();

			String updateEmergencyContactQuery = QueryMaker.UPDATE_EMERGENCY_CONTACT_DETAILS;

			preparedStatement = connection.prepareStatement(updateEmergencyContactQuery);

			preparedStatement.setString(1, patientForm.getEmFname());
			preparedStatement.setString(2, patientForm.getEmMname());
			preparedStatement.setString(3, patientForm.getEmLname());
			preparedStatement.setString(4, patientForm.getEmAdd());
			preparedStatement.setString(5, patientForm.getEmCity());
			preparedStatement.setString(6, patientForm.getEmState());
			preparedStatement.setString(7, patientForm.getEmCountry());
			preparedStatement.setString(8, patientForm.getEmPhone());
			preparedStatement.setString(9, patientForm.getEmMobile());
			preparedStatement.setString(10, patientForm.getEmEmailID());
			preparedStatement.setString(11, patientForm.getEmRelation());
			preparedStatement.setInt(12, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Emergency contact information details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating emergency contact information detail into table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertIdentification(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertIdentification(PatientForm patientForm, int patientID) {
		try {
			connection = getConnection();

			String insertIdentificationQuery = QueryMaker.INSERT_IDENTIFICATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertIdentificationQuery);

			preparedStatement.setString(1, ActivityStatus.ID_DOCUMENT);
			preparedStatement.setString(2, patientForm.getAadhaarNo());
			preparedStatement.setInt(3, patientID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Identification details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting identification detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateIdentification(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updateIdentification(PatientForm patientForm) {
		try {
			connection = getConnection();

			String updateIdentificationQuery = QueryMaker.UPDATE_IDENTIFICATION_DETAILS;

			preparedStatement = connection.prepareStatement(updateIdentificationQuery);

			preparedStatement.setString(1, patientForm.getAadhaarNo());
			preparedStatement.setInt(2, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Identification information details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating identification detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public int retrieveLastVolunteerID(int clinicID) {
		int volunteerID = 0;

		try {
			connection = getConnection();

			String retrieveLastVolunteerIDQuery = QueryMaker.RETRIEVE_LAST_VOLUNTEER_ID;

			preparedStatement = connection.prepareStatement(retrieveLastVolunteerIDQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				volunteerID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return volunteerID;

	}

	public String insertPatientSurvey(PatientForm patientForm, int clinicID) {

		try {
			connection = getConnection();

			String insertPatientSurveyQuery = QueryMaker.INSERT_SURVEY_DETAILS;

			preparedStatement = connection.prepareStatement(insertPatientSurveyQuery);

			preparedStatement.setString(1, patientForm.getFirstName());
			preparedStatement.setString(2, patientForm.getMiddleName());
			preparedStatement.setString(3, patientForm.getLastName());
			preparedStatement.setString(4, patientForm.getMobile());
			preparedStatement.setString(5, patientForm.getEmailID());
			preparedStatement.setString(6, patientForm.getDob());
			preparedStatement.setString(7, patientForm.getAge());
			preparedStatement.setString(8, patientForm.getGender());
			preparedStatement.setString(9, patientForm.getAddress());
			preparedStatement.setString(10, patientForm.getTravelOutside());
			preparedStatement.setString(11, patientForm.getAdmitted());
			preparedStatement.setString(12, patientForm.getSuffer_from());
			preparedStatement.setString(13, patientForm.getSurgeries());
			preparedStatement.setString(14, patientForm.getDiabOrHypertension());
			preparedStatement.setString(15, patientForm.getAwareness());
			preparedStatement.setString(16, patientForm.getKnowFamily());
			preparedStatement.setString(17, patientForm.getPsychological_issue());
			preparedStatement.setString(18, patientForm.getTraining());
			preparedStatement.setString(19, patientForm.getTested_positive());
			preparedStatement.setString(20, patientForm.getEmergencyContact_name());
			preparedStatement.setString(21, patientForm.getEmergencyContact_relation());
			preparedStatement.setString(22, patientForm.getEmergencyContact_mobile());
			preparedStatement.setString(23, patientForm.getIDproof());
			preparedStatement.setInt(24, clinicID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Survey details inserted successfully.");

			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#insertPatientVisit(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertPatientVisit(PatientForm patientForm, int visitNumber) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date date1 = new Date();

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");

		try {

			/*
			 * Checking whether appointment ID is 0 and process accordingly
			 */
			if (patientForm.getAptID() == 0) {

				connection = getConnection();

				String insertPatientVisitQuery = QueryMaker.INSERT_VISIT_DETAILS;

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				preparedStatement.setString(1, "OPD");
				preparedStatement.setInt(2, visitNumber);
				preparedStatement.setString(3, patientForm.getVisitType());
				preparedStatement.setString(4, dateFormat.format(date));
				preparedStatement.setString(5, null);
				preparedStatement.setString(6, null);
				preparedStatement.setString(7, patientForm.getDiagnosis());
				preparedStatement.setString(8, patientForm.getMedicalNotes());
				preparedStatement.setString(9, ActivityStatus.ACTIVE);
				preparedStatement.setInt(10, patientForm.getPatientID());
				preparedStatement.setInt(11, 0);
				preparedStatement.setInt(12, patientForm.getNextVisitDays());
				preparedStatement.setInt(13, 0);

				preparedStatement.execute();

				status = "success";
				System.out.println("visit details inserted successfully.");

				preparedStatement.close();
				connection.close();

			} else {

				/*
				 * Retrieve appointment visit time to and from.
				 */
				String timeValues = retrieveValueFromAppointment(patientForm.getAptID());

				/*
				 * Splitting timeValues string in order to get ToTime and FromTime
				 */
				String[] timeArray = timeValues.split("=");

				String apptTimeFrom = timeArray[0];

				String apptTimeTo = timeArray[1];

				connection = getConnection();

				String insertPatientVisitQuery = QueryMaker.INSERT_VISIT_DETAILS;

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				preparedStatement.setString(1, "OPD");
				preparedStatement.setInt(2, visitNumber);
				preparedStatement.setString(3, patientForm.getVisitType());
				preparedStatement.setString(4, dateFormat.format(date));
				preparedStatement.setString(5, apptTimeFrom);
				preparedStatement.setString(6, apptTimeTo);
				preparedStatement.setString(7, patientForm.getDiagnosis());
				preparedStatement.setString(8, patientForm.getMedicalNotes());
				preparedStatement.setString(9, ActivityStatus.ACTIVE);
				preparedStatement.setInt(10, patientForm.getPatientID());
				preparedStatement.setInt(11, 0);
				preparedStatement.setInt(12, patientForm.getNextVisitDays());
				preparedStatement.setInt(13, patientForm.getAptID());

				preparedStatement.execute();

				status = "success";
				System.out.println("visit details inserted successfully.");

				preparedStatement.close();
				connection.close();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#insertPatientVisit(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef, String nextVisitDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		String dischargDate = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			if (patientForm.getDateOfDischarge() == null || patientForm.getDateOfDischarge() == "") {
				// Converting date into db format
				dischargDate = null;
			} else if (patientForm.getDateOfDischarge().isEmpty()) {
				// Converting date into db format
				dischargDate = null;
			} else {
				// Converting date into db format
				dischargDate = dateFormat.format(dateFormat1.parse(patientForm.getDateOfDischarge()));
			}

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setInt(2, patientForm.getVisitTypeID());
			preparedStatement.setString(3, date1);
			preparedStatement.setString(4, patientForm.getVisitFromTimeHH());
			preparedStatement.setString(5, patientForm.getVisitToTimeHH());
			preparedStatement.setString(6, patientForm.getCancerType());
			preparedStatement.setString(7, patientForm.getMedicalNotes());
			preparedStatement.setString(8, ActivityStatus.ACTIVE);
			preparedStatement.setInt(9, patientForm.getPatientID());
			preparedStatement.setInt(10, newVisitRef);
			preparedStatement.setInt(11, patientForm.getNextVisitDays());

			if (patientForm.getAptID() == 0) {
				preparedStatement.setInt(12, 0);
			} else {
				preparedStatement.setInt(12, patientForm.getAptID());
			}

			preparedStatement.setString(13, nextVisitDate);

			preparedStatement.setInt(14, patientForm.getClinicID());
			preparedStatement.setString(15, patientForm.getOnExamination());
			preparedStatement.setInt(16, patientForm.getClinicianID());
			preparedStatement.setString(17, patientForm.getSystemicHistory());
			preparedStatement.setString(18, patientForm.getOccularHistory());
			preparedStatement.setString(19, patientForm.getPersonalHistory());

			if (patientForm.getComplainingOf() == "") {
				preparedStatement.setString(20, "");
			} else {
				preparedStatement.setString(20, patientForm.getComplainingOf());
			} // Change

			preparedStatement.setString(21, dischargDate);
			preparedStatement.setString(22, patientForm.getProcedure());
			preparedStatement.setString(23, patientForm.getAdmission_time());
			preparedStatement.setString(24, patientForm.getDischarge_time());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveVisitIDbyPatientID(int)
	 */
	public int retrieveVisitIDbyPatientID(int patientID) {
		int visitID = 0;
		try {
			connection = getConnection();

			String retrieveVisitIDbyPatientIDQuery = QueryMaker.RETRIEVE_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitIDbyPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				visitID = resultSet.getInt("id");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving visit id from Visit table due to:::" + exception.getMessage());
			status = "error";
		}
		return visitID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertPrescriptionDetails(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String insertPrescriptionDetails(PatientForm patientForm, int visitID) {
		try {
			connection = getConnection();

			String insertPrescriptionDetailsQuery = QueryMaker.INSERT_PRESCRIPTION_DETAIL;

			preparedStatement = connection.prepareStatement(insertPrescriptionDetailsQuery);

			preparedStatement.setString(1, patientForm.getDrugName());
			preparedStatement.setString(2, patientForm.getDose());
			preparedStatement.setString(3, patientForm.getDoseUnit());
			preparedStatement.setString(4, patientForm.getNoOfDays());
			preparedStatement.setString(5, patientForm.getFrequency());
			preparedStatement.setString(6, patientForm.getComment());
			preparedStatement.setString(7, ActivityStatus.ACTIVE);
			preparedStatement.setInt(8, visitID);

			preparedStatement.execute();

			status = "success";
			System.out.println("prescription detail inserted successfully into table Prescription.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting prescription detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePrescriptionList()
	 */
	public List<PatientForm> retrievePrescriptionList(int patientID, int visitID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrievePrescriptionListQuery = QueryMaker.RETREIVE_PRESCRIPTION_LIST_New;

			preparedStatement = connection.prepareStatement(retrievePrescriptionListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				// patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setTradeName(resultSet.getString("tradeName"));
				// patientForm.setDosage(resultSet.getDouble("dosage"));
				patientForm.setNoOfDays(resultSet.getString("numberOfDays"));
				patientForm.setFrequency(resultSet.getString("frequency"));
				patientForm.setComment(resultSet.getString("comment"));
				patientForm.setProductQuantity(resultSet.getDouble("quantity"));
				patientForm.setPatientID(patientID);
				patientForm.setVisitID(visitID);
				patientForm.setCount("" + count + "");
				patientForm.setCategory(resultSet.getString("category"));
				patientForm.setBillingCategoryID(resultSet.getInt("categoryID"));

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("Presc list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving prescription list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public List<PatientForm> retrieveAllPatientPrescriptionList(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrievePrescriptionListQuery = QueryMaker.RETREIVE_ALL_PATIENT_PRESCRIPTION_LIST_New;

			preparedStatement = connection.prepareStatement(retrievePrescriptionListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, patientID);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				// patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setTradeName(resultSet.getString("tradeName"));
				// patientForm.setDosage(resultSet.getDouble("dosage"));
				patientForm.setNoOfDays(resultSet.getString("numberOfDays"));
				patientForm.setFrequency(resultSet.getString("frequency"));
				patientForm.setComment(resultSet.getString("comment"));
				patientForm.setProductQuantity(resultSet.getDouble("quantity"));
				patientForm.setPatientID(patientID);
				patientForm.setVisitID(resultSet.getInt("visitID"));
				patientForm.setCount("" + count + "");
				patientForm.setCategory(resultSet.getString("category"));
				patientForm.setBillingCategoryID(resultSet.getInt("categoryID"));
				patientForm.setVisitDate(resultSet.getString("visitDate"));

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("Presc list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving prescription list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePrescriptionListByID(int)
	 */
	public List<PatientForm> retrievePrescriptionListByID(int prescID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		try {

			connection = getConnection();

			String retrievePrescriptionListByIDQuery = QueryMaker.RETREIVE_PRESCRIPTION_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePrescriptionListByIDQuery);
			preparedStatement.setInt(1, prescID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setDose(resultSet.getString("dose"));
				patientForm.setDoseUnit(resultSet.getString("doseUnit"));
				patientForm.setNoOfDays(resultSet.getString("numberOfDays"));
				patientForm.setFrequency(resultSet.getString("frequency"));
				patientForm.setComment(resultSet.getString("comment"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving prescription list based on prescriptionID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updatePrescriptionDetails(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String updatePrescriptionDetails(PatientForm patientForm) {
		try {
			connection = getConnection();

			String updatePrescriptionDetailsQuery = QueryMaker.UPDATE_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(updatePrescriptionDetailsQuery);

			preparedStatement.setString(1, patientForm.getDrugName());
			preparedStatement.setString(2, patientForm.getDose());
			preparedStatement.setString(3, patientForm.getDoseUnit());
			preparedStatement.setString(4, patientForm.getNoOfDays());
			preparedStatement.setString(5, patientForm.getFrequency());
			preparedStatement.setString(6, patientForm.getComment());
			preparedStatement.setInt(7, patientForm.getPrescriptionID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("prescription details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating prescription detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deletePrescriptionDetails(int)
	 */
	public String deletePrescriptionDetails(int prescID) {
		try {
			connection = getConnection();

			String deletePrescriptionDetailsQuery = QueryMaker.DELETE_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(deletePrescriptionDetailsQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, prescID);

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("prescription details deleted (updated activityStatus to Inactive) successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting prescription detail (updating activityStatus to Inactive) into table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertBillDetails(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertBillDetails(PatientForm patientForm, int visitID, int userID) {
		try {
			connection = getConnection();
			System.out.println("test bill added");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String insertBillDetailsQuery = QueryMaker.INSERT_BILL_DETAIL;

			preparedStatement = connection.prepareStatement(insertBillDetailsQuery);

			preparedStatement.setString(1, patientForm.getReceiptNo());

			if (patientForm.getReceiptDate() == null || patientForm.getReceiptDate() == "") {
				preparedStatement.setString(2, null);
			} else {

				if (patientForm.getReceiptDate().isEmpty()) {
					preparedStatement.setString(2, null);
				} else {
					preparedStatement.setString(2,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getReceiptDate())));
				}
			}
			System.out.println("net AMount >>>> " + patientForm.getCharges());
			preparedStatement.setDouble(3, patientForm.getTotalBill());

			if (patientForm.getNetAmount() > 0D) {
				preparedStatement.setDouble(4, patientForm.getNetAmount());
			} else {
				preparedStatement.setDouble(4, patientForm.getTotalBill());
			}
			preparedStatement.setInt(5, visitID);
			preparedStatement.setDouble(6, patientForm.getAdvPayment());
			preparedStatement.setDouble(7, patientForm.getBalPayment());
			preparedStatement.setString(8, patientForm.getPaymentType());
			preparedStatement.setInt(9, visitID);
			preparedStatement.setInt(10, userID);
			preparedStatement.setDouble(11, patientForm.getTotalDiscount());
			preparedStatement.setString(12, ActivityStatus.ACTIVE);
			preparedStatement.setDouble(13, patientForm.getCharges());
			preparedStatement.setDouble(14, patientForm.getProductRate());
			preparedStatement.setString(15, patientForm.getProductName());
			preparedStatement.setInt(16, patientForm.getProductID());
			preparedStatement.setString(17, patientForm.getDiscountType());

			preparedStatement.execute();
			System.out.println(preparedStatement);
			status = "success";
			System.out.println("bill detail inserted successfully into table Billing.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting bill detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertBillDetails(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertBillDetails_bk(PatientForm patientForm, int visitID, int userID) {
		try {
			connection = getConnection();
			System.out.println("test bill added");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String insertBillDetailsQuery = QueryMaker.INSERT_BILL_DETAIL;

			preparedStatement = connection.prepareStatement(insertBillDetailsQuery);

			preparedStatement.setString(1, patientForm.getReceiptNo());

			if (patientForm.getReceiptDate() == null || patientForm.getReceiptDate() == "") {
				preparedStatement.setString(2, null);
			} else {

				if (patientForm.getReceiptDate().isEmpty()) {
					preparedStatement.setString(2, null);
				} else {
					preparedStatement.setString(2,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getReceiptDate())));
				}
			}
			System.out.println("net AMount >>>> " + patientForm.getCharges());
			preparedStatement.setDouble(3, patientForm.getTotalBill());
			preparedStatement.setDouble(4, patientForm.getTotalBill());
			preparedStatement.setInt(5, visitID);
			preparedStatement.setDouble(6, patientForm.getAdvPayment());
			preparedStatement.setDouble(7, patientForm.getBalPayment());
			preparedStatement.setString(8, patientForm.getPaymentType());
			preparedStatement.setInt(9, visitID);
			preparedStatement.setInt(10, userID);
			preparedStatement.setDouble(11, patientForm.getTotalDiscount());
			preparedStatement.setString(12, ActivityStatus.ACTIVE);
			preparedStatement.setDouble(13, patientForm.getCharges());

			preparedStatement.execute();
			System.out.println(preparedStatement);
			status = "success";
			System.out.println("bill detail inserted successfully into table Billing.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting bill detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveBillList(int)
	 */
	public List<PatientForm> retrieveBillList(int patientID, int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retrieveBillListQuery = QueryMaker.RETREIVE_BILL_LIST;

			preparedStatement = connection.prepareStatement(retrieveBillListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setBillID(resultSet.getInt("id"));
				patientForm.setDescription(resultSet.getString("chargeType"));
				patientForm.setCharges(resultSet.getDouble("charge"));
				patientForm.setVisitID(resultSet.getInt("visitID"));
				patientForm.setPatientID(patientID);
				patientForm.setRate(resultSet.getDouble("rate"));
				patientForm.setTotalBill(resultSet.getDouble("totalBill"));
				patientForm.setVisitID(visitID);

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving billing list from database due to:::" + exception.getMessage());
			status = "error";

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveExistingVisitList(int,
	 * int)
	 */
	public List<PatientForm> retrieveExistingVisitList(int patientID, int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		double eyewearTotal = 0D;

		try {
			connection = getConnection();

			String retrieveExistingVisitListQuery = QueryMaker.RETRIEVE_EXISTING_VISIT_LIST;
			String retrieveExistingPrescriptionListQuery = QueryMaker.RETRIEVE_EXISTING_PRESCRIPTION_LIST;
			String retrievePatientDetailQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS;
			String retrieveExistingBillingListQuery = QueryMaker.RETRIEVE_EXISTING_BILLING_LIST;
			String retriveOPDVisitQuery = QueryMaker.RETRIEVE_OPD_VISIT_DETAILS;
			String retrieveOpticianQuery = QueryMaker.RETRIEVE_OPTICIAN_DETAILS;
			String retrieveCycloplegicRefractionQuery = QueryMaker.RETRIEVE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_VISIT_ID;
			String retrieveOldGLassesQuery = QueryMaker.RETRIEVE_OPTICIAN_OLD_GLASSES_DETAILS;
			String retrieveEyewearQuery = QueryMaker.RETRIEVE_EYEWEAR_DETAILS;
			String retrieveMedicatCertificateQuery = QueryMaker.RETRIEVE_MEDICAL_CERTIFICATE;

			/*
			 * Fetching opticianID from VisitID
			 */
			patientForm.setOpticinID(retrieveOpticianID(visitID));

			System.out.println("Optician ID while editing ::: " + patientForm.getOpticinID());

			/*
			 * For retrieving optician
			 */
			preparedStatement6 = connection.prepareStatement(retrieveOpticianQuery);

			preparedStatement6.setInt(1, patientForm.getOpticinID());

			resultSet6 = preparedStatement6.executeQuery();

			while (resultSet6.next()) {
				patientForm.setSphDiskOD(resultSet6.getString("sphDistOD"));
				patientForm.setSphDiskOS(resultSet6.getString("sphDistOS"));
				patientForm.setSphNearOD(resultSet6.getString("sphNearOD"));
				patientForm.setSphNearOS(resultSet6.getString("sphNearOS"));
				patientForm.setCylDiskOD(resultSet6.getString("cylDistOD"));
				patientForm.setCylDiskOS(resultSet6.getString("cylDistOS"));
				patientForm.setCylNearOD(resultSet6.getString("cylNearOD"));
				patientForm.setCylNearOS(resultSet6.getString("cylNearOS"));
				patientForm.setAxisDiskOD(resultSet6.getString("axisDistOD"));
				patientForm.setAxisDiskOS(resultSet6.getString("axisDistOS"));
				patientForm.setAxisNearOD(resultSet6.getString("axisNearOD"));
				patientForm.setAxisNearOS(resultSet6.getString("axisNearOS"));
				patientForm.setVnDiskOD(resultSet6.getString("vnDistOD"));
				patientForm.setVnDiskOS(resultSet6.getString("vnDistOS"));
				patientForm.setVnNearOD(resultSet6.getString("vnNearOD"));
				patientForm.setVnNearOS(resultSet6.getString("vnNearOS"));
				patientForm.setOptomeryComment(resultSet6.getString("comments"));
				patientForm.setSpectacleComments(resultSet6.getString("spectacleComments"));

				if (resultSet6.getString("spectacleComments") == null) {
					patientForm.setDefaultSpectacleComments(null);
				} else {
					patientForm.setDefaultSpectacleComments(resultSet6.getString("spectacleComments").split(", "));
				}

			}

			resultSet6.close();
			preparedStatement6.close();

			/*
			 * Retrieving Cycloplegic Refraction details
			 */
			preparedStatement6 = connection.prepareStatement(retrieveCycloplegicRefractionQuery);

			preparedStatement6.setInt(1, visitID);

			resultSet6 = preparedStatement6.executeQuery();

			while (resultSet6.next()) {
				patientForm.setDistCTCOD(resultSet6.getString("distCTCOD"));
				patientForm.setDistHTCOD(resultSet6.getString("distHTCOD"));
				patientForm.setDistAtropineOD(resultSet6.getString("distAtropineOD"));
				patientForm.setDistTPlusOD(resultSet6.getString("distTPlusOD"));
				patientForm.setDistCTCOS(resultSet6.getString("distCTCOS"));
				patientForm.setDistHTCOS(resultSet6.getString("distHTCOS"));
				patientForm.setDistAtropineOS(resultSet6.getString("distAtropineOS"));
				patientForm.setDistTPlusOS(resultSet6.getString("distTPlusOS"));
				patientForm.setNearCTCOD(resultSet6.getString("nearCTCOD"));
				patientForm.setNearHTCOD(resultSet6.getString("nearHTCOD"));
				patientForm.setNearAtropineOD(resultSet6.getString("nearAtropineOD"));
				patientForm.setNearTPlusOD(resultSet6.getString("nearTPlusOD"));
				patientForm.setNearCTCOS(resultSet6.getString("nearCTCOS"));
				patientForm.setNearHTCOS(resultSet6.getString("nearHTCOS"));
				patientForm.setNearAtropineOS(resultSet6.getString("nearAtropineOS"));
				patientForm.setNearTPlusOS(resultSet6.getString("nearTPlusOS"));

				if (resultSet6.getString("cycloplegicRefractionData") == null) {
					patientForm.setDefaultCycloplegicRefraction(null);
				} else {
					patientForm.setDefaultCycloplegicRefraction(
							resultSet6.getString("cycloplegicRefractionData").split(", "));
				}

				patientForm.setCycloplegicRefractionID(resultSet6.getInt("id"));
			}

			/*
			 * For retrieving Patient Details
			 */
			preparedStatement3 = connection.prepareStatement(retrievePatientDetailQuery);
			preparedStatement3.setInt(1, patientID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {
				patientForm.setPatientID(patientID);
				patientForm.setFirstName(resultSet3.getString("firstName"));
				patientForm.setLastName(resultSet3.getString("lastName"));
				patientForm.setMiddleName(resultSet3.getString("middleName"));
				patientForm.setGender(resultSet3.getString("gender"));
				patientForm.setAge(resultSet3.getString("age"));
				patientForm.setPatientID(resultSet3.getInt("id"));
				patientForm.setMobile(resultSet3.getString("mobile"));
			}

			/*
			 * For retrieving existing visit list
			 */
			preparedStatement = connection.prepareStatement(retrieveExistingVisitListQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				date = databaseDate.parse(resultSet.getString("visitDate"));

				// patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(dateToBeDisplayed.format(date));

				/*
				 * Declaring variable for time to and from
				 */
				if (resultSet.getString("visitTimeFrom") == null || resultSet.getString("visitTimeFrom") == "") {
					patientForm.setVisitFromTime("");
				} else if (resultSet.getString("visitTimeFrom").isEmpty()) {
					patientForm.setVisitFromTime("");
				} else {
					patientForm.setVisitFromTime(resultSet.getString("visitTimeFrom"));
				}

				if (resultSet.getString("visitTimeTo") == null || resultSet.getString("visitTimeTo") == "") {
					patientForm.setVisitToTime("");
				} else if (resultSet.getString("visitTimeTo").isEmpty()) {
					patientForm.setVisitToTime("");
				} else {
					patientForm.setVisitToTime(resultSet.getString("visitTimeTo"));
				}
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setVisitID(resultSet.getInt("id"));
				// patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

			}

			/*
			 * For retrieving existing prescription list
			 */
			preparedStatement1 = connection.prepareStatement(retrieveExistingPrescriptionListQuery);

			preparedStatement1.setInt(1, visitID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, patientForm.getIsCompStr());

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientForm.setPrescriptionID(resultSet1.getInt("id"));
				patientForm.setDrugName(resultSet1.getString("drugName"));
				patientForm.setDose(resultSet1.getString("dose"));
				patientForm.setTradeName(resultSet1.getString("tradeName"));
				patientForm.setNoOfDays(resultSet1.getString("numberOfDays"));
				patientForm.setFrequency(resultSet1.getString("frequency"));
				patientForm.setComment(resultSet1.getString("comment"));
			}

			/*
			 * For retrieving existing billing list
			 */
			preparedStatement2 = connection.prepareStatement(retrieveExistingBillingListQuery);

			preparedStatement2.setInt(1, visitID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {
				patientForm.setDescription(resultSet2.getString("chargeType"));
				patientForm.setCharges(resultSet2.getDouble("charge"));
				patientForm.setBillID(resultSet2.getInt("id"));
				patientForm.setRate(resultSet2.getDouble("rate"));
				patientForm.setTotalBill(resultSet2.getDouble("totalBill"));
			}

			/*
			 * For retrieving OPD Visit details
			 */
			preparedStatement5 = connection.prepareStatement(retriveOPDVisitQuery);
			preparedStatement5.setInt(1, visitID);

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {

				patientForm.setEyeLidLowerOD(resultSet5.getString("eyelidLowerOD"));
				patientForm.setEyeLidLowerOS(resultSet5.getString("eyelidLowerOS"));
				patientForm.setEyeLidUpperOD(resultSet5.getString("eyelidUpperOD"));
				patientForm.setEyeLidUpperOS(resultSet5.getString("eyelidUpperOS"));
				patientForm.setVisualAcuityDistOD(resultSet5.getString("visualAcuityDistOD"));
				patientForm.setVisualAcuityDistOS(resultSet5.getString("visualAcuityDistOS"));
				patientForm.setVisualAcuityNearOD(resultSet5.getString("visualAcuityNearOD"));
				patientForm.setVisualAcuityNearOS(resultSet5.getString("visualAcuityNearOS"));
				patientForm.setPinholeVisionDistOD(resultSet5.getString("pinholeVisionDistOD"));
				patientForm.setPinholeVisionDistOS(resultSet5.getString("pinholeVisionDistOS"));
				patientForm.setPinholeVisionNearOD(resultSet5.getString("pinholeVisionNearOD"));
				patientForm.setPinholeVisionNearOS(resultSet5.getString("pinholeVisionNearOS"));
				patientForm.setBCVADistOD(resultSet5.getString("bcvaDistOD"));
				patientForm.setBCVADistOS(resultSet5.getString("bcvaDistOS"));
				patientForm.setBCVANearOD(resultSet5.getString("bcvaNearOD"));
				patientForm.setBCVANearOS(resultSet5.getString("bcvaNearOS"));
				patientForm.setConjunctivaOD(resultSet5.getString("conjunctivaOD"));
				patientForm.setConjunctivaOS(resultSet5.getString("conjunctivaOS"));
				patientForm.setPupilOD(resultSet5.getString("pupilOD"));
				patientForm.setPupilOS(resultSet5.getString("pupilOS"));
				patientForm.setCorneaOD(resultSet5.getString("corneaOD"));
				patientForm.setCorneaOS(resultSet5.getString("corneaOS"));
				patientForm.setACOD(resultSet5.getString("anteriorChamberOD"));
				patientForm.setACOS(resultSet5.getString("anteriorChamberOS"));
				patientForm.setIrisOD(resultSet5.getString("irisOD"));
				patientForm.setIrisOS(resultSet5.getString("irisOS"));
				patientForm.setLensOD(resultSet5.getString("lensOD"));
				patientForm.setLensOS(resultSet5.getString("lensOS"));
				patientForm.setDiscOD(resultSet5.getString("discOD"));
				patientForm.setDiscOS(resultSet5.getString("discOS"));
				patientForm.setVesselOD(resultSet5.getString("vesselOD"));
				patientForm.setVesselOS(resultSet5.getString("vesselOS"));
				patientForm.setMaculaOD(resultSet5.getString("maculaOD"));
				patientForm.setMaculaOS(resultSet5.getString("maculaOS"));
				patientForm.setIODOD(resultSet5.getString("iopOD"));
				patientForm.setIODOS(resultSet5.getString("iopOS"));
				patientForm.setSacOD(resultSet5.getString("sacOD"));
				patientForm.setSacOS(resultSet5.getString("sacOS"));
				patientForm.setK1OD(resultSet5.getString("biometryK1OD"));
				patientForm.setK1OS(resultSet5.getString("biometryK1OS"));
				patientForm.setK2OD(resultSet5.getString("biometryK2OD"));
				patientForm.setK2OS(resultSet5.getString("biometryK2OS"));
				patientForm.setAxialLengthOD(resultSet5.getString("biometryAxialLengthOD"));
				patientForm.setAxialLengthOS(resultSet5.getString("biometryAxialLengthOS"));
				patientForm.setIOLOD(resultSet5.getString("biometryIOLOD"));
				patientForm.setIOLOS(resultSet5.getString("biometryIOLOS"));
			}

			/*
			 * For retrieving Old glasses
			 */
			preparedStatement7 = connection.prepareStatement(retrieveOldGLassesQuery);

			preparedStatement7.setInt(1, patientForm.getOpticinID());

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {
				patientForm.setOldSphDiskOD(resultSet7.getString("sphDistOD"));
				patientForm.setOldSphDiskOS(resultSet7.getString("sphDistOS"));
				patientForm.setOldSphNearOD(resultSet7.getString("sphNearOD"));
				patientForm.setOldSphNearOS(resultSet7.getString("sphNearOS"));
				patientForm.setOldCylDiskOD(resultSet7.getString("cylDistOD"));
				patientForm.setOldCylDiskOS(resultSet7.getString("cylDistOS"));
				patientForm.setOldCylNearOD(resultSet7.getString("cylNearOD"));
				patientForm.setOldCylNearOS(resultSet7.getString("cylNearOS"));
				patientForm.setOldAxisDiskOD(resultSet7.getString("axisDistOD"));
				patientForm.setOldAxisDiskOS(resultSet7.getString("axisDistOS"));
				patientForm.setOldAxisNearOD(resultSet7.getString("axisNearOD"));
				patientForm.setOldAxisNearOS(resultSet7.getString("axisNearOS"));
				patientForm.setOldVnDiskOD(resultSet7.getString("vnDistOD"));
				patientForm.setOldVnDiskOS(resultSet7.getString("vnDistOS"));
				patientForm.setOldVnNearOD(resultSet7.getString("vnNearOD"));
				patientForm.setOldVnNearOS(resultSet7.getString("vnNearOS"));
			}

			/*
			 * For retrieving Old glasses
			 */
			preparedStatement8 = connection.prepareStatement(retrieveEyewearQuery);

			preparedStatement8.setInt(1, patientForm.getOpticinID());

			resultSet8 = preparedStatement8.executeQuery();

			while (resultSet8.next()) {
				patientForm.setTint(resultSet8.getString("tint"));
				patientForm.setGlass(resultSet8.getString("glass"));
				patientForm.setMaterial(resultSet8.getString("material"));
				patientForm.setClinic(resultSet8.getString("clinic"));
				patientForm.setFrame(resultSet8.getString("frame"));
				patientForm.setGlassCharge(resultSet8.getDouble("glassCharges"));
				patientForm.setFrameCharge(resultSet8.getDouble("frameCharges"));
				patientForm.setDiscount(resultSet8.getDouble("discount"));
				patientForm.setNetPayment(resultSet8.getDouble("netPayment"));
				patientForm.setAdvance(resultSet8.getDouble("advance"));
				patientForm.setBalance(resultSet8.getDouble("balance"));
				patientForm.setBalancePaid(resultSet8.getDouble("balPaid"));

				/*
				 * Converting balancePaidDate from database format into dd-mm-yyyy
				 */
				date = databaseDate.parse(resultSet8.getString("balPaidDate"));

				patientForm.setBalancePaidDate(dateToBeDisplayed.format(date));
				patientForm.setOpticinID(resultSet8.getInt("optometryID"));

				/*
				 * Calculating eyewear total.
				 */
				eyewearTotal = resultSet8.getDouble("glassCharges") + resultSet8.getDouble("frameCharges");
				patientForm.setTotal(eyewearTotal);
			}

			/*
			 * Retrieving medical certificate text
			 */
			preparedStatement9 = connection.prepareStatement(retrieveMedicatCertificateQuery);
			preparedStatement9.setInt(1, visitID);

			resultSet9 = preparedStatement9.executeQuery();

			while (resultSet9.next()) {

				patientForm.setMedicalCerti(resultSet9.getString("medicalCertificate"));
			}

			list.add(patientForm);

			resultSet2.close();
			preparedStatement2.close();

			resultSet1.close();
			preparedStatement1.close();

			resultSet.close();
			preparedStatement.close();

			resultSet3.close();
			preparedStatement3.close();

			resultSet4.close();
			preparedStatement4.close();

			resultSet5.close();
			preparedStatement5.close();

			resultSet7.close();
			preparedStatement7.close();

			resultSet8.close();
			preparedStatement8.close();

			resultSet9.close();
			preparedStatement9.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving existing visit list based on patient Id from database due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateVisit(com.edhanvantari.form
	 * .PatientForm)
	 */
	public String updateVisit(PatientForm patientForm) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		try {
			connection = getConnection();

			String updateVisitQuery = QueryMaker.UPDATE_VISIT_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			// Converting date into db format
			date = dateToBeParsed123.parse(patientForm.getVisitDate());

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				preparedStatement.setString(1, null);
			} else if (patientForm.getVisitDate().isEmpty()) {
				preparedStatement.setString(1, null);
			} else {
				preparedStatement.setString(1,
						dateToBeFormatted.format(dateToBeParsed123.parse(patientForm.getVisitDate())));
			}

			preparedStatement.setString(2, patientForm.getVisitFromTimeHH());
			preparedStatement.setString(3, patientForm.getVisitToTimeHH());
			preparedStatement.setString(4, patientForm.getCancerType());
			if (patientForm.getTemplate() == "" || patientForm.getTemplate() == null) {
				System.out.println("get medical note ::::: ");
				preparedStatement.setString(5, patientForm.getMedicalNotes());
			} else {
				System.out.println("get template ::::: ");
				preparedStatement.setString(5, patientForm.getTemplate());
			}

			preparedStatement.setString(6, patientForm.getOnExamination());
			preparedStatement.setInt(7, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated Visit details into Visit table.");

			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating visit details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateBill(com.edhanvantari.form
	 * .PatientForm)
	 */
	public String updateBill(PatientForm patientForm) {
		try {
			connection = getConnection();
			// System.out.println("Net Amount ::: fgajdgf :::" +
			// patientForm.getTotalBill());
			String updateVisitQuery = QueryMaker.UPDATE_BILLING_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			preparedStatement.setDouble(1, patientForm.getTotalBill());

			if (patientForm.getNetAmount() > 0D) {
				preparedStatement.setDouble(2, patientForm.getNetAmount());
			} else {
				preparedStatement.setDouble(2, patientForm.getTotalBill());
			}

			preparedStatement.setString(3, patientForm.getBillingType());
			preparedStatement.setDouble(4, patientForm.getAdvPayment());
			preparedStatement.setDouble(5, patientForm.getBalPayment());
			preparedStatement.setString(6, patientForm.getPaymentType());
			preparedStatement.setDouble(7, patientForm.getTotalDiscount());
			preparedStatement.setDouble(8, patientForm.getCharges());
			preparedStatement.setInt(9, patientForm.getProductID());
			preparedStatement.setString(10, patientForm.getProductName());
			preparedStatement.setDouble(11, patientForm.getProductRate());
			preparedStatement.setString(12, patientForm.getDiscountType());
			preparedStatement.setInt(13, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated bill details into Receipt table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating billing details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateBill(com.edhanvantari.form
	 * .PatientForm)
	 */
	public String updateBill_bk(PatientForm patientForm) {
		try {
			connection = getConnection();
			System.out.println("Net Amount ::: fgajdgf :::" + patientForm.getTotalBill());
			String updateVisitQuery = QueryMaker.UPDATE_BILLING_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			preparedStatement.setDouble(1, patientForm.getTotalBill());
			preparedStatement.setDouble(2, patientForm.getTotalBill());
			preparedStatement.setString(3, patientForm.getBillingType());
			preparedStatement.setDouble(4, patientForm.getAdvPayment());
			preparedStatement.setDouble(5, patientForm.getBalPayment());
			preparedStatement.setString(6, patientForm.getPaymentType());
			preparedStatement.setDouble(7, patientForm.getTotalDiscount());
			preparedStatement.setDouble(8, patientForm.getCharges());
			preparedStatement.setInt(9, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated bill details into Receipt table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating billing details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastEnteredPatient(int)
	 */
	public List<PatientForm> retrieveLastEnteredVisitDetail(int patientID) {
		List<PatientForm> lastEnetredVisitList = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		double eyewearTotal = 0D;

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		try {
			connection = getConnection();

			String retrieveLastEnteredVisitDetailQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS;
			String retrivePatientDetailQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS;
			String retriveOPDVisitQuery = QueryMaker.RETRIEVE_OPD_VISIT_DETAILS;
			String retrieveEyewearQuery = QueryMaker.RETRIEVE_EYEWEAR_DETAILS;

			/*
			 * Retrieving patient details from Patient table
			 */
			preparedStatement1 = connection.prepareStatement(retrivePatientDetailQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				patientForm.setFirstName(resultSet1.getString("firstName"));
				patientForm.setLastName(resultSet1.getString("lastName"));
				patientForm.setMiddleName(resultSet1.getString("middleName"));
				patientForm.setGender(resultSet1.getString("gender"));
				patientForm.setAge(resultSet1.getString("age"));
				patientForm.setPatientID(resultSet1.getInt("id"));
				patientForm.setMobile(resultSet1.getString("mobile"));

			}

			/*
			 * Retieving Last enetered visit details From Visit tables.
			 */
			preparedStatement = connection.prepareStatement(retrieveLastEnteredVisitDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// date = databaseDate.parse(resultSet.getString("visitDate"));
				if (resultSet.getString("visitDate") == "" || resultSet.getString("visitDate") == null) {
					patientForm.setVisitDate("");
				} else if (resultSet.getString("visitDate").isEmpty()) {
					patientForm.setVisitDate("");
				} else {
					patientForm.setVisitDate(dateToBeDisplayed.format(resultSet.getDate("visitDate")));
				}

				patientForm.setVisitID(resultSet.getInt("id"));
				// patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				// patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(dateToBeDisplayed.format(date));

				/*
				 * Declaring variable for time to and from
				 */
				if (resultSet.getString("visitTimeFrom") == null || resultSet.getString("visitTimeFrom") == "") {
					patientForm.setVisitFromTime("");
				} else if (resultSet.getString("visitTimeFrom").isEmpty()) {
					patientForm.setVisitFromTime("");
				} else {
					patientForm.setVisitFromTime(resultSet.getString("visitTimeFrom"));
				}

				if (resultSet.getString("visitTimeTo") == null || resultSet.getString("visitTimeTo") == "") {
					patientForm.setVisitToTime("");
				} else if (resultSet.getString("visitTimeTo").isEmpty()) {
					patientForm.setVisitToTime("");
				} else {
					patientForm.setVisitToTime(resultSet.getString("visitTimeTo"));
				}
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
				if (resultSet.getString("complainingOf") == "") {
					patientForm.setComplainingOf("");
				} else {
					patientForm.setComplainingOf(resultSet.getString("complainingOf"));
				}
			}
			System.out.println(
					"Visit ID while retrieving last enetered OPD visit details is ::: " + patientForm.getVisitID());

			/*
			 * Retrieving last enetered OPD Visit details from OphthalmologyOPD table
			 */
			preparedStatement2 = connection.prepareStatement(retriveOPDVisitQuery);
			preparedStatement2.setInt(1, patientForm.getVisitID());

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				patientForm.setEyeLidLowerOD(resultSet2.getString("eyelidLowerOD"));
				patientForm.setEyeLidLowerOS(resultSet2.getString("eyelidLowerOS"));
				patientForm.setEyeLidUpperOD(resultSet2.getString("eyelidUpperOD"));
				patientForm.setEyeLidUpperOS(resultSet2.getString("eyelidUpperOS"));
				patientForm.setVisualAcuityDistOD(resultSet2.getString("visualAcuityDistOD"));
				patientForm.setVisualAcuityDistOS(resultSet2.getString("visualAcuityDistOS"));
				patientForm.setVisualAcuityNearOD(resultSet2.getString("visualAcuityNearOD"));
				patientForm.setVisualAcuityNearOS(resultSet2.getString("visualAcuityNearOS"));
				patientForm.setPinholeVisionDistOD(resultSet2.getString("pinholeVisionDistOD"));
				patientForm.setPinholeVisionDistOS(resultSet2.getString("pinholeVisionDistOS"));
				patientForm.setPinholeVisionNearOD(resultSet2.getString("pinholeVisionNearOD"));
				patientForm.setPinholeVisionNearOS(resultSet2.getString("pinholeVisionNearOS"));
				patientForm.setBCVADistOD(resultSet2.getString("bcvaDistOD"));
				patientForm.setBCVADistOS(resultSet2.getString("bcvaDistOS"));
				patientForm.setBCVANearOD(resultSet2.getString("bcvaNearOD"));
				patientForm.setBCVANearOS(resultSet2.getString("bcvaNearOS"));
				patientForm.setConjunctivaOD(resultSet2.getString("conjunctivaOD"));
				patientForm.setConjunctivaOS(resultSet2.getString("conjunctivaOS"));
				patientForm.setCorneaOD(resultSet2.getString("corneaOD"));
				patientForm.setCorneaOS(resultSet2.getString("corneaOS"));
				patientForm.setPupilOS(resultSet2.getString("pupilOD"));
				patientForm.setPupilOS(resultSet2.getString("pupilOS"));
				patientForm.setACOD(resultSet2.getString("anteriorChamberOD"));
				patientForm.setACOS(resultSet2.getString("anteriorChamberOS"));
				patientForm.setIrisOD(resultSet2.getString("irisOD"));
				patientForm.setIrisOS(resultSet2.getString("irisOS"));
				patientForm.setLensOD(resultSet2.getString("lensOD"));
				patientForm.setLensOS(resultSet2.getString("lensOS"));
				patientForm.setDiscOD(resultSet2.getString("discOD"));
				patientForm.setDiscOS(resultSet2.getString("discOS"));
				patientForm.setVesselOD(resultSet2.getString("vesselOD"));
				patientForm.setVesselOS(resultSet2.getString("vesselOS"));
				patientForm.setMaculaOD(resultSet2.getString("maculaOD"));
				patientForm.setMaculaOS(resultSet2.getString("maculaOS"));
				patientForm.setIODOD(resultSet2.getString("iopOD"));
				patientForm.setIODOS(resultSet2.getString("iopOS"));
				patientForm.setSacOD(resultSet2.getString("sacOD"));
				patientForm.setSacOS(resultSet2.getString("sacOS"));
				patientForm.setK1OD(resultSet2.getString("biometryK1OD"));
				patientForm.setK1OS(resultSet2.getString("biometryK1OS"));
				patientForm.setK2OD(resultSet2.getString("biometryK2OD"));
				patientForm.setK2OS(resultSet2.getString("biometryK2OS"));
				patientForm.setAxialLengthOD(resultSet2.getString("biometryAxialLengthOD"));
				patientForm.setAxialLengthOS(resultSet2.getString("biometryAxialLengthOS"));
				patientForm.setIOLOD(resultSet2.getString("biometryIOLOD"));
				patientForm.setIOLOS(resultSet2.getString("biometryIOLOS"));
				patientForm.setLeftEyeHistory(resultSet2.getString("leftEyeHistory")); // New Entry 1
				patientForm.setLeftEyeDuration(resultSet2.getString("leftEyeDuration")); // New Entry 2
				patientForm.setRightEyeHistory(resultSet2.getString("rightEyeDuration")); // New Entry 3
				patientForm.setRightEyeDuration(resultSet2.getString("rightEyeHistory")); // New Entry 4
			}

			/*
			 * Retrieving optician ID from Visit ID
			 */
			patientForm.setOpticinID(retrieveOpticianID(patientForm.getVisitID()));
			System.out.println("Optician ID while retrieving all details ::: " + patientForm.getOpticinID());

			/*
			 * Retrieving last entered Eyewear details
			 */
			preparedStatement3 = connection.prepareStatement(retrieveEyewearQuery);
			preparedStatement3.setInt(1, patientForm.getOpticinID());

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {
				patientForm.setTint(resultSet3.getString("tint"));
				patientForm.setGlass(resultSet3.getString("glass"));
				patientForm.setMaterial(resultSet3.getString("material"));
				patientForm.setClinic(resultSet3.getString("clinic"));
				patientForm.setFrame(resultSet3.getString("frame"));
				patientForm.setGlassCharge(resultSet3.getDouble("glassCharges"));
				patientForm.setFrameCharge(resultSet3.getDouble("frameCharges"));
				patientForm.setDiscount(resultSet3.getDouble("discount"));
				patientForm.setNetPayment(resultSet3.getDouble("netPayment"));
				patientForm.setAdvance(resultSet3.getDouble("advance"));
				patientForm.setBalance(resultSet3.getDouble("balance"));
				patientForm.setBalancePaid(resultSet3.getDouble("balPaid"));
				patientForm.setBalancePaidDate(resultSet3.getString("balPaidDate"));
				patientForm.setOpticinID(resultSet3.getInt("optometryID"));

				/*
				 * Calculating eyewear total.
				 */
				eyewearTotal = resultSet3.getDouble("glassCharges") + resultSet3.getDouble("frameCharges");
				patientForm.setTotal(eyewearTotal);
			}

			lastEnetredVisitList.add(patientForm);
			System.out.println("Successfully retieved last enetred visit details");

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last enetred Visit details table due to:::"
					+ exception.getMessage());
		}
		return lastEnetredVisitList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#retrieveLastEnteredVisitDetail(int,
	 * int)
	 */
	public List<PatientForm> retrieveLastEnteredVisitDetail(int patientID, int lastVisitID, int clinicID,
			String visitType) {
		List<PatientForm> lastEnetredVisitList = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		double eyewearTotal = 0D;

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		try {
			connection = getConnection();

			int id_value = 0;
			String retrieveLastEnteredVisitDetailQuery = "";

			if (lastVisitID == 0) {
				retrieveLastEnteredVisitDetailQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS;
				id_value = patientID;
			} else {
				retrieveLastEnteredVisitDetailQuery = QueryMaker.GET_VISIT_DATA;
				id_value = lastVisitID;
			}

			String retrivePatientDetailQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS;
			String retriveOPDVisitQuery = QueryMaker.RETRIEVE_OPD_VISIT_DETAILS;
			String retrieveEyewearQuery = QueryMaker.RETRIEVE_EYEWEAR_DETAILS;
			String retrieveOpticianQuery = QueryMaker.RETRIEVE_OPTICIAN_DETAILS;
			String retrieveCycloplegicRefractionQuery = QueryMaker.RETRIEVE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_VISIT_ID;
			String retrieveOpticianOldGlassesQuery = QueryMaker.RETRIEVE_OPTICIAN_OLD_GLASSES_DETAILS;
			String retrieveDiagnosisQuery = QueryMaker.RETRIEVE_DIAGNOSIS_BY_VISIT_ID;

			/*
			 * Retrieving patient details from Patient table
			 */
			preparedStatement1 = connection.prepareStatement(retrivePatientDetailQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				patientForm.setPatientID(resultSet1.getInt("id"));
				patientForm.setFirstName(resultSet1.getString("firstName"));
				patientForm.setLastName(resultSet1.getString("lastName"));
				patientForm.setMiddleName(resultSet1.getString("middleName"));
				patientForm.setAge(resultSet1.getString("age"));
				patientForm.setGender(resultSet1.getString("gender"));
				patientForm.setMobile(resultSet1.getString("mobile"));
				patientForm.setAddress(resultSet1.getString("address"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet1.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet1.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet1.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet1.getString("dateOfBirth") == null || resultSet1.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet1.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeDisplayed.format(databaseDate.parse(resultSet1.getString("dateOfBirth"))));

					}
				}

				patientForm.setOccupation(resultSet1.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet1.getString("practiceRegNumber"));

				patientForm.setEC(resultSet1.getString("ec"));
				patientForm.setEmailID(resultSet1.getString("email"));

				if (resultSet1.getString("email") == null || resultSet1.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet1.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setFirstVisitDate(dateToBeDisplayed.format(new Date()));
			}

			/*
			 * Retieving Last enetered visit details From Visit tables.
			 */
			preparedStatement = connection.prepareStatement(retrieveLastEnteredVisitDetailQuery);

			preparedStatement.setInt(1, id_value);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// date = databaseDate.parse(resultSet.getString("visitDate"));
				if (resultSet.getString("visitDate") == "" || resultSet.getString("visitDate") == null) {
					patientForm.setVisitDate("");
				} else if (resultSet.getString("visitDate").isEmpty()) {
					patientForm.setVisitDate("");
				} else {
					patientForm.setVisitDate(dateToBeDisplayed.format(resultSet.getDate("visitDate")));
				}

				patientForm.setVisitID(resultSet.getInt("id"));
				// patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				// patientForm.setVisitType(resultSet.getString("visitType"));
				// patientForm.setVisitDate(dateToBeDisplayed.format(date));

				/*
				 * Splitting visit Time from and time to
				 */
				patientForm.setVisitFromTimeHH(resultSet.getString("visitTimeFrom"));
				patientForm.setVisitToTimeHH(resultSet.getString("visitTimeTo"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setAdvice(resultSet.getString("advice"));
				patientForm.setTemplate(resultSet.getString("visitNote"));
				patientForm.setReferredBy(resultSet.getString("referredBy"));
				patientForm.setClinicianID(resultSet.getInt("clinicianID"));
				patientForm.setReportID(resultSet.getInt("templateID"));
				patientForm.setSystemicHistory(resultSet.getString("systemicHistory"));
				patientForm.setOccularHistory(resultSet.getString("occularHistory"));
				patientForm.setPersonalHistory(resultSet.getString("personalHistory"));
				patientForm.setDilationStartTime(resultSet.getString("dilationStartTime"));
				patientForm.setDilationEndTime(resultSet.getString("dilationEndTime"));
				patientForm.setDilationDuration(resultSet.getString("dilationDuration"));
				if (resultSet.getString("complainingOf") == "") {
					patientForm.setComplainingOf("");
				} else {
					patientForm.setComplainingOf(resultSet.getString("complainingOf"));
				}
//				patientForm.setComplainingOf(resultSet.getString("complainingOf"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = databaseDate.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeDisplayed.format(date));
				}

				if (resultSet.getString("estimatedDueDate") == null || resultSet.getString("estimatedDueDate") == "") {
					patientForm.setEstimatedDueDate("");
					patientForm.setLastMenstrualPeriod("");
					patientForm.setNameOfGuardian("");
					patientForm.setWeekOfPregnancy(0);
				} else if (resultSet.getString("estimatedDueDate").isEmpty()) {
					patientForm.setEstimatedDueDate("");
					patientForm.setLastMenstrualPeriod("");
					patientForm.setNameOfGuardian("");
					patientForm.setWeekOfPregnancy(0);
				} else {
					String test = dateToBeDisplayed.format(resultSet.getDate("estimatedDueDate"));
					System.out.println("test :::::" + test);
					patientForm.setEstimatedDueDate(test);
					patientForm
							.setLastMenstrualPeriod(dateToBeDisplayed.format(resultSet.getDate("lastMenstrualPeriod")));
					patientForm.setNameOfGuardian(resultSet.getString("nameOfGuardian"));
					patientForm.setWeekOfPregnancy(resultSet.getInt("weekOfPregnancy"));
				}

				patientForm.setIsConsultationDone(resultSet.getInt("isConsultationDone"));

			}

			preparedStatement6 = connection.prepareStatement(retrieveDiagnosisQuery);

			preparedStatement6.setInt(1, lastVisitID);

			resultSet6 = preparedStatement6.executeQuery();

			while (resultSet6.next()) {

				patientForm.setCancerType(resultSet6.getString("diagnosis"));
			}

			/*
			 * Retrieving patient's last visitID
			 */
			String retreivePatientLastVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving last enetered OPD Visit details from OphthalmologyOPD table
			 */
			preparedStatement2 = connection.prepareStatement(retriveOPDVisitQuery);
			preparedStatement2.setInt(1, lastVisitID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				patientForm.setEyeLidLowerOD(resultSet2.getString("eyelidLowerOD"));
				patientForm.setEyeLidLowerOS(resultSet2.getString("eyelidLowerOS"));
				patientForm.setEyeLidUpperOD(resultSet2.getString("eyelidUpperOD"));
				patientForm.setEyeLidUpperOS(resultSet2.getString("eyelidUpperOS"));
				patientForm.setVisualAcuityDistOD(resultSet2.getString("visualAcuityDistOD"));
				patientForm.setVisualAcuityDistOS(resultSet2.getString("visualAcuityDistOS"));
				patientForm.setVisualAcuityNearOD(resultSet2.getString("visualAcuityNearOD"));
				patientForm.setVisualAcuityNearOS(resultSet2.getString("visualAcuityNearOS"));
				patientForm.setPinholeVisionDistOD(resultSet2.getString("pinholeVisionDistOD"));
				patientForm.setPinholeVisionDistOS(resultSet2.getString("pinholeVisionDistOS"));
				patientForm.setPinholeVisionNearOD(resultSet2.getString("pinholeVisionNearOD"));
				patientForm.setPinholeVisionNearOS(resultSet2.getString("pinholeVisionNearOS"));
				patientForm.setBCVADistOD(resultSet2.getString("bcvaDistOD"));
				patientForm.setBCVADistOS(resultSet2.getString("bcvaDistOS"));
				patientForm.setBCVANearOD(resultSet2.getString("bcvaNearOD"));
				patientForm.setBCVANearOS(resultSet2.getString("bcvaNearOS"));
				patientForm.setConjunctivaOD(resultSet2.getString("conjunctivaOD"));
				patientForm.setConjunctivaOS(resultSet2.getString("conjunctivaOS"));
				patientForm.setCorneaOD(resultSet2.getString("corneaOD"));
				patientForm.setCorneaOS(resultSet2.getString("corneaOS"));
				patientForm.setPupilOD(resultSet2.getString("pupilOD"));
				patientForm.setPupilOS(resultSet2.getString("pupilOS"));
				patientForm.setACOD(resultSet2.getString("anteriorChamberOD"));
				patientForm.setACOS(resultSet2.getString("anteriorChamberOS"));
				patientForm.setIrisOD(resultSet2.getString("irisOD"));
				patientForm.setIrisOS(resultSet2.getString("irisOS"));
				patientForm.setLensOD(resultSet2.getString("lensOD"));
				patientForm.setLensOS(resultSet2.getString("lensOS"));
				patientForm.setDiscOD(resultSet2.getString("discOD"));
				patientForm.setDiscOS(resultSet2.getString("discOS"));
				patientForm.setVesselOD(resultSet2.getString("vesselOD"));
				patientForm.setVesselOS(resultSet2.getString("vesselOS"));
				patientForm.setMaculaOD(resultSet2.getString("maculaOD"));
				patientForm.setMaculaOS(resultSet2.getString("maculaOS"));
				patientForm.setIODOD(resultSet2.getString("iopOD"));
				patientForm.setIODOS(resultSet2.getString("iopOS"));
				patientForm.setSacOD(resultSet2.getString("sacOD"));
				patientForm.setSacOS(resultSet2.getString("sacOS"));
				patientForm.setK1OD(resultSet2.getString("biometryK1OD"));
				patientForm.setK1OS(resultSet2.getString("biometryK1OS"));
				patientForm.setK2OD(resultSet2.getString("biometryK2OD"));
				patientForm.setK2OS(resultSet2.getString("biometryK2OS"));
				patientForm.setAxialLengthOD(resultSet2.getString("biometryAxialLengthOD"));
				patientForm.setAxialLengthOS(resultSet2.getString("biometryAxialLengthOS"));
				patientForm.setIOLOD(resultSet2.getString("biometryIOLOD"));
				patientForm.setIOLOS(resultSet2.getString("biometryIOLOS"));
				patientForm.setPosteriorComment(resultSet2.getString("posteriorComment"));
				patientForm.setBiometryComment(resultSet2.getString("biometryComment"));
				patientForm.setScleraOD(resultSet2.getString("scleraOD"));
				patientForm.setScleraOS(resultSet2.getString("scleraOS"));
				patientForm.setLeftEyeHistory(resultSet2.getString("leftEyeHistory")); // New Entry 1
				patientForm.setLeftEyeDuration(resultSet2.getString("leftEyeDuration")); // New Entry 2
				patientForm.setRightEyeHistory(resultSet2.getString("rightEyeHistory")); // New Entry 3
				patientForm.setRightEyeDuration(resultSet2.getString("rightEyeDuration")); // New Entry 4
			}

			/*
			 * Retrieving optician ID from Visit ID
			 */
			patientForm.setOpticinID(retrieveOpticianID(patientForm.getVisitID()));

			/*
			 * Retrieving visit type from Visit ID
			 */
			patientForm.setVisitType(visitType);
			System.out.println("visit type is ::: " + patientForm.getVisitType());

			/*
			 * Retrieving last entered Eyewear details
			 */
			preparedStatement3 = connection.prepareStatement(retrieveEyewearQuery);
			preparedStatement3.setInt(1, patientForm.getOpticinID());

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {
				patientForm.setTint(resultSet3.getString("tint"));
				patientForm.setGlass(resultSet3.getString("glass"));
				patientForm.setMaterial(resultSet3.getString("material"));
				patientForm.setClinic(resultSet3.getString("clinic"));
				patientForm.setFrame(resultSet3.getString("frame"));
				patientForm.setGlassCharge(resultSet3.getDouble("glassCharges"));
				patientForm.setFrameCharge(resultSet3.getDouble("frameCharges"));
				patientForm.setDiscount(resultSet3.getDouble("discount"));
				patientForm.setNetPayment(resultSet3.getDouble("netPayment"));
				patientForm.setAdvance(resultSet3.getDouble("advance"));
				patientForm.setBalance(resultSet3.getDouble("balance"));
				patientForm.setBalancePaid(resultSet3.getDouble("balPaid"));
				patientForm.setBalancePaidDate(resultSet3.getString("balPaidDate"));
				patientForm.setOpticinID(resultSet3.getInt("optometryID"));
				patientForm.setOptDiscountType(resultSet3.getString("discountType"));

				/*
				 * Calculating eyewear total.
				 */
				eyewearTotal = resultSet3.getDouble("glassCharges") + resultSet3.getDouble("frameCharges");
				patientForm.setTotal(eyewearTotal);
			}

			/*
			 * Setting last enetered visitID into PatientForm's lastVisitID variable
			 */
			patientForm.setLastVisitID(retrievVisitID(patientForm.getPatientID(), clinicID));

			/*
			 * Retrieving optician old glasses details
			 */
			preparedStatement4 = connection.prepareStatement(retrieveOpticianOldGlassesQuery);
			preparedStatement4.setInt(1, patientForm.getOpticinID());

			resultSet4 = preparedStatement4.executeQuery();

			while (resultSet4.next()) {
				patientForm.setOldSphDiskOD(resultSet4.getString("sphDistOD"));
				patientForm.setOldSphDiskOS(resultSet4.getString("sphDistOS"));
				patientForm.setOldSphNearOD(resultSet4.getString("sphNearOD"));
				patientForm.setOldSphNearOS(resultSet4.getString("sphNearOS"));
				patientForm.setOldCylDiskOD(resultSet4.getString("cylDistOD"));
				patientForm.setOldCylDiskOS(resultSet4.getString("cylDistOS"));
				patientForm.setOldCylNearOD(resultSet4.getString("cylNearOD"));
				patientForm.setOldCylNearOS(resultSet4.getString("cylNearOS"));
				patientForm.setOldAxisDiskOD(resultSet4.getString("axisDistOD"));
				patientForm.setOldAxisDiskOS(resultSet4.getString("axisDistOS"));
				patientForm.setOldAxisNearOD(resultSet4.getString("axisNearOD"));
				patientForm.setOldAxisNearOS(resultSet4.getString("axisNearOS"));
				patientForm.setOldVnDiskOD(resultSet4.getString("vnDistOD"));
				patientForm.setOldVnDiskOS(resultSet4.getString("vnDistOS"));
				patientForm.setOldVnNearOD(resultSet4.getString("vnNearOD"));
				patientForm.setOldVnNearOS(resultSet4.getString("vnNearOS"));
			}

			/*
			 * Retrieving optician new glasses details
			 */
			preparedStatement5 = connection.prepareStatement(retrieveOpticianQuery);
			preparedStatement5.setInt(1, patientForm.getOpticinID());

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {
				patientForm.setSphDiskOD(resultSet5.getString("sphDistOD"));
				patientForm.setSphDiskOS(resultSet5.getString("sphDistOS"));
				patientForm.setSphNearOD(resultSet5.getString("sphNearOD"));
				patientForm.setSphNearOS(resultSet5.getString("sphNearOS"));
				patientForm.setCylDiskOD(resultSet5.getString("cylDistOD"));
				patientForm.setCylDiskOS(resultSet5.getString("cylDistOS"));
				patientForm.setCylNearOD(resultSet5.getString("cylNearOD"));
				patientForm.setCylNearOS(resultSet5.getString("cylNearOS"));
				patientForm.setAxisDiskOD(resultSet5.getString("axisDistOD"));
				patientForm.setAxisDiskOS(resultSet5.getString("axisDistOS"));
				patientForm.setAxisNearOD(resultSet5.getString("axisNearOD"));
				patientForm.setAxisNearOS(resultSet5.getString("axisNearOS"));
				patientForm.setVnDiskOD(resultSet5.getString("vnDistOD"));
				patientForm.setVnDiskOS(resultSet5.getString("vnDistOS"));
				patientForm.setVnNearOD(resultSet5.getString("vnNearOD"));
				patientForm.setVnNearOS(resultSet5.getString("vnNearOS"));
				patientForm.setOptomeryComment(resultSet5.getString("comments"));
				patientForm.setSpectacleComments(resultSet5.getString("spectacleComments"));
				if (resultSet5.getString("spectacleComments") == null) {
					patientForm.setDefaultSpectacleComments(null);
				} else {
					patientForm.setDefaultSpectacleComments(resultSet5.getString("spectacleComments").split(", "));
				}
			}

			resultSet5.close();
			preparedStatement5.close();

			/*
			 * Retrieving Cycloplegic Refraction details
			 */
			preparedStatement5 = connection.prepareStatement(retrieveCycloplegicRefractionQuery);

			preparedStatement5.setInt(1, lastVisitID);

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {
				patientForm.setDistCTCOD(resultSet5.getString("distCTCOD"));
				patientForm.setDistHTCOD(resultSet5.getString("distHTCOD"));
				patientForm.setDistAtropineOD(resultSet5.getString("distAtropineOD"));
				patientForm.setDistTPlusOD(resultSet5.getString("distTPlusOD"));
				patientForm.setDistCTCOS(resultSet5.getString("distCTCOS"));
				patientForm.setDistHTCOS(resultSet5.getString("distHTCOS"));
				patientForm.setDistAtropineOS(resultSet5.getString("distAtropineOS"));
				patientForm.setDistTPlusOS(resultSet5.getString("distTPlusOS"));
				patientForm.setNearCTCOD(resultSet5.getString("nearCTCOD"));
				patientForm.setNearHTCOD(resultSet5.getString("nearHTCOD"));
				patientForm.setNearAtropineOD(resultSet5.getString("nearAtropineOD"));
				patientForm.setNearTPlusOD(resultSet5.getString("nearTPlusOD"));
				patientForm.setNearCTCOS(resultSet5.getString("nearCTCOS"));
				patientForm.setNearHTCOS(resultSet5.getString("nearHTCOS"));
				patientForm.setNearAtropineOS(resultSet5.getString("nearAtropineOS"));
				patientForm.setNearTPlusOS(resultSet5.getString("nearTPlusOS"));

				if (resultSet5.getString("cycloplegicRefractionData") == null) {
					patientForm.setDefaultCycloplegicRefraction(null);
				} else {
					patientForm.setDefaultCycloplegicRefraction(
							resultSet5.getString("cycloplegicRefractionData").split(", "));
				}

				patientForm.setCycloplegicRefractionID(resultSet5.getInt("id"));

			}

			/*
			 * Retrieving USG PCPNDT details visitID
			 */
			String retreivePCPNDTQuery = QueryMaker.RETRIEVE_PCPNDT_DATA_VISIT_ID;

			preparedStatement7 = connection.prepareStatement(retreivePCPNDTQuery);

			preparedStatement7.setInt(1, patientForm.getVisitID());

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {
				System.out.println("in pcpndt loop" + resultSet7.getString("ageOfSons"));
				patientForm.setNumberOfSons(resultSet7.getInt("numberOfSons"));
				patientForm.setSonAge(resultSet7.getString("ageOfSons"));
				patientForm.setNumberOfDaughters(resultSet7.getInt("numberOfDaughters"));
				patientForm.setDaughterAge(resultSet7.getString("ageOfDaughters"));
			}

			lastEnetredVisitList.add(patientForm);
			resultSet.close();
			resultSet1.close();
			resultSet2.close();
			resultSet3.close();
			resultSet4.close();
			resultSet5.close();
			resultSet6.close();
			resultSet7.close();

			System.out.println("Successfully retieved last enetred visit details");

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last enetred Visit details table due to:::"
					+ exception.getMessage());
		}
		return lastEnetredVisitList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#retrieveLastEnteredPrescDetail(int)
	 */
	public List<PatientForm> retrieveLastEnteredPrescDetail(int visitID) {
		List<PatientForm> lastEnetredPrescList = new ArrayList<PatientForm>();
		PatientForm patientForm = null;
		try {
			connection = getConnection();

			String retrieveLastEnteredPrescDetailQuery = QueryMaker.RETRIEVE_LAST_ENTERED_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredPrescDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setDose(resultSet.getString("dose"));
				patientForm.setDoseUnit(resultSet.getString("doseUnit"));
				patientForm.setNoOfDays(resultSet.getString("numberOfDays"));
				patientForm.setFrequency(resultSet.getString("frequency"));
				patientForm.setComment(resultSet.getString("comment"));
				patientForm.setActivityStatus(resultSet.getString("activityStatus"));
				patientForm.setVisitID(visitID);
			}

			lastEnetredPrescList.add(patientForm);
			System.out.println("Successfully retieved last enetred prescription details");

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last enetred Prescrtiption details table due to:::"
					+ exception.getMessage());
		}
		return lastEnetredPrescList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastEnteredBillingDetail
	 * (int)
	 */
	public List<PatientForm> retrieveLastEnteredBillingDetail(int visitID) {
		List<PatientForm> lastEnetredPrescList = new ArrayList<PatientForm>();
		PatientForm patientForm = null;
		try {
			connection = getConnection();

			String retrieveLastEnteredBillingDetailQuery = QueryMaker.RETRIEVE_LAST_ENTERED_BILLING_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredBillingDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setBillID(resultSet.getInt("id"));
				patientForm.setDescription(resultSet.getString("chargeType"));
				patientForm.setCharges(resultSet.getDouble("charge"));
				patientForm.setActivityStatus(resultSet.getString("activityStatus"));
				patientForm.setVisitID(visitID);
				patientForm.setRate(resultSet.getDouble("rate"));
				patientForm.setServiceTax(resultSet.getDouble("serviceTax"));
				patientForm.setTotalBill(resultSet.getDouble("totalBill"));
			}

			lastEnetredPrescList.add(patientForm);
			System.out.println("Successfully retieved last enetred billing details");

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last enetred Billing details table due to:::"
					+ exception.getMessage());
		}
		return lastEnetredPrescList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientVisitDetail(int,
	 * int)
	 */
	public List<PatientForm> retrievePatientVisitDetail(int patientID, int clinicID) {
		List<PatientForm> patientVisitDetails = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String retrievePatientVisitDetailQuery = QueryMaker.RETRIEVE_PATIENT_VISIT_DETAIL1;

			preparedStatement = connection.prepareStatement(retrievePatientVisitDetailQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitTypeID(resultSet.getInt("visitTypeID"));
				patientForm.setMdDoctorID(resultSet.getInt("mdDoctorID"));
				patientForm.setPcpndtStatus(resultSet.getString("pcpndtStatus"));
				// patientForm.setVisitDate(dateFormat.format(resultSet.getDate("visitDate")));

				if (resultSet.getString("visitDate") == "" || resultSet.getString("visitDate") == null) {
					patientForm.setVisitDate("");
				} else if (resultSet.getString("visitDate").isEmpty()) {
					patientForm.setVisitDate("");
				} else {
					patientForm.setVisitDate(dateFormat.format(resultSet.getDate("visitDate")));
				}

				if (resultSet.getString("diagnosis") == null || resultSet.getString("diagnosis") == "") {
					patientForm.setDiagnosis(retrievePersonalHistroyDiagnosis(patientID));
				} else {
					if (resultSet.getString("diagnosis").isEmpty()) {
						patientForm.setDiagnosis(retrievePersonalHistroyDiagnosis(patientID));
					} else {
						patientForm.setDiagnosis(resultSet.getString("diagnosis"));
					}
				}
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));

				patientForm.setReceiptID(resultSet.getInt("receiptID"));
				patientForm.setBalPayment(resultSet.getDouble("balPayment"));

				patientForm.setVisitID(resultSet.getInt("id"));

				patientForm.setPatientID(patientID);

				patientVisitDetails.add(patientForm);
			}

			System.out.println("Successfully retrieved patient's visit details.");

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient total visit details from table due to:::"
					+ exception.getMessage());
		}
		return patientVisitDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientVisitDetailForStaff
	 * (int)
	 */
	public List<PatientForm> retrievePatientVisitDetailForStaff(int patientID) {
		List<PatientForm> patientVisitDetails = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {
			connection = getConnection();

			String retrievePatientVisitDetailQuery = QueryMaker.RETRIEVE_PATIENT_VISIT_DETAIL_FOR_STAFF;

			preparedStatement = connection.prepareStatement(retrievePatientVisitDetailQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, "OPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(resultSet.getString("visitDate"));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				patientForm.setVisitID(resultSet.getInt("id"));

				patientVisitDetails.add(patientForm);
			}
			System.out.println("Successfully retrieved patient's visit details.");

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient total visit details for Staff from table due to:::"
							+ exception.getMessage());
		}
		return patientVisitDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientIDByVisitID(int)
	 */
	public int retrievePatientIDByVisitID(int visitID) {
		int patientID = 0;
		try {
			connection = getConnection();

			String retrievePatientIDByVisitIDQuery = QueryMaker.RETRIEVE_PATIENT_ID_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientIDByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientID = resultSet.getInt("patientID");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient id from Visit table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return patientID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertPrescrpitonDetails(int)
	 */
	public JSONObject insertPrescrpitonDetails(PatientForm patientForm) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String insertPrescriptionDetailsQuery = QueryMaker.INSERT_PRESCRIPTION_DETAIL;

			preparedStatement = connection.prepareStatement(insertPrescriptionDetailsQuery);

			preparedStatement.setString(1, patientForm.getDrugName());
			preparedStatement.setString(2, patientForm.getDose());
			preparedStatement.setString(3, patientForm.getTradeName());
			preparedStatement.setString(4, patientForm.getNoOfDays());
			preparedStatement.setString(5, patientForm.getFrequency());
			preparedStatement.setString(6, patientForm.getComment());
			preparedStatement.setString(7, ActivityStatus.ACTIVE);
			preparedStatement.setInt(8, patientForm.getVisitID());
			preparedStatement.setInt(9, patientForm.getNoOfPills());

			preparedStatement.execute();

			object.put("SuccessMessage", "New prescription added successfully. ");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to add new prescription. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deletePrescription(int)
	 */
	public JSONObject deletePrescription(int prescriptionID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deletePrescriptionQuery = QueryMaker.DELETE_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(deletePrescriptionQuery);

			preparedStatement.setInt(2, prescriptionID);
			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Prescription details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting prescription.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyApointmentTime(int, int,
	 * java.lang.String, int)
	 */
	public JSONObject verifyApointmentTime(int fromHH, int fromMM, String fromAMPM, int clinicID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		String errorMessage = "";

		boolean check = false;
		try {
			connection = getConnection();

			String verifyApointmentTimeQuery = QueryMaker.RETRIEVE_CLINIC_TIME;

			preparedStatement = connection.prepareStatement(verifyApointmentTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String[] clinicStartArray = resultSet.getString("clinicStart").split(":");
				String[] clinicEndArray = resultSet.getString("clinicEnd").split(":");
				String[] breakStartArray = resultSet.getString("breakStart").split(":");
				String[] breakEndArray = resultSet.getString("breakEnd").split(":");
				int apptDuration = Integer.parseInt(resultSet.getString("appointmentDuration"));

				// getting clinic start time hour, minute and ampm value from
				// clinicStartArray varaible
				int clinicStartHH = Integer.parseInt(clinicStartArray[0]);

				String startArray[] = clinicStartArray[1].split(" ");

				int clinicStartMM = Integer.parseInt(startArray[0]);

				String clinicStartAMPM = startArray[1].trim();

				// getting clinic end time hour, minute and ampm value from
				// clinicEndArray varaible
				int clinicEndHH = Integer.parseInt(clinicEndArray[0]);

				String endArray[] = clinicEndArray[1].split(" ");

				int clinicEndMM = Integer.parseInt(endArray[0]);

				String clinicEndAMPM = endArray[1].trim();

				// getting break start time hour, minute and ampm value from
				// clinicEndArray varaible
				int breakStartHH = Integer.parseInt(breakStartArray[0]);

				String startArray1[] = breakStartArray[1].split(" ");

				int breakStartMM = Integer.parseInt(startArray1[0]);

				String breakStartAMPM = startArray1[1].trim();

				// getting break end time hour, minute and ampm value from
				// clinicEndArray varaible
				int breakEndHH = Integer.parseInt(breakEndArray[0]);

				String startArray2[] = breakEndArray[1].split(" ");

				int breakEndMM = Integer.parseInt(startArray2[0]);

				String breakEndAMPM = startArray2[1].trim();

				System.out.println("Clinic Start HH : " + clinicStartHH + "\nClinic End HH : " + clinicEndHH
						+ "\nClinic Start MM : " + clinicStartMM + "\nCLinic End MM: " + clinicEndMM
						+ "\nClinic Start AMPM : " + clinicStartAMPM + "\nClinic End AMPM : " + clinicEndAMPM
						+ "\nBreak Start HH : " + breakStartHH + "\nBreak End HH : " + breakEndHH
						+ "\nBreak Start MM : " + breakStartMM + "\nBreak End MM: " + breakEndMM
						+ "\nBreak Start AMPM : " + breakStartAMPM + "\nBreak End AMPM : " + breakEndAMPM);

				System.out.println("Check 1 :: " + (fromHH < clinicStartHH));

				System.out.println("Check 2 :: " + (fromAMPM.trim() != clinicStartAMPM.trim()));

				/*
				 * Checking whether time selected by user lies between the clinic start and end
				 * time, if not then give error message on UI to restrict user to submit the
				 * form
				 */
				/*
				 * Checking whether appointment start hour selected by user is less than
				 * clinicStart hour and appt ampm value is equal to clinic start ampm,if yes
				 * then give error message
				 */
				if ((fromHH < clinicStartHH) && (fromAMPM.equals(clinicStartAMPM))) {

					errorMessage = "Clinic time is from " + clinicStartHH + ":" + clinicStartMM + " " + clinicStartAMPM
							+ " to " + clinicEndHH + ":" + clinicEndMM + " " + clinicEndAMPM
							+ ". Please select appointment time accordingly.";

					System.out.println("Case 1 :: " + errorMessage);

					/*
					 * Checking whether appointment start hour selected by user is equal to
					 * breakStart hour and Checking whether appointment start hour selected by user
					 * is equal to breakEnd hour and appt ampm value is equal to breakStart ampm,if
					 * yes then give error message
					 */
				} else if ((fromHH == breakStartHH) && (fromHH == breakEndHH) && (fromAMPM.equals(breakEndAMPM))) {

					if (fromMM >= breakStartMM && fromMM <= breakEndMM) {

						errorMessage = "Clinic break time is from " + breakStartHH + ":" + breakStartMM + " "
								+ breakStartAMPM + " to " + breakEndHH + ":" + breakEndMM + " " + breakEndAMPM
								+ ". Please select appointment time accordingly.";

						System.out.println("Case 3 :: " + errorMessage);

						check = true;
					}

					/*
					 * Checking whether appointment start hour selected by user is equal to
					 * breakStart hour and appt ampm value is equal to breakStart ampm,if yes then
					 * give error message OR
					 */
				} else if ((fromHH == breakStartHH) && (fromAMPM.equals(breakStartAMPM)) && (!check)) {

					errorMessage = "Clinic break time is from " + breakStartHH + ":" + breakStartMM + " "
							+ breakStartAMPM + " to " + breakEndHH + ":" + breakEndMM + " " + breakEndAMPM
							+ ". Please select appointment time accordingly.";

					System.out.println("Case 4 :: " + errorMessage);

					/*
					 * Checking whether appointment start hour selected by user is equal to breakEnd
					 * hour and appt ampm value is equal to breakEnd ampm,if yes then give error
					 * message
					 */
				} else if ((fromHH == breakEndHH) && (fromAMPM.equals(breakEndAMPM))) {

					if (fromMM != 0 && fromMM < breakEndMM) {

						errorMessage = "Clinic break time is from " + breakStartHH + ":" + breakStartMM + " "
								+ breakStartAMPM + " to " + breakEndHH + ":" + breakEndMM + " " + breakEndAMPM
								+ ". Please select appointment time accordingly.";

						System.out.println("Case 5 :: " + errorMessage);

					}

					/*
					 * Checking whether appointment start hour selected by user is equal to
					 * clinicEnd hour and appt ampm value is equal to clinic end ampm value
					 */
				} else if ((fromHH == clinicEndHH) && (fromAMPM.equals(clinicEndAMPM))) {

					// Adding appointmentDuration value with user selected
					// appointment minute value and storing it into tempDur
					// variable
					int tempDur = apptDuration + fromMM;

					/*
					 * If tempDur is equal to 60 then appointment time lies between clinic end time
					 * else give error message
					 */
					if (tempDur > 60) {
						errorMessage = "Clinic end time is " + clinicEndHH + ":" + clinicEndMM + " " + clinicEndAMPM
								+ ". Please select appointment time accordingly.";

						System.out.println("Case 6 :: " + errorMessage);
					}

					/*
					 * Checking whether appointment start hour selected by user is greater than
					 * clinicEnd hour and appt ampm value is equal to clinic end ampm value
					 */
				} else if ((fromHH > clinicEndHH) && (fromAMPM.equals(clinicEndAMPM))
						|| (fromHH > clinicEndHH) && (fromAMPM.trim() != clinicEndAMPM.trim())) {

					/*
					 * If appointment start hour selected by user is equal to 12 and AMPM value is
					 * equal to clinic end AMPM value then appointment time is in between clinic
					 * start and end time else give error message
					 */
					if (fromHH == 12 && fromAMPM.equals(clinicEndAMPM)) {

						errorMessage = "";

						/*
						 * If appointment start hour selected by user is equal to 12 and AMPM value is
						 * not equal to clinic end AMPM value then appointment time is beyond clinic end
						 * time, so give error message
						 */
					} else if (fromHH == 12 && fromAMPM.trim() != clinicEndAMPM.trim()) {
						errorMessage = "Clinic end time is " + clinicEndHH + ":" + clinicEndMM + " " + clinicEndAMPM
								+ ". Please select appointment time accordingly.";

						System.out.println("Case 7 :: " + errorMessage);

						/*
						 * If appointment start hour selected by user is not equal to 12 and AMPM value
						 * is equal to clinic end AMPM value then appointment time is beyond clinic end
						 * time, so give error message
						 */
					} else if (fromHH != 12 && fromAMPM.equals(clinicEndAMPM)) {
						errorMessage = "Clinic end time is " + clinicEndHH + ":" + clinicEndMM + " " + clinicEndAMPM
								+ ". Please select appointment time accordingly.";

						System.out.println("Case 8 :: " + errorMessage);
					}

				}

				object.put("CheckMsg", errorMessage);
				array.add(object);

				values.put("Release1", array);

			}

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying appointment time with clinic time from Calendar table due to:::"
							+ exception.getMessage());

			object.put("ErrMSg",
					"Exception occurred while checking appointment time. Please check server log for more details.");
			array.add(object);

			values.put("Release1", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updatePrescrpitonDetails(com.
	 * edhanvantari.form.PatientForm)
	 */
	public JSONObject updatePrescrpitonDetails(PatientForm patientForm) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();
		try {
			connection = getConnection();

			String updatePrescriptionDetailsQuery = QueryMaker.UPDATE_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(updatePrescriptionDetailsQuery);

			preparedStatement.setString(1, patientForm.getDrugName());
			preparedStatement.setString(2, patientForm.getDose());
			preparedStatement.setString(3, patientForm.getTradeName());
			preparedStatement.setString(4, patientForm.getNoOfDays());
			preparedStatement.setString(5, patientForm.getFrequency());
			preparedStatement.setString(6, patientForm.getComment());
			preparedStatement.setInt(7, patientForm.getPrescriptionID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("prescription details udpated successfully..");

			object.put("SuccessMessage", "Prescription updated successfully. ");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to update prescription. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateBilling(int)
	 */
	public JSONObject updateBilling(PatientForm patientForm) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String dateToBeInsert = simpleDateFormat.format(date);

		try {
			connection = getConnection();

			String updateVisitQuery = QueryMaker.UPDATE_BILLING_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			preparedStatement.setString(1, patientForm.getDescription());
			preparedStatement.setDouble(2, patientForm.getCharges());
			preparedStatement.setDouble(3, patientForm.getRate());
			preparedStatement.setDouble(4, patientForm.getTotalBill());
			preparedStatement.setString(5, dateToBeInsert);
			preparedStatement.setInt(6, patientForm.getBillID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated bill details into Billing table.");

			object.put("SuccessMessage", "Bill updated successfully. ");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating bil details into table due to:::" + exception.getMessage());

			object.put("ErrorMessage", "Failed to update bill. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyVisitDetails(int)
	 */
	public int verifyVisitDetails(int patientID, int clinicID) {
		int check = 0;
		try {
			connection = getConnection();

			String verifyVisitDetailsQuery = QueryMaker.VERIFY_VISIT_DETAIL;

			preparedStatement = connection.prepareStatement(verifyVisitDetailsQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying visit details into Visit table due to:::"
					+ exception.getMessage());
		}
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveVisitNumber(int)
	 */
	public int retrieveVisitNumber(int patientID, int clinicID) {
		int visitNumber = 0;
		try {
			connection = getConnection();

			String retrieveVisitNumberQuery = QueryMaker.RETRIEVE_VISIT_NUMBER;

			preparedStatement = connection.prepareStatement(retrieveVisitNumberQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// visitNumber = 0;
				visitNumber = resultSet.getInt("visitNumber");
			}
			System.out.println("Visit number is ::::" + visitNumber);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visitNumber from Visit table due to:::"
					+ exception.getMessage());
		}
		return visitNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievVisitIDForNewVisitType(int,
	 * java.lang.String)
	 */
	public int retrievVisitIDForNewVisitType(int patientID, String visitType) {
		int visitID = 0;
		try {
			connection = getConnection();

			String retrievVisitIDForNewVisitTypeQuery = QueryMaker.RETRIEVE_VISIT_ID_FOR_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(retrievVisitIDForNewVisitTypeQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, "New");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = 0;
				visitID = resultSet.getInt("id");
			}
			System.out.println("Visit ID for Visit Type " + visitType + " is :::: " + visitID);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visitID based on New visit Type table due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertOPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String updateOPDVisit(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertOPDPrescriptionQuery = QueryMaker.UPDATE_OPD_PRESCRIPTION_DETAIL;

			preparedStatement = connection.prepareStatement(insertOPDPrescriptionQuery);

			preparedStatement.setString(1, patientForm.getEyeLidUpperOD());
			preparedStatement.setString(2, patientForm.getEyeLidUpperOS());
			preparedStatement.setString(3, patientForm.getEyeLidLowerOD());
			preparedStatement.setString(4, patientForm.getEyeLidLowerOS());
			preparedStatement.setString(5, patientForm.getVisualAcuityDistOD());
			preparedStatement.setString(6, patientForm.getVisualAcuityDistOS());
			preparedStatement.setString(7, patientForm.getVisualAcuityNearOD());
			preparedStatement.setString(8, patientForm.getVisualAcuityNearOS());
			preparedStatement.setString(9, patientForm.getPinholeVisionDistOD());
			preparedStatement.setString(10, patientForm.getPinholeVisionDistOS());
			preparedStatement.setString(11, patientForm.getPinholeVisionNearOD());
			preparedStatement.setString(12, patientForm.getPinholeVisionNearOS());
			preparedStatement.setString(13, patientForm.getBCVADistOD());
			preparedStatement.setString(14, patientForm.getBCVADistOS());
			preparedStatement.setString(15, patientForm.getBCVANearOD());
			preparedStatement.setString(16, patientForm.getBCVANearOS());
			preparedStatement.setString(17, patientForm.getConjunctivaOD());
			preparedStatement.setString(18, patientForm.getConjunctivaOS());
			preparedStatement.setString(19, patientForm.getCorneaOD());
			preparedStatement.setString(20, patientForm.getCorneaOS());
			preparedStatement.setString(21, patientForm.getPupilOD()); // New entry 1
			preparedStatement.setString(22, patientForm.getPupilOS()); // New entry 2
			preparedStatement.setString(23, patientForm.getACOD());
			preparedStatement.setString(24, patientForm.getACOS());
			preparedStatement.setString(25, patientForm.getIrisOD());
			preparedStatement.setString(26, patientForm.getIrisOS());
			preparedStatement.setString(27, patientForm.getLensOD());
			preparedStatement.setString(28, patientForm.getLensOS());
			preparedStatement.setString(29, patientForm.getDiscOD());
			preparedStatement.setString(30, patientForm.getDiscOS());
			preparedStatement.setString(31, patientForm.getVesselOD());
			preparedStatement.setString(32, patientForm.getVesselOS());
			preparedStatement.setString(33, patientForm.getMaculaOD());
			preparedStatement.setString(34, patientForm.getMaculaOS());
			preparedStatement.setString(35, patientForm.getIODOD());
			preparedStatement.setString(36, patientForm.getIODOS());
			preparedStatement.setString(37, patientForm.getSacOD());
			preparedStatement.setString(38, patientForm.getSacOS());
			preparedStatement.setString(39, patientForm.getK1OD());
			preparedStatement.setString(40, patientForm.getK1OS());
			preparedStatement.setString(41, patientForm.getK2OD());
			preparedStatement.setString(42, patientForm.getK2OS());
			preparedStatement.setString(43, patientForm.getAxialLengthOD());
			preparedStatement.setString(44, patientForm.getAxialLengthOS());
			preparedStatement.setString(45, patientForm.getIOLOD());
			preparedStatement.setString(46, patientForm.getIOLOS());
			preparedStatement.setString(47, patientForm.getPosteriorComment());
			preparedStatement.setString(48, patientForm.getBiometryComment());
			preparedStatement.setString(49, patientForm.getScleraOD());
			preparedStatement.setString(50, patientForm.getScleraOS());
			preparedStatement.setString(51, patientForm.getLeftEyeHistory()); // New Entry 3
			preparedStatement.setString(52, patientForm.getLeftEyeDuration()); // New Entry 4
			preparedStatement.setString(53, patientForm.getRightEyeHistory()); // New Entry 5
			preparedStatement.setString(54, patientForm.getRightEyeDuration()); // New Entry 6
			preparedStatement.setInt(55, patientForm.getVisitID());
			preparedStatement.execute();

			System.out.println("New OPD Visit has been addedd successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting OPD Prescription details into table due to:::"
					+ exception.getMessage());

			status = "error";
			return status;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertOPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String insertOPDVisit(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertOPDVisitQuery = QueryMaker.INSERT_OPD_PRESCRIPTION_DETAIL1;

			preparedStatement = connection.prepareStatement(insertOPDVisitQuery);

			preparedStatement.setString(1, patientForm.getEyeLidUpperOD());
			preparedStatement.setString(2, patientForm.getEyeLidUpperOS());
			preparedStatement.setString(3, patientForm.getEyeLidLowerOD());
			preparedStatement.setString(4, patientForm.getEyeLidLowerOS());
			preparedStatement.setString(5, patientForm.getVisualAcuityDistOD());
			preparedStatement.setString(6, patientForm.getVisualAcuityDistOS());
			preparedStatement.setString(7, patientForm.getVisualAcuityNearOD());
			preparedStatement.setString(8, patientForm.getVisualAcuityNearOS());
			preparedStatement.setString(9, patientForm.getPinholeVisionDistOD());
			preparedStatement.setString(10, patientForm.getPinholeVisionDistOS());
			preparedStatement.setString(11, patientForm.getPinholeVisionNearOD());
			preparedStatement.setString(12, patientForm.getPinholeVisionNearOS());
			preparedStatement.setString(13, patientForm.getBCVADistOD());
			preparedStatement.setString(14, patientForm.getBCVADistOS());
			preparedStatement.setString(15, patientForm.getBCVANearOD());
			preparedStatement.setString(16, patientForm.getBCVANearOS());
			preparedStatement.setString(17, patientForm.getConjunctivaOD());
			preparedStatement.setString(18, patientForm.getConjunctivaOS());
			preparedStatement.setString(19, patientForm.getCorneaOD());
			preparedStatement.setString(20, patientForm.getCorneaOS());
			preparedStatement.setString(21, patientForm.getPupilOD()); // New entry 1
			preparedStatement.setString(22, patientForm.getPupilOS()); // New entry 2
			preparedStatement.setString(23, patientForm.getACOD());
			preparedStatement.setString(24, patientForm.getACOS());
			preparedStatement.setString(25, patientForm.getIrisOD());
			preparedStatement.setString(26, patientForm.getIrisOS());
			preparedStatement.setString(27, patientForm.getLensOD());
			preparedStatement.setString(28, patientForm.getLensOS());
			preparedStatement.setString(29, patientForm.getDiscOD());
			preparedStatement.setString(30, patientForm.getDiscOS());
			preparedStatement.setString(31, patientForm.getVesselOD());
			preparedStatement.setString(32, patientForm.getVesselOS());
			preparedStatement.setString(33, patientForm.getMaculaOD());
			preparedStatement.setString(34, patientForm.getMaculaOS());
			preparedStatement.setString(35, patientForm.getIODOD());
			preparedStatement.setString(36, patientForm.getIODOS());
			preparedStatement.setString(37, patientForm.getSacOD());
			preparedStatement.setString(38, patientForm.getSacOS());
			preparedStatement.setString(39, patientForm.getK1OD());
			preparedStatement.setString(40, patientForm.getK1OS());
			preparedStatement.setString(41, patientForm.getK2OD());
			preparedStatement.setString(42, patientForm.getK2OS());
			preparedStatement.setString(43, patientForm.getAxialLengthOD());
			preparedStatement.setString(44, patientForm.getAxialLengthOS());
			preparedStatement.setString(45, patientForm.getIOLOD());
			preparedStatement.setString(46, patientForm.getIOLOS());
			preparedStatement.setString(47, patientForm.getPosteriorComment());
			preparedStatement.setString(48, patientForm.getBiometryComment());
			preparedStatement.setString(49, patientForm.getScleraOD());
			preparedStatement.setString(50, patientForm.getScleraOS());
			preparedStatement.setString(51, patientForm.getLeftEyeHistory()); // New Entry 3
			preparedStatement.setString(52, patientForm.getLeftEyeDuration()); // New Entry 4
			preparedStatement.setString(53, patientForm.getRightEyeHistory()); // New Entry 5
			preparedStatement.setString(54, patientForm.getRightEyeDuration()); // New Entry 6
			preparedStatement.setInt(55, patientForm.getVisitID());
			preparedStatement.execute();

			System.out.println("New OPD details has been addedd successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting OPD details details into table due to:::"
					+ exception.getMessage());

			status = "error";
			return status;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertDummyOptician(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertDummyOptician(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertDummyOpticianQuery = QueryMaker.INSERT_DUMMY_OPTICIAN_DETAILS;

			preparedStatement = connection.prepareStatement(insertDummyOpticianQuery);

			preparedStatement.setInt(1, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("New Optician details has been addedd successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Optician details into table due to:::" + exception.getMessage());

			status = "error";
			return status;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertDummyOpticianOldGlasses(com
	 * .edhanvantari.form.PatientForm)
	 */
	public String insertDummyOpticianOldGlasses(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertDummyOpticianOldGlassesQuery = QueryMaker.INSERT_DUMMY_OPTICIAN_OLD_GLASSES_DETAILS;

			preparedStatement = connection.prepareStatement(insertDummyOpticianOldGlassesQuery);

			preparedStatement.setInt(1, patientForm.getOpticinID());

			preparedStatement.execute();

			System.out.println("Optician Old glasses details has been addedd successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Optician old glasses details into table due to:::"
					+ exception.getMessage());

			status = "error";
			return status;
		}
	}

	public JSONObject retrieveOPDPrscriptionDetail(int visitID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrievePrscriptionDetailQuery = QueryMaker.RETRIEVE_PRESCRIPTION_DETAIL;

			preparedStatement = connection.prepareStatement(retrievePrscriptionDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("prescriptionID", resultSet.getInt("id"));
				object.put("drugName", resultSet.getString("drugName"));
				object.put("dose", resultSet.getString("dose"));
				object.put("doseUnit", resultSet.getString("tradeName"));
				object.put("numberOfDays", resultSet.getString("numberOfDays"));
				object.put("frequency", resultSet.getString("frequency"));
				object.put("comment", resultSet.getString("comment"));
				object.put("visitID", resultSet.getInt("visitID"));

				array.add(object);

			}

			values.put("Release", array);
			if (resultSet == null) {
				System.out.println("RS Not null");
			} else {
				System.out.println("RS null");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#updatePatientVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String updatePatientVisit(PatientForm patientForm) {
		int visitNo = 1;

		/*
		 * Get the current time
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		patientForm.setVisitFromTime(dateFormat.format(date));

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed = new SimpleDateFormat("MM/dd/yyyy");

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.UPDATE_VISIT;

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			/*
			 * Converting date
			 */
			if (patientForm.getVisitDate().substring(2, 3).equals("-")) {
				date1 = dateToBeParsed123.parse(patientForm.getVisitDate());
				preparedStatement.setString(4, dateToBeFormatted.format(date1));
			} else if (patientForm.getVisitDate().substring(4, 5).equals("-")) {
				preparedStatement.setString(4, patientForm.getVisitDate());
			} else {
				date1 = dateToBeParsed.parse(patientForm.getVisitDate());
				preparedStatement.setString(4, dateToBeFormatted.format(date1));
			}

			String timeFrom = null;
			String timeTo = null;

			/*
			 * Combining time From and time To respectively
			 */
			timeFrom = patientForm.getVisitFromTimeHH() + ":" + patientForm.getVisitFromTimeMM() + " "
					+ patientForm.getVisitFromTimeAMPM();

			timeTo = patientForm.getVisitToTimeHH() + ":" + patientForm.getVisitToTimeMM() + " "
					+ patientForm.getVisitToTimeAMPM();

			preparedStatement.setString(1, "OPD");
			preparedStatement.setInt(2, visitNo);
			preparedStatement.setString(3, patientForm.getVisitType());
			preparedStatement.setString(5, timeFrom);
			preparedStatement.setString(6, timeTo);
			preparedStatement.setString(7, patientForm.getDiagnosis());
			preparedStatement.setString(8, patientForm.getMedicalNotes());
			preparedStatement.setString(9, ActivityStatus.ACTIVE);
			preparedStatement.setInt(10, patientForm.getPatientID());
			preparedStatement.setInt(11, 0);
			preparedStatement.setInt(12, patientForm.getNextVisitDays());
			preparedStatement.setInt(13, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

			/*
			 * Updating status of appointment to Completed
			 */
			completeAppointment(patientForm.getAptID());

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#updatePatientVisit(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String updatePatientVisit(PatientForm patientForm, int visitNumber, String nextVisitDate) {
		/*
		 * Get the current time
		 */
		Date date = new Date();

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.UPDATE_VISIT;

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			// Converting date into db format
			date = dateToBeParsed123.parse(patientForm.getVisitDate());

			String timeFrom = null;
			String timeTo = null;

			/*
			 * Combining time From and time To respectively
			 * 
			 * timeFrom = patientForm.getVisitFromTimeHH() + ":" +
			 * patientForm.getVisitFromTimeMM() + " " + patientForm.getVisitFromTimeAMPM();
			 * 
			 * timeTo = patientForm.getVisitToTimeHH() + ":" +
			 * patientForm.getVisitToTimeMM() + " " + patientForm.getVisitToTimeAMPM();
			 */
			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setString(2, dateToBeFormatted.format(date));
			preparedStatement.setString(3, patientForm.getVisitFromTimeHH());
			preparedStatement.setString(4, patientForm.getVisitToTimeHH());
			preparedStatement.setString(5, patientForm.getCancerType());
			preparedStatement.setString(6, patientForm.getMedicalNotes());
			preparedStatement.setInt(7, patientForm.getNextVisitDays());
			preparedStatement.setString(8, nextVisitDate);
			preparedStatement.setString(9, patientForm.getSystemicHistory());
			preparedStatement.setString(10, patientForm.getOccularHistory());
			preparedStatement.setString(11, patientForm.getPersonalHistory());

			if (patientForm.getComplainingOf() == "") {
				preparedStatement.setString(12, "");
			} else {
				preparedStatement.setString(12, patientForm.getComplainingOf());
			}

			preparedStatement.setString(13, patientForm.getDilationStartTime());
			preparedStatement.setString(14, patientForm.getDilationEndTime());
			preparedStatement.setString(15, patientForm.getDilationDuration());
			preparedStatement.setInt(16, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

			/*
			 * Updating status of appointment to Completed
			 */
			completeAppointment(patientForm.getAptID());

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#updatePatientVisit(com.edhanvantari
	 * .form.PatientForm, int, int)
	 */
	public String updatePatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef) {
		/*
		 * Get the current time
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.UPDATE_VISIT;

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			// Converting date into db format
			date = dateToBeParsed123.parse(patientForm.getVisitDate());

			String timeFrom = null;
			String timeTo = null;

			/*
			 * Combining time From and time To respectively
			 */
			timeFrom = patientForm.getVisitFromTimeHH() + ":" + patientForm.getVisitFromTimeMM() + " "
					+ patientForm.getVisitFromTimeAMPM();

			timeTo = patientForm.getVisitToTimeHH() + ":" + patientForm.getVisitToTimeMM() + " "
					+ patientForm.getVisitToTimeAMPM();

			preparedStatement.setString(1, "OPD");
			preparedStatement.setInt(2, visitNumber);
			preparedStatement.setString(3, patientForm.getVisitType());
			preparedStatement.setString(4, dateToBeFormatted.format(date));
			preparedStatement.setString(5, timeFrom);
			preparedStatement.setString(6, timeTo);
			preparedStatement.setString(7, patientForm.getDiagnosis());
			preparedStatement.setString(8, patientForm.getMedicalNotes());
			preparedStatement.setString(9, ActivityStatus.ACTIVE);
			preparedStatement.setInt(10, patientForm.getPatientID());
			preparedStatement.setInt(11, newVisitRef);
			preparedStatement.setInt(12, patientForm.getNextVisitDays());
			preparedStatement.setInt(13, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

			/*
			 * Updating status of appointment to Completed
			 */
			completeAppointment(patientForm.getAptID());

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deletePresc(int)
	 */
	public String deletePresc(int visitID) {
		try {
			connection = getConnection();

			String deletePrescQuery = QueryMaker.DELETE_PRESCRIPTION;

			preparedStatement = connection.prepareStatement(deletePrescQuery);
			preparedStatement.setInt(1, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted PRescription details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting prescription details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteVisit(int)
	 */
	public String deleteVisit(int visitID) {
		try {
			connection = getConnection();

			String deleteVisitQuery = QueryMaker.DELETE_VISIT;

			preparedStatement = connection.prepareStatement(deleteVisitQuery);
			preparedStatement.setInt(1, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted visit details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting visit details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteOPDVisit(int)
	 */
	public String deleteOPDVisit(int visitID) {
		try {
			connection = getConnection();

			String deleteOPDVisitQuery = QueryMaker.DELETE_OPD_VISIT;

			preparedStatement = connection.prepareStatement(deleteOPDVisitQuery);
			preparedStatement.setInt(1, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted OPD visit details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting OPD visit details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteOpticianDetails(int)
	 */
	public String deleteOpticianDetails(int visitID) {
		try {
			connection = getConnection();

			String deleteOpticianDetailsQuery = QueryMaker.DELETE_OPTICIAN;

			preparedStatement = connection.prepareStatement(deleteOpticianDetailsQuery);
			preparedStatement.setInt(1, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted Optician details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Optician details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteEyewearDetails(int)
	 */
	public String deleteEyewearDetails(int opticianID) {
		try {
			connection = getConnection();

			String deleteEyewearDetailsQuery = QueryMaker.DELETE_EYEWEAR;

			preparedStatement = connection.prepareStatement(deleteEyewearDetailsQuery);
			preparedStatement.setInt(1, opticianID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted Eyewear details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Eyewear details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteOpticianOldGlassesDetails
	 * (int)
	 */
	public String deleteOpticianOldGlassesDetails(int opticianID) {
		try {
			connection = getConnection();

			String deleteOpticianOldGlassesDetailsQuery = QueryMaker.DELETE_OPTICIAN_OLD_GLASSESS;

			preparedStatement = connection.prepareStatement(deleteOpticianOldGlassesDetailsQuery);
			preparedStatement.setInt(1, opticianID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully deleted Optician Old glasses details from table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Optician old glasses details due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientID()
	 */
	public int retrievePatientID() {
		int patientID = 0;

		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred patient ID due to:::" + exception.getMessage());
		}
		return patientID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOpticianDetails(int)
	 */
	public List<PatientForm> retrieveLastOpticianDetails(int visitID) {
		PatientForm patientForm = new PatientForm();
		List<PatientForm> list = new ArrayList<PatientForm>();
		int check = 0;

		try {
			connection = getConnection();

			String retrieveLastOpticianDetailsQuery = QueryMaker.RETRIEVE_LAST_OPTICIAN_LIST;

			preparedStatement = connection.prepareStatement(retrieveLastOpticianDetailsQuery);
			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				patientForm.setSphDiskOD(resultSet.getString("sphDistOD"));
				patientForm.setSphDiskOS(resultSet.getString("sphDistOS"));
				patientForm.setSphNearOD(resultSet.getString("sphNearOD"));
				patientForm.setSphNearOS(resultSet.getString("sphNearOS"));
				patientForm.setCylDiskOD(resultSet.getString("cylDistOD"));
				patientForm.setCylDiskOS(resultSet.getString("cylDistOS"));
				patientForm.setCylNearOD(resultSet.getString("cylNearOD"));
				patientForm.setCylNearOS(resultSet.getString("cylNearOS"));
				patientForm.setAxisDiskOD(resultSet.getString("axisDistOD"));
				patientForm.setAxisDiskOS(resultSet.getString("axisDistOS"));
				patientForm.setAxisNearOD(resultSet.getString("axisNearOD"));
				patientForm.setAxisNearOS(resultSet.getString("axisNearOS"));
				patientForm.setVnDiskOD(resultSet.getString("vnDistOD"));
				patientForm.setVnDiskOS(resultSet.getString("vnDistOS"));
				patientForm.setVnNearOD(resultSet.getString("vnNearOD"));
				patientForm.setVnNearOS(resultSet.getString("vnNearOS"));
				patientForm.setOptomeryComment(resultSet.getString("comments"));
				patientForm.setSpectacleComments(resultSet.getString("spectacleComments"));

				if (resultSet.getString("spectacleComments") == null) {
					patientForm.setDefaultSpectacleComments(null);
				} else {
					patientForm.setDefaultSpectacleComments(resultSet.getString("spectacleComments").split(", "));
				}

				list.add(patientForm);
			}

			if (check == 0) {
				patientForm.setSphDiskOD(" ");
				patientForm.setSphDiskOS(" ");
				patientForm.setSphNearOD(" ");
				patientForm.setSphNearOS(" ");
				patientForm.setCylDiskOD(" ");
				patientForm.setCylDiskOS(" ");
				patientForm.setCylNearOD(" ");
				patientForm.setCylNearOS(" ");
				patientForm.setAxisDiskOD(" ");
				patientForm.setAxisDiskOS(" ");
				patientForm.setAxisNearOD(" ");
				patientForm.setAxisNearOS(" ");
				patientForm.setVnDiskOD(" ");
				patientForm.setVnDiskOS(" ");
				patientForm.setVnNearOD(" ");
				patientForm.setVnNearOS(" ");

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred patient ID due to:::" + exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOPDVisitID(int)
	 */
	public int retrieveLastOPDVisitID(int patientID, int clinicID) {
		int visitID = 0;

		try {
			connection = getConnection();

			String retrieveLastOPDVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}
	
	public int retrieveLastOPDVisitIDByCareType(int patientID, int clinicID, String careType) {
		int visitID = 0;

		try {
			connection = getConnection();

			String retrieveLastOPDVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, careType);
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastIPDVisitID(int)
	 */
	public int retrieveLastIPDVisitID(int patientID, int clinicID) {
		int visitID = 0;

		try {
			connection = getConnection();

			String retrieveLastIPDVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastIPDVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "IPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveOpticianID(int)
	 */
	public int retrieveOpticianID(int visitID) {
		int opticianID = 0;

		try {
			connection1 = getConnection();

			String retrieveOpticianIDQuery = QueryMaker.RETRIEVE_LAST_OPTICIAN_ID;

			preparedStatement6 = connection1.prepareStatement(retrieveOpticianIDQuery);
			preparedStatement6.setInt(1, visitID);

			resultSet6 = preparedStatement6.executeQuery();

			while (resultSet6.next()) {
				opticianID = resultSet6.getInt("id");
			}

			resultSet6.close();
			preparedStatement6.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred optician ID due to:::" + exception.getMessage());
		}
		return opticianID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateOpticianDetails(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updateOpticianDetails(PatientForm form) {
		try {
			connection = getConnection();

			String insertOpticianDetailsQuery = QueryMaker.UPDATE_OPTICIAN_DETAILS;

			preparedStatement = connection.prepareStatement(insertOpticianDetailsQuery);

			preparedStatement.setString(1, form.getSphNearOD());
			preparedStatement.setString(2, form.getSphNearOS());
			preparedStatement.setString(3, form.getSphDiskOD());
			preparedStatement.setString(4, form.getSphDiskOS());
			preparedStatement.setString(5, form.getCylNearOD());
			preparedStatement.setString(6, form.getCylNearOS());
			preparedStatement.setString(7, form.getCylDiskOD());
			preparedStatement.setString(8, form.getCylDiskOS());
			preparedStatement.setString(9, form.getAxisNearOD());
			preparedStatement.setString(10, form.getAxisNearOS());
			preparedStatement.setString(11, form.getAxisDiskOD());
			preparedStatement.setString(12, form.getAxisDiskOS());
			preparedStatement.setString(13, form.getVnNearOD());
			preparedStatement.setString(14, form.getVnNearOS());
			preparedStatement.setString(15, form.getVnDiskOD());
			preparedStatement.setString(16, form.getVnDiskOS());
			preparedStatement.setString(17, form.getOptomeryComment());
			preparedStatement.setString(18, form.getSpectacleComments());
			preparedStatement.setInt(19, form.getOpticinID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully updated Optician details...");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating optician details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateOpticianOldGlassDetails(com
	 * .edhanvantari.form.PatientForm)
	 */
	public String updateOpticianOldGlassDetails(PatientForm form) {
		try {
			connection = getConnection();

			String insertOpticianOldGlassDetailsQuery = QueryMaker.UPDATE_OPTICIAN_OLD_GLASS_DETAILS;

			preparedStatement = connection.prepareStatement(insertOpticianOldGlassDetailsQuery);

			preparedStatement.setString(1, form.getOldSphNearOD());
			preparedStatement.setString(2, form.getOldSphNearOS());
			preparedStatement.setString(3, form.getOldSphDiskOD());
			preparedStatement.setString(4, form.getOldSphDiskOS());
			preparedStatement.setString(5, form.getOldCylNearOD());
			preparedStatement.setString(6, form.getOldCylNearOS());
			preparedStatement.setString(7, form.getOldCylDiskOD());
			preparedStatement.setString(8, form.getOldCylDiskOS());
			preparedStatement.setString(9, form.getOldAxisNearOD());
			preparedStatement.setString(10, form.getOldAxisNearOS());
			preparedStatement.setString(11, form.getOldAxisDiskOD());
			preparedStatement.setString(12, form.getOldAxisDiskOS());
			preparedStatement.setString(13, form.getOldVnNearOD());
			preparedStatement.setString(14, form.getOldVnNearOS());
			preparedStatement.setString(15, form.getOldVnDiskOD());
			preparedStatement.setString(16, form.getOldVnDiskOS());
			preparedStatement.setInt(17, form.getOpticinID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated Optician Old glasses details...");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while udpating optician old glasses details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertEyewearDetails(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertEyewearDetails(PatientForm form) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertEyewearDetailsQuery = QueryMaker.INSERT_EYEWEAR_DETAILS;

			preparedStatement = connection.prepareStatement(insertEyewearDetailsQuery);

			preparedStatement.setString(1, form.getTint());
			preparedStatement.setString(2, form.getGlass());
			preparedStatement.setString(3, form.getMaterial());
			preparedStatement.setString(4, form.getClinic());
			preparedStatement.setString(5, form.getFrame());
			preparedStatement.setDouble(6, form.getFrameCharge());
			preparedStatement.setDouble(7, form.getGlassCharge());
			preparedStatement.setDouble(8, form.getDiscount());
			preparedStatement.setDouble(9, form.getNetPayment());
			preparedStatement.setDouble(10, form.getAdvance());
			preparedStatement.setDouble(11, form.getBalance());
			preparedStatement.setDouble(12, form.getBalancePaid());
			preparedStatement.setString(13, form.getOptDiscountType());

			/*
			 * Converting date
			 */
			System.out.println("Balance paid date ::: " + form.getBalancePaidDate());

			if (form.getBalancePaidDate() == "" || form.getBalancePaidDate() == null) {

				form.setBalancePaidDate(null);
				preparedStatement.setString(13, form.getBalancePaidDate());

			} else if (form.getBalancePaidDate().isEmpty()) {

				form.setBalancePaidDate(null);
				preparedStatement.setString(13, form.getBalancePaidDate());

			} else {

				date1 = dateToBeParsed123.parse(form.getBalancePaidDate());

				preparedStatement.setString(13, dateToBeFormatted.format(date1));

			}

			preparedStatement.setInt(14, form.getOpticinID());
			preparedStatement.setString(15, form.getDiscountType());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully inserted Eyewear details...");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Eyewear details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOLDGlasses(int)
	 */
	public JSONObject retrieveLastOLDGlasses(int opticianID) {

		JSONObject values = null;
		int check = 0;
		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		String oldSphDiskOD = null;
		String oldCylDiskOD = null;
		String oldAxisDiskOD = null;
		String oldVnDiskOD = null;
		String oldSphDiskOS = null;
		String oldCylDiskOS = null;
		String oldAxisDiskOS = null;
		String oldVnDiskOS = null;
		String oldSphNearOD = null;
		String oldCylNearOD = null;
		String oldAxisNearOD = null;
		String oldVnNearOD = null;
		String oldSphNearOS = null;
		String oldCylNearOS = null;
		String oldAxisNearOS = null;
		String oldVnNearOS = null;

		try {
			connection = getConnection();

			String retrieveLastOLDGlassesQuery = QueryMaker.RETRIEVE_LAST_OLD_GLASSES_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveLastOLDGlassesQuery);

			preparedStatement.setInt(1, opticianID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				oldSphDiskOD = resultSet.getString("sphDistOD");
				oldCylDiskOD = resultSet.getString("cylDistOD");
				oldAxisDiskOD = resultSet.getString("axisDistOD");
				oldVnDiskOD = resultSet.getString("vnDistOD");
				oldSphDiskOS = resultSet.getString("sphDistOS");
				oldCylDiskOS = resultSet.getString("cylDistOS");
				oldAxisDiskOS = resultSet.getString("axisDistOS");
				oldVnDiskOS = resultSet.getString("vnDistOS");
				oldSphNearOD = resultSet.getString("sphNearOD");
				oldCylNearOD = resultSet.getString("cylNearOD");
				oldAxisNearOD = resultSet.getString("axisNearOD");
				oldVnNearOD = resultSet.getString("vnNearOD");
				oldSphNearOS = resultSet.getString("sphNearOS");
				oldCylNearOS = resultSet.getString("cylNearOS");
				oldAxisNearOS = resultSet.getString("axisNearOS");
				oldVnNearOS = resultSet.getString("vnNearOS");

				object.put("oldSphDiskOD", oldSphDiskOD);
				object.put("oldCylDiskOD", oldCylDiskOD);
				object.put("oldAxisDiskOD", oldAxisDiskOD);
				object.put("oldVnDiskOD", oldVnDiskOD);
				object.put("oldSphDiskOS", oldSphDiskOS);
				object.put("oldCylDiskOS", oldCylDiskOS);
				object.put("oldAxisDiskOS", oldAxisDiskOS);
				object.put("oldVnDiskOS", oldVnDiskOS);
				object.put("oldSphNearOD", oldSphNearOD);
				object.put("oldCylNearOD", oldCylNearOD);
				object.put("oldAxisNearOD", oldAxisNearOD);
				object.put("oldVnNearOD", oldVnNearOD);
				object.put("oldSphNearOS", oldSphNearOS);
				object.put("oldCylNearOS", oldCylNearOS);
				object.put("oldAxisNearOS", oldAxisNearOS);
				object.put("oldVnNearOS", oldVnNearOS);
				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {
				object = new JSONObject();
				object.put("check", check);
				array.add(object);
				values.put("Release", array);
			}

			System.out.println("Successfully fetched previous old glasses values...");

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting billing details into table due to:::" + exception.getMessage());

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertMedicalCertificate(java.lang
	 * .String)
	 */
	public String updateMedicalCertificate(String medicalCertiText, int visitID) {
		try {

			connection = getConnection();

			String insertMedicalCertificateQuery = QueryMaker.INSERT_MEDICAL_CERTIFICATE;

			preparedStatement = connection.prepareStatement(insertMedicalCertificateQuery);
			preparedStatement.setString(1, medicalCertiText);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully updated medical certificate into table.");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting medical Certificate details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateReferralLetter(String referralLetterText, int visitID) {
		try {

			connection = getConnection();

			String insertReferralLetterQuery = QueryMaker.UPDATE_REFERRAL_LETTER;

			preparedStatement = connection.prepareStatement(insertReferralLetterQuery);
			preparedStatement.setString(1, referralLetterText);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully updated referral letter into table.");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting referral letter details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertdummyMedicalDocument(int)
	 */
	public String insertdummyMedicalDocument(int visitID) {
		try {
			connection = getConnection();

			String insertdummyMedicalDocument = QueryMaker.INSERT_DUMMY_MEDICAL_DOCUMENT;

			preparedStatement = connection.prepareStatement(insertdummyMedicalDocument);
			preparedStatement.setInt(1, visitID);

			preparedStatement.execute();

			System.out.println("Successfully inserted dummy medical document details..");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting dummy medical document details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteDummyMedicalDocument(int)
	 */
	public String deleteDummyMedicalDocument(int visitID) {

		try {
			connection = getConnection();

			String deleteDummyMedicalDocumentQuery = QueryMaker.DELETE_DUMMY_MEDICAL_DOCUMENT;

			preparedStatement = connection.prepareStatement(deleteDummyMedicalDocumentQuery);
			preparedStatement.setInt(1, visitID);

			preparedStatement.execute();

			System.out.println("Successfully deleted dummy medical document details..");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting dummy medical document details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveMedicalCertificate(int)
	 */
	public String retrieveMedicalCertificate(int visitID) {
		String medicalCertificate = null;
		try {
			connection = getConnection();

			String retrieveMedicalCertificateQuery = QueryMaker.RETRIEVE_MEDICAL_CERTIFICATE;

			preparedStatement = connection.prepareStatement(retrieveMedicalCertificateQuery);
			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				medicalCertificate = resultSet.getString("medicalCertificate");
				System.out.println("inside while medical cert::" + medicalCertificate);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("medical cert retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving medical certificate from table due to:::"
					+ exception.getMessage());
		}
		return medicalCertificate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertReferralLetter(java.lang.
	 * String, int)
	 */
	public String insertReferralLetter(String referralLetterText, int visitID) {
		try {

			System.out.println("visit id is.. " + visitID);
			System.out.println("inside insert reff function.. ");
			System.out.println("inside insert reff function.. " + referralLetterText);
			connection = getConnection();

			String insertReferralLetterQuery = QueryMaker.INSERT_REFERRAL_LETTER;

			preparedStatement = connection.prepareStatement(insertReferralLetterQuery);

			preparedStatement.setString(1, referralLetterText);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully inserted referral letter into table.");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting referral letter details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveReferralLetter(int)
	 */
	public String retrieveReferralLetter(int visitID) {
		String referralLetterText = null;
		try {
			connection = getConnection();

			String retrieveReferralLetterQuery = QueryMaker.RETRIEVE_REFERRAL_LETTER;

			preparedStatement = connection.prepareStatement(retrieveReferralLetterQuery);
			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				referralLetterText = resultSet.getString("referralLetter");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Ref letter retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving referral letter from table due to:::" + exception.getMessage());

		}
		return referralLetterText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertIPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String insertIPDVisit(PatientForm patientForm) {

		System.out.println("insert IPD Visit");

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed = new SimpleDateFormat("MM/dd/yyyy");

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		try {
			connection = getConnection();

			String insertIPDVisitQuery = QueryMaker.INSERT_IPD_VISIT;

			preparedStatement = connection.prepareStatement(insertIPDVisitQuery);

			/*
			 * Converting date
			 */
			if (patientForm.getVisitDate().substring(2, 3).equals("-")) {
				date = dateToBeParsed123.parse(patientForm.getVisitDate());
			} else {
				date = dateToBeParsed.parse(patientForm.getVisitDate());
			}

			preparedStatement.setString(1, patientForm.getEyeLidUpperOD());
			preparedStatement.setString(2, patientForm.getEyeLidUpperOS());
			preparedStatement.setString(3, patientForm.getEyeLidLowerOD());
			preparedStatement.setString(4, patientForm.getEyeLidLowerOS());
			preparedStatement.setString(5, patientForm.getVisualAcuityDistOD());
			preparedStatement.setString(6, patientForm.getVisualAcuityDistOS());
			preparedStatement.setString(7, patientForm.getVisualAcuityNearOD());
			preparedStatement.setString(8, patientForm.getVisualAcuityNearOS());
			preparedStatement.setString(9, patientForm.getPinholeVisionDistOD());
			preparedStatement.setString(10, patientForm.getPinholeVisionDistOS());
			preparedStatement.setString(11, patientForm.getPinholeVisionNearOD());
			preparedStatement.setString(12, patientForm.getPinholeVisionNearOS());
			preparedStatement.setString(13, patientForm.getBCVADistOD());
			preparedStatement.setString(14, patientForm.getBCVADistOS());
			preparedStatement.setString(15, patientForm.getBCVANearOD());
			preparedStatement.setString(16, patientForm.getBCVANearOS());
			preparedStatement.setString(17, patientForm.getConjunctivaOD());
			preparedStatement.setString(18, patientForm.getConjunctivaOS());
			preparedStatement.setString(19, patientForm.getCorneaOD());
			preparedStatement.setString(20, patientForm.getCorneaOS());
			preparedStatement.setString(21, patientForm.getPupilOD());
			preparedStatement.setString(22, patientForm.getPupilOS());
			preparedStatement.setString(23, patientForm.getACOD());
			preparedStatement.setString(24, patientForm.getACOS());
			preparedStatement.setString(25, patientForm.getIrisOD());
			preparedStatement.setString(26, patientForm.getIrisOS());
			preparedStatement.setString(27, patientForm.getLensOD());
			preparedStatement.setString(28, patientForm.getLensOS());
			preparedStatement.setString(29, patientForm.getDiscOD());
			preparedStatement.setString(30, patientForm.getDiscOS());
			preparedStatement.setString(31, patientForm.getVesselOD());
			preparedStatement.setString(32, patientForm.getVesselOS());
			preparedStatement.setString(33, patientForm.getMaculaOD());
			preparedStatement.setString(34, patientForm.getMaculaOS());
			preparedStatement.setString(35, patientForm.getIODOD());
			preparedStatement.setString(36, patientForm.getIODOS());
			preparedStatement.setString(37, patientForm.getSacOD());
			preparedStatement.setString(38, patientForm.getSacOS());
			preparedStatement.setString(39, patientForm.getK1OD());
			preparedStatement.setString(40, patientForm.getK1OS());
			preparedStatement.setString(41, patientForm.getK2OD());
			preparedStatement.setString(42, patientForm.getK2OS());
			preparedStatement.setString(43, patientForm.getAxialLengthOD());
			preparedStatement.setString(44, patientForm.getAxialLengthOS());
			preparedStatement.setString(45, patientForm.getIOLOD());
			preparedStatement.setString(46, patientForm.getIOLOS());
			preparedStatement.setString(47, dateToBeFormatted.format(date));
			preparedStatement.setString(48, patientForm.getHo());
			preparedStatement.setString(49, patientForm.getAllergicTo());
			preparedStatement.setInt(50, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully inserted IPD details into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting IPD visit details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertOTNOtes(com.edhanvantari.
	 * form.PatientForm)
	 */
	public String insertOTNOtes(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertOTNOtesQuery = QueryMaker.INSERT_OT_NOTES;

			preparedStatement = connection.prepareStatement(insertOTNOtesQuery);

			preparedStatement.setString(1, patientForm.getOTNotes());
			preparedStatement.setInt(2, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully inserted OT NOtes into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting OT NOtes details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertInvestigations(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertInvestigations(PatientForm patientForm, String panelTestvalue, String value) {

		boolean isNumber = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		try {
			connection = getConnection();

			String insertInvestigationsQuery = QueryMaker.INSERT_LAB_INVESTIGATION;

			preparedStatement = connection.prepareStatement(insertInvestigationsQuery);

			preparedStatement.setString(1, panelTestvalue);
			preparedStatement.setString(2, panelTestvalue);

			String val = "";

			if (value == null) {
				val = "";
			} else if (value.isEmpty()) {
				val = "";
			} else if (value.equals("null")) {
				val = "";
			} else {
				val = value;
			}

			/*
			 * Checking whether value is quantitative or qualitative by checking whether
			 * value is numeric or string, if numeric then it is quantitative else
			 * qualitative
			 */
			isNumber = isNumeric(val);

			if (isNumber) {

				preparedStatement.setString(3, "");
				preparedStatement.setDouble(4, Double.parseDouble(val));

			} else {

				preparedStatement.setString(3, val);
				preparedStatement.setDouble(4, 0D);

			}

			preparedStatement.setInt(5, patientForm.getVisitID());

			preparedStatement.setString(6, dateFormat1.format(date));

			preparedStatement.execute();

			System.out.println("Successfully inserted investigation into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while investigation details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNumeric(String value) {

		boolean check = false;

		try {

			Double.parseDouble(value);

			check = true;

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			check = false;
		}

		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertOE(com.edhanvantari.form.
	 * PatientForm)
	 */
	public String insertOE(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertOEQuery = QueryMaker.INSERT_OE;

			preparedStatement = connection.prepareStatement(insertOEQuery);

			preparedStatement.setInt(1, patientForm.getOEPulse());
			preparedStatement.setInt(2, patientForm.getOEBPSys());
			preparedStatement.setInt(3, patientForm.getOEBPDia());
			preparedStatement.setString(4, patientForm.getOERS());
			preparedStatement.setString(5, patientForm.getOECVS());
			preparedStatement.setInt(6, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully inserted OE into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting OE details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveIPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public List<PatientForm> retrieveIPDVisit(PatientForm patientForm) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveIPDContinuationSheetList
	 * (int)
	 */
	public List<PatientForm> retrieveIPDContinuationSheetList(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {
			connection = getConnection();

			String retrieveIPDContinuationSheetListQuery = QueryMaker.RETRIEVE_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(retrieveIPDContinuationSheetListQuery);
			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setCountinuationSheetDate(resultSet.getString("contDate"));
				patientForm.setCountinuationSheetDescription(resultSet.getString("description"));
				patientForm.setCountinuationSheetTeatment(resultSet.getString("treatment"));
				patientForm.setContinuationSheetID(resultSet.getInt("id"));

				list.add(patientForm);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD Continuation sheet details from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveIPDComplaintsList(int)
	 */
	public List<PatientForm> retrieveIPDComplaintsList(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;
		int count = 1;

		try {
			connection = getConnection();

			String retrieveIPDComplaintsListQuery = QueryMaker.RETRIEVE_IPD_COMPLAINTS;

			preparedStatement = connection.prepareStatement(retrieveIPDComplaintsListQuery);
			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setComplaintID(resultSet.getInt("id"));
				patientForm.setOSLt1(resultSet.getString("complaintOD"));
				patientForm.setODRt1(resultSet.getString("complaintOS"));
				patientForm.setCount(String.valueOf(count));

				list.add(patientForm);

				count++;

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD complaints details from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveIPDComplaints(int)
	 */
	public JSONObject retrieveIPDComplaints(int visitID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveIPDComplaintsQuery = QueryMaker.RETRIEVE_IPD_COMPLAINTS;

			preparedStatement = connection.prepareStatement(retrieveIPDComplaintsQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("complaintID", resultSet.getInt("id"));
				object.put("complaintOD", resultSet.getString("complaintOD"));
				object.put("complaintOS", resultSet.getString("complaintOS"));
				object.put("visitID", resultSet.getInt("visitID"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD complaint details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve IPD complaint details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertIPDContinuationSheet(com.
	 * edhanvantari.form.PatientForm)
	 */
	public JSONObject insertIPDContinuationSheet(PatientForm patientForm) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String insertIPDContinuationSheetQuery = QueryMaker.INSERT_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(insertIPDContinuationSheetQuery);

			preparedStatement.setString(1, patientForm.getCountinuationSheetDate());
			preparedStatement.setString(2, patientForm.getCountinuationSheetDescription());
			preparedStatement.setString(3, patientForm.getCountinuationSheetTeatment());
			preparedStatement.setInt(4, patientForm.getVisitID());

			preparedStatement.execute();

			object.put("SuccessMessage", "New IPD continuation sheet added successfully. ");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting IPD continuation sheet details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage",
					"Failed to add new IPD continuation sheet. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveIPDContinuationSheet(int)
	 */
	public JSONObject retrieveIPDContinuationSheet(int visitID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveIPDContinuationSheetQuery = QueryMaker.RETRIEVE_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(retrieveIPDContinuationSheetQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("continuationID", resultSet.getInt("id"));
				object.put("continuationDate", resultSet.getString("contDate"));
				object.put("continuationDesc", resultSet.getString("description"));
				object.put("continuationTreat", resultSet.getString("treatment"));
				object.put("visitID", resultSet.getInt("visitID"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD continuation Sheet details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve IPD continuation Sheet details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteIPDComplaints(int)
	 */
	public JSONObject deleteIPDComplaints(int complaintID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String deleteIPDComplaintsQuery = QueryMaker.DELETE_IPD_COMPLAINTS;

			preparedStatement = connection.prepareStatement(deleteIPDComplaintsQuery);

			preparedStatement.setInt(1, complaintID);

			preparedStatement.executeUpdate();

			object.put("SuccessMessage", "IPD Complaint deleted successfully.");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting IPD Complaint details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to delete IPD Complaint.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#deleteIPDContinuationSheet(int)
	 */
	public JSONObject deleteIPDContinuationSheet(int continuationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String deleteIPDContinuationSheetQuery = QueryMaker.DELETE_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(deleteIPDContinuationSheetQuery);

			preparedStatement.setInt(1, continuationID);

			preparedStatement.executeUpdate();

			object.put("SuccessMessage", "IPD continuation sheet deleted successfully.");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting IPD continuation sheet details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to delete IPD continuation sheet.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveVisitIDByVisitNumber(int)
	 */
	public int retrieveVisitIDByVisitNumber(int visitNumber) {
		int visitID = 0;

		try {

			connection = getConnection();

			String retrieveVisitIDByVisitNumberQuery = QueryMaker.RETRIEVE_VISIT_ID_BY_VISIT_NUMBER;

			preparedStatement = connection.prepareStatement(retrieveVisitIDByVisitNumberQuery);

			preparedStatement.setInt(1, visitNumber);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visitID based on visitnUmber from table due to:::"
					+ exception.getMessage());

		}
		return visitID;
	}

	public List<PatientForm> retrieveLastOPDVisitForIPD(int patientID, int visitID, int lastVisitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = new PatientForm();

		try {
			connection = getConnection();

			String retrieveExistingVisitListQuery = QueryMaker.RETRIEVE_EXISTING_VISIT_LIST;
			String retrievePatientDetailQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS;
			// String retriveOPDVisitQuery =
			// QueryMaker.RETRIEVE_OPD_VISIT_DETAILS;

			/*
			 * For retrieving existing visit list
			 */
			preparedStatement = connection.prepareStatement(retrieveExistingVisitListQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setAptID(resultSet.getInt("apptID"));

			}

			/*
			 * For retrieving Patient Details
			 */
			preparedStatement3 = connection.prepareStatement(retrievePatientDetailQuery);
			preparedStatement3.setInt(1, patientID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {
				patientForm.setPatientID(patientID);
				patientForm.setFirstName(resultSet3.getString("firstName"));
				patientForm.setLastName(resultSet3.getString("lastName"));
				patientForm.setMiddleName(resultSet3.getString("middleName"));
				patientForm.setGender(resultSet3.getString("gender"));
				patientForm.setAge(resultSet3.getString("age"));
				patientForm.setPatientID(resultSet3.getInt("id"));
				patientForm.setMobile(resultSet3.getString("mobile"));
				patientForm.setAddress(resultSet3.getString("address"));
			}

			/*
			 * For retrieving OPD Visit details
			 */
			/*
			 * preparedStatement5 = connection .prepareStatement(retriveOPDVisitQuery);
			 * preparedStatement5.setInt(1, lastVisitID);
			 * 
			 * resultSet5 = preparedStatement5.executeQuery();
			 * 
			 * while (resultSet5.next()) {
			 * 
			 * patientForm.setEyeLidLowerOD(resultSet5 .getString("eyelidLowerOD"));
			 * patientForm.setEyeLidLowerOS(resultSet5 .getString("eyelidLowerOS"));
			 * patientForm.setEyeLidUpperOD(resultSet5 .getString("eyelidUpperOD"));
			 * patientForm.setEyeLidUpperOS(resultSet5 .getString("eyelidUpperOS"));
			 * patientForm.setVisualAcuityDistOD(resultSet5
			 * .getString("visualAcuityDistOD"));
			 * patientForm.setVisualAcuityDistOS(resultSet5
			 * .getString("visualAcuityDistOS"));
			 * patientForm.setVisualAcuityNearOD(resultSet5
			 * .getString("visualAcuityNearOD"));
			 * patientForm.setVisualAcuityNearOS(resultSet5
			 * .getString("visualAcuityNearOS"));
			 * patientForm.setPinholeVisionDistOD(resultSet5
			 * .getString("pinholeVisionDistOD"));
			 * patientForm.setPinholeVisionDistOS(resultSet5
			 * .getString("pinholeVisionDistOS"));
			 * patientForm.setPinholeVisionNearOD(resultSet5
			 * .getString("pinholeVisionNearOD"));
			 * patientForm.setPinholeVisionNearOS(resultSet5
			 * .getString("pinholeVisionNearOS"));
			 * patientForm.setBCVADistOD(resultSet5.getString("bcvaDistOD"));
			 * patientForm.setBCVADistOS(resultSet5.getString("bcvaDistOS"));
			 * patientForm.setBCVANearOD(resultSet5.getString("bcvaNearOD"));
			 * patientForm.setBCVANearOS(resultSet5.getString("bcvaNearOS"));
			 * patientForm.setConjunctivaOD(resultSet5 .getString("conjunctivaOD"));
			 * patientForm.setConjunctivaOS(resultSet5 .getString("conjunctivaOS"));
			 * patientForm.setCorneaOD(resultSet5.getString("corneaOD"));
			 * patientForm.setCorneaOS(resultSet5.getString("corneaOS"));
			 * patientForm.setACOD(resultSet5.getString("anteriorChamberOD"));
			 * patientForm.setACOS(resultSet5.getString("anteriorChamberOS"));
			 * patientForm.setIrisOD(resultSet5.getString("irisOD"));
			 * patientForm.setIrisOS(resultSet5.getString("irisOS"));
			 * patientForm.setLensOD(resultSet5.getString("lensOD"));
			 * patientForm.setLensOS(resultSet5.getString("lensOS"));
			 * patientForm.setDiscOD(resultSet5.getString("discOD"));
			 * patientForm.setDiscOS(resultSet5.getString("discOS"));
			 * patientForm.setVesselOD(resultSet5.getString("vesselOD"));
			 * patientForm.setVesselOS(resultSet5.getString("vesselOS"));
			 * patientForm.setMaculaOD(resultSet5.getString("maculaOD"));
			 * patientForm.setMaculaOS(resultSet5.getString("maculaOS"));
			 * patientForm.setIODOD(resultSet5.getString("iopOD"));
			 * patientForm.setIODOS(resultSet5.getString("iopOS"));
			 * patientForm.setSacOD(resultSet5.getString("sacOD"));
			 * patientForm.setSacOS(resultSet5.getString("sacOS"));
			 * patientForm.setK1OD(resultSet5.getString("biometryK1OD"));
			 * patientForm.setK1OS(resultSet5.getString("biometryK1OS"));
			 * patientForm.setK2OD(resultSet5.getString("biometryK2OD"));
			 * patientForm.setK2OS(resultSet5.getString("biometryK2OS"));
			 * patientForm.setAxialLengthOD(resultSet5 .getString("biometryAxialLengthOD"));
			 * patientForm.setAxialLengthOS(resultSet5 .getString("biometryAxialLengthOS"));
			 * patientForm.setIOLOD(resultSet5.getString("biometryIOLOD"));
			 * patientForm.setIOLOS(resultSet5.getString("biometryIOLOS")); }
			 */

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			resultSet3.close();
			preparedStatement3.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last OPD visit for IPD from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateIPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String updateIPDVisit(PatientForm patientForm) {

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String dischargDate = null;

		try {

			if (patientForm.getDateOfDischarge() == null || patientForm.getDateOfDischarge() == "") {
				// Converting date into db format
				dischargDate = null;
			} else if (patientForm.getDateOfDischarge().isEmpty()) {
				// Converting date into db format
				dischargDate = null;
			} else {
				// Converting date into db format
				dischargDate = dateToBeFormatted.format(dateToBeParsed123.parse(patientForm.getDateOfDischarge()));
			}

			connection = getConnection();

			String updateIPDVisitQuery = QueryMaker.UPDATE_IPD_VISIT;

			preparedStatement = connection.prepareStatement(updateIPDVisitQuery);

			/*
			 * Converting date
			 */
			date = dateToBeParsed123.parse(patientForm.getVisitDate());

			preparedStatement.setString(1, patientForm.getDiagnosis());
			preparedStatement.setString(2, dateToBeFormatted.format(date));
			preparedStatement.setInt(7, patientForm.getVisitID());
			preparedStatement.setString(3, dischargDate);
			preparedStatement.setString(4, patientForm.getProcedure());
			preparedStatement.setString(5, patientForm.getAdmission_time());
			preparedStatement.setString(6, patientForm.getDischarge_time());

			preparedStatement.executeUpdate();

			System.out.println("Successfully insereted Visit for IPD");

			preparedStatement.close();

			connection.close();

			/*
			 * Updating status of appointment to Completed
			 */
			completeAppointment(patientForm.getAptID());

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last OPD visit for IPD from table due to:::"
					+ exception.getMessage());

			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveCareType(int)
	 */
	public String retrieveCareType(int visitTypeID) {
		String careType = null;
		try {

			connection = getConnection();

			String retrieveCareTypeQuery = QueryMaker.RETRIEVE_CARE_TYPE;

			preparedStatement = connection.prepareStatement(retrieveCareTypeQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				careType = resultSet.getString("careType");
			}

			System.out.println("Care type of visit " + visitTypeID + " is ::: " + careType);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving care type of visit table due to:::" + exception.getMessage());
		}
		return careType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retireveLastEneteredIPDVisit(int,
	 * int)
	 */
	public List<PatientForm> retireveLastEneteredIPDVisit(int patientID, int visitID, int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = new PatientForm();

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		try {
			connection = getConnection();

			String retrieveExistingVisitListQuery = QueryMaker.RETRIEVE_EXISTING_VISIT_LIST;
			String retrievePatientDetailQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS;
			String retreiveOpthalmologyComplaintsQuery = QueryMaker.RETRIEVE_IPD_COMPLAINTS;
			String retriveIPDVisitQuery = QueryMaker.RETRIEVE_IPD_VISIT;
			String retrieveOTNoteQuery = QueryMaker.RETRIEVE_OT_NOTES;
			// String retrieveInvestigationQuery = QueryMaker.RETRIEVE_INVESTOGATION;
			String retrieveOEQuery = QueryMaker.RETRIEVE_OE;
			String retrieveConsentQuery = QueryMaker.RETRIEVE_CONSENT1;
			String retrieveIPDContinuationSheetListQuery = QueryMaker.RETRIEVE_IPD_CONTINUATION_SHEET;

			/*
			 * For retrieving existing visit list
			 */
			preparedStatement = connection.prepareStatement(retrieveExistingVisitListQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				date = databaseDate.parse(resultSet.getString("visitDate"));

				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setVisitDate(dateToBeDisplayed.format(date));
				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setAdvice(resultSet.getString("advice"));
				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
				patientForm.setDateOfDischarge(resultSet.getString("dateOfDischarge"));
				patientForm.setProcedure(resultSet.getString("procedure"));
				patientForm.setAdmission_time(resultSet.getString("admission_time"));
				patientForm.setDischarge_time(resultSet.getString("discharge_time"));

			}

			/*
			 * For retrieving Patient Details
			 */
			preparedStatement3 = connection.prepareStatement(retrievePatientDetailQuery);
			preparedStatement3.setInt(1, patientID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {
				patientForm.setPatientID(patientID);
				patientForm.setFirstName(resultSet3.getString("firstName"));
				patientForm.setLastName(resultSet3.getString("lastName"));
				patientForm.setMiddleName(resultSet3.getString("middleName"));
				patientForm.setGender(resultSet3.getString("gender"));
				patientForm.setAge(resultSet3.getString("age"));
				patientForm.setPatientID(resultSet3.getInt("id"));
				patientForm.setMobile(resultSet3.getString("mobile"));
				patientForm.setAddress(resultSet3.getString("address"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet3.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet3.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet3.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
			}

			/*
			 * Retrieving OpthalmologyComplaints by visitID
			 */

			preparedStatement = connection.prepareStatement(retreiveOpthalmologyComplaintsQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setComplaintID(resultSet.getInt("id"));
				patientForm.setAxialLengthOD(resultSet.getString("complaintOD"));
				patientForm.setAxialLengthOS(resultSet.getString("complaintOS"));
			}

			/*
			 * For retrieving OPD Visit details
			 */
			preparedStatement5 = connection.prepareStatement(retriveIPDVisitQuery);
			preparedStatement5.setInt(1, visitID);

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {

				patientForm.setEyeLidLowerOD(resultSet5.getString("eyelidLowerOD"));
				patientForm.setEyeLidLowerOS(resultSet5.getString("eyelidLowerOS"));
				patientForm.setEyeLidUpperOD(resultSet5.getString("eyelidUpperOD"));
				patientForm.setEyeLidUpperOS(resultSet5.getString("eyelidUpperOS"));
				patientForm.setVisualAcuityDistOD(resultSet5.getString("visualAcuityDistOD"));
				patientForm.setVisualAcuityDistOS(resultSet5.getString("visualAcuityDistOS"));
				patientForm.setVisualAcuityNearOD(resultSet5.getString("visualAcuityNearOD"));
				patientForm.setVisualAcuityNearOS(resultSet5.getString("visualAcuityNearOS"));
				patientForm.setPinholeVisionDistOD(resultSet5.getString("pinholeVisionDistOD"));
				patientForm.setPinholeVisionDistOS(resultSet5.getString("pinholeVisionDistOS"));
				patientForm.setPinholeVisionNearOD(resultSet5.getString("pinholeVisionNearOD"));
				patientForm.setPinholeVisionNearOS(resultSet5.getString("pinholeVisionNearOS"));
				patientForm.setBCVADistOD(resultSet5.getString("bcvaDistOD"));
				patientForm.setBCVADistOS(resultSet5.getString("bcvaDistOS"));
				patientForm.setBCVANearOD(resultSet5.getString("bcvaNearOD"));
				patientForm.setBCVANearOS(resultSet5.getString("bcvaNearOS"));
				patientForm.setConjunctivaOD(resultSet5.getString("conjunctivaOD"));
				patientForm.setConjunctivaOS(resultSet5.getString("conjunctivaOS"));
				patientForm.setCorneaOD(resultSet5.getString("corneaOD"));
				patientForm.setCorneaOS(resultSet5.getString("corneaOS"));
				patientForm.setACOD(resultSet5.getString("anteriorChamberOD"));
				patientForm.setACOS(resultSet5.getString("anteriorChamberOS"));
				patientForm.setIrisOD(resultSet5.getString("irisOD"));
				patientForm.setIrisOS(resultSet5.getString("irisOS"));
				patientForm.setLensOD(resultSet5.getString("lensOD"));
				patientForm.setLensOS(resultSet5.getString("lensOS"));
				patientForm.setDiscOD(resultSet5.getString("discOD"));
				patientForm.setDiscOS(resultSet5.getString("discOS"));
				patientForm.setVesselOD(resultSet5.getString("vesselOD"));
				patientForm.setVesselOS(resultSet5.getString("vesselOS"));
				patientForm.setMaculaOD(resultSet5.getString("maculaOD"));
				patientForm.setMaculaOS(resultSet5.getString("maculaOS"));
				patientForm.setIODOD(resultSet5.getString("iopOD"));
				patientForm.setIODOS(resultSet5.getString("iopOS"));
				patientForm.setSacOD(resultSet5.getString("sacOD"));
				patientForm.setSacOS(resultSet5.getString("sacOS"));
				patientForm.setK1OD(resultSet5.getString("biometryK1OD"));
				patientForm.setK1OS(resultSet5.getString("biometryK1OS"));
				patientForm.setK2OD(resultSet5.getString("biometryK2OD"));
				patientForm.setK2OS(resultSet5.getString("biometryK2OS"));
				patientForm.setAxialLengthOD(resultSet5.getString("biometryAxialLengthOD"));
				patientForm.setAxialLengthOS(resultSet5.getString("biometryAxialLengthOS"));
				patientForm.setIOLOD(resultSet5.getString("biometryIOLOD"));
				patientForm.setIOLOS(resultSet5.getString("biometryIOLOS"));
				patientForm.setHo(resultSet5.getString("historyOf"));
				patientForm.setAllergicTo(resultSet5.getString("allergicTo"));
			}

			/*
			 * For retrieving OTNotes
			 */
			preparedStatement4 = connection.prepareStatement(retrieveOTNoteQuery);
			preparedStatement4.setInt(1, visitID);

			resultSet4 = preparedStatement4.executeQuery();

			while (resultSet4.next()) {
				patientForm.setOTNotes(resultSet4.getString("notes"));
			}

			/*
			 * For retrieving LabInvestigation
			 * 
			 * preparedStatement6 = connection.prepareStatement(retrieveInvestigationQuery);
			 * preparedStatement6.setInt(1, visitID);
			 * 
			 * resultSet6 = preparedStatement6.executeQuery();
			 * 
			 * while (resultSet6.next()) {
			 * patientForm.setInvertigationsHb(resultSet6.getDouble("haemogobinPercent"));
			 * patientForm.setInvertigationsWBC(resultSet6.getDouble("wbcCount"));
			 * patientForm.setInvertigationsBT(resultSet6.getDouble("bt"));
			 * patientForm.setInvertigationsCT(resultSet6.getDouble("ct"));
			 * patientForm.setInvertigationsF(resultSet6.getDouble("bloodSugarLevelFasting")
			 * );
			 * patientForm.setInvertigationsPP(resultSet6.getDouble("bloodSugarLevelPP"));
			 * patientForm.setInvertigationsUrine(resultSet6.getString(
			 * "urineRoutineAndMicroscopy")); }
			 */

			/*
			 * For retrieving Vital Signs
			 */
			preparedStatement7 = connection.prepareStatement(retrieveOEQuery);
			preparedStatement7.setInt(1, visitID);

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {
				patientForm.setOEPulse(resultSet7.getInt("pulse"));
				patientForm.setOEBPSys(resultSet7.getInt("systolicBP"));
				patientForm.setOEBPDia(resultSet7.getInt("diastolicBP"));
				patientForm.setOERS(resultSet7.getString("respiratorySystem"));
				patientForm.setOECVS(resultSet7.getString("cardioVascularSystem"));
			}

			preparedStatement9 = connection.prepareStatement(retrieveConsentQuery);
			preparedStatement9.setInt(1, visitID);

			resultSet9 = preparedStatement9.executeQuery();

			while (resultSet9.next()) {

				patientForm.setConsentFileDBName(resultSet9.getString("consentDocument"));
			}

			/*
			 * For retrieving continuation sheet details
			 */
			preparedStatement8 = connection.prepareStatement(retrieveIPDContinuationSheetListQuery);
			preparedStatement8.setInt(1, visitID);

			resultSet8 = preparedStatement8.executeQuery();

			while (resultSet8.next()) {

				if (resultSet8.getString("contDate") == "" || resultSet8.getString("contDate") == null) {

					patientForm.setCountinuationSheetDate(null);
				} else if (resultSet8.getString("contDate").isEmpty()) {

					patientForm.setCountinuationSheetDate(null);
				} else {

					patientForm.setCountinuationSheetDate(
							dateToBeDisplayed.format(databaseDate.parse(resultSet8.getString("contDate"))));
				}

				patientForm.setCountinuationSheetDescription(resultSet8.getString("description"));
				patientForm.setCountinuationSheetTeatment(resultSet8.getString("treatment"));
				patientForm.setContinuationSheetID(resultSet8.getInt("id"));

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			resultSet3.close();
			preparedStatement3.close();

			resultSet5.close();
			preparedStatement5.close();

			resultSet4.close();
			preparedStatement4.close();

			/*
			 * resultSet6.close(); preparedStatement6.close();
			 */

			resultSet7.close();
			preparedStatement7.close();

			resultSet9.close();
			preparedStatement9.close();

			resultSet8.close();
			preparedStatement8.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last OPD visit for IPD from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateInvestigation(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String updateInvestigation(int invastigationID, String value) {

		boolean isNumber = false;

		try {
			connection = getConnection();

			String updateInvestigationQuery = QueryMaker.UPDATE_LAB_INVESTIGATION;

			preparedStatement = connection.prepareStatement(updateInvestigationQuery);

			String val = "";

			if (value == null) {
				val = "";
			} else if (value.isEmpty()) {
				val = "";
			} else if (value.equals("null")) {
				val = "";
			} else {
				val = value;
			}

			/*
			 * Checking whether value is quantitative or qualitative by checking whether
			 * value is numeric or string, if numeric then it is quantitative else
			 * qualitative
			 */

			isNumber = isNumeric(val);

			if (isNumber) {

				preparedStatement.setString(1, "");
				preparedStatement.setDouble(2, Double.parseDouble(val));

			} else {

				preparedStatement.setString(1, val);
				preparedStatement.setDouble(2, 0D);

			}

			preparedStatement.setInt(3, invastigationID);

			preparedStatement.execute();

			System.out.println("Successfully udpated investigation into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating investigation details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateOE(com.edhanvantari.form.
	 * PatientForm)
	 */
	public String updateOE(PatientForm patientForm) {

		try {
			connection = getConnection();

			String updateOEQuery = QueryMaker.UPDATE_OE;

			preparedStatement = connection.prepareStatement(updateOEQuery);

			preparedStatement.setInt(1, patientForm.getOEPulse());
			preparedStatement.setInt(2, patientForm.getOEBPSys());
			preparedStatement.setInt(3, patientForm.getOEBPDia());
			preparedStatement.setString(4, patientForm.getOERS());
			preparedStatement.setString(5, patientForm.getOECVS());
			preparedStatement.setInt(6, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully udpated OE into table");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while udpating OE details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateOEIPDVisit(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String updateOEIPDVisit(PatientForm patientForm) {

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		try {
			connection = getConnection();

			String updateOEIPDVisitQuery = QueryMaker.UPDATE_IPD_VISIT_DETAILS;

			preparedStatement = connection.prepareStatement(updateOEIPDVisitQuery);

			date = dateToBeParsed123.parse(patientForm.getVisitDate());

			preparedStatement.setString(1, patientForm.getEyeLidUpperOD());
			preparedStatement.setString(2, patientForm.getEyeLidUpperOS());
			preparedStatement.setString(3, patientForm.getEyeLidLowerOD());
			preparedStatement.setString(4, patientForm.getEyeLidLowerOS());
			preparedStatement.setString(5, patientForm.getVisualAcuityDistOD());
			preparedStatement.setString(6, patientForm.getVisualAcuityDistOS());
			preparedStatement.setString(7, patientForm.getVisualAcuityNearOD());
			preparedStatement.setString(8, patientForm.getVisualAcuityNearOS());
			preparedStatement.setString(9, patientForm.getPinholeVisionDistOD());
			preparedStatement.setString(10, patientForm.getPinholeVisionDistOS());
			preparedStatement.setString(11, patientForm.getPinholeVisionNearOD());
			preparedStatement.setString(12, patientForm.getPinholeVisionNearOS());
			preparedStatement.setString(13, patientForm.getBCVADistOD());
			preparedStatement.setString(14, patientForm.getBCVADistOS());
			preparedStatement.setString(15, patientForm.getBCVANearOD());
			preparedStatement.setString(16, patientForm.getBCVANearOS());
			preparedStatement.setString(17, patientForm.getConjunctivaOD());
			preparedStatement.setString(18, patientForm.getConjunctivaOS());
			preparedStatement.setString(19, patientForm.getCorneaOD());
			preparedStatement.setString(20, patientForm.getCorneaOS());
			preparedStatement.setString(21, patientForm.getPupilOD());
			preparedStatement.setString(22, patientForm.getPupilOS());
			preparedStatement.setString(23, patientForm.getACOD());
			preparedStatement.setString(24, patientForm.getACOS());
			preparedStatement.setString(25, patientForm.getIrisOD());
			preparedStatement.setString(26, patientForm.getIrisOS());
			preparedStatement.setString(27, patientForm.getLensOD());
			preparedStatement.setString(28, patientForm.getLensOS());
			preparedStatement.setString(29, patientForm.getDiscOD());
			preparedStatement.setString(30, patientForm.getDiscOS());
			preparedStatement.setString(31, patientForm.getVesselOD());
			preparedStatement.setString(32, patientForm.getVesselOS());
			preparedStatement.setString(33, patientForm.getMaculaOD());
			preparedStatement.setString(34, patientForm.getMaculaOS());
			preparedStatement.setString(35, patientForm.getIODOD());
			preparedStatement.setString(36, patientForm.getIODOS());
			preparedStatement.setString(37, patientForm.getSacOD());
			preparedStatement.setString(38, patientForm.getSacOS());
			preparedStatement.setString(39, patientForm.getK1OD());
			preparedStatement.setString(40, patientForm.getK1OS());
			preparedStatement.setString(41, patientForm.getK2OD());
			preparedStatement.setString(42, patientForm.getK2OS());
			preparedStatement.setString(43, patientForm.getAxialLengthOD());
			preparedStatement.setString(44, patientForm.getAxialLengthOS());
			preparedStatement.setString(45, patientForm.getIOLOD());
			preparedStatement.setString(46, patientForm.getIOLOS());
			preparedStatement.setString(47, dateToBeFormatted.format(date));
			preparedStatement.setString(48, patientForm.getHo());
			preparedStatement.setString(49, patientForm.getAllergicTo());
			preparedStatement.setInt(50, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully updated IPD details into table");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating IPD visit details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateOTNotes(com.edhanvantari.
	 * form.PatientForm)
	 */
	public String updateOTNotes(PatientForm patientForm) {

		try {
			connection = getConnection();

			String updateOTNotesQuery = QueryMaker.UPDATE_OT_NOTES;

			preparedStatement = connection.prepareStatement(updateOTNotesQuery);

			preparedStatement.setString(1, patientForm.getOTNotes());
			preparedStatement.setInt(2, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully updated OT NOtes into table");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating OT NOtes details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertConsentDocument(com.
	 * edhanvantari .form.PatientForm)
	 */
	public String insertConsentDocument(String fileNameWithPath, int visitID) {

		try {
			connection = getConnection();

			String insertConsentDocumentQuery = QueryMaker.INSERT_CONSENT_DOCUMENT;

			preparedStatement = connection.prepareStatement(insertConsentDocumentQuery);

			preparedStatement.setString(1, fileNameWithPath);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully inserted consent file to Database");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting consent document into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertConsent(com.edhanvantari.
	 * form.PatientForm)
	 */
	public String insertConsent(PatientForm patientForm) {

		String finalConsentText = patientForm.getConsentTextDB() + " $ " + patientForm.getConsentText1DB() + " % "
				+ patientForm.getConsentText2DB();

		try {
			connection = getConnection();

			String insertConsentDocumentQuery = QueryMaker.INSERT_CONSENT_DOCUMENT;

			preparedStatement = connection.prepareStatement(insertConsentDocumentQuery);

			preparedStatement.setString(1, finalConsentText);
			preparedStatement.setInt(2, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully inserted consent to Database");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting consent document into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveConsent(int)
	 */
	public String retrieveConsent(int visitID) {
		String consentText = null;

		try {
			connection = getConnection();

			String retrieveConsentQuery = QueryMaker.RETRIEVE_CONSENT;

			preparedStatement8 = connection.prepareStatement(retrieveConsentQuery);
			preparedStatement8.setInt(1, visitID);

			resultSet8 = preparedStatement8.executeQuery();

			while (resultSet8.next()) {
				consentText = resultSet8.getString("consentDocument");
			}

			resultSet8.close();
			preparedStatement8.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving consent text from table due to:::" + exception.getMessage());
		}
		return consentText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateConsent(com.edhanvantari.
	 * form.PatientForm)
	 */
	public String updateConsent(PatientForm patientForm) {
		String finalConsentText = patientForm.getConsentTextDB() + "$" + patientForm.getConsentText1DB() + "%"
				+ patientForm.getConsentText2DB();

		try {
			connection = getConnection();

			String updateConsentQuery = QueryMaker.UPDATE_DOCUMENT;

			preparedStatement = connection.prepareStatement(updateConsentQuery);

			preparedStatement.setString(1, finalConsentText);
			preparedStatement.setInt(2, patientForm.getVisitID());

			preparedStatement.execute();

			System.out.println("Successfully udpated consent to Database");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while udapting consent document into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#searchPatientByPatientName(java.
	 * lang.String, int, int, java.lang.String)
	 */
	public List<PatientForm> searchPatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria, String fromDate, String toDate) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String searchPatient = QueryMaker.SEARCH_ALL_PATIENT;

			patientName = patientName.replaceAll("\\s", "%");

			preparedStatement = connection.prepareStatement(searchPatient);
			preparedStatement.setString(1, "%" + patientName + "%");
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			System.out.println("query is :::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			/*
			 * if (searchCriteria.equals("MobileNo")) {
			 * 
			 * String searchPatientByPatientMobileQuery =
			 * QueryMaker.SEARCH_PATIENT_BY_MOBILE_NO;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(searchPatientByPatientMobileQuery);
			 * 
			 * preparedStatement.setString(1, patientName); preparedStatement.setInt(2,
			 * practiceID); preparedStatement.setString(3, ActivityStatus.ACTIVE);
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else if (searchCriteria.equals("PatientName")) {
			 * 
			 * String searchPatientByPatientNameQuery =
			 * QueryMaker.SEARCH_PATIENT_BY_PATIENT_NAME;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(searchPatientByPatientNameQuery);
			 * 
			 * if (patientName.contains(" ")) { patientName = patientName.replace(" ", "%");
			 * }
			 * 
			 * preparedStatement.setInt(1, clinicID); preparedStatement.setString(2, "%" +
			 * patientName + "%"); preparedStatement.setInt(3, practiceID);
			 * preparedStatement.setString(4, ActivityStatus.ACTIVE);
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else if(searchCriteria.equals("RegNo")) {
			 * 
			 * String searchPatientByPatientRegNoQuery =
			 * QueryMaker.SEARCH_PATIENT_BY_REGISTRATION_NUMBER;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(searchPatientByPatientRegNoQuery);
			 * 
			 * preparedStatement.setString(1, "%" + patientName + "%");
			 * preparedStatement.setInt(3, practiceID); preparedStatement.setString(2,
			 * ActivityStatus.ACTIVE);
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }else if(searchCriteria.equals("VisitDate")){ if(!fromDate.equals("") &&
			 * !toDate.equals("")) {
			 * System.out.println("INSIDE VISIT DATE IFF:::"+fromDate);
			 * System.out.println("INSIDE VISIT DATE IFF:::"+toDate);
			 * 
			 * String searchPatientByPatientDateRangeQuery =
			 * QueryMaker.SEARCH_PATIENT_BY_DATE_RANGE; preparedStatement =
			 * connection.prepareStatement(searchPatientByPatientDateRangeQuery);
			 * preparedStatement.setInt(1, clinicID); preparedStatement.setString(2,
			 * dateToBeFormatted.format(dateToBeParsed123.parse(fromDate)));
			 * preparedStatement.setString(3,
			 * dateToBeFormatted.format(dateToBeParsed123.parse(toDate)));
			 * preparedStatement.setString(4, ActivityStatus.ACTIVE);
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }else { System.out.println("INSIDE VISIT DATE ELSE:::"+fromDate); String
			 * searchPatientByPatientStartDateQuery =
			 * QueryMaker.SEARCH_PATIENT_BY_START_DATE;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(searchPatientByPatientStartDateQuery);
			 * preparedStatement.setInt(1, clinicID); preparedStatement.setString(2,
			 * dateToBeFormatted.format(dateToBeParsed123.parse(fromDate)));
			 * preparedStatement.setInt(3, clinicID); preparedStatement.setString(4,
			 * ActivityStatus.ACTIVE);
			 * 
			 * resultSet = preparedStatement.executeQuery(); } }
			 */

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setPatientName(patientName);
				patientForm.setRegistrationNo(resultSet.getString("regNo"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient details based on PatientName from Patient table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	public List<PatientForm> searchPatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			if (searchCriteria.equals("MobileNo")) {

				String searchPatientByPatientMobileQuery = QueryMaker.SEARCH_PATIENT_BY_MOBILE_NO;

				preparedStatement = connection.prepareStatement(searchPatientByPatientMobileQuery);

				preparedStatement.setString(1, patientName);
				preparedStatement.setInt(2, practiceID);
				preparedStatement.setString(3, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("PatientName")) {

				String searchPatientByPatientNameQuery = QueryMaker.SEARCH_PATIENT_BY_PATIENT_NAME;

				preparedStatement = connection.prepareStatement(searchPatientByPatientNameQuery);

				if (patientName.contains(" ")) {
					patientName = patientName.replace(" ", "%");
				}

				preparedStatement.setInt(1, clinicID);
				preparedStatement.setString(2, "%" + patientName + "%");
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("RegNo")) {

				String searchPatientByPatientRegNoQuery = QueryMaker.SEARCH_PATIENT_BY_REGISTRATION_NUMBER;

				preparedStatement = connection.prepareStatement(searchPatientByPatientRegNoQuery);

				preparedStatement.setString(1, "%" + patientName + "%");
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

			}

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setPatientName(patientName);
				patientForm.setRegistrationNo(resultSet.getString("regNo"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient details based on PatientName from Patient table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientFullName(int)
	 */
	public String retrievePatientFullName(int patientID) {
		String patientFullName = null;

		try {

			connection = getConnection();

			String retrievePatientFullNameQuery = QueryMaker.RETRIEVE_PATIENT_NAME;

			preparedStatement = connection.prepareStatement(retrievePatientFullNameQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {
					patientFullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");

				} else {

					patientFullName = resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " "
							+ resultSet.getString("lastName");
				}
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient full name Patient table due to:::"
					+ exception.getMessage());
		}

		return patientFullName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentNumber()
	 */
	public int retrieveAppointmentNumber() {
		int aptNumber = 0;

		/*
		 * Getting current date and converting it into DB format
		 */
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String retrieveAppointmentNumberQuery = QueryMaker.RETRIEVE_APPOINTMENT_NUMBER;

			preparedStatement = connection.prepareStatement(retrieveAppointmentNumberQuery);
			preparedStatement.setString(1, dateFormat.format(date));

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				aptNumber = resultSet.getInt("apptNumber");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment number from Appointment table due to:::"
					+ exception.getMessage());
		}
		return aptNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertAppointment(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertAppointment(PatientForm patientForm, int aptNumber) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed = new SimpleDateFormat("MM/dd/yyyy");

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String endDate = "";

		try {
			connection = getConnection();

			String insertAppointmentQuery = QueryMaker.INSERT_APPOINTMENT;

			preparedStatement = connection.prepareStatement(insertAppointmentQuery);

			/*
			 * Converting date
			 */
			if (patientForm.getAppointmentDate().substring(2, 3).equals("-")) {
				date = dateToBeParsed123.parse(patientForm.getAppointmentDate());
				preparedStatement.setString(1, dateToBeFormatted.format(date));
			} else if (patientForm.getAppointmentDate().substring(4, 5).equals("-")) {
				preparedStatement.setString(1, patientForm.getAppointmentDate());
			} else {
				date = dateToBeParsed.parse(patientForm.getAppointmentDate());
				preparedStatement.setString(1, dateToBeFormatted.format(date));
			}

			if (patientForm.getEndDate() == null || patientForm.getEndDate() == "") {
				endDate = null;
			} else if (patientForm.getEndDate().isEmpty()) {
				endDate = null;
			} else {
				endDate = dateToBeFormatted.format(dateToBeParsed123.parse(patientForm.getEndDate()));
			}

			preparedStatement.setInt(2, aptNumber);

			if (patientForm.getFacilityDashboard() == 2) {
				preparedStatement.setString(3, patientForm.getApptSlot());
			} else {
				preparedStatement.setString(3, patientForm.getAptTimeFrom());
			}

			// preparedStatement.setString(3, patientForm.getAptTimeFrom());
			preparedStatement.setString(4, patientForm.getAptTimeTo());
			preparedStatement.setString(5, ActivityStatus.BOOKED);
			preparedStatement.setInt(6, patientForm.getPatientID());
			preparedStatement.setInt(7, patientForm.getClinicID());
			preparedStatement.setInt(8, patientForm.getVisitTypeID());
			preparedStatement.setInt(9, patientForm.getCliniciaID());
			preparedStatement.setInt(10, patientForm.getWalkIn());
			preparedStatement.setInt(11, patientForm.getNextApptTaken());
			preparedStatement.setInt(12, patientForm.getRoomTypeID());
			preparedStatement.setString(13, endDate);
			preparedStatement.setString(14, patientForm.getComment());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfullt insrted appointment details");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Appointment into Appointment table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentList()
	 */
	public List<PatientForm> retrieveAppointmentList(int clinicID, int clinicianID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			String retrieveAppointmentListQuery = "";

			if (clinicianID == 0) {

				retrieveAppointmentListQuery = QueryMaker.RETRIEVE_APPOINTMENT_LIST;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);

			} else {

				retrieveAppointmentListQuery = QueryMaker.RETRIEVE_APPOINTMENT_LIST_BY_CLINICIAN_ID;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);
				preparedStatement3.setInt(3, clinicianID);

			}

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));

				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);

				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));

				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentList()
	 */
	public List<PatientForm> retrieveAppointmentList(int clinicID, int clinicianID, String careType) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String whereCondition = "";

		if (clinicianID > 0) {
			whereCondition += " AND clinicianID = " + clinicianID;
		}

		if (!careType.equals("NA")) {
			whereCondition += " AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = '" + careType + "')";
		}

		try {

			connection = getConnection();

			String retrieveAppointmentListQuery = "SELECT id,comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, "
					+ "walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate ) = MONTH(NOW()) AND"
					+ " DAY(apptDate ) = DAY(NOW()) AND status <> ? AND clinicID = ?" + whereCondition
					+ " ORDER BY apptTimeFrom";

			preparedStatement3 = connection.prepareStatement(retrieveAppointmentListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, clinicID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));

				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);

				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));
				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentWeekList()
	 */
	public List<PatientForm> retrieveAppointmentWeekList(int clinicID, int clinicianID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			if (clinicianID == 0) {

				String retrieveAppointmentWeekListQuery = QueryMaker.RETRIEVE_APPOINTMENT_WEEK_LIST;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentWeekListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);

			} else {

				String retrieveAppointmentWeekListQuery = QueryMaker.RETRIEVE_APPOINTMENT_WEEK_LIST_BY_CLINICIAN_ID;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentWeekListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);
				preparedStatement3.setInt(3, clinicianID);

			}

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));

				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Appointment Week List from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	public List<PatientForm> retrieveAppointmentWeekList(int clinicID, int clinicianID, String careType) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String whereCondition = "";

		if (clinicianID > 0) {
			whereCondition += " AND clinicianID = " + clinicianID;
		}

		if (!careType.equals("NA")) {
			whereCondition += " AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = '" + careType + "')";
		}

		try {

			connection = getConnection();

			String retrieveAppointmentWeekListQuery = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, "
					+ "walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE WEEKOFYEAR(apptDate) = WEEKOFYEAR(NOW()) AND status <> ? AND clinicID = ? "
					+ whereCondition + " ORDER BY apptTimeFrom";

			preparedStatement3 = connection.prepareStatement(retrieveAppointmentWeekListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, clinicID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));
				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Appointment Week List from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentMonthList()
	 */
	public List<PatientForm> retrieveAppointmentMonthList(int clinicID, int clinicianID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			if (clinicianID == 0) {

				String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);

				resultSet3 = preparedStatement3.executeQuery();

			} else {

				String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST_BY_CLINICIAN_ID;

				preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

				preparedStatement3.setString(1, ActivityStatus.CANCELLED);
				preparedStatement3.setInt(2, clinicID);
				preparedStatement3.setInt(3, clinicianID);

				resultSet3 = preparedStatement3.executeQuery();

			}

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));
				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Appointment Month List from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	public List<PatientForm> retrieveAppointmentListByCount(int clinicID, int clinicianID, int monthCount,
			String careType) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			if (clinicianID == 0) {

				if (careType.equalsIgnoreCase("NA")) {
					String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_WO_CARETYPE;
					preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

					preparedStatement3.setInt(1, monthCount);
					preparedStatement3.setInt(2, monthCount);
					preparedStatement3.setString(3, ActivityStatus.CANCELLED);
					preparedStatement3.setInt(4, clinicID);

					resultSet3 = preparedStatement3.executeQuery();
				} else {

					String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT;

					preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

					preparedStatement3.setInt(1, monthCount);
					preparedStatement3.setInt(2, monthCount);
					preparedStatement3.setString(3, ActivityStatus.CANCELLED);
					preparedStatement3.setInt(4, clinicID);
					preparedStatement3.setString(5, careType);

					resultSet3 = preparedStatement3.executeQuery();
				}

			} else {

				if (careType.equalsIgnoreCase("NA")) {

					String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_AND_CLINICIAN_ID;

					preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

					preparedStatement3.setInt(1, monthCount);
					preparedStatement3.setInt(2, monthCount);
					preparedStatement3.setString(3, ActivityStatus.CANCELLED);
					preparedStatement3.setInt(4, clinicID);
					preparedStatement3.setInt(5, clinicianID);

					resultSet3 = preparedStatement3.executeQuery();
				} else {
					String retrieveAppointmentMonthListQuery = QueryMaker.RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_AND_CLINICIAN_ID_AND_CARE_TYPE;

					preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

					preparedStatement3.setInt(1, monthCount);          
					preparedStatement3.setInt(2, monthCount);          
					preparedStatement3.setString(3, ActivityStatus.CANCELLED); 
					preparedStatement3.setInt(4, clinicID);            
					preparedStatement3.setInt(5, clinicianID);         
					preparedStatement3.setString(6, careType);         

					resultSet3 = preparedStatement3.executeQuery();

				}

			}

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));
				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Appointment Month List from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentMonthList(int,
	 * int, java.lang.String)
	 */
	public List<PatientForm> retrieveAppointmentMonthList(int clinicID, int clinicianID, String careType) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String whereCondition = "";

		if (clinicianID > 0) {
			whereCondition += " AND clinicianID = " + clinicianID;
		}

		if (!careType.equals("NA")) {
			whereCondition += " AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = '" + careType + "')";
		}

		try {

			connection = getConnection();

			String retrieveAppointmentMonthListQuery = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, "
					+ "walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) "
					+ "AND MONTH(apptDate )=MONTH(NOW()) AND status <> ? AND clinicID = ? " + whereCondition
					+ " ORDER BY apptTimeFrom";

			System.out.println(".." + retrieveAppointmentMonthListQuery);

			preparedStatement3 = connection.prepareStatement(retrieveAppointmentMonthListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, clinicID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));
				form.setVisitTypeID(resultSet3.getInt("visitTypeID"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));
				form.setComment(resultSet3.getString("comments"));
				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Appointment Month List from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastEnteredAppointmentList
	 * (int)
	 */
	public List<PatientForm> retrieveLastEnteredAppointmentList(int patientID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = new PatientForm();

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		/*
		 * Retrieving patientName
		 */
		String patientName = retrievePatientFullName(patientID);

		try {

			connection = getConnection();

			String retrieveLastEnteredAppointmentListQuery = QueryMaker.RETRIEVE_LAST_ENTERED_APPOINTMENT;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredAppointmentListQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String dbDate = resultSet.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				/*
				 * Splitting appointment time to and from into seperate hh and mm respectively
				 */

				System.out.println("Time from from table :: " + resultSet.getString("apptTimeFrom"));
				String[] fromTimeArray = resultSet.getString("apptTimeFrom").split(":");

				String[] toTimeArry = resultSet.getString("apptTimeTo").split(":");

				System.out.println("Array AFter split for Time.... : " + fromTimeArray[1]);

				String[] fromMMArray = fromTimeArray[1].split(" ");

				String fromMM = fromMMArray[0];

				String fromAMPM = fromMMArray[1];

				String[] toMMArray = toTimeArry[1].split(" ");

				String toMM = toMMArray[0];

				String toAMPM = toMMArray[1];

				form.setAppointmentDate(dateToBeParsed123.format(date));
				form.setAptFromHH(fromTimeArray[0]);
				form.setAptFromMM(fromMM);
				form.setAptfromMMAMPM(fromAMPM);
				form.setAptToHH(toTimeArry[0]);
				form.setAptToMM(toMM);
				form.setAptToMMAMPM(toAMPM);
				form.setPatientID(resultSet.getInt("patientID"));
				form.setAptID(resultSet.getInt("id"));
				form.setPatientName(patientName);

			}

			/*
			 * Adding form into List
			 */
			list.add(form);

			resultSet.close();
			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entered Appointment details from Appointment table due to:::"
							+ exception.getMessage());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#cancelAppointment(int)
	 */
	public String cancelAppointment(int aptID) {
		try {
			connection = getConnection();

			String cancelAppointmentQuery = QueryMaker.CANCEL_APPOINTMENT;

			preparedStatement = connection.prepareStatement(cancelAppointmentQuery);

			preparedStatement.setString(1, ActivityStatus.CANCELLED);
			preparedStatement.setInt(2, aptID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully cancelled the appointment");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while cancelling Appointment due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#confirmAppointment(int)
	 */
	public String confirmAppointment(int aptID) {
		try {
			connection = getConnection();

			String confirmAppointmentQuery = QueryMaker.CONFIRM_APPOINTMENT;

			preparedStatement = connection.prepareStatement(confirmAppointmentQuery);

			preparedStatement.setString(1, ActivityStatus.CONFIRMED);
			preparedStatement.setInt(2, aptID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully confirmed the appointment");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while confirming Appointment due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveValueFromAppointment(int)
	 */
	public String retrieveValueFromAppointment(int aptID) {
		String valueFromAppt = null;

		try {

			connection = getConnection();

			String retrieveValueFromAppointmentQuery = QueryMaker.RETRIEVE_APPOINTMENT_VALUES;

			preparedStatement = connection.prepareStatement(retrieveValueFromAppointmentQuery);

			preparedStatement.setInt(1, aptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String aptTimeFrom = resultSet.getString("apptTimeFrom");
				String aptTimeTo = resultSet.getString("apptTimeTo");

				valueFromAppt = aptTimeFrom + "=" + aptTimeTo;

				System.out.println("Appointment To and From time values ::: " + valueFromAppt);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving appt time values from  Appointment due to:::"
					+ exception.getMessage());
		}
		return valueFromAppt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#completeAppointment(int)
	 */
	public String completeAppointment(int aptID) {

		try {
			connection = getConnection();

			String completeAppointmentQuery = QueryMaker.COMPLETE_APPOINTMENT;

			preparedStatement = connection.prepareStatement(completeAppointmentQuery);

			preparedStatement.setString(1, ActivityStatus.COMPLETED);
			preparedStatement.setInt(2, aptID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully completed the appointment");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while completing Appointment due to:::" + exception.getMessage());
			status = "error";
		}
		return status;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateNextVisitNo(com.edhanvantari
	 * .form.PatientForm)
	 */
	public String updateNextVisitDays(PatientForm patientForm) {
		try {
			connection = getConnection();

			String updateNextVisitDaysQuery = QueryMaker.UPDATE_NEXT_VISIT_DAYS;

			preparedStatement = connection.prepareStatement(updateNextVisitDaysQuery);

			preparedStatement.setInt(1, patientForm.getNextVisitDays());
			preparedStatement.setInt(2, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			System.out.println("Successfully updated nextVisitDays into Visit table");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating nextVisitDays into Visit table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertContinuationSheetDetails(
	 * com.edhanvantari.form.PatientForm)
	 */
	public String insertContinuationSheetDetails(PatientForm patientForm) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("dd-MM-yyyy");

		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertIPDContinuationSheetQuery = QueryMaker.INSERT_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(insertIPDContinuationSheetQuery);

			/*
			 * Checking whether continuation date in null or "" if so, set date as
			 * 0000-00-00
			 */
			if (patientForm.getCountinuationSheetDate().isEmpty()) {
				patientForm.setCountinuationSheetDate(null);
				preparedStatement.setString(1, patientForm.getCountinuationSheetDate());
			} else {
				System.out.println(patientForm.getCountinuationSheetDate());
				System.out.println("*-*-*");
				date1 = dateToBeFormatted.parse(patientForm.getCountinuationSheetDate());
				preparedStatement.setString(1, patientForm.getCountinuationSheetDate());
			}

			preparedStatement.setString(2, patientForm.getCountinuationSheetDescription());
			preparedStatement.setString(3, patientForm.getCountinuationSheetTeatment());
			preparedStatement.setInt(4, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted IPD continuation sheet details");

			preparedStatement.close();

			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting IPD continuation sheet details into table due to:::"
					+ exception.getMessage());

			status = "error";

			return status;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertContinuationSheetDetails(
	 * com.edhanvantari.form.PatientForm)
	 */
	public String insertContinuationSheetDetails_bk(PatientForm patientForm) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted1 = new SimpleDateFormat("dd-MM-yyyy");

		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertIPDContinuationSheetQuery = QueryMaker.INSERT_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(insertIPDContinuationSheetQuery);

			/*
			 * Checking whether continuation date in null or "" if so, set date as
			 * 0000-00-00
			 */
			if (patientForm.getCountinuationSheetDate().isEmpty()) {
				patientForm.setCountinuationSheetDate(null);
				preparedStatement.setString(1, patientForm.getCountinuationSheetDate());
			} else {
				date1 = dateToBeFormatted1.parse(patientForm.getCountinuationSheetDate());
				preparedStatement.setString(1, dateToBeFormatted.format(date1));
			}

			preparedStatement.setString(2, patientForm.getCountinuationSheetDescription());
			preparedStatement.setString(3, patientForm.getCountinuationSheetTeatment());
			preparedStatement.setInt(4, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted IPD continuation sheet details");

			preparedStatement.close();

			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting IPD continuation sheet details into table due to:::"
					+ exception.getMessage());

			status = "error";

			return status;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#updateContinuationSheetDetails(
	 * com.edhanvantari.form.PatientForm)
	 */
	public String updateContinuationSheetDetails(PatientForm patientForm) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			connection = getConnection();

			String insertIPDContinuationSheetQuery = QueryMaker.UPDATE_IPD_CONTINUATION_SHEET;

			preparedStatement = connection.prepareStatement(insertIPDContinuationSheetQuery);

			/*
			 * Checking whether continuation date in null or "" if so, set date as
			 * 0000-00-00
			 */
			if (patientForm.getCountinuationSheetDate().isEmpty()) {
				patientForm.setCountinuationSheetDate(null);
				preparedStatement.setString(1, patientForm.getCountinuationSheetDate());
			} else {
				date1 = dateToBeParsed123.parse(patientForm.getCountinuationSheetDate());
				preparedStatement.setString(1, dateToBeFormatted.format(date1));
			}

			preparedStatement.setString(2, patientForm.getCountinuationSheetDescription());
			preparedStatement.setString(3, patientForm.getCountinuationSheetTeatment());
			preparedStatement.setInt(4, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated IPD continuation sheet details");

			preparedStatement.close();

			connection.close();

			return status;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating IPD continuation sheet details into table due to:::"
					+ exception.getMessage());

			status = "error";

			return status;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOPDVisitIDByPatientID
	 * (int)
	 */
	public int retrieveLastOPDVisitIDByPatientID(int patientID, int clinicID) {
		int oldVisitID = 0;

		try {
			connection = getConnection();

			String retrieveLastOPDVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitIDQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				oldVisitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return oldVisitID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOPDVisitDetails(int)
	 */
	public List<PatientForm> retrieveLastOPDVisitDetails(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		try {
			connection = getConnection();

			String retrieveLastOPDVisitDetailsQuery = QueryMaker.RETRIEVE_EXISTING_VISIT_LIST;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitDetailsQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				/*
				 * Parsing db date into correct Date format
				 */
				date = databaseDate.parse(resultSet.getString("visitDate"));

				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				// patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(dateToBeDisplayed.format(date));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));

				if (resultSet.getString("visitTimeFrom") == null || resultSet.getString("visitTimeFrom") == "") {
					patientForm.setVisitFromTime("");
				} else if (resultSet.getString("visitTimeFrom").isEmpty()) {
					patientForm.setVisitFromTime("");
				} else {
					patientForm.setVisitFromTime(resultSet.getString("visitTimeFrom"));
				}

				if (resultSet.getString("visitTimeTo") == null || resultSet.getString("visitTimeTo") == "") {
					patientForm.setVisitToTime("");
				} else if (resultSet.getString("visitTimeTo").isEmpty()) {
					patientForm.setVisitToTime("");
				} else {
					patientForm.setVisitToTime(resultSet.getString("visitTimeTo"));
				}

				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
			}
			list.add(patientForm);

			System.out.println("Successfully retrieved last OPD visit details");

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred OPD visit details from Visit table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLastOpthalmologyOPDDetails
	 * (int)
	 */
	public List<PatientForm> retrieveLastOpthalmologyOPDDetails(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		try {
			connection = getConnection();

			String retrieveLastOpthalmologyOPDDetailsQuery = QueryMaker.RETRIEVE_OPD_VISIT_DETAILS;

			preparedStatement5 = connection.prepareStatement(retrieveLastOpthalmologyOPDDetailsQuery);
			preparedStatement5.setInt(1, visitID);

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {

				patientForm.setEyeLidLowerOD(resultSet5.getString("eyelidLowerOD"));
				patientForm.setEyeLidLowerOS(resultSet5.getString("eyelidLowerOS"));
				patientForm.setEyeLidUpperOD(resultSet5.getString("eyelidUpperOD"));
				patientForm.setEyeLidUpperOS(resultSet5.getString("eyelidUpperOS"));
				patientForm.setVisualAcuityDistOD(resultSet5.getString("visualAcuityDistOD"));
				patientForm.setVisualAcuityDistOS(resultSet5.getString("visualAcuityDistOS"));
				patientForm.setVisualAcuityNearOD(resultSet5.getString("visualAcuityNearOD"));
				patientForm.setVisualAcuityNearOS(resultSet5.getString("visualAcuityNearOS"));
				patientForm.setPinholeVisionDistOD(resultSet5.getString("pinholeVisionDistOD"));
				patientForm.setPinholeVisionDistOS(resultSet5.getString("pinholeVisionDistOS"));
				patientForm.setPinholeVisionNearOD(resultSet5.getString("pinholeVisionNearOD"));
				patientForm.setPinholeVisionNearOS(resultSet5.getString("pinholeVisionNearOS"));
				patientForm.setBCVADistOD(resultSet5.getString("bcvaDistOD"));
				patientForm.setBCVADistOS(resultSet5.getString("bcvaDistOS"));
				patientForm.setBCVANearOD(resultSet5.getString("bcvaNearOD"));
				patientForm.setBCVANearOS(resultSet5.getString("bcvaNearOS"));
				patientForm.setConjunctivaOD(resultSet5.getString("conjunctivaOD"));
				patientForm.setConjunctivaOS(resultSet5.getString("conjunctivaOS"));
				patientForm.setCorneaOD(resultSet5.getString("corneaOD"));
				patientForm.setCorneaOS(resultSet5.getString("corneaOS"));
				patientForm.setPupilOD(resultSet5.getString("pupilOD"));
				patientForm.setPupilOS(resultSet5.getString("pupilOS"));
				patientForm.setACOD(resultSet5.getString("anteriorChamberOD"));
				patientForm.setACOS(resultSet5.getString("anteriorChamberOS"));
				patientForm.setIrisOD(resultSet5.getString("irisOD"));
				patientForm.setIrisOS(resultSet5.getString("irisOS"));
				patientForm.setLensOD(resultSet5.getString("lensOD"));
				patientForm.setLensOS(resultSet5.getString("lensOS"));
				patientForm.setDiscOD(resultSet5.getString("discOD"));
				patientForm.setDiscOS(resultSet5.getString("discOS"));
				patientForm.setVesselOD(resultSet5.getString("vesselOD"));
				patientForm.setVesselOS(resultSet5.getString("vesselOS"));
				patientForm.setMaculaOD(resultSet5.getString("maculaOD"));
				patientForm.setMaculaOS(resultSet5.getString("maculaOS"));
				patientForm.setIODOD(resultSet5.getString("iopOD"));
				patientForm.setIODOS(resultSet5.getString("iopOS"));
				patientForm.setSacOD(resultSet5.getString("sacOD"));
				patientForm.setSacOS(resultSet5.getString("sacOS"));
				patientForm.setK1OD(resultSet5.getString("biometryK1OD"));
				patientForm.setK1OS(resultSet5.getString("biometryK1OS"));
				patientForm.setK2OD(resultSet5.getString("biometryK2OD"));
				patientForm.setK2OS(resultSet5.getString("biometryK2OS"));
				patientForm.setAxialLengthOD(resultSet5.getString("biometryAxialLengthOD"));
				patientForm.setAxialLengthOS(resultSet5.getString("biometryAxialLengthOS"));
				patientForm.setIOLOD(resultSet5.getString("biometryIOLOD"));
				patientForm.setIOLOS(resultSet5.getString("biometryIOLOS"));
			}

			list.add(patientForm);

			System.out.println("Successfully retrieved last OphthalmologyOPD details");

			resultSet5.close();
			preparedStatement5.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred OPD details from OphthalomologyOPD table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#verifyVisitForPatientByPatientID
	 * (int)
	 */
	public List<Integer> verifyVisitForPatientByPatientID(int patientID) {
		List<Integer> list = new ArrayList<Integer>();
		int count = 1;

		try {
			connection = getConnection();

			String verifyVisitForPatientByPatientIDQuery = QueryMaker.RETRIEVE_LAST_OPD_VISIT_ID1;

			preparedStatement = connection.prepareStatement(verifyVisitForPatientByPatientIDQuery);
			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(count);

				count++;
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying exsiting visit counts for patient table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveApptDurationFromClinicID
	 * (int)
	 */
	public String retrieveApptDurationFromClinicID(int visitTypeID) {
		String apptDuration = "0";
		try {

			connection = getConnection();

			String retrieveApptDurationFromClinicIDQuery = QueryMaker.RETRIEVE_APPOINTMENT_DURATION;

			preparedStatement = connection.prepareStatement(retrieveApptDurationFromClinicIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				apptDuration = resultSet.getString("visitDuration");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while appointment duration from Calendar table on the basis of clinicID due to:::"
							+ exception.getMessage());
		}
		return apptDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertLabReport(java.util.List)
	 */
	public String insertLabReport(List<String> fileNameList) {
		try {

			connection = getConnection();

			String insertLabReportQuery = QueryMaker.INSERT_LAB_REPORT;

			preparedStatement = connection.prepareStatement(insertLabReportQuery);

			/*
			 * Iterating fileNameList in order to insert all values one by one into
			 * LabReport table
			 */
			Iterator<String> iterator = fileNameList.iterator();

			while (iterator.hasNext()) {
				String value = (String) iterator.next();

				// Splitting value by '='
				String[] valueArray = value.split("=");

				System.out.println("value arr 0::" + valueArray[0]);
				System.out.println("value arr 1::" + valueArray[1]);
				preparedStatement.setString(1, valueArray[0]);
				preparedStatement.setString(2, valueArray[1]);

				preparedStatement.execute();

			}
			System.out.println("value inserted successfully into LabReport");
			status = "success";

			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting lab report into table due to:::" + exception.getMessage());

			status = "error";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveLabReportFileName(com.
	 * edhanvantari.form.PatientForm)
	 */
	public List<File> retrieveLabReportFileName(PatientForm patientForm, String realPath) {
		List<File> list = new ArrayList<File>();

		try {

			connection = getConnection();

			String retrieveLabReportFileNameQuery = QueryMaker.RETRIEVE_LAB_REPORT_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveLabReportFileNameQuery);

			preparedStatement.setInt(1, patientForm.getVisitID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String fileName = resultSet.getString("report");

				/*
				 * Creating file in add into list
				 */
				File outFile = new File(realPath, fileName);

				list.add(outFile);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving lab report filename from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentListByPatientID
	 * (int)
	 */
	public List<String> retrieveAppointmentListByPatientID(int patientID) {
		List<String> list = new ArrayList<String>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			String retrieveAppointmentListByPatientIDQuery = QueryMaker.RETRIEVE_APPOINTMENT_LIST_BY_PATIENT_ID;

			preparedStatement1 = connection.prepareStatement(retrieveAppointmentListByPatientIDQuery);

			preparedStatement1.setInt(1, patientID);
			preparedStatement1.setString(2, ActivityStatus.CANCELLED);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form = new PatientForm();

				String dbDate = resultSet1.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				String[] timeFromArray = resultSet1.getString("apptTimeFrom").split(":");

				String fromHH = timeFromArray[0];

				String[] fromMMArray = timeFromArray[1].split(" ");

				String fromMM = fromMMArray[0];

				String FromamPm = fromMMArray[1];

				if (fromHH.length() == 1) {
					fromHH = "0" + fromHH;
				} else {
					fromHH = fromHH;
				}

				if (fromMM.length() == 1) {
					fromMM = "0" + fromMM;
				} else {
					fromMM = fromMM;
				}

				String[] timeToArray = resultSet1.getString("apptTimeTo").split(":");

				String toHH = timeToArray[0];

				String[] toMMArray = timeToArray[1].split(" ");

				String toMM = toMMArray[0];

				String toAmPM = toMMArray[1];

				if (toHH.length() == 1) {
					toHH = "0" + toHH;
				} else {
					toHH = toHH;
				}

				if (toMM.length() == 1) {
					toMM = "0" + toMM;
				} else {
					toMM = toMM;
				}

				String finalFromTime = fromHH + ":" + fromMM + " " + FromamPm;

				String finalToTime = toHH + ":" + toMM + " " + toAmPM;

				form.setAppointmentDate(dateToBeParsed123.format(date));
				form.setAptTimeFrom(finalFromTime);
				form.setAptTimeTo(finalToTime);
				form.setPatientID(resultSet1.getInt("patientID"));

				form.setAptID(resultSet1.getInt("id"));
				form.setAptStatus(resultSet1.getString("status"));

				/*
				 * Storing all the values in a string
				 */
				String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom() + "=" + form.getAptTimeTo()
						+ "=" + form.getPatientID() + "=" + form.getAptID() + "=" + form.getAptStatus();

				/*
				 * Adding form into List
				 */
				list.add(finalValues);

			}

			resultSet1.close();
			preparedStatement1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	public boolean verifyFnameLnameAge(PatientForm form) {
		try {
			connection = getConnection();

			String verifyFnameLnameAgeQuery = QueryMaker.VERIFY_FNAME_LNAME_AGE;

			preparedStatement = connection.prepareStatement(verifyFnameLnameAgeQuery);

			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getLastName());
			preparedStatement.setString(3, form.getAge());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, lname and age from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	public boolean verifyFnameLnameGender(PatientForm form) {
		try {
			connection = getConnection();

			String verifyFnameLnameGenderQuery = QueryMaker.VERIFY_FNAME_LNAME_GENDER;

			preparedStatement = connection.prepareStatement(verifyFnameLnameGenderQuery);

			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getLastName());
			preparedStatement.setString(3, form.getGender());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, lname and gender from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	public boolean verifyFnameLnameAgeGender(PatientForm form) {
		try {
			connection = getConnection();

			String verifyFnameLnameAgeGenderQuery = QueryMaker.VERIFY_FNAME_LNAME_AGE_GENDER;

			preparedStatement = connection.prepareStatement(verifyFnameLnameAgeGenderQuery);

			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getLastName());
			preparedStatement.setString(3, form.getAge());
			preparedStatement.setString(4, form.getGender());
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.setInt(6, form.getPracticeID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying patient fname, lname , age and gender from table Patient due to:::"
							+ exception.getMessage());
			status = "error";
		}
		return false;
	}

	public String retrievePatientNameByPatientID(int patientID) {
		String patientName = "";

		try {

			connection = getConnection();

			String retrievePatientNameByPatientIDQuery = QueryMaker.RETRIEVE_PATIENT_NAME_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientNameByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientName = resultSet.getString("firstName") + "=" + resultSet.getString("middleName") + "="
						+ resultSet.getString("lastName");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient's fname, mname and lname based on patientID due to:::"
							+ exception.getMessage());
		}
		return patientName;
	}

	public int retrieveLastEnteredVisitIDByVisitNumber(int visitNumber, int patientID, int visitTypeID, int clinicID) {
		int visitID = 0;

		try {

			connection = getConnection();

			String retrieveLastEnteredVisitIDByVisitNumberQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_ID_BY_VISIT_NUMBER;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredVisitIDByVisitNumberQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setInt(2, patientID);
			preparedStatement.setInt(3, visitTypeID);
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving last entered visit id based on visit Number due to:::"
							+ exception.getMessage());
		}
		return visitID;
	}

	public String insertMedicalCertificate(String medicalCerti, int visitID) {
		try {
			connection = getConnection();

			String insertMedicalCertificateQuery = QueryMaker.INSERT_MEDICAL_DOCUMENT;

			preparedStatement = connection.prepareStatement(insertMedicalCertificateQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, medicalCerti);

			preparedStatement.execute();

			System.out.println("Successfully inserted medical document details..");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting medical document details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<PatientForm> retrievePatientDetailsByPatientID(int patientID, int lastOPDVisitID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int check = 0;

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setPhone(resultSet.getString("phone"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));

					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmailID("No");
				} else if (resultSet.getString("email").isEmpty()) {
					patientForm.setEmailID("No");
				} else {
					patientForm.setEmailID(resultSet.getString("email"));
				}

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setLastVisitID(lastOPDVisitID);
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				// patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setAptID(resultSet.getInt("apptID"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					String visitDate = dateToBeFormat.format(new Date());

					String[] dateArray = visitDate.split("-");

					patientForm.setVisitDay(dateArray[0]);
					patientForm.setVisitMonth(dateArray[1]);
					patientForm.setVisitYear(dateArray[2]);

					patientForm.setFirstVisitDate(visitDate);

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						String visitDate = dateToBeFormat.format(new Date());

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

			}

			if (check == 0) {
				String visitDate = dateToBeFormat.format(new Date());

				String[] dateArray = visitDate.split("-");

				patientForm.setVisitDay(dateArray[0]);
				patientForm.setVisitMonth(dateArray[1]);
				patientForm.setVisitYear(dateArray[2]);

				patientForm.setFirstVisitDate(visitDate);
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving referred by details by patientID
			 */
			String retrieveReferredByDetailQuery = QueryMaker.RETRIEVE_REFERRED_BY_DETAILS_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveReferredByDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setSourceType(resultSet.getString("sourceType"));
				patientForm.setSourceName(resultSet.getString("sourceName"));
				patientForm.setSourceContact(resultSet.getString("sourceContact"));

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public String insertOpticianDetails(PatientForm form) {
		try {
			connection = getConnection();

			String insertOpticianDetailsQuery = QueryMaker.INSERT_OPTICIAN_DETAILS;

			preparedStatement = connection.prepareStatement(insertOpticianDetailsQuery);

			preparedStatement.setString(1, form.getSphNearOD());
			preparedStatement.setString(2, form.getSphNearOS());
			preparedStatement.setString(3, form.getSphDiskOD());
			preparedStatement.setString(4, form.getSphDiskOS());
			preparedStatement.setString(5, form.getCylNearOD());
			preparedStatement.setString(6, form.getCylNearOS());
			preparedStatement.setString(7, form.getCylDiskOD());
			preparedStatement.setString(8, form.getCylDiskOS());
			preparedStatement.setString(9, form.getAxisNearOD());
			preparedStatement.setString(10, form.getAxisNearOS());
			preparedStatement.setString(11, form.getAxisDiskOD());
			preparedStatement.setString(12, form.getAxisDiskOS());
			preparedStatement.setString(13, form.getVnNearOD());
			preparedStatement.setString(14, form.getVnNearOS());
			preparedStatement.setString(15, form.getVnDiskOD());
			preparedStatement.setString(16, form.getVnDiskOS());
			preparedStatement.setInt(17, form.getVisitID());
			preparedStatement.setString(18, form.getOptomeryComment());
			preparedStatement.setString(19, form.getSpectacleComments());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully inserted Optician details...");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting optician details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String insertOpticianOldGlassDetails(PatientForm form) {
		try {
			connection = getConnection();

			String insertOpticianOldGlassDetailsQuery = QueryMaker.INSERT_OPTICIAN_OLD_GLASS_DETAILS;

			preparedStatement = connection.prepareStatement(insertOpticianOldGlassDetailsQuery);

			preparedStatement.setString(1, form.getOldSphNearOD());
			preparedStatement.setString(2, form.getOldSphNearOS());
			preparedStatement.setString(3, form.getOldSphDiskOD());
			preparedStatement.setString(4, form.getOldSphDiskOS());
			preparedStatement.setString(5, form.getOldCylNearOD());
			preparedStatement.setString(6, form.getOldCylNearOS());
			preparedStatement.setString(7, form.getOldCylDiskOD());
			preparedStatement.setString(8, form.getOldCylDiskOS());
			preparedStatement.setString(9, form.getOldAxisNearOD());
			preparedStatement.setString(10, form.getOldAxisNearOS());
			preparedStatement.setString(11, form.getOldAxisDiskOD());
			preparedStatement.setString(12, form.getOldAxisDiskOS());
			preparedStatement.setString(13, form.getOldVnNearOD());
			preparedStatement.setString(14, form.getOldVnNearOS());
			preparedStatement.setString(15, form.getOldVnDiskOD());
			preparedStatement.setString(16, form.getOldVnDiskOS());
			preparedStatement.setInt(17, form.getOpticinID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully inserted Optician Old glasses details...");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting optician old glasses details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateEyewearDetails(PatientForm form) {
		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			connection = getConnection();

			String updateEyewearDetailsQuery = QueryMaker.UPDATE_EYEWEAR_DETAILS;

			preparedStatement = connection.prepareStatement(updateEyewearDetailsQuery);

			preparedStatement.setString(1, form.getTint());
			preparedStatement.setString(2, form.getGlass());
			preparedStatement.setString(3, form.getMaterial());
			preparedStatement.setString(4, form.getClinic());
			preparedStatement.setString(5, form.getFrame());
			preparedStatement.setDouble(6, form.getFrameCharge());
			preparedStatement.setDouble(7, form.getGlassCharge());
			preparedStatement.setDouble(8, form.getDiscount());
			preparedStatement.setDouble(9, form.getNetPayment());
			preparedStatement.setDouble(10, form.getAdvance());
			preparedStatement.setDouble(11, form.getBalance());
			preparedStatement.setDouble(12, form.getBalancePaid());

			/*
			 * Converting date
			 */
			System.out.println("Balance paid date ::: " + form.getBalancePaidDate());
			if (form.getBalancePaidDate() == null || form.getBalancePaidDate() == "") {
				form.setBalancePaidDate(null);
				preparedStatement.setString(13, form.getBalancePaidDate());
			} else if (form.getBalancePaidDate().isEmpty()) {

				form.setBalancePaidDate(null);
				preparedStatement.setString(13, form.getBalancePaidDate());

			} else {

				date1 = dateToBeParsed123.parse(form.getBalancePaidDate());

				preparedStatement.setString(13, dateToBeFormatted.format(date1));

			}

			preparedStatement.setString(14, form.getOptDiscountType());
			preparedStatement.setInt(15, form.getOpticinID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpate Eyewear details...");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Eyewear details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public void insertDummyVisit(PatientForm patientForm, int visitNumber) {

		try {

			connection = getConnection();

			String insertDummyVisitQuery = QueryMaker.INSERT_DUMMY_VISIT;

			preparedStatement = connection.prepareStatement(insertDummyVisitQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setString(2, "IPD");
			preparedStatement.setString(3, "New");
			preparedStatement.setInt(4, patientForm.getPatientID());

			preparedStatement.execute();

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while dummy visit details into table due to:::" + exception.getMessage());
		}

	}

	public String insertCurrentMedication(String drugName, int duration, String comments, int patientID) {
		try {

			connection = getConnection();

			String insertCurrentMedicationQuery = QueryMaker.INSERT_CURRENTMEDICATION;

			preparedStatement = connection.prepareStatement(insertCurrentMedicationQuery);

			preparedStatement.setString(1, drugName);
			preparedStatement.setInt(2, duration);
			preparedStatement.setString(3, comments);
			preparedStatement.setInt(4, patientID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Current Medication details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Current Medication details into prescriptionhistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertMedicalHistory(String diagnosis, String description, int patientID) {

		try {

			connection = getConnection();

			String insertComplaintsQuery = QueryMaker.INSERT_MEDICAL_HISTORY;

			preparedStatement = connection.prepareStatement(insertComplaintsQuery);

			preparedStatement.setString(1, diagnosis);
			preparedStatement.setString(2, description);
			preparedStatement.setInt(3, patientID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Medical History details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Medical History details into medicalhistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertComplaints(String symptom, int duration, String comments, int visitID) {

		try {

			connection = getConnection();

			String insertComplaintsQuery = QueryMaker.INSERT_PRESENT_COMPLAINTS;

			preparedStatement = connection.prepareStatement(insertComplaintsQuery);

			preparedStatement.setString(1, symptom);
			preparedStatement.setInt(2, duration);
			preparedStatement.setString(3, comments);
			preparedStatement.setInt(4, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Complaints details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Complaintsn details into presentcomplaints table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertComplaint(String complaintOD, String complaintOS, int visitID) {

		try {

			connection = getConnection();

			String insertComplaintQuery = QueryMaker.INSERT_IPD_COMPLAINTS;

			preparedStatement = connection.prepareStatement(insertComplaintQuery);

			preparedStatement.setString(1, complaintOD);
			preparedStatement.setString(2, complaintOS);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while dummy visit details into table due to:::" + exception.getMessage());

			status = "error";
		}

		return status;

	}

	public String updatePatientVisitDetails(PatientForm patientForm) {

		/*
		 * Converting visitDate into yyyy-MM-dd format in order to insert it into
		 * database
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		String dischargDate = null;

		try {

			if (patientForm.getDateOfDischarge() == null || patientForm.getDateOfDischarge() == "") {
				// Converting date into db format
				dischargDate = null;
			} else if (patientForm.getDateOfDischarge().isEmpty()) {
				// Converting date into db format
				dischargDate = null;
			} else {
				// Converting date into db format
				dischargDate = dateToBeFormatted.format(dateToBeParsed123.parse(patientForm.getDateOfDischarge()));
			}

			connection = getConnection();

			String updatePatientVisitDetailsQuery = QueryMaker.UPDATE_PATIENT_VISIT;

			preparedStatement = connection.prepareStatement(updatePatientVisitDetailsQuery);

			date1 = dateToBeParsed123.parse(patientForm.getVisitDate());

			preparedStatement.setString(1, dateToBeFormatted.format(date1));
			preparedStatement.setString(2, patientForm.getDiagnosis());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, patientForm.getAptID());
			preparedStatement.setInt(9, patientForm.getVisitID());
			preparedStatement.setString(5, dischargDate);
			preparedStatement.setString(6, patientForm.getProcedure());
			preparedStatement.setString(7, patientForm.getAdmission_time());
			preparedStatement.setString(8, patientForm.getDischarge_time());

			preparedStatement.execute();

			System.out.println("Patient visit inserted successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while dummy visit details into table due to:::" + exception.getMessage());

			status = "error";
		}

		return status;

	}

	public String retireveMedicalRegistrationNo(String practiceSuffixe) {

		String medRegNo = "";

		int check = 0;

		try {

			connection = getConnection();

			String retireveMedicalRegistrationNoQuery = QueryMaker.RETRIEVE_REGISTRATION_NO_FROM_PATIENT;

			preparedStatement = connection.prepareStatement(retireveMedicalRegistrationNoQuery);

			preparedStatement.setString(1, "%" + practiceSuffixe + "-%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
				String[] regNoArray = resultSet.getString("practiceRegNumber").split("-");
				int number = Integer.parseInt(regNoArray[1]) + 1;
				medRegNo = regNoArray[0] + "-" + number;
			}

			if (check == 0) {
				medRegNo = practiceSuffixe + "-1";
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving medical registration no due to:::" + exception.getMessage());
		}

		return medRegNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#insertPersonalHistory(com.
	 * edhanvantari.form.PatientForm)
	 */
	public String insertPersonalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertPersonalHistoryQuery = QueryMaker.INSERT_PERSONAL_HISTORY;

			preparedStatement = connection.prepareStatement(insertPersonalHistoryQuery);

			preparedStatement.setString(1, patientForm.getPersonalHistorySmoking());
			preparedStatement.setString(2, patientForm.getPersonalHistorySmokingDetails());
			preparedStatement.setString(3, patientForm.getPersonalHistoryDiet());
			preparedStatement.setString(4, patientForm.getPersonalHistoryPurisha());
			preparedStatement.setString(5, patientForm.getPersonalHistoryMootra());
			preparedStatement.setString(6, patientForm.getPersonalHistoryKshudha());
			preparedStatement.setString(7, patientForm.getPersonalHistoryNidra());
			preparedStatement.setString(8, patientForm.getPersonalHistoryOther());
			preparedStatement.setInt(9, patientForm.getPatientID());
			preparedStatement.setString(10, patientForm.getPersonalHistOther());
			preparedStatement.setString(11, patientForm.getMedHistDiagnosis());
			preparedStatement.setString(12, patientForm.getMedHistComment());
			preparedStatement.setString(13, patientForm.getMedHistOther());
			preparedStatement.setString(14, patientForm.getPersonalHistoryAlcohol());
			preparedStatement.setString(15, patientForm.getPersonalHistoryAlcoholDetails());
			preparedStatement.setString(16, patientForm.getPersonalHistoryTobacco());
			preparedStatement.setString(17, patientForm.getPersonalHistoryTobaccoDetails());
			preparedStatement.setString(18, patientForm.getPersonalHistoryFoodChoice());
			preparedStatement.setString(19, patientForm.getPersonalHistoryFoodChoiceDetails());

			preparedStatement.execute();

			status = "success";

			System.out.println("Personal history details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting personal history details into PersonalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrievePatientAgeByPatientID(int)
	 */
	public String retrievePatientAgeByPatientID(int patientID) {

		String patientAge = "";

		try {

			connection = getConnection();

			String retrievePatientAgeByPatientIDQuery = QueryMaker.RETRIEVE_PATIENT_AGE_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePatientAgeByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientAge = "" + resultSet.getInt("age");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient age based on patient ID due to:::"
					+ exception.getMessage());

		}

		return patientAge;
	}

	public JSONObject retrieveTradeNameByCategory(int categoryID, int clinicID) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object;

		boolean check = false;

		try {

			connection = getConnection();

			String retrieveTradeNameByCategoryQuery = QueryMaker.RETRIEVE_TRADE_NAME_BY_CATEGORY_ID;

			preparedStatement = connection.prepareStatement(retrieveTradeNameByCategoryQuery);

			preparedStatement.setInt(1, categoryID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = true;

				object = new JSONObject();

				object.put("productID", resultSet.getInt("id"));
				object.put("tradeName", resultSet.getString("tradeName"));
				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			if (!check) {

				object = new JSONObject();

				object.put("msg",
						"No tradeName found for selected Category. Either select another category or add new product to the same category in Prescription Management.");
				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Exception occured while retrieving trade name based on category due to:::"
					+ exception.getMessage());

			object = new JSONObject();

			object.put("ErrMsg", "Exception occured while retrieving trade name based on category");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveProductNameAndCategoryIDByProductID(int productID) {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONObject object = new JSONObject();

		try {

			connection = getConnection();

			String retrieveProductNameAndCategoryIDByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_NAME_CATEGORY_ID_BY_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retrieveProductNameAndCategoryIDByProductIDQuery);

			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object.put("tradeName", resultSet.getString("tradeName"));
				object.put("categoryID", resultSet.getInt("categoryID"));
				object.put("categoryName", resultSet.getString("name"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println(
					"Exception occured while retrieving product name and category ID based on productID due to:::"
							+ exception.getMessage());

			object = new JSONObject();

			object.put("ErrMsg", "Exception occured while retrieving product name and category ID based on productID");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public String retrieveProductNameByID(int productID) {

		String productName = "";

		try {

			connection1 = getConnection();

			String retrieveProductNameByIDQuery = QueryMaker.RETRIEVE_PRODUCT_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveProductNameByIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				productName = resultSet1.getString("tradeName");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return productName;

	}

	public String retrieveCompoundDetailsByID(int prescriptionID) {

		String productName = "";

		try {

			connection1 = getConnection();

			String retrieveCompoundDetailsByIDQuery = QueryMaker.RETRIEVE_COMPOUND_DETAILS_BY_PRESCRIPTION_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveCompoundDetailsByIDQuery);

			preparedStatement1.setInt(1, prescriptionID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				productName = resultSet1.getString("compound") + "=" + resultSet1.getInt("isCompound") + "="
						+ resultSet1.getInt("numberOfDays") + "=" + resultSet1.getString("frequency") + "="
						+ resultSet1.getDouble("dosage") + "=" + resultSet1.getString("section");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return productName;

	}

	public String retrievePrescriptionCommentByID(int prescriptionID) {

		String comment = "";

		try {

			connection1 = getConnection();

			String retrievePrescriptionCommentByIDQuery = QueryMaker.RETRIEVE_COMPOUND_DETAILS_BY_PRESCRIPTION_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePrescriptionCommentByIDQuery);

			preparedStatement1.setInt(1, prescriptionID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				comment = resultSet1.getString("comment");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return comment;

	}

	public String retrievePrescriptionInstructionByID(int prescriptionID) {

		String instruction = "";

		try {

			connection1 = getConnection();

			String retrievePrescriptionInstructionByIDQuery = QueryMaker.RETRIEVE_COMPOUND_DETAILS_BY_PRESCRIPTION_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePrescriptionInstructionByIDQuery);

			preparedStatement1.setInt(1, prescriptionID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				instruction = resultSet1.getString("instruction");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return instruction;

	}

	public String retrieveProductBarcodeByProductID(int productID) {

		String prodBarcode = "";

		try {

			connection1 = getConnection();

			String retrieveProductBarcodeByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_BARCODE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveProductBarcodeByProductIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				prodBarcode = resultSet1.getString("barcode");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return prodBarcode;

	}

	public String retrievePrescriptionFrequencyByVisitID(int visitID) {

		String frequency = "";

		try {

			connection = getConnection();

			String retrievePrescriptionFrequencyByVisitIDQuery = QueryMaker.RETRIEVE_PRESCRIPTION_FREQUENCY_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrievePrescriptionFrequencyByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				frequency = resultSet.getString("frequency");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return frequency;
	}

	public int retrieveLastEnteredPrescriptionID(int visitID) {

		int prescID = 0;

		try {

			connection = getConnection();

			String retrieveLastEnteredPrescriptionIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_PRESCRIPTION_ID;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredPrescriptionIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				prescID = resultSet.getInt("id");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

		}

		return prescID;
	}

	public String retrieveReceiptNo(int clinicID, String suffix) {

		String receiptNo = "";

		int check = 0;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

		try {

			connection = getConnection();

			String retrieveReceiptNoQuery = QueryMaker.RETRIEVE_BILLING_RECEIPT_NO;

			preparedStatement = connection.prepareStatement(retrieveReceiptNoQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				String[] receiptNoArray = resultSet.getString("receiptNo").split("-");
				if (receiptNoArray[0].isEmpty() || receiptNoArray[0] == null || receiptNoArray[0] == "") {
					receiptNo = suffix + "-" + dateFormat.format(new Date()) + "-" + 1;
				} else {
					receiptNo = suffix + "-" + dateFormat.format(new Date()) + "-"
							+ (Integer.parseInt(receiptNoArray[2]) + 1);
				}

			}

			if (check == 0) {

				receiptNo = suffix + "-" + dateFormat.format(new Date()) + "-1";

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return receiptNo;
	}

	public String retrievePracticeSuffix(int practiceID) {

		String suffix = "";

		try {

			connection1 = getConnection();

			String retrievePracticeSuffixQuery = QueryMaker.RETRIEVE_PRACTICE_SUFFIX;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeSuffixQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				suffix = resultSet1.getString("suffix");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return suffix;

	}

	public String updateSurvey(PatientForm patientForm) {
		try {

			connection = getConnection();

			String updateSurveyQuery = QueryMaker.UPDATE_SURVEY;

			preparedStatement = connection.prepareStatement(updateSurveyQuery);

			preparedStatement.setString(1, patientForm.getFirstName());
			preparedStatement.setString(2, patientForm.getMiddleName());
			preparedStatement.setString(3, patientForm.getLastName());
			preparedStatement.setString(4, patientForm.getMobile());
			preparedStatement.setString(5, patientForm.getEmailID());
			preparedStatement.setString(6, patientForm.getDob());
			preparedStatement.setString(7, patientForm.getAge());
			preparedStatement.setString(8, patientForm.getGender());
			preparedStatement.setString(9, patientForm.getAddress());
			preparedStatement.setString(10, patientForm.getTravelOutside());
			preparedStatement.setString(11, patientForm.getAdmitted());
			preparedStatement.setString(12, patientForm.getSuffer_from());
			preparedStatement.setString(13, patientForm.getSurgeries());
			preparedStatement.setString(14, patientForm.getDiabOrHypertension());
			preparedStatement.setString(15, patientForm.getAwareness());
			preparedStatement.setString(16, patientForm.getKnowFamily());
			preparedStatement.setString(17, patientForm.getPsychological_issue());
			preparedStatement.setString(18, patientForm.getTraining());
			preparedStatement.setString(19, patientForm.getTested_positive());
			preparedStatement.setString(20, patientForm.getEmergencyContact_name());
			preparedStatement.setString(21, patientForm.getEmergencyContact_relation());
			preparedStatement.setString(22, patientForm.getEmergencyContact_mobile());
			preparedStatement.setString(23, patientForm.getIDproof());
			preparedStatement.setInt(24, patientForm.getVolunteerID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Survey details udpated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Survey details into PersonalHistory table due to:::"
					+ exception.getMessage());

			status = "error";
		}
		return status;
	}

	public String updatePersonalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updatePersonalHistoryQuery = QueryMaker.UPDATE_PERSONAL_HISTORY;

			preparedStatement = connection.prepareStatement(updatePersonalHistoryQuery);

			preparedStatement.setString(1, patientForm.getPersonalHistorySmoking());
			preparedStatement.setString(2, patientForm.getPersonalHistorySmokingDetails());
			preparedStatement.setString(3, patientForm.getPersonalHistoryDiet());
			preparedStatement.setString(4, patientForm.getPersonalHistoryPurisha());
			preparedStatement.setString(5, patientForm.getPersonalHistoryMootra());
			preparedStatement.setString(6, patientForm.getPersonalHistoryKshudha());
			preparedStatement.setString(7, patientForm.getPersonalHistoryNidra());
			preparedStatement.setString(8, patientForm.getPersonalHistoryOther());
			preparedStatement.setString(9, patientForm.getPersonalHistOther());
			preparedStatement.setString(10, patientForm.getMedHistDiagnosis());
			preparedStatement.setString(11, patientForm.getMedHistComment());
			preparedStatement.setString(12, patientForm.getMedHistOther());
			preparedStatement.setString(13, patientForm.getPersonalHistoryAlcohol());
			preparedStatement.setString(14, patientForm.getPersonalHistoryAlcoholDetails());
			preparedStatement.setString(15, patientForm.getPersonalHistoryTobacco());
			preparedStatement.setString(16, patientForm.getPersonalHistoryTobaccoDetails());
			preparedStatement.setString(17, patientForm.getPersonalHistoryFoodChoice());
			preparedStatement.setString(18, patientForm.getPersonalHistoryFoodChoiceDetails());
			preparedStatement.setInt(19, patientForm.getPatientID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Personal history details udpated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating personal history details into PersonalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public void updateConsultationAppointmentStatus(int patientID, int apptID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateConsultationAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(updateConsultationAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.CONSULTATION);
			// preparedStatement.setString(2, dateFormat.format(new Date()));
			preparedStatement.setInt(2, apptID);
			preparedStatement.setInt(3, patientID);

			preparedStatement.executeUpdate();

			System.out.println("Appointment Status successfully changed to Consultation");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void updatePrescriptionAppointmentStatus(int patientID, int apptID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updatePrescriptionAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(updatePrescriptionAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.PRESCRIPTION);
			// preparedStatement.setString(2, dateFormat.format(new Date()));
			preparedStatement.setInt(2, apptID);
			preparedStatement.setInt(3, patientID);

			preparedStatement.executeUpdate();

			System.out.println("Appointment Status successfully changed to Prescription");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void updateBillingAppointmentStatus(int patientID, int apptID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateBillingAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(updateBillingAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.BILLING);
			// preparedStatement.setString(2, dateFormat.format(new Date()));
			preparedStatement.setInt(2, apptID);
			preparedStatement.setInt(3, patientID);

			preparedStatement.executeUpdate();

			System.out.println("Appointment Status successfully changed to Billing");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public HashMap<String, String> retrieveDiagnosisListForSMS(int practiceID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveDiagnosisListForSMSQuery = QueryMaker.RETRIEVE_DIAGNOSIS_LIST_FOR_SMS;

			preparedStatement = connection.prepareStatement(retrieveDiagnosisListForSMSQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("diagnosis") == null || resultSet.getString("diagnosis") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("diagnosis").isEmpty()) {
						System.out.println("Null found.");
					} else {
						map.put(resultSet.getString("diagnosis"), resultSet.getString("diagnosis"));
					}
				}
			}

			resultSet.close();
			preparedStatement.close();

			// Retrieving diagnosis list from PersonalHistory table
			String retrievePersonalHistoryDiagnosisListForSMSQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_DIAGNOSIS_LIST_FOR_SMS;

			preparedStatement = connection.prepareStatement(retrievePersonalHistoryDiagnosisListForSMSQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("diagnosis") == null || resultSet.getString("diagnosis") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("diagnosis").isEmpty()) {
						System.out.println("Null found.");
					} else {
						map.put(resultSet.getString("diagnosis"), resultSet.getString("diagnosis"));
					}
				}
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public List<String> retrievePatientMobileNoBasedOnDiagnosis(String diagnosis, int practiceID) {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrievePatientMobileNoBasedOnDiagnosisQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS;

			preparedStatement = connection.prepareStatement(retrievePatientMobileNoBasedOnDiagnosisQuery);

			preparedStatement.setString(1, diagnosis);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getString("mobile"));
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public int retrievePracticeIDByClinicID(int clinicID) {

		int practiceID = 0;

		try {

			connection1 = getConnection();

			String retrievePracticeIDByClinicIDQuery = QueryMaker.RETRIEVE_PRACTICE_ID_BY_CLINICID;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeIDByClinicIDQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				practiceID = resultSet1.getInt("practiceID");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return practiceID;
	}

	public String retrievePracticeNameByID(int practiceID) {

		String practiceName = "";

		try {

			connection1 = getConnection();

			String retrievePracticeNameByIDQuery = QueryMaker.RETRIEVE_PRACTICE_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeNameByIDQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				practiceName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return practiceName;
	}

	public String retrieveClinicNameByID(int clinicID) {

		String clincName = "";

		try {

			connection1 = getConnection();

			String retrieveClinicNameByIDQuery = QueryMaker.RETRIEVE_CLINIC_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveClinicNameByIDQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				clincName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clincName;
	}

	public String retrievePatientMobileNoByID(int patientID) {

		String mobile = "";

		try {

			connection1 = getConnection();

			String retrievePatientMobileNoByIDQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePatientMobileNoByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				mobile = resultSet1.getString("mobile");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return mobile;
	}

	public String retrievePatientFirstLastNameByID(int patientID) {

		String patientName = "";

		try {

			connection1 = getConnection();

			String retrievePatientFirstLastNameByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrievePatientFirstLastNameByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientName = resultSet1.getString("firstName") + " " + resultSet1.getString("lastName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientName;
	}

	public String retrievePatientFirstMiddleLastNameByID(int patientID) {

		String patientName = "";

		try {

			connection1 = getConnection();

			String retrievePatientFirstMiddleLastNameByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_MIDDLE_LAST_NAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrievePatientFirstMiddleLastNameByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientName = resultSet1.getString("firstName") + " " + resultSet1.getString("middleName") + " "
						+ resultSet1.getString("lastName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientName;
	}

	public String retrieveAge(PatientForm form) {

		String age = "";

		try {

			connection1 = getConnection();

			String retrieveAgeByIDQuery = QueryMaker.RETRIEVE_PATIENT_AGE_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrieveAgeByIDQuery);

			preparedStatement1.setString(1, form.getFirstName());
			preparedStatement1.setString(2, form.getMiddleName());
			preparedStatement1.setString(3, form.getLastName());

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				age = resultSet1.getString("age");
				System.out.println("inside while age=" + age);
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return age;
	}

	public String retrieveUsernameByID(int patientID) {
		String uname = "";
		try {

			connection1 = getConnection();

			String retrieveUnameByIDQuery = QueryMaker.RETRIEVE_PATIENT_USERNAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrieveUnameByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				uname = resultSet1.getString("username");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return uname;

	}

	public String retrieveUsernameBYuserID(int userID) {
		String uname = "";
		try {
			System.out.println("userid in try..:" + userID);
			connection1 = getConnection();

			String retrieveUnameByIDQuery = QueryMaker.RETRIEVE_USER_USERNAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrieveUnameByIDQuery);

			preparedStatement1.setInt(1, userID);

			resultSet1 = preparedStatement1.executeQuery();

			System.out.println("outside while..");
			while (resultSet1.next()) {
				uname = resultSet1.getString("username");
				System.out.println("username in while::" + uname);
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return uname;

	}

	public String retrievePasswordByID(int patientID) {
		String passw = "";
		try {

			connection1 = getConnection();

			String retrievePassByIDQuery = QueryMaker.RETRIEVE_PATIENT_PASSWORD_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrievePassByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				passw = resultSet1.getString("password");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return passw;

	}

	public double retrieveBillAmountByVisitID(int visitID) {

		double billAmount = 0D;

		try {

			connection1 = getConnection();

			String retrieveBillAmountByVisitIDQuery = QueryMaker.RETRIEVE_BILL_AMOUNT_BY_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveBillAmountByVisitIDQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				billAmount = resultSet1.getDouble("netAmount");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return billAmount;
	}

	public String retrieveVisitDateAndTime(int visitID) {

		String dateAndTime = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection1 = getConnection();

			String retrieveVisitDateAndTimeQuery = QueryMaker.RETRIEVE_VISIT_DATE_AND_TIME;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitDateAndTimeQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				String date = "";

				if (resultSet1.getString("visitDate") == null || resultSet1.getString("visitDate") == ""
						|| resultSet1.getString("visitDate").isEmpty()) {
					date = "";
				} else {
					date = dateFormat.format(resultSet1.getDate("visitDate"));
				}

				String time = "";

				if (resultSet1.getString("visitTimeFrom") == null || resultSet1.getString("visitTimeFrom") == ""
						|| resultSet1.getString("visitTimeFrom").isEmpty()) {
					time = "";
				} else {

					if (resultSet1.getString("visitTimeFrom").equals("null:null null")) {
						time = "";
					} else {
						time = resultSet1.getString("visitTimeFrom");
					}

				}

				if (time == "") {
					dateAndTime = date;
				} else {
					dateAndTime = date + " " + time;
				}
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return dateAndTime;
	}

	public List<String> retrievePrescriptionListForSMSByVisitID(int visitID) {

		List<String> list = new ArrayList<String>();

		try {

			connection1 = getConnection();

			String retrievePrescriptionListForSMSByVisitIDQuery = QueryMaker.RETRIEVE_PRESCRIPTION_LIST_FOR_SMS_BY_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePrescriptionListForSMSByVisitIDQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				String str = resultSet1.getString("tradeName") + " " + resultSet1.getString("dosage") + " "
						+ resultSet1.getString("frequency") + " " + resultSet1.getInt("quantity") + " "
						+ resultSet1.getString("comment");

				list.add(str);

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrievePatientEmailByID(int patientID) {

		String email = "";

		try {

			connection1 = getConnection();

			String retrievePatientEmailByIDQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePatientEmailByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				email = resultSet1.getString("email");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return email;
	}

	public String retrievePatientRegistrationNoByID(int patientID) {

		String regNo = "";

		try {

			connection1 = getConnection();

			String retrievePatientRegistrationNoByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrievePatientRegistrationNoByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				regNo = resultSet1.getString("practiceRegNumber");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return regNo;
	}

	public int retrieveVisitTypeID(int practiceID) {

		int visitTypeID = 0;

		try {

			connection1 = getConnection();

			String retrieveVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_ID_FOR_NEW_VISIT_FLAG;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitTypeIDQuery);

			preparedStatement1.setInt(1, practiceID);
			preparedStatement1.setInt(2, 1);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				visitTypeID = resultSet1.getInt("id");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return visitTypeID;
	}

	public String retrievePatientGenderByPatientID(int patientID) {

		String patientGender = "";

		try {

			connection = getConnection();

			String retrievePatientGenderByPatientIDQuery = QueryMaker.RETRIEVE_PATIENT_AGE_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePatientGenderByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientGender = "" + resultSet.getString("gender");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patient gender based on patient ID due to:::"
					+ exception.getMessage());

		}

		return patientGender;
	}

	public String updatePatient(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String verifyPatientDetailQuery = QueryMaker.UPDATE_PATIENT_DETAILS1;

			preparedStatement = connection.prepareStatement(verifyPatientDetailQuery);

			preparedStatement.setString(1, patientForm.getGender());
			preparedStatement.setString(2, patientForm.getRhFactor());

			if (patientForm.getDateOfBirth() == null || patientForm.getDateOfBirth() == "") {
				preparedStatement.setString(3, null);
			} else {
				if (patientForm.getDateOfBirth().isEmpty()) {
					preparedStatement.setString(3, null);
				} else {
					preparedStatement.setString(3, dateFormat1.format(dateFormat.parse(patientForm.getDateOfBirth())));
				}
			}

			if (patientForm.getAge() == null || patientForm.getAge() == "") {
				preparedStatement.setInt(5, 0);
			} else {
				if (patientForm.getAge().isEmpty()) {
					preparedStatement.setInt(5, 0);
				} else {
					preparedStatement.setString(5, patientForm.getAge());
				}
			}

			preparedStatement.setString(4, patientForm.getBloodGroup());
			preparedStatement.setString(6, patientForm.getMobile());
			preparedStatement.setString(7, patientForm.getEmailID());
			preparedStatement.setString(8, patientForm.getPhone());
			preparedStatement.setString(9, patientForm.getAddress());
			preparedStatement.setString(10, patientForm.getCity());
			preparedStatement.setString(11, patientForm.getState());
			preparedStatement.setString(12, patientForm.getCountry());
			preparedStatement.setInt(13, patientForm.getPracticeID());
			preparedStatement.setString(14, patientForm.getEC());
			preparedStatement.setString(15, patientForm.getOccupation());
			preparedStatement.setInt(16, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("patient details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public HashMap<String, String> retrieveAppointmentTimeById(int apptID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection1 = getConnection();

			String retrieveAppointmentTimeByIdQuery = QueryMaker.RETRIEVE_APPOINTMENT_TIME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveAppointmentTimeByIdQuery);

			preparedStatement1.setInt(1, apptID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				map.put("timeFrom", resultSet1.getString("apptTimeFrom"));
				map.put("timeTo", resultSet1.getString("apptTimeTo"));
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

		}

		return map;
	}

	public int retrieveVisitIDByPatientIDAndApptID(int patientID, int aptID) {

		int visitID = 0;

		try {

			connection1 = getConnection();

			String retrieveVisitIDByPatientIDAndApptIDQuery = QueryMaker.RETRIEVE_VISIT_ID_BY_PATIENT_ID_AND_APT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitIDByPatientIDAndApptIDQuery);

			preparedStatement1.setInt(1, patientID);
			preparedStatement1.setInt(2, aptID);
			preparedStatement1.setString(3, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				visitID = resultSet1.getInt("id");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

		}

		return visitID;
	}

	public String retirevePatientClinicRegistrationNo(String clinicSuffix, int clinicID) {

		String regNo = "";

		int check = 0;

		try {
			// System.out.println("clinicSuffix ::: " + clinicSuffix + "clinicID ::: " +
			// clinicID);
			connection = getConnection();

			String retirevePatientClinicRegistrationNoQuery = QueryMaker.RETRIEVE_CLINIC_REGISTRATION_NO;

			preparedStatement = connection.prepareStatement(retirevePatientClinicRegistrationNoQuery);

			preparedStatement.setString(1, "%" + clinicSuffix + "%");
			preparedStatement.setInt(2, clinicID);
			// System.out.println("query is ::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
				String[] regNoArray = resultSet.getString("regNumber").split("-");
				// System.out.println("regNoArray[1] :::: "+regNoArray[1]);
				int number = Integer.parseInt(regNoArray[regNoArray.length - 1]) + 1;
				// Creating new array excluding last element
				String[] regArray = Arrays.copyOf(regNoArray, regNoArray.length - 1);
				regNo = String.join("-", regArray) + "-" + number;
				// System.out.println("registration number :: " + regNo);
			}

			if (check == 0) {
				regNo = clinicSuffix + "-1";
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving clinic registration no due to:::" + exception.getMessage());
		}

		return regNo;
	}

	public String insertClinicRegistration(int patientID, int clinicID, String regNo) {

		try {

			connection = getConnection();

			String insertClinicRegistrationQuery = QueryMaker.INSERT_CLINIC_REGISTRATION;

			preparedStatement = connection.prepareStatement(insertClinicRegistrationQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, regNo);
			preparedStatement.setInt(3, patientID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public JSONObject verifyPatientExists(int patientID, int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String verifyPatientExistsQuery = QueryMaker.VERIFY_PATIENT_EXISTS_FOR_CURRENT_CLINIC;

			preparedStatement = connection.prepareStatement(verifyPatientExistsQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

			object.put("patCheck", check);

			array.add(object);

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("patCheck", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public boolean verifyPatientExistsForCurrentClinic(int patientID, int clinicID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyPatientExistsForCurrentClinicQuery = QueryMaker.VERIFY_PATIENT_EXISTS_FOR_CURRENT_CLINIC;

			preparedStatement = connection.prepareStatement(verifyPatientExistsForCurrentClinicQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;

		}

		return check;
	}

	public String retrieveClinicNameByClinicID(int clinicID) {
		String ClinicName = "";

		try {

			connection1 = getConnection();

			String retrieveClinicNameByClinicIDQuery = QueryMaker.RETRIEVE_CLINIC_NAME_BY_CLINIC_ID;

			preparedStatement2 = connection1.prepareStatement(retrieveClinicNameByClinicIDQuery);

			preparedStatement2.setInt(1, clinicID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {
				ClinicName = resultSet2.getString("name");
			}

			resultSet2.close();
			preparedStatement2.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return ClinicName;
	}

	public String retrieveClinicRegNoByClinicID(int clinicID, int patientID) {

		String regNo = "";

		try {

			connection1 = getConnection();

			String retrieveClinicRegNoByClinicIDQuery = QueryMaker.RETRIEVE_CLINIC_REG_NO_BY_CLINIC_ID;

			preparedStatement2 = connection1.prepareStatement(retrieveClinicRegNoByClinicIDQuery);

			preparedStatement2.setInt(2, patientID);
			preparedStatement2.setInt(1, clinicID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {
				regNo = resultSet2.getString("regNumber");
			}

			resultSet2.close();
			preparedStatement2.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return regNo;
	}

	public String retrieveClinicRegNoByPatientID(int patientID) {

		String regNo = "";

		try {

			connection1 = getConnection();

			String retrieveClinicRegNoByPatientIDQuery = QueryMaker.RETRIEVE_CLINIC_REG_NO_BY_PATIENT_ID;

			preparedStatement2 = connection1.prepareStatement(retrieveClinicRegNoByPatientIDQuery);

			preparedStatement2.setInt(1, patientID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {
				regNo = resultSet2.getString("regNumber");
			}

			resultSet2.close();
			preparedStatement2.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return regNo;
	}

	public List<String> retrievePatientMobileNoBasedOnDiagnosisForClinic(String diagnosis, int clinicID) {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrievePatientMobileNoBasedOnDiagnosisForClinicQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS_FOR_CLINIC;

			preparedStatement = connection.prepareStatement(retrievePatientMobileNoBasedOnDiagnosisForClinicQuery);

			preparedStatement.setString(1, diagnosis);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getString("mobile"));
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public JSONObject retrievePatientListByDiagnosis(String diagnosis, int practiceID, String praCheck, int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			if (praCheck.equals("Practice")) {

				String retrievePatientListByDiagnosisQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS;

				preparedStatement = connection.prepareStatement(retrievePatientListByDiagnosisQuery);

				preparedStatement.setString(1, diagnosis);
				preparedStatement.setInt(2, practiceID);

			} else {

				String retrievePatientListByDiagnosisQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS_FOR_CLINIC;

				preparedStatement = connection.prepareStatement(retrievePatientListByDiagnosisQuery);

				preparedStatement.setString(1, diagnosis);
				preparedStatement.setInt(2, clinicID);

			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				if (resultSet.getString("patientName") == null || resultSet.getString("patientName") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("patientName").isEmpty()) {
						System.out.println("Null found.");
					} else {
						object = new JSONObject();

						object.put("mobile", resultSet.getString("mobile"));
						object.put("name", resultSet.getString("patientName"));
						object.put("check", check);

						array.add(object);

						values.put("Release", array);
					}
				}
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrievePatientListForCredit(int practiceID, int clinicID, String praCheck) {

		JSONObject values = null;
		JSONArray array = new JSONArray();
		values = new JSONObject();
		JSONObject object = null;
		int check = 0;
		try {
			connection = getConnection();
			List<Integer> visitIDList = retrieveVisitIDForCredit(practiceID, clinicID, praCheck);

			String visitID = "";

			if (visitIDList == null) {
				System.out.println("No visitID found from Receipt for credits.");
			} else {
				for (Integer visitIDString : visitIDList) {

					System.out.println("visitidSring is::" + visitIDString);

					visitID += "," + visitIDString;

					if (visitID != null && visitID.length() > 0 && visitID.charAt(visitID.length() - 1) == ',') {
						visitID = visitID.substring(0, visitID.length() - 1);
						System.out.println("visitID: " + visitID);
					}

				}

				visitID = visitID.substring(1);

				System.out.println("visitIDList: " + visitID);

				System.out.println("visitIDList: " + visitID + "--" + clinicID);

				String retrievePatientListForCreditQuery = "SELECT DISTINCT(SELECT CONCAT(firstName,' ',lastName) FROM Patient where id = v.patientID AND activityStatus = 'Active') AS patientName, (SELECT mobile FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS mobile, patientID FROM Visit AS v WHERE v.id IN ("
						+ visitID + ")";
				System.out.println("query::" + retrievePatientListForCreditQuery);
				// QueryMaker.RETRIEVE_PATIENT_DETAILS_FOR_CREDIT_BY_VISIT_ID;

				// preparedStatement.setInt(1, visitIDString);
				preparedStatement = connection.prepareStatement(retrievePatientListForCreditQuery);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					System.out.println("query::" + retrievePatientListForCreditQuery);

					System.out.println("Patient: " + resultSet.getString("patientName"));

					check = 1;
					if (resultSet.getString("patientName") == null || resultSet.getString("patientName") == "") {
						System.out.println("Null found.");
					} else {
						if (resultSet.getString("patientName").isEmpty()) {
							System.out.println("Null found.");
						} else {
							object = new JSONObject();
							object.put("patientID", resultSet.getInt("patientID"));
							object.put("patientName", resultSet.getString("patientName"));
							object.put("check", check);
							array.add(object);
							values.put("Release", array);
						}
					}
				}

			}
			if (check == 0) {
				object = new JSONObject();
				object.put("check", check);
				array.add(object);
				values.put("Release", array);
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
			return values;
		} catch (Exception exception) {
			exception.printStackTrace();
			object.put("check", check);
			array.add(object);
			values.put("Release", array);
			return values;
		}

	}

	public List<Integer> retrieveVisitIDForCredit(int practiceID, int clinicID, String pracCheck) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			connection1 = getConnection();

			if (pracCheck.equals("Practice")) {

				String retrieveVisitIDForCreditQuery = QueryMaker.RETRIEVE_VISIT_LIST_FOR_PRACTICE;

				preparedStatement1 = connection1.prepareStatement(retrieveVisitIDForCreditQuery);

				preparedStatement1.setInt(1, practiceID);

			} else {

				String retrievePatientListForCreditQuery = QueryMaker.RETRIEVE_VISIT_LIST_FOR_CLINIC;

				preparedStatement1 = connection1.prepareStatement(retrievePatientListForCreditQuery);

				preparedStatement1.setInt(1, clinicID);

			}

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				list.add(resultSet1.getInt("visitID"));
			}

			resultSet1.close();
			preparedStatement1.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public double retrievePatientCredit(int patientID, int practiceID, int clinicID, String check) {

		double credit = 0D;

		try {

			connection1 = getConnection();

			if (check.equals("Practice")) {

				String retrievePatientCreditQuery = QueryMaker.RETRIEVE_PATIENT_CREDIT_FOR_PRACTICE;

				preparedStatement1 = connection1.prepareStatement(retrievePatientCreditQuery);

				preparedStatement1.setInt(1, practiceID);
				preparedStatement1.setInt(2, patientID);

			} else {

				String retrievePatientCreditQuery = QueryMaker.RETRIEVE_PATIENT_CREDIT_FOR_CLINIC;

				preparedStatement1 = connection1.prepareStatement(retrievePatientCreditQuery);

				preparedStatement1.setInt(1, clinicID);
				preparedStatement1.setInt(2, patientID);

			}

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				credit = resultSet1.getDouble("balPayment");
			}

			resultSet1.close();
			preparedStatement1.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return credit;
	}

	public List<String> retrieveAllPatientForCredit(int practiceID, int clinicID, String check) {

		List<String> list = new ArrayList<String>();

		try {

			connection1 = getConnection();

			if (check.equals("Practice")) {

				String retrieveAllPatientForCreditQuery = QueryMaker.RETRIEVE_ALL_PATIENT_CREDIT_FOR_PRACTICE;

				preparedStatement1 = connection1.prepareStatement(retrieveAllPatientForCreditQuery);

				preparedStatement1.setInt(1, practiceID);

			} else {

				String retrieveAllPatientForCreditQuery = QueryMaker.RETRIEVE_ALL_PATIENT_CREDIT_FOR_CLINIC;

				preparedStatement1 = connection1.prepareStatement(retrieveAllPatientForCreditQuery);

				preparedStatement1.setInt(1, clinicID);

			}

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				String finalStrint = resultSet1.getDouble("balPayment") + "=" + resultSet1.getInt("patientID");

				list.add(finalStrint);
			}

			resultSet1.close();
			preparedStatement1.close();

			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public JSONObject retrieveFrequencyCount(String frequency, int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveFrequencyCountQuery = QueryMaker.RETRIEVE_FREQUENCY_COUNT;

			preparedStatement = connection.prepareStatement(retrieveFrequencyCountQuery);

			preparedStatement.setString(1, frequency);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("freqCount", resultSet.getDouble("count"));

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving frequency count from Frequency table based on frequency");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public String disablePatient(int patientID, String status) {

		try {

			connection = getConnection();

			String disablePatientQuery = QueryMaker.DISABLE_PATIENT;

			preparedStatement = connection.prepareStatement(disablePatientQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, patientID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Patient disabled successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public JSONObject retrieveActivePatientList(String praCheck, int practiceID, int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			if (praCheck.equals("Practice")) {

				String retrieveActivePatientListQuery = QueryMaker.RETRIEVE_ACTIVE_PATIENT_LIST_FOR_PRACTICE;

				preparedStatement = connection.prepareStatement(retrieveActivePatientListQuery);

				preparedStatement.setInt(1, practiceID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);

			} else {

				String retrieveActivePatientListQuery = QueryMaker.RETRIEVE_PatientLists;

				preparedStatement = connection.prepareStatement(retrieveActivePatientListQuery);

				preparedStatement.setInt(1, clinicID);

			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				if (resultSet.getString("patientName") == null || resultSet.getString("patientName") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("patientName").isEmpty()) {
						System.out.println("Null found.");
					} else {
						object = new JSONObject();

						object.put("mobile", resultSet.getString("mobile"));
						object.put("name", resultSet.getString("patientName"));
						object.put("patientID", resultSet.getInt("id"));
						object.put("check", check);

						array.add(object);

						values.put("Release", array);
					}
				}
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public List<Integer> retrievePatientIDListForCustomizedSMS(String check, int practiceID, int clinicID) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			connection = getConnection();

			if (check.equals("Practice")) {

				String retrievePatientIDListForCustomizedSMSQuery = QueryMaker.RETRIEVE_ACTIVE_PATIENT_LIST_FOR_PRACTICE;

				preparedStatement = connection.prepareStatement(retrievePatientIDListForCustomizedSMSQuery);

				preparedStatement.setInt(1, practiceID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);

			} else {

				String retrievePatientIDListForCustomizedSMSQuery = QueryMaker.RETRIEVE_PatientLists;

				preparedStatement = connection.prepareStatement(retrievePatientIDListForCustomizedSMSQuery);

				preparedStatement.setInt(1, clinicID);

			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("patientName") == null || resultSet.getString("patientName") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("patientName").isEmpty()) {
						System.out.println("Null found.");
					} else {
						list.add(resultSet.getInt("id"));
					}
				}
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveClinicianNameByID(int clinicianID) {

		String clinicianName = "";

		try {

			connection1 = getConnection();

			String retrieveClinicianNameByIDQUery = QueryMaker.RETRIEVE_CLINICIAN_NAME_BY_ID;

			preparedStatement2 = connection1.prepareStatement(retrieveClinicianNameByIDQUery);

			preparedStatement2.setInt(1, clinicianID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				clinicianName = resultSet2.getString("empName");

			}

			resultSet2.close();
			preparedStatement2.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clinicianName;
	}

	public List<PatientForm> retrieveMyAppointmentList(int userID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			String retrieveMyAppointmentListQuery = QueryMaker.RETRIEVE_MY_APPOINTMENT_LIST;

			preparedStatement3 = connection.prepareStatement(retrieveMyAppointmentListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, userID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));

				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));

				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	public List<PatientForm> retrieveMyAppointmentWeekList(int userID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			String retrieveMyAppointmentWeekListQuery = QueryMaker.RETRIEVE_MY_APPOINTMENT_WEEK_LIST;

			preparedStatement3 = connection.prepareStatement(retrieveMyAppointmentWeekListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, userID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));

				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));

				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	public List<PatientForm> retrieveMyAppointmentMonthList(int userID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm form = null;

		/*
		 * Converting date from Database into DD-MM-YYYY format in order to display it
		 * on UI
		 */
		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {

			connection = getConnection();

			String retrieveMyAppointmentMonthListQuery = QueryMaker.RETRIEVE_MY_APPOINTMENT_MONTH_LIST;

			preparedStatement3 = connection.prepareStatement(retrieveMyAppointmentMonthListQuery);

			preparedStatement3.setString(1, ActivityStatus.CANCELLED);
			preparedStatement3.setInt(2, userID);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				form = new PatientForm();

				String dbDate = resultSet3.getString("apptDate");

				date = dateToBeFormatted.parse(dbDate);

				form.setAppointmentDate(dateToBeParsed123.format(date));
				String[] timeFromArr = resultSet3.getString("apptTimeFrom").split(" ");

				String[] array = timeFromArr[0].split(":");

				String timeFrom = array[0] + ":" + array[1] + " " + timeFromArr[1];

				form.setAptTimeFrom(timeFrom);

				String[] timeToArr = resultSet3.getString("apptTimeTo").split(" ");

				String[] array1 = timeToArr[0].split(":");

				String timeTo = array1[0] + ":" + array1[1] + " " + timeToArr[1];

				form.setAptTimeTo(timeTo);
				form.setPatientID(resultSet3.getInt("patientID"));

				/*
				 * Retrieving patientName from PatientID
				 */
				form.setPatientName(retrievePatientFullName(form.getPatientID()));

				form.setAptID(resultSet3.getInt("id"));
				form.setAptStatus(resultSet3.getString("status"));

				form.setClinicianName(retrieveClinicianNameByID(resultSet3.getInt("clinicianID")));
				form.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, form.getPatientID()));

				/*
				 * Storing all the values in a string
				 */
				/*
				 * String finalValues = form.getAppointmentDate() + "=" + form.getAptTimeFrom()
				 * + "=" + form.getAptTimeTo() + "=" + retrieveClinicRegNoByClinicID(clinicID,
				 * form.getPatientID()) + "=" + form.getPatientName() + "=" + form.getAptID() +
				 * "=" + form.getAptStatus() + "=" + form.getClinicianName() + "=" +
				 * form.getPatientID();
				 */

				/*
				 * Adding form into List
				 */
				list.add(form);

			}

			resultSet3.close();
			preparedStatement3.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment List from Appointment table due to:::"
					+ exception.getMessage());
		}

		return list;
	}

	public List<Integer> retrieveHeadUserIDList(int clinicID) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			connection = getConnection();

			String retrieveHeadUserIDListQuery = QueryMaker.RETRIEVE_HEAD_USER_ID_LIST;

			preparedStatement = connection.prepareStatement(retrieveHeadUserIDListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getInt("userID"));

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveEmployeeMailID(int employeeID) {

		String email = "";

		try {

			connection1 = getConnection();

			String retrieveEmployeeMailIDQuery = QueryMaker.RETRIEVE_EMPLOYEE_EMAIL_AND_MOBILE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveEmployeeMailIDQuery);

			preparedStatement1.setInt(1, employeeID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				email = resultSet1.getString("email");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return email;
	}

	public String retrieveEmployeeMobileNo(int employeeID) {

		String mobile = "";

		try {

			connection1 = getConnection();

			String retrieveEmployeeMobileNoQuery = QueryMaker.RETRIEVE_EMPLOYEE_EMAIL_AND_MOBILE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveEmployeeMobileNoQuery);

			preparedStatement1.setInt(1, employeeID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				mobile = resultSet1.getString("mobile");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return mobile;
	}

	public HashMap<String, String> retrievePaymentDetailsBy(int receiptID) {

		HashMap<String, String> map = new HashMap<String, String>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection1 = getConnection();

			String retrieveEmployeeIDByUserIDQuery = QueryMaker.RETRIEVE_PAYMENT_DETAILS;

			preparedStatement1 = connection1.prepareStatement(retrieveEmployeeIDByUserIDQuery);

			preparedStatement1.setInt(1, receiptID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				map.put("chequeNumber", resultSet1.getString("chequeNumber"));
				map.put("bankName", resultSet1.getString("bankName"));
				map.put("bankBranch", resultSet1.getString("bankBranch"));

				if (resultSet1.getString("chequeDate") == null || resultSet1.getString("chequeDate") == "") {
					map.put("chequeDate", "");
				} else {
					if (resultSet1.getString("chequeDate").isEmpty()) {
						map.put("chequeDate", "");
					} else {
						map.put("chequeDate", dateFormat.format(resultSet1.getDate("chequeDate")));
					}
				}
				map.put("chequeAmount", "" + resultSet1.getDouble("chequeAmount"));
				map.put("cashPaid", "" + resultSet1.getDouble("cashPaid"));
				map.put("cashToReturn", "" + resultSet1.getString("cashToReturn"));
				map.put("cardNumber", resultSet1.getString("cardNumber"));
				map.put("chequeIssueBy", resultSet1.getString("chequeIssueBy"));
				map.put("creditNote", "" + resultSet1.getDouble("creditNote"));
				map.put("mobile", "" + resultSet1.getString("mobile"));
				map.put("otherMode", resultSet1.getString("otherMode"));
				map.put("cashAdjStatus", resultSet1.getString("cashAdjStatus"));
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public boolean verifyDataExists(int patientID, String tableName) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsQuery = "SELECT patientID FROM " + tableName + " WHERE patientID = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String verifyFrameDetailsExist(int visitID, String tableName) {

		String check = "no";

		try {

			connection1 = getConnection();

			String verifyFrameDetailsExist = "SELECT id FROM " + tableName
					+ " WHERE optometryID = (SELECT id FROM Optometry WHERE visitID = ?)";

			preparedStatement1 = connection1.prepareStatement(verifyFrameDetailsExist);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = "yes";
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "no";
		}

		return check;
	}

	public List<Integer> retrievePatientIDByAppointmentID(String appointmentID) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			connection = getConnection();

			String retrievePatientIDByAppointmentIDQuery = "SELECT distinct(patientID) FROM Appointment WHERE id IN ("
					+ appointmentID + ")";

			preparedStatement = connection.prepareStatement(retrievePatientIDByAppointmentIDQuery);

			// preparedStatement.setString(1, appointmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getInt("patientID"));
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public JSONObject verifyVisitExistsForAppointment(int patientID, int appointmentID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		int visitID = 0;

		try {
			connection = getConnection();

			String verifyVisitExistsForAppointmentQuery = QueryMaker.VERIFY_VISIT_EXISTS_FOR_APPOINTMENT;

			preparedStatement = connection.prepareStatement(verifyVisitExistsForAppointmentQuery);

			preparedStatement.setInt(1, appointmentID);
			preparedStatement.setInt(2, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
				visitID = resultSet.getInt("id");
			}

			object.put("check", check);
			object.put("visitID", visitID);

			array.add(object);

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", 0);
			object.put("visitID", 0);

			object.put("ErrorMessage",
					"Exception occured while verifying whether visit exists for appointmentID and patientID");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject verifyUserPassword(String password, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		// Encrypting password in order to check into DB
		String encryptedPIN = EncDescUtil.EncryptText(password);

		boolean result = false;

		try {
			connection = getConnection();

			String verifyUserPasswordQuery = QueryMaker.VERIFY_USER_PASSWORD;

			preparedStatement = connection.prepareStatement(verifyUserPasswordQuery);

			preparedStatement.setString(1, encryptedPIN);
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				result = true;

			}

			/*
			 * Checking whether user password is correct or not and setting variable
			 * according, so as to edit the CRF details
			 */
			if (result) {

				object.put("PINCheck", "check");

				array.add(object);

				values.put("Release", array);

				resultSet.close();
				preparedStatement.close();

				connection.close();

				return values;

			} else {

				object.put("PINCheck", "uncheck");

				array.add(object);

				values.put("Release", array);

				resultSet.close();
				preparedStatement.close();

				connection.close();

				return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while verifying whether user password is corrent or not");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public boolean verifyVisitExists(String tableName, int visitID) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsQuery = "SELECT id FROM " + tableName + " WHERE visitID = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
				System.out.println("yes visit exist for billlist");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
			System.out.println("visit not exist for billlist");
		}

		return check;
	}

	public int retrieveVisitTypeIDByVisitID(int visitID) {

		int visitTypeID = 0;

		try {

			connection1 = getConnection();

			String retrieveVisitTypeIDByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_ID_BY_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitTypeIDByVisitIDQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				visitTypeID = resultSet1.getInt("visitTypeID");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return visitTypeID;
	}

	public void updateAttendedByInAppointmentForPatient(int userID, int appointmentID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateAttendedByInAppointmentForPatientQuery = QueryMaker.UPDATE_ATTENDED_BY_IN_APPOINTMENT_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(updateAttendedByInAppointmentForPatientQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, appointmentID);

			preparedStatement.executeUpdate();

			System.out.println("Attended by updated successfully into Appointment table for patient");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public String retrievePersonalHistroyDiagnosis(int patientID) {

		String diagnosis = "";

		try {

			connection1 = getConnection();

			String retrievePersonalHistroyDiagnosisQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID;

			preparedStatement1 = connection1.prepareStatement(retrievePersonalHistroyDiagnosisQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				diagnosis = resultSet1.getString("diagnosis");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return diagnosis;
	}

	public String retrievePatientCountryByID(int patientID) {

		String country = "";

		try {

			connection = getConnection();

			String retrievePatientCountryByIDQuery = QueryMaker.RETRIEVE_PATIENT_COUNTRY_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePatientCountryByIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				country = resultSet.getString("country");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return country;
	}

	public String retrieveJSPPageNameByVisitTypeID(int visitTypeID) {

		String jspPageName = "";

		try {

			connection = getConnection();

			String retrieveJSPPageNameByVisitTypeIDQuery = QueryMaker.RETRIEVE_JSP_PAGE_NAME_BY_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(retrieveJSPPageNameByVisitTypeIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jspPageName = resultSet.getString("jspPageName");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return jspPageName;
	}

	public List<PatientForm> retrievecomplaintList(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retreiveComplaintsDetailsByVisitIDQuery = QueryMaker.RETRIEVE_PresentComplaint_Details_By_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreiveComplaintsDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setComplaintID(resultSet.getInt("id"));
				patientForm.setSymptomCheckName(resultSet.getString("complaints"));
				patientForm.setDuration(resultSet.getInt("duration"));
				patientForm.setOther(resultSet.getString("other"));
				patientForm.setComments(resultSet.getString("comments"));

				list.add(patientForm);
			}

			System.out.println("Present Complaint details are retrieved successfully.." + list.size());

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveMedicalHistoryList(int patientID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retreiveMedicalHistoryDetailsByVisitIDQuery = QueryMaker.RETRIEVE_MedicalHistory_Details_By_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreiveMedicalHistoryDetailsByVisitIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setHistoryID(resultSet.getInt("id"));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setDescription(resultSet.getString("description"));
				patientForm.setComments(resultSet.getString("comments"));

				list.add(patientForm);
			}
			System.out.println("medical history List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveCurrentMedicationList(int patientID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retreiveCurrentMedicationDetailsByVisitIDQuery = QueryMaker.RETRIEVE_CurrentMedication_Details_By_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreiveCurrentMedicationDetailsByVisitIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setMedicationID(resultSet.getInt("id"));
				patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setDuration(resultSet.getInt("duration"));
				patientForm.setComments(resultSet.getString("comments"));

				list.add(patientForm);
			}

			System.out.println("Current medication list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrievePrescriptionList(int patientID, int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrievePrescriptionListQuery = QueryMaker.RETREIVE_PRESCRIPTION_LIST;

			preparedStatement = connection.prepareStatement(retrievePrescriptionListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				// patientForm.setDrugName(resultSet.getString("drugName"));
				patientForm.setTradeName(resultSet.getString("tradeName"));
				patientForm.setDosage(resultSet.getDouble("dosage"));
				if (resultSet.getString("numberOfDays").contains(",")) {
					patientForm.setNoOfDays(resultSet.getString("numberOfDays").replaceAll(",", "<br>"));
				} else {
					patientForm.setNoOfDays(resultSet.getString("numberOfDays"));
				}
				patientForm.setFrequency(resultSet.getString("frequency").replaceAll(",", "<br>"));
				patientForm.setComment(resultSet.getString("comment"));
				patientForm.setProductQuantity(resultSet.getDouble("quantity"));
				patientForm.setPatientID(patientID);
				patientForm.setVisitID(visitID);
				patientForm.setCount("" + count + "");
				patientForm.setCategory(resultSet.getString("category"));
				patientForm.setBillingCategoryID(resultSet.getInt("categoryID"));

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving prescription list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public List<PatientForm> retrieveSurveyDetails(PatientForm patientForm, int clinicID, int lastVolunteerID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		try {

			System.out.println("clinicid is::" + clinicID);
			System.out.println("volunteer is::" + lastVolunteerID);

			connection = getConnection();

			String retrieveSurveyByIDQuery = QueryMaker.RETREIVE_SURVEY_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSurveyByIDQuery);

			preparedStatement.setInt(1, lastVolunteerID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setEmailID(resultSet.getString("email"));
				patientForm.setDob(resultSet.getString("dob"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setTravelOutside(resultSet.getString("travelOutside"));
				patientForm.setAdmitted(resultSet.getString("admitted"));
				patientForm.setSuffer_from(resultSet.getString("suffer_from"));
				patientForm.setSurgeries(resultSet.getString("surgeries"));
				patientForm.setDiabOrHypertension(resultSet.getString("diabOrHypertension"));
				patientForm.setAwareness(resultSet.getString("awareness"));
				patientForm.setKnowFamily(resultSet.getString("knowFamily"));
				patientForm.setPsychological_issue(resultSet.getString("psychological_issue"));
				patientForm.setTraining(resultSet.getString("training"));
				patientForm.setTested_positive(resultSet.getString("tested_positive"));
				patientForm.setEmergencyContact_name(resultSet.getString("emergencyContact_name"));
				patientForm.setEmergencyContact_relation(resultSet.getString("emergencyContact_relation"));
				patientForm.setEmergencyContact_mobile(resultSet.getString("emergencyContact_mobile"));
				patientForm.setIDproof(resultSet.getString("iDproof"));
			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving survey details based on volunteerID & clinicID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public List<PatientForm> retrieveDefaultPatientDetails(int patientID, int lastOPDVisitID, int clinicID, int aptID,
			int VisitTypeID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		System.out.println("LAST OPD VISIT ID::" + lastOPDVisitID);
		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));

					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				// patientForm.setLastVisitID(lastOPDVisitID);
				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
			}

			/*
			 * Retrieving visit dureation from VisitTypeID
			 */

			int visitDuration = 0;

			String retrieveAppointmentDurationQuery = QueryMaker.RETRIEVE_VISIT_DURATION_BY_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(retrieveAppointmentDurationQuery);

			preparedStatement.setInt(1, VisitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				visitDuration = resultSet.getInt("visitDuration");

			}

			SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

			Date date = new Date();

			System.out.println(dateFormat.format(date));

			if (aptID == 0) {

				String timeFrom = null;

				String timeTo = null;

				timeFrom = timeFormat.format(date);

				System.out.println("timeFrom::" + timeFrom);

				Calendar visitTimeTo = Calendar.getInstance();
				visitTimeTo.add(Calendar.MINUTE, visitDuration);

				timeTo = timeFormat.format(visitTimeTo.getTime());

				patientForm.setVisitFromTimeHH(timeFrom);
				patientForm.setVisitToTimeHH(timeTo);

			} else {

				/*
				 * retrieving appointment timeFrom, timeTo, and appt date
				 */
				String retrieveAppointmentDetailsQuery = QueryMaker.RETRIEVE_APPOINTMENT_TIME_BY_ID;

				preparedStatement = connection.prepareStatement(retrieveAppointmentDetailsQuery);

				preparedStatement.setInt(1, aptID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					/*
					 * Declaring variable for time to and from
					 */
					String timeFrom = null;

					String timeTo = null;

					/*
					 * Splitting visit Time from and time to
					 */

					timeFrom = resultSet.getString("apptTimeFrom");

					timeTo = resultSet.getString("apptTimeTo");

					patientForm.setVisitFromTimeHH(timeFrom.split(":")[0] + ":" + timeFrom.split(":")[1]);
					patientForm.setVisitToTimeHH(timeTo.split(":")[0] + ":" + timeTo.split(":")[1]);

				}

				resultSet.close();
				preparedStatement.close();

			}
			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setOnExamination(resultSet.getString("onExamination"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				patientForm.setVisitFromTimeHH(resultSet.getString("visitTimeFrom"));
				patientForm.setVisitToTimeHH(resultSet.getString("visitTimeTo"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving Personal history details by patientID from Personalhistory table
			 */

			String retreivePersonalHistoryDetailsByPatientIDQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreivePersonalHistoryDetailsByPatientIDQuery);
			System.out.println("patient id at personalhist is::" + patientID);
			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPersonalHistorySmoking(resultSet.getString("smoking"));
				patientForm.setPersonalHistorySmokingDetails(resultSet.getString("smokingDetails"));
				patientForm.setPersonalHistoryAlcohol(resultSet.getString("alcohol"));
				patientForm.setPersonalHistoryAlcoholDetails(resultSet.getString("alcoholDetails"));
				patientForm.setPersonalHistoryTobacco(resultSet.getString("tobacco"));
				patientForm.setPersonalHistoryTobaccoDetails(resultSet.getString("tobaccoDetails"));
				patientForm.setPersonalHistoryFoodChoice(resultSet.getString("foodChoice"));
				patientForm.setPersonalHistoryFoodChoiceDetails(resultSet.getString("foodChoiceDetails"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving VitalSigns details by visitID from VitalSigns table
			 */
			String retreiveVitalSignsDetailsByVisitIDQuery = QueryMaker.RETRIEVE_VITAL_SIGNS;

			preparedStatement = connection.prepareStatement(retreiveVitalSignsDetailsByVisitIDQuery);
			System.out.println("visit id at personalhist is :" + lastOPDVisitID);
			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setWeight(resultSet.getDouble("weight"));
				patientForm.setHeight(resultSet.getDouble("height"));
				patientForm.setOEPulse(resultSet.getInt("pulse"));
				patientForm.setOEBPSys(resultSet.getInt("systolicBP"));
				patientForm.setOEBPDia(resultSet.getInt("diastolicBP"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */
			String retreivePatientLastVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setLastVisitID(resultSet.getInt("id"));

			}

			resultSet.close();
			preparedStatement.close();
			/*
			 * Retrieving patient's last visitID
			 */
			String retreivePatientLastVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setAdvice(resultSet.getString("advice"));

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = dateFormat.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeFormat.format(date));
				}

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Default patientList retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public int retrieveNewVisitTypeIDByClinicID(int clinicID) {

		int newVisitTypeID = 0;

		try {

			connection = getConnection();

			String retrieveNewVisitTypeIDQuery = QueryMaker.RETRIEVE_NEW_VISIT_TYPE_ID_BY_CLINIC_ID;

			preparedStatement = connection.prepareStatement(retrieveNewVisitTypeIDQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, 1);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				newVisitTypeID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return newVisitTypeID;
	}

	public int retrievVisitIDForNewVisitType(int patientID, int newVisitTypeID) {
		int visitID = 0;
		try {
			connection = getConnection();

			String retrievVisitIDForNewVisitTypeQuery = QueryMaker.RETRIEVE_VISIT_ID_FOR_NEW_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(retrievVisitIDForNewVisitTypeQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, newVisitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visitID based on New visit Type table due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	public boolean verifyFollowUpVisitType(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyFollowUpVisitTypeQuery = QueryMaker.RETRIEVE_FOLLOW_UP_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(verifyFollowUpVisitTypeQuery);
			preparedStatement.setInt(1, visitTypeID);
			preparedStatement.setInt(2, 0);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String insertVisitPrescriptionDetails(String tradeName, double dosage, String frequency, double noOfPills,
			String comment, String noOfDays, int visitID, int categoryID) {

		try {

			connection = getConnection();

			String insertVisitPrescriptionDetailsQuery = QueryMaker.INSERT_VISIT_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(insertVisitPrescriptionDetailsQuery);

			// preparedStatement.setInt(1, noOfDays);
			// preparedStatement.setString(1, frequency);
			preparedStatement.setDouble(1, dosage);
			preparedStatement.setString(2, comment);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, visitID);
			preparedStatement.setString(5, tradeName);
			preparedStatement.setDouble(6, noOfPills);
			preparedStatement.setString(7, noOfDays);
			preparedStatement.setString(8, frequency);
			preparedStatement.setInt(9, categoryID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public List<PatientForm> retrieveVisitBillingByVisitTypeID(int visitTypeID, int clinicID, String clinicSuffix,
			int patientID) {
		System.out.println("ReceiptNo ::: " + clinicID + "," + clinicSuffix);
		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection1 = getConnection();

			String retrieveVisitBillingByVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitBillingByVisitTypeIDQuery);

			preparedStatement1.setInt(1, visitTypeID);
			preparedStatement1.setInt(2, clinicID);
			preparedStatement1.setInt(3, patientID);
			// System.out.println("preparedStatement431" + preparedStatement1);
			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				// Check if the diagnosis has a json value
				/*
				 * if (new JSONValidator().isValid(resultSet1.getString("diagnosis"))) {
				 * 
				 * org.json.JSONArray diagnosisJSONArray = new
				 * org.json.JSONArray(resultSet1.getString("diagnosis"));
				 * 
				 * double totalRate = 0;
				 * 
				 * for (int i = 0; i < diagnosisJSONArray.length(); i++) {
				 * 
				 * org.json.JSONObject diagnosisJSONObject =
				 * diagnosisJSONArray.getJSONObject(i);
				 * 
				 * totalRate += Double.parseDouble(diagnosisJSONObject.getString("rate"));
				 * 
				 * }
				 * 
				 * form.setNetAmount(resultSet1.getDouble("consultationCharges") + totalRate);
				 * 
				 * } else { form.setNetAmount(resultSet1.getDouble("consultationCharges")); }
				 */

				form.setNetAmount(resultSet1.getDouble("consultationCharges"));
				form.setTotalAmount(resultSet1.getDouble("consultationCharges"));

				form.setBillingType(resultSet1.getString("billingType"));
				form.setCharges(resultSet1.getDouble("consultationCharges"));
				form.setReceiptDate(dateFormat.format(new Date()));
				form.setReceiptNo(retrieveReceiptNo(clinicID, clinicSuffix));
				form.setCancerType(resultSet1.getString("diagnosis"));
				form.setAdvPayment(0);
				form.setBalPayment(0);

				list.add(form);

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

			System.out.println("Bill list 11 retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PatientForm> retrieveVisitBillingByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection1 = getConnection();

			String retrieveVisitBillingByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitBillingByVisitIDQuery);
			System.out.println("visitid at billlist::" + visitID);
			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setBillingType(resultSet1.getString("billingType"));
				form.setTotalAmount(resultSet1.getDouble("totalAmount"));
				form.setNetAmount(resultSet1.getDouble("netAmount"));
				form.setReceiptID(resultSet1.getInt("id"));
				form.setReceiptNo(resultSet1.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet1.getTimestamp("receiptDate")));
				form.setCancerType(resultSet1.getString("diagnosis"));

				form.setAdvPayment(resultSet1.getDouble("advPayment"));
				form.setBalPayment(resultSet1.getDouble("balPayment"));
				form.setPaymentType(resultSet1.getString("paymentType"));
				form.setEmergencyCharges(resultSet1.getDouble("emergencyCharge"));
				form.setMlcCharges(resultSet1.getDouble("MLCCharges"));
				form.setAmbulanceDoctorCharges(resultSet1.getDouble("ambulanceDoctorsCharges"));
				form.setTotalDiscount(resultSet1.getDouble("totalDiscount"));
				form.setCharges(resultSet1.getDouble("consultationCharges"));
				form.setProductName(resultSet1.getString("productName"));
				form.setProductID(resultSet1.getInt("productID"));
				form.setProductRate(resultSet1.getDouble("productRate"));
				form.setDiscountType(resultSet1.getString("discountType"));
				form.setIsConsultationDone(resultSet1.getInt("isConsultationDone"));

			}

			connection = getConnection();

			String retrievePAYMENTDETAILSByreceiptIDQuery = QueryMaker.RETRIEVE_PAYMENT_DETAILS;

			preparedStatement = connection.prepareStatement(retrievePAYMENTDETAILSByreceiptIDQuery);

			preparedStatement.setInt(1, form.getReceiptID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setChequeNo(resultSet.getString("chequeNumber"));
				form.setChequeBankName(resultSet.getString("bankName"));
				form.setChequeBankBranch(resultSet.getString("bankBranch"));
				form.setChequeAmt(resultSet.getDouble("chequeAmount"));

				if (form.getPaymentType().contains("Cheque")) {

					if (resultSet.getString("chequeDate") == null || resultSet.getString("chequeDate") == "") {

						form.setChequeDate(null);
					} else if (resultSet.getString("chequeDate").isEmpty()) {

						form.setChequeDate(null);
					} else {

						form.setChequeDate(dateFormat1.format(dateFormat2.parse(resultSet.getString("chequeDate"))));

					}

				} else {

					form.setChequeDate(null);

				}

				form.setCashPaid(resultSet.getDouble("cashPaid"));
				form.setCashToReturn(resultSet.getDouble("cashToReturn"));
				form.setCardMobileNo(resultSet.getString("cardNumber"));
				form.setChequeIssuedBy(resultSet.getString("chequeIssueBy"));
				form.setCreditNoteBal(resultSet.getDouble("creditNote"));
				form.setCMobileNo(resultSet.getString("mobile"));
				form.setOtherType(resultSet.getString("otherMode"));
				form.setCardAmount(resultSet.getDouble("cardAmount"));
				form.setOtherAmount(resultSet.getDouble("otherAmount"));

			}

			list.add(form);

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println(" Bill List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PatientForm> retrieveVisitBillingByVisitID_bk(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection1 = getConnection();

			String retrieveVisitBillingByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitBillingByVisitIDQuery);
			System.out.println("visitid at billlist::" + visitID);
			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setBillingType(resultSet1.getString("billingType"));
				form.setTotalAmount(resultSet1.getDouble("totalAmount"));
				form.setNetAmount(resultSet1.getDouble("netAmount"));
				form.setReceiptID(resultSet1.getInt("id"));
				form.setReceiptNo(resultSet1.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet1.getTimestamp("receiptDate")));
				form.setCancerType(resultSet1.getString("diagnosis"));

				form.setAdvPayment(resultSet1.getDouble("advPayment"));
				form.setBalPayment(resultSet1.getDouble("balPayment"));
				form.setPaymentType(resultSet1.getString("paymentType"));
				form.setEmergencyCharges(resultSet1.getDouble("emergencyCharge"));
				form.setMlcCharges(resultSet1.getDouble("MLCCharges"));
				form.setAmbulanceDoctorCharges(resultSet1.getDouble("ambulanceDoctorsCharges"));
				form.setTotalDiscount(resultSet1.getDouble("totalDiscount"));
				form.setCharges(resultSet1.getDouble("consultationCharges"));

			}

			connection = getConnection();

			String retrievePAYMENTDETAILSByreceiptIDQuery = QueryMaker.RETRIEVE_PAYMENT_DETAILS;

			preparedStatement = connection.prepareStatement(retrievePAYMENTDETAILSByreceiptIDQuery);

			preparedStatement.setInt(1, form.getReceiptID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setChequeNo(resultSet.getString("chequeNumber"));
				form.setChequeBankName(resultSet.getString("bankName"));
				form.setChequeBankBranch(resultSet.getString("bankBranch"));
				form.setChequeAmt(resultSet.getDouble("chequeAmount"));

				if (form.getPaymentType().contains("Cheque")) {

					if (resultSet.getString("chequeDate") == null || resultSet.getString("chequeDate") == "") {

						form.setChequeDate(null);
					} else if (resultSet.getString("chequeDate").isEmpty()) {

						form.setChequeDate(null);
					} else {

						form.setChequeDate(dateFormat1.format(dateFormat2.parse(resultSet.getString("chequeDate"))));

					}

				} else {

					form.setChequeDate(null);

				}

				form.setCashPaid(resultSet.getDouble("cashPaid"));
				form.setCashToReturn(resultSet.getDouble("cashToReturn"));
				form.setCardMobileNo(resultSet.getString("cardNumber"));
				form.setChequeIssuedBy(resultSet.getString("chequeIssueBy"));
				form.setCreditNoteBal(resultSet.getDouble("creditNote"));
				form.setCMobileNo(resultSet.getString("mobile"));
				form.setOtherType(resultSet.getString("otherMode"));
				form.setCardAmount(resultSet.getDouble("cardAmount"));
				form.setOtherAmount(resultSet.getDouble("otherAmount"));

			}

			list.add(form);

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println(" Bill List retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PatientForm> retrievePatientDetails(int patientID, int lastOPDVisitID, int clinicID, int nextApptTaken,
			int aptID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				System.out.println("age DAO::" + resultSet.getString("age"));
				System.out.println("gender DAOO::" + resultSet.getString("gender"));

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setPhone(resultSet.getString("phone"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));

					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}

				patientForm.setEC(resultSet.getString("ec"));

				patientForm.setEmailID(resultSet.getString("email"));

				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));

				patientForm.setRhFactor(resultSet.getString("rhFactor"));

				patientForm.setLastVisitID(lastOPDVisitID);

				patientForm.setNextApptTaken(nextApptTaken);

			}
			String date = "";
			int visitTypeID = 0;
			if (aptID == 0) {
				Date date1 = new Date();
				date = new SimpleDateFormat("yyyy-MM-dd").format(date1);

				System.out.println(date1 + "date is ::::1::: " + date);

			} else {
				visitTypeID = retrieveVisitTypeIDByApptID(aptID);
				date = retrieveAppoDateByApptID(aptID);
			}

			int nextVisitDays = retrieveNextVisitDays(visitTypeID);
			System.out.println(
					"appo date::" + date + "nect visit days:" + nextVisitDays + " visit type id::" + visitTypeID);

			LocalDate date1 = LocalDate.parse(date);
			LocalDate date2 = date1.plusDays(nextVisitDays);

			String appoDate = date1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String formattedDate = date2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

			if (formattedDate.equals(appoDate)) {
				patientForm.setAppointmentDate("");
			} else {
				patientForm.setAppointmentDate(formattedDate);
			}
			System.out.println("appo date final:" + patientForm.getAppointmentDate());

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			// status = "error";

		}
		return list;
	}

	public double retrieveConsultationChargesFromPrescription(int visitID) {

		double consultationCharges = 0;

		try {

			connection = getConnection();

			String retrieveConsultationChargesFromPrescriptionQuery = QueryMaker.RETRIEVE_PRESCRIPTION_CONSULTATION_CHARGES;

			preparedStatement = connection.prepareStatement(retrieveConsultationChargesFromPrescriptionQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				consultationCharges = resultSet.getDouble("consultationCharges");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return consultationCharges;
	}

	public List<PatientForm> searchInactivePatientByPatientName(String patientName, int practiceID, int clinicID,
			String searchCriteria) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {
			connection = getConnection();

			if (searchCriteria.equals("MobileNo")) {

				String searchInactivePatientByPatientNameQuery = QueryMaker.SEARCH_PATIENT_BY_MOBILE_NO;

				preparedStatement = connection.prepareStatement(searchInactivePatientByPatientNameQuery);

				preparedStatement.setString(1, patientName);
				preparedStatement.setInt(2, practiceID);
				preparedStatement.setString(3, ActivityStatus.INACTIVE);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("PatientName")) {

				String searchInactivePatientByPatientNameQuery = QueryMaker.SEARCH_PATIENT_BY_PATIENT_NAME;

				preparedStatement = connection.prepareStatement(searchInactivePatientByPatientNameQuery);

				if (patientName.contains(" ")) {
					patientName = patientName.replace(" ", "%");
				}

				preparedStatement.setInt(1, clinicID);
				preparedStatement.setString(2, "%" + patientName + "%");
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(4, ActivityStatus.INACTIVE);

				resultSet = preparedStatement.executeQuery();

			} else {

				String searchInactivePatientByPatientNameQuery = QueryMaker.SEARCH_PATIENT_BY_REGISTRATION_NUMBER;

				preparedStatement = connection.prepareStatement(searchInactivePatientByPatientNameQuery);

				preparedStatement.setString(1, "%" + patientName + "%");
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(2, ActivityStatus.INACTIVE);

				resultSet = preparedStatement.executeQuery();

			}

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setPatientName(patientName);
				patientForm.setRegistrationNo(resultSet.getString("regNo"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return list;
	}

	public List<PatientForm> retrieveInactivePatientList(int practiceID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retrievePatientListQuery = QueryMaker.RETREIVE_PATIENT_LIST;

			preparedStatement = connection.prepareStatement(retrievePatientListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setRegistrationNo(resultSet.getString("regNo"));

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving patient list from database due to:::" + exception.getMessage());
			status = "error";

		}
		return list;
	}

	public List<String> retrieveDiagnosis(String diagnosis) {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrieveDiagnosisQuery = QueryMaker.RETRIEVE_DIAGNOSIS;

			if (diagnosis.contains(" ")) {
				diagnosis = diagnosis.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveDiagnosisQuery);

			preparedStatement.setString(1, "%" + diagnosis + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getString("diagnosis"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String insertVitalSignsDetailsForVisit(PatientForm patientForm) {

		try {
			connection = getConnection();

			String insertVitalSignsDetailsForVisitQuery = QueryMaker.INSERT_VitalSigns_Details;

			preparedStatement = connection.prepareStatement(insertVitalSignsDetailsForVisitQuery);

			preparedStatement.setDouble(1, patientForm.getWeight());
			preparedStatement.setInt(2, patientForm.getOEPulse());
			preparedStatement.setInt(3, patientForm.getOEBPSys());
			preparedStatement.setInt(4, patientForm.getOEBPDia());
			preparedStatement.setInt(5, patientForm.getVisitID());
			preparedStatement.setDouble(6, patientForm.getHeight());

			preparedStatement.execute();

			System.out.println("Successfully inserted VitalSigns details into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting VitalSigns details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;

	}

	public String insertSymptomCheckDetailsForVisit(String symptom, String value, int visitID) {

		try {
			connection = getConnection();

			String insertSymptomCheckDetailsForVisitQuery = QueryMaker.INSERT_SymptomCheck_Detail;

			preparedStatement = connection.prepareStatement(insertSymptomCheckDetailsForVisitQuery);

			preparedStatement.setString(1, symptom);
			preparedStatement.setString(2, value);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			System.out.println("Successfully inserted SymptomCheck details into table");

			status = "success";

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting SymptomCheck details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<PatientForm> retrievesymptomCheckList(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		try {

			connection = getConnection();

			String retreiveSymptomDetailsByVisitIDQuery = QueryMaker.RETRIEVE_SymptomCheck_Details_By_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreiveSymptomDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setSymptomID(resultSet.getInt("id"));
				patientForm.setSymptomCheckName(resultSet.getString("symptom"));
				patientForm.setSymptomCheckValue(resultSet.getString("value"));

				list.add(patientForm);
			}

			System.out.println("Symptomcheck list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String updateVitalSignsDetailsForVisit(PatientForm patientForm) {

		try {
			connection = getConnection();

			String updateVisitQuery = QueryMaker.UPDATE_VITALSIGNS_DETAILS;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			preparedStatement.setDouble(1, patientForm.getWeight());
			preparedStatement.setInt(2, patientForm.getOEPulse());
			preparedStatement.setInt(3, patientForm.getOEBPSys());
			preparedStatement.setInt(4, patientForm.getOEBPDia());
			preparedStatement.setDouble(5, patientForm.getHeight());
			preparedStatement.setInt(6, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated VitalSigns details into VitalSigns table.");

			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating VitalSigns details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;

	}

	public JSONObject deleteSymptomDetails(int symptomID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteSymptomDetailsQuery = QueryMaker.DELETE_SymptomCheck_DETAILS;

			preparedStatement = connection.prepareStatement(deleteSymptomDetailsQuery);

			preparedStatement.setInt(1, symptomID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Symptom details into table due to:::" + exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Symptom details.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteComplaintDetails(int complaintID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteComplaintDetailsQuery = QueryMaker.DELETE_PresentComplaints_DETAILS;

			preparedStatement = connection.prepareStatement(deleteComplaintDetailsQuery);

			preparedStatement.setInt(1, complaintID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Complaint details into table due to:::" + exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Complaint details.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteMedicalHistoryDetails(int historyID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteMedicalHistoryDetailsQuery = QueryMaker.DELETE_MedicalHistory_DETAILS;

			preparedStatement = connection.prepareStatement(deleteMedicalHistoryDetailsQuery);

			preparedStatement.setInt(1, historyID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Medical History details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Medical History details.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteCurrentMedicationDetails(int medicationID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteCurrentMedicationDetailsQuery = QueryMaker.DELETE_CurrentMedication_DETAILS;

			preparedStatement = connection.prepareStatement(deleteCurrentMedicationDetailsQuery);

			preparedStatement.setInt(1, medicationID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting current medication details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting current medication details.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public List<PatientForm> retrievelabReportDetailsList(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();

		try {
			connection = getConnection();

			String retrievelabReportDetailsListQuery = QueryMaker.RETRIEVE_lab_Report_Details_List;

			preparedStatement = connection.prepareStatement(retrievelabReportDetailsListQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				PatientForm patientForm = new PatientForm();

				patientForm.setReportsID(resultSet.getInt("id"));
				patientForm.setReportDBName(resultSet.getString("report"));
				patientForm.setDescription(resultSet.getString("description"));

				list.add(patientForm);
			}
			System.out.println("successfully retrieved lab reports details list");
			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Visit details For Patient into table due to:::"
					+ exception.getMessage());

		}

		return list;

	}

	public String labReportDownload(int reportsID) {

		System.out.println("reportid is::" + reportsID);
		String fileName = "";

		try {

			connection = getConnection();

			String retrieveLabReportFileNameQuery = QueryMaker.RETRIEVE_LAB_REPORT_FILE_NAME1;

			preparedStatement = connection.prepareStatement(retrieveLabReportFileNameQuery);

			preparedStatement.setInt(1, reportsID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				fileName = resultSet.getString("report");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving lab report filename from table due to:::"
					+ exception.getMessage());
		}
		return fileName;
	}

	public String insertPaymentDetails(PatientForm patientForm, String paymentType, int receiptID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String insertPaymentDetailsQuery = QueryMaker.INSERT_PAYMENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertPaymentDetailsQuery);

			preparedStatement.setInt(1, receiptID);
			preparedStatement.setString(2, patientForm.getChequeNo());
			preparedStatement.setString(3, patientForm.getChequeBankName());
			preparedStatement.setString(4, patientForm.getChequeBankBranch());

			/*
			 * Check whether paymentType is equal to Cheque then only parse the chequeDate
			 * else insert NULL in chequeDate column in PaymentType
			 */
			if (paymentType.contains("Cheque")) {

				if (patientForm.getChequeDate() == null || patientForm.getChequeDate() == "") {
					preparedStatement.setString(5, null);
				} else if (patientForm.getChequeDate().isEmpty()) {
					preparedStatement.setString(5, null);
				} else {
					preparedStatement.setString(5, dateFormat.format(dateFormat1.parse(patientForm.getChequeDate())));
				}

			} else {

				preparedStatement.setString(5, null);

			}

			preparedStatement.setDouble(6, patientForm.getChequeAmt());
			preparedStatement.setDouble(7, patientForm.getCashPaid());
			preparedStatement.setDouble(8, patientForm.getCashToReturn());
			preparedStatement.setString(9, patientForm.getCardMobileNo());
			preparedStatement.setString(10, patientForm.getChequeIssuedBy());
			preparedStatement.setString(11, ActivityStatus.ACTIVE);
			preparedStatement.setDouble(12, patientForm.getCreditNoteBal());
			preparedStatement.setString(13, patientForm.getCMobileNo());
			preparedStatement.setString(14, patientForm.getOtherType());
			preparedStatement.setString(15, patientForm.getAdjustAmtStatus());
			preparedStatement.setDouble(16, patientForm.getCardAmount());
			preparedStatement.setString(17, dateFormat.format(new Date()));
			preparedStatement.setDouble(18, patientForm.getOtherAmount());

			preparedStatement.execute();

			status = "success";

			System.out.println("Payment Details added succeddfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		}

		return status;
	}

	public String updatePaymentDetails(PatientForm patientForm, String paymentType, int receiptID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String updatePaymentDetailsQuery = QueryMaker.UPDATE_PAYMENT_DETAILS;

			preparedStatement = connection.prepareStatement(updatePaymentDetailsQuery);

			preparedStatement.setString(1, patientForm.getChequeNo());
			preparedStatement.setString(2, patientForm.getChequeBankName());
			preparedStatement.setString(3, patientForm.getChequeBankBranch());

			/*
			 * Check whether paymentType is equal to Cheque then only parse the chequeDate
			 * else insert NULL in chequeDate column in PaymentType
			 */
			if (paymentType != null && paymentType.contains("Cheque")) {

				if (patientForm.getChequeDate() == null || patientForm.getChequeDate() == "") {
					preparedStatement.setString(4, null);
				} else if (patientForm.getChequeDate().isEmpty()) {
					preparedStatement.setString(4, null);
				} else {
					preparedStatement.setString(4, dateFormat.format(dateFormat1.parse(patientForm.getChequeDate())));
				}

			} else {

				preparedStatement.setString(4, null);

			}

			preparedStatement.setDouble(5, patientForm.getChequeAmt());
			preparedStatement.setDouble(6, patientForm.getCashPaid());
			preparedStatement.setDouble(7, patientForm.getCashToReturn());
			preparedStatement.setString(8, patientForm.getCardMobileNo());
			preparedStatement.setString(9, patientForm.getChequeIssuedBy());
			preparedStatement.setDouble(10, patientForm.getCreditNoteBal());
			preparedStatement.setString(11, patientForm.getCMobileNo());
			preparedStatement.setString(12, patientForm.getOtherType());
			preparedStatement.setString(13, patientForm.getAdjustAmtStatus());
			preparedStatement.setDouble(14, patientForm.getCardAmount());
			preparedStatement.setString(15, dateFormat.format(new Date()));
			preparedStatement.setDouble(16, patientForm.getOtherAmount());

			preparedStatement.setInt(17, receiptID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Payment Details updated succeddfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		}

		return status;
	}

	public int retrieveLastEnteredReceiptID(int visitID) {

		int receiptID = 0;

		try {

			connection = getConnection();

			String retrieveLastEnteredReceiptIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_RECEIPT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredReceiptIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				receiptID = resultSet.getInt("id");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving receiptID from table due to:::" + exception.getMessage());
		}

		return receiptID;
	}

	public boolean verifyhasConsentValue(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyhasConsentValueQuery = QueryMaker.RETRIEVE_hasConsent_Value;

			preparedStatement = connection.prepareStatement(verifyhasConsentValueQuery);
			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getInt("hasConsent") == 1) {
					check = true;
				} else {
					check = false;
				}

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String retrieveConsentDocFileName(int visitTypeID) {
		String consentText = null;

		try {
			connection = getConnection();

			String retrieveConsentDocFileNameQuery = QueryMaker.RETRIEVE_CONSENTDoc_FileName;

			preparedStatement8 = connection.prepareStatement(retrieveConsentDocFileNameQuery);
			preparedStatement8.setInt(1, visitTypeID);

			resultSet8 = preparedStatement8.executeQuery();

			while (resultSet8.next()) {
				consentText = resultSet8.getString("consentDocument");
			}

			resultSet8.close();
			preparedStatement8.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving consent text from table due to:::" + exception.getMessage());
		}
		return consentText;
	}

	public List<PatientForm> reytrieveLabInvastigationLstByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String retrieveInvestigationQuery = QueryMaker.RETRIEVE_INVESTOGATION;

			preparedStatement = connection.prepareStatement(retrieveInvestigationQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setInvestigationDetailsID(resultSet.getInt("id"));

				patientForm.setOtherTest(resultSet.getString("test"));

				if (resultSet.getString("qualitativeValue") == null || resultSet.getString("qualitativeValue") == "") {
					patientForm.setOtherTestValue("" + resultSet.getDouble("quantitativeValue"));
				} else {
					if (resultSet.getString("qualitativeValue").isEmpty()) {
						patientForm.setOtherTestValue("" + resultSet.getDouble("quantitativeValue"));
					} else {
						patientForm.setOtherTestValue(resultSet.getString("qualitativeValue"));
					}
				}

				list.add(patientForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			exception.printStackTrace();
		}

		return list;
	}

	public String updateConsentDocument(String fileName, int visitID) {

		try {
			connection = getConnection();

			String updateConsentDocumentQuery = QueryMaker.UPDATE_DOCUMENT;

			preparedStatement = connection.prepareStatement(updateConsentDocumentQuery);

			preparedStatement.setString(1, fileName);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully updated consent file to Database");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating consent document into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String insertTradeNameInProductTable(String tradeName, int clinicID) {

		try {

			connection = getConnection();

			String insertProductQuery = QueryMaker.INSERT_TRADE_NAME_PRODUCT;

			preparedStatement = connection.prepareStatement(insertProductQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setString(2, tradeName);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, clinicID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Trade name inserted successfully into Product table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String insertOPDVisitForOpticianDetails(PatientForm form, int visitNumber) {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertOPDVisitForOpticianDetailsQuery = QueryMaker.INSERT_VISIT_DETAILS_FOR_OPTICIAN;

			preparedStatement = connection.prepareStatement(insertOPDVisitForOpticianDetailsQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setInt(2, form.getVisitTypeID());
			preparedStatement.setString(3, dateFormat.format(date));
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, form.getPatientID());

			if (form.getAptID() == 0) {
				preparedStatement.setInt(6, 0);
			} else {
				preparedStatement.setInt(6, form.getAptID());
			}

			preparedStatement.setInt(7, form.getClinicID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public int retrievVisitID(int patientID, int clinicID) {
		int visitID = 0;
		try {
			connection2 = getConnection();

			String retrievVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID_FOR_EDIT;

			preparedStatement11 = connection2.prepareStatement(retrievVisitIDQuery);
			preparedStatement11.setInt(1, patientID);
			preparedStatement11.setInt(2, clinicID);

			resultSet11 = preparedStatement11.executeQuery();

			while (resultSet11.next()) {
				visitID = resultSet11.getInt("id");
			}

			resultSet11.close();
			preparedStatement11.close();
			connection2.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visitID based on cliniID & patientID due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	public String insertInvestigationDetails(String testName, int visitID) {

		try {

			connection = getConnection();

			String insertInvestigationDetailsQuery = QueryMaker.INSERT_Investigation_TEST_NAME;

			preparedStatement = connection.prepareStatement(insertInvestigationDetailsQuery);

			preparedStatement.setString(1, testName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Investigation Details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String insertDiagnosisInPVDiagnosisTable(String diagnosis) {

		try {

			connection = getConnection();

			String insertDiagnosisQuery = QueryMaker.INSERT_DIAGNOSIS_IN_PVDIAGNOSIS;

			preparedStatement = connection.prepareStatement(insertDiagnosisQuery);

			preparedStatement.setString(1, diagnosis);

			preparedStatement.execute();

			status = "success";

			System.out.println("Diagnosis inserted successfully in PVDiagnosis table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String insertTestNameInPVLabTestsTable(String testName) {

		try {

			connection = getConnection();

			String insertTestNameInPVLabTestsTableQuery = QueryMaker.INSERT_TEST_NAME_PVLabTests;

			preparedStatement = connection.prepareStatement(insertTestNameInPVLabTestsTableQuery);

			preparedStatement.setString(1, testName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("TestName inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public List<PatientForm> retrieveInvestigationTestsList(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrieveInvestigationTestsListQuery = QueryMaker.RETREIVE_InvestigationTest_LIST;

			preparedStatement = connection.prepareStatement(retrieveInvestigationTestsListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setInvestigationID(resultSet.getInt("id"));
				patientForm.setInvestigation(resultSet.getString("investigation"));
				patientForm.setVisitID(visitID);
				patientForm.setCount("" + count + "");

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("investigation test list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving PrescribedInvestigation list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public JSONObject deleteDiagnostic(int diagnosticID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteDiagnosticQuery = QueryMaker.DELETE_DIAGNOSTIC_DETAILS;

			preparedStatement = connection.prepareStatement(deleteDiagnosticQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, diagnosticID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting diagnostic details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting diagnostic.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteProcedure(int procedureID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteProcedureQuery = QueryMaker.DELETE_PROCEDURE_DETAILS;

			preparedStatement = connection.prepareStatement(deleteProcedureQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, procedureID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Procedure details into table due to:::" + exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Procedure.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteInvestigation(int investigationID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteInvestigationQuery = QueryMaker.DELETE_INVESTIGATION_DETAILS;

			preparedStatement = connection.prepareStatement(deleteInvestigationQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, investigationID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Investigation details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Investigation.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public int retrieveNextVisitDays(int visitTypeID) {
		int nextVisitDays = 0;
		try {
			connection = getConnection();

			String retrieveNextVisitDaysQuery = QueryMaker.RETRIEVE_CONFIGURATION_VISIT_TYPE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveNextVisitDaysQuery);
			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				nextVisitDays = resultSet.getInt("nextVisitDays");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving nextVisitDays based on visitTypeID due to:::"
					+ exception.getMessage());
		}
		return nextVisitDays;
	}

	public boolean checkEyewearDetails(int opticinID) {

		boolean check = false;

		try {

			connection = getConnection();

			String checkEyewearDetailsQuery = QueryMaker.OPTICIAN_PDF_RETRIEVE_EYEWEAR;

			preparedStatement = connection.prepareStatement(checkEyewearDetailsQuery);
			preparedStatement.setInt(1, opticinID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String updateVisitColumns(int nextVisitDays, String advice, int visitID) {

		try {
			connection = getConnection();

			String updateVisitColumnsQuery = QueryMaker.UPDATE_Visit_With_NextVisitDays_AND_Advice;

			preparedStatement = connection.prepareStatement(updateVisitColumnsQuery);

			preparedStatement.setInt(1, nextVisitDays);
			preparedStatement.setString(2, advice);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			System.out.println("Successfully updated Visit With NextVisitDays");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Visit With NextVisitDays into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public int retrieveLastVisitID(int patientID, int clinicID) {
		int visitID = 0;

		try {
			connection = getConnection();

			String retrieveLastOPDVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitIDQuery);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving last entred visit ID for careType OPD due to:::"
					+ exception.getMessage());
		}
		return visitID;
	}

	public JSONObject retrieveCategoryWithDrugName(String tradeName, int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		int count = 1;

		try {
			connection = getConnection();

			String retrieveCategoryWithDrugNameQuery = QueryMaker.RETRIEVE_Category_With_DrugName;

			preparedStatement = connection.prepareStatement(retrieveCategoryWithDrugNameQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				if (count > 1) {
					object.put("categoryID", "-1");
				} else {
					object.put("categoryID", resultSet.getInt("categoryID"));
				}

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

				count++;
			}

			if (check == 0) {
				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving category based on drugName from Product & Category table");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveFrequencyValue(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveCategoryWithDrugNameQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST;

			preparedStatement = connection.prepareStatement(retrieveCategoryWithDrugNameQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("frequency", resultSet.getString("frequencyValues"));

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {
				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving frequency values from PVFrequency table");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public int retrievePrescriptionID(String tradeName, int visitID) {
		int prescriptionID = 0;
		try {
			connection = getConnection();

			String retrievePrescriptionIDQuery = QueryMaker.RETRIEVE_PrescriptionID;

			preparedStatement = connection.prepareStatement(retrievePrescriptionIDQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setInt(2, visitID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				prescriptionID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving prescriptionID based on visitID & tradeName due to:::"
							+ exception.getMessage());
		}
		return prescriptionID;
	}

	public String insertFrequencyDetails(String frequency, int numberOfDays, int prescriptionID) {

		try {

			connection = getConnection();

			String insertFrequencyDetailsQuery = QueryMaker.INSERT_Frequency_Details;

			preparedStatement = connection.prepareStatement(insertFrequencyDetailsQuery);

			preparedStatement.setString(1, frequency);
			preparedStatement.setInt(2, numberOfDays);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, prescriptionID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Frequency details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public JSONObject retrieveFrequencyDetailsByPrescriptionID(int prescriptionID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveFrequencyDetailsByPrescriptionIDQuery = QueryMaker.RETRIEVE_FRQUENCY_LIST_By_PrescriptionID;

			preparedStatement = connection.prepareStatement(retrieveFrequencyDetailsByPrescriptionIDQuery);

			preparedStatement.setInt(1, prescriptionID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("frequencyDetailsID", resultSet.getInt("id"));

				object.put("frequency", resultSet.getString("frequency"));

				object.put("numberOfDays", resultSet.getInt("numberOfDays"));

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			if (check == 0) {
				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving frequency details from Frequency table");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject deleteFrequencyDetails(int frequencyDetailsID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteFrequencyDetailsQuery = QueryMaker.DELETE_FREQUENCY_DETAILS;

			preparedStatement = connection.prepareStatement(deleteFrequencyDetailsQuery);

			preparedStatement.setInt(2, frequencyDetailsID);
			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Frequency Details into table due to:::" + exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting Frequency Details.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public boolean CheckVisitTypeNewVisitCheck(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String CheckVisitTypeNewVisitCheckQuery = QueryMaker.VisitType_NewVisit_Check;

			preparedStatement = connection.prepareStatement(CheckVisitTypeNewVisitCheckQuery);

			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String verifyMedicalCertificate(int visitID) {

		String check = "no";

		try {

			connection = getConnection();

			String verifyMedicalcert = QueryMaker.VERIFY_MEDICAL_CERT;

			preparedStatement = connection.prepareStatement(verifyMedicalcert);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("medicalCertificate") == null
						|| resultSet.getString("medicalCertificate") == "") {
					check = "no";
				} else {
					check = "yes";
				}
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "no";
		}

		return check;
	}

	public String verifyReferralLetter(int visitID) {

		String check = "no";

		try {

			connection = getConnection();

			String verifyReferralLetter = QueryMaker.VERIFY_REFERRAL_LETTER;

			preparedStatement = connection.prepareStatement(verifyReferralLetter);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("referralLetter") == null || resultSet.getString("referralLetter") == "") {
					check = "no";
				} else {
					check = "yes";
				}
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "no";
		}

		return check;
	}

	public int verifyConsentDetailsExist(int userID) {
		int check = 0;
		try {
			connection = getConnection();

			String verifyConsentDetailsExistQuery = QueryMaker.VERIFY_Consent_DETAIL;

			preparedStatement = connection.prepareStatement(verifyConsentDetailsExistQuery);
			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying Consent details into UsageConsent table due to:::"
					+ exception.getMessage());
		}

		return check;
	}

	public String addConsentDetails(PatientForm patientForm, int userID) {
		try {

			connection = getConnection();

			String addConsentDetailsQuery = QueryMaker.INSERT_Consent_Details;

			preparedStatement = connection.prepareStatement(addConsentDetailsQuery);

			preparedStatement.setString(1, patientForm.getConsentType());
			preparedStatement.setString(2, patientForm.getReadRule());
			preparedStatement.setString(3, patientForm.getAcceptRule());
			preparedStatement.setString(4, patientForm.getInfoTrue());
			preparedStatement.setInt(5, userID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Consent details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String retrieveProfilePic(int patientID) {
		String profilePicName = null;

		try {

			connection = getConnection();

			String retrieveProfilePicQuery = QueryMaker.RETRIEVE_PATIENT_PROFILE_PIC_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveProfilePicQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				profilePicName = resultSet.getString("profilePic");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving profile pic name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return profilePicName;
	}

	@Override
	public int retrieveClinicIDByPatientID(int patientID) {
		int clinicID = 0;
		try {
			connection = getConnection();

			String retrieveClinicIDbyPatientIDQuery = QueryMaker.RETRIEVE_CLINIC_ID_BY_PATIENTID;

			preparedStatement = connection.prepareStatement(retrieveClinicIDbyPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicID = resultSet.getInt("clinicID");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving clinicID from ClinicRegistration table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return clinicID;
	}

	@Override
	public List<PatientForm> retreivePatientProfileDetails(int patientID) {
		List<PatientForm> userList = new ArrayList<PatientForm>();
		PatientForm Form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String retreivePatientDetailByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreivePatientDetailByPatientIDQuery);
			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Form.setPatientID(resultSet.getInt("id"));
				Form.setUsername(resultSet.getString("username"));
				String decryPass = EncDescUtil.DecryptText(resultSet.getString("password"));
				Form.setPassword(decryPass);
				Form.setActivityStatus(resultSet.getString("activityStatus"));

				// int clinicID = resultSet.getInt("defaultClinicID");
				int practiceID = resultSet.getInt("practiceID");

				// Form.setClinicID(clinicID);
				// Form.setClinicName(retrieveClinicNameByClinicID(clinicID));
				Form.setPracticeID(practiceID);
				// Form.setPracticeName(retrievePracticeNameByPracticeID(practiceID));
				Form.setFirstName(resultSet.getString("firstName"));
				Form.setMiddleName(resultSet.getString("middleName"));
				Form.setLastName(resultSet.getString("lastName"));
				Form.setAge(resultSet.getString("age"));
				Form.setDateOfBirth(resultSet.getString("dateOfBirth"));
				Form.setGender(resultSet.getString("gender"));
				Form.setBloodGroup(resultSet.getString("bloodGroup"));
				Form.setRhFactor(resultSet.getString("rhFactor"));
				Form.setAddress(resultSet.getString("address"));
				Form.setCity(resultSet.getString("city"));
				Form.setState(resultSet.getString("state"));
				Form.setCountry(resultSet.getString("country"));
				Form.setMobile(resultSet.getString("mobile"));
				Form.setPhone(resultSet.getString("phone"));
				Form.setEmailID(resultSet.getString("email"));

			}

			userList.add(Form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retreiving patient detail based on patient id from database due to:::"
							+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PatientDAOInf#insertPatientVisit(com.edhanvantari
	 * .form.PatientForm, int)
	 */
	public String insertPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_VISIT_DETAILS1;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			/*
			 * Checking whether appointment ID is 0 and process accordingly
			 */
			if (patientForm.getAptID() == 0) {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				String timeFrom = null;
				String timeTo = null;

				if (patientForm.getVisitFromTimeHH() == null || patientForm.getVisitFromTimeHH() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().equals("-1")) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM() == null || patientForm.getVisitFromTimeMM() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().equals("-1")) {
					timeFrom = "";
				} else {
					if (patientForm.getVisitFromTimeMM().length() == 1) {
						timeFrom = patientForm.getVisitFromTimeHH() + ":0" + patientForm.getVisitFromTimeMM();
					} else {
						timeFrom = patientForm.getVisitFromTimeHH() + ":" + patientForm.getVisitFromTimeMM();
					}
				}

				if (patientForm.getVisitToTimeHH() == null || patientForm.getVisitToTimeHH() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().equals("-1")) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM() == null || patientForm.getVisitToTimeMM() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().equals("-1")) {
					timeTo = "";
				} else {
					if (patientForm.getVisitToTimeMM().length() == 1) {
						timeTo = patientForm.getVisitToTimeHH() + ":0" + patientForm.getVisitToTimeMM();
					} else {
						timeTo = patientForm.getVisitToTimeHH() + ":" + patientForm.getVisitToTimeMM();
					}
				}

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, timeFrom);
				preparedStatement.setString(5, timeTo);
				preparedStatement.setString(6, patientForm.getDiagnosis());
				preparedStatement.setString(7, patientForm.getMedicalNotes());
				preparedStatement.setString(8, ActivityStatus.ACTIVE);
				preparedStatement.setInt(9, patientForm.getPatientID());
				preparedStatement.setInt(10, newVisitRef);
				preparedStatement.setInt(11, patientForm.getNextVisitDays());
				preparedStatement.setInt(12, 0);
				preparedStatement.setInt(13, patientForm.getClinicID());
				preparedStatement.setString(14, patientForm.getCategory());

				preparedStatement.setString(15, patientForm.getCategoryType());

				preparedStatement.execute();

			} else {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				/*
				 * Retrieving appointment time from and time to from Appointment table based on
				 * appointmentID
				 */
				HashMap<String, String> apptTime = retrieveAppointmentTimeById(patientForm.getAptID());

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, apptTime.get("timeFrom"));
				preparedStatement.setString(5, apptTime.get("timeTo"));
				preparedStatement.setString(6, patientForm.getDiagnosis());
				preparedStatement.setString(7, patientForm.getMedicalNotes());
				preparedStatement.setString(8, ActivityStatus.ACTIVE);
				preparedStatement.setInt(9, patientForm.getPatientID());
				preparedStatement.setInt(10, newVisitRef);
				preparedStatement.setInt(11, patientForm.getNextVisitDays());
				preparedStatement.setInt(12, patientForm.getAptID());
				preparedStatement.setInt(13, patientForm.getClinicID());
				preparedStatement.setString(14, patientForm.getCategory());
				preparedStatement.setString(15, patientForm.getCategoryType());

				preparedStatement.execute();

			}

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public int retrieveLastEnteredVisitIDByVisitNumber(int visitNumber, int patientID, int clinicID) {
		int visitID = 0;

		try {

			connection = getConnection();

			String retrieveLastEnteredVisitIDByVisitNumberQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_ID_BY_VISIT_NUMBER1;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredVisitIDByVisitNumberQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setInt(2, patientID);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return visitID;
	}

	public String updateMedicalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updateMedicalHistoryQuery = QueryMaker.UPDATE_MEDICAL_HISTORY_DETAILS;

			preparedStatement = connection.prepareStatement(updateMedicalHistoryQuery);

			preparedStatement.setString(1, patientForm.getShortHistoryDiagnosis());
			preparedStatement.setString(2, patientForm.getShortHistoryDescription());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, patientForm.getShortHistoryOtherDetails());
			preparedStatement.setString(5, patientForm.getDiabetesMellitus());
			preparedStatement.setDouble(6, patientForm.getDiabetesMellitusDuration());
			preparedStatement.setString(7, patientForm.getDiabetesMellitusDesc());
			preparedStatement.setString(8, patientForm.getHypertension());
			preparedStatement.setDouble(9, patientForm.getHypertensionDuration());
			preparedStatement.setString(10, patientForm.getHypertensionDesc());
			preparedStatement.setString(11, patientForm.getAsthema());
			preparedStatement.setDouble(12, patientForm.getAsthemaDuration());
			preparedStatement.setString(13, patientForm.getAsthemaDesc());
			preparedStatement.setString(14, patientForm.getIschemicHeartDisease());
			preparedStatement.setDouble(15, patientForm.getIschemicHeartDiseaseDuration());
			preparedStatement.setString(16, patientForm.getIschemicHeartDiseaseDesc());
			preparedStatement.setString(17, patientForm.getAllergies());
			preparedStatement.setDouble(18, patientForm.getAllergiesDuration());
			preparedStatement.setString(19, patientForm.getAllergiesDesc());
			preparedStatement.setString(20, patientForm.getSurgicalHistory());
			preparedStatement.setDouble(21, patientForm.getSurgicalHistoryDuration());
			preparedStatement.setString(22, patientForm.getSurgicalHistoryDesc());
			preparedStatement.setString(23, patientForm.getGynecologyHistory());
			preparedStatement.setDouble(24, patientForm.getGynecologyHistoryDuration());
			preparedStatement.setString(25, patientForm.getGynecologyHistoryDesc());
			preparedStatement.setInt(26, patientForm.getPatientID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Medical history details udpated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while udpating medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertMedicalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertMedicalHistoryQuery = QueryMaker.INSERT_MEDICAL_HISTORY_DETAILS;

			preparedStatement = connection.prepareStatement(insertMedicalHistoryQuery);

			preparedStatement.setString(1, patientForm.getShortHistoryDiagnosis());
			preparedStatement.setString(2, patientForm.getShortHistoryDescription());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, patientForm.getShortHistoryOtherDetails());
			preparedStatement.setInt(5, patientForm.getPatientID());
			preparedStatement.setString(6, patientForm.getDiabetesMellitus());
			preparedStatement.setDouble(7, patientForm.getDiabetesMellitusDuration());
			preparedStatement.setString(8, patientForm.getDiabetesMellitusDesc());
			preparedStatement.setString(9, patientForm.getHypertension());
			preparedStatement.setDouble(10, patientForm.getHypertensionDuration());
			preparedStatement.setString(11, patientForm.getHypertensionDesc());
			preparedStatement.setString(12, patientForm.getAsthema());
			preparedStatement.setDouble(13, patientForm.getAsthemaDuration());
			preparedStatement.setString(14, patientForm.getAsthemaDesc());
			preparedStatement.setString(15, patientForm.getIschemicHeartDisease());
			preparedStatement.setDouble(16, patientForm.getIschemicHeartDiseaseDuration());
			preparedStatement.setString(17, patientForm.getIschemicHeartDiseaseDesc());
			preparedStatement.setString(18, patientForm.getAllergies());
			preparedStatement.setDouble(19, patientForm.getAllergiesDuration());
			preparedStatement.setString(20, patientForm.getAllergiesDesc());
			preparedStatement.setString(21, patientForm.getSurgicalHistory());
			preparedStatement.setDouble(22, patientForm.getSurgicalHistoryDuration());
			preparedStatement.setString(23, patientForm.getSurgicalHistoryDesc());
			preparedStatement.setString(24, patientForm.getGynecologyHistory());
			preparedStatement.setDouble(25, patientForm.getGynecologyHistoryDuration());
			preparedStatement.setString(26, patientForm.getGynecologyHistoryDesc());

			preparedStatement.execute();

			status = "success";

			System.out.println("Medical history details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String updateFamilyHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updateFamilyHistoryQuery = QueryMaker.UPDATE_FAMILY_HISTORY_DETAILS;

			preparedStatement = connection.prepareStatement(updateFamilyHistoryQuery);

			preparedStatement.setString(1, patientForm.getFamHistDiagnosis());
			preparedStatement.setString(2, patientForm.getFamHistComment());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, patientForm.getFamHistOther());
			preparedStatement.setString(5, patientForm.getFamHistDiabetesMellitus());
			preparedStatement.setDouble(6, patientForm.getFamHistDiabetesMellitusDuration());
			preparedStatement.setString(7, patientForm.getFamHistDiabetesMellitusDesc());
			preparedStatement.setString(8, patientForm.getFamHistHypertension());
			preparedStatement.setDouble(9, patientForm.getFamHistHypertensionDuration());
			preparedStatement.setString(10, patientForm.getFamHistHypertensionDesc());
			preparedStatement.setString(11, patientForm.getFamHistAsthema());
			preparedStatement.setDouble(12, patientForm.getFamHistAsthemaDuration());
			preparedStatement.setString(13, patientForm.getFamHistAsthemaDesc());
			preparedStatement.setString(14, patientForm.getFamHistAllergies());
			preparedStatement.setDouble(15, patientForm.getFamHistAllergiesDuration());
			preparedStatement.setString(16, patientForm.getFamHistAllergiesDesc());
			preparedStatement.setInt(17, patientForm.getPatientID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Family history details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertFamilyHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertFamilyHistoryQuery = QueryMaker.INSERT_FAMILY_HISTORY_DETAILS;

			preparedStatement = connection.prepareStatement(insertFamilyHistoryQuery);

			preparedStatement.setString(1, patientForm.getFamHistDiagnosis());
			preparedStatement.setString(2, patientForm.getFamHistComment());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, patientForm.getFamHistOther());
			preparedStatement.setInt(5, patientForm.getPatientID());
			preparedStatement.setString(6, patientForm.getFamHistDiabetesMellitus());
			preparedStatement.setDouble(7, patientForm.getFamHistDiabetesMellitusDuration());
			preparedStatement.setString(8, patientForm.getFamHistDiabetesMellitusDesc());
			preparedStatement.setString(9, patientForm.getFamHistHypertension());
			preparedStatement.setDouble(10, patientForm.getFamHistHypertensionDuration());
			preparedStatement.setString(11, patientForm.getFamHistHypertensionDesc());
			preparedStatement.setString(12, patientForm.getFamHistAsthema());
			preparedStatement.setDouble(13, patientForm.getFamHistAsthemaDuration());
			preparedStatement.setString(14, patientForm.getFamHistAsthemaDesc());
			preparedStatement.setString(15, patientForm.getFamHistAllergies());
			preparedStatement.setDouble(16, patientForm.getFamHistAllergiesDuration());
			preparedStatement.setString(17, patientForm.getFamHistAllergiesDesc());

			preparedStatement.execute();

			status = "success";

			System.out.println("Family history details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public boolean verifyOtherComplaintDataExists(String otherComplaint) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyOtherComplaintDataExistsQuery = "SELECT * FROM PVComplaints WHERE complaint = ?";

			preparedStatement1 = connection1.prepareStatement(verifyOtherComplaintDataExistsQuery);

			preparedStatement1.setString(1, otherComplaint);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String insertPVComplaints(String complaint) {

		try {

			connection = getConnection();

			String insertPVComplaintsQuery = QueryMaker.INSERT_PVComplaints;

			preparedStatement1 = connection.prepareStatement(insertPVComplaintsQuery);

			preparedStatement1.setString(1, complaint);

			preparedStatement1.execute();

			status = "success";

			System.out.println("Complaints inserted successfully into PVComplaints table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertVitalSigns(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertVitalSignsQuery = QueryMaker.INSERT_VITAL_SIGNS;

			preparedStatement = connection.prepareStatement(insertVitalSignsQuery);

			preparedStatement.setInt(1, patientForm.getOEPulse());
			preparedStatement.setInt(2, patientForm.getOEBPSys());
			preparedStatement.setInt(3, patientForm.getOEBPDia());
			preparedStatement.setString(4, patientForm.getOERS());
			preparedStatement.setString(5, patientForm.getOECVS());
			preparedStatement.setInt(6, patientForm.getVisitID());
			preparedStatement.setDouble(7, patientForm.getWeight());
			preparedStatement.setDouble(8, patientForm.getHeight());
			preparedStatement.setInt(9, patientForm.getVisitID());
			preparedStatement.setString(10, patientForm.getComment());
			preparedStatement.setDouble(11, patientForm.getTemperature());
			preparedStatement.setDouble(12, patientForm.getRespiration());
			preparedStatement.setString(13, patientForm.getPallor());
			preparedStatement.setString(14, patientForm.getIcterus());
			preparedStatement.setDouble(15, patientForm.getAbdominalCircumference());
			preparedStatement.setDouble(16, patientForm.getBmi());

			preparedStatement.execute();

			status = "success";

			System.out.println("Vital details successfully into VitalSigns table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;

	}

	public String insertPresentComplaints(String complaint, int visitID) {

		try {

			connection = getConnection();

			String insertPresentComplaintsQuery = QueryMaker.INSERT_PRESENT_HISTORY;

			preparedStatement = connection.prepareStatement(insertPresentComplaintsQuery);

			preparedStatement.setString(1, complaint);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Present complaints inserted successfully into PresentComplaints table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertOnExaminationDetails(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertOnExaminationDetailsQuery = QueryMaker.INSERT_ON_EXAMINATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertOnExaminationDetailsQuery);

			preparedStatement.setString(1, patientForm.getOnExamination());

			if (patientForm.getOnExaminationDate() == null || patientForm.getOnExaminationDate() == "") {
				preparedStatement.setString(2, null);
			} else if (patientForm.getOnExaminationDate().isEmpty()) {
				preparedStatement.setString(2, null);
			} else {
				preparedStatement.setString(2,
						dateFormat1.format(dateFormat.parse(patientForm.getOnExaminationDate())));
			}

			preparedStatement.setInt(3, patientForm.getVisitID());
			preparedStatement.setString(4, patientForm.getRsPhonchi());
			preparedStatement.setString(5, patientForm.getRsCrepitation());
			preparedStatement.setString(6, patientForm.getRsClear());
			preparedStatement.setString(7, patientForm.getS1s2());
			preparedStatement.setString(8, patientForm.getMurmur());
			preparedStatement.setDouble(9, patientForm.getMurmurSys());
			preparedStatement.setDouble(10, patientForm.getMurmurDia());
			preparedStatement.setString(11, patientForm.getWnl());
			preparedStatement.setString(12, patientForm.getCnsWNL());
			preparedStatement.setString(13, patientForm.getCnsOther());
			preparedStatement.setString(14, patientForm.getAbdomen());
			preparedStatement.setString(15, patientForm.getRightHypochondriac());
			preparedStatement.setString(16, patientForm.getLeftHypochondriac());
			preparedStatement.setString(17, patientForm.getEpigastric());
			preparedStatement.setString(18, patientForm.getRightLumbar());
			preparedStatement.setString(19, patientForm.getLeftLumbar());
			preparedStatement.setString(20, patientForm.getRightIliac());
			preparedStatement.setString(21, patientForm.getLeftIliac());
			preparedStatement.setString(22, patientForm.getHypogastric());
			preparedStatement.setString(23, patientForm.getUmbilical());

			preparedStatement.execute();

			status = "success";

			System.out.println("On examination details added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertInvestigationDetails(String findings, int visitID, String findingDate, String findingType,
			String reportFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertInvestigationDetailsQuery = QueryMaker.INSERT_INVESTIGATION;

			preparedStatement = connection.prepareStatement(insertInvestigationDetailsQuery);

			preparedStatement.setString(1, findings);
			preparedStatement.setInt(2, visitID);

			if (findingDate == null || findingDate == "") {
				preparedStatement.setString(3, null);
			} else {
				if (findingDate.isEmpty()) {
					preparedStatement.setString(3, null);
				} else if (findingDate.equals("null")) {
					preparedStatement.setString(3, null);
				} else {
					preparedStatement.setString(3, dateFormat1.format(dateFormat.parse(findingDate)));
				}
			}

			preparedStatement.setString(4, findingType);
			preparedStatement.setString(5, reportFileName);

			preparedStatement.execute();

			status = "success";

			System.out.println("Investigation details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting investigation details into Investigation table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public void insertVisitStatus(int visitID, int aptID, int patientID, String inTime, String outTime, String status,
			int clinicID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		outTime = dateFormat.format(date);

		System.out.println("out time.." + outTime);

		try {

			connection = getConnection();

			String insertVisitStatusQuery = QueryMaker.INSERT_VISIT_STATUS;

			preparedStatement = connection.prepareStatement(insertVisitStatusQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, aptID);
			preparedStatement.setString(3, status);

			if (inTime == null || inTime == "") {
				preparedStatement.setString(4, null);
			} else if (inTime.isEmpty()) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setString(4, dateFormat1.format(dateFormat.parse(inTime)));
			}

			preparedStatement.setString(5, dateFormat1.format(dateFormat.parse(outTime)));

			preparedStatement.setInt(6, patientID);
			preparedStatement.setInt(7, clinicID);

			preparedStatement.execute();

			System.out.println("Visit status updated for status: " + status);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public List<PatientForm> retrieveGeneralHospitalPatientDetailsForNewVisit(int patientID, int lastVisitID,
			int clinicID, int aptID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		String profilePic = null;

		int check = 0;

		String realPath = xmlUtil.getContextPath();

		try {

			connection = getConnection();

			/*
			 * Retrieving patient's details based on patientID
			 */
			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setVisitType("New");
				patientForm.setPhone(resultSet.getString("phone"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					patientForm.setDateOfBirth(
							dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				// patientForm.setRegistrationNo(resultSet.getString("registrationNumber"));
				patientForm.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id")));
				patientForm.setEC(resultSet.getString("ec"));

				profilePic = resultSet.getString("profilePic");

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmailID("No");
				} else if (resultSet.getString("email").isEmpty()) {
					patientForm.setEmailID("No");
				} else {
					patientForm.setEmailID(resultSet.getString("email"));
				}
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Checking whether profilePic image exists into profilePic folder or not, if
			 * exists then setting the profile pic name into reportDBName variable of
			 * patientFOrm else copying the image from Logo folder of server to profilePic
			 * folder, if file does not exist into Logo folder too then setting default
			 * image name into reportDBName variable of patientForm
			 */
			if (profilePic == null || profilePic == "") {
				patientForm.setReportDBName("defUser.png");
			} else if (profilePic.isEmpty()) {
				patientForm.setReportDBName("defUser.png");
			} else {

				File file = new File(realPath + profilePic);

				if (file.exists()) {

					System.out.println("real path is ...." + realPath);

					// check whether file exists into profilePic folder if not then copy from Logo
					// folder
					File logoFile = new File(realPath + "profilePics/" + profilePic);
					if (logoFile.exists()) {
						patientForm.setReportDBName(profilePic);
					} else {
						FileUtils.copyFile(file, logoFile);
						patientForm.setReportDBName(profilePic);
					}
				} else {
					patientForm.setReportDBName("defUser.png");
				}

			}

			if (lastVisitID == 0) {

				/*
				 * Retrieving visit details by visitID
				 */
				String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID3;

				preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

				preparedStatement.setInt(1, patientID);
				preparedStatement.setInt(2, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					check = 1;

					patientForm.setVisitID(0);
					patientForm.setDiagnosis(resultSet.getString("diagnosis"));
					patientForm.setMedicalNotes(resultSet.getString("visitNote"));
					patientForm.setAptID(resultSet.getInt("apptID"));
					patientForm.setCategory(resultSet.getString("category"));
					patientForm.setCategoryType(resultSet.getString("categoryType"));
					patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
					patientForm.setNextVisitWeeks(resultSet.getInt("nextVisitWeeks"));
					patientForm.setNextVisitMonths(resultSet.getInt("nextVisitMonths"));

					if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

						String visitDate = dateToBeFormat.format(new Date());

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					} else {

						if (resultSet.getString("visitDate").isEmpty()) {

							String visitDate = dateToBeFormat.format(new Date());

							String[] dateArray = visitDate.split("-");

							patientForm.setVisitDay(dateArray[0]);
							patientForm.setVisitMonth(dateArray[1]);
							patientForm.setVisitYear(dateArray[2]);

							patientForm.setFirstVisitDate(visitDate);

						} else {

							String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

							String[] dateArray = visitDate.split("-");

							patientForm.setVisitDay(dateArray[0]);
							patientForm.setVisitMonth(dateArray[1]);
							patientForm.setVisitYear(dateArray[2]);

							patientForm.setFirstVisitDate(visitDate);

						}

					}

				}

			} else {

				/*
				 * Retrieving visit details by visitID
				 */
				String retrieveVisitDetailQuery = QueryMaker.OPD_PDF_RETRIEVE_VISIT;

				preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

				preparedStatement.setInt(1, lastVisitID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					check = 1;

					patientForm.setVisitID(resultSet.getInt("id"));
					patientForm.setDiagnosis(resultSet.getString("diagnosis"));
					patientForm.setMedicalNotes(resultSet.getString("visitNote"));
					patientForm.setAptID(resultSet.getInt("apptID"));
					patientForm.setCategory(resultSet.getString("category"));
					patientForm.setCategoryType(resultSet.getString("categoryType"));
					patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
					patientForm.setNextVisitWeeks(resultSet.getInt("nextVisitWeeks"));
					patientForm.setNextVisitMonths(resultSet.getInt("nextVisitMonths"));

					if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

						String visitDate = dateToBeFormat.format(new Date());

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					} else {

						if (resultSet.getString("visitDate").isEmpty()) {

							String visitDate = dateToBeFormat.format(new Date());

							String[] dateArray = visitDate.split("-");

							patientForm.setVisitDay(dateArray[0]);
							patientForm.setVisitMonth(dateArray[1]);
							patientForm.setVisitYear(dateArray[2]);

							patientForm.setFirstVisitDate(visitDate);

						} else {

							String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

							String[] dateArray = visitDate.split("-");

							patientForm.setVisitDay(dateArray[0]);
							patientForm.setVisitMonth(dateArray[1]);
							patientForm.setVisitYear(dateArray[2]);

							patientForm.setFirstVisitDate(visitDate);

						}

					}

				}

			}

			if (check == 0) {
				String visitDate = dateToBeFormat.format(new Date());

				String[] dateArray = visitDate.split("-");

				patientForm.setVisitDay(dateArray[0]);
				patientForm.setVisitMonth(dateArray[1]);
				patientForm.setVisitYear(dateArray[2]);

				patientForm.setFirstVisitDate(visitDate);
			}

			resultSet.close();
			preparedStatement.close();

			check = 0;

			/*
			 * Retrieving patient's firstVisit Date
			 */
			String retrieveFirstVisitDateQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_VISIT_DATE;

			preparedStatement = connection.prepareStatement(retrieveFirstVisitDateQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				patientForm.setVisitDate(dateToBeFormat.format(dateFormat.parse(resultSet.getString("visitDate"))));
			}

			if (check == 0) {
				patientForm.setVisitDate(dateToBeFormat.format(new Date()));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving medical history details by patientID
			 */
			String retrieveMedicalHistoryDetailQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveMedicalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setShortHistoryDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setShortHistoryDescription(resultSet.getString("description"));
				patientForm.setShortHistoryOtherDetails(resultSet.getString("otherDetails"));
				patientForm.setDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setHypertension(resultSet.getString("hypertension"));
				patientForm.setHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setAsthema(resultSet.getString("asthema"));
				patientForm.setAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setIschemicHeartDisease(resultSet.getString("ischemicHeartDisease"));
				patientForm.setIschemicHeartDiseaseDesc(resultSet.getString("ischemicHeartDiseaseDesc"));
				patientForm.setIschemicHeartDiseaseDuration(resultSet.getDouble("ischemicHeartDiseaseDuration"));
				patientForm.setAllergies(resultSet.getString("allergies"));
				patientForm.setAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setAllergiesDuration(resultSet.getDouble("allergiesDuration"));
				patientForm.setSurgicalHistory(resultSet.getString("surgicalHistory"));
				patientForm.setSurgicalHistoryDesc(resultSet.getString("surgicalHistoryDesc"));
				patientForm.setSurgicalHistoryDuration(resultSet.getDouble("surgicalHistoryDuration"));
				patientForm.setGynecologyHistory(resultSet.getString("gynecologyHistory"));
				patientForm.setGynecologyHistoryDesc(resultSet.getString("gynecologyHistoryDesc"));
				patientForm.setGynecologyHistoryDuration(resultSet.getDouble("gynecologyHistoryDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveFamilyHistoryDetailQuery = QueryMaker.RETRIEVE_FAMILY_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveFamilyHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setFamHistDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setFamHistComment(resultSet.getString("description"));
				patientForm.setFamHistOther(resultSet.getString("otherDetails"));
				patientForm.setFamHistDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setFamHistDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setFamHistDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setFamHistHypertension(resultSet.getString("hypertension"));
				patientForm.setFamHistHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setFamHistHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setFamHistAsthema(resultSet.getString("asthema"));
				patientForm.setFamHistAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setFamHistAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setFamHistAllergies(resultSet.getString("allergies"));
				patientForm.setFamHistAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setFamHistAllergiesDuration(resultSet.getDouble("allergiesDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrievePersonalHistoryDetailQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePersonalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPersonalHistorySmoking(resultSet.getString("smoking"));
				patientForm.setPersonalHistorySmokingDetails(resultSet.getString("smokingDetails"));
				patientForm.setSmokingDuration(resultSet.getDouble("smokingDuration"));
				patientForm.setAlcohol(resultSet.getString("alcohol"));
				patientForm.setAlcoholDesc(resultSet.getString("alcoholDesc"));
				patientForm.setAlcoholDuration(resultSet.getDouble("alcoholDuration"));
				patientForm.setMishari(resultSet.getString("mishari"));
				patientForm.setMishariDesc(resultSet.getString("mishariDesc"));
				patientForm.setMishariDuration(resultSet.getDouble("mishariDuration"));
				patientForm.setTobacco(resultSet.getString("tobacco"));
				patientForm.setTobaccoDesc(resultSet.getString("tobaccoDesc"));
				patientForm.setTobaccoDuration(resultSet.getDouble("tobaccoDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveVitalDetailQuery = QueryMaker.RETRIEVE_PATIENT_VITALS_FOR_NEW_VISIT;

			preparedStatement = connection.prepareStatement(retrieveVitalDetailQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setOEPulse(resultSet.getInt("pulse"));
				patientForm.setOEBPDia(resultSet.getInt("diastolicBP"));
				patientForm.setOEBPSys(resultSet.getInt("systolicBP"));
				patientForm.setRespiration(resultSet.getDouble("respiration"));
				patientForm.setOERS(resultSet.getString("respiratorySystem"));
				patientForm.setOECVS(resultSet.getString("cardioVascularSystem"));
				patientForm.setWeight(resultSet.getDouble("weight"));
				patientForm.setHeight(resultSet.getDouble("height"));
				patientForm.setTemperature(resultSet.getDouble("temperature"));
				patientForm.setPallor(resultSet.getString("pallor"));
				patientForm.setIcterus(resultSet.getString("icterus"));
				patientForm.setAbdominalCircumference(resultSet.getDouble("abdominalCircumference"));
				patientForm.setBmi(resultSet.getDouble("bmi"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveCVSCNSQuery = QueryMaker.RETRIEVE_CVS_CNS_DETAILS_FOR_NEW_VISIT;

			preparedStatement = connection.prepareStatement(retrieveCVSCNSQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setMurmurDia(resultSet.getDouble("murmurDiastolic"));
				patientForm.setMurmurSys(resultSet.getDouble("murmurSystolic"));
				patientForm.setCnsOther(resultSet.getString("cnsOther"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveSignificantFindigsQuery = QueryMaker.RETRIEVE_SIGNIFICANT_FINDINGS_DETAILS_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(retrieveSignificantFindigsQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "Significant Findings");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setBiopsyFindings(resultSet.getString("findings"));

			}

			/*
			 * Retrieving Physiotherapy details by visitID
			 */
			String retrievePhysiotherapyQuery = QueryMaker.RETRIEVE_Physiotherapy_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(retrievePhysiotherapyQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPhysiotherapyID(resultSet.getInt("id"));
				patientForm.setPhysiotherapy(resultSet.getString("physiotherapy"));

			}
			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving PresentCOMPLAINT details by visitID
			 */

			String retrieveOtherComplaintsForEXistintVisitPatientQuery = QueryMaker.RETRIEVE_OTHER_COMPLAINT_LIST_FOR_EXISTING_VISIT_PATIENT;

			preparedStatement = connection.prepareStatement(retrieveOtherComplaintsForEXistintVisitPatientQuery);

			preparedStatement.setInt(1, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			List<String> OriginalComplaintsList = new ArrayList<String>();

			while (resultSet.next()) {

				patientForm.setComplaintID(resultSet.getInt("id"));

				String complaintList = resultSet.getString("complaints");

				String[] complaintNew = complaintList.split(",");

				for (int i = 0; i < complaintNew.length; i++) {

					OriginalComplaintsList.add(complaintNew[i].trim());
				}

				System.out.println("Complaints List : " + OriginalComplaintsList);

				patientForm.setComplaintsListValues(OriginalComplaintsList);

				// patientForm.setComplaints(resultSet.getString("complaints"));
			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public List<PatientForm> retrieveVisitBillingByVisitID1(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection1 = getConnection();

			String retrieveVisitBillingByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_ID1;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitBillingByVisitIDQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setBillingType(resultSet1.getString("billingType"));
				form.setTotalAmount(resultSet1.getDouble("totalAmount"));
				form.setNetAmount(resultSet1.getDouble("netAmount"));
				form.setReceiptID(resultSet1.getInt("id"));
				form.setReceiptNo(resultSet1.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet1.getTimestamp("receiptDate")));
				form.setCharges(resultSet1.getDouble("consultationCharges"));
				form.setTotalDiscount(resultSet1.getDouble("totalDiscount"));
				form.setAdvPayment(resultSet1.getDouble("advPayment"));
				form.setBalPayment(resultSet1.getDouble("balPayment"));
				form.setPaymentType(resultSet1.getString("paymentType"));
				form.setEmergencyCharges(resultSet1.getDouble("emergencyCharge"));
				form.setMlcCharges(resultSet1.getDouble("MLCCharges"));
				form.setAmbulanceDoctorCharges(resultSet1.getDouble("ambulanceDoctorsCharges"));

			}

			connection = getConnection();

			String retrievePAYMENTDETAILSByreceiptIDQuery = QueryMaker.RETRIEVE_PAYMENT_DETAILS;

			preparedStatement = connection.prepareStatement(retrievePAYMENTDETAILSByreceiptIDQuery);

			preparedStatement.setInt(1, form.getReceiptID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setChequeNo(resultSet.getString("chequeNumber"));
				form.setChequeBankName(resultSet.getString("bankName"));
				form.setChequeBankBranch(resultSet.getString("bankBranch"));
				form.setChequeAmt(resultSet.getDouble("chequeAmount"));

				if (form.getPaymentType().contains("Cheque")) {

					if (resultSet.getString("chequeDate") == null || resultSet.getString("chequeDate") == "") {

						form.setChequeDate(null);
					} else if (resultSet.getString("chequeDate").isEmpty()) {

						form.setChequeDate(null);
					} else {

						form.setChequeDate(dateFormat1.format(dateFormat2.parse(resultSet.getString("chequeDate"))));

					}

				} else {

					form.setChequeDate(null);

				}

				form.setCashPaid(resultSet.getDouble("cashPaid"));
				form.setCashToReturn(resultSet.getDouble("cashToReturn"));
				form.setCardMobileNo(resultSet.getString("cardNumber"));
				form.setChequeIssuedBy(resultSet.getString("chequeIssueBy"));
				form.setCreditNoteBal(resultSet.getDouble("creditNote"));
				form.setCMobileNo(resultSet.getString("mobile"));
				form.setOtherType(resultSet.getString("otherMode"));
				form.setCardAmount(resultSet.getDouble("cardAmount"));
				form.setOtherAmount(resultSet.getDouble("otherAmount"));

			}

			list.add(form);

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PatientForm> retrieveVisitBillingByVisitTypeID(int visitTypeID, int clinicID, String clinicSuffix) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection1 = getConnection();

			String retrieveVisitBillingByVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_ID1;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitBillingByVisitTypeIDQuery);

			preparedStatement1.setInt(1, visitTypeID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setBillingType(resultSet1.getString("billingType"));
				form.setCharges(resultSet1.getDouble("consultationCharges"));
				form.setTotalAmount(resultSet1.getDouble("consultationCharges"));
				form.setNetAmount(resultSet1.getDouble("consultationCharges"));
				form.setTotalDiscount(0);
				form.setAdvPayment(0);
				form.setBalPayment(0);
				form.setReceiptDate(dateFormat.format(new Date()));
				form.setReceiptNo(retrieveReceiptNo(clinicID, clinicSuffix));

				list.add(form);

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrievePresentComplaintsOfLastVisit(int visitID) {

		String complaint = "";

		try {

			connection = getConnection();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrievePresentComplaintsOfLastVisitQuery = QueryMaker.RETRIEVE_PRESENT_COMPLAINT_LIST_FOR_LAST_VISIT;

			preparedStatement = connection.prepareStatement(retrievePresentComplaintsOfLastVisitQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.OTHER);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				complaint = complaint + "," + resultSet.getString("complaints");
			}

			if (complaint.startsWith(",")) {
				complaint = complaint.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return complaint;
	}

	public HashMap<String, String> getPVComplaints() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String PVComplaintsQuery = QueryMaker.RETRIEVE_PVComplaints_LIST;

			preparedStatement = connection.prepareStatement(PVComplaintsQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("complaint"), resultSet.getString("complaint"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public String retrieveCategoryListOfLastVisit(int visitID) {

		String CategoryList = "";

		try {

			connection = getConnection();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveCategoryListOfLastVisitQuery = QueryMaker.RETRIEVE_CATEGORY_LIST_FOR_LAST_VISIT;

			preparedStatement = connection.prepareStatement(retrieveCategoryListOfLastVisitQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CategoryList = resultSet.getString("category") + "," + resultSet.getString("categoryType");
			}

			if (CategoryList.startsWith(",")) {
				CategoryList = CategoryList.substring(1);
			}

			System.out.println("CategoryList: " + CategoryList);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return CategoryList;
	}

	public List<PatientForm> retrievemedicalHistoryListOfLastVisit(PatientForm patientForm, int patientID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		try {

			connection = getConnection();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveMedicalHistoryDetailQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveMedicalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setShortHistoryDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setShortHistoryDescription(resultSet.getString("description"));
				patientForm.setShortHistoryOtherDetails(resultSet.getString("otherDetails"));
				patientForm.setDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setHypertension(resultSet.getString("hypertension"));
				patientForm.setHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setAsthema(resultSet.getString("asthema"));
				patientForm.setAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setIschemicHeartDisease(resultSet.getString("ischemicHeartDisease"));
				patientForm.setIschemicHeartDiseaseDesc(resultSet.getString("ischemicHeartDiseaseDesc"));
				patientForm.setIschemicHeartDiseaseDuration(resultSet.getDouble("ischemicHeartDiseaseDuration"));
				patientForm.setAllergies(resultSet.getString("allergies"));
				patientForm.setAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setAllergiesDuration(resultSet.getDouble("allergiesDuration"));
				patientForm.setSurgicalHistory(resultSet.getString("surgicalHistory"));
				patientForm.setSurgicalHistoryDesc(resultSet.getString("surgicalHistoryDesc"));
				patientForm.setSurgicalHistoryDuration(resultSet.getDouble("surgicalHistoryDuration"));
				patientForm.setGynecologyHistory(resultSet.getString("gynecologyHistory"));
				patientForm.setGynecologyHistoryDesc(resultSet.getString("gynecologyHistoryDesc"));
				patientForm.setGynecologyHistoryDuration(resultSet.getDouble("gynecologyHistoryDuration"));

				list.add(patientForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveFamilyHistoryListOfLastVisit(PatientForm patientForm, int patientID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		try {

			connection = getConnection();
			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveFamilyHistoryDetailQuery = QueryMaker.RETRIEVE_FAMILY_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveFamilyHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setFamHistDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setFamHistComment(resultSet.getString("description"));
				patientForm.setFamHistOther(resultSet.getString("otherDetails"));
				patientForm.setFamHistDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setFamHistDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setFamHistDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setFamHistHypertension(resultSet.getString("hypertension"));
				patientForm.setFamHistHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setFamHistHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setFamHistAsthema(resultSet.getString("asthema"));
				patientForm.setFamHistAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setFamHistAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setFamHistAllergies(resultSet.getString("allergies"));
				patientForm.setFamHistAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setFamHistAllergiesDuration(resultSet.getDouble("allergiesDuration"));

				list.add(patientForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrievePersonalHistoryListOfLastVisit(PatientForm patientForm, int patientID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		try {

			connection = getConnection();
			/*
			 * Retrieving family history details by patientID
			 */

			String retrievePersonalHistoryDetailQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePersonalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPersonalHistorySmoking(resultSet.getString("smoking"));
				patientForm.setPersonalHistorySmokingDetails(resultSet.getString("smokingDetails"));
				patientForm.setSmokingDuration(resultSet.getDouble("smokingDuration"));
				patientForm.setAlcohol(resultSet.getString("alcohol"));
				patientForm.setAlcoholDesc(resultSet.getString("alcoholDesc"));
				patientForm.setAlcoholDuration(resultSet.getDouble("alcoholDuration"));
				patientForm.setMishari(resultSet.getString("mishari"));
				patientForm.setMishariDesc(resultSet.getString("mishariDesc"));
				patientForm.setMishariDuration(resultSet.getDouble("mishariDuration"));
				patientForm.setTobacco(resultSet.getString("tobacco"));
				patientForm.setTobaccoDesc(resultSet.getString("tobaccoDesc"));
				patientForm.setTobaccoDuration(resultSet.getDouble("tobaccoDuration"));

				list.add(patientForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveOnExaminationListByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm patientForm = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveOnExaminationListByVisitIDQuery = QueryMaker.RETRIEVE_ON_EXAMINATION_LIST_BY_VISIT_ID1;

			preparedStatement = connection.prepareStatement(retrieveOnExaminationListByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				patientForm.setOnExaminationID(resultSet.getInt("id"));
				patientForm.setOnExamination(resultSet.getString("description"));

				if (resultSet.getString("examinationDate") == null || resultSet.getString("examinationDate") == "") {
					patientForm.setOnExaminationDate("");
				} else {
					if (resultSet.getString("examinationDate").isEmpty()) {
						patientForm.setOnExaminationDate("");
					} else {
						patientForm.setOnExaminationDate(
								dateFormat.format(dateFormat1.parse(resultSet.getString("examinationDate"))));
					}
				}

				patientForm.setRsPhonchi(resultSet.getString("rhonchi"));
				patientForm.setRsCrepitation(resultSet.getString("crepitation"));
				patientForm.setRsClear(resultSet.getString("clear"));
				patientForm.setS1s2(resultSet.getString("s1s2"));
				patientForm.setMurmur(resultSet.getString("murmur"));
				patientForm.setMurmurSys(resultSet.getDouble("murmurSystolic"));
				patientForm.setMurmurDia(resultSet.getDouble("murmurDiastolic"));
				patientForm.setWnl(resultSet.getString("cvsWNL"));
				patientForm.setCnsWNL(resultSet.getString("cnsWNL"));
				patientForm.setCnsOther(resultSet.getString("cnsOther"));
				patientForm.setAbdomen(resultSet.getString("abdomen"));
				patientForm.setRightHypochondriac(resultSet.getString("rightHypochondriac"));
				patientForm.setLeftHypochondriac(resultSet.getString("leftHypochondriac"));
				patientForm.setEpigastric(resultSet.getString("epigastric"));
				patientForm.setRightLumbar(resultSet.getString("rightLumbar"));
				patientForm.setLeftLumbar(resultSet.getString("leftLumbar"));
				patientForm.setRightIliac(resultSet.getString("rightIliac"));
				patientForm.setLeftIliac(resultSet.getString("leftIliac"));
				patientForm.setHypogastric(resultSet.getString("hypogastric"));
				patientForm.setUmbilical(resultSet.getString("umbilical"));
				list.add(patientForm);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

		}

		return list;
	}

	public List<PatientForm> retrieveInjectionPrescriptionList(int patientID, int visitID, String category) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrieveInjectionPrescriptionListQuery = QueryMaker.RETREIVE_GeneralHospital_PRESCRIPTION_LIST;

			preparedStatement = connection.prepareStatement(retrieveInjectionPrescriptionListQuery);

			preparedStatement.setString(1, category);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPrescriptionID(resultSet.getInt("id"));
				patientForm.setDosageAfterMeal(resultSet.getDouble("dosageAfterMeal"));
				patientForm.setDosageBeforeMeal(resultSet.getDouble("dosageBeforeMeal"));
				patientForm.setDosageAfterDinner(resultSet.getDouble("dosageAfterDinner"));
				patientForm.setComment(resultSet.getString("comment"));
				patientForm.setDuration(resultSet.getInt("duration"));
				patientForm.setTradeName(resultSet.getString("tradeName"));
				patientForm.setPatientID(patientID);
				patientForm.setVisitID(visitID);

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving prescription list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public boolean verifyDataExistsInvestigation(int visitID, String tableName) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsInvestigationQuery = "SELECT visitID FROM " + tableName + " WHERE visitID = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsInvestigationQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String updateGeneralHospitalVitalSignsWeight(double weight, int visitID) {

		try {

			connection = getConnection();

			String updateGeneralHospitalVitalSignsWeightQuery = QueryMaker.UPDATE_GeneralHospital_VITAL_SIGNS_WEIGHT;

			preparedStatement = connection.prepareStatement(updateGeneralHospitalVitalSignsWeightQuery);

			preparedStatement.setDouble(1, weight);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully updated weight details.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating weight into VitalSigns table due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalVitalSignsWeight(double weight, int visitID) {

		try {

			connection = getConnection();

			String insertGeneralHospitalVitalSignsWeightQuery = QueryMaker.INSERT_GeneralHospital_VITAL_SIGNS_WEIGHT;

			preparedStatement = connection.prepareStatement(insertGeneralHospitalVitalSignsWeightQuery);

			preparedStatement.setDouble(1, weight);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			System.out.println("Successfully inserted weight details.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting weight into VitalSigns table due to:::"
					+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String updateGeneralHospitalPrescribedInvestigatios(PatientForm patientForm, int visitID) {

		try {
			connection = getConnection();

			String updateGeneralHospitalPrescribedInvestigatiosQuery = QueryMaker.UPDATE_GeneralHospital_Prescribed_Investigations;

			preparedStatement = connection.prepareStatement(updateGeneralHospitalPrescribedInvestigatiosQuery);

			preparedStatement.setString(1, patientForm.getInvestigation());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Prescribed Investigation details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Prescribed Investigation details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalPrescribedInvestigatios(PatientForm patientForm, int visitID) {

		try {
			connection = getConnection();

			String insertGeneralHospitalPrescribedInvestigatiosQuery = QueryMaker.INSERT_GeneralHospital_Prescribed_Investigations;

			preparedStatement = connection.prepareStatement(insertGeneralHospitalPrescribedInvestigatiosQuery);

			preparedStatement.setString(1, patientForm.getInvestigation());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Prescribed Investigation details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Prescribed Investigation details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String updateGeneralHospitalPhysiotherapy(String physiotherapy, int visitID) {

		try {
			connection = getConnection();

			String updateGeneralHospitalPhysiotherapyQuery = QueryMaker.UPDATE_GeneralHospital_Physiotherapy;

			preparedStatement = connection.prepareStatement(updateGeneralHospitalPhysiotherapyQuery);

			preparedStatement.setString(1, physiotherapy);
			preparedStatement.setInt(2, visitID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Physiotherapy details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Physiotherapy detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalPhysiotherapy(String physiotherapy, int visitID) {

		try {
			connection = getConnection();

			String insertGeneralHospitalPhysiotherapyQuery = QueryMaker.INSERT_GeneralHospital_Physiotherapy;

			preparedStatement = connection.prepareStatement(insertGeneralHospitalPhysiotherapyQuery);

			preparedStatement.setString(1, physiotherapy);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Physiotherapy details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Physiotherapy detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalPrescriptionVisitDetail(PatientForm patientForm, int visitID) {

		try {
			connection = getConnection();

			String insertGeneralHospitalPrescriptionVisitDetailQuery = QueryMaker.Update_VISIT_DETAILS;

			preparedStatement = connection.prepareStatement(insertGeneralHospitalPrescriptionVisitDetailQuery);

			preparedStatement.setInt(1, patientForm.getNextVisitDays());
			preparedStatement.setInt(2, patientForm.getNextVisitWeeks());
			preparedStatement.setInt(3, patientForm.getNextVisitMonths());
			preparedStatement.setInt(4, visitID);

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalVisitPrescriptionDetails(int productID, double dosageBefore, double dosageAfter,
			double duration, int visitID, double dosageAfterDinner, String comment) {

		try {

			connection = getConnection();

			String insertGeneralHospitalVisitPrescriptionDetailsQuery = QueryMaker.INSERT_GeneralHospital_VISIT_PRESCRIPTION_DETAILS;

			preparedStatement = connection.prepareStatement(insertGeneralHospitalVisitPrescriptionDetailsQuery);

			preparedStatement.setInt(1, productID);
			preparedStatement.setDouble(2, dosageBefore);
			preparedStatement.setDouble(3, dosageAfter);
			preparedStatement.setDouble(4, duration);
			preparedStatement.setDouble(5, dosageAfterDinner);
			preparedStatement.setString(6, comment);
			preparedStatement.setString(7, ActivityStatus.ACTIVE);
			preparedStatement.setInt(8, visitID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public boolean verifyVisitExistsNew(String tableName, int visitID) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsQuery = "SELECT id FROM " + tableName + " WHERE id = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String updateBillDetails(PatientForm patientForm, int visitID) {
		try {
			connection = getConnection();

			String updateVisitQuery = QueryMaker.UPDATE_BILLING_DETAILS1;

			preparedStatement = connection.prepareStatement(updateVisitQuery);

			preparedStatement.setDouble(1, patientForm.getTotalAmount());
			preparedStatement.setDouble(2, patientForm.getTotalDiscount());
			preparedStatement.setDouble(3, patientForm.getNetAmount());
			preparedStatement.setDouble(4, patientForm.getAdvPayment());
			preparedStatement.setDouble(5, patientForm.getBalPayment());
			preparedStatement.setString(6, patientForm.getPaymentType());

			preparedStatement.setInt(7, visitID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Successfully updated bill details into Receipt table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating billing details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public boolean verifyTransactionDetailsExists(String tableName, String chargeType, int receiptID) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsQuery = "SELECT id FROM " + tableName + " WHERE chargeType = ? AND receiptID = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setString(1, chargeType);
			preparedStatement1.setInt(2, receiptID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String updateTransactionDetails(int transactionID, double chargeQty, double chargeAmt) {

		try {

			connection = getConnection();

			String UPDATETransactionDetailsQuery = QueryMaker.UPDATE_TRANSACTION_DETAILS1;

			preparedStatement = connection.prepareStatement(UPDATETransactionDetailsQuery);

			preparedStatement.setDouble(1, chargeQty);
			preparedStatement.setDouble(2, chargeAmt);
			preparedStatement.setInt(3, transactionID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		}

		return status;
	}

	public String insertTransactionDetails(String chargeType, int receiptID, double chargeQty, double chargeRate,
			double chargeAmt) {

		try {

			connection = getConnection();

			String insertTransactionDetailsQuery = QueryMaker.INSERT_TRANSACTION_DETAILS1;

			preparedStatement = connection.prepareStatement(insertTransactionDetailsQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setDouble(2, chargeQty);
			preparedStatement.setDouble(3, chargeRate);
			preparedStatement.setDouble(4, chargeAmt);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.setInt(6, receiptID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		}

		return status;
	}

	public String inserGeneralHospitalBillDetails(PatientForm patientForm, int visitID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			connection = getConnection();

			System.out.println("InsertBillDetails: " + visitID);

			String insertBillDetailsQuery = QueryMaker.INSERT_BILL_DETAIL1;

			preparedStatement = connection.prepareStatement(insertBillDetailsQuery);

			preparedStatement.setString(1, patientForm.getReceiptNo());

			if (patientForm.getReceiptDate() == null || patientForm.getReceiptDate() == "") {
				preparedStatement.setString(2, null);
			} else {

				if (patientForm.getReceiptDate().isEmpty()) {
					preparedStatement.setString(2, null);
				} else {
					preparedStatement.setString(2,
							dateToBeFormatted.format(dateFormat.parse(patientForm.getReceiptDate())));
				}
			}

			preparedStatement.setString(3, patientForm.getBillingType());
			preparedStatement.setDouble(4, patientForm.getCharges());
			preparedStatement.setDouble(5, patientForm.getTotalAmount());
			preparedStatement.setDouble(6, patientForm.getTotalDiscount());
			preparedStatement.setDouble(7, patientForm.getNetAmount());
			preparedStatement.setDouble(8, patientForm.getAdvPayment());
			preparedStatement.setDouble(9, patientForm.getBalPayment());
			preparedStatement.setString(10, patientForm.getPaymentType());

			preparedStatement.setInt(11, visitID);

			preparedStatement.execute();

			status = "success";
			System.out.println("bill detail inserted successfully into table Billing.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting bill detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String retrieveReceiptDetailsByVisitID(int visitID) {

		String receiptDetails = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			String retrieveReceiptDetailsByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_ID1;

			preparedStatement = connection.prepareStatement(retrieveReceiptDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				receiptDetails = resultSet.getString("receiptNo") + "$"
						+ dateFormat.format(resultSet.getTimestamp("receiptDate"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving receiptID from table due to:::" + exception.getMessage());
		}

		return receiptDetails;
	}

	public String generalHospitalLabReportDownload(int reportsID) {

		String fileName = "";

		try {

			connection = getConnection();

			String retrieveLabReportFileNameQuery = QueryMaker.RETRIEVE_GeneralHospital_LAB_REPORT_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveLabReportFileNameQuery);

			preparedStatement.setInt(1, reportsID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				fileName = resultSet.getString("report");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving lab report filename from table due to:::"
					+ exception.getMessage());
		}
		return fileName;
	}

	public int retrieveRoomTypeByVisitID(int visitID) {

		int roomTypeID = 0;

		try {

			connection = getConnection();

			String retrieveRoomTypeByVisitIDQuery = QueryMaker.OPD_PDF_RETRIEVE_VISIT;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				roomTypeID = resultSet.getInt("roomTypeID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return roomTypeID;
	}

	public List<PatientForm> retrieveIPDChargesByVisitID(int visitID, String chargeType) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveIPDChargesByVisitIDQuery = QueryMaker.RETRIEVE_IPD_CHARGES_BY_VISIT_ID_AND_TYPE;

			preparedStatement = connection.prepareStatement(retrieveIPDChargesByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, chargeType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PatientForm();

				form.setIPDChargeID(resultSet.getInt("id"));
				form.setIPDChargeName(resultSet.getString("chargeName"));
				form.setIPDChargeType(resultSet.getString("chargeType"));
				form.setIPDRate(resultSet.getDouble("rate"));
				form.setIPDQuantity(resultSet.getDouble("quantity"));
				form.setIPDAmount(resultSet.getDouble("amount"));
				form.setPatientID(resultSet.getInt("patientID"));
				form.setVisitID(visitID);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveOTChargesByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveOTChargesByVisitIDQuery = QueryMaker.RETRIEVE_OPD_CHARGES_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveOTChargesByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PatientForm();

				form.setOtChargeID(resultSet.getInt("id"));
				form.setOperationName(resultSet.getString("operationName"));
				form.setConsultantName(resultSet.getString("consultantName"));
				form.setOTAssistantName(resultSet.getString("OTAssistantName"));
				form.setAnaesthetistName(resultSet.getString("anaesthetistName"));
				form.setOtDateTime(resultSet.getString("operationDateTime"));
				form.setPatientID(resultSet.getInt("patientID"));
				form.setRate(resultSet.getDouble("rate"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyIPDDischargeVisitType(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyDischargeVisitTypeQuery = QueryMaker.VERIFY_DISCHARGE_IPD_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(verifyDischargeVisitTypeQuery);

			preparedStatement.setInt(1, visitTypeID);
			preparedStatement.setInt(2, 1);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public int retrieveNewVisitRef(int visitID) {

		int newVisitRef = 0;

		try {

			connection = getConnection();

			String retrieveNewVisitRefQuery = QueryMaker.RETRIEVE_NEW_VISIT_REF;

			preparedStatement = connection.prepareStatement(retrieveNewVisitRefQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				newVisitRef = resultSet.getInt("newVisitRef");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return newVisitRef;
	}

	public String retrievePaymentType(int visitID) {

		String paymentType = "";

		try {

			connection = getConnection();

			String retrievePaymentTypeQuery = QueryMaker.RETRIEVE_RECEIPT_DETAILS_FOR_PDF;

			preparedStatement = connection.prepareStatement(retrievePaymentTypeQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				paymentType = resultSet.getString("paymentType");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return paymentType;
	}

	public List<PatientForm> retrieveIPDTransactionsByVisitID(int visitID, String chargeType) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			String retrieveIPDTransactionsByVisitIDQuery = QueryMaker.RETRIEVE_IPD_TRANSACTIONS_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveIPDTransactionsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, chargeType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PatientForm();

				form.setIPDChargeType(resultSet.getString("chargeType"));
				form.setIPDChargeName(resultSet.getString("IPDCharge"));
				form.setIPDRate(resultSet.getDouble("rate"));
				form.setIPDQuantity(resultSet.getDouble("quantity"));
				form.setIPDAmount(resultSet.getDouble("amount"));

				if (resultSet.getString("otDateTime") == null || resultSet.getString("otDateTime") == "") {
					form.setOtDateTime(null);
				} else if (resultSet.getString("otDateTime").isEmpty()) {
					form.setOtDateTime(null);
				} else {
					form.setOtDateTime(dateFormat.format(resultSet.getTimestamp("otDateTime")));
				}
				form.setTransactionID(resultSet.getInt("id"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveIPDChargesDetailsByNewVisitID(int newVisitID, String chargeType) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveIPDChargesDetailsByNewVisitIDQuery = QueryMaker.RETRIEVE_FINAL_IPD_CHARGES_BY_NEW_VISIT_ID_AND_CHARGE_TYPE;

			preparedStatement = connection.prepareStatement(retrieveIPDChargesDetailsByNewVisitIDQuery);

			preparedStatement.setInt(1, newVisitID);
			preparedStatement.setInt(2, newVisitID);
			preparedStatement.setString(3, chargeType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PatientForm();

				form.setIPDChargeType(resultSet.getString("chargeType"));
				form.setIPDChargeName(resultSet.getString("chargeName"));
				form.setIPDRate(resultSet.getDouble("rate"));
				form.setIPDQuantity(resultSet.getDouble("quantity"));
				form.setIPDAmount(resultSet.getDouble("amount"));
				form.setOtDateTime(null);
				form.setTransactionID(0);

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveOTChargesDetailsByNewVisitID(int newVisitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveOTChargesDetailsByNewVisitIDQuery = QueryMaker.RETRIEVE_FINAL_OT_CHARGES_BY_NEW_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveOTChargesDetailsByNewVisitIDQuery);

			preparedStatement.setInt(1, newVisitID);
			preparedStatement.setInt(2, newVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PatientForm();

				form.setOtChargeID(resultSet.getInt("id"));
				form.setOperationName(resultSet.getString("operationName"));
				form.setConsultantName(resultSet.getString("consultantName"));
				form.setOTAssistantName(resultSet.getString("OTAssistantName"));
				form.setAnaesthetistName(resultSet.getString("anaesthetistName"));
				form.setOtDateTime(resultSet.getString("operationDateTime"));
				form.setIPDChargeType(ActivityStatus.OT);
				form.setIPDRate(resultSet.getDouble("rate"));
				form.setIPDChargeName(resultSet.getString("operationName"));
				form.setTransactionID(0);

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveIPDBillCharges(int visitTypeID, int clinicID, String clinicSuffix,
			int newVisitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		form.setReceiptDate(dateFormat.format(new Date()));
		form.setReceiptNo(retrieveReceiptNo(clinicID, clinicSuffix));

		double IPDTotalCharge = 0D;
		double OTTotalCharge = 0D;
		double advancePayment = 0D;
		double emergencyCharge = 0D;
		double MLCCharge = 0D;
		double ambulanceDoctorCharge = 0D;

		try {

			connection1 = getConnection();

			/*
			 * Retrieving ipd charges sum for the newVisitID and all visitID having
			 * newVisitRef as newVisitID from IPD Charge table
			 */
			String retrieveIPDTotalChargeQuery = QueryMaker.RETRIEVE_IPD_TOTAL_CHARGE_BY_NEW_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveIPDTotalChargeQuery);

			preparedStatement1.setInt(1, newVisitID);
			preparedStatement1.setInt(2, newVisitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				IPDTotalCharge = resultSet1.getDouble("totalRate");
			}

			resultSet1.close();
			preparedStatement1.close();

			/*
			 * Retrieving OT charges sum for the newVisitID and all visitID having
			 * newVisitRef as newVisitID from OT Charge table
			 */
			String retrieveOTChargeQuery = QueryMaker.RETRIEVE_OT_TOTAL_CHARGE_BY_NEW_VISIT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveOTChargeQuery);

			preparedStatement1.setInt(1, newVisitID);
			preparedStatement1.setInt(2, newVisitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				OTTotalCharge = resultSet1.getDouble("totalRate");
			}

			resultSet1.close();
			preparedStatement1.close();

			/*
			 * Retrieving emergency charges sum and sum of advance payments for the
			 * newVisitID and all visitID having newVisitRef as newVisitID from Visit table
			 */
			String retrieveAdvAndEmergencyChargesQuery = QueryMaker.RETRIEVE_TOTAL_IPD_ADVANCE_PAYMENT;

			preparedStatement1 = connection1.prepareStatement(retrieveAdvAndEmergencyChargesQuery);

			preparedStatement1.setInt(1, newVisitID);
			preparedStatement1.setInt(2, newVisitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				advancePayment = resultSet1.getDouble("totalAdvancePayment");
				emergencyCharge = resultSet1.getDouble("totalEmergencyPayment");
				MLCCharge = resultSet1.getDouble("MLCCharges");
				ambulanceDoctorCharge = resultSet1.getDouble("ambulanceDoctorsCharges");
			}

			double totalAmount = IPDTotalCharge + OTTotalCharge;
			double netAmount = totalAmount + emergencyCharge + MLCCharge + ambulanceDoctorCharge;

			double balanceAmount = netAmount - advancePayment;

			form.setTotalAmount(totalAmount);
			form.setNetAmount(netAmount);
			form.setTotalDiscount(0);
			form.setEmergencyCharges(emergencyCharge);
			form.setAdvPayment(advancePayment);
			form.setBalPayment(balanceAmount);
			form.setMlcCharges(MLCCharge);
			form.setAmbulanceDoctorCharges(ambulanceDoctorCharge);

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return list;
	}

	public JSONObject insertBillDetails(PatientForm patientForm) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String dateToBeInsert = simpleDateFormat.format(date);
		try {
			connection = getConnection();

			String insertBillDetailsQuery = QueryMaker.INSERT_BILL_DETAIL1;

			preparedStatement = connection.prepareStatement(insertBillDetailsQuery);

			preparedStatement.setString(1, patientForm.getDescription());
			preparedStatement.setDouble(2, patientForm.getCharges());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, patientForm.getVisitID());
			preparedStatement.setDouble(5, patientForm.getRate());
			preparedStatement.setDouble(6, patientForm.getTotalBill());
			preparedStatement.setString(7, dateToBeInsert);

			preparedStatement.execute();

			object.put("SuccessMessage", "New Bill added successfully. ");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting billing details into table due to:::" + exception.getMessage());

			object.put("ErrorMessage", "Failed to add new bill. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveBillDetail(int visitID, String serviceCharge) {

		JSONObject values = null;

		double totalBill = 0.0;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveBillDetailQuery = QueryMaker.RETRIEVE_BILLING_DETAIL;
			String retrieveTotalBillQuery = QueryMaker.RETRIEVE_TOTAL_BILL;

			/*
			 * Retrieving total service charge
			 */
			preparedStatement1 = connection.prepareStatement(retrieveTotalBillQuery);

			preparedStatement1.setInt(1, visitID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				totalBill = resultSet1.getDouble("SUM");
				System.out.println("TOtal bill is :: " + totalBill);
			}

			preparedStatement = connection.prepareStatement(retrieveBillDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("billID", resultSet.getInt("id"));
				object.put("chargeType", resultSet.getString("chargeType"));
				object.put("charge", resultSet.getString("charge"));
				object.put("rate", resultSet.getString("rate"));
				object.put("totalBill", totalBill);
				object.put("visitID", resultSet.getInt("visitID"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			resultSet1.close();
			preparedStatement1.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveTradeNameForPrescriptionBYProductID(String tradeName, String category) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrievePrscriptionDetailQuery = QueryMaker.RETRIEVE_TradeName_BY_ProductID;

			preparedStatement = connection.prepareStatement(retrievePrscriptionDetailQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setString(2, category);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("tradeNameID", resultSet.getInt("id"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject deleteGeneralHospitalPrescRow(int prescriptionID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteGeneralHospitalPrescRowQuery = QueryMaker.DELETE_GeneralHospital_Presc_Row;

			preparedStatement = connection.prepareStatement(deleteGeneralHospitalPrescRowQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, prescriptionID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting biopsy findings due to:::" + exception.getMessage());

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject deleteBilling(int billID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String deleteBillingQuery = QueryMaker.DELETE_BILL_DETAILS;

			preparedStatement = connection.prepareStatement(deleteBillingQuery);

			preparedStatement.setInt(2, billID);
			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.executeUpdate();

			object.put("SuccessMessage", "Bill deleted successfully.");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Billing details into table due to:::" + exception.getMessage());

			object.put("ErrorMessage", "Failed to delete prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrievePrscriptionDetail(int visitID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 1;

		try {
			connection = getConnection();

			String retrievePrscriptionDetailQuery = QueryMaker.RETRIEVE_PRESCRIPTION_DETAIL;

			preparedStatement = connection.prepareStatement(retrievePrscriptionDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("prescriptionID", resultSet.getInt("id"));
				object.put("drugName", resultSet.getString("drugName"));
				object.put("dose", resultSet.getString("dose"));
				object.put("doseUnit", resultSet.getString("tradeName"));
				object.put("numberOfDays", resultSet.getInt("numberOfDays"));
				object.put("frequency", resultSet.getString("frequency"));
				object.put("comment", resultSet.getString("comment"));
				object.put("visitID", resultSet.getInt("visitID"));
				object.put("srNo", count);
				object.put("noOfPills", resultSet.getInt("pillCount"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Prescription details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public List<PatientForm> retrieveGeneralHospitalPatientDetailsForExistiongVisit(int patientID, int visitID,
			int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		String profilePic = null;

		int check = 0;

		String realPath = xmlUtil.getContextPath();

		try {

			connection = getConnection();

			/*
			 * Retrieving patient's details based on patientID
			 */
			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setVisitType("New");
				patientForm.setPhone(resultSet.getString("phone"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					patientForm.setDateOfBirth(
							dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				// patientForm.setRegistrationNo(resultSet.getString("registrationNumber"));
				patientForm.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id")));
				patientForm.setEC(resultSet.getString("ec"));

				profilePic = resultSet.getString("profilePic");

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmailID("No");
				} else if (resultSet.getString("email").isEmpty()) {
					patientForm.setEmailID("No");
				} else {
					patientForm.setEmailID(resultSet.getString("email"));
				}
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Checking whether profilePic image exists into profilePic folder or not, if
			 * exists then setting the profile pic name into reportDBName variable of
			 * patientFOrm else copying the image from Logo folder of server to profilePic
			 * folder, if file does not exist into Logo folder too then setting default
			 * image name into reportDBName variable of patientForm
			 */
			if (profilePic == null || profilePic == "") {
				patientForm.setReportDBName("defUser.png");
			} else if (profilePic.isEmpty()) {
				patientForm.setReportDBName("defUser.png");
			} else {

				File file = new File(realPath + profilePic);

				if (file.exists()) {

					System.out.println("real path is ...." + realPath);

					// check whether file exists into profilePic folder if not then copy from Logo
					// folder
					File logoFile = new File(realPath + "profilePics/" + profilePic);
					if (logoFile.exists()) {
						patientForm.setReportDBName(profilePic);
					} else {
						FileUtils.copyFile(file, logoFile);
						patientForm.setReportDBName(profilePic);
					}
				} else {
					patientForm.setReportDBName("defUser.png");
				}

			}

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_Existing_VISIT_DEATILS_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				patientForm.setVisitID(resultSet.getInt("id"));
				;
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setCategory(resultSet.getString("category"));
				patientForm.setCategoryType(resultSet.getString("categoryType"));
				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));
				patientForm.setNextVisitWeeks(resultSet.getInt("nextVisitWeeks"));
				patientForm.setNextVisitMonths(resultSet.getInt("nextVisitMonths"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					String visitDate = dateToBeFormat.format(new Date());

					String[] dateArray = visitDate.split("-");

					patientForm.setVisitDay(dateArray[0]);
					patientForm.setVisitMonth(dateArray[1]);
					patientForm.setVisitYear(dateArray[2]);

					patientForm.setFirstVisitDate(visitDate);

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						String visitDate = dateToBeFormat.format(new Date());

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

			}

			if (check == 0) {
				String visitDate = dateToBeFormat.format(new Date());

				String[] dateArray = visitDate.split("-");

				patientForm.setVisitDay(dateArray[0]);
				patientForm.setVisitMonth(dateArray[1]);
				patientForm.setVisitYear(dateArray[2]);

				patientForm.setFirstVisitDate(visitDate);
			}

			resultSet.close();
			preparedStatement.close();

			check = 0;

			/*
			 * Retrieving patient's firstVisit Date
			 */
			String retrieveFirstVisitDateQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_VISIT_DATE;

			preparedStatement = connection.prepareStatement(retrieveFirstVisitDateQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				patientForm.setVisitDate(dateToBeFormat.format(dateFormat.parse(resultSet.getString("visitDate"))));
			}

			if (check == 0) {
				patientForm.setVisitDate(dateToBeFormat.format(new Date()));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving medical history details by patientID
			 */
			String retrieveMedicalHistoryDetailQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveMedicalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setShortHistoryDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setShortHistoryDescription(resultSet.getString("description"));
				patientForm.setShortHistoryOtherDetails(resultSet.getString("otherDetails"));
				patientForm.setDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setHypertension(resultSet.getString("hypertension"));
				patientForm.setHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setAsthema(resultSet.getString("asthema"));
				patientForm.setAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setIschemicHeartDisease(resultSet.getString("ischemicHeartDisease"));
				patientForm.setIschemicHeartDiseaseDesc(resultSet.getString("ischemicHeartDiseaseDesc"));
				patientForm.setIschemicHeartDiseaseDuration(resultSet.getDouble("ischemicHeartDiseaseDuration"));
				patientForm.setAllergies(resultSet.getString("allergies"));
				patientForm.setAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setAllergiesDuration(resultSet.getDouble("allergiesDuration"));
				patientForm.setSurgicalHistory(resultSet.getString("surgicalHistory"));
				patientForm.setSurgicalHistoryDesc(resultSet.getString("surgicalHistoryDesc"));
				patientForm.setSurgicalHistoryDuration(resultSet.getDouble("surgicalHistoryDuration"));
				patientForm.setGynecologyHistory(resultSet.getString("gynecologyHistory"));
				patientForm.setGynecologyHistoryDesc(resultSet.getString("gynecologyHistoryDesc"));
				patientForm.setGynecologyHistoryDuration(resultSet.getDouble("gynecologyHistoryDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveFamilyHistoryDetailQuery = QueryMaker.RETRIEVE_FAMILY_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveFamilyHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setFamHistDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setFamHistComment(resultSet.getString("description"));
				patientForm.setFamHistOther(resultSet.getString("otherDetails"));
				patientForm.setFamHistDiabetesMellitus(resultSet.getString("diabetesMellitus"));
				patientForm.setFamHistDiabetesMellitusDesc(resultSet.getString("diabetesMellitusDesc"));
				patientForm.setFamHistDiabetesMellitusDuration(resultSet.getDouble("diabetesMellitusDuration"));
				patientForm.setFamHistHypertension(resultSet.getString("hypertension"));
				patientForm.setFamHistHypertensionDesc(resultSet.getString("hypertensionDesc"));
				patientForm.setFamHistHypertensionDuration(resultSet.getDouble("hypertensionDuration"));
				patientForm.setFamHistAsthema(resultSet.getString("asthema"));
				patientForm.setFamHistAsthemaDesc(resultSet.getString("asthemaDesc"));
				patientForm.setFamHistAsthemaDuration(resultSet.getDouble("asthemaDuration"));
				patientForm.setFamHistAllergies(resultSet.getString("allergies"));
				patientForm.setFamHistAllergiesDesc(resultSet.getString("allergiesDesc"));
				patientForm.setFamHistAllergiesDuration(resultSet.getDouble("allergiesDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrievePersonalHistoryDetailQuery = QueryMaker.RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePersonalHistoryDetailQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPersonalHistorySmoking(resultSet.getString("smoking"));
				patientForm.setPersonalHistorySmokingDetails(resultSet.getString("smokingDetails"));
				patientForm.setSmokingDuration(resultSet.getDouble("smokingDuration"));
				patientForm.setAlcohol(resultSet.getString("alcohol"));
				patientForm.setAlcoholDesc(resultSet.getString("alcoholDesc"));
				patientForm.setAlcoholDuration(resultSet.getDouble("alcoholDuration"));
				patientForm.setMishari(resultSet.getString("mishari"));
				patientForm.setMishariDesc(resultSet.getString("mishariDesc"));
				patientForm.setMishariDuration(resultSet.getDouble("mishariDuration"));
				patientForm.setTobacco(resultSet.getString("tobacco"));
				patientForm.setTobaccoDesc(resultSet.getString("tobaccoDesc"));
				patientForm.setTobaccoDuration(resultSet.getDouble("tobaccoDuration"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveVitalDetailQuery = QueryMaker.RETRIEVE_PATIENT_VITALS_FOR_EXISTTING_VISIT;

			preparedStatement = connection.prepareStatement(retrieveVitalDetailQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setOEPulse(resultSet.getInt("pulse"));
				patientForm.setOEBPDia(resultSet.getInt("diastolicBP"));
				patientForm.setOEBPSys(resultSet.getInt("systolicBP"));
				patientForm.setRespiration(resultSet.getDouble("respiration"));
				patientForm.setOERS(resultSet.getString("respiratorySystem"));
				patientForm.setOECVS(resultSet.getString("cardioVascularSystem"));
				patientForm.setWeight(resultSet.getDouble("weight"));
				patientForm.setHeight(resultSet.getDouble("height"));
				patientForm.setTemperature(resultSet.getDouble("temperature"));
				patientForm.setPallor(resultSet.getString("pallor"));
				patientForm.setIcterus(resultSet.getString("icterus"));
				patientForm.setAbdominalCircumference(resultSet.getDouble("abdominalCircumference"));
				patientForm.setBmi(resultSet.getDouble("bmi"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveCVSCNSQuery = QueryMaker.RETRIEVE_CVS_CNS_DETAILS_FOR_EXISTING_VISIT;

			preparedStatement = connection.prepareStatement(retrieveCVSCNSQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setMurmurDia(resultSet.getDouble("murmurDiastolic"));
				patientForm.setMurmurSys(resultSet.getDouble("murmurSystolic"));
				patientForm.setCnsOther(resultSet.getString("cnsOther"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveSignificantFindigsQuery = QueryMaker.RETRIEVE_SIGNIFICANT_FINDINGS_DETAILS_FOR_EXISTING_PATIENT;

			preparedStatement = connection.prepareStatement(retrieveSignificantFindigsQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, "Significant Findings");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setBiopsyFindings(resultSet.getString("findings"));

			}
			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving Physiotherapy details by visitID
			 */
			String retrievePhysiotherapyQuery = QueryMaker.RETRIEVE_Physiotherapy_FOR_EXISTING_PATIENT;

			preparedStatement = connection.prepareStatement(retrievePhysiotherapyQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPhysiotherapyID(resultSet.getInt("id"));
				patientForm.setPhysiotherapy(resultSet.getString("physiotherapy"));

			}
			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving PresentCOMPLAINT details by visitID
			 */

			String retrieveOtherComplaintsForEXistintVisitPatientQuery = QueryMaker.RETRIEVE_OTHER_COMPLAINT_LIST_FOR_EXISTING_VISIT_PATIENT;

			preparedStatement = connection.prepareStatement(retrieveOtherComplaintsForEXistintVisitPatientQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			List<String> OriginalComplaintsList = new ArrayList<String>();

			while (resultSet.next()) {

				patientForm.setComplaintID(resultSet.getInt("id"));

				String complaintList = resultSet.getString("complaints");

				String[] complaintNew = complaintList.split(",");

				for (int i = 0; i < complaintNew.length; i++) {

					OriginalComplaintsList.add(complaintNew[i].trim());
				}

				System.out.println("Complaints List : " + OriginalComplaintsList);

				patientForm.setComplaintsListValues(OriginalComplaintsList);

				// patientForm.setComplaints(resultSet.getString("complaints"));
			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public List<PatientForm> retrieveOtherFindingsListByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String retrieveOtherFindingsListByVisitIDQuery = QueryMaker.RETRIEVE_INVESTIGATION_LIST_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveOtherFindingsListByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.OTHER);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PatientForm();

				form.setOtherFindingDescription(resultSet.getString("findings"));
				form.setOtherFindingType(resultSet.getString("findingType"));
				form.setReportDBName(resultSet.getString("reportFile"));

				if (resultSet.getString("findingDate") == null || resultSet.getString("findingDate") == "") {
					form.setOtherFindingDate("");
				} else {
					if (resultSet.getString("findingDate").isEmpty()) {
						form.setOtherFindingDate("");
					} else {
						form.setOtherFindingDate(dateFormat.format(resultSet.getDate("findingDate")));
					}
				}

				form.setFindingsID(resultSet.getInt("id"));

				list.add(form);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PatientForm> retrieveVitalSignsListByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String retrieveVitalSignsListByVisitIDQuery = QueryMaker.RETRIEVE_VITAL_SIGNS_LIST_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveVitalSignsListByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PatientForm();

				form.setWeightName(resultSet.getDouble("weight"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {
					form.setVitalSignsDateName(dateFormat.format(resultSet.getDate("oldVisitDate")));
				} else {
					if (resultSet.getString("visitDate").isEmpty()) {
						form.setVitalSignsDateName(dateFormat.format(resultSet.getDate("oldVisitDate")));
					} else {
						form.setVitalSignsDateName(dateFormat.format(resultSet.getDate("visitDate")));
					}
				}

				form.setVitalSignsID(resultSet.getInt("id"));
				form.setComment(resultSet.getString("comment"));

				list.add(form);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String updateGeneralHospitalPatientVisit(PatientForm patientForm, int visitNumber, int neVisitRef) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {

			System.out.println("patientForm.getCategory():" + patientForm.getCategory() + "-"
					+ patientForm.getCategoryType() + "-" + patientForm.getVisitID());

			connection = getConnection();

			String updateRasayuVisitQuery = QueryMaker.UPDATE_GeneralHospital_VISIT;

			preparedStatement = connection.prepareStatement(updateRasayuVisitQuery);

			System.out.println("patientForm.getVisitDate() :" + patientForm.getVisitDate());
			preparedStatement.setInt(1, patientForm.getVisitTypeID());
			/*
			 * if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
			 * // Converting date into db format date1 = null;
			 * 
			 * status = "nullDate";
			 * 
			 * return status; } else if (patientForm.getVisitDate().isEmpty()) { //
			 * Converting date into db format date1 = null;
			 * 
			 * status = "nullDate";
			 * 
			 * return status; } else { // Converting date into db format date1 =
			 * dateFormat.format(dateFormat1.parse(patientForm.getVisitDate())); }
			 */

			preparedStatement.setString(2, dateFormat.format(dateFormat1.parse(patientForm.getVisitDate())));
			preparedStatement.setString(3, patientForm.getDiagnosis());
			preparedStatement.setString(4, patientForm.getCategory());
			preparedStatement.setString(5, patientForm.getCategoryType());
			preparedStatement.setInt(6, patientForm.getClinicID());
			preparedStatement.setInt(7, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("GeneralHospital visit updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String updatePresentComplaints(String complaints, int visitID) {

		try {

			connection = getConnection();

			String insertPresentComplaintsQuery = QueryMaker.UPDATE_PRESENT_HISTORY;

			preparedStatement = connection.prepareStatement(insertPresentComplaintsQuery);

			preparedStatement.setString(1, complaints);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Present complaints updated successfully into PresentComplaints table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateVitalSigns(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormattee = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateVitalSignsQuery = QueryMaker.UPDATE_VITAL_SIGNS;

			preparedStatement = connection.prepareStatement(updateVitalSignsQuery);

			preparedStatement.setInt(1, patientForm.getOEPulse());
			preparedStatement.setInt(2, patientForm.getOEBPSys());
			preparedStatement.setInt(3, patientForm.getOEBPDia());
			preparedStatement.setString(4, patientForm.getOERS());
			preparedStatement.setString(5, patientForm.getOECVS());
			preparedStatement.setString(6, dateToBeFormattee.format(dateFormat.parse(patientForm.getVisitDate())));
			preparedStatement.setDouble(7, patientForm.getWeight());
			preparedStatement.setDouble(8, patientForm.getHeight());
			preparedStatement.setString(9, patientForm.getComment());
			preparedStatement.setDouble(10, patientForm.getTemperature());
			preparedStatement.setDouble(11, patientForm.getRespiration());
			preparedStatement.setString(12, patientForm.getPallor());
			preparedStatement.setString(13, patientForm.getIcterus());
			preparedStatement.setDouble(14, patientForm.getAbdominalCircumference());
			preparedStatement.setDouble(15, patientForm.getBmi());
			preparedStatement.setInt(16, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Vital details updated successfully into VitalSigns table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;

	}

	public String updateOnExaminationDetails(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateOnExaminationDetailsQuery = QueryMaker.UPDATE_ON_EXAMINATION_DETAILS;

			preparedStatement = connection.prepareStatement(updateOnExaminationDetailsQuery);

			preparedStatement.setString(1, patientForm.getOnExamination());

			if (patientForm.getOnExaminationDate() == null || patientForm.getOnExaminationDate() == "") {
				preparedStatement.setString(2, null);
			} else if (patientForm.getOnExaminationDate().isEmpty()) {
				preparedStatement.setString(2, null);
			} else {
				preparedStatement.setString(2,
						dateFormat1.format(dateFormat.parse(patientForm.getOnExaminationDate())));
			}

			preparedStatement.setString(3, patientForm.getRsPhonchi());
			preparedStatement.setString(4, patientForm.getRsCrepitation());
			preparedStatement.setString(5, patientForm.getRsClear());
			preparedStatement.setString(6, patientForm.getS1s2());
			preparedStatement.setString(7, patientForm.getMurmur());
			preparedStatement.setDouble(8, patientForm.getMurmurSys());
			preparedStatement.setDouble(9, patientForm.getMurmurDia());
			preparedStatement.setString(10, patientForm.getWnl());
			preparedStatement.setString(11, patientForm.getCnsWNL());
			preparedStatement.setString(12, patientForm.getCnsOther());
			preparedStatement.setString(13, patientForm.getAbdomen());
			preparedStatement.setString(14, patientForm.getRightHypochondriac());
			preparedStatement.setString(15, patientForm.getLeftHypochondriac());
			preparedStatement.setString(16, patientForm.getEpigastric());
			preparedStatement.setString(17, patientForm.getRightLumbar());
			preparedStatement.setString(18, patientForm.getLeftLumbar());
			preparedStatement.setString(19, patientForm.getRightIliac());
			preparedStatement.setString(20, patientForm.getLeftIliac());
			preparedStatement.setString(21, patientForm.getHypogastric());
			preparedStatement.setString(22, patientForm.getUmbilical());
			preparedStatement.setInt(23, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("On examination details added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateInvestigationDetails(String findings, int visitID, String findingType) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateInvestigationDetailsQuery = QueryMaker.UPDATE_INVESTIGATION;

			preparedStatement = connection.prepareStatement(updateInvestigationDetailsQuery);

			preparedStatement.setString(1, findings);

			preparedStatement.setString(2, findingType);
			preparedStatement.setInt(3, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Investigation details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating investigation details into Investigation table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public void updateAppointmentVisitType(int aptID, int visitTypeID) {

		try {

			connection = getConnection();

			String updateAppointmentVisitTypeQuery = QueryMaker.UPDATE_APPOINTMENT_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(updateAppointmentVisitTypeQuery);

			preparedStatement.setInt(1, visitTypeID);
			preparedStatement.setInt(2, aptID);

			preparedStatement.executeUpdate();

			System.out.println("Visit type updated successfully into Appointment");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Failed to udpated visit type into Appointment.");
		}

	}

	public JSONObject retrieveOTChargesDisbursementList(int otChargeID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveOTChargesDisbursementListQuery = QueryMaker.RETRIEVE_OT_CHARGES_DISBURSEMENT_LIST;

			preparedStatement = connection.prepareStatement(retrieveOTChargesDisbursementListQuery);

			preparedStatement.setInt(1, otChargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				object = new JSONObject();

				object.put("check", check);
				object.put("chargeType", resultSet.getString("chargeType"));
				object.put("charges", resultSet.getDouble("charges"));
				object.put("disbursementID", resultSet.getInt("id"));
				object.put("patientID", resultSet.getInt("patientID"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				object.put("chargeType", "");
				object.put("charges", 0);
				object.put("disbursementID", 0);
				object.put("patientID", 0);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public boolean verifyNewVisitType(int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyNewVisitTypeQuery = QueryMaker.VERIFY_NEW_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(verifyNewVisitTypeQuery);

			preparedStatement.setInt(1, visitTypeID);
			preparedStatement.setInt(2, 1);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public int retrieveLastNewVisitID(int patientID, int clinicID) {

		int newVisitID = 0;

		try {

			connection = getConnection();

			String retrieveLastNewVisitIDQuery = QueryMaker.RETRIEVE_LAST_NEW_VISIT;

			preparedStatement = connection.prepareStatement(retrieveLastNewVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				newVisitID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return newVisitID;
	}

	public String insertIPDPatientVisit(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_IPD_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;

			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;

			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			preparedStatement.setInt(1, patientForm.getVisitNumber());
			preparedStatement.setInt(2, patientForm.getVisitTypeID());
			preparedStatement.setString(3, date1);
			preparedStatement.setString(4, patientForm.getVisitFromTime());
			preparedStatement.setString(5, null);
			preparedStatement.setString(6, patientForm.getDiagnosis());
			preparedStatement.setString(7, patientForm.getMedicalNotes());
			preparedStatement.setString(8, ActivityStatus.ACTIVE);
			preparedStatement.setInt(9, patientForm.getPatientID());
			preparedStatement.setInt(10, patientForm.getNewVisitRef());
			preparedStatement.setInt(11, patientForm.getNextVisitDays());
			preparedStatement.setInt(12, patientForm.getAptID());
			preparedStatement.setInt(13, patientForm.getClinicID());
			preparedStatement.setString(14, patientForm.getCategory());

			preparedStatement.setString(15, patientForm.getCategoryType());
			preparedStatement.setDouble(16, patientForm.getAdvance());
			preparedStatement.setDouble(17, patientForm.getEmergencyCharges());
			preparedStatement.setInt(18, patientForm.getRoomTypeID());
			preparedStatement.setDouble(19, patientForm.getMlcCharges());
			preparedStatement.setDouble(20, patientForm.getAmbulanceDoctorCharges());

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD visit details inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertIPDCharges(String chargeType, String chargeName, double rate, double quantity, double amount,
			int visitID) {

		try {

			connection = getConnection();

			String insertIPDChargesQuery = QueryMaker.INSERT_IPD_CHARGES_DETAILS;

			preparedStatement = connection.prepareStatement(insertIPDChargesQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setString(2, chargeName);
			preparedStatement.setDouble(3, rate);
			preparedStatement.setDouble(4, quantity);
			preparedStatement.setDouble(5, amount);
			preparedStatement.setInt(6, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("IPD charges added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertOTCharges(String operation, String dateTime, String consultant, String anaesthetist,
			String otAssistant, double rate, int visitID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {

			connection = getConnection();

			String insertOTChargesQuery = QueryMaker.INSERT_OT_CHARGES;

			preparedStatement = connection.prepareStatement(insertOTChargesQuery);

			preparedStatement.setString(1, operation);
			if (dateTime == null || dateTime == "") {
				preparedStatement.setString(2, null);
			} else if (dateTime.isEmpty()) {
				preparedStatement.setString(2, null);
			} else {
				preparedStatement.setString(2, dateFormat1.format(dateFormat.parse(dateTime)));
			}
			preparedStatement.setString(3, consultant);
			preparedStatement.setString(4, anaesthetist);
			preparedStatement.setString(5, otAssistant);
			preparedStatement.setDouble(6, rate);
			preparedStatement.setInt(7, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OT charges added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveLastOTChargeID(int visitID, String operationName) {

		int OTChargeID = 0;

		try {

			connection = getConnection();

			String retrieveLastOTChargeIDQuery = QueryMaker.RETRIEVE_LAST_OT_CHARGE_ID;

			preparedStatement = connection.prepareStatement(retrieveLastOTChargeIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, operationName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				OTChargeID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OTChargeID;
	}

	public String insertChargeDisbursement(int OTChargeID, String chargeType, double charges) {

		try {

			connection = getConnection();

			String insertChargeDisbursementQuery = QueryMaker.INSERT_CHARGE_DISBURSEMENT;

			preparedStatement = connection.prepareStatement(insertChargeDisbursementQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setDouble(2, charges);
			preparedStatement.setInt(3, OTChargeID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OT Charges disbursement added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PatientForm> retrieveIPDPatientDetails(int patientID, int clinicID, int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		String profilePic = null;

		int check = 0;

		String realPath = xmlUtil.getContextPath();

		try {

			connection = getConnection();

			/*
			 * Retrieving patient's details based on patientID
			 */
			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID1;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));
				patientForm.setVisitType("New");
				patientForm.setPhone(resultSet.getString("phone"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					patientForm.setDateOfBirth(
							dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				// patientForm.setRegistrationNo(resultSet.getString("registrationNumber"));
				patientForm.setRegistrationNo(retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id")));
				patientForm.setEC(resultSet.getString("ec"));

				profilePic = resultSet.getString("profilePic");

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmailID("No");
				} else if (resultSet.getString("email").isEmpty()) {
					patientForm.setEmailID("No");
				} else {
					patientForm.setEmailID(resultSet.getString("email"));
				}
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Checking whether profilePic image exists into profilePic folder or not, if
			 * exists then setting the profile pic name into reportDBName variable of
			 * patientFOrm else copying the image from Logo folder of server to profilePic
			 * folder, if file does not exist into Logo folder too then setting default
			 * image name into reportDBName variable of patientForm
			 */
			if (profilePic == null || profilePic == "") {
				patientForm.setReportDBName("defUser.png");
			} else if (profilePic.isEmpty()) {
				patientForm.setReportDBName("defUser.png");
			} else {

				File file = new File(realPath + profilePic);

				if (file.exists()) {

					System.out.println("real path is ...." + realPath);

					// check whether file exists into profilePic folder if not then copy from Logo
					// folder
					File logoFile = new File(realPath + "profilePics/" + profilePic);
					if (logoFile.exists()) {
						patientForm.setReportDBName(profilePic);
					} else {
						FileUtils.copyFile(file, logoFile);
						patientForm.setReportDBName(profilePic);
					}
				} else {
					patientForm.setReportDBName("defUser.png");
				}

			}

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.OPD_PDF_RETRIEVE_VISIT;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setAdvance(resultSet.getDouble("advancePayment"));
				patientForm.setEmergencyCharges(resultSet.getDouble("emergencyPayment"));
				patientForm.setRoomTypeID(resultSet.getInt("roomTypeID"));
				patientForm.setMlcCharges(resultSet.getDouble("MLCCharges"));
				patientForm.setAmbulanceDoctorCharges(resultSet.getDouble("ambulanceDoctorsCharges"));
				patientForm.setDateOfDischarge(resultSet.getString("dateOfDischarge"));
				patientForm.setProcedure(resultSet.getString("procedure"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					String visitDate = dateToBeFormat.format(new Date());

					String[] dateArray = visitDate.split("-");

					patientForm.setVisitDay(dateArray[0]);
					patientForm.setVisitMonth(dateArray[1]);
					patientForm.setVisitYear(dateArray[2]);

					patientForm.setFirstVisitDate(visitDate);

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						String visitDate = dateToBeFormat.format(new Date());

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

			}

			if (check == 0) {
				String visitDate = dateToBeFormat.format(new Date());

				String[] dateArray = visitDate.split("-");

				patientForm.setVisitDay(dateArray[0]);
				patientForm.setVisitMonth(dateArray[1]);
				patientForm.setVisitYear(dateArray[2]);

				patientForm.setFirstVisitDate(visitDate);
			}

			resultSet.close();
			preparedStatement.close();

			if (check == 0) {

				/*
				 * Retrieving patient's last diagnosis value
				 */
				String retrieveLastDiagnosisQuery = QueryMaker.RETRIEVE_PATIENT_LAST_DIAGNOSIS;

				preparedStatement = connection.prepareStatement(retrieveLastDiagnosisQuery);

				preparedStatement.setInt(1, patientID);
				preparedStatement.setInt(2, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					check = 1;

					patientForm.setDiagnosis(resultSet.getString("diagnosis"));
				}

				resultSet.close();
				preparedStatement.close();
			}

			check = 0;

			/*
			 * Retrieving patient's firstVisit Date
			 */
			String retrieveFirstVisitDateQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_VISIT_DATE1;

			preparedStatement = connection.prepareStatement(retrieveFirstVisitDateQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setInt(3, 1);
			preparedStatement.setInt(4, clinicID);
			preparedStatement.setString(5, "IPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;

				patientForm.setVisitDate(dateToBeFormat.format(dateFormat.parse(resultSet.getString("visitDate"))));
			}

			if (check == 0) {
				patientForm.setVisitDate(dateToBeFormat.format(new Date()));
			}

			resultSet.close();
			preparedStatement.close();

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public String retrieveBillingTypeByVisitTypeID(int visitTypeID) {

		String billingType = "";

		try {

			connection = getConnection();

			String retrieveBillingTypeByVisitTypeIDQuery = QueryMaker.RETRIEVE_BILLING_TYPE_BY_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(retrieveBillingTypeByVisitTypeIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				billingType = resultSet.getString("billingType");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return billingType;
	}

	public String updateIPDReceipt(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updateIPDReceiptQuery = QueryMaker.UPDATE_IPD_RECEIPT;

			preparedStatement = connection.prepareStatement(updateIPDReceiptQuery);

			preparedStatement.setDouble(1, patientForm.getConsultationCharges());
			preparedStatement.setDouble(2, patientForm.getTotalAmount());
			preparedStatement.setDouble(3, patientForm.getAdjAmount());
			preparedStatement.setDouble(4, patientForm.getTaxAmount());
			preparedStatement.setDouble(5, patientForm.getNetAmount());
			preparedStatement.setDouble(6, patientForm.getAdvPayment());
			preparedStatement.setDouble(7, patientForm.getBalPayment());
			preparedStatement.setString(8, patientForm.getPaymentType());
			preparedStatement.setString(9, patientForm.getBillingType());
			preparedStatement.setDouble(10, patientForm.getTotalDiscount());
			preparedStatement.setDouble(11, patientForm.getEmergencyCharges());
			preparedStatement.setInt(12, patientForm.getUserID());
			preparedStatement.setDouble(13, patientForm.getMlcCharges());
			preparedStatement.setDouble(14, patientForm.getAmbulanceDoctorCharges());
			preparedStatement.setInt(15, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("IPD Receipt updated successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateIPDTransactions(double quantity, int transactionID, double amount, double taxAmount,
			double discount, double rate, String chargeType, String chargeName, String otDateTime) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			connection = getConnection();

			String updateIPDTransactionsQuery = QueryMaker.UPDATE_IPD_TRANSACTIONS;

			preparedStatement = connection.prepareStatement(updateIPDTransactionsQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setDouble(2, amount);
			preparedStatement.setDouble(3, discount);
			preparedStatement.setDouble(4, taxAmount);
			preparedStatement.setDouble(5, rate);
			preparedStatement.setString(6, chargeType);
			preparedStatement.setString(7, chargeName);
			if (otDateTime == null || otDateTime == "") {
				preparedStatement.setString(8, null);
			} else if (otDateTime.isEmpty()) {
				preparedStatement.setString(8, null);
			} else {
				preparedStatement.setString(8, dateFormat1.format(dateFormat.parse(otDateTime)));
			}
			preparedStatement.setInt(9, transactionID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("IPD transactions udpated successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertIPDTransactions(double quantity, int receiptID, double amount, double taxAmount,
			double discount, double rate, String chargeType, String chargeName, String otDateTime) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			connection = getConnection();

			String insertIPDTransactionsQuery = QueryMaker.INSERT_IPD_TRANSACTIONS;

			preparedStatement = connection.prepareStatement(insertIPDTransactionsQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setInt(2, receiptID);
			preparedStatement.setDouble(3, amount);
			preparedStatement.setDouble(4, discount);
			preparedStatement.setDouble(5, taxAmount);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);
			preparedStatement.setDouble(7, rate);
			preparedStatement.setString(8, chargeType);
			preparedStatement.setString(9, chargeName);
			if (otDateTime == null || otDateTime == "") {
				preparedStatement.setString(10, null);
			} else if (otDateTime.isEmpty()) {
				preparedStatement.setString(10, null);
			} else {
				preparedStatement.setString(10, dateFormat1.format(dateFormat.parse(otDateTime)));
			}

			preparedStatement.execute();

			status = "success";

			System.out.println("IPD transactions added successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public boolean verifyPaymentDataExists(int receiptID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyPaymentDataExistsQuery = QueryMaker.VERIFY_PAYMENT_DETAILS_EXISTS;

			preparedStatement = connection.prepareStatement(verifyPaymentDataExistsQuery);

			preparedStatement.setInt(1, receiptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String insertIPDReceipt(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			connection = getConnection();

			String insertIPDReceiptQuery = QueryMaker.INSERT_IPD_RECEIPT;

			preparedStatement = connection.prepareStatement(insertIPDReceiptQuery);

			preparedStatement.setString(1, patientForm.getReceiptNo());
			preparedStatement.setString(2, dateFormat1.format(dateFormat.parse(patientForm.getReceiptDate())));
			preparedStatement.setDouble(3, patientForm.getConsultationCharges());
			preparedStatement.setDouble(4, patientForm.getTotalAmount());
			preparedStatement.setDouble(5, patientForm.getAdjAmount());
			preparedStatement.setDouble(6, patientForm.getTaxAmount());
			preparedStatement.setDouble(7, patientForm.getNetAmount());
			preparedStatement.setDouble(8, patientForm.getAdvPayment());
			preparedStatement.setDouble(9, patientForm.getBalPayment());
			preparedStatement.setString(10, patientForm.getPaymentType());
			preparedStatement.setString(11, patientForm.getBillingType());
			preparedStatement.setString(12, ActivityStatus.ACTIVE);
			preparedStatement.setInt(13, patientForm.getVisitID());
			preparedStatement.setInt(14, patientForm.getUserID());
			preparedStatement.setDouble(15, patientForm.getTotalDiscount());
			preparedStatement.setDouble(16, patientForm.getEmergencyCharges());
			preparedStatement.setDouble(17, patientForm.getMlcCharges());
			preparedStatement.setDouble(18, patientForm.getAmbulanceDoctorCharges());

			preparedStatement.execute();

			status = "success";

			System.out.println("IPD Receipt added successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public HashMap<String, Object> retrieveIPDTarrifDetailsByID(int chargeID) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			connection = getConnection();

			String retrieveIPDTarrifDetailsByIDQuery = QueryMaker.RETRIEVE_IPD_CHARGE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveIPDTarrifDetailsByIDQuery);

			preparedStatement.setInt(1, chargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put("itemName", resultSet.getString("itemName"));
				map.put("roomTypeID", resultSet.getInt("roomTypeID"));
				map.put("charges", resultSet.getDouble("charges"));
				map.put("roomType", resultSet.getString("roomType"));
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

	public HashMap<String, Object> retrieveIPDConsultantDetailsByID(int chargeID) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			connection = getConnection();

			String retrieveIPDTarrifDetailsByIDQuery = QueryMaker.RETRIEVE_IPD_CONSULTANT_CHARGE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveIPDTarrifDetailsByIDQuery);

			preparedStatement.setInt(1, chargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put("doctorName", resultSet.getString("doctorName"));
				map.put("roomTypeID", resultSet.getInt("roomTypeID"));
				map.put("charges", resultSet.getDouble("charges"));
				map.put("roomType", resultSet.getString("roomType"));
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

	public HashMap<Integer, String> retrieveIPDTarrifChargesList(String status, int roomTypeID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveIPDTarrifChargesListQuery = QueryMaker.RETRIEVE_IPD_TARIFF_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveIPDTarrifChargesListQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, roomTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("itemName"));
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

	public HashMap<Integer, String> retrieveIPDConsultantChargesList(String status, int roomTypeID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveIPDConsultantChargesListQuery = QueryMaker.RETRIEVE_IPD_CONSULTANT_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveIPDConsultantChargesListQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, roomTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("doctorName"));
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

	public String updateIPDPatientVisit(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String updateIPDPatientVisitQuery = QueryMaker.UPDDATE_IPD_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;

			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;

			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			preparedStatement = connection.prepareStatement(updateIPDPatientVisitQuery);

			preparedStatement.setString(1, date1);
			preparedStatement.setString(2, patientForm.getDiagnosis());
			preparedStatement.setDouble(3, patientForm.getAdvance());
			preparedStatement.setDouble(4, patientForm.getEmergencyCharges());
			preparedStatement.setInt(5, patientForm.getRoomTypeID());
			preparedStatement.setDouble(6, patientForm.getMlcCharges());
			preparedStatement.setDouble(7, patientForm.getAmbulanceDoctorCharges());
			preparedStatement.setInt(8, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD visit details updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateIPDCharges(String chargeType, String chargeName, double rate, double quantity, double amount,
			int chargeID) {

		try {

			connection = getConnection();

			String updateIPDChargesQuery = QueryMaker.UPDATE_IPD_CHARGES_DETAILS;

			preparedStatement = connection.prepareStatement(updateIPDChargesQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setString(2, chargeName);
			preparedStatement.setDouble(3, rate);
			preparedStatement.setDouble(4, quantity);
			preparedStatement.setDouble(5, amount);
			preparedStatement.setInt(6, chargeID);

			preparedStatement.execute();

			status = "success";

			System.out.println("IPD charges updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateOTCharges(String operation, String dateTime, String consultant, String anaesthetist,
			String otAssistant, double rate, int otChargeID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {

			connection = getConnection();

			String insertOTChargesQuery = QueryMaker.UPDATE_OT_CHARGES;

			preparedStatement = connection.prepareStatement(insertOTChargesQuery);

			preparedStatement.setString(1, operation);
			if (dateTime == null || dateTime == "") {
				preparedStatement.setString(2, null);
			} else if (dateTime.isEmpty()) {
				preparedStatement.setString(2, null);
			} else {
				preparedStatement.setString(2, dateFormat1.format(dateFormat.parse(dateTime)));
			}
			preparedStatement.setString(3, consultant);
			preparedStatement.setString(4, anaesthetist);
			preparedStatement.setString(5, otAssistant);
			preparedStatement.setDouble(6, rate);
			preparedStatement.setInt(7, otChargeID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OT charges updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String deleteRow(String tableName, int deleteID) {

		try {

			connection = getConnection();

			String retrieveRoomTypeByVisitIDQuery = "DELETE FROM " + tableName + " WHERE id = ?";

			preparedStatement = connection.prepareStatement(retrieveRoomTypeByVisitIDQuery);

			preparedStatement.setInt(1, deleteID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Row delete successfully from : " + tableName);

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateGeneralHospitalPersonalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updatePersonalHistoryQuery = QueryMaker.UPDATE_PERSONAL_HISTORY1;

			preparedStatement = connection.prepareStatement(updatePersonalHistoryQuery);

			preparedStatement.setString(1, patientForm.getPersonalHistorySmoking());
			preparedStatement.setString(2, patientForm.getPersonalHistorySmokingDetails());
			preparedStatement.setString(3, patientForm.getPersonalHistoryDiet());
			preparedStatement.setString(4, patientForm.getPersonalHistoryPurisha());
			preparedStatement.setString(5, patientForm.getPersonalHistoryMootra());
			preparedStatement.setString(6, patientForm.getPersonalHistoryKshudha());
			preparedStatement.setString(7, patientForm.getPersonalHistoryNidra());
			preparedStatement.setString(8, patientForm.getPersonalHistoryOther());
			preparedStatement.setString(9, patientForm.getPersonalHistOther());
			preparedStatement.setString(10, patientForm.getMedHistDiagnosis());
			preparedStatement.setString(11, patientForm.getMedHistComment());
			preparedStatement.setString(12, patientForm.getMedHistOther());
			preparedStatement.setInt(13, patientForm.getPatientID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Personal history details udpated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating personal history details into PersonalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String insertGeneralHospitalPersonalHistory(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertPersonalHistoryQuery = QueryMaker.INSERT_PERSONAL_HISTORY1;

			preparedStatement = connection.prepareStatement(insertPersonalHistoryQuery);

			preparedStatement.setString(1, patientForm.getPersonalHistorySmoking());
			preparedStatement.setString(2, patientForm.getPersonalHistorySmokingDetails());
			preparedStatement.setString(3, patientForm.getPersonalHistoryDiet());
			preparedStatement.setString(4, patientForm.getPersonalHistoryPurisha());
			preparedStatement.setString(5, patientForm.getPersonalHistoryMootra());
			preparedStatement.setString(6, patientForm.getPersonalHistoryKshudha());
			preparedStatement.setString(7, patientForm.getPersonalHistoryNidra());
			preparedStatement.setString(8, patientForm.getPersonalHistoryOther());
			preparedStatement.setInt(9, patientForm.getPatientID());
			preparedStatement.setString(10, patientForm.getPersonalHistOther());
			preparedStatement.setString(11, patientForm.getMedHistDiagnosis());
			preparedStatement.setString(12, patientForm.getMedHistComment());
			preparedStatement.setString(13, patientForm.getMedHistOther());

			preparedStatement.execute();

			status = "success";

			System.out.println("Personal history details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting personal history details into PersonalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public List<PatientForm> retrieveCRFList(int visitID, String formName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean verifyNewOrFollowUpVisitForPatient(int visitID, int clinicID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyNewOrFollowUpVisitForPatientQuery = QueryMaker.VERIFY_NEW_OR_FOLLOW_UP_VISIT_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(verifyNewOrFollowUpVisitForPatientQuery);

			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, 0);
			preparedStatement.setInt(3, clinicID);
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public JSONObject retrieveIPDVisitTypeExceptNewVisit(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveIPDVisitTypeExceptNewVisitQuery = QueryMaker.RETRIEVE_IPD_VISIT_TYPE_EXCEPT_NEW_VISIT;

			preparedStatement = connection.prepareStatement(retrieveIPDVisitTypeExceptNewVisitQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, 0);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("visitTypeID", resultSet.getInt("id"));
				object.put("visitType", resultSet.getString("name"));

				array.add(object);

				values.put("Release", array);
				System.out.println("values11:" + values);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrorMessage", "Failed to retireve prescription details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retrieveNonIPDVisitType(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveNonIPDVisitTypeQuery = QueryMaker.RETRIEVE_NON_IPD_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(retrieveNonIPDVisitTypeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("visitTypeID", resultSet.getInt("id"));
				object.put("visitType", resultSet.getString("name"));

				array.add(object);

				values.put("Release", array);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrorMessage", "Failed to retireve non ipd visit type details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	@Override
	public List<PatientForm> retrieveGenaralClinicPatientDetailsForNewVisit(int patientID, int lastVisitID,
			int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Date date = new Date();

		System.out.println("LAST VISIT ID::" + lastVisitID);

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				// patientForm.setLastVisitID(lastOPDVisitID);
				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
			}

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				patientForm.setVisitFromTimeHH(resultSet.getString("visitTimeFrom"));
				patientForm.setVisitToTimeHH(resultSet.getString("visitTimeTo"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */
			String retreivePatientLastVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setLastVisitID(resultSet.getInt("id"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving MedicalHistory details by visitID
			 */

			String retreiveMedicalHistoryDetailsBYVisitIDQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreiveMedicalHistoryDetailsBYVisitIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setMedicalHistorycomments(resultSet.getString("comments"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */

			String retreivePatientLastVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setAdvice(resultSet.getString("advice"));

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = dateFormat.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeFormat.format(date));
				}

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Default patientList retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public List<PatientForm> retrieveGeneralClinicPatientDetails(int patientID, int visitID, int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Date date = new Date();

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				// patientForm.setLastVisitID(lastOPDVisitID);
				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
			}
			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				if (resultSet.getString("lastMenstrualPeriod") == null
						|| resultSet.getString("lastMenstrualPeriod") == "") {
					patientForm.setLastMenstrualPeriod(null);
				} else if (resultSet.getString("lastMenstrualPeriod").isEmpty()) {
					patientForm.setLastMenstrualPeriod(null);
				} else {
					patientForm.setLastMenstrualPeriod(
							dateToBeFormat.format(dateFormat.parse(resultSet.getString("lastMenstrualPeriod"))));
				}

				if (resultSet.getString("estimatedDueDate") == null || resultSet.getString("estimatedDueDate") == "") {
					patientForm.setEstimatedDueDate(null);
				} else if (resultSet.getString("estimatedDueDate").isEmpty()) {
					patientForm.setEstimatedDueDate(null);
				} else {
					patientForm.setEstimatedDueDate(
							dateToBeFormat.format(dateFormat.parse(resultSet.getString("estimatedDueDate"))));
				}

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving OnExamination details by visitID
			 */

			String retreiveOnExaminationDetailsBYVisitIDQuery = QueryMaker.RETRIEVE_ON_EXAMINATION_LIST_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreiveOnExaminationDetailsBYVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setOnExamination(resultSet.getString("description"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving MedicalHistory details by visitID
			 */

			String retreiveMedicalHistoryDetailsBYVisitIDQuery = QueryMaker.RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retreiveMedicalHistoryDetailsBYVisitIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setMedicalHistorycomments(resultSet.getString("comments"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving PresentComplaints details by visitID
			 */

			String retreivePresentComplaintsDetailsBYVisitIDQuery = QueryMaker.RETRIEVE_PRESENTCOMPLAINTS;

			preparedStatement = connection.prepareStatement(retreivePresentComplaintsDetailsBYVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setComments(resultSet.getString("comments"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's visit Details
			 */

			String retreivePatientVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setAdvice(resultSet.getString("advice"));

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = dateFormat.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeFormat.format(date));
				}

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Default patientList retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public String insertGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_General_Clinic_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			/*
			 * Checking whether appointment ID is 0 and process accordingly
			 */
			if (patientForm.getAptID() == 0) {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				String timeFrom = null;
				String timeTo = null;

				if (patientForm.getVisitFromTimeHH() == null || patientForm.getVisitFromTimeHH() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().equals("-1")) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM() == null || patientForm.getVisitFromTimeMM() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().equals("-1")) {
					timeFrom = "";
				} else {
					if (patientForm.getVisitFromTimeMM().length() == 1) {
						timeFrom = patientForm.getVisitFromTimeHH() + ":0" + patientForm.getVisitFromTimeMM();
					} else {
						timeFrom = patientForm.getVisitFromTimeHH() + ":" + patientForm.getVisitFromTimeMM();
					}
				}

				if (patientForm.getVisitToTimeHH() == null || patientForm.getVisitToTimeHH() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().equals("-1")) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM() == null || patientForm.getVisitToTimeMM() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().equals("-1")) {
					timeTo = "";
				} else {
					if (patientForm.getVisitToTimeMM().length() == 1) {
						timeTo = patientForm.getVisitToTimeHH() + ":0" + patientForm.getVisitToTimeMM();
					} else {
						timeTo = patientForm.getVisitToTimeHH() + ":" + patientForm.getVisitToTimeMM();
					}
				}

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, timeFrom);
				preparedStatement.setString(5, timeTo);
				preparedStatement.setString(6, patientForm.getCancerType());
				preparedStatement.setString(7, ActivityStatus.ACTIVE);
				preparedStatement.setInt(8, patientForm.getPatientID());
				preparedStatement.setInt(9, newVisitRef);
				preparedStatement.setInt(10, patientForm.getNextVisitDays());
				preparedStatement.setInt(11, 0);
				preparedStatement.setInt(12, patientForm.getClinicID());

				if (patientForm.getLastMenstrualPeriod() == null || patientForm.getLastMenstrualPeriod() == "") {
					preparedStatement.setString(13, null);
				} else if (patientForm.getLastMenstrualPeriod().isEmpty()) {
					preparedStatement.setString(13, null);
				} else {
					preparedStatement.setString(13,
							dateFormat.format(dateFormat1.parse(patientForm.getLastMenstrualPeriod())));
				}

				if (patientForm.getEstimatedDueDate() == null || patientForm.getEstimatedDueDate() == "") {
					preparedStatement.setString(14, null);
				} else if (patientForm.getEstimatedDueDate().isEmpty()) {
					preparedStatement.setString(14, null);
				} else {
					preparedStatement.setString(14,
							dateFormat.format(dateFormat1.parse(patientForm.getEstimatedDueDate())));
				}

				preparedStatement.setString(15, patientForm.getAdvice());

				preparedStatement.execute();

			} else {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				/*
				 * Retrieving appointment time from and time to from Appointment table based on
				 * appointmentID
				 */
				HashMap<String, String> apptTime = retrieveAppointmentTimeById(patientForm.getAptID());

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, apptTime.get("timeFrom"));
				preparedStatement.setString(5, apptTime.get("timeTo"));
				preparedStatement.setString(6, patientForm.getCancerType());
				preparedStatement.setString(7, ActivityStatus.ACTIVE);
				preparedStatement.setInt(8, patientForm.getPatientID());
				preparedStatement.setInt(9, newVisitRef);
				preparedStatement.setInt(10, patientForm.getNextVisitDays());
				preparedStatement.setInt(11, patientForm.getAptID());
				preparedStatement.setInt(12, patientForm.getClinicID());

				if (patientForm.getLastMenstrualPeriod() == null || patientForm.getLastMenstrualPeriod() == "") {
					preparedStatement.setString(13, null);
				} else if (patientForm.getLastMenstrualPeriod().isEmpty()) {
					preparedStatement.setString(13, null);
				} else {
					preparedStatement.setString(13,
							dateFormat.format(dateFormat1.parse(patientForm.getLastMenstrualPeriod())));
				}

				if (patientForm.getEstimatedDueDate() == null || patientForm.getEstimatedDueDate() == "") {
					preparedStatement.setString(14, null);
				} else if (patientForm.getEstimatedDueDate().isEmpty()) {
					preparedStatement.setString(14, null);
				} else {
					preparedStatement.setString(14,
							dateFormat.format(dateFormat1.parse(patientForm.getEstimatedDueDate())));
				}

				preparedStatement.setString(15, patientForm.getAdvice());

				preparedStatement.execute();

			}

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String updateMedicalHistoryDetails(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updateMedicalHistoryQuery = QueryMaker.UPDATE_MEDICAL_HISTORY_DETAILS1;

			preparedStatement = connection.prepareStatement(updateMedicalHistoryQuery);

			preparedStatement.setString(1, patientForm.getMedicalHistorycomments());

			preparedStatement.setInt(2, patientForm.getPatientID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Medical history details udpated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while udpating medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public String insertMedicalHistoryDetails(PatientForm patientForm) {

		try {

			connection = getConnection();

			String insertMedicalHistoryQuery = QueryMaker.INSERT_MEDICAL_HISTORY_DETAILS1;

			preparedStatement = connection.prepareStatement(insertMedicalHistoryQuery);

			preparedStatement.setString(1, patientForm.getMedicalHistorycomments());

			preparedStatement.setInt(2, patientForm.getPatientID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Medical history details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting medical history details into MedicalHistory table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public String insertPresentComplaintsDetails(String comments, int visitID) {

		try {

			connection = getConnection();

			String insertPresentComplaintsQuery = QueryMaker.INSERT_PRESENT_HISTORY1;

			preparedStatement = connection.prepareStatement(insertPresentComplaintsQuery);

			preparedStatement.setString(1, comments);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Present complaints details inserted successfully into PresentComplaints table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String insertOnExaminationDetails1(String onExamination, int visitID) {

		try {

			connection = getConnection();

			String insertOnExaminationDetailsQuery = QueryMaker.INSERT_ON_EXAMINATION_DETAILS1;

			preparedStatement = connection.prepareStatement(insertOnExaminationDetailsQuery);

			preparedStatement.setString(1, onExamination);

			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("On examination details added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int i) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String updatePatientVisitQuery = QueryMaker.UPDATE_General_Clinic_VISIT_DETAILS;
			preparedStatement = connection.prepareStatement(updatePatientVisitQuery);

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				preparedStatement.setString(1, null);

			} else if (patientForm.getVisitDate().isEmpty()) {
				preparedStatement.setString(1, null);

			} else {
				// Converting date into db format
				preparedStatement.setString(1, dateFormat.format(dateFormat1.parse(patientForm.getVisitDate())));
			}

			preparedStatement.setString(2, patientForm.getCancerType());
			preparedStatement.setInt(3, patientForm.getNextVisitDays());

			if (patientForm.getLastMenstrualPeriod() == null || patientForm.getLastMenstrualPeriod() == "") {
				preparedStatement.setString(4, null);
			} else if (patientForm.getLastMenstrualPeriod().isEmpty()) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setString(4,
						dateFormat.format(dateFormat1.parse(patientForm.getLastMenstrualPeriod())));
			}

			if (patientForm.getEstimatedDueDate() == null || patientForm.getEstimatedDueDate() == "") {
				preparedStatement.setString(5, null);
			} else if (patientForm.getEstimatedDueDate().isEmpty()) {
				preparedStatement.setString(5, null);
			} else {
				preparedStatement.setString(5, dateFormat.format(dateFormat1.parse(patientForm.getEstimatedDueDate())));
			}

			preparedStatement.setString(6, patientForm.getAdvice());

			preparedStatement.setInt(7, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public boolean verifyVisitDataExists(int visitID, String tableName) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyDataExistsQuery = "SELECT visitID FROM " + tableName + " WHERE visitID = ?";

			preparedStatement1 = connection1.prepareStatement(verifyDataExistsQuery);

			preparedStatement1.setInt(1, visitID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	@Override
	public String updatePresentComplaints1(String comments, int visitID) {

		try {

			connection = getConnection();

			String insertPresentComplaintsQuery = QueryMaker.UPDATE_PRESENT_HISTORY1;

			preparedStatement = connection.prepareStatement(insertPresentComplaintsQuery);

			preparedStatement.setString(1, comments);
			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Present complaints details updated successfully into PresentComplaints table");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateOnExaminationDetails1(String onExamination, int visitID) {

		try {

			connection = getConnection();

			String updateOnExaminationDetails1 = QueryMaker.UPDATE_ON_EXAMINATION_DETAILS1;

			preparedStatement = connection.prepareStatement(updateOnExaminationDetails1);

			preparedStatement.setString(1, onExamination);

			preparedStatement.setInt(2, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("On examination details updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PatientForm> retrieveGenaralClinicPatientDetailsForNewIPDVisit(int patientID, int lastVisitID,
			int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Date date = new Date();

		System.out.println("LAST VISIT ID::" + lastVisitID);

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
			}

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				patientForm.setVisitFromTimeHH(resultSet.getString("visitTimeFrom"));
				patientForm.setVisitToTimeHH(resultSet.getString("visitTimeTo"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */

			String retreivePatientLastVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, lastVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setAdvice(resultSet.getString("advice"));

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = dateFormat.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeFormat.format(date));
				}

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Default patientList retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public List<PatientForm> retrieveGenaralClinicPatientDetailsForIPDVisit(int patientID, int visitID, int clinicID) {

		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Date date = new Date();

		try {

			connection = getConnection();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
				patientForm.setBloodGroup(resultSet.getString("bloodGroup"));
			}

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				patientForm.setVisitFromTimeHH(resultSet.getString("visitTimeFrom"));
				patientForm.setVisitToTimeHH(resultSet.getString("visitTimeTo"));

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */

			String retreivePatientLastVisitDetailsIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitDetailsIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setAdvice(resultSet.getString("advice"));

				patientForm.setTreatmentTypeName(resultSet.getString("treatment"));

				patientForm.setNextVisitDays(resultSet.getInt("nextVisitDays"));

				if (resultSet.getString("nextVisitDate") == null || resultSet.getString("nextVisitDate") == "") {
					patientForm.setNextVisitDate(null);
				} else if (resultSet.getString("nextVisitDate").isEmpty()) {
					patientForm.setNextVisitDate(null);
				} else {
					date = dateFormat.parse(resultSet.getString("nextVisitDate"));
					patientForm.setNextVisitDate(dateToBeFormat.format(date));
				}

			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println("Default patientList retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public String insertIPDGenaralClinicPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_General_IPD_Clinic_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;

				status = "nullDate";

				return status;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			/*
			 * Checking whether appointment ID is 0 and process accordingly
			 */
			if (patientForm.getAptID() == 0) {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				String timeFrom = null;
				String timeTo = null;

				if (patientForm.getVisitFromTimeHH() == null || patientForm.getVisitFromTimeHH() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeHH().equals("-1")) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM() == null || patientForm.getVisitFromTimeMM() == "") {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().isEmpty()) {
					timeFrom = "";
				} else if (patientForm.getVisitFromTimeMM().equals("-1")) {
					timeFrom = "";
				} else {
					if (patientForm.getVisitFromTimeMM().length() == 1) {
						timeFrom = patientForm.getVisitFromTimeHH() + ":0" + patientForm.getVisitFromTimeMM();
					} else {
						timeFrom = patientForm.getVisitFromTimeHH() + ":" + patientForm.getVisitFromTimeMM();
					}
				}

				if (patientForm.getVisitToTimeHH() == null || patientForm.getVisitToTimeHH() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeHH().equals("-1")) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM() == null || patientForm.getVisitToTimeMM() == "") {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().isEmpty()) {
					timeTo = "";
				} else if (patientForm.getVisitToTimeMM().equals("-1")) {
					timeTo = "";
				} else {
					if (patientForm.getVisitToTimeMM().length() == 1) {
						timeTo = patientForm.getVisitToTimeHH() + ":0" + patientForm.getVisitToTimeMM();
					} else {
						timeTo = patientForm.getVisitToTimeHH() + ":" + patientForm.getVisitToTimeMM();
					}
				}

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, timeFrom);
				preparedStatement.setString(5, timeTo);
				preparedStatement.setString(6, patientForm.getCancerType());
				preparedStatement.setString(7, ActivityStatus.ACTIVE);
				preparedStatement.setInt(8, patientForm.getPatientID());
				preparedStatement.setInt(9, newVisitRef);
				preparedStatement.setInt(10, patientForm.getNextVisitDays());
				preparedStatement.setInt(11, 0);
				preparedStatement.setInt(12, patientForm.getClinicID());
				preparedStatement.setString(13, patientForm.getTreatmentTypeName());

				preparedStatement.setString(14, patientForm.getAdvice());

				preparedStatement.execute();

			} else {

				preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

				/*
				 * Retrieving appointment time from and time to from Appointment table based on
				 * appointmentID
				 */
				HashMap<String, String> apptTime = retrieveAppointmentTimeById(patientForm.getAptID());

				preparedStatement.setInt(1, visitNumber);
				preparedStatement.setInt(2, patientForm.getVisitTypeID());
				preparedStatement.setString(3, date1);
				preparedStatement.setString(4, apptTime.get("timeFrom"));
				preparedStatement.setString(5, apptTime.get("timeTo"));
				preparedStatement.setString(6, patientForm.getCancerType());
				preparedStatement.setString(7, ActivityStatus.ACTIVE);
				preparedStatement.setInt(8, patientForm.getPatientID());
				preparedStatement.setInt(9, newVisitRef);
				preparedStatement.setInt(10, patientForm.getNextVisitDays());
				preparedStatement.setInt(11, patientForm.getAptID());
				preparedStatement.setInt(12, patientForm.getClinicID());
				preparedStatement.setString(13, patientForm.getTreatmentTypeName());

				preparedStatement.setString(14, patientForm.getAdvice());

				preparedStatement.execute();

			}

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String updateGenaralClinicPatientIPDVisit(PatientForm patientForm, int visitNumber, int i) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String updatePatientVisitQuery = QueryMaker.UPDATE_General_IPD_Clinic_VISIT_DETAILS;
			preparedStatement = connection.prepareStatement(updatePatientVisitQuery);

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				preparedStatement.setString(1, null);

			} else if (patientForm.getVisitDate().isEmpty()) {
				preparedStatement.setString(1, null);

			} else {
				// Converting date into db format
				preparedStatement.setString(1, dateFormat.format(dateFormat1.parse(patientForm.getVisitDate())));
			}

			preparedStatement.setString(2, patientForm.getCancerType());
			preparedStatement.setInt(3, patientForm.getNextVisitDays());

			preparedStatement.setString(4, patientForm.getTreatmentTypeName());

			preparedStatement.setString(5, patientForm.getAdvice());

			preparedStatement.setInt(6, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	public int retrieveProductStockID(String productName, int clinicID) {

		int oldStockID = 0;

		try {

			connection = getConnection();

			String retrieveProductStockIDQuery = QueryMaker.RETRIEVE_OLD_STOCK_ID_BY_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retrieveProductStockIDQuery);

			preparedStatement.setString(1, productName);
			preparedStatement.setString(2, ActivityStatus.NOT_EMPTY);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				oldStockID = resultSet.getInt("id");

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return oldStockID;
	}

	public double retrieveProductNetStock(String productName, int stockID, int clinicID) {
		double netStock = 0D;

		try {
			connection = getConnection();

			String retrieveProductNetStockQuery = QueryMaker.RETRIEVE_PRODUCT_NET_STOCK1;

			preparedStatement = connection.prepareStatement(retrieveProductNetStockQuery);

			preparedStatement.setInt(1, stockID);
			preparedStatement.setString(2, productName);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				netStock = resultSet.getDouble("netStock");

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return netStock;

		} catch (Exception exception) {
			exception.printStackTrace();

			return netStock;
		}

	}

	public int retrieveNewStockID(String productName, int clinicID) {

		int oldStockID = 0;

		try {

			connection = getConnection();

			String retrieveNewStockIDQuery = QueryMaker.RETRIEVE_NEW_STOCK_ID_BY_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retrieveNewStockIDQuery);

			preparedStatement.setString(1, productName);
			preparedStatement.setString(2, ActivityStatus.NOT_EMPTY);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				oldStockID = resultSet.getInt("id");

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return oldStockID;
	}

	public String updateNetStock(double netStock, int stockID) {

		try {
			connection = getConnection();

			String updateNetStockQuery = QueryMaker.UPDATE_NET_STOCK;

			preparedStatement = connection.prepareStatement(updateNetStockQuery);

			preparedStatement.setDouble(1, netStock);
			preparedStatement.setInt(2, stockID);

			preparedStatement.executeUpdate();

			System.out.println("Successfully updated netStock into Stock table");

			status = "success";

			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating netstock into stock table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateNetStockAndStatus(double netStock, int stockID) {

		try {
			connection = getConnection();

			String updateNetStockQuery = QueryMaker.UPDATE_NET_STOCK_AND_STATUS;

			preparedStatement = connection.prepareStatement(updateNetStockQuery);

			preparedStatement.setDouble(1, netStock);
			preparedStatement.setString(2, ActivityStatus.EMPTY);
			preparedStatement.setInt(3, stockID);

			preparedStatement.executeUpdate();

			System.out.println("Successfully updated netStock & status into Stock table");

			status = "success";
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating netstock & status into stock table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateNetStockWhenRowDelete(int stockID, int quantity) {

		try {
			connection = getConnection();

			String updateNetStockQuery = QueryMaker.UPDATE_NET_STOCK_WHEN_ROW_DELETE;

			preparedStatement = connection.prepareStatement(updateNetStockQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setInt(2, stockID);

			preparedStatement.executeUpdate();

			System.out.println("Successfully updated netStock into Stock table");

			status = "success";
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating netstock into stock table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	@Override
	public double retrieveTotalNetStockByProductName(String productName, int clinicID) {
		double totalStock = 0D;

		try {

			connection1 = getConnection();

			String retrieveTotalStockQuery = QueryMaker.RETRIEVE_TOTAL_STOCK;

			preparedStatement1 = connection1.prepareStatement(retrieveTotalStockQuery);

			preparedStatement1.setString(1, productName);
			preparedStatement1.setInt(2, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				totalStock = resultSet1.getDouble("netStock");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return totalStock;
	}

	@Override
	public int retrieveVisitTypeIDByApptID(int aptID) {
		int VisitTypeID = 0;

		try {

			connection1 = getConnection();

			String retrieveVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_ID_BY_APPT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitTypeIDQuery);

			preparedStatement1.setInt(1, aptID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				VisitTypeID = resultSet1.getInt("visitTypeID");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return VisitTypeID;
	}

	@Override
	public String retrieveAppoDateByApptID(int aptID) {
		String date = null;

		try {

			connection1 = getConnection();

			String retrieveAppoDateQuery = QueryMaker.RETRIEVE_APPO_DATE_ID_BY_APPT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveAppoDateQuery);

			preparedStatement1.setInt(1, aptID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				date = resultSet1.getString("apptDate");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return date;
	}

	@Override
	public JSONObject deleteLabReport(int reportsID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {

			connection = getConnection();

			String deleteLabReportQuery = QueryMaker.DELETE_Report_DETAILS;

			preparedStatement = connection.prepareStatement(deleteLabReportQuery);

			preparedStatement.setInt(1, reportsID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting report details into table due to:::" + exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting report.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public String deleteGeneralHospitalPrescribedInvestigatios(int visitID) {

		try {
			connection = getConnection();

			String updateGeneralHospitalPrescribedInvestigatiosQuery = QueryMaker.DELETE_GeneralHospital_Prescribed_Investigations;

			preparedStatement = connection.prepareStatement(updateGeneralHospitalPrescribedInvestigatiosQuery);

			preparedStatement.setInt(1, visitID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Prescribed Investigation details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Prescribed Investigation details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public HashMap<String, HashMap<String, String>> retrieveGroupLabTestsList(String columnName, String searchTest,
			int practiceID, int groupCheck) {

		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();

		HashMap<String, String> valueMap = new HashMap<String, String>();

		String groupName = "";

		try {

			connection = getConnection();
			String retrieveGroupLabTestsListQuery = "";
			if (groupCheck == 1) {
				System.out.println("test");
				retrieveGroupLabTestsListQuery = "SELECT CONCAT_WS('$', test, COALESCE(" + columnName + ",'null')"
						+ ", CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'),"
						+ " COALESCE(subgroup,'null')) AS groupValues, groupName, test FROM PVLabTests "
						+ "WHERE (groupName IS NOT null OR groupName <> '') AND (test LIKE ? OR groupName LIKE ?) AND practiceID = ? AND activityStatus = ?";

			} else {
				System.out.println("test 1");
				retrieveGroupLabTestsListQuery = "SELECT CONCAT_WS('$', test, COALESCE(" + columnName + ",'null')"
						+ ", CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'),"
						+ " COALESCE(subgroup,'null')) AS groupValues, groupName, test FROM PVLabTests "
						+ "WHERE (test LIKE ? OR groupName LIKE ?) AND practiceID = ? AND activityStatus = ?";

			}

			preparedStatement = connection.prepareStatement(retrieveGroupLabTestsListQuery);

			if (searchTest.matches("\\s+")) {
				searchTest = searchTest.replace("\\s+", "%");
			}

			preparedStatement.setString(1, "%" + searchTest + "%");

			preparedStatement.setString(2, "%" + searchTest + "%");

			preparedStatement.setInt(3, practiceID);

			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			System.out.println("preparedStatement 111 ::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				groupName = resultSet.getString("groupName");

				valueMap.put(resultSet.getString("groupValues"), resultSet.getString("test"));

				System.out.println("valueMap inside....: " + valueMap);

				System.out.println("groupName inside.......: " + groupName);

				map.put(groupName, valueMap);
			}

			System.out.println("valueMap outside: " + valueMap);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public HashMap<String, String> getlabTestValueList(String columnName) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveGroupLabTestsListQuery = "SELECT GROUP_CONCAT(CONCAT_WS('$', test, " + columnName
					+ ", CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'),"
					+ " COALESCE(subgroup,'null'), 0) SEPARATOR '*') AS groupValues, test FROM PVLabTests "
					+ "WHERE (groupName IS NOT null OR groupName <> '') AND activityStatus = ? GROUP BY groupName";

			preparedStatement = connection.prepareStatement(retrieveGroupLabTestsListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getString("groupValues"), resultSet.getString("test"));
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

	@Override
	public List<PatientForm> retrievePVTests(String normalRangeColName) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {
			connection = getConnection();

			String retrievePVTestsQuery = "SELECT id, panel, test, " + normalRangeColName
					+ ", rate, groupName, groupRate, subgroup FROM PVLabTests WHERE activityStatus = ?";

			preparedStatement = connection.prepareStatement(retrievePVTestsQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {
				form = new PatientForm();

				form.setSrNo(srNo);
				form.setCBCPanel(resultSet.getString("panel"));
				form.setCBCProfileTest(resultSet.getString("test"));
				form.setRate(resultSet.getDouble("rate"));
				form.setCBCProfileNormalValue(resultSet.getString(normalRangeColName));
				form.setGroupName(resultSet.getString("groupName"));
				if (resultSet.getString("groupName") == null || resultSet.getString("groupName") == "") {
					form.setGroupCheck(0);
				} else if (resultSet.getString("groupName").isEmpty()) {
					form.setGroupCheck(0);
				} else {
					form.setGroupCheck(1);
				}
				form.setGroupRate(resultSet.getDouble("groupRate"));
				form.setSubGroup(resultSet.getString("subgroup"));

				srNo++;

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<String> getLabDefaultValueList(String testName) {
		List<String> list = new ArrayList<String>();
		PatientForm form = null;

		try {

			connection = getConnection();

			String getLabDefaultValueListQuery = QueryMaker.RETRIEVE_Lab_Default_Value_List;

			preparedStatement1 = connection.prepareStatement(getLabDefaultValueListQuery);

			preparedStatement1.setString(1, testName);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				String finalValues = resultSet1.getString("defaultValue");

				list.add(finalValues);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving default value List from PVLabTestDefaultValues table due to:::"
							+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PatientForm> retrieveLabPatientDetails(int patientID, int lastOPDVisitID, int clinicID, int aptID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveRecieptDetailsByVisitIDQuery = QueryMaker.RETRIEVE_RECEIPT_DETAILS_BY_VISITID;

			preparedStatement = connection.prepareStatement(retrieveRecieptDetailsByVisitIDQuery);

			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();
				patientForm.setConsultationCharges(resultSet.getDouble("consultationCharges"));
			}

			resultSet.close();
			preparedStatement.close();

			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				Date date = new Date();
				SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
				String currentTime = format1.format(date);

				patientForm.setVisitFromTime(currentTime + ":00");

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setMobile(resultSet.getString("mobile"));
				patientForm.setAddress(resultSet.getString("address"));

				/*
				 * Retrieving dateOfBirth and converting it into dd-MM-yyyy only if dateOfBirth
				 * is not
				 */
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					patientForm.setDateOfBirth("");
				} else {
					if (resultSet.getString("dateOfBirth").isEmpty()) {
						patientForm.setDateOfBirth("");
					} else {

						patientForm.setDateOfBirth(
								dateToBeFormat.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));

					}
				}

				patientForm.setOccupation(resultSet.getString("occupation"));
				patientForm.setMedicalRegNo(resultSet.getString("practiceRegNumber"));

				String clinicRegNo = retrieveClinicRegNoByClinicID(clinicID, resultSet.getInt("id"));

				if (clinicRegNo == null || clinicRegNo == "") {
					patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
				} else {
					if (clinicRegNo.isEmpty()) {
						patientForm.setRegistrationNo(retrieveClinicRegNoByPatientID(resultSet.getInt("id")));
					} else {
						patientForm.setRegistrationNo(clinicRegNo);
					}
				}
				patientForm.setEC(resultSet.getString("ec"));
				patientForm.setEmailID(resultSet.getString("email"));

				if (resultSet.getString("email") == null || resultSet.getString("email") == "") {
					patientForm.setEmEmailID("No");
				} else {
					if (resultSet.getString("email").isEmpty()) {
						patientForm.setEmEmailID("No");
					} else {
						patientForm.setEmEmailID("Yes");
					}
				}

				patientForm.setFirstVisitDate(dateToBeFormat.format(new Date()));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * retrieving appointment timeFrom, timeTo, and appt date
			 */
			String retrieveAppointmentDetailsQuery = QueryMaker.RETRIEVE_APPOINTMENT_TIME_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveAppointmentDetailsQuery);

			preparedStatement.setInt(1, aptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				/*
				 * Declaring variable for time to and from
				 */
				String[] timeFromArray = null;

				String timeFromHH = null;

				String[] timeFromArray1 = null;

				String timeFromMM = null;

				String timeFromAMPM = null;

				String[] timeToArray = null;

				String timeToHH = null;

				String[] timeToArray1 = null;

				String timeToMM = null;

				String timeToAMPM = null;

				/*
				 * Splitting visit Time from and time to
				 */
				if (resultSet.getString("apptTimeFrom") == null || resultSet.getString("apptTimeFrom") == "") {

					timeFromArray = null;

					timeFromHH = "-1";

					timeFromArray1 = null;

					timeFromMM = "-1";

					timeFromAMPM = "-1";

				} else if (resultSet.getString("apptTimeFrom").isEmpty()) {

					timeFromArray = null;

					timeFromHH = "-1";

					timeFromArray1 = null;

					timeFromMM = "-1";

					timeFromAMPM = "-1";

				} else {
					timeFromArray = resultSet.getString("apptTimeFrom").split(":");

					timeFromHH = timeFromArray[0];

					timeFromMM = timeFromArray[1];

				}

				if (resultSet.getString("apptTimeTo") == null || resultSet.getString("apptTimeTo") == "") {

					timeToArray = null;

					timeToHH = "-1";

					timeToArray1 = null;

					timeToMM = "-1";

					timeToAMPM = "-1";

				} else if (resultSet.getString("apptTimeTo").isEmpty()) {

					timeToArray = null;

					timeToHH = "-1";

					timeToArray1 = null;

					timeToMM = "-1";

					timeToAMPM = "-1";

				} else {
					timeToArray = resultSet.getString("apptTimeTo").split(":");

					timeToHH = timeToArray[0];

					timeToMM = timeToArray[1];
				}

				patientForm.setVisitFromTimeHH(timeFromHH);
				patientForm.setVisitFromTimeMM(timeFromMM);
				patientForm.setVisitFromTimeAMPM(timeFromAMPM);
				patientForm.setVisitToTimeHH(timeToHH);
				patientForm.setVisitToTimeMM(timeToMM);
				patientForm.setVisitToTimeAMPM(timeToAMPM);

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving visit details by visitID
			 */
			String retrieveVisitDetailQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveVisitDetailQuery);

			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setCancerType(resultSet.getString("diagnosis"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setAptID(resultSet.getInt("apptID"));
				patientForm.setVisitID(resultSet.getInt("id"));
				patientForm.setSampleID(resultSet.getString("sampleID"));
				patientForm.setMdDoctorID(resultSet.getInt("mdDoctorID"));
				patientForm.setReferredBy(resultSet.getString("referredBy"));
				patientForm.setMedicalNotes(resultSet.getString("visitNote"));
				patientForm.setTemplate(resultSet.getString("visitNote"));
				patientForm.setTotalRate(resultSet.getDouble("totalRate"));
				patientForm.setClinicianID(resultSet.getInt("clinicianID"));
				patientForm.setReportID(resultSet.getInt("templateID"));

				if (resultSet.getString("estimatedDueDate") == null || resultSet.getString("estimatedDueDate") == "") {
					patientForm.setEstimatedDueDate("");
					patientForm.setLastMenstrualPeriod("");
					patientForm.setNameOfGuardian("");
					patientForm.setWeekOfPregnancy(0);
				} else if (resultSet.getString("estimatedDueDate").isEmpty()) {
					patientForm.setEstimatedDueDate("");
					patientForm.setLastMenstrualPeriod("");
					patientForm.setNameOfGuardian("");
					patientForm.setWeekOfPregnancy(0);
				} else {
					String test = dateToBeFormat.format(resultSet.getDate("estimatedDueDate"));
					System.out.println("test :::::" + test);
					patientForm.setEstimatedDueDate(test);
					patientForm.setLastMenstrualPeriod(dateToBeFormat.format(resultSet.getDate("lastMenstrualPeriod")));
					patientForm.setNameOfGuardian(resultSet.getString("nameOfGuardian"));
					patientForm.setWeekOfPregnancy(resultSet.getInt("weekOfPregnancy"));
				}

				if (resultSet.getString("visitDate") == null || resultSet.getString("visitDate") == "") {

					patientForm.setVisitDay("");
					patientForm.setVisitMonth("");
					patientForm.setVisitYear("");

					patientForm.setFirstVisitDate("");

				} else {

					if (resultSet.getString("visitDate").isEmpty()) {

						patientForm.setVisitDay("");
						patientForm.setVisitMonth("");
						patientForm.setVisitYear("");

						patientForm.setFirstVisitDate("");

					} else {

						String visitDate = dateToBeFormat.format(resultSet.getDate("visitDate"));

						String[] dateArray = visitDate.split("-");

						patientForm.setVisitDay(dateArray[0]);
						patientForm.setVisitMonth(dateArray[1]);
						patientForm.setVisitYear(dateArray[2]);

						patientForm.setFirstVisitDate(visitDate);

					}

				}

				/*
				 * Declaring variable for time to and from
				 */
				String[] timeFromArray = null;

				String timeFromHH = null;

				String[] timeFromArray1 = null;

				String timeFromMM = null;

				String timeFromAMPM = null;

				String[] timeToArray = null;

				String timeToHH = null;

				String[] timeToArray1 = null;

				String timeToMM = null;

				String timeToAMPM = null;

				/*
				 * Splitting visit Time from and time to
				 */
				if (resultSet.getString("visitTimeFrom") == null || resultSet.getString("visitTimeFrom") == "") {

					timeFromArray = null;

					timeFromHH = "-1";

					timeFromArray1 = null;

					timeFromMM = "-1";

					timeFromAMPM = "-1";

				} else if (resultSet.getString("visitTimeFrom").isEmpty()) {

					timeFromArray = null;

					timeFromHH = "-1";

					timeFromArray1 = null;

					timeFromMM = "-1";

					timeFromAMPM = "-1";

				} else {
					timeFromArray = resultSet.getString("visitTimeFrom").split(":");

					timeFromHH = timeFromArray[0];

					timeFromMM = timeFromArray[1];

				}

				if (resultSet.getString("visitTimeTo") == null || resultSet.getString("visitTimeTo") == "") {

					timeToArray = null;

					timeToHH = "-1";

					timeToArray1 = null;

					timeToMM = "-1";

					timeToAMPM = "-1";

				} else if (resultSet.getString("visitTimeTo").isEmpty()) {

					timeToArray = null;

					timeToHH = "-1";

					timeToArray1 = null;

					timeToMM = "-1";

					timeToAMPM = "-1";

				} else {
					timeToArray = resultSet.getString("visitTimeTo").split(":");

					timeToHH = timeToArray[0];

					timeToMM = timeToArray[1];
				}

				patientForm.setVisitFromTimeHH(timeFromHH);
				patientForm.setVisitFromTimeMM(timeFromMM);
				patientForm.setVisitFromTimeAMPM(timeFromAMPM);
				patientForm.setVisitToTimeHH(timeToHH);
				patientForm.setVisitToTimeMM(timeToMM);
				patientForm.setVisitToTimeAMPM(timeToAMPM);
				patientForm.setVisitFromTime(resultSet.getString("visitTimeFrom"));
				patientForm.setIsReportDispatched(resultSet.getBoolean("isReportDispatched"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving patient's last visitID
			 */
			String retreivePatientLastVisitIDQuery = QueryMaker.RETRIEVE_LAST_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreivePatientLastVisitIDQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, "OPD");
			preparedStatement.setInt(4, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setLastVisitID(resultSet.getInt("id"));
			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving USG PCPNDT details visitID
			 */
			String retreivePCPNDTQuery = QueryMaker.RETRIEVE_PCPNDT_DATA_VISIT_ID;

			preparedStatement = connection.prepareStatement(retreivePCPNDTQuery);

			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm.setNumberOfSons(resultSet.getInt("numberOfSons"));
				patientForm.setSonAge(resultSet.getString("ageOfSons"));
				patientForm.setNumberOfDaughters(resultSet.getInt("numberOfDaughters"));
				patientForm.setDaughterAge(resultSet.getString("ageOfDaughters"));
			}

			list.add(patientForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving patient list based on patientID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public List<PatientForm> retrieveBillDetail(int visitID, int visitTypeID, int clinicID, String clinicSuffix) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = new PatientForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection = getConnection();

			String retrieveVisitBillingByVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_IDNew;
			String retrieveVisitDateByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_DATE_BY_VISIT_ID1;
			String retrieveBillDetailQuery = QueryMaker.RETRIEVE_BILLING_DETAIL1;
			String retrievePaymentDetails = QueryMaker.RETRIEVE_PAYMENT_DETAILS;

			/*
			 * Retrieving total service charge
			 */
			preparedStatement1 = connection.prepareStatement(retrieveVisitBillingByVisitTypeIDQuery);

			preparedStatement1.setInt(1, visitTypeID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				form.setBillingType(resultSet1.getString("billingType"));
				form.setCharges(resultSet1.getDouble("consultationCharges"));
				// form.setNetAmount(resultSet1.getDouble("consultationCharges"));
				form.setReceiptDate(dateFormat.format(new Date()));
				form.setReceiptNo(retrieveReceiptNo(clinicID, clinicSuffix));
			}
			resultSet1.close();
			preparedStatement1.close();

			connection = getConnection();

			preparedStatement = connection.prepareStatement(retrieveVisitDateByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form.setReceiptDate(resultSet.getString("visitDate") + " " + timeFormat.format(new Date()));
			}
			resultSet.close();
			preparedStatement.close();

			preparedStatement = connection.prepareStatement(retrieveBillDetailQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			System.out.println("preparedStatement :: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				form.setReceiptNo(resultSet.getString("receiptNo"));
				form.setTotalAmount(resultSet.getDouble("totalAmount"));
				form.setAdjAmount(resultSet.getDouble("adjAmount"));
				form.setNetAmount(resultSet.getDouble("netAmount"));
				form.setAdvPayment(resultSet.getDouble("advPayment"));
				form.setBalPayment(resultSet.getDouble("balPayment"));
				form.setTotalDiscount(resultSet.getDouble("totalDiscount"));
				form.setReceiptID(resultSet.getInt("id"));
				form.setPaymentType(resultSet.getString("paymentType"));
				form.setConsultationCharges(resultSet.getDouble("consultationCharges"));
			}
			resultSet.close();
			preparedStatement.close();

			/*
			 * check whether receiptID is 0, if so then calculate total amount net amount by
			 * retrieving test details from PVLabTests table
			 */
			if (form.getReceiptID() == 0) {

				double totalAmount = 0D;

				// Retrieving single test total amount by adding rate of all test with isGroup
				// as 0 for the specific visitID
				String retrieveLabInvestigationQuery = QueryMaker.RETRIEVE_SINGLE_LAB_TEST_TOTAL_RATE_BY_VISIT_ID;

				preparedStatement = connection.prepareStatement(retrieveLabInvestigationQuery);

				preparedStatement.setInt(1, visitID);
				preparedStatement.setInt(2, 0);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					totalAmount = resultSet.getDouble("total");

				}

				resultSet.close();
				preparedStatement.close();

				// Retrieving group test total amount by adding rate of all test with isGroup
				// as 0 for the specific visitID
				String retrieveLabInvestigationQuery1 = QueryMaker.RETRIEVE_GROUP_LAB_TEST_TOTAL_RATE_BY_VISIT_ID;

				preparedStatement = connection.prepareStatement(retrieveLabInvestigationQuery1);

				preparedStatement.setInt(1, visitID);
				preparedStatement.setInt(2, 1);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					totalAmount += resultSet.getDouble("groupRate");

				}

				resultSet.close();
				preparedStatement.close();

				form.setTotalAmount(totalAmount);
				form.setNetAmount(totalAmount);
				form.setBalPayment(totalAmount);

			}

			preparedStatement = connection.prepareStatement(retrievePaymentDetails);

			preparedStatement.setInt(1, form.getReceiptID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form.setChequeAmt(resultSet.getDouble("chequeAmount"));
				form.setChequeBankBranch(resultSet.getString("bankBranch"));
				form.setChequeBankName(resultSet.getString("bankName"));
				if (resultSet.getString("chequeDate") == null || resultSet.getString("chequeDate") == "") {
					form.setChequeDate("");
				} else if (resultSet.getString("chequeDate").isEmpty()) {
					form.setChequeDate("");
				} else {
					form.setChequeDate(dateFormat2.format(resultSet.getDate("chequeDate")));
				}
				form.setChequeIssuedBy(resultSet.getString("chequeIssueBy"));
				form.setChequeNo(resultSet.getString("chequeNumber"));
				form.setCashPaid(resultSet.getDouble("cashPaid"));
				form.setCashToReturn(resultSet.getDouble("cashToReturn"));
				form.setCardMobileNo(resultSet.getString("cardNumber"));
				form.setCardAmount(resultSet.getDouble("cardAmount"));
				form.setCMobileNo(resultSet.getString("mobile"));
				form.setOtherType(resultSet.getString("otherMode"));
				form.setOtherAmount(resultSet.getDouble("otherAmount"));
			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);

		}

		return list;

	}

	@Override
	public List<PatientForm> retrieveTransactionListByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveTransactionDetailsByVisitIDQuery = QueryMaker.RETRIEVE_TRANSACTION_DETAILS1;

			preparedStatement = connection.prepareStatement(retrieveTransactionDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {

				form = new PatientForm();

				form.setSrNo(srNo);
				// form.setCBCPanel(resultSet.g);
				form.setCBCProfileTest(resultSet.getString("test"));
				form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
				form.setRate(resultSet.getDouble("amount"));

				list.add(form);

				srNo++;

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PatientForm> retrieveTransactionDetailsByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveTransactionDetailsByVisitIDQuery = QueryMaker.RETRIEVE_TRANSACTION_DETAILS1;

			preparedStatement = connection.prepareStatement(retrieveTransactionDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {

				form = new PatientForm();

				form.setSrNo(srNo);
				// form.setCBCPanel(resultSet.g);
				form.setCBCProfileTest(resultSet.getString("test"));
				form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
				form.setRate(resultSet.getDouble("amount"));
				form.setGroupCheck(resultSet.getInt("isGroup"));

				list.add(form);

				srNo++;

			}

			/*
			 * check whether data available in transaction table or not by checking srNo is
			 * equal to 1 or not , if yes then data not available in transaction table which
			 * means no receipt yet added for the current visitID, and so retrieve the test
			 * details for bill from LabInvestigation table
			 */
			if (srNo == 1) {

				JDBCHelper.closeResultSet(resultSet);
				JDBCHelper.closeStatement(preparedStatement);

				String retrieveLabInvestigationByVisitIDQuery = QueryMaker.RETRIEVE_LAB_INVESTIGATION_BY_VISIT_ID_AND_IS_GROUP;

				preparedStatement = connection.prepareStatement(retrieveLabInvestigationByVisitIDQuery);

				preparedStatement.setInt(1, visitID);
				preparedStatement.setInt(2, 0);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					form = new PatientForm();

					form.setSrNo(srNo);
					// form.setCBCPanel(resultSet.g);
					form.setCBCProfileTest(resultSet.getString("test"));
					form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
					form.setRate(resultSet.getDouble("rate"));
					form.setGroupCheck(0);

					list.add(form);

					srNo++;

				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PatientForm> retrieveTransactionDetailsForGroupTestByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveTransactionDetailsByVisitIDQuery = QueryMaker.RETRIEVE_TRANSACTION_DETAILS1;

			preparedStatement = connection.prepareStatement(retrieveTransactionDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {

				form = new PatientForm();

				form.setSrNo(srNo);
				// form.setCBCPanel(resultSet.g);
				form.setCBCProfileTest(resultSet.getString("test"));
				form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
				form.setRate(resultSet.getDouble("amount"));

				list.add(form);

				srNo++;

			}

			/*
			 * check whether data available in transaction table or not by checking srNo is
			 * equal to 1 or not , if yes then data not available in transaction table which
			 * means no receipt yet added for the current visitID, and so retrieve the test
			 * details for bill from LabInvestigation table
			 */
			if (srNo == 1) {

				JDBCHelper.closeResultSet(resultSet);
				JDBCHelper.closeStatement(preparedStatement);

				String retrieveLabInvestigationByVisitIDQuery = QueryMaker.RETRIEVE_LAB_INVESTIGATION_BY_VISIT_ID_AND_IS_GROUP1;

				preparedStatement = connection.prepareStatement(retrieveLabInvestigationByVisitIDQuery);

				preparedStatement.setInt(1, visitID);
				preparedStatement.setInt(2, 1);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					form = new PatientForm();

					form.setSrNo(srNo);
					// form.setCBCPanel(resultSet.g);
					form.setCBCProfileTest(resultSet.getString("groupName"));
					form.setRate(resultSet.getDouble("groupRate"));

					list.add(form);

					srNo++;

				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public String retrieveLabTestByVisitID(int visitID) {

		String investigationString = "";

		try {
			connection = getConnection();

			String retrieveLabTestByVisitIDQuery = QueryMaker.RETRIEVE_LAB_INVESTIGATIONNew;

			preparedStatement = connection.prepareStatement(retrieveLabTestByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				investigationString = resultSet.getString("test");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return investigationString;
	}

	@Override
	public String insertLabPatientVisit(PatientForm patientForm, int visitNumber, int newVisitRef) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.INSERT_LAB_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			/*
			 * Checking whether appointment ID is 0 and process accordingly
			 */
			/*
			 * if (patientForm.getAptID() == 0) {
			 * 
			 * preparedStatement = connection.prepareStatement(insertPatientVisitQuery);
			 * 
			 * preparedStatement.setInt(1, visitNumber); preparedStatement.setInt(2,
			 * patientForm.getVisitTypeID()); preparedStatement.setString(3, date1);
			 * preparedStatement.setString(4, patientForm.getVisitFromTime());
			 * preparedStatement.setString(5, patientForm.getVisitToTime());
			 * preparedStatement.setString(6, patientForm.getDiagnosis());
			 * preparedStatement.setString(7, patientForm.getMedicalNotes());
			 * preparedStatement.setString(8, ActivityStatus.ACTIVE);
			 * preparedStatement.setInt(9, patientForm.getPatientID());
			 * preparedStatement.setInt(10, newVisitRef); preparedStatement.setInt(11,
			 * patientForm.getNextVisitDays()); preparedStatement.setInt(12, 0);
			 * preparedStatement.setInt(13, patientForm.getClinicID());
			 * preparedStatement.setString(14, patientForm.getReferredBy());
			 * preparedStatement.setString(15, patientForm.getSampleID());
			 * preparedStatement.setInt(16, patientForm.getMdDoctorID());
			 * preparedStatement.setDouble(17, patientForm.getTotalRate());
			 * preparedStatement.setBoolean(18, patientForm.getIsReportDispatched());
			 * 
			 * preparedStatement.execute();
			 * 
			 * } else {
			 * 
			 * preparedStatement = connection.prepareStatement(insertPatientVisitQuery);
			 * 
			 * preparedStatement.setInt(1, visitNumber); preparedStatement.setInt(2,
			 * patientForm.getVisitTypeID()); preparedStatement.setString(3, date1);
			 * preparedStatement.setString(4, patientForm.getVisitFromTime());
			 * preparedStatement.setString(5, patientForm.getVisitToTime());
			 * preparedStatement.setString(6, patientForm.getDiagnosis());
			 * preparedStatement.setString(7, patientForm.getMedicalNotes());
			 * preparedStatement.setString(8, ActivityStatus.ACTIVE);
			 * preparedStatement.setInt(9, patientForm.getPatientID());
			 * preparedStatement.setInt(10, newVisitRef); preparedStatement.setInt(11,
			 * patientForm.getNextVisitDays()); preparedStatement.setInt(12,
			 * patientForm.getAptID()); preparedStatement.setInt(13,
			 * patientForm.getClinicID()); preparedStatement.setString(14,
			 * patientForm.getReferredBy()); preparedStatement.setString(15,
			 * patientForm.getSampleID()); preparedStatement.setInt(16,
			 * patientForm.getMdDoctorID()); preparedStatement.setDouble(17,
			 * patientForm.getTotalRate()); preparedStatement.setBoolean(18,
			 * patientForm.getIsReportDispatched());
			 * 
			 * preparedStatement.execute();
			 * 
			 * }
			 */

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setInt(2, patientForm.getVisitTypeID());
			preparedStatement.setString(3, date1);
			preparedStatement.setString(4, patientForm.getVisitFromTime());
			preparedStatement.setString(5, patientForm.getVisitToTime());
			preparedStatement.setString(6, patientForm.getDiagnosis());
			preparedStatement.setString(7, patientForm.getMedicalNotes());
			preparedStatement.setString(8, ActivityStatus.ACTIVE);
			preparedStatement.setInt(9, patientForm.getPatientID());
			preparedStatement.setInt(10, newVisitRef);
			preparedStatement.setInt(11, patientForm.getNextVisitDays());
			preparedStatement.setInt(12, patientForm.getAptID() == 0 ? 0 : patientForm.getAptID());
			preparedStatement.setInt(13, patientForm.getClinicID());
			preparedStatement.setString(14, patientForm.getReferredBy());
			preparedStatement.setString(15, patientForm.getSampleID());
			preparedStatement.setInt(16, patientForm.getMdDoctorID());
			preparedStatement.setDouble(17, patientForm.getTotalRate());
			if (patientForm.getIsReportDispatched() == null) {
				preparedStatement.setBoolean(18, false);
			} else {
				preparedStatement.setBoolean(18, patientForm.getIsReportDispatched());
			}

			preparedStatement.execute();

			status = "success";
			System.out.println("visit details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String insertLabInvestigationDetails(String test, String value, String normalRange, int visitID,
			String investigationDate, double rate, int groupCheck, double groupRate, String groupName, String subGroup,
			String testType) {

		boolean isNumber = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertLabInvestigationDetailsQuery = QueryMaker.INSERT_LAB_INVESTIGATION1;

			preparedStatement = connection.prepareStatement(insertLabInvestigationDetailsQuery);

			// preparedStatement.setString(1, panel);
			preparedStatement.setString(1, test);

			String val = "";

			if (value == null) {
				val = "";
			} else if (value.isEmpty()) {
				val = "";
			} else if (value.equals("null")) {
				val = "";
			} else {
				val = value;
			}

			/*
			 * Checking whether value is quantitative or qualitative by checking whether
			 * value is numeric or string, if numeric then it is quantitative else
			 * qualitative
			 */
			isNumber = isNumeric(val);

			if (isNumber) {

				preparedStatement.setString(2, "");
				preparedStatement.setDouble(3, Double.parseDouble(val));

			} else {

				preparedStatement.setString(2, val);
				preparedStatement.setDouble(3, 0D);

			}

			preparedStatement.setString(4, normalRange);
			preparedStatement.setInt(5, visitID);

			if (investigationDate == null || investigationDate == "") {
				preparedStatement.setString(6, null);
			} else {
				if (investigationDate.isEmpty()) {
					preparedStatement.setString(6, null);
				} else if (investigationDate.equals("null")) {
					preparedStatement.setString(6, null);
				} else {
					preparedStatement.setString(6, dateFormat1.format(dateFormat.parse(investigationDate)));
				}
			}

			preparedStatement.setDouble(7, rate);
			preparedStatement.setInt(8, groupCheck);
			preparedStatement.setDouble(9, groupRate);
			preparedStatement.setString(10, groupName);
			preparedStatement.setString(11, subGroup);
			preparedStatement.setString(12, testType);

			preparedStatement.execute();

			System.out.println("Lab investigation details added successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting lab investigation detials into LabInvestigation table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public String updatetReceiptDetails(PatientForm patientForm) {

		try {

			connection = getConnection();

			String updatetReceiptDetailsQuery = QueryMaker.UPDATE_LAB_BILL_RECEIPT;

			preparedStatement = connection.prepareStatement(updatetReceiptDetailsQuery);

			preparedStatement.setDouble(1, patientForm.getTotalAmount());
			preparedStatement.setDouble(2, patientForm.getNetAmount());
			preparedStatement.setDouble(3, patientForm.getAdvPayment());
			preparedStatement.setDouble(4, patientForm.getBalPayment());
			preparedStatement.setString(5, patientForm.getPaymentType());
			preparedStatement.setString(6, patientForm.getBillingType());
			preparedStatement.setInt(7, patientForm.getUserID());
			preparedStatement.setDouble(8, patientForm.getTotalDiscount());
			preparedStatement.setDouble(9, patientForm.getConsultationCharges()); // Change
			preparedStatement.setInt(10, patientForm.getVisitID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Bill receipt udpated successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public boolean verifyOtherDataExists(int ID, String tableName, String refColumnName) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyOtherDataExistsQuery = "SELECT id FROM " + tableName + " WHERE " + refColumnName + " = ?";

			preparedStatement1 = connection1.prepareStatement(verifyOtherDataExistsQuery);

			preparedStatement1.setInt(1, ID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				check = true;
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	@Override
	public String insertTransactionDetails(double quantity, int receiptID, double amount, double discount,
			double taxAmount, String activityStatus, int prescriptionID, int productID, String test, double rate) {

		try {

			connection = getConnection();

			String insertTransactionDetailsQuery = QueryMaker.INSERT_BILL_TRANSACTION_DETAILS;

			preparedStatement = connection.prepareStatement(insertTransactionDetailsQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setInt(2, receiptID);
			preparedStatement.setInt(3, prescriptionID);
			preparedStatement.setDouble(4, amount);
			preparedStatement.setDouble(5, taxAmount);
			preparedStatement.setString(6, activityStatus);
			preparedStatement.setInt(7, productID);
			preparedStatement.setDouble(8, rate);
			preparedStatement.setDouble(9, discount);
			preparedStatement.setString(10, test);

			preparedStatement.execute();

			status = "success";

			System.out.println("Transaction details added successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String insertReceiptDetails(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			connection = getConnection();

			String insertReceiptDetailsQuery = QueryMaker.INSERT_LAB_BILL_RECEIPT;

			preparedStatement = connection.prepareStatement(insertReceiptDetailsQuery);

			preparedStatement.setString(1, patientForm.getReceiptNo());
			preparedStatement.setString(2, dateFormat1.format(dateFormat.parse(patientForm.getReceiptDate())));
			preparedStatement.setDouble(3, patientForm.getTotalAmount());
			preparedStatement.setDouble(4, patientForm.getNetAmount());
			preparedStatement.setDouble(5, patientForm.getAdvPayment());
			preparedStatement.setDouble(6, patientForm.getBalPayment());
			preparedStatement.setString(7, patientForm.getPaymentType());
			preparedStatement.setString(8, patientForm.getBillingType());
			preparedStatement.setString(9, ActivityStatus.ACTIVE);
			preparedStatement.setInt(10, patientForm.getVisitID());
			preparedStatement.setInt(11, patientForm.getUserID());
			preparedStatement.setDouble(12, patientForm.getTotalDiscount());
			preparedStatement.setDouble(13, patientForm.getConsultationCharges()); // Change

			preparedStatement.execute();

			status = "success";

			System.out.println("Bill receipt added successfully");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public int retrieveLastReceiptID(int visitID) {

		int receiptID = 0;

		try {

			connection = getConnection();

			String retrieveLastReceiptIDQuery = QueryMaker.RETRIEVE_LAST_ENTERED_RECEIPT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastReceiptIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				receiptID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return receiptID;
	}

	public boolean verifyResultAddedForVisit(int visitID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyResultAddedForVisitQuery = QueryMaker.VERIFY_LAB_RESULT_ADDED_FOR_VISIT;

			preparedStatement = connection.prepareStatement(verifyResultAddedForVisitQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public List<PatientForm> retrieveExistingLabTestByVisitID(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveExistingLabTestByVisitIDQuery = QueryMaker.RETRIEVE_LAB_INVESTIGATION1;

			preparedStatement = connection.prepareStatement(retrieveExistingLabTestByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {
				form = new PatientForm();

				form.setSrNo(srNo);
				/* form.setCBCPanel(resultSet.getString("panel")); */
				form.setCBCProfileTest(resultSet.getString("test").trim());
				form.setRate(resultSet.getDouble("rate"));
				form.setGroupRate(resultSet.getDouble("groupRate"));
				form.setIsGroup(resultSet.getInt("isGroup"));
				form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
				form.setInvestigationDetailsID(resultSet.getInt("id"));
				form.setVisitID(resultSet.getInt("visitID"));
				form.setGroupName(resultSet.getString("groupName"));
				form.setSubGroup(resultSet.getString("subGroup"));

				if (resultSet.getString("qualitativeValue") == null || resultSet.getString("qualitativeValue") == "") {
					form.setCBCProfileValue("" + resultSet.getDouble("quantitativeValue"));
					System.out.println("..." + resultSet.getDouble("quantitativeValue"));
				} else if (resultSet.getString("qualitativeValue").isEmpty()) {
					form.setCBCProfileValue("" + resultSet.getDouble("quantitativeValue"));
					System.out.println("..." + resultSet.getDouble("quantitativeValue"));
				} else {
					form.setCBCProfileValue(resultSet.getString("qualitativeValue"));
				}
				form.setTestType(resultSet.getString("testType"));

				list.add(form);

				srNo++;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String updateLabPatientVisit(PatientForm patientForm) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			String insertPatientVisitQuery = QueryMaker.UPDATE_LAB_VISIT_DETAILS;

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			preparedStatement = connection.prepareStatement(insertPatientVisitQuery);

			preparedStatement.setString(1, date1);
			preparedStatement.setString(2, patientForm.getReferredBy());
			preparedStatement.setString(3, patientForm.getSampleID());
			preparedStatement.setString(4, patientForm.getMedicalNotes());
			preparedStatement.setInt(5, patientForm.getMdDoctorID());
			preparedStatement.setDouble(6, patientForm.getTotalRate());
			if (patientForm.getIsReportDispatched() == null) {
				preparedStatement.setBoolean(7, false);
			} else {
				preparedStatement.setBoolean(7, patientForm.getIsReportDispatched());
			}
			preparedStatement.setInt(8, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("visit details updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String updateLabInvestigationDetails(int investigationID, String value) {

		boolean isNumber = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("value is: " + value + "--" + investigationID);

		try {

			connection = getConnection();

			String insertLabInvestigationDetailsQuery = QueryMaker.UPDATE_LAB_INVESTIGATION1;

			preparedStatement = connection.prepareStatement(insertLabInvestigationDetailsQuery);

			String val = "";

			if (value == null) {
				val = "";
			} else if (value.isEmpty()) {
				val = "";
			} else if (value.equals("null")) {
				val = "";
			} else {
				val = value;
			}

			/*
			 * Checking whether value is quantitative or qualitative by checking whether
			 * value is numeric or string, if numeric then it is quantitative else
			 * qualitative
			 */
			isNumber = isNumeric(val);

			if (isNumber) {

				preparedStatement.setString(1, "");
				preparedStatement.setDouble(2, Double.parseDouble(val));

			} else {

				preparedStatement.setString(1, val);
				preparedStatement.setDouble(2, 0D);

			}

			preparedStatement.setInt(3, investigationID);

			preparedStatement.executeUpdate();

			System.out.println("Lab investigation details updated successfully.");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	/**
	 * 
	 * @param patientID
	 * @return
	 */
	public String retrievePatientFNameLNameByID(int patientID) {

		String patientName = "";

		try {

			connection1 = getConnection();

			String retrievePatientFNameLNameByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID1;

			preparedStatement1 = connection1.prepareStatement(retrievePatientFNameLNameByIDQuery);

			preparedStatement1.setInt(1, patientID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientName = resultSet1.getString("firstName") + "=" + resultSet1.getString("lastName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientName;
	}

	public String retrieveToEmailByPractiecID(int practiceID) {
		String toEmail = "";
		System.out.println("practiceid is::" + practiceID);
		try {
			connection1 = getConnection();
			String retrieveToEmailByIDQuery = QueryMaker.RETRIEVE_TO_EMAIL_BY_PRACTICE_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveToEmailByIDQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				toEmail = resultSet1.getString("emailTo");
			}
			resultSet1.close();
			preparedStatement1.close();
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return toEmail;
	}

	@Override
	public String retrieveFromEmailByPractiecID(int practiceID) {
		String fromEmail = "";
		System.out.println("practiceid is::" + practiceID);
		try {
			connection1 = getConnection();
			String retrieveFromEmailByIDQuery = QueryMaker.RETRIEVE_FROM_EMAIL_BY_PRACTICE_ID;
			preparedStatement1 = connection1.prepareStatement(retrieveFromEmailByIDQuery);
			preparedStatement1.setInt(1, practiceID);
			resultSet1 = preparedStatement1.executeQuery();
			while (resultSet1.next()) {
				fromEmail = resultSet1.getString("emailFrom");
			}
			resultSet1.close();
			preparedStatement1.close();
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return fromEmail;
	}

	@Override
	public String retrieveFromEmailPassByPractiecID(int practiceID) {
		String fromEmailPass = "";
		System.out.println("practiceid is::" + practiceID);
		try {
			connection1 = getConnection();
			String retrieveFromEmailPassByIDQuery = QueryMaker.RETRIEVE_FROM_EMAIL_PASS_BY_PRACTICE_ID;
			preparedStatement1 = connection1.prepareStatement(retrieveFromEmailPassByIDQuery);
			preparedStatement1.setInt(1, practiceID);
			resultSet1 = preparedStatement1.executeQuery();
			while (resultSet1.next()) {
				fromEmailPass = resultSet1.getString("emailFromPass");
			}
			resultSet1.close();
			preparedStatement1.close();
			connection1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return fromEmailPass;
	}

	@Override
	public LinkedHashMap<String, String> retrieveSingleLabTestListByVisitID(int visitID) {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveExistingLabTestByVisitIDQuery = QueryMaker.RETRIEVE_DISTINCT_SUB_GROUP_FOR_TEST_BY_VISIT;

			preparedStatement = connection.prepareStatement(retrieveExistingLabTestByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, 0);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String test = "";

				String subGroup = resultSet.getString("subGroup");

				String retrieveLabTestBySubgroupQuery = QueryMaker.RETRIEVE_LAB_TESTS_BY_VISIT_ID_AND_SUB_GROUP;

				preparedStatement1 = connection.prepareStatement(retrieveLabTestBySubgroupQuery);

				preparedStatement1.setInt(1, visitID);
				preparedStatement1.setString(2, subGroup);
				preparedStatement1.setInt(3, 0);

				resultSet1 = preparedStatement1.executeQuery();

				while (resultSet1.next()) {

					String result = "";

					if (resultSet1.getString("qualitativeValue") == null
							|| resultSet1.getString("qualitativeValue") == "") {

						result = "" + resultSet1.getDouble("quantitativeValue");

					} else if (resultSet1.getString("qualitativeValue").isEmpty()) {

						result = "" + resultSet1.getDouble("quantitativeValue");

					} else {
						result = resultSet1.getString("qualitativeValue");
					}

					test = test + "___" + resultSet1.getString("test") + "==="
							+ resultSet1.getString("qualitativeValue") + "==="
							+ resultSet1.getDouble("quantitativeValue") + "===" + resultSet1.getString("normalRange");

				}

				if (test.startsWith("___")) {
					test = test.substring(3);
				}

				if (test.contains("===")) {
					map.put(subGroup, test);
				} else {
					continue;
				}
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

	@Override
	public LinkedHashMap<String, LinkedHashMap<String, String>> retrieveGroupLabTestByVisitID(int visitID, int practiceID) {

		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		try {

			connection = getConnection();

			String retrieveGroupLabTestByVisitIDQuery = QueryMaker.RETRIEVE_LAB_TEST_DISTINCT_GROUP_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveGroupLabTestByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, 1);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				String groupTest = "";

				String groupName = resultSet.getString("groupName");

				if (groupName != null) {

					String retrieveTestDetailsQuery = QueryMaker.RETRIEVE_LAB_TESTS_BY_VISIT_ID_AND_GROUP_NAME;

					preparedStatement1 = connection.prepareStatement(retrieveTestDetailsQuery);

					preparedStatement1.setInt(1, visitID);
					preparedStatement1.setString(2, groupName);
					preparedStatement1.setInt(3, practiceID);

					resultSet1 = preparedStatement1.executeQuery();  
					
					while (resultSet1.next()) {
						String result = "";

						if (resultSet1.getString("qualitativeValue") == null
								|| resultSet1.getString("qualitativeValue") == "") {

							result = "" + resultSet1.getDouble("quantitativeValue");

						} else if (resultSet1.getString("qualitativeValue").isEmpty()) {

							result = "" + resultSet1.getDouble("quantitativeValue");

						} else {
							result = resultSet1.getString("qualitativeValue");
						}

						groupTest = groupTest + "___" + resultSet1.getString("test") + "==="
								+ resultSet1.getString("qualitativeValue") + "==="
								+ resultSet1.getDouble("quantitativeValue") + "==="
								+ resultSet1.getString("normalRange") + "===" + resultSet1.getString("subGroup");

					}

					if (groupTest.startsWith("___")) {
						groupTest = groupTest.substring(3);
					}

					LinkedHashMap<String, String> subMap = new LinkedHashMap<>();

					if (!groupTest.contains("===")) {
						continue;
					} else {
						String array[] = groupTest.split("___");

						for (int i = 0; i < array.length; i++) {

							String subGroup = "";

							String subArray[] = array[i].split("===");

							if (subArray.length > 4) {
								subGroup = subArray[4];
							}

							if (subMap.containsKey(subGroup)) {

								String value = subMap.get(subGroup);

								value = value + "___" + array[i];

								subMap.put(subGroup, value);
							} else {
								subMap.put(subGroup, array[i]);
							}
							
							/*
							 * LinkedHashMap<String, String> reversedSubMap = new LinkedHashMap<>();
							 * 
							 * String[] keys = subMap.keySet().toArray(new String [subMap.size()]);
							 * 
							 * for(int j = keys.length - 1; j >= 0; j--) { reversedSubMap.put(keys[j],
							 * subMap.get(keys[j])); }
							 */
							
							map.put(groupName, subMap);
						}
					}

				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public String retrieveTestNormalRangeDesc(String test, String group) {

		String normalRangeDesc = "";

		try {

			connection = getConnection();

			String retrieveTestRemarkeQuery = QueryMaker.VERIFY_IS_NORMA_RANGE_EXCLUDE;

			preparedStatement = connection.prepareStatement(retrieveTestRemarkeQuery);

			preparedStatement.setString(1, test);
			preparedStatement.setString(2, test);
			preparedStatement.setString(3, group);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				normalRangeDesc = resultSet.getString("normalRangeDesc");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return normalRangeDesc;
	}

	@Override
	public String retrieveTestRemark(String test, String group) {

		String remark = "";

		try {

			connection = getConnection();
			
			if(group == "") {
				String retrieveTestRemarkeQuery = QueryMaker.VERIFY_IS_NORMA_RANGE_EXCLUDE_WITHOUT_GROUP;

				preparedStatement = connection.prepareStatement(retrieveTestRemarkeQuery);

				preparedStatement.setString(1, test);
				
			} else {
				String retrieveTestRemarkeQuery = QueryMaker.VERIFY_IS_NORMA_RANGE_EXCLUDE;

				preparedStatement = connection.prepareStatement(retrieveTestRemarkeQuery);

				preparedStatement.setString(1, test);
				preparedStatement.setString(2, group);

			}
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				remark = resultSet.getString("remarks");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return remark;
	}

	@Override
	public boolean isNormalValueExclude(String test, String groupName) {

		boolean check = false;

		try {

			connection = getConnection();

			String isNormalValueExcludeQuery = QueryMaker.VERIFY_IS_NORMA_RANGE_EXCLUDE;

			preparedStatement = connection.prepareStatement(isNormalValueExcludeQuery);

			preparedStatement.setString(1, test);
			preparedStatement.setString(2, groupName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getBoolean("isExcludeNormalValues");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public boolean showNormalRange(String test, String groupName) {

		boolean check = false;

		try {

			connection = getConnection();

			String isNormalValueExcludeQuery = QueryMaker.VERIFY_IS_NORMA_RANGE_EXCLUDE;

			preparedStatement = connection.prepareStatement(isNormalValueExcludeQuery);

			preparedStatement.setString(1, test);
			preparedStatement.setString(2, groupName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getBoolean("showNormalRangeDesc");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public JSONObject retrieveGroupValues(String groupName, String columnName, int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveLabTestListQuery = "SELECT GROUP_CONCAT(CONCAT_WS('$', test, COALESCE(" + columnName
					+ ",'null')"
					+ ", CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'),"
					+ " COALESCE(subgroup,'null')) SEPARATOR '===') AS groupValues, groupRate FROM PVLabTests "
					+ "WHERE (groupName IS NOT null OR groupName <> '') AND groupName = ? AND activityStatus = ? AND practiceID ="
					+ practiceID;

			preparedStatement = connection.prepareStatement(retrieveLabTestListQuery);

			preparedStatement.setString(1, groupName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			System.out.println("preparedStatement ::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("groupValues", resultSet.getString("groupValues"));

				object.put("groupRate", resultSet.getDouble("groupRate"));

				array.add(object);

				values.put("Release", array);
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrorMessage", "Failed to retrieve group values. Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	@Override
	public JSONObject retrieveLabDefaultValueList(String test) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveLabDefaultValueListQuery = QueryMaker.RETRIEVE_Lab_Default_Value_List;

			preparedStatement = connection.prepareStatement(retrieveLabDefaultValueListQuery);

			preparedStatement.setString(1, test);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				object = new JSONObject();

				object.put("check", check);
				object.put("testVal", resultSet.getString("defaultValue"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public JSONObject deleteLabInvestigationTest(int testNameID) {
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {

			connection = getConnection();

			String deleteLabInvestigationTestQuery = QueryMaker.DELETE_LabInvestigationTest_DETAILS;

			preparedStatement = connection.prepareStatement(deleteLabInvestigationTestQuery);

			preparedStatement.setInt(1, testNameID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			object.put("ExceptionMessage", "");
			array.add(object);

			values.put("Release", array);

			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Prescription details into table due to:::"
					+ exception.getMessage());

			check = 0;
			object.put("check", check);
			object.put("ExceptionMessage",
					"Exception occured while deleting prescription.Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public String insertVisitBDPData(PatientForm patientForm, int visitNumber) {
		// TODO Auto-generated method stub

		String result = "error";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			String insertPLoginAttemptQuery = QueryMaker.INSERT_INTO_VISIT_DATA;

			preparedStatement = connection.prepareStatement(insertPLoginAttemptQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setString(2, date1);
			preparedStatement.setString(3, patientForm.getTemplate());
			preparedStatement.setInt(4, patientForm.getPatientID());
			preparedStatement.setInt(5, patientForm.getClinicID());
			preparedStatement.setString(6, ActivityStatus.ACTIVE);
			preparedStatement.setInt(7, patientForm.getVisitTypeID());
			preparedStatement.setInt(8, patientForm.getClinicianID());
			preparedStatement.setInt(9, patientForm.getReportID());

			preparedStatement.execute();

			result = "success";

			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting into LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;
	}

	@Override
	public String retrieveTemplate(int lastOPDVisitID) {
		// TODO Auto-generated method stub
		String template = "";
		System.out.println("hhiiiii");
		try {

			connection = getConnection();

			String retrievePaymentTypeQuery = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrievePaymentTypeQuery);

			preparedStatement.setInt(1, lastOPDVisitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				template = resultSet.getString("visitNote");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return template;
	}

	@Override
	public int retrieveclinicianID(int visitID) {
		// TODO Auto-generated method stub
		int clinicianID = -1;

		try {

			connection = getConnection();

			String retrieveclinicianID = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveclinicianID);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicianID = resultSet.getInt("clinicianID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return clinicianID;
	}

	@Override
	public int retrieveReportID(int visitID) {
		// TODO Auto-generated method stub
		int reportID = -1;

		try {

			connection = getConnection();

			String retrieveReportID = QueryMaker.RETRIEVE_VISIT_DEATILS_BY_ID1;

			preparedStatement = connection.prepareStatement(retrieveReportID);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reportID = resultSet.getInt("templateID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return reportID;
	}

	@Override
	public int retrieveclinicianIDBYAptID(int aptID) {
		// TODO Auto-generated method stub
		int clinicianID = -1;

		try {

			connection = getConnection();

			String retrieveclinicianIDBYAptID = QueryMaker.RETRIEVE_CLINICIAN_DEATILS_BY_APT_ID;

			preparedStatement = connection.prepareStatement(retrieveclinicianIDBYAptID);

			preparedStatement.setInt(1, aptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicianID = resultSet.getInt("clinicianID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return clinicianID;
	}

	@Override
	public String UpdateVisitBDPData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		String result = "error";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;

		try {
			connection = getConnection();

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			String insertPLoginAttemptQuery = QueryMaker.UPDATE_INTO_VISIT_DATA;

			preparedStatement = connection.prepareStatement(insertPLoginAttemptQuery);

			preparedStatement.setString(1, date1);
			preparedStatement.setString(2, patientForm.getTemplate());
			preparedStatement.setInt(3, patientForm.getClinicianID());
			preparedStatement.setInt(4, patientForm.getReportID());
			preparedStatement.setInt(5, patientForm.getVisitID());
			System.out.println("test :: " + preparedStatement);
			preparedStatement.executeUpdate();

			result = "success";
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating status to Locked from LoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return result;
	}

	@Override
	public String UpdateUSGVisitBDPData(PatientForm patientForm) {
		// TODO Auto-generated method stub
		String result = "error";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;
		String lastMenstrualPeriod = null;
		String estimatedDueDate = null;
		try {
			connection = getConnection();

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			///////////////////////////////////
			////// check last period date
			//////////////////////////////////
			if (patientForm.getLastMenstrualPeriod() == null || patientForm.getLastMenstrualPeriod() == "") {
				// Converting date into db format
				lastMenstrualPeriod = null;
			} else if (patientForm.getLastMenstrualPeriod().isEmpty()) {
				// Converting date into db format
				lastMenstrualPeriod = null;
			} else {
				// Converting date into db format
				lastMenstrualPeriod = dateFormat.format(dateFormat1.parse(patientForm.getLastMenstrualPeriod()));
			}

			//////////////////////////////////////
			// check estimate preganancy date
			/////////////////////////////////////

			if (patientForm.getEstimatedDueDate() == null || patientForm.getEstimatedDueDate() == "") {
				// Converting date into db format
				estimatedDueDate = null;
			} else if (patientForm.getEstimatedDueDate().isEmpty()) {
				// Converting date into db format
				estimatedDueDate = null;
			} else {
				// Converting date into db format
				estimatedDueDate = dateFormat.format(dateFormat1.parse(patientForm.getEstimatedDueDate()));
			}

			String insertPLoginAttemptQuery = QueryMaker.UPDATE_USG_VISIT_DATA;

			preparedStatement = connection.prepareStatement(insertPLoginAttemptQuery);

			preparedStatement.setString(1, date1);
			preparedStatement.setInt(2, patientForm.getClinicianID());
			preparedStatement.setString(3, patientForm.getNameOfGuardian());
			preparedStatement.setString(4, lastMenstrualPeriod);
			preparedStatement.setInt(5, patientForm.getWeekOfPregnancy());
			preparedStatement.setString(6, estimatedDueDate);
			preparedStatement.setString(7, patientForm.getReferredBy());
			preparedStatement.setInt(8, patientForm.getVisitID());

			preparedStatement.executeUpdate();

			result = "success";
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating status to Locked from LoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return result;
	}

	@Override
	public List<PatientForm> retrieveTransactionDetailsByVisitID1(int visitID, String careType) {
		// TODO Auto-generated method stub
		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();
			if (careType == "PathologyLab") {
				String retrieveTransactionDetailsByVisitIDQuery = QueryMaker.RETRIEVE_TRANSACTION_DETAILS1;

				preparedStatement = connection.prepareStatement(retrieveTransactionDetailsByVisitIDQuery);

				preparedStatement.setInt(1, visitID);
				preparedStatement.setInt(2, visitID);

				resultSet = preparedStatement.executeQuery();

				int srNo = 1;

				while (resultSet.next()) {

					form = new PatientForm();

					form.setSrNo(srNo);
					// form.setCBCPanel(resultSet.g);
					form.setCBCProfileTest(resultSet.getString("test"));
					form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
					form.setRate(resultSet.getDouble("amount"));
					form.setGroupCheck(resultSet.getInt("isGroup"));

					list.add(form);

					srNo++;

				}

				/*
				 * check whether data available in transaction table or not by checking srNo is
				 * equal to 1 or not , if yes then data not available in transaction table which
				 * means no receipt yet added for the current visitID, and so retrieve the test
				 * details for bill from LabInvestigation table
				 */
				if (srNo == 1) {

					JDBCHelper.closeResultSet(resultSet);
					JDBCHelper.closeStatement(preparedStatement);

					String retrieveLabInvestigationByVisitIDQuery = QueryMaker.RETRIEVE_LAB_INVESTIGATION_BY_VISIT_ID_AND_IS_GROUP;

					preparedStatement = connection.prepareStatement(retrieveLabInvestigationByVisitIDQuery);

					preparedStatement.setInt(1, visitID);
					preparedStatement.setInt(2, 0);

					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {

						form = new PatientForm();

						form.setSrNo(srNo);
						// form.setCBCPanel(resultSet.g);
						form.setCBCProfileTest(resultSet.getString("test"));
						form.setCBCProfileNormalValue(resultSet.getString("normalRange"));
						form.setRate(resultSet.getDouble("rate"));
						form.setGroupCheck(0);

						list.add(form);

						srNo++;

					}

				}
			} else {
				String retrieveVistiDetailsByVisitIDQuery = QueryMaker.RETRIEVE_VISIT_TRANSACTION_DETAILS;

				preparedStatement = connection.prepareStatement(retrieveVistiDetailsByVisitIDQuery);

				preparedStatement.setInt(1, visitID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					form = new PatientForm();

					// form.setCBCPanel(resultSet.g);
					form.setVisitType(resultSet.getString("name"));
					form.setVisitRate(resultSet.getDouble("consultationCharges"));

					list.add(form);

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public String insertVisitUSGData(PatientForm patientForm, int visitNumber) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String result = "error";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		String date1 = null;
		String lastMenstrualPeriod = null;
		String estimatedDueDate = null;
		try {
			connection = getConnection();

			if (patientForm.getVisitDate() == null || patientForm.getVisitDate() == "") {
				// Converting date into db format
				date1 = null;
			} else if (patientForm.getVisitDate().isEmpty()) {
				// Converting date into db format
				date1 = null;
			} else {
				// Converting date into db format
				date1 = dateFormat.format(dateFormat1.parse(patientForm.getVisitDate()));
			}

			///////////////////////////////////
			////// check last period date
			//////////////////////////////////
			if (patientForm.getLastMenstrualPeriod() == null || patientForm.getLastMenstrualPeriod() == "") {
				// Converting date into db format
				lastMenstrualPeriod = null;
			} else if (patientForm.getLastMenstrualPeriod().isEmpty()) {
				// Converting date into db format
				lastMenstrualPeriod = null;
			} else {
				// Converting date into db format
				lastMenstrualPeriod = dateFormat.format(dateFormat1.parse(patientForm.getLastMenstrualPeriod()));
			}

			//////////////////////////////////////
			// check estimate preganancy date
			/////////////////////////////////////

			if (patientForm.getEstimatedDueDate() == null || patientForm.getEstimatedDueDate() == "") {
				// Converting date into db format
				estimatedDueDate = null;
			} else if (patientForm.getEstimatedDueDate().isEmpty()) {
				// Converting date into db format
				estimatedDueDate = null;
			} else {
				// Converting date into db format
				estimatedDueDate = dateFormat.format(dateFormat1.parse(patientForm.getEstimatedDueDate()));
			}

			String insertUSGVisitDataQuery = QueryMaker.INSERT_INTO_USG_VISIT_DATA;

			preparedStatement = connection.prepareStatement(insertUSGVisitDataQuery);

			preparedStatement.setInt(1, visitNumber);
			preparedStatement.setString(2, date1);
			preparedStatement.setInt(3, patientForm.getPatientID());
			preparedStatement.setInt(4, patientForm.getClinicID());
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.setInt(6, patientForm.getVisitTypeID());
			preparedStatement.setInt(7, patientForm.getClinicianID());
			preparedStatement.setString(8, patientForm.getNameOfGuardian());
			preparedStatement.setString(9, lastMenstrualPeriod);
			preparedStatement.setInt(10, patientForm.getWeekOfPregnancy());
			preparedStatement.setString(11, estimatedDueDate);
			preparedStatement.setString(12, patientForm.getReferredBy());
			preparedStatement.setString(13, ActivityStatus.PENDING);

			preparedStatement.execute();

			result = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting into LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;

	}

	@Override
	public JSONObject retrivePatientDetailsByID(int patientID, int visitID, String pcpNDTLocalPath) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			int clinicianID = 0;
			int refferedBy = 0;
			connection = getConnection();

			/*
			 * Retrieving patient's details based on patientID
			 */
			String retrievePatientDetailsByPatientIDQuery = QueryMaker.RETREIVE_PATIENT_DETAILS_BY_PID_VID;

			preparedStatement = connection.prepareStatement(retrievePatientDetailsByPatientIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				refferedBy = resultSet.getInt("referredBy");
				clinicianID = resultSet.getInt("clinicianID");

				map.put("pFirstName", resultSet.getString("firstName"));
				map.put("pMiddleName", resultSet.getString("middleName"));
				map.put("pLastName", resultSet.getString("lastName"));
				map.put("pMobile", resultSet.getString("mobile"));
				map.put("pAddress", resultSet.getString("address"));
				map.put("pReltv", resultSet.getString("nameOfGuardian"));
				map.put("pAge", resultSet.getString("age"));
				map.put("pDOB", resultSet.getString("dateOfBirth"));
				map.put("pGender", resultSet.getString("gender"));
				map.put("ppincode", resultSet.getString("pincode"));
				map.put("lastMenstrualPeriod", resultSet.getString("lastMenstrualPeriod"));
				map.put("estimatedDueDate", resultSet.getString("estimatedDueDate"));
				map.put("weekOfPregnancy", resultSet.getString("weekOfPregnancy"));
				map.put("visitDate", resultSet.getString("visitDate"));
			}

			String retrieverefferedDoctorDetails = QueryMaker.RETREIVE_REFFERAL_DOCTOR_DETAILS;

			preparedStatement1 = connection.prepareStatement(retrieverefferedDoctorDetails);

			preparedStatement1.setInt(1, refferedBy);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("reffClinicAddress", resultSet1.getString("clinicAddress"));
				map.put("reffDoctorName", resultSet1.getString("doctorName"));
				map.put("reffClinicName", resultSet1.getString("clinicName"));
			}

			String retrieveRadiologyDoctorDetails = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement2 = connection.prepareStatement(retrieveRadiologyDoctorDetails);

			preparedStatement2.setInt(1, clinicianID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("radiologyName", resultSet2.getString("firstName") + " " + resultSet2.getString("middleName")
						+ " " + resultSet2.getString("lastName"));
				map.put("radiologyRegNo", resultSet2.getString("clinicianRegNo"));
				map.put("radiologyPlace", resultSet2.getString("city"));
			}

			String retrieveSonDaughterDetails = QueryMaker.RETRIEVE_PCPNDT_DATA_VISIT_ID;

			preparedStatement3 = connection.prepareStatement(retrieveSonDaughterDetails);

			preparedStatement3.setInt(1, visitID);

			resultSet3 = preparedStatement3.executeQuery();
			System.out.println(preparedStatement3);
			while (resultSet3.next()) {

				map.put("numberOfSons", resultSet3.getString("numberOfSons"));
				map.put("ageOfSons", resultSet3.getString("ageOfSons"));
				map.put("numberOfDaughters", resultSet3.getString("numberOfDaughters"));
				map.put("ageOfDaughters", resultSet3.getString("ageOfDaughters"));
			}

			map.put("pcpNDTLocalPath", pcpNDTLocalPath);

			resultSet.close();
			preparedStatement.close();
			resultSet1.close();
			preparedStatement1.close();
			resultSet2.close();
			preparedStatement2.close();
			connection.close();

			object.put("patientDetails", map);

			array.add(object);
			values.put("Release", array);

			System.out.println("values are ::" + values);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return values;
	}

	@Override
	public String UpdatePCPNDTStatus(int patientID, int visitID) {
		// TODO Auto-generated method stub
		try {
			connection = getConnection();

			String updatePCPNDTStatus = QueryMaker.UPDATE_PCPNDT_STATUS;

			preparedStatement = connection.prepareStatement(updatePCPNDTStatus);

			preparedStatement.setString(1, ActivityStatus.DONE);
			preparedStatement.setInt(2, visitID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			status = "error";
		}
		return status;
	}

	@Override
	public String insertSonsDaughtersData(PatientForm patientForm) {
		// TODO Auto-generated method stub

		String result = "error";

		try {
			connection = getConnection();

			String insertSonsDaughtersAgeQuery = QueryMaker.INSERT_INTO_SONS_DAUGHTERS_DATA;

			preparedStatement1 = connection.prepareStatement(insertSonsDaughtersAgeQuery);

			preparedStatement1.setInt(1, patientForm.getNumberOfSons());
			preparedStatement1.setString(2, patientForm.getSonAge());
			preparedStatement1.setInt(3, patientForm.getNumberOfDaughters());
			preparedStatement1.setString(4, patientForm.getDaughterAge());
			preparedStatement1.setInt(5, patientForm.getVisitID());
			preparedStatement1.execute();

			result = "success";

			preparedStatement1.close();
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting into LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;

	}

	@Override
	public String getSonAge(int visitID) {
		// TODO Auto-generated method stub
		String ageOfSon = "";
		try {
			connection = getConnection();

			String retreivePCPNDTQuery = QueryMaker.RETRIEVE_PCPNDT_DATA_VISIT_ID;

			preparedStatement7 = connection.prepareStatement(retreivePCPNDTQuery);

			preparedStatement7.setInt(1, visitID);

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {
				System.out.println("in pcpndt loop" + resultSet7.getString("ageOfSons"));
				ageOfSon = resultSet7.getString("ageOfSons");
			}
			resultSet7.close();
			preparedStatement7.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ageOfSon;

	}

	@Override
	public String getDaughterAge(int visitID) {
		// TODO Auto-generated method stub
		String ageOfDaughters = "";
		try {
			connection = getConnection();

			String retreivePCPNDTQuery = QueryMaker.RETRIEVE_PCPNDT_DATA_VISIT_ID;

			preparedStatement7 = connection.prepareStatement(retreivePCPNDTQuery);

			preparedStatement7.setInt(1, visitID);

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {
				System.out.println("in pcpndt loop" + resultSet7.getString("ageOfDaughters"));
				ageOfDaughters = resultSet7.getString("ageOfDaughters");
			}
			resultSet7.close();
			preparedStatement7.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ageOfDaughters;

	}

	@Override
	public List<PatientForm> retrieveOrthoDiagnosisList(int visitId) {
		// TODO Auto-generated method stub
		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveOrthoDiagnosisListQuery = QueryMaker.RETRIEVE_ORTHO_DIAGNOSIS_LIST_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveOrthoDiagnosisListQuery);

			preparedStatement.setInt(1, visitId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String diagnosisJSONString = resultSet.getString("diagnosis");

				if (diagnosisJSONString == null || diagnosisJSONString == "") {
					continue;
				} else if (diagnosisJSONString.isEmpty()) {
					continue;
				} else {
					org.json.JSONArray diagnosisJSONArray = new org.json.JSONArray(diagnosisJSONString);

					for (int i = 0; i < diagnosisJSONArray.length(); i++) {
						org.json.JSONObject diagnosisJSONObject = diagnosisJSONArray.getJSONObject(i);

						int min = 100;
						int max = 200;
						// Generating Random number
						int randomNumber = (int) (Math.random() * (max - min + 1) + min);

						form = new PatientForm();

						form.setDiagnosis(diagnosisJSONObject.getString("diagnosis"));
						// form.setDiagnosisRate(diagnosisJSONObject.getString("rate"));
						form.setDiagnosisID(randomNumber);

						list.add(form);

					}
				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PatientForm> retrieveOrthoBillingItemList(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		String diagnosis = "";

		try {

			connection = getConnection();

			// Retrieving diagnosis from the Visit table based on the visitID
			String retrieveDiagnosisFromVisitQuery = QueryMaker.RETRIEVE_EXISTING_VISIT_LIST;

			preparedStatement = connection.prepareStatement(retrieveDiagnosisFromVisitQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				diagnosis = resultSet.getString("diagnosis");
			}

			resultSet.close();
			preparedStatement.close();

			String retrieveOrthoBillingItemListQuery = QueryMaker.RETRIEVE_RECEIPT_ITEM_DETAILS_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveOrthoBillingItemListQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			int receiptItemsRows = 0;

			while (resultSet.next()) {

				receiptItemsRows++;

				form = new PatientForm();

				form.setItemRate(resultSet.getDouble("rate"));
				form.setItemAmount(resultSet.getDouble("amount"));
				form.setItemQuantity(resultSet.getInt("quantity"));
				form.setItemName(resultSet.getString("item"));
				form.setReceiptItemID(resultSet.getInt("id"));

				list.add(form);

			}

			/**
			 * Check if receiptItemsRows is zero, if so then generating the billing items
			 * based on the diagnosis added for this visit for ortho practice and also check
			 * if the cancerType value is of JSON type if so then only proceed further
			 */
			/*
			 * if (receiptItemsRows == 0 && new JSONValidator().isValid(diagnosis)) {
			 * 
			 * org.json.JSONArray diagnosisJSONArray = new org.json.JSONArray(diagnosis);
			 * 
			 * for (int i = 0; i < diagnosisJSONArray.length(); i++) {
			 * 
			 * org.json.JSONObject diagnosisJSONObject =
			 * diagnosisJSONArray.getJSONObject(i);
			 * 
			 * form = new PatientForm();
			 * 
			 * form.setItemRate(Double.parseDouble(diagnosisJSONObject.getString("rate")));
			 * form.setItemAmount(Double.parseDouble(diagnosisJSONObject.getString("rate")))
			 * ; form.setItemQuantity(1);
			 * form.setItemName(diagnosisJSONObject.getString("diagnosis"));
			 * form.setReceiptItemID(0if (receiptItemsRows == 0 && new
			 * JSONValidator().isValid(diagnosis)) {
			 * 
			 * org.json.JSONArray diagnosisJSONArray = new org.json.JSONArray(diagnosis);
			 * 
			 * for (int i = 0; i < diagnosisJSONArray.length(); i++) {
			 * 
			 * org.json.JSONObject diagnosisJSONObject =
			 * diagnosisJSONArray.getJSONObject(i);
			 * 
			 * form = new PatientForm();
			 * 
			 * form.setItemRate(Double.parseDouble(diagnosisJSONObject.getString("rate")));
			 * form.setItemAmount(Double.parseDouble(diagnosisJSONObject.getString("rate")))
			 * ; form.setItemQuantity(1);
			 * form.setItemName(diagnosisJSONObject.getString("diagnosis"));
			 * form.setReceiptItemID(0);
			 * 
			 * list.add(form);
			 * 
			 * }
			 * 
			 * });
			 * 
			 * list.add(form);
			 * 
			 * }
			 * 
			 * }
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public String insertReceiptItemDetails(String itemName, double rate, double amount, int quantity, int receiptID) {
		try {
			connection = getConnection();

			String insertReceiptItemDetailsQuery = QueryMaker.INSERT_RECEIPT_ITEM;

			preparedStatement = connection.prepareStatement(insertReceiptItemDetailsQuery);

			preparedStatement.setString(1, itemName);
			preparedStatement.setInt(2, quantity);
			preparedStatement.setDouble(3, rate);
			preparedStatement.setDouble(4, amount);
			preparedStatement.setInt(5, receiptID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Receipt item details added successfully into ReceiptItems table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Receipt item details added successfully into ReceiptItems table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String updateReceiptItemDetails(String itemName, double rate, double amount, int quantity,
			int receiptItemID) {
		try {
			connection = getConnection();

			String insertReceiptItemDetailsQuery = QueryMaker.UPDATE_RECEIPT_ITEM;

			preparedStatement = connection.prepareStatement(insertReceiptItemDetailsQuery);

			preparedStatement.setString(1, itemName);
			preparedStatement.setInt(2, quantity);
			preparedStatement.setDouble(3, rate);
			preparedStatement.setDouble(4, amount);
			preparedStatement.setInt(5, receiptItemID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Receipt item details updated successfully into ReceiptItems table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Receipt item details added successfully into ReceiptItems table due to:::"
							+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public String retrieveClinicPhoneNo(int clinicID) {

		String phoneNo = "";

		try {

			connection1 = getConnection();

			String retrieveClinicPhoneNoQuery = QueryMaker.RETRIEVE_CLINIC_PHONE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveClinicPhoneNoQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				phoneNo = resultSet1.getString("phoneNo");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return phoneNo;
	}

	@Override
	public String retrievePracticeBucketName(int practiceID) {

		String bucketName = "";

		try {

			connection1 = getConnection();

			String retrievePracticeBucketNameQuery = QueryMaker.RETRIEVE_PRACTICE_DETAILS;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeBucketNameQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				bucketName = resultSet1.getString("bucketName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return bucketName;
	}

	@Override
	public String insertCycloplegicRefraction(PatientForm form) {
		try {
			connection = getConnection();

			String insertCycloplegicRefractionQuery = QueryMaker.INSERT_CYCLOPLEGIC_REFRACTION_DETAILS;

			preparedStatement = connection.prepareStatement(insertCycloplegicRefractionQuery);

			preparedStatement.setString(1, form.getDistCTCOD());
			preparedStatement.setString(2, form.getDistHTCOD());
			preparedStatement.setString(3, form.getDistAtropineOD());
			preparedStatement.setString(4, form.getDistTPlusOD());
			preparedStatement.setString(5, form.getDistCTCOS());
			preparedStatement.setString(6, form.getDistHTCOS());
			preparedStatement.setString(7, form.getDistAtropineOS());
			preparedStatement.setString(8, form.getDistTPlusOS());
			preparedStatement.setString(9, form.getNearCTCOD());
			preparedStatement.setString(10, form.getNearHTCOD());
			preparedStatement.setString(11, form.getNearAtropineOD());
			preparedStatement.setString(12, form.getNearTPlusOD());
			preparedStatement.setString(13, form.getNearCTCOS());
			preparedStatement.setString(14, form.getNearHTCOS());
			preparedStatement.setString(15, form.getNearAtropineOS());
			preparedStatement.setString(16, form.getNearTPlusOS());
			preparedStatement.setInt(17, form.getVisitID());
			preparedStatement.setString(18, form.getCycloplegicRefraction());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully inserted Cycloplegic Refraction details...");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Cycloplegic Refraction details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	@Override
	public String updateCycloplegicRefraction(PatientForm form) {
		try {
			connection = getConnection();

			String updateCycloplegicRefractionQuery = QueryMaker.UPDATE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(updateCycloplegicRefractionQuery);

			preparedStatement.setString(1, form.getDistCTCOD());
			preparedStatement.setString(2, form.getDistHTCOD());
			preparedStatement.setString(3, form.getDistAtropineOD());
			preparedStatement.setString(4, form.getDistTPlusOD());
			preparedStatement.setString(5, form.getDistCTCOS());
			preparedStatement.setString(6, form.getDistHTCOS());
			preparedStatement.setString(7, form.getDistAtropineOS());
			preparedStatement.setString(8, form.getDistTPlusOS());
			preparedStatement.setString(9, form.getNearCTCOD());
			preparedStatement.setString(10, form.getNearHTCOD());
			preparedStatement.setString(11, form.getNearAtropineOD());
			preparedStatement.setString(12, form.getNearTPlusOD());
			preparedStatement.setString(13, form.getNearCTCOS());
			preparedStatement.setString(14, form.getNearHTCOS());
			preparedStatement.setString(15, form.getNearAtropineOS());
			preparedStatement.setString(16, form.getNearTPlusOS());
			preparedStatement.setString(17, form.getCycloplegicRefraction());
			preparedStatement.setInt(18, form.getVisitID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully updated Cycloplegic Refraction details...");

			preparedStatement.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Cycloplegic Refraction details into table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	@Override
	public int retrieveIPDRoomTypeID(String roomName, int practiceID) {

		int roomTypeID = 0;

		try {

			connection = getConnection();

			String retrieveIPDRoomTypeIDQuery = QueryMaker.RETRIEVE_ROOM_TYPE_ID_BY_ROOM_NAME;

			preparedStatement = connection.prepareStatement(retrieveIPDRoomTypeIDQuery);

			preparedStatement.setString(1, roomName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				roomTypeID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			roomTypeID = 0;
		}

		return roomTypeID;
	}

	public HashMap<Integer, String> retrieveIPDTarrifChargesListByRoomType(String status, int roomTypeID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveIPDTarrifChargesListQuery = QueryMaker.RETRIEVE_IPD_TARIFF_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveIPDTarrifChargesListQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, roomTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"),
						resultSet.getString("itemName") + "===" + resultSet.getDouble("charges"));
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

	public HashMap<Integer, String> retrieveOPDChargesList(int practiceID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveOPDChargesListQuery = QueryMaker.RETRIEVE_ALL_OPDCharges;

			preparedStatement = connection.prepareStatement(retrieveOPDChargesListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"),
						resultSet.getString("chargeType") + "===" + resultSet.getDouble("charges"));
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

	@Override
	public JSONObject retrieveRoomTypeForBooking(String startDate, String endDate, int clinicID, int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String retrieveRoomTypeForBookingQuery = QueryMaker.RETRIEVE_ROOM_FOR_BOOKING;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeForBookingQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(startDate)));
			preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(endDate)));
			preparedStatement.setInt(4, practiceID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				/*
				 * int leftCount = resultSet.getInt("roomCapacity") -
				 * resultSet.getInt("bookedCount");
				 * 
				 * if (leftCount <= 0) { continue; }
				 */

				if (resultSet.getInt("bookedCount") > 0) {
					continue;
				}

				object.put("id", resultSet.getInt("id"));
				object.put("name", resultSet.getString("roomType"));
				// object.put("leftCount", leftCount);

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrorMessage", "Failed to retireve ROoms for booking.");
			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public JSONObject deleteReceiptItemByID(int receiptItemID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		String status = "error";

		try {
			connection = getConnection();

			String deleteReceiptItemByIDQuery = QueryMaker.DELETE_RECEIPT_ITEM_BY_ID;

			preparedStatement = connection.prepareStatement(deleteReceiptItemByIDQuery);

			preparedStatement.setInt(1, receiptItemID);

			preparedStatement.executeUpdate();

			status = "success";

			object.put("status", status);

			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("status", status);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
	}

	@Override
	public List<PatientForm> fetchPharmaPatient(String patientName, int practiceID, int clinicID, String searchCriteria,
			String fromDate, String toDate) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String searchPatient = QueryMaker.SEARCH_ALL_PATIENT;

			patientName = patientName.replaceAll("\\s", "%");

			preparedStatement = connection.prepareStatement(searchPatient);
			preparedStatement.setString(1, "%" + patientName + "%");
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			System.out.println("query is :::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setMiddleName(resultSet.getString("middleName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setAge(resultSet.getString("age"));
				patientForm.setGender(resultSet.getString("gender"));
				patientForm.setPatientName(patientName);
				patientForm.setRegistrationNo(resultSet.getString("regNo"));

				list.add(patientForm);

				System.out.println("Data fetched success");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving patient details based on PatientName from Patient table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	public HashMap<Integer, String> retrieveIOLTarrifChargesListByRoomType(int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveIOLTarrifChargesListQuery = QueryMaker.RETRIEVE_IOL_TARIFF_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveIOLTarrifChargesListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("tradeName") + "==="
						+ resultSet.getDouble("sellingPrice") + "===" + resultSet.getInt("id"));
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

	@Override
	public String insertReferredByDoctor(String referredBy, int practiceID) {
		// TODO Auto-generated method stub
		try {

			connection = getConnection();

			String insertDiagnosisQuery = QueryMaker.INSERT_REF_DOC;

			preparedStatement = connection.prepareStatement(insertDiagnosisQuery);

			preparedStatement.setString(1, referredBy);

			preparedStatement.setInt(2, practiceID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Referred By Doctor inserted successfully in PVReferringDoctor table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	@Override
	public int retrieveReferredByDoctorID(String refDoctor) {
		// TODO Auto-generated method stub
		int refDocID = 0;
		try {

			connection = getConnection();

			String retrieveReferredByDoctorIDQuery = QueryMaker.RETRIEVE_REF_DOC_ID;

			preparedStatement = connection.prepareStatement(retrieveReferredByDoctorIDQuery);

			preparedStatement.setString(1, refDoctor);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				refDocID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return refDocID;
	}

	@Override
	public String insertPECMedicalHistory(PatientForm form, int patientID) {
		// TODO Auto-generated method stub
		try {

			connection = getConnection();

			String insertPECMedicalHistory = QueryMaker.INSERT_PEC_MEDICAL_HISTORY;

			preparedStatement = connection.prepareStatement(insertPECMedicalHistory);

			preparedStatement.setString(1, form.getIsDiabetes());

			preparedStatement.setString(2, form.getAsthema());

			preparedStatement.setString(3, form.getHypertension());

			preparedStatement.setString(4, form.getIschemicHeartDisease());

			preparedStatement.setString(5, form.getOtherDetails());

			preparedStatement.setInt(6, patientID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Medical History inserted successfully in MedicalHistory table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	@Override
	public String updatePECMedicalHistory(PatientForm form) {
		// TODO Auto-generated method stub
		try {

			connection = getConnection();

			String updatePECMedicalHistoryQuery = QueryMaker.UPDATE_PEC_MEDICAL_HISTORY_DETAILS;

			preparedStatement = connection.prepareStatement(updatePECMedicalHistoryQuery);

			preparedStatement.setString(1, form.getIsDiabetes());

			preparedStatement.setString(2, form.getAsthema());

			preparedStatement.setString(3, form.getHypertension());

			preparedStatement.setString(4, form.getIschemicHeartDisease());

			preparedStatement.setString(5, form.getOtherDetails());

			preparedStatement.setInt(6, form.getPatientID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Medical History updated successfully in MedicalHistory table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	@Override
	public int retrieveProductID(int visitID) {
		// TODO Auto-generated method stub
		int productID = 0;
		try {

			connection = getConnection();

			String retrieveProductIDFromReceiptQuery = QueryMaker.RETRIEVE_PRODUCT_ID_FROM_RECEIPT;

			preparedStatement = connection.prepareStatement(retrieveProductIDFromReceiptQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				productID = resultSet.getInt("productID");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return productID;
	}

	@Override
	public String updateIOLChargeStock(int productID) {
		// TODO Auto-generated method stub
		try {

			connection = getConnection();

			String updateIOLChargeStockQuery = QueryMaker.UPDATE_IOL_CHARGE_STOCK;

			preparedStatement = connection.prepareStatement(updateIOLChargeStockQuery);

			preparedStatement.setInt(1, productID);

			preparedStatement.execute();

			status = "success";

			System.out.println("IOL Charge stock quantity updated successfully in Stock table.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	@Override
	public List<PatientForm> fetchPharmaPatient(int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		try {
			connection = getConnection();

			String searchPatient = QueryMaker.RETRIEVE_PATIENT_BY_PRESCRIPTION;

			preparedStatement = connection.prepareStatement(searchPatient);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setVisitDate(resultSet.getString("visitDate"));
				patientForm.setVisitID(resultSet.getInt("visitID"));

				list.add(patientForm);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured during fetching new pharma data" + e.getMessage());
		}

		return list;
	}

	public JSONObject retreivePharmaBill(int visitID, int clinicID, String clinicSuffix) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		try {
			connection1 = getConnection();

			String retreiveBill = QueryMaker.RETRIEVE_PRESCRIPTION;
			preparedStatement1 = connection1.prepareStatement(retreiveBill);

			preparedStatement1.setInt(1, visitID);
			preparedStatement1.setInt(2, clinicID);
			System.out.println(visitID);
			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("drugName", resultSet1.getString("drugName"));
				object.put("name", resultSet1.getString("categoryName"));
				object.put("numberOfDays", resultSet1.getString("numberOfDays"));
				object.put("frequency", resultSet1.getString("frequency"));
				object.put("quantity", resultSet1.getString("quantity"));
				object.put("comment", resultSet1.getString("comment"));
				object.put("rate", resultSet1.getInt("sellingPrice"));
				object.put("visitDate", dateFormat2.format(resultSet1.getDate("visitDate")));
				object.put("firstName", resultSet1.getString("firstName"));
				object.put("lastName", resultSet1.getString("lastName"));
				object.put("pId", resultSet1.getInt("id"));
				object.put("receiptNo", retrieveReceiptNo(clinicID, clinicSuffix));
				object.put("receiptDate", dateFormat.format(new Date()));

				array.add(object);

			}

			values.put("Release", array);

			resultSet1.close();
			preparedStatement1.close();

			connection1.close();

			System.out.println(values);
			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD complaint details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve IPD complaint details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public JSONObject retreivePharmaBill_bk(int visitID, int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retreiveBill = QueryMaker.RETRIEVE_PRESCRIPTION;
			preparedStatement = connection.prepareStatement(retreiveBill);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setInt(2, clinicID);
			System.out.println(visitID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("inside while");

				object = new JSONObject();

				object.put("drugName", resultSet.getString("drugName"));
				object.put("name", resultSet.getString("name"));
				object.put("numberOfDays", resultSet.getString("numberOfDays"));
				object.put("frequency", resultSet.getString("frequency"));
				object.put("quantity", resultSet.getString("quantity"));
				object.put("comment", resultSet.getString("comment"));
				object.put("rate", resultSet.getString("rate"));

				array.add(object);

			}

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();

			connection.close();

			System.out.println(values);
			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving IPD complaint details into table due to:::"
					+ exception.getMessage());

			object.put("ErrorMessage", "Failed to retireve IPD complaint details.");
			array.add(object);

			values.put("Release", array);

			return values;
		}

	}

	public List<PatientForm> fetchPharmaPatient(int clinicID, String searchName) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		int searchCheck = 0;

		if (searchName == null || searchName == "") {
			searchCheck = 1;
		} else if (searchName.isEmpty()) {
			searchCheck = 1;
		}

		try {
			connection = getConnection();

			if (searchCheck == 0) {
				String searchPharmaPatient = QueryMaker.SEARCH_PATIENT_BY_NAME_FOR_PHARMA;

				preparedStatement1 = connection.prepareStatement(searchPharmaPatient);

				preparedStatement1.setInt(1, clinicID);
				preparedStatement1.setString(2, searchName);

				resultSet1 = preparedStatement1.executeQuery();

				while (resultSet1.next()) {
					patientForm = new PatientForm();

					patientForm.setPatientID(resultSet1.getInt("id"));
					patientForm.setFirstName(resultSet1.getString("firstName"));
					patientForm.setLastName(resultSet1.getString("lastName"));
					patientForm.setVisitDate(resultSet1.getString("visitDate"));
					patientForm.setVisitID(resultSet1.getInt("visitID"));
					patientForm.setAptID(resultSet1.getInt("apptID"));

					list.add(patientForm);

				}

				resultSet1.close();
				preparedStatement1.close();
			} else {
				String searchPatient = QueryMaker.RETRIEVE_PATIENT_BY_PRESCRIPTION;

				preparedStatement = connection.prepareStatement(searchPatient);

				preparedStatement.setInt(1, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					patientForm = new PatientForm();
					patientForm.setPatientID(resultSet.getInt("id"));
					patientForm.setFirstName(resultSet.getString("firstName"));
					patientForm.setLastName(resultSet.getString("lastName"));
					patientForm.setVisitDate(resultSet.getString("visitDate"));
					patientForm.setVisitID(resultSet.getInt("visitID"));
					patientForm.setAptID(resultSet.getInt("apptID"));

					list.add(patientForm);

				}
				resultSet.close();
				preparedStatement.close();
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured during fetching new pharma data" + e.getMessage());
		}

		return list;
	}

	public List<PatientForm> fetchPharmaPatient_bk(int clinicID, String searchName) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = new PatientForm();

		int searchCheck = 0;

		if (searchName == null || searchName == "") {
			searchCheck = 1;
		} else if (searchName.isEmpty()) {
			searchCheck = 1;
		}

		try {
			connection = getConnection();

			String searchPatient = QueryMaker.RETRIEVE_PATIENT_BY_PRESCRIPTION;

			preparedStatement = connection.prepareStatement(searchPatient);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setPatientID(resultSet.getInt("id"));
				patientForm.setFirstName(resultSet.getString("firstName"));
				patientForm.setLastName(resultSet.getString("lastName"));
				patientForm.setVisitDate(resultSet.getString("visitDate"));
				patientForm.setVisitID(resultSet.getInt("visitID"));

				list.add(patientForm);

			}

			if (searchCheck == 0) {
				String searchPharmaPatient = QueryMaker.SEARCH_PATIENT_BY_NAME_FOR_PHARMA;

				preparedStatement1 = connection.prepareStatement(searchPharmaPatient);

				preparedStatement1.setInt(1, clinicID);
				preparedStatement1.setString(2, searchName);

				resultSet1 = preparedStatement1.executeQuery();

				while (resultSet1.next()) {
					patientForm = new PatientForm();

					patientForm.setPatientID(resultSet1.getInt("id"));
					patientForm.setFirstName(resultSet1.getString("firstName"));
					patientForm.setLastName(resultSet1.getString("lastName"));
					patientForm.setVisitDate(resultSet1.getString("visitDate"));
					patientForm.setVisitID(resultSet1.getInt("visitID"));

					list.add(patientForm);

				}
			}

			resultSet1.close();
			preparedStatement1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured during fetching new pharma data" + e.getMessage());
		}

		return list;
	}

	public JSONObject retrieveNAAppointmentSlots(String apptDate, int clinicID) {
		PatientForm patientForm = new PatientForm();
		JSONObject values = null;
		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		Set<Integer> break1 = new HashSet<Integer>();
		Set<Integer> break2 = new HashSet<Integer>();
		Map<Integer, Integer> apptMap = new HashMap<Integer, Integer>();
		int check = 1;

		Date currentDate = new Date();

		String currentDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

		try {
			connection = getConnection();
			String retrieveClinicTimesQuery = QueryMaker.RETRIEVE_CALENDAR_BY_CLINIC_ID;

			// System.out.println("clinicID from new method is ________" + clinicID);

			preparedStatement = connection.prepareStatement(retrieveClinicTimesQuery);

			preparedStatement.setInt(1, clinicID);
			resultSet = preparedStatement.executeQuery();

			String retrieveApptByClinicAndDateQuery = QueryMaker.RETRIEVE_PI_APPT_BY_CLINIC_AND_DATE;

			preparedStatement1 = connection.prepareStatement(retrieveApptByClinicAndDateQuery);

			preparedStatement1.setInt(1, clinicID);
			if (apptDate.substring(2, 3).equals("-")) {
				System.out.println("Insidde 1st if");
				preparedStatement1.setString(2, apptDate);

			} else {
				System.out.println("Insidde 1st else");
				preparedStatement1.setString(2, apptDate);

			}

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				int mapKey = Integer.parseInt(resultSet1.getString("apptTimeFrom").substring(0, 2));
				apptMap.put(mapKey, resultSet1.getInt("count"));
			}

			System.out.println(apptMap);

			long clinicTimeDiff = 0L;
			long break1TimeDiff = 0L;
			long break2TimeDiff = 0L;
			String breakStart1 = "";
			String breakStart2 = "";
			String breakEnd1 = "";
			String breakEnd2 = "";

			while (resultSet.next()) {

				String startTime = resultSet.getString("clinicStart");
				String endTime = resultSet.getString("clinicEnd");
				breakStart1 = resultSet.getString("breakStart1");
				breakStart2 = resultSet.getString("breakStart2");
				breakEnd1 = resultSet.getString("breakEnd1");
				breakEnd2 = resultSet.getString("breakEnd2");

				String currentTime = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());

				SimpleDateFormat hmFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat hFormat = new SimpleDateFormat("HH");
				Date clinicStart = hmFormat.parse(startTime);
				Date clinicEnd = hmFormat.parse(endTime);
				clinicTimeDiff = clinicEnd.getTime() - clinicStart.getTime();
				clinicTimeDiff = clinicTimeDiff / (60 * 60 * 1000) % 24;

				if (!(breakStart1.equals("") || breakStart1.equals(null))) {
					Date break1Start = hmFormat.parse(breakStart1);
					Date break1End = hmFormat.parse(breakEnd1);
					break1TimeDiff = break1End.getTime() - break1Start.getTime();
					break1TimeDiff = break1TimeDiff / (60 * 60 * 1000) % 24;
					int slot = 0;
					while (break1TimeDiff > 0) {
						if (slot == 0) {
							slot = Integer.parseInt(breakStart1.substring(0, 2));
							if (breakStart1.substring(3, 5).equals("00")) {
								break1.add(slot);
							}
						} else {
							break1.add(slot);
						}
						slot += 1;
						break1TimeDiff -= 1;
					}
					System.out.println(break1);
				}

				if (!(breakStart2.equals("") || breakStart2.equals(null))) {
					Date break2Start = hmFormat.parse(breakStart2);
					Date break2End = hmFormat.parse(breakEnd2);
					break2TimeDiff = break2End.getTime() - break2Start.getTime();
					break2TimeDiff = break2TimeDiff / (60 * 60 * 1000) % 24;
					int slot = 0;
					while (break2TimeDiff > 0) {
						if (slot == 0) {
							slot = Integer.parseInt(breakStart2.substring(0, 2));
							if (breakStart2.substring(3, 5).equals("00")) {
								break2.add(slot);
							}
						} else {
							break2.add(slot);
						}
						slot += 1;
						break2TimeDiff -= 1;
					}
				}

				int slot = Integer.parseInt(startTime.substring(0, 2));
				while (clinicTimeDiff > 0) {

					// System.out.println(slot+"..."+currentTime+"...."+clinicTimeDiff);

					if (Integer.parseInt(currentTime) > slot && currentDateFormat.equals(apptDate)) {
						clinicTimeDiff -= 1;
						slot += 1;
						continue;
					}

					if (break1.contains(slot) || break2.contains(slot)) {
						clinicTimeDiff -= 1;
						slot += 1;
						continue;
					}

					if (apptMap.containsKey(slot)) {

						if (apptMap.get(slot) >= 6) {

							/*
							 * if(break1.contains(slot) || break2.contains(slot)) { clinicTimeDiff -= 1;
							 * slot += 1; continue; }
							 */
							clinicTimeDiff -= 1;

							slot += 1;

						} else {

							object = new JSONObject();

							object.put("slot", slot);

							clinicTimeDiff -= 1;

							slot += 1;

							check++;

							array.add(object);

							values.put("Release", array);
						}

					} else {
						object = new JSONObject();

						object.put("slot", slot);

						clinicTimeDiff -= 1;

						slot += 1;

						check++;

						array.add(object);

						values.put("Release", array);
					}

				}

			}

			resultSet.close();
			preparedStatement.close();
			resultSet1.close();
			preparedStatement1.close();
			connection.close();

			if (check == 1) {

				object = new JSONObject();

				object.put("check", 0);

				array.add(object);

				values.put("Release", array);
			}

		} catch (Exception e) {
			e.printStackTrace();

			object.put("ErrMsg", "Error occured while apoointment slots.");

			array.add(object);

			values.put("Release", array);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PatientDAOInf#retrieveAppointmentNumber()
	 */
	public int retrieveAppointmentNumber(int clinicID, String apptDate, String apptFromTime) {
		int aptNumber = 0;

		/*
		 * Getting current date and converting it into DB format
		 */
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int check = 0;

		try {
			connection = getConnection();

			String retrieveAppointmentNumberQuery = QueryMaker.RETRIEVE_APPOINTMENT_NUMBER_BY_DATE_AND_TIME;

			preparedStatement = connection.prepareStatement(retrieveAppointmentNumberQuery);

			preparedStatement.setString(1, dateFormat.format(date));
			preparedStatement.setString(2, apptFromTime);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				aptNumber = resultSet.getInt("apptNumber") + 1;
			}

			if (check == 0) {

				resultSet.close();
				preparedStatement.close();

				// Retrieving clinic time and appt from time difference and then calculating
				// appointment number accordingly

				String query = QueryMaker.RETRIEVE_CALENDAR_TIME_DIFF_BY_CLINIC_ID;

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setString(1, apptFromTime);
				preparedStatement.setInt(2, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					int hourDiff = resultSet.getInt("hourDiff");

					aptNumber = 6 * hourDiff;
				}

				resultSet.close();
				preparedStatement.close();
				connection.close();

			} else {

				resultSet.close();
				preparedStatement.close();
				connection.close();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Appointment number from Appointment table due to:::"
					+ exception.getMessage());
		}
		return aptNumber;
	}

	public void updateDoneAppointmentStatus(int patientID, int visitID) {

		try {

			connection = getConnection();

			String updateDoneAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS_FOR_PATIENT;

			preparedStatement = connection.prepareStatement(updateDoneAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.DONE);
			preparedStatement.setInt(2, visitID);
			preparedStatement.setInt(3, patientID);

			preparedStatement.executeUpdate();

			System.out.println("Appointment Status successfully changed to Done");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void updateConsultationFlagInVisit(int visitID, int isConsultationDone) {

		try {

			connection = getConnection();

			String updateConsultationFlagInVisitQuery = QueryMaker.UPDATE_IS_CONSULTATION_IS_VISIT;

			preparedStatement = connection.prepareStatement(updateConsultationFlagInVisitQuery);

			preparedStatement.setInt(1, isConsultationDone);
			preparedStatement.setInt(2, visitID);

			preparedStatement.executeUpdate();

			System.out.println("Consultation done flag updated successfully into Visit table");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public void insertVisitEditHistoryDetails(PatientForm patientForm) {
		try {

			connection = getConnection();

			String insertVisitEditHistoryDetailsQuery = QueryMaker.INSERT_VISIT_EDIT_HISTORY;

			preparedStatement = connection.prepareStatement(insertVisitEditHistoryDetailsQuery);

			preparedStatement.setString(1, patientForm.getAction());
			preparedStatement.setInt(2, patientForm.getUserID());
			preparedStatement.setInt(3, patientForm.getVisitID());
			preparedStatement.setString(4, patientForm.getVisitType());
			preparedStatement.setInt(5, patientForm.getClinicID());

			preparedStatement.execute();

			System.out.println("Visit edit history added");

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public String retrieveVisitLastEdittedByDetails(int visitID, String careType) {
		String historyData = "";
		try {

			connection = getConnection();

			String retrieveVisitLastEdittedByDetailsQuery = QueryMaker.RETRIEVE_VISIT_EDIT_HISTORY;

			preparedStatement = connection.prepareStatement(retrieveVisitLastEdittedByDetailsQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, careType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				historyData = "Details last " + resultSet.getString("action") + "ed by <b>Dr. "
						+ resultSet.getString("userName") + "</b> at <b>" + resultSet.getString("dateTimeFMT")
						+ "</b>.";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return historyData;
	}

	@Override
	public boolean verifyOTPVerified(String mobile, int clinicID) {
		boolean status = false;

		try {

			connection = getConnection();

			String verifyOTPVerifiedQuery = QueryMaker.RETRIEVE_OTP;

			preparedStatement = connection.prepareStatement(verifyOTPVerifiedQuery);

			preparedStatement.setInt(3, clinicID);
			preparedStatement.setString(1, mobile);
			preparedStatement.setString(2, ActivityStatus.VERIFIED);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String insertOTPDetails(String OTP, String mobileNo, int clinicID) {
		// TODO Auto-generated method stub

		try {

			connection = getConnection();

			// INSERT INTO OTPVerification (OTP, mobileNo, status, clinicID, createdAt)
			// VALUES (?,?,?,?,NOW())

			String insertOTPDetailsQuery = QueryMaker.INSERT_OTP;

			preparedStatement = connection.prepareStatement(insertOTPDetailsQuery);

			preparedStatement.setString(1, OTP);
			preparedStatement.setInt(4, clinicID);
			preparedStatement.setString(2, mobileNo);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;

	}

	@Override
	public String validateOTP(String mobile, int clinicID) {
		// TODO Auto-generated method stub

		try {

			connection = getConnection();

			// INSERT INTO OTPVerification (OTP, mobileNo, status, clinicID, createdAt)
			// VALUES (?,?,?,?,NOW())

			String validateOTPQuery = QueryMaker.UPDATE_OTP;

			preparedStatement = connection.prepareStatement(validateOTPQuery);

			preparedStatement.setInt(3, clinicID);
			preparedStatement.setString(2, mobile);
			preparedStatement.setString(1, ActivityStatus.VERIFIED);

			preparedStatement.executeUpdate();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;

	}

	public List<PatientForm> retrieveProceduresList(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrieveProcedureListQuery = QueryMaker.RETREIVE_PROCEDURE_LIST;

			preparedStatement = connection.prepareStatement(retrieveProcedureListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setProcedureID(resultSet.getInt("id"));
				patientForm.setProcedure(resultSet.getString("procedureName"));
				patientForm.setVisitID(visitID);
				patientForm.setCount("" + count + "");

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("Procedure test list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Procedure list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public List<PatientForm> retrieveDiagnosticList(int visitID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		int count = 1;

		try {

			connection = getConnection();

			String retrieveDiagnosticListQuery = QueryMaker.RETREIVE_DIAGNOSTIC_LIST;

			preparedStatement = connection.prepareStatement(retrieveDiagnosticListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientForm = new PatientForm();

				patientForm.setDiagnosticID(resultSet.getInt("id"));
				patientForm.setDiagnostic(resultSet.getString("diagnostic"));
				patientForm.setVisitID(visitID);
				patientForm.setCount("" + count + "");

				list.add(patientForm);

				count++;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			System.out.println("Diagnostic test list retrieved succesfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Diagnostic list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public String insertProcedureDetails(String procedure, int visitID) {
		try {

			connection = getConnection();

			String insertProcedureDetailsQuery = QueryMaker.INSERT_PROCEDURE_DETAILS;

			preparedStatement = connection.prepareStatement(insertProcedureDetailsQuery);

			preparedStatement.setString(1, procedure);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Procedure Details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String insertDiagnosticDetails(String diagnostic, int visitID) {

		try {

			connection = getConnection();

			String insertDiagnosticDetailsQuery = QueryMaker.INSERT_DIAGNOSTIC_DETAILS;

			preparedStatement = connection.prepareStatement(insertDiagnosticDetailsQuery);

			preparedStatement.setString(1, diagnostic);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, visitID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Diagnostic Details inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	@Override
	public List<String> retrieveTestTypeForVisitByVisitID(int visitID) {

		List<String> testlist = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrieveTestTypeForVisitByVisitIDQuery = QueryMaker.RETRIEVE_TEST_TYPE_FOR_VISIT;

			preparedStatement = connection.prepareStatement(retrieveTestTypeForVisitByVisitIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				testlist.add(resultSet.getString("testType"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return testlist;
	}

	@Override
	public String insertVisitTemplateData(String templateData, int temlpateID, int visitID, int clinicianID,
			int investigationID) {
		try {

			connection = getConnection();

			String insertVisitTemplateDataQuery = QueryMaker.INSERT_VISIT_TEMPLATE_DATA;

			preparedStatement = connection.prepareStatement(insertVisitTemplateDataQuery);

			preparedStatement.setString(1, templateData);
			preparedStatement.setInt(3, visitID);
			preparedStatement.setInt(2, temlpateID);
			preparedStatement.setInt(4, clinicianID);
			preparedStatement.setInt(5, investigationID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Visit template data inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateVisitTemplateData(String templateData, int temlpateID, int visitID, int clinicianID,
			int investigationID, int visitTemplateID) {
		try {

			connection = getConnection();

			String updateVisitTemplateDataQuery = QueryMaker.UPDATE_VISIT_TEMPLATE_DATA;

			preparedStatement = connection.prepareStatement(updateVisitTemplateDataQuery);

			preparedStatement.setString(1, templateData);
			preparedStatement.setInt(2, clinicianID);
			preparedStatement.setInt(3, temlpateID);
			preparedStatement.setInt(4, visitTemplateID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Visit template data updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateBDPPatient(PatientForm patientForm) {

		try {
			connection = getConnection();

			String updatePatientDetailQuery = QueryMaker.UPDATE_BDP_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(updatePatientDetailQuery);

			preparedStatement.setString(1, patientForm.getAge());
			preparedStatement.setString(2, patientForm.getMobile());
			preparedStatement.setString(3, patientForm.getEmailID());
			preparedStatement.setString(4, patientForm.getAddress());
			preparedStatement.setInt(5, patientForm.getPatientID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("patient details udpated successfully..");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating patient detail into table due to:::" + exception.getMessage());
			status = "error";
		}

		return status;
	}

	@Override
	public List<PatientForm> retrieveVisitTemplateData(int visitID) {

		List<PatientForm> list = new ArrayList<PatientForm>();

		PatientForm form = null;

		try {

			connection = getConnection();

			String retrieveVisitTemplateDataQuery = QueryMaker.RETRIEVE_VISIT_TEMPLATE_DATA;

			preparedStatement = connection.prepareStatement(retrieveVisitTemplateDataQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PatientForm();

				form.setTemplate(resultSet.getString("templateData"));
				form.setClinicianName(resultSet.getString("clinicianName"));
				form.setTemplateName(resultSet.getString("templateName"));
				form.setTemplateType(resultSet.getString("templateType"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PatientForm> retrieveAllOPDVisitDetails(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm patientForm = null;

		/*
		 * To covert date from database into DD-MM-YYYY
		 */
		SimpleDateFormat databaseDate = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeDisplayed = new SimpleDateFormat("dd-MM-yyyy");

		Date date = new Date();

		try {
			connection = getConnection();

			String retrieveLastOPDVisitDetailsQuery = QueryMaker.RETRIEVE_ALL_VISIT__FOR_PATIENTLIST;

			preparedStatement = connection.prepareStatement(retrieveLastOPDVisitDetailsQuery);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patientForm = new PatientForm();

				/*
				 * Parsing db date into correct Date format
				 */
				date = databaseDate.parse(resultSet.getString("visitDate"));

				patientForm.setVisitNumber(resultSet.getInt("visitNumber"));
				// patientForm.setCareType(resultSet.getString("careType"));
				patientForm.setVisitType(resultSet.getString("visitType"));
				patientForm.setVisitDate(dateToBeDisplayed.format(date));
				patientForm.setDiagnosis(resultSet.getString("diagnosis"));

				if (resultSet.getString("visitTimeFrom") == null || resultSet.getString("visitTimeFrom") == "") {
					patientForm.setVisitFromTime("");
				} else if (resultSet.getString("visitTimeFrom").isEmpty()) {
					patientForm.setVisitFromTime("");
				} else {
					patientForm.setVisitFromTime(resultSet.getString("visitTimeFrom"));
				}

				if (resultSet.getString("visitTimeTo") == null || resultSet.getString("visitTimeTo") == "") {
					patientForm.setVisitToTime("");
				} else if (resultSet.getString("visitTimeTo").isEmpty()) {
					patientForm.setVisitToTime("");
				} else {
					patientForm.setVisitToTime(resultSet.getString("visitTimeTo"));
				}

				patientForm.setMedicalNotes(resultSet.getString("visitNote"));

				list.add(patientForm);
			}

			System.out.println("Successfully retrieved last OPD visit details");

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving last entred OPD visit details from Visit table due to:::"
							+ exception.getMessage());
		}
		return list;
	}

	@Override
	public PatientForm retrieveVisitTemplateDataByInvestigationID(int investigationID) {

		PatientForm form = new PatientForm();

		try {

			connection = getConnection();

			String retrieveVisitTemplateDataQuery = QueryMaker.RETRIEVE_VISIT_TEMPLATE_DATA_BY_INVEST_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitTemplateDataQuery);

			preparedStatement.setInt(1, investigationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setTemplate(resultSet.getString("templateData"));
				form.setClinicianID(resultSet.getInt("clinicianID"));
				form.setTemplateID(resultSet.getInt("templateID"));
				form.setInvestigationID(investigationID);
				form.setVisitTemplateID(resultSet.getInt("id"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return form;
	}

	/**
	 * Verifies if a pathology test is available for a specific visit.
	 *
	 * @param visitID the ID of the visit to check for a pathology test
	 * @return true if a pathology test is available for the visit, false otherwise
	 */
	@Override
	public boolean verifyPathologyTestAvailableForVisit(int visitID) {

		boolean check = false;

		try {
			connection = getConnection();
			String verifyPathologyTestAvailableForVisitQuery = QueryMaker.VERIFY_PATHOLOGY_TEST_AVAILABLE_FOR_VISIT;
			preparedStatement = connection.prepareStatement(verifyPathologyTestAvailableForVisitQuery);
			preparedStatement.setInt(1, visitID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}
	
	public String updateDispatchedDetails(int investigationID) {
		
		try {
			connection = getConnection();
			
			String updateIsDispatchedStatusQuery = QueryMaker.UPDATE_IS_DISPATCHED_STATUS;
			
			preparedStatement = connection.prepareStatement(updateIsDispatchedStatusQuery);
			
			preparedStatement.setBoolean(1, ActivityStatus.TRUE);
			preparedStatement.setInt(2 , investigationID);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
			status = "success";
			
		} catch (Exception exception) {
			exception.printStackTrace();
			
			status = "error";
		}
		
		return status;
		
	}
	
	public int[] retrieveIsDiapatchedDetails(int visitID) {
		
		int []ids = null;
		
		try {
			connection = getConnection();
			String retrieveIsDispatchDetailsQuery = QueryMaker.RETRIEVE_IS_DISPATCHED_DETAILS_BY_VISITID;
			preparedStatement = connection.prepareStatement(retrieveIsDispatchDetailsQuery);
			
			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.LabTest);
			
			resultSet = preparedStatement.executeQuery();
			int i = 0;
			while(resultSet.next()) {
				ids[i] = resultSet.getInt("id");
				i++;
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return ids;
	}
	
	public String insertIsNormalValue(int visitID, String textNormal) {
		
		try {
			
			connection = getConnection();
			String insertIsNormalValue = QueryMaker.INSERT_IS_NORMAL_VALUE;
			preparedStatement = connection.prepareStatement(insertIsNormalValue);
			
			preparedStatement.setInt(2, visitID);
			
			if(textNormal.equalsIgnoreCase("normal")) {
				preparedStatement.setBoolean(1, ActivityStatus.TRUE);
			} else if(textNormal.equalsIgnoreCase("abnormal")) { 
				preparedStatement.setBoolean(1, ActivityStatus.FALSE);
			} else {
				preparedStatement.setBoolean(1, (Boolean) null);
			}
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "error";
		}
		
		return status;
		
	}
	
	public Boolean retrieveNormalBoolVal(int visitID) {
		Boolean isNormal = null;
		try {
			connection = getConnection();
			String retrieveNormalBoolValue = QueryMaker.RETRIEVE_NORMAL_BOOL_VALUE;
			
			preparedStatement = connection.prepareStatement(retrieveNormalBoolValue);
			preparedStatement.setInt(1, visitID);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				isNormal = resultSet.getBoolean("isNormal");
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isNormal;
		
	}
	
	public int retrieveTestIDByTestName(String testName, int practiceID) {
		int testID = 0;
		
		try {
			
			connection = getConnection();
			String retrieveTestIDQuery = QueryMaker.RETRIEVE_TEST_ID_BY_TEST_NAME;
			preparedStatement = connection.prepareStatement(retrieveTestIDQuery);
			
			preparedStatement.setString(1, testName);
			preparedStatement.setInt(2, practiceID);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				testID = resultSet.getInt("id");
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return testID;
	}
	
	public String retrieveGroupRemark(String groupName) {
		String remark = "";
		
		try {
			connection = getConnection();
			
			String getGroupRemark = "SELECT groupRemark FROM groupTest WHERE groupName = ?";
			preparedStatement = connection.prepareStatement(getGroupRemark);
			
			preparedStatement.setString(1, groupName);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				remark = resultSet.getString("groupRemark");
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return remark;
	}
	
	public TreeMap <String, TreeMap <String, TreeMap <Integer, PatientForm>>>  manupulateSubGroupData(List<PatientForm> labTestListNew, LoginForm loginForm) {
		
		TreeMap<String, TreeMap<String, TreeMap<Integer, PatientForm>>> subGroups = new TreeMap<>();
		PatientDAOInf patientDAOInf = new PatientDAOImpl();
		
		HashMap<String, Integer> testIDs = new HashMap<>();
		List<String> testNames = new ArrayList<>();

		for (PatientForm form1 : labTestListNew) {
			testNames.add(form1.getCBCProfileTest());
		}

		for (String testName : testNames) {
			int testID = patientDAOInf.retrieveTestIDByTestName(testName, loginForm.getPracticeID());
			testIDs.put(testName, testID);
		}

		for (PatientForm form1 : labTestListNew) {
			if (form1.getSubGroup() != null && !form1.getSubGroup().equals("null")
					&& !form1.getSubGroup().trim().isEmpty()) {
				if (form1.getIsGroup() == 1) {
					if (subGroups.containsKey(form1.getGroupName().trim())) {
						TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = subGroups
								.get(form1.getGroupName());
						if (subGroupList.containsKey(form1.getSubGroup().trim())) {
							TreeMap<Integer, PatientForm> labTestIDMap = subGroupList.get(form1.getSubGroup().trim());
							labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()) , form1);
						} else {
							TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
							labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
							subGroupList.put(form1.getSubGroup().trim(), labTestIDMap);
						}
					} else {
						TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = new TreeMap<>();
						TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
						labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
						subGroupList.put(form1.getSubGroup().trim(), labTestIDMap);
						subGroups.put(form1.getGroupName().trim(), subGroupList);
					}
				} 
			} else {
			    if (form1.getIsGroup() == 1) {
			        if (subGroups.containsKey(form1.getGroupName().trim())) {
			        	TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = subGroups.get(form1.getGroupName().trim());
			            if (subGroupList.containsKey("noSubGroup")) {
			            	TreeMap<Integer, PatientForm> labTestIDMap = subGroupList.get("noSubGroup");
			                labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			            } else {
			            	TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
			                labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			                subGroupList.put("noSubGroup", labTestIDMap);
			            }
			        } else {
			        	TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = new TreeMap<>();
			            TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
			            labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			            subGroupList.put("noSubGroup", labTestIDMap);
			            subGroups.put(form1.getGroupName().trim(), subGroupList);
			        }
			    }
			}
			
			if (form1.getIsGroup() == 0) {
			    if (subGroups.containsKey("noGroup")) {
			    	TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = subGroups.get("noGroup");
			        if (subGroupList.containsKey(form1.getSubGroup().trim())) {
			        	TreeMap<Integer, PatientForm> labTestIDMap = subGroupList.get(form1.getSubGroup().trim());
			            labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			        } else {
			        	TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
			            labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			            subGroupList.put(form1.getSubGroup(), labTestIDMap);
			        }
			    } else {
			    	TreeMap<String, TreeMap<Integer, PatientForm>> subGroupList = new TreeMap<>();
			        TreeMap<Integer, PatientForm> labTestIDMap = new TreeMap<>();
			        labTestIDMap.put(testIDs.get(form1.getCBCProfileTest()), form1);
			        if (form1.getSubGroup() != null && !form1.getSubGroup().equals("null") && !form1.getSubGroup().trim().isEmpty()) {
			            subGroupList.put(form1.getSubGroup(), labTestIDMap);
			        } else {
			            subGroupList.put("noSubGroup", labTestIDMap);
			        }
			        subGroups.put("noGroup", subGroupList);
			    }
			}	
		}
		
        for (Map.Entry<String, TreeMap<String, TreeMap<Integer, PatientForm>>> outerEntry : subGroups.entrySet()) {
            String outerKey = outerEntry.getKey();
            TreeMap<String, TreeMap<Integer, PatientForm>> secondLevelMap = outerEntry.getValue();

            TreeMap<String, TreeMap<Integer, PatientForm>> updatedSecondLevelMap = new TreeMap<>();
            
            String newKey = "";
            for (Map.Entry<String, TreeMap<Integer, PatientForm>> innerEntry : secondLevelMap.entrySet()) {
                String innerKey = innerEntry.getKey();
                TreeMap<Integer, PatientForm> valueMap = innerEntry.getValue();
                TreeMap<Integer, PatientForm> updatedValueMap = new TreeMap<>();
                for (Map.Entry<Integer, PatientForm> valueEntry : valueMap.entrySet()) {
                    Integer oldKey = valueEntry.getKey();
                    PatientForm patientForm = valueEntry.getValue();
                    newKey = oldKey + "_" + innerKey;
                    updatedValueMap.put(oldKey, patientForm); 
                }
                if(!innerKey.equalsIgnoreCase("noSubGroup")) {
                	updatedSecondLevelMap.put(newKey, updatedValueMap);
                } else {
                	updatedSecondLevelMap.put(innerKey, updatedValueMap);
                }
            }
            subGroups.put(outerKey, updatedSecondLevelMap);
        }			
		return subGroups;
	}

}
