<%@page import="java.util.Collections"%>
<%@page import="java.util.Set"%>
<%@page import="com.edhanvantari.form.PatientForm"%>
<%@page import="com.edhanvantari.daoImpl.ClinicDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.ClinicDAOInf"%>
<%@page import="java.util.HashMap"%>
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

    <title>Lab Test Quotation | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    
    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
     <link  rel="stylesheet" type="text/css" href="build/css/bootstrap-multiselect.css" />
     
    <link rel="stylesheet" href="build/css/jquery-ui.css"> 
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    

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

		.select2-container--default .select2-selection--multiple .select2-selection__choice__remove {
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

		function showLoadingImg1(){	
			var sampleID = $("#sampleID").val();
			
			var mdDoctorID = $("#mdDoctorID").val();
			
			var labTestAutoID = $("#labTestAutoID").val(); 
			
			if(sampleID == null || sampleID ==""){
				alert("Please add sampleID field");
				return false;
			}else if(mdDoctorID ==""){
				alert("Please Select MD Doctor");
				return false;
			}else {
				$('html, body').animate({
				    scrollTop: $('body').offset().top
				}, 1000);
					
				$('body').css('background', '#FCFAF5');
				$(".container").css("opacity","0.2");
				$("#loader").show();
	
				return true;
			}
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
		
		HashMap<String, String>	labTestValueListNew = (HashMap<String, String>) request.getAttribute("labTestValueListNew");
		HashMap<String, String>	labTestValueListNew1 = (HashMap<String, String>) request.getAttribute("labTestValueListNew1");
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
	
    
    
    <!-- Ends -->
    
    
	
	
	
	<script type="text/javascript">
	
		function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		}
		
	</script>
	
	<script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		//setInterval(function(){$('#weekCalendar').fullCalendar('refetchEvents')}, 30000);
    		setInterval(function(){$('body').css('padding-right','0px')}, 1000);
    		
    		$('#collectionTimeID').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0,
    			format: 'hh:ii:ss'
    	    });
    		
    		setTimeout(function(){
    			//alert('hii');
    			$('#collectionTimeID').attr("value",$("#apptStartTimeID1Hidden").val());
    		},1000);
    		
    		
        });
    </script>
    
    <script type="text/javascript">
    
    	var paymentCounter = 1;

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
    	
    	//For dues pending modal
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
    	String gender = (String) request.getAttribute("gender");
   		System.out.println("gender :: "+gender);
    	if(gender == null || gender == ""){
    		gender = "Male";	
    	}
    	
    	HashMap<String, String> genderMap = new HashMap<String, String>();
    	
    	genderMap.put("MALE", "normalValues");
    	genderMap.put("FEMALE", "normalValuesWomen");
    	genderMap.put("CHILD", "normalValuesChild");
    
    	// retrieving hashmap of group as key with values as their respective lab tests
    	//HashMap<String, HashMap<String, String>> groupLabTestMap = patientDAOInf.retrieveGroupLabTestsList(genderMap.get(gender),"");
    
    	//List<PatientForm> list = patientDAOInf.retrievePVTests(genderMap.get(gender));
		
		String labTest = (String) request.getAttribute("labTest");
		System.out.println("labTest :: "+labTest);
		if(labTest == null || labTest == ""){
			labTest = "dummy";
		}
		
		String paymentType = (String) request.getAttribute("paymentType");
		System.out.println("paymentType :: "+paymentType);
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
		
		function feildCheck(event){
			
			var sampleID = $("#sampleID").val();
			
			var mdDoctorID = $("#mdDoctorID").val();
			
			var labTestAutoID = $("#labTestAutoID").val(); 
			
			console.log("inside else if else........"+labTestAutoID);
			
			if(sampleID == null || sampleID ==""){
				alert("Please add sampleID field");
				return false;
			}else if(mdDoctorID ==""){
				alert("Please Select MD Doctor");
				return false;
			}else {
				$('html, body').animate({
				   scrollTop: $('body').offset().top
				}, 1000);
					
				$('body').css('background', '#FCFAF5');
				$(".container").css("opacity","0.2");
				$("#loader").show();
	
				return true;
			}
		}
		
		// function to test all single test checkboxes and making it readonly
		function checkSingleTests(checkID, checkValue){
			if($("#"+checkID).is(":checked")){
				// spliting value by === and checking the checkboxes from single
				// test sections checked and readonly
				var checkArray = checkValue.split("===");
				
				//alert('hoo');
				
				for(var i = 0; i < checkArray.length; i++){
					$("input[name='test'][value='"+checkArray[i]+"$0']").prop("checked", true);
					$("input[name='test'][value='"+checkArray[i]+"$0']").attr("onclick", "return false;");
					$("label input[name='test'][value='"+checkArray[i]+"$0']").css("cursor","not-allowed");
					$("input[name='test'][value='"+checkArray[i]+"$0']").val(checkArray[i]+"$1");
				}
			}else{
				
				// spliting value by === and unchecking the checkboxes from single
				// test sections and making them readonly
				var checkArray = checkValue.split("===");
				
				for(var i = 0; i < checkArray.length; i++){
					$("input[name='test'][value='"+checkArray[i]+"$1']").prop("checked", false);
					$("input[name='test'][value='"+checkArray[i]+"$1']").removeAttr("onclick");
					$("input[name='test'][value='"+checkArray[i]+"$1']").css("cursor","pointer");
					$("input[name='test'][value='"+checkArray[i]+"$1']").val(checkArray[i]+"$0");
				}
				
			}
		}
		
		function retrieveValues(value, text){
			
			console.log("value: "+value+"text: "+text);
			
			var rate = value.split('$');
			
			if(value == text){
				console.log("inside if");
				retriveGroupValues(text);
				
			}else{
				console.log("inside else"+rate[2]);
				
				passTestToSelect(value+"$0", rate[2], 'totalRateID');
			}
		}
		
		function passTestToSelect(selectVal, rateVal, totalRateID){
					
					console.log("teste :: "+selectVal);
					
					var optionValue = selectVal.split('$');
					
					var rate = $("#"+totalRateID).val();
					
					var finalRate = 0;
				
					var stringArr = $("#labTestHiddenID").val();
					
					var stringValue= "";
					
					if(optionValue[optionValue.length - 1] == "1"){
						$('#newLabTestID').append("<label class='checkbox'><input type='checkbox' class='checkboxClass' value='0$"+selectVal+"' checked='checked' onclick='removeTestToSelect(this.value, \"totalRateID\" );'> "+optionValue[4]+" - "+optionValue[3]+"</label></li>")
						//$(".js-example-placeholder-multiple"). empty();
					}else{
							
						$('#newLabTestID1').append("<label class='checkbox'><input type='checkbox' class='checkboxClass' value='0$"+selectVal+"' checked='checked' onclick='removeTestToSelect(this.value, \"totalRateID\" );'> "+optionValue[0]+" - "+optionValue[2]+"</label></li>")
						//$(".js-example-placeholder-multiple1"). empty();
					}
				
					finalRate = parseInt(rate) + parseInt(rateVal);
						
					console.log("finalRate : "+finalRate+"--"+rateVal);
						
					$("#"+totalRateID).val(finalRate);
							
					stringValue = stringArr + "*" + selectVal;
						
					$("#labTestHiddenID").val(stringValue);	
				
		}
		
		function removeLabTest(testValID, test, isGroup){
			
			console.log("test: "+testValID+"-"+test+"-"+isGroup)
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
		
					for ( var i = 0; i < array.Release.length; i++) {

						var check = array.Release[i].check;
					}
	
				}
			};
			xmlhttp.open("GET", "RemoveLabInvestigationTest?labTestID="+ testValID, true);
			xmlhttp.send();
			
		}
		
		function removeTestToSelect(selectVal, totalRateID){
			
			var optionValue = selectVal.split('$');
			
			var rateValue = 0;
			
			if(optionValue[7] == "1"){
				
				rateValue = optionValue[4];
			}else{
				rateValue = optionValue[3];
			}
			
			var rate = $("#"+totalRateID).val();
			
			var finalRate = 0;
			
			var stringArr = $("#labTestHiddenID").val();
			
			var finalStringVal = "";
			
			var stringValue= "";
			
			finalRate = parseInt(rate) - parseInt(rateValue);
				 
			console.log("finalRateNew : "+finalRate+"--"+rate+"--"+rateValue);
				 
			$("#"+totalRateID).val(finalRate);
				 
			if(stringArr.includes('*')){
						
				console.log("inside stringArr......"+stringArr+"-----------"+selectVal);
					 
				if(stringArr.includes("*"+selectVal)){
						
					finalStringVal = stringArr.replace("*"+selectVal, "");
							
					$("#labTestHiddenID").val(finalStringVal);		
				}
			}else{
						
				finalStringVal = stringArr.replace(selectVal, "");
						
				$("#labTestHiddenID").val(finalStringVal);
			}
			
			if(optionValue[0] == "0"){
				console.log("inside if : "+optionValue[1]+optionValue[7]+"TRID");
				
				$("label:has(:checkbox:not(:checked))").remove()
				
			}else{
				
				$("label:has(:checkbox:not(:checked))").remove()
				
				removeLabTest(optionValue[0], optionValue[1], optionValue[7]);
			}
			
		}
		
	</script>
    
    <!-- Retrieve values details by groupName -->
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retriveGroupValues(groupName) {
			var patientID = $("#patientID").val();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					var groupValues = "";
					
					var groupRate = 0;
					
					var groupCheck = 0;
					
					var length = array.Release.length;
					
					for ( var i = 0; i < array.Release.length; i++) {
						groupValues = array.Release[i].groupValues;
						
						groupCheck++;
						
						if(groupCheck == length){
							groupRate = array.Release[i].groupRate
						}
						
						console.log('groupValues: '+groupValues);
						const myArray = groupValues.split("===");
						groupValues = myArray[0]+"$1"+"*"+myArray[1]+"$1";
						
						passTestToSelect(groupValues, groupRate, 'totalRateID');
					}
	
				}
			};
			xmlhttp.open("GET", "RetrieveGroupValues?groupName="+groupName, true);
			xmlhttp.send();
		}
	</script>
	<!-- ENds -->
	
  <sx:head/>
  
  </head>
 
 <body class="nav-md" >
  
  <!-- To show loading icon while page is loading -->
   <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
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
                        <h3>Lab Test Quotation</h3>
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
		              
					
			         
			         
			        
				 <div id="myTabContent1" class="tab-content">
				 
				 	<!-- Visit div -->
			   <!--  <div class="tab-pane fade in active" id="visit"> -->
				    
	
				     		
							<div class="row" style="margin-top:10px;">
						    	
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10 multiselect" style="margin-left: 1%;">
						    	<div class ="row">
						    		<s:select list="getGroupTestList" headerKey="" headerValue="" name="groupTest" id="groupTest" class="form-control" onchange = "selectGroupLabTest()"></s:select>
									<%-- <select name="" class="js-example-placeholder-multiple js-states form-control" id="labTestAutoID" value="" style="width:100%" data-placeholder="Select Group" tabindex="-1" multiple>
									</select> --%>
									<!-- js-example-basic-multiple  onchange="passTestToSelect(this.value, 'totalRateID');"-->
						    	</div>
						    	<div class ="row">
							    	<div id="newLabTestID" style="margin-left: 10%; margin-top: -1%;">
							    		<input type="hidden" id="labTestHiddenID" class="form-control" name="labTestDetails">
							    		<%-- <s:select list="labTestValueListNew" headerKey="" value="labTestListValues" name="labTestDetails" id="newLabTestID" class="form-control multiCheckboxClass" multiple="true" onclick="removeTestToSelect(this.value, 'totalRateID');"></s:select> --%>
							    		
							    		<% for(String testValue : labTestValueListNew.keySet()){ %> 
							    			
							    			<label class="checkbox"><input type="checkbox" class="checkboxClass" value="<%= testValue%>" id="<%=labTestValueListNew.get(testValue)%>ID" checked="checked" onclick="removeTestToSelect(this.value, 'totalRateID');"> <%=labTestValueListNew.get(testValue)%></label> 
							    		
							    		<% } %>  
							    	</div>
							    </div>
						    	</div>
						    	
						    	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-10 multiselect" style="margin-left: 1%;">
						    	<div class ="row">
						    		<s:select list="getSingleTestList" headerKey="" headerValue="" name="singleTest" id="singleTest" class="form-control" onchange = "selectGroupLabTest()"></s:select>
									<%-- <select name="" class="js-example-placeholder-multiple1 js-states form-control" id="labTestAutoID1" value="" style="width:100%" data-placeholder="Select Test" tabindex="-1" multiple>
									</select> --%>
									<!-- js-example-basic-multiple  onchange="passTestToSelect(this.value, 'totalRateID');"-->
						    	</div>
						    	<div class ="row">
							    	<div id="newLabTestID1" style="margin-left: 10%; margin-top: -1%;">
							    		<!-- <input type="hidden" id="labTestHiddenID1" class="form-control" name="labTestDetails"> -->
							    		<%-- <s:select list="labTestValueListNew" headerKey="" value="labTestListValues" name="labTestDetails" id="newLabTestID" class="form-control multiCheckboxClass" multiple="true" onclick="removeTestToSelect(this.value, 'totalRateID');"></s:select> --%>
							    		
							    		<% for(String testValue1 : labTestValueListNew1.keySet()){ %> 
							    			
							    			<label class="checkbox"><input type="checkbox" class="checkboxClass" value="<%= testValue1%>" id="<%=labTestValueListNew1.get(testValue1)%>ID1" checked="checked" onclick="removeTestToSelect(this.value, 'totalRateID');"> <%=labTestValueListNew1.get(testValue1)%></label> 
							    		
							    		<% } %>  
							    	</div>
							    </div>
						    	</div>		
						    	
						    	<div class="col-md-2" style="margin-top: -2%;">
							    	<s:if test="%{getTotalRate() == 0}">
							    		Total rate : <input type="number" id="totalRateID" name="totalRate" class="form-control" readonly="readonly" value="0">
							    	</s:if>
									<s:else>
										Total rate : <input type="number" id="totalRateID" name="totalRate" class="form-control" readonly="readonly" value="<s:property value="totalRate"/>">
									</s:else>
						    		
						    	</div>
						    	
							</div>
							
				      
			    </div>
			    <!-- ends -->
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
   <%--  <script src="vendors/jquery/dist/jquery.min.js"></script> --%>
    <!-- Bootstrap -->
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="vendors/nprogress/nprogress.js"></script>
    <!-- validator -->
    <script src="vendors/validator/validator.js"></script>
   
	<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script> 

	
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
     
     <script type="text/javascript" src="build/js/bootstrap-multiselect.js" charset="UTF-8"></script>
     
     <script src="build/js/jquery-ui.js"></script>
     
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
   
    /*For lab test autocompletor  */	
	
	var patientID = $("#patientID").val();
	
	var finalarr= [];
	
	$(document).ready(function(){
				
		$(".js-example-placeholder-multiple").select2({
			includeSelectAllOption: true,
			  ajax: { 
			   url: "SearchLabTestNameList?groupCheck=0",
			   type: "GET",
			   dataType: 'json',
			   delay: 250,
			   data: function (params) {
				return {
			    	searchTestName: params.term // search term
			    };
			   },
			   processResults: function (data) {
				  var arr = []
				  var arr1 = []
				  var mergeArr = []
				  
				  var counter = 0
				  var counter1 = 0
				  
                   $.each(data, function (index, value) {
                	   
                	   if(finalarr.includes(index)){
            			   return true;
            		   }
                	   
                	  if(counter == 0){
                    	arr.push({
                      		text: "Profile Test", isNew: true,
                  			children: [{
                  			    id: index,
                  			    text: index,
                  			    newOption: true
                  			}]
                        })
                      }else{
                    	arr.push({
                      		//text: "Profile Test", isNew: true,
                  			children: [{
	                  			id: index,
	                  			text: index,
	                  			newOption: true
                  			}]
                         })
                      }
                    	
                    	counter++;
                      
                      
                   })
                        
                   for(var i=0; i<arr.length; i++){
                	   mergeArr.push(arr[i]);
                   }
				 
				  for(var j=0; j<arr1.length; j++){
               	   mergeArr.push(arr1[j]);
                  }
				 
                  console.log('mergeArr =>' , mergeArr[0].id, 'mergeArr1 =>', mergeArr[0].text);
				  return {
					   results: mergeArr
				   }; 
               },
               
			   cache: true
			  },
			  escapeMarkup: function (markup) { return markup; },
	          minimumInputLength: 1
	           
		}).on('select2:select', function(e) {
			
			var data = e.params.data;
		   
			console.log('select_val: ', data.text, 'data: ', data.id)
			
			finalarr.push(data.text);
			
			retrieveValues(data.id, data.text);
			//$("#select2-selection__choice").remove()
		});
		
		
		$(".js-example-placeholder-multiple1").select2({
			includeSelectAllOption: true,
			  ajax: { 
			   url: "SearchLabTestNameList?groupCheck=0",
			   type: "GET",
			   dataType: 'json',
			   delay: 250,
			   data: function (params) {
				return {
			    	searchTestName: params.term // search term
			    };
			   },
			   processResults: function (data) {
				  var arr = []
				  var arr1 = []
				  var mergeArr = []
				  
				  var counter = 0
				  var counter1 = 0
				  
                   $.each(data, function (index, value) {
                	   
                	   
                	   $.each(value, function (index, value) {
                		  
                		   if(finalarr.includes(value)){
                			   return true;
                		   }
                		   
                		   if(counter1 ==0){
                			   arr1.push({
                                   
                       			text: "Single Test", isNew: true,
                       			children: [{
   	                    			id: index,
   	                    			text: value,
   	                    			newOption: true
                       			}]
                               })
                		   }else{
                			   arr1.push({
                                   
                       			//text: "Single Test", isNew: true,
                       			children: [{
   	                    			id: index,
   	                    			text: value,
   	                    			newOption: true
                       			}]
                               })
                		   }
                		  
                		   counter1++;
                       })  
                   })
                        
                   for(var i=0; i<arr.length; i++){
                	   mergeArr.push(arr[i]);
                   }
				 
				  for(var j=0; j<arr1.length; j++){
               	   mergeArr.push(arr1[j]);
                  }
				 
                  console.log('mergeArr =>' , mergeArr[0].id, 'mergeArr1 =>', mergeArr[0].text);
				  return {
					   results: mergeArr
				   }; 
               },
               
			   cache: true
			  },
			  escapeMarkup: function (markup) { return markup; },
	          minimumInputLength: 1
	           
		}).on('select2:select', function(e) {
			
			var data = e.params.data;
		   
			console.log('select_val: ', data.text, 'data: ', data.id)
			
			finalarr.push(data.text);
			
			retrieveValues(data.id, data.text);
			//$("#select2-selection__choice").remove()
		});
	});
	
</script>
    
	<script>
   
    
    
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
    		"<td>"+testName+"<input type='hidden'  name='test' value='"+testName+"'></td>"+
    		"<td>"+testRate+"<input type='hidden' class='billingRateClass' name='billingProdRate' value='"+testRate+"'></td>"+
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
    
    function selectGroupLabTest()
    {
    	
    	var groupTest = $("#groupTest").val();
    	var singleTest = $("#singleTest").val();
    
    	var groupRate = 0;
    	var testRate = 0;
    	
    	if(groupTest != "")
    		{
	    		for(var i = 0 ; i<groupTest.length ; i++)
	    		{
	    			
	    			var groupValue = groupTest[i].toString().split('$');
	    			//alert(groupTest[i]);
	    			
	    			if(typeof(groupValue[1]) != "undefined")
	    				{	
	    					groupRate = groupRate + parseInt(groupValue[1]);	
	    					
	    				}
	    		}
	    	 	
    		}
    	   	
    	
    	if(singleTest != "")
    		{
	    		for(var j = 0 ;j<singleTest.length ;j++)
	    		{
	    			var testValue = singleTest[j].toString().split('$');
	    			if(typeof(testValue[5]) != "undefined")
	    				{
	    					testRate = testRate + parseInt(testValue[5]);		
	    				}
	    		}
    		}
	    	
    	
    	var finalRate = groupRate + testRate;
    	
    	$("#totalRateID").val(finalRate);
    	
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
        	
    		var totalAmount = parseFloat(itemRate - parseFloat(testRate));
        	
        	$("#totalAmountID").val(totalAmount);
        	
        	var netAmount = parseFloat(totalAmount - parseFloat(concessionRate));
        	
        	$("#netAmountID").val(netAmount);
        	$("#balPaymentID").val(netAmount);
        	
        	$("#"+trID).remove();
    		
    	}
    }
    
    $(document).ready(function() {
		
	    $('#singleTest').select2({
	      placeholder: "Select Single Test",
	      width: "100%",
	      multiple:true

	    })
	    
	    $('#groupTest').select2({
		      placeholder: "Select Group Test",
		      width: "100%",
		      multiple:true
		    })
	  
  });
    </script>
  </body>
</html>