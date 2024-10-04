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

    <title>View Existing Visit | E-Dhanvantari</title>

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
	
	<% 
			/* private List<Integer> vendorTypeListValues1;
			private List<Integer> vendorTypeListValues2;
			private List<Integer> vendorTypeListValues3;
			private List<Integer> vendorTypeListValues4; 
	
	
			List<Integer> OriginalList = new ArrayList<Integer>();
			String vendorType = resultSet.getString("vendorType");
			String vendorArr[] = vendorType.split(",");
			for (int j = 0; j < vendorArr.length; j++) {
				OriginalList.add(Integer.parseInt(vendorArr[j].trim()));
				System.out.println("OriginalList :" + OriginalList);
			}
			form.setVendorTypeListValues(OriginalList);*/
	%>

    <script type="text/javascript">
      function windowOpen(){
        document.location="Welcome.jsp";
      }
      
      function windowOpen1(){
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
		#labReportDivID{
			padding-bottom: 10px;
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

span.required {
	   color: red !important;
	}
	
	</style>
	
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
    
    <%
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
   	%>
    
	
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
			console.log(queryString);
			
			popup = window.open('LabReportView'+queryString,"Popup", "width=700,height=700");
			
		}
	
		function downloadLabReport(report){
			var queryString = "";
			queryString += "?reportsID="+report;
			
			document.forms["labRepForm"].action = 'DownloadLabReport'+queryString;
			document.forms["labRepForm"].submit();
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
		System.out.println("check value in jsp"+medicalCertiCheck);
	
		/* String medicalCertificateFinalText = "";
		
		if (medicalCertiText == null || medicalCertiText == "") {
	
			medicalCertiText = "";
		} else {
			
			String[] array = medicalCertiText.split(" ");
	
			for (int i = 0; i < array.length; i++) {
	
				if (array[i].contains("'")) {
	
					String[] temp1 = array[i].split("'");
	
					String temp2 = temp1[1];
					
					// Splitting temp2 by @ to get value as well as id 
					String[] temp4 = temp2.split("@");
					
					String fieldValue = temp4[0];
					
					fieldValue = fieldValue.replace("_"," ");
					
					String idValue = temp4[1];
					
					String fieldValueTemp = fieldValue;
	
	
					medicalCertificateFinalText += fieldValueTemp
							.replace(
									fieldValueTemp,
									"<input type='text' class='form-control' style='width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;' name='"+ idValue +"' id='"+ idValue +"' value='"
											+ fieldValue + "'> ") + " ";
	
				} else if (array[i].contains("\n")) {
					medicalCertificateFinalText += array[i] + "<br><br>";
				} else if (array[i].contains("\t")) {
					medicalCertificateFinalText += array[i]
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				} else {
					medicalCertificateFinalText += array[i] + " ";
				}
			}
		} */
		
	%>
	
	<!-- Print visit, prescription and billing -->
	
	<script type="text/javascript">
		function printVisit(){
	 		var autoSelecter = dojo.widget.byId("search");
			var diag = autoSelecter.getSelectedValue();
			
			if(diag == null || diag ==""){
    			alert("Please add diagnosis field");
			}else{
				document.forms["visitrForm"].action = "UpdatePrintGenPhyVisit";
				document.forms["visitrForm"].submit();
	   			return true;
			}
		
		}
	
		function enablePrintButton(){
			document.getElementById("printButton").disabled = false;
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
    
    <!-- Displaying Medical Certificate text -->
    
    <script type="text/javascript">
	  function displayVal(){
	console.log("inside dispval function");
		  $(document).ready(function(){
			  $('#companyName').prop('required',false);
			  $('#disease').prop('required',false);
			  $('#dutyDays').prop('required',false);
			  $('#wef').prop('required',false);
			  $('#place').prop('required',false);
			  
			  });
		  
		  console.log("outside if loop of dispval");
		  if($("#medicalCertiText").length){
			 var medicalText =document.getElementById("medicalCertiText").value;
		  }else{
			  medicalText = "";
		  }
		
		if(medicalText == ""){
			  console.log("inside if loop for predefined text");
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
		
	    var medicalCertificate = companyName+ "$" + disease + "$" +dutyDays+ "$" + wef + "$" +place+ "$" +visitDte;
	 
	    var medicalCertificateForPDF = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in "+companyName1+ ", is suffering from " + disease1 + " and I consider that the period of absence from duty of "+ dutyDays + " days with effect from "+ wef1 + " is absolutely necessary for the restoration of his/her health.\n\n\n Place: "+place1+ " \n\n\n Date: " +visitDte + " \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    document.getElementById("medicalCerti").value = medicalCertificate;
	
	    document.getElementById("medicalCertiForPDF").value = medicalCertificateForPDF;
	  }else{
		  console.log("inside else loop for free text");
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
			  refLetText =document.getElementById("refLetterText").value; 
		  }else{
			  refLetText = $("#refLDivID").text();
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
	
	
	<%
		String pdfOutFIleName = (String) request.getAttribute("PDFOutFileName");
		System.out.println("OPD PDF out file path ::: "+pdfOutFIleName);
		
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
		  OPDPdfAction();
	  });
	  
	</script>
	<% 
		}
	%>
	
	<script type="text/javascript">
	var popup;
		function OPDPdfAction(){
			popup = window.open('OPDPDFDownload?pdfOutPath=<%=pdfOutFIleName%>',"Popup", "width=700,height=700");
			popup.focus();
			popup.print();
		}
	
	</script>
	
	<script type="text/javascript">
	
		function confirmDelete(prescID,visitID){
			if (confirm("Are you sure you want to delete prescription?")) {
				deletePrescription(prescID, visitID);
			}
			
		}
	
		function confirmBillDelete(billID,visitID){
			if (confirm("Are you sure you want to delete bill?")) {
				deleteBilling(billID, visitID);
			}
		}
	
	</script>
	
	<script type="text/javascript">
		function populateCharge(){
			var rate = document.getElementById("rate").value;
			var count = document.getElementById("count").value;
			var charge = rate * count;
			document.getElementById("charges").value = charge;
			document.getElementById("totalBill").value = charge;
	
			retrieveValues(visitID.value);
		}
	</script>
	
	<!-- retrieving value based on drug name selected -->
	
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getValuesByDrugName(drugName) {
	
			var autoSelecter = dojo.widget.byId("tradeName");
			var drugName = autoSelecter.getSelectedValue();
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						document.getElementById("dose").value = array.Release[i].Dose;
						document.getElementById("drugName").value = array.Release[i].DoseUnit;
					}
				}
			};
			xmlhttp.open("GET", "ChangeValuesByDrugName?drugName="
					+ drugName, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>
	
	<!-- Ends -->
	
	<!-- retrieving value based on charge type selected -->
	
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getValuesByChargeType(chargeType) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						document.getElementById("rate").value = array.Release[i].Charge;
					}
	
					populateCharge();
				}
			};
			xmlhttp.open("GET", "ChangeValuesByChargeType?chargeType="
					+ chargeType, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	</script>

	<!-- Ends -->
	
	<!-- Add Bill  -->
	
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function addBillDetails(chargeType, rate, charge, totalBill, visitID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var undefinedCheck = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {
						document.getElementById("successMSG").innerHTML = array.Release[i].SuccessMessage;
						document.getElementById("errorMSG").innerHTML = array.Release[i].ErrorMessage;
						document.getElementById("exceptionMSG").innerHTML = array.Release[i].ExceptionMessage;
	
						if(document.getElementById("successMSG").innerHTML == "undefined"){
							document.getElementById("successMSG").innerHTML = "";
						}
	
						if(document.getElementById("errorMSG").innerHTML == "undefined"){
							document.getElementById("errorMSG").innerHTML = "";
						}
	
						if(document.getElementById("exceptionMSG").innerHTML == "undefined"){
							document.getElementById("exceptionMSG").innerHTML = "";
						}
						
						if(document.getElementById("errorMSG").innerHTML != ""){
							undefinedCheck = 1;
						}
					}
					
					var totalBill = "";
					var array_element = "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'> "+
					" <thead>";
					array_element += "<tr>"+
	                    "<th>Description</th>"+
	                    "<th>Charges(Rs)</th>"+
	                    "<th>Action</th>"+
	                	"</tr>  </thead>  <tbody>";
					for ( var i = 0; i < array.Release.length; i++) {
							
							array_element += "<tr>";
							array_element += "<td>"+
							 array.Release[i].chargeType+"</td>";
							array_element += "<td>"+
							 array.Release[i].charge+"</td>";
							array_element += "<td>"+
							 "<a onclick='confirmBillDelete("+array.Release[i].billID+","+array.Release[i].visitID+")';><img src='images/delete_icon_1.png'"+
							 " onmouseover='this.src='images/delete_icon_2.png'' onmouseout='this.src='images/delete_icon_1.png'' alt='Delete Billing'"+
							 "title='Delete Billing' style='margin-top:5px; height:24px;'/> </a></td>";
							array_element += "</tr>";
							totalBill = array.Release[i].totalBill;
						
					}
	
					if(totalBill == "undefined"){
						totalBill = "";
					}	
					array_element += "<tr><td colspan='2' align='right'>"+
					"Total Bill Amount</td><td>"+
					totalBill+"</td></tr>";
					array_element += "</tbody> </table>";
					
					if(undefinedCheck != 1){
						document.getElementById("billDivID").innerHTML = array_element;
					}
					
				}
			};
			xmlhttp.open("GET", "AddBillDetails?chargeType="
					+ chargeType + "&rate=" + rate + "&charge=" + 
					charge + "&totalBill=" + totalBill 
					+ "&visitID=" + visitID , true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
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
			System.out.println("Hour value is ::: "+hour);
			System.out.println("AMPM value is ::: "+ampm);
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
		
		String appointmentDuration = patientDAOInf.retrieveApptDurationFromClinicID(form.getVisitTypeID());
		
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
    
    	function addPrescriptionRow(drugName, frequency, noOfDays, visitID, comment, noOfPills){
    		
    		var autoSelecter = dojo.widget.byId("tradeName");
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Please select trade name");
			}else if(noOfDays == ""){
				alert("Please enter no of days");
			}else{
				addPrescriptionRow1(tradeName, frequency, noOfDays, visitID, comment, noOfPills)
			}
    		
    	}
		
    	var prescCounter = 1;
    
		function addPrescriptionRow1(tradeName, frequency, noOfDays, visitID, comment, noOfPills){
			
			var autoSelecter = dojo.widget.byId("tradeName");
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+tradeName+"<input type='hidden' name='newDrugID' value='"+tradeName+"'></td>"
					   +"<td style='text-align:center'>"+frequency+"<input type='hidden' name='newDrugFrequency' value='"+frequency+"'></td>"
					   +"<td style='text-align:center'>"+noOfPills+"<input type='hidden' name='newDrugQuantity' value='"+noOfPills+"'></td>"
					   +"<td style='text-align:center'>"+noOfDays+"<input type='hidden' name='newDrugNoOfDays' value='"+noOfDays+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#prescTRID"));
			
			prescCounter++;
			
			autoSelecter.innerHTML = "";
			
			$("#frequency").val("-1");
			$("#noOfPills").val("");
			$("#noOfDays").val("");
			$("#comment").val("");
			
		}
		
		function removeTR(TRID){
			
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+TRID).remove();	
			}
				
		}
		
		//function to delete prescription row
		function deletePrescRow(trID, prescriptionID){
			if(confirm("Are you sure you want to delete this row?")){
				deletePrescRow1(trID, prescriptionID);
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
	
		function deletePrescRow1(trID, prescriptionID) {
	
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
					
					//retrieveValues(visitID);
						
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
	
	<!-- Add prescription -->
    <script type="text/javascript" charset="UTF-8">
    
    function addOPDPrescription(drugName, frequency, visitID, comment, noOfPills, category){
    		
    		if(drugName == null || drugName.trim() === ''){
				alert("Please select drug name");
			}else if(category == "-1"){
				alert("Please select category");
			}else if(frequency == ""){
				alert("Please add frequency details");
			}else{
			
				addOPDPrescription1(drugName, frequency, visitID, comment, noOfPills, category)
			}
    		
    	}
		
    	var prescCounter = 1;
    
		function addOPDPrescription1(drugName, frequency, visitID, comment, noOfPills, category){
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var e = document.getElementById("categoryID");
	        var strUser = e.options[e.selectedIndex].text;
	        
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+drugName+"<input type='hidden' name='newDrugID' value='"+drugName+"'></td>"
					   +"<td style='text-align:center'>"+strUser+"<input type='hidden' name='newDrugCategoryID' value='"+category+"'></td>"
					   
					   +"<td style='text-align:center'><input type='hidden' name='newDrugFrequency' value='"+frequency+"'><a href='javascript:viewFrequencyValue(\""+frequency+"\");' style='font-size: 12px;'><b><u>VIEW</u></b></a></td>"
					   
					   +"<td style='text-align:center'>"+noOfPills+"<input type='hidden' name='newDrugQuantity' value='"+noOfPills+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#prescTRID"));
			
			prescCounter++;
			
			$("#tradeName").val("");
			$("#categoryID").val("-1");
			$("#noOfPills").val("");
			$("#comment").val("");
			$("#FrequencyValueID").val("");
			
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

	function addFrequencyValue(category, tradeName) {
		
		if(tradeName == null || tradeName.trim() === ''){
			alert("Please select tradeName");
		}else if(category == "-1"){
			alert("Please select category");
		}else{
			
			$('#frequencyTableID tr:gt(1)').remove();
			
			$("#frequencyModal").modal("show");
		}
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
		
		var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
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
	}
	
	function removeFrequencyTR(TRID, stringToBeRemoved){
		
		console.log("stringToBeRemoved value: "+stringToBeRemoved);
		
		if(confirm("Are you sure you want to delete this row?")){
			
			var mainStringText = $("#FrequencyValueID").val(); 
			
    		var newValue = mainStringText.replace(stringToBeRemoved,'');
        	
    		$("#FrequencyValueID").val(newValue);
    		
			$("#"+TRID).remove();	
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
		console.log("values: "+values);
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
	function viewFrequencyValue(frequencyDetails) {
		console.log("value is: "+frequencyDetails);
		var tdTag = "";
		
		if(frequencyDetails!=""){
			if(frequencyDetails.includes(',')){
				var frequencyStr = frequencyDetails.split(',');
				
				for(var i = 0; i< frequencyStr.length; i++){
					
					var strValue = frequencyStr[i].split('\\$');
					
					tdTag += "<tr style='font-size: 14px;' >"
					   		+"<td style='text-align:center'>"+strValue[0]+"</td>"
							+"<td style='text-align:center'>"+strValue[1]+"</td>"
							+"</tr> ";
				}
				
				$("#frequencyViewTRID").html(tdTag);
				
				$("#frequencyViewModal").modal('show');
				
			}else{
				
				var strValue = frequencyDetails.split('\\$');
			
				tdTag += "<tr style='font-size: 14px;' >"
			   		+"<td style='text-align:center;'>"+strValue[0]+"</td>"
					+"<td style='text-align:center'>"+strValue[1]+"</td>"
					+"</tr> ";
				
				$("#frequencyViewTRID").html(tdTag);
				
				$("#frequencyViewModal").modal('show');
			}
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
	
	
	<script type="text/javascript">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function EditFrequencyValue(prescriptionID, frequencyDetailsID){

		$('#frequencyEditTableID tr:gt(1)').remove();
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var frequencyDetailsID = "";
				var frequency = "";
				var noOfDays = "";
				var trTag = "";
				
				for ( var i = 0; i < array.Release.length; i++) {
				
					frequencyDetailsID = array.Release[i].frequencyDetailsID;
					frequency = array.Release[i].frequency;
					noOfDays = array.Release[i].numberOfDays;
					check = array.Release[i].check;
				
					var TRID = "newFreqEditTRID"+frequencyDetailsID;
					
					trTag += "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+frequency+"</td>"
					   +"<td style='text-align:center'>"+noOfDays+"</td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='deleteFrequencyRow(\""+TRID+"\", \""+frequencyDetailsID+"\");'/></td>"
					   +"</tr>";
				}
				
				if(check == 1){
					
					$("#prescriptionFreqID").val(prescriptionID);
					$("#frequencyEditTRID").html(trTag);
					
					$("#frequencyEditModal").modal('show');
				}else{
					$("#prescriptionFreqID").val(prescriptionID);
					$("#frequencyEditModal").modal('show');
				}
				
			}
		};
		xmlhttp.open("GET", "RetrieveFrequencyDetailsByPrescriptionID?prescriptionID="+ prescriptionID , true);
		xmlhttp.send();
	}
	
	//function to delete prescription row
	function deleteFrequencyRow(trID, frequencyDetailsID){
		if(confirm("Are you sure you want to delete this row?")){
			deleteFrequency(trID, frequencyDetailsID);
		}
	}

</script>
<!-- Ends -->

<!-- Delete frequencyDetails -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function deleteFrequency(trID, frequencyDetailsID) {

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
					document.getElementById("successMSG").innerHTML = "Frequency details deleted successfully.";
					$("#"+trID).remove();
				}
				
			}
		};
		xmlhttp.open("GET", "DeleteFrequencyDetails?frequencyDetailsID="+ frequencyDetailsID , true);
		xmlhttp.send();
	}
</script>
<!-- Ends -->
	
	<!-- Add Complaint -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addComplaintRow(symptom, duration, other, comments){
 				console.log("value:"+duration+"-" +comments);
				addComplaintRow1(symptom, duration, other, comments)
    	}
		
    	var compCounter = 1;
    
		function addComplaintRow1(symptom, duration, other, comments, visitID){
			
			var TRID = "newCompTRID"+compCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+symptom+"<input type='hidden' name='newSymptom' value='"+symptom+"'></td>"
					   +"<td style='text-align:center'>"+duration+"<input type='hidden' name='newDuration' value='"+duration+"'></td>"
					   +"<td style='text-align:center'>"+other+"<input type='hidden' name='newOther' value='"+other+"'></td>"
					   +"<td style='text-align:center'>"+comments+"<input type='hidden' name='newComments' value='"+comments+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#complaintTRID"));
			
			compCounter++;
			  
			
			$("#symptomOID").val("");
			$("#presentDurationID").val("");
			$("#otherID").val("");
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
					
					//retrieveValues(visitID);
						
				}
			};
			xmlhttp.open("GET", "DeleteComplaint?complaintID="+ complaintID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	
	<!-- Add MHO -->
    <script type="text/javascript" charset="UTF-8">
    
    	function addMedicalHistoryRow(diagnosis, description, comments){
 				
 				addMedicalHistoryRow1(diagnosis, description, comments)
    	}
		
    	var medCounter = 1;
    
		function addMedicalHistoryRow1(diagnosis, description, comments){
			
			var TRID = "newMediTRID"+medCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+diagnosis+"<input type='hidden' name='newDiagnosis' value='"+diagnosis+"'></td>"
					   +"<td style='text-align:center'>"+description+"<input type='hidden' name='newDescription' value='"+description+"'></td>"
					   +"<td style='text-align:center'>"+comments+"<input type='hidden' name='newComments' value='"+comments+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#HistoryTRID"));
			
			medCounter++;
			  
			
			$("#MHOdiagnosisID").val("");
			$("#MHOdescriptionID").val("");
			$("#MHOcommentsID").val("");
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
					
					//retrieveValues(visitID);
						
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
					   +"<td style='text-align:center'>"+comments+"<input type='hidden' name='newComments' value='"+comments+"'></td>"
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
		function deleteMedicationRow(trID, medicationID){
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
					
					//retrieveValues(visitID);
						
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
					
					//retrieveValues(visitID);
						
				}
			};
			xmlhttp.open("GET", "DeleteSymptom?symptomID="+ symptomID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	<!-- Delete billing -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteBilling(billID,visitID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						document.getElementById("successMSG").innerHTML = array.Release[i].SuccessMessage;
						document.getElementById("errorMSG").innerHTML = array.Release[i].ErrorMessage;
						document.getElementById("exceptionMSG").innerHTML = array.Release[i].ExceptionMessage;
	
						if(document.getElementById("successMSG").innerHTML == "undefined"){
							document.getElementById("successMSG").innerHTML = "";
						}
	
						if(document.getElementById("errorMSG").innerHTML == "undefined"){
							document.getElementById("errorMSG").innerHTML = "";
						}
	
						if(document.getElementById("exceptionMSG").innerHTML == "undefined"){
							document.getElementById("exceptionMSG").innerHTML = "";
						}
					}
					
					retrieveBillValues(visitID);
					
				}
			};
			xmlhttp.open("GET", "DeleteBilling?billID="+ billID , true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	<!-- retrieve prescription values -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveValues(visitID) {
	
			visitID = document.getElementById("visitID").value;
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					var array_element = "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'> "+
					" <thead>";
					array_element += "<tr>"+
	                "<th>Trade Name</th>"+
	                "<th>Dose</th>"+
	                "<th>Frequency</th>"+
	                "<th>No. of Pills</th>"+
	                "<th>No. of Days</th>"+
	                "<th>Comments</th>"+
	                "<th>Action</th>"+
	            	"</tr>  </thead>  <tbody>";
					for ( var i = 0; i < array.Release.length; i++) {
						
							array_element += "<tr>";
							array_element += "<td>"+
							 array.Release[i].doseUnit+"</td>";
							array_element += "<td>"+
							 array.Release[i].dose+"</td>";
							array_element += "<td>"+
							 array.Release[i].frequency+"</td>";
							array_element += "<td>"+
							 array.Release[i].noOfPills+"</td>";
							array_element += "<td>"+
							 array.Release[i].numberOfDays+"</td>";
							array_element += "<td>"+
							 array.Release[i].comment+"</td>";
							array_element += "<td>"+
							 "<a onclick='confirmDelete("+array.Release[i].prescriptionID+","+array.Release[i].visitID+")';><img src='images/delete_icon_1.png'"+
							 " onmouseover='this.src='images/delete_icon_2.png'' onmouseout='this.src='images/delete_icon_1.png'' alt='Delete Prescription'"+
							 "title='Delete Prescription' style='margin-top:5px; height:24px;'/> </a></td>";
							array_element += "</tr>";
							
					}
					
					array_element += "</tbody> </table>";
					
					document.getElementById("OPDPResc").innerHTML = array_element;
	
					retrieveBillValues(visitID);
	
				}
			};
			xmlhttp.open("GET", "RetrievePrescriptionValues?visitID="
					+ visitID, true);
			xmlhttp.send();
		}
	</script>
	<!-- Ends -->
	
	<!-- Retrieve bill details -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveBillValues(visitID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					
					var totalBill = "";
					var array_element = "<table id='datatable-responsive' class='table table-striped table-bordered dt-responsive nowrap' cellspacing='0' width='100%'> "+
					" <thead>";
					array_element += "<tr>"+
	                    "<th>Description</th>"+
	                    "<th>Charges(Rs)</th>"+
	                    "<th>Action</th>"+
	                	"</tr>  </thead>  <tbody>";
					for ( var i = 0; i < array.Release.length; i++) {
							
							array_element += "<tr>";
							array_element += "<td>"+
							 array.Release[i].chargeType+"</td>";
							array_element += "<td>"+
							 array.Release[i].charge+"</td>";
							array_element += "<td>"+
							 "<a onclick='confirmBillDelete("+array.Release[i].billID+","+array.Release[i].visitID+")';><img src='images/delete_icon_1.png'"+
							 " onmouseover='this.src='images/delete_icon_2.png'' onmouseout='this.src='images/delete_icon_1.png'' alt='Delete Billing'"+
							 "title='Delete Billing' style='margin-top:5px; height:24px;'/> </a></td>";
							array_element += "</tr>";
							totalBill = array.Release[i].totalBill;
							
					}
	
					if(totalBill == "undefined"){
						totalBill = "";
					}	
					array_element += "<tr><td colspan='2' align='right'>"+
					"Total Bill Amount</td><td>"+
					+totalBill+"</td></tr>";
					array_element += "</tbody> </table>";
					document.getElementById("billDivID").innerHTML = array_element;
				}
			};
			xmlhttp.open("GET", "RetrieveBillValues?visitID="
					+ visitID, true);
			xmlhttp.send();
		}
	</script>
	<!-- ENds -->

    <%
		String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");
    
    	if(lastEneteredVisitList == null || lastEneteredVisitList == ""){
    		lastEneteredVisitList = "dummy";
    	}
    	
	%>
	
    <!-- Retrieve tabs by practice ID -->
    
    <%
   		 
	    HashMap<String, String> ValueMap = new HashMap<String, String>();
	    
	    ValueMap.put("clinician","OPD Visit,Prescription,Billing,Report,Medical Certificate,Referral Letter");
	    ValueMap.put("superAdmin","Screening");
	    ValueMap.put("billDesk","Prescription,Billing");
	    ValueMap.put("compounder","Prescription,Billing");
	    
	    System.out.println("ValueMap: "+ValueMap);
		
	    String tabs = ValueMap.get(form.getUserType());
	
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
    
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("Screening", "visit");
	
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
	
	<%
	
	int apptID = (Integer) request.getAttribute("apptID");
	
		//int apptID = (Integer) request.getAttribute("apptID");
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
	
	%>
	
	<script type="text/javascript">
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
		
		<!-- Change balance payment -->

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
    		
    		if($(input).is(":checked")){
    			$('#chequeDetailID').show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$('#chequeDetailID').hide(1000);
    			
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
			console.log("checkboxLength: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
				document.forms["genPhyBillID"].action = "AddNewBill";
				document.forms["genPhyBillID"].submit();
	   			return true;
			}else{
				$('.checkboxClass').prop("required", true);
				alert("Please select Payment type!");
			}
		}
		
    	function hideChequeDetailsDiv(input){
    		
    		if($(input).is(":checked")){
    			$("#cashDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#cashDetailID").hide(1000);
    			
    			$("#cashPaidID").val("");
    			$("#cashToReturnID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}

    	}
    	
    	function displayCardDiv(input){
    		
    		if($(input).is(":checked")){
    			$('#cardDetailID').show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$('#cardDetailID').hide(1000);
    			
    			$("#cardMobileNoID").val("");
    			$("#cMobileNoID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}
    		
    	}
    	
    	function displayChequeDetailsDivCreditNote(input){

    		if($(input).is(":checked")){
    			$("#creditNoteDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#creditNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}
    		
        	paymentCounter++;

    	}
    	
    	function displayChequeDetailsDivOther(input){

    		if($(input).is(":checked")){
    			$("#otherDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}else{
    			$("#otherDetailID").hide(1000);
    			
    			$("#otherTypeID").val("");
    			$("#otherTypeAmountID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    		}
    		
    	}
    	
    	function showAmountReturnedDiv(cashID, chequeID, cardID, otherID){
    		if($("#"+cashID).is(":checked") || $("#"+chequeID).is(":checked") || 
    				$("#"+cardID).is(":checked") || $("#"+otherID).is(":checked")){
    			$("#amountReturnedDivID").show(500);
    		}else{
    			$("#amountReturnedDivID").hide(500);
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
					
					String idValues = paymentMap.get(arr[i]);
					
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
		function uploadLabReport(){
			document.forms["labRepForm"].action = "EditLabReport";
			document.forms["labRepForm"].submit();
		}
	</script>

    <script type="text/javascript">
	   	 function CheckDiagnosisField(){

	   		var autoSelecter = dojo.widget.byId("search");
			var diag = autoSelecter.getSelectedValue();
			
			if(diag == null || diag ==""){
    			alert("Please add diagnosis field");
			}else{
				document.forms["visitrForm"].action = "EditVisit";
				document.forms["visitrForm"].submit();
	   			return true;
			}
    	}
		
</script>

    <script type="text/javascript">
    	function myFunction1(SpanID,value){
    		console.log("value is::"+value);
    		if(value.includes(".")){
    			$("#"+SpanID).html("Please enter Integer value only"); 
    			$('#send').prop('disabled', true);
    		}else{
    			$("#"+SpanID).html(""); 
    			$('#send').prop('disabled', false);
    			return true;
    		}
    			
    	}
    
    </script>
    
    
     <sx:head/>
  
  </head>
   
  <%
    	if(lastEneteredVisitList == "success"){ 
  %>

  <!-- <body class="nav-md" onload="getValuesByChargeType(description.value);"> -->
  <body class="nav-md" >
  
  <%
        } else{
  %>

  <!-- <body class="nav-md" onload="getValuesByChargeType(description.value);"> -->
  <body class="nav-md">

  <%
        }
  %>
  
  <!-- Start Frequency Add Modal-->
  
  <div id="frequencyModal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">Frequency DETAILS</h4>
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
          <button type="button" class="btn btn-default" id="patientDetailsCloseID" data-dismiss="modal">Close</button>
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
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">Frequency DETAILS</h4>
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
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">View Frequency DETAILS</h4>
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
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
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
                    <li><a href="RenderEditProfile"> Profile</a></li>
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
                        <h3>Edit Existing VISIT</h3>
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
					  	//System.out.println("..."+tabs);
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
		              			
		              		}
		              	%>
					 </ul>
			    
			        
				 <div id="myTabContent1" class="tab-content">
				
			     <!-- Visit div -->
				 					 	
			     <div class="tab-pane fade in active" id="visit">
				    <form class="form-horizontal form-label-left" novalidate action="EditSurvey" name="visitrForm" id="visitrForm" method="POST" style="margin-top:20px;">
				    
				       <input type="hidden" name="id" id="volunteerID" value="<s:property value="volunteerID"/>">
			         <input type="hidden"  name="clinicID" id="clinicID" value="<s:property value="clinicID"/>">
				    <%-- <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>"> --%>
			          
				   <div class="col-md-6"><label for="name" style="font-size: 20px; font-weight: bold; padding-left: 29px;">Volunteer Details:</label></div>
				    <div class="row" style="padding-top: 50px;">	
				     <div class="col-md-9">
				     	<div class="col-md-3"><label for="name" style="font-size: 15px; padding-left: 82px;">Name*:</label></div>
		               	<div class="col-md-3">
								<input class="form-control" name="firstName"
								style="text-transform: capitalize;"
								value="<s:property value='firstname'/>" id="firstNameID"
								placeholder="First Name" required="required" type="text"
								onblur="validate(this.value, 'firstNameID');">
						</div>
						<div class="col-md-3">
								<input class="form-control" name="middleName"
								style="text-transform: capitalize;"
								value="<s:property value='middlename'/>" id="middleNameID"
								placeholder="Middle Name" type="text"
								onblur="validate(this.value, 'middleNameID');">
						</div>
						<div class="col-md-3">
								<input class="form-control" name="lastName"
								style="text-transform: capitalize;"
								value="<s:property value='lastname'/>" id="lastNameID"
								placeholder="Last Name" required="required" type="text"
								onblur="validate(this.value, 'lastNameID');">
						</div>
	                  </div>
	                  </div>
	                  
	                  <div class="row" style="padding-top: 20px;">	
	                  <div class="col-md-9">
	                  <div class="col-md-3"><label for="name" style="font-size: 15px; padding-left: 75px;">Mobile*:</label></div>
							<div class="col-md-3">
							<input type="number" name="mobile" placeholder="Mobile"
							id="mobileID" value="<s:property value='mobile'/>" class="form-control">
							</div>
						
							
						<div class="col-md-2"><label for="name" style="font-size: 15px; padding-left: 33px;">Email:</label></div>
							<div class="col-md-4">
							<input type="email" name="emailID" value="<s:property value='email'/>"
							placeholder="Email Address" class="form-control">
						</div>
					</div>
					</div>
								
				<div class="row" style="padding-top: 20px;">
				<div class="col-md-9">
				<div class="col-md-3" style="margin-left: -3%;">
						<label for="name" style="font-size: 15px; padding-left: 19px;" class="control-label">Date Of Birth*:</label>
						<div class="input-group">
							<input type="text" class="form-control" name="dob" id="estdDateID" placeholder="dd-MM-yyyy">
							<span class="input-group-addon"><i class="fa fa-calendar"></i> </span>
						</div>
						</div>							
	                  <<%-- div class="col-md-6">
	                  	<div class="col-md-5"><label for="name" style="font-size: 15px; padding-left: 19px;">Date Of Birth*:</label></div>
		               	<div class="col-md-6" style="padding-left: 39px;"><input type="text" class="form-control"  value="<s:property value="dob"/>" /></div>
	                  </div>  --%> 
	              
	              <div class="col-md-3" align="center">
						<label for="name"style="font-size: 15px; padding-left: 26px;" class="control-label">Age</label>
						<div class="input-group"><input type="text" class="form-control" name="age" id="ageID" placeholder="dd-MM-yyyy"></div>
					</div>
	              	<%-- <div class="col-md-3">
	                  	<div class="col-md-6"><label for="name" style="font-size: 15px; padding-left: 26px;">Age*:</label></div>
		               	<div class="col-md-5"><input type="text" class="form-control"  value="<s:property value="age"/>" /></div>
	                  </div> --%>                      
	            
				     <div class="col-md-3">
				     	<div class="col-md-5"><label for="name" style="font-size: 15px; padding-left: 0px;">Gender*:</label></div>
		               	<div class="col-md-7"><input type="text" class="form-control" value="<s:property value="gender"/>" /></div>
			          </div>             
	              </div>   
	              </div>
	              <div class="row" style="padding-top: 20px;">
					<div class="col-md-9">
						<div class="col-md-3"><label for="Address" style="font-size: 15px; padding-left: 60px;">Address*:</label></div>
						<div class="col-md-9">
							<s:textarea name="address" class="form-control" placeholder="Address"></s:textarea>
							</div>
						</div>
					</div>
			
			<div class="col-md-6"><label for="name" style="font-size: 20px; font-weight: bold; padding-left: 29px; padding-top: 50px;">Screening:</label></div>
	                      
                  <%-- <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Is the volunteer’s age between 18 years to 25 years? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="age"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div> --%>
				  
				   <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Has the volunteer travelled outside India or have returned from outside India in the past 3 months? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="travelOutside"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div>
	                      
	              <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Has the volunteer been admitted to a hospital or has undergone ANY surgery/procedure in the past 5 months? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="admitted"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div>        
	                      
	               <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Does the volunteer suffer from any of the following? </font>
				  </div>
				  <s:select list="#{'Asthma':'Asthma','Cough':'Cough','Cold':'Cold','Breathing_issues':'Breathing_issues','None':'None'}" headerKey="" id="vendorTypeID" value="vendorTypeListValues1" Class="multiCheckboxClass" cssStyle="width: 100%" name="suffer_from" multiple="true" ></s:select>
				  
				  <!-- <div class="col-md-6" style="padding-left: 40px;">
				  <div id="suffer_fromID" style="padding-top:10px;">
					
				    		<input type="checkbox" name="suffer_from" value="Asthma" id="myCheck" class="myCheckClass">Asthma&nbsp;&nbsp;&nbsp;&nbsp;  
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="suffer_from" value="Cough" id="myCheck"  class="myCheckClass">Cough&nbsp;&nbsp;&nbsp;&nbsp;   
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="suffer_from" value="Cold" id="myCheck"  class="myCheckClass" >Cold &nbsp;&nbsp;&nbsp;&nbsp;  
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="suffer_from" value="Breathing_issues" id="myCheck"  class="myCheckClass">Breathing issues&nbsp;&nbsp;&nbsp;&nbsp;  
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="suffer_from" value="None" id="myCheck"  class="myCheckClass">None
				    	</div>
				   </div>   -->   
	                   
	               <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Has the volunteer undergone any of the following surgeries/procedures? </font>
				  </div>
				  
				  <s:select list="#{'Bi_pass':'Bi_pass','Angio_Surgery':'Angio_Surgery','Organ_Transplant':'Organ_Transplant','None':'None'}" headerKey="" id="vendorTypeID" value="vendorTypeListValues2" Class="multiCheckboxClass" cssStyle="width: 100%" name="surgeries" multiple="true" ></s:select>
				  
				  <!-- <div class="col-md-6" style="padding-left: 40px;">
				  <div id="surgeriesID" style="padding-top:10px;">
					
				    		<input type="checkbox" name="surgeries" value="Bi-pass" id="myCheck" class="myCheckClass">Bi-pass&nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="surgeries" value="Angio-Surgery " id="myCheck"  class="myCheckClass">Angio-Surgery  &nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="surgeries" value="Organ Transplant" id="myCheck"  class="myCheckClass" >Organ Transplant &nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="surgeries" value="None" id="myCheck"  class="myCheckClass">None 
				    	</div>  
	               </div>   -->    
	                
	              <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Does the volunteer suffer from any of the following? </font>
				  </div>
				  <s:select list="#{'Diabetes':'Diabetes','Friends':'Hypertension', 'None':'None'}" headerKey="" id="vendorTypeID" value="vendorTypeListValues3" Class="multiCheckboxClass" cssStyle="width: 100%" name="diabOrHypertension" multiple="true" ></s:select>
				  
				 <!--  <div class="col-md-6" style="padding-left: 40px;">
				  <div id="diabOrHypertensionID" style="padding-top:10px;">
					
				    		<input type="checkbox" name="diabOrHypertension" value="Diabetes" id="myCheck" class="myCheckClass">Diabetes&nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="diabOrHypertension" value="Hypertension" id="myCheck"  class="myCheckClass">Hypertension  &nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="diabOrHypertension" value="None" id="myCheck"  class="myCheckClass">None 
				    	</div>  
	               </div>     
	                -->
	               <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Is the volunteer aware of the implications and results of what the COVID-19 virus has on human health? Is the volunteer  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; aware of the complications and risk involved in the infection?  </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="awareness"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div>  
	                      
	               <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Does the volunteer’s family know that they are volunteering for helping citizens during this COVID-19 pandemic? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="knowFamily"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div>     
	                      
	               <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Does the volunteer suffer from any psychological issues? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="psychological_issue"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div>   
	               
				  <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Does the volunteer have training about what precautions and measures should he/she undertake for themselves and their &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  family to avoid COVID-19 infection? </font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="training"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div> 
                   
                   <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Has any of the following from the volunteer’s acquaintance tested positive for COVID-19? </font>
				  </div>
				  <s:select list="#{'Family_Members':'Family_Members','Friends':'Friends', 'Relatives':'Relatives', 'None':'None'}" headerKey="" id="vendorTypeID" value="vendorTypeListValues4" Class="multiCheckboxClass" cssStyle="width: 100%" name="tested_positive" multiple="true" ></s:select>
				  
				  <!-- <div class="col-md-6" style="padding-left: 40px;">
				  <div id="tested_positiveID" style="padding-top:10px;">
					
				    		<input type="checkbox" name="tested_positive" value="Family_Members" id="myCheck" class="myCheckClass">Family Members&nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="tested_positive" value="Friends" id="myCheck"  class="myCheckClass">Friends  &nbsp;&nbsp; &nbsp;&nbsp; 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="tested_positive" value="Relatives" id="myCheck"  class="myCheckClass">Relatives 
				    		&nbsp;&nbsp; &nbsp;&nbsp; <input type="checkbox" name="tested_positive" value="None" id="myCheck"  class="myCheckClass">None 
				    	</div>  
	               </div>   -->
                   
                   <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Who should be contacted from the volunteer’s family in case of any emergency?</font>
				  </div>
				  
				  <div class="row" style="padding-top: 20px;">
				<div class="col-md-12" style="padding-top: 20px;">						
	                  <div class="col-md-4">
	                  	<div class="col-md-2"><label for="name" style="font-size: 15px; padding-left: 19px;">Name:</label></div>
		               	<div class="col-md-10" style="padding-left: 39px;"><input type="text" class="form-control"  value="<s:property value="emergencyContact_name"/>" /></div>
	                  </div>  
	              
	              	<div class="col-md-4">
	                  	<div class="col-md-4"><label for="name" style="font-size: 15px; padding-left: 26px;">Relation:</label></div>
		               	<div class="col-md-8"><input type="text" class="form-control"  value="<s:property value="emergencyContact_relation"/>" /></div>
	                  </div>                      
	            
				     <div class="col-md-4">
				     	<div class="col-md-5"><label for="name" style="font-size: 15px; padding-left: 0px;">Mobile No.:</label></div>
		               	<div class="col-md-7"><input type="number" class="form-control" value="<s:property value="emergencyContact_mobile"/>" /></div>
			          </div>             
	              </div>   
	              </div>
				  
				  <div class="col-md-10" style="padding-top:20px;">
				    <font style="font-size: 14px;font-weight: bold;padding-left: 30px; ">Has the volunteer been advised to keep either AADHAAR CARD, PAN CARD or any GOVERNMENT ISSUED ID PROOF &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; with him at all times during the volunteering?</font>
				  </div>
				  <div class="col-md-2" style="padding-top:20px;">
				    <s:radio list="#{'Yes':'Yes','No':'No'}" name="IDproof"  onclick="displayConentDocDiv(this.value);"  ></s:radio>
				  </div> 
	                         
	                      <!-- <div class="ln_solid"></div> --> 
	                      
	                      <div class="form-group" >
	                        <div class="col-md-12"  style="padding-top: 50px;" align="center">
	                          <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
	                          
	                        <button id="send" type="submit" class="btn btn-success " >Update</button>
			                 <%-- <%
					        	}
			                 %>
	                           --%>
	                          
	                        </div>
	                      </div>
				        
				      </form>
				      
			      </div>
			    <!--  END --> 
			    
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
    
    document.addEventListener('DOMContentLoaded', function() {

    	$('#single_cal3').attr("readonly","readonly");
    	
        $('#single_cal3').daterangepicker({
          singleDatePicker: true,
          calender_style: "picker_3"
        }, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
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
    		
    		if(new Date(today).getTime() <= calendarDate.getTime()){
    			return true;
    		}else{
    			alert("Selected date must be greater than current date.");
    			$(this).val("");
    		}
    	});
        
		$('.clinic_start').datetimepicker({
	        language:  'en',
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			disabledTimeIntervals: [ [ moment().hour(0), moment().hour(8).minutes(30) ], [ moment().hour(20).minutes(30), moment().hour(24) ] ],
			forceParse: 1
	    });
		
		setTimeout(function(){
			//alert('hii');
			$('#visitToTimeHHID').attr("value",$("#visitHiddenTimeToID").val());
			$('#visitFromTimeHHID').attr("value",$("#visitHiddenTimeFromID").val());
		},500);
        
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
        $("#search").autocomplete({
            source : function(request, response) {
                    $.ajax({
                            url : "SerachDiagnosis",
                            type : "POST",
                            data : {
                                    searchName : request.term
                            }, 
                            dataType : "json",
                            success : function(jsonResponse) {
                                    response(jsonResponse);
                                    //alert(jsonResponse);
                            },
                            error: function(errorMsg){
                            	alert("ERROR: "+errorMsg);
                            }
                    });
                    }
            });
    });
    
    </script>
    
    <script type="text/javascript">
    $(window).load( function() {
    	
 
    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
			$('#estdDateID').daterangepicker({
	            singleDatePicker: true,
	            calender_style: "picker_3"
	          }, function(start, end, label) {
	            console.log(start.toISOString(), end.toISOString(), label);
	        });
	        
			/* To calculate age  estd year*/
			$('#estdDateID').on('hide.daterangepicker', function(ev, picker) {
				var selectedDate = $(this).val();
				var dateSplit = selectedDate.split('-');
				var date = new Date();   // JS Engine will parse the string automagically
				var date21=date.getDate();
				var month = date.getMonth()+1;
				var year1=date.getFullYear();
				var userDOB = dateSplit[0] + "-" + dateSplit[1] + "-" + dateSplit[2];
				console.log('dateOfBirth: '+userDOB);		
				var birthYear;
				if(dateSplit[1]>month||dateSplit[1]==month){
					if(dateSplit[1]==month){
						if(dateSplit[0]>date21){
						     birthYear = year1-dateSplit[2]-1;
						}else{
						     birthYear = year1-dateSplit[2];
						}
					}else{
						 birthYear = year1-dateSplit[2]-1;
					}
				}else{
					birthYear = year1-dateSplit[2];
				}
				console.log('birthYear: '+birthYear);		
				document.getElementById("ageID").innerHTML = birthYear;
			
			}); 
		}
	</script>
    
  </body>
</html>