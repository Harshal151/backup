package com.edhanvantari.form;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PatientForm {

	private List<String> selectedPrintField;
	private Boolean isReportDispatched;
	private int []isDispatched;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int userID;
	private String middleName;
	private int patientID;
	private String gender;
	private String dob;
	private String dateOfBirth;
	private String bloodGroup;
	private String rhFactor;
	private String address;
	private String city;
	private String state;
	private String country;
	private String phone;
	private String mobile;
	private String emailID;
	private String emFname;
	private String emMname;
	private String emLname;
	private String emAdd;
	private String emCity;
	private String emState;
	private String emCountry;
	private String emPhone;
	private String emMobile;
	private String emEmailID;
	private String aadhaarNo;
	private String emRelation;
	private String careType;
	private String visitType;
	private String visitDate;
	private String visitFromTime;
	private String visitToTime;
	private String diagnosis;
	private String medicalNotes;
	private String drugName;
	private String dose;
	private String doseUnit;
	private String noOfDays;
	private String frequency;
	private String comment;
	private String description;
	private double rate;
	private String count;
	private double charges;
	private String activityStatus;
	private int visitID;
	private int prescriptionID;
	private int billID;
	public int visitNumber;
	public double totalBill;
	public double serviceTax;
	public String serviceCharge;
	public double finalTotalBill;
	public String age;
	public String tradeName;
	public String eyeLidUpperOD;
	public String eyeLidUpperOS;
	public String eyeLidLowerOD;
	public String eyeLidLowerOS;
	public String conjunctivaOD;
	public String conjunctivaOS;
	public String CorneaOD;
	public String CorneaOS;
	public String ACOD;
	public String ACOS;
	public String irisOD;
	public String irisOS;
	public String lensOD;
	public String lensOS;
	public String IODOD;
	public String IODOS;
	public String sacOD;
	public String sacOS;
	public String k1OD;
	public String k1OS;
	public String k2OD;
	public String k2OS;
	public String axialLengthOD;
	public String axialLengthOS;
	public String IOLOD;
	public String IOLOS;
	public String pinholeVisionOD;
	public String pinholeVisionOS;
	public String BCVAOD;
	public String BCVAOS;
	public String discOD;
	public String vesselOD;
	public String maculaOD;
	public String discOS;
	public String vesselOS;
	public String maculaOS;
	public int nextVisitDays;
	public String sphDiskOD;
	public String cylDiskOD;
	public String axisDiskOD;
	public String vnDiskOD;
	public String sphDiskOS;
	public String cylDiskOS;
	public String axisDiskOS;
	public String vnDiskOS;
	public String sphNearOD;
	public String cylNearOD;
	public String axisNearOD;
	public String vnNearOD;
	public String sphNearOS;
	public String cylNearOS;
	public String axisNearOS;
	public String vnNearOS;
	public String tint;
	public String glass;
	public String material;
	public String clinic;
	public String frame;
	private double frameCharge;
	private double glassCharge;
	private double total;
	private double discount;
	private double netPayment;
	private double advance;
	private double balance;
	private double balancePaid;
	public String balancePaidDate;
	private int opticinID;
	private int lastVisitID;
	public String visualAcuityDistOD;
	public String visualAcuityNearOD;
	public String visualAcuityDistOS;
	public String visualAcuityNearOS;
	public String pinholeVisionDistOD;
	public String pinholeVisionNearOD;
	public String pinholeVisionDistOS;
	public String pinholeVisionNearOS;
	public String BCVADistOD;
	public String BCVANearOD;
	public String BCVADistOS;
	public String BCVANearOS;
	private String oldSphDiskOD;
	private String oldCylDiskOD;
	private String oldAxisDiskOD;
	private String oldVnDiskOD;
	private String oldSphDiskOS;
	private String oldCylDiskOS;
	private String oldAxisDiskOS;
	private String oldVnDiskOS;
	private String oldSphNearOD;
	private String oldCylNearOD;
	private String oldAxisNearOD;
	private String oldVnNearOD;
	private String oldSphNearOS;
	private String oldCylNearOS;
	private String oldAxisNearOS;
	private String oldVnNearOS;
	private String medicalCerti;
	private String medicalCertiForPDF;
	private String referralLetter;
	private String referralLetterForPDF;
	private String co;
	private String ODRt[];
	private String OSLt[];
	private int OEPulse;
	private int OEBPSys;
	private int OEBPDia;
	private String OERS;
	private String OECVS;
	private String OEAdv;
	private double invertigationsHb;
	private double invertigationsWBC;
	private double invertigationsBT;
	private double invertigationsCT;
	private double invertigationsBSL;
	private double invertigationsF;
	private double invertigationsPP;
	private String invertigationsUrine;
	private String countinuationSheetTeatment;
	private String countinuationSheetDate;
	private String countinuationSheetDescription;
	private String OTNotes;
	private String ho;
	private String allergicTo;
	private int complaintID;
	private int continuationSheetID;
	private String consentText1;
	private String consentText;
	private String consentText2;
	private File consentFile;
	private String consentFileFileName;
	private String consentFileDBName;
	private String consentText2DB;
	private String consentText1DB;
	private String consentTextDB;
	private String patientName;
	private String appointmentDate;
	private String aptFromHH;
	private String aptFromMM;
	private String aptToHH;
	private String aptToMM;
	private int aptNumber;
	private int aptID;
	private String aptTimeFrom;
	private String aptTimeTo;
	private String aptStatus;
	private String aptfromMMAMPM;
	private String aptToMMAMPM;
	private int noOfPills;
	private String consentDiagnosis;
	private File IPDFile;
	private String visitFromTimeHH;
	private String visitFromTimeAMPM;
	private String visitToTimeAMPM;
	private String visitToTimeHH;
	private String visitToTimeMM;
	private String visitFromTimeMM;
	private List<File> labReport = new ArrayList<File>();
	private List<String> labReportContentType = new ArrayList<String>();
	private List<String> labReportFileName = new ArrayList<String>();
	private InputStream fileInputStream;
	private String fileName;
	private String searchPatientName;
	private String searchName;
	private String ODRt1;
	private String OSLt1;
	private String occupation;
	private String EC;
	private String sonDaughterOf;
	private String cancerType;
	private String medicalRegNo;
	private String firstVisitDate;
	private String dobYear;
	private String dobMonth;
	private String dobDate;
	private int practiceID;
	private String registrationNo;
	private String sourceType;
	private String sourceName;
	private String sourceContact;
	private String shortHistoryDescription;
	private String shortHistoryDiagnosis;
	private String shortHistoryOtherDetails;
	private String personalHistorySmoking;
	private String personalHistorySmokingDetails;
	private String personalHistoryDiet;
	private String clinicianName;
	private String personalHistoryPurisha;
	private String personalHistoryMootra;
	private String personalHistoryKshudha;
	private String personalHistoryNidra;
	private String personalHistoryOther;
	private String consentDate;
	private String nameOfGuardian;
	private String relationWithPatient;
	private double weight;
	private String appetite;
	private String sleep;
	private String bowels;
	private String mood;
	private String primary;
	private String nodesInvolved;
	private String metastasis;
	private String tnmStaging;
	private String dateOfPrimaryDiagnosis;
	private String dateOfMetastasisDiagnosis;
	private String biopsyFindingsDate;
	private String biopsyFindings;
	private String PETFindingsDate;
	private String PETFindings;
	private String CTMRIFindingsDate;
	private String CTMRIFindings;
	private String otherInvestigationsDate;
	private String otherInvestigations;
	private String tumorMarkersDate;
	private String tumorMarkers;
	private String notes;
	private String[] chemoTaken;
	private String[] chemoFirstCycleDate;
	private String[] chemoNoOfCycles;
	private String[] chemoAgents;
	private String[] chemoAdvReactions;
	private String[] radioTaken;
	private String[] radioDetails;
	private String[] radioFirstCycleDate;
	private String[] radioNoOfCycles;
	private String[] radioAdvReactions;
	private String[] underwentSurgery;
	private String[] underwentSurgeryDate;
	private String[] surgeryName;
	private String[] surgeryExcision;
	private String[] marginsFreeOfTumor;
	private String[] lymphNodesDissected;
	private String[] lymphNodesNo;
	private String[] positiveNodes;
	private String conventionalTherapies;
	private String alternativeTherapy;
	private String alternativeTherapyDetails;
	private String estimatedSurvival;
	private String consentGender;
	private int consentAge;
	private String consentMobile;
	private int clinicID;
	private double physicalScore;
	private double socialScore;
	private double emotionalScore;
	private double functionalScore;
	private double totalScore;
	private String ecogStatus;
	private String clinicalEvent;
	private String qolDate;
	private String[] chemoAgent;
	private String[] chemoDose;
	private String[] chemoCycle;
	private String[] dateOfChemo;
	private String[] treatmentType;
	private String[] chemoAdverseEvent;
	private String[] chemoReceived;
	private String[] chemoRecvdAgentName;
	private String[] chemoRecvdStartDate;
	private String[] chemoRecvdEndDate;
	private String[] chemoRecvdNoOfCycle;
	private String[] chemoRecvdSymptoms;
	private String[] tumorEventType;
	private String[] investigationDetails;
	private String[] tumorStages;
	private String[] tumorSite;
	private String[] tumorDimension;
	private String[] investigationDate;
	private String[] tumorMarkerDate;
	private String[] tumorMarker;
	private String[] tumorMarkerObservedValue;
	private String[] tumorMarkerNormalValue;
	private String[] symptom;
	private String[] symptomCode;
	private String[] symptomValue;
	private String[] otherSymptom;
	private String[] panel;
	private String[] test;
	private String[] qualitativeValue;
	private String[] quantitativeValue;
	private String[] normalRange;
	private String[] vitalSignsWeight;
	private String[] values;
	private String liverProfilePanel;
	private String[] liverProfileTest;
	private String[] liverProfileNormalRange;
	private String[] liverProfileValues;
	private String[] liverPanelArr;
	private String[] liverDateArr;
	private String liverTest;
	private String liverNormalRange;
	private String liverValue;
	private String renalProfilePanel;
	private String[] renalProfileTest;
	private String[] renalProfileNormalRange;
	private String[] renalProfileValues;
	private String[] renalPanelArr;
	private String[] renalDateArr;
	private String renalNormalRange;
	private String renalTest;
	private String renalValue;
	private String CBCPanel;
	private String[] CBCTest;
	private String[] CBCNormalRange;
	private String[] CBCValues;
	private String[] CBCPanelArr;
	private String[] CBCDateArr;
	private String CBCProfileTest;
	private String CBCProfileNormalValue;
	private String CBCProfileValue;
	private int visitTypeID;
	private String[] biopsyFindingsDateArray;
	private String[] biopsyFindingsArray;
	private String[] PETFindingsDateArray;
	private String[] PETFindingsArray;
	private String[] CTFindingsDateArray;
	private String[] CTFindingsArray;
	private String[] otherInvstgDateArray;
	private String[] otherInvstgArray;
	private String[] tumorMarkerDateArray;
	private String[] tumorMarkerArray;
	private String category;
	private String[] drugID;
	private String[] drugNoOfDays;
	private String[] drugFrequency;
	private String[] drugDosage;
	private String[] drugQuantity;
	private String[] drugComment;
	private String[] categoryID;
	private String[] drugBarcode;
	private String[] isCompound;
	private String[] compound;
	private String[] drugStockID;
	private int quantity;
	private String receiptNo;
	private String receiptDate;
	private double consultationCharges;
	private double totalAmount;
	private double netAmount;
	private double advPayment;
	private double balPayment;
	private double taxAmount;
	private String paymentType;
	private String billingType;
	private String productName;
	private String productBarcode;
	private String refReceiptNo;
	private double productAmount;
	private double productRate;
	private double totalVat;
	private double productQuantity;
	private double productTaxAmount;
	private int productID;
	private int transactionID;
	private int productCategoryID;
	private String transactionDate;
	private String chequeIssuedBy;
	private String chequeNo;
	private String chequeBankName;
	private String chequeBankBranch;
	private String chequeDate;
	private double chequeAmt;
	private String cardMobileNo;
	private String productCat;
	private String productTradeName;
	private String barcode;
	private double productVAT;
	private int receiptID;
	private int productCatID;
	private String[] billingProdCatID;
	private String[] billingProdTradeNameID;
	private String[] billingProdBarcode;
	private String[] billingProdQuantity;
	private String[] billingProdRate;
	private String[] billingProdAmount;
	private String[] billingProdVAT;
	private int billingCategoryID;
	private int billingProdID;
	private String billingBarcode;
	private String billingProdCat;
	private int billingQuantity;
	private double billingRate;
	private double billingAmount;
	private double billingVAT;
	private String billingProductName;
	private String[] prescID;
	private double cashPaid;
	private double cashToReturn;
	private double creditNoteBal;
	private String famHistDiagnosis;
	private String famHistComment;
	private String famHistOther;
	private String medHistDiagnosis;
	private String medHistComment;
	private String medHistOther;
	private String personalHistOther;
	private String tnmTVal;
	private String tnmNVal;
	private String tnmMVal;
	private String primaryDiagnDay;
	private String primaryDiagnMon;
	private String primaryDiagnYear;
	private String metaDiagnDay;
	private String metaDiagnMon;
	private String metaDiagnYear;
	private String biopsyFindingsDateName;
	private String biopsyFindingsName;
	private String PETFindingsDateName;
	private String PETFindingsName;
	private String CTFindingsDateName;
	private String CTFindingsName;
	private String otherInvstgDateName;
	private String otherInvstgName;
	private String tumorMarkerDateName;
	private String chemoTakenName;
	private String chemoFirstCycleDateName;
	private int chemoNoOfCyclesName;
	private String chemoAgentsName;
	private String chemoAdvReactionsName;
	private String radioTakenName;
	private String radioDetailsName;
	private String radioFirstCycleDateName;
	private int radioNoOfCyclesName;
	private String radioAdvReactionsName;
	private String underwentSurgeryName;
	private String underwentSurgeryDateName;
	private String surgery;
	private String surgeryExcisionName;
	private String marginsFreeOfTumorName;
	private String lymphNodesDissectedName;
	private int lymphNodesNoName;
	private int positiveNodesName;
	private String tumorMarkerName;
	private String chemoAgentName;
	private String chemoDoseName;
	private String chemoCycleName;
	private String dateOfChemoName;
	private String treatmentTypeName;
	private String tumorEventTypeName;
	private String investigationDetailsName;
	private String tumorStagesName;
	private String tumorSiteName;
	private String tumorDimensionName;
	private String investigationDateName;
	private String invstgDateDay;
	private String invstgDateMonth;
	private String invstgDateYear;
	private int investigationDetailsID;
	private String[] routineDietTime;
	private String[] routineDietFood;
	private String[] otherDietFactor;
	private String dietFactor;
	private String lifestyleFactor;
	private String[] otherLifestyleFactor;
	private String symptomFactor;
	private String[] otherSymptomFactor;
	private String[] dietObservations;
	private String[] observationsValue;
	private String observationsValueName;
	private String dietObservationsName;
	private String otherSymptomFactorName;
	private String otherLifestyleFactorName;
	private String otherDietFactorName;
	private String routineDietFoodName;
	private String routineDietTimeName;
	private int dietID;
	private int dietObservationID;
	private int dietLifestyleID;
	private int dietScheduleID;
	private int dietAdviceID;
	private String grainGood;
	private String grainNotGood;
	private String grainRecipe;
	private String grainInstruction;
	private String pulseGood;
	private String pulseNotGood;
	private String pulseRecipe;
	private String pulseInstruction;
	private String leafyVegGood;
	private String leafyVegNotGood;
	private String leafyVegRecipe;
	private String leafyVegInstruction;
	private String fruitVegGood;
	private String fruitVegNotGood;
	private String fruitVegRecipe;
	private String fruitVegInstruction;
	private String rootGood;
	private String rootNotGood;
	private String rootInstruction;
	private String fruitGood;
	private String fruitNotGood;
	private String fruitRecipe;
	private String fruitInstruction;
	private String spicesGood;
	private String spicesNotGood;
	private String spicesInstruction;
	private String dryFruitGood;
	private String dryFruitNotGood;
	private String dryFruitRecipe;
	private String dryFruitInstruction;
	private String otherGood;
	private String otherNotGood;
	private String otherInstruction;
	private String milkProdGood;
	private String milkProdNotGood;
	private String milkProdRecipe;
	private String milkProdInstruction;
	private String meatGood;
	private String meatNotGood;
	private String meatRecipe;
	private String meatInstruction;
	private String beverageGood;
	private String beverageNotGood;
	private String beverageInstruction;
	private String waterGood;
	private String waterNotGood;
	private String waterInstruction;
	private String generalInstruction;
	private String[] symptomCheckID;
	private int biopsyFindingsID;
	private int PETFindingsID;
	private int CTMRIFindingsID;
	private int otherFindingsID;
	private int tumorMarkerFindingsID;
	private int chemoToleranceID;
	private int chemoReceivedID;
	private int chemoID;
	private int radioID;
	private int surgeryID;
	private int tumorMakerID;
	private int symptomID;
	private int vitalSignsID;
	private String chemoReceivedName;
	private String chemoRecvdAgent;
	private String chemoRecvdStartDateName;
	private String chemoRecvdEndDateName;
	private int chemoRecvdNoOfCycleName;
	private String tumorMarkerObserverdValueName;
	private String tumorMarkerNormalValueName;
	private String symptomCheckName;
	private String symptomCheckCode;
	private String symptomCheckValue;
	private String otherSymptomName;
	private double weightName;
	private String vitalSignsDateName;
	private String deleteType;
	private int deleteID;
	private String visitDay;
	private String visitMonth;
	private String visitYear;
	private int tumorSpecificDetailID;
	private String[] tumorSpeficID;
	private int QOLID;
	private String[] otherSymptoms;
	private String[] otherSymptomCode;
	private String[] otherSymptomValue;
	private String[] otherSymptomComment;
	private int baselineCancerID;
	private int baselineTreatmentID;
	private int srNo;
	private String cMobileNo;
	private int supplierID;
	private String startDate;
	private String endDate;
	private String fromDate;
	private String toDate;
	private String submitBtn;
	private String practiceSuffix;
	private String clinicSuffix;
	private String check;
	private String patientIDString;
	private String compoundName;
	private int isCompoundName;
	private int stockID;
	private String[] otherLabTest;
	private String[] otherLatTestVal;
	private String otherTest;
	private String otherTestValue;
	private String isCompStr;
	private double dosage;
	private String mhoDescription;
	private String surgicalHistoryDescription;
	private String currentMedications;
	private String XRAYFindingDate;
	private String XRAYFindingType;
	private String XRAYFindingDescription;
	private File XRAYFindingReportFile;
	private String XRAYFindingReportFileFileName;
	private String XRAYFindingReportFileContentType;
	private String UCGFindingDate;
	private String UCGFindingType;
	private String UCGFindingDescription;
	private File UCGFindingReportFile;
	private String UCGFindingReportFileFileName;
	private String UCGFindingReportFileContentType;
	private String CTFindingDate;
	private String CTFindingType;
	private String CTFindingDescription;
	private File CTFindingReportFile;
	private String[] symptomDate;
	private String sypmtomDateStr;
	private int findingsID;
	private String[] medication;
	private String[] medicationDescription;
	private String medicationName;
	private String medicationDesc;
	private int medicationID;
	private String reportDBName;
	private int reportsID;
	private int surgicalID;
	private String renalInvestigationDate;
	private String liverInvestigationDate;
	private String CBCInvestigationDate;
	private String renalDay;
	private String renalMonth;
	private String renalDate;
	private String liverDate;
	private String liverDay;
	private String liverMonth;
	private String CBCDate;
	private String CBCDay;
	private String CBCMonth;
	private String[] otherLabInvestigationDate;
	private int cliniciaID;
	private String[] instructions;
	private String prescInstructions;
	private String otherType;
	private String[] appointmentID;
	private String[] patID;
	private String cbcHemoglobinVal;
	private String cbcWBCVal;
	private String cbcPlateleteVal;
	private String cbcRBCVal;
	private String cbcSmear;
	private String liverBilirubinVal;
	private String liverSCOTVal;
	private String liverSGPTVal;
	private String liverALPVal;
	private String liverProteinVal;
	private String renalUreaVal;
	private String renalCreatineVal;
	private String renalAcidVal;
	private String CTFindingReportFileFileName;
	private String CTFindingReportFileContentType;
	private String MRIFindingDate;
	private String MRIFindingType;
	private String MRIFindingDescription;
	private File MRIFindingReportFile;
	private String MRIFindingReportFileFileName;
	private String MRIFindingReportFileContentType;
	private String OtherFindingDate;
	private String OtherFindingType;
	private String OtherFindingDescription;
	private File OtherFindingReportFile;
	private String OtherFindingReportFileFileName;
	private String OtherFindingReportFileContentType;
	private String[] MRIFindingDateArray;
	private String[] MRIFindingArray;
	private String pageCheck;
	private String[] vitalSignsComment;
	private String symptomDateName;
	private String onExamination;
	private String onExaminationDate;
	private int onExaminationID;
	private String[] drugSection;
	private String[] drugDiscount;
	private String sectionName;
	private String[] newDrugID;
	private String[] newDrugNoOfDays;
	private String[] newDrugFrequency;
	private String[] newDrugCategoryID;
	private String[] newDrugDosage;
	private String[] newDrugQuantity;
	private String[] newDrugComment;
	private String[] newCategoryID;
	private String[] newDrugBarcode;
	private String[] newIsCompound;
	private String[] newCompound;
	private String[] newSection;
	private String[] newInstruction;
	private String[] newDrugDiscount;
	private String[] newDrugRate;
	private int newPrescID;
	private int newDrugNameID;
	private String newDrugName;
	private int newDrugNoOfDaysName;
	private String newDrugFrequencyName;
	private double newDrugDosageName;
	private double newDrugQuantityName;
	private String newDrugCommentName;
	private String newDrugInstructionString;
	private int newCategoryIDName;
	private String newDrugBarcodeName;
	private int newIsCompoundName;
	private String newCompoundName;
	private String newDrugSectionName;
	private double newDrugDiscountName;
	private double newDrugRateName;
	private int freqCount;
	private double totalQuantity;
	private String compoundClassName;
	private String[] newPrescriptionID;
	private String searchCriteria;
	private double totalDiscount;
	private String[] onExamArr;
	private String[] onExamDescArr;
	private String[] XRAYFindingDateArr;
	private String[] XRAYFindingTypeArr;
	private String[] XRAYFindingDescriptionArr;
	private List<File> XRAYReportFile = new ArrayList<File>();
	private List<String> XRAYReportFileContentType = new ArrayList<String>();
	private List<String> XRAYReportFileFileName = new ArrayList<String>();
	private String[] UCGFindingDateArr;
	private String[] UCGFindingTypeArr;
	private String[] UCGFindingDescriptionArr;
	private List<File> UCGReportFile = new ArrayList<File>();
	private List<String> UCGReportFileContentType = new ArrayList<String>();
	private List<String> UCGReportFileFileName = new ArrayList<String>();
	private String[] CTFindingDateArr;
	private String[] CTFindingDescriptionArr;
	private String[] CTFindingTypeArr;
	private List<File> CTReportFile = new ArrayList<File>();
	private List<String> CTReportFileContentType = new ArrayList<String>();
	private List<String> CTReportFileFileName = new ArrayList<String>();
	private String[] MRIFindingDateArr;
	private String[] MRIFindingTypeArr;
	private String[] MRIFindingDescriptionArr;
	private List<File> MRIReportFile = new ArrayList<File>();
	private List<String> MRIReportFileContentType = new ArrayList<String>();
	private List<String> MRIReportFileFileName = new ArrayList<String>();
	private String[] OtherFindingDateArr;
	private String[] OtherFindingTypeArr;
	private String[] OtherFindingDescriptionArr;
	private List<File> OtherReportFile = new ArrayList<File>();
	private List<String> OtherReportFileContentType = new ArrayList<String>();
	private List<String> OtherReportFileFileName = new ArrayList<String>();
	private String symptomStatus;
	private int walkIn;
	private int minQuantity;
	private String supplierName;
	private String[] vitalSignsDate;
	private String documentType;
	private String sugarAvgValue;
	private String identificationFileName;
	private String formDataFileName;
	private String profilePicFileName;
	private String locationLatLong;
	private String patientFormNo;
	private String centerName;
	private String[] chemoUnit;
	private int nextApptTaken;
	private String gp1;
	private String gp2;
	private String gp3;
	private String gp4;
	private String gp5;
	private String gp6;
	private String gp7;
	private String gs1;
	private String gs2;
	private String gs3;
	private String gs4;
	private String gs5;
	private String gs6;
	private String gs7;
	private String q1;
	private String ge1;
	private String ge2;
	private String ge3;
	private String ge4;
	private String ge5;
	private String ge6;
	private String gf1;
	private String gf2;
	private String gf3;
	private String gf4;
	private String gf5;
	private String gf6;
	private String gf7;
	private String savedQuery;
	private String queryTitle;
	private int saveQueryID;
	private String adjustAmtStatus;
	private double adjAmount;
	private String[] newSymptom;
	private String[] newValue;
	private double otherAmount;
	private double cardAmount;
	private String[] editPanel;
	private String[] editInvestigationDetailsID;
	private String[] editValues;
	private int investigationID;
	private String investigation;
	public String nextVisitDate;
	public String advice;
	public int frequencyDetailsID;
	private String[] newEditFrequency;
	private String[] newEditNoOfDays;
	public String biometryComment;
	public String posteriorComment;
	public String scleraOD;
	public String scleraOS;
	private String[] newDuration;
	private String[] newOther;
	private String[] PnewComments;
	private String[] MnewComments;
	private String[] CnewComments;
	private String[] newDiagnosis;
	private String[] newDescription;
	private String[] newDrugname;
	private String personalHistoryAlcohol;
	private String personalHistoryAlcoholDetails;
	private String personalHistoryTobacco;
	private String personalHistoryTobaccoDetails;
	private String personalHistoryFoodChoice;
	private String personalHistoryFoodChoiceDetails;
	private String personalHistoryVeg;
	private String personalHistoryVegDetails;
	private String personalHistoryNonveg;
	private String personalHistoryNonvegDetails;
	private int duration;
	private String other;
	private String comments;
	private int historyID;
	private String[] selectNameArr;
	private String[] tableNameArr;
	private String[] colNameArr;
	private String[] searchTextArr;
	private String[] criteriaNameArr;
	private String initialWhere;
	private String parentTableName;
	private String[] savedQueryParameterValueIDArr;
	private String[] savedQueryParameterIDArr;
	private String travelOutside;
	private String admitted;
	private String suffer_from;
	private String surgeries;
	private String diabOrHypertension;
	private String awareness;
	private String knowFamily;
	private String psychological_issue;
	private String training;
	private String tested_positive;
	private String emergencyContact_name;
	private String emergencyContact_relation;
	private String emergencyContact_mobile;
	private String IDproof;
	private String psychological_issue_details;
	private int volunteerID;
	private String consentType;
	private String readRule;
	private String acceptRule;
	private String infoTrue;
	private String jspPageName;
	private String formName;
	private String categoryType;
	private String diabetesMellitus;
	private double diabetesMellitusDuration;
	private String diabetesMellitusDesc;
	private String hypertension;
	private double hypertensionDuration;
	private String hypertensionDesc;
	private String asthema;
	private double asthemaDuration;
	private String asthemaDesc;
	private String ischemicHeartDisease;
	private double ischemicHeartDiseaseDuration;
	private String ischemicHeartDiseaseDesc;
	private String allergies;
	private double allergiesDuration;
	private String allergiesDesc;
	private String surgicalHistory;
	private double surgicalHistoryDuration;
	private String surgicalHistoryDesc;
	private String gynecologyHistory;
	private double gynecologyHistoryDuration;
	private String gynecologyHistoryDesc;
	private String famHistDiabetesMellitus;
	private double famHistDiabetesMellitusDuration;
	private String famHistDiabetesMellitusDesc;
	private String famHistHypertension;
	private double famHistHypertensionDuration;
	private String famHistHypertensionDesc;
	private String famHistAsthema;
	private double famHistAsthemaDuration;
	private String famHistAsthemaDesc;
	private String famHistAllergies;
	private double famHistAllergiesDuration;
	private String famHistAllergiesDesc;
	private String complaints;
	private String[] otherComplaint;
	private double height;
	private double temperature;
	private double respiration;
	private String pallor;
	private String icterus;
	private double abdominalCircumference;
	private double bmi;
	private String rsPhonchi;
	private String rsCrepitation;
	private String rsClear;
	private String s1s2;
	private String murmur;
	private double murmurDia;
	private String wnl;
	private String cnsWNL;
	private String cnsOther;
	private String abdomen;
	private String rightHypochondriac;
	private String epigastric;
	private String leftHypochondriac;
	private String rightLumbar;
	private String umbilical;
	private String leftLumbar;
	private String rightIliac;
	private String hypogastric;
	private String leftIliac;
	private double murmurSys;
	public int nextVisitWeeks;
	public int nextVisitMonths;
	private double smokingDuration;
	private String alcohol;
	private double alcoholDuration;
	private String alcoholDesc;
	private String mishari;
	private double mishariDuration;
	private String mishariDesc;
	private String tobacco;
	private double tobaccoDuration;
	private String tobaccoDesc;
	private int physiotherapyID;
	private String physiotherapy;
	private List<String> ComplaintsListValues;
	private double emergencyCharges;
	public double mlcCharges;
	public double ambulanceDoctorCharges;
	private double dosageAfterMeal;
	private double dosageBeforeMeal;
	private double dosageAfterDinner;
	private String[] newDrugDuration;
	private String[] newDrugDosageBefore;
	private String[] newDrugDosageAfter;
	private String[] newDrugDosageAfterDinner;
	private String[] newDrugDosageComment;
	private String[] chargeType;
	private String[] chargeQuantity;
	private String[] chargeRate;
	private String[] chargeAmount;
	private String[] chargeID;
	private String[] chargeName;
	private int IPDChargeID;
	private String IPDChargeName;
	private String IPDChargeType;
	private double IPDRate;
	private double IPDQuantity;
	private double IPDAmount;
	private String operationName;
	private String consultantName;
	private String anaesthetistName;
	private String OTAssistantName;
	private String otDateTime;
	private int otChargeID;
	private int newVisitRef;
	private int roomTypeID;
	private String[] operation;
	private String[] consultant;
	private String[] OTAssistant;
	private String[] otDateTimeArr;
	private String[] anaesthetist;
	private String[] OTRate;
	private String[] chargeDisbursementArr;
	private int tarrifChargeID;
	private int consultantChargeID;
	private String[] editChargeType;
	private String[] editChargeQuantity;
	private String[] editChargeRate;
	private String[] editChargeAmount;
	private String[] editChargeID;
	private String[] editChargeName;
	private String[] editOperation;
	private String[] editConsultant;
	private String[] editOTAssistant;
	private String[] editOtDateTimeArr;
	private String[] editAnaesthetist;
	private String[] editOTRate;
	private String[] editOTChargeID;
	private String addButton;
	private String savePrintVisitID;
	private String medicalHistorycomments;
	private String lastMenstrualPeriod;
	private String estimatedDueDate;
	private String groupName;
	private int groupCheck;
	private double groupRate;
	private String subGroup;
	private String referredBy;
	private String sampleID;
	private String[] testID;
	private String[] testRate;
	private String[] testName;
	private int labVisit;
	private int mdDoctorID;
	private String labTestDetails;
	private String searchTestName;
	private List<String> labTestListValues;
	private String[] groupNameVal;
	private String[] groupRateVal;
	private String[] subGroupVal;
	private String[] isGroupVal;
	private String[] rateVal;
	private String[] normalVal;
	private String[] observationsVal;
	private String[] testTypeArr;
	private int isGroup;
	private int labTestID;
	private double totalRate;
	private String template;
	private int reportID;
	private int clinicianID;
	private double visitRate;
	private int weekOfPregnancy;
	private String pcpndtStatus;
	private String chkVal;
	private String txtNoramlVal;
	private int numberOfDaughters;
	private int numberOfSons;
	private String sonAge;
	private String DaughterAge;
	private String pcpNDTResult;
	private String diagnosisRate;
	private int diagnosisID;
	private double itemRate;
	private double itemAmount;
	private String itemName;
	private int itemQuantity;
	private String[] itemNameArr;
	private String[] itemRateArr;
	private String[] itemIDArr;
	private String[] itemQuantityArr;
	private int receiptItemID;
	private String systemicHistory;
	private String occularHistory;
	private String personalHistory;
	private String optomeryComment;
	private String complainingOf;
	private String spectacleComments;
	private String[] defaultSpectacleComments;
	private String distCTCOD;
	private String distHTCOD;
	private String distAtropineOD;
	private String distTPlusOD;
	private String distCTCOS;
	private String distHTCOS;
	private String distAtropineOS;
	private String distTPlusOS;
	private String nearCTCOD;
	private String nearHTCOD;
	private String nearAtropineOD;
	private String nearTPlusOD;
	private String nearCTCOS;
	private String nearHTCOS;
	private String nearAtropineOS;
	private String nearTPlusOS;
	private int cycloplegicRefractionID;
	private String cycloplegicRefraction;
	private String[] defaultCycloplegicRefraction;
	private String dateOfDischarge;
	private String procedure;
	private int procedureID;
	private String admission_time;
	private String discharge_time;
	private String refDoctor;
	private int refDoctorID;
	private String isDiabetes;
	private String otherDetails;
	private String pharmaSearchName;
	public String PupilOD;
	public String PupilOS;
	private int facilityDashboard;
	private String apptSlot;
	private String discountType;
	private String optDiscountType;
	private int isConsultationDone;
	private String action;
	private String[] diagnosticArray;
	private String[] procedureArray;
	private String diagnostic;
	private int diagnosticID;
	private String testType;
	private String[] clinicianIDArr;
	private String[] reportIDArr;
	private String[] templateArr;
	private String[] investigationIDArr;
	private String[] visitTemplateIDArr;
	private String templateName;
	private String templateType;
	private int templateID;
	private int visitTemplateID;
	
	private String leftEyeHistory; // New Field 1
	private String leftEyeDuration; // New Field 2
	private String rightEyeHistory; // New Field 3
	private String rightEyeDuration; // New Filed 4
	
	private String dilationStartTime;
	private String dilationEndTime;
	private String dilationDuration;
	
	/**
	 * @return the isDispatched
	 */
	public int[] getIsDispatched() {
		return isDispatched;
	}

	/**
	 * @param isDispatched the isDispatched to set
	 */
	public void setIsDispatched(int[] isDispatched) {
		this.isDispatched = isDispatched;
	}

	
	/**
	 * @return the selectedPrintField
	 */
	public List<String> getSelectedPrintField() {
		return selectedPrintField;
	}

	/**
	 * @param selectedPrintField the selectedPrintField to set
	 */
	public void setSelectedPrintField(List<String> selectedPrintField) {
		this.selectedPrintField = selectedPrintField;
	}
	
	/**
	 * @return the procedureArray
	 */
	public String[] getProcedureArray() {
		return procedureArray;
	}

	/**
	 * @param procedureArray the procedureArray to set
	 */
	public void setProcedureArray(String[] procedureArray) {
		this.procedureArray = procedureArray;
	}
	
	
	/**
	 * @return the isReportDispatched
	 */
	public Boolean getIsReportDispatched() {
		return isReportDispatched;
	}

	/**
	 * @param isReportDispatched the isReportDispatched to set
	 */
	public void setIsReportDispatched(Boolean isReportDispatched) {
		this.isReportDispatched = isReportDispatched;
	}
	

	/**
	 * @return the dilationStartTime
	 */
	public String getDilationStartTime() {
		return dilationStartTime;
	}

	/**
	 * @param dilationStartTime the dilationStartTime to set
	 */
	public void setDilationStartTime(String dilationStartTime) {
		this.dilationStartTime = dilationStartTime;
	}

	/**
	 * @return the dilationEndTime
	 */
	public String getDilationEndTime() {
		return dilationEndTime;
	}

	/**
	 * @param dilationEndTime the dilationEndTime to set
	 */
	public void setDilationEndTime(String dilationEndTime) {
		this.dilationEndTime = dilationEndTime;
	}

	/**
	 * @return the dilationDuration
	 */
	public String getDilationDuration() {
		return dilationDuration;
	}

	/**
	 * @param dilationDuration the dilationDuration to set
	 */
	public void setDilationDuration(String dilationDuration) {
		this.dilationDuration = dilationDuration;
	}

	/**
	 * @return the leftEyeHistory
	 */
	public String getLeftEyeHistory() {
		return leftEyeHistory;
	}

	/**
	 * @param leftEyeHistory the leftEyeHistory to set
	 */
	public void setLeftEyeHistory(String leftEyeHistory) {
		this.leftEyeHistory = leftEyeHistory;
	}

	/**
	 * @return the leftEyeDuration
	 */
	public String getLeftEyeDuration() {
		return leftEyeDuration;
	}

	/**
	 * @param leftEyeDuration the leftEyeDuration to set
	 */
	public void setLeftEyeDuration(String leftEyeDuration) {
		this.leftEyeDuration = leftEyeDuration;
	}

	/**
	 * @return the rightEyeHistory
	 */
	public String getRightEyeHistory() {
		return rightEyeHistory;
	}

	/**
	 * @param rightEyeHistory the rightEyeHistory to set
	 */
	public void setRightEyeHistory(String rightEyeHistory) {
		this.rightEyeHistory = rightEyeHistory;
	}

	/**
	 * @return the rightEyeDuration
	 */
	public String getRightEyeDuration() {
		return rightEyeDuration;
	}

	/**
	 * @param rightEyeDuration the rightEyeDuration to set
	 */
	public void setRightEyeDuration(String rightEyeDuration) {
		this.rightEyeDuration = rightEyeDuration;
	}

	/**
	 * @return the visitTemplateIDArr
	 */
	public String[] getVisitTemplateIDArr() {
		return visitTemplateIDArr;
	}

	/**
	 * @param visitTemplateIDArr the visitTemplateIDArr to set
	 */
	public void setVisitTemplateIDArr(String[] visitTemplateIDArr) {
		this.visitTemplateIDArr = visitTemplateIDArr;
	}

	/**
	 * @return the visitTemplateID
	 */
	public int getVisitTemplateID() {
		return visitTemplateID;
	}

	/**
	 * @param visitTemplateID the visitTemplateID to set
	 */
	public void setVisitTemplateID(int visitTemplateID) {
		this.visitTemplateID = visitTemplateID;
	}

	/**
	 * @return the templateID
	 */
	public int getTemplateID() {
		return templateID;
	}

	/**
	 * @param templateID the templateID to set
	 */
	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}

	/**
	 * @return the investigationIDArr
	 */
	public String[] getInvestigationIDArr() {
		return investigationIDArr;
	}

	/**
	 * @param investigationIDArr the investigationIDArr to set
	 */
	public void setInvestigationIDArr(String[] investigationIDArr) {
		this.investigationIDArr = investigationIDArr;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}

	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * @return the clinicianIDArr
	 */
	public String[] getClinicianIDArr() {
		return clinicianIDArr;
	}

	/**
	 * @param clinicianIDArr the clinicianIDArr to set
	 */
	public void setClinicianIDArr(String[] clinicianIDArr) {
		this.clinicianIDArr = clinicianIDArr;
	}

	/**
	 * @return the reportIDArr
	 */
	public String[] getReportIDArr() {
		return reportIDArr;
	}

	/**
	 * @param reportIDArr the reportIDArr to set
	 */
	public void setReportIDArr(String[] reportIDArr) {
		this.reportIDArr = reportIDArr;
	}

	/**
	 * @return the templateArr
	 */
	public String[] getTemplateArr() {
		return templateArr;
	}

	/**
	 * @param templateArr the templateArr to set
	 */
	public void setTemplateArr(String[] templateArr) {
		this.templateArr = templateArr;
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
	 * @return the testTypeArr
	 */
	public String[] getTestTypeArr() {
		return testTypeArr;
	}

	/**
	 * @param testTypeArr the testTypeArr to set
	 */
	public void setTestTypeArr(String[] testTypeArr) {
		this.testTypeArr = testTypeArr;
	}

	/**
	 * @return the diagnosticID
	 */
	public int getDiagnosticID() {
		return diagnosticID;
	}

	/**
	 * @param diagnosticID the diagnosticID to set
	 */
	public void setDiagnosticID(int diagnosticID) {
		this.diagnosticID = diagnosticID;
	}

	/**
	 * @return the diagnosticArray
	 */
	public String[] getDiagnosticArray() {
		return diagnosticArray;
	}

	/**
	 * @param diagnosticArray the diagnosticArray to set
	 */
	public void setDiagnosticArray(String[] diagnosticArray) {
		this.diagnosticArray = diagnosticArray;
	}

	/**
	 * @return the diagnostic
	 */
	public String getDiagnostic() {
		return diagnostic;
	}

	/**
	 * @param diagnostic the diagnostic to set
	 */
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the optDiscountType
	 */
	public String getOptDiscountType() {
		return optDiscountType;
	}

	/**
	 * @param optDiscountType the optDiscountType to set
	 */
	public void setOptDiscountType(String optDiscountType) {
		this.optDiscountType = optDiscountType;
	}

	/**
	 * @return the itemQuantityArr
	 */
	public String[] getItemQuantityArr() {
		return itemQuantityArr;
	}

	/**
	 * @param itemQuantityArr the itemQuantityArr to set
	 */
	public void setItemQuantityArr(String[] itemQuantityArr) {
		this.itemQuantityArr = itemQuantityArr;
	}

	/**
	 * @return the isConsultationDone
	 */
	public int getIsConsultationDone() {
		return isConsultationDone;
	}

	/**
	 * @param isConsultationDone the isConsultationDone to set
	 */
	public void setIsConsultationDone(int isConsultationDone) {
		this.isConsultationDone = isConsultationDone;
	}

	/**
	 * @return the discountType
	 */
	public String getDiscountType() {
		return discountType;
	}

	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	/**
	 * @return the apptSlot
	 */
	public String getApptSlot() {
		return apptSlot;
	}

	/**
	 * @param apptSlot the apptSlot to set
	 */
	public void setApptSlot(String apptSlot) {
		this.apptSlot = apptSlot;
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
	 * @return the pupilOD
	 */
	public String getPupilOD() {
		return PupilOD;
	}

	/**
	 * @param pupilOD the pupilOD to set
	 */
	public void setPupilOD(String pupilOD) {
		PupilOD = pupilOD;
	}

	/**
	 * @return the pupilOS
	 */
	public String getPupilOS() {
		return PupilOS;
	}

	/**
	 * @param pupilOS the pupilOS to set
	 */
	public void setPupilOS(String pupilOS) {
		PupilOS = pupilOS;
	}

	/**
	 * @return the pharmaSearchName
	 */
	public String getPharmaSearchName() {
		return pharmaSearchName;
	}

	/**
	 * @param pharmaSearchName the pharmaSearchName to set
	 */
	public void setPharmaSearchName(String pharmaSearchName) {
		this.pharmaSearchName = pharmaSearchName;
	}

	/**
	 * @return the refDoctor
	 */
	public String getRefDoctor() {
		return refDoctor;
	}

	/**
	 * @return the refDoctorID
	 */
	public int getRefDoctorID() {
		return refDoctorID;
	}

	/**
	 * @return the isDiabetes
	 */
	public String getIsDiabetes() {
		return isDiabetes;
	}

	/**
	 * @return the otherDetails
	 */
	public String getOtherDetails() {
		return otherDetails;
	}

	/**
	 * @param refDoctor the refDoctor to set
	 */
	public void setRefDoctor(String refDoctor) {
		this.refDoctor = refDoctor;
	}

	/**
	 * @param refDoctorID the refDoctorID to set
	 */
	public void setRefDoctorID(int refDoctorID) {
		this.refDoctorID = refDoctorID;
	}

	/**
	 * @param isDiabetes the isDiabetes to set
	 */
	public void setIsDiabetes(String isDiabetes) {
		this.isDiabetes = isDiabetes;
	}

	/**
	 * @param otherDetails the otherDetails to set
	 */
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	/**
	 * @return the admission_time
	 */
	public String getAdmission_time() {
		return admission_time;
	}

	/**
	 * @return the discharge_time
	 */
	public String getDischarge_time() {
		return discharge_time;
	}

	/**
	 * @param admission_time the admission_time to set
	 */
	public void setAdmission_time(String admission_time) {
		this.admission_time = admission_time;
	}

	/**
	 * @param discharge_time the discharge_time to set
	 */
	public void setDischarge_time(String discharge_time) {
		this.discharge_time = discharge_time;
	}

	/**
	 * @return the dateOfDischarge
	 */
	public String getDateOfDischarge() {
		return dateOfDischarge;
	}

	/**
	 * @return the procedure
	 */
	public String getProcedure() {
		return procedure;
	}

	/**
	 * @param dateOfDischarge the dateOfDischarge to set
	 */
	public void setDateOfDischarge(String dateOfDischarge) {
		this.dateOfDischarge = dateOfDischarge;
	}

	/**
	 * @param procedure the procedure to set
	 */
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	
	/**
	 * @return the procedureID
	 */
	public int getProcedureID() {
		return procedureID;
	}

	/**
	 * @param procedureID the procedureID to set
	 */
	public void setProcedureID(int procedureID) {
		this.procedureID = procedureID;
	}

	/**
	 * @return the cycloplegicRefraction
	 */
	public String getCycloplegicRefraction() {
		return cycloplegicRefraction;
	}

	/**
	 * @param cycloplegicRefraction the cycloplegicRefraction to set
	 */
	public void setCycloplegicRefraction(String cycloplegicRefraction) {
		this.cycloplegicRefraction = cycloplegicRefraction;
	}

	/**
	 * @return the defaultCycloplegicRefraction
	 */
	public String[] getDefaultCycloplegicRefraction() {
		return defaultCycloplegicRefraction;
	}

	/**
	 * @param defaultCycloplegicRefraction the defaultCycloplegicRefraction to set
	 */
	public void setDefaultCycloplegicRefraction(String[] defaultCycloplegicRefraction) {
		this.defaultCycloplegicRefraction = defaultCycloplegicRefraction;
	}

	/**
	 * @return the cycloplegicRefractionID
	 */
	public int getCycloplegicRefractionID() {
		return cycloplegicRefractionID;
	}

	/**
	 * @param cycloplegicRefractionID the cycloplegicRefractionID to set
	 */
	public void setCycloplegicRefractionID(int cycloplegicRefractionID) {
		this.cycloplegicRefractionID = cycloplegicRefractionID;
	}

	/**
	 * @return the distCTCOD
	 */
	public String getDistCTCOD() {
		return distCTCOD;
	}

	/**
	 * @return the distHTCOD
	 */
	public String getDistHTCOD() {
		return distHTCOD;
	}

	/**
	 * @return the distAtropineOD
	 */
	public String getDistAtropineOD() {
		return distAtropineOD;
	}

	/**
	 * @return the distTPlusOD
	 */
	public String getDistTPlusOD() {
		return distTPlusOD;
	}

	/**
	 * @return the distCTCOS
	 */
	public String getDistCTCOS() {
		return distCTCOS;
	}

	/**
	 * @return the distHTCOS
	 */
	public String getDistHTCOS() {
		return distHTCOS;
	}

	/**
	 * @return the distAtropineOS
	 */
	public String getDistAtropineOS() {
		return distAtropineOS;
	}

	/**
	 * @return the distTPlusOS
	 */
	public String getDistTPlusOS() {
		return distTPlusOS;
	}

	/**
	 * @return the nearCTCOD
	 */
	public String getNearCTCOD() {
		return nearCTCOD;
	}

	/**
	 * @return the nearHTCOD
	 */
	public String getNearHTCOD() {
		return nearHTCOD;
	}

	/**
	 * @return the nearAtropineOD
	 */
	public String getNearAtropineOD() {
		return nearAtropineOD;
	}

	/**
	 * @return the nearTPlusOD
	 */
	public String getNearTPlusOD() {
		return nearTPlusOD;
	}

	/**
	 * @return the nearCTCOS
	 */
	public String getNearCTCOS() {
		return nearCTCOS;
	}

	/**
	 * @return the nearHTCOS
	 */
	public String getNearHTCOS() {
		return nearHTCOS;
	}

	/**
	 * @return the nearAtropineOS
	 */
	public String getNearAtropineOS() {
		return nearAtropineOS;
	}

	/**
	 * @return the nearTPlusOS
	 */
	public String getNearTPlusOS() {
		return nearTPlusOS;
	}

	/**
	 * @param distCTCOD the distCTCOD to set
	 */
	public void setDistCTCOD(String distCTCOD) {
		this.distCTCOD = distCTCOD;
	}

	/**
	 * @param distHTCOD the distHTCOD to set
	 */
	public void setDistHTCOD(String distHTCOD) {
		this.distHTCOD = distHTCOD;
	}

	/**
	 * @param distAtropineOD the distAtropineOD to set
	 */
	public void setDistAtropineOD(String distAtropineOD) {
		this.distAtropineOD = distAtropineOD;
	}

	/**
	 * @param distTPlusOD the distTPlusOD to set
	 */
	public void setDistTPlusOD(String distTPlusOD) {
		this.distTPlusOD = distTPlusOD;
	}

	/**
	 * @param distCTCOS the distCTCOS to set
	 */
	public void setDistCTCOS(String distCTCOS) {
		this.distCTCOS = distCTCOS;
	}

	/**
	 * @param distHTCOS the distHTCOS to set
	 */
	public void setDistHTCOS(String distHTCOS) {
		this.distHTCOS = distHTCOS;
	}

	/**
	 * @param distAtropineOS the distAtropineOS to set
	 */
	public void setDistAtropineOS(String distAtropineOS) {
		this.distAtropineOS = distAtropineOS;
	}

	/**
	 * @param distTPlusOS the distTPlusOS to set
	 */
	public void setDistTPlusOS(String distTPlusOS) {
		this.distTPlusOS = distTPlusOS;
	}

	/**
	 * @param nearCTCOD the nearCTCOD to set
	 */
	public void setNearCTCOD(String nearCTCOD) {
		this.nearCTCOD = nearCTCOD;
	}

	/**
	 * @param nearHTCOD the nearHTCOD to set
	 */
	public void setNearHTCOD(String nearHTCOD) {
		this.nearHTCOD = nearHTCOD;
	}

	/**
	 * @param nearAtropineOD the nearAtropineOD to set
	 */
	public void setNearAtropineOD(String nearAtropineOD) {
		this.nearAtropineOD = nearAtropineOD;
	}

	/**
	 * @param nearTPlusOD the nearTPlusOD to set
	 */
	public void setNearTPlusOD(String nearTPlusOD) {
		this.nearTPlusOD = nearTPlusOD;
	}

	/**
	 * @param nearCTCOS the nearCTCOS to set
	 */
	public void setNearCTCOS(String nearCTCOS) {
		this.nearCTCOS = nearCTCOS;
	}

	/**
	 * @param nearHTCOS the nearHTCOS to set
	 */
	public void setNearHTCOS(String nearHTCOS) {
		this.nearHTCOS = nearHTCOS;
	}

	/**
	 * @param nearAtropineOS the nearAtropineOS to set
	 */
	public void setNearAtropineOS(String nearAtropineOS) {
		this.nearAtropineOS = nearAtropineOS;
	}

	/**
	 * @param nearTPlusOS the nearTPlusOS to set
	 */
	public void setNearTPlusOS(String nearTPlusOS) {
		this.nearTPlusOS = nearTPlusOS;
	}

	/**
	 * @return the defaultSpectacleComments
	 */
	public String[] getDefaultSpectacleComments() {
		return defaultSpectacleComments;
	}

	/**
	 * @param defaultSpectacleComments the defaultSpectacleComments to set
	 */
	public void setDefaultSpectacleComments(String[] defaultSpectacleComments) {
		this.defaultSpectacleComments = defaultSpectacleComments;
	}

	/**
	 * @return the spectacleComments
	 */
	public String getSpectacleComments() {
		return spectacleComments;
	}

	/**
	 * @param spectacleComments the spectacleComments to set
	 */
	public void setSpectacleComments(String spectacleComments) {
		this.spectacleComments = spectacleComments;
	}

	/**
	 * @return the complainingOf
	 */
	public String getComplainingOf() {
		return complainingOf;
	}

	/**
	 * @param complainingOf the complainingOf to set
	 */
	public void setComplainingOf(String complainingOf) {
		this.complainingOf = complainingOf;
	}

	/**
	 * @return the optomeryComment
	 */
	public String getOptomeryComment() {
		return optomeryComment;
	}

	/**
	 * @param optomeryComment the optomeryComment to set
	 */
	public void setOptomeryComment(String optomeryComment) {
		this.optomeryComment = optomeryComment;
	}

	/**
	 * @return the systemicHistory
	 */
	public String getSystemicHistory() {
		return systemicHistory;
	}

	/**
	 * @param systemicHistory the systemicHistory to set
	 */
	public void setSystemicHistory(String systemicHistory) {
		this.systemicHistory = systemicHistory;
	}

	/**
	 * @return the occularHistory
	 */
	public String getOccularHistory() {
		return occularHistory;
	}

	/**
	 * @param occularHistory the occularHistory to set
	 */
	public void setOccularHistory(String occularHistory) {
		this.occularHistory = occularHistory;
	}

	/**
	 * @return the personalcHistory
	 */
	public String getPersonalHistory() {
		return personalHistory;
	}

	/**
	 * @param personalcHistory the personalcHistory to set
	 */
	public void setPersonalHistory(String personalHistory) {
		this.personalHistory = personalHistory;
	}

	/**
	 * @return the receiptItemID
	 */
	public int getReceiptItemID() {
		return receiptItemID;
	}

	/**
	 * @param receiptItemID the receiptItemID to set
	 */
	public void setReceiptItemID(int receiptItemID) {
		this.receiptItemID = receiptItemID;
	}

	/**
	 * @return the itemIDArr
	 */
	public String[] getItemIDArr() {
		return itemIDArr;
	}

	/**
	 * @param itemIDArr the itemIDArr to set
	 */
	public void setItemIDArr(String[] itemIDArr) {
		this.itemIDArr = itemIDArr;
	}

	/**
	 * @return the itemNameArr
	 */
	public String[] getItemNameArr() {
		return itemNameArr;
	}

	/**
	 * @param itemNameArr the itemNameArr to set
	 */
	public void setItemNameArr(String[] itemNameArr) {
		this.itemNameArr = itemNameArr;
	}

	/**
	 * @return the itemRateArr
	 */
	public String[] getItemRateArr() {
		return itemRateArr;
	}

	/**
	 * @param itemRateArr the itemRateArr to set
	 */
	public void setItemRateArr(String[] itemRateArr) {
		this.itemRateArr = itemRateArr;
	}

	/**
	 * @return the itemRate
	 */
	public double getItemRate() {
		return itemRate;
	}

	/**
	 * @param itemRate the itemRate to set
	 */
	public void setItemRate(double itemRate) {
		this.itemRate = itemRate;
	}

	/**
	 * @return the itemAmount
	 */
	public double getItemAmount() {
		return itemAmount;
	}

	/**
	 * @param itemAmount the itemAmount to set
	 */
	public void setItemAmount(double itemAmount) {
		this.itemAmount = itemAmount;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemQuantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * @param itemQuantity the itemQuantity to set
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
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

	/**
	 * @return the diagnosisRate
	 */
	public String getDiagnosisRate() {
		return diagnosisRate;
	}

	/**
	 * @param diagnosisRate the diagnosisRate to set
	 */
	public void setDiagnosisRate(String diagnosisRate) {
		this.diagnosisRate = diagnosisRate;
	}

	/**
	 * @return the pcpNDTResult
	 */
	public String getPcpNDTResult() {
		return pcpNDTResult;
	}

	/**
	 * @param pcpNDTResult the pcpNDTResult to set
	 */
	public void setPcpNDTResult(String pcpNDTResult) {
		this.pcpNDTResult = pcpNDTResult;
	}

	/**
	 * @return the sonAge
	 */
	public String getSonAge() {
		return sonAge;
	}

	/**
	 * @param sonAge the sonAge to set
	 */
	public void setSonAge(String sonAge) {
		this.sonAge = sonAge;
	}

	/**
	 * @return the daughterAge
	 */
	public String getDaughterAge() {
		return DaughterAge;
	}

	/**
	 * @param daughterAge the daughterAge to set
	 */
	public void setDaughterAge(String daughterAge) {
		DaughterAge = daughterAge;
	}

	/**
	 * @return the numberOfDaughters
	 */
	public int getNumberOfDaughters() {
		return numberOfDaughters;
	}

	/**
	 * @param numberOfDaughters the numberOfDaughters to set
	 */
	public void setNumberOfDaughters(int numberOfDaughters) {
		this.numberOfDaughters = numberOfDaughters;
	}

	/**
	 * @return the numberOfSons
	 */
	public int getNumberOfSons() {
		return numberOfSons;
	}

	/**
	 * @param numberOfSons the numberOfSons to set
	 */
	public void setNumberOfSons(int numberOfSons) {
		this.numberOfSons = numberOfSons;
	}

	/**
	 * @return the chkVal
	 */
	public String getChkVal() {
		return chkVal;
	}

	/**
	 * @param chkVal the chkVal to set
	 */
	public void setChkVal(String chkVal) {
		this.chkVal = chkVal;
	}

	/**
	 * @return the txtNoramlVal
	 */
	public String getTxtNoramlVal() {
		return txtNoramlVal;
	}

	/**
	 * @param txtNoramlVal the txtNoramlVal to set
	 */
	public void setTxtNoramlVal(String txtNoramlVal) {
		this.txtNoramlVal = txtNoramlVal;
	}

	public String getPcpndtStatus() {
		return pcpndtStatus;
	}

	public void setPcpndtStatus(String pcpndtStatus) {
		this.pcpndtStatus = pcpndtStatus;
	}

	/**
	 * @return the weekOfPregnancy
	 */
	public int getWeekOfPregnancy() {
		return weekOfPregnancy;
	}

	/**
	 * @param weekOfPregnancy the weekOfPregnancy to set
	 */
	public void setWeekOfPregnancy(int weekOfPregnancy) {
		this.weekOfPregnancy = weekOfPregnancy;
	}

	/**
	 * @return the visitRate
	 */
	public double getVisitRate() {
		return visitRate;
	}

	/**
	 * @param visitRate the visitRate to set
	 */
	public void setVisitRate(double visitRate) {
		this.visitRate = visitRate;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public int getClinicianID() {
		return clinicianID;
	}

	public void setClinicianID(int clinicianID) {
		this.clinicianID = clinicianID;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSavePrintVisitID() {
		return savePrintVisitID;
	}

	public void setSavePrintVisitID(String savePrintVisitID) {
		this.savePrintVisitID = savePrintVisitID;
	}

	public double getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(double totalRate) {
		this.totalRate = totalRate;
	}

	public int getLabTestID() {
		return labTestID;
	}

	public void setLabTestID(int labTestID) {
		this.labTestID = labTestID;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String[] getNormalVal() {
		return normalVal;
	}

	public void setNormalVal(String[] normalVal) {
		this.normalVal = normalVal;
	}

	public String[] getGroupNameVal() {
		return groupNameVal;
	}

	public void setGroupNameVal(String[] groupNameVal) {
		this.groupNameVal = groupNameVal;
	}

	public String[] getGroupRateVal() {
		return groupRateVal;
	}

	public void setGroupRateVal(String[] groupRateVal) {
		this.groupRateVal = groupRateVal;
	}

	public String[] getSubGroupVal() {
		return subGroupVal;
	}

	public void setSubGroupVal(String[] subGroupVal) {
		this.subGroupVal = subGroupVal;
	}

	public String[] getIsGroupVal() {
		return isGroupVal;
	}

	public void setIsGroupVal(String[] isGroupVal) {
		this.isGroupVal = isGroupVal;
	}

	public String[] getRateVal() {
		return rateVal;
	}

	public void setRateVal(String[] rateVal) {
		this.rateVal = rateVal;
	}

	public String[] getObservationsVal() {
		return observationsVal;
	}

	public void setObservationsVal(String[] observationsVal) {
		this.observationsVal = observationsVal;
	}

	public List<String> getLabTestListValues() {
		return labTestListValues;
	}

	public void setLabTestListValues(List<String> labTestListValues) {
		this.labTestListValues = labTestListValues;
	}

	public String getSearchTestName() {
		return searchTestName;
	}

	public void setSearchTestName(String searchTestName) {
		this.searchTestName = searchTestName;
	}

	public String getLabTestDetails() {
		return labTestDetails;
	}

	public void setLabTestDetails(String labTestDetails) {
		this.labTestDetails = labTestDetails;
	}

	public int getMdDoctorID() {
		return mdDoctorID;
	}

	public void setMdDoctorID(int mdDoctorID) {
		this.mdDoctorID = mdDoctorID;
	}

	public int getLabVisit() {
		return labVisit;
	}

	public void setLabVisit(int labVisit) {
		this.labVisit = labVisit;
	}

	public String[] getTestID() {
		return testID;
	}

	public void setTestID(String[] testID) {
		this.testID = testID;
	}

	public String[] getTestRate() {
		return testRate;
	}

	public void setTestRate(String[] testRate) {
		this.testRate = testRate;
	}

	public String[] getTestName() {
		return testName;
	}

	public void setTestName(String[] testName) {
		this.testName = testName;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupCheck() {
		return groupCheck;
	}

	public void setGroupCheck(int groupCheck) {
		this.groupCheck = groupCheck;
	}

	public double getGroupRate() {
		return groupRate;
	}

	public void setGroupRate(double groupRate) {
		this.groupRate = groupRate;
	}

	public String getMedicalHistorycomments() {
		return medicalHistorycomments;
	}

	public void setMedicalHistorycomments(String medicalHistorycomments) {
		this.medicalHistorycomments = medicalHistorycomments;
	}

	public String getLastMenstrualPeriod() {
		return lastMenstrualPeriod;
	}

	public void setLastMenstrualPeriod(String lastMenstrualPeriod) {
		this.lastMenstrualPeriod = lastMenstrualPeriod;
	}

	public String getEstimatedDueDate() {
		return estimatedDueDate;
	}

	public void setEstimatedDueDate(String estimatedDueDate) {
		this.estimatedDueDate = estimatedDueDate;
	}

	/**
	 * @return the addButton
	 */
	public String getAddButton() {
		return addButton;
	}

	/**
	 * @param addButton the addButton to set
	 */
	public void setAddButton(String addButton) {
		this.addButton = addButton;
	}

	/**
	 * @return the editOperation
	 */
	public String[] getEditOperation() {
		return editOperation;
	}

	/**
	 * @param editOperation the editOperation to set
	 */
	public void setEditOperation(String[] editOperation) {
		this.editOperation = editOperation;
	}

	/**
	 * @return the editConsultant
	 */
	public String[] getEditConsultant() {
		return editConsultant;
	}

	/**
	 * @param editConsultant the editConsultant to set
	 */
	public void setEditConsultant(String[] editConsultant) {
		this.editConsultant = editConsultant;
	}

	/**
	 * @return the editOTAssistant
	 */
	public String[] getEditOTAssistant() {
		return editOTAssistant;
	}

	/**
	 * @param editOTAssistant the editOTAssistant to set
	 */
	public void setEditOTAssistant(String[] editOTAssistant) {
		this.editOTAssistant = editOTAssistant;
	}

	/**
	 * @return the editOtDateTimeArr
	 */
	public String[] getEditOtDateTimeArr() {
		return editOtDateTimeArr;
	}

	/**
	 * @param editOtDateTimeArr the editOtDateTimeArr to set
	 */
	public void setEditOtDateTimeArr(String[] editOtDateTimeArr) {
		this.editOtDateTimeArr = editOtDateTimeArr;
	}

	/**
	 * @return the editAnaesthetist
	 */
	public String[] getEditAnaesthetist() {
		return editAnaesthetist;
	}

	/**
	 * @param editAnaesthetist the editAnaesthetist to set
	 */
	public void setEditAnaesthetist(String[] editAnaesthetist) {
		this.editAnaesthetist = editAnaesthetist;
	}

	/**
	 * @return the editOTRate
	 */
	public String[] getEditOTRate() {
		return editOTRate;
	}

	/**
	 * @param editOTRate the editOTRate to set
	 */
	public void setEditOTRate(String[] editOTRate) {
		this.editOTRate = editOTRate;
	}

	/**
	 * @return the editOTChargeID
	 */
	public String[] getEditOTChargeID() {
		return editOTChargeID;
	}

	/**
	 * @param editOTChargeID the editOTChargeID to set
	 */
	public void setEditOTChargeID(String[] editOTChargeID) {
		this.editOTChargeID = editOTChargeID;
	}

	/**
	 * @return the editChargeType
	 */
	public String[] getEditChargeType() {
		return editChargeType;
	}

	/**
	 * @param editChargeType the editChargeType to set
	 */
	public void setEditChargeType(String[] editChargeType) {
		this.editChargeType = editChargeType;
	}

	/**
	 * @return the editChargeQuantity
	 */
	public String[] getEditChargeQuantity() {
		return editChargeQuantity;
	}

	/**
	 * @param editChargeQuantity the editChargeQuantity to set
	 */
	public void setEditChargeQuantity(String[] editChargeQuantity) {
		this.editChargeQuantity = editChargeQuantity;
	}

	/**
	 * @return the editChargeRate
	 */
	public String[] getEditChargeRate() {
		return editChargeRate;
	}

	/**
	 * @param editChargeRate the editChargeRate to set
	 */
	public void setEditChargeRate(String[] editChargeRate) {
		this.editChargeRate = editChargeRate;
	}

	/**
	 * @return the editChargeAmount
	 */
	public String[] getEditChargeAmount() {
		return editChargeAmount;
	}

	/**
	 * @param editChargeAmount the editChargeAmount to set
	 */
	public void setEditChargeAmount(String[] editChargeAmount) {
		this.editChargeAmount = editChargeAmount;
	}

	/**
	 * @return the editChargeID
	 */
	public String[] getEditChargeID() {
		return editChargeID;
	}

	/**
	 * @param editChargeID the editChargeID to set
	 */
	public void setEditChargeID(String[] editChargeID) {
		this.editChargeID = editChargeID;
	}

	/**
	 * @return the editChargeName
	 */
	public String[] getEditChargeName() {
		return editChargeName;
	}

	/**
	 * @param editChargeName the editChargeName to set
	 */
	public void setEditChargeName(String[] editChargeName) {
		this.editChargeName = editChargeName;
	}

	/**
	 * @return the tarrifChargeID
	 */
	public int getTarrifChargeID() {
		return tarrifChargeID;
	}

	/**
	 * @param tarrifChargeID the tarrifChargeID to set
	 */
	public void setTarrifChargeID(int tarrifChargeID) {
		this.tarrifChargeID = tarrifChargeID;
	}

	/**
	 * @return the consultantChargeID
	 */
	public int getConsultantChargeID() {
		return consultantChargeID;
	}

	/**
	 * @param consultantChargeID the consultantChargeID to set
	 */
	public void setConsultantChargeID(int consultantChargeID) {
		this.consultantChargeID = consultantChargeID;
	}

	/**
	 * @return the chargeDisbursementArr
	 */
	public String[] getChargeDisbursementArr() {
		return chargeDisbursementArr;
	}

	/**
	 * @param chargeDisbursementArr the chargeDisbursementArr to set
	 */
	public void setChargeDisbursementArr(String[] chargeDisbursementArr) {
		this.chargeDisbursementArr = chargeDisbursementArr;
	}

	/**
	 * @return the operation
	 */
	public String[] getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String[] operation) {
		this.operation = operation;
	}

	/**
	 * @return the consultant
	 */
	public String[] getConsultant() {
		return consultant;
	}

	/**
	 * @param consultant the consultant to set
	 */
	public void setConsultant(String[] consultant) {
		this.consultant = consultant;
	}

	/**
	 * @return the oTAssistant
	 */
	public String[] getOTAssistant() {
		return OTAssistant;
	}

	/**
	 * @param oTAssistant the oTAssistant to set
	 */
	public void setOTAssistant(String[] oTAssistant) {
		OTAssistant = oTAssistant;
	}

	/**
	 * @return the otDateTimeArr
	 */
	public String[] getOtDateTimeArr() {
		return otDateTimeArr;
	}

	/**
	 * @param otDateTimeArr the otDateTimeArr to set
	 */
	public void setOtDateTimeArr(String[] otDateTimeArr) {
		this.otDateTimeArr = otDateTimeArr;
	}

	/**
	 * @return the anaesthetist
	 */
	public String[] getAnaesthetist() {
		return anaesthetist;
	}

	/**
	 * @param anaesthetist the anaesthetist to set
	 */
	public void setAnaesthetist(String[] anaesthetist) {
		this.anaesthetist = anaesthetist;
	}

	/**
	 * @return the oTRate
	 */
	public String[] getOTRate() {
		return OTRate;
	}

	/**
	 * @param oTRate the oTRate to set
	 */
	public void setOTRate(String[] oTRate) {
		OTRate = oTRate;
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
	 * @return the newVisitRef
	 */
	public int getNewVisitRef() {
		return newVisitRef;
	}

	/**
	 * @param newVisitRef the newVisitRef to set
	 */
	public void setNewVisitRef(int newVisitRef) {
		this.newVisitRef = newVisitRef;
	}

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return the consultantName
	 */
	public String getConsultantName() {
		return consultantName;
	}

	/**
	 * @param consultantName the consultantName to set
	 */
	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	/**
	 * @return the anaesthetistName
	 */
	public String getAnaesthetistName() {
		return anaesthetistName;
	}

	/**
	 * @param anaesthetistName the anaesthetistName to set
	 */
	public void setAnaesthetistName(String anaesthetistName) {
		this.anaesthetistName = anaesthetistName;
	}

	/**
	 * @return the oTAssistantName
	 */
	public String getOTAssistantName() {
		return OTAssistantName;
	}

	/**
	 * @param oTAssistantName the oTAssistantName to set
	 */
	public void setOTAssistantName(String oTAssistantName) {
		OTAssistantName = oTAssistantName;
	}

	/**
	 * @return the otDateTime
	 */
	public String getOtDateTime() {
		return otDateTime;
	}

	/**
	 * @param otDateTime the otDateTime to set
	 */
	public void setOtDateTime(String otDateTime) {
		this.otDateTime = otDateTime;
	}

	/**
	 * @return the otChargeID
	 */
	public int getOtChargeID() {
		return otChargeID;
	}

	/**
	 * @param otChargeID the otChargeID to set
	 */
	public void setOtChargeID(int otChargeID) {
		this.otChargeID = otChargeID;
	}

	/**
	 * @return the iPDChargeID
	 */
	public int getIPDChargeID() {
		return IPDChargeID;
	}

	/**
	 * @param iPDChargeID the iPDChargeID to set
	 */
	public void setIPDChargeID(int iPDChargeID) {
		IPDChargeID = iPDChargeID;
	}

	/**
	 * @return the iPDChargeName
	 */
	public String getIPDChargeName() {
		return IPDChargeName;
	}

	/**
	 * @param iPDChargeName the iPDChargeName to set
	 */
	public void setIPDChargeName(String iPDChargeName) {
		IPDChargeName = iPDChargeName;
	}

	/**
	 * @return the iPDChargeType
	 */
	public String getIPDChargeType() {
		return IPDChargeType;
	}

	/**
	 * @param iPDChargeType the iPDChargeType to set
	 */
	public void setIPDChargeType(String iPDChargeType) {
		IPDChargeType = iPDChargeType;
	}

	/**
	 * @return the iPDRate
	 */
	public double getIPDRate() {
		return IPDRate;
	}

	/**
	 * @param iPDRate the iPDRate to set
	 */
	public void setIPDRate(double iPDRate) {
		IPDRate = iPDRate;
	}

	/**
	 * @return the iPDQuantity
	 */
	public double getIPDQuantity() {
		return IPDQuantity;
	}

	/**
	 * @param iPDQuantity the iPDQuantity to set
	 */
	public void setIPDQuantity(double iPDQuantity) {
		IPDQuantity = iPDQuantity;
	}

	/**
	 * @return the iPDAmount
	 */
	public double getIPDAmount() {
		return IPDAmount;
	}

	/**
	 * @param iPDAmount the iPDAmount to set
	 */
	public void setIPDAmount(double iPDAmount) {
		IPDAmount = iPDAmount;
	}

	/**
	 * @return the chargeType
	 */
	public String[] getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String[] chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the chargeQuantity
	 */
	public String[] getChargeQuantity() {
		return chargeQuantity;
	}

	/**
	 * @param chargeQuantity the chargeQuantity to set
	 */
	public void setChargeQuantity(String[] chargeQuantity) {
		this.chargeQuantity = chargeQuantity;
	}

	/**
	 * @return the chargeRate
	 */
	public String[] getChargeRate() {
		return chargeRate;
	}

	/**
	 * @param chargeRate the chargeRate to set
	 */
	public void setChargeRate(String[] chargeRate) {
		this.chargeRate = chargeRate;
	}

	/**
	 * @return the chargeAmount
	 */
	public String[] getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount the chargeAmount to set
	 */
	public void setChargeAmount(String[] chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @return the chargeID
	 */
	public String[] getChargeID() {
		return chargeID;
	}

	/**
	 * @param chargeID the chargeID to set
	 */
	public void setChargeID(String[] chargeID) {
		this.chargeID = chargeID;
	}

	/**
	 * @return the chargeName
	 */
	public String[] getChargeName() {
		return chargeName;
	}

	/**
	 * @param chargeName the chargeName to set
	 */
	public void setChargeName(String[] chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * @return the newDrugDosageAfterDinner
	 */
	public String[] getNewDrugDosageAfterDinner() {
		return newDrugDosageAfterDinner;
	}

	/**
	 * @param newDrugDosageAfterDinner the newDrugDosageAfterDinner to set
	 */
	public void setNewDrugDosageAfterDinner(String[] newDrugDosageAfterDinner) {
		this.newDrugDosageAfterDinner = newDrugDosageAfterDinner;
	}

	/**
	 * @return the newDrugDosageComment
	 */
	public String[] getNewDrugDosageComment() {
		return newDrugDosageComment;
	}

	/**
	 * @param newDrugDosageComment the newDrugDosageComment to set
	 */
	public void setNewDrugDosageComment(String[] newDrugDosageComment) {
		this.newDrugDosageComment = newDrugDosageComment;
	}

	/**
	 * @return the newDrugDuration
	 */
	public String[] getNewDrugDuration() {
		return newDrugDuration;
	}

	/**
	 * @param newDrugDuration the newDrugDuration to set
	 */
	public void setNewDrugDuration(String[] newDrugDuration) {
		this.newDrugDuration = newDrugDuration;
	}

	/**
	 * @return the newDrugDosageBefore
	 */
	public String[] getNewDrugDosageBefore() {
		return newDrugDosageBefore;
	}

	/**
	 * @param newDrugDosageBefore the newDrugDosageBefore to set
	 */
	public void setNewDrugDosageBefore(String[] newDrugDosageBefore) {
		this.newDrugDosageBefore = newDrugDosageBefore;
	}

	/**
	 * @return the newDrugDosageAfter
	 */
	public String[] getNewDrugDosageAfter() {
		return newDrugDosageAfter;
	}

	/**
	 * @param newDrugDosageAfter the newDrugDosageAfter to set
	 */
	public void setNewDrugDosageAfter(String[] newDrugDosageAfter) {
		this.newDrugDosageAfter = newDrugDosageAfter;
	}

	/**
	 * @return the dosageAfterMeal
	 */
	public double getDosageAfterMeal() {
		return dosageAfterMeal;
	}

	/**
	 * @param dosageAfterMeal the dosageAfterMeal to set
	 */
	public void setDosageAfterMeal(double dosageAfterMeal) {
		this.dosageAfterMeal = dosageAfterMeal;
	}

	/**
	 * @return the dosageBeforeMeal
	 */
	public double getDosageBeforeMeal() {
		return dosageBeforeMeal;
	}

	/**
	 * @param dosageBeforeMeal the dosageBeforeMeal to set
	 */
	public void setDosageBeforeMeal(double dosageBeforeMeal) {
		this.dosageBeforeMeal = dosageBeforeMeal;
	}

	/**
	 * @return the dosageAfterDinner
	 */
	public double getDosageAfterDinner() {
		return dosageAfterDinner;
	}

	/**
	 * @param dosageAfterDinner the dosageAfterDinner to set
	 */
	public void setDosageAfterDinner(double dosageAfterDinner) {
		this.dosageAfterDinner = dosageAfterDinner;
	}

	/**
	 * @return the emergencyCharges
	 */
	public double getEmergencyCharges() {
		return emergencyCharges;
	}

	/**
	 * @param emergencyCharges the emergencyCharges to set
	 */
	public void setEmergencyCharges(double emergencyCharges) {
		this.emergencyCharges = emergencyCharges;
	}

	/**
	 * @return the mlcCharges
	 */
	public double getMlcCharges() {
		return mlcCharges;
	}

	/**
	 * @param mlcCharges the mlcCharges to set
	 */
	public void setMlcCharges(double mlcCharges) {
		this.mlcCharges = mlcCharges;
	}

	/**
	 * @return the ambulanceDoctorCharges
	 */
	public double getAmbulanceDoctorCharges() {
		return ambulanceDoctorCharges;
	}

	/**
	 * @param ambulanceDoctorCharges the ambulanceDoctorCharges to set
	 */
	public void setAmbulanceDoctorCharges(double ambulanceDoctorCharges) {
		this.ambulanceDoctorCharges = ambulanceDoctorCharges;
	}

	/**
	 * @return the complaintsListValues
	 */
	public List<String> getComplaintsListValues() {
		return ComplaintsListValues;
	}

	/**
	 * @param complaintsListValues the complaintsListValues to set
	 */
	public void setComplaintsListValues(List<String> complaintsListValues) {
		ComplaintsListValues = complaintsListValues;
	}

	/**
	 * @return the physiotherapyID
	 */
	public int getPhysiotherapyID() {
		return physiotherapyID;
	}

	/**
	 * @param physiotherapyID the physiotherapyID to set
	 */
	public void setPhysiotherapyID(int physiotherapyID) {
		this.physiotherapyID = physiotherapyID;
	}

	/**
	 * @return the physiotherapy
	 */
	public String getPhysiotherapy() {
		return physiotherapy;
	}

	/**
	 * @param physiotherapy the physiotherapy to set
	 */
	public void setPhysiotherapy(String physiotherapy) {
		this.physiotherapy = physiotherapy;
	}

	/**
	 * @return the smokingDuration
	 */
	public double getSmokingDuration() {
		return smokingDuration;
	}

	/**
	 * @param smokingDuration the smokingDuration to set
	 */
	public void setSmokingDuration(double smokingDuration) {
		this.smokingDuration = smokingDuration;
	}

	/**
	 * @return the alcohol
	 */
	public String getAlcohol() {
		return alcohol;
	}

	/**
	 * @param alcohol the alcohol to set
	 */
	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	/**
	 * @return the alcoholDuration
	 */
	public double getAlcoholDuration() {
		return alcoholDuration;
	}

	/**
	 * @param alcoholDuration the alcoholDuration to set
	 */
	public void setAlcoholDuration(double alcoholDuration) {
		this.alcoholDuration = alcoholDuration;
	}

	/**
	 * @return the alcoholDesc
	 */
	public String getAlcoholDesc() {
		return alcoholDesc;
	}

	/**
	 * @param alcoholDesc the alcoholDesc to set
	 */
	public void setAlcoholDesc(String alcoholDesc) {
		this.alcoholDesc = alcoholDesc;
	}

	/**
	 * @return the mishari
	 */
	public String getMishari() {
		return mishari;
	}

	/**
	 * @param mishari the mishari to set
	 */
	public void setMishari(String mishari) {
		this.mishari = mishari;
	}

	/**
	 * @return the mishariDuration
	 */
	public double getMishariDuration() {
		return mishariDuration;
	}

	/**
	 * @param mishariDuration the mishariDuration to set
	 */
	public void setMishariDuration(double mishariDuration) {
		this.mishariDuration = mishariDuration;
	}

	/**
	 * @return the mishariDesc
	 */
	public String getMishariDesc() {
		return mishariDesc;
	}

	/**
	 * @param mishariDesc the mishariDesc to set
	 */
	public void setMishariDesc(String mishariDesc) {
		this.mishariDesc = mishariDesc;
	}

	/**
	 * @return the tobacco
	 */
	public String getTobacco() {
		return tobacco;
	}

	/**
	 * @param tobacco the tobacco to set
	 */
	public void setTobacco(String tobacco) {
		this.tobacco = tobacco;
	}

	/**
	 * @return the tobaccoDuration
	 */
	public double getTobaccoDuration() {
		return tobaccoDuration;
	}

	/**
	 * @param tobaccoDuration the tobaccoDuration to set
	 */
	public void setTobaccoDuration(double tobaccoDuration) {
		this.tobaccoDuration = tobaccoDuration;
	}

	/**
	 * @return the tobaccoDesc
	 */
	public String getTobaccoDesc() {
		return tobaccoDesc;
	}

	/**
	 * @param tobaccoDesc the tobaccoDesc to set
	 */
	public void setTobaccoDesc(String tobaccoDesc) {
		this.tobaccoDesc = tobaccoDesc;
	}

	/**
	 * @return the nextVisitWeeks
	 */
	public int getNextVisitWeeks() {
		return nextVisitWeeks;
	}

	/**
	 * @param nextVisitWeeks the nextVisitWeeks to set
	 */
	public void setNextVisitWeeks(int nextVisitWeeks) {
		this.nextVisitWeeks = nextVisitWeeks;
	}

	/**
	 * @return the nextVisitMonths
	 */
	public int getNextVisitMonths() {
		return nextVisitMonths;
	}

	/**
	 * @param nextVisitMonths the nextVisitMonths to set
	 */
	public void setNextVisitMonths(int nextVisitMonths) {
		this.nextVisitMonths = nextVisitMonths;
	}

	/**
	 * @return the murmurSys
	 */
	public double getMurmurSys() {
		return murmurSys;
	}

	/**
	 * @param murmurSys the murmurSys to set
	 */
	public void setMurmurSys(double murmurSys) {
		this.murmurSys = murmurSys;
	}

	/**
	 * @return the leftIliac
	 */
	public String getLeftIliac() {
		return leftIliac;
	}

	/**
	 * @param leftIliac the leftIliac to set
	 */
	public void setLeftIliac(String leftIliac) {
		this.leftIliac = leftIliac;
	}

	/**
	 * @return the rsPhonchi
	 */
	public String getRsPhonchi() {
		return rsPhonchi;
	}

	/**
	 * @param rsPhonchi the rsPhonchi to set
	 */
	public void setRsPhonchi(String rsPhonchi) {
		this.rsPhonchi = rsPhonchi;
	}

	/**
	 * @return the rsCrepitation
	 */
	public String getRsCrepitation() {
		return rsCrepitation;
	}

	/**
	 * @param rsCrepitation the rsCrepitation to set
	 */
	public void setRsCrepitation(String rsCrepitation) {
		this.rsCrepitation = rsCrepitation;
	}

	/**
	 * @return the rsClear
	 */
	public String getRsClear() {
		return rsClear;
	}

	/**
	 * @param rsClear the rsClear to set
	 */
	public void setRsClear(String rsClear) {
		this.rsClear = rsClear;
	}

	/**
	 * @return the s1s2
	 */
	public String getS1s2() {
		return s1s2;
	}

	/**
	 * @param s1s2 the s1s2 to set
	 */
	public void setS1s2(String s1s2) {
		this.s1s2 = s1s2;
	}

	/**
	 * @return the murmur
	 */
	public String getMurmur() {
		return murmur;
	}

	/**
	 * @param murmur the murmur to set
	 */
	public void setMurmur(String murmur) {
		this.murmur = murmur;
	}

	/**
	 * @return the murmurDia
	 */
	public double getMurmurDia() {
		return murmurDia;
	}

	/**
	 * @param murmurDia the murmurDia to set
	 */
	public void setMurmurDia(double murmurDia) {
		this.murmurDia = murmurDia;
	}

	/**
	 * @return the wnl
	 */
	public String getWnl() {
		return wnl;
	}

	/**
	 * @param wnl the wnl to set
	 */
	public void setWnl(String wnl) {
		this.wnl = wnl;
	}

	/**
	 * @return the cnsWNL
	 */
	public String getCnsWNL() {
		return cnsWNL;
	}

	/**
	 * @param cnsWNL the cnsWNL to set
	 */
	public void setCnsWNL(String cnsWNL) {
		this.cnsWNL = cnsWNL;
	}

	/**
	 * @return the cnsOther
	 */
	public String getCnsOther() {
		return cnsOther;
	}

	/**
	 * @param cnsOther the cnsOther to set
	 */
	public void setCnsOther(String cnsOther) {
		this.cnsOther = cnsOther;
	}

	/**
	 * @return the abdomen
	 */
	public String getAbdomen() {
		return abdomen;
	}

	/**
	 * @param abdomen the abdomen to set
	 */
	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	/**
	 * @return the rightHypochondriac
	 */
	public String getRightHypochondriac() {
		return rightHypochondriac;
	}

	/**
	 * @param rightHypochondriac the rightHypochondriac to set
	 */
	public void setRightHypochondriac(String rightHypochondriac) {
		this.rightHypochondriac = rightHypochondriac;
	}

	/**
	 * @return the epigastric
	 */
	public String getEpigastric() {
		return epigastric;
	}

	/**
	 * @param epigastric the epigastric to set
	 */
	public void setEpigastric(String epigastric) {
		this.epigastric = epigastric;
	}

	/**
	 * @return the leftHypochondriac
	 */
	public String getLeftHypochondriac() {
		return leftHypochondriac;
	}

	/**
	 * @param leftHypochondriac the leftHypochondriac to set
	 */
	public void setLeftHypochondriac(String leftHypochondriac) {
		this.leftHypochondriac = leftHypochondriac;
	}

	/**
	 * @return the rightLumbar
	 */
	public String getRightLumbar() {
		return rightLumbar;
	}

	/**
	 * @param rightLumbar the rightLumbar to set
	 */
	public void setRightLumbar(String rightLumbar) {
		this.rightLumbar = rightLumbar;
	}

	/**
	 * @return the umbilical
	 */
	public String getUmbilical() {
		return umbilical;
	}

	/**
	 * @param umbilical the umbilical to set
	 */
	public void setUmbilical(String umbilical) {
		this.umbilical = umbilical;
	}

	/**
	 * @return the leftLumbar
	 */
	public String getLeftLumbar() {
		return leftLumbar;
	}

	/**
	 * @param leftLumbar the leftLumbar to set
	 */
	public void setLeftLumbar(String leftLumbar) {
		this.leftLumbar = leftLumbar;
	}

	/**
	 * @return the rightIliac
	 */
	public String getRightIliac() {
		return rightIliac;
	}

	/**
	 * @param rightIliac the rightIliac to set
	 */
	public void setRightIliac(String rightIliac) {
		this.rightIliac = rightIliac;
	}

	/**
	 * @return the hypogastric
	 */
	public String getHypogastric() {
		return hypogastric;
	}

	/**
	 * @param hypogastric the hypogastric to set
	 */
	public void setHypogastric(String hypogastric) {
		this.hypogastric = hypogastric;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the respiration
	 */
	public double getRespiration() {
		return respiration;
	}

	/**
	 * @param respiration the respiration to set
	 */
	public void setRespiration(double respiration) {
		this.respiration = respiration;
	}

	/**
	 * @return the pallor
	 */
	public String getPallor() {
		return pallor;
	}

	/**
	 * @param pallor the pallor to set
	 */
	public void setPallor(String pallor) {
		this.pallor = pallor;
	}

	/**
	 * @return the icterus
	 */
	public String getIcterus() {
		return icterus;
	}

	/**
	 * @param icterus the icterus to set
	 */
	public void setIcterus(String icterus) {
		this.icterus = icterus;
	}

	/**
	 * @return the abdominalCircumference
	 */
	public double getAbdominalCircumference() {
		return abdominalCircumference;
	}

	/**
	 * @param abdominalCircumference the abdominalCircumference to set
	 */
	public void setAbdominalCircumference(double abdominalCircumference) {
		this.abdominalCircumference = abdominalCircumference;
	}

	/**
	 * @return the bmi
	 */
	public double getBmi() {
		return bmi;
	}

	/**
	 * @param bmi the bmi to set
	 */
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	/**
	 * @return the otherComplaint
	 */
	public String[] getOtherComplaint() {
		return otherComplaint;
	}

	/**
	 * @param otherComplaint the otherComplaint to set
	 */
	public void setOtherComplaint(String[] otherComplaint) {
		this.otherComplaint = otherComplaint;
	}

	/**
	 * @return the complaints
	 */
	public String getComplaints() {
		return complaints;
	}

	/**
	 * @param complaints the complaints to set
	 */
	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	/**
	 * @return the famHistDiabetesMellitus
	 */
	public String getFamHistDiabetesMellitus() {
		return famHistDiabetesMellitus;
	}

	/**
	 * @param famHistDiabetesMellitus the famHistDiabetesMellitus to set
	 */
	public void setFamHistDiabetesMellitus(String famHistDiabetesMellitus) {
		this.famHistDiabetesMellitus = famHistDiabetesMellitus;
	}

	/**
	 * @return the famHistDiabetesMellitusDuration
	 */
	public double getFamHistDiabetesMellitusDuration() {
		return famHistDiabetesMellitusDuration;
	}

	/**
	 * @param famHistDiabetesMellitusDuration the famHistDiabetesMellitusDuration to
	 *                                        set
	 */
	public void setFamHistDiabetesMellitusDuration(double famHistDiabetesMellitusDuration) {
		this.famHistDiabetesMellitusDuration = famHistDiabetesMellitusDuration;
	}

	/**
	 * @return the famHistDiabetesMellitusDesc
	 */
	public String getFamHistDiabetesMellitusDesc() {
		return famHistDiabetesMellitusDesc;
	}

	/**
	 * @param famHistDiabetesMellitusDesc the famHistDiabetesMellitusDesc to set
	 */
	public void setFamHistDiabetesMellitusDesc(String famHistDiabetesMellitusDesc) {
		this.famHistDiabetesMellitusDesc = famHistDiabetesMellitusDesc;
	}

	/**
	 * @return the famHistHypertension
	 */
	public String getFamHistHypertension() {
		return famHistHypertension;
	}

	/**
	 * @param famHistHypertension the famHistHypertension to set
	 */
	public void setFamHistHypertension(String famHistHypertension) {
		this.famHistHypertension = famHistHypertension;
	}

	/**
	 * @return the famHistHypertensionDuration
	 */
	public double getFamHistHypertensionDuration() {
		return famHistHypertensionDuration;
	}

	/**
	 * @param famHistHypertensionDuration the famHistHypertensionDuration to set
	 */
	public void setFamHistHypertensionDuration(double famHistHypertensionDuration) {
		this.famHistHypertensionDuration = famHistHypertensionDuration;
	}

	/**
	 * @return the famHistHypertensionDesc
	 */
	public String getFamHistHypertensionDesc() {
		return famHistHypertensionDesc;
	}

	/**
	 * @param famHistHypertensionDesc the famHistHypertensionDesc to set
	 */
	public void setFamHistHypertensionDesc(String famHistHypertensionDesc) {
		this.famHistHypertensionDesc = famHistHypertensionDesc;
	}

	/**
	 * @return the famHistAsthema
	 */
	public String getFamHistAsthema() {
		return famHistAsthema;
	}

	/**
	 * @param famHistAsthema the famHistAsthema to set
	 */
	public void setFamHistAsthema(String famHistAsthema) {
		this.famHistAsthema = famHistAsthema;
	}

	/**
	 * @return the famHistAsthemaDuration
	 */
	public double getFamHistAsthemaDuration() {
		return famHistAsthemaDuration;
	}

	/**
	 * @param famHistAsthemaDuration the famHistAsthemaDuration to set
	 */
	public void setFamHistAsthemaDuration(double famHistAsthemaDuration) {
		this.famHistAsthemaDuration = famHistAsthemaDuration;
	}

	/**
	 * @return the famHistAsthemaDesc
	 */
	public String getFamHistAsthemaDesc() {
		return famHistAsthemaDesc;
	}

	/**
	 * @param famHistAsthemaDesc the famHistAsthemaDesc to set
	 */
	public void setFamHistAsthemaDesc(String famHistAsthemaDesc) {
		this.famHistAsthemaDesc = famHistAsthemaDesc;
	}

	/**
	 * @return the famHistAllergies
	 */
	public String getFamHistAllergies() {
		return famHistAllergies;
	}

	/**
	 * @param famHistAllergies the famHistAllergies to set
	 */
	public void setFamHistAllergies(String famHistAllergies) {
		this.famHistAllergies = famHistAllergies;
	}

	/**
	 * @return the famHistAllergiesDuration
	 */
	public double getFamHistAllergiesDuration() {
		return famHistAllergiesDuration;
	}

	/**
	 * @param famHistAllergiesDuration the famHistAllergiesDuration to set
	 */
	public void setFamHistAllergiesDuration(double famHistAllergiesDuration) {
		this.famHistAllergiesDuration = famHistAllergiesDuration;
	}

	/**
	 * @return the famHistAllergiesDesc
	 */
	public String getFamHistAllergiesDesc() {
		return famHistAllergiesDesc;
	}

	/**
	 * @param famHistAllergiesDesc the famHistAllergiesDesc to set
	 */
	public void setFamHistAllergiesDesc(String famHistAllergiesDesc) {
		this.famHistAllergiesDesc = famHistAllergiesDesc;
	}

	/**
	 * @return the diabetesMellitus
	 */
	public String getDiabetesMellitus() {
		return diabetesMellitus;
	}

	/**
	 * @param diabetesMellitus the diabetesMellitus to set
	 */
	public void setDiabetesMellitus(String diabetesMellitus) {
		this.diabetesMellitus = diabetesMellitus;
	}

	/**
	 * @return the diabetesMellitusDuration
	 */
	public double getDiabetesMellitusDuration() {
		return diabetesMellitusDuration;
	}

	/**
	 * @param diabetesMellitusDuration the diabetesMellitusDuration to set
	 */
	public void setDiabetesMellitusDuration(double diabetesMellitusDuration) {
		this.diabetesMellitusDuration = diabetesMellitusDuration;
	}

	/**
	 * @return the diabetesMellitusDesc
	 */
	public String getDiabetesMellitusDesc() {
		return diabetesMellitusDesc;
	}

	/**
	 * @param diabetesMellitusDesc the diabetesMellitusDesc to set
	 */
	public void setDiabetesMellitusDesc(String diabetesMellitusDesc) {
		this.diabetesMellitusDesc = diabetesMellitusDesc;
	}

	/**
	 * @return the hypertension
	 */
	public String getHypertension() {
		return hypertension;
	}

	/**
	 * @param hypertension the hypertension to set
	 */
	public void setHypertension(String hypertension) {
		this.hypertension = hypertension;
	}

	/**
	 * @return the hypertensionDuration
	 */
	public double getHypertensionDuration() {
		return hypertensionDuration;
	}

	/**
	 * @param hypertensionDuration the hypertensionDuration to set
	 */
	public void setHypertensionDuration(double hypertensionDuration) {
		this.hypertensionDuration = hypertensionDuration;
	}

	/**
	 * @return the hypertensionDesc
	 */
	public String getHypertensionDesc() {
		return hypertensionDesc;
	}

	/**
	 * @param hypertensionDesc the hypertensionDesc to set
	 */
	public void setHypertensionDesc(String hypertensionDesc) {
		this.hypertensionDesc = hypertensionDesc;
	}

	/**
	 * @return the asthema
	 */
	public String getAsthema() {
		return asthema;
	}

	/**
	 * @param asthema the asthema to set
	 */
	public void setAsthema(String asthema) {
		this.asthema = asthema;
	}

	/**
	 * @return the asthemaDuration
	 */
	public double getAsthemaDuration() {
		return asthemaDuration;
	}

	/**
	 * @param asthemaDuration the asthemaDuration to set
	 */
	public void setAsthemaDuration(double asthemaDuration) {
		this.asthemaDuration = asthemaDuration;
	}

	/**
	 * @return the asthemaDesc
	 */
	public String getAsthemaDesc() {
		return asthemaDesc;
	}

	/**
	 * @param asthemaDesc the asthemaDesc to set
	 */
	public void setAsthemaDesc(String asthemaDesc) {
		this.asthemaDesc = asthemaDesc;
	}

	/**
	 * @return the ischemicHeartDisease
	 */
	public String getIschemicHeartDisease() {
		return ischemicHeartDisease;
	}

	/**
	 * @param ischemicHeartDisease the ischemicHeartDisease to set
	 */
	public void setIschemicHeartDisease(String ischemicHeartDisease) {
		this.ischemicHeartDisease = ischemicHeartDisease;
	}

	/**
	 * @return the ischemicHeartDiseaseDuration
	 */
	public double getIschemicHeartDiseaseDuration() {
		return ischemicHeartDiseaseDuration;
	}

	/**
	 * @param ischemicHeartDiseaseDuration the ischemicHeartDiseaseDuration to set
	 */
	public void setIschemicHeartDiseaseDuration(double ischemicHeartDiseaseDuration) {
		this.ischemicHeartDiseaseDuration = ischemicHeartDiseaseDuration;
	}

	/**
	 * @return the ischemicHeartDiseaseDesc
	 */
	public String getIschemicHeartDiseaseDesc() {
		return ischemicHeartDiseaseDesc;
	}

	/**
	 * @param ischemicHeartDiseaseDesc the ischemicHeartDiseaseDesc to set
	 */
	public void setIschemicHeartDiseaseDesc(String ischemicHeartDiseaseDesc) {
		this.ischemicHeartDiseaseDesc = ischemicHeartDiseaseDesc;
	}

	/**
	 * @return the allergies
	 */
	public String getAllergies() {
		return allergies;
	}

	/**
	 * @param allergies the allergies to set
	 */
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	/**
	 * @return the allergiesDuration
	 */
	public double getAllergiesDuration() {
		return allergiesDuration;
	}

	/**
	 * @param allergiesDuration the allergiesDuration to set
	 */
	public void setAllergiesDuration(double allergiesDuration) {
		this.allergiesDuration = allergiesDuration;
	}

	/**
	 * @return the allergiesDesc
	 */
	public String getAllergiesDesc() {
		return allergiesDesc;
	}

	/**
	 * @param allergiesDesc the allergiesDesc to set
	 */
	public void setAllergiesDesc(String allergiesDesc) {
		this.allergiesDesc = allergiesDesc;
	}

	/**
	 * @return the surgicalHistory
	 */
	public String getSurgicalHistory() {
		return surgicalHistory;
	}

	/**
	 * @param surgicalHistory the surgicalHistory to set
	 */
	public void setSurgicalHistory(String surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}

	/**
	 * @return the surgicalHistoryDuration
	 */
	public double getSurgicalHistoryDuration() {
		return surgicalHistoryDuration;
	}

	/**
	 * @param surgicalHistoryDuration the surgicalHistoryDuration to set
	 */
	public void setSurgicalHistoryDuration(double surgicalHistoryDuration) {
		this.surgicalHistoryDuration = surgicalHistoryDuration;
	}

	/**
	 * @return the surgicalHistoryDesc
	 */
	public String getSurgicalHistoryDesc() {
		return surgicalHistoryDesc;
	}

	/**
	 * @param surgicalHistoryDesc the surgicalHistoryDesc to set
	 */
	public void setSurgicalHistoryDesc(String surgicalHistoryDesc) {
		this.surgicalHistoryDesc = surgicalHistoryDesc;
	}

	/**
	 * @return the gynecologyHistory
	 */
	public String getGynecologyHistory() {
		return gynecologyHistory;
	}

	/**
	 * @param gynecologyHistory the gynecologyHistory to set
	 */
	public void setGynecologyHistory(String gynecologyHistory) {
		this.gynecologyHistory = gynecologyHistory;
	}

	/**
	 * @return the gynecologyHistoryDuration
	 */
	public double getGynecologyHistoryDuration() {
		return gynecologyHistoryDuration;
	}

	/**
	 * @param gynecologyHistoryDuration the gynecologyHistoryDuration to set
	 */
	public void setGynecologyHistoryDuration(double gynecologyHistoryDuration) {
		this.gynecologyHistoryDuration = gynecologyHistoryDuration;
	}

	/**
	 * @return the gynecologyHistoryDesc
	 */
	public String getGynecologyHistoryDesc() {
		return gynecologyHistoryDesc;
	}

	/**
	 * @param gynecologyHistoryDesc the gynecologyHistoryDesc to set
	 */
	public void setGynecologyHistoryDesc(String gynecologyHistoryDesc) {
		this.gynecologyHistoryDesc = gynecologyHistoryDesc;
	}

	/**
	 * @return the categoryType
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * @return the jspPageName
	 */
	public String getJspPageName() {
		return jspPageName;
	}

	/**
	 * @param jspPageName the jspPageName to set
	 */
	public void setJspPageName(String jspPageName) {
		this.jspPageName = jspPageName;
	}

	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the searchPatientName
	 */
	public String getSearchPatientName() {
		return searchPatientName;
	}

	/**
	 * @param searchPatientName the searchPatientName to set
	 */
	public void setSearchPatientName(String searchPatientName) {
		this.searchPatientName = searchPatientName;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the consentType
	 */
	public String getConsentType() {
		return consentType;
	}

	/**
	 * @param consentType the consentType to set
	 */
	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}

	/**
	 * @return the readRule
	 */
	public String getReadRule() {
		return readRule;
	}

	/**
	 * @param readRule the readRule to set
	 */
	public void setReadRule(String readRule) {
		this.readRule = readRule;
	}

	/**
	 * @return the acceptRule
	 */
	public String getAcceptRule() {
		return acceptRule;
	}

	/**
	 * @param acceptRule the acceptRule to set
	 */
	public void setAcceptRule(String acceptRule) {
		this.acceptRule = acceptRule;
	}

	/**
	 * @return the infoTrue
	 */
	public String getInfoTrue() {
		return infoTrue;
	}

	/**
	 * @param infoTrue the infoTrue to set
	 */
	public void setInfoTrue(String infoTrue) {
		this.infoTrue = infoTrue;
	}

	/**
	 * @return the pnewComments
	 */
	public String[] getPnewComments() {
		return PnewComments;
	}

	/**
	 * @param pnewComments the pnewComments to set
	 */
	public void setPnewComments(String[] pnewComments) {
		PnewComments = pnewComments;
	}

	/**
	 * @return the mnewComments
	 */
	public String[] getMnewComments() {
		return MnewComments;
	}

	/**
	 * @param mnewComments the mnewComments to set
	 */
	public void setMnewComments(String[] mnewComments) {
		MnewComments = mnewComments;
	}

	/**
	 * @return the cnewComments
	 */
	public String[] getCnewComments() {
		return CnewComments;
	}

	/**
	 * @param cnewComments the cnewComments to set
	 */
	public void setCnewComments(String[] cnewComments) {
		CnewComments = cnewComments;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the volunteerID
	 */
	public int getVolunteerID() {
		return volunteerID;
	}

	/**
	 * @param volunteerID the volunteerID to set
	 */
	public void setVolunteerID(int volunteerID) {
		this.volunteerID = volunteerID;
	}

	/**
	 * @return the psychological_issue_details
	 */
	public String getPsychological_issue_details() {
		return psychological_issue_details;
	}

	/**
	 * @param psychological_issue_details the psychological_issue_details to set
	 */
	public void setPsychological_issue_details(String psychological_issue_details) {
		this.psychological_issue_details = psychological_issue_details;
	}

	/**
	 * @return the travelOutside
	 */
	public String getTravelOutside() {
		return travelOutside;
	}

	/**
	 * @param travelOutside the travelOutside to set
	 */
	public void setTravelOutside(String travelOutside) {
		this.travelOutside = travelOutside;
	}

	/**
	 * @return the admitted
	 */
	public String getAdmitted() {
		return admitted;
	}

	/**
	 * @param admitted the admitted to set
	 */
	public void setAdmitted(String admitted) {
		this.admitted = admitted;
	}

	/**
	 * @return the suffer_from
	 */
	public String getSuffer_from() {
		return suffer_from;
	}

	/**
	 * @param suffer_from the suffer_from to set
	 */
	public void setSuffer_from(String suffer_from) {
		this.suffer_from = suffer_from;
	}

	/**
	 * @return the surgeries
	 */
	public String getSurgeries() {
		return surgeries;
	}

	/**
	 * @param surgeries the surgeries to set
	 */
	public void setSurgeries(String surgeries) {
		this.surgeries = surgeries;
	}

	/**
	 * @return the diabOrHypertension
	 */
	public String getDiabOrHypertension() {
		return diabOrHypertension;
	}

	/**
	 * @param diabOrHypertension the diabOrHypertension to set
	 */
	public void setDiabOrHypertension(String diabOrHypertension) {
		this.diabOrHypertension = diabOrHypertension;
	}

	/**
	 * @return the awareness
	 */
	public String getAwareness() {
		return awareness;
	}

	/**
	 * @param awareness the awareness to set
	 */
	public void setAwareness(String awareness) {
		this.awareness = awareness;
	}

	/**
	 * @return the knowFamily
	 */
	public String getKnowFamily() {
		return knowFamily;
	}

	/**
	 * @param knowFamily the knowFamily to set
	 */
	public void setKnowFamily(String knowFamily) {
		this.knowFamily = knowFamily;
	}

	/**
	 * @return the psychological_issue
	 */
	public String getPsychological_issue() {
		return psychological_issue;
	}

	/**
	 * @param psychological_issue the psychological_issue to set
	 */
	public void setPsychological_issue(String psychological_issue) {
		this.psychological_issue = psychological_issue;
	}

	/**
	 * @return the training
	 */
	public String getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(String training) {
		this.training = training;
	}

	/**
	 * @return the tested_positive
	 */
	public String getTested_positive() {
		return tested_positive;
	}

	/**
	 * @param tested_positive the tested_positive to set
	 */
	public void setTested_positive(String tested_positive) {
		this.tested_positive = tested_positive;
	}

	/**
	 * @return the emergencyContact_name
	 */
	public String getEmergencyContact_name() {
		return emergencyContact_name;
	}

	/**
	 * @param emergencyContact_name the emergencyContact_name to set
	 */
	public void setEmergencyContact_name(String emergencyContact_name) {
		this.emergencyContact_name = emergencyContact_name;
	}

	/**
	 * @return the emergencyContact_relation
	 */
	public String getEmergencyContact_relation() {
		return emergencyContact_relation;
	}

	/**
	 * @param emergencyContact_relation the emergencyContact_relation to set
	 */
	public void setEmergencyContact_relation(String emergencyContact_relation) {
		this.emergencyContact_relation = emergencyContact_relation;
	}

	/**
	 * @return the emergencyContact_mobile
	 */
	public String getEmergencyContact_mobile() {
		return emergencyContact_mobile;
	}

	/**
	 * @param emergencyContact_mobile the emergencyContact_mobile to set
	 */
	public void setEmergencyContact_mobile(String emergencyContact_mobile) {
		this.emergencyContact_mobile = emergencyContact_mobile;
	}

	/**
	 * @return the iDproof
	 */
	public String getIDproof() {
		return IDproof;
	}

	/**
	 * @param iDproof the iDproof to set
	 */
	public void setIDproof(String iDproof) {
		IDproof = iDproof;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the savedQueryParameterValueIDArr
	 */
	public String[] getSavedQueryParameterValueIDArr() {
		return savedQueryParameterValueIDArr;
	}

	/**
	 * @param savedQueryParameterValueIDArr the savedQueryParameterValueIDArr to set
	 */
	public void setSavedQueryParameterValueIDArr(String[] savedQueryParameterValueIDArr) {
		this.savedQueryParameterValueIDArr = savedQueryParameterValueIDArr;
	}

	/**
	 * @return the savedQueryParameterIDArr
	 */
	public String[] getSavedQueryParameterIDArr() {
		return savedQueryParameterIDArr;
	}

	/**
	 * @param savedQueryParameterIDArr the savedQueryParameterIDArr to set
	 */
	public void setSavedQueryParameterIDArr(String[] savedQueryParameterIDArr) {
		this.savedQueryParameterIDArr = savedQueryParameterIDArr;
	}

	/**
	 * @return the parentTableName
	 */
	public String getParentTableName() {
		return parentTableName;
	}

	/**
	 * @param parentTableName the parentTableName to set
	 */
	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}

	/**
	 * @return the selectNameArr
	 */
	public String[] getSelectNameArr() {
		return selectNameArr;
	}

	/**
	 * @param selectNameArr the selectNameArr to set
	 */
	public void setSelectNameArr(String[] selectNameArr) {
		this.selectNameArr = selectNameArr;
	}

	/**
	 * @return the tableNameArr
	 */
	public String[] getTableNameArr() {
		return tableNameArr;
	}

	/**
	 * @param tableNameArr the tableNameArr to set
	 */
	public void setTableNameArr(String[] tableNameArr) {
		this.tableNameArr = tableNameArr;
	}

	/**
	 * @return the colNameArr
	 */
	public String[] getColNameArr() {
		return colNameArr;
	}

	/**
	 * @param colNameArr the colNameArr to set
	 */
	public void setColNameArr(String[] colNameArr) {
		this.colNameArr = colNameArr;
	}

	/**
	 * @return the searchTextArr
	 */
	public String[] getSearchTextArr() {
		return searchTextArr;
	}

	/**
	 * @param searchTextArr the searchTextArr to set
	 */
	public void setSearchTextArr(String[] searchTextArr) {
		this.searchTextArr = searchTextArr;
	}

	/**
	 * @return the criteriaNameArr
	 */
	public String[] getCriteriaNameArr() {
		return criteriaNameArr;
	}

	/**
	 * @param criteriaNameArr the criteriaNameArr to set
	 */
	public void setCriteriaNameArr(String[] criteriaNameArr) {
		this.criteriaNameArr = criteriaNameArr;
	}

	/**
	 * @return the initialWhere
	 */
	public String getInitialWhere() {
		return initialWhere;
	}

	/**
	 * @param initialWhere the initialWhere to set
	 */
	public void setInitialWhere(String initialWhere) {
		this.initialWhere = initialWhere;
	}

	/**
	 * @return the personalHistoryFoodChoiceDetails
	 */
	public String getPersonalHistoryFoodChoiceDetails() {
		return personalHistoryFoodChoiceDetails;
	}

	/**
	 * @param personalHistoryFoodChoiceDetails the personalHistoryFoodChoiceDetails
	 *                                         to set
	 */
	public void setPersonalHistoryFoodChoiceDetails(String personalHistoryFoodChoiceDetails) {
		this.personalHistoryFoodChoiceDetails = personalHistoryFoodChoiceDetails;
	}

	/**
	 * @return the personalHistoryFoodChoice
	 */
	public String getPersonalHistoryFoodChoice() {
		return personalHistoryFoodChoice;
	}

	/**
	 * @param personalHistoryFoodChoice the personalHistoryFoodChoice to set
	 */
	public void setPersonalHistoryFoodChoice(String personalHistoryFoodChoice) {
		this.personalHistoryFoodChoice = personalHistoryFoodChoice;
	}

	/**
	 * @return the historyID
	 */
	public int getHistoryID() {
		return historyID;
	}

	/**
	 * @param historyID the historyID to set
	 */
	public void setHistoryID(int historyID) {
		this.historyID = historyID;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the personalHistoryAlcohol
	 */
	public String getPersonalHistoryAlcohol() {
		return personalHistoryAlcohol;
	}

	/**
	 * @param personalHistoryAlcohol the personalHistoryAlcohol to set
	 */
	public void setPersonalHistoryAlcohol(String personalHistoryAlcohol) {
		this.personalHistoryAlcohol = personalHistoryAlcohol;
	}

	/**
	 * @return the personalHistoryAlcoholDetails
	 */
	public String getPersonalHistoryAlcoholDetails() {
		return personalHistoryAlcoholDetails;
	}

	/**
	 * @param personalHistoryAlcoholDetails the personalHistoryAlcoholDetails to set
	 */
	public void setPersonalHistoryAlcoholDetails(String personalHistoryAlcoholDetails) {
		this.personalHistoryAlcoholDetails = personalHistoryAlcoholDetails;
	}

	/**
	 * @return the personalHistoryTobacco
	 */
	public String getPersonalHistoryTobacco() {
		return personalHistoryTobacco;
	}

	/**
	 * @param personalHistoryTobacco the personalHistoryTobacco to set
	 */
	public void setPersonalHistoryTobacco(String personalHistoryTobacco) {
		this.personalHistoryTobacco = personalHistoryTobacco;
	}

	/**
	 * @return the personalHistoryTobaccoDetails
	 */
	public String getPersonalHistoryTobaccoDetails() {
		return personalHistoryTobaccoDetails;
	}

	/**
	 * @param personalHistoryTobaccoDetails the personalHistoryTobaccoDetails to set
	 */
	public void setPersonalHistoryTobaccoDetails(String personalHistoryTobaccoDetails) {
		this.personalHistoryTobaccoDetails = personalHistoryTobaccoDetails;
	}

	/**
	 * @return the personalHistoryVeg
	 */
	public String getPersonalHistoryVeg() {
		return personalHistoryVeg;
	}

	/**
	 * @param personalHistoryVeg the personalHistoryVeg to set
	 */
	public void setPersonalHistoryVeg(String personalHistoryVeg) {
		this.personalHistoryVeg = personalHistoryVeg;
	}

	/**
	 * @return the personalHistoryVegDetails
	 */
	public String getPersonalHistoryVegDetails() {
		return personalHistoryVegDetails;
	}

	/**
	 * @param personalHistoryVegDetails the personalHistoryVegDetails to set
	 */
	public void setPersonalHistoryVegDetails(String personalHistoryVegDetails) {
		this.personalHistoryVegDetails = personalHistoryVegDetails;
	}

	/**
	 * @return the personalHistoryNonveg
	 */
	public String getPersonalHistoryNonveg() {
		return personalHistoryNonveg;
	}

	/**
	 * @param personalHistoryNonveg the personalHistoryNonveg to set
	 */
	public void setPersonalHistoryNonveg(String personalHistoryNonveg) {
		this.personalHistoryNonveg = personalHistoryNonveg;
	}

	/**
	 * @return the personalHistoryNonvegDetails
	 */
	public String getPersonalHistoryNonvegDetails() {
		return personalHistoryNonvegDetails;
	}

	/**
	 * @param personalHistoryNonvegDetails the personalHistoryNonvegDetails to set
	 */
	public void setPersonalHistoryNonvegDetails(String personalHistoryNonvegDetails) {
		this.personalHistoryNonvegDetails = personalHistoryNonvegDetails;
	}

	/**
	 * @return the newDrugname
	 */
	public String[] getNewDrugname() {
		return newDrugname;
	}

	/**
	 * @param newDrugname the newDrugname to set
	 */
	public void setNewDrugname(String[] newDrugname) {
		this.newDrugname = newDrugname;
	}

	/**
	 * @return the newDescription
	 */
	public String[] getNewDescription() {
		return newDescription;
	}

	/**
	 * @param newDescription the newDescription to set
	 */
	public void setNewDescription(String[] newDescription) {
		this.newDescription = newDescription;
	}

	/**
	 * @return the newDiagnosis
	 */
	public String[] getNewDiagnosis() {
		return newDiagnosis;
	}

	/**
	 * @param newDiagnosis the newDiagnosis to set
	 */
	public void setNewDiagnosis(String[] newDiagnosis) {
		this.newDiagnosis = newDiagnosis;
	}

	/**
	 * @return the scleraOD
	 */
	public String getScleraOD() {
		return scleraOD;
	}

	/**
	 * @return the newOther
	 */
	public String[] getNewOther() {
		return newOther;
	}

	/**
	 * @param newOther the newOther to set
	 */
	public void setNewOther(String[] newOther) {
		this.newOther = newOther;
	}

	/**
	 * @return the newDuration
	 */
	public String[] getNewDuration() {
		return newDuration;
	}

	/**
	 * @param newDuration the newDuration to set
	 */
	public void setNewDuration(String[] newDuration) {
		this.newDuration = newDuration;
	}

	/**
	 * @param scleraOD the scleraOD to set
	 */
	public void setScleraOD(String scleraOD) {
		this.scleraOD = scleraOD;
	}

	/**
	 * @return the scleraOS
	 */
	public String getScleraOS() {
		return scleraOS;
	}

	/**
	 * @param scleraOS the scleraOS to set
	 */
	public void setScleraOS(String scleraOS) {
		this.scleraOS = scleraOS;
	}

	/**
	 * @return the biometryComment
	 */
	public String getBiometryComment() {
		return biometryComment;
	}

	/**
	 * @param biometryComment the biometryComment to set
	 */
	public void setBiometryComment(String biometryComment) {
		this.biometryComment = biometryComment;
	}

	/**
	 * @return the posteriorComment
	 */
	public String getPosteriorComment() {
		return posteriorComment;
	}

	/**
	 * @param posteriorComment the posteriorComment to set
	 */
	public void setPosteriorComment(String posteriorComment) {
		this.posteriorComment = posteriorComment;
	}

	/**
	 * @return the newEditFrequency
	 */
	public String[] getNewEditFrequency() {
		return newEditFrequency;
	}

	/**
	 * @param newEditFrequency the newEditFrequency to set
	 */
	public void setNewEditFrequency(String[] newEditFrequency) {
		this.newEditFrequency = newEditFrequency;
	}

	/**
	 * @return the newEditNoOfDays
	 */
	public String[] getNewEditNoOfDays() {
		return newEditNoOfDays;
	}

	/**
	 * @param newEditNoOfDays the newEditNoOfDays to set
	 */
	public void setNewEditNoOfDays(String[] newEditNoOfDays) {
		this.newEditNoOfDays = newEditNoOfDays;
	}

	/**
	 * @return the frequencyDetailsID
	 */
	public int getFrequencyDetailsID() {
		return frequencyDetailsID;
	}

	/**
	 * @param frequencyDetailsID the frequencyDetailsID to set
	 */
	public void setFrequencyDetailsID(int frequencyDetailsID) {
		this.frequencyDetailsID = frequencyDetailsID;
	}

	/**
	 * @return the advice
	 */
	public String getAdvice() {
		return advice;
	}

	/**
	 * @param advice the advice to set
	 */
	public void setAdvice(String advice) {
		this.advice = advice;
	}

	/**
	 * @return the nextVisitDate
	 */
	public String getNextVisitDate() {
		return nextVisitDate;
	}

	/**
	 * @param nextVisitDate the nextVisitDate to set
	 */
	public void setNextVisitDate(String nextVisitDate) {
		this.nextVisitDate = nextVisitDate;
	}

	/**
	 * @return the investigationID
	 */
	public int getInvestigationID() {
		return investigationID;
	}

	/**
	 * @param investigationID the investigationID to set
	 */
	public void setInvestigationID(int investigationID) {
		this.investigationID = investigationID;
	}

	/**
	 * @return the investigation
	 */
	public String getInvestigation() {
		return investigation;
	}

	/**
	 * @param investigation the investigation to set
	 */
	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	/**
	 * @return the editPanel
	 */
	public String[] getEditPanel() {
		return editPanel;
	}

	/**
	 * @param editPanel the editPanel to set
	 */
	public void setEditPanel(String[] editPanel) {
		this.editPanel = editPanel;
	}

	/**
	 * @return the editInvestigationDetailsID
	 */
	public String[] getEditInvestigationDetailsID() {
		return editInvestigationDetailsID;
	}

	/**
	 * @param editInvestigationDetailsID the editInvestigationDetailsID to set
	 */
	public void setEditInvestigationDetailsID(String[] editInvestigationDetailsID) {
		this.editInvestigationDetailsID = editInvestigationDetailsID;
	}

	/**
	 * @return the editValues
	 */
	public String[] getEditValues() {
		return editValues;
	}

	/**
	 * @param editValues the editValues to set
	 */
	public void setEditValues(String[] editValues) {
		this.editValues = editValues;
	}

	/**
	 * @return the otherAmount
	 */
	public double getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(double otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @return the cardAmount
	 */
	public double getCardAmount() {
		return cardAmount;
	}

	/**
	 * @param cardAmount the cardAmount to set
	 */
	public void setCardAmount(double cardAmount) {
		this.cardAmount = cardAmount;
	}

	/**
	 * @return the reportsID
	 */
	public int getReportsID() {
		return reportsID;
	}

	/**
	 * @param reportsID the reportsID to set
	 */
	public void setReportsID(int reportsID) {
		this.reportsID = reportsID;
	}

	/**
	 * @return the newSymptom
	 */
	public String[] getNewSymptom() {
		return newSymptom;
	}

	/**
	 * @param newSymptom the newSymptom to set
	 */
	public void setNewSymptom(String[] newSymptom) {
		this.newSymptom = newSymptom;
	}

	/**
	 * @return the newValue
	 */
	public String[] getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String[] newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the adjAmount
	 */
	public double getAdjAmount() {
		return adjAmount;
	}

	/**
	 * @param adjAmount the adjAmount to set
	 */
	public void setAdjAmount(double adjAmount) {
		this.adjAmount = adjAmount;
	}

	/**
	 * @return the adjustAmtStatus
	 */
	public String getAdjustAmtStatus() {
		return adjustAmtStatus;
	}

	/**
	 * @param adjustAmtStatus the adjustAmtStatus to set
	 */
	public void setAdjustAmtStatus(String adjustAmtStatus) {
		this.adjustAmtStatus = adjustAmtStatus;
	}

	/**
	 * @return the saveQueryID
	 */
	public int getSaveQueryID() {
		return saveQueryID;
	}

	/**
	 * @param saveQueryID the saveQueryID to set
	 */
	public void setSaveQueryID(int saveQueryID) {
		this.saveQueryID = saveQueryID;
	}

	/**
	 * @return the queryTitle
	 */
	public String getQueryTitle() {
		return queryTitle;
	}

	/**
	 * @param queryTitle the queryTitle to set
	 */
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}

	/**
	 * @return the savedQuery
	 */
	public String getSavedQuery() {
		return savedQuery;
	}

	/**
	 * @param savedQuery the savedQuery to set
	 */
	public void setSavedQuery(String savedQuery) {
		this.savedQuery = savedQuery;
	}

	/**
	 * @return the cMobileNo
	 */
	public String getcMobileNo() {
		return cMobileNo;
	}

	/**
	 * @param cMobileNo the cMobileNo to set
	 */
	public void setcMobileNo(String cMobileNo) {
		this.cMobileNo = cMobileNo;
	}

	/**
	 * @return the gp1
	 */
	public String getGp1() {
		return gp1;
	}

	/**
	 * @param gp1 the gp1 to set
	 */
	public void setGp1(String gp1) {
		this.gp1 = gp1;
	}

	/**
	 * @return the gp2
	 */
	public String getGp2() {
		return gp2;
	}

	/**
	 * @param gp2 the gp2 to set
	 */
	public void setGp2(String gp2) {
		this.gp2 = gp2;
	}

	/**
	 * @return the gp3
	 */
	public String getGp3() {
		return gp3;
	}

	/**
	 * @param gp3 the gp3 to set
	 */
	public void setGp3(String gp3) {
		this.gp3 = gp3;
	}

	/**
	 * @return the gp4
	 */
	public String getGp4() {
		return gp4;
	}

	/**
	 * @param gp4 the gp4 to set
	 */
	public void setGp4(String gp4) {
		this.gp4 = gp4;
	}

	/**
	 * @return the gp5
	 */
	public String getGp5() {
		return gp5;
	}

	/**
	 * @param gp5 the gp5 to set
	 */
	public void setGp5(String gp5) {
		this.gp5 = gp5;
	}

	/**
	 * @return the gp6
	 */
	public String getGp6() {
		return gp6;
	}

	/**
	 * @param gp6 the gp6 to set
	 */
	public void setGp6(String gp6) {
		this.gp6 = gp6;
	}

	/**
	 * @return the gp7
	 */
	public String getGp7() {
		return gp7;
	}

	/**
	 * @param gp7 the gp7 to set
	 */
	public void setGp7(String gp7) {
		this.gp7 = gp7;
	}

	/**
	 * @return the gs1
	 */
	public String getGs1() {
		return gs1;
	}

	/**
	 * @param gs1 the gs1 to set
	 */
	public void setGs1(String gs1) {
		this.gs1 = gs1;
	}

	/**
	 * @return the gs2
	 */
	public String getGs2() {
		return gs2;
	}

	/**
	 * @param gs2 the gs2 to set
	 */
	public void setGs2(String gs2) {
		this.gs2 = gs2;
	}

	/**
	 * @return the gs3
	 */
	public String getGs3() {
		return gs3;
	}

	/**
	 * @param gs3 the gs3 to set
	 */
	public void setGs3(String gs3) {
		this.gs3 = gs3;
	}

	/**
	 * @return the gs4
	 */
	public String getGs4() {
		return gs4;
	}

	/**
	 * @param gs4 the gs4 to set
	 */
	public void setGs4(String gs4) {
		this.gs4 = gs4;
	}

	/**
	 * @return the gs5
	 */
	public String getGs5() {
		return gs5;
	}

	/**
	 * @param gs5 the gs5 to set
	 */
	public void setGs5(String gs5) {
		this.gs5 = gs5;
	}

	/**
	 * @return the gs6
	 */
	public String getGs6() {
		return gs6;
	}

	/**
	 * @param gs6 the gs6 to set
	 */
	public void setGs6(String gs6) {
		this.gs6 = gs6;
	}

	/**
	 * @return the gs7
	 */
	public String getGs7() {
		return gs7;
	}

	/**
	 * @param gs7 the gs7 to set
	 */
	public void setGs7(String gs7) {
		this.gs7 = gs7;
	}

	/**
	 * @return the q1
	 */
	public String getQ1() {
		return q1;
	}

	/**
	 * @param q1 the q1 to set
	 */
	public void setQ1(String q1) {
		this.q1 = q1;
	}

	/**
	 * @return the ge1
	 */
	public String getGe1() {
		return ge1;
	}

	/**
	 * @param ge1 the ge1 to set
	 */
	public void setGe1(String ge1) {
		this.ge1 = ge1;
	}

	/**
	 * @return the ge2
	 */
	public String getGe2() {
		return ge2;
	}

	/**
	 * @param ge2 the ge2 to set
	 */
	public void setGe2(String ge2) {
		this.ge2 = ge2;
	}

	/**
	 * @return the ge3
	 */
	public String getGe3() {
		return ge3;
	}

	/**
	 * @param ge3 the ge3 to set
	 */
	public void setGe3(String ge3) {
		this.ge3 = ge3;
	}

	/**
	 * @return the ge4
	 */
	public String getGe4() {
		return ge4;
	}

	/**
	 * @param ge4 the ge4 to set
	 */
	public void setGe4(String ge4) {
		this.ge4 = ge4;
	}

	/**
	 * @return the ge5
	 */
	public String getGe5() {
		return ge5;
	}

	/**
	 * @param ge5 the ge5 to set
	 */
	public void setGe5(String ge5) {
		this.ge5 = ge5;
	}

	/**
	 * @return the ge6
	 */
	public String getGe6() {
		return ge6;
	}

	/**
	 * @param ge6 the ge6 to set
	 */
	public void setGe6(String ge6) {
		this.ge6 = ge6;
	}

	/**
	 * @return the gf1
	 */
	public String getGf1() {
		return gf1;
	}

	/**
	 * @param gf1 the gf1 to set
	 */
	public void setGf1(String gf1) {
		this.gf1 = gf1;
	}

	/**
	 * @return the gf2
	 */
	public String getGf2() {
		return gf2;
	}

	/**
	 * @param gf2 the gf2 to set
	 */
	public void setGf2(String gf2) {
		this.gf2 = gf2;
	}

	/**
	 * @return the gf3
	 */
	public String getGf3() {
		return gf3;
	}

	/**
	 * @param gf3 the gf3 to set
	 */
	public void setGf3(String gf3) {
		this.gf3 = gf3;
	}

	/**
	 * @return the gf4
	 */
	public String getGf4() {
		return gf4;
	}

	/**
	 * @param gf4 the gf4 to set
	 */
	public void setGf4(String gf4) {
		this.gf4 = gf4;
	}

	/**
	 * @return the gf5
	 */
	public String getGf5() {
		return gf5;
	}

	/**
	 * @param gf5 the gf5 to set
	 */
	public void setGf5(String gf5) {
		this.gf5 = gf5;
	}

	/**
	 * @return the gf6
	 */
	public String getGf6() {
		return gf6;
	}

	/**
	 * @param gf6 the gf6 to set
	 */
	public void setGf6(String gf6) {
		this.gf6 = gf6;
	}

	/**
	 * @return the gf7
	 */
	public String getGf7() {
		return gf7;
	}

	/**
	 * @param gf7 the gf7 to set
	 */
	public void setGf7(String gf7) {
		this.gf7 = gf7;
	}

	/**
	 * @return the nextApptTaken
	 */
	public int getNextApptTaken() {
		return nextApptTaken;
	}

	/**
	 * @param nextApptTaken the nextApptTaken to set
	 */
	public void setNextApptTaken(int nextApptTaken) {
		this.nextApptTaken = nextApptTaken;
	}

	/**
	 * @return the chemoUnit
	 */
	public String[] getChemoUnit() {
		return chemoUnit;
	}

	/**
	 * @param chemoUnit the chemoUnit to set
	 */
	public void setChemoUnit(String[] chemoUnit) {
		this.chemoUnit = chemoUnit;
	}

	/**
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * @param centerName the centerName to set
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	/**
	 * @return the patientFormNo
	 */
	public String getPatientFormNo() {
		return patientFormNo;
	}

	/**
	 * @param patientFormNo the patientFormNo to set
	 */
	public void setPatientFormNo(String patientFormNo) {
		this.patientFormNo = patientFormNo;
	}

	/**
	 * @return the locationLatLong
	 */
	public String getLocationLatLong() {
		return locationLatLong;
	}

	/**
	 * @param locationLatLong the locationLatLong to set
	 */
	public void setLocationLatLong(String locationLatLong) {
		this.locationLatLong = locationLatLong;
	}

	/**
	 * @return the profilePicFileName
	 */
	public String getProfilePicFileName() {
		return profilePicFileName;
	}

	/**
	 * @param profilePicFileName the profilePicFileName to set
	 */
	public void setProfilePicFileName(String profilePicFileName) {
		this.profilePicFileName = profilePicFileName;
	}

	/**
	 * @return the formDataFileName
	 */
	public String getFormDataFileName() {
		return formDataFileName;
	}

	/**
	 * @param formDataFileName the formDataFileName to set
	 */
	public void setFormDataFileName(String formDataFileName) {
		this.formDataFileName = formDataFileName;
	}

	/**
	 * @return the identificationFileName
	 */
	public String getIdentificationFileName() {
		return identificationFileName;
	}

	/**
	 * @param identificationFileName the identificationFileName to set
	 */
	public void setIdentificationFileName(String identificationFileName) {
		this.identificationFileName = identificationFileName;
	}

	/**
	 * @return the sugarAvgValue
	 */
	public String getSugarAvgValue() {
		return sugarAvgValue;
	}

	/**
	 * @param sugarAvgValue the sugarAvgValue to set
	 */
	public void setSugarAvgValue(String sugarAvgValue) {
		this.sugarAvgValue = sugarAvgValue;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the vitalSignsDate
	 */
	public String[] getVitalSignsDate() {
		return vitalSignsDate;
	}

	/**
	 * @param vitalSignsDate the vitalSignsDate to set
	 */
	public void setVitalSignsDate(String[] vitalSignsDate) {
		this.vitalSignsDate = vitalSignsDate;
	}

	/**
	 * @return the newDrugRateName
	 */
	public double getNewDrugRateName() {
		return newDrugRateName;
	}

	/**
	 * @param newDrugRateName the newDrugRateName to set
	 */
	public void setNewDrugRateName(double newDrugRateName) {
		this.newDrugRateName = newDrugRateName;
	}

	/**
	 * @return the newDrugRate
	 */
	public String[] getNewDrugRate() {
		return newDrugRate;
	}

	/**
	 * @param newDrugRate the newDrugRate to set
	 */
	public void setNewDrugRate(String[] newDrugRate) {
		this.newDrugRate = newDrugRate;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @return the minQuantity
	 */
	public int getMinQuantity() {
		return minQuantity;
	}

	/**
	 * @param minQuantity the minQuantity to set
	 */
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
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
	 * @return the symptomStatus
	 */
	public String getSymptomStatus() {
		return symptomStatus;
	}

	/**
	 * @param symptomStatus the symptomStatus to set
	 */
	public void setSymptomStatus(String symptomStatus) {
		this.symptomStatus = symptomStatus;
	}

	/**
	 * @return the xRAYFindingDateArr
	 */
	public String[] getXRAYFindingDateArr() {
		return XRAYFindingDateArr;
	}

	/**
	 * @param xRAYFindingDateArr the xRAYFindingDateArr to set
	 */
	public void setXRAYFindingDateArr(String[] xRAYFindingDateArr) {
		XRAYFindingDateArr = xRAYFindingDateArr;
	}

	/**
	 * @return the xRAYFindingTypeArr
	 */
	public String[] getXRAYFindingTypeArr() {
		return XRAYFindingTypeArr;
	}

	/**
	 * @param xRAYFindingTypeArr the xRAYFindingTypeArr to set
	 */
	public void setXRAYFindingTypeArr(String[] xRAYFindingTypeArr) {
		XRAYFindingTypeArr = xRAYFindingTypeArr;
	}

	/**
	 * @return the xRAYFindingDescriptionArr
	 */
	public String[] getXRAYFindingDescriptionArr() {
		return XRAYFindingDescriptionArr;
	}

	/**
	 * @param xRAYFindingDescriptionArr the xRAYFindingDescriptionArr to set
	 */
	public void setXRAYFindingDescriptionArr(String[] xRAYFindingDescriptionArr) {
		XRAYFindingDescriptionArr = xRAYFindingDescriptionArr;
	}

	/**
	 * @return the xRAYReportFile
	 */
	public List<File> getXRAYReportFile() {
		return XRAYReportFile;
	}

	/**
	 * @param xRAYReportFile the xRAYReportFile to set
	 */
	public void setXRAYReportFile(List<File> xRAYReportFile) {
		XRAYReportFile = xRAYReportFile;
	}

	/**
	 * @return the xRAYReportFileContentType
	 */
	public List<String> getXRAYReportFileContentType() {
		return XRAYReportFileContentType;
	}

	/**
	 * @param xRAYReportFileContentType the xRAYReportFileContentType to set
	 */
	public void setXRAYReportFileContentType(List<String> xRAYReportFileContentType) {
		XRAYReportFileContentType = xRAYReportFileContentType;
	}

	/**
	 * @return the xRAYReportFileFileName
	 */
	public List<String> getXRAYReportFileFileName() {
		return XRAYReportFileFileName;
	}

	/**
	 * @param xRAYReportFileFileName the xRAYReportFileFileName to set
	 */
	public void setXRAYReportFileFileName(List<String> xRAYReportFileFileName) {
		XRAYReportFileFileName = xRAYReportFileFileName;
	}

	/**
	 * @return the uCGFindingDateArr
	 */
	public String[] getUCGFindingDateArr() {
		return UCGFindingDateArr;
	}

	/**
	 * @param uCGFindingDateArr the uCGFindingDateArr to set
	 */
	public void setUCGFindingDateArr(String[] uCGFindingDateArr) {
		UCGFindingDateArr = uCGFindingDateArr;
	}

	/**
	 * @return the uCGFindingTypeArr
	 */
	public String[] getUCGFindingTypeArr() {
		return UCGFindingTypeArr;
	}

	/**
	 * @param uCGFindingTypeArr the uCGFindingTypeArr to set
	 */
	public void setUCGFindingTypeArr(String[] uCGFindingTypeArr) {
		UCGFindingTypeArr = uCGFindingTypeArr;
	}

	/**
	 * @return the uCGFindingDescriptionArr
	 */
	public String[] getUCGFindingDescriptionArr() {
		return UCGFindingDescriptionArr;
	}

	/**
	 * @param uCGFindingDescriptionArr the uCGFindingDescriptionArr to set
	 */
	public void setUCGFindingDescriptionArr(String[] uCGFindingDescriptionArr) {
		UCGFindingDescriptionArr = uCGFindingDescriptionArr;
	}

	/**
	 * @return the uCGReportFile
	 */
	public List<File> getUCGReportFile() {
		return UCGReportFile;
	}

	/**
	 * @param uCGReportFile the uCGReportFile to set
	 */
	public void setUCGReportFile(List<File> uCGReportFile) {
		UCGReportFile = uCGReportFile;
	}

	/**
	 * @return the uCGReportFileContentType
	 */
	public List<String> getUCGReportFileContentType() {
		return UCGReportFileContentType;
	}

	/**
	 * @param uCGReportFileContentType the uCGReportFileContentType to set
	 */
	public void setUCGReportFileContentType(List<String> uCGReportFileContentType) {
		UCGReportFileContentType = uCGReportFileContentType;
	}

	/**
	 * @return the uCGReportFileFileName
	 */
	public List<String> getUCGReportFileFileName() {
		return UCGReportFileFileName;
	}

	/**
	 * @param uCGReportFileFileName the uCGReportFileFileName to set
	 */
	public void setUCGReportFileFileName(List<String> uCGReportFileFileName) {
		UCGReportFileFileName = uCGReportFileFileName;
	}

	/**
	 * @return the cTFindingDateArr
	 */
	public String[] getCTFindingDateArr() {
		return CTFindingDateArr;
	}

	/**
	 * @param cTFindingDateArr the cTFindingDateArr to set
	 */
	public void setCTFindingDateArr(String[] cTFindingDateArr) {
		CTFindingDateArr = cTFindingDateArr;
	}

	/**
	 * @return the cTFindingDescriptionArr
	 */
	public String[] getCTFindingDescriptionArr() {
		return CTFindingDescriptionArr;
	}

	/**
	 * @param cTFindingDescriptionArr the cTFindingDescriptionArr to set
	 */
	public void setCTFindingDescriptionArr(String[] cTFindingDescriptionArr) {
		CTFindingDescriptionArr = cTFindingDescriptionArr;
	}

	/**
	 * @return the cTFindingTypeArr
	 */
	public String[] getCTFindingTypeArr() {
		return CTFindingTypeArr;
	}

	/**
	 * @param cTFindingTypeArr the cTFindingTypeArr to set
	 */
	public void setCTFindingTypeArr(String[] cTFindingTypeArr) {
		CTFindingTypeArr = cTFindingTypeArr;
	}

	/**
	 * @return the cTReportFile
	 */
	public List<File> getCTReportFile() {
		return CTReportFile;
	}

	/**
	 * @param cTReportFile the cTReportFile to set
	 */
	public void setCTReportFile(List<File> cTReportFile) {
		CTReportFile = cTReportFile;
	}

	/**
	 * @return the cTReportFileContentType
	 */
	public List<String> getCTReportFileContentType() {
		return CTReportFileContentType;
	}

	/**
	 * @param cTReportFileContentType the cTReportFileContentType to set
	 */
	public void setCTReportFileContentType(List<String> cTReportFileContentType) {
		CTReportFileContentType = cTReportFileContentType;
	}

	/**
	 * @return the cTReportFileFileName
	 */
	public List<String> getCTReportFileFileName() {
		return CTReportFileFileName;
	}

	/**
	 * @param cTReportFileFileName the cTReportFileFileName to set
	 */
	public void setCTReportFileFileName(List<String> cTReportFileFileName) {
		CTReportFileFileName = cTReportFileFileName;
	}

	/**
	 * @return the mRIFindingDateArr
	 */
	public String[] getMRIFindingDateArr() {
		return MRIFindingDateArr;
	}

	/**
	 * @param mRIFindingDateArr the mRIFindingDateArr to set
	 */
	public void setMRIFindingDateArr(String[] mRIFindingDateArr) {
		MRIFindingDateArr = mRIFindingDateArr;
	}

	/**
	 * @return the mRIFindingTypeArr
	 */
	public String[] getMRIFindingTypeArr() {
		return MRIFindingTypeArr;
	}

	/**
	 * @param mRIFindingTypeArr the mRIFindingTypeArr to set
	 */
	public void setMRIFindingTypeArr(String[] mRIFindingTypeArr) {
		MRIFindingTypeArr = mRIFindingTypeArr;
	}

	/**
	 * @return the mRIFindingDescriptionArr
	 */
	public String[] getMRIFindingDescriptionArr() {
		return MRIFindingDescriptionArr;
	}

	/**
	 * @param mRIFindingDescriptionArr the mRIFindingDescriptionArr to set
	 */
	public void setMRIFindingDescriptionArr(String[] mRIFindingDescriptionArr) {
		MRIFindingDescriptionArr = mRIFindingDescriptionArr;
	}

	/**
	 * @return the mRIReportFile
	 */
	public List<File> getMRIReportFile() {
		return MRIReportFile;
	}

	/**
	 * @param mRIReportFile the mRIReportFile to set
	 */
	public void setMRIReportFile(List<File> mRIReportFile) {
		MRIReportFile = mRIReportFile;
	}

	/**
	 * @return the mRIReportFileContentType
	 */
	public List<String> getMRIReportFileContentType() {
		return MRIReportFileContentType;
	}

	/**
	 * @param mRIReportFileContentType the mRIReportFileContentType to set
	 */
	public void setMRIReportFileContentType(List<String> mRIReportFileContentType) {
		MRIReportFileContentType = mRIReportFileContentType;
	}

	/**
	 * @return the mRIReportFileFileName
	 */
	public List<String> getMRIReportFileFileName() {
		return MRIReportFileFileName;
	}

	/**
	 * @param mRIReportFileFileName the mRIReportFileFileName to set
	 */
	public void setMRIReportFileFileName(List<String> mRIReportFileFileName) {
		MRIReportFileFileName = mRIReportFileFileName;
	}

	/**
	 * @return the otherFindingDateArr
	 */
	public String[] getOtherFindingDateArr() {
		return OtherFindingDateArr;
	}

	/**
	 * @param otherFindingDateArr the otherFindingDateArr to set
	 */
	public void setOtherFindingDateArr(String[] otherFindingDateArr) {
		OtherFindingDateArr = otherFindingDateArr;
	}

	/**
	 * @return the otherFindingTypeArr
	 */
	public String[] getOtherFindingTypeArr() {
		return OtherFindingTypeArr;
	}

	/**
	 * @param otherFindingTypeArr the otherFindingTypeArr to set
	 */
	public void setOtherFindingTypeArr(String[] otherFindingTypeArr) {
		OtherFindingTypeArr = otherFindingTypeArr;
	}

	/**
	 * @return the otherFindingDescriptionArr
	 */
	public String[] getOtherFindingDescriptionArr() {
		return OtherFindingDescriptionArr;
	}

	/**
	 * @param otherFindingDescriptionArr the otherFindingDescriptionArr to set
	 */
	public void setOtherFindingDescriptionArr(String[] otherFindingDescriptionArr) {
		OtherFindingDescriptionArr = otherFindingDescriptionArr;
	}

	/**
	 * @return the otherReportFile
	 */
	public List<File> getOtherReportFile() {
		return OtherReportFile;
	}

	/**
	 * @param otherReportFile the otherReportFile to set
	 */
	public void setOtherReportFile(List<File> otherReportFile) {
		OtherReportFile = otherReportFile;
	}

	/**
	 * @return the otherReportFileContentType
	 */
	public List<String> getOtherReportFileContentType() {
		return OtherReportFileContentType;
	}

	/**
	 * @param otherReportFileContentType the otherReportFileContentType to set
	 */
	public void setOtherReportFileContentType(List<String> otherReportFileContentType) {
		OtherReportFileContentType = otherReportFileContentType;
	}

	/**
	 * @return the otherReportFileFileName
	 */
	public List<String> getOtherReportFileFileName() {
		return OtherReportFileFileName;
	}

	/**
	 * @param otherReportFileFileName the otherReportFileFileName to set
	 */
	public void setOtherReportFileFileName(List<String> otherReportFileFileName) {
		OtherReportFileFileName = otherReportFileFileName;
	}

	/**
	 * @return the liverPanelArr
	 */
	public String[] getLiverPanelArr() {
		return liverPanelArr;
	}

	/**
	 * @param liverPanelArr the liverPanelArr to set
	 */
	public void setLiverPanelArr(String[] liverPanelArr) {
		this.liverPanelArr = liverPanelArr;
	}

	/**
	 * @return the liverDateArr
	 */
	public String[] getLiverDateArr() {
		return liverDateArr;
	}

	/**
	 * @param liverDateArr the liverDateArr to set
	 */
	public void setLiverDateArr(String[] liverDateArr) {
		this.liverDateArr = liverDateArr;
	}

	/**
	 * @return the liverTest
	 */
	public String getLiverTest() {
		return liverTest;
	}

	/**
	 * @param liverTest the liverTest to set
	 */
	public void setLiverTest(String liverTest) {
		this.liverTest = liverTest;
	}

	/**
	 * @return the liverNormalRange
	 */
	public String getLiverNormalRange() {
		return liverNormalRange;
	}

	/**
	 * @param liverNormalRange the liverNormalRange to set
	 */
	public void setLiverNormalRange(String liverNormalRange) {
		this.liverNormalRange = liverNormalRange;
	}

	/**
	 * @return the liverValue
	 */
	public String getLiverValue() {
		return liverValue;
	}

	/**
	 * @param liverValue the liverValue to set
	 */
	public void setLiverValue(String liverValue) {
		this.liverValue = liverValue;
	}

	/**
	 * @return the renalPanelArr
	 */
	public String[] getRenalPanelArr() {
		return renalPanelArr;
	}

	/**
	 * @param renalPanelArr the renalPanelArr to set
	 */
	public void setRenalPanelArr(String[] renalPanelArr) {
		this.renalPanelArr = renalPanelArr;
	}

	/**
	 * @return the renalDateArr
	 */
	public String[] getRenalDateArr() {
		return renalDateArr;
	}

	/**
	 * @param renalDateArr the renalDateArr to set
	 */
	public void setRenalDateArr(String[] renalDateArr) {
		this.renalDateArr = renalDateArr;
	}

	/**
	 * @return the renalNormalRange
	 */
	public String getRenalNormalRange() {
		return renalNormalRange;
	}

	/**
	 * @param renalNormalRange the renalNormalRange to set
	 */
	public void setRenalNormalRange(String renalNormalRange) {
		this.renalNormalRange = renalNormalRange;
	}

	/**
	 * @return the renalTest
	 */
	public String getRenalTest() {
		return renalTest;
	}

	/**
	 * @param renalTest the renalTest to set
	 */
	public void setRenalTest(String renalTest) {
		this.renalTest = renalTest;
	}

	/**
	 * @return the renalValue
	 */
	public String getRenalValue() {
		return renalValue;
	}

	/**
	 * @param renalValue the renalValue to set
	 */
	public void setRenalValue(String renalValue) {
		this.renalValue = renalValue;
	}

	/**
	 * @return the cBCPanelArr
	 */
	public String[] getCBCPanelArr() {
		return CBCPanelArr;
	}

	/**
	 * @param cBCPanelArr the cBCPanelArr to set
	 */
	public void setCBCPanelArr(String[] cBCPanelArr) {
		CBCPanelArr = cBCPanelArr;
	}

	/**
	 * @return the cBCDateArr
	 */
	public String[] getCBCDateArr() {
		return CBCDateArr;
	}

	/**
	 * @param cBCDateArr the cBCDateArr to set
	 */
	public void setCBCDateArr(String[] cBCDateArr) {
		CBCDateArr = cBCDateArr;
	}

	/**
	 * @return the cBCProfileTest
	 */
	public String getCBCProfileTest() {
		return CBCProfileTest;
	}

	/**
	 * @param cBCProfileTest the cBCProfileTest to set
	 */
	public void setCBCProfileTest(String cBCProfileTest) {
		CBCProfileTest = cBCProfileTest;
	}

	/**
	 * @return the cBCProfileNormalValue
	 */
	public String getCBCProfileNormalValue() {
		return CBCProfileNormalValue;
	}

	/**
	 * @param cBCProfileNormalValue the cBCProfileNormalValue to set
	 */
	public void setCBCProfileNormalValue(String cBCProfileNormalValue) {
		CBCProfileNormalValue = cBCProfileNormalValue;
	}

	/**
	 * @return the cBCProfileValue
	 */
	public String getCBCProfileValue() {
		return CBCProfileValue;
	}

	/**
	 * @param cBCProfileValue the cBCProfileValue to set
	 */
	public void setCBCProfileValue(String cBCProfileValue) {
		CBCProfileValue = cBCProfileValue;
	}

	/**
	 * @return the onExamArr
	 */
	public String[] getOnExamArr() {
		return onExamArr;
	}

	/**
	 * @param onExamArr the onExamArr to set
	 */
	public void setOnExamArr(String[] onExamArr) {
		this.onExamArr = onExamArr;
	}

	/**
	 * @return the onExamDescArr
	 */
	public String[] getOnExamDescArr() {
		return onExamDescArr;
	}

	/**
	 * @param onExamDescArr the onExamDescArr to set
	 */
	public void setOnExamDescArr(String[] onExamDescArr) {
		this.onExamDescArr = onExamDescArr;
	}

	/**
	 * @return the totalDiscount
	 */
	public double getTotalDiscount() {
		return totalDiscount;
	}

	/**
	 * @param totalDiscount the totalDiscount to set
	 */
	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @return the newPrescriptionID
	 */
	public String[] getNewPrescriptionID() {
		return newPrescriptionID;
	}

	/**
	 * @param newPrescriptionID the newPrescriptionID to set
	 */
	public void setNewPrescriptionID(String[] newPrescriptionID) {
		this.newPrescriptionID = newPrescriptionID;
	}

	/**
	 * @return the compoundClassName
	 */
	public String getCompoundClassName() {
		return compoundClassName;
	}

	/**
	 * @param compoundClassName the compoundClassName to set
	 */
	public void setCompoundClassName(String compoundClassName) {
		this.compoundClassName = compoundClassName;
	}

	/**
	 * @return the totalQuantity
	 */
	public double getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * @return the freqCount
	 */
	public int getFreqCount() {
		return freqCount;
	}

	/**
	 * @param freqCount the freqCount to set
	 */
	public void setFreqCount(int freqCount) {
		this.freqCount = freqCount;
	}

	/**
	 * @return the newPrescID
	 */
	public int getNewPrescID() {
		return newPrescID;
	}

	/**
	 * @param newPrescID the newPrescID to set
	 */
	public void setNewPrescID(int newPrescID) {
		this.newPrescID = newPrescID;
	}

	/**
	 * @return the newDrugNameID
	 */
	public int getNewDrugNameID() {
		return newDrugNameID;
	}

	/**
	 * @param newDrugNameID the newDrugNameID to set
	 */
	public void setNewDrugNameID(int newDrugNameID) {
		this.newDrugNameID = newDrugNameID;
	}

	/**
	 * @return the newDrugName
	 */
	public String getNewDrugName() {
		return newDrugName;
	}

	/**
	 * @param newDrugName the newDrugName to set
	 */
	public void setNewDrugName(String newDrugName) {
		this.newDrugName = newDrugName;
	}

	/**
	 * @return the newDrugNoOfDaysName
	 */
	public int getNewDrugNoOfDaysName() {
		return newDrugNoOfDaysName;
	}

	/**
	 * @param newDrugNoOfDaysName the newDrugNoOfDaysName to set
	 */
	public void setNewDrugNoOfDaysName(int newDrugNoOfDaysName) {
		this.newDrugNoOfDaysName = newDrugNoOfDaysName;
	}

	/**
	 * @return the newDrugFrequencyName
	 */
	public String getNewDrugFrequencyName() {
		return newDrugFrequencyName;
	}

	/**
	 * @return the newDrugCategoryID
	 */
	public String[] getNewDrugCategoryID() {
		return newDrugCategoryID;
	}

	/**
	 * @param newDrugCategoryID the newDrugCategoryID to set
	 */
	public void setNewDrugCategoryID(String[] newDrugCategoryID) {
		this.newDrugCategoryID = newDrugCategoryID;
	}

	/**
	 * @param newDrugFrequencyName the newDrugFrequencyName to set
	 */
	public void setNewDrugFrequencyName(String newDrugFrequencyName) {
		this.newDrugFrequencyName = newDrugFrequencyName;
	}

	/**
	 * @return the newDrugDosageName
	 */
	public double getNewDrugDosageName() {
		return newDrugDosageName;
	}

	/**
	 * @param newDrugDosageName the newDrugDosageName to set
	 */
	public void setNewDrugDosageName(double newDrugDosageName) {
		this.newDrugDosageName = newDrugDosageName;
	}

	/**
	 * @return the newDrugQuantityName
	 */
	public double getNewDrugQuantityName() {
		return newDrugQuantityName;
	}

	/**
	 * @param newDrugQuantityName the newDrugQuantityName to set
	 */
	public void setNewDrugQuantityName(double newDrugQuantityName) {
		this.newDrugQuantityName = newDrugQuantityName;
	}

	/**
	 * @return the newDrugCommentName
	 */
	public String getNewDrugCommentName() {
		return newDrugCommentName;
	}

	/**
	 * @param newDrugCommentName the newDrugCommentName to set
	 */
	public void setNewDrugCommentName(String newDrugCommentName) {
		this.newDrugCommentName = newDrugCommentName;
	}

	/**
	 * @return the newDrugInstructionString
	 */
	public String getNewDrugInstructionString() {
		return newDrugInstructionString;
	}

	/**
	 * @param newDrugInstructionString the newDrugInstructionString to set
	 */
	public void setNewDrugInstructionString(String newDrugInstructionString) {
		this.newDrugInstructionString = newDrugInstructionString;
	}

	/**
	 * @return the newCategoryIDName
	 */
	public int getNewCategoryIDName() {
		return newCategoryIDName;
	}

	/**
	 * @param newCategoryIDName the newCategoryIDName to set
	 */
	public void setNewCategoryIDName(int newCategoryIDName) {
		this.newCategoryIDName = newCategoryIDName;
	}

	/**
	 * @return the newDrugBarcodeName
	 */
	public String getNewDrugBarcodeName() {
		return newDrugBarcodeName;
	}

	/**
	 * @param newDrugBarcodeName the newDrugBarcodeName to set
	 */
	public void setNewDrugBarcodeName(String newDrugBarcodeName) {
		this.newDrugBarcodeName = newDrugBarcodeName;
	}

	/**
	 * @return the newIsCompoundName
	 */
	public int getNewIsCompoundName() {
		return newIsCompoundName;
	}

	/**
	 * @param newIsCompoundName the newIsCompoundName to set
	 */
	public void setNewIsCompoundName(int newIsCompoundName) {
		this.newIsCompoundName = newIsCompoundName;
	}

	/**
	 * @return the newCompoundName
	 */
	public String getNewCompoundName() {
		return newCompoundName;
	}

	/**
	 * @param newCompoundName the newCompoundName to set
	 */
	public void setNewCompoundName(String newCompoundName) {
		this.newCompoundName = newCompoundName;
	}

	/**
	 * @return the newDrugSectionName
	 */
	public String getNewDrugSectionName() {
		return newDrugSectionName;
	}

	/**
	 * @param newDrugSectionName the newDrugSectionName to set
	 */
	public void setNewDrugSectionName(String newDrugSectionName) {
		this.newDrugSectionName = newDrugSectionName;
	}

	/**
	 * @return the newDrugDiscountName
	 */
	public double getNewDrugDiscountName() {
		return newDrugDiscountName;
	}

	/**
	 * @param newDrugDiscountName the newDrugDiscountName to set
	 */
	public void setNewDrugDiscountName(double newDrugDiscountName) {
		this.newDrugDiscountName = newDrugDiscountName;
	}

	/**
	 * @return the newDrugID
	 */
	public String[] getNewDrugID() {
		return newDrugID;
	}

	/**
	 * @param newDrugID the newDrugID to set
	 */
	public void setNewDrugID(String[] newDrugID) {
		this.newDrugID = newDrugID;
	}

	/**
	 * @return the newDrugNoOfDays
	 */
	public String[] getNewDrugNoOfDays() {
		return newDrugNoOfDays;
	}

	/**
	 * @param newDrugNoOfDays the newDrugNoOfDays to set
	 */
	public void setNewDrugNoOfDays(String[] newDrugNoOfDays) {
		this.newDrugNoOfDays = newDrugNoOfDays;
	}

	/**
	 * @return the newDrugFrequency
	 */
	public String[] getNewDrugFrequency() {
		return newDrugFrequency;
	}

	/**
	 * @param newDrugFrequency the newDrugFrequency to set
	 */
	public void setNewDrugFrequency(String[] newDrugFrequency) {
		this.newDrugFrequency = newDrugFrequency;
	}

	/**
	 * @return the newDrugDosage
	 */
	public String[] getNewDrugDosage() {
		return newDrugDosage;
	}

	/**
	 * @param newDrugDosage the newDrugDosage to set
	 */
	public void setNewDrugDosage(String[] newDrugDosage) {
		this.newDrugDosage = newDrugDosage;
	}

	/**
	 * @return the newDrugQuantity
	 */
	public String[] getNewDrugQuantity() {
		return newDrugQuantity;
	}

	/**
	 * @param newDrugQuantity the newDrugQuantity to set
	 */
	public void setNewDrugQuantity(String[] newDrugQuantity) {
		this.newDrugQuantity = newDrugQuantity;
	}

	/**
	 * @return the newDrugComment
	 */
	public String[] getNewDrugComment() {
		return newDrugComment;
	}

	/**
	 * @param newDrugComment the newDrugComment to set
	 */
	public void setNewDrugComment(String[] newDrugComment) {
		this.newDrugComment = newDrugComment;
	}

	/**
	 * @return the newCategoryID
	 */
	public String[] getNewCategoryID() {
		return newCategoryID;
	}

	/**
	 * @param newCategoryID the newCategoryID to set
	 */
	public void setNewCategoryID(String[] newCategoryID) {
		this.newCategoryID = newCategoryID;
	}

	/**
	 * @return the newDrugBarcode
	 */
	public String[] getNewDrugBarcode() {
		return newDrugBarcode;
	}

	/**
	 * @param newDrugBarcode the newDrugBarcode to set
	 */
	public void setNewDrugBarcode(String[] newDrugBarcode) {
		this.newDrugBarcode = newDrugBarcode;
	}

	/**
	 * @return the newIsCompound
	 */
	public String[] getNewIsCompound() {
		return newIsCompound;
	}

	/**
	 * @param newIsCompound the newIsCompound to set
	 */
	public void setNewIsCompound(String[] newIsCompound) {
		this.newIsCompound = newIsCompound;
	}

	/**
	 * @return the newCompound
	 */
	public String[] getNewCompound() {
		return newCompound;
	}

	/**
	 * @param newCompound the newCompound to set
	 */
	public void setNewCompound(String[] newCompound) {
		this.newCompound = newCompound;
	}

	/**
	 * @return the newSection
	 */
	public String[] getNewSection() {
		return newSection;
	}

	/**
	 * @param newSection the newSection to set
	 */
	public void setNewSection(String[] newSection) {
		this.newSection = newSection;
	}

	/**
	 * @return the newInstruction
	 */
	public String[] getNewInstruction() {
		return newInstruction;
	}

	/**
	 * @param newInstruction the newInstruction to set
	 */
	public void setNewInstruction(String[] newInstruction) {
		this.newInstruction = newInstruction;
	}

	/**
	 * @return the newDrugDiscount
	 */
	public String[] getNewDrugDiscount() {
		return newDrugDiscount;
	}

	/**
	 * @param newDrugDiscount the newDrugDiscount to set
	 */
	public void setNewDrugDiscount(String[] newDrugDiscount) {
		this.newDrugDiscount = newDrugDiscount;
	}

	/**
	 * @return the drugSection
	 */
	public String[] getDrugSection() {
		return drugSection;
	}

	/**
	 * @param drugSection the drugSection to set
	 */
	public void setDrugSection(String drugSection[]) {
		this.drugSection = drugSection;
	}

	/**
	 * @return the drugDiscount
	 */
	public String[] getDrugDiscount() {
		return drugDiscount;
	}

	/**
	 * @param drugDiscount the drugDiscount to set
	 */
	public void setDrugDiscount(String drugDiscount[]) {
		this.drugDiscount = drugDiscount;
	}

	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return the onExaminationID
	 */
	public int getOnExaminationID() {
		return onExaminationID;
	}

	/**
	 * @param onExaminationID the onExaminationID to set
	 */
	public void setOnExaminationID(int onExaminationID) {
		this.onExaminationID = onExaminationID;
	}

	/**
	 * @return the onExamination
	 */
	public String getOnExamination() {
		return onExamination;
	}

	/**
	 * @param onExamination the onExamination to set
	 */
	public void setOnExamination(String onExamination) {
		this.onExamination = onExamination;
	}

	/**
	 * @return the onExaminationDate
	 */
	public String getOnExaminationDate() {
		return onExaminationDate;
	}

	/**
	 * @param onExaminationDate the onExaminationDate to set
	 */
	public void setOnExaminationDate(String onExaminationDate) {
		this.onExaminationDate = onExaminationDate;
	}

	/**
	 * @return the symptomDateName
	 */
	public String getSymptomDateName() {
		return symptomDateName;
	}

	/**
	 * @param symptomDateName the symptomDateName to set
	 */
	public void setSymptomDateName(String symptomDateName) {
		this.symptomDateName = symptomDateName;
	}

	/**
	 * @return the vitalSignsComment
	 */
	public String[] getVitalSignsComment() {
		return vitalSignsComment;
	}

	/**
	 * @param vitalSignsComment the vitalSignsComment to set
	 */
	public void setVitalSignsComment(String vitalSignsComment[]) {
		this.vitalSignsComment = vitalSignsComment;
	}

	/**
	 * @return the pageCheck
	 */
	public String getPageCheck() {
		return pageCheck;
	}

	/**
	 * @param pageCheck the pageCheck to set
	 */
	public void setPageCheck(String pageCheck) {
		this.pageCheck = pageCheck;
	}

	/**
	 * @return the MRIFindingDateArray
	 */
	public String[] getMRIFindingDateArray() {
		return MRIFindingDateArray;
	}

	/**
	 * @param MRIFindingDateArray the MRIFindingDateArray to set
	 */
	public void setMRIFindingDateArray(String MRIFindingDateArray[]) {
		this.MRIFindingDateArray = MRIFindingDateArray;
	}

	/**
	 * @return the MRIFindingArray
	 */
	public String[] getMRIFindingArray() {
		return MRIFindingArray;
	}

	/**
	 * @param MRIFindingArray the MRIFindingArray to set
	 */
	public void setMRIFindingArray(String MRIFindingArray[]) {
		this.MRIFindingArray = MRIFindingArray;
	}

	/**
	 * @return the cbcHemoglobinVal
	 */
	public String getCbcHemoglobinVal() {
		return cbcHemoglobinVal;
	}

	/**
	 * @param cbcHemoglobinVal the cbcHemoglobinVal to set
	 */
	public void setCbcHemoglobinVal(String cbcHemoglobinVal) {
		this.cbcHemoglobinVal = cbcHemoglobinVal;
	}

	/**
	 * @return the cbcWBCVal
	 */
	public String getCbcWBCVal() {
		return cbcWBCVal;
	}

	/**
	 * @param cbcWBCVal the cbcWBCVal to set
	 */
	public void setCbcWBCVal(String cbcWBCVal) {
		this.cbcWBCVal = cbcWBCVal;
	}

	/**
	 * @return the cbcPlateleteVal
	 */
	public String getCbcPlateleteVal() {
		return cbcPlateleteVal;
	}

	/**
	 * @param cbcPlateleteVal the cbcPlateleteVal to set
	 */
	public void setCbcPlateleteVal(String cbcPlateleteVal) {
		this.cbcPlateleteVal = cbcPlateleteVal;
	}

	/**
	 * @return the cbcRBCVal
	 */
	public String getCbcRBCVal() {
		return cbcRBCVal;
	}

	/**
	 * @param cbcRBCVal the cbcRBCVal to set
	 */
	public void setCbcRBCVal(String cbcRBCVal) {
		this.cbcRBCVal = cbcRBCVal;
	}

	/**
	 * @return the cbcSmear
	 */
	public String getCbcSmear() {
		return cbcSmear;
	}

	/**
	 * @param cbcSmear the cbcSmear to set
	 */
	public void setCbcSmear(String cbcSmear) {
		this.cbcSmear = cbcSmear;
	}

	/**
	 * @return the liverBilirubinVal
	 */
	public String getLiverBilirubinVal() {
		return liverBilirubinVal;
	}

	/**
	 * @param liverBilirubinVal the liverBilirubinVal to set
	 */
	public void setLiverBilirubinVal(String liverBilirubinVal) {
		this.liverBilirubinVal = liverBilirubinVal;
	}

	/**
	 * @return the liverSCOTVal
	 */
	public String getLiverSCOTVal() {
		return liverSCOTVal;
	}

	/**
	 * @param liverSCOTVal the liverSCOTVal to set
	 */
	public void setLiverSCOTVal(String liverSCOTVal) {
		this.liverSCOTVal = liverSCOTVal;
	}

	/**
	 * @return the liverSGPTVal
	 */
	public String getLiverSGPTVal() {
		return liverSGPTVal;
	}

	/**
	 * @param liverSGPTVal the liverSGPTVal to set
	 */
	public void setLiverSGPTVal(String liverSGPTVal) {
		this.liverSGPTVal = liverSGPTVal;
	}

	/**
	 * @return the liverALPVal
	 */
	public String getLiverALPVal() {
		return liverALPVal;
	}

	/**
	 * @param liverALPVal the liverALPVal to set
	 */
	public void setLiverALPVal(String liverALPVal) {
		this.liverALPVal = liverALPVal;
	}

	/**
	 * @return the liverProteinVal
	 */
	public String getLiverProteinVal() {
		return liverProteinVal;
	}

	/**
	 * @param liverProteinVal the liverProteinVal to set
	 */
	public void setLiverProteinVal(String liverProteinVal) {
		this.liverProteinVal = liverProteinVal;
	}

	/**
	 * @return the renalUreaVal
	 */
	public String getRenalUreaVal() {
		return renalUreaVal;
	}

	/**
	 * @param renalUreaVal the renalUreaVal to set
	 */
	public void setRenalUreaVal(String renalUreaVal) {
		this.renalUreaVal = renalUreaVal;
	}

	/**
	 * @return the renalCreatineVal
	 */
	public String getRenalCreatineVal() {
		return renalCreatineVal;
	}

	/**
	 * @param renalCreatineVal the renalCreatineVal to set
	 */
	public void setRenalCreatineVal(String renalCreatineVal) {
		this.renalCreatineVal = renalCreatineVal;
	}

	/**
	 * @return the renalAcidVal
	 */
	public String getRenalAcidVal() {
		return renalAcidVal;
	}

	/**
	 * @param renalAcidVal the renalAcidVal to set
	 */
	public void setRenalAcidVal(String renalAcidVal) {
		this.renalAcidVal = renalAcidVal;
	}

	/**
	 * @return the appointmentID
	 */
	public String[] getAppointmentID() {
		return appointmentID;
	}

	/**
	 * @param appointmentID the appointmentID to set
	 */
	public void setAppointmentID(String appointmentID[]) {
		this.appointmentID = appointmentID;
	}

	/**
	 * @return the patID
	 */
	public String[] getPatID() {
		return patID;
	}

	/**
	 * @param patID the patID to set
	 */
	public void setPatID(String patID[]) {
		this.patID = patID;
	}

	/**
	 * @return the otherType
	 */
	public String getOtherType() {
		return otherType;
	}

	/**
	 * @param otherType the otherType to set
	 */
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	/**
	 * @return the instructions
	 */
	public String[] getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions[]) {
		this.instructions = instructions;
	}

	/**
	 * @return the prescInstructions
	 */
	public String getPrescInstructions() {
		return prescInstructions;
	}

	/**
	 * @param prescInstructions the prescInstructions to set
	 */
	public void setPrescInstructions(String prescInstructions) {
		this.prescInstructions = prescInstructions;
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
	 * @return the otherLabInvestigationDate
	 */
	public String[] getOtherLabInvestigationDate() {
		return otherLabInvestigationDate;
	}

	/**
	 * @param otherLabInvestigationDate the otherLabInvestigationDate to set
	 */
	public void setOtherLabInvestigationDate(String otherLabInvestigationDate[]) {
		this.otherLabInvestigationDate = otherLabInvestigationDate;
	}

	/**
	 * @return the liverInvestigationDate
	 */
	public String getLiverInvestigationDate() {
		return liverInvestigationDate;
	}

	/**
	 * @param liverInvestigationDate the liverInvestigationDate to set
	 */
	public void setLiverInvestigationDate(String liverInvestigationDate) {
		this.liverInvestigationDate = liverInvestigationDate;
	}

	/**
	 * @return the cBCInvestigationDate
	 */
	public String getCBCInvestigationDate() {
		return CBCInvestigationDate;
	}

	/**
	 * @param cBCInvestigationDate the cBCInvestigationDate to set
	 */
	public void setCBCInvestigationDate(String cBCInvestigationDate) {
		this.CBCInvestigationDate = cBCInvestigationDate;
	}

	/**
	 * @return the renalDay
	 */
	public String getRenalDay() {
		return renalDay;
	}

	/**
	 * @param renalDay the renalDay to set
	 */
	public void setRenalDay(String renalDay) {
		this.renalDay = renalDay;
	}

	/**
	 * @return the renalMonth
	 */
	public String getRenalMonth() {
		return renalMonth;
	}

	/**
	 * @param renalMonth the renalMonth to set
	 */
	public void setRenalMonth(String renalMonth) {
		this.renalMonth = renalMonth;
	}

	/**
	 * @return the renalDate
	 */
	public String getRenalDate() {
		return renalDate;
	}

	/**
	 * @param renalDate the renalDate to set
	 */
	public void setRenalDate(String renalDate) {
		this.renalDate = renalDate;
	}

	/**
	 * @return the liverDate
	 */
	public String getLiverDate() {
		return liverDate;
	}

	/**
	 * @param liverDate the liverDate to set
	 */
	public void setLiverDate(String liverDate) {
		this.liverDate = liverDate;
	}

	/**
	 * @return the liverDay
	 */
	public String getLiverDay() {
		return liverDay;
	}

	/**
	 * @param liverDay the liverDay to set
	 */
	public void setLiverDay(String liverDay) {
		this.liverDay = liverDay;
	}

	/**
	 * @return the liverMonth
	 */
	public String getLiverMonth() {
		return liverMonth;
	}

	/**
	 * @param liverMonth the liverMonth to set
	 */
	public void setLiverMonth(String liverMonth) {
		this.liverMonth = liverMonth;
	}

	/**
	 * @return the cBCDate
	 */
	public String getCBCDate() {
		return CBCDate;
	}

	/**
	 * @param cBCDate the cBCDate to set
	 */
	public void setCBCDate(String cBCDate) {
		this.CBCDate = cBCDate;
	}

	/**
	 * @return the cBCDay
	 */
	public String getCBCDay() {
		return CBCDay;
	}

	/**
	 * @param cBCDay the cBCDay to set
	 */
	public void setCBCDay(String cBCDay) {
		this.CBCDay = cBCDay;
	}

	/**
	 * @return the cBCMonth
	 */
	public String getCBCMonth() {
		return CBCMonth;
	}

	/**
	 * @param cBCMonth the cBCMonth to set
	 */
	public void setCBCMonth(String cBCMonth) {
		this.CBCMonth = cBCMonth;
	}

	/**
	 * @return the sypmtomDateStr
	 */
	public String getSypmtomDateStr() {
		return sypmtomDateStr;
	}

	/**
	 * @return the renalInvestigationDate
	 */
	public String getRenalInvestigationDate() {
		return renalInvestigationDate;
	}

	/**
	 * @param renalInvestigationDate the renalInvestigationDate to set
	 */
	public void setRenalInvestigationDate(String renalInvestigationDate) {
		this.renalInvestigationDate = renalInvestigationDate;
	}

	/**
	 * @return the surgicalID
	 */
	public int getSurgicalID() {
		return surgicalID;
	}

	/**
	 * @param surgicalID the surgicalID to set
	 */
	public void setSurgicalID(int surgicalID) {
		this.surgicalID = surgicalID;
	}

	/**
	 * @return the reportDBName
	 */
	public String getReportDBName() {
		return reportDBName;
	}

	/**
	 * @param reportDBName the reportDBName to set
	 */
	public void setReportDBName(String reportDBName) {
		this.reportDBName = reportDBName;
	}

	/**
	 * @return the medicationID
	 */
	public int getMedicationID() {
		return medicationID;
	}

	/**
	 * @param medicationID the medicationID to set
	 */
	public void setMedicationID(int medicationID) {
		this.medicationID = medicationID;
	}

	/**
	 * @return the medicationName
	 */
	public String getMedicationName() {
		return medicationName;
	}

	/**
	 * @param medicationName the medicationName to set
	 */
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	/**
	 * @return the medicationDesc
	 */
	public String getMedicationDesc() {
		return medicationDesc;
	}

	/**
	 * @param medicationDesc the medicationDesc to set
	 */
	public void setMedicationDesc(String medicationDesc) {
		this.medicationDesc = medicationDesc;
	}

	/**
	 * @return the medication
	 */
	public String[] getMedication() {
		return medication;
	}

	/**
	 * @param medication the medication to set
	 */
	public void setMedication(String medication[]) {
		this.medication = medication;
	}

	/**
	 * @return the medicationDescription
	 */
	public String[] getMedicationDescription() {
		return medicationDescription;
	}

	/**
	 * @param medicationDescription the medicationDescription to set
	 */
	public void setMedicationDescription(String medicationDescription[]) {
		this.medicationDescription = medicationDescription;
	}

	/**
	 * @return the findingsID
	 */
	public int getFindingsID() {
		return findingsID;
	}

	/**
	 * @param findingsID the findingsID to set
	 */
	public void setFindingsID(int findingsID) {
		this.findingsID = findingsID;
	}

	/**
	 * @param sypmtomDateStr the sypmtomDateStr to set
	 */
	public void setSypmtomDateStr(String sypmtomDateStr) {
		this.sypmtomDateStr = sypmtomDateStr;
	}

	/**
	 * @return the symptomDate
	 */
	public String[] getSymptomDate() {
		return symptomDate;
	}

	/**
	 * @param symptomDate the symptomDate to set
	 */
	public void setSymptomDate(String symptomDate[]) {
		this.symptomDate = symptomDate;
	}

	/**
	 * @return the mhoDescription
	 */
	public String getMhoDescription() {
		return mhoDescription;
	}

	/**
	 * @param mhoDescription the mhoDescription to set
	 */
	public void setMhoDescription(String mhoDescription) {
		this.mhoDescription = mhoDescription;
	}

	/**
	 * @return the surgicalHistoryDescription
	 */
	public String getSurgicalHistoryDescription() {
		return surgicalHistoryDescription;
	}

	/**
	 * @param surgicalHistoryDescription the surgicalHistoryDescription to set
	 */
	public void setSurgicalHistoryDescription(String surgicalHistoryDescription) {
		this.surgicalHistoryDescription = surgicalHistoryDescription;
	}

	/**
	 * @return the currentMedications
	 */
	public String getCurrentMedications() {
		return currentMedications;
	}

	/**
	 * @param currentMedications the currentMedications to set
	 */
	public void setCurrentMedications(String currentMedications) {
		this.currentMedications = currentMedications;
	}

	/**
	 * @return the xRAYFindingDate
	 */
	public String getXRAYFindingDate() {
		return XRAYFindingDate;
	}

	/**
	 * @param xRAYFindingDate the xRAYFindingDate to set
	 */
	public void setXRAYFindingDate(String xRAYFindingDate) {
		this.XRAYFindingDate = xRAYFindingDate;
	}

	/**
	 * @return the xRAYFindingType
	 */
	public String getXRAYFindingType() {
		return XRAYFindingType;
	}

	/**
	 * @param xRAYFindingType the xRAYFindingType to set
	 */
	public void setXRAYFindingType(String xRAYFindingType) {
		this.XRAYFindingType = xRAYFindingType;
	}

	/**
	 * @return the xRAYFindingDescription
	 */
	public String getXRAYFindingDescription() {
		return XRAYFindingDescription;
	}

	/**
	 * @param xRAYFindingDescription the xRAYFindingDescription to set
	 */
	public void setXRAYFindingDescription(String xRAYFindingDescription) {
		this.XRAYFindingDescription = xRAYFindingDescription;
	}

	/**
	 * @return the xRAYFindingReportFile
	 */
	public File getXRAYFindingReportFile() {
		return XRAYFindingReportFile;
	}

	/**
	 * @param xRAYFindingReportFile the xRAYFindingReportFile to set
	 */
	public void setXRAYFindingReportFile(File xRAYFindingReportFile) {
		this.XRAYFindingReportFile = xRAYFindingReportFile;
	}

	/**
	 * @return the xRAYFindingReportFileFileName
	 */
	public String getXRAYFindingReportFileFileName() {
		return XRAYFindingReportFileFileName;
	}

	/**
	 * @param xRAYFindingReportFileFileName the xRAYFindingReportFileFileName to set
	 */
	public void setXRAYFindingReportFileFileName(String xRAYFindingReportFileFileName) {
		this.XRAYFindingReportFileFileName = xRAYFindingReportFileFileName;
	}

	/**
	 * @return the xRAYFindingReportFileContentType
	 */
	public String getXRAYFindingReportFileContentType() {
		return XRAYFindingReportFileContentType;
	}

	/**
	 * @param xRAYFindingReportFileContentType the xRAYFindingReportFileContentType
	 *                                         to set
	 */
	public void setXRAYFindingReportFileContentType(String xRAYFindingReportFileContentType) {
		this.XRAYFindingReportFileContentType = xRAYFindingReportFileContentType;
	}

	/**
	 * @return the uCGFindingDate
	 */
	public String getUCGFindingDate() {
		return UCGFindingDate;
	}

	/**
	 * @param uCGFindingDate the uCGFindingDate to set
	 */
	public void setUCGFindingDate(String uCGFindingDate) {
		this.UCGFindingDate = uCGFindingDate;
	}

	/**
	 * @return the uCGFindingType
	 */
	public String getUCGFindingType() {
		return UCGFindingType;
	}

	/**
	 * @param uCGFindingType the uCGFindingType to set
	 */
	public void setUCGFindingType(String uCGFindingType) {
		this.UCGFindingType = uCGFindingType;
	}

	/**
	 * @return the uCGFindingDescription
	 */
	public String getUCGFindingDescription() {
		return UCGFindingDescription;
	}

	/**
	 * @param uCGFindingDescription the uCGFindingDescription to set
	 */
	public void setUCGFindingDescription(String uCGFindingDescription) {
		this.UCGFindingDescription = uCGFindingDescription;
	}

	/**
	 * @return the uCGFindingReportFile
	 */
	public File getUCGFindingReportFile() {
		return UCGFindingReportFile;
	}

	/**
	 * @param uCGFindingReportFile the uCGFindingReportFile to set
	 */
	public void setUCGFindingReportFile(File uCGFindingReportFile) {
		this.UCGFindingReportFile = uCGFindingReportFile;
	}

	/**
	 * @return the uCGFindingReportFileFileName
	 */
	public String getUCGFindingReportFileFileName() {
		return UCGFindingReportFileFileName;
	}

	/**
	 * @param uCGFindingReportFileFileName the uCGFindingReportFileFileName to set
	 */
	public void setUCGFindingReportFileFileName(String uCGFindingReportFileFileName) {
		this.UCGFindingReportFileFileName = uCGFindingReportFileFileName;
	}

	/**
	 * @return the uCGFindingReportFileContentType
	 */
	public String getUCGFindingReportFileContentType() {
		return UCGFindingReportFileContentType;
	}

	/**
	 * @param uCGFindingReportFileContentType the uCGFindingReportFileContentType to
	 *                                        set
	 */
	public void setUCGFindingReportFileContentType(String uCGFindingReportFileContentType) {
		this.UCGFindingReportFileContentType = uCGFindingReportFileContentType;
	}

	/**
	 * @return the cTFindingDate
	 */
	public String getCTFindingDate() {
		return CTFindingDate;
	}

	/**
	 * @param cTFindingDate the cTFindingDate to set
	 */
	public void setCTFindingDate(String cTFindingDate) {
		this.CTFindingDate = cTFindingDate;
	}

	/**
	 * @return the cTFindingType
	 */
	public String getCTFindingType() {
		return CTFindingType;
	}

	/**
	 * @param cTFindingType the cTFindingType to set
	 */
	public void setCTFindingType(String cTFindingType) {
		this.CTFindingType = cTFindingType;
	}

	/**
	 * @return the cTFindingDescription
	 */
	public String getCTFindingDescription() {
		return CTFindingDescription;
	}

	/**
	 * @param cTFindingDescription the cTFindingDescription to set
	 */
	public void setCTFindingDescription(String cTFindingDescription) {
		this.CTFindingDescription = cTFindingDescription;
	}

	/**
	 * @return the cTFindingReportFile
	 */
	public File getCTFindingReportFile() {
		return CTFindingReportFile;
	}

	/**
	 * @param cTFindingReportFile the cTFindingReportFile to set
	 */
	public void setCTFindingReportFile(File cTFindingReportFile) {
		this.CTFindingReportFile = cTFindingReportFile;
	}

	/**
	 * @return the cTFindingReportFileFileName
	 */
	public String getCTFindingReportFileFileName() {
		return CTFindingReportFileFileName;
	}

	/**
	 * @param cTFindingReportFileFileName the cTFindingReportFileFileName to set
	 */
	public void setCTFindingReportFileFileName(String cTFindingReportFileFileName) {
		this.CTFindingReportFileFileName = cTFindingReportFileFileName;
	}

	/**
	 * @return the cTFindingReportFileContentType
	 */
	public String getCTFindingReportFileContentType() {
		return CTFindingReportFileContentType;
	}

	/**
	 * @param cTFindingReportFileContentType the cTFindingReportFileContentType to
	 *                                       set
	 */
	public void setCTFindingReportFileContentType(String cTFindingReportFileContentType) {
		this.CTFindingReportFileContentType = cTFindingReportFileContentType;
	}

	/**
	 * @return the mRIFindingDate
	 */
	public String getMRIFindingDate() {
		return MRIFindingDate;
	}

	/**
	 * @param mRIFindingDate the mRIFindingDate to set
	 */
	public void setMRIFindingDate(String mRIFindingDate) {
		this.MRIFindingDate = mRIFindingDate;
	}

	/**
	 * @return the mRIFindingType
	 */
	public String getMRIFindingType() {
		return MRIFindingType;
	}

	/**
	 * @param mRIFindingType the mRIFindingType to set
	 */
	public void setMRIFindingType(String mRIFindingType) {
		this.MRIFindingType = mRIFindingType;
	}

	/**
	 * @return the mRIFindingDescription
	 */
	public String getMRIFindingDescription() {
		return MRIFindingDescription;
	}

	/**
	 * @param mRIFindingDescription the mRIFindingDescription to set
	 */
	public void setMRIFindingDescription(String mRIFindingDescription) {
		this.MRIFindingDescription = mRIFindingDescription;
	}

	/**
	 * @return the mRIFindingReportFile
	 */
	public File getMRIFindingReportFile() {
		return MRIFindingReportFile;
	}

	/**
	 * @param mRIFindingReportFile the mRIFindingReportFile to set
	 */
	public void setMRIFindingReportFile(File mRIFindingReportFile) {
		this.MRIFindingReportFile = mRIFindingReportFile;
	}

	/**
	 * @return the mRIFindingReportFileFileName
	 */
	public String getMRIFindingReportFileFileName() {
		return MRIFindingReportFileFileName;
	}

	/**
	 * @param mRIFindingReportFileFileName the mRIFindingReportFileFileName to set
	 */
	public void setMRIFindingReportFileFileName(String mRIFindingReportFileFileName) {
		this.MRIFindingReportFileFileName = mRIFindingReportFileFileName;
	}

	/**
	 * @return the mRIFindingReportFileContentType
	 */
	public String getMRIFindingReportFileContentType() {
		return MRIFindingReportFileContentType;
	}

	/**
	 * @param mRIFindingReportFileContentType the mRIFindingReportFileContentType to
	 *                                        set
	 */
	public void setMRIFindingReportFileContentType(String mRIFindingReportFileContentType) {
		this.MRIFindingReportFileContentType = mRIFindingReportFileContentType;
	}

	/**
	 * @return the otherFindingDate
	 */
	public String getOtherFindingDate() {
		return OtherFindingDate;
	}

	/**
	 * @param otherFindingDate the otherFindingDate to set
	 */
	public void setOtherFindingDate(String otherFindingDate) {
		this.OtherFindingDate = otherFindingDate;
	}

	/**
	 * @return the otherFindingType
	 */
	public String getOtherFindingType() {
		return OtherFindingType;
	}

	/**
	 * @param otherFindingType the otherFindingType to set
	 */
	public void setOtherFindingType(String otherFindingType) {
		this.OtherFindingType = otherFindingType;
	}

	/**
	 * @return the otherFindingDescription
	 */
	public String getOtherFindingDescription() {
		return OtherFindingDescription;
	}

	/**
	 * @param otherFindingDescription the otherFindingDescription to set
	 */
	public void setOtherFindingDescription(String otherFindingDescription) {
		this.OtherFindingDescription = otherFindingDescription;
	}

	/**
	 * @return the otherFindingReportFile
	 */
	public File getOtherFindingReportFile() {
		return OtherFindingReportFile;
	}

	/**
	 * @param otherFindingReportFile the otherFindingReportFile to set
	 */
	public void setOtherFindingReportFile(File otherFindingReportFile) {
		this.OtherFindingReportFile = otherFindingReportFile;
	}

	/**
	 * @return the otherFindingReportFileFileName
	 */
	public String getOtherFindingReportFileFileName() {
		return OtherFindingReportFileFileName;
	}

	/**
	 * @param otherFindingReportFileFileName the otherFindingReportFileFileName to
	 *                                       set
	 */
	public void setOtherFindingReportFileFileName(String otherFindingReportFileFileName) {
		this.OtherFindingReportFileFileName = otherFindingReportFileFileName;
	}

	/**
	 * @return the otherFindingReportFileContentType
	 */
	public String getOtherFindingReportFileContentType() {
		return OtherFindingReportFileContentType;
	}

	/**
	 * @param otherFindingReportFileContentType the
	 *                                          otherFindingReportFileContentType to
	 *                                          set
	 */
	public void setOtherFindingReportFileContentType(String otherFindingReportFileContentType) {
		this.OtherFindingReportFileContentType = otherFindingReportFileContentType;
	}

	/**
	 * @return the dosage
	 */
	public double getDosage() {
		return dosage;
	}

	/**
	 * @param dosage the dosage to set
	 */
	public void setDosage(double dosage) {
		this.dosage = dosage;
	}

	/**
	 * @return the isCompStr
	 */
	public String getIsCompStr() {
		return isCompStr;
	}

	/**
	 * @param isCompStr the isCompStr to set
	 */
	public void setIsCompStr(String isCompStr) {
		this.isCompStr = isCompStr;
	}

	/**
	 * @return the otherTestValue
	 */
	public String getOtherTestValue() {
		return otherTestValue;
	}

	/**
	 * @param otherTestValue the otherTestValue to set
	 */
	public void setOtherTestValue(String otherTestValue) {
		this.otherTestValue = otherTestValue;
	}

	/**
	 * @return the otherTest
	 */
	public String getOtherTest() {
		return otherTest;
	}

	/**
	 * @param otherTest the otherTest to set
	 */
	public void setOtherTest(String otherTest) {
		this.otherTest = otherTest;
	}

	/**
	 * @return the otherLabTest
	 */
	public String[] getOtherLabTest() {
		return otherLabTest;
	}

	/**
	 * @param otherLabTest the otherLabTest to set
	 */
	public void setOtherLabTest(String[] otherLabTest) {
		this.otherLabTest = otherLabTest;
	}

	/**
	 * @return the otherLatTestVal
	 */
	public String[] getOtherLatTestVal() {
		return otherLatTestVal;
	}

	/**
	 * @param otherLatTestVal the otherLatTestVal to set
	 */
	public void setOtherLatTestVal(String[] otherLatTestVal) {
		this.otherLatTestVal = otherLatTestVal;
	}

	/**
	 * @return the drugStockID
	 */
	public String[] getDrugStockID() {
		return drugStockID;
	}

	/**
	 * @param drugStockID the drugStockID to set
	 */
	public void setDrugStockID(String[] drugStockID) {
		this.drugStockID = drugStockID;
	}

	/**
	 * @return the stockID
	 */
	public int getStockID() {
		return stockID;
	}

	/**
	 * @param stockID the stockID to set
	 */
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	/**
	 * @return the isCompoundName
	 */
	public int getIsCompoundName() {
		return isCompoundName;
	}

	/**
	 * @param isCompoundName the isCompoundName to set
	 */
	public void setIsCompoundName(int isCompoundName) {
		this.isCompoundName = isCompoundName;
	}

	/**
	 * @return the compoundName
	 */
	public String getCompoundName() {
		return compoundName;
	}

	/**
	 * @param compoundName the compoundName to set
	 */
	public void setCompoundName(String compoundName) {
		this.compoundName = compoundName;
	}

	/**
	 * @return the compound
	 */
	public String[] getCompound() {
		return compound;
	}

	/**
	 * @param compound the compound to set
	 */
	public void setCompound(String[] compound) {
		this.compound = compound;
	}

	/**
	 * @return the isCompound
	 */
	public String[] getIsCompound() {
		return isCompound;
	}

	/**
	 * @param isCompound the isCompound to set
	 */
	public void setIsCompound(String[] isCompound) {
		this.isCompound = isCompound;
	}

	/**
	 * @return the patientIDString
	 */
	public String getPatientIDString() {
		return patientIDString;
	}

	/**
	 * @param patientIDString the patientIDString to set
	 */
	public void setPatientIDString(String patientIDString) {
		this.patientIDString = patientIDString;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
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
	 * @return the clinicSuffix
	 */
	public String getClinicSuffix() {
		return clinicSuffix;
	}

	/**
	 * @param clinicSuffix the clinicSuffix to set
	 */
	public void setClinicSuffix(String clinicSuffix) {
		this.clinicSuffix = clinicSuffix;
	}

	/**
	 * @return the submitBtn
	 */
	public String getSubmitBtn() {
		return submitBtn;
	}

	/**
	 * @param submitBtn the submitBtn to set
	 */
	public void setSubmitBtn(String submitBtn) {
		this.submitBtn = submitBtn;
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
	 * @return the supplierID
	 */
	public int getSupplierID() {
		return supplierID;
	}

	/**
	 * @param supplierID the supplierID to set
	 */
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	/**
	 * @return the cMobileNo
	 */
	public String getCMobileNo() {
		return cMobileNo;
	}

	/**
	 * @param cMobileNo the cMobileNo to set
	 */
	public void setCMobileNo(String cMobileNo) {
		this.cMobileNo = cMobileNo;
	}

	/**
	 * @return the srNo
	 */
	public int getSrNo() {
		return srNo;
	}

	/**
	 * @param srNo the srNo to set
	 */
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	/**
	 * @return the baselineCancerID
	 */
	public int getBaselineCancerID() {
		return baselineCancerID;
	}

	/**
	 * @param baselineCancerID the baselineCancerID to set
	 */
	public void setBaselineCancerID(int baselineCancerID) {
		this.baselineCancerID = baselineCancerID;
	}

	/**
	 * @return the baselineTreatmentID
	 */
	public int getBaselineTreatmentID() {
		return baselineTreatmentID;
	}

	/**
	 * @param baselineTreatmentID the baselineTreatmentID to set
	 */
	public void setBaselineTreatmentID(int baselineTreatmentID) {
		this.baselineTreatmentID = baselineTreatmentID;
	}

	/**
	 * @return the otherSymptoms
	 */
	public String[] getOtherSymptoms() {
		return otherSymptoms;
	}

	/**
	 * @param otherSymptoms the otherSymptoms to set
	 */
	public void setOtherSymptoms(String[] otherSymptoms) {
		this.otherSymptoms = otherSymptoms;
	}

	/**
	 * @return the otherSymptomCode
	 */
	public String[] getOtherSymptomCode() {
		return otherSymptomCode;
	}

	/**
	 * @param otherSymptomCode the otherSymptomCode to set
	 */
	public void setOtherSymptomCode(String[] otherSymptomCode) {
		this.otherSymptomCode = otherSymptomCode;
	}

	/**
	 * @return the otherSymptomValue
	 */
	public String[] getOtherSymptomValue() {
		return otherSymptomValue;
	}

	/**
	 * @param otherSymptomValue the otherSymptomValue to set
	 */
	public void setOtherSymptomValue(String[] otherSymptomValue) {
		this.otherSymptomValue = otherSymptomValue;
	}

	/**
	 * @return the otherSymptomComment
	 */
	public String[] getOtherSymptomComment() {
		return otherSymptomComment;
	}

	/**
	 * @param otherSymptomComment the otherSymptomComment to set
	 */
	public void setOtherSymptomComment(String[] otherSymptomComment) {
		this.otherSymptomComment = otherSymptomComment;
	}

	/**
	 * @return the QOLID
	 */
	public int getQOLID() {
		return QOLID;
	}

	/**
	 * @param QOLID the QOLID to set
	 */
	public void setQOLID(int QOLID) {
		this.QOLID = QOLID;
	}

	/**
	 * @return the tumorSpeficID
	 */
	public String[] getTumorSpeficID() {
		return tumorSpeficID;
	}

	/**
	 * @param tumorSpeficID the tumorSpeficID to set
	 */
	public void setTumorSpeficID(String[] tumorSpeficID) {
		this.tumorSpeficID = tumorSpeficID;
	}

	/**
	 * @return the tumorSpecificDetailID
	 */
	public int getTumorSpecificDetailID() {
		return tumorSpecificDetailID;
	}

	/**
	 * @param tumorSpecificDetailID the tumorSpecificDetailID to set
	 */
	public void setTumorSpecificDetailID(int tumorSpecificDetailID) {
		this.tumorSpecificDetailID = tumorSpecificDetailID;
	}

	/**
	 * @return the visitDay
	 */
	public String getVisitDay() {
		return visitDay;
	}

	/**
	 * @param visitDay the visitDay to set
	 */
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}

	/**
	 * @return the visitMonth
	 */
	public String getVisitMonth() {
		return visitMonth;
	}

	/**
	 * @param visitMonth the visitMonth to set
	 */
	public void setVisitMonth(String visitMonth) {
		this.visitMonth = visitMonth;
	}

	/**
	 * @return the visitYear
	 */
	public String getVisitYear() {
		return visitYear;
	}

	/**
	 * @param visitYear the visitYear to set
	 */
	public void setVisitYear(String visitYear) {
		this.visitYear = visitYear;
	}

	/**
	 * @return the deleteID
	 */
	public int getDeleteID() {
		return deleteID;
	}

	/**
	 * @param deleteID the deleteID to set
	 */
	public void setDeleteID(int deleteID) {
		this.deleteID = deleteID;
	}

	/**
	 * @return the deleteType
	 */
	public String getDeleteType() {
		return deleteType;
	}

	/**
	 * @param deleteType the deleteType to set
	 */
	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	/**
	 * @return the vitalSignsDateName
	 */
	public String getVitalSignsDateName() {
		return vitalSignsDateName;
	}

	/**
	 * @param vitalSignsDateName the vitalSignsDateName to set
	 */
	public void setVitalSignsDateName(String vitalSignsDateName) {
		this.vitalSignsDateName = vitalSignsDateName;
	}

	/**
	 * @return the weightName
	 */
	public double getWeightName() {
		return weightName;
	}

	/**
	 * @param weightName the weightName to set
	 */
	public void setWeightName(double weightName) {
		this.weightName = weightName;
	}

	/**
	 * @return the symptomCheckValue
	 */
	public String getSymptomCheckValue() {
		return symptomCheckValue;
	}

	/**
	 * @param symptomCheckValue the symptomCheckValue to set
	 */
	public void setSymptomCheckValue(String symptomCheckValue) {
		this.symptomCheckValue = symptomCheckValue;
	}

	/**
	 * @return the otherSymptomName
	 */
	public String getOtherSymptomName() {
		return otherSymptomName;
	}

	/**
	 * @param otherSymptomName the otherSymptomName to set
	 */
	public void setOtherSymptomName(String otherSymptomName) {
		this.otherSymptomName = otherSymptomName;
	}

	/**
	 * @return the symptomCheckName
	 */
	public String getSymptomCheckName() {
		return symptomCheckName;
	}

	/**
	 * @param symptomCheckName the symptomCheckName to set
	 */
	public void setSymptomCheckName(String symptomCheckName) {
		this.symptomCheckName = symptomCheckName;
	}

	/**
	 * @return the symptomCheckCode
	 */
	public String getSymptomCheckCode() {
		return symptomCheckCode;
	}

	/**
	 * @param symptomCheckCode the symptomCheckCode to set
	 */
	public void setSymptomCheckCode(String symptomCheckCode) {
		this.symptomCheckCode = symptomCheckCode;
	}

	/**
	 * @return the tumorMarkerNormalValueName
	 */
	public String getTumorMarkerNormalValueName() {
		return tumorMarkerNormalValueName;
	}

	/**
	 * @param tumorMarkerNormalValueName the tumorMarkerNormalValueName to set
	 */
	public void setTumorMarkerNormalValueName(String tumorMarkerNormalValueName) {
		this.tumorMarkerNormalValueName = tumorMarkerNormalValueName;
	}

	/**
	 * @return the tumorMarkerObserverdValueName
	 */
	public String getTumorMarkerObserverdValueName() {
		return tumorMarkerObserverdValueName;
	}

	/**
	 * @param tumorMarkerObserverdValueName the tumorMarkerObserverdValueName to set
	 */
	public void setTumorMarkerObserverdValueName(String tumorMarkerObserverdValueName) {
		this.tumorMarkerObserverdValueName = tumorMarkerObserverdValueName;
	}

	/**
	 * @return the chemoReceivedName
	 */
	public String getChemoReceivedName() {
		return chemoReceivedName;
	}

	/**
	 * @param chemoReceivedName the chemoReceivedName to set
	 */
	public void setChemoReceivedName(String chemoReceivedName) {
		this.chemoReceivedName = chemoReceivedName;
	}

	/**
	 * @return the chemoRecvdAgent
	 */
	public String getChemoRecvdAgent() {
		return chemoRecvdAgent;
	}

	/**
	 * @param chemoRecvdAgent the chemoRecvdAgent to set
	 */
	public void setChemoRecvdAgent(String chemoRecvdAgent) {
		this.chemoRecvdAgent = chemoRecvdAgent;
	}

	/**
	 * @return the chemoRecvdStartDateName
	 */
	public String getChemoRecvdStartDateName() {
		return chemoRecvdStartDateName;
	}

	/**
	 * @param chemoRecvdStartDateName the chemoRecvdStartDateName to set
	 */
	public void setChemoRecvdStartDateName(String chemoRecvdStartDateName) {
		this.chemoRecvdStartDateName = chemoRecvdStartDateName;
	}

	/**
	 * @return the chemoRecvdEndDateName
	 */
	public String getChemoRecvdEndDateName() {
		return chemoRecvdEndDateName;
	}

	/**
	 * @param chemoRecvdEndDateName the chemoRecvdEndDateName to set
	 */
	public void setChemoRecvdEndDateName(String chemoRecvdEndDateName) {
		this.chemoRecvdEndDateName = chemoRecvdEndDateName;
	}

	/**
	 * @return the chemoRecvdNoOfCycleName
	 */
	public int getChemoRecvdNoOfCycleName() {
		return chemoRecvdNoOfCycleName;
	}

	/**
	 * @param chemoRecvdNoOfCycleName the chemoRecvdNoOfCycleName to set
	 */
	public void setChemoRecvdNoOfCycleName(int chemoRecvdNoOfCycleName) {
		this.chemoRecvdNoOfCycleName = chemoRecvdNoOfCycleName;
	}

	/**
	 * @return the chemoToleranceID
	 */
	public int getChemoToleranceID() {
		return chemoToleranceID;
	}

	/**
	 * @param chemoToleranceID the chemoToleranceID to set
	 */
	public void setChemoToleranceID(int chemoToleranceID) {
		this.chemoToleranceID = chemoToleranceID;
	}

	/**
	 * @return the chemoReceivedID
	 */
	public int getChemoReceivedID() {
		return chemoReceivedID;
	}

	/**
	 * @param chemoReceivedID the chemoReceivedID to set
	 */
	public void setChemoReceivedID(int chemoReceivedID) {
		this.chemoReceivedID = chemoReceivedID;
	}

	/**
	 * @return the chemoID
	 */
	public int getChemoID() {
		return chemoID;
	}

	/**
	 * @param chemoID the chemoID to set
	 */
	public void setChemoID(int chemoID) {
		this.chemoID = chemoID;
	}

	/**
	 * @return the radioID
	 */
	public int getRadioID() {
		return radioID;
	}

	/**
	 * @param radioID the radioID to set
	 */
	public void setRadioID(int radioID) {
		this.radioID = radioID;
	}

	/**
	 * @return the surgeryID
	 */
	public int getSurgeryID() {
		return surgeryID;
	}

	/**
	 * @param surgeryID the surgeryID to set
	 */
	public void setSurgeryID(int surgeryID) {
		this.surgeryID = surgeryID;
	}

	/**
	 * @return the tumorMakerID
	 */
	public int getTumorMakerID() {
		return tumorMakerID;
	}

	/**
	 * @param tumorMakerID the tumorMakerID to set
	 */
	public void setTumorMakerID(int tumorMakerID) {
		this.tumorMakerID = tumorMakerID;
	}

	/**
	 * @return the symptomID
	 */
	public int getSymptomID() {
		return symptomID;
	}

	/**
	 * @param symptomID the symptomID to set
	 */
	public void setSymptomID(int symptomID) {
		this.symptomID = symptomID;
	}

	/**
	 * @return the vitalSignsID
	 */
	public int getVitalSignsID() {
		return vitalSignsID;
	}

	/**
	 * @param vitalSignsID the vitalSignsID to set
	 */
	public void setVitalSignsID(int vitalSignsID) {
		this.vitalSignsID = vitalSignsID;
	}

	/**
	 * @return the biopsyFindingsID
	 */
	public int getBiopsyFindingsID() {
		return biopsyFindingsID;
	}

	/**
	 * @param biopsyFindingsID the biopsyFindingsID to set
	 */
	public void setBiopsyFindingsID(int biopsyFindingsID) {
		this.biopsyFindingsID = biopsyFindingsID;
	}

	/**
	 * @return the pETFindingsID
	 */
	public int getPETFindingsID() {
		return PETFindingsID;
	}

	/**
	 * @param pETFindingsID the pETFindingsID to set
	 */
	public void setPETFindingsID(int pETFindingsID) {
		PETFindingsID = pETFindingsID;
	}

	/**
	 * @return the cTMRIFindingsID
	 */
	public int getCTMRIFindingsID() {
		return CTMRIFindingsID;
	}

	/**
	 * @param cTMRIFindingsID the cTMRIFindingsID to set
	 */
	public void setCTMRIFindingsID(int cTMRIFindingsID) {
		CTMRIFindingsID = cTMRIFindingsID;
	}

	/**
	 * @return the otherFindingsID
	 */
	public int getOtherFindingsID() {
		return otherFindingsID;
	}

	/**
	 * @param otherFindingsID the otherFindingsID to set
	 */
	public void setOtherFindingsID(int otherFindingsID) {
		this.otherFindingsID = otherFindingsID;
	}

	/**
	 * @return the tumorMarkerFindingsID
	 */
	public int getTumorMarkerFindingsID() {
		return tumorMarkerFindingsID;
	}

	/**
	 * @param tumorMarkerFindingsID the tumorMarkerFindingsID to set
	 */
	public void setTumorMarkerFindingsID(int tumorMarkerFindingsID) {
		this.tumorMarkerFindingsID = tumorMarkerFindingsID;
	}

	/**
	 * @return the symptomCheckID
	 */
	public String[] getSymptomCheckID() {
		return symptomCheckID;
	}

	/**
	 * @param symptomCheckID the symptomCheckID to set
	 */
	public void setSymptomCheckID(String[] symptomCheckID) {
		this.symptomCheckID = symptomCheckID;
	}

	/**
	 * @return the generalInstruction
	 */
	public String getGeneralInstruction() {
		return generalInstruction;
	}

	/**
	 * @param generalInstruction the generalInstruction to set
	 */
	public void setGeneralInstruction(String generalInstruction) {
		this.generalInstruction = generalInstruction;
	}

	/**
	 * @return the symptomFactor
	 */
	public String getSymptomFactor() {
		return symptomFactor;
	}

	/**
	 * @param symptomFactor the symptomFactor to set
	 */
	public void setSymptomFactor(String symptomFactor) {
		this.symptomFactor = symptomFactor;
	}

	/**
	 * @return the otherSymptomFactor
	 */
	public String[] getOtherSymptomFactor() {
		return otherSymptomFactor;
	}

	/**
	 * @param otherSymptomFactor the otherSymptomFactor to set
	 */
	public void setOtherSymptomFactor(String[] otherSymptomFactor) {
		this.otherSymptomFactor = otherSymptomFactor;
	}

	/**
	 * @return the dietObservations
	 */
	public String[] getDietObservations() {
		return dietObservations;
	}

	/**
	 * @param dietObservations the dietObservations to set
	 */
	public void setDietObservations(String[] dietObservations) {
		this.dietObservations = dietObservations;
	}

	/**
	 * @return the observationsValue
	 */
	public String[] getObservationsValue() {
		return observationsValue;
	}

	/**
	 * @param observationsValue the observationsValue to set
	 */
	public void setObservationsValue(String[] observationsValue) {
		this.observationsValue = observationsValue;
	}

	/**
	 * @return the observationsValueName
	 */
	public String getObservationsValueName() {
		return observationsValueName;
	}

	/**
	 * @param observationsValueName the observationsValueName to set
	 */
	public void setObservationsValueName(String observationsValueName) {
		this.observationsValueName = observationsValueName;
	}

	/**
	 * @return the dietObservationsName
	 */
	public String getDietObservationsName() {
		return dietObservationsName;
	}

	/**
	 * @param dietObservationsName the dietObservationsName to set
	 */
	public void setDietObservationsName(String dietObservationsName) {
		this.dietObservationsName = dietObservationsName;
	}

	/**
	 * @return the otherSymptomFactorName
	 */
	public String getOtherSymptomFactorName() {
		return otherSymptomFactorName;
	}

	/**
	 * @param otherSymptomFactorName the otherSymptomFactorName to set
	 */
	public void setOtherSymptomFactorName(String otherSymptomFactorName) {
		this.otherSymptomFactorName = otherSymptomFactorName;
	}

	/**
	 * @return the otherLifestyleFactorName
	 */
	public String getOtherLifestyleFactorName() {
		return otherLifestyleFactorName;
	}

	/**
	 * @param otherLifestyleFactorName the otherLifestyleFactorName to set
	 */
	public void setOtherLifestyleFactorName(String otherLifestyleFactorName) {
		this.otherLifestyleFactorName = otherLifestyleFactorName;
	}

	/**
	 * @return the otherDietFactorName
	 */
	public String getOtherDietFactorName() {
		return otherDietFactorName;
	}

	/**
	 * @param otherDietFactorName the otherDietFactorName to set
	 */
	public void setOtherDietFactorName(String otherDietFactorName) {
		this.otherDietFactorName = otherDietFactorName;
	}

	/**
	 * @return the routineDietFoodName
	 */
	public String getRoutineDietFoodName() {
		return routineDietFoodName;
	}

	/**
	 * @param routineDietFoodName the routineDietFoodName to set
	 */
	public void setRoutineDietFoodName(String routineDietFoodName) {
		this.routineDietFoodName = routineDietFoodName;
	}

	/**
	 * @return the routineDietTimeName
	 */
	public String getRoutineDietTimeName() {
		return routineDietTimeName;
	}

	/**
	 * @param routineDietTimeName the routineDietTimeName to set
	 */
	public void setRoutineDietTimeName(String routineDietTimeName) {
		this.routineDietTimeName = routineDietTimeName;
	}

	/**
	 * @return the dietID
	 */
	public int getDietID() {
		return dietID;
	}

	/**
	 * @param dietID the dietID to set
	 */
	public void setDietID(int dietID) {
		this.dietID = dietID;
	}

	/**
	 * @return the dietObservationID
	 */
	public int getDietObservationID() {
		return dietObservationID;
	}

	/**
	 * @param dietObservationID the dietObservationID to set
	 */
	public void setDietObservationID(int dietObservationID) {
		this.dietObservationID = dietObservationID;
	}

	/**
	 * @return the dietLifestyleID
	 */
	public int getDietLifestyleID() {
		return dietLifestyleID;
	}

	/**
	 * @param dietLifestyleID the dietLifestyleID to set
	 */
	public void setDietLifestyleID(int dietLifestyleID) {
		this.dietLifestyleID = dietLifestyleID;
	}

	/**
	 * @return the dietScheduleID
	 */
	public int getDietScheduleID() {
		return dietScheduleID;
	}

	/**
	 * @param dietScheduleID the dietScheduleID to set
	 */
	public void setDietScheduleID(int dietScheduleID) {
		this.dietScheduleID = dietScheduleID;
	}

	/**
	 * @return the dietAdviceID
	 */
	public int getDietAdviceID() {
		return dietAdviceID;
	}

	/**
	 * @param dietAdviceID the dietAdviceID to set
	 */
	public void setDietAdviceID(int dietAdviceID) {
		this.dietAdviceID = dietAdviceID;
	}

	/**
	 * @return the grainGood
	 */
	public String getGrainGood() {
		return grainGood;
	}

	/**
	 * @param grainGood the grainGood to set
	 */
	public void setGrainGood(String grainGood) {
		this.grainGood = grainGood;
	}

	/**
	 * @return the grainNotGood
	 */
	public String getGrainNotGood() {
		return grainNotGood;
	}

	/**
	 * @param grainNotGood the grainNotGood to set
	 */
	public void setGrainNotGood(String grainNotGood) {
		this.grainNotGood = grainNotGood;
	}

	/**
	 * @return the grainRecipe
	 */
	public String getGrainRecipe() {
		return grainRecipe;
	}

	/**
	 * @param grainRecipe the grainRecipe to set
	 */
	public void setGrainRecipe(String grainRecipe) {
		this.grainRecipe = grainRecipe;
	}

	/**
	 * @return the grainInstruction
	 */
	public String getGrainInstruction() {
		return grainInstruction;
	}

	/**
	 * @param grainInstruction the grainInstruction to set
	 */
	public void setGrainInstruction(String grainInstruction) {
		this.grainInstruction = grainInstruction;
	}

	/**
	 * @return the pulseGood
	 */
	public String getPulseGood() {
		return pulseGood;
	}

	/**
	 * @param pulseGood the pulseGood to set
	 */
	public void setPulseGood(String pulseGood) {
		this.pulseGood = pulseGood;
	}

	/**
	 * @return the pulseNotGood
	 */
	public String getPulseNotGood() {
		return pulseNotGood;
	}

	/**
	 * @param pulseNotGood the pulseNotGood to set
	 */
	public void setPulseNotGood(String pulseNotGood) {
		this.pulseNotGood = pulseNotGood;
	}

	/**
	 * @return the pulseRecipe
	 */
	public String getPulseRecipe() {
		return pulseRecipe;
	}

	/**
	 * @param pulseRecipe the pulseRecipe to set
	 */
	public void setPulseRecipe(String pulseRecipe) {
		this.pulseRecipe = pulseRecipe;
	}

	/**
	 * @return the pulseInstruction
	 */
	public String getPulseInstruction() {
		return pulseInstruction;
	}

	/**
	 * @param pulseInstruction the pulseInstruction to set
	 */
	public void setPulseInstruction(String pulseInstruction) {
		this.pulseInstruction = pulseInstruction;
	}

	/**
	 * @return the leafyVegGood
	 */
	public String getLeafyVegGood() {
		return leafyVegGood;
	}

	/**
	 * @param leafyVegGood the leafyVegGood to set
	 */
	public void setLeafyVegGood(String leafyVegGood) {
		this.leafyVegGood = leafyVegGood;
	}

	/**
	 * @return the leafyVegNotGood
	 */
	public String getLeafyVegNotGood() {
		return leafyVegNotGood;
	}

	/**
	 * @param leafyVegNotGood the leafyVegNotGood to set
	 */
	public void setLeafyVegNotGood(String leafyVegNotGood) {
		this.leafyVegNotGood = leafyVegNotGood;
	}

	/**
	 * @return the leafyVegRecipe
	 */
	public String getLeafyVegRecipe() {
		return leafyVegRecipe;
	}

	/**
	 * @param leafyVegRecipe the leafyVegRecipe to set
	 */
	public void setLeafyVegRecipe(String leafyVegRecipe) {
		this.leafyVegRecipe = leafyVegRecipe;
	}

	/**
	 * @return the leafyVegInstruction
	 */
	public String getLeafyVegInstruction() {
		return leafyVegInstruction;
	}

	/**
	 * @param leafyVegInstruction the leafyVegInstruction to set
	 */
	public void setLeafyVegInstruction(String leafyVegInstruction) {
		this.leafyVegInstruction = leafyVegInstruction;
	}

	/**
	 * @return the fruitVegGood
	 */
	public String getFruitVegGood() {
		return fruitVegGood;
	}

	/**
	 * @param fruitVegGood the fruitVegGood to set
	 */
	public void setFruitVegGood(String fruitVegGood) {
		this.fruitVegGood = fruitVegGood;
	}

	/**
	 * @return the fruitVegNotGood
	 */
	public String getFruitVegNotGood() {
		return fruitVegNotGood;
	}

	/**
	 * @param fruitVegNotGood the fruitVegNotGood to set
	 */
	public void setFruitVegNotGood(String fruitVegNotGood) {
		this.fruitVegNotGood = fruitVegNotGood;
	}

	/**
	 * @return the fruitVegRecipe
	 */
	public String getFruitVegRecipe() {
		return fruitVegRecipe;
	}

	/**
	 * @param fruitVegRecipe the fruitVegRecipe to set
	 */
	public void setFruitVegRecipe(String fruitVegRecipe) {
		this.fruitVegRecipe = fruitVegRecipe;
	}

	/**
	 * @return the fruitVegInstruction
	 */
	public String getFruitVegInstruction() {
		return fruitVegInstruction;
	}

	/**
	 * @param fruitVegInstruction the fruitVegInstruction to set
	 */
	public void setFruitVegInstruction(String fruitVegInstruction) {
		this.fruitVegInstruction = fruitVegInstruction;
	}

	/**
	 * @return the rootGood
	 */
	public String getRootGood() {
		return rootGood;
	}

	/**
	 * @param rootGood the rootGood to set
	 */
	public void setRootGood(String rootGood) {
		this.rootGood = rootGood;
	}

	/**
	 * @return the rootNotGood
	 */
	public String getRootNotGood() {
		return rootNotGood;
	}

	/**
	 * @param rootNotGood the rootNotGood to set
	 */
	public void setRootNotGood(String rootNotGood) {
		this.rootNotGood = rootNotGood;
	}

	/**
	 * @return the rootInstruction
	 */
	public String getRootInstruction() {
		return rootInstruction;
	}

	/**
	 * @param rootInstruction the rootInstruction to set
	 */
	public void setRootInstruction(String rootInstruction) {
		this.rootInstruction = rootInstruction;
	}

	/**
	 * @return the fruitGood
	 */
	public String getFruitGood() {
		return fruitGood;
	}

	/**
	 * @param fruitGood the fruitGood to set
	 */
	public void setFruitGood(String fruitGood) {
		this.fruitGood = fruitGood;
	}

	/**
	 * @return the fruitNotGood
	 */
	public String getFruitNotGood() {
		return fruitNotGood;
	}

	/**
	 * @param fruitNotGood the fruitNotGood to set
	 */
	public void setFruitNotGood(String fruitNotGood) {
		this.fruitNotGood = fruitNotGood;
	}

	/**
	 * @return the fruitRecipe
	 */
	public String getFruitRecipe() {
		return fruitRecipe;
	}

	/**
	 * @param fruitRecipe the fruitRecipe to set
	 */
	public void setFruitRecipe(String fruitRecipe) {
		this.fruitRecipe = fruitRecipe;
	}

	/**
	 * @return the fruitInstruction
	 */
	public String getFruitInstruction() {
		return fruitInstruction;
	}

	/**
	 * @param fruitInstruction the fruitInstruction to set
	 */
	public void setFruitInstruction(String fruitInstruction) {
		this.fruitInstruction = fruitInstruction;
	}

	/**
	 * @return the spicesGood
	 */
	public String getSpicesGood() {
		return spicesGood;
	}

	/**
	 * @param spicesGood the spicesGood to set
	 */
	public void setSpicesGood(String spicesGood) {
		this.spicesGood = spicesGood;
	}

	/**
	 * @return the spicesNotGood
	 */
	public String getSpicesNotGood() {
		return spicesNotGood;
	}

	/**
	 * @param spicesNotGood the spicesNotGood to set
	 */
	public void setSpicesNotGood(String spicesNotGood) {
		this.spicesNotGood = spicesNotGood;
	}

	/**
	 * @return the spicesInstruction
	 */
	public String getSpicesInstruction() {
		return spicesInstruction;
	}

	/**
	 * @param spicesInstruction the spicesInstruction to set
	 */
	public void setSpicesInstruction(String spicesInstruction) {
		this.spicesInstruction = spicesInstruction;
	}

	/**
	 * @return the dryFruitGood
	 */
	public String getDryFruitGood() {
		return dryFruitGood;
	}

	/**
	 * @param dryFruitGood the dryFruitGood to set
	 */
	public void setDryFruitGood(String dryFruitGood) {
		this.dryFruitGood = dryFruitGood;
	}

	/**
	 * @return the dryFruitNotGood
	 */
	public String getDryFruitNotGood() {
		return dryFruitNotGood;
	}

	/**
	 * @param dryFruitNotGood the dryFruitNotGood to set
	 */
	public void setDryFruitNotGood(String dryFruitNotGood) {
		this.dryFruitNotGood = dryFruitNotGood;
	}

	/**
	 * @return the dryFruitRecipe
	 */
	public String getDryFruitRecipe() {
		return dryFruitRecipe;
	}

	/**
	 * @param dryFruitRecipe the dryFruitRecipe to set
	 */
	public void setDryFruitRecipe(String dryFruitRecipe) {
		this.dryFruitRecipe = dryFruitRecipe;
	}

	/**
	 * @return the dryFruitInstruction
	 */
	public String getDryFruitInstruction() {
		return dryFruitInstruction;
	}

	/**
	 * @param dryFruitInstruction the dryFruitInstruction to set
	 */
	public void setDryFruitInstruction(String dryFruitInstruction) {
		this.dryFruitInstruction = dryFruitInstruction;
	}

	/**
	 * @return the otherGood
	 */
	public String getOtherGood() {
		return otherGood;
	}

	/**
	 * @param otherGood the otherGood to set
	 */
	public void setOtherGood(String otherGood) {
		this.otherGood = otherGood;
	}

	/**
	 * @return the otherNotGood
	 */
	public String getOtherNotGood() {
		return otherNotGood;
	}

	/**
	 * @param otherNotGood the otherNotGood to set
	 */
	public void setOtherNotGood(String otherNotGood) {
		this.otherNotGood = otherNotGood;
	}

	/**
	 * @return the otherInstruction
	 */
	public String getOtherInstruction() {
		return otherInstruction;
	}

	/**
	 * @param otherInstruction the otherInstruction to set
	 */
	public void setOtherInstruction(String otherInstruction) {
		this.otherInstruction = otherInstruction;
	}

	/**
	 * @return the milkProdGood
	 */
	public String getMilkProdGood() {
		return milkProdGood;
	}

	/**
	 * @param milkProdGood the milkProdGood to set
	 */
	public void setMilkProdGood(String milkProdGood) {
		this.milkProdGood = milkProdGood;
	}

	/**
	 * @return the milkProdNotGood
	 */
	public String getMilkProdNotGood() {
		return milkProdNotGood;
	}

	/**
	 * @param milkProdNotGood the milkProdNotGood to set
	 */
	public void setMilkProdNotGood(String milkProdNotGood) {
		this.milkProdNotGood = milkProdNotGood;
	}

	/**
	 * @return the milkProdRecipe
	 */
	public String getMilkProdRecipe() {
		return milkProdRecipe;
	}

	/**
	 * @param milkProdRecipe the milkProdRecipe to set
	 */
	public void setMilkProdRecipe(String milkProdRecipe) {
		this.milkProdRecipe = milkProdRecipe;
	}

	/**
	 * @return the milkProdInstruction
	 */
	public String getMilkProdInstruction() {
		return milkProdInstruction;
	}

	/**
	 * @param milkProdInstruction the milkProdInstruction to set
	 */
	public void setMilkProdInstruction(String milkProdInstruction) {
		this.milkProdInstruction = milkProdInstruction;
	}

	/**
	 * @return the meatGood
	 */
	public String getMeatGood() {
		return meatGood;
	}

	/**
	 * @param meatGood the meatGood to set
	 */
	public void setMeatGood(String meatGood) {
		this.meatGood = meatGood;
	}

	/**
	 * @return the meatNotGood
	 */
	public String getMeatNotGood() {
		return meatNotGood;
	}

	/**
	 * @param meatNotGood the meatNotGood to set
	 */
	public void setMeatNotGood(String meatNotGood) {
		this.meatNotGood = meatNotGood;
	}

	/**
	 * @return the meatRecipe
	 */
	public String getMeatRecipe() {
		return meatRecipe;
	}

	/**
	 * @param meatRecipe the meatRecipe to set
	 */
	public void setMeatRecipe(String meatRecipe) {
		this.meatRecipe = meatRecipe;
	}

	/**
	 * @return the meatInstruction
	 */
	public String getMeatInstruction() {
		return meatInstruction;
	}

	/**
	 * @param meatInstruction the meatInstruction to set
	 */
	public void setMeatInstruction(String meatInstruction) {
		this.meatInstruction = meatInstruction;
	}

	/**
	 * @return the beverageGood
	 */
	public String getBeverageGood() {
		return beverageGood;
	}

	/**
	 * @param beverageGood the beverageGood to set
	 */
	public void setBeverageGood(String beverageGood) {
		this.beverageGood = beverageGood;
	}

	/**
	 * @return the beverageNotGood
	 */
	public String getBeverageNotGood() {
		return beverageNotGood;
	}

	/**
	 * @param beverageNotGood the beverageNotGood to set
	 */
	public void setBeverageNotGood(String beverageNotGood) {
		this.beverageNotGood = beverageNotGood;
	}

	/**
	 * @return the beverageInstruction
	 */
	public String getBeverageInstruction() {
		return beverageInstruction;
	}

	/**
	 * @param beverageInstruction the beverageInstruction to set
	 */
	public void setBeverageInstruction(String beverageInstruction) {
		this.beverageInstruction = beverageInstruction;
	}

	/**
	 * @return the waterGood
	 */
	public String getWaterGood() {
		return waterGood;
	}

	/**
	 * @param waterGood the waterGood to set
	 */
	public void setWaterGood(String waterGood) {
		this.waterGood = waterGood;
	}

	/**
	 * @return the waterNotGood
	 */
	public String getWaterNotGood() {
		return waterNotGood;
	}

	/**
	 * @param waterNotGood the waterNotGood to set
	 */
	public void setWaterNotGood(String waterNotGood) {
		this.waterNotGood = waterNotGood;
	}

	/**
	 * @return the waterInstruction
	 */
	public String getWaterInstruction() {
		return waterInstruction;
	}

	/**
	 * @param waterInstruction the waterInstruction to set
	 */
	public void setWaterInstruction(String waterInstruction) {
		this.waterInstruction = waterInstruction;
	}

	/**
	 * @return the lifestyleFactor
	 */
	public String getLifestyleFactor() {
		return lifestyleFactor;
	}

	/**
	 * @param lifestyleFactor the lifestyleFactor to set
	 */
	public void setLifestyleFactor(String lifestyleFactor) {
		this.lifestyleFactor = lifestyleFactor;
	}

	/**
	 * @return the otherLifestyleFactor
	 */
	public String[] getOtherLifestyleFactor() {
		return otherLifestyleFactor;
	}

	/**
	 * @param otherLifestyleFactor the otherLifestyleFactor to set
	 */
	public void setOtherLifestyleFactor(String[] otherLifestyleFactor) {
		this.otherLifestyleFactor = otherLifestyleFactor;
	}

	/**
	 * @return the otherDietFactor
	 */
	public String getDietFactor() {
		return dietFactor;
	}

	/**
	 * @param dietFactor the dietFactor to set
	 */
	public void setDietFactor(String dietFactor) {
		this.dietFactor = dietFactor;
	}

	/**
	 * @return the otherDietFactor
	 */
	public String[] getOtherDietFactor() {
		return otherDietFactor;
	}

	/**
	 * @param otherDietFactor the otherDietFactor to set
	 */
	public void setOtherDietFactor(String[] otherDietFactor) {
		this.otherDietFactor = otherDietFactor;
	}

	/**
	 * @return the routineDietFood
	 */
	public String[] getRoutineDietFood() {
		return routineDietFood;
	}

	/**
	 * @param routineDietFood the routineDietFood to set
	 */
	public void setRoutineDietFood(String[] routineDietFood) {
		this.routineDietFood = routineDietFood;
	}

	/**
	 * @return the routineDietTime
	 */
	public String[] getRoutineDietTime() {
		return routineDietTime;
	}

	/**
	 * @param routineDietTime the routineDietTime to set
	 */
	public void setRoutineDietTime(String[] routineDietTime) {
		this.routineDietTime = routineDietTime;
	}

	/**
	 * @return the investigationDetailsID
	 */
	public int getInvestigationDetailsID() {
		return investigationDetailsID;
	}

	/**
	 * @param investigationDetailsID the investigationDetailsID to set
	 */
	public void setInvestigationDetailsID(int investigationDetailsID) {
		this.investigationDetailsID = investigationDetailsID;
	}

	/**
	 * @return the tumorEventTypeName
	 */
	public String getTumorEventTypeName() {
		return tumorEventTypeName;
	}

	/**
	 * @param tumorEventTypeName the tumorEventTypeName to set
	 */
	public void setTumorEventTypeName(String tumorEventTypeName) {
		this.tumorEventTypeName = tumorEventTypeName;
	}

	/**
	 * @return the investigationDetailsName
	 */
	public String getInvestigationDetailsName() {
		return investigationDetailsName;
	}

	/**
	 * @param investigationDetailsName the investigationDetailsName to set
	 */
	public void setInvestigationDetailsName(String investigationDetailsName) {
		this.investigationDetailsName = investigationDetailsName;
	}

	/**
	 * @return the tumorStagesName
	 */
	public String getTumorStagesName() {
		return tumorStagesName;
	}

	/**
	 * @param tumorStagesName the tumorStagesName to set
	 */
	public void setTumorStagesName(String tumorStagesName) {
		this.tumorStagesName = tumorStagesName;
	}

	/**
	 * @return the tumorSiteName
	 */
	public String getTumorSiteName() {
		return tumorSiteName;
	}

	/**
	 * @param tumorSiteName the tumorSiteName to set
	 */
	public void setTumorSiteName(String tumorSiteName) {
		this.tumorSiteName = tumorSiteName;
	}

	/**
	 * @return the tumorDimensionName
	 */
	public String getTumorDimensionName() {
		return tumorDimensionName;
	}

	/**
	 * @param tumorDimensionName the tumorDimensionName to set
	 */
	public void setTumorDimensionName(String tumorDimensionName) {
		this.tumorDimensionName = tumorDimensionName;
	}

	/**
	 * @return the investigationDateName
	 */
	public String getInvestigationDateName() {
		return investigationDateName;
	}

	/**
	 * @param investigationDateName the investigationDateName to set
	 */
	public void setInvestigationDateName(String investigationDateName) {
		this.investigationDateName = investigationDateName;
	}

	/**
	 * @return the invstgDateDay
	 */
	public String getInvstgDateDay() {
		return invstgDateDay;
	}

	/**
	 * @param invstgDateDay the invstgDateDay to set
	 */
	public void setInvstgDateDay(String invstgDateDay) {
		this.invstgDateDay = invstgDateDay;
	}

	/**
	 * @return the invstgDateMonth
	 */
	public String getInvstgDateMonth() {
		return invstgDateMonth;
	}

	/**
	 * @param invstgDateMonth the invstgDateMonth to set
	 */
	public void setInvstgDateMonth(String invstgDateMonth) {
		this.invstgDateMonth = invstgDateMonth;
	}

	/**
	 * @return the invstgDateYear
	 */
	public String getInvstgDateYear() {
		return invstgDateYear;
	}

	/**
	 * @param invstgDateYear the invstgDateYear to set
	 */
	public void setInvstgDateYear(String invstgDateYear) {
		this.invstgDateYear = invstgDateYear;
	}

	/**
	 * @return the qolDate
	 */
	public String getQolDate() {
		return qolDate;
	}

	/**
	 * @param qolDate the qolDate to set
	 */
	public void setQolDate(String qolDate) {
		this.qolDate = qolDate;
	}

	/**
	 * @return the biopsyFindingsDateName
	 */
	public String getBiopsyFindingsDateName() {
		return biopsyFindingsDateName;
	}

	/**
	 * @param biopsyFindingsDateName the biopsyFindingsDateName to set
	 */
	public void setBiopsyFindingsDateName(String biopsyFindingsDateName) {
		this.biopsyFindingsDateName = biopsyFindingsDateName;
	}

	/**
	 * @return the biopsyFindingsName
	 */
	public String getBiopsyFindingsName() {
		return biopsyFindingsName;
	}

	/**
	 * @param biopsyFindingsName the biopsyFindingsName to set
	 */
	public void setBiopsyFindingsName(String biopsyFindingsName) {
		this.biopsyFindingsName = biopsyFindingsName;
	}

	/**
	 * @return the pETFindingsDateName
	 */
	public String getPETFindingsDateName() {
		return PETFindingsDateName;
	}

	/**
	 * @param pETFindingsDateName the pETFindingsDateName to set
	 */
	public void setPETFindingsDateName(String pETFindingsDateName) {
		PETFindingsDateName = pETFindingsDateName;
	}

	/**
	 * @return the pETFindingsName
	 */
	public String getPETFindingsName() {
		return PETFindingsName;
	}

	/**
	 * @param pETFindingsName the pETFindingsName to set
	 */
	public void setPETFindingsName(String pETFindingsName) {
		PETFindingsName = pETFindingsName;
	}

	/**
	 * @return the cTFindingsDateName
	 */
	public String getCTFindingsDateName() {
		return CTFindingsDateName;
	}

	/**
	 * @param cTFindingsDateName the cTFindingsDateName to set
	 */
	public void setCTFindingsDateName(String cTFindingsDateName) {
		CTFindingsDateName = cTFindingsDateName;
	}

	/**
	 * @return the cTFindingsName
	 */
	public String getCTFindingsName() {
		return CTFindingsName;
	}

	/**
	 * @param cTFindingsName the cTFindingsName to set
	 */
	public void setCTFindingsName(String cTFindingsName) {
		CTFindingsName = cTFindingsName;
	}

	/**
	 * @return the otherInvstgDateName
	 */
	public String getOtherInvstgDateName() {
		return otherInvstgDateName;
	}

	/**
	 * @param otherInvstgDateName the otherInvstgDateName to set
	 */
	public void setOtherInvstgDateName(String otherInvstgDateName) {
		this.otherInvstgDateName = otherInvstgDateName;
	}

	/**
	 * @return the otherInvstgName
	 */
	public String getOtherInvstgName() {
		return otherInvstgName;
	}

	/**
	 * @param otherInvstgName the otherInvstgName to set
	 */
	public void setOtherInvstgName(String otherInvstgName) {
		this.otherInvstgName = otherInvstgName;
	}

	/**
	 * @return the tumorMarkerDateName
	 */
	public String getTumorMarkerDateName() {
		return tumorMarkerDateName;
	}

	/**
	 * @param tumorMarkerDateName the tumorMarkerDateName to set
	 */
	public void setTumorMarkerDateName(String tumorMarkerDateName) {
		this.tumorMarkerDateName = tumorMarkerDateName;
	}

	/**
	 * @return the chemoTakenName
	 */
	public String getChemoTakenName() {
		return chemoTakenName;
	}

	/**
	 * @param chemoTakenName the chemoTakenName to set
	 */
	public void setChemoTakenName(String chemoTakenName) {
		this.chemoTakenName = chemoTakenName;
	}

	/**
	 * @return the chemoFirstCycleDateName
	 */
	public String getChemoFirstCycleDateName() {
		return chemoFirstCycleDateName;
	}

	/**
	 * @param chemoFirstCycleDateName the chemoFirstCycleDateName to set
	 */
	public void setChemoFirstCycleDateName(String chemoFirstCycleDateName) {
		this.chemoFirstCycleDateName = chemoFirstCycleDateName;
	}

	/**
	 * @return the chemoNoOfCyclesName
	 */
	public int getChemoNoOfCyclesName() {
		return chemoNoOfCyclesName;
	}

	/**
	 * @param chemoNoOfCyclesName the chemoNoOfCyclesName to set
	 */
	public void setChemoNoOfCyclesName(int chemoNoOfCyclesName) {
		this.chemoNoOfCyclesName = chemoNoOfCyclesName;
	}

	/**
	 * @return the chemoAgentsName
	 */
	public String getChemoAgentsName() {
		return chemoAgentsName;
	}

	/**
	 * @param chemoAgentsName the chemoAgentsName to set
	 */
	public void setChemoAgentsName(String chemoAgentsName) {
		this.chemoAgentsName = chemoAgentsName;
	}

	/**
	 * @return the chemoAdvReactionsName
	 */
	public String getChemoAdvReactionsName() {
		return chemoAdvReactionsName;
	}

	/**
	 * @param chemoAdvReactionsName the chemoAdvReactionsName to set
	 */
	public void setChemoAdvReactionsName(String chemoAdvReactionsName) {
		this.chemoAdvReactionsName = chemoAdvReactionsName;
	}

	/**
	 * @return the radioTakenName
	 */
	public String getRadioTakenName() {
		return radioTakenName;
	}

	/**
	 * @param radioTakenName the radioTakenName to set
	 */
	public void setRadioTakenName(String radioTakenName) {
		this.radioTakenName = radioTakenName;
	}

	/**
	 * @return the radioDetailsName
	 */
	public String getRadioDetailsName() {
		return radioDetailsName;
	}

	/**
	 * @param radioDetailsName the radioDetailsName to set
	 */
	public void setRadioDetailsName(String radioDetailsName) {
		this.radioDetailsName = radioDetailsName;
	}

	/**
	 * @return the radioFirstCycleDateName
	 */
	public String getRadioFirstCycleDateName() {
		return radioFirstCycleDateName;
	}

	/**
	 * @param radioFirstCycleDateName the radioFirstCycleDateName to set
	 */
	public void setRadioFirstCycleDateName(String radioFirstCycleDateName) {
		this.radioFirstCycleDateName = radioFirstCycleDateName;
	}

	/**
	 * @return the radioNoOfCyclesName
	 */
	public int getRadioNoOfCyclesName() {
		return radioNoOfCyclesName;
	}

	/**
	 * @param radioNoOfCyclesName the radioNoOfCyclesName to set
	 */
	public void setRadioNoOfCyclesName(int radioNoOfCyclesName) {
		this.radioNoOfCyclesName = radioNoOfCyclesName;
	}

	/**
	 * @return the radioAdvReactionsName
	 */
	public String getRadioAdvReactionsName() {
		return radioAdvReactionsName;
	}

	/**
	 * @param radioAdvReactionsName the radioAdvReactionsName to set
	 */
	public void setRadioAdvReactionsName(String radioAdvReactionsName) {
		this.radioAdvReactionsName = radioAdvReactionsName;
	}

	/**
	 * @return the underwentSurgeryName
	 */
	public String getUnderwentSurgeryName() {
		return underwentSurgeryName;
	}

	/**
	 * @param underwentSurgeryName the underwentSurgeryName to set
	 */
	public void setUnderwentSurgeryName(String underwentSurgeryName) {
		this.underwentSurgeryName = underwentSurgeryName;
	}

	/**
	 * @return the underwentSurgeryDateName
	 */
	public String getUnderwentSurgeryDateName() {
		return underwentSurgeryDateName;
	}

	/**
	 * @param underwentSurgeryDateName the underwentSurgeryDateName to set
	 */
	public void setUnderwentSurgeryDateName(String underwentSurgeryDateName) {
		this.underwentSurgeryDateName = underwentSurgeryDateName;
	}

	/**
	 * @return the surgery
	 */
	public String getSurgery() {
		return surgery;
	}

	/**
	 * @param surgery the surgery to set
	 */
	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	/**
	 * @return the surgeryExcisionName
	 */
	public String getSurgeryExcisionName() {
		return surgeryExcisionName;
	}

	/**
	 * @param surgeryExcisionName the surgeryExcisionName to set
	 */
	public void setSurgeryExcisionName(String surgeryExcisionName) {
		this.surgeryExcisionName = surgeryExcisionName;
	}

	/**
	 * @return the marginsFreeOfTumorName
	 */
	public String getMarginsFreeOfTumorName() {
		return marginsFreeOfTumorName;
	}

	/**
	 * @param marginsFreeOfTumorName the marginsFreeOfTumorName to set
	 */
	public void setMarginsFreeOfTumorName(String marginsFreeOfTumorName) {
		this.marginsFreeOfTumorName = marginsFreeOfTumorName;
	}

	/**
	 * @return the lymphNodesDissectedName
	 */
	public String getLymphNodesDissectedName() {
		return lymphNodesDissectedName;
	}

	/**
	 * @param lymphNodesDissectedName the lymphNodesDissectedName to set
	 */
	public void setLymphNodesDissectedName(String lymphNodesDissectedName) {
		this.lymphNodesDissectedName = lymphNodesDissectedName;
	}

	/**
	 * @return the lymphNodesNoName
	 */
	public int getLymphNodesNoName() {
		return lymphNodesNoName;
	}

	/**
	 * @param lymphNodesNoName the lymphNodesNoName to set
	 */
	public void setLymphNodesNoName(int lymphNodesNoName) {
		this.lymphNodesNoName = lymphNodesNoName;
	}

	/**
	 * @return the positiveNodesName
	 */
	public int getPositiveNodesName() {
		return positiveNodesName;
	}

	/**
	 * @param positiveNodesName the positiveNodesName to set
	 */
	public void setPositiveNodesName(int positiveNodesName) {
		this.positiveNodesName = positiveNodesName;
	}

	/**
	 * @return the tumorMarkerName
	 */
	public String getTumorMarkerName() {
		return tumorMarkerName;
	}

	/**
	 * @param tumorMarkerName the tumorMarkerName to set
	 */
	public void setTumorMarkerName(String tumorMarkerName) {
		this.tumorMarkerName = tumorMarkerName;
	}

	/**
	 * @return the chemoAgentName
	 */
	public String getChemoAgentName() {
		return chemoAgentName;
	}

	/**
	 * @param chemoAgentName the chemoAgentName to set
	 */
	public void setChemoAgentName(String chemoAgentName) {
		this.chemoAgentName = chemoAgentName;
	}

	/**
	 * @return the chemoDoseName
	 */
	public String getChemoDoseName() {
		return chemoDoseName;
	}

	/**
	 * @param chemoDoseName the chemoDoseName to set
	 */
	public void setChemoDoseName(String chemoDoseName) {
		this.chemoDoseName = chemoDoseName;
	}

	/**
	 * @return the chemoCycleName
	 */
	public String getChemoCycleName() {
		return chemoCycleName;
	}

	/**
	 * @param chemoCycleName the chemoCycleName to set
	 */
	public void setChemoCycleName(String chemoCycleName) {
		this.chemoCycleName = chemoCycleName;
	}

	/**
	 * @return the dateOfChemoName
	 */
	public String getDateOfChemoName() {
		return dateOfChemoName;
	}

	/**
	 * @param dateOfChemoName the dateOfChemoName to set
	 */
	public void setDateOfChemoName(String dateOfChemoName) {
		this.dateOfChemoName = dateOfChemoName;
	}

	/**
	 * @return the treatmentTypeName
	 */
	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	/**
	 * @param treatmentTypeName the treatmentTypeName to set
	 */
	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	/**
	 * @return the metaDiagnDay
	 */
	public String getMetaDiagnDay() {
		return metaDiagnDay;
	}

	/**
	 * @param metaDiagnDay the metaDiagnDay to set
	 */
	public void setMetaDiagnDay(String metaDiagnDay) {
		this.metaDiagnDay = metaDiagnDay;
	}

	/**
	 * @return the metaDiagnMon
	 */
	public String getMetaDiagnMon() {
		return metaDiagnMon;
	}

	/**
	 * @param metaDiagnMon the metaDiagnMon to set
	 */
	public void setMetaDiagnMon(String metaDiagnMon) {
		this.metaDiagnMon = metaDiagnMon;
	}

	/**
	 * @return the metaDiagnYear
	 */
	public String getMetaDiagnYear() {
		return metaDiagnYear;
	}

	/**
	 * @param metaDiagnYear the metaDiagnYear to set
	 */
	public void setMetaDiagnYear(String metaDiagnYear) {
		this.metaDiagnYear = metaDiagnYear;
	}

	/**
	 * @return the primaryDiagnDay
	 */
	public String getPrimaryDiagnDay() {
		return primaryDiagnDay;
	}

	/**
	 * @param primaryDiagnDay the primaryDiagnDay to set
	 */
	public void setPrimaryDiagnDay(String primaryDiagnDay) {
		this.primaryDiagnDay = primaryDiagnDay;
	}

	/**
	 * @return the primaryDiagnMon
	 */
	public String getPrimaryDiagnMon() {
		return primaryDiagnMon;
	}

	/**
	 * @param primaryDiagnMon the primaryDiagnMon to set
	 */
	public void setPrimaryDiagnMon(String primaryDiagnMon) {
		this.primaryDiagnMon = primaryDiagnMon;
	}

	/**
	 * @return the primaryDiagnYear
	 */
	public String getPrimaryDiagnYear() {
		return primaryDiagnYear;
	}

	/**
	 * @param primaryDiagnYear the primaryDiagnYear to set
	 */
	public void setPrimaryDiagnYear(String primaryDiagnYear) {
		this.primaryDiagnYear = primaryDiagnYear;
	}

	/**
	 * @return the tnmTVal
	 */
	public String getTnmTVal() {
		return tnmTVal;
	}

	/**
	 * @param tnmTVal the tnmTVal to set
	 */
	public void setTnmTVal(String tnmTVal) {
		this.tnmTVal = tnmTVal;
	}

	/**
	 * @return the tnmNVal
	 */
	public String getTnmNVal() {
		return tnmNVal;
	}

	/**
	 * @param tnmNVal the tnmNVal to set
	 */
	public void setTnmNVal(String tnmNVal) {
		this.tnmNVal = tnmNVal;
	}

	/**
	 * @return the tnmMVal
	 */
	public String getTnmMVal() {
		return tnmMVal;
	}

	/**
	 * @param tnmMVal the tnmMVal to set
	 */
	public void setTnmMVal(String tnmMVal) {
		this.tnmMVal = tnmMVal;
	}

	/**
	 * @return the personalHistOther
	 */
	public String getPersonalHistOther() {
		return personalHistOther;
	}

	/**
	 * @param personalHistOther the personalHistOther to set
	 */
	public void setPersonalHistOther(String personalHistOther) {
		this.personalHistOther = personalHistOther;
	}

	/**
	 * @return the medHistDiagnosis
	 */
	public String getMedHistDiagnosis() {
		return medHistDiagnosis;
	}

	/**
	 * @param medHistDiagnosis the medHistDiagnosis to set
	 */
	public void setMedHistDiagnosis(String medHistDiagnosis) {
		this.medHistDiagnosis = medHistDiagnosis;
	}

	/**
	 * @return the medHistComment
	 */
	public String getMedHistComment() {
		return medHistComment;
	}

	/**
	 * @param medHistComment the medHistComment to set
	 */
	public void setMedHistComment(String medHistComment) {
		this.medHistComment = medHistComment;
	}

	/**
	 * @return the medHistOther
	 */
	public String getMedHistOther() {
		return medHistOther;
	}

	/**
	 * @param medHistOther the medHistOther to set
	 */
	public void setMedHistOther(String medHistOther) {
		this.medHistOther = medHistOther;
	}

	/**
	 * @return the famHistOther
	 */
	public String getFamHistOther() {
		return famHistOther;
	}

	/**
	 * @param famHistOther the famHistOther to set
	 */
	public void setFamHistOther(String famHistOther) {
		this.famHistOther = famHistOther;
	}

	/**
	 * @return the famHistComment
	 */
	public String getFamHistComment() {
		return famHistComment;
	}

	/**
	 * @param famHistComment the famHistComment to set
	 */
	public void setFamHistComment(String famHistComment) {
		this.famHistComment = famHistComment;
	}

	/**
	 * @return the famHistDiagnosis
	 */
	public String getFamHistDiagnosis() {
		return famHistDiagnosis;
	}

	/**
	 * @param famHistDiagnosis the famHistDiagnosis to set
	 */
	public void setFamHistDiagnosis(String famHistDiagnosis) {
		this.famHistDiagnosis = famHistDiagnosis;
	}

	/**
	 * @return the cashPaid
	 */
	public double getCashPaid() {
		return cashPaid;
	}

	/**
	 * @param cashPaid the cashPaid to set
	 */
	public void setCashPaid(double cashPaid) {
		this.cashPaid = cashPaid;
	}

	/**
	 * @return the cashToReturn
	 */
	public double getCashToReturn() {
		return cashToReturn;
	}

	/**
	 * @param cashToReturn the cashToReturn to set
	 */
	public void setCashToReturn(double cashToReturn) {
		this.cashToReturn = cashToReturn;
	}

	/**
	 * @return the creditNoteBal
	 */
	public double getCreditNoteBal() {
		return creditNoteBal;
	}

	/**
	 * @param creditNoteBal the creditNoteBal to set
	 */
	public void setCreditNoteBal(double creditNoteBal) {
		this.creditNoteBal = creditNoteBal;
	}

	/**
	 * @return the totalVat
	 */
	public double getTotalVat() {
		return totalVat;
	}

	/**
	 * @param totalVat the totalVat to set
	 */
	public void setTotalVat(double totalVat) {
		this.totalVat = totalVat;
	}

	/**
	 * @return the prescID
	 */
	public String[] getPrescID() {
		return prescID;
	}

	/**
	 * @param prescID the prescID to set
	 */
	public void setPrescID(String[] prescID) {
		this.prescID = prescID;
	}

	/**
	 * @return the chequeIssuedBy
	 */
	public String getChequeIssuedBy() {
		return chequeIssuedBy;
	}

	/**
	 * @param chequeIssuedBy the chequeIssuedBy to set
	 */
	public void setChequeIssuedBy(String chequeIssuedBy) {
		this.chequeIssuedBy = chequeIssuedBy;
	}

	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}

	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	/**
	 * @return the chequeBankName
	 */
	public String getChequeBankName() {
		return chequeBankName;
	}

	/**
	 * @param chequeBankName the chequeBankName to set
	 */
	public void setChequeBankName(String chequeBankName) {
		this.chequeBankName = chequeBankName;
	}

	/**
	 * @return the chequeBankBranch
	 */
	public String getChequeBankBranch() {
		return chequeBankBranch;
	}

	/**
	 * @param chequeBankBranch the chequeBankBranch to set
	 */
	public void setChequeBankBranch(String chequeBankBranch) {
		this.chequeBankBranch = chequeBankBranch;
	}

	/**
	 * @return the chequeDate
	 */
	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the chequeAmt
	 */
	public double getChequeAmt() {
		return chequeAmt;
	}

	/**
	 * @param chequeAmt the chequeAmt to set
	 */
	public void setChequeAmt(double chequeAmt) {
		this.chequeAmt = chequeAmt;
	}

	/**
	 * @return the cardMobileNo
	 */
	public String getCardMobileNo() {
		return cardMobileNo;
	}

	/**
	 * @param cardMobileNo the cardMobileNo to set
	 */
	public void setCardMobileNo(String cardMobileNo) {
		this.cardMobileNo = cardMobileNo;
	}

	/**
	 * @return the productCat
	 */
	public String getProductCat() {
		return productCat;
	}

	/**
	 * @param productCat the productCat to set
	 */
	public void setProductCat(String productCat) {
		this.productCat = productCat;
	}

	/**
	 * @return the productTradeName
	 */
	public String getProductTradeName() {
		return productTradeName;
	}

	/**
	 * @param productTradeName the productTradeName to set
	 */
	public void setProductTradeName(String productTradeName) {
		this.productTradeName = productTradeName;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the productVAT
	 */
	public double getProductVAT() {
		return productVAT;
	}

	/**
	 * @param productVAT the productVAT to set
	 */
	public void setProductVAT(double productVAT) {
		this.productVAT = productVAT;
	}

	/**
	 * @return the receiptID
	 */
	public int getReceiptID() {
		return receiptID;
	}

	/**
	 * @param receiptID the receiptID to set
	 */
	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}

	/**
	 * @return the productCatID
	 */
	public int getProductCatID() {
		return productCatID;
	}

	/**
	 * @param productCatID the productCatID to set
	 */
	public void setProductCatID(int productCatID) {
		this.productCatID = productCatID;
	}

	/**
	 * @return the billingProdCatID
	 */
	public String[] getBillingProdCatID() {
		return billingProdCatID;
	}

	/**
	 * @param billingProdCatID the billingProdCatID to set
	 */
	public void setBillingProdCatID(String[] billingProdCatID) {
		this.billingProdCatID = billingProdCatID;
	}

	/**
	 * @return the billingProdTradeNameID
	 */
	public String[] getBillingProdTradeNameID() {
		return billingProdTradeNameID;
	}

	/**
	 * @param billingProdTradeNameID the billingProdTradeNameID to set
	 */
	public void setBillingProdTradeNameID(String[] billingProdTradeNameID) {
		this.billingProdTradeNameID = billingProdTradeNameID;
	}

	/**
	 * @return the billingProdBarcode
	 */
	public String[] getBillingProdBarcode() {
		return billingProdBarcode;
	}

	/**
	 * @param billingProdBarcode the billingProdBarcode to set
	 */
	public void setBillingProdBarcode(String[] billingProdBarcode) {
		this.billingProdBarcode = billingProdBarcode;
	}

	/**
	 * @return the billingProdQuantity
	 */
	public String[] getBillingProdQuantity() {
		return billingProdQuantity;
	}

	/**
	 * @param billingProdQuantity the billingProdQuantity to set
	 */
	public void setBillingProdQuantity(String[] billingProdQuantity) {
		this.billingProdQuantity = billingProdQuantity;
	}

	/**
	 * @return the billingProdRate
	 */
	public String[] getBillingProdRate() {
		return billingProdRate;
	}

	/**
	 * @param billingProdRate the billingProdRate to set
	 */
	public void setBillingProdRate(String[] billingProdRate) {
		this.billingProdRate = billingProdRate;
	}

	/**
	 * @return the billingProdAmount
	 */
	public String[] getBillingProdAmount() {
		return billingProdAmount;
	}

	/**
	 * @param billingProdAmount the billingProdAmount to set
	 */
	public void setBillingProdAmount(String[] billingProdAmount) {
		this.billingProdAmount = billingProdAmount;
	}

	/**
	 * @return the billingProdVAT
	 */
	public String[] getBillingProdVAT() {
		return billingProdVAT;
	}

	/**
	 * @param billingProdVAT the billingProdVAT to set
	 */
	public void setBillingProdVAT(String[] billingProdVAT) {
		this.billingProdVAT = billingProdVAT;
	}

	/**
	 * @return the billingCategoryID
	 */
	public int getBillingCategoryID() {
		return billingCategoryID;
	}

	/**
	 * @param billingCategoryID the billingCategoryID to set
	 */
	public void setBillingCategoryID(int billingCategoryID) {
		this.billingCategoryID = billingCategoryID;
	}

	/**
	 * @return the billingProdID
	 */
	public int getBillingProdID() {
		return billingProdID;
	}

	/**
	 * @param billingProdID the billingProdID to set
	 */
	public void setBillingProdID(int billingProdID) {
		this.billingProdID = billingProdID;
	}

	/**
	 * @return the billingBarcode
	 */
	public String getBillingBarcode() {
		return billingBarcode;
	}

	/**
	 * @param billingBarcode the billingBarcode to set
	 */
	public void setBillingBarcode(String billingBarcode) {
		this.billingBarcode = billingBarcode;
	}

	/**
	 * @return the billingProdCat
	 */
	public String getBillingProdCat() {
		return billingProdCat;
	}

	/**
	 * @param billingProdCat the billingProdCat to set
	 */
	public void setBillingProdCat(String billingProdCat) {
		this.billingProdCat = billingProdCat;
	}

	/**
	 * @return the billingQuantity
	 */
	public int getBillingQuantity() {
		return billingQuantity;
	}

	/**
	 * @param billingQuantity the billingQuantity to set
	 */
	public void setBillingQuantity(int billingQuantity) {
		this.billingQuantity = billingQuantity;
	}

	/**
	 * @return the billingRate
	 */
	public double getBillingRate() {
		return billingRate;
	}

	/**
	 * @param billingRate the billingRate to set
	 */
	public void setBillingRate(double billingRate) {
		this.billingRate = billingRate;
	}

	/**
	 * @return the billingAmount
	 */
	public double getBillingAmount() {
		return billingAmount;
	}

	/**
	 * @param billingAmount the billingAmount to set
	 */
	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
	}

	/**
	 * @return the billingVAT
	 */
	public double getBillingVAT() {
		return billingVAT;
	}

	/**
	 * @param billingVAT the billingVAT to set
	 */
	public void setBillingVAT(double billingVAT) {
		this.billingVAT = billingVAT;
	}

	/**
	 * @return the billingProductName
	 */
	public String getBillingProductName() {
		return billingProductName;
	}

	/**
	 * @param billingProductName the billingProductName to set
	 */
	public void setBillingProductName(String billingProductName) {
		this.billingProductName = billingProductName;
	}

	/**
	 * @return the categoryID
	 */
	public String[] getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(String[] categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the drugBarcode
	 */
	public String[] getDrugBarcode() {
		return drugBarcode;
	}

	/**
	 * @param drugBarcode the drugBarcode to set
	 */
	public void setDrugBarcode(String[] drugBarcode) {
		this.drugBarcode = drugBarcode;
	}

	/**
	 * @return the productCategoryID
	 */
	public int getProductCategoryID() {
		return productCategoryID;
	}

	/**
	 * @param productCategoryID the productCategoryID to set
	 */
	public void setProductCategoryID(int productCategoryID) {
		this.productCategoryID = productCategoryID;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the drugID
	 */
	public String[] getDrugID() {
		return drugID;
	}

	/**
	 * @param drugID the drugID to set
	 */
	public void setDrugID(String[] drugID) {
		this.drugID = drugID;
	}

	/**
	 * @return the drugNoOfDays
	 */
	public String[] getDrugNoOfDays() {
		return drugNoOfDays;
	}

	/**
	 * @param drugNoOfDays the drugNoOfDays to set
	 */
	public void setDrugNoOfDays(String[] drugNoOfDays) {
		this.drugNoOfDays = drugNoOfDays;
	}

	/**
	 * @return the drugFrequency
	 */
	public String[] getDrugFrequency() {
		return drugFrequency;
	}

	/**
	 * @param drugFrequency the drugFrequency to set
	 */
	public void setDrugFrequency(String[] drugFrequency) {
		this.drugFrequency = drugFrequency;
	}

	/**
	 * @return the drugDosage
	 */
	public String[] getDrugDosage() {
		return drugDosage;
	}

	/**
	 * @param drugDosage the drugDosage to set
	 */
	public void setDrugDosage(String[] drugDosage) {
		this.drugDosage = drugDosage;
	}

	/**
	 * @return the drugQuantity
	 */
	public String[] getDrugQuantity() {
		return drugQuantity;
	}

	/**
	 * @param drugQuantity the drugQuantity to set
	 */
	public void setDrugQuantity(String[] drugQuantity) {
		this.drugQuantity = drugQuantity;
	}

	/**
	 * @return the drugComment
	 */
	public String[] getDrugComment() {
		return drugComment;
	}

	/**
	 * @param drugComment the drugComment to set
	 */
	public void setDrugComment(String[] drugComment) {
		this.drugComment = drugComment;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the receiptNo
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * @param receiptNo the receiptNo to set
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	/**
	 * @return the receiptDate
	 */
	public String getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	/**
	 * @return the consultationCharges
	 */
	public double getConsultationCharges() {
		return consultationCharges;
	}

	/**
	 * @param consultationCharges the consultationCharges to set
	 */
	public void setConsultationCharges(double consultationCharges) {
		this.consultationCharges = consultationCharges;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the netAmount
	 */
	public double getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the advPayment
	 */
	public double getAdvPayment() {
		return advPayment;
	}

	/**
	 * @param advPayment the advPayment to set
	 */
	public void setAdvPayment(double advPayment) {
		this.advPayment = advPayment;
	}

	/**
	 * @return the balPayment
	 */
	public double getBalPayment() {
		return balPayment;
	}

	/**
	 * @param balPayment the balPayment to set
	 */
	public void setBalPayment(double balPayment) {
		this.balPayment = balPayment;
	}

	/**
	 * @return the taxAmount
	 */
	public double getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productBarcode
	 */
	public String getProductBarcode() {
		return productBarcode;
	}

	/**
	 * @param productBarcode the productBarcode to set
	 */
	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	/**
	 * @return the refReceiptNo
	 */
	public String getRefReceiptNo() {
		return refReceiptNo;
	}

	/**
	 * @param refReceiptNo the refReceiptNo to set
	 */
	public void setRefReceiptNo(String refReceiptNo) {
		this.refReceiptNo = refReceiptNo;
	}

	/**
	 * @return the productAmount
	 */
	public double getProductAmount() {
		return productAmount;
	}

	/**
	 * @param productAmount the productAmount to set
	 */
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}

	/**
	 * @return the productRate
	 */
	public double getProductRate() {
		return productRate;
	}

	/**
	 * @param productRate the productRate to set
	 */
	public void setProductRate(double productRate) {
		this.productRate = productRate;
	}

	/**
	 * @return the productQuantity
	 */
	public double getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @return the productTaxAmount
	 */
	public double getProductTaxAmount() {
		return productTaxAmount;
	}

	/**
	 * @param productTaxAmount the productTaxAmount to set
	 */
	public void setProductTaxAmount(double productTaxAmount) {
		this.productTaxAmount = productTaxAmount;
	}

	/**
	 * @return the productID
	 */
	public int getProductID() {
		return productID;
	}

	/**
	 * @param productID the productID to set
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}

	/**
	 * @return the transactionID
	 */
	public int getTransactionID() {
		return transactionID;
	}

	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the chemoTaken
	 */
	public String[] getChemoTaken() {
		return chemoTaken;
	}

	/**
	 * @param chemoTaken the chemoTaken to set
	 */
	public void setChemoTaken(String[] chemoTaken) {
		this.chemoTaken = chemoTaken;
	}

	/**
	 * @return the chemoFirstCycleDate
	 */
	public String[] getChemoFirstCycleDate() {
		return chemoFirstCycleDate;
	}

	/**
	 * @param chemoFirstCycleDate the chemoFirstCycleDate to set
	 */
	public void setChemoFirstCycleDate(String[] chemoFirstCycleDate) {
		this.chemoFirstCycleDate = chemoFirstCycleDate;
	}

	/**
	 * @return the chemoNoOfCycles
	 */
	public String[] getChemoNoOfCycles() {
		return chemoNoOfCycles;
	}

	/**
	 * @param chemoNoOfCycles the chemoNoOfCycles to set
	 */
	public void setChemoNoOfCycles(String[] chemoNoOfCycles) {
		this.chemoNoOfCycles = chemoNoOfCycles;
	}

	/**
	 * @return the chemoAgents
	 */
	public String[] getChemoAgents() {
		return chemoAgents;
	}

	/**
	 * @param chemoAgents the chemoAgents to set
	 */
	public void setChemoAgents(String[] chemoAgents) {
		this.chemoAgents = chemoAgents;
	}

	/**
	 * @return the chemoAdvReactions
	 */
	public String[] getChemoAdvReactions() {
		return chemoAdvReactions;
	}

	/**
	 * @param chemoAdvReactions the chemoAdvReactions to set
	 */
	public void setChemoAdvReactions(String[] chemoAdvReactions) {
		this.chemoAdvReactions = chemoAdvReactions;
	}

	/**
	 * @return the radioTaken
	 */
	public String[] getRadioTaken() {
		return radioTaken;
	}

	/**
	 * @param radioTaken the radioTaken to set
	 */
	public void setRadioTaken(String[] radioTaken) {
		this.radioTaken = radioTaken;
	}

	/**
	 * @return the radioDetails
	 */
	public String[] getRadioDetails() {
		return radioDetails;
	}

	/**
	 * @param radioDetails the radioDetails to set
	 */
	public void setRadioDetails(String[] radioDetails) {
		this.radioDetails = radioDetails;
	}

	/**
	 * @return the radioFirstCycleDate
	 */
	public String[] getRadioFirstCycleDate() {
		return radioFirstCycleDate;
	}

	/**
	 * @param radioFirstCycleDate the radioFirstCycleDate to set
	 */
	public void setRadioFirstCycleDate(String[] radioFirstCycleDate) {
		this.radioFirstCycleDate = radioFirstCycleDate;
	}

	/**
	 * @return the radioNoOfCycles
	 */
	public String[] getRadioNoOfCycles() {
		return radioNoOfCycles;
	}

	/**
	 * @param radioNoOfCycles the radioNoOfCycles to set
	 */
	public void setRadioNoOfCycles(String[] radioNoOfCycles) {
		this.radioNoOfCycles = radioNoOfCycles;
	}

	/**
	 * @return the radioAdvReactions
	 */
	public String[] getRadioAdvReactions() {
		return radioAdvReactions;
	}

	/**
	 * @param radioAdvReactions the radioAdvReactions to set
	 */
	public void setRadioAdvReactions(String[] radioAdvReactions) {
		this.radioAdvReactions = radioAdvReactions;
	}

	/**
	 * @return the biopsyFindingsDateArray
	 */
	public String[] getBiopsyFindingsDateArray() {
		return biopsyFindingsDateArray;
	}

	/**
	 * @param biopsyFindingsDateArray the biopsyFindingsDateArray to set
	 */
	public void setBiopsyFindingsDateArray(String[] biopsyFindingsDateArray) {
		this.biopsyFindingsDateArray = biopsyFindingsDateArray;
	}

	/**
	 * @return the biopsyFindingsArray
	 */
	public String[] getBiopsyFindingsArray() {
		return biopsyFindingsArray;
	}

	/**
	 * @param biopsyFindingsArray the biopsyFindingsArray to set
	 */
	public void setBiopsyFindingsArray(String[] biopsyFindingsArray) {
		this.biopsyFindingsArray = biopsyFindingsArray;
	}

	/**
	 * @return the pETFindingsDateArray
	 */
	public String[] getPETFindingsDateArray() {
		return PETFindingsDateArray;
	}

	/**
	 * @param pETFindingsDateArray the pETFindingsDateArray to set
	 */
	public void setPETFindingsDateArray(String[] pETFindingsDateArray) {
		PETFindingsDateArray = pETFindingsDateArray;
	}

	/**
	 * @return the pETFindingsArray
	 */
	public String[] getPETFindingsArray() {
		return PETFindingsArray;
	}

	/**
	 * @param pETFindingsArray the pETFindingsArray to set
	 */
	public void setPETFindingsArray(String[] pETFindingsArray) {
		PETFindingsArray = pETFindingsArray;
	}

	/**
	 * @return the cTFindingsDateArray
	 */
	public String[] getCTFindingsDateArray() {
		return CTFindingsDateArray;
	}

	/**
	 * @param cTFindingsDateArray the cTFindingsDateArray to set
	 */
	public void setCTFindingsDateArray(String[] cTFindingsDateArray) {
		CTFindingsDateArray = cTFindingsDateArray;
	}

	/**
	 * @return the cTFindingsArray
	 */
	public String[] getCTFindingsArray() {
		return CTFindingsArray;
	}

	/**
	 * @param cTFindingsArray the cTFindingsArray to set
	 */
	public void setCTFindingsArray(String[] cTFindingsArray) {
		CTFindingsArray = cTFindingsArray;
	}

	/**
	 * @return the otherInvstgDateArray
	 */
	public String[] getOtherInvstgDateArray() {
		return otherInvstgDateArray;
	}

	/**
	 * @param otherInvstgDateArray the otherInvstgDateArray to set
	 */
	public void setOtherInvstgDateArray(String[] otherInvstgDateArray) {
		this.otherInvstgDateArray = otherInvstgDateArray;
	}

	/**
	 * @return the otherInvstgArray
	 */
	public String[] getOtherInvstgArray() {
		return otherInvstgArray;
	}

	/**
	 * @param otherInvstgArray the otherInvstgArray to set
	 */
	public void setOtherInvstgArray(String[] otherInvstgArray) {
		this.otherInvstgArray = otherInvstgArray;
	}

	/**
	 * @return the tumorMarkerDateArray
	 */
	public String[] getTumorMarkerDateArray() {
		return tumorMarkerDateArray;
	}

	/**
	 * @param tumorMarkerDateArray the tumorMarkerDateArray to set
	 */
	public void setTumorMarkerDateArray(String[] tumorMarkerDateArray) {
		this.tumorMarkerDateArray = tumorMarkerDateArray;
	}

	/**
	 * @return the tumorMarkerArray
	 */
	public String[] getTumorMarkerArray() {
		return tumorMarkerArray;
	}

	/**
	 * @param tumorMarkerArray the tumorMarkerArray to set
	 */
	public void setTumorMarkerArray(String[] tumorMarkerArray) {
		this.tumorMarkerArray = tumorMarkerArray;
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
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * @return the liverProfilePanel
	 */
	public String getLiverProfilePanel() {
		return liverProfilePanel;
	}

	/**
	 * @param liverProfilePanel the liverProfilePanel to set
	 */
	public void setLiverProfilePanel(String liverProfilePanel) {
		this.liverProfilePanel = liverProfilePanel;
	}

	/**
	 * @return the liverProfileTest
	 */
	public String[] getLiverProfileTest() {
		return liverProfileTest;
	}

	/**
	 * @param liverProfileTest the liverProfileTest to set
	 */
	public void setLiverProfileTest(String[] liverProfileTest) {
		this.liverProfileTest = liverProfileTest;
	}

	/**
	 * @return the liverProfileNormalRange
	 */
	public String[] getLiverProfileNormalRange() {
		return liverProfileNormalRange;
	}

	/**
	 * @param liverProfileNormalRange the liverProfileNormalRange to set
	 */
	public void setLiverProfileNormalRange(String[] liverProfileNormalRange) {
		this.liverProfileNormalRange = liverProfileNormalRange;
	}

	/**
	 * @return the liverProfileValues
	 */
	public String[] getLiverProfileValues() {
		return liverProfileValues;
	}

	/**
	 * @param liverProfileValues the liverProfileValues to set
	 */
	public void setLiverProfileValues(String[] liverProfileValues) {
		this.liverProfileValues = liverProfileValues;
	}

	/**
	 * @return the renalProfilePanel
	 */
	public String getRenalProfilePanel() {
		return renalProfilePanel;
	}

	/**
	 * @param renalProfilePanel the renalProfilePanel to set
	 */
	public void setRenalProfilePanel(String renalProfilePanel) {
		this.renalProfilePanel = renalProfilePanel;
	}

	/**
	 * @return the renalProfileTest
	 */
	public String[] getRenalProfileTest() {
		return renalProfileTest;
	}

	/**
	 * @param renalProfileTest the renalProfileTest to set
	 */
	public void setRenalProfileTest(String[] renalProfileTest) {
		this.renalProfileTest = renalProfileTest;
	}

	/**
	 * @return the renalProfileNormalRange
	 */
	public String[] getRenalProfileNormalRange() {
		return renalProfileNormalRange;
	}

	/**
	 * @param renalProfileNormalRange the renalProfileNormalRange to set
	 */
	public void setRenalProfileNormalRange(String[] renalProfileNormalRange) {
		this.renalProfileNormalRange = renalProfileNormalRange;
	}

	/**
	 * @return the renalProfileValues
	 */
	public String[] getRenalProfileValues() {
		return renalProfileValues;
	}

	/**
	 * @param renalProfileValues the renalProfileValues to set
	 */
	public void setRenalProfileValues(String[] renalProfileValues) {
		this.renalProfileValues = renalProfileValues;
	}

	/**
	 * @return the cBCPanel
	 */
	public String getCBCPanel() {
		return CBCPanel;
	}

	/**
	 * @param cBCPanel the cBCPanel to set
	 */
	public void setCBCPanel(String cBCPanel) {
		CBCPanel = cBCPanel;
	}

	/**
	 * @return the cBCTest
	 */
	public String[] getCBCTest() {
		return CBCTest;
	}

	/**
	 * @param cBCTest the cBCTest to set
	 */
	public void setCBCTest(String[] cBCTest) {
		CBCTest = cBCTest;
	}

	/**
	 * @return the cBCNormalRange
	 */
	public String[] getCBCNormalRange() {
		return CBCNormalRange;
	}

	/**
	 * @param cBCNormalRange the cBCNormalRange to set
	 */
	public void setCBCNormalRange(String[] cBCNormalRange) {
		CBCNormalRange = cBCNormalRange;
	}

	/**
	 * @return the cBCValues
	 */
	public String[] getCBCValues() {
		return CBCValues;
	}

	/**
	 * @param cBCValues the cBCValues to set
	 */
	public void setCBCValues(String[] cBCValues) {
		CBCValues = cBCValues;
	}

	/**
	 * @return the vitalSignsWeight
	 */
	public String[] getVitalSignsWeight() {
		return vitalSignsWeight;
	}

	/**
	 * @param vitalSignsWeight the vitalSignsWeight to set
	 */
	public void setVitalSignsWeight(String[] vitalSignsWeight) {
		this.vitalSignsWeight = vitalSignsWeight;
	}

	/**
	 * @return the panel
	 */
	public String[] getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(String[] panel) {
		this.panel = panel;
	}

	/**
	 * @return the test
	 */
	public String[] getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String[] test) {
		this.test = test;
	}

	/**
	 * @return the qualitativeValue
	 */
	public String[] getQualitativeValue() {
		return qualitativeValue;
	}

	/**
	 * @param qualitativeValue the qualitativeValue to set
	 */
	public void setQualitativeValue(String[] qualitativeValue) {
		this.qualitativeValue = qualitativeValue;
	}

	/**
	 * @return the quantitativeValue
	 */
	public String[] getQuantitativeValue() {
		return quantitativeValue;
	}

	/**
	 * @param quantitativeValue the quantitativeValue to set
	 */
	public void setQuantitativeValue(String[] quantitativeValue) {
		this.quantitativeValue = quantitativeValue;
	}

	/**
	 * @return the normalRange
	 */
	public String[] getNormalRange() {
		return normalRange;
	}

	/**
	 * @param normalRange the normalRange to set
	 */
	public void setNormalRange(String[] normalRange) {
		this.normalRange = normalRange;
	}

	/**
	 * @return the symptom
	 */
	public String[] getSymptom() {
		return symptom;
	}

	/**
	 * @param symptom the symptom to set
	 */
	public void setSymptom(String[] symptom) {
		this.symptom = symptom;
	}

	/**
	 * @return the symptomCode
	 */
	public String[] getSymptomCode() {
		return symptomCode;
	}

	/**
	 * @param symptomCode the symptomCode to set
	 */
	public void setSymptomCode(String[] symptomCode) {
		this.symptomCode = symptomCode;
	}

	/**
	 * @return the symptomValue
	 */
	public String[] getSymptomValue() {
		return symptomValue;
	}

	/**
	 * @param symptomValue the symptomValue to set
	 */
	public void setSymptomValue(String[] symptomValue) {
		this.symptomValue = symptomValue;
	}

	/**
	 * @return the otherSymptom
	 */
	public String[] getOtherSymptom() {
		return otherSymptom;
	}

	/**
	 * @param otherSymptom the otherSymptom to set
	 */
	public void setOtherSymptom(String[] otherSymptom) {
		this.otherSymptom = otherSymptom;
	}

	/**
	 * @return the tumorMarkerDate
	 */
	public String[] getTumorMarkerDate() {
		return tumorMarkerDate;
	}

	/**
	 * @param tumorMarkerDate the tumorMarkerDate to set
	 */
	public void setTumorMarkerDate(String[] tumorMarkerDate) {
		this.tumorMarkerDate = tumorMarkerDate;
	}

	/**
	 * @return the tumorMarker
	 */
	public String[] getTumorMarker() {
		return tumorMarker;
	}

	/**
	 * @param tumorMarker the tumorMarker to set
	 */
	public void setTumorMarker(String[] tumorMarker) {
		this.tumorMarker = tumorMarker;
	}

	/**
	 * @return the tumorMarkerObservedValue
	 */
	public String[] getTumorMarkerObservedValue() {
		return tumorMarkerObservedValue;
	}

	/**
	 * @param tumorMarkerObservedValue the tumorMarkerObservedValue to set
	 */
	public void setTumorMarkerObservedValue(String[] tumorMarkerObservedValue) {
		this.tumorMarkerObservedValue = tumorMarkerObservedValue;
	}

	/**
	 * @return the tumorMarkerNormalValue
	 */
	public String[] getTumorMarkerNormalValue() {
		return tumorMarkerNormalValue;
	}

	/**
	 * @param tumorMarkerNormalValue the tumorMarkerNormalValue to set
	 */
	public void setTumorMarkerNormalValue(String[] tumorMarkerNormalValue) {
		this.tumorMarkerNormalValue = tumorMarkerNormalValue;
	}

	/**
	 * @return the tumorEventType
	 */
	public String[] getTumorEventType() {
		return tumorEventType;
	}

	/**
	 * @param tumorEventType the tumorEventType to set
	 */
	public void setTumorEventType(String[] tumorEventType) {
		this.tumorEventType = tumorEventType;
	}

	/**
	 * @return the investigationDetails
	 */
	public String[] getInvestigationDetails() {
		return investigationDetails;
	}

	/**
	 * @param investigationDetails the investigationDetails to set
	 */
	public void setInvestigationDetails(String[] investigationDetails) {
		this.investigationDetails = investigationDetails;
	}

	/**
	 * @return the tumorStages
	 */
	public String[] getTumorStages() {
		return tumorStages;
	}

	/**
	 * @param tumorStages the tumorStages to set
	 */
	public void setTumorStages(String[] tumorStages) {
		this.tumorStages = tumorStages;
	}

	/**
	 * @return the tumorSite
	 */
	public String[] getTumorSite() {
		return tumorSite;
	}

	/**
	 * @param tumorSite the tumorSite to set
	 */
	public void setTumorSite(String[] tumorSite) {
		this.tumorSite = tumorSite;
	}

	/**
	 * @return the tumorDimension
	 */
	public String[] getTumorDimension() {
		return tumorDimension;
	}

	/**
	 * @param tumorDimension the tumorDimension to set
	 */
	public void setTumorDimension(String[] tumorDimension) {
		this.tumorDimension = tumorDimension;
	}

	/**
	 * @return the investigationDate
	 */
	public String[] getInvestigationDate() {
		return investigationDate;
	}

	/**
	 * @param investigationDate the investigationDate to set
	 */
	public void setInvestigationDate(String[] investigationDate) {
		this.investigationDate = investigationDate;
	}

	/**
	 * @return the chemoReceived
	 */
	public String[] getChemoReceived() {
		return chemoReceived;
	}

	/**
	 * @param chemoReceived the chemoReceived to set
	 */
	public void setChemoReceived(String[] chemoReceived) {
		this.chemoReceived = chemoReceived;
	}

	/**
	 * @return the chemoRecvdAgentName
	 */
	public String[] getChemoRecvdAgentName() {
		return chemoRecvdAgentName;
	}

	/**
	 * @param chemoRecvdAgentName the chemoRecvdAgentName to set
	 */
	public void setChemoRecvdAgentName(String[] chemoRecvdAgentName) {
		this.chemoRecvdAgentName = chemoRecvdAgentName;
	}

	/**
	 * @return the chemoRecvdStartDate
	 */
	public String[] getChemoRecvdStartDate() {
		return chemoRecvdStartDate;
	}

	/**
	 * @param chemoRecvdStartDate the chemoRecvdStartDate to set
	 */
	public void setChemoRecvdStartDate(String[] chemoRecvdStartDate) {
		this.chemoRecvdStartDate = chemoRecvdStartDate;
	}

	/**
	 * @return the chemoRecvdEndDate
	 */
	public String[] getChemoRecvdEndDate() {
		return chemoRecvdEndDate;
	}

	/**
	 * @param chemoRecvdEndDate the chemoRecvdEndDate to set
	 */
	public void setChemoRecvdEndDate(String[] chemoRecvdEndDate) {
		this.chemoRecvdEndDate = chemoRecvdEndDate;
	}

	/**
	 * @return the chemoRecvdNoOfCycle
	 */
	public String[] getChemoRecvdNoOfCycle() {
		return chemoRecvdNoOfCycle;
	}

	/**
	 * @param chemoRecvdNoOfCycle the chemoRecvdNoOfCycle to set
	 */
	public void setChemoRecvdNoOfCycle(String[] chemoRecvdNoOfCycle) {
		this.chemoRecvdNoOfCycle = chemoRecvdNoOfCycle;
	}

	/**
	 * @return the chemoRecvdSymptoms
	 */
	public String[] getChemoRecvdSymptoms() {
		return chemoRecvdSymptoms;
	}

	/**
	 * @param chemoRecvdSymptoms the chemoRecvdSymptoms to set
	 */
	public void setChemoRecvdSymptoms(String[] chemoRecvdSymptoms) {
		this.chemoRecvdSymptoms = chemoRecvdSymptoms;
	}

	/**
	 * @return the chemoAgent
	 */
	public String[] getChemoAgent() {
		return chemoAgent;
	}

	/**
	 * @param chemoAgent the chemoAgent to set
	 */
	public void setChemoAgent(String[] chemoAgent) {
		this.chemoAgent = chemoAgent;
	}

	/**
	 * @return the chemoDose
	 */
	public String[] getChemoDose() {
		return chemoDose;
	}

	/**
	 * @param chemoDose the chemoDose to set
	 */
	public void setChemoDose(String[] chemoDose) {
		this.chemoDose = chemoDose;
	}

	/**
	 * @return the chemoCycle
	 */
	public String[] getChemoCycle() {
		return chemoCycle;
	}

	/**
	 * @param chemoCycle the chemoCycle to set
	 */
	public void setChemoCycle(String[] chemoCycle) {
		this.chemoCycle = chemoCycle;
	}

	/**
	 * @return the dateOfChemo
	 */
	public String[] getDateOfChemo() {
		return dateOfChemo;
	}

	/**
	 * @param dateOfChemo the dateOfChemo to set
	 */
	public void setDateOfChemo(String[] dateOfChemo) {
		this.dateOfChemo = dateOfChemo;
	}

	/**
	 * @return the treatmentType
	 */
	public String[] getTreatmentType() {
		return treatmentType;
	}

	/**
	 * @param treatmentType the treatmentType to set
	 */
	public void setTreatmentType(String[] treatmentType) {
		this.treatmentType = treatmentType;
	}

	/**
	 * @return the chemoAdverseEvent
	 */
	public String[] getChemoAdverseEvent() {
		return chemoAdverseEvent;
	}

	/**
	 * @param chemoAdverseEvent the chemoAdverseEvent to set
	 */
	public void setChemoAdverseEvent(String[] chemoAdverseEvent) {
		this.chemoAdverseEvent = chemoAdverseEvent;
	}

	/**
	 * @return the clinicalEvent
	 */
	public String getClinicalEvent() {
		return clinicalEvent;
	}

	/**
	 * @param clinicalEvent the clinicalEvent to set
	 */
	public void setClinicalEvent(String clinicalEvent) {
		this.clinicalEvent = clinicalEvent;
	}

	/**
	 * @return the physicalScore
	 */
	public double getPhysicalScore() {
		return physicalScore;
	}

	/**
	 * @param physicalScore the physicalScore to set
	 */
	public void setPhysicalScore(double physicalScore) {
		this.physicalScore = physicalScore;
	}

	/**
	 * @return the socialScore
	 */
	public double getSocialScore() {
		return socialScore;
	}

	/**
	 * @param socialScore the socialScore to set
	 */
	public void setSocialScore(double socialScore) {
		this.socialScore = socialScore;
	}

	/**
	 * @return the emotionalScore
	 */
	public double getEmotionalScore() {
		return emotionalScore;
	}

	/**
	 * @param emotionalScore the emotionalScore to set
	 */
	public void setEmotionalScore(double emotionalScore) {
		this.emotionalScore = emotionalScore;
	}

	/**
	 * @return the functionalScore
	 */
	public double getFunctionalScore() {
		return functionalScore;
	}

	/**
	 * @param functionalScore the functionalScore to set
	 */
	public void setFunctionalScore(double functionalScore) {
		this.functionalScore = functionalScore;
	}

	/**
	 * @return the totalScore
	 */
	public double getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the ecogStatus
	 */
	public String getEcogStatus() {
		return ecogStatus;
	}

	/**
	 * @param ecogStatus the ecogStatus to set
	 */
	public void setEcogStatus(String ecogStatus) {
		this.ecogStatus = ecogStatus;
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
	 * @return the consentGender
	 */
	public String getConsentGender() {
		return consentGender;
	}

	/**
	 * @param consentGender the consentGender to set
	 */
	public void setConsentGender(String consentGender) {
		this.consentGender = consentGender;
	}

	/**
	 * @return the consentAge
	 */
	public int getConsentAge() {
		return consentAge;
	}

	/**
	 * @param consentAge the consentAge to set
	 */
	public void setConsentAge(int consentAge) {
		this.consentAge = consentAge;
	}

	/**
	 * @return the consentMobile
	 */
	public String getConsentMobile() {
		return consentMobile;
	}

	/**
	 * @param consentMobile the consentMobile to set
	 */
	public void setConsentMobile(String consentMobile) {
		this.consentMobile = consentMobile;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the appetite
	 */
	public String getAppetite() {
		return appetite;
	}

	/**
	 * @param appetite the appetite to set
	 */
	public void setAppetite(String appetite) {
		this.appetite = appetite;
	}

	/**
	 * @return the sleep
	 */
	public String getSleep() {
		return sleep;
	}

	/**
	 * @param sleep the sleep to set
	 */
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}

	/**
	 * @return the bowels
	 */
	public String getBowels() {
		return bowels;
	}

	/**
	 * @param bowels the bowels to set
	 */
	public void setBowels(String bowels) {
		this.bowels = bowels;
	}

	/**
	 * @return the mood
	 */
	public String getMood() {
		return mood;
	}

	/**
	 * @param mood the mood to set
	 */
	public void setMood(String mood) {
		this.mood = mood;
	}

	/**
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}

	/**
	 * @return the nodesInvolved
	 */
	public String getNodesInvolved() {
		return nodesInvolved;
	}

	/**
	 * @param nodesInvolved the nodesInvolved to set
	 */
	public void setNodesInvolved(String nodesInvolved) {
		this.nodesInvolved = nodesInvolved;
	}

	/**
	 * @return the metastasis
	 */
	public String getMetastasis() {
		return metastasis;
	}

	/**
	 * @param metastasis the metastasis to set
	 */
	public void setMetastasis(String metastasis) {
		this.metastasis = metastasis;
	}

	/**
	 * @return the tnmStaging
	 */
	public String getTnmStaging() {
		return tnmStaging;
	}

	/**
	 * @param tnmStaging the tnmStaging to set
	 */
	public void setTnmStaging(String tnmStaging) {
		this.tnmStaging = tnmStaging;
	}

	/**
	 * @return the dateOfPrimaryDiagnosis
	 */
	public String getDateOfPrimaryDiagnosis() {
		return dateOfPrimaryDiagnosis;
	}

	/**
	 * @param dateOfPrimaryDiagnosis the dateOfPrimaryDiagnosis to set
	 */
	public void setDateOfPrimaryDiagnosis(String dateOfPrimaryDiagnosis) {
		this.dateOfPrimaryDiagnosis = dateOfPrimaryDiagnosis;
	}

	/**
	 * @return the dateOfMetastasisDiagnosis
	 */
	public String getDateOfMetastasisDiagnosis() {
		return dateOfMetastasisDiagnosis;
	}

	/**
	 * @param dateOfMetastasisDiagnosis the dateOfMetastasisDiagnosis to set
	 */
	public void setDateOfMetastasisDiagnosis(String dateOfMetastasisDiagnosis) {
		this.dateOfMetastasisDiagnosis = dateOfMetastasisDiagnosis;
	}

	/**
	 * @return the biopsyFindingsDate
	 */
	public String getBiopsyFindingsDate() {
		return biopsyFindingsDate;
	}

	/**
	 * @param biopsyFindingsDate the biopsyFindingsDate to set
	 */
	public void setBiopsyFindingsDate(String biopsyFindingsDate) {
		this.biopsyFindingsDate = biopsyFindingsDate;
	}

	/**
	 * @return the biopsyFindings
	 */
	public String getBiopsyFindings() {
		return biopsyFindings;
	}

	/**
	 * @param biopsyFindings the biopsyFindings to set
	 */
	public void setBiopsyFindings(String biopsyFindings) {
		this.biopsyFindings = biopsyFindings;
	}

	/**
	 * @return the pETFindingsDate
	 */
	public String getPETFindingsDate() {
		return PETFindingsDate;
	}

	/**
	 * @param pETFindingsDate the pETFindingsDate to set
	 */
	public void setPETFindingsDate(String pETFindingsDate) {
		PETFindingsDate = pETFindingsDate;
	}

	/**
	 * @return the pETFindings
	 */
	public String getPETFindings() {
		return PETFindings;
	}

	/**
	 * @param pETFindings the pETFindings to set
	 */
	public void setPETFindings(String pETFindings) {
		PETFindings = pETFindings;
	}

	/**
	 * @return the cTMRIFindingsDate
	 */
	public String getCTMRIFindingsDate() {
		return CTMRIFindingsDate;
	}

	/**
	 * @param cTMRIFindingsDate the cTMRIFindingsDate to set
	 */
	public void setCTMRIFindingsDate(String cTMRIFindingsDate) {
		CTMRIFindingsDate = cTMRIFindingsDate;
	}

	/**
	 * @return the cTMRIFindings
	 */
	public String getCTMRIFindings() {
		return CTMRIFindings;
	}

	/**
	 * @param cTMRIFindings the cTMRIFindings to set
	 */
	public void setCTMRIFindings(String cTMRIFindings) {
		CTMRIFindings = cTMRIFindings;
	}

	/**
	 * @return the otherInvestigationsDate
	 */
	public String getOtherInvestigationsDate() {
		return otherInvestigationsDate;
	}

	/**
	 * @param otherInvestigationsDate the otherInvestigationsDate to set
	 */
	public void setOtherInvestigationsDate(String otherInvestigationsDate) {
		this.otherInvestigationsDate = otherInvestigationsDate;
	}

	/**
	 * @return the otherInvestigations
	 */
	public String getOtherInvestigations() {
		return otherInvestigations;
	}

	/**
	 * @param otherInvestigations the otherInvestigations to set
	 */
	public void setOtherInvestigations(String otherInvestigations) {
		this.otherInvestigations = otherInvestigations;
	}

	/**
	 * @return the tumorMarkersDate
	 */
	public String getTumorMarkersDate() {
		return tumorMarkersDate;
	}

	/**
	 * @param tumorMarkersDate the tumorMarkersDate to set
	 */
	public void setTumorMarkersDate(String tumorMarkersDate) {
		this.tumorMarkersDate = tumorMarkersDate;
	}

	/**
	 * @return the tumorMarkers
	 */
	public String getTumorMarkers() {
		return tumorMarkers;
	}

	/**
	 * @param tumorMarkers the tumorMarkers to set
	 */
	public void setTumorMarkers(String tumorMarkers) {
		this.tumorMarkers = tumorMarkers;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the underwentSurgery
	 */
	public String[] getUnderwentSurgery() {
		return underwentSurgery;
	}

	/**
	 * @param underwentSurgery the underwentSurgery to set
	 */
	public void setUnderwentSurgery(String[] underwentSurgery) {
		this.underwentSurgery = underwentSurgery;
	}

	/**
	 * @return the underwentSurgeryDate
	 */
	public String[] getUnderwentSurgeryDate() {
		return underwentSurgeryDate;
	}

	/**
	 * @param underwentSurgeryDate the underwentSurgeryDate to set
	 */
	public void setUnderwentSurgeryDate(String[] underwentSurgeryDate) {
		this.underwentSurgeryDate = underwentSurgeryDate;
	}

	/**
	 * @return the surgeryName
	 */
	public String[] getSurgeryName() {
		return surgeryName;
	}

	/**
	 * @param surgeryName the surgeryName to set
	 */
	public void setSurgeryName(String[] surgeryName) {
		this.surgeryName = surgeryName;
	}

	/**
	 * @return the surgeryExcision
	 */
	public String[] getSurgeryExcision() {
		return surgeryExcision;
	}

	/**
	 * @param surgeryExcision the surgeryExcision to set
	 */
	public void setSurgeryExcision(String[] surgeryExcision) {
		this.surgeryExcision = surgeryExcision;
	}

	/**
	 * @return the marginsFreeOfTumor
	 */
	public String[] getMarginsFreeOfTumor() {
		return marginsFreeOfTumor;
	}

	/**
	 * @param marginsFreeOfTumor the marginsFreeOfTumor to set
	 */
	public void setMarginsFreeOfTumor(String[] marginsFreeOfTumor) {
		this.marginsFreeOfTumor = marginsFreeOfTumor;
	}

	/**
	 * @return the lymphNodesDissected
	 */
	public String[] getLymphNodesDissected() {
		return lymphNodesDissected;
	}

	/**
	 * @param lymphNodesDissected the lymphNodesDissected to set
	 */
	public void setLymphNodesDissected(String[] lymphNodesDissected) {
		this.lymphNodesDissected = lymphNodesDissected;
	}

	/**
	 * @return the lymphNodesNo
	 */
	public String[] getLymphNodesNo() {
		return lymphNodesNo;
	}

	/**
	 * @param lymphNodesNo the lymphNodesNo to set
	 */
	public void setLymphNodesNo(String[] lymphNodesNo) {
		this.lymphNodesNo = lymphNodesNo;
	}

	/**
	 * @return the positiveNodes
	 */
	public String[] getPositiveNodes() {
		return positiveNodes;
	}

	/**
	 * @param positiveNodes the positiveNodes to set
	 */
	public void setPositiveNodes(String[] positiveNodes) {
		this.positiveNodes = positiveNodes;
	}

	/**
	 * @return the conventionalTherapies
	 */
	public String getConventionalTherapies() {
		return conventionalTherapies;
	}

	/**
	 * @param conventionalTherapies the conventionalTherapies to set
	 */
	public void setConventionalTherapies(String conventionalTherapies) {
		this.conventionalTherapies = conventionalTherapies;
	}

	/**
	 * @return the alternativeTherapy
	 */
	public String getAlternativeTherapy() {
		return alternativeTherapy;
	}

	/**
	 * @param alternativeTherapy the alternativeTherapy to set
	 */
	public void setAlternativeTherapy(String alternativeTherapy) {
		this.alternativeTherapy = alternativeTherapy;
	}

	/**
	 * @return the alternativeTherapyDetails
	 */
	public String getAlternativeTherapyDetails() {
		return alternativeTherapyDetails;
	}

	/**
	 * @param alternativeTherapyDetails the alternativeTherapyDetails to set
	 */
	public void setAlternativeTherapyDetails(String alternativeTherapyDetails) {
		this.alternativeTherapyDetails = alternativeTherapyDetails;
	}

	/**
	 * @return the estimatedSurvival
	 */
	public String getEstimatedSurvival() {
		return estimatedSurvival;
	}

	/**
	 * @param estimatedSurvival the estimatedSurvival to set
	 */
	public void setEstimatedSurvival(String estimatedSurvival) {
		this.estimatedSurvival = estimatedSurvival;
	}

	/**
	 * @return the consentDate
	 */
	public String getConsentDate() {
		return consentDate;
	}

	/**
	 * @param consentDate the consentDate to set
	 */
	public void setConsentDate(String consentDate) {
		this.consentDate = consentDate;
	}

	/**
	 * @return the nameOfGuardian
	 */
	public String getNameOfGuardian() {
		return nameOfGuardian;
	}

	/**
	 * @param nameOfGuardian the nameOfGuardian to set
	 */
	public void setNameOfGuardian(String nameOfGuardian) {
		this.nameOfGuardian = nameOfGuardian;
	}

	/**
	 * @return the relationWithPatient
	 */
	public String getRelationWithPatient() {
		return relationWithPatient;
	}

	/**
	 * @param relationWithPatient the relationWithPatient to set
	 */
	public void setRelationWithPatient(String relationWithPatient) {
		this.relationWithPatient = relationWithPatient;
	}

	/**
	 * @return the clinicianName
	 */
	public String getClinicianName() {
		return clinicianName;
	}

	/**
	 * @param clinicianName the clinicianName to set
	 */
	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}

	/**
	 * @return the personalHistoryPurisha
	 */
	public String getPersonalHistoryPurisha() {
		return personalHistoryPurisha;
	}

	/**
	 * @param personalHistoryPurisha the personalHistoryPurisha to set
	 */
	public void setPersonalHistoryPurisha(String personalHistoryPurisha) {
		this.personalHistoryPurisha = personalHistoryPurisha;
	}

	/**
	 * @return the personalHistoryMootra
	 */
	public String getPersonalHistoryMootra() {
		return personalHistoryMootra;
	}

	/**
	 * @param personalHistoryMootra the personalHistoryMootra to set
	 */
	public void setPersonalHistoryMootra(String personalHistoryMootra) {
		this.personalHistoryMootra = personalHistoryMootra;
	}

	/**
	 * @return the personalHistoryKshudha
	 */
	public String getPersonalHistoryKshudha() {
		return personalHistoryKshudha;
	}

	/**
	 * @param personalHistoryKshudha the personalHistoryKshudha to set
	 */
	public void setPersonalHistoryKshudha(String personalHistoryKshudha) {
		this.personalHistoryKshudha = personalHistoryKshudha;
	}

	/**
	 * @return the personalHistoryNidra
	 */
	public String getPersonalHistoryNidra() {
		return personalHistoryNidra;
	}

	/**
	 * @param personalHistoryNidra the personalHistoryNidra to set
	 */
	public void setPersonalHistoryNidra(String personalHistoryNidra) {
		this.personalHistoryNidra = personalHistoryNidra;
	}

	/**
	 * @return the personalHistoryOther
	 */
	public String getPersonalHistoryOther() {
		return personalHistoryOther;
	}

	/**
	 * @param personalHistoryOther the personalHistoryOther to set
	 */
	public void setPersonalHistoryOther(String personalHistoryOther) {
		this.personalHistoryOther = personalHistoryOther;
	}

	/**
	 * @return the shortHistoryDescription
	 */
	public String getShortHistoryDescription() {
		return shortHistoryDescription;
	}

	/**
	 * @param shortHistoryDescription the shortHistoryDescription to set
	 */
	public void setShortHistoryDescription(String shortHistoryDescription) {
		this.shortHistoryDescription = shortHistoryDescription;
	}

	/**
	 * @return the shortHistoryDiagnosis
	 */
	public String getShortHistoryDiagnosis() {
		return shortHistoryDiagnosis;
	}

	/**
	 * @param shortHistoryDiagnosis the shortHistoryDiagnosis to set
	 */
	public void setShortHistoryDiagnosis(String shortHistoryDiagnosis) {
		this.shortHistoryDiagnosis = shortHistoryDiagnosis;
	}

	/**
	 * @return the shortHistoryOtherDetails
	 */
	public String getShortHistoryOtherDetails() {
		return shortHistoryOtherDetails;
	}

	/**
	 * @param shortHistoryOtherDetails the shortHistoryOtherDetails to set
	 */
	public void setShortHistoryOtherDetails(String shortHistoryOtherDetails) {
		this.shortHistoryOtherDetails = shortHistoryOtherDetails;
	}

	/**
	 * @return the personalHistorySmoking
	 */
	public String getPersonalHistorySmoking() {
		return personalHistorySmoking;
	}

	/**
	 * @param personalHistorySmoking the personalHistorySmoking to set
	 */
	public void setPersonalHistorySmoking(String personalHistorySmoking) {
		this.personalHistorySmoking = personalHistorySmoking;
	}

	/**
	 * @return the personalHistorySmokingDetails
	 */
	public String getPersonalHistorySmokingDetails() {
		return personalHistorySmokingDetails;
	}

	/**
	 * @param personalHistorySmokingDetails the personalHistorySmokingDetails to set
	 */
	public void setPersonalHistorySmokingDetails(String personalHistorySmokingDetails) {
		this.personalHistorySmokingDetails = personalHistorySmokingDetails;
	}

	/**
	 * @return the personalHistoryDiet
	 */
	public String getPersonalHistoryDiet() {
		return personalHistoryDiet;
	}

	/**
	 * @param personalHistoryDiet the personalHistoryDiet to set
	 */
	public void setPersonalHistoryDiet(String personalHistoryDiet) {
		this.personalHistoryDiet = personalHistoryDiet;
	}

	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @return the sourceContact
	 */
	public String getSourceContact() {
		return sourceContact;
	}

	/**
	 * @param sourceContact the sourceContact to set
	 */
	public void setSourceContact(String sourceContact) {
		this.sourceContact = sourceContact;
	}

	/**
	 * @return the registrationNo
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}

	/**
	 * @param registrationNo the registrationNo to set
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
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
	 * @return the dobYear
	 */
	public String getDobYear() {
		return dobYear;
	}

	/**
	 * @param dobYear the dobYear to set
	 */
	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}

	/**
	 * @return the dobMonth
	 */
	public String getDobMonth() {
		return dobMonth;
	}

	/**
	 * @param dobMonth the dobMonth to set
	 */
	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}

	/**
	 * @return the dobDate
	 */
	public String getDobDate() {
		return dobDate;
	}

	/**
	 * @param dobDate the dobDate to set
	 */
	public void setDobDate(String dobDate) {
		this.dobDate = dobDate;
	}

	/**
	 * @return the medicalRegNo
	 */
	public String getMedicalRegNo() {
		return medicalRegNo;
	}

	/**
	 * @param medicalRegNo the medicalRegNo to set
	 */
	public void setMedicalRegNo(String medicalRegNo) {
		this.medicalRegNo = medicalRegNo;
	}

	/**
	 * @return the firstVisitDate
	 */
	public String getFirstVisitDate() {
		return firstVisitDate;
	}

	/**
	 * @param firstVisitDate the firstVisitDate to set
	 */
	public void setFirstVisitDate(String firstVisitDate) {
		this.firstVisitDate = firstVisitDate;
	}

	/**
	 * @return the cancerType
	 */
	public String getCancerType() {
		return cancerType;
	}

	/**
	 * @param cancerType the cancerType to set
	 */
	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	/**
	 * @return the sonDaughterOf
	 */
	public String getSonDaughterOf() {
		return sonDaughterOf;
	}

	/**
	 * @param sonDaughterOf the sonDaughterOf to set
	 */
	public void setSonDaughterOf(String sonDaughterOf) {
		this.sonDaughterOf = sonDaughterOf;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEC() {
		return EC;
	}

	public void setEC(String eC) {
		EC = eC;
	}

	/**
	 * @return the oDRt1
	 */
	public String getODRt1() {
		return ODRt1;
	}

	/**
	 * @param oDRt1 the oDRt1 to set
	 */
	public void setODRt1(String oDRt1) {
		ODRt1 = oDRt1;
	}

	/**
	 * @return the oSLt1
	 */
	public String getOSLt1() {
		return OSLt1;
	}

	/**
	 * @param oSLt1 the oSLt1 to set
	 */
	public void setOSLt1(String oSLt1) {
		OSLt1 = oSLt1;
	}

	/**
	 * @return the searchPatientName
	 */
	public String getSearchName() {
		return searchName;
	}

	/**
	 * @param searchPatientName the searchPatientName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	/**
	 * @return the fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	/**
	 * @param fileInputStream the fileInputStream to set
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the labReport
	 */
	public List<File> getLabReport() {
		return labReport;
	}

	/**
	 * @param labReport the labReport to set
	 */
	public void setLabReport(List<File> labReport) {
		this.labReport = labReport;
	}

	/**
	 * @return the labReportContentType
	 */
	public List<String> getLabReportContentType() {
		return labReportContentType;
	}

	/**
	 * @param labReportContentType the labReportContentType to set
	 */
	public void setLabReportContentType(List<String> labReportContentType) {
		this.labReportContentType = labReportContentType;
	}

	/**
	 * @return the labReportFileName
	 */
	public List<String> getLabReportFileName() {
		return labReportFileName;
	}

	/**
	 * @param labReportFileName the labReportFileName to set
	 */
	public void setLabReportFileName(List<String> labReportFileName) {
		this.labReportFileName = labReportFileName;
	}

	/**
	 * @return the visitFromTimeHH
	 */
	public String getVisitFromTimeHH() {
		return visitFromTimeHH;
	}

	/**
	 * @param visitFromTimeHH the visitFromTimeHH to set
	 */
	public void setVisitFromTimeHH(String visitFromTimeHH) {
		this.visitFromTimeHH = visitFromTimeHH;
	}

	/**
	 * @return the visitFromTimeAMPM
	 */
	public String getVisitFromTimeAMPM() {
		return visitFromTimeAMPM;
	}

	/**
	 * @param visitFromTimeAMPM the visitFromTimeAMPM to set
	 */
	public void setVisitFromTimeAMPM(String visitFromTimeAMPM) {
		this.visitFromTimeAMPM = visitFromTimeAMPM;
	}

	/**
	 * @return the visitToTimeAMPM
	 */
	public String getVisitToTimeAMPM() {
		return visitToTimeAMPM;
	}

	/**
	 * @param visitToTimeAMPM the visitToTimeAMPM to set
	 */
	public void setVisitToTimeAMPM(String visitToTimeAMPM) {
		this.visitToTimeAMPM = visitToTimeAMPM;
	}

	/**
	 * @return the visitToTimeHH
	 */
	public String getVisitToTimeHH() {
		return visitToTimeHH;
	}

	/**
	 * @param visitToTimeHH the visitToTimeHH to set
	 */
	public void setVisitToTimeHH(String visitToTimeHH) {
		this.visitToTimeHH = visitToTimeHH;
	}

	/**
	 * @return the visitToTimeMM
	 */
	public String getVisitToTimeMM() {
		return visitToTimeMM;
	}

	/**
	 * @param visitToTimeMM the visitToTimeMM to set
	 */
	public void setVisitToTimeMM(String visitToTimeMM) {
		this.visitToTimeMM = visitToTimeMM;
	}

	/**
	 * @return the visitFromTimeMM
	 */
	public String getVisitFromTimeMM() {
		return visitFromTimeMM;
	}

	/**
	 * @param visitFromTimeMM the visitFromTimeMM to set
	 */
	public void setVisitFromTimeMM(String visitFromTimeMM) {
		this.visitFromTimeMM = visitFromTimeMM;
	}

	/**
	 * @return the iPDFile
	 */
	public File getIPDFile() {
		return IPDFile;
	}

	/**
	 * @param iPDFile the iPDFile to set
	 */
	public void setIPDFile(File iPDFile) {
		IPDFile = iPDFile;
	}

	/**
	 * @return the consentDiagnosis
	 */
	public String getConsentDiagnosis() {
		return consentDiagnosis;
	}

	/**
	 * @param consentDiagnosis the consentDiagnosis to set
	 */
	public void setConsentDiagnosis(String consentDiagnosis) {
		this.consentDiagnosis = consentDiagnosis;
	}

	/**
	 * @return the noOfPills
	 */
	public int getNoOfPills() {
		return noOfPills;
	}

	/**
	 * @param noOfPills the noOfPills to set
	 */
	public void setNoOfPills(int noOfPills) {
		this.noOfPills = noOfPills;
	}

	/**
	 * @return the aptfromMMAMPM
	 */
	public String getAptfromMMAMPM() {
		return aptfromMMAMPM;
	}

	/**
	 * @param aptfromMMAMPM the aptfromMMAMPM to set
	 */
	public void setAptfromMMAMPM(String aptfromMMAMPM) {
		this.aptfromMMAMPM = aptfromMMAMPM;
	}

	/**
	 * @return the aptToMMAMPM
	 */
	public String getAptToMMAMPM() {
		return aptToMMAMPM;
	}

	/**
	 * @param aptToMMAMPM the aptToMMAMPM to set
	 */
	public void setAptToMMAMPM(String aptToMMAMPM) {
		this.aptToMMAMPM = aptToMMAMPM;
	}

	/**
	 * @return the aptStatus
	 */
	public String getAptStatus() {
		return aptStatus;
	}

	/**
	 * @param aptStatus the aptStatus to set
	 */
	public void setAptStatus(String aptStatus) {
		this.aptStatus = aptStatus;
	}

	/**
	 * @return the aptTimeFrom
	 */
	public String getAptTimeFrom() {
		return aptTimeFrom;
	}

	/**
	 * @param aptTimeFrom the aptTimeFrom to set
	 */
	public void setAptTimeFrom(String aptTimeFrom) {
		this.aptTimeFrom = aptTimeFrom;
	}

	/**
	 * @return the aptTimeTo
	 */
	public String getAptTimeTo() {
		return aptTimeTo;
	}

	/**
	 * @param aptTimeTo the aptTimeTo to set
	 */
	public void setAptTimeTo(String aptTimeTo) {
		this.aptTimeTo = aptTimeTo;
	}

	/**
	 * @return the appointmentDate
	 */
	public String getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * @param appointmentDate the appointmentDate to set
	 */
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	/**
	 * @return the aptFromHH
	 */
	public String getAptFromHH() {
		return aptFromHH;
	}

	/**
	 * @param aptFromHH the aptFromHH to set
	 */
	public void setAptFromHH(String aptFromHH) {
		this.aptFromHH = aptFromHH;
	}

	/**
	 * @return the aptFromMM
	 */
	public String getAptFromMM() {
		return aptFromMM;
	}

	/**
	 * @param aptFromMM the aptFromMM to set
	 */
	public void setAptFromMM(String aptFromMM) {
		this.aptFromMM = aptFromMM;
	}

	/**
	 * @return the aptToHH
	 */
	public String getAptToHH() {
		return aptToHH;
	}

	/**
	 * @param aptToHH the aptToHH to set
	 */
	public void setAptToHH(String aptToHH) {
		this.aptToHH = aptToHH;
	}

	/**
	 * @return the aptToMM
	 */
	public String getAptToMM() {
		return aptToMM;
	}

	/**
	 * @param aptToMM the aptToMM to set
	 */
	public void setAptToMM(String aptToMM) {
		this.aptToMM = aptToMM;
	}

	/**
	 * @return the aptNumber
	 */
	public int getAptNumber() {
		return aptNumber;
	}

	/**
	 * @param aptNumber the aptNumber to set
	 */
	public void setAptNumber(int aptNumber) {
		this.aptNumber = aptNumber;
	}

	/**
	 * @return the aptID
	 */
	public int getAptID() {
		return aptID;
	}

	/**
	 * @param aptID the aptID to set
	 */
	public void setAptID(int aptID) {
		this.aptID = aptID;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the consentText2DB
	 */
	public String getConsentText2DB() {
		return consentText2DB;
	}

	/**
	 * @param consentText2DB the consentText2DB to set
	 */
	public void setConsentText2DB(String consentText2DB) {
		this.consentText2DB = consentText2DB;
	}

	/**
	 * @return the consentText1DB
	 */
	public String getConsentText1DB() {
		return consentText1DB;
	}

	/**
	 * @param consentText1DB the consentText1DB to set
	 */
	public void setConsentText1DB(String consentText1DB) {
		this.consentText1DB = consentText1DB;
	}

	/**
	 * @return the consentTextDB
	 */
	public String getConsentTextDB() {
		return consentTextDB;
	}

	/**
	 * @param consentTextDB the consentTextDB to set
	 */
	public void setConsentTextDB(String consentTextDB) {
		this.consentTextDB = consentTextDB;
	}

	/**
	 * @return the consentFile
	 */
	public File getConsentFile() {
		return consentFile;
	}

	/**
	 * @param consentFile the consentFile to set
	 */
	public void setConsentFile(File consentFile) {
		this.consentFile = consentFile;
	}

	/**
	 * @return the consentFileFileName
	 */
	public String getConsentFileFileName() {
		return consentFileFileName;
	}

	/**
	 * @param consentFileFileName the consentFileFileName to set
	 */
	public void setConsentFileFileName(String consentFileFileName) {
		this.consentFileFileName = consentFileFileName;
	}

	/**
	 * @return the consentFileDBName
	 */
	public String getConsentFileDBName() {
		return consentFileDBName;
	}

	/**
	 * @param consentFileDBName the consentFileDBName to set
	 */
	public void setConsentFileDBName(String consentFileDBName) {
		this.consentFileDBName = consentFileDBName;
	}

	/**
	 * @return the consentText1
	 */
	public String getConsentText1() {
		return consentText1;
	}

	/**
	 * @param consentText1 the consentText1 to set
	 */
	public void setConsentText1(String consentText1) {
		this.consentText1 = consentText1;
	}

	/**
	 * @return the consentText
	 */
	public String getConsentText() {
		return consentText;
	}

	/**
	 * @param consentText the consentText to set
	 */
	public void setConsentText(String consentText) {
		this.consentText = consentText;
	}

	/**
	 * @return the consentText2
	 */
	public String getConsentText2() {
		return consentText2;
	}

	/**
	 * @param consentText2 the consentText2 to set
	 */
	public void setConsentText2(String consentText2) {
		this.consentText2 = consentText2;
	}

	/**
	 * @return the complaintID
	 */
	public int getComplaintID() {
		return complaintID;
	}

	/**
	 * @param complaintID the complaintID to set
	 */
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}

	/**
	 * @return the continuationSheetID
	 */
	public int getContinuationSheetID() {
		return continuationSheetID;
	}

	/**
	 * @param continuationSheetID the continuationSheetID to set
	 */
	public void setContinuationSheetID(int continuationSheetID) {
		this.continuationSheetID = continuationSheetID;
	}

	/**
	 * @return the oEPulse
	 */
	public int getOEPulse() {
		return OEPulse;
	}

	/**
	 * @param oEPulse the oEPulse to set
	 */
	public void setOEPulse(int oEPulse) {
		OEPulse = oEPulse;
	}

	/**
	 * @return the oEBPSys
	 */
	public int getOEBPSys() {
		return OEBPSys;
	}

	/**
	 * @param oEBPSys the oEBPSys to set
	 */
	public void setOEBPSys(int oEBPSys) {
		OEBPSys = oEBPSys;
	}

	/**
	 * @return the oEBPDia
	 */
	public int getOEBPDia() {
		return OEBPDia;
	}

	/**
	 * @param oEBPDia the oEBPDia to set
	 */
	public void setOEBPDia(int oEBPDia) {
		OEBPDia = oEBPDia;
	}

	/**
	 * @return the invertigationsHb
	 */
	public double getInvertigationsHb() {
		return invertigationsHb;
	}

	/**
	 * @param invertigationsHb the invertigationsHb to set
	 */
	public void setInvertigationsHb(double invertigationsHb) {
		this.invertigationsHb = invertigationsHb;
	}

	/**
	 * @return the invertigationsWBC
	 */
	public double getInvertigationsWBC() {
		return invertigationsWBC;
	}

	/**
	 * @param invertigationsWBC the invertigationsWBC to set
	 */
	public void setInvertigationsWBC(double invertigationsWBC) {
		this.invertigationsWBC = invertigationsWBC;
	}

	/**
	 * @return the invertigationsBT
	 */
	public double getInvertigationsBT() {
		return invertigationsBT;
	}

	/**
	 * @param invertigationsBT the invertigationsBT to set
	 */
	public void setInvertigationsBT(double invertigationsBT) {
		this.invertigationsBT = invertigationsBT;
	}

	/**
	 * @return the invertigationsCT
	 */
	public double getInvertigationsCT() {
		return invertigationsCT;
	}

	/**
	 * @param invertigationsCT the invertigationsCT to set
	 */
	public void setInvertigationsCT(double invertigationsCT) {
		this.invertigationsCT = invertigationsCT;
	}

	/**
	 * @return the invertigationsBSL
	 */
	public double getInvertigationsBSL() {
		return invertigationsBSL;
	}

	/**
	 * @param invertigationsBSL the invertigationsBSL to set
	 */
	public void setInvertigationsBSL(double invertigationsBSL) {
		this.invertigationsBSL = invertigationsBSL;
	}

	/**
	 * @return the invertigationsF
	 */
	public double getInvertigationsF() {
		return invertigationsF;
	}

	/**
	 * @param invertigationsF the invertigationsF to set
	 */
	public void setInvertigationsF(double invertigationsF) {
		this.invertigationsF = invertigationsF;
	}

	/**
	 * @return the invertigationsPP
	 */
	public double getInvertigationsPP() {
		return invertigationsPP;
	}

	/**
	 * @param invertigationsPP the invertigationsPP to set
	 */
	public void setInvertigationsPP(double invertigationsPP) {
		this.invertigationsPP = invertigationsPP;
	}

	/**
	 * @return the ho
	 */
	public String getHo() {
		return ho;
	}

	/**
	 * @param ho the ho to set
	 */
	public void setHo(String ho) {
		this.ho = ho;
	}

	/**
	 * @return the allergicTo
	 */
	public String getAllergicTo() {
		return allergicTo;
	}

	/**
	 * @param allergicTo the allergicTo to set
	 */
	public void setAllergicTo(String allergicTo) {
		this.allergicTo = allergicTo;
	}

	/**
	 * @return the co
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

	/**
	 * @return the oDRt
	 */
	public String[] getODRt() {
		return ODRt;
	}

	/**
	 * @param oDRt the oDRt to set
	 */
	public void setODRt(String[] oDRt) {
		ODRt = oDRt;
	}

	/**
	 * @return the oSLt
	 */
	public String[] getOSLt() {
		return OSLt;
	}

	/**
	 * @param oSLt the oSLt to set
	 */
	public void setOSLt(String[] oSLt) {
		OSLt = oSLt;
	}

	/**
	 * @return the oERS
	 */
	public String getOERS() {
		return OERS;
	}

	/**
	 * @param oERS the oERS to set
	 */
	public void setOERS(String oERS) {
		OERS = oERS;
	}

	/**
	 * @return the oECVS
	 */
	public String getOECVS() {
		return OECVS;
	}

	/**
	 * @param oECVS the oECVS to set
	 */
	public void setOECVS(String oECVS) {
		OECVS = oECVS;
	}

	/**
	 * @return the oEAdv
	 */
	public String getOEAdv() {
		return OEAdv;
	}

	/**
	 * @param oEAdv the oEAdv to set
	 */
	public void setOEAdv(String oEAdv) {
		OEAdv = oEAdv;
	}

	/**
	 * @return the invertigationsUrine
	 */
	public String getInvertigationsUrine() {
		return invertigationsUrine;
	}

	/**
	 * @param invertigationsUrine the invertigationsUrine to set
	 */
	public void setInvertigationsUrine(String invertigationsUrine) {
		this.invertigationsUrine = invertigationsUrine;
	}

	/**
	 * @return the countinuationSheetTeatment
	 */
	public String getCountinuationSheetTeatment() {
		return countinuationSheetTeatment;
	}

	/**
	 * @param countinuationSheetTeatment the countinuationSheetTeatment to set
	 */
	public void setCountinuationSheetTeatment(String countinuationSheetTeatment) {
		this.countinuationSheetTeatment = countinuationSheetTeatment;
	}

	/**
	 * @return the countinuationSheetDate
	 */
	public String getCountinuationSheetDate() {
		return countinuationSheetDate;
	}

	/**
	 * @param countinuationSheetDate the countinuationSheetDate to set
	 */
	public void setCountinuationSheetDate(String countinuationSheetDate) {
		this.countinuationSheetDate = countinuationSheetDate;
	}

	/**
	 * @return the countinuationSheetDescription
	 */
	public String getCountinuationSheetDescription() {
		return countinuationSheetDescription;
	}

	/**
	 * @param countinuationSheetDescription the countinuationSheetDescription to set
	 */
	public void setCountinuationSheetDescription(String countinuationSheetDescription) {
		this.countinuationSheetDescription = countinuationSheetDescription;
	}

	/**
	 * @return the oTNotes
	 */
	public String getOTNotes() {
		return OTNotes;
	}

	/**
	 * @param oTNotes the oTNotes to set
	 */
	public void setOTNotes(String oTNotes) {
		OTNotes = oTNotes;
	}

	/**
	 * @return the referralLetter
	 */
	public String getReferralLetter() {
		return referralLetter;
	}

	/**
	 * @return the referralLetterForPDF
	 */
	public String getReferralLetterForPDF() {
		return referralLetterForPDF;
	}

	/**
	 * @param referralLetter the referralLetter to set
	 */
	public void setReferralLetter(String referralLetter) {
		this.referralLetter = referralLetter;
	}

	/**
	 * @param referralLetterForPDF the referralLetterForPDF to set
	 */
	public void setReferralLetterForPDF(String referralLetterForPDF) {
		this.referralLetterForPDF = referralLetterForPDF;
	}

	/**
	 * @return the medicalCertiForPDF
	 */
	public String getMedicalCertiForPDF() {
		return medicalCertiForPDF;
	}

	/**
	 * @param medicalCertiForPDF the medicalCertiForPDF to set
	 */
	public void setMedicalCertiForPDF(String medicalCertiForPDF) {
		this.medicalCertiForPDF = medicalCertiForPDF;
	}

	/**
	 * @return the medicalCerti
	 */
	public String getMedicalCerti() {
		return medicalCerti;
	}

	/**
	 * @param medicalCerti the medicalCerti to set
	 */
	public void setMedicalCerti(String medicalCerti) {
		this.medicalCerti = medicalCerti;
	}

	/**
	 * @return the oldSphDiskOD
	 */
	public String getOldSphDiskOD() {
		return oldSphDiskOD;
	}

	/**
	 * @return the oldCylDiskOD
	 */
	public String getOldCylDiskOD() {
		return oldCylDiskOD;
	}

	/**
	 * @return the oldAxisDiskOD
	 */
	public String getOldAxisDiskOD() {
		return oldAxisDiskOD;
	}

	/**
	 * @return the oldVnDiskOD
	 */
	public String getOldVnDiskOD() {
		return oldVnDiskOD;
	}

	/**
	 * @return the oldSphDiskOS
	 */
	public String getOldSphDiskOS() {
		return oldSphDiskOS;
	}

	/**
	 * @return the oldCylDiskOS
	 */
	public String getOldCylDiskOS() {
		return oldCylDiskOS;
	}

	/**
	 * @return the oldAxisDiskOS
	 */
	public String getOldAxisDiskOS() {
		return oldAxisDiskOS;
	}

	/**
	 * @return the oldVnDiskOS
	 */
	public String getOldVnDiskOS() {
		return oldVnDiskOS;
	}

	/**
	 * @return the oldSphNearOD
	 */
	public String getOldSphNearOD() {
		return oldSphNearOD;
	}

	/**
	 * @return the oldCylNearOD
	 */
	public String getOldCylNearOD() {
		return oldCylNearOD;
	}

	/**
	 * @return the oldAxisNearOD
	 */
	public String getOldAxisNearOD() {
		return oldAxisNearOD;
	}

	/**
	 * @return the oldVnNearOD
	 */
	public String getOldVnNearOD() {
		return oldVnNearOD;
	}

	/**
	 * @return the oldSphNearOS
	 */
	public String getOldSphNearOS() {
		return oldSphNearOS;
	}

	/**
	 * @return the oldCylNearOS
	 */
	public String getOldCylNearOS() {
		return oldCylNearOS;
	}

	/**
	 * @return the oldAxisNearOS
	 */
	public String getOldAxisNearOS() {
		return oldAxisNearOS;
	}

	/**
	 * @return the oldVnNearOS
	 */
	public String getOldVnNearOS() {
		return oldVnNearOS;
	}

	/**
	 * @param oldSphDiskOD the oldSphDiskOD to set
	 */
	public void setOldSphDiskOD(String oldSphDiskOD) {
		this.oldSphDiskOD = oldSphDiskOD;
	}

	/**
	 * @param oldCylDiskOD the oldCylDiskOD to set
	 */
	public void setOldCylDiskOD(String oldCylDiskOD) {
		this.oldCylDiskOD = oldCylDiskOD;
	}

	/**
	 * @param oldAxisDiskOD the oldAxisDiskOD to set
	 */
	public void setOldAxisDiskOD(String oldAxisDiskOD) {
		this.oldAxisDiskOD = oldAxisDiskOD;
	}

	/**
	 * @param oldVnDiskOD the oldVnDiskOD to set
	 */
	public void setOldVnDiskOD(String oldVnDiskOD) {
		this.oldVnDiskOD = oldVnDiskOD;
	}

	/**
	 * @param oldSphDiskOS the oldSphDiskOS to set
	 */
	public void setOldSphDiskOS(String oldSphDiskOS) {
		this.oldSphDiskOS = oldSphDiskOS;
	}

	/**
	 * @param oldCylDiskOS the oldCylDiskOS to set
	 */
	public void setOldCylDiskOS(String oldCylDiskOS) {
		this.oldCylDiskOS = oldCylDiskOS;
	}

	/**
	 * @param oldAxisDiskOS the oldAxisDiskOS to set
	 */
	public void setOldAxisDiskOS(String oldAxisDiskOS) {
		this.oldAxisDiskOS = oldAxisDiskOS;
	}

	/**
	 * @param oldVnDiskOS the oldVnDiskOS to set
	 */
	public void setOldVnDiskOS(String oldVnDiskOS) {
		this.oldVnDiskOS = oldVnDiskOS;
	}

	/**
	 * @param oldSphNearOD the oldSphNearOD to set
	 */
	public void setOldSphNearOD(String oldSphNearOD) {
		this.oldSphNearOD = oldSphNearOD;
	}

	/**
	 * @param oldCylNearOD the oldCylNearOD to set
	 */
	public void setOldCylNearOD(String oldCylNearOD) {
		this.oldCylNearOD = oldCylNearOD;
	}

	/**
	 * @param oldAxisNearOD the oldAxisNearOD to set
	 */
	public void setOldAxisNearOD(String oldAxisNearOD) {
		this.oldAxisNearOD = oldAxisNearOD;
	}

	/**
	 * @param oldVnNearOD the oldVnNearOD to set
	 */
	public void setOldVnNearOD(String oldVnNearOD) {
		this.oldVnNearOD = oldVnNearOD;
	}

	/**
	 * @param oldSphNearOS the oldSphNearOS to set
	 */
	public void setOldSphNearOS(String oldSphNearOS) {
		this.oldSphNearOS = oldSphNearOS;
	}

	/**
	 * @param oldCylNearOS the oldCylNearOS to set
	 */
	public void setOldCylNearOS(String oldCylNearOS) {
		this.oldCylNearOS = oldCylNearOS;
	}

	/**
	 * @param oldAxisNearOS the oldAxisNearOS to set
	 */
	public void setOldAxisNearOS(String oldAxisNearOS) {
		this.oldAxisNearOS = oldAxisNearOS;
	}

	/**
	 * @param oldVnNearOS the oldVnNearOS to set
	 */
	public void setOldVnNearOS(String oldVnNearOS) {
		this.oldVnNearOS = oldVnNearOS;
	}

	/**
	 * @return the visualAcuityDistOD
	 */
	public String getVisualAcuityDistOD() {
		return visualAcuityDistOD;
	}

	/**
	 * @return the visualAcuityNearOD
	 */
	public String getVisualAcuityNearOD() {
		return visualAcuityNearOD;
	}

	/**
	 * @return the visualAcuityDistOS
	 */
	public String getVisualAcuityDistOS() {
		return visualAcuityDistOS;
	}

	/**
	 * @return the visualAcuityNearOS
	 */
	public String getVisualAcuityNearOS() {
		return visualAcuityNearOS;
	}

	/**
	 * @return the pinholeVisionDistOD
	 */
	public String getPinholeVisionDistOD() {
		return pinholeVisionDistOD;
	}

	/**
	 * @return the pinholeVisionNearOD
	 */
	public String getPinholeVisionNearOD() {
		return pinholeVisionNearOD;
	}

	/**
	 * @return the pinholeVisionDistOS
	 */
	public String getPinholeVisionDistOS() {
		return pinholeVisionDistOS;
	}

	/**
	 * @return the pinholeVisionNearOS
	 */
	public String getPinholeVisionNearOS() {
		return pinholeVisionNearOS;
	}

	/**
	 * @return the bCVADistOD
	 */
	public String getBCVADistOD() {
		return BCVADistOD;
	}

	/**
	 * @return the bCVANearOD
	 */
	public String getBCVANearOD() {
		return BCVANearOD;
	}

	/**
	 * @return the bCVADistOS
	 */
	public String getBCVADistOS() {
		return BCVADistOS;
	}

	/**
	 * @return the bCVANearOS
	 */
	public String getBCVANearOS() {
		return BCVANearOS;
	}

	/**
	 * @param visualAcuityDistOD the visualAcuityDistOD to set
	 */
	public void setVisualAcuityDistOD(String visualAcuityDistOD) {
		this.visualAcuityDistOD = visualAcuityDistOD;
	}

	/**
	 * @param visualAcuityNearOD the visualAcuityNearOD to set
	 */
	public void setVisualAcuityNearOD(String visualAcuityNearOD) {
		this.visualAcuityNearOD = visualAcuityNearOD;
	}

	/**
	 * @param visualAcuityDistOS the visualAcuityDistOS to set
	 */
	public void setVisualAcuityDistOS(String visualAcuityDistOS) {
		this.visualAcuityDistOS = visualAcuityDistOS;
	}

	/**
	 * @param visualAcuityNearOS the visualAcuityNearOS to set
	 */
	public void setVisualAcuityNearOS(String visualAcuityNearOS) {
		this.visualAcuityNearOS = visualAcuityNearOS;
	}

	/**
	 * @param pinholeVisionDistOD the pinholeVisionDistOD to set
	 */
	public void setPinholeVisionDistOD(String pinholeVisionDistOD) {
		this.pinholeVisionDistOD = pinholeVisionDistOD;
	}

	/**
	 * @param pinholeVisionNearOD the pinholeVisionNearOD to set
	 */
	public void setPinholeVisionNearOD(String pinholeVisionNearOD) {
		this.pinholeVisionNearOD = pinholeVisionNearOD;
	}

	/**
	 * @param pinholeVisionDistOS the pinholeVisionDistOS to set
	 */
	public void setPinholeVisionDistOS(String pinholeVisionDistOS) {
		this.pinholeVisionDistOS = pinholeVisionDistOS;
	}

	/**
	 * @param pinholeVisionNearOS the pinholeVisionNearOS to set
	 */
	public void setPinholeVisionNearOS(String pinholeVisionNearOS) {
		this.pinholeVisionNearOS = pinholeVisionNearOS;
	}

	/**
	 * @param bCVADistOD the bCVADistOD to set
	 */
	public void setBCVADistOD(String bCVADistOD) {
		BCVADistOD = bCVADistOD;
	}

	/**
	 * @param bCVANearOD the bCVANearOD to set
	 */
	public void setBCVANearOD(String bCVANearOD) {
		BCVANearOD = bCVANearOD;
	}

	/**
	 * @param bCVADistOS the bCVADistOS to set
	 */
	public void setBCVADistOS(String bCVADistOS) {
		BCVADistOS = bCVADistOS;
	}

	/**
	 * @param bCVANearOS the bCVANearOS to set
	 */
	public void setBCVANearOS(String bCVANearOS) {
		BCVANearOS = bCVANearOS;
	}

	/**
	 * @return the lastVisitID
	 */
	public int getLastVisitID() {
		return lastVisitID;
	}

	/**
	 * @param lastVisitID the lastVisitID to set
	 */
	public void setLastVisitID(int lastVisitID) {
		this.lastVisitID = lastVisitID;
	}

	/**
	 * @return the opticinID
	 */
	public int getOpticinID() {
		return opticinID;
	}

	/**
	 * @param opticinID the opticinID to set
	 */
	public void setOpticinID(int opticinID) {
		this.opticinID = opticinID;
	}

	/**
	 * @return the sphDiskOD
	 */
	public String getSphDiskOD() {
		return sphDiskOD;
	}

	/**
	 * @return the cylDiskOD
	 */
	public String getCylDiskOD() {
		return cylDiskOD;
	}

	/**
	 * @return the axisDiskOD
	 */
	public String getAxisDiskOD() {
		return axisDiskOD;
	}

	/**
	 * @return the vnDiskOD
	 */
	public String getVnDiskOD() {
		return vnDiskOD;
	}

	/**
	 * @return the sphDiskOS
	 */
	public String getSphDiskOS() {
		return sphDiskOS;
	}

	/**
	 * @return the cylDiskOS
	 */
	public String getCylDiskOS() {
		return cylDiskOS;
	}

	/**
	 * @return the axisDiskOS
	 */
	public String getAxisDiskOS() {
		return axisDiskOS;
	}

	/**
	 * @return the vnDiskOS
	 */
	public String getVnDiskOS() {
		return vnDiskOS;
	}

	/**
	 * @return the sphNearOD
	 */
	public String getSphNearOD() {
		return sphNearOD;
	}

	/**
	 * @return the cylNearOD
	 */
	public String getCylNearOD() {
		return cylNearOD;
	}

	/**
	 * @return the axisNearOD
	 */
	public String getAxisNearOD() {
		return axisNearOD;
	}

	/**
	 * @return the vnNearOD
	 */
	public String getVnNearOD() {
		return vnNearOD;
	}

	/**
	 * @return the sphNearOS
	 */
	public String getSphNearOS() {
		return sphNearOS;
	}

	/**
	 * @return the cylNearOS
	 */
	public String getCylNearOS() {
		return cylNearOS;
	}

	/**
	 * @return the axisNearOS
	 */
	public String getAxisNearOS() {
		return axisNearOS;
	}

	/**
	 * @return the vnNearOS
	 */
	public String getVnNearOS() {
		return vnNearOS;
	}

	/**
	 * @return the tint
	 */
	public String getTint() {
		return tint;
	}

	/**
	 * @return the glass
	 */
	public String getGlass() {
		return glass;
	}

	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @return the clinic
	 */
	public String getClinic() {
		return clinic;
	}

	/**
	 * @return the frame
	 */
	public String getFrame() {
		return frame;
	}

	/**
	 * @return the frameCharge
	 */
	public double getFrameCharge() {
		return frameCharge;
	}

	/**
	 * @return the glassCharge
	 */
	public double getGlassCharge() {
		return glassCharge;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @return the netPayment
	 */
	public double getNetPayment() {
		return netPayment;
	}

	/**
	 * @return the advance
	 */
	public double getAdvance() {
		return advance;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @return the balancePaid
	 */
	public double getBalancePaid() {
		return balancePaid;
	}

	/**
	 * @return the balancePaidDate
	 */
	public String getBalancePaidDate() {
		return balancePaidDate;
	}

	/**
	 * @param sphDiskOD the sphDiskOD to set
	 */
	public void setSphDiskOD(String sphDiskOD) {
		this.sphDiskOD = sphDiskOD;
	}

	/**
	 * @param cylDiskOD the cylDiskOD to set
	 */
	public void setCylDiskOD(String cylDiskOD) {
		this.cylDiskOD = cylDiskOD;
	}

	/**
	 * @param axisDiskOD the axisDiskOD to set
	 */
	public void setAxisDiskOD(String axisDiskOD) {
		this.axisDiskOD = axisDiskOD;
	}

	/**
	 * @param vnDiskOD the vnDiskOD to set
	 */
	public void setVnDiskOD(String vnDiskOD) {
		this.vnDiskOD = vnDiskOD;
	}

	/**
	 * @param sphDiskOS the sphDiskOS to set
	 */
	public void setSphDiskOS(String sphDiskOS) {
		this.sphDiskOS = sphDiskOS;
	}

	/**
	 * @param cylDiskOS the cylDiskOS to set
	 */
	public void setCylDiskOS(String cylDiskOS) {
		this.cylDiskOS = cylDiskOS;
	}

	/**
	 * @param axisDiskOS the axisDiskOS to set
	 */
	public void setAxisDiskOS(String axisDiskOS) {
		this.axisDiskOS = axisDiskOS;
	}

	/**
	 * @param vnDiskOS the vnDiskOS to set
	 */
	public void setVnDiskOS(String vnDiskOS) {
		this.vnDiskOS = vnDiskOS;
	}

	/**
	 * @param sphNearOD the sphNearOD to set
	 */
	public void setSphNearOD(String sphNearOD) {
		this.sphNearOD = sphNearOD;
	}

	/**
	 * @param cylNearOD the cylNearOD to set
	 */
	public void setCylNearOD(String cylNearOD) {
		this.cylNearOD = cylNearOD;
	}

	/**
	 * @param axisNearOD the axisNearOD to set
	 */
	public void setAxisNearOD(String axisNearOD) {
		this.axisNearOD = axisNearOD;
	}

	/**
	 * @param vnNearOD the vnNearOD to set
	 */
	public void setVnNearOD(String vnNearOD) {
		this.vnNearOD = vnNearOD;
	}

	/**
	 * @param sphNearOS the sphNearOS to set
	 */
	public void setSphNearOS(String sphNearOS) {
		this.sphNearOS = sphNearOS;
	}

	/**
	 * @param cylNearOS the cylNearOS to set
	 */
	public void setCylNearOS(String cylNearOS) {
		this.cylNearOS = cylNearOS;
	}

	/**
	 * @param axisNearOS the axisNearOS to set
	 */
	public void setAxisNearOS(String axisNearOS) {
		this.axisNearOS = axisNearOS;
	}

	/**
	 * @param vnNearOS the vnNearOS to set
	 */
	public void setVnNearOS(String vnNearOS) {
		this.vnNearOS = vnNearOS;
	}

	/**
	 * @param tint the tint to set
	 */
	public void setTint(String tint) {
		this.tint = tint;
	}

	/**
	 * @param glass the glass to set
	 */
	public void setGlass(String glass) {
		this.glass = glass;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * @param clinic the clinic to set
	 */
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}

	/**
	 * @param frameCharge the frameCharge to set
	 */
	public void setFrameCharge(double frameCharge) {
		this.frameCharge = frameCharge;
	}

	/**
	 * @param glassCharge the glassCharge to set
	 */
	public void setGlassCharge(double glassCharge) {
		this.glassCharge = glassCharge;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @param netPayment the netPayment to set
	 */
	public void setNetPayment(double netPayment) {
		this.netPayment = netPayment;
	}

	/**
	 * @param advance the advance to set
	 */
	public void setAdvance(double advance) {
		this.advance = advance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @param balancePaid the balancePaid to set
	 */
	public void setBalancePaid(double balancePaid) {
		this.balancePaid = balancePaid;
	}

	/**
	 * @param balancePaidDate the balancePaidDate to set
	 */
	public void setBalancePaidDate(String balancePaidDate) {
		this.balancePaidDate = balancePaidDate;
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
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * @return the eyeLidUpperOD
	 */
	public String getEyeLidUpperOD() {
		return eyeLidUpperOD;
	}

	/**
	 * @param eyeLidUpperOD the eyeLidUpperOD to set
	 */
	public void setEyeLidUpperOD(String eyeLidUpperOD) {
		this.eyeLidUpperOD = eyeLidUpperOD;
	}

	/**
	 * @return the eyeLidUpperOS
	 */
	public String getEyeLidUpperOS() {
		return eyeLidUpperOS;
	}

	/**
	 * @param eyeLidUpperOS the eyeLidUpperOS to set
	 */
	public void setEyeLidUpperOS(String eyeLidUpperOS) {
		this.eyeLidUpperOS = eyeLidUpperOS;
	}

	/**
	 * @return the eyeLidLowerOD
	 */
	public String getEyeLidLowerOD() {
		return eyeLidLowerOD;
	}

	/**
	 * @param eyeLidLowerOD the eyeLidLowerOD to set
	 */
	public void setEyeLidLowerOD(String eyeLidLowerOD) {
		this.eyeLidLowerOD = eyeLidLowerOD;
	}

	/**
	 * @return the eyeLidLowerOS
	 */
	public String getEyeLidLowerOS() {
		return eyeLidLowerOS;
	}

	/**
	 * @param eyeLidLowerOS the eyeLidLowerOS to set
	 */
	public void setEyeLidLowerOS(String eyeLidLowerOS) {
		this.eyeLidLowerOS = eyeLidLowerOS;
	}

	/**
	 * @return the conjunctivaOD
	 */
	public String getConjunctivaOD() {
		return conjunctivaOD;
	}

	/**
	 * @param conjunctivaOD the conjunctivaOD to set
	 */
	public void setConjunctivaOD(String conjunctivaOD) {
		this.conjunctivaOD = conjunctivaOD;
	}

	/**
	 * @return the conjunctivaOS
	 */
	public String getConjunctivaOS() {
		return conjunctivaOS;
	}

	/**
	 * @param conjunctivaOS the conjunctivaOS to set
	 */
	public void setConjunctivaOS(String conjunctivaOS) {
		this.conjunctivaOS = conjunctivaOS;
	}

	/**
	 * @return the corneaOD
	 */
	public String getCorneaOD() {
		return CorneaOD;
	}

	/**
	 * @param corneaOD the corneaOD to set
	 */
	public void setCorneaOD(String corneaOD) {
		CorneaOD = corneaOD;
	}

	/**
	 * @return the corneaOS
	 */
	public String getCorneaOS() {
		return CorneaOS;
	}

	/**
	 * @param corneaOS the corneaOS to set
	 */
	public void setCorneaOS(String corneaOS) {
		CorneaOS = corneaOS;
	}

	/**
	 * @return the aCOD
	 */
	public String getACOD() {
		return ACOD;
	}

	/**
	 * @param aCOD the aCOD to set
	 */
	public void setACOD(String aCOD) {
		ACOD = aCOD;
	}

	/**
	 * @return the aCOS
	 */
	public String getACOS() {
		return ACOS;
	}

	/**
	 * @param aCOS the aCOS to set
	 */
	public void setACOS(String aCOS) {
		ACOS = aCOS;
	}

	/**
	 * @return the irisOD
	 */
	public String getIrisOD() {
		return irisOD;
	}

	/**
	 * @param irisOD the irisOD to set
	 */
	public void setIrisOD(String irisOD) {
		this.irisOD = irisOD;
	}

	/**
	 * @return the irisOS
	 */
	public String getIrisOS() {
		return irisOS;
	}

	/**
	 * @param irisOS the irisOS to set
	 */
	public void setIrisOS(String irisOS) {
		this.irisOS = irisOS;
	}

	/**
	 * @return the lensOD
	 */
	public String getLensOD() {
		return lensOD;
	}

	/**
	 * @param lensOD the lensOD to set
	 */
	public void setLensOD(String lensOD) {
		this.lensOD = lensOD;
	}

	/**
	 * @return the lensOS
	 */
	public String getLensOS() {
		return lensOS;
	}

	/**
	 * @param lensOS the lensOS to set
	 */
	public void setLensOS(String lensOS) {
		this.lensOS = lensOS;
	}

	/**
	 * @return the iODOD
	 */
	public String getIODOD() {
		return IODOD;
	}

	/**
	 * @param iODOD the iODOD to set
	 */
	public void setIODOD(String iODOD) {
		IODOD = iODOD;
	}

	/**
	 * @return the iODOS
	 */
	public String getIODOS() {
		return IODOS;
	}

	/**
	 * @param iODOS the iODOS to set
	 */
	public void setIODOS(String iODOS) {
		IODOS = iODOS;
	}

	/**
	 * @return the sacOD
	 */
	public String getSacOD() {
		return sacOD;
	}

	/**
	 * @param sacOD the sacOD to set
	 */
	public void setSacOD(String sacOD) {
		this.sacOD = sacOD;
	}

	/**
	 * @return the sacOS
	 */
	public String getSacOS() {
		return sacOS;
	}

	/**
	 * @param sacOS the sacOS to set
	 */
	public void setSacOS(String sacOS) {
		this.sacOS = sacOS;
	}

	/**
	 * @return the k1OD
	 */
	public String getK1OD() {
		return k1OD;
	}

	/**
	 * @param k1od the k1OD to set
	 */
	public void setK1OD(String k1od) {
		k1OD = k1od;
	}

	/**
	 * @return the k1OS
	 */
	public String getK1OS() {
		return k1OS;
	}

	/**
	 * @param k1os the k1OS to set
	 */
	public void setK1OS(String k1os) {
		k1OS = k1os;
	}

	/**
	 * @return the k2OD
	 */
	public String getK2OD() {
		return k2OD;
	}

	/**
	 * @param k2od the k2OD to set
	 */
	public void setK2OD(String k2od) {
		k2OD = k2od;
	}

	/**
	 * @return the k2OS
	 */
	public String getK2OS() {
		return k2OS;
	}

	/**
	 * @param k2os the k2OS to set
	 */
	public void setK2OS(String k2os) {
		k2OS = k2os;
	}

	/**
	 * @return the axialLengthOD
	 */
	public String getAxialLengthOD() {
		return axialLengthOD;
	}

	/**
	 * @param axialLengthOD the axialLengthOD to set
	 */
	public void setAxialLengthOD(String axialLengthOD) {
		this.axialLengthOD = axialLengthOD;
	}

	/**
	 * @return the axialLengthOS
	 */
	public String getAxialLengthOS() {
		return axialLengthOS;
	}

	/**
	 * @param axialLengthOS the axialLengthOS to set
	 */
	public void setAxialLengthOS(String axialLengthOS) {
		this.axialLengthOS = axialLengthOS;
	}

	/**
	 * @return the iOLOD
	 */
	public String getIOLOD() {
		return IOLOD;
	}

	/**
	 * @param iOLOD the iOLOD to set
	 */
	public void setIOLOD(String iOLOD) {
		IOLOD = iOLOD;
	}

	/**
	 * @return the iOLOS
	 */
	public String getIOLOS() {
		return IOLOS;
	}

	/**
	 * @param iOLOS the iOLOS to set
	 */
	public void setIOLOS(String iOLOS) {
		IOLOS = iOLOS;
	}

	/**
	 * @return the pinholeVisionOD
	 */
	public String getPinholeVisionOD() {
		return pinholeVisionOD;
	}

	/**
	 * @param pinholeVisionOD the pinholeVisionOD to set
	 */
	public void setPinholeVisionOD(String pinholeVisionOD) {
		this.pinholeVisionOD = pinholeVisionOD;
	}

	/**
	 * @return the pinholeVisionOS
	 */
	public String getPinholeVisionOS() {
		return pinholeVisionOS;
	}

	/**
	 * @param pinholeVisionOS the pinholeVisionOS to set
	 */
	public void setPinholeVisionOS(String pinholeVisionOS) {
		this.pinholeVisionOS = pinholeVisionOS;
	}

	/**
	 * @return the bCVAOD
	 */
	public String getBCVAOD() {
		return BCVAOD;
	}

	/**
	 * @param bCVAOD the bCVAOD to set
	 */
	public void setBCVAOD(String bCVAOD) {
		BCVAOD = bCVAOD;
	}

	/**
	 * @return the bCVAOS
	 */
	public String getBCVAOS() {
		return BCVAOS;
	}

	/**
	 * @param bCVAOS the bCVAOS to set
	 */
	public void setBCVAOS(String bCVAOS) {
		BCVAOS = bCVAOS;
	}

	/**
	 * @return the discOD
	 */
	public String getDiscOD() {
		return discOD;
	}

	/**
	 * @param discOD the discOD to set
	 */
	public void setDiscOD(String discOD) {
		this.discOD = discOD;
	}

	/**
	 * @return the vesselOD
	 */
	public String getVesselOD() {
		return vesselOD;
	}

	/**
	 * @param vesselOD the vesselOD to set
	 */
	public void setVesselOD(String vesselOD) {
		this.vesselOD = vesselOD;
	}

	/**
	 * @return the maculaOD
	 */
	public String getMaculaOD() {
		return maculaOD;
	}

	/**
	 * @param maculaOD the maculaOD to set
	 */
	public void setMaculaOD(String maculaOD) {
		this.maculaOD = maculaOD;
	}

	/**
	 * @return the discOS
	 */
	public String getDiscOS() {
		return discOS;
	}

	/**
	 * @param discOS the discOS to set
	 */
	public void setDiscOS(String discOS) {
		this.discOS = discOS;
	}

	/**
	 * @return the vesselOS
	 */
	public String getVesselOS() {
		return vesselOS;
	}

	/**
	 * @param vesselOS the vesselOS to set
	 */
	public void setVesselOS(String vesselOS) {
		this.vesselOS = vesselOS;
	}

	/**
	 * @return the maculaOS
	 */
	public String getMaculaOS() {
		return maculaOS;
	}

	/**
	 * @param maculaOS the maculaOS to set
	 */
	public void setMaculaOS(String maculaOS) {
		this.maculaOS = maculaOS;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the finaTotalBill
	 */
	public double getFinalTotalBill() {
		return finalTotalBill;
	}

	/**
	 * @param finaTotalBill the finaTotalBill to set
	 */
	public void setFinalTotalBill(double finalTotalBill) {
		this.finalTotalBill = finalTotalBill;
	}

	/**
	 * @return the serviceCharge
	 */
	public String getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	/**
	 * @return the totalBill
	 */
	public double getTotalBill() {
		return totalBill;
	}

	/**
	 * @return the serviceTax
	 */
	public double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param totalBill the totalBill to set
	 */
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the visitNumber
	 */
	public int getVisitNumber() {
		return visitNumber;
	}

	/**
	 * @param visitNumber the visitNumber to set
	 */
	public void setVisitNumber(int visitNumber) {
		this.visitNumber = visitNumber;
	}

	/**
	 * @return the charges
	 */
	public double getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(double charges) {
		this.charges = charges;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @return the billID
	 */
	public int getBillID() {
		return billID;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @param billID the billID to set
	 */
	public void setBillID(int billID) {
		this.billID = billID;
	}

	/**
	 * @return the visitID
	 */
	public int username() {
		return visitID;
	}

	/**
	 * @return the prescriptionID
	 */
	public int getPrescriptionID() {
		return prescriptionID;
	}

	/**
	 * @param visitID the visitID to set
	 */
	public void setVisitID(int visitID) {
		this.visitID = visitID;
	}

	/**
	 * @param prescriptionID the prescriptionID to set
	 */
	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	/**
	 * @return the drugName
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * @return the dose
	 */
	public String getDose() {
		return dose;
	}

	/**
	 * @return the doseUnit
	 */
	public String getDoseUnit() {
		return doseUnit;
	}

	/**
	 * @return the noOfDays
	 */
	public String getNoOfDays() {
		return noOfDays;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param drugName the drugName to set
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	/**
	 * @param dose the dose to set
	 */
	public void setDose(String dose) {
		this.dose = dose;
	}

	/**
	 * @param doseUnit the doseUnit to set
	 */
	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	/**
	 * @param noOfDays the noOfDays to set
	 */
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the careType
	 */
	public String getCareType() {
		return careType;
	}

	/**
	 * @return the visitType
	 */
	public String getVisitType() {
		return visitType;
	}

	/**
	 * @return the visitDate
	 */
	public String getVisitDate() {
		return visitDate;
	}

	/**
	 * @return the visitFromTime
	 */
	public String getVisitFromTime() {
		return visitFromTime;
	}

	/**
	 * @return the visitToTime
	 */
	public String getVisitToTime() {
		return visitToTime;
	}

	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * @return the medicalNotes
	 */
	public String getMedicalNotes() {
		return medicalNotes;
	}

	/**
	 * @return the activityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param careType the careType to set
	 */
	public void setCareType(String careType) {
		this.careType = careType;
	}

	/**
	 * @param visitType the visitType to set
	 */
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * @param visitFromTime the visitFromTime to set
	 */
	public void setVisitFromTime(String visitFromTime) {
		this.visitFromTime = visitFromTime;
	}

	/**
	 * @param visitToTime the visitToTime to set
	 */
	public void setVisitToTime(String visitToTime) {
		this.visitToTime = visitToTime;
	}

	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * @param medicalNotes the medicalNotes to set
	 */
	public void setMedicalNotes(String medicalNotes) {
		this.medicalNotes = medicalNotes;
	}

	/**
	 * @param activityStatus the activityStatus to set
	 */
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * @return the aadhaarNo
	 */
	public String getAadhaarNo() {
		return aadhaarNo;
	}

	/**
	 * @return the emRelation
	 */
	public String getEmRelation() {
		return emRelation;
	}

	/**
	 * @param aadhaarNo the aadhaarNo to set
	 */
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	/**
	 * @param emRelation the emRelation to set
	 */
	public void setEmRelation(String emRelation) {
		this.emRelation = emRelation;
	}

	/**
	 * @return the emFname
	 */
	public String getEmFname() {
		return emFname;
	}

	/**
	 * @return the emMname
	 */
	public String getEmMname() {
		return emMname;
	}

	/**
	 * @return the emLname
	 */
	public String getEmLname() {
		return emLname;
	}

	/**
	 * @return the emAdd
	 */
	public String getEmAdd() {
		return emAdd;
	}

	/**
	 * @return the emCity
	 */
	public String getEmCity() {
		return emCity;
	}

	/**
	 * @return the emState
	 */
	public String getEmState() {
		return emState;
	}

	/**
	 * @return the emCountry
	 */
	public String getEmCountry() {
		return emCountry;
	}

	/**
	 * @return the emPhone
	 */
	public String getEmPhone() {
		return emPhone;
	}

	/**
	 * @return the emMobile
	 */
	public String getEmMobile() {
		return emMobile;
	}

	/**
	 * @return the emEmailID
	 */
	public String getEmEmailID() {
		return emEmailID;
	}

	/**
	 * @param emFname the emFname to set
	 */
	public void setEmFname(String emFname) {
		this.emFname = emFname;
	}

	/**
	 * @param emMname the emMname to set
	 */
	public void setEmMname(String emMname) {
		this.emMname = emMname;
	}

	/**
	 * @param emLname the emLname to set
	 */
	public void setEmLname(String emLname) {
		this.emLname = emLname;
	}

	/**
	 * @param emAdd the emAdd to set
	 */
	public void setEmAdd(String emAdd) {
		this.emAdd = emAdd;
	}

	/**
	 * @param emCity the emCity to set
	 */
	public void setEmCity(String emCity) {
		this.emCity = emCity;
	}

	/**
	 * @param emState the emState to set
	 */
	public void setEmState(String emState) {
		this.emState = emState;
	}

	/**
	 * @param emCountry the emCountry to set
	 */
	public void setEmCountry(String emCountry) {
		this.emCountry = emCountry;
	}

	/**
	 * @param emPhone the emPhone to set
	 */
	public void setEmPhone(String emPhone) {
		this.emPhone = emPhone;
	}

	/**
	 * @param emMobile the emMobile to set
	 */
	public void setEmMobile(String emMobile) {
		this.emMobile = emMobile;
	}

	/**
	 * @param emEmailID the emEmailID to set
	 */
	public void setEmEmailID(String emEmailID) {
		this.emEmailID = emEmailID;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
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
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
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
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the bloodGroup
	 */
	public String getBloodGroup() {
		return bloodGroup;
	}

	/**
	 * @param bloodGroup the bloodGroup to set
	 */
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	/**
	 * @return the rhFactor
	 */
	public String getRhFactor() {
		return rhFactor;
	}

	/**
	 * @param rhFactor the rhFactor to set
	 */
	public void setRhFactor(String rhFactor) {
		this.rhFactor = rhFactor;
	}

	public int getVisitID() {
		// TODO Auto-generated method stub
		return visitID;
	}

}
