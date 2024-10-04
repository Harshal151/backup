package com.edhanvantari.action;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.edhanvantari.form.PatientForm;
import com.edhanvantari.util.ConfigListenerUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DownloadAction extends ActionSupport
		implements ModelDriven<PatientForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PatientForm patientForm = new PatientForm();

	private Map<String, Object> session = null;

	HttpServletRequest request;
	HttpServletResponse response;

	/**
	 * 
	 * @throws Exception
	 */
	public void ViewFiles() throws Exception {

		System.out.println("Inside......");

		HttpServletRequest request = ServletActionContext.getRequest();

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");

		System.out.println("Real path is :: " + realPath);

		ConfigListenerUtil xmlUtil = new ConfigListenerUtil();

		String accessKey = xmlUtil.getAccessKey(realPath);

		String secreteKey = xmlUtil.getSecreteKey(realPath);

		// getting input file location from S3 bucket
		String s3reportFilePath = xmlUtil.getS3RDMLReportFilePath(realPath);

		// getting s3 bucket name
		String bucketName = request.getParameter("bucketName");

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion(realPath);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		String pdfOutFIleName = request.getParameter("pdfOutPath");
		System.out.println("PDF File path from action ::::: " + pdfOutFIleName);

		S3ObjectInputStream s3ObjectInputStream = s3
				.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, pdfOutFIleName))
				.getObjectContent();

		patientForm.setFileInputStream(s3ObjectInputStream);

		patientForm.setFileName(pdfOutFIleName);

		OutputStream out11 = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = patientForm.getFileInputStream().read(buf)) > 0) {
			out11.write(buf, 0, len);
		}

		patientForm.getFileInputStream().close();
		out11.close();

	}

	/**
	 * @return the patientForm
	 */
	public PatientForm getPatientForm() {
		return patientForm;
	}

	/**
	 * @param patientForm the patientForm to set
	 */
	public void setPatientForm(PatientForm patientForm) {
		this.patientForm = patientForm;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	@Override
	public PatientForm getModel() {
		// TODO Auto-generated method stub
		return patientForm;
	}

}
