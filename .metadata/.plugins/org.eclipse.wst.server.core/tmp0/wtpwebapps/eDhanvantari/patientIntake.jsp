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
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<!-- <head> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Favicon -->
<link rel="shortcut icon" href="images/Icon.png">

<title>Patient Intake Form | E-Dhanvantari</title>

<!-- Bootstrap -->
<link href="vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="vendors/nprogress/nprogress.css" rel="stylesheet">

<!-- Datatables -->
<link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"
	rel="stylesheet">
<link
	href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"
	rel="stylesheet">
<link
	href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css"
	rel="stylesheet">
<link
	href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css"
	rel="stylesheet">
<link
	href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css"
	rel="stylesheet">

<link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">

<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />

<!-- Custom Theme Style -->
<link href="build/css/custom.min.css" rel="stylesheet">

<script type="text/javascript">
	function windowOpen() {
		$('html, body').animate({
			scrollTop : $('body').offset().top
		}, 1000);

		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity", "0.2");
		$("#loader").show();

		$("body").find("button").attr("disabled", "disabled"); //To disable My appo, view appo, search patient, add new pat buttons

		$('a#profPicID').remove(); //To disable profile pic at the right top corner
		$('a#edhanLinkID').remove(); //To disable edhan link given on top of left menu
		$('a#dashboardID').remove(); //To disable leftmenu options dashboard
		$('a#pmID').remove();
		$('a#pmID1').remove();
		$('a#pmID2').remove();
		$('a#pmID3').remove();
		$('a#pmID4').remove(); //To disable leftmenu options patient manag
		$('a#ivntID').remove();
		$('a#ivntID1').remove();
		$('a#ivntID2').remove();
		$('a#ivntID3').remove();
		$('a#ivntID4').remove();
		$('a#ivntID5').remove(); //To disable leftmenu options invt mang
		$('a#ivntID6').remove();
		$('a#ivntID7').remove();
		$('a#ivntID8').remove();
		$('a#ivntID9').remove();
		$('a#ivntID10').remove();
		$('a#ivntID11').remove(); //To disable leftmenu options invt mang
		$('a#ivntID12').remove();
		$('a#ivntID13').remove();
		$('a#ivntID14').remove();
		$('a#ivntID15').remove();
		$('a#ivntID16').remove();
		$('a#ivntID17').remove(); //To disable leftmenu options invt mang
		$('a#ivntID18').remove();
		$('a#ivntID19').remove(); //To disable leftmenu options invt mang
		$('a#admID').remove();
		$('a#admID1').remove();
		$('a#admID2').remove();
		$('a#admID3').remove();
		$('a#admID4').remove();
		$('a#admID5').remove(); //To disable leftmenu options adminstr
		$('a#admID6').remove();
		$('a#admID7').remove();
		$('a#admID8').remove();
		$('a#admID9').remove();
		$('a#admID10').remove(); //To disable leftmenu options adminstr
		$('a#repID').remove();
		$('a#repID1').remove();
		$('a#repID2').remove();
		$('a#repID3').remove();
		$('a#repID4').remove();
		$('a#repID5').remove(); //To disable leftmenu options Reports
		$('a#repID6').remove();
		$('a#repID7').remove();
		$('a#repID8').remove();
		$('a#repID9').remove();
		$('a#repID10').remove();
		$('a#repID11').remove(); //To disable leftmenu options Reports
		$('a#prID').remove();
		$('a#prID1').remove();
		$('a#prID2').remove(); //To disable leftmenu options Participant mng

		return true;
		document.location = "patientIntake.jsp";
	}
</script>

<style type="text/css">
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


<!-- remove up down arrow from input type number -->
<style type="text/css">
input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

#multipatdetails {
	overflow-y: scroll;
}

#multipatdetails1 {
	overflow-y: scroll;
}

.containerButton {
	text-align: center;
}
</style>

<!-- Setting width for gender radio values -->
<style type="text/css">
#genderMale {
	width: 5%;
}

#genderFemale {
	width: 5%;
}

#genderUnknown {
	width: 5%;
}
</style>

<!-- Ends -->


<script type="text/javascript">
	function getValue(ID, value, prevVal) {

		if (value == "") {
			$("#" + ID + "").val(prevVal);
		} else {
			$("#" + ID + "").val(value);
		}

	}
</script>


<!-- Calculating age by birth date -->

<script type="text/javascript">
	function calculateAge(userDate, userMonth, userYear) {

		var date = new Date(); // JS Engine will parse the string automagically

		var date21 = date.getDate();
		var month = date.getMonth() + 1;
		var year1 = date.getFullYear();

		var userDOB = userDate + "-" + userMonth + "-" + userYear;

		$('#dateOfBirthID').val(userDOB);

		var birthYear;

		if (userMonth > month || userMonth == month) {
			if (userMonth == month) {
				if (userDate > date21) {
					birthYear = year1 - userYear - 1;
				} else {
					birthYear = year1 - userYear;
				}
			} else {
				birthYear = year1 - userYear - 1;
			}
		} else {
			birthYear = year1 - userYear;
		}

		$('#ageID').val(birthYear);
		$('#ageID').attr('readonly', true);

	}
</script>


<!-- Ends -->


<%
String queryParam = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8.toString());
int practiceID = Integer.valueOf(queryParam.split("=")[1]);


%>

<!-- For login message -->
<%
Date date = new Date();
// Create SimpleDateFormat object 
SimpleDateFormat sdfo = new SimpleDateFormat("dd-MM-yyyy");
String currentDate = sdfo.format(date);

// Get the two dates to be compared 
/* Date d1 = sdfo.parse(endDate); */
Date d2 = sdfo.parse(currentDate);
// Print the dates 

%>
<!-- Ends -->

<!-- If user is clinician then after adding patient it directly goes to visit type selection model view -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	// Check if the mobile no is verified or not, if not then send OTP and verify the mobile not before proceeding ahead with patient and appointment registration
	function verifyMobileNo(mobileNo1, practiceID, clinicID) {
		var mobileNo = mobileNo1;
		
		if(mobileNo.length < 10){
			 alert("Mobile no is invalid. Please enter valid 10 digit mobile no.")
		 }else{
			 
			var xmlhttp;
			if (window.XMLHttpRequest) {
				xmlhttp = new XMLHttpRequest();
			} else {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var otpStatus = "";
					var otp = "";
					var verifyCheck = "";
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						verificationOTP = array.Release[i].otp;
						otpStatus = array.Release[i].otpStatus;
						verifyCheck = array.Release[i].verifyCheck;
	
					}
					
					if(verifyCheck == "verified"){
						checkPatientForAppointment(mobileNo1, practiceID, clinicID)
					}else if(otpStatus == "failed"){
						alert("Failed to send OTP for mobile verification due to technical issue")
					}else{
						$("#verifyMobleNo").val(mobileNo1)
						$("#verifyClinicID").val(clinicID)
						$("#verifyPracticeID").val(practiceID)
						
						$("#otpVerificationModal").modal('show');
					}
	
				}
			};
			
			xmlhttp.open("POST", "custom/SendOTPForVerification?mobile="
						+ mobileNo + "&practiceID=" + practiceID + "&clinicID="
						+ clinicID, true);
			//xmlhttp.open("POST", "custom/SendOTPForVerification?mobile="+mobileNo, true);
			xmlhttp.send();
		 }
	}

	function checkPatientForAppointment(mobileNo1, practiceID, clinicID) {
		
		var x = document.getElementById("searchDIV");
		x.style.display = "none";

		var mobNo1 = mobileNo1;
		var practiceID = practiceID;
		var clinicID = clinicID;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				console.log(xmlhttp.responseText);
				var array = JSON.parse(xmlhttp.responseText);

				var check = 0;
				var fName = "";
				var mName = "";
				var lName = "";
				var mobNo = "";
				var regNo = "";
				var patDOB = "";
				var patID = "";
				var gen = "";
				var email = "";
				var bg = "";
				var age = 0;
				var rhf = "";

				var patientListTag = "<table border='1' style='width: 100%;'><thead><tr>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Name</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Mobile No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Reg. No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Select</td>";
				patientListTag += "</thead></tr><tbody>";

				if (array.Release.length > 1) {
					for (var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						fName = array.Release[i].fName;
						mName = array.Release[i].mName;
						lName = array.Release[i].lName;
						mobNo = array.Release[i].mobileNo;
						regNo = array.Release[i].regNo;
						patID = array.Release[i].patientID;
						patDOB = array.Release[i].dateOfBirth;
						gen = array.Release[i].gender;
						email = array.Release[i].email;
						bg = array.Release[i].bloodGroup;
						age = array.Release[i].age;
						rhf = array.Release[i].rhFactor;

						//Creating table rows
						patientListTag += "<tr>";
						patientListTag += "<td style='padding: 5px;font-size: 14px;'>"
								+ fName + " " + mName + " " + lName + "</td>";
						patientListTag += "<td style='padding: 5px;font-size: 14px;'>"
								+ mobNo + "</td>";
						patientListTag += "<td style='padding: 5px;font-size: 14px;'>"
								+ regNo + "</td>";
						patientListTag += "<td style='padding: 5px;font-size: 14px;text-align: center;'><input type='radio' name='patientSelect1234' onclick='redirectAppointment(\""
								+ patID
								+ "\",\""
								+ practiceID
								+ "\",\""
								+ clinicID + "\");'></td>";
						patientListTag += "</tr>";

					}

					console.log("Check :: " + check);

					patientListTag += "</tbody></table>";

					$('#patientDetailsListID123').html(patientListTag);

					$('#multipatdetails1').modal('show');
				} else {

					check = array.Release[0].check;
					fName = array.Release[0].fName;
					mName = array.Release[0].mName;
					lName = array.Release[0].lName;
					mobNo = array.Release[0].mobileNo;
					regNo = array.Release[0].regNo;
					patID = array.Release[0].patientID;
					patDOB = array.Release[0].dateOfBirth;
					gen = array.Release[0].gender;
					email = array.Release[0].email;
					bg = array.Release[0].bloodGroup;
					age = array.Release[0].age;
					rhf = array.Release[0].rhFactor;
					diabetes = array.Release[0].isDiabetes;
					hypertension = array.Release[0].hypertension;
					asthema = array.Release[0].asthema;
					heart = array.Release[0].ischemicHeartDisease
					od = array.Release[0].otherDetails;
					if (typeof fName === "undefined") {
						var x = document.getElementById("apptDIV");
						x.style.display = "none";
						var y = document.getElementById("patientDIV");
						y.style.display = "none";
						var z = document.getElementById("searchDIV");
						z.style.display = "block";
						document.getElementById("newPatient").style.display = "block";
						document.getElementById("addPatientAppt").style.display = "none";
					} else {

						$('#patientDetailsListID123').html(patientListTag);

						$("#patientDetailModal1").modal('hide');

						$('#firstNameID').val(fName);
						$('#middleNameID').val(mName);
						$('#lastNameID').val(lName);
						$('#patMobileNoID1').val(mobNo);
						$('#single_cal3').val(patDOB);
						$('#regNo').val(regNo);
						$('#patMobileNoID1').val(mobNo);
						$('#ageID').val(age);
						document.getElementById(gen).checked = true;
						$('#emailID').val(email);
						$('#bloodGroupID').val(bg);
						$('#rhFactorID').val(rhf);

						if (diabetes === "Yes") {
							//$('#diabYes').checked = true;
							document.getElementById('diabYes').checked = true;
						} else if (diabetes === "No") {
							//$('#diabNo').checked = true;
							document.getElementById('diabNo').checked = true;
						}
						if (hypertension === "Yes") {
							document.getElementById('hypYes').checked = true;
						} else if (hypertension === "No") {
							document.getElementById('hypNo').checked = true;
						}
						if (asthema === "Yes") {
							document.getElementById('asthYes').checked = true;
						} else if (asthema === "No") {
							document.getElementById('asthNo').checked = true;
						}
						if (heart === "Yes") {
							document.getElementById('heartYes').checked = true;
						} else if (heart === "No") {
							document.getElementById('heartNo').checked = true;
						}

						$('#otherDetails').val(od);
						document.getElementById("newPatient").style.display = "none";
						document.getElementById("addPatientAppt").style.display = "block";

					}
				}

			}
		};
		xmlhttp.open("POST", "custom/VerifyPatientIntakeDetails?mobile="
				+ mobileNo1 + "&practiceID=" + practiceID + "&clinicID="
				+ clinicID, true);
		xmlhttp.send();

	}
</script>
<script type="text/javascript" charset="UTF-8"> 
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function redirectAppointment(patID, practiceID, clinicID) {

		/* var x = document.getElementById("myDIV");
		x.style.display = "none"; */
		var patID = patID;
		var practiceID = practiceID;
		var clinicID = clinicID;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				console.log(xmlhttp.responseText);
				var array = JSON.parse(xmlhttp.responseText);

				var check = array.Release[0].check;
				var fName = array.Release[0].fName;
				var mName = array.Release[0].mName;
				var lName = array.Release[0].lName;
				var mobNo = array.Release[0].mobileNo;
				var regNo = array.Release[0].regNo;
				var patID = array.Release[0].patientID;
				var patDOB = array.Release[0].dateOfBirth;
				var gen = array.Release[0].gender;
				var email = array.Release[0].email;
				var bg = array.Release[0].bloodGroup;
				var age = array.Release[0].age;
				var rhf = array.Release[0].rhFactor;
				var diabetes = array.Release[0].isDiabetes;
				var hypertension = array.Release[0].hypertension;
				var asthema = array.Release[0].asthema;
				var heart = array.Release[0].ischemicHeartDisease
				var od = array.Release[0].otherDetails;

				$('#firstNameID').val(fName);
				$('#middleNameID').val(mName);
				$('#lastNameID').val(lName);
				$('#patMobileNoID1').val(mobNo);
				$('#single_cal3').val(patDOB);
				$('#regNo').val(regNo);
				$('#patMobileNoID1').val(mobNo);
				$('#ageID').val(age);
				document.getElementById(gen).checked = true;
				$('#emailID').val(email);
				$('#bloodGroupID').val(bg);
				$('#rhFactorID').val(rhf);
				if (diabetes === "Yes") {
					document.getElementById('diabYes').checked = true;
				} else if (diabetes === "No") {
					document.getElementById('diabNo').checked = true;
				}
				if (hypertension === "Yes") {
					document.getElementById('hypYes').checked = true;
				} else if (hypertension === "No") {
					document.getElementById('hypNo').checked = true;
				}
				if (asthema === "Yes") {
					document.getElementById('asthYes').checked = true;
				} else if (asthema === "No") {
					document.getElementById('asthNo').checked = true;
				}
				if (heart === "Yes") {
					document.getElementById('heartYes').checked = true;
				} else if (heart === "No") {
					document.getElementById('heartNo').checked = true;
				}

				$('#otherDetails').val(od);
				document.getElementById("newPatient").style.display = "none";
				document.getElementById("addPatientAppt").style.display = "block";
				$('#multipatdetails1').modal('hide');

			}
		};
		xmlhttp
				.open("POST", "custom/RedirectPatientIntakeDetails?patientID="
						+ patID + "&practiceID=" + practiceID + "&clinicID="
						+ clinicID, true);
		xmlhttp.send();
	}
</script>

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getApptType(clinicID) {

		var clinicID = clinicID;
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				console.log(xmlhttp.responseText);
				
				var array = JSON.parse(xmlhttp.responseText);
				
				var s = '<option value="-1">Appointment Type</option>';  
               	for (var i = 0; i < array.Release.length; i++) {  
                	s += '<option value="' + array.Release[i].id + '">' + array.Release[i].name + '</option>';  
               	}  
               	$("#apptType").html(s); 

			}
		};
		xmlhttp
				.open("POST", "custom/GetPIAppointmentType?clinicID="
						+ clinicID, true);
		xmlhttp.send();
	}
</script>

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getAppointmentSlots(clinicID, apptDate) {

		var clinicID = clinicID;
		var apptDate = apptDate;
		console.log(clinicID);
		console.log(apptDate);
		

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				console.log(xmlhttp.responseText);
				
				var array = JSON.parse(xmlhttp.responseText);
				
				var s = '<option value="-1">Available Appointment Times</option>';  
               	for (var i = 0; i < array.Release.length; i++) {  
               		if(array.Release[i].slot == "9" || array.Release[i].slot == "8"){
               			s += '<option value="0' + array.Release[i].slot + ':00:00">0' + array.Release[i].slot + ':00</option>';  
               		}
               		else{
               			s += '<option value="' + array.Release[i].slot + ':00:00">' + array.Release[i].slot + ':00</option>';  
               		}
                	
               	}  
               	$("#apptSlots").html(s); 

			}
		};
		xmlhttp
				.open("POST", "custom/GetPIAppointmentSlots?clinicID="
						+ clinicID + "&apptDate=" + apptDate, true);
		xmlhttp.send();
	}
</script>

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function newAppointment(clinicID, mobile, fname, mname, lname, gender, dob, age, email, refBy, bg, rhf, diab, hyp, asthma, heart, od, regNo, appDate, apptSlot, apptType, apptStatus, walkIn, practiceID) {
		
		if(diab === undefined){
			diab = ""
		}
		if(heart === undefined){
			heart = ""
		}
		if(asthma === undefined){
			asthma = ""
		}
		if(hyp === undefined){
			hyp = ""
		}
		if(walkIn === undefined){
			walkIn = 0
		}
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var statusMsg = "";

				for (var i = 0; i < array.Release.length; i++) {

					statusMsg = array.Release[i].Msg;

				}
				console.log("status msg :: " + statusMsg);

				if (statusMsg == "Success") {
					
					alert("Patient and appointment added successfully.");
					window.location.reload();
					//window.location.href = 'http://localhost:8080/eDhanvantari/Trial?id=11';		
					/* var y = document.getElementByID("searchDIV");
					y.style.display = "none";
					var x = document.getElementByID("apptDIV");
					x.style.display = "none";
					var z = document.getElementByID("patientDIV");
					z.style.display = "block"; */


				} else {


				}

			}
		};
		xmlhttp.open("GET", "custom/AddNewPatientIntakeAppointment?clinicID=" + clinicID
				+ "&mobile=" + mobile + "&firstName=" + fname
				+ "&middleName=" + mname + "&lastName="
				+ lname + "&gender=" + gender + "&dob=" + dob
				+ "&age=" + age + "&email=" + email + "&bloodGroup="
				+ bg + "&rhFactor=" + rhf + "&isDiabetes=" + diab
				+ "&hypertension=" + hyp + "&asthema=" + asthma
				+ "&ischemicHeartDisease=" + heart + "&otherDetails=" + od 
				+ "&registrationNo=" + regNo + "&apptDate=" + appDate + "&apptType=" + apptType + "&apptTimeFrom=" + apptSlot
				+ "&apptStatus=" + apptStatus + "&walkIn=" + walkIn + "&practiceID=" + practiceID,
				true);
		xmlhttp.send();
	}
</script>

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function addAppointment(clinicID, mobile, fname, mname, lname, gender, dob, age, email, refBy, bg, rhf, diab, hyp, asthma, heart, od, regNo, appDate, apptSlot, apptType, apptStatus, walkIn, practiceID) {
		
		if(diab === undefined){
			diab = ""
		}
		if(heart === undefined){
			heart = ""
		}
		if(asthma === undefined){
			asthma = ""
		}
		if(hyp === undefined){
			hyp = ""
		}
		if(walkIn === undefined){
			walkIn = 0
		}
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var statusMsg = "";

				for (var i = 0; i < array.Release.length; i++) {

					statusMsg = array.Release[i].Msg;

				}
				console.log("status msg :: " + statusMsg);

				if (statusMsg == "Success") {
					
					alert("Appointment added successfully.");
					window.location.reload();

					//window.location.href = 'http://localhost:8080/eDhanvantari/Trial?id=11';		
					/* var y = document.getElementByID("searchDIV");
					y.style.display = "none";
					var x = document.getElementByID("patientDIV");
					x.style.display = "none";
					var z = document.getElementByID("apptDIV");
					z.style.display = "block"; */


				} else {


				}

			}
		};
		xmlhttp.open("GET", "custom/AddPatientIntakeAppointment?clinicID=" + clinicID
				+ "&mobile=" + mobile + "&firstName=" + fname
				+ "&middleName=" + mname + "&lastName="
				+ lname + "&gender=" + gender + "&dob=" + dob
				+ "&age=" + age + "&email=" + email + "&bloodGroup="
				+ bg + "&rhFactor=" + rhf + "&isDiabetes=" + diab
				+ "&hypertension=" + hyp + "&asthema=" + asthma
				+ "&ischemicHeartDisease=" + heart + "&otherDetails=" + od 
				+ "&registrationNo=" + regNo + "&apptDate=" + appDate + "&apptType=" + apptType  + "&apptTimeFrom=" + apptSlot
				+ "&apptStatus=" + apptStatus + "&walkIn=" + walkIn + "&practiceID=" + practiceID,
				true);
		xmlhttp.send();
	}
</script>

<!-- Shortcut menu Full screen function -->

<script type="text/javascript">
	window.onload = maxWindow;

	function widnwoFullScreen() {

		var docElm = document.documentElement;
		if (docElm.requestFullscreen) {
			docElm.requestFullscreen();
		} else if (docElm.mozRequestFullScreen) {
			docElm.mozRequestFullScreen();
		} else if (docElm.webkitRequestFullScreen) {
			docElm.webkitRequestFullScreen();
		}

	}
</script>

<!-- Ends -->



<%
/* String apptCheck = (String) request.getAttribute("apptCheck");
System.out.println("apptcheck ::: " + apptCheck);
if (apptCheck == null || apptCheck == "") {
	apptCheck = "appointment";
} */

PatientDAOInf patientDAOInf = new PatientDAOImpl();
LoginDAOInf loginDAOInf = new LoginDAOImpl();

HashMap<Integer, String> clinicMap = loginDAOInf.retrieveClinicList(practiceID);

%>


<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
		var dateNow = new Date();
		$('.clinic_start').datetimepicker({
			language : 'en',
			weekStart : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 1,
			minView : 0,
			maxView : 1,
			forceParse : 1,
			defaultDate : dateNow
		});

		setTimeout(function() {
			$('#apptStartTimeID1').attr("value",
					$("#apptStartTimeID1Hidden").val());
		}, 1000);

	});
</script>

<!-- make an input field accept only letters-->
<script type="text/javascript">
	function validate(value, InputID) {

		var patt1 = new RegExp("[a-zA-Z]+(\s[a-zA-Z]+)?$", "g");

		if (value == "") {
			$("#" + InputID).val("");
			return false;
		} else if (!/^[a-zA-Z\s]*$/g.test(value)) {
			alert("Name should not contains any special characters and numbers.");
			$("#" + InputID).val("");
			return false;
		} else if (patt1.test(value)) {
			return true;
		} else if (/\s+\S*$/.test(value)) {
			$("#" + InputID).val($("#" + InputID).val().trim());
			return true;
		} else {
			alert("Name should not contains any special characters and numbers.");
			$("#" + InputID).val("");
			return false;
		}

	}
</script>

</head>

<body class="nav-md">

<!-- OTP modal -->
	
	<div id="otpVerificationModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">VERIFY MOBILE NO.</h4>
				</div>

				<div class="modal-body">

					<center style="margin-bottom: 20px;">
						<font style="color: black; font-size: 16px;">Enter OTP sent to your mobile no</font>
					</center>
					
					<input type="hidden" id="verifyMobleNo">
					<input type="hidden" id="verifyClinicID">
					<input type="hidden" id="verifyPracticeID">

					<div class="row">
						<div class="col-md-10 col-sm-10 col-xs-8">
							<input type="number" name="" id="otpID"
								class="form-control"
								placeholder="Enter OTP here">
								<small id="otpMsgID" style="font-weight: bold;color: green;display: none;" >OTP validated successfully!</small>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-4" id="lockImgID1"
							style="margin-left: -15px;"></div>
					</div>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button"  class="btn btn-primary"
							data-dismiss="modal">Close</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->
	
	<img src="images/Preloader_2.gif" alt="Loading Icon"
		class="loadingImage" id="loader">

	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<!-- /menu profile quick info -->

					<br />

					<!-- Including leftMenu jsp -->
					<%-- <jsp:include page="leftMenu.jsp"></jsp:include> --%>
					<!-- Ends -->

					<!-- /menu footer buttons -->
					<%-- <div class="sidebar-footer hidden-small">
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
					</div> --%>
					<!-- /menu footer buttons -->

				</div>
			</div>



			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>

					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="page-title">
									<div class="title_left">
										<h3>
											<b>PATIENT INTAKE FORM</b>
										</h3>
									</div>
								</div>

								<div class="ln_solid" style="margin-top: 0px;"></div>
								<div class="x_content">

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
									</div>

									<div>
										<form class="form-horizontal form-label-left"
											action="AddPatientAppointment" name="apptFormID"
											onsubmit="submitApptForm();" id="apptFormID" method="POST"
											style="margin-top: 20px;">

											<input type="hidden" name="practiceID" id="practiceID"
												value=<%=practiceID%>>
											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="clinicID">Clinic Name<span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<select id="clinicID" name="clinicID" class="form-control"
														onchange="getApptType(this.value);">
														<%
														if (clinicMap.size() == 1) {
															HashMap.Entry<Integer, String> entry = clinicMap.entrySet().iterator().next();
														%>
														<option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>

														<%
														} else {
														for (Integer key : clinicMap.keySet()) {
														%>

														<option value="<%=key%>"><%=clinicMap.get(key)%></option>

														<%
														}
														}
														%>

													</select>
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type"
													class="control-label col-md-3 col-sm-4">Mobile <span
													class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="number" name="mobile" required="required"
														placeholder="Mobile" id="mobile" class="form-control"
														onKeyPress="if(this.value.length==10) return false;"
														aria-describedby="inputGroupSuccess1Status">
												</div>
											</div>


											<div class="containerButton">
												<div>
													<button type="button" class="btn btn-primary"
														onclick="verifyMobileNo(mobile.value, practiceID.value, clinicID.value);">Check
														Patient</button>
												</div>
											</div>

											<div id="searchDIV" style="display: none; text-align: center">No
												patient found for the entered mobile number.</div>

											<div id="apptDIV"
												style="display: none; text-align: center">Appointment added successfully.</div>
											
											<div id="patientDIV"
												style="display: none; text-align: center">Patient and
												appointment added successfully.</div>

											<div class="ln_solid" style="margin-top: 0px;"></div>



											<div id="multipatdetails1" class="modal fade" role="dialog"
												aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog modal-md">
													<div class="modal-content">

														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true">&times;</button>
															<h4 class="modal-title" id="myModalLabel"
																style="text-align: center;">PATIENT DETAIL</h4>
														</div>
														<div class="modal-body">

															<div class="row">

																<div class="col-md-12 col-sm-12 col-xs-12"
																	id="patientDetailsListID123" style="padding: 15px;">

																</div>

															</div>

														</div>
														<div class="modal-footer" align="center">
															<button type="button" class="btn btn-default"
																data-dismiss="modal">Close</button>
															<button type="button" class="btn btn-success"
																onclick="submitAddNewPatientForm();">Add New
																Patient</button>
														</div>

													</div>
												</div>
											</div>


											<%-- <s:iterator value="patientList" var="form"> --%>

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="name" style="padding-top: 3%">Name <span
													class="required">*</span>
												</label>
												<div class="col-md-2 col-sm-2 col-xs-12"
													style="padding-top: 2%">
													<input class="form-control" name="firstName"
														style="text-transform: capitalize;"
														value="<s:property value='firstName'/>" id="firstNameID"
														placeholder="First Name" required="required" type="text"
														onblur="validate(this.value, 'firstNameID');">
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12"
													style="padding-top: 2%">
													<input class="form-control" name="middleName"
														style="text-transform: capitalize;"
														value="<s:property value='middleName'/>" id="middleNameID"
														placeholder="Middle Name" type="text"
														onblur="validate(this.value, 'middleNameID');">
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12"
													style="padding-top: 2%">
													<input class="form-control" name="lastName"
														style="text-transform: capitalize;"
														value="<s:property value='lastName'/>" id="lastNameID"
														placeholder="Last Name" required="required" type="text"
														onblur="validate(this.value, 'lastNameID');">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="Gender">Gender<span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													
													<input type="radio" name="genderID" id="Male" value="Male" />
													Male <input type="radio" name="genderID" id="Female"
														value="Female" />Female <input type="radio"
														name="genderID" id="Child" value="Child" /> Child <input
														type="radio" name="genderID" id="Unknown" value="Unknown" />
													Unknown
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type"
													class="control-label col-md-3 col-sm-4">Date Of
													Birth</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="DateOfBirth"
														placeholder="Date Of Birth" id="single_cal3"
														class="form-control" readonly="readonly">
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type"
													class="control-label col-md-3 col-sm-4">Age <span
													class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="number" name="age" placeholder="Age"
														required="required" id="ageID" class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="Email">Email </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="email" name="emailID" id="emailID"
														placeholder="Email Address" class="form-control">
												</div>
											</div>

											<div class="item form-group search-container">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="ReferredBy">Referred By </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="refBy" id="refByID"
														placeholder="Referred By" class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type"
													class="control-label col-md-3 col-xs-12 col-sm-4"
													style="padding-top: 2%">Blood Group</label>
												<div class="col-md-4 col-sm-4 col-xs-12"
													style="padding-top: 2%">
													<s:select list="#{'A':'A','B':'B','O':'O','AB':'AB'}"
														id="bloodGroupID" name="bloodGroup" class="form-control"
														headerKey="" headerValue="Blood Group"></s:select>
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12"
													style="padding-top: 2%">
													<s:select list="#{'+ve':'+ve','-ve':'-ve'}" name="rhFactor"
														id="rhFactorID" class="form-control" headerKey=""
														headerValue="Rh"></s:select>
												</div>
											</div>

											<!-- Medical History Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12"
													style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" 
														aria-expanded="false"
														aria-controls="collapseExample"> MEDICAL HISTORY: </a>
												</div>

												<div class="" id="medicalHistory"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="isDiabetes">Do you suffer from Diabetes? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<input type="radio" name="diabetes" id="diabYes"
																value="Yes" /> Yes <input type="radio" name="diabetes"
																id="diabNo" value="No" /> No
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="asthema">Do you suffer from Asthma? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<input type="radio" name="asthma" id="asthYes"
																value="Yes" /> Yes <input type="radio" name="asthma"
																id="asthNo" value="No" /> No
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="hypertension">Do you suffer from High Blood
															Pressure? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<input type="radio" name="hypertension" id="hypYes"
																value="Yes" /> Yes <input type="radio"
																name="hypertension" id="hypNo" value="No" /> No
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Gender">Do you suffer from Heart Disease? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<input type="radio" name="heart" id="heartYes"
																value="Yes" /> Yes <input type="radio" name="heart"
																id="heartNo" value="No" /> No
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="otherDetails">Any other medical condition or
															disease? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:textarea name="otherDetails" id="otherDetails"
																class="form-control"
																placeholder="Other medical condition or disease"></s:textarea>
														</div>
													</div>

												</div>
											</div>
											<!-- Ends -->

											<div class="item form-group">
												<label for="User Type"
													class="control-label col-md-3 col-sm-4">Medical
													Registration No. </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="" readonly="readonly" id="regNo"
														placeholder="Registration No."
														class="form-control"
														placeholder="Medical Registration No."> <input
														type="hidden" name="registrationNo" readonly="readonly"
														placeholder="Medical Registration No."
														class="form-control">
												</div>
											</div>


											<!-- Appointment Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12"
													style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#appointmentInfo" aria-expanded="false"
														aria-controls="collapseExample"> APPOINTMENT DETAILS:
													</a>
												</div>

												<div class="collapse in" id="appointmentInfo"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Appointment Date">Appointment Date<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" class="form-control"
																value="<%=currentDate%>" name="appointmentDate"
																id="apptDate" placeholder="Appointment Date"
																aria-describedby="inputSuccess2Status3" onchange="getAppointmentSlots(clinicID.value, apptDate.value);">
														</div>
													</div>
													
													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="apptSlots">Appointment Time
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<select class="form-control" id="apptSlots"
																name="apptSlots"></select>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="apptType">Appointment Type<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															
															<select class="form-control" id="apptType"
																name="apptType"></select>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Status">Status </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="aptStatus" readonly="readonly"
																value="Booked" placeholder="Status" class="form-control">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Status">Walk In? </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="checkbox" name="walkIn" id="walkIn"
																style="zoom: 1.8;" value="1">
														</div>
													</div>

												</div>
											</div>
											<!-- Ends -->

											<div class="ln_solid"></div>
											<div class="form-group" id="newPatient">
												<div class="col-md-12 col-xs-12" align="center">
													<button type="button" onclick="windowOpen();"
														class="btn btn-primary">Cancel</button>
													<button type="button" class="btn btn-success "
														id="apptSubmit"
														onclick="newAppointment(clinicID.value, mobile.value, firstNameID.value, middleNameID.value, lastNameID.value, document.querySelector('input[name = genderID]:checked').value, single_cal3.value, ageID.value, emailID.value, refByID.value, bloodGroupID.value, rhFactorID.value, document.querySelector('input[name = diabetes]:checked')?.value, document.querySelector('input[name = hypertension]:checked')?.value, document.querySelector('input[name = asthma]:checked')?.value, document.querySelector('input[name = heart]:checked')?.value, otherDetails.value, regNo.value, apptDate.value, apptSlots.value, apptType.value, aptStatus.value, document.querySelector('input[name = walkIn]:checked')?.value, practiceID.value)">New
														Appointment</button>
												</div>
											</div>

											<div class="form-group" id="addPatientAppt"
												style="display: none">
												<div class="col-md-12 col-xs-12" align="center">
													<button type="button" onclick="windowOpen();"
														class="btn btn-primary">Cancel</button>
													<button type="button" class="btn btn-success "
														id="apptSubmit"
														onclick="addAppointment(clinicID.value, mobile.value, firstNameID.value, middleNameID.value, lastNameID.value, document.querySelector('input[name = genderID]:checked').value, single_cal3.value, ageID.value, emailID.value, refByID.value, bloodGroupID.value, rhFactorID.value, document.querySelector('input[name = diabetes]:checked')?.value, document.querySelector('input[name = hypertension]:checked')?.value, document.querySelector('input[name = asthma]:checked')?.value, document.querySelector('input[name = heart]:checked')?.value, otherDetails.value, regNo.value, apptDate.value, apptSlots.value, apptType.value, aptStatus.value, document.querySelector('input[name = walkIn]:checked')?.value, practiceID.value)">Add
														Appointment</button>
												</div>
											</div>
										</form>
									</div>

								</div>
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



	<!-- jQuery -->
	<script src="vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="vendors/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script src="vendors/nprogress/nprogress.js"></script>
	<!-- validator -->
	<script src="vendors/validator/validator.js"></script>

	<!-- Custom Theme Scripts -->
	<script src="build/js/custom.min.js"></script>

	<!-- Datatables -->
	<script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
	<script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	<script
		src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
	<script
		src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


	<script type="text/javascript"
		src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>

	<!-- bootstrap-daterangepicker -->
	<script src="js/moment/moment.min.js"></script>
	<script src="js/datepicker/daterangepicker.js"></script>

	<!-- Datepicker -->
	<script>
		document.addEventListener('DOMContentLoaded', function() {

			$("input[type=number]").on("keydown", function(e) {
				if (e.which === 38 || e.which === 40) {
					e.preventDefault();
				}
			});

			$('#single_cal3').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_2",
				showDropdowns : true,
				minYear : 1901,
				maxYear : parseInt(moment().format('YYYY'), 10)
			}, function(start, end, label) {
				var years = moment().diff(start, 'years');
				$("#ageID").val(years);
			});

			$('#single_cal3').on(
					'hide.daterangepicker',
					function(ev, picker) {
						var selectedDate = $(this).val();

						var dateArr = selectedDate.split("-");

						var d1 = new Date();
						var dd = d1.getDate();
						var mm = d1.getMonth() + 1;
						if (dd < 10) {
							dd = '0' + dd;
						}

						if (mm < 10) {
							mm = '0' + mm;
						}
						var yyyy = d1.getFullYear();

						var today = yyyy + "-" + mm + "-" + dd;

						var calendarDate = new Date(dateArr[2] + "-"
								+ dateArr[1] + "-" + dateArr[0]);

						console.log("Dates: " + calendarDate + "--" + today);
						//alert("Dates: "+new Date(today).getTime()+"--"+calendarDate.getTime());

					});

			$('#apptDate').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_2",
				showDropdowns : true,
				minYear : 1901,
				maxYear : parseInt(moment().format('YYYY'), 10)
			}, function(start, end, label) {
				//var years = moment().diff(start, 'years');
				//$("#ageID").val(years);
			});

			$('#apptDate').on(
					'hide.daterangepicker',
					function(ev, picker) {
						var selectedDate = $(this).val();

						var dateArr = selectedDate.split("-");

						var d1 = new Date();
						var dd = d1.getDate();
						var mm = d1.getMonth() + 1;
						if (dd < 10) {
							dd = '0' + dd;
						}

						if (mm < 10) {
							mm = '0' + mm;
						}
						var yyyy = d1.getFullYear();

						var today = yyyy + "-" + mm + "-" + dd;

						var calendarDate = new Date(dateArr[2] + "-"
								+ dateArr[1] + "-" + dateArr[0]);

						console.log("Dates: " + calendarDate + "--" + today);
						//alert("Dates: "+new Date(today).getTime()+"--"+calendarDate.getTime());

					});

		});
	</script>
	<!-- /Datepicker -->


	<!-- validator -->
	<script>
		// initialize the validator function
		validator.message.date = 'not a real date';

		// validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
		$('form').on('blur',
				'input[required], input.optional, select.required',
				validator.checkField).on('change', 'select.required',
				validator.checkField).on('keypress',
				'input[required][pattern]', validator.keypress);

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
		
		var verificationOTP = "";
		
		$(document).ready(function() {
			
			$("#sendOTP").on("click",function(){
	    		 var mobileNo = $("#mobileID").val()
	    		 
	    		 if(mobileNo.length < 10){
	    			 alert("Mobile no is invalid. Please end valid 10 digit mobile no.")
	    		 }else{
	    			 
	    			var xmlhttp;
	 				if (window.XMLHttpRequest) {
	 					xmlhttp = new XMLHttpRequest();
	 				} else {
	 					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	 				}	
					xmlhttp.onreadystatechange = function() {
						if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			
							var array = JSON.parse(xmlhttp.responseText);
							
							var otpStatus = "";
							var otp = "";
							var verifyCheck = "";
			
							for ( var i = 0; i < array.Release.length; i++) {
								
								verificationOTP = array.Release[i].otp;
								otpStatus = array.Release[i].otpStatus;
								verifyCheck = array.Release[i].verifyCheck;
			
							}
							
							if(verifyCheck == "verified"){
								alert("Mobile no is already verified")
							}else if(otpStatus == "failed"){
								alert("Failed to send SMS due to technical issue")
							}else{
								$("#otpVerificationModal").modal('show');
							}
			
						}
					};
					
					xmlhttp.open("POST", "SendOTP?mobile="+mobileNo, true);
					xmlhttp.send();
	    		 }
	    	  });
	    	  
	    	  $("#otpID").on("keyup",function(){
	    		  var userOTP = $(this).val();
	    		  
	    		  console.log("..."+verificationOTP)
	    		  
	    		  if(userOTP == verificationOTP){
	    			  
	    			  var mobileNo1 = $("#verifyMobleNo").val()
	    			  var clinicID = $("#verifyClinicID").val()
	    			  var practiceID = $("#verifyPracticeID").val()
	    			  
	    			  document.getElementById("lockImgID1").innerHTML = "<img src='images/tick_check.png' alt='Correct OTP' title='Corrent OTP' style='height:24px;margin-top: 5px;'>";
	    			  
	    			  $("#otpMsgID").show(500)
	    			  
	    			  var xmlhttp;
		   				if (window.XMLHttpRequest) {
		   					xmlhttp = new XMLHttpRequest();
		   				} else {
		   					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		   				}	
		  				xmlhttp.onreadystatechange = function() {
		  					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		  		
		  						var array = JSON.parse(xmlhttp.responseText);
		  						
		  						var validateOTP = "";
		  		
		  						for ( var i = 0; i < array.Release.length; i++) {
		  							
		  							validateOTP = array.Release[i].validateOTP;
		  		
		  						}
		  						
		  						setTimeout(function(){
		  							document.getElementById("lockImgID1").innerHTML = "";
		  							$("#otpMsgID").hide()
		  							$("#otpID").val("")
		  	    				  $("#otpVerificationModal").modal("hide")
		  	    				  
		  	    				  checkPatientForAppointment(mobileNo1, practiceID, clinicID)
		  	    				  
		  	    			  }, 3000);
		  		
		  					}
		  				};
		  				
		  				xmlhttp.open("POST", "custom/ValidateOTPForPatientIntake?mobile="
		  						+ mobileNo1 + "&practiceID=" + practiceID + "&clinicID="
		  						+ clinicID, true);
		  				
		  				//xmlhttp.open("POST", "custom/ValidateOTPForPatientIntake?mobile="+mobileNo1, true);
		  				xmlhttp.send();
	    			  
	    		  }else{
	    			  $("#otpMsgID").hide()
	    			  document.getElementById("lockImgID1").innerHTML = "<img src='images/wrong.png' alt='Incorrect OTP' title='Incorrect OTP' style='height:24px;margin-top: 5px;'>";
	    		  }
	    	  });
	    	  
			getApptType($("#clinicID").val());
			//$('#cancerType').select2();
			$("#refDoctor").select2({
				tags : true
			});
		});
		
		
	</script>
	<!-- /validator -->

</body>
</html>