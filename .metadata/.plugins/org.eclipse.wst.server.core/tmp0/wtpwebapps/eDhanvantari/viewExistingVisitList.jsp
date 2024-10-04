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

    <title>Existing Visit List | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <!-- Datatables -->
    <link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <script type="text/javascript">
      function windowOpen(){
        document.location="Welcome.jsp";
      }
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableUser(url){
			if(confirm("Disabled users cannot access application. Are you sure you want to disable this user?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
				document.location = url;
			}
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
	</script>
	
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
			
			
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();	
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
	
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
    
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
    
    <!-- Patient name -->
    
    <%
    
    	String patientName = (String) request.getAttribute("patientName");
   		
    %>
    
    <!--  -->
    
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
    
    <script type="text/javascript">
	
    function verifyBillDuePending(visitID, visitTypeID, mdDoctorID,careType){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = "";

				for ( var i = 0; i < array.Release.length; i++) {
					check = array.Release[i].check;
				}
				
				if(check == "NoResult"){
					//alert("No results added for this visit. Please add result first.");
					window.location.assign("DownloadLabTestReport?visitID="+visitID+"&mdDoctorID="+mdDoctorID);
				}else if(check == "NoDues"){
					//print the report
					window.location.assign("DownloadLabTestReport?visitID="+visitID+"&mdDoctorID="+mdDoctorID);
				}else if(check == "Dues"){
					//Function to fetch the bill details
					retrieveBillDetails(visitID, visitTypeID,careType);
				}else{
					alert(check);
				}
			}
		};
		xmlhttp.open("POST", "VerifyBillDuePending?visitID=" + visitID + "&visitTypeID=" + visitTypeID , true);
		xmlhttp.send();
	}
	
	function retrieveBillDetails(visitID, visitTypeID,careType){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var netAmt = 0;
				var totalAmt = 0;
				var advPayment = 0;
				var totalDiscount = 0;
				var balPayment = 0;
				
				var fName = "";
				var lName = "";
				var visitID = 0;
				var receiptID = 0;
				var billingType = "";
				var paymentType = "";
				var patientID = 0;
				var receiptNo = "";
				var receiptDate = "";
				var cashPaid = 0;
				var cashToReturn = 0;
				var cheqNo = "";
				var cheqIssuedBy = "";
				var cheqBankName = "";
				var cheqBankBranch = "";
				var cheqDate = "";
				var cheqAmt = 0;
				var cardAmount = 0;
				var cardNo = "";
				var cardMobile = "";
				var otherType = "";
				var otherAmt = 0;
				
				var TRTag = "";
				
				var srNo= 1;

				for ( var i = 0; i < array.Release.length; i++) {
					netAmt = array.Release[i].netAmount;
					totalAmt = array.Release[i].totalAmt;
					advPayment = array.Release[i].advPayment;
					totalDiscount = array.Release[i].totalDiscount;
					balPayment = array.Release[i].balPayment;
					fName = array.Release[i].fistName;
					lName = array.Release[i].lastName;
					visitID = array.Release[i].visitID;
					receiptID = array.Release[i].receiptID;
					billingType = array.Release[i].billingType;
					paymentType = array.Release[i].paymentType;
					patientID = array.Release[i].patientID;
					receiptNo = array.Release[i].receiptNo;
					receiptDate = array.Release[i].receiptDate;
					cashPaid = array.Release[i].cashPaid;
					cashToReturn = array.Release[i].cashToReturn;
					cheqNo = array.Release[i].cheqNo;
					cheqIssuedBy = array.Release[i].cheqIssueBy;
					cheqBankName = array.Release[i].cheqBankName;
					cheqBankBranch = array.Release[i].cheqBankBranch;
					cheqDate = array.Release[i].cheqDate;
					cheqAmt = array.Release[i].cheqAmt;
					cardAmount = array.Release[i].cardAmt;
					cardNo = array.Release[i].cardNo;
					cardMobile = array.Release[i].cardMobileNo;
					otherType = array.Release[i].otherType;
					otherAmt = array.Release[i].otherAmt;
					
					var testName = array.Release[i].testName;
					var rate = array.Release[i].rate;
					
					TRTag += "<tr style='font-size: 14px;'>"
							+"<td style='text-align:center;'>"+srNo+"</td>"
							+"<td>"+testName+"</td>"
							+"<td>"+rate+"</td><input type='hidden' name='test' value='"+testName+"'><input type='hidden' name='billingProdRate' value='"+rate+"'></tr>";
							
					srNo++;
				}
				
				console.log("...."+TRTag);
				
				$("#testTBodyID").html(TRTag);
				
				$("#totalAmountID").val(totalAmt);
				$("#netAmountID").val(netAmt);
				$("#totalVatID").val(totalDiscount);
				
				var advTotalPayment = parseInt(parseInt(advPayment) + parseInt(balPayment));
				
				$("#advPaymentID").val(advPayment);
				$("#advPaymentHiddenID").val(advTotalPayment);
				$("#amtPaidID").val(balPayment);
				$("#balPaymentID1").val(balPayment);
				
				$("#cashPaidID").val(cashPaid);
				$("#cashToReturnID").val(cashToReturn);
				$("#chequeIssuedByID").val(cheqIssuedBy);
				$("#chequeNoID").val(cheqNo);
				$("#chequeBankNameID").val(cheqBankName);
				$("#chequeBankBranchID").val(cheqBankBranch);
				$("#single_cal311").val(cheqDate);
				$("#chequeAmtID").val(cheqAmt);
				$("#cardAmountID").val(cardAmount);
				$("#cardMobileNoID").val(cardNo);
				$("#cMobileNoID").val(cardMobile);
				$("#otherTypeID").val(otherType);
				$("#otherTypeAmountID").val(otherAmt);
				
				if(paymentType == null || paymentType == ""){
					console.log("null payment type found.");
				}else{
					
					if(paymentType.includes("Cash")){
						$("#cashPaidHiddenID").val(advTotalPayment);
						$("#chequeAmtHiddenID").val(0);
						$("#cardAmountHiddenID").val(0);
						$("#otherAmountHiddenID").val(0);
					}
					if(paymentType.includes("Cheque")){
						$("#cashPaidHiddenID").val(0);
						$("#chequeAmtHiddenID").val(advTotalPayment);
						$("#cardAmountHiddenID").val(0);
						$("#otherAmountHiddenID").val(0);
					}
					if(paymentType.includes("Credit/Debit Card")){
						$("#cashPaidHiddenID").val(0);
						$("#chequeAmtHiddenID").val(0);
						$("#cardAmountHiddenID").val(advTotalPayment);
						$("#otherAmountHiddenID").val(0);					
					}
					if(paymentType.includes("Other")){
						$("#cashPaidHiddenID").val(0);
						$("#chequeAmtHiddenID").val(0);
						$("#cardAmountHiddenID").val(0);
						$("#otherAmountHiddenID").val(advTotalPayment);
					}
					
					var paymentMap = {
						'Cash': 'paymentTypeCashID=cashDetailID',
						'Cheque': 'paymentTypeChequeID=chequeDetailID',
						'Credit/Debit Card': 'paymentTypeCardID=cardDetailID',
						'Other': 'paymentTypeOtherID=otherDetailID'
					};
					
					if(paymentType.includes(",")){
						
						var payArr = paymentType.split(",");
						
						for(var i = 0; i < payArr.length; i++){
							
							var pay = paymentMap[payArr[i]];
							
							var arr = pay.split("=");
							
							var checkID = arr[0];
							var checkDivID = arr[1];
							
							$("#"+checkID).attr("checked", true);
				    		
				    		$("#"+checkDivID).show();
						}
						
					}else{
						var pay = paymentMap[paymentType];
						
						var payArr = pay.split("=");
						
						var checkID = payArr[0];
						var checkDivID = payArr[1];
						
						$("#"+checkID).attr("checked", true);
			    		
			    		$("#"+checkDivID).show();
					}
				}
				
				$("#visitTypeID").val(visitTypeID);
				$("#patientID").val(patientID);
				$("#visitID").val(visitID);
				$("#firstName").val(fName);
				$("#lastName").val(lName);
				$("#billingTypeID").val(billingType);
				$("#receiptID").val(receiptID);
				$("#receiptDateID").val(receiptDate);
				$("#receiptNoID").val(receiptNo);
				$("#receiptDatefontID").text(receiptDate);
				$("#receiptNofontID").text(receiptNo);
				
				$("#billModalID").modal("show");
				
			}
		};
		xmlhttp.open("POST", "RetrieveLabBillDetails?visitID="
				+ visitID + "&visitTypeID=" + visitTypeID + "&careType="+careType , true);
		xmlhttp.send();
		
	}
    
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
			//$("#advPaymentID").val(netAmount);
		}
    	
		function disableSubmitBtn(btnID){
			
			$("#"+btnID).attr('disabled',true);
			return true;
		}
		
		function openPcpNDTModal(visitID)
		{
			$("#visitIDPCPNDT").val(visitID);
			$("#pcpNDTModal").modal("show");
		}
		
		function setDetailsPCPNDT1()
		{
			
			var patientID = $("#patientID").val();
			var visitID = $("#visitIDPCPNDT").val();
			var pcpNDTResult = $("#pcpNDTResult").val();
			var txtNormalVal = $('input[name="txtNormal"]:checked').val();
			
			
			var checkBoxVal = [];
		        $('.chkValue:checkbox:checked').each(function(i){
		        	checkBoxVal[i] = $(this).val();
		        });
		    var chkVal = checkBoxVal.toString();
		    var pcpDetails= null;
		    
			xmlhttp.onreadystatechange = function() {
				
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					for ( var i = 0; i < array.Release.length; i++) {
						
						pcpDetails = array.Release[i].patientDetails;
						
					}	
					sendPcpNDTdataLocal(pcpDetails);
					$("#pcpNDTModal").modal("hide");
					$("#upload"+visitID).hide();
					$( "<a href='javascript:changePCPNDTStatus("+visitID+","+patientID+")' id='changeStatus"+visitID+"'><i class='fa fa-check' aria-hidden='true' style='font-size: 18px; color : #0a6a16' title='Update Status' alt='Update Status'></i></a>" ).insertAfter( "#upload"+visitID );
				}
			};
			
			xmlhttp.open("POST", "getDetailsPCPNDT?visitID=" + visitID + "&patientID=" + patientID + "&txtNormal=" + txtNormalVal, true);
			xmlhttp.send(); 
		}
		
		
		function sendPcpNDTdataLocal(jsonDATA)
		{
			
			var weekOfPregnancy = jsonDATA.weekOfPregnancy;
			var numberOfDaughters = jsonDATA.numberOfDaughters;
			var numberOfSons = jsonDATA.numberOfSons;
			var estimatedDueDate = jsonDATA.estimatedDueDate;
			var radiologyRegNo = jsonDATA.radiologyRegNo;
			var reffClinicName = jsonDATA.reffClinicName;
			var ageOfSons = encodeURI(jsonDATA.ageOfSons);
			var pMiddleName = jsonDATA.pMiddleName;
			var ageOfDaughters = encodeURI(jsonDATA.ageOfDaughters);
			var lastMenstrualPeriod = jsonDATA.lastMenstrualPeriod;
			var pDOB = jsonDATA.pDOB;
			var pFirstName = jsonDATA.pFirstName;
			var pGender = jsonDATA.pGender;
			var ppincode = jsonDATA.ppincode;
			var radiologyName = jsonDATA.radiologyName;
			var pAddress = jsonDATA.pAddress;
			var pLastName = jsonDATA.pLastName;
			var pReltv = jsonDATA.pReltv;
			var visitDate = jsonDATA.visitDate;
			var reffDoctorName = jsonDATA.reffDoctorName;
			var pMobile = jsonDATA.pMobile;
			var pAge = jsonDATA.pAge;
			var reffClinicAddress = jsonDATA.reffClinicAddress;
			
			var pcpNDTResult = $("#pcpNDTResult").val();
			var txtNoramlVal = $('input[name="txtNormal"]:checked').val();
			
			var checkBoxVal = [];
		        $('.chkValue:checkbox:checked').each(function(i){
		        	checkBoxVal[i] = $(this).val();
		        });
		    var chkVal = checkBoxVal.toString()
			
		    var pcpNDTLocalPath = jsonDATA.pcpNDTLocalPath;
		   
		    xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				}
			};
			xmlhttp.open("POST", pcpNDTLocalPath+"?weekOfPregnancy="+weekOfPregnancy
					+"&numberOfDaughters="+numberOfDaughters
					+"&numberOfSons="+numberOfSons
					+"&estimatedDueDate="+estimatedDueDate
					+"&radiologyRegNo="+radiologyRegNo
					+"&reffClinicName="+reffClinicName
					+"&ageOfSons="+ageOfSons
					+"&pMiddleName="+pMiddleName
					+"&ageOfDaughters="+ageOfDaughters
					+"&lastMenstrualPeriod="+lastMenstrualPeriod
					+"&pDOB="+pDOB
					+"&pFirstName="+pFirstName
					+"&pGender="+pGender
					+"&ppincode="+ppincode
					+"&radiologyName="+radiologyName
					+"&pAddress="+pAddress
					+"&pLastName="+pLastName
					+"&pReltv="+pReltv
					+"&visitDate="+visitDate
					+"&reffDoctorName="+reffDoctorName
					+"&pMobile="+pMobile
					+"&pAge="+pAge
					+"&pcpNDTResult="+pcpNDTResult
					+"&txtNoramlVal="+txtNoramlVal
					+"&chkVal="+chkVal
					+"&reffClinicAddress="+reffClinicAddress, true);
			xmlhttp.send(); 
		}
		
		function changePCPNDTStatus(visitID, patientID)
		{
			
			xmlhttp.onreadystatechange = function() {
				
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					for ( var i = 0; i < array.Release.length; i++) {
						var message = array.Release[i].MSG;
						$("#changeStatus"+visitID).remove();
					}	
				}
			};
			
			xmlhttp.open("POST", "updatePCPNDTStatus?visitID=" + visitID + "&patientID=" + patientID, true);
			xmlhttp.send(); 
		}
		
    </script>
    
  </head>

  <body class="nav-md">
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
  <!-- bill modal -->
  
  <div id="billModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="myModalLabel">LAB TEST BILL</h4>
      </div>
      
        <div class="modal-body">
          	
          	<form class="form-horizontal form-label-left" novalidate id="genPhyBillID" name="genPhyBillID" onsubmit="return disableSubmitBtn('addBillBtnID');" action="EditDueLabBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						          <%-- <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5> --%>
						  </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
					      <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					      <input type="hidden"  name="visitTypeID" id="visitTypeID" value="<s:property value="visitTypeID"/>">
                           
                           
                           <input type="hidden" name="billingType" id="billingTypeID" value="<s:property value="billingType"/>">
                           <input type="hidden" name="receiptID" id="receiptID" value="<s:property value="receiptID"/>">

						<div class="row" style="margin-top: 15px;padding-left: 15px;">
							
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt Date:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;" id="receiptDatefontID"><s:property value="receiptDate"/></font>
								<input type="hidden" name="receiptDate" id="receiptDateID" value="<s:property value="receiptDate"/>">
							</div>
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;" id="receiptNofontID"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" id="receiptNoID" value="<s:property value="receiptNo"/>">
							</div>
							
						</div>

	                      
	                   <div class="row" style="margin-top:10px;padding: 15px;">
							      
							      	<table  class="table table-striped table-bordered dt-responsive nowrap">
							      		
							      		<thead>
								
								            <tr style="font-size: 14px;">
								            	<th style="text-align: center">Sr.No.</th>
								            	<th>Test</th>
								            	<th>Rate</th>
								            </tr>
								        </thead>
								        
								        <tbody id="testTBodyID">
								        
								        </tbody>
								                               
								        <tbody>
								        		<%-- <s:iterator value="prescriptionList">
								        			<tr style="font-size: 14px;">
								        				<td style="text-align: center"><s:property value="srNo"/></td>
								        				
								        				<td><s:property value="CBCProfileTest"/>
								        				<input type="hidden" name="test" value="<s:property value="CBCProfileTest"/>">
								        				<input type="hidden" name="billingProdRate" value="<s:property value="rate"/>">
								        				</td>
								        				
								        				<td><s:property value="rate"/></td>
								        			</tr>
								        		</s:iterator> --%>
								        	
								        	<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Total Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="totalAmount"/>" class="form-control" name="totalAmount" id="totalAmountID">
								        		</td>
								        		
											</tr>
							
											
											<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Concession, if any</font>	
								        		</td>
								        		
								        		<td> 
								        			<input type="number" class="form-control" readonly="readonly" value="<s:property value="totalDiscount"/>" onkeyup="changeNetAmtByDiscount(this.value,totalAmountID.value);" name="totalDiscount" id="totalVatID">
								        		</td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Net Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="netAmount"/>" class="form-control" name="netAmount" id="netAmountID">
								        		</td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Advance Payment</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" readonly="readonly" value="<s:property value="advPayment"/>" name="" id="advPaymentID" onkeyup="changeBalancePayment(advPaymentID.value, netAmountID.value);">
								        		</td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Balance Payment</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" readonly="readonly" value="<s:property value="balPayment"/>" name="" id="balPaymentID1">
								        			<input type="hidden" name="balPayment" id="balPaymentID" value="0">
								        		</td>
								        		
											</tr> 
											
											<tr>
								        			
								        		<td colspan="2" align="right">
								        			<font style="font-size: 14px; font-weight: bold;color: red">Amount to be paid</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" readonly="readonly" style="color: red"  name="" id="amtPaidID">
								        			<input type="hidden" name="advPayment" id="advPaymentHiddenID" value="">
								        		</td>
								        		
											</tr>
								        	
								        </tbody>
							      		
							      	</table>
							      
							      </div>
							      
							      <div class="row" style="padding: 0 15px 0 15px;">
			                    	<div class="col-md-2 col-sm-3 col-md-4" style="padding-top:5px;">
			                    		<font style="font-size: 16px;">Payment Type*</font>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="hideChequeDetailsDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeCashID" value="Cash" class="form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Cash</font>
			                    		</div>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeChequeID" value="Cheque" class="form-control">
			                    		</div>
			                    		<div class="col-md-4 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Cheque</font>
			                    		</div>
			                    	</div>
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayCardDiv(this);" style="height: 25px;box-shadow: none;" id="paymentTypeCardID" value="Credit/Debit Card" class="form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Credit/Debit Card</font>
			                    		</div>
			                    	</div>
			                    	
			                    <!-- 	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivCreditNote(this);" style="height: 25px;box-shadow: none;" id="paymentTypeCreditNoteID" value="Credit Note" class="form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Credit Note</font>
			                    		</div>
			                    	</div> -->
			                    	
			                    	<div class="col-md-2 col-sm-3 col-md-4">
			                    		<div class="col-md-4 col-sm-4 col-xs-6">
			                    			<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivOther(this);" style="height: 25px;box-shadow: none;" id="paymentTypeOtherID" value="Other" class="form-control">
			                    		</div>
			                    		<div class="col-md-8 col-sm-4 col-xs-6" style="padding-top:5px;">
			                    			<font style="font-size: 15px;">Other</font>
			                    		</div>
			                    	</div>
			                    </div>
			                    
			                    <%-- <div id="cashDetailID" style="padding: 15px;display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-2">
				                    		<font style="font-size: 16px;">Cash Paid</font>
				                    	</div>
				                    	<div class="col-md-2">
					                        <input type="text" class="form-control" name="" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, amtPaidID.value);" value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
					                        <input type="hidden" name="cashPaid" id="cashPaidHiddenID">
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
				                    		<input  class="form-control" name="" id="chequeAmtID" value="<s:property value="chequeAmt"/>"  placeholder="Amount" type="number">
				                    		<input type="hidden" name="chequeAmt" id="chequeAmtHiddenID">
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
					                        <input type="number" class="form-control" value="<s:property value="cardAmount"/>" id="cardAmountID" name="" placeholder="Amount">
					                        <input type="hidden" name="cardAmount" id="cardAmountHiddenID">
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
			                    
			                    <%-- <div id="creditNoteDetailID" style="padding: 15px; display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-3">
				                    		<font style="font-size: 16px;">Credit Balance</font>
				                    	</div>
				                    	<div class="col-md-3">
					                        <input type="text" class="form-control" id="creditNoteBalID" name="creditNoteBal"  value="<s:property value="creditNoteBal"/>" placeholder="Credit Note Balance">
				                    	</div>
				                    </div>
				                    
			                    </div> --%>
			                    
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
					                        <input type="number" class="form-control" id="otherTypeAmountID" name="" value="<s:property value="otherAmount"/>" placeholder="Enter Other amount here">
					                        <input type="hidden" name="otherAmount" id="otherAmountHiddenID">
				                    	</div>
				                    </div>
				                    
			                    </div>

			     	<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12" align="center">
		                        <button type="button" data-dismiss="modal" class="btn btn-primary" >Close</button>
		                        <!-- <button class="btn btn-success" type="button" onclick="addBillDetails(description.value,rate.value,charges.value,totalBill.value,visitID.value);">Add</button> -->
		                        
			               	 <button class="btn btn-success" type="submit" id="addBillBtnID" onclick="showLoadingImg();">Update</button>
			                
		                        
		                       <!--  <button class="btn btn-warning" type="button" onclick="printBilling();">Print</button> -->
		                      </div>
		                    </div>
			      </form>
         
        </div>
        <!-- <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <a href="Logout"><button type="button" style="width:25%" class="btn btn-success">OK</button></a>
        </center>
        </div>
 -->
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
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" id ="profPicID"aria-expanded="false">
                  	<%
              		if(daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%= form.getFullName() %>
	                
	                <%
	              		}else{
S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveProfilePic(form.getUserID()))).getObjectContent();
	 						
	 						IOUtils.copy(s3ObjectInputStream, new FileOutputStream(new File(realPath + "images/" +daoInf.retrieveProfilePic(form.getUserID()))));  
	 				  %>
	                	
	                 <img src="<%=  "images/" +daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
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
                        <h3>EXISTING VISIT LIST</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                    
                    <div class="row" style="padding-left: 15px;">
				      	<h4><b><%=patientName %></b></h4>
				    </div>
				      
				    <div class="ln_solid" ></div>
                     
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
		              
		              <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                      <thead>
                        <tr>
                          <th>Visit No</th>
                          <th>Visit Type</th>
                          <th>Visit Date</th>
                          <!-- <th>Diagnosis</th> -->
                          <th style="text-align:center;">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        <s:iterator value="patientList" var="UserForm">
                        	<tr id="visitTable<s:property value='visitID' />">
                        			<td><s:property value="visitNumber" /></td>
										
									<td><s:property value="visitType" /></td>
									
									<td><s:property value="visitDate" /></td>
									
									<%-- <td><s:property value="diagnosis" /></td> --%>
									
									<s:if test="%{getCareType() == 'PathologyLab'}">
									    <td align="center">
									    	<%-- <s:if test="%{getReceiptID() == 0}"> &nbsp; --%>
												<s:url id="approveURL"
													action="RenderEditExistingVisit">
													<s:param name="visitID" value="%{visitID}" />
													<s:param name="visitTypeID" value="%{visitTypeID}" />
												</s:url> <s:a href="%{approveURL}">
													<i class="fas fa-pen-square" title="Fill Report" alt="Fill Report" style="font-size: 18px;"></i>
												</s:a>
											<%-- </s:if> --%>
											<a href="javascript:verifyBillDuePending(<s:property value="visitID" />,<s:property value="visitTypeID" />, <s:property value="mdDoctorID" />,<s:property value="careType" />)">
												<i class="fas fa-print" title="Print Report" alt="Print Report" style="font-size: 18px;"></i>
											</a>
											<s:if test="%{getReceiptID() != 0}"> &nbsp;
												<s:url id="approveURL2"
													action="PrintLabBill">
													<s:param name="visitID" value="%{visitID}" />
													<s:param name="visitTypeID" value="%{visitTypeID}" />
													<s:param name="patientID" value="%{patientID}" />
												</s:url> <s:a href="%{approveURL2}">
													<i class="fas fa-file-invoice-dollar" title="Print Bill" alt="Print Bill" style="font-size: 18px;"></i>
												</s:a>
											</s:if>
											<s:if test="%{getBalPayment() > 0}"> &nbsp;
												<a href="javascript:retrieveBillDetails(<s:property value="visitID" />,<s:property value="visitTypeID" />,'<s:property value="careType" />')">
													<i class="fas fa-rupee-sign" title="Clear Dues" alt="Clear Dues" style="font-size: 18px;color: red;"></i>
												</a>
											</s:if>
										</td>
									</s:if>
									<s:else>
									 
										<td align="center"><s:url id="approveURL"
											action="RenderEditExistingVisit">
											<s:param name="visitID" value="%{visitID}" />
											<s:param name="visitTypeID" value="%{visitTypeID}" />
										</s:url> <s:a href="%{approveURL}">
											<img src="images/visit_edit_1.png" style="height:24px;" onmouseover="this.src='images/visit_edit_2.png'"
												onmouseout="this.src='images/visit_edit_1.png'" alt="Open visit"
												title="Open visit" />
										</s:a>
										<s:if test="%{(int)getReceiptID() != 0}"> &nbsp;
												<s:url id="approveURL2"
													action="PrintLabBill">
													<s:param name="visitID" value="%{visitID}" />
													<s:param name="visitTypeID" value="%{visitTypeID}" />
													<s:param name="patientID" value="%{patientID}" />
												</s:url> <s:a href="%{approveURL2}">
													<i class="fas fa-file-invoice-dollar" title="Print Bill" alt="Print Bill" style="font-size: 18px;"></i>
												</s:a>
											</s:if>
											<s:if test="%{getBalPayment() > 0}"> &nbsp;
												<a href="javascript:retrieveBillDetails(<s:property value="visitID" />,<s:property value="visitTypeID" />,'<s:property value="careType" />')">
													<i class="fas fa-rupee-sign" title="Clear Dues" alt="Clear Dues" style="font-size: 18px;color: red;"></i>
												</a>
											</s:if>
											<s:if test="%{getCareType() == 'USG' && getPcpndtStatus() == 'Pending'}">
												
												<a href="javascript:openPcpNDTModal(<s:property value="visitID" />)" id="upload<s:property value='visitID' />">
													<i class="fa fa-upload"  aria-hidden="true" style="font-size: 18px; color : #288bb7" title="Upload PCPNDT Report" alt="Upload PCPNDT Report"></i>
												</a>
											</s:if>
										</td>
									</s:else>
                        	</tr>
                        </s:iterator>
                      </tbody>
                    </table>
                    
                    <div class="col-md-4" style="margin-top:15px;">
                           <a href="ViewPatient">
                             <button class="btn btn-success" type="button" onclick="showLoadingImg();">Cancel</button></a>
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
    
    <!-- PCPNDT modal -->
    	<div id="pcpNDTModal" class="modal fade" role="dialog">
		    <div class="modal-dialog">
		        <!-- Modal content-->
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal">&times;</button>
		                <h4 class="modal-title" style="text-align-last: center">Result and Indications</h4>
		            </div>
		            <div class="modal-body">
		            	<input type="hidden" name="visitID" id="visitIDPCPNDT" value = "">
						<div class="form-check">
						  <input class="form-check-input" type="radio" name="txtNormal" value="normal" id="txtNormal">
						  <label class="form-check-label" for="txtNormal1">
						    Normal
						  </label>
						  
						  <input class="form-check-input" type="radio" name="txtNormal" value="abnormal" id="txtNormal">
						  <label class="form-check-label" for="txtNormal1">
						    Abnormal
						  </label>
						</div>
						<div style= "margin-top : 10px">
									<textarea class="form-control" placeholder="Result" id="pcpNDTResult" rows="6"></textarea>
						</div>
						<div style= "margin-top : 10px">
							  		<input class="form-check-input chkValue" type="checkbox" value="01" id="chk1">
							  <label class="form-check-label" for="chk1" style="margin-right: 15px;">
							    1
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="02" id="chk2">
							  <label class="form-check-label " for="chk2" style="margin-right: 15px;">
							    2
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="03" id="chk3">
							  <label class="form-check-label" for="chk3" style="margin-right: 15px;">
							    3
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="04" id="chk4">
							  <label class="form-check-label" for="chk4" style="margin-right: 15px;">
							    4
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="05" id="chk5">
							  <label class="form-check-label" for="chk5" style="margin-right: 15px;">
							    5
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="06" id="chk6">
							  <label class="form-check-label" for="chk6" style="margin-right: 15px;">
							    6
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="07" id="chk7">
							  <label class="form-check-label" for="chk7" style="margin-right: 15px;">
							    7
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="08" id="chk8">
							  <label class="form-check-label" for="chk8" style="margin-right: 15px;">
							    8
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="09" id="chk9">
							  <label class="form-check-label" for="chk9" style="margin-right: 15px;">
							    9
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="10" id="chk10">
							  <label class="form-check-label" for="chk10" style="margin-right: 15px;">
							    10
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="11" id="chk11">
							  <label class="form-check-label" for="chk11" style="margin-right: 15px;">
							    11
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="12" id="chk12">
							  <label class="form-check-label" for="chk12" style="margin-right: 15px;">
							    12
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="13" id="chk13">
							  <label class="form-check-label" for="chk13" style="margin-right: 15px;">
							    13
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="14" id="chk14">
							  <label class="form-check-label" for="chk14" style="margin-right: 15px;">
							    14
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="15" id="chk15">
							  <label class="form-check-label" for="chk15" style="margin-right: 15px;">
							    15
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="16" id="chk16">
							  <label class="form-check-label" for="chk16" style="margin-right: 15px;">
							    16
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="17" id="chk17">
							  <label class="form-check-label" for="chk17" style="margin-right: 15px;">
							    17
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="18" id="chk18">
							  <label class="form-check-label" for="chk18" style="margin-right: 15px;">
							    18
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="19" id="chk19">
							  <label class="form-check-label" for="chk19" style="margin-right: 15px;">
							    19
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="20" id="chk20">
							  <label class="form-check-label" for="chk20" style="margin-right: 15px;">
							    20
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="21" id="ch2k1">
							  <label class="form-check-label" for="chk21" style="margin-right: 15px;">
							    21
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="22" id="chk22">
							  <label class="form-check-label" for="chk22" style="margin-right: 15px;">
							    22
							  </label>
							  
							  <input class="form-check-input chkValue" type="checkbox" value="23" id="chk23">
							  <label class="form-check-label" for="chk23" style="margin-right: 15px;">
							    23
							  </label>
							  
						</div>
		            </div>
		            <div class="modal-footer">
		            <button type="button" class="btn btn-warning" onclick="setDetailsPCPNDT1();" >Submit</button>
		                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		            </div>
		        </div>
		    </div>
		</div>
	<!-- PCPNDT MODAL END -->
	
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
    <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    
    <!-- Datatables -->
    <script>
      $(document).ready(function() {

    	  $("input[type=number]").on("keydown",function(e){
      		if (e.which === 38 || e.which === 40) {
      			e.preventDefault();
      		}
      	});
    	  
        $('#datatable-responsive').DataTable({
        	aoColumnDefs: [
		      	/*{ "iDataSort": 1, "aTargets": [ 0 ] },*/
		      	/*{ "targets":  1 },*/
		      	{ "targets":  2 , "type":"date" },
		    ],
            order: [[2, 'desc']],
        });

      });
    </script>
    <!-- /Datatables -->
    
  </body>
</html>