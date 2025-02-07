package com.edhanvantari.form;

public class ConfigurationForm {

	private String testTypeTemplates;
	private String groupRemark;
	private String testTypeTemplatesRemoved;
	private int clinicTypeID;
	private String clinicType;
	private int prescID;
	private int billingID;
	private int frequencyID;
	private int referringDoctID;
	private int diagnosisID;
	private String diagnosis;
	private String diagnosisICD10Code;
	private String referringDoctName;
	private String referringDoctSpecialisation;
	private String referringDoctClinicName;
	private String referringDoctClinicAddres;
	private String referringDoctPhone;
	private String referringDoctEmail;
	private String frequency;
	private int clinicID;
	private double charge;
	private String chargeType;
	private String clinicName;
	private String prescTradeName;
	private String prescDrugName;
	private String prescDose;
	private String prescForm;
	private String prescICD10Code;
	private String searchClinicType;
	private String searchDiagnosis;
	private String searchFrequency;
	private String searchPresctipion;
	private String searchRefDoctor;
	private String searchBilling;
	private String searchVisitTypeName;
	private String visitTypeName;
	private String visitTypeDuration;
	private int visitTypeID;
	private double frequencyCount;
	private int sortOrder;
	private String searchInstructionName;
	private int instructionID;
	private String instruction;
	private int categoryID;
	private String categoryName;
	private String searchTest;
	private int testID;
	private String test;
	private double rate;
	private String panel;
	private String normalValues;
	private int SMSTemplateID;
	private String SMSTemplateTitle;
	private String SMSTemplateText;
	private String searchSMSTemplate;
	private String searchLabTestsName;
	private int labTestID;
	private String subgroup;
	private String group;
	private double grouRate;
	private String defaultValue;
	private int defaultValueID;
	private String[] editdefaultValue;
	private String[] defaultValueArr;
	private String[] editdefaultValueID;
	private int srNo;
	private String womenNormalRange;
	private String childNormalRange;
	private String remarks;
	private int isExcludeNormalValues;
	private int showNormalRangeDesc;
	private String normalRangeDesc;
	private int TemplateID;
	private String TempReportType;
	private String TempName;
	private String searchTemplate;
	private String template;
	private String groupName;
	private double groupRate;
	private String labTest;
	private int groupID;
	private String labTestIDs;
	private double testRate;
	private int practiceID;
	private int isOutsourced;
	private String testType;
	
	/**
	 * @return the groupRemark
	 */
	public String getGroupRemark() {
		return groupRemark;
	}

	/**
	 * @param groupRemark the groupRemark to set
	 */
	public void setGroupRemark(String groupRemark) {
		this.groupRemark = groupRemark;
	}
	
	/**
	 * @return the testTypeTemplatesRemoved
	 */
	public String getTestTypeTemplatesRemoved() {
		return testTypeTemplatesRemoved;
	}

	/**
	 * @param testTypeTemplatesRemoved the testTypeTemplatesRemoved to set
	 */
	public void setTestTypeTemplatesRemoved(String testTypeTemplatesRemoved) {
		this.testTypeTemplatesRemoved = testTypeTemplatesRemoved;
	}

	/**
	 * @return the testType
	 */
	public String getTestType() {
		return testType;
	}

	/**
	 * @param testType the testType to set
	 */
	public void setTestType(String testType) {
		this.testType = testType;
	}

	/**
	 * @return the isOutsourced
	 */
	public int getIsOutsourced() {
		return isOutsourced;
	}

	/**
	 * @param isOutsourced the isOutsourced to set
	 */
	public void setIsOutsourced(int isOutsourced) {
		this.isOutsourced = isOutsourced;
	}

	/**
	 * @return the practiceID
	 */
	public int getPracticeID() {
		return practiceID;
	}

	/**
	 * @param practiceID the practiceID to set
	 */
	public void setPracticeID(int practiceID) {
		this.practiceID = practiceID;
	}

	/**
	 * @return the testRate
	 */
	public double getTestRate() {
		return testRate;
	}

	/**
	 * @param testRate the testRate to set
	 */
	public void setTestRate(double testRate) {
		this.testRate = testRate;
	}

	/**
	 * @return the labTestIDs
	 */
	public String getLabTestIDs() {
		return labTestIDs;
	}

	/**
	 * @param labTestIDs the labTestIDs to set
	 */
	public void setLabTestIDs(String labTestIDs) {
		this.labTestIDs = labTestIDs;
	}

	/**
	 * @return the groupID
	 */
	public int getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	/**
	 * @return the groupRate
	 */
	public double getGroupRate() {
		return groupRate;
	}

	/**
	 * @param groupRate the groupRate to set
	 */
	public void setGroupRate(double groupRate) {
		this.groupRate = groupRate;
	}

	/**
	 * @return the labTest
	 */
	public String getLabTest() {
		return labTest;
	}

	/**
	 * @param labTest the labTest to set
	 */
	public void setLabTest(String labTest) {
		this.labTest = labTest;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int getTemplateID() {
		return TemplateID;
	}

	public void setTemplateID(int templateID) {
		TemplateID = templateID;
	}

	public String getTempReportType() {
		return TempReportType;
	}

	public void setTempReportType(String tempReportType) {
		TempReportType = tempReportType;
	}

	public String getTempName() {
		return TempName;
	}

	public void setTempName(String tempName) {
		TempName = tempName;
	}

	public String getSearchTemplate() {
		return searchTemplate;
	}

	public void setSearchTemplate(String searchTemplate) {
		this.searchTemplate = searchTemplate;
	}

	public String getWomenNormalRange() {
		return womenNormalRange;
	}

	public void setWomenNormalRange(String womenNormalRange) {
		this.womenNormalRange = womenNormalRange;
	}

	public String getChildNormalRange() {
		return childNormalRange;
	}

	public void setChildNormalRange(String childNormalRange) {
		this.childNormalRange = childNormalRange;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getIsExcludeNormalValues() {
		return isExcludeNormalValues;
	}

	public void setIsExcludeNormalValues(int isExcludeNormalValues) {
		this.isExcludeNormalValues = isExcludeNormalValues;
	}

	public int getShowNormalRangeDesc() {
		return showNormalRangeDesc;
	}

	public void setShowNormalRangeDesc(int showNormalRangeDesc) {
		this.showNormalRangeDesc = showNormalRangeDesc;
	}

	public String getNormalRangeDesc() {
		return normalRangeDesc;
	}

	public void setNormalRangeDesc(String normalRangeDesc) {
		this.normalRangeDesc = normalRangeDesc;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getDefaultValueID() {
		return defaultValueID;
	}

	public void setDefaultValueID(int defaultValueID) {
		this.defaultValueID = defaultValueID;
	}

	public String[] getEditdefaultValue() {
		return editdefaultValue;
	}

	public void setEditdefaultValue(String[] editdefaultValue) {
		this.editdefaultValue = editdefaultValue;
	}

	public String[] getDefaultValueArr() {
		return defaultValueArr;
	}

	public void setDefaultValueArr(String[] defaultValueArr) {
		this.defaultValueArr = defaultValueArr;
	}

	public String[] getEditdefaultValueID() {
		return editdefaultValueID;
	}

	public void setEditdefaultValueID(String[] editdefaultValueID) {
		this.editdefaultValueID = editdefaultValueID;
	}

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public double getGrouRate() {
		return grouRate;
	}

	public void setGrouRate(double grouRate) {
		this.grouRate = grouRate;
	}

	public int getLabTestID() {
		return labTestID;
	}

	public void setLabTestID(int labTestID) {
		this.labTestID = labTestID;
	}

	public String getSearchLabTestsName() {
		return searchLabTestsName;
	}

	public void setSearchLabTestsName(String searchLabTestsName) {
		this.searchLabTestsName = searchLabTestsName;
	}

	public String getSearchSMSTemplate() {
		return searchSMSTemplate;
	}

	public void setSearchSMSTemplate(String searchSMSTemplate) {
		this.searchSMSTemplate = searchSMSTemplate;
	}

	public int getSMSTemplateID() {
		return SMSTemplateID;
	}

	public void setSMSTemplateID(int sMSTemplateID) {
		SMSTemplateID = sMSTemplateID;
	}

	public String getSMSTemplateTitle() {
		return SMSTemplateTitle;
	}

	public void setSMSTemplateTitle(String sMSTemplateTitle) {
		SMSTemplateTitle = sMSTemplateTitle;
	}

	public String getSMSTemplateText() {
		return SMSTemplateText;
	}

	public void setSMSTemplateText(String sMSTemplateText) {
		SMSTemplateText = sMSTemplateText;
	}
	
	/**
	 * @return the testTypeTemplates
	 */
	public String getTestTypeTemplates() {
		return testTypeTemplates;
	}

	/**
	 * @param testTypeTemplates the testTypeTemplates to set
	 */
	public void setTestTypeTemplates(String testTypeTemplates) {
		this.testTypeTemplates = testTypeTemplates;
	}

	/**
	 * @return the testID
	 */
	public int getTestID() {
		return testID;
	}

	/**
	 * @param testID the testID to set
	 */
	public void setTestID(int testID) {
		this.testID = testID;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the panel
	 */
	public String getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(String panel) {
		this.panel = panel;
	}

	/**
	 * @return the normalValues
	 */
	public String getNormalValues() {
		return normalValues;
	}

	/**
	 * @param normalValues the normalValues to set
	 */
	public void setNormalValues(String normalValues) {
		this.normalValues = normalValues;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the searchTest
	 */
	public String getSearchTest() {
		return searchTest;
	}

	/**
	 * @param searchTest the searchTest to set
	 */
	public void setSearchTest(String searchTest) {
		this.searchTest = searchTest;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the searchInstructionName
	 */
	public String getSearchInstructionName() {
		return searchInstructionName;
	}

	/**
	 * @param searchInstructionName the searchInstructionName to set
	 */
	public void setSearchInstructionName(String searchInstructionName) {
		this.searchInstructionName = searchInstructionName;
	}

	/**
	 * @return the instructionID
	 */
	public int getInstructionID() {
		return instructionID;
	}

	/**
	 * @param instructionID the instructionID to set
	 */
	public void setInstructionID(int instructionID) {
		this.instructionID = instructionID;
	}

	/**
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/**
	 * @return the frequencyCount
	 */
	public double getFrequencyCount() {
		return frequencyCount;
	}

	/**
	 * @param frequencyCount the frequencyCount to set
	 */
	public void setFrequencyCount(double frequencyCount) {
		this.frequencyCount = frequencyCount;
	}

	/**
	 * @return the visitTypeID
	 */
	public int getVisitTypeID() {
		return visitTypeID;
	}

	/**
	 * @param visitTypeID the visitTypeID to set
	 */
	public void setVisitTypeID(int visitTypeID) {
		this.visitTypeID = visitTypeID;
	}

	/**
	 * @return the searchVisitTypeName
	 */
	public String getSearchVisitTypeName() {
		return searchVisitTypeName;
	}

	/**
	 * @param searchVisitTypeName the searchVisitTypeName to set
	 */
	public void setSearchVisitTypeName(String searchVisitTypeName) {
		this.searchVisitTypeName = searchVisitTypeName;
	}

	/**
	 * @return the visitTypeName
	 */
	public String getVisitTypeName() {
		return visitTypeName;
	}

	/**
	 * @param visitTypeName the visitTypeName to set
	 */
	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}

	/**
	 * @return the visitTypeDuration
	 */
	public String getVisitTypeDuration() {
		return visitTypeDuration;
	}

	/**
	 * @param visitTypeDuration the visitTypeDuration to set
	 */
	public void setVisitTypeDuration(String visitTypeDuration) {
		this.visitTypeDuration = visitTypeDuration;
	}

	/**
	 * @return the searchBilling
	 */
	public String getSearchBilling() {
		return searchBilling;
	}

	/**
	 * @param searchBilling the searchBilling to set
	 */
	public void setSearchBilling(String searchBilling) {
		this.searchBilling = searchBilling;
	}

	/**
	 * @return the searchPresctipion
	 */
	public String getSearchPresctipion() {
		return searchPresctipion;
	}

	/**
	 * @param searchPresctipion the searchPresctipion to set
	 */
	public void setSearchPresctipion(String searchPresctipion) {
		this.searchPresctipion = searchPresctipion;
	}

	/**
	 * @return the searchRefDoctor
	 */
	public String getSearchRefDoctor() {
		return searchRefDoctor;
	}

	/**
	 * @param searchRefDoctor the searchRefDoctor to set
	 */
	public void setSearchRefDoctor(String searchRefDoctor) {
		this.searchRefDoctor = searchRefDoctor;
	}

	/**
	 * @return the searchFrequency
	 */
	public String getSearchFrequency() {
		return searchFrequency;
	}

	/**
	 * @param searchFrequency the searchFrequency to set
	 */
	public void setSearchFrequency(String searchFrequency) {
		this.searchFrequency = searchFrequency;
	}

	/**
	 * @return the searchDiagnosis
	 */
	public String getSearchDiagnosis() {
		return searchDiagnosis;
	}

	/**
	 * @param searchDiagnosis the searchDiagnosis to set
	 */
	public void setSearchDiagnosis(String searchDiagnosis) {
		this.searchDiagnosis = searchDiagnosis;
	}

	/**
	 * @return the searchClinicType
	 */
	public String getSearchClinicType() {
		return searchClinicType;
	}

	/**
	 * @param searchClinicType the searchClinicType to set
	 */
	public void setSearchClinicType(String searchClinicType) {
		this.searchClinicType = searchClinicType;
	}

	/**
	 * @return the prescTradeName
	 */
	public String getPrescTradeName() {
		return prescTradeName;
	}

	/**
	 * @param prescTradeName the prescTradeName to set
	 */
	public void setPrescTradeName(String prescTradeName) {
		this.prescTradeName = prescTradeName;
	}

	/**
	 * @return the prescDrugName
	 */
	public String getPrescDrugName() {
		return prescDrugName;
	}

	/**
	 * @param prescDrugName the prescDrugName to set
	 */
	public void setPrescDrugName(String prescDrugName) {
		this.prescDrugName = prescDrugName;
	}

	/**
	 * @return the prescDose
	 */
	public String getPrescDose() {
		return prescDose;
	}

	/**
	 * @param prescDose the prescDose to set
	 */
	public void setPrescDose(String prescDose) {
		this.prescDose = prescDose;
	}

	/**
	 * @return the prescForm
	 */
	public String getPrescForm() {
		return prescForm;
	}

	/**
	 * @param prescForm the prescForm to set
	 */
	public void setPrescForm(String prescForm) {
		this.prescForm = prescForm;
	}

	/**
	 * @return the prescICD10Code
	 */
	public String getPrescICD10Code() {
		return prescICD10Code;
	}

	/**
	 * @param prescICD10Code the prescICD10Code to set
	 */
	public void setPrescICD10Code(String prescICD10Code) {
		this.prescICD10Code = prescICD10Code;
	}

	/**
	 * @return the clinicName
	 */
	public String getClinicName() {
		return clinicName;
	}

	/**
	 * @param clinicName the clinicName to set
	 */
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	/**
	 * @return the charge
	 */
	public double getCharge() {
		return charge;
	}

	/**
	 * @param charge the charge to set
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the clinicID
	 */
	public int getClinicID() {
		return clinicID;
	}

	/**
	 * @param clinicID the clinicID to set
	 */
	public void setClinicID(int clinicID) {
		this.clinicID = clinicID;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the referringDoctName
	 */
	public String getReferringDoctName() {
		return referringDoctName;
	}

	/**
	 * @param referringDoctName the referringDoctName to set
	 */
	public void setReferringDoctName(String referringDoctName) {
		this.referringDoctName = referringDoctName;
	}

	/**
	 * @return the referringDoctSpecialisation
	 */
	public String getReferringDoctSpecialisation() {
		return referringDoctSpecialisation;
	}

	/**
	 * @param referringDoctSpecialisation the referringDoctSpecialisation to set
	 */
	public void setReferringDoctSpecialisation(String referringDoctSpecialisation) {
		this.referringDoctSpecialisation = referringDoctSpecialisation;
	}

	/**
	 * @return the referringDoctClinicName
	 */
	public String getReferringDoctClinicName() {
		return referringDoctClinicName;
	}

	/**
	 * @param referringDoctClinicName the referringDoctClinicName to set
	 */
	public void setReferringDoctClinicName(String referringDoctClinicName) {
		this.referringDoctClinicName = referringDoctClinicName;
	}

	/**
	 * @return the referringDoctClinicAddres
	 */
	public String getReferringDoctClinicAddres() {
		return referringDoctClinicAddres;
	}

	/**
	 * @param referringDoctClinicAddres the referringDoctClinicAddres to set
	 */
	public void setReferringDoctClinicAddres(String referringDoctClinicAddres) {
		this.referringDoctClinicAddres = referringDoctClinicAddres;
	}

	/**
	 * @return the referringDoctPhone
	 */
	public String getReferringDoctPhone() {
		return referringDoctPhone;
	}

	/**
	 * @param referringDoctPhone the referringDoctPhone to set
	 */
	public void setReferringDoctPhone(String referringDoctPhone) {
		this.referringDoctPhone = referringDoctPhone;
	}

	/**
	 * @return the referringDoctEmail
	 */
	public String getReferringDoctEmail() {
		return referringDoctEmail;
	}

	/**
	 * @param referringDoctEmail the referringDoctEmail to set
	 */
	public void setReferringDoctEmail(String referringDoctEmail) {
		this.referringDoctEmail = referringDoctEmail;
	}

	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * @return the diagnosisICD10Code
	 */
	public String getDiagnosisICD10Code() {
		return diagnosisICD10Code;
	}

	/**
	 * @param diagnosisICD10Code the diagnosisICD10Code to set
	 */
	public void setDiagnosisICD10Code(String diagnosisICD10Code) {
		this.diagnosisICD10Code = diagnosisICD10Code;
	}

	/**
	 * @return the clinicTypeID
	 */
	public int getClinicTypeID() {
		return clinicTypeID;
	}

	/**
	 * @param clinicTypeID the clinicTypeID to set
	 */
	public void setClinicTypeID(int clinicTypeID) {
		this.clinicTypeID = clinicTypeID;
	}

	/**
	 * @return the clinicType
	 */
	public String getClinicType() {
		return clinicType;
	}

	/**
	 * @param clinicType the clinicType to set
	 */
	public void setClinicType(String clinicType) {
		this.clinicType = clinicType;
	}

	/**
	 * @return the prescID
	 */
	public int getPrescID() {
		return prescID;
	}

	/**
	 * @param prescID the prescID to set
	 */
	public void setPrescID(int prescID) {
		this.prescID = prescID;
	}

	/**
	 * @return the billingID
	 */
	public int getBillingID() {
		return billingID;
	}

	/**
	 * @param billingID the billingID to set
	 */
	public void setBillingID(int billingID) {
		this.billingID = billingID;
	}

	/**
	 * @return the frequencyID
	 */
	public int getFrequencyID() {
		return frequencyID;
	}

	/**
	 * @param frequencyID the frequencyID to set
	 */
	public void setFrequencyID(int frequencyID) {
		this.frequencyID = frequencyID;
	}

	/**
	 * @return the referringDoctID
	 */
	public int getReferringDoctID() {
		return referringDoctID;
	}

	/**
	 * @param referringDoctID the referringDoctID to set
	 */
	public void setReferringDoctID(int referringDoctID) {
		this.referringDoctID = referringDoctID;
	}

	/**
	 * @return the diagnosisID
	 */
	public int getDiagnosisID() {
		return diagnosisID;
	}

	/**
	 * @param diagnosisID the diagnosisID to set
	 */
	public void setDiagnosisID(int diagnosisID) {
		this.diagnosisID = diagnosisID;
	}

}
