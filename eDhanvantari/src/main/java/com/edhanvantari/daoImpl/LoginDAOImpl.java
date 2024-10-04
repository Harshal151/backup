package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.RegistrationDAOinf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.EncDescUtil;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;

public class LoginDAOImpl extends DAOConnection implements LoginDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connection1 = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	String status = "error";

	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	RegistrationDAOinf registrationDAOinf = null;

	ConfigurationUtil configurationUtil = null;

	public String verifyUserCredentials(LoginForm loginForm) {
		try {

			if (retrieveLockedUser(loginForm.getUsername()).equals(ActivityStatus.LOCKED)) {
				status = "locked";
				return status;
			} else {
				ResultSet resultSet1 = null;

				connection = getConnection();

				String verifyUserCredentialsQuery = QueryMaker.RETRIEVE_USER_CREDENTIALS;

				preparedStatement = connection.prepareStatement(verifyUserCredentialsQuery);
				preparedStatement.setString(1, ActivityStatus.ACTIVE);

				resultSet1 = preparedStatement.executeQuery();

				while (resultSet1.next()) {

					/*
					 * decrypt the password from database and store it in one string variable
					 */
					String decryptedPassword = null;
					decryptedPassword = EncDescUtil.DecryptText(resultSet1.getString("password").trim());

					// System.out.println("Decrypted password is::::::" + decryptedPassword);

					/*
					 * System.out.println( "User credentials are:::::" + loginForm.getUsername() +
					 * " " + loginForm.getPassword());
					 */

					if ((loginForm.getUsername().equals("") || loginForm.getUsername().equals(null))
							&& (loginForm.getPassword().equals("") || loginForm.getPassword().equals(null))) {
						status = "input";
						return status;
					} else if (((resultSet1.getString("username")).equals(loginForm.getUsername()))
							&& (decryptedPassword.equals(loginForm.getPassword()))) {
						// System.out.println("Credetials Matched.....");
						status = "success";
						return status;
					}
					if (((resultSet1.getString("username")).equals(loginForm.getUsername()))
							&& (decryptedPassword != (loginForm.getPassword()))) {
						/*
						 * Retrieving userID from User table
						 */
						registrationDAOinf = new RegistrationDAOImpl();
						int userId = registrationDAOinf.retrieveUserIDByUsername(loginForm.getUsername());

						// Setting user id into session
						loginForm.setUserID(resultSet1.getInt("id"));
						String result = null;

						if (userId != 0) {
							// System.out.println("User id from User table for login attempt ::: " +
							// userId);

							/*
							 * Verifying whether username that entered by user and username from
							 * loginAttempt table matches
							 */
							int userIDForLoginAttempt = retrieveUserIDFromLoginAttempt(userId);

							// System.out.println("UserID from LoginAttempt is ::" + userIDForLoginAttempt);

							if (userId == userIDForLoginAttempt) {
								System.out.println("updating LoginAttempt table");

								/*
								 * Retrieving date from LoginAttempt. If current date is equal to loginAttempt
								 * date then update the record in loginAttempt table with incremented
								 * attemptCounter value . If date value from login attempt table is not equal to
								 * current date or time then insert new value into Login attempt table with same
								 * user ID. And If login attempt date value matches current date and time value,
								 * then after 10 min of time interval update the counterAttempt value of that
								 * userID to 1
								 */
								String loginAttemptDate = retrieveLoginAttemptTimestamp(userId);

								String[] timeStampArray = loginAttemptDate.split(" ");

								String date = timeStampArray[0];

								String time = timeStampArray[1];

								// Splitting time in order to get hour and
								// minutes
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

								// Comparing current date time with login
								// attempt date time
								int timeCheck = date.compareTo(currDate);

								// System.out.println("date compare check is :: " + timeCheck);

								// adding 10 to current minute in order to
								// get interval of 10 minute

								if (timeCheck == 0 && hour.equals(currHour)
										&& Integer.parseInt(currMin) >= Integer.parseInt(minute)) {

									extraMinutes = 0;
									check = false;

									int tempMinute = Integer.parseInt(minute) + 10;

									// System.out.println("Temp minute with 10 minute interval is :::: " +
									// tempMinute);

									if (tempMinute > Integer.parseInt(currMin)) {

										if (tempMinute >= 60) {

											System.out.println("temp minute is greater than 60");

											check = true;

											// finding extra minutes by
											// substracting 60 from tempMinute
											extraMinutes = tempMinute - 60;
										}

										/*
										 * updating attempt counter value of user by incrementing it by 1
										 */

										// Retrieving max login attempt value
										// from
										// ClinicConfiguration table based on
										// user name

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										// System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) {
											// incrementing counter by 1
											counter++;
											// System.out.println("counter value in if::: " + counter);

											/*
											 * updating attemptCounter from LoginAttempt table
											 */
											updateLoginAttempt(userId, counter, loginAttemptDate);

											status = "input";

										} else {
											// System.out.println("Login attempt exceeded the limit.");
											// System.out.println("counter value in else::: " + counter);

											counter = 1;

											/*
											 * Updating activity status of LoginAttempt to Disable for that particular
											 * userID
											 */
											updateLoginAttemptStatus(userId);

											/*
											 * Updating activityStatus of User From AppUser table to Locked
											 */
											updateUserStatus(userId);
											System.out.println(
													"User is locked by updating his/her acticityStatus to Locked");

											status = "login";

										}

									} else {

										/*
										 * Setting static variables extraMinutes and check to its default value
										 */
										extraMinutes = 0;
										check = false;

										// System.out.println("inserting new value with counter as 1");

										/*
										 * Updating activity status of LoginAttempt to Disable for that particular
										 * userID
										 */
										updateLoginAttemptStatus(userId);

										counter = 1;

										/*
										 * Inserting new record into LoginAttempt with counter value as 1
										 */
										insertLoginAttempt(userId, counter, loginForm.getUsername().trim());

										status = "input";

									}

								} else if (check) {

									// System.out.println("Inside check trueee");
									/*
									 * Inside this loop means, if current time is 12:55, so after adding 10 minute
									 * to it, the time becomes 13.05. If this is the case, then update login attempt
									 * till the converted time exceeds
									 */
									if ((Integer.parseInt(currMin) > extraMinutes)) {

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										// System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) {
											// incrementing counter by 1
											counter++;
											System.out.println("counter value in if::: " + counter);

											/*
											 * updating attemptCounter from LoginAttempt table
											 */
											updateLoginAttempt(userId, counter, loginAttemptDate);

											status = "input";

										} else {
											/*
											 * System.out.println("Login attempt exceeded the limit.");
											 * System.out.println("counter value in else::: " + counter);
											 */

											counter = 1;

											/*
											 * Updating activity status of LoginAttempt to Disable for that particular
											 * userID
											 */
											updateLoginAttemptStatus(userId);

											/*
											 * Updating activityStatus of User From AppUser table to Locked
											 */
											updateUserStatus(userId);
											/*
											 * System.out.println(
											 * "User is locked by updating his/her acticityStatus to Locked");
											 */

											status = "login";

										}

									} else if ((Integer.parseInt(currMin) <= extraMinutes)) {

										if ((Integer.parseInt(currMin) == extraMinutes)) {
											check = false;
										}

										int MAX_LOGIN_ATTEMPT = retrieveInvalideAttempt(
												resultSet1.getString("username"));

										// System.out.println("MAX_LOGIN_ATTEMPT ::: " + MAX_LOGIN_ATTEMPT);

										if (MAX_LOGIN_ATTEMPT > counter) {
											// incrementing counter by 1
											counter++;
											// System.out.println("counter value in if::: " + counter);

											/*
											 * updating attemptCounter from LoginAttempt table
											 */
											updateLoginAttempt(userId, counter, loginAttemptDate);

											status = "input";

										} else {
											/*
											 * System.out.println("Login attempt exceeded the limit.");
											 * System.out.println("counter value in else::: " + counter);
											 */

											counter = 1;

											/*
											 * Updating activity status of LoginAttempt to Disable for that particular
											 * userID
											 */
											updateLoginAttemptStatus(userId);

											/*
											 * Updating activityStatus of User From AppUser table to Locked
											 */
											updateUserStatus(userId);
											/*
											 * System.out.println(
											 * "User is locked by updating his/her acticityStatus to Locked");
											 */

											status = "login";

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
										updateLoginAttemptStatus(userId);

										counter = 1;

										// inserting new value with same userID
										insertLoginAttempt(userId, counter, loginForm.getUsername().trim());

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
									updateLoginAttemptStatus(userId);

									counter = 1;

									// inserting new value with same userID
									insertLoginAttempt(userId, counter, loginForm.getUsername().trim());

									status = "input";
								}

							} else {

								/*
								 * Setting static variables extraMinutes and check to its default value
								 */
								extraMinutes = 0;
								check = false;

								// System.out.println("inserting into LoginAttempt table");

								counter = 1;

								/*
								 * Updating activity status of LoginAttempt to Disable for that particular
								 * userID
								 */
								updateLoginAttemptStatus(userId);

								/*
								 * Inserting values into LoginAttempt table
								 */
								insertLoginAttempt(userId, counter, loginForm.getUsername().trim());

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

	@Override
	public LoginForm getUserPatientDetail(String username) {
		LoginForm form = new LoginForm();

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		try {
			System.out.println("USERNAME ::" + username);
			connection = getConnection();

			String getUserPatientDetailQuery = QueryMaker.RETRIEVE_USER_PATIENT_DETAILS;

			// String retrievePatientVisitListQuery =
			// QueryMaker.RETREIVE_PATIENT_VISIT_LIST;

			preparedStatement = connection.prepareStatement(getUserPatientDetailQuery);
			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int patientID = resultSet.getInt("id");
				form.setPatientID(patientID);
				form.setFirstName(resultSet.getString("firstName"));
				form.setLastName(resultSet.getString("lastName"));
				form.setMiddleName(resultSet.getString("middleName"));
				int clinicID = resultSet.getInt("clinicID");
				form.setClinicID(clinicID);
				int practiceID = resultSet.getInt("practiceID");
				form.setPracticeID(practiceID);
				form.setPatientJSPName(retrievePatientFormName(clinicID));
				form.setClinicSuffix(retrieveClinicSuffix(clinicID));
				form.setOPDJSPName(retrieveOPDFormName(clinicID));

				System.out.println("CLINICID GSABZDBH::" + clinicID + "     ..." + patientID);
				System.out.println("PRACTICEID GSABZDBH::" + practiceID);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Logged in Patient details from Patient due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return form;
	}

	/**
	 * 
	 * @param userID
	 * @return username
	 */
	public int retrieveUserIDFromLoginAttempt(int userID) {
		int userId = 0;
		try {
			connection = getConnection();

			String retrieveUserIDFromLoginAttemptQuery = QueryMaker.RETRIEVE_USER_ID_FROM_LOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(retrieveUserIDFromLoginAttemptQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userId = resultSet.getInt("userID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving userID from LoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return userId;
	}

	/**
	 * 
	 * @param userID
	 * @param counter
	 * @return result
	 */
	public String insertLoginAttempt(int userID, int counter, String username) {
		String result = "error";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date today = new Date();

		try {
			connection = getConnection();

			String insertLoginAttemptQuery = QueryMaker.INSERT_INTO_LOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(insertLoginAttemptQuery);

			preparedStatement.setInt(1, counter);
			preparedStatement.setInt(2, userID);
			preparedStatement.setString(3, sdf.format(today));
			preparedStatement.setString(4, ActivityStatus.ENABLE);

			preparedStatement.execute();

			result = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting into LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param userID
	 * @param counter
	 * @return result
	 */
	public String updateLoginAttempt(int userID, int counter, String loginAttemptDate) {

		String result = "error";
		try {
			connection = getConnection();

			String updateLoginAttemptQuery = QueryMaker.UPDATE_LOGIN_ATTEMPT;

			preparedStatement = connection.prepareStatement(updateLoginAttemptQuery);

			preparedStatement.setInt(1, counter);
			preparedStatement.setInt(2, userID);
			preparedStatement.setString(3, loginAttemptDate);

			preparedStatement.executeUpdate();

			result = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating LoginAttempt table due to:::" + exception.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param userId
	 * @return result
	 */
	public String updateUserStatus(int userId) {
		String result = "error";
		try {
			connection = getConnection();

			String updateUserStatusQuery = QueryMaker.UPDATE_USER_STATUS;

			preparedStatement = connection.prepareStatement(updateUserStatusQuery);
			preparedStatement.setString(1, ActivityStatus.LOCKED);
			preparedStatement.setInt(2, userId);

			preparedStatement.executeUpdate();

			result = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating status to Locked from LoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param username
	 * @return userStatus
	 */
	public String retrieveLockedUser(String username) {
		String userStatus = "dummy";
		try {
			connection = getConnection();

			String retrieveLockedUserQuery = QueryMaker.RETRIVE_LOCKED_USER_STATUS;

			preparedStatement = connection.prepareStatement(retrieveLockedUserQuery);
			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userStatus = resultSet.getString("activityStatus");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying user status as Locked from User table due to:::"
					+ exception.getMessage());
		}
		return userStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#insertAudit(java.lang.String,
	 * java.lang.String)
	 */
	public String insertAudit(String ipAddress, String actionName, int userID) {
		try {
			connection = getConnection();

			String insertAuditQuery = QueryMaker.INSERT_LOGIN_AUDIT;

			preparedStatement = connection.prepareStatement(insertAuditQuery);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, actionName);
			preparedStatement.setString(3, ipAddress);

			preparedStatement.execute();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting audit detail into Audit table due to:::"
					+ exception.getMessage());
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#getUserDetail(java.lang.String)
	 */

	public LoginForm getUserDetail(String username) {
		LoginForm form = new LoginForm();
		try {
			System.out.println("USERNAME ::" + username);
			connection = getConnection();

			String getUserDetailQuery = QueryMaker.RETRIEVE_USER_DETAILS;

			preparedStatement = connection.prepareStatement(getUserDetailQuery);
			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setUserID(resultSet.getInt("id"));
				form.setState(resultSet.getString("state"));
				// form.setFirstName(resultSet.getString("firstName"));
				// form.setLastName(resultSet.getString("lastName"));
				// form.setMiddleName(resultSet.getString("middleName"));

				// if (resultSet.getString("middleName") == null ||
				// resultSet.getString("middleName") == "") {

				// String fullName = resultSet.getString("firstName") + " " +
				// resultSet.getString("lastName");

				// form.setFullName(fullName);

				// } else {

				// String fullName = resultSet.getString("firstName") + " " +
				// resultSet.getString("middleName") + " "
				// + resultSet.getString("lastName");

				// form.setFullName(fullName);
				// }
				form.setFacilityDashboard(resultSet.getInt("facilityDashboard"));
				form.setThirdPartyAPIIntegration(resultSet.getInt("thirdPartyAPIIntegration"));
				form.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				// form.setSpecailization(resultSet.getString("specialization"));
				form.setUserType(resultSet.getString("userType"));
				int clinicID = resultSet.getInt("defaultClinicID");
				form.setClinicID(clinicID);
				// form.setOPDJSPName(resultSet.getString("OPDPageName"));
				// form.setIPDJSPName(resultSet.getString("IPDPageName"));
				int practiceID = resultSet.getInt("practiceID");
				form.setPracticeID(practiceID);
				form.setClinicName(retrieveClinicName(resultSet.getInt("defaultClinicID")));
				// retrieving and storing OPD jsp page name into OPDJSPName
				// variable
				form.setOPDJSPName(retrieveOPDFormName(clinicID));
				// retrieving and storing patient jsp page name into
				// patientJSPPName
				// variable
				form.setPatientJSPName(retrievePatientFormName(clinicID));
				// Retrieving practice suffix based on practiceID
				form.setPracticeSuffix(retrievePracticeSuffix(practiceID));
				form.setClinicSuffix(retrieveClinicSuffix(clinicID));
				form.setVisitTypeID(retrieveVisitTypeID(clinicID));

				System.out.println("PRACTICEID GSABZDBH::" + practiceID);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Logged in user details from User due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfPatients()
	 */
	public String calculateTotalNoOfPatients(int practiceID) {
		String totalNoOfPatients = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfPatientsQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_PATIENTS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfPatientsQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfPatients = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Patients ::: " + totalNoOfPatients);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Patients due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfPatients;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfOPDVisits()
	 */
	public String calculateTotalNoOfOPDVisits(int clinicID) {

		String totalNoOfOPDVisits = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfOPDVisitsQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_OPD_VISITS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfOPDVisitsQuery);
			preparedStatement.setString(1, "OPD");
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfOPDVisits = resultSet.getString("COUNT");
			}
			System.out.println("Total No of OPD Visits ::: " + totalNoOfOPDVisits);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of OPD Visits due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfOPDVisits;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfIPDVisits()
	 */
	public String calculateTotalNoOfIPDVisits(int clinicID) {
		String totalNoOfIPDVisits = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfIPDVisitsQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_IPD_VISITS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfIPDVisitsQuery);
			preparedStatement.setString(1, "IPD");
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfIPDVisits = resultSet.getString("COUNT");
			}
			System.out.println("Total No of IPD Visits ::: " + totalNoOfIPDVisits);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of IPD Visits due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfIPDVisits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalBilling()
	 */
	public Double calculateTotalBilling(int clinicID) {
		String totalBill = null;
		double finalBill = 0D;

		try {
			connection = getConnection();

			String calculateTotalNoOfIPDVisitsQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_BILL;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfIPDVisitsQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalBill = resultSet.getString("SUM");
			}

			if (totalBill == null || totalBill == "") {
				totalBill = "0";
			}
			finalBill = Double.parseDouble(totalBill);
			System.out.println("Total No of Bill ::: " + finalBill);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while calculating total no of Bill due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return finalBill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicName(int)
	 */
	public String retrieveClinicName(int clinicID) {
		String clinicName = null;

		try {
			connection = getConnection();

			String retrieveClinicName = QueryMaker.RETRIEVE_CLINIC_NAME;

			preparedStatement = connection.prepareStatement(retrieveClinicName);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicName = resultSet.getString("name");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving clinic name from Clinic table due to:::"
					+ exception.getMessage());
		}
		return clinicName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalOpticianBilling(int)
	 */
	public Double calculateTotalOpticianBilling(int clinicID) {
		String totalBill = null;
		double finalBill = 0D;

		try {
			connection = getConnection();

			String calculateTotalOpticianBillingQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_OPTICIAN_BILL;

			preparedStatement = connection.prepareStatement(calculateTotalOpticianBillingQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalBill = resultSet.getString("SUM");
			}

			if (totalBill == null || totalBill == "") {
				totalBill = "0";
			}
			finalBill = Double.parseDouble(totalBill);
			System.out.println("Total No of Opticiab Bill ::: " + finalBill);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Optician Bill due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return finalBill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfOpticianVisit(int)
	 */
	public String calculateTotalNoOfOpticianVisit(int clinicID) {

		String totalNoOfOpticianVisits = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfOpticianVisitQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_OPTICIAN_VISITS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfOpticianVisitQuery);
			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfOpticianVisits = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Optician Visits ::: " + totalNoOfOpticianVisits);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while calculating total no of Optician Visits due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfOpticianVisits;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfAppointments(int
	 * clinicID)
	 */
	public String calculateTotalNoOfAppointments(int practiceID) {
		String totalNoOfIPDAppointments = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfAppointmentsQuery = QueryMaker.CALCULATE_TOTAL_NO_OF_APPOINTMENTS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfAppointmentsQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfIPDAppointments = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Appointments ::: " + totalNoOfIPDAppointments);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Appointments due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfIPDAppointments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfPatients()
	 */
	public String calculateTotalNoOfPatients() {
		String totalNoOfPatients = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfPatientsQuery = QueryMaker.CALCULATE_NO_OF_PATIENTS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfPatientsQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfPatients = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Patients ::: " + totalNoOfPatients);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Patiens due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfPatients;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfOPDVisits()
	 */
	public String calculateTotalNoOfOPDVisits() {
		String totalNoOfOPDVisits = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfOPDVisitsQuery = QueryMaker.CALCULATE_NO_OF_OPD_VISITS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfOPDVisitsQuery);

			preparedStatement.setString(1, "OPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfOPDVisits = resultSet.getString("COUNT");
			}
			System.out.println("Total No of OPD Visits ::: " + totalNoOfOPDVisits);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of OPD Visits due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfOPDVisits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateTotalNoOfIPDVisits()
	 */
	public String calculateTotalNoOfIPDVisits() {
		String totalNoOfIPDVisits = null;

		try {
			connection = getConnection();

			String calculateTotalNoOfIPDVisitsQuery = QueryMaker.CALCULATE_NO_OF_IPD_VISITS;

			preparedStatement = connection.prepareStatement(calculateTotalNoOfIPDVisitsQuery);

			preparedStatement.setString(1, "IPD");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfIPDVisits = resultSet.getString("COUNT");
			}
			System.out.println("Total No of IPD Visits ::: " + totalNoOfIPDVisits);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of IPD Visits due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfIPDVisits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfActiveUsers()
	 */
	public String calculateNoOfActiveUsers() {
		String totalNoOfActiveUsers = null;

		try {
			connection = getConnection();

			String calculateNoOfActiveUsersQuery = QueryMaker.CALCULATE_NO_OF_ACTIVE_USER;

			preparedStatement = connection.prepareStatement(calculateNoOfActiveUsersQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfActiveUsers = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Active Users ::: " + totalNoOfActiveUsers);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Active USers due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfActiveUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfAdminUsers()
	 */
	public String calculateNoOfAdminUsers() {
		String totalNoOfAdminUsers = null;

		try {
			connection = getConnection();

			String calculateNoOfActiveUsersQuery = QueryMaker.CALCULATE_NO_OF_ADMIN_USER;

			preparedStatement = connection.prepareStatement(calculateNoOfActiveUsersQuery);

			preparedStatement.setString(1, ActivityStatus.ADMINISTRATOR);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfAdminUsers = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Admin Users ::: " + totalNoOfAdminUsers);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of admin USers due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfAdminUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfClinicians()
	 */
	public String calculateNoOfClinicians() {
		String totalNoOfClinicians = null;

		try {
			connection = getConnection();

			String calculateNoOfCliniciansQuery = QueryMaker.CALCULATE_NO_OF_CLINICIANS;

			preparedStatement = connection.prepareStatement(calculateNoOfCliniciansQuery);

			preparedStatement.setString(1, ActivityStatus.CLINICIAN);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfClinicians = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Clinicians ::: " + totalNoOfClinicians);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of clinicians due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfClinicians;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfStaffs()
	 */
	public String calculateNoOfStaffs() {
		String totalNoOfStaffs = null;

		try {
			connection = getConnection();

			String calculateNoOfStaffsQuery = QueryMaker.CALCULATE_NO_OF_STAFFS;

			preparedStatement = connection.prepareStatement(calculateNoOfStaffsQuery);

			preparedStatement.setString(1, ActivityStatus.STAFF);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfStaffs = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Staffs ::: " + totalNoOfStaffs);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Staffs due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfStaffs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfClinics()
	 */
	public String calculateNoOfClinics() {
		String totalNoOfClinics = null;

		try {
			connection = getConnection();

			String calculateNoOfClinicsQuery = QueryMaker.CALCULATE_NO_OF_CLINICS;

			preparedStatement = connection.prepareStatement(calculateNoOfClinicsQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfClinics = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Clinics ::: " + totalNoOfClinics);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Clinics due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfClinics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfClinicTypes()
	 */
	public String calculateNoOfClinicTypes() {
		String totalNoOfClinicTypes = null;

		try {
			connection = getConnection();

			String calculateNoOfClinicTypesQuery = QueryMaker.CALCULATE_NO_OF_CLINIC_TYPES;

			preparedStatement = connection.prepareStatement(calculateNoOfClinicTypesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfClinicTypes = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Clinic Types ::: " + totalNoOfClinicTypes);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Clinic Types due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfClinicTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfDrugs()
	 */
	public String calculateNoOfDrugs() {
		String totalNoOfDrugs = null;

		try {
			connection = getConnection();

			String calculateNoOfDrugsQuery = QueryMaker.CALCULATE_NO_OF_DRUGS;

			preparedStatement = connection.prepareStatement(calculateNoOfDrugsQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfDrugs = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Drugs ::: " + totalNoOfDrugs);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Drugs due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfDrugs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfDiagnoses()
	 */
	public String calculateNoOfDiagnoses() {
		String totalNoOfDiagnoses = null;

		try {
			connection = getConnection();

			String calculateNoOfDiagnosesQuery = QueryMaker.CALCULATE_NO_OF_DIAGNOSES;

			preparedStatement = connection.prepareStatement(calculateNoOfDiagnosesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfDiagnoses = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Diagnoses ::: " + totalNoOfDiagnoses);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while calculating total no of Diagnoses due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfDiagnoses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#calculateNoOfReferringDoctor()
	 */
	public String calculateNoOfReferringDoctor() {
		String totalNoOfReferringDoctors = null;

		try {
			connection = getConnection();

			String calculateNoOfReferringDoctorQuery = QueryMaker.CALCULATE_NO_OF_REFERRING_DOCTORS;

			preparedStatement = connection.prepareStatement(calculateNoOfReferringDoctorQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				totalNoOfReferringDoctors = resultSet.getString("COUNT");
			}
			System.out.println("Total No of Referring Doctors ::: " + totalNoOfReferringDoctors);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while calculating total no of Referring Doctors due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return totalNoOfReferringDoctors;
	}

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveLoginAttemptTimestamp(int userID) {
		String loginAttemptDate = null;

		try {

			connection = getConnection();

			String retrieveLoginAttemptTimestampQuery = QueryMaker.RETRIEVE_LOGIN_ATTEMPT_DATE;

			preparedStatement = connection.prepareStatement(retrieveLoginAttemptTimestampQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				loginAttemptDate = resultSet.getString("dateAndTime");
			}

			System.out
					.println("Date and time from Login Attempt for userID : " + userID + " is :: " + loginAttemptDate);

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
		return loginAttemptDate;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String updateLoginAttemptStatus(int userId) {

		String result = "error";
		try {
			connection = getConnection();

			String updateLoginAttemptStatusQuery = QueryMaker.UPDATE_LOGIN_ATTEMPT_STATUS;

			preparedStatement = connection.prepareStatement(updateLoginAttemptStatusQuery);

			preparedStatement.setString(1, ActivityStatus.DISABLE);
			preparedStatement.setInt(2, userId);

			preparedStatement.executeUpdate();

			result = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			result = "error";
		}
		return result;
	}

	public JSONObject verifyUserPIN(String PIN, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		// Encrypting PIN in order to check into DB
		String encryptedPIN = EncDescUtil.EncryptText(PIN);

		boolean result = false;

		try {
			connection = getConnection();

			String verifyUserPINQuery = QueryMaker.VERIFY_USER_PIN;

			preparedStatement = connection.prepareStatement(verifyUserPINQuery);

			preparedStatement.setString(1, encryptedPIN);
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				result = true;

			}

			/*
			 * Checking whether user PIN is correct or not and setting variable according,
			 * so as to unlock screen
			 */
			if (result) {

				object.put("PINCheck", "check");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				// return values;

			} else {

				object.put("PINCheck", "uncheck");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				// return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying credentials.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public JSONObject verifyUserOldPassword(String oldPassword, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		// Encrypting Password in order to check into DB
		String encryptedPass = EncDescUtil.EncryptText(oldPassword);

		boolean result = false;

		try {
			connection = getConnection();

			String verifyUserOldPasswordQuery = QueryMaker.VERIFY_USER_OLD_PASSWORD;

			preparedStatement = connection.prepareStatement(verifyUserOldPasswordQuery);

			preparedStatement.setString(1, encryptedPass);
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				result = true;

			}

			/*
			 * Checking whether user OLD Password is correct or not and setting variable
			 * according, so as to proceed further for change password
			 */
			if (result) {

				object.put("PassCheck", "check");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				// return values;

			} else {

				object.put("PassCheck", "uncheck");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				// return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying old password.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public JSONObject updateUserSecurityCredentials(String newPass, String newPIN, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			/*
			 * If newPass value is null, update PIN into AppUser table
			 */
			if (newPass == null || newPass == "") {

				// Encrypting PIN in order to update into AppUser
				String encryptedPIN = EncDescUtil.EncryptText(newPIN);

				String updateUserSecurityCredentialsQuery = QueryMaker.UPDATE_PIN;

				preparedStatement = connection.prepareStatement(updateUserSecurityCredentialsQuery);

				preparedStatement.setString(1, encryptedPIN);
				preparedStatement.setInt(2, userID);

				preparedStatement.executeUpdate();

				object.put("securityCheck", "check");

				array.add(object);

				values.put("Release", array);

				// preparedStatement.close();

				// return values;

				/*
				 * If newPIN value is null, update password into AppUser table
				 */
			} else if (newPIN == null || newPIN == "") {

				// Encrypting Password in order to update into AppUser
				String encryptedPass = EncDescUtil.EncryptText(newPass);

				String updateUserSecurityCredentialsQuery = QueryMaker.UPDATE_PASSWORD;

				preparedStatement = connection.prepareStatement(updateUserSecurityCredentialsQuery);

				preparedStatement.setString(1, encryptedPass);
				preparedStatement.setInt(2, userID);

				preparedStatement.executeUpdate();

				object.put("securityCheck", "check");

				array.add(object);

				values.put("Release", array);

				// preparedStatement.close();

				// return values;

				/*
				 * If newPass and newPIN both are not null, update both into AppUser table
				 */
			} else {

				// Encrypting Password and PIN in order to update into AppUser
				String encryptedPass = EncDescUtil.EncryptText(newPass);
				String encryptedPIN = EncDescUtil.EncryptText(newPIN);

				String updateUserSecurityCredentialsQuery = QueryMaker.UPDATE_PASSWORD_AND_PIN;

				preparedStatement = connection.prepareStatement(updateUserSecurityCredentialsQuery);

				preparedStatement.setString(1, encryptedPIN);
				preparedStatement.setString(2, encryptedPass);
				preparedStatement.setInt(3, userID);

				preparedStatement.executeUpdate();

				object.put("securityCheck", "check");

				array.add(object);

				values.put("Release", array);

				// preparedStatement.close();

				// return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while updating security credentials.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			// JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;

	}

	public String insertPasswordHistory(int userID, String password) {

		/*
		 * Encrypting password
		 */
		String encryptedPass = EncDescUtil.EncryptText(password);
		try {

			connection = getConnection();

			String insertPasswordHistoryQuery = QueryMaker.INSERT_PASSWORD_HISTORY;

			preparedStatement = connection.prepareStatement(insertPasswordHistoryQuery);

			preparedStatement.setString(1, encryptedPass);
			preparedStatement.setInt(2, userID);

			preparedStatement.execute();

			System.out.println("Successfully insertd password into PasswordHistory table");

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			// JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
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

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return result;
	}

	public String retrieveClinicLogo(int clinicID) {
		String logoFileName = null;

		try {

			connection = getConnection();

			String retrieveClinicLogoQuery = QueryMaker.RETRIEVE_CLINIC_LOGO_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveClinicLogoQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				logoFileName = resultSet.getString("logo");
				// System.out.println("Clinic logo file name :;" + logoFileName);
			}

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving clinic logo file name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return logoFileName;
	}

	public String retrieveProfilePic(int userID) {
		String profilePicName = null;

		try {

			connection = getConnection();

			String retrieveClinicLogoQuery = QueryMaker.RETRIEVE_USER_PROFILE_PIC_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveClinicLogoQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				profilePicName = resultSet.getString("profilePic");

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

		return profilePicName;
	}

	public String retrieveLastLogin(int userID) {

		String loginDate = null;

		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:SS");

		try {

			connection = getConnection();

			String retrieveLastLoginQuery = QueryMaker.RETRIEVE_LAST_LOGIN_DATE_TIME;

			preparedStatement = connection.prepareStatement(retrieveLastLoginQuery);

			preparedStatement.setString(1, "Login");
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				loginDate = resultSet.getString("timeStampLog");

				String[] array = loginDate.split("\\.");

				loginDate = array[0];
			}

			/*
			 * If loginDate is null then set current date time as loginDate
			 */
			if (loginDate == null || loginDate == "") {

				loginDate = dateFormat.format(date);

			}

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
		return loginDate;
	}

	public String retrieverLastPasswordChange(int userID) {

		String loginDate = "";

		try {

			connection = getConnection();

			String retrieverLastPasswordChangeQuery = QueryMaker.RETRIEVE_LAST_PASSWORD_CHANGE_DATE_TIME;

			preparedStatement = connection.prepareStatement(retrieverLastPasswordChangeQuery);

			preparedStatement.setString(1, "Password changed");
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				loginDate = resultSet.getString("timeStampLog");

				String[] array = loginDate.split("\\.");

				loginDate = array[0];
			}

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
		return loginDate;
	}

	public String retrieveLastPINChange(int userID) {

		String loginDate = "";

		try {

			connection = getConnection();

			String retrieveLastPINChangeQuery = QueryMaker.RETRIEVE_LAST_PIN_CHANGE_DATE_TIME;

			preparedStatement = connection.prepareStatement(retrieveLastPINChangeQuery);

			preparedStatement.setString(1, "PIN changed");
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				loginDate = resultSet.getString("timeStampLog");

				String[] array = loginDate.split("\\.");

				loginDate = array[0];
			}

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
		return loginDate;
	}

	public int retrieveInvalideAttempt(String username) {
		int attemptCount = 0;

		try {

			connection = getConnection();

			String retrieveInvalideAttemptQuery = QueryMaker.RETRIEVE_INVALIDATE_ATTEMPT;

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

	public String retrievePatientFormName(int clinicID) {

		String formName = "";

		try {

			connection1 = getConnection();

			String retrievePatientFormNameQuery = QueryMaker.RETRIEVE_PATIENT_FORM_NAME_FROM_PRACTICE;

			preparedStatement1 = connection1.prepareStatement(retrievePatientFormNameQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				formName = resultSet1.getString("patientForm");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return formName;
	}

	public String retrieveOPDFormName(int clinicID) {

		String formName = "";

		try {

			connection1 = getConnection();

			String retrievePatientFormNameQuery = QueryMaker.RETRIEVE_OPD_FORM_NAME;

			preparedStatement1 = connection1.prepareStatement(retrievePatientFormNameQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				formName = resultSet1.getString("jspPageName");
			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return formName;

	}

	public String retrievePracticeSuffix(int practiceID) {

		String practiceSuffix = "";

		try {

			connection1 = getConnection();

			String retrievePracticeSuffixQuery = QueryMaker.RETRIEVE_PRACTICE_SUFFIX;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeSuffixQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				practiceSuffix = resultSet1.getString("suffix");
			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return practiceSuffix;

	}

	public String retrieveClinicSuffix(int clinicID) {

		String clinicSuffix = "";

		try {

			connection1 = getConnection();

			String retrieveClinicSuffixQuery = QueryMaker.RETRIEVE_CLINIC_SUFFIX;

			preparedStatement1 = connection1.prepareStatement(retrieveClinicSuffixQuery);

			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				clinicSuffix = resultSet1.getString("suffix");
			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return clinicSuffix;

	}

	public int retrieveVisitTypeID(int practiceID) {

		int visitTypeID = 0;

		try {

			connection1 = getConnection();

			String retrieveVisitTypeIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitTypeIDQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				visitTypeID = resultSet1.getInt("id");
			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return visitTypeID;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicStartTime(int)
	 */
	public String retrieveClinicStartTime(int clinicID) {

		String clinicStartTime = "";

		try {

			connection = getConnection();

			String retrieveClinicStartTimeQuery = QueryMaker.RETRIEVE_CLINIC_START_TIME;

			preparedStatement = connection.prepareStatement(retrieveClinicStartTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicStartTime = resultSet.getString("clinicStart");
			}

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

		return clinicStartTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicEndTime(int)
	 */
	public String retrieveClinicEndTime(int clinicID) {

		String clinicEndTime = "";

		try {

			connection = getConnection();

			String retrieveClinicEndTimeQuery = QueryMaker.RETRIEVE_CLINIC_END_TIME;

			preparedStatement = connection.prepareStatement(retrieveClinicEndTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicEndTime = resultSet.getString("clinicEnd");
			}

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

		return clinicEndTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicBreakTime(int)
	 */
	public List<String> retrieveClinicBreakTime(int clinicID) {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrieveClinicBreakTimeQuery = QueryMaker.RETRIEVE_CLINIC_BREAK_TIMES;

			preparedStatement = connection.prepareStatement(retrieveClinicBreakTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String breakTime1 = resultSet.getString("breakStart1") + "=" + resultSet.getString("breakEnd1");

				System.out.println("Break time 1 -- " + breakTime1);

				list.add(breakTime1);

				String breakTime2 = "";

				if (resultSet.getString("breakStart2").isEmpty() || resultSet.getString("breakEnd2").isEmpty()) {

					breakTime2 = "";

				} else {

					breakTime2 = resultSet.getString("breakStart2") + "=" + resultSet.getString("breakEnd2");

				}

				System.out.println("Break time 2 -- " + breakTime2);

				if (breakTime2 != "") {
					list.add(breakTime2);
				}

			}

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

		return list;
	}

	public List<LoginForm> retrieveCircularByActivityStatus() {
		List<LoginForm> circularlist = new ArrayList<LoginForm>();
		LoginForm form = null;

		try {

			connection = getConnection();

			String retrieveCircularsQuery = QueryMaker.RETRIEVE_CIRCULARS_BY_ACTIVITY_STATUS;

			preparedStatement = connection.prepareStatement(retrieveCircularsQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setCircular(resultSet.getString("circular"));
				form.setCircularFileName(resultSet.getString("circularFileName"));
				System.out.println("circular name is -- " + form.getCircular());
				circularlist.add(form);
			}

			System.out.println("Successfully retieved circular names");

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return circularlist;
	}

	public String retrieveVisitTypeNameByID(int visitTypeID) {

		String visitTypeName = "";

		try {

			connection = getConnection();

			String retrieveVisitTypeNameByIDQuery = QueryMaker.RETRIEVE_VISIT_TYPE_NAME_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitTypeNameByIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitTypeName = resultSet.getString("name");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving visit type name based on id from PVVisitType table due to:::"
							+ exception.getMessage());
		}

		return visitTypeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveVisitDuration(int)
	 */
	public String retrieveVisitDuration(int practiceID) {

		String visitDuration = "";

		try {

			connection = getConnection();

			String retrieveVisitDurationQuery = QueryMaker.RETIEVE_VISIT_DURATION;

			preparedStatement = connection.prepareStatement(retrieveVisitDurationQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				visitDuration = resultSet.getString("visitDuration");
			}

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

		return visitDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicStartEndTime(int)
	 */
	public JSONObject retrieveClinicStartEndTime(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String clinicTime = "";

		try {
			connection = getConnection();

			String retrieveClinicStartEndTimeQuery = QueryMaker.RETRIEVE_CLINIC_START_END_TIME;

			preparedStatement = connection.prepareStatement(retrieveClinicStartEndTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicTime = resultSet.getString("clinicStart") + "=" + resultSet.getString("clinicEnd");

			}

			object.put("clinicTime", clinicTime);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic start and end time.");

			array.add(object);

			values.put("Release", array);
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicBreak1Time(int)
	 */
	public JSONObject retrieveClinicBreak1Time(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String breakTime = "";

		try {
			connection = getConnection();

			String retrieveClinicBreak1TimeQuery = QueryMaker.RETRIEVE_CLINIC_BREAK1_TIME;

			preparedStatement = connection.prepareStatement(retrieveClinicBreak1TimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				breakTime = resultSet.getString("breakStart1") + "=" + resultSet.getString("breakEnd1");

			}

			object.put("break1Time", breakTime);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic break1 time.");

			array.add(object);

			values.put("Release", array);
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicBreak2Time(int)
	 */
	public JSONObject retrieveClinicBreak2Time(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String breakTime = "";

		try {
			connection = getConnection();

			String retrieveClinicBreak2TimeQuery = QueryMaker.RETRIEVE_CLINIC_BREAK2_TIME;

			preparedStatement = connection.prepareStatement(retrieveClinicBreak2TimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				breakTime = resultSet.getString("breakStart2") + "=" + resultSet.getString("breakEnd2");

			}

			object.put("break2Time", breakTime);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving clinic break2 time.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveClinicVisitDuration(int)
	 */
	public JSONObject retrieveClinicVisitDuration(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int visitDuration = 0;

		try {
			connection = getConnection();

			String retrieveClinicVisitDurationQuery = QueryMaker.RETIEVE_VISIT_DURATION;

			preparedStatement = connection.prepareStatement(retrieveClinicVisitDurationQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				visitDuration = resultSet.getInt("visitDuration");

			}

			object.put("visitDuration", visitDuration);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving visit duration time.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#verifyPatientDetails(java.lang.
	 * String, java.lang.String, java.lang.String, int, int, int)
	 */
	public JSONObject verifyPatientDetails(String fName, String mName, String lName, int mobileNo, int clinicID,
			int practiceID) {
		System.out.println("verifyPatientDetails");
		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 1;

		try {
			connection = getConnection();

			if ((!fName.isEmpty() && !mName.isEmpty() && !lName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile, DATE_FORMAT(dateOfBirth, '%d-%m-%Y') as dateOfBirth FROM Patient WHERE firstName LIKE ? AND middleName LIKE ? AND lastName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + mName + "%");
				preparedStatement.setString(3, "%" + lName + "%");
				preparedStatement.setString(4, "" + mobileNo + "%");
				preparedStatement.setString(5, ActivityStatus.ACTIVE);
				preparedStatement.setInt(6, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("dateOfBirth", resultSet.getString("dateOfBirth"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && !mName.isEmpty() && !lName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND middleName LIKE ? AND lastName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + mName + "%");
				preparedStatement.setString(3, "%" + lName + "%");
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setInt(5, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && !lName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND lastName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + lName + "%");
				preparedStatement.setString(3, "" + mobileNo + "%");
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setInt(5, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && !mName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND middleName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + mName + "%");
				preparedStatement.setString(3, "" + mobileNo + "%");
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setInt(5, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!mName.isEmpty() && !lName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE middleName LIKE ? AND lastName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + mName + "%");
				preparedStatement.setString(2, "%" + lName + "%");
				preparedStatement.setString(3, "" + mobileNo + "%");
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setInt(5, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && !mName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND middleName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + mName + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && !lName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND lastName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "%" + lName + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!fName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, "" + mobileNo + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!mName.isEmpty() && !lName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE middleName LIKE ? AND lastName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + mName + "%");
				preparedStatement.setString(2, "%" + lName + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!mName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE middleName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + mName + "%");
				preparedStatement.setString(2, "" + mobileNo + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if ((!lName.isEmpty() && mobileNo != 0)) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE lastName LIKE ? AND mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + lName + "%");
				preparedStatement.setString(2, "" + mobileNo + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if (!(fName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE firstName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + fName + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if (!(mName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE middleName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + mName + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else if (!(lName.isEmpty())) {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE lastName LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "%" + lName + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			} else {

				String verifyPatientDetailsQuery = "SELECT id, firstName, middleName, lastName, mobile FROM Patient WHERE mobile LIKE ? AND activityStatus = ? AND practiceID = ?";

				preparedStatement = connection.prepareStatement(verifyPatientDetailsQuery);

				preparedStatement.setString(1, "" + mobileNo + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);

				resultSet = preparedStatement.executeQuery();
				System.out.println(preparedStatement);
				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);

					check++;

					array.add(object);

					values.put("Release", array);

				}

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			}

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveVisitTypesList(int)
	 */
	public HashMap<Integer, String> retrieveVisitTypesList(int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveVisitTypesListQuery = QueryMaker.RETRIEVE_VISIT_TYPES1;

			preparedStatement = connection.prepareStatement(retrieveVisitTypesListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

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

		return map;
	}

	public HashMap<Integer, String> retrieveVisitTypesListForAppo(int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveVisitTypesListQuery = QueryMaker.RETRIEVE_VISIT_TYPES1;

			preparedStatement = connection.prepareStatement(retrieveVisitTypesListQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

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

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#retrieveAppointmentDuration(int)
	 */
	public JSONObject retrieveAppointmentDuration(int visitTypeID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int visitDuration = 0;

		try {
			connection = getConnection();

			String retrieveAppointmentDurationQuery = QueryMaker.RETRIEVE_VISIT_DURATION_BY_VISIT_TYPE_ID;

			preparedStatement = connection.prepareStatement(retrieveAppointmentDurationQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				visitDuration = resultSet.getInt("visitDuration");

			}

			object.put("visitDuration", visitDuration);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving appointment duration based on visitTypeID.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public String retrieveUserFullNameByUserID(int userID) {

		String fullName = "";

		try {

			connection1 = getConnection();

			String retrieveUserFullNameByUserIDQuery = QueryMaker.RETRIEVE_FULL_NAME;

			preparedStatement1 = connection1.prepareStatement(retrieveUserFullNameByUserIDQuery);

			preparedStatement1.setInt(1, userID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				fullName = resultSet1.getString("firstName") + " " + resultSet1.getString("lastName");

			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return fullName;
	}

	public HashMap<Integer, String> retrieveClinicList(int practiceID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveClinicListQuery = QueryMaker.RETRIEVE_CLINIC_LIST_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

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

		return map;
	}

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

			// preparedStatement.close();
			// connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving visit id from Visit table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return visitID;
	}

	public List<PatientForm> retrievePatientVisitList(int patientID, int clinicID) {
		List<PatientForm> list = new ArrayList<PatientForm>();
		PatientForm Form = null;

		SimpleDateFormat dateToBeParsed123 = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		try {

			connection = getConnection();

			String retrievePatientVisitListQuery = QueryMaker.RETREIVE_PATIENT_VISIT_LIST;

			preparedStatement = connection.prepareStatement(retrievePatientVisitListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, patientID);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Form = new PatientForm();

				Form.setVisitID(resultSet.getInt("id"));
				date = dateToBeFormatted.parse(resultSet.getString("visitDate"));
				Form.setVisitDate(dateToBeParsed123.format(date));
				Form.setVisitTypeID(resultSet.getInt("visitTypeID"));
				Form.setAptID(resultSet.getInt("apptID"));
				String visitType = resultSet.getString("visitType");
				Form.setVisitType(visitType);
				Form.setDiagnosis(resultSet.getString("diagnosis"));
				Form.setJspPageName(resultSet.getString("jspPageName"));
				Form.setFormName(resultSet.getString("CformName"));

				list.add(Form);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving patient visit list from database due to:::"
					+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	public HashMap<Integer, String> retrievePatientClinicList(int patientID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveClinicListQuery = QueryMaker.RETRIEVE_CLINIC_LIST_BY_PATIENT_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicListQuery);

			preparedStatement.setInt(1, patientID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

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

		return map;
	}

	public JSONObject retrieveSlot1NameAndTime(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String slotTime = "";
		String slotName = "";

		try {
			connection = getConnection();

			String retrieveSlot1NameAndTimeQuery = QueryMaker.RETRIEVE_SLOT1_NAME_AND_TIME;

			preparedStatement = connection.prepareStatement(retrieveSlot1NameAndTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				slotTime = resultSet.getString("slot1Start") + "=" + resultSet.getString("slot1End");
				slotName = resultSet.getString("slot1");

			}

			object.put("slotTime", slotTime);
			object.put("slotName", slotName);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving slot1 name and time.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public JSONObject retrieveSlot2NameAndTime(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String slotTime = "";
		String slotName = "";

		try {
			connection = getConnection();

			String retrieveSlot2NameAndTimeQuery = QueryMaker.RETRIEVE_SLOT2_NAME_AND_TIME;

			preparedStatement = connection.prepareStatement(retrieveSlot2NameAndTimeQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				slotTime = resultSet.getString("slot2Start") + "=" + resultSet.getString("slot2End");
				slotName = resultSet.getString("slot2");

			}

			object.put("slotTime", slotTime);
			object.put("slotName", slotName);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving slot2 name and time.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public String retrievePatientClinicRegistrationNo(int patientID, int clinicID) {

		String regNo = "";

		try {

			connection1 = getConnection();

			String retrievePatientClinicRegistrationNoQuery = QueryMaker.RETRIEVE_PATIENT_CLINIC_REGISTRATION_NO;

			preparedStatement1 = connection1.prepareStatement(retrievePatientClinicRegistrationNoQuery);

			preparedStatement1.setInt(2, patientID);
			preparedStatement1.setInt(1, clinicID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				regNo = resultSet1.getString("regNumber");

			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return regNo;
	}

	public HashMap<Integer, String> retrieveClinicianList(int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection1 = getConnection();

			String retrieveClinicianListQuery = QueryMaker.RETRIEVE_CLINICIAN_LIST_FOR_CLINIC;

			preparedStatement1 = connection1.prepareStatement(retrieveClinicianListQuery);

			preparedStatement1.setInt(1, clinicID);
			preparedStatement1.setString(2, "clinician");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put(resultSet1.getInt("id"), resultSet1.getString("empName"));

			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return map;
	}

	public JSONObject retrieveDaysOtherThanClinicWorkingDays(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		String hiddenDays = "";

		String workingDays = "";

		HashMap<String, String> daysMap = new HashMap<String, String>();

		daysMap.put("0", "Sun");
		daysMap.put("1", "Mon");
		daysMap.put("2", "Tue");
		daysMap.put("3", "Wed");
		daysMap.put("4", "Thu");
		daysMap.put("5", "Fri");
		daysMap.put("6", "Sat");

		try {
			connection = getConnection();

			String retrieveDaysOtherThanClinicWorkingDaysQuery = QueryMaker.RETRIEVE_CLINIC_WORKDAYS_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveDaysOtherThanClinicWorkingDaysQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				workingDays = resultSet.getString("workDays");

			}

			System.out.println("workingDays ..." + workingDays);

			if (workingDays == null || workingDays == "") {

				hiddenDays = "";

			} else if (workingDays.isEmpty()) {

				hiddenDays = "";

			} /*
				 * else if (workingDays.equals("noDays")) {
				 * 
				 * hiddenDays = "-1";
				 * 
				 * }
				 */ else {

				for (String index : daysMap.keySet()) {
					if (!workingDays.contains(daysMap.get(index))) {
						hiddenDays += "," + index;
					}
				}
				if (hiddenDays.startsWith(",")) {
					hiddenDays = hiddenDays.substring(1);
				}

			}

			System.out.println("hidden days ..." + hiddenDays);

			object.put("hiddenDays", hiddenDays);

			array.add(object);

			values.put("Release", array);

			// resultSet.close();
			// preparedStatement.close();
			// connection.close();
			//
			// return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving days other than clinic working days");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	public HashMap<Integer, String> retrievepracticeList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrievepracticeListQuery = QueryMaker.RETRIEVE_PRACTICE_LIST;

			preparedStatement = connection.prepareStatement(retrievepracticeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

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

		return map;
	}

	public int retrievePracticeIDByuserID(int userID) {

		int PracticeID = 0;

		try {
			connection = getConnection();

			String retrievePracticeIDByuserIDQuery = QueryMaker.RETRIEVE_Practice_Details;

			preparedStatement = connection.prepareStatement(retrievePracticeIDByuserIDQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				PracticeID = resultSet.getInt("practiceID");
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving PracticeID from Practice table due to:::"
					+ exception.getMessage());
		}
		return PracticeID;
	}

	public List<LoginForm> retrieveConsentDetailsByUserID(int userID) {
		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = null;

		try {

			connection = getConnection();

			String retrieveConsentDetailsByUserIDQuery = QueryMaker.VERIFY_Consent_DETAIL;

			preparedStatement = connection.prepareStatement(retrieveConsentDetailsByUserIDQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setConsentType(resultSet.getString("consentType"));
				form.setAcceptRule(resultSet.getString("acceptRule"));
				form.setReadRule(resultSet.getString("readRule"));
				form.setInfoTrue(resultSet.getString("infoTrue"));

			}

			list.add(form);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Consent details based on userIID from database due to:::"
							+ exception.getMessage());
			status = "error";

		}
		return list;
	}

	@Override
	public String verifyPatientCredentials(LoginForm form) {
		System.out.println("inside verify patient..");
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
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving patientId from PLoginAttempt table due to:::"
					+ exception.getMessage());
		}
		return patientId;
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

	public String retrieveUserFullNameWithQualification(int userID) {

		String userName = "";

		try {

			connection = getConnection();

			String retrieveUserFullNameWithQualificationQuery = QueryMaker.RETRIEVE_USER_FULL_NAME_WITH_QUALIFICATION;

			preparedStatement = connection.prepareStatement(retrieveUserFullNameWithQualificationQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userName = resultSet.getString("fullName") + " (" + resultSet.getString("qualification") + ")";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return userName;
	}

	@Override
	public HashMap<Integer, String> retrieveRoomTypeListByPracticeID(int practiceID, int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveRoomTypeListByPracticeIDQuery = QueryMaker.RETRIEVE_ROOM_FOR_BOOKING_WO_DATE;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeListByPracticeIDQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				/*
				 * int leftCount = resultSet.getInt("roomCapacity") -
				 * resultSet.getInt("bookedCount");
				 * 
				 * if (leftCount <= 0) { continue; }
				 */

				if (resultSet.getInt("bookedCount") > 0) {
					continue;
				}

				map.put(resultSet.getInt("id"), resultSet.getString("roomType"));

			}

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

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#verifyPatientintakeDetails(String,
	 * int, int)
	 */
	public JSONObject verifyPatientIntakeDetails(String mobile, int practiceID, int clinicID, String fName,
			String mName, String lName) {
		System.out.println("verifyPatientIntakeDetails");
		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 1;

		try {
			connection = getConnection();

			if (mName != null) {
				String verifyPatientIntakeDetailsQuery = "SELECT distinct p.id, p.firstName, p.practiceRegNumber, p.middleName, p.lastName, p.age, p.gender, p.bloodGroup, p.rhFactor, p.email, p.mobile, DATE_FORMAT(p.dateOfBirth, '%d-%m-%Y') as dateOfBirth, m.isDiabetes, m.hypertension, m.ischemicHeartDisease, m.asthema, m.otherDetails FROM Patient p LEFT JOIN MedicalHistory m ON p.id = m.patientID WHERE p.mobile LIKE ? AND p.activityStatus = ? AND p.practiceID = ? AND p.firstName = ? AND p.middleName = ? AND p.lastName = ?";
				preparedStatement = connection.prepareStatement(verifyPatientIntakeDetailsQuery);

				preparedStatement.setString(1, "" + mobile + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(4, fName);
				preparedStatement.setString(5, mName);
				preparedStatement.setString(6, lName);
			} else {
				String verifyPatientIntakeDetailsQuery = "SELECT distinct p.id, p.firstName, p.practiceRegNumber, p.middleName, p.lastName, p.age, p.gender, p.bloodGroup, p.rhFactor, p.email, p.mobile, DATE_FORMAT(p.dateOfBirth, '%d-%m-%Y') as dateOfBirth, m.isDiabetes, m.hypertension, m.ischemicHeartDisease, m.asthema, m.otherDetails FROM Patient p LEFT JOIN MedicalHistory m ON p.id = m.patientID WHERE p.mobile LIKE ? AND p.activityStatus = ? AND p.practiceID = ? AND p.firstName = ? AND p.lastName = ?";
				preparedStatement = connection.prepareStatement(verifyPatientIntakeDetailsQuery);

				preparedStatement.setString(1, "" + mobile + "%");
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);
				preparedStatement.setString(4, fName);
				preparedStatement.setString(5, lName);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("patientID", resultSet.getInt("id"));
				object.put("fName", resultSet.getString("firstName"));
				object.put("mName", resultSet.getString("middleName"));
				object.put("lName", resultSet.getString("lastName"));
				object.put("mobileNo", resultSet.getString("mobile"));
				object.put("dateOfBirth", resultSet.getString("dateOfBirth"));
				object.put("regNo", resultSet.getString("practiceRegNumber"));
				object.put("check", check);
				object.put("age", resultSet.getInt("age"));
				object.put("gender", resultSet.getString("gender"));
				object.put("bloodGroup", resultSet.getString("bloodGroup"));
				object.put("rhFactor", resultSet.getString("rhFactor"));
				object.put("email", resultSet.getString("email"));
				object.put("isDiabetes", resultSet.getString("isDiabetes"));
				object.put("asthema", resultSet.getString("asthema"));
				object.put("hypertension", resultSet.getString("hypertension"));
				object.put("ischemicHeartDisease", resultSet.getString("ischemicHeartDisease"));
				object.put("otherDetails", resultSet.getString("otherDetails"));

				check++;

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			if (check == 1) {

				object = new JSONObject();

				object.put("check", 0);

				array.add(object);

				values.put("Release", array);
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#redirectPatientintakeDetails(int,
	 * int, int)
	 */
	public JSONObject redirectPatientIntakeDetails(int patID, int practiceID, int clinicID) {
		System.out.println("redirectPatientIntakeDetails");
		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 1;

		try {
			connection = getConnection();

			if ((practiceID != 0 && patID != 0)) {

				String redirectPatientIntakeDetailsQuery = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, p.bloodGroup, p.rhFactor, p.email, p.mobile, DATE_FORMAT(p.dateOfBirth, '%d-%m-%Y') as dateOfBirth, m.isDiabetes, m.hypertension, m.ischemicHeartDisease, m.asthema, m.otherDetails FROM Patient p LEFT JOIN MedicalHistory m ON p.id = m.patientID WHERE p.id = ? AND p.activityStatus = ? AND p.practiceID = ?";

				preparedStatement = connection.prepareStatement(redirectPatientIntakeDetailsQuery);

				preparedStatement.setInt(1, patID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, practiceID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("patientID", resultSet.getInt("id"));
					object.put("fName", resultSet.getString("firstName"));
					object.put("mName", resultSet.getString("middleName"));
					object.put("lName", resultSet.getString("lastName"));
					object.put("mobileNo", resultSet.getString("mobile"));
					object.put("dateOfBirth", resultSet.getString("dateOfBirth"));
					object.put("regNo", retrievePatientClinicRegistrationNo(resultSet.getInt("id"), clinicID));
					object.put("check", check);
					object.put("age", resultSet.getInt("age"));
					object.put("gender", resultSet.getString("gender"));
					object.put("bloodGroup", resultSet.getString("bloodGroup"));
					object.put("rhFactor", resultSet.getString("rhFactor"));
					object.put("email", resultSet.getString("email"));
					object.put("isDiabetes", resultSet.getString("isDiabetes"));
					object.put("asthema", resultSet.getString("asthema"));
					object.put("hypertension", resultSet.getString("hypertension"));
					object.put("ischemicHeartDisease", resultSet.getString("ischemicHeartDisease"));
					object.put("otherDetails", resultSet.getString("otherDetails"));

					check++;

					array.add(object);

					values.put("Release", array);

				}

				resultSet.close();
				preparedStatement.close();
				connection.close();

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#redirectPatientintakeDetails(int,
	 * int, int)
	 */
	public JSONObject retrievePIAppointmentType(int clinicID) {
		System.out.println("retrievePIAppointmentType");
		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 1;

		try {
			connection = getConnection();

			if ((clinicID != 0)) {

				String retrievePIAppointmentTypeQuery = QueryMaker.RETRIEVE_VISIT_TYPES1;

				preparedStatement = connection.prepareStatement(retrievePIAppointmentTypeQuery);

				preparedStatement.setInt(1, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					object = new JSONObject();

					object.put("id", resultSet.getInt("id"));
					object.put("name", resultSet.getString("name"));

					check++;

					array.add(object);

					values.put("Release", array);

				}

				resultSet.close();
				preparedStatement.close();
				connection.close();

				if (check == 1) {

					object = new JSONObject();

					object.put("check", 0);

					array.add(object);

					values.put("Release", array);
				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#insertPIPatientDetails(com.
	 * edhanvantari.form.LoginForm)
	 */
	public String insertPIPatientDetails(LoginForm form) {

		try {

			connection = getConnection();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			String insertPIPatientDetailsQuery = QueryMaker.INSERT_PI_PATIENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertPIPatientDetailsQuery);

			preparedStatement.setString(1, form.getFirstName());
			preparedStatement.setString(2, form.getMiddleName());
			preparedStatement.setString(3, form.getLastName());
			preparedStatement.setString(4, form.getMobile());
			preparedStatement.setString(5, form.getRegistrationNo());
			preparedStatement.setInt(6, form.getPracticeID());
			preparedStatement.setString(7, form.getGender());
			preparedStatement.setInt(8, form.getAge());
			if (form.getDob() == null) {
				preparedStatement.setString(9, null);
			} else {
				if (form.getDob().substring(2, 3).equals("-")) {

					preparedStatement.setString(9, dateFormat1.format(dateFormat.parse(form.getDob())));

				} else {

					preparedStatement.setString(9, form.getDob());

				}
			}
			preparedStatement.setString(10, form.getBloodGroup());
			preparedStatement.setString(11, form.getRhFactor());
			preparedStatement.setString(12, form.getEmail());
			preparedStatement.setString(13, ActivityStatus.ACTIVE);
			preparedStatement.setString(14, form.getPhone());
			preparedStatement.setString(15, form.getState());
			preparedStatement.setString(16, form.getCity());
			preparedStatement.setString(17, form.getAddress());
			preparedStatement.setString(18, form.getCountry());
			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting patient details into Patient table due to:::"
					+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	public String updatePIPatientDetails(LoginForm form) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder updateQuery = new StringBuilder("UPDATE Patient SET ");
		List<Object> values = new ArrayList<>();

		try {
			connection = getConnection();

			if (StringUtils.isNotBlank(form.getFirstName())) {
				updateQuery.append("firstName = ?, ");
				values.add(form.getFirstName());
			}

			if (StringUtils.isNotBlank(form.getMiddleName())) {
				updateQuery.append("middleName = ?, ");
				values.add(form.getMiddleName());
			}

			if (StringUtils.isNotBlank(form.getLastName())) {
				updateQuery.append("lastName = ?, ");
				values.add(form.getLastName());
			}

			if (StringUtils.isNotBlank(form.getMobile())) {
				updateQuery.append("mobile = ?, ");
				values.add(form.getMobile());
			}

			/*
			 * if (StringUtils.isNotBlank(form.getRegistrationNo())) {
			 * updateQuery.append("registrationNo = ?, ");
			 * values.add(form.getRegistrationNo()); }
			 */
			/*
			 * if (form.getPracticeID() != 0) { updateQuery.append("practiceID = ?, ");
			 * values.add(form.getPracticeID()); }
			 */

			if (StringUtils.isNotBlank(form.getGender())) {
				updateQuery.append("gender = ?, ");
				values.add(form.getGender());
			}

			if (form.getAge() != 0) {
				updateQuery.append("age = ?, ");
				values.add(form.getAge());
			}

			if (StringUtils.isNotBlank(form.getDob())) {
				updateQuery.append("dob = ?, ");
				if (form.getDob().substring(2, 3).equals("-")) {
					values.add(dateFormat1.format(dateFormat.parse(form.getDob())));
				} else {
					values.add(form.getDob());
				}
			}

			if (StringUtils.isNotBlank(form.getBloodGroup())) {
				updateQuery.append("bloodGroup = ?, ");
				values.add(form.getBloodGroup());
			}

			if (StringUtils.isNotBlank(form.getRhFactor())) {
				updateQuery.append("rhFactor = ?, ");
				values.add(form.getRhFactor());
			}

			if (StringUtils.isNotBlank(form.getEmail())) {
				updateQuery.append("email = ?, ");
				values.add(form.getEmail());
			}

			if (StringUtils.isNotBlank(form.getPhone())) {
				updateQuery.append("phone = ?, ");
				values.add(form.getPhone());
			}

			if (StringUtils.isNoneBlank(form.getAddress())) {
				updateQuery.append("address = ?, ");
				values.add(form.getAddress());
			}

			if (StringUtils.isNoneBlank(form.getState())) {
				updateQuery.append("state = ?, ");
				values.add(form.getState());
			}

			if (StringUtils.isNoneBlank(form.getCity())) {
				updateQuery.append("city = ?, ");
				values.add(form.getCity());
			}

			if (StringUtils.isNoneBlank(form.getCountry())) {
				updateQuery.append("country = ?, ");
				values.add(form.getCountry());
			}

			if (updateQuery.toString().endsWith(", ")) {
				updateQuery.setLength(updateQuery.length() - 2);
			}

			updateQuery.append(" WHERE id = ?");
			values.add(form.getPatientID());

			preparedStatement = connection.prepareStatement(updateQuery.toString());

			for (int i = 0; i < values.size(); i++) {
				preparedStatement.setObject(i + 1, values.get(i));
			}

			if (values.size() == 1) {
				status = "success";
			} else {
				preparedStatement.executeUpdate();
				status = "success";

				preparedStatement.close();
				connection.close();
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occurred while updating patient details into Patient table due to:::"
					+ exception.getMessage());
			status = "error";
		}

		return status;
	}

	public String insertClinicRegistration(String registrationNo, int clinicID, int patientID) {

		try {
			connection = getConnection();

			String insertClinicRegistrationQuery = QueryMaker.INSERT_CLINIC_REGISTRATION;

			preparedStatement = connection.prepareStatement(insertClinicRegistrationQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, registrationNo);
			preparedStatement.setInt(3, patientID);
			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting clinic registration details into Clinicregistration table due to:::"
							+ exception.getMessage());

			status = "error";
		}

		return status;

	}

	public int retrievePIPatientIDByRegNumber(String registrationNo) {
		int patientID = 0;

		try {

			connection = getConnection();

			String retrievePIPatientIDByRegNumberQuery = QueryMaker.RETRIEVE_PATIENT_ID_BY_REG_NUMBER;

			preparedStatement = connection.prepareStatement(retrievePIPatientIDByRegNumberQuery);

			preparedStatement.setString(1, registrationNo);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientID;

	}

	public int retrieveClinicianIDByName(LoginForm form) {

		int clinicianID = 0;

		try {

			connection = getConnection();

			if (form.getRefDocMName() == null) {

				String retrieveClinicianIDByName = QueryMaker.RETRIEVE_CLINICIAN_ID_BY_FNAME_LNAME;
				preparedStatement = connection.prepareStatement(retrieveClinicianIDByName);

				preparedStatement.setString(1, form.getRefDocFName());
				preparedStatement.setString(2, form.getRefDocLName());
				preparedStatement.setInt(3, form.getPracticeID());
				preparedStatement.setString(4, ActivityStatus.CLINICIAN);

			} else {

				String retrieveClinicianIDByName = QueryMaker.RETRIEVE_CLINICIAN_ID_BY_NAME;

				preparedStatement = connection.prepareStatement(retrieveClinicianIDByName);

				preparedStatement.setString(1, form.getRefDocFName());
				preparedStatement.setString(2, form.getRefDocLName());
				preparedStatement.setString(3, form.getRefDocMName());
				preparedStatement.setInt(4, form.getPracticeID());
				preparedStatement.setString(5, ActivityStatus.CLINICIAN);

			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicianID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clinicianID;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#insertPIPatientDetails(com.
	 * edhanvantari.form.LoginForm)
	 */
	public String insertPIMedicalHistory(LoginForm form) {

		try {

			connection = getConnection();

			String insertPIMedicalHistoryQuery = QueryMaker.INSERT_PI_MEDICAL_HISTORY;

			preparedStatement = connection.prepareStatement(insertPIMedicalHistoryQuery);

			preparedStatement.setInt(1, form.getPatientID());
			preparedStatement.setString(2, form.getIsDiabetes());
			preparedStatement.setString(3, form.getHypertension());
			preparedStatement.setString(4, form.getAsthema());
			preparedStatement.setString(5, form.getIschemicHeartDisease());
			preparedStatement.setString(6, form.getOtherDetails());

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting patient details into Patient table due to:::"
					+ exception.getMessage());

			status = "error";

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#insertPIPatientDetails(com.
	 * edhanvantari.form.LoginForm)
	 */
	public String updatePIMedicalHistory(LoginForm form) {

		StringBuilder updateQuery = new StringBuilder("UPDATE MedicalHistory SET ");
		List<Object> values = new ArrayList<>();

		try {

			connection = getConnection();

			if (StringUtils.isNotBlank(form.getIsDiabetes())) {
				updateQuery.append("isDiabetes = ?, ");
				values.add(form.getIsDiabetes());
			}

			if (StringUtils.isNotBlank(form.getHypertension())) {
				updateQuery.append("hypertension = ?, ");
				values.add(form.getHypertension());
			}

			if (StringUtils.isNotBlank(form.getAsthema())) {
				updateQuery.append("asthema = ?, ");
				values.add(form.getAsthema());
			}

			if (StringUtils.isNotBlank(form.getIschemicHeartDisease())) {
				updateQuery.append("ischemicHeartDisease = ?, ");
				values.add(form.getIschemicHeartDisease());
			}

			if (StringUtils.isNotBlank(form.getOtherDetails())) {
				updateQuery.append("otherDetails = ?, ");
				values.add(form.getOtherDetails());
			}

			if (updateQuery.toString().endsWith(", ")) {
				updateQuery.setLength(updateQuery.length() - 2);
			}

			updateQuery.append(" WHERE patientID = ?");
			values.add(form.getPatientID());

			preparedStatement = connection.prepareStatement(updateQuery.toString());

			for (int i = 0; i < values.size(); i++) {
				preparedStatement.setObject(i + 1, values.get(i));
			}

			preparedStatement.executeUpdate();
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating patient details into Patient table due to:::"
					+ exception.getMessage());

			status = "Exception occured while updating patient details into Patient table due to:::"
					+ exception.getMessage();

		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.LoginDAOInf#retrievePIPatientIDByFirstAndLastName(
	 * java.lang.String, java.lang.String)
	 */
	public int retrievePIPatientIDByFirstAndLastName(String fName, String lName) {

		int patientID = 0;

		try {

			connection = getConnection();

			String retrievePIPatientIDByFirstAndLastNameQuery = QueryMaker.RETRIEVE_PATIENT_ID_BY_FIRST_AND_LAST_NAME;

			preparedStatement = connection.prepareStatement(retrievePIPatientIDByFirstAndLastNameQuery);

			preparedStatement.setString(1, fName);
			preparedStatement.setString(2, lName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientID;
	}

	public int retrievePIPatientIDByName(LoginForm form) {

		int patientID = 0;

		try {

			connection = getConnection();

			if (form.getMiddleName() != null) {
				String retrievePIPatientIDByFirstAndLastNameAndMobileQuery = "SELECT id FROM Patient WHERE firstName = ? AND lastName = ? AND middleName = ? AND practiceID = ?";
				preparedStatement = connection.prepareStatement(retrievePIPatientIDByFirstAndLastNameAndMobileQuery);

				preparedStatement.setString(1, form.getFirstName());
				preparedStatement.setString(2, form.getLastName());
				preparedStatement.setString(3, form.getMiddleName());
				preparedStatement.setInt(4, form.getPracticeID());
			} else {
				String retrievePIPatientIDByFirstAndLastNameAndMobileQuery = "SELECT id FROM Patient WHERE firstName = ? AND lastName = ? AND practiceID = ?";
				preparedStatement = connection.prepareStatement(retrievePIPatientIDByFirstAndLastNameAndMobileQuery);

				preparedStatement.setString(1, form.getFirstName());
				preparedStatement.setString(2, form.getLastName());
				preparedStatement.setInt(3, form.getPracticeID());
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				patientID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientID;
	}

	public int retrievePIAppointmentID(int patientID, String apptTimeFrom, String apptTimeTo, String apptDate) {
		int apptID = 0;

		try {
			connection = getConnection();

			String retrieveAppointmetID = QueryMaker.RETRIEVE_APPOINTMENT_ID;

			preparedStatement = connection.prepareStatement(retrieveAppointmetID);

			preparedStatement.setInt(1, patientID);
			preparedStatement.setString(2, apptTimeFrom);
			preparedStatement.setString(3, apptTimeTo);
			preparedStatement.setString(4, apptDate);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				apptID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return apptID;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#insertPIPatientDetails(com.
	 * edhanvantari.form.LoginForm)
	 */
	public String insertPIAppointmentDetails(LoginForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		String apptTimeTo = "";

		try {
			connection = getConnection();

			String insertPIApppointmentDetailsQuery = QueryMaker.INSERT_PI_APPOINTMENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertPIApppointmentDetailsQuery);

			if (form.getApptDate().substring(2, 3).equals("-")) {

				preparedStatement.setString(1, dateFormat1.format(dateFormat.parse(form.getApptDate())));

			} else {

				preparedStatement.setString(1, form.getApptDate());

			}
			preparedStatement.setString(2, ActivityStatus.BOOKED);
			preparedStatement.setInt(3, form.getPatientID());
			preparedStatement.setInt(4, form.getClinicID());
			preparedStatement.setInt(5, form.getApptType());
			preparedStatement.setInt(6, form.getWalkIn());
			preparedStatement.setString(7, form.getApptTimeFrom());
			preparedStatement.setString(8, form.getApptTimeTo());
			preparedStatement.setInt(9, form.getApptNumber());
			preparedStatement.setInt(10, form.getClinicianID());
			preparedStatement.setString(11, form.getComments());

			preparedStatement.execute();

			preparedStatement.close();
			connection.close();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting PI apoointment details due to:::" + exception.getMessage());

			status = "error";
		}
		return status;
	}

	public String updatePIAppointmentDetails(LoginForm form) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder updateQuery = new StringBuilder("UPDATE Appointment SET ");
		List<Object> values = new ArrayList<>();
		String apptTimeTo = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();

			if (StringUtils.isNotBlank(form.getUpdatedApptDate())) {
				updateQuery.append("apptDate = ?, ");
				if (form.getUpdatedApptDate().substring(2, 3).equals("-")) {
					values.add(dateFormat1.format(dateFormat.parse(form.getUpdatedApptDate())));
				} else {
					values.add(form.getUpdatedApptDate());
				}
			}

			if (form.getApptType() != 0) {
				updateQuery.append("visitTypeID = ?, ");
				values.add(form.getApptType());
			}
			
			if(StringUtils.isNotBlank(form.getComments())) {
				updateQuery.append("comments = ?, ");
				values.add(form.getComments());
			}

			if (StringUtils.isNotBlank(form.getApptStatus())) {
				updateQuery.append("status = ?, ");
				values.add(form.getApptStatus());
			}

			if (form.getWalkIn() != 0) {
				updateQuery.append("walkIn = ?, ");
				values.add(form.getWalkIn());
			}

			if (StringUtils.isNotBlank(form.getUpdatedApptTimeFrom())) {
				updateQuery.append("apptTimeFrom = ?, ");
				values.add(form.getUpdatedApptTimeFrom());
			}

			if (StringUtils.isNoneBlank(form.getUpdatedApptTimeTo())) {
				updateQuery.append("apptTimeTo = ?, ");
				values.add(form.getUpdatedApptTimeTo());
			}

			if (updateQuery.toString().endsWith(", ")) {
				updateQuery.setLength(updateQuery.length() - 2);
			}

			updateQuery.append(" WHERE id = ?");
			values.add(form.getApptID());

			preparedStatement = connection.prepareStatement(updateQuery.toString());

			if (values.size() == 0) {
				
				status = "success";
				 
			} else {
				for (int i = 0; i < values.size(); i++) {
					preparedStatement.setObject(i + 1, values.get(i));
				}
				
				preparedStatement.executeUpdate();
				status = "success";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occurred while updating appointment details due to:::" + exception.getMessage());
			status = "error";
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.LoginDAOInf#redirectPatientintakeDetails(int,
	 * int, int)
	 */
	public JSONObject retrievePIAppointmentSlots(int clinicID, String apptDate) {
		System.out.println("retrievePIAppointmentType");
		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 1;
		Set<Integer> break1 = new HashSet<Integer>();
		Set<Integer> break2 = new HashSet<Integer>();
		Map<Integer, Integer> apptMap = new HashMap<Integer, Integer>();

		try {
			connection = getConnection();

			if ((clinicID != 0)) {

				String retrieveClinicTimesQuery = QueryMaker.RETRIEVE_CALENDAR_BY_CLINIC_ID;

				preparedStatement = connection.prepareStatement(retrieveClinicTimesQuery);

				preparedStatement.setInt(1, clinicID);

				resultSet = preparedStatement.executeQuery();

				String retrieveApptByClinicAndDateQuery = QueryMaker.RETRIEVE_PI_APPT_BY_CLINIC_AND_DATE;

				preparedStatement1 = connection.prepareStatement(retrieveApptByClinicAndDateQuery);

				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

				preparedStatement1.setInt(1, clinicID);
				if (apptDate.substring(2, 3).equals("-")) {

					preparedStatement1.setString(2, dateFormat1.format(dateFormat.parse(apptDate)));

				} else {

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

					int piSlot = Integer.parseInt(startTime.substring(0, 2));
					while (clinicTimeDiff > 0) {

						System.out.println(piSlot);

						if (break1.contains(piSlot) || break2.contains(piSlot)) {
							clinicTimeDiff -= 1;
							piSlot += 1;
							continue;
						}

						if (apptMap.containsKey(piSlot)) {

							if (apptMap.get(piSlot) >= 6) {

								/*
								 * if(break1.contains(piSlot) || break2.contains(piSlot)) { clinicTimeDiff -= 1;
								 * piSlot += 1; continue; }
								 */
								clinicTimeDiff -= 1;

								piSlot += 1;

							} else {

								object = new JSONObject();

								object.put("slot", piSlot);

								clinicTimeDiff -= 1;

								piSlot += 1;

								check++;

								array.add(object);

								values.put("Release", array);
							}

						} else {
							object = new JSONObject();

							object.put("slot", piSlot);

							clinicTimeDiff -= 1;

							piSlot += 1;

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

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying patient details.");

			array.add(object);

			values.put("Release", array);

			// return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return values;
	}

	@Override
	public String retrieveUserMedRegistrationNumber(int userID) {

		String regNumber = "";

		try {

			connection = getConnection();

			String retrieveUserFullNameWithMedRegistrationNumberQuery = QueryMaker.RETRIEVE_USER_FULL_NAME_WITH_QUALIFICATION;

			preparedStatement = connection.prepareStatement(retrieveUserFullNameWithMedRegistrationNumberQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				regNumber = resultSet.getString("clinicianRegNo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return regNumber;
	}

	public String insertEmergencyContact(int patientID, LoginForm form) {
		try {
			connection = getConnection();

			String insertEmergencyContactQuery = QueryMaker.INSERT_EMERGENCY_CONTACT_INFORMATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertEmergencyContactQuery);

			preparedStatement.setString(1, form.getEmFname());
			preparedStatement.setString(2, form.getEmMname());
			preparedStatement.setString(3, form.getEmLname());
			preparedStatement.setString(4, form.getEmAdd());
			preparedStatement.setString(5, form.getEmCity());
			preparedStatement.setString(6, form.getEmState());
			preparedStatement.setString(7, form.getEmCountry());
			preparedStatement.setString(8, form.getEmPhone());
			preparedStatement.setString(9, form.getEmMobile());
			preparedStatement.setString(10, form.getEmEmailID());
			preparedStatement.setInt(11, patientID);
			preparedStatement.setString(12, form.getEmRelation());

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

	public String updatePIEmergencyContact(int patientID, LoginForm form) {

		StringBuilder updateQuery = new StringBuilder("UPDATE EmergencyContact SET ");
		List<Object> values = new ArrayList<>();

		try {

			connection = getConnection();

			if (StringUtils.isNotBlank(form.getEmFname())) {
				updateQuery.append("firstName = ?, ");
				values.add(form.getEmFname());
			}

			if (StringUtils.isNotBlank(form.getEmMname())) {
				updateQuery.append("middleName = ?, ");
				values.add(form.getEmMname());
			}

			if (StringUtils.isNotBlank(form.getEmLname())) {
				updateQuery.append("lastName = ?, ");
				values.add(form.getEmLname());
			}

			if (StringUtils.isNotBlank(form.getEmAdd())) {
				updateQuery.append("address = ?, ");
				values.add(form.getEmAdd());
			}

			if (StringUtils.isNotBlank(form.getEmCity())) {
				updateQuery.append("city = ?, ");
				values.add(form.getEmCity());
			}

			if (StringUtils.isNotBlank(form.getEmState())) {
				updateQuery.append("state = ?, ");
				values.add(form.getEmState());
			}

			if (StringUtils.isNotBlank(form.getEmCountry())) {
				updateQuery.append("country = ?, ");
				values.add(form.getEmCountry());
			}

			if (StringUtils.isNotBlank(form.getEmPhone())) {
				updateQuery.append("phone = ?, ");
				values.add(form.getEmPhone());
			}

			if (StringUtils.isNotBlank(form.getEmMobile())) {
				updateQuery.append("mobile = ?, ");
				values.add(form.getEmMobile());
			}

			if (StringUtils.isNotBlank(form.getEmEmailID())) {
				updateQuery.append("email = ?, ");
				values.add(form.getEmEmailID());
			}

			if (StringUtils.isNotBlank(form.getEmRelation())) {
				updateQuery.append("relationToPatient = ?, ");
				values.add(form.getEmRelation());
			}

			if (updateQuery.toString().endsWith(", ")) {
				updateQuery.setLength(updateQuery.length() - 2);
			}

			updateQuery.append(" WHERE patientID = ?");
			values.add(form.getPatientID());

			preparedStatement = connection.prepareStatement(updateQuery.toString());

			for (int i = 0; i < values.size(); i++) {
				preparedStatement.setObject(i + 1, values.get(i));
			}

			preparedStatement.executeUpdate();
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating EmergencyContact detail into table due to:::"
					+ exception.getMessage());
			status = "Exception occured while updating EmergencyContact detail into table due to:::"
					+ exception.getMessage();
		}

		return status;

	}

	public String insertIdentification(LoginForm form, int patientID) {
		try {
			connection = getConnection();

			String insertIdentificationQuery = QueryMaker.INSERT_IDENTIFICATION_DETAILS;

			preparedStatement = connection.prepareStatement(insertIdentificationQuery);

			preparedStatement.setString(1, ActivityStatus.ID_DOCUMENT);
			preparedStatement.setString(2, form.getAadhaarNo());
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

	public String updatePIIdentification(LoginForm form, int patientID) {
		StringBuilder updateQuery = new StringBuilder("UPDATE Identification SET ");
		List<Object> values = new ArrayList<>();

		try {
			connection = getConnection();

			if (StringUtils.isNotBlank(form.getAadhaarNo())) {
				updateQuery.append("idNumber = ?, ");
				values.add(form.getAadhaarNo());
			}

			if (updateQuery.toString().endsWith(", ")) {
				updateQuery.setLength(updateQuery.length() - 2);
			}

			updateQuery.append(" WHERE patientID = ?");
			values.add(form.getPatientID());

			preparedStatement = connection.prepareStatement(updateQuery.toString());

			for (int i = 0; i < values.size(); i++) {
				preparedStatement.setObject(i + 1, values.get(i));
			}

			preparedStatement.executeUpdate();
			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating identification detail into table due to:::"
					+ exception.getMessage());
			status = "Exception occured while updating identification detail into table due to:::"
					+ exception.getMessage();
		}

		return status;

	}

	public int retrieveAppointmentNumber(String apptDate, int clinicID) {

		int apptNo = 0;

		try {

			connection = getConnection();

			String retrieveAppointmentNumberQuery = QueryMaker.RETRIEVE_APPOINTMENT_NUMBER_BY_CLINIC_ID;

			preparedStatement = connection.prepareStatement(retrieveAppointmentNumberQuery);

			preparedStatement.setString(1, apptDate);
			
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				apptNo = resultSet.getInt("apptNumber");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return apptNo;
	}
	
}
