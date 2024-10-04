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

    <title>Add Stock | E-Dhanvantari</title>
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

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
        document.location="dashboard.jsp";
      }
    </script>
    
    
    <!-- Barcode scanning -->
    
    <script type="text/javascript">
	
	    document.addEventListener('DOMContentLoaded', function() {

	    		$("#barcodeID").keypress(function(e){
	    		   
	    		     $("#barcodeID").focus();
	    		     
	    		     setTimeout(function() {
	    		    	 
	    		      var barcode = $("#barcodeID").val();

	    		      getProduct(barcode);
	    		      
	    		}, 500);
	    		   });
	    	     
		});
	    
	  
	</script>
    
    <!-- Ends -->
    
    
    <!-- Product based on barcode -->

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getProduct(productBarcode) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var productID = "";
					
					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						productID = array.Release[i].productID;
						check = array.Release[i].check;

					}
					
					if(check == 0){
						
						$("#productAlertModal").modal('show');
					}
						
					else{
						
						document.getElementById("productID").value = productID;
						getProductRate(productID);
						
					}
					
				}
			};
			xmlhttp.open("GET", "GetProduct?barcode="
					+ productBarcode, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    
    <!-- Display and hide cheque details div -->
    
    <script type="text/javascript">
    
    	function displayChequeDetailsDiv(){

    		$('#debitDetailID').hide(1000);
    		$('#cashDetailID').hide(1000);
    		$('#chequeDetailID').show(1000);

    	}
    	
    	function displayDebitDetailsDiv(){
    		
    		$('#chequeDetailID').hide(1000);
    		$('#cashDetailID').hide(1000);
    		$('#debitDetailID').show(1000);
    		
    	}

    	function hideChequeDetailsDiv(){

    		$('#chequeDetailID').hide(1000);
    		$('#debitDetailID').hide(1000);
    		$('#cashDetailID').show(1000);

    	}
    	
    </script>
    
    <!-- Ends -->
    
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableUser(url){
			if(confirm("Disabled users cannot access application. Are you sure you want to disable this user?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- PDF Print window -->
    
    <%
		String pdfOutFIleName = (String) request.getAttribute("PDFOutFileName");
		
		if(pdfOutFIleName == null || pdfOutFIleName == ""){
			
			pdfOutFIleName = "dummy";
			
		}else{
			
			if(pdfOutFIleName.contains("\\")){
				
				pdfOutFIleName = pdfOutFIleName.replaceAll("\\\\", "/");
				
			}
		}
	%>
	
	<%
		if(pdfOutFIleName!="dummy"){
	%>
	<script type="text/javascript">
	
	    document.addEventListener('DOMContentLoaded', function() {
			  PdfAction();
		});
	  
	</script>
	<% 
		}
	%>
	
	<script type="text/javascript">
	
		var popup;
		function PdfAction(){
			popup = window.open('PDFDownload?pdfOutPath=<%=pdfOutFIleName%>',"Popup", "width=700,height=700");
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
		
		.table-bordered>tbody>tr>td{
			word-wrap: break-word !important;
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
	
	 <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
	</style>
	
	<!-- Get CUrrent date and time -->
	
	<%
		Date date = new Date();
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	%>
	
	<!-- Ends -->
    
    <!-- For login message -->
    <%
    LoginForm form = (LoginForm) session.getAttribute("USER");
		if(session.getAttribute("USER") == "" || session.getAttribute("USER") == null){
			String loginMessage = "Plase login using valid credentials";
			request.setAttribute("loginMessage",loginMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
		
		int userID = form.getUserID();
		
	LoginDAOInf daoInf = new LoginDAOImpl();
	
	PrescriptionManagementDAOInf managementDAOInf = new PrescriptionManagementDAOImpl();
	
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
    
    <!-- Retrieving clinic LIst -->
    
    <%
    
    	HashMap<Integer, String> clinicMap = daoInf.retrieveClinicList(form.getPracticeID());
    
    %>
    
    <!-- Ends -->
    
    <%
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
    
		//int registerCheck = registrationDAOinf.checkOpenLeaveRegister(form.getPracticeID());
	%>
    
    
    <!-- Retrieve receipt no -->
    
    <%
    	String receiptNo = managementDAOInf.retrieveReceiptNo(form.getClinicID(), form.getClinicSuffix());
    %>
    
    <!-- Ends -->
    
    
    
    <!-- Retrieve Component, COncession and Other CHarges list -->
    
    <%
    	ConfigurationUtil configurationUtil = new ConfigurationUtil();
    
    	HashMap<Integer, String> productList = configurationUtil.sortHashMap(managementDAOInf.retrieveProductList(form.getClinicID()));
    
    
    	//HashMap<Integer, String> taxList = productDAOInf.retrieveProductTax();
	    
	    //List<String> otherChargesList = billingDAOInf.retrieveOtherChargesList(form.getBloodBankID());
    %>
    
    <!-- Ends -->
    
    <!-- For session timeout -->
    <%
    
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
    	String userListEnable = (String) request.getAttribute("userListEnable");
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
    		//Setting blank values to PIN fields
        	$('#lockPIN').val("");

        	//opening security credentials modal
    		$("#lockModal").modal("show");
    }

    function showLogoutModal(){
    		$("#logoutModal").modal("show");
    }

    function showSecurityModal(){
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
    }
    </script>
    
    <!-- ENds -->
    
    
    <!-- Client details based on Client -->

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function getClientDetails(customerName) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					var check = 0;
					var clientID;
					var clientMobile;
					var clientAddress;
					var clientAgency;
					var clientVAT;
					
					if(customerName == "" || customerName == null){

						check = 0;

					}else{
	
						for ( var i = 0; i < array.Release.length; i++) {
	
							console.log(array.Release.length);
							
							clientID = array.Release[i].clientID;
							clientMobile = array.Release[i].clientMobile;
							clientAddress = array.Release[i].clientAddress;
							clientAgency = array.Release[i].clientAgency;
							clientVAT = array.Release[i].clientVAT;
							check = array.Release[i].check;
	
							if(clientID == "undefined"){
								clientID = 0;
							}
							if(clientMobile == "undefined"){
								clientMobile = "";
							}
							if(clientAddress == "undefined"){
								clientAddress = "";
							}
							if(clientAgency == "undefined"){
								clientAgency = "";
							}
							if(clientVAT == "undefined"){
								clientVAT = "";
							}
							if(check == "undefined"){
								check = 0;
							}
							
						}

					}

					if(check == 1){
						
						$('#customerMobileID').val(clientMobile);
						$('#supplierID').val(clientID);
						$('#customerAddressID').val(clientAddress);
						$('#customerAgencyID').val(clientAgency);
						$('#customerVATNoID').val(clientVAT);

						$('#send').attr('disabled', false);
						//$('#send1').attr('disabled', false);

						
					}else{

						$('#customerMobileID').val("");
						$('#supplierID').val(0);
						$('#customerAddressID').val("");
						$('#customerAgencyID').val("");
						$('#customerVATNoID').val("");

						$('#send').attr('disabled', true);
						//$('#send1').attr('disabled', false);

					}
					
				}
			};
			xmlhttp.open("GET", "GetClientDetails?supplierID="
					+ customerName, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
	</script>
    
    <!-- Ends -->
    
    
    <!-- Rate based on product -->

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getProductRate(productID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var rate;
					
					if(productID == "000"){
						
						//document.getElementById("productRateID").value = "";

						document.getElementById("barcodeID").value = "";
						
						/*document.getElementById("VATID").value = "";
						
						document.getElementById("inStockID").value = "";
						
						document.getElementById("productQuantityID").value = "1";
						
						document.getElementById("productAmountID").value = "";*/
						
					}else{
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							//document.getElementById("productRateID").value = array.Release[i].productRate;

							document.getElementById("barcodeID").value = array.Release[i].productBarcode;
							
							/*document.getElementById("VATID").value = array.Release[i].productVAT;
							
							document.getElementById("inStockID").value = array.Release[i].productNetStock;
							
							rate = array.Release[i].productRate;*/
						}

						/*var quantity = document.getElementById("productQuantityID").value;

						var productAmt = parseFloat(parseFloat(quantity) * parseFloat(rate));

						document.getElementById("productAmountID").value = parseFloat(productAmt);*/
						
					}
					
				}
			};
			xmlhttp.open("GET", "GetProductRate?productID="
					+ productID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    
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
				
				$('#lockButtonID').click();
			
    	}

    	function hideSecCredModal(){
				
				$('#secCredCancelID').click();

    	}
    </script>
    
    <!-- Ends -->
  
    
    <!-- Quantity change function -->
    
    <script type="text/javascript">
    	
		function QntyCheck(compQuantity, compRate){


			if(compQuantity == ""){
				compQuantity = 0;

				document.getElementById("productAmountID").value = 0;
        	}

			if(compRate == ""){
				compRate = 0;

				document.getElementById("productAmountID").value = 0;

        	}

			var amount = parseFloat(parseFloat(compQuantity) * parseFloat(compRate));
			
			if(amount.toString().includes(".")){
				var array = amount.toString().split(".");
				
				if(array[1].length > 2 ){
					amount = array[0] + "." + array[1].substr(0,2);
				}
			}

			document.getElementById("productAmountID").value = parseFloat(amount);
			
		}

		
		function actualPaymentAmtChange(actualPayment, netRecvPayment){

			if(actualPayment == ""){
				actualPayment = 0;
        	}

			if(netRecvPayment == ""){
				netRecvPayment = 0;
        	}

			var outstndngAmt = parseFloat(parseFloat(netRecvPayment) - parseFloat(actualPayment));
			
			if(outstndngAmt.toString().includes(".")){
				var array = outstndngAmt.toString().split(".");
				
				if(array[1].length > 2 ){
					outstndngAmt = array[0] + "." + array[1].substr(0,2);
				}
			}

			document.getElementById("productBalancePaymentAmtID").value = parseFloat(outstndngAmt);
			
		}
		
		function cashToReturn(productCashPaid, productAdvncePayment){
			
			if(productCashPaid == ""){
				productCashPaid = 0;
        	}

			if(productAdvncePayment == ""){
				productAdvncePayment = 0;
        	}
			
			var cashToReturn = parseFloat(parseFloat(productCashPaid) - parseFloat(productAdvncePayment));
			
			if(cashToReturn.toString().includes(".")){
				var array = cashToReturn.toString().split(".");
				
				if(array[1].length > 2 ){
					cashToReturn = array[0] + "." + array[1].substr(0,2);
				}
			}
			
			document.getElementById("productCashToReturnID").value = parseFloat(cashToReturn);
			
		}


    </script>
    
    <!-- Ends -->
    
    <!-- Product Name on productID -->

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function addNewRow1(product, barcode, expiryDate, quantity, costPrice, sellingPrice, taxName, taxPercent, amount) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						var productName = array.Release[i].productName;
						
						addNewRow(product, barcode, expiryDate, quantity, costPrice, sellingPrice, taxName, taxPercent, amount, productName);

					}
					
				}
			};
			xmlhttp.open("GET", "GetProductName?productID="
					+ product, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		function addNewRow2(product, barcode, expiryDate, quantity, costPrice, sellingPrice, taxName, taxPercent, amount){
			
			if(product == "000"){
				alert("No product selected. Please select product.");
			}else if(costPrice == ""){
				alert("Please enter cost price.");
			}else if(sellingPrice == ""){
				alert("Please enter selling price.");
			}else if(quantity == ""){
				alert("Please enter quantity.");
			}else{
				addNewRow1(product, barcode, expiryDate, quantity, costPrice, sellingPrice, taxName, taxPercent, amount);
			}
			
		}
		
	</script>
    
    <!-- Ends -->
    
    <!-- Add New Row function for Component -->
    
    <script type="text/javascript">
    	var counter = 1;
    	
    	function addNewRow(product, barcode, expiryDate, quantity, costPrice, sellingPrice, taxName, taxPercent, amount, productName){
    		
	    		//Calculating VAT
				var finalVAT = (parseFloat(amount) * parseFloat(taxPercent)) / 100;
	    		
				var taxInclusive = "";
	    		
	    		if($("#taxInclYesID").is(":checked")){
	    			taxInclusive = $("#taxInclYesID").val();
	    		}else if($("#taxInclNoID").is(":checked")){
	    			taxInclusive = $("#taxInclNoID").val();
	    		}else{
	    			taxInclusive = "1";
	    		}
				
				var array = ["Yes","No"];
        	
            	var trID = "trID"+counter;
            	var tdID = "tdID"+counter;
            	var delImgTDID = "delImgTDID"+counter;
            	var amtTDIT = "amtTDID"+counter;
            	var VATTDIT = "VATTDID"+counter;
				var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
				"<td style='width: 15%'>"+productName+"<input type='hidden' name='productCompID' value='"+product+"'></td>"+
				"<td style='width: 10%'>"+barcode+"<input type='hidden' name='productCompBarcode' value='"+barcode+"'></td>"+
				"<td style='width: 10%'>"+expiryDate+"<input type='hidden' name='productExpiryDate' value='"+expiryDate+"'></td>"+
				"<td style='width: 10%'>"+costPrice+"<input type='hidden' name='productCostPrice' value='"+costPrice+"'></td>"+
				"<td style='width: 10%'>"+sellingPrice+"<input type='hidden' name='productSellingPrice' value='"+sellingPrice+"'></td>"+
				"<td style='width: 10%'>"+array[taxInclusive]+"<input type='hidden' name='productTaxInclusive' value='"+taxInclusive+"'></td>"+
				"<td style='width: 10%'>"+taxName+"<input type='hidden' name='productTaxName' value='"+taxName+"'></td>"+
				"<td style='width: 10%'>"+taxPercent+"<input type='hidden' name='productTaxPercent' value='"+taxPercent+"'></td>"+
				"<td style='width: 10%'>"+quantity+"<input type='hidden' name='productQuantity' value='"+quantity+"'></td>"+
				"<td style='width: 10%' id="+amtTDIT+">"+amount+"<input type='hidden' name='productAmount' value='"+amount+"'></td>"+
				"<td style='width: 5%' id="+delImgTDID+"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Delete row' title='Delete row' onclick='removeTR(\"" + trID +"\",\""+counter+"\",\""+tdID+"\",\""+amount+"\",\""+finalVAT+"\");'/></td>"+
				"</tr>";

				$(trTag).insertAfter($('#compTRID'));
				
				var totalAmt = document.getElementById("productTotalAmountID").value;
				
				var productVAT = document.getElementById("productVATID").value;
				
				if(totalAmt == ""){
					totalAmt = 0;
				}
				
				if(productVAT == ""){
					productVAT = 0;
				}
				
				finalVAT = parseFloat(parseFloat(finalVAT) + parseFloat(productVAT));
				

				if(finalVAT.toString().includes(".")){
					var array = finalVAT.toString().split(".");
					
					if(array[1].length > 2 ){
						finalVAT = array[0] + "." + array[1].substr(0,2);
					}
				}
				
				var finalAmt = parseFloat(parseFloat(totalAmt) + parseFloat(amount));
				
				if(finalAmt.toString().includes(".")){
					var array = finalAmt.toString().split(".");
					
					if(array[1].length > 2 ){
						finalAmt = array[0] + "." + array[1].substr(0,2);
					}
				}
				
				document.getElementById("productTotalAmountID").value = parseFloat(finalAmt);
				
				//document.getElementById("productNetReceivableAmtID").value = parseFloat(finalAmt);

    			document.getElementById("productAdvancePaymentAmtID").value = parseFloat(finalAmt);
				
				document.getElementById("productBalancePaymentAmtID").value = 0;
				
				document.getElementById("productVATID").value = parseFloat(finalVAT);

				counter++;

				//changing sr no of next row
				$("#barcodeID").val("");
				//$("#productRateID").val("");
				$("#productQuantityID").val(1);
				$("#productID").val("000");
				$("#productAmountID").val("");
				//$("#inStockID").val("");
				//$("#VATID").val("");
				$("#expiryDateID").val("");
				$("#costPriceID").val("");
				$("#sellingPriceID").val("");
				$("#productTaxPercentID").val("");
				$("#taxInclYesID").prop("checked", false);
				$("#taxInclNoID").prop("checked", false);
				$("#taxNameID").val("000");
           
			
    	}

    	//Function to remove TR
    	function removeTR(trID, count, tdID, amount, finalVAT){
        	if(confirm("Are you sure you want to remove this row?")){

            	dummyTotalAmt = $('#productTotalAmountID').val();
            	
            	var productVAT = document.getElementById("productVATID").value;
            	
            	if(productVAT == ""){
					productVAT = 0;
				}

            	$("#"+trID+"").remove();
            	
            	finalVAT = parseFloat(parseFloat(productVAT) - parseFloat(finalVAT));
            	
            	if(finalVAT.toString().includes(".")){
					var array = finalVAT.toString().split(".");
					
					if(array[1].length > 2 ){
						finalVAT = array[0] + "." + array[1].substr(0,2);
					}
				}

            	var finalAmt = parseFloat(dummyTotalAmt) - parseFloat(amount);
            	
            	if(finalAmt.toString().includes(".")){
					var array = finalAmt.toString().split(".");
					
					if(array[1].length > 2 ){
						finalAmt = array[0] + "." + array[1].substr(0,2);
					}
				}

            	document.getElementById("productTotalAmountID").value = parseFloat(finalAmt);

    			//document.getElementById("productNetReceivableAmtID").value = parseFloat(finalAmt);
    			
				document.getElementById("productAdvancePaymentAmtID").value = parseFloat(finalAmt);
				
				document.getElementById("productBalancePaymentAmtID").value = 0;
				
				document.getElementById("productVATID").value = parseFloat(finalVAT);

        	}
    	}
    </script>
    
    <!-- Ends -->
    
    
    
    <!-- Delete product row -->
    
    <script type="text/javascript">
    
    	function deleteRow(stockID, amount, productID, stockReceiptID, VAT){
    		if(confirm("Are you sure you want to remove this row?")){
    			deleteProductRow(stockID, amount, productID, stockReceiptID, VAT);
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
	
		function deleteProductRow(stockID, amount, productID, stockReceiptID, VAT) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						
					}
					
					//Removing TR
					var trID = "prodTRID"+stockID;
					
					$("#"+trID+"").remove();
					
					//Calculating VAT
					var finalVAT = (parseFloat(amount) * parseFloat(VAT)) / 100;
					
					var productVAT = document.getElementById("productVATID").value;
	            	
	            	if(productVAT == ""){
						productVAT = 0;
					}
					
					var productNetReceivableAmt = $('#productTotalAmountID').val();
					
					if(productNetReceivableAmt == ""){
						productNetReceivableAmt = 0;
					}
					
					
					if(check == 1){
						
						var totalAmount = parseFloat(parseFloat(productNetReceivableAmt) - parseFloat(amount));
						
						finalVAT = parseFloat(productVAT) - parseFloat(finalVAT);
						
						$('#productTotalAmountID').val(totalAmount);
						
						//$('#productNetReceivableAmtID').val(totalAmount);
						
						$('#productAdvancePaymentAmtID').val(totalAmount);
						
						$('#productBalancePaymentAmtID').val(0);
						
						$('#productVATID').val(finalVAT);
						
						//$('#productCashPaidID').val("");
						
						//$('#productCashToReturnID').val("");
						
					}else{
						alert("Failed to delete row. Please check server logs for more details.")
					}
					
				}
			};
			xmlhttp.open("GET", "DeleteProductRow?stockID="
					+ stockID+"&productID="+productID+
					"&receiptID="+stockReceiptID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    <!-- Retrievig product tax name list -->
    
    <%
    
    	List<String> taxList = managementDAOInf.retrieveproducttaxList();
    
    %>
    
    <!-- Ends -->
    
    <!-- Tax percent based on tax name -->
    
    <script type="text/javascript">
    	
    	function getTaxPercent(taxName){
    		
    		taxName = taxName.replace("%","==");
    		
    		getTaxPercent1(taxName);
    		
    	}
    
    </script>

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getTaxPercent1(taxName) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						document.getElementById("productTaxPercentID").value = parseFloat(array.Release[i].taxPercent);

					}

					
					
				}
			};
			xmlhttp.open("GET", "GetTaxPercent?taxName="
					+ taxName, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    	function submitForm(){
    		

    		var supplier = $("#suplierNameID").val();
    		
    		if(supplier == "-1"){
    			alert("No supplier selected. Please select supplier.");
    			return false;
    		}else{
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
    
    
    <!-- get stock receipt no by clinicID -->

	<script type="text/javascript" charset="UTF-8">
	
		function retrieveStockReceiptNo(clinicID){
			if(clinicID == "000"){
				alert("Please select clinic.");
				$("#receiptNoID").val("");
			}else{
				retrieveStockReceiptNo1(clinicID);
			}
		}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveStockReceiptNo1(clinicID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					//var visitCheck = "0";
					
					var receiptNo = "";
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						receiptNo = array.Release[i].receiptNo;

					}
					
					$("#receiptNoID").val(receiptNo);
	
					retrieveProductListByClinicID(clinicID);
					
				}
			};
			xmlhttp.open("GET", "RetrieveStockReceiptNoByClinicID?clinicID="
					+ clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		function retrieveProductListByClinicID(clinicID) {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = "0";
					
					var productTag = "<select name='productCompID1' id='productID' class='form-control' onchange='getProductRate(productID.value);'>"+
									 "<option value='000'>Select Product</option>";
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						var productID = array.Release[i].productID;
						var tradeName = array.Release[i].tradeName;
						
						productTag += "<option value='"+productID+"'>"+tradeName+"</option>";

					}
					
					productTag += "</select>";
					
					if(check == 0){
						alert("No product found for the selected clinic. Please add product for selected clinic.");
						
						productTag = "<select name='productCompID1' id='productID' class='form-control' onchange='getProductRate(productID.value);'>"+
						 "<option value='000'>Select Product</option></select>";
						 
						$("#productTDID").html(productTag);
					}else{
						$("#productTDID").html(productTag);
					}
					
				}
			};
			xmlhttp.open("GET", "RetrieveProductListByClinicID?clinicID="
					+ clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
    
    <!-- Ends -->
    
    
  </head>

  <body class="nav-md" id="myID" onload="myFun();">
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
  
  
  <!-- Product alert modal -->
  
  <div id="productAlertModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;">No product found for entered barcode. Please enter valid barcode.</h4>
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
  
  
  <!-- Outstanding amount error modal -->
  
  <div id="outstandingAmtModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-body">
          
          <center>
          	<h4 style="color:black;">Not allowed to enter value.</h4>
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
          <a href="LogoutUser"><button type="submit" style="width:25%" class="btn btn-success">OK</button></a>
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
          <a href="LogoutUser?userID=<%= userID %>"><button type="submit" style="width:25%" class="btn btn-primary">Logout</button></a>
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
                        <h3>ADD STOCK</h3>
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
		              
		             <form action="AddStockReceipt" class="form-horizontal" onsubmit="return submitForm();"  method="POST">                     
                     
                     <div class="row">
                     
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">Receipt Date & Time</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     		 <b style="font-size: 16px;"><%=dateFormat.format(date) %></b>
                     	</div>
                     	<div class="col-md-2 col-xs-7">
                     		<font style="font-size: 16px;">Receipt No*</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6" >
                     		<input  class="form-control" name="receiptNo" placeholder="Receipt No" required="required" id="receiptNoID" value="<%=receiptNo %>" readonly="readonly" type="text">
                     	</div>
                     	
                     </div>
                     
                     <div class="row" style="margin-top:15px;">
                     
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">Supplier Name*</font>
                     	</div>
                     	<div class="col-md-3 col-xs-6">
                     		
                     		<s:select list="stockSupplierList" name="name" value="-1" class="form-control" id="suplierNameID" onchange="getClientDetails(suplierNameID.value);" headerKey="-1" headerValue="Select Supplier" required="required" ></s:select>
                     		<input type="hidden" name="supplierID" id="supplierID">
                     	</div>
                     	<div class="col-md-1" id="clientCheckID">
                     		
                     	</div>
                     	<div class="col-md-2 col-xs-5">
                     		<font style="font-size: 16px;">Mobile</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     		<input  class="form-control" readonly="readonly"  name="mobile" id="customerMobileID" placeholder="Mobile" type="number" onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
                     	</div>
                     	
                     </div>
                     
                     <div class="row" style="margin-top:15px;">
                     
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">Agency</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     		<input  class="form-control" readonly="readonly" name="agency" id="customerAgencyID" placeholder="Agency" type="text">
                     	</div>
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">GSTIN</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     		<input  class="form-control" readonly="readonly"  name="vatNumber" id="customerVATNoID" placeholder="GSTIN" type="text">
                     	</div>
                     	
                     </div>
                     
                     <div class="row" style="margin-top:15px;">
                     	
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">Address</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     	    <textarea rows="3" class="form-control" readonly="readonly" name="address"  class="form-control" id="customerAddressID" placeholder="Address"></textarea>
                     	</div>
                     	<div class="col-md-2 col-xs-6">
                     		<font style="font-size: 16px;">Clinic</font>
                     	</div>
                     	<div class="col-md-4 col-xs-6">
                     		<select name="clinicID" class="form-control" onchange="retrieveStockReceiptNo(this.value);">
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
                     	</div>
                     	
                     </div>
                     
                      <div class="ln_solid"></div>
		              
		              <div style="width: 100%; margin-top: 15px;overflow: auto; white-space: nowrap;">
		              
		              <table class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                      <thead>
                        <tr>
                          <th style="width: 15%">Product</th>
                          <th style='width: 10%'>Barcode</th>
                          <th style='width: 10%'>Expiry Date</th>
                          <th style='width: 10%'>Cost Price</th>
                          <th style='width: 10%'>Selling Price</th>
                          <th style='width: 10%'>Tax Inclusive</th>
                          <th style='width: 10%'>Tax Name</th>
                          <th style='width: 10%'>Tax Percent</th>
                          <th style='width: 5%'>Qty</th>
                          <th style='width: 15%'>Amt</th>
                          <th style='width: 5%'></th>
                        </tr>
                      </thead>
                      <tbody>                       	
                        	
                        	<tr id="compTRID">
						
									<td  style="width: 15%" id="productTDID"><select class="form-control" name="productCompID1" id="productID" onchange="getProductRate(productID.value);">
											<option value="000">Select Product</option>
											<%
												for(int key: productList.keySet()){
											%>
											
												<option value="<%=key %>"><%=productList.get(key) %></option>
												
											<%
											}
											%>
										</select></td>
										
									<td style='width: 10%'><input  class="form-control" name="barcode1" id="barcodeID"  placeholder="Barcode" type="text"></td>
									
									<td style='width: 10%'><input  class="form-control" name="" id="expiryDateID"  placeholder="Expiry Date" type="text"></td>
									
									<td style='width: 10%'><input  class="form-control" name="cp" id="costPriceID" placeholder="Cost Price" onkeyup="QntyCheck(productQuantityID.value, costPriceID.value);" type="number"></td>
									
									<td style='width: 10%'><input  class="form-control" name="sp" id="sellingPriceID" placeholder="Selling Price" type="number"></td>
									
									<td style='width: 10%'><input type="radio" name="taxIncl" id="taxInclYesID" value="0">&nbsp;Yes<br>
										<input type="radio" name="taxIncl" id="taxInclNoID" value="1">&nbsp;No</td>
										
									<td style='width: 10%'>
										<select name="" id="taxNameID" class="form-control" onchange="getTaxPercent(taxNameID.value);">
											<option value="000">Select Tax Name</option>
											<%
												for(String taxName : taxList){
											%>
											<option value="<%=taxName%>"><%=taxName%></option>
											<%
												}
											%>
									 	</select>
									</td>
									
									<td style='width: 10%'><input type="number" name="" id="productTaxPercentID"  placeholder="Tax Percent" class="form-control"></td>
									
									<td style='width: 5%'><input  class="form-control" name="productQuantity1" id="productQuantityID" placeholder="Quantity" onkeyup="QntyCheck(productQuantityID.value, costPriceID.value);" value="1" onfocus="this.value='0'" onblur="this.value" type="number"></td>
									
									<td style='width: 15%'><input  class="form-control" name="productAmount1" id="productAmountID" placeholder="Amount" readonly="readonly" type="number"></td>
									
  									<td style='width: 5%'> <img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  									onclick="addNewRow2(productID.value, barcodeID.value, expiryDateID.value, productQuantityID.value, costPriceID.value, sellingPriceID.value, taxNameID.value, productTaxPercentID.value, productAmountID.value);" 
											onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Product"
											title="Add Product" /></td>
									
                        	</tr>
                       
                        	
                        	<tr>
                        		
                        		<td colspan="9" align="right" style="font-size: 14px;padding-top:15px;">Total Value of Stock Added</td>
                        		
                        		<td colspan="2">
                        			<input  class="form-control" name="productTotalAmount" value="" id="productTotalAmountID" placeholder="Total Value of Stock Added" readonly="readonly" type="number">
                        		</td>
                        	
                        	</tr>
                        	
                        	<!-- <tr>
                        		
                        		<td colspan="7" align="right" style="font-size: 14px;padding-top:15px;">Net Receivable Amt.</td>
                        		
                        		<td colspan="2">
                        			<input  class="form-control" name="productNetReceivableAmt" id="productNetReceivableAmtID" readonly="readonly" placeholder="Net Receivable Amt." type="number">
                        		</td>
                        	
                        	</tr> -->
                        	
                        	<tr>
                        		
                        		<td colspan="9" align="right" style="font-size: 14px;padding-top:15px;">Advance Payment</td>
                        		
                        		<td colspan="2">
                        			<input  class="form-control" name="productAdvancePaymentAmt" id="productAdvancePaymentAmtID" placeholder="Advance Payment" onkeyup="actualPaymentAmtChange(productAdvancePaymentAmtID.value, productTotalAmountID.value);" type="number" step="0.01">
                        		</td>
                        	
                        	</tr>
                        	
                        	<tr>
                        		
                        		<td colspan="9" align="right" style="font-size: 14px;padding-top:15px;">Balance Payment</td>
                        		
                        		<td colspan="2">
                        			<input  class="form-control" name="productBalancePaymentAmt" id="productBalancePaymentAmtID" readonly="readonly" placeholder="Balance Payment" type="number">
                        		</td>
                        	
                        	</tr>
                        	
                        	<tr>
                        		
                        		<td colspan="9" align="right" style="font-size: 14px;padding-top:15px;">Total GST on Stock Added</td>
                        		
                        		<td colspan="2">
                        			<input  class="form-control" name="productVAT" id="productVATID" readonly="readonly" placeholder="Total GST on Stock Added" type="number">
                        		</td>
                        	
                        	</tr>
                        	
                      </tbody>
                    </table>
                    </div>
                    
                    <!-- <div class="row" style="margin-top:20px;">
                    	<div class="col-md-2 col-sm-3 col-md-4" style="padding-top:5px;">
                    		<font style="font-size: 16px;">Payment Type</font>
                    	</div>
                    	
                    	<div class="col-md-2 col-sm-3 col-md-4">
                    		<div class="col-md-4 col-sm-4 col-xs-6">
                    			<input type="radio" name="productPaymentType" checked="checked" onclick="hideChequeDetailsDiv();" id="paymentTypeCashID" style="height: 25px;box-shadow: none;" value="Cash" class="form-control">
                    		</div>
                    		<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top:5px;">
                    			<font style="font-size: 15px;">Cash</font>
                    		</div>
                    	</div>
                    	
                    	<div class="col-md-2 col-sm-3 col-md-4">
                    		<div class="col-md-4 col-sm-4 col-xs-6">
                    			<input type="radio" name="productPaymentType" onclick="displayChequeDetailsDiv();" id="paymentTypeChequeID" style="height: 25px;box-shadow: none;" value="Cheque" class="form-control">
                    		</div>
                    		<div class="col-md-4 col-sm-4 col-xs-6" style="padding-top:5px;">
                    			<font style="font-size: 15px;">Cheque</font>
                    		</div>
                    	</div>
                    	
                    	<div class="col-md-2 col-sm-3 col-md-4">
                    		<div class="col-md-4 col-sm-4 col-xs-6">
                    			<input type="radio" name="productPaymentType" onclick="displayDebitDetailsDiv();" id="paymentTypeDebitID" style="height: 25px;box-shadow: none;" value="Debit Card" class="form-control">
                    		</div>
                    		<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top:5px;">
                    			<font style="font-size: 15px;">Debit Card</font>
                    		</div>
                    	</div>
                    	
                    	<div class="col-md-2 col-sm-3 col-md-4">
                    		<div class="col-md-4 col-sm-4 col-xs-6">
                    			<input type="radio" name="productPaymentType" onclick="displayDebitDetailsDiv();" id="paymentTypeCreditID" style="height: 25px;box-shadow: none;" value="Credit Card" class="form-control">
                    		</div>
                    		<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top:5px;">
                    			<font style="font-size: 15px;">Credit Card</font>
                    		</div>
                    	</div>
                    	
                    </div> -->
                    
                    <!-- <div id="cashDetailID" style="margin-top:15px;">
                    
                    <div class="ln_solid"></div>
                    
	                    <div class="row">
	                    	<div class="col-md-3">
	                    		<font style="font-size: 16px;">Cash Paid</font>
	                    	</div>
	                    	<div class="col-md-3">
		                        <input type="number" step="0.01" class="form-control" name="productCashPaid" id="productCashPaidID" onkeyup="cashToReturn(productCashPaidID.value, productAdvancePaymentAmtID.value)" placeholder="Cash Paid">
	                    	</div>
	                    	<div class="col-md-3" align="right">
	                    		<font style="font-size: 16px;">Cash To Return</font>
	                    	</div>
	                    	<div class="col-md-3">
	                    		<input  class="form-control" name="productCashToReturn" id="productCashToReturnID" readonly="readonly" placeholder="Cash To Return" type="number">
	                    	</div>
	                    </div>
	                    
	                <div class="ln_solid"></div>
                    
                    </div>
                    
                    <div id="chequeDetailID" style="margin-top:15px; display: none;">
                    
                    <div class="ln_solid"></div>
                    
	                    <div class="row">
	                    	<div class="col-md-3">
	                    		<font style="font-size: 16px;">Cheque Issued By</font>
	                    	</div>
	                    	<div class="col-md-3">
		                        <input type="text" class="form-control" name="chequeIssuedBy" placeholder="Cheque Issued By">
	                    	</div>
	                    	<div class="col-md-3" align="right">
	                    		<font style="font-size: 16px;">Cheque No.</font>
	                    	</div>
	                    	<div class="col-md-3">
	                    		<input  class="form-control" name="chequeNo"  placeholder="Cheque No." type="text">
	                    	</div>
	                    </div>
	                    
	                    <div class="row" style="margin-top:15px;">
	                    	<div class="col-md-3">
	                    		<font style="font-size: 16px;">Bank Name</font>
	                    	</div>
	                    	<div class="col-md-3">
		                        <input type="text" class="form-control" name="chequeBankName" placeholder="Bank Name">
	                    	</div>
	                    	<div class="col-md-3" align="right">
	                    		<font style="font-size: 16px;">Branch</font>
	                    	</div>
	                    	<div class="col-md-3">
	                    		<input  class="form-control" name="chequeBankBranch"  placeholder="Branch" type="text">
	                    	</div>
	                    </div>
	                    
	                    <div class="row" style="margin-top:15px;">
	                    	<div class="col-md-3">
	                    		<font style="font-size: 16px;">Date</font>
	                    	</div>
	                    	<div class="col-md-3">
		                        <input type="text" class="form-control" name="chequeDate" id="single_cal31" placeholder="Date">
	                    	</div>
	                    	<div class="col-md-3" align="right">
	                    		<font style="font-size: 16px;">Amount</font>
	                    	</div>
	                    	<div class="col-md-3">
	                    		<input  class="form-control" name="chequeAmt"  placeholder="Amount" type="number" step="0.01">
	                    	</div>
	                    </div>
	                    
	                <div class="ln_solid"></div>
                    
                    </div> 
                    
                    <div id="debitDetailID" style="margin-top:15px; display: none;">
                    
                    <div class="ln_solid"></div>
                    
	                    <div class="row">
	                    	<div class="col-md-3">
	                    		<font style="font-size: 16px;">Card Number</font>
	                    	</div>
	                    	<div class="col-md-3">
		                        <input type="text" class="form-control" name="paymentCardNo" placeholder="Card Number">
	                    	</div>
	                    </div>
	                    
	                <div class="ln_solid"></div>
                    
                    </div> -->
                    
                    <div class="row" style="margin-top:15px;">
                    	<div class="col-md-3 col-xs-3">
                    		<font style="font-size: 16px;">Receipt Give By</font>
                    	</div>
                    	<div class="col-md-3 col-xs-2">
                    		<font style="font-size: 16px;"><b><%=form.getFullName() %></b></font>
                    	</div>
                    	<div class="col-md-3 col-xs-4" align="right">
                    		<font style="font-size: 16px;">Receivers Signature</font>
                    	</div>
                    	<div class="col-md-3 col-xs-2">
                    		
                    	</div>
                    </div>
                    
                     <div class="ln_solid"></div>
                     
                     <div class="row col-xs-12">
                     	<div class="col-md-3">
                    		
                    	</div>
                    	<div class="col-md-3">
                    		<button type="button" style="width:100%;" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
                    	</div>
                    	<div class="col-md-3">
                    		<button id="send" style="width:100%;" type="submit" name="addReceiptButton" value="Add" class="btn btn-success ">Add Stock</button>
                    	</div>
                    	<!-- <div class="col-md-2">
                    		<button id="send1" style="width:100%;" type="submit" name="addReceiptButton" value="Print" disabled="disabled" class="btn btn-warning ">Print Stock</button>
                    	</div>  -->
                    	<div class="col-md-3">
                    		
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
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    

    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- /Datatables -->
    
    <!-- bootstrap-daterangepicker -->

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
          console.log(start.toISOString(), end.toISOString(), label);
        });

        $('#single_cal31').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
        $('#expiryDateID').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
      });
    }
    </script>

    <!-- /bootstrap-daterangepicker -->
    
  </body>
</html>