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

    <title>Dashboard | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <!-- For full calendar -->
    <link href="build/css/fullcalendar.min.css" rel="stylesheet" />
	<link href="build/css/fullcalendar.print.min.css" rel="stylesheet" media="print" />
    
    <script type="text/javascript">
      function windowOpen(){
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
			$('a#repID').remove();	//To disable leftmenu options Reports
			
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
	
    <!-- For login message -->
    <%
    LoginForm form = (LoginForm) session.getAttribute("USER");
    //System.out.println("session>..."+session+"..."+session.isNew());
		if(form == null){
			//System.out.println("session inside..."+session);
			String loginMessage = "Plase login using valid credentials";
			request.setAttribute("loginMessage",loginMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request,response);
		}
		
		int userID = form.getUserID();
		
	LoginDAOInf daoInf = new LoginDAOImpl();
	
	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
	
	eDhanvantariServiceInf serviceInf = new eDhanvantariServiceImpl();
	
	
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
	
	<!--  -->
	
	<%
		//Appointment list messages
		String todayApptMsg = (String) request.getAttribute("todayApptMsg");
		
		String weekApptMsg = (String) request.getAttribute("weekApptMsg");
		
		String monthApptMsg = (String) request.getAttribute("monthApptMsg");
		
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
    
    
    <!-- Retrieving Clinic Start and end time -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getClinicStartEndTime() {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var clinicStartTime = "";
				var clinicEndTime = "";

				for ( var i = 0; i < array.Release.length; i++) {
					var clinicTimeArray = array.Release[i].clinicTime.split("=");
					
					clinicStartTime = clinicTimeArray[0];
					clinicEndTime = clinicTimeArray[1];
					
				}
				
				getClinicBreak1Time(clinicStartTime, clinicEndTime);
			}
		};
		xmlhttp.open("GET", "GetClinicStartEndTime" , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Retrieving Clinic break1 time -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getClinicBreak1Time(clinicStartTime, clinicEndTime) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var break1StartTime = "";
				var break1EndTime = "";

				for ( var i = 0; i < array.Release.length; i++) {
					var break1TimeArray = array.Release[i].break1Time.split("=");
					
					break1StartTime = break1TimeArray[0];
					break1EndTime = break1TimeArray[1];

				}
				
				getClinicBreak2Time(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime);
			}
		};
		xmlhttp.open("GET", "GetClinicBreak1Time" , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Retrieving Clinic break2 time -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getClinicBreak2Time(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var break2StartTime = "";
				var break2EndTime = "";

				for ( var i = 0; i < array.Release.length; i++) {
					var break2TimeArray = array.Release[i].break2Time.split("=");
					
					break2StartTime = break2TimeArray[0];
					break2EndTime = break2TimeArray[1];

				}
				
				//getClinicSlot1TimeAndName(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime);
				getDaysOtherThanClinicWorkingDays(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime);
			}
		};
		xmlhttp.open("GET", "GetClinicBreak2Time" , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Retrieving days other than clinic working days from calendar -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getDaysOtherThanClinicWorkingDays(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var hiddenDays = "";

				for ( var i = 0; i < array.Release.length; i++) {
					hiddenDays = array.Release[i].hiddenDays;

				}
				
				var hiddenDaysArr;
				console.log("hiddendays from JSP..."+hiddenDays);
				if(hiddenDays == "" || hiddenDays == null){
					//creating array from String
					hiddenDaysArr = [];
					console.log("array..."+hiddenDaysArr);
				}else if(typeof hiddenDays === "undefined"){
					hiddenDaysArr = [];
					console.log("array1..."+hiddenDaysArr);
				}else{
					//creating array from String
					hiddenDaysArr = JSON.parse("["+hiddenDays+"]");
					console.log("array..."+hiddenDaysArr);	
				}
					
					getCalendar(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime, hiddenDaysArr);
			
			
			}
			
		};
		xmlhttp.open("GET", "GetDaysOtherThanClinicWorkingDays" , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    
    <!-- For jquery full calendar -->
    
    <%
    	if(form.getUserType().equals("clinician")){
    %>
    
    <script type="text/javascript"> 

   		function getCalendar(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime, hiddenDaysArr){
   			
   			if(break1StartTime == "" || break1EndTime == ""){
   				break1StartTime = "00:00:00";
   				break1EndTime = "00:00:00";
   			}
   			
   			if(break2StartTime == "" || break2EndTime == ""){
   				break2EndTime = "00:00:00";
   				break2StartTime = "00:00:00";
   			}
   			
   			//Today's calendar
			$('#todayCalendar').fullCalendar({
				
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaDay(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: 'today prev,next' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
					 			url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
								type: 'POST',
								error: function() {
										console.log('there was an error while fetching calendar events!');
										},
								success: function(event){
										$('#todayCalendar').fullCalendar('removeEvents');
						             	$('#todayCalendar').fullCalendar('addEventSource', event);         
						              	$('#todayCalendar').fullCalendar('rerenderEvents' );
										}
								}
											
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				allDaySlot: true, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day'
				dayClick: function(date, jsEvent, view, all) {
	
			        //alert('Clicked on: ' + date.format());
	
			        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
			        //alert(date);
			        //alert(new Date());
			        //alert('Current view: ' + view.name);
			        $('#startTimeID').val(date.format());
				
					//Setting agenda as day for week's appointment view
					$('#agendaID').val("day");
					
					$('#patientFNameID').val("");
					$('#patientMNameID').val("");
					$('#patientLNameID').val("");
					$('#patientMobNoID').val("");
					$('#patientRegNoID').val("");
					$('#patientFNameID2').val("");
					$('#patientMNameID2').val("");
					$('#patientLNameID2').val("");
					$('#patientMobNoID2').val("");
					$('#patientRegNoID2').val("");
					$('#patientFNameID1').val("");
					$('#patientMNameID1').val("");
					$('#patientLNameID1').val("");
					$('#patientMobNoID1').val("");
					$('#patientRegNoID1').val("");
					$('#apptPatientID').val("");
					$('#apptPatientID2').val("");
					$('#apptPatientID1').val("");
					$('#apptTypeID').val("000");
					$('#apptDateID').val("");
					$('#apptStartTimeID').val("");
					$('#apptEndTimeID').val("");
					$('#apptTypeID1').val("000");
					$('#apptDateID1').val("");
					$('#apptStartTimeID1').val("");
					$('#apptEndTimeID1').val("");
					$('#apptTypeID2').val("000");
					$('#apptDateID2').val("");
					$('#apptStartTimeID2').val("");
					$('#apptEndTimeID2').val("");
					$('#endTimeID3').val("");
					$('#endTimeID2').val("");
					$('#endTimeID1').val("");
	
					console.log("todayCalendar 1");
					
			        //showing modal
			        $('#patientDetailModal').modal('show');
	
			    }, //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
			    eventClick: function(event, jsEvent, view){
			    	
					//$('#patDetailsID').html(event.title);
					console.log("todayCalendar 2");
					openNewVisit(event.title, event.id);
					
					
					 
			    }//this function gives the event information on clicking on the particular event
			});
   			
			//week's calendar
			$('#weekCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaWeek(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: 'agendaWeek,agendaDay today prev,next' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
							    url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
							    type: 'POST',
							    error: function() {
							    	console.log('there was an error while fetching calendar events!');
						        },
						        success: function(events){
									//$('#weekCalendar').fullCalendar('removeEvents');
					             	//$('#weekCalendar').fullCalendar('addEventSource', events); 
					              	$('#weekCalendar').fullCalendar('rerenderEvents' );
								}
							}
								
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        hiddenDays: hiddenDaysArr,//to hide non working days
		        eventMouseout: function (data, event, view) {
		          // $(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				columnFormat: 'ddd DD/MM', //To set days title on calendar; ddd - Display day as Mon, Tue, Web and so on, DD - display date, MM- display month
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day'
				dayClick: function(date, jsEvent, view, all) {
					
			        //alert('Clicked on: ' + date.format());
	
			        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
			        //alert(date);
			        //alert(new Date());
			        //alert('Current view: ' + view.name);
			        var calendarDate = date.format();
			        
			        var currentDate = new Date();
			        
			        //Splitting calendar date by T in order to get date and time seperately
			        var calDateArr = calendarDate.split("T");
			        
			        var calDate = calDateArr[0]
			        
			        var calendarTime = calDateArr[1];
			        
					var finalCalDate = new Date(calDate);
					
					//getting calendar day month and year from finalCalDate
					var calDay = finalCalDate.getDate();
					var calMonth = finalCalDate.getMonth() + 1;
					var calYear = finalCalDate.getFullYear();
					
					//getting current day, month and year from currentDate
					var currDay = currentDate.getDate();
				    var currMOnth = currentDate.getMonth()+1;
				    var currYear = currentDate.getFullYear();
					
					//alert(currDay+".."+currMOnth+".."+currYear);
					
					var singleTime = "0,1,2,3,4,5,6,7,8,9";
					
					var mins = currentDate.getMinutes();
					
					if(singleTime.includes(mins)){
						mins = "0"+mins;
					}
					
					//getting current time from currentDate
					var currTime = currentDate.getHours() + ":" + mins;
					//alert(currTime);
					//Splitting calendar time in order to get only hours and minutes
					var calendarTimeArr = calendarTime.split(":");
					
					var calTime = calendarTimeArr[0] + ":" + calendarTimeArr[1];
					//alert(calTime);
			        
			        if(currYear == calYear){
			          	//alert("year matched");
			        	if(calMonth == currMOnth){
			            	//alert("month equals or greater than current");
			                if(calDay == currDay){
			                	//alert(".."+calTime+"..."+currTime)
			                	//alert("day equal to current");
			                    if(calTime >= currTime){
			                    	//alert("time greter than or equal to current");
			                    	$('#startTimeID').val(date.format());
			    					 
			    					//Setting agenda as Week for week's appointment view
			    					$('#agendaID').val("week");
			    					
			    					$('#patientFNameID').val("");
			    					$('#patientMNameID').val("");
			    					$('#patientLNameID').val("");
			    					$('#patientMobNoID').val("");
			    					$('#patientRegNoID').val("");
			    					$('#patientFNameID2').val("");
			    					$('#patientMNameID2').val("");
			    					$('#patientLNameID2').val("");
			    					$('#patientMobNoID2').val("");
			    					$('#patientRegNoID2').val("");
			    					$('#patientFNameID1').val("");
			    					$('#patientMNameID1').val("");
			    					$('#patientLNameID1').val("");
			    					$('#patientMobNoID1').val("");
			    					$('#patientRegNoID1').val("");
			    					$('#apptPatientID').val("");
			    					$('#apptPatientID2').val("");
			    					$('#apptPatientID1').val("");
			    					$('#apptTypeID').val("000");
			    					$('#apptDateID').val("");
			    					$('#apptStartTimeID').val("");
			    					$('#apptEndTimeID').val("");
			    					$('#apptTypeID1').val("000");
			    					$('#apptDateID1').val("");
			    					$('#apptStartTimeID1').val("");
			    					$('#apptEndTimeID1').val("");
			    					$('#apptTypeID2').val("000");
			    					$('#apptDateID2').val("");
			    					$('#apptStartTimeID2').val("");
			    					$('#apptEndTimeID2').val("");
			    					$('#endTimeID3').val("");
			    					$('#endTimeID2').val("");
			    					$('#endTimeID1').val("");
			    	
			    			        //showing modal
			    			        $('#patientDetailModal').modal('show');
			                    }else{
			                    	//alert("NOt allowed as time is less than");
			                    	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                    	$("#calendarDateTimeAlertModal").modal("show");
			                    	//return false;
			                   	}
			                }else if(calDay > currDay){
			                	//alert("day greater than to current")
			                	$('#startTimeID').val(date.format());
								
								//Setting agenda as Week for week's appointment view
								$('#agendaID').val("week");
								
								$('#patientFNameID').val("");
								$('#patientMNameID').val("");
								$('#patientLNameID').val("");
								$('#patientMobNoID').val("");
								$('#patientRegNoID').val("");
								$('#patientFNameID2').val("");
								$('#patientMNameID2').val("");
								$('#patientLNameID2').val("");
								$('#patientMobNoID2').val("");
								$('#patientRegNoID2').val("");
								$('#patientFNameID1').val("");
								$('#patientMNameID1').val("");
								$('#patientLNameID1').val("");
								$('#patientMobNoID1').val("");
								$('#patientRegNoID1').val("");
								$('#apptPatientID').val("");
								$('#apptPatientID2').val("");
								$('#apptPatientID1').val("");
								$('#apptTypeID').val("000");
								$('#apptDateID').val("");
								$('#apptStartTimeID').val("");
								$('#apptEndTimeID').val("");
								$('#apptTypeID1').val("000");
								$('#apptDateID1').val("");
								$('#apptStartTimeID1').val("");
								$('#apptEndTimeID1').val("");
								$('#apptTypeID2').val("000");
								$('#apptDateID2').val("");
								$('#apptStartTimeID2').val("");
								$('#apptEndTimeID2').val("");
								$('#endTimeID3').val("");
								$('#endTimeID2').val("");
								$('#endTimeID1').val("");
				
						        //showing modal
						        $('#patientDetailModal').modal('show');
			                }else{
			                	//alert("day less than current day, not allow");
			                	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                    $("#calendarDateTimeAlertModal").modal("show");
			                	//return false;
			                }
			            }else if(calMonth > currMOnth){
			            	//alert("allowed");
			            	$('#startTimeID').val(date.format());
							
							//Setting agenda as Week for week's appointment view
							$('#agendaID').val("week");
							
							$('#patientFNameID').val("");
							$('#patientMNameID').val("");
							$('#patientLNameID').val("");
							$('#patientMobNoID').val("");
							$('#patientRegNoID').val("");
							$('#patientFNameID2').val("");
							$('#patientMNameID2').val("");
							$('#patientLNameID2').val("");
							$('#patientMobNoID2').val("");
							$('#patientRegNoID2').val("");
							$('#patientFNameID1').val("");
							$('#patientMNameID1').val("");
							$('#patientLNameID1').val("");
							$('#patientMobNoID1').val("");
							$('#patientRegNoID1').val("");
							$('#apptPatientID').val("");
							$('#apptPatientID2').val("");
							$('#apptPatientID1').val("");
							$('#apptTypeID').val("000");
							$('#apptDateID').val("");
							$('#apptStartTimeID').val("");
							$('#apptEndTimeID').val("");
							$('#apptTypeID1').val("000");
							$('#apptDateID1').val("");
							$('#apptStartTimeID1').val("");
							$('#apptEndTimeID1').val("");
							$('#apptTypeID2').val("000");
							$('#apptDateID2').val("");
							$('#apptStartTimeID2').val("");
							$('#apptEndTimeID2').val("");
							$('#endTimeID3').val("");
							$('#endTimeID2').val("");
							$('#endTimeID1').val("");
			
					        //showing modal
					        $('#patientDetailModal').modal('show');
			            }else{
			            	//alert("month less than current");
			            	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                $("#calendarDateTimeAlertModal").modal("show");
			            	//return false;
			            }
			          }else if(calYear > currYear){
			        	  
			        	//alert("allowed");
			            	$('#startTimeID').val(date.format());
							
							//Setting agenda as Week for week's appointment view
							$('#agendaID').val("week");
							
							$('#patientFNameID').val("");
							$('#patientMNameID').val("");
							$('#patientLNameID').val("");
							$('#patientMobNoID').val("");
							$('#patientRegNoID').val("");
							$('#patientFNameID2').val("");
							$('#patientMNameID2').val("");
							$('#patientLNameID2').val("");
							$('#patientMobNoID2').val("");
							$('#patientRegNoID2').val("");
							$('#patientFNameID1').val("");
							$('#patientMNameID1').val("");
							$('#patientLNameID1').val("");
							$('#patientMobNoID1').val("");
							$('#patientRegNoID1').val("");
							$('#apptPatientID').val("");
							$('#apptPatientID2').val("");
							$('#apptPatientID1').val("");
							$('#apptTypeID').val("000");
							$('#apptDateID').val("");
							$('#apptStartTimeID').val("");
							$('#apptEndTimeID').val("");
							$('#apptTypeID1').val("000");
							$('#apptDateID1').val("");
							$('#apptStartTimeID1').val("");
							$('#apptEndTimeID1').val("");
							$('#apptTypeID2').val("000");
							$('#apptDateID2').val("");
							$('#apptStartTimeID2').val("");
							$('#apptEndTimeID2').val("");
							$('#endTimeID3').val("");
							$('#endTimeID2').val("");
							$('#endTimeID1').val("");
			
					        //showing modal
					        $('#patientDetailModal').modal('show');
			        	  
			          }else{
			          	//alert("year not matched");
			          	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			            $("#calendarDateTimeAlertModal").modal("show");
			          	return false;
			          }
	
			    }, //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
			    eventClick: function(event, jsEvent, view){
			    	
					$('#patDetailsID').html(event.title);
					
					$("#patDetailsID123").val(event.title);
					
					$("#doneApptID").val(event.id);
					
					$('#patDetailsID1').val(event.title);
					
					var startArray = event.start.format();
					
					$('#updatestartTimeID').val(startArray);
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var array = startTime.split(" ");
					
					var dateArray = array[0].split("-");
					
					var finalStartDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
					
					$('#sDateID').val(finalStartDate);
					
					$('#sHHID').val(array[1]);
					
					var sTimeArray = array[1].split(":");
					
					$('#sMMID').val(sTimeArray[1]);
					
					var apptType = event.visitTypeID;
					
					$('#updateapptTypeID').val(apptType);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
					
					var array1 = endTime.split(" ");
					
					var dateArray1 = array1[0].split("-");
					
					var finalEndDate = dateArray1[2] + "-" + dateArray1[1] + "-" + dateArray1[0];
					
					$('#eDateID').val(finalEndDate);
					
					$('#eHHID').val(array1[1]);
					
					var eTimeArray = array1[1].split(":");
					
					$('#eMMID').val(eTimeArray[1]);
					
					//For getting patientID and appointmentID
					var patDetailsArray = event.title.split("-");
					
					var patDetailsArray1 = patDetailsArray[0].split(":");
					
					var patientID = patDetailsArray1[2];
					
					var apptID = event.id;		
					
					//setting patientID and apptID into hidden fields
					$("#patientIDForClinician").val(patientID);
					$("#apptIDForClinician").val(apptID);
					
					//displaying consultation button
					$("#ConsultationBtnID").show("show");
			    	
					//Check what appointment status is, and according to show CRF page i.e.; if 
		    		//status is Booked or Confirmed, redirect to add new visit page else render to
		    		//edit visit page
		    		var detailArray = event.title.split("(");
		    		
		    		var statusArray = detailArray[2].split(")");
		    		
		    		var aptStatus = statusArray[0];
		    		
		    		if(aptStatus == "Booked"){
		    			
		    			//verifyVisitExistsForAppointment(patientID, apptID);
		    			//Show event details modal with all event details
				    	$("#eventDetailModal").modal('show');
		    			
		    		}else if(aptStatus == "Confirmed"){
		    			verifyVisitExistsForAppointment(patientID, apptID);	    			
		    		}else if(aptStatus == "No Show" || aptStatus == "Done"){
		    			return true;
		    		}else if(aptStatus == "Billing"){
		    			//Show appointment done alter message modal
				    	$("#apptDoneAlertModal").modal('show');
		    		}else{
		        		
		        		window.location.assign("RenderEditAppointmentVisit?patientID="+patientID+"&aptID="+apptID);
		    			
		    		}
		    		
		    		/* if(aptStatus == "Billing"){
		    			
		    		}else if(aptStatus == "Done" || aptStatus == "No Show"){
		    			return true;
		    		}else{
		    		} */
			    	
					//$('#patDetailsID').html(event.title);
					
					//openNewVisit(event.title, event.id);
			    	
			    }//this function gives the event information on clicking on the particular event
			});
			
		}
	
		function createEvent(date, endDate, agenda, titleMsg){
					var newEvent = new Object();
	
			        newEvent.title=titleMsg;
			        newEvent.start=date;
			       
			      	/*var array = date.format().split("T");
	
			      	var newDate = new Date();
	
			      	var newTime = new Date(newDate.setMinutes(parseInt(array[1].split(":")[1]) + 15);
	
			      	alert(newTime);*/
	
			        newEvent.end=endDate;
			        newEvent.color='blue';
			        
			        if(agenda == "week"){
			        	
			        	 $('#weekCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }else{
			        	
			        	 $('#todayCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }
			        
		}
		
	
	</script>
    
    
    <%
    	}else if(form.getUserType().equals("compounder") || form.getUserType().equals("dietician") 
    			|| form.getUserType().equals("billDesk")  || form.getUserType().equals("accountant")){
    %>
    
    <script type="text/javascript">    

   		function getCalendar(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime, hiddenDaysArr){
   			
   			if(break1StartTime == "" || break1EndTime == ""){
   				break1StartTime = "00:00:00";
   				break1EndTime = "00:00:00";
   			}
   			
   			if(break2StartTime == "" || break2EndTime == ""){
   				break2EndTime = "00:00:00";
   				break2StartTime = "00:00:00";
   			}
   			
   			//Today's calendar
			$('#todayCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaDay(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it will display time labels with the interval of specified minutes; here it is 05 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: '' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
					 			url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
								type: 'POST',
								error: function() {
									console.log('there was an error while fetching calendar events!');
										},
								}
											
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				 //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day'
			    eventClick: function(event, jsEvent, view){
			    	
					$('#patDetailsID').html(event.title);
					
					var startArray = event.start.format();
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
			    	
					var eventType = event.visitTypeID;
					
					$('#eventTypeID').html(eventType);
					
					//Show event details modal with all event details
			    	//$("#eventDetailModal1").modal('show');
					openEditVisit(event.title, event.id);
					 
			    }//this function gives the event information on clicking on the particular event
			});
   			
			//week's calendar
			$('#weekCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaWeek(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: 'agendaWeek,agendaDay today prev,next' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
							    url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
							    type: 'POST',
							    error: function() {
							    	console.log('there was an error while fetching calendar events!');
						        },
						        success: function(events){
									//$('#weekCalendar').fullCalendar('removeEvents');
					             	//$('#weekCalendar').fullCalendar('addEventSource', events);         
					              	$('#weekCalendar').fullCalendar('rerenderEvents' );
								}
							}
								
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
		        hiddenDays: hiddenDaysArr,//to hide non working days
				columnFormat: 'ddd DD/MM', //To set days title on calendar; ddd - Display day as Mon, Tue, Web and so on, DD - display date, MM- display month
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				 //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day'
			    eventClick: function(event, jsEvent, view){
			    	
					$('#patDetailsID').html(event.title);
					
					var startArray = event.start.format();
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
					
					var eventType = event.visitTypeID;
					
					$('#eventTypeID').html(eventType);
					
					//Show event details modal with all event details
					//$("#eventDetailModal1").modal('show');
					openEditVisit(event.title, event.id);
			    	
			    }//this function gives the event information on clicking on the particular event
			});
			
		}
	
		function createEvent(date, endDate, agenda, titleMsg){
					var newEvent = new Object();
	
			        newEvent.title=titleMsg;
			        newEvent.start=date;
			       
			      	/*var array = date.format().split("T");
	
			      	var newDate = new Date();
	
			      	var newTime = new Date(newDate.setMinutes(parseInt(array[1].split(":")[1]) + 15);
	
			      	alert(newTime);*/
	
			        newEvent.end=endDate;
			        newEvent.color='blue';
			        
			        if(agenda == "week"){
			        	
			        	 $('#weekCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }else{
			        	
			        	 $('#todayCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }
			        
		}
		
	
	</script>
    
    
    <%
    	}else if(form.getUserType().equals("receptionist") || form.getUserType().equals("optician")){
    %>
    
    <script type="text/javascript">    

   		function getCalendar(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime, hiddenDaysArr){
   			
   			if(break1StartTime == "" || break1EndTime == ""){
   				break1StartTime = "00:00:00";
   				break1EndTime = "00:00:00";
   			}
   			
   			if(break2StartTime == "" || break2EndTime == ""){
   				break2EndTime = "00:00:00";
   				break2StartTime = "00:00:00";
   			}
   			
   			//Today's calendar
			$('#todayCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaDay(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: '' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
					 			url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
								type: 'POST',
								error: function() {
									console.log('there was an error while fetching calendar events!');
										},
								}
											
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day'
				dayClick: function(date, jsEvent, view, all) {
	
			        //alert('Clicked on: ' + date.format());
	
			        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
			        //alert(date);
			        //alert(new Date());
			        //alert('Current view: ' + view.name);
			        
			        $('#startTimeID').val(date.format());
					
					//Setting agenda as day for week's appointment view
					$('#agendaID').val("day");
					
					$('#patientFNameID').val("");
					$('#patientMNameID').val("");
					$('#patientLNameID').val("");
					$('#patientMobNoID').val("");
					$('#patientRegNoID').val("");
					$('#patientFNameID2').val("");
					$('#patientMNameID2').val("");
					$('#patientLNameID2').val("");
					$('#patientMobNoID2').val("");
					$('#patientRegNoID2').val("");
					$('#patientFNameID1').val("");
					$('#patientMNameID1').val("");
					$('#patientLNameID1').val("");
					$('#patientMobNoID1').val("");
					$('#patientRegNoID1').val("");
					$('#apptPatientID').val("");
					$('#apptPatientID2').val("");
					$('#apptPatientID1').val("");
					$('#apptTypeID').val("000");
					$('#apptDateID').val("");
					$('#apptStartTimeID').val("");
					$('#apptEndTimeID').val("");
					$('#apptTypeID1').val("000");
					$('#apptDateID1').val("");
					$('#apptStartTimeID1').val("");
					$('#apptEndTimeID1').val("");
					$('#apptTypeID2').val("000");
					$('#apptDateID2').val("");
					$('#apptStartTimeID2').val("");
					$('#apptEndTimeID2').val("");
					$('#endTimeID3').val("");
					$('#endTimeID2').val("");
					$('#endTimeID1').val("");
	
			        //showing modal
			        $('#patientDetailModal').modal('show');
	
			    }, //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
			    eventClick: function(event, jsEvent, view){
			    	
					$('#patDetailsID').html(event.title);
					
					$("#patDetailsID123").val(event.title);
					
					$('#patDetailsID1').val(event.title);
					
					$("#doneApptID").val(event.id);
					
					var startArray = event.start.format();
					
					$('#updatestartTimeID').val(startArray);
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var array = startTime.split(" ");
					
					var dateArray = array[0].split("-");
					
					var finalStartDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
					
					$('#sDateID').val(finalStartDate);
					
					$('#sHHID').val(array[1]);
					
					var sTimeArray = array[1].split(":");
					
					$('#sMMID').val(sTimeArray[1]);
					
					var apptType = event.visitTypeID;
					
					$('#updateapptTypeID').val(apptType);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
					
					var array1 = endTime.split(" ");
					
					var dateArray1 = array1[0].split("-");
					
					var finalEndDate = dateArray1[2] + "-" + dateArray1[1] + "-" + dateArray1[0];
					
					$('#eDateID').val(finalEndDate);
					
					$('#eHHID').val(array1[1]);
					
					var eTimeArray = array1[1].split(":");
					
					$('#eMMID').val(eTimeArray[1]);
					
					//Check what appointment status is, and according to show CRF page i.e.; if 
		    		//status is Booked or Confirmed, redirect to add new visit page else render to
		    		//edit visit page
		    		var detailArray = event.title.split("(");
		    		
		    		var statusArray = detailArray[2].split(")");
		    		
		    		var aptStatus = statusArray[0];
		    		
		    		if(aptStatus == "Billing"){
		    			//Show appointment done alter message modal
				    	$("#apptDoneAlertModal").modal('show');
		    		}else if(aptStatus == "Done" || aptStatus == "No Show"){
		    			return true;
		    		}else if(aptStatus == "Booked"){
		    			//Show event details modal with all event details
				    	$("#eventDetailModal").modal('show');
		    		}else{
		    			return true;
		    		}
					 
			    }//this function gives the event information on clicking on the particular event
			});
   			
			//week's calendar
			$('#weekCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaWeek(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: "<select class='form-control'><option value='0'>Select</option></select>", //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: 'agendaWeek,agendaDay today prev,next' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					    ],
				eventSources: [
							{
							    url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
							    type: 'POST',
							    error: function() {
							    	console.log('there was an error while fetching calendar events!');
						        },
						        success: function(events){
									//$('#weekCalendar').fullCalendar('removeEvents');
					             	//$('#weekCalendar').fullCalendar('addEventSource', events);  
					             	//alert('success');
					              	$('#weekCalendar').fullCalendar('rerenderEvents' );
								}
							}
								
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				hiddenDays: hiddenDaysArr,//to hide non working days
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				columnFormat: 'ddd DD/MM', //To set days title on calendar; ddd - Display day as Mon, Tue, Web and so on, DD - display date, MM- display month
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				/* allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day' */
				dayClick: function(date, jsEvent, view, all) {
	
			        //alert('Clicked on: ' + date.format());
	
			        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
			        //alert(date);
			        //alert(new Date());
			        //alert('Current view: ' + view.name);
			        var calendarDate = date.format();
			        
			        var currentDate = new Date();
			        
			        //Splitting calendar date by T in order to get date and time seperately
			        var calDateArr = calendarDate.split("T");
			        
			        var calDate = calDateArr[0]
			        
			        var calendarTime = calDateArr[1];
			        
					var finalCalDate = new Date(calDate);
					
					//getting calendar day month and year from finalCalDate
					var calDay = finalCalDate.getDate();
					var calMonth = finalCalDate.getMonth() + 1;
					var calYear = finalCalDate.getFullYear();
					
					//getting current day, month and year from currentDate
					var currDay = currentDate.getDate();
				    var currMOnth = currentDate.getMonth()+1;
				    var currYear = currentDate.getFullYear();
					
					//alert(currDay+".."+currMOnth+".."+currYear);
					
					var singleTime = "0,1,2,3,4,5,6,7,8,9";
					
					var mins = currentDate.getMinutes();
					
					if(singleTime.includes(mins)){
						mins = "0"+mins;
					}
					
					//getting current time from currentDate
					var currTime = currentDate.getHours() + ":" + mins;
					//alert(currTime);
					//Splitting calendar time in order to get only hours and minutes
					var calendarTimeArr = calendarTime.split(":");
					
					var calTime = calendarTimeArr[0] + ":" + calendarTimeArr[1];
					//alert(calTime);
			        
			        if(currYear == calYear){
			          	//alert("year matched");
			        	if(calMonth == currMOnth){
			            	//alert("month equals or greater than current");
			                if(calDay == currDay){
			                	//alert(".."+calTime+"..."+currTime)
			                	//alert("day equal to current");
			                    if(calTime >= currTime){
			                    	//alert("time greter than or equal to current");
			                    	$('#startTimeID').val(date.format());
			    					
			    					//Setting agenda as Week for week's appointment view
			    					$('#agendaID').val("week");
			    					
			    					$('#patientFNameID').val("");
			    					$('#patientMNameID').val("");
			    					$('#patientLNameID').val("");
			    					$('#patientMobNoID').val("");
			    					$('#patientRegNoID').val("");
			    					$('#patientFNameID2').val("");
			    					$('#patientMNameID2').val("");
			    					$('#patientLNameID2').val("");
			    					$('#patientMobNoID2').val("");
			    					$('#patientRegNoID2').val("");
			    					$('#patientFNameID1').val("");
			    					$('#patientMNameID1').val("");
			    					$('#patientLNameID1').val("");
			    					$('#patientMobNoID1').val("");
			    					$('#patientRegNoID1').val("");
			    					$('#apptPatientID').val("");
			    					$('#apptPatientID2').val("");
			    					$('#apptPatientID1').val("");
			    					$('#apptTypeID').val("000");
			    					$('#apptDateID').val("");
			    					$('#apptStartTimeID').val("");
			    					$('#apptEndTimeID').val("");
			    					$('#apptTypeID1').val("000");
			    					$('#apptDateID1').val("");
			    					$('#apptStartTimeID1').val("");
			    					$('#apptEndTimeID1').val("");
			    					$('#apptTypeID2').val("000");
			    					$('#apptDateID2').val("");
			    					$('#apptStartTimeID2').val("");
			    					$('#apptEndTimeID2').val("");
			    					$('#endTimeID3').val("");
			    					$('#endTimeID2').val("");
			    					$('#endTimeID1').val("");
			    	
			    			        //showing modal
			    			        $('#patientDetailModal').modal('show');
			                    }else{
			                    	//alert("NOt allowed as time is less than");
			                    	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                    	$("#calendarDateTimeAlertModal").modal("show");
			                    	//return false;
			                   	}
			                }else if(calDay > currDay){
			                	//alert("day greater than to current")
			                	$('#startTimeID').val(date.format());
								
								//Setting agenda as Week for week's appointment view
								$('#agendaID').val("week");
								
								$('#patientFNameID').val("");
								$('#patientMNameID').val("");
								$('#patientLNameID').val("");
								$('#patientMobNoID').val("");
								$('#patientRegNoID').val("");
								$('#patientFNameID2').val("");
								$('#patientMNameID2').val("");
								$('#patientLNameID2').val("");
								$('#patientMobNoID2').val("");
								$('#patientRegNoID2').val("");
								$('#patientFNameID1').val("");
								$('#patientMNameID1').val("");
								$('#patientLNameID1').val("");
								$('#patientMobNoID1').val("");
								$('#patientRegNoID1').val("");
								$('#apptPatientID').val("");
								$('#apptPatientID2').val("");
								$('#apptPatientID1').val("");
								$('#apptTypeID').val("000");
								$('#apptDateID').val("");
								$('#apptStartTimeID').val("");
								$('#apptEndTimeID').val("");
								$('#apptTypeID1').val("000");
								$('#apptDateID1').val("");
								$('#apptStartTimeID1').val("");
								$('#apptEndTimeID1').val("");
								$('#apptTypeID2').val("000");
								$('#apptDateID2').val("");
								$('#apptStartTimeID2').val("");
								$('#apptEndTimeID2').val("");
								$('#endTimeID3').val("");
								$('#endTimeID2').val("");
								$('#endTimeID1').val("");
				
						        //showing modal
						        $('#patientDetailModal').modal('show');
			                }else{
			                	//alert("day less than current day, not allow");
			                	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                    $("#calendarDateTimeAlertModal").modal("show");
			                	//return false;
			                }
			            }else if(calMonth > currMOnth){
			            	//alert("allowed");
			            	$('#startTimeID').val(date.format());
							
							//Setting agenda as Week for week's appointment view
							$('#agendaID').val("week");
							
							$('#patientFNameID').val("");
							$('#patientMNameID').val("");
							$('#patientLNameID').val("");
							$('#patientMobNoID').val("");
							$('#patientRegNoID').val("");
							$('#patientFNameID2').val("");
							$('#patientMNameID2').val("");
							$('#patientLNameID2').val("");
							$('#patientMobNoID2').val("");
							$('#patientRegNoID2').val("");
							$('#patientFNameID1').val("");
							$('#patientMNameID1').val("");
							$('#patientLNameID1').val("");
							$('#patientMobNoID1').val("");
							$('#patientRegNoID1').val("");
							$('#apptPatientID').val("");
							$('#apptPatientID2').val("");
							$('#apptPatientID1').val("");
							$('#apptTypeID').val("000");
							$('#apptDateID').val("");
							$('#apptStartTimeID').val("");
							$('#apptEndTimeID').val("");
							$('#apptTypeID1').val("000");
							$('#apptDateID1').val("");
							$('#apptStartTimeID1').val("");
							$('#apptEndTimeID1').val("");
							$('#apptTypeID2').val("000");
							$('#apptDateID2').val("");
							$('#apptStartTimeID2').val("");
							$('#apptEndTimeID2').val("");
							$('#endTimeID3').val("");
							$('#endTimeID2').val("");
							$('#endTimeID1').val("");
			
					        //showing modal
					        $('#patientDetailModal').modal('show');
			            }else{
			            	//alert("month less than current");
			            	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			                $("#calendarDateTimeAlertModal").modal("show");
			            	//return false;
			            }
			          }else if(calYear > currYear){
			        	  
			        	//alert("allowed");
			            	$('#startTimeID').val(date.format());
							
							//Setting agenda as Week for week's appointment view
							$('#agendaID').val("week");
							
							$('#patientFNameID').val("");
							$('#patientMNameID').val("");
							$('#patientLNameID').val("");
							$('#patientMobNoID').val("");
							$('#patientRegNoID').val("");
							$('#patientFNameID2').val("");
							$('#patientMNameID2').val("");
							$('#patientLNameID2').val("");
							$('#patientMobNoID2').val("");
							$('#patientRegNoID2').val("");
							$('#patientFNameID1').val("");
							$('#patientMNameID1').val("");
							$('#patientLNameID1').val("");
							$('#patientMobNoID1').val("");
							$('#patientRegNoID1').val("");
							$('#apptPatientID').val("");
							$('#apptPatientID2').val("");
							$('#apptPatientID1').val("");
							$('#apptTypeID').val("000");
							$('#apptDateID').val("");
							$('#apptStartTimeID').val("");
							$('#apptEndTimeID').val("");
							$('#apptTypeID1').val("000");
							$('#apptDateID1').val("");
							$('#apptStartTimeID1').val("");
							$('#apptEndTimeID1').val("");
							$('#apptTypeID2').val("000");
							$('#apptDateID2').val("");
							$('#apptStartTimeID2').val("");
							$('#apptEndTimeID2').val("");
							$('#endTimeID3').val("");
							$('#endTimeID2').val("");
							$('#endTimeID1').val("");
			
					        //showing modal
					        $('#patientDetailModal').modal('show');
			        	  
			          }else{
			          	//alert("year not matched");
			          	$("#alertMsgID").html("Not allowed to add pre dated appointment.");
			            $("#calendarDateTimeAlertModal").modal("show");
			          	return false;
			          }
	
			    }, //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
			    eventClick: function(event, jsEvent, view){
			    	
			    	//displaying consultation button
					$("#ConsultationBtnID").hide();
			    	
					$('#patDetailsID').html(event.title);
					
					$("#patDetailsID123").val(event.title);
					
					$("#doneApptID").val(event.id);
					
					$('#patDetailsID1').val(event.title);
					
					var startArray = event.start.format();
					
					$('#updatestartTimeID').val(startArray);
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var array = startTime.split(" ");
					
					var dateArray = array[0].split("-");
					
					var finalStartDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
					
					$('#sDateID').val(finalStartDate);
					
					$('#sHHID').val(array[1]);
					
					var sTimeArray = array[1].split(":");
					
					$('#sMMID').val(sTimeArray[1]);
					
					var apptType = event.visitTypeID;
					
					$('#updateapptTypeID').val(apptType);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
					
					var array1 = endTime.split(" ");
					
					var dateArray1 = array1[0].split("-");
					
					var finalEndDate = dateArray1[2] + "-" + dateArray1[1] + "-" + dateArray1[0];
					
					$('#eDateID').val(finalEndDate);
					
					$('#eHHID').val(array1[1]);
					
					var eTimeArray = array1[1].split(":");
					
					$('#eMMID').val(eTimeArray[1]);
			    	
					//Check what appointment status is, and according to show CRF page i.e.; if 
		    		//status is Booked or Confirmed, redirect to add new visit page else render to
		    		//edit visit page
		    		var detailArray = event.title.split("(");
		    		
		    		var statusArray = detailArray[2].split(")");
		    		
		    		var aptStatus = statusArray[0];
		    		
		    		if(aptStatus == "Billing"){
		    			//Show appointment done alter message modal
				    	$("#apptDoneAlertModal").modal('show');
		    		}else if(aptStatus == "Booked"){
		    			//Show event details modal with all event details
				    	$("#eventDetailModal").modal('show');
		    		}else{
		    			return true;
		    		}
			    	
			    }//this function gives the event information on clicking on the particular event
			});
			
		}
	
		function createEvent(date, endDate, agenda, titleMsg){
					var newEvent = new Object();
	
			        newEvent.title=titleMsg;
			        newEvent.start=date;
			       
			      	/*var array = date.format().split("T");
	
			      	var newDate = new Date();
	
			      	var newTime = new Date(newDate.setMinutes(parseInt(array[1].split(":")[1]) + 15);
	
			      	alert(newTime);*/
	
			        newEvent.end=endDate;
			        newEvent.color='blue';
			        
			        if(agenda == "week"){
			        	
			        	 $('#weekCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	window.location.assign("Welcome.jsp");
			        	
			        }else{
			        	
			        	 $('#todayCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }
			        
		}
		
	
	</script>
    
    
    <%
    	} else if(form.getUserType().equals("administrator") || form.getUserType().equals("superAdmin")){
    %>  
    
    <script type="text/javascript">    

   		function getCalendar(clinicStartTime, clinicEndTime, break1StartTime, break1EndTime, break2StartTime, break2EndTime, hiddenDaysArr){
   			
   			if(break1StartTime == "" || break1EndTime == ""){
   				break1StartTime = "00:00:00";
   				break1EndTime = "00:00:00";
   			}
   			
   			if(break2StartTime == "" || break2EndTime == ""){
   				break2EndTime = "00:00:00";
   				break2StartTime = "00:00:00";
   			}
   			
   			//Today's calendar
			$('#todayCalendar').fullCalendar({
				
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaDay(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: '', //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: '' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
					 			url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
								type: 'POST',
								error: function() {
									console.log('there was an error while fetching calendar events!');
										},
								}
											
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				/* allDaySlot: true, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day' */
				dayClick: function(date, jsEvent, view, all) {
	
					//alert('Clicked on: ' + date.format());
	
			        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
			        //alert(date);
			        //alert(new Date());
			        //alert('Current view: ' + view.name);
			        
			        $('#startTimeID').val(date.format());
					
					//Setting agenda as day for week's appointment view
					$('#agendaID').val("day");
					
					$('#patientFNameID').val("");
					$('#patientMNameID').val("");
					$('#patientLNameID').val("");
					$('#patientMobNoID').val("");
					$('#patientRegNoID').val("");
					$('#patientFNameID2').val("");
					$('#patientMNameID2').val("");
					$('#patientLNameID2').val("");
					$('#patientMobNoID2').val("");
					$('#patientRegNoID2').val("");
					$('#patientFNameID1').val("");
					$('#patientMNameID1').val("");
					$('#patientLNameID1').val("");
					$('#patientMobNoID1').val("");
					$('#patientRegNoID1').val("");
					$('#apptPatientID').val("");
					$('#apptPatientID2').val("");
					$('#apptPatientID1').val("");
					$('#apptTypeID').val("000");
					$('#apptDateID').val("");
					$('#apptStartTimeID').val("");
					$('#apptEndTimeID').val("");
					$('#apptTypeID1').val("000");
					$('#apptDateID1').val("");
					$('#apptStartTimeID1').val("");
					$('#apptEndTimeID1').val("");
					$('#apptTypeID2').val("000");
					$('#apptDateID2').val("");
					$('#apptStartTimeID2').val("");
					$('#apptEndTimeID2').val("");
					$('#endTimeID3').val("");
					$('#endTimeID2').val("");
					$('#endTimeID1').val("");
	
			        //showing modal
			        $('#patientDetailModal').modal('show');
	
			    }, //function to get the days click information. When clicked on agendaWeek and agendaDay view, returns date and time of clicked region.
			    eventClick: function(event, jsEvent, view){
			    	
					$('#patDetailsID').html(event.title);
					
					$("#patDetailsID123").val(event.title);
					
					$('#patDetailsID1').val(event.title);
					
					$("#doneApptID").val(event.id);
					
					var startArray = event.start.format();
					
					$('#updatestartTimeID').val(startArray);
					
					var startTime = startArray.replace("T"," ");
					
					$('#eventStartID').html(startTime);
					
					var array = startTime.split(" ");
					
					var dateArray = array[0].split("-");
					
					var finalStartDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
					
					$('#sDateID').val(finalStartDate);
					
					$('#sHHID').val(array[1]);
					
					var sTimeArray = array[1].split(":");
					
					$('#sMMID').val(sTimeArray[1]);
					
					var apptType = event.visitTypeID;
					
					$('#updateapptTypeID').val(apptType);
					
					var endArray = event.end.format();
					
					var endTime = endArray.replace("T"," ");
					
					$('#eventEndID').html(endTime);
					
					var array1 = endTime.split(" ");
					
					var dateArray1 = array1[0].split("-");
					
					var finalEndDate = dateArray1[2] + "-" + dateArray1[1] + "-" + dateArray1[0];
					
					$('#eDateID').val(finalEndDate);
					
					$('#eHHID').val(array1[1]);
					
					var eTimeArray = array1[1].split(":");
					
					$('#eMMID').val(eTimeArray[1]);
					
					//Check what appointment status is, and according to show CRF page i.e.; if 
		    		//status is Booked or Confirmed, redirect to add new visit page else render to
		    		//edit visit page
		    		var detailArray = event.title.split("(");
		    		
		    		var statusArray = detailArray[2].split(")");
		    		
		    		var aptStatus = statusArray[0];
		    		
		    		if(aptStatus == "Billing"){
		    			//Show appointment done alter message modal
				    	$("#apptDoneAlertModal").modal('show');
		    		}else if(aptStatus == "Done" || aptStatus == "No Show"){
		    			return true;
		    		}else{
		    			//Show event details modal with all event details
				    	$("#eventDetailModal").modal('show');
		    		}
					 
			    }//this function gives the event information on clicking on the particular event
			});
   			
			//week's calendar
			$('#weekCalendar').fullCalendar({
				defaultView: 'agendaDay', //to change the default view of caledar. Values are- month(for month's), agendaWeek(for week's), agendaDay(for day's)
				firstDay: 1, //to change the first day of week. by default it is Sunday. Values are 0-Sun,1-Mon,2-Tue,3-Wed,4-Thu,5-Fri,6-Sat 
				editable: false, //whether to drag or extend the slots from Calendar, false-does not allow, true-allows
				eventLimit: true, // allow "more" link when too many events
				minTime: clinicStartTime, //set start time of calendar for that particular view. 
	        	maxTime: clinicEndTime, //set end time of calendar for that particular view.
	        	slotDuration: '00:15:00', //determines the duration between two times. Default value is 30 minutes.
	        	slotLabelInterval: '00:15:00', //Display label interval of time specified; here it would display time labels with the interval of 15 minutes. Before setting slotLabelInterval, it is mandatory to set slotDuration for the time interval, as for slotDuration's 30 minutes value, the value for slotLabelInterval would be 1 hour. i.e., double the slotDuration.
				header: { //for setting the header for the customize header for the calendar
					left: "<select class='form-control'><option value='0'>Select</option></select>", //set the content to be displayed on the left top corner of calendar
					center: 'title', // set the content to be displayed on the center top of the calendar
					right: 'agendaWeek,agendaDay today prev,next' //set the content to be displayed on the right top corner of the calendar
				},
				events: [  
					        {
					            start: break1StartTime,
					            end: break1EndTime,
					            title: 'Break1',
					            color: 'gray'
					        },
					        
					        {
					            start: break2StartTime,
					            end: break2EndTime,
					            title: 'Break2',
					            color: 'gray'
					        },
					        
					    ],
				eventSources: [
							{
							    url: '/eDhanvantari/ApptCalendarJSONServlet', // use the `url` property
							    type: 'POST',
							    error: function() {
							    	console.log('there was an error while fetching calendar events!');
						        },
						        success: function(events){
									//$('#weekCalendar').fullCalendar('removeEvents');
					             	//$('#weekCalendar').fullCalendar('addEventSource', events);  
					             	//alert('success');
					              	$('#weekCalendar').fullCalendar('rerenderEvents' );
								}
							}
								
						],//For adding event from external source; like from DB
				eventRender: function(event, element, view) {
						    /* Only muck about if it is a background rendered event */
						    if (event.rendering == 'background') {
						      var bgEventTitle = document.createElement('span');
						      bgEventTitle.style.color= 'white';
						      /* bgEventTitle.classList.add('fc-event'); */
						      bgEventTitle.innerHTML = /*'<span class="fc-title">' + */ event.title /* + '</span>'*/ ;
						      /* set container element positioning to relative so the positioning above will work */
						      element.html(bgEventTitle);
						    }
				},//to set the title to background events
				hiddenDays: hiddenDaysArr ,//to hide non working days
				eventMouseover: function (data, event, view) {
					
					var details = data.title.replace("#", "<br>#");

		            tooltip = '<div class="tooltiptopicevent" style="color: black;width:250px;height:auto;background:#e5ffec;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;line-height: 200%;border-radius: 5px;">' + details + '</div>';


		            $("body").append(tooltip);
		            $(this).mouseover(function (e) {
		                //$(this).css('z-index', 10000);
		                $('.tooltiptopicevent').fadeIn('500');
		                $('.tooltiptopicevent').fadeTo('10', 1.9);
		            }).mousemove(function (e) {
		                $('.tooltiptopicevent').css('top', e.pageY + 10);
		                $('.tooltiptopicevent').css('left', e.pageX + 20);
		            });


		        },//To show tooltip on mouse over on event which contains event details.
		        hiddenDays: hiddenDaysArr,//to hide non working days
		        eventMouseout: function (data, event, view) {
		            //$(this).css('z-index', 8);

		            $('.tooltiptopicevent').remove();

		        },//To remove the tooltip which has been showed on mouse over
				columnFormat: 'ddd DD/MM', //To set days title on calendar; ddd - Display day as Mon, Tue, Web and so on, DD - display date, MM- display month
				allDaySlot: false, //to display 'allDay' slot into the calendar. Values are true-to display, false-not to display
				allDayText: 'Leaves', //to change the allDay text, by default it is 'all-day' */
			});
			
		}
	
		function createEvent(date, endDate, agenda, titleMsg){
					var newEvent = new Object();
	
			        newEvent.title=titleMsg;
			        newEvent.start=date;
			       
			      	/*var array = date.format().split("T");
	
			      	var newDate = new Date();
	
			      	var newTime = new Date(newDate.setMinutes(parseInt(array[1].split(":")[1]) + 15);
	
			      	alert(newTime);*/
	
			        newEvent.end=endDate;
			        newEvent.color='blue';
			        
			        if(agenda == "week"){
			        	
			        	 $('#weekCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	window.location.assign("Welcome.jsp");
			        	
			        }else{
			        	
			        	 $('#todayCalendar').fullCalendar('renderEvent', newEvent);
			        	 
			        	 //location.reload();
			        	 window.location.assign("Welcome.jsp");
			        	
			        }
			        
		}
		
	
	</script>
    
    <%
    	}
    %>
	
    
    <!-- Ends -->
    
    
    <!-- Check patient details from calendar -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function checkPatient(fName, mName, lName, mobileNo1, startDate, agenda) {
		
		var firstName = fName;
		var middleName = mName;
		var lastName = lName;
		var mobNo1 = mobileNo1;
		mobileNo1 = mobileNo1.substr(0,9);
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var fName = "";
				var mName = "";
				var lName = "";
				var mobNo = "";
				var regNo = "";
				var patID = "";
				
				var patientListTag = "<table border='1' style='width: 100%;'><thead><tr>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Name</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Mobile No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Reg. No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Select</td>";
				patientListTag += "</thead></tr><tbody>";

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					fName = array.Release[i].fName;
					mName = array.Release[i].mName;
					lName = array.Release[i].lName;
					mobNo = array.Release[i].mobileNo;
					regNo = array.Release[i].regNo;
					patID = array.Release[i].patientID;
					
					//Creating table rows
					patientListTag += "<tr>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+fName + " "+ mName + " " + lName +"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+mobNo+"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+regNo+"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;text-align: center;'><input type='radio' name='patientSelect' onclick='setPatientID(\""+patID+"\");'></td>";
					patientListTag += "</tr>";

				}
				
				console.log("Check :: "+check);
				
				patientListTag += "</tbody></table>";
				
				if(check == 0){
					
					//$('#patientErrID').html("No patient found.");
					
					$('#startTimeID3').val(startDate);
					//$('#endTimeID1').val(endDate);
					$('#agendaID3').val(agenda);
					
					//Splitting start date in order to get date and time seperate
					var startDateArray = startDate.split("T");
					
					$('#apptDateID1').val(startDateArray[0]);
					
					//Splitting time by : in order to get HH and MM seperate
					var startTime = startDateArray[1];
					
					var startTimeArray = startTime.split(":");
					
					$("#hhID1").val(startTimeArray[0]);
					$("#mmID1").val(startTimeArray[1]);
					
					$('#apptStartTimeID1').val(startTime);
					
					//$("#patientDetailModal").modal('hide');
					$('#patientDetailsCloseID').click();
					
					window.location.assign("RenderAddNewPatientAppointment?firstName="+firstName+"&middleName="+middleName+"&lastName="+lastName+"&mobile="+mobNo1+"&appointmentDate="+startDateArray[0]+"&aptTimeFrom="+startTime);
					
					//retrieveRegistrationNumber();
					
					//$('#newPatietModal').modal('show');
					
				}else if(check == 1){
					
					$('#patientFNameID1').val(fName);
					$('#patientMNameID1').val(mName);
					$('#patientLNameID1').val(lName);
					$('#patientMobNoID1').val(mobNo);
					$('#patientRegNoID1').val(regNo);
					$('#apptPatientID').val(patID);
					
					$('#startTimeID1').val(startDate);
					//$('#endTimeID1').val(endDate);
					$('#agendaID1').val(agenda);
					
					//Splitting start date in order to get date and time seperate
					var startDateArray = startDate.split("T");
					
					$('#apptDateID').val(startDateArray[0]);
					
					//Splitting time by : in order to get HH and MM seperate
					var startTime = startDateArray[1];
					
					var startTimeArray = startTime.split(":");
					
					$("#hhID2").val(startTimeArray[0]);
					$("#mmID2").val(startTimeArray[1]);
					
					$('#apptStartTimeID').val(startTime);
					
					//$('#patientErrID').html("");
					
					//$("#patientDetailModal").modal('hide');
					$('#patientDetailsCloseID').click();
					
					$('#singlePatDetails').modal('show');
					
				}else{
					
					//$('#patientErrID').html("");
					
					$('#patientDetailsListID').html(patientListTag);
					
					$('#startTimeID2').val(startDate);
					//$('#endTimeID2').val(endDate);
					$('#agendaID2').val(agenda);
					
					//Splitting start date in order to get date and time seperate
					var startDateArray = startDate.split("T");
					
					$('#apptDateID2').val(startDateArray[0]);
					
					//Splitting time by : in order to get HH and MM seperate
					var startTime = startDateArray[1];
					
					var startTimeArray = startTime.split(":");
					
					$("#hhID3").val(startTimeArray[0]);
					$("#mmID3").val(startTimeArray[1]);
					
					$('#apptStartTimeID2').val(startTime);
					
					$("#patientDetailModal").modal('hide');
					
					$('#firstNameID').val(firstName);
					$('#middleNameID').val(middleName);
					$('#lastNameID').val(lastName);
					$('#patMobileNoID').val(mobNo1);
					$('#clinicRegNoID').val("");
					
					$('#multiPatDetails').modal('show');
					
				}
				
			}
		};
		xmlhttp.open("POST", "VerifyPatientDetails?firstName="+fName+"&middleName="+mName+
				"&mobileNo="+mobileNo1+"&lastName="+lName, true);
		xmlhttp.send();
	}
	
	</script>
    
    <!-- Ends -->
    
    
    <!-- Check patient details from calendar -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function checkSearchPatient(fName, mName, lName, mobileNo1) {
		
		var firstName = fName;
		var middleName = mName;
		var lastName = lName;
		var mobNo1 = mobileNo1;
		mobileNo1 = mobileNo1.substr(0,9);
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var fName = "";
				var mName = "";
				var lName = "";
				var mobNo = "";
				var regNo = "";
				var patID = "";
				
				var patientListTag = "<table border='1' style='width: 100%;'><thead><tr>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Name</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Mobile No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Select</td>";
				patientListTag += "</thead></tr><tbody>";

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					fName = array.Release[i].fName;
					mName = array.Release[i].mName;
					lName = array.Release[i].lName;
					mobNo = array.Release[i].mobileNo;
					patID = array.Release[i].patientID;
					
					//Creating table rows
					patientListTag += "<tr>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+fName + " "+ mName + " " + lName +"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+mobNo+"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;text-align: center;'><input type='radio' id='searchPatRadioID' name='patientSelect' onclick='setPatientID(\""+patID+"\");'></td>";
					patientListTag += "</tr>";

				}
				
				console.log("Check :: "+check);
				
				patientListTag += "</tbody></table>";
				
				if(check == 0){
					
					$('#patientSearchDetailsCloseID').click();
					
					window.location.assign("RenderAddNewPatient?firstName="+firstName+"&middleName="+middleName+"&lastName="+lastName+"&mobile="+mobNo1);
					
				}else if(check == 1){
					$('#patientSearchDetailsListID').html(patientListTag);
					
					$("#searchPatientDetailModal").modal('hide');
					
					$('#firstSearchNameID').val(firstName);
					$('#middleSearchNameID').val(middleName);
					$('#lastSearchNameID').val(lastName);
					$('#patSearchMobileNoID').val(mobNo1);
					$('#searchPatientID').val(patID);
					
					$('#searchPatRadioID').prop('checked', true);
					
					$('#multiSearchPatDetails').modal('show');
					
				}else {
					
					$('#patientSearchDetailsListID').html(patientListTag);
					
					$("#searchPatientDetailModal").modal('hide');
					
					$('#firstSearchNameID').val(firstName);
					$('#middleSearchNameID').val(middleName);
					$('#lastSearchNameID').val(lastName);
					$('#patSearchMobileNoID').val(mobNo1);
					$('#multiSearchPatDetails').modal('show');
					
				}
				
			}
		};
		xmlhttp.open("POST", "VerifyPatientDetails?firstName="+fName+"&middleName="+mName+
				"&mobileNo="+mobileNo1+"&lastName="+lName, true);
		xmlhttp.send();
	}
	
	</script>
    
    <!-- Ends -->
    
    
    <!-- Retrieving registration number -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function retrieveRegistrationNumber(visitTypeID, startDate) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var regNo = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					regNo = array.Release[i].regNo;

				}
				
				$('#patientRegNoID2').val(regNo);
				
				$('#newPatietModal').modal('show');

			}
		};
		xmlhttp.open("GET", "RetrieveRegistrationNumber" , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Check patient details from dashboard -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function checkPatientForAppointment(fName, mName, lName, mobileNo1) {
		
		var firstName = fName;
		var middleName = mName;
		var lastName = lName;
		var mobNo1 = mobileNo1;
		mobileNo1 = mobileNo1.substr(0,9);
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var fName = "";
				var mName = "";
				var lName = "";
				var mobNo = "";
				var regNo = "";
				var patID = "";
				
				var patientListTag = "<table border='1' style='width: 100%;'><thead><tr>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Name</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Mobile No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Patient Reg. No.</td>";
				patientListTag += "<td style='padding: 5px;font-size: 14px;font-weight:bold;'>Select</td>";
				patientListTag += "</thead></tr><tbody>";

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					fName = array.Release[i].fName;
					mName = array.Release[i].mName;
					lName = array.Release[i].lName;
					mobNo = array.Release[i].mobileNo;
					regNo = array.Release[i].regNo;
					patID = array.Release[i].patientID;
					
					//Creating table rows
					patientListTag += "<tr>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+fName + " "+ mName + " " + lName +"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+mobNo+"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;'>"+regNo+"</td>";
					patientListTag += "<td style='padding: 5px;font-size: 14px;text-align: center;'><input type='radio' name='patientSelect1234' onclick='redirectAppointment(\""+patID+"\");'></td>";
					patientListTag += "</tr>";

				}
				
				console.log("Check :: "+check);
				
				patientListTag += "</tbody></table>";
				
				if(check == 0){
					
					document.forms["apptPatFormID"].action = "RenderAddPatientAppointment";
					document.forms["apptPatFormID"].submit();
					
					
				}else if(check == 1){
					
					$("#patientIDIDID1").val(patID);
					
					document.forms["apptPatFormID"].action = "RenderAddPatientAppointment";
					document.forms["apptPatFormID"].submit();
					
					
				}else{
					
					//$('#patientErrID').html("");
					
					$('#patientDetailsListID123').html(patientListTag);
					
					$("#patientDetailModal1").modal('hide');
					
					$('#firstNameID1').val(firstName);
					$('#middleNameID1').val(middleName);
					$('#lastNameID1').val(lastName);
					$('#patMobileNoID1').val(mobNo1);
					$('#clinicRegNoID1').val("");
					
					$('#multiPatDetails1').modal('show');
					
				}
				
			}
		};
		xmlhttp.open("POST", "VerifyPatientDetails?firstName="+fName+"&middleName="+mName+
				"&mobileNo="+mobileNo1+"&lastName="+lName, true);
		xmlhttp.send();
	}
	
	//Function to set patient od of selected patient into apptPatientID1 field
	function setPatientID(patientID){
		$('#apptPatientID1').val(patientID);
		
		$('#searchPatientID').val(patientID);
		
		//Checking whether selected patient exists with the same clinic or not,
		//if not then give message saying 'Patient not registered to this clinic. 
		//Selecting this patient will register them to this clinic.', else proceed further.
		verifyPatientExistsToCurrClinic(patientID);
	}
	
	function redirectAppointment(patientID){
		
		$("#patientIDID1").val(patientID);
		
		document.forms["apptPatFormID1"].action = "RenderAddPatientAppointment";
		document.forms["apptPatFormID1"].submit();
	}
	
	function submitAddNewPatientForm(){
		document.forms["apptPatFormID2"].action = "RenderAddPatientAppointment";
		document.forms["apptPatFormID2"].submit();
	}
	
	</script>
    
    <!-- Ends -->
    
    
    
    <!-- function to verify patient exists to current clinic or not -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function verifyPatientExistsToCurrClinic(patientID) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].patCheck;

				}
				console.log('...'+check+'...');
				if(check == 1){
					return true;
				}else{
					$("#patientVerifyAlertModal").modal('show');
				}

			}
		};
		xmlhttp.open("GET", "VerifyPatientExistsToCurrClinic?patientID="+patientID , true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    <script type="text/javascript" charset="UTF-8">
    
    	function getPresentDates(inputID){
    		
    		setTimeout(function(){
    			
    		},500);
    	}
    
    </script>
    
    <!-- Retrieving appointment duration based on visit type ID -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function getDateTime(startTime, startDate, visitTypeID) {
		
		var FinalTime = startDate + "T" + startTime;
		$('#updatestartTimeID').val(FinalTime);
		
		getAppointmentDuration(visitTypeID, FinalTime);
	}
	
	function getAppointmentDuration(visitTypeID, startDate) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var appointmentDuration = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					appointmentDuration = array.Release[i].visitDuration;

				}
				
				console.log("visitTypeID, startDate: "+visitTypeID+"-"+startDate);
				//Calculation end time
				var newDateTime = startDate;
				
		        var array = newDateTime.split("T");

		        var newDate = array[0];

		        var timeArr = array[1].split(":");

		        /* var minute = parseInt(timeArr[1]) + parseInt(appointmentDuration); */
		        var minute = parseInt(timeArr[1]);
		        
		       // alert(minute);

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
		        
		        //alert(newFinalTime);

		        var finalNewDateTime = newDate + "T" + newFinalTime + ":" + second;
		        
		        $('#endTimeID1').val(finalNewDateTime);
		        $('#endTimeID3').val(finalNewDateTime);
		        $('#endTimeID2').val(finalNewDateTime);
		        $('#apptEndTimeID').val(newFinalTime + ":" + second);
		        $('#apptEndTimeID1').val(newFinalTime + ":" + second);
		        $('#apptEndTimeID2').val(newFinalTime + ":" + second);
		        $('#eHHID').val(newFinalTime + ":" + second);
		       
			}
		};
		xmlhttp.open("GET", "GetAppointmentDuration?visitTypeID="+visitTypeID , true);
		xmlhttp.send();
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
    
    <!-- Ends -->
    
    <!-- Register appointment for patient -->
    
    
    <script type="text/javascript">
    
    	function registerAppointment1(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID){
    		
    		var walkIn = 0;
    		
    		if(visitTypeID == "000"){
    			alert('Please select appointment type.');
    		}else if(apptDate == ""){
    			alert('Please enter appointment date.');
    		}else if(apptEndTime == ""){
    			alert('Appointment end time is blank. Please select/re-select appointment type to auto generate appointment end time.');
    		}else{
    			$("#singlePatSubmtBtnID").attr("disabled", true);
    			$("#multiplePatSubmtBtnID").attr("disabled", true);
    			
    			if($('#walkInID').is(":checked")){
    				walkIn = $('#walkInID').val();
    			}
    			
    			registerAppointment(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID, walkIn);
    		}
    		
    	}
    	
		function registerAppointment3(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID){
			
			var walkIn = 0;
    		
    		if(visitTypeID == "000"){
    			alert('Please select appointment type.');
    		}else if(apptDate == ""){
    			alert('Please enter appointment date');
    		}else if(apptEndTime == ""){
    			alert('Appointment end time is blank. Please select/re-select appointment type to auto generate appointment end time.');
    		}else{
    			$("#singlePatSubmtBtnID").attr("disabled", true);
    			$("#multiplePatSubmtBtnID").attr("disabled", true);
    			
    			if($('#walkInID1').is(":checked")){
    				walkIn = $('#walkInID1').val();
    			}
    			
    			registerAppointment(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID, walkIn);
    		}
    		
    	}
    	
		function registerAppointment2(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID){
    		
    		if(visitTypeID == "000"){
    			alert('Please select appointment type.');
    		}else if(patientFNameID2 == ""){
    			alert('Please enter patient first name.');
    		}else if(patientLNameID2 == ""){
    			alert('Please enter patient last name.');
    		}else if(patientMobNoID2 == ""){
    			alert('Please enter patient mobile no.');
    		}else if(apptDate == ""){
    			alert('Please enter appointment date');
    		}else if(apptEndTime == ""){
    			alert('Appointment end time is blank. Please select/re-select appointment type to auto generate appointment end time.');
    		}else{
    			registerAppointment(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID,0);
    		}
    		
    	}
    
    </script>
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function registerAppointment(startDate, endDate, agenda, patientID, apptDate, apptStartTime, visitTypeID, apptEndTime, fName, mName, lName, mobileNo, regNo, apptByID, walkIn) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var statuMsg = "";

				for ( var i = 0; i < array.Release.length; i++) {
					
					statuMsg = array.Release[i].Msg;

				}
				
				console.log("status msg :: "+statuMsg);
				
				if(statuMsg == "Success"){
					
					//Retrieving patient details based on patientID
					//retrievePatientDetails(patientID, startDate, endDate, agenda);
					//location.reload();
					window.location.assign("Welcome.jsp");
					
					//Calling createEvent function in order to display event added into Calendar
					//createEvent(startDate, endDate, agenda, "Booked");
					
					//$('#singlePatDetails').modal('hide');
					$('#singlePatDetailsCloseID').click();
					$('#newPatietModal').modal('hide');
					$('#multiPatDetails').modal('hide');
					
				}else{
					
					//$('#singlePatDetails').modal('hide');
					$('#singlePatDetailsCloseID').click();
					$('#newPatietModal').modal('hide');
					$('#multiPatDetails').modal('hide');
					
					$("#apptErrorMsgModal").modal('show');
					
				}

			}
		};
		xmlhttp.open("GET", "AddAppointment?visitTypeID="+visitTypeID+"&patientID="+patientID+
				"&apptDate="+apptDate+"&apptStartTime="+apptStartTime+"&apptEndTime="+apptEndTime+"&walkIn="+walkIn+
				"&firstName="+fName+"&middleName="+mName+"&lastName="+lName+"&mobileNo="+mobileNo+"&regNo="+regNo+"&cliniciaID="+apptByID, true);
		xmlhttp.send();
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
    
    
    <%
    	HashMap<Integer, String> map = daoInf.retrieveVisitTypesList(form.getClinicID());
    
    	HashMap<Integer, String> clinicianMap = daoInf.retrieveClinicianList(form.getClinicID());
    	
    	List<String> hoursList = registrationDAOinf.retrieveStartHourListByClinicStartEndHour(form.getClinicID());
    
    %>
    
    
    <!-- Update appointment status to confirmed -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function updateConfirmAppointmentStatus(patientDetail) {
		
		var array = patientDetail.split("-");
		
		var array2 = array[0].split(":");
		
		var patientID = array2[2];
		
		//getting appointment start and end date and time
		var startTime = $("#eventStartID").html();
		var endTime = $("#eventEndID").html();
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

				}
				
				if(check == 0){
					$("#apptUpdateErrID").html("Failed to confirm the appointment. Please check server logs for more details.");
					$("#apptUpdateErrorModal").modal("show");
				}else{
					$("#eventDetailModal").modal("hide");
					 window.location.assign("Welcome.jsp");
				}

			}
		};
		xmlhttp.open("GET", "UpdateConfirmAppointmentStatus?patientID="+patientID+"&apptStartTime="
				+startTime+"&apptEndTime="+endTime, true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Update appointment status to done -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function updateDoneAppointmentStatus(patientDetail, appointmentID) {
		
		var array = patientDetail.split("-");
		
		var array2 = array[0].split(":");
		
		var patientID = array2[2];
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

				}
				
				if(check == 0){
					$("#apptUpdateErrID").html("Failed to complete the appointment. Please check server logs for more details.");
					$("#apptUpdateErrorModal").modal("show");
				}else{
					$("#apptDoneAlertModal").modal("hide");
					 //location.reload();
					 window.location.assign("Welcome.jsp");
				}

			}
		};
		xmlhttp.open("GET", "UpdateDoneAppointmentStatus?patientID="+patientID+"&apptID="
				+appointmentID, true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    
    <!-- Update appointment status to cancelled -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function updateCancelledAppointmentStatus(patientDetail) {
		
		var array = patientDetail.split("-");
		
		var array2 = array[0].split(":");
		
		var patientID = array2[2];
		
		//getting appointment start and end date and time
		var startTime = $("#eventStartID").html();
		var endTime = $("#eventEndID").html();
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;

				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

				}
				
				if(check == 0){
					$("#apptUpdateErrID").html("Failed to cancel the appointment. Please check server logs for more details.");
					$("#apptUpdateErrorModal").modal("show");
				}else{
					$("#eventDetailModal").modal("hide");
					 //location.reload();
					 window.location.assign("Welcome.jsp");
				}

			}
		};
		xmlhttp.open("GET", "UpdateCancelledAppointmentStatus?patientID="+patientID+"&apptStartTime="
				+startTime+"&apptEndTime="+endTime, true);
		xmlhttp.send();
	}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Open New Visit -->
    
    <script type="text/javascript">
    
    	function openNewVisit(patientDetail, apptID){
    		
    		var array = patientDetail.split("-");
    		
    		var array1 = array[0].split(":");
    		
    		var patientID = array1[2];
    		
    		//Check what appointment status is, and according to show CRF page i.e.; if 
    		//status is Booked or Confirmed, redirect to add new visit page else render to
    		//edit visit page
    		var detailArray = patientDetail.split("(");
    		
    		var statusArray = detailArray[2].split(")");
    		
    		var aptStatus = statusArray[0];
    		
    		if(aptStatus == "Booked" || aptStatus == "Confirmed"){
    			
    			verifyVisitExistsForAppointment(patientID, apptID);
    			
    		}else if(aptStatus == "No Show" || aptStatus == "Done"){
    			return true;
    		}else{
        		
        		window.location.assign("RenderEditAppointmentVisit?patientID="+patientID+"&aptID="+apptID);
    			
    		}
    		
    	}
    
    </script>
    
    <!-- Ends -->
    
    
    <!-- Function to verify whether visit added for the appointmentID -->
    
    <script type="text/javascript">    	
    	
    	var xmlhttp;
    	if (window.XMLHttpRequest) {
    		xmlhttp = new XMLHttpRequest();
    	} else {
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}

    	function verifyVisitExistsForAppointment(patientID, apptID) {
    	
    		xmlhttp.onreadystatechange = function() {
    			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

    				var array = JSON.parse(xmlhttp.responseText);
    				
    				var check = 0;

    				for ( var i = 0; i < array.Release.length; i++) {
    					
    					check = array.Release[i].check;

    				}
    				
    				if(check == 0){
    					
    					$("#eventDetailModal").modal("hide");
    					
    					$("#pID").val(patientID);
    	        		
    	        		$("#apptID").val(apptID);
    	        		
    	        		$("#visitTypeModal").modal('show');
    				}else{
    					window.location.assign("RenderEditAppointmentVisit?patientID="+patientID+"&aptID="+apptID);
    				}

    			}
    		};
    		xmlhttp.open("GET", "VerifyVisitExistsForAppointment?patientID="+patientID+"&aptID="
    				+apptID, true);
    		xmlhttp.send();
    	}
    	
    </script>
    
    <!--  Ends -->
    
    
    <!-- Open Edit visit for Dietician and Compounder -->
    
    <script type="text/javascript">
    
    	function openEditVisit(patientDetail, apptID){
    		
    		var array = patientDetail.split("-");
    		
    		var array1 = array[0].split(":");
    		
    		var patientID = array1[2];
    		
    		//Check what appointment status is, and according to show CRF page i.e.; if 
    		//status is Booked or Confirmed, redirect to add new visit page else render to
    		//edit visit page
    		var detailArray = patientDetail.split("(");
    		
    		var statusArray = detailArray[2].split(")");
    		
    		var aptStatus = statusArray[0];
    		
    		if(aptStatus == "Booked" || aptStatus == "Confirmed"){
    			
    			$("#eventDetailModal1").modal('show');
    			
    		}else if(aptStatus == "No Show" || aptStatus == "Done"){
    			return true;
    		}else{
        		
        		window.location.assign("RenderEditAppointmentVisit?patientID="+patientID+"&aptID="+apptID);
    			
    		}
    		
    	}
    
    </script>
    
    <!-- Ends -->
    
    
    <!-- Updating appointment -->
    
    <script type="text/javascript">
    
    	function updateAppointment(patientDetail, sDate, sHH, eDate, eHH, visitType){
    		
    		if(sDate == "" || sHH == "000"){
    			alert("Appointment start date and time not selected properly. Please select appointment start date and time correctly.");
    		}else if(sDate == "" || sHH == "000"){
    			alert("Appointment end date and time not selected properly. Please select appointment end date and time correctly.");
    		}else{
    			updateAppointment1(patientDetail, sDate, sHH, eDate, eHH, visitType);
    		}
    		
    	}
    	
    	
    	var xmlhttp;
    	if (window.XMLHttpRequest) {
    		xmlhttp = new XMLHttpRequest();
    	} else {
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}

    	function updateAppointment1(patientDetail, sDate, sHH, eDate, eHH, visitType) {
    		
    		var array1 = patientDetail.split("-");
    		
    		var array2 = array1[0].split(":");
    		
    		var patientID = array2[2];
    		
    		console.log("patientID ... "+patientID);
    		console.log("patient details ... "+patientDetail);
    		
    		//getting appointment start and end date and time
    		var startTime = $("#eventStartID").html();
    		var endTime = $("#eventEndID").html();
    	
    		xmlhttp.onreadystatechange = function() {
    			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

    				var array = JSON.parse(xmlhttp.responseText);
    				
    				var check = 0;

    				for ( var i = 0; i < array.Release.length; i++) {
    					
    					check = array.Release[i].check;

    				}
    				
    				if(check == 0){
    					$("#apptUpdateErrID").html("Failed to update appointment. Please check server logs for more details.");
    					$("#apptUpdateErrorModal").modal("show");
    				}else{
    					$("#eventDetailModal").modal("hide");
    					 //location.reload();
    					 window.location.assign("Welcome.jsp");
    				}

    			}
    		};
    		xmlhttp.open("GET", "UpdateAppointment?patientID="+patientID+"&startDate="
    				+sDate+"&endDate="+eDate+"&clinicStartHH="
    				+sHH+"&clinicEndHH="+eHH+"&apptStartTime="
    				+startTime+"&apptEndTime="+endTime+"&visitTypeID="+visitType, true);
    		xmlhttp.send();
    	}
    	
    </script>
    
    <!--  Ends -->
    
    <script type="text/javascript">
    
    	function pageReload(){
    		$("#apptUpdateErrorModal").modal("hide");
    		 //location.reload();
    		 window.location.assign("Welcome.jsp");
    	}
    
    </script>
    
    
    <!-- Add New Row function for leave type -->
    
    <script type="text/javascript">
    	var counter = 1;
    	
    	function addLeaveTypeRow(leaveType, deescription, entitlementFreq, expirationFreq, entitlementCount, expirationCount){
    			
    			//Checking whether all values are selected and filled in, if
    			//yes, then only proceed further to add row else give error msg
    			if(leaveType == ""){
    				
    				alert("Please enter leave type.");
    				
    			}else if($('#autoEntYesID').is(':checked') && entitlementCount == ""){
    				
    				alert("Please enter entitlement count.");
    				
    			}else if($('#autoExpYesID').is(':checked') && expirationCount == ""){
    				
    				alert("Please enter expiration count.");
    				
    			}else{
    				
    				addLeaveTypeRow1(leaveType, deescription, entitlementFreq, expirationFreq, entitlementCount, expirationCount);
    				
    			}
			
    	}
    	
    	function addLeaveTypeRow1(leaveType, deescription, entitlementFreq, expirationFreq, entitlementCount, expirationCount){
    		
    		//Getting value of auto entitlement
			var autoEnt = "";
			var autoEnt1 = "";
			
			//Getting value of auto expiration
			var autoExp = "";
			var autoExp1 = "";
			
			//Getting value of carry over
			var carOver = "";
			var carOver1 = "";
			
			var array = ["No","Yes"]; 
			
			if($("#autoEntYesID").is(":checked")){
				autoEnt = array[$("#autoEntYesID").val()];
				autoEnt1 = $("#autoEntYesID").val();
			}else if($("#autoEntNoID").is(":checked")){
				autoEnt = array[$("#autoEntNoID").val()];
				autoEnt1 = $("#autoEntNoID").val();
			}else{
				autoEnt = "";
				autoEnt1 = "";
			}
			
			if($("#autoExpYesID").is(":checked")){
				autoExp = array[$("#autoExpYesID").val()];
				autoExp1 = $("#autoExpYesID").val();
			}else if($("#autoExpNoID").is(":checked")){
				autoExp = array[$("#autoExpNoID").val()];
				autoExp1 = $("#autoExpNoID").val();
			}else{
				autoExp = "";
				autoExp1 = "";
			}
			
			if($("#carryOverYes").is(":checked")){
				carOver = array[$("#carryOverYes").val()];
				carOver1 = $("#carryOverYes").val();
			}else if($("#carryOverNo").is(":checked")){
				carOver = array[$("#carryOverNo").val()];
				carOver1 = $("#carryOverNo").val();
			}else{
				carOver = "";
				carOver1 = "";
			}
			
			var trID = "newLTTRID"+counter;
        	var tdID = "tdID"+counter;
        	var delImgTDID = "delImgTDID"+counter;
			var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
			"<td style='text-align:center;'>"+leaveType+"<input type='hidden' name='leaveType' value='"+leaveType+"'></td>"+
			"<td style='text-align:center;'>"+deescription+"<input type='hidden' name='leaveTypeDescription' value='"+deescription+"'></td>"+
			"<td style='text-align:center;'>"+entitlementFreq+"<input type='hidden' name='entitlementFrequency' value='"+entitlementFreq+"'></td>"+
			"<td style='text-align:center;'>"+autoEnt+"<input type='hidden' name='autoEntitlement' value='"+autoEnt1+"'></td>"+
			"<td style='text-align:center;'>"+expirationFreq+"<input type='hidden' name='deductionFrequency' value='"+expirationFreq+"'></td>"+
			"<td style='text-align:center;'>"+autoExp+"<input type='hidden' name='autoDeduction' value='"+autoExp1+"'></td>"+
			"<td style='text-align:center;'>"+entitlementCount+"<input type='hidden' name='entitlementCount' value='"+entitlementCount+"'></td>"+
			"<td style='text-align:center;'>"+expirationCount+"<input type='hidden' name='expirationCount' value='"+expirationCount+"'></td>"+
			"<td style='text-align:center;'>"+carOver+"<input type='hidden' name='carryOver' value='"+carOver1+"'></td>"+
			"<td id="+delImgTDID+"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeLTTR(\"" + trID +"\",\""+counter+"\");'/></td>"+
			"</tr>";

			$(trTag).insertAfter($('#leveTypeTRID'));
			
			counter++;

			$("#typeID").val("-1");
			$("#descID").val("");
			$("#entitleFreqID").val("");
			$("#expirFreqID").val("");
			$('#autoEntYesID').prop('checked', false);
			$('#autoEntNoID').prop('checked', false);
			$('#autoExpYesID').prop('checked', false);
			$('#autoExpNoID').prop('checked', false);
			$("#entCountID").val("");
			$("#expCountID").val("");
			$('#carryOverYes').prop('checked', false);
			$('#carryOverNo').prop('checked', false);
    		
    	}

    	//Function to remove TR
    	function removeLTTR(trID, count){
        	if(confirm("Are you sure you want to remove this row?")){

            	$("#"+trID+"").remove();

            	}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Retrieving clinic LIst -->
    
    <%
    	HashMap<Integer, String> practiceMap = daoInf.retrievepracticeList();
    
    	HashMap<Integer, String> clinicMap = daoInf.retrieveClinicList(form.getPracticeID());
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
	
	
		function submitChangeClinicForm(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

			$("#changeClinicForm").submit();	
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
	
	
	<!-- Visit type  modal -->
    
    <script type="text/javascript">
    
    	function openVisitTypeModal(patientID){
    		$("#pID").val(patientID);
    		
    		$("#multiSearchPatDetails").modal('hide');
    		
    		$("#visitTypeModal").modal('show');
    	}
    
    </script>
    
    <!-- Ends -->
    
    <%
    	String myApptCheck = (String) request.getAttribute("myApptCheck");
    
    	if(myApptCheck == null || myApptCheck == ""){
    		myApptCheck = "dummy";	
    	}
    %>
    
    <script type="text/javascript">
    
    	function submitApptForm(checkVal){
    		
    		$("#checkID").val(checkVal);
    		$("#appointmentForm").submit();
    		
    	}
    	
		function submitApptReportFormToday(){
    		
    		//$("#checkID").val(checkVal);
    		$("#apptTodayFormID").attr('action', 'DownloadAppointmentReportToday');
    		$("#apptTodayFormID").submit();
    		
    	}
		
		function submitApptReportFormWeek(){
    		
    		//$("#checkID").val(checkVal);
    		$("#apptWeekFormID").attr('action', 'DownloadAppointmentReportWeek');
    		$("#apptWeekFormID").submit();
    		
    	}
		
		function submitApptReportFormMonth(){
    		
    		//$("#checkID").val(checkVal);
    		$("#apptMonthFormID").attr('action', 'DownloadAppointmentReportMonth');
    		$("#apptMonthFormID").submit();
    		
    	}
    
    </script>
    
    
    <!-- Check all check boxes for month, week and today's report -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    	
    	//for month's list
    	$("#reportCheckID").click(function () {
    	     $("input[name=reportCheck]").not(this).prop('checked', this.checked);
    	     
    	     //If select all check box is selected then only add all hidden input 
    	     //elements to div else remove all input elements.
    	     if($('#reportCheckID').is(':checked')){
    	    	 
    	    	//Getting length of all input type checkboxes and creating hidden input 
        	     //elements for receiptID and patientID 
        	     var checkBoxLen = $('input[name=reportCheck]').length;
        	     
        	     var temp = checkBoxLen - 1;
        	     
        	     var i = 0;
        	     
        	     var temp1 = 1;
        	     
        	     for(i; i < temp; i++){
        	    	 
        	    	 //getting receiptID and patientID from checkbox values
        	    	 var IDs = $('input[name=reportCheck]').eq(temp1).val();
        	    	 
        	    	 //Splitting IDs by - in order  to get receiptID and patientID from it
        	    	 var IDArray = IDs.split("-");
        	    	 
        	    	 var appointmentID = IDArray[0];
        	    	 
        	    	 var patientID = IDArray[1];
        	    	 
        	    	 $('#reportDivID').append("<input type='hidden' name='appointmentID' value='"+appointmentID+"'> "+
        	    			 "<input type='hidden' name='patID' value='"+patientID+"'>");
        	    	 
        	    	 temp1++;
        	    	 
        	     }
        	     
        	     //enabling submit buttons
        	    // $('#pdfID').attr('disabled', false);
        	     $('#excelID').attr('disabled', false);
        	     
        	     $('#dwldApptReportMonthBtnID').attr('disabled', false);
    	    	 
    	     }else{
    	    	 
    	    	 $('#reportDivID').html("");
    	    	 
    	    	 //disabling submit buttons
    	    	 //$('#pdfID').attr('disabled', true);
    	    	 $('#excelID').attr('disabled', true);
    	    	 
    	    	 $('#dwldApptReportMonthBtnID').attr('disabled', true);
    	    	 
    	     }

    	 });
    	
    	//for week's list
    	$("#reportWeekCheckID").click(function () {
   	     $('input[name=reportWeekCheck]').not(this).prop('checked', this.checked);
   	     
   	     //If select all check box is selected then only add all hidden input 
   	     //elements to div else remove all input elements.
   	     if($('#reportWeekCheckID').is(':checked')){
   	    	 
   	    	//Getting length of all input type checkboxes and creating hidden input 
       	     //elements for receiptID and patientID 
       	     var checkBoxLen = $('input[name=reportWeekCheck]').length;
       	     
       	     var temp = checkBoxLen - 1;
       	     
       	     var i = 0;
       	     
       	     var temp1 = 1;
       	     
       	     for(i; i < temp; i++){
       	    	 
       	    	 //getting receiptID and patientID from checkbox values
       	    	 var IDs = $('input[name=reportWeekCheck]').eq(temp1).val();
       	    	 
       	    	 //Splitting IDs by - in order  to get receiptID and patientID from it
       	    	 var IDArray = IDs.split("-");
       	    	 
       	    	 var appointmentID = IDArray[0];
       	    	 
       	    	 var patientID = IDArray[1];
       	    	 
       	    	 $('#reportWeekDivID').append("<input type='hidden' name='appointmentID' value='"+appointmentID+"'> "+
       	    			 "<input type='hidden' name='patID' value='"+patientID+"'>");
       	    	 
       	    	 temp1++;
       	    	 
       	     }
       	     
       	     //enabling submit buttons
       	    // $('#pdfID').attr('disabled', false);
       	     $('#excelWeekID').attr('disabled', false);
       	     
       	  $('#dwldApptReportWeekBtnID').attr('disabled', false);
   	    	 
   	     }else{
   	    	 
   	    	 $('#reportWeekDivID').html("");
   	    	 
   	    	 //disabling submit buttons
   	    	 //$('#pdfID').attr('disabled', true);
   	    	 $('#excelWeekID').attr('disabled', true);
   	    	 
   	    	 $('#dwldApptReportWeekBtnID').attr('disabled', true);
   	    	 
   	     }

   	 });
    	
    	
    	//for today's list
    	$("#reportTodayCheckID").click(function () {
   	     $('input[name=reportTodayCheck]').not(this).prop('checked', this.checked);
   	     
   	     //If select all check box is selected then only add all hidden input 
   	     //elements to div else remove all input elements.
   	     if($('#reportTodayCheckID').is(':checked')){
   	    	 
   	    	//Getting length of all input type checkboxes and creating hidden input 
       	     //elements for receiptID and patientID 
       	     var checkBoxLen = $('input[name=reportTodayCheck]').length;
       	     
       	     var temp = checkBoxLen - 1;
       	     
       	     var i = 0;
       	     
       	     var temp1 = 1;
       	     
       	     for(i; i < temp; i++){
       	    	 
       	    	 //getting receiptID and patientID from checkbox values
       	    	 var IDs = $('input[name=reportTodayCheck]').eq(temp1).val();
       	    	 
       	    	 //Splitting IDs by - in order  to get receiptID and patientID from it
       	    	 var IDArray = IDs.split("-");
       	    	 
       	    	 var appointmentID = IDArray[0];
       	    	 
       	    	 var patientID = IDArray[1];
       	    	 
       	    	 $('#reportTodayDivID').append("<input type='hidden' name='appointmentID' value='"+appointmentID+"'> "+
       	    			 "<input type='hidden' name='patID' value='"+patientID+"'>");
       	    	 
       	    	 temp1++;
       	    	 
       	     }
       	     
       	     //enabling submit buttons
       	    // $('#pdfID').attr('disabled', false);
       	     $('#excelTodayID').attr('disabled', false);
       	     
       		  $("#dwldApptReportBtnID").attr('disabled', false);
   	    	 
   	     }else{
   	    	 
   	    	 $('#reportTodayDivID').html("");
   	    	 
   	    	 //disabling submit buttons
   	    	 //$('#pdfID').attr('disabled', true);
   	    	 $('#excelTodayID').attr('disabled', true);
   	    	 
   	    	 $("#dwldApptReportBtnID").attr('disabled', true);
   	    	 
   	     }

   	 });
    	
    });
    
    //For month's report
    function reportVal(checkValue){
    	
    	if($('#'+checkValue+'').is(':checked')){
    		
    		var IDs = $('#'+checkValue+'').val();
    		
    		//Splitting IDs by - in order  to get receiptID and patientID from it
	    	 var IDArray = IDs.split("-");
	    	 
	    	 var appointmentID = IDArray[0];
	    	 
	    	 var patientID = IDArray[1];
	    	 
	    	 $('#reportDivID').append("<input type='hidden' class='"+checkValue+"' name='appointmentID' value='"+appointmentID+"'> "+
	    			 "<input type='hidden' name='patID' class='"+checkValue+"' value='"+patientID+"'>");
	    	 
	    	 //enabling submit buttons
    	    // $('#pdfID').attr('disabled', false);
    	     $('#excelID').attr('disabled', false);
    	     $('#dwldApptReportMonthBtnID').attr('disabled', false);
    	
    	}else{
    		
    		$('.'+checkValue+'').remove();
    		
    	}
    }
    
    //For week's report
 	function reportWeekVal(checkValue){
    	
    	if($('#'+checkValue+'').is(':checked')){
    		
    		var IDs = $('#'+checkValue+'').val();
    		
    		//Splitting IDs by - in order  to get receiptID and patientID from it
	    	 var IDArray = IDs.split("-");
	    	 
	    	 var appointmentID = IDArray[0];
	    	 
	    	 var patientID = IDArray[1];
	    	 
	    	 $('#reportWeekDivID').append("<input type='hidden' class='"+checkValue+"' name='appointmentID' value='"+appointmentID+"'> "+
	    			 "<input type='hidden' name='patID' class='"+checkValue+"' value='"+patientID+"'>");
	    	 
	    	 //enabling submit buttons
    	    // $('#pdfID').attr('disabled', false);
    	     $('#excelWeekID').attr('disabled', false);
    	     
    	     $('#dwldApptReportWeekBtnID').attr('disabled', false);
    	
    	}else{
    		
    		$('.'+checkValue+'').remove();
    		
    	}
    }
    
 	//For today's report
 	function reportTodayVal(checkValue){
    	
    	if($('#'+checkValue+'').is(':checked')){
    		
    		var IDs = $('#'+checkValue+'').val();
    		
    		//Splitting IDs by - in order  to get receiptID and patientID from it
	    	 var IDArray = IDs.split("-");
	    	 
	    	 var appointmentID = IDArray[0];
	    	 
	    	 var patientID = IDArray[1];
	    	 
	    	 $('#reportTodayDivID').append("<input type='hidden' class='"+checkValue+"' name='appointmentID' value='"+appointmentID+"'> "+
	    			 "<input type='hidden' name='patID' class='"+checkValue+"' value='"+patientID+"'>");
	    	 
	    	 //enabling submit buttons
    	    // $('#pdfID').attr('disabled', false);
    	     $('#excelTodayID').attr('disabled', false);
    	     
    	     $("#dwldApptReportBtnID").attr('disabled', false);
    	
    	}else{
    		
    		$('.'+checkValue+'').remove();
    		
    	}
    }
    
    </script>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    	function addNewPatient(firstName, middleName, lastName, clinicRegNo, mobNo1, apptDate, startTime){
    		window.location.assign("RenderAddNewPatientAppointment?firstName="+firstName+"&middleName="+middleName+"&lastName="+lastName+"&mobile="+mobNo1+"&appointmentDate="+apptDate+"&aptTimeFrom="+startTime);
    	}
    	
    	
    	function addNewSearchPatient(firstName, middleName, lastName, mobNo1){
    		$("#multiSearchPatDetails").modal('hide');
    		window.location.assign("RenderAddNewPatient?firstName="+firstName+"&middleName="+middleName+"&lastName="+lastName+"&mobile="+mobNo1);
    	}
    	
    </script>
    
    
    <!-- function to set interval in order to refresh calendar events -->
    
    <script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		setInterval(function(){$('#weekCalendar').fullCalendar('refetchEvents')}, 30000);
    		setInterval(function(){$('body').css('padding-right','0px')}, 1000);
    		
        });
    </script>
    
    <!-- Ends -->
    
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
	    function disableUser(url){
			/* if(confirm("Are uou sure you want to delete this participant?")){
				document.location = url;
			} */
			$("#disableActionID").val(url);
			$("#disableModalID").modal("show");
		}
		
		function renderDisablePatient(url, status){
			
			if(confirm("Are you sure you want to delete this patient?")){
				document.location = url+"&activityStatus="+status;
			}
			
		}
    </script>
    
    <!-- Ends -->
    
    <!-- Setting font-size for calendar event's text -->
    
    <style type="text/css">
    	.fc-content{
    		font-size: 13px;
    		font-weight: bold;
    		word-wrap: break-word;
    	}
    </style>
    
    <!-- Ends -->
    
    <!-- Function to submit view appointment form -->
    
    <script type="text/javascript">
    
    	function submitViewApptForm(){
    		$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
    		
    		$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

    		$("#viewApptForm").submit();
    	}
    	
    	function removeBodyPadding(){
    		//alert("hii");
    	}
    
    	function checkSearchPatientForm(){
    		
    		$("#searchPatientDetailModal").modal('show');
    	}
    			
    </script>
    
    <!-- Ends -->
    
    <style type="text/css">
		#multiPatDetails{
	 		overflow-y:scroll;		
		}
		
		#multiPatDetails1{
			overflow-y:scroll;
		}
		
		span.required {
	   color: red !important;
	}
	
		
	</style>
	
	
	<!-- done current appointment and take next appointment -->
    
    <script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function takeNextAppointment(patientDetail, appointmentID) {
		
		var array = patientDetail.split("-");
		
		var array2 = array[0].split(":");
		
		var patientID = array2[2];
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				/* var firstName = "";
				var middleName = "";
				var lastName = ""; */
				var patientID = "";
			/* 	var emailID = "";
				var mobile = "";
				var registrationNo = "";
				var emailCheck = ""; */

				for ( var i = 0; i < array.Release.length; i++) {
					
					/* firstName = array.Release[i].firstName;
					middleName = array.Release[i].middleName;
					lastName = array.Release[i].lastName; */
					patientID = array.Release[i].patientID;
					/* emailID = array.Release[i].emailID;
					mobile = array.Release[i].mobile;
					registrationNo = array.Release[i].registrationNo;
					emailCheck = array.Release[i].emailCheck; */

				}
				
				 window.location.assign("RenderAddPatientAppointment?patientID="+patientID);

			}
		};
		xmlhttp.open("GET", "UpdateDoneAppointmentStatusAndRetrievePatientDetails?patientID="+patientID+"&apptID="
				+appointmentID, true);
		xmlhttp.send();
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
    
    </script>
    
  </head>

  <body class="nav-md" onload="getClinicStartEndTime();">
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  <!-- disable status modal -->
  
  <div id="disableModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">SELECT STATUS</h4>
        </div>
      
        <input type="hidden" name="" id="disableActionID">
        
        <div class="modal-body">
        
        	<div class="row" align="center">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<select name="" id="statusID" class="form-control" required="required">
        					<option value="Inactive">Inactive</option>
        					<option value="Expired">Expire</option>
        					<option value="Lost To Follow-Up">Lost To Follow-Up</option>						
						</select>
        		
        		</div>
        	
        	</div>         

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary" onclick="renderDisablePatient(disableActionID.value,statusID.value);">Submit</button>
          </center>
        </div>
		
      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  <!-- date and time less than alert for calendar event modal -->
  
  <div id="calendarDateTimeAlertModal" class="modal fade" style="z-index: 2000;"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;" id="alertMsgID"></h4>
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
  
  
  <!-- Patient verification alert modal -->
  
  <div id="patientVerifyAlertModal" class="modal fade" style="z-index: 2000;"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;">Patient not registered to this clinic. Selecting this patient will register them to this clinic.</h4>
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
  
  
  <!-- Visit type modal -->
  
  <div id="visitTypeModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">SELECT VISIT TYPE</h4>
        </div>
        <form action="ChangeVisitTypeID" method="POST">
        
        <input type="hidden" name="patientID" id="pID">
        
        <input type="hidden" name="aptID" id="apptID">
        
        <div class="modal-body">
        
        	<div class="row" align="center">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<select name="visitTypeID" id="visitTypeID" class="form-control" required="required">
        					<option value="000">Select Visit Type*</option>
        				
						<%
						
							for(Integer key: map.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=map.get(key)%></option>
						
						<%
							}
						%>
						
						</select>
        		
        		</div>
        	
        	</div>         

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary" onclick="return submitForm();">Submit</button>
          </center>
        </div>
		</form>
      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- Register open alert modal -->
  
  <div id="registerErrorModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
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
  
  
  
  <!-- Appointment udpate error msg modal -->
  
  <div id="apptUpdateErrorModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel">ERROR MESSAGE</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<center>
        				<font style="color:red; font-size: 14px;" id="apptUpdateErrID"></font>
        			</center>
        		
        		</div>
        	
        	</div>

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" onclick="pageReload();">Close</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- Calendar appointment modal -->
  
  <div id="patientDetailModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">PATIENT DETAILS</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Name</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientFNameID" class="form-control" placeholder="First Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientMNameID" class="form-control" placeholder="Middle Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientLNameID" class="form-control" placeholder="Last Name">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Mobile Number</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="number" id="patientMobNoID"  class="form-control" placeholder="Mobile Number">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Reg. No.</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientRegNoID" value="" class="form-control" placeholder="Reg. No." readonly="readonly">
        			</div>
        		
        		</div>
        	
        	</div>
         
          <input type="hidden" name="" id="startTimeID">
          <input type="hidden" name="" id="agendaID">

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" id="patientDetailsCloseID" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="checkPatient(patientFNameID.value, patientMNameID.value, patientLNameID.value, patientMobNoID.value, startTimeID.value, agendaID.value);">Check Patient</button>
        </div>

      </div>
    </div>
  </div>
  
   <!-- Search Patient Details Modal -->
  
  <div id="searchPatientDetailModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">PATIENT DETAILS</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Name</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientSearchFNameID" class="form-control" placeholder="First Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientSearchMNameID" class="form-control" placeholder="Middle Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientSearchLNameID" class="form-control" placeholder="Last Name">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Mobile Number</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="number" id="patientSearchMobNoID"  class="form-control" placeholder="Mobile Number">
        			</div>
        		</div>
        	</div>
        </div>
        
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" id="patientSearchDetailsCloseID" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="checkSearchPatient(patientSearchFNameID.value, patientSearchMNameID.value, patientSearchLNameID.value, patientSearchMobNoID.value);">Check Patient</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- END -->
  
  
  <!-- multiple patient details modal -->
  
  <div id="multiSearchPatDetails" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center;">PATIENT DETAIL</h4>
        </div>
        <div class="modal-body">
       
        	<div class="row">
        		<div class="col-md-12 col-sm-12 col-xs-12" id="patientSearchDetailsListID" style="padding:15px;">
	        		
        		</div>
        	</div>
        
	        <input type="hidden" id="firstSearchNameID">
			<input type="hidden" id="middleSearchNameID">
	       	<input type="hidden" id="lastSearchNameID">
	       	<input type="hidden" id="patSearchMobileNoID">
		    <input type="hidden" id="searchPatientID">
		        	
        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="addNewSearchPatient(firstSearchNameID.value, middleSearchNameID.value, lastSearchNameID.value, patSearchMobileNoID.value);">Add New Patient</button>
          <button type="button" class="btn btn-primary" id="multipleSearchPatSubmtBtnID" onclick="openVisitTypeModal(searchPatientID.value);">Consultation</button>
        </div>

      </div>
    </div>
  </div>
  
  <div id="patientDetailModal1" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">PATIENT DETAILS</h4>
        </div>
        <div class="modal-body">
        
        <form id="apptPatFormID" name="apptPatFormID">
        
        <input type="hidden" id="patientIDIDID1" name="patientID">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Name</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="fNameID" name="firstName" class="form-control" placeholder="First Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="mNameID" name="middleName" class="form-control" placeholder="Middle Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="lNameID" name="lastName" class="form-control" placeholder="Last Name">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Mobile Number</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="number" id="mobID" name="mobile" class="form-control" placeholder="Mobile Number">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Reg. No.</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="regNoID" name="registrationNo" class="form-control" placeholder="Reg. No." readonly="readonly">
        			</div>
        		
        		</div>
        	
        	</div>
		</form>
        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" id="patientDetailsCloseID" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="checkPatientForAppointment(fNameID.value, mNameID.value, mobID.value, regNoID.value);">Check Patient</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- New Patient appointment modal -->
  
  <div id="newPatietModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">APPOINTMENT FOR NEW PATIENT</h4>
        </div>
        <div class="modal-body">
        
        	<input type="hidden" id="apptPatientID2">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Name*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientFNameID2" class="form-control" placeholder="First Name*">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientMNameID2" class="form-control" placeholder="Middle Name">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientLNameID2" class="form-control" placeholder="Last Name*">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Mobile Number*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientMobNoID2" class="form-control" placeholder="Mobile Number*">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Reg. No.*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientRegNoID2" readonly="readonly" class="form-control" placeholder="Reg. No.">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Date*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="apptDateID1" class="form-control" placeholder="Appointment Date" oninput="getPresentDates(this.value);">
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-6">
        				<font style="font-size: 14px;">Appt. Start Time</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
				             <input class="form-control" size="16" id="apptStartTimeID1" type="text" value="" readonly>
				                <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	                	</div>
			                	
        				<!-- <input type="hidden" id="apptStartTimeID1" class="form-control" placeholder="Appt Start Time" readonly="readonly"> -->
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Type*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        			
        				<select id="apptTypeID1" class="form-control" onchange="getAppointmentDuration(apptTypeID1.value, startTimeID3.value);">
        					<option value="000">Select Appt. Type</option>
        				
						<%
						
							for(Integer key: map.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=map.get(key)%></option>
						
						<%
							}
						%>
						
						</select>

        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment By</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<select id="apptByID" class="form-control">
        					<option value="-1">Select Appt. By</option>
        				
						<%
						
							for(Integer key: clinicianMap.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=clinicianMap.get(key)%></option>
						
						<%
							}
						%>
						
						</select>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<div class="col-md-2 col-sm-2 col-xs-2">
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-4">
						<font style="font-size: 14px;">Appt. End Time</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-4">
        				<input type="text" id="apptEndTimeID1" class="form-control" placeholder="Appt End Time" readonly="readonly">
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-2">
        			</div>
        		
        		</div>
        	
        	</div>
        	
          <input type="hidden" name="" id="endTimeID3">         
          <input type="hidden" name="" id="startTimeID3">
          <input type="hidden" name="" id="agendaID3">
		</div>
    
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="registerAppointment2(startTimeID3.value, endTimeID3.value, agendaID3.value, apptPatientID2.value, apptDateID1.value, apptStartTimeID1.value, apptTypeID1.value, apptEndTimeID1.value, patientFNameID2.value, patientMNameID2.value, patientLNameID2.value, patientMobNoID2.value, patientRegNoID2.value, apptByID.value);">Add Appointment</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- single patient details modal -->
  
  <div id="singlePatDetails" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">PATIENT DETAIL</h4>
        </div>
        <div class="modal-body">
        
        	<input type="hidden" id="apptPatientID">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Name</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientFNameID1" class="form-control" placeholder="First Name" readonly="readonly">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientMNameID1" class="form-control" placeholder="Middle Name" readonly="readonly">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientLNameID1" class="form-control" placeholder="Last Name" readonly="readonly" >
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Mobile Number</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="number" id="patientMobNoID1" class="form-control" placeholder="Mobile Number" readonly="readonly">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Reg. No.</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="patientRegNoID1" class="form-control" placeholder="Reg. No." readonly="readonly">
        			</div>
        		</div>
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Date</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="apptDateID" class="form-control" placeholder="Appointment Date">
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-6">
        				<font style="font-size: 14px;">Appt. Start Time</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
				             <input class="form-control" size="16" id="apptStartTimeID" type="text" value="" readonly>
				             <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	                	</div>
        			</div>
        		</div>
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Type</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        			
        				<select id="apptTypeID" class="form-control" onchange="getAppointmentDuration(apptTypeID.value, startTimeID1.value);">
        					<option value="000">Select Appt. Type</option>
        				
						<%
						
							for(Integer key: map.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=map.get(key)%></option>
						
						<%
							}
						%>
						
						</select>

        			</div>
        			
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment By</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<select id="apptByID1" class="form-control">
        					<option value="-1">Select Appt. By</option>
        				
						<%
						
							for(Integer key: clinicianMap.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=clinicianMap.get(key)%></option>
						
						<%
							}
						%>
						
						</select>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appt. End Time</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
						<input type="text" id="apptEndTimeID" class="form-control" placeholder="Appt End Time" readonly="readonly">
        			</div>
        			
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Walk In?</font>
        			</div>
        			
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="checkbox" name="walkIn" id="walkInID" value="1" style="zoom: 1.8;">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<input type="hidden" name="" id="startTimeID1">
          <input type="hidden" name="" id="endTimeID1">
          <input type="hidden" name="" id="agendaID1">
        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal" id="singlePatDetailsCloseID">Close</button>
          <button type="button" class="btn btn-primary" id="singlePatSubmtBtnID" onclick="registerAppointment1(startTimeID1.value, endTimeID1.value, agendaID1.value, apptPatientID.value, apptDateID.value, apptStartTimeID.value, apptTypeID.value, apptEndTimeID.value, patientFNameID1.value, patientMNameID1.value, patientLNameID1.value, patientMobNoID1.value, patientRegNoID1.value, apptByID1.value);">Add Appointment</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- multiple patient details modal -->
  
  <div id="multiPatDetails" class="modal fade"0 role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center;">PATIENT DETAIL</h4>
        </div>
        <div class="modal-body">
        
        	<input type="hidden" id="apptPatientID1">
        
        	<div class="row">
        		<div class="col-md-12 col-sm-12 col-xs-12" id="patientDetailsListID" style="padding:15px;"></div>
        	</div>
        	
        	<input type="hidden" id="firstNameID">
        	<input type="hidden" id="middleNameID">
        	<input type="hidden" id="lastNameID">
        	<input type="hidden" id="clinicRegNoID">
        	<input type="hidden" id="patMobileNoID">
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Date</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="apptDateID2" class="form-control" placeholder="Appointment Date">
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-6">
        				<font style="font-size: 14px;">Appt. Start Time</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
				             <input class="form-control" size="16" id="apptStartTimeID2" type="text" value="" readonly>
				                <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	                	</div>
	                	
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment Type</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        			
        				<select id="apptTypeID2" class="form-control" onchange="getAppointmentDuration(apptTypeID2.value, startTimeID2.value);">
        					<option value="000">Select Appt. Type</option>
        				
						<%
						
							for(Integer key: map.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=map.get(key)%></option>
						
						<%
							}
						%>
						
						</select>

        			</div>
        			
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Appointment By</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<select id="apptByID2" class="form-control">
        					<option value="-1">Select Appt. By</option>
        				
						<%
						
							for(Integer key: clinicianMap.keySet()){
							
						%>
						
							<option value="<%=key%>"><%=clinicianMap.get(key)%></option>
						
						<%
							}
						%>
						
						</select>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
						<font style="font-size: 14px;">Appt. End Time</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="text" id="apptEndTimeID2" class="form-control" placeholder="Appt End Time" readonly="readonly">
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Walk In?</font>
        			</div>
        			
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<input type="checkbox" name="walkIn" id="walkInID1" value="1" style="zoom: 1.8;">
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<input type="hidden" name="" id="startTimeID2">
          <input type="hidden" name="" id="endTimeID2">
          <input type="hidden" name="" id="agendaID2">

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="addNewPatient(firstNameID.value, middleNameID.value, lastNameID.value, clinicRegNoID.value, patMobileNoID.value, apptDateID2.value, apptStartTimeID2.value);">Add New Patient</button>
          <button type="button" class="btn btn-primary" id="multiplePatSubmtBtnID" onclick="registerAppointment3(startTimeID2.value, endTimeID2.value, agendaID2.value, apptPatientID1.value, apptDateID2.value, apptStartTimeID2.value, apptTypeID2.value, apptEndTimeID2.value, '', '', '', '', '', apptByID2.value);">Add Appointment</button>
        </div>

      </div>
    </div>
  </div>
  
  
  
  <div id="multiPatDetails1" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center;">PATIENT DETAIL</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" id="patientDetailsListID123" style="padding:15px;">
        		
        		</div>
        	
        	<form action="" name="apptPatFormID1" id="apptPatFormID1">
        		<input type="hidden" id="patientIDID1" name="patientID">
        	</form>
        	
        	<form action="" name="apptPatFormID2" id="apptPatFormID2">
        	
        		<input type="hidden" name="firstName" id="firstNameID1">
	        	<input type="hidden" name="middleName" id="middleNameID1">
	        	<input type="hidden" name="lastName" id="lastNameID1">
	        	<input type="hidden" name="mobile" id="patMobileNoID1">
	        	<input type="hidden" name="registrationNo" id="clinicRegNoID1">
        		
        	</form>
        	
        	</div>

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="submitAddNewPatientForm();">Add New Patient</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  <!-- Appointment Error msg modal -->
  
  <div id="apptErrorMsgModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel">ERROR MESSAGE</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<center>
        				<font style="color:red; font-size: 14px;">Failed to add appointment. Please check server logs for more details.</font>
        			</center>
        		
        		</div>
        	
        	</div>

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  
  <!-- done appointment alert message modal -->
  
  <div id="apptDoneAlertModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" align="center" id="myModalLabel" style="font-weight: bold;">Are you sure that the appointment is completed?</h4>
        </div>
        <input type="hidden" id="patDetailsID123">
        <input type="hidden" id="doneApptID">
        
        <div class="modal-footer" align="center">
        
        	<center>
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-success" onclick="updateDoneAppointmentStatus(patDetailsID123.value, doneApptID.value);">Yes</button>
        		<button type="button" class="btn btn-warning" onclick="takeNextAppointment(patDetailsID123.value, doneApptID.value);">Take Next Appointment</button>
        	</center>
          
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->
  
  
  
  <!-- event details modal -->
  
  <div id="eventDetailModal" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" align="center" id="myModalLabel" style="font-weight: bold;">APPOINTMENT DETAIL</h4>
        </div>
        <div class="modal-body">
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<font style="font-size: 14px;font-weight: bold;">Patient Details: </font>
        			<font style="font-size: 14px;" id="patDetailsID"></font>
        				<input type="hidden" id="patDetailsID1">
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<font style="font-size: 14px;font-weight: bold;">Appt. Starts at: </font>
        			<font style="font-size: 14px;" id="eventStartID"></font>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" style="margin-top:15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
        		
        			<font style="font-size: 14px;font-weight: bold;">Appt. Ends at: </font>
        			<font style="font-size: 14px;" id="eventEndID"></font>
        		
        		</div>
        	
        	</div>
        	
        	<hr>
        	
        	<div class="row" style="margin-top: 15px;">
        		<font style="font-size: 14px; font-weight: bold;">Update Appointment</font>
        	</div>
        	
        	<hr>
        	
        	<div class="row" style="margin-top: 15px;">
        	
        		<div class="col-md-3">
	        		<font style="font-size: 14px;font-weight: bold;">Appointment Type</font>
	        	</div>
	        	<div class="col-md-3">
	        		<select id="updateapptTypeID" class="form-control" onchange="getAppointmentDuration(updateapptTypeID.value, updatestartTimeID.value);">
	        			<option value="000">Select Appt. Type</option>
	        			<%
							
							for(Integer key: map.keySet()){
								
						%>
							
						<option value="<%=key%>"><%=map.get(key)%></option>
							
						<%
							}
						%>
							
					</select>
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
        		<div class="col-md-3">
        			<font style="font-size: 14px;font-weight: bold;">Appt. Starts at: </font>
        		</div>
        		<div class="col-md-3">
        			<input type="text" id="sDateID" class="form-control" placeholder="Select date">
        		</div>
        		<div class="col-md-6">
        		
        			<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
				        <input class="form-control" size="16" id="sHHID" type="text" value="" readonly onchange="getDateTime(sHHID.value, sDateID.value, updateapptTypeID.value);">
				        <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	               	</div>
        		</div>
        	</div>
        	
        	<div class="row" style="margin-top: 15px;">
        		<div class="col-md-3">
        			<font style="font-size: 14px;font-weight: bold;">Appt. Ends at: </font>
        		</div>
        		<div class="col-md-3">
        			<input type="text" id="eDateID" class="form-control" placeholder="Select date">
        		</div>
        		<div class="col-md-6">
        		
        		<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
				             <input class="form-control" size="16" id="eHHID" type="text" value="" readonly>
				              <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	               	</div>
        		</div>
        	</div>
        	
        	<input type="hidden" id="patientIDForClinician">
        	<input type="hidden" id="apptIDForClinician">
	         <input type="hidden" name="" id="updatestartTimeID">
	         
        </div>
        <div class="modal-footer" align="center">
        
        	<center>
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-success" onclick="updateConfirmAppointmentStatus(patDetailsID1.value);">Confirm Appointment</button>
        		<button type="button" class="btn btn-warning" onclick="updateCancelledAppointmentStatus(patDetailsID1.value);">Cancel Appointment</button>
        		<button type="button" class="btn btn-primary" onclick="updateAppointment(patDetailsID1.value, sDateID.value, sHHID.value, eDateID.value, eHHID.value, updateapptTypeID.value);">Update Appointment</button>
        		<button type="button" class="btn btn-success" id="ConsultationBtnID" style="display: none;" onclick="verifyVisitExistsForAppointment(patientIDForClinician.value, apptIDForClinician.value);">Consultation</button>
        	</center>
          
        </div>

      </div>
    </div>
  </div>
  
  
  <div id="eventDetailModal1" class="modal fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel">ERROR MESSAGE</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<center>
        				<font style="color:red; font-size: 14px;">No visit has been added for the patient yet.</font>
        			</center>
        		
        		</div>
        	
        	</div>

        </div>
        <div class="modal-footer" align="center">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>

      </div>
    </div>
  </div>
  
  
  <!-- Ends -->
  
  
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
          <a href="Logout?userID=<%= userID %>"><button type="submit" style="width:25%" class="btn btn-primary">Logout</button></a>
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
                  
	                 
        <%
          	if(form.getUserType().equals("superAdmin")){
        %>
	            
	            <div class="col-md-1" style="margin-top:15px;padding-left:0px;">
	              	<font style=" font-size: 16px;font-weight: bold;">Practice</font>
	              </div>
	              
	             <div class="col-md-2" style="margin-top:10px;padding-left:0px;">
	              	<select name="" class="form-control" onchange="submitChangePracticeForm(this.value);" style="border-radius: 5px;">
						<option value="-1">Select Practice</option>
						<%
							Set<Integer> set = practiceMap.keySet();
							for(Integer practiceID: set){
								if(practiceID == form.getPracticeID()){
						%>
							<option value="<%=practiceID%>" selected="selected"><%=practiceMap.get(practiceID) %></option>
						<%
								}else{
						%>
							<option value="<%=practiceID%>"><%=practiceMap.get(practiceID) %></option>
						<%
								}
							}
						%>
					</select>
					
		 		</div>
		 		
		 		<div class="col-md-1" style="margin-top:15px;padding-left:0px;">
	              	<font style=" font-size: 16px;font-weight: bold;">Clinic</font>
	            </div>
	              
		 		<div class="col-md-2" style="margin-top:10px;padding-left:0px;">
	              	
	              <form action="ChangeClinic" method="POST" id="changeClinicForm" name="changeClinicForm">
	              <input type="hidden" name="changedPracticeID" id="" value = "<%=form.getPracticeID() %>" >
					<select name="changedClinicID" class="form-control" onchange="submitChangeClinicForm();" style="border-radius: 5px;">
						<%
							Set<Integer> set1 = clinicMap.keySet();
							for(Integer clinicID: set1){
								if(clinicID == form.getClinicID()){
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
	                
		<% }else{ %>
		
	             <div class="col-md-1" style="margin-top:15px;padding-left:0px;">
	              	<font style=" font-size: 16px;font-weight: bold;">Clinic</font>
	             </div>
	            
	             <div class="col-md-2" style="margin-top:10px;padding-left:0px;">
	              	<form action="ChangeClinic" method="POST" id="changeClinicForm" name="changeClinicForm">
	              	<input type="hidden" name="changedPracticeID" id="" value = "<%=form.getPracticeID() %>" >
					<select name="changedClinicID" class="form-control" onchange="submitChangeClinicForm();" style="border-radius: 5px;">
						<%
							Set<Integer> set = clinicMap.keySet();
							for(Integer clinicID: set){
								if(clinicID == form.getClinicID()){
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
					</form>

	              </div>
			<% } %>
			
              <ul class="nav navbar-nav navbar-right" style="width: 40%;">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
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
        
         <%
          	 if(form.getUserType().equals("administrator")){
          %>
           
           <%
          	}else{
           %>
		   
		   <div class="ln_solid"></div>
		   
		   			<div class="row">
                      
                     	<%
							//if(form.getSpecailization().contains("Optician")){
						%>
					
						<!-- <div class="col-md-3">
						
							<a href="RenderAddNewPatient"> <button class="btn btn-primary" type="button" >Add New Patient</button></a>
						
						</div>
						
						<div class="col-md-6">
						
							<input type="text" class="form-control" required name="patientName" placeholder="Search Patient By Patient Name" >
						
						</div>
						
						<div class="col-md-3">
						
							<button class="btn btn-success" type="submit" style="width:100%;">Search Patient</button>
						
						</div> -->
						
						<%
							//}else{
						%>
						
						<%
							if(form.getUserType().equals("compounder") || form.getUserType().equals("dietician") || form.getUserType().equals("billDesk")  || form.getUserType().equals("accountant")){
						%> 
						
						 <div class="col-md-2">
						
							<a href="ViewAppointment"> <button type="button" class="btn btn-warning"  style="width:100%;" onclick="showLoadingImg();">All Appointments</button></a>
						
						</div> 
						
						<form action="SearchPatient" method="POST">
						
						<div class="col-md-3">
						
							<input type="text" class="form-control" required name="patientName" placeholder="Search Patient By Name/Mobile No" >
						
						</div>
						
						<div class="col-md-2">
                          <s:select list="#{'Visit Date':'Visit Date','PatientName':'Patient Name','MobileNo':'Mobile No.','RegNo':'Registration No.'}" class="form-control" name="searchCriteria"></s:select>
                        </div>
						
						<div class="col-md-2">
						
							<button class="btn btn-success active" type="submit" style="width:100%;" onclick="showLoadingImg();">Search Patient</button>
						
						</div>
						
						</form>
						<%
							}else if(form.getUserType().equals("clinician")){
						%>
						
						<div class="col-md-2">
						
							<a href="ViewMyAppointment"> <button type="button" class="btn btn-default" style="width:100%;" onclick="showLoadingImg();" >My Appointments</button></a>
						
						</div> 
						
						 <div class="col-md-2">
						
							<a href="ViewAppointment"> <button type="button" class="btn btn-warning"  style="width:100%;" onclick="showLoadingImg();">All Appointments</button></a>
						
						</div> 
						
						<!-- <div class="col-md-2">
						
							<a href="RenderAddNewPatient"> <button class="btn btn-primary" type="button" style="width:100%;">Add New Patient</button></a>
						
						</div> -->
						
						<form action="SearchPatient" method="POST">
						
						<div class="col-md-2">
                          <s:select list="#{'Visit Date':'Visit Date','PatientName':'Patient Name','MobileNo':'Mobile No.','RegNo':'Registration No.'}" class="form-control" name="searchCriteria"></s:select>
                        </div>
                        
                        <div class="col-md-2">
						
							<input type="text" class="form-control" required name="patientName" placeholder="Search Term" >
							
						</div>
						
						<div class="col-md-2">
						
							<button class="btn btn-success active" type="submit" style="width:100%;" onclick="showLoadingImg();">Search Patient</button>
							
						</div>
						
						<div class="col-md-2">
						
							<a href="javascript:checkSearchPatientForm();"> <button class="btn btn-primary" type="button" style="width:100%;">Add New Patient</button></a>
						
						</div>
						</form>
						
						<%
							}else{
						%>
						
					<% if(form.getClinicID()!=0){ %>
						
						<div class="row">
						
							<div class="col-md-2">
							
								<a href="#patientDetailModal1" data-toggle="modal"> <button type="button" class="btn btn-default"  style="width:100%;" onclick="showLoadingImg();">Add Appointments</button></a>
							
							</div> 
							
							<div class="col-md-3">
								<form action="ViewAppointment" method="POST" id="viewApptForm" name="viewApptForm">
									<select name="cliniciaID" class="form-control">
										<option value="0">All</option>
										<%
										
											for(Integer key: clinicianMap.keySet()){
											
										%>
										
											<option value="<%=key%>"><%=clinicianMap.get(key)%></option>
										
										<%
											}
										%>
									</select>
								</form>
							</div> 
							
							<div class="col-md-2">
								<a href="javascript:submitViewApptForm();"> <button type="button" class="btn btn-warning"  style="width:100%;">All Appointments</button></a>
							</div> 
							
							<div class="col-md-2">
								<a href="javascript:checkSearchPatientForm();"> <button class="btn btn-primary" type="button" style="width:100%;">Add New Patient</button></a>
							</div>
						
						</div>
						
						<form action="SearchPatient" method="POST">
						
						<div class="row" style="margin-top: 15px;">
							
							<div class="col-md-2">
							
								<input type="text" class="form-control" required name="patientName" placeholder="Search Patient By Name/Mobile No" >
							
							</div>
							
							<div class="col-md-2">
	                          <s:select list="#{'Visit Date':'Visit Date','PatientName':'Patient Name','MobileNo':'Mobile No.','RegNo':'Registration No.'}" class="form-control" name="searchCriteria"></s:select>
	                        </div>
							
							<div class="col-md-2">
							
								<button class="btn btn-success active" type="submit" style="width:100%;" onclick="showLoadingImg();">Search Patient</button>
							
							</div>
						
						</div>
						
						</form>
						
					<% }%>
						
						<% 
							}
						%>

						
						<%
							//}
						%>
                     </div>
                     
				<div class="row">
					<div class="col-md-12" align="center" style="margin-bottom:20px;">
						<s:if test="hasActionErrors()">
						    <center>
						    <font style="color: red; margin-bottom: 10px;font-size:16px;"><s:actionerror /></font>
						    </center>
						</s:if> <s:else>
						    <center>
						    <font style="color: green; margin-bottom: 10px;font-size:16px;"><s:actionmessage/> </font>
						    </center>
						</s:else>
					</div>
				</div>
				
				<%
					if(patientCheck == "Patient"){
				%>
				
				<div class="ln_solid" style="margin-top:0px;"></div>
				
				<div class="row" style="margin-bottom: 15px;margin-left: 0px;">
					<font style="font-size: 16px; font-weight: bold; text-decoration: underline;">PATIENT LIST</font>
				</div>
				
				<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                      <thead>
                        <tr>
                          <th>First Name</th>
                          <th>Middle Name</th>
                          <th>Last Name</th>
                          <th>Registration No.</th>
                          <th>Age</th>
                          <th>Gender</th>
                          <th style="text-align:center;">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                      
                        <s:iterator value="patientList" var="UserForm">
                        
                        <form action="" id="ViewAptForm" name="ViewAptForm" style="display: none;">
							<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
						</form>
						
                        	<tr>
                        			<td style="width: 15%"><s:property value="firstName" /></td>
						
									<td style="width: 15%"><s:property value="middleName" /></td>
										
									<td style="width: 15%"><s:property value="lastName" /></td>
									
									<td style="width: 20%"><s:property value="registrationNo" /></td>
									
									<td style="width: 10%"><s:property value="age" /></td>
									
									<td style="width: 10%"><s:property value="gender" /></td>
										 
									<td align="center">
									<%
					                  if(form.getUserType().equals("administrator") || form.getUserType().equals("receptionist")){
					                %>
									<s:url id="approveURL"
										action="RenderEditPatient">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="%{approveURL}">
										<img src="images/patient_1.png" style="height:24px;" onmouseover="this.src='images/patient_2.png'"
											onmouseout="this.src='images/patient_1.png'" alt="Edit Patient"
											title="Edit Patient" />
									</s:a>&nbsp;
									<s:url id="approveURL11"
										action="RenderDisablePatient">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
										<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
											onmouseout="this.src='images/delete_icon_1.png'" alt="Remove Patient"
											title="Remove Patient" />
									</s:a>
									<%
					                  }else{
									%>
									<%
										if(form.getUserType().equals("compounder") || form.getUserType().equals("dietician")
												|| form.getUserType().equals("billDesk") || form.getUserType().equals("accountant")){
									%>
									<s:url id="approveURL1"
										action="RenderViewVisit">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="%{approveURL1}">
										<img src="images/visit_view_1.png" style="height:24px;" onmouseover="this.src='images/visit_view_2.png'"
											onmouseout="this.src='images/visit_view_1.png'" alt="Open Existing visits"
											title="Open Existing Visits" />
									</s:a>&nbsp;
									<%
										}else{
									%>
									<s:url id="approveURL"
										action="RenderEditPatient">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="%{approveURL}">
										<img src="images/patient_1.png" style="height:24px;" onmouseover="this.src='images/patient_2.png'"
											onmouseout="this.src='images/patient_1.png'" alt="Edit Patient"
											title="Edit Patient" />
									</s:a>
									<s:a href="javascript:openVisitTypeModal(%{patientID});">
										<img src="images/visit_view_1.png" style="height:24px;" onmouseover="this.src='images/visit_view_2.png'"
											onmouseout="this.src='images/visit_view_1.png'" alt="New visit"
											title="New visit" />
									</s:a>
									
									<s:url id="approveURL1"
										action="RenderViewVisit">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="%{approveURL1}">
										<img src="images/visit_view_1.png" style="height:24px;" onmouseover="this.src='images/visit_view_2.png'"
											onmouseout="this.src='images/visit_view_1.png'" alt="Open Existing visits"
											title="Open Existing Visits" />
									</s:a>
									<s:url id="approveURL11"
										action="RenderDisablePatient">
										<s:param name="patientID" value="%{patientID}" />
									</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
										<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
											onmouseout="this.src='images/delete_icon_1.png'" alt="Remove Patient"
											title="Remove Patient" />
									</s:a>
									<%
										}
					                  }
									%>
									</td>
									
                        	</tr>
                        </s:iterator>
                      </tbody>
                    </table>
						            
		            <%
					} else if(patientCheck == "Appointment"){
		            %>
		            <div class="ln_solid" style="margin-top:0px;"></div>
		            
		            <div class="row" style="margin-bottom: 15px;margin-left: 0px;">
						<font style="font-size: 16px; font-weight: bold; text-decoration: underline;">APPOINTMENT LIST</font>
					</div>
		            
		            <form name="appointmentForm" id="appointmentForm" action="SendAppointmentReport" method="POST">
		            	<input type="hidden" name="check" id="checkID">
		            </form>
		            
		             <ul id="myTab1" class="nav nav-tabs">
				        <li class="active"><a href="#today" data-toggle="tab">Today's Appointments</a></li>
				        <li><a href="#week" data-toggle="tab">Week's Appointments</a></li>
				        <li><a href="#month" data-toggle="tab">Month's Appointments</a></li>
				     </ul>
				     
				     
				  <div id="myTabContent1" class="tab-content">
				  
				  	<!-- Today's appointment tab -->
				    <div class="tab-pane fade in active" id="today">
				    
				    <%
				    	if(todayApptMsg == null || todayApptMsg == ""){
				    %>
				    
				    <div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding: 20px;">
		                <div class="col-xs-12">
		                
		                
		                <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                      <thead>
	                        <tr>
	                          <th>Appt. Date</th>
	                          <th>Appt. Time From</th>
	                          <th>Appt. Time To</th>
	                          <th>Patient ID</th>
	                          <th>Patient Name</th>
	                          <th>Attended By</th>
	                          <th>Status</th>
	                          
	                          <%
				            	if(myApptCheck.equals("mine")){
				            	}else{
				              %>
	                          <th style="text-align: center;"><input type="checkbox" name="reportTodayCheck" id="reportTodayCheckID" style="margin-top:0px;height: 20px; width:20px;"> </th>
	                          <%
				            	}
	                          %>
	                          <!-- <th style="text-align:center;">Action</th> -->
	                        </tr>
	                      </thead>
	                      <tbody>
	                      
	                      <s:iterator value="appointmentList" var="UserForm">
	                      	<tr>
	                      		<td><s:property value="appointmentDate" /></td>
	                      		
	                      		<td><s:property value="aptTimeFrom" /></td>
	                      		
	                      		<td><s:property value="aptTimeTo" /></td>
	                      		
	                      		<td><s:property value="registrationNo" /></td>
	                      		
	                      		<td><s:property value="patientName" /></td>
	                      		
	                      		<td><s:property value="clinicianName" /></td>
	                      		
	                      		<td><s:property value="aptStatus" /></td>
	                      		
	                      		 <%
					            	if(myApptCheck.equals("mine")){
					            	}else{
					              %>
	                      		
	                      		<td align="center">
									<input type="checkbox" id="checkTodayID<s:property value="aptID" />" name="reportTodayCheck" onclick="reportTodayVal('checkTodayID<s:property value="aptID" />');" value="<s:property value="aptID" />-<s:property value="patientID" />" style="margin-top:0px;height: 20px; width:20px;">
								</td>
								
								<%
					            	}
								%>
	                      	</tr>
	                      </s:iterator>
	                      
	                      </tbody>
	                    </table>
		                </div>
		                
		               		<%
				            	if(myApptCheck.equals("mine")){
				            		
				            %>
				            
				            	<div class="row" align="right" style="padding-right: 15px;">
				            	 	<a href="DownloadAppointmentReport?check=Day">
		                             <button class="btn btn-warning" type="button">Download Appointment Report</button></a>
				            	 </div>
				            
				            <%
				            		
				            	}else{
				            %>
			            	 <div class="row" align="right" style="padding-right: 15px;">
			            	 	<form action="DownloadTodayAppointmentReport" id="apptTodayFormID" method="POST">
				            	 	<div class="col-md-4" id="reportTodayDivID"></div>
				            		<!-- <button type="submit" value="" name="" class="btn btn-success" id="excelTodayID" disabled="disabled">Download Visit Reports</button>
			            	 	<a href="javascript:submitApptForm('Day');">
	                             <button class="btn btn-primary" type="button">Send Report</button></a> -->
	                             <a href="javascript:submitApptReportFormToday();">
		                             <button class="btn btn-warning" type="button" id="dwldApptReportBtnID" disabled="disabled" onclick="showLoadingImg();">Download Appointment Report</button></a>
			            	 	</form>
			            	 </div>
			            <%
			            	}
			            %>
		                
		            </div>
				    
				    <%
				    	}else{
				    %>
				    
				    <div class="row">
						<div class="col-md-12" align="center" style="margin-bottom: 5em;">
		
							    <center>
							   		<font id="errorFontID"><%=todayApptMsg %></font>
							    </center>
							
						</div>
					</div>
		            
		            <%
		            	}
		            %>
				      
				    </div>
				      
				      
				      <!-- Week's appointment tab -->
				    <div class="tab-pane fade" id="week">
				    
				        <%
				    	 if(weekApptMsg == null || weekApptMsg == ""){
					    %>
					    
					    <div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding: 20px;">
			                <div class="col-xs-12">
			                
			                <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
		                      <thead>
		                        <tr>
		                          <th>Appt. Date</th>
		                          <th>Appt. Time From</th>
		                          <th>Appt. Time To</th>
		                          <th>Patient ID</th>
		                          <th>Patient Name</th>
		                          <th>Attended By</th>
		                          <th>Status</th>
		                          <%
					            	if(myApptCheck.equals("mine")){
					            	}else{
					              %>
		                          <th style="text-align: center;"><input type="checkbox" name="reportWeekCheck" id="reportWeekCheckID" style="margin-top:0px;height: 20px; width:20px;"> </th>
		                          <!-- <th style="text-align:center;">Action</th> -->
		                          <%
					            	}
		                          %>
		                        </tr>
		                      </thead>
		                      <tbody>
		                      
		                      <s:iterator value="appointmentWeekList" var="UserForm">
		                      	<tr>
		                      		<td><s:property value="appointmentDate" /></td>
		                      		
		                      		<td><s:property value="aptTimeFrom" /></td>
		                      		
		                      		<td><s:property value="aptTimeTo" /></td>
		                      		
		                      		<td><s:property value="registrationNo" /></td>
		                      		
		                      		<td><s:property value="patientName" /></td>
		                      		
		                      		<td><s:property value="clinicianName" /></td>
		                      		
		                      		<td><s:property value="aptStatus" /></td>
		                      		<%
					            	if(myApptCheck.equals("mine")){
					            	}else{
					              %>
		                      		<td align="center">
										<input type="checkbox" id="checkWeekID<s:property value="aptID" />" name="reportWeekCheck" onclick="reportWeekVal('checkWeekID<s:property value="aptID" />');" value="<s:property value="aptID" />-<s:property value="patientID" />" style="margin-top:0px;height: 20px; width:20px;">
									</td>
									<%
					            	}
									%>
		                      	</tr>
	                      	 </s:iterator>
		                        
		                      </tbody>
		                    </table>
			                </div>
			                
			                <%
				            	if(myApptCheck.equals("mine")){
				            		
				            %>
				            
				            	<div class="row" align="right" style="padding-right: 15px;">
				            	 	<a href="DownloadAppointmentReport?check=Week">
		                             <button class="btn btn-warning" type="button" onclick="showLoadingImg();">Download Appointment Report</button></a>
				            	 </div>
				            
				            <%
				            		
				            	}else{
				            %>
				            	 <div class="row" align="right" style="padding-right: 15px;">
				            	 	<form action="DownloadWeekAppointmentReport" id="apptWeekFormID" method="POST">
				            	 	<div class="col-md-4" id="reportWeekDivID"></div>
				            		<!-- <button type="submit" value="" name="" class="btn btn-success" id="excelWeekID" disabled="disabled">Download Visit Reports</button>
				            	 	<a href="javascript:submitApptForm('Week');">
		                             <button class="btn btn-primary" type="button">Send Report</button></a> -->
		                             <a href="javascript:submitApptReportFormWeek();">
		                             <button class="btn btn-warning" type="button" id="dwldApptReportWeekBtnID" disabled="disabled" onclick="showLoadingImg();">Download Appointment Report</button></a>
				            	 	</form>
				            	 </div>
				            <%
				            	}
				            %>
			            
			            </div>
					    
					    <%
					    	}else{
					    %>
					    
					    <div class="row">
							<div class="col-md-12" align="center" style="margin-bottom: 5em;">
			
								    <center>
								   		<font id="errorFontID"><%=weekApptMsg %></font>
								    </center>
								
							</div>
						</div>
			            
			            <%
			            	}
			            %>
				      
				    </div>
				    
				    <!-- Month's appointment tab -->
				     <div class="tab-pane fade" id="month">
				      
				      	<%
				    	 if(monthApptMsg == null || monthApptMsg == ""){
					    %>
					    
					    <div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding: 20px;">
			                <div class="col-xs-12">
			                
			                <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
		                      <thead>
		                        <tr>
		                          <th>Appt. Date</th>
		                          <th>Appt. Time From</th>
		                          <th>Appt. Time To</th>
		                          <th>Patient ID</th>
		                          <th>Patient Name</th>
		                          <th>Attended By</th>
		                          <th>Status</th>
		                          <%
					            	if(myApptCheck.equals("mine")){
					            	}else{
					              %>
		                          <th style="text-align: center;"><input type="checkbox" name="reportCheck" id="reportCheckID" style="margin-top:0px;height: 20px; width:20px;"> </th>
		                          <!-- <th style="text-align:center;">Action</th> -->
		                          <%
					            	}
		                          %>
		                        </tr>
		                      </thead>
		                      <tbody>
		                      
		                      <s:iterator value="appointmentMonthList" var="UserForm">
		                      	<tr>
		                      		<td><s:property value="appointmentDate" /></td>
		                      		
		                      		<td><s:property value="aptTimeFrom" /></td>
		                      		
		                      		<td><s:property value="aptTimeTo" /></td>
		                      		
		                      		<td><s:property value="registrationNo" /></td>
		                      		
		                      		<td><s:property value="patientName" /></td>
		                      		
		                      		<td><s:property value="clinicianName" /></td>
		                      		
		                      		<td><s:property value="aptStatus" /></td>
		                      		
		                      		<%
					            	if(myApptCheck.equals("mine")){
					            	}else{
					              %>
		                      		<td align="center">
		                      			<input type="checkbox" id="checkID<s:property value="aptID" />" name="reportCheck" onclick="reportVal('checkID<s:property value="aptID" />');" value="<s:property value="aptID" />-<s:property value="patientID" />" style="margin-top:0px;height: 20px; width:20px;">
									</td>
									<%
					            	}
									%>
		                      	</tr>
	                      	 </s:iterator>
		                      
		                      </tbody>
		                    </table>
			                </div>
			                
			                 <%
				            	if(myApptCheck.equals("mine")){
				            		
				            %>
				            
				            	<div class="row" align="right" style="padding-right: 15px;">
				            	 	<a href="DownloadAppointmentReport?check=Month">
		                             <button class="btn btn-warning" type="button" onclick="showLoadingImg();">Download Appointment Report</button></a>
				            	 </div>
				            
				            <%
				            		
				            	}else{
				            %>
				            	 <div class="row" align="right" style="padding-right: 15px;">
				            	 	<form action="DownloadMonthAppointmentReport" id="apptMonthFormID" method="POST">
				            	 	<div class="col-md-4" id="reportDivID"></div>
				            		<!-- <button type="submit" value="" name="" class="btn btn-success" id="excelID" disabled="disabled">Download Visit Reports</button>
				            	 	<a href="javascript:submitApptForm('Month');">
		                             <button class="btn btn-primary" type="button">Send Report</button></a> -->
		                             <a href="javascript:submitApptReportFormMonth();">
		                             <button class="btn btn-warning" type="button" id="dwldApptReportMonthBtnID" disabled="disabled" onclick="showLoadingImg();">Download Appointment Report</button></a>
				            	 	</form>
				            	 </div>
				            <%
				            	}
				            %>
				            
			            </div>
					    
					    <%
					    	}else{
					    %>
					    
					    <div class="row">
							<div class="col-md-12" align="center" style="margin-bottom: 5em;">
			
								    <center>
								   		<font id="errorFontID"><%=monthApptMsg %></font>
								    </center>
								
							</div>
						</div>
			            
			            <%
			            	}
			            %>
				      
				    </div>
				    
				  </div>
		            
		            
		            <%
					}
		            %>
           
           <%
          	}
           %>
           
           <!-- For jquery full calendar -->
           <div class="ln_solid" style="margin-top:0px;"></div>
           			
           			<div class="row">
           			<div class="col-md-3" style="margin-top: 5px;">
           				<h4 style="font-weight: bold;">Appointment Calendar</h4>
           			</div>
           			
           			<!-- <div class="col-md-2 col-sm-3 col-xs-4" style="padding-top:5px;" id="slot1DivID">
           				<div style="float:left;height: 30px; width: 30px; background-color: #ADDFFF;border: 1px solid #ADDFFF;margin-top: -3px;"></div>&nbsp; - 
           				<font style="font-size: 15px; font-weight: bold;" id="visitType1FontID"></font>
           			</div>
           			
           			<div class="col-md-2 col-sm-3 col-xs-4" style="padding-top:5px;" id="slot2DivID">
           				<div style="float:left;height: 30px; width: 30px; background-color: #E0B0FF;border: 1px solid #E0B0FF;margin-top: -3px;"></div>&nbsp; - 
           				<font style="font-size: 15px; font-weight: bold" id="visitType2FontID"></font>
           			</div> -->
           		<div class="col-md-9" style="margin-top: 10px;">	
           			<table border='0' style='width: 100%;'>
	           			<tr style="height: 25px;">
	           				<td style="background-color: blue; width: 15%;" align="center"><font style="font-size: 13px; font-weight: bold;" id="" color="white";>Booked</font></td>
	           				<td style="background-color: pink; width: 15%;" align="center"><font style="font-size: 13px; font-weight: bold;" id="" color="black";>Confirmed</font></td>
	           				<td style="background-color: green; width: 15%;" align="center"><font style="font-size: 13px; font-weight: bold;" id="" color="white";>In Progress</font></td>
	           				<td style="background-color: #b8b894; width: 15%;" align="center"><font style="font-size: 13px; font-weight: bold;" id="" color="black";>Done</font></td>
	           				<td style="background-color: #cc0000; width: 15%;" align="center"><font style="font-size: 13px; font-weight: bold;" id="" color="white";>No Show / Cancelled</font></td>
	           			</tr>
           			</table>
           		</div>
           			<!-- <div class="col-md-2" style="margin-top: 10px;">
           			
           				<div style="float:left;height: 20px; width: 20px; background-color: blue;border: 1px solid blue;margin-top: 1px;"></div>&nbsp; - 
           					<font style="font-size: 13px; font-weight: bold;" id="">Booked</font>
	           			</div>
	           			
	           			<div class="col-md-2" style="margin-top: 10px;">
	           				<div style="float:left;height: 20px; width: 20px; background-color: pink;border: 1px solid pink;margin-top: 1px;"></div>&nbsp; - 
	           				<font style="font-size: 13px; font-weight: bold;" id="">Confirmed</font>
	           			</div>
	           			
	           			<div class="col-md-2" style="margin-top: 10px;">
	           				<div style="float:left;height: 20px; width: 20px; background-color: green;border: 1px solid green;margin-top: 1px;"></div>&nbsp; - 
           					<font style="font-size: 13px; font-weight: bold;" id="">In Progress</font>
	           			</div>
	           			
	           			<div class="col-md-1" style="margin-top: 10px;padding-left: 0px;padding-right: 0px;">
	           				<div style="float:left;height: 20px; width: 20px; background-color: #b8b894;border: 1px solid #b8b894;margin-top: 1px;"></div>&nbsp; - 
           					<font style="font-size: 13px; font-weight: bold;" id="">Done</font>
	           			</div>
	           			
	           			<div class="col-md-2" style="margin-top: 10px;">
	           				<div style="float:left;height: 20px; width: 20px; background-color: #cc0000;border: 1px solid #cc0000;margin-top: 1px;"></div>&nbsp; - 
           					<font style="font-size: 13px; font-weight: bold;" id="">No Show / Cancelled</font>
	           			</div> -->
			</div>
				     
				    <!--  <div class="row" style="margin-top:15px;">
				     
				     	
           			
				     </div>
				     
				      <div class="row" style="margin-top:15px;">
				      	
				      </div> -->
				     
				     <div class="row" style="margin-top: 10px;">
				     	<div id="weekCalendar" style="padding: 10px;"></div>
				     </div>
				     
           
           <br/>
           
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
   
    document.addEventListener('DOMContentLoaded', function() {
    	
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
    	$('#sDateID').attr("readonly","readonly");
        $('#sDateID').daterangepicker({
          singleDatePicker: true,
          calender_style: "picker_3"
        }, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#sDateID').on('hide.daterangepicker', function(ev, picker) {
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
    		
    		if(new Date(today).getTime() <= calendarDate.getTime()){
    			return true;
    		}else{
    			alert("Selected date must be greater than current date.");
    			$(this).val("");
    		}
    	});
        
        
        $('#eDateID').attr("readonly","readonly");
        
        $('#eDateID').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
        $('#eDateID').on('hide.daterangepicker', function(ev, picker) {
			var selectedDate = $(this).val();
			
			var startDate = $('#sDateID').val();
			
			var startDateArr = startDate.split("-");
				
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
    		
    		var startDateVal = new Date(startDateArr[2]+"-"+startDateArr[1]+"-"+startDateArr[0]);
    		
    		console.log("Dates: "+calendarDate+"--"+startDateVal);
    		
    		//alert("Dates: "+new Date(today).getTime()+"--"+calendarDate.getTime());
    		
    		if(startDateVal.getTime() <= calendarDate.getTime()){
    			return true;
    		}else{
    			alert("Selected date must be greater than start date.");
    			$(this).val("");
    		}
    	});
        
        $('#sDateID1').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
          
          $('#eDateID1').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_3"
            }, function(start, end, label) {
              console.log(start.toISOString(), end.toISOString(), label);
            });
            
          $('#apptDateID1').attr("readonly","readonly");
          
            $('#apptDateID1').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_3"
              }, function(start, end, label) {
                console.log(start.toISOString(), end.toISOString(), label);
              });
            
            $('#apptDateID1').on('hide.daterangepicker', function(ev, picker) {
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
        		
        		if(new Date(today).getTime() <= calendarDate.getTime()){
        			return true;
        		}else{
        			alert("Selected date must be greater than current date.");
        			$(this).val("");
        		}
        	});
            
            $('#apptDateID2').attr("readonly","readonly");
            $('#apptDateID2').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_3"
              }, function(start, end, label) {
                console.log(start.toISOString(), end.toISOString(), label);
              });
            
            $('#apptDateID2').on('hide.daterangepicker', function(ev, picker) {
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
        		
        		if(new Date(today).getTime() <= calendarDate.getTime()){
        			return true;
        		}else{
        			alert("Selected date must be greater than current date.");
        			$(this).val("");
        		}
        	});
            
            $('#apptDateID').attr("readonly","readonly");
            $('#apptDateID').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_3"
              }, function(start, end, label) {
                console.log(start.toISOString(), end.toISOString(), label);
              });
            
            
            $('#apptDateID').on('hide.daterangepicker', function(ev, picker) {
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
	        		
	        		if(new Date(today).getTime() <= calendarDate.getTime()){
	        			return true;
	        		}else{
	        			alert("Selected date must be greater than current date.");
	        			$(this).val("");
	        		}
            	});
            
            $('.appt_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.appt_start1').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.appt_start2').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.appt_start3').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.appt_start4').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.appt_start5').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
        
      });
   
    </script>
    
    
  </body>
</html>