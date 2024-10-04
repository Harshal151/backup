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
    
     <!-- Datatables -->
    <link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>Add New Visit | E-Dhanvantari</title>

	<!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <!-- For full calendar -->
    <link href="build/css/fullcalendar.min.css" rel="stylesheet" />
	<link href="build/css/fullcalendar.print.min.css" rel="stylesheet" media="print" />
    
    <link rel="stylesheet" href="build/css/jquery-ui.css"> 
  
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
	</style>
	
	 <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
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
		
		String fullName = form.getFullName();

		String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");
	    
    	if(lastEneteredVisitList == null || lastEneteredVisitList == ""){
    		lastEneteredVisitList = "dummy";
    	}
    	
    	String saveVisitcheck = (String) request.getAttribute("saveVisitcheck");
		if(saveVisitcheck == null || saveVisitcheck == ""){
			saveVisitcheck = "noSave";
		}
		
    	Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = format.format(date);
		
		String[] currentDateArray = currentDate.split("-");
		
		String currDay = currentDateArray[0];
		String currMonth = currentDateArray[1];
		String currYear = currentDateArray[2];
		
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
	
	<script type="text/javascript">
	var popupWindow;
	function viewPopUp(url) {
		popupWindow = window.open(
			url,
			'popUpWindow','height=600,width=800,left=10,top=10,resizable=no,scrollbars=yes,addressbar=no,toolbar=no,menubar=no,location=0,directories=no,status=yes');
		}
	</script>
	
	<!-- Print visit, prescription and billing -->
	
	<script type="text/javascript">
		function printVisit(){
			
			document.forms["visitrForm"].action = "PrintGeneralClinicVisit";
			document.forms["visitrForm"].submit();
		}
	
		function updateOPDVisit(){
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
		
			document.forms["visitrForm"].action = "UpdateGeneralClinicVisit";
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
		String medicalCertiText = (String) request.getAttribute("medicalCertiText");
		String medicalCertiCheck = (String) request.getAttribute("medicalCertiCheck");
	%>
	
	<%
		String referralLetterText = (String) request.getAttribute("referralLetter");
		String referralLetterCheck = (String) request.getAttribute("referralLetterCheck");
	%>
	
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
				
				document.forms["genPhyBillID"].action = "AddNewBill";
				document.forms["genPhyBillID"].submit();
	   			return true;
				
			}else{
				$('.checkboxClass').prop("required", true);
				alert("Please select Payment type!");
				return false;
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
    	
    	function displayChequeDetailsDivCreditNote(input){

    		var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
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
    	
    </script>

<!-- Ends -->

<% 
 
    String paymentType = (String) request.getAttribute("paymentType");
	
		HashMap<String,String> paymentMap = new HashMap<String, String>();
		
		paymentMap.put("Cash","paymentTypeCashID=cashDetailID");
		paymentMap.put("Cheque","paymentTypeChequeID=chequeDetailID");
		paymentMap.put("Credit/Debit Card","paymentTypeCardID=cardDetailID");
		paymentMap.put("Credit Note","paymentTypeCreditNoteID=creditNoteDetailID");
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
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
		// Change balance payment

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
				
	</script>
    
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

<!-- Add more file function for lab report -->
	
	<script type="text/javascript">
	var filecnt = 1;
	
		function myFunction() {
		var id = "Uploadfile"+filecnt;
			var para = document.createElement("DIV");
			para.setAttribute("id", "labReportDivID");
			var t = document.createElement("INPUT");
			t.setAttribute("type", "file");
			t.setAttribute("name", "labReport");
			t.setAttribute("id",id);
			t.setAttribute("onchange", "validate_fileupload('"+id+"')");
			para.appendChild(t);
			document.getElementById("myID").appendChild(para);
		filecnt++;
		}
	</script>
		
	<script type="text/javascript">
	
	function validate_fileupload(input){	
	 	var fileInput = document.getElementById(input);
	    var filePath = fileInput.value;
	    var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.pdf)$/i;
	    if(filePath==null || !allowedExtensions.exec(filePath)){
	        alert('Please upload file having extensions .jpeg/.jpg/.png/.pdf only.');
	        fileInput.value="";
	        return false;
	    }else{
	    	return true;
	    }
	  } 
	</script>
	
	<!-- Ends -->
	
	<script type="text/javascript">
		function ViewLabReport(report){
			
			var queryString = "";
			queryString += "?reportsID="+report;
			
			popup = window.open('LabReportView'+queryString,"Popup", "width=700,height=700");
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
	</script>
	
	
	
	<script type="text/javascript">
		function displaycertificate(value){
			if(value=="0"){
				$("#medicalTextDivID").show();
				$("#medicalCertiTextDivID").hide();
			}else if(value=="1"){
				$("#medicalCertiTextDivID").show();
				$("#medicalTextDivID").hide();
			}
		}
		
		function displayRefLetter(value){
			if(value=="0"){  
				$("#refLetterTextDivID").show();
				$("#refLetterDivID").hide();
			}else if(value=="1"){
				$("#refLetterDivID").show();
				$("#refLetterTextDivID").hide();
			}
		}
		
	</script>
	
	<!-- Displaying Medical Certificate text -->
    
    <script type="text/javascript">
	  function displayVal(){
		  
		  $(document).ready(function(){
			  $('#companyName').prop('required',false);
			  $('#disease').prop('required',false);
			  $('#dutyDays').prop('required',false);
			  $('#wef').prop('required',false);
			  $('#place').prop('required',false);
			  
			  });
	
	  if($("#medicalCertiText").length){
		  var medicalText =document.getElementById("medicalCertiText").value;
	  }else{
		  medicalText = "";
	  }
	  
	  if(medicalText == ""){
		  
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
	
	    var medicalCertificate = doctorName+ "$" +companyName+ "$" + disease + "$" +dutyDays+ "$" + wef + "$" +place+ "$" +visitDte;
	   
	   var medicalCertificateForPDF = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in "+companyName1+ ", is suffering from " + disease1 + " and I consider that the period of absence from duty of "+ dutyDays + " days with effect from "+ wef1 + " is absolutely necessary for the restoration of his/her health.\n\n\n Place: "+place1+ " \n\n\n Date: " +visitDte + " \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    document.getElementById("medicalCerti").value = medicalCertificate;
	
	    document.getElementById("medicalCertiForPDF").value = medicalCertificateForPDF;
	    
	 }else{
		  document.getElementById("medicalCerti").value = medicalText;
			
		  document.getElementById("medicalCertiForPDF").value = medicalText;
	  }
		return true;
	  }
	  
	</script>
	
	<!-- Ends -->
	
	<!-- Displaying referral letter text -->	
	
	<script type="text/javascript">
	  function displayRefLet(){
		 
		  $(document).ready(function(){
			  $('#conditionText').prop('required',false);
  
			  });
		  
		  if($("#refLetterText").length){
			  var refLetText =document.getElementById("refLetterText").value; 
		  }else{
			  refLetText = "";
		  }
	 
	  if(refLetText == ""){
			  
	    var conditionText = document.getElementById("conditionText").value;
	    var doctName = document.getElementById("doctName").value;
	    var doctorName = document.getElementById("doctrNameRef").value;
	    var patientName = document.getElementById("patientNameRef").value;
	    var visitDte = document.getElementById("date12").value;
	    var conditionText1 = document.getElementById("conditionText").value;
	    var doctName1 = document.getElementById("doctName").value;
	
	    var referralLetter = visitDte+ "$" + doctName + "$" +patientName+ "$" + conditionText + "$" + doctorName;
	   
	    var referralLetterForPDF = "Date: "+visitDte+ "\n\n\n Dear Dr. "+ doctName1 + ", \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n " + conditionText1 + " \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
	    document.getElementById("referralLetter").value = referralLetter;
	
	    document.getElementById("referralLetterForPDF").value = referralLetterForPDF;
	
	  }else{
			
		  document.getElementById("referralLetter").value = refLetText;
			
		  document.getElementById("referralLetterForPDF").value = refLetText;
	  }
	  
	  return true;
  }
	</script>
	
	<!-- Ends -->	
	
	
	<!-- Retrieve tabs by practice ID -->
    
    <%
    	HashMap<String, String> ValueMap = new HashMap<String, String>();
    
	    ValueMap.put("clinician","OPD Visit,Billing,Report,Medical Certificate,Referral Letter");
	
    	String tabs = ValueMap.get(form.getUserType());
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("OPD Visit", "visit");
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

		String medicalCertificateTabEnable = (String) request.getAttribute("medicalCertificateTabEnable");
	
		if(medicalCertificateTabEnable == null || medicalCertificateTabEnable == ""){
			medicalCertificateTabEnable = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#medCerti"]').tab('show');
			
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
	
		String referralLetterTabEnable = (String) request.getAttribute("referralLetterTabEnable");
	
		if(referralLetterTabEnable == null || referralLetterTabEnable == ""){
			referralLetterTabEnable = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#refLetter"]').tab('show');
			
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
	
		String labReportResult = (String) request.getAttribute("labReportResult");
	
		if(labReportResult == null || labReportResult == ""){
			labReportResult = "dummy";
		}
	
		String labReportTabEnable = (String) request.getAttribute("labReportTabEnable");
	
		if(labReportTabEnable == null || labReportTabEnable == ""){
			labReportTabEnable = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#labRep"]').tab('show');
			
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
    
	<!-- Add prescription -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addOPDPrescription(drugName, frequency1, visitID, comment, noOfPills,noOfDays){
    		console.log("frequency val at view::"+frequency1);
    		if(drugName == null || drugName.trim() === ''){
				alert("Please select drug name");
			}else if(noOfDays == ""){
				alert("Please insert no. of days");
			}else if(frequency1 == ""){
				alert("Please add frequency details");
			}else{
			
				addOPDPrescription1(drugName, frequency1, visitID, comment, noOfPills,noOfDays)
			}
    	}
		
    	var prescCounter = 1;
    	
		function addOPDPrescription1(drugName, frequency1, visitID, comment, noOfPills,noOfDays){
			
			var TRID = "newPrescTRID"+prescCounter;
			  
			console.log("frequency val at view::"+frequency1);
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+drugName+"<input type='hidden' name='newDrugID' value='"+drugName+"'></td>"
					   
					   +"<td style='text-align:center'>"+frequency1+"<input type='hidden' name='newDrugFrequency' value='"+frequency1+"'></td>"
					   +"<td style='text-align:center'>"+noOfDays+"<input type='hidden' name='newDrugNoOfDays' value='"+noOfDays+"'></td>"
					   +"<td style='text-align:center'>"+noOfPills+"<input type='hidden' name='newDrugQuantity' value='"+noOfPills+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#prescTRID"));
			
			prescCounter++;
			
			$("#tradeName").val("");
			$("#noOfDays").val("");
			$("#noOfPills").val("");
			$("#comment").val("");
			$("#frequency1").val("");
		}
		
		function removeTR(TRID){
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete prescription row
		function deletePrescRow(trID, prescriptionID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteprscription(trID, prescriptionID);
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
	
		function deleteprscription(trID, prescriptionID) {
	
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
					
				}
			};
			xmlhttp.open("GET", "DeletePrescription?prescriptionID="+ prescriptionID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->

<!-- Add Investigation -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addInvestigation(investigation){
    		var autoSelecter = dojo.widget.byId("investigation");
			var investigation = autoSelecter.getSelectedValue();
			
    		if(investigation == "-1"){
				alert("Please select investigation test name");
			}else{
				addInvestigation1(investigation);
			}
    	}
		
    	var investCounter = 1;
    
		function addInvestigation1(investigation){
			var autoSelecter = dojo.widget.byId("investigation");
			
			var TRID = "newInvestTRID"+investCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center' >"+investigation+"<input type='hidden' name='test' value='"+investigation+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeInvestTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
					    
			$(trTag).insertAfter($("#investTRID"));
			
			investCounter++;
			
			autoSelecter.innerHTML = "";
			$("#investigation").val("-1");
		}
		
		function removeInvestTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete investigation row
		function deleteInvestigationRow(trID, investigationID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteInvestigation(trID, investigationID);
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
	
		function deleteInvestigation(trID, investigationID) {
	
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
						document.getElementById("successMSG").innerHTML = "Investigation details deleted successfully.";
						$("#"+trID).remove();
					}
				}
			};
			xmlhttp.open("GET", "DeleteInvestigation?investigationID="+ investigationID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->

	<!-- Add Complaint -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addComplaintRow(symptom, duration, comments){
 				console.log("value:"+duration+"-" +comments);
				addComplaintRow1(symptom, duration,comments)
    	}
		
    	var compCounter = 1;
    
		function addComplaintRow1(symptom, duration,comments, visitID){
			
			var TRID = "newCompTRID"+compCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+symptom+"<input type='hidden' name='newSymptom' value='"+symptom+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'>"+comments+"<input type='hidden' name='PnewComments' value='"+comments+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#complaintTRID"));
			
			compCounter++;
			  
			$("#symptomOID").val("");
			$("#presentDurationID").val("");
			$("#presentCommentsID").val("");
			
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete complaint row
		function deleteComplaintRow(trID, complaintID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteComplaintRow1(trID, complaintID);
			}
		}

	</script>
	<!-- Ends -->
	
	<!-- Delete Complaint -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteComplaintRow1(trID, complaintID) {
	
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
						document.getElementById("successMSG").innerHTML = "Complaint details deleted successfully.";
						$("#"+trID).remove();
					}	
				}
			};
			xmlhttp.open("GET", "DeleteComplaint?complaintID="+ complaintID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	
	<!-- Add MHO -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addMedicalHistoryRow(diagnosis, description){
 				
 				addMedicalHistoryRow1(diagnosis, description)
    	}
		
    	var medCounter = 1;
    
		function addMedicalHistoryRow1(diagnosis, description){
			
			var TRID = "newMediTRID"+medCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+diagnosis+"<input type='hidden' name='newDiagnosis' value='"+diagnosis+"'></td>"
					   +"<td style='text-align:center'>"+description+"<input type='hidden' name='newDescription' value='"+description+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#HistoryTRID"));
			
			medCounter++;
			  
			$("#MHOdiagnosisID").val("");
			$("#MHOdescriptionID").val("");
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete MHO row
		function deleteHistoryRow(trID, historyID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteHistoryRow(trID, historyID);
			}
		}

	</script>
	<!-- Ends -->
	
	<!-- Delete MHO -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteHistoryRow(trID, historyID) {
	
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
						document.getElementById("successMSG").innerHTML = "Medical History details deleted successfully.";
						$("#"+trID).remove();
					}
				}
			};
			xmlhttp.open("GET", "DeleteMedicalHistory?historyID="+ historyID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	
	<!-- Add Current Medication -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addMedicationRow(drugName, duration, comments){
 				
    		addMedicationRow1(drugName, duration, comments)
    	}
		
    	var currmedCounter = 1;
    
		function addMedicationRow1(drugName, duration, comments){
			console.log("drugname is::"+drugName);
			console.log("duration is::"+duration);
			console.log("comments is::"+comments);
			
			var TRID = "newCurrMediTRID"+currmedCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+drugName+"<input type='hidden' name='newDrugname' value='"+drugName+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'>"+comments+"<input type='hidden' name='CnewComments' value='"+comments+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#MedicationTRID"));
			
			currmedCounter++;
			  
			$("#CurrentMedidrugnameID").val("");
			$("#CurrentMedidurationID").val("");
			$("#CurrentMedicommentsID").val("");
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete MHO row
		function deleteMedicationRow(drugName, duration, comments){
			if(confirm("Are you sure you want to delete this row?")){
				deleteMedicationRow(trID, medicationID);
			}
		}

	</script>
	<!-- Ends -->
	
	<!-- Delete Current Medication -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteMedicationRow(trID, medicationID) {
	
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
						document.getElementById("successMSG").innerHTML = "Current Medication details deleted successfully.";
						$("#"+trID).remove();
					}
				}
			};
			xmlhttp.open("GET", "DeleteCurrentMedication?medicationID="+ medicationID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	<!-- Add Symptom -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addSymptomRow(symptom, value, visitID){
    		
    		if(symptom == ""){
				alert("Please enter symptom name");
			}else if(value == "-1"){
				alert("Please select symptom value");
			}else{
				addSymptomRow1(symptom, value, visitID)
			}
    	}
		
    	var symptCounter = 1;
    
		function addSymptomRow1(symptom, value, visitID){
			
			var TRID = "newSympTRID"+symptCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+symptom+"<input type='hidden' name='newSymptom' value='"+symptom+"'></td>"
					   +"<td style='text-align:center'>"+value+"<input type='hidden' name='newValue' value='"+value+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#symptomTRID"));
			
			prescCounter++;
			  
			$("#valueID").val("-1");
			$("#symptomOID").val("");
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
		}
		
		//function to delete Symptom row
		function deleteSymptomRow(trID, symptomID){
			if(confirm("Are you sure you want to delete this row?")){
				deleteSymptomRow1(trID, symptomID);
			}
		}

	</script>
	<!-- Ends -->
	
	<!-- Delete Symptom -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteSymptomRow1(trID, symptomID) {
	
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
						document.getElementById("successMSG").innerHTML = "Symptom details deleted successfully.";
						$("#"+trID).remove();
					}
				}
			};
			xmlhttp.open("GET", "DeleteSymptom?symptomID="+ symptomID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
    
    
	
	<%
	
	int apptID = (Integer) request.getAttribute("apptID");
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
	
	%>
    
<script type="text/javascript">
	
	function CategoryValue(tradeName){
	
		if(tradeName == ""){
			alert("Please select drug name");
			$("#categoryID").val("-1");
			
		}else{
			retrieveCategoryWithDrugName(tradeName);
		}
	}
	
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function retrieveCategoryWithDrugName(tradeName) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var categoryID = "";

				for ( var i = 0; i < array.Release.length; i++) {
				
					categoryID = array.Release[i].categoryID;
					check = array.Release[i].check;
				}
				
				if(check == 1){
					$("#categoryID").val(categoryID);
					
				}else{
					$("#categoryID").val("-1");
				}
				
			}
		};
		xmlhttp.open("GET", "RetrieveCategoryWithDrugName?tradeName="+ tradeName , true);
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
  
  <body class="nav-md">
		<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
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
                        <h3>ADD NEW VISIT</h3>
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
					  	System.out.println("..."+tabs);
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
				    <form class="form-horizontal form-label-left" novalidate action="AddNewGeneralClinicVisit" onsubmit="return showLoadingImg();" name="visitrForm" id="visitrForm" method="POST" style="margin-top:20px;">
				    
				    <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
			          
					<div class="row" style="margin-top:15px;">
						<center>
							<h4><b>CASE REPORT FORM</b></h4>
						</center>
					</div>
						  
	                <div class="row" style="margin-top:15px;">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"  >
							 <table border="1" width="98%">
							    	<tr>
							    		<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Name: </td>
							    		<td colspan="2"> <input type="text" style="border:none;cursor:not-allowed;background: transparent;"  class="form-control" readonly="readonly" value="<s:property value="lastName"/> <s:property value="firstName"/> <s:property value="middleName"/>"  name="" placeholder="Patient Name" autofocus=""></td>
							    		<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Date of first visit: </td>
							    		<td colspan="1" style="padding-left:10px;font-size:14px;"> <input type="text" class="form-control" name="" style="border:none;cursor:not-allowed;background: transparent;" readonly="readonly" value="<s:property value="visitDate"/>" aria-describedby="inputSuccess2Status3"> </td>
							    	</tr>
							    			
							    	<tr>
							    		<td style="padding-left:10px;font-size:14px; font-weight: bold;">Age: </td>
							    		<td>
							    			<input type="number" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" name="age" value="<s:property value="age"/>" placeholder="Age" autofocus=""> 
							    		</td>
							    		<td style="padding-left:10px;font-size:14px; font-weight: bold;">Sex: </td>
							    		<td colspan="3">
							    			<input type="text" name="gender" style="border:none;cursor:not-allowed;background: transparent;" class="form-control" readonly="readonly" value="<s:property value="gender"/>" placeholder="Gender" autofocus="">
							    		</td>
							    	</tr>
							    	<tr>
							    		<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Address: </td>
							    		<td colspan="4"> <input type="text" class="form-control" style="border:none;cursor:not-allowed;background: transparent;" value="<s:property value="address"/>" readonly="readonly"  name="address" placeholder="" autofocus=""></td>
							    	</tr>
							    			
							    	<tr>
							    		<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Phone Number: </td>
							    		<td colspan="4"><input type="number" name="mobile" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control"  value="<s:property value="mobile"/>" autofocus=""  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status"></td>
							    		<%-- <td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Alternate Number: </td>
							    		<td colspan="2"> <input type="number" name="phone" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" value="<s:property value="phone"/>" autofocus=""  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status"></td> --%>
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
						   	<div class="col-md-3 col-xs-12">
						    	<font style="font-size: 15px;font-weight: bold;padding-left: 10px;">Medical registration number: </font>
						    </div>
						    	
						    <div class="col-md-3">
						    	<input class="form-control" name="registrationNo" readonly="readonly" value="<s:property value="registrationNo"/>" placeholder="Medical registration number">
						    </div>
						    
						    <div class="col-md-3 col-xs-6 col-sm-8">
						    		<font style="font-size: 14px;font-weight: bold;">Visit Date (DD/MM/YYYY): </font>
						    </div>	
						<%	
							if(saveVisitcheck.equals("Saved")){
						%>
						    	<div class="col-md-1 col-xs-2" style="margin-left: -2%">
						    		<input type="text" class="form-control" name="visitDay" value="<s:property value="visitDay"/>" id="visitDayID" placeholder="Day" maxlength="2" onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
						    		<font style="color: red;" id="dateFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitMonth" value="<s:property value="visitMonth"/>" id="visitMonthID" placeholder="Month" maxlength="2" onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
						    		<font style="color: red;" id="monthFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitYear" value="<s:property value="visitYear"/>" id="visitYearID" placeholder="Year" maxlength="4" onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
						    		<font style="color: red;" id="yearFontID"></font>
						    	</div>
						    	
						    	<input type="hidden" class="form-control" name="visitDate" value="<s:property value="firstVisitDate"/>" id="finalDateID">
						    	
						 <%
							}else{
						 %>
						    	
						    	<div class="col-md-1 col-xs-2" style="margin-left: -2%">
						    		<input type="text" class="form-control" name="visitDay" value="<%=currDay %>" id="visitDayID" placeholder="Day" maxlength="2" onkeyup="dateCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'dateFontID', 'visitMonthID', 'finalDateID');">
						    		<font style="color: red;" id="dateFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitMonth" value="<%=currMonth %>" id="visitMonthID" placeholder="Month" maxlength="2" onkeyup="monthCheck(visitDayID.value, visitMonthID.value, visitYearID.value, 'monthFontID', 'visitYearID', 'finalDateID');">
						    		<font style="color: red;" id="monthFontID"></font>
						    	</div>
						    	<div class="col-md-1 col-xs-2">
						    		<input type="text" class="form-control" name="visitYear" value="<%=currYear %>" id="visitYearID" placeholder="Year" maxlength="4" onkeyup="yearCheck(visitYearID.value, visitMonthID.value, visitDayID.value, 'yearFontID', 'finalDateID');">
						    		<font style="color: red;" id="yearFontID"></font>
						    	</div>
						    	
						    	<input type="hidden" class="form-control" name="visitDate" value="<%=currentDate %>" id="finalDateID">
						<%
							}
						%>
							</div>
					
					<div class="row" style="margin-top:20px;">
					 	<div class="col-md-3 col-xs-6">
					 		<label for="User Type" style="font-size: 15px; padding-left: 10px;">Diagnosis </label>
					 	</div>
						<div class="col-md-3"><sx:autocompleter name="cancerType" cssStyle="padding-left:14px;" list="diagnoseList" showDownArrow="false" id="search" cssClass="form-control"/></div>
					</div>
							
	            	<div class="row" style="padding-top: 20px;">
		             	<div class="col-md-3 col-xs-6">
		             	 	<label for="User Type" style="font-size: 15px; padding-left: 10px; ">Complaining Of</label>
		             	</div>
		             	<div class="col-md-6">
		             	 	<s:textarea name="comments" class="form-control" placeholder="comments"></s:textarea>
		             	</div>
		            </div>
		            
		            <div class="row" style="padding-top: 20px;">
		             	<div class="col-md-3 col-xs-6">
		             	 	<label for="User Type" style="font-size: 15px; padding-left: 10px; ">History Of</label>
		             	</div>
		             	<div class="col-md-6">
		             	 	<s:textarea name="medicalHistorycomments" class="form-control" placeholder="comments"></s:textarea>
		             	</div>
		            </div>
		            
		            <div class="row" style="padding-top: 20px;">
		             	<div class="col-md-3 col-xs-6">
		             	 	<label for="User Type" style="font-size: 15px; padding-left: 10px; ">On Examination</label>
		             	</div>
		             	<div class="col-md-6">
		             	 	<s:textarea name="onExamination" class="form-control" placeholder="description"></s:textarea>
		             	</div>
		            </div>
		            
		            <div class="row" style="padding-top: 20px;">
		             	<div class="col-md-3 col-xs-6">
		             	 	<label for="User Type" style="font-size: 15px; padding-left: 10px;">LMP</label>
		             	</div>
		             	<div class="col-md-3">
		             	 	<input type="text" class="form-control" name="lastMenstrualPeriod" id="lastMenstrualPeriodID" value="<s:property value="lastMenstrualPeriod"/>" placeholder="dd-MM-yyyy" autocomplete="off">
		             	</div>
		             	<div class="col-md-3 col-xs-6">
		             	 	<label for="User Type" style="font-size: 15px; ">EDD</label>
		             	</div>
		             	<div class="col-md-3" style="margin-left: -2%">
		             	 	<input type="text" class="form-control" readonly="readonly" name="estimatedDueDate" value="<s:property value="estimatedDueDate"/>" id="estimatedDueDateID" >
		             	</div>
		            </div>
		       
		        <!-- Prescription div -->
		        
		       <div class="row" style="padding-top: 20px;margin-left:0px;margin-bottom:5px;">
					 <h4 style="margin-left: 10px;"><b>Prescription Details:</b></h4>	
					<div class="row" style="padding-top: 20px;">
					 	<div class="col-md-3" >
						  	<label for="User Type" style="font-size: 15px; padding-left: 10px; ">Advice</label>
						</div>	
						<div class="col-md-4">
						  	<input type="text" name="advice" id="adviceID" class="form-control" placeholder="Advice" value="<s:property value="advice"/>">
						</div>
					</div>
					
					<label for="User Type" style="font-size: 15px; padding-left: 10px; padding-top: 20px;">Investigation</label>
				
				      <div class="row" style="margin-left:0px;margin-right:0px;margin-top:15px;">
				      	 <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 29%; margin-left: 1%;" >
					            <thead>
									<tr>
					                   <th style="width: 80%">Test Name</th>
					                   <th>Action</th>
					                </tr>
					            </thead>
					            <tbody>
					              <s:iterator value="InvestigationTestsList">
					                  <tr id="investigationTRID<s:property value="investigationID"/>">
					                      <td style="font-size: 14px;text-align: center;"><s:property value="investigation"/></td>
					                      <td style="font-size: 14px;text-align: center;"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick="deleteInvestigationRow('investigationTRID<s:property value="investigationID"/>',<s:property value="investigationID"/>);"/> </td>
					                  </tr>
					              </s:iterator>
					                            	
					            	<tr id="investTRID">
					                </tr>
					                            	
					             </tbody>
					         </table>
				          </div>
				
				          <div class="col-md-3">
				             <sx:autocompleter name="investigation" cssStyle="padding-left:14px;" cssClass="form-control" id="investigation" list="InvestigationList" showDownArrow="false" />
				          </div>
				            
				          <div class="col-md-2">
				              <a onclick="addInvestigation('');" > 
							  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'"
							  	alt="Add Value" title="Add Value" style="margin-top:5px;height: 24px;" />
							  </a>
				         </div>
				     </div>
				      
				      <div class="row" style="margin-top:10px;margin-left:0px;margin-bottom:5px;">
					
				      <h4 style="margin-top:20px;margin-left:15px;"><b>Prescription</b></h4>
				
				      <div class="row" id="OPDPResc" style="margin-left:0px;margin-right:0px;margin-top:15px;">
				      	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 97%; margin-left: 1%;">
					        <thead>
								<tr>
					            	<th style="width: 25%">Drug Name</th>
					                <th style="width: 17%">Frequency</th>
					                <th style="width: 10%">No. of days</th>
					                <th style="width: 9%">Quantity</th>
					                <th style="width: 31%">Comments</th>
					                <th style="">Action</th>
					            </tr>
					         </thead>
					         <tbody>
					            <s:iterator value="prescriptionList">
					                <tr id="visitPrescTRID<s:property value="prescriptionID"/>">
					                   <td style="font-size: 14px;  margin-top:10px; text-align: center;"><s:property value="tradeName"/></td>
					                            			
					                   <td style="font-size: 14px;  margin-top:10px; text-align: center;"><s:property value="frequency"/></td>
					                            			
					                   <td style="font-size: 14px; text-align: center;"><s:property value="noOfDays"/></td>
					                            			
					                   <td style="font-size: 14px; text-align: center;"><s:property value="productQuantity"/></td>
					                            			
					                   <td style="font-size: 14px;text-align: center;"><s:property value="comment"/></td>
					                            			
					                   <td style="font-size: 14px; text-align: center;"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick="deletePrescRow('visitPrescTRID<s:property value="prescriptionID"/>',<s:property value="prescriptionID"/>);"/> </td>
					               </tr>
					           </s:iterator>
					                            	
					           <tr id="prescTRID"> </tr>
					         </tbody>
					     </table>
				       </div>
				
				            <div class="col-md-3 col-xs-7 col-sm-3" >
				            	<div>
									<input type='text' required='required' name="tradeName" style="margin-top:10px;" class='form-control' id="tradeName" placeholder='Drug Name'>
									
								</div>
							</div>
				            
				            <div class="col-md-2 col-xs-7 col-sm-2">
				            	<s:select list="frequencyList" name="frequency" style="margin-top:10px;"  id="frequency1" class="form-control" headerKey="-1" headerValue="Frequency"></s:select>
				            </div>
				           
				           <div class="col-md-1 col-xs-7 col-sm-1">
				              <input type="number" class="form-control" style="margin-top:10px;"  name="noOfDays" id="noOfDays"  placeholder="Days">
				            </div>
				            
				            <div class="col-md-1 col-xs-7 col-sm-1">
				              <input type="number" class="form-control" style="margin-top:10px;"  name="noOfPills" id="noOfPills"  placeholder="Qty">
				            </div>
				            
				            <div class="col-md-4 col-xs-7 col-sm-4">
				              <input type="text" class="form-control" style="margin-top:10px;"  name="comment" id="comment"  placeholder="Comment">
				            </div>
				            
				            <div class="col-md-1">
				              <a onclick="addOPDPrescription(tradeName.value ,frequency1.value ,visitID.value,comment.value,noOfPills.value,noOfDays.value);" > 
							  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
									onmouseout="this.src='images/add_icon_1.png'" alt="Add Prescription"
									title="Add Prescription" style="margin-top:5px;height: 24px; margin-top:10px;" />
							  </a>
				            </div>
				      </div>
				    
				    <div class="row" style="margin-top:100px;"> 
					      <div class="col-md-3"></div>
					      <div class="col-md-3">
                          	  <label class="control-label" for="Date" style="padding-left:145px;">Follow-up after</label>
                          </div>
                          <div class="col-md-1 col-xs-3" >
                            <input type="number" class="form-control" name="nextVisitDays" value= "<s:property value="nextVisitDays"/>" id="nextVisitDaysID" >
                          </div>
                          <div class="col-md-1">
                          	  <label class="control-label" style="text-align: left;" for="Date">Days.</label>
                          </div>
                          <div class="col-md-3"></div>
                      </div>
                      
				      <div class="ln_solid"></div>
		                    
	                     <div class="form-group" >
	                        <div class="col-md-12 col-xs-12"  style="padding-top: 50px;" align="center">
	                          <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
	                          
	                          <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button class="btn btn-success" type="button" id="send" onclick="updateOPDVisit();">Save</button>
	                          <button class="btn btn-warning" type="button" id="printButton" onclick="printVisit();">Print</button> 
	                           <%
					        	}else{
			                 %>
			                 <button id="send" type="submit" class="btn btn-success " >Save</button>
			              
			                 <%
					        	}
			                 %>
	                         
	                        </div>
	                      </div> 
				        
				      </form>
				      
			      </div>
			    <!--  END -->
			    
			    <!-- Billing div -->
			     <div class="tab-pane fade" id="billing" >
			      <form class="form-horizontal form-label-left" onsubmit = "return checkCheckbox();" id="genPhyBillID" name="genPhyBillID" action="AddNewBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						 </div>
						      
					      <input type="visible" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
					      <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
					       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
							
						<s:iterator value="billList">

						<div class="row" style="margin-top: 15px;">
							
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt Date:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptDate"/></font>
								<input type="hidden" name="receiptDate" value="<s:property value="receiptDate"/>">
							</div>
							<div class="col-md-2 col-xs-4 col-sm-2">
								<font style="font-size: 14px;">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" value="<s:property value="receiptNo"/>">
							</div>
							
						</div>

						<div class="row" style="margin-top: 15px;">
							
							<div class="col-md-2 col-xs-3">
								<font style="font-size: 14px;">Diagnosis</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="cancerType"/></font>
								<input type="hidden" name="visitDate" value="<s:property value="cancerType"/>">
							</div>
							<div class="col-md-2 col-xs-12 col-sm-2">
								<font style="font-size: 14px;">Visit Type:</font>
							</div>
							<div class="col-md-4 col-xs-11 col-sm-4">
								<input type="text" class="form-control" readonly="readonly" value="<%=visitType%>">
							</div>
						</div>
						
	                      <div class="item form-group" style="margin-top:15px;">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Charges(Rs)<span class="required">*</span></label>
	                         <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text" class="form-control" name="charges" id="charges" required="required" value="<s:property value="netAmount"/>" onkeyup="changeTotalBill('totalBill',charges.value);" placeholder="Charges(Rs)">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Total Bill(Rs)</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text" class="form-control"  name="totalBill" id="totalBill" value="<s:property value="netAmount"/>" readonly="readonly" placeholder="Total Bill(Rs)">
	                        </div>
	                      </div>
	                      
	                      <%--  <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Amount Paid<span class="required">*</span></label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="number" class="form-control" name="advPayment" id="advPaymentID" required="required" value = "<s:property value="advPayment"/>" onkeyup="changeBalancePayment(advPaymentID.value, totalBill.value);">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Balance Payment</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                         <input type="number" class="form-control" name="balPayment" value = "<s:property value="balPayment"/>" id="balPaymentID">
	                        </div>
	                      </div> --%>
	                      
	                      <div class="row" style="padding: 0 15px 0 15px;">
							<div class="col-md-2 col-sm-3 col-xs-5" style="padding-top: 5px;">
								<font style="font-size: 16px;">Payment Type*</font>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="hideChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeCashID" value="Cash" class="checkboxClass form-control">
										
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cash</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeChequeID" value="Cheque" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cheque</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayCardDiv(this);" style="height: 25px; box-shadow: none;"
										id="paymentTypeCardID" value="Credit/Debit Card" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit/Debit Card</font>
								</div>
							</div>
							<div class="col-sm-3"></div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivCreditNote(this);"
										style="height: 25px; box-shadow: none;" id="paymentTypeCreditNoteID" value="Credit Note" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit Note</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivOther(this);"
										style="height: 25px; box-shadow: none;" id="paymentTypeOtherID" value="Other" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Other</font>
								</div>
							</div>
						</div>
						
						<%-- <div id="cashDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6" style="padding-top:3%;">
									<font style="font-size: 16px;">Cash Paid</font><span class="required" >*</span>
								</div>
								<div class="col-md-2 col-xs-4" style="padding-top:3%;">
									<input type="text" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, totalBill.value);"
										value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
								</div>
								<div class="col-md-2 col-xs-6" style="padding-top:3%;">
									<font style="font-size: 16px;">Cash To Return</font>
								</div>
								<div class="col-md-2 col-xs-4" style="padding-top:3%;">
									<input class="form-control" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>" placeholder="Cash To Return" type="number">
								</div>
							</div>
						</div> --%>
						
						<div id="chequeDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row" style="margin-top: 15px;">
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
									<input class="form-control" name="chequeNo" id="chequeNoID" value="<s:property value="chequeNo"/>" placeholder="Cheque No." type="text">
								</div>
							</div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Bank Name</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input type="text" class="form-control" id="chequeBankNameID" value="<s:property value="chequeBankName"/>" name="chequeBankName" placeholder="Bank Name">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Branch</font><span class="required" >*</span>
								</div>
								<div class="col-md-3">
									<input class="form-control" name="chequeBankBranch" id="chequeBankBranchID" value="<s:property value="chequeBankBranch"/>" placeholder="Branch" type="text">
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
									<input class="form-control" name="chequeAmt" id="chequeAmtID" value="<s:property value="chequeAmt"/>" placeholder="Amount" type="number">
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
									<input type="number" class="form-control" id="cMobileNoID" value="<s:property value="cMobileNo"/>" name="cMobileNo" placeholder="Mobile No.">
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
		              
			       		    <button class="btn btn-success" type="submit" name="addButton" value="add" id="billSubmtBtnID" >Save</button>
			        
			        		<button class="btn btn-warning" type="submit" name="addButton" value="print" id="billSubmtBtnID">Save & Print</button>
			        
		                </div>
		           </div>
			      </form>
			      
			      <div class="ln_solid"></div>
			      
			    </div>
			    <!-- ends -->
			    
			    <!-- Lab report div -->
			    <div class="tab-pane fade" id="labRep" >
			    
			      <form class="form-horizontal form-label-left" novalidate action="AddLabReport" name="labRepForm" onsubmit="return showLoadingImg();" id="labRepForm" method="POST" style="margin-top:20px;" enctype="multipart/form-data">
			      
			      	<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
			        <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
			        <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			        <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			        <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">

			       <div class="row" style="margin-top:10px;margin-left:0px;padding-left:15px;">
			          <h5 style="margin-top:0px;font-size:16px;">Upload Lab Report File(s) here</h5>
			      </div>
			      
			      <div class="row" style="padding-left:25px;">
			      	<div class="col-md-3" style="margin-top:10px;margin-bottom:10px;">
			      		<input type="file" name="labReport" id="Uploadfile" onchange="validate_fileupload('Uploadfile');">
			      		
					</div>
			      	<div class="col-md-6" style="margin-top:10px;">
			      		<button type="button" class="btn btn-default" onclick="myFunction();">Add More Files</button>
			      	</div>
			      	
			      	<div class="col-md-12" id="myID"></div>
			      </div>
			      
			      <div class="row" style="padding: 15px;">
					<table id="labRepTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th style="width: 30%;">File Name</th>
								<th style="width: 30%; text-align: center;">Action</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="labReportList" var="form">
								<tr id="labRepTRID<s:property value="reportsID"/>">
									<td><s:property value="reportDBName" /></td>
									<td style="text-align: center;">
										<button class="btn btn-warning" type="button" onclick="ViewLabReport('<s:property value="reportsID"/>');">View</button>
									<%-- 	<button class="btn btn-success" type="button" onclick="downloadLabReport('<s:property value="reportsID"/>');">Download</button> --%>
										<img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' 
											onclick="deleteReportRow('labRepTRID<s:property value="reportsID"/>', <s:property value="reportsID"/>);"/> 
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				
			      <div class="ln_solid"></div>
		                      
		          <div class="form-group">
		             <div class="col-md-12" align="center">
		                 <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		               
		                 <button class="btn btn-success" type="submit" id="uploadLabReportBtnID"  >Add Report</button>
		             </div>
		          </div>
			      
			      </form>
			    </div>
			    <!-- Ends -->
			    
			    
			    <!-- Medical certificate -->
			    <div class="tab-pane fade" id="medCerti" >
			    	<form class="app-cam" id="" name="" action="AddMedicalCerificate"   method="POST" style="width:95%;margin:0px;" >
						
						<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName"  value="<%=fullName %>">
					        <input type="hidden" id="patientName"  value="<s:property value="firstName"/>  <s:property value="lastName"/>">
					       
					        <textarea style="display: none" name="medicalCerti" id="medicalCerti" cols="" rows="5"></textarea>
					        <textarea style="display: none" name="medicalCertiForPDF" id="medicalCertiForPDF" cols="" rows="5"></textarea>
										<%
					     					if (medicalCertiCheck == "no") {
					     				%>
										<div class="row" style="margin:15px;">
					 						<s:radio list="#{'1':'Pre-defined','0':'Free-text'}" name="radioVal" id="radioValID" onclick="displaycertificate(this.value);"></s:radio>
				    					</div>
				    					<%
					     					}
				    					%>
					    				<div class="row" style="margin:15px;">
					    
					     				<%
					     				if (medicalCertiText == null || medicalCertiText == "") {
					     				%>
					     				
				    					<div class="col-md-12 col-xs-12" id="medicalTextDivID" style="margin:25px;display: none;" >
											<s:textarea cols="2" rows="10" id="medicalCertiText" name="medicalCertiText" style="width:100%;"></s:textarea>
										</div>
					     				
					     				<div class="col-md-12 col-xs-12" id= "medicalCertiTextDivID" style="border:1px solid gray;margin:25px;display: none;">
									
									    <label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
									      after careful personal examination of the case hereby certify that, 
									      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName" required>, 
									      is suffering from <input type="text" class="form-control"  name="disease" id="disease" style="width:20%; display: inline-block;margin-top:10px;" required> and I consider that 
									      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> days 
									      with effect from <input type="text" class="form-control"  name="wef" id="wef" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> is 
									      absolutely necessary for the restoration of his/her health.</label>
									
									      <div class="row" style="margin-top:25px;">
									        <div class="col-md-12 col-xs-12">
									          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
									          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place" required></div>
									        </div>
									      </div>
									
									      <div class="row" style="margin-top:25px;margin-bottom: 20%;">
									        <div class="col-md-12 col-xs-12">
									          <div class="col-md-1" style="padding-top: 5px;"> <label>Date:</label></div>
									          <div class="col-md-3" ><input type="text" class="form-control" value="<%=currentDate %>"  name="date" id="date11"></div>
									          <div class="col-md-4" style="padding-top: 20px;" align="center"> <label>(SEAL)</label></div>
									          <div class="col-md-4" style="padding-top: 20px;"><label>Registered Medical Practitioner</label></div>
									        </div>
									      </div>
									      </div>
									    
					     				<% 	
					     				} else {
					     					
									    	if(medicalCertiText.contains("$")){
									    		String[] strval = medicalCertiText.split("\\$");
									    	
									    %>
				    					
					     				<div class="col-md-12 col-xs-12" id= "medicalCertiTextDivID" style="border:1px solid gray;margin:25px;">
									  
									    <label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
									      after careful personal examination of the case hereby certify that, 
									      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName" value="<%=strval[1] %>" required>, 
									      is suffering from <input type="text" class="form-control"  name="disease" id="disease" value="<%=strval[2] %>" style="width:20%; display: inline-block;margin-top:10px;" required> and I consider that 
									      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" value="<%=strval[3] %>" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> days 
									      with effect from <input type="text" class="form-control"  name="wef" id="wef" value="<%=strval[4] %>" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> is 
									      absolutely necessary for the restoration of his/her health.</label>
									
									      <div class="row" style="margin-top:25px;">
									        <div class="col-md-12 col-xs-12">
									          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
									          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place" value="<%=strval[5] %>" required></div>
									        </div>
									      </div>
									
									      <div class="row" style="margin-top:25px;margin-bottom: 20%;">
									        <div class="col-md-12 col-xs-12">
									          <div class="col-md-1" style="padding-top: 5px;"> <label>Date:</label></div>
									          <div class="col-md-3" ><input type="text" class="form-control" value="<%=strval[6] %>" name="date" id="date11"></div>
									          <div class="col-md-4" style="padding-top: 20px;" align="center"> <label>(SEAL)</label></div>
									          <div class="col-md-4" style="padding-top: 20px;"><label>Registered Medical Practitioner</label></div>
									        </div>
									      </div>
									      
									      </div>
									      
									    	<%
									    	}else {
									    	%>
									    
				    					<div class="col-md-12 col-xs-12" id="medicalTextDivID" style="margin:25px;" >
											<textarea cols="2" rows="10" id="medicalCertiText" name="medicalCertiText" style="width:100%;"><%=medicalCertiText %></textarea>
										</div>
					     				
									    <%	
					     					}
					     				}
									      %>   
									   
					    </div>
					    
					    <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12 col-xs-12" align="center">
			                   <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                   <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button class="btn btn-success" type="submit" onclick="displayVal();">Save & Print</button>
	                         	
	                          <%
					        	}else{
			                 %>
			                 <button class="btn btn-success" type="submit" onclick=" displayVal();">Save & Print</button>
			                 <%
					        	}
			                 %>
			               </div>
			            </div>
			
			     </form>
			    </div>
			    <!-- Ends -->
			    
			    
			    <!-- Referral letter div -->
			    <div class="tab-pane fade" id="refLetter" >
			    
			    		<form class="app-cam" id="" name="" action="AddReferralLetter"  method="POST" style="width:95%;margin:0px;" >
			    		
			    		<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName" value="<%=fullName %>">
					    <input type="hidden" id="patientName" value="<s:property value="firstName"/> <s:property value="lastName"/>">
				      
				        <input type="hidden" class="form-control" value="<%=currentDate %>"  name="date" id="date12">
				      
				        <input type="hidden" id="doctrNameRef" value="<%=fullName %>">
				        
					    <input type="hidden" id="patientNameRef" value="<s:property value="firstName"/> <s:property value="lastName"/>">
					    <textarea style="display: none" name="referralLetter" id="referralLetter" cols="" rows="5"></textarea>
					    <textarea style="display: none" name="referralLetterForPDF" id="referralLetterForPDF" cols="" rows="5"></textarea>
			
						<%
							if(referralLetterCheck == "no"){
						%>
						<div class="row" style="margin:15px;">
				 			<s:radio list="#{'1':'Pre-defined','0':'Free-text'}" name="radioRefVal" id="radioRefValID" onclick="displayRefLetter(this.value);"></s:radio>
			    		</div>
			    		<% 
			    			}
			    		%>
			    		
						<%
							if(referralLetterText == null || referralLetterText == ""){
						%>
						
						<div class="col-md-12 col-xs-12" id="refLetterTextDivID" style="margin:25px;display: none;" >
							<s:textarea cols="2" rows="10" id="refLetterText" name="refLetterText" style="width:100%;"></s:textarea>
						</div>
  
						<div class="col-md-12 col-xs-12" id= "refLetterDivID" style="margin:25px;display: none;">
			
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						
						          <div class="col-md-1" style="margin-left: -15px;"> <label>Date:</label> </div>
						          <div class="col-md-3" style="margin-left: -15px;"> <label><%=currentDate %></label></div>
						        
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;margin-top: 10px;">Dear Dr. </label><s:select list="doctorList" name="doctName" id="doctName" style="width:20%; " class="form-control"></s:select>, 
						
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;">I am referring <s:property value="firstName"/> <s:property value="lastName"/> to you for the following conditions:</label><br><br>
						
						          <textarea rows="4" name="conditionText" id="conditionText" class="form-control" required></textarea><br><br>
						
						          <label for="Name" style="font-size:14px;">Please advise Mr/Ms <s:property value="firstName"/> <s:property value="lastName"/>  on further treatment. </label> <br><br>
						
						          <label for="Name" style="font-size:14px;">Sincerely, </label>
						          <br>
						          <label for="Name" style="font-size:14px;">Dr. <%=fullName %> </label>
						
						      </div>
						
						    </div>
						</div>
								<% 	
				     				} else {
				     					
								    	if(referralLetterText.contains("$")){
								    		String[] strval = referralLetterText.split("\\$");
								    		
								    %>
									<div class="col-md-12 col-xs-12" id= "refLetterDivID" style="margin:25px;">
			
								    <div class="row" >
						      
						      	<div class="col-md-12 col-xs-12" style="margin:15px;">
						
						          <div class="col-md-1" style="margin-left: -15px;"> <label>Date:</label> </div>
						          <div class="col-md-3" style="margin-left: -15px;"> <label><%=currentDate %></label></div>
						        
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;margin-top: 10px;">Dear Dr. </label><s:select list="doctorList" name="doctName"  id="doctName" style="width:20%; " class="form-control"></s:select>, 
						
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;">I am referring <s:property value="firstName"/> <s:property value="lastName"/> to you for the following conditions:</label><br><br>
						
						          <textarea rows="4" name="conditionText" id="conditionText" class="form-control" required><%=strval[3] %></textarea><br><br>
						
						          <label for="Name" style="font-size:14px;">Please advise Mr/Ms <s:property value="firstName"/> <s:property value="lastName"/>  on further treatment. </label> <br><br>
						
						          <label for="Name" style="font-size:14px;">Sincerely, </label>
						          <br>
						          <label for="Name" style="font-size:14px;">Dr. <%=strval[4] %> </label>
						
						      </div>
						
						    </div>
						    </div>
						     <%
							}else{
			             %>
			             
						<div class="col-md-12 col-xs-12" id="refLetterTextDivID" style="margin:25px;" >
							<textarea cols="2" rows="10" id="refLetterText" name="refLetterText" style="width:100%;"><%=referralLetterText %></textarea>
						</div>
  
						<%
							}
				     	}
			            %>
			             
			            <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12 col-xs-12" align="center">
			                   <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                   <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button class="btn btn-success" type="submit"onclick="displayRefLet();" >Save & Print</button>
	                          <%
					        	}else{
			                 %>
			                 <button class="btn btn-success" type="submit"  onclick="displayRefLet();">Save & Print</button>
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
    
    
    <script type="text/javascript">
    	
    function addDays(dateObj, numDays) {
		dateObj.setDate(dateObj.getDate() + numDays);
		return dateObj;
	}
    
    document.addEventListener('DOMContentLoaded', function() {

    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
    	$('#lastMenstrualPeriodID').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(selected) {
        	  
        	  var dateVal = new Date(selected);
        	  console.log("date: "+dateVal);
           
              var nextWeek = dateVal.setDate(dateVal.getDate() + 280);
              
              var finalDate = moment(nextWeek).format('DD-MM-YYYY');
              console.log("date is: "+finalDate);
              
              $("#estimatedDueDateID").val(finalDate);
          
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
   
   <script>
   
    /*For drugName autocompletor  */	
	
	  $(function() {
	        var tradeNameSelect = $("#tradeName").autocomplete({
	            source : function(request, response) {
	                    $.ajax({
	                            url : "SearchTradeNameList",
	                            type : "POST",
	                            data : {
	                            	searchPatientName : request.term
	                            }, 
	                            dataType : "json",
	                            success : function(jsonResponse) {
	                                    response(jsonResponse);
	                            },
	                            error: function(errorMsg){
	                            	alert("ERROR: "+errorMsg);
	                            }
	                    });
	                },
	                select: function(){
	                	setTimeout(function(){
	                		//alert('hiiii'+$("#tradeName").val()+"...");
	                		CategoryValue($("#tradeName").val());
	                	},500);
	                }
	            });
	    });
	  
    </script>
    
  </body>
</html>