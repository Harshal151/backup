package com.edhanvantari.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.edhanvantari.daoImpl.ApptCalendarDAOImpl;
import com.edhanvantari.daoInf.ApptCalendarDAOinf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class ApptCalendarJSONUtil extends ActionSupport
		implements ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;
	HttpServletResponse response;

	private Map<String, Object> session = null;

	ApptCalendarDAOinf calendarDAOinf = null;

	eDhanvantariServiceInf serviceInf = null;

	List<ApptCalendarDTO> list = null;

	/**
	 * 
	 * @throws Exception
	 */
	public void apptCalendarJSONServlet() throws Exception {

		calendarDAOinf = new ApptCalendarDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		list = calendarDAOinf.retrieveAppointmentCalendarDetails(userForm.getClinicID());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(new Gson().toJson(list));

	}

	/**
	 * @return the list
	 */
	public List<ApptCalendarDTO> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<ApptCalendarDTO> list) {
		this.list = list;
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
