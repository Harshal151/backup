<%@page import="com.edhanvantari.form.PatientForm"%>
<%@page import="com.edhanvantari.daoImpl.ClinicDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.ClinicDAOInf"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.edhanvantari.daoImpl.RegistrationDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.RegistrationDAOinf"%>
<%@page import="com.edhanvantari.daoImpl.PatientDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.PatientDAOInf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.edhanvantari.daoImpl.LoginDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.LoginDAOInf"%>
<%@page import="com.edhanvantari.util.ConfigurationUtil"%>
<%@page import="com.edhanvantari.util.ActivityStatus"%>
<%@page import="com.edhanvantari.util.ConfigXMLUtil"%>
<%@page import="com.edhanvantari.form.LoginForm"%>
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
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>View Lab Visit | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
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
		
		String fullName = form.getFullName();

	
	%>
    <!-- Ends -->
    
    
    <%
    	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
    
    	List<PatientForm>labTestList = (List<PatientForm>) request.getAttribute("labTestList");
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
		function downloadLabReport(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

			document.forms["labRepForm"].action = "AddDownloadLabReport";
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
	
		String medicalCertificateFinalText = "";
		
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
		}
		
	%>
	
	
	<%
		String referralLetterText = (String) request.getAttribute("referralLetter");
	
		String referralLetterFinalText = "";
		
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
		}
		
	%>
	
	<!-- Print visit, prescription and billing -->
	
	<script type="text/javascript">
		function printVisit(){
			document.forms["visitrForm"].action = "PrintGenPhyVisit";
			document.forms["visitrForm"].submit();
		}
	
		function enablePrintButton(){
			document.getElementById("printButton").disabled = false;
		}
	
		function printPrescription(){
			document.forms["genPhyPrescID"].action = "PrintGenPhyPrescription";
			document.forms["genPhyPrescID"].submit();
		}
	
		function printBilling(){
			document.forms["genPhyBillID"].action = "PrintGenPhyBilling";
			document.forms["genPhyBillID"].submit();
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
		  $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
		  
		  $('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

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
	    
	
	    var medicalCertificate = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in '"+companyName+ "@companyName', is suffering from '" + disease + "@disease' and I consider that the period of absence from duty of '"+ dutyDays + "@dutyDays' days with effect from '"+ wef + "@wef' is absolutely necessary for the restoration of his/her health.\n\n\n Place: '"+place+ "@place' \n\n\n Date: '" +visitDte + "@date11' \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    var medicalCertificateForPDF = "I, Dr. "+doctorName+ " registered medical practitioner, after careful personal examination of the case hereby certify that, Sh./Smt. "+ patientName + " working in "+companyName1+ ", is suffering from " + disease1 + " and I consider that the period of absence from duty of "+ dutyDays + " days with effect from "+ wef1 + " is absolutely necessary for the restoration of his/her health.\n\n\n Place: "+place1+ " \n\n\n Date: " +visitDte + " \t\t\t (SEAL) \t\t\t Registered Medical Practitionar";
	
	    document.getElementById("medicalCerti").value = medicalCertificate;
	
	    document.getElementById("medicalCertiForPDF").value = medicalCertificateForPDF;
	
	
	  }
	</script>
	
	<!-- Ends -->
	
	<!-- Displaying referral letter text -->	
	
	<script type="text/javascript">
	  function displayRefLet(){
		  $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
		  
		  $('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

	    var conditionText = document.getElementById("conditionText").value;
	    var doctName = document.getElementById("doctName").value;
	    var doctorName = document.getElementById("doctrNameRef").value;
	    var patientName = document.getElementById("patientNameRef").value;
	    var visitDte = document.getElementById("date12").value;
	    var conditionText1 = document.getElementById("conditionText").value;
	    var doctName1 = document.getElementById("doctName").value;
	
	    if(doctName.indexOf(" ") != "-1"){
	        var regExp = new RegExp(' ','g');
	    	doctName = doctName.replace(regExp, "_");
	    }else{
	    	doctName = doctName;
	    }
	
	    if(conditionText.indexOf(" ") != "-1"){
	    	var regExp = new RegExp(' ','g');
	    	conditionText = conditionText.replace(regExp, "_");
	    }else{
	    	conditionText = conditionText;
	    }
	
	    var referralLetter = "Date: "+visitDte+ " \n\n\n Dear Dr. '"+ doctName + "@doctName', \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n '" + conditionText + "@conditionText' \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
	    var referralLetterForPDF = "Date: "+visitDte+ "\n\n\n Dear Dr. "+ doctName1 + ", \n\n\n I am referring Mr/Ms "+patientName+ " to you for the following conditions:\n\n " + conditionText1 + " \n\n\n Please advise Mr/Ms "+patientName+ " on further treatment. \n\n\n Sincerely, \n\n Dr. "+ doctorName + "";
	
	    document.getElementById("referralLetter").value = referralLetter;
	
	    document.getElementById("referralLetterForPDF").value = referralLetterForPDF;
	
	
	  }
	</script>
	
	<!-- Ends -->
	

	
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
    
    	function addPrescriptionRow(drugName , dose, frequency, noOfDays, visitID, comment, noOfPills){
    		
    		var autoSelecter = dojo.widget.byId("tradeName");
			var tradeName = autoSelecter.getSelectedValue();
			
			if(tradeName == ""){
				alert("Please select trade name");
			}else if(dose == ""){
				alert("Please enter dose");
			}else if(noOfDays == ""){
				alert("Please enter no of days");
			}else{
				addPrescriptionRow1(tradeName , dose, frequency, noOfDays, visitID, comment, noOfPills)
			}
    		
    	}
		
    	var prescCounter = 1;
    
		function addPrescriptionRow1(tradeName , dose, frequency, noOfDays, visitID, comment, noOfPills){
			
			var autoSelecter = dojo.widget.byId("tradeName");
			
			var TRID = "newPrescTRID"+prescCounter;
			
			var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
					   +"<td style='text-align:center'>"+tradeName+"<input type='hidden' name='newDrugID' value='"+tradeName+"'></td>"
					   +"<td style='text-align:center'>"+dose+"<input type='hidden' name='newDrugDosage' value='"+dose+"'></td>"
					   +"<td style='text-align:center'>"+frequency+"<input type='hidden' name='newDrugFrequency' value='"+frequency+"'></td>"
					   +"<td style='text-align:center'>"+noOfPills+"<input type='hidden' name='newDrugQuantity' value='"+noOfPills+"'></td>"
					   +"<td style='text-align:center'>"+noOfDays+"<input type='hidden' name='newDrugNoOfDays' value='"+noOfDays+"'></td>"
					   +"<td style='text-align:center'>"+comment+"<input type='hidden' name='newDrugComment' value='"+comment+"'></td>"
					   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""+TRID+"\");'/></td>"
					   +"</tr>";
			
			$(trTag).insertAfter($("#prescTRID"));
			
			prescCounter++;
			
			autoSelecter.innerHTML = "";
			$("#dose").val("");
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
    	//,Report,Medical Certificate,Referral Letter
	    ValueMap.put("clinician","Lab Visit,Billing");
	    ValueMap.put("billDesk","Prescription,Billing");
	    ValueMap.put("compounder","Prescription,Billing");
    
    	String tabs = ValueMap.get(form.getUserType());
    
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
    <%
    
  		//Adding div ID values which is to be associated to tabs
		HashMap<String, String> idMap = new HashMap<String, String>();
	
	    idMap.put("Lab Visit", "visit");
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
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
	
	%>
	
	<script type="text/javascript">
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
	</script>
	
	<script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		//setInterval(function(){$('#weekCalendar').fullCalendar('refetchEvents')}, 30000);
    		setInterval(function(){$('body').css('padding-right','0px')}, 1000);
    		
    		/* $('#collectionTimeID').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    }); */
    		
    		setTimeout(function(){
    			//alert('hii');
    			$('#collectionTimeID').attr("value",$("#apptStartTimeID1Hidden").val());
    		},1000);
    		
    		
        });
    </script>
    
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

        	/* $('#cardDetailID').hide(1000);
        	$("#cashDetailID").hide(1000);
        	$("#creditNoteDetailID").hide(1000);
        	$("#otherDetailID").hide(1000); */
        	
        	//$("#creditNoteBalID").val(0);

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

    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#creditNoteDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
    		
    		//$("#creditNoteBalID").val(0);

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
    		
    		/* $('#chequeDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#creditNoteDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
    		
    		//$("#creditNoteBalID").val(0);
    		
    	}
    	
    	function displayChequeDetailsDivCreditNote(input){

    		if($(input).is(":checked")){
    			//$("#creditNoteDetailID").show(1000);
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    			
    		}else{
    			//$("#creditNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalID").val("");
    			
    			showAmountReturnedDiv("paymentTypeCashID", "paymentTypeChequeID", "paymentTypeCardID", "paymentTypeOtherID");
    			
    		}
    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
        	
        	/* var netAmount = $("#netAmountID").val();
        	
        	$("#advPaymentID").val(0);
        	$("#balPaymentID").val(netAmount);
        	$("#creditNoteBalID").val(netAmount); */
        	
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
    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#creditNoteDetailID").hide(1000); */
        	
        	//$("#creditNoteBalID").val(0);

    	}
    	
    	//For dues pending modal
		function displayDuesChequeDetailsDiv(input){
    		
    		if($(input).is(":checked")){
    			$('#chequeDuesDetailID').show(1000);
    		}else{
    			$('#chequeDuesDetailID').hide(1000);
    			
    			$("#chequeIssuedByDuesID").val("");
    			$("#chequeDuesNoID").val("");
    			$("#chequeBankNameDuesID").val("");
    			$("#chequeBankBranchDuesID").val("");
    			$("#single_cal3111").val("");
    			$("#chequeAmtDuesID").val("");
    		}

        	/* $('#cardDetailID').hide(1000);
        	$("#cashDetailID").hide(1000);
        	$("#creditNoteDetailID").hide(1000);
        	$("#otherDetailID").hide(1000); */
        	
        	//$("#creditNoteBalID").val(0);

    	}

    	function hideDuesChequeDetailsDiv(input){
    		
    		if($(input).is(":checked")){
    			$("#cashDuesDetailID").show(1000);
    		}else{
    			$("#cashDuesDetailID").hide(1000);
    			
    			$("#cashPaidDuesID").val("");
    			$("#cashToReturnDuesID").val("");
    		}

    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#creditNoteDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
    		
    		//$("#creditNoteBalID").val(0);

    	}
    	
    	function displayDuesCardDiv(input){
    		
    		if($(input).is(":checked")){
    			$('#cardDuesDetailID').show(1000);
    		}else{
    			$('#cardDuesDetailID').hide(1000);
    			
    			$("#cardMobileNoDuesID").val("");
    			$("#cardAmountDuesID").val("");
    			$("#cMobileNoDuesID").val("");
    		}
    		
    		/* $('#chequeDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#creditNoteDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
    		
    		//$("#creditNoteBalID").val(0);
    		
    	}
    	
    	function displayDuesChequeDetailsDivCreditNote(input){

    		if($(input).is(":checked")){
    			$("#creditDuesNoteDetailID").show(1000);
    		}else{
    			$("#creditDuesNoteDetailID").hide(1000);
    			
    			$("#creditNoteBalDuesID").val("");
    		}
    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#otherDetailID").hide(1000); */
        	
        	/* var netAmount = $("#netAmountID").val();
        	
        	$("#advPaymentID").val(0);
        	$("#balPaymentID").val(netAmount);
        	$("#creditNoteBalID").val(netAmount); */
        	
        	//paymentCounter++;

    	}
    	
    	function displayDuesChequeDetailsDivOther(input){

    		if($(input).is(":checked")){
    			$("#otherDuesDetailID").show(1000);
    		}else{
    			$("#otherDuesDetailID").hide(1000);
    			
    			$("#otherTypeDuesID").val("");
    			$("#otherTypeDuesAmountID").val("");
    		}
    		/* $('#chequeDetailID').hide(1000);
    		$('#cardDetailID').hide(1000);
    		$("#cashDetailID").hide(1000);
    		$("#creditNoteDetailID").hide(1000); */
        	
        	//$("#creditNoteBalID").val(0);

    	}
    	
    	
    	function showAmountReturnedDiv(cashID, chequeID, cardID, otherID){
    		if($("#"+cashID).is(":checked") || $("#"+chequeID).is(":checked") || 
    				$("#"+cardID).is(":checked") || $("#"+otherID).is(":checked")){
    			$("#amountReturnedDivID").show(500);
    		}else{
    			$("#amountReturnedDivID").hide(500);
    		}
    	}
    	
function changeCashToReturn(cashPaid, advPayment){
			
			if(cashPaid == ""){
				cashPaid = 0;
			}
			
			if(advPayment == ""){
				advPayment = 0;
			}
			
			var cashTOReturn = parseFloat(cashPaid) - parseFloat(advPayment);
			
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
			$("#balPaymentID").val(netAmount);
		}
    	
    </script>
    
    <%
    	
		
		String paymentType = (String) request.getAttribute("paymentType");
		
		HashMap<String,String> paymentMap = new HashMap<String, String>();
		
		paymentMap.put("Cash","paymentTypeCashID=cashDetailID");
		paymentMap.put("Cheque","paymentTypeChequeID=chequeDetailID");
		paymentMap.put("Credit/Debit Card","paymentTypeCardID=cardDetailID");
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
	
		
		function openAddNewModal(){
			$("#addRefByModalID").modal('show');
		}
		
		function addRefDoc(){
			if($("#referringDoctNameID").val() == ""){
				alert("Enter doctor name.");
			}else{
				 var formData = new FormData($("#refByFormID")[0]);
					//alert(formData);
				addRefDoc1(formData);
			}
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function addRefDoc1(formData) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var msg = "";

					for ( var i = 0; i < array.Release.length; i++) {
						msg = array.Release[i].MSG;
					}
					
					if(msg.includes("success")){
						$("#addRefByModalID").modal('hide');
						
						alert(msg);
						
						retrieveReferringDoctorList();
					}else{
						
						$("#addRefByModalID").modal('hide');
						
						alert(msg);
						
					}
				}
			};
			xmlhttp.open("POST", "AddReferringDoctor");
			xmlhttp.send(formData);
		}
		
		function retrieveReferringDoctorList() {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var msg = "";
					
					var selectTag = "<select name='referredBy' id='doctName' class='form-control'>"+
									"<option value=''>Select Referred By</option>";

					for ( var i = 0; i < array.Release.length; i++) {
						msg = array.Release[i].MSG;
						
						selectTag += "<option value='"+array.Release[i].doctName+"'>"+array.Release[i].doctName+"</option>";
					}
					
					selectTag += "</select>";
					
					$("#refDocDivID").html(selectTag);
					
				}
			};
			xmlhttp.open("GET", "RetrieveReferringDoctorList", true);
			xmlhttp.send();
		}
		
		function disableSubmitBtn(btnID){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();

    		$("#"+btnID).attr('disabled',true);
    		return true;
    	}
		
		function calculateValues(test,inputID){
			
			if(test == "Indirect Bilirubin"){
				var totalBilirubin = $("input[name='observationsValue'][data-title='Total Bilirubin']").val();
				
				if(totalBilirubin == ""){
					totalBilirubin = "0";
				}
				
				var directBilirubin = $("input[name='observationsValue'][data-title='Direct Bilirubin']").val();
				
				if(directBilirubin == ""){
					directBilirubin = "0";
				}
				
				var indirectBilirubin = parseFloat(parseFloat(totalBilirubin) - parseFloat(directBilirubin));
				
				if(isNaN(indirectBilirubin)){
					indirectBilirubin = 0;
				}else{
					indirectBilirubin = Math.round(indirectBilirubin * 100) / 100;
				}
				
				$("#"+inputID).val(indirectBilirubin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Serum Globulin"){
				var totalProteins = $("input[name='observationsValue'][data-title='Total Proteins']").val();
				
				if(totalProteins == ""){
					totalProteins = "0";
				}
				
				var serumAlbumin = $("input[name='observationsValue'][data-title='Serum Albumin']").val();
				
				if(serumAlbumin == ""){
					serumAlbumin = "0";
				}
				
				var serumGlobulin = parseFloat(parseFloat(totalProteins) - parseFloat(serumAlbumin));
				
				if(isNaN(serumGlobulin)){
					serumGlobulin = 0;
				}else{
					serumGlobulin = Math.round(serumGlobulin * 100) / 100;
				}
				
				$("#"+inputID).val(serumGlobulin);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Total Cholesterol/HDL Ratio"){
				
				var serumChol = $("input[name='observationsValue'][data-title='Serum Cholesterol']").val();
				
				if(serumChol == ""){
					serumChol = "0";
				}
				
				var hdlChol = $("input[name='observationsValue'][data-title='H.D.L. Cholesterol']").val();
				
				if(hdlChol == ""){
					hdlChol = "0";
				}
				
				var choleRatio = parseFloat(parseFloat(serumChol) / parseFloat(hdlChol));
				
				if(isNaN(choleRatio)){
					choleRatio = 0;
				}else{
					choleRatio = Math.round(choleRatio * 100) / 100;
				}
				
				$("#"+inputID).val(choleRatio);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.V."){
				
				var pcvVal = $("input[name='observationsValue'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var rbcCount = $("input[name='observationsValue'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mcvVal = parseFloat(parseFloat(pcvVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mcvVal)){
					mcvVal = 0;
				}else{
					mcvVal = Math.round(mcvVal * 100) / 100;
				}
				
				$("#"+inputID).val(mcvVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H."){
				
				var hemoVal = $("input[name='observationsValue'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var rbcCount = $("input[name='observationsValue'][data-title='Erythrocyte Count']").val();
				
				if(rbcCount == ""){
					rbcCount = "0";
				}
				
				var mchVal = parseFloat(parseFloat(hemoVal) / parseFloat(rbcCount) * 10);
				
				if(isNaN(mchVal)){
					mchVal = 0;
				}else{
					mchVal = Math.round(mchVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "M.C.H.C."){
				
				var hemoVal = $("input[name='observationsValue'][data-title='Haemoglobin']").val();
				
				if(hemoVal == ""){
					hemoVal = "0";
				}
				
				var pcvVal = $("input[name='observationsValue'][data-title='P.C.V.']").val();
				
				if(pcvVal == ""){
					pcvVal = "0";
				}
				
				var mchcVal = parseFloat((parseFloat(hemoVal) * 100) / parseFloat(pcvVal));
				
				if(isNaN(mchcVal)){
					mchcVal = 0;
				}else{
					mchcVal = Math.round(mchcVal * 100) / 100;
				}
				
				$("#"+inputID).val(mchcVal);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "Prothrombin Ratio"){
				
				var controlTimeVal = $("input[name='observationsValue'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsValue'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				console.log("controlTimeVal.."+controlTimeVal+"..patiemtTimeVal.."+patiemtTimeVal);
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				console.log("..PR.."+PR);
				
				if(isNaN(PR)){
					PR = 0;
				}else{
					PR = Math.round(PR * 100) / 100;
				}
				
				console.log("..PR....."+PR);
				
				$("#"+inputID).val(PR);
				$("#"+inputID).attr("readonly","readonly");
				
			}else if(test == "I.N.R. (International Normalised Ratio)"){
				
				var controlTimeVal = $("input[name='observationsValue'][data-title='Control Time (MNPT)']").val();
				
				if(controlTimeVal == ""){
					controlTimeVal = "0";
				}
				
				var patiemtTimeVal = $("input[name='observationsValue'][data-title='Patients Time']").val();
				
				if(patiemtTimeVal == ""){
					patiemtTimeVal = "0";
				}
				
				var isiVal = $("input[name='observationsValue'][data-title='I.S.I. Value of the prothrombin used']").val();
				
				if(isiVal == ""){
					isiVal = "0";
				}
				
				var PR = parseFloat(parseFloat(patiemtTimeVal) / parseFloat(controlTimeVal));
				
				var INRVal = parseFloat(Math.pow(PR,isiVal));
				
				if(isNaN(INRVal)){
					PR = 0;
				}
				
				$("#"+inputID).val(INRVal.toFixed(2));
				$("#"+inputID).attr("readonly","readonly");
				
			}
			
		}
	
	</script>
	
	<%
		boolean resultCheck = (Boolean) request.getAttribute("resultCheck");
	%>
    
  <sx:head/>
  
  </head>
  
  <!-- <body class="nav-md" onload="getValuesByChargeType(description.value);"> -->
  <body class="nav-md" >
  
  <!-- To show loading icon while page is loading -->
   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader" >
  
  <!-- Add new Referred by modal -->
  
  <div id="addRefByModalID" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
      	<div class="modal-header">
      		<center><h5><b>Add New Referring Doctor</b></h5></center>
      	</div>
        <div class="modal-body">
          
          <form class="form-horizontal form-label-left" id="refByFormID" >
	                      
	          <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Doctor Name">Doctor Name <span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctName" id="referringDoctNameID" required="required" placeholder="Doctor Name" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Specialization">Specialization
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctSpecialisation" placeholder="Specialization" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Clinic Name">Clinic Name
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="text" name="referringDoctClinicName" placeholder="Clinic Name" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Clinic Address">Clinic Address
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <textarea name="referringDoctClinicAddres" placeholder="Clinic Address" class="form-control"></textarea>
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Phone">Phone
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="number" name="referringDoctPhone" placeholder="Phone" class="form-control">
	                        </div>
	                      </div>
	                      
	                      <div class="item form-group">
	                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="Email">Email
	                        </label>
	                        <div class="col-md-6 col-sm-6 col-xs-12">
	                          <input type="email" name="referringDoctEmail" placeholder="Email" class="form-control">
	                        </div>
	                      </div>
	          
          </form>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="addRefDoc();">Add</button>
        </center>
        </div>

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
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                  	<%
                  	if(daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()){
	              	%>
	              
	                 <img src="images/user.png" alt="Profile Pic"><%= form.getFullName() %>
	                
	                <%
	              		}else{
	                %>
	                
	                 <img src="<%= daoInf.retrieveProfilePic(form.getUserID()) %>" alt="Profile Pic"><%= form.getFullName() %>
	                
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
                        <h3>VIEW EXISTING VISIT</h3>
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
		              			//System.out.println("hiii");
		              		}
		              	%>
					  </ul>
			    
			        
				 <div id="myTabContent1" class="tab-content">
				 
				 
				 	<!-- Visit div -->
			    <div class="tab-pane fade in active" id="visit">
				    <form class="form-horizontal form-label-left" action="EditLabVisit" name="visitrForm" onsubmit="return disableSubmitBtn('sendBtnID');" id="visitrForm" method="POST" style="margin-top:20px;">
	
				      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
			         <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
			         <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
			         <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			          <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
				      
					      <%-- <div class="row">
								<div class="col-md-6">
									<button type="button" class="btn btn-warning" id="viewLastBtnID" onclick="viewPopUp('ViewLastOPDVisitDetails?patientID=<s:property value="patientID"/>&visitID=<s:property value="lastVisitID"/>');">View Last Visit</button>
								</div>
								<div class="col-md-6"></div>
						  </div> --%>
						  
						  <div class="row" style="margin-top:15px;">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"  >
							    		<table border="1" width="100%">
							    		
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Name: </td>
							    				<td colspan="3"> <input type="text" style="border:none;cursor:not-allowed;background: transparent;"  class="form-control" readonly="readonly" value="<s:property value="firstName"/> <s:property value="middleName"/> <s:property value="lastName"/>"  name="" placeholder="Patient Name" autofocus=""></td>
							    				<td style="padding-left:10px;font-size:14px; font-weight: bold;">Age: </td>
							    				<td><input type="number" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" name="age" value="<s:property value="age"/>" placeholder="Age" autofocus=""> </td>
							    				<%-- <td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Date of first visit: </td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px;"> <input type="text" class="form-control" name="" style="border:none;cursor:not-allowed;background: transparent;" readonly="readonly" value="<s:property value="visitDate"/>" aria-describedby="inputSuccess2Status3"> </td>
							    				<td colspan="1" style="font-size:14px; font-weight: bold;text-align: center;"> Profile Pic </td> --%>
							    			</tr>
							    			
							    			
							    			<%-- <tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Address: </td>
							    				<td colspan="5"> <input type="text" class="form-control" style="border:none;cursor:not-allowed;background: transparent;" value="<s:property value="address"/>" readonly="readonly"  name="address" placeholder="" autofocus=""></td>
							    			</tr> --%>
							    			
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;">Sex: </td>
							    				<td colspan="3">
							    				<input type="text" name="gender" style="border:none;cursor:not-allowed;background: transparent;" class="form-control" readonly="readonly" value="<s:property value="gender"/>" placeholder="Gender" autofocus=""></td>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Phone Number: </td>
							    				<td colspan="3"><input type="number" name="mobile" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control"  value="<s:property value="mobile"/>" autofocus=""></td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="1" style="padding-left:10px;font-size:14px; font-weight: bold;"> Email: </td>
							    				<td colspan="5"> <input type="text" name="emailID" style="border:none;background: transparent;cursor:not-allowed;" readonly="readonly" class="form-control" value="<s:property value="emailID"/>" autofocus=""></td>
							    			</tr>
							    			
							    				    			
							    		</table>
							 </div>
							 </div>
							 
							 <div class="ln_solid"></div>
								
						    <div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Medical registration number: </font>
						    	</div>
						    	
						    	<div class="col-md-3">
						    		<input class="form-control" name="registrationNo" readonly="readonly" value="<s:property value="registrationNo"/>" placeholder="Medical registration number">
						    	</div>
						    	
							</div>
							
	                      <div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
						    		<font style="font-size: 14px;font-weight: bold;">Referred By. </font>
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10" id="refDocDivID">
						    		<s:select list="doctorList" headerKey="" headerValue="Select Referred By" name="referredBy" id="doctName" class="form-control"></s:select>
						    	</div>
						    	<div class="col-md-1 col-lg-1 col-sm-1 col-xs-2">
						    		<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Add New"
						                            					onclick="openAddNewModal();"
																		title="Add New" style="height: 24px;cursor: pointer;" />
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
						    		<font style="font-size: 14px;font-weight: bold;">Sample ID* </font>
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10">
						    		<input type="text" class="form-control" name="sampleID" required="required" value="<s:property value="sampleID"/>" placeholder="Enter Sample ID here">
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
						    
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
						    		<font style="font-size: 14px;font-weight: bold;">Collection Date*</font>
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10">
						    		<input type="text" class="form-control" name="visitDate" required="required" value="<s:property value="firstVisitDate"/>" id="single_cal3" placeholder="Visit Date" aria-describedby="inputSuccess2Status3">
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
						    		<font style="font-size: 14px;font-weight: bold;">Collection Time*</font>
						    	</div>
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10">
						    		<input type="hidden" id="apptStartTimeID1Hidden" value="<s:property value="visitFromTime"/>">
			        					<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii:00" data-link-field="dtp_input3" data-link-format="hh:ii:00">
						                    <input class="form-control" size="16" required="required" id="collectionTimeID" name="visitFromTime" type="text" value="" readonly>
						                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
					                	</div>
						    		
						    	</div>
						    	
							</div>
							
							<div class="ln_solid"></div>
								
						    <div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Prescribed Tests*: </font>
						    	</div>
						    	
							</div>
							
							<div class="row" style="margin-top:10px;">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								
									<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
			                            <thead>
			
			                                <tr >
			                                    <th>Sr. No.</th>
			                                    <th>Test</th>
			                                    <th>Normal Range</th>
			                                    <th>Observed Value</th>
			                                </tr>
			                             </thead>
			                               
			                            <tbody>
			                            	<%-- <s:iterator value="labTestList"> --%>
			                            	
			                            	<% for(PatientForm form1: labTestList ){ %>
			                            		
			                            		<tr style="font-size: 14px;">
		                            			<td style="text-align: center;"><%=form1.getSrNo() %></td>
		                            			
		                            			<td><%=form1.getCBCProfileTest() %></td>
		                            			
		                            			<td><%=form1.getCBCProfileNormalValue() %>
		                            			<input type="hidden" name="investigationDetails" value="<%=form1.getInvestigationDetailsID() %>">
		                            			</td>
		                            			
		                            		<%	
		                            			List<String>LabDefaultValueList = patientDAOInf.getLabDefaultValueList(form1.getCBCProfileTest());
		                            		%>
		                            			<td>
		                            			<% 
		                            				if(LabDefaultValueList.size()<=0){ %>
		                            					
		                            					<input type="text" class="form-control" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>" name="observationsValue" onfocus="calculateValues('<%=form1.getCBCProfileTest() %>', this.id);" value="<%=form1.getCBCProfileValue() %>">
		                            			
		                            			<%	}else{ %>
		                            			
		                            			<%
		                            					if(LabDefaultValueList.contains(form1.getCBCProfileValue()) || form1.getCBCProfileValue().equals("0.0")){
		                            			%>
		                            			
		                            					<input type="text" class="form-control otherClass" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>Other" style="display: none;" name="" value="0.0">
		                            				
		                            				<select name="observationsValue" class="form-control otherSelectClass" id="testValueID<%=form1.getInvestigationDetailsID() %>" style="display: block;">
		                            				
		                            				<option value="0">Select Value</option>
			                            		
			                            		<% 	for(String values: LabDefaultValueList){ %>
			                            				
			                            				<% if(values.equals(form1.getCBCProfileValue())){ %>
			                            					
			                            					<option value="<%= values %>" selected="selected"><%= values %></option>
			                            				
			                            				<% }else{ %>	
			                            				
			                            					<option value="<%= values %>"><%= values %></option>
			                            				
			                            				<% } %>
			                            		<% } %>
		                            			
			                            		 </select>
		                            			
		                            			<%
		                            					}else{
		                            			%>
		                            			
		                            			<input type="text" class="form-control otherClass" data-title="<%=form1.getCBCProfileTest() %>" id="testValueID<%=form1.getInvestigationDetailsID() %>Other" style="display: block;" name="observationsValue" value="<%=form1.getCBCProfileValue() %>">
		                            				
		                            				<select name="" class="form-control otherSelectClass" id="testValueID<%=form1.getInvestigationDetailsID() %>" style="display: none;">
		                            				
		                            				<option value="0">Select Value</option>
			                            		
			                            		<% 	for(String values: LabDefaultValueList){ %>
			                            				
			                            				<% if(values.equals(form1.getCBCProfileValue())){ %>
			                            					
			                            					<option value="<%= values %>" selected="selected"><%= values %></option>
			                            				
			                            				<% }else{ %>	
			                            				
			                            					<option value="<%= values %>"><%= values %></option>
			                            				
			                            				<% } %>
			                            		<% } %>
		                            			
			                            		 </select>
		                            			
		                            			<%
		                            					}
		                            			%>
			                            		
		                            		<%  }%>
		                            			 
		                            			</td>
		                            			
		                            		</tr>
		                            		
			                            <%	}%>
			                            		
			                            	<%-- </s:iterator> --%>
			                            </tbody>
			                        </table>   
								
						    	</div>
							</div>
							
							<div class="row">
						    
						    	<div class="col-md-3">
						    		<font style="font-size: 14px;font-weight: bold;">Conclusion/Comments: </font>
						    	</div>
						    	
							</div>
							
							<div class="row">
						    
						    	<div class="col-md-12">
						    		<s:textarea name="medicalNotes" rows="6" class="form-control"> </s:textarea>
						    	</div>
						    	
							</div>
	                      
	                    
	                      <div class="ln_solid"></div>
	                      
	                      <div class="form-group">
	                        <div class="col-md-12" align="center">
	                          <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                 <button id="sendBtnID" type="submit" class="btn btn-success "  onclick="showLoadingImg();">Update</button>
			                 <button id="" type="button" class="btn btn-info " onclick="window.location='DownloadLabTestReport?visitID=<s:property value="visitID"/>'" onclick="showLoadingImg();">Print Report</button>
			                 <button id="" type="button" class="btn btn-info " onclick="window.location='EmailLabTestReport?visitID=<s:property value="visitID"/>'" onclick="showLoadingImg();">Email Report</button>
	                          <!-- <button class="btn btn-warning" type="button" style="width:15%" id="printButton"   onclick="printVisit();">Print</button> -->
	                        </div>
	                      </div>
				        
				      </form>
			      </div>
			     <!-- End -->
			     
			     
			     <!-- Prescription div -->
			      <%-- <div class="tab-pane fade" id="presc" >
			        <form class="form-horizontal form-label-left" novalidate id="genPhyPrescID" name="genPhyPrescID" action="AddNewPrescription" method="POST" style="margin-top:20px;">
				
					  <div class="row" style="margin-top:10px;margin-left:0px;">
				          <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5>
				      </div>
				      
				     <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
			      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
			      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
				      
				      <div class="row" style="margin-top:10px;margin-left:0px;margin-bottom:5px;">
					
				      <h4 style="margin-top:20px;margin-left:15px;"><b>Prescription</b></h4>
				
				      <div class="row" id="OPDPResc" style="margin-left:0px;margin-right:0px;margin-top:15px;">
				      
				                			<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					                            <thead>
					
					                                <tr >
					                                    <th>Trade Name</th>
					                                    <th>Dose</th>
					                                    <th>Frequency</th>
					                                    <th>No. of Pills</th>
					                                    <th>No. of Days</th>
					                                    <th>Comments</th>
					                                    <th>Action</th>
					                                </tr>
					                             </thead>
					                               
					                            <tbody>
					                            	<tr id="prescTRID">
					                            	</tr>
					                            	
					                            	<s:iterator value="prescriptionList">
					                            		<tr id="visitPrescTRID<s:property value="prescriptionID"/>">
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="tradeName"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="dosage"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="frequency"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="productQuantity"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="noOfDays"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><s:property value="comment"/></td>
					                            			
					                            			<td style="font-size: 14px;text-align: center;"><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick="deletePrescRow('visitPrescTRID<s:property value="prescriptionID"/>',<s:property value="prescriptionID"/>);"/> </td>
					                            			<td style="font-size: 14px;text-align: center;"></td>
					                            		</tr>
					                            	</s:iterator>
					                            	
					                            </tbody>
					                        </table>
				            </div>
				
				            <div class="col-md-2" >
				            	<!-- <div onkeyup="getValuesByDrugName(tradeName.value);"> -->
				            	<div>
									<sx:autocompleter name="tradeName" cssStyle="padding-left:14px;" cssClass="form-control" id="tradeName" list="drugList" showDownArrow="false" /> 
								</div>
				            </div>
				            <div class="col-md-1">
				            <!-- <input type="hidden" class="form-control" style="padding:10px;font-size: 1em;height:35px;border:1px solid gray; color:black;background: transparent;" readonly="readonly" onfocus="getValuesByDrugName(tradeName.value);"  name="drugName" id="drugName" placeholder="Drug Name" autofocus="">
				              <input type="text" class="form-control"  name="dose" id="dose" placeholder="Dose" onfocus="getValuesByDrugName(tradeName.value);"> -->
				              <input type="text" class="form-control"  name="dose" id="dose" placeholder="Dose">
				            </div>
				            
				            <div class="col-md-2">
				              <s:select list="frequencyList" name="frequency"  id="frequency" class="form-control" headerKey="-1" headerValue="Frequency"></s:select>
				            </div>
				            
				            <div class="col-md-2">
				              <input type="text" class="form-control" name="noOfPills" id="noOfPills"  placeholder="no of Pills">
				            </div>
				            
				            <div class="col-md-2">
				              <input type="text" class="form-control"  name="noOfDays" id="noOfDays"  placeholder="# of Days">
				            </div>
				            
				            <div class="col-md-2">
				              <input type="text" class="form-control" name="comment" id="comment"  placeholder="Comment">
				            </div>
				            
				            <div class="col-md-1">
				              <a onclick="addPrescriptionRow('',dose.value,frequency.value,noOfDays.value,visitID.value,comment.value,noOfPills.value);" > 
							  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
															onmouseout="this.src='images/add_icon_1.png'" alt="Add Prescription"
															title="Add Prescription" style="margin-top:5px;height: 24px;" />
							  </a>
				            </div>
				      </div>
				      
				      <div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12" align="center">
		                        <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		                        <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button type="submit" class="btn btn-success">Save</button>
	                          <%
					        	}else{
			                 %>
			                 <button type="submit" class="btn btn-success" >Save</button>
			                 <%
					        	}
			                 %>
		                        <button class="btn btn-warning" type="button" onclick="printPrescription();">Print</button>
		                      </div>
		                    </div>
				    
				      </form>
			      
			    </div> --%>
			    <!-- End -->
			    
			    
			    <!-- Billing div -->
			     <div class="tab-pane fade" id="billing" >
			      <form class="form-horizontal form-label-left" novalidate id="genPhyBillID" name="genPhyBillID" onsubmit="return disableSubmitBtn('addBillBtnID');" action="EditLabBill" method="POST" style="margin-top:20px;">
			
					      <div class="row" style="margin-top:10px;margin-left:0px;">
						          <%-- <h5 style="margin-top:0px;font-size:14px;">Patient Name: <%=firstName %>&nbsp;<%=middleName %>&nbsp;<%=lastName %>&nbsp;(<%=patientID %>)</h5> --%>
						  </div>
						      
					      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
					      <input type="hidden" name="visitID" id="billingVisitID" value="<s:property value="visitID"/>">
					      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
					       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
					        <input type="hidden"  name="aptID" id="aptID" value="<%=apptID%>">
                           
                           <s:iterator value="billList">
                           
                           <input type="hidden" name="billingType" value="<s:property value="billingType"/>">
                           <input type="hidden" name="receiptID" value="<s:property value="receiptID"/>">

						<div class="row" style="margin-top: 15px;padding-left: 15px;">
							
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt Date:</font>
							</div>
							<div class="col-md-4">
								<%-- <font style="font-size: 14px;font-weight: bold;"><s:property value="receiptDate"/></font> --%>
								<input type="text" class="form-control" id="receiptDateID" name="receiptDate" value="<s:property value="receiptDate"/>">
							</div>
							<div class="col-md-2">
								<font style="font-size: 14px;">Receipt No:</font>
							</div>
							<div class="col-md-4">
								<font style="font-size: 14px;font-weight: bold;"><s:property value="receiptNo"/></font>
								<input type="hidden" name="receiptNo" value="<s:property value="receiptNo"/>">
							</div>
							
						</div>

	                      
	                   <div class="row" style="margin-top:10px;padding: 15px;">
							      
							      	<table  class="table table-striped table-bordered dt-responsive nowrap">
							      		
							      		<thead>
								
								            <tr style="font-size: 14px;">
								            	<!-- <th style="text-align: center">Sr.No.</th> -->
								            	<th>Test</th>
								            	<th>Rate</th>
								            	<th></th>
								            </tr>
								        </thead>
								                               
								        <tbody>
								        		<s:iterator value="groupTestList">
								        			<tr style="font-size: 14px;">
								        				<%-- <td style="text-align: center"><s:property value="srNo"/></td> --%>
								        				
								        				<td><s:property value="CBCProfileTest"/>
								        				<input type="hidden" name="test" value="<s:property value="CBCProfileTest"/>">
								        				<input type="hidden" name="billingProdRate" class="billingRateClass" value="<s:property value="rate"/>">
								        				</td>
								        				
								        				<td><s:property value="rate"/></td>
								        				
								        				<td></td>
								        			</tr>
								        		</s:iterator>
								        		
								        		<s:iterator value="prescriptionList">
								        			<tr style="font-size: 14px;">
								        				<%-- <td style="text-align: center"><s:property value="srNo"/></td> --%>
								        				
								        				<td><s:property value="CBCProfileTest"/>
								        				<input type="hidden" name="test" value="<s:property value="CBCProfileTest"/>">
								        				<input type="hidden" name="billingProdRate" class="billingRateClass" value="<s:property value="rate"/>">
								        				</td>
								        				
								        				<td><s:property value="rate"/></td>
								        				<td></td>
								        			</tr>
								        		</s:iterator>
								        	
								        		
								        	<tr style="font-size: 14px;" id="labTestTRID">
								        		
								        			<td id="labTestTDID">
									        			<select name='' id='testID' class='form-control' onchange='retrieveLabTestRate(this.value);'>
									        				<option value=''>Select Tests</option>
									        			</select>
									        		</td>
									        		
									        		<td>
									        			<input type="number" class="form-control" name="" value="0"  id="testRate">
									        		</td>
									        		
									        		<td style="text-align: center;"><a onclick="addTestRow(testID.value, testRate.value, totalVatID.value);" > 
														<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" 
															alt="Add Item" title="Add Item" style="margin-top:5px;height:24px;" /></a></td>
								        		
								        		</tr>
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Total Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="totalAmount"/>" class="form-control" name="totalAmount" id="totalAmountID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
							
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Concession, if any</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" value="<s:property value="totalDiscount"/>" onkeyup="changeNetAmtByDiscount(this.value,totalAmountID.value);" name="totalDiscount" id="totalVatID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Net Amount</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" readonly="readonly" value="<s:property value="netAmount"/>" class="form-control" name="netAmount" id="netAmountID">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Advance Payment</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" value="<s:property value="advPayment"/>" name="advPayment" id="advPaymentID" onkeyup="changeBalancePayment(advPaymentID.value, netAmountID.value);">
								        		</td>
								        		
								        		<td></td>
								        		
											</tr>
											
											<tr>
								        			
								        		<td colspan="1" align="right">
								        			<font style="font-size: 14px; font-weight: bold;">Balance Payment</font>	
								        		</td>
								        		
								        		<td>
								        			<input type="number" class="form-control" readonly="readonly" value="<s:property value="balPayment"/>" name="balPayment" id="balPaymentID">
								        		</td>
								        		
								        		<td></td>
								        		
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
			                    
			                    <div id="cashDetailID" style="padding: 15px;display: none;">
			                    
			                    <div class="ln_solid"></div>
			                    
				                    <div class="row">
				                    	<div class="col-md-2">
				                    		<font style="font-size: 16px;">Cash Paid</font>
				                    	</div>
				                    	<div class="col-md-2">
					                        <input type="text" class="form-control" name="cashPaid" id="cashPaidID" onkeyup="changeCashToReturn(cashPaidID.value, advPaymentID.value);" value="<s:property value="cashPaid"/>" placeholder="Cash Paid">
				                    	</div>
				                    	<div class="col-md-2" align="right">
				                    		<font style="font-size: 16px;">Cash To Return</font>
				                    	</div>
				                    	<div class="col-md-2">
				                    		<input  class="form-control" name="cashToReturn" id="cashToReturnID" value="<s:property value="cashToReturn"/>"  placeholder="Cash To Return" type="number">
				                    	</div>
				                    </div>
				                    
			                    </div>
			                    
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
				                    		<input  class="form-control" name="chequeAmt" id="chequeAmtID" value="<s:property value="chequeAmt"/>"  placeholder="Amount" type="number">
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
					                        <input type="number" class="form-control" value="<s:property value="cardAmount"/>" id="cardAmountID" name="cardAmount" placeholder="Amount">
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
					                        <input type="number" class="form-control" id="otherTypeAmountID" name="otherAmount" value="<s:property value="otherAmount"/>" placeholder="Enter Other amount here">
				                    	</div>
				                    </div>
				                    
			                    </div>
			                    

			                   
	                      
			     </s:iterator>

			     	<div class="ln_solid"></div>
		                      
		                    <div class="form-group">
		                      <div class="col-md-12" align="center">
		                        <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		                        <!-- <button class="btn btn-success" type="button" onclick="addBillDetails(description.value,rate.value,charges.value,totalBill.value,visitID.value);">Add</button> -->
		                        
			               	 <button class="btn btn-success" type="submit" id="addBillBtnID" >Update</button>
			                
		                        
		                       <!--  <button class="btn btn-warning" type="button" onclick="printBilling();">Print</button> -->
		                      </div>
		                    </div>
			      </form>
			      
			      <div class="ln_solid"></div>
			      
			  <!--     <div class="row" id="billDivID">
			      
			                        <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
			                            <thead>
			
			                                <tr>
			                                    <th>Description</th>
			                                    <th>Charges(Rs)</th>
			                                    <th>Action</th>
			                                </tr>
			                             </thead>
			                               
			                            
			                            <tbody>
										<tr>
											<td colspan="2" align="right">Total Bill Amount</td>
											<td></td>
										</tr>
			                            </tbody>
			                        </table>
			                   
			            </div> -->
			      
			    </div>
			    <!-- ends -->
			    
			    <!-- Lab report div -->
			    <div class="tab-pane fade" id="labRep" >
			    
			      <form class="form-horizontal form-label-left" novalidate action="AddLabReport" name="labRepForm" id="labRepForm" method="POST" style="margin-top:20px;" enctype="multipart/form-data">
			      
			      	<input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
			        <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
			        <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
			        <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">

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
		                      
		          <div class="form-group">
		             <div class="col-md-12" align="center">
		                 <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		                 <%
				        	if(labReportResult.equals("Success")){
				         %>
		                 <button class="btn btn-success" type="submit" onclick="showLoadingImg();">Add Lab Report</button>
		                 <%
				        	}else{
		                 %>
		                 <button class="btn btn-success" type="submit"  onclick="showLoadingImg();">Add Lab Report</button>
		                 <%
				        	}
		                 %>
		                 <button class="btn btn-warning" type="button" onclick="downloadLabReport();" >Download Lab Report</button>
		             </div>
		          </div>
			      
			      </form>
			    </div>
			    <!-- Ends -->
			    
			    
			    <!-- Medical certificate -->
			    <div class="tab-pane fade" id="medCerti" >
			    	<form class="app-cam" id="" name="" action="AddMedicalCerificate" method="POST" style="width:95%;margin:0px;" >
						
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
					    
					     				<%
									    	if(medicalCertiText == null || medicalCertiText == ""){
									    %>
									    
									     <div class="col-md-12" style="border:1px solid gray;margin:25px;">
									    
									    <label for="Name" style="font-size:14px;margin-top:20px;">I, Dr. <%=fullName %> registered medical practitioner, 
									      after careful personal examination of the case hereby certify that, 
									      Sh./Smt. <s:property value="firstName"/> <s:property value="lastName"/> working in <input type="text" class="form-control" style="width:20%; display: inline-block;margin-top:10px;" name="companyName" id="companyName">, 
									      is suffering from <input type="text" class="form-control"  name="disease" id="disease" style="width:20%; display: inline-block;margin-top:10px;"> and I consider that 
									      the period of absence from duty of <input type="text" class="form-control"  name="dutyDays" id="dutyDays" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;"> days 
									      with effect from <input type="text" class="form-control"  name="wef" id="wef" style="width:20%; display: inline-block;margin-top:10px; margin-bottom:10px;"> is 
									      absolutely necessary for the restoration of his/her health.</label>
									
									      <div class="row" style="margin-top:25px;">
									        <div class="col-md-12">
									          <div class="col-md-1" style="padding-top:5px;"><label>Place:</label></div>
									          <div class="col-md-3"><input type="text" class="form-control"  name="place" id="place"></div>
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
									    	} else{
									    		
									      %>
									      
									        <div class="col-md-12" style="border:1px solid gray;margin:25px;">
									    		 <div style="margin-top:15px;margin-bottom:15%;"> 
									    		 <label>  
													<%=medicalCertificateFinalText %>
												</label>
												</div>
									      </div>
									      
									      <%
									    	}
									      %>
					      
					     
					
					    </div>
					    
					    <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12" align="center">
			                   <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                   <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button class="btn btn-success" type="submit" onclick="displayVal();" >Save & Print</button>
	                          <%
					        	}else{
			                 %>
			                 <button class="btn btn-success" type="submit" onclick="displayVal();" >Save & Print</button>
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
			    
			    		<form class="app-cam" id="" name="" action="AddReferralLetter" method="POST" style="width:95%;margin:0px;" >
			    		
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
							if(referralLetterText == null || referralLetterText == ""){
						%>
			
						    <div class="row" >
						      
						      <div class="col-md-12" style="margin:15px;">
						
						          <div class="col-md-1" style="margin-left: -15px;"> <label>Date:</label> </div>
						          <div class="col-md-3" style="margin-left: -15px;"> <label><%=currentDate %></label></div>
						        
						
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
						
						          <textarea rows="4" name="conditionText" id="conditionText" class="form-control"></textarea><br><br>
						
						          <label for="Name" style="font-size:14px;">Please advise Mr/Ms <s:property value="firstName"/> <s:property value="lastName"/>  on further treatment. </label> <br><br>
						
						          <label for="Name" style="font-size:14px;">Sincerely, </label>
						          <br>
						          <label for="Name" style="font-size:14px;">Dr. <%=fullName %> </label>
						
						      </div>
						
						    </div>
				
			             <%
							}else{
			             %>
						    <div class="row">
						    	<div class="col-md-12">
						    		<label style="margin:15px;width: 100%;'">
						    			<%=referralLetterFinalText %>
						    		</label>
						    	</div>
						    </div>
			    
			             <%
							}
			             %>
			             
			            <div class="ln_solid"></div>
		                      
			            <div class="form-group">
			               <div class="col-md-12" align="center">
			                   <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
			                   <%
	                          	if(lastEneteredVisitList.equals("success")){
	                          %>
	                         	<button class="btn btn-success" type="submit" onclick="displayRefLet();">Save & Print</button>
	                          <%
					        	}else{
			                 %>
			                 <button class="btn btn-success" type="submit" onclick="displayRefLet();" >Save & Print</button>
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
    <!-- FastClick -->
    <script src="vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="vendors/nprogress/nprogress.js"></script>
    <!-- validator -->
    <script src="vendors/validator/validator.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    
    <!-- Datatables -->
    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>
    
     <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>

    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    
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
   
    document.addEventListener('DOMContentLoaded', function() {

        $('#single_cal3').daterangepicker({
          singleDatePicker: true,
          calender_style: "picker_3"
        }, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#single_cal311').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
        
        $('#receiptDateID').datetimepicker({
	        language:  'en',
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
		
			format: 'dd-mm-yyyy hh:ii:ss'
	    });
        
        $("input[name=observationsValue], .otherClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var inputID = $(this).attr("id");
        		
        		if($(this).attr("id").includes("Other")){
        			$("#"+$(this).attr("id")).toggle();
        			
        			var selectID = inputID.substr(0,inputID.length - 5);
        			
					$("#"+inputID).removeAttr("name");
        			
        			$("#"+selectID).attr("name","observationsValue");
        			
        			$("#"+selectID).toggle();
        			$("#"+selectID).focus();
        			
        		}else{
        			$(this).val("NA");	
        		}
        		//$(this).attr("readonly", "readonly");
        	}
        });
        
        $("select[name=observationsValue], .otherSelectClass").keyup(function(e){
        	if(e.keyCode == 27){
        		var selectID = $(this).attr("id");
        		
        		var otherID = selectID+"Other";
        		
        		//$("#"+selectID).toggle(500);
        		$("#"+otherID).attr("name","observationsValue");
        		$("#"+otherID).toggle();
        		$("#"+otherID).focus();
        		$("#"+selectID).toggle();
        		
        		$("#"+selectID).removeAttr("name");
        		
        		/* $(this).toggle({
        			
        		}); */
        		//$(this).attr("readonly", "readonly");
        	}
        });
        
var visitID = $("#billingVisitID").val();
        
        console.log("..visitID.."+visitID);
        
        xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var msg = "";
				
				var selectTag = "<select name='' id='testID' class='form-control' onchange='retrieveLabTestRate(this.value);'>"+
								"<option value=''>Select Tests</option>";

				for ( var i = 0; i < array.Release.length; i++) {
					msg = array.Release[i].MSG;
					
					selectTag += "<option value='"+array.Release[i].TestID+"="+array.Release[i].TestRate+"'>"+array.Release[i].TestName+"</option>";
				}
				
				selectTag += "</select>";
				
				$("#labTestTDID").html(selectTag);
				
			}
		};
		xmlhttp.open("GET", "RetrieveLabTestByVisitID?visitID="+visitID, true);
		xmlhttp.send();
        
      });
    
    function retrieveLabTestRate(testValue){
    	var array1 = testValue.split("=");
    	
    	var testID = array1[0];
    	
    	var testRate = array1[1];
    	
    	$("#testRate").val(testRate);
    }
    
    var testCounter = 1;
    
    function addTestRow(testValue, testRate, concessionRate){
    	
    	if(testValue == ""){
    		
    		alert("Please select test.");
    		
    	}else{
    		
    		if(concessionRate == ""){
        		concessionRate = 0;
        	}
        	
    		var itemRate = 0;
        	
        	$(".billingRateClass").each(function(){
        		
        		itemRate = parseFloat(itemRate + parseFloat($(this).val()));
        		
        	});
        	
        	console.log("..itemRate.."+itemRate);
        	
        	var testName = $("#testID option:selected").text().trim();
        	
        	var testValArray = testValue.split("=");
        	
    		var testID = testValArray[0];
        	
        	var testRate = testValArray[1];
        	
        	var trID = "newTestTRID"+testCounter;
        	var tdID = "newTestTDID"+testCounter;
        	
    		var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
    		"<td>"+testName+"<input type='hidden'  name='testName' value='"+testName+"'></td>"+
    		"<td>"+testRate+"<input type='hidden' class='billingRateClass' name='testRate' value='"+testRate+"'></td>"+
    		"<td style='text-align: center;'><img src='images/delete_icon_1.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\"" + trID +"\",\""+testRate+"\",\""+concessionRate+"\",\""+testValue+"\",\""+testName+"\");'/></td>"+
    		"</tr>";

    		$(trTag).insertBefore($('#labTestTRID'));
        	
        	var totalAmount = parseFloat(itemRate + parseFloat(testRate));
        	
        	$("#totalAmountID").val(totalAmount);
        	
        	var netAmount = parseFloat(totalAmount - parseFloat(concessionRate));
        	
        	$("#netAmountID").val(netAmount);
        	$("#balPaymentID").val(netAmount);
        	
        	testCounter++;
        	
        	$("#testID").val("");
        	$("#testRate").val("0");
        	
        	//removing that option tag from select tag
        	$("#testID option[value='"+testValue+"']").remove();
    		
    	}
    	
    }
    
    function removeTR(trID, testRate, concessionRate, testValue, testName){
    	
    	if(confirm("Are you sure you want to delete this row?")){
    		
    		$('#testID')
            .append($("<option></option>")
                       .attr("value",testValue)
                       .text(testName));
    		
    		if(testRate == ""){
        		testRate = 0;
        	}
        	
        	if(concessionRate == ""){
        		concessionRate = 0;
        	}
        	
        	itemRate = 0;
        	
        	$(".billingRateClass").each(function(){
        		
        		itemRate = parseFloat(itemRate + parseFloat($(this).val()));
        		
        	});
        	
        	console.log("./itemRate.."+itemRate);
        	
    		var totalAmount = parseFloat(itemRate - parseFloat(testRate));
        	
        	$("#totalAmountID").val(totalAmount);
        	
        	var netAmount = parseFloat(totalAmount - parseFloat(concessionRate));
        	
        	$("#netAmountID").val(netAmount);
        	$("#balPaymentID").val(netAmount);
        	
        	$("#"+trID).remove();
    		
    	}
  
    }
   
    </script>
    
  </body>
</html>