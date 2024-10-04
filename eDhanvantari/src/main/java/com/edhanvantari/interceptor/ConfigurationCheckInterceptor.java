package com.edhanvantari.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.edhanvantari.daoImpl.ListnerDAOImpl;
import com.edhanvantari.daoInf.ListnerDOAInf;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ConfigurationCheckInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void destroy() {

		System.out.println("Configuration check Interceptor destroyed");

	}

	
	public void init() {

		System.out.println("Configuration check Intereptor initialized");

	}

	
	public String intercept(ActionInvocation invocation) throws Exception {

		System.out.println("Preprocessing of Configuration check inceptor....");

		String result = invocation.invoke();

		String realPath = null;

		// Getting request object
		HttpServletRequest request = ServletActionContext.getRequest();

		realPath = request.getServletContext().getRealPath("/");

		System.out
				.println("Postprocessing of Configuration check inceptor....");

		ListnerDOAInf doaInf = new ListnerDAOImpl();

		String message = null;

		/*
		 * Checking for new tradeName value from Prescription table, if
		 * available then add that new value into PVPrescription table
		 */
		message = doaInf.checkTradeNameFromPrescription(realPath);

		if (message.equalsIgnoreCase("success")) {

			System.out
					.println("Successfully inserted newly found tradeName into PVPrescription table.");

		} else if (message.equalsIgnoreCase("input")) {

			System.out.println(message);

		} else if (message.equalsIgnoreCase("error")) {

			System.out.println("No records found..");

		} else {

			System.out.println("Exception occured.");

		}

		/*
		 * Checking for new diagnosis value from Visit table, if available then
		 * add that new value into PVDiagnoses table
		 */
		message = doaInf.checkDiagnosisFromVisit(realPath);

		if (message.equalsIgnoreCase("success")) {

			System.out
					.println("Successfully inserted newly found diagnosis from visit into PVDiagnoses table.");

		} else if (message.equalsIgnoreCase("input")) {

			System.out.println(message);

		} else if (message.equalsIgnoreCase("error")) {

			System.out.println("No records found..");

		} else {

			System.out.println("Exception occured.");

		}

		return result;
	}

}
