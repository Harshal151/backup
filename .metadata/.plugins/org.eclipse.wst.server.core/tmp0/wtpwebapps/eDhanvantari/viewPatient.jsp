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

	int userID = form.getUserID();
	int finalCount =0;

	LoginDAOInf daoInf = new LoginDAOImpl();
	ClinicDAOInf clinicDaoInf = new ClinicDAOImpl();

	ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
	
	String searchCriteria = (String) request.getAttribute("searchCriteria");
	System.out.println("searchCriteria:"+searchCriteria);
	
	int allowed_visit = clinicDaoInf.retrieveAllowedVisitByPracticeID(form.getPracticeID());
	System.out.println("allowed visit::"+allowed_visit);
	
	String startDate = clinicDaoInf.retrieveStartDateByPracticeID(form.getPracticeID());
	System.out.println("start date::"+startDate);
	
	String endDate = clinicDaoInf.retrieveEndDateByPracticeID(form.getPracticeID());
	System.out.println("end date::"+endDate);
	
	int visitCount = clinicDaoInf.retrieveVisitCountBetweenDates(startDate,endDate);
	System.out.println("visitCount::"+visitCount);
	
	if(allowed_visit > 0){
		System.out.println("in allowed iff");
		finalCount = allowed_visit-visitCount;
	}else{
		System.out.println("in allowed else");
		finalCount = 0;
	}
	
	Date date = new Date();
	// Create SimpleDateFormat object 
    SimpleDateFormat  sdfo  = new SimpleDateFormat("yyyy-MM-dd"); 
    String currentDate = sdfo.format(date);
    
    // Get the two dates to be compared 
    Date d1 = sdfo.parse(endDate); 
	Date d2 = sdfo.parse(currentDate);
    // Print the dates 
    System.out.println("curr date : " + sdfo.format(d2)); 
    System.out.println("end date : " + sdfo.format(d1)); 
    
    int expiryDate = 1;
    if (d1.compareTo(d2) < 0) { 
    	expiryDate = 0;
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



<%
	if (form.getUserType().equals("surveyor")) {
%>

<title>View Participant List | Anuyash</title>

<%
	} else {
%>

<title>View Patient List | E-Dhanvantari</title>

<%
	}
%>

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

<!-- Custom Theme Style -->
<link href="build/css/custom.min.css" rel="stylesheet">

<script type="text/javascript">
	function windowOpen() {
		document.location = "Welcome.jsp";
	}
</script>

<!-- Disable User start -->

<%
	if (form.getUserType().equals("surveyor")) {
%>

<script type="text/javascript">
	function disableUser(url) {
		$("#disableActionID").val(url);
		$("#disableModalID").modal("show");
	}

	function renderDisablePatient(url, status) {

		if (confirm("Are you sure you want to delete this participant?")) {
			document.location = url + "&activityStatus=" + status;
		}

	}
</script>

<%
	} else {
%>

<script type="text/javascript">
	function disableUser(url) {
		$("#disableActionID").val(url);
		$("#disableModalID").modal("show");
	}

	function renderDisablePatient(url, status) {
		console.log("url..." + url + "&activityStatus=" + status);
		if (confirm("Are you sure you want to delete this patient?")) {
			document.location = url + "&activityStatus=" + status;
		}

	}
</script>

<%
	}
%>

<!-- Ends -->

<!-- Delete user alert function -->

<script type="text/javascript">
	function rejectRequest(url) {
		if (confirm("Are you sure you want to delete user?")) {
			document.location = url;
		}
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

<%
	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

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
		var interval =
<%=maxSessionTimeout%>
	;
		setInterval(function() {

			//Setting blank values to PIN fields
			$('#lockPIN').val("");

			//opening security credentials modal
			$("#lockModal").modal("show");

		}, interval);
	});
</script>

<!-- Ends -->

<%
	String patientListEnable = (String) request.getAttribute("patientListEnable");
%>


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

<!-- Show modals function -->

<script type="text/javascript">
	function showLockModal() {
		$(document).ready(function() {
			//Setting blank values to PIN fields
			$('#lockPIN').val("");

			//opening security credentials modal
			$("#lockModal").modal("show");
		});
	}

	function showLogoutModal() {
		$(document).ready(function() {
			$("#logoutModal").modal("show");
		});
	}

	function showSecurityModal() {
		$(document).ready(function() {
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

				for (var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PINCheck;
					var errMsg = array.Release[i].ErrMsg;

					if (check == "check") {

						document.getElementById("lockImgID").innerHTML = "";

						unlockModal();

					} else {

						document.getElementById("lockImgID").innerHTML = "<img src='images/wrong.png' alt='Incorrect PIN' title='Incorrect PIN' style='height:24px;margin-top: 5px;'>";

					}
				}
			}
		};
		xmlhttp.open("GET", "VerifyUnlockPIN?lockPIN=" + lockPIN, true);
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

				for (var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if (check == "check") {

						document.getElementById("oldPassID").innerHTML = "<img src='images/correct.png' alt='Correct Old Password' title='Correct Old Password' style='height:24px;margin-top: 5px;'>";

						//Enabling New password and confirm password input fields
						document.getElementById("newPassID").disabled = false;

					} else {

						document.getElementById("oldPassID").innerHTML = "<img src='images/wrong.png' alt='Incorrect Old Password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";

					}
				}
			}
		};
		xmlhttp.open("GET", "VerifyOldPass?oldPass=" + oldPass, true);
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

				for (var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].newPassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if (check == "true") {

						document.getElementById("newPassCheckID").innerHTML = "<img src='images/correct.png' alt='Valid new password' title='New password is valid' style='height:24px;margin-top: 5px;'>";
						document.getElementById("confirmNewPassID").disabled = false;

					} else {

						document.getElementById("newPassCheckID").innerHTML = "<img src='images/wrong.png' alt='Invalid new password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";

					}
				}
			}
		};
		xmlhttp.open("GET", "NewPassCheck?newPass=" + newPass, true);
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

				for (var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].PassCheck;
					var errMsg = array.Release[i].ErrMsg;

					if (check == "check") {

						hideSecCredModal();

					} else {

						document.getElementById("oldPassID").innerHTML = "<img src='images/wrong.png' alt='Incorrect Old Password' title='Incorrect Old Password' style='height:24px;margin-top: 5px;'>";

					}
				}
			}
		};
		xmlhttp.open("GET", "UpdateSecurityCredentials?newPass=" + newPass
				+ "&newPIN=" + newPIN, true);
		xmlhttp.send();
	}
</script>

<!-- Ends -->


<!-- Confirm new pass and Confirm new PIN check -->

<script type="text/javascript">
	function checkConfirmNewPass(confirmNewPassID, newPassID) {

		if (confirmNewPassID == newPassID) {

			document.getElementById("confirmPassID").innerHTML = "<img src='images/correct.png' alt='Confirm New Password matched' title='Confirm New Password matched' style='height:24px;margin-top: 5px;'>";

			$('#securityBtnID').prop('disabled', false);

		} else {

			$('#securityBtnID').prop('disabled', true);

			document.getElementById("confirmPassID").innerHTML = "<img src='images/wrong.png' alt='Confirm Password didn't match' title='Confirm New Password must be same as New Password' style='height:24px;margin-top: 5px;'>";

		}
	}

	function checkConfirmNewPIN(confirmNewPINID, newPINID) {

		if (confirmNewPINID == newPINID) {

			document.getElementById("confirmPINID").innerHTML = "<img src='images/correct.png' alt='Confirm New PIN matched' title='Confirm New PIN matched' style='height:24px;margin-top: 5px;'>";

			$('#securityBtnID').prop('disabled', false);

		} else {

			$('#securityBtnID').prop('disabled', true);

			document.getElementById("confirmPINID").innerHTML = "<img src='images/wrong.png' alt='Confirm PIN didn't match' title='Confirm New PIN must be same as New PIN' style='height:24px;margin-top: 5px;'>";

		}
	}
</script>

<!-- Ends -->

<!-- To show Visit date serach boxes -->
    <script type="text/javascript">
    	function show1(val){
    		if(val == "VisitDate"){
    			$("#dateDivID11").show(1000);
    			$("#dateDivID12").show(1000);
    			$("#searchDivID1").hide(1000);
    		}else{
    			$("#dateDivID11").hide(1000);
    			$("#dateDivID12").hide(1000);
    			$("#searchDivID1").show(1000);
    		}
    	}
    </script>
<!-- Ends -->

<!-- Hide Lock Modal and Security Credentials modal -->

<script type="text/javascript">
	function unlockModal() {
		$(document).ready(function() {

			$('#lockButtonID').click();
		});
	}

	function hideSecCredModal() {
		$(document).ready(function() {

			$('#secCredCancelID').click();
		});
	}
</script>

<!-- Ends -->


<%
	HashMap<Integer, String> map = daoInf.retrieveVisitTypesList(form.getClinicID());
%>



<!-- Visit type  modal -->

<script type="text/javascript">
	<%-- function openVisitTypeModal(patientID) {
	
		var finalCount = <%=finalCount%> 
		console.log("final count::"+finalCount);
		
		if(finalCount == 0){
			alert("Your monthly visit quota is full..!!");
			$("#visitTypeModal").modal('hide');
		}else{ 
			$("#pID").val(patientID);

			$("#visitTypeModal").modal('show');
		}
		
	} --%>
   	
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function openVisitTypeModal(patientID){
		
		var finalCount = <%=finalCount%>
		var expiryDate = <%=expiryDate%>
	
		console.log("final count::"+finalCount);
		
		if(finalCount == 0 ){
			alert("Your monthly visit quota is full..!!");
			$("#visitTypeModal").modal('hide');
		}else if(expiryDate == 0){
			alert("Your Plan has expired. Please renew your plan..!!");
			$("#visitTypeModal").modal('hide');
		}else{ 
			
		
				$("#pID").val(patientID);
				
				xmlhttp.onreadystatechange = function() {
					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		
						var array = JSON.parse(xmlhttp.responseText);
						
						var msg = "";
		
						for ( var i = 0; i < array.Release.length; i++) {
							
							msg = array.Release[i].message;
		
						}
						
						console.log("msg..."+msg);
						
						if(msg == "NoVisitFound" || msg == "DischargeVisitFound"){
							
							$("#visitTypeModal").modal('show');
							
						}else if(msg == "OPDVisit"){
							retrieveNonIPDVisitType();
							
						}else{
							console.log("inside");
							//retrieveIPDVisitTypeExceptNew();
							$("#visitTypeModal").modal('show');
							
						}
		
					}
				};
		}
		
		xmlhttp.open("GET", "VerifyIPDVisitType?patientID="+patientID, true);
		xmlhttp.send();
		
	}
	
	function retrieveIPDVisitTypeExceptNew(){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var selectTag = "<select name='visitTypeID' id='visitTypeID' class='form-control' required='required'><option value='000'>Select Visit Type*</option>";

				for ( var i = 0; i < array.Release.length; i++) {
					
					msg = array.Release[i].message;
					console.log("visit type after addm::"+array.Release[i].visitType);
					selectTag += "<option value='"+array.Release[i].visitTypeID+"'>"+array.Release[i].visitType+"</option>";
				}
				
				selectTag += "</select>";
				
				$("#visitTypeDivID").html(selectTag);
				$("#visitTypeModal").modal('show');
				

			}
		};
		xmlhttp.open("GET", "RetrieveIPDVisitTypeExceptNew", true);
		xmlhttp.send();
		
	}
	
	function retrieveNonIPDVisitType(){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var selectTag = "<select name='visitTypeID' id='visitTypeID' class='form-control' required='required'><option value='000'>Select Visit Type*</option>";

				for ( var i = 0; i < array.Release.length; i++) {
					
					msg = array.Release[i].message;
					console.log("visit type after addm::"+array.Release[i].visitType);
					selectTag += "<option value='"+array.Release[i].visitTypeID+"'>"+array.Release[i].visitType+"</option>";
				}
				
				selectTag += "</select>";
				
				$("#visitTypeDivID").html(selectTag);
				$("#visitTypeModal").modal('show');
				

			}
		};
		xmlhttp.open("GET", "RetrieveNonIPDVisitType", true);
		xmlhttp.send();
		
	}
	
	function submitForm() {
		
		var visitType = $("#visitTypeID").val();

		if (visitType == "000") {
			alert("Please select visit type.");
			return false;
		} else {
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
	}
</script>

<!-- Ends -->

<style type="text/css">
.input-sm {
	width: 73%;
}
</style>


</head>

<body class="nav-md">

   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

	<!-- disable status modal -->

	<div id="disableModalID" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"
						style="text-align: center">SELECT STATUS</h4>
				</div>

				<input type="hidden" name="" id="disableActionID">

				<div class="modal-body">

					<div class="row" align="center">

						<div class="col-md-12 col-sm-12 col-xs-12">

							<select name="" id="statusID" class="form-control"
								required="required">
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
						<button type="submit" class="btn btn-primary"
							onclick="renderDisablePatient(disableActionID.value,statusID.value);">Submit</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->


	<!-- Visit type modal -->

	<div id="visitTypeModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"
						style="text-align: center">SELECT VISIT TYPE</h4>
				</div>
				<form action="ChangeVisitTypeID" method="POST" onsubmit="return submitForm();">

					<input type="hidden" name="patientID" id="pID">

					<div class="modal-body">

						<div class="row" align="center">

							<div class="col-md-12 col-sm-12 col-xs-12" id="visitTypeDivID">

								<select name="visitTypeID" id="visitTypeID" class="form-control"
									required="required">
									<option value="000">Select Visit Type*</option>

									<%
										for (Integer key : map.keySet()) {
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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary"
								>Submit</button>
						</center>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Ends -->


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
							<li class=""><a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" id ="profPicID" aria-expanded="false"> 
							
							<% if (daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()) {  %> 
							
							 <img src="images/user.png" alt="Profile Pic"><%=form.getFullName()%>
							<% } else {
							
								S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveProfilePic(form.getUserID()))).getObjectContent();
					 						
					 			IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" +daoInf.retrieveProfilePic(form.getUserID()))));  
					 		 %>
					         
					         <img src="<%= "images/" +daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
							
							<% } %>
							
							 <span class=" fa fa-angle-down"></span>
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
										<%
											if (form.getUserType().equals("surveyor")) {
										%>
										<h3>PARTICIPANT LIST</h3>
										<%
											} else {
										%>
										<h3>PATIENT LIST</h3>
										<%
											}
										%>
									</div>
								</div>

								<div class="ln_solid" style="margin-top: 0px;"></div>

								<div class="row">
									<form action="SearchViewPatient" method="POST" onsubmit="return showLoadingImg();" >
									
										<%
											if (form.getUserType().equals("surveyor")) {
										%>
										
										<%-- <div class="col-md-2 col-sm-3 col-xs-6">
											<s:select list="#{'PatientName':'Participant Name','MobileNo':'Mobile No.','RegNo':'Registration No.','VisitDate':'Visit Date'}"
												class="form-control" name="searchCriteria" onchange="show1(this.value);"></s:select>
										</div> --%>
										
										<div class="col-md-3 col-sm-3 col-xs-6"  id="searchDivID1">
											<input type="text" name="searchName" class="form-control"
												placeholder="Enter participant name / mobile no./registration no.">
										</div>
										
										<div class="col-md-2 col-sm-3 col-xs-3 " id="dateDivID11" style="display: none;">
						
											<input type="text" class="form-control"  name ="startDate" id="datepicker1" placeholder="From Date" title="Enter only From Date if you want to search for a single date rather than a range.">
									
										</div>
										
										<div class="col-md-2 col-sm-3 col-xs-3 " id="dateDivID12" style="display: none;">
											<input type="text" class="form-control" id="datepicker2" name ="endDate" placeholder="End Date" >
										</div>
						
										<div class="col-md-2 col-sm-3 col-xs-6 hidden-xs">
											<button type="submit" class="btn btn-success active"
												style="width: 100%;" >Search Participant</button>
										</div>
										<div class="col-md-2 col-sm-3 col-xs-6 hidden-xs">
											<a href="RenderViewPatientList">
												<button type="button" class="btn btn-primary"
													style="width: 100%;" onclick="showLoadingImg();">View All Participants</button>
											</a>
										</div>
										<div
											class="col-md-2 col-sm-3 col-xs-6 hidden-sm hidden-md hidden-lg">
											<button type="submit" class="btn btn-success active"
												style="width: 100%; margin-top: 15px;">Search
												Participants</button>
										</div>
										<div
											class="col-md-2 col-sm-3 col-xs-6 hidden-sm hidden-md hidden-lg">
											<a href="RenderViewPatientList">
												<button type="button" class="btn btn-primary"
													style="width: 100%; margin-top: 15px;" onclick="showLoadingImg();">View All
													Participants</button>
											</a>
										</div>
										<%
											} else {
										%>
										<%-- <div class="col-md-2 col-sm-3 col-xs-6">
											<s:select list="#{'PatientName':'Patient Name','MobileNo':'Mobile No.','RegNo':'Registration No.','VisitDate':'Visit Date'}"
												class="form-control" name="searchCriteria" onchange="show1(this.value);"></s:select>
										</div> --%>
										
										<div class="col-md-3 col-sm-3 col-xs-6"  id="searchDivID1">
											<input type="text" name="searchName" class="form-control" placeholder="Enter patient name / mobile no." >
										</div>
										
										<div class="col-md-2 col-sm-3 col-xs-3 " id="dateDivID11" style="display: none;">
						
											<input type="text" class="form-control"  name ="fromDate" id="datepicker1" placeholder="From Date" title="Enter only From Date if you want to search for a single date rather than a range.">
									
										</div>
										
										<div class="col-md-2 col-sm-3 col-xs-3 " id="dateDivID12" style="display: none;">
											<input type="text" class="form-control" id="datepicker2" name ="toDate" placeholder="End Date" >
										</div>
										
										<div class="col-md-2 col-sm-3 col-xs-6 hidden-xs">
											<button type="submit" class="btn btn-success active"
												style="width: 100%;">Search Patient</button>
										</div>
										<!-- <div class="col-md-2 col-sm-3 col-xs-6 hidden-xs">
											<a href="RenderViewPatientList">
												<button type="button" class="btn btn-primary"
													style="width: 100%;" onclick="showLoadingImg();">View All Patients</button>
											</a>
										</div> -->
										<div
											class="col-md-2 col-sm-3 col-xs-6 hidden-sm hidden-md hidden-lg">
											<button type="submit" class="btn btn-success active"
												style="width: 100%; margin-top: 15px;">Search
												Patient</button>
										</div>
										<!-- <div class="col-md-2 col-sm-3 col-xs-6 hidden-sm hidden-md hidden-lg">
											<a href="RenderViewPatientList">
												<button type="button" class="btn btn-primary"
													style="width: 100%; margin-top: 15px;" onclick="showLoadingImg();">View All
													Patients</button>
											</a>
										</div> -->
										<%
											}
										%>
										
									</form>
								</div>

								<div class="ln_solid"></div>

								<div class="x_content">


									<%
										if (patientListEnable == null || patientListEnable == "") {
									%>

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

									<%
										} else if (patientListEnable.equals("patientSearchListEnable")) {
									%>

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

									<table id="datatable-responsive"
										class="table table-striped table-bordered dt-responsive nowrap"
										cellspacing="0" width="100%">
										<thead>
											<tr>
												<th>First Name</th>
												<th>Middle Name</th>
												<th>Last Name</th>
												<th>Registration No.</th>
												<th>Age</th>
												<th>Gender</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<s:iterator value="searchPatientList" var="UserForm">
												<tr>
													<td style="width: 15%"><s:property value="firstName" /></td>

													<td style="width: 15%"><s:property value="middleName" /></td>

													<td style="width: 15%"><s:property value="lastName" /></td>

													<td style="width: 20%"><s:property value="registrationNo" /></td>

													<td style="width: 10%"><s:property value="age" /></td>

													<td style="width: 10%"><s:property value="gender" /></td>

													<td align="center">
														<%
															if (form.getUserType().equals("administrator") || form.getUserType().equals("receptionist")
																			|| form.getUserType().equals("surveyor")) {
														%> <s:url id="approveURL"
															action="RenderEditPatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL}">
															<img src="images/patient_1.png" style="height: 24px;"
																onmouseover="this.src='images/patient_2.png'"
																onmouseout="this.src='images/patient_1.png'"
																alt="Edit Patient" title="Edit Patient" />
														</s:a>&nbsp; <s:url id="approveURL11"
															action="RenderDisablePatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
															<img src="images/delete_icon_1.png" style="height: 20px;"
																onmouseover="this.src='images/delete_icon_2.png'"
																onmouseout="this.src='images/delete_icon_1.png'"
																alt="Remove Patient" title="Remove Patient" />
														</s:a> <%
 											} else {
										 %> <%
 											if (form.getUserType().equals("compounder") || form.getUserType().equals("dietician")|| form.getUserType().equals("billDesk")) {
 										%> <s:url id="approveURL1" action="RenderViewVisit">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL1}">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="View visit" title="View visit" />
														</s:a>&nbsp; <%
 											} else {
 										%> <s:url id="approveURL" action="RenderEditPatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL}">
															<img src="images/patient_1.png" style="height: 24px;"
																onmouseover="this.src='images/patient_2.png'"
																onmouseout="this.src='images/patient_1.png'"
																alt="Edit Patient" title="Edit Patient" />
														</s:a>&nbsp; <s:a
															href="javascript:openVisitTypeModal(%{patientID});">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="New visit" title="New visit" />
														</s:a> &nbsp; <s:url id="approveURL1" action="RenderViewVisit">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL1}">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="View visit" title="View visit" />
														</s:a>&nbsp; <s:url id="approveURL11"
															action="RenderDisablePatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
															<img src="images/delete_icon_1.png" style="height: 20px;"
																onmouseover="this.src='images/delete_icon_2.png'"
																onmouseout="this.src='images/delete_icon_1.png'"
																alt="Remove Patient" title="Remove Patient" />
														</s:a> <%
 													}
 												}
										 %>
													</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>

									<%
										} else if (patientListEnable.equals("patientListEnable")) {
									%>

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

									<table id="datatable-responsive"
										class="table table-striped table-bordered dt-responsive nowrap"
										cellspacing="0" width="100%">
										<thead>
											<tr>
												<th>First Name</th>
												<th>Middle Name</th>
												<th>Last Name</th>
												<th>Registration No.</th>
												<th>Age</th>
												<th>Gender</th>
												<th style="text-align: center;">Action</th>
											</tr>
										</thead>
										<tbody>
											<s:iterator value="patientList" var="UserForm">
												<tr>
													<td style="width: 15%"><s:property value="firstName" /></td>

													<td style="width: 15%"><s:property value="middleName" /></td>

													<td style="width: 15%"><s:property value="lastName" /></td>

													<td style="width: 20%"><s:property value="registrationNo" /></td>

													<td style="width: 10%"><s:property value="age" /></td>

													<td style="width: 10%"><s:property value="gender" /></td>

													<td align="center">
														<%
															if (form.getUserType().equals("administrator") || form.getUserType().equals("receptionist")
																			|| form.getUserType().equals("surveyor")) {
														%> <s:url id="approveURL"
															action="RenderEditPatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL}">
															<img src="images/patient_1.png" style="height: 24px;"
																onmouseover="this.src='images/patient_2.png'"
																onmouseout="this.src='images/patient_1.png'"
																alt="Edit Patient" title="Edit Patient" />
														</s:a>&nbsp; <s:url id="approveURL11"
															action="RenderDisablePatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
															<img src="images/delete_icon_1.png" style="height: 20px;"
																onmouseover="this.src='images/delete_icon_2.png'"
																onmouseout="this.src='images/delete_icon_1.png'"
																alt="Remove Patient" title="Remove Patient" />
														</s:a> <%
												} else {
													 %> <%
													 	if (form.getUserType().equals("compounder") || form.getUserType().equals("dietician")
													 						|| form.getUserType().equals("billDesk") || form.getUserType().equals("accountant")) {
													 %> <s:url id="approveURL1" action="RenderViewVisit">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL1}">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="View visit" title="View visit" />
														</s:a>&nbsp; <%
 												} else {
														 %> <s:url id="approveURL" action="RenderEditPatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL}">
															<img src="images/patient_1.png" style="height: 24px;"
																onmouseover="this.src='images/patient_2.png'"
																onmouseout="this.src='images/patient_1.png'"
																alt="Edit Patient" title="Edit Patient" />
														</s:a>&nbsp; <s:a
															href="javascript:openVisitTypeModal(%{patientID});">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="New visit" title="New visit" />
														</s:a> &nbsp; <s:url id="approveURL1" action="RenderViewVisit">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="%{approveURL1}">
															<img src="images/visit_view_1.png" style="height: 24px;"
																onmouseover="this.src='images/visit_view_2.png'"
																onmouseout="this.src='images/visit_view_1.png'"
																alt="View visit" title="View visit" />
														</s:a>&nbsp; <s:url id="approveURL11"
															action="RenderDisablePatient">
															<s:param name="patientID" value="%{patientID}" />
														</s:url> <s:a href="javascript:disableUser('%{approveURL11}')">
															<img src="images/delete_icon_1.png" style="height: 20px;"
																onmouseover="this.src='images/delete_icon_2.png'"
																onmouseout="this.src='images/delete_icon_1.png'"
																alt="Remove Patient" title="Remove Patient" />
														</s:a> <%
 	}
 			}
 %>
													</td>

												</tr>
											</s:iterator>
										</tbody>
									</table>

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
	<script src="vendors/jszip/dist/jszip.min.js"></script>
	<script src="vendors/pdfmake/build/pdfmake.min.js"></script>
	<script src="vendors/pdfmake/build/vfs_fonts.js"></script>

	<!-- Custom Theme Scripts -->
	<script src="build/js/custom.min.js"></script>

	<!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>

	<!-- Datatables -->
	<script>
		$(document).ready(function() {

			$('#datatable-responsive').DataTable();

		});
	</script>
	
	<script>
   		 $( document ).ready(function() {
   			console.log("inside ready function");
   			  var searchVal = '<%=searchCriteria%>';
   			  console.log("searchCriteria:"+searchVal);
   			if(searchVal === "VisitDate"){
   				$("#dateDivID11").show();
    			$("#dateDivID12").show();
   				$("#searchDivID1").hide();
   			}else{
   				$("#dateDivID11").hide();
    			$("#dateDivID12").hide();
   				$("#searchDivID1").show();
   			} 
   		});
    </script>
    
    
	<script>
	
	document.addEventListener('DOMContentLoaded', function() {
    	
		$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
		
    	$('#datepicker1').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
    	
        $('#datepicker2').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
	});

        </script>
</body>
</html>