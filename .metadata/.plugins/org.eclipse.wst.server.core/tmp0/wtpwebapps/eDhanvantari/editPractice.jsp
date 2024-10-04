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

<title>Edit Practice | E-Dhanvantari</title>

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
	
	 <link href="build/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
			
        document.location="editPracticeList.jsp";
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


 <!-- remove up down arrow from input type number -->
    <style type="text/css">
		input[type=number]::-webkit-inner-spin-button, 
		input[type=number]::-webkit-outer-spin-button { 
			-webkit-appearance: none; 
			 margin: 0; 
		}
		
	.fa-arrow-circle-up:before {
		font-size: 30px;
		color: #4169E1;
	}
		
	</style>
	

<!-- To visible logo file upload div -->

<script type="text/javascript">
    	function logoShow(){
			$('#profilePicID1').hide();
			$('#profilePicID').show();
			$('#logoClickID').click();
    	}
    </script>
    
    <script type="text/javascript">
  
    	function logoImgShow(Did,DDid){
    		
    		$('#'+DDid).hide();
			
    		$('#'+Did).show();
			
    	}
    	
    </script>

<!-- Ends -->



<!-- Chckbox check according to values store in DB -->

<%
	String applScheduled = (String) request.getAttribute("applScheduled");

	if (applScheduled == null || applScheduled == "") {
		applScheduled = "dummy";
	}

	String applUpdated = (String) request.getAttribute("applUpdated");

	if (applUpdated == null || applUpdated == "") {
		applUpdated = "dummy";
	}

	String applCancelled = (String) request.getAttribute("applCancelled");

	if (applCancelled == null || applCancelled == "") {
		applCancelled = "dummy";
	}

	String sendBill = (String) request.getAttribute("sendBill");

	if (sendBill == null || sendBill == "") {
		sendBill = "dummy";
	}

	String sendPresc = (String) request.getAttribute("sendPresc");

	if (sendPresc == null || sendPresc == "") {
		sendPresc = "dummy";
	}

	String sendThankRefDoc = (String) request.getAttribute("sendThankRefDoc");

	if (sendThankRefDoc == null || sendThankRefDoc == "") {
		sendThankRefDoc = "dummy";
	}

	String welcomeMSg = (String) request.getAttribute("welcomeMSg");

	if (welcomeMSg == null || welcomeMSg == "") {
		welcomeMSg = "dummy";
	}
	
	String patLoginCredMSg = (String) request.getAttribute("patLoginCredMSg");

	if (patLoginCredMSg == null || patLoginCredMSg == "") {
		patLoginCredMSg = "dummy";
	}

	String leaveAppl = (String) request.getAttribute("leaveAppl");

	if (leaveAppl == null || leaveAppl == "") {
		leaveAppl = "dummy";
	}

	String leaveAppr = (String) request.getAttribute("leaveAppr");

	if (leaveAppr == null || leaveAppr == "") {
		leaveAppr = "dummy";
	}

	String leaveRej = (String) request.getAttribute("leaveRej");

	if (leaveRej == null || leaveRej == "") {
		leaveRej = "dummy";
	}

	String leaveCalcel = (String) request.getAttribute("leaveCalcel");

	if (leaveCalcel == null || leaveCalcel == "") {
		leaveCalcel = "dummy";
	}
	
	String apptReminder = (String) request.getAttribute("apptReminder");

	if (apptReminder == null || apptReminder == "") {
		apptReminder = "dummy";
	}
	
	String inventoryAlert = (String) request.getAttribute("inventoryAlert");

	if (inventoryAlert == null || inventoryAlert == "") {
		inventoryAlert = "dummy";
	}

	/*String holidayInti = (String) request.getAttribute("holidayInti");

	if (holidayInti == null || holidayInti == "") {
		holidayInti = "dummy";
	}*/
	
	String reviewForm = (String) request.getAttribute("reviewForm");

	if (reviewForm == null || reviewForm == "") {
		reviewForm = "dummy";
	}
	
	String reviewFormURL = (String) request.getAttribute("reviewFormURL");

	if (reviewFormURL == null || reviewFormURL == "") {
		reviewFormURL = "dummy";
	}
%>

<script type="text/javascript">
    
    document.addEventListener('DOMContentLoaded', function() {
    
	    <%//For Appointment scheduled
			if (applScheduled.equals("both")) {%>
	    
	    $('input[name=apptSchedlSMS]').prop('checked', true);
	    $('input[name=apptSchedlEmail]').prop('checked', true);
	    
	    <%} else if (applScheduled.equals("SMS")) {%>
	    
	    $('input[name=apptSchedlSMS]').prop('checked', true);
	    
	    <%} else if (applScheduled.equals("Email")) {%>
	    
	    $('input[name=apptSchedlEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Appointment Updated
			if (applUpdated.equals("both")) {%>
	    
	    $('input[name=apptUpdatedSMS]').prop('checked', true);
	    $('input[name=apptUpdatedEmail]').prop('checked', true);
	    
	    <%} else if (applUpdated.equals("SMS")) {%>
	    
	    $('input[name=apptUpdatedSMS]').prop('checked', true);
	    
	    <%} else if (applUpdated.equals("Email")) {%>
	    
	    $('input[name=apptUpdatedEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Appointment Cancelled
			if (applCancelled.equals("both")) {%>
	    
	    $('input[name=apptCancelledSMS]').prop('checked', true);
	    $('input[name=apptCancelledEmail]').prop('checked', true);
	    
	    <%} else if (applCancelled.equals("SMS")) {%>
	    
	    $('input[name=apptCancelledSMS]').prop('checked', true);
	    
	    <%} else if (applCancelled.equals("Email")) {%>
	    
	    $('input[name=apptCancelledEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Send Bill
			if (sendBill.equals("both")) {%>
	    
	    $('input[name=sendBillSMS]').prop('checked', true);
	    $('input[name=sendBillEmail]').prop('checked', true);
	    
	    <%} else if (sendBill.equals("SMS")) {%>
	    
	    $('input[name=sendBillSMS]').prop('checked', true);
	    
	    <%} else if (sendBill.equals("Email")) {%>
	    
	    $('input[name=sendBillEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Send Prescription
			if (sendPresc.equals("both")) {%>
	    
	    $('input[name=sendPrescSMS]').prop('checked', true);
	    $('input[name=sendPrescEmail]').prop('checked', true);
	    
	    <%} else if (sendPresc.equals("SMS")) {%>
	    
	    $('input[name=sendPrescSMS]').prop('checked', true);
	    
	    <%} else if (sendPresc.equals("Email")) {%>
	    
	    $('input[name=sendPrescEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Send ref doc thanks
			if (sendThankRefDoc.equals("both")) {%>
	    
	    $('input[name=sendThanksToRefDocSMS]').prop('checked', true);
	    $('input[name=sendThanksToRefDocEmail]').prop('checked', true);
	    
	    <%} else if (sendThankRefDoc.equals("SMS")) {%>
	    
	    $('input[name=sendThanksToRefDocSMS]').prop('checked', true);
	    
	    <%} else if (sendThankRefDoc.equals("Email")) {%>
	    
	    $('input[name=sendThanksToRefDocEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For Patient login credentials
			if (patLoginCredMSg.equals("both")) {%>
	    
	    $('input[name=patLoginCredSMS]').prop('checked', true);
	    $('input[name=patLoginCredEmail]').prop('checked', true);
	    
	    <%} else if (patLoginCredMSg.equals("SMS")) {%>
	    
	    $('input[name=patLoginCredSMS]').prop('checked', true);
	    
	    <%} else if (patLoginCredMSg.equals("Email")) {%>
	    
	    $('input[name=patLoginCredEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For welcome msg
			if (welcomeMSg.equals("both")) {%>
	    
	    $('input[name=welcomeMsgSMS]').prop('checked', true);
	    $('input[name=welcomeMsgEmail]').prop('checked', true);
	    
	    <%} else if (welcomeMSg.equals("SMS")) {%>
	    
	    $('input[name=welcomeMsgSMS]').prop('checked', true);
	    
	    <%} else if (welcomeMSg.equals("Email")) {%>
	    
	    $('input[name=welcomeMsgEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For leave application
			if (leaveAppl.equals("both")) {%>
	    
	    $('input[name=leaveApplSMS]').prop('checked', true);
	    $('input[name=leaveApplEmail]').prop('checked', true);
	    
	    <%} else if (leaveAppl.equals("SMS")) {%>
	    
	    $('input[name=leaveApplSMS]').prop('checked', true);
	    
	    <%} else if (leaveAppl.equals("Email")) {%>
	    
	    $('input[name=leaveApplEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For leave approval
			if (leaveAppr.equals("both")) {%>
	    
	    $('input[name=leaveApprovSMS]').prop('checked', true);
	    $('input[name=leaveApprovEmail]').prop('checked', true);
	    
	    <%} else if (leaveAppr.equals("SMS")) {%>
	    
	    $('input[name=leaveApprovSMS]').prop('checked', true);
	    
	    <%} else if (leaveAppr.equals("Email")) {%>
	    
	    $('input[name=leaveApprovEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For leave rejection
			if (leaveRej.equals("both")) {%>
	    
	    $('input[name=leaveRejSMS]').prop('checked', true);
	    $('input[name=leaveRejEmail]').prop('checked', true);
	    
	    <%} else if (leaveRej.equals("SMS")) {%>
	    
	    $('input[name=leaveRejSMS]').prop('checked', true);
	    
	    <%} else if (leaveRej.equals("Email")) {%>
	    
	    $('input[name=leaveRejEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For leave cancellation
			if (leaveCalcel.equals("both")) {%>
	    
	    $('input[name=leaveCanclSMS]').prop('checked', true);
	    $('input[name=leaveCanclEmail]').prop('checked', true);
	    
	    <%} else if (leaveCalcel.equals("SMS")) {%>
	    
	    $('input[name=leaveCanclSMS]').prop('checked', true);
	    
	    <%} else if (leaveCalcel.equals("Email")) {%>
	    
	    $('input[name=leaveCanclEmail]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For appt reminder
			if (apptReminder.equals("both")) {%>
	    
	    $('input[name=smsDailyAppt]').prop('checked', true);
	    $('input[name=emailDailyAppt]').prop('checked', true);
	    
	    <%} else if (apptReminder.equals("SMS")) {%>
	    
	    $('input[name=smsDailyAppt]').prop('checked', true);
	    
	    <%} else if (apptReminder.equals("Email")) {%>
	    
	    $('input[name=emailDailyAppt]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For inventory alert
			if (inventoryAlert.equals("both")) {%>
	    
	    $('input[name=smsInventory]').prop('checked', true);
	    $('input[name=emailInventory]').prop('checked', true);
	    
	    <%} else if (inventoryAlert.equals("SMS")) {%>
	    
	    $('input[name=smsInventory]').prop('checked', true);
	    
	    <%} else if (inventoryAlert.equals("Email")) {%>
	    
	    $('input[name=emailInventory]').prop('checked', true);
	    
	    <%}%>
	    
	    <%//For holiday intimation
			//if (holidayInti.equals("both")) {%>
	    
	   // $('input[name=holidayIntimtnSMS]').prop('checked', true);
	   //$('input[name=holidayIntimtnEmail]').prop('checked', true);
	    
	    <%//} else if (holidayInti.equals("SMS")) {%>
	    
	    //$('input[name=holidayIntimtnSMS]').prop('checked', true);
	    
	    <%//} else if (holidayInti.equals("Email")) {%>
	    
	    //$('input[name=holidayIntimtnEmail]').prop('checked', true);
	    
	    <%//}%>
	    
	    <%//For Review form
			if (reviewForm.equals("both")) {%>
	    
	    $('input[name=smsReviewForm]').prop('checked', true);
	    $('input[name=emailReviewForm]').prop('checked', true);
	    
	    <%} else if (reviewForm.equals("SMS")) {%>
	    
	    $('input[name=smsReviewForm]').prop('checked', true);
	    
	    <%} else if (reviewForm.equals("Email")) {%>
	    
	    $('input[name=emailReviewForm]').prop('checked', true);
	    
	    <%}%>
	    
	    if( $('input[name=smsReviewForm]').is(':checked') || $('input[name=emailReviewForm]').is(':checked')){
	    	$("#reviewTRID").show();
	    }
    
    });
    
    </script>

<!-- Ends -->



<!-- Show workflow modal -->

<script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		$('#workflowAID').click(function() {
    			event.preventDefault();
    			
    			var clinicName = $('#clinicNameID').val();
    			
    			$('#stepID').focus();
    			
    			//Checking whether clinic name is added, if not, give alert msg else proceed
    			if(clinicName == ""){
    				
    				alert('Please add clinic name first.');
    				
    			}else{
    				
					$('#workflowClinicID').val(clinicName);
        			
        			$('#workflowModalID').modal('show');
    				
    				if(rowCheckCounter != 1){
        				
        				$('#workflowTblID tr:gt(0)').remove();        				
        				
        				var clinicSelectTag = "<select name='' id='prevClinicListID' class='form-control' style='width:50%;' onchange=getClinicWorkflow(prevClinicListID.value);''>";
        				clinicSelectTag += "<option value='000'>Select Clinic</option>";
        				
        				//Providing previously added clinic list, so that if
        				//user wants to use same workflow as previous clinic,
        				//he/she can do that by selecting clinic from dropdown
        				var clinicNames = $("input[name=practiceClinicNameEdit]").serialize();
        				
        				var clinicNameArray = "";
        				
        				//Checking whether clinic name contains only one value, if so,
        				//display that value into drop down else display all the available
        				//clinic names
        				if(clinicNames.length > 1){
        					
        					clinicNameArray = clinicNames.split("&");
        					
        				}else{
        					
        					clinicNameArray = clinicNames;
        					
        				}
        				
        				for(var i = 0; i < clinicNameArray.length; i++){
        					
        					//Splitting by = in order to get only Clinic name
        					var nameArray = clinicNameArray[i].split("=");
        					
        					var value1 = decodeURIComponent(nameArray[1]);
        					
        					if(value1.includes("+")){
        						value1 = value1.replace(/\+/g," ");
        					}
        					
        					clinicSelectTag += "<option value='"+value1+"'>"+value1+"</option>";
        					
        				}
        				
        				clinicSelectTag += "</select>";
        				
        				$('#clinicListID').html(clinicSelectTag);
        				$('#clinicSelectID').show();
        				
        				rowCheckCounter = 1;
        			}
    				
    			}
    			
    		});
    		
        });
    </script>

<!-- Ends -->


<!-- function to get clinic workflow -->

<script type="text/javascript">
	
		function getClinicWorkflow(clinicName){
			
			if(clinicName.includes(",")){
				clinicName = clinicName.replace(","," ");
			}
			
			console.log(clinicName);
			
			if(clinicName == "000"){
				
				$('#workflowShowDivID').hide();
				$('#workflowDivID').show();
				$('#workflowValID').val("");
				
			}else{
				
				$('#workflowShowDivID').show();
				
				var searchString = clinicName + "=";
				
				//retrieving values of all workflow text boxes
				var workflowVals = $('input[name=workflowDummy]').serialize();
				
				workflowVals = decodeURIComponent(workflowVals);
				
				//replacing all & with , in order to get all workflow string
				workflowVals = workflowVals.replace(/&/g,'|');
				
				console.log(workflowVals);
				
				if(workflowVals.includes("+")){
					
					var stringToReplace = "\\+";
	    			var re = new RegExp(stringToReplace, 'g');
	    			workflowVals = workflowVals.replace(re,' ');
					
				}
				
				console.log(workflowVals);
				
				workflowVals = workflowVals.replace(/workflowDummy=/g,'')
				
				console.log(workflowVals);
				
				var newVal = workflowVals.split(",");
				
				console.log(newVal);
				
				var newWorkflowVal = "";
				
				var finalVal = "";
				
				if(workflowVals.includes("|")){
					
					var newWrkflwArray = workflowVals.split("|");
					
					for(var i = 0; i < newWrkflwArray.length; i++){
						
						newVal = newWrkflwArray[i].split(",");
						
						//iterating over workflowVals string and checking
						//whether string contain searchString pattern, if 
						//yes, setting those values into workflow input text
						for(var j = 0; j < newVal.length; j++){
							
							if(newVal[j].includes(searchString)){
								
								var newArray = newVal[j].split("=");
									
								newWorkflowVal += "," + newArray[1];
								
								finalVal += "," + newVal[j];
								
								if(finalVal.startsWith(",")){
									finalVal = finalVal.substr(1);
								}
								
							}
							
						}
						
					}
					
				}else{
					
					//iterating over workflowVals string and checking
					//whether string contain searchString pattern, if 
					//yes, setting those values into workflow input text
					for(var i = 0; i < newVal.length; i++){
						
						if(newVal[i].includes(searchString)){
							
							var newArray = newVal[i].split("=");
								
							newWorkflowVal += "," + newArray[1];
							
							finalVal += "," + newVal[i];
							
							if(finalVal.startsWith(",")){
								finalVal = finalVal.substr(1);
							}
							
						}
						
					}
					
				}
				
				if(newWorkflowVal.startsWith(",")){
					newWorkflowVal = newWorkflowVal.substr(1);
				}
				
				console.log(newWorkflowVal);
				
				if(newWorkflowVal.includes(",")){
					
					var workflowArray = newWorkflowVal.split(",");
					
					var tag = "<table border='1' style='text-align:center; width:100%'>";
					var srNo = 1;
					
					tag += "<tr style='font-size:14px;'>"+
					"<td style='padding:5px;width:8%;font-weight:bold;'>Sr.No.</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>Step Sequence</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>User Role</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>Statuse</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>OPD Form</td>"+
		    		"</tr>";
					
					for(var i = 0; i < workflowArray.length; i++){
						
						var workflowArrayq1 = workflowArray[i].split("$");
						
						tag += "<tr style='font-size:14px;'>"+
						"<td style='padding:5px;width:8%;'>"+srNo+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[0]+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[1]+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[2]+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[3]+"</td>"+
			    		"</tr>";
			    		
						srNo++;
						
					}
					
					tag += "</table>";
					
					$('#workflowShowDivID').html(tag);
					
				}else{
					
					var tag = "<table border='1' style='text-align:center; width:100%'>";
					var srNo = 1;
					
					var workflowArray = newWorkflowVal.split("$");
					
					tag += "<tr style='font-size:14px;'>"+
					"<td style='padding:5px;width:8%;font-weight:bold;'>Sr.No.</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>Step Sequence</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>User Role</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>Status</td>"+
		    		"<td style='padding:5px;width:23%;font-weight:bold;'>OPD Form</td>"+
		    		"</tr>";
					
					tag += "<tr style='font-size:14px;'>"+
					"<td style='padding:5px;width:8%;'>"+srNo+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArray[0]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArray[1]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArray[2]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArray[3]+"</td>"+
		    		"</tr>";
		    		
					tag += "</table>";
					
					$('#workflowShowDivID').html(tag);
					
					
				}
				
				//Checking whether clinic name stored in workflow modal
				//and cliniName recieved in this function is same or not
				//if not then replace clinic name stroed in workflow into
				//workflowVals varaible
				var newClinicName = $('#workflowClinicID').val();
				
				if(newClinicName.includes(",")){
					newClinicName = newClinicName.replace(","," ");
				}
				
				console.log(newClinicName != clinicName);
				
				console.log('New Name: '+newClinicName);
				console.log('old name: '+clinicName);
				
				if(newClinicName != clinicName){
					
					var re = new RegExp(clinicName, 'g');
					finalVal = finalVal.replace(re,newClinicName);
					
				}
				
				console.log(finalVal);
				
				$('#workflowValID').val(finalVal);
				
				$('#workflowDivID').hide();
				
			}
		
		}
	
	</script>

<!-- Ends -->


<!-- Function to display added workflow details -->

<script type="text/javascript">
		
		function viewWorkflowDetails(workflow){
			
			//Splitting workflow string by + and assigning values
			var workflowArray = workflow.split("$")
			
			if(workflow.startsWith(",")){
				workflow = workflow.substr(1);
			}
			
			//Show view workflow modal
			$('#workflowDetailModalID').modal('show');
			
			if(workflow.includes(",")){
				
				var workflowArray = workflow.split(",");
				
				var tag = "<table border='1' style='text-align:center; width:100%'>";
				var srNo = 1;
				
				tag += "<tr style='font-size:14px;'>"+
				"<td style='padding:5px;width:8%;font-weight:bold;'>Sr.No.</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>Step Sequence</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>User Role</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>Status</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>OPD Form</td>"+
	    		"</tr>";
				
				for(var i = 0; i < workflowArray.length; i++){
					
					var workflowArrayq1 = workflowArray[i].split("$");
					
					tag += "<tr style='font-size:14px;'>"+
					"<td style='padding:5px;width:8%;'>"+srNo+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[0]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[1]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[2]+"</td>"+
		    		"<td style='padding:5px;width:23%;'>"+workflowArrayq1[3]+"</td>"+
		    		"</tr>";
		    		
					srNo++;
					
				}
				
				tag += "</table>";
				
				$('#workflowViewTblID').html(tag);
				
			}else{
				
				var tag = "<table border='1' style='text-align:center; width:100%'>";
				var srNo = 1;
				
				var workflowArray = workflow.split("$");
				
				tag += "<tr style='font-size:14px;'>"+
				"<td style='padding:5px;width:8%;font-weight:bold;'>Sr.No.</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>Step Sequence</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>User Role</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>Status</td>"+
	    		"<td style='padding:5px;width:23%;font-weight:bold;'>OPD Form</td>"+
	    		"</tr>";
				
				tag += "<tr style='font-size:14px;'>"+
				"<td style='padding:5px;width:8%;'>"+srNo+"</td>"+
	    		"<td style='padding:5px;width:23%;'>"+workflowArray[0]+"</td>"+
	    		"<td style='padding:5px;width:23%;'>"+workflowArray[1]+"</td>"+
	    		"<td style='padding:5px;width:23%;'>"+workflowArray[2]+"</td>"+
	    		"<td style='padding:5px;width:23%;'>"+workflowArray[3]+"</td>"+
	    		"</tr>";
	    		
				tag += "</table>";
				
				$('#workflowViewTblID').html(tag);
				
			}
			
		}
	
	</script>

<!-- Ends -->


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

	RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();
	
	ClinicDAOInf clinicDaoInf = new ClinicDAOImpl();
	
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
	//int registerCheck = registrationDAOinf.checkOpenLeaveRegister(form.getPracticeID());
%>


<!-- For session timeout -->
<%
	ConfigurationUtil configurationUtil = new ConfigurationUtil();

	String sessionTimeoutString = configurationUtil.getSessionTimeOut();

	if (sessionTimeoutString == null || sessionTimeoutString == "") {
		sessionTimeoutString = "30";
	} else if(sessionTimeoutString.isEmpty()){
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


<!-- Retrieving OPD Form list -->

<%

	List<String> OPDJSPList = registrationDAOinf.retrieveOPDPageList();
%>

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


<!-- Add New Row function for Component -->
    
    <script type="text/javascript">
    	var counter = 1;
    	
    	var rowCheckCounter = 1;
    	
		function addNewRow() {

			var dummyText = "dummySettingTextID" +counter;
			var trID = "trID" + counter;
			var tdID = "tdID" + counter;
			var delImgTDID = "delImgTDID" + counter;
			var trTag = "<tr id='"+trID+"'>"+
						"<td style='width: 25%;'><input type ='text' class='form-control' name='practiceClinicName' id='cNameID"+counter+"'></td>"+
						"<td style='width: 11%;'><input type ='text' class='form-control' name='practiceClinicSuffix' id='cSuffixID"+counter+"'></td>"+
						"<td style='width: 20%;'><input type ='text' class='form-control' name='clinicPhoneNoArray' id='cPhoneID"+counter+"'></td>"+
						"<td style='width: 10%;'><input class='form-control' name='' readonly='readonly' value='Active' id='statuID' placeholder='Status' type='text'></td>"+
						
						"<td id='aSettingID' style='text-align: center; font-weight: bold;'><a href='javascript:addSetting(\"" + dummyText + "\",\"cNameID" + counter + "\",\"cSuffixID" + counter + "\",\"cPhoneID" + counter + "\");'>Add Setting</a><input type='hidden' id='"+dummyText+"' name='dummySettingTextArr'></td>"+
						"<td style='width: 20%;'></td>"+
						"<td id="+delImgTDID+"  style='text-align: center;''><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTR(\""
						+ trID + "\",\"" + counter + "\");'/></td>" + "</tr>";
						
			$(trTag).insertBefore($('#compTRID'));
		
			counter++;

		}
		
    	//Function to remove TR
    	function removeTR(trID, count){
        	if(confirm("Are you sure you want to remove this row?")){

            	$("#"+trID+"").remove();

            	}
    	}
    	
    </script>
    
    <!-- Ends -->

<!-- Add New MD Row function for Component -->

<script type="text/javascript">
	var counter1 = 1;

	var rowCheckCounter1 = 1;
	
	function addNewMDRow() {
		 
		var dummyText = "dummySettingTextID" +counter1;
		var trID = "trID" + counter1;
		var tdID = "tdID" + counter1;
		var delImgTDID = "delImgTDID" + counter1;
		var trTag = "<tr id='"+trID+"'>"+  
					"<td style='width: 25%;'><input type='text' name='mdNameArr' class='form-control' placeholder='Pathalogy Lab MD Name' ></td>"+
					"<td style='width: 25%;'><input type='text' name='mdQualificationArr' class='form-control' placeholder='Pathalogy Lab MD Qualification' ></td>"+
					"<td style='width: 22%;' ><input type='file' name='mdSignatureImage' class='form-control' onchange='readURL(this)'accept='Image/*'></td>"+
					"<td style='width: 11%;'><input type='text' class='datepickerClass form-control' id='mdStartDateID' name ='mdStartDateArr' placeholder='Start Date' onclick='showDate();' ></td>"+
					"<td style='width: 11%;'><input type='text' class='datepickerClass form-control' id='mdEndDateID' name ='mdEndDateArr' placeholder='End Date' onclick='showDate();'></td>"+
					"<td id="+delImgTDID+" style='width: 20%; text-align: center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeMDTR(\""
					+ trID + "\",\"" + counter1 + "\");'/></td>" + "</tr>";
					
		$(trTag).insertBefore($('#mdDetailsTRID'));
		
		counter1++;

	}
	
	function showDate() {
		$('.datepickerClass').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
          
     }
	
	//Function to remove TR
	function removeMDTR(trID, count) {
		if (confirm("Are you sure you want to remove this row?")) {

			$("#" + trID + "").remove();

		}
	}
	
</script>

<!-- Ends -->


<!-- Verify practice name already exists -->

<script type="text/javascript" charset="UTF-8">
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function verifyPracticeExists(practiceName) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				for ( var i = 0; i < array.Release.length; i++) {
					var check = array.Release[i].Practice;

					if(check == "non-exist"){

						document.getElementById("practiceCheckID").innerHTML = "<img src='images/correct.png' alt='Practice does not exist' title='Practice does not exist' style='height:24px;margin-top: 5px;'>";
						
						$("#practiceSubmitID").attr('disabled',false);
						
					}else{

						document.getElementById("practiceCheckID").innerHTML = "<img src='images/wrong.png' alt='Practice already exists' title='Practice already exists' style='height:24px;margin-top: 5px;'>";
						
						$("#practiceSubmitID").attr('disabled',true);
						
					}
				}
			}
		};
		xmlhttp.open("GET", "VerifyPracticeExists?practiceName="
				+ practiceName, true);
		xmlhttp.send();
	}
	</script>

<!-- Ends -->


<!-- Add Workflow -->

<script type="text/javascript">
    
    	var workflowCheckCounter = 1;
    
function addWorkflow(stepSeq, userRole, apptStatus, clinicName, OPDFormName){
    		
    		if(stepSeq == "" || userRole == "-1" || apptStatus == "" || OPDFormName == "-1"){
    			
    			if(stepSeq == ""){
    				alert('Please insert value for Step Sequence');
    			}
    			
    			if(userRole == "-1"){
    				alert('Please select value for User Role');
    			}
    			
				if(apptStatus == ""){
					alert('Please insert value for Appt. Status');
    			}
				
				if(OPDFormName == "-1"){
    				alert('Please select value for OPD Form');
    			}
    			
    		}else{
    			
    			if(clinicName.includes(",")){
    				clinicName = clinicName.replace(","," ");
    			}
    			
    			var valueToAppend = "";
        		
        		if(workflowCheckCounter != 1){
        			
        			valueToAppend = "," + clinicName + "=" + stepSeq + "$" + userRole + "$" + apptStatus + "$" + OPDFormName;
        			
        			workflowCheckCounter++;
        			
        		}else{
        			
        			valueToAppend = clinicName + "=" +stepSeq + "$" + userRole + "$" + apptStatus + "$" + OPDFormName;
        			
        			workflowCheckCounter++;
        		}
        		
        		//appending values to workflow text box
        		$('#workflowValID').val($('#workflowValID').val()+valueToAppend);
        		
        		var trID = "TRID"+workflowCheckCounter;
        		
        		var nextTRTag = "<tr id='"+trID+"' style='font-size:14px;'>"+
        		"<td style='padding:5px;'><input type='text' value='"+stepSeq+"' name='' readonly='readonly' class='form-control'></td>"+
        		"<td style='padding:5px;'><input type='text' value='"+userRole+"' name='' readonly='readonly' class='form-control'></td>"+
        		"<td style='padding:5px;'><input type='text' value='"+apptStatus+"' name='' readonly='readonly' class='form-control'></td>"+
        		"<td style='padding:5px;'><input type='text' value='"+OPDFormName+"' name='' readonly='readonly' class='form-control'></td>"+
        		"<td><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeWorkflowRow(\"" + valueToAppend +"\",\""+trID+"\");'/></td>"+
        		"</tr>";
        		
        		$(nextTRTag).insertAfter($('#workflowTRID'));
        		$('#workflowAID').html("Workflow added");
        		
        		$('#stepID').val("");
        		$('#userTypeID').val("-1");
        		$('#opdFormID').val("-1");
        		$('#apptStatusID').val("");
    			
    		}

    	}
    	
    	//function to remove row
    	function removeWorkflowRow(valueToBeRemoved, trID){
    		
    		var workflowVal = $('#workflowValID').val();
    		var newValue = workflowVal.replace(valueToBeRemoved,'');
    		
    		//updating value of bagNo field
    		$('#workflowValID').val(newValue);
    		
    		//removing TR
    		$("#"+trID+"").remove();
    		
    	}
    
    </script>

<!-- Ends -->


<!-- Retrieve workflow details based on clinic ID -->

<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function getWorkflowDetails(clinicID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var tag = "<table border='1' style='text-align:center; width:100%'>";
					var srNo = 1;
					
					tag += "<tr style='font-size:14px;'>"+
					"<td style+'padding:5px;width:8%;'>Sr.No.</td>"+
		    		"<td style='padding:5px;width:23;'>Step Sequence</td>"+
		    		"<td style='padding:5px;width:23%;'>User Role</td>"+
		    		"<td style='padding:5px;width:23%;'>Appt. Status</td>"+
		    		"<td style='padding:5px;width:23%;'>OPD Form</td>"+
		    		"</tr>";
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						tag += "<tr style='font-size:14px;'>"+
						"<td style+'padding:5px;width:8%;'>"+srNo+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+array.Release[i].stepSeq+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+array.Release[i].role+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+array.Release[i].status+"</td>"+
			    		"<td style='padding:5px;width:23%;'>"+array.Release[i].OPDForm+"</td>"+
			    		"</tr>";
			    		
						srNo++;
						
					}
					tag += "</table>";
					
					$('#workflowViewTblID').html(tag);
					$('#workflowDetailModalID').modal('show');
				}
			};
			xmlhttp.open("GET", "GetWorkflowDetails?clinicID="
					+ clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->


<!-- Deleting row from Clinic list -->


	<script type="text/javascript">
    
    	function deleteRow1(clinicID){
    		if(confirm("Are you sure you want to delete this row?")){
    			deleteRow(clinicID);
    		}
    	}
    	

    	function addNewPlan(practiceID){
    		
    	
    			if(confirm("Are you sure to Add New Plan?  \nBy cliking on 'OK' Your existing plan will expire. ")){
    					disablePlan(practiceID);
    					$("#planTRID").show();
    			}else{
    				$("#planTRID").hide();
    			}
   
    	}
    
    	function CheckEmptyField(){
    		
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
    			
    			document.forms["addPract"].action = "EditPractice";
    			document.forms["addPract"].submit();
    			return true;
   		}
    </script>


<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteRow(clinicID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						
					}
					
					if(check == 1){
						
						var clinicTRID = "clinicTRID"+clinicID;
						
						$("#"+clinicTRID+"").remove();
						
					}else{
						alert('Failed to delete row. Please check server logs for more details.')
					}

				}
			};
			xmlhttp.open("GET", "DeleteClinicRow?clinicID="
					+ clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->

<!-- Delete existing plan -->
<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function disablePlan(practiceID) {
			$('#planTableTRID tr:gt(1)').remove();
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check = 0;
					var id = 0;
					var visit = "";
					var startDate = "";
					var endDate = "";
					var status = "";
					var trID = 0;
					var trTag="";
			
					
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						console.log("Check::"+check);
					}

					for ( var i = 0; i < array.Release1.length; i++) {
						
						id = array.Release1[i].id;
						visit = array.Release1[i].noOfVisit;
						startDate = array.Release1[i].startDate;
						endDate = array.Release1[i].endDate;
						status = array.Release1[i].status;
						
						console.log(" visit:"+visit+"  start date:"+startDate+" end date:"+endDate+" status:"+status);
						
						trTag += "<tr id='planTRID"+id+"'>"+
									"<td style='text-align: center;'>"+visit+"</td>"+
									"<td style='text-align: center;'>"+startDate+"</td>"+
									"<td style='text-align: center;'>"+endDate+"</td>"+
									"<td style='text-align: center;'>"+status+"</td>"+ "</tr>";
							
				    }
					$(trTag).insertAfter($('#planTRID'));
					
					if(check == 1){
						
						document.getElementById("successMSG").innerHTML = "Existing plan Inactivated successfully. Please add new plan details.";
						
						setTimeout(function(){
							$("#successErrorMsgID").hide(1000);
						},2000);
						
				
					}else{
						
						document.getElementById("exceptionMSG").innerHTML = "Failed to disable row. Please check server logs for more details.";
						
						setTimeout(function(){
							$("#successErrorMsgID").hide(1000);
						},2000);
					}

				}
			};
			xmlhttp.open("GET", "DisablePlanRow?practiceID="+practiceID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->

<!-- Add Calendar setting -->
    
    <script type="text/javascript">
    
	    function addCalendarSetting(clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays, patientForm,hiddenInput){
			
			/* if(logo == ""){
				alert("Please enter logo file path.");
			}else if(patientForm == "-1"){
				alert("Please select patient form.");
			}else if((!($("#a4sizeID").is(":checked"))) && (!($("#a5sizeID").is(":checked")))){
				alert("Please select page size.");
			}else{ */
				addCalendarSetting1(clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays, patientForm,hiddenInput);
			
		}
	
		function addCalendarSetting1(clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays,patientForm,hiddenInput){
			
			var workdaysVals = "";
			
			$('#workdaysID option:selected').each(function() {
				workdaysVals += $(this).val()+",";
			});
			
			workdaysVals = workdaysVals.replace(/,\s*$/, "");
			
			//Getting value of Pagesize
			var pageSize = $('input[name=size]:checked').val();
			
			if(pageSize == "undefined"){
				pageSize = "";
			}
			
			var patientFormName = $("#patientFormID option[value='"+patientForm+"']").text();
			
			//var reportFormName = $("#reportFormID option[value="+reportForm+"]").text();
			
			var calendarSettingVal = clinicStartTime + "$" + clinicEndTime + "$" + breakStart1 + "$" + breakEnd1 + "$" + breakStart2 + "$" + breakEnd2 + "$" + workdaysVals +"$"+ pageSize + "$" + patientForm + "$" +  patientFormName;
			
			$("#"+hiddenInput).val(calendarSettingVal);
			
			
			$("#clinicStartID").val("");
			$("#clinicEndID").val("");
			$("#break1StartID").val("");
			$("#break1EndID").val("");
			$("#break2StartID").val("");
			$("#break2EndID").val("");
			$("#workdaysID").val("-1");
			//$("#logoID").val("");
			//$("#letterHeadImageID").val("");
			$('#a4sizeID').prop('checked', false);
			$('#a5sizeID').prop('checked', false);
			$("#patientFormID").val("-1");
			//$("#reportFormID").val("-1");
			
			$("#calendarSettingModal").modal('hide');
			
		}
		
		function viewCalendarSetting(settingString){
			
			var stringArray = settingString.split("$");
			
			$("#cStartID").html(stringArray[0]);
			$("#cEndID").html(stringArray[1]);
			$("#b1StartID").html(stringArray[2]);
			$("#b1EndID").html(stringArray[3]);
			$("#bStart2ID").html(stringArray[4]);
			$("#bEnd2ID").html(stringArray[5]);
			$("#wID").html(stringArray[6]);
			//$("#logoFileID").html(stringArray[7]);
			//$("#lHeadImageID").html(stringArray[9]);
			$("#pageSizeID").html(stringArray[8]);
			$("#pFormID").html(stringArray[10]);
			//$("#rFormID").html(stringArray[21]);
			
			$("#viewSettingModal").modal('show');
			
		}
    	
    	function addSetting(dummyText,nameID,suffixID,phoneID){
    		
    		var clinicName = $("#"+nameID).val();

    		var clinicSuffix = $("#"+suffixID).val();
    		
    		var clinicPhone = $("#"+phoneID).val();

    		if (clinicName == "") {
    			alert('Please enter clinic name.');
    		} else if (clinicSuffix == "") {
    			alert('Please enter clinic suffix.');
    		} else if (clinicPhone == "") {
    			alert('Please enter clinic phone.');
    		} else{
    			
    			$("#inputTextID").val(dummyText);
    			$("#calendarSettingModal").modal('show');
    		}
    		
    	}
    
    </script>
    
    <!-- Ends -->


<!-- Edit Upload Image -->

<%-- <script type="text/javascript">

	/* 	function editUploadImage(clinicID, logo, letterHeadImage){
			
		} */
		
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function editUploadImage(clinicID, logo, letterHeadImage) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						var msg = array.Release[i].msg;
						var check = array.Release[i].check;
						
						if(check == "1"){  //If values edited successfully then show alert modal 
							
							$("#editUploadImageModal").modal('hide');
							
							$("#editUploadAlertModal").modal('show');
							
							
						}else{//If values failed to edit then show alert modal 1
							
							$("#editUploadImageModal").modal('hide');
							
							$("#editUploadAlertModal1").modal('show');
							
						}
						
					}

				}
			};
			xmlhttp.open("GET", "EditClinicUploadImage?clinicID="+clinicID+"&logoFile="+logo+"&letterHeadFile="+letterHeadImage, true);
			
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script> --%>
<!-- Ends -->


<!-- Edit Calendar setting -->

<script type="text/javascript">

		function editCalendarSetting(clinicID, clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays, patientForm){
			
			/* if(logo == ""){
				alert("Please enter logo file path.");
			}else if(patientForm == "-1"){
				alert("Please select patient form.");
			}else if((!($("#a4pageSizeID").is(":checked"))) && (!($("#a5pageSizeID").is(":checked")))){
				alert("Please select page size.");
			}else{ */
				editCalendarSetting1(clinicID, clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays, patientForm);
			
		}

    
    	function editCalendarSetting1(clinicID, clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdays,  patientForm){
    		
    		patientForm = patientForm.replace("&", "$");
    		
    		var workdaysVals = "";
			
			$('#wID1 option:selected').each(function() {
				workdaysVals += $(this).val()+",";
			});
			
			workdaysVals = workdaysVals.replace(/,\s*$/, "");
			
			
			//Getting value of Pagesize
			var pageSize = $('input[name=pgSize]:checked').val();
			
			if(pageSize == "undefined"){
				pageSize = "";
			}
			
			editClinicCalendar(clinicID, clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdaysVals, patientForm, pageSize);

    	}
    
    </script>

<!-- Ends -->



<!-- Edit calendar Setting -->

<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function editClinicCalendar(clinicID, clinicStartTime, clinicEndTime, breakStart1, breakEnd1, breakStart2, breakEnd2, workdaysVals,patientForm, pageSize) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						var msg = array.Release[i].msg;
						var check = array.Release[i].check;
						
						if(check == "1"){
							
							$("#editCalendarSettingModal").modal('hide');
							
							$("#editCalendarAlertModal").modal('show');
							
							
						}else{
							
							$("#editCalendarSettingModal").modal('hide');
							
							$("#editCalendarAlertModal1").modal('show');
							
						}
						
					}

				}
			};
			xmlhttp.open("GET", "EditClinicCalendar?clinicID="
					+ clinicID+"&clinicStartName="+clinicStartTime+"&clinicEndName="+clinicEndTime+"&breakStart1="
					+breakStart1+"&breakEnd1="+breakEnd1+"&breakStart2="+breakStart2+"&breakEnd2="+breakEnd2+"&workdaysName="
					+workdaysVals+"&pageSize="+pageSize+"&patientForm="+patientForm, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->


<!-- View Setting -->

<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function viewSetting(clinicID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var selectTag = "<select class='form-control' id='wID1' multiple='multiple'>"
								   +"<option value='-1'>Select Workdays</option>";
	
					for ( var i = 0; i < array.Release.length; i++) {
						
						$("#clinicID").val(clinicID);
						
						if(array.Release[i].letterHeadImage == null || array.Release[i].letterHeadImage == "" 
								|| array.Release[i].letterHeadImage == "null"){
							$("#lHImageID").val("");
						}else{
							$("#lHImageID").val(array.Release[i].letterHeadImage);
						} 
						
						if(array.Release[i].pageSize == null || array.Release[i].pageSize == "" 
								|| array.Release[i].pageSize == "null"){
							$("#a4pageSizeID").prop("checked", false);
							$("#a5pageSizeID").prop("checked", false);
						}else{
							if(array.Release[i].pageSize == "A4"){
								$("#a4pageSizeID").prop("checked", true);
							}else if(array.Release[i].pageSize == "A5"){
								$("#a5pageSizeID").prop("checked", true);
							}else{
								$("#a4pageSizeID").prop("checked", false);
								$("#a5pageSizeID").prop("checked", false);
							}
						}
						
						if(array.Release[i].logo == null || array.Release[i].logo == "" 
								|| array.Release[i].logo == "null"){
							$("#logoPathID").val("");
						}else{
							$("#logoPathID").val(array.Release[i].logo);
						}
						
						if(array.Release[i].patientForm == null || array.Release[i].patientForm == "" 
								|| array.Release[i].patientForm == "null"){
							$("#patFormID").val("-1");
						}else{
							console.log("patient form ::"+array.Release[i].patientForm)
						
							$("#patFormID").val(array.Release[i].patientForm);
						}
						
						/* if(array.Release[i].reportForm == null || array.Release[i].reportForm == "" 
								|| array.Release[i].reportForm == "null"){
							$("#repFormID").val("-1");
						}else{
							$("#repFormID").val(array.Release[i].reportForm);
						} */
						
						$("#editClinicStartID").val(array.Release[i].clinicStart);
						
						$("#editClinicEndID").val(array.Release[i].clinicEnd);
						
						$("#editBreak1StartID").val(array.Release[i].beakStart1);
						
						$("#edtBreak1EndID").val(array.Release[i].breakEnd1);
						
						$("#editBreak2StartID").val(array.Release[i].breakStart2);
						
						$("#editBreak2EndID").val(array.Release[i].breakEnd2);
						
						if(array.Release[i].workDays == null || array.Release[i].workDays == "" 
								|| array.Release[i].workDays == "null"){
							
							selectTag += "<option value='Mon'>Mon</option>"
										+"<option value='Tue'>Tue</option>"
										+"<option value='Wed'>Wed</option>"
										+"<option value='Thu'>Thu</option>"
										+"<option value='Fri'>Fri</option>"
										+"<option value='Sat'>Sat</option>"
										+"<option value='Sun'>Sun</option>";
							selectTag += "</select>";
							
							$("#workdaysDivID").html(selectTag);
						}else{
							
							var workdayArr = array.Release[i].workDays.split(",");
							
							var array1 = ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"];
							
							for(var j = 0;j < array1.length ; j++){
								
								if(array1.indexOf(workdayArr[j]) === -1){
									
									selectTag += "<option value='"+array1[j]+"'>"+array1[j]+"</option>";
									
								}else{
									
									selectTag += "<option value='"+array1[j]+"' selected='selected'>"+array1[j]+"</option>";
									
								}
								
							}
							selectTag += "</select>";
							
							$("#workdaysDivID").html(selectTag);
						}
						
					}
					
					$("#editCalendarSettingModal").modal('show');

				}
			};
			xmlhttp.open("GET", "RetrieveClinicCalendar?clinicID=" + clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->


<!-- Upload Image -->

<script type="text/javascript">
    	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function uploadImg(clinicID) {
		
			xmlhttp.onreadystatechange = function() {
			
				
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					const reader = new FileReader();
					for ( var i = 0; i < array.Release.length; i++) {
						
						$("#clinicID").val(clinicID);
						
						if(array.Release[i].letterHeadImage == null || array.Release[i].letterHeadImage == "" || array.Release[i].letterHeadImage == "null"){
							
						console.log("Inside letterhead null");
							$("#lHImageID").val("");  //Id of input type=text.
							$("#letterDivID").show();
							$("#letterDID").hide();
						}else{
							console.log("Inside letterhead");
							var aaa = array.Release[i].letterHeadImage;
							var bits1 = aaa.split("/");
							var letterheadName = bits1[bits1.length-1];
							
							$("#lHImageID").val(letterheadName);  //Id of input type=text.
							$("#letterDivID").hide();
							$("#letterDID").show();
						} 
						
												
						if(array.Release[i].logo == null || array.Release[i].logo == "" || array.Release[i].logo == "null"){
							console.log("Inside logo null");
							$("#logoPathID").val(""); //Id of input type=text.
							$("#logoDivID").show();
							$("#logoDID").hide();
							
						}else{
							
							console.log("Inside logo");
							
							var aaa = array.Release[i].logo;
							var bits1 = aaa.split("/");
							var logoName = bits1[bits1.length-1];
						
							$("#logoPathID").val(logoName); //Id of input type=text.
							$("#logoDivID").hide();
							$("#logoDID").show();
						}
						
					}
					$("#UploadclinicID").val(clinicID);
					$("#editUploadImageModal").modal('show');

				}
			};
			xmlhttp.open("GET", "RetrieveUploadImage?clinicID="+clinicID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>

<!-- Ends -->

<script type="text/javascript">
    
    	function checkReviewForm(input, val){
    		
    		if($("#smsReviewFormID").is(":checked") && $("#emailReviewFormID").is(":checked")){
    			$("#reviewTRID").show(1000);
    		}else if($("#smsReviewFormID").is(":checked")){
    			$("#reviewTRID").show(1000);
    		}else if($("#emailReviewFormID").is(":checked")){
    			$("#reviewTRID").show(1000);
    		}else{
    			$("#reviewFormID").val("");
    			$("#reviewTRID").hide(1000);
    		}
    		
    	}
    
    </script>
    
    
    <script type="text/javascript">
    	document.addEventListener('DOMContentLoaded', function() {
        	
    		//setInterval(function(){$('#weekCalendar').fullCalendar('refetchEvents')}, 30000);
    		setInterval(function(){$('body').css('padding-right','0px')}, 1000);
    		
    		$('.clinic_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.clinic_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.break1_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.break1_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.break2_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.break2_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_clinic_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_clinic_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_break1_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_break1_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_break2_start').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
    		$('.edit_break2_end').datetimepicker({
    	        language:  'en',
    	        weekStart: 1,
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 1,
    			minView: 0,
    			maxView: 1,
    			forceParse: 0
    	    });
    		
        });
    </script>

</head>

<body class="nav-md">
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
	<!-- Edit calendar alert message modal -->

	<div id="editCalendarAlertModal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body">

					<center>
						<h4 style="color: green;" id="">Calendar details updated successfully.</h4>
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
	
	<div id="editCalendarAlertModal1" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body">

					<center>
						<h4 style="color: red;" id="">Failed to udpate calendar details. Please check server logs for more details.</h4>
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


<!-- Edit Upload Image alert message modal -->

	<div id="editUploadAlertModal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body">

					<center>
						<h4 style="color: green;" id="">Image updated successfully.</h4>
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
	
	<div id="editUploadAlertModal1" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body">

					<center>
						<h4 style="color: red;" id="">Failed to udpate Image. Please check server logs for more details.</h4>
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
	
	 <!-- view calendar setting modal -->
  
  <div id="viewSettingModal" class="modal fade" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="myModalLabel">CALENDAR SETTING</h4>
      </div>
        <div class="modal-body">
        
        <div class="row" style="padding:10px;">
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Logo: </font><font id="logoFileID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Page Size: </font><font id="pageSizeID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Patient Form: </font><font id="pFormID"></font>
         		</div>
         	</div>
         	
         	<!-- <div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Report Form: </font><font id="rFormID"></font>
         		</div>
         	</div> -->
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Letter Head Image: </font><font id="lHeadImageID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Clinic Start: </font><font id="cStartID"></font>&nbsp;&nbsp;
         			<font style="font-weight: bold;">Clinic End: </font><font id="cEndID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Break Start1: </font><font id="b1StartID"></font>&nbsp;&nbsp;
         			<font style="font-weight: bold;">Break End1: </font><font id="b1EndID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Break Start2: </font><font id="bStart2ID"></font>&nbsp;&nbsp;
         			<font style="font-weight: bold;">Break End2: </font><font id="bEnd2ID"></font>
         		</div>
         	</div>
         	
         	<div class="row" style="margin-top: 15px;">
         		<div class="col-md-12 col-sm-12 col-xs-12">
         			<font style="font-weight: bold;">Workdays: </font><font id="wID"></font>
         		</div>
         	</div>
         	
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        </center>
        </div>

      </div>
    </div>
  </div>
  <!-- Ends -->
	
	
	<!-- Edit calendar setting modal -->

	<div id="editCalendarSettingModal" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">EDIT SETTING FOR CALENDAR</h4>
				</div>
				<div class="modal-body" align="center">
				
				<input type="hidden" id="clinicID">

					<div class="row" style="padding: 5px;">
			        	
			        	<!-- <div class="row" align="center" style="margin-top: 15px;">
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">Logo*</font>
			        			</div>
			        			<div class="col-md-3 col-sm-3 col-xs-3">
			        				<input type="text" id="logoPathID" class="form-control">
			        			</div>
			        		
			        		</div>
			        	
			        	</div> -->
			        	
			        	<div class="row" align="center" style="margin-top: 15px;">
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">Page Size*</font>
			        			</div>
			        			<div class="col-md-3 col-sm-3 col-xs-3">
			        				<input type="radio" id="a4pageSizeID" name="pgSize" value="A4">&nbsp;A4&nbsp;
			        				<input type="radio" id="a5pageSizeID" name="pgSize" value="A5">&nbsp;A5
			        			</div>
			        		
			        		</div>
			        	
			        	</div>
			        	
			        	<!-- <div class="row" align="center" style="margin-top: 15px;">
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">Letter Head Image</font>
			        			</div>
			        			<div class="col-md-3 col-sm-3 col-xs-3">
			        				<input type="text" id="lHImageID" class="form-control">
			        			</div>
			        		
			        		</div>
			        	
			        	</div> -->
			        	
			        	<div class="row" align="center" style="margin-top: 15px;">
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">Patient Form*</font>
			        			</div>
			        			<div class="col-md-3 col-sm-3 col-xs-3">
			        				<s:select list="patientJSPList" name="patientForm" id="patFormID" class="form-control" headerKey="-1" headerValue="Select Patient Form"></s:select>
			        			</div>
			        		
			        		</div>
			        	
			        	</div>
			        	
			        	<%-- <div class="row" align="center" style="margin-top: 15px;">
        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">Report Form*</font>
			        			</div>
			        			<div class="col-md-3 col-sm-3 col-xs-3">
			        				<s:select list="reportJSPList" name="" id="repFormID" class="form-control" headerKey="-1" headerValue="Select Report Form"></s:select>
			        			</div>
			        		
			        		</div>
			        	
			        	</div> --%>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Clinic Start</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
			        				<div class="input-group date edit_clinic_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
					                    <input class="form-control" size="16" id="editClinicStartID" type="text" value="" readonly>
					                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
					                </div>
			        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="cStartHHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="cStartMMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Clinic End</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
			        				<div class="input-group date edit_clinic_end col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
					                    <input class="form-control" size="16" id="editClinicEndID" type="text" value="" readonly>
					                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
					                </div>
			        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="cEndHHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="cEndMMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Break Start1</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date edit_break1_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" id="editBreak1StartID" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="b1StartHHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="b1StartMMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Break End1</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date edit_break1_end col-md-12"  data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" id="edtBreak1EndID" type="text" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="b1EndHHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="b1EndMMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Break Start2</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date edit_break2_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" id="editBreak2StartID" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="bStart2HHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="bStart2MMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Break End2</font>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date edit_break2_end col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" value="" id="editBreak2EndID" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
								<%-- <div class="col-md-2 col-sm-2 col-xs-3">
									<select id="bEnd2HHID" class="form-control">
										<option value="000">HH</option>
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-3">
									<select id="bEnd2MMID" class="form-control">
										<option value="000">MM</option>
										<option value="00">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select>
								</div> --%>

							</div>

						</div>

						<div class="row" align="center" style="margin-top: 15px;">

							<div class="col-md-12 col-sm-12 col-xs-12">

								<div class="col-md-3 col-sm-3 col-xs-6">
									<font style="font-size: 14px;">Workdays</font>
								</div>
								<div class="col-md-3 col-sm-3 col-xs-6" id="workdaysDivID">
									<select id="wID1" name="" class="form-control"
										multiple="multiple">
										<option value="-1">Select Workdays</option>
										<option value="Mon">Mon</option>
										<option value="Tue">Tue</option>
										<option value="Wed">Wed</option>
										<option value="Thu">Thu</option>
										<option value="Fri">Fri</option>
										<option value="Sat">Sat</option>
										<option value="Sun">Sun</option>
									</select>
								</div>

							</div>

						</div>

					</div>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-default" onclick=""
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary"
							onclick="editCalendarSetting(clinicID.value, editClinicStartID.value, editClinicEndID.value, editBreak1StartID.value, edtBreak1EndID.value, editBreak2StartID.value, editBreak2EndID.value, wID1.value, patFormID.value);">Edit</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->


	<!-- CAlendar setting modal -->
  
  <div id="calendarSettingModal" class="modal fade" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="myModalLabel">ADD SETTING FOR CALENDAR</h4>
      </div>
        <div class="modal-body" align="center">
        <input type="hidden" id="inputTextID">
        <div class="row" style="padding:5px;">
        	
        	<!-- <div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Logo*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-3">
        				<input type="text" id="logoID" class="form-control">
        			</div>
        		
        		</div>
        	
        	</div> -->
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Page Size*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-3">
        				<input type="radio" id="a4sizeID" name="size" value="A4">&nbsp;A4&nbsp;
        				<input type="radio" id="a5sizeID" name="size" value="A5">&nbsp;A5
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<!-- <div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Letter Head Image</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-3">
        				<input type="text" id="letterHeadImageID" class="form-control">
        			</div>
        		
        		</div>
        	
        	</div> -->
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Patient Form*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-3">
        				<s:select list="patientJSPList" name="patientForm" id="patientFormID" class="form-control" headerKey="-1" headerValue="Select Patient Form"></s:select>
        			</div>
        		
        		</div>
        	
        	</div>
        	
        	<%-- <div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Report Form*</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-3">
        				<s:select list="reportJSPList" name="" id="reportFormID" class="form-control" headerKey="-1" headerValue="Select Report Form"></s:select>
        			</div>
        		
        		</div>
        	
        	</div> --%>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Clinic Start</font>
        			</div>
        			
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date clinic_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" id="clinicStartID" type="text" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="clnicStartMMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Clinic End</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date clinic_end col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" id="clinicEndID" type="text" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="clinicEndHHID" class="form-control" >
											<option value="000">HH</option>
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="clinicEndMMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Break Start1</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date break1_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" id="break1StartID" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakStart1HHID" class="form-control" >
											<option value="000">HH</option>
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakStart1MMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Break End1</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date break1_end col-md-12"  data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" id="break1EndID" type="text" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakEnd1HHID" class="form-control" >
											<option value="000">HH</option>
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakEnd1MMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Break Start2</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date break2_start col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" id="break2StartID" value="" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakStart2HHID" class="form-control" >
											<option value="000">HH</option>
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakStart2MMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Break End2</font>
        			</div>
        			<div class="col-md-4 col-sm-4 col-xs-6">
        				<div class="input-group date break2_end col-md-12" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
		                    <input class="form-control" size="16" type="text" value="" id="break2EndID" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
		                </div>
        			</div>
        			<%-- <div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakEnd2HHID" class="form-control" >
											<option value="000">HH</option>
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>
        			</div>
        			<div class="col-md-2 col-sm-2 col-xs-3">
        				<select id="breakEnd2MMID" class="form-control" >
											<option value="000">MM</option>
											<option value="00">00</option>
											<option value="05">05</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="20">20</option>
											<option value="25">25</option>
											<option value="30">30</option>
											<option value="35">35</option>
											<option value="40">40</option>
											<option value="45">45</option>
											<option value="50">50</option>
											<option value="55">55</option>
										</select>
        			</div> --%>
        		
        		</div>
        	
        	</div>
        	
        	<div class="row" align="center" style="margin-top: 15px;">
        	
        		<div class="col-md-12 col-sm-12 col-xs-12">
        		
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<font style="font-size: 14px;">Workdays</font>
        			</div>
        			<div class="col-md-3 col-sm-3 col-xs-6">
        				<select id="workdaysID" name="" class="form-control" multiple="multiple">
					         					<option value="-1">Select Workdays</option>
					         					<option value="Mon">Mon</option>
					         					<option value="Tue">Tue</option>
					         					<option value="Wed">Wed</option>
					         					<option value="Thu">Thu</option>
					         					<option value="Fri">Fri</option>
					         					<option value="Sat">Sat</option>
					         					<option value="Sun">Sun</option>
					         				</select>
        			</div>
        		
        		</div>
        	
        	</div>
         	
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-default" onclick="" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="addCalendarSetting(clinicStartID.value, clinicEndID.value, break1StartID.value, break1EndID.value, break2StartID.value, break2EndID.value, workdaysID.value, patientFormID.value, inputTextID.value);" >Add</button>
        </center>
        </div>

      </div>
    </div>
  </div>
  
  <!-- Ends -->

<!-- Edit Upload Image modal -->

	<div id="editUploadImageModal" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
			<form action="EditClinicUploadImage" method="POST" enctype="multipart/form-data" autocomplete="off">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">UPLOAD LOGO AND LETTERHEAD IMAGE</h4>
				</div>
				<div class="modal-body" align="center">
				
				<input type="hidden" id="UploadclinicID" name="clinicID" value="<s:property value="clinicID" />">

					<div class="row" style="padding: 5px;">
			        	
			        	<div class="row" align="center" style="margin-top: 15px;">
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px; padding-left:60px;">Logo File</font>
			        			</div>
			        			<div class="col-md-6 col-sm-3 col-xs-3"  style="display:none" id="logoDivID">
			        			
			        				<input type="file" id="" name ="logoFile" value="<s:property value="logoFileDBName"/>" class="form-control" >
			        				
			        			</div>
			        			<div  style="display:none" id="logoDID">
				        			<div class="col-md-5" >
				        				<input type="text" name="logoFileDBName" id="logoPathID" class="form-control" value="<s:property value="logoFileDBName"/>" readonly="readonly" >
									</div>
									<div class="col-md-1">
										<a href="javascript:logoImgShow('logoDivID','logoDID')"><img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Upload logo" title="Upload logo"/></a>
									
									</div>
			        			</div>
			        			
			        		
			        		</div>
			        	
			        	</div> 
			        	
			        	
			        	<div class="row" align="center" style="margin-top: 15px;" >
			        	
			        		<div class="col-md-12 col-sm-12 col-xs-12">
			        		
			        			<div class="col-md-3 col-sm-3 col-xs-6">
			        				<font style="font-size: 14px;">LetterHead Image</font>
			        			</div>
			        			<div class="col-md-6 col-sm-3 col-xs-3"  style="display:none" id="letterDivID">
			        				<input type="file" id=""  name ="letterHeadFile"  value="<s:property value="letterHeadFileDBName"/>" class="form-control">
			        			</div>
			        			<div style="display:none" id="letterDID">
				        			<div class="col-md-5"  >
				        				<input type="text" name="letterHeadFileDBName" id="lHImageID" class="form-control" value="<s:property value="letterHeadFileDBName"/>" readonly="readonly">
										</div>
									<div class="col-md-1">
										<a href="javascript:logoImgShow('letterDivID','letterDID')"><img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Upload letterHead Image" title="Upload letterHead Image"/></a>
									
									</div>
			        			</div>
			        			
			        		
			        		</div>
			        	
			        	</div> 
			        	
					</div>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-default" onclick=""  data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Upload</button>
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
						<h4 style="color: black;">Leave register is not opened for this year. Kindly contact administrator.</h4>
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


	<!-- Workflow modal -->

	<div id="workflowModalID" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">ADD WORKFLOW FOR
						CLINIC</h4>
				</div>
				<div class="modal-body">

					<div class="row" style="padding: 5px;">

						<div class="col-md-12" id="clinicSelectID" style="display: none;">
							<div class="col-md-6">
								<font style="font-size: 14px; font-weight: bold;">Select
									clinic to copy workflow</font>
							</div>
							<div class="col-md-6" id="clinicListID"></div>

							<hr>
						</div>

						<div id="workflowDivID">

							<table id="workflowTblID">
								<tr id="workflowTRID">
									<td style="padding: 5px; width: 23%;"><input type="number"
										class="form-control" name="" id="stepID"
										placeholder="Step Sequence"> <input type="hidden"
										id="workflowClinicID"></td>
									<td style="padding: 5px; width: 23%;"><select
										id="userTypeID" name="" class="form-control">
											<option value="-1">Select Role</option>
											<option value="administrator">Administrator</option>
											<option value="clinician">Clinician</option>
											<option value="staff">Staff</option>
									</select></td>
									<td style="padding: 5px; width: 23%;"><input type="text"
										class="form-control" name="" id="apptStatusID"
										placeholder="Appointment Status"></td>
									<td style="padding: 5px; width: 23%;"><select
										id="opdFormID" name="" class="form-control">
											<option value="-1">Select OPD Form</option>

											<%
												for (String opdFormName : OPDJSPList) {
											%>

											<option value="<%=opdFormName%>"><%=opdFormName%></option>

											<%
												}
											%>

									</select></td>
									<td style="width: 8%;"><img src="images/addBill.png"
										style="height: 24px; cursor: pointer;"
										onclick="addWorkflow(stepID.value, userTypeID.value, apptStatusID.value, workflowClinicID.value, opdFormID.value);"
										onmouseover="this.src='images/addBill1.png'"
										onmouseout="this.src='images/addBill.png'" alt="Add Workflow"
										title="Add Workflow" /></td>
								</tr>
							</table>

						</div>

						<div id="workflowShowDivID" style="margin-top: 15px;"></div>

					</div>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-primary" onclick=""
							data-dismiss="modal">Close</button>
					</center>
				</div>

			</div>
		</div>
	</div>

	<!-- Ends -->


	<!-- Workflow details modal -->

	<div id="workflowDetailModalID" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header" align="center" style="color: black;">
					<h4 class="modal-title" id="myModalLabel">WORKFLOW DETAILS FOR
						CLINIC</h4>
				</div>
				<div class="modal-body">

					<div class="row" id="workflowViewTblID" style="padding: 5px;">

					</div>

				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
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
								class="user-profile dropdown-toggle" id ="profPicID" data-toggle="dropdown"
								aria-expanded="false"> 
 			<%	if (daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()).isEmpty()) { %> 
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
										<h3>EDIT PRACTICE</h3>
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
										
										<font style="color: green;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="successMSG"></center> </font>
    					
    									<font id="" style="color: red;font-size:16px;margin-left:40px;text-align: center;margin:0px;"><center id="exceptionMSG"></center></font>
									</div>

									<form class="form-horizontal form-label-left" novalidate
										action="EditPractice" name ="addPract" id="addPract" method="POST" style="margin-top: 20px;"
										enctype="multipart/form-data">

										<s:iterator value="practiceList" var="editPracticeForm">

											<input type="hidden" name="practiceID"
												value="<s:property value="practiceID"/>">
											<input type="hidden" name="practiceConfID"
												value="<s:property value="practiceConfID"/>">
											<input type="hidden" name="communicationID"
												value="<s:property value="communicationID"/>">

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-3 col-xs-12"
													for="Practice Name">Practice Name<span
													class="required">*</span>
												</label>
												<div class="col-md-5 col-sm-5 col-xs-11">
													<input type="text" name="practiceName" id="practiceNameID"
														required="required" readonly="readonly"
														value="<s:property value="practiceName"/>"
														onkeyup="verifyPracticeExists(practiceNameID.value);"
														placeholder="Practice Name" class="form-control">
												</div>
												<div id="practiceCheckID" class="col-md-1 col-sm-1 col-xs-1">

												</div>
											</div>

											<div class="item form-group">
												<label class="control-label col-md-3 col-sm-3 col-xs-12"
													for="Suffix">Suffix<span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 col-xs-12">
													<input type="text" name="practiceSuffix"
														placeholder="Practice Suffix"
														value="<s:property value="practiceSuffix"/>"
														required="required" class="form-control">
												</div>
											</div>

											<!-- Practice Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12" style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#clinicSetting" aria-expanded="false"
														aria-controls="collapseExample"> PRACTICE SETTINGS: </a>
												</div>

												<div class="collapse in" id="clinicSetting"
													style="margin-top: 15px;">

													<!-- <div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Tagline">Tagline </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="tagline"
																value="<s:property value="tagline"/>"
																placeholder="Tagline" class="form-control">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Website">Website </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="website"
																value="<s:property value="website"/>"
																placeholder="Website" class="form-control">
														</div>
													</div>

													<div class="item form-group" id="profilePicID1">
														<label for="Profile Pic"
															class="control-label col-md-3 col-sm-3 col-xs-12">Logo
															<span class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<div class="col-md-8 col-sm-8 col-xs-12"
																style="margin-left: -10px;">
																<input type="text" name="pathToLogo"
																	class="form-control" required="required"
																	value="<s:property value="pathToLogo"/>"
																	readonly="readonly">
															</div>
															<div class="col-md-4 col-sm-4 col-xs-12">
																<button class="btn btn-default" type="button"
																	onclick="logoShow();">Change Logo</button>
															</div>
														</div>
													</div>

													<div class="item form-group" id="profilePicID"
														style="display: none;">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Logo">Logo<span class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:file name="pathToLogoFile" class="form-control"
																id="logoClickID" required="required"></s:file>
														</div>
													</div>-->

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Consent Documents">Consent Documents </label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="consentDocuments"
																value="<s:property value="consentDocuments"/>"
																placeholder="Consent Documents" class="form-control">
														</div>
													</div>

													<!-- <div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Page Size">Page Size<span class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<s:radio list="#{'A4':'A4','A5':'A5'}" name="pageSize"
																required="required" style="margin-top: 5px;width: 5%;"></s:radio>
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Letter Head Image">Letter Head Image </label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="text" name="lettrHeadImage"
																value="<s:property value="lettrHeadImage"/>"
																class="form-control" placeholder="Letter Head Image">
														</div>
													</div> -->

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Session Timeout">Session Timeout<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="text" name="sessionTimeout"
																value="<s:property value="sessionTimeout"/>"
																class="form-control" required="required"
																placeholder="Session Timeout">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Invalid Attempts">Invalid Attempts<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="text" name="invalidAttempts"
																value="<s:property value="invalidAttempts"/>"
																class="form-control" required="required"
																placeholder="Invalid Attempts">
														</div>
													</div>
													
													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Bucket Name">Bucket Name<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="text" name="bucketName"
																value="<s:property value="bucketName"/>"
																class="form-control" required="required"
																placeholder="Bucket Name">
													</div>
												</div>
												
												<div class="item form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12"
														for="Bucket Name">Dashboard?<span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-10">
														<s:radio list="#{'0':'Calendar','1':'Facility','2':'Appointment List'}" required="required" name="facilityDashboard"></s:radio>
													</div>
												</div>
												
												<div class="item form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12"
														for="Bucket Name">Third Party API Integration?<span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-10">
														<s:radio list="#{'0':'No','1':'Yes'}" required="required" name="thirdPartyAPIIntegration"></s:radio>
													</div>
												</div>
												
												</div>
											</div>
											<!-- Ends -->
											
											<!-- SMS Details -->
        			  <div class="row" >
        			  	<div class="col-md-12 col-sm-12" style="margin-bottom:15px;">
        			  		<a style="text-decoration:underline;font-size:14px; font-weight:bold; text-align:right;color: #23527c;" class="col-md-3 col-sm-3" data-toggle="collapse" href="#SMSSetting" aria-expanded="false" aria-controls="collapseExample">
  								SMS SETTINGS:
							</a>
        			  	</div>
        			  	
        			  	<div class="collapse" id="SMSSetting" style="margin-top:15px;">
		                    
		                  <%--   <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Consent Documents">SMS Username
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-12">
		                          <input type="text" name="smsUsername" value="<s:property value="smsUsername"/>" placeholder="SMS Username" class="form-control">
		                        </div>
		                    </div>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Session Timeout">SMS Password
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="password" name="smsPassword" value="<s:property value="smsPassword"/>" class="form-control"  placeholder="SMS Password">
		                        </div>
		                    </div> --%>
		                    
		                    <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Invalid Attempts">SMS URL
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="url" name="smsURL" class="form-control" value="<s:property value="smsURL"/>" placeholder="SMS URL">
		                        </div>
		                    </div>
		                    
		                     <div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Invalid Attempts">SMS Sender ID
		                        </label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="text" name="smsSenderID" value="<s:property value="smsSenderID"/>" class="form-control" placeholder="SMS Sender ID">
		                        </div>
		                    </div>
        			  	
        			  		<div class="item form-group">
		                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Invalid Attempts">SMS API Key</label>
		                        <div class="col-md-6 col-sm-6 col-xs-10">
		                          <input type="text" name="smsApiKey" value="<s:property value="smsApiKey"/>" class="form-control" placeholder="SMS API Key">
		                        </div>
		                    </div>
        			  	</div>
        			  </div>        			  
        			  <!-- Ends -->


											<!-- Communication Details -->
											<div class="row">
												<div class="col-md-12 col-sm-12" style="margin-bottom: 15px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-3 col-sm-3" data-toggle="collapse"
														href="#commSetting" aria-expanded="false"
														aria-controls="collapseExample"> COMMUNICATION
														SETTINGS: </a>
												</div>

												<div class="collapse" id="commSetting"
													style="margin-top: 15px;">

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Email Notification To">Email Notification To<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="email" name="emailNotificationsTo"
																value="<s:property value="emailNotificationsTo"/>"
																class="form-control" required="required"
																placeholder="Email Notification To">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Send Email From">Send Email From<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="email" name="sendEmailsFrom"
																value="<s:property value="sendEmailsFrom"/>"
																class="form-control" required="required"
																placeholder="Send Email From">
														</div>
													</div>

													<div class="item form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="Email From Password">Email From Password<span
															class="required">*</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-10">
															<input type="password" name="emailsFromPassword"
																value="<s:property value="emailsFromPassword"/>"
																required="required" class="form-control"
																placeholder="Email From Password">
														</div>
													</div>

													<div class="row" style="margin-top: 15px;">
														<h4 style="font-weight: bold;">Communication Table</h4>
													</div>

													<div class="row" style="margin-top: 10px; padding: 0 40px;">

														<table id="datatable-responsive"
															class="table table-striped table-bordered dt-responsive nowrap"
															cellspacing="0" width="100%">
															<thead>
																<tr>
																	<th style="text-align: center;">Configuration</th>
																	<th style="text-align: center;">SMS</th>
																	<th style="text-align: center;">Email</th>
																</tr>
															</thead>
															<tbody>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Appointment
																		Scheduled</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptSchedlSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptSchedlEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Appointment
																		Updated</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptUpdatedSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptUpdatedEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Appointment
																		Cancelled</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptCancelledSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="apptCancelledEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Send
																		Bill</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendBillSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendBillEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Send
																		Prescription</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendPrescSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendPrescEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Send
																		thanks to referring doctors</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendThanksToRefDocSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="sendThanksToRefDocEmail"
																		value="1" style="zoom: 1.5;"></td>

																</tr>

																<tr>

																	<td style="font-size: 14px; text-align: center;">Welcome
																	message to Patient</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="welcomeMsgSMS" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="welcomeMsgEmail" value="1"
																		style="zoom: 1.5;"></td>

																</tr>
																
																<tr>
	
																	<td style="font-size: 14px; text-align: center;">Patient Login Credentials
																		</td>
	
																	<td style="text-align: center;"><input
																		type="checkbox" name="patLoginCredSMS" value="1"
																		style="zoom: 1.5;"></td>
	
																	<td style="text-align: center;"><input
																		type="checkbox" name="patLoginCredEmail" value="1"
																		style="zoom: 1.5;"></td>
	
																</tr>

																 <tr>

																	<td style="font-size: 14px; text-align: center;">Appointment
																		Reminder</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="smsDailyAppt" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="emailDailyAppt" value="1"
																		style="zoom: 1.5;"></td>

																</tr>
																
																<tr>

																	<td style="font-size: 14px; text-align: center;">Inventory
																		Alert</td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="smsInventory" value="1"
																		style="zoom: 1.5;"></td>

																	<td style="text-align: center;"><input
																		type="checkbox" name="emailInventory" value="1"
																		style="zoom: 1.5;"></td>

																</tr>
																
																<tr>
                        	
									                        		<td style="font-size:14px;text-align:center;">Send thanks to patient</td>
									                        		
									                        		<td style="text-align:center;"><input type="checkbox" name="smsReviewForm" id="smsReviewFormID" onclick="checkReviewForm(this, 'SMS');" value="1" style="zoom:1.5;">  </td>
									                        		
									                        		<td style="text-align:center;"><input type="checkbox" name="emailReviewForm" id="emailReviewFormID" onclick="checkReviewForm(this, 'Email');" value="1" style="zoom:1.5;"> </td>
									                        	
									                        	</tr>
									                        	
									                        	<tr id="reviewTRID" style="display: none;">
									                        		<td style="font-size:14px;text-align:center;">Review Form Link</td>
									                        		
									                        		<td style="text-align:center;" colspan="2"><input type="text" id="reviewFormID" name="reviewForm" class="form-control" value="<%=reviewFormURL %>"> </td>
									                        	</tr>

															</tbody>
														</table>

													</div>



												</div>
											</div>
											<!-- Ends -->
											
											
										<!-- Plan Details table -->
										<div class="col-md-6">
											<div class="col-md-12 col-sm-12" style="margin-bottom: 15px; margin-left :0px;">
												<a
													style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c; margin-right:12px;"
													class="col-md-6 col-sm-6 col-xs-12" data-toggle="collapse"
													aria-expanded="false" aria-controls="collapseExample"> PLAN DETAILS: </a>
											
												<button id="addNewPlanID" type="button" class="btn btn-success" onclick="addNewPlan(<s:property value="practiceID"/> );">Add New Plan</button>
											
											</div>
										</div>
										
										<div class="row" style="margin-top: 15px; padding: 30px;">
											<table class="table table-striped table-bordered dt-responsive nowrap" id ="planTableTRID">
												<thead>
														<tr> 
														
															<th style="text-align: center; width:25%;">No. of Visits</th>
															<th style="text-align: center; width:25%;">Start Date</th>
															<th style="text-align: center; width:25%;">End Date</th>
															<th style="text-align: center; width:25%;">Status</th>
														</tr>
													</thead>
													<tbody>

												<tr id="planTRID" style="display: none;" > 
													
											
													<td style="text-align: center;"><select  id="noOfVisitID" class="form-control" name="NoOfVisits">
																		<option value="100">100</option>
																		<option value="200">200</option>
																		<option value="500">500</option>
																		<option value="1000">1000</option>
																		<option value="2000">2000</option>
																		<option value="1000000">Unlimited</option></select>
													</td>
										
				            
								             		<td style="text-align: center;"><input type="text" class="form-control" name ="DateStart" id="datepicker1" placeholder="Start Date" required></td>
								          
								            		<td style="text-align: center;"><input type="text" class="form-control" name ="DateEnd" id="datepicker2" placeholder="End Date" required></td>
								         
								             		<td style="text-align: center;"><input type="text" class="form-control" name ="planStatus" readonly="readonly" id="planStatusID"></td>
								  
												</tr>
												
														<s:iterator value="planDetailsList" var="clinicForm">
														
																<tr style="font-size: 14px;" id="planTRID<s:property value="planID" />">
																	
																<td style="text-align: center;"><input type="hidden" name="planNoOfVisits" value="<s:property value="planNoOfVisits" />"><s:property value="planNoOfVisits" /></td>
																		
																<td style="text-align: center;"><input type="hidden" name="planDateStart" value="<s:property value="planDateStart" />"><s:property value="planDateStart" /></td>

																<td style="text-align: center;"><input type="hidden" name="planDateEnd"  value="<s:property value="planDateEnd" />"><s:property value="planDateEnd" /></td>
									
																<td style="text-align: center;"><input type="hidden" name="planStatus1" value="<s:property value="planStatus1" />"><s:property value="planStatus1" /></td>

																</tr>

															</s:iterator>
														</tbody>
													</table>
										</div>
									<!-- END -->
									
									
											<div class="col-md-12">
												<div class="col-md-3 col-sm-12" style="margin-bottom: 15px; margin-left :5px;">
													<a
														style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
														class="col-md-12 col-sm-6 col-xs-12" data-toggle="collapse"
														href="#clinicWorklowSetting" aria-expanded="false"
														aria-controls="collapseExample"> CLINIC SETTINGS: </a>
												</div>
												<div class="col-md-1" align="left">
														<img src="images/addBill.png" style="height:30px;cursor: pointer;" 
				  											onclick="addNewRow();" 
															onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Clinic"
															title="Add Clinic"/>
												</div>
											</div>
												
											<div class="row" style="margin-top: 15px; padding: 30px;">
											
													<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="50%" >
														<thead>
															<tr>
																<th style="text-align: center;">Clinic Name</th>
																<th style="text-align: center;">Clinic Suffix</th>
																<th style="text-align: center;">Clinic Phone</th>
																<th style="text-align: center;">Status</th>
																<th style="text-align: center;">Settings</th>
																<th style="text-align: center;">Upload Image</th>
																<th style="text-align: center;">Action</th>
																</tr>
															
														</thead>
														<tbody>

															<tr id="compTRID"> </tr>
														<s:iterator value="clinicList" var="clinicForm">
	
																<tr style="font-size: 14px;" id="clinicTRID<s:property value="clinicID" />">
																	

																	<td style="text-align: center;"><input type="hidden" name="clinicNameArr" value="<s:property value="clinicName" />"><s:property value="clinicName" /></td>
																			
																	<td style="text-align: center;"><s:property value="clinicSuffixName" /><input type="hidden" name="clinicIDArr" value="<s:property value="clinicID" />"></td>
																	
																	<td style="text-align: center;"><input type="text" class="form-control" name="editClinicPhoneNoArray" id="editCPhoneID<s:property value="clinicID" />" value="<s:property value="clinicPhoneNo" />"></td>

																	<td style="text-align: center;"><s:property value="clinicActivityStatus" /></td>
										
																	<%-- <td style="text-align: center;">
																	<div class="row" id="LogoID<s:property value="clinicID" />" >
																		<div class="col-md-10" >
																			<input type="text" name="logoLabelArr[<s:property value="clinicID" />][]" id="logoLabelID<s:property value="clinicID" />" class="form-control" style="width:100%" value="<s:property value="logoArrDBName"/>" readonly="readonly" >
																			<input type="file" name="logoArrStr" id="logoFileID<s:property value="clinicID" />" value="<s:property value="logoArrDBName"/>">
																		</div>								
																		<div class="col-md-2" >
																			<a href="javascript:logoImgShow('<s:property value="clinicID" />')"><img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Upload logo" title="Upload logo"/></a>
																		</div>
																	</div>
																	<div class="row" id="LogoID11<s:property value="clinicID" />" style="display:none;"><div class="col-md-12" >
																			<input type="file" name="" id="FileID<s:property value="clinicID" />">
																			<input type="hidden" name="" id="HiddenFileID<s:property value="clinicID" />" value="<s:property value="logoArrDBName"/>">
																	</div></div>
																	</td> --%>
																	
																	<%-- <td style="text-align: center;">
																	<div class="row" id="LetterID<s:property value="clinicID" />" >
																		<div class="col-md-10" >
																			<input type="text" name="LetterHeadArrStr" id="letterArrID<s:property value="clinicID" />" class="form-control" style="width:100%" value="<s:property value="LetterHeadArrDBName"/>" readonly="readonly" >
											
																		</div>
																		<div class="col-md-2" >
																			<a href="javascript:letterHeadShow('<s:property value="clinicID" />')"><img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'" onmouseout="this.src='images/add_icon_1.png'" alt="Upload letterHead Image" title="Upload letterHead Image"/></a>
																		</div>
																	</div>
																	<div class="row" id="LetterID11<s:property value="clinicID" />" style="display:none;"><div class="col-md-12" >
																			<input type="file"  id="FileIDLetter<s:property value="clinicID" />" onChange="ChangeValue(this,'HiddenFileIDLetter<s:property value="clinicID" />')">
																			<input type="hidden" name="" id="HiddenFileIDLetter<s:property value="clinicID" />">
																	</div></div>
																	</td> --%>

																	<td style="text-align: center;"><a href="javascript:viewSetting(<s:property value="clinicID" />);">Edit Setting</a><input type="hidden" id="dummySettingTextID" name="dummySettingTextEdit"></td>
																	
																	<td style="text-align: center;"><a href="javascript:uploadImg(<s:property value="clinicID" />);">Upload Logo and Letterhead Image</a></td>
																
																	<td style="text-align: center;"><img src='images/delete.png'
																		style='height: 24px; cursor: pointer;'
																		alt='Remove row'
																		onclick="deleteRow1(<s:property value="clinicID" />);"
																		title='Remove row' /></td>

																</tr>

															</s:iterator>

														</tbody>
													</table>

												</div>
										
											<!-- Ends -->

<!-- MD Details -->
        			  <div class="row" >
        			  	<div class="col-md-12 col-sm-12" style="margin-bottom:15px;">
        			  		<div class="col-md-3 col-sm-12" style="margin-bottom: 15px; margin-left :5px;">
	        			  		<a style="text-decoration: underline; font-size: 14px; font-weight: bold; text-align: right; color: #23527c;"
									class="col-md-12 col-sm-6 col-xs-12" data-toggle="collapse" href="#MDSetting" aria-expanded="false" aria-controls="collapseExample">
	        			  			MD SETTINGS:
								</a>
							</div>
							<div class="col-md-1" align="left">
								<img src="images/addBill.png" style="height: 30px; margin-left:15px; cursor: pointer;"
									onclick="addNewMDRow();" onmouseover="this.src='images/addBill1.png'"
									onmouseout="this.src='images/addBill.png'" alt="Add Clinic" title="Add Clinic" />
							</div>			
        			  	</div>
        			  	
        			  	<div class="row" style="margin-top: 15px; padding: 30px;">
        			  	
        			  	<table class="table table-striped table-bordered dt-responsive nowrap">
								<thead>
										<tr>
											<th style="text-align: center; width:22%;">MD Name</th>
											<th style="text-align: center; width:22%;">MD Qualification</th>
											<th style="text-align: center; width:20%;">MD Signature Image</th>
											<th style="text-align: center; width:10%;">Start Date</th>
											<th style="text-align: center; width:12%;">End Date</th>
											<th style="text-align: center;">Action</th>
										</tr>
									</thead>
									<tbody>
										<tr id="mdDetailsTRID"></tr>
										
										<s:iterator value="mdDetailsList"  var="clinicForm">
											<tr style="font-size: 14px;" id="mdDetailsTRID<s:property value="mdDetailsID" />">
																					
												<td style="text-align: center;"><s:property value="practiceMDName"/></td>
																					
												<td style="text-align: center;"><s:property value="practiceMDQualification" /></td>
			
												<td style="text-align: center;"><s:property value="practiceMDSignatureImage" /></td>
					
												<td style="text-align: center;"><s:property value="practiceMDStartDate" /></td>
												
												<td style="text-align: center;"><input type="text" class="form-control" name="practiceMDEndDateEdit" id="practiceEndDateID<s:property value="mdDetailsID" />" value="<s:property value="practiceMDEndDate" />">
																				<input type="hidden" class="form-control" name="mdDetailsIDEdit" value="<s:property value="mdDetailsID" />">
												</td>
													
												<script>
													document.addEventListener('DOMContentLoaded', function() {
														$('#practiceEndDateID'+<s:property value="mdDetailsID"/>).daterangepicker({
												            singleDatePicker: true,
												            calender_style: "picker_3"
												          }, function(start, end, label) {
												            console.log(start.toISOString(), end.toISOString(), label);
												          });
												          
												       });
												</script>
													
											</tr>
										</s:iterator>
									</tbody>
							</table>
						    
        			  	</div>
        			  	</div>
					<!-- Ends -->
					
											<div class="ln_solid"></div>
											<div class="form-group">
												<div class="col-md-12 col-xs-12" align="center">
													<button type="button" onclick="windowOpen();"
														class="btn btn-primary">Cancel</button>
													<button id="practiceSubmitID" type="button"
														class="btn btn-success" onclick="CheckEmptyField();">Update Practice</button>
												</div>
											</div>

										</s:iterator>
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
		
		<script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		
	<!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>

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