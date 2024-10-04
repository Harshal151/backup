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
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>Edit Existing OPD Visit | E-Dhanvantari</title>

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
      
      function windowOpen1(patientID){
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
          document.location="RenderViewVisit?patientID="+patientID;
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
	
	<style type="text/css">
		#labReportDivID{
			padding-bottom: 10px;
		}
	</style>
	    
	     <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
	
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
		
		ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		
		String fullName = form.getFullName();
	
		LinkedHashMap<String, String> frequencyList = configXMLUtil1.getFrequencySortedListList(form.getPracticeID());
		
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
	
	<%
		String disableButtonCheck = (String) request.getAttribute("disableButtonCheck");
	
		if(disableButtonCheck == null || disableButtonCheck == ""){
			disableButtonCheck = "enable";
		}
	%>
	
	
	<%
		if(disableButtonCheck.equals("disable")){
	%>
	
	<script type="text/javascript">
	
	  document.addEventListener('DOMContentLoaded', function() {
		  $("#viewLastBtnID").prop("disabled",true);
	  });
	  
	</script>
	
	<%
		}
	%>
	
	
	<%
		String medicalCertiText = (String) request.getAttribute("medicalCertiText");
		String medicalCertiCheck = (String) request.getAttribute("medicalCertiCheck");
	
	%>
	
	
	<%
		String referralLetterText = (String) request.getAttribute("referralLetter");
		String referralLetterCheck = (String) request.getAttribute("referralLetterCheck");
	%>
	
	<!-- Print prescription and billing -->
	
	<script type="text/javascript">
		function CheckDiagnosisField(){
			
			var diag = $("#cancerType").val();
			
			if(diag == null || diag ==""){
    			alert("Please add diagnosis field");
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
				document.forms["OPDForm"].action = "UpdateOPDVisit";
				document.forms["OPDForm"].submit();
	   			return true;
			}
			 
	   	 }
		
		function printOPDVisit(){
			
			var diag = $("#cancerType").val();
			
			if(diag == null || diag ==""){
    			alert("Please add diagnosis field");
			}else{
				
				document.forms["OPDForm"].action = "UpdatePrintOPDVisit";
				document.forms["OPDForm"].submit();
	   			return true;
			}
		}
	
		function printOptician(){
			
			document.forms["genOpticianID"].action = "PrintUpdateOptician";
			document.forms["genOpticianID"].submit();
		}
	
		function updatePrescription(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			
			document.forms["genPhyPrescID"].action = "EditPrescription";
			document.forms["genPhyPrescID"].submit();
			// $("#savePrescID").attr("disabled", "disabled");
		}
		
		function printBilling1(){
			document.forms["OPDBILLForm"].action = "UpdatePrintGenPhyBilling";
			document.forms["OPDBILLForm"].submit();
		}
		
	</script>
	
	<!-- Ends -->
	
	<!-- Add more file function for lab report -->
	
	<script type="text/javascript">
		function myFunction() {
			var para = document.createElement("DIV");
			para.setAttribute("id", "labReportDivID");
			var t = document.createElement("INPUT");
			t.setAttribute("type", "file");
			t.setAttribute("name", "labReport");
			para.appendChild(t);
			document.getElementById("myID").appendChild(para);
		}
	</script>
	
	<!-- Ends -->
	
	<%

		String opticianFloatCheck = (String) request.getAttribute("opticianFloatCheck");
		if(opticianFloatCheck == null || opticianFloatCheck == ""){
			opticianFloatCheck = "";
		}
	
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
    
	int apptID = (Integer) request.getAttribute("apptID");
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
	
    %>
    <!-- Ends -->
    
    <script type="text/javascript">
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
	

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
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
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
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}

    	}
		
		function checkCheckbox() {
			
			var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			
			var clicked;
			if(checkboxLength>0){
				
				$('.checkboxClass').prop("required", false);
				document.forms["OPDBILLForm"].action = "EditBill";
				document.forms["OPDBILLForm"].submit();
	   			return true;
			}else{
				$('.checkboxClass').prop("required", true);
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
    
    <% String paymentType = (String) request.getAttribute("paymentType");
		
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
		  var medicalText = "";
		  
		  if($("#medicalCertiText").length){
			  medicalText =document.getElementById("medicalCertiText").value;
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
			
			    if(companyName.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        companyName = companyName.replace(regExp, "_");
			    }else{
			    	companyName = companyName;
			    }
			
			    if(disease.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        disease = disease.replace(regExp, "_");
			    }else{
			    	disease = disease;
			    }
			
			    if(place.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        place = place.replace(regExp, "_");
			    }else{
			    	place = place;
			    }
			
			    if(wef.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        wef = wef.replace(regExp, "_");
			    }else{
			    	wef = wef;
			    }
			    
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
			  $('#vision1').prop('required',false);
			  $('#fundusRE').prop('required',false);
			  $('#fundusLE').prop('required',false);
			  $('#surgery').prop('required',false);
			  $('#eye').prop('required',false);
			  $('#expert').prop('required',false);
			  });
		  
  		  var refLetText = "";
		  
		  if($("#refLetterText").length){
			  refLetText =document.getElementById("refLetterText").value; 
		  }else{
			  refLetText = "";
		  }
			
		  if(refLetText == ""){
			  
			    var doctName = document.getElementById("doctName").value;
			   var doctorName = document.getElementById("doctrNameRef").value;
			    var patientName = document.getElementById("patientNameRef").value;
			    var visitDte = document.getElementById("date12").value;
			    var vision1 = document.getElementById("vision1").value;
			    var fundusRE = document.getElementById("fundusRE").value;
			    var fundusLE = document.getElementById("fundusLE").value;
			    var surgery = document.getElementById("surgery").value;
			    var eye = document.getElementById("eye").value;
			    var age = document.getElementById("ageRef").value;
			    var expert = document.getElementById("expert").value;
			    var visualAcuityDistOD = document.getElementById("visualAcuityDistODRef").value;
			    var visualAcuityDistOS = document.getElementById("visualAcuityDistOSRef").value;
			    var visualAcuityNearOD = document.getElementById("visualAcuityNearODRef").value;
			    var visualAcuityNearOS = document.getElementById("visualAcuityNearOSRef").value;
			
			    if(doctName.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			    	doctName = doctName.replace(regExp, "_");
			    }else{
			    	doctName = doctName;
			    }
			
			    var referralLetter = doctName+ "$" +visitDte+ "$" +vision1+ "$" +fundusRE+ "$" +fundusLE+ "$" +surgery+ "$" +eye+ "$" +expert+ "$" +doctorName;
				
			    var referralLetterForPDF = "Date: "+visitDte+ " \n\n To,\n\n Dr. '"+ doctName + "@doctName', \n\n Respected sir,\n\n Mr./Mrs./Mis.  "+patientName+ "  age " +age+ " is examined for ophthalmic examination. His visual acuity is "+visualAcuityDistOD+" in RE and "+visualAcuityDistOS+" in LE for distance and "+visualAcuityNearOD+" in RE and "+visualAcuityNearOS+" in LE. His colour vision is "+vision1+". His fundus examination has found "+fundusRE+" in RE and "+fundusLE+" In LE. He is advised to undergo "+surgery+" Surgery in "+eye+" eye. \n\n Sending hear with for "+expert+" and your expert opinion and further treatment accordingly. \n\n Please do the needful.";
			
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
	
	<script type="text/javascript">
	
		function confirmBillDelete(billID,visitID){
			if (confirm("Are you sure you want to delete bill?")) {
				deleteBilling(billID, visitID);
			}
		}
	
	</script>

	
	<!-- Fetch old values of Optician -->
	
	<script type="text/javascript">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function fetchOldGlassPreviousValues(previousVisitID) {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					var check = 0;
					var errMsg = "";
					for ( var i = 0; i < array.Release.length; i++) {
						check = array.Release[i].check;
						errMsg=array.Release[i].ExceptionMessage
						if(check == 1){
							document.getElementById("oldSphDiskOD").value = array.Release[i].oldSphDiskOD;
							document.getElementById("oldCylDiskOD").value = array.Release[i].oldCylDiskOD;
							document.getElementById("oldAxisDiskOD").value = array.Release[i].oldAxisDiskOD;
							document.getElementById("oldVnDiskOD").value = array.Release[i].oldVnDiskOD;
							document.getElementById("oldSphDiskOS").value = array.Release[i].oldSphDiskOS;
							document.getElementById("oldCylDiskOS").value = array.Release[i].oldCylDiskOS;
							document.getElementById("oldAxisDiskOS").value = array.Release[i].oldAxisDiskOS;
							document.getElementById("oldVnDiskOS").value = array.Release[i].oldVnDiskOS;
							document.getElementById("oldSphNearOD").value = array.Release[i].oldSphNearOD;
							document.getElementById("oldCylNearOD").value = array.Release[i].oldCylNearOD;
							document.getElementById("oldAxisNearOD").value = array.Release[i].oldAxisNearOD;
							document.getElementById("oldVnNearOD").value = array.Release[i].oldVnNearOD;
							document.getElementById("oldSphNearOS").value = array.Release[i].oldSphNearOS;
							document.getElementById("oldCylNearOS").value = array.Release[i].oldCylNearOS;
							document.getElementById("oldAxisNearOS").value = array.Release[i].oldAxisNearOS;
							document.getElementById("oldVnNearOS").value = array.Release[i].oldVnNearOS;
						}
					}
					if(check == 0){
						
						document.getElementById("exceptionMSG").innerHTML = "No previous record found for Old Glasses. Please add new record.";
						
						if(document.getElementById("errorMSG").innerHTML == "undefined"){
							document.getElementById("errorMSG").innerHTML = "";
						}
	
						if(document.getElementById("exceptionMSG").innerHTML == "undefined"){
							document.getElementById("exceptionMSG").innerHTML = "";
						}
						
						document.getElementById("oldSphDiskOD").value = "--";
						document.getElementById("oldCylDiskOD").value = "--";
						document.getElementById("oldAxisDiskOD").value = "--";
						document.getElementById("oldVnDiskOD").value = "--";
						document.getElementById("oldSphDiskOS").value = "--";
						document.getElementById("oldCylDiskOS").value = "--";
						document.getElementById("oldAxisDiskOS").value = "--";
						document.getElementById("oldVnDiskOS").value = "--";
						document.getElementById("oldSphNearOD").value = "--";
						document.getElementById("oldCylNearOD").value = "--";
						document.getElementById("oldAxisNearOD").value = "--";
						document.getElementById("oldVnNearOD").value = "--";
						document.getElementById("oldSphNearOS").value = "--";
						document.getElementById("oldCylNearOS").value = "--";
						document.getElementById("oldAxisNearOS").value = "--";
						document.getElementById("oldVnNearOS").value = "--";
					}
				}
			};
			xmlhttp.open("GET", "FetchOldGlassPreviousValue?visitID="
					+ previousVisitID, true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xmlhttp.send();
		}
	</script>
	
	<!-- Ends -->
	
	
	<%

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = format.format(date);
		
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
		String currentTime = format1.format(date);
		
		String[] timeArray = currentTime.split(":");
		String hour = timeArray[0];
		String minute = timeArray[1];
		int minuteInt = Integer.parseInt(minute);
		int hourInteger = Integer.parseInt(hour);
		String ampm = "am";
		
		if(hourInteger > 12){
			ampm = "pm";
			hour = String.valueOf(hourInteger - 12);
		}else if(hourInteger == 12){
			ampm = "pm";
		}
		
		if((minuteInt >= 0) && (minuteInt < 5)){
			
			minute = "0";
			
		}else if((minuteInt >= 5) && (minuteInt < 10)){
			
			minute = "5";
			
		}else if((minuteInt >= 10) && (minuteInt < 15)){
			
			minute = "10";
			
		}else if((minuteInt >= 15) && (minuteInt < 20)){
			
			minute = "15";
			
		}else if((minuteInt >= 20) && (minuteInt < 25)){
			
			minute = "20";
			
		}else if((minuteInt >= 25) && (minuteInt < 30)){
			
			minute = "25";
			
		}else if((minuteInt >= 30) && (minuteInt < 35)){
			
			minute = "30";
			
		}else if((minuteInt >= 35) && (minuteInt < 40)){
			
			minute = "35";
			
		}else if((minuteInt >= 40) && (minuteInt < 45)){
			
			minute = "40";
			
		}else if((minuteInt >= 45) && (minuteInt < 50)){
			
			minute = "45";
			
		}else if((minuteInt >= 50) && (minuteInt < 55)){
			
			minute = "50";
			
		}else{
			
			minute = "55";
			
		}
		
		//Retrieving appointment duration from Calendar table based on clinicID
		PatientDAOInf patientDAOInf = new PatientDAOImpl();
		
		String appointmentDuration = patientDAOInf.retrieveApptDurationFromClinicID(form.getClinicID());
		
		int minutesWithDuration = minuteInt + Integer.parseInt(appointmentDuration);
		
		int hourInt = Integer.parseInt(hour);
		
		String toAmPm = ampm;
		
		System.out.println("To Hour:"+String.valueOf(hourInt));
		System.out.println("To Moniute:"+String.valueOf(minutesWithDuration));
		System.out.println("To AMPM:"+toAmPm);
		
		if(minutesWithDuration > 60){
			
			if(hourInt > 12){
	
				minutesWithDuration = minutesWithDuration - 60;
	
				hourInt = hourInt - 12;
	
				toAmPm = "pm";
				
			}else if(hourInt == 12){
	
				minutesWithDuration = minutesWithDuration - 60;
	
				toAmPm = "pm";
				
			}else{
	
				minutesWithDuration = minutesWithDuration - 60;
				
			}
			
		}else if (minutesWithDuration == 60){
			
			if(hourInt > 12){
	
				minutesWithDuration = 0;
	
				hourInt = hourInt - 12;
	
				toAmPm = "pm";
				
			}else if(hourInt == 12){
	
				minutesWithDuration = 0;
	
				toAmPm = "pm";
				
			}else{
	
				minutesWithDuration = 0;
				
			}
			
		}
		
		if((minutesWithDuration >= 0) && (minutesWithDuration < 5)){
			
			minutesWithDuration = 0;
			
		}else if((minutesWithDuration >= 5) && (minutesWithDuration < 10)){
			
			minutesWithDuration = 5;
			
		}else if((minutesWithDuration >= 10) && (minutesWithDuration < 15)){
			
			minutesWithDuration = 10;
			
		}else if((minutesWithDuration >= 15) && (minutesWithDuration < 20)){
			
			minutesWithDuration = 15;
			
		}else if((minutesWithDuration >= 20) && (minutesWithDuration < 25)){
			
			minutesWithDuration = 20;
			
		}else if((minutesWithDuration >= 25) && (minutesWithDuration < 30)){
			
			minutesWithDuration = 25;
			
		}else if((minutesWithDuration >= 30) && (minutesWithDuration < 35)){
			
			minutesWithDuration = 30;
			
		}else if((minutesWithDuration >= 35) && (minutesWithDuration < 40)){
			
			minutesWithDuration = 35;
			
		}else if((minutesWithDuration >= 40) && (minutesWithDuration < 45)){
			
			minutesWithDuration = 40;
			
		}else if((minutesWithDuration >= 45) && (minutesWithDuration < 50)){
			
			minutesWithDuration = 45;
			
		}else if((minutesWithDuration >= 50) && (minutesWithDuration < 55)){
			
			minutesWithDuration = 50;
			
		}else{
			
			minutesWithDuration = 55;
			
		}
		
	%>
	
	
	<%
		String appointmentCheck = (String) request.getAttribute("appointmentCheck");
		if(appointmentCheck == null || appointmentCheck == ""){
			appointmentCheck = "";
		}else{
			
	%>
	
	<script type="text/javascript">
		document.addEventListener('DOMContentLoaded', function() {
	
			//setting values for visit from time
			$("#fromHH").val(<%=hour%>);
			$("#fromMM").val(<%=minute%>);
			$("#fromMMAMPM").val('<%=ampm%>');
	
			//setting values for visit to time
			$("#toHH").val(<%=String.valueOf(hourInt)%>);
			$("#toMM").val(<%=String.valueOf(minutesWithDuration)%>);
			$("#toMMAMPM").val('<%=toAmPm%>');
		});
	</script>
	
	<%
		}
	%>
	
	<!-- Retrieve tabs by practice ID -->
    
    <%
    	HashMap<String, String> ValueMap = new HashMap<String, String>();
    
	    ValueMap.put("clinician","OPD Visit,Prescription,Optician,Billing,Report,Medical Certificate,Referral Letter");
	    ValueMap.put("superAdmin","OPD Visit,Prescription,Optician,Billing,Report,Medical Certificate,Referral Letter");
	    ValueMap.put("billDesk","Prescription,Billing");
	    ValueMap.put("compounder","Prescription,Billing");
	    ValueMap.put("optician","Optician");
    
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
			idMap.put("Optician", "optician");
			idMap.put("Billing", "bill");
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
	
		String opticianTabEnable = (String) request.getAttribute("opticianTabEnable");
	
		if(opticianTabEnable == null || opticianTabEnable == ""){
			opticianTabEnable = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#optician"]').tab('show');
			
			<%	
			//Removing class active to the first value in tab String
			if(tabs.contains(",")){
				String ID = tabs.split(",")[0];
		%>
			$("#<%=idMap.get(ID)%>").removeClass('active');
		<%
				
			}else{
				
		%>
			<%-- $("#<%=idMap.get(tabs)%>").removeClass('active'); --%>
		<%
				
			}
	    
	    %>
	
	});
	</script>
	
	<%
		}
	%>
	
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
	
		String prescTabCheck = (String) request.getAttribute("prescTabCheck");
	
		if(prescTabCheck == null || prescTabCheck == ""){
			prescTabCheck = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
			$('.span_11 a[href="#presc"]').tab('show');
			
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
	
			$('.span_11 a[href="#bill"]').tab('show');
			
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
    
    <!-- Add prescription -->
    <script type="text/javascript" charset="UTF-8">
   
		function addOPDPrescription(drugName, category, frequency1, visitID, comment, noOfPills,noOfDays){
			
			var frequency2 = [];
			$('input[name="frequency[]"]').each(function(){
				frequency2.push($(this).val());
		    });
		   
		    var noOfDays1 = [];
		    
		    $('input[name="totalDays[]"]').each(function(){
		    	noOfDays1.push($(this).val());
		    });
		    
		 
		 if(drugName == null || drugName.trim() === ''){
				alert("Please select drug name");
			}else if(noOfDays == ""){
				alert("Please insert no. of days");
			}else if(frequency1 == ""){
				alert("Please add frequency details");
			}else{
				addOPDPrescription1(drugName, category, frequency2, visitID, comment, noOfPills,noOfDays1)
			}
			
		}
		
		var prescCounter = 1;

		function addOPDPrescription1(drugName, category, frequency1, visitID, comment, noOfPills,noOfDays){
			
			var categoryName = "";
			
			if(category == ''){
				categoryName = '';
			}else{
				categoryName = $("#category1 option:selected").text().trim();
			}
			
			var frequency2 = frequency1.join();
			var frequency3 = frequency2.replace(',','<br>');
			
			var noOfDays2 = noOfDays.join();
			var noOfDays3 = noOfDays2.replace(',','<br>');
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+drugName+"<input type='hidden' name='newDrugID' value='"+drugName+"'><input type='hidden' name='newDrugCategoryID' value='"+category+"'></td>"
					   +"<td style='text-align:center'>"+categoryName+"</td>"
					   +"<td style='text-align:center'>"+frequency3+"<input type='hidden' name='newDrugFrequency' value='"+frequency1+"'></td>"
					   +"<td style='text-align:center'>"+noOfDays3+"<input type='hidden' name='newDrugNoOfDays' value='"+noOfDays+"'></td>"
					   +"<td style='text-align:center'>"+noOfPills+"<input type='hidden' name='newDrugQuantity' value='"+noOfPills+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			//$(trTag).insertAfter($("#prescTRID"));
			$(".addedFrequency tbody").append(trTag);
			prescCounter++;
			
			$("#addFrequency").empty();
			$("#noOfDays").empty();
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
	
	<!-- add more for Prescription Frequency -->
<script type="text/javascript">
	
	function closeModal()
	{
		$(".freqRow").remove();
		$("#frequencyModal").modal('hide');	
	}

	function addPrescriptionFrequency(frequency, noOfDays){
		
		if(frequency == "-1"){
			alert("Please select frequency");
		}else if(noOfDays == ""){
			alert("Please add number of days");
		}else{
			addPrescriptionFrequency1(frequency, noOfDays);
		}
		
	}
	
	var frequencyCounter = 1;

	function addPrescriptionFrequency1(frequency, noOfDays){
		
		var TRID = "newPrescTRID"+frequencyCounter;
		
		var frequencyString = $("#FrequencyValueID").val(); 
		
		frequencyStringVal =","+frequency+"$"+noOfDays;
		
		var trTag = "<tr style='font-size: 14px;' class='freqRow' id='"+TRID+"'>"
				   +"<td style='text-align:center'>"+frequency+"<input type='hidden' name='newFrequency' value='"+frequency+"'></td>"
				   +"<td style='text-align:center'>"+noOfDays+"<input type='hidden' name='newNoOfDays' value='"+noOfDays+"'></td>"
				   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeFrequencyTR(\""+TRID+"\", \""+frequencyStringVal+"\");'/></td>"
				   +"</tr>";
		
		frequencyString += frequencyStringVal;
		
		if(frequencyString.startsWith(',')){
			frequencyString = frequencyString.substr(1);
		}
		
		$("#FrequencyValueID").val(frequencyString);
		
		$(trTag).insertAfter($("#frequencyTRID"));
		
		frequencyCounter++;
		
		$("#noOfDaysID").val("");
		$("#frequency").val("-1");
		
		$("#addFrequency").append("<tr class ="+TRID+"><td style='padding: 10px 0px 10px 0px;'>"+frequency+"<input type='hidden' class='form-control' name='frequency[]' id='frequency1' value='"+frequency+"'></td></tr>");
		$("#noOfDays").append("<tr class ="+TRID+"><td style='padding-bottom: 5px;'><input type='text' class='form-control' name='totalDays[]' id='noOfDays1' value='"+noOfDays+"'></td></tr>");
	}
	
	function removeFrequencyTR(TRID, stringToBeRemoved){
				
		if(confirm("Are you sure you want to delete this row?")){
			
			var mainStringText = $("#FrequencyValueID").val(); 
			
    		var newValue = mainStringText.replace(stringToBeRemoved,'');
        	
    		$("#FrequencyValueID").val(newValue);
    		
			$("#"+TRID).remove();
			$("."+TRID).remove();
		}		
	}
	
</script>
<!-- End -->

<!-- Saving frequencyDetails -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function SaveFrequency() {
	var values = $("#frequencyEditModalID").serialize();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var excptnMsg = "";

				for ( var i = 0; i < array.Release.length; i++) {
				
					excptnMsg = array.Release[i].ErrMsg;
					check = array.Release[i].check;
				}
				
				if(check == 0){
					document.getElementById("exceptionMSG").innerHTML = excptnMsg;
				}else{
					alert("Frequency details inserted successfully.");
				}
				
			}
		};
		xmlhttp.open("GET", "AddFrequency?"+values, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
</script>
<!-- Ends -->

<!-- Frequency edit add more function -->
<script type="text/javascript">

	function editPrescriptionFrequency(editFrequency, editNoOfDays){
		
		if(editFrequency == "-1"){
			alert("Please select frequency");
		}else if(editNoOfDays == ""){
			alert("Please add number of days");
		}else{
			editPrescriptionFrequency1(editFrequency, editNoOfDays);
		}
		
	}
	
	var frequencyEditCounter = 1;
	
	function editPrescriptionFrequency1(editFrequency, editNoOfDays){
		
		var TRID = "newPrescTRID"+frequencyEditCounter;
		
		var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
				   +"<td style='text-align:center'>"+editFrequency+"<input type='hidden' name='newEditFrequency' value='"+editFrequency+"'></td>"
				   +"<td style='text-align:center'>"+editNoOfDays+"<input type='hidden' name='newEditNoOfDays' value='"+editNoOfDays+"'></td>"
				   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeEditFrequencyTR(\""+TRID+"\");'/></td>"
				   +"</tr>";
		
		$(trTag).insertAfter($("#editFrequencyTRID"));
		   
		frequencyEditCounter++;
		
		$("#noOfDaysEditID").val("");
		$("#frequencyEdit").val("-1");
	}
	
	
	function removeEditFrequencyTR(TRID){
		
		if(confirm("Are you sure you want to delete this row?")){
			
			$("#"+TRID).remove();	
		}		
	}
</script>

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
			
			autoSelecter.textInputNode.value = "";
			//autoSelecter.innerHTML = "";
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
	
	<!-- Eyewear calculation -->
	
	<script type="text/javascript">

	  function calculateTotal(){
	    
	    var glassCharge = document.getElementById("glassChargeID").value;
	    
	    var frameCharge = document.getElementById("frameChargeID").value;
	
	    var totalBill12;
	
	    if(glassCharge == "" || frameCharge == ""){
	      if(glassCharge == ""){
	       
	        document.getElementById("totalID").value=frameCharge;
	        document.getElementById("netPaymentID").value=frameCharge;
	        document.getElementById("balanceID").value=frameCharge;
	      }else{
	        
	        document.getElementById("totalID").value=glassCharge;
	        document.getElementById("netPaymentID").value=glassCharge;
	        document.getElementById("balanceID").value=glassCharge;
	      }
	    }else{
	      totalBill12 = parseInt(glassCharge) + parseInt(frameCharge);
	      document.getElementById("totalID").value=totalBill12;
	      document.getElementById("netPaymentID").value=totalBill12;
	      document.getElementById("balanceID").value=totalBill12;
	    }
	    
	  }
	
	  function calculateDiscount(){
	    
	    var total = document.getElementById("totalID").value;
	    
	    var discount = document.getElementById("discountID").value;
	
	    var totalBill12;
	
	    if(discount == ""){
	      document.getElementById("netPaymentID").value=total;
	    }else{
	      totalBill12 = parseInt(total) - parseInt(discount);
	      document.getElementById("netPaymentID").value=totalBill12;
	    }
	
	    calculateBalance();
	    
	  }
	
	  function calculateBalance(){
	    
	    var netPayment = document.getElementById("netPaymentID").value;
	    
	    var advance = document.getElementById("advanceID").value;
	    
	    var totalBill12;
	
	    if(advance == ""){
	      document.getElementById("balanceID").value=netPayment;
	    }else{
	      totalBill12 = parseInt(netPayment) - parseInt(advance);
	      document.getElementById("balanceID").value=totalBill12;
	    }
	    
	  }
	  
	  function checkFrequency(element) {
		    var values = [];
		    
		    //alert(element.options.length);
		    $("#selectdFrq").remove();
		    $("#noOfDays").append("<div id='selectdFrq'>");
		    for (var i = 0; i < element.options.length; i++) {
		      if (element.options[i].selected) {
		    	$("#selectdFrq").append(""+element.options[i].value+"<br><input type='number' class='form-control' name='totalDays[]' id='noOfDays1' placeholder='Days'></div>");
		      }
		    }
		    $("#noOfDays").append("</div>");
		  }
	
	</script>
	
	<!-- Ends -->
		

    
    <%
		String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");
	%>
   
   
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
	 
	<script type="text/javascript">
	function showValues(value, nearID, sphNearID){
		
		if($("#"+sphNearID).val() != "--"){
			$("#"+nearID).val(value);
		}
		
	}
	
	function showAxisDiskValue(value, axisID, sphNearID) {
		
		if($("#"+sphNearID).val() != "--"){
			$("#"+axisID).val(value);
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
  
  <%
    	if(lastEneteredVisitList == "success"){ 
  %>

  	<body class="nav-md" >
  <%
        } else{
  %>

	<body class="nav-md" >
  <%
        }
  %>
  
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
  <!-- Start Frequency Add Modal-->
  
  <div id="frequencyModal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick = "closeModal()">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">Frequency Details</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        		<div class="col-md-12 col-sm-12 col-xs-12" id="frequencyDIvID" align="center">
	        	 	<table id="frequencyTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 70%; margin-left: 1%;">
					    <thead>
							<tr><th style="width: 55%">Frequency</th> <th>No. Of Days</th><th>Action</th></tr>
					    </thead>
					    <tbody>
			        		<tr style="font-size: 14px;" id="frequencyTRID">
							   <td style="text-align:center">
								   <select class="form-control" name="" id="frequency">
								   <option value="-1">Select Frequency</option>
								   		<% for(String frequencyName : frequencyList.keySet()){ %>
								   		
								   		<option value="<%= frequencyName%>"><%=frequencyList.get(frequencyName)%></option>
									   	
									   	<% } %>
								   </select>
							   </td>
							   <td style="text-align:center"><input class="form-control" type="number" id="noOfDaysID" name=""></td>
							   <td style="text-align:center"><a onclick="addPrescriptionFrequency(frequency.value, noOfDaysID.value);" > 
								<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
									alt="Add Frequency" title="Add Frequency" style="margin-top:5px;height: 24px;" /> </a>
							   </td>
						   </tr>
						</tbody>
					</table>
				</div>
        	</div>
        </div>
        <div class="modal-footer" style="text-align: center;">
          <button type="button" class="btn btn-default" id="patientDetailsCloseID" onclick = "closeModal()">Close</button>
       </div>

      </div>
    </div>
  </div>
  
  <!-- END -->
  
  
  <!-- Start Frequency Add Modal-->
  
  <div id="frequencyEditModal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">Frequency Details</h4>
        </div>
        <form method="" action="" id="frequencyEditModalID">
	        <div class="modal-body">
	        
	        	<div class="row">
	        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
	        		<input type="hidden" id ="prescriptionFreqID" name="prescriptionID">
	        		
		        	 	<table id="frequencyEditTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 70%; margin-left: 1%;">
						    <thead>
								<tr><th style="width: 55%">Frequency</th> <th>No. Of Days</th><th>Action</th></tr>
						    </thead>
						    <tbody>
				        		<tr style="font-size: 14px;" id="editFrequencyTRID">
								   <td style="text-align:center">
									   <select class="form-control" name="" id="frequencyEdit">
										   <option value="-1">Select Frequency</option>
										<% for(String frequencyName : frequencyList.keySet()){ %>
										   		
									   		<option value="<%= frequencyName%>"><%=frequencyList.get(frequencyName)%></option>
											   	
									   	<% } %>
									   </select>
								   </td>
								   <td style="text-align:center"><input class="form-control" type="number" id="noOfDaysEditID" name=""></td>
								   <td style="text-align:center"><a onclick="editPrescriptionFrequency(frequencyEdit.value, noOfDaysEditID.value);" > 
									<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
										alt="Add Frequency" title="Add Frequency" style="margin-top:5px;height: 24px;" /> </a>
								   </td>
							   </tr>
							</tbody>
							
							<tbody id="frequencyEditTRID"></tbody>
						</table>
					</div>
	        	</div>
	        </div>
	        <div class="modal-footer" style="text-align: center;">
	          <button type="button" class="btn btn-default" id="patientDetailsCloseID" data-dismiss="modal">Close</button>
	           <button type="submit" class="btn btn-success" id="patientDetailsSaveID" data-dismiss="modal" onclick="SaveFrequency();" >Save</button>
	       </div>
	       
	   </form>
      </div>
    </div>
  </div>
  
  <!-- END -->
  
  <!-- Start Frequency View Modal-->
  
  <div id="frequencyViewModal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">View Frequency Details</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
	        	 	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 70%; margin-left: 1%;">
					    <thead>
							<tr><th style="width: 55%; text-align:center; ">Frequency</th> <th style="text-align:center;">No. Of Days</th></tr>
					    </thead>
					    <tbody id="frequencyViewTRID">
			        		
						</tbody>
					</table>
				</div>
        	</div>
        </div>
        <div class="modal-footer" style="text-align: center;">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       </div>

      </div>
    </div>
  </div>
  
  <!-- END -->
  
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
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" id="profPicID" aria-expanded="false">
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
                        <h3>EDIT EXISTING VISIT</h3>
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
			       
					  <h4 style=""><b>Visit For <s:property value="firstName"/>&nbsp;<s:property value="lastName"/>&nbsp;(<s:property value="medicalRegNo"/>)</b></h4>
		              
		              <div class="ln_solid"></div>
			        
					<ul id="myTab1" class="nav nav-tabs">
			        
			        <%
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
		              			<li><a href="#<%=idMap.get(tabs) %>" data-toggle="tab"><%=tabs %></a></li>
		              	<%
		              		}
		              	%>
		             
		             </ul>
				 <div id="myTabContent1" class="tab-content">
				 
				<!-- Visit div -->
			    <div class="tab-pane fade" id="visit">
			    
				    <form class="form-horizontal form-label-left" novalidate id="OPDForm" name="K" action="UpdateOPDVisit" method="POST" style="margin-top:20px;">
						
						<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         	<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			          	<input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>"> 
			          	<input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
			         	<input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          	<input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
				      	<input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				         
				    	 <div class="row">
								<div class="col-md-6">
									<button type="button" class="btn btn-warning" id="viewLastBtnID" onclick="viewPopUp('ViewLastOPDVisitDetails?patientID='+<s:property value="patientID"/>+'&visitID='+<s:property value="visitID"/>);">View Last Visit</button>
								</div>
								<div class="col-md-6"></div>
						  </div>
						  
						  
						<div class="row" style="margin-top:15px;">
						
					    	<div class="col-md-6 col-xs-12">
					      
						    	<div class="col-md-4 col-xs-6">
						    	 	<label for="Name" style="font-size:14px;">Patient Name(ID)</label>
						    	</div>
						    	
						    	<div class="col-md-8 col-xs-6">
						      		<input type="text" class="form-control" readonly="readonly" value="<s:property value="firstName"/> <s:property value="middelName"/> <s:property value="lastName"/> ( <s:property value="patientID"/> )"  name="" placeholder="Patient Name">
						    	</div>
					    	
					     	</div>
					
					    	<div class="col-md-6 col-xs-12">
					    
					    		<div class="col-md-4 col-xs-6">
					      			<label for="Name" style="font-size:14px;">Date</label>
					   		 	</div>
					    	
					    		<div class="col-md-8 col-xs-6">
					    			<input type="text" class="form-control" readonly="readonly" name="visitDate" value="<s:property value="visitDate"/>" id="single_cal3" placeholder="Visit Date" aria-describedby="inputSuccess2Status3">
					    		</div>
					    
					    	</div>
					    
					    </div>
					
					    <div class="row" style="margin-top:15px;">
					    
					    	<div class="col-md-6 col-xs-12">
					      
						    	<div class="col-md-4 col-xs-6">
						    	 	<label for="Name" style="font-size:14px;">Age</label>
						    	</div>
						    	
						    	<div class="col-md-8 col-xs-6">
						      		<input type="text"  class="form-control" readonly="readonly" value="<s:property value="age"/>"  name="age" placeholder="Age">
						    	</div>
					    	
					     	</div>
					
					    	<div class="col-md-6 col-xs-12">
					    
					    		<div class="col-md-4 col-xs-6">
					      			<label for="Name" style="font-size:14px;">Gender</label>
					   		 	</div>
					    	
					    		<div class="col-md-8 col-xs-6">
					     			<input type="text" class="form-control"  readonly="readonly" value="<s:property value="gender"/>"  name="gender" placeholder="Gender">
					    		</div>
					    
					    	</div>
					    	
					    </div>
					    
					    <div class="row" style="margin-top:15px;">
					    
					    	<div class="col-md-6 col-xs-12 search-container">
					      
						    	<div class="col-md-4 col-xs-6">
						    	 	<label for="Name" style="font-size:14px;">Diagnosis*</label>
						    	</div>
						    	
						    	<div class="col-md-8 col-xs-6">
						      		<%-- <sx:autocompleter name="cancerType" cssStyle="padding-left:14px;" list="diagnoseList" showDownArrow="false" id="search" cssClass="form-control"/> --%> 
						      		<s:select list="diagnoseList" headerKey="-1" headerValue="Select Diagnosis" name="cancerType" id="cancerType" class="form-control" style="border-radius: 5px;" required="required">

									</s:select>	
						      		<%-- <input type="text" class="form-control" name="cancerType" id="search" required="required" value="<s:property value="cancerType"/>" placeholder="Diagnosis"> --%>
						    	</div>
					    	
					     	</div>
					
					    	<div class="col-md-6 col-xs-12">
					    
					    		<div class="col-md-4 col-xs-6">
					      			<label for="Name" style="font-size:14px;">Visit Type</label>
					   		 	</div>
					    	
					    		<div class="col-md-8 col-xs-6">
						      		<input type="text" class="form-control" readonly="readonly" value="<s:property value="visitType"/> " name="" placeholder="Visit Type">
						    	</div>
					    
					    	</div>
					    	
					    </div>
					
					
					    <div class="row" style="margin-top:15px;">
					    	
					    	<div class="col-md-12 col-xs-12">
					   
					    		<div class="col-md-2 col-xs-6">
					      			<label for="Name" style=" margin-top:10px;font-size:14px;">Medical Note</label>
					    		</div>
					    		
					    		<div class="col-md-10 col-xs-6" ">
					      			<s:textarea name="medicalNotes"  class="form-control" placeholder="Medical Notes"></s:textarea>
					    		</div>
					    	
					    	</div>
					    
					    </div>
					    
					    <div class="ln_solid"></div>
					    
					    <div class="row" >
                      
					    <div class="col-md-4 col-sm-4" align="left">
					    
						    <div class="col-md-12">
						      <label for="On Exam" style=" margin-top:10px;font-size:16px;"><b>On Exam</b></label>
						    </div>
					    
					    </div>
					
					    <div class="col-md-4 col-sm-4" align="left" > 
					    
						    <div class="col-md-12">
						      <label for="OD (RE)" style=" margin-top:10px;font-size:16px;"><b>OD (RE)</b></label>
						    </div>
					    
					    </div>
					
					    <div class="col-md-4 col-sm-4" align="left">
						    <div class="col-md-12 col-sm-12">
						      <label for="OD (RE)" style=" margin-top:10px;font-size:16px;"><b>OS (LE)</b></label>
						    </div>
					    </div>
					    
					  </div>
					  
					  <div class="row">
					  
					       <div class="col-md-4 col-sm-4" align="left">
					      
						  		<div class="col-md-12 col-sm-12">
							     	<a style="" data-toggle="collapse" href="#eyeLid" aria-expanded="false" aria-controls="collapseExample">
						  				<label for="Name" style=" margin-top:10px;font-size:16px;cursor: pointer;"><b>Eye Lid</b></label>
								  	</a>
								</div>
							
					 	   </div>
						
					       <div class="col-md-4 col-sm-4" align="left" > 
					      
					    	  	<div class="col-md-12 col-sm-12">
					      
					    	  	</div>
					    
					       </div>
					
					       <div class="col-md-4 col-sm-4" align="left">
					      	
					      		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    	
					       </div>
					      
					    </div>
					
						<div class="collapse" id="eyeLid" style="margin-top:15px;">
						
						    <div class="row">
						    
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Upper</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    	
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text"  class="form-control"  name="eyeLidUpperOD" value="<s:property value="eyeLidUpperOD"/>" placeholder="Upper OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						    	
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text"  class="form-control"  name="eyeLidUpperOS" value="<s:property value="eyeLidUpperOS"/>" placeholder="Upper OS">
						    		</div>
						    	</div>
						    	
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						    
						      	<div class="col-md-4 col-sm-4" align="left">
						      	
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Lower</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="eyeLidLowerOD" value="<s:property value="eyeLidLowerOD"/>" placeholder="Lower OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="eyeLidLowerOS" value="<s:property value="eyeLidLowerOS"/>" placeholder="Lower OS">
						    		</div>
						    		
						    	</div>
						    	
						    </div>
					    </div>
					    
					    <div class="row">
					    
					      	<div class="col-md-4 col-sm-4" align="left">
					      	
					    		<div class="col-md-12 col-sm-12">
					    		
					      			<a style="text-decoration:none;" data-toggle="collapse" href="#vision" aria-expanded="false" aria-controls="collapseExample">
				  						<label for="Name" style=" margin-top:10px;font-size:16px;cursor: pointer;"><b>Vision</b></label>
									</a>
								
					    		</div>
					
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left" > 
					    		
					    		<div class="col-md-12 col-sm-12 ">
					      
					    		</div>
					    
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left">
					    		
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    	
					    	</div>
					    
					    </div>
					
						<div class="collapse" id="vision">
						
							<div class="row">
							
						      	<div class="col-md-4 col-sm-4 " align="left">
						      	
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;"></label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-5" align="left" > 
						    		
							    	<div class="col-md-6 col-xs-6">
							  			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Dist</b></label>
							    	</div>
							    		
							    	<div class="col-md-6 col-xs-6">
							  			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Near</b></label>
							    	</div>
						    
						    	</div>
						
							    <div class="col-md-4 col-sm-4 col-xs-5" align="left">
							     		
							     	<div class="col-md-6 col-xs-6">
							  			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Dist</b></label>
							    	</div>
							    	
							    	<div class="col-md-6 col-xs-6">
							  			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Near</b></label>
							    	</div>
							    
							    </div>
						    </div>
							
						    <div class="row" style="margin-top:15px;">
						      
						      	<div class="col-md-4 col-sm-4" align="left">
						    
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Visual Acuity</label>
						   			</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 " align="left" > 
						    		
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60', 'No PL':'No PL', 'PL/PR':'PL/PR', 'Faulty PL':'Faulty PL', '1 mtr':'1 mtr', '2 mtr':'2 mtr', '3 mtr':'3 mtr', 'HM +ve':'HM +ve'}" name="visualAcuityDistOD"  class="form-control"   ></s:select>
						    		</div>
						    
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36', 'No PL':'No PL', 'PL/PR':'PL/PR', 'Faulty PL':'Faulty PL', '1 mtr':'1 mtr', '2 mtr':'2 mtr', '3 mtr':'3 mtr', 'HM +ve':'HM +ve'}" name="visualAcuityNearOD"  class="form-control"   ></s:select>
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						   				
						   			<div class="col-md-6">
						  				<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60', 'No PL':'No PL', 'PL/PR':'PL/PR', 'Faulty PL':'Faulty PL', '1 mtr':'1 mtr', '2 mtr':'2 mtr', '3 mtr':'3 mtr', 'HM +ve':'HM +ve'}" name="visualAcuityDistOS"  class="form-control"  ></s:select>
						    		</div>
						    
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36', 'No PL':'No PL', 'PL/PR':'PL/PR', 'Faulty PL':'Faulty PL', '1 mtr':'1 mtr', '2 mtr':'2 mtr', '3 mtr':'3 mtr', 'HM +ve':'HM +ve'}" name="visualAcuityNearOS"  class="form-control"   ></s:select>
						    		</div>
						    	
						    	</div>
						    	
						    </div>
						    
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4" align="left">
						    			
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Pinhole Vision</label>
						   			</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 

						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="pinholeVisionDistOD"  class="form-control"   ></s:select>
								    </div>
						    
								    <div class="col-md-6">
									  	<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="pinholeVisionNearOD"  class="form-control"   ></s:select>
						    		</div>
						    
						    	</div>
						
							    <div class="col-md-4 col-sm-4" align="left">
							   
							   		<div class="col-md-6">
							  			<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="pinholeVisionDistOS"  class="form-control"   ></s:select>
							    	</div>
							    
							    	<div class="col-md-6">
							  			<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="pinholeVisionNearOS"  class="form-control" ></s:select>
							    	</div>
							    
							    </div>
							    
						    </div>
						    
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">BCVA</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60', 'No improvement':'No improvement'}" name="BCVADistOD"  class="form-control"  ></s:select>
						    		</div>
						    
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36', 'No improvement':'No improvement'}" name="BCVANearOD"  class="form-control" ></s:select>
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						   			
						   			<div class="col-md-6">
						  				<s:select list="#{'--':'--','6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60', 'No improvement':'No improvement'}" name="BCVADistOS"  class="form-control"   ></s:select>
						    		</div>
						    
						    		<div class="col-md-6">
						  				<s:select list="#{'--':'--','N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36', 'No improvement':'No improvement'}" name="BCVANearOS"  class="form-control"  ></s:select>
								    </div>
						    	
						    	</div>
						    	
						    </div>
						    
					    </div>
					
					    <div class="row">
					      
					      	<div class="col-md-4 col-sm-4" align="left">
					    		
					    		<div class="col-md-12 col-sm-12" >
					      			<a style="" data-toggle="collapse" href="#anteriorSeg" aria-expanded="false" aria-controls="collapseExample">
				  						<label for="Name" style=" margin-top:10px;font-size:16px;cursor: pointer;"><b>Anterior Segment</b></label>
				  					</a>
					    		</div>
					
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left" > 
					    		
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    
					    	</div>
					
					    		<div class="col-md-4 col-sm-4" align="left">

								    <div class="col-md-12 col-sm-12">
					      
								    </div>
					    	</div>
					    	
					    </div>
					
						<div class="collapse" id="anteriorSeg" style="margin-top:15px;">
						    
						    <div class="row">
						      
						      	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Conjunctiva</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="conjunctivaOD" value="<s:property value="conjunctivaOD"/>" placeholder="Conjunctiva OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control" name="conjunctivaOS" value="<s:property value="conjunctivaOS"/>" placeholder="Conjunctiva OS">
								    </div>
			
							    </div>
			
						    </div>
						 
						 	<div class="row" style="margin-top:10px;">
							 
							 	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
									<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Sclera</label>
						    		</div>							 	
							 	 </div>
							 	 
							 	 <div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control" name="scleraOD" value="<s:property value="scleraOD"/>" placeholder="Sclera OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control" name="scleraOS" value="<s:property value="scleraOS"/>" placeholder="Sclera OS">
								    </div>
			
							    </div>
						    </div>
						    
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Cornea</label>
						    		</div>
						
							    </div>
						
							    <div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
		
								    <div class="col-md-12 col-sm-12">
									  <input type="text" class="form-control" name="CorneaOD" value="<s:property value="CorneaOD"/>" placeholder="Cornea OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="CorneaOS" value="<s:property value="CorneaOS"/>" placeholder="Cornea OS">
						    		</div>
						    
						    	</div>
						    
						    </div>
						 	
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
						    	
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">AC</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
						    	
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="ACOD" value="<s:property value="ACOD"/>" placeholder="AC OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="ACOS" value="<s:property value="ACOS"/>" placeholder="AC OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Iris</label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
						    	
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="irisOD" value="<s:property value="irisOD"/>" placeholder="Iris OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    	
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="irisOS" value="<s:property value="irisOS"/>" placeholder="Iris OS">
						    		</div>
						    	
						    	</div>

						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      
						      	<div class="col-md-4 col-sm-4 col-xs-3" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
								      <label for="Name" style=" margin-top:10px;font-size:14px;">Lens</label>
								    </div>
						
							    </div>
						
							    <div class="col-md-4 col-sm-4 col-xs-6" align="left" > 
								
								    <div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="lensOD" value="<s:property value="lensOD"/>" placeholder="Lens OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="lensOS" value="<s:property value="lensOS"/>" placeholder="Lens OS">
								    </div>
		
							    </div>
			
						    </div>
					    </div>
					    
					    <div class="row">
					      
					      	<div class="col-md-4 col-sm-4" align="left">
					    
					    		<div class="col-md-12 col-sm-12">
					      			<a style="" data-toggle="collapse" href="#posteriorSeg" aria-expanded="false" aria-controls="collapseExample">
				  						<label for="Name" style=" margin-top:10px;font-size:16px;cursor: pointer;"><b>Posterior Segment</b></label>
				  					</a>
					    		</div>
					
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left" > 
					    		
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left">
					    
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    	
					    	</div>
					    
					    </div>
					
						<div class="collapse" id="posteriorSeg" style="margin-top:15px;">
						    
						    <div class="row">
						    
						      	<div class="col-md-4 col-sm-4" align="left">
						    	
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;"><b></b></label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4"> 
						    		
						    		<div class="col-md-6 col-sm-6">
						      			<img src="images/posterio_segment.png" alt="Posterior Segment OD">
						    		</div>
						    	
						    		<div class="col-md-6 col-sm-6">
									    <input type="text" class="form-control"  name="discOD" value="<s:property value="discOD"/>" placeholder="Disc OD">
									    <input type="text" class="form-control"  style="margin-top:10px;" name="vesselOD" value="<s:property value="vesselOD"/>" placeholder="Vessel OD">
									    <input type="text" class="form-control"  style="margin-top:10px;" name="maculaOD" value="<s:property value="maculaOD"/>" placeholder="Macula OD">
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4">
						    		
						    		<div class="col-md-6 col-sm-6">
						        		<img src="images/posterio_segment_1.png" alt="Posterior Segment OS">
						    		</div>
						     
						     		<div class="col-md-6 col-sm-6">
									    <input type="text" class="form-control"  name="discOS" value="<s:property value="discOS"/>" placeholder="Disc OS">
									    <input type="text" class="form-control"  style="margin-top:10px;" name="vesselOS" value="<s:property value="vesselOS"/>" placeholder="Vessel OS">
									    <input type="text" class="form-control"  style="margin-top:10px;"  name="maculaOS" value="<s:property value="maculaOS"/>" placeholder="Macula OS">
						    		</div>
						    	</div>
						    
						    </div>
					    
					    	<div class="col-md-4 col-sm-4" align="left">
						   	</div>
						    	
					    	<div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 10px;">
					    		<s:textarea rows="3" class="form-control" name="posteriorComment" placeholder="Comment"></s:textarea>
					    	</div>
					    </div>
					    
					    <div class="row">
					      
					      	<div class="col-md-4 col-sm-4" align="left">
					    		
					    		<div class="col-md-12 col-sm-12">
					      			<a style="" data-toggle="collapse" href="#biometric" aria-expanded="false" aria-controls="collapseExample">
				  						<label for="Name" style=" margin-top:10px;font-size:16px;cursor: pointer;"><b>I.O.P, Sac. & Biometry</b></label></a>
					    		</div>
					
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left" > 
					    		
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    
					    	</div>
					
					    	<div class="col-md-4 col-sm-4" align="left">
					    		
					    		<div class="col-md-12 col-sm-12">
					      
					    		</div>
					    	</div>
					    	
					    </div>
					
						<div class="collapse" id="biometric" style="margin-top:15px;">
						    <div class="row">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>I.O.P</b></label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="IODOD" value="<s:property value="IODOD"/>" placeholder="I.O.D OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" >
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"  name="IODOS" value="<s:property value="IODOS"/>" placeholder="I.O.D OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Sac</b></label>
						    		</div>
						
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  				<input type="text" class="form-control"  name="sacOD" value="<s:property value="sacOD"/>" placeholder="Sac OD">
						    		</div>
						    
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12" >
						        		<input type="text" class="form-control"  name="sacOS" value="<s:property value="sacOS"/>" placeholder="Sac OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;"><b>Biometry</b></label>
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						  
								    </div>
						    
							    </div>
						
							    <div class="col-md-4 col-sm-4" align="left">
						
								    <div class="col-md-12 col-sm-12">
						        
								    </div>
	
							    </div>
	
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">K1</label>
						    		</div>
						      
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<input type="text"  class="form-control"  name="k1OD" value="<s:property value="k1OD"/>" placeholder="K1 OD">
						    		</div>
						     
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						         		<input type="text"  class="form-control" name="k1OS" value="<s:property value="k1OS"/>" placeholder="K1 OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">K2</label>
						    		</div>
						     
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						       			<input type="text"   class="form-control"  name="k2OD" value="<s:property value="k2OD"/>" placeholder="K2 OD">
						    		</div>
						    	
						    	</div>
						
							    <div class="col-md-4 col-sm-4" align="left">
			
								    <div class="col-md-12 col-sm-12">
								        <input type="text"   class="form-control" name="k2OS" value="<s:property value="k2OS"/>" placeholder="K2 OS">
								    </div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">Axial Length</label>
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-7" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control" name="axialLengthOD" value="<s:property value="axialLengthOD"/>" placeholder="Axial Length OD">
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4 col-xs-7" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text"  class="form-control"  name="axialLengthOS" value="<s:property value="axialLengthOS"/>" placeholder="Axial Length OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
						
						    <div class="row" style="margin-top:10px;">
						      	
						      	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						      			<label for="Name" style=" margin-top:10px;font-size:14px;">IOL</label>
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left" > 
						    		
						    		<div class="col-md-12 col-sm-12">
						        		<input type="text" class="form-control"   name="IOLOD" value="<s:property value="IOLOD"/>" placeholder="IOL OD">
						    		</div>
						    	
						    	</div>
						
						    	<div class="col-md-4 col-sm-4" align="left">
						    		
						    		<div class="col-md-12 col-sm-12">
						         		<input type="text"  class="form-control"  name="IOLOS" value="<s:property value="IOLOS"/>" placeholder="IOL OS">
						    		</div>
						    	
						    	</div>
						    
						    </div>
					    	
					    </div>

						<div class="row" style="margin-top:10px;">
						      	
						    <div class="col-md-4 col-sm-4" align="left">
						    	<div class="col-md-12 col-sm-12">
						      		<label for="Name" style="margin-top:10px;font-size:16px;"><b>Additional Comments</b></label>
						    	</div>
						    </div>
						
						    <div class="col-md-8 col-sm-8 col-xs-12" align="left" > 
						    	<div class="col-md-12 col-sm-12 col-xs-12">
						        	<s:textarea rows="3" class="form-control" name="biometryComment" placeholder="Comment"></s:textarea>
						    	</div>
						    </div>
						</div>
						
	                      <div class="ln_solid"></div>
	                      
	                      <div class="form-group">
	                        <div class="col-md-12" align="center">
	                        <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
	                       <button id="SaveVisitID" type="button" class="btn btn-success" onclick="CheckDiagnosisField();">Update</button>
	                          <button class="btn btn-warning" id="PrintVisitID" type="button"  onclick="printOPDVisit();">Update & Print</button>
	                          
	                        </div>
	                      </div>
				    
				    </form>
				        
			      </div>
			     <!-- End -->
			     
			     
			     <!-- Prescription div -->
			      <div class="tab-pane fade" id="presc" >
			        <form class="form-horizontal form-label-left" novalidate id="genPhyPrescID" name="genPhyPrescID" onsubmit="return updatePrescription();" action="EditPrescription" method="POST" style="margin-top:20px;">
				
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>"> 
				      <input type="hidden" name="visitID" value="<s:property value="visitID"/>"> 
				      <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
					  <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					  <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>"> 
				      
				      <div class="row" style="margin-top:10px;margin-left:0px;margin-bottom:5px;">
					 
						<div class="row" style="margin-top:20px;">
					 		<div class="col-md-4 col-xs-12 col-sm-6" style="margin-left:15px;">
					 			<h4><b>Diagnosis: </b><s:property value="cancerType"/></h4>
					 		</div>
						  
							<div class="col-md-2 col-sm-1 col-xs-12" style="margin-left:15px;" >
						  		<h4><b>Advice: </b></h4>
						  	</div>	
						  	<div class="col-md-5 col-sm-4 col-xs-10" style="margin-left:15px;">
						  		<input type="text" name="advice" id="adviceID" class="form-control" placeholder="Advice" value="<s:property value="advice"/>">
						    </div>
						</div>

				      <h4 style="margin-top:20px;margin-left:15px;"><b>Investigation</b></h4>
				
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
				
				          <div class="col-md-3 col-xs-3">
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
				      
					                <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap addedFrequency" cellspacing="0" width="100%" style="width: 97%; margin-left: 1%;">
					                            <thead>
													<tr>
					                                    <th style="width: 25%">Drug Name</th>
					                                    <th style="width: 20%">Category</th>
					                                    <th style="width: 17%">Frequency (<a href='#' data-toggle='modal' data-target='#frequencyModal'>Click Here</a>)</th>
					                                    <th style="width: 10%">No. of days</th>
					                                    <th style="width: 9%">Quantity</th>
					                                    <th style="width: 25%">Comments</th>
					                                    <th style="">Action</th>
					                                </tr>
					                             </thead>
					                               
					                            <tbody>
					                            	<s:iterator value="prescriptionList">
					                            		<tr id="visitPrescTRID<s:property value="prescriptionID"/>">
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="tradeName"/></td>
					                            			
					                            			<td style="font-size: 14px;  margin-top:10px; text-align: center;"><s:property value="category"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property escape="false" value="frequency"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property escape="false" value="noOfDays"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="productQuantity"/></td>
					                            			
					                              			<td style="font-size: 14px;text-align: center;"><s:property value="comment"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick="deletePrescRow('visitPrescTRID<s:property value="prescriptionID"/>',<s:property value="prescriptionID"/>);"/> </td>
					                            		</tr>
					                            	</s:iterator>
					                            	
					                            	<tr id="prescTRID">
					                            	</tr>
					                            	
					                            </tbody>
					                        </table>
					                                
				            </div>
				
							<div class="col-md-2 col-xs-7 col-sm-3" >
				            	<div>
									<input type='text' required='required' name="tradeName" class='form-control' id="tradeName" placeholder='Drug Name'>
								</div>
				            </div>
				            
				            <div class="col-md-2 col-xs-7 col-sm-2">
				            	<s:select list="categoryList" name="category"  id="category1" class="form-control" headerKey="" headerValue="Category"></s:select>
				            </div>
				            
				            <%-- <div class="col-md-2 col-xs-7 col-sm-2">
				            	<s:select list="frequencyList" name="frequency[]"  id="frequency1" class="form-control"  multiple="true" onchange="checkFrequency(this)"></s:select>
				            </div>
				            
				             <div class="col-md-2 col-xs-7 col-sm-1" id="noOfDays">
				            	<div id="selectdFrq">
				            	
				            	</div>
				            </div> --%>
				            
				            <div class="col-md-2 col-xs-7 col-sm-2" id="addFrequency">
				            	
				            </div>
				            
				            <div class="col-md-2 col-xs-7 col-sm-1" id="noOfDays">
				            	
				            </div>
				     
				            
				            <div class="col-md-1 col-xs-7 col-sm-1">
				              <input type="text" class="form-control" name="noOfPills" id="noOfPills"  placeholder="Qty">
				            </div>
				            
				              <div class="col-md-2 col-xs-7 col-sm-4">
				              <input type="text" class="form-control" name="comment" id="comment"  placeholder="Comment">
				            </div>
				            
				           <div class="col-md-1">
				             
							  <a onclick="addOPDPrescription(tradeName.value, category1.value,frequency1.value,visitID.value,comment.value,noOfPills.value,noOfDays1.value);" > 
							  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
									onmouseout="this.src='images/add_icon_1.png'" alt="Add Prescription"
									title="Add Prescription" style="margin-top:5px;height: 24px; margin-top:10px;" />
							  </a>
				            </div>
				      </div>
				    
				     <div class="row" style="margin-top:100px;"> 
					      <div class="col-md-4 col-sm-3"></div>
					      <div class="col-md-2 col-xs-3 col-sm-3">
                            <input type="number" class="form-control" name="nextVisitDays" value= "<s:property value="nextVisitDays"/>" id="nextVisitDaysID" >
                          </div>
                          <div class="col-md-2 col-xs-5 col-sm-2" >
                          	  <label class="control-label" style="text-align: left;" for="Date"> दिवसांनी परत दाखवा.</label>
                          </div>
                          <div class="col-md-3"></div>
                      </div>
                      
				      <div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12 col-xs-12" align="center">
		                        <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
		                        <button type="submit" class="btn btn-success" id="savePrescID" name="addButton" value="add">Save</button>
			                 	<button class="btn btn-warning" type="submit" id="PrintPrescID" name="addButton" value="print">Save & Print</button>
	                          </div>
		                    </div>
				      
				      </form>
			      
			    </div>
			    <!-- End -->
			    
			    <!-- Optician div -->
			    <div class="tab-pane fade" id="optician" >
			    
			   		 <form class="app-cam" id="genOpticianID" name="" action="UpdateOptician" onsubmit="return showLoadingImg();"  method="POST" style="width:100%;margin:0px;" >
			   		 
				      <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				       <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>"> 
				      <input type="hidden"  name="visitNumber" id="visitNumber" value="<s:property value="visitNumber"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
	  				  <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
						
					<div class="row" style="margin-top:15px;">
					    <div class="col-md-6 col-xs-12">
					    <div class="col-md-4 col-xs-3">
					     <label for="Name" style="font-size:14px;">Patient Name(ID)</label>
					    </div>
					    <div class="col-md-8 col-xs-9">
					      <input type="text" class="form-control"  readonly="readonly" value="<s:property value="firstName"/> <s:property value="middelName"/> <s:property value="lastName"/> ( <s:property value="registrationNo"/> )"  name="" placeholder="Patient Name" >
					    </div>
					    </div>
					
					     <div class="col-md-6 col-xs-12">
					    <div class="col-md-4 col-xs-3">
					      <label for="Name" style="font-size:14px;">Date</label>
					    </div>
					   <div class="col-md-8 col-xs-9">
					     <input type="text" readonly="readonly"  class="form-control" name="visitDate" value="<s:property value="visitDate"/>" placeholder="Visit Date"  >
					    </div>
					    </div>
				    </div>
				
				    <div class="row" style="margin-top:15px;">
					     <div class="col-md-6 col-xs-12">
					    <div class="col-md-4 col-xs-3">
					     <label for="Name" style="font-size:14px;">Mobile No.</label>
					    </div>
					    <div class="col-md-8 col-xs-9">
					      <input type="text"  class="form-control" readonly="readonly" value="<s:property value="mobile"/>"  name="mobile" placeholder="Mobile No"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status"  >
					    </div>
					    </div>
				    </div>
				    
				     <div class="ln_solid"></div>
			    
			     <div class="row">
			      <div class="col-md-4" align="left">
			    <div class="col-md-6">
			      <label for="Name" style=" margin-top:10px;font-size:18px;"><b>Old Glasses</b></label>
			    </div>
			    <div class="col-md-6">
			      <button type="button" onclick="fetchOldGlassPreviousValues(lastVisitID.value);">Fetch Values</button>
			    </div>
			    </div>
			
			    <div class="col-md-4" align="center" > 
			    <div class="col-md-12"></div>
			    
			    </div>
			
			    <div class="col-md-4" align="center">
			    <div class="col-md-12">
			      
			    </div>
			    </div>
			    </div>
			
			    <div class="row">
			      <div class="col-md-12 col-xs-12">
			      <div class="row">
			      	<div class="col-md-2 col-xs-3"></div>
				    <div class="col-md-5 col-xs-3" align="center">RE</div>
				    <div class="col-md-5 col-xs-3" align="center">LE</div>
			      </div>
			      
			      <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-2"></div>
				      	<div class="col-md-2 col-xs-1" align="center" style="width: 11%;">SPH</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 11%;">CYL</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Axis</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Vn</div>
				      	<div class="col-md-2 col-xs-1" align="center" style="width: 11%;">SPH</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 11%;">CYL</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Axis</div>
				      	<div class="col-md-1 col-xs-1" align="center">Vn</div>
				  </div>
			      
			       <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-2" align="center">Dist</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldSphDiskOD" id="oldSphDiskOD" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--','-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldCylDiskOD" id="oldCylDiskOD"  class="form-control" onclick="showValues(this.value, 'oldCylNearOD', 'oldSphNearOD');" ></s:select>
						</div> 
				      	<div class="col-md-1" align="center" style="width: 9%;"> 
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="oldAxisDiskOD" id="oldAxisDiskOD" onclick="showAxisDiskValue(this.value, 'oldAxisNearOD', 'oldSphNearOD');" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;" >
							<s:select list="#{'--':'--', '6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="oldVnDiskOD" id="oldVnDiskOD" class="form-control"></s:select>
						</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldSphDiskOS" id="oldSphDiskOS" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldCylDiskOS" id="oldCylDiskOS" class="form-control" onclick="showValues(this.value, 'oldCylNearOS', 'oldSphNearOS');" ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="oldAxisDiskOS" id="oldAxisDiskOS" class="form-control" onclick="showAxisDiskValue(this.value, 'oldAxisNearOS', 'oldSphNearOS');" ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="oldVnDiskOS" id="oldVnDiskOS" class="form-control"></s:select>
						</div>
				      </div>
				      
				      <div class="row col-xs-12" style="margin-top:15px;">
				      	<div class="col-md-2 col-xs-2" align="center">Near</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50'}" name="oldSphNearOD" id="oldSphNearOD" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldCylNearOD" id="oldCylNearOD" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="oldAxisNearOD" id="oldAxisNearOD" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', 'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="oldVnNearOD" id="oldVnNearOD" class="form-control"   ></s:select>
						</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50'}" name="oldSphNearOS" id="oldSphNearOS" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="oldCylNearOS" id="oldCylNearOS" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="oldAxisNearOS" id="oldAxisNearOS" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', 'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="oldVnNearOS" id="oldVnNearOS" class="form-control"   ></s:select>
						</div>
				      </div>
			      
			      </div>  
			    </div>
			    
			    <div class="ln_solid"></div>
			   
			     <div class="row">
			      <div class="col-md-4" align="left">
			    <div class="col-md-12">
			      <label for="Name" style=" margin-top:10px;font-size:18px;"><b>New Glasses</b></label>
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center" > 
			    <div class="col-md-12">
			      
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center">
			    <div class="col-md-12">
			      
			    </div>
			    </div>
			    </div>
			
			    <div class="row">
			      <div class="col-md-12">
			      
			      <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-3"></div>
				      	<div class="col-md-5 col-xs-3" align="center">RE</div>
				      	<div class="col-md-5 col-xs-3" align="center">LE</div>
				  </div>
			      
			      <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-2"></div>
				      	<div class="col-md-2 col-xs-1" align="center" style="width: 11%;">SPH</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 11%;">CYL</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Axis</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Vn</div>
				      	<div class="col-md-2 col-xs-1" align="center" style="width: 11%;">SPH</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 11%;">CYL</div>
				      	<div class="col-md-1 col-xs-1" align="center" style="width: 10%;">Axis</div>
				      	<div class="col-md-1 col-xs-1" align="center">Vn</div>
				  </div>
			      
			      <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-2" align="center">Dist</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="sphDiskOD"  class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="cylDiskOD" id="cylDiskODID" class="form-control" onclick="showValues(this.value, 'cylNearODID', 'sphNearODID')"></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%; ">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="axisDiskOD" id="axisDiskODID" onclick="showAxisDiskValue(this.value, 'axisNearODID', 'sphNearODID')" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="vnDiskOD"  class="form-control"   ></s:select>
						</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="sphDiskOS"  class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="cylDiskOS"  class="form-control" onclick="showValues(this.value, 'cylNearOSID', 'sphNearOSID')" ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="axisDiskOS" id="axisDiskOSID" class="form-control" onclick="showAxisDiskValue(this.value, 'axisNearOSID', 'sphNearOSID')" ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="vnDiskOS"  class="form-control"   ></s:select>
						</div>
				      </div>
				      
				      <div class="row col-xs-12">
				      	<div class="col-md-2 col-xs-2" align="center">Near</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50'}" name="sphNearOD" id="sphNearODID" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="cylNearOD" id= "cylNearODID" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="axisNearOD" id="axisNearODID" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', 'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="vnNearOD"  class="form-control"   ></s:select>
						</div>
				      	<div class="col-md-2" align="center" style="width: 11%;">
							<s:select list="#{'--':'--','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50'}" name="sphNearOS" id="sphNearOSID" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 11%;">
							<s:select list="#{'--':'--', '-20.00':'-20.00','-19.75':'-19.75','-19.50':'-19.50','-19.25':'-19.25','-19.00':'-19.00','-18.75':'-18.75','-18.50':'-18.50','-18.25':'-18.25','-18.00':'-18.00','-17.75':'-17.75','-17.50':'-17.50','-17.25':'-17.25','-17.00':'-17.00','-16.75':'-16.75','-16.50':'-16.50','-16.25':'-16.25','-16.00':'-16.00','-15.75':'-15.75','-15.50':'-15.50','-15.25':'-15.25','-15.00':'-15.00','-14.75':'-14.75','-14.50':'-14.50','-14.25':'-14.25','-14.00':'-14.00','-13.75':'-13.75','-13.50':'-13.50','-13.25':'-13.25','-13.00':'-13.00','-12.75':'-12.75','-12.50':'-12.50','-12.25':'-12.25','-12.00':'-12.00','-11.75':'-11.75','-11.50':'-11.50','-11.25':'-11.25','-11.00':'-11.00','-10.75':'-10.75','-10.50':'-10.50','-10.25':'-10.25','-10.00':'-10.00','-9.75':'-9.75','-9.50':'-9.50','-9.25':'-9.25','-9.00':'-9.00','-8.75':'-8.75','-8.50':'-8.50','-8.25':'-8.25','-8.00':'-8.00','-7.75':'-7.75','-7.50':'-7.50','-7.25':'-7.25','-7.00':'-7.00','-6.75':'-6.75','-6.50':'-6.50','-6.25':'-6.25','-6.00':'-6.00','-5.75':'-5.75','-5.50':'-5.50','-5.25':'-5.25','-5.00':'-5.00','-4.75':'-4.75','-4.50':'-4.50','-4.25':'-4.25','-4.00':'-4.00','-3.75':'-3.75','-3.50':'-3.50','-3.25':'-3.25','-3.00':'-3.00','-2.75':'-2.75','-2.50':'-2.50','-2.25':'-2.25','-2.00':'-2.00','-1.75':'-1.75','-1.50':'-1.50','-1.25':'-1.25','-1.00':'-1.00','-0.75':'-0.75','-0.50':'-0.50','-0.25':'-0.25','0':'0','+0.25':'+0.25','+0.50':'+0.50','+0.75':'+0.75','+1.00':'+1.00','+1.25':'+1.25','+1.50':'+1.50','+1.75':'+1.75','+2.00':'+2.00','+2.25':'+2.25','+2.50':'+2.50','+2.75':'+2.75','+3.00':'+3.00','+3.25':'+3.25','+3.50':'+3.50','+3.75':'+3.75','+4.00':'+4.00','+4.25':'+4.25','+4.50':'+4.50','+4.75':'+4.75','+5.00':'+5.00','+5.25':'+5.25','+5.50':'+5.50','+5.75':'+5.75','+6.00':'+6.00','+6.25':'+6.25','+6.50':'+6.50','+6.75':'+6.75','+7.00':'+7.00','+7.25':'+7.25','+7.50':'+7.50','+7.75':'+7.75','+8.00':'+8.00','+8.25':'+8.25','+8.50':'+8.50','+8.75':'+8.75','+9.00':'+9.00','+9.25':'+9.25','+9.50':'+9.50','+9.75':'+9.75','+10.00':'+10.00','+10.25':'+10.25','+10.50':'+10.50','+10.75':'+10.75','+11.00':'+11.00','+11.25':'+11.25','+11.50':'+11.50','+11.75':'+11.75','+12.00':'+12.00','+12.25':'+12.25','+12.50':'+12.50','+12.75':'+12.75','+13.00':'+13.00','+13.25':'+13.25','+13.50':'+13.50','+13.75':'+13.75','+14.00':'+14.00','+14.25':'+14.25','+14.50':'+14.50','+14.75':'+14.75','+15.00':'+15.00','+15.25':'+15.25','+15.50':'+15.50','+15.75':'+15.75','+16.00':'+16.00','+16.25':'+16.25','+16.50':'+16.50','+16.75':'+16.75','+17.00':'+17.00','+17.25':'+17.25','+17.50':'+17.50','+17.75':'+17.75','+18.00':'+18.00','+18.25':'+18.25','+18.50':'+18.50','+18.75':'+18.75','+19.00':'+19.00','+19.25':'+19.25','+19.50':'+19.50','+19.75':'+19.75','+20.00':'+20.00'}" name="cylNearOS" id ="cylNearOSID" class="form-control"></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', '0':'0','5':'5','10':'10','15':'15','20':'20','25':'25','30':'30','35':'35','40':'40','45':'45','50':'50','55':'55','60':'60','65':'65','70':'70','75':'75','80':'80','85':'85','90':'90','95':'95','100':'100','105':'105','110':'110','115':'115','120':'120','125':'125','130':'130','135':'135','140':'140','145':'145','150':'150','155':'155','160':'160','165':'165','170':'170','175':'175','180':'180'}" name="axisNearOS" id="axisNearOSID" class="form-control"  ></s:select>
						</div>
				      	<div class="col-md-1" align="center" style="width: 9%;">
							<s:select list="#{'--':'--', 'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="vnNearOS"  class="form-control"   ></s:select>
						</div>
				      </div>
			      
			      </div>    
			    </div>
			    
			    <div class="ln_solid"></div>
			
			<% if(form.getUserType().equals("optician")){%>
			
			<div class="row">
			      <div class="col-md-4" align="left">
			    <div class="col-md-12">
			      <label for="Name" style=" margin-top:10px;font-size:18px;"><b>Frame Details</b></label>
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center" > 
			    <div class="col-md-12">
			      
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center">
			    <div class="col-md-12">
			      
			    </div>
			    </div>
			    </div>
			
			    <div class="row" style="margin-top:15px;font-size: 14px;">
			      <div class="col-md-12">
			      <table border="1" width="100%">
			        <tr align="left">
			          <td>Tint</td>
			          <td>Glass</td>
			          <td>Material</td>
			          <td>Clinic</td>
			          <td>Frame</td>
			        </tr>
			        <tr align="left">
			          <td><input type="text" name="tint" style="border:none;" class="form-control" value="<s:property value="tint"/>" ></td>
			          <td><input type="text" name="glass" style="border:none;" class="form-control" value="<s:property value="glass"/>" ></td>
			          <td><input type="text" name="material" style="border:none;" class="form-control" value="<s:property value="material"/>" ></td>
			          <td><input type="text" name="clinic" style="border:none;" class="form-control" value="<s:property value="clinic"/>" ></td>
			          <td><input type="text" name="frame" style="border:none;" class="form-control" value="<s:property value="frame"/>" ></td>
			        </tr>
			      </table>  
			      </div>  
			    </div>
			
			<div class="ln_solid"></div>
			
			  <div class="row">
			      <div class="col-md-4" align="left">
			    <div class="col-md-12">
			      <label for="Name" style=" margin-top:10px;font-size:18px;"><b>Frame Charges</b></label>
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center" > 
			    <div class="col-md-12">
			      
			    </div>
			    
			    </div>
			
			    <div class="col-md-4" align="center">
			    <div class="col-md-12">
			      
			    </div>
			    </div>
			    </div>
			
			    <div class="row">
			      <div class="col-md-12" style="margin-top:15px;font-size: 14px;">
			      
			      <table border="1" width="90%">
			        <tr align="left">
			          <td>Frame</td>
			          <td><input type="text" name="frameCharge" onkeyup="calculateTotal();" style="border:none;" value="<s:property value="frameCharge"/>" id="frameChargeID" class="form-control"></td>
			          <td colspan="2" style="border-bottom: 0;"></td>
			
			        </tr>
			
			        <tr align="left">
			          <td>Glass</td>
			          <td><input type="text" name="glassCharge" onkeyup="calculateTotal();" style="border:none;" value="<s:property value="glassCharge"/>" id="glassChargeID"  class="form-control"></td>
			          <td colspan="2" style="border-top: 0;"></td>
			
			        </tr>
			        <tr align="left">
			          <td>Total</td>
			          <td><input type="text" value="<s:property value="total"/>" style="border:none;cursor: not-allowed;background: transparent;" name="total" id="totalID"  readonly="readonly" class="form-control"></td>
			          <td>Discount</td>
			          <td><input type="text" name="discount" value="<s:property value="discount"/>" style="border:none;" id="discountID" onkeyup="calculateDiscount();" class="form-control"></td>
			        </tr>
			
			        <tr align="left">
			          <td>Net Payment</td>
			          <td><input type="text" value="<s:property value="netPayment"/>" style="border:none;cursor: not-allowed;background: transparent;" name="netPayment" id="netPaymentID" readonly="readonly"  class="form-control"></td>
			          <td colspan="2"></td>
			        </tr>
			
			        <tr align="left">
			          <td>Advance</td>
			          <td><input type="text" value="<s:property value="advance"/>" style="border:none;" name="advance" id="advanceID" onkeyup="calculateBalance();"  class="form-control"></td>
			          <td>Balance</td>
			          <td><input type="text" value="<s:property value="balance"/>" style="border:none;cursor: not-allowed;background: transparent;" name="balance" id="balanceID" onfocus="calculateBalance();" readonly="readonly" class="form-control"></td>
			        </tr>
			
			        <tr align="left">
			          <td>Balance Paid</td>
			          <td><input type="text" name="balancePaid" value="<s:property value="balancePaid"/>" style="border:none;"  class="form-control"></td>
			          <td>Balance Paid Date</td>
			          <td><input type="text" class="form-control" name="balancePaidDate" value="<s:property value="balancePaidDate"/>" style="border:none;" id="single_cal31" placeholder="Balance Paid Date" aria-describedby="inputSuccess2Status3"></td>
			        </tr>
			      </table>

			      </div>  
			    </div>
			
			<% }  %>    
				<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12" align="center">
		                        <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
		                        <button class="btn btn-success" type="submit" >Update</button>
		                        <button class="btn btn-warning" type="button" onclick="printOptician();">Update & Print</button>
		                      </div>
		                    </div>		
			   	</form>
			    </div>
			    <!-- Ends -->
			    
			    
			    <!-- Billing div -->
			     <div class="tab-pane fade" id="bill" >
			      <form class="form-horizontal form-label-left" onsubmit = "return checkCheckbox();" id="OPDBILLForm" action="EditBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						     </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
					       <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
					       <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
						  <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
						  <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">

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
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Charges(Rs)</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text" class="form-control"  name="charges" id="charges" placeholder="Charges(Rs)" value="<s:property value="totalAmount"/>" onkeyup="changeTotalBill('totalBill',charges.value);" >
	                        </div>
	                     </div>
	                      
	                     <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5"">Total Bill(Rs)</label>
	                         <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text" class="form-control"  name="totalBill" id="totalBill" readonly="readonly" placeholder="Total Bill(Rs)" value="<s:property value="netAmount"/>">
	                        </div>
	                     </div>
	                      
	                    <%--  <div class="item form-group">
	                       <label for="User Type" class="control-label col-md-3 col-xs-5"">Advance Payment</label>
	                         <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="number" class="form-control" name="advPayment" id="advPaymentID" value = "<s:property value="advPayment"/>" onkeyup="changeBalancePayment(advPaymentID.value, totalBill.value);">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5"">Balance Payment</label>
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
							<div class="row">
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
									<input type="number" class="form-control" id="cMobileNoID" value="<s:property value="cMobileNo"/>" name="cMobileNo" placeholder="Mobile No."  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status">
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
		                        <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
		                  	<button class="btn btn-warning" type="submit" name="addButton" value="print" id="billSubmtBtnID">Save & Print</button>
	                		<button class="btn btn-success" type="submit" name="addButton" value="add" id="billSubmtBtnID" >Save</button>
			               	
		                      </div>
		                    </div>
			     
			      </form>
			      
			    </div>
			    <!-- ends -->
			    
			    <!-- Lab report div -->
			    <div class="tab-pane fade" id="labRep" >
			    
			      <form class="form-horizontal form-label-left" novalidate action="EditLabReport" onsubmit="return showLoadingImg();"name="labRepForm" id="labRepForm" method="POST" style="margin-top:20px;" enctype="multipart/form-data">
			      
			      	<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
			        <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
			        <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			        <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
			        <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
			        <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">

			       <div class="row" style="margin-top:10px;margin-left:0px;padding-left:15px;">
			          <h5 style="margin-top:0px;font-size:16px;">Upload Lab Report File(s) here</h5>
			      </div>
			      
			      <div class="row" style="padding-left:25px;">
			      	<div class="col-md-3" style="margin-top:10px;margin-bottom:10px;">
			      		<input type="file" name="labReport">
			      	</div>
			      	<div class="col-md-6" style="margin-top:10px;">
			      		<button type="button" class="btn btn-default" onclick="myFunction();">Add More Files</button>
			      	</div>
			      	
			      	<div class="col-md-12" id="myID"></div>
			      </div>
			      
			      <div class="ln_solid"></div>
		            
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
										<%-- <button class="btn btn-success" type="button" onclick="downloadLabReport('<s:property value="reportsID"/>');">Download</button> --%>
										<img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' 
											onclick="deleteReportRow('labRepTRID<s:property value="reportsID"/>', <s:property value="reportsID"/>);"/>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				          
		           <div class="form-group">
		             <div class="col-md-12" align="center">
		                 <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
		               
		                 <button class="btn btn-success" type="submit" id="uploadLabReportBtnID"  >Add Report</button>
		             </div>
		          </div>
			      
			      </form>
			    </div>
			    <!-- Ends -->
			    
			    
			    <!-- Medical certificate -->
			    <div class="tab-pane fade" id="medCerti" >
			    	<form class="app-cam" id="" name="" action="UpdateMedicalCertificate" method="POST" style="width:95%;margin:0px;" >
						
						<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName" value="<%=fullName %>">
					    <input type="hidden" id="patientName" value="<s:property value="firstName"/> <s:property value="lastName"/>">
					        
					        <textarea style="display: none" name="medicalCerti" id="medicalCerti" cols="" rows="5"></textarea>
					        <textarea style="display: none" name="medicalCertiForPDF" id="medicalCertiForPDF" cols="" rows="5"></textarea>
					
							<%
						    	if(medicalCertiCheck == "no"){
						    %>
					    	<div class="row" style="margin:15px;">
					 			<s:radio list="#{'1':'Pre-defined','0':'Free-text'}" name="radioVal" id="radioValID" onclick="displaycertificate(this.value);"></s:radio>
				    		</div>
				    		<% 
						    	}
				    		%>
				    		
					    	<div class="row" style="margin:15px;">
					     	<%
						    	if(medicalCertiText == null || medicalCertiText == ""){
						    %>
							<div class="col-md-12 col-xs-12" id="medicalTextDivID" style="margin:25px;display: none;" >
								<s:textarea cols="2" rows="10" id="medicalCertiText" name="medicalCertiText" style="width:100%;"></s:textarea>
							</div>
  
								<div class="col-md-12 col-xs-12" id= "medicalCertiTextDivID" style="margin:25px;margin-bottom: -128px;display: none;">
									<label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
									      after careful personal examination of the case hereby certify that, 
									      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName">, 
									      is suffering from <input type="text" class="form-control"  name="disease" id="disease" style="width:20%; display: inline-block;margin-top:10px;"> and I consider that 
									      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;"> days 
									      with effect from <input type="text" class="form-control"  name="wef" id="wef" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;"> is 
									      absolutely necessary for the restoration of his/her health.</label>
									
									      <div class="row" style="margin-top:25px;">
									        <div class="col-md-12 col-xs-12">
									          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
									          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place"></div>
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
								<div class="col-md-12 col-xs-12" id= "medicalCertiTextDivID" style="margin:25px;margin-bottom: -128px;">
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
								       <div class="col-md-3" ><input type="text" class="form-control"  value="<%=strval[6] %>"   name="date" id="date11"></div>
								       <div class="col-md-4" style="padding-top: 20px;" align="center"> <label>(SEAL)</label></div>
								       <div class="col-md-4" style="padding-top: 20px;"><label>Registered Medical Practitioner</label></div>
							        </div>
						      </div>
						   </div>
						   <%
							} else{
									    		
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
			                   <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
			                   <button class="btn btn-success" type="submit" onclick="displayVal();" >Save & Print</button>
			               </div>
			            </div>
			
			     </form>
			    </div>
			    <!-- Ends -->
			    
			    <!-- Referral letter div -->
			    <div class="tab-pane fade" id="refLetter" >
			    
			    		<form class="app-cam" id="" name="" action="UpdateReferralLetter" method="POST" style="width:95%;margin:0px;">
			    		
			    		<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName" value="<%=fullName %>">
					    <input type="hidden" id="patientName" value="<s:property value="firstName"/> <s:property value="lastName"/>">
				      
				        <input type="hidden" id="doctrNameRef" value="<%=fullName %>">
				        
				        <input type="hidden" id="ageRef" value="<s:property value="age"/>">
				        <input type="hidden" id="visualAcuityDistODRef" value="<s:property value="visualAcuityDistOD"/>">
				        <input type="hidden" id="visualAcuityDistOSRef" value="<s:property value="visualAcuityDistOS"/>">
				        <input type="hidden" id="visualAcuityNearODRef" value="<s:property value="visualAcuityNearOD"/>">
				        <input type="hidden" id="visualAcuityNearOSRef" value="<s:property value="visualAcuityNearOS"/>">
				        
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
						          <div class="col-md-3" style="margin-left: -15px;"> <label><%=currentDate %><input type="hidden" class="form-control" value="<%=currentDate %>"  name="date" id="date12"></label></div>
						      </div>
						
						    </div>
						
						    <div class="row" >
						    	<div class="col-md-12 col-xs-12" style="margin:15px;">
						       		<label for="Name" style="font-size:14px;margin-top: 10px;">To,</label>
						       	</div>
						     </div>
						     
						    <div class="row" >
						       <div class="col-md-12 col-xs-12" style="margin:15px;">
							        <label for="Name" style="font-size:14px; ">Dr. </label><s:select list="doctorList" name="doctName" id="doctName" style="width:20%; " class="form-control"></s:select> 
									<label for="Name" style="font-size:14px;margin-top: 10px;">Respected sir,</label>
								</div>
							</div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;">Mr./Mrs./Mis.  <s:property value="firstName"/> <s:property value="lastName"/>,  age: <s:property value="age"/>. Is examined for ophthalmic examination. 
						          His visual acuity is <s:property value="visualAcuityDistOD"/> in RE and <s:property value="visualAcuityDistOS"/> in LE for distance and <s:property value="visualAcuityNearOD"/> in RE and <s:property value="visualAcuityNearOS"/> in LE.  
						          His colour vision is <input type="text" class="form-control"  name="vision1" id="vision1" style="width:20%; display: inline-block;margin-top:10px;" required>  .
						          His fundus examination has found <input type="text" class="form-control"  name="fundusRE" id="fundusRE" style="width:20%; display: inline-block;margin-top:10px;" required> 
						          in RE and <input type="text" class="form-control"  name="fundusLE" id="fundusLE" style="width:20%; display: inline-block;margin-top:10px;" required>  In LE.
						          He is advised to undergo <input type="text" class="form-control"  name="surgery" id="surgery" style="width:20%; display: inline-block;margin-top:10px;" required> 
						          Surgery in <input type="text" class="form-control"  name="eye" id="eye" style="width:20%; display: inline-block;margin-top:10px;" required> eye.</label><br><br>
						
						          <label for="Name" style="font-size:14px;">Sending hear with for <input type="text" class="form-control"  name="expert" id="expert" style="width:20%; display: inline-block;margin-top:10px;" required>
						          and your expert opinion and further treatment accordingly. </label> <br><br>
						          
						          <label for="Name" style="font-size:14px;">Please do the needful.</label> 
						      </div>
						    </div>
						    
						    <div class="row" style="margin:22px;">
							<label for="Name" style="font-size:14px; margin-top: 10px;">Your’s Sincerely, </label>
							<br>
						     <label for="Name" style="font-size:14px;">Dr. <%=fullName %> </label>
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
						          <div class="col-md-3" style="margin-left: -15px;"> <label><input type="text" class="form-control" value="<%=strval[1] %>" name="date" id="date12"></label></div>
						      </div>
						
						    </div>
						
						    <div class="row" >
						    	<div class="col-md-12 col-xs-12" style="margin:15px;">
						       		<label for="Name" style="font-size:14px;margin-top: 10px;">To,</label>
						       	</div>
						     </div>
						     
						    <div class="row" >
						       <div class="col-md-12 col-xs-12" style="margin:15px;">
							        <label for="Name" style="font-size:14px; ">Dr. </label><s:select list="doctorList" name="doctName" id="doctName" style="width:20%; " class="form-control"></s:select> 
									<label for="Name" style="font-size:14px;margin-top: 10px;">Respected sir,</label>
								</div>
							</div>
						
						    <div class="row" >
						      
						      <div class="col-md-12 col-xs-12" style="margin:15px;">
						        
						           <label for="Name" style="font-size:14px;">Mr./Mrs./Mis.  <s:property value="firstName"/> <s:property value="lastName"/>,  age: <s:property value="age"/>. Is examined for ophthalmic examination. 
						          His visual acuity is <s:property value="visualAcuityDistOD"/> in RE and <s:property value="visualAcuityDistOS"/> in LE for distance and <s:property value="visualAcuityNearOD"/> in RE and <s:property value="visualAcuityNearOS"/> in LE.  
						          His colour vision is <input type="text" class="form-control"  name="vision1" id="vision1" value="<%=strval[2] %>" style="width:20%; display: inline-block;margin-top:10px;" required>  .
						          His fundus examination has found <input type="text" class="form-control"  name="fundusRE" id="fundusRE" value="<%=strval[3] %>" style="width:20%; display: inline-block;margin-top:10px;" required> 
						          in RE and <input type="text" class="form-control"  name="fundusLE" id="fundusLE" value="<%=strval[4] %>" style="width:20%; display: inline-block;margin-top:10px;" required>  In LE.
						          He is advised to undergo <input type="text" class="form-control"  name="surgery" id="surgery" value="<%=strval[5] %>" style="width:20%; display: inline-block;margin-top:10px;" required> 
						          Surgery in <input type="text" class="form-control"  name="eye" id="eye" value="<%=strval[6] %>" style="width:20%; display: inline-block;margin-top:10px;" required> eye.</label><br><br>
						
						          <label for="Name" style="font-size:14px;">Sending hear with for <input type="text" class="form-control"  name="expert" id="expert" value="<%=strval[7] %>" style="width:20%; display: inline-block;margin-top:10px;" required>
						          and your expert opinion and further treatment accordingly. </label> <br><br>
						          
						          <label for="Name" style="font-size:14px;">Please do the needful.</label> 
					
						      </div>
						    </div>
						    
						    <div class="row" style="margin:22px;">
							<label for="Name" style="font-size:14px; margin-top: 10px;">Your’s Sincerely, </label>
							<br>
						     <label for="Name" style="font-size:14px;">Dr. <%=fullName %> </label>
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
			                   <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary">Cancel</button>
			                   <button class="btn btn-success" type="submit" onclick="displayRefLet();" >Save & Print</button>
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
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    
	<script src="build/js/jquery-ui.js"></script>
	
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    
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
    
    <script>
   
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
                		CategoryValue($("#tradeName").val());
                	},500);
                }
            });
    });
	 
    $(document).ready(function() {

    	$("#cancerType").select2({
      	  tags: true
      	});
      });
    </script>
    
  </body>
</html>