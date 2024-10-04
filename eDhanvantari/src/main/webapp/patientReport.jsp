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

    <title>Generate Clinical Report | E-Dhanvantari</title>

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
			$('a#pmID').remove();	//To disable leftmenu options patient manag
			$('a#ivntID').remove();	//To disable leftmenu options invt mang
			$('a#admID').remove();	//To disable leftmenu options adminstr
			$('a#repID').remove();	//To disable leftmenu options Reports
			
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
    	String searchCriteria = (String) request.getAttribute("searchCriteria");
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
    
    	function checkCriteria(criteria){
    		
    		if(criteria == "All Patients"){
    			$("#searchNameID").removeAttr("required");
    		}else{
    			$("#searchNameID").attr("required","required");
    		}
    	}
    
    </script>
    
    
    <!-- retrieving column list based on table -->
    
    <script type="text/javascript" charset="UTF-8">

  	//to store count value in order to store it into select statement to find out count of query
  	// and its default value is *
  	var countValue = "*";
  	
  	//To store presviously selected table names only in order to check whether that particular 
  	//table name already added into the query, if so then only appneding new condition from that table else
  	// proceeding further with the current workflow
  	var lastTableName = "";
  
  	 var xmlhttp;
 	if (window.XMLHttpRequest) {
 		xmlhttp = new XMLHttpRequest();
 	} else {
 		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
 	}
    
    function retrieveColumnNameList(value, selectIndex, mainDivID, parentTableName){
    	
    	//var selectIndex = $("#" +mainDivID +" "+ selectItem).index();
    	
    	//alert(selectIndex);
    	if(value == ""){
    		
    		alert("Please select value.");
    		
    		$("#"+mainDivID+" div:gt("+selectIndex+")").remove();
    		
    	}else{
    		if(value == "NA"){
	    		
	    		checkNoCriteria(value, divID, colDivID, className, parentTable, tableNameList, mainDivID);
	    	}else{
	    		
	    		 /* check whether selected value is column name or table name by
	    		 checking whether it contains =, if it conains =, then selected 
	    		 value is column name else it is table name, and if it's table name,
	    		 then retrieve column list of the selected table */
	    		
	    		 if(value.includes("=")){
	    				
	    			// checking whether column name is use as fkey in the XML file if
	   	    		// so, then retrieving the table name and cname of the same, else
	    	    	// display criteria as per the data type of the column
	    	    	checkColumnType(value, selectIndex, mainDivID, parentTableName);
	    			
	    		 }else{
	    			 
	    			 // retrieving column name list based on the selected table name from the 
	    			 // database
	    			 retrieveColumnNameListByTableName(value, selectIndex, mainDivID, parentTableName);
	    		 }
	    	}
    	}	
    }
    
    
	var hiddenCounter = 1;
	//function to check whether column name is used as fkey in any of the ptable tag in reporting XML
	function checkColumnType(value, selectIndex, mainDivID, parentTable) {
		
		//alert(parentTable);
		
		if(value == "NA"){
    		
    		checkNoCriteria(value, divID, colDivID, className, parentTable, tableNameList, mainDivID);
    	
		}else{
    		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var ErrMsg = "";
					var attributeValue = "";
					var check = 0;
					
					for ( var i = 0; i < array.Release.length; i++) {
						console.log("checkcolumn,, release");
						ErrMsg = array.Release[i].ErrMsg;
						check = array.Release[i].check;
						attributeValue = array.Release[i].attributes;
					}
					
					var criteriaArray = [];
					
					console.log("Error..."+ErrMsg);
					
					console.log("attributeValue.."+attributeValue+"..check..."+check);
					
					var hiddenIDValue = "";
					
					var hiddenID = "";
					
					var classVal = "";
					
					if(parentTable.includes(",")){
						var newArray = parentTable.split(",");
						
						hiddenIDValue = newArray[newArray.length-1].split("AS");
						
						hiddenID = hiddenIDValue[0].trim()+"HiddenID";
						
						classVal = hiddenIDValue[0].trim()+"Class";
					}else{
						hiddenIDValue = parentTable.split("AS");
						
						hiddenID = hiddenIDValue[0].trim()+"HiddenID";
						
						classVal = hiddenIDValue[0].trim()+"Class";
					}
					
					var colValueArray = value.split("=");
					
					if(attributeValue != "" && typeof attributeValue != "undefined"){
						var dataType = colValueArray[0];
						var columnName = colValueArray[1];
						
						$("#"+mainDivID+" div:gt("+selectIndex+")").remove();
						
						console.log("...dataType: "+dataType+"...columnName: "+columnName);
						
						criteriaArray['Equal To'] = '= \"?\"';
						criteriaArray['Not Equal To'] = '<> \"?\"';
						criteriaArray['Like'] = 'LIKE \"%?%\"';
						criteriaArray['In'] = 'IN(\"?\")';
						criteriaArray['Not In'] = 'NOT IN(\"?\")';
						
						var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patCriteriaDivClass'>";
						
						if ($("#"+hiddenID).length){
						 /* it exists then don't create hiddenID @image tag*/
							
						 var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\""+attributeValue+"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
								+"<option value='= \"?\"'>Equal To</option>"
								+"<option value='<> \"?\"'>Not Equal To</option>"
								+"<option value='LIKE \"%?%\"'>Like</option>"
								+"<option value='IN(\"?\")'>In</option>"
								+"<option value='NOT IN(\"?\")'>Not In</option>"
								+"</select></div>"
								+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
								+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
								+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+classVal+"' id='' value =''>"
								+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\""+attributeValue+"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
						 
						}else{
						 /* it doesn't exist then create hiddenID @image tag
						 create hiddenID with parentTable name */ 
						 
							var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\""+attributeValue+"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
								+"<option value='= \"?\"'>Equal To</option>"
								+"<option value='<> \"?\"'>Not Equal To</option>"
								+"<option value='LIKE \"%?%\"'>Like</option>"
								+"<option value='IN(\"?\")'>In</option>"
								+"<option value='NOT IN(\"?\")'>Not In</option>"
								+"</select></div>"
								+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
								+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
								+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class ='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
								+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\""+attributeValue+"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
						}
												
						$("#"+mainDivID).append(criteriaSelectTag);
						
					}else{
						
						var dataType = colValueArray[0];
						var columnName = colValueArray[1];
						
						$("#"+mainDivID+" div:gt("+selectIndex+")").remove();
						
						var criteriaClass = "patCriteriaClass";
						
						if(dataType.includes("date") || dataType.includes("timestamp")){
							
							criteriaArray['Equal To'] = '= \"?\"';
							criteriaArray['Not Equal To'] = '<> \"?\"';
							criteriaArray['Less Than'] = '< \"?\"';
							criteriaArray['Greater Than'] = '> \"?\"';
							criteriaArray['Between'] = 'BETWEEN \"?\" AND \"?\"';
							
							var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patCriteriaDivClass'>";
							
							if ($("#"+hiddenID).length){
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='< \"?\"'>Less Than</option>"
									+"<option value='> \"?\"'>Greater Than</option>"
									+"<option value='BETWEEN \"?\" AND \"?\"'>Between</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass' placeholder='Enter Date' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+classVal+"' id='' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}else{
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='< \"?\"'>Less Than</option>"
									+"<option value='> \"?\"'>Greater Than</option>"
									+"<option value='BETWEEN \"?\" AND \"?\"'>Between</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass'  placeholder='Enter Date' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}
							
							$("#"+mainDivID).append(criteriaSelectTag);
							
							applyDatepicker();
							
						}else if(dataType.includes("int(") || dataType == "long" || dataType.includes("double")){
							
							criteriaArray['Equal To'] = '= \"?\"';
							criteriaArray['Not Equal To'] = '<> \"?\"';
							criteriaArray['Less Than'] = '< \"?\"';
							criteriaArray['Greater Than'] = '> \"?\"';
							criteriaArray['Between'] = 'BETWEEN \"?\" AND \"?\"';
							
							var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patCriteriaDivClass'>";
							
							if ($("#"+hiddenID).length){
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='< \"?\"'>Less Than</option>"
									+"<option value='> \"?\"'>Greater Than</option>"
									+"<option value='BETWEEN \"?\" AND \"?\"'>Between</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText ' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+classVal+"' id='' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}else{
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='< \"?\"'>Less Than</option>"
									+"<option value='> \"?\"'>Greater Than</option>"
									+"<option value='BETWEEN \"?\" AND \"?\"'>Between</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText ' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}
									 
							$("#"+mainDivID).append(criteriaSelectTag);
							
						}else{
							console.log("else...dataType: "+dataType+"...columnaName: "+columnName);	
							
							criteriaArray['Equal To'] = '= \"?\"';
							criteriaArray['Not Equal To'] = '<> \"?\"';
							criteriaArray['Like'] = 'LIKE \"%?%\"';
							criteriaArray['In'] = 'IN(\"?\")';
							criteriaArray['Not In'] = 'NOT IN(\"?\")';
							
							var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patCriteriaDivClass'>";
							
							if ($("#"+hiddenID).length){
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='LIKE \"%?%\"'>Like</option>"
									+"<option value='IN(\"?\")'>In</option>"
									+"<option value='NOT IN(\"?\")'>Not In</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+classVal+"' id='' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}else{
								
								var criteriaSelectTag = divTag + "<select name='' data-fkeyattr='"+attributeValue+"' class='reportCriteriaClass form-control patCriteriaClass Criteria' id ='displaySearchFieldID"+selectTagIndex+"' onchange='displaySearchFields(this.value, \""+mainDivID+"\",\""+dataType+"\",\""+parentTable+"\",\""+value+"\",\""+criteriaArray+"\", \"displaySearchFieldID"+selectTagIndex+"\",\"\",\""+hiddenID+"\",\""+classVal+"\",\""+selectIndex+"\")'>"
									+"<option value='= \"?\"'>Equal To</option>"
									+"<option value='<> \"?\"'>Not Equal To</option>"
									+"<option value='LIKE \"%?%\"'>Like</option>"
									+"<option value='IN(\"?\")'>In</option>"
									+"<option value='NOT IN(\"?\")'>Not In</option>"
									+"</select></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
									+"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName+"'></div>"
									+"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
									+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+mainDivID+"\",\""+dataType+"\",\""+value+"\",\""+criteriaArray+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"displaySearchFieldID"+selectTagIndex+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
							}
											
							$("#"+mainDivID).append(criteriaSelectTag);
						}			
					}
				}
			};
			xmlhttp.open("GET", "VerifyFkeyExists?check="+ value , true);
			xmlhttp.send();
		}
	}
	
	
	function checkNoCriteria(value, divID, colDivID, className, parentTable, tableNameList, mainDivID) {
		
		var hiddenIDValue = parentTable.split("AS");
				
		var hiddenID = hiddenIDValue[0].trim()+"HiddenID";
				
		var classVal = hiddenIDValue[0].trim()+"Class";
				
		var colValueArray = value.split("=");
				
		var dataType = colValueArray[0];
		var columnName = colValueArray[1];
					
		$("#"+divID).html("");
					
		console.log("...dataType: "+dataType+"...columnName: "+columnName);
					
		if ($("#"+hiddenID).length){
			 /* it exists then don't create hiddenID @image tag*/
						
			var criteriaSelectTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+classVal+"' id='' value =''>"
							+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+className+"\",\""+parentTable+"\",\""+tableNameList+"\", \""+divID+"\",\""+dataType+"\",\""+colDivID+"\",\""+value+"\",\"\",\""+mainDivID+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"\", \"\");' alt='Add Query' title='Add Query' /></div>";
					 
		}else{
			/* it doesn't exist then create hiddenID @image tag
			 create hiddenID with parentTable name */ 
					 
			var criteriaSelectTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
							+"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+className+"\",\""+parentTable+"\",\""+tableNameList+"\", \""+divID+"\",\""+dataType+"\",\""+colDivID+"\",\""+value+"\",\"\",\""+mainDivID+"\",\"\", \""+hiddenID+"\", \""+classVal+"\", \"\", \"\");' alt='Add Query' title='Add Query' /></div>";
		}
		
		$("#"+divID).append(criteriaSelectTag);
	}
	
	
	function displaySearchFields(criteria, divID, dataType, parentTable, selectValue, criteriaArray, displaySearchFieldID, fkeyAttribute, hiddenID, hiddenClass, selectIndex){
		
		var newSelectIndex = parseInt(selectIndex) + 1;
		
		//alert(selectIndex);
		
		$("#"+divID).find("div:gt("+newSelectIndex+")").remove();
		
		var columnName = selectValue.split("=");
		
		if(fkeyAttribute != "" && typeof fkeyAttribute != "undefined"){
			
			if ($("#"+hiddenID).length){
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>"; 
					 
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			}
	
			$("#"+divID).append(divTag);
		
		}else if(criteria.includes("BETWEEN") && (dataType.includes("date") || dataType.includes("timestamp"))){
			
			if ($("#"+hiddenID).length){
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass'  placeholder='Enter From Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass2 SearchText dateClass'  placeholder='Enter To Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
				
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass'  placeholder='Enter From Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass2 SearchText dateClass'  placeholder='Enter To Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			}
			
			$("#"+divID).append(divTag);
			
			applyDatepicker();
		
		}else if(dataType.includes("date") || dataType.includes("timestamp")){
			
			if ($("#"+hiddenID).length){
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass' placeholder='Enter Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText dateClass' placeholder='Enter Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			}
			
			$("#"+divID).append(divTag);
			
			applyDatepicker();
			
		}else if(criteria.includes("BETWEEN") && (dataType.includes("int(") || dataType == "long" || dataType.includes("double"))){
		
			if ($("#"+hiddenID).length){
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter From Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass2 SearchText' placeholder='Enter To Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
				
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter From Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass2 SearchText' placeholder='Enter To Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			}
			
	
			$("#"+divID).append(divTag);	
			
		}else if(dataType.includes("int(") || dataType == "long" || dataType.includes("double")){
			
			if ($("#"+hiddenID).length){
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");'  alt='Add Query' title='Add Query' /></div>";
				
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");'  alt='Add Query' title='Add Query' /></div>";
			}
			
			$("#"+divID).append(divTag);
			
		}else{
			
			if ($("#"+hiddenID).length){
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='"+hiddenClass+"' id='' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>"; 
					 
			}else{
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patAddImgDivClass' id='imageDivID'><input type='hidden' class='reporthiddenClass' data-where='' data-table='' id='"+hiddenID+"' value =''>"
					 +"<img src='images/addBill.png' id='addImageID' style='height:24px;margin-top:5px;cursor: pointer;' onclick='addQueryRow(\""+parentTable+"\", \""+divID+"\",\""+dataType+"\",\""+selectValue+"\",\""+criteriaArray+"\",\""+fkeyAttribute+"\", \""+hiddenID+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \"patSearchDivClass\", \""+selectIndex+"\");' alt='Add Query' title='Add Query' /></div>";
			}
	
			$("#"+divID).append(divTag);
		}
	}
	
	function editDisplaySearchFields(criteria, divID, dataType, className,searchDivClassName, parentTable, selectValue, fKeyAttribute, selectIndex){
		console.log("Hello: "+fKeyAttribute);
		$("#"+divID).find("."+searchDivClassName+"1").remove();
		$("#"+divID).find("."+searchDivClassName+"2").remove();
		
		var columnName = selectValue.split("=");
		
		if(fKeyAttribute != "" && typeof fKeyAttribute != "undefined"){
			console.log("Hello");
			
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"; 
			
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
		
		}else if(criteria.includes("BETWEEN") && (dataType.includes("date") || dataType.includes("timestamp"))){
			
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText dateClass'  placeholder='Enter From Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass2 "+className+"SearchText dateClass'  placeholder='Enter To Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>";
				
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
			
			applyDatepicker();
		
		}else if(dataType.includes("date") || dataType.includes("timestamp")){
			
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText dateClass' placeholder='Enter Date' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>";
			
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
			
			applyDatepicker();
			
		}else if(criteria.includes("BETWEEN") && (dataType.includes("int(") || dataType == "long" || dataType.includes("double"))){
		
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText' placeholder='Enter From Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
					 +"<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass2'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass2 "+className+"SearchText' placeholder='Enter To Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>";
			
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
			
		}else if(dataType.includes("int(") || dataType == "long" || dataType.includes("double")){
			
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='number' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>";
	
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
			
		}else{
			
			var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patSearchDivClass1'>"
					 +"<input type='text' class='reportSearchTextClass form-control patSearchClass1 "+className+"SearchText' placeholder='Enter Value' data-table ='"+parentTable+"' data-text='"+columnName[1]+"'></div>"
			
			$("#"+mainDivID).find(".patAddImgDivClass").before(divTag);
		}
	}
	
	function applyDatepicker(){
		 	
	   $(".dateClass").daterangepicker({
	        singleDatePicker: true,
	        calender_style: "picker_3",
	        format: "YYYY-MM-DD"
	    }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	    });
	}
	
	var newDivCounter = 1;
	
	var selectTagIndex = 0;
	
	function addQueryRow(parentTableName, mainDivID, dataType, selectedValue, criteriaArray, fKeyAttribute, hiddenID, hiddenClass, displaySearchFieldID, searchDivClassName, selectIndex){
		
		var practiceID = $("#practiceID").val();
		var clinicID = $("#clinicID").val();
		
		/* var whereColumnName = $("."+className+"Criteria").val();
		
		var searchText= ""; */
		
		var min=1; 
	    var max=10000;  
	    var random = 
	    Math.floor(Math.random() * (+max - +min)) + +min; 
		
		var className = hiddenClass + "_" + random;
		
		console.log("...parentTableName..sss"+parentTableName);
		
		var fromTableValue = "Patient AS pt";
		var whereValue = "";
		var alias = "pt";
		
		var divID = className+"DivID"+newDivCounter;
		
		var newDivTag = "<div class='row' style='margin-top: 15px;' id=''>";
		var finalOptionVal = "";
		
		//var hiddenCheckValue = $("#"+hiddenID).val();
		
		var hiddenCheckValue = "";
		
		$(".reporthiddenClass").each(function(){
			if($(this).val() != ""){
				hiddenCheckValue = hiddenCheckValue + "," + $(this).val();	
			}
		});
		
		if(hiddenCheckValue.startsWith(",")){
			hiddenCheckValue = hiddenCheckValue.substr(1);
		}
		
		var hiddenIDCheckValue = $("#"+hiddenID).val();
		
		if(parentTableName == "Patient AS pt"){
			
			$("#"+mainDivID+" select").eq(selectIndex).find("option").each(function(){
				
				var optionVal = $(this).val();
				var optionText = $(this).html().trim();
				
				if(optionVal.includes("=")){
					var optionValue = optionVal.split("=");
					
					var hiddenValArr = hiddenCheckValue.split(",");
					var hiddenIDValArr = hiddenIDCheckValue.split(",");
					
					if(hiddenIDValArr.includes(optionValue[1])){
						
						finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
						"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' checked='checked' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
					}else if(hiddenValArr.includes(optionValue[1])){
						
						finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
						"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' disabled='disabled' checked='checked' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
					}else{
						
						finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
						"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
					}
					
				}
				 
			   hiddenCounter++;
			});
						
			$("#checkboxDivID").html(finalOptionVal);
			$("#checkboxModal").modal("show");
			
			$("#checkboxSubmitID").attr("onclick", "addValues(\""+className+"\", \""+hiddenID+"\", \""+mainDivID+"\", \""+parentTableName+"\", \""+selectTagIndex+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \""+dataType+"\", \""+searchDivClassName+"\", \""+selectedValue+"\", \""+fKeyAttribute+"\", \""+selectIndex+"\");");
			
		}else{
			
			fromTableValue += "," + parentTableName;
			
			for(var i = 0;i <= parseInt(selectIndex); i++){
				
				$("#"+mainDivID+" select").eq(i).find("option").each(function(){
					
					var optionVal = $(this).val();
					var optionText = $(this).html().trim();
					
					var aliasName = parentTableName.split("AS");
					
					if(optionVal.includes("=")){
						
						var optionValue = optionVal.split("=");
					
						var hiddenValArr = hiddenCheckValue.split(",");
						var hiddenIDValArr = hiddenIDCheckValue.split(",");
						
						if(hiddenIDValArr.includes(optionValue[1])){
							
							finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
							"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' checked='checked' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
						}else if(hiddenValArr.includes(optionValue[1])){
							
							finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
							"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' disabled='disabled' checked='checked' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
						}else{
							
							finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
							"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
						}
					}
					hiddenCounter++;
					
				});
				
			}
			
			/* $("."+className+"Table").eq("0").find("option").each(function(){
				
				var optionVal = $(this).val();
				var optionText = $(this).html().trim();
				
				var aliasName = parentTableName.split("AS");
				
				if(optionVal.includes("=")){
					
					var optionValue = optionVal.split("=");
				
					var hiddenValArr = hiddenCheckValue.split(",");
				
					if(hiddenValArr.includes(optionValue[1])){
						
						console.log("findValues1: "+hiddenValArr);
						finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
						"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' checked='checked' id='checkBoxID' class='"+className+"checkbox'>&nbsp;"+optionText+"</label></div>";
					}else{
						finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
						"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
					}
				}
				
				hiddenCounter++;
			}); */
			
			var initialWhereCondition = "";
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var array = JSON.parse(xmlhttp.responseText);
					
					var ErrMsg = "";
					//var condition = "";
					
					for ( var i = 0; i < array.Release.length; i++) {
						
						ErrMsg = array.Release[i].ErrMsg;
						initialWhereCondition = array.Release[i].conditionValue;
					}
					
					console.log("ErrorID..."+initialWhereCondition);
					
					$("#"+hiddenID).attr("data-where",initialWhereCondition);	
				
					/* if(firstWhereCondition != "" && firstWhereCondition == initialWhereCondition){
						$("#"+hiddenID).attr("data-where",initialWhereCondition);	
					}else{
						$("#"+hiddenID).attr("data-where",firstWhereCondition + initialWhereCondition);
					} */
					
				}
			};
			xmlhttp.open("GET", "GetInitialWhereConditionByFromQuery?check="+ parentTableName , true);
			xmlhttp.send();
			
			$("#checkboxDivID").html(finalOptionVal);
			$("#checkboxModal").modal("show");
			
			$("#checkboxSubmitID").attr("onclick", "addValues(\""+className+"\", \""+hiddenID+"\", \""+mainDivID+"\", \""+parentTableName+"\", \""+selectTagIndex+"\", \""+hiddenClass+"\", \""+displaySearchFieldID+"\", \""+dataType+"\", \""+searchDivClassName+"\", \""+selectedValue+"\", \""+fKeyAttribute+"\", \""+selectIndex+"\");");
		}
		
		selectTagIndex++;
	}
	
	var firstWhereCondition = "";

	function retrieveColumnNameListByTableName(tableName, selectIndex, mainDivID, parentTable) {
		
		//alert(".."+selectIndex);
		
		$("#"+mainDivID+" div:gt("+selectIndex+")").remove();
		
		selectIndex = parseInt(selectIndex) + 1;
		
		parentTable = parentTable + ", " +tableName;
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var errMsg = "";
				var tableCheck = "";
				var counter = 0;
				
				var selectTag = "";
				
				var columnKey = "";	
				
				var newCheck = 0;
				
				var tableClass = tableName.split("AS");
				
				var table = tableClass[0].trim();
				
				var columnListOptionTag = "<optgroup label='Columns'>";
				
				var divTag = "<div class='col-sm-2 col-md-3 col-lg-2 col-xs-12 patCriteriaDivClass'>";
				
				for ( var i = 0; i < array.Release1.length; i++) {
					console.log("inside release1");
					var columns = array.Release1[i].columns;
					newCheck = array.Release1[i].check;
					
					if(columns != "" || typeof columns != "undefined"){
						var keyArr = columns.split("=");
						
						var columnType = array.Release1[i].columnType;
						var fieldName = array.Release1[i].fieldName;
						
						columnKey = keyArr[0];
						
						columnListOptionTag += "<option value='"+columnType+"="+keyArr[1]+"'>"+fieldName+"</option>";	
					}
				}
				
				 for ( var i = 0; i < array.Release2.length; i++) {
					 console.log("inside release2..");
					firstWhereCondition += array.Release2[i].finalWhere;
				} 
				
				columnListOptionTag += "</optgroup>";

				for ( var i = 0; i < array.Release.length; i++) {
					check = array.Release[i].check;
					errMsg = array.Release[i].ErrMsg;
					tableCheck = array.Release[i].tableCheck;
					console.log("inside release..");
					if(tableCheck == "visit"){
						
						var nameArr = array.Release[i].tableName.split("=");
						console.log("inside if");
						if(counter == 0){
							console.log("inside if counter");
							var optioNTag = divTag + "<select name='' id='visitSelectID' data-parent-table='"+tableName+"' class='reportTableColClass form-control selectClass "+table+"Table' onchange='retrieveColumnNameList(this.value, \""+ selectIndex +"\", \""+ mainDivID +"\", \""+parentTable+"\");'><option value=''>Select Table</option><option value='NA'>No Criteria</option>"
											+ columnListOptionTag + "<optgroup label='Tables'>";
							
							selectTag += optioNTag + "<option value='"+nameArr[0]+"'>"+nameArr[1]+"</option>";
							
							counter++;
						}else{
							console.log("inside if else counter");
							
							selectTag += "<option value='"+nameArr[0]+"'>"+nameArr[1]+"</option>";
						}
					
					}else if(tableCheck == "visitEmpty"){
						console.log("inside else if counter..visitempty");
						
						selectTag = divTag + "<select name='' id='subTableID' class='reportTableColClass form-control selectClass "+table+"Table' onchange='checkColumnType(this.value, \""+selectIndex+"\",\"" + mainDivID +"\",\"" + parentTable + "\");'><option value=''>Select Columns</option><option value='NA'>No Criteria</option>"
								   +columnListOptionTag;
					}else{
						console.log("inside else");
						var keyArr = array.Release[i].columns.split("=");
						
						//alert('joo');
						
						var columnType = array.Release[i].columnType;
						
						var fieldName = array.Release[i].fieldName;
						
						if(counter == 0){
							var optioNTag = divTag + "<select name='' id='visitSelectID' class='reportTableColClass form-control selectClass "+table+"Table' onchange='checkColumnType(this.value, \""+selectIndex+"\",\"" + mainDivID +"\",\"" + parentTable + "\");'><option value=''>Select Columns</option><option value='NA'>No Criteria</option>";
							
							selectTag += optioNTag + "<option value='"+columnType+"="+keyArr[1]+"'>"+fieldName+"</option>";
							
							counter++;
						}else{
							
							selectTag += "<option value='"+columnType+"="+keyArr[1]+"'>"+fieldName+"</option>";
						}
						
					}
					
					//Adding currently selected table name into lastTableName variable
					lastTableName = tableName;
				}
				
				if(tableCheck == "visit"){
					selectTag += "</optgroup></select></div>";
				}else{
					selectTag += "</select></div>";
				}
				
				$("#"+mainDivID).append(selectTag);

				
			}
		};
		xmlhttp.open("GET", "RetrieveColumnNameList?check="
				+ tableName + "&parentTableName="+ parentTable, true);
		xmlhttp.send();
	}
	
	var classNameCounter = 0;
	
	/* To generate new select tag on modal close */
    function retrieveSelectTableList(className, divID, parentTable, selectedValue) {
		
    	parentTable = "Patient AS pt";
		
    	classNameCounter++;
    	
    	var selectTag = "";
		var columnKey = "";
		var tableName = parentTable.split('AS');
		
		var selectTag = $("#"+divID).find("select").eq(0);
		
		parentTable = $("#"+divID).find("select").eq(0).attr("data-parent-table");
		
		var newCheck = 0;
		
		var selectClassName = className+classNameCounter;
		
		console.log("mainDIvID: "+selectedValue);
		var newDivID =divID+selectTagIndex;
		var divTag = "<div class='row' id='"+newDivID+"' style='padding-left: 30px;margin-bottom: 15px;'>"+
		               "<div class='col-md-2 col-sm-2 col-xs-12'>"+
		                  "<select name='searchCriteria' data-parent-table='"+parentTable+"' class='reportTableColClass form-control selectClass "+selectClassName+"Table' onchange='retrieveColumnNameList(this.value, 0, \""+newDivID+"\", \""+parentTable+"\");' id='searchCriteriaID'>"+
		                  $(selectTag).html();
		                  
		       divTag +=  "</select></div>"+
		              "</div>";
		              
		$(divTag).insertAfter("#"+divID);
    	
    	/* 
    	
    	
    
		if(tableNameList ==""){
			
			var selectTag = "";
			var columnKey = "";
			var tableName = parentTable.split('AS');
			
			var selectTag = $("#"+mainDIvID).find("select").eq(0);
			
			parentTable = $("#"+mainDIvID).find("select").eq(0).attr("data-parent-table");
			
			var newCheck = 0;
			
			var selectClassName = className+classNameCounter;
			
			console.log("mainDIvID: "+selectedValue);
			var newDivID =divID+selectTagIndex;
			var divTag = "<div class='row' id='"+newDivID+"' style='padding-left: 30px;margin-bottom: 15px;'>"+
			               "<div class='col-md-2 col-sm-2 col-xs-12'>"+
			                  "<select name='searchCriteria' data-parent-table='"+parentTable+"' class='reportTableColClass form-control selectClass "+selectClassName+"Table' onchange='retrieveColumnNameList(this.value, \"newCriteriaDivID"+selectTagIndex+"\", \"newColDivID"+selectTagIndex+"\", \""+selectClassName+"\", \""+parentTable+"\", \""+tableNameList+"\", \""+newDivID+"\", \"ColNewDivID"+selectTagIndex+"\");' id='searchCriteriaID'>"+
			                  $(selectTag).html();
			                  
			       divTag +=  "</select></div>"+
			                "<div class='' id='newColDivID"+selectTagIndex+"'>"+
			                "</div>"+
			                
			                "<div class='' id='ColNewDivID"+selectTagIndex+"'>"+
			                "</div>"+
			                
			                "<div class='' id='newCriteriaDivID"+selectTagIndex+"'>"+
			                "</div>"+
			              "</div>";
			              
			$(divTag).insertAfter("#"+divID);
		
		}else{
		
			var tableName = tableNameList.split(",");
			
			parentTable = $("#"+mainDIvID).find("select").eq(0).attr("data-parent-table");
			
			var newDivID =divID+selectTagIndex;
			var divTag = "<div class='row' id='"+newDivID+"' style='padding-left: 30px;margin-bottom: 15px;'>"+
			               "<div class='col-md-2 col-sm-2 col-xs-12'>"+
			                  "<select name='searchCriteria' data-parent-table='"+parentTable+"' class='reportTableColClass form-control selectClass ' onchange='retrieveColumnNameList(this.value, \"newCriteriaDivID"+selectTagIndex+"\", \"newColDivID"+selectTagIndex+"\", \""+className+"\", \""+parentTable+"\", \""+tableNameList+"\", \""+newDivID+"\", \"ColNewDivID"+selectTagIndex+"\");' id='searchCriteriaID'>"+
			                  "<option value=''>Select Table</option>";
			                  for ( var i = 0; i < tableName.length; i++) {
			  					
			                	var table = tableName[i];
			                	
			                	var tableTitle = table.split("AS");
			  						
				  				divTag += "<option value='"+table+"'>"+tableTitle[0]+"</option>";
			  				}	
			                  
			       divTag +=  "</select></div>"+
			                "<div class='' id='newColDivID"+selectTagIndex+"'>"+
			                "</div>"+
			                
			                "<div class='' id='ColNewDivID"+selectTagIndex+"'>"+
			                "</div>"+
			                
			                "<div class='' id='newCriteriaDivID"+selectTagIndex+"'>"+
			                "</div>"+
			              "</div>";
			              
			$(divTag).insertAfter("#"+divID);
		} */
		
	}
	
	
	function changeCountValue(checkboxID){
		if($("#"+checkboxID+"").is(":checked")){
			countValue = "count(*)";
		}else{
			countValue = "*";
		}
	}
	
	//function to save query
	function saveQuery(saveTitle){
		$("#saveQueryTitleID").val(saveTitle);
		$("#saveBtnID").text("Query Saved");
		$("#querySaveModal").modal("hide");
		
		saveQueryValues(saveTitle);
		//document.forms["formID"].submit();
	}
	</script>
    
    <!-- Ends -->
    
    
  </head>

  <body class="nav-md">
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
  <!-- Query save modal modal -->
  
  <div id="querySaveModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="">SAVE QUERY</h4>
      </div>
        <div class="modal-body">
          
         <div class="row" style="padding: 15px;">
         	<textarea rows="2" cols="2" id="saveTitleID" class="form-control" placeholder="Enter title for this query"></textarea>
         	<!-- <input type="text" id="saveTitleID" class="form-control" placeholder="Enter title for this query"> -->
         </div>

        </div>
        <div class="modal-footer">
        <center>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" onclick="saveQuery(saveTitleID.value);">Save & Generate</button>
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
  
 <!-- Column names add checkbox modal modal -->
  
  <div id="checkboxModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
      <div class="modal-header" align="center" style="color:black;">
          <h4 class="modal-title" id="">Select Values</h4>
      </div>
        <div class="modal-body">
          
         <div class="row" id="checkboxDivID">
         	
         </div>

        </div>
        <div class="modal-footer">
        <center>
           <button type="button" class="btn btn-success" id="checkboxSubmitID" >Ok</button>
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
                        <h3>GENERATE CLINICAL REPORTS</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                    
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
                    
                     <div class="row" id="">
                     <form action="GenerateClinicalReportForAyurved" method="POST" id="formID" style="padding: 15px;">
                     	<input type="hidden" id="saveQueryTitleID" name ="queryTitle">
                     	
                     	<div id="inputDivID"></div>
                     	
                     	<div class="row">
	                     	<div class="col-md-8">
	                     	</div>
	                     	<div class="col-md-4">
		                     	<div class="col-md-6">	<!-- SaveClinicalReportForAllRCCPatients -->
		                     		<button type="button" id="saveButtonID" disabled="disabled" class="btn btn-success" data-toggle="modal" data-target="#querySaveModal" onclick="showLoadingImg();">Save This Query</button>
		                     	</div>
		                     	
		                     	<div class="col-md-6">
		                     		<button type="button" onclick="saveQueryValues('');" id="exportButtonID" disabled="disabled" class="btn btn-primary">Export All Report</button>
		                     	</div>
		                    </div>
		                </div>
                     	<hr> 
                     	<div class="row" >
	        			  	
	        			  	<div class="collapse in" id="patientInfo1" style="margin-top:15px;"> 
			                    <div class="row" id="mainDivID" style="padding-left: 30px;margin-bottom: 15px;">
			                        <div class="col-md-2 col-sm-2 col-xs-12" id="">
			                          <s:select list="#{ }" headerKey="" data-parent-table="Patient AS pt" headerValue="Select Patient Columns" name="searchCriteria" class="reportTableColClass form-control selectClass patientClassTable" onchange="retrieveColumnNameList(this.value, 0, 'mainDivID', 'Patient AS pt');" id="selectTagID">
			                        	<s:optgroup label="Column" list="patientColumnList"></s:optgroup>
			                          	<s:optgroup label="Tables" list="tableList"></s:optgroup>
			                          </s:select>
			                        </div>
			                       
			                    </div>
			                   	
	        			  	</div>
        			  	</div>
        			  	 
        			  </form>
                     </div>
                     
                     <div class="ln_solid"></div>
                     
                     <div class="row" id="saveQueryDivID" align="right" style="display: none;">
                     	<button type="button" id="saveBtnID" class="btn btn-primary" data-toggle="modal" data-target="#querySaveModal" onclick="showLoadingImg();">Save this query</button>
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
   
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
    
    
    <!-- Datatables -->
    <script>
      $(document).ready(function() {

        $('#datatable-responsive').DataTable();

      });
    </script>
    <!-- /Datatables -->
    
    
<script>
   
    document.addEventListener('DOMContentLoaded', function() {

    	$("input[type=number]").on("keydown",function(e){
    		if (e.which === 38 || e.which === 40) {
    			e.preventDefault();
    		}
    	});
    	
        $('#sDateID1').daterangepicker({
            singleDatePicker: true,
            calender_style: "picker_3"
          }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
          
          $('#eDateID1').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_3"
            }, function(start, end, label) {
              console.log(start.toISOString(), end.toISOString(), label);
            });  
      });
   
    
    /* To save query values using className on clicking save button */
    function saveQueryValues(queryTitle){
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
		$('a#pmID').remove();	//To disable leftmenu options patient manag
		$('a#ivntID').remove();	//To disable leftmenu options invt mang
		$('a#admID').remove();	//To disable leftmenu options adminstr
		$('a#repID').remove();	//To disable leftmenu options Reports
		
       var finalHiddenValue = "";
    	
       var finalHiddenText = "";
    	
       /* combining the checkbox values with its tableName & passing its to hidden input tag */
	   $(".reporthiddenClass").each(function(index){
	   		if($(this).val() == ""){
	   			finalHiddenValue =  $(this).attr("data-table") + "== ";
	   		}else{
	   			finalHiddenValue =  $(this).attr("data-table") + "=="  + $(this).val();	
	   		}
	   		
	   		finalHiddenText += "<input type='hidden' value='"+finalHiddenValue+"' name='selectNameArr'>";
	   });
	   
	   /* combining the From & where conditions values & passing its to hidden input tag */
	   var value = $('.reporthiddenClass');
	   var dataWhere = '';
	   	
	   $.each(value, function (index, item) {
	   		finalHiddenText += "<input type='hidden' value='"+$(this).attr("data-table")+"' name='tableNameArr'>";
	   		
			dataWhere += $(this).attr("data-where");
	   }); 
	   
	   finalHiddenText += "<input type='hidden' value='"+dataWhere+"' name='initialWhere'>";
	   	
	   /* combining the selected columns values & passing its to hidden input tag */
	   $(".reportTableColClass").each(function(index){
	   		var selectedValue = $(this).val();
	   		
	   		if(selectedValue.includes('=')){
	   			
	   			finalHiddenText += "<input type='hidden' value='"+selectedValue+"' name='colNameArr'>";
	   		}else if(selectedValue == "NA"){
	   			finalHiddenText += "<input type='hidden' value='"+selectedValue+ "=" + $(this).attr("data-parent-table") +"' name='colNameArr'>";
	   			finalHiddenText += "<input type='hidden' value='' name='criteriaNameArr'>";
	   		}
	   		
	   });
	   	
	   /* combining the selected columns criteria & passing its to hidden input tag */
	   $(".reportCriteriaClass").each(function(index){
	   		var fkeyAttr = $(this).attr("data-fkeyattr");
	   		
	   		if(typeof fkeyAttr === "undefined"){
	   			fkeyAttr = "";
	   		}
	   		
	   		finalHiddenText += "<input type='hidden' value='"+$(this).val()+"___"+fkeyAttr+"' name='criteriaNameArr'>";
	   });
	   	
	   /* combining the searchText with its selectedTableValue as well as selectedValue & passing its to hidden input tag */ 
	   $(".reportSearchTextClass").each(function(index){
	   		var selectedSearch = $(this).val();
	   		var selectedVal = $(this).attr("data-text");
	   		var selectedTableVal = $(this).attr("data-table");
	   	
	   		finalHiddenText += "<input type='hidden' value='"+selectedTableVal+"---"+selectedVal+"---"+$(this).val()+"' name='searchTextArr'>";
	   });
	   	
	   $("#inputDivID").html(finalHiddenText);
	   	
	  	//alert("selectedCriteria: "+finalHiddenText);
	  	
	   	document.forms["formID"].submit();
	}
        
	    /* To append checkBox Values to HiddenInaputID  */
	    function addValues(className, hiddenID, divID, parentTable, selectIndex, hiddenClass, displaySearchFieldID, dataType, searchDivClassName, selectedValue, fKeyAttribute, totalIndexNo){
	    	var checkBoxValue = [];
	    	$("."+className+"checkbox:enabled:checked").each(function(){
	    		console.log("Checked values: "+$(this).val());
				checkBoxValue.push($(this).val());
		  	});
	    	
	    	$("#"+hiddenID).val(checkBoxValue);
	    	
	    	$("#"+hiddenID).attr("data-table", parentTable);
	    	
	    	//$("."+className+"Table").attr('disabled',true);
	    	
	    	//totalIndexNo = parseInt(totalIndexNo) - 1;
	    	
	    	$("#"+divID).find("select").not(".reportCriteriaClass").attr('disabled',true);
	    	
	    	/* for(var i = 0; i <= totalIndexNo; i++){
	    		var selectTag = $("#"+divID).find("select").eq(i);
		    	
		    	$(selectTag).attr('disabled',true); 
	    	} */
	        
	       	$("#"+displaySearchFieldID).attr("onchange", "editDisplaySearchFields(this.value, \""+divID+"\",\""+dataType+"\", \""+className+"\",\""+searchDivClassName+"\",\""+parentTable+"\", \""+selectedValue+"\", \""+fKeyAttribute+"\",\""+totalIndexNo+"\");");
	    	$("#addImageID").attr("src", "images/presc1.png");
	    	$("#addImageID").attr("id", "editImageID"+selectTagIndex+"");
	    	$("#editImageID"+selectTagIndex+"").attr("onclick", "editQueryRow(\""+divID+"\",\""+selectIndex+"\",\""+className+"\",\""+parentTable+"\", \""+hiddenID+"\");");
	    	$("#editImageID"+selectTagIndex+"").attr("alt","Edit Query");
	    	$("#editImageID"+selectTagIndex+"").attr("title", "Edit Query");
	    		
	    	var imgTag = "<img src='images/delete.png' id='deleteImageID"+selectTagIndex+"' onclick='deleteQueryRow(\""+divID+"\", \""+hiddenClass+"\", \""+hiddenID+"\");' style='height:24px;margin-top:5px;cursor: pointer;' alt='Delete Row' title='Delete Row' />";
	    	
	    	$(imgTag).insertAfter("#editImageID"+selectTagIndex+"");
	    	
	    	$("#exportButtonID").attr('disabled',false);
	    	$("#saveButtonID").attr('disabled',false);
	    	 
	    	var selectList = className.split('ClassTable');
	    	
	    	firstWhereCondition = "";
	    	
	    	if(selectedValue != "NA"){
	    		retrieveSelectTableList(className, divID, parentTable, selectedValue);
	    	}
	    	
	    	$("#checkboxModal").modal("hide");
	    }
	    
	    /* To update checkbox values on edit button click */
	    function editQueryRow(mainDivID, selectIndex, className, parentTable, hiddenID){
	    	var finalOptionVal = "";
			
	    	var hiddenCheckValue = "";
	    	
	    	var hiddenIDCheckValue = $("#"+hiddenID).val();
	    	
	    	var rowSelectedValArry = hiddenIDCheckValue.split(",");
			
			if(parentTable == "Patient AS pt"){
				
				$("#"+mainDivID+" select").eq(selectIndex).find("option").each(function(){
					
					var optionVal = $(this).val();
					var optionText = $(this).html().trim();
					
					console.log("hiddenCheckValue: "+hiddenIDCheckValue+"--"+hiddenCheckValue);
					
					if(optionVal.includes("=")){
						var optionValue = optionVal.split("=");
						
						if(rowSelectedValArry.includes(optionValue[1])){
							
							finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
							"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' checked='checked'  class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
			
						}else{
						
							finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
							"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
						}	
					}
					
					$("#"+mainDivID).find("select").not(".reportCriteriaClass").attr('checked',true);
				});
							
				$("#checkboxDivID").html(finalOptionVal);
				$("#checkboxModal").modal("show");
				
				$("#checkboxSubmitID").attr("onclick", "editValues(\""+className+"\", \""+hiddenID+"\");");
				
			}else{
				
				for(var i = 0;i <= parseInt(selectIndex); i++){
					
					$("#"+mainDivID+" select").eq(i).find("option").each(function(){
							
						var optionVal = $(this).val();
						var optionText = $(this).html().trim();
						
						var aliasName = parentTable.split("AS");
						
						console.log("hiddenCheckValue: "+hiddenIDCheckValue+"--"+hiddenCheckValue);
						
						if(optionVal.includes("=") && !optionVal.startsWith("=")){
							
							var optionValue = optionVal.split("=");
						
							if(rowSelectedValArry.includes(optionValue[1])){
								
								finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
								"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' checked='checked'  class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
				
							}else{
							
								finalOptionVal = finalOptionVal + "<div class='col-md-3'><label>"+
								"<input type='checkbox' name ='checkbox' value ='"+optionValue[1]+"' id='checkBoxID' class='"+className+"checkbox' >&nbsp;"+optionText+"</label></div>";
							}	
						}
					});
				}
				
				$(".reporthiddenClass").each(function(){
					if($(this).val() != ""){
						hiddenCheckValue = hiddenCheckValue + "," +$(this).val();
					}
				});
				
				if(hiddenCheckValue.startsWith(",")){
					hiddenCheckValue = hiddenCheckValue.substr(1);
				}
				
				var hiddenValArr = hiddenCheckValue.split(",");
				
				$("#checkboxDivID").html(finalOptionVal);
				$("#checkboxModal").modal("show");
				
				for(var i = 0; i < hiddenValArr.length; i++){
			
					if(!rowSelectedValArry.includes(hiddenValArr[i])){
					
						$("input:checkbox[value='"+hiddenValArr[i]+"']").prop('disabled',true);
						$("input:checkbox[value='"+hiddenValArr[i]+"']").attr('checked',true);
					}
				}
				
				$("#checkboxSubmitID").attr("onclick", "editValues(\""+className+"\", \""+hiddenID+"\");");
			}
	    }
	    
	    
	 /* updating updated chwckbox values to hiddenID */
	 function editValues(className, hiddenID){   
	    var checkBoxValue = [];
	    	$("."+className+"checkbox:enabled:checked").each(function(){
	    		
				checkBoxValue.push($(this).val());
		  	});
	    	
	    	$("#"+hiddenID).val(checkBoxValue);
	    	
	    	$("#checkboxModal").modal("hide");
	 }
	    
	 /* Delete the current row */
	 function deleteQueryRow(divID, hiddenClass, hiddenID){
		 
		 if(confirm("Are you sure you want to delete this row?")){
			 
			 var hiddenValue = $("#"+hiddenID).val();
			 var hiddenDataWhere = $("#"+hiddenID).attr("data-where");
			 var hiddenDataTable = $("#"+hiddenID).attr("data-table");
			 var hiddenClassNew = $("#"+hiddenID).attr("class");
			
			 $("#"+divID).remove();	
				
			 if (!$("#"+hiddenID).length){
					
					console.log("inside div");
					
					$("."+hiddenClass).val(hiddenValue);
					
					$("."+hiddenClass).eq("0").attr("id", hiddenID);
					$("."+hiddenClass).eq("0").attr("data-where", hiddenDataWhere);
					$("."+hiddenClass).eq("0").attr("data-table", hiddenDataTable);
					$("."+hiddenClass).eq("0").attr("class", hiddenClassNew);
					
				}
			 
			 if(!$(".reporthiddenClass").length){
				 console.log();
				 $("#exportButtonID").attr('disabled', true);
			     $("#saveButtonID").attr('disabled', true);
			 }else{
				 $("#exportButtonID").attr('disabled', false);
			     $("#saveButtonID").attr('disabled',  false);
			 }
			 	
		} 
	 }
	 
</script>
	  
  </body>
</html>