package com.edhanvantari.util;

public class QueryMaker {
	
    public static final String INSERT_TEST_TYPE_TEMPLATES_QUERY = "INSERT INTO TestTypeTemplates (testType, templateID, pvlabtestID) VALUES (?,?,?)";
    
    public static final String RETRIEVE_SELECTED_TEMPLATES = "SELECT ttt.templateID, t.TempName FROM TestTypeTemplates ttt JOIN Templates t ON ttt.templateID = t.id WHERE ttt.pvlabtestID = ?";
	
    public static final String REMOVE_SELECTED_TEMPLATES = "DELETE FROM TestTypeTemplates WHERE pvlabtestID = ? AND templateID = ?";
    
	public static final String RETRIEVE_RECEIPT_DETAILS_BY_VISITID = "SELECT consultationCharges FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_USER_CREDENTIALS = "SELECT * FROM AppUser WHERE activityStatus = ?";

	public static final String RETRIEVE_PATIENT_CREDENTIALS = "SELECT * FROM Patient WHERE username = ?";

	public static final String INSERT_USER_DETAIL = "INSERT INTO AppUser (username, password, userType, activityStatus, defaultClinicID, pin, practiceID, firstName, middleName, lastName, address, city, state, country, phone, mobile, email, profilePic,  signImage, qualification,clinicianRegNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_USER_DETAIL_WITH_OPD_IPD_JSP_NAME = "INSERT INTO AppUser (username, password, userType, activityStatus, firstName, middleName, lastName, specialization, clinicID, address, city, state, country, phone, mobile, email, opdPageName, ipdPageName, pin, profilePic) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_SIGN_UP_USER_DETAIL = "INSERT INTO UserRequest (firstName,middleName, lastName, email, username,comments, approvalStatus) VALUES (?,?,?,?,?,?,?)";

	public static final String RETREIVE_EDIT_USER_LIST = "SELECT id, username, userType, activityStatus, firstName,middleName, lastName FROM AppUser WHERE practiceID = ?";

	public static final String RETREIVE_USER_BY_USER_ID = "SELECT * FROM AppUser WHERE id = ?";

	public static final String RETREIVE_PATIENT_BY_PATIENT_ID = "SELECT * FROM Patient WHERE id = ?";

	public static final String UPDATE_USER_DETAIL = "UPDATE AppUser SET password = ?,userType = ?, defaultClinicID = ?, pin = ?, practiceID = ?, activityStatus = ?, firstName = ?, middleName =?, lastName = ?, address = ?, city = ?, state = ?, country = ?, phone = ?, mobile = ?, email = ?, profilePic = ?, signImage = ?, qualification = ?, clinicianRegNo = ?,username=? WHERE id = ? ";

	public static final String UPDATE_USER_DETAIL_WITH_OPD_IPD_JSP_NAME = "UPDATE AppUser SET password = ?,userType = ?, firstName = ?, middleName = ?, lastName = ?, specialization = ?, clinicID = ?, address = ?, city = ?, state = ?, country = ?, phone = ?, mobile = ?, email = ?, opdPageName = ?, ipdPageName = ?, pin = ?, profilePic = ? WHERE id = ?";

	public static final String UPDATE_USER_REQUEST_TABLE = "UPDATE UserRequest SET approvalStatus = ? WHERE id = ?";

	public static final String REJECT_USER = "UPDATE AppUser SET activityStatus=? WHERE id=?";

	public static final String UPDATE_ACTIVITY_STATUS_ACTIVE = "UPDATE AppUser SET activityStatus=? WHERE id=?";

	public static final String RETREIVE_USER_ID = "SELECT id from AppUser WHERE userRequestID = ?";

	public static final String INSERT_CLINICIAN_DETAIL = "INSERT INTO Clinician (firstName, middleName, lastName, userID, clinicID) VALUES (?,?,?,?,?)";

	public static final String INSERT_PATIENT_DETAIL = "INSERT INTO Patient (firstName, middleName, lastName, userID, clinicID) VALUES (?,?,?,?,?)";

	public static final String INSERT_CONTACT_INFO = "INSERT INTO ContactInformation (address, city, state, country, phone, mobile, email, userID) VALUES (?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_USERID = "SELECT id FROM AppUser WHERE username=?";

	public static final String RETRIEVE_PATIENTID = "SELECT id FROM Patient WHERE username=?";

	public static final String VERIFY_USERNAME = "SELECT * FROM AppUser WHERE username = ? AND practiceID = ?";

	public static final String VERIFY_USERNAME_WITH_USER_ID = "SELECT * FROM AppUser WHERE username = ? AND practiceID = ? AND id <> ?";

	public static final String RETREIVE_USER_BY_USER_ID1 = "SELECT firstName, middleName, lastName,clinicID FROM Clinician WHERE userID = ?";

	public static final String RETREIVE_USER_BY_USER_ID2 = "SELECT firstName, middleName, lastName, clinicID FROM Staff WHERE userID = ?";

	public static final String RETREIVE_USER_BY_USER_ID3 = "SELECT firstName, middleName, lastName,clinicID FROM Patient WHERE userID = ?";

	public static final String RETREIVE_USER_BY_USER_ID4 = "SELECT address, city, state, country, phone, mobile, email FROM ContactInformation WHERE userID = ?";

	public static final String RETREIVE_CLINIC_LIST = "SELECT * FROM Clinic";

	public static final String RETREIVE_CLINIC_LIST_BY_CLINIC_ID = "SELECT * FROM Clinic WHERE id = ?";

	public static final String UPDATE_CLINIC = "UPDATE Clinic SET name = ?, clinicType = ?, activityStatus = ? WHERE id = ?";

	public static final String DELETE_CLINIC = "UPDATE Clinic SET activityStatus = ? WHERE id = ?";

	public static final String UPDATE_ADMIN_USER_STATUS = "UPDATE AppUser SET userType = ? WHERE id = ?";

	public static final String UPDATE_CLINICIAN_DETAIL = "UPDATE Clinician SET firstName = ?, middleName = ?, lastName = ?, clinicID = ?  WHERE userID = ?";

	public static final String UPDATE_STAFF_DETAIL = "UPDATE Staff SET firstName = ?, middleName = ?, lastName = ?, clinicID = ?  WHERE userID = ?";

	public static final String UPDATE_PATIENT_DETAIL = "UPDATE Patient SET firstName = ?, middleName = ?, lastName = ?, clinicID = ?  WHERE userID = ?";

	public static final String UPDATE_CONTACT_INFO = "UPDATE ContactInformation SET address = ?, city = ?, state = ?, country = ?, phone = ?, mobile = ?, email = ?  WHERE userID = ?";

	public static final String INSERT_ADMINISTRATOR = "INSERT INTO Administrator (firstName, middleName, lastName, userID) VALUES (?,?,?,?)";

	public static final String VERIFY_EMAIL = "SELECT email FROM ContactInformation WHERE email=?";

	public static final String RETRIEVE_USER_ID = "SELECT userID FROM ContactInformation WHERE email=?";

	public static final String UPDATE_USER_PASSWORD = "UPDATE AppUser SET password = ? WHERE id = ?";

	public static final String RETRIEVE_USER_ID_FROM_LOGIN_ATTEMPT = "SELECT userID FROM LoginAttempt WHERE userID = ? AND activityStatus = ? ";

	public static final String RETRIEVE_PATIENT_ID_FROM_PLOGIN_ATTEMPT = "SELECT patientID FROM PLoginAttempt WHERE patientID = ? AND activityStatus = ? ";

	public static final String INSERT_INTO_LOGIN_ATTEMPT = "INSERT INTO LoginAttempt (attemptCounter,userID, dateAndTime, activityStatus) VALUES (?,?,?,?) ";

	public static final String INSERT_INTO_P_LOGIN_ATTEMPT = "INSERT INTO PLoginAttempt (attemptCounter,patientID, dateAndTime, activityStatus) VALUES (?,?,?,?) ";

	public static final String UPDATE_LOGIN_ATTEMPT = "UPDATE LoginAttempt SET attemptCounter = ?  WHERE userID = ? AND dateAndTime = ?";

	public static final String UPDATE_P_LOGIN_ATTEMPT = "UPDATE PLoginAttempt SET attemptCounter = ?  WHERE patientID = ? AND dateAndTime = ?";

	public static final String UPDATE_USER_STATUS = "UPDATE AppUser SET activityStatus = ?  WHERE id = ? ";

	public static final String UPDATE_PATIENT_STATUS = "UPDATE Patient SET activityStatus = ?  WHERE id = ? ";

	public static final String RETRIVE_LOCKED_USER_STATUS = "SELECT activityStatus FROM AppUser WHERE username = ? ";

	public static final String RETRIVE_LOCKED_PATIENT_STATUS = "SELECT activityStatus FROM Patient WHERE username = ? ";

	public static final String INSERT_LOGIN_AUDIT = "INSERT INTO AuditLog (userid, action, ipaddress) VALUES (?,?,?)";

	public static final String INSERT_PATIENT_DETAILS = "INSERT INTO Patient (firstName, middleName, lastName, gender, rhFactor, dateOfBirth, bloodGroup, age, mobile, email, phone, address, city, state, country, practiceID, occupation, ec, practiceRegNumber, activityStatus, referredBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_USERNAME_PASSWORD_LOGINSTATUS = "UPDATE Patient SET username = ?, password = ?, loginStatus = ? WHERE id = ?";

	public static final String RETRIEVE_USERNAME_from_PATIENT_BY_ID = "SELECT username FROM Patient WHERE patientID = ?";

	public static final String RETRIEVE_PASSWORD_from_PATIENT_BY_ID = "SELECT password FROM Patient WHERE patientID = ?";

	public static final String RETRIEVE_PATIENT_ID = "SELECT id FROM Patient WHERE firstName = ? AND lastName = ? AND practiceID = ?";

	public static final String INSERT_CONTACT_INFORMATION_DETAILS = "INSERT INTO PatientContact (address, city, state, country, phone, mobile, email, patientID) VALUES (?,?,?,?,?,?,?,?)";

	public static final String VERIFY_PATIENT_DETAIL = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND activityStatus = ?";

	public static final String RETREIVE_PATIENT_LIST = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender,p.practiceRegNumber, (SELECT regNumber FROM ClinicRegistration WHERE patientID = p.id and clinicID = ?) AS regNo FROM Patient AS p WHERE p.practiceID = ? AND p.activityStatus = ?";

	public static final String RETREIVE_PATIENT_VISIT_LIST = "SELECT v.id, v.visitDate, v.visitTypeID, v.apptID, (SELECT name FROM PVVisitType WHERE id = v.visitTypeID)AS visitType, diagnosis, (SELECT formName FROM PVVisitType WHERE clinicID = ? AND name = visitType)AS CformName , (SELECT jspPageName FROM FormMapping WHERE formName =CformName) AS jspPageName FROM Visit AS v WHERE patientID = ? AND clinicID = ? ORDER BY id DESC";

	public static final String RETREIVE_PATIENT_LIST_BY_ID = "SELECT * FROM Patient WHERE id = ?";

	public static final String RETREIVE_IDENTIFICATION_BY_ID = "SELECT idNumber, idDocumentPath, idDocument FROM Identification WHERE patientID = ?";

	public static final String RETREIVE_CONTACT_INFO_LIST_BY_ID = "SELECT * FROM PatientContact WHERE patientID = ?";

	public static final String RETREIVE_EMERGENCY_CONTACT_LIST_BY_ID = "SELECT * FROM EmergencyContact WHERE patientID = ?";

	public static final String UPDATE_PATIENT_DETAILS = "UPDATE Patient SET firstName = ?, middleName = ?, lastName = ?, gender = ?, rhFactor = ?, dateOfBirth = ?, bloodGroup = ?, age = ?, mobile = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, country = ?, practiceID = ?, ec = ?, occupation = ?, practiceRegNumber = ?, referredBy = ? WHERE id = ?";

	public static final String UPDATE_PATIENT_DETAILS1 = "UPDATE Patient SET gender = ?, rhFactor = ?, dateOfBirth = ?, bloodGroup = ?, age = ?, mobile = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, country = ?, practiceID = ?, ec = ?, occupation = ?  WHERE id = ?";

	public static final String UPDATE_CONTACT_INFO_DETAILS = "UPDATE PatientContact SET address = ?, city = ?, state = ?, country =?, phone = ?, mobile = ?, email = ?  WHERE patientID = ?";

	public static final String VERFIY_PATIENT_CREDENTIALS = "SELECT * FROM Patient WHERE id = ?";

	public static final String VERIFY_NAME_DOB = "SELECT * FROM Patient WHERE firstName = ? AND middleName = ? AND lastName = ? AND dateOfBirth = ? AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_LNAME_DOB = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND dateOfBirth = ?  AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_LNAME_AGE = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND age = ? AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_LNAME_GENDER = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND gender = ? AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_LNAME_AGE_GENDER = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND age = ? AND gender = ? AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_MNAME_LNAME = "SELECT * FROM Patient WHERE firstName = ? AND middleName = ? AND lastName = ?  AND activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_FNAME_LNAME = "SELECT * FROM Patient WHERE firstName = ? AND lastName = ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETREIVE_PATIENT_NAME_DOB_LIST = "SELECT * FROM Patient WHERE ((firstName = ? AND middleName = ? AND lastName = ?  AND dateOfBirth = ?) OR (firstName = ? AND lastName = ? AND dateOfBirth = ?) OR (firstName = ? AND middleName = ? AND lastName = ?) OR (firstName = ? AND lastName = ?) OR (firstName = ? AND lastName = ? AND age = ?) OR (firstName = ? AND lastName = ? AND gender = ?) OR (firstName = ? AND lastName = ? AND age = ? AND gender = ?)) AND practiceID = ?";

	public static final String INSERT_EMERGENCY_CONTACT_INFORMATION_DETAILS = "INSERT INTO EmergencyContact (firstName, middleName, lastName, address, city, state, country, phone, mobile, email, patientID, relationToPatient) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_EMERGENCY_CONTACT_DETAILS = "UPDATE EmergencyContact SET firstName = ?, middleName = ?, lastName = ?, address = ?, city = ?, state = ?, country =?, phone = ?, mobile = ?, email = ?, relationToPatient =  ? WHERE patientID = ?";

	public static final String INSERT_IDENTIFICATION_DETAILS = "INSERT INTO Identification (idDocument, idNumber, patientID) VALUES (?,?,?)";

	public static final String UPDATE_IDENTIFICATION_DETAILS = "UPDATE Identification SET idNumber = ? WHERE patientID = ?";

	public static final String INSERT_VISIT_DETAILS = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, nextVisitDate, clinicID, onExamination, clinicianID, systemicHistory, occularHistory, personalHistory, complainingOf ,dateOfDischarge, `procedure`, admission_time, discharge_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // Change
	
	public static final String INSERT_COMPLAINING_OF_DETAILS = "INSERT INTO OphthalmologyOPD (leftEyeHistory, leftEyeDuration, rightEyeHistory, rightEyeDuration, visitID) VALUES (?,?,?,?,?)";
	public static final String INSERT_SURVEY_DETAILS = "INSERT INTO Volunteer (firstname, middlename, lastname, mobile, email, dob, age, gender, address, travelOutside, admitted, suffer_from, surgeries, diabOrHypertension, awareness, knowFamily, psychological_issue, training, tested_positive, emergencyContact_name, emergencyContact_relation, emergencyContact_mobile, IDproof, clinicID ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_VOLUNTEER_ID = "SELECT id FROM Volunteer ORDER BY id DESC LIMIT 1";

	public static final String RETREIVE_SURVEY_BY_ID = "SELECT firstname, middlename, lastname, mobile, email, dob, age, gender, address, travelOutside, admitted, suffer_from, surgeries, diabOrHypertension, awareness, knowFamily, psychological_issue, training, tested_positive, emergencyContact_name, emergencyContact_relation, emergencyContact_mobile, IDproof FROM Volunteer WHERE ID = ? AND ClinicID = ? ";

	public static final String INSERT_PRESENT_COMPLAINTS = "INSERT INTO PresentComplaints (complaints, duration,comments, visitID) VALUES (?,?,?,?)";

	public static final String INSERT_MEDICAL_HISTORY = "INSERT INTO MedicalHistory (diagnosis, description,patientID) VALUES (?,?,?)";

	public static final String INSERT_CURRENTMEDICATION = "INSERT INTO PrescriptionHistory (drugname, duration, comments, patientID) VALUES (?,?,?,?)";

	public static final String UPDATE_VISIT = "UPDATE Visit SET visitNumber = ?, visitDate = ?, visitTimeFrom = ?, visitTimeTo = ?, diagnosis = ?, visitNote = ?, nextVisitDays = ?, nextVisitDate = ?, systemicHistory = ?, occularHistory = ?, personalHistory = ?, complainingOf = ? ,dilationStartTime = ?, dilationEndTime = ?, dilationDuration = ? WHERE id = ?";

	public static final String RETRIEVE_VISIT_ID = "SELECT id FROM Visit WHERE patientID = ?";

	public static final String RETRIEVE_PATIENT_ID_BY_VISIT_ID = "SELECT patientID FROM Visit WHERE id = ?";

	public static final String INSERT_PRESCRIPTION_DETAIL = "INSERT INTO Prescription (drugName, dose, tradeName, numberOfDays, frequency, comment, activityStatus, visitID, pillCount) VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String RETREIVE_PRESCRIPTION_LIST = "SELECT *, (SELECT Category.name FROM Category WHERE Category.id = Prescription.categoryID) as category FROM Prescription WHERE activityStatus = ? AND visitID = ?";

	public static final String RETREIVE_PRESCRIPTION_LIST_New = "SELECT p.id, p.tradeName,p.numberOfDays,p.frequency,p.comment,p.quantity, p.categoryID, (SELECT Category.name FROM Category WHERE Category.id = p.categoryID) as category FROM Prescription as p WHERE activityStatus = ? AND visitID = ?";

	public static final String RETREIVE_ALL_PATIENT_PRESCRIPTION_LIST_New = "SELECT p.id, p.visitID, p.tradeName,p.numberOfDays,p.frequency,p.comment,p.quantity, p.categoryID, (SELECT Category.name FROM Category WHERE Category.id = p.categoryID) as category, (SELECT DATE_FORMAT(Visit.visitDate, '%d-%m-%Y') FROM Visit WHERE Visit.id = p.visitID) AS visitDate FROM Prescription as p WHERE activityStatus = ? AND visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)";

	public static final String RETREIVE_PRESCRIPTION_LIST_BY_ID = "SELECT * FROM Prescription WHERE id = ? AND activityStatus = ? ";

	public static final String UPDATE_PRESCRIPTION_DETAILS = "UPDATE Prescription SET drugName = ?, dose = ?, tradeName = ?, numberOfDays = ?, frequency = ?, comment = ?  WHERE id = ?";

	public static final String DELETE_PRESCRIPTION_DETAILS = "UPDATE Prescription SET activityStatus = ? WHERE id = ? ";

	public static final String INSERT_BILL_DETAIL_BK = "INSERT INTO Receipt (receiptNo, receiptDate, totalAmount, netAmount, billingType, advPayment, balPayment, paymentType, visitID, userID,totalDiscount,activityStatus, consultationCharges) VALUES (?,?,?,?,(SELECT PVVisitType.billingType FROM PVVisitType WHERE PVVisitType.id = (SELECT Visit.visitTypeID FROM Visit where Visit.id = ?)),?,?,?,?,?,?,?,?)";

	public static final String INSERT_BILL_DETAIL = "INSERT INTO Receipt (receiptNo, receiptDate, totalAmount, netAmount, billingType, advPayment, balPayment, paymentType, visitID, userID,totalDiscount,activityStatus, consultationCharges, productRate, productName, productID, discountType) VALUES (?,?,?,?,(SELECT PVVisitType.billingType FROM PVVisitType WHERE PVVisitType.id = (SELECT Visit.visitTypeID FROM Visit where Visit.id = ?)),?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETREIVE_BILL_LIST = "SELECT *,(SELECT CONCAT(firstName,' ',middleName,' ',lastName) FROM AppUser WHERE id = R.userID) AS clinicianName FROM Receipt AS R WHERE visitID = ?";

	public static final String RETRIEVE_EXISTING_VISIT_LIST = "SELECT id, visitNumber, nextVisitDays, advice, visitTypeID, (SELECT name FROM PVVisitType WHERE id = visitTypeID) AS visitType, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, DATE_FORMAT(dateOfDischarge, '%d-%m-%Y') as dateOfDischarge, `procedure`, admission_time, discharge_time FROM Visit WHERE id = ?";

	public static final String RETRIEVE_ALL_VISIT__FOR_PATIENTLIST = "SELECT id, visitNumber, nextVisitDays, advice, visitTypeID, (SELECT name FROM PVVisitType WHERE id = visitTypeID) AS visitType, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, DATE_FORMAT(dateOfDischarge, '%d-%m-%Y') as dateOfDischarge, `procedure`, admission_time, discharge_time FROM Visit WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ? AND isCompound = ?";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST_FOR_BILLING = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ? ORDER BY compound";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST1 = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ? AND isCompound = ? AND compound = ?";

	public static final String RETRIEVE_EXISTING_BILLING_LIST = "SELECT * FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String UPDATE_VISIT_DETAILS = "UPDATE Visit SET visitDate = ?, visitTimeFrom = ?, visitTimeTo = ?, diagnosis = ?, visitNote = ?, onExamination = ? WHERE id = ?";

	public static final String UPDATE_BILLING_DETAILS_BK = "UPDATE Receipt SET totalAmount = ?, netAmount = ?, billingType = ?, advPayment = ?, balPayment = ?, paymentType = ?,totalDiscount = ?, consultationCharges = ? WHERE visitID = ?";

	public static final String UPDATE_BILLING_DETAILS = "UPDATE Receipt SET totalAmount = ?, netAmount = ?, billingType = ?, advPayment = ?, balPayment = ?, paymentType = ?,totalDiscount = ?, consultationCharges = ?, productID = ?, productName = ?, productRate = ?, discountType = ? WHERE visitID = ?";

	public static final String UPDATE_BILLING_DETAILS1 = "UPDATE Receipt SET totalAmount = ?, totalDiscount = ?, netAmount = ?, advPayment = ?, balPayment = ?, paymentType = ? WHERE visitID = ?";

	public static final String RETRIEVE_CLINIC_LIST = "SELECT id, name FROM Clinic WHERE activityStatus = ? AND practiceID = ? ORDER BY name";

	public static final String RETRIEVE_LAST_ENTERED_VISIT_DETAILS = "SELECT * FROM Visit WHERE patientID = ? ";

	public static final String RETRIEVE_LAST_ENTERED_VISIT_DETAILS_1 = "SELECT * FROM Visit WHERE patientID = ? AND id = ?";

	public static final String RETRIEVE_VISIT_COUNT_BETWEEN_DATES = "SELECT COUNT(*) AS rowcount FROM Visit WHERE visitDate BETWEEN ? AND ?";

	public static final String RETRIEVE_ALLOWED_VISIT_BY_PracticeID = "SELECT NoOfVisit FROM PlanDetails WHERE PracticeID = ? AND status = 'Active'";

	public static final String RETRIEVE_PLAN_ID_BY_PracticeID = "SELECT id FROM PlanDetails WHERE PracticeID = ? AND status = 'Active'";

	public static final String RETRIEVE_DIAGNOSIS_BY_VISIT_ID = "SELECT diagnosis FROM Visit WHERE id = ?";

	public static final String RETRIEVE_LAST_ENTERED_PRESCRIPTION_DETAILS = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_LAST_ENTERED_BILLING_DETAILS = "SELECT * FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PATIENT_DETAILS = "SELECT * FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PATIENT_VISIT_DETAIL = "SELECT * FROM Visit WHERE patientID = ? AND careType = ? ORDER BY visitNumber DESC";

	public static final String RETRIEVE_PATIENT_VISIT_DETAIL1 = "SELECT v.id, v.visitTypeID, v.mdDoctorID, v.pcpndtStatus ,(SELECT name FROM PVVisitType WHERE id = v.visitTypeID) AS visitType, (SELECT careType FROM PVVisitType WHERE id = v.visitTypeID) AS careType, v.diagnosis, v.visitDate, v.patientID, v.visitNumber, (SELECT id FROM Receipt WHERE visitID = v.id) AS receiptID, (SELECT netAmount FROM Receipt WHERE visitID = v.id) AS netAmount,(SELECT balPayment FROM Receipt WHERE visitID = v.id) AS balPayment FROM Visit AS v WHERE v.patientID = ? and v.clinicID = ? AND v.activityStatus = ? ORDER BY v.id DESC";

	public static final String RETRIEVE_PATIENT_VISIT_DETAIL_FOR_STAFF = "SELECT * FROM Visit WHERE patientID = ? AND careType = ? ORDER BY visitNumber DESC";

	public static final String RETRIEVE_PRESCRIPTION_DETAIL = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_BILLING_DETAIL = "SELECT * FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_TOTAL_BILL = "SELECT sum(totalBill) as SUM FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_TOTAL_SERIVE_CHARGE = "SELECT sum(serviceCharge) as SUM FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_CLINICIAN_SPECIALISATION_DETAIL = "SELECT specialization FROM AppUser WHERE id = ? AND userType = ?";

	public static final String RETRIEVE_STAFF_SPECIALISATION_DETAIL = "SELECT specialization FROM AppUser WHERE id = ? AND userType = ?";

	public static final String RETRIEVE_USER_TYPE = "SELECT userType FROM AppUser WHERE id = ? ";

	public static final String RETRIEVE_USER_DETAILS = "SELECT *, (SELECT Practice.facilityDashboard FROM Practice WHERE Practice.id = AppUser.practiceID) AS facilityDashboard, (SELECT Practice.thirdPartyAPIIntegration FROM Practice WHERE Practice.id = AppUser.practiceID) AS thirdPartyAPIIntegration FROM AppUser WHERE username = ? ";

	public static final String RETRIEVE_USER_PATIENT_DETAILS = "SELECT  p.id,p.firstName,p.middleName,p.lastName,p.practiceID,(select clinicID from ClinicRegistration where patientid = p.id)AS ClinicID FROM Patient AS p WHERE username = ?";

	public static final String RETRIEVE_USER_NAME_SIGN = "SELECT firstName, lastName, signImage FROM AppUser WHERE practiceID = ? AND username = ? ";

	public static final String VERIFY_VISIT_DETAIL = "SELECT * FROM Visit WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_VISIT_NUMBER = "SELECT COALESCE(max(visitNumber), 0) + 1 AS visitNumber FROM Visit WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_VISIT_ID_FOR_VISIT_TYPE = "SELECT id FROM Visit WHERE patientID = ? AND visitType = ? AND careType = 'OPD'";

	public static final String RETRIEVE_TOTAL_SERVICE_CHARGE = "SELECT sum(serviceCharge) AS SERVICE FROM Billing WHERE visitID = ? AND activityStatus = ?";

	public static final String CALCULATE_TOTAL_NO_OF_PATIENTS = "SELECT count(*) AS COUNT FROM Patient WHERE practiceID = ?";

	public static final String CALCULATE_TOTAL_NO_OF_OPD_VISITS = "SELECT count(*) AS COUNT FROM Visit WHERE careType = ? AND patientID IN (SELECT id FROM Patient WHERE practiceID = ?)";

	public static final String CALCULATE_TOTAL_NO_OF_IPD_VISITS = "SELECT count(*) AS COUNT FROM Visit WHERE careType = ? AND patientID IN (SELECT id FROM Patient WHERE practiceID = ?)";

	public static final String CALCULATE_TOTAL_NO_OF_APPOINTMENTS = "SELECT count(*) AS COUNT FROM Appointment WHERE patientID IN (SELECT id FROM Patient WHERE practiceID = ?)";

	public static final String CALCULATE_TOTAL_NO_OF_BILL = "SELECT sum(totalBill) AS SUM FROM Billing WHERE visitID IN (SELECT id FROM Visit WHERE patientID IN (SELECT id FROM Patient WHERE practiceID = ?)) AND YEAR(billDate) = YEAR(NOW()) AND MONTH(billdate) = MONTH(NOW())";

	public static final String CALCULATE_TOTAL_NO_OF_OPTICIAN_BILL = "SELECT sum(netPayment) AS SUM FROM Eyewear WHERE optometryID IN (SELECT id FROM Optometry WHERE visitID IN (SELECT id FROM Visit WHERE patientID IN (SELECT id FROM Patient WHERE clinicID = ?)))";

	public static final String CALCULATE_TOTAL_NO_OF_OPTICIAN_VISITS = " SELECT count(*) AS COUNT FROM Optometry WHERE visitID IN (SELECT id FROM Visit WHERE patientID IN (SELECT id FROM Patient WHERE clinicID = ?))";

	public static final String UPDATE_OPD_PRESCRIPTION_DETAIL_bk = "UPDATE OphthalmologyOPD SET eyelidUpperOD = ?, eyelidUpperOS = ?, eyelidLowerOD = ?, "
			+ "eyelidLowerOS = ?, visualAcuityDistOD = ?, visualAcuityDistOS = ?,visualAcuityNearOD = ?, visualAcuityNearOS = ?, pinholeVisionDistOD = ?, pinholeVisionDistOS = ?,"
			+ "pinholeVisionNearOD = ?, pinholeVisionNearOS = ?,  bcvaDistOD = ?, bcvaDistOS = ?, bcvaNearOD = ?, bcvaNearOS = ?,"
			+ "conjunctivaOD = ?, conjunctivaOS = ?, corneaOD = ?, corneaOS = ?, anteriorChamberOD = ?, anteriorChamberOS = ?, irisOD = ?, "
			+ "irisOS = ?, lensOD = ?, lensOS = ?, discOD = ?, discOS = ?, vesselOD = ?, vesselOS = ?, maculaOD = ?, maculaOS = ?, iopOD = ?, "
			+ "iopOS = ?, sacOD = ?, sacOS = ?, biometryK1OD = ?, biometryK1OS = ?, biometryK2OD = ?, biometryK2OS = ?, biometryAxialLengthOD = ?, "
			+ "biometryAxialLengthOS = ?, biometryIOLOD = ?, biometryIOLOS = ?, posteriorComment = ?, biometryComment = ?, scleraOD = ?, scleraOS = ? WHERE visitID = ?";

	public static final String UPDATE_OPD_PRESCRIPTION_DETAIL = "UPDATE OphthalmologyOPD SET eyelidUpperOD = ?, eyelidUpperOS = ?, eyelidLowerOD = ?, "
			+ "eyelidLowerOS = ?, visualAcuityDistOD = ?, visualAcuityDistOS = ?,visualAcuityNearOD = ?, visualAcuityNearOS = ?, pinholeVisionDistOD = ?, pinholeVisionDistOS = ?,"
			+ "pinholeVisionNearOD = ?, pinholeVisionNearOS = ?,  bcvaDistOD = ?, bcvaDistOS = ?, bcvaNearOD = ?, bcvaNearOS = ?,"
			+ "conjunctivaOD = ?, conjunctivaOS = ?, corneaOD = ?, corneaOS = ?, pupilOD = ?, pupilOS = ?, anteriorChamberOD = ?, anteriorChamberOS = ?, irisOD = ?, "
			+ "irisOS = ?, lensOD = ?, lensOS = ?, discOD = ?, discOS = ?, vesselOD = ?, vesselOS = ?, maculaOD = ?, maculaOS = ?, iopOD = ?, "
			+ "iopOS = ?, sacOD = ?, sacOS = ?, biometryK1OD = ?, biometryK1OS = ?, biometryK2OD = ?, biometryK2OS = ?, biometryAxialLengthOD = ?, "
			+ "biometryAxialLengthOS = ?, biometryIOLOD = ?, biometryIOLOS = ?, posteriorComment = ?, biometryComment = ?, scleraOD = ?, scleraOS = ?, "
			+ "leftEyeHistory = ?, leftEyeDuration = ?, rightEyeHistory = ?, rightEyeDuration = ? WHERE visitID = ?";
	public static final String INSERT_OPD_PRESCRIPTION_DETAIL1_bk = "INSERT INTO OphthalmologyOPD (eyelidUpperOD, eyelidUpperOS, eyelidLowerOD, "
			+ "eyelidLowerOS, visualAcuityDistOD, visualAcuityDistOS,visualAcuityNearOD, visualAcuityNearOS, pinholeVisionDistOD, pinholeVisionDistOS,"
			+ "pinholeVisionNearOD, pinholeVisionNearOS,  bcvaDistOD, bcvaDistOS, bcvaNearOD, bcvaNearOS,"
			+ "conjunctivaOD, conjunctivaOS, corneaOD, corneaOS, anteriorChamberOD, anteriorChamberOS, irisOD, "
			+ "irisOS, lensOD, lensOS, discOD, discOS, vesselOD, vesselOS,  maculaOD, maculaOS, iopOD, "
			+ "iopOS, sacOD, sacOS, biometryK1OD, biometryK1OS, biometryK2OD, biometryK2OS, biometryAxialLengthOD, "
			+ "biometryAxialLengthOS, biometryIOLOD, biometryIOLOS, posteriorComment, biometryComment, scleraOD, scleraOS, visitID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_OPD_PRESCRIPTION_DETAIL1 = "INSERT INTO OphthalmologyOPD (eyelidUpperOD, eyelidUpperOS, eyelidLowerOD, "
			+ "eyelidLowerOS, visualAcuityDistOD, visualAcuityDistOS,visualAcuityNearOD, visualAcuityNearOS, pinholeVisionDistOD, pinholeVisionDistOS,"
			+ "pinholeVisionNearOD, pinholeVisionNearOS,  bcvaDistOD, bcvaDistOS, bcvaNearOD, bcvaNearOS,"
			+ "conjunctivaOD, conjunctivaOS, corneaOD, corneaOS, pupilOD, pupilOS, anteriorChamberOD, anteriorChamberOS, irisOD, "
			+ "irisOS, lensOD, lensOS, discOD, discOS, vesselOD, vesselOS,  maculaOD, maculaOS, iopOD, "
			+ "iopOS, sacOD, sacOS, biometryK1OD, biometryK1OS, biometryK2OD, biometryK2OS, biometryAxialLengthOD, "
			+ "biometryAxialLengthOS, biometryIOLOD, biometryIOLOS, posteriorComment, biometryComment, scleraOD, scleraOS, leftEyeHistory, leftEyeDuration, rightEyeHistory, rightEyeDuration, visitID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_DUMMY_OPTICIAN_DETAILS = "INSERT INTO Optometry (visitID) VALUES (?)";

	public static final String INSERT_DUMMY_OPTICIAN_OLD_GLASSES_DETAILS = "INSERT INTO OldGlasses (optometryID) VALUES (?)";

	public static final String RETRIEVE_OPD_VISIT_DETAILS = "SELECT * FROM OphthalmologyOPD WHERE visitID = ?";

	public static final String DELETE_PRESCRIPTION = "DELETE FROM Prescription WHERE visitID = ?";

	public static final String DELETE_VISIT = "DELETE FROM Visit WHERE id = ?";

	public static final String DELETE_OPD_VISIT = "DELETE FROM OphthalmologyOPD WHERE visitID = ?";

	public static final String DELETE_BILLING = "DELETE FROM Billing WHERE visitID = ?";

	public static final String DELETE_OPTICIAN = "DELETE FROM Optometry WHERE visitID = ?";

	public static final String DELETE_EYEWEAR = "DELETE FROM Eyewear WHERE optometryID = ?";

	public static final String DELETE_OPTICIAN_OLD_GLASSESS = "DELETE FROM OldGlasses WHERE optometryID = ?";

	public static final String OPD_PDF_RETRIEVE_PATIENT = "SELECT * FROM Patient WHERE id = ?";

	public static final String OPD_PDF_RETRIEVE_VISIT = "SELECT *, DATE_FORMAT(visitDate, '%d-%m-%Y') as formattedVisitDate, DATE_FORMAT(dateOfDischarge, '%d-%m-%Y') as dateOfDischarge, `procedure`, (SELECT name FROM PVVisitType WHERE id = visitTypeID) AS visitType, DATE_FORMAT(dateOfDischarge, '%d-%m-%Y') as dateOfDischarge, `procedure` FROM Visit WHERE id = ?";

	public static final String OPD_PDF_RETRIEVE_VISIT_TYPE_NAME = "SELECT name FROM PVVisitType WHERE id = ?";

	public static final String OPD_PDF_RETRIEVE_VITALSIGNS = "SELECT weight,systolicBP,diastolicBP,pulse FROM VitalSigns WHERE visitID = ?";

	public static final String OPD_PDF_RETRIEVE_SYMPTOM = "SELECT symptom,value FROM symptomcheck WHERE visitID = ?";

	public static final String OPD_PDF_RETRIEVE_OPD_VISIT = "SELECT * FROM OphthalmologyOPD WHERE visitID = ?";

	public static final String OPTICIAN_PDF_RETRIEVE_OLD_OPTICIAN = "SELECT * FROM OldGlasses WHERE optometryID = ? AND (sphNearOD <> '--' OR sphNearOS <> '--' OR sphDistOD <> '--' OR sphDistOS <> '--' OR cylNearOD <> '--' OR cylNearOS <> '--' OR cylDistOD <> '--' OR cylDistOS <> '--' OR axisNearOD <> '--' OR axisNearOS <> '--' OR axisDistOD <> '--' OR axisDistOS <> '--' OR vnNearOD <> '--' OR vnNearOS <> '--' OR vnDistOD <> '--' OR vnDistOS <> '--')";

	public static final String OPTICIAN_PDF_RETRIEVE_NEW_OPTICIAN = "SELECT * FROM Optometry WHERE id = ?";

	public static final String OPTICIAN_PDF_RETRIEVE_EYEWEAR = "SELECT * FROM Eyewear WHERE optometryID = ?";

	public static final String OPD_PDF_RETRIEVE_PRESCRIPTION = "SELECT * FROM Prescription WHERE visitID = ? AND activityStatus = ?";

	public static final String OPD_PDF_RETRIEVE_PRESCRIPTIONPDF = "SELECT p.id, p.tradeName, p.quantity, p.comment, p.frequency ,p.numberOfDays, (SELECT name FROM Category WHERE id = p.categoryID) as category, (SELECT Product.drugName FROM Product WHERE Product.tradeName = p.tradeName AND Product.clinicID = (SELECT Visit.clinicID FROM Visit WHERE Visit.id = p.visitID) AND Product.activityStatus = 'Active') AS drugName  FROM Prescription AS p WHERE p.visitID = ? AND p.activityStatus = ?";

	public static final String OPD_PDF_RETRIEVE_PRESCRIPTIONPDF_investigation = "SELECT investigation FROM PrescribedInvestigations WHERE visitID = ? ";

	public static final String RETRIEVE_LAST_ENTERED_PATIENT_ID = "SELECT id FROM Patient ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_LAST_OPTICIAN_LIST = "SELECT * FROM Optometry WHERE visitID = ?";

	public static final String RETRIEVE_LAST_OPD_VISIT_ID1 = "SELECT id FROM Visit WHERE patientID = ?";

	public static final String RETRIEVE_LAST_OPTICIAN_ID = "SELECT id FROM Optometry WHERE visitID = ?";

	public static final String RETRIEVE_EYEWEAR_DETAILS = "SELECT * FROM Eyewear WHERE optometryID = ?";

	public static final String RETRIEVE_OPTICIAN_DETAILS = "SELECT * FROM Optometry WHERE id = ?";

	public static final String RETRIEVE_OPTICIAN_OLD_GLASSES_DETAILS = "SELECT * FROM OldGlasses WHERE optometryID = ?";

	public static final String UPDATE_OPTICIAN_DETAILS = "UPDATE Optometry SET sphNearOD = ?, sphNearOS = ?, sphDistOD = ?, sphDistOS = ?, cylNearOD = ?, cylNearOS = ?, cylDistOD = ?, cylDistOS = ?, axisNearOD = ?, axisNearOS = ?, axisDistOD = ?, axisDistOS = ?, vnNearOD = ?, vnNearOS = ?, vnDistOD = ?, vnDistOS = ?, comments = ?, spectacleComments = ? WHERE id = ?";

	public static final String UPDATE_OPTICIAN_OLD_GLASS_DETAILS = "UPDATE OldGlasses SET sphNearOD = ?, sphNearOS = ?, sphDistOD = ?, sphDistOS = ?, cylNearOD = ?, cylNearOS = ?, cylDistOD = ?, cylDistOS = ?, axisNearOD = ?, axisNearOS = ?, axisDistOD = ?, axisDistOS = ?, vnNearOD = ?, vnNearOS = ?, vnDistOD = ?, vnDistOS = ? WHERE optometryID = ?";

	public static final String INSERT_EYEWEAR_DETAILS = "INSERT INTO Eyewear (tint, glass, material, clinic, frame, frameCharges, glassCharges, discount, netPayment, advance, balance, balPaid, balPaidDate, optometryID, discountType) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_OLD_GLASSES_DETAILS = "SELECT * FROM OldGlasses WHERE optometryID = ?";

	public static final String INSERT_DUMMY_MEDICAL_DOCUMENT = "INSERT INTO MedicalDocuments (visitID) VALUES (?)";

	public static final String INSERT_MEDICAL_CERTIFICATE = "UPDATE MedicalDocuments SET medicalCertificate = ? WHERE visitID = ?";

	public static final String DELETE_DUMMY_MEDICAL_DOCUMENT = "DELETE FROM MedicalDocuments WHERE visitID = ?";

	public static final String RETRIEVE_MEDICAL_CERTIFICATE = "SELECT * FROM MedicalDocuments WHERE visitID = ?";

	public static final String RETRIEVE_REFERRAL_LETTER = "SELECT * FROM MedicalDocuments WHERE visitID = ?";

	public static final String INSERT_REFERRAL_LETTER = "INSERT INTO MedicalDocuments (referralLetter, visitID) VALUES (?,?)";

	public static final String UPDATE_REFERRAL_LETTER = "UPDATE MedicalDocuments SET referralLetter = ? WHERE visitID = ?";

	public static final String INSERT_IPD_VISIT_bk = "INSERT INTO OphthalmologyIPD (eyelidUpperOD , eyelidUpperOS , eyelidLowerOD , "
			+ "eyelidLowerOS , visualAcuityDistOD , visualAcuityDistOS ,visualAcuityNearOD , visualAcuityNearOS , pinholeVisionDistOD , pinholeVisionDistOS ,"
			+ "pinholeVisionNearOD , pinholeVisionNearOS ,  bcvaDistOD , bcvaDistOS , bcvaNearOD , bcvaNearOS ,"
			+ "conjunctivaOD , conjunctivaOS , corneaOD , corneaOS , anteriorChamberOD , anteriorChamberOS , irisOD , "
			+ "irisOS , lensOD , lensOS , discOD , discOS , vesselOD , vesselOS , maculaOD , maculaOS , iopOD , "
			+ "iopOS , sacOD , sacOS , biometryK1OD , biometryK1OS , biometryK2OD , biometryK2OS , biometryAxialLengthOD , "
			+ "biometryAxialLengthOS , biometryIOLOD , biometryIOLOS ,dateOfSurgery, historyOf, allergicTo, visitID) "
			+ "VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	public static final String INSERT_IPD_VISIT = "INSERT INTO OphthalmologyIPD (eyelidUpperOD , eyelidUpperOS , eyelidLowerOD , "
			+ "eyelidLowerOS , visualAcuityDistOD , visualAcuityDistOS ,visualAcuityNearOD , visualAcuityNearOS , pinholeVisionDistOD , pinholeVisionDistOS ,"
			+ "pinholeVisionNearOD , pinholeVisionNearOS ,  bcvaDistOD , bcvaDistOS , bcvaNearOD , bcvaNearOS ,"
			+ "conjunctivaOD , conjunctivaOS , corneaOD , corneaOS , pupilOD , pupilOS , anteriorChamberOD , anteriorChamberOS , irisOD , "
			+ "irisOS , lensOD , lensOS , discOD , discOS , vesselOD , vesselOS , maculaOD , maculaOS , iopOD , "
			+ "iopOS , sacOD , sacOS , biometryK1OD , biometryK1OS , biometryK2OD , biometryK2OS , biometryAxialLengthOD , "
			+ "biometryAxialLengthOS , biometryIOLOD , biometryIOLOS ,dateOfSurgery, historyOf, allergicTo, visitID) "
			+ "VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	public static final String INSERT_OT_NOTES = "INSERT INTO OTNotes (notes, visitID) VALUES (?,?) ";

	public static final String INSERT_IPD_CONTINUATION_SHEET = "INSERT INTO IPDContinuationSheet (contDate, description, treatment, visitID) VALUES (?,?,?,?)";

	public static final String INSERT_IPD_COMPLAINTS = "INSERT INTO OpthalmologyComplaints (complaintOD, complaintOS, visitID) VALUES (?,?,?)";

	public static final String INSERT_OE = "INSERT INTO VitalSigns (pulse, systolicBP, diastolicBP, respiratorySystem, cardioVascularSystem, visitID) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_INESTIGATION = "INSERT INTO LabInvestigations (haemogobinPercent, wbcCount, bt, ct, bloodSugarLevelFasting, bloodSugarLevelPP, urineRoutineAndMicroscopy, visitID) VALUES (?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_IPD_VISIT = "SELECT * FROM OphthalmologyIPD WHERE visitID = ?";

	public static final String RETRIEVE_OT_NOTES = "SELECT * FROM OTNotes WHERE visitID = ?";

	public static final String RETRIEVE_INVESTOGATION = "SELECT * FROM LabInvestigations WHERE visitID = ?";

	public static final String RETRIEVE_OE = "SELECT * FROM VitalSigns WHERE visitID = ?";

	public static final String RETRIEVE_IPD_CONTINUATION_SHEET = "SELECT *, DATE_FORMAT(contDate, '%d-%m-%Y') as formattedContDate FROM IPDContinuationSheet WHERE visitID = ?";

	public static final String RETRIEVE_IPD_COMPLAINTS = "SELECT * FROM OpthalmologyComplaints WHERE visitID = ?";

	public static final String DELETE_IPD_COMPLAINTS = "DELETE FROM OpthalmologyComplaints WHERE id = ?";

	public static final String DELETE_IPD_CONTINUATION_SHEET = "DELETE FROM IPDContinuationSheet WHERE id = ?";

	public static final String RETRIEVE_VISIT_ID_BY_VISIT_NUMBER = "SELECT id FROM Visit WHERE visitNumber = ?";

	public static final String UPDATE_IPD_VISIT = "UPDATE Visit SET diagnosis = ?, visitDate = ?, dateOfDischarge = ?, `procedure` = ?, admission_time = ?, discharge_time = ? WHERE id = ?";

	public static final String RETRIEVE_OPHTHALMALOGY_IPD = "SELECT * FROM OphthalmologyIPD WHERE visitID = ?";

	public static final String RETRIEVE_VITAL_SIGNS = "SELECT * FROM VitalSigns WHERE visitID = ?";

	public static final String RETRIEVE_CARE_TYPE = "SELECT careType FROM PVVisitType WHERE id = ?";

	public static final String UPDATE_IPD_VISIT_DETAILS_bk = "UPDATE OphthalmologyIPD SET eyelidUpperOD = ?, eyelidUpperOS =? , eyelidLowerOD = ? , "
			+ "eyelidLowerOS= ? , visualAcuityDistOD= ? , visualAcuityDistOS=? ,visualAcuityNearOD =?, visualAcuityNearOS=? , pinholeVisionDistOD=? , pinholeVisionDistOS=? ,"
			+ "pinholeVisionNearOD =? , pinholeVisionNearOS=? ,  bcvaDistOD=? , bcvaDistOS=? , bcvaNearOD=? , bcvaNearOS=? ,"
			+ "conjunctivaOD=? , conjunctivaOS=? , corneaOD=? , corneaOS=? , anteriorChamberOD=? , anteriorChamberOS=? , irisOD=? , "
			+ "irisOS =?, lensOD =?, lensOS=? , discOD=? , discOS=? , vesselOD=? , vesselOS=? , maculaOD=? , maculaOS=? , iopOD=? , "
			+ "iopOS =?, sacOD =?, sacOS =?, biometryK1OD =?, biometryK1OS =?, biometryK2OD =?, biometryK2OS=? , biometryAxialLengthOD=? , "
			+ "biometryAxialLengthOS =?, biometryIOLOD =?, biometryIOLOS =?,dateOfSurgery =?, historyOf =?, allergicTo=? WHERE visitID =?";

	public static final String UPDATE_IPD_VISIT_DETAILS = "UPDATE OphthalmologyIPD SET eyelidUpperOD = ?, eyelidUpperOS =? , eyelidLowerOD = ? , "
			+ "eyelidLowerOS= ? , visualAcuityDistOD= ? , visualAcuityDistOS=? ,visualAcuityNearOD =?, visualAcuityNearOS=? , pinholeVisionDistOD=? , pinholeVisionDistOS=? ,"
			+ "pinholeVisionNearOD =? , pinholeVisionNearOS=? ,  bcvaDistOD=? , bcvaDistOS=? , bcvaNearOD=? , bcvaNearOS=? ,"
			+ "conjunctivaOD=? , conjunctivaOS=? , corneaOD=? , corneaOS=? , pupilOD=? , pupilOS=? , anteriorChamberOD=? , anteriorChamberOS=? , irisOD=? , "
			+ "irisOS =?, lensOD =?, lensOS=? , discOD=? , discOS=? , vesselOD=? , vesselOS=? , maculaOD=? , maculaOS=? , iopOD=? , "
			+ "iopOS =?, sacOD =?, sacOS =?, biometryK1OD =?, biometryK1OS =?, biometryK2OD =?, biometryK2OS=? , biometryAxialLengthOD=? , "
			+ "biometryAxialLengthOS =?, biometryIOLOD =?, biometryIOLOS =?,dateOfSurgery =?, historyOf =?, allergicTo=? WHERE visitID =?";

	public static final String UPDATE_INESTIGATION = "UPDATE LabInvestigations SET haemogobinPercent = ?, wbcCount = ?, bt = ?,"
			+ " ct = ?,  bloodSugarLevelFasting = ?, bloodSugarLevelPP = ?, urineRoutineAndMicroscopy = ? WHERE visitID = ?";

	public static final String UPDATE_OE = "UPDATE VitalSigns SET pulse = ?, systolicBP = ?, diastolicBP = ?, respiratorySystem = ?, "
			+ "cardioVascularSystem = ? WHERE visitID = ?";

	public static final String UPDATE_OT_NOTES = "UPDATE OTNotes SET notes = ? WHERE visitID = ? ";

	public static final String RETRIEVE_CLINIC_NAME = "SELECT * FROM Clinic WHERE id = ?";

	public static final String INSERT_CONSENT_DOCUMENT = "INSERT INTO Consent (consentDocument, visitID) VALUES (?,?)";

	public static final String RETRIEVE_CONSENT = "SELECT consentDocument FROM Consent WHERE visitID = ?";

	public static final String RETRIEVE_CONSENT1 = "SELECT * FROM Consent WHERE visitID = ?";

	public static final String UPDATE_DOCUMENT = "UPDATE Consent SET consentDocument = ? WHERE visitID = ?";

	public static final String SEARCH_PATIENT_BY_PATIENT_NAME = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, (SELECT regNumber FROM ClinicRegistration WHERE patientID = p.id and clinicID = ?) AS regNo FROM Patient AS p WHERE (CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) LIKE ? AND p.practiceID = ? AND p.activityStatus = ?)";

	public static final String SEARCH_PATIENT_BY_REGISTRATION_NUMBER = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, c.regNumber AS regNo FROM Patient AS p, ClinicRegistration AS c WHERE p.id = c.patientID AND c.regNumber LIKE ? AND p.activityStatus = ? AND p.practiceID = ?";

	public static final String SEARCH_PATIENT_BY_DATE_RANGE = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, (SELECT regNumber FROM ClinicRegistration WHERE patientID = p.id and clinicID = ?) AS regNo FROM Patient AS p WHERE id in (SELECT DISTINCT(patientID) FROM Visit WHERE visitDate between ? and ?) and p.activityStatus = ?";

	public static final String SEARCH_PATIENT_BY_START_DATE = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, (SELECT regNumber FROM ClinicRegistration WHERE patientID = p.id and clinicID = ?) AS regNo FROM Patient AS p WHERE id IN (SELECT DISTINCT(patientID) FROM Visit WHERE visitDate = ? AND clinicID = ?) and p.activityStatus = ?";

	public static final String SEARCH_PATIENT_BY_DATE = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, (SELECT regNumber FROM ClinicRegistration WHERE patientID = p.id and clinicID = ?) AS regNo FROM Patient AS p WHERE id =? and p.activityStatus = ?";

	public static final String SEARCH_PATIENT_BY_MOBILE_NO = "SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, c.regNumber AS regNo FROM Patient AS p , ClinicRegistration AS c WHERE p.id = c.patientID AND p.mobile = ? AND p.practiceID = ? AND p.activityStatus = ?";

	public static final String RETRIEVE_PATIENT_NAME = "SELECT firstName, middleName, lastName FROM Patient WHERE id = ?";

	public static final String RETRIEVE_APPOINTMENT_NUMBER = "SELECT apptNumber FROM Appointment WHERE apptDate = ?";

	public static final String RETRIEVE_APPOINTMENT_NUMBER_BY_DATE_AND_TIME = "SELECT apptNumber, HOUR(TIMEDIFF((SELECT Calendar.clinicStart FROM Calendar WHERE Calendar.clinicID = Appointment.clinicID), apptTimeFrom)) as hourDiff FROM Appointment WHERE apptDate = ? AND apptTimeFrom = ? AND clinicID = ?";

	public static final String INSERT_APPOINTMENT = "INSERT INTO Appointment (apptDate, apptNumber, apptTimeFrom, apptTimeTo, status, patientID, clinicID, visitTypeID, clinicianID, walkin, nextApptTaken, roomTypeID, apptEndDate, comments) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_ENTERED_APPOINTMENT = "SELECT id, apptDate, apptNumber, apptTimeFrom, apptTimeTo, status, patientID FROM Appointment WHERE patientID = ? ";

	public static final String RETRIEVE_APPOINTMENT_LIST = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate ) = MONTH(NOW()) AND DAY(apptDate ) = DAY(NOW()) AND status <> ? AND clinicID = ? ORDER BY apptTimeFrom"; // New added 

	public static final String RETRIEVE_APPOINTMENT_LIST_BY_CLINICIAN_ID = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate ) = MONTH(NOW()) AND DAY(apptDate ) = DAY(NOW()) AND status <> ? AND clinicID = ? AND clinicianID = ? ORDER BY apptTimeFrom"; // New added 

	public static final String RETRIEVE_APPOINTMENT_WEEK_LIST = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE WEEKOFYEAR(apptDate) = WEEKOFYEAR(NOW()) AND status <> ? AND clinicID = ? ORDER BY apptTimeFrom"; // New added

	public static final String RETRIEVE_APPOINTMENT_WEEK_LIST_BY_CLINICIAN_ID = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE WEEKOFYEAR(apptDate) = WEEKOFYEAR(NOW()) AND status <> ? AND clinicID = ? AND clinicianID = ? ORDER BY apptTimeFrom"; // New added

	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate )=MONTH(NOW()) AND status <> ? AND clinicID = ? ORDER BY apptTimeFrom"; // New added

	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom, '%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo, '%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate) = YEAR(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND MONTH(apptDate) = MONTH(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND status <> ? AND clinicID = ? AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = ?) ORDER BY apptTimeFrom";
	
	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_WO_CARETYPE = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom, '%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo, '%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate) = YEAR(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND MONTH(apptDate) = MONTH(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND status <> ? AND clinicID = ? ORDER BY apptTimeFrom";
	
	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST_BY_CLINICIAN_ID = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate )=MONTH(NOW()) AND status <> ? AND clinicID = ? AND clinicianID = ? ORDER BY apptTimeFrom"; // New added

	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_AND_CLINICIAN_ID = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate) = YEAR(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND MONTH(apptDate) = MONTH(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND status <> ? AND clinicID = ? AND clinicianID = ? ORDER BY apptTimeFrom";
	
	public static final String RETRIEVE_APPOINTMENT_MONTH_LIST_BY_COUNT_AND_CLINICIAN_ID_AND_CARE_TYPE = "SELECT id, comments, visitTypeID, apptDate, TIME_FORMAT(apptTimeFrom, '%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo, '%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate) = YEAR(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND MONTH(apptDate) = MONTH(DATE_ADD(NOW(), INTERVAL ? MONTH)) AND status <> ? AND clinicID = ? AND clinicianID = ? AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = ?) ORDER BY apptTimeFrom";
	
	public static final String CANCEL_APPOINTMENT = "UPDATE Appointment SET status = ? WHERE id = ?";

	public static final String CONFIRM_APPOINTMENT = "UPDATE Appointment SET status = ? WHERE id = ?";

	public static final String RETRIEVE_APPOINTMENT_VALUES = "SELECT id, apptTimeFrom, apptTimeTo FROM Appointment WHERE id = ? ";

	public static final String COMPLETE_APPOINTMENT = "UPDATE Appointment SET status = ? WHERE id = ?";

	public static final String UPDATE_NEXT_VISIT_DAYS = "UPDATE Visit SET nextVisitDays = ? WHERE id = ?";

	public static final String RETRIEVE_LAST_ENTERED_CLINIC_ID = "SELECT id FROM Clinic";

	public static final String INSERT_CALENDAR_DETAILS = "INSERT INTO Calendar (clinicStart, clinicEnd, breakStart, breakEnd, appointmentDuration, clinicID) VALUES (?,?,?,?,?,?)";

	public static final String RETRIEVE_CALENDAR_BY_CLINIC_ID = "SELECT * FROM Calendar WHERE clinicID = ?";

	public static final String UPDATE_CALENDAR_DETAILS = "UPDATE Calendar SET clinicStart = ?, clinicEnd = ?, breakStart = ?, breakEnd = ?, appointmentDuration = ?, clinicID = ? WHERE id = ?";

	public static final String UPDATE_IPD_CONTINUATION_SHEET = "UPDATE IPDContinuationSheet SET contDate = ?, description = ?, treatment = ? WHERE visitID = ?";

	public static final String UPDATE_CLINIC_CONFIGURATION_DETAILS = "UPDATE ClinicConfiguration SET tagline = ?, website = ?, logo = ?, consentDocumentPath = ?, pageSize = ?,letterHeadImage = ?, sessionTimeOut = ?, invalidAttempts = ?, emailTo = ?, emailFrom = ?, emailFromPass = ?, currency = ? , clinicID = ? WHERE id = ?";

	public static final String INSERT_PRACTICE_CONFIGURATION_DETAILS = "INSERT INTO PracticeConfiguration (tagline, website , logo, consentDocumentPath, pageSize,letterHeadImage, sessionTimeOut, invalidAttempts, currency1 , practiceID, patientForm, currency2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_CLINIC_CONFIGURATION_DETAILS = "SELECT * FROM ClinicConfiguration WHERE clinicID = ?";

	public static final String RETRIEVE_APPOINTMENT_DURATION = "SELECT visitDuration FROM PVVisitType WHERE id = ?";

	public static final String VERIFY_CLINIC_EXISTS = "SELECT name FROM Clinic WHERE name = ?";

	public static final String VERIFY_CLINIC_EXISTS1 = "SELECT name FROM Clinic WHERE name = ? AND id <> ?";

	public static final String RETRIEVE_CLINIC_TYPE_LIST = "SELECT * FROM PVClinicType ORDER BY id ASC";

	public static final String INSERT_CLINIC_TYPE = "INSERT INTO PVClinicType (clinicType) VALUES (?)";

	public static final String RETRIEVE_DIAGNOSIS_LIST = "SELECT * FROM PVDiagnosis";

	public static final String INSERT_DIAGNOSIS = "INSERT INTO PVDiagnosis (diagnosis, icd10Code) VALUE (?,?)";

	public static final String RETRIEVE_REFERRING_DOCTOR_LIST = "SELECT * FROM PVReferringDoctors WHERE practiceID = ? ORDER BY id ASC ";

	public static final String INSERT_REFERRING_DOCTOR = "INSERT INTO PVReferringDoctors (doctorName, specialization, clinicName, clinicAddress, phone, email,practiceID) VALUES (?,?,?,?,?,?,?)";

	public static final String RETRIEVE_FREQUENCY_LIST = "SELECT id, frequencyValues, count, sortOrder FROM PVFrequency WHERE practiceID = ? AND activityStatus = ?";

	public static final String INSERT_FREQUENCY = "INSERT INTO PVFrequency (frequencyValues, count, sortOrder, practiceID, activityStatus) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_BILLING_LIST = "SELECT * FROM PVBillingItems";

	public static final String INSERT_BILLING = "INSERT INTO PVBillingItems (chargeType, charge, clinicID) VALUES (?,?,?)";

	public static final String RETRIEVE_CLINIC_NAME_BY_ID = "SELECT name FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_PRESCRIPTION_LIST = "SELECT * FROM PVPrescription";

	public static final String INSERT_PRESCRIPTION = "INSERT INTO PVPrescription (tradeName, drugName, dose, icd10Code) VALUES (?,?,?,?)";

	public static final String INSERT_LAB_REPORT = "INSERT INTO Reports (report, visitID) VALUES (?,?)";

	public static final String RETRIEVE_LAB_REPORT_FILE_NAME = "SELECT report FROM Reports WHERE visitID = ?";

	public static final String RETRIEVE_SESSION_TIMEOUT = "SELECT sessionTimeOut FROM Practice WHERE id = ?";

	public static final String RETRIEVE_INVALID_ATTEMPTS = "SELECT invalidAttempts FROM Practice WHERE id = ?";

	public static final String RETRIEVE_EMAIL_TO = "SELECT emailTo FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_EMAIL_FROM = "SELECT emailFrom FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_EMAIL_FROM_PASS = "SELECT emailFromPass FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_REVIEW_FORM_URL = "SELECT reviewForm FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_CURRENCY = "SELECT currency1 FROM Practice WHERE id = ?";

	public static final String RETRIEVE_CURRENCY1 = "SELECT currency2 FROM Practice WHERE id = ?";

	public static final String RETRIEVE_LETTER_HEAD_IMAGE = "SELECT letterHeadImage FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_CLINIC_SUFFIX = "SELECT suffix FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_PAGE_SIZE = "SELECT pageSize FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_CONSENT_DOCUMENT_PATH = "SELECT consentDocumentPath FROM Practice WHERE id = ?";

	public static final String RETRIEVE_LOGO = "SELECT logo FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_WEBSITE = "SELECT website FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_TAGLINE = "SELECT tagline FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_DIAGNOSES_LIST = "SELECT diagnosis FROM PVDiagnosis ORDER BY id ASC";

	public static final String RETRIEVE_TRADE_NAME_LIST = "SELECT tradeName FROM Product ORDER BY id ASC";

	public static final String RETRIEVE_TRADE_NAME_LIST_BY_TRADE_NAME = "SELECT tradeName FROM Product WHERE tradeName LIKE ? AND activityStatus = ? ORDER BY tradeName ASC";

	public static final String RETRIEVE_CATEGORY_LIST = "SELECT id, name FROM Category ORDER BY id ASC";

	public static final String RETRIEVE_FRQUENCY_LIST = "SELECT frequencyValues FROM PVFrequency WHERE practiceID = ? AND activitySTatus='Active' ORDER BY sortOrder ASC";

	public static final String RETRIEVE_DRUGNAME_DOSE = "SELECT drugName, dose FROM PVPrescription WHERE tradeName = ? ORDER BY id ASC";

	public static final String RETRIEVE_CHARGE_TYPE_LIST = "SELECT chargeType FROM PVBillingItems ORDER BY id ASC";

	public static final String RETRIEVE_CHARGE_BY_CHARGE_TYPE = "SELECT charge FROM PVBillingItems WHERE chargeType = ?";

	public static final String RETRIEVE_CONFIGURATION_CLINIC_TYPE_LIST_BY_ID = "SELECT * FROM PVClinicType WHERE id = ? ORDER BY id ASC";

	public static final String DELETE_CONFIGURATION_CLINIC_TYPE = "DELETE FROM PVClinicType WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_CLINIC_TYPE = "UPDATE PVClinicType SET clinicType = ? WHERE id = ?";

	public static final String RETRIEVE_CONFIGURATION_BILLING_LIST_BY_ID = "SELECT * FROM PVBillingItems WHERE id = ?";

	public static final String DELETE_CONFIGURATION_BILLING = "DELETE FROM PVBillingItems WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_BILLING = "UPDATE PVBillingItems SET chargeType = ?, charge = ?, clinicID = ? WHERE id = ?";

	public static final String RETRIEVE_CONFIGURATION_DIAGNOSIS_LIST_BY_ID = "SELECT * FROM PVDiagnosis WHERE id = ?";

	public static final String DELETE_CONFIGURATION_DIAGNOSIS = "DELETE FROM PVDiagnosis WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_DIAGNOSIS = "UPDATE PVDiagnosis SET diagnosis = ?, icd10Code = ? WHERE id = ?";

	public static final String RETRIEVE_CONFIGURATION_FREQUENCY_LIST_BY_ID = "SELECT * FROM PVFrequency WHERE id = ?";

	public static final String DELETE_CONFIGURATION_FREQUENCY = "UPDATE PVFrequency SET activityStatus = ? WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_FREQUENCY = "UPDATE PVFrequency SET frequencyValues = ?, count = ?, sortOrder = ? WHERE id = ?";

	public static final String RETRIEVE_CONFIGURATION_PRESCRIPTION_LIST_BY_ID = "SELECT * FROM PVPrescription WHERE id = ?";

	public static final String DELETE_CONFIGURATION_PRESCRIPTION = "DELETE FROM PVPrescription WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_PRESCRIPTION = "UPDATE PVPrescription SET tradeName = ?, drugName = ?, dose = ?, icd10Code = ? WHERE id = ?";

	public static final String RETRIEVE_CONFIGURATION_REF_DOC_LIST_BY_ID = "SELECT * FROM PVReferringDoctors WHERE id = ?";

	public static final String DELETE_CONFIGURATION_REF_DOC = "DELETE FROM PVReferringDoctors WHERE id = ?";

	public static final String UPDATE_CONFIGURATION_REF_DOC = "UPDATE PVReferringDoctors SET doctorName = ?, specialization = ?, clinicName = ?, clinicAddress = ?, phone = ?, email = ? WHERE id = ?";

	public static final String CHECK_PRESCRIPTION_TABLE = "SELECT tradeName, drugName, dose FROM Prescription";

	public static final String CHECK_PVPRESCRIPTION_TABLE = "SELECT tradeName FROM PVPrescription WHERE tradeName = ?";

	public static final String INSERT_INTO_PVPRESCRIPTION_TABLE = "INSERT INTO PVPrescription (tradeName, drugName, dose) VALUES (?,?,?)";

	public static final String CHECK_DIAGNOSIS_FROM_VISIT = "SELECT diagnosis FROM Visit WHERE diagnosis <> '-1' AND diagnosis <> ''";

	public static final String CHECK_DIAGNOSIS_FROM_PVDIAGNOSES = "SELECT diagnosis FROM PVDiagnosis WHERE diagnosis = ?";

	public static final String INSERT_DIAGNOSIS_INTO_PVDIAGNOSES = "INSERT INTO PVDiagnosis (diagnosis, isCancerType) VALUES (?,?)";

	public static final String CHECK_DIAGNOSIS_FROM_PERSONAL_HISTORY = "SELECT diagnosis FROM PersonalHistory WHERE diagnosis <> '-1' AND diagnosis <> '' AND diagnosis <> 'undefined'";

	public static final String RETRIEVE_OPD_JSP_LIST = "SELECT formName, jspPageName FROM FormMapping WHERE careType = ? ORDER BY id ASC";

	public static final String RETRIEVE_IPD_JSP_LIST = "SELECT formName, jspPageName FROM FormMapping WHERE careType = ? ORDER BY id ASC";

	public static final String RETRIEVE_CLINIC_TIME = "SELECT * FROM Calendar WHERE clinicID = ?";

	public static final String CALCULATE_NO_OF_PATIENTS = "SELECT count(*) AS COUNT FROM Patient";

	public static final String CALCULATE_NO_OF_OPD_VISITS = "SELECT count(*) AS COUNT FROM Visit WHERE careType = ?";

	public static final String CALCULATE_NO_OF_IPD_VISITS = "SELECT count(*) AS COUNT FROM Visit WHERE careType = ?";

	public static final String CALCULATE_NO_OF_ACTIVE_USER = "SELECT count(*) AS COUNT FROM AppUser where activityStatus = ?";

	public static final String CALCULATE_NO_OF_ADMIN_USER = "SELECT count(*) AS COUNT FROM AppUser where userType = ?";

	public static final String CALCULATE_NO_OF_CLINICIANS = "SELECT count(*) AS COUNT FROM AppUser where userType = ?";

	public static final String CALCULATE_NO_OF_STAFFS = "SELECT count(*) AS COUNT FROM AppUser where userType = ?";

	public static final String CALCULATE_NO_OF_CLINICS = "SELECT count(*) AS COUNT FROM Clinic";

	public static final String CALCULATE_NO_OF_CLINIC_TYPES = "SELECT count(*) AS COUNT FROM PVClinicType";

	public static final String CALCULATE_NO_OF_DRUGS = "SELECT count(*) AS COUNT FROM PVPrescription";

	public static final String CALCULATE_NO_OF_DIAGNOSES = "SELECT count(*) AS COUNT FROM PVDiagnosis";

	public static final String CALCULATE_NO_OF_REFERRING_DOCTORS = "SELECT count(*) AS COUNT FROM PVReferringDoctors";

	public static final String RETRIEVE_APPOINTMENT_LIST_BY_PATIENT_ID = "SELECT id, apptDate, apptTimeFrom, apptTimeTo, status, patientID FROM Appointment WHERE patientID = ? AND status <> ?";

	public static final String RETRIEVE_INVALIDATE_LOGIN_ATTEMPT = "SELECT invalidAttempts FROM ClinicConfiguration WHERE clinicID = (SELECT clinicID FROM AppUser WHERE username = ?)";

	public static final String RETRIEVE_VISIT_FOR_MEDICAL_HISTORY_REPORT = "SELECT * FROM Visit WHERE patientID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PRESCRIPTION_FOR_MEDICAL_HISTORY_REPORT = "SELECT * FROM Prescription WHERE visitID IN ( SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ?";

	public static final String RETRIEVE_BILLING_FOR_MEDICAL_HISTORY_REPORT = "SELECT * FROM Billing WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ?";

	public static final String RETRIEVE_TOTAL_BILL_FOR_MEDICAL_HISTORY_REPORT = "SELECT sum(totalBill) as SUM FROM Billing WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ?";

	public static final String RETRIEVE_LOGIN_ATTEMPT_DATE = "SELECT dateAndTime FROM LoginAttempt WHERE userID = ? AND activityStatus = ?";

	public static final String RETRIEVE_P_LOGIN_ATTEMPT_DATE = "SELECT dateAndTime FROM PLoginAttempt WHERE patientID = ? AND activityStatus = ?";

	public static final String UPDATE_LOGIN_ATTEMPT_STATUS = "UPDATE LoginAttempt SET activityStatus = ?  WHERE userID = ? ";

	public static final String UPDATE_P_LOGIN_ATTEMPT_STATUS = "UPDATE PLoginAttempt SET activityStatus = ?  WHERE patientID = ? ";

	public static final String VERIFY_USER_PIN = "SELECT pin from AppUser where pin = ? AND id = ?";

	public static final String VERIFY_USER_OLD_PASSWORD = "SELECT password from AppUser where password = ? AND id = ?";

	public static final String UPDATE_PASSWORD = "UPDATE AppUser set password = ? WHERE id = ?";

	public static final String UPDATE_PIN = "UPDATE AppUser set pin = ? WHERE id = ?";

	public static final String UPDATE_PASSWORD_AND_PIN = "UPDATE AppUser set pin = ?, password = ? WHERE id = ?";

	public static final String INSERT_PASSWORD_HISTORY = "INSERT INTO PasswordHistory (password, userID) VALUES (?,?)";

	public static final String VERIFY_PASSWORD_HISTORY = "SELECT password FROM PasswordHistory WHERE userID = ? AND password = ? ORDER BY id DESC LIMIT 5";

	public static final String RETRIEVE_CLINIC_LOGO_FILE_NAME = "SELECT logo FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_USER_SIGNATURE_FILE_NAME = "SELECT signImage FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_USER_PROFILE_PIC_FILE_NAME = "SELECT profilePic FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_PATIENT_PROFILE_PIC_FILE_NAME = "SELECT profilePic FROM Patient WHERE id = ?";

	public static final String SEARCH_USER_BY_USER_NAME = "SELECT id, username, userType, activityStatus, firstName, middleName, lastName FROM AppUser WHERE CONCAT(firstName,' ',middleName,' ',lastName) LIKE ? AND practiceID = ? ORDER BY activityStatus ASC";

	public static final String RETRIEVE_LAST_LOGIN_DATE_TIME = "SELECT timeStampLog FROM AuditLog WHERE action = ? AND userid = ? ORDER BY id DESC LIMIT 1, 1";

	public static final String RETRIEVE_LAST_PASSWORD_CHANGE_DATE_TIME = "SELECT timeStampLog FROM AuditLog WHERE action = ? AND userid = ? ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_LAST_PIN_CHANGE_DATE_TIME = "SELECT timeStampLog FROM AuditLog WHERE action = ? AND userid = ? ORDER BY id DESC LIMIT 1";

	public static final String VERIFY_PIN_CHANGE = "SELECT pin FROM AppUser WHERE id = ? AND pin = ?";

	public static final String VERIFY_PASSWORD = "SELECT password FROM AppUser WHERE id = ? AND password = ?";

	public static final String RETRIEVE_CLINIC_NAME_BY_CLINIC_ID = "SELECT name FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_NAME_BY_PRACTICE_ID = "SELECT name FROM Practice WHERE id = ?";

	public static final String SEARCH_CLINIC_TYPE_LIST = "SELECT * FROM PVClinicType WHERE clinicType LIKE ?";

	public static final String SEARCH_DIAGNOSIS_LIST = "SELECT * FROM PVDiagnosis WHERE diagnosis LIKE ?";

	public static final String SEARCH_FREQUENCY_LIST = "SELECT id, frequencyValues, count, sortOrder FROM PVFrequency WHERE frequencyValues LIKE ? AND practiceID = ? AND activityStatus = ?";

	public static final String SEARCH_PRESCRIPTION_LIST = "SELECT * FROM PVPrescription WHERE tradeName LIKE ?";

	public static final String SEARCH_REF_DOCTOR_LIST = "SELECT * FROM PVReferringDoctors WHERE doctorName LIKE ?";

	public static final String SEARCH_BILLING_LIST = "SELECT * FROM PVBillingItems WHERE chargeType LIKE ?";

	public static final String SEARCH_CLINIC_LIST = "SELECT * FROM Clinic WHERE name LIKE ?";

	public static final String RETRIEVE_PATIENT_NAME_BY_PATIENT_ID = "SELECT firstName, middleName, lastName FROM Patient WHERE id = ?";

	public static final String RETRIEVE_LAST_ENTERED_VISIT_ID_BY_VISIT_NUMBER = "SELECT id FROM Visit WHERE visitNumber = ? AND patientID = ? AND visitTypeID = ? AND clinicID = ?";

	public static final String INSERT_MEDICAL_DOCUMENT = "INSERT INTO MedicalDocuments (visitID, medicalCertificate) VALUES (?,?)";

	public static final String RETREIVE_PATIENT_LIST_BY_PATIENT_ID = "SELECT id, firstName, middleName, lastName, age, gender, address, mobile, dateOfBirth, occupation, ec, practiceRegNumber, email, phone, bloodGroup, rhFactor FROM Patient WHERE id = ?";

	public static final String INSERT_OPTICIAN_DETAILS = "INSERT INTO Optometry (sphNearOD, sphNearOS, sphDistOD, sphDistOS, cylNearOD, cylNearOS, cylDistOD, cylDistOS, axisNearOD, axisNearOS, axisDistOD, axisDistOS, vnNearOD, vnNearOS, vnDistOD, vnDistOS, visitID, comments, spectacleComments) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_OPTICIAN_OLD_GLASS_DETAILS = "INSERT INTO OldGlasses (sphNearOD, sphNearOS, sphDistOD, sphDistOS, cylNearOD, cylNearOS, cylDistOD, cylDistOS, axisNearOD, axisNearOS, axisDistOD, axisDistOS, vnNearOD, vnNearOS, vnDistOD, vnDistOS, optometryID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_EYEWEAR_DETAILS = "UPDATE Eyewear SET tint = ?, glass = ?, material = ?, clinic = ?, frame = ?, frameCharges = ?, glassCharges = ?, discount = ?, netPayment = ?, advance = ?, balance = ?, balPaid = ?, balPaidDate = ?, discountType = ? WHERE optometryID = ?";

	public static final String INSERT_DUMMY_VISIT = "INSERT INTO Visit (visitNumber, careType, visitType, patientID) VALUES (?,?,?,?)";

	public static final String UPDATE_PATIENT_VISIT = "UPDATE Visit SET visitDate = ?, diagnosis = ?, activityStatus = ?, apptID = ?, dateOfDischarge = ?, `procedure` = ?, admission_time = ?, discharge_time = ? WHERE id = ?";

	public static final String RETRIEVE_INVALIDATE_ATTEMPT = "SELECT invalidAttempts FROM Practice WHERE id = (SELECT practiceID FROM AppUser WHERE username = ?)";

	public static final String RETRIEVE_INVALIDATE_ATTEMPT_FROM_PATIENT = "SELECT invalidAttempts FROM Patient WHERE username = ?";

	public static final String SEARCH_VISIT_TYPE_LIST = "SELECT vt.id, vt.name AS visitType, vt.billingType, vt.formName, pt.name AS practice FROM PVVisitType AS vt, Clinic AS pt WHERE (vt.clinicID = pt.id AND vt.name LIKE ? AND pt.id IN (SELECT id FROM Clinic WHERE practiceID = ?)) OR (vt.clinicID = pt.id AND pt.name LIKE ? AND pt.id IN (SELECT id FROM Clinic WHERE practiceID = ?))";

	public static final String RETRIEVE_ALL_VISIT_TYPE_LIST = "SELECT vt.id, vt.name AS visitType, vt.billingType, vt.formName, pt.name AS practice FROM PVVisitType AS vt, Clinic AS pt WHERE (vt.clinicID = pt.id AND pt.id IN (SELECT id FROM Clinic WHERE practiceID = ?)) ORDER BY vt.id ASC";

	public static final String INSERT_VISIT_TYPE = "INSERT INTO PVVisitType (name, visitDuration, practiceID) VALUE (?,?,?)";

	public static final String RETRIEVE_CONFIGURATION_VISIT_TYPE_LIST_BY_ID = "SELECT * FROM PVVisitType WHERE id = ?";

	public static final String UPDATE_VISIT_TYPE = "UPDATE PVVisitType SET name = ?, visitDuration = ?, practiceID = ? WHERE id = ?";

	public static final String DELETE_CONFIGURATION_VISIT_TYPE = "DELETE FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_BY_PRACTICE_NAME = "SELECT id FROM Practice WHERE name = ?";

	public static final String INSERT_PRACTICE = "INSERT INTO Practice (name, activityStatus, suffix, consentDocumentPath, sessionTimeOut, invalidAttempts, bucketName, facilityDashboard, thirdPartyAPIIntegration) VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_ENTERED_PRACTICE_ID = "SELECT id FROM Practice WHERE name = ?";

	public static final String RETRIEVE_LETTER_IMG_BY_CLINICID = "SELECT letterHeadImage FROM Clinic WHERE id = ?";

	public static final String INSERT_CLINIC = "INSERT INTO Clinic (name, activityStatus, practiceID, suffix, pageSize,patientForm, phoneNo) VALUES (?,?,?,?,?,?,?)";

	public static final String INSERT_PLAN_DETAILS = "INSERT INTO PlanDetails (NoOfVisit, startDate, endDate, status, practiceID) VALUES (?,?,?,?,?)";

	public static final String UPDATE_PLAN_DETAILS = "UPDATE PlanDetails SET NoOfVisit = ?, startDate = ?, endDate = ?, status = ? WHERE practiceID = ?";

	public static final String RETRIEVE_LAST_CLINIC_ID_FOR_PRACTICE_ID = "SELECT id FROM Clinic WHERE practiceID = ? ORDER BY id DESC LIMIT 1";

	public static final String SEARCH_PRACTICE_LIST = "SELECT * FROM Practice WHERE name LIKE ? AND activityStatus = ? AND id =? ";

	public static final String RETRIEVE_ALL_PRACTICE_LIST = "SELECT * FROM Practice WHERE id =? AND activityStatus = ?";

	public static final String RETRIEVE_PLAN_DETAILS_LIST_BY_PRACTICE_ID = "SELECT id, NoOfVisit, startDate, endDate, status FROM PlanDetails WHERE practiceID = ? ORDER BY id DESC";

	public static final String RETRIEVE_PLAN_DETAILS_LIST_BY_PLAN_ID = "SELECT id, NoOfVisit, startDate, endDate, status FROM PlanDetails WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_DETAILS = "SELECT id, name, suffix, consentDocumentPath, sessionTimeOut, invalidAttempts, bucketName, facilityDashboard, thirdPartyAPIIntegration FROM Practice WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_CONFIG_DETAILS = "SELECT * FROM PracticeConfiguration WHERE practiceID = ?";

	public static final String RETRIEVE_CLINIC_ID_BY_PRACTICE_ID = "SELECT cli.id, cli.name, cli.phoneNo, cli.activityStatus, cli.suffix, cli.pageSize, cli.patientForm, cal.clinicStart, cal.clinicEnd, cal.breakStart1, cal.breakEnd1, cal.breakStart2, cal.breakEnd2, cal.workDays FROM Clinic AS cli, Calendar AS cal WHERE cli.id = cal.clinicID AND cli.practiceID = ?";

	public static final String VERIFY_PLAN_ID_BY_PRACTICE_ID = "SELECT id FROM PlanDetails WHERE practiceID = ? AND status = 'Active'";

	public static final String DELETE_CLINIC_ROW_BY_ID = "DELETE FROM  Clinic WHERE id = ?";

	public static final String DISABLE_PLAN_ROW_BY_ID = "UPDATE PlanDetails SET status = 'Inactive' WHERE practiceID = ? AND status = 'Active'";

	public static final String UPDATE_PRACTICE = "UPDATE Practice SET name = ?, suffix = ?, consentDocumentPath = ?, sessionTimeOut = ?, invalidAttempts = ?, bucketName = ?, facilityDashboard = ?, thirdPartyAPIIntegration = ? WHERE id = ?";

	public static final String UPDATE_CLINIC_TABLE = "UPDATE Clinic SET  logo = ?, letterHeadImage = ? WHERE id = ?";

	public static final String UPDATE_PRACTICE_CONFIGURATION_DETAILS = "UPDATE PracticeConfiguration SET tagline = ?, website = ?, logo = ?, consentDocumentPath = ?, pageSize = ?,letterHeadImage = ?, sessionTimeOut = ?, invalidAttempts = ?, currency1 = ? , practiceID = ?, patientForm = ?, currency2 = ? WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_LIST = "SELECT id, name from Practice WHERE activityStatus = ? ORDER BY name";

	public static final String RETRIEVE_PATIENT_PAGE_LIST = "SELECT formName, jspPageName FROM FormMapping WHERE careType <> ?";

	public static final String RETRIEVE_PATIENT_PAGE_LIST1 = "SELECT formName, jspPageName FROM FormMapping WHERE careType = ?";

	public static final String RETRIEVE_PATIENT_FORM_NAME_FROM_PRACTICE = "SELECT patientForm FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_OPD_FORM_NAME = "SELECT (SELECT jspPageName FROM FormMapping WHERE formName = w.formName) AS jspPageName FROM PVVisitType AS w WHERE clinicID = ?";

	public static final String RETRIEVE_REGISTRATION_NO_FROM_PATIENT = "SELECT practiceRegNumber FROM Patient WHERE  practiceRegNumber LIKE ?";

	public static final String RETRIEVE_PRACTICE_SUFFIX = "SELECT suffix FROM Practice WHERE id = ?";

	public static final String INSERT_CONSENT_DETAILS = "INSERT INTO Consent (name, gender, age, sonDaughterOf, address, phone, dateOfConsent, nameOfGuardian, relationToPatient, clinicianName, visitID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_PERSONAL_HISTORY = "INSERT INTO PersonalHistory (smoking, smokingDetails, diet, vegadharanaPurisha, vegadharanaMootra, vegadharanaKshudha, vegadharanaNidra, vegadharanaOther, patientID, other, diagnosis, description, otherDetails,alcohol,alcoholDetails,tobacco,tobaccoDetails,foodChoice,foodChoiceDetails) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PATIENT_AGE_BY_ID = "SELECT age, gender FROM Patient WHERE id = ?";

	public static final String INSERT_REFERRED_BY_DETAILS = "INSERT INTO ReferredBy (sourceType, sourceName, sourceContact, patientID) VALUES (?,?,?,?)";

	public static final String INSERT_LAB_INVESTIGATION = "INSERT INTO LabInvestigations (panel, test, qualitativeValue, quantitativeValue, visitID, investigationDate) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_VITAL_SIGNS_WEIGHT = "INSERT INTO VitalSigns (weight, visitID, comment, visitDate) VALUES (?,?,?,?)";

	public static final String UPDATE_VISIT_NOTE_INTO_VISIT = "UPDATE Visit SET visitNote = ? WHERE id = ?";

	public static final String INSERT_CLINIC_CALENDAR_DETAILS = "INSERT INTO Calendar (clinicStart, clinicEnd, breakStart1, breakEnd1, breakStart2, breakEnd2, clinicID, workDays) VALUES (?,?,?,?,?,?,?,?)";

	public static final String DELETE_CALENDAR_DETAILS = "DELETE FROM Calendar WHERE clinicID = ?";

	public static final String INSERT_VISIT_TYPE_DETAILS = "INSERT INTO PVVisitType (name, visitDuration, billingType, clinicID, newVisit, formName, consultationCharges, currency, careType, isDischarge, hasConsent, consentDocument, nextVisitDays) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_VISIT_TYPE_ID_BY_NAME = "SELECT id FROM PVVisitType WHERE name = ?";

	public static final String UPDATE_VISIT_TYPE_DETAILS = "UPDATE PVVisitType SET name = ?, visitDuration = ?, billingType = ?, formName = ?, clinicID = ?, newVisit = ?, consultationCharges = ?, currency = ?, careType = ?, isDischarge = ?, hasConsent = ?, consentDocument = ?, nextVisitDays = ? WHERE id = ?";

	public static final String VERIFY_VISIT_EXISTS_FOR_VISIT_TYPE_ID = "SELECT id FROM Visit WHERE visitTypeID = ?";

	public static final String DELETE_VISIT_TYPE_BY_VISIT_TYPE_ID = "DELETE FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_VISIT_TYPE_NAME_BY_ID = "SELECT name FROM PVVisitType WHERE id = ?";

	public static final String INSERT_COMMUNICATION = "INSERT INTO Communication (smsApptSchedule, smsApptUpdate, smsApptCancel, emailApptSchedule, emailApptUpdate, emailApptCancel, emailBill, smsBill, emailPresc, emailTo, emailFrom, emailFromPass, smsPresc, smsRefThanks, emailRefThanks, smsWelcome, emailWelcome, practiceID, emailReviewForm, smsReviewForm, reviewForm, smsInventory, emailInventory, smsDailyAppt, emailDailyAppt,smsUsername,smsPassword,smsURL,smsSenderID,smsApiKey, smsPatPortalCredentials, emailPatPortalCredentials) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_COMMUNICATION = "UPDATE Communication SET smsApptSchedule = ?, smsApptUpdate = ?, smsApptCancel = ?, emailApptSchedule = ?, emailApptUpdate = ?, emailApptCancel = ?, emailBill = ?, smsBill = ?, emailPresc = ?, emailTo = ?, emailFrom = ?, emailFromPass = ?, smsPresc = ?, smsRefThanks = ?, emailRefThanks = ?, smsWelcome = ?, emailWelcome = ?, practiceID = ?, emailReviewForm = ?, smsReviewForm = ?, reviewForm = ?, smsInventory = ?, emailInventory = ?, smsDailyAppt = ?, emailDailyAppt = ?, smsUsername = ?, smsPassword = ?, smsURL = ?, smsSenderID = ?, smsApiKey = ?, smsPatPortalCredentials = ?, emailPatPortalCredentials = ? WHERE id = ?";

	public static final String RETRIEVE_COMMUNICATION_ID_BY_PRACTICE_ID = "SELECT id, emailTo, emailFrom, emailFromPass, smsUsername, smsPassword, smsURL, smsSenderID,  smsApiKey FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_CLINIC_DETAILS_BY_PRACTICE_ID = "SELECT id, name FROM Clinic WHERE practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_VISIT_TYPE_ID = "SELECT id FROM PVVisitType WHERE clinicID = ?";

	public static final String RETRIEVE_CLINIC_START_TIME = "SELECT clinicStart FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_CLINIC_END_TIME = "SELECT clinicEnd FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_CLINIC_BREAK_TIMES = "SELECT breakStart1, breakEnd1, breakStart2, breakEnd2 FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_CIRCULARS_BY_ACTIVITY_STATUS = "SELECT circular,circularFileName FROM  GovtCirculars WHERE Status = 'Active'";

	public static final String RETIEVE_VISIT_DURATION = "SELECT visitDuration FROM PVVisitType WHERE practiceID = ?";

	public static final String RETIEVE_CIRCULAR_FILENAME = "SELECT circularFileName FROM GovtCirculars WHERE circular = ?";

	public static final String RETRIEVE_CLINIC_START_END_TIME = "SELECT clinicStart, clinicEnd FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_CLINIC_BREAK1_TIME = "SELECT breakStart1, breakEnd1 FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_CLINIC_BREAK2_TIME = "SELECT breakStart2, breakEnd2 FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_SLOT1_NAME_AND_TIME = "SELECT slot1, slot1Start, slot1End FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_SLOT2_NAME_AND_TIME = "SELECT slot2, slot2Start, slot2End FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_VERIFY_PATIENT_DETAILS = "SELECT id, firstName, middleName, lastName, mobile, registrationNumber FROM Patient WHERE firstName = ? OR middleName = ? OR lastName = ? OR mobile = ?";

	public static final String RETRIEVE_VISIT_TYPES1 = "SELECT id, name FROM PVVisitType WHERE clinicID = ?";

	public static final String RETRIEVE_VISIT_DURATION_BY_VISIT_TYPE_ID = "SELECT visitDuration FROM PVVisitType WHERE id = ?";

	public static final String INSERT_PATIENT_DETAILS1 = "INSERT INTO Patient (firstName, middleName, lastName, mobile, practiceRegNumber, practiceID) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_APPOINTMENT_DETAILS = "INSERT INTO Appointment (apptNumber, apptDate, apptTimeFrom, apptTimeTo, status, patientID, clinicID, visitTypeID, clinicianID, walkin) VALUES (?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PATIENT_ID_BY_FIRST_AND_LAST_NAME = "SELECT id FROM Patient WHERE firstName = ? AND lastName = ?";

	public static final String RETRIEVE_PATIENT_ID_BY_FIRST_AND_LAST_NAME_AND_MOBILE = "SELECT id FROM Patient WHERE firstName = ? AND lastName = ? AND mobile = ?";
	
	public static final String RETRIEVE_PATIENT_DETAILS_BY_ID = "SELECT id, firstName, lastName FROM Patient WHERE id = ?";

	public static final String RETRIEVE_APPT_CALENDAR_DETAILS = "SELECT a.id, a.status, a.apptDate, a.apptTimeFrom, a.apptTimeTo, a.patientID, a.nextApptTaken, a.apptNumber, (SELECT CONCAT(firstName,' ',middleName,' ',lastName) as empName FROM AppUser WHERE id = a.clinicianID) AS clinicianName, (SELECT concat(p.id, '-',p.firstName, ' ', p.lastName, '(', (SELECT c.regNumber FROM ClinicRegistration AS c WHERE c.clinicID = a.clinicID AND c.patientID = p.id), ')' ) FROM Patient AS p WHERE p.id = a.patientID) AS patientDetails, (SELECT vt.name FROM PVVisitType AS vt WHERE vt.id = a.visitTypeID) AS visitType, a.visitTypeID FROM Appointment AS a WHERE clinicID = ? AND (MONTH(apptDate) BETWEEN (MONTH(now())-1) AND (MONTH(now())+3)) AND (YEAR(apptDate)=YEAR(now()))";

	public static final String SEARCH_CATEGORY = "SELECT * FROM Category WHERE name LIKE ?";

	public static final String RETRIEVE_ALL_CATEGORY = "SELECT * FROM Category";

	public static final String INSERT_CATEGORY = "INSERT INTO Category (name, type) VALUES (?,?)";

	public static final String RETRIEVE_CATEGORY_BY_ID_BK = "SELECT id, name, type FROM Category WHERE id = ?";

	public static final String RETRIEVE_CATEGORY_BY_ID = "SELECT id, name, type, otcategory FROM Category WHERE id = ?";

	public static final String UPDATE_CATEGORY = "UPDATE Category SET name  = ?, type = ? WHERE id = ?";

	public static final String DELETE_CATEGORY = "DELETE FROM Category WHERE id = ?";

	public static final String SEARCH_TAX = "SELECT * FROM Tax WHERE taxName LIKE ?";

	public static final String RETRIEVE_ALL_TAXES = "SELECT * FROM Tax";

	public static final String INSERT_TAX = "INSERT INTO Tax (taxName, taxPercent) VALUES (?,?)";

	public static final String RETRIEVE_TAX_BY_ID = "SELECT id, taxName, taxPercent FROM Tax WHERE id = ?";

	public static final String UPDATE_TAX = "UPDATE Tax SET taxName  = ?, taxPercent = ? WHERE id = ?";

	public static final String DELETE_TAX = "DELETE FROM Tax WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_CATEGORIES = "SELECT id, name FROM Category";

	public static final String RETRIEVE_PRODUCT_TAX = "SELECT id, taxName, taxPercent FROM Tax ORDER BY taxName";

	public static final String INSERT_PRODUCT = "INSERT INTO Product (drugName,tradeName, barcode, description, unit, categoryID, minQuantity, strength, form, code, activityStatus, clinicID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETIREVE_LAST_ENTERED_PRODUCT_ID = "SELECT id FROM Product WHERE barcode = ?";

	public static final String INSERT_PRODUCT_PRICE = "INSERT INTO Price (costPrice, sellingPrice, priceFrom, taxInclusive, taxName, taxPercent, productID, activityStatus) VALUES (?,?,?,?,?,?,?,?)";

	public static final String RETIREVE_ALL_PRODUCT = "SELECT id, tradeName, categoryID, description, activityStatus FROM Product WHERE clinicID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PRODUCT_CATEGORY_NAME = "SELECT name FROM Category WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_PRICE_BY_ID = "SELECT * FROM Price WHERE productID = ? ORDER BY activityStatus";

	public static final String SEARCH_PRODUCT = "SELECT id, tradeName, categoryID, description, activityStatus FROM Product WHERE tradeName LIKE ? AND clinicID = ? AND activityStatus = ?";

	public static final String UPDATE_PRODUCT = "UPDATE Product SET drugName = ?, tradeName = ?, barcode = ?, description = ?, unit = ?, categoryID = ?, minQuantity = ?, strength = ?, form = ?, code = ?  WHERE id = ?";

	public static final String INACTIVE_PRODUCT_PRICE = "UPDATE Price SET activityStatus = ? WHERE productID = ?";

	public static final String UPDATE_ACTIVE_PRODUCT_PRICE = "UPDATE Product SET activePrice = ? WHERE id = ?";

	public static final String INSERT_SUPPLIER = "INSERT INTO Supplier (name, agency, vatNumber, mobile, email, address, city, state, country, pinCode, registrationDate, activityStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_ALL_SUPPLIER = "SELECT * FROM Supplier WHERE activityStatus = ?";

	public static final String UPDATE_SUPPLIER = "UPDATE Supplier SET name = ?, agency = ?, vatNumber = ?, mobile = ?, email = ?, address = ?, city = ?, state = ?, country = ?, pinCode = ?, registrationDate = ?, activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_ALL_SUPPLIERS = "SELECT * FROM Supplier";

	public static final String RETRIEVE_SUPPLIER_BY_ID = "SELECT id, name, agency, vatNumber, mobile, email, address, city, state, country, pinCode, registrationDate FROM Supplier WHERE id = ?";

	public static final String SEARCH_SUPPLIER = "SELECT * FROM Supplier WHERE name LIKE ? AND activityStatus = ?";

	public static final String DELETE_SUPPLIER = "UPDATE Supplier SET activityStatus = ? WHERE id = ?";

	public static final String VERIFY_PRODUCT_EXIST = "SELECT id FROM Product WHERE barcode = ?";

	public static final String RETRIEVE_TAX_PERCENT_BY_TAX_NAME = "SELECT taxPercent FROM Tax WHERE taxName = ?";

	public static final String RETRIEVE_RECEIPT_NO = "SELECT receiptNo FROM StockReceipt WHERE clinicID = ? ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_PRODUCT_LIST = "SELECT id, tradeName FROM Product WHERE clinicID = ? AND activityStatus = ? ORDER BY tradeName";

	public static final String RETRIEVE_SUPPLIER_LIST = "SELECT id, name FROM Supplier WHERE activityStatus = ? ORDER BY name";

	public static final String RETRIEVE_SUPPLIER_LIST_BY_ID = "SELECT id, agency, mobile, address, vatNumber FROM Supplier WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_RATE_DETAILS_BY_ID = "SELECT a.barcode  FROM Product as a WHERE a.id = ?";

	public static final String RETRIEVE_PRODUCT_ID_BY_BARCODE = "SELECT id FROM Product WHERE barcode = ?";

	public static final String RETRIEVE_PRODUCT_NAME_BY_ID = "SELECT tradeName FROM Product WHERE id = ?";

	public static final String INSERT_STOCK_RECEIPT = "INSERT INTO StockReceipt (receiptNo, totalAmount, balPayment, activityStatus, userID, supplierID, clinicID) VALUE (?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_INSERTED_STOCK_RECEIPT_ID = "SELECT id FROM StockReceipt ORDER BY id DESC LIMIT 1";

	public static final String INSERT_STOCK = "INSERT INTO Stock (stockDate, productID, amount, expiryDate, taxAmount, status, stockReceiptID, costPrice, sellingPrice, taxInclusive, taxName, taxPercent, netStock, clinicID) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_TAX_PERCENT_BY_PRODUCT_ID = "SELECT taxPercent FROM Price WHERE productID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PRODUCT_QUANTITY = "SELECT sum(quantity) AS quantity FROM StockTransaction WHERE stockID = ?";

	public static final String UPDATE_PRODUCT_QUANTITY = "UPDATE Stock SET netStock = netStock + ? WHERE id = ?";

	public static final String SEARCH_STOCK_RECEIPT = "SELECT id, receiptNo, receiptDate, (SELECT name FROM Supplier WHERE id = supplierID AND activityStatus = ?) AS supplier, totalAmount, (SELECT name FROM Clinic WHERE id = clinicID) AS clinic FROM StockReceipt WHERE ((receiptNo LIKE ?) OR (supplierID IN (SELECT id FROM Supplier WHERE name LIKE ?))) AND clinicID = ?";

	public static final String SEARCH_STOCK_RECEIPT_BY_PRODUCT_NAME = "SELECT sr.id, sr.receiptNo, sr.receiptDate, (SELECT name FROM Supplier WHERE id = sr.supplierID AND activityStatus = ? ) AS supplier, sr.totalAmount, (SELECT name FROM Clinic WHERE id = sr.clinicID) AS clinic FROM StockReceipt AS sr WHERE sr.id IN (SELECT stockReceiptID FROM Stock WHERE productID IN (SELECT id FROM Product WHERE tradeName LIKE ? AND activityStatus = ?)) AND sr.clinicID = ?;";

	public static final String RETRIEVE_ALL_STOCK_LIST = "SELECT id, receiptNo, receiptDate, (SELECT name FROM Supplier WHERE id = supplierID AND activityStatus = ? ) AS supplier, totalAmount, (SELECT name FROM Clinic WHERE id = clinicID) AS clinic FROM StockReceipt WHERE clinicID = ?";

	public static final String UPDATE_TAX_AMOUNT = "UPDATE StockReceipt SET tax = ? WHERE id = ?";

	public static final String RETRIEVE_TAX_AMOUNT = "SELECT SUM(taxAmount) AS tax FROM Stock WHERE stockReceiptID = ? ";

	public static final String DELETE_PRODUCT_FROM_STOCK = "DELETE FROM Stock WHERE id = ?";

	public static final String RETRIEVE_STOCK_PRODUCT_LIST = "SELECT id, stockDate, productID, costPrice, sellingPrice, netStock, amount, expiryDate, taxInclusive, taxName, taxPercent, taxAmount, stockReceiptID, status, clinicID FROM Stock WHERE stockReceiptID = ?";

	public static final String RETRIEVE_PRODUCT_NAME_AND_RATE_BY_PRODUCT_ID = "SELECT a.tradeName FROM Product as a WHERE a.id = ?";

	public static final String RETRIEVE_PRODUCT_BARCODE_BY_ID = "SELECT barcode FROM Product WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_NET_STOCK = "SELECT netStock FROM Product WHERE id = ? AND activityStatus = ?";

	public static final String RETRIEVE_STOCK_RECEIPT_LIST = "SELECT * FROM StockReceipt WHERE id = ?";

	public static final String UPDATE_STOCK_RECEIPT = "UPDATE StockReceipt SET receiptNo = ?, totalAmount = ?, balPayment = ?, activityStatus = ?, userID = ?, supplierID = ?, clinicID = ? WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_TAX_AMOUNT = "SELECT taxAmount FROM Stock WHERE productID = ?";

	public static final String UPDATE_STOCK_RECEIPT_TAX_AMOUNT = "UPDATE StockReceipt SET tax = tax - ? WHERE id = ?";

	public static final String SEARCH_STOCK_ORDER = "SELECT st.productID, (SELECT tradeName FROM Product WHERE id = st.productID) AS tradeName, (SELECT barcode FROM Product WHERE id = st.productID) AS barcode, (SELECT minQuantity FROM Product WHERE id = st.productID) AS minQuantity, st.netStock, st.stockReceiptID, (SELECT name FROM Clinic WHERE id = sr.clinicID) AS clinic, sr.clinicID FROM Stock AS st, StockReceipt AS sr WHERE st.stockReceiptID = sr.id AND sr.supplierID = ? AND sr.clinicID = ? AND sr.clinicID = st.clinicID";

	public static final String RETRIEVE_SUPPLIE_NAME_BY_ID = "SELECT name FROM Supplier WHERE id = ?";

	public static final String INSERT_ORDER = "INSERT INTO Orders (supplierID, productID, quantity, userID, clinicID, orderNumber) VALUES (?,?,?,?,?,?)";

	public static final String RETRIEVE_CLINIC_NAME_BY_PRACTICE_ID = "SELECT name FROM Clinic WHERE practiceID = ?";

	public static final String RETRIEVE_EMAIL_FROM_BY_PRACTICE_ID = "SELECT emailFrom FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_EMAIL_FROM_PASS_BY_PRACTICE_ID = "SELECT emailFromPass FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_SUPPLIER_NAME_BY_ID = "SELECT email FROM Supplier WHERE id = ?";

	public static final String RETRIEVE_TAB_NAME_BY_FORM_NAME = "SELECT tabs FROM FormMapping WHERE formName = ?";

	public static final String RETRIEVE_TRADE_NAME_BY_CATEGORY_ID = "SELECT id, tradeName FROM Product WHERE categoryID = ? AND clinicID = ? AND activityStatus = ? ORDER BY tradeName";

	public static final String RETRIEVE_PRODUCT_NAME_CATEGORY_ID_BY_PRODUCT_ID = "SELECT categoryID, tradeName, (SELECT name FROM Category WHERE id = categoryID) AS name FROM Product WHERE id = ?";

	public static final String RETRIEVE_BILLING_TYPE = "SELECT billingType FROM PVVisitType WHERE clinicID = ?";

	public static final String INSERT_RASAYU_PRESCRIPTION = "INSERT INTO Prescription (productID, numberOfDays, frequency, dosage, comment, activityStatus, visitID, quantity, compound, isCompound, instruction, discount, section, rate, consultationCharges) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PRESCRIPTION_FREQUENCY_BY_VISIT_ID = "SELECT frequency FROM Prescription WHERE visitID = ?";

	public static final String RETRIEVE_LAST_ENTERED_PRESCRIPTION_ID = "SELECT id FROM Prescription WHERE visitID = ? ORDER BY id DESC LIMIT 1";

	public static final String INSERT_BILLING_RECEIPT = "INSERT INTO Receipt (receiptNo, consultationCharges, totalAmount, tax, netAmount, advPayment, balPayment, paymentType, billingType, activityStatus, referenceReceiptNo, visitID, userID, totalDiscount, adjAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_BILLING_RECEIPT_NO = "SELECT receiptNo FROM Receipt WHERE visitID IN (SELECT id FROM Visit WHERE clinicID = ?) ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_LAST_ENTERED_RECEIPT_ID = "SELECT id FROM Receipt WHERE visitID = ? ORDER BY id DESC LIMIT 1";

	public static final String INSERT_PAYMENT_DETAILS = "INSERT INTO PaymentDetails (receiptID, chequeNumber, bankName, bankBranch, chequeDate, chequeAmount, cashPaid, cashToReturn, cardNumber, chequeIssueBy, activityStatus, creditNote, mobile, otherMode, cashAdjStatus, cardAmount, paymentDate, otherAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_TRANSACTION_DETAILS = "INSERT INTO Transactions (quantity, receiptID, prescriptionID, amount, taxAmount, activityStatus, productID, rate, discount) VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_TOTAL_PRODUCT_QUANTITY = "SELECT sum(quantity) AS sum FROM Transactions WHERE productID = ? AND receiptID = ?";

	public static final String RETRIEVE_TOTAL_TRANSACTION_TAX_AMOUNT = "SELECT sum(taxAmount) AS sum FROM Transactions WHERE receiptID = ?";

	public static final String UPDATE_RECEIPT_TAX_AMOUNT = "UPDATE Receipt SET tax = ? WHERE id = ?";

	public static final String RETRIEVE_PAYMENT_TYPE = "SELECT paymentType FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_TRANSACTION_DETAILS = "SELECT id, quantity, receiptID, prescriptionID, amount, discount, taxAmount, productID, rate, (SELECT tradeName FROM Product WHERE id = productID) AS product FROM Transactions WHERE receiptID = (SELECT id FROM Receipt WHERE visitID = ?) ORDER BY product";

	public static final String RETRIEVE_CATEGORY_BY_PRODUCT_ID = "SELECT categoryID FROM Product WHERE id = ?";

	public static final String RETRIEVE_RECEIPT_LIST = "SELECT * FROM Receipt WHERE id = ?";

	public static final String RETRIEVE_PAYMENT_DETAILS = "SELECT * FROM PaymentDetails WHERE receiptID = ?";

	public static final String UPDATE_PRODUCT_QUANTITY_FOR_BILLING = "UPDATE Stock SET netStock = netStock - ? WHERE id = ? AND productID = ? AND clinicID = ?";

	public static final String RETRIEVE_CONSULTAION_CHARGES = "SELECT consultationCharges FROM PVVisitType WHERE clinicID = ?";

	public static final String RETRIEVE_VISIT_DEATILS_BY_ID = "SELECT id, diagnosis, visitNote, visitDate, apptID FROM Visit WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_VISIT_DEATILS_BY_ID2 = "SELECT id, diagnosis, visitNote, visitDate, apptID FROM Visit WHERE patientID = ?";

	public static final String OPD_PDF_RETRIEVE_PRESENT_COMPLAINTS = "SELECT * FROM PresentComplaints WHERE visitID = ?";

	public static final String OPD_PDF_RETRIEVE_MEDICAL_HISTORY = "SELECT * FROM MedicalHistory WHERE patientID = ?";

	public static final String OPD_PDF_RETRIEVE_PERSONAL_HISTORY = "SELECT * FROM PersonalHistory WHERE patientID = ?";

	public static final String OPD_PDF_RETRIEVE_PRESCRIPTION_HISTORY = "SELECT * FROM PrescriptionHistory WHERE patientID = ?";

	public static final String OPD_PDF_RETRIEVE_VITAL_SIGNS = "SELECT * FROM VitalSigns WHERE visitID = ?";

	public static final String OPD_PDF_RETRIEVE_Clinician_Details = "SELECT signImage, firstName, lastName FROM AppUser WHERE practiceID = ? AND activityStatus = ? AND username = ? ";

	public static final String RETRIEVE_REFERRED_BY_DETAILS_BY_PATIENT_ID = "SELECT sourceType, sourceName, sourceContact FROM ReferredBy WHERE patientID = ?";

	public static final String RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID = "SELECT smoking, smokingDetails, diet, other, diagnosis, description, otherDetails, alcohol, alcoholDetails, tobacco, tobaccoDetails, foodChoice, foodChoiceDetails FROM PersonalHistory WHERE patientID = ?";

	public static final String RETRIEVE_SOURCE_TYPE = "SELECT sourceType FROM ReferredBy WHERE patientID = ?";

	public static final String RETRIEVE_VITAL_SIGNS_LIST = "SELECT id, weight, comment, visitDate, (SELECT visitDate FROM Visit WHERE id = visitID) AS oldVisitDate FROM  VitalSigns WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) ORDER BY visitDate DESC";

	public static final String DELETE_VITAL_SIGNS_DETAILS = "DELETE FROM VitalSigns WHERE id = ?";

	public static final String RETRIEVE_NATURAL_URGES = "SELECT vegadharanaPurisha, vegadharanaMootra, vegadharanaKshudha, vegadharanaNidra, vegadharanaOther FROM PersonalHistory WHERE patientID = ?";

	public static final String RETRIEVE_LAB_INVESTIGATION = "SELECT * FROM LabInvestigations WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?)";

	public static final String UPDATE_RASAYU_VISIT = "UPDATE Visit SET visitTypeID = ?, visitDate = ?, diagnosis = ?, visitNote = ?, clinicID = ? WHERE id = ?";

	public static final String UPDATE_REFERRED_BY_DETAILS = "UPDATE ReferredBy SET sourceType = ?, sourceName = ?, sourceContact = ? WHERE patientID = ?";

	public static final String UPDATE_CONSENT_DETAILS = "UPDATE Consent SET name = ?, gender = ?, age = ?, sonDaughterOf = ?, address = ?, phone = ?, dateOfConsent = ?, nameOfGuardian = ?, relationToPatient = ?, clinicianName = ? WHERE visitID = ?";

	public static final String UPDATE_PERSONAL_HISTORY = "UPDATE PersonalHistory SET smoking = ?, smokingDetails = ?, diet = ?, vegadharanaPurisha = ?, vegadharanaMootra = ?, vegadharanaKshudha = ?, vegadharanaNidra = ?, vegadharanaOther = ?, other = ?, diagnosis = ?, description = ?, otherDetails = ?,alcohol = ?, alcoholDetails = ?, tobacco = ?, tobaccoDetails = ?,foodChoice = ?,foodChoiceDetails = ?  WHERE patientID = ?";

	public static final String UPDATE_SURVEY = "UPDATE Volunteer SET firstname = ?, middlename = ?, lastname = ?, mobile = ?, email =?, dob = ?, age = ?, gender = ?, address = ?,  travelOutside = ?, admitted = ?, suffer_from = ?, surgeries = ?, diabOrHypertension = ?, awareness = ?, knowFamily = ?, psychological_issue = ?, training =?, tested_positive = ?, emergencyContact_name = ?, emergencyContact_relation = ?, emergencyContact_mobile = ?, IDproof = ? WHERE ID = ?";

	public static final String UPDATE_LAB_INVESTIGATION = "UPDATE LabInvestigations SET qualitativeValue = ?, quantitativeValue = ? WHERE id = ?";

	public static final String UPDATE_BILLING_RECEIPT = "UPDATE Receipt SET receiptNo = ?, consultationCharges = ?, totalAmount = ?, tax = ?, netAmount = ?, advPayment = ?, balPayment = ?, paymentType = ?, billingType = ?, activityStatus = ?, referenceReceiptNo = ?, userID = ?, totalDiscount = ?, receiptDate = receiptDate WHERE visitID = ?";

	public static final String UPDATE_PAYMENT_DETAILS = "UPDATE PaymentDetails SET chequeNumber = ?, bankName = ?, bankBranch = ?, chequeDate = ?, chequeAmount = ?, cashPaid = ?, cashToReturn = ?, cardNumber = ?, chequeIssueBy = ?, creditNote = ?, mobile = ?, otherMode = ?, cashAdjStatus = ?, cardAmount = ?, paymentDate = ?, otherAmount = ? WHERE receiptID = ?";

	public static final String DELETE_TRANSACTIONS_DETAILS_BY_RECEIPT_ID = "DELETE FROM Transactions WHERE receiptID = ?";

	public static final String UPDATE_APPOINTMENT_STATUS = "UPDATE Appointment SET status = ? WHERE status = ? AND apptDate = ?";

	public static final String UPDATE_APPOINTMENT_STATUS_FOR_PATIENT = "UPDATE Appointment SET status = ? WHERE id = (SELECT Visit.apptID FROM Visit WHERE Visit.id = ?) AND patientID = ?";

	public static final String UDPATE_APPOINTMENT_STATUS_FOR_PATIENT1 = "UPDATE Appointment SET status = ? WHERE apptDate = ? AND patientID = ? AND apptTimeFrom  = ? AND apptTimeTo = ?";

	public static final String RETRIEVE_VITAL_SIGNS_LIST_FOR_NEW_VISIT = "SELECT id, weight, (SELECT visitDate FROM Visit WHERE id = visitID) AS visitDate, comment FROM  VitalSigns WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?)";

	public static final String VERIFY_USERNAME_EXISTS = "SELECT id, username FROM AppUser WHERE username = ?";

	public static final String RETRIEVE_USERNAME_BY_ID = "SELECT username FROM AppUser WHERE id = ?";

	public static final String UPDATE_APPOINTMENT = "UPDATE Appointment SET apptDate = ?, apptTimeFrom = ?, apptTimeTo = ?, visitTypeID = ? WHERE patientID = ? AND apptDate = ? AND apptTimeFrom = ? AND apptTimeTo = ?";

	public static final String RETRIEVE_FULL_NAME = "SELECT firstName, lastName FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_CLINIC_LIST_BY_PRACTICE_ID = "SELECT id, name FROM Clinic WHERE practiceID = ? ORDER BY name";

	public static final String RETRIEVE_CLINIC_LIST_BY_PATIENT_ID = "SELECT id, name FROM Clinic WHERE id IN  (SELECT clinicID FROM Visit WHERE id IN (SELECT id FROM Visit WHERE patientID = ?)) ORDER BY name";

	public static final String RETRIEVE_RECEIPT_DETAILS_FOR_PDF = "SELECT * FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_TRANSACTION_DEATILS_FOR_PDF = "SELECT t.quantity, t.amount, (SELECT tradeName FROM Product WHERE id = t.productID) AS product,(SELECT barcode FROM Product WHERE id = t.productID) AS barcode, (SELECT (SELECT name FROM Category WHERE id = categoryID) AS category FROM Product WHERE id = t.productID), (SELECT sellingPrice FROM Price WHERE productID = t.productID) AS rate, (SELECT taxPercent FROM Price WHERE productID = t.productID) AS vat FROM Transactions AS t WHERE receiptID IN (SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String RETRIEVE_PATIENT_DETAILS_FOR_PDF = "SELECT id, firstName, middleName, lastName FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PRESCRIPTION_LIST_FOR_PDF = "SELECT quantity, (SELECT tradeName FROM Product WHERE id = productID) AS product, compound, numberOfDays, frequency, dosage, comment, isCompound FROM Prescription WHERE visitID = ?";

	public static final String RETRIEVE_PRESCRIPTION_LIST_FOR_LABEL = "SELECT (SELECT dosage FROM Prescription WHERE id = prescriptionID) AS dosage, (SELECT frequency FROM Prescription WHERE id = prescriptionID) AS frequency, (SELECT comment FROM Prescription WHERE id = prescriptionID) AS comment, (SELECT tradeName FROM Product WHERE id = productID) AS tradeName, quantity FROM Transactions WHERE receiptID = (SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String RETRIEVE_PAYMENT_DEATILS_FOR_PDF = "SELECT * FROM PaymentDetails WHERE receiptID = (SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String RETRIEVE_EMPLOYEE_NAME_BY_ID = "SELECT firstName, middleName, lastName FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_DIAGNOSIS_LIST_FOR_SMS = "SELECT distinct(diagnosis) AS diagnosis FROM Visit WHERE clinicID IN (SELECT id FROM Clinic WHERE practiceID = ?) AND diagnosis <> '-1'";

	public static final String RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS = "SELECT distinct(SELECT mobile FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS mobile, (SELECT CONCAT(firstName, ' ', lastName) FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS patientName FROM Visit AS v WHERE v.diagnosis IN (?) AND v.clinicID IN (SELECT id FROM Clinic WHERE practiceID = ?)";

	public static final String RETRIEVE_PATIENT_MOBILE_BASED_ON_DIAGNOSIS_FOR_CLINIC = "SELECT distinct(SELECT mobile FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS mobile, (SELECT CONCAT(firstName, ' ', lastName) FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS patientName FROM Visit AS v WHERE v.diagnosis IN (?) AND v.clinicID = ?";

	public static final String RETRIEVE_PRACTICE_NAME_BY_ID = "SELECT name FROM Practice WHERE id = ?";

	public static final String RETRIEVE_PRACTICE_ID_BY_CLINICID = "SELECT practiceID FROM Clinic WHERE id = ?";

	public static final String DECREASE_ALLOWED_VISIT_BY_1 = "UPDATE Practice SET allowed_visit = allowed_visit - 1 where id = ?";

	public static final String RETRIEVE_START_DATE_BY_PracticeID = "SELECT startDate FROM PlanDetails WHERE practiceID = ?";

	public static final String RETRIEVE_END_DATE_BY_PracticeID = "SELECT endDate FROM PlanDetails WHERE practiceID = ?";

	public static final String RETRIEVE_PATIENT_MOBILE_BY_ID = "SELECT mobile, email FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID1 = "SELECT firstName, lastName, practiceRegNumber FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PATIENT_FIRST_NAME_MIDDLE_LAST_NAME_BY_ID1 = "SELECT firstName, lastName, middleName, practiceRegNumber FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PATIENT_AGE_BY_ID1 = "SELECT age FROM Patient WHERE firstName = ? AND middleName = ? AND lastName = ?";

	public static final String RETRIEVE_PATIENT_USERNAME_BY_ID1 = "SELECT username FROM Patient WHERE id = ?";

	public static final String RETRIEVE_USER_USERNAME_BY_ID1 = "SELECT username FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_PATIENT_PASSWORD_BY_ID1 = "SELECT password FROM Patient WHERE id = ?";

	public static final String RETRIEVE_PATIENT_FIRST_NAME_LAST_NAME_BY_ID = "SELECT p.id, p.firstName, p.lastName, c.regNumber AS regNo FROM Patient AS p , ClinicRegistration AS c WHERE p.id = c.patientID AND p.id = ? AND c.clinicID = ?";

	public static final String RETRIEVE_BILL_AMOUNT_BY_VISIT_ID = "SELECT netAmount FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_VISIT_DATE_AND_TIME = "SELECT visitDate, visitTimeFrom FROM Visit WHERE id = ?";

	public static final String RETRIEVE_PRESCRIPTION_LIST_FOR_SMS_BY_VISIT_ID = "SELECT (SELECT dosage FROM Prescription WHERE id = prescriptionID) AS dosage, (SELECT frequency FROM Prescription WHERE id = prescriptionID) AS frequency, (SELECT comment FROM Prescription WHERE id = prescriptionID) AS comment, (SELECT tradeName FROM Product WHERE id = productID) AS tradeName, quantity FROM Transactions WHERE receiptID = (SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String VERIFY_RECEIPT_ADDED = "SELECT id FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_VISIT_TYPE_ID_FOR_NEW_VISIT_FLAG = "SELECT id FROM PVVisitType WHERE practiceID = ? AND newVisit = ?";

	public static final String RETRIEVE_CALENDAR_DETAILS_BY_CLINIC_ID = "SELECT cli.pageSize,cli.patientForm, cal.clinicStart, cal.clinicEnd, cal.breakStart1, cal.breakEnd1, cal.breakStart2, cal.breakEnd2, cal.workDays, cal.clinicID FROM Clinic AS cli, Calendar AS cal WHERE cli.id = cal.clinicID AND cli.id = ?";

	public static final String RETRIEVE_PRACTICEID_BY_CLINICID = "SELECT practiceID FROM Clinic where id =?";

	public static final String RETRIEVE_UPLOADED_IMG_BY_CLINIC_ID = "SELECT letterHeadImage, logo FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_APPOINTMENT_LIST_FOR_REMINDER_SMS = "SELECT patientID, apptDate, apptTimeFrom, clinicID, (SELECT practiceID FROM Clinic WHERE id = clinicID) AS practiceID, (SELECT CONCAT(firstName,' ',lastName) FROM AppUser WHERE id = clinicianID) as doctorName FROM Appointment WHERE DAY(apptDate) = DAY(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?)";

	public static final String RETRIEVE_CLINICIAN_DEATILS_LIST = "SELECT email, id, firstName, lastName, practiceID FROM AppUser WHERE userType = ?";

	public static final String RETRIEVE_CLINIC_ID_LIST_FOR_TODAYS_APPOINTMENT = "SELECT distinct(clinicID) FROM Appointment WHERE DAY(apptDate) = DAY(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?)";

	public static final String RETRIEVE_TODAYS_APPOINTMENT_LIST_FOR_CLINIC_ID = "SELECT patientID, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, (SELECT name FROM Clinic WHERE id = clinicID) AS clinicName, (SELECT name FROM PVVisitType WHERE id = visitTypeID) AS visitType FROM Appointment WHERE DAY(apptDate) = DAY(NOW())  AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?) AND clinicID = ? ORDER BY apptTimeFrom";

	public static final String UPDATE_CLINIC_CALENDAR_DETAILS = "UPDATE Calendar SET clinicStart = ?, clinicEnd =?, breakStart1 = ?, breakEnd1 = ?, breakStart2 = ?, breakEnd2 = ?, workDays = ? WHERE clinicID = ?";

	public static final String UPDATE_CLINIC_PageSize_PatForm_DETAILS = "UPDATE Clinic SET pageSize = ?, patientForm = ? WHERE id = ?";

	public static final String RETRIEVE_USER_EMPLOYEE_DETAILS_BY_ID = "SELECT * FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_APPOINTMENT_TIME_BY_ID = "SELECT apptTimeFrom, apptTimeTo, apptDate FROM Appointment WHERE id = ?";

	public static final String RETRIEVE_VISIT_ID_BY_PATIENT_ID_AND_APT_ID = "SELECT id FROM Visit WHERE patientID = ? AND apptID = ? AND activityStatus = ?";

	public static final String UPDATE_CLINIC_LOGO = "UPDATE Clinic SET logo = ? WHERE id = ?";

	public static final String UPDATE_CLINIC_DETAILS = "UPDATE Clinic SET logo = ?,  letterHeadImage = ? WHERE id = ?";
	
	public static final String UPDATE_COMMENT_BY_APPT_ID = "UPDATE Appointment SET comments = ? WHERE id = ?"; //Change
	
	public static final String RETRIEVE_COMMENT_BY_APPT_ID = "SELECT comments FROM Appointment WHERE id = ?"; //Change

	public static final String RETRIEVE_REVIEW_FOR_URL = "SELECT reviewForm FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_CLINIC_REGISTRATION_NO = "SELECT regNumber FROM ClinicRegistration WHERE regNumber LIKE ? AND clinicID = ?";

	public static final String INSERT_CLINIC_REGISTRATION = "INSERT INTO ClinicRegistration (clinicID, regNumber, patientID) VALUES (?,?,?)";

	public static final String VERIFY_PATIENT_EXISTS_FOR_CURRENT_CLINIC = "SELECT id FROM ClinicRegistration WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_CLINIC_REG_NO_BY_CLINIC_ID = "SELECT regNumber, patientFormNo FROM ClinicRegistration WHERE clinicID = ? AND patientID = ?";

	public static final String RETRIEVE_CLINIC_REG_NO_BY_PATIENT_ID = "SELECT regNumber FROM ClinicRegistration WHERE patientID = ?";

	public static final String RETRIEVE_RECEIPTIONISTIST_USER_DETAILS = "SELECT CONCAT(firstName,' ',lastName) AS empName, email FROM AppUser WHERE defaultClinicID = ? AND userType = ?";

	public static final String RETRIEVE_HEAD_NAME = "SELECT CONCAT(firstName,' ',lastName) AS headName FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_VISIT_LIST_FOR_PRACTICE = "SELECT visitID FROM Receipt WHERE balPayment > 0 AND userID IN (SELECT id FROM AppUser WHERE practiceID = ?)";

	public static final String RETRIEVE_VISIT_LIST_FOR_CLINIC = "SELECT visitID FROM Receipt WHERE balPayment > 0 AND visitID IN (SELECT id FROM Visit WHERE clinicID = ?)";

	public static final String RETRIEVE_PATIENT_DETAILS_FOR_CREDIT_BY_VISIT_ID = "SELECT DISTINCT(SELECT CONCAT(firstName,' ',lastName) FROM Patient where id = v.patientID AND activityStatus = 'Active') AS patientName, (SELECT mobile FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS mobile, patientID FROM Visit AS v WHERE v.id IN (?)";

	public static final String RETRIEVE_PATIENT_CREDIT_FOR_PRACTICE = "SELECT balPayment FROM Receipt WHERE balPayment > 0 AND userID IN (SELECT id FROM AppUser WHERE practiceID = ?) AND visitID IN (SELECT id FROM Visit WHERE patientID = ?)";

	public static final String RETRIEVE_PATIENT_CREDIT_FOR_CLINIC = "SELECT balPayment FROM Receipt WHERE balPayment > 0 AND visitID IN (SELECT id FROM Visit WHERE clinicID = ? AND patientID = ?)";

	public static final String RETRIEVE_ALL_PATIENT_CREDIT_FOR_PRACTICE = "SELECT balPayment, (SELECT patientID FROM Visit WHERE id = visitID) AS patientID FROM Receipt WHERE balPayment > 0 AND userID IN (SELECT id FROM AppUser WHERE practiceID = ?)";

	public static final String RETRIEVE_ALL_PATIENT_CREDIT_FOR_CLINIC = "SELECT balPayment, (SELECT patientID FROM Visit WHERE id = visitID) AS patientID FROM Receipt WHERE balPayment > 0 AND visitID IN (SELECT id FROM Visit WHERE clinicID = ?)";

	public static final String RETRIEVE_STOCK_ID = "SELECT id FROM Stock WHERE stockReceiptID = ? AND productID = ?";

	public static final String INSERT_STOCK_TRANSACTION = "INSERT INTO StockTransaction (stockID, quantity, action, transactionDate) VALUES (?,?,?,DATE(NOW()))";

	public static final String RETRIEVE_FREQUENCY_COUNT = "SELECT count FROM PVFrequency WHERE frequencyValues = ? AND practiceID = ?";

	public static final String RETRIEVE_DISTINCT_COMPOUND = "SELECT distinct(compound) FROM Prescription WHERE visitID = ? AND activityStatus = ? AND isCompound = ?";

	public static final String RETRIEVE_PRODUCT_RATE_TAX_BY_ID = "SELECT sellingPrice, taxPercent FROM Stock WHERE id = ? AND productID = ?";

	public static final String RETRIEVE_OLD_STOCK_ID_BY_PRODUCT_ID = "SELECT id FROM Stock WHERE productID = (SELECT id FROM Product WHERE tradeName = ?) AND status = ? AND clinicID = ? LIMIT 1";

	public static final String RETRIEVE_OTHER_LAB_TEST_LIST = "SELECT * FROM LabInvestigations WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) AND panel = ? ORDER BY id DESC";

	public static final String DELETE_OTHER_LAB_TEST_DETAILS = "DELETE FROM LabInvestigations WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_NET_STOCK1 = "SELECT netStock FROM Stock WHERE id = ? AND productID = (SELECT id FROM Product WHERE tradeName = ?) AND clinicID = ?";

	public static final String RETRIEVE_NEW_STOCK_ID_BY_PRODUCT_ID = "SELECT id FROM Stock WHERE productID = (SELECT id FROM Product WHERE tradeName = ?) AND status = ? AND clinicID = ?";

	public static final String RETRIEVE_PRODUCT_RATE_FOR_NEW_STOCK_ID = "SELECT sellingPrice, taxPercent FROM Stock WHERE id = ? AND productID = ? AND clinicID = ?";

	public static final String UPDATE_PRODUCT_QUANTITYAND_STATUS_FOR_BILLING = "UPDATE Stock SET netStock = netStock - ?, status = ? WHERE id = ? AND productID = ? AND clinicID = ?";

	public static final String UPDATE_RECEIPT_TAX_AMOUNT_AND_OTHER_AMOUNTS = "UPDATE Receipt SET tax = ?, totalAmount = ?, netAmount = ?, advPayment = ?, balPayment = ? WHERE id = ?";

	public static final String RETRIEVE_COMPOUND_DETAILS_BY_PRESCRIPTION_ID = "SELECT numberOfDays, frequency, dosage, compound, isCompound, comment, instruction, section FROM Prescription WHERE id = ?";

	public static final String VERIFY_PRESCRIPTION_ID_EXISTS = "SELECT prescriptionID FROM Transactions WHERE prescriptionID = ?";

	public static final String UPDATE_TRANSACTION_DETAILS = "UPDATE Transactions SET quantity = ?, receiptID = ?, amount = ?, taxAmount = ?, productID = ?, rate = ?, discount = ? WHERE prescriptionID = ?";

	public static final String RETRIEVE_PRODUCT_PREVIOUS_QUANTITY = "SELECT quantity FROM Transactions WHERE receiptID = ? AND prescriptionID = ? AND productID = ?";

	public static final String RETRIEVE_EXISTING_TRANSACTION_LIST_FOR_EDIT_BILLING = "SELECT * FROM Transactions WHERE prescriptionID = ? AND activityStatus = ?";

	public static final String RETRIEVE_RECEIPT_DATE_AND_NO_BY_ID = "SELECT receiptNo, receiptDate FROM Receipt WHERE visitID = ?";

	public static final String INSERT_CURRENT_MEDICATION = "INSERT INTO PrescriptionHistory (drugName, description, tradeName, patientID) VALUES (?,?,?,?)";

	public static final String RETRIEVE_CURRENT_MEDICATION_LIST = "SELECT * FROM PrescriptionHistory WHERE patientID = ? ORDER BY id DESC";

	public static final String RETRIEVE_PATIENT_CLINIC_REGISTRATION_NO = "SELECT regNumber FROM ClinicRegistration WHERE clinicID = ? AND patientID = ?";

	public static final String VERIFY_CLINIC_LOGO_EXISTS = "SELECT id FROM Clinic WHERE id = ? AND logo = ?";

	public static final String VERIFY_RECEIPT_NO_BY_VISIT_ID = "SELECT id FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_PRODUCT_QUANTITY_FROM_STOCK_TRANSACTION = "SELECT quantity FROM StockTransaction WHERE stockID = ?";

	public static final String DISABLE_PATIENT = "UPDATE Patient SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_ACTIVE_PATIENT_LIST_FOR_PRACTICE = "SELECT id, CONCAT(firstName,' ', middleName,' ', lastName) AS patientName, mobile FROM Patient WHERE practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_ACTIVE_PATIENT_LIST_FOR_CLINIC = "SELECT DISTINCT(v.patientID) AS id, (SELECT CONCAT(firstName,' ',lastName) FROM Patient where id = v.patientID AND activityStatus = 'Active') AS patientName, (SELECT mobile FROM Patient WHERE id = v.patientID AND activityStatus = 'Active') AS mobile FROM Visit AS v WHERE v.clinicID IN (?)";

	public static final String RETRIEVE_CLINICIAN_LIST_FOR_CLINIC = "SELECT id, CONCAT(firstName,' ',middleName,' ',lastName) as empName FROM AppUser where defaultClinicID = ? AND userType = ? AND activityStatus = 'Active'";

	public static final String RETRIEVE_CLINICIAN_NAME_BY_ID = "SELECT CONCAT(firstName,' ',middleName,' ',lastName) as empName FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_MY_APPOINTMENT_LIST = "SELECT id, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate ) = MONTH(NOW()) AND DAY(apptDate ) = DAY(NOW()) AND status <> ? AND clinicianID = ? ORDER BY id ASC";

	public static final String RETRIEVE_MY_APPOINTMENT_WEEK_LIST = "SELECT id, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE WEEKOFYEAR(apptDate) = WEEKOFYEAR(NOW()) AND status <> ? AND clinicianID = ? ORDER BY id ASC";

	public static final String RETRIEVE_MY_APPOINTMENT_MONTH_LIST = "SELECT id, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE YEAR(apptDate ) = YEAR(NOW()) AND MONTH(apptDate )=MONTH(NOW()) AND status <> ? AND clinicianID = ? ORDER BY id ASC";

	public static final String RETRIEVE_HEAD_USER_ID_LIST = "SELECT id FROM AppUser WHERE defaultClinicID = ?";

	public static final String RETRIEVE_EMPLOYEE_EMAIL_AND_MOBILE_BY_ID = "SELECT mobile, email FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_LAB_INVESTIGATION1 = "SELECT * FROM LabInvestigations WHERE visitID = ?";

	public static final String RETRIEVE_OTHER_LAB_TEST_LIST_BY_VISIT_ID = "SELECT * FROM LabInvestigations WHERE visitID = ? AND panel = ?";

	public static final String RETRIEVE_VITAL_SIGNS_LIST_BY_VISIT_ID = "SELECT id, weight, visitDate, comment, (SELECT visitDate FROM Visit WHERE id = visitID) AS oldVisitDate FROM  VitalSigns WHERE visitID = ? ORDER BY visitDate DESC";

	public static final String VERIFY_PRESCRIPTION_EXISTS = "SELECT id FROM Prescription WHERE visitID = ?";

	public static final String RETRIEVE_PATIENT_ID_BY_APPOINTMENT_ID = "SELECT distinct(patientID) FROM Appointment WHERE id IN (?)";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST_BY_PATIENT_ID = "SELECT * FROM Prescription WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ? AND isCompound = ?";

	public static final String RETRIEVE_DISTINCT_COMPOUND_BY_PATIENT_ID = "SELECT distinct(compound) FROM Prescription WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ? AND isCompound = ?";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST1_BY_PATIENT_ID = "SELECT * FROM Prescription WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ? AND isCompound = ? AND compound = ?";

	public static final String RETRIEVE_EXISTING_PRESCRIPTION_LIST_BY_PATIENT_ID1 = "SELECT * FROM Prescription WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND activityStatus = ?";

	public static final String RETRIEVE_VISIT_ID_BY_PATIENT_ID = "SELECT id FROM Visit WHERE patientID = ?";

	public static final String RETRIEVE_CLINIC_ID_BY_PATIENTID = "SELECT clinicID FROM ClinicRegistration WHERE patientID = ?";

	public static final String VERIFY_VISIT_EXISTS_FOR_APPOINTMENT = "SELECT id FROM Visit WHERE apptID = ? AND patientID = ?";

	public static final String VERIFY_USER_PASSWORD = "SELECT password from AppUser where password = ? AND id = ?";

	public static final String RETRIEVE_VISIT_TYPE_ID_BY_VISIT_ID = "SELECT visitTypeID FROM Visit WHERE id = ?";

	public static final String UPDATE_APPOINTMENT_STATUS_DONE = "UPDATE Appointment SET status = ? WHERE id = ? AND patientID = ?";

	public static final String RETRIEVE_PRACTICE_ID_LIST = "SELECT id FROM Practice";

	public static final String SEARCH_INSTRUCTION_LIST = "SELECT * FROM PVInstructions WHERE instructions LIKE ?";

	public static final String RETRIEVE_ALL_INSTRUCTION_LIST = "SELECT * FROM PVInstructions";

	public static final String INSERT_INSTRUCTION = "INSERT INTO PVInstructions (instructions) VALUES (?)";

	public static final String UPDATE_INSTRUCTION = "UPDATE PVInstructions SET instructions = ? WHERE id = ?";

	public static final String RETRIEVE_INSTRUCTION_LIST_BY_ID = "SELECT * FROM PVInstructions WHERE id = ?";

	public static final String DELETE_CONFIGURATION_INSTRUCTION = "DELETE FROM PVInstructions WHERE id = ?";

	public static final String INSERT_ON_EXAMINATION = "INSERT INTO OnExamination (description, examinationDate, visitID) VALUES (?,?,?)";

	public static final String RETRIEVE_ON_EXAMINATION_LIST_BY_PATIENT_ID = "SELECT id, description, examinationDate FROM OnExamination WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) ORDER BY id DESC";

	public static final String DELETE_ON_EXAMINATION_DETAILS = "DELETE FROM OnExamination WHERE id = ?";

	public static final String RETRIEVE_FRQUENCY_LIST_BY_CATEGORY = "SELECT frequencyValues FROM PVFrequency WHERE categoryID = ? ORDER BY frequencyValues ASC";

	public static final String RETRIEVE_RASAYAN_PRODUCT_LIST = "SELECT id, tradeName FROM Product WHERE categoryID = ? AND clinicID = ? AND activityStatus = ? ORDER BY tradeName";

	public static final String RETRIEVE_PRESCRIPTION_RASAYAN_LIST = "SELECT pr.id, pr.numberOfDays, pr.frequency, pr.dosage, pr.comment, pr.compound, pr.productID, pr.quantity, pr.isCompound, pr.instruction, pr.discount, pr.rate, pr.section, (SELECT tradeName FROM Product WHERE id = pr.productID) AS product, (SELECT (SELECT name FROM Category WHERE id = pd.categoryID) FROM Product AS pd WHERE pd.id = pr.productID) AS category FROM Prescription AS pr WHERE pr.visitID = ? AND pr.section = ? ORDER BY pr.id DESC";

	public static final String VERIFY_PRESCRIPTION_EXISTS_FOR_VISIT = "SELECT id FROM Prescription WHERE visitID = ?";

	public static final String UPDATE_RASAYU_PRESCRIPTION = "UPDATE Prescription SET productID = ?, numberOfDays = ?, frequency = ?, dosage = ?, comment = ?, activityStatus = ?, quantity = ?, compound = ?, isCompound = ?, instruction = ?, discount = ?, section = ?, rate = ?, consultationCharges = ?  WHERE id = ?";

	public static final String RETRIEVE_DISCTINCT_PRESCRIPTION_COMPOUND_SECTION = "SELECT DISTINCT(compound) FROM Prescription WHERE visitID = ? AND isCompound = ?";

	public static final String RETRIEVE_PRESCRIPTION_COMPOUND_LIST = "SELECT pr.id, pr.numberOfDays, pr.frequency, pr.dosage, pr.comment, pr.compound, pr.productID, pr.quantity, pr.isCompound, pr.instruction, pr.discount, pr.rate, pr.section, (SELECT tradeName FROM Product WHERE id = pr.productID) AS product, (SELECT (SELECT name FROM Category WHERE id = pd.categoryID) FROM Product AS pd WHERE pd.id = pr.productID) AS category, (SELECT count FROM PVFrequency WHERE frequencyValues = pr.frequency) AS freqCount, (SELECT strength FROM Product WHERE id = pr.productID) AS strength FROM Prescription AS pr WHERE pr.visitID = ? AND pr.compound = ? AND pr.isCompound = ? ORDER BY pr.id DESC";

	public static final String RETRIEVE_LAST_VISIT_ID = "SELECT id FROM Visit WHERE patientID = ? AND clinicID = ? AND visitTypeID IN (SELECT id FROM PVVisitType WHERE careType = ? AND clinicID = ?) ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_LAST_VISIT_ID_FOR_EDIT = "SELECT id FROM Visit WHERE patientID = ? AND clinicID = ? ORDER BY id DESC LIMIT 1,1";

	public static final String VERIFY_PRESCRIPTION_EXISTS_FOR_EDIT = "SELECT id FROM Prescription WHERE visitID = ? and id = ?";

	public static final String RETREVE_COMPOUND_TO_GENERATE_COMPOUND_COUNTER = "SELECT compound FROM Prescription WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND isCompound = ? AND compound LIKE ? ORDER BY compound";

	public static final String UPDATE_ATTENDED_BY_IN_APPOINTMENT_FOR_PATIENT = "UPDATE Appointment SET clinicianID = ? WHERE id = ?";

	public static final String RETRIEVE_START_END_HOUR_BY_CLINIC_TIME = "SELECT SUBSTRING_INDEX(clinicStart,':',1) AS startHour, SUBSTRING_INDEX(clinicEnd,':',1) AS endHour FROM Calendar WHERE clinicID = ?";

	public static final String RETRIEVE_LAB_INVESTIGATION_DETAILS = "SELECT id, panel, test, qualitativeValue, quantitativeValue, investigationDate FROM LabInvestigations WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?) AND panel = ?";

	public static final String RETRIEVE_BALANCE_PAYMENT_BY_PATIENT_ID = "SELECT sum(balPayment) as balPayment FROM Receipt WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) AND balPayment > 0";

	public static final String RETRIEVE_LAB_INVESTIGATION_LIST = "SELECT id, investigationDate, qualitativeValue, quantitativeValue FROM LabInvestigations WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) AND test = ? ORDER BY investigationDate DESC";

	public static final String RETRIEVE_PERSONAL_HISTORY_DIAGNOSIS_LIST_FOR_SMS = "SELECT distinct(diagnosis) AS diagnosis FROM PersonalHistory WHERE patientID IN (SELECT id FROM Patient WHERE practiceID = ?) AND diagnosis <> '-1' AND diagnosis <> 'undefined'";

	public static final String RETRIEVE_ON_EXAMINATION_LIST_BY_VISIT_ID = "SELECT id, description, examinationDate FROM OnExamination WHERE visitID = ?";

	public static final String RETRIEVE_LAB_INVESTIGATION_LIST_BY_VISIT_ID = "SELECT id, investigationDate, qualitativeValue, quantitativeValue FROM LabInvestigations WHERE visitID = ? AND test = ? ORDER BY investigationDate DESC";

	public static final String UPDATE_DIAGNOSIS_IN_VISIT = "UPDATE Visit SET diagnosis = ? WHERE id = ?";

	public static final String RETRIEVE_CONSENT_BY_PATIENT_ID = "SELECT consentDocument FROM Consent WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ?)";

	public static final String UPDATE_STOCK_CLINIC = "UPDATE Stock SET clinicID = ? WHERE stockReceiptID = ?";

	public static final String RETRIEVE_ORDER_NO = "SELECT orderNumber FROM Orders WHERE orderNumber LIKE ?";

	public static final String VERIFY_ORDER_CREATED = "SELECT id FROM Orders WHERE orderNumber = ?";

	public static final String VERIFY_APPOINTMENT_EXISTS_FOR_PRACTICE = "SELECT id FROM Appointment WHERE DAY(apptDate) = DAY(now()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?) AND clinicID IN (SELECT id FROM Clinic WHERE practiceID = ?)";

	public static final String RETRIEVE_DEFAULT_CLINIC_ID_LIST = "SELECT distinct(defaultClinicID), practiceID FROM AppUser WHERE activityStatus = ?";

	public static final String VERIFY_APPOINTMENT_EXISTS_FOR_CLINIC = "SELECT id FROM Appointment WHERE DAY(apptDate) = DAY(now()) AND YEAR(apptDate) = YEAR(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND clinicID = ? AND status IN (?,?)";

	public static final String RETRIEVE_DISTINCT_CLINICIAN_ID_LIST = "SELECT distinct(clinicianID) FROM Appointment WHERE clinicID = ?  AND DAY(apptDate) = DAY(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?)";

	public static final String RETRIEVE_APPOINTMENT_COUNT_FOR_CLINICIAN = "SELECT count(*) AS count FROM Appointment WHERE clinicianID = ? AND clinicID = ? AND DAY(apptDate) = DAY(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?)";

	public static final String RETRIEVE_PATIENT_DETAILS_FOR_APPOINTMENT = "SELECT (SELECT concat(firstName, ' ',lastName) FROM Patient WHERE id = patientID) AS patName FROM Appointment WHERE clinicID = ? AND clinicianID = ? AND DAY(apptDate) = DAY(NOW()) AND MONTH(apptDate) = MONTH(NOW()) AND YEAR(apptDate) = YEAR(NOW()) AND status IN (?,?)";

	public static final String RETRIEVE_CLINICIAN_MOBILE_BY_ID = "SELECT mobile, email FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_APPT_DETAILS = "SELECT id, apptDate, TIME_FORMAT(apptTimeFrom,'%r') AS apptTimeFrom, TIME_FORMAT(apptTimeTo,'%r') AS apptTimeTo, walkin, patientID, clinicID, clinicianID, status FROM Appointment WHERE status <> ? AND id = ? ORDER BY apptTimeFrom";

	public static final String VERIFY_NEXT_APPOINTMENT_TAKEN = "SELECT id FROM Appointment WHERE patientID = ? AND apptDate > ?";

	public static final String RETRIEVE_PRODUCT_LIST_TO_BE_ORDERED = "SELECT s.id, s.productID, s.costPrice, s.sellingPrice, s.netStock, s.clinicID, (SELECT name FROM Clinic WHERE id = s.clinicID) AS clinicName, p.tradeName, p.activityStatus, p.minQuantity, (SELECT sr.supplierID FROM StockReceipt AS sr WHERE sr.id = s.stockReceiptID) AS supplierID, (SELECT name FROM Supplier WHERE id = supplierID) AS supplierName FROM Stock AS s, Product AS p WHERE s.productID = p.id AND s.clinicID = p.clinicID AND s.netStock < p.minQuantity AND s.clinicID = ? AND status <> ?";

	public static final String RETRIEVE_PATIENT_COUNTRY_BY_ID = "SELECT country FROM Patient WHERE id = ? AND activityStatus = ?";

	public static final String RETRIEVE_JSP_PAGE_NAME_BY_VISIT_TYPE_ID = "SELECT (SELECT jspPageName FROM FormMapping WHERE formName = w.formName) AS jspPageName FROM PVVisitType AS w WHERE id = ?";

	public static final String RETRIEVE_VISIT_DEATILS_BY_ID1 = "SELECT * FROM Visit WHERE id = ?";

	public static final String RETRIEVE_NEW_VISIT_TYPE_ID_BY_CLINIC_ID = "SELECT id FROM PVVisitType WHERE clinicID = ? AND newVisit = ?";

	public static final String RETRIEVE_VISIT_ID_FOR_NEW_VISIT_TYPE_ID = "SELECT id FROM Visit WHERE patientID = ? AND visitTypeID = ?";

	public static final String RETRIEVE_FOLLOW_UP_VISIT_TYPE_ID = "SELECT id FROM PVVisitType WHERE id = ? AND newVisit = ?";

	public static final String INSERT_VISIT_PRESCRIPTION_DETAILS = "INSERT INTO Prescription (dosage, comment, activityStatus, visitID, tradeName, quantity, numberOfDays,frequency, categoryID) VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_ID = "SELECT pv.billingType, pv.consultationCharges, v.diagnosis FROM PVVisitType AS pv, Visit AS v WHERE pv.id = v.visitTypeID AND pv.id = ? AND v.clinicID = ? AND v.patientID= ? ORDER BY v.id DESC LIMIT 1";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_ID_BK = "SELECT r.id, r.consultationCharges , r.receiptNo, r.receiptDate, r.totalAmount, r.netAmount, r.billingType, v.diagnosis, r.advPayment, r.balPayment, r.paymentType, r.emergencyCharge, r.MLCCharges, r.ambulanceDoctorsCharges, r.totalDiscount FROM Receipt AS r, Visit AS v WHERE v.id = r.visitID AND r.visitID = ? ";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_ID = "SELECT r.id, r.consultationCharges , r.receiptNo, r.receiptDate, r.totalAmount, r.netAmount, r.billingType, r.discountType, v.diagnosis, r.advPayment, r.balPayment, r.paymentType, r.emergencyCharge, r.MLCCharges, r.ambulanceDoctorsCharges, r.totalDiscount, r.productName, r.productID, r.productRate, v.isConsultationDone FROM Receipt AS r, Visit AS v WHERE v.id = r.visitID AND r.visitID = ? ";

	public static final String RETRIEVE_PRODUCT_DEFAULT_STRENGTH = "SELECT strength FROM Product WHERE id = ?";

	public static final String RETRIEVE_CLINIC_ID_BY_VISIT_ID = "SELECT clinicID FROM Visit WHERE id = ?";

	public static final String RETRIEVE_VISIT_DATE_BY_PATIENT_ID = "SELECT visitDate FROM Visit WHERE patientID = ?";

	public static final String RETRIEVE_VISIT_DATE_BY_VISIT_ID = "SELECT visitDate FROM Visit WHERE id = ?";

	public static final String VERIFY_CANCER_TYPE_EXISTS = "SELECT id FROM PVDiagnosis WHERE diagnosis = ? AND isCancerType = ?";

	public static final String INSERT_NEW_CANCER_TYPE_IN_PVDIAGNOSIS = "INSERT INTO PVDiagnosis (diagnosis, isCancerType) VALUES (?,?)";

	public static final String RETRIEVE_CANCER_TYPE_LIST = "SELECT diagnosis FROM PVDiagnosis WHERE isCancerType = ? ORDER BY diagnosis";

	public static final String INSERT_ANUYASH_PATIENT_DETAILS = "INSERT INTO Patient (firstName, middleName, lastName, gender, age, mobile, email, phone, address, city, state, country, practiceID, occupation, practiceRegNumber, activityStatus, profilePic) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_ANUYASH_PATIENT_IDENTIFICATION_DETAILS = "INSERT INTO Identification (idDocument, idNumber, patientID, idDocumentPath) VALUES (?,?,?,?)";

	public static final String UPDATE_ANUYASH_PATIENT_DATA_FORM = "UPDATE Patient SET dataForm = ? WHERE id = ?";

	public static final String UPDATE_ANUYASH_PATIENT_DETAILS = "UPDATE Patient SET firstName = ?, middleName = ?, lastName = ?, gender = ?, age = ?, mobile = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, country = ?, occupation = ?, profilePic = ? WHERE id = ?";

	public static final String UPDATE_ANUYASH_PATIENT_IDENTIFICATION_DETAILS = "UPDATE Identification SET idDocument = ?, idDocumentPath = ? WHERE patientID = ?";

	public static final String INSERT_ANUYASH_CLINIC_REGISTRATION = "INSERT INTO ClinicRegistration (clinicID, regNumber, patientID, userID, patientFormNo) VALUE (?,?,?,?,?)";

	public static final String UPDATE_ANUYASH_CLINIC_REGISTRATION = "UPDATE ClinicRegistration SET patientFormNo = ? WHERE clinicID = ? AND patientID = ?";

	public static final String UPDATE_VISIT_DATE_FOR_RASAYU = "UPDATE Visit SET visitDate = ? WHERE id = ?";

	public static final String VERIFY_TRADE_NAME_EXISTS = "SELECT id FROM Product WHERE tradeName = ?  AND clinicID = ? AND activityStatus = ?";

	public static final String VERIFY_DIAGNOSIS_EXISTS = "SELECT id FROM PVDiagnosis WHERE diagnosis = ?";

	public static final String UPDATE_NEXT_APPOINTMENT_TAKEN = "UPDATE Appointment SET nextApptTaken = ? WHERE id = ? AND patientID = ?";

	public static final String RETRIEVE_REPORT_PAGE_LIST = "SELECT formName, jspPageName FROM FormMapping WHERE careType = ?";

	public static final String RETRIEVE_CLINICIAN_LIST_FOR_CLINIC1 = "SELECT id, CONCAT(firstName,' ',middleName,' ',lastName) as empName FROM AppUser where userType = ?";

	public static final String UPDATE_APPOINTMENT_VISIT_TYPE = "UPDATE Appointment SET visitTypeID = ? WHERE id = ?";

	public static final String INSERT_SAVED_QUERY = "INSERT INTO SavedQuery (query, title, activityStatus, clinicID, userID, joinCondition) VALUES (?,?,?,?,?,?)";

	public static final String RETRIEVE_SAVED_QUERY = "SELECT id, query, title, clinicID, activityStatus, userID, joinCondition, (SELECT username FROM AppUser WHERE id = userID) AS username FROM SavedQuery WHERE clinicID = ? AND activityStatus = ?";

	public static final String Update_SAVED_QUERY = "UPDATE SavedQuery SET title = ? WHERE id = ?";

	public static final String Update_SAVED_QUERY_PARAMETER_VALUES = "UPDATE SavedQueryParameterValues SET searchValue = ? WHERE id = ?";

	public static final String Update_SAVED_QUERY_PARAMETER = "UPDATE SavedQueryParameter SET selectValue = ? WHERE id = ?";

	public static final String RETRIVE_SavedQuery_Values_By_SaveQueryID = "SELECT * FROM SavedQueryParameter WHERE savedQueryID = ?";

	public static final String RETRIVE_SavedQuery_SearchText = "SELECT searchValue FROM SavedQueryParameterValues WHERE savedQueryParameterID IN (SELECT id FROM SavedQueryParameter WHERE savedQueryID = ?) ORDER BY savedQueryParameterID";

	public static final String RETRIVE_Selected_Value_List = "SELECT GROUP_CONCAT(columnName, '__', fieldName SEPARATOR ',') AS selectedValues FROM ReportConfig WHERE tableName = ? AND reportFlag = 1";

	public static final String RETRIVE_ColumnName = "SELECT fieldName FROM ReportConfig WHERE tableName = ? AND columnName = ?";

	public static final String RETRIVE_SavedQuery_SearchValues_By_SaveQueryParameterID = "SELECT GROUP_CONCAT(searchValue, '__', id SEPARATOR '___') AS searchValue FROM SavedQueryParameterValues WHERE savedQueryParameterID = ?";

	public static final String RETRIVE_SAVED_QUERY_ID = "SELECT id FROM SavedQuery WHERE title = ? AND clinicID = ? AND userID = ? AND activityStatus = ?";

	public static final String INSERT_SAVED_QUERY_PARAMETER = "INSERT INTO SavedQueryParameter (tableName, columnName, columnDataType, selectValue, criteria, savedQueryID) VALUES (?,?,?,?,?,?)";

	public static final String RETRIVE_ColumnNames_Of_Table = "SELECT GROUP_CONCAT(CONCAT(?,'.',columnName), '__', fieldName SEPARATOR ',') AS selectedValues FROM ReportConfig WHERE tableName = ? AND reportFlag = 1";

	public static final String RETRIVE_SAVED_QUERY_PARAMETER_ID = "SELECT id FROM SavedQueryParameter WHERE tableName = ? AND columnName = ? AND savedQueryID = ?";

	public static final String INSERT_SAVED_QUERY_PARAMETER_VALUE = "INSERT INTO SavedQueryParameterValues (searchValue, savedQueryParameterID) VALUES (?,?)";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME = "SELECT rc.id, rc.receiptNo, rc.receiptDate, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.receiptNo LIKE ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME_BY_START_END_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME_BY_START_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate LIKE ?";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO_START_END_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.receiptNo LIKE ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO_START_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, (SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.receiptNo LIKE ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate LIKE ?";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_START_END_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_START_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate LIKE ?";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME_BY_START_END_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME_BY_START_DATE = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.patientID IN (SELECT p.id FROM Patient AS p WHERE CONCAT(p.firstName,' ',p.lastName) LIKE ?)) AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate LIKE ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_START_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.receiptDate LIKE ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_START_AND_END_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.receiptNo LIKE ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO_AND_START_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.receiptNo LIKE ? AND sr.receiptDate LIKE ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO_AND_START_AND_END_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.receiptNo LIKE ? AND sr.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.id IN (SELECT stockReceiptID FROM Stock WHERE productID IN (SELECT id FROM Product WHERE tradeName LIKE ?))";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT_START_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.id IN (SELECT stockReceiptID FROM Stock WHERE productID IN (SELECT id FROM Product WHERE tradeName LIKE ?)) AND sr.receiptDate LIKE ?";

	public static final String RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT_START_AND_END_DATE = "SELECT sr.id, sr.receiptNo, sr.receiptDate, sr.totalAmount, sr.tax, sr.balPayment, sr.activityStatus, sr.userID, sr.supplierID, sr.clinicID, (SELECT c.name FROM Clinic AS c WHERE c.id = sr.clinicID) AS clinicName, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.id FROM Clinic AS c WHERE c.id = sr.clinicID)) AS practiceName, (SELECT s.name FROM Supplier AS s WHERE s.id = sr.supplierID) AS supplierName, (SELECT ap.username FROM AppUser AS ap WHERE ap.id = sr.userID) AS username FROM StockReceipt AS sr WHERE sr.supplierID = ? AND sr.clinicID = ? AND sr.id IN (SELECT stockReceiptID FROM Stock WHERE productID IN (SELECT id FROM Product WHERE tradeName LIKE ?)) AND sr.receiptDate BETWEEN ? AND ?";

	public static final String RETRIEVE_STOCK_DETAILS_BY_STOCK_RECEIPT_ID = "SELECT st.id, st.stockDate, st.productID, st.costPrice, st.sellingPrice, st.netStock, st.amount, st.taxInclusive, st.taxName, st.taxPercent, st.taxAmount, st.status, (SELECT p.tradeName FROM Product AS p WHERE p.id = st.productID) as productName FROM Stock AS st WHERE st.stockReceiptID = ?";

	public static final String RETRIEVE_STOCK_DETAILS_BY_STOCK_RECEIPT_ID_AND_STATUS = "SELECT st.id, st.stockDate, st.productID, st.costPrice, st.sellingPrice, st.netStock, st.amount, st.taxInclusive, st.taxName, st.taxPercent, st.taxAmount, st.status, (SELECT p.tradeName FROM Product AS p WHERE p.id = st.productID) as productName FROM Stock AS st WHERE st.stockReceiptID = ? AND st.status = ?";

	public static final String RETRIEVE_DUES_PENDING_RECEIPT_DETAILS = "SELECT id, receiptNo, receiptDate, totalAmount, netAmount, advPayment, balPayment FROM 	Receipt WHERE balPayment > 0 AND visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)";

	public static final String RETRIEVE_PAYMENT_TYPE_BY_RECEIPT_ID = "SELECT paymentType FROM Receipt WHERE id = ?";

	public static final String UPDATE_ADV_BAL_PAYMENT_BY_RECEIPT_ID = "UPDATE Receipt SET advPayment = ?, balPayment = ?, paymentType = ? WHERE id = ?";

	public static final String RETRIEVE_PRESCRIPTION_CONSULTATION_CHARGES = "SELECT consultationCharges FROM Prescription WHERE visitID = ?";

	public static final String RETRIEVE_SALES_REPORT_LIST = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) ORDER BY product";

	public static final String RETRIEVE_SALES_REPORT_LIST_BY_START_DATE = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND rc.receiptDate LIKE ? ORDER BY product";

	public static final String RETRIEVE_SALES_REPORT_LIST_BY_START_AND_END_DATE = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND rc.receiptDate BETWEEN ? AND ? ORDER BY product";

	public static final String RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND tr.productID IN (SELECT id FROM Product WHERE tradeName LIKE ?) ORDER BY product";

	public static final String RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT_AND_START_DATE = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND tr.productID IN (SELECT id FROM Product WHERE tradeName LIKE ?)AND rc.receiptDate LIKE ? ORDER BY product";

	public static final String RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT_AND_START_AND_END_DATE = "SELECT (SELECT tradeName FROM Product WHERE id = tr.productID) AS product, rc.receiptNo, rc.receiptDate, tr.quantity, tr.rate, tr.amount, tr.discount, (SELECT username FROM AppUser WHERE id = rc.userID) AS username, (SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT cl.practiceID FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName, (SELECT cl.name FROM Clinic AS cl WHERE cl.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName FROM Receipt AS rc, Transactions AS tr WHERE rc.id = tr.receiptID AND tr.activityStatus = ? AND rc.visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND tr.productID IN (SELECT id FROM Product WHERE tradeName LIKE ?)AND rc.receiptDate BETWEEN ? AND ? ORDER BY product";

	public static final String RETRIEVE_PRODUCT_STOCK_LIST_TO_REMOVE = "SELECT st.id, st.costPrice, st.sellingPrice, st.netStock, st.productID, (SELECT tradeName FROM Product WHERE id = st.productID) AS product, st.status, str.receiptNo, st.stockReceiptID, str.receiptDate FROM Stock AS st, StockReceipt AS str WHERE st.stockReceiptID = str.id AND st.clinicID = ? AND st.productID = ? AND st.status = ?";

	public static final String RETRIEVE_PRODUCT_QUANTITY_BY_STOCK_ID = "SELECT netStock FROM Stock WHERE id = ?";

	public static final String UPDATE_PRODUCT_STOCK = "UPDATE Stock SET netStock = ?, status = ? WHERE id = ?";

	public static final String RETRIEVE_SECTION_NAME = "SELECT section FROM Prescription WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_TOTAL_NET_STOCK_LIST = "SELECT count(productID), sum(netStock) AS netStock, status, (SELECT tradeName FROM Product WHERE id = productID) AS tradeName, productID  FROM Stock WHERE clinicID = ? AND status = ? GROUP BY productID ORDER BY tradeName";

	public static final String RETRIEVE_STOCK_RECEIPT_DETAILS_BY_PRODUCT_ID = "SELECT str.receiptDate, str.receiptNo, st.netStock FROM Stock AS st, StockReceipt AS str WHERE st.stockReceiptID = str.id AND st.productID = ? and st.status = ? AND st.clinicID = ?";

	public static final String RETRIEVE_LAST_CANCER_TYPE_BY_PATIENT_ID = "SELECT diagnosis FROM Visit WHERE patientID = ? AND diagnosis NOT IN (?,?)";

	public static final String DISABLE_PRODUCT = "UPDATE Product SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_PRODUCT_STOCK_LIST = "SELECT id, netStock FROM Stock WHERE productID = ? AND clinicID = ? AND status = ?";

	public static final String RETRIEVE_RECEIPT_DETAILS_BY_START_AND_END_DATE = "SELECT * FROM Receipt WHERE receiptDate BETWEEN ? AND ? AND visitID IN (SELECT id FROM Visit WHERE clinicID = ?) AND activityStatus = ? ORDER BY receiptDate";

	public static final String RETRIEVE_EXTRA_PAYMENT_BY_PATIENT_ID = "SELECT sum(cashToReturn) AS cashToReturn FROM PaymentDetails WHERE cashAdjStatus = ? AND receiptID in (SELECT id FROM Receipt WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?))";

	public static final String RETRIEVE_ADJUSTED_AMOUNT_BY_VISIT_ID = "SELECT adjAmount, totalDiscount, balPayment, advPayment, netAmount FROM Receipt WHERE visitID = ?";

	public static final String UPDATE_EXTA_PAYMENT_DETAILS_FOR_PATIENT = "UPDATE PaymentDetails SET cashAdjStatus = ?, cashToReturn = ? WHERE receiptID IN (SELECT id FROM Receipt WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)) AND cashAdjStatus = ?";

	public static final String RETRIEVE_CLINIC_WORKDAYS_BY_ID = "SELECT workDays FROM Calendar WHERE clinicID = ?";

	public static final String VERIFY_Category_Name_Already_Exists = "SELECT id FROM Category WHERE name = ?";

	public static final String VERIFY_EDit_Category_Name_Already_Exists = "SELECT id FROM Category WHERE name = ? AND id != ?";

	public static final String VERIFY_VatNumber_Already_Exists = "SELECT id FROM Supplier WHERE vatNumber = ? AND activityStatus = ?";

	public static final String VERIFY_EDit_VatNumber_Already_Exists = "SELECT id FROM Supplier WHERE vatNumber = ? AND activityStatus = ? AND id != ?";

	public static final String VERIFY_Instructions_Already_Exists = "SELECT id FROM PVInstructions WHERE instructions = ?";

	public static final String VERIFY_EDit_Instructions_Already_Exists = "SELECT id FROM PVInstructions WHERE instructions = ? AND id != ?";

	public static final String VERIFY_Tax_Details_Already_Exists = "SELECT id FROM Tax WHERE taxName = ? AND taxPercent = ?";

	public static final String VERIFY_EDit_TaxDeatils_Already_Exists = "SELECT id FROM Tax WHERE taxName = ? AND taxPercent = ? AND id != ?";

	public static final String VERIFY_Frequency_Details_Already_Exists = "SELECT id FROM PVFrequency WHERE frequencyValues = ? AND sortOrder = ? AND practiceID = ? AND activityStatus = ?";

	public static final String VERIFY_EDit_Frequency_Details_Already_Exists = "SELECT id FROM PVFrequency WHERE frequencyValues = ? AND sortOrder = ? AND practiceID = ? AND id != ?";

	public static final String VERIFY_EDit_TradeName_Already_Exists = "SELECT id FROM Product WHERE tradeName = ? AND activityStatus = ? AND id != ?";

	public static final String RETRIEVE_SMS_URL_Details_BY_ID = "SELECT smsUsername, smsPassword, smsURL, smsSenderID, smsApiKey FROM Communication WHERE practiceID = ?";

	public static final String RETRIEVE_ClinicList_For_Practice_BY_PracticeID = "SELECT id, name FROM Clinic WHERE practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_Practice_Details = "SELECT * FROM AppUser WHERE id = ? AND activityStatus = ?";

	public static final String RETRIEVE_PRACTICE_LIST_For_Admin = "SELECT id, name from Practice WHERE id IN(SELECT practiceID FROM AppUser WHERE id = ?) AND activityStatus = ? ORDER BY name";

	public static final String RETRIEVE_ALL_ACTIVE_PRACTICE_LIST = "SELECT * FROM Practice WHERE activityStatus = ?";

	public static final String VERIFY_OPEN_LEAVE_REGISTER = "SELECT id FROM LeaveRegister WHERE practiceID = ? AND status = ?";

	public static final String SEARCH_ALL_PRACTICE_LIST = "SELECT * FROM Practice WHERE name LIKE ? AND activityStatus = ?";

	public static final String RETRIEVE_DIAGNOSIS = "SELECT diagnosis FROM PVDiagnosis WHERE diagnosis LIKE ?";

	public static final String INSERT_VitalSigns_Details = "INSERT INTO VitalSigns (weight, pulse, systolicBP, diastolicBP, visitID, height) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_SymptomCheck_Detail = "INSERT INTO SymptomCheck (symptom, value, visitID) VALUES (?,?,?)";

	public static final String RETRIEVE_SymptomCheck_Details_By_VISIT_ID = "SELECT * FROM SymptomCheck WHERE visitID = ?";

	public static final String RETRIEVE_PresentComplaint_Details_By_VISIT_ID = "SELECT * FROM PresentComplaints WHERE visitID = ?";

	public static final String RETRIEVE_MedicalHistory_Details_By_PATIENT_ID = "SELECT * FROM MedicalHistory WHERE patientID = ?";

	public static final String RETRIEVE_CurrentMedication_Details_By_PATIENT_ID = "SELECT * FROM PrescriptionHistory WHERE patientID = ?";

	public static final String UPDATE_VITALSIGNS_DETAILS = "UPDATE VitalSigns SET weight = ?, pulse = ?, systolicBP = ?, diastolicBP = ?, height = ? WHERE visitID = ?";

	public static final String DELETE_SymptomCheck_DETAILS = "DELETE FROM SymptomCheck WHERE id = ?";

	public static final String DELETE_PresentComplaints_DETAILS = "DELETE FROM PresentComplaints WHERE id = ?";

	public static final String DELETE_MedicalHistory_DETAILS = "DELETE FROM MedicalHistory WHERE id = ?";

	public static final String DELETE_CurrentMedication_DETAILS = "DELETE FROM Prescriptionhistory WHERE id = ?";

	public static final String RETRIEVE_lab_Report_Details_List = "SELECT * FROM Reports WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)";

	public static final String RETRIEVE_LAB_REPORT_FILE_NAME1 = "SELECT report FROM Reports WHERE id = ?";

	public static final String RETRIEVE_PaymentType_By_ReceiptID = "SELECT paymentType FROM Receipt WHERE visitID = ?";

	public static final String VERIFY_VisitType_Name_Already_Exists = "SELECT id FROM PVVisitType WHERE name = ? AND formName = ? AND clinicID = ?";

	public static final String VERIFY_EDit_VisitTypey_Name_Already_Exists = "SELECT id FROM PVVisitType WHERE name = ? AND formName = ? AND clinicID = ? AND id != ?";

	public static final String RETRIEVE_hasConsent_Value = "SELECT hasConsent FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_CONSENTDoc_FileName = "SELECT consentDocument FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_INVESTOGATION1 = "SELECT * FROM LabInvestigations WHERE visitID IN(SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) ORDER BY id DESC LIMIT 1";

	public static final String INSERT_TRADE_NAME_PRODUCT = "INSERT INTO Product (drugName, tradeName, activityStatus, clinicID) VALUES (?,?,?,?)";

	public static final String INSERT_VISIT_DETAILS_FOR_OPTICIAN = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, activityStatus, patientID, apptID, clinicID) VALUES (?,?,?,?,?,?,?)";

	public static final String RETRIEVE_VISIT_ID_FOR_OPTICIAN = "SELECT id FROM Visit WHERE patientID = ? AND clinicID = ? ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_Investigation_TEST_NAME_LIST = "SELECT test FROM PVLabTests ORDER BY test ASC";

	public static final String INSERT_Investigation_TEST_NAME = "INSERT INTO PrescribedInvestigations (investigation, activityStatus, visitID) VALUES (?,?,?)";

	public static final String INSERT_DIAGNOSIS_IN_PVDIAGNOSIS = "INSERT INTO PVDiagnosis (diagnosis) VALUES (?)";

	public static final String VERIFY_Test_NAME_EXISTS = "SELECT id FROM PVLabTests WHERE test = ? AND activityStatus = ?";

	public static final String INSERT_TEST_NAME_PVLabTests = "INSERT INTO PVLabTests (test, activityStatus) VALUES (?,?)";

	public static final String RETREIVE_InvestigationTest_LIST = "SELECT * FROM PrescribedInvestigations WHERE activityStatus = ? AND visitID = ? ORDER BY id ASC";

	public static final String DELETE_INVESTIGATION_DETAILS = "UPDATE PrescribedInvestigations SET activityStatus = ? WHERE id = ? ";
	
	public static final String DELETE_DIAGNOSTIC_DETAILS = "UPDATE PrescribedDiagnostics SET activityStatus = ? WHERE id = ? ";
	
	public static final String DELETE_PROCEDURE_DETAILS = "UPDATE PrescribedProcedures SET activityStatus = ? WHERE id = ? ";

	public static final String UPDATE_Visit_With_NextVisitDays_AND_Advice = "UPDATE Visit SET nextVisitDays = ?, advice = ? WHERE id = ?";

	public static final String RETREIVE_Investigations_Comma_SEPARATED_Test_LIST = "SELECT GROUP_CONCAT(investigation ORDER BY id DESC SEPARATOR ', ')AS investigation FROM PrescribedInvestigations WHERE activityStatus = ? AND visitID = ?";

	public static final String RETRIEVE_Category_With_DrugName = "SELECT categoryID FROM Product WHERE tradeName = ? AND clinicID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PrescriptionID = "SELECT * FROM Prescription WHERE tradeName = ? AND visitID = ? AND activityStatus = ?";

	public static final String INSERT_Frequency_Details = "INSERT INTO Frequency (frequency, numberOfDays, activityStatus, prescriptionID) VALUES (?,?,?,?)";

	public static final String RETRIEVE_FRQUENCY_LIST_By_PrescriptionID = "SELECT * FROM Frequency WHERE prescriptionID = ? AND activityStatus = ?";

	public static final String DELETE_FREQUENCY_DETAILS = "UPDATE Frequency SET activityStatus = ? WHERE id = ? ";

	public static final String VisitType_NewVisit_Check = "SELECT id FROM PVVisitType WHERE newVisit = ? AND id = ?";

	public static final String VERIFY_MEDICAL_CERT = "SELECT * FROM MedicalDocuments WHERE VisitID = ?";

	public static final String VERIFY_REFERRAL_LETTER = "SELECT * FROM MedicalDocuments WHERE VisitID = ?";

	public static final String RETRIEVE_PatientLists = "SELECT DISTINCT(c.patientID) AS id, CONCAT(p.firstName,' ',p.lastName) AS patientName, p.mobile FROM Patient AS p, ClinicRegistration AS c  WHERE p.id = c.patientID AND p.activityStatus = 'Active' AND c.clinicID = ?";

	public static final String INSERT_Consent_Details = "INSERT INTO UsageConsent (consentType, readRule, acceptRule, infoTrue, userID) VALUES (?,?,?,?,?)";

	public static final String VERIFY_Consent_DETAIL = "SELECT * FROM UsageConsent WHERE userID = ?";

	public static final String RETRIEVE_ROOM_TYPE_BY_NAME = "SELECT id, roomType, roomCapacity FROM PVIPDRoomTypes WHERE roomType LIKE ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ROOM_TYPE_LIST = "SELECT id, roomType, roomCapacity FROM PVIPDRoomTypes WHERE activityStatus = ? AND practiceID = ?";

	public static final String INSERT_ROOM_TYPE = "INSERT INTO PVIPDRoomTypes (roomType, activityStatus, practiceID, roomCapacity) VALUES (?,?,?,?)";

	public static final String VERIFY_ROOM_TYPE_EXISTS = "SELECT id, roomType FROM PVIPDRoomTypes WHERE roomType = ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ROOM_TYPE_BY_ID = "SELECT id, roomType, roomCapacity FROM PVIPDRoomTypes WHERE id = ?";

	public static final String UPDATE_ROOM_TYPE = "UPDATE PVIPDRoomTypes SET roomType = ?, roomCapacity = ? WHERE id = ?";

	public static final String VERIFY_ROOM_TYPE_EXISTS1 = "SELECT id, roomType FROM PVIPDRoomTypes WHERE roomType = ? AND activityStatus = ? AND id <> ? AND practiceID = ?";

	public static final String UPDATE_ROOM_TYPE_STATUS = "UPDATE PVIPDRoomTypes SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_IPD_CHARGE_LIST_BY_ITEM = "SELECT id, itemName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDTarrifCharges WHERE itemName LIKE ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ALL_IPD_CHARGE_LIST = "SELECT id, itemName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDTarrifCharges WHERE activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_IPD_CHARGE_EXISTS = "SELECT id, itemName, charges FROM PVIPDTarrifCharges WHERE itemName = ? AND activityStatus = ? AND roomTypeID = ? AND practiceID = ?";

	public static final String INSERT_IPD_CHARGES = "INSERT INTO PVIPDTarrifCharges (itemName, charges, activityStatus, roomTypeID, practiceID) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_IPD_CHARGE_LIST_BY_ID = "SELECT id, itemName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDTarrifCharges WHERE id = ?";

	public static final String VERIFY_IPD_CHARGE_EXISTS1 = "SELECT id, itemName, charges FROM PVIPDTarrifCharges WHERE itemName = ? AND activityStatus = ? AND roomTypeID = ? AND id <> ? AND practiceID = ?";

	public static final String UPDATE_IPD_CHARGES = "UPDATE PVIPDTarrifCharges SET itemName = ?, charges = ?, roomTypeID = ? WHERE id = ?";

	public static final String UPDATE_IPD_CHARGE_STATUS = "UPDATE PVIPDTarrifCharges SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_IPD_CONSULTANT_CHARGE_LIST_BY_DOCTOR_NAME = "SELECT id, doctorName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDConsultantCharges WHERE doctorName LIKE ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ALL_IPD_CONSULTANT_CHARGE_LIST = "SELECT id, doctorName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDConsultantCharges WHERE activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_IPD_CONSULTANT_CHARGE_EXISTS = "SELECT id, doctorName, charges FROM PVIPDConsultantCharges WHERE doctorName = ? AND activityStatus = ? AND roomTypeID = ? AND practiceID = ?";

	public static final String INSERT_IPD_CONSULTANT_CHARGES = "INSERT INTO PVIPDConsultantCharges (doctorName, charges, activityStatus, roomTypeID, practiceID) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_IPD_CONSULTANT_CHARGE_LIST_BY_ID = "SELECT id, doctorName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDConsultantCharges WHERE id = ?";

	public static final String VERIFY_IPD_CONSULTANT_CHARGE_EXISTS1 = "SELECT id, doctorName, charges FROM PVIPDConsultantCharges WHERE doctorName = ? AND activityStatus = ? AND roomTypeID = ? AND id <> ? AND practiceID = ?";

	public static final String UPDATE_IPD_CONSULTANT_CHARGES = "UPDATE PVIPDConsultantCharges SET doctorName = ?, charges = ?, roomTypeID = ? WHERE id = ?";

	public static final String UPDATE_IPD_CONSULTANT_CHARGE_STATUS = "UPDATE PVIPDConsultantCharges SET activityStatus = ? WHERE id = ?";

	public static final String SEARCH_Test_LIST = "SELECT * FROM PVLabTests WHERE test LIKE ?";

	public static final String RETRIEVE_Test_LIST = "SELECT * FROM PVLabTests WHERE id IN(SELECT testID FROM PVGroupLabTest WHERE practiceID = ?)";

	public static final String RETRIEVE_CONFIGURATION_Test_LIST_BY_ID = "SELECT * FROM PVLabTests WHERE id = ?";

	public static final String VERIFY_Test_Already_Exists = "SELECT id FROM PVLabTests WHERE test = ?";

	public static final String INSERT_TEST_DETAILS = "INSERT INTO PVLabTests (test, rate, panel, normalValues, groupName) VALUE (?,?,?,?,?)";

	public static final String VERIFY_Edit_Test_Already_Exists = "SELECT id FROM PVLabTests WHERE test = ? AND id != ? ";

	public static final String UPDATE_CONFIGURATION_Tests = "UPDATE PVLabTests SET test = ?, rate = ?, panel = ?, normalValues = ?, groupName = ? WHERE id = ?";

	public static final String SEARCH_OPDCharges = "SELECT * FROM PVOPDCharges WHERE chargeType LIKE ? AND practiceID = ?";

	public static final String RETRIEVE_ALL_OPDCharges = "SELECT * FROM PVOPDCharges WHERE practiceID = ?";

	public static final String RETRIEVE_ALL_IPDCharges_FOR_PRACTICE = "SELECT id, itemName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDTarrifCharges WHERE activityStatus = ? AND roomTypeID = ? AND practiceID = ?";

	public static final String VERIFY_CHARGETYPE_Already_Exists = "SELECT id FROM PVOPDCharges WHERE chargeType = ? AND practiceID = ?";

	public static final String INSERT_OPDChargeDetails = "INSERT INTO PVOPDCharges (chargeType, charges, practiceID) VALUES (?,?,?)";

	public static final String RETRIEVE_OPDCharge_Details_BY_ID = "SELECT id, chargeType, charges FROM PVOPDCharges WHERE id = ?";

	public static final String VERIFY_EDit_ChargeType_Already_Exists = "SELECT id FROM PVOPDCharges WHERE chargeType = ? AND id != ? AND practiceID = ?";

	public static final String UPDATE_OPDCharges_Details = "UPDATE PVOPDCharges SET chargeType  = ?, charges = ? WHERE id = ?";

	public static final String DELETE_OPDCharges = "DELETE FROM PVOPDCharges WHERE id = ?";

	public static final String RETRIEVE_LAST_ENTERED_VISIT_ID_BY_VISIT_NUMBER1 = "SELECT id FROM Visit WHERE visitNumber = ? AND patientID = ? AND clinicID = ?";

	public static final String INSERT_VISIT_DETAILS1 = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, clinicID, category, categoryType) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_MEDICAL_HISTORY_DETAILS = "UPDATE MedicalHistory SET diagnosis = ?, description = ?, activityStatus = ?, otherDetails = ?, diabetesMellitus = ?, diabetesMellitusDuration = ?, diabetesMellitusDesc = ?, hypertension = ?, hypertensionDuration = ?, hypertensionDesc = ?, asthema = ?, asthemaDuration = ?, asthemaDesc = ?, ischemicHeartDisease = ?, ischemicHeartDiseaseDuration = ?, ischemicHeartDiseaseDesc = ?, allergies = ?, allergiesDuration = ?, allergiesDesc = ?, surgicalHistory = ?, surgicalHistoryDuration = ?, surgicalHistoryDesc = ?, gynecologyHistory = ?, gynecologyHistoryDuration = ?, gynecologyHistoryDesc = ? WHERE patientID = ?";

	public static final String INSERT_MEDICAL_HISTORY_DETAILS = "INSERT INTO MedicalHistory (diagnosis, description, activityStatus, otherDetails, patientID, diabetesMellitus, diabetesMellitusDuration, diabetesMellitusDesc, hypertension, hypertensionDuration, hypertensionDesc, asthema, asthemaDuration, asthemaDesc, ischemicHeartDisease, ischemicHeartDiseaseDuration, ischemicHeartDiseaseDesc, allergies, allergiesDuration, allergiesDesc, surgicalHistory, surgicalHistoryDuration, surgicalHistoryDesc, gynecologyHistory, gynecologyHistoryDuration, gynecologyHistoryDesc) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_FAMILY_HISTORY_DETAILS = "UPDATE FamilyHistory SET diagnosis = ?, description = ?, activityStatus = ?, otherDetails = ?, diabetesMellitus = ?, diabetesMellitusDuration = ?, diabetesMellitusDesc = ?, hypertension = ?, hypertensionDuration = ?, hypertensionDesc = ?, asthema = ?, asthemaDuration = ?, asthemaDesc = ?, allergies = ?, allergiesDuration = ?, allergiesDesc = ?WHERE patientID = ?";

	public static final String INSERT_FAMILY_HISTORY_DETAILS = "INSERT INTO FamilyHistory (diagnosis, description, activityStatus, otherDetails, patientID, diabetesMellitus, diabetesMellitusDuration, diabetesMellitusDesc, hypertension, hypertensionDuration, hypertensionDesc, asthema, asthemaDuration, asthemaDesc, allergies, allergiesDuration, allergiesDesc) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_PVComplaints = "INSERT INTO PVComplaints (complaint) VALUES (?)";

	public static final String INSERT_VITAL_SIGNS = "INSERT INTO VitalSigns (pulse, systolicBP, diastolicBP, respiratorySystem, cardioVascularSystem, visitDate, weight, height, visitID, comment, temperature, respiration, pallor, icterus, abdominalCircumference, bmi) VALUES (?,?,?,?,?,(SELECT visitDate FROM Visit WHERE id = ?),?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_PRESENT_HISTORY = "INSERT INTO PresentComplaints (complaints, visitID) VALUES (?,?)";

	public static final String INSERT_ON_EXAMINATION_DETAILS = "INSERT INTO OnExamination (description, examinationDate, visitID, rhonchi, crepitation, clear, s1s2, murmur, murmurSystolic, murmurDiastolic, cvsWNL, cnsWNL, cnsOther, abdomen, rightHypochondriac, leftHypochondriac, epigastric, rightLumbar, leftLumbar, rightIliac, leftIliac, hypogastric, umbilical) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_INVESTIGATION = "INSERT INTO Investigations (findings, visitID, findingDate, findingType, reportFile) VALUES (?,?,?,?,?)";

	public static final String INSERT_VISIT_STATUS = "INSERT INTO VisitStatus (visitID, apptID, apptStatus, inTime, outTime, patientID,clinicID) VALUES (?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PATIENT_FIRST_VISIT_DATE = "SELECT visitDate, diagnosis FROM Visit WHERE patientID = ? AND clinicID = ? LIMIT 1";

	public static final String RETRIEVE_MEDICAL_HISTORY_BY_PATIENT_ID = "SELECT * FROM MedicalHistory WHERE patientID = ?";

	public static final String RETRIEVE_FAMILY_HISTORY_BY_PATIENT_ID = "SELECT * FROM FamilyHistory WHERE patientID = ?";

	public static final String RETRIEVE_PATIENT_VITALS_FOR_NEW_VISIT = "SELECT * FROM VitalSigns WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)";

	public static final String RETRIEVE_CVS_CNS_DETAILS_FOR_NEW_VISIT = "SELECT murmurSystolic, murmurDiastolic, cnsOther FROM OnExamination WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?)";

	public static final String RETRIEVE_SIGNIFICANT_FINDINGS_DETAILS_FOR_PATIENT = "SELECT * FROM Investigations WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) AND findingType = ?";

	public static final String RETRIEVE_Physiotherapy_FOR_PATIENT = "SELECT * FROM Physiotherapy WHERE visitID IN (SELECT id FROM Visit WHERE patientID = ? AND clinicID = ?) AND activityStatus = ?";

	public static final String RETRIEVE_OTHER_COMPLAINT_LIST_FOR_EXISTING_VISIT_PATIENT = "SELECT id, complaints, complaintsType, visitID FROM PresentComplaints WHERE visitID = ?";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_ID1 = "SELECT * FROM Receipt WHERE visitID = ?";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_ID1 = "SELECT * FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_OPDCharges_Details_By_ReceiptID = "SELECT GROUP_CONCAT(id, '#', chargeType, '#', quantity, '#', rate, '#', amount)AS Details FROM Transactions WHERE receiptID IN(SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String RETRIEVE_PrescribedInvestigations_LIST_BY_VISIT_ID = "SELECT * FROM PrescribedInvestigations WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_PRESENT_COMPLAINT_LIST_FOR_LAST_VISIT = "SELECT * FROM PresentComplaints WHERE visitID = ? AND complaintsType <> ?";

	public static final String RETRIEVE_PVComplaints_LIST = "SELECT * FROM PVComplaints ORDER BY id ASC";

	public static final String RETRIEVE_CATEGORY_LIST_FOR_LAST_VISIT = "SELECT category, categoryType FROM Visit WHERE id = ? ";

	public static final String RETRIEVE_PERSONAL_HISTORY_BY_PATIENT_ID1 = "SELECT * FROM PersonalHistory WHERE patientID = ?";

	public static final String RETRIEVE_ON_EXAMINATION_LIST_BY_VISIT_ID1 = "SELECT * FROM OnExamination WHERE visitID = ?";

	public static final String RETREIVE_GeneralHospital_PRESCRIPTION_LIST = "SELECT p.id, p.dosageAfterMeal, p.dosageBeforeMeal, p.dosageAfterDinner, p.comment, p.duration, pr.tradeName FROM Product AS pr, Prescription AS p WHERE  pr.id = p.productID AND pr.categoryId IN(SELECT id FROM Category WHERE name = ?) AND p.activityStatus = ? AND p.visitID = ?";

	public static final String RETRIEVE_Injection_LIST = "SELECT id, tradeName FROM Product WHERE clinicID = ? AND categoryID IN(SELECT id FROM Category WHERE name = ?) AND activityStatus = ?";

	public static final String RETRIEVE_FRQUENCY_LIST1 = "SELECT frequencyValues FROM PVFrequency ORDER BY id ASC";

	public static final String UPDATE_GeneralHospital_VITAL_SIGNS_WEIGHT = "UPDATE VitalSigns SET weight = ? WHERE visitID = ?";

	public static final String INSERT_GeneralHospital_VITAL_SIGNS_WEIGHT = "INSERT INTO VitalSigns (weight, visitID) VALUES (?,?)";

	public static final String UPDATE_GeneralHospital_Prescribed_Investigations = "UPDATE PrescribedInvestigations SET investigation = ? WHERE activityStatus = ? AND visitID = ?";

	public static final String INSERT_GeneralHospital_Prescribed_Investigations = "INSERT INTO PrescribedInvestigations (investigation,activityStatus,visitID) VALUES (?,?,?)";

	public static final String UPDATE_GeneralHospital_Physiotherapy = "UPDATE Physiotherapy SET physiotherapy =? WHERE visitID = ? AND activityStatus = ?";

	public static final String INSERT_GeneralHospital_Physiotherapy = "INSERT INTO Physiotherapy (physiotherapy, activityStatus, visitID) VALUES (?,?,?)";

	public static final String Update_VISIT_DETAILS = "UPDATE Visit SET nextVisitDays = ?, nextVisitWeeks = ?, nextVisitMonths = ? WHERE id =?";

	public static final String INSERT_GeneralHospital_VISIT_PRESCRIPTION_DETAILS = "INSERT INTO Prescription (productID, dosageBeforeMeal, dosageAfterMeal, duration, dosageAfterDinner, comment, activityStatus, visitID) VALUES (?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PRESENTCOMPLAINTS = "SELECT * FROM PresentComplaints WHERE visitID = ?";

	public static final String RETRIEVE_PATIENT_VITALS_FOR_EXISTTING_VISIT = "SELECT * FROM VitalSigns WHERE visitID = ?";

	public static final String RETRIEVE_SIGNIFICANT_FINDINGS_DETAILS_FOR_EXISTING_PATIENT = "SELECT * FROM Investigations WHERE visitID = ? AND findingType = ?";

	public static final String RETRIEVE_Physiotherapy_FOR_EXISTING_PATIENT = "SELECT * FROM Physiotherapy WHERE visitID = ? AND activityStatus = ?";

	public static final String UPDATE_TRANSACTION_DETAILS1 = "UPDATE Transactions SET quantity = ?, amount = ? WHERE id = ?";

	public static final String INSERT_TRANSACTION_DETAILS1 = "INSERT INTO Transactions (chargeType, quantity, rate, amount, activityStatus, receiptID) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_BILL_DETAIL1 = "INSERT INTO Receipt (receiptNo, receiptDate, billingType, consultationCharges, totalAmount, totalDiscount, netAmount, advPayment, balPayment, paymentType, visitID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_GeneralHospital_LAB_REPORT_FILE_NAME = "SELECT report FROM Reports WHERE id = ?";

	public static final String RETRIEVE_IPD_CHARGES_BY_VISIT_ID_AND_TYPE = "SELECT id, chargeType, chargeName, rate, quantity, amount, visitID, (SELECT patientID FROM Visit WHERE id = visitID) AS patientID FROM IPDCharges WHERE visitID = ? AND chargeType = ? ORDER BY id DESC";

	public static final String RETRIEVE_OPD_CHARGES_BY_VISIT_ID = "SELECT id, operationName, DATE_FORMAT(operationDateTime, '%d-%m-%Y %H:%i') AS operationDateTime, consultantName, anaesthetistName, OTAssistantName, rate, visitID, (SELECT patientID FROM Visit WHERE id = visitID) AS patientID FROM OTcharges WHERE visitID = ? ORDER BY id DESC";

	public static final String VERIFY_DISCHARGE_IPD_VISIT_TYPE = "SELECT id FROM PVVisitType WHERE id = ? AND isDischarge = ?";

	public static final String RETRIEVE_NEW_VISIT_REF = "SELECT newVisitRef FROM Visit WHERE id = ?";

	public static final String RETRIEVE_IPD_TRANSACTIONS_BY_VISIT_ID = "SELECT * FROM Transactions WHERE receiptID IN (SELECT id FROM Receipt WHERE visitID = ? AND activityStatus = ?) AND activityStatus = ? AND chargeType = ?";

	public static final String RETRIEVE_FINAL_IPD_CHARGES_BY_NEW_VISIT_ID_AND_CHARGE_TYPE = "SELECT rate, sum(quantity) as quantity, chargeName, chargeType, sum(amount) AS amount FROM IPDCharges WHERE visitID IN (SELECT id FROM Visit WHERE newVisitRef = ? or id = ?) AND chargeType = ? GROUP BY chargeName";

	public static final String RETRIEVE_FINAL_OT_CHARGES_BY_NEW_VISIT_ID = "SELECT id, operationName, DATE_FORMAT(operationDateTime, '%d-%m-%Y %H:%i:%s') AS operationDateTime, consultantName, anaesthetistName, OTAssistantName, rate, visitID FROM OTcharges WHERE visitID IN (SELECT id FROM Visit WHERE newVisitRef = ? OR id = ?)";

	public static final String RETRIEVE_IPD_TOTAL_CHARGE_BY_NEW_VISIT_ID = "SELECT SUM(amount) AS totalRate FROM IPDCharges WHERE visitID IN (SELECT id FROM Visit WHERE newVisitRef = ? OR id = ?)";

	public static final String RETRIEVE_OT_TOTAL_CHARGE_BY_NEW_VISIT_ID = "SELECT SUM(rate) AS totalRate FROM OTcharges WHERE visitID IN (SELECT id FROM Visit WHERE newVisitRef = ? OR id = ?)";

	public static final String RETRIEVE_TOTAL_IPD_ADVANCE_PAYMENT = "SELECT SUM(advancePayment) AS totalAdvancePayment, SUM(emergencyPayment) AS totalEmergencyPayment, SUM(MLCCharges) AS MLCCharges, SUM(ambulanceDoctorsCharges) AS ambulanceDoctorsCharges FROM Visit WHERE id = ? OR newVisitRef = ?";

	public static final String RETREIVE_PATIENT_LIST_BY_PATIENT_ID1 = "SELECT id, firstName, middleName, lastName, age, gender, address, mobile, dateOfBirth, occupation, ec, practiceRegNumber, email, phone, profilePic FROM Patient WHERE id = ?";

	public static final String RETRIEVE_TradeName_BY_ProductID = "SELECT id FROM Product WHERE tradeName = ? AND categoryID IN(SELECT id FROM Category WHERE name= ?) AND activityStatus = ?";

	public static final String DELETE_GeneralHospital_Presc_Row = "UPDATE Prescription SET activityStatus = ? WHERE id = ?";

	public static final String DELETE_BILL_DETAILS = "UPDATE Billing SET activityStatus = ? WHERE id = ? ";

	public static final String RETRIEVE_VISIT_DEATILS_BY_ID3 = "SELECT * FROM Visit WHERE patientID = ? AND clinicID = ?";

	public static final String RETRIEVE_Existing_VISIT_DEATILS_BY_VISIT_ID = "SELECT id, diagnosis, category, categoryType, nextVisitDays, nextVisitWeeks, nextVisitMonths, visitDate FROM Visit WHERE id = ? AND activityStatus = ?";

	public static final String RETRIEVE_CVS_CNS_DETAILS_FOR_EXISTING_VISIT = "SELECT murmurSystolic, murmurDiastolic, cnsOther FROM OnExamination WHERE visitID = ?";

	public static final String RETRIEVE_INVESTIGATION_LIST_BY_VISIT_ID = "SELECT * FROM Investigations WHERE visitID = ? AND findingType = ? ORDER BY findingDate DESC";

	public static final String UPDATE_GeneralHospital_VISIT = "UPDATE Visit SET visitTypeID = ?, visitDate = ?, diagnosis = ?, category = ?, categoryType = ?, clinicID = ? WHERE id = ?";

	public static final String UPDATE_PRESENT_HISTORY = "UPDATE PresentComplaints SET complaints = ? WHERE visitID = ?";

	public static final String UPDATE_VITAL_SIGNS = "UPDATE VitalSigns SET pulse = ?, systolicBP = ?, diastolicBP = ?, respiratorySystem = ?, cardioVascularSystem = ?, visitDate = ?, weight = ?, height = ?, comment = ?, temperature = ?, respiration = ?, pallor = ?, icterus = ?, abdominalCircumference = ?, bmi = ? WHERE visitID = ?";

	public static final String UPDATE_ON_EXAMINATION_DETAILS = "UPDATE OnExamination SET description = ?, examinationDate = ?, rhonchi = ?, crepitation = ?, clear = ?, s1s2 = ?, murmur = ?, murmurSystolic = ?, murmurDiastolic = ?, cvsWNL = ?, cnsWNL = ?, cnsOther = ?, abdomen = ?, rightHypochondriac = ?, leftHypochondriac = ?, epigastric = ?, rightLumbar = ?, leftLumbar = ?, rightIliac = ?, leftIliac = ?, hypogastric = ?, umbilical = ? WHERE visitID = ?";

	public static final String UPDATE_INVESTIGATION = "UPDATE Investigations SET findings = ?, findingType = ? WHERE visitID = ?";

	public static final String RETRIEVE_OT_CHARGES_DISBURSEMENT_LIST = "SELECT id, chargeType, charges, otChargeID, (SELECT patientID FROM Visit WHERE id = (SELECT visitID FROM OTcharges WHERE id = otChargeID)) AS patientID FROM OTChargesDisbursement WHERE otChargeID = ?";

	public static final String VERIFY_NEW_VISIT_TYPE = "SELECT id FROM PVVisitType WHERE id = ? AND newVisit = ?";

	public static final String RETRIEVE_LAST_NEW_VISIT = "SELECT id FROM Visit WHERE patientID = ? AND clinicID = ? AND activityStatus = ? AND visitTypeID = (SELECT id FROM PVVisitType WHERE newVisit = ? AND clinicID = ?) ORDER BY id DESC LIMIT 1";

	public static final String INSERT_IPD_VISIT_DETAILS = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, clinicID, category, categoryType, advancePayment,emergencyPayment, roomTypeID, MLCCharges, ambulanceDoctorsCharges) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_IPD_CHARGES_DETAILS = "INSERT INTO IPDCharges (chargeType, chargeName, rate, quantity, amount, visitID) VALUES (?,?,?,?,?,?)";

	public static final String INSERT_OT_CHARGES = "INSERT INTO OTcharges (operationName, operationDateTime, consultantName, anaesthetistName, OTAssistantName, rate, visitID) VALUES (?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LAST_OT_CHARGE_ID = "SELECT id FROM OTcharges WHERE visitID = ? AND operationName = ? ORDER BY id DESC LIMIT 1";

	public static final String INSERT_CHARGE_DISBURSEMENT = "INSERT INTO OTChargesDisbursement (chargeType, charges, otChargeID) VALUES (?,?,?)";

	public static final String RETRIEVE_PATIENT_LAST_DIAGNOSIS = "SELECT id, diagnosis FROM Visit WHERE patientID = ? AND clinicID = ? ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_PATIENT_FIRST_VISIT_DATE1 = "SELECT visitDate FROM Visit WHERE patientID = ? AND clinicID = ? AND visitTypeID = (SELECT id FROM PVVisitType WHERE newVisit = ? AND clinicID = ? AND careType = ?) ORDER BY id DESC LIMIT 1";

	public static final String RETRIEVE_BILLING_TYPE_BY_VISIT_TYPE_ID = "SELECT billingType FROM PVVisitType WHERE id = ?";

	public static final String UPDATE_IPD_RECEIPT = "UPDATE Receipt SET receiptDate = receiptDate, consultationCharges = ?, totalAmount = ?, adjAmount = ?, tax = ?, netAmount = ?, advPayment = ?, balPayment = ?, paymentType = ?, billingType = ?, totalDiscount = ?, emergencyCharge = ?, userID = ?, MLCCharges = ?, ambulanceDoctorsCharges = ? WHERE visitID = ?";

	public static final String UPDATE_IPD_TRANSACTIONS = "UPDATE Transactions SET quantity = ?, amount = ?, discount = ?, taxAmount = ?, rate = ?, chargeType = ?, IPDCharge = ?, otDateTime = ? WHERE id = ?";

	public static final String INSERT_IPD_TRANSACTIONS = "INSERT INTO Transactions (quantity, receiptID, amount, discount, taxAmount, activityStatus, rate, chargeType, IPDCharge, otDateTime) VALUES (?,?,?,?,?,?,?,?,?,?)";

	public static final String VERIFY_PAYMENT_DETAILS_EXISTS = "SELECT id FROM PaymentDetails WHERE receiptID = ?";

	public static final String INSERT_IPD_RECEIPT = "INSERT INTO Receipt (receiptNo, receiptDate, consultationCharges, totalAmount, adjAmount, tax, netAmount, advPayment, balPayment, paymentType, billingType, activityStatus, visitID, userID, totalDiscount, emergencyCharge, MLCCharges, ambulanceDoctorsCharges) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_ADMISSION_VISIT_DATE_TIME = "SELECT DATE_FORMAT(visitDate, '%d-%m-%Y') AS visitDate, visitTimeFrom FROM Visit WHERE id = ?";

	public static final String RETRIEVE_DISCHARGE_DATE = "SELECT DATE_FORMAT(visitDate, '%d-%m-%Y') AS visitDate FROM Visit WHERE newVisitRef = ? AND visitTypeID = (SELECT id FROM PVVisitType WHERE isDischarge = ? AND clinicID = ?)";

	public static final String RETRIEVE_VISIT_ID_LIST = "SELECT id, DATE_FORMAT(visitDate, '%d-%m-%Y') AS visitDate, visitTypeID, advancePayment, emergencyPayment, roomTypeID, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType, MLCCharges, ambulanceDoctorsCharges FROM Visit WHERE id = ? OR newVisitRef = ?";

	public static final String RETRIEVE_IPD_TARIFF_CHARGE_LIST = "SELECT id, itemName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDTarrifCharges WHERE activityStatus = ? AND roomTypeID = ?";

	public static final String RETRIEVE_IPD_CONSULTANT_CHARGE_LIST = "SELECT id, doctorName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVIPDConsultantCharges WHERE activityStatus = ? AND roomTypeID = ?";

	public static final String RETRIEVE_PATIENT_DETAILS_FOR_PDF1 = "SELECT id, firstName, middleName, lastName, address, mobile FROM Patient WHERE id = ?";

	public static final String UPDDATE_IPD_VISIT_DETAILS = "UPDATE Visit SET visitDate = ?, diagnosis = ?, advancePayment = ?,emergencyPayment = ?, roomTypeID = ?, MLCCharges = ?, ambulanceDoctorsCharges = ? WHERE id = ?";

	public static final String UPDATE_IPD_CHARGES_DETAILS = "UPDATE IPDCharges SET chargeType = ?, chargeName = ?, rate = ?, quantity = ?, amount = ? WHERE id = ?";

	public static final String UPDATE_OT_CHARGES = "UPDATE OTcharges SET operationName = ?, operationDateTime = ?, consultantName = ?, anaesthetistName = ?, OTAssistantName = ?, rate = ? WHERE id = ?";

	public static final String UPDATE_PERSONAL_HISTORY1 = "UPDATE PersonalHistory SET smoking = ?, smokingDetails = ?, diet = ?, vegadharanaPurisha = ?, vegadharanaMootra = ?, vegadharanaKshudha = ?, vegadharanaNidra = ?, vegadharanaOther = ?, other = ?, diagnosis = ?, description = ?, otherDetails = ? WHERE patientID = ?";

	public static final String INSERT_PERSONAL_HISTORY1 = "INSERT INTO PersonalHistory (smoking, smokingDetails, diet, vegadharanaPurisha, vegadharanaMootra, vegadharanaKshudha, vegadharanaNidra, vegadharanaOther, patientID, other, diagnosis, description, otherDetails) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String VERIFY_NEW_OR_FOLLOW_UP_VISIT_FOR_PATIENT = "SELECT v.id FROM Visit AS v WHERE v.visitTypeID IN (SELECT id FROM PVVisitType WHERE newVisit IN (?,?) and clinicID = ? AND isDischarge = ?) and v.id = ?";

	public static final String RETRIEVE_IPD_VISIT_TYPE_EXCEPT_NEW_VISIT = "SELECT * FROM PVVisitType WHERE clinicID = ? AND newVisit = ?";

	public static final String RETRIEVE_NON_IPD_VISIT_TYPE = "SELECT * FROM PVVisitType WHERE clinicID = ?";

	public static final String INSERT_General_Clinic_VISIT_DETAILS = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, clinicID, lastMenstrualPeriod, estimatedDueDate, advice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_MEDICAL_HISTORY_DETAILS1 = "UPDATE MedicalHistory SET comments = ? WHERE patientID = ?";

	public static final String INSERT_MEDICAL_HISTORY_DETAILS1 = "INSERT INTO MedicalHistory (comments, patientID) VALUES (?,?)";

	public static final String INSERT_PRESENT_HISTORY1 = "INSERT INTO PresentComplaints (comments, visitID) VALUES (?,?)";

	public static final String INSERT_ON_EXAMINATION_DETAILS1 = "INSERT INTO OnExamination (description, visitID) VALUES (?,?)";

	public static final String UPDATE_General_Clinic_VISIT_DETAILS = "UPDATE Visit SET visitDate = ?, diagnosis = ?, nextVisitDays = ?, lastMenstrualPeriod = ?, estimatedDueDate = ?, advice = ? WHERE id= ?";

	public static final String UPDATE_PRESENT_HISTORY1 = "UPDATE PresentComplaints SET comments = ? WHERE visitID = ?";

	public static final String UPDATE_ON_EXAMINATION_DETAILS1 = "UPDATE OnExamination SET description = ? WHERE visitID = ?";

	public static final String INSERT_General_IPD_Clinic_VISIT_DETAILS = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, clinicID, treatment, advice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_General_IPD_Clinic_VISIT_DETAILS = "UPDATE Visit SET visitDate = ?, diagnosis = ?, nextVisitDays = ?, treatment = ?, advice = ? WHERE id= ?";

	public static final String UPDATE_NET_STOCK = "UPDATE Stock SET netStock = ? WHERE id = ?";

	public static final String VERIFY_PRODUCT_ID_EXISTS = "SELECT id, netStock FROM Stock WHERE productID = ?  AND clinicID = ?";

	public static final String UPDATE_STOCK = "UPDATE Stock SET netStock = ? WHERE id = ?";

	public static final String RETIREVE_NETSTOCK_BY_STOCK_ID = "SELECT netStock FROM Stock WHERE id = ?";

	public static final String RETRIEVE_TOTAL_STOCK = "SELECT SUM(netStock) AS netStock FROM Stock WHERE productID = (SELECT id FROM Product WHERE tradeName = ? AND clinicID = ?)";

	public static final String UPDATE_NET_STOCK_AND_STATUS = "UPDATE Stock SET netStock = ? , status = ? WHERE id = ?";

	public static final String UPDATE_NET_STOCK_WHEN_ROW_DELETE = "UPDATE Stock SET netStock = netStock + ? WHERE id = ?";

	public static final String RETRIEVE_Patient_Details_LIST_BY_Patient_NAME = "SELECT p.id as id, CONCAT(p.firstName, ' ', p.lastName, ' ', p.mobile, ' ', '(', cr.regNumber, ')')as patientDetails FROM Patient as p inner join ClinicRegistration as cr on p.id = cr.patientID where concat_ws(' ',p.firstName,p.middleName,p.lastName,p.email,p.mobile) LIKE ? AND p.activityStatus = ? AND p.practiceID = ?";

	public static final String RETRIEVE_VISIT_TYPE_ID_BY_APPT_ID = "SELECT visitTypeID FROM Appointment WHERE id = ?";

	public static final String RETRIEVE_APPO_DATE_ID_BY_APPT_ID = "SELECT apptDate FROM Appointment WHERE id = ?";

	public static final String RETRIEVE_BUCKET_NAME = "SELECT bucketName FROM Practice WHERE id = ?";

	public static final String VERIFY_SMSTemplate_Name_Already_Exists = "SELECT id FROM SMSTemplate WHERE title = ?";

	public static final String INSERT_SMS_TEMPLATE = "INSERT INTO SMSTemplate (title, text) VALUES (?,?)";

	public static final String RETRIEVE_ALL_SMS_TEMPLATES = "SELECT * FROM SMSTemplate";

	public static final String SEARCH_SMS_TEMPLATE = "SELECT * FROM SMSTemplate WHERE title LIKE ?";

	public static final String VERIFY_EDit_SMS_TEMPLATE_Already_Exists = "SELECT id FROM SMSTemplate WHERE title = ? AND id != ?";

	public static final String UPDATE_SMS_TEMPLATE = "UPDATE SMSTemplate SET title  = ?, text = ? WHERE id = ?";

	public static final String DELETE_SMS_TEMPLATE = "DELETE FROM SMSTemplate WHERE id = ?";

	public static final String RETRIEVE_SMS_TEMPLATE_BY_ID = "SELECT id, title, text FROM SMSTemplate WHERE id = ?";

	public static final String DELETE_Report_DETAILS = "DELETE FROM Reports WHERE id = ?";

	public static final String DELETE_GeneralHospital_Prescribed_Investigations = "DELETE FROM PrescribedInvestigations WHERE visitID = ?";

	public static final String SEARCH_LAB_TEST_LIST = "SELECT * FROM PVLabTests WHERE test LIKE ? AND practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_ALL_LAB_TEST_LIST = "SELECT * FROM PVLabTests WHERE activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ALL_DefaultValue_LIST = "SELECT * FROM PVLabTestDefaultValues WHERE labTestID = ?";

	public static final String DELETE_CONFIGURATION_LAB_TEST = "UPDATE PVLabTests SET activityStatus = ? WHERE id = ?";

	public static final String VERIFY_Lab_Test_Already_Exists = "SELECT id FROM PVLabTests WHERE test = ? AND subgroup = ?  AND activityStatus = ?";

	public static final String INSERT_LAB_TEST = "INSERT INTO PVLabTests (test, normalValues, activityStatus, normalValuesWomen, normalValuesChild, subgroup, remarks, isExcludeNormalValues, showNormalRangeDesc, normalRangeDesc, practiceID, isOutsourced, type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_LABTEST_ID = "SELECT id FROM PVLabTests WHERE test = ?";

	public static final String INSERT_Default_Value = "INSERT INTO PVLabTestDefaultValues (defaultValue, labTestID) VALUES (?,?)";

	public static final String UPDATE_LAB_TEST = "UPDATE PVLabTests SET test = ?, normalValues = ?, normalValuesWomen = ?, normalValuesChild = ?, subgroup = ?, remarks = ?, isExcludeNormalValues = ?, showNormalRangeDesc = ?, normalRangeDesc = ?, isOutsourced = ?, type = ? WHERE id = ?";

	public static final String VERIFY_EDit_Lab_Test_Already_Exists = "SELECT id FROM PVLabTests WHERE test = ?  AND subgroup = ?  AND activityStatus = ?";

	public static final String UPDATE_Default_Value = "UPDATE PVLabTestDefaultValues SET defaultValue = ? WHERE id = ?";

	public static final String RETRIEVE_Lab_Default_Value_List = "SELECT defaultValue FROM PVLabTestDefaultValues WHERE labTestID IN (SELECT id FROM PVLabTests WHERE test = ? AND activityStatus = ?)";

	public static final String RETRIEVE_SINGLE_LAB_TEST_TOTAL_RATE_BY_VISIT_ID = "SELECT SUM(rate) as total FROM LabInvestigations WHERE visitID = ? AND isGroup = ?";

	public static final String RETRIEVE_GROUP_LAB_TEST_TOTAL_RATE_BY_VISIT_ID = "SELECT li.groupRate, li.groupName FROM LabInvestigations AS li WHERE li.visitID = ? AND li.isGroup = ? GROUP BY li.groupName ";

	public static final String RETRIEVE_TRANSACTION_DETAILS1 = "SELECT t.id, t.quantity, t.receiptID, t.prescriptionID, t.amount, t.discount, t.taxAmount, t.productID, t.rate, (SELECT tradeName FROM Product WHERE id = t.productID) AS product, t.test, (SELECT normalRange FROM LabInvestigations WHERE visitID = ? AND test = t.test) AS normalRange, t.isGroup FROM Transactions AS t WHERE t.receiptID = (SELECT id FROM Receipt WHERE visitID = ?) ORDER BY t.test";

	public static final String RETRIEVE_LAB_INVESTIGATION_BY_VISIT_ID_AND_IS_GROUP = "SELECT * FROM LabInvestigations WHERE visitID = ? AND isGroup = ?";

	public static final String RETRIEVE_LAB_INVESTIGATION_BY_VISIT_ID_AND_IS_GROUP1 = "SELECT li.id, li.panel, li.test, li.qualitativeValue, li.quantitativeValue, li.rate, li.groupName, groupRate FROM LabInvestigations AS li WHERE li.visitID = ? AND li.isGroup = ? GROUP BY li.groupName";

	public static final String RETRIEVE_LAB_INVESTIGATIONNew = "SELECT GROUP_CONCAT(test) AS test FROM LabInvestigations WHERE visitID = ?";

	public static final String INSERT_LAB_VISIT_DETAILS = "INSERT INTO Visit (visitNumber, visitTypeID, visitDate, visitTimeFrom, visitTimeTo, diagnosis, visitNote, activityStatus, patientID, newVisitRef, nextVisitDays, apptID, clinicID, referredBy, sampleID, mdDoctorID, totalRate, isReportDispatched) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //Change

	public static final String INSERT_LAB_INVESTIGATION1 = "INSERT INTO LabInvestigations (test, qualitativeValue, quantitativeValue, normalRange, visitID, investigationDate, rate, isGroup, groupRate, groupName, subGroup, testType) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_LAB_BILL_RECEIPT = "UPDATE Receipt SET receiptNo = receiptNo, receiptDate = receiptDate, totalAmount = ?, netAmount = ?, advPayment = ?, balPayment = ?, paymentType = ?, billingType = ?, userID = ?, totalDiscount = ?, consultationCharges = ? WHERE visitID = ?";

	public static final String INSERT_BILL_TRANSACTION_DETAILS = "INSERT INTO Transactions (quantity, receiptID, prescriptionID, amount, taxAmount, activityStatus, productID, rate, discount, test) VALUES (?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_LAB_BILL_RECEIPT = "INSERT INTO Receipt (receiptNo, receiptDate, totalAmount, netAmount, advPayment, balPayment, paymentType, billingType, activityStatus, visitID, userID, totalDiscount, consultationCharges) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //Change

	public static final String VERIFY_REF_DOCTOR_EXISTS = "SELECT id FROM PVReferringDoctors WHERE doctorName  = ? AND practiceID = ?";

	public static final String VERIFY_REF_DOCTOR_EXISTS1 = "SELECT id FROM PVReferringDoctors WHERE doctorName  = ? AND id <> ?";

	public static final String VERIFY_LAB_RESULT_ADDED_FOR_VISIT = "SELECT qualitativeValue, quantitativeValue FROM LabInvestigations WHERE visitID = ? AND qualitativeValue = '' AND quantitativeValue = 0";

	public static final String UPDATE_LAB_VISIT_DETAILS = "UPDATE Visit SET visitDate = ?, referredBy = ?, sampleID = ?, visitNote = ?, mdDoctorID = ?, totalRate = ?, isReportDispatched = ? WHERE id = ?"; //Change

	public static final String UPDATE_LAB_INVESTIGATION1 = "UPDATE LabInvestigations SET qualitativeValue = ?, quantitativeValue = ? WHERE id = ?";

	public static final String RETRIEVE_USER_FULL_NAME_WITH_QUALIFICATION = "SELECT CONCAT(firstName, ' ', lastName) AS fullName, qualification, clinicianRegNo FROM AppUser WHERE id = ?";

	public static final String RETRIEVE_TO_EMAIL_BY_PRACTICE_ID = "SELECT emailTo FROM Communication WHERE practiceID  = ?";

	public static final String RETRIEVE_FROM_EMAIL_BY_PRACTICE_ID = "SELECT emailFrom FROM Communication WHERE practiceID  = ?";

	public static final String RETRIEVE_FROM_EMAIL_PASS_BY_PRACTICE_ID = "SELECT emailFromPass FROM Communication WHERE practiceID  = ?";

	public static final String RETRIEVE_LAB_TEST_DISTINCT_GROUP_BY_VISIT_ID = "SELECT distinct(li.groupName) AS groupName FROM LabInvestigations AS li WHERE li.visitID = ? AND li.isGroup = ? AND li.testType = 'PathologyLab'";

	public static final String RETRIEVE_LAB_TESTS_BY_VISIT_ID_AND_GROUP_NAME = "SELECT labInvest.test, labInvest.qualitativeValue, labInvest.quantitativeValue, labInvest.normalRange, labInvest.groupName, labInvest.subGroup FROM LabInvestigations as labInvest join PVLabTests as pvtest on labInvest.test = pvtest.test WHERE labInvest.visitID = ? AND labInvest.groupName = ? AND pvtest.practiceID = ? AND labInvest.testType = 'PathologyLab' order by pvtest.id";

	public static final String RETRIEVE_DISTINCT_SUB_GROUP_FOR_TEST_BY_VISIT = "SELECT distinct(subGroup) AS subGroup FROM LabInvestigations WHERE visitID = ? AND isGroup = ? AND testType = 'PathologyLab'";

	public static final String RETRIEVE_LAB_TESTS_BY_VISIT_ID_AND_SUB_GROUP = "SELECT test, qualitativeValue, quantitativeValue, normalRange, groupName, subGroup FROM LabInvestigations WHERE visitID = ? AND subGroup = ? AND isGroup = ? AND testType = 'PathologyLab'";

	public static final String RETRIEVE_PRACTICE_MD_DETAILS = "SELECT id, MDName, MDQualification, MDSignatureImage FROM PracticeMDDetails WHERE practiceID = ? AND ? BETWEEN startDate AND endDate";

	public static final String RETRIEVE_PRACTICE_MD_DETAILS_By_ID = "SELECT id, MDName, MDQualification, MDSignatureImage FROM PracticeMDDetails WHERE id = ?";

	public static final String VERIFY_IS_NORMA_RANGE_EXCLUDE = "SELECT isExcludeNormalValues, remarks, showNormalRangeDesc, normalRangeDesc FROM PVLabTests WHERE test = ? AND groupName = ?";

	public static final String VERIFY_IS_NORMA_RANGE_EXCLUDE_WITHOUT_GROUP = "SELECT isExcludeNormalValues, remarks, showNormalRangeDesc, normalRangeDesc FROM PVLabTests WHERE test = ?";
	
	public static final String INSERT_MD_DETAILS = "INSERT INTO PracticeMDDetails (MDName, MDQualification, MDSignatureImage, startDate, endDate, practiceID) VALUES (?,?,?,?,?,?)";

	public static final String RETRIEVE_MD_DETAILS_LIST_BY_PRACTICE_ID = "SELECT id, MDName, MDQualification, MDSignatureImage, startDate, endDate FROM PracticeMDDetails WHERE practiceID = ? ORDER BY id DESC";

	public static final String UPDATE_MD_DETAILS = "UPDATE PracticeMDDetails SET endDate = ? WHERE id = ? AND practiceID = ?";

	public static final String RETRIEVE_VISIT_BILLING_BY_VISIT_TYPE_IDNew = "SELECT billingType, consultationCharges FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_VISIT_DATE_BY_VISIT_ID1 = "SELECT DATE_FORMAT(visitDate, '%d-%m-%Y') AS visitDate FROM Visit WHERE id = ?";

	public static final String RETRIEVE_BILLING_DETAIL1 = "SELECT * FROM Receipt WHERE visitID = ? AND activityStatus = ?";

	public static final String RETRIEVE_LabInvestigations_Value_DETAILS = "SELECT CONCAT_WS('$', id, test, COALESCE(normalRange,'null'), CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'), COALESCE(subgroup,'null'), isGroup) AS groupValues, test, groupName,groupRate, rate, isGroup, testType FROM LabInvestigations WHERE visitID = ?";

	public static final String RETRIEVE_LabInvestigations_GROUP_Value_DETAILS = "SELECT GROUP_CONCAT(CONCAT_WS('$', id, test, COALESCE(normalRange,'null'), CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'), COALESCE(subgroup,'null'), isGroup) SEPARATOR '===') AS groupValues, test, groupName,groupRate, rate, isGroup FROM LabInvestigations WHERE visitID = ? AND isGroup = 1 GROUP BY groupName;";

	public static final String RETRIEVE_LabInvestigations_Value_DETAILS1 = "SELECT GROUP_CONCAT(CONCAT_WS('$', test, COALESCE(normalRange,'null'), CAST(rate AS decimal(9,1)), CAST(groupRate AS decimal(9,1)), COALESCE(groupName,'null'), COALESCE(subgroup,'null'), isGroup)SEPARATOR ',') AS groupValues FROM LabInvestigations WHERE visitID = ?";

	public static final String DELETE_LabInvestigationTest_DETAILS = "DELETE FROM LabInvestigations WHERE id = ?";

	public static final String RETRIEVE_VISIT_DETAILS = "SELECT * FROM OphthalmologyOPD WHERE visitID = ?";

	public static final String VERIFY_TempName_Already_Exists = "SELECT id FROM Templates WHERE TempReportType = ?";

	public static final String INSERT_TEMPLATE = "INSERT INTO Templates ( TempReportType, TempName, TemplateText) VALUES (?,?,?)";

	public static final String RETRIEVE_ALL_TEMPLATES = "SELECT * FROM Templates";

	public static final String SEARCH_TEMPLATE = "SELECT * FROM Templates WHERE TempReportType LIKE ? OR TempName LIKE ?";

	public static final String VERIFY_EDit_TEMPLATE_Already_Exists = "SELECT id FROM Templates WHERE  TempReportType = ? AND id != ?";

	public static final String UPDATE_TEMPLATE = "UPDATE Templates SET  TempReportType  = ?, TempName = ?, TemplateText = ? WHERE id = ?";

	public static final String DELETE_TEMPLATE = "DELETE FROM Templates WHERE id = ?";

	public static final String RETRIEVE_TEMPLATE_BY_ID = "SELECT id, TempReportType, TempName, TemplateText FROM Templates WHERE id = ?";
	
	public static final String RETRIEVE_TEMPLATE_AND_ID_BY_TYPE = "SELECT id, TempName FROM Templates WHERE TempReportType = ?";

	public static final String RETRIEVE_DOCTOR_LIST = "SELECT id,CONCAT(firstName,' ',middleName,' ',lastName) AS doctorName FROM AppUser WHERE practiceID = ? AND userType = 'clinician'";

	public static final String RETREIVE_REPORT_TYPE = "SELECT * FROM Templates WHERE id = ?";
	
	public static final String RETRIEVE_REPORT_TYPE_BY_TEMPREPORTTYPE = "SELECT * FROM Templates WHERE TempReportType = ?";
	
	public static final String RETRIEVE_PVLABTEST_ID = "SELECT id FROM PVLabTests WHERE test = ?";
	
	public static final String RETRIEVE_TEMPLATE_IDS = "SELECT templateID FROM TestTypeTemplates WHERE pvlabtestID = ?";
	
	public static final String RetrieveTemplate = "SELECT * FROM Templates WHERE id = ?";

	public static final String INSERT_INTO_VISIT_DATA = "INSERT INTO Visit (visitNumber,visitDate,visitNote,patientID,clinicID,activityStatus,visitTypeID,clinicianID,templateID) VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String GET_VISIT_DATA = "SELECT * FROM Visit WHERE id = ?";

	public static final String RETREIVE_VISIT_DETAILS = "SELECT * FROM PVVisitType WHERE id = ?";

	public static final String RETRIEVE_CLINICIAN_DEATILS_BY_APT_ID = "SELECT clinicianID FROM Appointment WHERE id = ?";

	public static final String UPDATE_INTO_VISIT_DATA = "UPDATE Visit SET visitDate = ? ,visitNote = ?,clinicianID = ? ,templateID = ? WHERE id = ?";

	public static final String RETRIEVE_VISIT_TRANSACTION_DETAILS = "SELECT * FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = ?)";

	public static final String INSERT_INTO_USG_VISIT_DATA = "INSERT INTO Visit (visitNumber,visitDate,patientID,clinicID,activityStatus,visitTypeID,clinicianID,nameOfGuardian,lastMenstrualPeriod,weekOfPregnancy,estimatedDueDate,referredBy,pcpndtStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RetrieveAddedReferringDoctor = "SELECT * FROM PVReferringDoctors ORDER BY id DESC LIMIT 1";

	public static final String UPDATE_USG_VISIT_DATA = "UPDATE Visit SET visitDate = ? ,clinicianID = ? ,nameOfGuardian = ?,lastMenstrualPeriod = ?,weekOfPregnancy=? ,estimatedDueDate =?,referredBy = ? WHERE id = ?";

	public static final String RETREIVE_PATIENT_DETAILS_BY_PID_VID = "SELECT * FROM(SELECT * FROM Visit WHERE id = ?) v INNER JOIN(SELECT * FROM Patient WHERE id = ?) p ON v.patientID = p.id;";

	public static final String RETREIVE_REFFERAL_DOCTOR_DETAILS = "SELECT * FROM PVReferringDoctors WHERE id = ?";

	public static final String UPDATE_PCPNDT_STATUS = "UPDATE Visit SET pcpndtStatus = ? WHERE id = ?";

	public static final String INSERT_INTO_SONS_DAUGHTERS_DATA = "INSERT INTO pcpNDTData (numberOfSons,ageOfSons,numberOfDaughters,ageOfDaughters,visitID) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_PCPNDT_DATA_VISIT_ID = "SELECT * FROM pcpNDTData WHERE visitID = ?";

	public static final String INSERT_GROUP_TESTS = "INSERT INTO groupTest (groupName,groupRate,practiceID, labTestID, groupRemark) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_GROUP_TEST_LIST = "SELECT * FROM groupTest WHERE practiceID = ?";

	public static final String INSERT_LAB_TEST_RATE = "INSERT INTO PVGroupLabTest (testID,testRate,practiceID) VALUES (?,?,?)";

	public static final String SEARCH_GROUP_LIST = "SELECT * FROM groupTest WHERE groupName LIKE ? AND practiceID = ? ";

	public static final String SEARCH_LAB_RATE_LIST = "SELECT * ,(SELECT testRate FROM PVGroupLabTest WHERE testID = pv.id AND practiceID = ?) as testRate FROM PVLabTests as pv WHERE test LIKE ?";

	public static final String RETRIEVE_TEST_LIST_BY_ID = "SELECT *,(SELECT testRate FROM PVGroupLabTest WHERE testID = pv.id) as testRate FROM PVLabTests AS pv WHERE id = ?";

	public static final String RETRIEVE_GROUP_ID_LIST = "SELECT id FROM groupTest WHERE practiceID = ? ORDER BY id DESC limit 1";

	public static final String INSERT_TEST_GROUP_ID_TESTS = "INSERT INTO groupTestID (groupID,testId) VALUES (?,?)";

	public static final String RETRIEVE_TEST_ID = "SELECT * FROM groupTestID WHERE groupID = ?";

	public static final String RETRIEVE_SINGLE_TEST_LIST = "SELECT *,(SELECT test FROM PVLabTests WHERE id = pv.testID) as testName,(SELECT CONCAT_WS('$', test, COALESCE(normalValuesWomen,'null'),COALESCE(subgroup,'null'), COALESCE(type,'null')) FROM PVLabTests WHERE id = pv.testID) as testDetail FROM PVGroupLabTest as pv WHERE practiceID = ?";

	public static final String RETRIEVE_GROUPNAME_LIST_BY_ID = "SELECT *,(SELECT CONCAT_WS('$', groupName,(SELECT cast(groupRate as decimal(9,1)) FROM groupTest WHERE id = gt.groupID)) FROM groupTest WHERE id = gt.groupID) AS groupDetails FROM groupTestID AS gt WHERE testID = ?";

	public static final String RETRIEVE_GROUP_DETAILS_LIST_BY_ID = "SELECT *,(SELECT GROUP_CONCAT(groupTestID.testID) FROM groupTestID WHERE groupTestID.groupID = groupTest.id) AS testIDs FROM groupTest WHERE id = ?";

	public static final String RETRIEVE_LAB_TEST_LIST = "SELECT *,(SELECT test FROM PVLabTests WHERE id = pv.testID) AS testName FROM PVGroupLabTest AS pv WHERE practiceID = ?;";

	public static final String RETRIEVE_LAB_TEST_DETAILS_LIST_BY_ID = "SELECT * FROM PVGroupLabTest WHERE id = ?";

	public static final String SEARCH_ALL_PATIENT = " SELECT p.id, p.firstName, p.middleName, p.lastName, p.age, p.gender, c.regNumber AS regNo FROM Patient AS p , ClinicRegistration AS c WHERE p.id = c.patientID AND concat_ws(' ',p.firstName,p.middleName,p.lastName,p.id,p.mobile,c.regNumber) LIKE ? AND p.practiceID = ? AND p.activityStatus = ?";

	public static final String UPDATE_LAB_TEST_RATE = "UPDATE PVGroupLabTest SET testRate = ? WHERE practiceID = ? AND testID = ?";

	public static final String RETRIEVE_ORTHO_DIAGNOSIS_LIST_BY_VISIT_ID = "SELECT diagnosis FROM Visit WHERE id = ?";

	public static final String RETRIEVE_RECEIPT_ITEM_DETAILS_BY_VISIT_ID = "SELECT * FROM ReceiptItems WHERE receiptID = (SELECT id FROM Receipt WHERE visitID = ?)";

	public static final String INSERT_RECEIPT_ITEM = "INSERT INTO ReceiptItems (item, quantity, rate, amount, receiptID) VALUES (?,?,?,?,?)";

	public static final String UPDATE_RECEIPT_ITEM = "UPDATE ReceiptItems SET item = ?, quantity = ?, rate = ?, amount = ? WHERE id = ?";

	public static final String RETRIEVE_RECEIPT_ITEM_DETAILS_BY_RECEIPT_ID = "SELECT * FROM ReceiptItems WHERE receiptID = ?";

	public static final String UPDATE_CLINIC_PHONE = "UPDATE Clinic SET phoneNo = ? WHERE id = ?";

	public static final String RETRIEVE_CLINIC_PHONE_BY_ID = "SELECT phoneNo FROM Clinic WHERE id = ?";

	public static final String RETRIEVE_APPOINTMENT_DOCTOR_NAME = "SELECT (SELECT CONCAT(firstName,' ',lastName) FROM AppUser WHERE id = clinicianID) as doctorName FROM Appointment WHERE patientID = ? AND apptDate = ? AND apptTimeFrom = ? AND apptTimeTo = ? AND clinicID = ?";

	public static final String RETRIEVE_PATIENT_ID_BY_VISIT = "SELECT patientID FROM Visit WHERE id = ?";

	public static final String INSERT_CYCLOPLEGIC_REFRACTION_DETAILS = "INSERT INTO CycloplegicRefraction (distCTCOD, distHTCOD, distAtropineOD, distTPlusOD, distCTCOS, distHTCOS, distAtropineOS, distTPlusOS, nearCTCOD, nearHTCOD, nearAtropineOD, nearTPlusOD, nearCTCOS, nearHTCOS, nearAtropineOS, nearTPlusOS, visitID, cycloplegicRefractionData) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_CYCLOPLEGIC_REFRACTION_DETAILS = "UPDATE CycloplegicRefraction SET distCTCOD = ?, distHTCOD = ?, distAtropineOD = ?, distTPlusOD = ?, distCTCOS = ?, distHTCOS = ?, distAtropineOS = ?, distTPlusOS = ?, nearCTCOD = ?, nearHTCOD = ?, nearAtropineOD = ?, nearTPlusOD = ?, nearCTCOS = ?, nearHTCOS = ?, nearAtropineOS = ?, nearTPlusOS = ? WHERE id = ?";

	public static final String UPDATE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_VISIT_ID = "UPDATE CycloplegicRefraction SET distCTCOD = ?, distHTCOD = ?, distAtropineOD = ?, distTPlusOD = ?, distCTCOS = ?, distHTCOS = ?, distAtropineOS = ?, distTPlusOS = ?, nearCTCOD = ?, nearHTCOD = ?, nearAtropineOD = ?, nearTPlusOD = ?, nearCTCOS = ?, nearHTCOS = ?, nearAtropineOS = ?, nearTPlusOS = ?, cycloplegicRefractionData = ? WHERE visitID = ?";

	public static final String RETRIEVE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_VISIT_ID = "SELECT * FROM CycloplegicRefraction WHERE visitID = ? AND (distCTCOD <> '--' AND distCTCOD IS NOT NULL OR distHTCOD <> '--' AND distHTCOD IS NOT NULL OR distAtropineOD <> '--' AND distAtropineOD IS NOT NULL OR distTPlusOD <> '--' AND distTPlusOD IS NOT NULL OR distCTCOS <> '--' AND distCTCOS IS NOT NULL OR distHTCOS <> '--' AND distHTCOS IS NOT NULL OR distAtropineOS <> '--' AND distAtropineOS IS NOT NULL OR distTPlusOS <> '--' AND distTPlusOS IS NOT NULL OR nearCTCOD <> '--' AND nearCTCOD IS NOT NULL OR nearHTCOD <> '--' AND nearHTCOD IS NOT NULL OR nearAtropineOD <> '--' AND nearAtropineOD IS NOT NULL OR nearTPlusOD <> '--' AND nearTPlusOD IS NOT NULL OR nearCTCOS <> '--' AND nearCTCOS IS NOT NULL OR nearHTCOS <> '--' AND nearHTCOS IS NOT NULL OR nearAtropineOS <> '--' AND nearAtropineOS IS NOT NULL OR nearTPlusOS <> '--' AND nearTPlusOS IS NOT NULL)";

	public static final String RETRIEVE_CYCLOPLEGIC_REFRACTION_DETAILS_BY_ID = "SELECT * FROM CycloplegicRefraction WHERE id = ?";

	public static final String RETRIEVE_ROOM_TYPE_ID_BY_ROOM_NAME = "SELECT id FROM PVIPDRoomTypes WHERE roomType = ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_OT_TYPE_BY_NAME = "SELECT id, name FROM PVOTTypes WHERE name LIKE ? AND practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_ALL_OT_TYPE = "SELECT id, name FROM PVOTTypes WHERE practiceID = ? AND activityStatus = ?";

	public static final String VERIFY_OT_TYPE_EXISTS = "SELECT id, name FROM PVOTTypes WHERE name = ? AND practiceID = ? AND activityStatus = ?";

	public static final String VERIFY_OT_TYPE_EXISTS1 = "SELECT id, name FROM PVOTTypes WHERE name = ? AND practiceID = ? AND activityStatus = ? AND id <> ?";

	public static final String INSERT_OT_TYPE = "INSERT INTO PVOTTypes (name, activityStatus) VALUES (?,?)";

	public static final String UPDATE_OT_TYPE = "UPDATE PVOTTypes SET name = ? WHERE id = ?";

	public static final String RETRIEVE_OT_TYPE_BY_ID = "SELECT id, name FROM PVOTTypes WHERE id = ?";

	public static final String UPDATE_OT_TYPE_STATUS = "UPDATE PVOTTypes SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_SERVICE_TYPE_BY_NAME = "SELECT id, name, roomCapacity FROM PVServiceTypes WHERE name LIKE ? AND practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_ALL_SERVICE_TYPE = "SELECT id, name, roomCapacity FROM PVServiceTypes WHERE practiceID = ? AND activityStatus = ?";

	public static final String VERIFY_SERVICE_TYPE_EXISTS = "SELECT id, name FROM PVServiceTypes WHERE name = ? AND practiceID = ? AND activityStatus = ?";

	public static final String VERIFY_SERVICE_TYPE_EXISTS1 = "SELECT id, name FROM PVServiceTypes WHERE name = ? AND practiceID = ? AND activityStatus = ? AND id <> ?";

	public static final String INSERT_SERVICE_TYPE = "INSERT INTO PVServiceTypes (name, activityStatus, roomCapacity) VALUES (?,?,?)";

	public static final String UPDATE_SERVICE_TYPE = "UPDATE PVServiceTypes SET name = ?, roomCapacity = ? WHERE id = ?";

	public static final String UPDATE_SERVICE_TYPE_STATUS = "UPDATE PVServiceTypes SET activityStatus = ? WHERE id = ?";

	public static final String RETRIEVE_ROOM_CHARGE_LIST_BY_DOCTOR_NAME = "SELECT id, roomName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVRoomCharges WHERE roomName LIKE ? AND activityStatus = ? AND practiceID = ?";

	public static final String RETRIEVE_ALL_ROOM_CHARGE_LIST = "SELECT id, roomName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVRoomCharges WHERE activityStatus = ? AND practiceID = ?";

	public static final String VERIFY_ROOM_CHARGE_EXISTS = "SELECT id, roomName, charges FROM PVRoomCharges WHERE roomName = ? AND activityStatus = ? AND roomTypeID = ? AND practiceID = ?";

	public static final String INSERT_ROOM_CHARGES = "INSERT INTO PVRoomCharges (roomName, charges, activityStatus, roomTypeID, practiceID) VALUES (?,?,?,?,?)";

	public static final String RETRIEVE_ROOM_CHARGES_LIST_BY_ID = "SELECT id, roomName, activityStatus, roomTypeID, charges, (SELECT roomType FROM PVIPDRoomTypes WHERE id = roomTypeID) AS roomType FROM PVRoomCharges WHERE id = ?";

	public static final String VERIFY_ROOM_CHARGE_EXISTS1 = "SELECT id, roomName, charges FROM PVRoomCharges WHERE doctorName = ? AND activityStatus = ? AND roomTypeID = ? AND id <> ? AND practiceID = ?";

	public static final String UPDATE_ROOM_CHARGES = "UPDATE PVRoomCharges SET roomName = ?, charges = ?, roomTypeID = ? WHERE id = ?";

	public static final String UPDATE_ROOM_CHARGE_STATUS = "UPDATE PVRoomCharges SET activityStatus = ? WHERE id = ?";

	// public static final String RETRIEVE_ROOM_FOR_BOOKING = "SELECT *, (SELECT
	// COUNT(*) FROM Appointment WHERE roomTypeID = PVIPDRoomTypes.id AND clinicID =
	// ? AND apptDate >= ? AND apptEndDate <= ? AND status NOT IN
	// ('Cancelled','Vacant')) AS bookedCount FROM PVIPDRoomTypes WHERE id NOT IN
	// (SELECT roomTypeID FROM Appointment WHERE clinicID = ? AND apptDate >= ? AND
	// apptEndDate <= ? AND status NOT IN ('Cancelled','Vacant')) AND practiceID = ?
	// AND activityStatus = ?";
	public static final String RETRIEVE_ROOM_FOR_BOOKING = "SELECT *, (SELECT COUNT(*) FROM Appointment WHERE roomTypeID = PVIPDRoomTypes.id AND clinicID = ? AND apptDate >= ? AND apptEndDate <= ? AND status NOT IN ('Cancelled','Vacant')) AS bookedCount FROM PVIPDRoomTypes WHERE practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_ROOM_FOR_BOOKING_WO_DATE = "SELECT *, (SELECT COUNT(*) FROM Appointment WHERE roomTypeID = PVIPDRoomTypes.id AND clinicID = ? AND status NOT IN ('Cancelled','Vacant')) AS bookedCount FROM PVIPDRoomTypes WHERE practiceID = ? AND activityStatus = ?";

	public static final String RETRIEVE_APPOINTMENT_BY_ROOM_TYPPE_ID = "SELECT *, DATE_FORMAT(apptDate, '%d-%m-%Y') as apptDateFMT, DATE_FORMAT(apptEndDate, '%d-%m-%Y') as apptEndDateFMT, (SELECT PVVisitType.name FROM PVVisitType WHERE PVVisitType.id = Appointment.visitTypeID) AS visitType, (SELECT CONCAT_WS(' ', Patient.firstName,Patient.middleName,Patient.lastName) FROM Patient WHERE Patient.id = Appointment.patientID) AS patientName FROM Appointment WHERE roomTypeID = ? AND clinicID = ? AND status NOT IN ('Cancelled','Vacant')";
	
	public static final String RETRIEVE_APPOINTMENT_ID = "SELECT id FROM Appointment WHERE patientID = ? AND apptTimeFrom = ? AND apptTimeTo = ? AND apptDate = ?";
	
	public static final String RETRIEVE_VISIT_ID_BY_APPOINTMENT_ID = "SELECT id FROM Visit WHERE apptID = ? AND clinicID = ?";

	public static final String DELETE_RECEIPT_ITEM_BY_ID = "DELETE FROM ReceiptItems WHERE id = ?";

	public static final String INSERT_PEC_PATIENT_DETAILS = "INSERT INTO Patient (firstName, middleName, lastName, gender, rhFactor, dateOfBirth, bloodGroup, age, mobile, email, phone, address, city, state, country, practiceID, occupation, ec, practiceRegNumber, activityStatus, referredBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String UPDATE_PEC_PATIENT_DETAILS = "UPDATE Patient SET firstName = ?, middleName = ?, lastName = ?, gender = ?, rhFactor = ?, dateOfBirth = ?, bloodGroup = ?, age = ?, mobile = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, country = ?, practiceID = ?, ec = ?, occupation = ?, practiceRegNumber = ?, referredBy = ? WHERE id = ?";

	public static final String INSERT_PEC_MEDICAL_HISTORY = "INSERT INTO MedicalHistory (isDiabetes, asthema, hypertension, ischemicHeartDisease, otherDetails, patientID) VALUES (?, ?, ?, ?, ?, ?)";

	public static final String RETRIEVE_PRODUCT_ID_FROM_RECEIPT = "SELECT productID FROM Receipt WHERE visitID = ?";

	public static final String INSERT_REF_DOC = "INSERT INTO PVReferringDoctors (doctorName, practiceID) VALUES (?,?)";

	public static final String RETRIEVE_REF_DOC_ID = "SELECT id FROM PVReferringDoctors WHERE doctorName = ?";

	// public static final String RETRIEVE_PEC_TRADE_NAME_LIST = "SELECT tradeName
	// FROM Product WHERE categoryID IN (SELECT id FROM Category WHERE otcategory =
	// 'No') ORDER BY id ASC";

	public static final String RETRIEVE_PEC_TRADE_NAME_LIST = "SELECT tradeName FROM Product WHERE clinicID = ? AND categoryID IN (SELECT id FROM Category WHERE otcategory = 'No') ORDER BY id ASC";

	// public static final String RETRIEVE_PEC_TRADE_NAME_LIST_BY_TRADE_NAME =
	// "SELECT tradeName FROM Product WHERE tradeName LIKE ? AND activityStatus = ?
	// AND categoryID IN (SELECT id FROM Category WHERE otcategory = 'No') ORDER BY
	// tradeName ASC";

	public static final String RETRIEVE_PEC_TRADE_NAME_LIST_BY_TRADE_NAME = "SELECT tradeName FROM Product WHERE tradeName LIKE ? AND activityStatus = ? AND clinicID = ? AND categoryID IN (SELECT id FROM Category WHERE otcategory = 'No') ORDER BY tradeName ASC";

	public static final String RETRIEVE_PEC_CATEGORY_LIST = "SELECT id, name FROM Category WHERE otcategory = 'No' ORDER BY id ASC";

	public static final String INSERT_CATEGORY_WITH_OT = "INSERT INTO Category (name, type, otcategory) VALUES (?,?,?)";

	public static final String UPDATE_CATEGORY_WITH_OT = "UPDATE Category SET name  = ?, type = ?, otcategory = ? WHERE id = ?";

	public static final String UPDATE_PEC_MEDICAL_HISTORY_DETAILS = "UPDATE MedicalHistory SET isDiabetes = ?, asthema = ?, hypertension = ?, ischemicHeartDisease = ?, otherDetails = ? WHERE patientID = ?";

	public static final String RETRIEVE_IOL_TARIFF_CHARGE_LIST = "SELECT p.id, p.tradeName, s.sellingPrice FROM Product p, Stock s WHERE p.id = s.productID AND p.categoryID IN(SELECT id FROM Category WHERE otcategory='Yes') AND p.id IN(SELECT productID FROM Stock WHERE netStock > 0) AND p.clinicID = ?";

	public static final String UPDATE_IOL_CHARGE_STOCK = "UPDATE Stock SET netStock = netStock - 1 WHERE productID = ?";

	public static final String RETRIEVE_REFERRED_BY_ID = "SELECT doctorName FROM PVReferringDoctors WHERE id = ?";

	public static final String RETRIEVE_PATIENT_BY_PRESCRIPTION_bk = "SELECT p.firstName, p.lastName, p.ID, v.visitDate, pr.visitID FROM Patient p INNER JOIN Visit v ON p.id = v.patientID INNER JOIN Prescription pr ON v.ID = pr.visitID and v.clinicID = ? AND DATE(v.visitDate) = DATE(NOW())";

	public static final String RETRIEVE_PATIENT_BY_PRESCRIPTION = "SELECT p.firstName, p.lastName, p.ID, v.visitDate, v.apptID, pr.visitID FROM Patient p INNER JOIN Visit v ON p.id = v.patientID INNER JOIN Prescription pr ON v.ID = pr.visitID and v.clinicID = ? AND DATE(v.visitDate) = DATE(NOW()) GROUP BY pr.visitID";

	// public static final String RETRIEVE_PRESCRIPTION = "SELECT p.drugName,
	// c.name, pr.numberOfDays, pr.frequency, pr.quantity, pr.comment, pr.rate FROM
	// Prescription pr JOIN Product p ON p.tradeName = pr.tradeName JOIN Category c
	// ON p.categoryID = c.id WHERE pr.visitID = ? AND p.clinicID = ?";

	// public static final String RETRIEVE_PRESCRIPTION = "SELECT p.drugName,
	// c.name, pr.numberOfDays, pr.frequency, pr.quantity, pr.comment, pr.rate, p.id
	// AS productID, s.sellingPrice, v.visitDate, pt.firstName, pt.lastName, pt.id
	// FROM Prescription pr JOIN Product p ON p.tradeName = pr.tradeName JOIN
	// Category c ON p.categoryID = c.id JOIN Stock s ON p.id = s.productID JOIN
	// Visit v ON pr.visitID = v.id JOIN Patient pt ON v.patientID = pt.id WHERE
	// pr.visitID = ? AND p.clinicID = ?";

	public static final String RETRIEVE_PRESCRIPTION_bk =

			"SELECT  p.drugName,  (SELECT c.name FROM Category c WHERE c.id=p.categoryID) AS categoryName, pr.numberOfDays,pr.frequency,pr.quantity,pr.comment,pr.rate, p.id AS productID,s.sellingPrice,v.visitDate,pt.firstName,pt.lastName, pt.id FROM Prescription pr JOIN Product p ON p.tradeName= pr.tradeName JOIN Stock s ON p.id= s.productID JOIN Visit v ON pr.visitID= v.id JOIN Patient pt ON v.patientID= pt.id WHERE pr.visitID=? AND p.clinicID=?";

	public static final String RETRIEVE_PRESCRIPTION = "SELECT  p.drugName,  (SELECT c.name FROM Category c WHERE p.categoryID = c.id) AS categoryName, pr.numberOfDays,pr.frequency,pr.quantity,pr.comment,pr.rate, p.id AS productID,s.sellingPrice,v.visitDate,pt.firstName,pt.lastName, pt.id FROM Prescription pr JOIN Product p ON p.tradeName= pr.tradeName JOIN Stock s ON p.id= s.productID JOIN Visit v ON pr.visitID= v.id JOIN Patient pt ON v.patientID= pt.id WHERE pr.visitID=? AND p.clinicID=?";

	public static final String SEARCH_PATIENT_BY_NAME_FOR_PHARMA_bk = "SELECT p.firstName, p.lastName, p.ID, v.visitDate, pr.visitID "
			+ "FROM Patient p " + "INNER JOIN Visit v ON p.id = v.patientID "
			+ "INNER JOIN Prescription pr ON v.ID = pr.visitID AND v.clinicID = ? "
			+ "WHERE CONCAT(p.firstName, ' ', p.middleName, ' ', p.lastName) LIKE CONCAT('%', ?, '%')";

	public static final String SEARCH_PATIENT_BY_NAME_FOR_PHARMA = "SELECT p.firstName, p.lastName, p.ID, v.visitDate, v.apptID, pr.visitID "
			+ "FROM Patient p " + "INNER JOIN Visit v ON p.id = v.patientID "
			+ "INNER JOIN Prescription pr ON v.ID = pr.visitID AND v.clinicID = ? "
			+ "WHERE CONCAT(p.firstName, ' ', p.middleName, ' ', p.lastName) LIKE CONCAT('%', ?, '%') GROUP BY pr.visitID";

	public static final String UPDATE_PRACTICE_URL = "UPDATE Practice SET url = ? WHERE id = ?";

	public static final String INSERT_PI_PATIENT_DETAILS = "INSERT INTO Patient (firstName, middleName, lastName, mobile, practiceRegNumber, practiceID, gender, age, dateOfBirth, bloodGroup, rhFactor, email, activityStatus, phone, state, city, address, country) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_PI_MEDICAL_HISTORY = "INSERT INTO MedicalHistory (patientID, isDiabetes, hypertension, asthema, ischemicHeartDisease, otherDetails) VALUES (?,?,?,?,?,?)";

	public static final String UPDATE_PI_MEDICAL_HISTORY = "UPDATE MedicalHistory SET isDiabetes = ?, hypertension = ?, asthema = ?, ischemicHeartDisease = ?, otherDetails = ? WHERE patientID = ?";

	public static final String INSERT_PI_APPOINTMENT_DETAILS = "INSERT INTO Appointment (apptDate, status, patientID, clinicID, visitTypeID, walkin, apptTimeFrom, apptTimeTo, apptNumber, clinicianID, comments) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

	public static final String RETRIEVE_PI_APPT_BY_CLINIC_AND_DATE = "SELECT apptTimeFrom, COUNT(SUBSTRING(apptTimeFrom,1,2)) AS count FROM Appointment WHERE clinicID = ? AND apptDate = ? GROUP BY SUBSTRING(apptTimeFrom,1,2)";

	public static final String RETRIEVE_CALENDAR_TIME_DIFF_BY_CLINIC_ID = "SELECT *, HOUR(TIMEDIFF(clinicStart, ?)) as hourDiff FROM Calendar WHERE clinicID = ?";

	public static final String UPDATE_IS_CONSULTATION_IS_VISIT = "UPDATE Visit SET isConsultationDone = ? WHERE id = ?";

	public static final String INSERT_VISIT_EDIT_HISTORY = "INSERT INTO VisitEditHistory (dateTime, action, userID, visitID, visitType, clinicID) VALUES (NOW(), ?, ?, ?, ?, ?)";

	public static final String RETRIEVE_VISIT_EDIT_HISTORY = "SELECT *, DATE_FORMAT(dateTime, '%d-%m-%Y %H:%i') AS dateTimeFMT, (SELECT CONCAT_WS(AppUser.firstName,' ',AppUser.lastName) FROM AppUser WHERE AppUser.id = VisitEditHistory.userID) AS userName FROM VisitEditHistory WHERE visitID = ? AND visitType = ? ORDER BY ID DESC LIMIT 1";

	public static final String RETRIEVE_OTP = "SELECT * FROM OTPVerification WHERE mobileNo = ? AND status = ? AND clinicID = ? ORDER BY id DESC LIMIT 1";

	public static final String INSERT_OTP = "INSERT INTO OTPVerification (OTP, mobileNo, status, clinicID, createdAt) VALUES (?,?,?,?,NOW())";

	public static final String UPDATE_OTP = "UPDATE OTPVerification SET status = ? WHERE mobileNo = ? AND clinicID = ?";

	public static final String RETREIVE_DIAGNOSTIC_LIST = "SELECT * FROM PrescribedDiagnostics WHERE activityStatus = ? AND visitID = ? ORDER BY id ASC";
	
	public static final String RETREIVE_PROCEDURE_LIST = "SELECT * FROM PrescribedProcedures WHERE activityStatus = ? AND visitID = ? ORDER BY id ASC";

	public static final String INSERT_DIAGNOSTIC_DETAILS = "INSERT INTO PrescribedDiagnostics (diagnostic, activityStatus, visitID) VALUES (?,?,?)";
	
	public static final String INSERT_PROCEDURE_DETAILS = "INSERT INTO PrescribedProcedures (procedureName, activityStatus, visitID) VALUES (?,?,?)";

	public static final String RETRIEVE_TEST_TYPE_FOR_VISIT = "SELECT CONCAT_WS('==',id,testType,test) AS testType FROM LabInvestigations WHERE visitID = ? and testType <> 'PathologyLab'";

	public static final String INSERT_VISIT_TEMPLATE_DATA = "INSERT INTO VisitTemplateData (templateData, templateID, visitID, clinicianID, investigationID) VALUES (?,?,?,?,?)";

	public static final String UPDATE_VISIT_TEMPLATE_DATA = "UPDATE VisitTemplateData SET templateData = ?, clinicianID = ?, templateID = ? WHERE id = ?";

	public static final String UPDATE_BDP_PATIENT_DETAILS = "UPDATE Patient SET age = ?, mobile = ?, email = ?, address = ? WHERE id = ?";

	public static final String RETRIEVE_VISIT_TEMPLATE_DATA = "SELECT *, (SELECT Templates.TempReportType FROM Templates WHERE Templates.id = VisitTemplateData.templateID) AS templateType, (SELECT Templates.TempName FROM Templates WHERE Templates.id = VisitTemplateData.templateID) AS templateName, (SELECT CONCAT_WS(' ', AppUser.firstName, AppUser.lastName) FROM AppUser WHERE AppUser.id = VisitTemplateData.clinicianID) AS clinicianName FROM VisitTemplateData WHERE visitID = ?";

	public static final String RETRIEVE_VISIT_TEMPLATE_DATA_BY_INVEST_ID = "SELECT * FROM VisitTemplateData WHERE investigationID = ?";
	
	public static final String RETRIEVE_VISIT_TEMPLATE_NAME_BY_ID = "SELECT TempName FROM Templates WHERE id = ?";
	
	public static final String VERIFY_PATHOLOGY_TEST_AVAILABLE_FOR_VISIT = 
		    "SELECT id FROM LabInvestigations WHERE visitID = ? AND testType = 'PathologyLab'"; // New added

	public static final String RETRIEVE_PATIENT_ID_BY_REG_NUMBER = "SELECT id FROM Patient WHERE practiceRegNumber = ?";
	
	public static final String RETRIEVE_CLINICIAN_ID_BY_NAME = "SELECT id from AppUser WHERE firstName = ? AND middleName = ? AND lastName = ? AND practiceID = ? AND userType = ?";

	public static final String RETRIEVE_CLINICIAN_ID_BY_FNAME_LNAME = "SELECT id from AppUser WHERE firstName = ? AND lastName = ? AND practiceID = ? AND userType = ?";
	
	public static final String UPDATE_IS_DISPATCHED_STATUS = "UPDATE LabInvestigations SET isDispatched = ? WHERE id = ?";
	
	public static final String RETRIEVE_IS_DISPATCHED_DETAILS_BY_VISITID = "SELECT * FROM LabInvestigations WHERE visitID = ? AND testType != ?";

	public static final String RETRIEVE_APPOINTMENT_NUMBER_BY_CLINIC_ID = "SELECT apptNumber FROM Appointment WHERE apptDate = ? AND clinicID = ?";

	public static final String RETRIEVE_CLINICIAN_ID_BY_INVEST_ID = "SELECT clinicianID FROM VisitTemplateData where investigationID = ?";
	
	public static final String RETRIEVE_CLINICIAN_DETAILS_BY_ID = "SELECT firstName, lastName, qualification, clinicianRegNo FROM AppUser WHERE id = ?";

	public static final String INSERT_IS_NORMAL_VALUE = "UPDATE pcpNDTData SET isNormal = ? WHERE visitID = ?";

	public static final String RETRIEVE_NORMAL_BOOL_VALUE = "SELECT isNormal FROM pcpNDTData WHERE visitID =?";

	public static final String RETRIEVE_TEST_ID_BY_TEST_NAME = "SELECT id FROM PVLabTests WHERE test = ? AND practiceID = ?";
}
