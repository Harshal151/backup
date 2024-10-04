package com.edhanvantari.daoInf;

import java.util.List;

import com.edhanvantari.util.ApptCalendarDTO;

public interface ApptCalendarDAOinf {

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<ApptCalendarDTO> retrieveAppointmentCalendarDetails(int clinicID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String retrieveVisitTypeNameByID(int visitTypeID);

	/**
	 * 
	 * @param clinicianID
	 * @return
	 */
	public String retrieveClinicianNameByID(int clinicianID);

	/**
	 * 
	 * @param patientID
	 * @param apptDate
	 * @return
	 */
	public String verifyNextAppointmentTaken(int patientID, String apptDate);

}
