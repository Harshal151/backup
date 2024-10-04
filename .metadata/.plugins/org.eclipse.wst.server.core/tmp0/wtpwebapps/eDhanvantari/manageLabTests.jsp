<%@page import="com.edhanvantari.daoImpl.RegistrationDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.RegistrationDAOinf"%>
<%@page import="com.edhanvantari.util.ConfigurationUtil"%>
<%@page import="com.edhanvantari.util.ConfigXMLUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.edhanvantari.util.ActivityStatus"%>
<%@page import="com.edhanvantari.form.ClinicForm"%>
<%@page import="com.edhanvantari.daoImpl.LoginDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.LoginDAOInf"%>
<%@page import="com.edhanvantari.form.LoginForm"%>
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

<title>Manage Lab Tests | E-Dhanvantari</title>

<!-- Favicon -->
<link rel="shortcut icon" href="images/Icon.png">

<!-- Bootstrap -->
<link href="vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="vendors/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="build/css/custom.min.css" rel="stylesheet">

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

<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />

<script type="text/javascript">
      function windowOpen(){
        document.location="dashboard.jsp";
      }
    </script>

<style type="text/css">
ul.actionMessage {
	list-style: none;
	font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
}

ul.errorMessage {
	list-style: none;
	font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
}

.btn-success.active {
	color: white;
	background: #FABA0C;
	border: 1px solid #FABA0C;
}

.btn-success.active:hover, .btn-success.active:focus, .btn-success.active:active,
	.open .dropdown-toggle.btn-success.active {
	color: white;
	background: #d6a317;
	border: 1px solid #d6a317;
}

.loadingImage {
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
	</script>

<style type="text/css">
.dataTables_filter {
	width: 100%;
}
</style>


<!-- Delete concession popup message -->

<script type="text/javascript">
		function deleteConcession(url){
			if(confirm("Are you sure you want to delete this value?")){
				document.location = url;
			}
		}
	</script>

<!-- Ends -->

<script type="text/javascript">

		function deleteClinicType(url){
			if (confirm("Are you sure you want to delete?")) {
				document.location = url;
			}
			
		}
	
	</script>


<script type="text/javascript">
		function disableClinic(){
			if(confirm("Are you sure you want to disable clinic?")){
				document.getElementById("myForm").action = "DisableClinic";
				document.forms["myForm"].submit();
			}
		}
	
		function enableClinic(){
			if(confirm("Are you sure you want to enable clinic?")){
				document.getElementById("myForm").action = "EnableClinic";
				document.forms["myForm"].submit();
			}
		}
	</script>


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

LoginDAOInf daoInf = new LoginDAOImpl();

ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
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

<%
String componentMsg = (String) request.getAttribute("componentMsg");
if (componentMsg == null || componentMsg == "") {
	componentMsg = "dummy";
}

String componentEdit = (String) request.getAttribute("componentEdit");
if (componentEdit == null || componentEdit == "") {
	componentEdit = "add";
}
%>

<!-- Add IPD complaint values -->

<script type="text/javascript">
    	var counterNew = 1;
    
    	function addDefaultValueRow(value) {
			
			if(value == ""){
				
				alert("Please add default value first.");
			}else{
				
				addDefaultValueRow1(value);
			}
		}	
		
		function addDefaultValueRow1(value) {
			
			var trID = "COTRID"+counterNew;
        	var tdID = "COTDID"+counterNew;
        	
			var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
			"<td>"+value+"<input type='hidden' name='defaultValueArr' value='"+value+"'></td>"+
			"<td><img src='images/delete_icon_1.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\"" + trID +"\",\""+tdID+"\");'/></td>"+
			"</tr>";

			$(trTag).insertBefore($('#DefaultValueTRID'));
			
			//setting fields to its default value
			$("#defaultValueID").val("");
		}
		
		//Remove tr function
		function removeTR(trID, tdID){
        	
        	if(confirm("Are you sure you want to remove this row?")){

            	$("#"+trID+"").remove();
            }
        }
		
		
		var counterNew1 = 1;
		
    	function editDefaultValueRow(value) {
			
			if(value == ""){
				
				alert("Please add default value first.");
			}else{
				
				editDefaultValueRow1(value);
			}
		}	
		
		function editDefaultValueRow1(value) {
			
			var trID = "COTRID"+counterNew1;
        	var tdID = "COTDID"+counterNew1;
        	
			var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
			"<td>"+value+"<input type='hidden' name='defaultValueArr' value='"+value+"'></td>"+
			"<td><img src='images/delete_icon_1.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\"" + trID +"\",\""+tdID+"\");'/></td>"+
			"</tr>";

			$(trTag).insertBefore($('#EditDefaultValueTRID'));
			
			//setting fields to its default value
			$("#defaultValueeditID").val("");
		}
		
		//Remove tr function
		function removeTR(trID, tdID){
        	
        	if(confirm("Are you sure you want to remove this row?")){

            	$("#"+trID+"").remove();
            }
        }
		
		function confirmDelete(DefaultValueID, trID){
			
			var ValueID = $("#"+DefaultValueID).val();
			
			if (confirm("Are you sure you want to delete this row?")) {
				
				deleteDefaultValue(ValueID, trID);
			}
		}
		
	</script>

<!-- Ends -->

<!-- Delete DefaultValue -->
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteDefaultValue(ValueID, trID) {
	
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
						document.getElementById("successMSG").innerHTML = "Default Value deleted successfully.";
						$("#"+trID).remove();
					}
				}
			};
			xmlhttp.open("GET", "DeleteDefaultValue?defaultValueID="+ ValueID , true);
			xmlhttp.send();
		}
		
		
		function reportDownload(){
			
			document.location="GenerateLabDataReport";
		}
	</script>
<%-- 	<script type="text/javascript" charset="UTF-8">
	  $('#templateList').select2({
		    placeholder: "Select Multiple Templates",
		    width: "100%",
		    multiple:true,
		    allowClear: true,
		  })
	</script> --%>
<!-- Ends -->

</head>

<body class="nav-md">

	<!-- To show loading icon while page is loading -->
	<img src="images/Preloader_2.gif" alt="Loading Icon"
		class="loadingImage" id="loader">

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
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <%
 if (daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()) {
 %> <img src="images/user.png" alt="Profile Pic"><%=form.getFullName()%>

									<%
									} else {
									%> <img src="<%=daoInf.retrieveProfilePic(form.getUserID())%>"
									alt="Profile Pic"><%=form.getFullName()%> <%
 }
 %> <span class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="RenderEditProfile"
										onclick="showLoadingImg();"> Profile</a></li>
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
										<h3>CONFIGURE LAB TESTS</h3>
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

										<font
											style="color: green; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="successMSG"></center> </font> <font id=""
											style="color: red; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="errorMSG"></center></font> <font id=""
											style="color: red; font-size: 16px; margin-left: 40px; text-align: center; margin: 0px;"><center
												id="exceptionMSG"></center></font>
									</div>

									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<form id="SearchLabTestsID" action="SearchLabTests"
												method="POST">
												<div class="col-md-4 col-sm-4 col-xs-4">
													<input type="text" class="form-control" required="required"
														placeholder="Search lab tests by name"
														name="searchLabTestsName">
												</div>
												<div class="col-md-2 col-sm-3 col-xs-4">
													<button style="width: 100%;" type="submit"
														class="btn btn-success active" onclick="showLoadingImg();">Search
														Lab Tests</button>
												</div>
												<div class="col-md-2 col-sm-3 col-xs-4">
													<a href="ViewAllLabTest">
														<button type="button" class="btn btn-primary "
															onclick="showLoadingImg();">View All Lab Tests</button>
													</a>
												</div>
												<!-- <div class="col-md-2 col-sm-3 col-xs-4">
				              		<button type="button" class="btn btn-primary" onclick="reportDownload()">Export Report</button>
				              	</div> -->
											</form>
										</div>
									</div>

									<div class="ln_solid"></div>

									<!-- Search div -->

									<div class="col-md-12 col-sm-12 col-xs-12"
										style="overflow-x: scroll;">

										<%
										if (componentMsg.equals("available")) {
										%>

										<!-- <div class="row" style="margin-top:30px;"> -->
										<table id="datatable-responsive"
											class="table table-striped table-bordered dt-responsive nowrap"
											cellspacing="0">
											<thead>
												<tr>
													<th style="width: 10% !important;">Sr.No</th>
													<!--   <th style="width: 15% !important;">Panel</th> -->
													<th style="width: 15% !important;">Test</th>
													<th style="width: 15% !important;">Action</th>
												</tr>
											</thead>
											<tbody>
												<s:iterator value="configureLabTestList" var="UserForm">
													<tr>

														<td style="width: 10%; !important"><s:property
																value="srNo" /></td>

														<%-- 	<td style="width: 15%; !important"><s:property value="panel" /></td> --%>

														<td style="width: 15%; !important"><s:property
																value="test" /></td>


														<td align="center" style="width: 15%; !important"><s:url
																id="approveURL" action="RenderEditLabTest">
																<s:param name="labTestID" value="%{labTestID}" />
																<s:param name="searchLabTestsName"
																	value="%{searchLabTestsName}" />
															</s:url> <s:a href="%{approveURL}">
																<img src="images/user_1.png" style="height: 24px;"
																	onmouseover="this.src='images/user_2.png'"
																	onmouseout="this.src='images/user_1.png'"
																	alt="Edit Lab Test" title="Edit Lab Test" />
															</s:a>&nbsp;&nbsp;&nbsp;&nbsp; <s:url id="disableUserURL"
																action="DeleteLabTest">
																<s:param name="labTestID" value="%{labTestID}" />
																<s:param name="searchLabTestsName"
																	value="%{searchLabTestsName}" />
															</s:url> <s:a
																href="javascript:deleteConcession('%{disableUserURL}')">
																<img src="images/delete_icon_1.png"
																	style="height: 20px;"
																	onmouseover="this.src='images/delete_icon_2.png'"
																	onmouseout="this.src='images/delete_icon_1.png'"
																	alt="Delete Lab Test" title="Delete Lab Test" />
															</s:a></td>
													</tr>
												</s:iterator>
											</tbody>
										</table>
										<!--  </div> -->

										<%
										}
										%>

									</div>
									<!-- ENds -->
									<div class="col-md-2 col-sm-2 col-xs-12"></div>
									<!-- Add and Update Div -->
									<div class="col-md-8 col-sm-8 col-xs-12">

										<%
										if (componentEdit.equals("add")) {
										%>

										<form class="form-horizontal form-label-left" novalidate
											action="ConfigureLabTests" method="POST" id="addLabTestID"
											style="margin-top: 20px;">


											<%--  <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Instruction">Panel <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="panel" required="required" placeholder="Panel" class="form-control">
	                        </div>
	                      </div> --%>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Test <span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="test" required="required"
														placeholder="Test" class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">SubGroup </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="subgroup" placeholder="SubGroup"
														class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Normal Values(Men) <span
													class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="normalValues" required="required"
														placeholder="Normal values(Men)" class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Normal Values(Women) <span
													class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="womenNormalRange"
														required="required" placeholder="Normal values(Women)"
														class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Normal Values(Children) <span
													class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="childNormalRange"
														required="required" placeholder="Normal values(Children)"
														class="form-control">
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">IsExcludeNormalValues </label>
												<div class="col-md-6 col-sm-6 col-xs-12"
													style="padding-top: 6px;">
													<s:radio list="#{'1':'Yes','0':'No'}"
														id="isExcludeNormalValuesID" value="0"
														name="isExcludeNormalValues"></s:radio>
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Show Normal Range">Show Normal Range
													Description </label>
												<div class="col-md-6 col-sm-6 col-xs-12"
													style="padding-top: 6px;">
													<s:radio list="#{'1':'Yes','0':'No'}"
														id="showNormalRangeDescID" value="0"
														name="showNormalRangeDesc"></s:radio>
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Is Outsourced Test? </label>
												<div class="col-md-6 col-sm-6 col-xs-12"
													style="padding-top: 6px;">
													<s:radio list="#{'1':'Yes','0':'No'}" id="isOutsourced"
														value="0" name="isOutsourced"></s:radio>
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Type? <span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12"
													style="padding-top: 6px;">
													<s:radio
														list="#{'PathologyLab':'Lab Test','XRay':'XRay','USG':'USG','CTScan':'CT Scan','MRI':'MRI'}"
														required="required" id="testType" name="testType"
														onclick="getSelectedTestType()"></s:radio>
												</div>

											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Select Templates</label>
												<div class="col-md-6 col-sm-6 col-xs-12"
													style="padding-top: 6px;">
													<input type="hidden" name="testTypeTemplates"
														id="hiddenTypeTemplateID" class="form-control"
														value="<s:property value='testTypeTemplates'/>"> <select
														name="template" id="templateList" class="form-control"
														onChange="getSelectedTemplates()"></select>
													<%
													HashMap<String, String> selectedTemplateList = (HashMap<String, String>) request.getAttribute("selectedTemplateList");

													if (selectedTemplateList != null && !selectedTemplateList.isEmpty()) {
														for (String testValue : selectedTemplateList.keySet()) {
													%>	
													<label class="checkbox"> <input type="checkbox"
														class="checkboxClass" value="<%=testValue%>"
														id="<%=selectedTemplateList.get(testValue)%>ID"
														checked="checked"
														onclick="removeTestToSelect(this);"
														disabled> <%=selectedTemplateList.get(testValue)%>
													</label>
													<%
													}
													} else {
													// Optionally, you can display a message or leave this else block empty.
													// For example:
													// out.println("No templates selected.");
													}
													%>
												</div>

											</div>

											<%-- <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Instruction">Rate <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="number" name="rate" required="required" placeholder="Rate" class="form-control">
	                        </div>
	                      </div> --%>


											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Remarks </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<s:textarea rows="4" cols="2" name="remarks"
														placeholder="Remarks" class="form-control"></s:textarea>
												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-4 col-sm-4 col-xs-12"
													for="Instruction">Normal-range Description </label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<s:textarea rows="4" cols="2" name="normalRangeDesc"
														placeholder="Normal-range Description"
														class="form-control"></s:textarea>
												</div>
											</div>

											<table
												class="table table-striped table-bordered dt-responsive nowrap"
												cellspacing="0" style="margin-top: 25px;">
												<thead>
													<tr>
														<th>Default Value</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<tr id="DefaultValueTRID">
														<td><input type="text" name="defaultValue"
															id="defaultValueID" placeholder="Default Value"
															class="form-control"></td>
														<td><a
															onclick="addDefaultValueRow(defaultValueID.value);">
																<img src="images/add_icon_1.png"
																onmouseover="this.src='images/add_icon_2.png'"
																onmouseout="this.src='images/add_icon_1.png'"
																alt="Add Default Value" title="Add Complaint"
																style="margin-top: 5px; height: 24px;" />
														</a></td>
													</tr>
												</tbody>
											</table>

											<div class="ln_solid"></div>
											<div class="form-group">
												<div class="col-md-12" align="center">
													<button type="reset" class="btn btn-primary"
														onclick="window.location='RenderConfigureLabTest'"
														onclick="showLoadingImg();">Cancel</button>
													<button id="send" style="width: 15%;" type="submit"
														class="btn btn-success " onclick="showLoadingImg();">Add</button>
												</div>
											</div>
										</form>

										<%
										} else {
										%>

										<form class="form-horizontal form-label-left" novalidate
											action="EditConfigureLabTest" method="POST"
											style="margin-top: 20px;">

											<s:iterator value="labTestList" var="labTestForm">

												<input type="hidden" name="labTestID"
													value="<s:property value="labTestID" />">
												<input type="hidden" name="searchLabTestsName"
													value="<s:property value="searchLabTestsName" />">


												<%-- <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Instruction">Panel <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="panel" required="required" value="<s:property value="panel" />" placeholder="Panel" class="form-control">
	                        </div>
	                      </div> --%>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Test <span class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" name="test" required="required"
															value="<s:property value="test" />" placeholder="Test"
															class="form-control">
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">SubGroup </label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" name="subgroup"
															value="<s:property value="subgroup" />"
															placeholder="SubGroup" class="form-control">
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Normal Values(Men) <span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" name="normalValues" required="required"
															value="<s:property value="normalValues" />"
															placeholder="Normal values(Men)" class="form-control">
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Normal Values(Women) <span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" name="womenNormalRange"
															required="required"
															value="<s:property value="womenNormalRange" />"
															placeholder="Normal values(Women)" class="form-control">
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Normal Values(Children) <span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" name="childNormalRange"
															required="required"
															value="<s:property value="childNormalRange" />"
															placeholder="Normal values(Children)"
															class="form-control">
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">IsExcludeNormalValues </label>
													<div class="col-md-6 col-sm-6 col-xs-12"
														style="padding-top: 6px;">
														<s:radio list="#{'1':'Yes','0':'No'}"
															id="isExcludeNormalValuesID" name="isExcludeNormalValues"></s:radio>
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Show Normal Range">Show Normal Range
														Description </label>
													<div class="col-md-6 col-sm-6 col-xs-12"
														style="padding-top: 6px;">
														<s:radio list="#{'1':'Yes','0':'No'}"
															id="showNormalRangeDescID" name="showNormalRangeDesc"></s:radio>
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Is Outsourced Test? </label>
													<div class="col-md-6 col-sm-6 col-xs-12"
														style="padding-top: 6px;">
														<s:radio list="#{'1':'Yes','0':'No'}" id="isOutsourced"
															name="isOutsourced"></s:radio>
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Type? <span class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12"
														style="padding-top: 6px;">
														<s:radio
															list="#{'PathologyLab':'Lab Test','XRay':'XRay','USG':'USG','CTScan':'CT Scan','MRI':'MRI'}"
															required="required" id="testType" name="testType" onclick="getSelectedTestType()"></s:radio>
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Select Templates</label>
													<div class="col-md-6 col-sm-6 col-xs-12"
														style="padding-top: 6px;">
														<input type="hidden" name="testTypeTemplates"
															id="hiddenTypeTemplateID" class="form-control"
															value="<s:property value='testTypeTemplates'/>">
														<input type="hidden" name="testTypeTemplatesRemoved"
														id="hiddenTypeTemplateIDRemoved" class="form-control"
														value="<s:property value='testTypeTemplatesRemoved'/>">
														<select name="template" id="templateList"
															class="form-control" onChange="getSelectedTemplates()"></select>
														<%
														HashMap<String, String> selectedTemplateList = (HashMap<String, String>) request.getAttribute("selectedTemplateList");

														if (selectedTemplateList != null && !selectedTemplateList.isEmpty()) {
															for (String testValue : selectedTemplateList.keySet()) {
														%>
														<label class="checkbox"> <input type="checkbox"
															class="checkboxClass" value="<%=testValue%>"
															id="<%=selectedTemplateList.get(testValue)%>"
															checked="checked"
															onclick="removeTestToSelect(this);"
															> <%=selectedTemplateList.get(testValue)%>
														</label>
														<%
														}
														} else {
														// Optionally, you can display a message or leave this else block empty.
														// For example:
														// out.println("No templates selected.");
														}
														%>
													</div>

												</div>

												<%-- <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Instruction">Rate <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="number" name="rate" required="required" value="<s:property value="rate" />" placeholder="Rate" class="form-control">
	                        </div>
	                      </div> --%>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Remarks </label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<s:textarea rows="4" cols="2" name="remarks"
															placeholder="Remarks" class="form-control"></s:textarea>
													</div>
												</div>

												<div class="item form-group">
													<label class="control-label col-md-4 col-sm-4 col-xs-12"
														for="Instruction">Normal-range Description </label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<s:textarea rows="4" cols="2" name="normalRangeDesc"
															placeholder="Normal-range Description"
															class="form-control"></s:textarea>
													</div>
												</div>

												<table
													class="table table-striped table-bordered dt-responsive nowrap"
													cellspacing="0" style="margin-top: 25px;">
													<thead>
														<tr>
															<th>Default Value</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody>

														<s:iterator value="DefaultValueList">

															<input type="hidden"
																id="editDefaultValueID<s:property value="defaultValueID" />"
																name="editdefaultValueID"
																value="<s:property value="defaultValueID" />">
															<tr
																id="editDefaultValueTR<s:property value="defaultValueID" />">
																<td><input type="text" name="editdefaultValue"
																	id="defaultValueID"
																	value="<s:property value="defaultValue" />"
																	placeholder="Default Value" class="form-control"></td>
																<td><a
																	href="javascript:confirmDelete('editDefaultValueID<s:property value="defaultValueID" />', 'editDefaultValueTR<s:property value="defaultValueID" />');">
																		<img src="images/delete_icon_1.png"
																		onmouseover="this.src='images/delete_icon_2.png'"
																		onmouseout="this.src='images/delete_icon_1.png'"
																		alt="Remove Row" title="Remove Row"
																		style="height: 24px;" />
																</a></td>
															</tr>
														</s:iterator>

														<tr id="EditDefaultValueTRID">
															<td><input type="text" name="defaultValue"
																id="defaultValueeditID" placeholder="Default Value"
																class="form-control"></td>
															<td><a
																onclick="editDefaultValueRow(defaultValueeditID.value);">
																	<img src="images/add_icon_1.png"
																	onmouseover="this.src='images/add_icon_2.png'"
																	onmouseout="this.src='images/add_icon_1.png'"
																	alt="Add Default Value" title="Add Complaint"
																	style="margin-top: 5px; height: 24px;" />
															</a></td>
														</tr>
													</tbody>
												</table>

												<div class="ln_solid"></div>
												<div class="form-group">
													<div class="col-md-12" align="center">
														<button type="reset" class="btn btn-primary"
															onclick="window.location='ViewAllLabTest'"
															onclick="showLoadingImg();">Cancel</button>
														<button id="send" style="width: 15%;" type="submit"
															class="btn btn-success " onclick="showLoadingImg();">Update</button>
													</div>
												</div>

											</s:iterator>

										</form>

										<%
										}
										%>

									</div>
									<!-- ENds -->
									<div class="col-md-2 col-sm-2 col-xs-12"></div>

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

	<!-- Custom Theme Scripts -->
	<script src="build/js/custom.min.js"></script>

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

	<!-- Select2 -->
	<script
		src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

	<script>
    // In your Javascript (external .js resource or <script> tag)
    $(document).ready(function() {
        $('#templateList').select2({
		    placeholder: "Select Multiple Templates",
		    width: "100%",
		    multiple:true,
		    allowClear: true,
		  });
    });
    </script>

	<!-- The JavaScript section -->
	<script type="text/javascript">
	let selectedTestTypeValue = null;
	function getSelectedTestType() {
	    // Check if any radio button is already selected
	    const selectedRadioButton = document.querySelector('input[name="testType"]:checked');

	    if (selectedRadioButton) {
	    	if(selectedTestTypeValue === null){
	    		selectedTestTypeValue = selectedRadioButton.value;
	    	}
	        const selectedValue = selectedRadioButton.value;
	        
	        // If the selected value differs from the previously selected one, uncheck checkboxes and run the function
	        if (selectedTestTypeValue !== null && selectedTestTypeValue !== selectedValue) {
	            const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	            checkboxes.forEach(checkbox => {
	                checkbox.checked = false;
	                removeTestToSelect(checkbox); // Run removeTestToSelect for each unchecked checkbox
	            });

	            // Update the stored test type value
	            selectedTestTypeValue = selectedValue;
	        }

	        // Parse the templatesByType object from the backend
	        const templatesByType = JSON.parse(`<%=request.getAttribute("getTemplatesByType") != null ? request.getAttribute("getTemplatesByType") : "{}"%>`);

	        const dataArray = templatesByType.data || [];

	        console.log("Selected Test Type:", selectedValue);
	        console.log("Data Array:", dataArray);

	        const templateSelect = document.getElementById('templateList');
	        templateSelect.innerHTML = ''; // Clear the dropdown

	        const testTypeMapping = {
	            "XRay": "X-RAY",
	            "USG": "USG",
	            "CTScan": "CT SCAN",
	            "MRI": "MRI",
	            "PathologyLab": "PathologyLab"
	        };

	        const jsonKey = testTypeMapping[selectedValue];

	        // Find the matching template for the selected test type
	        const matchingTemplate = dataArray.find(item => item[jsonKey]);

	        if (matchingTemplate) {
	            console.log("Populating templates for:", selectedValue);
	            populateDropdown(templateSelect, matchingTemplate[jsonKey]);
	        } else {
	            console.log("No templates found for the selected test type.");
	        }
	    }
	}

	function populateDropdown(selectElement, options) {
	    console.log("Populating dropdown with options:", options);
	    // Add the options to the dropdown
	    for (const [id, name] of Object.entries(options)) {
	        const option = document.createElement('option');
	        option.value = id;
	        option.text = name;
	        selectElement.appendChild(option);
	    }
	}

	// Immediately check and populate if a radio button is already selected
	window.onload = getSelectedTestType;

	// Also ensure the function runs when the user changes the selection
	document.querySelectorAll('input[name="testType"]').forEach(radio => {
	    radio.addEventListener('change', getSelectedTestType);
	});

</script>

	<script type="text/javascript">
function getSelectedTemplates() {
    const selectElement = document.getElementById('templateList');
    const selectedOptions = Array.from(selectElement.selectedOptions);

    const idToTextMap = {};

    selectedOptions.forEach(option => {
        const id = option.value;
        const text = option.text; 
        idToTextMap[id] = text;
    });

    console.log("ID to Text Map:", idToTextMap);

    const dataToSend = JSON.stringify(idToTextMap);
    
    console.log("dataToSend: ", dataToSend);

    $("#hiddenTypeTemplateID").val(dataToSend);
}

var uncheckedValuesMap = uncheckedValuesMap || {};

function removeTestToSelect(checkbox) {
    var templateID = checkbox.value;
    var templateName = checkbox.id;

    if (!checkbox.checked) {
        uncheckedValuesMap[templateID] = templateName;

        var labelElement = checkbox.closest('label');
        if (labelElement) {
            labelElement.remove();
        }
    } else {
        delete uncheckedValuesMap[templateID];
    }

    var hiddenInput = document.getElementById('hiddenTypeTemplateIDRemoved');
    hiddenInput.value = JSON.stringify(uncheckedValuesMap);

    console.log("Current unchecked values map:", uncheckedValuesMap);
}

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

	<!-- Datatables -->
	<script>
      $(document).ready(function() {

        $('#datatable-responsive').DataTable();     

      });
    </script>
	<!-- /Datatables -->

</body>
</html>