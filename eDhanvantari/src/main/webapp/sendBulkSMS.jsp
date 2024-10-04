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
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>Broadcast Messages | E-Dhanvantari</title>

<!-- Select2 Theme Style -->                                                                                                                           
   <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.12/css/select2.min.css" rel="stylesheet"/>
   
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

		.select2-selection{
			width: 240%;
		}
		
		.select2-dropdown{
			width: 240px !important;
		}
		
		.select2-container {
   			 width: 114px;
		}

		.select2-dropdown select2-dropdown--above {
			width: none !important;
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
    
		//int registerCheck = registrationDAOinf.checkOpenLeaveRegister(form.getPracticeID());
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
    
    
    <script type="text/javascript">
		function abc(userType){
			selectSpecialisation(userType);
		}
    </script>
    
    <!-- Specialization selection based on user role -->
    
    <script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function selectSpecialisation(userType) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
				 	if(userType =="administrator"){
						
						var array_element = null;
	
						array_element = "<select name='specialsation' class='form-control'"+
						"> <option value='-1'>Select Specialisation</option> </select>";
						document.getElementById("specialsation").innerHTML = array_element;
						document.getElementById("specSpanID").innerHTML = "" ;
						
					}else if(userType == "clinician"){
	
						var array_element = "<select name='specialsation' required='required' class='form-control'"+
						"><option value='-1'>Select Specialisation</option>";
						
						for ( var i = 0; i < array.Release.length; i++) {
	
							array_element += "<option value='"+array.Release[i].Ophthalmologist+"'>"+array.Release[i].Ophthalmologist+"</option>";
							array_element += "<option value='"+array.Release[i].Orthopaedic+"'>"+array.Release[i].Orthopaedic+"</option>";
							array_element += "<option value='"+array.Release[i].Gynaecologist+"'>"+array.Release[i].Gynaecologist+"</option>";
							array_element += "<option value='"+array.Release[i].GeneralPhysician+"'>"+array.Release[i].GeneralPhysician+"</option>";
						}
	
						array_element += "  </select>";
						document.getElementById("specialsation").innerHTML = array_element;
						document.getElementById("specSpanID").innerHTML = "*" ;
						
					}else if(userType == "staff"){
	
						var array_element = "<select name='specialsation' required='required' class='form-control' "+
						"><option value='-1'>Select Specialisation</option> ";
						
						for ( var i = 0; i < array.Release.length; i++) {
	
							array_element += "<option value='"+array.Release[i].Receptionist+"'>"+array.Release[i].Receptionist+"</option>";
							array_element += "<option value='"+array.Release[i].Optician+"'>"+array.Release[i].Optician+"</option>";
						}
	
						array_element += " </select>";
						document.getElementById("specialsation").innerHTML = array_element;
						document.getElementById("specSpanID").innerHTML = "*" ;
						
					}				
					
				}
			};
			xmlhttp.open("GET", "ChangeSpecialisation?userType="
					+ userType, true);
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
    
    
    <!-- Retrieving clinic list based on practiceID -->
    
    <script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getPracticeClinic(practiceID) {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var array_element = "";
					
					if(practiceID == "-1"){
						
						array_element = "<select name='clinicID' required='required' class='form-control'"+
						"> <option value='-1'>Select Clinic</option></select>";
						
						document.getElementById("clinicListID").innerHTML = array_element;
						
					}else{
						
						array_element = "";
						
					}

					array_element = "<select name='clinicID' required='required' class='form-control'"+
					"> <option value='-1'>Select Clinic</option>";

					for ( var i = 0; i < array.Release.length; i++) {
						
						var clinicVal = array.Release[i].clinicVal;
						
						var clinicArray = clinicVal.split("=");

						array_element += "<option value='"+clinicArray[0]+"'>"+clinicArray[1]+"</option>";
						
					}
					
					array_element += "  </select>";
					document.getElementById("clinicListID").innerHTML = array_element;
				}
			};
			xmlhttp.open("GET", "GetPracticeClinic?practiceID="
					+ practiceID , true);
			xmlhttp.send();
			
		}
			
	</script>
    
    <!-- Ends -->
    
    
    <script type="text/javascript">
    
    	function submitForm(){
    		
    		var diagnosis = $("#diagnosisID").val();
    		var practice = $("#pracID").val();
    		var clinic = $("#clinicID").val();
    		
    		if(diagnosis == "-1"){
    			alert("No diagnosis selected. Please select diagnosis.");
    			
    			return false;
    		}else if(practice == "-1"){
				alert("No practice/clinic selected. Please select either practice or clinic.");
    			
    			return false;
    		}else if(practice == "Clinic" && clinic == "-1"){
				alert("No clinic selected. Please select clinic.");
    			
    			return false;
    		}else if($("#patDivID").is(":visible")){
    			
    			var patient = $("#patMobileID").val();
    			
    			if(patient == "-1"){
    				
    				alert("No patient selected. Please select patient.");
        			
        			return false;
    				
    			}else{
    				$('html, body').animate({
    			        scrollTop: $('body').offset().top
    			    }, 1000);
    				
    				$('body').css('background', '#FCFAF5');
    				$(".container").css("opacity","0.2");
    				$("#loader").show();
    				return true;
    			}
    			
    		}else{
    			$('html, body').animate({
    		        scrollTop: $('body').offset().top
    		    }, 1000);
    			
    			$('body').css('background', '#FCFAF5');
    			$(".container").css("opacity","0.2");
    			$("#loader").show();
    			return true;
    		}
    	}
    	
    	function showClinic(val){
    		
    		if(val == "Clinic"){
    			$("#clincDivID").show(1000);
    			
    			var diagnosis = $("#diagnosisID").val();
    			
    			if(diagnosis == "-1"){
    				return true;
    			}else{
    				retrievePatientListForDiagnosis1(diagnosis, val, 0);
    			}
    			
    		}else{
    			$("#clincDivID").hide(1000);
    			
    			if(val == "-1"){
    				return true;
    			}else{
    				var diagnosis = $("#diagnosisID").val();
        			
        			if(diagnosis == "-1"){
        				return true;
        			}else{
        				retrievePatientListForDiagnosis1(diagnosis, val, 0);
        			}	
    			}
    		}
    		
    	}
    	
		function submitForm1(){
			
    		var practice = $("#pracID1").val();
    		var clinic = $("#clinicID1").val();
    		
    		if(practice == "-1"){
				alert("No practice/clinic selected. Please select either practice or clinic.");
    			
    			return false;
    		}else if(practice == "Clinic" && clinic == "-1"){
				alert("No clinic selected. Please select clinic.");
    			
    			return false;
    		}else if($("#patDivID1").is(":visible")){
    			
    			var patient = $("#patMobileID1").val();
    			
    			if(patient.includes("-1")){
    				
    				alert("Either no patient is selected or 'Select Patient' option is selected.");
        			
        			return false;
    				
    			}else{
    				$('html, body').animate({
    			        scrollTop: $('body').offset().top
    			    }, 1000);
    				
    				$('body').css('background', '#FCFAF5');
    				$(".container").css("opacity","0.2");
    				$("#loader").show();
    				return true;
    			}
    			
    		}else{
    			$('html, body').animate({
    		        scrollTop: $('body').offset().top
    		    }, 1000);
    			
    			$('body').css('background', '#FCFAF5');
    			$(".container").css("opacity","0.2");
    			$("#loader").show();
    			return true;
    		}
    	}
    	
		function showClinic1(val){
    		
    		if(val == "Clinic"){
    			$("#clincDivID1").show(1000);
    		}else{
    			$("#clincDivID1").hide(1000);
    			retrievePatientListForCredit(val, 0);
    		}
    		
    	}
		
		function checkDiagnosis(diagnosis, val){
			if(val == "-1"){
				$("#diagnosisID").val(null).trigger("change");
				$("#patMobileID").val(null).trigger("change");
				$("#patDivID").hide(1000);
    			return true;
			}else if(diagnosis == "-1"){
				$("#patMobileID").val(null).trigger("change");
				$("#patDivID").hide(1000);
    			return true;
			}
			else{
				retrievePatientListForDiagnosis1(diagnosis, "Clinic", val);
			}
		}
		
		
		function showClinic2(val){
    		
			if(val == "Clinic"){
				$("#patMobileID2").val(null).trigger("change");
    			$("#clincDivID2").show(1000);
    			$("#clinicID2").attr("required","required");
    			$("#patDivID2").hide(1000);
    		}else if(val == ""){
    			 $("#clinicID2").val(null).trigger("change");
    			$("#clincDivID2").hide(1000);
    			$("#patDivID2").hide(1000);
    			$("#clinicID2").removeAttr("required");
    			$("#patMobileID2").val(null).trigger("change");	
    		}else{
    			$("#patMobileID2").val(null).trigger("change");
    			 $("#clinicID2").val(null).trigger("change");
    			$("#clincDivID2").hide(1000);
    			$("#clinicID2").removeAttr("required");
    			
    			retrievePatientActiveList1(val,0);
    		}
			
    	}
    
    </script>
    
    
    <!-- Patient list based on diagnosis for diagnosis SMS -->
    
    <script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrievePatientListForDiagnosis(diagnosis, pracCheck){
			if(diagnosis == "-1"){
				$("#patMobileID").val(null).trigger("change");
				$("#patDivID").hide(1000);
			}else if(pracCheck == "-1"){
				alert("No practice/Clinic selected. Please select either practice or clinic.");	
			}else if(pracCheck == "Clinic"){
				var clinic = $("#clinicID").val();
				
				if(clinic == "-1"){
					$("#patMobileID").val(null).trigger("change");
					alert("No clinic selected. Please select clinic.");	
				}else{
					$("#patMobileID").val(null).trigger("change");
					retrievePatientListForDiagnosis1(diagnosis, pracCheck, clinic);
				}
			}
			/* else{
				retrievePatientListForDiagnosis1(diagnosis, pracCheck, 0);
			} */
		}
	
		function retrievePatientListForDiagnosis1(diagnosis, pracCheck, clinic) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<option value='All'>All Patients</option>";
						
						var check = 0;
	
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].mobile+"'>"+array.Release[i].name+"</option>";
						}
	
						//array_element += " </select>";
						
						if(check == 0){
							
							$("#SendDiagnosisBulkSMSID").attr('disabled',true);
							$("#patMobileID").val(null).trigger("change");
							$("#patDivID").hide(1000);
							
						}else{
							
							document.getElementById("patMobileID").innerHTML = array_element;	
						
							$("#SendDiagnosisBulkSMSID").attr('disabled',false)
							$("#patDivID").show(1000);
							
						}
					
				}
			};
			xmlhttp.open("GET", "RetrievePatientListForDiagnosis?diagnosis="
					+ diagnosis+"&check="+pracCheck+"&clinicID="+clinic, true);
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Patient list based on diagnosis for patient credit SMS -->
    
    <script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrievePatientListForCredit1(check, clinicID){
			
			if(clinicID == "-1"){
				alert("No clinic is selected. Please select clinic.");
				$("#patDivID1").hide(1000);
				$("#patMobileID1").val(null).trigger("change");
			}else{
				$("#patMobileID1").val(null).trigger("change");
				retrievePatientListForCredit(check, clinicID);
			}
			
		}
	
		function retrievePatientListForCredit(check, clinicID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<option value='All'>All Patients</option>";
						
						var check = 0;
	
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].patientID+"'>"+array.Release[i].patientName+"</option>";
						}
	
					//	array_element += " </select>";
						
						if(check == 0){
							
							alert("No patient found");
							$("#sendCreditID").attr('disabled',true);
							$("#patDivID1").hide(1000);
							$("#patMobileID1").val(null).trigger("change");
						}else{
							
							document.getElementById("patMobileID1").innerHTML = array_element;	
							
							$("#sendCreditID").attr('disabled',false);
							$("#patDivID1").show(1000);
							
						}
					
				}
			};
			xmlhttp.open("GET", "RetrievePatientListForCredit?clinicID="
					+ clinicID+"&check="+check, true);
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    
    
     <!-- Retrieve active patient list to send customised SMS -->
    
    <script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrievePatientActiveList(check, clinicID){
			
			if(clinicID == "-1"){
				alert("No clinic is selected. Please select clinic.");
				$("#patDivID2").hide(1000);
				$("#patMobileID2").val(null).trigger("change");
			}else{
				$("#patMobileID2").val(null).trigger("change");
				retrievePatientActiveList1(check, clinicID);
			}
			
		}
	
		function retrievePatientActiveList1(check, clinicID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<option value='All'>All Patients</option>";
						
						var check = 0;
	
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].patientID+"'>"+array.Release[i].name+"</option>";
						}
	
					//	array_element += " </select>";
						
						if(check == 0){
							
							alert("No patient found");
							$("#SendBulkSMSID").attr('disabled',true);
							$("#patDivID2").hide(1000);
							$("#patMobileID2").val(null).trigger("change");
						}else{
							
							document.getElementById("patMobileID2").innerHTML = array_element;	
							$("#SendBulkSMSID").attr('disabled',false);
							$("#patDivID2").show(1000);
							
						}
					
				}
			};
			xmlhttp.open("GET", "RetrievePatientActiveList?clinicID="
					+ clinicID+"&check="+check, true);
			xmlhttp.send();
		}
		
		
		function fetchMessage(smsText){
		    	
		    if(smsText =="-1"){
				$("#msgDivID2").hide();
			    	
			   	$("#commentID").val("");
		    }else{
			   	$("#msgDivID2").show();
			    	
			   	$("#commentID").val(smsText);
		    }
		}
	</script>
    
    <!-- Ends -->
       
  </head>

  <body class="nav-md">
  
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
                        <h3>BROADCAST MESSAGES</h3>
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
		              
		              <ul id="myTab1" class="nav nav-tabs">
		              	<li class="active"><a href="#customSMS" data-toggle="tab">Customized SMS</a></li>
				       <!--  <li><a href="#practice" data-toggle="tab">Diagnosis SMS</a></li>
				        <li><a href="#patCredit" data-toggle="tab">Patient's Credit SMS</a></li> -->
				     </ul>
				     
				     <div id="myTabContent1" class="tab-content">
				     
				     	<!-- Customise SMS tab -->
					     <div class="tab-pane fade in active" id="customSMS">
			                
			                 <form action="SendCustomizedBulkSMS" class="form-horizontal form-label-left" onsubmit="return showLoadingImg();" method="POST" style="margin-top: 15px;">
			                 
				                  <div class="item form-group">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Practice/Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="#{'Practice':'Practice','Clinic':'Clinic' }" name="check" class="form-control" id="pracID2" headerKey="-1" value="Practice" headerValue="Select Practice/Clinic" onchange="showClinic2(this.value);"></s:select>
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group" id="clincDivID2" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="clinicList" name="clinicID" class="form-control" headerKey="-1" id="clinicID2" headerValue="Select Clinic" onchange="retrievePatientActiveList('Clinic', this.value);"></s:select>
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group" id="patDivID2" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Patient<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12" id="patListID2">
			                       		<select name="patientIDString" id="patMobileID2" class="js-example-basic-multiple form-control" multiple="multiple" required="required"></select>
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group">
			                        <label for="Activity Status" class="control-label col-md-3 col-sm-3 col-xs-12">Select Template<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                            <s:select list="SMSTemplateTitleList" name="SMSTemplateTitle" class="form-control" headerKey="-1" id="templateTitleTextID"  headerValue="Select Template" required="required" onchange="fetchMessage(this.value);"></s:select>
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group" id="msgDivID2" style="display: none;"> 
			                        <label for="Activity Status" class="control-label col-md-3 col-sm-3 col-xs-12">Message<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <textarea  name="comment" rows="5" cols="3" class="form-control" id="commentID" required="required" required="required"></textarea>
			                        </div>
			                      </div>
			                      
			                      <div class="ln_solid"></div>
			                      <div class="form-group">
			                        <div class="col-md-12 col-xs-12" align="center">
			                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
			                          <button id="SendBulkSMSID" type="submit" class="btn btn-success" >Send SMS</button>
			                        </div>
			                      </div>
		                    </form>
					     </div>
					     <!-- Ends -->
				     
				     
					     <!-- Diagnosis SMS tab -->
					     <div class="tab-pane fade in" id="practice">
			                
			                 <form action="SendDiagnosisBulkSMS" class="form-horizontal form-label-left" method="POST" style="margin-top: 15px;">
			                 
				                  <%-- <div class="item form-group">
			                        <label for="Clinic" class="control-label col-md-3">Practice/Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="#{'Practice':'Practice','Clinic':'Clinic' }" name="check" class="form-control" id="pracID" headerKey="-1" value="Practice" headerValue="Select Practice/Clinic" onchange="showClinic(this.value);"></s:select>
			                        </div>
			                      </div> --%>
			                      <input type="hidden" name="check" value="Clinic">
			                      <div class="item form-group" id="clincDivID">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="clinicList" name="clinicID" class="form-control" headerKey="-1" id="clinicID" headerValue="Select Clinic" onchange="checkDiagnosis(diagnosisID.value, this.value);"></s:select>
			                        </div>
			                      </div>
				                 
				                  <div class="item form-group">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Diagnosis<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="diagnoseList" name="diagnosis" class="form-control" headerKey="-1" headerValue="Select Diagnosis" id="diagnosisID" onchange="retrievePatientListForDiagnosis(this.value, 'Clinic');"></s:select>
			                        </div>
			                      </div>
			                    <%--   
			                      <div class="item form-group" id="sendDivID" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3">Send message to<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                       		<s:radio list="#{'All Patients':'All Patients','Selected Patients':'Selected Patients' }" class="radioValClass" id="radioValID2" name="pageCheck" onchange="showPatientList1(this.value);"></s:radio>
			                        </div>
			                      </div> --%>
			                      
			                      <div class="item form-group" id="patDivID" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Patient<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12" id="patListID">
			                          <select name="mobile" id="patMobileID" class="js-example-basic-multiple form-control" multiple="multiple" required="required">
										
										</select>
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group">
			                        <label for="Activity Status" class="control-label col-md-3 col-sm-3 col-xs-12">Message<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <textarea  name="comment" class="form-control" required="required"></textarea>
			                        </div>
			                      </div>
			                      
			                      <div class="ln_solid"></div>
			                      <div class="form-group">
			                        <div class="col-md-12 col-xs-12" align="center">
			                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
			                          <button type="submit" class="btn btn-success " id = "SendDiagnosisBulkSMSID" onclick="return submitForm();">Send SMS</button>
			                        </div>
			                      </div>
			                      
		                    </form>
			                
					     
					     </div>
					     <!-- Ends -->
					     
					     <!-- Patient's Credit SMS tab -->
					     <div class="tab-pane fade in" id="patCredit">
			                
			                 <form action="SendPatientCreditBulkSMS" class="form-horizontal form-label-left" method="POST" style="margin-top: 15px;">
			                 
				                  <%-- <div class="item form-group">
			                        <label for="Clinic" class="control-label col-md-3">Practice/Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="#{'Practice':'Practice','Clinic':'Clinic' }" name="check" class="form-control" id="pracID1" headerKey="-1" value="Practice" headerValue="Select Practice/Clinic" onchange="showClinic1(this.value);"></s:select>
			                        </div>
			                      </div> --%>
			                       
			                       <input type="hidden" name="check" value="Clinic">
			                      <div class="item form-group" id="clincDivID1">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Clinic<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                          <s:select list="clinicList" name="clinicID" class="form-control" headerKey="-1" id="clinicID1" headerValue="Select Clinic" onchange="retrievePatientListForCredit1('Clinic',this.value);"></s:select>
			                        </div>
			                      </div>
			                      
			                       <%-- <div class="item form-group" id="sendDivID1" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3">Send message to<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12">
			                       		<s:radio list="#{'All Patients':'All Patients','Selected Patients':'Selected Patients' }" class="radioValClass" id="radioValID1" name="pageCheck" onchange="showPatientList2(this.value);"></s:radio>
			                        </div>
			                      </div> --%>
			                      <div class="item form-group" id="patDivID1" style="display: none;">
			                        <label for="Clinic" class="control-label col-md-3 col-sm-3">Patient<span class="required">*</span></label>
			                        <div class="col-md-3 col-sm-4 col-xs-12" id="patListID1">
			                          <select name="patientIDString" id="patMobileID1" class="js-example-basic-multiple form-control" multiple="multiple" required="required">
										
										</select>
			                        </div>
			                      </div>
			                      
			                      <div class="ln_solid"></div>
			                      <div class="form-group">
			                        <div class="col-md-12 col-xs-12" align="center">
			                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
			                          <button id="sendCreditID"  type="submit" class="btn btn-success " onclick="return submitForm1();">Send SMS</button>
			                        </div>
			                      </div>
			                      
		                    </form>
			                
					     
					     </div>
					     <!-- Ends -->
				     
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
          <div class="pull-right">
            
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

     <!-- jQuery -->
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js" ></script>
  <%--    <script src="vendors/jquery/dist/jquery.min.js"></script> --%> 
    
     <!-- Select2 Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.12/js/select2.min.js"></script>
    
    <script>
		$(document).ready(function() {
		    $(".js-example-basic-multiple").select2();
		});
	
	</script> 

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
    
  </body>
</html>