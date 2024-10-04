package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edhanvantari.daoInf.ApptCalendarDAOinf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ApptCalendarDTO;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;

public class ApptCalendarDAOImpl extends DAOConnection implements ApptCalendarDAOinf {

	Connection connection = null;
	Connection connection1 = null;
	Connection connection2 = null;

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;

	String status = "error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.ApptCalendarDAOinf#
	 * retrieveAppointmentCalendarDetails()
	 */
	public List<ApptCalendarDTO> retrieveAppointmentCalendarDetails(int clinicID) {

		List<ApptCalendarDTO> list = new ArrayList<ApptCalendarDTO>();

		ApptCalendarDTO apptCalendarDTO = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveAppointmentCalendarDetailsQuery = QueryMaker.RETRIEVE_APPT_CALENDAR_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveAppointmentCalendarDetailsQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				apptCalendarDTO = new ApptCalendarDTO();

				apptCalendarDTO.setStart(resultSet.getString("apptDate") + "T" + resultSet.getString("apptTimeFrom"));
				apptCalendarDTO.setEnd(resultSet.getString("apptDate") + "T" + resultSet.getString("apptTimeTo"));

				if (resultSet.getString("status").equals(ActivityStatus.BOOKED)) {

					apptCalendarDTO.setColor("blue");
					apptCalendarDTO.setRendering("");

				} else if (resultSet.getString("status").equals(ActivityStatus.CANCELLED)
						|| resultSet.getString("status").equals(ActivityStatus.NO_SHOW)) {

					apptCalendarDTO.setColor("#cc0000");
					apptCalendarDTO.setRendering("background");

				} else if (resultSet.getString("status").equals(ActivityStatus.PRESCRIPTION)) {

					apptCalendarDTO.setColor("green");
					apptCalendarDTO.setRendering("");

				} else if (resultSet.getString("status").equals(ActivityStatus.BILLING)) {

					apptCalendarDTO.setColor("green");
					apptCalendarDTO.setRendering("");

				} else if (resultSet.getString("status").equals(ActivityStatus.DIETICIAN)) {

					apptCalendarDTO.setColor("yellow");
					apptCalendarDTO.setRendering("");

				} else if (resultSet.getString("status").equals(ActivityStatus.CONSULTATION)) {

					apptCalendarDTO.setColor("green");
					apptCalendarDTO.setRendering("");

				} else if (resultSet.getString("status").equals(ActivityStatus.DONE)) {

					apptCalendarDTO.setColor("#b8b894");
					apptCalendarDTO.setRendering("");

				} else {
					apptCalendarDTO.setColor("Pink");
					apptCalendarDTO.setRendering("");
				}
				/*
				 * else {
				 * 
				 * apptCalendarDTO.setColor("Pink"); apptCalendarDTO.setRendering("");
				 * 
				 * }
				 */

				int patientID = resultSet.getInt("patientID");

				String[] array = { "No", "Yes" };

				// String check = verifyNextAppointmentTaken(patientID,
				// resultSet.getString("apptDate"));
				String clinicianName = resultSet.getString("clinicianName");
				String clinicianName1 = "";
				
				if(clinicianName == null || clinicianName.isEmpty()) {
					clinicianName1 = "None";
				}else {
					clinicianName1 = clinicianName;
				}
				
				apptCalendarDTO.setTitle("Assigned To: " + clinicianName1 + "\t#"
						+ resultSet.getInt("apptNumber") + ":\t" + resultSet.getString("patientDetails") + "\t"
						+ resultSet.getString("visitType") + "(" + resultSet.getString("status") + ")"
						+ " Next appt. taken?: " + array[resultSet.getInt("nextApptTaken")]);

				apptCalendarDTO.setId(resultSet.getInt("id"));
				
				// Retrieving visitID
				String visitIDQuery = QueryMaker.RETRIEVE_VISIT_ID_BY_APPOINTMENT_ID;
				
				preparedStatement1 = connection.prepareStatement(visitIDQuery);

				preparedStatement1.setInt(1, resultSet.getInt("id"));
				preparedStatement1.setInt(2, clinicID);

				resultSet1 = preparedStatement1.executeQuery();
				
				while(resultSet1.next()) {
					apptCalendarDTO.setVisitID(resultSet1.getInt("id"));
				}
				
				resultSet1.close();
				preparedStatement1.close();
				
				apptCalendarDTO.setVisitTypeID(resultSet.getInt("visitTypeID"));
		
				list.add(apptCalendarDTO);

			}

			/*
			 * retrieving the list of employees those are on leave today and adding there
			 * names and leave type as a title
			 
			List<String> emplLeaveList = retrieveLeaveEmployeeList();

			if (emplLeaveList.size() > 0) {

				for (String title : emplLeaveList) {

					apptCalendarDTO = new ApptCalendarDTO();

					String[] array = title.split("=");

					apptCalendarDTO.setTitle(array[0]);

					String leaveType = array[1];

					if (leaveType.equals("Paid Leave")) {
						apptCalendarDTO.setColor("#98AFC7");
					} else if (leaveType.equals("Medical Leave")) {
						apptCalendarDTO.setColor("#737CA1");
					} else if (leaveType.equals("Paternity Leave")) {
						apptCalendarDTO.setColor("#1589FF");
					} else if (leaveType.equals("Maternity Leave")) {
						apptCalendarDTO.setColor("#736AFF");
					} else if (leaveType.equals("Parental Work from Home")) {
						apptCalendarDTO.setColor("#B048B5");
					} else if (leaveType.equals("Optional Leave")) {
						apptCalendarDTO.setColor("#B93B8F");
					} else if (leaveType.equals("Work from Home")) {
						apptCalendarDTO.setColor("#B38481");
					} else if (leaveType.equals("Half-day Leave")) {
						apptCalendarDTO.setColor("#C11B17");
					} else if (leaveType.equals("Half-day Work from Home")) {
						apptCalendarDTO.setColor("#E77471");
					} else if (leaveType.equals("Sabbatical Leave")) {
						apptCalendarDTO.setColor("#F87217");
					}
					apptCalendarDTO.setStart(dateFormat.format(new Date()));
					apptCalendarDTO.setEnd(dateFormat.format(new Date()));

					list.add(apptCalendarDTO);

				}
			}*/

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

	public String retrievePatientDetailsByID(int patientID, int clinicID) {

		String patientDetails = "";

		PatientDAOInf daoInf = new PatientDAOImpl();

		try {

			connection2 = getConnection();

			String retrievePatientDetailsByIDQuery = QueryMaker.RETRIEVE_PATIENT_DETAILS_BY_ID;

			preparedStatement2 = connection2.prepareStatement(retrievePatientDetailsByIDQuery);

			preparedStatement2.setInt(1, patientID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				patientDetails = resultSet2.getInt("id") + "-" + resultSet2.getString("firstName") + " "
						+ resultSet2.getString("lastName") + "("
						+ daoInf.retrieveClinicRegNoByClinicID(clinicID, patientID) + ")";

			}

			// resultSet2.close();
			// preparedStatement2.close();
			// connection2.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeConnection(connection2);
		}

		return patientDetails;
	}

	public String retrieveVisitTypeNameByID(int visitTypeID) {

		String visitType = "";

		try {

			connection1 = getConnection();

			String retrieveVisitTypeNameByIDQUery = QueryMaker.RETRIEVE_VISIT_TYPE_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveVisitTypeNameByIDQUery);

			preparedStatement1.setInt(1, visitTypeID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				visitType = resultSet1.getString("name");

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

		return visitType;
	}

	public String retrieveClinicianNameByID(int clinicianID) {

		String clinicianName = "";

		try {

			connection1 = getConnection();

			String retrieveClinicianNameByIDQUery = QueryMaker.RETRIEVE_CLINICIAN_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveClinicianNameByIDQUery);

			preparedStatement1.setInt(1, clinicianID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				clinicianName = resultSet1.getString("empName");

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

		return clinicianName;
	}

	public String verifyNextAppointmentTaken(int patientID, String apptDate) {

		String check = "No";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection1 = getConnection();

			String verifyNextAppointmentTakenQUery = QueryMaker.VERIFY_NEXT_APPOINTMENT_TAKEN;

			preparedStatement1 = connection1.prepareStatement(verifyNextAppointmentTakenQUery);

			preparedStatement1.setInt(1, patientID);
			preparedStatement1.setString(2, dateFormat.format(dateFormat.parse(apptDate)));

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				check = "Yes";

			}

			// resultSet1.close();
			// preparedStatement1.close();
			// connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "No";
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return check;
	}

}
