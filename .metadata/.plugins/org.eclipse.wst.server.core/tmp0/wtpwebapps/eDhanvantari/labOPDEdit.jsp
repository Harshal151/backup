<%@page import="com.edhanvantari.form.PatientForm"%>
<%@page import="com.edhanvantari.daoImpl.ClinicDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.ClinicDAOInf"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.edhanvantari.daoImpl.RegistrationDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.RegistrationDAOinf"%>
<%@page import="com.edhanvantari.daoImpl.PatientDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.PatientDAOInf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.edhanvantari.daoImpl.LoginDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.LoginDAOInf"%>
<%@page import="com.edhanvantari.util.ConfigurationUtil"%>
<%@page import="com.edhanvantari.util.ActivityStatus"%>
<%@page import="com.edhanvantari.util.ConfigXMLUtil"%>
<%@page import="com.edhanvantari.form.LoginForm"%>
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

    <title>View Lab Visit | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
     <link rel="stylesheet" href="build/css/jquery-ui.css"> 
     
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
     <link  rel="stylesheet" type="text/css" href="build/css/jquery.multiselect.css" />
     
    <link  rel="stylesheet" type="text/css" href="build/css/bootstrap-multiselect.css" />
     
     <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
   <!-- <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" /> -->
    
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  
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
        }
      
      var finalarr= [];
    </script>
    
    <style type="text/css">
		.dojoComboBoxOptions{
			top: 564px;
			left: 531px;
		}
		
		.datatable-responsive_filter{
			width:100%;
		}
		
	</style>

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
		
	</style>
	
	<script type="text/javascript">  
	
		function showLoadingImg(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();		
		} 
		
		
		function showLoadingImg1(){	
			var sampleID = $("#sampleID").val();
			
			var mdDoctorID = $("#mdDoctorID").val();
			
			var labTestAutoID = $("#labTestAutoID").val(); 
			
			console.log("inside........"+sampleID+"--"+mdDoctorID+"--"+labTestAutoID);
			
			if(sampleID == null || sampleID ==""){
				alert("Please add sampleID field");
				return false;
			}else if(mdDoctorID ==""){
				alert("Please Select MD Doctor");
				return false;
			}else{
				
				$('html, body').animate({
				    scrollTop: $('body').offset().top
				}, 1000);
					
				$('body').css('background', '#FCFAF5');
				$(".container").css("opacity","0.2");
				$("#loader").show();
	
				document.location="EditLabVisit";
				 
				return true;
			}
			
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

		boolean resultCheck = (Boolean) request.getAttribute("resultCheck");
		
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
    
    	List<PatientForm>labTestList = (List<PatientForm>) request.getAttribute("labTestList");
    	
    	HashMap<String, String>	labTestValueListNew = (HashMap<String, String>) request.getAttribute("labTestValueListNew");
    	HashMap<String, String>	labTestValueListNew1 = (HashMap<String, String>) request.getAttribute("labTestValueListNew1");
    	int labTestListLength = labTestList.size();
    	
	%>
    
	
	<script type="text/javascript">
	var popupWindow;
	function viewPopUp(url) {
		popupWindow = window.open(
			url,
			'popUpWindow','height=600,width=800,left=10,top=10,resizable=no,scrollbars=yes,addressbar=no,toolbar=no,menubar=no,location=0,directories=no,status=yes');
	
	}
	</script>
	
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
    
	<script type="text/javascript">
	
		function confirmDelete(prescID,visitID){
			if (confirm("Are you sure you want to delete prescription?")) {
				deletePrescription(prescID, visitID);
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
		
		if(hourInteger > 12){
			ampm = "pm";
			hour = String.valueOf(hourInteger - 12);
			System.out.println("Hour value is ::: "+hour);
			System.out.println("AMPM value is ::: "+ampm);
		}else if(hourInteger == 12){
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
		
		//Retrieving appointment duration from Calendar table based on clinicID
		PatientDAOInf patientDAOInf = new PatientDAOImpl();
		
		String appointmentDuration = patientDAOInf.retrieveApptDurationFromClinicID(form.getVisitTypeID());
		
		int minutesWithDuration = minuteInt + Integer.parseInt(appointmentDuration);
		
		int hourInt = Integer.parseInt(hour);
		
		String toAmPm = ampm;
		
		System.out.println("To Hour:"+String.valueOf(hourInt));
		System.out.println("To Moniute:"+String.valueOf(minutesWithDuration));
		System.out.println("To AMPM:"+toAmPm);
		
		if(minutesWithDuration > 60){
			
			if(hourInt > 12){
	
				minutesWithDuration = minutesWithDuration - 60;
	
				hourInt = hourInt - 12;
	
				toAmPm = "pm";
				
			}else if(hourInt == 12){
	
				minutesWithDuration = minutesWithDuration - 60;
	
				toAmPm = "pm";
				
			}else{
	
				minutesWithDuration = minutesWithDuration - 60;
				
			}
			
		}else if (minutesWithDuration == 60){
			
			if(hourInt > 12){
	
				minutesWithDuration = 0;
	
				hourInt = hourInt - 12;
	
				toAmPm = "pm";
				
			}else if(hourInt == 12){
	
				minutesWithDuration = 0;
	
				toAmPm = "pm";
				
			}else{
	
				minutesWithDuration = 0;
				
			}
			
		}
		
		if((minutesWithDuration >= 0) && (minutesWithDuration < 5)){
			
			minutesWithDuration = 0;
			
		}else if((minutesWithDuration >= 5) && (minutesWithDuration < 10)){
			
			minutesWithDuration = 5;
			
		}else if((minutesWithDuration >= 10) && (minutesWithDuration < 15)){
			
			minutesWithDuration = 10;
			
		}else if((minutesWithDuration >= 15) && (minutesWithDuration < 20)){
			
			minutesWithDuration = 15;
			
		}else if((minutesWithDuration >= 20) && (minutesWithDuration < 25)){
			
			minutesWithDuration = 20;
			
		}else if((minutesWithDuration >= 25) && (minutesWithDuration < 30)){
			
			minutesWithDuration = 25;
			
		}else if((minutesWithDuration >= 30) && (minutesWithDuration < 35)){
			
			minutesWithDuration = 30;
			
		}else if((minutesWithDuration >= 35) && (minutesWithDuration < 40)){
			
			minutesWithDuration = 35;
			
		}else if((minutesWithDuration >= 40) && (minutesWithDuration < 45)){
			
			minutesWithDuration = 40;
			
		}else if((minutesWithDuration >= 45) && (minutesWithDuration < 50)){
			
			minutesWithDuration = 45;
			
		}else if((minutesWithDuration >= 50) && (minutesWithDuration < 55)){
			
			minutesWithDuration = 50;
			
		}else{
			
			minutesWithDuration = 55;
			
		}
	%>
	
	<%
		String appointmentCheck = (String) request.getAttribute("appointmentCheck");
		System.out.println("appointmentCheck :: " +appointmentCheck);
		if(appointmentCheck == null || appointmentCheck == ""){
			appointmentCheck = "";
		}else{
			
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
   		System.out.println("lastEneteredVisitList :: " +lastEneteredVisitList);
    	if(lastEneteredVisitList == null || lastEneteredVisitList == ""){
    		lastEneteredVisitList = "dummy";
    	}
    	
    	String billCheck = (String) request.getAttribute("billCheck");
    	
	%>
    
    <!-- Retrieve tabs by practice ID -->
    
    <%
    	
    	HashMap<String, String> ValueMap = new HashMap<String, String>();
    	//,Report,Medical Certificate,Referral Letter
    	ValueMap.put("manager","Lab Visit,Billing");
	    ValueMap.put("clinician","Lab Visit,Billing");
	    ValueMap.put("billDesk","Prescription,Billing");
	    ValueMap.put("compounder","Prescription,Billing");
    
    	String tabs = ValueMap.get(form.getUserType());
    
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
    
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("Lab Visit", "visit");
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
	
	
	<%-- <%
	
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
	%>--%>
	
	
	<%
		int apptID = (Integer) request.getAttribute("apptID");
		System.out.println("apptID :: " +apptID);
	%>
	
	<script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		//setInterval(function(){$('#weekCalendar').fullCalendar('refetchEvents')}, 30000);
    		setInterval(function(){$('body').css('padding-right','0px')}, 1000);
    		
    		/* $('#collectionTimeID').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    }); */
    		
    		setTimeout(function(){
    			//alert('hii');
    			$('#collectionTimeID').attr("value",$("#apptStartTimeID1Hidden").val());
    		},1000);
    		
    		
        });
    </script>
    
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
    			
    		}else{
    			$("#cashDetailID").hide(1000);
    			
    			$("#cashPaidID").removeAttr("required");
    			
    			$("#cashPaidID").val("");
    			$("#cashToReturnID").val("");
    		}
    	}
    	
		function displayCardDiv(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
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
    	
    	function displayChequeDetailsDivOther(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
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
    	
	function changeCashToReturn(cashPaid, advPayment){
			
			if(cashPaid == ""){
				cashPaid = 0;
			}
			
			if(advPayment == ""){
				advPayment = 0;
			}
			
			var cashTOReturn = parseFloat(cashPaid) - parseFloat(advPayment);
			
			if(cashTOReturn.toString().includes(".")){
				var array = cashTOReturn.toString().split(".");
				
				if(array[1].length > 2 ){
					cashTOReturn = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			$("#cashToReturnID").val(cashTOReturn);
			
		}
		
		function changeDuesCashToReturn(cashPaid, netAmount){
			
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
			
			$("#cashToReturnDuesID").val(cashTOReturn);
			
		}
		
	function changeBalancePayment(advPayment,netAmount){
			
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
			$("#balPaymentID").val(netAmount);
		}
    	
    </script>
    
    <%
		String paymentType = (String) request.getAttribute("paymentType");
    	System.out.println("paymentType :: " +paymentType);
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
					
					String idValues = paymentMap.get(arr[i]);
					
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
	%>
	
	<%
		if(!pdfOutFIleName1.equals("dummy")){
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
	
		
		function openAddNewModal(){
			$("#addRefByModalID").modal('show');
		}
		
		function addRefDoc(){
			if($("#referringDoctNameID").val() == ""){
				alert("Enter doctor name.");
			}else{
				 var formData = new FormData($("#refByFormID")[0]);
					//alert(formData);
				addRefDoc1(formData);
			}
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function addRefDoc1(formData) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var msg = "";

					for ( var i = 0; i < array.Release.length; i++) {
						msg = array.Release[i].MSG;
					}
					
					if(msg.includes("success")){
						$("#addRefByModalID").modal('hide');
						
						alert(msg);
						
						retrieveReferringDoctorList();
					}else{
						
						$("#addRefByModalID").modal('hide');
						
						alert(msg);
					}
				}
			};
			xmlhttp.open("POST", "AddReferringDoctor");
			xmlhttp.send(formData);
		}
		
		function retrieveReferringDoctorList() {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var msg = "";
					
					var selectTag = "<select name='referredBy' id='doctName' class='form-control'>"+
									"<option value=''>Select Referred By</option>";

					for ( var i = 0; i < array.Release.length; i++) {
						msg = array.Release[i].MSG;
						
						selectTag += "<option value='"+array.Release[i].doctName+"'>"+array.Release[i].doctName+"</option>";
					}
					
					selectTag += "</select>";
					
					$("#refDocDivID").html(selectTag);
					
				}
			};
			xmlhttp.open("GET", "RetrieveReferringDoctorList", true);
			xmlhttp.send();
		}
		
		function disableSubmitBtn(btnID){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

    		$("#"+btnID).attr('disabled',true);
    		return true;
    	}
		
		function calculateValues(test,inputID){
			
			if(test == "Indirect Bilirubin"){
				var totalBilirubin = $("input[name='observationsValue'][data-title='Total Bilirubin']").val();
				
				if(totalBilirubin == ""){
					totalBilirubin = "0";
				}
				
				var directBilirubin = $("input[name='observationsValue'][data-title='Direct Bilirubin']").val();
				
				if(directBilirubin == ""){
					directBilirubin = "0";
				}
				
				var indirectBilirubin = parseFloat(parseFloat(totalBilirubin) - parseFloat(directBilirubin));
				
				if(isNaN(indirectBilirubin)){
					indirectBilirubin = 0;
				}else{
					indirectBilirubin = Math.round(indirectBilirubin * 100) / 100;
				}
				
				$("#"+inputID).val(indirectBilirubin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Serum Globulin"){
				var totalProteins = $("input[name='observationsValue'][data-title='Total Proteins']").val();
				
				if(totalProteins == ""){
					totalProteins = "0";
				}
				
				var serumAlbumin = $("input[name='observationsValue'][data-title='Serum Albumin']").val();
				
				if(serumAlbumin == ""){
					serumAlbumin = "0";
				}
				
				var serumGlobulin = parseFloat(parseFloat(totalProteins) - parseFloat(serumAlbumin));
				
				if(isNaN(serumGlobulin)){
					serumGlobulin = 0;
				}else{
					serumGlobulin = Math.round(serumGlobulin * 100) / 100;
				}
				
				$("#"+inputID).val(serumGlobulin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Total Cholesterol/HDL Ratio"){
				
				var serumChol = $("input[name='observationsValue'][data-title='Serum Cholesterol']").val();
				
				if(serumChol == ""){
					serumChol = "0";
				}
				
				var hdlChol = $("input[name='observationsValue'][data-title='H.D.L. Cholesterol']").val();
				
				if(hdlChol == ""){
					hdlChol = "0";
				}
				
				var choleRatio = parseFloat(parseFloat(serumChol) / parseFloat(hdlChol));
				
				if(isNaN(choleRatio)){
					choleRatio = 0;
				}else{
					choleRatio = Math.round(choleRatio * 100) / 100;
				}
				
				$("#"+inputID).val(choleRatio);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.V."){
				
				var pcvVal = $("input[name='observationsValue'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var rbcCount = $("input[name='observationsValue'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mcvVal = parseFloat(parseFloat(pcvVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mcvVal)){
					mcvVal = 0;
				}else{
					mcvVal = Math.round(mcvVal * 100) / 100;
				}
				
				$("#"+inputID).val(mcvVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H."){
				
				var hemoVal = $("input[name='observationsValue'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var rbcCount = $("input[name='observationsValue'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mchVal = parseFloat(parseFloat(hemoVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mchVal)){
					mchVal = 0;
				}else{
					mchVal = Math.round(mchVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H.C."){
				
				var hemoVal = $("input[name='observationsValue'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var pcvVal = $("input[name='observationsValue'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var mchcVal = parseFloat((parseFloat(hemoVal) * 100) / parseFloat(pcvVal));
				
				if(isNaN(mchcVal)){
					mchcVal = 0;
				}else{
					mchcVal = Math.round(mchcVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchcVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Prothrombin Ratio"){
				
				var controlTimeVal = $("input[name='observationsValue'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsValue'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				console.log("controlTimeVal.."+controlTimeVal+"..patiemtTimeVal.."+patiemtTimeVal);
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				console.log("..PR.."+PR);
				
				if(isNaN(PR)){
					PR = 0;
				}else{
					PR = Math.round(PR * 100) / 100;
				}
				
				console.log("..PR....."+PR);
				
				$("#"+inputID).val(PR);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "I.N.R. (International Normalised Ratio)"){
				
				var controlTimeVal = $("input[name='observationsValue'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsValue'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				var isiVal = $("input[name='observationsValue'][data-title='I.S.I. Value of the prothrombin used']").val();
				
				if(isiVal == ""){
					isiVal = "0";
				}
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				var INRVal = parseFloat(Math.pow(PR,isiVal));
				
				if(isNaN(INRVal)){
					PR = 0;
				}
				
				$("#"+inputID).val(INRVal.toFixed(2));
				$("#"+inputID).attr("readonly","readonly");
				
			}
			
		}
	
		function calculateValues1(test,inputID){
			
			if(test == "Indirect Bilirubin"){
				var totalBilirubin = $("input[name='observationsVal'][data-title='Total Bilirubin']").val();
				
				if(totalBilirubin == ""){
					totalBilirubin = "0";
				}
				
				var directBilirubin = $("input[name='observationsVal'][data-title='Direct Bilirubin']").val();
				
				if(directBilirubin == ""){
					directBilirubin = "0";
				}
				
				var indirectBilirubin = parseFloat(parseFloat(totalBilirubin) - parseFloat(directBilirubin));
				
				if(isNaN(indirectBilirubin)){
					indirectBilirubin = 0;
				}else{
					indirectBilirubin = Math.round(indirectBilirubin * 100) / 100;
				}
				
				$("#"+inputID).val(indirectBilirubin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Serum Globulin"){
				var totalProteins = $("input[name='observationsVal'][data-title='Total Proteins']").val();
				
				if(totalProteins == ""){
					totalProteins = "0";
				}
				
				var serumAlbumin = $("input[name='observationsVal'][data-title='Serum Albumin']").val();
				
				if(serumAlbumin == ""){
					serumAlbumin = "0";
				}
				
				var serumGlobulin = parseFloat(parseFloat(totalProteins) - parseFloat(serumAlbumin));
				
				if(isNaN(serumGlobulin)){
					serumGlobulin = 0;
				}else{
					serumGlobulin = Math.round(serumGlobulin * 100) / 100;
				}
				
				$("#"+inputID).val(serumGlobulin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Total Cholesterol/HDL Ratio"){
				
				var serumChol = $("input[name='observationsVal'][data-title='Serum Cholesterol']").val();
				
				if(serumChol == ""){
					serumChol = "0";
				}
				
				var hdlChol = $("input[name='observationsVal'][data-title='H.D.L. Cholesterol']").val();
				
				if(hdlChol == ""){
					hdlChol = "0";
				}
				
				var choleRatio = parseFloat(parseFloat(serumChol) / parseFloat(hdlChol));
				
				if(isNaN(choleRatio)){
					choleRatio = 0;
				}else{
					choleRatio = Math.round(choleRatio * 100) / 100;
				}
				
				$("#"+inputID).val(choleRatio);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.V."){
				
				var pcvVal = $("input[name='observationsVal'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var rbcCount = $("input[name='observationsVal'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mcvVal = parseFloat(parseFloat(pcvVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mcvVal)){
					mcvVal = 0;
				}else{
					mcvVal = Math.round(mcvVal * 100) / 100;
				}
				
				$("#"+inputID).val(mcvVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H."){
				
				var hemoVal = $("input[name='observationsVal'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var rbcCount = $("input[name='observationsVal'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mchVal = parseFloat(parseFloat(hemoVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mchVal)){
					mchVal = 0;
				}else{
					mchVal = Math.round(mchVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H.C."){
				
				var hemoVal = $("input[name='observationsVal'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var pcvVal = $("input[name='observationsVal'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var mchcVal = parseFloat((parseFloat(hemoVal) * 100) / parseFloat(pcvVal));
				
				if(isNaN(mchcVal)){
					mchcVal = 0;
				}else{
					mchcVal = Math.round(mchcVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchcVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Prothrombin Ratio"){
				
				var controlTimeVal = $("input[name='observationsVal'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsVal'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				console.log("controlTimeVal.."+controlTimeVal+"..patiemtTimeVal.."+patiemtTimeVal);
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				console.log("..PR.."+PR);
				
				if(isNaN(PR)){
					PR = 0;
				}else{
					PR = Math.round(PR * 100) / 100;
				}
				
				console.log("..PR....."+PR);
				
				$("#"+inputID).val(PR);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "I.N.R. (International Normalised Ratio)"){
				
				var controlTimeVal = $("input[name='observationsVal'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsVal'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				var isiVal = $("input[name='observationsVal'][data-title='I.S.I. Value of the prothrombin used']").val();
				
				if(isiVal == ""){
					isiVal = "0";
				}
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				var INRVal = parseFloat(Math.pow(PR,isiVal));
				
				if(isNaN(INRVal)){
					PR = 0;
				}
				
				$("#"+inputID).val(INRVal.toFixed(2));
				$("#"+inputID).attr("readonly","readonly");
				
			}
			
		}
		
	</script>
	
	<script type="text/javascript">
	/* $( document ).ready(function() {
		var srno = 1; 	
		$('.checkboxClass').each(function () {
	           if (this.checked) {
		        	   myString = $(this).val(); 
		               myString = myString.substring(myString.indexOf('$')+1);
		               
		                var optionValue = myString.split('$');
		       		
			       		var trTag = "";
			       		
			       		trTag = "<input type='hidden' name='isGroupVal' value='"+optionValue[6]+"'><input type='hidden' name='normalVal' value='"+optionValue[1]+"'><input type='hidden' name='groupNameVal' value='"+optionValue[4]+"'><input type='hidden' name='groupRateVal' value='"+optionValue[3]+"'><input type='hidden' name='rateVal' value='"+optionValue[2]+"'><input type='hidden' name='subGroupVal' value='"+optionValue[5]+"'>";
						
			       		$(".test"+srno).append(trTag);
			       		srno = srno+1;
		           }
		});
	}); */
	/* function retrieveValues(value, text){
				
		console.log("value: "+value+"text: "+text);
			
		var rate = value.split('$');
		
		if(value == text){
			//console.log("inside if");
			retriveGroupValues(text);
		}else{
			//console.log("inside else");
				
			passTestToSelect(value+"$0", rate[2], 'totalRateID');
		}
	} */
	
	 var CounterVal = 1;
	 
	 var srNO = <%=labTestListLength%>;
	 
	 /* function retrieveLabDefaultValueList(selectVal, totalRateID, length){
		
		var optionValue = selectVal.split('$');
		
		var trTag = "";
			
		var trID = "newTestTRID"+CounterVal;
		
		var testName = optionValue[0];
		
		if(testName.includes('.')){
		 	testName = optionValue[0].replaceAll(".", "_");
		}else if(testName.includes(" ")){
			testName = optionValue[0].replaceAll(" ", "_");
		}
		
		srNO++;
		
		trTag = "<tr id ='"+testName+optionValue[6]+"TRID' style='font-size: 14px;'>"
				+"<td style='text-align: center;'><input type='hidden' name='isGroupVal' value='"+optionValue[6]+"'>"+srNO+"</td>"
				+"<td><input type='hidden' name='test' value='"+optionValue[0]+"'><input type='hidden' name='normalVal' value='"+optionValue[1]+"'><input type='hidden' name='groupNameVal' value='"+optionValue[4]+"'>"+optionValue[0]+"</td>"
				+"<td><input type='hidden' name='groupRateVal' value='"+optionValue[3]+"'><input type='hidden' name='rateVal' value='"+optionValue[2]+"'><input type='hidden' name='subGroupVal' value='"+optionValue[5]+"'>"+optionValue[1]+"</td><td>";
		
		var tdTag = "<select name='observationsVal' class='form-control otherSelectClass' id='testValueID"+trID+"' style='display: block;'>"
					+"<option value='0'>Select Value</option>";
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					 console.log("inside retrieveLabDefaultValueList: "+selectVal);
					 
					for ( var i = 0; i < array.Release.length; i++) {
						
						console.log("inside for....: "+array.Release[i].check);
						var check = array.Release[i].check;
						var testVal = array.Release[i].testVal;
	
						tdTag +="<option value='"+testVal+"'>"+testVal+"</option>";
					}
					
					if(check == 1){
						
						trTag += tdTag+"</select></td></tr>";
						
						console.log("inside check if......"+trTag);
						
						$(trTag).insertBefore($('#labDefaultTRID'));
						
					}else if(check == 0){
	
						trTag +="<input type='text' class='form-control' data-title='"+optionValue[0]+"' id='testValueID"+trID+"' name='observationsVal' onfocus='calculateValues1('"+optionValue[0]+"', this.id);' >"
						
							  +"</td></tr>"; 
							  
					    $(trTag).insertBefore($('#labDefaultTRID'));
						
						console.log("inside check else......"+trTag);
					}
					
					CounterVal++;
					
		     		console.log("outside loop....: "+CounterVal);
		     		
				}
			};
			xmlhttp.open("GET", "RetrieveLabDefaultValueList?searchTestName="+ optionValue[0], true);
			xmlhttp.send();
	 	
	 }
	
	 function passTestToSelect(selectVal, rateVal, totalRateID, length){
			
			var optionValue = selectVal.split('$');
				
			var rate = $("#"+totalRateID).val();
			
			var finalRate = 0;
		
			if(optionValue[6] == "1"){
					
				$('#newLabTestID').append("<label class='checkbox'><input type='checkbox' class='checkboxClass' value='0$"+selectVal+"' checked='checked' onclick='removeTestToSelect(this.value, \"totalRateID\" );'> "+optionValue[4]+" - "+optionValue[3]+"</label></a></li>")
			}else{
					
				$('#newLabTestID1').append("<label class='checkbox'><input type='checkbox' class='checkboxClass' value='0$"+selectVal+"' checked='checked' onclick='removeTestToSelect(this.value, \"totalRateID\" );'> "+optionValue[0]+" - "+optionValue[2]+"</label></a></li>")
			}
				
			finalRate = parseInt(rate) + parseInt(rateVal);
				
			//console.log("finalRate : "+selectVal+"--"+finalRate+"--"+rate);
				
			$("#"+totalRateID).val(finalRate);
			
			retrieveLabDefaultValueList(selectVal, totalRateID, length);
		} */
		
		function removeTestToSelect(selectVal, totalRateID){
			
			var optionValue = selectVal.split('$');
		
			var rateValue = 0;
			
			if(optionValue[7] == "1"){
				
				rateValue = optionValue[4];
			}else{
				rateValue = optionValue[3];
			}
			
			var rate = $("#"+totalRateID).val();
			
			var finalRate = 0;
			
			var stringArr = $("#labTestHiddenID").val();
			
			var finalStringVal = "";
			
			var stringAppend = "";
			
			var stringValue= "";
			
			finalRate = parseInt(rate) - parseInt(rateValue);
			 
			console.log("finalRateNew : "+finalRate+"--"+rate+"--"+rateValue);
			 
			$("#"+totalRateID).val(finalRate);
			
			console.log("selectVal: "+selectVal);
			 
			finalRate = parseInt(rate) - parseInt(rateValue);
				 
			$("#"+totalRateID).val(finalRate);
			
			if(optionValue[0] == "0"){
				console.log("inside if : "+optionValue[1]+optionValue[7]+"TRID");
				
				$("label:has(:checkbox:not(:checked))").remove()
				
				var testName = optionValue[1];
				
				if(testName.includes('.')){
				 	testName = optionValue[1].replaceAll(".", "_");
				}else if(testName.includes(" ")){
					testName = optionValue[1].replaceAll(" ", "_");
				}
				
				$("#"+testName+optionValue[7]+"TRID").remove();
			}else{
				
				$("label:has(:checkbox:not(:checked))").remove()
				
				removeLabTest(optionValue[0], optionValue[1], optionValue[7]);
			}
			
		}
		
	</script>
    
    <!-- Retrieve values details by groupName -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retriveGroupValues(groupName) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					var groupValues = "";
					
					var groupRate = 0;
					
					var groupCheck =0;
					
					var length = array.Release.length;
					
					for ( var i = 0; i < array.Release.length; i++) {
						groupValues = array.Release[i].groupValues;
		
						groupCheck++;
						
						if(groupCheck == length){
							groupRate = array.Release[i].groupRate
						}
						const myArray = groupValues.split("===");
						groupValues = myArray[0]+"$1"+"*"+myArray[1]+"$1";
						
						passTestToSelect(groupValues, groupRate, 'totalRateID', length);
					}
	
				}
			};
			xmlhttp.open("GET", "RetrieveGroupValues?groupName="
					+ groupName, true);
			xmlhttp.send();
		}
		
		function removeLabTest(testValID, test, isGroup){
			
			console.log("test: "+testValID+"-"+test+"-"+isGroup)
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
		
					for ( var i = 0; i < array.Release.length; i++) {

						var check = array.Release[i].check;
					}
	
					if(check == 1){
					
						console.log("TR iD: "+test+isGroup+"TRID");
						$('#myTableRow').remove();
						
						var testName = test;
						
						if(testName.includes('.')){
						 	testName = test.replaceAll(".", "_");
						}else if(testName.includes(" ")){
							testName = test.replaceAll(" ", "_");
						}
						
						$("#"+testName+isGroup+"TRID").remove();
					}
				}
			};
			xmlhttp.open("GET", "RemoveLabInvestigationTest?labTestID="+ testValID, true);
			xmlhttp.send();
			
		}
				
	</script>
	<!-- ENds -->
    
  <sx:head/>
  
  </head>
  
  <body class="nav-md" >
  
  <!-- To show loading icon while page is loading -->
   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader" >
  
  <!-- Add new Referred by modal -->
  
  <div id="addRefByModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
      	<div class="modal-header">
      		<center><h5><b>Add New Referring Doctor</b></h5></center>
      	</div>
        <div class="modal-body">
          
          <form class="form-horizontal form-label-left" id="refByFormID" >
	                      
	          <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Doctor Name">Doctor Name <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctName" id="referringDoctNameID" required="required" placeholder="Doctor Name" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Specialization">Specialization
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctSpecialisation" placeholder="Specialization" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Clinic Name">Clinic Name
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctClinicName" placeholder="Clinic Name" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Clinic Address">Clinic Address
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <textarea name="referringDoctClinicAddres" placeholder="Clinic Address" class="form-control"></textarea>
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Phone">Phone
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="number" name="referringDoctPhone" placeholder="Phone" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Email">Email
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="email" name="referringDoctEmail" placeholder="Email" class="form-control">
	                        </div>
	                      </div>
	          
          </form>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="addRefDoc();">Add</button>
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
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                  	<%
                  	if(daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%= form.getFullName() %>
	                
	                <%
	              		}else{
	                %>
	                
	                 <img src="<%= daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
	                
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
                        <h3>VIEW EXISTING VISIT</h3>
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
			         
			         <h4 style=""><b>Visit For <s:property value="firstName"/>&nbsp;<s:property value="lastName"/>&nbsp;(<s:property value="medicalRegNo"/>)</b></h4>
		              
		              <div class="ln_solid"></div>
			        
					 <%--  <ul id="myTab1" class="nav nav-tabs">
					  
					  
					  	<%
					  	//System.out.println("..."+tabs);
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
		              			//System.out.println("hiii");
		              		}
		              	%>
					  </ul> --%>
			    
			        
				 <div id="myTabContent1" class="tab-content">
				 
				 	<!-- Visit div -->
			    <!-- <div class="tab-pane fade in active" id="visit"> -->
				    <form class="form-horizontal form-label-left" action="EditLabVisit" name="visitrForm" id="visitrForm" method="POST" onsubmit="return disableSubmitBtn('sendBtnID');" style="margin-top:20px;">
	
				      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%= apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
				      
						  <div class="row" style="margin-top:15px;">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"  >
							    		<table border="1" width="100%">
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Name: </td>
							    				<td colspan="3"> <input type="text" style="border:none;cursor:not-allowed;background: transparent;"  class="form-control" readonly="readonly" value="<s:property value="firstName"/> <s:property value="middleName"/> <s:property value="lastName"/>"  name="" placeholder="Patient Name" autofocus=""></td>
							    				<td style="padding-left:10px;font-size:14px; font-weight: bold;">Age: </td>
							    				<td><input type="number" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" name="age" value="<s:property value="age"/>" placeholder="Age" autofocus=""> </td>
							    			</tr>
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;">Sex: </td>
							    				<td colspan="3">
							    				<input type="text" name="gender" style="border:none;cursor:not-allowed;background: transparent;" class="form-control" readonly="readonly" value="<s:property value="gender"/>" placeholder="Gender" autofocus=""></td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Phone Number: </td>
							    				<td colspan="3"><input type="number" name="mobile" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control"  value="<s:property value="mobile"/>" autofocus=""></td>
							    			</tr>
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Email: </td>
							    				<td colspan="5"> <input type="text" name="emailID" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" value="<s:property value="emailID"/>" autofocus=""></td>
							    			</tr>		
							    		</table>
							 </div>
							 </div>
							 
							 <div class="ln_solid"></div>
								
						    <div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Medical registration number: </font>
						    	</div>
						    	
						    	<div class="col-md-2">
						    		<input class="form-control" name="registrationNo" readonly="readonly" value="<s:property value="registrationNo"/>" placeholder="Medical registration number">
						    	</div>
						    	<div class="col-md-1"></div>
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Sample ID<span class="required">*</span> </font>
						    	</div>
						    	<div class="col-md-2">
						    		<input type="text" class="form-control" name="sampleID" id="sampleID" required="required" value="<s:property value="sampleID"/>" placeholder="Enter Sample ID here">
						    	</div>
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Collection Date<span class="required">*</span></font>
						    	</div>
						    	<div class="col-md-2">
						    		<input type="text" class="form-control" name="visitDate" required="required" value="<s:property value="firstVisitDate"/>" id="single_cal3" placeholder="Visit Date" aria-describedby="inputSuccess2Status3">
						    	</div>
						    	<div class="col-md-1"></div>
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Collection Time<span class="required">*</span></font>
						    	</div>
						    	<div class="col-md-2">
						    		<input type="hidden" id="apptStartTimeID1Hidden" value="<s:property value="visitFromTime"/>">
			        					<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
						                    <input class="form-control" size="16" required="required" id="collectionTimeID" name="visitFromTime" type="text" value="" readonly>
						                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
					                	</div>
						    	</div>
							</div>
							
	                      	<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Referred By. </font>
						    	</div>
						    	<div class="col-md-2" id="refDocDivID">
						    		<s:select list="doctorList" headerKey="" headerValue="Select Referred By" name="referredBy" id="doctName" class="form-control"></s:select>
						    	</div>
						    	<div class="col-md-1">
						    		<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Add New"
						                   onclick="openAddNewModal();" title="Add New" style="height: 24px;cursor: pointer;" />
						    	</div>
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">MD Doctor<span class="required">*</span> </font>
						    	</div>
						    	<div class="col-md-2">
						    		<s:select list="mdDetailsList" headerKey="" headerValue="Select MD Doctor" name="mdDoctorID" id="mdDoctorID" class="form-control" required="required"></s:select>
						    	</div>
							</div>
						
							<div class="ln_solid"></div>
								
						    <div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Prescribed Tests<span class="required">*</span>: </font>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    	
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10 multiselect" style="margin-left: 1%;">
									<div class="row">
									<s:select list="getGroupTestList" headerKey="" headerValue="" name="groupTest" id="groupTest" class="form-control" onchange = "selectGroupLabTest()"></s:select>	
										<%-- <select name="" class="js-example-placeholder-multiple js-states form-control" id="labTestAutoID" value="" style="width:100%" data-placeholder="Select Fields" tabindex="-1" multiple>
										</select> --%>
									</div><!-- js-example-basic-multiple  onchange="passTestToSelect(this.value, 'totalRateID');"-->
							    	
							    	<div class="" id="newLabTestID" style="margin-left: 0%; margin-top: -1%;">
							    		<input type="hidden" id="labTestHiddenID" class="form-control" name="labTestDetails">
							    		
							    		 <% for(String testValue : labTestValueListNew.keySet()){ %> 
							    			
							    			<label class="checkbox"><input type="checkbox" class="checkboxClass" value="<%= testValue%>" id="<%=labTestValueListNew.get(testValue)%>ID" checked="checked" onclick="removeTestToSelect(this.value, 'totalRateID');"> <%=labTestValueListNew.get(testValue)%></label> 
							    		
							    		<% } %> 
							    		<%-- <s:select list="labTestValueListNew" headerKey="" id="newLabTestID" value="labTestListValues" class="checkboxClass form-control" name="labTestDetails"  multiple="true" onclick="removeTestToSelect(this.value, 'totalRateID');" cssStyle="width: 100%" ></s:select> --%>
							    		
							    	</div>
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10 multiselect" style="margin-left: 1%;">
						    	<div class ="row">
						    	<s:select list="getSingleTestList" headerKey="" headerValue="" name="singleTest" id="singleTest" class="form-control" onchange = "selectGroupLabTest()"></s:select>
									<%-- <select name="" class="js-example-placeholder-multiple1 js-states form-control" id="labTestAutoID1" value="" style="width:100%" data-placeholder="Select Test" tabindex="-1" multiple>
									</select> --%>
									<!-- js-example-basic-multiple  onchange="passTestToSelect(this.value, 'totalRateID');"-->
						    	</div>
						    	<div class ="row">
							    	<div id="newLabTestID1" style="margin-left: 0%; margin-top: -1%;">
							    		<!-- <input type="hidden" id="labTestHiddenID1" class="form-control" name="labTestDetails"> -->
							    		<%-- <s:select list="labTestValueListNew" headerKey="" value="labTestListValues" name="labTestDetails" id="newLabTestID" class="form-control multiCheckboxClass" multiple="true" onclick="removeTestToSelect(this.value, 'totalRateID');"></s:select> --%>
							    		
							    		<% for(String testValue1 : labTestValueListNew1.keySet()){ %> 
							    			
							    			<label class="checkbox"><input type="checkbox" class="checkboxClass" value="<%= testValue1%>" id="<%=labTestValueListNew1.get(testValue1)%>ID1" checked="checked" onclick="removeTestToSelect(this.value, 'totalRateID');"> <%=labTestValueListNew1.get(testValue1)%></label> 
							    		
							    		<% } %>  
							    	</div>
							    </div>
						    	</div>	
						    	
						    	<div class="col-md-2" style="margin-top: -2%;">
						    		<s:if test="%{getTotalRate() == 0}">
							    		Total rate : <input type="number" id="totalRateID" name="totalRate" class="form-control" readonly="readonly" value="0">
							    	</s:if>
									<s:else>
										Total rate : <input type="number" id="totalRateID" name="totalRate" class="form-control" readonly="readonly" value="<s:property value="totalRate"/>">
									</s:else>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								
									<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
			                            <thead>
			
			                                <tr >
			                                    <th>Sr. No.</th>
			                                    <th>Test</th>
			                                    <th>Normal Range</th>
			                                    <th>Observed Value</th>
			                                </tr>
			                             </thead>
			                               
			                            <tbody >
			                            	<%-- <s:iterator value="labTestList"> --%>
			                            	
			                            	<% for(PatientForm form1: labTestList ){ 
			                            	 String testVal = form1.getCBCProfileTest(); 
			                            			 
			                            		if(testVal.contains(".")){
			                            			testVal = form1.getCBCProfileTest().replaceAll(".", "_");
			                            		}else if(testVal.contains(" ")){
			                            			testVal = form1.getCBCProfileTest().replaceAll(" ", "_");
			                            		}
			                            	%>
			                            	
			                            	<%-- <script>finalarr.push(<%=form1.getCBCProfileTest() %>);</script> --%>
			                            		
			                            		<tr id="<%=testVal %><%=form1.getIsGroup() %>TRID" style="font-size: 14px;">
		                            			<td style="text-align: center;"><%=form1.getSrNo() %></td>
		                            			
		                            			<td><%=form1.getCBCProfileTest() %></td>
		                            			
		                            			<td><%=form1.getCBCProfileNormalValue() %>
		                            			<input type="hidden" name="normalVal" value="<%=form1.getCBCProfileNormalValue() %>">
		                            			<div class="test<%=form1.getSrNo() %>"></div>
		                            			<input type="hidden" name="investigationDetails" value="<%=form1.getInvestigationDetailsID() %>">
		                            			</td>
		                            			
		                            		<%	
		                            			List<String>LabDefaultValueList = patientDAOInf.getLabDefaultValueList(form1.getCBCProfileTest());
		                            		%>
		                            			<td>
		                            			<% 
		                            				if(LabDefaultValueList.size()<=0){ %>
		                            					
		                            					<input type="text" class="form-control" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>" name="observationsValue" onfocus="calculateValues('<%=form1.getCBCProfileTest() %>', this.id);" value="<%=form1.getCBCProfileValue() %>">
		                            			
		                            			<%	}else{ %>
		                            			
		                            			<%
		                            					if(LabDefaultValueList.contains(form1.getCBCProfileValue()) || form1.getCBCProfileValue().equals("0.0")){
		                            			%>
		                            			
		                            					<input type="text" class="form-control otherClass" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>Other" style="display: none;" name="" value="0.0">
		                            				
		                            				<select name="observationsValue" class="form-control otherSelectClass" id="testValueID<%=form1.getInvestigationDetailsID() %>" style="display: block;">
		                            				
		                            				<option value="0">Select Value</option>
			                            		
			                            		<% 	for(String values: LabDefaultValueList){ %>
			                            				
			                            				<% if(values.equals(form1.getCBCProfileValue())){ %>
			                            					
			                            					<option value="<%= values %>" selected="selected"><%= values %></option>
			                            				
			                            				<% }else{ %>	
			                            				
			                            					<option value="<%= values %>"><%= values %></option>
			                            				
			                            				<% } %>
			                            		<% } %>
		                            			
			                            		 </select>
		                            			
		                            			<%
		                            					}else{
		                            			%>
		                            			
		                            			<input type="text" class="form-control otherClass" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>Other" style="display: block;" name="observationsValue" value="<%=form1.getCBCProfileValue() %>">
		                            				
		                            				<select name="" class="form-control otherSelectClass" id="testValueID<%=form1.getInvestigationDetailsID() %>" style="display: none;">
		                            				
		                            				<option value="0">Select Value</option>
			                            		
			                            		<% 	for(String values: LabDefaultValueList){ %>
			                            				
			                            				<% if(values.equals(form1.getCBCProfileValue())){ %>
			                            					
			                            					<option value="<%= values %>" selected="selected"><%= values %></option>
			                            				
			                            				<% }else{ %>	
			                            				
			                            					<option value="<%= values %>"><%= values %></option>
			                            				
			                            				<% } %>
			                            		<% } %>
		                            			
			                            		 </select>
		                            			
		                            			<%
		                            					}
		                            			%>
			                            		
		                            		<%  }%>
		                            			 
		                            			</td>
		                            			
		                            		</tr>
		                            		
			                            <%	}%>
			                            <tr id="labDefaultTRID"></tr>
			                            </tbody>
			                        </table>   
								
						    	</div>
							</div>
							
							<div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Conclusion/Comments: </font>
						    	</div>
						    	
							</div>
							
							<div class="row">
						    
						    	<div class="col-md-12">
						    		<s:textarea name="medicalNotes" rows="6" class="form-control"> </s:textarea>
						    	</div>
						    	
							</div>
	                      
	                    
	                      <div class="ln_solid"></div>
	                      
	                      <div class="form-group">
	                        <div class="col-md-12" align="center">
	                          <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                 <button id="sendBtnID" type="submit" class="btn btn-success " >Update</button>
			                 <button id="" type="button" class="btn btn-info " onclick="window.location='DownloadLabTestReport?visitID=<s:property value="visitID"/>&mdDoctorID=<s:property value="mdDoctorID"/>'">Print Report</button>
			                 <%-- <button id="" type="button" class="btn btn-info " onclick="window.location='EmailLabTestReport?visitID=<s:property value="visitID"/>&mdDoctorID=<s:property value="mdDoctorID"/>'" >Email Report</button> --%>
	                        </div>
	                      </div>
				        
				      </form>
			    <!--   </div> -->
			     <!-- End -->
			     
			    <!-- Billing div -->
			     <!-- <div class="tab-pane fade" id="billing" > -->
			      <hr>
			      <form class="form-horizontal form-label-left" novalidate id="genPhyBillID" name="genPhyBillID" onsubmit="return disableSubmitBtn('addBillBtnID');" action="EditLabBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						          <%-- <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5> --%>
						  </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden" name="visitID" id="billingVisitID" value="<s:property value="visitID"/>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
					       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					        <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
                           
                           <s:iterator value="billList">
                           
                           <input type="hidden" name="billingType" value="<s:property value="billingType"/>">
                           <input type="hidden" name="receiptID" value="<s:property value="receiptID"/>">

						<div class="row" style="margin-top: 15px;padding-left: 15px;">
							
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt Date:</font>
							</div>
							<div class="col-md-4">
								<input type="text" class="form-control" id="receiptDateID" name="receiptDate" value="<s:property value="receiptDate"/>">
							</div>
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" value="<s:property value="receiptNo"/>">
							</div>
						</div>

	                   <div class="row" style="margin-top:10px;padding: 15px;">
							      
							      	<table  class="table table-striped table-bordered dt-responsive nowrap">
							      		
							      		<thead>
								
								            <tr style="font-size: 14px;">
								            	<th>Test</th>
								            	<th>Rate</th>
								            	<th></th>
								            </tr>
								        </thead>
								                               
								        <tbody>
								        		<s:iterator value="groupTestList">
								        			<tr style="font-size: 14px;">
								        				<td><s:property value="CBCProfileTest"/>
								        				<input type="hidden" name="test" value="<s:property value="CBCProfileTest"/>">
								        				<input type="hidden" name="billingProdRate" class="billingRateClass" value="<s:property value="rate"/>">
								        				</td>
								        				
								        				<td><s:property value="rate"/></td>
								        				
								        				<td></td>
								        			</tr>
								        		</s:iterator>
								        		
								        		<s:iterator value="prescriptionList">
								        			<tr style="font-size: 14px;">
								        				<td><s:property value="CBCProfileTest"/>
								        				<input type="hidden" name="test" value="<s:property value="CBCProfileTest"/>">
								        				<input type="hidden" name="billingProdRate" class="billingRateClass" value="<s:property value="rate"/>">
								        				</td>
								        				
								        				<td><s:property value="rate"/></td>
								        				<td></td>
								        			</tr>
								        		</s:iterator>
								        	
								        		
								        	<%-- <tr style="font-size: 14px;" id="labTestTRID">
								        		
								        			<td id="labTestTDID">
									        			<select name='' id='testID' class='form-control' onchange='retrieveLabTestRate(this.value);'>
									        				<option value=''>Select Tests</option>
									        			</select>
									        		</td>
									        		
									        		<td>
									        			<input type="number" class="form-control" name="" value="0"  id="testRate">
									        		</td>
									        		
									        		<td style="text-align: center;"><a onclick="addTestRow(testID.value, testRate.value, totalVatID.value);" > 
														<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
															alt="Add Item" title="Add Item" style="margin-top:5px;height:24px;" /></a></td>
								        		
								        		</tr> --%>
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Total Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="totalAmount"/>" class="form-control" name="totalAmount" id="totalAmountID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
							
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Concession, if any</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="checkboxClass form-control" value="<s:property value="totalDiscount"/>" onkeyup="changeNetAmtByDiscount(this.value,totalAmountID.value);" name="totalDiscount" id="totalVatID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Net Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="netAmount"/>" class="form-control" name="netAmount" id="netAmountID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Advance Payment</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" value="<s:property value="advPayment"/>" name="advPayment" id="advPaymentID" onkeyup="changeBalancePayment(advPaymentID.value, netAmountID.value);">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Balance Payment</font>	
								        		</td>
								        		<td>
								        			<input type="number" class="form-control" readonly="readonly" value="<s:property value="balPayment"/>" name="balPayment" id="balPaymentID">
								        		</td>
								        		<td></td>
											</tr>
								        </tbody>
							      	</table>
							      </div>
							      
							      <div class="row" style="padding: 0 15px 0 15px;">
			                    	<div class="col-md-2 col-sm-3 col-md-4" style="padding-top:5px;">
			                    		<font style="font-size: 16px;">Payment Type<span class="required">*</span></font>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="hideChequeDetailsDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeCashID" value="Cash" class="checkboxClass form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Cash</font>
			                    		</div>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeChequeID" value="Cheque" class="checkboxClass form-control">
			                    		</div>
			                    		<div class="col-md-4 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Cheque</font>
			                    		</div>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayCardDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeCardID" value="Credit/Debit Card" class="checkboxClass form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Credit/Debit Card</font>
			                    		</div>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivOther(this);" style="height: 25px;box-shadow: none;" id="paymentTypeOtherID" value="Other" class="checkboxClass form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Other</font>
			                    		</div>
			                    	</div>
			                    </div>
			                    
			                   <%--  <div id="cashDetailID" style="padding: 15px;display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-2">
				                    		<font style="font-size: 16px;">Cash Paid</font>
				                    	</div>
				                    	<div class="col-md-2">
					                        <input type="text" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, advPaymentID.value);" value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
				                    	</div>
				                    	<div class="col-md-2" align="right">
				                    		<font style="font-size: 16px;">Cash To Return</font>
				                    	</div>
				                    	<div class="col-md-2">
				                    		<input  class="form-control" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>"  placeholder="Cash To Return" type="number">
				                    	</div>
				                    </div>
				                    
			                    </div> --%>
			                    
			                    <div id="chequeDetailID" style="padding: 15px; display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Cheque Issued By</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" id="chequeIssuedByID" name="chequeIssuedBy" value="<s:property value="chequeIssuedBy"/>" placeholder="Cheque Issued By">
				                    	</div>
				                    	<div class="col-md-3" align="right">
				                    		<font style="font-size: 16px;">Cheque No.</font>
				                    	</div>
				                    	<div class="col-md-3">
				                    		<input  class="form-control" name="chequeNo" id="chequeNoID" value="<s:property value="chequeNo"/>"  placeholder="Cheque No." type="text">
				                    	</div>
				                    </div>
				                    
				                    <div class="row" style="margin-top:15px;">
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Bank Name</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" id="chequeBankNameID" value="<s:property value="chequeBankName"/>" name="chequeBankName" placeholder="Bank Name">
				                    	</div>
				                    	<div class="col-md-3" align="right">
				                    		<font style="font-size: 16px;">Branch</font>
				                    	</div>
				                    	<div class="col-md-3">
				                    		<input  class="form-control" name="chequeBankBranch" id="chequeBankBranchID" value="<s:property value="chequeBankBranch"/>" placeholder="Branch" type="text">
				                    	</div>
				                    </div>
				                    
				                    <div class="row" style="margin-top:15px;">
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Date</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" name="chequeDate" value="<s:property value="chequeDate"/>" id="single_cal311" placeholder="Date">
				                    	</div>
				                    	<div class="col-md-3" align="right">
				                    		<font style="font-size: 16px;">Amount</font>
				                    	</div>
				                    	<div class="col-md-3">
				                    		<input  class="form-control" name="chequeAmt" id="chequeAmtID" value="<s:property value="chequeAmt"/>"  placeholder="Amount" type="number">
				                    	</div>
				                    </div>
			                    
			                    </div>
			                    
			                    <div id="cardDetailID" style="padding: 15px; display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
			                    	<div class="row">
			                    
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Amount</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="number" class="form-control" value="<s:property value="cardAmount"/>" id="cardAmountID" name="cardAmount" placeholder="Amount">
				                    	</div>
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Card No.</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" value="<s:property value="cardMobileNo"/>" id="cardMobileNoID" name="cardMobileNo" placeholder="Card No.">
				                    	</div>
				                    	
				                    </div>
				                    
				                    <div class="row" style="margin-top: 15px;">
			                    
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Mobile No.</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="number" class="form-control" value="<s:property value="cMobileNo"/>" id="cMobileNoID"  name="cMobileNo" placeholder="Mobile No.">
				                    	</div>
				                    	
				                    </div>
			                    
			                    </div>
			                    
			                    <div id="otherDetailID" style="padding: 15px; display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Other Type</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" id="otherTypeID" name="otherType" value="<s:property value="otherType"/>" placeholder="Enter Other payment here">
				                    	</div>
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Amount</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="number" class="form-control" id="otherTypeAmountID" name="otherAmount" value="<s:property value="otherAmount"/>" placeholder="Enter Other amount here">
				                    	</div>
				                    </div>
			                    </div>
			     	
			     			<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12" align="center">
		                        <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		                        
			               	 	<button class="btn btn-success" type="submit" id="addBillBtnID" >Update</button>
			                </div>
		                    </div>
		              </s:iterator>
			      </form>
			      
			      <div class="ln_solid"></div>
			      
			   <!--  </div> -->
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
   <%--  <script src="vendors/jquery/dist/jquery.min.js"></script> --%>
    <!-- Bootstrap -->
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="vendors/nprogress/nprogress.js"></script>
    <!-- validator -->
    <script src="vendors/validator/validator.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script> --%>
	
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    
    <!-- Datatables -->
    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <%-- <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script> --%>
    
    <script src="build/js/jquery-ui.js"></script>
     
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
     <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>

	<script type="text/javascript" src="build/js/bootstrap-multiselect.js" charset="UTF-8"></script>
    
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
   
	   /*For lab test autocompletor  */	
	   	
		var patientID = $("#patientID").val();
		
		var visitID = $("#visitID").val();
		 
		$(document).ready(function(){
			
			var billCheck = <%=billCheck%>;
			console.log("billCheck: "+typeof billCheck);
			if(billCheck ==true){
			 setTimeout(function(){
					console.log("insiide");
					$(".select2-search__field").prop('disabled', true)
					$(".select2-search__field").css('cursor', "not-allowed")
					
					$(".checkboxClass").attr("disabled", true);	
			 
			 },1500);		 

			}
			
			/* $(".js-example-placeholder-multiple").select2({
				includeSelectAllOption: true,
				  ajax: { 
				   url: "SearchLabTestNameList?patientID="+patientID+"&groupCheck=1",
				   type: "GET",
				   dataType: 'json',
				   delay: 250,
				   data: function (params) {
					return {
				    	searchTestName: params.term // search term
				    };
				   },
				   processResults: function (data) {
					  var arr = []
					  var arr1 = []
					  var mergeArr = []
					  
					  var counter = 0
					  var counter1 = 0
					  
	                   $.each(data, function (index, value) {
	                	   
	                	   if(finalarr.includes(index)){
	            			   return true;
	            		   }
	                	   
	                	  if(counter == 0){
	                    	arr.push({
	                      		text: "Profile Test", isNew: true,
	                  			children: [{
	                  			    id: index,
	                  			    text: index,
	                  			    newOption: true
	                  			}]
	                        })
	                      }else{
	                    	arr.push({
	                      		//text: "Profile Test", isNew: true,
	                  			children: [{
		                  			id: index,
		                  			text: index,
		                  			newOption: true
	                  			}]
	                         })
	                      }
	                    	
	                    	counter++;
	                      
	                      
	                   })
	                        
	                   for(var i=0; i<arr.length; i++){
	                	   mergeArr.push(arr[i]);
	                   }
					 
					  for(var j=0; j<arr1.length; j++){
	               	   mergeArr.push(arr1[j]);
	                  }
					 
	                  console.log('mergeArr =>' , mergeArr[0].id, 'mergeArr1 =>', mergeArr[0].text);
					  return {
						   results: mergeArr
					   }; 
	               },
	               
				   cache: true
				  },
				  escapeMarkup: function (markup) { return markup; },
		          minimumInputLength: 1
		           
			}).on('select2:select', function(e) {
				
				var data = e.params.data;
			   
				console.log('select_val: ', data.text, 'data: ', data.id)
				
				finalarr.push(data.text);
				
				retrieveValues(data.id, data.text);
				//$("#select2-selection__choice").remove()
			});
			
			
			$(".js-example-placeholder-multiple1").select2({
				includeSelectAllOption: true,
				  ajax: { 
				   url: "SearchLabTestNameList?patientID="+patientID+"&groupCheck=0",
				   type: "GET",
				   dataType: 'json',
				   delay: 250,
				   data: function (params) {
					return {
				    	searchTestName: params.term // search term
				    };
				   },
				   processResults: function (data) {
					  var arr = []
					  var arr1 = []
					  var mergeArr = []
					  
					  var counter = 0
					  var counter1 = 0
					  
	                   $.each(data, function (index, value) {
	                	   
	                	   
	                	   $.each(value, function (index, value) {
	                		  
	                		   if(finalarr.includes(value)){
	                			   return true;
	                		   }
	                		   
	                		   if(counter1 ==0){
	                			   arr1.push({
	                                   
	                       			text: "Single Test", isNew: true,
	                       			children: [{
	   	                    			id: index,
	   	                    			text: value,
	   	                    			newOption: true
	                       			}]
	                               })
	                		   }else{
	                			   arr1.push({
	                                   
	                       			//text: "Single Test", isNew: true,
	                       			children: [{
	   	                    			id: index,
	   	                    			text: value,
	   	                    			newOption: true
	                       			}]
	                               })
	                		   }
	                		  
	                		   counter1++;
	                       })  
	                   })
	                        
	                   for(var i=0; i<arr.length; i++){
	                	   mergeArr.push(arr[i]);
	                   }
					 
					  for(var j=0; j<arr1.length; j++){
	               	   mergeArr.push(arr1[j]);
	                  }
					 
	                  console.log('mergeArr =>' , mergeArr[0].id, 'mergeArr1 =>', mergeArr[0].text);
					  return {
						   results: mergeArr
					   }; 
	               },
	               
				   cache: true
				  },
				  escapeMarkup: function (markup) { return markup; },
		          minimumInputLength: 1
		           
			}).on('select2:select', function(e) {
				
				var data = e.params.data;
			   
				console.log('select_val: ', data.text, 'data: ', data.id)
				
				finalarr.push(data.text);
				
				retrieveValues(data.id, data.text);
				//$("#select2-selection__choice").remove()
			}); */
			
		});
	
	</script>

    <script>
   
    document.addEventListener('DOMContentLoaded', function() {

        $('#single_cal3').daterangepicker({
          singleDatePicker: true,
          calender_style: "picker_3"
        }, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#single_cal311').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
        $('#receiptDateID').datetimepicker({
	        language:  'en',
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
		
			format: 'dd-mm-yyyy hh:ii:ss'
	    });
        
        $("input[name=observationsValue], .otherClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var inputID = $(this).attr("id");
        		
        		if($(this).attr("id").includes("Other")){
        			$("#"+$(this).attr("id")).toggle();
        			
        			var selectID = inputID.substr(0,inputID.length - 5);
        			
					$("#"+inputID).removeAttr("name");
        			
        			$("#"+selectID).attr("name","observationsValue");
        			
        			$("#"+selectID).toggle();
        			$("#"+selectID).focus();
        			
        		}else{
        			$(this).val("NA");	
        		}
        	}
        });
        
        $("select[name=observationsValue], .otherSelectClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var selectID = $(this).attr("id");
        		
        		var otherID = selectID+"Other";
        		
        		$("#"+otherID).attr("name","observationsValue");
        		$("#"+otherID).toggle();
        		$("#"+otherID).focus();
        		$("#"+selectID).toggle();
        		
        		$("#"+selectID).removeAttr("name");
        	}
        });
        
        
        $("input[name=observationsVal], .otherClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var inputID = $(this).attr("id");
        		
        		if($(this).attr("id").includes("Other")){
        			$("#"+$(this).attr("id")).toggle();
        			
        			var selectID = inputID.substr(0,inputID.length - 5);
        			
					$("#"+inputID).removeAttr("name");
        			
        			$("#"+selectID).attr("name","observationsVal");
        			
        			$("#"+selectID).toggle();
        			$("#"+selectID).focus();
        			
        		}else{
        			$(this).val("NA");	
        		}
        	}
        });
        
        $("select[name=observationsVal], .otherSelectClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var selectID = $(this).attr("id");
        		
        		var otherID = selectID+"Other";
        		
        		$("#"+otherID).attr("name","observationsVal");
        		$("#"+otherID).toggle();
        		$("#"+otherID).focus();
        		$("#"+selectID).toggle();
        		
        		$("#"+selectID).removeAttr("name");
        		
        	}
        });
        
	var visitID = $("#billingVisitID").val();
        
        console.log("..visitID.."+visitID);
        
        xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var msg = "";
				
				var selectTag = "<select name='' id='testID' class='form-control' onchange='retrieveLabTestRate(this.value);'>"+
								"<option value=''>Select Tests</option>";

				for ( var i = 0; i < array.Release.length; i++) {
					msg = array.Release[i].MSG;
					
					selectTag += "<option value='"+array.Release[i].TestID+"="+array.Release[i].TestRate+"'>"+array.Release[i].TestName+"</option>";
				}
				
				selectTag += "</select>";
				
				$("#labTestTDID").html(selectTag);
				
			}
		};
		xmlhttp.open("GET", "RetrieveLabTestByVisitID?visitID="+visitID, true);
		xmlhttp.send();
        
      });
    
    function retrieveLabTestRate(testValue){
    	var array1 = testValue.split("=");
    	
    	var testID = array1[0];
    	
    	var testRate = array1[1];
    	
    	$("#testRate").val(testRate);
    }
    
    var testCounter = 1;
    
    function addTestRow(testValue, testRate, concessionRate){
    	
    	if(testValue == ""){
    		
    		alert("Please select test.");
    		
    	}else{
    		
    		if(concessionRate == ""){
        		concessionRate = 0;
        	}
        	
    		var itemRate = 0;
        	
        	$(".billingRateClass").each(function(){
        		
        		itemRate = parseFloat(itemRate + parseFloat($(this).val()));
        		
        	});
        	
        	console.log("..itemRate.."+itemRate);
        	
        	var testName = $("#testID option:selected").text().trim();
        	
        	var testValArray = testValue.split("=");
        	
    		var testID = testValArray[0];
        	
        	var testRate = testValArray[1];
        	
        	var trID = "newTestTRID"+testCounter;
        	var tdID = "newTestTDID"+testCounter;
        	
    		var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
    		"<td>"+testName+"<input type='hidden'  name='testName' value='"+testName+"'></td>"+
    		"<td>"+testRate+"<input type='hidden' class='billingRateClass' name='testRate' value='"+testRate+"'></td>"+
    		"<td style='text-align: center;'><img src='images/delete_icon_1.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\"" + trID +"\",\""+testRate+"\",\""+concessionRate+"\",\""+testValue+"\",\""+testName+"\");'/></td>"+
    		"</tr>";

    		$(trTag).insertBefore($('#labTestTRID'));
        	
        	var totalAmount = parseFloat(itemRate + parseFloat(testRate));
        	
        	$("#totalAmountID").val(totalAmount);
        	
        	var netAmount = parseFloat(totalAmount - parseFloat(concessionRate));
        	
        	$("#netAmountID").val(netAmount);
        	$("#balPaymentID").val(netAmount);
        	
        	testCounter++;
        	
        	$("#testID").val("");
        	$("#testRate").val("0");
        	
        	//removing that option tag from select tag
        	$("#testID option[value='"+testValue+"']").remove();
    		
    	}
    	
    }
    
    function removeTR(trID, testRate, concessionRate, testValue, testName){
    	
    	if(confirm("Are you sure you want to delete this row?")){
    		
    		$('#testID')
            .append($("<option></option>")
                       .attr("value",testValue)
                       .text(testName));
    		
    		if(testRate == ""){
        		testRate = 0;
        	}
        	
        	if(concessionRate == ""){
        		concessionRate = 0;
        	}
        	
        	itemRate = 0;
        	
        	$(".billingRateClass").each(function(){
        		
        		itemRate = parseFloat(itemRate + parseFloat($(this).val()));
        		
        	});
        	
        	console.log("./itemRate.."+itemRate);
        	
    		var totalAmount = parseFloat(itemRate - parseFloat(testRate));
        	
        	$("#totalAmountID").val(totalAmount);
        	
        	var netAmount = parseFloat(totalAmount - parseFloat(concessionRate));
        	
        	$("#netAmountID").val(netAmount);
        	$("#balPaymentID").val(netAmount);
        	
        	$("#"+trID).remove();
    		
    	}
  
    }
    
    function selectGroupLabTest()
    {
    	var groupTest = $("#groupTest").val();
    	var singleTest = $("#singleTest").val();
    	
    	var newGroupTest = "";
    	var groupRate = 0;
    	var testRate = 0;
    	if(groupTest != "" && groupTest != null)
    		{
	    		for(var i = 0 ; i<groupTest.length ; i++)
	    		{
	    			newGroupTest = groupTest[i]+"*"+newGroupTest;
	    			var groupValue = groupTest[i].toString().split('$');
	    			
	    			if(typeof(groupValue[1]) != "undefined")
	    				{	
	    					groupRate = groupRate + parseInt(groupValue[1]);	
	    					
	    				}
	    		}
	    	 	
    		}
    	   	
    	var newSingleTest ="";
    	if(singleTest != "" && singleTest != null)
    		{
	    		for(var j = 0 ;j<singleTest.length ;j++)
	    		{
	    			newSingleTest =singleTest[j]+"*"+newSingleTest;
	    			var testValue = singleTest[j].toString().split('$');
	    			if(typeof(testValue[6]) != "undefined")
	    				{
	    					testRate = testRate + parseInt(testValue[6]);		
	    				}
	    		}
    		}
	    	
    	
    	var finalRate = groupRate + testRate;
    	
    	$("#totalRateID").val(finalRate);
    	$("#labTestHiddenID").val(newGroupTest+""+newSingleTest);
    	
    }
    
    $(document).ready(function() {
		
    	  
	    $('#singleTest').select2({
	      placeholder: "Select Single Test",
	      width: "100%",
	      multiple:true,
	      allowClear: true
	    })
	    
	    $('#groupTest').select2({
		      placeholder: "Select Group Test",
		      width: "100%",
		      multiple:true,
		      allowClear: true
		    })
	  
  });
   
    </script>
    
  </body>
</html>