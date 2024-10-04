package com.edhanvantari.form;

import java.io.File;

public class RegistrationForm {

	private String firstName;
	private String lastName;
	private String emailID;
	private String username;
	private String password;
	private String userType;
	private int userID;
	private String middleName;
	private String comments;
	private String fullName;
	private String gender;
	private String dateOfBirth;
	private String address;
	private String city;
	private String state;
	private String country;
	private String mobile;
	private String activityStatus;
	private int clinicID;
	private String specialsation;
	private String[] specialisationArray;
	private String OPDJSPName;
	private String IPDJSPName;
	private String PIN;
	private File profilePic;
	private String profilePicFileName;
	private String profilePicDBName;
	private String searchUserName;
	private String clinicName;
	private String profilePicContentType;
	private int practiceID;
	private String phoneNo;
	private String practiceName;
	private String departmentName;
	private String departmentDescription;
	private int departmentIncharge;
	private int departmentID;
	private String departmentInchargeName;
	private String searchDepartmentName;
	private String designationName;
	private int designationID;
	private String designationDescription;
	private String searchDesingationName;
	private String phone;
	private String employeeID;
	private String joiningDate;
	private int empID;
	private String leavingDate;
	private String searchEmployeeName;
	private String searchLeaveTypeName;
	private int leaveTypeID;
	private String[] leaveType;
	private String[] leaveTypeDescription;
	private String[] entitlementFrequency;
	private String[] autoEntitlement;
	private String[] deductionFrequency;
	private String[] autoDeduction;
	private String[] entitlementCount;
	private String[] expirationCount;
	private String[] carryOver;
	private String autoEntitlementName;
	private String autoDeductionName;
	private String leaveYear;
	private String startDate;
	private String endDate;
	private String statusCheck;
	private int reportingDesignID;
	private String reportingDesignationName;
	private int reportsTo;
	private String reportsToName;
	private File signature;
	private String signatureFileName;
	private String signatureDBName;
	private String signatureContentType;
	private String regNumber;
	private String qualification;
	private String clinicianRegNo;
	
	
	
	
	/**
	 * @return the clinicianRegNo
	 */
	public String getClinicianRegNo() {
		return clinicianRegNo;
	}

	/**
	 * @param clinicianRegNo the clinicianRegNo to set
	 */
	public void setClinicianRegNo(String clinicianRegNo) {
		this.clinicianRegNo = clinicianRegNo;
	}

	/**
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * @param qualification the qualification to set
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	/**
	 * @return the regNumber
	 */
	public String getRegNumber() {
		return regNumber;
	}

	/**
	 * @param regNumber the regNumber to set
	 */
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	/**
	 * @return the signature
	 */
	public File getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(File signature) {
		this.signature = signature;
	}

	/**
	 * @return the signatureFileName
	 */
	public String getSignatureFileName() {
		return signatureFileName;
	}

	/**
	 * @param signatureFileName the signatureFileName to set
	 */
	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}

	/**
	 * @return the signatureDBName
	 */
	public String getSignatureDBName() {
		return signatureDBName;
	}

	/**
	 * @param signatureDBName the signatureDBName to set
	 */
	public void setSignatureDBName(String signatureDBName) {
		this.signatureDBName = signatureDBName;
	}

	/**
	 * @return the signatureContentType
	 */
	public String getSignatureContentType() {
		return signatureContentType;
	}

	/**
	 * @param signatureContentType the signatureContentType to set
	 */
	public void setSignatureContentType(String signatureContentType) {
		this.signatureContentType = signatureContentType;
	}

	/**
	 * @return the reportsToName
	 */
	public String getReportsToName() {
		return reportsToName;
	}

	/**
	 * @param reportsToName
	 *            the reportsToName to set
	 */
	public void setReportsToName(String reportsToName) {
		this.reportsToName = reportsToName;
	}

	/**
	 * @return the reportsTo
	 */
	public int getReportsTo() {
		return reportsTo;
	}

	/**
	 * @param reportsTo
	 *            the reportsTo to set
	 */
	public void setReportsTo(int reportsTo) {
		this.reportsTo = reportsTo;
	}

	/**
	 * @return the reportingDesignationName
	 */
	public String getReportingDesignationName() {
		return reportingDesignationName;
	}

	/**
	 * @param reportingDesignationName
	 *            the reportingDesignationName to set
	 */
	public void setReportingDesignationName(String reportingDesignationName) {
		this.reportingDesignationName = reportingDesignationName;
	}

	/**
	 * @return the reportingDesignID
	 */
	public int getReportingDesignID() {
		return reportingDesignID;
	}

	/**
	 * @param reportingDesignID
	 *            the reportingDesignID to set
	 */
	public void setReportingDesignID(int reportingDesignID) {
		this.reportingDesignID = reportingDesignID;
	}

	/**
	 * @return the statusCheck
	 */
	public String getStatusCheck() {
		return statusCheck;
	}

	/**
	 * @param statusCheck
	 *            the statusCheck to set
	 */
	public void setStatusCheck(String statusCheck) {
		this.statusCheck = statusCheck;
	}

	/**
	 * @return the carryOver
	 */
	public String[] getCarryOver() {
		return carryOver;
	}

	/**
	 * @param carryOver
	 *            the carryOver to set
	 */
	public void setCarryOver(String[] carryOver) {
		this.carryOver = carryOver;
	}

	/**
	 * @return the leaveYear
	 */
	public String getLeaveYear() {
		return leaveYear;
	}

	/**
	 * @param leaveYear
	 *            the leaveYear to set
	 */
	public void setLeaveYear(String leaveYear) {
		this.leaveYear = leaveYear;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the leaveType
	 */
	public String[] getLeaveType() {
		return leaveType;
	}

	/**
	 * @param leaveType
	 *            the leaveType to set
	 */
	public void setLeaveType(String[] leaveType) {
		this.leaveType = leaveType;
	}

	/**
	 * @return the leaveTypeDescription
	 */
	public String[] getLeaveTypeDescription() {
		return leaveTypeDescription;
	}

	/**
	 * @param leaveTypeDescription
	 *            the leaveTypeDescription to set
	 */
	public void setLeaveTypeDescription(String[] leaveTypeDescription) {
		this.leaveTypeDescription = leaveTypeDescription;
	}

	/**
	 * @return the entitlementFrequency
	 */
	public String[] getEntitlementFrequency() {
		return entitlementFrequency;
	}

	/**
	 * @param entitlementFrequency
	 *            the entitlementFrequency to set
	 */
	public void setEntitlementFrequency(String[] entitlementFrequency) {
		this.entitlementFrequency = entitlementFrequency;
	}

	/**
	 * @return the autoEntitlement
	 */
	public String[] getAutoEntitlement() {
		return autoEntitlement;
	}

	/**
	 * @param autoEntitlement
	 *            the autoEntitlement to set
	 */
	public void setAutoEntitlement(String[] autoEntitlement) {
		this.autoEntitlement = autoEntitlement;
	}

	/**
	 * @return the deductionFrequency
	 */
	public String[] getDeductionFrequency() {
		return deductionFrequency;
	}

	/**
	 * @param deductionFrequency
	 *            the deductionFrequency to set
	 */
	public void setDeductionFrequency(String[] deductionFrequency) {
		this.deductionFrequency = deductionFrequency;
	}

	/**
	 * @return the autoDeduction
	 */
	public String[] getAutoDeduction() {
		return autoDeduction;
	}

	/**
	 * @param autoDeduction
	 *            the autoDeduction to set
	 */
	public void setAutoDeduction(String[] autoDeduction) {
		this.autoDeduction = autoDeduction;
	}

	/**
	 * @return the entitlementCount
	 */
	public String[] getEntitlementCount() {
		return entitlementCount;
	}

	/**
	 * @param entitlementCount
	 *            the entitlementCount to set
	 */
	public void setEntitlementCount(String[] entitlementCount) {
		this.entitlementCount = entitlementCount;
	}

	/**
	 * @return the expirationCount
	 */
	public String[] getExpirationCount() {
		return expirationCount;
	}

	/**
	 * @param expirationCount
	 *            the expirationCount to set
	 */
	public void setExpirationCount(String[] expirationCount) {
		this.expirationCount = expirationCount;
	}

	/**
	 * @return the autoEntitlementName
	 */
	public String getAutoEntitlementName() {
		return autoEntitlementName;
	}

	/**
	 * @param autoEntitlementName
	 *            the autoEntitlementName to set
	 */
	public void setAutoEntitlementName(String autoEntitlementName) {
		this.autoEntitlementName = autoEntitlementName;
	}

	/**
	 * @return the autoDeductionName
	 */
	public String getAutoDeductionName() {
		return autoDeductionName;
	}

	/**
	 * @param autoDeductionName
	 *            the autoDeductionName to set
	 */
	public void setAutoDeductionName(String autoDeductionName) {
		this.autoDeductionName = autoDeductionName;
	}

	/**
	 * @return the searchLeaveTypeName
	 */
	public String getSearchLeaveTypeName() {
		return searchLeaveTypeName;
	}

	/**
	 * @param searchLeaveTypeName
	 *            the searchLeaveTypeName to set
	 */
	public void setSearchLeaveTypeName(String searchLeaveTypeName) {
		this.searchLeaveTypeName = searchLeaveTypeName;
	}

	/**
	 * @return the leaveTypeID
	 */
	public int getLeaveTypeID() {
		return leaveTypeID;
	}

	/**
	 * @param leaveTypeID
	 *            the leaveTypeID to set
	 */
	public void setLeaveTypeID(int leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	/**
	 * @return the searchEmployeeName
	 */
	public String getSearchEmployeeName() {
		return searchEmployeeName;
	}

	/**
	 * @param searchEmployeeName
	 *            the searchEmployeeName to set
	 */
	public void setSearchEmployeeName(String searchEmployeeName) {
		this.searchEmployeeName = searchEmployeeName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the employeeID
	 */
	public String getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID
	 *            the employeeID to set
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return the joiningDate
	 */
	public String getJoiningDate() {
		return joiningDate;
	}

	/**
	 * @param joiningDate
	 *            the joiningDate to set
	 */
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	/**
	 * @return the empID
	 */
	public int getEmpID() {
		return empID;
	}

	/**
	 * @param empID
	 *            the empID to set
	 */
	public void setEmpID(int empID) {
		this.empID = empID;
	}

	/**
	 * @return the leavingDate
	 */
	public String getLeavingDate() {
		return leavingDate;
	}

	/**
	 * @param leavingDate
	 *            the leavingDate to set
	 */
	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}

	/**
	 * @return the designationName
	 */
	public String getDesignationName() {
		return designationName;
	}

	/**
	 * @param designationName
	 *            the designationName to set
	 */
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	/**
	 * @return the designationID
	 */
	public int getDesignationID() {
		return designationID;
	}

	/**
	 * @param designationID
	 *            the designationID to set
	 */
	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	/**
	 * @return the designationDescription
	 */
	public String getDesignationDescription() {
		return designationDescription;
	}

	/**
	 * @param designationDescription
	 *            the designationDescription to set
	 */
	public void setDesignationDescription(String designationDescription) {
		this.designationDescription = designationDescription;
	}

	/**
	 * @return the searchDesingationName
	 */
	public String getSearchDesingationName() {
		return searchDesingationName;
	}

	/**
	 * @param searchDesingationName
	 *            the searchDesingationName to set
	 */
	public void setSearchDesingationName(String searchDesingationName) {
		this.searchDesingationName = searchDesingationName;
	}

	/**
	 * @return the searchDepartmentName
	 */
	public String getSearchDepartmentName() {
		return searchDepartmentName;
	}

	/**
	 * @param searchDepartmentName
	 *            the searchDepartmentName to set
	 */
	public void setSearchDepartmentName(String searchDepartmentName) {
		this.searchDepartmentName = searchDepartmentName;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentDescription
	 */
	public String getDepartmentDescription() {
		return departmentDescription;
	}

	/**
	 * @param departmentDescription
	 *            the departmentDescription to set
	 */
	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	/**
	 * @return the departmentIncharge
	 */
	public int getDepartmentIncharge() {
		return departmentIncharge;
	}

	/**
	 * @param departmentIncharge
	 *            the departmentIncharge to set
	 */
	public void setDepartmentIncharge(int departmentIncharge) {
		this.departmentIncharge = departmentIncharge;
	}

	/**
	 * @return the departmentID
	 */
	public int getDepartmentID() {
		return departmentID;
	}

	/**
	 * @param departmentID
	 *            the departmentID to set
	 */
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * @return the departmentInchargeName
	 */
	public String getDepartmentInchargeName() {
		return departmentInchargeName;
	}

	/**
	 * @param departmentInchargeName
	 *            the departmentInchargeName to set
	 */
	public void setDepartmentInchargeName(String departmentInchargeName) {
		this.departmentInchargeName = departmentInchargeName;
	}

	/**
	 * @return the practiceName
	 */
	public String getPracticeName() {
		return practiceName;
	}

	/**
	 * @param practiceName
	 *            the practiceName to set
	 */
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the practiceID
	 */
	public int getPracticeID() {
		return practiceID;
	}

	/**
	 * @param practiceID
	 *            the practiceID to set
	 */
	public void setPracticeID(int practiceID) {
		this.practiceID = practiceID;
	}

	/**
	 * @return the profilePicContentType
	 */
	public String getProfilePicContentType() {
		return profilePicContentType;
	}

	/**
	 * @param profilePicContentType
	 *            the profilePicContentType to set
	 */
	public void setProfilePicContentType(String profilePicContentType) {
		this.profilePicContentType = profilePicContentType;
	}

	/**
	 * @return the clinicName
	 */
	public String getClinicName() {
		return clinicName;
	}

	/**
	 * @param clinicName
	 *            the clinicName to set
	 */
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	/**
	 * @return the searchUserName
	 */
	public String getSearchUserName() {
		return searchUserName;
	}

	/**
	 * @param searchUserName
	 *            the searchUserName to set
	 */
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	/**
	 * @return the profilePic
	 */
	public File getProfilePic() {
		return profilePic;
	}

	/**
	 * @param profilePic
	 *            the profilePic to set
	 */
	public void setProfilePic(File profilePic) {
		this.profilePic = profilePic;
	}

	/**
	 * @return the profilePicFileName
	 */
	public String getProfilePicFileName() {
		return profilePicFileName;
	}

	/**
	 * @param profilePicFileName
	 *            the profilePicFileName to set
	 */
	public void setProfilePicFileName(String profilePicFileName) {
		this.profilePicFileName = profilePicFileName;
	}

	/**
	 * @return the profilePicDBName
	 */
	public String getProfilePicDBName() {
		return profilePicDBName;
	}

	/**
	 * @param profilePicDBName
	 *            the profilePicDBName to set
	 */
	public void setProfilePicDBName(String profilePicDBName) {
		this.profilePicDBName = profilePicDBName;
	}

	/**
	 * @return the pIN
	 */
	public String getPIN() {
		return PIN;
	}

	/**
	 * @param pIN
	 *            the pIN to set
	 */
	public void setPIN(String pIN) {
		PIN = pIN;
	}

	/**
	 * @return the oPDJSPName
	 */
	public String getOPDJSPName() {
		return OPDJSPName;
	}

	/**
	 * @param oPDJSPName
	 *            the oPDJSPName to set
	 */
	public void setOPDJSPName(String oPDJSPName) {
		OPDJSPName = oPDJSPName;
	}

	/**
	 * @return the iPDJSPName
	 */
	public String getIPDJSPName() {
		return IPDJSPName;
	}

	/**
	 * @param iPDJSPName
	 *            the iPDJSPName to set
	 */
	public void setIPDJSPName(String iPDJSPName) {
		IPDJSPName = iPDJSPName;
	}

	/**
	 * @return the specialisationArray
	 */
	public String[] getSpecialisationArray() {
		return specialisationArray;
	}

	/**
	 * @param specialisationArray
	 *            the specialisationArray to set
	 */
	public void setSpecialisationArray(String[] specialisationArray) {
		this.specialisationArray = specialisationArray;
	}

	/**
	 * @return the specialsation
	 */
	public String getSpecialsation() {
		return specialsation;
	}

	/**
	 * @param specialsation
	 *            the specialsation to set
	 */
	public void setSpecialsation(String specialsation) {
		this.specialsation = specialsation;
	}

	/**
	 * @return the clinicID
	 */
	public int getClinicID() {
		return clinicID;
	}

	/**
	 * @param clinicID
	 *            the clinicID to set
	 */
	public void setClinicID(int clinicID) {
		this.clinicID = clinicID;
	}

	/**
	 * @return the activityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param activityStatus
	 *            the activityStatus to set
	 */
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID
	 *            the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

}
