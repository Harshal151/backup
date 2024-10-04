package com.edhanvantari.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoImpl.ConfigurationDAOImpl;
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoInf.ConfigurationDAOInf;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.form.ConfigurationForm;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ConfigurationAction extends ActionSupport
		implements ModelDriven<ConfigurationForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ConfigurationForm configurationForm = new ConfigurationForm();

	ConfigurationDAOInf configurationDAOInf = null;

	eDhanvantariServiceInf serviceInf = null;

	String message = "success";

	List<ConfigurationForm> configureClinicTypeList;
	List<ConfigurationForm> configureDiagnosisList;
	List<ConfigurationForm> configureReferringDoctorList;
	List<ConfigurationForm> configureFrequencyList;
	List<ConfigurationForm> configureBillingList;
	List<ConfigurationForm> configurePrescriptionList;
	List<ConfigurationForm> configureVisitTypeList;
	List<ConfigurationForm> configureInstructionsList;
	HashMap<Integer, String> billingClinicList;

	List<ConfigurationForm> clinicTypeEditList;
	List<ConfigurationForm> diagnosisEditList;
	List<ConfigurationForm> prescriptionEditList;
	List<ConfigurationForm> billingEditList;
	List<ConfigurationForm> frequencyEditList;
	List<ConfigurationForm> referringDoctEditList;
	List<ConfigurationForm> visitTypeEditList;
	List<ConfigurationForm> instructionsList;
	List<ConfigurationForm> groupTestList;
	List<ConfigurationForm> configureTestList;
	List<ConfigurationForm> TestEditList;
	List<ConfigurationForm> SMSTemplateList;
	List<ConfigurationForm> editSMSTemplateList;
	List<ConfigurationForm> configureLabTestList;
	List<ConfigurationForm> labTestList;
	List<ConfigurationForm> DefaultValueList;
	List<ConfigurationForm> TemplateList;
	List<ConfigurationForm> editTemplateList;
	List<ConfigurationForm> retriveAllGroupTests;
	List<ConfigurationForm> retriveAllLabTests;
	HashMap<Integer, String> labTests;

	ArrayList<String> templateListByType;
	
	HashMap<String, String> selectedTemplateList = null;

	HashMap<Integer, String> caregoryList;

	HttpServletRequest request;
	HttpServletResponse response;

	private Map<String, Object> session = null;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchDiagnoses() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		configureDiagnosisList = configurationDAOInf.searchDiagnosesList(configurationForm.getSearchDiagnosis());

		if (configureDiagnosisList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Diagnosis found for : " + configurationForm.getSearchDiagnosis());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchFrequencies() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		configureFrequencyList = configurationDAOInf.searchFrequencyList(configurationForm.getSearchFrequency(),
				userForm.getPracticeID());

		if (configureFrequencyList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Frequency found for : " + configurationForm.getSearchFrequency());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchRefDoctor() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		configureReferringDoctorList = configurationDAOInf
				.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

		if (configureReferringDoctorList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No referring doctor found for : " + configurationForm.getSearchRefDoctor());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfigureDiagnoses() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Retrieving diagnosis list from PVDiagnosis table
		 */
		configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

		return SUCCESS;
	}
	
	public String renderConfigureLabTest() throws Exception {
		
		configurationDAOInf = new ConfigurationDAOImpl();
		
		JSONObject data = configurationDAOInf.RetrieveTestTypesAndTemplates();
		
		request.setAttribute("getTemplatesByType", data.toString());
		System.out.println("JSON DATA: " + data.toString());
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllDiagnoses() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Retrieving diagnosis list from PVDiagnosis table
		 */
		configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

		if (configureDiagnosisList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Diagnosis found. Please add new Diagnosis.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureDiagnoses() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting diagnosis into PVDiagnosis table
		 */
		message = configurationDAOInf.insertDiagnosis(configurationForm);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Diagnosis configured successfully.");

			/*
			 * Retrieving diagnosis list from PVDiagnosis table
			 */
			configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Diagnoses", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Error while configuring diagnosis. Please check logs for more details.");

			/*
			 * Retrieving diagnosis list from PVDiagnosis table
			 */
			configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Diagnoses Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfigureReferringDoctor() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Retrieving referring doctor list from PVReferringDoctors table
		 */
		configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllRefDoctor() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Retrieving referring doctor list from PVReferringDoctors table
		 */
		configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

		if (configureReferringDoctorList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No referring doctor found. Please add new referring doctor.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureReferringDoctor() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting referring doctor into PVReferringDoctors table
		 */
		message = serviceInf.addReferringDoctor(configurationForm, userForm.getPracticeID());
		// message = configurationDAOInf.insertReferringDoctor(configurationForm);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Referring Doctor configured successfully.");

			/*
			 * Retrieving referring doctor list from PVReferringDoctors table
			 */
			configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Referring Doctor", userForm.getUserID());

			return SUCCESS;

		} else if (message.equals("input")) {

			addActionMessage(
					"Failed to add new referring doctor. Referring doctor with same doctor name already exists.");

			/*
			 * Retrieving referring doctor list from PVReferringDoctors table
			 */
			configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Referring doctor with same doctor name already exists.",
					userForm.getUserID());

			return ERROR;
		} else {

			addActionError("Error while configuring referring doctor. Please check logs for more details.");

			/*
			 * Retrieving referring doctor list from PVReferringDoctors table
			 */
			configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Referring Doctor Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfigureFrequency() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllFrequencies() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Retrieving existing frequency list from table PVFrequency
		 */
		configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

		if (configureFrequencyList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No frequencies found. Please add new Frequency.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureFrequency() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting frequency into PVFrequency table
		 */

		System.out.println("userForm.getPracticeID(): " + userForm.getPracticeID());

		message = serviceInf.addFrequency(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Frequency configured successfully.");

			/*
			 * Retrieving existing frequency list from table PVFrequency
			 */
			configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Frequency", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Frequency with same Category already exists. Please add new Frequency or Category.");

			/*
			 * Retrieving existing frequency list from table PVFrequency
			 */
			configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Frequency with same Category name Already Exist Exeption Occurred", userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Error while configuring frequency. Please check logs for more details.");

			/*
			 * Retrieving existing frequency list from table PVFrequency
			 */
			configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Frequency Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditDiagnosis() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		String searchDiagnosis = configurationForm.getSearchDiagnosis();

		diagnosisEditList = configurationDAOInf.retrieveDiagnosisListByID(configurationForm.getDiagnosisID(),
				searchDiagnosis);

		if (searchDiagnosis == null || searchDiagnosis == "") {

			configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

		} else {

			configureDiagnosisList = configurationDAOInf.searchDiagnosesList(searchDiagnosis);

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteDiagnosis() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchDiagnosis = configurationForm.getSearchDiagnosis();

		message = configurationDAOInf.deleteDiagnosis(configurationForm.getDiagnosisID());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Diagnosis deleted successfully.");

			if (searchDiagnosis == null || searchDiagnosis == "") {

				configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			} else {

				configureDiagnosisList = configurationDAOInf.searchDiagnosesList(searchDiagnosis);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Diagnoses", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("Failed to delete diagnosis. Please check logs for more details.");

			/*
			 * Retrieving diagnosis list from PVDiagnosis table
			 */
			configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Diagnoses Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureDiagnosis() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchDiagnosis = configurationForm.getSearchDiagnosis();

		message = configurationDAOInf.updateDiagnosis(configurationForm);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Diagnosis udpated successfully.");

			diagnosisEditList = configurationDAOInf.retrieveDiagnosisListByID(configurationForm.getDiagnosisID(),
					searchDiagnosis);

			if (searchDiagnosis == null || searchDiagnosis == "") {

				configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			} else {

				configureDiagnosisList = configurationDAOInf.searchDiagnosesList(searchDiagnosis);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Diagnoses", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated diagnosis. Please check logs for more details.");

			diagnosisEditList = configurationDAOInf.retrieveDiagnosisListByID(configurationForm.getDiagnosisID(),
					searchDiagnosis);

			if (searchDiagnosis == null || searchDiagnosis == "") {

				configureDiagnosisList = configurationDAOInf.retrieveExistingDiagnosisList();

			} else {

				configureDiagnosisList = configurationDAOInf.searchDiagnosesList(searchDiagnosis);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Diagnoses Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditFrequency() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchFrequency = configurationForm.getSearchFrequency();

		frequencyEditList = configurationDAOInf.retrieveFrequencyListByID(configurationForm.getFrequencyID(),
				configurationForm.getSearchFrequency());

		if (searchFrequency == null || searchFrequency == "") {

			configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

		} else {

			configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency, userForm.getPracticeID());

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteFrequency() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchFrequency = configurationForm.getSearchFrequency();

		message = configurationDAOInf.deleteFrequency(configurationForm.getFrequencyID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Frequency Inactivated successfully.");

			if (searchFrequency == null || searchFrequency == "") {

				configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			} else {

				configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Frequency Inactivated", userForm.getUserID());

			request.setAttribute("componentMsg", "available");
			return SUCCESS;
		} else {
			addActionError("Failed to updating frequency satatus to InActive. Please check logs for more details.");

			if (searchFrequency == null || searchFrequency == "") {

				configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			} else {

				configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Frequency Inactivated Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureFrequency() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchFrequency = configurationForm.getSearchFrequency();

		message = serviceInf.editFrequency(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Frequency udpated successfully.");

			frequencyEditList = configurationDAOInf.retrieveFrequencyListByID(configurationForm.getFrequencyID(),
					searchFrequency);

			if (searchFrequency == null || searchFrequency == "") {

				configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			} else {

				configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Frequency", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Frequency with same Caregory already exists. Please add new Frequency or Caregory.");

			frequencyEditList = configurationDAOInf.retrieveFrequencyListByID(configurationForm.getFrequencyID(),
					searchFrequency);

			if (searchFrequency == null || searchFrequency == "") {

				configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			} else {

				configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency,
						userForm.getPracticeID());

			}

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Edit Frequency with same Caregory Already Exist Exeption Occurred", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;

		} else {
			addActionError("Failed to udpated frequency. Please check logs for more details.");

			frequencyEditList = configurationDAOInf.retrieveFrequencyListByID(configurationForm.getFrequencyID(),
					searchFrequency);

			if (searchFrequency == null || searchFrequency == "") {

				configureFrequencyList = configurationDAOInf.retrieveExistingFrequencyList(userForm.getPracticeID());

			} else {

				configureFrequencyList = configurationDAOInf.searchFrequencyList(searchFrequency,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Frequency Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditRefDoc() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		String searchRefDoc = configurationForm.getSearchRefDoctor();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		referringDoctEditList = configurationDAOInf.retrieveRefDocListByID(configurationForm.getReferringDoctID(),
				searchRefDoc);

		if (searchRefDoc == null || searchRefDoc == "") {

			configureReferringDoctorList = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

		} else {

			configureReferringDoctorList = configurationDAOInf
					.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRefDoc() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchRefDoc = configurationForm.getSearchRefDoctor();

		message = configurationDAOInf.deleteRefDoc(configurationForm.getReferringDoctID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Referring Doctor  deleted successfully.");

			if (searchRefDoc == null || searchRefDoc == "") {

				configureReferringDoctorList = configurationDAOInf
						.retrieveReferringDoctorList(userForm.getPracticeID());

			} else {

				configureReferringDoctorList = configurationDAOInf
						.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Referring Doctor", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else {
			addActionError("Failed to delete Referring Doctor. Please check logs for more details.");

			if (searchRefDoc == null || searchRefDoc == "") {

				configureReferringDoctorList = configurationDAOInf
						.retrieveReferringDoctorList(userForm.getPracticeID());

			} else {

				configureReferringDoctorList = configurationDAOInf
						.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Referring Doctor Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureReferringDoctor() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchRefDoc = configurationForm.getSearchRefDoctor();

		message = serviceInf.edirReferringDoctor(configurationForm);
		// message = configurationDAOInf.updateRefDoc(configurationForm);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Referring Doctor udpated successfully.");

			referringDoctEditList = configurationDAOInf.retrieveRefDocListByID(configurationForm.getReferringDoctID(),
					searchRefDoc);

			if (searchRefDoc == null || searchRefDoc == "") {

				configureReferringDoctorList = configurationDAOInf
						.retrieveReferringDoctorList(userForm.getPracticeID());

			} else {

				configureReferringDoctorList = configurationDAOInf
						.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Referring Doctor", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equals("input")) {

			addActionMessage(
					"Failed to add new referring doctor. Referring doctor with same doctor name already exists.");

			referringDoctEditList = configurationDAOInf.retrieveRefDocListByID(configurationForm.getReferringDoctID(),
					searchRefDoc);

			if (searchRefDoc == null || searchRefDoc == "") {

				configureReferringDoctorList = configurationDAOInf
						.retrieveReferringDoctorList(userForm.getPracticeID());

			} else {

				configureReferringDoctorList = configurationDAOInf
						.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Referring doctor with same doctor name already exists.",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		} else {
			addActionError("Failed to udpated referring doctor. Please check logs for more details.");

			referringDoctEditList = configurationDAOInf.retrieveRefDocListByID(configurationForm.getReferringDoctID(),
					searchRefDoc);

			if (searchRefDoc == null || searchRefDoc == "") {

				configureReferringDoctorList = configurationDAOInf
						.retrieveReferringDoctorList(userForm.getPracticeID());

			} else {

				configureReferringDoctorList = configurationDAOInf
						.searchreferringDoctorList(configurationForm.getSearchRefDoctor());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Referring Doctor Exception Occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfigureVisitType() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchVisitType() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		configureVisitTypeList = configurationDAOInf.searchVisitTypeList(configurationForm.getSearchVisitTypeName(),
				userForm.getPracticeID());

		if (configureVisitTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No visit type found for visit : " + configurationForm.getSearchVisitTypeName());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllVisitType() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Retrieving diagnosis list from PVDiagnosis table
		 */
		configureVisitTypeList = configurationDAOInf.retrieveExistingVisitTypeList(userForm.getPracticeID());

		if (configureVisitTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Visit Type found. Please add new Visit Type.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureVisitType() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting diagnosis into PVDiagnosis table
		 */
		message = configurationDAOInf.insertVisitType(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Visit Type configured successfully.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Visit Type", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Error while configuring visit Type. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Visit Type Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditVisitType() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		String searchVisitType = configurationForm.getSearchVisitTypeName();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		visitTypeEditList = configurationDAOInf.retrieveVisitTypeListByID(configurationForm.getVisitTypeID(),
				searchVisitType);

		if (searchVisitType == null || searchVisitType == "") {

			configureVisitTypeList = configurationDAOInf.retrieveExistingVisitTypeList(userForm.getPracticeID());

		} else {

			configureVisitTypeList = configurationDAOInf.searchVisitTypeList(searchVisitType, userForm.getPracticeID());

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureVisitType() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchVisitType = configurationForm.getSearchVisitTypeName();

		message = configurationDAOInf.updateVisitType(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Visit Type udpated successfully.");

			visitTypeEditList = configurationDAOInf.retrieveVisitTypeListByID(configurationForm.getVisitTypeID(),
					searchVisitType);

			if (searchVisitType == null || searchVisitType == "") {

				configureVisitTypeList = configurationDAOInf.retrieveExistingVisitTypeList(userForm.getPracticeID());

			} else {

				configureVisitTypeList = configurationDAOInf.searchVisitTypeList(searchVisitType,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Visit Type", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated visit Type. Please check logs for more details.");

			visitTypeEditList = configurationDAOInf.retrieveVisitTypeListByID(configurationForm.getVisitTypeID(),
					searchVisitType);

			if (searchVisitType == null || searchVisitType == "") {

				configureVisitTypeList = configurationDAOInf.retrieveExistingVisitTypeList(userForm.getPracticeID());

			} else {

				configureVisitTypeList = configurationDAOInf.searchVisitTypeList(searchVisitType,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Visit Type Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteVisitType() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		String searchVisitType = configurationForm.getSearchVisitTypeName();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = configurationDAOInf.deletevisitType(configurationForm.getVisitTypeID());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Visit Type deleted successfully.");

			if (searchVisitType == null || searchVisitType == "") {

				configureVisitTypeList = configurationDAOInf.retrieveExistingVisitTypeList(userForm.getPracticeID());

			} else {

				configureVisitTypeList = configurationDAOInf.searchVisitTypeList(searchVisitType,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Visit Type", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("Failed to delete visit type. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Visit Type Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchInstructions() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		configureInstructionsList = configurationDAOInf
				.searchInstructionList(configurationForm.getSearchInstructionName());

		if (configureInstructionsList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No instructions found for : " + configurationForm.getSearchInstructionName());

			return ERROR;

		}
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllInstructions() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Retrieving existing instruction list from table PVInstructions
		 */
		configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

		if (configureInstructionsList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No instructions found. Please add new Instruction.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureInstructions() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting instructions into PVInstruction table
		 */
		// message = configurationDAOInf.insertInstruction(configurationForm);

		message = serviceInf.addInstruction(configurationForm);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Instruction configured successfully.");

			/*
			 * Retrieving existing instruction list from table PVInstructions
			 */
			configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Instruction", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Instruction with same name already exists. Please add new Instruction.");

			/*
			 * Retrieving existing instruction list from table PVInstructions
			 */
			configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Instruction with same name Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {
			addActionError("Error while configuring instruction. Please check logs for more details.");

			/*
			 * Retrieving existing instruction list from table PVInstructions
			 */
			configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Instruction Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureInstructions() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchInstruction = configurationForm.getSearchInstructionName();

		message = serviceInf.editInstruction(configurationForm);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Instruction udpated successfully.");

			instructionsList = configurationDAOInf.retrieveInstructionListByID(configurationForm.getInstructionID(),
					searchInstruction);

			if (searchInstruction == null || searchInstruction == "") {

				configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			} else {

				configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Instruction", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Instruction with same name already exists. Please add new Instruction.");

			instructionsList = configurationDAOInf.retrieveInstructionListByID(configurationForm.getInstructionID(),
					searchInstruction);

			if (searchInstruction == null || searchInstruction == "") {

				configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			} else {

				configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

			}

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Edit Instruction with same name Already Exist Exeption Occurred", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;

		} else {

			addActionError("Failed to udpated instruction. Please check logs for more details.");

			instructionsList = configurationDAOInf.retrieveInstructionListByID(configurationForm.getInstructionID(),
					searchInstruction);

			if (searchInstruction == null || searchInstruction == "") {

				configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			} else {

				configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Instruction Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditInstruction() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		String searchInstruction = configurationForm.getSearchInstructionName();

		instructionsList = configurationDAOInf.retrieveInstructionListByID(configurationForm.getInstructionID(),
				searchInstruction);

		if (searchInstruction == null || searchInstruction == "") {

			configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

		} else {

			configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteInstruction() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchInstruction = configurationForm.getSearchInstructionName();

		message = configurationDAOInf.deleteInstruction(configurationForm.getInstructionID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Instruction deleted successfully.");

			if (searchInstruction == null || searchInstruction == "") {

				configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			} else {

				configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Instruction", userForm.getUserID());

			request.setAttribute("componentMsg", "available");
			return SUCCESS;
		} else {
			addActionError("Failed to delete instruction. Please check logs for more details.");

			if (searchInstruction == null || searchInstruction == "") {

				configureInstructionsList = configurationDAOInf.retrieveExistingInstructionsList();

			} else {

				configureInstructionsList = configurationDAOInf.searchInstructionList(searchInstruction);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Configure Instruction Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchTest() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		configureTestList = configurationDAOInf.searchTestList(configurationForm.getSearchTest());

		if (configureTestList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Test details found for : " + configurationForm.getSearchTest());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllTests() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Retrieving diagnosis list from PVDiagnosis table
		 */
		configureTestList = configurationDAOInf.retrieveExistingTestsList();

		if (configureTestList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Test details found. Please add new Details.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		String searchTest = configurationForm.getSearchTest();

		TestEditList = configurationDAOInf.retrieveTestDetailsListByID(configurationForm.getTestID(), searchTest);

		if (searchTest == null || searchTest == "") {

			configureTestList = configurationDAOInf.retrieveExistingTestsList();

		} else {

			configureTestList = configurationDAOInf.searchTestList(searchTest);

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * INserting Tests into PVLabTest table
		 */

		message = serviceInf.addTestDetails(configurationForm);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Test details configured successfully.");

			/*
			 * Retrieving Tests list from PVDiagnosis table
			 */
			configureTestList = configurationDAOInf.retrieveExistingTestsList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Test Details", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Test name already exists. Please add new Test name.");

			/*
			 * Retrieving Tests list from PVDiagnosis table
			 */
			configureTestList = configurationDAOInf.retrieveExistingTestsList();
			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Test Details Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Error while configuring Test details. Please check logs for more details.");

			/*
			 * Retrieving Tests list from PVDiagnosis table
			 */
			configureTestList = configurationDAOInf.retrieveExistingTestsList();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Test Details Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureTests() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchTest = configurationForm.getSearchTest();

		// message = configurationDAOInf.updateDiagnosis(configurationForm);
		message = serviceInf.editTestDetails(configurationForm);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Test details udpated successfully.");

			TestEditList = configurationDAOInf.retrieveTestDetailsListByID(configurationForm.getTestID(), searchTest);

			if (searchTest == null || searchTest == "") {

				configureTestList = configurationDAOInf.retrieveExistingTestsList();

			} else {

				configureTestList = configurationDAOInf.searchTestList(searchTest);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Test Details", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Test name already exists. Please add new Test name.");

			TestEditList = configurationDAOInf.retrieveTestDetailsListByID(configurationForm.getTestID(), searchTest);

			if (searchTest == null || searchTest == "") {

				configureTestList = configurationDAOInf.retrieveExistingTestsList();

			} else {

				configureTestList = configurationDAOInf.searchTestList(searchTest);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Test name already exists",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");
			return ERROR;

		} else {
			addActionError("Failed to udpated Test details. Please check logs for more details.");

			TestEditList = configurationDAOInf.retrieveTestDetailsListByID(configurationForm.getTestID(), searchTest);

			if (searchTest == null || searchTest == "") {

				configureTestList = configurationDAOInf.retrieveExistingTestsList();

			} else {

				configureTestList = configurationDAOInf.searchTestList(searchTest);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Test details Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String addSMSTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addSMSTemplate(configurationForm);

		if (message.equals("success")) {

			addActionMessage("SMS Template added successfully.");

			// retrieving sms template list from smstemplate table
			SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add SMS Template", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("SMS Template with same name already exists. Please add new SMS Template.");

			// retrieving sms template list from smstemplate table
			SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add SMS Template Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add SMS Template. Please check server logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add SMS Template Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	public String editSMSTemplate() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editSMSTemplate(configurationForm);

		if (message.equals("success")) {

			addActionMessage("SMS Template updated successfully.");

			String searchSMSTemplateName = configurationForm.getSearchSMSTemplate();

			if (searchSMSTemplateName == null || searchSMSTemplateName == "") {

				// retrieving SMS Template list from SMS Template table
				SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			} else {

				SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchSMSTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit SMS Template", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("SMS Template with same name already exists. Please add new SMS Template.");

			String searchSMSTemplateName = configurationForm.getSearchSMSTemplate();

			if (searchSMSTemplateName == null || searchSMSTemplateName == "") {

				// retrieving SMS Template list from SMS Template table
				SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			} else {

				SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchSMSTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit SMS Template Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update category. Please check server logs for more details.");

			String searchSMSTemplateName = configurationForm.getSearchSMSTemplate();

			if (searchSMSTemplateName == null || searchSMSTemplateName == "") {

				// retrieving SMS Template list from SMS Template table
				SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			} else {

				SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchSMSTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit SMS Template Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	public String renderEditSMSTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		editSMSTemplateList = configurationDAOInf.retrieveSMSTemplateByID(configurationForm.getSMSTemplateID(),
				configurationForm.getSearchSMSTemplate());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchCategoryName = configurationForm.getSearchSMSTemplate();

		if (searchCategoryName == null || searchCategoryName == "") {

			// retrieving category list from Category table
			SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

		} else {

			SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchCategoryName);

		}

		return SUCCESS;

	}

	public String searchSMSTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		SMSTemplateList = configurationDAOInf.searchSMSTemplate(configurationForm.getSearchSMSTemplate());

		if (SMSTemplateList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No SMS Template found for : " + configurationForm.getSearchSMSTemplate());

			return ERROR;

		}
	}

	public String viewAllSMSTemplates() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

		if (SMSTemplateList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No SMSTemplate found. Please add new SMSTemplate.");

			return ERROR;

		}
	}

	public String deleteSMSTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = configurationDAOInf.deleteSMSTemplate(configurationForm.getSMSTemplateID());

		if (message.equals("success")) {

			addActionMessage("Category deleted successfully.");

			String searchSMSTemplateName = configurationForm.getSearchSMSTemplate();

			if (searchSMSTemplateName == null || searchSMSTemplateName == "") {

				// retrieving category list from Category table
				SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			} else {

				SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchSMSTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete category. Please check server logs for more details.");

			String searchSMSTemplateName = configurationForm.getSearchSMSTemplate();

			if (searchSMSTemplateName == null || searchSMSTemplateName == "") {

				// retrieving category list from Category table
				SMSTemplateList = configurationDAOInf.retrieveAllSMSTemplates();

			} else {

				SMSTemplateList = configurationDAOInf.searchSMSTemplate(searchSMSTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchLabTests() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
				userForm.getPracticeID());

		if (configureLabTestList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No lab test found for : " + configurationForm.getSearchLabTestsName());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllLabTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();
		
		JSONObject data = configurationDAOInf.RetrieveTestTypesAndTemplates();
		
		request.setAttribute("getTemplatesByType", data.toString());
		System.out.println("JSON DATA: " + data.toString());

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		/*
		 * Retrieving existing instruction list from table PVInstructions
		 */
		configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

		if (configureLabTestList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No lab test found. Please add new lab test.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureLabTests() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addLabTest(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Lab Test configured successfully.");

			configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());
			
			int labTestID = configurationDAOInf.retrieveLabTestID(configurationForm.getTest());
			
			if (configurationForm.getTestTypeTemplates() == null || configurationForm.getTestTypeTemplates().length() <= 3) {
			    System.out.println("No templates to add.");
			    message = "No templates to add";
			} else {
				message = configurationDAOInf.insertTemplatesTestTypeData(configurationForm.getTestTypeTemplates(), labTestID, configurationForm.getTestType());
			}
			
			
			if(message.equalsIgnoreCase("success")) {
				
				selectedTemplateList = configurationDAOInf.retrieveSelectedTemplates(labTestID);
				request.setAttribute("selectedTemplateList", selectedTemplateList);
				System.out.printf("selectedTemplateList: %s%n", selectedTemplateList);	
			}
						
			System.out.printf("testTypeTemplates: %s%n", configurationForm.getTestTypeTemplates());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Lab Test", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Lab test with same panel and test already exists. Please add new lab test.");

			configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Lab Test with same panel and test Already Exist Exeption Occurred", userForm.getUserID());

			return ERROR;

		} else {
			addActionError("Error while configuring lab test. Please check logs for more details.");

			configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Instruction Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editConfigureLabTest() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchLabTest = configurationForm.getSearchLabTestsName();

		message = serviceInf.editLabTest(configurationForm, userForm.getPracticeID());

		if (message.equalsIgnoreCase("success")) {
			
			int labTestID = configurationDAOInf.retrieveLabTestID(configurationForm.getTest());
			
			System.out.printf("configurationForm.getTestTypeTemplatesRemoved(): %s%n", configurationForm.getTestTypeTemplatesRemoved());

			if (configurationForm.getTestTypeTemplatesRemoved() == null || configurationForm.getTestTypeTemplatesRemoved().length() <= 3) {
			    System.out.println("No templates to remove.");
			    message = "No templates to remove";
			} else {
			    message = configurationDAOInf.removeTemplatesTestTypeData(configurationForm.getTestTypeTemplatesRemoved(), labTestID);			  
			}
			
			if (configurationForm.getTestTypeTemplates() == null || configurationForm.getTestTypeTemplates().isEmpty()) {
			    System.out.println("No templates to select.");
			    message = "No templates to select";
			} else {
				message = configurationDAOInf.insertTemplatesTestTypeData(configurationForm.getTestTypeTemplates(), labTestID, configurationForm.getTestType());
			}

			if(message.equalsIgnoreCase("success")){
				selectedTemplateList = configurationDAOInf.retrieveSelectedTemplates(labTestID);
				request.setAttribute("selectedTemplateList", selectedTemplateList);
			}
			
			addActionMessage("Lab test udpated successfully.");

			labTestList = configurationDAOInf.retrieveLabTestListByID(configurationForm.getLabTestID(), searchLabTest);

			DefaultValueList = configurationDAOInf.retrieveDefaultValueList(configurationForm.getLabTestID());

			if (searchLabTest == null || searchLabTest == "") {

				configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			} else {

				configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Lab test", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Lab test with same panel and test already exists. Please add new Lab test.");

			labTestList = configurationDAOInf.retrieveLabTestListByID(configurationForm.getLabTestID(), searchLabTest);

			DefaultValueList = configurationDAOInf.retrieveDefaultValueList(configurationForm.getLabTestID());

			if (searchLabTest == null || searchLabTest == "") {

				configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			} else {

				configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
						userForm.getPracticeID());

			}

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Edit Lab Test with same panel and test Already Exist Exeption Occurred", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;

		} else {

			addActionError("Failed to udpated lab test. Please check logs for more details.");

			labTestList = configurationDAOInf.retrieveLabTestListByID(configurationForm.getLabTestID(), searchLabTest);

			DefaultValueList = configurationDAOInf.retrieveDefaultValueList(configurationForm.getLabTestID());

			if (searchLabTest == null || searchLabTest == "") {

				configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			} else {

				configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Lab test Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditLabTest() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchLabTest = configurationForm.getSearchLabTestsName();
		
		JSONObject data = configurationDAOInf.RetrieveTestTypesAndTemplates();
		
		System.out.println("JSON DATA update: " + data.toString());

		labTestList = configurationDAOInf.retrieveLabTestListByID(configurationForm.getLabTestID(), searchLabTest);
		
		System.out.println("labTestID Update: " + configurationForm.getLabTestID());
		
		selectedTemplateList = configurationDAOInf.retrieveSelectedTemplates(configurationForm.getLabTestID());
		
		request.setAttribute("selectedTemplateList", selectedTemplateList);

		DefaultValueList = configurationDAOInf.retrieveDefaultValueList(configurationForm.getLabTestID());

		if (searchLabTest == null || searchLabTest == "") {

			configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

		} else {

			configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
					userForm.getPracticeID());

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");
		
		request.setAttribute("getTemplatesByType", data.toString());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteLabTest() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchLabTest = configurationForm.getSearchLabTestsName();

		message = configurationDAOInf.deleteLabTest(configurationForm.getLabTestID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Lab test deleted successfully.");

			if (searchLabTest == null || searchLabTest == "") {

				configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			} else {

				configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Lab Test", userForm.getUserID());

			request.setAttribute("componentMsg", "available");
			return SUCCESS;
		} else {
			addActionError("Failed to delete lab test. Please check logs for more details.");

			if (searchLabTest == null || searchLabTest == "") {

				configureLabTestList = configurationDAOInf.retrieveExistingLabTestsList(userForm.getPracticeID());

			} else {

				configureLabTestList = configurationDAOInf.searchLabTestList(configurationForm.getSearchLabTestsName(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Lab Test Exception occurred", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addReferringDoctor() throws Exception {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		configurationDAOInf = new ConfigurationDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		response.setCharacterEncoding("UTF-8");

		LoginForm userForm = (LoginForm) session.get("USER");

		try {

			message = serviceInf.addReferringDoctor(configurationForm, userForm.getPracticeID());

			if (message.equals("success")) {

				values = configurationDAOInf.RetrieveAddedReferringDoctor(configurationForm);

			} else if (message.equals("input")) {
				object.put("MSG",
						"Failed to add new referring doctor. Referring doctor with same doctor name already exists.");
				array.add(object);

				values.put("Release", array);
			} else {
				object.put("MSG", "Failed to add new referring doctor. Please check server logs for more details.");
				array.add(object);

				values.put("Release", array);
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();
			// System.out.println("Exception occured while deleting IPD complaint due to:::"
			// + exception.getMessage());

			object.put("MSG",
					"Exception occured while adding new referring doctor.  Please check server logs for more details.");
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
	public void retrieveReferringDoctorList() throws Exception {

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		configurationDAOInf = new ConfigurationDAOImpl();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * Getting userID from Session
			 */
			LoginForm userForm = (LoginForm) session.get("USER");

			List<ConfigurationForm> list = configurationDAOInf.retrieveReferringDoctorList(userForm.getPracticeID());

			for (ConfigurationForm cForm : list) {

				object = new JSONObject();

				object.put("doctName", cForm.getReferringDoctName());
				object.put("MSG", "success");

				array.add(object);

				values.put("Release", array);

			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();
			// System.out.println("Exception occured while deleting IPD complaint due to:::"
			// + exception.getMessage());

			object.put("MSG",
					"Exception occured while retrieving referring doctor list.  Please check server logs for more details.");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	public String addTemplate() {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addTemplate(configurationForm);

		if (message.equals("success")) {

			addActionMessage("Template added successfully.");

			// retrieving template list from template table
			TemplateList = configurationDAOInf.retrieveAllTemplates();

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add  Template", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Template with same name already exists. Please add new SMS Template.");

			// retrieving template list from template table
			TemplateList = configurationDAOInf.retrieveAllTemplates();

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add Template Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add Template. Please check server logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Template Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	public String editTemplate() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editTemplate(configurationForm);

		if (message.equals("success")) {

			addActionMessage("Template updated successfully.");

			String searchTemplateName = configurationForm.getSearchTemplate();

			if (searchTemplateName == null || searchTemplateName == "") {

				// retrieving Template list from SMS Template table
				TemplateList = configurationDAOInf.retrieveAllTemplates();

			} else {

				TemplateList = configurationDAOInf.searchTemplate(searchTemplateName);

			}

			request.setAttribute("componentMsg", "available");
			request.setAttribute("componentEdit", "edit");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Template", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Template with same name already exists. Please add new Template.");

			String searchTemplateName = configurationForm.getSearchTemplate();

			if (searchTemplateName == null || searchTemplateName == "") {

				// retrieving Template list from Template table
				TemplateList = configurationDAOInf.retrieveAllTemplates();

			} else {

				TemplateList = configurationDAOInf.searchTemplate(searchTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Template Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update category. Please check server logs for more details.");

			String searchTemplateName = configurationForm.getSearchTemplate();

			if (searchTemplateName == null || searchTemplateName == "") {

				// retrieving Template list from Template table
				TemplateList = configurationDAOInf.retrieveAllTemplates();

			} else {

				TemplateList = configurationDAOInf.searchTemplate(searchTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Template Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	public String renderEditTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		editTemplateList = configurationDAOInf.retrieveTemplateByID(configurationForm.getTemplateID(),
				configurationForm.getSearchTemplate());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchCategoryName = configurationForm.getSearchTemplate();

		if (searchCategoryName == null || searchCategoryName == "") {

			// retrieving category list from Category table
			TemplateList = configurationDAOInf.retrieveAllTemplates();

		} else {

			TemplateList = configurationDAOInf.searchTemplate(searchCategoryName);

		}

		return SUCCESS;

	}

	public String searchTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		TemplateList = configurationDAOInf.searchTemplate(configurationForm.getSearchTemplate());

		if (TemplateList.size() > 0) {
			System.out.println("-------------------------3");
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {
			System.out.println("-------------------------4");
			addActionError("No Template found for : " + configurationForm.getSearchTemplate());

			return ERROR;

		}
	}

	public String viewAllTemplates() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		TemplateList = configurationDAOInf.retrieveAllTemplates();

		if (TemplateList.size() > 0) {

			request.setAttribute("componentMsg", "available");
			request.setAttribute("componentEdit", "add");

			return SUCCESS;

		} else {

			addActionError("No Template found. Please add new Template.");

			return ERROR;

		}
	}

	public String deleteTemplate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = configurationDAOInf.deleteTemplate(configurationForm.getTemplateID());

		if (message.equals("success")) {

			addActionMessage("Template deleted successfully.");

			String searchTemplateName = configurationForm.getSearchTemplate();

			if (searchTemplateName == null || searchTemplateName == "") {

				// retrieving category list from Category table
				TemplateList = configurationDAOInf.retrieveAllTemplates();

			} else {

				TemplateList = configurationDAOInf.searchTemplate(searchTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete Template. Please check server logs for more details.");

			String searchTemplateName = configurationForm.getSearchTemplate();

			if (searchTemplateName == null || searchTemplateName == "") {

				// retrieving category list from Category table
				TemplateList = configurationDAOInf.retrieveAllTemplates();

			} else {

				TemplateList = configurationDAOInf.searchTemplate(searchTemplateName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	public void retrieveTemplate() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			System.out.println();

			values = configurationDAOInf.RetrieveTemplate(configurationForm.getTemplateID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Template List based on PracticeID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String renderGroupLabTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());

		return SUCCESS;

	}

	public String configureGroupTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = configurationDAOInf.insertGroupTests(configurationForm.getGroupName(),
				configurationForm.getGroupRate(), configurationForm.getLabTest(), configurationForm.getGroupRemark() ,userForm.getPracticeID());

		labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());

		return SUCCESS;

	}

	public String viewAllGroupTests() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		retriveAllGroupTests = configurationDAOInf.retrieveAllGroupTests(userForm.getPracticeID());

		if (retriveAllGroupTests.size() > 0) {
			labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {
			labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());
			addActionError("No Group Test found. Please add new Group Test.");

			return ERROR;

		}
	}

	public String viewAllLabTests() throws Exception {
		configurationDAOInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		retriveAllLabTests = configurationDAOInf.retriveAllLabTests(userForm.getPracticeID());

		if (retriveAllLabTests.size() > 0) {
			labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {
			labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			addActionError("No Group Test found. Please add new Group Test.");

			return ERROR;

		}
	}

	public String RenderLabTestsRate() throws Exception {

		LoginForm userForm = (LoginForm) session.get("USER");

		configurationDAOInf = new ConfigurationDAOImpl();
		labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
		return SUCCESS;
	}

	public String ConfigureTestRate() throws Exception {
		try {
			LoginForm userForm = (LoginForm) session.get("USER");

			configurationDAOInf = new ConfigurationDAOImpl();

			message = configurationDAOInf.insertLabTestRate(configurationForm.getLabTestID(),
					configurationForm.getTestRate(), userForm.getPracticeID());
			if (message.equals("success")) {
				addActionMessage("Lab Test Insert successfully.");
				labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;

	}

	public String EditConfigureLabTestCharges() throws Exception {

		try {
			LoginForm userForm = (LoginForm) session.get("USER");

			configurationDAOInf = new ConfigurationDAOImpl();

			message = configurationDAOInf.UpdateLabTestRate(configurationForm.getLabTestID(),
					configurationForm.getTestRate(), userForm.getPracticeID());
			if (message.equals("success")) {
				addActionMessage("Lab Test udpated successfully.");
				labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;

	}

	public String SearchGroupTest() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		retriveAllGroupTests = configurationDAOInf.searchGroupTest(configurationForm.getSearchTest(),
				userForm.getPracticeID());

		if (retriveAllGroupTests.size() > 0) {
			labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Group Test found for : " + configurationForm.getSearchTest());

			return ERROR;

		}
	}

	public String SearchLabTestRate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		retriveAllLabTests = configurationDAOInf.SearchLabTestRate(configurationForm.getSearchTest(),
				userForm.getPracticeID());

		if (retriveAllLabTests.size() > 0) {
			labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Lab Test found for : " + configurationForm.getSearchTest());

			return ERROR;

		}
	}

	public String RenderLabTestQuotation() throws Exception {

		return SUCCESS;
	}

	public String renderEditGroupTest() throws Exception {
		System.out.println("renderEditGroupTest");
		configurationDAOInf = new ConfigurationDAOImpl();
		LoginForm userForm = (LoginForm) session.get("USER");

		String searchTest = configurationForm.getSearchTest();

		groupTestList = configurationDAOInf.retrieveGroupListByID(configurationForm.getGroupID(), searchTest);

		if (searchTest == null || searchTest == "") {

			retriveAllGroupTests = configurationDAOInf.retrieveAllGroupTests(userForm.getPracticeID());

		} else {

			retriveAllGroupTests = configurationDAOInf.retrieveAllGroupTests(userForm.getPracticeID());

		}
		labTests = configurationDAOInf.retrieveTestsByPracticeID(userForm.getPracticeID());
		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	public String renderEditLabTestRate() throws Exception {

		configurationDAOInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		String searchTest = configurationForm.getSearchTest();

		labTestList = configurationDAOInf.retrieveLabTestRateListByID(configurationForm.getLabTestID(), searchTest);

		retriveAllLabTests = configurationDAOInf.retriveAllLabTests(userForm.getPracticeID());

		if (retriveAllLabTests.size() > 0) {
			labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			request.setAttribute("componentMsg", "available");
			request.setAttribute("componentEdit", "edit");
			return SUCCESS;

		} else {
			labTests = configurationDAOInf.retrieveAllTests(userForm.getPracticeID());
			addActionError("No Lab Test found. Please add new Lab Test.");

			return ERROR;

		}

	}

	public List<ConfigurationForm> getEditSMSTemplateList() {
		return editSMSTemplateList;
	}

	public void setEditSMSTemplateList(List<ConfigurationForm> editSMSTemplateList) {
		this.editSMSTemplateList = editSMSTemplateList;
	}

	public List<ConfigurationForm> getSMSTemplateList() {
		return SMSTemplateList;
	}

	public void setSMSTemplateList(List<ConfigurationForm> sMSTemplateList) {
		SMSTemplateList = sMSTemplateList;
	}
	
	/**
	 * @return the templateListByType
	 */
	public ArrayList<String> getTemplateListByType() {
		return templateListByType;
	}

	/**
	 * @param templateListByType the templateListByType to set
	 */
	public void setTemplateListByType(ArrayList<String> templateListByType) {
		this.templateListByType = templateListByType;
	}

	/**
	 * @return the testEditList
	 */
	public List<ConfigurationForm> getTestEditList() {
		return TestEditList;
	}

	/**
	 * @param testEditList the testEditList to set
	 */
	public void setTestEditList(List<ConfigurationForm> testEditList) {
		TestEditList = testEditList;
	}

	/**
	 * @return the configureTestList
	 */
	public List<ConfigurationForm> getConfigureTestList() {
		return configureTestList;
	}

	/**
	 * @param configureTestList the configureTestList to set
	 */
	public void setConfigureTestList(List<ConfigurationForm> configureTestList) {
		this.configureTestList = configureTestList;
	}

	/**
	 * @return the caregoryList
	 */
	public HashMap<Integer, String> getCaregoryList() {
		return caregoryList;
	}

	/**
	 * @param caregoryList the caregoryList to set
	 */
	public void setCaregoryList(HashMap<Integer, String> caregoryList) {
		this.caregoryList = caregoryList;
	}

	/**
	 * @return the configureInstructionsList
	 */
	public List<ConfigurationForm> getConfigureInstructionsList() {
		return configureInstructionsList;
	}

	/**
	 * @param configureInstructionsList the configureInstructionsList to set
	 */
	public void setConfigureInstructionsList(List<ConfigurationForm> configureInstructionsList) {
		this.configureInstructionsList = configureInstructionsList;
	}

	/**
	 * @return the instructionsList
	 */
	public List<ConfigurationForm> getInstructionsList() {
		return instructionsList;
	}

	/**
	 * @param instructionsList the instructionsList to set
	 */
	public void setInstructionsList(List<ConfigurationForm> instructionsList) {
		this.instructionsList = instructionsList;
	}

	/**
	 * @return the configureVisitTypeList
	 */
	public List<ConfigurationForm> getConfigureVisitTypeList() {
		return configureVisitTypeList;
	}

	/**
	 * @param configureVisitTypeList the configureVisitTypeList to set
	 */
	public void setConfigureVisitTypeList(List<ConfigurationForm> configureVisitTypeList) {
		this.configureVisitTypeList = configureVisitTypeList;
	}

	/**
	 * @return the visitTypeEditList
	 */
	public List<ConfigurationForm> getVisitTypeEditList() {
		return visitTypeEditList;
	}

	/**
	 * @param visitTypeEditList the visitTypeEditList to set
	 */
	public void setVisitTypeEditList(List<ConfigurationForm> visitTypeEditList) {
		this.visitTypeEditList = visitTypeEditList;
	}

	/**
	 * @return the clinicTypeEditList
	 */
	public List<ConfigurationForm> getClinicTypeEditList() {
		return clinicTypeEditList;
	}

	/**
	 * @param clinicTypeEditList the clinicTypeEditList to set
	 */
	public void setClinicTypeEditList(List<ConfigurationForm> clinicTypeEditList) {
		this.clinicTypeEditList = clinicTypeEditList;
	}

	/**
	 * @return the diagnosisEditList
	 */
	public List<ConfigurationForm> getDiagnosisEditList() {
		return diagnosisEditList;
	}

	/**
	 * @param diagnosisEditList the diagnosisEditList to set
	 */
	public void setDiagnosisEditList(List<ConfigurationForm> diagnosisEditList) {
		this.diagnosisEditList = diagnosisEditList;
	}

	/**
	 * @return the prescriptionEditList
	 */
	public List<ConfigurationForm> getPrescriptionEditList() {
		return prescriptionEditList;
	}

	/**
	 * @param prescriptionEditList the prescriptionEditList to set
	 */
	public void setPrescriptionEditList(List<ConfigurationForm> prescriptionEditList) {
		this.prescriptionEditList = prescriptionEditList;
	}

	/**
	 * @return the billingEditList
	 */
	public List<ConfigurationForm> getBillingEditList() {
		return billingEditList;
	}

	/**
	 * @param billingEditList the billingEditList to set
	 */
	public void setBillingEditList(List<ConfigurationForm> billingEditList) {
		this.billingEditList = billingEditList;
	}

	/**
	 * @return the frequencyEditList
	 */
	public List<ConfigurationForm> getFrequencyEditList() {
		return frequencyEditList;
	}

	/**
	 * @param frequencyEditList the frequencyEditList to set
	 */
	public void setFrequencyEditList(List<ConfigurationForm> frequencyEditList) {
		this.frequencyEditList = frequencyEditList;
	}

	/**
	 * @return the referringDoctEditList
	 */
	public List<ConfigurationForm> getReferringDoctEditList() {
		return referringDoctEditList;
	}

	/**
	 * @param referringDoctEditList the referringDoctEditList to set
	 */
	public void setReferringDoctEditList(List<ConfigurationForm> referringDoctEditList) {
		this.referringDoctEditList = referringDoctEditList;
	}

	/**
	 * @return the billingClinicList
	 */
	public HashMap<Integer, String> getBillingClinicList() {
		return billingClinicList;
	}

	/**
	 * @param billingClinicList the billingClinicList to set
	 */
	public void setBillingClinicList(HashMap<Integer, String> billingClinicList) {
		this.billingClinicList = billingClinicList;
	}

	/**
	 * @return the configureBillingList
	 */
	public List<ConfigurationForm> getConfigureBillingList() {
		return configureBillingList;
	}

	/**
	 * @param configureBillingList the configureBillingList to set
	 */
	public void setConfigureBillingList(List<ConfigurationForm> configureBillingList) {
		this.configureBillingList = configureBillingList;
	}

	/**
	 * @return the configurePrescriptionList
	 */
	public List<ConfigurationForm> getConfigurePrescriptionList() {
		return configurePrescriptionList;
	}

	/**
	 * @param configurePrescriptionList the configurePrescriptionList to set
	 */
	public void setConfigurePrescriptionList(List<ConfigurationForm> configurePrescriptionList) {
		this.configurePrescriptionList = configurePrescriptionList;
	}

	/**
	 * @return the configureFrequencyList
	 */
	public List<ConfigurationForm> getConfigureFrequencyList() {
		return configureFrequencyList;
	}

	/**
	 * @param configureFrequencyList the configureFrequencyList to set
	 */
	public void setConfigureFrequencyList(List<ConfigurationForm> configureFrequencyList) {
		this.configureFrequencyList = configureFrequencyList;
	}

	/**
	 * @return the configureReferringDoctorList
	 */
	public List<ConfigurationForm> getConfigureReferringDoctorList() {
		return configureReferringDoctorList;
	}

	/**
	 * @param configureReferringDoctorList the configureReferringDoctorList to set
	 */
	public void setConfigureReferringDoctorList(List<ConfigurationForm> configureReferringDoctorList) {
		this.configureReferringDoctorList = configureReferringDoctorList;
	}

	/**
	 * @return the configureDiagnosisList
	 */
	public List<ConfigurationForm> getConfigureDiagnosisList() {
		return configureDiagnosisList;
	}

	/**
	 * @param configureDiagnosisList the configureDiagnosisList to set
	 */
	public void setConfigureDiagnosisList(List<ConfigurationForm> configureDiagnosisList) {
		this.configureDiagnosisList = configureDiagnosisList;
	}

	/**
	 * @return the configureClinicTypeList
	 */
	public List<ConfigurationForm> getConfigureClinicTypeList() {
		return configureClinicTypeList;
	}

	/**
	 * @param configureClinicTypeList the configureClinicTypeList to set
	 */
	public void setConfigureClinicTypeList(List<ConfigurationForm> configureClinicTypeList) {
		this.configureClinicTypeList = configureClinicTypeList;
	}

	/**
	 * @return the configurationForm
	 */
	public ConfigurationForm getConfigurationForm() {
		return configurationForm;
	}

	/**
	 * @param configurationForm the configurationForm to set
	 */
	public void setConfigurationForm(ConfigurationForm configurationForm) {
		this.configurationForm = configurationForm;
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

	public ConfigurationForm getModel() {
		// TODO Auto-generated method stub
		return configurationForm;
	}

	public List<ConfigurationForm> getConfigureLabTestList() {
		return configureLabTestList;
	}

	public void setConfigureLabTestList(List<ConfigurationForm> configureLabTestList) {
		this.configureLabTestList = configureLabTestList;
	}

	public List<ConfigurationForm> getLabTestList() {
		return labTestList;
	}

	public void setLabTestList(List<ConfigurationForm> labTestList) {
		this.labTestList = labTestList;
	}

	public List<ConfigurationForm> getDefaultValueList() {
		return DefaultValueList;
	}

	public void setDefaultValueList(List<ConfigurationForm> defaultValueList) {
		DefaultValueList = defaultValueList;
	}

	public List<ConfigurationForm> getTemplateList() {
		return TemplateList;
	}

	public void setTemplateList(List<ConfigurationForm> templateList) {
		TemplateList = templateList;
	}

	public List<ConfigurationForm> getEditTemplateList() {
		return editTemplateList;
	}

	public void setEditTemplateList(List<ConfigurationForm> editTemplateList) {
		this.editTemplateList = editTemplateList;
	}
	
	/**
	 * @return the selectedTemplateList
	 */
	public HashMap<String, String> getSelectedTemplateList() {
		return selectedTemplateList;
	}

	/**
	 * @param selectedTemplateList the selectedTemplateList to set
	 */
	public void setSelectedTemplateList(HashMap<String, String> selectedTemplateList) {
		this.selectedTemplateList = selectedTemplateList;
	}

	/**
	 * @return the labTests
	 */
	public HashMap<Integer, String> getLabTests() {
		return labTests;
	}

	/**
	 * @param labTests the labTests to set
	 */
	public void setLabTests(HashMap<Integer, String> labTests) {
		this.labTests = labTests;
	}

	/**
	 * @return the retriveAllGroupTests
	 */
	public List<ConfigurationForm> getRetriveAllGroupTests() {
		return retriveAllGroupTests;
	}

	/**
	 * @param retriveAllGroupTests the retriveAllGroupTests to set
	 */
	public void setRetriveAllGroupTests(List<ConfigurationForm> retriveAllGroupTests) {
		this.retriveAllGroupTests = retriveAllGroupTests;
	}

	/**
	 * @return the groupTestList
	 */
	public List<ConfigurationForm> getGroupTestList() {
		return groupTestList;
	}

	/**
	 * @param groupTestList the groupTestList to set
	 */
	public void setGroupTestList(List<ConfigurationForm> groupTestList) {
		this.groupTestList = groupTestList;
	}

	/**
	 * @return the retriveAllLabTests
	 */
	public List<ConfigurationForm> getRetriveAllLabTests() {
		return retriveAllLabTests;
	}

	/**
	 * @param retriveAllLabTests the retriveAllLabTests to set
	 */
	public void setRetriveAllLabTests(List<ConfigurationForm> retriveAllLabTests) {
		this.retriveAllLabTests = retriveAllLabTests;
	}

}
