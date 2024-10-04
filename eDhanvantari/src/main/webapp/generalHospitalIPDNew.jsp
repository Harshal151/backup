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
    <script type="text/javascript" src="build/js/jquery-ui-1.10.0.custom.min.js"></script>
    <script type="text/javascript" src="build/js/jquery-ui.js"></script>
    
    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <link  rel="stylesheet" type="text/css" href="build/css/bootstrap-3.0.3.min.css" />
    <link  rel="stylesheet" type="text/css" href="build/css/bootstrap-multiselect.css" />
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    
     <link href="datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
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
          document.location="ViewPatient";
        }
    </script>
    
    <style type="text/css">
		.dojoComboBoxOptions{
			top: 564px;
			left: 531px;
		}
		
		.datatable-responsive_filter{
			width:100%;
		}
	
    	input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
		    -webkit-appearance: none;
		    -moz-appearance: none;
		    appearance: none;
		    margin: 0; 
		}
    
		#labReportDivID{
			padding-bottom: 10px;
		}
	
		ul.actionMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		ul.errorMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
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
		if(session.getAttribute("USER") == "" || session.getAttribute("USER") == null){
			form.setFullName("");
			String loginMessage = "Plase login using valid credentials";
			request.setAttribute("loginMessage",loginMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
		
		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		
		String fullName = form.getFullName();

		String visitTabCheck = (String) request.getAttribute("visitTabCheck");
		
		String IPDVisitCheck = (String) request.getAttribute("IPDVisitCheck");
		
		String IPDBillCheck = (String) request.getAttribute("IPDBillCheck");
	
		if(IPDBillCheck == null || IPDBillCheck == ""){
			IPDBillCheck = "dummy";
		}
		
		if(IPDVisitCheck == null || IPDVisitCheck == ""){
			IPDVisitCheck = "dummy";
		}
	
		if(visitTabCheck == null || visitTabCheck == ""){
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

				AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
				
	%>
    <!-- Ends -->

	<!-- Print visit, prescription and billing -->
	
	<script type="text/javascript">
		function saveVisit(){
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
			document.forms["visitrForm"].action = "EditGeneralHospitalAddIPDVisit";
			document.forms["visitrForm"].submit();
		}

		
	</script>
	
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

    %>
    <!-- Ends -->
  
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
		
		if(hourInteger > 12){
			ampm = "pm";
			hour = String.valueOf(hourInteger - 12);
			ampm = "pm";
		}
		
		if((minuteInt >= 0) && (minuteInt < 5)){
			
			minute = "0";
			
		}else if((minuteInt >= 5) && (minuteInt < 10)){
			
			minute = "5";
			
		}else if((minuteInt >= 10) && (minuteInt < 15)){
			
			minute = "10";
			
		}else if((minuteInt >= 15) && (minuteInt < 20)){
			
			minute = "15";
			
		}else if((minuteInt >= 20) && (minuteInt < 25)){
			
			minute = "20";
			
		}else if((minuteInt >= 25) && (minuteInt < 30)){
			
			minute = "25";
			
		}else if((minuteInt >= 30) && (minuteInt < 35)){
			
			minute = "30";
			
		}else if((minuteInt >= 35) && (minuteInt < 40)){
			
			minute = "35";
			
		}else if((minuteInt >= 40) && (minuteInt < 45)){
			
			minute = "40";
			
		}else if((minuteInt >= 45) && (minuteInt < 50)){
			
			minute = "45";
			
		}else if((minuteInt >= 50) && (minuteInt < 55)){
			
			minute = "50";
			
		}else{
			
			minute = "55";
			
		}
		
		String[] currentDateArray = currentDate.split("-");
		
		String currDay = currentDateArray[0];
		String currMonth = currentDateArray[1];
		String currYear = currentDateArray[2];
		
		//Retrieving appointment duration from Calendar table based on clinicID
		PatientDAOInf patientDAOInf = new PatientDAOImpl();
		
		//verify whether existing visit type is discharge visit 
		boolean dischargeVisit = patientDAOInf.verifyIPDDischargeVisitType(form.getVisitTypeID());
		
		PrescriptionManagementDAOInf managementDAOInf = new PrescriptionManagementDAOImpl();
		
		//retrieving roomtype list
		HashMap<Integer, String> roomTypeMap = managementDAOInf.retrieveRoomTypeList(form.getPracticeID());
		
		LinkedHashMap<Integer, String> roomTypeList = configXMLUtil1.sortHashMapByValue1(roomTypeMap);
	%>
	
	
	<%
		String appointmentCheck = (String) request.getAttribute("appointmentCheck");
		if(appointmentCheck == null || appointmentCheck == ""){
			appointmentCheck = "";
		}else{
			
	%>
	
	
	
	<%
		}
	%>
	
	
    
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
    
    <%
		String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");
    
    	if(lastEneteredVisitList == null || lastEneteredVisitList == ""){
    		lastEneteredVisitList = "dummy";
    	}
    	
    	String printAvailable = (String) request.getAttribute("printAvailable");
        
    	if(printAvailable == null || printAvailable == ""){
    		printAvailable = "dummy";
    	}
    	
	%>
	
	<!-- Retrieve tabs by practice ID -->
    
    <%
	    HashMap<String, String> ValueMap = new HashMap<String, String>();
	    
    	if(dischargeVisit){
    		ValueMap.put("clinician","IPD Visit,Billing");
    	}else{
    		ValueMap.put("clinician","IPD Visit");	
    	}
	    ValueMap.put("billDesk","Billing");
	    ValueMap.put("compounder","Billing");
	
		String tabs = ValueMap.get(form.getUserType());
     %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("IPD Visit", "visit");
		idMap.put("Prescription", "presc");
		idMap.put("Billing", "billing");
		idMap.put("Report", "labRep");
		idMap.put("Medical Certificate", "medCerti");
		idMap.put("Referral Letter", "refLetter");
		
		//Adding class active to the first value in tab String
		if(tabs.contains(",")){
			String ID = tabs.split(",")[0];
	%>
		$("#<%=idMap.get(ID)%>").addClass('active in');
	<%
			
		}else{
			
	%>
		$("#<%=idMap.get(tabs)%>").addClass('active in');
	<%
			
		}
    
    %>
    
    });
   
    </script>


	
	<%
		String billingTabCheck = (String) request.getAttribute("billingTabCheck");
		
		if(billingTabCheck == null || billingTabCheck == ""){
			billingTabCheck = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#billing"]').tab('show');
			
			<%	
				//Removing class active to the first value in tab String
				if(tabs.contains(",")){
					String ID = tabs.split(",")[0];
			%>
				$("#<%=idMap.get(ID)%>").removeClass('active');
			<%
					
				}else{
					
			%>
				$("#<%=idMap.get(tabs)%>").removeClass('active');
			<%
					
				}
		    
		    %>
	
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
		if(saveVisitcheck == null || saveVisitcheck == ""){
			saveVisitcheck = "noSave";
		}
	%>
	
	
	<%
	
		String systemStartDate = (String) request.getAttribute("systemStartDate");
    	
    	if(systemStartDate == null || systemStartDate == ""){
    		systemStartDate = "NA";
    	}
	%>
	
	
	
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
	.multiselect-container{
	overflow:auto; 
	  height:540%;
	  
	}

</style>	
	
	<!-- Multiselect checkbox dropdown list -->
		
<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
		$('#ComplaintsID').multiselect({
	     	includeSelectAllOption: true
	 	});
	   
	 });

</script>

	<!-- Ends -->
	
	
  
  <script type="text/javascript">
		
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
		
		function checkCheckbox() {
						
			var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				$('html, body').animate({
			        scrollTop: $('body').offset().top
			    }, 1000);
				
				$('body').css('background', '#FCFAF5');
				$(".container").css("opacity","0.2");
				$("#loader").show();
				
				$('.checkboxClass').prop("required", false);
				
				document.forms["genPhyBillID"].action = "AddNewIPDBill";
				document.forms["genPhyBillID"].submit();
				$("#"+btnID).attr('disabled',true);
					return true;
				
			}else{
				$('.checkboxClass').prop("required", true);
				return false;
			}
		
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
				
				finalAmount = changeQty * changeRate;
				
				$("#"+amount).val(finalAmount);
			
				//for single med quantity calculation
				$("input[name='chargeAmount']").each(function(){
					
					var currVal = $(this).val();
					
					finaltotalAmt = parseFloat(parseFloat(finaltotalAmt) + parseFloat(currVal));
					
				});
				
				var finalAmt = parseFloat(parseFloat(finaltotalAmt) + parseFloat(consultationCharge));
				
				$("#"+totalAmt).val(finalAmt);
				
				$("#netAmountID").val(finalAmt);
				
			}
			
		}
		
		
		function changeNetAmtByDiscount(discountAmt, totalAmt, emergencyCharges, advPayment, mlcCharges, ambulanceDoctorCharges){
			if(discountAmt == ""){
				discountAmt = 0;
			}
			
			if(totalAmt == ""){
				totalAmt = 0;
			}
			
			if(emergencyCharges == ""){
				emergencyCharges = 0;
			}
			
			if(mlcCharges == ""){
				mlcCharges = 0;
			}
			
			if(ambulanceDoctorCharges == ""){
				ambulanceDoctorCharges = 0;
			}
		
			var netAmount = parseFloat(parseFloat(totalAmt) + parseFloat(emergencyCharges) + parseFloat(mlcCharges) + parseFloat(ambulanceDoctorCharges) - parseFloat(discountAmt));
			
			if(netAmount.toString().includes(".")){
				var array = netAmount.toString().split(".");
				
				if(array[1].length > 2 ){
					netAmount = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			$("#netAmountID").val(netAmount);
			if(advPayment == 0){
				$("#advPaymentID").val(netAmount);
			}else{
				var advPayment = $("#advPaymentID").val();
				var balPayment = parseFloat(parseFloat(netAmount) - parseFloat(advPayment));
				
				if(balPayment.toString().includes(".")){
					var array = balPayment.toString().split(".");
					
					if(array[1].length > 2 ){
						balPayment = array[0] + "." + array[1].substr(0,2);
					}
				}
				
				$("#balPaymentID").val(balPayment);
			}
		}
		
	</script>

<!-- Ends -->

<!-- Function to change the cash to return to customer -->

<script type="text/javascript">
	
		function changeCashToReturn(cashPaid, balPayment){
			
			if(cashPaid == ""){
				cashPaid = 0;
			}
			
			if(balPayment == ""){
				balPayment = 0;
			}
			
			var cashTOReturn = parseFloat(cashPaid) - parseFloat(balPayment);
			
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
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$('#chequeDetailID').show(1000);
    			
    			$("#chequeNoID").attr("required", "required");
    			$("#chequeBankNameID").attr("required", "required");
    			$("#chequeBankBranchID").attr("required", "required");
    			$("#chequeAmtID").attr("required", "required");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
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
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}


    	}

    	function hideChequeDetailsDiv(input){
    		
    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$("#cashDetailID").show(1000);
    			
    			$("#cashPaidID").attr("required", "required");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#cashDetailID").hide(1000);
    			
    			$("#cashPaidID").removeAttr("required");
    			
    			$("#cashPaidID").val("");
    			$("#cashToReturnID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}

    	}
    	
    	function displayCardDiv(input){
    		
    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$('#cardDetailID').show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$('#cardDetailID').hide(1000);
    			
    			$("#cardMobileNoID").val("");
    			$("#cMobileNoID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}
 
    		
    	}
    	
    	function displayChequeDetailsDivCreditNote(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$("#creditNoteDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#creditNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}

        	paymentCounter++;

    	}
    	
    	function displayChequeDetailsDivOther(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
			}
			
    		if($(input).is(":checked")){
    			$("#otherDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#otherDetailID").hide(1000);
    			
    			$("#otherTypeID").val("");
    			$("#otherTypeAmountID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
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
    	
    	function showAmountReturnedDiv(cashID, chequeID, cardID, otherID){
    		if($("#"+cashID).is(":checked") || $("#"+chequeID).is(":checked") || 
    				$("#"+cardID).is(":checked") || $("#"+otherID).is(":checked")){
    			$("#amountReturnedDivID").show(500);
    		}else{
    			$("#amountReturnedDivID").hide(500);
    		}
    	}
    	
    </script>

<!-- Ends -->

	<% 
	String paymentType = (String) request.getAttribute("paymentType");
		
		HashMap<String,String> paymentMap = new HashMap<String, String>();
		
		paymentMap.put("Cash","paymentTypeCashID=cashDetailID");
		paymentMap.put("Cheque","paymentTypeChequeID=chequeDetailID");
		paymentMap.put("Credit/Debit Card","paymentTypeCardID=cardDetailID");
		paymentMap.put("Other","paymentTypeOtherID=otherDetailID");
		
		
		if(paymentType == null || paymentType == ""){
			paymentType = "dummy";
		}else{
			
			if(paymentType.contains(",")){
				
				String[] arr = paymentType.split(",");
				
				for(int i = 0; i < arr.length; i++){
					
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
			}else{
				
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
			
				$("#visitrForm").attr("action","AddNewGeneralHospitalVisit");
			  	  $("#visitrForm").submit(); 
			  	  
				return true;
			}
		}
		
		var otDChargeCounter = 1;
		
		function openChargeDisbursementModal(){
			if(otDChargeCounter == 1){
				$("#chargeDisbursementHiddenID").val("");
				$('#otChargeDisbursementTbodyID tr:gt(0)').remove();	
			}
			$("#chargeDisbursementModalID").modal("show");
		}
		
		var editOTDCounter = 100;
		
		function fetchEditDetails(chargeDisbursementValue, chargeDisbursementHiddenID, chargeTypeClass, chargesClass, rateID){
			
			$('#editChargesDTBodyID tr:gt(0)').remove();
			
			$('#editOtChargeDisbursementTbodyID tr:gt(0)').remove();
			
			editOtDChargeCounter = 500;
			
			var TRTag = "";
			
			if(chargeDisbursementValue.includes("\\$")){
				//splitting chargeDisbursementValue by $ in order to display all charges details
				var array1 = chargeDisbursementValue.split("\\$");
				
				for(var i = 0; i < array1.length; i++){
					var newArray = array1[i].split("==");
					
					var chargeType = newArray[0];
					var charges = newArray[1];
					
					var TRID = "editOTCDTRID"+editOTDCounter;
					
					editOTDCounter++;
					
					TRTag += "<tr style='font-size: 14px;' id='"+TRID+"'>"
						   + "<td><input type='text' class='form-control "+chargeTypeClass+"' value='"+chargeType+"'></td>"
						   + "<td><input type='number' class='form-control "+chargesClass+"' value='"+charges+"'></td>"
						   + "<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
						   + "</tr>";
					
				}
				
			}else{
				
				var array1 = chargeDisbursementValue.split("==");
				
				var chargeType = array1[0];
				var charges = array1[1];
				
				var TRID = "editOTCDTRID"+editOTDCounter;
				
				TRTag += "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   + "<td><input type='text' class='form-control "+chargeTypeClass+"' value='"+chargeType+"'></td>"
					   + "<td><input type='number' class='form-control "+chargesClass+"' value='"+charges+"'></td>"
					   + "<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   + "</tr>";
					   
				editOTDCounter++;
				
			}		
			
			$(TRTag).insertAfter($("#editChargesDTRID"));
			
			var onclickMethod = "";
			
			$("#dEditbtnID").attr("onclick","saveEditChargesDisbursement('"+chargeTypeClass+"','"+chargesClass+"','"+chargeDisbursementHiddenID+"','"+rateID+"');");
			
			$("#editChargeTypeHiddenID").val(chargeTypeClass);
			
			$("#editChargesHiddenID").val(chargesClass);
			
			$("#editChargeDisbursementModalID").modal("show");
			
		}
		
		function retrieveOTChargeDisbursementDetails(otChargeID){
			
			$('#viewOtChargeDisbursementTbodyID tr:gt(0)').remove();
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = 0;
					
					var TRTag = "";
	
					for ( var i = 0; i < array.Release.length; i++) {
					
						check = array.Release[i].check;
						
						TRTag += "<tr>"
								+"<td style='text-align:center;'>"+array.Release[i].chargeType+"</td>"
								+"<td style='text-align:center;'>"+array.Release[i].charges+"</td>"
								+"</tr>";
	
					}
					
					if(check == 0){
						alert("No charge disbursement added");
					}else{
						$(TRTag).insertAfter($("#viewOtChargeDisbursementTRID"));
						$("#viewChargeDisbursementModalID").modal("show");
					}
					
					
				}
			};
			xmlhttp.open("GET", "RetrieveOTChargesDisbursementDetails?otChargeID="+ otChargeID , true);
			xmlhttp.send();
			
		}
	</script>
	
	<%
		String pdfOutFIleName1 = (String) request.getAttribute("PDFOutFileName");
		System.out.println("IPD PDF out file path ::: "+pdfOutFIleName1);
		
		if(pdfOutFIleName1 == null || pdfOutFIleName1 == ""){
			
			pdfOutFIleName1 = "dummy";
			
		}else{
			
			if(pdfOutFIleName1.contains("\\")){
				
				pdfOutFIleName1 = pdfOutFIleName1.replaceAll("\\\\", "/");
			}
		}
	
		if(pdfOutFIleName1!="dummy"){ %>
		<script type="text/javascript">
		
		  document.addEventListener('DOMContentLoaded', function() {
			  OPDPdfAction();
		  });
		  
		</script>
	<%  }  %>
	
	<script type="text/javascript">
	var popup;
		function OPDPdfAction(){
			popup = window.open('PDFDownload?pdfOutPath=<%=pdfOutFIleName1%>',"Popup", "width=700,height=700");
			popup.focus();
			popup.print();
		}
	</script>
	
<sx:head/>
  </head>
  

  <!-- <body class="nav-md" onload="getValuesByChargeType(description.value);"> -->
  <body class="nav-md" style="overflow-x: hidden;">
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

	<!-- Add OT charges disbursement modal -->
  
  <div id="chargeDisbursementModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="">OT CHARGES DISBURSEMENT</h4>
      </div>
      
        <div class="modal-body">
          
         <div class="row">
         	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th>Charge Type</th>
					                       <th>Charges</th>
					                       <th style="text-align: center;">Action</th>
					                    </tr>
					                </thead>
					                
					                <tbody>
					                    
					                   <tr>   
					                        <td id="">
					                        	<input type="text" class="form-control" id="dChargeTypeID" placeholder="Charge type">
					                        </td> 	
					                        <td>
					                        	<input type="number" class="form-control" id="dChargeID" placeholder="Charges">
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:void(0)" id="otChargeDImgaID"> 
												  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
												  		alt="Add Charge" title="Add Charge" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                   </tr>
					                 </tbody>
					                 <tbody id="otChargeDisbursementTbodyID" style="font-size: 14px;">
					                 	<tr id="otChargeDisbursementTRID">
					                    </tr>
					                 </tbody>
					            </table>
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" id="dAddbtnID" >Save</button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- Edit OT charges disbursement modal -->
  
  <div id="editChargeDisbursementModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="">EDIT OT CHARGES DISBURSEMENT</h4>
      </div>
      
        <div class="modal-body">
          
         <div class="row">
         	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th>Charge Type</th>
					                       <th>Charges</th>
					                       <th style="text-align: center;">Action</th>
					                    </tr>
					                </thead>
					                
					                <tr>   
					                        <td id="">
					                        	<input type="text" class="form-control" id="editdChargeTypeID" placeholder="Charge type">
					                        	<input type="hidden" id="editChargeTypeHiddenID">
					                        </td> 	
					                        <td>
					                        	<input type="number" class="form-control" id="editdChargeID" placeholder="Charges">
					                        	<input type="hidden" id="editChargesHiddenID">
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:void(0)" id="editotChargeDImgaID"> 
												  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
												  		alt="Add Charge" title="Add Charge" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                   </tr>
					                 </tbody>
					                 <tbody id="editOtChargeDisbursementTbodyID" style="font-size: 14px;">
					                 	<tr id="editOtChargeDisbursementTRID">
					                    </tr>
					                 </tbody>
					                
					                <tbody id="editChargesDTBodyID" style="font-size: 14px;">
					                    
					                   <tr id="editChargesDTRID">   
					                   </tr>
					                 </tbody>
					            </table>
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" id="dEditbtnID" >Save</button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  <!-- View OT charges disbursement modal -->
  
  <div id="viewChargeDisbursementModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="">OT CHARGES DISBURSEMENT</h4>
      </div>
      
        <div class="modal-body">
          
         <div class="row">
         	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th style="text-align: center;">Charge Type</th>
					                       <th style="text-align: center;">Charges</th>
					                    </tr>
					                </thead>
					                
					                 <tbody id="viewOtChargeDisbursementTbodyID" style="font-size: 14px;">
					                 	<tr id="viewOtChargeDisbursementTRID">
					                    </tr>
					                 </tbody>
					                
					            </table>
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  

  <!-- Register open alert modal -->
  
  <div id="registerErrorModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;">Leave register is not opened for this year. Kindly contact administrator.</h4>
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
  
  <div id="logoutModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
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
          <a href="Logout"><button type="button" style="width:25%" class="btn btn-success">OK</button></a>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  <!-- Lock modal -->
  
  <div id="lockModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
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
          <a href="Logout?userID=<%= userID %>"><button type="submit" style="width:25%" class="btn btn-primary">Logout</button></a>
          <button type="button" id="lockButtonID" class="btn btn-primary" data-dismiss="modal" style="display: none;"></button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  <!-- Security Credential modal -->
  
  <div id="securityModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
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
            	<jsp:include page="leftMenu.jsp"></jsp:include>
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

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" id ="profPicID" aria-expanded="false">
                  	<%
              		if(daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%= form.getFullName() %>
	                
	                <%
	              		}else{
S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveProfilePic(form.getUserID()))).getObjectContent();
	 						
	 						IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" +daoInf.retrieveProfilePic(form.getUserID()))));  
	 				  %>
	                	
	                 <img src="<%= "images/" +daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
	                <%
	              		}
	                %>
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="RenderEditProfile" onclick="showLoadingImg();"> Profile</a></li>
                    <!--<li><a href="DownloadManual"> Help</a></li>-->
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
                        <h3>ADD NEW IPD VISIT</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                  <div class="x_content span_11">
                  
                  	<div>
		              	<s:if test="hasActionErrors()">
							<center>
							<font style="color: red; font-size:16px;"><s:actionerror /></font>
							</center>
						</s:if><s:else>
							<center>
							<font style="color: green;font-size:16px;"><s:actionmessage /></font>
							</center>
						</s:else>
						
							<font style="color: green;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="successMSG"></center> </font>
    					
    						<font id="" style="color: red;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="errorMSG"></center></font>
    					
    						<font id="" style="color: red;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="exceptionMSG"></center></font>
    					
		              </div>
		              
					
			         <s:iterator value="patientList" var="patientVat">
			         
			         <h4 style=""><b>Visit For <s:property value="firstName"/>&nbsp;<s:property value="lastName"/>&nbsp;(<s:property value="registrationNo"/>)</b></h4>
		              
		              <div class="ln_solid"></div>
			        
					  <ul id="myTab1" class="nav nav-tabs">
					  
					  
					  	<%
		              		//Check whether tabs string contains comma (,), if yes
		              		//split tabs string by , and create tabs else create only 
		              		//one tab
		              		if(tabs.contains(",")){
		              			
		              			int count = 1;
		              			
		              			String[] tabsArray = tabs.split(",");
		              			
		              			for(int i = 0; i < tabsArray.length; i++){
		              				
		              				if(count == 1){
		              					count++;		              				
						%>
		              	
		              			<li class="active"><a href="#<%=idMap.get(tabsArray[i]) %>" data-toggle="tab"><%=tabsArray[i] %></a></li>
		              				
		              	<% 
		              				}else{
		              							
						%>
						
								<li><a href="#<%=idMap.get(tabsArray[i]) %>" data-toggle="tab"><%=tabsArray[i] %></a></li>
						
						<%		              					
		              				}	
		              				
		              			}
		              			
		              		}else{
		              			
		              	%>
		              		<li class="active"><a href="#<%=idMap.get(tabs) %>" data-toggle="tab"><%=tabs %></a></li>
		              	<%
		              			
		              		}
		              	%>
					 
					  </ul>
			    
			        
				 <div id="myTabContent1" class="tab-content">
				 
				 
				 	<!-- Visit div -->
			    <div class="tab-pane fade in active" id="visit">
				    <form class="form-horizontal form-label-left" action="AddNewGeneralHospitalIPDVisit" onsubmit="return disableSubmitBtn('VisitSubmitID');" name="visitrForm" id="visitrForm" method="POST" style="margin-top:20px;">
	
				      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
			          <input type="hidden" name="startDate" value="<%=systemStartDate%>">
			          <input type="hidden" name="visitFromTime" value="<%=currentTime%>">
				      
					      <div class="row" style="margin-top:15px;">
									<center>
										<h4><b>CASE REPORT FORM</b></h4>
									</center>
								</div>
						  
	                      
	                      <div class="row" style="margin-top:15px;">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"  >
							    		<table border="1" width="100%">
							    		
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Name: </td>
							    				<td colspan="2"> <input type="text" style="border:none;cursor:not-allowed;background: transparent;"  class="form-control" readonly="readonly" value="<s:property value="lastName"/> <s:property value="firstName"/> <s:property value="middleName"/>"  name="" placeholder="Patient Name" autofocus=""></td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Date of first visit: </td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px;"> <input type="text" class="form-control" name="" style="border:none;cursor:not-allowed;background: transparent;" readonly="readonly" value="<s:property value="visitDate"/>" aria-describedby="inputSuccess2Status3"> </td>
							    				<!-- <td colspan="1" style="font-size:14px; font-weight: bold;text-align: center;"> Profile Pic </td> -->
							    			</tr>
							    			
							    			<tr>
							    				<%-- <td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> DOB: </td>
							    				<td><input type="text" class="form-control" name="dateOfBirth" id="" readonly="readonly" style="border:none;cursor:not-allowed;background: transparent;" value="<s:property value="dateOfBirth"/>" aria-describedby="inputSuccess2Status3"></td> --%>
							    				<td style="padding-left:10px;font-size:14px; font-weight: bold;">Age: </td>
							    				<td><input type="number" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" name="age" value="<s:property value="age"/>" placeholder="Age" autofocus=""> </td>
							    				<td style="padding-left:10px;font-size:14px; font-weight: bold;">Sex: </td>
							    				<td colspan="3">
							    				<input type="text" name="gender" style="border:none;cursor:not-allowed;background: transparent;" class="form-control" readonly="readonly" value="<s:property value="gender"/>" placeholder="Gender" autofocus=""></td>
							    			
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Address: </td>
							    				<td colspan="4"> <input type="text" class="form-control" style="border:none;cursor:not-allowed;background: transparent;" value="<s:property value="address"/>" readonly="readonly"  name="address" placeholder="" autofocus=""></td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Phone Number: </td>
							    				<td colspan="1"><input type="number" name="mobile" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control"  value="<s:property value="mobile"/>" autofocus=""  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status"></td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Alternate Number: </td>
							    				<td colspan="2"> <input type="number" name="phone" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" value="<s:property value="phone"/>" autofocus=""  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status"></td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Occupation: </td>
							    				<td colspan="4"><input type="text" name="occupation" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" value="<s:property value="occupation"/>"  autofocus=""></td>
							    				
							    			</tr>
							    				    			
							    		</table>
							 </div>
							 </div>
							 
							 <div class="ln_solid"></div>
							 
							 <div class="row">
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Medical registration number: </font>
						    	</div>
						    	
						    	<div class="col-md-3">
						    		<input class="form-control" name="registrationNo" readonly="readonly" value="<s:property value="registrationNo"/>" placeholder="Medical registration number">
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
							
								<%	
									if(saveVisitcheck.equals("Saved")){
								%>
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Visit Date (DD/MM/YYYY)*: </font>
						    	</div>
						    	
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitDay" required="required" value="<s:property value="visitDay"/>" id="visitDayID" placeholder="Day" maxlength="2" onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
						    		<font style="color: red;" id="dateFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitMonth" required="required" value="<s:property value="visitMonth"/>" id="visitMonthID" placeholder="Month" maxlength="2" onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
						    		<font style="color: red;" id="monthFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitYear" required="required" value="<s:property value="visitYear"/>" id="visitYearID" placeholder="Year" maxlength="4" onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
						    		<font style="color: red;" id="yearFontID"></font>
						    	</div>
						    	
						    	<input type="hidden" class="form-control" name="visitDate" value="<s:property value="firstVisitDate"/>" id="finalDateID">
						    	
						    	<%
									}else{
						    	%>
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Visit Date (DD/MM/YYYY)*: </font>
						    	</div>
						    	
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitDay" value="<%=currDay %>" required="required" id="visitDayID" placeholder="Day" maxlength="2" onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
						    		<font style="color: red;" id="dateFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitMonth" value="<%=currMonth %>" id="visitMonthID" required="required" placeholder="Month" maxlength="2" onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
						    		<font style="color: red;" id="monthFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitYear" value="<%=currYear %>" id="visitYearID" placeholder="Year" required="required" maxlength="4" onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
						    		<font style="color: red;" id="yearFontID"></font>
						    	</div>
						    	
						    	<input type="hidden" class="form-control" name="visitDate" value="<%=currentDate %>" id="finalDateID">
						    	<%
									}
						    	%>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Diagnosis:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input type="text" class="form-control" value="<s:property value="diagnosis"/>" name="diagnosis">
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Room Type*:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<select name="roomTypeID" class="form-control" required="required" id="roomTypeID">
						    			<option value="">Select Room Type</option>
						    		<%
						    			for(Integer roomTypeID : roomTypeList.keySet()){
						    		%>
						    			<option value="<%=roomTypeID%>"><%=roomTypeList.get(roomTypeID)%></option>
						    		<%
						    			}
						    		%>
						    		</select>
						    	</div>
						    	
						    	<div class="col-md-2 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">MLC Charges:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input type="number" class="form-control" value="<s:property value="mlcCharges"/>" name="mlcCharges" >
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Emergency Charges:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input type="number" class="form-control" value="<s:property value="emergencyCharges"/>" name="emergencyCharges" >
						    	</div>
						    	
						    	<div class="col-md-2 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Advance Payment:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input type="number" class="form-control" value="<s:property value="advance"/>" name="advance" >
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-xs-6">
						    		<font style="font-size: 14px;font-weight: bold;">Ambulance's Doctor Charges:</font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input type="number" class="form-control" value="<s:property value="ambulanceDoctorCharges"/>" name="ambulanceDoctorCharges" >
						    	</div>																	
						    	
							</div>
							
							<div class="ln_solid"></div>
							
							<div class="row" style="margin-top:15px;">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Tariff Charges:</font>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th>Item Name</th>
					                       <th>Rate(Rs.)</th>
					                       <th>Quantity</th>
					                       <th>Amount(Rs.)</th>
					                       <th style="text-align: center;">Action</th>
					                    </tr>
					                </thead>
					                
					                <tbody>
					                    
					                   <tr>   
					                        <td id="tariffItemSelectTrID">
					                        	<select name="" id="tariffItemID" class="form-control" onchange="retrieveIPDTariffRate(this.value);">
					                        		<option value="">Select Item</option>
					                        	</select>
					                        </td> 	
					                        <td>
					                        	<input type="hidden" id="itemNameHiddenID">
					                        	<input type="number" class="form-control" id="tarrifRateID" onkeyup="calculateAmount(this.value,tariffQntyID.value,'tariffAmtID');" placeholder="Enter Rate">
					                        </td>
					                         <td>
					                        	<input type="number" class="form-control" id="tariffQntyID" onkeyup="calculateAmount(tarrifRateID.value,this.value,'tariffAmtID');" placeholder="Enter Quantity" value="1">
					                        </td>
					                        <td>
					                        	<input type="number" class="form-control" id="tariffAmtID" placeholder="Enter Amount" readonly="readonly">
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:void(0)" id="tariffImgaID"> 
												  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
												  		alt="Add Tariff Charge" title="Add Tariff Charge" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                   </tr>
					                 </tbody>
					                 <tbody id="tariffTbodyID">
					                 	<tr id="tariffTRID">
					                    </tr>
					                 </tbody>
					                 <tbody>
					                 	<s:iterator value="IPDTariffChargeList">
					                 		<tr style="font-size: 14px;">
					                 			<td><s:property value="IPDChargeName"/></td>
					                 			<td><s:property value="IPDRate"/></td>
					                 			<td><s:property value="IPDQuantity"/></td>
					                 			<td><s:property value="IPDAmount"/></td>
					                 			<td style="text-align: center;">NA</td>
					                 		</tr>
					                 	</s:iterator>
					                 </tbody>
					            </table>
						    	
							</div>
							
							<div class="ln_solid"></div>
							
							<div class="row" style="margin-top:15px;">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Consultant Charges:</font>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th>Doctor Name</th>
					                       <th>Rate(Rs.)</th>
					                       <th>Quantity</th>
					                       <th>Amount(Rs.)</th>
					                       <th style="text-align: center;">Action</th>
					                    </tr>
					                </thead>
					                
					                <tbody>
					                    
					                   <tr>   
					                        <td id="consultantItemSelectTrID">
					                        	<select name="" id="doctorNameID" class="form-control" onchange="retrieveIPDConsultantRate(this.value);">
					                        		<option value="">Select Doctor Name</option>
					                        	</select>
					                        </td> 	
					                        <td>
					                        	<input type="hidden" id="doctorNameHiddenID">
					                        	<input type="number" class="form-control" id="consultantRateID" onkeyup="calculateAmount(this.value,consultantQntyID.value,'consultantAmtID');" placeholder="Enter Rate">
					                        </td>
					                         <td>
					                        	<input type="number" class="form-control" id="consultantQntyID" onkeyup="calculateAmount(consultantRateID.value,this.value,'tariffAmtID');" placeholder="Enter Quantity" value="1">
					                        </td>
					                        <td>
					                        	<input type="number" class="form-control" id="consultantAmtID" placeholder="Enter Amount" readonly="readonly">
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:void(0)" id="consultantImgaID"> 
												  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
												  		alt="Add Consultant Charge" title="Add Consultant Charge" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                   </tr>
					                 </tbody>
					                 <tbody id="consultantTbodyID" style="font-size: 14px;">
					                 	<tr id="consultantTRID">
					                    </tr>
					                 </tbody>
					                 <tbody>
					                 	<s:iterator value="IPDConsultantChargeList">
					                 		<tr style="font-size: 14px;">
					                 			<td><s:property value="IPDChargeName"/></td>
					                 			<td><s:property value="IPDRate"/></td>
					                 			<td><s:property value="IPDQuantity"/></td>
					                 			<td><s:property value="IPDAmount"/></td>
					                 			<td style="text-align: center;">NA</td>
					                 		</tr>
					                 	</s:iterator>
					                 </tbody>
					            </table>
						    	
							</div>
							
							<div class="ln_solid"></div>
							
							<div class="row" style="margin-top:15px;">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">OT Charges:</font>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					               <thead>
										<tr>
					                       <th>Operation</th>
					                       <th>Date & Time</th>
					                       <th>Consultant</th>
					                       <th>Anaesthetist</th>
					                       <th>OT Assistant</th>
					                       <th>Rate</th>
					                       <th>Charges Disbursement</th>
					                       <th style="text-align: center;">Action</th>
					                    </tr>
					                </thead>
					                
					                <tbody>
					                    
					                   <tr>   
					                        <td id="">
					                        	<input type="text" class="form-control" id="operationID" placeholder="Operation Name">
					                        </td> 	
					                        <td>
					                        	<input type="text" class="form-control otDateTimeClass" id="otDateTimeID" placeholder="Date & Time">
					                        </td>
					                         <td>
					                        	<input type="text" class="form-control" id="consultantID" placeholder="Consultant Name">
					                        </td>
					                        <td>
					                        	<input type="text" class="form-control" id="anaesthetistID" placeholder="Anaesthetist Name">
					                        </td>
					                        <td>
					                        	<input type="text" class="form-control" id="otAssistanceID" placeholder="OT Assistant Name">
					                        </td>
					                         <td>
					                        	<input type="number" class="form-control" readonly="readonly" id="otRateID" placeholder="Rate">
					                        	<input type="hidden" id="chargeDisbursementHiddenID">
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:openChargeDisbursementModal();"> 
												  	<img src="images/rupee.png" onmouseover="this.src='images/rupee.png'" onmouseout="this.src='images/rupee.png'" 
												  		alt="Add Charge Disbursement" title="Add Charge Disbursement" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                        <td style="text-align: center;">
					                        	<a href="javascript:void(0)" id="otChargeImgaID"> 
												  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
												  		alt="Add OT Charge" title="Add OT Charge" style="margin-top:5px;height: 24px;" />
												  </a>
					                        </td>
					                   </tr>
					                 </tbody>
					                 <tbody id="otChargeTbodyID" style="font-size: 14px;">
					                 	<tr id="otChargeTRID">
					                    </tr>
					                 </tbody>
					                 <tbody>
					                 	<s:iterator value="OTChargeList">
					                 		<tr style="font-size: 14px;">
					                 			<td><s:property value="operationName"/></td>
					                 			<td><s:property value="otDateTime"/></td>
					                 			<td><s:property value="consultantName"/></td>
					                 			<td><s:property value="anaesthetistName"/></td>
					                 			<td><s:property value="OTAssistantName"/></td>
					                 			<td><s:property value="rate"/></td>
					                 			<td style="text-align: center;"><a href="javascript:retrieveOTChargeDisbursementDetails(<s:property value="otChargeID"/>);">View Details</a></td>
					                 			<td style="text-align: center;">NA</td>
					                 		</tr>
					                 	</s:iterator>
					                 </tbody>
					            </table>
						    	
							</div>
							
	                      <div class="ln_solid"></div>
	                      
	                      <div class="form-group">
	                        <div class="col-md-12 col-xs-12" align="center">
	                          <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
	                      
	                           <%if(IPDVisitCheck.equals("Yes")){ %>
	                           
	                           <button type="button" class="btn btn-success" id="VisitSubmitID" onclick="saveVisit();" >Save</button>
	                           
		                      
		                      <%}else{ %>
		                       
	                            <button type="submit" class="btn btn-success" id="VisitSubmitID"  >Add Visit</button>
	                          
	                          <% } %>
	                        </div>
	                      </div>
				        
				      </form>
			      </div>
			     <!-- End -->
			     
			    
			    <!-- Billing div -->
			     <div class="tab-pane fade" id="billing" >
			     
					<form class="form-horizontal form-label-left" id="genPhyBillID" name="genPhyBillID" onsubmit="return checkCheckbox();" action="AddNewIPDBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:15px;">
						    <h5 style="margin-top:0px;font-size:14px;">Patient Name: <s:property value="lastName"/>&nbsp; <s:property value="firstName"/> &nbsp;<s:property value="middleName"/> &nbsp;(<s:property value="registrationNo"/>)</h5> 
						  </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
					      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
					       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
							
						<s:iterator value="billList">

						<div class="row" style="margin-top: 15px;margin-left:5px;">
							
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt Date:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptDate"/></font>
								<input type="hidden" name="receiptDate" value="<s:property value="receiptDate"/>">
							</div>
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" value="<s:property value="receiptNo"/>">
							</div>
							
						</div>

				        <div class="item form-group" style="margin-top:15px;padding-left: 15px;">
	                        <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                          <thead>
	                          	
								<tr style="font-size: 14px;">
					                <th>Charges For</th>
					                <th>OT Date & Time(If any)</th>
					                <th>Rate</th>
					                <th>Quantity</th>
					                <th>Amount</th>
					           	</tr>
					         </thead>
							<tbody>	
								<s:if test="%{getIPDBillTariffChargeList().isEmpty()}">
								</s:if>
								<s:else>	
									<tr>
										<td colspan="5" style="font-size: 14px;font-weight: bold">
											Tariff Charges
										</td>
									</tr>
									<s:iterator value="IPDBillTariffChargeList">
										<tr style="font-size: 14px;">
											<td><s:property value="IPDChargeName"/>
												<input type="hidden" name="chargeName" value="<s:property value="IPDChargeName"/>">
												<input type="hidden" name="chargeID" value="<s:property value="transactionID"/>">
												<input type="hidden" name="otDateTimeArr" value="<s:property value="otDateTime"/>">
											</td>
											<td><input type="hidden" name="chargeType" value="<s:property value="IPDChargeType"/>"> </td>
											<td><s:property value="IPDRate"/>
												<input type="hidden" name="billingProdRate" value="<s:property value="IPDRate"/>">
											</td>
											<td><s:property value="IPDQuantity"/>
												<input type="hidden" name="billingProdQuantity" value="<s:property value="IPDQuantity"/>">
											</td>
								 		<td><s:property value="IPDAmount"/>
												<input type="hidden" name="billingProdAmount" value="<s:property value="IPDAmount"/>">
											</td>
										</tr>
									</s:iterator>
									</s:else>
									
									<s:if test="%{getIPDBillConsultantChargeList().isEmpty()}">
									</s:if>
									<s:else>
										<tr>
											<td colspan="5" style="font-size: 14px;font-weight: bold">
												Consultant Charges
											</td>
										</tr>
									    <s:iterator value="IPDBillConsultantChargeList">
									       <tr style="font-size: 14px;">
											<td><s:property value="IPDChargeName"/>
												<input type="hidden" name="chargeName" value="<s:property value="IPDChargeName"/>">
												<input type="hidden" name="chargeID" value="<s:property value="transactionID"/>">
												<input type="hidden" name="otDateTimeArr" value="<s:property value="otDateTime"/>">
											</td>
											<td><input type="hidden" name="chargeType" value="<s:property value="IPDChargeType"/>"> </td>
											<td><s:property value="IPDRate"/>
												<input type="hidden" name="billingProdRate" value="<s:property value="IPDRate"/>">
											</td>
											<td><s:property value="IPDQuantity"/>
												<input type="hidden" name="billingProdQuantity" value="<s:property value="IPDQuantity"/>">
											</td>
											<td><s:property value="IPDAmount"/>
												<input type="hidden" name="billingProdAmount" value="<s:property value="IPDAmount"/>">
											</td>
										</tr>
									   </s:iterator> 
									</s:else>
									
									<s:if test="%{getOTBillChargeList().isEmpty()}">
									</s:if>
									<s:else>
										<tr>
											<td colspan="5" style="font-size: 14px;font-weight: bold">
												OT Charges
											</td>
										</tr>
									    <s:iterator value="OTBillChargeList">
									       <tr style="font-size: 14px;">
											<td><s:property value="IPDChargeName"/>
												<input type="hidden" name="chargeName" value="<s:property value="IPDChargeName"/>">
												<input type="hidden" name="chargeID" value="<s:property value="transactionID"/>">
											</td>
											<td><s:property value="otDateTime"/>
												<input type="hidden" name="otDateTimeArr" value="<s:property value="otDateTime"/>">
												<input type="hidden" name="chargeType" value="<s:property value="IPDChargeType"/>"> 
											</td>
											<td><s:property value="IPDRate"/>
												<input type="hidden" name="billingProdRate" value="<s:property value="IPDRate"/>">
											</td>
											<td>
												<input type="hidden" name="billingProdQuantity" value="1">
											</td>
											<td><s:property value="IPDRate"/>
												<input type="hidden" name="billingProdAmount" value="<s:property value="IPDRate"/>">
											</td>
										</tr>
									   </s:iterator> 
									</s:else>
			               </tbody>
			                  
			                  <tbody>
								<tr style="font-size: 14px;">
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Total Amount</font>
									</td>

									<td>
										<input type="number" readonly="readonly" class="form-control" name="totalAmount" value = "<s:property value="totalAmount"/>" id="totalAmountID">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Emergency Charges</font>
									</td>

									<td>
										<input type="number" readonly="readonly" class="form-control" name="emergencyCharges" value = "<s:property value="emergencyCharges"/>" id="emergencyChargesID">
									</td>
								</tr>
								<tr style="font-size: 14px;">
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">MLC Charges</font>
									</td>

									<td>
										<input type="number" readonly="readonly" class="form-control" name="mlcCharges" value = "<s:property value="mlcCharges"/>" id="mlcChargesID">
									</td>
								</tr>
								<tr style="font-size: 14px;">
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Ambulance's Doctor Charges</font>
									</td>

									<td>
										<input type="number" readonly="readonly" class="form-control" name="ambulanceDoctorCharges" value = "<s:property value="ambulanceDoctorCharges"/>" id="ambulanceDoctorChargesID">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Concession if any</font>
									</td>
									<td>
										<input type="number" class="form-control" onkeyup="changeNetAmtByDiscount(this.value,totalAmountID.value, emergencyChargesID.value, advPaymentID.value, mlcChargesID.value, ambulanceDoctorChargesID.value);" value = "<s:property value="totalDiscount"/>" name="totalDiscount" id="totalVatID">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Net Amount</font>
									</td>
									<td>
										<input type="number" readonly="readonly" class="form-control" value = "<s:property value="netAmount"/>" name="netAmount" id="netAmountID">
									</td>
								</tr>
								<%-- <tr>
									<td colspan="4" align="right" style="padding-top: 15px;">
										<font style="font-size: 14px; font-weight: bold;">Amount Paid<span class="required">*</span></font>
									</td>
									<td>
										<input type="number" class="form-control" name="advPayment" required="required" id="advPaymentID" value = "<s:property value="advPayment"/>" onkeyup="changeBalancePayment(advPaymentID.value, netAmountID.value);">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right" style="padding-top: 15px;">
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
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8" style="padding-top: 5px;">
								<font style="font-size: 16px;">Payment Type*</font>
							</div>
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
								<div class="col-md-4 col-sm-4 col-xs-6">
									 <input type="checkbox" name="paymentType" onclick="hideChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeCashID" value="Cash" class="checkboxClass form-control"> 
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cash</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
								<div class="col-md-4 col-sm-4 col-xs-6">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeChequeID" value="Cheque" class="checkboxClass form-control">
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cheque</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
								<div class="col-md-4 col-sm-4 col-xs-6">
									<input type="checkbox" name="paymentType" onclick="displayCardDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeCardID" value="Credit/Debit Card" class=" checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit/Debit Card</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
								<div class="col-md-4 col-sm-4 col-xs-6">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivCreditNote(this);"
										style="height: 25px; box-shadow: none;" id="paymentTypeCreditNoteID" value="Credit Note" class=" checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit Note</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-md-4 col-xs-8">
								<div class="col-md-4 col-sm-4 col-xs-6">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivOther(this);"
										style="height: 25px; box-shadow: none;" id="paymentTypeOtherID" value="Other" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Other</font>
								</div>
							</div>
						</div>
						<%-- <div id="cashDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cash Paid*</font><span class="required" >*</span>
								</div>
								<div class="col-md-2">
									<input type="text" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, balPaymentID.value);"
											value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cash To Return</font>
								</div>
								<div class="col-md-2">
									<input class="form-control" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>" placeholder="Cash To Return" type="number">
								</div>
							</div>
						</div> --%>
						<div id="chequeDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cheque Issued By</font>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="chequeIssuedByID" name="chequeIssuedBy" value="<s:property value="chequeIssuedBy"/>" placeholder="Cheque Issued By">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cheque No.</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input class="form-control" name="chequeNo" id="chequeNoID" value="<s:property value="chequeNo"/>" placeholder="Cheque No." type="text" >
								</div>
							</div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Bank Name</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="chequeBankNameID" value="<s:property value="chequeBankName"/>" name="chequeBankName" placeholder="Bank Name" >
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Branch</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input class="form-control" name="chequeBankBranch" id="chequeBankBranchID" value="<s:property value="chequeBankBranch"/>" placeholder="Branch" type="text" >
								</div>
							</div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Date</font>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" name="chequeDate" value="<s:property value="chequeDate"/>" id="chequeDateID" placeholder="Date">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Amount</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input class="form-control" name="chequeAmt" id="chequeAmtID" value="<s:property value="chequeAmt"/>" placeholder="Amount" type="number" >
								</div>
							</div>
						</div>
						<div id="cardDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Amount</font>
								</div>
								<div class="col-md-3">
									<input type="number" class="form-control" id="cardAmountID" value="<s:property value="cardAmount"/>" name="cardAmount" placeholder="Amount">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Card No.</font>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="cardMobileNoID" value="<s:property value="cardMobileNo"/>" name="cardMobileNo" placeholder="Card No.">
								</div>
							</div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Mobile No.</font>
								</div>
								<div class="col-md-3">
									<input type="number" class="form-control" id="cMobileNoID" value="<s:property value="cMobileNo"/>" name="cMobileNo" placeholder="Mobile No."  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
								</div>
							</div>
						</div>
						<div id="creditNoteDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Credit Balance</font>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="creditNoteBalID" name="creditNoteBal" value="<s:property value="creditNoteBal"/>" placeholder="Credit Note Balance">
								</div>
							</div>
						</div>
						
						<div id="otherDetailID" style="padding: 15px; display: none;">

							<div class="ln_solid"></div>

							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Other Type</font>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="otherTypeID" name="otherType" value="<s:property value="otherType"/>" placeholder="Enter Other payment here">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Amount</font>
								</div>
								<div class="col-md-3">
									<input type="number" class="form-control" id="otherTypeAmountID" name="otherAmount" value="<s:property value="otherAmount"/>" placeholder="Enter Other amount here">
								</div>
							</div>
						</div>
	                      
			     </s:iterator>
			     	<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12 col-xs-12" align="center">
		                        <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		                        
		                      <%if(IPDBillCheck.equals("Yes")){ %>
	                           
	                           <button class="btn btn-success" type="submit" name="addButton" value="save" id="billSubmtBtnID" >Save</button>
	                           
		                       
		                      <%}else{ %>
		                       
	                            <button class="btn btn-success" type="submit" name="addButton" value="add" id="billSubmtBtnID" >Add Bill</button>
	                            
	                          
	                          <% } %>
		                       
		                       
		                      </div>
		                    </div>
			      </form>
			      
			      <div class="ln_solid"></div>
			      
			    </div>
			    <!-- ends -->
			
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
          <div class="pull-right">
            
          </div>
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
    <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    
    <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    
	<script src="build/js/jquery-ui.js"></script>
	
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    
    <%
		int roomTypeID = (Integer) request.getAttribute("roomTypeID");
    
    	
		if(roomTypeID != 0){
	%>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#roomTypeID").val("<%=roomTypeID%>");
			//$("#roomTypeID").trigger("change");
			getIPDList('<%=roomTypeID%>');
		});
	</script>
	<%
		}
	%>
    <script type="text/javascript">
    function getIPDList(roomTypeID){
		if(roomTypeID == ""){
			var tarrifSelectTag = "<select name='' id='tariffItemID' class='form-control' onchange='retrieveIPDTariffRate(this.value);'><option value=''>Select Item</option></select>";
			
			$("#tariffItemSelectTrID").html(tarrifSelectTag);
			
			var consultantSelectTag = "<select name='' id='doctorNameID' class='form-control' onchange='retrieveIPDConsultantRate(this.value);'><option value=''>Select Doctor Name</option></select>";
			
			$("#consultantItemSelectTrID").html(consultantSelectTag);
		}else{
			$.ajax({
                url : "RetrieveIPDListsByRoomType",
                type : "POST",
                data : {
                	roomTypeID : roomTypeID,
                }, 
                dataType : "json",
                success : function(jsonResponse) {
                	var tarrifSelectTag = jsonResponse["tarrifSelectTag"];
                	var consultantSelectTag = jsonResponse["consultantSelectTag"];
                	
                	$("#tariffItemSelectTrID").html(tarrifSelectTag);
                	
                	$("#consultantItemSelectTrID").html(consultantSelectTag);
                	
                },
                error: function(errorMsg){
                	alert("ERROR: "+errorMsg);
                }
        	});
		}
	}
    
    	$(document).ready(function(){
    		
    		$("#roomTypeID").on('change',function(){
    			var roomTypeID = $(this).val();
    			
    			//$("#tariffTbodyID").html("");
    			$('#tariffTbodyID tr:gt(0)').remove();
    			
    			$('#consultantTbodyID tr:gt(0)').remove();
    			
    			if(roomTypeID == ""){
    				var tarrifSelectTag = "<select name='' id='tariffItemID' class='form-control' onchange='retrieveIPDTariffRate(this.value);'><option value=''>Select Item</option></select>";
    				
    				$("#tariffItemSelectTrID").html(tarrifSelectTag);
    				
    				var consultantSelectTag = "<select name='' id='doctorNameID' class='form-control' onchange='retrieveIPDConsultantRate(this.value);'><option value=''>Select Doctor Name</option></select>";
    				
    				$("#consultantItemSelectTrID").html(consultantSelectTag);
    			}else{
    				$.ajax({
    	                url : "RetrieveIPDListsByRoomType",
    	                type : "POST",
    	                data : {
    	                	roomTypeID : roomTypeID,
    	                }, 
    	                dataType : "json",
    	                success : function(jsonResponse) {
    	                	var tarrifSelectTag = jsonResponse["tarrifSelectTag"];
    	                	var consultantSelectTag = jsonResponse["consultantSelectTag"];
    	                	
    	                	$("#tariffItemSelectTrID").html(tarrifSelectTag);
    	                	
    	                	$("#consultantItemSelectTrID").html(consultantSelectTag);
    	                	
    	                },
    	                error: function(errorMsg){
    	                	alert("ERROR: "+errorMsg);
    	                }
    	        	});
    			}
    		});
    		
    		
    		
    		
    		var tariffCounter = 1;
    		
    		$("#tariffImgaID").on('click',function(){
    			
    			var itemIDVal = $("#tariffItemID").val();
    			var itemVal = $("#itemNameHiddenID").val();
    			var quantityVal = $("#tariffQntyID").val();
    			var rateVal = $("#tarrifRateID").val();
    			var amountVal = $("#tariffAmtID").val();
    			
    			if(itemIDVal == ""){
    				alert("No tariff item selected. Kindly select tariff item.");
    			}else{
    				
    				var TRID = "newTariffTRID"+tariffCounter;
    				
    				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
    						   +"<td style='text-align:center;padding-top:15px;'>"+itemVal+"<input type='hidden' name='chargeName' value='"+itemVal+"'><input type='hidden' name='chargeType' value='Tariff'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeRate' id='newTariffRateID"+tariffCounter+"' onkeyup='calculateAmount(this.value,newTariffQntyID"+tariffCounter+".value,\"newTariffAmtID"+tariffCounter+"\");' value='"+rateVal+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeQuantity' id='newTariffQntyID"+tariffCounter+"' onkeyup='calculateAmount(newTariffRateID"+tariffCounter+".value,this.value,\"newTariffAmtID"+tariffCounter+"\");' value='"+quantityVal+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeAmount' id='newTariffAmtID"+tariffCounter+"' readonly='readonly' value='"+amountVal+"'></td>"
    						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
    						   +"</tr>";
    						   
    				$(trTag).insertAfter($("#tariffTRID"));
    				
    				tariffCounter++;
    				
    				$("#tariffItemID").val("");
    				$("#itemNameHiddenID").val("");
    				$("#tariffQntyID").val("1");
    				$("#tarrifRateID").val("");
    				$("#tariffAmtID").val("");
    				
    			}
    			
    		});
    		
			var consultantCounter = 1;
    		
    		$("#consultantImgaID").on('click',function(){
    			
    			var consultantIDVal = $("#doctorNameID").val();
    			var doctorNameVal = $("#doctorNameHiddenID").val();
    			var quantityVal = $("#consultantQntyID").val();
    			var rateVal = $("#consultantRateID").val();
    			var amountVal = $("#consultantAmtID").val();
    			
    			if(consultantIDVal == ""){
    				alert("No doctor name selected. Kindly select doctor name.");
    			}else{
    				
    				var TRID = "newConsultantChrgTRID"+consultantCounter;
    				
    				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
    						   +"<td style='text-align:center;padding-top:15px;'>"+doctorNameVal+"<input type='hidden' name='chargeName' value='"+doctorNameVal+"'><input type='hidden' name='chargeType' value='Consultant'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeRate' id='newConsultantChrgRateID"+consultantCounter+"' onkeyup='calculateAmount(this.value,newConsultantChrgQntyID"+consultantCounter+".value,\"newConsultantChrgAmtID"+tariffCounter+"\");' value='"+rateVal+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeQuantity' id='newConsultantChrgQntyID"+consultantCounter+"' onkeyup='calculateAmount(newConsultantChrgRateID"+consultantCounter+".value,this.value,\"newConsultantChrgAmtID"+tariffCounter+"\");' value='"+quantityVal+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='chargeAmount' id='newConsultantChrgAmtID"+consultantCounter+"' readonly='readonly' value='"+amountVal+"'></td>"
    						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
    						   +"</tr>";
    						   
    				$(trTag).insertAfter($("#consultantTRID"));
    				
    				consultantCounter++;
    				
    				$("#doctorNameID").val("");
    				$("#doctorNameHiddenID").val("");
    				$("#consultantQntyID").val("1");
    				$("#consultantRateID").val("");
    				$("#consultantAmtID").val("");
    				
    			}
    			
    		});
    		
			var otChargeCounter = 1;
    		
    		$("#otChargeImgaID").on('click',function(){
    			
    			var operationVal = $("#operationID").val();
    			var dateTimeVal = $("#otDateTimeID").val();
    			var consultantVal = $("#consultantID").val();
    			var anaesthetistVal = $("#anaesthetistID").val();
    			var assistantVal = $("#otAssistanceID").val();
    			var rateVal = $("#otRateID").val();
    			var chargeDisbursementHidden = $("#chargeDisbursementHiddenID").val();
    			
    			if(operationVal == ""){
    				alert("Please enter operation name.");
    			}else if(dateTimeVal == ""){
    				alert("Please enter date & time.");
    			}else if(rateVal == ""){
    				alert("Please enter rate.");
    			}else{
    				
    				var TRID = "newOTChrgTRID"+otChargeCounter;
    				
    				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
    						   +"<td style='text-align:center;'><input type='text' class='form-control' name='operation' value='"+operationVal+"'></td>"
    						   +"<td ><input type='text' class='form-control otDateTimeClass' name='otDateTimeArr' id='"+otChargeCounter+"' value='"+dateTimeVal+"'></td>"
    						   +"<td ><input type='text' class='form-control' name='consultant' id='"+otChargeCounter+"'  value='"+consultantVal+"'></td>"
    						   +"<td ><input type='text' class='form-control' name='OTAssistant' id='"+otChargeCounter+"'  value='"+anaesthetistVal+"'></td>"
    						   +"<td ><input type='text' class='form-control' name='anaesthetist' id='"+otChargeCounter+"'  value='"+assistantVal+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='OTRate' readonly='readonly' id='otRateID"+otChargeCounter+"'  value='"+rateVal+"'><input type='hidden' class='form-control' name='chargeDisbursementArr' id='chargeDHiddenID"+otChargeCounter+"' value='"+chargeDisbursementHidden+"'></td>"
    						   +"<td style='text-align:center'><a style='text-decoration: underline;' href='javascript:fetchEditDetails(chargeDHiddenID"+otChargeCounter+".value,\"chargeDHiddenID"+otChargeCounter+"\",\"chargeTypeClass"+otChargeCounter+"\",\"chargesClass"+otChargeCounter+"\",\"otRateID"+otChargeCounter+"\");'>Edit Details</a></td>"
    						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
    						   +"</tr>";
    						   
    				$(trTag).insertAfter($("#otChargeTRID"));
    				
    				otChargeCounter++;
    				
    				$("#operationID").val("");
    				$("#otDateTimeID").val("");
    				$("#consultantID").val("");
    				$("#anaesthetistID").val("");
    				$("#otAssistanceID").val("");
    				$("#otRateID").val("");
    				$("#chargeDisbursementHiddenID").val("");
    				
    				otDChargeCounter = 1;
    				
    				appyDatePicker("otDateTimeClass");
    				
    			}
    			
    		});
    		
    		$("#otChargeDImgaID").on('click',function(){
    			
    			var dChargeType = $("#dChargeTypeID").val();
    			var dCharge = $("#dChargeID").val();
    			
    			if(dChargeType == ""){
    				alert("Please enter charge type.");
    			}else if(dCharge == ""){
    				alert("Please enter charges.");
    			}else{
    				
    				var TRID = "newOTChargeDTRID"+otDChargeCounter;
    				
    				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
    						   +"<td style='text-align:center;'><input type='text' class='form-control' name='dChargeType' value='"+dChargeType+"'></td>"
    						   +"<td ><input type='number' class='form-control' name='dCharge' id='"+otDChargeCounter+"'  value='"+dCharge+"'></td>"
    						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
    						   +"</tr>";
    						   
    				$(trTag).insertAfter($("#otChargeDisbursementTRID"));
    				
    				otDChargeCounter++;
    				
    				$("#dChargeTypeID").val("");
    				$("#dChargeID").val("");    				
    			}
    			
    		});
    		
			var editOtDChargeCounter = 500;
    		
    		$("#editotChargeDImgaID").on('click',function(){
    			
    			var dChargeType = $("#editdChargeTypeID").val();
    			var dCharge = $("#editdChargeID").val();
    			
    			var chargeTypeClass = $("#editChargeTypeHiddenID").val();
    			var chargeClass = $("#editChargesHiddenID").val();
    			
    			if(dChargeType == ""){
    				alert("Please enter charge type.");
    			}else if(dCharge == ""){
    				alert("Please enter charges.");
    			}else{
    				
    				var TRID = "editOTChargeDTRID"+editOtDChargeCounter;
    				
    				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
    						   +"<td style='text-align:center;'><input type='text' class='form-control "+chargeTypeClass+"' name='' value='"+dChargeType+"'></td>"
    						   +"<td ><input type='number' class='form-control "+chargeClass+"' name='' id='"+editOtDChargeCounter+"'  value='"+dCharge+"'></td>"
    						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
    						   +"</tr>";
    						   
    				$(trTag).insertAfter($("#editOtChargeDisbursementTRID"));
    				
    				editOtDChargeCounter++;
    				
    				$("#editdChargeTypeID").val("");
    				$("#editdChargeID").val("");    				
    			}
    			
    		});
    		
    		$("#dAddbtnID").on("click",function(){
    			
    			var OTCharge = 0;
    			
    			var chargeDisbursementString = "";
    			
    			var i = 0;
    			
    			$("input[name=dCharge]").each(function(){
    				
    				var chargeType = $($("input[name=dChargeType]").get(i)).val();
    				
    				var charge = $(this).val();
    				
    				OTCharge = parseFloat(parseFloat(OTCharge) + parseFloat(charge));
    				
    				//creating chargeDisbursement string
    				chargeDisbursementString += "$" + chargeType + "==" + charge;
    				
    				i++;
    				
    			});
    			
    			if(chargeDisbursementString.startsWith("\\$")){
    				chargeDisbursementString = chargeDisbursementString.substr(1);
    			}
    			
    			$("#otRateID").val(OTCharge);
    			
    			$("#chargeDisbursementHiddenID").val(chargeDisbursementString);
    			
    			$("#chargeDisbursementModalID").modal("hide");
    			
    		});
    	});
    	
    	function saveEditChargesDisbursement(chargeTYpeClass, ChargeClass, chargeDisbursementHiddenID, rateID){
    		
    		var OTCharge = 0;
			
			var chargeDisbursementString = "";
			
			var i = 0;
			
			$("."+ChargeClass).each(function(){
				
				var chargeType = $($("."+chargeTYpeClass).get(i)).val();
				
				var charge = $(this).val();
				
				OTCharge = parseFloat(parseFloat(OTCharge) + parseFloat(charge));
				
				//creating chargeDisbursement string
				chargeDisbursementString += "$" + chargeType + "==" + charge;
				
				i++;
				
			});
			
			if(chargeDisbursementString.startsWith("\\$")){
				chargeDisbursementString = chargeDisbursementString.substr(1);
			}
			
			$("#"+rateID).val(OTCharge);
			
			$("#"+chargeDisbursementHiddenID).val(chargeDisbursementString);
			
			$("#editChargeDisbursementModalID").modal("hide");
    		
    	}
    	
    	function appyDatePicker(className){
    		 $('.'+className).datetimepicker({
                 //language:  'fr',
                 format: "dd-mm-yyyy hh:ii"
             });
    	}
    	
    	function retrieveIPDTariffRate(chargeID){
    		
    		var roomTypeID = $("#roomTypeID").val();
			
			if(roomTypeID == ""){
				alert("No room type selected. Kindly select room type.");
				
				$("#itemNameHiddenID").val("");
				$("#tariffQntyID").val("1");
				$("#tarrifRateID").val("");
				$("#tariffAmtID").val("");
			}else if(chargeID == ""){
				alert("No tariff item selected. Kindly select tariff item.");
				
				$("#itemNameHiddenID").val("");
				$("#tariffQntyID").val("1");
				$("#tarrifRateID").val("");
				$("#tariffAmtID").val("");
			}else{
				$.ajax({
	                url : "RetrieveIPDTarrifRate",
	                type : "POST",
	                data : {
	                	roomTypeID : roomTypeID,
	                	tarrifChargeID : chargeID
	                }, 
	                dataType : "json",
	                success : function(jsonResponse) {
	                	var charge = jsonResponse["charges"];
	                	var quantity = $("#tariffQntyID").val();
	                	var amount = 0;
	                	$("#tarrifRateID").val(charge);
	                	
	                	if(quantity == ""){
	                		quantity = 0;
	                	}
	                	
	                	if(charge == ""){
	                		charge = 0;
	                	}
	                	
	                	var amount = parseFloat(parseFloat(charge) * parseFloat(quantity));
	                	
	                	$("#tariffAmtID").val(charge);
	                	$("#itemNameHiddenID").val(jsonResponse["itemName"]);
	                },
	                error: function(errorMsg){
	                	alert("ERROR: "+errorMsg);
	                }
	        	});
			}
    	}
    	
    	
		function retrieveIPDConsultantRate(consultantChargeID){
    		
    		var roomTypeID = $("#roomTypeID").val();
			
			if(roomTypeID == ""){
				alert("No room type selected. Kindly select room type.");
				
				$("#doctorNameHiddenID").val("");
				$("#consultantQntyID").val("1");
				$("#consultantRateID").val("");
				$("#consultantAmtID").val("");
			}else if(consultantChargeID == ""){
				alert("No doctor name selected. Kindly select doctor name.");
				
				$("#doctorNameHiddenID").val("");
				$("#consultantQntyID").val("1");
				$("#consultantRateID").val("");
				$("#consultantAmtID").val("");
			}else{
				$.ajax({
	                url : "RetrieveIPDConsultantRate",
	                type : "POST",
	                data : {
	                	roomTypeID : roomTypeID,
	                	consultantChargeID : consultantChargeID
	                }, 
	                dataType : "json",
	                success : function(jsonResponse) {
	                	var charge = jsonResponse["charges"];
	                	var quantity = $("#consultantQntyID").val();
	                	var amount = 0;
	                	$("#consultantRateID").val(charge);
	                	
	                	if(quantity == ""){
	                		quantity = 0;
	                	}
	                	
	                	if(charge == ""){
	                		charge = 0;
	                	}
	                	
	                	var amount = parseFloat(parseFloat(charge) * parseFloat(quantity));
	                	
	                	$("#consultantAmtID").val(charge);
	                	$("#doctorNameHiddenID").val(jsonResponse["doctorName"]);
	                },
	                error: function(errorMsg){
	                	alert("ERROR: "+errorMsg);
	                }
	        	});
			}
    	}
    	
    	function removeTR(trID){
    		if(confirm("Are you sure you want to delete this row?")){
				$("#"+trID).remove();	
			}
    	}
    	
    	function calculateAmount(rateVal, qntyVal, amtID){
    		if(rateVal == ""){
    			rateVal = 0;
    		}
    		
    		if(qntyVal == ""){
    			qntyVal = 0;
    		}
    		
    		var amount = parseFloat(parseFloat(rateVal) * parseFloat(qntyVal));
    		
    		$("#"+amtID).val(amount);
    	}
    </script>

    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
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
    	
    	 $('.otDateTimeClass').datetimepicker({
             //language:  'fr',
             format: "dd-mm-yyyy hh:ii"
         });
      });
   
    </script>
    
  </body>
</html>