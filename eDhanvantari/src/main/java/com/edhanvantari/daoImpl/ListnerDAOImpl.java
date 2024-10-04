package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.edhanvantari.daoInf.ListnerDOAInf;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.QueryMaker;

public class ListnerDAOImpl extends DAOConnection implements ListnerDOAInf {

	String result = "error";

	public String checkTradeNameFromPrescription(String contextPath) {

		try {

			Connection connection = getConnection(contextPath);

			/*
			 * Retrieving all of the tradeName list from Prescription table and checking one
			 * by one whether any new tradeName added into it or not if yes, then adding
			 * that newly added tradeName along with drugName and dose into PVPrescription
			 * table
			 */
			String checkFromPrescriptionTableQuery = QueryMaker.CHECK_PRESCRIPTION_TABLE;

			PreparedStatement preparedStatement = connection.prepareStatement(checkFromPrescriptionTableQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String tradeName = resultSet.getString("tradeName");
				String drugName = resultSet.getString("drugName");
				String dose = resultSet.getString("dose");

				/*
				 * Opening new connection t PVPrescription table
				 */
				Connection connection2 = getConnection(contextPath);

				/*
				 * Checking into PVPrescription table whether tradeName already exists or not,
				 * if not, then insert into it
				 */
				String checkPVPrescriptionTableQuery = QueryMaker.CHECK_PVPRESCRIPTION_TABLE;

				PreparedStatement preparedStatement2 = connection2.prepareStatement(checkPVPrescriptionTableQuery);

				preparedStatement2.setString(1, tradeName);

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				if (resultSet2.next()) {
					System.out.println("TradeName already exists into PVPrescription table.");

					result = "input";
				} else {

					/**
					 * Inserting newly found tradeName along with drugName and dose into
					 * PVPrescription table
					 */

					Connection connection3 = getConnection(contextPath);

					String insertPVPrescriptionTableQuery = QueryMaker.INSERT_INTO_PVPRESCRIPTION_TABLE;

					PreparedStatement preparedStatement3 = connection3.prepareStatement(insertPVPrescriptionTableQuery);

					preparedStatement3.setString(1, tradeName);
					preparedStatement3.setString(2, drugName);
					preparedStatement3.setString(3, dose);

					preparedStatement3.execute();

					result = "success";

					preparedStatement3.close();

					connection3.close();
				}

				resultSet2.close();
				preparedStatement2.close();

				connection2.close();
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			result = "exception";
		}

		return result;
	}

	public String checkDiagnosisFromVisit(String contextPath) {

		try {

			Connection connection = getConnection(contextPath);

			/*
			 * Retrieving all of the diagnoses list from Visit table and checking one by one
			 * whether any new diagnoses added into it or not if yes, then adding that newly
			 * added diagnoses into PVDiagnoses table
			 */
			String checkDiagnosesFromVisitQuery = QueryMaker.CHECK_DIAGNOSIS_FROM_VISIT;

			PreparedStatement preparedStatement = connection.prepareStatement(checkDiagnosesFromVisitQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String diagnosis = resultSet.getString("diagnosis");

				/*
				 * if diagnosis value is NULL, do nothing
				 */
				if (diagnosis == null || diagnosis == "") {

					result = "input";

				} else {

					/*
					 * Opening new connection to PVDiagnoses table
					 */
					Connection connection2 = getConnection(contextPath);

					/*
					 * Checking into PVDiagnoses table whether diagnoses already exists or not, if
					 * not, then insert into it
					 */
					String checkDiagnosesIntoPVDiagnosesQuery = QueryMaker.CHECK_DIAGNOSIS_FROM_PVDIAGNOSES;

					PreparedStatement preparedStatement2 = connection2
							.prepareStatement(checkDiagnosesIntoPVDiagnosesQuery);

					preparedStatement2.setString(1, diagnosis);

					ResultSet resultSet2 = preparedStatement2.executeQuery();

					if (resultSet2.next()) {
						// System.out.println("Diagnoses already exists into
						// PVDiagnosis table.");

						result = "input";
					} else {

						/**
						 * Inserting newly found diagnoses into PVDiagnosis table
						 */

						Connection connection3 = getConnection(contextPath);

						String insertDiagnosesIntoPVDiagnosesQuery = QueryMaker.INSERT_DIAGNOSIS_INTO_PVDIAGNOSES;

						PreparedStatement preparedStatement3 = connection3
								.prepareStatement(insertDiagnosesIntoPVDiagnosesQuery);

						preparedStatement3.setString(1, diagnosis);
						preparedStatement3.setBoolean(2, false);

						preparedStatement3.execute();

						result = "success";

						preparedStatement3.close();

						connection3.close();
					}

					resultSet2.close();
					preparedStatement2.close();

					connection2.close();

				}
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			result = "exception";
		}

		return result;
	}

	public List<String> retrieveAppointmentListForReminderSMS(String contextPath) {

		List<String> list = new ArrayList<String>();

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientIDListFromAppointmentQuery = QueryMaker.RETRIEVE_APPOINTMENT_LIST_FOR_REMINDER_SMS;

			PreparedStatement preparedStatement = connection
					.prepareStatement(retrievePatientIDListFromAppointmentQuery);

			preparedStatement.setString(1, ActivityStatus.BOOKED);
			preparedStatement.setString(2, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String finalString = resultSet.getString("apptDate") + "=" + resultSet.getInt("patientID") + "="
						+ resultSet.getString("apptTimeFrom") + "=" + resultSet.getInt("clinicID") + "="
						+ resultSet.getInt("practiceID") + "=" + resultSet.getInt("doctorName");

				// System.out.println("Final String is :: " + finalString);

				list.add(finalString);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrievePatientMobileNoByID(int patientID, String contextPath) {

		String mobile = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientMobileNoByIDQuery = QueryMaker.RETRIEVE_PATIENT_MOBILE_BY_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrievePatientMobileNoByIDQuery);

			preparedStatement1.setInt(1, patientID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				mobile = resultSet1.getString("mobile");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return mobile;
	}

	public String retrievePracticeNameByID(int practiceID, String contextPath) {

		String practiceName = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientMobileNoByIDQuery = QueryMaker.RETRIEVE_PRACTICE_NAME_BY_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrievePatientMobileNoByIDQuery);

			preparedStatement1.setInt(1, practiceID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				practiceName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return practiceName;
	}

	public String retrieveClinicNameByID(int clinicID, String contextPath) {

		String clinicNAme = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientMobileNoByIDQuery = QueryMaker.RETRIEVE_CLINIC_NAME_BY_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrievePatientMobileNoByIDQuery);

			preparedStatement1.setInt(1, clinicID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				clinicNAme = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clinicNAme;
	}

	public String retrievePatientFirstLastNameByID(int patientID, String contextPath) {

		String patientName = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientMobileNoByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID1;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrievePatientMobileNoByIDQuery);

			preparedStatement1.setInt(1, patientID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientName = resultSet1.getString("firstName") + " " + resultSet1.getString("lastName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientName;
	}

	public List<String> retrieveClinicianUserID(String contextPath) {

		List<String> list = new ArrayList<String>();

		try {

			Connection connection = getConnection(contextPath);

			String retrieveClinicianUserIDQuery = QueryMaker.RETRIEVE_CLINICIAN_DEATILS_LIST;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveClinicianUserIDQuery);

			preparedStatement.setString(1, "clinician");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String finalString = resultSet.getString("email") + "=" + resultSet.getInt("practiceID") + "="
						+ resultSet.getString("firstName") + "=" + resultSet.getString("lastName");

				// System.out.println("Final String is :: " + finalString);

				list.add(finalString);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveEmailFromByPracticeID(int practiceID, String contextPath) {

		String emailFrom = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrieveEmailFromByPracticeIDQuery = QueryMaker.RETRIEVE_EMAIL_FROM_BY_PRACTICE_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrieveEmailFromByPracticeIDQuery);

			preparedStatement1.setInt(1, practiceID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				emailFrom = resultSet1.getString("emailFrom");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return emailFrom;
	}

	public String retrieveEmailFromPassByPracticeID(int practiceID, String contextPath) {

		String emailFromPass = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrieveEmailFromPassByPracticeIDQuery = QueryMaker.RETRIEVE_EMAIL_FROM_PASS_BY_PRACTICE_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrieveEmailFromPassByPracticeIDQuery);

			preparedStatement1.setInt(1, practiceID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				emailFromPass = resultSet1.getString("emailFromPass");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return emailFromPass;
	}

	public String retrievePatientFirstLastNameRegNoByID(int patientID, String contextPath, int clinicID) {

		String patientName = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientFirstLastNameRegNoByIDQuery = QueryMaker.RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID;

			PreparedStatement preparedStatement1 = connection
					.prepareStatement(retrievePatientFirstLastNameRegNoByIDQuery);

			preparedStatement1.setInt(1, patientID);
			preparedStatement1.setInt(2, clinicID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				patientName = resultSet1.getString("firstName") + " " + resultSet1.getString("lastName") + "("
						+ resultSet1.getString("regNo") + ")";
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patientName;
	}

	public List<Integer> retrieveClinicIDForTodaysAppt(String contextPath) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			Connection connection = getConnection(contextPath);

			String retrieveClinicIDForTodaysApptQuery = QueryMaker.RETRIEVE_CLINIC_ID_LIST_FOR_TODAYS_APPOINTMENT;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveClinicIDForTodaysApptQuery);

			preparedStatement.setString(1, ActivityStatus.BOOKED);
			preparedStatement.setString(2, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getInt("clinicID"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<String> retrieveAppointmentListForClinicID(int clinicID, String contextPath) {

		List<String> list = new ArrayList<String>();

		try {

			Connection connection = getConnection(contextPath);

			String retrieveAppointmentListForClinicIDQuery = QueryMaker.RETRIEVE_TODAYS_APPOINTMENT_LIST_FOR_CLINIC_ID;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveAppointmentListForClinicIDQuery);

			preparedStatement.setString(1, ActivityStatus.BOOKED);
			preparedStatement.setString(2, ActivityStatus.CONFIRMED);
			preparedStatement.setInt(3, clinicID);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// Splitting timeFrom and removing seconds from time
				String fromArr[] = resultSet.getString("apptTimeFrom").split(" ");

				String timeArr[] = fromArr[0].split(":");

				String apptTimeFrom = timeArr[0] + ":" + timeArr[1] + " " + fromArr[1];

				// Splitting timeTo and removing seconds from time
				String toArr[] = resultSet.getString("apptTimeTo").split(" ");

				String timeArr1[] = toArr[0].split(":");

				String apptTimeTo = timeArr1[0] + ":" + timeArr1[1] + " " + toArr[1];

				String finalString = apptTimeFrom + "=" + apptTimeTo + "=" + resultSet.getInt("patientID") + "="
						+ resultSet.getString("clinicName") + "=" + resultSet.getString("visitType");

				System.out.println("Final String is :: " + finalString);

				list.add(finalString);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<Integer> retrievePracticeIDList(String contextPath) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			Connection connection = getConnection(contextPath);

			String retrievePracticeIDListQuery = QueryMaker.RETRIEVE_PRACTICE_ID_LIST;

			PreparedStatement preparedStatement = connection.prepareStatement(retrievePracticeIDListQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getInt("id"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String checkDiagnosisFromPersonalHistory(String contextPath) {

		try {

			Connection connection = getConnection(contextPath);

			/*
			 * Retrieving all of the diagnoses list from PersonalHistory table and checking
			 * one by one whether any new diagnoses added into it or not if yes, then adding
			 * that newly added diagnoses into PVDiagnoses table
			 */
			String checkDiagnosesFromMedicalHistoryQuery = QueryMaker.CHECK_DIAGNOSIS_FROM_PERSONAL_HISTORY;

			PreparedStatement preparedStatement = connection.prepareStatement(checkDiagnosesFromMedicalHistoryQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String diagnosis = resultSet.getString("diagnosis");

				/*
				 * Opening new connection to PVDiagnoses table
				 */
				Connection connection2 = getConnection(contextPath);

				/*
				 * Checking into PVDiagnoses table whether diagnoses already exists or not, if
				 * not, then insert into it
				 */
				String checkDiagnosesIntoPVDiagnosesQuery = QueryMaker.CHECK_DIAGNOSIS_FROM_PVDIAGNOSES;

				PreparedStatement preparedStatement2 = connection2.prepareStatement(checkDiagnosesIntoPVDiagnosesQuery);

				preparedStatement2.setString(1, diagnosis);

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				if (resultSet2.next()) {
					// System.out.println("Diagnoses already exists into
					// PVDiagnosis table.");

					result = "input";
				} else {

					/**
					 * Inserting newly found diagnoses into PVDiagnosis table
					 */

					Connection connection3 = getConnection(contextPath);

					String insertDiagnosesIntoPVDiagnosesQuery = QueryMaker.INSERT_DIAGNOSIS_INTO_PVDIAGNOSES;

					PreparedStatement preparedStatement3 = connection3
							.prepareStatement(insertDiagnosesIntoPVDiagnosesQuery);

					preparedStatement3.setString(1, diagnosis);
					preparedStatement3.setBoolean(2, false);

					preparedStatement3.execute();

					result = "success";

					preparedStatement3.close();

					connection3.close();
				}

				resultSet2.close();
				preparedStatement2.close();

				connection2.close();
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			result = "exception";
		}

		return result;
	}

	public String retrieveClinicRegNoByClinicID(int clinicID, int patientID, String contextPath) {

		String regNo = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrieveClinicRegNoByClinicIDQuery = QueryMaker.RETRIEVE_CLINIC_REG_NO_BY_CLINIC_ID;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveClinicRegNoByClinicIDQuery);

			preparedStatement.setInt(2, patientID);
			preparedStatement.setInt(1, clinicID);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				regNo = resultSet.getString("regNumber");
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return regNo;
	}

	public boolean verifyAppointmentExistsForPractice(int practiceID, String contextPath) {

		boolean check = false;

		try {

			Connection connection = getConnection(contextPath);

			String verifyAppointmentExistsForPracticeQuery = QueryMaker.VERIFY_APPOINTMENT_EXISTS_FOR_PRACTICE;

			PreparedStatement preparedStatement = connection.prepareStatement(verifyAppointmentExistsForPracticeQuery);

			preparedStatement.setString(1, ActivityStatus.BOOKED);
			preparedStatement.setString(2, ActivityStatus.CONFIRMED);
			preparedStatement.setInt(3, practiceID);

			ResultSet resultSet = preparedStatement.executeQuery();

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

	public List<String> retrieveDefaultClinicIDList(String contextPath) {

		List<String> list = new ArrayList<String>();

		try {

			Connection connection = getConnection(contextPath);

			String retrieveDefaultClinicIDListQuery = QueryMaker.RETRIEVE_DEFAULT_CLINIC_ID_LIST;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveDefaultClinicIDListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getInt("defaultClinicID") + "$" + resultSet.getInt("practiceID"));

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public boolean verifyCommunication(String columnName, int clinicID, String contextPath) {

		boolean check = false;

		try {

			Connection connection = getConnection(contextPath);

			String verifyCommunicationQuery = "SELECT " + columnName
					+ " FROM Communication WHERE practiceID = (SELECT practiceID FROM Clinic WHERE id = ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(verifyCommunicationQuery);

			preparedStatement.setInt(1, clinicID);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getBoolean(columnName);

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

	public boolean verifyAppointmentExistsForClinic(int clinicID, String contextPath) {

		boolean check = false;

		try {

			Connection connection = getConnection(contextPath);

			String verifyAppointmentExistsForPracticeQuery = QueryMaker.VERIFY_APPOINTMENT_EXISTS_FOR_CLINIC;

			PreparedStatement preparedStatement = connection.prepareStatement(verifyAppointmentExistsForPracticeQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, ActivityStatus.BOOKED);
			preparedStatement.setString(3, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

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

	public List<Integer> retrieveDistinctClinicianIDFromAppointment(String contextPath, int clinicID) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			Connection connection = getConnection(contextPath);

			String retrieveDistinctClinicianIDFromAppointmentQuery = QueryMaker.RETRIEVE_DISTINCT_CLINICIAN_ID_LIST;

			PreparedStatement preparedStatement = connection
					.prepareStatement(retrieveDistinctClinicianIDFromAppointmentQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, ActivityStatus.BOOKED);
			preparedStatement.setString(3, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getInt("clinicianID"));

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveClinicianNameByID(int clinicianID, String contextPath) {

		String clinicianName = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrieveClinicianNameByIDQuery = QueryMaker.RETRIEVE_CLINICIAN_NAME_BY_ID;

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveClinicianNameByIDQuery);

			preparedStatement.setInt(1, clinicianID);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				clinicianName = resultSet.getString("empName");

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clinicianName;
	}

	public int retrieveAppointmentCountForClinician(int clinicianID, int clinicID, String contextPath) {

		int count = 0;

		try {

			Connection connection = getConnection(contextPath);

			String retrieveAppointmentCountForClinicianQuery = QueryMaker.RETRIEVE_APPOINTMENT_COUNT_FOR_CLINICIAN;

			PreparedStatement preparedStatement = connection
					.prepareStatement(retrieveAppointmentCountForClinicianQuery);

			preparedStatement.setInt(1, clinicianID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.BOOKED);
			preparedStatement.setString(4, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return count;
	}

	public String retrievePatientDetailsForAppointment(int clinicID, int clinicianID, String contextPath) {

		String patDetails = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrievePatientDetailsForAppointmentQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS_FOR_APPOINTMENT;

			PreparedStatement preparedStatement = connection
					.prepareStatement(retrievePatientDetailsForAppointmentQuery);

			preparedStatement.setInt(2, clinicianID);
			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(3, ActivityStatus.BOOKED);
			preparedStatement.setString(4, ActivityStatus.CONFIRMED);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				patDetails += "," + resultSet.getString("patName");

			}

			patDetails = patDetails.substring(1);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return patDetails;
	}

	public String retrieveClinicianMobileNoByID(int clinicianID, String contextPath) {

		String mobile = "";

		try {

			Connection connection = getConnection(contextPath);

			String retrieveClinicianMobileNoByIDQuery = QueryMaker.RETRIEVE_CLINICIAN_MOBILE_BY_ID;

			PreparedStatement preparedStatement1 = connection.prepareStatement(retrieveClinicianMobileNoByIDQuery);

			preparedStatement1.setInt(1, clinicianID);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				mobile = resultSet1.getString("mobile");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return mobile;
	}

	public boolean verifyCommunicationByPracticeID(String columnName, int practiceID, String contextPath) {

		boolean check = false;

		try {

			Connection connection = getConnection(contextPath);

			String verifyCommunicationByPracticeIDQuery = "SELECT " + columnName
					+ " FROM Communication WHERE practiceID = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(verifyCommunicationByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getBoolean(columnName);

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

	@Override
	public String updateBookedAppointmentStatus(String contextPath) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -1);

		Date date = calendar.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println(".........date is: " + dateFormat.format(date));

		try {

			Connection connection = getConnection(contextPath);

			String updateBookedAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS;

			PreparedStatement preparedStatement = connection.prepareStatement(updateBookedAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.NO_SHOW);
			preparedStatement.setString(2, ActivityStatus.BOOKED);
			preparedStatement.setString(3, dateFormat.format(date));

			preparedStatement.executeUpdate();

			result = "success";

			System.out.println("Appointment statuses changes from Booked to No Show");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			result = "error";
		}

		return result;
	}

	@Override
	public String updateDispensedAppointmentStatus(String contextPath) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -1);

		Date date = calendar.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println(".........date value is: " + dateFormat.format(date));

		try {

			Connection connection = getConnection(contextPath);

			String updateBookedAppointmentStatusQuery = QueryMaker.UPDATE_APPOINTMENT_STATUS;

			PreparedStatement preparedStatement = connection.prepareStatement(updateBookedAppointmentStatusQuery);

			preparedStatement.setString(1, ActivityStatus.DONE);
			preparedStatement.setString(2, ActivityStatus.BILLING);
			preparedStatement.setString(3, dateFormat.format(date));

			preparedStatement.executeUpdate();

			result = "success";

			System.out.println("Appointment statuses changes from Dispensed to Done");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			result = "error";
		}

		return result;
	}

}
