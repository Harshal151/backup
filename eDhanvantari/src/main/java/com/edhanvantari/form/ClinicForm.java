package com.edhanvantari.form;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class ClinicForm {
	
	private String updatedComment;

	private String clinicName;
	private String clinicType;
	private int clinicID;
	private String acticityStatus;
	private String clinicTimeFromHH;
	private String clinicTimeFromMM;
	private String clinicTimeFromAMPM;
	private String cliniTimeToHH;
	private String clinicTimeToMM;
	private String clinicTimeToAMPM;
	private String breakTimeFromHH;
	private String breakTimeFromMM;
	private String breakTimeFromAMPM;
	private String breakTimeToHH;
	private String breakTimeToMM;
	private String breakTimeToAMPM;
	private int apptDuration;
	private int apptID;
	private String tagline;
	private String website;
	private String pathToLogo;
	private String consentDocuments;
	private String pageSize;
	private String lettrHeadImage;
	private String sessionTimeout;
	private String invalidAttempts;
	private String emailNotificationsTo;
	private String sendEmailsFrom;
	private String emailsFromPassword;
	private String currencyForBilling;
	private int clnicConfigID;
	private String pathToLogoFileFileName;
	private File pathToLogoFile;
	private String realPath;
	private String pathToLogoFileContentType;
	private String searchClinic;
	private String practiceName;
	private int practiceID;
	private String[] workflow;
	private String[] practiceClinicName;
	private String[] practiceClinicNameEdit;
	private String[] workflowDummy;
	private String[] practiceClinicSuffix;
	private String[] practiceClinicSuffixEdit;
	private String searchPracticeName;
	private String workflowStatus;
	private String clinicActivityStatus;
	private int practiceConfID;
	private String patientForm;
	private String practiceSuffix;
	private String[] clinicStart;
	private String[] clinicEnd;
	private String[] clinicBreakStart1;
	private String[] clinicBreakEnd1;
	private String[] clinicBreakStart2;
	private String[] clinicBreakEnd2;
	private String[] workdays;
	private String[] slot1;
	private String[] slot2;
	private String[] slot1Start;
	private String[] slot1End;
	private String[] slot2Start;
	private String[] slot2End;
	private String workdaysName;
	private String clinicStartHH;
	private String clinicStartMM;
	private String clinicStartAMPM;
	private String clinicEndHH;
	private String clinicEndMM;
	private String clinicEndAMPM;
	private String breakStart1HH;
	private String breakStart1MM;
	private String breakStart1AMPM;
	private String breakEnd1HH;
	private String breakEnd1MM;
	private String breakEnd1AMPM;
	private String breakStart2HH;
	private String breakStart2MM;
	private String breakStart2AMPM;
	private String breakEnd2HH;
	private String breakEnd2MM;
	private String breakEnd2AMPM;
	private String clinicStartName;
	private String clinicEndName;
	private String breakStart1;
	private String breakEnd1;
	private String breakStart2;
	private String breakEnd2;
	private String currencyForBilling1;
	private String[] workflowStepSeq;
	private String[] workflowRole;
	private String[] workflowApptStatus;
	private String[] workflowOPDFormName;
	private String visitTypeName;
	private int visitDuration;
	private String billingType;
	private String careType;
	private int newVisitFlag;
	private int visitTypeID;
	private String searchVisitTypeName;
	private int workflowStepSeqName;
	private String workflowRoleName;
	private String workflowApptStatusName;
	private String workflowOPDForm;
	private int workflowID;
	private int apptSchedlSMS;
	private int apptSchedlEmail;
	private int apptUpdatedSMS;
	private int apptUpdatedEmail;
	private int apptCancelledSMS;
	private int apptCancelledEmail;
	private int sendBillSMS;
	private int sendBillEmail;
	private int sendPrescSMS;
	private int sendPrescEmail;
	private int sendThanksToRefDocSMS;
	private int sendThanksToRefDocEmail;
	private int welcomeMsgSMS;
	private int welcomeMsgEmail;
	private int leaveApplSMS;
	private int leaveApplEmail;
	private int leaveApprovSMS;
	private int leaveApprovEmail;
	private int leaveRejSMS;
	private int leaveRejEmail;
	private int leaveCanclSMS;
	private int leaveCanclEmail;
	private int holidayIntimtnSMS;
	private int holidayIntimtnEmail;
	private int communicationID;
	private int patientID;
	private String apptDate;
	private String apptStartTime;
	private String apptEndTime;
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobileNo;
	private String regNo;
	private String[] workflowOPDTabName;
	private String workflowOPDTab;
	private double consultationCharge;
	private String startDate;
	private String endDate;
	private String slot1Name;
	private String slot2Name;
	private String slot1StartTime;
	private String slot1EndTime;
	private String slot2StartTime;
	private String slot2EndTime;
	private int emailReviewForm;
	private String reviewForm;
	private int smsReviewForm;
	private String[] clinicTagline;
	private String[] clinicWebsite;
	private String[] clinicLogo;
	private String[] clinicPageSize;
	private String[] clinicLetterHeadImage;
	private String[] clinicPatientForm;
	private String[] clinicReportForm;
	private String clinicSuffixName;
	private int cliniciaID;
	private int smsDailyAppt;
	private int emailDailyAppt;
	private int smsInventory;
	private int emailInventory;
	private int walkIn;
	private String reportForm;
	private String smsUsername;
	private String smsPassword;
	private String smsURL;
	private String smsSenderID;
	private String smsApiKey;
	private String currency;
	private int isDischarge;
	private int hasConsent;
	private String consentDocFileName;
	private String consentDocDBName;
	private File consentDoc;
	private String consentDocContentType;
	private int nextVisitDays;
	private String clinicSettingName;
	private String clinicSettingSiffix;
	private String clinicSettingStatus;

	private int clinicSettingID;
	private String[] dummySettingTextArr;
	private String[] dummySettingTextArrEdit;
	private String dummySettingTextEdit;

	private String[] clinicNameArr;
	private String[] clinicIDArr;

	private String uploadImg;

	private File logoFile;
	private String logoFileFileName;
	private String logoFileDBName;
	private String logoFileContentType;

	private File letterHeadFile;
	private String letterHeadFileFileName;
	private String letterHeadFileDBName;
	private String letterHeadFileContentType;

	private String NoOfVisits;
	private String DateStart;
	private String DateEnd;
	private String planStatus;
	private int planID;
	private String planNoOfVisits;
	private String planDateStart;
	private String planDateEnd;
	private String planStatus1;
	private String bucketName;
	private InputStream fileInputStream;
	private String[] mdNameArr;
	private String[] mdQualificationArr;
	private String[] mdStartDateArr;
	private String[] mdEndDateArr;
	private int labVisit;
	private List<File> mdSignatureImage = new ArrayList<File>();
	private List<String> mdSignatureImageContentType = new ArrayList<String>();
	private List<String> mdSignatureImageFileName = new ArrayList<String>();
	private int mdDetailsID;
	private String practiceMDName;
	private String practiceMDQualification;
	private String practiceMDSignatureImage;
	private String practiceMDStartDate;
	private String practiceMDEndDate;
	private String[] practiceMDEndDateEdit;
	private int[] mdDetailsIDEdit;
	private String clinicPhoneNo;
	private String[] clinicPhoneNoArray;
	private String[] editClinicPhoneNoArray;
	private int patLoginCredSMS;
	private int patLoginCredEmail;

	private int facilityDashboard;

	private String roomName;
	private int roomCapacity;
	private int roomTypeID;

	private String patienName;

	private String displayName;

	private String practiceURL;
	private int thirdPartyAPIIntegration;
	

	/**
	 * @return the updatedComment
	 */
	public String getUpdatedComment() {
		return updatedComment;
	}

	/**
	 * @param updatedComment the updatedComment to set
	 */
	public void setUpdatedComment(String updatedComment) {
		this.updatedComment = updatedComment;
	}

	/**
	 * @return the thirdPartyAPIIntegration
	 */
	public int getThirdPartyAPIIntegration() {
		return thirdPartyAPIIntegration;
	}

	/**
	 * @param thirdPartyAPIIntegration the thirdPartyAPIIntegration to set
	 */
	public void setThirdPartyAPIIntegration(int thirdPartyAPIIntegration) {
		this.thirdPartyAPIIntegration = thirdPartyAPIIntegration;
	}

	/**
	 * @return the practiceURL
	 */
	public String getPracticeURL() {
		return practiceURL;
	}

	/**
	 * @param practiceURL the practiceURL to set
	 */
	public void setPracticeURL(String practiceURL) {
		this.practiceURL = practiceURL;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the roomTypeID
	 */
	public int getRoomTypeID() {
		return roomTypeID;
	}

	/**
	 * @param roomTypeID the roomTypeID to set
	 */
	public void setRoomTypeID(int roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	/**
	 * @return the patienName
	 */
	public String getPatienName() {
		return patienName;
	}

	/**
	 * @param patienName the patienName to set
	 */
	public void setPatienName(String patienName) {
		this.patienName = patienName;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @return the roomCapacity
	 */
	public int getRoomCapacity() {
		return roomCapacity;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @param roomCapacity the roomCapacity to set
	 */
	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	/**
	 * @return the facilityDashboard
	 */
	public int getFacilityDashboard() {
		return facilityDashboard;
	}

	/**
	 * @param facilityDashboard the facilityDashboard to set
	 */
	public void setFacilityDashboard(int facilityDashboard) {
		this.facilityDashboard = facilityDashboard;
	}

	/**
	 * @return the patLoginCredSMS
	 */
	public int getPatLoginCredSMS() {
		return patLoginCredSMS;
	}

	/**
	 * @param patLoginCredSMS the patLoginCredSMS to set
	 */
	public void setPatLoginCredSMS(int patLoginCredSMS) {
		this.patLoginCredSMS = patLoginCredSMS;
	}

	/**
	 * @return the patLoginCredEmail
	 */
	public int getPatLoginCredEmail() {
		return patLoginCredEmail;
	}

	/**
	 * @param patLoginCredEmail the patLoginCredEmail to set
	 */
	public void setPatLoginCredEmail(int patLoginCredEmail) {
		this.patLoginCredEmail = patLoginCredEmail;
	}

	/**
	 * @return the editClinicPhoneNoArray
	 */
	public String[] getEditClinicPhoneNoArray() {
		return editClinicPhoneNoArray;
	}

	/**
	 * @param editClinicPhoneNoArray the editClinicPhoneNoArray to set
	 */
	public void setEditClinicPhoneNoArray(String[] editClinicPhoneNoArray) {
		this.editClinicPhoneNoArray = editClinicPhoneNoArray;
	}

	/**
	 * @return the clinicPhoneNoArray
	 */
	public String[] getClinicPhoneNoArray() {
		return clinicPhoneNoArray;
	}

	/**
	 * @param clinicPhoneNoArray the clinicPhoneNoArray to set
	 */
	public void setClinicPhoneNoArray(String[] clinicPhoneNoArray) {
		this.clinicPhoneNoArray = clinicPhoneNoArray;
	}

	/**
	 * @return the clinicPhoneNo
	 */
	public String getClinicPhoneNo() {
		return clinicPhoneNo;
	}

	/**
	 * @param clinicPhoneNo the clinicPhoneNo to set
	 */
	public void setClinicPhoneNo(String clinicPhoneNo) {
		this.clinicPhoneNo = clinicPhoneNo;
	}

	public String[] getPracticeMDEndDateEdit() {
		return practiceMDEndDateEdit;
	}

	public void setPracticeMDEndDateEdit(String[] practiceMDEndDateEdit) {
		this.practiceMDEndDateEdit = practiceMDEndDateEdit;
	}

	public int[] getMdDetailsIDEdit() {
		return mdDetailsIDEdit;
	}

	public void setMdDetailsIDEdit(int[] mdDetailsIDEdit) {
		this.mdDetailsIDEdit = mdDetailsIDEdit;
	}

	public int getMdDetailsID() {
		return mdDetailsID;
	}

	public void setMdDetailsID(int mdDetailsID) {
		this.mdDetailsID = mdDetailsID;
	}

	public String getPracticeMDName() {
		return practiceMDName;
	}

	public void setPracticeMDName(String practiceMDName) {
		this.practiceMDName = practiceMDName;
	}

	public String getPracticeMDQualification() {
		return practiceMDQualification;
	}

	public void setPracticeMDQualification(String practiceMDQualification) {
		this.practiceMDQualification = practiceMDQualification;
	}

	public String getPracticeMDSignatureImage() {
		return practiceMDSignatureImage;
	}

	public void setPracticeMDSignatureImage(String practiceMDSignatureImage) {
		this.practiceMDSignatureImage = practiceMDSignatureImage;
	}

	public String getPracticeMDStartDate() {
		return practiceMDStartDate;
	}

	public void setPracticeMDStartDate(String practiceMDStartDate) {
		this.practiceMDStartDate = practiceMDStartDate;
	}

	public String getPracticeMDEndDate() {
		return practiceMDEndDate;
	}

	public void setPracticeMDEndDate(String practiceMDEndDate) {
		this.practiceMDEndDate = practiceMDEndDate;
	}

	public List<File> getMdSignatureImage() {
		return mdSignatureImage;
	}

	public void setMdSignatureImage(List<File> mdSignatureImage) {
		this.mdSignatureImage = mdSignatureImage;
	}

	public List<String> getMdSignatureImageContentType() {
		return mdSignatureImageContentType;
	}

	public void setMdSignatureImageContentType(List<String> mdSignatureImageContentType) {
		this.mdSignatureImageContentType = mdSignatureImageContentType;
	}

	public List<String> getMdSignatureImageFileName() {
		return mdSignatureImageFileName;
	}

	public void setMdSignatureImageFileName(List<String> mdSignatureImageFileName) {
		this.mdSignatureImageFileName = mdSignatureImageFileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public int getLabVisit() {
		return labVisit;
	}

	public void setLabVisit(int labVisit) {
		this.labVisit = labVisit;
	}

	public String[] getMdNameArr() {
		return mdNameArr;
	}

	public void setMdNameArr(String[] mdNameArr) {
		this.mdNameArr = mdNameArr;
	}

	public String[] getMdQualificationArr() {
		return mdQualificationArr;
	}

	public void setMdQualificationArr(String[] mdQualificationArr) {
		this.mdQualificationArr = mdQualificationArr;
	}

	public String[] getMdStartDateArr() {
		return mdStartDateArr;
	}

	public void setMdStartDateArr(String[] mdStartDateArr) {
		this.mdStartDateArr = mdStartDateArr;
	}

	public String[] getMdEndDateArr() {
		return mdEndDateArr;
	}

	public void setMdEndDateArr(String[] mdEndDateArr) {
		this.mdEndDateArr = mdEndDateArr;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	/**
	 * @return the planStatus1
	 */
	public String getPlanStatus1() {
		return planStatus1;
	}

	/**
	 * @param planStatus1 the planStatus1 to set
	 */
	public void setPlanStatus1(String planStatus1) {
		this.planStatus1 = planStatus1;
	}

	/**
	 * @return the planNoOfVisits
	 */
	public String getPlanNoOfVisits() {
		return planNoOfVisits;
	}

	/**
	 * @param planNoOfVisits the planNoOfVisits to set
	 */
	public void setPlanNoOfVisits(String planNoOfVisits) {
		this.planNoOfVisits = planNoOfVisits;
	}

	/**
	 * @return the planDateStart
	 */
	public String getPlanDateStart() {
		return planDateStart;
	}

	/**
	 * @param planDateStart the planDateStart to set
	 */
	public void setPlanDateStart(String planDateStart) {
		this.planDateStart = planDateStart;
	}

	/**
	 * @return the planDateEnd
	 */
	public String getPlanDateEnd() {
		return planDateEnd;
	}

	/**
	 * @param planDateEnd the planDateEnd to set
	 */
	public void setPlanDateEnd(String planDateEnd) {
		this.planDateEnd = planDateEnd;
	}

	/**
	 * @return the planID
	 */
	public int getPlanID() {
		return planID;
	}

	/**
	 * @param planID the planID to set
	 */
	public void setPlanID(int planID) {
		this.planID = planID;
	}

	/**
	 * @return the planStatus
	 */
	public String getPlanStatus() {
		return planStatus;
	}

	/**
	 * @param planStatus the planStatus to set
	 */
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	/**
	 * @return the noOfVisits
	 */
	public String getNoOfVisits() {
		return NoOfVisits;
	}

	/**
	 * @param noOfVisits the noOfVisits to set
	 */
	public void setNoOfVisits(String noOfVisits) {
		NoOfVisits = noOfVisits;
	}

	/**
	 * @return the dateStart
	 */
	public String getDateStart() {
		return DateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}

	/**
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return DateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}

	/**
	 * @return the logoFile
	 */
	public File getLogoFile() {
		return logoFile;
	}

	/**
	 * @param logoFile the logoFile to set
	 */
	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}

	/**
	 * @return the letterHeadFile
	 */
	public File getLetterHeadFile() {
		return letterHeadFile;
	}

	/**
	 * @param letterHeadFile the letterHeadFile to set
	 */
	public void setLetterHeadFile(File letterHeadFile) {
		this.letterHeadFile = letterHeadFile;
	}

	/**
	 * @return the logoFileFileName
	 */
	public String getLogoFileFileName() {
		return logoFileFileName;
	}

	/**
	 * @param logoFileFileName the logoFileFileName to set
	 */
	public void setLogoFileFileName(String logoFileFileName) {
		this.logoFileFileName = logoFileFileName;
	}

	/**
	 * @return the logoFileDBName
	 */
	public String getLogoFileDBName() {
		return logoFileDBName;
	}

	/**
	 * @param logoFileDBName the logoFileDBName to set
	 */
	public void setLogoFileDBName(String logoFileDBName) {
		this.logoFileDBName = logoFileDBName;
	}

	/**
	 * @return the logoFileContentType
	 */
	public String getLogoFileContentType() {
		return logoFileContentType;
	}

	/**
	 * @param logoFileContentType the logoFileContentType to set
	 */
	public void setLogoFileContentType(String logoFileContentType) {
		this.logoFileContentType = logoFileContentType;
	}

	/**
	 * @return the letterHeadFileFileName
	 */
	public String getLetterHeadFileFileName() {
		return letterHeadFileFileName;
	}

	/**
	 * @param letterHeadFileFileName the letterHeadFileFileName to set
	 */
	public void setLetterHeadFileFileName(String letterHeadFileFileName) {
		this.letterHeadFileFileName = letterHeadFileFileName;
	}

	/**
	 * @return the letterHeadFileDBName
	 */
	public String getLetterHeadFileDBName() {
		return letterHeadFileDBName;
	}

	/**
	 * @param letterHeadFileDBName the letterHeadFileDBName to set
	 */
	public void setLetterHeadFileDBName(String letterHeadFileDBName) {
		this.letterHeadFileDBName = letterHeadFileDBName;
	}

	/**
	 * @return the letterHeadFileContentType
	 */
	public String getLetterHeadFileContentType() {
		return letterHeadFileContentType;
	}

	/**
	 * @param letterHeadFileContentType the letterHeadFileContentType to set
	 */
	public void setLetterHeadFileContentType(String letterHeadFileContentType) {
		this.letterHeadFileContentType = letterHeadFileContentType;
	}

	/**
	 * @return the uploadImg
	 */
	public String getUploadImg() {
		return uploadImg;
	}

	/**
	 * @param uploadImg the uploadImg to set
	 */
	public void setUploadImg(String uploadImg) {
		this.uploadImg = uploadImg;
	}

	/**
	 * @return the clinicIDArr
	 */
	public String[] getClinicIDArr() {
		return clinicIDArr;
	}

	/**
	 * @param clinicIDArr the clinicIDArr to set
	 */
	public void setClinicIDArr(String[] clinicIDArr) {
		this.clinicIDArr = clinicIDArr;
	}

	/**
	 * @return the clinicNameArr
	 */
	public String[] getClinicNameArr() {
		return clinicNameArr;
	}

	/**
	 * @param clinicNameArr the clinicNameArr to set
	 */
	public void setClinicNameArr(String[] clinicNameArr) {
		this.clinicNameArr = clinicNameArr;
	}

	/**
	 * @return the dummySettingTextEdit
	 */
	public String getDummySettingTextEdit() {
		return dummySettingTextEdit;
	}

	/**
	 * @param dummySettingTextEdit the dummySettingTextEdit to set
	 */
	public void setDummySettingTextEdit(String dummySettingTextEdit) {
		this.dummySettingTextEdit = dummySettingTextEdit;
	}

	/**
	 * @return the dummySettingTextArrEdit
	 */
	public String[] getDummySettingTextArrEdit() {
		return dummySettingTextArrEdit;
	}

	/**
	 * @param dummySettingTextArrEdit the dummySettingTextArrEdit to set
	 */
	public void setDummySettingTextArrEdit(String[] dummySettingTextArrEdit) {
		this.dummySettingTextArrEdit = dummySettingTextArrEdit;
	}

	/**
	 * @return the practiceClinicNameEdit
	 */
	public String[] getPracticeClinicNameEdit() {
		return practiceClinicNameEdit;
	}

	/**
	 * @param practiceClinicNameEdit the practiceClinicNameEdit to set
	 */
	public void setPracticeClinicNameEdit(String[] practiceClinicNameEdit) {
		this.practiceClinicNameEdit = practiceClinicNameEdit;
	}

	/**
	 * @return the practiceClinicSuffixEdit
	 */
	public String[] getPracticeClinicSuffixEdit() {
		return practiceClinicSuffixEdit;
	}

	/**
	 * @param practiceClinicSuffixEdit the practiceClinicSuffixEdit to set
	 */
	public void setPracticeClinicSuffixEdit(String[] practiceClinicSuffixEdit) {
		this.practiceClinicSuffixEdit = practiceClinicSuffixEdit;
	}

	/**
	 * @return the dummySettingTextArr
	 */
	public String[] getDummySettingTextArr() {
		return dummySettingTextArr;
	}

	/**
	 * @param dummySettingTextArr the dummySettingTextArr to set
	 */
	public void setDummySettingTextArr(String[] dummySettingTextArr) {
		this.dummySettingTextArr = dummySettingTextArr;
	}

	/**
	 * @return the clinicSettingID
	 */
	public int getClinicSettingID() {
		return clinicSettingID;
	}

	/**
	 * @param clinicSettingID the clinicSettingID to set
	 */
	public void setClinicSettingID(int clinicSettingID) {
		this.clinicSettingID = clinicSettingID;
	}

	/**
	 * @return the clinicSettingName
	 */
	public String getClinicSettingName() {
		return clinicSettingName;
	}

	/**
	 * @param clinicSettingName the clinicSettingName to set
	 */
	public void setClinicSettingName(String clinicSettingName) {
		this.clinicSettingName = clinicSettingName;
	}

	/**
	 * @return the clinicSettingSiffix
	 */
	public String getClinicSettingSiffix() {
		return clinicSettingSiffix;
	}

	/**
	 * @param clinicSettingSiffix the clinicSettingSiffix to set
	 */
	public void setClinicSettingSiffix(String clinicSettingSiffix) {
		this.clinicSettingSiffix = clinicSettingSiffix;
	}

	/**
	 * @return the clinicSettingStatus
	 */
	public String getClinicSettingStatus() {
		return clinicSettingStatus;
	}

	/**
	 * @param clinicSettingStatus the clinicSettingStatus to set
	 */
	public void setClinicSettingStatus(String clinicSettingStatus) {
		this.clinicSettingStatus = clinicSettingStatus;
	}

	/**
	 * @return the smsApiKey
	 */
	public String getSmsApiKey() {
		return smsApiKey;
	}

	/**
	 * @param smsApiKey the smsApiKey to set
	 */
	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}

	/**
	 * @return the nextVisitDays
	 */
	public int getNextVisitDays() {
		return nextVisitDays;
	}

	/**
	 * @param nextVisitDays the nextVisitDays to set
	 */
	public void setNextVisitDays(int nextVisitDays) {
		this.nextVisitDays = nextVisitDays;
	}

	/**
	 * @return the consentDocContentType
	 */
	public String getConsentDocContentType() {
		return consentDocContentType;
	}

	/**
	 * @param consentDocContentType the consentDocContentType to set
	 */
	public void setConsentDocContentType(String consentDocContentType) {
		this.consentDocContentType = consentDocContentType;
	}

	/**
	 * @return the consentDoc
	 */
	public File getConsentDoc() {
		return consentDoc;
	}

	/**
	 * @param consentDoc the consentDoc to set
	 */
	public void setConsentDoc(File consentDoc) {
		this.consentDoc = consentDoc;
	}

	/**
	 * @return the consentDocFileName
	 */
	public String getConsentDocFileName() {
		return consentDocFileName;
	}

	/**
	 * @param consentDocFileName the consentDocFileName to set
	 */
	public void setConsentDocFileName(String consentDocFileName) {
		this.consentDocFileName = consentDocFileName;
	}

	/**
	 * @return the consentDocDBName
	 */
	public String getConsentDocDBName() {
		return consentDocDBName;
	}

	/**
	 * @param consentDocDBName the consentDocDBName to set
	 */
	public void setConsentDocDBName(String consentDocDBName) {
		this.consentDocDBName = consentDocDBName;
	}

	/**
	 * @return the hasConsent
	 */
	public int getHasConsent() {
		return hasConsent;
	}

	/**
	 * @param hasConsent the hasConsent to set
	 */
	public void setHasConsent(int hasConsent) {
		this.hasConsent = hasConsent;
	}

	/**
	 * @return the isDischarge
	 */
	public int getIsDischarge() {
		return isDischarge;
	}

	/**
	 * @param isDischarge the isDischarge to set
	 */
	public void setIsDischarge(int isDischarge) {
		this.isDischarge = isDischarge;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsURL() {
		return smsURL;
	}

	public void setSmsURL(String smsURL) {
		this.smsURL = smsURL;
	}

	public String getSmsSenderID() {
		return smsSenderID;
	}

	public void setSmsSenderID(String smsSenderID) {
		this.smsSenderID = smsSenderID;
	}

	/**
	 * @return the clinicReportForm
	 */
	public String[] getClinicReportForm() {
		return clinicReportForm;
	}

	/**
	 * @param clinicReportForm the clinicReportForm to set
	 */
	public void setClinicReportForm(String[] clinicReportForm) {
		this.clinicReportForm = clinicReportForm;
	}

	/**
	 * @return the reportForm
	 */
	public String getReportForm() {
		return reportForm;
	}

	/**
	 * @param reportForm the reportForm to set
	 */
	public void setReportForm(String reportForm) {
		this.reportForm = reportForm;
	}

	/**
	 * @return the walkIn
	 */
	public int getWalkIn() {
		return walkIn;
	}

	/**
	 * @param walkIn the walkIn to set
	 */
	public void setWalkIn(int walkIn) {
		this.walkIn = walkIn;
	}

	/**
	 * @return the smsDailyAppt
	 */
	public int getSmsDailyAppt() {
		return smsDailyAppt;
	}

	/**
	 * @param smsDailyAppt the smsDailyAppt to set
	 */
	public void setSmsDailyAppt(int smsDailyAppt) {
		this.smsDailyAppt = smsDailyAppt;
	}

	/**
	 * @return the emailDailyAppt
	 */
	public int getEmailDailyAppt() {
		return emailDailyAppt;
	}

	/**
	 * @param emailDailyAppt the emailDailyAppt to set
	 */
	public void setEmailDailyAppt(int emailDailyAppt) {
		this.emailDailyAppt = emailDailyAppt;
	}

	/**
	 * @return the smsInventory
	 */
	public int getSmsInventory() {
		return smsInventory;
	}

	/**
	 * @param smsInventory the smsInventory to set
	 */
	public void setSmsInventory(int smsInventory) {
		this.smsInventory = smsInventory;
	}

	/**
	 * @return the emailInventory
	 */
	public int getEmailInventory() {
		return emailInventory;
	}

	/**
	 * @param emailInventory the emailInventory to set
	 */
	public void setEmailInventory(int emailInventory) {
		this.emailInventory = emailInventory;
	}

	/**
	 * @return the cliniciaID
	 */
	public int getCliniciaID() {
		return cliniciaID;
	}

	/**
	 * @param cliniciaID the cliniciaID to set
	 */
	public void setCliniciaID(int cliniciaID) {
		this.cliniciaID = cliniciaID;
	}

	/**
	 * @return the clinicSuffixName
	 */
	public String getClinicSuffixName() {
		return clinicSuffixName;
	}

	/**
	 * @param clinicSuffixName the clinicSuffixName to set
	 */
	public void setClinicSuffixName(String clinicSuffixName) {
		this.clinicSuffixName = clinicSuffixName;
	}

	/**
	 * @return the clinicPatientForm
	 */
	public String[] getClinicPatientForm() {
		return clinicPatientForm;
	}

	/**
	 * @param clinicPatientForm the clinicPatientForm to set
	 */
	public void setClinicPatientForm(String[] clinicPatientForm) {
		this.clinicPatientForm = clinicPatientForm;
	}

	/**
	 * @return the smsReviewForm
	 */
	public int getSmsReviewForm() {
		return smsReviewForm;
	}

	/**
	 * @param smsReviewForm the smsReviewForm to set
	 */
	public void setSmsReviewForm(int smsReviewForm) {
		this.smsReviewForm = smsReviewForm;
	}

	/**
	 * @return the clinicTagline
	 */
	public String[] getClinicTagline() {
		return clinicTagline;
	}

	/**
	 * @param clinicTagline the clinicTagline to set
	 */
	public void setClinicTagline(String[] clinicTagline) {
		this.clinicTagline = clinicTagline;
	}

	/**
	 * @return the clinicWebsite
	 */
	public String[] getClinicWebsite() {
		return clinicWebsite;
	}

	/**
	 * @param clinicWebsite the clinicWebsite to set
	 */
	public void setClinicWebsite(String[] clinicWebsite) {
		this.clinicWebsite = clinicWebsite;
	}

	/**
	 * @return the clinicLogo
	 */
	public String[] getClinicLogo() {
		return clinicLogo;
	}

	/**
	 * @param clinicLogo the clinicLogo to set
	 */
	public void setClinicLogo(String[] clinicLogo) {
		this.clinicLogo = clinicLogo;
	}

	/**
	 * @return the clinicPageSize
	 */
	public String[] getClinicPageSize() {
		return clinicPageSize;
	}

	/**
	 * @param clinicPageSize the clinicPageSize to set
	 */
	public void setClinicPageSize(String[] clinicPageSize) {
		this.clinicPageSize = clinicPageSize;
	}

	/**
	 * @return the clinicLetterHeadImage
	 */
	public String[] getClinicLetterHeadImage() {
		return clinicLetterHeadImage;
	}

	/**
	 * @param clinicLetterHeadImage the clinicLetterHeadImage to set
	 */
	public void setClinicLetterHeadImage(String[] clinicLetterHeadImage) {
		this.clinicLetterHeadImage = clinicLetterHeadImage;
	}

	/**
	 * @return the practiceClinicSuffix
	 */
	public String[] getPracticeClinicSuffix() {
		return practiceClinicSuffix;
	}

	/**
	 * @param practiceClinicSuffix the practiceClinicSuffix to set
	 */
	public void setPracticeClinicSuffix(String[] practiceClinicSuffix) {
		this.practiceClinicSuffix = practiceClinicSuffix;
	}

	/**
	 * @return the emailReviewForm
	 */
	public int getEmailReviewForm() {
		return emailReviewForm;
	}

	/**
	 * @param emailReviewForm the emailReviewForm to set
	 */
	public void setEmailReviewForm(int emailReviewForm) {
		this.emailReviewForm = emailReviewForm;
	}

	/**
	 * @return the reviewForm
	 */
	public String getReviewForm() {
		return reviewForm;
	}

	/**
	 * @param reviewForm the reviewForm to set
	 */
	public void setReviewForm(String reviewForm) {
		this.reviewForm = reviewForm;
	}

	/**
	 * @return the slot1Name
	 */
	public String getSlot1Name() {
		return slot1Name;
	}

	/**
	 * @param slot1Name the slot1Name to set
	 */
	public void setSlot1Name(String slot1Name) {
		this.slot1Name = slot1Name;
	}

	/**
	 * @return the slot2Name
	 */
	public String getSlot2Name() {
		return slot2Name;
	}

	/**
	 * @param slot2Name the slot2Name to set
	 */
	public void setSlot2Name(String slot2Name) {
		this.slot2Name = slot2Name;
	}

	/**
	 * @return the slot1StartTime
	 */
	public String getSlot1StartTime() {
		return slot1StartTime;
	}

	/**
	 * @param slot1StartTime the slot1StartTime to set
	 */
	public void setSlot1StartTime(String slot1StartTime) {
		this.slot1StartTime = slot1StartTime;
	}

	/**
	 * @return the slot1EndTime
	 */
	public String getSlot1EndTime() {
		return slot1EndTime;
	}

	/**
	 * @param slot1EndTime the slot1EndTime to set
	 */
	public void setSlot1EndTime(String slot1EndTime) {
		this.slot1EndTime = slot1EndTime;
	}

	/**
	 * @return the slot2StartTime
	 */
	public String getSlot2StartTime() {
		return slot2StartTime;
	}

	/**
	 * @param slot2StartTime the slot2StartTime to set
	 */
	public void setSlot2StartTime(String slot2StartTime) {
		this.slot2StartTime = slot2StartTime;
	}

	/**
	 * @return the slot2EndTime
	 */
	public String getSlot2EndTime() {
		return slot2EndTime;
	}

	/**
	 * @param slot2EndTime the slot2EndTime to set
	 */
	public void setSlot2EndTime(String slot2EndTime) {
		this.slot2EndTime = slot2EndTime;
	}

	/**
	 * @return the slot1
	 */
	public String[] getSlot1() {
		return slot1;
	}

	/**
	 * @param slot1 the slot1 to set
	 */
	public void setSlot1(String[] slot1) {
		this.slot1 = slot1;
	}

	/**
	 * @return the slot2
	 */
	public String[] getSlot2() {
		return slot2;
	}

	/**
	 * @param slot2 the slot2 to set
	 */
	public void setSlot2(String[] slot2) {
		this.slot2 = slot2;
	}

	/**
	 * @return the slot1Start
	 */
	public String[] getSlot1Start() {
		return slot1Start;
	}

	/**
	 * @param slot1Start the slot1Start to set
	 */
	public void setSlot1Start(String[] slot1Start) {
		this.slot1Start = slot1Start;
	}

	/**
	 * @return the slot1End
	 */
	public String[] getSlot1End() {
		return slot1End;
	}

	/**
	 * @param slot1End the slot1End to set
	 */
	public void setSlot1End(String[] slot1End) {
		this.slot1End = slot1End;
	}

	/**
	 * @return the slot2Start
	 */
	public String[] getSlot2Start() {
		return slot2Start;
	}

	/**
	 * @param slot2Start the slot2Start to set
	 */
	public void setSlot2Start(String[] slot2Start) {
		this.slot2Start = slot2Start;
	}

	/**
	 * @return the slot2End
	 */
	public String[] getSlot2End() {
		return slot2End;
	}

	/**
	 * @param slot2End the slot2End to set
	 */
	public void setSlot2End(String[] slot2End) {
		this.slot2End = slot2End;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the consultationCharge
	 */
	public double getConsultationCharge() {
		return consultationCharge;
	}

	/**
	 * @param consultationCharge the consultationCharge to set
	 */
	public void setConsultationCharge(double consultationCharge) {
		this.consultationCharge = consultationCharge;
	}

	/**
	 * @return the workflowOPDTab
	 */
	public String getWorkflowOPDTab() {
		return workflowOPDTab;
	}

	/**
	 * @param workflowOPDTab the workflowOPDTab to set
	 */
	public void setWorkflowOPDTab(String workflowOPDTab) {
		this.workflowOPDTab = workflowOPDTab;
	}

	/**
	 * @return the workflowOPDTabName
	 */
	public String[] getWorkflowOPDTabName() {
		return workflowOPDTabName;
	}

	/**
	 * @param workflowOPDTabName the workflowOPDTabName to set
	 */
	public void setWorkflowOPDTabName(String[] workflowOPDTabName) {
		this.workflowOPDTabName = workflowOPDTabName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @param regNo the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the patientID
	 */
	public int getPatientID() {
		return patientID;
	}

	/**
	 * @param patientID the patientID to set
	 */
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	/**
	 * @return the apptDate
	 */
	public String getApptDate() {
		return apptDate;
	}

	/**
	 * @param apptDate the apptDate to set
	 */
	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}

	/**
	 * @return the apptStartTime
	 */
	public String getApptStartTime() {
		return apptStartTime;
	}

	/**
	 * @param apptStartTime the apptStartTime to set
	 */
	public void setApptStartTime(String apptStartTime) {
		this.apptStartTime = apptStartTime;
	}

	/**
	 * @return the apptEndTime
	 */
	public String getApptEndTime() {
		return apptEndTime;
	}

	/**
	 * @param apptEndTime the apptEndTime to set
	 */
	public void setApptEndTime(String apptEndTime) {
		this.apptEndTime = apptEndTime;
	}

	/**
	 * @return the workdays
	 */
	public String[] getWorkdays() {
		return workdays;
	}

	/**
	 * @param workdays the workdays to set
	 */
	public void setWorkdays(String[] workdays) {
		this.workdays = workdays;
	}

	/**
	 * @return the workdaysName
	 */
	public String getWorkdaysName() {
		return workdaysName;
	}

	/**
	 * @param workdaysName the workdaysName to set
	 */
	public void setWorkdaysName(String workdaysName) {
		this.workdaysName = workdaysName;
	}

	/**
	 * @return the communicationID
	 */
	public int getCommunicationID() {
		return communicationID;
	}

	/**
	 * @param communicationID the communicationID to set
	 */
	public void setCommunicationID(int communicationID) {
		this.communicationID = communicationID;
	}

	/**
	 * @return the apptSchedlSMS
	 */
	public int getApptSchedlSMS() {
		return apptSchedlSMS;
	}

	/**
	 * @param apptSchedlSMS the apptSchedlSMS to set
	 */
	public void setApptSchedlSMS(int apptSchedlSMS) {
		this.apptSchedlSMS = apptSchedlSMS;
	}

	/**
	 * @return the apptSchedlEmail
	 */
	public int getApptSchedlEmail() {
		return apptSchedlEmail;
	}

	/**
	 * @param apptSchedlEmail the apptSchedlEmail to set
	 */
	public void setApptSchedlEmail(int apptSchedlEmail) {
		this.apptSchedlEmail = apptSchedlEmail;
	}

	/**
	 * @return the apptUpdatedSMS
	 */
	public int getApptUpdatedSMS() {
		return apptUpdatedSMS;
	}

	/**
	 * @param apptUpdatedSMS the apptUpdatedSMS to set
	 */
	public void setApptUpdatedSMS(int apptUpdatedSMS) {
		this.apptUpdatedSMS = apptUpdatedSMS;
	}

	/**
	 * @return the apptUpdatedEmail
	 */
	public int getApptUpdatedEmail() {
		return apptUpdatedEmail;
	}

	/**
	 * @param apptUpdatedEmail the apptUpdatedEmail to set
	 */
	public void setApptUpdatedEmail(int apptUpdatedEmail) {
		this.apptUpdatedEmail = apptUpdatedEmail;
	}

	/**
	 * @return the apptCancelledSMS
	 */
	public int getApptCancelledSMS() {
		return apptCancelledSMS;
	}

	/**
	 * @param apptCancelledSMS the apptCancelledSMS to set
	 */
	public void setApptCancelledSMS(int apptCancelledSMS) {
		this.apptCancelledSMS = apptCancelledSMS;
	}

	/**
	 * @return the apptCancelledEmail
	 */
	public int getApptCancelledEmail() {
		return apptCancelledEmail;
	}

	/**
	 * @param apptCancelledEmail the apptCancelledEmail to set
	 */
	public void setApptCancelledEmail(int apptCancelledEmail) {
		this.apptCancelledEmail = apptCancelledEmail;
	}

	/**
	 * @return the sendBillSMS
	 */
	public int getSendBillSMS() {
		return sendBillSMS;
	}

	/**
	 * @param sendBillSMS the sendBillSMS to set
	 */
	public void setSendBillSMS(int sendBillSMS) {
		this.sendBillSMS = sendBillSMS;
	}

	/**
	 * @return the sendBillEmail
	 */
	public int getSendBillEmail() {
		return sendBillEmail;
	}

	/**
	 * @param sendBillEmail the sendBillEmail to set
	 */
	public void setSendBillEmail(int sendBillEmail) {
		this.sendBillEmail = sendBillEmail;
	}

	/**
	 * @return the sendPrescSMS
	 */
	public int getSendPrescSMS() {
		return sendPrescSMS;
	}

	/**
	 * @param sendPrescSMS the sendPrescSMS to set
	 */
	public void setSendPrescSMS(int sendPrescSMS) {
		this.sendPrescSMS = sendPrescSMS;
	}

	/**
	 * @return the sendPrescEmail
	 */
	public int getSendPrescEmail() {
		return sendPrescEmail;
	}

	/**
	 * @param sendPrescEmail the sendPrescEmail to set
	 */
	public void setSendPrescEmail(int sendPrescEmail) {
		this.sendPrescEmail = sendPrescEmail;
	}

	/**
	 * @return the sendThanksToRefDocSMS
	 */
	public int getSendThanksToRefDocSMS() {
		return sendThanksToRefDocSMS;
	}

	/**
	 * @param sendThanksToRefDocSMS the sendThanksToRefDocSMS to set
	 */
	public void setSendThanksToRefDocSMS(int sendThanksToRefDocSMS) {
		this.sendThanksToRefDocSMS = sendThanksToRefDocSMS;
	}

	/**
	 * @return the sendThanksToRefDocEmail
	 */
	public int getSendThanksToRefDocEmail() {
		return sendThanksToRefDocEmail;
	}

	/**
	 * @param sendThanksToRefDocEmail the sendThanksToRefDocEmail to set
	 */
	public void setSendThanksToRefDocEmail(int sendThanksToRefDocEmail) {
		this.sendThanksToRefDocEmail = sendThanksToRefDocEmail;
	}

	/**
	 * @return the welcomeMsgSMS
	 */
	public int getWelcomeMsgSMS() {
		return welcomeMsgSMS;
	}

	/**
	 * @param welcomeMsgSMS the welcomeMsgSMS to set
	 */
	public void setWelcomeMsgSMS(int welcomeMsgSMS) {
		this.welcomeMsgSMS = welcomeMsgSMS;
	}

	/**
	 * @return the welcomeMsgEmail
	 */
	public int getWelcomeMsgEmail() {
		return welcomeMsgEmail;
	}

	/**
	 * @param welcomeMsgEmail the welcomeMsgEmail to set
	 */
	public void setWelcomeMsgEmail(int welcomeMsgEmail) {
		this.welcomeMsgEmail = welcomeMsgEmail;
	}

	/**
	 * @return the leaveApplSMS
	 */
	public int getLeaveApplSMS() {
		return leaveApplSMS;
	}

	/**
	 * @param leaveApplSMS the leaveApplSMS to set
	 */
	public void setLeaveApplSMS(int leaveApplSMS) {
		this.leaveApplSMS = leaveApplSMS;
	}

	/**
	 * @return the leaveApplEmail
	 */
	public int getLeaveApplEmail() {
		return leaveApplEmail;
	}

	/**
	 * @param leaveApplEmail the leaveApplEmail to set
	 */
	public void setLeaveApplEmail(int leaveApplEmail) {
		this.leaveApplEmail = leaveApplEmail;
	}

	/**
	 * @return the leaveApprovSMS
	 */
	public int getLeaveApprovSMS() {
		return leaveApprovSMS;
	}

	/**
	 * @param leaveApprovSMS the leaveApprovSMS to set
	 */
	public void setLeaveApprovSMS(int leaveApprovSMS) {
		this.leaveApprovSMS = leaveApprovSMS;
	}

	/**
	 * @return the leaveApprovEmail
	 */
	public int getLeaveApprovEmail() {
		return leaveApprovEmail;
	}

	/**
	 * @param leaveApprovEmail the leaveApprovEmail to set
	 */
	public void setLeaveApprovEmail(int leaveApprovEmail) {
		this.leaveApprovEmail = leaveApprovEmail;
	}

	/**
	 * @return the leaveRejSMS
	 */
	public int getLeaveRejSMS() {
		return leaveRejSMS;
	}

	/**
	 * @param leaveRejSMS the leaveRejSMS to set
	 */
	public void setLeaveRejSMS(int leaveRejSMS) {
		this.leaveRejSMS = leaveRejSMS;
	}

	/**
	 * @return the leaveRejEmail
	 */
	public int getLeaveRejEmail() {
		return leaveRejEmail;
	}

	/**
	 * @param leaveRejEmail the leaveRejEmail to set
	 */
	public void setLeaveRejEmail(int leaveRejEmail) {
		this.leaveRejEmail = leaveRejEmail;
	}

	/**
	 * @return the leaveCanclSMS
	 */
	public int getLeaveCanclSMS() {
		return leaveCanclSMS;
	}

	/**
	 * @param leaveCanclSMS the leaveCanclSMS to set
	 */
	public void setLeaveCanclSMS(int leaveCanclSMS) {
		this.leaveCanclSMS = leaveCanclSMS;
	}

	/**
	 * @return the leaveCanclEmail
	 */
	public int getLeaveCanclEmail() {
		return leaveCanclEmail;
	}

	/**
	 * @param leaveCanclEmail the leaveCanclEmail to set
	 */
	public void setLeaveCanclEmail(int leaveCanclEmail) {
		this.leaveCanclEmail = leaveCanclEmail;
	}

	/**
	 * @return the holidayIntimtnSMS
	 */
	public int getHolidayIntimtnSMS() {
		return holidayIntimtnSMS;
	}

	/**
	 * @param holidayIntimtnSMS the holidayIntimtnSMS to set
	 */
	public void setHolidayIntimtnSMS(int holidayIntimtnSMS) {
		this.holidayIntimtnSMS = holidayIntimtnSMS;
	}

	/**
	 * @return the holidayIntimtnEmail
	 */
	public int getHolidayIntimtnEmail() {
		return holidayIntimtnEmail;
	}

	/**
	 * @param holidayIntimtnEmail the holidayIntimtnEmail to set
	 */
	public void setHolidayIntimtnEmail(int holidayIntimtnEmail) {
		this.holidayIntimtnEmail = holidayIntimtnEmail;
	}

	/**
	 * @return the workflowID
	 */
	public int getWorkflowID() {
		return workflowID;
	}

	/**
	 * @param workflowID the workflowID to set
	 */
	public void setWorkflowID(int workflowID) {
		this.workflowID = workflowID;
	}

	/**
	 * @return the workflowStepSeqName
	 */
	public int getWorkflowStepSeqName() {
		return workflowStepSeqName;
	}

	/**
	 * @param workflowStepSeqName the workflowStepSeqName to set
	 */
	public void setWorkflowStepSeqName(int workflowStepSeqName) {
		this.workflowStepSeqName = workflowStepSeqName;
	}

	/**
	 * @return the workflowRoleName
	 */
	public String getWorkflowRoleName() {
		return workflowRoleName;
	}

	/**
	 * @param workflowRoleName the workflowRoleName to set
	 */
	public void setWorkflowRoleName(String workflowRoleName) {
		this.workflowRoleName = workflowRoleName;
	}

	/**
	 * @return the workflowApptStatusName
	 */
	public String getWorkflowApptStatusName() {
		return workflowApptStatusName;
	}

	/**
	 * @param workflowApptStatusName the workflowApptStatusName to set
	 */
	public void setWorkflowApptStatusName(String workflowApptStatusName) {
		this.workflowApptStatusName = workflowApptStatusName;
	}

	/**
	 * @return the workflowOPDForm
	 */
	public String getWorkflowOPDForm() {
		return workflowOPDForm;
	}

	/**
	 * @param workflowOPDForm the workflowOPDForm to set
	 */
	public void setWorkflowOPDForm(String workflowOPDForm) {
		this.workflowOPDForm = workflowOPDForm;
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
	 * @return the visitDuration
	 */
	public int getVisitDuration() {
		return visitDuration;
	}

	/**
	 * @param visitDuration the visitDuration to set
	 */
	public void setVisitDuration(int visitDuration) {
		this.visitDuration = visitDuration;
	}

	/**
	 * @return the billingType
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType the billingType to set
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return the careType
	 */
	public String getCareType() {
		return careType;
	}

	/**
	 * @param careType the careType to set
	 */
	public void setCareType(String careType) {
		this.careType = careType;
	}

	/**
	 * @return the newVisitFlag
	 */
	public int getNewVisitFlag() {
		return newVisitFlag;
	}

	/**
	 * @param newVisitFlag the newVisitFlag to set
	 */
	public void setNewVisitFlag(int newVisitFlag) {
		this.newVisitFlag = newVisitFlag;
	}

	/**
	 * @return the workflowStepSeq
	 */
	public String[] getWorkflowStepSeq() {
		return workflowStepSeq;
	}

	/**
	 * @param workflowStepSeq the workflowStepSeq to set
	 */
	public void setWorkflowStepSeq(String[] workflowStepSeq) {
		this.workflowStepSeq = workflowStepSeq;
	}

	/**
	 * @return the workflowRole
	 */
	public String[] getWorkflowRole() {
		return workflowRole;
	}

	/**
	 * @param workflowRole the workflowRole to set
	 */
	public void setWorkflowRole(String[] workflowRole) {
		this.workflowRole = workflowRole;
	}

	/**
	 * @return the workflowApptStatus
	 */
	public String[] getWorkflowApptStatus() {
		return workflowApptStatus;
	}

	/**
	 * @param workflowApptStatus the workflowApptStatus to set
	 */
	public void setWorkflowApptStatus(String[] workflowApptStatus) {
		this.workflowApptStatus = workflowApptStatus;
	}

	/**
	 * @return the workflowOPDFormName
	 */
	public String[] getWorkflowOPDFormName() {
		return workflowOPDFormName;
	}

	/**
	 * @param workflowOPDFormName the workflowOPDFormName to set
	 */
	public void setWorkflowOPDFormName(String[] workflowOPDFormName) {
		this.workflowOPDFormName = workflowOPDFormName;
	}

	/**
	 * @return the currencyForBilling1
	 */
	public String getCurrencyForBilling1() {
		return currencyForBilling1;
	}

	/**
	 * @param currencyForBilling1 the currencyForBilling1 to set
	 */
	public void setCurrencyForBilling1(String currencyForBilling1) {
		this.currencyForBilling1 = currencyForBilling1;
	}

	/**
	 * @return the clinicStartName
	 */
	public String getClinicStartName() {
		return clinicStartName;
	}

	/**
	 * @param clinicStartName the clinicStartName to set
	 */
	public void setClinicStartName(String clinicStartName) {
		this.clinicStartName = clinicStartName;
	}

	/**
	 * @return the clinicEndName
	 */
	public String getClinicEndName() {
		return clinicEndName;
	}

	/**
	 * @param clinicEndName the clinicEndName to set
	 */
	public void setClinicEndName(String clinicEndName) {
		this.clinicEndName = clinicEndName;
	}

	/**
	 * @return the breakStart1
	 */
	public String getBreakStart1() {
		return breakStart1;
	}

	/**
	 * @param breakStart1 the breakStart1 to set
	 */
	public void setBreakStart1(String breakStart1) {
		this.breakStart1 = breakStart1;
	}

	/**
	 * @return the breakEnd1
	 */
	public String getBreakEnd1() {
		return breakEnd1;
	}

	/**
	 * @param breakEnd1 the breakEnd1 to set
	 */
	public void setBreakEnd1(String breakEnd1) {
		this.breakEnd1 = breakEnd1;
	}

	/**
	 * @return the breakStart2
	 */
	public String getBreakStart2() {
		return breakStart2;
	}

	/**
	 * @param breakStart2 the breakStart2 to set
	 */
	public void setBreakStart2(String breakStart2) {
		this.breakStart2 = breakStart2;
	}

	/**
	 * @return the breakEnd2
	 */
	public String getBreakEnd2() {
		return breakEnd2;
	}

	/**
	 * @param breakEnd2 the breakEnd2 to set
	 */
	public void setBreakEnd2(String breakEnd2) {
		this.breakEnd2 = breakEnd2;
	}

	/**
	 * @return the clinicStartHH
	 */
	public String getClinicStartHH() {
		return clinicStartHH;
	}

	/**
	 * @param clinicStartHH the clinicStartHH to set
	 */
	public void setClinicStartHH(String clinicStartHH) {
		this.clinicStartHH = clinicStartHH;
	}

	/**
	 * @return the clinicStartMM
	 */
	public String getClinicStartMM() {
		return clinicStartMM;
	}

	/**
	 * @param clinicStartMM the clinicStartMM to set
	 */
	public void setClinicStartMM(String clinicStartMM) {
		this.clinicStartMM = clinicStartMM;
	}

	/**
	 * @return the clinicStartAMPM
	 */
	public String getClinicStartAMPM() {
		return clinicStartAMPM;
	}

	/**
	 * @param clinicStartAMPM the clinicStartAMPM to set
	 */
	public void setClinicStartAMPM(String clinicStartAMPM) {
		this.clinicStartAMPM = clinicStartAMPM;
	}

	/**
	 * @return the clinicEndHH
	 */
	public String getClinicEndHH() {
		return clinicEndHH;
	}

	/**
	 * @param clinicEndHH the clinicEndHH to set
	 */
	public void setClinicEndHH(String clinicEndHH) {
		this.clinicEndHH = clinicEndHH;
	}

	/**
	 * @return the clinicEndMM
	 */
	public String getClinicEndMM() {
		return clinicEndMM;
	}

	/**
	 * @param clinicEndMM the clinicEndMM to set
	 */
	public void setClinicEndMM(String clinicEndMM) {
		this.clinicEndMM = clinicEndMM;
	}

	/**
	 * @return the clinicEndAMPM
	 */
	public String getClinicEndAMPM() {
		return clinicEndAMPM;
	}

	/**
	 * @param clinicEndAMPM the clinicEndAMPM to set
	 */
	public void setClinicEndAMPM(String clinicEndAMPM) {
		this.clinicEndAMPM = clinicEndAMPM;
	}

	/**
	 * @return the breakStart1HH
	 */
	public String getBreakStart1HH() {
		return breakStart1HH;
	}

	/**
	 * @param breakStart1HH the breakStart1HH to set
	 */
	public void setBreakStart1HH(String breakStart1HH) {
		this.breakStart1HH = breakStart1HH;
	}

	/**
	 * @return the breakStart1MM
	 */
	public String getBreakStart1MM() {
		return breakStart1MM;
	}

	/**
	 * @param breakStart1MM the breakStart1MM to set
	 */
	public void setBreakStart1MM(String breakStart1MM) {
		this.breakStart1MM = breakStart1MM;
	}

	/**
	 * @return the breakStart1AMPM
	 */
	public String getBreakStart1AMPM() {
		return breakStart1AMPM;
	}

	/**
	 * @param breakStart1AMPM the breakStart1AMPM to set
	 */
	public void setBreakStart1AMPM(String breakStart1AMPM) {
		this.breakStart1AMPM = breakStart1AMPM;
	}

	/**
	 * @return the breakEnd1HH
	 */
	public String getBreakEnd1HH() {
		return breakEnd1HH;
	}

	/**
	 * @param breakEnd1HH the breakEnd1HH to set
	 */
	public void setBreakEnd1HH(String breakEnd1HH) {
		this.breakEnd1HH = breakEnd1HH;
	}

	/**
	 * @return the breakEnd1MM
	 */
	public String getBreakEnd1MM() {
		return breakEnd1MM;
	}

	/**
	 * @param breakEnd1MM the breakEnd1MM to set
	 */
	public void setBreakEnd1MM(String breakEnd1MM) {
		this.breakEnd1MM = breakEnd1MM;
	}

	/**
	 * @return the breakEnd1AMPM
	 */
	public String getBreakEnd1AMPM() {
		return breakEnd1AMPM;
	}

	/**
	 * @param breakEnd1AMPM the breakEnd1AMPM to set
	 */
	public void setBreakEnd1AMPM(String breakEnd1AMPM) {
		this.breakEnd1AMPM = breakEnd1AMPM;
	}

	/**
	 * @return the breakStart2HH
	 */
	public String getBreakStart2HH() {
		return breakStart2HH;
	}

	/**
	 * @param breakStart2HH the breakStart2HH to set
	 */
	public void setBreakStart2HH(String breakStart2HH) {
		this.breakStart2HH = breakStart2HH;
	}

	/**
	 * @return the breakStart2MM
	 */
	public String getBreakStart2MM() {
		return breakStart2MM;
	}

	/**
	 * @param breakStart2MM the breakStart2MM to set
	 */
	public void setBreakStart2MM(String breakStart2MM) {
		this.breakStart2MM = breakStart2MM;
	}

	/**
	 * @return the breakStart2AMPM
	 */
	public String getBreakStart2AMPM() {
		return breakStart2AMPM;
	}

	/**
	 * @param breakStart2AMPM the breakStart2AMPM to set
	 */
	public void setBreakStart2AMPM(String breakStart2AMPM) {
		this.breakStart2AMPM = breakStart2AMPM;
	}

	/**
	 * @return the breakEnd2HH
	 */
	public String getBreakEnd2HH() {
		return breakEnd2HH;
	}

	/**
	 * @param breakEnd2HH the breakEnd2HH to set
	 */
	public void setBreakEnd2HH(String breakEnd2HH) {
		this.breakEnd2HH = breakEnd2HH;
	}

	/**
	 * @return the breakEnd2MM
	 */
	public String getBreakEnd2MM() {
		return breakEnd2MM;
	}

	/**
	 * @param breakEnd2MM the breakEnd2MM to set
	 */
	public void setBreakEnd2MM(String breakEnd2MM) {
		this.breakEnd2MM = breakEnd2MM;
	}

	/**
	 * @return the breakEnd2AMPM
	 */
	public String getBreakEnd2AMPM() {
		return breakEnd2AMPM;
	}

	/**
	 * @param breakEnd2AMPM the breakEnd2AMPM to set
	 */
	public void setBreakEnd2AMPM(String breakEnd2AMPM) {
		this.breakEnd2AMPM = breakEnd2AMPM;
	}

	/**
	 * @return the clinicStart
	 */
	public String[] getClinicStart() {
		return clinicStart;
	}

	/**
	 * @param clinicStart the clinicStart to set
	 */
	public void setClinicStart(String[] clinicStart) {
		this.clinicStart = clinicStart;
	}

	/**
	 * @return the clinicEnd
	 */
	public String[] getClinicEnd() {
		return clinicEnd;
	}

	/**
	 * @param clinicEnd the clinicEnd to set
	 */
	public void setClinicEnd(String[] clinicEnd) {
		this.clinicEnd = clinicEnd;
	}

	/**
	 * @return the clinicBreakStart1
	 */
	public String[] getClinicBreakStart1() {
		return clinicBreakStart1;
	}

	/**
	 * @param clinicBreakStart1 the clinicBreakStart1 to set
	 */
	public void setClinicBreakStart1(String[] clinicBreakStart1) {
		this.clinicBreakStart1 = clinicBreakStart1;
	}

	/**
	 * @return the clinicBreakEnd1
	 */
	public String[] getClinicBreakEnd1() {
		return clinicBreakEnd1;
	}

	/**
	 * @param clinicBreakEnd1 the clinicBreakEnd1 to set
	 */
	public void setClinicBreakEnd1(String[] clinicBreakEnd1) {
		this.clinicBreakEnd1 = clinicBreakEnd1;
	}

	/**
	 * @return the clinicBreakStart2
	 */
	public String[] getClinicBreakStart2() {
		return clinicBreakStart2;
	}

	/**
	 * @param clinicBreakStart2 the clinicBreakStart2 to set
	 */
	public void setClinicBreakStart2(String[] clinicBreakStart2) {
		this.clinicBreakStart2 = clinicBreakStart2;
	}

	/**
	 * @return the clinicBreakEnd2
	 */
	public String[] getClinicBreakEnd2() {
		return clinicBreakEnd2;
	}

	/**
	 * @param clinicBreakEnd2 the clinicBreakEnd2 to set
	 */
	public void setClinicBreakEnd2(String[] clinicBreakEnd2) {
		this.clinicBreakEnd2 = clinicBreakEnd2;
	}

	/**
	 * @return the practiceSuffix
	 */
	public String getPracticeSuffix() {
		return practiceSuffix;
	}

	/**
	 * @param practiceSuffix the practiceSuffix to set
	 */
	public void setPracticeSuffix(String practiceSuffix) {
		this.practiceSuffix = practiceSuffix;
	}

	/**
	 * @return the patientForm
	 */
	public String getPatientForm() {
		return patientForm;
	}

	/**
	 * @param patientForm the patientForm to set
	 */
	public void setPatientForm(String patientForm) {
		this.patientForm = patientForm;
	}

	/**
	 * @return the practiceConfID
	 */
	public int getPracticeConfID() {
		return practiceConfID;
	}

	/**
	 * @param practiceConfID the practiceConfID to set
	 */
	public void setPracticeConfID(int practiceConfID) {
		this.practiceConfID = practiceConfID;
	}

	/**
	 * @return the clinicActivityStatus
	 */
	public String getClinicActivityStatus() {
		return clinicActivityStatus;
	}

	/**
	 * @param clinicActivityStatus the clinicActivityStatus to set
	 */
	public void setClinicActivityStatus(String clinicActivityStatus) {
		this.clinicActivityStatus = clinicActivityStatus;
	}

	/**
	 * @return the workflowStatus
	 */
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	/**
	 * @param workflowStatus the workflowStatus to set
	 */
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	/**
	 * @return the searchPracticeName
	 */
	public String getSearchPracticeName() {
		return searchPracticeName;
	}

	/**
	 * @param searchPracticeName the searchPracticeName to set
	 */
	public void setSearchPracticeName(String searchPracticeName) {
		this.searchPracticeName = searchPracticeName;
	}

	/**
	 * @return the workflowDummy
	 */
	public String[] getWorkflowDummy() {
		return workflowDummy;
	}

	/**
	 * @param workflowDummy the workflowDummy to set
	 */
	public void setWorkflowDummy(String[] workflowDummy) {
		this.workflowDummy = workflowDummy;
	}

	/**
	 * @return the workflow
	 */
	public String[] getWorkflow() {
		return workflow;
	}

	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(String[] workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return the practiceClinicName
	 */
	public String[] getPracticeClinicName() {
		return practiceClinicName;
	}

	/**
	 * @param practiceClinicName the practiceClinicName to set
	 */
	public void setPracticeClinicName(String[] practiceClinicName) {
		this.practiceClinicName = practiceClinicName;
	}

	/**
	 * @return the practiceName
	 */
	public String getPracticeName() {
		return practiceName;
	}

	/**
	 * @param practiceName the practiceName to set
	 */
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
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
	 * @return the searchClinic
	 */
	public String getSearchClinic() {
		return searchClinic;
	}

	/**
	 * @param searchClinic the searchClinic to set
	 */
	public void setSearchClinic(String searchClinic) {
		this.searchClinic = searchClinic;
	}

	/**
	 * @return the pathToLogoFileContentType
	 */
	public String getPathToLogoFileContentType() {
		return pathToLogoFileContentType;
	}

	/**
	 * @param pathToLogoFileContentType the pathToLogoFileContentType to set
	 */
	public void setPathToLogoFileContentType(String pathToLogoFileContentType) {
		this.pathToLogoFileContentType = pathToLogoFileContentType;
	}

	/**
	 * @return the realPath
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * @param realPath the realPath to set
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	/**
	 * @return the pathToLogoFileFileName
	 */
	public String getPathToLogoFileFileName() {
		return pathToLogoFileFileName;
	}

	/**
	 * @param pathToLogoFileFileName the pathToLogoFileFileName to set
	 */
	public void setPathToLogoFileFileName(String pathToLogoFileFileName) {
		this.pathToLogoFileFileName = pathToLogoFileFileName;
	}

	/**
	 * @return the pathToLogoFile
	 */
	public File getPathToLogoFile() {
		return pathToLogoFile;
	}

	/**
	 * @param pathToLogoFile the pathToLogoFile to set
	 */
	public void setPathToLogoFile(File pathToLogoFile) {
		this.pathToLogoFile = pathToLogoFile;
	}

	/**
	 * @return the clnicConfigID
	 */
	public int getClnicConfigID() {
		return clnicConfigID;
	}

	/**
	 * @param clnicConfigID the clnicConfigID to set
	 */
	public void setClnicConfigID(int clnicConfigID) {
		this.clnicConfigID = clnicConfigID;
	}

	/**
	 * @return the tagline
	 */
	public String getTagline() {
		return tagline;
	}

	/**
	 * @param tagline the tagline to set
	 */
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the pathToLogo
	 */
	public String getPathToLogo() {
		return pathToLogo;
	}

	/**
	 * @param pathToLogo the pathToLogo to set
	 */
	public void setPathToLogo(String pathToLogo) {
		this.pathToLogo = pathToLogo;
	}

	/**
	 * @return the consentDocuments
	 */
	public String getConsentDocuments() {
		return consentDocuments;
	}

	/**
	 * @param consentDocuments the consentDocuments to set
	 */
	public void setConsentDocuments(String consentDocuments) {
		this.consentDocuments = consentDocuments;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the lettrHeadImage
	 */
	public String getLettrHeadImage() {
		return lettrHeadImage;
	}

	/**
	 * @param lettrHeadImage the lettrHeadImage to set
	 */
	public void setLettrHeadImage(String lettrHeadImage) {
		this.lettrHeadImage = lettrHeadImage;
	}

	/**
	 * @return the sessionTimeout
	 */
	public String getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * @return the invalidAttempts
	 */
	public String getInvalidAttempts() {
		return invalidAttempts;
	}

	/**
	 * @param invalidAttempts the invalidAttempts to set
	 */
	public void setInvalidAttempts(String invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	/**
	 * @return the emailNotificationsTo
	 */
	public String getEmailNotificationsTo() {
		return emailNotificationsTo;
	}

	/**
	 * @param emailNotificationsTo the emailNotificationsTo to set
	 */
	public void setEmailNotificationsTo(String emailNotificationsTo) {
		this.emailNotificationsTo = emailNotificationsTo;
	}

	/**
	 * @return the sendEmailsFrom
	 */
	public String getSendEmailsFrom() {
		return sendEmailsFrom;
	}

	/**
	 * @param sendEmailsFrom the sendEmailsFrom to set
	 */
	public void setSendEmailsFrom(String sendEmailsFrom) {
		this.sendEmailsFrom = sendEmailsFrom;
	}

	/**
	 * @return the emailsFromPassword
	 */
	public String getEmailsFromPassword() {
		return emailsFromPassword;
	}

	/**
	 * @param emailsFromPassword the emailsFromPassword to set
	 */
	public void setEmailsFromPassword(String emailsFromPassword) {
		this.emailsFromPassword = emailsFromPassword;
	}

	/**
	 * @return the currencyForBilling
	 */
	public String getCurrencyForBilling() {
		return currencyForBilling;
	}

	/**
	 * @param currencyForBilling the currencyForBilling to set
	 */
	public void setCurrencyForBilling(String currencyForBilling) {
		this.currencyForBilling = currencyForBilling;
	}

	/**
	 * @return the apptID
	 */
	public int getApptID() {
		return apptID;
	}

	/**
	 * @param apptID the apptID to set
	 */
	public void setApptID(int apptID) {
		this.apptID = apptID;
	}

	/**
	 * @return the clinicTimeFromHH
	 */
	public String getClinicTimeFromHH() {
		return clinicTimeFromHH;
	}

	/**
	 * @param clinicTimeFromHH the clinicTimeFromHH to set
	 */
	public void setClinicTimeFromHH(String clinicTimeFromHH) {
		this.clinicTimeFromHH = clinicTimeFromHH;
	}

	/**
	 * @return the clinicTimeFromMM
	 */
	public String getClinicTimeFromMM() {
		return clinicTimeFromMM;
	}

	/**
	 * @param clinicTimeFromMM the clinicTimeFromMM to set
	 */
	public void setClinicTimeFromMM(String clinicTimeFromMM) {
		this.clinicTimeFromMM = clinicTimeFromMM;
	}

	/**
	 * @return the clinicTimeFromAMPM
	 */
	public String getClinicTimeFromAMPM() {
		return clinicTimeFromAMPM;
	}

	/**
	 * @param clinicTimeFromAMPM the clinicTimeFromAMPM to set
	 */
	public void setClinicTimeFromAMPM(String clinicTimeFromAMPM) {
		this.clinicTimeFromAMPM = clinicTimeFromAMPM;
	}

	/**
	 * @return the cliniTimeToHH
	 */
	public String getCliniTimeToHH() {
		return cliniTimeToHH;
	}

	/**
	 * @param cliniTimeToHH the cliniTimeToHH to set
	 */
	public void setCliniTimeToHH(String cliniTimeToHH) {
		this.cliniTimeToHH = cliniTimeToHH;
	}

	/**
	 * @return the clinicTimeToMM
	 */
	public String getClinicTimeToMM() {
		return clinicTimeToMM;
	}

	/**
	 * @param clinicTimeToMM the clinicTimeToMM to set
	 */
	public void setClinicTimeToMM(String clinicTimeToMM) {
		this.clinicTimeToMM = clinicTimeToMM;
	}

	/**
	 * @return the clinicTimeToAMPM
	 */
	public String getClinicTimeToAMPM() {
		return clinicTimeToAMPM;
	}

	/**
	 * @param clinicTimeToAMPM the clinicTimeToAMPM to set
	 */
	public void setClinicTimeToAMPM(String clinicTimeToAMPM) {
		this.clinicTimeToAMPM = clinicTimeToAMPM;
	}

	/**
	 * @return the breakTimeFromHH
	 */
	public String getBreakTimeFromHH() {
		return breakTimeFromHH;
	}

	/**
	 * @param breakTimeFromHH the breakTimeFromHH to set
	 */
	public void setBreakTimeFromHH(String breakTimeFromHH) {
		this.breakTimeFromHH = breakTimeFromHH;
	}

	/**
	 * @return the breakTimeFromMM
	 */
	public String getBreakTimeFromMM() {
		return breakTimeFromMM;
	}

	/**
	 * @param breakTimeFromMM the breakTimeFromMM to set
	 */
	public void setBreakTimeFromMM(String breakTimeFromMM) {
		this.breakTimeFromMM = breakTimeFromMM;
	}

	/**
	 * @return the breakTimeFromAMPM
	 */
	public String getBreakTimeFromAMPM() {
		return breakTimeFromAMPM;
	}

	/**
	 * @param breakTimeFromAMPM the breakTimeFromAMPM to set
	 */
	public void setBreakTimeFromAMPM(String breakTimeFromAMPM) {
		this.breakTimeFromAMPM = breakTimeFromAMPM;
	}

	/**
	 * @return the breakTimeToHH
	 */
	public String getBreakTimeToHH() {
		return breakTimeToHH;
	}

	/**
	 * @param breakTimeToHH the breakTimeToHH to set
	 */
	public void setBreakTimeToHH(String breakTimeToHH) {
		this.breakTimeToHH = breakTimeToHH;
	}

	/**
	 * @return the breakTimeToMM
	 */
	public String getBreakTimeToMM() {
		return breakTimeToMM;
	}

	/**
	 * @param breakTimeToMM the breakTimeToMM to set
	 */
	public void setBreakTimeToMM(String breakTimeToMM) {
		this.breakTimeToMM = breakTimeToMM;
	}

	/**
	 * @return the breakTimeToAMPM
	 */
	public String getBreakTimeToAMPM() {
		return breakTimeToAMPM;
	}

	/**
	 * @param breakTimeToAMPM the breakTimeToAMPM to set
	 */
	public void setBreakTimeToAMPM(String breakTimeToAMPM) {
		this.breakTimeToAMPM = breakTimeToAMPM;
	}

	/**
	 * @return the apptDuration
	 */
	public int getApptDuration() {
		return apptDuration;
	}

	/**
	 * @param apptDuration the apptDuration to set
	 */
	public void setApptDuration(int apptDuration) {
		this.apptDuration = apptDuration;
	}

	/**
	 * @return the acticityStatus
	 */
	public String getActicityStatus() {
		return acticityStatus;
	}

	/**
	 * @param acticityStatus the acticityStatus to set
	 */
	public void setActicityStatus(String acticityStatus) {
		this.acticityStatus = acticityStatus;
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

}
