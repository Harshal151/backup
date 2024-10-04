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

    <title>Remove Stock | E-Dhanvantari</title>
    
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
    	//HashMap<Integer, String> productList = managementDAOInf.retrieveProductList(form.getPracticeID());
    
    	//HashMap<Integer, String> taxList = productDAOInf.retrieveProductTax();
	    
	    //List<String> otherChargesList = billingDAOInf.retrieveOtherChargesList(form.getBloodBankID());
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
						
						//document.getElementById("VATID").value = "";
						
						//document.getElementById("inStockID").value = "";
						
						//document.getElementById("productQuantityID").value = "1";
						
						//document.getElementById("productAmountID").value = "";
						
					}else{
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							//document.getElementById("productRateID").value = array.Release[i].productRate;

							document.getElementById("barcodeID").value = array.Release[i].productBarcode;
							
							//document.getElementById("VATID").value = array.Release[i].productVAT;
							
							//document.getElementById("inStockID").value = array.Release[i].productNetStock;
							
							//rate = array.Release[i].productRate;
						}

						//var quantity = document.getElementById("productQuantityID").value;

						//var productAmt = parseFloat(parseFloat(quantity) * parseFloat(rate));

						//document.getElementById("productAmountID").value = parseFloat(productAmt);
						
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
				"<td>"+productName+"<input type='hidden' name='productCompID' value='"+product+"'></td>"+
				"<td>"+barcode+"<input type='hidden' name='productCompBarcode' value='"+barcode+"'></td>"+
				"<td>"+expiryDate+"<input type='hidden' name='productExpiryDate' value='"+expiryDate+"'></td>"+
				"<td>"+costPrice+"<input type='hidden' name='productCostPrice' value='"+costPrice+"'></td>"+
				"<td>"+sellingPrice+"<input type='hidden' name='productSellingPrice' value='"+sellingPrice+"'></td>"+
				"<td>"+array[taxInclusive]+"<input type='hidden' name='productTaxInclusive' value='"+taxInclusive+"'></td>"+
				"<td>"+taxName+"<input type='hidden' name='productTaxName' value='"+taxName+"'></td>"+
				"<td>"+taxPercent+"<input type='hidden' name='productTaxPercent' value='"+taxPercent+"'></td>"+
				"<td>"+quantity+"<input type='hidden' name='productQuantity' value='"+quantity+"'></td>"+
				"<td id="+amtTDIT+">"+amount+"<input type='hidden' name='productAmount' value='"+amount+"'></td>"+
				"<td id="+delImgTDID+"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Delete row' title='Delete row' onclick='removeTR(\"" + trID +"\",\""+counter+"\",\""+tdID+"\",\""+amount+"\",\""+finalVAT+"\");'/></td>"+
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
    
    
    
    
    <!-- Retrieving clinic LIst -->
    
    <%
    
    	HashMap<Integer, String> clinicMap = daoInf.retrieveClinicList(form.getPracticeID());
    
    %>
    
    <!-- Ends -->
    
    <!-- Retrievig product tax name list -->
    
    <%
    
    	List<String> taxList = managementDAOInf.retrieveproducttaxList();
    
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    	function selectRow(checkboxID, quantityToBeRemoveID, reasonID, stockID){
    		
    		var reason = $("#"+reasonID).val();
    		
    		//alert(reason);
    		
    		//check whether reason is selected or not, if yes, then only proceed further to 
    		//check the checkbox and add name attributes else uncheck the checkbox and 
    		//remove name properties
    		if(reason != ""){
    			
    			if($('#'+checkboxID+'').is(':checked')){
        			
        			$("#"+quantityToBeRemoveID).attr("name","productQuantity");
        			
        			$("#"+reasonID).attr("name","removeReason");
        			
        			$("#"+stockID).attr("name","productStockID");
        			
        		}else{
        			
    				$("#"+quantityToBeRemoveID).removeAttr("name");
        			
        			$("#"+reasonID).removeAttr("name");
        			
        			$("#"+stockID).removeAttr("name");
        			
        		}
    			
    		}else{
    			
    			alert("Reason not selected. Please select reason.");
    			
    			$('#'+checkboxID+'').prop("checked", false);
    			
    			$("#"+quantityToBeRemoveID).removeAttr("name");
    			
    			$("#"+reasonID).removeAttr("name");
    			
    			$("#"+stockID).removeAttr("name");
    			
    		}
    		
    	}
    		
    		function disableQuantity(reason, quantityID, netStock){
    			
    			//Check whether reason is Expired or Price changed, if so, then disable
    			//quantity text box, else enable quantity 
    			if(reason == "Remove-Expired" || reason == "Remove-Price Changed"){
    				$("#"+quantityID).attr("readonly",true);
    				$("#"+quantityID).val(netStock);
    			}else{
    				$("#"+quantityID).attr("readonly",false);
    				$("#"+quantityID).val("");
   
    			}
    			
    		}
    		
    	function verifyNetStock(quantityToBeRemove, netStock, quantityID, reasonID){
    		if(quantityToBeRemove == ""){
    			quantityToBeRemove = 0;
    		}
    		
    		var reason = $("#"+reasonID).val();
    		
    		if(reason == ""){
    			$("#"+quantityID).val("");
    			
    			alert("Reason not selected. Please select reason first.");
    			
    		}else{
    			if(quantityToBeRemove > netStock){
        			
        			$("#"+quantityID).val("");
        			
        			alert("Quantity to be removed should not be greater than available net stock for the product.");
        		}
    		}
    		
    	}
    			
    
    </script>
    
    
    <%
    	String searchCheck = (String) request.getAttribute("searchCheck");
    
    	if(searchCheck == null || searchCheck == ""){
    		searchCheck = "dummy";	
    	}
    %>
    
  </head>

  <body class="nav-md" id="myID" >
  
  
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
                        <h3>REMOVE STOCK</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                     
                  <div class="x_content">
		              
		              <form action="SearchRemoveStock" method="POST" onsubmit="return showLoadingImg();"> 
		              	<div class="row">
		             		<div class="col-md-3">
		             			<s:select list="productCategoryList" class="form-control" name="productID" id="productID" headerKey="000" headerValue="Select Product"></s:select>
		             		</div>
		             		<div class="col-md-2">
		             			<input type="submit" class="btn btn-primary" value="Search Stocks">
		             		</div>
		             		<div class="col-md-2">
		             			<input type="button" class="btn btn-default" value="Cancel" onclick="windowOpen();">
		             		</div>
		             	</div>
		              </form>
		              
		              <%
		              	if(searchCheck.equals("Found")){
		              %>
		              
		              <form action="RemoveStock" class="form-horizontal" method="POST" onsubmit="return showLoadingImg();">
                     
                      <div class="ln_solid"></div>
                      
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
                        <tr style="font-size: 14px;">
                          <th>Sr.No.</th>
                          <th>Product</th>
                          <th>Stock Receipt No.</th>
                          <th>Stock Receipt Date</th>
                          <th>Net Stock</th>
                          <th>Cost Price</th>
                          <th>Selling Price</th>
                          <th>Reason</th>
                          <th>Stock To Be Removed</th>
                          <th>Select</th>
                        </tr>
                      </thead>
                      <tbody>   
                      	<s:iterator value="productList">
                      	
                      		<tr style="font-size: 14px;">
                      			<td style="text-align: center;"><s:property value="srNo"/> </td>
                      			
                      			<td><s:property value="productName"/> <input type="hidden" name="" id="removeStockID<s:property value="srNo"/>" value="<s:property value="stockID"/>"> </td>
                      			
                      			<td><s:property value="receiptNo"/> </td>
                      			
                      			<td><s:property value="receiptDate"/> </td>
                      			
                      			<td><s:property value="netStock"/> </td>
                      			
                      			<td><s:property value="costPrice"/> </td>
                      			
                      			<td><s:property value="sellingPrice"/> </td>
                      			
                      			<td>
                      				<select class="form-control" id="removeReasonID<s:property value="srNo"/>" name="" onchange="disableQuantity(this.value,'removedQuntityID<s:property value="srNo"/>',<s:property value="netStock"/>);">
                      					<option value="">Select Reason</option>
                      					<option value="Remove-Damaged">Remove-Damaged</option>
                      					<option value="Remove-Expired">Remove-Expired</option>
                      					<option value="Remove-Price Changed">Remove-Price Changed</option>
                      				</select> 
                      			</td>
                      			
                      			<td><input type="text" class="form-control" name="" id="removedQuntityID<s:property value="srNo"/>" value="" onkeyup="verifyNetStock(this.value,<s:property value="netStock"/>, this.id, 'removeReasonID<s:property value="srNo"/>');"> </td>
                      			
                      			<td style="text-align: center;">
                      				<input type="checkbox" name="myCheckbox" id="removeCheckID<s:property value="srNo"/>" style="zoom: 1.5" onclick="selectRow(this.id, 'removedQuntityID<s:property value="srNo"/>','removeReasonID<s:property value="srNo"/>','removeStockID<s:property value="srNo"/>');">
                      			</td>
                      		</tr>
                      			
                      	</s:iterator>                     	
                        	
                      </tbody>
                    </table>
	                    
	                   <div class="ln_solid"></div>
                     
                     <div class="row">
                     	<div class="col-md-3">
                    		
                    	</div>
                    	<div class="col-md-3">
                    		<button type="button" style="width:100%;" onclick="windowOpen();" class="btn btn-default">Cancel</button>
                    	</div>
                    	<div class="col-md-3">
                    		<button id="send" style="width:100%;" type="submit" name="addReceiptButton" value="Add"  class="btn btn-success" >Confirm</button>
                    	</div>
                    	<!-- <div class="col-md-2">
                    		<button id="send1" style="width:100%;" type="submit" name="addReceiptButton" value="Print" disabled="disabled" class="btn btn-warning ">Print Stock</button>
                    	</div>  -->
                    	<div class="col-md-3">
                    		
                    	</div>
                     </div>
                    
                    </form>
		              
		              <%
		              	}else{
		              %>
		              
		              <div class="ln_solid"></div>
		              
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

 <script>
    document.addEventListener('DOMContentLoaded', function() {
        
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
      });
    </script>
    
    
  </body>
</html>