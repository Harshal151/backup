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

    <title>Edit Existing IPD Visit | E-Dhanvantari</title>

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
		
		.dojoComboBox{
			width:100%;
		}
		
		.dojoComboBoxOuter{
			width: 40%;
		}
		
		.dj_khtml {
			width: 40%;
		}
		
		.dj_safari{
			width: 40%;
		}
		
		.ctnTextarea {
		  resize: none;
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
		
		LinkedHashMap<String, String> frequencyList = configXMLUtil1.getFrequencySortedListList(form.getPracticeID());
		
		String fullName = form.getFullName();
	
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
		
		PatientDAOInf patientDAOInf = new PatientDAOImpl();
		
		HashMap<Integer, String> IPDChargesList = patientDAOInf.retrieveIPDTarrifChargesListByRoomType(ActivityStatus.ACTIVE, patientDAOInf.retrieveIPDRoomTypeID("Operation Theatre", form.getPracticeID()));
				
	%>
    <!-- Ends -->
    
    
    <%
   		String consentDocFileName = (String) request.getAttribute("consentDocFileName");
    
    	String consentDocFile = (String) request.getAttribute("consentDocFile");
    
    	List<PatientForm> LabInvastigationList = (List<PatientForm>) request.getAttribute("LabInvastigationList");
    	
	%>
    
	<%
		String consentTextDB1 = (String) request.getAttribute("consentText1");
	
		String consentTextDB1Final = "";
		
		if (consentTextDB1 == null || consentTextDB1 == "") {
	
			consentTextDB1 = "";
		} else {
			
			String[] array = consentTextDB1.split(" ");
	
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
	
	
					consentTextDB1Final += fieldValueTemp
							.replace(
									fieldValueTemp,
									"<input type='text' class='form-control'  name='"+ idValue +"' id='"+ idValue +"' placeholder='' autofocus='' style='width: 20%;margin-top:15px;margin-bottom:15px;display:inline-block;' value='"
											+ fieldValue + "'> ") + " ";
	
				} else if (array[i].contains("\n")) {
					consentTextDB1Final += array[i] + "<br>";
				} else if (array[i].contains("\t")) {
					consentTextDB1Final += array[i]
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				} else {
					consentTextDB1Final += array[i] + " ";
				}
			}
		}
	%>
	
	
	<%
		String consentTextDB2 = (String) request.getAttribute("consentText2");
	
		String consentTextDB2Final = "";
		
		if (consentTextDB2 == null || consentTextDB2 == "") {
	
			consentTextDB2 = "";
		} else {
			
			String[] array = consentTextDB2.split(" ");
	
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
	
	
					consentTextDB2Final += fieldValueTemp
							.replace(
									fieldValueTemp,
									"<input type='text' class='form-control'  name='"+ idValue +"' id='"+ idValue +"' placeholder='' autofocus='' style='width: 20%;margin-top:15px;margin-bottom:15px;display:inline-block;' value='"
											+ fieldValue + "'> ") + " ";
	
				} else if (array[i].contains("\n")) {
					consentTextDB2Final += array[i] + "<br>";
				} else if (array[i].contains("\t")) {
					consentTextDB1Final += array[i]
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				} else {
					consentTextDB2Final += array[i] + " ";
				}
			}
		}
		
	%>
	
	
	<%
		String consentTextDB3 = (String) request.getAttribute("consentText3");
	
		String consentTextDB3Final = "";
		
		if (consentTextDB3 == null || consentTextDB3 == "") {
	
			consentTextDB3 = "";
		} else {
			
			String[] array = consentTextDB3.split(" ");
	
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
	
	
					consentTextDB3Final += fieldValueTemp
							.replace(
									fieldValueTemp,
									"<input type='text' class='form-control'  name='"+ idValue +"' id='"+ idValue +"' placeholder='' autofocus='' style='width: 20%;margin-top:15px;margin-bottom:15px;display:inline-block;' value='"
											+ fieldValue + "'> ") + " ";
	
				} else if (array[i].contains("\n")) {
					consentTextDB3Final += array[i] + "<br>";
				} else if (array[i].contains("\t")) {
					consentTextDB1Final += array[i]
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				} else {
					consentTextDB3Final += array[i] + " ";
				}
			}
		}
		
	%>
	
	
	
	<%
	
		String ipdVisitPage = (String) request.getAttribute("display");
	
		String renderCheck = (String) request.getAttribute("renderCheck");
	
		if(ipdVisitPage == null || ipdVisitPage == ""){
			ipdVisitPage = "none";
		}else{
	
	%>
	
	<script>
	document.addEventListener('DOMContentLoaded', function() {
	
		$('.span_11 a[href="#visit"]').tab('show');
	
	});
	</script>
	
	<%
		}
	%>
	
	<%
		String pdfOutFIleName1 = (String) request.getAttribute("PDFOutFileName");
		System.out.println("IPD PDF out file path ::: "+pdfOutFIleName1);
		
		 System.out.println("........pdfOutFIleName1........"+pdfOutFIleName1);
		 
		if(pdfOutFIleName1 == null || pdfOutFIleName1 == ""){
			
			pdfOutFIleName1 = "dummy";
			
		}else{
			
			if(pdfOutFIleName1.contains("\\")){
				
				pdfOutFIleName1 = pdfOutFIleName1.replaceAll("\\\\", "/");
			}
		}
	
		if(pdfOutFIleName1!="dummy"){ System.out.println("........inside billing pdf download........"); %>
		<script type="text/javascript">
		
		  document.addEventListener('DOMContentLoaded', function() {
			  OPDPdfAction();
		  });
		  
		</script>
	<%  }  %>
		
	<!-- Consent submit -->
	
	<script type="text/javascript">
	
		var popup;
		function OPDPdfAction(){
			popup = window.open('PDFDownload?pdfOutPath=<%=pdfOutFIleName1%>',"Popup", "width=700,height=700");
			popup.focus();
			popup.print();
		}

		function submitConsent(){
			getConsent2();
			document.forms["OPDForm"].action = "PrintConsent";
			document.forms["OPDForm"].submit();
		}
	
		function printIPDForm(){
			
			document.forms["OPDForm"].action = "UpdatePrintIPDForm";
			document.forms["OPDForm"].submit();
		}
	
		function updateConsent(){
			getConsent2();
			document.forms["OPDForm"].action = "UpdateConsent";
			document.forms["OPDForm"].submit();
		}
	
		function submitIPDForm(){
			document.forms["OPDForm"].action = "UpdateIPDVisit";
			document.forms["OPDForm"].submit();
		}
		
	</script>

	<!-- Ends -->
	
	<style type="text/css">
		.dojoComboBox{
		width:25px;
		height:25px;
		}
		
		span.required {
	   color: red !important;
	}
	
	</style>

	
	<script type="text/javascript">
		function getConsent(){
			
			 	var value1 = document.getElementById("ID1").value;
			    var value2 = document.getElementById("ID2").value;
			    var value3 = document.getElementById("ID3").value;
			    var value4 = document.getElementById("ID41").value;
			    var value5 = document.getElementById("ID4").value;
	
			    var value6 = document.getElementById("ID1").value;
			    var value7 = document.getElementById("ID2").value;
			    var value8 = document.getElementById("ID3").value;
			    var value9 = document.getElementById("ID41").value;
			    var value10 = document.getElementById("ID4").value;
	
			    if(value6.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        value6 = value6.replace(regExp, "_");
			    }else{
			    	value6 = value6;
			    }
	
			    if(value7.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        value7 = value7.replace(regExp, "_");
			    }else{
			    	value7 = value7;
			    }
	
			    if(value8.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        value8 = value8.replace(regExp, "_");
			    }else{
			    	value8 = value8;
			    }
	
			    if(value9.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        value9 = value9.replace(regExp, "_");
			    }else{
			    	value9 = value9;
			    }
	
			    if(value10.indexOf(" ") != "-1"){
			        var regExp = new RegExp(' ','g');
			        value10 = value10.replace(regExp, "_");
			    }else{
			    	value10 = value10;
			    }
	
			    var consentText1 = "माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना "+value1+" हा आजार असुन  त्यासाठी माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांवर  "+value2+" ही शस्त्रक्रिया / तपासणी करून घेण्यास मी तयार अहे. त्याकरिता मी "+value3+" संमती देत आहे. "+value4+" \n\n1) माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना झालेल्या आजाराबद्दल / त्यावरिल उपचार पद्धती आणि ऑपरेशनच्या आवश्यकतेबद्दल डॉ.  यांनी मला पूर्ण माहिती दिली आहे. हे ऑपरेशन करताना, केल्याने, न केल्यास संभावणाऱ्या परिणामांबद्दल व धोक्याबद्दल मला पूर्ण कल्पना देण्यात आली आहे. \n\n2) कोणतेही ऑपरेशन संपूर्णपणे सुरक्षित नसते. ऑपरेशनसाठी वापरण्यात येणारी औषधे व इंजेक्शन तसेच डोळ्यात घालण्यात येणारी औषधे व भुल देण्यासाठी वापरण्यात येणारी औषधे यामुळे पेशंटला आकस्मिक त्रास वा रिअॅक्शन येऊन गंभीर स्वरूपाचा धोका होऊ शकतो याची मला कल्पना देण्यात आली आहे. \n\n 3) पेशंटला पूर्वी "+value5+" या औषधांची अॅलर्जी / रिअॅक्शन आली होती / नव्हती याची माहिती मी डॉक्टरांना दिली आहे. \n\n4) ऑपरेशन / तपासणी / उपचार करताना काही कारणांमुळे ऑपरेशन / भुल यामध्ये काही बदल करावे लागले तर त्यासाठी माझी संमती गृहित आहे व त्यासंबंधी मला कल्पना देण्यात आली आहे.\n\nवरील सर्व मजकूर मी वाचला आहे / मला वाचून दाखविण्यात आला आहे./  मला तो समजला आहे व त्यास माझी पूर्ण मान्यता आहे." ;
	
			    var consentTextDatabase = "माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना '"+value6+"@ID1' हा आजार असुन  त्यासाठी माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांवर  '"+value7+"@ID2' ही शस्त्रक्रिया / तपासणी करून घेण्यास मी तयार अहे. त्याकरिता मी '"+value8+"@ID3' संमती देत आहे. '"+value9+"@ID41' \n\n1) माझ्या / वरिल पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना झालेल्या आजाराबद्दल / त्यावरिल उपचार पद्धती आणि ऑपरेशनच्या आवश्यकतेबद्दल डॉ.  यांनी मला पूर्ण माहिती दिली आहे. हे ऑपरेशन करताना, केल्याने, न केल्यास संभावणाऱ्या परिणामांबद्दल व धोक्याबद्दल मला पूर्ण कल्पना देण्यात आली आहे. \n\n2) कोणतेही ऑपरेशन संपूर्णपणे सुरक्षित नसते. ऑपरेशनसाठी वापरण्यात येणारी औषधे व इंजेक्शन तसेच डोळ्यात घालण्यात येणारी औषधे व भुल देण्यासाठी वापरण्यात येणारी औषधे यामुळे पेशंटला आकस्मिक त्रास वा रिअॅक्शन येऊन गंभीर स्वरूपाचा धोका होऊ शकतो याची मला कल्पना देण्यात आली आहे. \n\n 3) पेशंटला पूर्वी '"+value10+"@ID4' या औषधांची अॅलर्जी / रिअॅक्शन आली होती / नव्हती याची माहिती मी डॉक्टरांना दिली आहे. \n\n4) ऑपरेशन / तपासणी / उपचार करताना काही कारणांमुळे ऑपरेशन / भुल यामध्ये काही बदल करावे लागले तर त्यासाठी माझी संमती गृहित आहे व त्यासंबंधी मला कल्पना देण्यात आली आहे.\n\nवरील सर्व मजकूर मी वाचला आहे / मला वाचून दाखविण्यात आला आहे./  मला तो समजला आहे व त्यास माझी पूर्ण मान्यता आहे." ;
	
			    document.getElementById("consentText").value = consentText1;
	
			    document.getElementById("consentTextDB").value = consentTextDatabase;
	
			 
				    
		}
	
		function getConsent1(){
			
	
			var value1 = document.getElementById("ID5").value;
	
			var value2 = document.getElementById("ID5").value;
	
			if(value2.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value2 = value2.replace(regExp, "_");
		    }else{
		    	value2 = value2;
		    }
	
			var consentText1 = "मला माझ्या स्वतःच्या / पेशंटच्या उजव्या / डाव्या डोळ्याच्या आजाराबद्दल व शस्त्रक्रियेबाबत डॉक्टरांनी मला सविस्तर माहिती दिली आहे. शस्त्रक्रियेनंतर मागील पडद्यावरील (दृष्टीपटलावरील) आजारामुळे / मोतीबिंदु अधिक पिकल्यामुळे  / अपघाताने झालेल्या विकृतीमुळे / काचबिंदुमुळे / जंतुसंसर्गामुळे / जन्मजात वा आघाताने आलेल्या तिरळेपणामुळे / बुबुळाच्या आजारामुळे / इतर शरीरदोषामुळे ("+value1+") दृष्टीमध्ये किती प्रमाणात सुधारणा होईल हे सांगता येत नाही याची कल्पना मला डॉक्टरांनी दिली आहे व ते मला मान्य आहे. तरी संभाव्य दृष्टीहानीसाठी मी रुग्णालय व्यवस्थापन, स्टाफ किंवा डॉक्टर यांना जबाबदार धरणार नाही." ;
	
			var consentTextDatabase = "मला माझ्या स्वतःच्या / पेशंटच्या उजव्या / डाव्या डोळ्याच्या आजाराबद्दल व शस्त्रक्रियेबाबत डॉक्टरांनी मला सविस्तर माहिती दिली आहे. शस्त्रक्रियेनंतर मागील पडद्यावरील (दृष्टीपटलावरील) आजारामुळे / मोतीबिंदु अधिक पिकल्यामुळे  / अपघाताने झालेल्या विकृतीमुळे / काचबिंदुमुळे / जंतुसंसर्गामुळे / जन्मजात वा आघाताने आलेल्या तिरळेपणामुळे / बुबुळाच्या आजारामुळे / इतर शरीरदोषामुळे ( '"+value2+"@ID5' ) दृष्टीमध्ये किती प्रमाणात सुधारणा होईल हे सांगता येत नाही याची कल्पना मला डॉक्टरांनी दिली आहे व ते मला मान्य आहे. तरी संभाव्य दृष्टीहानीसाठी मी रुग्णालय व्यवस्थापन, स्टाफ किंवा डॉक्टर यांना जबाबदार धरणार नाही." ;
	
		    document.getElementById("consentText1").value = consentText1;
	
		    document.getElementById("consentText1DB").value = consentTextDatabase;
	
		    
		}
	
		function getConsent2(){
			
			var value1 = document.getElementById("ID6").value;
		    var value2 = document.getElementById("ID7").value;
		    var value3 = document.getElementById("ID8").value;
		    var value4 = document.getElementById("ID9").value;
		    var value5 = document.getElementById("ID10").value;
		    var value6 = document.getElementById("ID11").value;
	
	
		    var value7 = document.getElementById("ID6").value;
		    var value8 = document.getElementById("ID7").value;
		    var value9 = document.getElementById("ID8").value;
		    var value10 = document.getElementById("ID9").value;
		    var value11 = document.getElementById("ID10").value;
		    var value12 = document.getElementById("ID11").value;
	
	
		    if(value7.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value7 = value7.replace(regExp, "_");
		    }else{
		    	value7 = value7;
		    }
		    
		    if(value8.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value8 = value8.replace(regExp, "_");
		    }else{
		    	value8 = value8;
		    }
	
		    if(value9.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value9 = value9.replace(regExp, "_");
		    }else{
		    	value9 = value9;
		    }
	
		    if(value10.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value10 = value10.replace(regExp, "_");
		    }else{
		    	value10 = value10;
		    }
	
	
		    if(value11.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value11 = value11.replace(regExp, "_");
		    }else{
		    	value11 = value11;
		    }
	
	
		    if(value12.indexOf(" ") != "-1"){
		        var regExp = new RegExp(' ','g');
		        value12 = value12.replace(regExp, "_");
		    }else{
		    	value12 = value12;
		    }
		    
	
		    var consentText1 = "1) माझ्या / वरील पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना पूर्वीपासूनच  "+value1+" हा विकार आहे. तसेच माझ्या / वरील पेशंटच्या  "+value6+" या आजारामुळे माझ्या / वरील पेशंटच्या डोळ्यांवर परिणाम झाला / नाही. त्यामुळे पूर्वीच माझ्या / वरील पेशंटच्या उजव्या / डाव्या दोन्ही डोळ्यांची दृष्टी अधू झाली आहे./ गेली आहे. याची मला कल्पना आली आहे.\n\n 2) या ऑपरेशन नंतर माझ्या / वरील पेशंटच्या उजव्या / डाव्या दोन्ही डोळ्यांची दृष्टी सुधारणार नाही याची मला पूर्ण कल्पना आहे. तरी देखील माझ्या / वरील पेशंटच्या डोळ्यांवर  "+value3+" ही शस्त्रक्रिया करण्यास मी संमती देत आहे.\n\n 3) ह्या शस्त्रक्रियेनंतर माझ्या / वरील पेशंटचा डोळा विद्रुप होऊ शकतो व त्याचा लहान होऊ शकतो याची मला डॉक्टरांनी पूर्ण कल्पना दिली आहे. \n\n4) या शस्त्रक्रियेमध्ये मी माझा / वरील पेशंटचा उजवा / डावा डोळा काढून टाकण्यास अर्थात "+value4+"  ही शस्त्रक्रिया करण्यास डॉ. "+value5+" आणि भूलतज्ञ डॉ. "+value2+" यांना परवानगी देत आहे. " ;
	
		    var consentTextDatabase = "1) माझ्या / वरील पेशंटच्या उजव्या / डाव्या / दोन्ही डोळ्यांना पूर्वीपासूनच  '"+value7+"@ID6' हा विकार आहे. तसेच माझ्या / वरील पेशंटच्या  '"+value12+"@ID11' या आजारामुळे माझ्या / वरील पेशंटच्या डोळ्यांवर परिणाम झाला / नाही. त्यामुळे पूर्वीच माझ्या / वरील पेशंटच्या उजव्या / डाव्या दोन्ही डोळ्यांची दृष्टी अधू झाली आहे./ गेली आहे. याची मला कल्पना आली आहे.\n\n 2) या ऑपरेशन नंतर माझ्या / वरील पेशंटच्या उजव्या / डाव्या दोन्ही डोळ्यांची दृष्टी सुधारणार नाही याची मला पूर्ण कल्पना आहे. तरी देखील माझ्या / वरील पेशंटच्या डोळ्यांवर  '"+value9+"@ID8' ही शस्त्रक्रिया करण्यास मी संमती देत आहे.\n\n 3) ह्या शस्त्रक्रियेनंतर माझ्या / वरील पेशंटचा डोळा विद्रुप होऊ शकतो व त्याचा लहान होऊ शकतो याची मला डॉक्टरांनी पूर्ण कल्पना दिली आहे. \n\n4) या शस्त्रक्रियेमध्ये मी माझा / वरील पेशंटचा उजवा / डावा डोळा काढून टाकण्यास अर्थात '"+value10+"@ID9'  ही शस्त्रक्रिया करण्यास डॉ. '"+value11+"@ID10' आणि भूलतज्ञ डॉ. '"+value8+"@ID7' यांना परवानगी देत आहे. " ;
	 
		    document.getElementById("consentText2").value = consentText1;
	
		    document.getElementById("consentText2DB").value = consentTextDatabase;
		   
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
	//session.setMaxInactiveInterval(maxSessionTimeout);
	
	//redirecting to login page on session timeout.
	//int timeout = session.getMaxInactiveInterval();
	//response.setHeader("Refresh", timeout + "; URL = index.jsp?message=Your session has been expired. Please login again.");
	
	ClinicDAOInf clinicDAOInf = new ClinicDAOImpl();
	//retrieving visit type name by ID
	String visitType = clinicDAOInf.retrieveVisitTypeNameByID(form.getVisitTypeID());
    
    %>
    <!-- Ends -->
    
    <script type="text/javascript">
	
		/* function changeTotalBill(totalbillID, charge){
			$("#"+totalbillID+"").val(charge);
		} */
		
		function changeTotalBill(totalbillID, charge){
			
			var total = 0;
			$(".billingRateClass").each(function(){
				var rate = 0;
				if($(this).val() == ""){
					rate = 0;
				}else{
					rate = parseFloat($(this).val());
				}
				total = parseFloat(total) + rate
			});
			//console.log(total)
			$("#"+totalbillID+"").val(total);
			
			changeBalancePayment(advPaymentID.value, total);
		}
		
		// Change balance payment 

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

		function checkCheckbox(btnID) {
			console.log("btnID value is: "+btnID);
			var checkboxLength = $('input[name="paymentType"]:checkbox:checked').length;
			if(checkboxLength>0){
				
				$('.checkboxClass').prop("required", false);
				document.forms["OPDBILLForm"].action = "EditIPDOpthBill";
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
    
    <% String paymentType = (String) request.getAttribute("paymentType");
    
    	System.out.println("paymentType..."+paymentType);
		
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

<!-- Ends -->
    
    <script type="text/javascript">

		function confirmDelete(count){
			if (confirm("Are you sure you want to delete this row?")) {
				
				$("#cmplntTRID"+count+"").remove();
			}
			
		}
	
		function confirmIPDContinuationSheetDelete(contID,visitID){
			if (confirm("Are you sure you want to delete?")) {
				deleteIPDContinuationSheet(contID, visitID);
			}
		}
	
	</script>
	
	
	<%

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = format.format(date);
		
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
		String currentTime = format1.format(date);
		
	%>
	
	<%
    	String tabs = "IPD Visit,Prescription,Billing";
    %>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    
    	document.addEventListener('DOMContentLoaded', function() {
    
	    <%
	    
	  		//Adding div ID values which is to be associated to tabs
			HashMap<String, String> idMap = new HashMap<String, String>();
		
		    idMap.put("IPD Visit", "IPDVisit");
			idMap.put("Prescription", "prescription");
			idMap.put("Billing", "billing");
			/* idMap.put("Report", "labRep");
			idMap.put("Medical Certificate", "medCerti");
			idMap.put("Referral Letter", "refLetter"); */
			
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
		String prescTabCheck = (String) request.getAttribute("prescTabCheck");
	
		if(prescTabCheck == null || prescTabCheck == ""){
			prescTabCheck = "disable";
		}else{
	
	%>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	
		$('.span_11 a[href="#prescription"]').tab('show');
			
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
    
    
    <!-- Add IPD complaint values -->
    
    <script type="text/javascript">
    
    	var counterNew = 1;
    	
		function addIPDComplaints(ODRt, OSLt) {
			
			var trID = "COTRID"+counterNew;
        	var tdID = "COTDID"+counterNew;
        	
			var trTag = "<tr id="+trID+" style='font-size:14px;'>"+
			"<td>"+ODRt+"<input type='hidden' name='ODRt' value='"+ODRt+"'></td>"+
			"<td>"+OSLt+"<input type='hidden' name='OSLt' value='"+OSLt+"'></td>"+
			"<td><img src='images/delete_icon_1.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeCOTR(\"" + trID +"\",\""+tdID+"\");'/></td>"+
			"</tr>";

			$(trTag).insertBefore($('#ComplaintTRID'));
			
			//setting fields to its default value
			$("#ODRt").val("");
			$("#OSLt").val("");
		}
		
		//Remove tr function
		function removeCOTR(trID, tdID){
        	
        	if(confirm("Are you sure you want to remove this row?")){

            		$("#"+trID+"").remove();

            	}
        	
        }
		
	</script>
	
	<!-- Ends -->
	
	<%
		String lastEneteredVisitList = (String) request.getAttribute("lasteEnteredVisitList");
	
		String lasteEnteredPresList = (String) request.getAttribute("lasteEnteredPresList");
		
		String lasteEnteredBillList = (String) request.getAttribute("lasteEnteredBillList");
		
		String consentText123 = (String) request.getAttribute("consentText");
	%>
   
  <script type="text/javascript">
    	function consentPicShow(){    
			$('#consentID1').hide();
			$('#consentDocumentDivID').show();
			$('#consentDocClickID').click();
    	}
    	
		function checkMandatoryField(){
			
			if($('#OPDForm')[0].checkValidity()){
				$('.span_11 a[href="#ipd1"]').tab('show');
				
				$("#visit").removeClass('active');
			}else{
				//$('#OPDForm').find("#saveBtnID").click();
				$('#OPDForm')[0].reportValidity();
				alert("Please enter data for the mandatory fields")
			}
			
		}
    </script>
    
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
		var frequency3 = frequency2.replaceAll(',','<br>');
		
		var noOfDays2 = noOfDays.join();
		var noOfDays3 = noOfDays2.replaceAll(',','<br>');
		
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
		$("#category1").val("");
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
						$('.actionMessage span').text('Prescription deleted successfully.');
						//document.getElementById("successMSG").innerHTML = "Prescription deleted successfully.";
						$("#"+trID).remove();
					}
					
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
				   +"<td style='text-align:center'>"+frequency+"<input type='hidden' name='newFrequency' id='newFrequency' value='"+frequency+"'></td>"
				   +"<td style='text-align:center'>"+noOfDays+"<input type='hidden' name='newNoOfDays' id='newNoOfDays' value='"+noOfDays+"'></td>"
				   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeFrequencyTR(\""+TRID+"\", \""+frequencyStringVal+"\");'/></td>"
				   +"</tr>";
			
		frequencyString += frequencyStringVal;
		
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
	
	<script type="text/javascript">
		function updatePrescription(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			
			document.forms["genPhyPrescID"].action = "EditIPDPrescription";
			document.forms["genPhyPrescID"].submit();
			// $("#savePrescID").attr("disabled", "disabled");
		}
		
		function setBtnValue(action, btnID, formID){
			$("#"+btnID).val(action)
			document.forms[formID].submit();
		}
		
		function submitSaveForm(){
			document.forms["OPDForm"].action = "UpdateIPDVisit";
			document.forms["OPDForm"].submit();
		}
	</script>
	
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
	
	<!-- For Billing charges -->
	<script type="text/javascript">
		var billinItemTRCounter = 1;
	
		function addBillintItemRow(charge, chargeSelectorID, rate){
			if(charge == "") {
				alert("Please select charge")
			} else if(rate == "") {
				alert("Please enter rate")
			} else{
				
				var TRID = "newBillinItemTRID"+billinItemTRCounter;
				
				var chargeText = $("#billItemChargesID option:selected").text().trim();
				
				var trTag = "<tr style='font-size: 14px;' id='"+TRID+"'>"
						   +"<td style='text-align:center'>"+chargeText+"<input type='hidden' name='itemNameArr' value='"+chargeText+"'><input type='hidden' name='itemIDArr' value='0'></td>"
						   +"<td style='text-align:center'><input type='number' class='billingRateClass form-control' name='itemRateArr' value='"+rate+"' onkeyup='changeTotalBill(\"totalBill\",this.value);'></td>"
						   +"<td style='text-align:center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeBillTR(\""+TRID+"\");'/></td>"
						   +"</tr>";
				
				$(trTag).insertAfter($("#billingItemTRID"));
				billinItemTRCounter++;
				
				$("#billItemChargesID").val('').trigger('change')
				$("#billItemRateID").val("");
				
				changeTotalBill('totalBill', 0);
				
			}
		}
		
		function removeBillTR(trID){
			if(confirm("Are you sure you want to delete this row?")){
				$("#"+trID).remove();	
				
				changeTotalBill('totalBill', 0);
			}
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function removeBillTRAjax(trID, receiptItemID){
			
			if(confirm("Are you sure you want to delete this row?")){
				/* $("#"+trID).remove();	
				
				changeTotalBill('totalBill', 0); */
				
				xmlhttp.onreadystatechange = function() {
					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		
						var array = JSON.parse(xmlhttp.responseText);
						
						var status = "";
						
						for ( var i = 0; i < array.Release.length; i++) {
						
							status = array.Release[i].status;
		
						}
						
						$("#"+trID).remove();	
						
						changeTotalBill('totalBill', 0);
						
					}
				};
				xmlhttp.open("GET", "DeleteReceiptItemByID?receiptItemID="+ receiptItemID , true);
				xmlhttp.send();
			}
			
		}
		
		function setChargesRate(rate){
			
			$("#billItemRateID").val(rate)
		}
	</script>
	<!-- Billing charges end -->
	
	<script type="text/javascript">
		function openIPDChargesModal(){
			$("#IPDChargesModal").modal("show");
		}
		
		function closeOPDModal(){
			$("#ipdChargeTypeID").val("")
			$("#ipdChargesID").val("")
			
			$("#IPDChargesModal").modal("hide");
		}
		
		function addIPDCharges(chargesType, charges){
			if(chargesType == ""){
				alert("Please enter charge type")
			}else if(charges == ""){
				alert("Please enter charges")
			}else{
				addIPDCharges1(chargesType, charges);
			}
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function addIPDCharges1(chargesType, charges) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var selectTag = "<select id='billItemChargesID' class='form-control' style='width: 50%; display: inline-block;' onchange='setChargesRate(this.value)'>"+
									"<option value=''>Select Charges</option>";
					
					for ( var i = 0; i < array.Release.length; i++) {
					
						status = array.Release[i].status;
						
						selectTag += "<option value='"+array.Release[i].charges+"'>"+array.Release[i].itemName+"</option>";
	
					}
					
					selectTag += "</select>";
					
					$("#billItemChargesID").html(selectTag)
					
					$("#ipdChargeTypeID").val("")
					$("#ipdChargesID").val("")
					
					$("#IPDChargesModal").modal("hide");
					
					alert("IPD Charge added successfully.")
					
				}
			};
			xmlhttp.open("GET", "AddIPDChargesAJAX?IPDItems="+ chargesType + "&charges=" + charges , true);
			xmlhttp.send();
		}
	</script>
    
    <sx:head/>
    
  </head>

  <body class="nav-md">
  <img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
  
  <!-- Start IPD Charges Add Modal-->
  
  <div id="IPDChargesModal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick = "closeModal()">&times;</button>
          <h4 class="modal-title" id="myModalLabel" style="text-align:center">Add IPD Charges</h4>
        </div>
        <div class="modal-body">
        
        	<div class="row">
        		<div class="col-md-12 col-sm-12 col-xs-12" id="frequencyDIvID" align="center">
	        	 	<table id="frequencyTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" style="width: 70%; margin-left: 1%;">
					    <thead>
							<tr><th style="width: 55%">Charge Type</th> <th>Charges(Rs)</th></tr>
					    </thead>
					    <tbody>
			        		<tr style="font-size: 14px;" id="">
							   <td style="text-align:center">
								   <input type="text" class="form-control" name="" id="ipdChargeTypeID" placeholder="Enter value here">
							   </td>
							   <td style="text-align:center"><input class="form-control" type="number" id="ipdChargesID" name="" placeholder="Enter charges here"></td>
						   </tr>
						</tbody>
					</table>
				</div>
        	</div>
        </div>
        <div class="modal-footer" style="text-align: center;">
          <button type="button" class="btn btn-default" id="patientDetailsCloseID" onclick = "closeOPDModal()">Close</button>
          <button type="button" class="btn btn-success" id="" onclick = "addIPDCharges(ipdChargeTypeID.value, ipdChargesID.value)">Add</button>
       </div>

      </div>
    </div>
  </div>
  
  <!-- END -->
  
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
                        <h3>VIEW / EDIT EXISTING IPD VISIT</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;margin-bottom:0px;"></div>
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
						
							<font id="successMSG" style="color: green;font-size:16px;margin-left:40px;text-align: center;"> </font>
    					
    						<font id="errorMSG" style="color: red;font-size:16px;margin-left:40px;text-align: center;"></font>
    					
    						<font id="exceptionMSG" style="color: red;font-size:16px;margin-left:40px;text-align: center;"></font>
    					
		              </div>
		              
		              <s:iterator value="patientList" var="patientVat">
		              
		              <h4 style=""><b>IPD Visit For <s:property value="firstName"/>&nbsp;<s:property value="lastName"/>&nbsp;(<s:property value="patientID"/>)</b></h4>
		              
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
		             		<!-- IPD Visit -->
		             		<div class="tab-pane fade in active" id="IPDVisit">
			        
			        <form class="form-horizontal form-label-left" id="OPDForm" name="OPDForm" onsubmit="return showLoadingImg();" action="UpdateIPDVisit" method="POST" enctype="multipart/form-data">
						  <div id="myTabContent2" class="tab-content">
						  
						   <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>">
						      <input type="hidden"  name="visitID" id="visitID" value="<s:property value="visitID"/>">
						      <input type="hidden"  name="aptID" id="aptID" value="<s:property value="aptID"/>">
						      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
							  <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
						  
						    <div class="tab-pane fade in active"  id="visit">
						      
								<div class="row" style="margin-top:15px;">
									<center>
										<h4><b>CASE SHEET</b></h4>
									</center>
									
								<% if(consentDocFileName!=null){  %>
									
									<%-- <div class="col-md-2" style="padding-top: 23px;">
										<button class="btn btn-warning" type="button" id="downloadButtonID" onclick="window.location='DownloadConsent?fileName=<%=consentDocFileName%>'">Download Consent</button>
									</div> --%>
									
									<div class="col-md-8" id="consentID1" >
				                       <div class="col-md-4" style="margin-left:-10px;">
				                          <label style="padding-left: 35px;">Upload Consent Document</label>
				                          	<input  type="text" name="consentFileDBName" class="form-control" style="width:100%" value="<s:property value="consentFileDBName"/>" readonly="readonly">
				                       </div>
				                       <div class="col-md-4" style="padding-top: 23px;">
				                          <button class="btn btn-default" type="button" onclick="consentPicShow();">Upload Consent Document</button>
				                       </div>
				                   </div>
			                      
								  <div class="col-md-3" id="consentDocumentDivID" style="display: none;">
										<label style="padding-left: 35px;">Upload Consent Document</label>
										<s:file name="consentFile" class="form-control" id="consentDocClickID"></s:file>
								  </div>
									
								<%  } %>
								
								</div>
								
								<div class="row" style="margin-top:15px;">
								<div class="col-md-12" >
							    		<table border="1" width="100%">
							    		
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> Patient Name: </td>
							    				<td colspan="3"> <input type="text" style="border:none;cursor:not-allowed;"  class="form-control" readonly="readonly" value="<s:property value="firstName"/> <s:property value="middelName"/> <s:property value="lastName"/>"  name="" placeholder="Patient Name" autofocus=""></td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> Age/Gender: </td>
							    				<td colspan="3"> <input type="text" style="border:none;cursor:not-allowed;" class="form-control" readonly="readonly" value="<s:property value="age"/> <s:property value="gender"/>" placeholder="Age/Gender" autofocus=""> 
							    				<input type="hidden" name="age" value="<s:property value="age"/>"><input type="hidden" name="gender" value="<s:property value="gender"/>"> </td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> Address: </td>
							    				<td colspan="3"> <input type="text" class="form-control" style="border:none;cursor:not-allowed;" readonly="readonly" value="<s:property value="address"/>"  name="address" placeholder="" autofocus=""></td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> D.O.A & Surgery: </td>
							    				<td > <input type="text" class="form-control" style="border:none;" name="visitDate" value="<%=currentDate %>" id="single_cal3" placeholder="Visit Date" aria-describedby="inputSuccess2Status3"></td>
							    				<td style="padding-left:10px;"> D.O.A. Time: </td>
							    				<td><input type="text" class="form-control" name="admission_time" required="required" value="<s:property value="admission_time"/>" id="doa_time" placeholder="Select Time"> </td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> Diagnosis: </td>
							    				<td colspan="3" style="padding-top:10px;" class="search-container">
							    				<div class="item form-group search-container" >
							    				<div class="col-md-6 col-sm-6 col-xs-12"> 
													 <input type="text" class="form-control" name="cancerType" id="search" required="required" value="<s:property value="cancerType"/>" placeholder="Diagnosis">
												</div>
							    				</div>
												</td>
							    			</tr>
							    			
							    			<tr>
							    				<td colspan="2" style="padding-left:10px;"> Discharge Date*: </td>
							    				<td > <input type="text" class="form-control" style="border:none;" name="dateOfDischarge" value="<s:property value="dateOfDischarge"/>" id="dateOfDischarge" placeholder="Discharge Date" aria-describedby="inputSuccess2Status3"></td>
							    				<td style="padding-left:10px;"> D.O.D. Time: </td>
							    				<td><input type="text" class="form-control" name="discharge_time" value="<s:property value="discharge_time"/>" required="required" id="dod_time" placeholder="Select Time"> </td>
							    			</tr>
							    				    			
							    		</table>
							    		
							 </div>
							 </div>
							 
							 <div class="ln_solid"></div>
							 
							 <div class="row">
								    <div class="col-md-12 col-xs-12">
								     <div class="col-md-3 col-xs-3">
								      <label for="Name" style="font-size:14px;">Procedure*</label>
								    </div>
								     <div class="col-md-9">
								      <s:textarea  name="procedure" required="true" placeholder="Procedure" class="form-control" rows="3"></s:textarea>
								    </div>
								    </div>
							   </div>
								
						    <div class="row">
							      <div class="col-md-4" align="left">
							    <div class="col-md-12">
							      <label for="Name" style=" margin-top:10px;font-size:16px;"><b>Complaints Of:</b></label>
							    </div>
							    
							    </div>
							
							    <div class="col-md-4" align="left" > 
							    <div class="col-md-12">
							      
							    </div>
							    
							    </div>
							
							    <div class="col-md-4" align="left">
							    <div class="col-md-12">
							      
							    </div>
							    </div>
							    </div>
						    
						    <div class="row" style="margin-top:10px;">
						
						      <div class="col-md-12" id="IPDCOID" style="margin-left:0px;margin-right:0px;">
						                
						                        <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						                            <thead>
						
						                                <tr>
						                                    <th>OD (Rt)</th>
						                                    <th>OS (Lt)</th>
						                                    <th>Action</th>
						                                </tr>
						                             </thead>
						                               
						                             <tbody>
						                             
							                        	<s:iterator value="IPDComplaintsList" var="form">
							                        			 
							                        			 <%-- <input type="hidden" name="complaintID" id="complaintID" value="<s:property value="complaintID"/>">
							                        			 <input type="hidden" id="countID"  value="<s:property value="count"/>"> --%>
												
															<tr id="cmplntTRID<s:property value="count" />">
													
																<td><s:property value="ODRt1" /></td>
																	
																<td><s:property value="OSLt1" /></td>
																
																<td><s:a href="javascript:confirmDelete(countID.value);">
																	<img src="images/delete_icon_1.png" onmouseover="this.src='images/delete_icon_2.png'"
																		onmouseout="this.src='images/delete_icon_1.png'" alt="Remove Row"
																		title="Remove Row" style="height:24px;" />
																</s:a>
																</td>
													
															</tr>
							
														</s:iterator>
													
														<tr id="ComplaintTRID">
															
															<td><input type="text" class="form-control" name="ODRt12" id="ODRt" placeholder="OD (Rt)"></td>
															
															<td><input type="text" class="form-control" name="OSLt12" id="OSLt" placeholder="OS (Lt)"></td>
															
															<td><a onclick="addIPDComplaints(ODRt.value, OSLt.value);" > 
															  	<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
																							onmouseout="this.src='images/add_icon_1.png'" alt="Add Complaint"
																							title="Add Complaint" style="margin-top:5px;height:24px;" />
															  </a>
															</td>
															
														</tr>
							
						                            </tbody>
						                        </table>
						            </div>
						    </div>
						    
							    <div class="ln_solid"></div>
							
								<div class="row">
								    <div class="col-md-12 col-xs-12">
								     <div class="col-md-3 col-xs-3">
								      <label for="Name" style="font-size:14px;">H/O</label>
								    </div>
								     <div class="col-md-9">
								      <s:textarea  name="ho" placeholder="H/O" class="form-control" rows="3"></s:textarea>
								    </div>
								    </div>
							   </div>
							   
							   <div class="row" style="margin-top:15px;">
							    <div class="col-md-12 col-xs-12">
								     <div class="col-md-3 col-xs-3">
							      <label for="Name" style="font-size:14px;">Allergic To</label>
							    </div>
							     <div class="col-md-9">
							      <s:textarea  name="allergicTo" placeholder="Allergic To" rows="3" class="form-control"></s:textarea>
							    </div>
							    </div>
							   </div>
							   
							   <div class="ln_solid"></div>
	                      
			                      <div class="form-group">
			                        <div class="col-md-12 col-xs-12" align="center">
			                          <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary" style="width:15%">Cancel</button>
			                          <!-- <a href="#consent2" data-toggle="tab"><button class="btn btn-primary" type="button" style="width:15%" id="printButton" >Previous</button></a> -->
			                          <a href="javascript:checkMandatoryField()"  ><button class="btn btn-default"  type="button" style="width:15%" id="printButton">Next</button></a>
			                          <button class="btn btn-warning" type="button" onclick="printIPDForm();" style="width:15%">Print Blank IPD Form</button>
			                        </div>
			                      </div>
						
						      </div>
						      
						      <div class="tab-pane fade" id="ipd1" >
						      
						      <div class="row" style="margin-top: 15px;">
									<center>
										<h4><b>ON EXAM</b></h4>
									</center>
								</div>
						      
						      <div class="row" style="margin-top:15px;">
                      
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
								
									   <div class="col-md-4 col-sm-4 col-xs-5" align="left" >
									     		
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
								  				<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="visualAcuityDistOD"  class="form-control"   ></s:select>
								    		</div>
								    
								    		<div class="col-md-6">
								  				<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="visualAcuityNearOD"  class="form-control"   ></s:select>
								    		</div>
								    
								    	</div>
								
								    	<div class="col-md-4 col-sm-4" align="left">
								   				
								   			<div class="col-md-6">
								  				<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="visualAcuityDistOS"  class="form-control"  ></s:select>
								    		</div>
								    
								    		<div class="col-md-6">
								  				<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="visualAcuityNearOS"  class="form-control"   ></s:select>
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
								  				<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="pinholeVisionDistOD"  class="form-control"   ></s:select>
										    </div>
								    
										    <div class="col-md-6">
											  	<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="pinholeVisionNearOD"  class="form-control"   ></s:select>
								    		</div>
								    
								    	</div>
								
									    <div class="col-md-4 col-sm-4" align="left">
									   
									   		<div class="col-md-6">
									  			<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="pinholeVisionDistOS"  class="form-control"   ></s:select>
									    	</div>
									    
									    	<div class="col-md-6">
									  			<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="pinholeVisionNearOS"  class="form-control" ></s:select>
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
								  				<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="BCVADistOD"  class="form-control"  ></s:select>
								    		</div>
								    
								    		<div class="col-md-6">
								  				<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="BCVANearOD"  class="form-control" ></s:select>
								    		</div>
								    
								    	</div>
								
								    	<div class="col-md-4 col-sm-4" align="left">
								   			
								   			<div class="col-md-6">
								  				<s:select list="#{'6/6':'6/6', '6/9':'6/9', '6/12':'6/12', '6/18':'6/18', '6/24':'6/24', '6/36':'6/36', '6/60':'6/60'}" name="BCVADistOS"  class="form-control"   ></s:select>
								    		</div>
								    
								    		<div class="col-md-6">
								  				<s:select list="#{'N6':'N6', 'N8':'N8', 'N12':'N12', 'N18':'N18', 'N24':'N24', 'N36':'N36'}" name="BCVANearOS"  class="form-control"  ></s:select>
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
								
								    	 <div class="col-md-4 col-sm-4 col-xs-6" align="left">
								    		
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
								      			<label for="Name" style=" margin-top:10px;font-size:14px;">Cornea</label>
								    		</div>
								
									    </div>
								
									    <div class="col-md-4 col-sm-4 col-xs-6" align="left"> 
				
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
								
								    	<div class="col-md-4 col-sm-4 col-xs-6" align="left">
								    	
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
								
								    	<div class="col-md-4 col-sm-4 col-xs-6" align="left"> 
								    	
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
								
									    <div class="col-md-4 col-sm-4 col-xs-6" align="left">
										
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
								
								    	<div class="col-md-4 col-sm-4" align="left">
								    		
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
								
								    	<div class="col-md-4 col-sm-4" align="left" > 
								    		
								    		<div class="col-md-12 col-sm-12">
								        		<input type="text" class="form-control" name="axialLengthOD" value="<s:property value="axialLengthOD"/>" placeholder="Axial Length OD">
								    		</div>
								    	
								    	</div>
								
								    	<div class="col-md-4 col-sm-4" align="left">
								    		
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
		
			                      <div class="ln_solid"></div>
			                      
			                      <div class="form-group">
			                        <div class="col-md-12 col-xs-12" align="center">
			                          <a href="#visit" data-toggle="tab"><button class="btn btn-primary" type="button" style="width:15%" id="printButton"  >Previous</button></a>
			                          <a href="#ipd12" data-toggle="tab"><button class="btn btn-default" type="button" style="width:15%" id="printButton" >Next</button></a>
			                        </div>
			                      </div>
							    
						         </div>
						         
						         <div class="tab-pane fade" id="ipd12" >
						         
						         <div class="row" style="margin-top:15px;">
										<center>
											<h4><b>LAB & INVESTIGATION</b></h4>
										</center>
									 </div>
						         
							    <div class="row" style="margin-top:15px;">
							    	<div class="col-md-6">
							    		<table border="1" width="100%" style="font-size:14px;">
							    			<tr align="center">
							    				<td colspan="3"> O/E </td>
							    			</tr>
							    			
							    			<tr>
							    				<td style="padding-left:10px;"> Pulse: </td>
							    				<td colspan="2" style="padding-left:10px;"> <input type="number"   class="form-control" style="border:none;"  name="OEPulse" value="<s:property value="OEPulse"/>" placeholder="OE Pulse"> </td>
							    			</tr>
							    			
							    			<tr>
							    				<td style="padding-left:10px;"> B.P.: </td>
							    				<td colspan="2" style="padding-left:10px;">
							    				<div class="col-md-6" style="margin-left:-15px;"><input type="number" class="form-control" style="border:none;"  name="OEBPSys" value="<s:property value="OEBPSys"/>" placeholder="OE B.P. Systolic"></div>
							    				<div class="col-md-6"><input type="number"   class="form-control" style="border:none;"  name="OEBPDia" value="<s:property value="OEBPDia"/>" placeholder="OE B.P. Diastolic"></div>
							    				</td>
							    			</tr>
							    			
							    			<tr>
							    				<td style="padding-left:10px;"> R.S.: </td>
							    				<td colspan="2" style="padding-left:10px;"> <input type="text"   class="form-control" style="border:none;"  name="OERS" value="<s:property value="OERS"/>" placeholder="OE R.S."> </td>
							    			</tr>
							    			
							    			<tr>
							    				<td style="padding-left:10px;"> C.V.S.: </td>
							    				<td colspan="2" style="padding-left:10px;"> <input type="text"   class="form-control" style="border:none;"  name="OECVS" value="<s:property value="OECVS"/>" placeholder="OE CVS" > </td>
							    			</tr>
							    			
							    		</table>
							    	</div>
							    	
									<div class="col-md-6">
							    	  
							    		<table border="1" width="100%" style="font-size:14px;">
							    			<tr align="center">
							    				<td colspan="4"> Investigation </td>
							    			</tr>
							    			
							    	<% 
							    	  	String LabInvastigationList1 = ("HB%,WBC,B.T.,C.T.,F,PP,Urine(R&M)");
								    	  
								    	  String[] LabInvastigationListValue = LabInvastigationList1.split(",");
							    		
								    	  String[] Values1 = new String[0];
								    	  
								    	  for(PatientForm form1 : LabInvastigationList){
								    		
								    		  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(Values1));
								    		  
								    		  myList.add(form1.getOtherTest());
								    		  
								    		  Values1 = myList.toArray(Values1);%>
								    		  
								    	<% }   %>
								    	
								    		<s:iterator value="LabInvastigationList">
								    		<tr>
									    		<td style="padding-left:10px;"><input type="hidden" name="editPanel" value="<s:property value="otherTest"/>"> <s:property value="otherTest"/></td>
									    		<td colspan="3" style="padding-left:10px;"><input type="hidden" name="editInvestigationDetailsID" value="<s:property value="investigationDetailsID"/>"><input type="text" class="form-control" style="border:none;" name="editValues" value="<s:property value="otherTestValue"/>" placeholder="Investigations <s:property value="otherTest"/>" autofocus=""> </td>
									    	</tr>
									    	</s:iterator> 
									  
								    	 	<% 
								    	 		List<String> list = Arrays.asList(Values1);
								    	 	for(int i = 0; i < LabInvastigationListValue.length; i++){
								    	 		
								    	  		if(!(list.contains(LabInvastigationListValue[i]))){  %>
							    					
									    		<tr>
									    			<td style="padding-left:10px;"><input type="hidden" name="panel" value="<%=LabInvastigationListValue[i] %>"> <%=LabInvastigationListValue[i] %> </td>
									    			<td colspan="3" style="padding-left:10px;"><input type="text" class="form-control" style="border:none;" name="values" placeholder="Investigations <%=LabInvastigationListValue[i] %>" autofocus=""> </td>
									    		</tr>
									    		 
									    		<%	}
									    	  } %>
								    	
							    			
							    		
							    		</table>
							    	  
							    	</div>
							    </div>
							    
							    <div class="ln_solid"></div>
			                      
			                      <div class="form-group">
			                        <div class="col-md-12" align="center">
			                          <a href="#ipd1" data-toggle="tab"><button class="btn btn-primary" type="button" style="width:15%" id="printButton"  >Previous</button></a>
			                          <a href="#ipd2" data-toggle="tab"><button class="btn btn-default" type="button" style="width:15%" id="printButton" >Next</button></a>
			                        </div>
			                      </div>
						         
						         </div>
						         
						         <div class="tab-pane fade" id="ipd2" >
						         
						         	<div class="row" style="margin-top:15px;">
										<center>
											<h4><b>CONTINUATION SHEET</b></h4>
										</center>
									 </div>
						         
						         	<div class="row" style="margin-top:15px;">
						         		<div class="col-md-12">
						         		<div class="col-md-4">
						         			<label for="Name" style="font-size:14px;">Patient Name:&nbsp;&nbsp;<s:property value="firstName"/> <s:property value="middelName"/> <s:property value="lastName"/></label>
						         		</div>
						         		<div class="col-md-3">
						         			<label for="Name" style="font-size:14px;">Age:&nbsp;&nbsp;<s:property value="age"/> </label>
						         		</div>
						         		<div class="col-md-3">
						         			<label for="Name" style="font-size:14px;">Gender:&nbsp;&nbsp;<s:property value="gender"/></label>
						         		</div>
						         		</div>
						         	</div>
						
									<div class="ln_solid"></div>
						            
						            <div class="row" style="margin-top:25px;font-size:14px;">
								      <div class="col-md-12">
								      	<div class="col-md-4" style="border: 1px solid black;">
								      		Date
								      	</div>
								      	<div class="col-md-4" style="border-top: 1px solid black;border-bottom: 1px solid black;border-right: 1px solid black;">
								      		Description
								      	</div>
								      	<div class="col-md-4" style="border-top: 1px solid black;border-bottom: 1px solid black;border-right: 1px solid black;">
								      		Treatment
								      	</div>
								      	
								      	<div class="col-md-4" style="height:414px;border-left: 1px solid black;border-bottom: 1px solid black;border-right: 1px solid black;">
								      		<input type="text" class="form-control" style="border:none;" name="countinuationSheetDate" value="<s:property value="countinuationSheetDate"/>" id="single_cal31" placeholder="Click here to pick a date" aria-describedby="inputSuccess2Status3">		      	
								      	</div>
								      	
								      	<div class="col-md-4" style="border-bottom: 1px solid black;border-right: 1px solid black;">
								      		<s:textarea  name="countinuationSheetDescription" id="countinuationSheetDescription" placeholder="Description" class="form-control ctnTextarea" style="height:400px;width: 100%;color:black;font-size: 1em;outline: none;font-weight: 300;border: none;background:transparent;margin:0 0 1em 0;border-radius: 2px;-webkit-border-radius: 2px;-moz-border-radius: 2px;-o-border-radius: 2px;border:none;"></s:textarea>		      	
								      	</div>
								      	
								      	<div class="col-md-4" style="border-bottom: 1px solid black;border-right: 1px solid black;">
								      		<s:textarea  name="countinuationSheetTeatment" id="countinuationSheetTeatment" placeholder="Treatment" class="form-control ctnTextarea" style="height:400px;width: 100%;color:black;font-size: 1em;outline: none;font-weight: 300;border: none;background:transparent;margin:0 0 1em 0;border-radius: 2px;-webkit-border-radius: 2px;-moz-border-radius: 2px;-o-border-radius: 2px; border:none;"></s:textarea>		      	
								      	</div>
								       
								      </div>  
								    </div>
								    
								     <div class="ln_solid"></div>
			                      
				                      <div class="form-group">
				                        <div class="col-md-12" align="center">
				                          <a href="#ipd12" data-toggle="tab"><button class="btn btn-primary" type="button" style="width:15%" id="printButton"  >Previous</button></a>
				                          <a href="#ipd3" data-toggle="tab"><button class="btn btn-default" type="button" style="width:15%" id="printButton" >Next</button></a>
				                        </div>
				                      </div>
						         
						         </div>
						         
						         <div class="tab-pane fade" id="ipd3" >
						         
							         <div class="row" style="margin-top: 15px;">
										<center>
											<h4><b>OT NOTES</b></h4>
										</center>
									 </div>
									 
									 <div class="row" style="margin-top: 15px;">
									 	<div class="col-md-12">
									 		<s:textarea  name="OTNotes" placeholder="OT NOTES" class="form-control" style="height:400px; "></s:textarea>
									 	</div>
									 </div>
									 
									 <div class="ln_solid"></div>
	                      
				                      <div class="form-group">
				                        <div class="col-md-12" align="center">
				                          <button type="button" onclick="windowOpen1(<s:property value="patientID"/>);" class="btn btn-primary" style="width:15%">Cancel</button>
				                          <a href="#ipd2" data-toggle="tab"><button class="btn btn-default" type="button" style="width:15%" id="printButton" >Previous</button></a>
				                          <button class="btn btn-success" type="button" id="saveBtnID" style="width:15%" onclick="submitSaveForm()">Update & Print</button>
				                        </div>
				                      </div>
						         
						         </div>
						
							
						    </div>
						    </form>
						    
						    </div>
						    <!-- IPD Visit End -->
						    
						    <!-- Prescription -->
						    <div class="tab-pane fade" id="prescription">
						    	<form class="form-horizontal form-label-left" novalidate id="genPhyPrescID" name="genPhyPrescID" onsubmit="return updatePrescription();" action="EditIPDPrescription" method="POST" style="margin-top:20px;">
				
							      <input type="hidden" name="patientID" value="<s:property value="patientID"/>"> 
							      <input type="hidden" name="visitID" value="<s:property value="visitID"/>"> 
							    
							      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
							      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
								  <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
								  <input type="hidden"  name="addButton" id="addButton" value="add">
							      
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
							             <sx:autocompleter name="investigation" cssStyle="padding-left:14px;width:100%" cssClass="form-control" id="investigation" list="InvestigationList" showDownArrow="false" />
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
					                        <button type="button" class="btn btn-success" id="savePrescID" name="" onclick="setBtnValue('add','addButton','genPhyPrescID')" >Save</button>
						                 	<button class="btn btn-warning" type="button" id="PrintPrescID" name="" onclick="setBtnValue('print','addButton','genPhyPrescID')"  >Save & Print Discharge Card</button>
				                          </div>
					                    </div>
							      
							      </form>
						    </div>
						   	<!-- Prescriotion End -->
						   	
						   	<!-- Billing -->
						    <div class="tab-pane fade" id="billing">
						    	<form class="form-horizontal form-label-left" onsubmit="checkCheckbox();" name="OPDBILLForm" id="OPDBILLForm" action="EditIPDOpthBill" method="POST" style="margin-top:20px;">
			
							      <div class="row" style="margin-top:10px;margin-left:0px;">
								     <%--  <h5 style="margin-top:0px;font-size:14px;">Patient Name: <s:property value="firstName"/>&nbsp;<s:property value="middelName"/>&nbsp;<s:property value="lastName"/>&nbsp;(<s:property value="patientID"/>)</h5> --%>
								  </div>
								      
							      <input type="hidden" name="patientID" id="patientID" value="<s:property value="patientID"/>"> 
							      <input type="hidden" name="visitID" id="visitID" value="<s:property value="visitID"/>">
							 
							      <input type="hidden"  name="firstName" id="firstName" value="<s:property value="firstName"/>">
							      <input type="hidden"  name="lastVisitID" id="lastVisitID" value="<s:property value="lastVisitID"/>">
							       <input type="hidden"  name="lastName" id="lastName" value="<s:property value="lastName"/>">
							       <input type="hidden"  name="opticinID" id="opticinID" value="<s:property value="opticinID"/>">
							       <input type="hidden"  name="addButton" id="addButtonBill" value="add">
							      
		
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
								
								<div class="row" style="margin-top: 15px;">
									<table id="billingItemTableID" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
										<thead>
											<tr>
												<th style="width: 70%;text-align: center;">Charges</th>
												<th style="width: 25%; text-align: center;">Rate (Rs.)</th>
												<th style="width: 5%; text-align: center;"></th>
											</tr>
										</thead>
										<tbody>
										
											<tr id="billingItemTRID">
												<td style="text-align: right">
												
												<a href="javascript:void(0)" onclick="openIPDChargesModal()" > 
									 				<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
													onmouseout="this.src='images/add_icon_1.png'" alt="Add Charges"
													title="Add Charges" style="height: 20px;" />
									    		</a>
									    		&nbsp;
									    		
													<select id="billItemChargesID" class="form-control" style="width: 50%; display: inline-block;" onchange="setChargesRate(this.value)">
														<option value="">Select Charges</option>
														<%
															for (Integer id : IPDChargesList.keySet()) {
																
																String[] name = IPDChargesList.get(id).split("===");
											
														%>
															<option value="<%=name[1] %>"><%=name[0] %></option>
														<%	
															}
														%>
													</select>
												</td>
												<td style="text-align: center;">
													<input type="number" class="form-control" name="" id="billItemRateID" placeholder="Rate">
												</td>
												<td style="text-align: center;">
													<a onclick="addBillintItemRow(billItemChargesID.value, 'billItemChargesID', billItemRateID.value);" > 
										 				<img src="images/add_icon_1.png" onmouseover="this.src='images/add_icon_2.png'"
														onmouseout="this.src='images/add_icon_1.png'" alt="Add Charges"
														title="Add Charges" style="margin-top:5px;height: 20px;" />
										    		</a>
												</td>
											</tr>
											
											<s:iterator value="orthoBillItemList" var="form">
												<tr id="editBillItemTRID<s:property value="receiptItemID"/>">
													<td style="text-align: center;"><s:property value="itemName" />
														<input type="hidden" name="itemNameArr" value="<s:property value="itemName"/>">
														<input type="hidden" name="itemIDArr" value="<s:property value="receiptItemID"/>">
													</td>
													<td style="text-align: center;" >
														<input type="number" class="form-control billingRateClass" name="itemRateArr" id="itemRate" onkeyup="changeTotalBill('totalBill',charges.value);" value="<s:property value="itemRate"/>" placeholder="Rate">
													</td>
													<td style="text-align: center;">
														<a onclick="removeBillTRAjax('editBillItemTRID<s:property value="receiptItemID"/>', '<s:property value="receiptItemID"/>');" > 
											 				<img src="images/delete.png" onmouseover="this.src='images/delete.png'"
															onmouseout="this.src='images/delete.png'" alt="Remove Charges"
															title="Remove Charges" style="margin-top:5px;height: 20px;" />
											    		</a>
										    		</td>
												</tr>
											</s:iterator>
										
											<tr>
												<td style="text-align: right">
													<b>Consultation Charges(Rs)</b>
												</td>
												<td style="text-align: center;" colspan="2">
													<input type="number" class="form-control billingRateClass" name="charges" id="charges" value="<s:property value="charges"/>" onkeyup="changeTotalBill('totalBill',charges.value);" placeholder="Charges(Rs)">
												</td>
											</tr>
											
											<tr>
												<td style="text-align: right">
													<b>Total Bill(Rs)</b>
												</td>
												<td style="text-align: center;" colspan="2">
													<input type="text" class="form-control"  name="totalBill" id="totalBill" value="<s:property value="netAmount"/>" readonly="readonly" placeholder="Total Bill(Rs)">
												</td>
											</tr>
											
										</tbody>
									</table>
								</div>
								  
			                  <%--    <div class="item form-group" style="margin-top:15px;">
			                       <label for="User Type" class="control-label col-md-3 col-xs-5">Charges(Rs)</label>
			                        <div class="col-md-6 col-sm-6 col-xs-6">
			                         <input type="text" class="form-control"  name="charges" id="charges" value="<s:property value="netAmount"/>" onkeyup="changeTotalBill('totalBill',charges.value);" placeholder="Charges(Rs)">
			                        </div>
			                      </div>
			                      
			                      <div class="item form-group">
			                      <label for="User Type" class="control-label col-md-3 col-xs-5"">Total Bill(Rs)</label>
			                         <div class="col-md-6 col-sm-6 col-xs-6">
			                          <input type="text" class="form-control"  name="totalBill" id="totalBill" value="<s:property value="netAmount"/>" readonly="readonly" placeholder="Total Bill(Rs)">
			                        </div>
			                      </div>
			                 
			                  	<div class="item form-group">
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
			                      </div>
			                       --%>
			                      
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
											<font style="font-size: 16px;">Cash Paid</font>
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
											<font style="font-size: 16px;">Cheque No.</font>
										</div>
										<div class="col-md-3">
											<input class="form-control" name="chequeNo" id="chequeNoID" value="<s:property value="chequeNo"/>" placeholder="Cheque No." type="text">
										</div>
									</div>
									<div class="row" style="margin-top: 15px;">
										<div class="col-md-2 col-xs-6">
											<font style="font-size: 16px;">Bank Name</font>
										</div>
										<div class="col-md-3">
											<input type="text" class="form-control" id="chequeBankNameID" value="<s:property value="chequeBankName"/>" name="chequeBankName" placeholder="Bank Name">
										</div>
										<div class="col-md-2 col-xs-6">
											<font style="font-size: 16px;">Branch</font>
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
											<font style="font-size: 16px;">Amount</font>
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
				                        <button type="button" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
				                        
				                        <button type="button" class="btn btn-success" id="billSubmtBtnID" name="" onclick="setBtnValue('add','addButtonBill','OPDBILLForm')" >Save</button>
						                 	<button class="btn btn-warning" type="button" id="billSubmtBtnID" name="" onclick="setBtnValue('print','addButtonBill','OPDBILLForm')"  >Save & Print</button>
				                      
					               	 	<!-- <button class="btn btn-success" type="submit" name="addButton" value="add" id="billSubmtBtnID" >Save</button>
					               	
					               	  <button class="btn btn-warning" type="submit" name="addButton" value="print" id="billSubmtBtnID">Save & Print</button> -->
					               	  
				                      </div>
				                    </div>
					     
					      </form>
						    </div>
						   	<!-- Billing End -->
						   	
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
    
    <script type="text/javascript" src="build/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
   
   <script src="build/js/jquery-ui.js"></script>
   
<!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- bootstrap-daterangepicker -->
    
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
    	
        $('#single_cal3').daterangepicker({
          singleDatePicker: true,
          calender_style: "picker_3"
        }, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
        
        $('#dateOfDischarge').daterangepicker({
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
        
        setTimeout(function(){
        	
        	$('#doa_time').datetimepicker({
            	pickDate: false,
                minuteStep: 5,
                pickerPosition: 'bottom-right',
                format: 'HH:ii p',
                autoclose: true,
                showMeridian: true,
                startView: 1,
                maxView: 1,
            });
            
            $('#dod_time').datetimepicker({
            	pickDate: false,
                minuteStep: 5,
                pickerPosition: 'bottom-right',
                format: 'HH:ii p',
                autoclose: true,
                showMeridian: true,
                startView: 1,
                maxView: 1,
            });
            
        }, 2000);
        
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
                 		CategoryValue($("#tradeName").val());
                 	},500);
                 }
             });
     });
   
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
    
  </body>
</html>