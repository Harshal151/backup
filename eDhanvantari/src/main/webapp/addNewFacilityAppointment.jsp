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
<%@ taglib uri="/struts-tags" prefix="s" %>    
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

    <title>Add Appointment | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <script type="text/javascript">
      function windowOpen(){
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
        document.location="Welcome.jsp";
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
			$('a#pmID').remove();	//To disable leftmenu options patient manag
			$('a#ivntID').remove();	//To disable leftmenu options invt mang
			$('a#admID').remove();	//To disable leftmenu options adminstr
			$('a#repID').remove();	//To disable leftmenu options Reports$('a#profPicID').remove();	//To disable profile pic at the right top corner
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
	
	<!-- remove up down arrow from input type number -->
	
	<style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
	</style>


	<%
		String submitClinic = (String) request.getAttribute("submitClinic");
	%>
	
	<!-- For getting current date -->
	
	<%

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = format.format(date);
		
		SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");
		String currentTime = format1.format(date);
		
		String[] timeArray = currentTime.split(":");
		
		String currentHour = timeArray[0];
		
		String currentMinute = timeArray[1];
		
	%>
	
	<!-- Ends -->
    
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
    
    
    <%
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
    
		HashMap<Integer, String> clinicianMap = daoInf.retrieveClinicianList(form.getClinicID());
		
		List<String> hoursList = registrationDAOinf.retrieveStartHourListByClinicStartEndHour(form.getClinicID());
		
	%>
    
    
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
    
    int roomTypeID = (Integer) request.getAttribute("roomTypeID");
    
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
    
    <%
		String aptDateCheck = (String) request.getAttribute("aptDateCheck");
	%>
	
	<%
		String appointmentDuration = (String) request.getAttribute("apptDuration");
		System.out.println("Appointment duration values from JSP is ::: "+ appointmentDuration);
	%>
    
    <!-- Time change function -->
    <script type="text/javascript">

		function changeTimeTo(){
			var fromHH = document.getElementById("fromHH").value;
			var fromMM = document.getElementById("fromMM").value;
			var fromMMAMPM = document.getElementById("fromMMAMPM").value;
	
			var ChangedTime = parseInt(fromMM) + <%=appointmentDuration%>;
	
			if(ChangedTime == 60){
				fromHH = parseInt(fromHH) + 1;
	
				if(fromHH == 13 && fromMMAMPM == "am"){
					fromHH = 1;
					fromMMAMPM = "pm";
				}else if(fromHH == 13 && fromMMAMPM == "pm"){
					fromHH = 1;
					fromMMAMPM = "am";
				}
				
				ChangedTime = "0";
	
				document.getElementById("toHH").value = fromHH;
				document.getElementById("toMM").value = ChangedTime;
				document.getElementById("toMMAMPM").value = fromMMAMPM;
				
			}else if(ChangedTime > 60){
				fromHH = parseInt(fromHH) + 1;
	
				if(fromHH == 13 && fromMMAMPM == "am"){
					fromHH = 1;
					fromMMAMPM = "pm";
				}else if(fromHH == 13 && fromMMAMPM == "pm"){
					fromHH = 1;
					fromMMAMPM = "am";
				}
				
				ChangedTime = parseInt(ChangedTime) - 60;
	
				document.getElementById("toHH").value = fromHH;
				document.getElementById("toMM").value = ChangedTime;
				document.getElementById("toMMAMPM").value = fromMMAMPM;
			}else{
				document.getElementById("toHH").value = fromHH;
				document.getElementById("toMM").value = ChangedTime;
				document.getElementById("toMMAMPM").value = fromMMAMPM;
			}	
	
			timeCheck(fromHH,fromMM,fromMMAMPM);
		}
	
	</script>
    <!-- Ends -->
    
    <!-- Time check function to compare selected time with clinic start and end time -->
    
    <script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function timeCheck(fromHH,fromMM,fromMMAMPM) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release1.length; i++) {
						
						document.getElementById("timeCheckErrorID").innerHTML = array.Release1[i].CheckMsg;
	
						if(array.Release1[i].CheckMsg == "" 
							|| array.Release1[i].CheckMsg == "undefined"){
							
							//Enabling submit button
							//document.getElementById("apptSubmit").disabled = false;
							$('#send').attr('disabled', false);
	
						}else{
							
							//disabling submit button
							//document.getElementById("apptSubmit").disabled = true;
							$('#send').attr('disabled', true);
							
						}
						
					}
				}
			};
			xmlhttp.open("GET", "AppointmentTimeCheck?fromHH="
					+ fromHH + "&fromMM=" + fromMM + "&fromMMAMPM=" + fromMMAMPM, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
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
    
    
    <script type="text/javascript">
    
    	function getTimeVal(hh, mm, ID){
    		var finalTime = hh + ":" + mm + ":00";
    		
    		$("#"+ID+"").val(finalTime);
    	}
    
    </script>
    
    
    <%
    	HashMap<Integer, String> map = daoInf.retrieveVisitTypesList(form.getClinicID());
    
   	 	HashMap<Integer, String> roomMap = daoInf.retrieveRoomTypeListByPracticeID(form.getPracticeID(), form.getClinicID());
    %>
    
    
    <!-- Retrieving appointment duration based on visit type ID -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getAppointmentDuration(visitTypeID, startTime) {
		if(startTime == "" || startTime == null)
			{	
				alert("Select Appointment Time From");
				$("#apptTypeID1").val("");
			}else
			{
				xmlhttp.onreadystatechange = function() {
					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

						var array = JSON.parse(xmlhttp.responseText);
						
						var appointmentDuration = 0;

						for ( var i = 0; i < array.Release.length; i++) {
							
							appointmentDuration = array.Release[i].visitDuration;

						}
						
						//Calculation end time
				        var timeArr = startTime.split(":");

				        /* var minute = parseInt(timeArr[1]) + parseInt(appointmentDuration); */
				        var minute = parseInt(timeArr[1]);

				        var hour = timeArr[0];

				        var second = timeArr[2];

				        /* if(hour == "23" && minute >= "60"){
				        	hour = 00;
				        	minute = parseInt(minute) - 60;
				        }else if(minute>= "60"){
				        	hour = parseInt(hour) + 1;
				        	minute = parseInt(minute) - 60;
				        }

				        if(minute.toString().length == 1){
				        	minute = "0" + minute;
				        } */
				        
						var finalMinute = hour+":"+minute;
				        
				        //alert(appointmentDuration);
				        
				        var newFinalTime = addMinutes(finalMinute, appointmentDuration);
				        
				        $('#apptEndTimeID1').val(newFinalTime + ":" + second);

					}
				};
				xmlhttp.open("GET", "GetAppointmentDuration?visitTypeID="+visitTypeID , true);
				xmlhttp.send();
			}
		
	}
	
	//function to add minutes to time
	function addMinutes(time, minsToAdd) {
	  function z(n){
	    return (n<10? '0':'') + n;
	  }
	  var bits = time.split(':');
	  var mins = bits[0]*60 + (+bits[1]) + (+minsToAdd);
	
	  return z(mins%(24*60)/60 | 0) + ':' + z(mins%60);  
	} 
	</script>
	
	
	<script type="text/javascript">
	
		function submitForm(){
			
			var hhID1 = $("#hhID1").val();
			
			var mmID1 = $("#mmID1").val();
			
			var apptTypeID1 = $("#apptTypeID1").val();
			
			var apptEntTime = $("#apptEndTimeID1").val();
			
			if(hhID1 == "" || mmID1 == ""){
				$("#timeFontID").show(500);
				
				$("#apptFormID").submit(function(e){
			        return false;
			    });
			}else if(apptTypeID1 == "000"){
				
				$("#timeFontID").hide(500);
				
				$("#timeToFontTID").hide(500);
				
				$("#aptTypeFontID").show(500);
				
				$("#apptFormID").submit(function(e){
					return false;
			    });
			}else if(apptEntTime == ""){
				
				$("#timeFontID").hide(500);
				
				$("#timeToFontTID").show(500);
				
				$("#aptTypeFontID").hide(500);
				
				$("#apptFormID").submit(function(e){
					return false;
			    });
			}else{
				//alert('alert');
				$("#timeFontID").hide(500);
				$("#aptTypeFontID").hide(500);
				$("#timeToFontTID").hide(500);
				$("#apptSubmit").attr("disabled", true);
				
				$("#apptFormID").unbind('submit').submit();
				
				
			}
			
		}
		
		//Show email div function
		function showEmailDIv(checkVal){
			
			if(checkVal == "No"){
				$("#emailDivID").hide(1000);
				$("#emailID").removeAttr("required");
			}else{
				$("#emailDivID").show(1000);
				$("#emailID").attr("required","required");
			}
		}
	
	</script>
    
    <!-- Ends -->
    
    
<script type="text/javascript">
  
    document.addEventListener('DOMContentLoaded', function() {
    	var dateNow = new Date();
		$('.clinic_start').datetimepicker({
	        language:  'en',
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			disabledTimeIntervals: [ [ moment().hour(0), moment().hour(8).minutes(30) ], [ moment().hour(20).minutes(30), moment().hour(24) ] ],
			forceParse: 1,
			defaultDate:dateNow
	    });
		
		
		
	});
    
    function addReadonly(){
    	$("#apptStartTimeID1").attr("readonly","readonly");
    }
    
    function removeReadonly(){
    	$("#apptStartTimeID1").removeAttr("readonly");
    }
    
</script>
    
    <!-- make an input field accept only letters-->
<script type="text/javascript">
  
  function validate(value, InputID) {
	 
	  var patt1 = new RegExp("[a-zA-Z]+(\s[a-zA-Z]+)?$", "g");
	
	    if (value == "") {
	        $("#"+InputID).val("");
	        return false;
	    }else if (!/^[a-zA-Z\s]*$/g.test(value)) {
	        alert("Name should not contains any special characters and numbers.");
	        $("#"+InputID).val("");
	        return false;
	    }else if (patt1.test(value)) {
	    	return true;
	    }else if(/\s+\S*$/.test(value)){
	    	$("#"+InputID).val($("#"+InputID).val().trim());
	    	return true;
	    }else{
	    	alert("Name should not contains any special characters and numbers.");
	    	$("#"+InputID).val("");
	    	return false;
	    }
	    
	}
  
  </script>
  
  </head>

  <body class="nav-md" onload="myFun();">
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
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
          <a href="Logout"><button type="button" style="width:25%" class="btn btn-primary">Logout</button></a>
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
                        <h3>ADD NEW APPOINTMENT</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                  <div class="x_content">
                  
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
		              </div>

                    <form class="form-horizontal form-label-left" action="AddNewAppointmentForBooking" onsubmit="return showLoadingImg();" name="apptFormID" id="apptFormID" method="POST" style="margin-top:20px;" >
                      
                      <s:iterator value="patientList" var="patientAppointmentList">
                      
                      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
                      
                      <input type="hidden" name="nextApptTaken" id="nextApptTaken" value="0">
                      
                      <input type="hidden" name="" id="bookRoomTypeID" value="<%=roomTypeID%>">
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Patient Name">Patient Name<span class="required">*</span>
                        </label>
                        	<div class="col-md-2 col-sm-2 col-xs-12">
	                          <input  class="form-control" name="firstName" id= "firstNameID" style="text-transform: capitalize;" value="<s:property value="firstName"/>" placeholder="First Name" required="required" type="text" onblur="validate(this.value, 'firstNameID');">
	                        </div>
	                        <div class="col-md-2 col-sm-2 col-xs-12">
	                          <input  class="form-control" name="middleName" id= "middleNameID" style="text-transform: capitalize;" value="<s:property value="middleName"/>" placeholder="Middle Name" type="text" onblur="validate(this.value, 'middleNameID');">
	                        </div>
	                        <div class="col-md-2 col-sm-2 col-xs-12">
	                          <input class="form-control" name="lastName" id= "lastNameID" style="text-transform: capitalize;" value="<s:property value="lastName"/>" placeholder="Last Name" required="required" type="text" onblur="validate(this.value, 'lastNameID');">
	                        </div>
                      </div>
                      
                       <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Gender">Gender<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <s:radio list="#{'Male':'Male','Female':'Female','Child':'Child','Unknown':'Unknown'}" id="genderID" name="gender" required="required" ></s:radio>
                        </div>
                      </div>
                      
                      <div class="item form-group">
							<label for="User Type" class="control-label col-md-3 col-sm-4">Date Of Birth<%-- <span class="required">*</span> --%></label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="text" name="dateOfBirth" value="<s:property value="dateOfBirth"/>" placeholder="Date Of Birth" id = "single_cal4" value="<s:property value='dob'/>" class="form-control" >
							</div>
					</div>
											
					<div class="item form-group">
							<label for="User Type" class="control-label col-md-3 col-sm-4">Age</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="number" name="age" placeholder="Age" id="ageID" value="<s:property value='age'/>" class="form-control" required="required" readonly="readonly">
							</div>
					</div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="City">Mobile No<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="number" name="mobile" value="<s:property value="mobile"/>" required="required" placeholder="Mobile No." class="form-control"  onKeyPress="if(this.value.length==10) return false;"  aria-describedby="inputGroupSuccess1Status">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Blood Group</label>
                        <div class="col-md-4 col-sm-4 col-xs-12">
                          <s:select list="#{'A':'A','B':'B','O':'O','AB':'AB'}" id="bloodGroupID" name="bloodGroup" class="form-control" headerKey="" headerValue="Blood Group"></s:select>
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <s:select list="#{'+ve':'+ve','-ve':'-ve'}" name="rhFactor" id="rhFactorID" class="form-control" headerKey="" headerValue="Rh"></s:select>
                        </div>
                      </div>
                      
                      <div class="item form-group">
		                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Email">Email</label>
		                 <div class="col-md-4 col-sm-4 col-xs-6"  id="emailDivID">
		                     <input type="email" name="emailID" id="emailID" value="<s:property value='emailID'/>" placeholder="Email Address" class="form-control">
		                 </div>
		              </div>
		              
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="City">Registration No.
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" name="" readonly="readonly" value="<s:property value="registrationNo"/>" placeholder="Registration No." required="required" class="form-control">
                          <input type="hidden" name="registrationNo" readonly="readonly" value="<s:property value="medicalRegNo"/>" placeholder="Medical Registration No." class="form-control">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Appointment Date">Appointment From Date<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" class="form-control" name="appointmentDate" required="required" id="single_cal3" autocomplete="off" value="<s:property value="appointmentDate"/>" placeholder="Appointment From Date" aria-describedby="inputSuccess2Status3">
                          <font id="startDateFontID" style="display:none;color: red;margin-top: 5px;font-size: 15px;">Start date should be less than or equals to end date</font>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Appointment Date">Appointment To Date<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" class="form-control" name="endDate" required="required" autocomplete="off" id="single_cal31" value="<s:property value="endDate"/>" placeholder="Appointment To Date" aria-describedby="inputSuccess2Status3">
                          <font id="endDateFontID" style="display:none;color: red;margin-top: 5px;font-size: 15px;">End date should be greater than or equals to start date</font>
                        </div>
                      </div>
		              
		              <!-- <div style="color:red;margin-bottom: 10px;" id="timeCheckErrorID" align="center"></div>-->
		              
		              <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Appointment Type">Appointment Type<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <select id="apptTypeID1" name="visitTypeID" class="form-control" required="required" >
        					<option value="">Select Appt. Type</option>
        				
						<%
							for(Integer key: map.keySet()){
						%>
							<option value="<%=key%>"><%=map.get(key)%></option>
						<%
							}
						%>
						
						</select>
						<font id="aptTypeFontID" style="display:none;color: red;margin-top: 5px;font-size: 15px;">Please select Appointment Type</font>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Room Type">Room Type<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12" id="roomTypeDivID">
                          <select id="roomTypeID1" name="roomTypeID" class="form-control" required="required" >
        					<option value="">Select Room Type</option>
        				
						<%
							for(Integer key: roomMap.keySet()){
								
								if(roomTypeID == key){
						%>
							<option value="<%=key%>" selected><%=roomMap.get(key)%></option>
						<%
								}else{
						%>
							<option value="<%=key%>"><%=roomMap.get(key)%></option>
						<%
								}
							}
						%>
						
						</select>
						<font id="roomTypeFontID" style="display:none;color: red;margin-top: 5px;font-size: 15px;">Please select Room Type</font>
                        </div>
                      </div>
		              
		              <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Status">Status
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12"> 
                          <input type="text" name="aptStatus" readonly="readonly" value="Booked" placeholder="Status" class="form-control">
                        </div>
                      </div>

 					</s:iterator>
 
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-12" align="center">
                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
                          <button id="apptSubmit" type="submit" class="btn btn-success ">Add Appointment</button>
                        </div>
                      </div>
                    </form>
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

    <script>
    function myFun(){
      $(document).ready(function() {

    	  $("input[type=number]").on("keydown",function(e){
      		if (e.which === 38 || e.which === 40) {
      			e.preventDefault();
      		}
      	});
     
        $('#single_cal3').daterangepicker({
        	singleDatePicker: true,
          	calender_style: "picker_3"
        }, function(start, end, label) {
          //console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#single_cal3').on('apply.daterangepicker', function(ev, picker) {
            var fit_end_time = $('#single_cal31').val();
            var fit_start_time = $(this).val();
            
            if(fit_start_time == "" || fit_end_time == ""){
            	return false;
            } else {
            	
            	fit_start_time = fit_start_time.split('-');
            	fit_end_time = fit_end_time.split('-');

                var new_start_date = new Date(fit_start_time[2],fit_start_time[1],fit_start_time[0]);
                var new_end_date = new Date(fit_end_time[2],fit_end_time[1],fit_end_time[0]);

                if(new_end_date >= new_start_date) {
                	$("#startDateFontID").hide()
                	//retrieveRoomType($(this).val(), $('#single_cal31').val())
                }else{
                	$('#single_cal3').val("")
                	$("#startDateFontID").show()
                }
            	
            }
        });
        
        $('#single_cal31').daterangepicker({
        	singleDatePicker: true,
          	calender_style: "picker_3"
        }, function(start, end, label) {
          //console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#single_cal31').on('apply.daterangepicker', function(ev, picker) {
            var fit_end_time = $(this).val();
            var fit_start_time = $('#single_cal3').val();
            
            if(fit_start_time == "" || fit_end_time == ""){
            	return false;
            } else {
            	
            	fit_start_time = fit_start_time.split('-');
            	fit_end_time = fit_end_time.split('-');

                var new_start_date = new Date(fit_start_time[2],fit_start_time[1],fit_start_time[0]);
                var new_end_date = new Date(fit_end_time[2],fit_end_time[1],fit_end_time[0]);

                if(new_end_date >= new_start_date) {
                	$("#endDateFontID").hide()
                	//retrieveRoomType($('#single_cal3').val(), $(this).val())
                }else{
                	$('#single_cal31').val("")
                	$("#endDateFontID").show()
                }
            	
            }
        });
        
        
       /*  $('#single_cal3').on('hide.daterangepicker', function(ev, picker) {
			var selectedDate = $(this).val();
			
			var dateArr = selectedDate.split("-");
			
			var d1 = new Date();
			var dd = d1.getDate();
			var mm = d1.getMonth()+1; 
			if(dd<10) 
			{
			    dd='0'+dd;
			} 

			if(mm<10) 
			{
			    mm='0'+mm;
			}
			var yyyy = d1.getFullYear();
			
			var today = yyyy + "-" + mm + "-" + dd;
    		
    		var calendarDate = new Date(dateArr[2]+"-"+dateArr[1]+"-"+dateArr[0]);
    		
    		//console.log("Dates: "+calendarDate+"--"+today);
    		
    		//alert("Dates: "+new Date(today).getTime()+"--"+calendarDate.getTime());
    		
    		 if(new Date(today).getTime() <= calendarDate.getTime()){
    			return true;
    		}else{
    			alert("Selected date must be greater than current date.");
    			$(this).val("");
    		} 
    		return true;
    	}); */
        
        
      });
    }
    
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function retrieveRoomType(startDate, endDate){
		
		var patientID = $("#patientID").val()
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				
				var roomTypeID = $("#bookRoomTypeID").val()

				var array = JSON.parse(xmlhttp.responseText);
				
				var selectTag = '<select id="roomTypeID1" name="roomTypeID" class="form-control" required="required" ><option value="">Select Room Type</option>';

				for ( var i = 0; i < array.Release.length; i++) {
					
					if(roomTypeID == array.Release[i].id){
						selectTag += "<option value='"+array.Release[i].id+"' selected>"+array.Release[i].name +"</option>";
					}else{
						selectTag += "<option value='"+array.Release[i].id+"'>"+array.Release[i].name +"</option>";	
					}
					
				}
				
				selectTag += "</select>";
				
				$("#roomTypeID1").html(selectTag);
			}
		};
		xmlhttp.open("GET", "RetrieveRoomTypeForBooking?startDate="
				+ startDate + "&endDate=" + endDate + "&patientID="+patientID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
    </script>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});

    	 $('#single_cal4').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_2",
            showDropdowns: true,
            minYear: 1901,
            maxYear: parseInt(moment().format('YYYY'),10)
          }, function(start, end, label) {
            var years = moment().diff(start, 'years');
            $("#ageID").val(years);
          });
        
    	 /*$('#single_cal4').on('hide.daterangepicker', function(ev, picker) {
			var selectedDate = $(this).val();
			
			var dateArr = selectedDate.split("-");
			
			var d1 = new Date();
			var dd = d1.getDate();
			var mm = d1.getMonth()+1; 
			if(dd<10) 
			{
			    dd='0'+dd;
			} 

			if(mm<10) 
			{
			    mm='0'+mm;
			}
			var yyyy = d1.getFullYear();
			
			var today = yyyy + "-" + mm + "-" + dd;
    		
    		var calendarDate = new Date(dateArr[2]+"-"+dateArr[1]+"-"+dateArr[0]);
    		
    		console.log("Dates: "+calendarDate+"--"+today);
    		//alert("Dates: "+new Date(today).getTime()+"--"+calendarDate.getTime());
    		
    	}); */
        
      });
    </script>
	<!-- /Datepicker -->	
    
  </body>
</html>