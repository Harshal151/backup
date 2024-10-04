<%@page import="com.edhanvantari.daoImpl.*"%>
<%@page import="com.edhanvantari.daoInf.*"%>
<%@page import="com.edhanvantari.service.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.edhanvantari.util.*"%>
<%@page import="com.edhanvantari.form.*"%>
<%@page import="com.amazonaws.auth.*"%>
<%@page import="com.amazonaws.services.s3.*"%>
<%@page import="com.amazonaws.services.s3.model.*" %>
<%@page import="com.amazonaws.util.*"%>
<%@page import="java.io.*"%>
<%@ taglib uri="/struts-tags" prefix="s" %>   
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Datatables -->
    <link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>Patient Dashboard | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <!-- For full calendar -->
    <link href="build/css/fullcalendar.min.css" rel="stylesheet" />
	<link href="build/css/fullcalendar.print.min.css" rel="stylesheet" media="print" />
	
	<link href="jquery.ui.all.css" rel="stylesheet" type="text/css" />
 	<script src="jquery-1.5.1.js" type="text/javascript"></script>
 	<script src="jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>
    
    
    <script type="text/javascript">
      function windowOpen(){
        document.location="patientWelcome.jsp";
      }
    </script>
    
    <style type="text/css">

		ul.actionMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		ul.errorMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		#errorFontID{
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
			font-size:16px;
			margin-top:15px;
			color:red;
		}
		
		.btn-success.active{
			color:white;
		 	background: #FABA0C;
		 	border: 1px solid #FABA0C;
		}
		
		.btn-success.active:hover,.btn-success.active:focus,.btn-success.active:active,.open .dropdown-toggle.btn-success.active{
			color:white;
		 	background: #d6a317;
		 	border: 1px solid  #d6a317;
		}

		.loadingImage{
        	position: absolute;
          	left: 50%;
           	top: 25%;
         	margin-left: -50px; /*half the image width*/
          	margin-top: 10%; /*half the image height*/
          	color: black;
           	z-index: 1;
            display: none;
    	} 
		
	</style>
    <script type="text/javascript">  
    function showLoadingImg(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	       
	    }, 1000);   
		
		$('body').css('background', 'white');
		$(".container").css("opacity","0.2");
		$("#loader").show();

		return true;
				
	}  
	</script> 
    
    
    <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
	</style>

    
    <!--For login message -->
    <%
    	LoginForm form = (LoginForm) session.getAttribute("USER");
	   	if(form == null){
	   	String loginMessage = "Plase login using valid credentials";
	   	request.setAttribute("loginMessage",loginMessage);
	   	RequestDispatcher dispatcher = request.getRequestDispatcher("Pindex.jsp");
	   	dispatcher.forward(request,response);
	   }
		
	int patientID = form.getPatientID();
	System.out.println("pid:"+patientID);
	String FName = form.getFirstName(); 
	String MName = form.getMiddleName();
	String LName = form.getLastName();
	String patientName = FName+" "+MName+" "+LName;
	int practiceID = form.getPracticeID();
	int changedClinicID = form.getClinicID();
	System.out.println("changedClinicID on patientwelcome:"+changedClinicID);
	
	PatientDAOInf patientDaoInf = new PatientDAOImpl();
	LoginDAOInf daoInf = new LoginDAOImpl();
	ClinicDAOInf clinicDaoInf = new ClinicDAOImpl();
	
	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
	
	eDhanvantariServiceInf serviceInf = new eDhanvantariServiceImpl();

	String searchCriteria = (String) request.getAttribute("searchCriteria");
	System.out.println("searchCriteria:"+searchCriteria);

	String clinicName = daoInf.retrieveClinicName(form.getClinicID());
	
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

			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
			
	%>
    <!-- Ends -->
    
    
    
    <!-- Confirm / Cancel appointment Function -->
    
    <script type="text/javascript">

	function cancelAppt(url){
		if (confirm("Are you sure you want to cancel Appointment?")) {
			document.location = url;
		}
		
	}

	function confirmAppt(url){
		if (confirm("Are you sure you want to confirm appointment?")) {
			document.location = url;
		}
	}

	</script>
	
	<!-- Ends -->
	
	<script type="text/javascript">
	
		function editPatientProfile(patientID){
							
			location.href="RenderPatientProfile?patientID="+patientID;
			
		}
		
		function downloadCRF(visitID,formName){
		
			document.location="DownloadCRF?visitID="+visitID+"&formName="+formName;
		}
		
		function downloadPRESC(visitID,formName){
		
			document.location="DownloadPRESC?visitID="+visitID+"&formName="+formName;
		}
		
		function downloadBILL(visitID,formName){
		
			document.location="DownloadBILL?visitID="+visitID+"&formName="+formName;
		}
		

		function viewCRF(visitID, formName, visitTypeID, apptID) {
			
			viewCRF1(visitID, formName, visitTypeID, apptID);
		}
		
		function viewPRESC(visitID, formName, visitTypeID, apptID) {
			
			viewPRESC1(visitID, formName, visitTypeID, apptID);
		}
		
		function viewBILL(visitID) {
			
			viewBILL1(visitID);
		}
		
		function viewPage(visitID, formName, visitTypeID, apptID) {
			console.log("inside onclick method of view page");
			if(formName == "DefaultGeneralOPD"){
				console.log("inside generic");
				
				document.location="ViewGenericVisit?visitID="+visitID+"&formName="+formName+"&visitTypeID="+visitTypeID+"&apptID="+apptID;
				
			}else if(formName == "telephonic"){
				
				document.location="ViewTelephonicVisit?visitID="+visitID+"&formName="+formName+"&visitTypeID="+visitTypeID+"&apptID="+apptID;
				
			}else if(formName == "DefaultOpthalmologyOPD"){
				
				document.location="ViewOphthVisit?visitID="+visitID+"&formName="+formName+"&visitTypeID="+visitTypeID+"&apptID="+apptID;
				
			}
		}
		
		
	</script>
			
	<%
		//Appointment list messages
		String todayApptMsg = (String) request.getAttribute("todayApptMsg");
		
		String weekApptMsg = (String) request.getAttribute("weekApptMsg");
		
		String monthApptMsg = (String) request.getAttribute("monthApptMsg");
		
	%>
	
	<%
		String patientListEnable = (String) request.getAttribute("patientListEnable");
	%>	
	
	<%

		String patientCheck = (String) request.getAttribute("patientCheck");
	
		if(patientCheck == null || patientCheck == ""){
			patientCheck = "empty";
		}
	
	%>
	
	<!-- Ends -->
    
    <!-- For session timeout -->
    <%
    
	ConfigurationUtil configurationUtil = new ConfigurationUtil();
    
    String sessionTimeoutString = configurationUtil.getSessionTimeOut();
    
    if(sessionTimeoutString == null || sessionTimeoutString == ""){
    	sessionTimeoutString = "30";
    }else if(sessionTimeoutString.isEmpty()){
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
		xmlhttp.open("GET", "VerifyUnlockPIN?lockPIN="+ lockPIN, true);
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
		xmlhttp.open("GET", "VerifyOldPass?oldPass="+ oldPass, true);
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
		xmlhttp.open("GET", "NewPassCheck?newPass="+newPass, true);
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
		xmlhttp.open("GET", "UpdateSecurityCredentials?newPass="+ newPass + "&newPIN=" + newPIN , true);
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
    
    <!-- To show Visit date serach boxes -->
    <script type="text/javascript">
    	function show1(val){
    		if(val == "VisitDate"){
    			$("#dateDivID1").show(1000);
    			$("#searchDivID1").hide(1000);
    			
    		}else{
    			$("#dateDivID1").hide(1000);
    			$("#searchDivID1").show(1000);
    		}
    		
    	}
    	
    </script>
    <!-- Ends -->
    
    <!-- Retrieving patient details based on PatientID -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function retrievePatientDetails(patientID, startDate, endDate, agenda) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var patientDetail = "";

				for ( var i = 0; i < array.Release.length; i++) {
					
					var regNo = array.Release[i].regNo;
					
					if(array.Release[i].regNo == null || array.Release[i].regNo == ""){
						regNo = "";
					}
					
					patientDetail = array.Release[i].patientID + "-" + array.Release[i].firstName + " " + array.Release[i].lastName + "(" 
							+ regNo + ")";

				}
				
				//Calling createEvent function in order to display event added into Calendar
				createEvent(startDate, endDate, agenda, patientDetail);

			}
		};
		xmlhttp.open("GET", "RetrievePatientDetails?patientID="+patientID, true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    
    
    <script type="text/javascript">
    
    	function pageReload(){
    		$("#apptUpdateErrorModal").modal("hide");
    		 //location.reload();
    		 window.location.assign("patientWelcome.jsp");
    	}
    
    </script>
    
    
    
    <!-- Retrieving clinic LIst -->
    
    <%
    	HashMap<Integer, String> practiceMap = daoInf.retrievepracticeList();
    
    	HashMap<Integer, String> clinicMap = daoInf.retrievePatientClinicList(form.getPatientID());
    %>
    
    <!-- Ends -->
    

	<!-- Function to submit change clinicID form -->
	
	<script type="text/javascript">
		
		function submitChangePracticeForm(PracticeID){
			
			if(PracticeID == "-1"){
				alert("No Practice is selected. Please select Practice.");
				
				var array_element = "<select name='' id='' class='form-control' style='border-radius: 5px;'> "+
					"<option value='-1'>Select Practice</option></select>";
				
				document.getElementById("changeClinicForm").innerHTML = array_element;
			}else{
				
				retrieveClinic1(PracticeID);
			}
			
		}
	
		
		function retrieveClinic1(PracticeID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<input type='hidden' name='changedPracticeID' id='changedPracticeID' value='"+PracticeID+"'><select name='changedClinicID' id='changeClinicForm' class='form-control' onchange='submitChangeClinicForm();' "+
						"style='border-radius: 5px;' > <option value='-1'>Select Clinic</option>";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].clinicID+"'>"+array.Release[i].clinic+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No clinic found");
							
							var array_element = "<select name='' id='' class='form-control' style='border-radius: 5px;'> "+
							"<option value='-1'>Select Practice</option></select>";
							
							document.getElementById("changeClinicForm").innerHTML = array_element;
							
						}else{
							
							document.getElementById("changeClinicForm").innerHTML = array_element;	

						}
						
						
						
				}
			};
			xmlhttp.open("GET", "RetrieveClinicListForPractice?practiceID="
					+ PracticeID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
	
		function submitChangeClinicForm(clinicID,patientID){
		
			changeClinicForVisitList(clinicID,patientID);
			
		}
		
		function submitForm(){
    		var visitType = $("#visitTypeID").val();
    		
    		if(visitType == "000"){
    			alert("Please select visit type.");
    			return false;
    		}else{
    			return true;
    		}
    	}
	
	</script>
	
	<!-- Ends -->
	
	
<!-- Change Clinic -->
<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function changeClinicForVisitList(clinicID,patientID) {
			
			$('#visitTableTRID tr:gt(1)').remove();
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var trID = 0;
					var trTag="";
					var check = 0;
					var id = 0;
					var visitDate = "";
					var visitType = "";
					var diagnosis = "";
					var formName = "";
					var visitTypeID = "";
					var aptID = "";
					//var clinicID = 0;
					
					for ( var i = 0; i < array.Release1.length; i++) {
						
						check = array.Release1[i].check;
						console.log("Check::"+check);
						
						id = array.Release1[i].id;
						visitDate = array.Release1[i].visitDate;
						visitType = array.Release1[i].visitType;
						diagnosis = array.Release1[i].diagnosis;
						formName = array.Release1[i].formName;
						visitTypeID = array.Release1[i].visitTypeID;
						aptID = array.Release1[i].aptID;
						//clinicID = array.Release1[i].clinicID;
						
						console.log(" visit:"+visitDate+"  visit Type:"+visitType+" diagnosis:"+diagnosis+" formname:"+formName+" visitTypeID:"+visitTypeID+" aptID:"+aptID);
						
						trTag += "<tr id='visitTRIDD"+id+"'>"+
									"<td style='text-align: center;'>"+visitDate+"</td>"+
									"<td style='text-align: center;'>"+visitType+"</td>"+
									"<td style='text-align: center;'>"+diagnosis+"</td>"+
									"<td style='text-align: center;'><a href='javascript:viewCRF(\""+id+"\",\""+formName+"\",\""+visitTypeID+"\",\""+aptID+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;'>View</a>"+
																	"<a href='javascript:downloadCRF(\""+id+"\",\""+formName+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline;'>Download</a>"+
									"</td>"+														
									
									"<td style='text-align: center;'><a href='javascript:viewPRESC(\""+id+"\",\""+formName+"\",\""+visitTypeID+"\",\""+aptID+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;'>View</a>"+
																	"<a href='javascript:downloadPRESC(\""+id+"\",\""+formName+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline;'>Download</a>"+
									"</td>"+
									
									"<td style='text-align: center;'><a href='javascript:viewBILL(\""+id+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;'>View</a>"+
																	"<a href='javascript:downloadBILL(\""+id+"\",\""+formName+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline;'>Download</a>"+
									"</td>"+
									
									"<td style='text-align: center;'><a href='javascript:viewPage(\""+id+"\",\""+formName+"\",\""+visitTypeID+"\",\""+aptID+"\");' style='font-size: 15px;font-weight: bold;text-decoration: underline;'>View</a>"+
									"</td>"+
									"</tr>";
							
				    }
					$(trTag).insertAfter($('#visitTRIDD'));
					
					if(check == 1){
						
						document.getElementById("successMSG").innerHTML = "Clinic changed successfully.";
						
						setTimeout(function(){
							$("#successErrorMsgID").hide(1000);
						},2000);
						
				
					}else{
						
						document.getElementById("exceptionMSG").innerHTML = "Failed to change clinic. Please check server logs for more details.";
						
						setTimeout(function(){
							$("#successErrorMsgID").hide(1000);
						},2000);
					}

				}
			};
			xmlhttp.open("GET", "ChangeClinicForVisit?clinicID="+clinicID+"&patientID="+patientID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->

<!-- VIEW CRF -->
<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function viewCRF1(visitID, formName, visitTypeID, apptID) {
	console.log("inside ajax method..");
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					array_element = "";
					var trID = 0; var trTag=""; var check = 0;
					var patientID = 0; var patientName = ""; var age = ""; var regNo = ""; var mobile = ""; var gender = ""; var bloodGroup = ""; var visitDate = ""; var cancerType = "";
					var complaintID = 0; var complaints = ""; var complaintDuration = ""; var complaintComments = "";
					var historyID = 0; var diagnosis = ""; var description = ""; 
					var medicationID = 0; var drugName = ""; var medicationDuration = ""; var medicationComments = "";
					var visitType = ""; var medicalNotes = "";var eyeLidUpperOD = ""; var eyeLidUpperOS = ""; var eyeLidLowerOD =""; var eyeLidLowerOS ="";
					var visualAcuityDistOD = ""; var visualAcuityNearOD = ""; var visualAcuityDistOS =""; var visualAcuityNearOS = ""; 
					var pinholeVisionDistOD =""; var pinholeVisionNearOD =""; var pinholeVisionDistOS =""; var pinholeVisionNearOS =""; 
					var BCVADistOD =""; var BCVANearOD =""; var BCVADistOS =""; var BCVANearOS =""; var conjunctivaOD =""; var conjunctivaOS = ""; 
					var scleraOD = ""; var scleraOS = ""; var CorneaOD = ""; var CorneaOS =""; var ACOD =""; var ACOS =""; var irisOD =""; var irisOS = ""; 
					var lensOD = ""; var lensOS =""; var discOD =""; var vesselOD = ""; var maculaOD = ""; var discOS =""; var vesselOS =""; var maculaOS = ""; 
					var posteriorComment = ""; var IODOD =""; var IODOS =""; var sacOD =""; var sacOS = ""; var k1OD =""; var k1OS =""; var k2OD =""; var k2OS ="";
					var axialLengthOD =""; var axialLengthOS =""; var IOLOD =""; var IOLOS = ""; var biometryComment ="";
					var smoking =""; var smokingDetails = ""; var alcohol=""; var alcoholDetails =""; var tobacco =""; var tobaccoDetails=""; var foodChoice =""; var foodChoiceDetails=""; 
					var weight=""; var pulse =""; var systolicBP =""; var diastolicBP="";
					
					if(array.Release1){
						
					  for ( var i = 0; i < array.Release1.length; i++) {
						
						check = array.Release1[i].check;
						console.log("Check::"+check);
						
						patientID = array.Release1[i].patientID;
						patientName = array.Release1[i].patientName;
						age = array.Release1[i].age;
						regNo = array.Release1[i].regNo;
						mobile = array.Release1[i].mobile;
						gender = array.Release1[i].gender;
						bloodGroup = array.Release1[i].bloodGroup;
						visitDate = array.Release1[i].visitDate;
						cancerType = array.Release1[i].cancerType;
						
						complaintID = array.Release1[i].complaintID;
						complaints = array.Release1[i].complaints;
						if(typeof complaints === "undefined" || complaints == null){
							complaints = "";
						}else{
							complaints = complaints;
						}
						
						complaintDuration = array.Release1[i].complaintDuration;
						if(typeof complaintDuration === "undefined" || complaintDuration == null){
							complaintDuration = "";
						}else{
							complaintDuration = complaintDuration;
						}
						
						complaintComments = array.Release1[i].complaintComments;
						if(typeof complaintComments === "undefined" || complaintComments == null){
							complaintComments = "";
						}else{
							complaintComments = complaintComments;
						}
						
						
						historyID = array.Release1[i].historyID;
						diagnosis = array.Release1[i].diagnosis;
						if(typeof diagnosis === "undefined" || diagnosis == null){
							diagnosis = "";
						}else{
							diagnosis = diagnosis;
						}
						
						description = array.Release1[i].description;
						if(typeof description === "undefined" || description == null){
							description = "";
						}else{
							description = description;
						}
						
						
						medicationID = array.Release1[i].medicationID;
						drugName = array.Release1[i].drugName;
						if(typeof drugName === "undefined" || drugName == null){
							drugName = "";
						}else{
							drugName = drugName;
						}
						
						medicationDuration = array.Release1[i].medicationDuration;
						if(typeof medicationDuration === "undefined" || medicationDuration == null){
							medicationDuration = "";
						}else{
							medicationDuration = medicationDuration;
						}
						
						medicationComments = array.Release1[i].medicationComments;
						if(typeof medicationComments === "undefined" || medicationComments == null){
							medicationComments = "";
						}else{
							medicationComments = medicationComments;
						}
						
						smoking = array.Release1[i].smoking;
						smokingDetails = array.Release1[i].smokingDetails;
						alcohol = array.Release1[i].alcohol;
						alcoholDetails = array.Release1[i].alcoholDetails;
						tobacco = array.Release1[i].tobacco;
						tobaccoDetails = array.Release1[i].tobaccoDetails;
						foodChoice = array.Release1[i].foodChoice;
						foodChoiceDetails = array.Release1[i].foodChoiceDetails;
						
						weight = array.Release1[i].weight;
						if(typeof weight === "undefined" || weight == null){
							weight = "";
						}else{
							weight = weight;
						}
						
						pulse = array.Release1[i].pulse;
						if(typeof pulse === "undefined" || pulse == null){
							pulse = "";
						}else{
							pulse = pulse;
						}
						
						systolicBP = array.Release1[i].systolicBP;
						if(typeof systolicBP === "undefined" || systolicBP == null){
							systolicBP = "";
						}else{
							systolicBP = systolicBP;
						}
						
						diastolicBP = array.Release1[i].diastolicBP;
						if(typeof diastolicBP === "undefined" || diastolicBP == null){
							diastolicBP = "";
						}else{
							diastolicBP = diastolicBP;
						}
						
						
						
						console.log(" patientID:"+patientID+"  patientName:"+patientName+" age:"+age+" regNo:"+regNo+" mobile:"+mobile+" gender:"+gender+" bloodGroup:"+bloodGroup+" visitDate:"+visitDate+" cancerType:"+cancerType);
						console.log(" complaintID:"+complaintID+"  complaints:"+complaints+" ComplaintDuration:"+complaintDuration+" ComplaintComments:"+complaintComments);
						console.log(" historyID:"+historyID+"  diagnosis:"+diagnosis+" description:"+description);
						console.log(" medicationID:"+medicationID+"  drugName:"+drugName+" MedicationDuration:"+medicationDuration+" MedicationComments:"+medicationComments);
								
						array_element ="<tr id='visitTRIDD"+patientID+"'>"+
							"<div class='row'>"+	
						     "<div class='col-md-6 col-sm-6 col-xs-12'>"+
						     	"<div class='col-md-3'><label for='name' style='font-size: 15px; padding-left: 12px; margin-top: 0.5em;'>Name:</label></div>"+
				               	"<div class='col-md-8'><input type='text' class='form-control' style='margin-top: 0.2em;' readonly='readonly' value='"+patientName+"' /></div>"+
					          "</div>"+
			                    
			                  "<div class='col-md-6 col-sm-6 col-xs-12'>"+
			                  	"<div class='col-md-5'><label for='name' style='font-size: 15px; margin-top: 0.5em; padding-left:62px;'>Age:</label></div>"+
				               	"<div class='col-md-6'><input type='text' class='form-control' readonly='readonly'style='margin-top: 0.2em;' value='"+age+"' /></div>"+
			                  "</div>"+                      
			              "</div>"+ 
									
			              "<div class='row' style='padding-top: 10px;'>"+	
						     "<div class='col-md-6 col-sm-6 col-xs-12'>"+
						     	"<div class='col-md-3'><label for='name' style='font-size: 15px; padding-left: 38px; margin-top: -1%;'>ID:</label></div>"+
				               	"<div class='col-md-8'><input type='text' class='form-control' readonly='readonly' style='margin-top: -1%;' value='"+regNo+"'/></div>"+
					          "</div>"+
			                    
			                  "<div class='col-md-6 col-sm-6 col-xs-12'>"+
			                  	"<div class='col-md-5'><label for='name' style='font-size: 15px; margin-top: 0.5em; padding-left:43px'>Mobile:</label></div>"+
				               	"<div class='col-md-6'><input type='text' class='form-control' readonly='readonly' style='margin-top: 0.5em; padding-left:11px' value='"+mobile+"'/></div>"+
			                  "</div>"+                      
			              "</div>"+ 
			              
			              "<div class='row' style='padding-top: 10px;'>"+	
						     "<div class='col-md-6 col-sm-6 col-xs-12'>"+
						     	"<div class='col-md-3'><label for='name' style='font-size: 15px; padding-left: 0px; margin-top: -1%;'>Gender:</label></div>"+
				               	"<div class='col-md-8'><input type='text' class='form-control' readonly='readonly' style='margin-top: -1%;' value='"+gender+"' /></div>"+
					          "</div>"+
			                    
			                  "<div class='col-md-6 col-sm-6 col-xs-12'>"+
			                  	"<div class='col-md-6'><label for='name' style='font-size: 15px; margin-top: 0.5em;'>Blood Group:</label></div>"+
				               	"<div class='col-md-5'><input type='text' class='form-control' readonly='readonly' style='margin-top:0.5em;' value='"+bloodGroup+"' /></div>"+
			                  "</div>"+                      
			              "</div>"+   
			              
			             
			               "<div class='row' style='padding-top: 10px;'>"+	
						     "<div class='col-md-8 col-sm-6 col-xs-12'>"+
						     	"<div class='col-md-4'><label for='name' style='font-size: 15px;'>Date of visit:</label></div>"+
				               	"<div class='col-md-4' style='margin-left: 2px;'><input type='text' class='form-control' readonly='readonly' name='visitDate' value='"+visitDate+"' /></div>"+
					          "</div>"+      
			              "</div>"+ 
			              
			              "<div class='item form-group' >"+
						  	"<div class='col-md-8 col-sm-8 col-xs-12' style='padding-top: 20px;'>"+
			                        "<div class='col-md-3 col-sm-3 col-xs-4'  style='margin-right: 14px;'><label for='User Type' style='font-size: 15px; padding-left: 0px;'>Diagnosis*</label></div>"+
			                       
			                          "<div class='col-md-8 col-sm-5 col-xs-8'>"+
			                          	"<div class='col-md-12'><input type='text' class='form-control' readonly='readonly' name='cancerType' value='"+cancerType+"' /></div>"+
								       "</div>"+
			                "</div>"+
			                "</div>"+
			                
			                "<div class='row' >"+
				              "<div class='col-md-6'><label for='User Type' style='font-size: 15px; padding-left: 15px; padding-top: 25px;'>Present Complaints</label></div>"+
				            	 "</div>"+ 
			             			"<div class='item form-group'>"+
				                      "<div class='control-label col-md-12  col-sm-12 col-xs-12' style='padding-left:50px;'>"+
								      	"<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'>"+
							                "<thead>"+
												"<tr>"+
							                        "<th style='width: 30%;' align='left'>Symptom</th>"+
							                        "<th style='width: 20%' align='left'>Duration (Days)</th>"+
							                        "<th style='width: 50%;' align='left'>Comments</th>"+
							                    "</tr>"+
							                 "</thead>"+
							                 "<tbody>"+ 
							               "<tr>"+
							                 "<td><input type='text' class='form-control' name='' id='symptomOID'  placeholder='Symptom' readonly='readonly' value='"+complaints+"'></td>"+
							                 "<td><input type='number' class='form-control' name='' id='presentDurationID' readonly='readonly' placeholder='Duration' value='"+complaintDuration+"'></td>"+
							                 "<td><input type='text' class='form-control' name='' id='presentCommentsID' readonly='readonly' placeholder='Comments' value='"+complaintComments+"'></td>"+
							               "</tr>"+
							                "</tbody>"+
							             "</table>"+
								 "</div>"+ 
		      				"</div>"+
		      			
		      				
		      				"<div class='item form-group'>"+
				              "<div class='col-md-6 col-xs-6 col-sm-6'><label for='User Type' style='font-size: 15px; padding-left: 5px; padding-top: 15px;'>Medical History</label></div>"+
				            	 "</div>"+
			             			"<div class='item form-group'>"+
				                      "<div class='control-label col-md-12  col-sm-12 col-xs-12' style='padding-left:50px;'>"+
								      	"<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'>"+
							                "<thead>"+
												"<tr>"+
							                        "<th style='width: 28%;' align='left'>Diagnosis</th>"+
							                        "<th style='width: 60%;' align='left'>Description</th>"+
							                    "</tr>"+
							                 "</thead>"+
							                 "<tbody>"+ 
							             "<tr>"+
							                 "<td><input type='text' class='form-control' name=''  id='MHOdiagnosisID' readonly='readonly' placeholder='Diagnosis' value='"+diagnosis+"'></td>"+
							                 "<td><input type='text' class='form-control' name='' id='MHOdescriptionID' readonly='readonly' placeholder='Description' value='"+description+"'></td>"+
							             "</tr>"+
							                "</tbody>"+
							             "</table>"+
							         "</div>"+
								 "</div>"+
								 
								 
								 "<div class='row'>"+
							        "<div class='col-md-6'><label for='User Type' style='font-size: 15px; padding-left: 15px; padding-top: 15px;'>Personal History</label></div>"+
							    "</div>"+ 
							    
							    "<div class='item form-group'>"+
							    "<div class='control-label col-md-12 col-sm-12 col-xs-12' style='padding-left:30px; margin-top:-3%;'>"+
			                     	"<div class='row' style='padding-top: 30px;'>"+
			                     	   "<div class='col-md-6 col-sm-6'> "+
			                			"<div class='col-md-5'><label for='User Type' style='font-size: 15px;'>Smoking</label></div>"+
			                			"<div class='col-md-6'  style=' padding-left:34px;'>";
			                	 		if(smoking == "Yes"){
			                	 			array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='Yes' checked>Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						}else if(smoking == "No"){
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='No' checked style='padding-left:10px;'>No<label></div></div>";
			    						}else{
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistorySmoking' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						} 
			                	 		
			                	 		array_element += "</div>"+
										
					     			"<div class='col-md-6 col-sm-6'>"+
										"<div class='col-md-4'><label for='User Type' style='font-size: 15px;'>Comments</label></div>"+
										"<div class='col-md-8'><input type='text'  readonly='readonly' name='personalHistorySmokingDetails'  class='form-control' placeholder='comments' value='"+smokingDetails+"'></div>"+
			                 		"</div>"+
			                 		"</div>"+
			                 		
			                 		"<div class='row' >"+
		                      		"<div class='col-md-6 col-sm-6'>"+             
			                			"<div class='col-md-5'><label for='User Type' style='font-size: 15px;'>Alcohol</label></div>"+                  
				               			"<div class='col-md-6'  style=' margin-right:10px; padding-left:34px;'>";
			                			if(alcohol == "Yes"){
			                	 			array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='Yes' checked>Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						}else if(alcohol == "No"){
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='No' checked style='padding-left:10px;'>No<label></div></div>";
			    						}else{
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled' name='personalHistoryAlcohol' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						} 
				               			
			                			array_element += "</div>"+
									
									"<div class='col-md-6 col-sm-6'>"+
										"<div class='col-md-4'><label for='User Type' style='font-size: 15px;'>Comments</label></div>"+
										"<div class='col-md-8'><input type='text'  readonly='readonly' name='personalHistoryAlcoholDetails'  class='form-control' placeholder='comments' value='"+alcoholDetails+"'></div>"+
									"</div>"+
							 		"</div>"+
							 		
							 		"<div class='row' style='padding-top: 20px;'>"+
		                      		"<div class='col-md-6 col-sm-6'>"+           
			                			"<div class='col-md-5'><label for='User Type' style='font-size: 15px;' >Tobacco</label></div>"+
				               			"<div class='col-md-6' style='margin-right:10px;padding-left:34px;'>";
				               			if(tobacco == "Yes"){
			                	 			array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryTobacco' value='Yes' checked>Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryTobacco' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						}else if(tobacco == "No"){
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryTobacco' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryTobacco' value='No' checked style='padding-left:10px;'>No<label></div></div>";
			    						}else{
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryTobacco' value='Yes' >Yes</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryTobacco' value='No' style='padding-left:10px;'>No</label></div></div>";
			    						} 
				               			array_element += "</div>"+
				               			
									"<div class='col-md-6 col-sm-6'> "+
										"<div class='col-md-4'><label for='User Type' style='font-size: 15px;' >Comments</label></div>"+
	 	                          		"<div class='col-md-8'><input type='text'  readonly='readonly' name='personalHistoryTobaccoDetails'  class='form-control' placeholder='comments' value='"+tobaccoDetails+"'></div>"+
		                        	"</div>"+
			                		"</div>"+
			                		
			                		"<div class='row' >"+
		                      		"<div class='col-md-6 col-sm-6'>"+             
			                			"<div class='col-md-5'><label for='User Type' style='font-size: 15px;'>Food Choice</label></div>"+
				               			"<div class='col-md-6' style='margin-right:10px; padding-left:35px;'>";
				               			if(foodChoice == "Veg"){
			                	 			array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryFoodChoice' value='Yes' checked>Veg</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio' disabled='disabled'  name='personalHistoryFoodChoice' value='No' style='padding-left:10px;'>Non-Veg</label></div></div>";
			    						}else if(foodChoice == "Non-Veg"){
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryFoodChoice' value='Yes' >Veg</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryFoodChoice' value='No' checked style='padding-left:10px;'>Non-Veg<label></div></div>";
			    						}else{
			    							array_element +=  "<div class='col-md-9'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryFoodChoice' value='Yes' >Veg</label></div>";
			                	 			array_element += "<div class='col-md-1'><label class='radio'><input type='radio'  disabled='disabled' name='personalHistoryFoodChoice' value='No' style='padding-left:10px;'>Non-Veg</label></div></div>";
			    						} 
				               			array_element += "</div>"+
										
									"<div class='col-md-6 col-sm-6'>"+
										"<div class='col-md-4'><label for='User Type' style='font-size: 15px;'>Comments</label></div>"+
	 	                          		"<div class='col-md-8'><input type='text' readonly='readonly'  name='personalHistoryFoodChoiceDetails'  class='form-control' placeholder='comments' value='"+foodChoiceDetails+"'></div>"+
		                        	"</div>"+
			                 		"</div>"+
			                 	"</div>"+
			                 	"</div>"+ 
			                 	
							    
							    "<div class='item form-group' style='padding-top: 20px;'>"+
					              "<div class='col-md-6'><label for='User Type' style='font-size: 15px; padding-left: 5px; padding-top: 15px;'>Current Medication</label></div>"+
					            	 "</div>"+ 
				             			"<div class='item form-group'>"+
					                      "<div class='control-label col-md-12  col-sm-12 col-xs-12' style='padding-left:50px;''>"+
									      	"<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'>"+
								                "<thead>"+
													"<tr>"+
								                        "<th style='width: 30%;' align='left'>Drug Name</th>"+
								                        "<th style='width: 20%;' align='left'>Duration (Days)</th>"+
								                        "<th align='left'>Comments</th>"+
								                    "</tr>"+
								                 "</thead>"+
								                 "<tbody>"+ 
								                 "<tr>"+
								                 "<td><input type='text' class='form-control' name='' id='CurrentMedidrugnameID' readonly='readonly' placeholder='Drug Name'  value='"+drugName+"'></td>"+
								                 "<td><input type='number' class='form-control' name='' id='CurrentMedidurationID' readonly='readonly' placeholder='Duration'  value='"+medicationDuration+"'></td>"+
								                 "<td><input type='text' class='form-control' name='' id='CurrentMedicommentsID'readonly='readonly'  placeholder='Comments'  value='"+medicationComments+"'></td>"+
			      								 "</tr>"+
			      								
								                "</tbody>"+
								             "</table>"+
								         "</div>"+
									 "</div>"+ 
									 
									 
									 "<div class='item form-group col-md-12' >"+
				                      
				                     "<div class='col-md-4' ><label for='User Type' style='font-size: 15px; margin-left:6px; padding-top:10%;'>Vital Signs :</label></div>"+
				                 "<div class='col-md-12  col-sm-12 col-xs-8' style='margin-left:87px;'>"+
					                     "<div class='col-xs-12 col-md-3 col-sm-3' style='margin-left:-7%;' >"+
					                        "<label for='User Type' class='control-label' style='font-size: 15px; margin-left:15px;' >Weight</label>"+
					                        "<input type='number' style='width: 70%; margin-left:15px;' readonly='readonly' class='form-control' name='weight' id='weightID' value='"+weight+"'  placeholder='Weight (in kgs)'>"+
					                     "</div>"+
				                        
				                        "<div class='col-xs-12 col-md-3 col-sm-3' style='margin-left:0%;'>"+
					                       	"<label for='User Type' class='control-label' style='font-size: 15px;' >Systolic-BP</label>"+
					                        "<input class='form-control' style='width: 70%;' id='systolicBPID' onkeyup='myFunction1('sysSpanID',this.value)' type='number' readonly='readonly' name='OEBPSys' value='"+pulse+"'  placeholder='Systolic _'>"+
								        	"<font style='color: red;' id = 'sysSpanID'></font>"+
								        "</div>"+
				                        
				                        "<div class='col-xs-12 col-md-3 col-sm-3' style='margin-left:0%;'>"+
				                          	"<label for='User Type' class='control-label' style='font-size: 15px;'>Diastolic-BP</label>"+
					                        "<input class='form-control' style='width: 71%;' id='diastolicBPID' onkeyup='myFunction1('diaSpanID',this.value)' type='number'  readonly='readonly' name='OEBPDia' value='"+systolicBP+"'  placeholder='Diastolic_'>"+
				                      		"<font style='color: red;' id = 'diaSpanID'></font>"+
										"</div>"+
				                       
				                       "<div class='col-xs-12 col-md-3 col-sm-3' style='margin-left:0%;'>"+
				                       	  	"<label for='User Type' class='control-label' style='font-size: 15px;'>Pulse</label>"+
				                          	"<input type='number' style='width: 70%;' class='form-control' name='OEPulse' id='pulseID' readonly='readonly' onkeyup='myFunction1('pulseSpanID',this.value)' value='"+diastolicBP+"'  placeholder='Pulse'>"+
				                       		"<font style='color: red;' id = 'pulseSpanID'></font>"+
				                       "</div>"
				                   "</div>"+
			                   "</div>"+
			              
						"</tr>";
							
						
						document.getElementById("CRFDetailsID").innerHTML =  array_element;
						
						if(check == 1){
							
							document.getElementById("successMSG").innerHTML = "CRF Details displayed successfully.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
							
					
						}else{
							
							document.getElementById("exceptionMSG").innerHTML = "Failed to display CRF Details. Please check server logs for more details.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
						}
				    }
				}else if(array.Release2){
										
					for ( var i = 0; i < array.Release2.length; i++) {
						
						check = array.Release2[i].check;
						console.log("Check::"+check);
						
						patientID = array.Release2[i].patientID;
						if(typeof patientID === "undefined" || patientID == null){
							patientID = "";
						}else{
							patientID = patientID;
						}
						
						patientName = array.Release2[i].patientName;
						if(typeof patientName === "undefined" || patientName == null){
							patientName = "";
						}else{
							patientName = patientName;
						}
						
						visitDate = array.Release2[i].visitDate;
						if(typeof visitDate === "undefined" || visitDate == null){
							visitDate = "";
						}else{
							visitDate = visitDate;
						}
						
						age = array.Release2[i].age;
						if(typeof age === "undefined" || age == null){
							age = "";
						}else{
							age = age;
						}
						
						gender = array.Release2[i].gender;
						if(typeof gender === "undefined" || gender == null){
							gender = "";
						}else{
							gender = gender;
						}
						
						cancerType = array.Release2[i].cancerType;
						if(typeof cancerType === "undefined" || cancerType == null){
							cancerType = "";
						}else{
							cancerType = cancerType;
						}
						
						visitType = array.Release2[i].visitType;
						if(typeof visitType === "undefined" || visitType == null){
							visitType = "";
						}else{
							visitType = visitType;
						}
						
						medicalNotes = array.Release2[i].medicalNotes;
						if(typeof medicalNotes === "undefined" || medicalNotes == null){
							medicalNotes = "";
						}else{
							medicalNotes = medicalNotes;
						}
						
						eyeLidUpperOD = array.Release2[i].eyeLidUpperOD;
						if(typeof eyeLidUpperOD === "undefined" || eyeLidUpperOD == null){
							eyeLidUpperOD = "";
						}else{
							eyeLidUpperOD = eyeLidUpperOD;
						}
						
						eyeLidUpperOS = array.Release2[i].eyeLidUpperOS;
						if(typeof eyeLidUpperOS === "undefined" || eyeLidUpperOS == null){
							eyeLidUpperOS = "";
						}else{
							eyeLidUpperOS = eyeLidUpperOS;
						}
						
						eyeLidLowerOD = array.Release2[i].eyeLidLowerOD;
						if(typeof eyeLidLowerOD === "undefined" || eyeLidLowerOD == null){
							eyeLidLowerOD = "";
						}else{
							eyeLidLowerOD = eyeLidLowerOD;
						}
						
						eyeLidLowerOS = array.Release2[i].eyeLidLowerOS;
						if(typeof eyeLidLowerOS === "undefined" || eyeLidLowerOS == null){
							eyeLidLowerOS = "";
						}else{
							eyeLidLowerOS = eyeLidLowerOS;
						}
						
						visualAcuityDistOD = array.Release2[i].visualAcuityDistOD;
						if(typeof visualAcuityDistOD === "undefined" || visualAcuityDistOD == null){
							visualAcuityDistOD = "";
						}else{
							visualAcuityDistOD = visualAcuityDistOD;
						}
						
						visualAcuityNearOD = array.Release2[i].visualAcuityNearOD;
						if(typeof visualAcuityNearOD === "undefined" || visualAcuityNearOD == null){
							visualAcuityNearOD = "";
						}else{
							visualAcuityNearOD = visualAcuityNearOD;
						}
						
						visualAcuityDistOS = array.Release2[i].visualAcuityDistOS;
						if(typeof visualAcuityDistOS === "undefined" || visualAcuityDistOS == null){
							visualAcuityDistOS = "";
						}else{
							visualAcuityDistOS = visualAcuityDistOS;
						}
						
						visualAcuityNearOS = array.Release2[i].visualAcuityNearOS;
						if(typeof visualAcuityNearOS === "undefined" || visualAcuityNearOS == null){
							visualAcuityNearOS = "";
						}else{
							visualAcuityNearOS = visualAcuityNearOS;
						}
						
						pinholeVisionDistOD = array.Release2[i].pinholeVisionDistOD;
						if(typeof pinholeVisionDistOD === "undefined" || pinholeVisionDistOD == null){
							pinholeVisionDistOD = "";
						}else{
							pinholeVisionDistOD = pinholeVisionDistOD;
						}
						
						pinholeVisionNearOD = array.Release2[i].pinholeVisionNearOD;
						if(typeof pinholeVisionNearOD === "undefined" || pinholeVisionNearOD == null){
							pinholeVisionNearOD = "";
						}else{
							pinholeVisionNearOD = pinholeVisionNearOD;
						}
						
						pinholeVisionDistOS = array.Release2[i].pinholeVisionDistOS;
						if(typeof pinholeVisionDistOS === "undefined" || pinholeVisionDistOS == null){
							pinholeVisionDistOS = "";
						}else{
							pinholeVisionDistOS = pinholeVisionDistOS;
						}
						
						pinholeVisionNearOS = array.Release2[i].pinholeVisionNearOS;
						if(typeof pinholeVisionNearOS === "undefined" || pinholeVisionNearOS == null){
							pinholeVisionNearOS = "";
						}else{
							pinholeVisionNearOS = pinholeVisionNearOS;
						}
						
						BCVADistOD = array.Release2[i].BCVADistOD;
						if(typeof BCVADistOD === "undefined" || BCVADistOD == null){
							BCVADistOD = "";
						}else{
							BCVADistOD = BCVADistOD;
						}
						
						BCVANearOD = array.Release2[i].BCVANearOD;
						if(typeof BCVANearOD === "undefined" || BCVANearOD == null){
							BCVANearOD = "";
						}else{
							BCVANearOD = BCVANearOD;
						}
						
						BCVADistOS = array.Release2[i].BCVADistOS;
						if(typeof BCVADistOS === "undefined" || BCVADistOS == null){
							BCVADistOS = "";
						}else{
							BCVADistOS = BCVADistOS;
						}
						
						BCVANearOS = array.Release2[i].BCVANearOS;
						if(typeof BCVANearOS === "undefined" || BCVANearOS == null){
							BCVANearOS = "";
						}else{
							BCVANearOS = BCVANearOS;
						}
						
						conjunctivaOD = array.Release2[i].conjunctivaOD;
						if(typeof conjunctivaOD === "undefined" || conjunctivaOD == null){
							conjunctivaOD = "";
						}else{
							conjunctivaOD = conjunctivaOD;
						}
						
						conjunctivaOS = array.Release2[i].conjunctivaOS;
						if(typeof conjunctivaOS === "undefined" || conjunctivaOS == null){
							conjunctivaOS = "";
						}else{
							conjunctivaOS = conjunctivaOS;
						}
						
						scleraOD = array.Release2[i].scleraOD;
						if(typeof scleraOD === "undefined" || scleraOD == null){
							scleraOD = "";
						}else{
							scleraOD = scleraOD;
						}
						
						scleraOS = array.Release2[i].scleraOS;
						if(typeof scleraOS === "undefined" || scleraOS == null){
							scleraOS = "";
						}else{
							scleraOS = scleraOS;
						}
						
						CorneaOD = array.Release2[i].CorneaOD;
						if(typeof CorneaOD === "undefined" || CorneaOD == null){
							CorneaOD = "";
						}else{
							CorneaOD = CorneaOD;
						}
						
						CorneaOS = array.Release2[i].CorneaOS;
						if(typeof CorneaOS === "undefined" || CorneaOS == null){
							CorneaOS = "";
						}else{
							CorneaOS = CorneaOS;
						}
						
						ACOD = array.Release2[i].ACOD;
						if(typeof ACOD === "undefined" || ACOD == null){
							ACOD = "";
						}else{
							ACOD = ACOD;
						}
						
						ACOS = array.Release2[i].ACOS;
						if(typeof ACOS === "undefined" || ACOS == null){
							ACOS = "";
						}else{
							ACOS = ACOS;
						}
						
						irisOD = array.Release2[i].irisOD;
						if(typeof irisOD === "undefined" || irisOD == null){
							irisOD = "";
						}else{
							irisOD = irisOD;
						}
						
						irisOS = array.Release2[i].irisOS;
						if(typeof irisOS === "undefined" || irisOS == null){
							irisOS = "";
						}else{
							irisOS = irisOS;
						}
						
						lensOD = array.Release2[i].lensOD;
						if(typeof lensOD === "undefined" || lensOD == null){
							lensOD = "";
						}else{
							lensOD = lensOD;
						}
						
						lensOS = array.Release2[i].lensOS;
						if(typeof lensOS === "undefined" || lensOS == null){
							lensOS = "";
						}else{
							lensOS = lensOS;
						}
						
						discOD = array.Release2[i].discOD;
						if(typeof discOD === "undefined" || discOD == null){
							discOD = "";
						}else{
							discOD = discOD;
						}
						
						vesselOD = array.Release2[i].vesselOD;
						if(typeof vesselOD === "undefined" || vesselOD == null){
							vesselOD = "";
						}else{
							vesselOD = vesselOD;
						}
						
						maculaOD = array.Release2[i].maculaOD;
						if(typeof maculaOD === "undefined" || maculaOD == null){
							maculaOD = "";
						}else{
							maculaOD = maculaOD;
						}
						
						discOS = array.Release2[i].discOS;
						if(typeof discOS === "undefined" || discOS == null){
							discOS = "";
						}else{
							discOS = discOS;
						}
						
						vesselOS = array.Release2[i].vesselOS;
						if(typeof vesselOS === "undefined" || vesselOS == null){
							vesselOS = "";
						}else{
							vesselOS = vesselOS;
						}
						
						maculaOS = array.Release2[i].maculaOS;
						if(typeof maculaOS === "undefined" || maculaOS == null){
							maculaOS = "";
						}else{
							maculaOS = maculaOS;
						}
						
						posteriorComment = array.Release2[i].posteriorComment;
						if(typeof posteriorComment === "undefined" || posteriorComment == null){
							posteriorComment = "";
						}else{
							posteriorComment = posteriorComment;
						}
						
						IODOD = array.Release2[i].IODOD;
						if(typeof IODOD === "undefined" || IODOD == null){
							IODOD = "";
						}else{
							IODOD = IODOD;
						}
						
						IODOS = array.Release2[i].IODOS;
						if(typeof IODOS === "undefined" || IODOS == null){
							IODOS = "";
						}else{
							IODOS = IODOS;
						}
						
						sacOD = array.Release2[i].sacOD;
						if(typeof sacOD === "undefined" || sacOD == null){
							sacOD = "";
						}else{
							sacOD = sacOD;
						}
						
						sacOS = array.Release2[i].sacOS;
						if(typeof sacOS === "undefined" || sacOS == null){
							sacOS = "";
						}else{
							sacOS = sacOS;
						}
						
						k1OD = array.Release2[i].k1OD;
						if(typeof k1OD === "undefined" || k1OD == null){
							k1OD = "";
						}else{
							k1OD = k1OD;
						}
						
						k1OS = array.Release2[i].k1OS;
						if(typeof k1OS === "undefined" || k1OS == null){
							k1OS = "";
						}else{
							k1OS = k1OS;
						}
						
						k2OD = array.Release2[i].k2OD;
						if(typeof k2OD === "undefined" || k2OD == null){
							k2OD = "";
						}else{
							k2OD = k2OD;
						}
						
						k2OS = array.Release2[i].k2OS;
						if(typeof k2OS === "undefined" || k2OS == null){
							k2OS = "";
						}else{
							k2OS = k2OS;
						}
						
						axialLengthOD = array.Release2[i].axialLengthOD;
						if(typeof axialLengthOD === "undefined" || axialLengthOD == null){
							axialLengthOD = "";
						}else{
							axialLengthOD = axialLengthOD;
						}
						
						axialLengthOS = array.Release2[i].axialLengthOS;
						if(typeof axialLengthOS === "undefined" || axialLengthOS == null){
							axialLengthOS = "";
						}else{
							axialLengthOS = axialLengthOS;
						}
						
						IOLOD = array.Release2[i].IOLOD;
						if(typeof IOLOD === "undefined" || IOLOD == null){
							IOLOD = "";
						}else{
							IOLOD = IOLOD;
						}
						
						IOLOS = array.Release2[i].IOLOS;
						if(typeof IOLOS === "undefined" || IOLOS == null){
							IOLOS = "";
						}else{
							IOLOS = IOLOS;
						}
						
						biometryComment = array.Release2[i].biometryComment;
						if(typeof biometryComment === "undefined" || biometryComment == null){
							biometryComment = "";
						}else{
							biometryComment = biometryComment;
						}
						
						document.getElementById("CRFDetailsID").innerHTML ="<tr id='visitTRIDD"+patientID+"'>"+
						
						 "<div class='row' style='margin-top:15px;'>"+
							
					    	"<div class='col-md-6'>"+
					      
						    	"<div class='col-md-4'>"+
						    	 	"<label for='Name' style='font-size:14px;'>Patient Name(ID)</label>"+
						    	"</div>"+
						    	
						    	"<div class='col-md-8'>"+
						      		"<input type='text' class='form-control' readonly='readonly' value='"+patientName+" ("+patientID+")'  name='' placeholder='Patient Name'>"+
						    	"</div>"+
					    	
					     	"</div>"+
					
					    	"<div class='col-md-6'>"+
					    
					    		"<div class='col-md-4'>"+
					      			"<label for='Name' style='font-size:14px;'>Date</label>"+
					   		 	"</div>"+
					    	
					    		"<div class='col-md-8'>"+
					    			"<input type='text' class='form-control' readonly='readonly' name='visitDate' value='"+visitDate+"' id='single_cal3' placeholder='Visit Date' aria-describedby='inputSuccess2Status3'>"+
					    		"</div>"+
					    
					    	"</div>"+
					    
					    "</div>"+
					    
					    "<div class='row' style='margin-top:15px;'>"+
					    
				    	"<div class='col-md-6'>"+
				      
					    	"<div class='col-md-4'>"+
					    	 	"<label for='Name' style='font-size:14px;'>Age</label>"+
					    	"</div>"+
					    	
					    	"<div class='col-md-8'>"+
					      		"<input type='text'  class='form-control' readonly='readonly' value='"+age+"'  name='age' placeholder='Age'>"+
					    	"</div>"+
				    	
				     	"</div>"+
				
				    	"<div class='col-md-6'>"+
				    
				    		"<div class='col-md-4'>"+
				      			"<label for='Name' style='font-size:14px;'>Gender</label>"+
				   		 	"</div>"+
				    	
				    		"<div class='col-md-8'>"+
				     			"<input type='text' class='form-control'  readonly='readonly' value='"+gender+"'  name='gender' placeholder='Gender'>"+
				    		"</div>"+
				    
				    	"</div>"+
				    	
				    "</div>"+
				    
				    
					    "<div class='row' style='margin-top:15px;'>"+
					    
				    	"<div class='col-md-6 search-container'>"+
				      
					    	"<div class='col-md-4'>"+
					    	 	"<label for='Name' style='font-size:14px;'>Diagnosis*</label>"+
					    	"</div>"+
					    	
					    	"<div class='col-md-8'>"+
					      		"<input type='text' class='form-control' readonly='readonly' name='cancerType' value='"+cancerType+"' />"+ 
					      		
					    	"</div>"+
				    	
				     	"</div>"+
				
				    	"<div class='col-md-6'>"+
				    
				    		"<div class='col-md-4'>"+
				      			"<label for='Name' style='font-size:14px;'>Visit Type</label>"+
				   		 	"</div>"+
				    	
				    		"<div class='col-md-8'>"+
					      		"<input type='text' class='form-control' readonly='readonly' value='"+visitType+"' name='' placeholder='Visit Type'>"+
					    	"</div>"+
				    
				    	  "</div>"+
				    	
				   		 "</div>"+
				    
				    
					    "<div class='row' style='margin-top:15px;'>"+
				    	
				    	"<div class='col-md-12'>"+
				   
				    		"<div class='col-md-3'>"+
				      			"<label for='Name' style='margin-top:10px;font-size:14px;'>Medical Note</label>"+
				    		"</div>"+
				    		
				    		"<div class='col-md-9' style='margin-left:-2px;'>"+
				    			"<input type='text' class='form-control' readonly='readonly' value='"+medicalNotes+"' name='medicalNotes' placeholder='Medical Notes'>"+
				    		"</div>"+
				    	
				    	"</div>"+
				    
			    	"</div>"+
			    	
			    
				    	"<div class='ln_solid'></div>"+
					    
					    "<div class='row' >"+
		                
					    "<div class='col-md-4 col-sm-4' align='left'>"+
					    
						    "<div class='col-md-12'>"+
						      "<label for='On Exam' style='margin-top:10px;font-size:16px;'><b>On Exam</b></label>"+
						    "</div>"+
					    
					    "</div>"+
					
					    "<div class='col-md-4 col-sm-4' align='left' > "+
					    
						    "<div class='col-md-12'>"+
						      "<label for='OD (RE)' style='margin-top:10px;font-size:16px;'><b>OD (RE)</b></label>"+
						    "</div>"+
					    
					    "</div>"+
					
					    "<div class='col-md-4 col-sm-4' align='left'>"+
						    "<div class='col-md-12 col-sm-12'>"+
						      "<label for='OD (RE)' style='margin-top:10px;font-size:16px;'><b>OS (LE)</b></label>"+
						    "</div>"+
					    "</div>"+
					    
					  "</div>"+
				    
				  
					  "<div class='row'>"+
					  
				       "<div class='col-md-4 col-sm-4' align='left'>"+
				      
					  		"<div class='col-md-12 col-sm-12'>"+
						     	"<a style='' data-toggle='collapse' href='#eyeLid' aria-expanded='false' aria-controls='collapseExample'>"+
					  				"<label for='Name' style='margin-top:10px;font-size:16px;cursor: pointer;'><b>Eye Lid</b></label>"+
							  	"</a>"+
							"</div>"+
						
				 	   "</div>"+
					
				       "<div class='col-md-4 col-sm-4' align='left' > "+
				      
				    	  	"<div class='col-md-12 col-sm-12'></div>"+
				    
				       "</div>"+
				
				       "<div class='col-md-4 col-sm-4' align='left'>"+
				      	
				      		"<div class='col-md-12 col-sm-12'></div>"+
				    	
				       "</div>"+
				      
				    "</div>"+
			    
			    
							
						    "<div class='row'>"+
						    
						      	"<div class='col-md-4 col-sm-4' align='left'>"+
						    		
						    		"<div class='col-md-12 col-sm-12'>"+
						      			"<label for='Name' style='margin-top:10px;font-size:14px;'>Upper</label>"+
						    		"</div>"+
						
						    	"</div>"+
						
						    	"<div class='col-md-4 col-sm-4' align='left' >"+ 
						    	
						    		"<div class='col-md-12 col-sm-12'>"+
						  				"<input type='text'  class='form-control' readonly='readonly' name='eyeLidUpperOD' value='"+eyeLidUpperOD+"' placeholder='Upper OD'>"+
						    		"</div>"+
						    
						    	"</div>"+
						
						    	"<div class='col-md-4 col-sm-4' align='left'>"+
						    	
						    		"<div class='col-md-12 col-sm-12'>"+
						        		"<input type='text'  class='form-control' readonly='readonly' name='eyeLidUpperOS' value='"+eyeLidUpperOS+"' placeholder='Upper OS'>"+
						    		"</div>"+
						    	"</div>"+
						    	
						    "</div>"+
					    
						    "<div class='row' style='margin-top:10px;'>"+
						    
					      	"<div class='col-md-4 col-sm-4' align='left'>"+
					      	
					    		"<div class='col-md-12 col-sm-12'>"+
					      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Lower</label>"+
					    		"</div>"+
					
					    	"</div>"+
					
					    	"<div class='col-md-4 col-sm-4' align='left' > "+
					    		
					    		"<div class='col-md-12 col-sm-12'>"+
					  				"<input type='text' class='form-control' readonly='readonly' name='eyeLidLowerOD' value='"+eyeLidLowerOD+"' placeholder='Lower OD'>"+
					    		"</div>"+
					    
					    	"</div>"+
					
					    	"<div class='col-md-4 col-sm-4' align='left'>"+
					    		
					    		"<div class='col-md-12 col-sm-12'>"+
					        		"<input type='text' class='form-control' readonly='readonly' name='eyeLidLowerOS' value='"+eyeLidLowerOS+"' placeholder='Lower OS'>"+
					    		"</div>"+
					    		
					    	"</div>"+
					    	
					    "</div>"+
					    
			    
				    
						    "<div class='row'>"+
						    
					      	"<div class='col-md-4 col-sm-4' align='left'>"+
					      	
					    		"<div class='col-md-12 col-sm-12'>"+
					    		
					      			"<a style='text-decoration:none;' data-toggle='collapse' href='#vision' aria-expanded='false' aria-controls='collapseExample'>"+
				  						"<label for='Name' style=' margin-top:10px;font-size:16px;cursor: pointer;'><b>Vision</b></label>"+
									"</a>"+
								
					    		"</div>"+
					
					    	"</div>"+
					
					    	"<div class='col-md-4 col-sm-4' align='left' >"+
					    		
					    		"<div class='col-md-12 col-sm-12 '>"+
					      
					    		"</div>"+
					    
					    	"</div>"+
					
					    	"<div class='col-md-4 col-sm-4' align='left'>"+
					    		
					    		"<div class='col-md-12 col-sm-12'>"+
					      
					    		"</div>"+
					    	
					    	"</div>"+
					    "</div>"+
					
					   
					    "<div class='row'>"+
						
				      	"<div class='col-md-4 col-sm-4 ' align='left'>"+
				      	
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'></label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
				    		
					    	"<div class='col-md-6'>"+
					  			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Dist</b></label>"+
					    	"</div>"+
					    		
					    	"<div class='col-md-6'>"+
					  			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Near</b></label>"+
					    	"</div>"+
				    
				    	"</div>"+
				
					    "<div class='col-md-4 col-sm-4' align='left'>"+
					     		
					     	"<div class='col-md-6'>"+
					  			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Dist</b></label>"+
					    	"</div>"+
					    	
					    	"<div class='col-md-6'>"+
					  			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Near</b></label>"+
					    	"</div>"+
					    
					    "</div>"+
				   		"</div>"+
				    
				   		
				    	"<div class='row' style='margin-top:15px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Visual Acuity</label>"+
				   			"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4 ' align='left' > "+
				    		
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='visualAcuityDistOD' value='"+visualAcuityDistOD+"'>"+
				    		"</div>"+
				    
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='visualAcuityNearOD' value='"+visualAcuityNearOD+"'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				   				
				   			"<div class='col-md-6'>"+
				   				"<input type='text' class='form-control' readonly='readonly' name='visualAcuityDistOS' value='"+visualAcuityDistOS+"'>"+
				    		"</div>"+
				    
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='visualAcuityNearOS' value='"+visualAcuityNearOS+"'>"+
				    		"</div>"+
				    	
				    	"</div>"+
				    	
				    "</div>"+
				    
				 
					    "<div class='row' style='margin-top:10px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    			
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Pinhole Vision</label>"+
				   			"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
	
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='pinholeVisionDistOD' value='"+pinholeVisionDistOD+"'>"+
						    "</div>"+
				    
						    "<div class='col-md-6'>"+
						    	"<input type='text' class='form-control'readonly='readonly'  name='pinholeVisionNearOD' value='"+pinholeVisionNearOD+"'>"+ 
				    		"</div>"+
				    
				    	"</div>"+
				
					    "<div class='col-md-4 col-sm-4' align='left'>"+
					   
					   		"<div class='col-md-6'>"+
					   			"<input type='text' class='form-control' readonly='readonly' name='pinholeVisionDistOS' value='"+pinholeVisionDistOS+"'>"+ 
					    	"</div>"+
					    
					    	"<div class='col-md-6'>"+
					    		"<input type='text' class='form-control' readonly='readonly' name='pinholeVisionNearOS' value='"+pinholeVisionDistOS+"'>"+  
					    	"</div>"+
					    
					    "</div>"+
					    
				    "</div>"+
			    
				  				    
					    "<div class='row' style='margin-top:10px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style='margin-top:10px;font-size:14px;'>BCVA</label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' >"+
				    		
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='BCVADistOD' value='"+BCVADistOD+"'>"+
				    		"</div>"+
				    
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='BCVANearOD' value='"+BCVANearOD+"'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				   			
				   			"<div class='col-md-6'>"+
				   				"<input type='text' class='form-control' readonly='readonly' name='BCVADistOS' value='"+BCVADistOS+"'>"+ 
				    		"</div>"+
				    
				    		"<div class='col-md-6'>"+
				    			"<input type='text' class='form-control' readonly='readonly' name='BCVANearOS' value='"+BCVANearOS+"'>"+ 
						    "</div>"+
				    	
				    	"</div>"+
				    	
				    "</div>"+
				    
				    "</div>"+
				    
				    
				    
					    "<div class='row'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12' >"+
				      			"<a style='' data-toggle='collapse' href='#anteriorSeg' aria-expanded='false' aria-controls='collapseExample'>"+
			  						"<label for='Name' style=' margin-top:10px;font-size:16px;cursor: pointer;'><b>Anterior Segment</b></label>"+
			  					"</a>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
				    		
				    		"<div class='col-md-12 col-sm-12'></div>"+
				    
				    	"</div>"+
				
				    		"<div class='col-md-4 col-sm-4' align='left'>"+
	
							    "<div class='col-md-12 col-sm-12'></div>"+
				    	"</div>"+
				    	
				    "</div>"+
							
			    
				    
				    "<div class='row'>"+
				      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Conjunctiva</label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' >"+ 
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				  				"<input type='text' class='form-control' readonly='readonly' name='conjunctivaOD' value='"+conjunctivaOD+"' placeholder='Conjunctiva OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly' name='conjunctivaOS' value='"+conjunctivaOS+"' placeholder='Conjunctiva OS'>"+
						    "</div>"+
	
					    "</div>"+
	
				      "</div>"+
				    
				    
					    "<div class='row' style='margin-top:10px;'>"+
						 
					 	"<div class='col-md-4 col-sm-4' align='left'>"+
							"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Sclera</label>"+
				    		"</div>"+						 	
					 	 "</div>"+
					 	 
					 	 "<div class='col-md-4 col-sm-4' align='left' >"+ 
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				  				"<input type='text' class='form-control' readonly='readonly' name='scleraOD' value='"+scleraOD+"' placeholder='Sclera OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly' name='scleraOS' value='"+scleraOS+"' placeholder='Sclera OS'>"+
						    "</div>"+
	
					    "</div>"+
			    		"</div>"+

			    	
				    	"<div class='row' style='margin-top:10px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Cornea</label>"+
				    		"</div>"+
				
					    "</div>"+
				
					    "<div class='col-md-4 col-sm-4' align='left' >"+
	
						    "<div class='col-md-12 col-sm-12'>"+
							  "<input type='text' class='form-control' readonly='readonly' name='CorneaOD' value='"+CorneaOD+"' placeholder='Cornea OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly' name='CorneaOS' value='"+CorneaOS+"' placeholder='Cornea OS'>"+
				    		"</div>"+
				    
				    	"</div>"+
				    
				    	"</div>"+
			    
				    	
				    	"<div class='row' style='margin-top:10px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    	
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>AC</label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' >"+ 
				    	
				    		"<div class='col-md-12 col-sm-12'>"+
				  				"<input type='text' class='form-control' readonly='readonly' name='ACOD' value='"+ACOD+"' placeholder='AC OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly'  name='ACOS' value='"+ACOS+"' placeholder='AC OS'>"+
				    		"</div>"+
				    	
				    	"</div>"+
				    
				    	"</div>"+
				    	
				    	"<div class='row' style='margin-top:10px;'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Iris</label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
				    	
				    		"<div class='col-md-12 col-sm-12'>"+
				  				"<input type='text' class='form-control' readonly='readonly' name='irisOD' value='"+irisOD+"' placeholder='Iris OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    	
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly' name='irisOS' value='"+irisOS+"' placeholder='Iris OS'>"+
				    		"</div>"+
				    	
				    	"</div>"+

				   		 "</div>"+
				   		 
				   		 
				   		"<div class='row' style='margin-top:10px;'>"+
					      
					      	"<div class='col-md-4 col-sm-4' align='left'>"+
					    		
					    		"<div class='col-md-12 col-sm-12'>"+
							      "<label for='Name' style=' margin-top:10px;font-size:14px;'>Lens</label>"+
							    "</div>"+
					
						    "</div>"+
					
						    "<div class='col-md-4 col-sm-4' align='left' >"+ 
							
							    "<div class='col-md-12 col-sm-12'>"+
					  				"<input type='text' class='form-control' readonly='readonly' name='lensOD' value='"+lensOD+"' placeholder='Lens OD'>"+
					    		"</div>"+
					    
					    	"</div>"+
					
					    	"<div class='col-md-4 col-sm-4' align='left'>"+
					    		
					    		"<div class='col-md-12 col-sm-12'>"+
					        		"<input type='text' class='form-control' readonly='readonly' name='lensOS' value='"+lensOS+"' placeholder='Lens OS'>"+
							    "</div>"+
	
						    "</div>"+
		
					    	"</div>"+
				 
				    
				    
					    "<div class='row'>"+
					      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<a style='' data-toggle='collapse' href='#posteriorSeg' aria-expanded='false' aria-controls='collapseExample'>"+
			  						"<label for='Name' style=' margin-top:10px;font-size:16px;cursor: pointer;'><b>Posterior Segment</b></label>"+
			  					"</a>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
				    		
				    		"<div class='col-md-12 col-sm-12'></div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    
				    		"<div class='col-md-12 col-sm-12'></div>"+
				    	
				    	"</div>"+
				    
				    "</div>"+
				    
				    
				    
				    
				    "<div class='col-md-12 col-sm-4'>"+
				    				
				    	"<div class='col-md-6 col-sm-4'>"+
				    		
				    		"<div class='col-md-6 col-sm-6'>"+
				      			"<img src='images/posterio_segment.png' alt='Posterior Segment OD'>"+
				    		"</div>"+
				    	
				    		"<div class='col-md-5 col-sm-6' style='margin-left:10px;'>"+
							    "<input type='text' class='form-control' readonly='readonly' name='discOD' value='"+discOD+"' placeholder='Disc OD'>"+
							    "<input type='text' class='form-control' readonly='readonly' style='margin-top:10px;' name='vesselOD' value='"+vesselOD+"' placeholder='Vessel OD'>"+
							    "<input type='text' class='form-control' readonly='readonly' style='margin-top:10px;' name='maculaOD' value='"+maculaOD+"' placeholder='Macula OD'>"+
				    		"</div>"+
				    	
				    	"</div>"+
				
				    	"<div class='col-md-6 col-sm-4'>"+
				    		
				    		"<div class='col-md-6 col-sm-6'>"+
				        		"<img src='images/posterio_segment_1.png' alt='Posterior Segment OS'>"+
				    		"</div>"+
				     
				     		"<div class='col-md-5 col-sm-6' style='margin-left:10px;'>"+
							    "<input type='text' class='form-control' readonly='readonly' name='discOS' value='"+discOS+"' placeholder='Disc OS'>"+
							    "<input type='text' class='form-control' readonly='readonly' style='margin-top:10px;' name='vesselOS' value='"+vesselOS+"' placeholder='Vessel OS'>"+
							    "<input type='text' class='form-control' readonly='readonly' style='margin-top:10px;'  name='maculaOS' value='"+maculaOS+"' placeholder='Macula OS'>"+
				    		"</div>"+
				    	"</div>"+
				    
					    "</div>"+
				    
				    	
				    	"<div class='col-md-10 col-sm-8' style='margin-top: 10px; margin-left: 59px;'>"+
				    	 	"<input type='text' class='form-control' name='posteriorComment' readonly='readonly' placeholder='Comment' value='"+posteriorComment+"'>"+
				    	"</div>"+
				     
				      
				      
				      "<div class='row'>"+
				      
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<a style='' data-toggle='collapse' href='#biometric' aria-expanded='false' aria-controls='collapseExample'>"+
			  						"<label for='Name' style=' margin-top:10px;font-size:16px;cursor: pointer;'><b>I.O.P, Sac. & Biometry</b></label></a>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' >"+
				    		
				    		"<div class='col-md-12 col-sm-12'></div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'></div>"+
				    	"</div>"+
				    	
				    "</div>"+
				    
				    
				   
				    "<div class='row'>"+
				      	
				      	"<div class='col-md-4 col-sm-4' align='left'>"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				      			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>I.O.P</b></label>"+
				    		"</div>"+
				
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' > "+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				  				"<input type='text' class='form-control' readonly='readonly' name='IODOD' value='"+IODOD+"' placeholder='I.O.D OD'>"+
				    		"</div>"+
				    
				    	"</div>"+
				
				    	"<div class='col-md-4 col-sm-4' align='left' >"+
				    		
				    		"<div class='col-md-12 col-sm-12'>"+
				        		"<input type='text' class='form-control' readonly='readonly' name='IODOS' value='"+IODOS+"' placeholder='I.O.D OS'>"+
				    		"</div>"+
				    	
				    	"</div>"+
				    
				    "</div>"+
				    
				    "<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Sac</b></label>"+
			    		"</div>"+
			
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left' > "+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			  				"<input type='text' class='form-control' readonly='readonly' name='sacOD' value='"+sacOD+"' placeholder='Sac OD'>"+
			    		"</div>"+
			    
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12' >"+
			        		"<input type='text' class='form-control' readonly='readonly' name='sacOS' value='"+sacOS+"' placeholder='Sac OS'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			    
			    	"</div>"+
			    	
			    	"<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'><b>Biometry</b></label>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left' >"+ 
			    		
			    		"<div class='col-md-12 col-sm-12'></div>"+
			    
				    "</div>"+
			
				    "<div class='col-md-4 col-sm-4' align='left'>"+
			
					    "<div class='col-md-12 col-sm-12'></div>"+

				    "</div>"+

			    	"</div>"+
			    	
			    	
			    	"<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>K1</label>"+
			    		"</div>"+
			      
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left' >"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<input type='text'  class='form-control' readonly='readonly' name='k1OD' value='"+k1OD+"' placeholder='K1 OD'>"+
			    		"</div>"+
			     
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			         		"<input type='text'  class='form-control' readonly='readonly' name='k1OS' value='"+k1OS+"' placeholder='K1 OS'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			    
			    	"</div>"+
			
			    	
			    	"<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>K2</label>"+
			    		"</div>"+
			     
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left' >"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			       			"<input type='text'   class='form-control' readonly='readonly' name='k2OD' value='"+k2OD+"' placeholder='K2 OD'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
				    "<div class='col-md-4 col-sm-4' align='left'>"+

					    "<div class='col-md-12 col-sm-12'>"+
					        "<input type='text'   class='form-control' readonly='readonly' name='k2OS' value='"+k2OS+"' placeholder='K2 OS'>"+
					    "</div>"+
			    	
			    	"</div>"+
			    
			    	"</div>"+
			    	
			    	"<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>Axial Length</label>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			        		"<input type='text' class='form-control' readonly='readonly' name='axialLengthOD' value='"+axialLengthOD+"' placeholder='Axial Length OD'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			        		"<input type='text'  class='form-control' readonly='readonly' name='axialLengthOS' value='"+axialLengthOS+"' placeholder='Axial Length OS'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			    
			    	"</div>"+
			    	
			    	
			    	"<div class='row' style='margin-top:10px;'>"+
			      	
			      	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			      			"<label for='Name' style=' margin-top:10px;font-size:14px;'>IOL</label>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left' >"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			        		"<input type='text' class='form-control' readonly='readonly'  name='IOLOD' value='"+IOLOD+"' placeholder='IOL OD'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			
			    	"<div class='col-md-4 col-sm-4' align='left'>"+
			    		
			    		"<div class='col-md-12 col-sm-12'>"+
			         		"<input type='text'  class='form-control' readonly='readonly' name='IOLOS' value='"+IOLOS+"' placeholder='IOL OS'>"+
			    		"</div>"+
			    	
			    	"</div>"+
			    
			    	"</div>"+
			    	
				   
				    
				    "<div class='row' style='margin-top:10px;'>"+
			      	
				    "<div class='col-md-4 col-sm-4' align='left'>"+
				    	"<div class='col-md-12 col-sm-12'>"+
				      		"<label for='Name' style='margin-top:10px;font-size:16px;'><b>Additional Comments</b></label>"+
				    	"</div>"+
				    "</div>"+
				
				    "<div class='col-md-8 col-sm-8' align='left' >"+
				    	"<div class='col-md-12 col-sm-12' style='margin-top:10px;'>"+
				    		"<input type='text' class='form-control' name='biometryComment' readonly='readonly' placeholder='Comment' value='"+biometryComment+"'>"+
				    	"</div>"+
				    "</div>"+
					"</div>"+
			
				"</tr>";
						
						
						if(check == 1){
							
							document.getElementById("successMSG").innerHTML = "CRF Details displayed successfully.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
							
					
						}else{
							
							document.getElementById("exceptionMSG").innerHTML = "Failed to display CRF Details. Please check server logs for more details.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
						}
					
					}
					
				}
					
					
			$('#viewCRFModal').modal('show');
					

				}
			};
			xmlhttp.open("GET", "ViewCRF?visitID="+visitID+"&formName="+formName+"&visitTypeID="+visitTypeID+"&apptID="+apptID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->

<!-- VIEW PRESC -->
<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function viewPRESC1(visitID, formName, visitTypeID, apptID) {
	console.log("inside ajax method..");
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var trID = 0; var trTag=""; var check = 0; var patientID = 0; var cancerType =""; var advice = ""; var nextVisitDays = ""; var investigation = "";
					var tradeName = ""; var frequency =""; var numberOfDays = ""; var productQuantity = ""; var comment = "";
					 
					for ( var i = 0; i < array.Release1.length; i++) {
						
						check = array.Release1[i].check;
						console.log("Check::"+check);
						
						patientID = array.Release1[i].patientID;
						cancerType = array.Release1[i].cancerType;
						advice = array.Release1[i].advice;
						if(typeof advice === "undefined" || advice == null){
							advice = "";
						}else{
							advice = advice;
						}
						
						investigation = array.Release1[i].investigation;
						if(typeof investigation === "undefined" || investigation == null){
							investigation = "";
						}else{
							investigation = investigation;
						}
						
						tradeName = array.Release1[i].tradeName;
						if(typeof tradeName === "undefined" || tradeName == null){
							tradeName = "";
						}else{
							tradeName = tradeName;
						}
						
						frequency = array.Release1[i].frequency;
						if(typeof frequency === "undefined" || frequency == null){
							frequency = "";
						}else{
							frequency = frequency;
						}
						
						numberOfDays = array.Release1[i].numberOfDays;
						if(typeof numberOfDays === "undefined"  || numberOfDays == null){
							numberOfDays = "";
						}else{
							numberOfDays = numberOfDays;
						}
						
						productQuantity = array.Release1[i].productQuantity;
						if(typeof productQuantity === "undefined" || productQuantity == null){
							productQuantity = "";
						}else{
							productQuantity = productQuantity;
						}
						
						comment = array.Release1[i].comment;
						if(typeof comment === "undefined" || comment == null){
							comment = "";
						}else{
							comment = comment;
						}
						
						nextVisitDays = array.Release1[i].nextVisitDays;
						if(typeof nextVisitDays === "undefined" || nextVisitDays == null){
							nextVisitDays = "";
						}else{
							nextVisitDays = nextVisitDays;
						}
						
						
						console.log("cancerType:"+cancerType+" advice:"+advice+" nextVisitDays:"+nextVisitDays+" investigation:"+investigation+" tradeName:"+tradeName);
						console.log("frequency:"+frequency+" numberOfDays:"+numberOfDays+" productQuantity:"+productQuantity+" comment:"+comment);
						
						document.getElementById("PRESCDetailsID").innerHTML ="<tr id='visitTRIDD"+patientID+"'>"+
							"<div class='row' style='margin-top:10px;margin-left:0px;margin-bottom:5px;'>"+
							 
							"<div class='row' style='margin-top:20px;'>"+
						 		"<div class='col-md-5 col-xs-12 col-sm-6' style='margin-left:15px;'>"+
						 			"<h4><b>Diagnosis: </b>"+cancerType+"</h4>"+
						 		"</div>"+
							  
								"<div class='col-md-2 col-sm-1 col-xs-12' style='margin-left:15px;' >"+
							  		"<h4><b>Advice: </b></h4>"+
							  	"</div>"+
							  	"<div class='col-md-4 col-sm-4 col-xs-10' >"+
							  		"<input type='text' name='advice' id='adviceID' class='form-control' readonly='readonly' placeholder='Advice' value='"+advice+"'>"+
							    "</div>"+
							"</div>"+
							
							"<h4 style='margin-top:20px;margin-left:15px;'><b>Investigation</b></h4>"+
							
						      "<div class='row' style='margin-left:0px;margin-right:0px;margin-top:15px;'>"+
						      
						            "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' style='width: 29%; margin-left: 2%;' >"+
							            "<thead>"+
											"<tr>"+
							                   "<th style='width: 80%'>Test Name</th>"+
							                "</tr>"+
							            "</thead>"+
							            "<tbody>"+
						                      "<td style='font-size: 14px;text-align: center;' readonly='readonly'>"+investigation+"</td>"+
						                               	
							             "</tbody>"+
							         "</table>"+
						          "</div>"+
						          
							"</div>"+
							
							
							"<div class='row' style='margin-top:10px;margin-left:0px;margin-bottom:5px;'>"+
							
						      "<h4 style='margin-top:20px;margin-left:15px;'><b>Prescription</b></h4>"+
						
						      "<div class='row' id='OPDPResc' style='margin-left:0px;margin-right:0px;margin-top:15px;'>"+
						      
						            "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%' style='width: 97%; margin-left: 1%;'>"+
							                            "<thead>"+
															"<tr>"+
							                                    "<th style='width: 25%'>Drug Name</th>"+
							                                    "<th style='width: 17%'>Frequency</th>"+
							                                    "<th style='width: 10%'>No. of days</th>"+
							                                    "<th style='width: 9%'>Quantity</th>"+
							                                    "<th style='width: 31%'>Comments</th>"+
							                                "</tr>"+
							                             "</thead>"+
							                               
							                            "<tbody>"+
							                            	
							                            			"<td style='font-size: 14px;text-align: center;' readonly='readonly'>"+tradeName+"</td>"+
							                            			
							                            			"<td style='font-size: 14px;text-align: center;' readonly='readonly' >"+frequency+"</td>"+
							                            			
							                            			"<td style='font-size: 14px;text-align: center;' readonly='readonly'>"+numberOfDays+"</td>"+
							                            			
							                            			"<td style='font-size: 14px;text-align: center;' readonly='readonly'>"+productQuantity+"</td>"+
							                            			
							                              			"<td style='font-size: 14px;text-align: center;' readonly='readonly'>"+comment+"</td>"+
							                            "</tbody>"+
							                        "</table>"+
						            "</div>"+
						         "</div>"+
						         
						         "<div class='row' style='margin-top:50px;'>"+ 
							      "<div class='col-md-2 col-sm-3'></div>"+
							      "<div class='col-md-4 col-xs-6 col-sm-3'>"+
		                          	  "<label class='control-label' for='Date' style='padding-left:50px;'>Follow-up after</label>"+
		                          "</div>"+
		                          "<div class='col-md-2 col-xs-3 col-sm-2' >"+
		                            "<input type='number' class='form-control' name='nextVisitDays'  readonly='readonly' value= '"+nextVisitDays+"' id='nextVisitDaysID' >"+
		                          "</div>"+
		                          "<div class='col-md-1'>"+
		                          	  "<label class='control-label' style='text-align: left;' for='Date'>days.</label>"+
		                          "</div>"+
		                          "<div class='col-md-3'></div>"+
		                      "</div>"+
		                      "</tr>";
		                      
		                      if(check == 1){
									
									document.getElementById("successMSG").innerHTML = "Prescription Details displayed successfully.";
									
									setTimeout(function(){
										$("#successErrorMsgID").hide(1000);
									},2000);
									
							
								}else{
									
									document.getElementById("exceptionMSG").innerHTML = "Failed to display Prescription Details. Please check server logs for more details.";
									
									setTimeout(function(){
										$("#successErrorMsgID").hide(1000);
									},2000);
								}
							}
						$('#viewPRESCModal').modal('show');
									

						}
					};
					xmlhttp.open("GET", "ViewPRESC?visitID="+visitID+"&formName="+formName+"&visitTypeID="+visitTypeID+"&apptID="+apptID, true);
					xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
					xmlhttp.send();
				}
		</script>

<!-- Ends -->	
	
	
<!-- VIEW BILL -->
<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function viewBILL1(visitID) {
	console.log("inside ajax method..");
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var trID = 0; var trTag=""; var check = 0; var patientID = 0; var visitType =""; var receiptDate = ""; var receiptNo = ""; var cancerType = "";
					var charges = ""; var totalBill =""; var advPayment = ""; var balPayment = ""; var cashPaid = ""; var cashToReturn = ""; var chequeIssueBy = "";
					var chequeNumber=""; var bankName = ""; var bankBranch = ""; var chequeDate = ""; var chequeAmount = ""; var cardAmount = ""; var cardNumber = ""; 
					var mobile = ""; var creditNote = ""; var otherMode = ""; var otherAmount = ""; var paymentType = "";
					
					for ( var i = 0; i < array.Release1.length; i++) {
						
						check = array.Release1[i].check;
						console.log("Check::"+check);
						
						patientID = array.Release1[i].patientID;
						receiptDate = array.Release1[i].receiptDate;
						if(typeof receiptDate === "undefined" || receiptDate ==  null){
							receiptDate = "";
						}else{
							receiptDate = receiptDate;
						}
						
						receiptNo = array.Release1[i].receiptNo;
						if(typeof receiptNo === "undefined" || receiptNo == null){
							receiptNo = "";
						}else{
							receiptNo = receiptNo;
						}
						
						cancerType = array.Release1[i].cancerType;
						if(typeof cancerType === "undefined" || cancerType == null){
							cancerType = "";
						}else{
							cancerType = cancerType;
						}
						
						visitType = array.Release1[i].visitType;
						if(typeof visitType === "undefined" || visitType == null){
							visitType = "";
						}else{
							visitType = visitType;
						}
						
						charges = array.Release1[i].charges;
						if(typeof charges === "undefined" || charges == null){
							charges = "";
						}else{
							charges = charges;
						}
						
						totalBill = array.Release1[i].totalBill;
						if(typeof totalBill === "undefined" || totalBill == null){
							totalBill = "";
						}else{
							totalBill = totalBill;
						}
						
						advPayment = array.Release1[i].advPayment;
						if(typeof advPayment === "undefined" || advPayment == null){
							advPayment = "";
						}else{
							advPayment = advPayment;
						}
						
						balPayment = array.Release1[i].balPayment;
						if(typeof balPayment === "undefined" || balPayment == null){
							balPayment = "";
						}else{
							balPayment = balPayment;
						}
						
						paymentType = array.Release1[i].paymentType;
						if(typeof paymentType === "undefined" || paymentType == null){
							paymentType = "";
						}else{
							paymentType = paymentType;
						}
						
						cashPaid =  array.Release1[i].cashPaid;
						if(typeof cashPaid === "undefined" || cashPaid == null){
							cashPaid = "";
						}else{
							cashPaid = cashPaid;
						}
						
						cashToReturn =  array.Release1[i].cashToReturn;
						if(typeof cashToReturn === "undefined" || cashToReturn == null){
							cashToReturn = "";
						}else{
							cashToReturn = cashToReturn;
						}
						
						chequeIssueBy = array.Release1[i].chequeIssueBy;
						if(typeof chequeIssueBy === "undefined" || chequeIssueBy == null){
							chequeIssueBy = "";
						}else{
							chequeIssueBy = chequeIssueBy;
						}
						
						chequeNumber = array.Release1[i].chequeNumber;
						if(typeof chequeNumber === "undefined" || chequeNumber == null){
							chequeNumber = "";
						}else{
							chequeNumber = chequeNumber;
						}
						
						bankName = array.Release1[i].bankName;
						if(typeof bankName === "undefined" || bankName == null){
							bankName = "";
						}else{
							bankName = bankName;
						}
						
						bankBranch = array.Release1[i].bankBranch;
						if(typeof bankBranch === "undefined" || bankBranch == null){
							bankBranch = "";
						}else{
							bankBranch = bankBranch;
						}
						
						chequeDate = array.Release1[i].chequeDate;
						if(typeof chequeDate === "undefined" || chequeDate == null){
							chequeDate = "";
						}else{
							chequeDate = chequeDate;
						}
						
						chequeAmount = array.Release1[i].chequeAmount;
						if(typeof chequeAmount === "undefined" || chequeAmount == null){
							chequeAmount = "";
						}else{
							chequeAmount = chequeAmount;
						}
						
						cardAmount =  array.Release1[i].cardAmount;
						if(typeof cardAmount === "undefined" || cardAmount == null){
							cardAmount = "";
						}else{
							cardAmount = cardAmount;
						}
						
						cardNumber =  array.Release1[i].cardNumber;
						if(typeof cardNumber === "undefined" || cardNumber == null){
							cardNumber = "";
						}else{
							cardNumber = cardNumber;
						}
						
						mobile = array.Release1[i].mobile;
						if(typeof mobile === "undefined" || mobile == null){
							mobile = "";
						}else{
							mobile = mobile;
						}
						
						creditNote = array.Release1[i].creditNote;
						if(typeof creditNote === "undefined" || creditNote == null){
							creditNote = "";
						}else{
							creditNote = creditNote;
						}
						
						otherMode =  array.Release1[i].otherMode;
						if(typeof otherMode === "undefined" || otherMode == null){
							otherMode = "";
						}else{
							otherMode = otherMode;
						}
						
						otherAmount =  array.Release1[i].otherAmount;
						if(typeof otherAmount === "undefined" || otherAmount == null){
							otherAmount = "";
						}else{
							otherAmount = otherAmount;
						}
						
						
						console.log("payment type:"+paymentType);
						
					 array_element ="<tr id='visitTRIDD"+patientID+"'>"+
							"<div class='row' style='margin-top: 15px;'>"+
							
							"<div class='col-md-3'>"+
								"<font style='font-size: 14px;'>Receipt Date:</font>"+
							"</div>"+
							"<div class='col-md-3'>"+
								"<font style='font-size: 14px;font-weight: bold;'>"+receiptDate+"</font>"+
								"<input type='hidden' name='receiptDate' value='"+receiptDate+"'>"+
							"</div>"+
							"<div class='col-md-3 col-xs-4 col-sm-2'>"+
								"<font style='font-size: 14px;  margin-left:12px'>Receipt No:</font>"+
							"</div>"+
							"<div class='col-md-3'>"+
								"<font style='font-size: 14px;font-weight: bold;'>"+receiptNo+"</font>"+
								"<input type='hidden' name='receiptNo' value='"+receiptNo+"'>"+
							"</div>"+
							"</div>"+
							
							"<div class='row' style='margin-top: 15px;'>"+
							
							"<div class='col-md-3 col-xs-4'>"+
								"<font style='font-size: 14px; margin-left:20px;'>Diagnosis</font>"+
							"</div>"+
							"<div class='col-md-3'>"+
								"<font style='font-size: 14px;font-weight: bold;'>"+cancerType+"</font>"+
								"<input type='hidden' name='visitDate' value='"+cancerType+"'>"+
							"</div>"+
							"<div class='col-md-3 col-xs-12 col-sm-2'>"+
								"<font style='font-size: 14px; margin-left:20px;'>Visit Type:</font>"+
							"</div>"+
							"<div class='col-md-3 col-xs-11 col-sm-4'>"+
								"<input type='text' class='form-control' readonly='readonly' value='"+visitType+"'>"+
							"</div>"+
							"</div>"+
						
							"<div class='item form-group col-md-12' style='margin-top:15px;'>"+
	                        "<label for='User Type' class='control-label col-md-4 col-xs-5'>Charges(Rs)</label>"+
	                        "<div class='col-md-6 col-sm-6 col-xs-6'>"+
	                          "<input type='text' class='form-control' readonly='readonly' name='charges' id='charges' value='"+charges+"'  placeholder='Charges(Rs)'>"+
	                        "</div>"+
	                       "</div>"+
	                      
	                      "<div class='item form-group  col-md-12'>"+
	                        "<label for='User Type' class='control-label col-md-4 col-xs-5'>Total Bill(Rs)</label>"+
	                        "<div class='col-md-6 col-sm-6 col-xs-6'>"+
	                          "<input type='text' class='form-control'  name='totalBill' id='totalBill' value='"+totalBill+"' readonly='readonly' placeholder='Total Bill(Rs)'>"+
	                        "</div>"+
	                      "</div>"+
	                      
	                      "<div class='item form-group col-md-12'>"+
	                        "<label for='User Type' class='control-label col-md-4 col-xs-5'>Advance Payment</label>"+
	                        "<div class='col-md-6 col-sm-6 col-xs-6'>"+
	                          "<input type='number' class='form-control' readonly='readonly' name='advPayment' id='advPaymentID' value = '"+advPayment+"' >"+
	                        "</div>"+
	                      "</div>"+
	                      
	                      "<div class='item form-group col-md-12'>"+
	                        "<label for='User Type' class='control-label col-md-4 col-xs-5'>Balance Payment</label>"+
	                        "<div class='col-md-6 col-sm-6 col-xs-6'>"+
	                         "<input type='number' class='form-control' name='balPayment' readonly='readonly' value = '"+balPayment+"' id='balPaymentID'>"+
	                        "</div>"+
	                      "</div>"+
	                      
	                      
	                      "<div class='row' style='padding: 15px 15px 0 15px;'>"+
							"<div class='col-md-3 col-sm-3 col-xs-5' style='padding-top: 5px;'>"+
								"<font style='font-size: 16px;'>Payment Type*</font>"+
							"</div>";
							
								if(paymentType == "Cash"){
	                	 			array_element += "<div class='col-md-9 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
	                	 								"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cash' style='height: 25px; box-shadow: none;' checked='checked'>Cash</label>"+
	                	 							
														"</div>"+
														
														"<div class='col-md-4 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														    "<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' value='Cheque' style='height: 25px; box-shadow: none;'>Cheque</label>"+
															
														"</div>"+
														
													"</div>"+
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4'>"+
															"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit/Debit Card' >Credit/Debit Card</label>"+
														"</div>"+
														
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4'>"+
															"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit Note' >Credit Note</label>"+
														"</div>"+
														
													"</div>"+
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4'>"+
															"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Other' >Other</label>"+
														"</div>"+
														
													"</div>"+
													"</div>"+
													
	                	 			"<div id='cashDetailID' style='padding: 15px; >"+
	    							
	    							"<div class='ln_solid'></div>"+
	    							"<div class='row'>"+
	    								"<div class='col-md-2 col-xs-6' style='padding-top:3%; margin-left:40px;'>"+
	    									"<font style='font-size: 16px;'>Cash Paid</font>"+
	    								"</div>"+
	    								"<div class='col-md-2 col-xs-4' style='padding-top:3%;'>"+
	    									"<input type='text' class='form-control' name='cashPaid' readonly='readonly' id='cashPaidID' onkeyup='changeCashToReturn(\""+cashPaid+"\", \""+totalBill+"\");' value='"+cashPaid+"' placeholder='Cash Paid'>"+
	    								"</div>"+
	    								"<div class='col-md-3 col-xs-6' style='padding-top:3%;'>"+
	    									"<font style='font-size: 16px;'>Cash To Return</font>"+
	    								"</div>"+
	    								"<div class='col-md-2 col-xs-4' style='padding-top:3%;'>"+
	    									"<input class='form-control' name='cashToReturn' id='cashToReturnID' readonly='readonly' value='"+cashToReturn+"' placeholder='Cash To Return' type='number'>"+
	    								"</div>"+
	    							"</div>"+
	    							"</div>";
	                	 			
	                	 			
								}else if(paymentType == "Cheque"){
	    							array_element += "<div class='col-md-9 col-sm-3 col-xs-12'>"+
	    												"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-top:5px;'>"+
	    												"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cash' style='height: 20px; box-shadow: none;'>Cash</label>"+
	    											"</div>"+
	    								
	    											"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4' >"+
	    												"<label class='radio'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' value='Cheque' style='height: 25px; box-shadow: none;' checked='checked'>Cheque</label>"+
					                	 			
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-top:5px;'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit/Debit Card' >Credit/Debit Card</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit Note' >Credit Note</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Other' >Other</label>"+
													"</div>"+
													"</div>"+
													"</div>"+
													
												
												"<div class='row'>"+	
													"<div class='col-md-12 ' style='margin-top: 15px;'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Cheque Issued By</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' id='chequeIssuedByID' readonly='readonly' name='chequeIssuedBy' value='"+chequeIssueBy+"' placeholder='Cheque Issued By'>"+
														"</div>"+
														"<div class='col-md-2 col-xs-6'>"+
															"<font style='font-size: 16px;'>Cheque No.</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input class='form-control' name='chequeNo' id='chequeNoID' readonly='readonly' value='"+chequeNumber+"' placeholder='Cheque No.' type='text'>"+
														"</div>"+
													"</div>"+
													"<div class='col-md-12' style='margin-top: 15px;'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Bank Name</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' id='chequeBankNameID' readonly='readonly' value='"+bankName+"' name='chequeBankName' placeholder='Bank Name'>"+
														"</div>"+
														"<div class='col-md-2 col-xs-6'>"+
															"<font style='font-size: 16px;'>Branch</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input class='form-control' name='chequeBankBranch' id='chequeBankBranchID'  readonly='readonly' value='"+bankBranch+"' placeholder='Branch' type='text'>"+
														"</div>"+
													"</div>"+
													"<div class='col-md-12' style='margin-top: 15px;'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Date</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' name='chequeDate' readonly='readonly' value='"+chequeDate+"' id='chequeDateID' placeholder='Date'>"+
														"</div>"+
														"<div class='col-md-2 col-xs-6'>"+
															"<font style='font-size: 16px;'>Amount</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input class='form-control' name='chequeAmt' id='chequeAmtID' readonly='readonly' value='"+chequeAmount+"' placeholder='Amount' type='number'>"+
														"</div>"+
													"</div>"+
													
													"</div>";
	    						
								}else if (paymentType == "Credit/Debit Card"){
	    							array_element += "<div class='col-md-9 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-left:20px; padding-top:10px;'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cash' style='height: 20px; box-shadow: none;'>Cash</label>"+
													"</div>"+
										
													"<div class='col-md-5 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-top:5px;'>"+
														"<label class='radio'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cheque' style='height: 20px; box-shadow: none;'>Cheque</label>"+
					                	 			
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit/Debit Card'  checked='checked'>Credit/Debit Card</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled'  class='checkboxClass form-control'name='paymentType' style='height: 25px; box-shadow: none;' value='Credit Note' >Credit Note</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Other' >Other</label>"+
													"</div>"+
													"</div>"+
													"</div>"+
								
	    								
													
													"<div class='col-md-12'></div>"+
													"<div class='row'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Amount</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='number' class='form-control' id='cardAmountID' readonly='readonly' value='"+cardAmount+"' name='cardAmount' placeholder='Amount'>"+
														"</div>"+
														"<div class='col-md-2 col-xs-6'>"+
															"<font style='font-size: 16px;'>Card No.</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' id='cardMobileNoID' readonly='readonly' value='"+cardNumber+"' name='cardMobileNo' placeholder='Card No.'>"+
														"</div>"+
													"</div>"+
													"<div class='row' style='margin-top: 15px;'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Mobile No.</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='number' class='form-control' id='cMobileNoID' readonly='readonly' value='"+mobile+"' name='cMobileNo' placeholder='Mobile No.'>"+
														"</div>"+
														"</div>"+
													"</div>";
								
								}else if (paymentType == "Credit Note"){
									array_element +=  "<div class='col-md-9 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-left:20px; padding-top:10px;'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cash' style='height: 20px; box-shadow: none;'>Cash</label>"+
													"</div>"+
										
													"<div class='col-md-5 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label class='radio'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' value='Cheque' style='height: 20px; box-shadow: none;'>Cheque</label>"+
					                	 			
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit/Debit Card'>Credit/Debit Card</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit Note'  checked='checked' >Credit Note</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Other' >Other</label>"+
													"</div>"+
													"</div>"+
													"</div>"+
			
										
													
													"<div class='col-md-12' style='margin-top: 15px;'></div>"+
													"<div class='row'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Credit Balance</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' id='creditNoteBalID' readonly='readonly' name='creditNoteBal' value='"+creditNote+"' placeholder='Credit Note Balance'>"+
														"</div>"+
												
													"</div>";
	    						
								}else if(paymentType == "Other"){
									array_element +=  "<div class='col-md-9 col-sm-3 col-xs-12'>"+
														"<div class='col-md-4 col-sm-4 col-xs-4' style='padding-left:20px; padding-top:10px;'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cash' style='height: 20px; box-shadow: none;'>Cash</label>"+
													"</div>"+
										
													"<div class='col-md-5 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label class='radio'><input type='checkbox'  disabled='disabled'  class='checkboxClass form-control' name='paymentType' value='Cheque' style='height: 20px; box-shadow: none;'>Cheque</label>"+
					                	 			
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control'  name='paymentType' style='height: 25px; box-shadow: none;' value='Credit/Debit Card'>Credit/Debit Card</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox'  disabled='disabled' class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Credit Note'  >Credit Note</label>"+
													"</div>"+
													"</div>"+
													
													"<div class='col-md-4 col-sm-3 col-xs-12'>"+
													"<div class='col-md-4 col-sm-4 col-xs-4'>"+
														"<label for='paymentType'><input type='checkbox' disabled='disabled'  class='checkboxClass form-control' name='paymentType' style='height: 25px; box-shadow: none;' value='Other'  checked='checked'>Other</label>"+
													"</div>"+
													"</div>"+
													"</div>"+

										

													"<div class='ln_solid'></div>"+

													"<div class='col-md-12' style='margin-top: 15px;'>"+
														"<div class='col-md-4 col-xs-6'>"+
															"<font style='font-size: 16px;'>Other Type</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='text' class='form-control' id='otherTypeID' readonly='readonly' name='otherType' value='"+otherMode+"' placeholder='Enter Other payment here'>"+
														"</div>"+
														"<div class='col-md-2 col-xs-6'>"+
															"<font style='font-size: 16px;'>Amount</font>"+
														"</div>"+
														"<div class='col-md-3'>"+
															"<input type='number' class='form-control' id='otherTypeAmountID' readonly='readonly'  name='otherAmount' value='"+otherAmount+"' placeholder='Enter Other amount here'>"+
														"</div>"+
												
													"</div>";
	    						}
	                	 		
	                	 		array_element += "</div>"+
	                	 		
							"</tr>";
						
						document.getElementById("BILLDetailsID").innerHTML = array_element;
							
						if(check == 1){
							
							document.getElementById("successMSG").innerHTML = "Bill Details displayed successfully.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
							
					
						}else{
							
							document.getElementById("exceptionMSG").innerHTML = "Failed to display Bill Details. Please check server logs for more details.";
							
							setTimeout(function(){
								$("#successErrorMsgID").hide(1000);
							},2000);
						}
					}
				$('#viewBILLModal').modal('show');
							

				}
			};
			xmlhttp.open("GET", "ViewBILL?visitID="+visitID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
</script>

<!-- Ends -->	
	
   <style type="text/css">
	   .example::after {
		  content: " ";
		  display: block;
		  position: absolute;
		  height: 1px;
		  background: black;
		  width: 100%;
		  left: 1px;
		  top: calc(45% - -21px);
	   }
	   
	   .exampleNew::after {
		  content: " ";
		  display: block;
		  position: absolute;
		  height: 1px;
		  background: black;
		  width: 100%;
		  left: 1px;
		  top: calc(90% - -65px);
	   }
	   
	   span.required {
	   color: red !important;
	}
	   
   </style>
 
 <body class="nav-md">  
 
   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
<!--  View CRF Modal -->

<div id="viewCRFModal" class="modal fade" role="dialog">
	  <div class="modal-dialog  modal-md">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title" style="text-align:center; padding-bottom:10px;">Clinical Registration Form</h4>
	      </div>
	 <div class="modal-body ">
	        <div class="row" id="CRFDetailsID" >
        		
        	</div>
   
      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	
	  </div>
	</div>
				
<!-- End -->

<!--  View PRESC Modal -->

<div id="viewPRESCModal" class="modal fade" role="dialog" >
	  <div class="modal-dialog modal-md">
      <div class="modal-content">

	      <div class="modal-header">
	        <h4 class="modal-title" style="text-align:center; padding-bottom:10px;">Prescription</h4>
	      </div>
	    
		 <div class="modal-body">
		        <div class="row" id="PRESCDetailsID"></div>
		  </div>
	      <div class="modal-footer">
		        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		   </div>
	  
	    </div>
	
	  </div>
	</div>
				
<!-- End -->
  
  
<!--  View BILL Modal -->

<div id="viewBILLModal" class="modal fade" role="dialog">
	  <div class="modal-dialog  modal-md">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title" style="text-align:center; padding-bottom:10px;">Bill</h4>
	      </div>
	 <div class="modal-body">
	        <div class="row" id="BILLDetailsID">
        		
        	</div>
   
      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	
	  </div>
	</div>
				
<!-- End -->
    
  <!-- Logout modal -->
  
  <div id="logoutModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
<!--       <div class="modal-header" align="center" style="color:black;"> -->
          <h4 class="modal-title" id="myModalLabel">LOGOUT</h4>
      </div>
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;">You will lose any unsaved data. Are you sure you want to log out?</h4>
          </center>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <a href="Logout"><button type="submit" style="width:25%" class="btn btn-success">OK</button></a>
        </center>
        </div>

      </div>
    </div>
  
  
  <!-- Ends -->
  
  <!-- Lock modal -->
  
  <div id="lockModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
       <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="myModalLabel">SCREEN LOCKED</h4>
      </div>
      
        <div class="modal-body">
          
          <center style="margin-bottom: 20px;">
          	<font style="color:black;font-size: 16px;">Enter your PIN to onlock the screen</font>
          </center>
          
          <div class="row">
	          <div class="col-md-10 col-sm-10 col-xs-8">
	          	<input type="password" name="lockPIN" id="lockPIN" onkeyup="checkUnlockPIN(lockPIN.value);" class="form-control" placeholder="Enter your PIN here">
	          </div>
	          <div class="col-md-2 col-sm-2 col-xs-4" id="lockImgID" style="margin-left:-15px;">
	          	
	          </div>
          </div>

        </div>
        <div class="modal-footer">
        <center>
          <a href="Logout?userID=<%=patientID %>"><button type="submit" style="width:25%" class="btn btn-primary">Logout</button></a>
          <button type="button" id="lockButtonID" class="btn btn-primary" data-dismiss="modal" style="display: none;"></button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  <!-- Security Credential modal -->
  
  <div id="securityModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="myModalLabel">CHANGE SECURITY CREDENTIALS</h4>
      </div>
      
        <div class="modal-body">
          
          <!-- start accordion -->
                    <div class="accordion" id="accordion" role="tablist" aria-multiselectable="true">
                      <div class="panel">
                        <a class="panel-heading collapsed" role="tab" id="headingTwo" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                          <h4 class="panel-title">Change Password</h4>
                        </a>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                          <div class="panel-body">
                           <div class="row" style="margin-bottom:15px;">
                           		<div class="col-md-4">
                           			Old Password
                           		</div>
                           		<div class="col-md-7">
                           			<input type="password" name="oldPassword" id="oldPassword" class="form-control" onkeyup="verifyOldPass(oldPassword.value);">
                           		</div>
                           		<div class="col-md-1" id="oldPassID">
                           			
                           		</div>
                           	</div>
                           		
                           	<div class="row" style="margin-bottom:15px;">
                           		<div class="col-md-4">
                           			New Password
                           		</div>
                           		<div class="col-md-7">
                           			<input type="password" name="newPassword" id="newPassID" disabled="disabled" onkeyup="newPassCheck(newPassID.value);" class="form-control">
                           		</div>
                           		<div class="col-md-1" id="newPassCheckID">
                           			
                           		</div>
                           	</div>
                           		
                           	<div class="row" style="margin-bottom:15px;">
                           		<div class="col-md-4">
                           			Confirm New Password
                           		</div>
                           		<div class="col-md-7">
                           			<input type="password" name="confirmNewPassword" id="confirmNewPassID" disabled="disabled" onkeyup="checkConfirmNewPass(confirmNewPassID.value,newPassID.value);" class="form-control">
                           		</div>
                           		<div class="col-md-1" id="confirmPassID">
                           			
                           		</div>
                           </div>
                          </div>
                        </div>
                      </div>
                      <div class="panel">
                        <a class="panel-heading collapsed" role="tab" id="headingThree" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                          <h4 class="panel-title">Change PIN</h4>
                        </a>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                          <div class="panel-body">
                           	<div class="row" style="margin-bottom:15px;">
                           		
                           		<div class="col-md-4">
                           			New PIN
                           		</div>
                           		<div class="col-md-7">
                           			<input type="password" name="newPIN" id="newPINID" class="form-control">
                           		</div>
                           		
                           	</div>
                           	
                           	<div class="row" style="margin-bottom:15px;">
                           		
                           		<div class="col-md-4">
                           			Confirm New PIN
                           		</div>
                           		<div class="col-md-7">
                           			<input type="password" name="confirmNewPIN" id="confirmNewPINID" onkeyup="checkConfirmNewPIN(confirmNewPINID.value,newPINID.value);" class="form-control">
                           		</div>
                           		<div class="col-md-1" id="confirmPINID">
                           			
                           		</div>
                           		
                           </div>
                          </div>
                        </div>
                      </div>
                    </div>
		<!-- end of accordion -->

        </div>
        <div class="modal-footer">
	        <center>
	          <button type="button" data-dismiss="modal" id="secCredCancelID" class="btn btn-primary">Cancel</button>
	          <button type="button" style="width:25%" id="securityBtnID" onclick="updateSecurityCredentials(newPassID.value, newPINID.value);" class="btn btn-success" disabled="disabled" data-dismiss="modal">Update</button>
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
           <jsp:include page="patientLeftMenu.jsp"></jsp:include>
           <!-- Ends -->
            
            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" style="color:#73879C;" data-placement="top" title="Security Credentials" onclick="showSecurityModal();">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" style="color:#73879C;" data-placement="top" title="FullScreen" onclick="widnwoFullScreen();">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" style="color:#73879C;" data-placement="top" title="Lock" onclick="showLockModal();">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" style="color:#73879C;" data-placement="top" title="Logout" onclick="showLogoutModal();">
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
                  
	                
		 		<div class="col-md-1" style="margin-top:15px;padding-left:0px;">
	              	<font style=" font-size: 16px;font-weight: bold;">Clinic</font>
	            </div>
	              
		 		<div class="col-md-2" style="margin-top:10px;padding-left:0px;">
	              	
	              <!-- <form action="ChangeClinic" method="POST" id="changeClinicForm" name="changeClinicForm"> -->
	              <input type="hidden" name="changedPracticeID" id="" value = "<%=practiceID%>" >
					<select name="changedClinicID" class="form-control" onchange="submitChangeClinicForm(this.value,<%=patientID%>);" style="border-radius: 5px;">
						<%
							Set<Integer> set1 = clinicMap.keySet();
							for(Integer clinicID: set1){
								if(clinicID == form.getClinicID() ){
						%>
							<option value="<%=clinicID%>" selected="selected"><%=clinicMap.get(clinicID) %></option>
						<%
								}else{
						%>
							<option value="<%=clinicID%>"><%=clinicMap.get(clinicID) %></option>
						<%
								}
							}
						%>
					</select>
					<%-- <select name="" id="changeClinicForm" class="form-control"  style="border-radius: 5px;" >
		       			<option value="-1">Select Clinic</option>
			        </select> --%>
				  </form>

	            </div>
	            
	           
	              
              <ul class="nav navbar-nav navbar-right" style="width: 41%;">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" id ="profPicID" aria-expanded="false">
                  	<%
              		if(patientDaoInf.retrieveProfilePic(patientID) == null || patientDaoInf.retrieveProfilePic(patientID).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%=patientName%>
	                
	                <%
	              		}else{
S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveProfilePic(form.getUserID()))).getObjectContent();
	 						
	 						IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" +daoInf.retrieveProfilePic(form.getUserID()))));  
	 				  %>
	                	
	                 <img src="<%=  "images/" +daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
	                <%
	              		}
	                %>
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                  
                  <!-- <li><a href="RenderEditProfile"> Profile</a></li> -->
                    <li><a href="javascript:editPatientProfile(<%=patientID%>)" > Profile</a></li>
                    <li><a href="Logout"><i class="fa fa-sign-out pull-right"></i>Log Out</a></li>
                  </ul>
                </li>
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
										
										<h3>PATIENT VISITS</h3>
									
									</div>
								</div>
								
								<font style="color: green;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="successMSG"></center> </font>
    					
    									<font id="" style="color: red;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="exceptionMSG"></center></font>
								
								<table class="table table-striped table-bordered dt-responsive nowrap" vcrf ="0" width="100%" id ="visitTableTRID">
										<thead>
											<tr>
												<th style=" text-align: center;">Visit Date</th>
												<th style=" text-align: center;">Visit Type</th>
												<th style=" text-align: center;">Diagnosis</th>
												<th style=" text-align: center;">CRF</th>
												<th style=" text-align: center;">Prescription</th>
												<th style=" text-align: center;">Bill</th>
												<th style=" text-align: center;">Visit</th>
											</tr>
										</thead>
										<tbody>
										
										<tr id="visitTRIDD" style="display: none;" > 
											
													
										</tr>
										
										
											<s:iterator value="patientVisitList" var="UserForm">
												<tr id="visitTRID<s:property value="visitID"/>">
													
													<td style="width: 15%; text-align: center;"><s:property value="visitDate" /></td>

													<td style="width: 15%; text-align: center;"><s:property value="visitType" /></td>

													<td style="width: 15%; text-align: center;"><s:property value="diagnosis" /></td>
													
													<td style="width: 15%; text-align: center;">
															<a href="javascript:viewCRF(<s:property value="visitID"/>,'<s:property value="formName"/>',<s:property value="visitTypeID"/>,<s:property value="apptID"/>);" style="font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;">View</a>
															<a href="javascript:downloadCRF(<s:property value="visitID"/>,'<s:property value="formName"/>');" style="font-size: 15px;font-weight: bold;text-decoration: underline;">Download</a>
													</td>
													
										 			<td style="width: 15%; text-align: center;">
										 					<a href="javascript:viewPRESC(<s:property value="visitID"/>,'<s:property value="formName"/>',<s:property value="visitTypeID"/>,<s:property value="apptID"/>);" style="font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;">View</a>
										 					<a href="javascript:downloadPRESC(<s:property value="visitID"/>,'<s:property value="formName"/>');" style="font-size: 15px;font-weight: bold;text-decoration: underline;">Download</a>
										 			</td>
										 			
													<td style="width: 15%; text-align: center;">
															<a href="javascript:viewBILL(<s:property value="visitID"/>);" style="font-size: 15px;font-weight: bold;text-decoration: underline; padding-right:15px;">View</a>
															<a href="javascript:downloadBILL(<s:property value="visitID"/>,'<s:property value="formName"/>');" style="font-size: 15px;font-weight: bold;text-decoration: underline;">Download</a>
													</td>
													
													<td style="width: 15%; text-align: center;">
															<a href="javascript:viewPage(<s:property value="visitID"/>,'<s:property value="formName"/>',<s:property value="visitTypeID"/>,<s:property value="apptID"/>);" style="font-size: 15px;font-weight: bold;text-decoration: underline;">View</a>
													</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
        						</div>
							</div>
						</div>
					</div>
				</div>
		
      
        
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            
          </div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    
    
    <!-- For full calendar -->
    <script src="build/js/moment.min.js"></script>
	<script src="build/js/fullcalendar.min.js"></script>
    
    <!-- Datatables -->
    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    
   <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
  
    <!-- Datatables -->
    <script>
      $(document).ready(function() {

        $('#datatable-responsive').DataTable();
        $('#datatable-responsive1').DataTable();
        $('#datatable-responsive2').DataTable();

      });
    </script>
    <!-- /Datatables -->
    
    <script>
    
	$(window).load( function() {

		$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
		
		$('.radioClass').prop('disabled', true);

	});
	</script>
    
  </body>
</html>