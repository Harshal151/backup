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

    <title>View Patient Visit | E-Dhanvantari</title>

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
			RequestDispatcher dispatcher = request.getRequestDispatcher("Pindex.jsp");
			dispatcher.forward(request,response);
		}
		
		int userID = form.getUserID();
		String doctName= form.getFullName();
		System.out.println("doctname:"+doctName);
		
		int patientID = form.getPatientID();
		String FName = form.getFirstName(); 
		String MName = form.getMiddleName();
		String LName = form.getLastName();
		String patientName = FName+" "+MName+" "+LName;
		
		int changedClinicID = form.getClinicID();
		System.out.println("changedClinicID on tele:"+changedClinicID);
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		PatientDAOInf patientDaoInf = new PatientDAOImpl();
		
		ConfigurationUtil configXMLUtil1 = new ConfigurationUtil();
		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		
		String fullName = form.getFullName();
		LinkedHashMap<String, String> frequencyList = configXMLUtil1.getFrequencySortedListList(form.getPracticeID());
		
		String clinicName = daoInf.retrieveClinicName(form.getClinicID());
		
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
      function windowOpen(){
        document.location="patientWelcome.jsp";
      }
      
      function windowOpen1(){
          document.location="patientWelcome.jsp";
        }
        
      function windowOpen2(){
    	  $('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

          document.location="RenderPatientVisitList";
        }
    </script>
    
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
	
		/* function downloadLabReport(report){
			var queryString = "";
			queryString += "?reportsID="+report;
			
			document.forms["labRepForm"].action = 'DownloadLabReport'+queryString;
			document.forms["labRepForm"].submit();
		} */
		
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
	
	
	<%
		String referralLetterText = (String) request.getAttribute("referralLetter");
		String referralLetterCheck = (String) request.getAttribute("referralLetterCheck");
		System.out.println("check1 value in jsp"+referralLetterCheck);
		/* String referralLetterFinalText = "";
		
		if (referralLetterText == null || referralLetterText == "") {
			referralLetterText = "";
		} else {
			
			String[] array = referralLetterText.split(" ");
	
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
					
					if(idValue.equals("doctName")){
						
						List<String> dorctList = configXMLUtil1.getReferralDoctorList();
						
							String selectTag = "<select class='form-control' name='doctName' id='doctName' style='width:20%;' id='"+ idValue +"' >";
							
							Iterator<String> iterator = dorctList.iterator();
							
							while (iterator.hasNext()) {
								String value = iterator.next();
								
								if(value.equals(fieldValue)){
									selectTag += "<option value='"+value+"' selected>"+value+"</option>";
								}else{
									selectTag += "<option value='"+value+"'>"+value+"</option>";
								}
							}
							selectTag += "</select>";
							
							referralLetterFinalText += fieldValueTemp
							.replace(
									fieldValueTemp,selectTag);
						
					}else{
						referralLetterFinalText += fieldValueTemp
						.replace(
								fieldValueTemp,
								"<textarea class='form-control' rows='4'  name='"+ idValue +"' id='"+ idValue +"'>"+ fieldValue + " </textarea>") + " ";	
					}
	
				} else if (array[i].contains("\n")) {
					referralLetterFinalText += array[i] + "<br><br>";
				} else if (array[i].contains("\t")) {
					referralLetterFinalText += array[i]
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				} else {
					referralLetterFinalText += array[i] + " ";
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
			
		function downloadVisit(){
	 		var autoSelecter = dojo.widget.byId("search");
			var diag = autoSelecter.getSelectedValue();
			
			if(diag == null || diag ==""){
    			alert("Please add diagnosis field");
			}else{
				document.forms["visitrForm"].action = "UpdateDownloadGenPhyVisit";
				document.forms["visitrForm"].submit();
	   			return true;
			}
		}	
		/* var	diag =  $("#search").val();
			
			if(diag == null || diag ==""){
	   			
	   			alert("Please add diagnosis field");
	   			
	   			return false;
	   		}else{ 

	   			document.forms["visitrForm"].action = "UpdatePrintGenPhyVisit";
				document.forms["visitrForm"].submit();
	   			return true;
	   		} */
			
	
	
		function enablePrintButton(){
			document.getElementById("printButton").disabled = false;
		}
	
		function printPrescription(){
			document.forms["genPhyPrescID"].action = "UpdatePrintGenPhyPrescription";
			document.forms["genPhyPrescID"].submit();
			$("#PrintPrescID").attr("disabled", "disabled");
		}
		
		function downloadPrescription(){
			document.forms["genPhyPrescID"].action = "UpdateDownloadGenPhyPrescription";
			document.forms["genPhyPrescID"].submit();
			//$("#DownPrescID").attr("disabled", "disabled");
		}
		
		function updatePrescription(){
			document.forms["genPhyPrescID"].action = "EditPrescription";
			document.forms["genPhyPrescID"].submit();
			 $("#savePrescID").attr("disabled", "disabled");
		}
		
		function printBilling(){
			var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			console.log("checkboxLength: "+checkboxLength);
			if(checkboxLength>0){
				$('.checkboxClass').prop("required", false);
				document.forms["genPhyBillID"].action = "UpdatePrintBill";
				document.forms["genPhyBillID"].submit();
	   			return true;
			}else{
				$('.checkboxClass').prop("required", true);
				alert("Please select Payment type!");
			}
		}
		
			function downloadBilling(){
				var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
				console.log("checkboxLength: "+checkboxLength);
				if(checkboxLength>0){
					$('.checkboxClass').prop("required", false);
					document.forms["genPhyBillID"].action = "UpdateDownloadBill";
					document.forms["genPhyBillID"].submit();
		   			return true;
				}else{
					$('.checkboxClass').prop("required", true);
					alert("Please select Payment type!");
				}
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
			console.log("inside function..");
	 	var fileInput = document.getElementById(input);
	    var filePath = fileInput.value;
	    console.log("extension is::"+filePath);
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
	
	    /* if(companyName.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	        companyName = companyName.replace(regExp, "_");
	    }else{
	    	companyName = companyName;
	    } */
	
	   /*  if(disease.indexOf(" ") != "-1"){
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
	    } */
	    
	    var medicalCertificate = companyName+ "$" + disease + "$" +dutyDays+ "$" + wef + "$" +place+ "$" +visitDte;
	   // var medicalCertificate = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in '"+companyName+ "@companyName', is suffering from '" + disease + "@disease' and I consider that the period of absence from duty of '"+ dutyDays + "@dutyDays' days with effect from '"+ wef + "@wef' is absolutely necessary for the restoration of his/her health.\n\n\n Place: '"+place+ "@place' \n\n\n Date: '" +visitDte + "@date11' \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
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
	
	 /*    if(doctName.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	    	doctName = doctName.replace(regExp, "_");
	    }else{
	    	doctName = doctName;
	    } */
	
	    /* if(conditionText.indexOf(" ") != "-1"){
	    	var regExp = new RegExp(' ','g');
	    	conditionText = conditionText.replace(regExp, "_");
	    }else{
	    	conditionText = conditionText;
	    } */
	
	    var referralLetter = visitDte+ "$" + doctName + "$" +patientName+ "$" + conditionText + "$" + doctorName;
	    //var referralLetter = "Date: "+visitDte+ " \n\n\n Dear Dr. '"+ doctName + "@doctName', \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n '" + conditionText + "@conditionText' \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
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
	function editPatientProfile(patientID){
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();

		location.href="RenderPatientProfile?patientID="+patientID;
		
	}
	
	
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
			xmlhttp.open("GET", "ChangeValuesByDrugName?drugName="+ drugName, true);
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
    
    function addOPDPrescription(drugName, frequency1, visitID, comment, noOfPills,noOfDays){
		console.log("frequency val at view::"+frequency);
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
		
		//var e = document.getElementById("categoryID");
        //var strUser = e.options[e.selectedIndex].text;
        
        console.log("frequency val at view::"+frequency);
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
    
    	function addComplaintRow(symptom, duration,comments){
 				console.log("value:"+duration+"-" +comments);
				addComplaintRow1(symptom, duration,comments)
    	}
		
    	var compCounter = 1;
    
		function addComplaintRow1(symptom, duration, comments, visitID){
			
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
	
	<%
		String lastEnteredPrescList = (String) request.getAttribute("lasteEnteredPrescList");
    
    	if(lastEnteredPrescList == null || lastEnteredPrescList == ""){
    		lastEnteredPrescList = "dummy";
    	}
	%>
    
    <%
		String lastEnteredBillList = (String) request.getAttribute("lasteEnteredBillList");
    
    	if(lastEnteredBillList == null || lastEnteredBillList == ""){
    		lastEnteredBillList = "dummy";
    	}
	%>
	
    <!-- Retrieve tabs by practice ID -->
    
    <%
   		 
	    HashMap<String, String> ValueMap = new HashMap<String, String>();
	  
	   /*  ValueMap.put("patient","Tele. Consult,Prescription,Billing,Report");
	    
		System.out.println("ValueMap: "+ValueMap);
		
	    String tabs = ValueMap.get("patient"); */
	  
	    String tabs = "Tele. Consult,Prescription,Billing,Report";
		   
	    System.out.println("---tab-usertype:"+tabs);
	    
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
    
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("Tele. Consult", "visit");
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
	
	int apptID = (Integer) request.getAttribute("apptID");
	
		//int apptID = (Integer) request.getAttribute("apptID");
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = (String) request.getAttribute("visitType");
	//String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
	
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
				document.forms["genPhyBillID"].action = "EditBill";
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
  
   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
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
            	<jsp:include page="patientLeftMenu.jsp"></jsp:include>
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

              <ul class="nav navbar-nav navbar-right" style="width: 41%;">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                  	<%
              		if(patientDaoInf.retrieveProfilePic(patientID) == null || patientDaoInf.retrieveProfilePic(patientID).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%=patientName%>
	                
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
                  
                  <!-- <li><a href="RenderEditProfile"> Profile</a></li> -->
                    <li><a href="javascript:editPatientProfile(<%=patientID%>)"> Profile</a></li>
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
                      <div class="title_left" style="width:100%">
                        <h3>Patient's VISIT</h3>
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
				    <form class="form-horizontal form-label-left" novalidate action="EditVisit" name="visitrForm" id="visitrForm" method="POST" style="margin-top:20px;">
				    
				    <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
			          
				    <div class="row">	
				     <div class="col-md-6 col-sm-6 col-xs-12">
				     	<div class="col-md-4"><label for="name" style="font-size: 15px; padding-left: 49px; margin-top: 0.5em;">Name:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" style=" margin-top: 0.2em;" readonly="readonly" value="<s:property value="firstName"/> <s:property value="lastName"/>" /></div>
			          </div>
	                    
	                  <div class="col-md-6 col-sm-6 col-xs-12">
	                  	<div class="col-md-4"><label for="name" style="font-size: 15px; margin-top: 0.5em; padding-left:62px;">Age:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" style="margin-top: 0.5em;" value="<s:property value="age"/>" /></div>
	                  </div>                      
	              </div> 
	              
	              
	              <div class="row" style="padding-top: 10px;">	
				     <div class="col-md-6 col-sm-6 col-xs-12">
				     	<div class="col-md-4"><label for="name" style="font-size: 15px; padding-left: 75px; margin-top: -1%;">ID:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" style="margin-top: -1%;" value="<s:property value="registrationNo"/>" /></div>
			          </div>
	                    
	                  <div class="col-md-6 col-sm-6 col-xs-12">
	                  	<div class="col-md-4"><label for="name" style="font-size: 15px; margin-top: 0.5em; padding-left:43px">Mobile:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" style="margin-top: 0.5em; padding-left:11px"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status" value="<s:property value="mobile"/>" /></div>
	                  </div>                      
	              </div>  
	              
	              
	              <div class="row" style="padding-top: 10px;">	
				     <div class="col-md-6 col-sm-6 col-xs-12">
				     	<div class="col-md-4"><label for="name" style="font-size: 15px; padding-left: 38px; margin-top: -1%;">Gender:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" style="margin-top: -1%;" value="<s:property value="gender"/>" /></div>
			          </div>
	                    
	                  <div class="col-md-6 col-sm-6 col-xs-12">
	                  	<div class="col-md-4"><label for="name" style="font-size: 15px; margin-top: 0.5em;">Blood Group:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" style="margin-top:0.5em;" value="<s:property value="bloodGroup"/>" /></div>
	                  </div>                      
	              </div>   
	              
	             
	               <div class="row" style="padding-top: 10px;">	
				     <div class="col-md-6 col-sm-6 col-xs-12">
				     	<div class="col-md-4"><label for="name" style="font-size: 15px; padding-left: 6px;">Date of visit:</label></div>
		               	<div class="col-md-4"><input type="text" class="form-control" readonly="readonly" name="visitDate" value="<%=currentDate%>" /></div>
			          </div>      
	              </div>        
	                       			           
				  <div class="item form-group" >
				  	<div class="col-md-8 col-sm-8 col-xs-12" style="padding-top: 20px;">
	                        <div class="col-md-3 col-sm-3 col-xs-4"><label for="User Type" style="font-size: 15px; padding-left: 10px;">Diagnosis*</label></div>
	                       
	                          <div class="col-md-6 col-sm-5 col-xs-8">
	                          	
	                          	<input type="text" readonly="readonly"  class="form-control" id="search" name="cancerType" required="required" value="<s:property value="cancerType"/>" placeholder="Diagnosis"> 
	                          </div>
	                		<font style="color: red;" id = "diagnosisSpanID"></font>
	                </div>
	                </div>
	             
	             <div class="row" >
		              <div class="col-md-3"><label for="User Type" style="font-size: 15px; padding-left: 15px; padding-top: 15px;">Present Complaints</label></div>
		            	 </div> 
	             			<div class="item form-group">
		                      <div class="control-label col-md-10  col-sm-12 col-xs-12" style="padding-left:50px;">
						      	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					                <thead>
										<tr>
					                        <th style="width: 30%; text-align: center;">Symptom</th>
					                        <th style="width: 20%; text-align: center;">Duration (Days)</th>
					                        <th style="width: 50%; text-align: center;">Comments</th>
					                        
					                    </tr>
					                 </thead>
					                 <tbody> 
					                 
      								<tr id="complaintTRID"></tr>
					                           	
					                   <s:iterator value="complaintList">
					                       <tr id="visitComplaintTRID<s:property value="complaintID"/>">
					                           <td style="font-size: 14px;text-align: center;"><s:property value="symptomCheckName"/></td>
					                           <td style="font-size: 14px;text-align: center;"><s:property value="duration"/></td>
					                           <td style="font-size: 14px;text-align: center;"><s:property value="comments"/></td>
					                       	   
					                       </tr>
					                   </s:iterator>
					                </tbody>
					             </table>
					        
						 </div> 
      				</div>
      			
      			
      			<div class="item form-group">
		              <div class="col-md-2 col-xs-6 col-sm-6"><label for="User Type" style="font-size: 15px; padding-left: 5px; padding-top: 15px;">Medical History</label></div>
		            	 </div> 
	             			<div class="item form-group">
		                      <div class="control-label col-md-10  col-sm-12 col-xs-12" style="padding-left:50px; margin-top:-1%;">
						      	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					                <thead>
										<tr>
					                        <th style="width: 28%;text-align: center;">Diagnosis</th>
					                        <th style="width: 60%;text-align: center;">Description</th>
					                        
					                    </tr>
					                 </thead>
					                 <tbody> 
					                 
      								<tr id="HistoryTRID"></tr>
					                           	
					                   <s:iterator value="historyList">
					                       <tr id="visitHistoryTRID<s:property value="historyID"/>">
					                           <td style="font-size: 14px;text-align: center;"><s:property value="diagnosis"/></td>
					                           <td style="font-size: 14px;text-align: center;"><s:property value="description"/></td>
					                       	  
					                       </tr>
					                   </s:iterator>
					                </tbody>
					             </table>
					         </div>
						 </div> 
						 
	                      	<div class="row" >
						        <div class="col-md-3"><label for="User Type" style="font-size: 15px; padding-left: 13px; padding-top: 15px;">Personal History</label></div>
						    </div> 
						    <div class="item form-group">
						    <div class="control-label col-md-10 col-sm-12 col-xs-12" style="padding-left:30px; margin-top:-3%;">
		                     	<div class="row" style="padding-top: 30px;">
		                     	   <div class="col-md-6 col-sm-6"> 
		                			<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Smoking</label></div>
		                	 		<div class="col-md-4"  style="padding-top: 3px; padding-left:34px;"><s:radio list="#{'Yes':'Yes','No':'No'}" name="personalHistorySmoking"  class="radioClass" onclick="displayConentDocDiv(this.value);"></s:radio></div>
								</div>
									
				     			<div class="col-md-6 col-sm-6"> 
									<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Comments</label></div>
									<div class="col-md-8"><input type="text"  readonly="readonly" name="personalHistorySmokingDetails"  class="form-control" placeholder="comments" value="<s:property value="personalHistorySmokingDetails"/>"></div>
		                 		</div>
		                 		</div>
		             
	                      		<div class="row" style="padding-top: 20px;">
	                      		<div class="col-md-6 col-sm-6">             
		                			<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Alcohol</label></div>                    
			               			<div class="col-md-4"  style="padding-top: 3px;  margin-right:10px; padding-left:34px;"><s:radio list="#{'Yes':'Yes','No':'No'}"  class="radioClass" name="personalHistoryAlcohol" onclick="displayConentDocDiv(this.value);"></s:radio></div>
								</div>
								
								<div class="col-md-6 col-sm-6"> 
									<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Comments</label></div>
									<div class="col-md-8"><input type="text"  readonly="readonly" name="personalHistoryAlcoholDetails"  class="form-control" placeholder="comments" value="<s:property value="personalHistoryAlcoholDetails"/>"></div>
								</div>
						 		</div>
		                 	
		               			<div class="row" style="padding-top: 20px;">
	                      		<div class="col-md-6 col-sm-6">           
		                			<div class="col-md-4"><label for="User Type" style="font-size: 15px;" >Tobacco</label></div>
			               			<div class="col-md-4" style="padding-top: 3px; margin-right:10px;padding-left:34px;"><s:radio list="#{'Yes':'Yes','No':'No'}"  class="radioClass" name="personalHistoryTobacco"  onclick="displayConentDocDiv(this.value);"></s:radio></div>
								</div>	
								<div class="col-md-6 col-sm-6"> 
									<div class="col-md-4"><label for="User Type" style="font-size: 15px;" >Comments</label></div>
 	                          		<div class="col-md-8"><input type="text"  readonly="readonly"  name="personalHistoryTobaccoDetails"  class="form-control" placeholder="comments" value="<s:property value="personalHistoryTobaccoDetails"/>"></div>
	                        	</div>
		                		</div>
		                 	
		                 		<div class="row" style="padding-top: 20px;">
	                      		<div class="col-md-6 col-sm-6">              
		                			<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Food Choice</label></div>
			               			<div class="col-md-5" style="padding-top: 3px; margin-right:10px; padding-left:10px;"><s:radio list="#{'Veg':'Veg','Non-Veg':'Non-Veg'}"  class="radioClass" name="personalHistoryFoodChoice"  onclick="displayConentDocDiv(this.value);"></s:radio></div>
								</div>
									
								<div class="col-md-6 col-sm-6">
									<div class="col-md-4"><label for="User Type" style="font-size: 15px;">Comments</label></div>
 	                          		<div class="col-md-8"><input type="text"  readonly="readonly"  name="personalHistoryFoodChoiceDetails"  class="form-control" placeholder="comments" value="<s:property value="personalHistoryFoodChoiceDetails"/>"></div>
	                           </div>
		                 		</div>
		                 	</div>
		                 </div>
		                 		
	             
	             <div class="item form-group" style="padding-top: 20px;">
		              <div class="col-md-3"><label for="User Type" style="font-size: 15px; padding-left: 7px; padding-top: 15px;">Current Medication</label></div>
		            	 </div> 
	             			<div class="item form-group">
		                      <div class="control-label col-md-10  col-sm-12 col-xs-12" style="padding-left:50px; margin-top:-1%;">
						      	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					                <thead>
										<tr>
					                        <th style="width: 30%;text-align: center;">Drug Name</th>
					                        <th style="width: 20%;text-align: center;">Duration (Days)</th>
					                        <th align="left">Comments</th>
					                       
					                    </tr>
					                 </thead>
					                 <tbody> 
					                 
      								<tr id="MedicationTRID"></tr>
					                           	
					                   <s:iterator value="medicationList">
					                       <tr id="visitMedicationTRID<s:property value="medicationID"/>">
					                           <td style="font-size: 14px;text-align: center;"><s:property value="drugName"/></td>
					                           <td style="font-size: 14px;text-align: center;"><s:property value="duration"/></td>
					                           <td style="font-size: 14px;text-align: center;"><s:property value="comments"/></td>
					                       	   
					                       </tr>
					                   </s:iterator>
					                </tbody>
					             </table>
					         </div>
						 </div> 
	             
	                      <div class="item form-group col-md-10" style="padding-top: 20px;" >
	                      
		                     <div class="col-md-2" "><label for="User Type" style="font-size: 15px; margin-left:6px; padding-top:20%;">Vital Signs :</label></div>
		                 <div class="col-md-10  col-sm-12 col-xs-8">
			                     <div class="col-xs-12 col-md-3 col-sm-3" style="margin-left:-7%;" >
			                        <label for="User Type" class="control-label" style="font-size: 15px; margin-left:15px;" >Weight</label>
			                        <input type="number"  readonly="readonly" style="width: 70%; margin-left:15px;" class="form-control" name="weight" id="weightID" value="<s:property value="weight"/>"  placeholder="Weight (in kgs)">
			                     </div>
		                        
		                        <div class="col-xs-12 col-md-3 col-sm-3" style="margin-left:0%;">
			                       	<label for="User Type" class="control-label" style="font-size: 15px;" >Systolic-BP</label>
			                        <input class="form-control"  readonly="readonly" bstyle="width: 70%;" id="systolicBPID" onkeyup="myFunction1('sysSpanID',this.value)" type="number" name="OEBPSys" value="<s:property value="OEBPSys"/>"  placeholder="Systolic _">
						        	<font style="color: red;" id = "sysSpanID"></font>
						        </div>
		                        
		                        <div class="col-xs-12 col-md-3 col-sm-3" style="margin-left:0%;">
		                          	<label for="User Type" class="control-label" style="font-size: 15px;">Diastolic-BP</label>
			                        <input class="form-control" readonly="readonly" style="width: 71%;" id="diastolicBPID" onkeyup="myFunction1('diaSpanID',this.value)" type="number" name="OEBPDia" value="<s:property value="OEBPDia"/>"  placeholder="Diastolic_">
		                      		<font style="color: red;" id = "diaSpanID"></font>
								</div>
		                       
		                       <div class="col-xs-12 col-md-3 col-sm-3" style="margin-left:0%;">
		                       	  	<label for="User Type" class="control-label" style="font-size: 15px;">Pulse</label>
		                          	<input type="number" readonly="readonly" style="width: 70%;" class="form-control" name="OEPulse" id="pulseID"  onkeyup="myFunction1('pulseSpanID',this.value)" value="<s:property value="OEPulse"/>"  placeholder="Pulse">
		                       		<font style="color: red;" id = "pulseSpanID"></font>
		                       </div>
		                   </div>
	                   </div>
	                         
	                      <!-- <div class="ln_solid"></div> --> 
	                      
	                      <div class="form-group" >
	                        <div class="col-md-12 col-sm-12 col-xs-12"  style="padding-top: 50px;" align="center">
	                          <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
	                         
	                        </div>
	                      </div>
				        
				      </form>
				      
			      </div>
			    <!--  END --> 
			     <!-- END -->
			     
			   <!-- Prescription div -->
			      <div class="tab-pane fade" id="presc" >
			        <form class="form-horizontal form-label-left" novalidate id="genPhyPrescID" name="genPhyPrescID" action="EditPrescription" method="POST" style="margin-top:20px;">
				
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
						  		<input type="text" readonly="readonly"  name="advice" id="adviceID" class="form-control" placeholder="Advice" value="<s:property value="advice"/>">
						    </div>
						</div>

				      <h4 style="margin-top:20px;margin-left:15px;"><b>Investigation</b></h4>
				
				      <div class="row" style="margin-left:0px;margin-right:0px;margin-top:15px;">
				      
				            <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 29%; margin-left: 1%;" >
					            <thead>
									<tr>
					                   <th style="width: 80%">Test Name</th>
					                 
					                </tr>
					            </thead>
					            <tbody>
					              <s:iterator value="InvestigationTestsList">
					                  <tr id="investigationTRID<s:property value="investigationID"/>">
					                      <td style="font-size: 14px;text-align: center;"><s:property value="investigation"/></td>
					                  </tr>
					              </s:iterator>
					                            	
					            	<tr id="investTRID">
					                </tr>
					                            	
					             </tbody>
					         </table>
				          </div>
				
				          
				     </div>
				     
				      <div class="row" style="margin-top:10px;margin-left:0px;margin-bottom:5px;">
					
				      <h4 style="margin-top:20px;margin-left:15px;"><b>Prescription</b></h4>
				
				      <div class="row" id="OPDPResc" style="margin-left:0px;margin-right:0px;margin-top:15px;">
				      
				            <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%" style="width: 97%; margin-left: 1%;">
					                            <thead>
													<tr>
					                                    <th style="width: 25%">Drug Name</th>
					                                    <th style="width: 17%">Frequency</th>
					                                    <th style="width: 10%">No. of days</th>
					                                    <th style="width: 9%">Quantity</th>
					                                    <th style="width: 31%">Comments</th>
					                                 
					                                </tr>
					                             </thead>
					                               
					                            <tbody>
					                            	<s:iterator value="prescriptionList">
					                            		<tr id="visitPrescTRID<s:property value="prescriptionID"/>">
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="tradeName"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="frequency"/></td>
					                            			
					                            			<%-- <td style="font-size: 14px;text-align: center;">
						                            			<div class="col-md-1" align="center">
														            <input type="hidden" id="FrequencyEditValueID<s:property value="prescriptionID"/>" >	
																	<a href="javascript:EditFrequencyValue(<s:property value="prescriptionID"/>, FrequencyEditValueID<s:property value="prescriptionID"/>);" style="font-size: 13px;"><b><u>EDIT</u></b></a>
													             </div>
					                            			</td> --%>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="noOfDays"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="productQuantity"/></td>
					                            			
					                              			<td style="font-size: 14px;text-align: center;"><s:property value="comment"/></td>
					                            			
					                            		</tr>
					                            	</s:iterator>
					                            	
					                            	<tr id="prescTRID">
					                            	</tr>
					                            	
					                            </tbody>
					                        </table>
				            </div>
				
				      </div>
				    
				    <div class="row" style="margin-top:100px;"> 
					      <div class="col-md-4 col-sm-3"></div>
					      <div class="col-md-2 col-xs-6 col-sm-3">
                          	  <label class="control-label" for="Date" style="padding-left:50px;">Follow-up after</label>
                          </div>
                          <div class="col-md-1 col-xs-3 col-sm-2" >
                            <input type="number" readonly="readonly"  class="form-control" name="nextVisitDays" value= "<s:property value="nextVisitDays"/>" id="nextVisitDaysID" >
                          </div>
                          <div class="col-md-1">
                          	  <label class="control-label" style="text-align: left;" for="Date">days.</label>
                          </div>
                          <div class="col-md-3"></div>
                      </div>
				      <div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12 col-xs-12" align="center">
		                        <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
		                       
	                          </div>
		                    </div>
				      
				      </form>
			      
			    </div>
			    <!-- End -->
			   
			     
			    
			    <!-- Billing div -->
			     <div class="tab-pane fade" id="billing" >
			      <form class="form-horizontal form-label-left" novalidate id="genPhyBillID" name="genPhyBillID" action="EditBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						          <%-- <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5> --%>
						  </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
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
								<font style="font-size: 14px;  margin-left:12px">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" value="<s:property value="receiptNo"/>">
							</div>
						</div>

						<div class="row" style="margin-top: 15px;">
							
							<div class="col-md-2 col-xs-4">
								<font style="font-size: 14px; margin-left:20px;">Diagnosis</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="cancerType"/></font>
								<input type="hidden" name="visitDate" value="<s:property value="cancerType"/>">
							</div>
							<div class="col-md-2 col-xs-12 col-sm-2">
								<font style="font-size: 14px; margin-left:20px;">Visit Type:</font>
							</div>
							<div class="col-md-4 col-xs-11 col-sm-4">
								<input type="text" class="form-control" readonly="readonly" value="<%=visitType%>">
							</div>
						</div>
						
	                      <div class="item form-group" style="margin-top:15px;">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Charges(Rs)</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text"  readonly="readonly" class="form-control"  name="charges" id="charges" value="<s:property value="netAmount"/>" onkeyup="changeTotalBill('totalBill',charges.value);" placeholder="Charges(Rs)">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Total Bill(Rs)</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="text"  readonly="readonly" class="form-control"  name="totalBill" id="totalBill" value="<s:property value="netAmount"/>" readonly="readonly" placeholder="Total Bill(Rs)">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Advance Payment</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                          <input type="number"  readonly="readonly" class="form-control" name="advPayment" id="advPaymentID" value = "<s:property value="advPayment"/>" onkeyup="changeBalancePayment(advPaymentID.value, totalBill.value);">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label for="User Type" class="control-label col-md-3 col-xs-5">Balance Payment</label>
	                        <div class="col-md-6 col-sm-6 col-xs-6">
	                         <input type="number"  readonly="readonly" class="form-control" name="balPayment" value = "<s:property value="balPayment"/>" id="balPaymentID">
	                        </div>
	                      </div>
	                      
	                      
	                      <div class="row" style="padding: 15px 15px 0 15px;">
							<div class="col-md-2 col-sm-3 col-xs-5" style="padding-top: 5px;">
								<font style="font-size: 16px;">Payment Type*</font>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="hideChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										 disabled="disabled" id="paymentTypeCashID" value="Cash" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cash</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDiv(this);" style="height: 25px; box-shadow: none;"
										 disabled="disabled" id="paymentTypeChequeID" value="Cheque" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Cheque</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayCardDiv(this);" style="height: 25px; box-shadow: none;"
										 disabled="disabled" id="paymentTypeCardID" value="Credit/Debit Card" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit/Debit Card</font>
								</div>
							</div>
							<div class="col-sm-3"></div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivCreditNote(this);"
										 disabled="disabled" style="height: 25px; box-shadow: none;" id="paymentTypeCreditNoteID" value="Credit Note" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Credit Note</font>
								</div>
							</div>
							<div class="col-md-2 col-sm-3 col-xs-12">
								<div class="col-md-4 col-sm-4 col-xs-4">
									<input type="checkbox" name="paymentType" onclick="displayChequeDetailsDivOther(this);"
										 disabled="disabled" style="height: 25px; box-shadow: none;" id="paymentTypeOtherID" value="Other" class="checkboxClass form-control">
								</div>
								<div class="col-md-8 col-sm-8 col-xs-6" style="padding-top: 5px;">
									<font style="font-size: 15px;">Other</font>
								</div>
							</div>
						</div>
						
						<div id="cashDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row">
								<div class="col-md-2 col-xs-6" style="padding-top:3%;">
									<font style="font-size: 16px;">Cash Paid</font>
								</div>
								<div class="col-md-2 col-xs-4" style="padding-top:3%;">
									<input type="text"  readonly="readonly" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, totalBill.value);"
											value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
								</div>
								<div class="col-md-2 col-xs-6" style="padding-top:3%;">
									<font style="font-size: 16px;">Cash To Return</font>
								</div>
								<div class="col-md-2 col-xs-4" style="padding-top:3%;">
									<input class="form-control"  readonly="readonly" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>" placeholder="Cash To Return" type="number">
								</div>
							</div>
						</div>
						<div id="chequeDetailID" style="padding: 15px; display: none;">
						
							<div class="ln_solid"></div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cheque Issued By</font>
								</div>
								<div class="col-md-3">
									<input type="text"  readonly="readonly" class="form-control" id="chequeIssuedByID" name="chequeIssuedBy" value="<s:property value="chequeIssuedBy"/>" placeholder="Cheque Issued By">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Cheque No.</font>
								</div>
								<div class="col-md-3">
									<input class="form-control"  readonly="readonly" name="chequeNo" id="chequeNoID" value="<s:property value="chequeNo"/>" placeholder="Cheque No." type="text">
								</div>
							</div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Bank Name</font>
								</div>
								<div class="col-md-3">
									<input type="text"  readonly="readonly" class="form-control" id="chequeBankNameID" value="<s:property value="chequeBankName"/>" name="chequeBankName" placeholder="Bank Name">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Branch</font>
								</div>
								<div class="col-md-3">
									<input class="form-control"  readonly="readonly" name="chequeBankBranch" id="chequeBankBranchID" value="<s:property value="chequeBankBranch"/>" placeholder="Branch" type="text">
								</div>
							</div>
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Date</font>
								</div>
								<div class="col-md-3">
									<input type="text"  readonly="readonly" class="form-control" name="chequeDate" value="<s:property value="chequeDate"/>" id="chequeDateID" placeholder="Date">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Amount</font>
								</div>
								<div class="col-md-3">
									<input class="form-control"  readonly="readonly" name="chequeAmt" id="chequeAmtID" value="<s:property value="chequeAmt"/>" placeholder="Amount" type="number">
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
									<input type="number" readonly="readonly"  class="form-control" id="cardAmountID" value="<s:property value="cardAmount"/>" name="cardAmount" placeholder="Amount">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Card No.</font>
								</div>
								<div class="col-md-3">
									<input type="text" readonly="readonly"  class="form-control" id="cardMobileNoID" value="<s:property value="cardMobileNo"/>" name="cardMobileNo" placeholder="Card No.">
								</div>
							</div>
							<div class="row">
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Mobile No.</font>
								</div>
								<div class="col-md-3">
									<input type="number"  readonly="readonly" class="form-control"  onKeyPress="if(this.value.length==10) return false;" aria-describedby="inputGroupSuccess1Status" id="cMobileNoID" value="<s:property value="cMobileNo"/>" name="cMobileNo" placeholder="Mobile No.">
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
									<input type="text"  readonly="readonly" class="form-control" id="creditNoteBalID" name="creditNoteBal" value="<s:property value="creditNoteBal"/>" placeholder="Credit Note Balance">
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
									<input type="text" readonly="readonly" class="form-control" id="otherTypeID" name="otherType" value="<s:property value="otherType"/>" placeholder="Enter Other payment here">
								</div>
								<div class="col-md-2 col-xs-6">
									<font style="font-size: 16px;">Amount</font>
								</div>
								<div class="col-md-3">
									<input type="number"  readonly="readonly" class="form-control" id="otherTypeAmountID" name="otherAmount" value="<s:property value="otherAmount"/>" placeholder="Enter Other amount here">
								</div>
							</div>
						</div>
						
			     </s:iterator>
			     	<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12 col-xs-12" align="center">
		                        <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
		                         
		                      </div>
		                    </div>
			      </form>
			      
			      <div class="ln_solid"></div>
			
			    </div>
			    <!-- ends -->
			    
			    <!-- Lab report div -->
			    <div class="tab-pane fade" id="labRep" >
			    
			      <form class="form-horizontal form-label-left" novalidate action="EditLabReport" name="labRepForm" id="labRepForm" method="POST" style="margin-top:20px;" enctype="multipart/form-data">
			      
			      	<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
			        <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
			        <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			        <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">

			       <div class="row" style="margin-top:10px;margin-left:0px;padding-left:15px;">
			          <h5 style="margin-top:0px;font-size:16px;">Lab Report File(s)</h5>
			      </div>
			      
			      <!-- <div class="row" style="padding-left:25px;">
			      	<div class="col-md-3" style="margin-top:10px;margin-bottom:10px;">
			      		<input type="file" name="labReport" id="Uploadfile" onchange="validate_fileupload('Uploadfile');">
			      	</div>
			      	<div class="col-md-6" style="margin-top:10px;">
			      		<button type="button" class="btn btn-default" onclick="myFunction();">Add More Files</button>
			      	</div>
			      	
			      	<div class="col-md-12" id="myID"></div>
			      </div> -->
			      
			       <div class="row" style="padding: 15px;">
					<table id="labRepTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th style="width: 30%;">File Name</th>
								
							</tr>
						</thead>
						<tbody>
							<s:iterator value="labReportList" var="form">
								<tr id="labRepTRID">
									<td><s:property value="reportDBName" /></td>
									
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				
			      <div class="ln_solid"></div>
		                      
		          <div class="form-group">
		             <div class="col-md-12" align="center">
		                 <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
		               
		                
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
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName" value="<%=fullName %>">
					        <input type="hidden" id="patientName" value="<s:property value="firstName"/>  <s:property value="lastName"/>">
					        
					        <textarea style="display: none" name="medicalCerti" id="medicalCerti" cols="" rows="5"></textarea>
					        <textarea style="display: none" name="medicalCertiForPDF" id="medicalCertiForPDF" cols="" rows="5"></textarea>
					
					    	<div class="row" style="margin:15px;">
					    
					    	<%-- <%
						    	if(medicalCertiCheck == "no"){
							%>
					    	<div class="row" style="margin:15px;">
						 			<s:radio list="#{'1':'Pre-defined','0':'Free-text'}" name="radioVal" id="radioValID" onclick="displaycertificate(this.value);"></s:radio>
					    	</div>
					    	<%
					    		} 
					    	%> --%>
		     				<%
						    	if(medicalCertiText == null || medicalCertiText == ""){
						    		System.out.println("inside if loop medical cert");
						    %>
								<div class="col-md-12" id="medicalTextDivID" style="margin:25px;display: none;" >
										<s:textarea cols="2" rows="10"  readonly="readonly" id="medicalCertiText" name="medicalCertiText" style="width:100%;"></s:textarea>
								</div>
 
								<div class="col-md-12" id= "medicalCertiTextDivID" style="margin:25px;display: none;">
							    
							    <label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
							      after careful personal examination of the case hereby certify that, 
							      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName" required>, 
							      is suffering from <input type="text" class="form-control"  name="disease" id="disease" style="width:20%; display: inline-block;margin-top:10px;" required> and I consider that 
							      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> days 
							      with effect from <input type="text" class="form-control"  name="wef" id="wef" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> is 
							      absolutely necessary for the restoration of his/her health.</label>
							
							      <div class="row" style="margin-top:25px;">
							        <div class="col-md-12">
							          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
							          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place" required></div>
							        </div>
							      </div>
							
							      <div class="row" style="margin-top:25px;margin-bottom: 20%;">
							        <div class="col-md-12">
							          <div class="col-md-1" style="padding-top: 5px;"> <label>Date:</label></div>
							          <div class="col-md-3" ><input type="text" class="form-control" value="<%=currentDate %>"  name="date" id="date11"></div>
							          <div class="col-md-4" style="padding-top: 20px;" align="center"> <label>(SEAL)</label></div>
							          <div class="col-md-4" style="padding-top: 20px;"><label>Registered Medical Practitioner</label></div>
							        </div>
							      </div>
							      
							      </div>
							      
							      <% 	
			     				} else {
			     					System.out.println("medical :"+medicalCertiText);
							    	if(medicalCertiText.contains("$")){
							    		String[] strval = medicalCertiText.split("\\$");
							    		
							    		System.out.println("string lenght:"+strval.length);
							    		System.out.println("strval 0 :"+strval[0]);
							    		System.out.println("strval 1 :"+strval[1]);
							    		System.out.println("strval 2 :"+strval[2]);
							    		System.out.println("strval 3 :"+strval[3]);
							    		System.out.println("strval 4 :"+strval[4]);
							    		System.out.println("strval 5 :"+strval[5]);
							    		
							    %>
								<div class="col-md-12" id= "medicalCertiTextDivID" style="margin:25px;">
							    
							    <label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
							      after careful personal examination of the case hereby certify that, 
							      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName" value="<%=strval[0] %>" required>, 
							      is suffering from <input type="text" class="form-control"  name="disease" id="disease" value="<%=strval[1] %>" style="width:20%; display: inline-block;margin-top:10px;" required> and I consider that 
							      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" value="<%=strval[2] %>" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> days 
							      with effect from <input type="text" class="form-control"  name="wef" id="wef" value="<%=strval[3] %>" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;" required> is 
							      absolutely necessary for the restoration of his/her health.</label>
							
							      <div class="row" style="margin-top:25px;">
							        <div class="col-md-12">
							          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
							          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place" value="<%=strval[4] %>" required></div>
							        </div>
							      </div>
							
							      <div class="row" style="margin-top:25px;margin-bottom: 20%;">
							        <div class="col-md-12">
							          <div class="col-md-1" style="padding-top: 5px;"> <label>Date:</label></div>
							          <div class="col-md-3" ><input type="text" class="form-control" value="<%=strval[5] %>" name="date" id="date11"></div>
							          <div class="col-md-4" style="padding-top: 20px;" align="center"> <label>(SEAL)</label></div>
							          <div class="col-md-4" style="padding-top: 20px;"><label>Registered Medical Practitioner</label></div>
							        </div>
							      </div>
							      
							      </div>
							      
							    <%
							    	} else{
							    		
							      %> 
									<div class="col-md-12" id="medicalTextDivID" style="margin:25px;" >
										<textarea cols="2" rows="10" id="medicalCertiText" readonly="readonly"  name="medicalCertiText" style="width:100%;"><%=medicalCertiText %></textarea>
									</div>
 
							      <%
							    		}
			     					}
							      %> 
					    </div>
					    
					    <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12" align="center">
			                   <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
			                   
			               </div>
			            </div>
			
			     </form>
			    </div>
			    <!-- Ends -->
			    
			    <!-- Referral letter div -->
			    <div class="tab-pane fade" id="refLetter" >
			    
			    		<form class="app-cam" id="" name="" action="UpdateReferralLetter" method="POST" style="width:95%;margin:0px;" >
			    		
			    		<input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
				      <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
				      <input type="hidden" name="patientID" value="<s:property value="patientID"/>">
				      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
				      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
				       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					    
					    <input type="hidden" id="doctrName" value="<%=fullName %>">
					    <input type="hidden" id="patientName" value="<s:property value="firstName"/> <s:property value="lastName"/>">
				      
				        <input type="hidden" id="doctrNameRef" value="<%=fullName %>">
				        
					    <input type="hidden" id="patientNameRef" value="<s:property value="firstName"/> <s:property value="lastName"/>">
					    <textarea style="display: none" name="referralLetter" id="referralLetter" cols="" rows="5"></textarea>
					    <textarea style="display: none" name="referralLetterForPDF" id="referralLetterForPDF" cols="" rows="5"></textarea>
			
						<%-- <%
							if(referralLetterCheck == "no"){
						%>
						<div class="row" style="margin:15px;">
				 			<s:radio list="#{'1':'Pre-defined','0':'Free-text'}" name="radioRefVal" id="radioRefValID" onclick="displayRefLetter(this.value);"></s:radio>
			    		</div>
			    		<%
							}
			    		%> --%>
						<%
							if(referralLetterText == null || referralLetterText == ""){
						%>
						
						<div class="col-md-12" id="refLetterTextDivID" style="margin:25px;display: none;" >
							<s:textarea cols="2" rows="10" id="refLetterText"  readonly="readonly" name="refLetterText" style="width:100%;"></s:textarea>
						</div>
  
						<div class="col-md-12" id= "refLetterDivID" style="margin:25px;display: none;">
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						
						          <div class="col-md-1" style="margin-left: -15px;"> <label>Date:</label> </div>
						          <div class="col-md-3" style="margin-left: -15px;"> <label><%=currentDate %><input type="hidden" class="form-control" value="<%=currentDate %>"  name="date" id="date12"></label></div>
						       
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;margin-top: 10px;">Dear Dr. </label><s:select list="doctorList" name="doctName" id="doctName" style="width:20%; " class="form-control"></s:select>, 
						
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						        
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
			     					System.out.println("refrral :"+referralLetterText);
							    	if(referralLetterText.contains("$")){
							    		String[] strval = referralLetterText.split("\\$");
							    		
							    		System.out.println("string lenght:"+strval.length);
							    		System.out.println("strval 0 :"+strval[0]);
							    		System.out.println("strval 1 :"+strval[1]);
							    		System.out.println("strval 2 :"+strval[2]);
							    		System.out.println("strval 3 :"+strval[3]);
							    		System.out.println("strval 4 :"+strval[4]);
							    			
							%>
								    
							<div class="col-md-12" id= "refLetterDivID" style="margin:25px;">
								    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						
						          <div class="col-md-1" style="margin-left: -15px;"> <label>Date:</label> </div>
						          <div class="col-md-3" style="margin-left: -15px;"> <label><input type="text" class="form-control" value="<%=strval[0] %>" name="date" id="date12"></label></div>
						        
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;margin-top: 10px;">Dear Dr. </label><s:select list="doctorList" name="doctName" id="doctName" style="width:20%; " class="form-control"></s:select>, 
						
						      </div>
						
						    </div>
						
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						        
						          <label for="Name" style="font-size:14px;">I am referring <s:property value="firstName"/> <s:property value="lastName"/> to you for the following conditions:</label><br><br>
						
						          <textarea rows="4" name="conditionText" id="conditionText" class="form-control" required><%=strval[3] %></textarea><br><br>
						
						          <label for="Name" style="font-size:14px;">Please advise Mr/Ms <s:property value="firstName"/> <s:property value="lastName"/>  on further treatment. </label> <br><br>
						
						          <label for="Name" style="font-size:14px;">Sincerely, </label>
						          <br>
						          <label for="Name" style="font-size:14px;">Dr. <%=fullName %> </label>
						
						      </div>
						
						    </div>
						      </div>
						  <%
							}else{
			             %>
			             
						<div class="col-md-12" id="refLetterTextDivID" style="margin:25px;" >
							<textarea cols="2" rows="10" id="refLetterText"  readonly="readonly" name="refLetterText" style="width:100%;"><%=referralLetterText %></textarea>
						</div>
  
					     <%
							}
				     	}
						 %> 
			             
			            <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12" align="center">
			                   <button type="button" onclick="windowOpen2();" class="btn btn-primary">Back</button>
			                   
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
                		//alert('hiiii'+$("#tradeName").val()+"...");
                		CategoryValue($("#tradeName").val());
                	},500);
                }
            });
    });
	 
    </script>
    
    <script>
    
	$(window).load( function() {
		
		$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
		
		$('.radioClass').prop('disabled', true);

	});
	</script>
	
  </body>
</html>