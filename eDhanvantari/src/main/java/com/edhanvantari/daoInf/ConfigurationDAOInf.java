package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.edhanvantari.form.ConfigurationForm;

public interface ConfigurationDAOInf {

	/**
	 * 
	 * @return
	 */
	public List<ConfigurationForm> retrieveExistingDiagnosisList();

	/**
	 * 
	 * @param configurationForm
	 * @return
	 */
	public String insertDiagnosis(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ConfigurationForm> retrieveReferringDoctorList(int practiceID);

	/**
	 * 
	 * @param configurationForm
	 * @return
	 */
	public String insertReferringDoctor(ConfigurationForm configurationForm, int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ConfigurationForm> retrieveExistingFrequencyList(int practiceID);

	/**
	 * 
	 * @param configurationForm
	 * @param practiceID
	 * @return
	 */
	public String insertFrequency(ConfigurationForm configurationForm, int practiceID);

	/**
	 * 
	 * @param diagnosisID
	 * @return
	 */
	public List<ConfigurationForm> retrieveDiagnosisListByID(int diagnosisID, String searchDiagnosis);

	/**
	 * 
	 * @param diagnosisID
	 * @return
	 */
	public String deleteDiagnosis(int diagnosisID);

	/**
	 * 
	 * @param diagnosisID
	 * @return
	 */
	public String updateDiagnosis(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param frequencyID
	 * @return
	 */
	public List<ConfigurationForm> retrieveFrequencyListByID(int frequencyID, String searchFrequency);

	/**
	 * 
	 * @param frequencyID
	 * @return
	 */
	public String deleteFrequency(int frequencyID);

	/**
	 * 
	 * @param configurationForm
	 * @return
	 */
	public String updateFrequency(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param refDocID
	 * @return
	 */
	public List<ConfigurationForm> retrieveRefDocListByID(int refDocID, String searchRefDoc);

	/**
	 * 
	 * @param refDocID
	 * @return
	 */
	public String deleteRefDoc(int refDocID);

	/**
	 * 
	 * @param refDocID
	 * @return
	 */
	public String updateRefDoc(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param searchDiagnoses
	 * @return
	 */
	public List<ConfigurationForm> searchDiagnosesList(String searchDiagnoses);

	/**
	 * 
	 * @param searchFrequency
	 * @param practiceID
	 * @return
	 */
	public List<ConfigurationForm> searchFrequencyList(String searchFrequency, int practiceID);

	/**
	 * 
	 * @param searchRefDcotor
	 * @return
	 */
	public List<ConfigurationForm> searchreferringDoctorList(String searchRefDcotor);

	/**
	 * 
	 * @param searchVisitType
	 * @param practiceID
	 * @return
	 */
	public List<ConfigurationForm> searchVisitTypeList(String searchVisitType, int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public List<ConfigurationForm> retrieveExistingVisitTypeList(int practiceID);

	/**
	 * 
	 * @param configurationForm
	 * @param practiceID
	 * @return
	 */
	public String insertVisitType(ConfigurationForm configurationForm, int practiceID);

	/**
	 * 
	 * @param visitTypeID
	 * @param searchVisitTypeName
	 * @return
	 */
	public List<ConfigurationForm> retrieveVisitTypeListByID(int visitTypeID, String searchVisitTypeName);

	/**
	 * 
	 * @param configurationForm
	 * @param practiceID
	 * @return
	 */
	public String updateVisitType(ConfigurationForm configurationForm, int practiceID);

	/**
	 * 
	 * @param visitTypeID
	 * @return
	 */
	public String deletevisitType(int visitTypeID);

	/**
	 * 
	 * @param searchInstruction
	 * @return
	 */
	public List<ConfigurationForm> searchInstructionList(String searchInstruction);

	/**
	 * 
	 * @return
	 */
	public List<ConfigurationForm> retrieveExistingInstructionsList();

	/**
	 * 
	 * @param configurationForm
	 * @return
	 */
	public String insertInstruction(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param configurationForm
	 * @return
	 */
	public String updateInstruction(ConfigurationForm configurationForm);

	/**
	 * 
	 * @param instructionID
	 * @param searchInstruction
	 * @return
	 */
	public List<ConfigurationForm> retrieveInstructionListByID(int instructionID, String searchInstruction);

	/**
	 * 
	 * @param instructionID
	 * @return
	 */
	public String deleteInstruction(int instructionID);

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> retrieveCategoryList();

	public boolean verifyInstructionAlreadyExists(String instruction);

	public boolean verifyEditInstructionsAlreadyExists(String instruction, int instructionID);

	public boolean verifyFrequencyDetailsAlreadyExists(String frequency, int sortOrder, int practiceID);

	public boolean verifyEditFrequencyAlreadyExists(String frequency, int frequencyID, int sortOrder, int practiceID);

	public HashMap<String, String> retrieveSMSURLDetailsByPracticeID(int practiceID);

	public List<ConfigurationForm> searchTestList(String searchTest);

	public List<ConfigurationForm> retrieveExistingTestsList();

	public List<ConfigurationForm> retrieveTestDetailsListByID(int testID, String searchTest);

	public boolean verifyTestAlreadyExists(String test);

	public String insertTestDetails(ConfigurationForm configurationForm);

	public boolean verifyEditTestAlreadyExists(String test, int testID);

	public String updateTestsDetails(ConfigurationForm configurationForm);

	public boolean verifySMSTemplateNameAlreadyExists(String smsTemplateTitle);

	public String insertSMSTemplate(String smsTemplateTitle, String smsTemplateText);

	public List<ConfigurationForm> retrieveAllSMSTemplates();

	public List<ConfigurationForm> searchSMSTemplate(String searchSMSTemplateName);

	public boolean verifyEditSMSTemplateNameAlreadyExists(String smsTemplateTitle, int smsTemplateID);

	public String updateSMSTemplate(String smsTemplateTitle, int smsTemplateID, String smsTemplateText);

	public List<ConfigurationForm> retrieveSMSTemplateByID(int smsTemplateID, String searchSMSTemplate);

	public String deleteSMSTemplate(int smsTemplateID);

	public HashMap<String, String> retrieveSMSTemplateTitleList();

	public List<ConfigurationForm> searchLabTestList(String searchLabTestsName, int practiceID);

	public List<ConfigurationForm> retrieveExistingLabTestsList(int practiceID);

	public List<ConfigurationForm> retrieveLabTestListByID(int labTestID, String searchLabTestName);

	public List<ConfigurationForm> retrieveDefaultValueList(int labTestID);

	public String deleteLabTest(int labTestID);

	public boolean verifyLabTestAlreadyExists(String test, String subGroup, int practiceID);

	public String insertLabTest(ConfigurationForm configurationForm);

	public int retrieveLabTestID(String test);

	public String insertLabTestDefaultValues(String value, int labTestID);

	public String updateLabTest(ConfigurationForm configurationForm);

	public boolean verifyEditLabTestAlreadyExists(String test, String groupName, String subGroup, int practiceID,
			int labTestID);

	public String updateLabTestDefaultValues(String editValue, int editValueID);

	public boolean verifyReferringDocExist(String referringDoctName, int practiceID);

	public boolean verifyEditReferringDocExist(String referringDoctName, int referringDoctID);

	public List<ConfigurationForm> retrieveAllTemplates();

	public List<ConfigurationForm> searchTemplate(String searchTemplateName);

	public List<ConfigurationForm> retrieveTemplateByID(int templateID, String searchTemplate);

	public String deleteTemplate(int templateID);

	public boolean verifyEditTemplateNameAlreadyExists(String tempReportType, int templateID);

	public String insertTemplate(String tempReportType, String tempName, String template);

	public String updateTemplate(String tempReportType, int templateID, String tempName, String template);

	public JSONObject RetrieveTemplate(int templateID);

	public JSONObject RetrieveAddedReferringDoctor(ConfigurationForm configurationForm);

	public HashMap<Integer, String> retrieveAllTests(int practiceID);

	public String insertGroupTests(String groupName, double groupRate, String labTests, String groupRemark, int practiceID);

	public List<ConfigurationForm> retrieveAllGroupTests(int practiceID);

	public String insertLabTestRate(int labTestID, double testRate, int practiceID);

	public List<ConfigurationForm> searchGroupTest(String searchTest, int practiceID);

	public List<ConfigurationForm> SearchLabTestRate(String searchTest, int practiceID);

	public List<ConfigurationForm> retrieveGroupListByID(int groupID, String searchTest);

	public List<ConfigurationForm> retriveAllLabTests(int practiceID);

	public List<ConfigurationForm> retrieveLabTestRateListByID(int labTestID, String searchTest);

	public HashMap<Integer, String> retrieveTestsByPracticeID(int practiceID);

	public String UpdateLabTestRate(int labTestID, double testRate, int practiceID);
	
	public JSONObject RetrieveTestTypesAndTemplates();
	
	public String insertTemplatesTestTypeData(String data, int labID, String testType);
	
	public String removeTemplatesTestTypeData(String data, int labID);
	
	public HashMap<String, String> retrieveSelectedTemplates(int labTestID);

}
