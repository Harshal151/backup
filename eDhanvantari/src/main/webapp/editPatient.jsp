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
<%@ taglib uri="/struts-tags" prefix="s"%>
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

<title>Edit Patient | E-Dhanvantari</title>

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
		
        document.location="editPatientList.jsp";
      }
    </script>

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

<!-- remove up down arrow from input type number -->
<style type="text/css">
input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}
</style>

<!-- Ends -->


<script type="text/javascript">
	
		function getValue(ID, value, prevVal){
			
			if(value == ""){
				$("#"+ID+"").val(prevVal);
			}else{
				$("#"+ID+"").val(value);	
			}
			
		}
	
	</script>



<!-- Calculating age by birth date -->

<script type="text/javascript">
	
		function calculateAge(userDate, userMonth, userYear){
				
				var date = new Date();   // JS Engine will parse the string automagically
	
				var date21=date.getDate();
				var month = date.getMonth()+1;
				var year1=date.getFullYear();
				
				var userDOB = userDate + "-" + userMonth + "-" + userYear;
				
				$('#dateOfBirthID').val(userDOB);
				
				var birthYear;
				
				if(userMonth>month||userMonth==month){
				    if(userMonth==month){
				        if(userDate>date21){
				            birthYear = year1-userYear-1;
				        }
				        else{
				            birthYear = year1-userYear;
				        }
				    }else{
				        birthYear = year1-userYear-1;
				    }
				}else{
				    birthYear = year1-userYear;
				}

				$('#ageID').val(birthYear);
				$('#ageID').attr('readonly', true);

		}
	
	</script>

<!-- Ends -->


<style type="text/css">
ul.actionMessage {
	list-style: none;
	font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
}

ul.errorMessage {
	list-style: none;
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
	if (session.getAttribute("USER") == "" || session.getAttribute("USER") == null) {
		form.setFullName("");
		String loginMessage = "Plase login using valid credentials";
		request.setAttribute("loginMessage", loginMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}
	int patientID = form.getPatientID();
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

<body class="nav-md">
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

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
								class="user-profile dropdown-toggle" id ="profPicID" data-toggle="dropdown"
								aria-expanded="false"> <%
							 	if (daoInf.retrieveProfilePic(form.getUserID()) == null
							 			|| daoInf.retrieveProfilePic(form.getUserID()).isEmpty()) {
							 %> <img src="images/user.png" alt="Profile Pic"><%=form.getFullName()%>

									<%
										} else {
							
											S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveProfilePic(form.getUserID()))).getObjectContent();
					 						
					 						IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" +daoInf.retrieveProfilePic(form.getUserID()))));  
					 				  %>
					                	
					                 <img src="<%= "images/" +daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
					                 
							 <%
							 	}
							 %> <span class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="RenderEditProfile" onclick="showLoadingImg();"> Profile</a></li>
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
										<h3>EDIT PATIENT</h3>
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

									<form class="form-horizontal form-label-left" novalidate onsubmit="return showLoadingImg();"
										action="EditPatient" method="POST" style="margin-top: 20px;">
								
										<s:iterator value="patientList" var="form">

											<input type="hidden" name="patientID"
												value="<s:property value='patientID'/>">
											<input type="hidden" name="practiceID"
												value="<s:property value='practiceID'/>">

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="name" style="padding-top:3%">Name <span class="required">*</span>
												</label>
												<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
													<input class="form-control" name="firstName"
														style="text-transform: capitalize;"
														value="<s:property value='firstName'/>" id="firstNameID"
														placeholder="First Name" required="required" type="text"
														onblur="validate(this.value, 'firstNameID');">
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
													<input class="form-control" name="middleName"
														style="text-transform: capitalize;"
														value="<s:property value='middleName'/>" id="middleNameID"
														placeholder="Middle Name" type="text"
														onblur="validate(this.value, 'middleNameID');">
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
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
													<s:radio
														list="#{'Male':'Male','Female':'Female','Child':'Child','Unknown':'Unknown'}"
														id="genderID" name="gender" required="required" style="margin-left:5px; margin-right:5px;"></s:radio>
												</div>
											</div>
											
											<div class="item form-group">
												<label for="User Type" class="control-label col-md-3 col-sm-4">Date Of Birth<span class="required">*</span></label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="DateOfBirth" placeholder="Date Of Birth" id = "single_cal3" value="<s:property value='dob'/>" class="form-control" required="required">
												</div>
											</div>
											
											<div class="item form-group">
												<label for="User Type" class="control-label col-md-3 col-sm-4">Age</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="number" name="age" placeholder="Age" id="ageID" value="<s:property value='age'/>" class="form-control" required="required" readonly="readonly">
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type" class="control-label col-md-3 col-sm-4">Mobile</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="number" name="mobile" placeholder="Mobile"
														id="mobileID" value="<s:property value='Mobile'/>"
														class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
												</div>
											</div>
											
											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-4 col-xs-12"
													for="Email">Email </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="email" name="emailID"
														value="<s:property value='emailID'/>"
														placeholder="Email Address" class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type" class="control-label col-md-3 col-xs-12 col-sm-4" style="padding-top:2%">Blood Group</label>
												<div class="col-md-4 col-sm-4 col-xs-12" style="padding-top:2%">
													<s:select list="#{'A':'A','B':'B','O':'O','AB':'AB'}" id="bloodGroupID" name="bloodGroup" class="form-control" headerKey=""
														headerValue="Blood Group"></s:select>
												</div>
												<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
													<s:select list="#{'+ve':'+ve','-ve':'-ve'}" name="rhFactor" id="rhFactorID" class="form-control"
														headerKey="" headerValue="Rh"></s:select>
												</div>
											</div>

											<div class="item form-group">
												<label for="User Type" class="control-label col-md-3 col-sm-4">Medical
													Registration No.
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="" readonly="readonly"
														value="<s:property value='registrationNo'/>"
														placeholder="Medical Registration No." required="required"
														class="form-control"> <input type="hidden"
														name="registrationNo" readonly="readonly"
														value="<s:property value='medicalRegNo'/>"
														placeholder="Medical Registration No." required="required"
														class="form-control">
												</div>
											</div>

											
											<!-- identification Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12" style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#identification" aria-expanded="false"
														aria-controls="collapseExample"> IDENTIFICATION: </a>
												</div>

												<div class="collapse" id="identification"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label for="User Type" class="control-label col-md-3 col-xs-12 col-sm-4">Aadhaar No.</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="aadhaarNo"
																value="<s:property value='aadhaarNo'/>"
																placeholder="Aadhaar No." class="form-control">
														</div>
													</div>

												</div>
											</div>
											<!-- Ends -->

											<!-- Contact information Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12" style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#contactInfo" aria-expanded="false"
														aria-controls="collapseExample"> CONTACT INFORMATION:
													</a>
												</div>

												<div class="collapse" id="contactInfo"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Address">Address </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:textarea name="address" class="form-control"
																placeholder="Address"></s:textarea>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="City">City </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="city"
																value="<s:property value='city'/>" placeholder="City"
																id="cityID" class="form-control" onfocus="this.value=''"
																onblur="getValue('cityID',cityID.value,'<s:property value='city'/>');">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="State">State </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="state"
																value="<s:property value='state'/>" placeholder="State"
																id="stateID" class="form-control"
																onfocus="this.value=''"
																onblur="getValue('stateID',stateID.value,'<s:property value='state'/>');">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Country">Country </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:select
																list="#{'Afghanistan':'Afghanistan','Albania':'Albania','Algeria':'Algeria','American Samoa':'American Samoa','Andorra':'Andorra','Angola':'Angola','Anguilla':'Anguilla','Antartica':'Antarctica','Antigua and Barbuda':'Antigua and Barbuda','Argentina':'Argentina','Armenia':'Armenia','Aruba':'Aruba','Australia':'Australia','Austria':'Austria','Azerbaijan':'Azerbaijan','Bahamas':'Bahamas','Bahrain':'Bahrain','Bangladesh':'Bangladesh','Barbados':'Barbados','Belarus':'Belarus','Belgium':'Belgium','Belize':'Belize','Benin':'Benin','Bermuda':'Bermuda','Bhutan':'Bhutan','Bolivia':'Bolivia','Bosnia and Herzegowina':'Bosnia and Herzegowina','Botswana':'Botswana','Bouvet Island':'Bouvet Island','Brazil':'Brazil','British Indian Ocean Territory':'British Indian Ocean Territory','Brunei Darussalam':'Brunei Darussalam','Bulgaria':'Bulgaria','Burkina Faso':'Burkina Faso','Burundi':'Burundi','Cambodia':'Cambodia','Cameroon':'Cameroon','Canada':'Canada','Cape Verde':'Cape Verde','Cayman Islands':'Cayman Islands','Central African Republic':'Central African Republic','Chad':'Chad','Chile':'Chile','China':'China','Christmas Island':'Christmas Island','Cocos Islands':'Cocos Islands','Colombia':'Colombia','Comoros':'Comoros','Congo':'Congo','Cook Islands':'Cook Islands','Costa Rica':'Costa Rica','Cota DIvoire':'Cota DIvoire','Croatia':'Croatia','Cuba':'Cuba','Cyprus':'Cyprus','Czech Republic':'Czech Republic','Denmark':'Denmark','Djibouti':'Djibouti','Dominica':'Dominica','Dominican Republic':'Dominican Republic','East Timor':'East Timor','Ecuador':'Ecuador','Egypt':'Egypt','El Salvador':'El Salvador','Equatorial Guinea':'Equatorial Guinea','Eritrea':'Eritrea','Estonia':'Estonia','Ethiopia':'Ethiopia','Falkland Islands':'Falkland Islands','Faroe Islands':'Faroe Islands','Fiji':'Fiji','Finland':'Finland','France':'France','France Metropolitan':'France, Metropolitan','French Guiana':'French Guiana','French Polynesia':'French Polynesia','French Southern Territories':'French Southern Territories','Gabon':'Gabon','Gambia':'Gambia','Georgia':'Georgia','Germany':'Germany','Ghana':'Ghana','Gibraltar':'Gibraltar','Greece':'Greece','Greenland':'Greenland','Grenada':'Grenada','Guadeloupe':'Guadeloupe','Guam':'Guam','Guatemala':'Guatemala','Guinea':'Guinea','Guinea-Bissau':'Guinea-Bissau','Guyana':'Guyana','Haiti':'Haiti','Heard and McDonald Islands':'Heard and McDonald Islands','Holy See':'Holy See','Honduras':'Honduras','Hong Kong':'Hong Kong','Hungary':'Hungary','Iceland':'Iceland','India':'India','Indonesia':'Indonesia','Iran':'Iran','Iraq':'Iraq','Ireland':'Ireland','Israel':'Israel','Italy':'Italy','Jamaica':'Jamaica','Japan':'Japan','Jordan':'Jordan','Kazakhstan':'Kazakhstan','Kenya':'Kenya','Kiribati':'Kiribati','Democratic Peoples Republic of Korea':'Democratic Peoples Republic of Korea','Korea':'Korea','Kuwait':'Kuwait','Kyrgyzstan':'Kyrgyzstan','Lao':'Lao','Latvia':'Latvia','Lebanon':'Lebanon','Lesotho':'Lesotho','Liberia':'Liberia','Libyan Arab Jamahiriya':'Libyan Arab Jamahiriya','Liechtenstein':'Liechtenstein','Lithuania':'Lithuania','Luxembourg':'Luxembourg','Macau':'Macau','Macedonia':'Macedonia','Madagascar':'Madagascar','Malawi':'Malawi','Malaysia':'Malaysia','Maldives':'Maldives','Mali':'Mali','Malta':'Malta','Marshall Islands':'Marshall Islands','Martinique':'Martinique','Mauritania':'Mauritania','Mauritius':'Mauritius','Mayotte':'Mayotte','Mexico':'Mexico','Micronesia':'Micronesia','Moldova':'Moldova','Monaco':'Monaco','Mongolia':'Mongolia','Montserrat':'Montserrat','Morocco':'Morocco','Mozambique':'Mozambique','Myanmar':'Myanmar','Namibia':'Namibia','Nauru':'Nauru','Nepal':'Nepal','Netherlands':'Netherlands','Netherlands Antilles':'Netherlands Antilles','New Caledonia':'New Caledonia','New Zealand':'New Zealand','Nicaragua':'Nicaragua','Niger':'Niger','Nigeria':'Nigeria','Niue':'Niue','Norfolk Island':'Norfolk Island','Northern Mariana Islands':'Northern Mariana Islands','Norway':'Norway','Oman':'Oman','Pakistan':'Pakistan','Palau':'Palau','Panama':'Panama','Papua New Guinea':'Papua New Guinea','Paraguay':'Paraguay','Peru':'Peru','Philippines':'Philippines','Pitcairn':'Pitcairn','Poland':'Poland','Portugal':'Portugal','Puerto Rico':'Puerto Rico','Qatar':'Qatar','Reunion':'Reunion','Romania':'Romania','Russia':'Russia','Rwanda':'Rwanda','Saint Kitts and Nevis':'Saint Kitts and Nevis' ,'Saint LUCIA':'Saint LUCIA','Saint Vincent':'Saint Vincent','Samoa':'Samoa','San Marino':'San Marino','Sao Tome and Principe':'Sao Tome and Principe' ,'Saudi Arabia':'Saudi Arabia','Senegal':'Senegal','Seychelles':'Seychelles','Sierra':'Sierra','Singapore':'Singapore','Slovakia':'Slovakia','Slovenia':'Slovenia','Solomon Islands':'Solomon Islands','Somalia':'Somalia','South Africa':'South Africa','South Georgia':'South Georgia','Span':'Spain','Sri Lanka':'Sri Lanka','St. Helena':'St. Helena','St. Pierre and Miguelon':'St. Pierre and Miguelon','Sudan':'Sudan','Suriname':'Suriname','Svalbard':'Svalbard','Swaziland':'Swaziland','Sweden':'Sweden','Switzerland':'Switzerland','Syria':'Syria','Taiwan':'Taiwan','Tajikistan':'Tajikistan','Tanzania':'Tanzania','Thailand':'Thailand','Togo':'Togo','Tokelau':'Tokelau','Tonga':'Tonga','Trinidad and Tobago':'Trinidad and Tobago','Tunisia':'Tunisia','Turkey':'Turkey','Turkmenistan':'Turkmenistan','Turks and Caicos':'Turks and Caicos','Tuvalu':'Tuvalu','Uganda':'Uganda','Ukraine':'Ukraine','United Arab Emirates':'United Arab Emirates','United Kingdom':'United Kingdom','United States':'United States','United States Minor Outlying Islands':'United States Minor Outlying Islands','Uruguay':'Uruguay','Uzbekistan':'Uzbekistan','Vanuatu':'Vanuatu','Venezuela':'Venezuela','Vietnam':'Vietnam','Virgin Islands (British)':'Virgin Islands (British)','Virgin Islands (U.S)':'Virgin Islands (U.S.)','Wallis and Futana Islands':'Wallis and Futuna Islands','Western Sahara':'Western Sahara','Yemen':'Yemen','Yugoslavia':'Yugoslavia','Zambia':'Zambia','Zimbabwe':'Zimbabwe'}"
																class="form-control" name="country" headerKey="-1"
																headerValue="Select Country"></s:select>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Phone">Phone </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="number" name="phone"
																value="<s:property value='phone'/>" class="form-control"
																placeholder="Phone"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
														</div>
													</div>
												</div>
											</div>
											<!-- Ends -->

											<!-- Emergency contact Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12" style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#emgContactInfo" aria-expanded="false"
														aria-controls="collapseExample"> EMERGENCY CONTACT: </a>
												</div>

												<div class="collapse" id="emgContactInfo"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="name" style="padding-top:3%">Name </label>
														<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
															<input class="form-control" name="emFname" id="emFnameID"
																value="<s:property value='emFname'/>"
																placeholder="First Name" type="text"
																onblur="validate(this.value, 'emFnameID');">
														</div>
														<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
															<input class="form-control" name="emMname" id="emMnameID"
																value="<s:property value='emMname'/>"
																placeholder="Middle Name" type="text"
																onblur="validate(this.value, 'emMnameID');">
														</div>
														<div class="col-md-2 col-sm-2 col-xs-12" style="padding-top:2%">
															<input class="form-control" name="emLname" id="emLnameID"
																value="<s:property value='emLname'/>"
																placeholder="Last Name" type="text"
																onblur="validate(this.value, 'emLnameID');">
														</div>
													</div>

													<div class="item form-group">
														<label for="User Type" class="control-label col-md-3 col-sm-4">Relation</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="emRelation"
																value="<s:property value='emRelation'/>"
																placeholder="Relation" class="form-control">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Address">Address </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:textarea name="emAdd" class="form-control"
																placeholder="Address"></s:textarea>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="City">City </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="emCity"
																value="<s:property value='emCity'/>" placeholder="City"
																class="form-control" id="emCityID"
																onfocus="this.value=''"
																onblur="getValue('emCityID',emCityID.value,'<s:property value='emCity'/>');">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="State">State </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="emState"
																value="<s:property value='emState'/>"
																placeholder="State" class="form-control" id="emStateID"
																onfocus="this.value=''"
																onblur="getValue('emStateID',emStateID.value,'<s:property value='emState'/>');">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Country">Country </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<%-- <input type="text" name="emCountry"  value="<s:property value='emCountry'/>" placeholder="Country" readonly="readonly" class="form-control"> --%>
															<s:select
																list="#{'Afghanistan':'Afghanistan','Albania':'Albania','Algeria':'Algeria','American Samoa':'American Samoa','Andorra':'Andorra','Angola':'Angola','Anguilla':'Anguilla','Antartica':'Antarctica','Antigua and Barbuda':'Antigua and Barbuda','Argentina':'Argentina','Armenia':'Armenia','Aruba':'Aruba','Australia':'Australia','Austria':'Austria','Azerbaijan':'Azerbaijan','Bahamas':'Bahamas','Bahrain':'Bahrain','Bangladesh':'Bangladesh','Barbados':'Barbados','Belarus':'Belarus','Belgium':'Belgium','Belize':'Belize','Benin':'Benin','Bermuda':'Bermuda','Bhutan':'Bhutan','Bolivia':'Bolivia','Bosnia and Herzegowina':'Bosnia and Herzegowina','Botswana':'Botswana','Bouvet Island':'Bouvet Island','Brazil':'Brazil','British Indian Ocean Territory':'British Indian Ocean Territory','Brunei Darussalam':'Brunei Darussalam','Bulgaria':'Bulgaria','Burkina Faso':'Burkina Faso','Burundi':'Burundi','Cambodia':'Cambodia','Cameroon':'Cameroon','Canada':'Canada','Cape Verde':'Cape Verde','Cayman Islands':'Cayman Islands','Central African Republic':'Central African Republic','Chad':'Chad','Chile':'Chile','China':'China','Christmas Island':'Christmas Island','Cocos Islands':'Cocos Islands','Colombia':'Colombia','Comoros':'Comoros','Congo':'Congo','Cook Islands':'Cook Islands','Costa Rica':'Costa Rica','Cota DIvoire':'Cota DIvoire','Croatia':'Croatia','Cuba':'Cuba','Cyprus':'Cyprus','Czech Republic':'Czech Republic','Denmark':'Denmark','Djibouti':'Djibouti','Dominica':'Dominica','Dominican Republic':'Dominican Republic','East Timor':'East Timor','Ecuador':'Ecuador','Egypt':'Egypt','El Salvador':'El Salvador','Equatorial Guinea':'Equatorial Guinea','Eritrea':'Eritrea','Estonia':'Estonia','Ethiopia':'Ethiopia','Falkland Islands':'Falkland Islands','Faroe Islands':'Faroe Islands','Fiji':'Fiji','Finland':'Finland','France':'France','France Metropolitan':'France, Metropolitan','French Guiana':'French Guiana','French Polynesia':'French Polynesia','French Southern Territories':'French Southern Territories','Gabon':'Gabon','Gambia':'Gambia','Georgia':'Georgia','Germany':'Germany','Ghana':'Ghana','Gibraltar':'Gibraltar','Greece':'Greece','Greenland':'Greenland','Grenada':'Grenada','Guadeloupe':'Guadeloupe','Guam':'Guam','Guatemala':'Guatemala','Guinea':'Guinea','Guinea-Bissau':'Guinea-Bissau','Guyana':'Guyana','Haiti':'Haiti','Heard and McDonald Islands':'Heard and McDonald Islands','Holy See':'Holy See','Honduras':'Honduras','Hong Kong':'Hong Kong','Hungary':'Hungary','Iceland':'Iceland','India':'India','Indonesia':'Indonesia','Iran':'Iran','Iraq':'Iraq','Ireland':'Ireland','Israel':'Israel','Italy':'Italy','Jamaica':'Jamaica','Japan':'Japan','Jordan':'Jordan','Kazakhstan':'Kazakhstan','Kenya':'Kenya','Kiribati':'Kiribati','Democratic Peoples Republic of Korea':'Democratic Peoples Republic of Korea','Korea':'Korea','Kuwait':'Kuwait','Kyrgyzstan':'Kyrgyzstan','Lao':'Lao','Latvia':'Latvia','Lebanon':'Lebanon','Lesotho':'Lesotho','Liberia':'Liberia','Libyan Arab Jamahiriya':'Libyan Arab Jamahiriya','Liechtenstein':'Liechtenstein','Lithuania':'Lithuania','Luxembourg':'Luxembourg','Macau':'Macau','Macedonia':'Macedonia','Madagascar':'Madagascar','Malawi':'Malawi','Malaysia':'Malaysia','Maldives':'Maldives','Mali':'Mali','Malta':'Malta','Marshall Islands':'Marshall Islands','Martinique':'Martinique','Mauritania':'Mauritania','Mauritius':'Mauritius','Mayotte':'Mayotte','Mexico':'Mexico','Micronesia':'Micronesia','Moldova':'Moldova','Monaco':'Monaco','Mongolia':'Mongolia','Montserrat':'Montserrat','Morocco':'Morocco','Mozambique':'Mozambique','Myanmar':'Myanmar','Namibia':'Namibia','Nauru':'Nauru','Nepal':'Nepal','Netherlands':'Netherlands','Netherlands Antilles':'Netherlands Antilles','New Caledonia':'New Caledonia','New Zealand':'New Zealand','Nicaragua':'Nicaragua','Niger':'Niger','Nigeria':'Nigeria','Niue':'Niue','Norfolk Island':'Norfolk Island','Northern Mariana Islands':'Northern Mariana Islands','Norway':'Norway','Oman':'Oman','Pakistan':'Pakistan','Palau':'Palau','Panama':'Panama','Papua New Guinea':'Papua New Guinea','Paraguay':'Paraguay','Peru':'Peru','Philippines':'Philippines','Pitcairn':'Pitcairn','Poland':'Poland','Portugal':'Portugal','Puerto Rico':'Puerto Rico','Qatar':'Qatar','Reunion':'Reunion','Romania':'Romania','Russia':'Russia','Rwanda':'Rwanda','Saint Kitts and Nevis':'Saint Kitts and Nevis' ,'Saint LUCIA':'Saint LUCIA','Saint Vincent':'Saint Vincent','Samoa':'Samoa','San Marino':'San Marino','Sao Tome and Principe':'Sao Tome and Principe' ,'Saudi Arabia':'Saudi Arabia','Senegal':'Senegal','Seychelles':'Seychelles','Sierra':'Sierra','Singapore':'Singapore','Slovakia':'Slovakia','Slovenia':'Slovenia','Solomon Islands':'Solomon Islands','Somalia':'Somalia','South Africa':'South Africa','South Georgia':'South Georgia','Span':'Spain','Sri Lanka':'Sri Lanka','St. Helena':'St. Helena','St. Pierre and Miguelon':'St. Pierre and Miguelon','Sudan':'Sudan','Suriname':'Suriname','Svalbard':'Svalbard','Swaziland':'Swaziland','Sweden':'Sweden','Switzerland':'Switzerland','Syria':'Syria','Taiwan':'Taiwan','Tajikistan':'Tajikistan','Tanzania':'Tanzania','Thailand':'Thailand','Togo':'Togo','Tokelau':'Tokelau','Tonga':'Tonga','Trinidad and Tobago':'Trinidad and Tobago','Tunisia':'Tunisia','Turkey':'Turkey','Turkmenistan':'Turkmenistan','Turks and Caicos':'Turks and Caicos','Tuvalu':'Tuvalu','Uganda':'Uganda','Ukraine':'Ukraine','United Arab Emirates':'United Arab Emirates','United Kingdom':'United Kingdom','United States':'United States','United States Minor Outlying Islands':'United States Minor Outlying Islands','Uruguay':'Uruguay','Uzbekistan':'Uzbekistan','Vanuatu':'Vanuatu','Venezuela':'Venezuela','Vietnam':'Vietnam','Virgin Islands (British)':'Virgin Islands (British)','Virgin Islands (U.S)':'Virgin Islands (U.S.)','Wallis and Futana Islands':'Wallis and Futuna Islands','Western Sahara':'Western Sahara','Yemen':'Yemen','Yugoslavia':'Yugoslavia','Zambia':'Zambia','Zimbabwe':'Zimbabwe'}"
																class="form-control" name="emCountry" headerKey="-1"
																headerValue="Select Country"></s:select>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Phone">Phone </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="number" name="emPhone"
																value="<s:property value='emPhone'/>"
																class="form-control" placeholder="Phone"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
														</div>
													</div>

													<div class="item form-group">
														<label for="User Type" class="control-label col-md-3 col-sm-4">Mobile</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="number" name="emMobile"
																value="<s:property value='emMobile'/>"
																placeholder="Mobile" class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-4 col-xs-12"
															for="Email">Email </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="email" name="emEmailID"
																value="<s:property value='emEmailID'/>"
																placeholder="Email Address" class="form-control">
														</div>
													</div>

												</div>
											</div>
											<!-- Ends -->

											<div class="ln_solid"></div>
											<div class="form-group">
												<div class="col-md-12 col-sm-12 col-xs-12" align="center">
													<button type="button" onclick="windowOpen();"
														class="btn btn-primary">Cancel</button>
													<button id="send" type="submit" class="btn btn-success ">Update
														Patient</button>
												</div>
											</div>

										</s:iterator>
									
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
	<!-- FastClick -->
	<script src="vendors/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script src="vendors/nprogress/nprogress.js"></script>
	<!-- validator -->
	<script src="vendors/validator/validator.js"></script>

	<!-- Datatables -->
	<script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
	<script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	<script
		src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
	<script
		src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>

	<script type="text/javascript"
		src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	
	<!-- bootstrap-daterangepicker -->
	<script src="js/moment/moment.min.js"></script>
	<script src="js/datepicker/daterangepicker.js"></script>
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
	

	<!-- Datepicker -->
	<script>
    document.addEventListener('DOMContentLoaded', function() {
        
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});

    	$('#single_cal3').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_2",
            showDropdowns: true,
            minYear: 1901,
            maxYear: parseInt(moment().format('YYYY'),10)
          }, function(start, end, label) {
            var years = moment().diff(start, 'years');
            $("#ageID").val(years);
          });
        
        $('#single_cal3').on('hide.daterangepicker', function(ev, picker) {
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
    		
    	});
        
      });
    </script>
	<!-- /Datepicker -->	
</body>
</html>