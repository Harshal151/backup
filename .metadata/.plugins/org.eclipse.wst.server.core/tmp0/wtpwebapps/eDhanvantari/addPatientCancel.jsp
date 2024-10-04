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

    <title>Add Patient | E-Dhanvantari</title>

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
			
			$$('a#profPicID').remove();	//To disable profile pic at the right top corner
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
		
        document.location="Welcome.jsp";
      }
    </script>
    
    <!-- Setting width for gender radio values -->
	
	<style type="text/css">
		#genderMale{
			width: 5%;
		}
		
		#genderFemale{
			width: 5%;
		}
		
		#genderUnknown{
			width: 5%;
		}
	</style>
	
	 <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
	</style>
	
	<!-- Ends -->
    
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
			
			if($("#mobileID").val().length < 10){
				alert("Please enter valid mobile no")
				return false;
			}
			
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
	
	
	<%
		String submitClinic = (String) request.getAttribute("submitClinic");
	%>
    
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
                        <h3>ADD PATIENT</h3>
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
		              
					<%
		            	/*
		            	* Checkig the add jsp page name for logged in user, if its rasayuOPDNew then
		            	* display add patient fields as per rasayu form else display common add patient form.	
		            	*/
		            	String[] formNameArray = form.getOPDJSPName().split("&");
		            	if(formNameArray[0].trim().equals("rasayuOPDNew.jsp")){
		            %>
		            
		            
		            <form class="form-horizontal form-label-left" action="AddRasayuPatient" method="POST" onsubmit="return showLoadingImg();" style="margin-top:20px;" >
                      
                      <s:iterator value="newPatientList" var="form">
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span class="required">*</span>
                        </label>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input  class="form-control" name="firstName" value="<s:property value='firstName'/>" placeholder="First Name" required="required" type="text">
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input  class="form-control" name="middleName" value="<s:property value='middleName'/>" placeholder="Middle Name" type="text">
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input class="form-control" name="lastName" value="<s:property value='lastName'/>" placeholder="Last Name" required="required" type="text">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Gender">Gender<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<s:radio list="#{'Male':'Male','Female':'Female','Unknown':'Unknown'}" name="gender" required="required"  ></s:radio>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Date of Birth<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
							
							<div class="col-md-4 col-md-4 col-md-4">
								<s:select list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31'}" name="dobDate" class="form-control" required="required" id="dateId" headerKey="-1" headerValue="Select Date"></s:select>
							</div>     
							
							<div class="col-md-4 col-md-4 col-md-4">
							
								<s:select list="#{'Jan':'Jan','Feb':'Feb','Mar':'Mar','Apr':'Apr','May':'May','Jun':'Jun','Jul':'Jul','Aug':'Aug','Sept':'Sept','Oct':'Oct','Nov':'Nov','Dec':'Dec'}" name="dobMonth" class="form-control" required="required" id="monthId" headerKey="-1" headerValue="Select Month"></s:select>
							
							</div> 
							
							<div class="col-md-4 col-md-4 col-md-4">
							
								<s:select list="#{'1963':'1963','1964':'1964','1965':'1965','1966':'1966','1967':'1967','1968':'1968','1969':'1969','1970':'1970','1971':'1971','1972':'1972','1973':'1973','1974':'1974','1975':'1975','1976':'1976','1977':'1977','1978':'1978','1979':'1979','1980':'1980','1981':'1981','1982':'1982','1983':'1983','1984':'1984','1985':'1985','1986':'1986','1987':'1987','1988':'1988','1989':'1989','1990':'1990','1991':'1991','1992':'1992','1993':'1993','1994':'1994','1995':'1995','1996':'1996','1997':'1997','1998':'1998','1999':'1999','2000':'2000','2001':'2001','2002':'2002','2003':'2003','2004':'2004','2005':'2005','2006':'2006','2007':'2007','2008':'2008','2009':'2009','2010':'2010'}" name="dobYear" class="form-control" id="yearId" required="required" onchange="calculateAge(dateId.value, monthId.value, yearId.value);" headerKey="-1" headerValue="Select Year"></s:select>
							
							</div>
							<input type="hidden" id="dateOfBirthID" value="<s:property value='dateOfBirth'/>" name="dateOfBirth">                     

                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Age<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" name="age" placeholder="Age" value="<s:property value='age'/>" readonly="readonly" required="required" id="ageID" class="form-control">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Mobile<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="number" name="mobile" placeholder="Mobile" id="mobileID" value="<s:property value='mobile'/>" required="required" class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
                        </div>
                      </div>
                      
                      <div class="item form-group">
	                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Email">Email
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="email" name="emailID" value="<s:property value='emailID'/>" placeholder="Email Address" class="form-control">
	                        </div>
	                    </div>
                      
        			  
        			  <!-- Contact information Details -->
        			  <div class="row" >
        			  	<div class="col-md-12" style="margin-bottom:15px;">
        			  		<a style="text-decoration:underline;font-size:14px; font-weight:bold; text-align:right;color: #23527c;" class="col-md-3 col-sm-3" data-toggle="collapse" href="#contactInfo" aria-expanded="false" aria-controls="collapseExample">
  								CONTACT INFORMATION:
							</a>
        			  	</div>	
        			  	
        			  	<div class="collapse" id="contactInfo" style="margin-top:15px;">
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Address">Address
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <s:textarea name="address"  class="form-control" placeholder="Address"></s:textarea>
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="City">City
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="city" value="<s:property value='city'/>" placeholder="City"  class="form-control" onfocus="this.value=''" onblur="this.value='Pune'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="State">State
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="state"  value="<s:property value='state'/>" placeholder="State" class="form-control" onfocus="this.value=''" onblur="this.value='Maharashtra'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Country">Country
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="country"  value="<s:property value='country'/>" placeholder="Country" readonly="readonly" class="form-control">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Alternative No.">Alternative No.
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="number" name="phone" value="<s:property value='phone'/>" class="form-control" placeholder="Alternative No."  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Occupation">Occupation
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="occupation" value="<s:property value='occupation'/>" placeholder="Occupation" class="form-control">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="EC">EC
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="EC" value="<s:property value='EC'/>" placeholder="EC" class="form-control">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Son/Daughter of">Son/Daughter of
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="sonDaughterOf" value="<s:property value='sonDaughterOf'/>" placeholder="Son/Daughter of" class="form-control">
		                        </div>
		                    </div>
        			  	
        			  	</div>
        			  </div>        			  
        			  <!-- Ends -->
        			  
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-12" align="center">
                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
                          <button id="send" style="width:15%;" type="submit" class="btn btn-success " >Add Patient</button>
                        </div>
                      </div>
                      </s:iterator>
                      
                    </form>
		            
		            
		            <%
		            	}else{
					%>
		              

                    <form class="form-horizontal form-label-left" novalidate action="AddPatient" method="POST" onsubmit="return showLoadingImg();" style="margin-top:20px;" >
                      
                      <s:iterator value="newPatientList" var="form">
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span class="required">*</span>
                        </label>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input  class="form-control" name="firstName" value="<s:property value='firstName'/>" placeholder="First Name" required="required" type="text">
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input  class="form-control" name="middleName" value="<s:property value='middleName'/>" placeholder="Middle Name" type="text">
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input class="form-control" name="lastName" value="<s:property value='lastName'/>" placeholder="Last Name" required="required" type="text">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Gender">Gender<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <s:radio list="#{'Male':'Male','Female':'Female','Unknown':'Unknown'}" name="gender" required="required"  ></s:radio>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Age<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" name="age" placeholder="Age" value="<s:property value='Age'/>" required="required" class="form-control">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Mobile<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" name="mobile" placeholder="Mobile" value="<s:property value='Mobile'/>" required="required" class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="User Type" class="control-label col-md-3">Blood Group<span class="required">*</span></label>
                        <div class="col-md-4 col-sm-4 col-xs-12">
                          <s:select list="#{'A':'A','B':'B','O':'O','AB':'AB'}" required="required" name="bloodGroup" class="form-control" headerKey="-1" headerValue="Blood Group"></s:select>
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <s:select list="#{'+ve':'+ve','-ve':'-ve'}" name="rhFactor" required="required" class="form-control" headerKey="-1" headerValue="Rh"></s:select>
                        </div>
                      </div>
                      
                      <!-- identification Details -->
                      <div class="row" >
        			  	<div class="col-md-12" style="margin-bottom:15px;">
        			  		<a style="text-decoration:underline;font-size:14px; font-weight:bold; text-align:right;color: #23527c;" class="col-md-3 col-sm-3" data-toggle="collapse" href="#identification" aria-expanded="false" aria-controls="collapseExample">
  								IDENTIFICATION:
							</a>
        			  	</div>
        			  	
        			  	<div class="collapse" id="identification" style="margin-top:15px;">
        			  	
        			  		<div class="item form-group">
		                        <label for="User Type" class="control-label col-md-3">Aadhaar No.</label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="aadhaarNo" value="<s:property value='aadhaarNo'/>" placeholder="Aadhaar No." class="form-control">
		                        </div>
		                    </div>
        			  	
        			  	</div>
        			  </div>
        			  <!-- Ends -->
        			  
        			  <!-- Contact information Details -->
        			  <div class="row" >
        			  	<div class="col-md-12" style="margin-bottom:15px;">
        			  		<a style="text-decoration:underline;font-size:14px; font-weight:bold; text-align:right;color: #23527c;" class="col-md-3 col-sm-3" data-toggle="collapse" href="#contactInfo" aria-expanded="false" aria-controls="collapseExample">
  								CONTACT INFORMATION:
							</a>
        			  	</div>	
        			  	
        			  	<div class="collapse" id="contactInfo" style="margin-top:15px;">
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Address">Address
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <s:textarea name="address"  class="form-control" placeholder="Address"></s:textarea>
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="City">City
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="city" value="<s:property value='city'/>" placeholder="City" class="form-control" onfocus="this.value=''" onblur="this.value='Pune'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="State">State
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="state" value="<s:property value='state'/>" placeholder="State" class="form-control" onfocus="this.value=''" onblur="this.value='Maharashtra'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Country">Country
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="country" value="<s:property value='country'/>" placeholder="Country" readonly="readonly" class="form-control">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Phone">Phone
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="number" name="phone" value="<s:property value='phone'/>" class="form-control" placeholder="Phone"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
		                        </div> 
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Email">Email
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="email" name="emailID" value="<s:property value='emailID'/>" placeholder="Email Address" class="form-control">
		                        </div>
		                    </div>
        			  	
        			  	</div>
        			  </div>        			  
        			  <!-- Ends -->
        			  
        			  <!-- Emergency contact Details -->
        			  <div class="row" >
        			  	<div class="col-md-12" style="margin-bottom:15px;">
        			  		<a style="text-decoration:underline;font-size:14px; font-weight:bold; text-align:right;color: #23527c;" class="col-md-3 col-sm-3" data-toggle="collapse" href="#emgContactInfo" aria-expanded="false" aria-controls="collapseExample">
  								EMERGENCY CONTACT:
							</a>
        			  	</div>	
        			  	
        			  	<div class="collapse" id="emgContactInfo" style="margin-top:15px;">
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name
		                        </label>
		                        <div class="col-md-2 col-sm-2 col-xs-12">
		                          <input  class="form-control" name="emFname" value="<s:property value='emFname'/>" placeholder="First Name" type="text">
		                        </div>
		                        <div class="col-md-2 col-sm-2 col-xs-12">
		                          <input  class="form-control" name="emMname" value="<s:property value='emMname'/>" placeholder="Middle Name" type="text">
		                        </div>
		                        <div class="col-md-2 col-sm-2 col-xs-12">
		                          <input class="form-control" name="emLname" value="<s:property value='emLname'/>" placeholder="Last Name" type="text">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label for="User Type" class="control-label col-md-3">Relation</label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="emRelation" value="<s:property value='emRelation'/>" placeholder="Relation" class="form-control">
		                        </div>
		                    </div>
                      
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Address">Address
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <s:textarea name="emAdd"  class="form-control" placeholder="emAdd"></s:textarea>
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="City">City
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="emCity" value="<s:property value='emCity'/>" placeholder="City" class="form-control" onfocus="this.value=''" onblur="this.value='Pune'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="State">State
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="emState"  value="<s:property value='emState'/>" placeholder="State" class="form-control" onfocus="this.value=''" onblur="this.value='Maharashtra'">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Country">Country
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="emCountry" value="<s:property value='emCountry'/>" placeholder="Country" readonly="readonly" class="form-control">
		                        </div>
		                    </div>
		
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Phone">Phone
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="number" name="emPhone" value="<s:property value='emPhone'/>" class="form-control" placeholder="Phone"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label for="User Type" class="control-label col-md-3">Mobile</label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="emMobile" value="<s:property value='emMobile'/>" placeholder="Mobile" class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Email">Email
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="email" name="emEmailID" value="<s:property value='emEmailID'/>" placeholder="Email Address" class="form-control">
		                        </div>
		                    </div>
        			  	
        			  	</div>
        			  </div>        			  
        			  <!-- Ends -->

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-12" align="center">
                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
                          <button id="send" style="width:15%;" type="submit" class="btn btn-success ">Add Patient</button>
                        </div>
                      </div>
                      
                    </s:iterator>
                    </form>
                    
                    
                    <%
		            	}
                    %>
                    
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
    <!-- FastClick -->
    <script src="vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="vendors/nprogress/nprogress.js"></script>
    <!-- validator -->
    <script src="vendors/validator/validator.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
      });
    </script>
    
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