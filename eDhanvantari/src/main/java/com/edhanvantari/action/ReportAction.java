package com.edhanvantari.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
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
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoImpl.PrescriptionManagementDAOImpl;
import com.edhanvantari.daoImpl.ReportDAOImpl;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.PrescriptionManagementDAOInf;
import com.edhanvantari.daoInf.ReportDAOInf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.edhanvantari.util.AWSS3Connect;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.ConvertToPDFUtil;
import com.edhanvantari.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ReportAction extends ActionSupport
		implements ModelDriven<PatientForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PatientForm patientForm = new PatientForm();

	ReportDAOInf reportDAOInf = null;
	PatientDAOInf patientDAOInf = null;

	HttpServletRequest request;
	HttpServletResponse response;

	private String pageName;

	private Map<String, Object> session = null;

	List<PatientForm> patientList = null;
	List<PatientForm> medicalBillingList = null;

	HashMap<String, String> tableList = null;

	HashMap<String, String> patientColumnList = null;

	eDhanvantariServiceInf serviceInf = null;

	List<PatientForm> saveQueryList = null;

	HashMap<Integer, String> supplierList = null;

	List<PatientForm> productStockList = null;

	String message = null;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderMedicalBilling() throws Exception {
		patientDAOInf = new PatientDAOImpl();
		reportDAOInf = new ReportDAOImpl();

		/*
		 * Getting clinicID from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		String patientName = patientForm.getPatientName();

		if (patientName == null || patientName == "") {

			/*
			 * retrieving all available patient list
			 */
			patientList = patientDAOInf.retrievePatientList(loginForm.getPracticeID(), loginForm.getClinicID());

		} else {

			/*
			 * Retrieving patient list on the basis of patientName entered into search box
			 * and clinicID
			 */
			patientList = patientDAOInf.searchPatientByPatientName(patientForm.getPatientName(),
					loginForm.getPracticeID(), loginForm.getClinicID(), patientForm.getSearchCriteria());

		}

		/*
		 * Retrieving list of visit for medical billing report
		 */
		medicalBillingList = reportDAOInf.retrieveListForMedicalReport(patientForm.getPatientID(),
				patientForm.getPatientName());

		if (medicalBillingList.size() > 0) {

			request.setAttribute("showModal", "show");

			String patientCheck = "Patient";

			request.setAttribute("patientCheck", patientCheck);

			return SUCCESS;

		} else {

			request.setAttribute("noVisitMsg", "No visit added for this patient yet. Please add a new visit.");

			request.setAttribute("showModal", "hide");

			String patientCheck = "Patient";

			request.setAttribute("patientCheck", patientCheck);

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 */
	public String printBillingReport() throws Exception {
		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		System.out.println("Last visit ID : " + patientForm.getLastVisitID());

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();
		
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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		System.out.println("Real path is :: " + realPath);

		//String pdfOutFIleName = realPath + "BillingReport_" + patientForm.getPatientID() + "_"
		String pdfOutFIleName = "BillingReport_" + patientForm.getPatientID() + "_"+ patientForm.getVisitID() + ".pdf";

		message = convertToPDFUtil.printPDFBillingReport(patientForm.getPatientID(), patientForm.getVisitID(), realPath,
				pdfOutFIleName);

		String patientName = patientForm.getPatientName();

		if (patientName == null || patientName == "") {

			/*
			 * retrieving all available patient list
			 */
			patientList = patientDAOInf.retrievePatientList(loginForm.getPracticeID(), loginForm.getClinicID());

		} else {

			/*
			 * Retrieving patient list on the basis of patientName entered into search box
			 * and clinicID
			 */
			patientList = patientDAOInf.searchPatientByPatientName(patientForm.getPatientName(),
					loginForm.getPracticeID(), loginForm.getClinicID(), patientForm.getSearchCriteria());

		}

		/*
		 * Retrieving list of visit for medical billing report
		 */
		medicalBillingList = reportDAOInf.retrieveListForMedicalReport(patientForm.getPatientID(),
				patientForm.getPatientName());

		request.setAttribute("showModal", "show");

		String patientCheck = "Patient";

		request.setAttribute("patientCheck", patientCheck);

		if (message.equalsIgnoreCase("success")) {

			File inputFile = new File(realPath + "/" +pdfOutFIleName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfOutFIleName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, pdfOutFIleName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(pdfOutFIleName);
			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Billing", loginForm.getUserID());

			return SUCCESS;
		} else {

			request.setAttribute("pdfFailMsg", "Failed to print billing report. check server logs for more details.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Billing Exception occurred", loginForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderMedicalNotes() throws Exception {
		patientDAOInf = new PatientDAOImpl();
		reportDAOInf = new ReportDAOImpl();

		/*
		 * Getting clinicID from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		String patientName = patientForm.getPatientName();

		if (patientName == null || patientName == "") {

			/*
			 * retrieving all available patient list
			 */
			patientList = patientDAOInf.retrievePatientList(loginForm.getPracticeID(), loginForm.getClinicID());

		} else {

			/*
			 * Retrieving patient list on the basis of patientName entered into search box
			 * and clinicID
			 */
			patientList = patientDAOInf.searchPatientByPatientName(patientForm.getPatientName(),
					loginForm.getPracticeID(), loginForm.getClinicID(), patientForm.getSearchCriteria());

		}

		/*
		 * Retrieving list of visit for medical billing report
		 */
		medicalBillingList = reportDAOInf.retrieveListForMedicalReport(patientForm.getPatientID(),
				patientForm.getPatientName());

		if (medicalBillingList.size() > 0) {

			request.setAttribute("showMedNoteModal", "show");

			String patientCheck = "Patient";

			request.setAttribute("patientCheck", patientCheck);

			return SUCCESS;

		} else {

			request.setAttribute("noVisitMsg", "No visit added for this patient yet. Please add a new visit.");

			request.setAttribute("showMedNoteModal", "hide");

			String patientCheck = "Patient";

			request.setAttribute("patientCheck", patientCheck);

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String printMedicalNotesReport() throws Exception {
		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		System.out.println("Last visit ID : " + patientForm.getLastVisitID());

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();
		
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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		System.out.println("Real path is :: " + realPath);

		//String pdfOutFIleName = realPath + "MedicalNoteReport_" + patientForm.getPatientID() + "_"
		String pdfOutFIleName = "MedicalNoteReport_" + patientForm.getPatientID() + "_"+ patientForm.getVisitID() + ".pdf";

		message = convertToPDFUtil.printMedicalNotePDFReport(patientForm.getPatientID(), patientForm.getVisitID(),
				realPath, pdfOutFIleName);

		String patientName = patientForm.getPatientName();

		if (patientName == null || patientName == "") {

			/*
			 * retrieving all available patient list
			 */
			patientList = patientDAOInf.retrievePatientList(loginForm.getPracticeID(), loginForm.getClinicID());

		} else {

			/*
			 * Retrieving patient list on the basis of patientName entered into search box
			 * and clinicID
			 */
			patientList = patientDAOInf.searchPatientByPatientName(patientForm.getPatientName(),
					loginForm.getPracticeID(), loginForm.getClinicID(), patientForm.getSearchCriteria());

		}

		/*
		 * Retrieving list of visit for medical billing report
		 */
		medicalBillingList = reportDAOInf.retrieveListForMedicalReport(patientForm.getPatientID(),
				patientForm.getPatientName());

		request.setAttribute("showMedNoteModal", "show");

		String patientCheck = "Patient";

		request.setAttribute("patientCheck", patientCheck);

		if (message.equalsIgnoreCase("success")) {

			File inputFile = new File(realPath + "/" +pdfOutFIleName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfOutFIleName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, pdfOutFIleName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(pdfOutFIleName);

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Medical Notes", loginForm.getUserID());

			return SUCCESS;
		} else {

			request.setAttribute("pdfFailMsg", "Failed to print billing report. check server logs for more details.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Medical Notes Exception occurred",
					loginForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void reportPDFDownload() throws Exception {
		String pdfOutFIleName = request.getParameter("pdfOutPath");
		System.out.println("PDF File path from action ::::: " + pdfOutFIleName);

		response.setContentType("application/pdf");
		InputStream in = new FileInputStream(pdfOutFIleName);
		OutputStream out11 = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out11.write(buf, 0, len);
		}
		in.close();
		out11.close();
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String printMedicalHistory() throws Exception {
		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();
		
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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		System.out.println("Real path is :: " + realPath);

		//String pdfOutFIleName = realPath + "MedicalHistoryReport_" + patientForm.getPatientID() + ".pdf";
		String pdfOutFIleName = "MedicalHistoryReport_" + patientForm.getPatientID() + ".pdf";

		message = convertToPDFUtil.printMedicalNoteHistoryPDFReport(patientForm.getPatientID(), realPath,
				pdfOutFIleName);

		String patientName = patientForm.getPatientName();

		if (patientName == null || patientName == "") {

			/*
			 * retrieving all available patient list
			 */
			patientList = patientDAOInf.retrievePatientList(loginForm.getPracticeID(), loginForm.getClinicID());

		} else {

			/*
			 * Retrieving patient list on the basis of patientName entered into search box
			 * and clinicID
			 */
			patientList = patientDAOInf.searchPatientByPatientName(patientForm.getPatientName(),
					loginForm.getPracticeID(), loginForm.getClinicID(), patientForm.getSearchCriteria());

		}

		String patientCheck = "Patient";

		request.setAttribute("patientCheck", patientCheck);

		if (message.equalsIgnoreCase("success")) {

			File inputFile = new File(realPath + "/" +pdfOutFIleName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfOutFIleName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, pdfOutFIleName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(pdfOutFIleName);

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Medical History", loginForm.getUserID());

			return SUCCESS;
		} else {

			addActionError("Failed to print billing report. check server logs for more details.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Report: Medical History Exception occurred",
					loginForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchPatientReport() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderPatientReport() throws Exception {

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		System.out.println("inside render report "+loginForm.getPracticeID()+""+loginForm.getClinicName());
		// Setting add opd page name value in pageName variable
		//setPageName("patientReport.jsp");
		
		tableList = configXMLUtil.retrievePatientRelatedTableList(
				patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

		//patientColumnList = reportDAOInf.retrievePatientColumnList("Patient AS pt", "id");
		
		patientColumnList = reportDAOInf.retrieveReportConfigPatientColumnList("Patient AS pt", "id");

		return SUCCESS;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveColumnNameList() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			/*
			 * Checking whether selected tag is singular or composite tag, if composite then
			 * retrieving the list of all table tags with the parent values as selected
			 * table name
			 */
			boolean check = configXMLUtil.verifySingularOrCompositeTag(patientForm.getCheck(),
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			if (check) {
				System.out.println("inside check");
				/**
				 * Retrieving value of key attribute of selected tag.
				 */
				String keyValue = configXMLUtil.retrieveKeyAttribute(patientForm.getCheck(),
						patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

				//values = reportDAOInf.retrieveColumnList(patientForm.getCheck(), keyValue);
				values = reportDAOInf.retrieveReportConfigColumnList(patientForm.getCheck(), keyValue);
				System.out.println("values- column list::"+values);
			} else {
				/*
				 * values = configXMLUtil.retrieveVisitRelatedTableList(
				 * patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()),
				 * loginForm.getClinicName(), patientForm.getCheck());
				 */
				
				values = configXMLUtil.retrieveReportConfigVisitRelatedTableList(
						patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName(),
						patientForm.getCheck());
			}

			String finalWhere = configXMLUtil.retrieveConditionByTaFromQuery(patientForm.getParentTableName(),
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			object.put("finalWhere", finalWhere);

			array.add(object);

			values.put("Release2", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve column name list based on table name.");

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
	public void verifyColumn() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			/*
			 * Checking whether selected tag is singular or composite tag, if composite then
			 * retrieving the list of all table tags with the parent values as selected
			 * table name
			 */
			boolean check = configXMLUtil.verifySingularOrCompositeTag(patientForm.getCheck(),
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			if (check) {
				/**
				 * Retrieving value of key attribute of selected tag.
				 */
				String keyValue = configXMLUtil.retrieveKeyAttribute(patientForm.getCheck(),
						patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

				values = reportDAOInf.retrieveColumnList(patientForm.getCheck(), keyValue);
			} else {
				values = configXMLUtil.retrieveVisitRelatedTableList(
						patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName(),
						patientForm.getCheck());
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve column name list based on table name.");

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
	public void getInitialWhereConditionByFromQuery() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			object.put("conditionValue", configXMLUtil.retrieveConditionByTaFromQuery(patientForm.getCheck(),
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName()));

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve initial where condition based on from query generated from UI");

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
	public void verifyFkeyExists() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			values = configXMLUtil.verifyfKeyExists(patientForm.getCheck(),
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to verify fkey into XML");

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
	public String generateClinicalReport() throws Exception {

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "ClinicalDataReport_" + dateFormat.format(new Date()) + ".xls";

		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());

		message = serviceInf.geterateClinicalDataReport(patientForm, excelFileName, realPath);

		if (message.equals("success")) {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("exception")) {

			addActionError(
					"Exception occurred while generating excel file for report. Please check server logs for more details.");

			tableList = configXMLUtil.retrievePatientRelatedTableList(
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			patientColumnList = reportDAOInf.retrievePatientColumnList("Patient AS pt", "id");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report Exception Occurred",
					loginForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to generate clinical data report. Please check server logs for more details.");

			tableList = configXMLUtil.retrievePatientRelatedTableList(
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			patientColumnList = reportDAOInf.retrievePatientColumnList("Patient AS pt", "id");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report Exception Occurred",
					loginForm.getUserID());

			return ERROR;

		}

	}

	public String generateClinicalReportForAyurved() throws Exception {

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		patientDAOInf = new PatientDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "ClinicalDataReport_" + dateFormat.format(new Date()) + ".xls";

		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());
		patientForm.setPracticeID(loginForm.getPracticeID());

		message = serviceInf.geterateClinicalDataReportForAyurved(patientForm, excelFileName, realPath);

		if (message.equals("success")) {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("exception")) {

			addActionError(
					"Exception occurred while generating excel file for report. Please check server logs for more details.");

			tableList = configXMLUtil.retrievePatientRelatedTableList(
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			patientColumnList = reportDAOInf.retrievePatientColumnList("Patient AS pt", "id");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report Exception Occurred",
					loginForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to generate clinical data report. Please check server logs for more details.");

			tableList = configXMLUtil.retrievePatientRelatedTableList(
					patientDAOInf.retrievePracticeNameByID(loginForm.getPracticeID()), loginForm.getClinicName());

			patientColumnList = reportDAOInf.retrievePatientColumnList("Patient AS pt", "id");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Generate Clinical Report Exception Occurred",
					loginForm.getUserID());

			return ERROR;

		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retriveSavedQueryValues() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		try {

			values = reportDAOInf.retriveSavedQueryValuesBySaveQueryID(patientForm.getSaveQueryID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve saved query values based on savedQueryID");

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
	public void retriveColumnNamesForSavedQueryValues() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		try {

			values = reportDAOInf.retriveColumnNamesForSavedQueryValuesBySaveQueryID(patientForm.getParentTableName());
			
			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve saved query values based on savedQueryID");

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
	public String renderExecuteReport() throws Exception {
		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		/*
		 * Getting form name to be display based on logged in User
		 */
		String[] formNameArray = loginForm.getOPDJSPName().split("&");

		System.out.println("Add OPD JSP Page name for logged in user is ::: " + formNameArray[0].trim());

		reportDAOInf = new ReportDAOImpl();

		saveQueryList = reportDAOInf.retrieveSavedQueryList(loginForm.getClinicID());

		if (saveQueryList == null || saveQueryList.size() == 0) {
			addActionError("No saved query found.");
		}

		
		  if (formNameArray[0].trim().equals("generalOPDNew.jsp")) {
		  
		  // Setting add opd page name value in pageName variable
		  setPageName("viewClinicalReports.jsp"); }
		 
		return SUCCESS;
	}

	public String executeClinicalReport() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		/*
		 * Getting form name to be display based on logged in User
		 */
		String[] formNameArray = loginForm.getOPDJSPName().split("&");
		
		System.out.println("Add OPD JSP Page name for logged in user is ::: " + formNameArray[0].trim());

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		reportDAOInf = new ReportDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		patientForm.setClinicID(loginForm.getClinicID());
		patientForm.setPracticeID(loginForm.getPracticeID());

		saveQueryList = reportDAOInf.retrieveSavedQueryList(loginForm.getClinicID());

		if (saveQueryList == null || saveQueryList.size() == 0) {
			addActionError("No saved query found.");
		}

		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "ClinicalDataReport_" + dateFormat.format(new Date()) + ".xls";

		message = serviceInf.executeClinicalDataReport(patientForm, excelFileName, realPath);

		// message = excelUtil.generateClinicalReport(patientForm, reportFilePath,
		// excelFileName);

		if (message.equals("success")) {

			daoInf.insertAudit(request.getRemoteAddr(), "Execute Clinical Report", loginForm.getUserID());

			return SUCCESS;

		} else {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Execute Clinical Report Exception Occurred",
					loginForm.getUserID());

			addActionError("Failed to execute clinical report. Please check server logs for more details.");

			return ERROR;
		}
	}

	public String updateClinicalReport() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		/*
		 * Getting form name to be display based on logged in User
		 */
		String[] formNameArray = loginForm.getOPDJSPName().split("&");
		
		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		reportDAOInf = new ReportDAOImpl();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		patientForm.setClinicID(loginForm.getClinicID());
		patientForm.setPracticeID(loginForm.getPracticeID());

		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "ClinicalDataReport_" + dateFormat.format(new Date()) + ".xls";

		message = serviceInf.updateClinicalDataReport(patientForm, excelFileName, realPath);

		if (message.equals("success")) {

			saveQueryList = reportDAOInf.retrieveSavedQueryList(loginForm.getClinicID());

			if (saveQueryList == null || saveQueryList.size() == 0) {
				addActionError("No saved query found.");
			}
			
			
			 if (formNameArray[0].trim().equals("generalOPDNew.jsp")) {
				  
				  // Setting add opd page name value in pageName variable
				  setPageName("viewClinicalReports.jsp"); 
				  }
			daoInf.insertAudit(request.getRemoteAddr(), "Update Clinical Report", loginForm.getUserID());

			return SUCCESS;

		} else {

			saveQueryList = reportDAOInf.retrieveSavedQueryList(loginForm.getClinicID());

			if (saveQueryList == null || saveQueryList.size() == 0) {
				addActionError("No saved query found.");
			}
			
			if (formNameArray[0].trim().equals("generalOPDNew.jsp")) {
				  
				  // Setting add opd page name value in pageName variable
				  setPageName("viewClinicalReports.jsp"); 
				  }
			
			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Update Clinical Report Exception Occurred",
					loginForm.getUserID());

			addActionError("Failed to update clinical report. Please check server logs for more details.");

			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderBillingReport() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateBillingReport() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		LoginDAOInf daoInf = new LoginDAOImpl();

		reportDAOInf = new ReportDAOImpl();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ExcelUtil excelUtil = new ExcelUtil();

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
		
		request.setAttribute("startDate", patientForm.getStartDate());
		request.setAttribute("endDate", patientForm.getEndDate());
		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());

		/*
		 * Retrieving report file path from edhanvantari.xml in order to store all
		 * reports file into it
		 */

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		
		String excelFileName = "BillingDataReport_" + dateFormat.format(new Date()) + ".xls";

		message = excelUtil.generateBillingReport(patientForm, realPath, excelFileName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);
			patientForm.setFileName(excelFileName);

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Billing Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("input")) {

			addActionError("No record found for selected criteria.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Billing Report Exception Occurred", loginForm.getUserID());

			return ERROR;

		} else {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Billing Report Exception Occurred", loginForm.getUserID());

			addActionError("Failed to generate billing data report. Please check server logs for more details.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderCreditReport() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateCreditReport() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		LoginDAOInf daoInf = new LoginDAOImpl();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ExcelUtil excelUtil = new ExcelUtil();

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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();


		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		/*
		 * Retrieving report file path from edhanvantari.xml in order to store all
		 * reports file into it
		 */

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		
		String excelFileName = "CreditReport_" + dateFormat.format(new Date()) + ".xls";

		message = excelUtil.generateCreditReport(patientForm, realPath, excelFileName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(excelFileName);
			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Credit Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("input")) {

			addActionError("No record found for selected criteria.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Credit Report Exception Occurred", loginForm.getUserID());

			return ERROR;

		} else {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Credit Report Exception Occurred", loginForm.getUserID());

			addActionError("Failed to generate credit report. Please check server logs for more details.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderStockReport() throws Exception {

		PrescriptionManagementDAOInf managementDAOInf = new PrescriptionManagementDAOImpl();

		supplierList = managementDAOInf.retrieveSupplierList();

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStockReport() throws Exception {

		PrescriptionManagementDAOInf managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		LoginDAOInf daoInf = new LoginDAOImpl();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ExcelUtil excelUtil = new ExcelUtil();

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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();


		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		supplierList = managementDAOInf.retrieveSupplierList();

		/*
		 * Retrieving report file path from edhanvantari.xml in order to store all
		 * reports file into it
		 */

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		
		String excelFileName = "StockReport_ " + dateFormat.format(new Date()) + ".xls";

		message = excelUtil.generateStockReport(patientForm, realPath, excelFileName);

		if (message.equals("success")) {
			
			File inputFile = new File(realPath+ "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(excelFileName);

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Stock Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("input")) {

			addActionError("No record found for selected criteria.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Stock Report Exception Occurred", loginForm.getUserID());

			return ERROR;

		} else {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Stock Report Exception Occurred", loginForm.getUserID());

			addActionError("Failed to generate stock report. Please check server logs for more details.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderSalesReport() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateSalesReport() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		LoginDAOInf daoInf = new LoginDAOImpl();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ConfigurationUtil util = new ConfigurationUtil();
		
		ExcelUtil excelUtil = new ExcelUtil();

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
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();


		patientForm.setUserID(loginForm.getUserID());
		patientForm.setClinicID(loginForm.getClinicID());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

		/*
		 * Retrieving report file path from edhanvantari.xml in order to store all
		 * reports file into it
		 */

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");
		
		String excelFileName = "SalesReport_" + dateFormat.format(new Date()) + ".xls";

		message = excelUtil.generateSalesReport(patientForm, realPath, excelFileName);

		if (message.equals("success")) {
			
			File inputFile = new File(realPath+ "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3reportFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, excelFileName)).getObjectContent();
			
			patientForm.setFileInputStream(s3ObjectInputStream);

			patientForm.setFileName(excelFileName);

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Sales Report", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equals("input")) {

			addActionError("No record found for selected criteria.");

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Sales Report Exception Occurred", loginForm.getUserID());

			return ERROR;

		} else {

			// Inserting into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Sales Report Exception Occurred", loginForm.getUserID());

			addActionError("Failed to generate sales report. Please check server logs for more details.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String retrieveStockDetails() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		reportDAOInf = new ReportDAOImpl();

		productStockList = reportDAOInf.retrieveProductTotalNetStock(loginForm.getClinicID(), ActivityStatus.NOT_EMPTY);

		request.setAttribute("productStockList", productStockList);
		
		request.setAttribute("statusCheck", "NotEmpty");

		if (productStockList.size() > 0) {

			return SUCCESS;
		} else {

			addActionError("No stock available for product.");

			return ERROR;
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveStockListByProductID() throws Exception {

		JSONObject values = new JSONObject();

		JSONObject object = null;

		JSONArray array = new JSONArray();

		response.setCharacterEncoding("UTF-8");

		reportDAOInf = new ReportDAOImpl();

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		try {

			productStockList = reportDAOInf.retrieveStockReceiptListByProductID(patientForm.getProductID(),
					loginForm.getClinicID());

			for (PatientForm form : productStockList) {

				object = new JSONObject();

				object.put("receiptNo", form.getReceiptNo());
				object.put("receiptDate", form.getReceiptDate());
				object.put("netStock", form.getNewDrugQuantityName());

				array.add(object);

				values.put("Release", array);

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Failed to retrieve stock details list based on product ID.");

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
	public String render3CForm() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generate3CForm() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		LoginDAOInf daoInf = new LoginDAOImpl();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ExcelUtil excelUtil = new ExcelUtil();

		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderStockDetails() throws Exception{
		
		request.setAttribute("productStockList", productStockList);
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String retrieveEmptyStockDetails() throws Exception {

		/*
		 * Getting user role from session
		 */
		LoginForm loginForm = (LoginForm) session.get("USER");

		reportDAOInf = new ReportDAOImpl();

		productStockList = reportDAOInf.retrieveProductTotalNetStock(loginForm.getClinicID(), ActivityStatus.EMPTY);

		request.setAttribute("productStockList", productStockList);
		
		request.setAttribute("statusCheck", "Empty");

		if (productStockList.size() > 0) {

			return SUCCESS;
		} else {

			addActionError("No stock available for product.");

			return ERROR;
		}

	}

	/**
	 * @return the productStockList
	 */
	public List<PatientForm> getProductStockList() {
		return productStockList;
	}

	/**
	 * @param productStockList
	 *            the productStockList to set
	 */
	public void setProductStockList(List<PatientForm> productStockList) {
		this.productStockList = productStockList;
	}

	/**
	 * @return the supplierList
	 */
	public HashMap<Integer, String> getSupplierList() {
		return supplierList;
	}

	/**
	 * @param supplierList
	 *            the supplierList to set
	 */
	public void setSupplierList(HashMap<Integer, String> supplierList) {
		this.supplierList = supplierList;
	}

	/**
	 * @return the saveQueryList
	 */
	public List<PatientForm> getSaveQueryList() {
		return saveQueryList;
	}

	/**
	 * @param saveQueryList
	 *            the saveQueryList to set
	 */
	public void setSaveQueryList(List<PatientForm> saveQueryList) {
		this.saveQueryList = saveQueryList;
	}

	/**
	 * @return the patientColumnList
	 */
	public HashMap<String, String> getPatientColumnList() {
		return patientColumnList;
	}

	/**
	 * @param patientColumnList
	 *            the patientColumnList to set
	 */
	public void setPatientColumnList(HashMap<String, String> patientColumnList) {
		this.patientColumnList = patientColumnList;
	}

	/**
	 * @return the tableList
	 */
	public HashMap<String, String> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList
	 *            the tableList to set
	 */
	public void setTableList(HashMap<String, String> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the medicalBillingList
	 */
	public List<PatientForm> getMedicalBillingList() {
		return medicalBillingList;
	}

	/**
	 * @param medicalBillingList
	 *            the medicalBillingList to set
	 */
	public void setMedicalBillingList(List<PatientForm> medicalBillingList) {
		this.medicalBillingList = medicalBillingList;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName
	 *            the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * @return the patientList
	 */
	public List<PatientForm> getPatientList() {
		return patientList;
	}

	/**
	 * @param patientList
	 *            the patientList to set
	 */
	public void setPatientList(List<PatientForm> patientList) {
		this.patientList = patientList;
	}

	/**
	 * @return the patientForm
	 */
	public PatientForm getPatientForm() {
		return patientForm;
	}

	/**
	 * @param patientForm
	 *            the patientForm to set
	 */
	public void setPatientForm(PatientForm patientForm) {
		this.patientForm = patientForm;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public PatientForm getModel() {

		return patientForm;
	}

}
