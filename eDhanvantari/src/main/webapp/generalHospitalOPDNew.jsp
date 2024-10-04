<%@page import="com.edhanvantari.daoImpl.*"%>
<%@page import="com.edhanvantari.daoInf.*"%>
<%@page import="com.edhanvantari.service.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.edhanvantari.util.*"%>
<%@page import="com.edhanvantari.form.*"%>
<%@page import="com.amazonaws.auth.*"%>
<%@page import="com.amazonaws.services.s3.*"%>
<%@page import="com.amazonaws.services.s3.model.*"%>
<%@page import="com.amazonaws.util.*"%>
<%@page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Favicon -->
<link rel="shortcut icon" href="images/Icon.png">

<title>Add New Visit | E-Dhanvantari</title>
<!-- jQuery -->
<script src="vendors/jquery/dist/jquery.min.js"></script>
<!-- jQuery -->
<script type="text/javascript" src="build/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="build/js/jquery-ui-1.10.0.custom.min.js"></script>
<script type="text/javascript" src="build/js/jquery-ui.js"></script>

<!-- Bootstrap -->
<link href="vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="vendors/nprogress/nprogress.css" rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="build/css/bootstrap-3.0.3.min.css" />
<link rel="stylesheet" type="text/css"
	href="build/css/bootstrap-multiselect.css" />

<!-- Custom Theme Style -->
<link href="build/css/custom.min.css" rel="stylesheet">

<script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>

<!-- Datatables -->
<script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script
	src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script
	src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>

<!-- Custom Theme Scripts -->
<script src="build/js/custom.min.js"></script>

<!-- validator -->
<script src="vendors/validator/validator.js"></script>

<script type="text/javascript">
      function windowOpen(){
        document.location="Welcome.jsp";
      }
      
      function windowOpen1(){
    	  $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
    	  
    	  $('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
          document.location="ViewPatient";
          $("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
			
			$('a#profPicID').remove();	//To disable profile pic at the right top corner
			$('a#edhanLinkID').remove(); 	//To disable edhan link given on top of left menu
			$('a#dashboardID').remove();	//To disable leftmenu options dashboard
			$('a#pmID').remove();	$('a#pmID1').remove();	$('a#pmID2').remove();	$('a#pmID3').remove();	$('a#pmID4').remove();	//To disable leftmenu options patient manag
			$('a#ivntID').remove();	$('a#ivntID1').remove();	$('a#ivntID2').remove();	$('a#ivntID3').remove();	$('a#ivntID4').remove();	$('a#ivntID5').remove();	//To disable leftmenu options invt mang
			$('a#ivntID6').remove();	$('a#ivntID7').remove();	$('a#ivntID8').remove();	$('a#ivntID9').remove();	$('a#ivntID10').remove();	$('a#ivntID11').remove();	//To disable leftmenu options invt mang
			$('a#ivntID12').remove();	$('a#ivntID13').remove();  $('a#ivntID14').remove();	$('a#ivntID15').remove();	$('a#ivntID16').remove();	$('a#ivntID17').remove();	//To disable leftmenu options invt mang
			$('a#ivntID18').remove();	$('a#ivntID19').remove();	//To disable leftmenu options invt mang
			$('a#admID').remove();	$('a#admID1').remove();	$('a#admID2').remove();	$('a#admID3').remove();	$('a#admID4').remove();	$('a#admID5').remove();	//To disable leftmenu options adminstr
			$('a#admID6').remove();	$('a#admID7').remove();	$('a#admID8').remove();	$('a#admID9').remove();	$('a#admID10').remove();	//To disable leftmenu options adminstr
			$('a#repID').remove();	$('a#repID1').remove();	$('a#repID2').remove();	$('a#repID3').remove();	$('a#repID4').remove();  $('a#repID5').remove();	//To disable leftmenu options Reports
			$('a#repID6').remove();	$('a#repID7').remove();	$('a#repID8').remove();	$('a#repID9').remove();	$('a#repID10').remove();  $('a#repID11').remove();	//To disable leftmenu options Reports
			$('a#prID').remove();	$('a#prID1').remove();	$('a#prID2').remove();	//To disable leftmenu options Participant mng
        }
    </script>

<style type="text/css">
.dojoComboBoxOptions {
	top: 564px;
	left: 531px;
}

.datatable-responsive_filter {
	width: 100%;
}
</style>

<style type="text/css">
#labReportDivID {
	padding-bottom: 10px;
}
</style>

<!-- remove up down arrow from input type number -->
<style type="text/css">
/*Code to remove up/down arrow from input type number text field*/
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* Firefox */
input[type=number] {
	-moz-appearance: textfield;
}

ul.actionMessage {
	list-style: none;
	font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
}

ul.errorMessage {
	list-style: none;
	font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
}

.loadingImage {
	position: absolute;
	left: 50%;
	top: 25%;
	margin-left: -50px; /*half the image width*/
	margin-top: 10%; /*half the image height*/
	color: black;
	z-index: 1;
	display: none;
}

span.required {
	color: red !important;
}
</style>

<script type="text/javascript">  
		function showLoadingImg(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();	
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
			
			$('a#profPicID').remove();	//To disable profile pic at the right top corner
			$('a#edhanLinkID').remove(); 	//To disable edhan link given on top of left menu
			$('a#dashboardID').remove();	//To disable leftmenu options dashboard
			$('a#pmID').remove();	$('a#pmID1').remove();	$('a#pmID2').remove();	$('a#pmID3').remove();	$('a#pmID4').remove();	//To disable leftmenu options patient manag
			$('a#ivntID').remove();	$('a#ivntID1').remove();	$('a#ivntID2').remove();	$('a#ivntID3').remove();	$('a#ivntID4').remove();	$('a#ivntID5').remove();	//To disable leftmenu options invt mang
			$('a#ivntID6').remove();	$('a#ivntID7').remove();	$('a#ivntID8').remove();	$('a#ivntID9').remove();	$('a#ivntID10').remove();	$('a#ivntID11').remove();	//To disable leftmenu options invt mang
			$('a#ivntID12').remove();	$('a#ivntID13').remove();  $('a#ivntID14').remove();	$('a#ivntID15').remove();	$('a#ivntID16').remove();	$('a#ivntID17').remove();	//To disable leftmenu options invt mang
			$('a#ivntID18').remove();	$('a#ivntID19').remove();	//To disable leftmenu options invt mang
			$('a#admID').remove();	$('a#admID1').remove();	$('a#admID2').remove();	$('a#admID3').remove();	$('a#admID4').remove();	$('a#admID5').remove();	//To disable leftmenu options adminstr
			$('a#admID6').remove();	$('a#admID7').remove();	$('a#admID8').remove();	$('a#admID9').remove();	$('a#admID10').remove();	//To disable leftmenu options adminstr
			$('a#repID').remove();	$('a#repID1').remove();	$('a#repID2').remove();	$('a#repID3').remove();	$('a#repID4').remove();  $('a#repID5').remove();	//To disable leftmenu options Reports
			$('a#repID6').remove();	$('a#repID7').remove();	$('a#repID8').remove();	$('a#repID9').remove();	$('a#repID10').remove();  $('a#repID11').remove();	//To disable leftmenu options Reports
			$('a#prID').remove();	$('a#prID1').remove();	$('a#prID2').remove();	//To disable leftmenu options Participant mng
			return true;
		}  
	</script>

<!-- For login message -->
<%
	LoginForm form = (LoginForm) session.getAttribute("USER");
	if (session.getAttribute("USER") == "" || session.getAttribute("USER") == null) {
		form.setFullName("");
		String loginMessage = "Plase login using valid credentials";
		request.setAttribute("loginMessage", loginMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	int userID = form.getUserID();

	LoginDAOInf daoInf = new LoginDAOImpl();

	ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	String fullName = form.getFullName();

	String visitTabCheck = (String) request.getAttribute("visitTabCheck");

	if (visitTabCheck == null || visitTabCheck == "") {
		visitTabCheck = "dummy";
	}

	//AWS code to read/view image file

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	ConfigurationUtil util = new ConfigurationUtil();

	String accessKey = xmlUtil.getAccessKey();

	String secreteKey = xmlUtil.getSecreteKey();

	AWSS3Connect awss3Connect = new AWSS3Connect();

	// getting input file location from S3 bucket
	String s3reportFilePath = xmlUtil.getS3RDMLFilePath();

	String realPath = request.getServletContext().getRealPath("/");

	// getting s3 bucket name
	String bucketName = util.getS3BucketName();

	// getting s3 bucket region
	String bucketRegion = xmlUtil.getS3BucketRegion();

	AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

	AmazonS3 s3 = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
%>
<!-- Ends -->

<%
	String medicalCertiText = (String) request.getAttribute("medicalCertiText");

	String medicalCertificateFinalText = "";

	if (medicalCertiText == null || medicalCertiText == "") {

		medicalCertiText = "";
	} else {

		String[] array = medicalCertiText.split(" ");

		for (int i = 0; i < array.length; i++) {

			if (array[i].contains("'")) {

				String[] temp1 = array[i].split("'");

				String temp2 = temp1[1];

				// Splitting temp2 by @ to get value as well as id 
				String[] temp4 = temp2.split("@");

				String fieldValue = temp4[0];

				fieldValue = fieldValue.replace("_", " ");

				String idValue = temp4[1];

				String fieldValueTemp = fieldValue;

				medicalCertificateFinalText += fieldValueTemp.replace(fieldValueTemp,
						"<input type='text' class='form-control' style='width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;' name='"
								+ idValue + "' id='" + idValue + "' value='" + fieldValue + "'> ")
						+ " ";

			} else if (array[i].contains("\n")) {
				medicalCertificateFinalText += array[i] + "<br><br>";
			} else if (array[i].contains("\t")) {
				medicalCertificateFinalText += array[i]
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			} else {
				medicalCertificateFinalText += array[i] + " ";
			}
		}
	}
%>


<%
	String referralLetterText = (String) request.getAttribute("referralLetter");

	String referralLetterFinalText = "";

	if (referralLetterText == null || referralLetterText == "") {
		referralLetterText = "";
	} else {

		String[] array = referralLetterText.split(" ");

		for (int i = 0; i < array.length; i++) {

			if (array[i].contains("'")) {

				String[] temp1 = array[i].split("'");

				String temp2 = temp1[1];

				// Splitting temp2 by @ to get value as well as id 
				String[] temp4 = temp2.split("@");

				String fieldValue = temp4[0];

				fieldValue = fieldValue.replace("_", " ");

				String idValue = temp4[1];

				String fieldValueTemp = fieldValue;

				if (idValue.equals("doctName")) {

					List<String> dorctList = configXMLUtil1.getReferralDoctorList();

					String selectTag = "<select class='form-control' name='doctName' id='doctName' style='width:20%;' id='"
							+ idValue + "' >";

					Iterator<String> iterator = dorctList.iterator();

					while (iterator.hasNext()) {
						String value = iterator.next();

						if (value.equals(fieldValue)) {
							selectTag += "<option value='" + value + "' selected>" + value + "</option>";
						} else {
							selectTag += "<option value='" + value + "'>" + value + "</option>";
						}
					}
					selectTag += "</select>";

					referralLetterFinalText += fieldValueTemp.replace(fieldValueTemp, selectTag);

				} else {
					referralLetterFinalText += fieldValueTemp.replace(fieldValueTemp,
							"<textarea class='form-control' rows='4'  name='" + idValue + "' id='" + idValue
									+ "'>" + fieldValue + " </textarea>")
							+ " ";
				}

			} else if (array[i].contains("\n")) {
				referralLetterFinalText += array[i] + "<br><br>";
			} else if (array[i].contains("\t")) {
				referralLetterFinalText += array[i]
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			} else {
				referralLetterFinalText += array[i] + " ";
			}
		}
	}
%>

<!-- Print visit, prescription and billing -->

<script type="text/javascript">
		function printVisit(){
			
			document.forms["visitrForm"].action = "PrintGeneralHospitalVisit";
			document.forms["visitrForm"].submit();
		}
		function updatePrescription(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
		
			document.forms["genPhyPrescID"].action = "AddNewGeneralHospitalPrescription";
			document.forms["genPhyPrescID"].submit();
			// $("#savePrescID").attr("disabled", "disabled");
		}
		
	</script>

<!-- Ends -->

<!-- Add more file function for lab report -->

<script type="text/javascript">
		function myFunction() {
			var para = document.createElement("DIV");
			para.setAttribute("id", "labReportDivID");
			var t = document.createElement("INPUT");
			t.setAttribute("type", "file");
			t.setAttribute("name", "labReport");
			para.appendChild(t);
			document.getElementById("myID").appendChild(para);
		}
	</script>

<!-- Ends -->

<!-- For session timeout -->
<%
	ConfigurationUtil configurationUtil = new ConfigurationUtil();

	String sessionTimeoutString = configurationUtil.getSessionTimeOut();

	if (sessionTimeoutString == null || sessionTimeoutString == "") {
		sessionTimeoutString = "30";
	} else if (sessionTimeoutString.isEmpty()) {
		sessionTimeoutString = "30";
	}

	int sessionTimeout = Integer.parseInt(sessionTimeoutString);

	int maxSessionTimeout = sessionTimeout * 60 * 1000;
	//session.setMaxInactiveInterval(maxSessionTimeout);

	//redirecting to login page on session timeout.
	//int timeout = session.getMaxInactiveInterval();
	//response.setHeader("Refresh", timeout + "; URL = index.jsp?message=Your session has been expired. Please login again.");
%>
<!-- Ends -->

<!-- Displaying Medical Certificate text -->

<script type="text/javascript">
	  function displayVal(){
		
	    var companyName = document.getElementById("companyName").value;
	    var dutyDays = document.getElementById("dutyDays").value;
	    var disease = document.getElementById("disease").value;
	    var doctorName = document.getElementById("doctrName").value;
	    var patientName = document.getElementById("patientName").value;
	    var wef = document.getElementById("wef").value;
	    var visitDte = document.getElementById("date11").value;
	    var place = document.getElementById("place").value;
	    var companyName1 = document.getElementById("companyName").value;
	    var disease1 = document.getElementById("disease").value;
	    var place1 = document.getElementById("place").value;
	    var wef1 = document.getElementById("wef").value;
	
	    if(companyName.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	        companyName = companyName.replace(regExp, "_");
	    }else{
	    	companyName = companyName;
	    }
	
	    if(disease.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	        disease = disease.replace(regExp, "_");
	    }else{
	    	disease = disease;
	    }
	
	    if(place.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	        place = place.replace(regExp, "_");
	    }else{
	    	place = place;
	    }
	
	    if(wef.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	        wef = wef.replace(regExp, "_");
	    }else{
	    	wef = wef;
	    }
	    
	
	    var medicalCertificate = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in '"+companyName+ "@companyName', is suffering from '" + disease + "@disease' and I consider that the period of absence from duty of '"+ dutyDays + "@dutyDays' days with effect from '"+ wef + "@wef' is absolutely necessary for the restoration of his/her health.\n\n\n Place: '"+place+ "@place' \n\n\n Date: '" +visitDte + "@date11' \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    var medicalCertificateForPDF = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in "+companyName1+ ", is suffering from " + disease1 + " and I consider that the period of absence from duty of "+ dutyDays + " days with effect from "+ wef1 + " is absolutely necessary for the restoration of his/her health.\n\n\n Place: "+place1+ " \n\n\n Date: " +visitDte + " \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    document.getElementById("medicalCerti").value = medicalCertificate;
	
	    document.getElementById("medicalCertiForPDF").value = medicalCertificateForPDF;
	
	
	  }
	</script>

<!-- Ends -->

<!-- Displaying referral letter text -->

<script type="text/javascript">
	  function displayRefLet(){
		 	
	    var conditionText = document.getElementById("conditionText").value;
	    var doctName = document.getElementById("doctName").value;
	    var doctorName = document.getElementById("doctrNameRef").value;
	    var patientName = document.getElementById("patientNameRef").value;
	    var visitDte = document.getElementById("date12").value;
	    var conditionText1 = document.getElementById("conditionText").value;
	    var doctName1 = document.getElementById("doctName").value;
	
	    if(doctName.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	    	doctName = doctName.replace(regExp, "_");
	    }else{
	    	doctName = doctName;
	    }
	
	    if(conditionText.indexOf(" ") != "-1"){
	    	var regExp = new RegExp(' ','g');
	    	conditionText = conditionText.replace(regExp, "_");
	    }else{
	    	conditionText = conditionText;
	    }
	
	    var referralLetter = "Date: "+visitDte+ " \n\n\n Dear Dr. '"+ doctName + "@doctName', \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n '" + conditionText + "@conditionText' \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
	    var referralLetterForPDF = "Date: "+visitDte+ "\n\n\n Dear Dr. "+ doctName1 + ", \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n " + conditionText1 + " \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
	    document.getElementById("referralLetter").value = referralLetter;
	
	    document.getElementById("referralLetterForPDF").value = referralLetterForPDF;
	
	
	  }
	</script>

<!-- Ends -->

<script type="text/javascript">
	
		function confirmDelete(prescID,visitID){
			if (confirm("Are you sure you want to delete prescription?")) {
				deletePrescription(prescID, visitID);
			}
			
		}
	
		function confirmBillDelete(billID,visitID){
			if (confirm("Are you sure you want to delete bill?")) {
				deleteBilling(billID, visitID);
			}
		}
	
	</script>

<%
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	String currentDate = format.format(date);

	SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
	String currentTime = format1.format(date);

	String[] timeArray = currentTime.split(":");
	String hour = timeArray[0];
	String minute = timeArray[1];
	int minuteInt = Integer.parseInt(minute);
	int hourInteger = Integer.parseInt(hour);
	String ampm = "am";

	if (hourInteger > 12) {
		ampm = "pm";
		hour = String.valueOf(hourInteger - 12);
		System.out.println("Hour value is ::: " + hour);
		System.out.println("AMPM value is ::: " + ampm);
	} else if (hourInteger == 12) {
		ampm = "pm";
	}

	if ((minuteInt >= 0) && (minuteInt < 5)) {

		minute = "0";

	} else if ((minuteInt >= 5) && (minuteInt < 10)) {

		minute = "5";

	} else if ((minuteInt >= 10) && (minuteInt < 15)) {

		minute = "10";

	} else if ((minuteInt >= 15) && (minuteInt < 20)) {

		minute = "15";

	} else if ((minuteInt >= 20) && (minuteInt < 25)) {

		minute = "20";

	} else if ((minuteInt >= 25) && (minuteInt < 30)) {

		minute = "25";

	} else if ((minuteInt >= 30) && (minuteInt < 35)) {

		minute = "30";

	} else if ((minuteInt >= 35) && (minuteInt < 40)) {

		minute = "35";

	} else if ((minuteInt >= 40) && (minuteInt < 45)) {

		minute = "40";

	} else if ((minuteInt >= 45) && (minuteInt < 50)) {

		minute = "45";

	} else if ((minuteInt >= 50) && (minuteInt < 55)) {

		minute = "50";

	} else {

		minute = "55";

	}

	String[] currentDateArray = currentDate.split("-");

	String currDay = currentDateArray[0];
	String currMonth = currentDateArray[1];
	String currYear = currentDateArray[2];

	//Retrieving appointment duration from Calendar table based on clinicID
	PatientDAOInf patientDAOInf = new PatientDAOImpl();

	String appointmentDuration = patientDAOInf.retrieveApptDurationFromClinicID(form.getVisitTypeID());

	int minutesWithDuration = minuteInt + Integer.parseInt(appointmentDuration);

	int hourInt = Integer.parseInt(hour);

	String toAmPm = ampm;

	System.out.println("To Hour:" + String.valueOf(hourInt));
	System.out.println("To Moniute:" + String.valueOf(minutesWithDuration));
	System.out.println("To AMPM:" + toAmPm);

	if (minutesWithDuration > 60) {

		if (hourInt > 12) {

			minutesWithDuration = minutesWithDuration - 60;

			hourInt = hourInt - 12;

			toAmPm = "pm";
		} else if (hourInt == 12) {

			minutesWithDuration = minutesWithDuration - 60;

			toAmPm = "pm";
		} else {

			minutesWithDuration = minutesWithDuration - 60;
		}

	} else if (minutesWithDuration == 60) {

		if (hourInt > 12) {

			minutesWithDuration = 0;

			hourInt = hourInt - 12;

			toAmPm = "pm";
		} else if (hourInt == 12) {

			minutesWithDuration = 0;

			toAmPm = "pm";
		} else {

			minutesWithDuration = 0;
		}

	}

	if ((minutesWithDuration >= 0) && (minutesWithDuration < 5)) {

		minutesWithDuration = 0;

	} else if ((minutesWithDuration >= 5) && (minutesWithDuration < 10)) {

		minutesWithDuration = 5;

	} else if ((minutesWithDuration >= 10) && (minutesWithDuration < 15)) {

		minutesWithDuration = 10;

	} else if ((minutesWithDuration >= 15) && (minutesWithDuration < 20)) {

		minutesWithDuration = 15;

	} else if ((minutesWithDuration >= 20) && (minutesWithDuration < 25)) {

		minutesWithDuration = 20;

	} else if ((minutesWithDuration >= 25) && (minutesWithDuration < 30)) {

		minutesWithDuration = 25;

	} else if ((minutesWithDuration >= 30) && (minutesWithDuration < 35)) {

		minutesWithDuration = 30;

	} else if ((minutesWithDuration >= 35) && (minutesWithDuration < 40)) {

		minutesWithDuration = 35;

	} else if ((minutesWithDuration >= 40) && (minutesWithDuration < 45)) {

		minutesWithDuration = 40;

	} else if ((minutesWithDuration >= 45) && (minutesWithDuration < 50)) {

		minutesWithDuration = 45;

	} else if ((minutesWithDuration >= 50) && (minutesWithDuration < 55)) {

		minutesWithDuration = 50;

	} else {

		minutesWithDuration = 55;
	}

	String appointmentCheck = (String) request.getAttribute("appointmentCheck");
	if (appointmentCheck == null || appointmentCheck == "") {
		appointmentCheck = "";
	} else {
%>

<script type="text/javascript">
		document.addEventListener('DOMContentLoaded', function() {
			
			//setting values for visit from time
			$("#fromHH").val(<%=hour%>);
			$("#fromMM").val(<%=minute%>);
			$("#fromMMAMPM").val('<%=ampm%>');
	
			//setting values for visit to time
			$("#toHH").val(<%=String.valueOf(hourInt)%>);
			$("#toMM").val(<%=String.valueOf(minutesWithDuration)%>);
			$("#toMMAMPM").val('<%=toAmPm%>');
		});
	</script>

<%
	}
%>

<script type="text/javascript">
function checkCheckbox() {
		
	var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
	if(checkboxLength>0){
		
		$('.checkboxClass').prop("required", false);
		
		document.forms["genPhyBillID"].action = "AddNewGeneralHospitalBill";
		document.forms["genPhyBillID"].submit();
			return true;
		
	}else{
		$('.checkboxClass').prop("required", true);
		return false;
	}
}
</script>


<!-- Lock screen pop up calling after time interval -->

<script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	var interval = <%=maxSessionTimeout%>;
    		setInterval(function(){

    			//Setting blank values to PIN fields
            	$('#lockPIN').val("");

            	//opening security credentials modal
        		$("#lockModal").modal("show");	
				
        	}, interval);
        });
    </script>

<!-- Ends -->

<!-- Shortcut menu Full screen function -->

<script type="text/javascript">
    window.onload = maxWindow;

    function widnwoFullScreen() {
        
    	var docElm = document.documentElement;
		if (docElm.requestFullscreen) {
		    docElm.requestFullscreen();
		}
		else if (docElm.mozRequestFullScreen) {
		    docElm.mozRequestFullScreen();
		}
		else if (docElm.webkitRequestFullScreen) {
		    docElm.webkitRequestFullScreen();
		}
		
	}

	</script>

<!-- Ends -->

<!-- Show modals function -->

<script type="text/javascript">
    function showLockModal(){
    	$(document).ready(function(){
    		//Setting blank values to PIN fields
        	$('#lockPIN').val("");

        	//opening security credentials modal
    		$("#lockModal").modal("show");
    	});
    }

    function showLogoutModal(){
    	$(document).ready(function(){
    		$("#logoutModal").modal("show");
    	});
    }

    function showSecurityModal(){
    	$(document).ready(function(){
        	//Setting blank values and disabling new Pass and confirm new Pass fields
    		$('#oldPassword').val("");
    		$('#newPassID').val("");
    		$('#confirmNewPassID').val("");
        	$('#newPassID').prop('disabled', true);
        	$('#confirmNewPassID').prop('disabled', true);

        	//Setting blank values to new PIN and confirm new PIN fields
        	$('#newPINID').val("");
    		$('#confirmNewPINID').val("");

    		//removing images by setting innerHTML to blank("")
    		$('#oldPassID').html("");
    		$('#newPassCheckID').html("");
    		$('#confirmPassID').html("");    		
    		$('#confirmPINID').html(""); 

    		//opening security credentials modal
    		$("#securityModal").modal("show");
    	});
    }
    </script>

<!-- ENds -->

<!-- Lock PIN check -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function checkUnlockPIN(lockPIN) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				for ( var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PINCheck;
					var errMsg = array.Release[i].ErrMsg;

					if(check == "check"){

						document.getElementById("lockImgID").innerHTML = "";

						unlockModal();
						
					}else{

						document.getElementById("lockImgID").innerHTML = "<img src='images/wrong.png' alt='Incorrect PIN' title='Incorrect PIN' style='height:24px;margin-top: 5px;'>";
						
					}
				}
			}
		};
		xmlhttp.open("GET", "VerifyUnlockPIN?lockPIN="
				+ lockPIN, true);
		xmlhttp.send();
	}
	</script>

<!-- Ends -->


<!-- Old Password check -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function verifyOldPass(oldPass) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				for ( var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if(check == "check"){

						document.getElementById("oldPassID").innerHTML = "<img src='images/correct.png' alt='Correct Old Password' title='Correct Old Password' style='height:24px;margin-top: 5px;'>";

						//Enabling New password and confirm password input fields
						document.getElementById("newPassID").disabled = false;
						
					}else{

						document.getElementById("oldPassID").innerHTML = "<img src='images/wrong.png' alt='Incorrect Old Password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";
						
					}
				}
			}
		};
		xmlhttp.open("GET", "VerifyOldPass?oldPass="
				+ oldPass, true);
		xmlhttp.send();
	}
	</script>

<!-- Ends -->

<!-- New Password check -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function newPassCheck(newPass) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				for ( var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].newPassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if(check == "true"){

						document.getElementById("newPassCheckID").innerHTML = "<img src='images/correct.png' alt='Valid new password' title='New password is valid' style='height:24px;margin-top: 5px;'>";
						document.getElementById("confirmNewPassID").disabled = false;
						
					}else{

						document.getElementById("newPassCheckID").innerHTML = "<img src='images/wrong.png' alt='Invalid new password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";
						
					}
				}
			}
		};
		xmlhttp.open("GET", "NewPassCheck?newPass="
				+ newPass, true);
		xmlhttp.send();
	}
	</script>

<!-- Ends -->


<!-- Submit Security Credentials -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function updateSecurityCredentials(newPass, newPIN) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				for ( var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if(check == "check"){

						hideSecCredModal();
						
					}else{

						document.getElementById("oldPassID").innerHTML = "<img src='images/wrong.png' alt='Incorrect Old Password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";
						
					}
				}
			}
		};
		xmlhttp.open("GET", "UpdateSecurityCredentials?newPass="
				+ newPass + "&newPIN=" + newPIN , true);
		xmlhttp.send();
	}
	</script>

<!-- Ends -->


<!-- Confirm new pass and Confirm new PIN check -->

<script type="text/javascript">
    	function checkConfirmNewPass(confirmNewPassID,newPassID){

			if(confirmNewPassID == newPassID){

				document.getElementById("confirmPassID").innerHTML = "<img src='images/correct.png' alt='Confirm New Password matched' title='Confirm New Password matched' style='height:24px;margin-top: 5px;'>";

				$('#securityBtnID').prop('disabled', false);
				
			}else{

				$('#securityBtnID').prop('disabled', true);

				document.getElementById("confirmPassID").innerHTML = "<img src='images/wrong.png' alt='Confirm Password didn't match' title='Confirm New Password must be same as New Password' style='height:24px;margin-top: 5px;'>";

			}
    	}

    	function checkConfirmNewPIN(confirmNewPINID,newPINID){

			if(confirmNewPINID == newPINID){

				document.getElementById("confirmPINID").innerHTML = "<img src='images/correct.png' alt='Confirm New PIN matched' title='Confirm New PIN matched' style='height:24px;margin-top: 5px;'>";

				$('#securityBtnID').prop('disabled', false);
				
			}else{

				$('#securityBtnID').prop('disabled', true);

				document.getElementById("confirmPINID").innerHTML = "<img src='images/wrong.png' alt='Confirm PIN didn't match' title='Confirm New PIN must be same as New PIN' style='height:24px;margin-top: 5px;'>";

			}
    	}
    </script>

<!-- Ends -->


<!-- Hide Lock Modal and Security Credentials modal -->

<script type="text/javascript">
    	function unlockModal(){
			$(document).ready(function(){
				
				$('#lockButtonID').click();
			});
    	}

    	function hideSecCredModal(){
			$(document).ready(function(){
				
				$('#secCredCancelID').click();
			});
    	}
    </script>

<!-- Ends -->


<!-- Add prescription -->
<script type="text/javascript" charset="UTF-8">
    
    	function addInjectionPrescriptionRow(drug, doseBefore, doseAfter, doseAfterDinner, comment, duration, check){
    		
    		var autoSelecter = dojo.widget.byId('tradeName');
    		
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Please select injection name");
			}else if(doseAfter == ""){
				alert("Please enter after dose");
			}else if(duration == ""){
				alert("Please select duration");
			}else{
				
				retrieveTradeNameForPrescription(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, check);
			}
    		
    	}
	
    	
		/* addmore row for Injection Prescription */
    	
    	var prescCounter = 1;
    
		function addInjectionPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID){
			
			var autoSelecter = dojo.widget.byId("tradeName");
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+tradeName+"<input type='hidden' name='newDrugID' value='"+tradeNameID+"'></td>"
					   +"<td style='text-align:center'>"+doseBefore+"<input type='hidden' name='newDrugDosageBefore' value='"+doseBefore+"'></td>"
					   +"<td style='text-align:center'>"+doseAfter+"<input type='hidden' name='newDrugDosageAfter' value='"+doseAfter+"'></td>"
					   +"<td style='text-align:center'>"+doseAfterDinner+"<input type='hidden' name='newDrugDosageAfterDinner' value='"+doseAfterDinner+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugDosageComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDrugDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
					   
			$(trTag).insertAfter($("#prescTRID"));
			
			prescCounter++;
			  
			autoSelecter.innerHTML = "";
		//	$("#injectionID").val("-1");
			$("#dosageBeforeMealID").val("");
			$("#dosageAfterMealID").val("");
			$("#durationID").val("");
			$("#dosageCommentID").val("");
			$("#dosageAfterDinnerID").val("");
			
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
				
		}
		
		//function to delete prescription row
		function deletePrescRow(trID, prescriptionID){
			if(confirm("Are you sure you want to delete this row?")){
				deletePrescRow1(trID, prescriptionID);
			}
		}

	</script>
<!-- Ends -->

<script type="text/javascript">
		
	/* retrieve TradeName For Prescription By productID */
    
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveTradeNameForPrescription(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, check){
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						var tradeNameID = array.Release[i].tradeNameID;
					}
					
					if(check=="Injection"){
						addInjectionPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID);
					}else if(check=="Tablet"){
						addTabletPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID);
					}else if(check=="Liquid"){
						addLiquidPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID);
					}
				}
			};
			xmlhttp.open("GET", "RetrieveTradeNameForPrescriptionBYProductID?tradeName="
					+ tradeName+"&category="+check, true);
			xmlhttp.send();
		}
	
	</script>


<!-- Delete prscription -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deletePrescRow1(trID, prescriptionID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = 0;
					var excptnMsg = "";
	
					for ( var i = 0; i < array.Release.length; i++) {
					
						excptnMsg = array.Release[i].ExceptionMessage;
						check = array.Release[i].check;
	
					}
					
					if(check == 0){
						document.getElementById("exceptionMSG").innerHTML = excptnMsg;
					}else{
						document.getElementById("successMSG").innerHTML = "Prescription deleted successfully.";
						$("#"+trID).remove();
					}
					
					//retrieveValues(visitID);
					
					
				}
			};
			xmlhttp.open("GET", "DeletePrescription?prescriptionID="+ prescriptionID , true);
			xmlhttp.send();
		}
	</script>

<script type="text/javascript">
	
		function addTabletPrescriptionRow(drug, doseBefore, doseAfter, doseAfterDinner, comment, duration, check){
		
			var autoSelecter = dojo.widget.byId("tradeNameTablet");
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Please select tablet/capsule name");
			}else if(doseAfter == ""){
				alert("Please enter after dose");
			}else if(duration == ""){
				alert("Please select duration");
			}else{
				retrieveTradeNameForPrescription(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, check);
			}
			
		}
		
		var prescCounter = 1;
	
		function addTabletPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID){
			
			var autoSelecter = dojo.widget.byId("tradeName");
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+tradeName+"<input type='hidden' name='newDrugID' value='"+tradeNameID+"'></td>"
					   +"<td style='text-align:center'>"+doseBefore+"<input type='hidden' name='newDrugDosageBefore' value='"+doseBefore+"'></td>"
					   +"<td style='text-align:center'>"+doseAfter+"<input type='hidden' name='newDrugDosageAfter' value='"+doseAfter+"'></td>"
					   +"<td style='text-align:center'>"+doseAfterDinner+"<input type='hidden' name='newDrugDosageAfterDinner' value='"+doseAfterDinner+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugDosageComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDrugDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTabTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
					   
			$(trTag).insertAfter($("#prescTabTRID"));
			
			prescCounter++;
			
			autoSelecter.innerHTML = "";
		//	$("#tabletID").val("-1");
			$("#dosageBeforeMealTabID").val("");
			$("#dosageAfterMealTabID").val("");
			$("#durationTabID").val("");
			$("#dosageCommentTabID").val("");
			$("#dosageAfterDinnerTabID").val(""); 
			
		}   
		
		function removeTabTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
				
		}
	</script>

<script type="text/javascript">
	
		function addLiquidPrescriptionRow(drug, doseBefore, doseAfter, doseAfterDinner, comment, duration, check){
		
			var autoSelecter = dojo.widget.byId("tradeNameLiquid");
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Please select liquid name");
			}else if(doseAfter == ""){
				alert("Please enter after dose");
			}else if(duration == ""){
				alert("Please select duration");
			}else{
				retrieveTradeNameForPrescription(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, check);
			}
			
		}
		
		var prescCounter = 1;
	
		function addLiquidPrescriptionRow1(tradeName, doseBefore, doseAfter, doseAfterDinner, comment, duration, tradeNameID){
			var autoSelecter = dojo.widget.byId("tradeName");
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+tradeName+"<input type='hidden' name='newDrugID' value='"+tradeNameID+"'></td>"
					   +"<td style='text-align:center'>"+doseBefore+"<input type='hidden' name='newDrugDosageBefore' value='"+doseBefore+"'></td>"
					   +"<td style='text-align:center'>"+doseAfter+"<input type='hidden' name='newDrugDosageAfter' value='"+doseAfter+"'></td>"
					   +"<td style='text-align:center'>"+doseAfterDinner+"<input type='hidden' name='newDrugDosageAfterDinner' value='"+doseAfterDinner+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugDosageComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDrugDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeLiquidTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
					   
			$(trTag).insertAfter($("#prescLiquidTRID"));
			
			prescCounter++;
			
			autoSelecter.innerHTML = "";
			//$("#liquidID").val("-1");
			$("#dosageBeforeMealLiqID").val("");
			$("#dosageAfterMealLiqID").val("");
			$("#durationLiqID").val("");
			$("#dosageAfterDinnerLiqID").val("");    
			$("#dosageCommentLiqID").val(""); 
			
		}
		
		function removeLiquidTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}	
		
		
		function deleteRow1(deletePrescID, TRID, prescDeleteType){
			if(confirm("Are you sure you want to remove this row?")){
				deletePrescription(deletePrescID, TRID, prescDeleteType);
			}
		}
		
		
	</script>
<!-- Ends -->

<!-- Delete prscription -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deletePrescription(deletePrescID, TRID, prescDeleteType) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						
					}
					
					if(check == 1){
						
						$("#"+TRID+"").remove();
						
					}else{
						alert("Failed to delete row. Please check server logs for more details.")
					}
					
				}
			};
			xmlhttp.open("GET", "DeleteGeneralHospitalPrescRow?prescriptionID="
					+ deletePrescID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
<!-- Ends -->


<!-- Delete billing -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteBilling(billID,visitID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						document.getElementById("successMSG").innerHTML = array.Release[i].SuccessMessage;
						document.getElementById("errorMSG").innerHTML = array.Release[i].ErrorMessage;
						document.getElementById("exceptionMSG").innerHTML = array.Release[i].ExceptionMessage;
	
						if(document.getElementById("successMSG").innerHTML == "undefined"){
							document.getElementById("successMSG").innerHTML = "";
						}
	
						if(document.getElementById("errorMSG").innerHTML == "undefined"){
							document.getElementById("errorMSG").innerHTML = "";
						}
	
						if(document.getElementById("exceptionMSG").innerHTML == "undefined"){
							document.getElementById("exceptionMSG").innerHTML = "";
						}
					}
					
					retrieveBillValues(visitID);
					
				}
			};
			xmlhttp.open("GET", "DeleteBilling?billID="+ billID , true);
			xmlhttp.send();
		}
	</script>
<!-- Ends -->

<!-- retrieve prescription values -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveValues(visitID) {
	
			visitID = document.getElementById("visitID").value;
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					var array_element = "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'> "+
					" <thead>";
					array_element += "<tr>"+
	                "<th>Trade Name</th>"+
	                "<th>Dose</th>"+
	                "<th>Frequency</th>"+
	                "<th>No. of Pills</th>"+
	                "<th>No. of Days</th>"+
	                "<th>Comments</th>"+
	                "<th>Action</th>"+
	            	"</tr>  </thead>  <tbody>";
					for ( var i = 0; i < array.Release.length; i++) {
						
							array_element += "<tr>";
							array_element += "<td>"+
							 array.Release[i].doseUnit+"</td>";
							array_element += "<td>"+
							 array.Release[i].dose+"</td>";
							array_element += "<td>"+
							 array.Release[i].frequency+"</td>";
							array_element += "<td>"+
							 array.Release[i].noOfPills+"</td>";
							array_element += "<td>"+
							 array.Release[i].numberOfDays+"</td>";
							array_element += "<td>"+
							 array.Release[i].comment+"</td>";
							array_element += "<td>"+
							 "<a onclick='confirmDelete("+array.Release[i].prescriptionID+","+array.Release[i].visitID+")';><img src='images/delete_icon_1.png'"+
							 " onmouseover='this.src='images/delete_icon_2.png'' onmouseout='this.src='images/delete_icon_1.png'' alt='Delete Prescription'"+
							 "title='Delete Prescription' style='margin-top:5px; height:24px;'/> </a></td>";
							array_element += "</tr>";
							
					}
					
					array_element += "</tbody> </table>";
					
					document.getElementById("OPDPResc").innerHTML = array_element;
	
					retrieveBillValues(visitID);
	
				}
			};
			xmlhttp.open("GET", "RetrievePrescriptionValues?visitID="
					+ visitID, true);
			xmlhttp.send();
		}
	</script>
<!-- Ends -->

<!-- Retrieve bill details -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveBillValues(visitID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					
					var totalBill = "";
					var array_element = "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'> "+
					" <thead>";
					array_element += "<tr>"+
	                    "<th>Description</th>"+
	                    "<th>Charges(Rs)</th>"+
	                    "<th>Action</th>"+
	                	"</tr>  </thead>  <tbody>";
					for ( var i = 0; i < array.Release.length; i++) {
							
							array_element += "<tr>";
							array_element += "<td>"+
							 array.Release[i].chargeType+"</td>";
							array_element += "<td>"+
							 array.Release[i].charge+"</td>";
							array_element += "<td>"+
							 "<a onclick='confirmBillDelete("+array.Release[i].billID+","+array.Release[i].visitID+")';><img src='images/delete_icon_1.png'"+
							 " onmouseover='this.src='images/delete_icon_2.png'' onmouseout='this.src='images/delete_icon_1.png'' alt='Delete Billing'"+
							 "title='Delete Billing' style='margin-top:5px; height:24px;'/> </a></td>";
							array_element += "</tr>";
							totalBill = array.Release[i].totalBill;
							
					}
	
					if(totalBill == "undefined"){
						totalBill = "";
					}	
					array_element += "<tr><td colspan='2' align='right'>"+
					"Total Bill Amount</td><td>"+
					+totalBill+"</td></tr>";
					array_element += "</tbody> </table>";
					document.getElementById("billDivID").innerHTML = array_element;
				}
			};
			xmlhttp.open("GET", "RetrieveBillValues?visitID="
					+ visitID, true);
			xmlhttp.send();
		}
	</script>
<!-- ENds -->


<%
	String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");

	if (lastEneteredVisitList == null || lastEneteredVisitList == "") {
		lastEneteredVisitList = "dummy";
	}

	String printAvailable = (String) request.getAttribute("printAvailable");

	if (printAvailable == null || printAvailable == "") {
		printAvailable = "dummy";
	}

	String printPrescAvailable = (String) request.getAttribute("printPrescAvailable");

	if (printPrescAvailable == null || printPrescAvailable == "") {
		printPrescAvailable = "dummy";
	}
%>

<!-- Retrieve tabs by practice ID -->

<%
	HashMap<String, String> ValueMap = new HashMap<String, String>();

	ValueMap.put("clinician", "OPD Visit,Prescription,Billing,Report,Medical Certificate,Referral Letter");
	ValueMap.put("billDesk", "Prescription,Billing");
	ValueMap.put("compounder", "Prescription,Billing");

	String tabs = ValueMap.get(form.getUserType());

	//String tabs = patientDAOInf.retrieveTabsByPracticeID(form.getVisitTypeID(), form.getUserType());

	HashMap<String, String> PVComplaints = patientDAOInf.getPVComplaints();
%>

<!-- Ends -->

<script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%//Adding div ID values which is to be associated to tabs
			HashMap<String, String> idMap = new HashMap<String, String>();

			idMap.put("OPD Visit", "visit");
			idMap.put("Prescription", "presc");
			idMap.put("Billing", "billing");
			idMap.put("Report", "labRep");
			idMap.put("Medical Certificate", "medCerti");
			idMap.put("Referral Letter", "refLetter");
			System.out.println("Inside id map2:");
			//Adding class active to the first value in tab String
			if (tabs.contains(",")) {

				String ID = tabs.split(",")[0];%>
		$("#<%=idMap.get(ID)%>").addClass('active in');
		
	<%} else {%>
		$("#<%=idMap.get(tabs)%>").addClass('active in');
		
	<%}%>
    
    });
   
    </script>


<%
	String medicalCertificateTabEnable = (String) request.getAttribute("medicalCertificateTabEnable");

	if (medicalCertificateTabEnable == null || medicalCertificateTabEnable == "") {
		medicalCertificateTabEnable = "disable";
	} else {
%>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#medCerti"]').tab('show');
			
			<%//Removing class active to the first value in tab String
				if (tabs.contains(",")) {
					String ID = tabs.split(",")[0];%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%} else {%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%}%>
	
	});
	</script>

<%
	}
%>


<%
	String referralLetterTabEnable = (String) request.getAttribute("referralLetterTabEnable");

	if (referralLetterTabEnable == null || referralLetterTabEnable == "") {
		referralLetterTabEnable = "disable";
	} else {
%>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#refLetter"]').tab('show');
			
			<%//Removing class active to the first value in tab String
				if (tabs.contains(",")) {
					String ID = tabs.split(",")[0];%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%} else {%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%}%>
	
	});
	</script>

<%
	}
%>



<%
	String prescTabCheck = (String) request.getAttribute("prescTabCheck");

	if (prescTabCheck == null || prescTabCheck == "") {
		prescTabCheck = "disable";
	} else {
%>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#presc"]').tab('show');
			
			<%//Removing class active to the first value in tab String
				if (tabs.contains(",")) {
					String ID = tabs.split(",")[0];%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%} else {%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%}%>
	
	});
	</script>

<%
	}
%>


<%
	String billingTabCheck = (String) request.getAttribute("billingTabCheck");

	if (billingTabCheck == null || billingTabCheck == "") {
		billingTabCheck = "disable";
	} else {
%>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#billing"]').tab('show');
			
			<%//Removing class active to the first value in tab String
				if (tabs.contains(",")) {
					String ID = tabs.split(",")[0];%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%} else {%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%}%>
	
	});
	</script>

<%
	}
%>

<%
	String labReportResult = (String) request.getAttribute("labReportResult");

	if (labReportResult == null || labReportResult == "") {
		labReportResult = "dummy";
	}

	String labReportTabEnable = (String) request.getAttribute("labReportTabEnable");

	if (labReportTabEnable == null || labReportTabEnable == "") {
		labReportTabEnable = "disable";
	} else {
%>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#labRep"]').tab('show');
			
			<%//Removing class active to the first value in tab String
				if (tabs.contains(",")) {
					String ID = tabs.split(",")[0];%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%} else {%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%}%>
	
	});
	
	
	</script>

<%
	}
%>

<%
	int apptID = (Integer) request.getAttribute("apptID");

	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();

	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
%>

<script type="text/javascript">
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
	</script>

<%
	String saveVisitcheck = (String) request.getAttribute("saveVisitcheck");
	if (saveVisitcheck == null || saveVisitcheck == "") {
		saveVisitcheck = "noSave";
	}
%>

<script type="text/javascript">
	
		function displayCategoryTypeDiv(category){
			
			if(category == "Govt"){
				
				$("#govtTypeID").show(500);
				
				$("#payeeNameID").hide(500);
				$("#otherDivID").hide(500);
				$("#otherTypeID").hide(500);
				
				$("#govtTypeID").attr("name","categoryType");
				
				$("#payeeNameID").removeAttr("name");
				$("#otherTypeID").removeAttr("name");
				
				var selectTag = "<select name='' class='form-control' id='otherTypeID' style='margin-top: 15px;'><option value=''>Select</option>"
					  		  + "</select>";
					  
				$("#otherTypeID").html(selectTag);
				
			}else if(category == "Payee"){
				
				$("#payeeNameID").show(500);
				$("#govtTypeID").hide(500);
				$("#otherDivID").hide(500);
				$("#otherTypeID").hide(500);
				
				$("#payeeNameID").attr("name","categoryType");
				
				$("#govtTypeID").removeAttr("name");
				$("#otherTypeID").removeAttr("name");
				
				
				var selectTag = "<select name='' class='form-control' id='otherTypeID' style='margin-top: 15px;'><option value=''>Select</option>"
			  		  + "</select>";
			  
				$("#otherTypeID").html(selectTag);
				
			}else{
				
				$("#otherDivID").show(500);
				$("#otherTypeID").show(500);
				
				$("#payeeNameID").hide(500);
				$("#govtTypeID").hide(500);
				
				$("#otherTypeID").attr("name","categoryType");
				
				$("#payeeNameID").removeAttr("name");
				$("#govtTypeID").removeAttr("name");
				
			}
			
		}
		
		function displayOtherTypeDiv(otherType){
				
			if(otherType == "Insurance"){
				
				var selectTag = "<select name='categoryType' class='form-control' id='otherTypeID' style='margin-top: 15px;'><option value=''>Select Insurance</option>"
							  + "<option value='Medi Assist'>Medi Assist</option>"
							  + "<option value='DHS'>DHS</option>"
							  + "<option value='Start Health'>Start Health</option>"
							  + "<option value='MD India'>MD India</option>"
							  + "<option value='Religare'>Religare</option>"
							  + "<option value='Universal Sampo'>Universal Sampo</option>"
							  + "<option value='TIK - Sigma'>TIK - Sigma</option></select>";
				
				$("#otherTypeID").html(selectTag);
				
				$("#otherTypeID").show();
				
			}else{
				
				var selectTag = "<select name='categoryType' class='form-control' id='otherTypeID' style='margin-top: 15px;'><option value=''>Select Company</option>"
					  + "<option value='JSW'>JSW</option>"
					  + "<option value='Jhonson'>Jhonson</option></select>";
					  
				$("#otherTypeID").html(selectTag);
				
				$("#otherTypeID").show();
				
			}
			
		}
		
		function enableFields(field1ID, field2ID, checkboxID){
			if($("#"+checkboxID).is(":checked")){
				$("#"+field1ID).removeAttr("readonly");
				$("#"+field2ID).removeAttr("readonly");
			}else{
				$("#"+field1ID).attr("readonly","readonly");
				$("#"+field2ID).attr("readonly","readonly");
			}
		}
		
		function showMurmurDiv(checkboxID, sysID, diaID){
			if($("#"+checkboxID).is(":checked")){
				$("#"+sysID).show(500);
				$("#"+diaID).show(500);
			}else{
				$("#"+sysID).val("");
				$("#"+diaID).val("");
				$("#"+sysID).hide(500);
				$("#"+diaID).hide(500);
			}
		}
		
		function showDiv(divID){
			$("#"+divID).show(500);
		}
		
		function checkRadioBtn(btnID){
			$("#"+btnID).click();
		}
		
		function showOtherDiv(checkboxID, divID, textareaID){
			if($("#"+checkboxID).is(":checked")){
				$("#"+textareaID).val("");
				$("#"+divID).show(500);
			}else{
				$("#"+textareaID).val("");
				$("#"+divID).show(500);
			}
		}
	
	</script>

<%
	List<String> complaintList = new ArrayList<String>();

	complaintList.add("Fever");
	complaintList.add("Headache");
	complaintList.add("Chest Pain");
	complaintList.add("Backache");
	complaintList.add("Joint Pain");
	complaintList.add("Weaknees");
	complaintList.add("Giddiness");

	String otherInsuranceList = "Medi Assist,DHS,Start Health,MD India,Religare,Universal Sampo,TIK - Sigma";

	String otherCompanyList = ("JSW,Jhonson");

	String systemStartDate = (String) request.getAttribute("systemStartDate");

	if (systemStartDate == null || systemStartDate == "") {
		systemStartDate = "NA";
	}
%>

<!-- Add other comlpaints -->

<script type="text/javascript">
		
		var otherComplaintsCounter = 1;
	
		function addOtherComplaints(otherComplaints){
			
			var trID = "otehrComplNewTRID"+otherComplaintsCounter;
			
			var trTag = "<tr id='"+trID+"' style='font-size:14px;'>"+
			"<td>"+otherComplaints+"<input type='hidden' name='otherComplaint' value='"+otherComplaints+"'></td>"+
			"<td><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDietFactTR(\"" + trID + "\");'/></td>"+
			"</tr>";

			$(trTag).insertAfter($('#complaintOtherTRID'));
			
			otherComplaintsCounter++;
			
			$('#complaintOtherID').val("");
			
		}
		
		//For removing TR
		function removeDietFactTR(trID){
        	if(confirm("Are you sure you want to remove this row?")){

            	$("#"+trID+"").remove();
        	}
    	}
	
	</script>

<script type="text/javascript">
	
		function calculateAge(userDate, userMonth, userYear, finalDateStoreID){
			
			var date = new Date();   // JS Engine will parse the string automagically
	
			var date21=date.getDate();
			var month = date.getMonth()+1;
			var year1=date.getFullYear();
			
			var userDOB = userDate + "-" + userMonth + "-" + userYear;
			
			$("#"+finalDateStoreID+"").val(userDOB);
	
		}
		
		var dateArray = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
		
		var monthArray = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "01", "02", "03", "04", "05", "06", "07", "08", "09");
		
		var yearArray = [];
	
	   var date = new Date();
	   
	   var year = date.getFullYear();
	   
	   var diff = parseInt(year) - 1900;
	    
	   var prevYear = 1900; 
	   
	    for(var i = 0; i < parseInt(diff); i++){
	    
	    	var a = i + 1;
	        
	        var newyear = prevYear+ a;
	    
	        yearArray.push(newyear);
	    }
	    
		function dateCheck(dateVal, monthVal, yearVal, fontID, nextFocusID, finalDateStoreID){
			
			if(isNaN(dateVal)){
				$("#"+fontID+"").html("Not valid input. Enter numbers only.");
			}else{
				
				if(dateVal.length == 2){
					
					if(dateArray.indexOf(dateVal) != -1){
			        	$("#"+nextFocusID+"").focus();
			        	$("#"+fontID+"").html("");
			        	calculateAge(dateVal, monthVal, yearVal, finalDateStoreID);
			        }else{
			        	$("#"+finalDateStoreID+"").val("");
			        	$("#"+fontID+"").html("Date value should not be more than 31.");
			        }
					
				}
				
			}
		}
		
		function monthCheck(dateVal, monthVal, yearVal, monthFontID, nextFocusID, finalDateStoreID){
			
			if(isNaN(monthVal)){
				$("#"+monthFontID+"").html("Not valid input. Enter numbers only.");
			}else{
				
				if(monthVal.length == 2){
					
					if(monthArray.indexOf(monthVal) != -1){
			        	$("#"+nextFocusID+"").focus();
			        	$("#"+monthFontID+"").html("");
			        	calculateAge(dateVal, monthVal, yearVal, finalDateStoreID);
			        }else{
			        	$("#"+finalDateStoreID+"").val("");
			        	$("#"+monthFontID+"").html("Month value should not be more than 12.");
			        }
					
				}
				
			}
			
		}
		
		function yearCheck(yearVal, monthVal, dateVal, fontID, finalDateStoreID){
			
			if(yearVal == ""){
				
				$("#"+finalDateStoreID+"").val("");
				
			}else{
				
				if(isNaN(yearVal)){
					$("#"+fontID+"").html("Not valid input. Enter numbers only.");
				}else{ 
					
					if(yearVal.length == 4){
						
						if(yearArray.indexOf(parseInt(yearVal)) != -1){
							calculateAge(dateVal, monthVal, yearVal, finalDateStoreID);
							$("#"+fontID+"").html("");
				        }else{
				        	$("#"+finalDateStoreID+"").val("");
				        	$("#"+fontID+"").html("Year value should be between 1900 to current year.");
				        }
						
					}
					
				}
				
			}
			
		}
	</script>

<style type="text/css">
.multiselect-container {
	overflow: auto;
	height: 540%;
}

.container {
	overflow: hidden;
}
</style>

<!-- Multiselect checkbox dropdown list -->

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
		$('#ComplaintsID').multiselect({
	     	includeSelectAllOption: true
	 	});
	   
	 });

	function maxAllowedMultiselect(obj) {
		
		//alert('hiii');
		var val = jQuery('#'+obj.id).val();
		//alert(val);
	    var selectedOptions = jQuery('#'+obj.id+" option[value!=\'\']:selected");
	    
	    /* if (selectedOptions.length >= maxAllowedCount) {
	        
	    	 if (selectedOptions.length > maxAllowedCount) {
	            selectedOptions.each(function(i) {
	                if (i >= maxAllowedCount) {
	                    jQuery(this).prop("selected",false);
	                }
	            });
	        } 
	        
	        jQuery('#'+obj.id+' option[value!=\'\']').not(':selected').prop("disabled",true);
	    } else {
	        jQuery('#'+obj.id+' option[value!=\'\']').prop("disabled",false);
	    } */
	   storeValues('ComplaintsID1','ComplaintsID');
	}
	
	
	function storeValues(hiddenInputID, selectID){
	    var finalValue = "";
	    $("#"+selectID+" option:selected").each(function(){
	    		finalValue += "," + $(this).val();
	    			
	    });
	    		
	    if(finalValue.startsWith(",")){
	    	finalValue = finalValue.substr(1);
	    }
	    		
	   	$("#"+hiddenInputID).val(finalValue);
	  }

</script>

<!-- Ends -->

<%
	String complaintList1 = (String) request.getAttribute("complaintList");
	if (complaintList1 == null || complaintList1 == "") {
		complaintList1 = "";
	}

	String categoryList1 = (String) request.getAttribute("categoryList");
	if (categoryList1 == null || categoryList1 == "") {
		categoryList1 = "";
	}
	System.out.println("Inside complaint liost11:" + categoryList1);
	String[] categoryVal = categoryList1.split(",");
	System.out.println("Inside complaint LIST22:");
	String categoryVal1 = "";

	if (categoryVal.length == 1) {
		categoryVal1 = "";
	} else {
		categoryVal1 = categoryVal[1];
	}

	List<PatientForm> MedicalHistory = (List<PatientForm>) request.getAttribute("MedicalHistory");
	List<PatientForm> FamilyHistory = (List<PatientForm>) request.getAttribute("FamilyHistory");
	List<PatientForm> PersonalHistory = (List<PatientForm>) request.getAttribute("PersonalHistory");
	List<PatientForm> onEaminationList = (List<PatientForm>) request.getAttribute("onEaminationList");
	List<PrescriptionManagementForm> OPDChargesList = (List<PrescriptionManagementForm>) request
			.getAttribute("OPDChargesList");

	List<PrescriptionManagementForm> LabTestList = (List<PrescriptionManagementForm>) request
			.getAttribute("LabTestList");

	String InvestigationDetails = (String) request.getAttribute("InvestigationDetails");
	if (InvestigationDetails == null || InvestigationDetails == "") {
		InvestigationDetails = "";
	}
	System.out.println("InvestigationDetails value is: " + InvestigationDetails);
	String ChargesDetails = (String) request.getAttribute("ChargesDetails");
	if (ChargesDetails == null || ChargesDetails == "") {
		ChargesDetails = "";
	}
%>


<script type="text/javascript">
		function ViewLabReport(report){
			
			var queryString = "";
			queryString += "?reportsID="+report;
			console.log(queryString);
			
			popup = window.open('GeneralHospitalLabReportView'+queryString,"Popup", "width=700,height=700");
			
		}
		
		function downloadLabReport(report){
			
			var queryString = "";
			queryString += "?reportsID="+report;
			
			document.forms["labRepForm"].action = 'GeneralHospitalLabReportDownload'+queryString;
			document.forms["labRepForm"].submit();
		}
		
		// Delete lab report
		
		function deleteReportRow(trID, labReportID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteLabReport(trID, labReportID);
			}
		}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteLabReport(trID, labReportID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = 0;
					var excptnMsg = "";
	
					for ( var i = 0; i < array.Release.length; i++) {
					
						excptnMsg = array.Release[i].ExceptionMessage;
						check = array.Release[i].check;
	
					}
					
					if(check == 0){
						document.getElementById("exceptionMSG").innerHTML = excptnMsg;
					}else{
						document.getElementById("successMSG").innerHTML = "Report deleted successfully.";
						$("#"+trID).remove();
					}
					
				}
			};
			xmlhttp.open("GET", "DeleteLabReport?reportsID="+ labReportID , true);
			xmlhttp.send();
		}
		
		function disableSubmitBtn(btnID){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
			
			$('a#profPicID').remove();	//To disable profile pic at the right top corner
			$('a#edhanLinkID').remove(); 	//To disable edhan link given on top of left menu
			$('a#dashboardID').remove();	//To disable leftmenu options dashboard
			$('a#pmID').remove();	$('a#pmID1').remove();	$('a#pmID2').remove();	$('a#pmID3').remove();	$('a#pmID4').remove();	//To disable leftmenu options patient manag
			$('a#ivntID').remove();	$('a#ivntID1').remove();	$('a#ivntID2').remove();	$('a#ivntID3').remove();	$('a#ivntID4').remove();	$('a#ivntID5').remove();	//To disable leftmenu options invt mang
			$('a#ivntID6').remove();	$('a#ivntID7').remove();	$('a#ivntID8').remove();	$('a#ivntID9').remove();	$('a#ivntID10').remove();	$('a#ivntID11').remove();	//To disable leftmenu options invt mang
			$('a#ivntID12').remove();	$('a#ivntID13').remove();  $('a#ivntID14').remove();	$('a#ivntID15').remove();	$('a#ivntID16').remove();	$('a#ivntID17').remove();	//To disable leftmenu options invt mang
			$('a#ivntID18').remove();	$('a#ivntID19').remove();	//To disable leftmenu options invt mang
			$('a#admID').remove();	$('a#admID1').remove();	$('a#admID2').remove();	$('a#admID3').remove();	$('a#admID4').remove();	$('a#admID5').remove();	//To disable leftmenu options adminstr
			$('a#admID6').remove();	$('a#admID7').remove();	$('a#admID8').remove();	$('a#admID9').remove();	$('a#admID10').remove();	//To disable leftmenu options adminstr
			$('a#repID').remove();	$('a#repID1').remove();	$('a#repID2').remove();	$('a#repID3').remove();	$('a#repID4').remove();  $('a#repID5').remove();	//To disable leftmenu options Reports
			$('a#repID6').remove();	$('a#repID7').remove();	$('a#repID8').remove();	$('a#repID9').remove();	$('a#repID10').remove();  $('a#repID11').remove();	//To disable leftmenu options Reports
			$('a#prID').remove();	$('a#prID1').remove();	$('a#prID2').remove();	//To disable leftmenu options Participant mng
			$("#"+btnID).attr('disabled',true);
			return true;
		}
		
	</script>

<!-- Change balance payment -->

<script type="text/javascript">
	
		function changeBalancePayment(advPayment, netAmount){
			
			if(advPayment == ""){
				advPayment = 0;
			}
			
			var balPayment = parseFloat(netAmount) - parseFloat(advPayment);
			
			if(balPayment.toString().includes(".")){
				var array = balPayment.toString().split(".");
				
				if(array[1].length > 2 ){
					balPayment = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			$("#balPaymentID").val(balPayment);
			
		}
		
		/* checkebox check uncheck event cal */
		function changeTotalAmtByQuantyRate(checkBoxID, quantity, rate, amount, totalAmt, chargeID, chargeType){
			
			var changeQty = $("#"+quantity).val();
			var changeRate = $("#"+rate).val();
			var changeAmt = $("#"+amount).val();
			var changeTAmt = $("#"+totalAmt).val();
			
			console.log("changeTAmt: "+changeTAmt);
			
			if($("#"+checkBoxID).is(":checked")){
				
				 $("#"+chargeID).attr("name","chargeID");
				 $("#"+quantity).attr("name","chargeQuantity");
				 $("#"+rate).attr("name","chargeRate");
				 $("#"+amount).attr("name","chargeAmount");
				 $("#"+chargeType).attr("name","chargeType"); 
				 
				var finalAmount = changeQty * changeRate;
				
				$("#"+amount).val(finalAmount);
			
				var finaltotalAmt = parseFloat(parseFloat(changeTAmt) + parseFloat(finalAmount));
				
				$("#"+totalAmt).val(finaltotalAmt);
				$("#netAmountID").val(finaltotalAmt);
				console.log("totalamt if: "+changeQty);
				
			}else if($("#"+checkBoxID).not(":checked")){
				
				 $("#"+chargeID).removeAttr("name","chargeID");
				 $("#"+quantity).removeAttr("name","chargeQuantity");
				 $("#"+rate).removeAttr("name","chargeRate");
				 $("#"+amount).removeAttr("name","chargeAmount");
				 $("#"+chargeType).removeAttr("name","chargeType");
				 
				$("#"+amount).val(0);
				
				var finaltotalAmt =  parseFloat(parseFloat(changeTAmt) - parseFloat(changeAmt)) ;
				
				$("#"+totalAmt).val(finaltotalAmt);
				$("#netAmountID").val(finaltotalAmt);
				console.log("totalamt else: "+changeQty);
				
			}
			
		}
		
		/* checkebox check uncheck event cal */
		function changeChargesAmount(checkBoxID, quantity, rate, amount, totalAmt, consultationCharge){
			
			var finaltotalAmt=0;
			var finalAmount;
			
			var changeQty = $("#"+quantity).val();
			var changeRate = $("#"+rate).val();
			var changeAmt = $("#"+amount).val();
			var changeTAmt = $("#"+totalAmt).val();
			
			if($("#"+checkBoxID).is(":checked")){
				
				console.log("checked : ");
				
				finalAmount = changeQty * changeRate;
				
				$("#"+amount).val(finalAmount);
			
				//for single med quantity calculation
				$("input[name='chargeAmount']").each(function(){
					
					var currVal = $(this).val();
					
					console.log("currVal : "+currVal);
					
					finaltotalAmt = parseFloat(parseFloat(finaltotalAmt) + parseFloat(currVal));
					
				});
				
				var finalAmt = parseFloat(parseFloat(finaltotalAmt) + parseFloat(consultationCharge));
				
				$("#"+totalAmt).val(finalAmt);
				
				$("#netAmountID").val(finalAmt);
				
				console.log("totalamt if: "+changeQty);
			}
			
		}
		
		
		function changeNetAmtByDiscount(discountAmt, totalAmt){
			if(discountAmt == ""){
				discountAmt = 0;
			}
			
			if(totalAmt == ""){
				totalAmt = 0;
			}
		
			var netAmount = parseFloat(parseFloat(totalAmt) - parseFloat(discountAmt));
			
			if(netAmount.toString().includes(".")){
				var array = netAmount.toString().split(".");
				
				if(array[1].length > 2 ){
					netAmount = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			$("#netAmountID").val(netAmount);
			$("#advPaymentID").val(netAmount);
		}
		
	</script>

<!-- Ends -->

<!-- Function to change the cash to return to customer -->

<script type="text/javascript">
	
		function changeCashToReturn(cashPaid, netAmount){
			
			if(cashPaid == ""){
				cashPaid = 0;
			}
			
			var cashTOReturn = parseFloat(cashPaid) - parseFloat(netAmount);
			
			if(cashTOReturn.toString().includes(".")){
				var array = cashTOReturn.toString().split(".");
				
				if(array[1].length > 2 ){
					cashTOReturn = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			$("#cashToReturnID").val(cashTOReturn);
			
		}
		
	</script>

<!-- Ends -->

<!-- Display and hide cheque details div -->

<script type="text/javascript">
    
    	var paymentCounter = 1;
    
		function displayChequeDetailsDiv(input){
			
			var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength1: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$('#chequeDetailID').show(1000);
    			
    			$("#chequeNoID").attr("required", "required");
    			$("#chequeBankNameID").attr("required", "required");
    			$("#chequeBankBranchID").attr("required", "required");
    			$("#chequeAmtID").attr("required", "required");
    			
    		}else{
    			$('#chequeDetailID').hide(1000);
    			
    			$("#chequeNoID").removeAttr("required");
    			$("#chequeBankNameID").removeAttr("required");
    			$("#chequeBankBranchID").removeAttr("required");
    			$("#chequeAmtID").removeAttr("required");
    			
    			$("#chequeIssuedByID").val("");
    			$("#chequeNoID").val("");
    			$("#chequeBankNameID").val("");
    			$("#chequeBankBranchID").val("");
    			$("#single_cal311").val("");
    			$("#chequeAmtID").val("");
    			
    		}

    	}

		function hideChequeDetailsDiv(input){
	  		
    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength1: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			console.log("cash checked");
    			$("#cashDetailID").show(1000);
    			
    			$("#cashPaidID").attr("required", "required");
    			
    		}else{
    			console.log("cash not checked");
    			$("#cashDetailID").hide(1000);
    			
    			$("#cashPaidID").removeAttr("required");
    			
    			$("#cashPaidID").val("");
    			$("#cashToReturnID").val("");
    			
    		}

    	}
    	
		function displayCardDiv(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength1: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$('#cardDetailID').show(1000);
    			
    		}else{
    			$('#cardDetailID').hide(1000);
    			
    			$("#cardMobileNoID").val("");
    			$("#cMobileNoID").val("");
    			
    		}
    		
    	}
    	
		function displayChequeDetailsDivCreditNote(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength1: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$("#creditNoteDetailID").show(1000);
    			
    		}else{
    			$("#creditNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalID").val("");
    			
    		}
    		
        	paymentCounter++;

    	}
    	
		function displayChequeDetailsDivOther(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength1: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$("#otherDetailID").show(1000);
    			    			
    		}else{
    			$("#otherDetailID").hide(1000);
    			
    			$("#otherTypeID").val("");
    			$("#otherTypeAmountID").val("");
    			
    			}
    		
    	}
    	
    	//For dues pending modal
		function displayDuesChequeDetailsDiv(input){
    		
    		if($(input).is(":checked")){
    			$('#chequeDuesDetailID').show(1000);
    		}else{
    			$('#chequeDuesDetailID').hide(1000);
    			
    			$("#chequeIssuedByDuesID").val("");
    			$("#chequeDuesNoID").val("");
    			$("#chequeBankNameDuesID").val("");
    			$("#chequeBankBranchDuesID").val("");
    			$("#single_cal3111").val("");
    			$("#chequeAmtDuesID").val("");
    		}

    	}

    	function hideDuesChequeDetailsDiv(input){
    		
    		if($(input).is(":checked")){
    			$("#cashDuesDetailID").show(1000);
    		}else{
    			$("#cashDuesDetailID").hide(1000);
    			
    			$("#cashPaidDuesID").val("");
    			$("#cashToReturnDuesID").val("");
    		}

    	}
    	
    	function displayDuesCardDiv(input){
    		
    		if($(input).is(":checked")){
    			$('#cardDuesDetailID').show(1000);
    		}else{
    			$('#cardDuesDetailID').hide(1000);
    			
    			$("#cardMobileNoDuesID").val("");
    			$("#cardAmountDuesID").val("");
    			$("#cMobileNoDuesID").val("");
    		}
    		
    	}
    	
    	function displayDuesChequeDetailsDivCreditNote(input){

    		if($(input).is(":checked")){
    			$("#creditDuesNoteDetailID").show(1000);
    		}else{
    			$("#creditDuesNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalDuesID").val("");
    		}
    		
    	}
    	
    	function displayDuesChequeDetailsDivOther(input){

    		if($(input).is(":checked")){
    			$("#otherDuesDetailID").show(1000);
    		}else{
    			$("#otherDuesDetailID").hide(1000);
    			
    			$("#otherTypeDuesID").val("");
    			$("#otherTypeDuesAmountID").val("");
    		}
    		
    	}
    	
    </script>

<!-- Ends -->

<%
	String paymentType = (String) request.getAttribute("paymentType");

	HashMap<String, String> paymentMap = new HashMap<String, String>();

	paymentMap.put("Cash", "paymentTypeCashID=cashDetailID");
	paymentMap.put("Cheque", "paymentTypeChequeID=chequeDetailID");
	paymentMap.put("Credit/Debit Card", "paymentTypeCardID=cardDetailID");
	paymentMap.put("Other", "paymentTypeOtherID=otherDetailID");

	if (paymentType == null || paymentType == "") {
		paymentType = "dummy";
	} else {

		if (paymentType.contains(",")) {

			String[] arr = paymentType.split(",");

			for (int i = 0; i < arr.length; i++) {

				String idValues = paymentMap.get(arr[i].trim());

				//splitting idValues by = in order to get checkbox ID and div ID seperately
				String[] arr1 = idValues.split("=");

				String checkboxID = arr1[0];
				String checkboxDivID = arr1[1];
%>
<script type="text/javascript">
				    
				    	document.addEventListener('DOMContentLoaded', function() {
				    		
				    		$("#<%=checkboxID%>").attr("checked", true);
				    		
				    		$("#<%=checkboxDivID%>").show();
				    		
				    	});
				    
				    </script>
<%
	}
		} else {

			String idValues = paymentMap.get(paymentType);

			//splitting idValues by = in order to get checkbox ID and div ID seperately
			String[] arr1 = idValues.split("=");

			String checkboxID = arr1[0];
			String checkboxDivID = arr1[1];
%>
<script type="text/javascript">
			    
			    	document.addEventListener('DOMContentLoaded', function() {
			    		
			    		$("#<%=checkboxID%>").attr("checked", true);
			    		
			    		$("#<%=checkboxDivID%>").show();
			    	});
			    
			    </script>
<%
	}
	}
%>


<script type="text/javascript">
		function checkDiagnosis(){
						
			var autoSelecter = dojo.widget.byId('ProvisionalDiagnosisID');
    		
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Provisional Diagnosis Not Selected. Please select Provisional Diagnosis");
				return false;
			}else{
				$('html, body').animate({
			        scrollTop: $('body').offset().top
			    }, 1000);
				
				$('body').css('background', '#FCFAF5');
				$(".container").css("opacity","0.2");
				$("#loader").show();
				$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
			    			
    			$('a#profPicID').remove();	//To disable profile pic at the right top corner
    			$('a#edhanLinkID').remove(); 	//To disable edhan link given on top of left menu
    			$('a#dashboardID').remove();	//To disable leftmenu options dashboard
    			$('a#pmID').remove();	$('a#pmID1').remove();	$('a#pmID2').remove();	$('a#pmID3').remove();	$('a#pmID4').remove();	//To disable leftmenu options patient manag
    			$('a#ivntID').remove();	$('a#ivntID1').remove();	$('a#ivntID2').remove();	$('a#ivntID3').remove();	$('a#ivntID4').remove();	$('a#ivntID5').remove();	//To disable leftmenu options invt mang
    			$('a#ivntID6').remove();	$('a#ivntID7').remove();	$('a#ivntID8').remove();	$('a#ivntID9').remove();	$('a#ivntID10').remove();	$('a#ivntID11').remove();	//To disable leftmenu options invt mang
    			$('a#ivntID12').remove();	$('a#ivntID13').remove();  $('a#ivntID14').remove();	$('a#ivntID15').remove();	$('a#ivntID16').remove();	$('a#ivntID17').remove();	//To disable leftmenu options invt mang
    			$('a#ivntID18').remove();	$('a#ivntID19').remove();	//To disable leftmenu options invt mang
    			$('a#admID').remove();	$('a#admID1').remove();	$('a#admID2').remove();	$('a#admID3').remove();	$('a#admID4').remove();	$('a#admID5').remove();	//To disable leftmenu options adminstr
    			$('a#admID6').remove();	$('a#admID7').remove();	$('a#admID8').remove();	$('a#admID9').remove();	$('a#admID10').remove();	//To disable leftmenu options adminstr
    			$('a#repID').remove();	$('a#repID1').remove();	$('a#repID2').remove();	$('a#repID3').remove();	$('a#repID4').remove();  $('a#repID5').remove();	//To disable leftmenu options Reports
    			$('a#repID6').remove();	$('a#repID7').remove();	$('a#repID8').remove();	$('a#repID9').remove();	$('a#repID10').remove();  $('a#repID11').remove();	//To disable leftmenu options Reports
    			$('a#prID').remove();	$('a#prID1').remove();	$('a#prID2').remove();	//To disable leftmenu options Participant mng
				$("#visitrForm").attr("action","AddNewGeneralHospitalVisit");
			  	  $("#visitrForm").submit(); 
			  	  
				return true;
			}
		}
	</script>

<%
	String pdfOutFIleName1 = (String) request.getAttribute("PDFOutFileName");
	System.out.println("IPD PDF out file path ::: " + pdfOutFIleName1);

	if (pdfOutFIleName1 == null || pdfOutFIleName1 == "") {

		pdfOutFIleName1 = "dummy";

	} else {

		if (pdfOutFIleName1.contains("\\")) {

			pdfOutFIleName1 = pdfOutFIleName1.replaceAll("\\\\", "/");
		}
	}

	if (pdfOutFIleName1 != "dummy") {
%>
<script type="text/javascript">
		
		  document.addEventListener('DOMContentLoaded', function() {
			  OPDPdfAction();
		  });
		  
		</script>
<%
	}
%>

<script type="text/javascript">
		
		var popup;
		function OPDPdfAction(){
			popup = window.open('PDFDownload?pdfOutPath=<%=pdfOutFIleName1%>',"Popup", "width=700,height=700");
			popup.focus();
			popup.print();
		}
	</script>


<sx:head />
</head>

<body class="nav-md">
	<img src="images/Preloader_2.gif" alt="Loading Icon"
		class="loadingImage" id="loader">

	<!-- Register open alert modal -->

	<div id="registerErrorModal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body">

					<center>
						<h4 style="color: black;">Leave register is not opened for
							this year. Kindly contact administrator.</h4>
					</center>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->

	<!-- Logout modal -->

	<div id="logoutModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">LOGOUT</h4>
				</div>

				<div class="modal-body">

					<center>
						<h4 style="color: black;">You will lose any unsaved data. Are
							you sure you want to log out?</h4>
					</center>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
						<a href="Logout"><button type="button" style="width: 25%"
								class="btn btn-success">OK</button></a>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->

	<!-- Lock modal -->

	<div id="lockModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">SCREEN LOCKED</h4>
				</div>

				<div class="modal-body">

					<center style="margin-bottom: 20px;">
						<font style="color: black; font-size: 16px;">Enter your PIN
							to onlock the screen</font>
					</center>

					<div class="row">
						<div class="col-md-10 col-sm-10 col-xs-8">
							<input type="password" name="lockPIN" id="lockPIN"
								onkeyup="checkUnlockPIN(lockPIN.value);" class="form-control"
								placeholder="Enter your PIN here">
						</div>
						<div class="col-md-2 col-sm-2 col-xs-4" id="lockImgID"
							style="margin-left: -15px;"></div>
					</div>

				</div>
				<div class="modal-footer">
					<center>
						<a href="Logout?userID=<%=userID%>"><button type="submit"
								style="width: 25%" class="btn btn-primary">Logout</button></a>
						<button type="button" id="lockButtonID" class="btn btn-primary"
							data-dismiss="modal" style="display: none;"></button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->

	<!-- Security Credential modal -->

	<div id="securityModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">CHANGE SECURITY
						CREDENTIALS</h4>
				</div>

				<div class="modal-body">

					<!-- start accordion -->
					<div class="accordion" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel">
							<a class="panel-heading collapsed" role="tab" id="headingTwo"
								data-toggle="collapse" data-parent="#accordion"
								href="#collapseTwo" aria-expanded="false"
								aria-controls="collapseTwo">
								<h4 class="panel-title">Change Password</h4>
							</a>
							<div id="collapseTwo" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingTwo">
								<div class="panel-body">
									<div class="row" style="margin-bottom: 15px;">
										<div class="col-md-4">Old Password</div>
										<div class="col-md-7">
											<input type="password" name="oldPassword" id="oldPassword"
												class="form-control"
												onkeyup="verifyOldPass(oldPassword.value);">
										</div>
										<div class="col-md-1" id="oldPassID"></div>
									</div>

									<div class="row" style="margin-bottom: 15px;">
										<div class="col-md-4">New Password</div>
										<div class="col-md-7">
											<input type="password" name="newPassword" id="newPassID"
												disabled="disabled" onkeyup="newPassCheck(newPassID.value);"
												class="form-control">
										</div>
										<div class="col-md-1" id="newPassCheckID"></div>
									</div>

									<div class="row" style="margin-bottom: 15px;">
										<div class="col-md-4">Confirm New Password</div>
										<div class="col-md-7">
											<input type="password" name="confirmNewPassword"
												id="confirmNewPassID" disabled="disabled"
												onkeyup="checkConfirmNewPass(confirmNewPassID.value,newPassID.value);"
												class="form-control">
										</div>
										<div class="col-md-1" id="confirmPassID"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel">
							<a class="panel-heading collapsed" role="tab" id="headingThree"
								data-toggle="collapse" data-parent="#accordion"
								href="#collapseThree" aria-expanded="false"
								aria-controls="collapseThree">
								<h4 class="panel-title">Change PIN</h4>
							</a>
							<div id="collapseThree" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingThree">
								<div class="panel-body">
									<div class="row" style="margin-bottom: 15px;">

										<div class="col-md-4">New PIN</div>
										<div class="col-md-7">
											<input type="password" name="newPIN" id="newPINID"
												class="form-control">
										</div>

									</div>

									<div class="row" style="margin-bottom: 15px;">

										<div class="col-md-4">Confirm New PIN</div>
										<div class="col-md-7">
											<input type="password" name="confirmNewPIN"
												id="confirmNewPINID"
												onkeyup="checkConfirmNewPIN(confirmNewPINID.value,newPINID.value);"
												class="form-control">
										</div>
										<div class="col-md-1" id="confirmPINID"></div>

									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- end of accordion -->

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" data-dismiss="modal" id="secCredCancelID"
							class="btn btn-primary">Cancel</button>
						<button type="button" style="width: 25%" id="securityBtnID"
							onclick="updateSecurityCredentials(newPassID.value, newPINID.value);"
							class="btn btn-success" disabled="disabled" data-dismiss="modal">Update</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->

	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<!-- /menu profile quick info -->

					<br />

					<!-- Including leftMenu jsp -->
					<jsp:include page="leftMenu.jsp"></jsp:include>
					<!-- Ends -->

					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" style="color: #73879C;"
							data-placement="top" title="Security Credentials"
							onclick="showSecurityModal();"> <span
							class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" style="color: #73879C;"
							data-placement="top" title="FullScreen"
							onclick="widnwoFullScreen();"> <span
							class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" style="color: #73879C;"
							data-placement="top" title="Lock" onclick="showLockModal();">
							<span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" style="color: #73879C;"
							data-placement="top" title="Logout" onclick="showLogoutModal();">
							<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
						</a>
					</div>
					<!-- /menu footer buttons -->

				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">
				<div class="nav_menu">
					<nav>
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>

						<ul class="nav navbar-nav navbar-right">
							<li class=""><a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								id="profPicID" aria-expanded="false"> <%
 	if (daoInf.retrieveProfilePic(form.getUserID()) == null
 			|| daoInf.retrieveProfilePic(form.getUserID()).isEmpty()) {
 		System.out.println("Inside prof pic:");
 %> <img src="images/user.png" alt="Profile Pic"><%=form.getFullName()%>

									<%
										} else {
											S3ObjectInputStream s3ObjectInputStream = s3
													.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath,
															daoInf.retrieveProfilePic(form.getUserID())))
													.getObjectContent();

											IOUtils.copy(s3ObjectInputStream, new FileOutputStream(
													new File(realPath + "images/" + daoInf.retrieveProfilePic(form.getUserID()))));
									%> <img
									src="<%="images/" + daoInf.retrieveProfilePic(form.getUserID())%>"
									alt="Profile Pic"><%=form.getFullName()%> <%
 	}
 %> <span class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="RenderEditProfile"
										onclick="showLoadingImg();"> Profile</a></li>
									<!--<li><a href="DownloadManual"> Help</a></li>-->
									<li><a href="Logout"><i
											class="fa fa-sign-out pull-right"></i>Log Out</a></li>
								</ul></li>
						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>

					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="page-title">
									<div class="title_left">
										<h3>ADD NEW VISIT</h3>
									</div>
								</div>

								<div class="ln_solid" style="margin-top: 0px;"></div>
								<div class="x_content span_11">

									<div>
										<s:if test="hasActionErrors()">
											<center>
												<font style="color: red; font-size: 16px;"><s:actionerror /></font>
											</center>
										</s:if>
										<s:else>
											<center>
												<font style="color: green; font-size: 16px;"><s:actionmessage /></font>
											</center>
										</s:else>

										<font
											style="color: green; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="successMSG"></center> </font> <font id=""
											style="color: red; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="errorMSG"></center></font> <font id=""
											style="color: red; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="exceptionMSG"></center></font>

									</div>


									<s:iterator value="patientList" var="patientVat">

										<h4 style="">
											<b>Visit For <s:property value="firstName" />&nbsp;<s:property
													value="lastName" />&nbsp;(<s:property
													value="registrationNo" />)
											</b>
										</h4>

										<div class="ln_solid"></div>

										<ul id="myTab1" class="nav nav-tabs">


											<%
												//Check whether tabs string contains comma (,), if yes
													//split tabs string by , and create tabs else create only 
													//one tab
													if (tabs.contains(",")) {

														int count = 1;

														String[] tabsArray = tabs.split(",");

														for (int i = 0; i < tabsArray.length; i++) {

															if (count == 1) {
																count++;
											%>

											<li class="active"><a
												href="#<%=idMap.get(tabsArray[i])%>" data-toggle="tab"><%=tabsArray[i]%></a></li>

											<%
												} else {
											%>

											<li><a href="#<%=idMap.get(tabsArray[i])%>"
												data-toggle="tab"><%=tabsArray[i]%></a></li>

											<%
												}

														}

													} else {

													}
											%>

										</ul>


										<div id="myTabContent1" class="tab-content">


											<!-- Visit div -->
											<div class="tab-pane fade in active" id="visit">
												<form class="form-horizontal form-label-left"
													action="AddNewGeneralHospitalVisit" name="visitrForm"
													id="visitrForm" method="POST" style="margin-top: 20px;">

													<input type="hidden" name="patientID" id="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="aptID" id="aptID" value="<%=apptID%>">
													<input type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>"> <input
														type="hidden" name="startDate"
														value="<%=systemStartDate%>">

													<div class="row" style="margin-top: 15px;">
														<center>
															<h4>
																<b>CASE REPORT FORM</b>
															</h4>
														</center>
													</div>


													<div class="row" style="margin-top: 15px;">
														<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
															<table border="1" width="100%">

																<tr>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Name:</td>
																	<td colspan="2"><input type="text"
																		style="border: none; cursor: not-allowed; background: transparent;"
																		class="form-control" readonly="readonly"
																		value="<s:property value="lastName"/> <s:property value="firstName"/> <s:property value="middleName"/>"
																		name="" placeholder="Patient Name" autofocus=""></td>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Date of first visit:</td>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px;"><input
																		type="text" class="form-control" name=""
																		style="border: none; cursor: not-allowed; background: transparent;"
																		readonly="readonly"
																		value="<s:property value="visitDate"/>"
																		aria-describedby="inputSuccess2Status3"></td>
																	<!-- <td colspan="1" style="font-size:14px; font-weight: bold;text-align: center;"> Profile Pic </td> -->
																</tr>

																<tr>
																	<%-- <td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> DOB: </td>
							    				<td><input type="text" class="form-control" name="dateOfBirth" id="" readonly="readonly" style="border:none;cursor:not-allowed;background: transparent;" value="<s:property value="dateOfBirth"/>" aria-describedby="inputSuccess2Status3"></td> --%>
																	<td
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">Age:
																	</td>
																	<td><input type="number"
																		style="border: none; background: transparent; cursor: not-allowed;"
																		readonly="readonly" class="form-control" name="age"
																		value="<s:property value="age"/>" placeholder="Age"
																		autofocus=""></td>
																	<td
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">Sex:
																	</td>
																	<td colspan="3"><input type="text" name="gender"
																		style="border: none; cursor: not-allowed; background: transparent;"
																		class="form-control" readonly="readonly"
																		value="<s:property value="gender"/>"
																		placeholder="Gender" autofocus=""></td>

																</tr>

																<tr>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Address:</td>
																	<td colspan="4"><input type="text"
																		class="form-control"
																		style="border: none; cursor: not-allowed; background: transparent;"
																		value="<s:property value="address"/>"
																		readonly="readonly" name="address" placeholder=""
																		autofocus=""></td>
																</tr>

																<tr>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Phone Number:</td>
																	<td colspan="1"><input type="number" name="mobile"
																		style="border: none; background: transparent; cursor: not-allowed;"
																		readonly="readonly" class="form-control"
																		value="<s:property value="mobile"/>" autofocus=""
																		onKeyPress="if(this.value.length==10) return false;"
																		aria-describedby="inputGroupSuccess1Status"></td>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Alternate Number:</td>
																	<td colspan="2"><input type="number" name="phone"
																		style="border: none; background: transparent; cursor: not-allowed;"
																		readonly="readonly" class="form-control"
																		value="<s:property value="phone"/>" autofocus=""
																		onKeyPress="if(this.value.length==10) return false;"
																		aria-describedby="inputGroupSuccess1Status"></td>
																</tr>

																<tr>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Occupation:</td>
																	<td colspan="4"><input type="text"
																		name="occupation"
																		style="border: none; background: transparent; cursor: not-allowed;"
																		readonly="readonly" class="form-control"
																		value="<s:property value="occupation"/>" autofocus=""></td>

																</tr>

															</table>
														</div>
													</div>

													<div class="ln_solid"></div>

													<div class="row">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Medical
																registration number: </font>
														</div>

														<div class="col-md-3">
															<input class="form-control" name="registrationNo"
																readonly="readonly"
																value="<s:property value="registrationNo"/>"
																placeholder="Medical registration number">
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<%
															if (saveVisitcheck.equals("Saved")) {
														%>

														<div class="col-md-3 col-xs-4">
															<font style="font-size: 14px; font-weight: bold;">Visit
																Date (DD/MM/YYYY): </font>
														</div>

														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitDay"
																value="<s:property value="visitDay"/>" id="visitDayID"
																placeholder="Day" maxlength="2"
																onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
															<font style="color: red;" id="dateFontID"></font>
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitMonth"
																value="<s:property value="visitMonth"/>"
																id="visitMonthID" placeholder="Month" maxlength="2"
																onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
															<font style="color: red;" id="monthFontID"></font>
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitYear"
																value="<s:property value="visitYear"/>" id="visitYearID"
																placeholder="Year" maxlength="4"
																onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
															<font style="color: red;" id="yearFontID"></font>
														</div>

														<input type="hidden" class="form-control" name="visitDate"
															value="<s:property value="firstVisitDate"/>"
															id="finalDateID">

														<%
															} else {
														%>
														<div class="col-md-3 col-xs-4">
															<font style="font-size: 14px; font-weight: bold;">Visit
																Date (DD/MM/YYYY): </font>
														</div>

														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitDay"
																value="<%=currDay%>" id="visitDayID" placeholder="Day"
																maxlength="2"
																onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
															<font style="color: red;" id="dateFontID"></font>
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitMonth"
																value="<%=currMonth%>" id="visitMonthID"
																placeholder="Month" maxlength="2"
																onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
															<font style="color: red;" id="monthFontID"></font>
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control" name="visitYear"
																value="<%=currYear%>" id="visitYearID"
																placeholder="Year" maxlength="4"
																onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
															<font style="color: red;" id="yearFontID"></font>
														</div>

														<input type="hidden" class="form-control" name="visitDate"
															value="<%=currentDate%>" id="finalDateID">
														<%
															}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3 col-sm-3 col-xs-12">
															<font style="font-size: 14px; font-weight: bold;">Category
															</font>
														</div>
														<%
															if (categoryList1.contains("Government")) {
																	System.out.println("inside category list govt");
														%>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																checked="checked" value="Government" id="GovernmentID"
																onclick="displayCategoryTypeDiv('Govt');">&nbsp;Government</label>
															<s:select
																list="#{'ESIS':'ESIS','RGJY':'RGJY','PMC':'PMC','CGHS':'CGHS','CSMA':'CSMA'}"
																headerKey="" headerValue="Select Govt."
																class="form-control" name="categoryType" id="govtTypeID"
																style="margin-top: 15px;"></s:select>
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Payee" id="PayeeID"
																onclick="displayCategoryTypeDiv('Payee');">&nbsp;Payee</label>
															<input type="text" class="form-control" name=""
																id="payeeNameID"
																value="<s:property value="categoryType"/>"
																style="margin-top: 15px; display: none;"
																placeholder="Payee Name">
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Other" id="OtherID"
																onclick="displayCategoryTypeDiv('Other');">&nbsp;Other</label>
															<div class="row" id="otherDivID"
																style="margin-top: 15px; display: none;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>
															<s:select list="#{}" headerKey="" headerValue="Select"
																class="form-control" name="" id="otherTypeID"
																style="margin-top: 15px;display: none;"></s:select>
														</div>
														<%
															} else if (categoryList1.contains("Payee")) {
																	System.out.println("inside category list payee");
														%>

														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Government" id="GovernmentID"
																onclick="displayCategoryTypeDiv('Govt');">&nbsp;Government</label>
															<s:select
																list="#{'ESIS':'ESIS','RGJY':'RGJY','PMC':'PMC','CGHS':'CGHS','CSMA':'CSMA'}"
																headerKey="" headerValue="Select Govt."
																class="form-control" name="" id="govtTypeID"
																style="margin-top: 15px;display: none;"></s:select>
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																checked="checked" id="PayeeID" value="Payee"
																onclick="displayCategoryTypeDiv('Payee');">&nbsp;Payee</label>
															<input type="text" class="form-control"
																name="categoryType" id="payeeNameID"
																style="margin-top: 15px;" placeholder="Payee Name">
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Other" id="OtherID"
																onclick="displayCategoryTypeDiv('Other');">&nbsp;Other</label>
															<div class="row" id="otherDivID"
																style="margin-top: 15px; display: none;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>
															<s:select list="#{}" headerKey="" headerValue="Select"
																class="form-control" name="" id="otherTypeID"
																style="margin-top: 15px;display: none;"></s:select>
														</div>

														<%
															} else if (categoryList1.contains("Other")) {
																	System.out.println("inside category list other");
														%>

														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Government" id="GovernmentID"
																onclick="displayCategoryTypeDiv('Govt');">&nbsp;Government</label>
															<s:select
																list="#{'ESIS':'ESIS','RGJY':'RGJY','PMC':'PMC','CGHS':'CGHS','CSMA':'CSMA'}"
																headerKey="" headerValue="Select Govt."
																class="form-control" name="" id="govtTypeID"
																style="margin-top: 15px;display: none;"></s:select>
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Payee" id="PayeeID"
																onclick="displayCategoryTypeDiv('Payee');">&nbsp;Payee</label>
															<input type="text" class="form-control" name=""
																id="payeeNameID"
																value="<s:property value="categoryType"/>"
																style="margin-top: 15px; display: none;"
																placeholder="Payee Name">
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																checked="checked" id="OtherID" value="Other"
																onclick="displayCategoryTypeDiv('Other');">&nbsp;Other</label>

															<%
																if (categoryVal.length == 1) {
															%>

															<div class="row" id="otherDivID"
																style="margin-top: 15px;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>

															<s:select list="#{}" headerKey="" headerValue="Select"
																class="form-control" name='categoryType'
																id="otherTypeID" style="margin-top: 15px; display:none;"></s:select>

															<%
																} else {

																			if (otherInsuranceList.contains(categoryVal1)) {
															%>
															<div class="row" id="otherDivID"
																style="margin-top: 15px;">
																<label><input type="radio" name="category1"
																	value="" checked="checked"
																	onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>

															<s:select
																list="#{'Medi Assist':'Medi Assist','DHS':'DHS','Start Health':'Start Health','MD India':'MD India','Religare':'Religare','Universal Sampo':'Universal Sampo','TIK - Sigma':'TIK - Sigma'}"
																headerKey="" headerValue="Select" class="form-control"
																name="categoryType" id="otherTypeID"
																style="margin-top: 15px;"></s:select>
															<%
																} else if (otherCompanyList.contains(categoryVal1)) {
															%>
															<div class="row" id="otherDivID"
																style="margin-top: 15px;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" checked="checked"
																	onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>

															<s:select list="#{'JSW':'JSW','Jhonson':'Jhonson'}"
																headerKey="" headerValue="Select" class="form-control"
																name='categoryType' id="otherTypeID"
																style="margin-top: 15px;"></s:select>
															<%
																} else {
															%>

															<div class="row" id="otherDivID"
																style="margin-top: 15px;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>

															<s:select list="#{}" headerKey="" headerValue="Select"
																class="form-control" name='categoryType'
																id="otherTypeID" style="margin-top: 15px; display:none;"></s:select>

															<%
																}
																		}
															%>
														</div>

														<%
															} else {
																	System.out.println("inside category list else");
														%>

														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Government" id="GovernmentID"
																onclick="displayCategoryTypeDiv('Govt');">&nbsp;Government</label>
															<s:select
																list="#{'ESIS':'ESIS','RGJY':'RGJY','PMC':'PMC','CGHS':'CGHS','CSMA':'CSMA'}"
																headerKey="" headerValue="Select Govt."
																class="form-control" name="" id="govtTypeID"
																style="margin-top: 15px;display: none;"></s:select>
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Payee" id="PayeeID"
																onclick="displayCategoryTypeDiv('Payee');">&nbsp;Payee</label>
															<input type="text" class="form-control" name=""
																id="payeeNameID"
																value="<s:property value="categoryType"/>"
																style="margin-top: 15px; display: none;"
																placeholder="Payee Name">
														</div>
														<div class="col-md-3 col-sm-3 col-xs-12">
															<label><input type="radio" name="category"
																value="Other" id="OtherID"
																onclick="displayCategoryTypeDiv('Other');">&nbsp;Other</label>
															<div class="row" id="otherDivID"
																style="margin-top: 15px; display: none;">
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Insurance');">&nbsp;Insurance</label>
																<label><input type="radio" name="category1"
																	value="" onclick="displayOtherTypeDiv('Company');">&nbsp;Company</label>
															</div>
															<s:select list="#{}" headerKey="" headerValue="Select"
																class="form-control" name="" id="otherTypeID"
																style="margin-top:15px; display: none;"></s:select>
														</div>

														<%
															}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Past
																History</font>
														</div>

													</div>

													<%
														if (MedicalHistory.size() < 1) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">DM: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="diabetesMellitus"
																id="diabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('diabetesMellitusDurationID','diabetesMellitusDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="diabetesMellitusDuration"
																step="0.01" readonly="readonly"
																id="diabetesMellitusDurationID"
																value="<s:property value="diabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="diabetesMellitusDesc"
																readonly="readonly" id="diabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Duration: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="hypertension"
																id="hypertensionID" style="zoom: 1.5;"
																onclick="enableFields('hypertensionDurationID','hypertensionDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="hypertensionDuration"
																step="0.01" readonly="readonly"
																id="hypertensionDurationID"
																value="<s:property value="hypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="hypertensionDesc" readonly="readonly"
																id="hypertensionDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Asthma: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="asthema" id="asthemaID"
																style="zoom: 1.5;"
																onclick="enableFields('asthemaDurationID','asthemaDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="asthemaDuration" step="0.01"
																readonly="readonly" id="asthemaDurationID"
																value="<s:property value="asthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="asthemaDesc" readonly="readonly"
																id="asthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">IHD: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="ischemicHeartDisease"
																id="ischemicHeartDiseaseID" style="zoom: 1.5;"
																onclick="enableFields('ischemicHeartDiseaseDurationID','ischemicHeartDiseaseDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="ischemicHeartDiseaseDuration"
																step="0.01" readonly="readonly"
																id="ischemicHeartDiseaseDurationID"
																value="<s:property value="ischemicHeartDiseaseDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="ischemicHeartDiseaseDesc"
																readonly="readonly" id="ischemicHeartDiseaseDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Allergies: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="allergies" id="allergiesID"
																style="zoom: 1.5;"
																onclick="enableFields('allergiesDurationID','allergiesDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="allergiesDuration" step="0.01"
																readonly="readonly" id="allergiesDurationID"
																value="<s:property value="allergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="allergiesDesc" readonly="readonly"
																id="allergiesDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Surgical History: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="surgicalHistory"
																id="surgicalHistoryID" style="zoom: 1.5;"
																onclick="enableFields('surgicalHistoryDurationID','surgicalHistoryDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="surgicalHistoryDuration"
																step="0.01" readonly="readonly"
																id="surgicalHistoryDurationID"
																value="<s:property value="surgicalHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="surgicalHistoryDesc"
																readonly="readonly" id="surgicalHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
															.
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Gynecology History:
															</font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="gynecologyHistory"
																id="gynecologyHistoryID" style="zoom: 1.5;"
																onclick="enableFields('gynecologyHistoryDurationID','gynecologyHistoryDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="gynecologyHistoryDuration"
																step="0.01" readonly="readonly"
																id="gynecologyHistoryDurationID"
																value="<s:property value="gynecologyHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="gynecologyHistoryDesc"
																readonly="readonly" id="gynecologyHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<%
														} else {

																for (PatientForm form1 : MedicalHistory) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">DM: </font>
														</div>

														<%
															if (form1.getDiabetesMellitus() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="diabetesMellitus"
																id="diabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('diabetesMellitusDurationID','diabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="diabetesMellitusDuration"
																step="0.01" readonly="readonly"
																id="diabetesMellitusDurationID"
																value="<s:property value="diabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="diabetesMellitusDesc"
																readonly="readonly" id="diabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {

																			if (form1.getDiabetesMellitus().equals("Yes")) {
														%>

														<div class="col-md-1">
															<input type="checkbox" name="diabetesMellitus"
																checked="checked" id="diabetesMellitusID"
																style="zoom: 1.5;"
																onclick="enableFields('diabetesMellitusDurationID','diabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="diabetesMellitusDuration"
																step="0.01" id="diabetesMellitusDurationID"
																value="<s:property value="diabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="diabetesMellitusDesc"
																id="diabetesMellitusDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>

														<div class="col-md-1">
															<input type="checkbox" name="diabetesMellitus"
																id="diabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('diabetesMellitusDurationID','diabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="diabetesMellitusDuration"
																step="0.01" readonly="readonly"
																id="diabetesMellitusDurationID"
																value="<s:property value="diabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="diabetesMellitusDesc"
																readonly="readonly" id="diabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Duration: </font>
														</div>

														<%
															if (form1.getHypertension() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="hypertension"
																id="hypertensionID" style="zoom: 1.5;"
																onclick="enableFields('hypertensionDurationID','hypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="hypertensionDuration"
																step="0.01" readonly="readonly"
																id="hypertensionDurationID"
																value="<s:property value="hypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="hypertensionDesc" readonly="readonly"
																id="hypertensionDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {

																			if (form1.getHypertension().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="hypertension"
																checked="checked" id="hypertensionID" style="zoom: 1.5;"
																onclick="enableFields('hypertensionDurationID','hypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="hypertensionDuration"
																step="0.01" id="hypertensionDurationID"
																value="<s:property value="hypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="hypertensionDesc"
																id="hypertensionDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="hypertension"
																id="hypertensionID" style="zoom: 1.5;"
																onclick="enableFields('hypertensionDurationID','hypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="hypertensionDuration"
																step="0.01" readonly="readonly"
																id="hypertensionDurationID"
																value="<s:property value="hypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="hypertensionDesc" readonly="readonly"
																id="hypertensionDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Asthma: </font>
														</div>

														<%
															if (form1.getAsthema() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="asthema" id="asthemaID"
																style="zoom: 1.5;"
																onclick="enableFields('asthemaDurationID','asthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="asthemaDuration" step="0.01"
																readonly="readonly" id="asthemaDurationID"
																value="<s:property value="asthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="asthemaDesc" readonly="readonly"
																id="asthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {

																			if (form1.getAsthema().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="asthema" id="asthemaID"
																checked="checked" style="zoom: 1.5;"
																onclick="enableFields('asthemaDurationID','asthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="asthemaDuration" step="0.01"
																id="asthemaDurationID"
																value="<s:property value="asthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="asthemaDesc" id="asthemaDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="asthema" id="asthemaID"
																style="zoom: 1.5;"
																onclick="enableFields('asthemaDurationID','asthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="asthemaDuration" step="0.01"
																readonly="readonly" id="asthemaDurationID"
																value="<s:property value="asthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="asthemaDesc" readonly="readonly"
																id="asthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>



													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">IHD: </font>
														</div>
														<%
															if (form1.getIschemicHeartDisease() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="ischemicHeartDisease"
																id="ischemicHeartDiseaseID" style="zoom: 1.5;"
																onclick="enableFields('ischemicHeartDiseaseDurationID','ischemicHeartDiseaseDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="ischemicHeartDiseaseDuration"
																step="0.01" readonly="readonly"
																id="ischemicHeartDiseaseDurationID"
																value="<s:property value="ischemicHeartDiseaseDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="ischemicHeartDiseaseDesc"
																readonly="readonly" id="ischemicHeartDiseaseDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form1.getIschemicHeartDisease().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="ischemicHeartDisease"
																checked="checked" id="ischemicHeartDiseaseID"
																style="zoom: 1.5;"
																onclick="enableFields('ischemicHeartDiseaseDurationID','ischemicHeartDiseaseDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="ischemicHeartDiseaseDuration"
																step="0.01" id="ischemicHeartDiseaseDurationID"
																value="<s:property value="ischemicHeartDiseaseDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="ischemicHeartDiseaseDesc"
																id="ischemicHeartDiseaseDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="ischemicHeartDisease"
																id="ischemicHeartDiseaseID" style="zoom: 1.5;"
																onclick="enableFields('ischemicHeartDiseaseDurationID','ischemicHeartDiseaseDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="ischemicHeartDiseaseDuration"
																step="0.01" readonly="readonly"
																id="ischemicHeartDiseaseDurationID"
																value="<s:property value="ischemicHeartDiseaseDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="ischemicHeartDiseaseDesc"
																readonly="readonly" id="ischemicHeartDiseaseDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>



													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Allergies: </font>
														</div>

														<%
															if (form1.getAllergies() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="allergies" id="allergiesID"
																style="zoom: 1.5;"
																onclick="enableFields('allergiesDurationID','allergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="allergiesDuration" step="0.01"
																readonly="readonly" id="allergiesDurationID"
																value="<s:property value="allergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="allergiesDesc" readonly="readonly"
																id="allergiesDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form1.getAllergies().equals("Yes")) {
														%>

														<div class="col-md-1">
															<input type="checkbox" name="allergies" id="allergiesID"
																checked="checked" style="zoom: 1.5;"
																onclick="enableFields('allergiesDurationID','allergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="allergiesDuration" step="0.01"
																id="allergiesDurationID"
																value="<s:property value="allergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="allergiesDesc" id="allergiesDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="allergies" id="allergiesID"
																style="zoom: 1.5;"
																onclick="enableFields('allergiesDurationID','allergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="allergiesDuration" step="0.01"
																readonly="readonly" id="allergiesDurationID"
																value="<s:property value="allergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="allergiesDesc" readonly="readonly"
																id="allergiesDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>


													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Surgical History: </font>
														</div>

														<%
															if (form1.getSurgicalHistory() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="surgicalHistory"
																id="surgicalHistoryID" style="zoom: 1.5;"
																onclick="enableFields('surgicalHistoryDurationID','surgicalHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="surgicalHistoryDuration"
																step="0.01" readonly="readonly"
																id="surgicalHistoryDurationID"
																value="<s:property value="surgicalHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="surgicalHistoryDesc"
																readonly="readonly" id="surgicalHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form1.getSurgicalHistory().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="surgicalHistory"
																checked="checked" id="surgicalHistoryID"
																style="zoom: 1.5;"
																onclick="enableFields('surgicalHistoryDurationID','surgicalHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="surgicalHistoryDuration"
																step="0.01" id="surgicalHistoryDurationID"
																value="<s:property value="surgicalHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="surgicalHistoryDesc"
																id="surgicalHistoryDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="surgicalHistory"
																id="surgicalHistoryID" style="zoom: 1.5;"
																onclick="enableFields('surgicalHistoryDurationID','surgicalHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="surgicalHistoryDuration"
																step="0.01" readonly="readonly"
																id="surgicalHistoryDurationID"
																value="<s:property value="surgicalHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="surgicalHistoryDesc"
																readonly="readonly" id="surgicalHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>


													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Gynecology History:
															</font>
														</div>

														<%
															if (form1.getGynecologyHistory() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="gynecologyHistory"
																id="gynecologyHistoryID" style="zoom: 1.5;"
																onclick="enableFields('gynecologyHistoryDurationID','gynecologyHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="gynecologyHistoryDuration"
																step="0.01" readonly="readonly"
																id="gynecologyHistoryDurationID"
																value="<s:property value="gynecologyHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="gynecologyHistoryDesc"
																readonly="readonly" id="gynecologyHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form1.getGynecologyHistory().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="gynecologyHistory"
																checked="checked" id="gynecologyHistoryID"
																style="zoom: 1.5;"
																onclick="enableFields('gynecologyHistoryDurationID','gynecologyHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="gynecologyHistoryDuration"
																step="0.01" id="gynecologyHistoryDurationID"
																value="<s:property value="gynecologyHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="gynecologyHistoryDesc"
																id="gynecologyHistoryDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="gynecologyHistory"
																id="gynecologyHistoryID" style="zoom: 1.5;"
																onclick="enableFields('gynecologyHistoryDurationID','gynecologyHistoryDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="gynecologyHistoryDuration"
																step="0.01" readonly="readonly"
																id="gynecologyHistoryDurationID"
																value="<s:property value="gynecologyHistoryDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="gynecologyHistoryDesc"
																readonly="readonly" id="gynecologyHistoryDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>


													</div>

													<%
														}
															}
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Family
																History</font>
														</div>

													</div>

													<%
														if (FamilyHistory.size() < 1) {
													%>
													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">DM: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="famHistDiabetesMellitus"
																id="famHistDiabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('famHistDiabetesMellitusDurationID','famHistDiabetesMellitusDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number"
																name="famHistDiabetesMellitusDuration" step="0.01"
																readonly="readonly"
																id="famHistDiabetesMellitusDurationID"
																value="<s:property value="famHistDiabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistDiabetesMellitusDesc"
																readonly="readonly" id="famHistDiabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">HTN: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="famHistHypertension"
																id="famHistHypertensionID" style="zoom: 1.5;"
																onclick="enableFields('famHistHypertensionDurationID','famHistHypertensionDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistHypertensionDuration"
																step="0.01" readonly="readonly"
																id="famHistHypertensionDurationID"
																value="<s:property value="famHistHypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistHypertensionDesc"
																readonly="readonly" id="famHistHypertensionDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Asthma: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="famHistAsthema"
																id="famHistAsthemaID" style="zoom: 1.5;"
																onclick="enableFields('famHistAsthemaDurationID','famHistAsthemaDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAsthemaDuration"
																step="0.01" readonly="readonly"
																id="famHistAsthemaDurationID"
																value="<s:property value="famHistAsthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAsthemaDesc" readonly="readonly"
																id="famHistAsthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Allergies: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="famHistAllergies"
																id="famHistAllergiesID" style="zoom: 1.5;"
																onclick="enableFields('famHistAllergiesDurationID','famHistAllergiesDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAllergiesDuration"
																step="0.01" readonly="readonly"
																id="famHistAllergiesDurationID"
																value="<s:property value="famHistAllergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAllergiesDesc"
																readonly="readonly" id="famHistAllergiesDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<%
														} else {

																for (PatientForm form2 : FamilyHistory) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">DM: </font>
														</div>
														<%
															if (form2.getFamHistDiabetesMellitus() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistDiabetesMellitus"
																id="famHistDiabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('famHistDiabetesMellitusDurationID','famHistDiabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number"
																name="famHistDiabetesMellitusDuration" step="0.01"
																readonly="readonly"
																id="famHistDiabetesMellitusDurationID"
																value="<s:property value="famHistDiabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistDiabetesMellitusDesc"
																readonly="readonly" id="famHistDiabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form2.getFamHistDiabetesMellitus().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistDiabetesMellitus"
																checked="checked" id="famHistDiabetesMellitusID"
																style="zoom: 1.5;"
																onclick="enableFields('famHistDiabetesMellitusDurationID','famHistDiabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number"
																name="famHistDiabetesMellitusDuration" step="0.01"
																id="famHistDiabetesMellitusDurationID"
																value="<s:property value="famHistDiabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistDiabetesMellitusDesc"
																id="famHistDiabetesMellitusDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistDiabetesMellitus"
																id="famHistDiabetesMellitusID" style="zoom: 1.5;"
																onclick="enableFields('famHistDiabetesMellitusDurationID','famHistDiabetesMellitusDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number"
																name="famHistDiabetesMellitusDuration" step="0.01"
																readonly="readonly"
																id="famHistDiabetesMellitusDurationID"
																value="<s:property value="famHistDiabetesMellitusDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistDiabetesMellitusDesc"
																readonly="readonly" id="famHistDiabetesMellitusDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">HTN: </font>
														</div>
														<%
															if (form2.getFamHistHypertension() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistHypertension"
																id="famHistHypertensionID" style="zoom: 1.5;"
																onclick="enableFields('famHistHypertensionDurationID','famHistHypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistHypertensionDuration"
																step="0.01" readonly="readonly"
																id="famHistHypertensionDurationID"
																value="<s:property value="famHistHypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistHypertensionDesc"
																readonly="readonly" id="famHistHypertensionDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form2.getFamHistHypertension().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistHypertension"
																checked="checked" id="famHistHypertensionID"
																style="zoom: 1.5;"
																onclick="enableFields('famHistHypertensionDurationID','famHistHypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistHypertensionDuration"
																step="0.01" id="famHistHypertensionDurationID"
																value="<s:property value="famHistHypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistHypertensionDesc"
																id="famHistHypertensionDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistHypertension"
																id="famHistHypertensionID" style="zoom: 1.5;"
																onclick="enableFields('famHistHypertensionDurationID','famHistHypertensionDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistHypertensionDuration"
																step="0.01" readonly="readonly"
																id="famHistHypertensionDurationID"
																value="<s:property value="famHistHypertensionDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistHypertensionDesc"
																readonly="readonly" id="famHistHypertensionDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Asthma: </font>
														</div>

														<%
															if (form2.getFamHistAsthema() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAsthema"
																id="famHistAsthemaID" style="zoom: 1.5;"
																onclick="enableFields('famHistAsthemaDurationID','famHistAsthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAsthemaDuration"
																step="0.01" readonly="readonly"
																id="famHistAsthemaDurationID"
																value="<s:property value="famHistAsthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAsthemaDesc" readonly="readonly"
																id="famHistAsthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form2.getFamHistAsthema().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAsthema"
																checked="checked" id="famHistAsthemaID"
																style="zoom: 1.5;"
																onclick="enableFields('famHistAsthemaDurationID','famHistAsthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAsthemaDuration"
																step="0.01" id="famHistAsthemaDurationID"
																value="<s:property value="famHistAsthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAsthemaDesc"
																id="famHistAsthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAsthema"
																id="famHistAsthemaID" style="zoom: 1.5;"
																onclick="enableFields('famHistAsthemaDurationID','famHistAsthemaDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAsthemaDuration"
																step="0.01" readonly="readonly"
																id="famHistAsthemaDurationID"
																value="<s:property value="famHistAsthemaDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAsthemaDesc" readonly="readonly"
																id="famHistAsthemaDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Allergies: </font>
														</div>

														<%
															if (form2.getFamHistAllergies() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAllergies"
																id="famHistAllergiesID" style="zoom: 1.5;"
																onclick="enableFields('famHistAllergiesDurationID','famHistAllergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAllergiesDuration"
																step="0.01" readonly="readonly"
																id="famHistAllergiesDurationID"
																value="<s:property value="famHistAllergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAllergiesDesc"
																readonly="readonly" id="famHistAllergiesDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form2.getFamHistAllergies().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAllergies"
																checked="checked" id="famHistAllergiesID"
																style="zoom: 1.5;"
																onclick="enableFields('famHistAllergiesDurationID','famHistAllergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAllergiesDuration"
																step="0.01" id="famHistAllergiesDurationID"
																value="<s:property value="famHistAllergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAllergiesDesc"
																id="famHistAllergiesDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="famHistAllergies"
																id="famHistAllergiesID" style="zoom: 1.5;"
																onclick="enableFields('famHistAllergiesDurationID','famHistAllergiesDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="famHistAllergiesDuration"
																step="0.01" readonly="readonly"
																id="famHistAllergiesDurationID"
																value="<s:property value="famHistAllergiesDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="famHistAllergiesDesc"
																readonly="readonly" id="famHistAllergiesDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>
													<%
														}
															}
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Habit</font>
														</div>

													</div>

													<%
														if (PersonalHistory.size() < 1) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Smoking: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="smoking" id="smokingID"
																style="zoom: 1.5;"
																onclick="enableFields('smokingDurationID','smokingDetailsID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="smokingDuration" step="0.01"
																readonly="readonly" id="smokingDurationID"
																value="<s:property value="smokingDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="personalHistorySmokingDetails"
																readonly="readonly" id="smokingDetailsID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Alcohol: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="alcohol" id="alcoholID"
																style="zoom: 1.5;"
																onclick="enableFields('alcoholDurationID','alcoholDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="alcoholDuration" step="0.01"
																readonly="readonly" id="alcoholDurationID"
																value="<s:property value="alcoholDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="alcoholDesc" readonly="readonly"
																id="alcoholDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Mishari: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="mishari" id="mishariID"
																style="zoom: 1.5;"
																onclick="enableFields('mishariDurationID','mishariDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="mishariDuration" step="0.01"
																readonly="readonly" id="mishariDurationID"
																value="<s:property value="mishariDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="mishariDesc" readonly="readonly"
																id="mishariDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Tobacco: </font>
														</div>

														<div class="col-md-1">
															<input type="checkbox" name="tobacco" id="tobaccoID"
																style="zoom: 1.5;"
																onclick="enableFields('tobaccoDurationID','tobaccoDescID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="tobaccoDuration" step="0.01"
																readonly="readonly" id="tobaccoDurationID"
																value="<s:property value="tobaccoDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="tobaccoDesc" readonly="readonly"
																id="tobaccoDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>

													</div>

													<%
														} else {

																for (PatientForm form3 : PersonalHistory) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Smoking: </font>
														</div>
														<%
															if (form3.getPersonalHistorySmoking() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="personalHistorySmoking"
																id="smokingID" " style="zoom: 1.5;"
																onclick="enableFields('smokingDurationID','smokingDetailsID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="smokingDuration" step="0.01"
																readonly="readonly" id="smokingDurationID"
																value="<s:property value="smokingDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="personalHistorySmokingDetails"
																readonly="readonly" id="smokingDetailsID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form3.getPersonalHistorySmoking().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="personalHistorySmoking"
																id="smokingID" checked="checked" style="zoom: 1.5;"
																onclick="enableFields('smokingDurationID','smokingDetailsID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="smokingDuration" step="0.01"
																id="smokingDurationID"
																value="<s:property value="smokingDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="personalHistorySmokingDetails"
																id="smokingDetailsID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="personalHistorySmoking"
																id="smokingID" style="zoom: 1.5;"
																onclick="enableFields('smokingDurationID','smokingDetailsID', this.id);"
																value="Yes">
														</div>

														<div class="col-md-2 col-xs-3">
															<input type="number" name="smokingDuration" step="0.01"
																readonly="readonly" id="smokingDurationID"
																value="<s:property value="smokingDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="personalHistorySmokingDetails"
																readonly="readonly" id="smokingDetailsID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>


													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Alcohol: </font>
														</div>
														<%
															if (form3.getAlcohol() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="alcohol" id="alcoholID"
																style="zoom: 1.5;"
																onclick="enableFields('alcoholDurationID','alcoholDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="alcoholDuration" step="0.01"
																readonly="readonly" id="alcoholDurationID"
																value="<s:property value="alcoholDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="alcoholDesc" readonly="readonly"
																id="alcoholDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form3.getAlcohol().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="alcohol" id="alcoholID"
																checked="checked" style="zoom: 1.5;"
																onclick="enableFields('alcoholDurationID','alcoholDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="alcoholDuration" step="0.01"
																id="alcoholDurationID"
																value="<s:property value="alcoholDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="alcoholDesc" id="alcoholDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="alcohol" id="alcoholID"
																style="zoom: 1.5;"
																onclick="enableFields('alcoholDurationID','alcoholDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="alcoholDuration" step="0.01"
																readonly="readonly" id="alcoholDurationID"
																value="<s:property value="alcoholDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="alcoholDesc" readonly="readonly"
																id="alcoholDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Mishari: </font>
														</div>
														<%
															if (form3.getMishari() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="mishari" id="mishariID"
																style="zoom: 1.5;"
																onclick="enableFields('mishariDurationID','mishariDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="mishariDuration" step="0.01"
																readonly="readonly" id="mishariDurationID"
																value="<s:property value="mishariDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="mishariDesc" readonly="readonly"
																id="mishariDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form3.getMishari().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="mishari" checked="checked"
																id="mishariID" style="zoom: 1.5;"
																onclick="enableFields('mishariDurationID','mishariDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="mishariDuration" step="0.01"
																id="mishariDurationID"
																value="<s:property value="mishariDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="mishariDesc" id="mishariDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="mishari" id="mishariID"
																style="zoom: 1.5;"
																onclick="enableFields('mishariDurationID','mishariDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="mishariDuration" step="0.01"
																readonly="readonly" id="mishariDurationID"
																value="<s:property value="mishariDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="mishariDesc" readonly="readonly"
																id="mishariDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>


													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-2 col-xs-3">
															<font style="font-size: 14px;">Tobacco: </font>
														</div>
														<%
															if (form3.getTobacco() == null) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="tobacco" id="tobaccoID"
																style="zoom: 1.5;"
																onclick="enableFields('tobaccoDurationID','tobaccoDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="tobaccoDuration" step="0.01"
																readonly="readonly" id="tobaccoDurationID"
																value="<s:property value="tobaccoDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="tobaccoDesc" readonly="readonly"
																id="tobaccoDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
																			if (form3.getTobacco().equals("Yes")) {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="tobacco" checked="checked"
																id="tobaccoID" style="zoom: 1.5;"
																onclick="enableFields('tobaccoDurationID','tobaccoDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="tobaccoDuration" step="0.01"
																id="tobaccoDurationID"
																value="<s:property value="tobaccoDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="tobaccoDesc" id="tobaccoDescID"
																class="form-control" placeholder="Details"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-1">
															<input type="checkbox" name="tobacco" id="tobaccoID"
																style="zoom: 1.5;"
																onclick="enableFields('tobaccoDurationID','tobaccoDescID', this.id);"
																value="Yes">
														</div>
														<div class="col-md-2 col-xs-3">
															<input type="number" name="tobaccoDuration" step="0.01"
																readonly="readonly" id="tobaccoDurationID"
																value="<s:property value="tobaccoDuration"/>"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-2 col-xs-3">
															<s:textarea name="tobaccoDesc" readonly="readonly"
																id="tobaccoDescID" class="form-control"
																placeholder="Details"></s:textarea>
														</div>
														<%
															}
																		}
														%>
													</div>

													<%
														}

															}
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Present
																Complaints</font>
														</div>

													</div>

													<%-- <s:iterator value="IPDComplaintsList" var="IPDComplaints"> --%>
													<div class="row"
														style="margin-top: 10px; padding: 0 15px 0 15px;">
														<div class="col-md-4">
															<input type="hidden" id="ComplaintsID1" name="complaints">
															<s:select list="PVComplaints" headerKey="-1"
																id="ComplaintsID" value="complaintsListValues"
																onchange="maxAllowedMultiselect(this)" multiple="true"></s:select>
														</div>
														<table id="datatable-responsive"
															class="table table-striped table-bordered dt-responsive nowrap"
															cellspacing="0" width="100%">
															<thead>

																<tr>
																	<th style="width: 90%">Other</th>
																	<th style="width: 10%"></th>
																</tr>
															</thead>

															<tbody>

																<tr id="complaintOtherTRID">

																	<td><input type="text" name=""
																		id="complaintOtherID" class="form-control"></td>

																	<td><a
																		onclick="addOtherComplaints(complaintOtherID.value);"
																		id="OtherComplaintsID"> <img
																			src="images/add_icon_1.png"
																			onmouseover="this.src='images/add_icon_2.png'"
																			onmouseout="this.src='images/add_icon_1.png'"
																			alt="Add Other Complaints"
																			title="Add Other Complaints"
																			style="margin-top: 5px; height: 24px;" />
																	</a></td>

																</tr>
															</tbody>
														</table>

													</div>
													<%-- </s:iterator> --%>
													<div class="row"
														style="margin-top: 10px; padding: 0 15px 0 15px;">

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Height: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" name="height"
																value="<s:property value="height"/>" step="0.01"
																placeholder="Height">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Weight: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" name="weight"
																value="<s:property value="weight"/>" step="0.01"
																placeholder="Weight">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Pulse: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" name="OEPulse"
																value="<s:property value="OEPulse"/>" step="0.01"
																placeholder="Pulse">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">B.P.: </font>
														</div>

														<div class="col-md-1">
															<input type="number" class="form-control" name="OEBPSys"
																value="<s:property value="OEBPSys"/>" step="0.01"
																placeholder="Systolic">
														</div>

														<div class="col-md-1">
															<input type="number" class="form-control" name="OEBPDia"
																value="<s:property value="OEBPDia"/>" step="0.01"
																placeholder="Diastolic">
														</div>

													</div>

													<div class="row"
														style="margin-top: 10px; padding: 0 15px 0 15px;">

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Temp.: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" step="0.01"
																value="<s:property value="temperature"/>"
																name="temperature" placeholder="Temperature">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Respiration: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" step="0.01"
																value="<s:property value="respiration"/>"
																name="respiration" placeholder="Respiration">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Pallor: </font>
														</div>

														<div class="col-md-2">
															<input type="text" class="form-control" name="pallor"
																value="<s:property value="pallor"/>"
																placeholder="Pallor">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">Icterus: </font>
														</div>

														<div class="col-md-2">
															<input type="text" class="form-control" name="icterus"
																value="<s:property value="icterus"/>"
																placeholder="Icterus">
														</div>

													</div>

													<div class="row"
														style="margin-top: 10px; padding: 0 15px 0 15px;">

														<div class="col-md-3 col-xs-6">
															<font style="font-size: 14px;">Abdominal
																Circumference: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" step="0.01"
																value="<s:property value="abdominalCircumference"/>"
																name="abdominalCircumference"
																placeholder="Abdominal Circumference">
														</div>

														<div class="col-md-1 col-xs-6">
															<font style="font-size: 14px;">BMI: </font>
														</div>

														<div class="col-md-2">
															<input type="number" class="form-control" step="0.01"
																value="<s:property value="bmi"/>" name="bmi"
																placeholder="BMI">
														</div>

													</div>

													<%
														for (PatientForm form4 : onEaminationList) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1">
															<font style="font-size: 14px; font-weight: bold;">RS</font>
														</div>

														<%
															if (form4.getRsPhonchi() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsPhonchi"
																style="zoom: 1.5;" value="Yes">&nbsp;Rhonchi</label>
														</div>
														<%
															} else {
																		if (form4.getRsPhonchi().equals("Yes")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsPhonchi"
																checked="checked" style="zoom: 1.5;" value="Yes">&nbsp;Rhonchi</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsPhonchi"
																style="zoom: 1.5;" value="Yes">&nbsp;Rhonchi</label>
														</div>
														<%
															}
																	}
														%>

														<%
															if (form4.getRsCrepitation() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox"
																name="rsCrepitation" style="zoom: 1.5;" value="Yes">&nbsp;Crepitation</label>
														</div>
														<%
															} else {
																		if (form4.getRsCrepitation().equals("Yes")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox"
																name="rsCrepitation" checked="checked"
																style="zoom: 1.5;" value="Yes">&nbsp;Crepitation</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox"
																name="rsCrepitation" style="zoom: 1.5;" value="Yes">&nbsp;Crepitation</label>
														</div>
														<%
															}
																	}
														%>

														<%
															if (form4.getRsClear() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsClear"
																style="zoom: 1.5;" value="Yes">&nbsp;Clear</label>
														</div>
														<%
															} else {
																		if (form4.getRsClear().equals("Yes")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsClear"
																checked="checked" style="zoom: 1.5;" value="Yes">&nbsp;Clear</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="rsClear"
																style="zoom: 1.5;" value="Yes">&nbsp;Clear</label>
														</div>
														<%
															}
																	}
														%>
													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1">
															<font style="font-size: 14px; font-weight: bold;">CVS</font>
														</div>
														<%
															if (form4.getS1s2() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="s1s2"
																style="zoom: 1.5;" value="Yes">&nbsp;S1S2</label>
														</div>
														<%
															} else {
																		if (form4.getS1s2().equals("Yes")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="s1s2"
																checked="checked" style="zoom: 1.5;" value="Yes">&nbsp;S1S2</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="s1s2"
																style="zoom: 1.5;" value="Yes">&nbsp;S1S2</label>
														</div>
														<%
															}
																	}
														%>

														<%
															if (form4.getMurmur() == null) {
														%>
														<div class="col-md-6">
															<div class="col-md-4">
																<label><input type="checkbox" name="murmur"
																	id="s1s2ID" style="zoom: 1.5;" value="Yes"
																	onclick="showMurmurDiv(this.id, 'sysDivID', 'diaDivID');">&nbsp;Murmur</label>
															</div>
															<div class="col-md-4" id="sysDivID"
																style="display: none;">
																<input type="number" name="murmurSys" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurSys"/>"
																	placeholder="Systolic">
															</div>
															<div class="col-md-4" id="diaDivID"
																style="display: none;">
																<input type="number" name="murmurDia" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurDia"/>"
																	placeholder="Diastolic">
															</div>
														</div>
														<%
															} else {
																		if (form4.getMurmur().equals("Yes")) {
														%>
														<div class="col-md-6">
															<div class="col-md-4">
																<label><input type="checkbox" name="murmur"
																	checked="checked" id="s1s2ID" style="zoom: 1.5;"
																	value="Yes"
																	onclick="showMurmurDiv(this.id, 'sysDivID', 'diaDivID');">&nbsp;Murmur</label>
															</div>
															<div class="col-md-4" id="sysDivID">
																<input type="number" name="murmurSys" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurSys"/>"
																	placeholder="Systolic">
															</div>
															<div class="col-md-4" id="diaDivID">
																<input type="number" name="murmurDia" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurDia"/>"
																	placeholder="Diastolic">
															</div>
														</div>
														<%
															} else {
														%>
														<div class="col-md-6">
															<div class="col-md-4">
																<label><input type="checkbox" name="murmur"
																	id="s1s2ID" style="zoom: 1.5;" value="Yes"
																	onclick="showMurmurDiv(this.id, 'sysDivID', 'diaDivID');">&nbsp;Murmur</label>
															</div>
															<div class="col-md-4" id="sysDivID"
																style="display: none;">
																<input type="number" name="murmurSys" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurSys"/>"
																	placeholder="Systolic">
															</div>
															<div class="col-md-4" id="diaDivID"
																style="display: none;">
																<input type="number" name="murmurDia" step="0.01"
																	class="form-control"
																	value="<s:property value="murmurDia"/>"
																	placeholder="Diastolic">
															</div>
														</div>
														<%
															}
																	}
														%>

														<%
															if (form4.getWnl() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="wnl"
																style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															} else {
																		if (form4.getWnl().equals("Yes")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="wnl"
																checked="checked" style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="wnl"
																style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															}
																	}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1">
															<font style="font-size: 14px; font-weight: bold;">CNS</font>
														</div>
														<%
															if (form4.getCnsWNL() == null) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="cnsWNL"
																style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															} else {
																		if (form4.getCnsWNL().equals("YEs")) {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" checked="checked"
																name="cnsWNL" style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name="cnsWNL"
																style="zoom: 1.5;" value="Yes">&nbsp;WNL</label>
														</div>
														<%
															}
																	}
														%>

														<%
															if (form4.getCnsOther() == null || form4.getCnsOther() == "") {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name=""
																style="zoom: 1.5;" id="cnsOtherCheckID"
																onclick="showOtherDiv(this.id,'cnsOtherDivID','cnsOtherID');"
																value="Yes">&nbsp;Other</label>
														</div>

														<div class="col-md-7" id="cnsOtherDivID"
															style="display: none;">
															<s:textarea name="cnsOther" class="form-control"
																id="cnsOtherID"></s:textarea>
														</div>
														<%
															} else {
														%>
														<div class="col-md-2">
															<label><input type="checkbox" name=""
																checked="checked" style="zoom: 1.5;"
																id="cnsOtherCheckID"
																onclick="showOtherDiv(this.id,'cnsOtherDivID','cnsOtherID');"
																value="Yes">&nbsp;Other</label>
														</div>

														<div class="col-md-7" id="cnsOtherDivID">
															<s:textarea name="cnsOther" class="form-control"
																id="cnsOtherID"></s:textarea>
														</div>
														<%
															}
														%>

													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Abdomen</font>
														</div>

													</div>

													<%
														if (form4.getAbdomen() == null) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="hepatomegalyID" style=""
																onclick="showDiv('abdomenDivID');" value="Hepatomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('hepatomegalyID');"><b>Hepatomegaly</b></font>
														</div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="splenomegalyID" style=""
																onclick="showDiv('abdomenDivID');" value="Splenomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('splenomegalyID');"><b>Splenomegaly</b></font>
														</div>

													</div>

													<div class="row" style="margin-top: 10px; display: none;"
														id="abdomenDivID">
														<div class="col-md-1"></div>

														<div class="col-md-6">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightHypochondriac"
																				value="Yes">&nbsp;Right Hypochondriac</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="epigastric" value="Yes">&nbsp;Epigastric</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label></td>
																	</tr>
																	<tr>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightLumbar" value="Yes">&nbsp;Right
																				Lumbar</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="umbilical" value="Yes">&nbsp;Umbilical</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="leftLumbar" value="Yes">&nbsp;Left
																				Lumbar</label></td>
																	</tr>
																	<tr>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightIliac" value="Yes">&nbsp;Right
																				Iliac</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="hypogastric" value="Yes">&nbsp;Hypogastric</label></td>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="leftIliac" value="Yes">&nbsp;Left
																				Iliac</label></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>

													<%
														} else {
																	if (form4.getAbdomen().equals("Hepatomegaly")) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="hepatomegalyID" checked="checked" style=""
																onclick="showDiv('abdomenDivID');" value="Hepatomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('hepatomegalyID');"><b>Hepatomegaly</b></font>
														</div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="splenomegalyID" style=""
																onclick="showDiv('abdomenDivID');" value="Splenomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('splenomegalyID');"><b>Splenomegaly</b></font>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;"
														id="abdomenDivID">
														<div class="col-md-1"></div>

														<div class="col-md-6">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<%
																			if (form4.getRightHypochondriac() == null) {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightHypochondriac"
																				value="Yes">&nbsp;Right Hypochondriac</label></td>
																		<%
																			} else {
																								if (form4.getRightHypochondriac().equals("Yes")) {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" checked="checked"
																				name="rightHypochondriac" value="Yes">&nbsp;Right
																				Hypochondriac</label></td>
																		<%
																			} else {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightHypochondriac"
																				value="Yes">&nbsp;Right Hypochondriac</label></td>
																		<%
																			}
																							}
																		%>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getEpigastric() == null) {
																			%> <label><input
																				type="checkbox" name="epigastric" value="Yes">&nbsp;Epigastric</label>
																			<%
																				} else {
																									if (form4.getEpigastric().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="epigastric"
																				value="Yes">&nbsp;Epigastric</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="epigastric" value="Yes">&nbsp;Epigastric</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftHypochondriac() == null) {
																			%> <label><input
																				type="checkbox" name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	} else {
 						if (form4.getLeftHypochondriac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked"
																				name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>

																	<tr>
																		<td style="padding: 10px;">
																			<%
																				if (form4.getRightLumbar() == null) {
																			%> <label><input
																				type="checkbox" name="rightLumbar" value="Yes">&nbsp;Right
																				Lumbar</label> <%
 	} else {
 						if (form4.getRightLumbar().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="rightLumbar"
																				value="Yes">&nbsp;Right Lumbar</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="rightLumbar" value="Yes">&nbsp;Right
																				Lumbar</label> <%
 	}
 					}
 %>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getUmbilical() == null) {
																			%> <label><input
																				type="checkbox" name="umbilical" value="Yes">&nbsp;Umbilical</label>
																			<%
																				} else {
																									if (form4.getUmbilical().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="umbilical"
																				value="Yes">&nbsp;Umbilical</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="umbilical" value="Yes">&nbsp;Umbilical</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftLumbar() == null) {
																			%> <label><input
																				type="checkbox" name="leftLumbar" value="Yes">&nbsp;Left
																				Lumbar</label> <%
 	} else {
 						if (form4.getLeftLumbar().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="leftLumbar"
																				value="Yes">&nbsp;Left Lumbar</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftLumbar" value="Yes">&nbsp;Left
																				Lumbar</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>
																	<tr>
																		<td style="padding: 10px;">
																			<%
																				if (form4.getRightIliac() == null) {
																			%> <label><input
																				type="checkbox" name="rightIliac" value="Yes">&nbsp;Right
																				Iliac</label> <%
 	} else {
 						if (form4.getRightIliac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="rightIliac"
																				value="Yes">&nbsp;Right Iliac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="rightIliac" value="Yes">&nbsp;Right
																				Iliac</label> <%
 	}
 					}
 %>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getHypogastric() == null) {
																			%> <label><input
																				type="checkbox" name="hypogastric" value="Yes">&nbsp;Hypogastric</label>
																			<%
																				} else {
																									if (form4.getHypogastric().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="hypogastric"
																				value="Yes">&nbsp;Hypogastric</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="hypogastric" value="Yes">&nbsp;Hypogastric</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftIliac() == null) {
																			%> <label><input
																				type="checkbox" name="leftIliac" value="Yes">&nbsp;Left
																				Iliac</label> <%
 	} else {
 						if (form4.getLeftIliac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="leftIliac"
																				value="Yes">&nbsp;Left Iliac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftIliac" value="Yes">&nbsp;Left
																				Iliac</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>

													<%
														} else if (form4.getAbdomen().equals("Splenomegaly")) {
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-1"></div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="hepatomegalyID" style=""
																onclick="showDiv('abdomenDivID');" value="Hepatomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('hepatomegalyID');"><b>Hepatomegaly</b></font>
														</div>

														<div class="col-md-1" align="right">
															<label><input type="radio" name="abdomen"
																id="splenomegalyID" checked="checked" style=""
																onclick="showDiv('abdomenDivID');" value="Splenomegaly"></label>
														</div>

														<div class="col-md-2">
															<font style="font-size: 14px; cursor: pointer;"
																onclick="checkRadioBtn('splenomegalyID');"><b>Splenomegaly</b></font>
														</div>

													</div>

													<div class="row" style="margin-top: 10px;"
														id="abdomenDivID">
														<div class="col-md-1"></div>

														<div class="col-md-6">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<%
																			if (form4.getRightHypochondriac() == null) {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightHypochondriac"
																				value="Yes">&nbsp;Right Hypochondriac</label></td>
																		<%
																			} else {
																								if (form4.getRightHypochondriac().equals("Yes")) {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" checked="checked"
																				name="rightHypochondriac" value="Yes">&nbsp;Right
																				Hypochondriac</label></td>
																		<%
																			} else {
																		%>
																		<td style="padding: 10px;"><label><input
																				type="checkbox" name="rightHypochondriac"
																				value="Yes">&nbsp;Right Hypochondriac</label></td>
																		<%
																			}
																							}
																		%>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getEpigastric() == null) {
																			%> <label><input
																				type="checkbox" name="epigastric" value="Yes">&nbsp;Epigastric</label>
																			<%
																				} else {
																									if (form4.getEpigastric().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="epigastric"
																				value="Yes">&nbsp;Epigastric</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="epigastric" value="Yes">&nbsp;Epigastric</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftHypochondriac() == null) {
																			%> <label><input
																				type="checkbox" name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	} else {
 						if (form4.getLeftHypochondriac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked"
																				name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftHypochondriac" value="Yes">&nbsp;Left
																				Hypochondriac</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>

																	<tr>
																		<td style="padding: 10px;">
																			<%
																				if (form4.getRightLumbar() == null) {
																			%> <label><input
																				type="checkbox" name="rightLumbar" value="Yes">&nbsp;Right
																				Lumbar</label> <%
 	} else {
 						if (form4.getRightLumbar().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="rightLumbar"
																				value="Yes">&nbsp;Right Lumbar</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="rightLumbar" value="Yes">&nbsp;Right
																				Lumbar</label> <%
 	}
 					}
 %>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getUmbilical() == null) {
																			%> <label><input
																				type="checkbox" name="umbilical" value="Yes">&nbsp;Umbilical</label>
																			<%
																				} else {
																									if (form4.getUmbilical().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="umbilical"
																				value="Yes">&nbsp;Umbilical</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="umbilical" value="Yes">&nbsp;Umbilical</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftLumbar() == null) {
																			%> <label><input
																				type="checkbox" name="leftLumbar" value="Yes">&nbsp;Left
																				Lumbar</label> <%
 	} else {
 						if (form4.getLeftLumbar().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="leftLumbar"
																				value="Yes">&nbsp;Left Lumbar</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftLumbar" value="Yes">&nbsp;Left
																				Lumbar</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>
																	<tr>
																		<td style="padding: 10px;">
																			<%
																				if (form4.getRightIliac() == null) {
																			%> <label><input
																				type="checkbox" name="rightIliac" value="Yes">&nbsp;Right
																				Iliac</label> <%
 	} else {
 						if (form4.getRightIliac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="rightIliac"
																				value="Yes">&nbsp;Right Iliac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="rightIliac" value="Yes">&nbsp;Right
																				Iliac</label> <%
 	}
 					}
 %>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getHypogastric() == null) {
																			%> <label><input
																				type="checkbox" name="hypogastric" value="Yes">&nbsp;Hypogastric</label>
																			<%
																				} else {
																									if (form4.getHypogastric().equals("Yes")) {
																			%> <label><input
																				type="checkbox" checked="checked" name="hypogastric"
																				value="Yes">&nbsp;Hypogastric</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="hypogastric" value="Yes">&nbsp;Hypogastric</label>
																			<%
																				}
																								}
																			%>
																		</td>

																		<td style="padding: 10px;">
																			<%
																				if (form4.getLeftIliac() == null) {
																			%> <label><input
																				type="checkbox" name="leftIliac" value="Yes">&nbsp;Left
																				Iliac</label> <%
 	} else {
 						if (form4.getLeftIliac().equals("Yes")) {
 %> <label><input
																				type="checkbox" checked="checked" name="leftIliac"
																				value="Yes">&nbsp;Left Iliac</label> <%
 	} else {
 %> <label><input
																				type="checkbox" name="leftIliac" value="Yes">&nbsp;Left
																				Iliac</label> <%
 	}
 					}
 %>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>


													<%
														}
																}
													%>

													<%
														}
													%>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Significant
																Findings</font>
														</div>

													</div>

													<div class="row"
														style="margin-top: 10px; padding: 0 15px 0 15px;">

														<div class="col-md-12">
															<s:textarea class="form-control" name="biopsyFindings"
																placeholder="Significant Findings"></s:textarea>
														</div>
													</div>

													<div class="row" style="margin-top: 10px;">

														<div class="col-md-3">
															<font style="font-size: 14px; font-weight: bold;">Provisional
																Diagnosis*</font>
														</div>

														<div class="col-md-3">

															<sx:autocompleter name="diagnosis"
																cssStyle="padding-left:14px;"
																id="ProvisionalDiagnosisID" cssClass="form-control"
																list="DiagnosisList" showDownArrow="false" />

														</div>

													</div>

													<div class="ln_solid"></div>

													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>

															<%
																if (printAvailable.equals("Yes")) {
															%>

															<button type="button" class="btn btn-success"
																disabled="disabled" id="VisitSubmitID"
																onclick="checkDiagnosis();">Add Visit</button>
															<button class="btn btn-warning" type="button"
																id="printButton" onclick="printVisit();">Print</button>
															<%
																} else {
															%>

															<button type="button" class="btn btn-success"
																id="VisitSubmitID" onclick="checkDiagnosis();">Add
																Visit</button>

															<%
																}
															%>
														</div>
													</div>

												</form>
											</div>
											<!-- End -->


											<!-- Prescription div -->
											<div class="tab-pane fade" id="presc">
												<form class="form-horizontal form-label-left" novalidate
													id="genPhyPrescID" name="genPhyPrescID"
													onsubmit="return updatePrescription();"
													action="AddNewGeneralHospitalPrescription" method="POST"
													style="margin-top: 20px;">

													<div class="row"
														style="margin-top: 10px; margin-left: 0px;">
														<%-- <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5> --%>
													</div>

													<input type="hidden" name="patientID" id="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="aptID" id="aptID" value="<%=apptID%>">
													<input type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>">

													<div class="row">
														<div class="col-md-4">
															<img
																src="<%=daoInf.retrieveClinicLogo(form.getClinicID())%>"
																alt="Clinic Logo"
																style="height: 53px; padding: 0px; margin-left: 15px;"
																class="img-circle profile_img">
														</div>
														<div class="col-md-4">
															<h4 style="margin-top: 20px;" align="center">
																<b>Prescription</b>
															</h4>
														</div>
														<div class="col-md-4">
															<h2 style="font-size: 15px; margin-top: 20px;">
																Consultant:<%=form.getFullName()%></h2>
														</div>
													</div>

													<div class="row" style="margin-top: 15px;">
														<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
															<table border="1" width="100%">
																<tr>
																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Name: <input type="text"
																		style="border: none; cursor: not-allowed; background: transparent;"
																		class="form-control" readonly="readonly"
																		value="<s:property value="lastName"/> <s:property value="firstName"/> <s:property value="middleName"/>"
																		name="" placeholder="Patient Name" autofocus="">
																	</td>

																	<td colspan="1"
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">
																		Date of visit: <input type="text" class="form-control"
																		name=""
																		style="border: none; cursor: not-allowed; background: transparent;"
																		readonly="readonly"
																		value="<s:property value="visitDate"/>"
																		aria-describedby="inputSuccess2Status3">
																	</td>

																	<td
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">Age:
																		<input type="number"
																		style="border: none; background: transparent; cursor: not-allowed;"
																		readonly="readonly" class="form-control" name="age"
																		value="<s:property value="age"/>" placeholder="Age"
																		autofocus="">
																	</td>

																	<td
																		style="padding-left: 10px; font-size: 14px; font-weight: bold;">Weight:
																		<input type="text" name="weight" class="form-control"
																		value="<s:property value="weight"/>"
																		placeholder="Weight">
																	</td>
																</tr>
															</table>
														</div>
													</div>
													<div class="row"
														style="margin-top: 10px; margin-left: 0px; margin-bottom: 5px;">
														<div class="row" id="OPDPResc"
															style="margin-left: 0px; margin-right: 0px; margin-top: 15px;">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<thead>
																	<tr>
																		<th>Injection</th>
																		<th>Before Meal/जेवणा अगोदर</th>
																		<th>After Meal/जेवणा नंतर</th>
																		<th>After Dinner</th>
																		<th>Comment</th>
																		<th>Duration(Days/दिवस )</th>
																		<th>Action</th>
																	</tr>
																</thead>

																<tbody>
																	<tr id="prescTRID">
																	</tr>

																	<s:iterator value="InjectionPrescriptionList">
																		<tr
																			id="visitPrescTRID<s:property value="prescriptionID"/>">
																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="tradeName" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageBeforeMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterDinner" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="comment" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="duration" /></td>

																			<td style="text-align: center;"><img
																				src='images/delete.png'
																				style='height: 24px; cursor: pointer;'
																				onclick="deleteRow1(<s:property value="prescriptionID" />,'visitPrescTRID<s:property value="prescriptionID"/>', 'Injection');"
																				alt='Delete row' title='Delete row' /></td>
																		</tr>
																	</s:iterator>
																</tbody>
															</table>
														</div>

														<div class="col-md-2">
															<sx:autocompleter name="tradeName"
																cssStyle="padding-left:14px;" cssClass="form-control"
																list="InjectionList" showDownArrow="false" />
														</div>
														<%-- <div class="col-md-3" >
				            	<s:select list="InjectionList" headerKey="-1" headerValue="Select Injection" class="form-control" name="tradeName" id="injectionID" ></s:select>
							</div> --%>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageBeforeMealID" placeholder="Before Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterMealID" placeholder="After Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterDinnerID" placeholder="After Dinner">
														</div>
														<div class="col-md-2 col-xs-3">
															<textarea rows="1" cols="1" name="" class="form-control"
																id="dosageCommentID" placeholder="Comment"></textarea>
														</div>
														<div class="col-md-1">
															<input type="number" name="" id="durationID"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-1">
															<a
																onclick="addInjectionPrescriptionRow('',dosageBeforeMealID.value,dosageAfterMealID.value,dosageAfterDinnerID.value,dosageCommentID.value,durationID.value, 'Injection');">
																<img src="images/add_icon_1.png"
																onmouseover="this.src='images/add_icon_2.png'"
																onmouseout="this.src='images/add_icon_1.png'"
																alt="Add Prescription" title="Add Prescription"
																style="margin-top: 5px; height: 24px;" />
															</a>
														</div>
													</div>

													<div class="row"
														style="margin-top: 10px; margin-left: 0px; margin-bottom: 5px;">
														<div class="row" id="OPDPResc"
															style="margin-left: 0px; margin-right: 0px; margin-top: 15px;">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<thead>
																	<tr>
																		<th>Tablet/Capsule</th>
																		<th>Before Meal/जेवणा अगोदर</th>
																		<th>After Meal/जेवणा नंतर</th>
																		<th>After Dinner</th>
																		<th>Comment</th>
																		<th>Duration(Days/दिवस )</th>
																		<th>Action</th>
																	</tr>
																</thead>

																<tbody>
																	<tr id="prescTabTRID">
																	</tr>

																	<s:iterator value="TabletPrescriptionList">
																		<tr
																			id="visitPrescTabletTRID<s:property value="prescriptionID"/>">
																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="tradeName" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageBeforeMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterDinner" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="comment" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="duration" /></td>

																			<td style="text-align: center;"><img
																				src='images/delete.png'
																				style='height: 24px; cursor: pointer;'
																				onclick="deleteRow1(<s:property value="prescriptionID" />,'visitPrescTabletTRID<s:property value="prescriptionID"/>', 'Tablet');"
																				alt='Delete row' title='Delete row' /></td>
																		</tr>
																	</s:iterator>
																</tbody>
															</table>
														</div>

														<div class="col-md-2">
															<sx:autocompleter name="tradeNameTablet"
																cssStyle="padding-left:14px;" cssClass="form-control"
																list="TabletList" showDownArrow="false" />

														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageBeforeMealTabID" placeholder="Before Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterMealTabID" placeholder="After Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterDinnerTabID" placeholder="After Dinner">
														</div>
														<div class="col-md-2 col-xs-3">
															<textarea rows="1" cols="1" name="" class="form-control"
																id="dosageCommentTabID" placeholder="Comment"></textarea>
														</div>
														<div class="col-md-1">
															<input type="number" name="" id="durationTabID"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-1">
															<a
																onclick="addTabletPrescriptionRow('',dosageBeforeMealTabID.value,dosageAfterMealTabID.value,dosageAfterDinnerTabID.value,dosageCommentTabID.value,durationTabID.value, 'Tablet');">
																<img src="images/add_icon_1.png"
																onmouseover="this.src='images/add_icon_2.png'"
																onmouseout="this.src='images/add_icon_1.png'"
																alt="Add Prescription" title="Add Prescription"
																style="margin-top: 5px; height: 24px;" />
															</a>
														</div>
													</div>

													<div class="row"
														style="margin-top: 10px; margin-left: 0px; margin-bottom: 5px;">
														<div class="row" id="OPDPResc"
															style="margin-left: 0px; margin-right: 0px; margin-top: 15px;">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<thead>
																	<tr>
																		<th>Liquid</th>
																		<th>Before Meal/जेवणा अगोदर</th>
																		<th>After Meal/जेवणा नंतर</th>
																		<th>After Dinner</th>
																		<th>Comment</th>
																		<th>Duration(Days/दिवस )</th>
																		<th>Action</th>
																	</tr>
																</thead>

																<tbody>
																	<tr id="prescLiquidTRID">
																	</tr>

																	<s:iterator value="LiquidPrescriptionList">
																		<tr
																			id="visitPrescLiquidTRID<s:property value="prescriptionID"/>">
																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="tradeName" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageBeforeMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterMeal" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="dosageAfterDinner" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="comment" /></td>

																			<td style="font-size: 14px; text-align: center;"><s:property
																					value="duration" /></td>

																			<td style="text-align: center;"><img
																				src='images/delete.png'
																				style='height: 24px; cursor: pointer;'
																				onclick="deleteRow1(<s:property value="prescriptionID" />,'visitPrescLiquidTRID<s:property value="prescriptionID"/>', 'Liquid');"
																				alt='Delete row' title='Delete row' /></td>
																		</tr>
																	</s:iterator>
																</tbody>
															</table>
														</div>

														<div class="col-md-2">
															<sx:autocompleter name="tradeNameLiquid"
																cssStyle="padding-left:14px;" cssClass="form-control"
																list="LiquidList" showDownArrow="false" />
															<%-- <s:select list="LiquidList" headerKey="-1" headerValue="Select Liquid" class="form-control" name="tradeName" id="liquidID" ></s:select> --%>
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageBeforeMealLiqID" placeholder="Before Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterMealLiqID" placeholder="After Meal">
														</div>
														<div class="col-md-2">
															<input type="number" class="form-control" name=""
																id="dosageAfterDinnerLiqID" placeholder="After Dinner">
														</div>
														<div class="col-md-2 col-xs-3">
															<textarea rows="1" cols="1" name="" class="form-control"
																id="dosageCommentLiqID" placeholder="Comment"></textarea>
														</div>
														<div class="col-md-1">
															<input type="number" name="" id="durationLiqID"
																class="form-control" placeholder="Duration">
														</div>

														<div class="col-md-1">
															<a
																onclick="addLiquidPrescriptionRow('',dosageBeforeMealLiqID.value,dosageAfterMealLiqID.value,dosageAfterDinnerLiqID.value,dosageCommentLiqID.value,durationLiqID.value, 'Liquid');">
																<img src="images/add_icon_1.png"
																onmouseover="this.src='images/add_icon_2.png'"
																onmouseout="this.src='images/add_icon_1.png'"
																alt="Add Prescription" title="Add Prescription"
																style="margin-top: 5px; height: 24px;" />
															</a>
														</div>
													</div>

													<div class="row" style="margin-top: 40px;">
														<div class="col-md-4">
															<font style="font-size: 14px; font-weight: bold;">Prescribed
																Investigations: </font>
														</div>
													</div>

													<table id="datatable-responsive"
														class="table table-striped table-bordered dt-responsive nowrap"
														cellspacing="0" width="100%">
														<tbody>

															<tr>
																<%
																	int j = 0;
																		for (PrescriptionManagementForm formOPD : LabTestList) {

																			if ((j % 4) == 0) {
																%>
															</tr>
															<tr>
																<td style="text-align: center;"><input
																	type="checkbox" name="investigation" style="zoom: 1.5;"
																	value="<%=formOPD.getTest()%>"></td>
																<td><%=formOPD.getTest()%></td>
																<%
																	} else {
																%>
																<td style="text-align: center;"><input
																	type="checkbox" name="investigation" style="zoom: 1.5;"
																	value="<%=formOPD.getTest()%>"></td>
																<td><%=formOPD.getTest()%></td>
																<%
																	}
																%>

																<script type="text/javascript">
			
			 document.addEventListener('DOMContentLoaded', function() {
				var Details = '<%=InvestigationDetails%>';
				
				if(Details!=""){
					if(Details.includes(",")){
						var array = Details.split(",");
						console.log("array:"+array);	
						for(var i = 0; i<array.length; i++){
							console.log("array value:"+array[i]);	
							$("input[name='investigation'][value='"+array[i].trim()+"']").prop("checked", true);
						}
					}else{
								
						$("input[name='investigation'][value='"+Details.trim()+"']").prop("checked", true);
					}
				}
			}); 
			
			</script>

																<%
																	j++;
																		}
																%>

															</tr>
														</tbody>
													</table>
													<div class="row" style="margin-top: 10px;">
														<div class="col-md-2">
															<font style="font-size: 14px; font-weight: bold;">Physiotherapy</font>
														</div>

														<div class="col-md-2">
															<s:textarea name="physiotherapy" id="PhysiotherapyID"
																class="form-control" placeholder="Physiotherapy"></s:textarea>
														</div>
													</div>
													<div class="row" style="margin-top: 10px;">
														<div class="col-md-2">
															<font style="font-size: 14px; font-weight: bold;">Follow-up</font>
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control"
																name="nextVisitDays"
																value="<s:property value="nextVisitDays"/>"
																id="visitDayID" placeholder="Days">
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control"
																name="nextVisitWeeks"
																value="<s:property value="nextVisitWeeks"/>"
																id="visitWeekID" placeholder="Weeks">
														</div>
														<div class="col-md-1 col-xs-2">
															<input type="text" class="form-control"
																name="nextVisitMonths"
																value="<s:property value="nextVisitMonths"/>"
																id="visitMonthID" placeholder="Months">
														</div>
													</div>

													<div class="ln_solid"></div>
													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>
															<%
																if (lastEneteredVisitList.equals("success")) {
															%>
															<button type="submit" id="savePrescID"
																class="btn btn-success" name="addButton" value="add">Save</button>
															<%
																} else {
															%>
															<button type="submit" id="savePrescID"
																class="btn btn-success" name="addButton" value="add">Save</button>
															<%
																}
															%>
															<%
																if (printPrescAvailable.equals("Yes")) {
															%>
															<button class="btn btn-warning" type="submit"
																name="addButton" value="print">Print</button>
															<%
																}
															%>

														</div>
													</div>

												</form>

											</div>
											<!-- End -->


											<!-- Billing div -->
											<div class="tab-pane fade" id="billing">

												<form class="form-horizontal form-label-left"
													id="genPhyBillID" onsubmit="return checkCheckbox();"
													name="genPhyBillID" action="AddNewGeneralHospitalBill"
													method="POST" style="margin-top: 20px;">

													<div class="row"
														style="margin-top: 10px; margin-left: 0px;">
														<h5 style="margin-top: 0px; font-size: 14px;">
															Patient Name:
															<s:property value="lastName" />
															&nbsp;
															<s:property value="firstName" />
															&nbsp;
															<s:property value="middleName" />
															&nbsp;(
															<s:property value="registrationNo" />
															)
														</h5>
													</div>

													<input type="hidden" name="patientID" id="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="aptID" id="aptID" value="<%=apptID%>">
													<input type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>">

													<s:iterator value="billList">

														<div class="row" style="margin-top: 15px;">

															<div class="col-md-2">
																<font style="font-size: 14px;">Receipt Date:</font>
															</div>
															<div class="col-md-4">
																<font style="font-size: 14px; font-weight: bold;"><s:property
																		value="receiptDate" /></font> <input type="hidden"
																	name="receiptDate"
																	value="<s:property value="receiptDate"/>">
															</div>
															<div class="col-md-2">
																<font style="font-size: 14px;">Receipt No:</font>
															</div>
															<div class="col-md-4">
																<font style="font-size: 14px; font-weight: bold;"><s:property
																		value="receiptNo" /></font> <input type="hidden"
																	name="receiptNo"
																	value="<s:property value="receiptNo"/>">
															</div>
														</div>

														<div class="item form-group"
															style="margin-top: 15px; padding-left: 15px;">
															<table id="datatable-responsive"
																class="table table-striped table-bordered dt-responsive nowrap"
																cellspacing="0" width="100%">
																<thead>
																	<tr>
																		<th colspan="4" align="right"
																			style="text-align: right;">Consultation Charges:
																		</th>
																		<th><input type="hidden" name="billingType"
																			value="<s:property value="billingType"/>"><input
																			type="number" readonly="readonly"
																			class="form-control"
																			value="<s:property value="charges"/>" name="charges"
																			id="consultationChargeID"></th>
																	</tr>
																	<tr>
																		<th></th>
																		<th>OPD Charge Type</th>
																		<th>Quantity</th>
																		<th>Rate</th>
																		<th>Amount</th>
																	</tr>
																</thead>
																<tbody>

																	<%
																		int i = 0;
																				for (PrescriptionManagementForm formOPD : OPDChargesList) {

																					if (ChargesDetails.contains(formOPD.getChargeType())) {

																						if (ChargesDetails.contains(",")) {

																							String[] Charges = ChargesDetails.split(",");

																							String[] ChargeValues = Charges[i].split("#");
																	%>
																	<tr>
																		<td><input type="checkbox" name="checkboxName"
																			checked="checked"
																			id="OPDChargeTypeID<%=ChargeValues[0]%>"
																			style="zoom: 1.5;"
																			onclick="changeTotalAmtByQuantyRate('OPDChargeTypeID<%=ChargeValues[0]%>', 'OPDChargesQuantyID<%=ChargeValues[0]%>', 'OPDChargesRateID<%=ChargeValues[0]%>', 'OPDChargesAmountID<%=ChargeValues[0]%>', 'totalAmountID', 'chargeID<%=ChargeValues[0]%>', 'chargeTypeID<%=ChargeValues[0]%>');"
																			value="Yes"></td>
																		<td><input type="hidden" name="chargeID"
																			id="chargeID<%=ChargeValues[0]%>"
																			value="<%=ChargeValues[0]%>"><input
																			type="hidden" name="chargeType"
																			id="chargeTypeID<%=ChargeValues[0]%>"
																			value="<%=ChargeValues[1]%>"><%=ChargeValues[1]%></td>
																		<td><input type="number" name="chargeQuantity"
																			id="OPDChargesQuantyID<%=ChargeValues[0]%>"
																			onkeyup="changeChargesAmount('OPDChargeTypeID<%=ChargeValues[0]%>', 'OPDChargesQuantyID<%=ChargeValues[0]%>', 'OPDChargesRateID<%=ChargeValues[0]%>', 'OPDChargesAmountID<%=ChargeValues[0]%>', 'totalAmountID', consultationChargeID.value);"
																			class="form-control" value="<%=ChargeValues[2]%>"></td>
																		<td><input type="number" name="chargeRate"
																			id="OPDChargesRateID<%=ChargeValues[0]%>"
																			class="form-control" readonly="readonly"
																			value="<%=ChargeValues[3]%>"></td>
																		<td><input type="number" name="chargeAmount"
																			id="OPDChargesAmountID<%=ChargeValues[0]%>"
																			class="form-control" readonly="readonly"
																			value="<%=ChargeValues[4]%>"></td>
																	</tr>
																	<%
																		i++;

																						} else {

																							i = 0;

																							String[] ChargeValues = ChargesDetails.split("#");
																	%>

																	<tr>
																		<td><input type="checkbox" name="checkboxName"
																			checked="checked"
																			id="OPDChargeTypeID<%=ChargeValues[0]%>"
																			style="zoom: 1.5;"
																			onclick="changeTotalAmtByQuantyRate('OPDChargeTypeID<%=ChargeValues[0]%>', 'OPDChargesQuantyID<%=ChargeValues[0]%>', 'OPDChargesRateID<%=ChargeValues[0]%>', 'OPDChargesAmountID<%=ChargeValues[0]%>', 'totalAmountID', 'chargeID<%=ChargeValues[0]%>', 'chargeTypeID<%=ChargeValues[0]%>');"
																			value="Yes"></td>
																		<td><input type="hidden" name="chargeID"
																			id="chargeID<%=ChargeValues[0]%>"
																			value="<%=ChargeValues[0]%>"><input
																			type="hidden" name="chargeType"
																			id="chargeTypeID<%=ChargeValues[0]%>"
																			value="<%=ChargeValues[1]%>"><%=ChargeValues[1]%></td>
																		<td><input type="number" name="chargeQuantity"
																			id="OPDChargesQuantyID<%=ChargeValues[0]%>"
																			onkeyup="changeChargesAmount('OPDChargeTypeID<%=ChargeValues[0]%>', 'OPDChargesQuantyID<%=ChargeValues[0]%>', 'OPDChargesRateID<%=ChargeValues[0]%>', 'OPDChargesAmountID<%=ChargeValues[0]%>', 'totalAmountID', consultationChargeID.value);"
																			class="form-control" value="<%=ChargeValues[2]%>"></td>
																		<td><input type="number" name="chargeRate"
																			id="OPDChargesRateID<%=ChargeValues[0]%>"
																			class="form-control" readonly="readonly"
																			value="<%=ChargeValues[3]%>"></td>
																		<td><input type="number" name="chargeAmount"
																			id="OPDChargesAmountID<%=ChargeValues[0]%>"
																			class="form-control" readonly="readonly"
																			value="<%=ChargeValues[4]%>"></td>
																	</tr>
																	<%
																		}

																					} else {
																	%>

																	<tr>
																		<td><input type="checkbox" name="checkboxName"
																			id="OPDChargeTypeID<%=formOPD.getChargesID()%>"
																			style="zoom: 1.5;"
																			onclick="changeTotalAmtByQuantyRate('OPDChargeTypeID<%=formOPD.getChargesID()%>', 'OPDChargesQuantyID<%=formOPD.getChargesID()%>', 'OPDChargesRateID<%=formOPD.getChargesID()%>', 'OPDChargesAmountID<%=formOPD.getChargesID()%>', 'totalAmountID', 'chargeID<%=formOPD.getChargesID()%>', 'chargeTypeID<%=formOPD.getChargesID()%>');"
																			value="Yes"></td>
																		<td><input type="hidden" name=""
																			id="chargeID<%=formOPD.getChargesID()%>"
																			value="<%=formOPD.getChargesID()%>"><input
																			type="hidden" name=""
																			id="chargeTypeID<%=formOPD.getChargesID()%>"
																			value="<%=formOPD.getChargeType()%>"><%=formOPD.getChargeType()%></td>
																		<td><input type="number" name=""
																			id="OPDChargesQuantyID<%=formOPD.getChargesID()%>"
																			onkeyup="changeChargesAmount('OPDChargeTypeID<%=formOPD.getChargesID()%>', 'OPDChargesQuantyID<%=formOPD.getChargesID()%>', 'OPDChargesRateID<%=formOPD.getChargesID()%>', 'OPDChargesAmountID<%=formOPD.getChargesID()%>', 'totalAmountID', consultationChargeID.value);"
																			class="form-control" value="1"></td>
																		<td><input type="number" name=""
																			id="OPDChargesRateID<%=formOPD.getChargesID()%>"
																			class="form-control" readonly="readonly"
																			value="<%=formOPD.getCharges()%>"></td>
																		<td><input type="number" name=""
																			id="OPDChargesAmountID<%=formOPD.getChargesID()%>"
																			class="form-control" readonly="readonly"></td>
																	</tr>
																	<%
																		}

																				}
																	%>

																</tbody>

																<tbody>
																	<tr>
																		<td colspan="4" align="right"><font
																			style="font-size: 14px; font-weight: bold;">Total
																				Amount</font></td>

																		<td><input type="number" readonly="readonly"
																			class="form-control" name="totalAmount"
																			value="<s:property value="totalAmount"/>"
																			id="totalAmountID"></td>
																	</tr>
																	<tr>
																		<td colspan="4" align="right"><font
																			style="font-size: 14px; font-weight: bold;">Concession
																				if any</font></td>
																		<td><input type="number" class="form-control"
																			onkeyup="changeNetAmtByDiscount(this.value,totalAmountID.value);"
																			value="<s:property value="totalDiscount"/>"
																			name="totalDiscount" id="totalVatID"></td>
																	</tr>
																	<tr>
																		<td colspan="4" align="right"><font
																			style="font-size: 14px; font-weight: bold;">Net
																				Amount</font></td>
																		<td><input type="number" readonly="readonly"
																			class="form-control"
																			value="<s:property value="netAmount"/>"
																			name="netAmount" id="netAmountID"></td>
																	</tr>
																	<%-- <tr>
									<td colspan="4" align="right">
										<font style="font-size: 14px; font-weight: bold;">Advance Payment</font>
									</td>
									<td>
										<input type="number" class="form-control" name="advPayment" id="advPaymentID" value = "<s:property value="advPayment"/>" onkeyup="changeBalancePayment(advPaymentID.value, netAmountID.value);">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right">
										<font style="font-size: 14px; font-weight: bold;">Balance Payment</font>
									</td>
									<td>
										<input type="number" class="form-control" name="balPayment" value = "<s:property value="balPayment"/>" id="balPaymentID">
									</td>
								</tr> --%>
																</tbody>

															</table>
														</div>

														<div class="row" style="padding: 0 15px 0 15px;">
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8"
																style="padding-top: 5px;">
																<font style="font-size: 16px;">Payment Type*</font>
															</div>
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
																<div class="col-md-4 col-sm-4 col-xs-6">
																	<input type="checkbox" name="paymentType"
																		onclick="hideChequeDetailsDiv(this);"
																		style="height: 25px; box-shadow: none;"
																		id="paymentTypeCashID" value="Cash"
																		class="checkboxClass form-control">
																</div>
																<div class="col-md-8 col-sm-8 col-xs-6"
																	style="padding-top: 5px;">
																	<font style="font-size: 15px;">Cash</font>
																</div>
															</div>
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
																<div class="col-md-4 col-sm-4 col-xs-6">
																	<input type="checkbox" name="paymentType"
																		onclick="displayChequeDetailsDiv(this);"
																		style="height: 25px; box-shadow: none;"
																		id="paymentTypeChequeID" value="Cheque"
																		class="checkboxClass form-control">
																</div>
																<div class="col-md-4 col-sm-4 col-xs-6"
																	style="padding-top: 5px;">
																	<font style="font-size: 15px;">Cheque</font>
																</div>
															</div>
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
																<div class="col-md-4 col-sm-4 col-xs-6">
																	<input type="checkbox" name="paymentType"
																		onclick="displayCardDiv(this);"
																		style="height: 25px; box-shadow: none;"
																		id="paymentTypeCardID" value="Credit/Debit Card"
																		class="checkboxClass form-control">
																</div>
																<div class="col-md-8 col-sm-4 col-xs-6"
																	style="padding-top: 5px;">
																	<font style="font-size: 15px;">Credit/Debit Card</font>
																</div>
															</div>
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
																<div class="col-md-4 col-sm-4 col-xs-6">
																	<input type="checkbox" name="paymentType"
																		onclick="displayChequeDetailsDivCreditNote(this);"
																		style="height: 25px; box-shadow: none;"
																		id="paymentTypeCreditNoteID" value="Credit Note"
																		class="checkboxClass form-control">

																</div>
																<div class="col-md-8 col-sm-4 col-xs-6"
																	style="padding-top: 5px;">
																	<font style="font-size: 15px;">Credit Note</font>
																</div>
															</div>
															<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
																<div class="col-md-4 col-sm-4 col-xs-6">
																	<input type="checkbox" name="paymentType"
																		onclick="displayChequeDetailsDivOther(this);"
																		style="height: 25px; box-shadow: none;"
																		id="paymentTypeOtherID" value="Other"
																		class="checkboxClass form-control">
																</div>
																<div class="col-md-8 col-sm-4 col-xs-6"
																	style="padding-top: 5px;">
																	<font style="font-size: 15px;">Other</font>
																</div>
															</div>
														</div>
														<%-- <div id="cashDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cash Paid</font><span class="required" >*</span>
								</div>
								<div class="col-md-2">
									<input type="text" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, netAmountID.value);"
											value="<s:property value="cashPaid"/>" placeholder="Cash Paid" >
								</div>
								<div class="col-md-2 col-xs-6" >
									<font style="font-size: 16px;">Cash To Return</font>
								</div>
								<div class="col-md-2">
									<input class="form-control" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>" placeholder="Cash To Return" type="number">
								</div>
							</div>
						</div> --%>
														<div id="chequeDetailID"
															style="padding: 15px; display: none;">

															<div class="ln_solid"></div>
															<div class="row">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Cheque Issued By</font>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="chequeIssuedByID" name="chequeIssuedBy"
																		value="<s:property value="chequeIssuedBy"/>"
																		placeholder="Cheque Issued By">
																</div>
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Cheque No.</font><span
																		class="required">*</span>
																</div>
																<div class="col-md-3">
																	<input class="form-control" name="chequeNo"
																		id="chequeNoID" value="<s:property value="chequeNo"/>"
																		placeholder="Cheque No." type="text">
																</div>
															</div>
															<div class="row" style="margin-top: 15px;">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Bank Name</font><span
																		class="required">*</span>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="chequeBankNameID"
																		value="<s:property value="chequeBankName"/>"
																		name="chequeBankName" placeholder="Bank Name">
																</div>
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Branch</font><span
																		class="required">*</span>
																</div>
																<div class="col-md-3">
																	<input class="form-control" name="chequeBankBranch"
																		id="chequeBankBranchID"
																		value="<s:property value="chequeBankBranch"/>"
																		placeholder="Branch" type="text">
																</div>
															</div>
															<div class="row" style="margin-top: 15px;">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Date</font>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		name="chequeDate"
																		value="<s:property value="chequeDate"/>"
																		id="chequeDateID" placeholder="Date">
																</div>
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Amount</font><span
																		class="required">*</span>
																</div>
																<div class="col-md-3">
																	<input class="form-control" name="chequeAmt"
																		id="chequeAmtID"
																		value="<s:property value="chequeAmt"/>"
																		placeholder="Amount" type="number">
																</div>
															</div>
														</div>
														<div id="cardDetailID"
															style="padding: 15px; display: none;">

															<div class="ln_solid"></div>
															<div class="row">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Amount</font>
																</div>
																<div class="col-md-3">
																	<input type="number" class="form-control"
																		id="cardAmountID"
																		value="<s:property value="cardAmount"/>"
																		name="cardAmount" placeholder="Amount">
																</div>
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Card No.</font>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="cardMobileNoID"
																		value="<s:property value="cardMobileNo"/>"
																		name="cardMobileNo" placeholder="Card No.">
																</div>
															</div>
															<div class="row">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Mobile No.</font>
																</div>
																<div class="col-md-3">
																	<input type="number" class="form-control"
																		id="cMobileNoID"
																		value="<s:property value="cMobileNo"/>"
																		name="cMobileNo" placeholder="Mobile No."
																		onKeyPress="if(this.value.length==10) return false;"
																		aria-describedby="inputGroupSuccess1Status">
																</div>
															</div>
														</div>
														<div id="creditNoteDetailID"
															style="padding: 15px; display: none;">

															<div class="ln_solid"></div>
															<div class="row">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Credit Balance</font>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="creditNoteBalID" name="creditNoteBal"
																		value="<s:property value="creditNoteBal"/>"
																		placeholder="Credit Note Balance">
																</div>
															</div>
														</div>

														<div id="otherDetailID"
															style="padding: 15px; display: none;">

															<div class="ln_solid"></div>

															<div class="row">
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Other Type</font>
																</div>
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="otherTypeID" name="otherType"
																		value="<s:property value="otherType"/>"
																		placeholder="Enter Other payment here">
																</div>
																<div class="col-md-3 col-xs-6">
																	<font style="font-size: 16px;">Amount</font>
																</div>
																<div class="col-md-3">
																	<input type="number" class="form-control"
																		id="otherTypeAmountID" name="otherAmount"
																		value="<s:property value="otherAmount"/>"
																		placeholder="Enter Other amount here">
																</div>
															</div>
														</div>

													</s:iterator>
													<div class="ln_solid"></div>

													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>

															<button class="btn btn-success" type="submit">Save</button>

														</div>
													</div>
												</form>

												<div class="ln_solid"></div>

											</div>
											<!-- ends -->

											<!-- Lab report div -->
											<div class="tab-pane fade" id="labRep">

												<form class="form-horizontal form-label-left" novalidate
													action="AddLabReport" name="labRepForm" id="labRepForm"
													method="POST" style="margin-top: 20px;"
													enctype="multipart/form-data">

													<input type="hidden" name="patientID" id="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>">

													<div class="row"
														style="margin-top: 10px; margin-left: 0px; padding-left: 15px;">
														<h5 style="margin-top: 0px; font-size: 16px;">Upload
															Lab Report File(s) here</h5>
													</div>

													<div class="row" style="padding-left: 25px;">
														<div class="col-md-3"
															style="margin-top: 10px; margin-bottom: 10px;">
															<input type="file" name="labReport">
														</div>
														<div class="col-md-6" style="margin-top: 10px;">
															<button type="button" class="btn btn-default"
																onclick="myFunction();">Add More Files</button>
														</div>

														<div class="col-md-12" id="myID"></div>
													</div>

													<div class="row" style="padding: 15px;">
														<table id="labRepTableID"
															class="table table-striped table-bordered dt-responsive nowrap"
															cellspacing="0" width="100%">
															<thead>
																<tr>
																	<th style="width: 30%;">File Name</th>
																	<th style="width: 40%;">Description</th>
																	<th style="width: 30%; text-align: center;">Action</th>
																</tr>
															</thead>
															<tbody>
																<s:iterator value="labReportList" var="form">
																	<tr id="labRepTRID<s:property value="reportsID"/>">
																		<td><s:property value="reportDBName" /></td>
																		<td><s:property value="description" /></td>
																		<td style="text-align: center;">
																			<button class="btn btn-warning" type="button"
																				onclick="ViewLabReport('<s:property value="reportsID"/>');">View</button>
																			<%-- <button class="btn btn-success" type="button" onclick="downloadLabReport('<s:property value="reportsID"/>');">Download</button> --%>
																			<img src='images/delete.png'
																			style='height: 24px; cursor: pointer;'
																			alt='Remove row' title='Remove row'
																			onclick="deleteReportRow('labRepTRID<s:property value="reportsID"/>', <s:property value="reportsID"/>);" />
																		</td>
																	</tr>
																</s:iterator>
															</tbody>
														</table>
													</div>

													<div class="ln_solid"></div>

													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>

															<button class="btn btn-success" type="submit"
																id="uploadLabReportBtnID">Add Report</button>
														</div>
													</div>

												</form>
											</div>
											<!-- Ends -->


											<!-- Medical certificate -->
											<div class="tab-pane fade" id="medCerti">
												<form class="app-cam" id="" name=""
													action="AddMedicalCerificate" method="POST"
													style="width: 95%; margin: 0px;">

													<input type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="opticinID" id="opticinID"
														value="<s:property value="opticinID"/>"> <input
														type="hidden" name="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="lastVisitID" id="lastVisitID"
														value="<s:property value="lastVisitID"/>"> <input
														type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>"> <input
														type="hidden" id="doctrName" value="<%=fullName%>">
													<input type="hidden" id="patientName"
														value="<s:property value="firstName"/>  <s:property value="lastName"/>">

													<textarea style="display: none" name="medicalCerti"
														id="medicalCerti" cols="" rows="5"></textarea>
													<textarea style="display: none" name="medicalCertiForPDF"
														id="medicalCertiForPDF" cols="" rows="5"></textarea>

													<div class="row" style="margin: 15px;">

														<%
															if (medicalCertiText == null || medicalCertiText == "") {
														%>

														<div class="col-md-12 col-xs-12"
															style="border: 1px solid gray; margin: 25px;">

															<label for="Name"
																style="font-size: 14px; margin-top: 20px;">I,
																Dr. <%=fullName%> registered medical practitioner,
																after careful personal examination of the case hereby
																certify that, Sh./Smt. <s:property value="firstName" />
																<s:property value="lastName" /> working in <input
																type="text" class="form-control"
																style="width: 20%; display: inline-block; margin-top: 10px;"
																name="companyName" id="companyName">, is
																suffering from <input type="text" class="form-control"
																name="disease" id="disease"
																style="width: 20%; display: inline-block; margin-top: 10px;">
																and I consider that the period of absence from duty of <input
																type="text" class="form-control" name="dutyDays"
																id="dutyDays"
																style="width: 20%; display: inline-block; margin-top: 10px; margin-bottom: 10px;">
																days with effect from <input type="text"
																class="form-control" name="wef" id="wef"
																style="width: 20%; display: inline-block; margin-top: 10px; margin-bottom: 10px;">
																is absolutely necessary for the restoration of his/her
																health.
															</label>

															<div class="row" style="margin-top: 25px;">
																<div class="col-md-12 col-xs-12">
																	<div class="col-md-1" style="padding-top: 5px;">
																		<label>Place:</label>
																	</div>
																	<div class="col-md-3">
																		<input type="text" class="form-control" name="place"
																			id="place">
																	</div>
																</div>
															</div>

															<div class="row"
																style="margin-top: 25px; margin-bottom: 20%;">
																<div class="col-md-12 col-xs-12">
																	<div class="col-md-1" style="padding-top: 5px;">
																		<label>Date:</label>
																	</div>
																	<div class="col-md-3">
																		<input type="text" class="form-control"
																			value="<%=currentDate%>" name="date" id="date11">
																	</div>
																	<div class="col-md-4" style="padding-top: 20px;"
																		align="center">
																		<label>(SEAL)</label>
																	</div>
																	<div class="col-md-4" style="padding-top: 20px;">
																		<label>Registered Medical Practitioner</label>
																	</div>
																</div>
															</div>

														</div>


														<%
															} else {
														%>

														<div class="col-md-12 col-xs-12"
															style="border: 1px solid gray; margin: 25px;">
															<div style="margin-top: 15px; margin-bottom: 15%;">
																<label> <%=medicalCertificateFinalText%>
																</label>
															</div>
														</div>

														<%
															}
														%>



													</div>

													<div class="ln_solid"></div>

													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>
															<%
																if (lastEneteredVisitList.equals("success")) {
															%>
															<button class="btn btn-success" type="submit"
																onclick="displayVal();">Save & Print</button>
															<%
																} else {
															%>
															<button class="btn btn-success" type="submit"
																onclick="displayVal();">Save & Print</button>
															<%
																}
															%>
														</div>
													</div>

												</form>
											</div>
											<!-- Ends -->

											<!-- Referral letter div -->
											<div class="tab-pane fade" id="refLetter">

												<form class="app-cam" id="" name=""
													action="AddReferralLetter" method="POST"
													style="width: 95%; margin: 0px;">

													<input type="hidden" name="visitID" id="visitID"
														value="<s:property value="visitID"/>"> <input
														type="hidden" name="opticinID" id="opticinID"
														value="<s:property value="opticinID"/>"> <input
														type="hidden" name="patientID"
														value="<s:property value="patientID"/>"> <input
														type="hidden" name="lastVisitID" id="lastVisitID"
														value="<s:property value="lastVisitID"/>"> <input
														type="hidden" name="firstName" id="firstName"
														value="<s:property value="firstName"/>"> <input
														type="hidden" name="lastName" id="lastName"
														value="<s:property value="lastName"/>"> <input
														type="hidden" id="doctrName" value="<%=fullName%>">
													<input type="hidden" id="patientName"
														value="<s:property value="firstName"/> <s:property value="lastName"/>">

													<input type="hidden" class="form-control"
														value="<%=currentDate%>" name="date" id="date12">

													<input type="hidden" id="doctrNameRef"
														value="<%=fullName%>"> <input type="hidden"
														id="patientNameRef"
														value="<s:property value="firstName"/> <s:property value="lastName"/>">
													<textarea style="display: none" name="referralLetter"
														id="referralLetter" cols="" rows="5"></textarea>
													<textarea style="display: none" name="referralLetterForPDF"
														id="referralLetterForPDF" cols="" rows="5"></textarea>

													<%
														if (referralLetterText == null || referralLetterText == "") {
													%>

													<div class="row">

														<div class="col-md-12 col-xs-12" style="margin: 15px;">

															<div class="col-md-1" style="margin-left: -15px;">
																<label>Date:</label>
															</div>
															<div class="col-md-3" style="margin-left: -15px;">
																<label><%=currentDate%></label>
															</div>


														</div>

													</div>

													<div class="row">

														<div class="col-md-12 col-xs-12" style="margin: 15px;">

															<label for="Name"
																style="font-size: 14px; margin-top: 10px;">Dear
																Dr. </label>
															<s:select list="doctorList" name="doctName" id="doctName"
																style="width:20%; " class="form-control"></s:select>
															,

														</div>

													</div>

													<div class="row">

														<div class="col-md-12 col-xs-12" style="margin: 15px;">

															<label for="Name" style="font-size: 14px;">I am
																referring <s:property value="firstName" /> <s:property
																	value="lastName" /> to you for the following
																conditions:
															</label><br>
															<br>

															<textarea rows="4" name="conditionText"
																id="conditionText" class="form-control"></textarea>
															<br>
															<br> <label for="Name" style="font-size: 14px;">Please
																advise Mr/Ms <s:property value="firstName" /> <s:property
																	value="lastName" /> on further treatment.
															</label> <br>
															<br> <label for="Name" style="font-size: 14px;">Sincerely,
															</label> <br> <label for="Name" style="font-size: 14px;">Dr.
																<%=fullName%>
															</label>

														</div>

													</div>

													<%
														} else {
													%>
													<div class="row">
														<div class="col-md-12 col-xs-12">
															<label style="margin: 15px; width: 100%;'"> <%=referralLetterFinalText%>
															</label>
														</div>
													</div>

													<%
														}
													%>

													<div class="ln_solid"></div>

													<div class="form-group">
														<div class="col-md-12 col-xs-12" align="center">
															<button type="button" onclick="windowOpen1();"
																class="btn btn-primary">Cancel</button>
															<%
																if (lastEneteredVisitList.equals("success")) {
															%>
															<button class="btn btn-success" type="submit"
																onclick="displayRefLet();">Save & Print</button>
															<%
																} else {
															%>
															<button class="btn btn-success" type="submit"
																onclick="displayRefLet();">Save & Print</button>
															<%
																}
															%>
														</div>
													</div>

												</form>

											</div>
											<!-- Ends -->

										</div>

									</s:iterator>

								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
			<!-- /page content -->

			<!-- footer content -->
			<footer>
				<div class="pull-right"></div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>

	<!-- jQuery -->
	<script src="vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>


	<!-- Datatables -->
	<script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
	<script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	<script
		src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
	<script
		src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>

	<script type="text/javascript"
		src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>

	<script type="text/javascript" src="build/css/bootstrap-multiselect.js"
		charset="UTF-8"></script>

	<script src="build/js/jquery-ui.js"></script>

	<!-- Custom Theme Scripts -->
	<script src="build/js/custom.min.js"></script>

	<!-- bootstrap-daterangepicker -->
	<script src="js/moment/moment.min.js"></script>
	<script src="js/datepicker/daterangepicker.js"></script>

	<!-- validator -->
	<script>
      // initialize the validator function
      validator.message.date = 'not a real date';

      // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
      $('form')
        .on('blur', 'input[required], input.optional, select.required', validator.checkField)
        .on('change', 'select.required', validator.checkField)
        .on('keypress', 'input[required][pattern]', validator.keypress);

      $('.multi.required').on('keyup blur', 'input', function() {
        validator.checkField.apply($(this).siblings().last()[0]);
      });

      $('form').submit(function(e) {
        e.preventDefault();
        var submit = true;

        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
          submit = false;
        }

        if (submit)
          this.submit();

        return false;
      });
    </script>
	<!-- /validator -->

	<script>
   
    document.addEventListener('DOMContentLoaded', function() {

    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
    	$('#chequeDateID').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
      });
   
    </script>

</body>
</html>