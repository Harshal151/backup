<%@page import="com.edhanvantari.daoImpl.*"%>
<%@page import="com.edhanvantari.daoInf.*"%>
<%@page import="com.edhanvantari.service.*"%>
<%@page import="java.util.*"%>
<%@page import="com.edhanvantari.util.*"%>
<%@page import="com.edhanvantari.form.*"%>
<%@page import="com.amazonaws.auth.*"%>
<%@page import="com.amazonaws.services.s3.*"%>
<%@page import="com.amazonaws.services.s3.model.*"%>
<%@page import="com.amazonaws.util.*"%>
<%@page import="java.io.*"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>Left menu | E-Dhanvantari</title>

<style type="text/css">
li {
	font-family: AVGARDM_2;
	font-size: 15px;
}
</style>

<script type="text/javascript">
	function windowOpen() {

		//document.location="Welcome.jsp";
		document.location = "Welcome";
	}
</script>

<!-- For login message -->

<%
String sessionValue = "true";
LoginForm form = (LoginForm) session.getAttribute("USER");

if (session.getAttribute("USER") == "" || session.getAttribute("USER") == null) {
	sessionValue = "";
	String loginMessage = "Plase login using valid credentials";
	request.setAttribute("loginMessage", loginMessage);
	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
	dispatcher.forward(request, response);
}

int userID = form.getUserID();

LoginDAOInf daoInf = new LoginDAOImpl();
ClinicDAOInf clinicDaoInf = new ClinicDAOImpl();

RegistrationDAOinf registrationDAOinf = new RegistrationDAOImpl();

eDhanvantariServiceInf serviceInf = new eDhanvantariServiceImpl();

int finalCount = 0;

int allowed_visit = clinicDaoInf.retrieveAllowedVisitByPracticeID(form.getPracticeID());
System.out.println("allowed visit::" + allowed_visit);

String startDate = clinicDaoInf.retrieveStartDateByPracticeID(form.getPracticeID());
System.out.println("start date::" + startDate);

String endDate = clinicDaoInf.retrieveEndDateByPracticeID(form.getPracticeID());
System.out.println("end date::" + endDate);

int visitCount = clinicDaoInf.retrieveVisitCountBetweenDates(startDate, endDate);
System.out.println("visitCount::" + visitCount);

if (allowed_visit > 0) {
	System.out.println("in allowed iff");
	finalCount = allowed_visit - visitCount;
} else {
	System.out.println("in allowed else");
	finalCount = 0;
}

//AWS code to read/view image file

ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

ConfigurationUtil util = new ConfigurationUtil();

String accessKey = xmlUtil.getAccessKey();

String secreteKey = xmlUtil.getSecreteKey();

AWSS3Connect awss3Connect = new AWSS3Connect();

// getting input file location from S3 bucket
String s3reportFilePath = xmlUtil.getS3RDMLFilePath();

// getting s3 bucket name
String bucketName = util.getS3BucketName();

String realPath = request.getServletContext().getRealPath("/");

// getting s3 bucket region
String bucketRegion = xmlUtil.getS3BucketRegion();

AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
		.withRegion(bucketRegion).build();
%>
<!-- Ends -->
<script type="text/javascript">
	jQuery(document).ready(function($) {
		if (
<%=sessionValue%>
	== "") {
			var url = "index.jsp";
			$(location).attr('href', url)
		}
	});
</script>
</head>

<body class="nav-md">

	<!-- sidebar menu -->
	<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
		<div class="menu_section">

			<div class="navbar nav_title" style="border: 0; height: 60px;">

				<div style="margin-top: -9%;">
					<a href="http://www.edhanvantari.com" target="_blank"
						class="site_title" id="edhanLinkID"
						style="height: 62px; background-color: white;"> <img
						src="images/Icon.png" height="50px" style="margin-left: 3px;">
						<img src="images/Text.png" height="15px"
						style="width: 120px; height: 22%; margin-left: 8%;"></a>
				</div>
			</div>

			<div class="clearfix"></div>

			<!-- menu profile quick info -->
			<div class="profile">
				<div class="profile_pic">

					<%
					if (daoInf.retrieveClinicLogo(form.getClinicID()) == null || daoInf.retrieveClinicLogo(form.getClinicID()).isEmpty()) {
					%>
					<img src="" alt="Clinic Logo"
						style="height: 45px; padding: 0px; margin-top: 0%;"
						class="img-circle profile_img">

					<%
					} else {

					S3ObjectInputStream s3ObjectInputStream = s3.getObject(
							new GetObjectRequest(bucketName + "/" + s3reportFilePath, daoInf.retrieveClinicLogo(form.getClinicID())))
							.getObjectContent();

					IOUtils.copy(s3ObjectInputStream,
							new FileOutputStream(new File(realPath + "images/" + daoInf.retrieveClinicLogo(form.getClinicID()))));
					%>
					<img
						src="<%="images/" + daoInf.retrieveClinicLogo(form.getClinicID())%>"
						alt="Clinic Logo"
						style="height: 45px; padding: 0px; margin-top: 0%;"
						class="img-circle profile_img">

					<%
					}
					%>

				</div>
				<div class="profile_info"
					style="margin-bottom: 10px; margin-top: -7%;">
					<h2 style="font-size: 16px; font-weight: bold;"><%=form.getClinicName()%></h2>
				</div>

			</div>

			<h3 style="color: #107B95; text-shadow: none;">a</h3>
			<%
			if (allowed_visit == 1000000) {
			%>
			<h2 style="font-size: 16px; padding-left: 10px; color: #FABA0C;">
				<b>Balance visit: Unlimited</b>
			</h2>
			<%
			} else {
			%>
			<h2 style="font-size: 16px; padding-left: 10px; color: #FABA0C;">
				<b>Balance visit: <%=finalCount%></b>
			</h2>
			<%
			}
			%>


			<ul class="nav side-menu">

				<%
				if (form.getUserType().equals("superAdmin")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="pmID1">Add Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID2">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="ActivatePatient"
							id="pmID3">Activate Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID4">Broadcast Messages</a></li>
					</ul></li>
				<li><a id="admID"><i class="fa fa-cogs"></i>Administration<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a id="repID1">Manage
								Practice<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddPractice" id="repID2">Add Practice</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditPracticeList" id="repID3">Edit Practice</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="repID9">Manage
								Users<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddUser" id="repID10">Add User</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditUserList" id="repID11">Edit User</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="repID4">Manage
								Visit Types<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddVisitType" id="repID5">Add Visit Type</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditVisitTypeList" id="repID6">Edit Visit Type</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureFrequency" id="ivntID17">Manage
								Frequency</a></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureInstruction" id="ivntID18">Manage
								Instruction</a></li>
						<li style="padding-left: 25px;"><a id="manageFacility">Manage
								Facility<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageRoomType.jsp" id="ivntID6">Manage Rooms</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageOTTypes.jsp" id="#">Manage OT</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageServiceTypes.jsp" id="#">Manage Services</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="manageCharges">Manage
								Charges<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageOPDCharges.jsp" id="ivntID7">Manage OPD Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderIPDCharges"
									id="ivntID8">Manage OT Charges</a></li>
								<li style="padding-left: 25px;"><a
									href="RenderIPDConsultantCharges" id="ivntID9">Manage
										Consultant Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderRoomCharges"
									id="ivntID9">Manage Room Charges</a></li>
							</ul></li>

						<li style="padding-left: 25px;"><a id="labInventory">Lab
								Inventory<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderConfigureLabTest">Manage Lab Tests</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestsRate">Manage Lab Tests Charges</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderGroupLabTest">Manage Group Tests</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestQuotation">Lab Tests Quotation </a></li>
							</ul></li>
						<li class="sub_menu" style="padding-left: 25px;"><a
							href="RenderConfigureReferringDoctor" id="repID8">Manage
								Referring Doctors</a></li>
						<li style="padding-left: 25px;"><a href="SMSTemplate.jsp"
							id="ivntID20">SMS Templates</a></li>
						<li class="sub_menu" style="padding-left: 25px;"><a
							href="RenderConfigureDiagnoses" id="repID7">Manage Diagnosis</a></li>
						<li style="padding-left: 25px;"><a id="manageTemplate">Manage
								Template<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a href="addTemplate.jsp"
									id="#">Add Template </a></li>
								<li style="padding-left: 25px;"><a href="viewTemplate.jsp"
									id="#">Edit Template </a></li>
							</ul>
					</ul></li>

				<li><a id="ivntID"><i class="fa fa-archive"></i>Manage
						Inventory<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID19">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID4">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID5">Edit Drugs &
										Product</a></li>
							</ul></li>
						<!--   <li style="padding-left:25px;"><a href="manageTest.jsp" id="ivntID10">Manage Test</a></li> -->
						<li style="padding-left: 25px;"><a id="ivntID11">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID12">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID13">Edit Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderRemoveStock" id="ivntID14">Remove Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID15">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID16">Check Stock</a>
							</ul></li>
					</ul></li>

				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<%-- <li style="padding-left:25px;"><a id="repID1">Clinical Data<span class="fa fa-chevron-down"></span></a>
			                          <ul class="nav child_menu">
			                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderPatientReport" id="repID2">Generate Report</a>
			                            </li>
			                             <li class="sub_menu" style="padding-left:25px;"><a href="RenderExecuteReport" id="repID3">Execute Report</a>
			                            </li>
			                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Diagnosis-based Reports</a>
			                            </li>
			                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Drug-based Reports</a>
			                            </li>
			                           </ul>
			                        </li> --%>
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
					</ul></li>

				<%
				} else if (form.getUserType().equals("administrator")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="pmID1">Add Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID2">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="ActivatePatient"
							id="pmID3">Activate Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID4">Broadcast Messages</a></li>
					</ul></li>
				<li><a id="admID"><i class="fa fa-cogs"></i>Administration<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a id="repID9">Manage
								Users<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddUser" id="repID10">Add User</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditUserList" id="repID11">Edit User</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="repID4">Manage
								Visit Types<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddVisitType" id="repID5">Add Visit Type</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditVisitTypeList" id="repID6">Edit Visit Type</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureFrequency" id="ivntID17">Manage
								Frequency</a></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureInstruction" id="ivntID18">Manage
								Instruction</a></li>
						<li style="padding-left: 25px;"><a id="manageFacility">Manage
								Facility<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageRoomType.jsp" id="ivntID6">Manage Rooms</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageOTTypes.jsp" id="#">Manage OT</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageServiceTypes.jsp" id="#">Manage Services</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="manageCharges">Manage
								Charges<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageOPDCharges.jsp" id="ivntID7">Manage OPD Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderIPDCharges"
									id="ivntID8">Manage OT Charges</a></li>
								<li style="padding-left: 25px;"><a
									href="RenderIPDConsultantCharges" id="ivntID9">Manage
										Consultant Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderRoomCharges"
									id="ivntID9">Manage Room Charges</a></li>
							</ul></li>
						<li class="sub_menu" style="padding-left: 25px;"><a
							href="RenderConfigureReferringDoctor" id="repID8">Manage
								Referring Doctors</a></li>
						<li style="padding-left: 25px;"><a href="SMSTemplate.jsp"
							id="ivntID20">SMS Templates</a></li>
							
							
						<li style="padding-left: 25px;"><a id="labInventory">Lab
								Inventory<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestsRate">Manage Lab Tests Charges</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderGroupLabTest">Manage Group Tests</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestQuotation">Lab Tests Quotation </a></li>
							</ul></li>
							
							
						<li style="padding-left: 25px;"><a id="manageTemplate">Manage
								Template<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a href="addTemplate.jsp"
									id="#">Add Template </a></li>
								<li style="padding-left: 25px;"><a href="viewTemplate.jsp"
									id="#">Edit Template </a></li>
							</ul></li>
							
							
					</ul></li>
				<li><a id="ivntID"><i class="fa fa-archive"></i>Inventory
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID4">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID5">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID6">Edit Drugs &
										Product</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="ivntID12">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID13">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID14">Edit Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderRemoveStock" id="ivntID15">Remove Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID16">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID17">Check Stock</a>
							</ul></li>
					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<%-- <li style="padding-left:25px;"><a id="repID1">Clinical Data<span class="fa fa-chevron-down"></span></a>
		                          <ul class="nav child_menu">
		                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderPatientReport" id="repID2">Generate Report</a>
		                            </li>
		                             <li class="sub_menu" style="padding-left:25px;"><a href="RenderExecuteReport" id="repID3">Execute Report</a>
		                            </li>
		                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Diagnosis-based Reports</a>
		                            </li>
		                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Drug-based Reports</a>
		                            </li>
		                           </ul>
		                        </li> --%>
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
					</ul></li>
				<%--  <li style="padding-left:25px;"><a id="repID7">Inventory<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderStockReport" id="repID8">Stock Reports</a>
	                            </li>
								<li class="sub_menu" style="padding-left:25px;"><a href="RenderSalesReport" id="repID9">Sales Reports</a>
	                            </li> 
	                           </ul>
	                        </li> --%>
				<!--   <li style="padding-left:25px;"><a href="medicalReport.jsp">Audit Reports</a> -->
				<!-- <li style="padding-left:25px;"><a href="medicalReport.jsp">Medical Reports</a></li>
                      <li style="padding-left:25px;"><a href="#">Referrals</a></li>
                      <li style="padding-left:25px;"><a href="#">Audits</a></li>
                      <li style="padding-left:25px;"><a href="#">Accounts</a></li> -->
				<%
				} else if (form.getUserType().equals("manager")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="pmID1">Add Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID2">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="ActivatePatient"
							id="pmID3">Activate Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID4">Broadcast Messages</a></li>
					</ul>
				<li><a id="admID"><i class="fa fa-cogs"></i>Administration<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">

						<li style="padding-left: 25px;"><a id="repID9">Manage
								Users<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddUser" id="repID10">Add User</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditUserList" id="repID11">Edit User</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="repID4">Manage
								Visit Types<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddVisitType" id="repID5">Add Visit Type</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="EditVisitTypeList" id="repID6">Edit Visit Type</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureFrequency" id="ivntID17">Manage
								Frequency</a></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureInstruction" id="ivntID18">Manage
								Instruction</a></li>
						<li style="padding-left: 25px;"><a id="manageFacility">Manage
								Facility<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageRoomType.jsp" id="ivntID6">Manage Rooms</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageOTTypes.jsp" id="#">Manage OT</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="manageServiceTypes.jsp" id="#">Manage Services</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a id="manageCharges">Manage
								Charges<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a
									href="manageOPDCharges.jsp" id="ivntID7">Manage OPD Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderIPDCharges"
									id="ivntID8">Manage OT Charges</a></li>
								<li style="padding-left: 25px;"><a
									href="RenderIPDConsultantCharges" id="ivntID9">Manage
										Consultant Charges</a></li>
								<li style="padding-left: 25px;"><a href="RenderRoomCharges"
									id="ivntID9">Manage Room Charges</a></li>
							</ul></li>

						<li style="padding-left: 25px;"><a id="labInventory">Lab
								Inventory<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestsRate">Manage Lab Tests Charges</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderGroupLabTest">Manage Group Tests</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderLabTestQuotation">Lab Tests Quotation </a></li>
							</ul></li>


						<li class="sub_menu" style="padding-left: 25px;"><a
							href="RenderConfigureReferringDoctor" id="repID8">Manage
								Referring Doctors</a></li>
						<li style="padding-left: 25px;"><a href="SMSTemplate.jsp"
							id="ivntID20">SMS Templates</a></li>
						<li class="sub_menu" style="padding-left: 25px;"><a
							href="RenderConfigureDiagnoses" id="repID7">Manage Diagnosis</a></li>
						<li style="padding-left: 25px;"><a id="manageTemplate">Manage
								Template<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li style="padding-left: 25px;"><a href="addTemplate.jsp"
									id="#">Add Template </a></li>
								<li style="padding-left: 25px;"><a href="viewTemplate.jsp"
									id="#">Edit Template </a></li>
							</ul></li>
					</ul></li>

				<li><a id="ivntID"><i class="fa fa-archive"></i>Manage
						Inventory<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID19">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID4">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID5">Edit Drugs &
										Product</a></li>
							</ul></li>
						<!--   <li style="padding-left:25px;"><a href="manageTest.jsp" id="ivntID10">Manage Test</a></li> -->
						<li style="padding-left: 25px;"><a id="ivntID11">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID12">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID13">Edit Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderRemoveStock" id="ivntID14">Remove Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID15">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID16">Check Stock</a>
							</ul></li>
					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<%-- <li style="padding-left:25px;"><a id="repID1">Clinical Data<span class="fa fa-chevron-down"></span></a>
				                          <ul class="nav child_menu">
				                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderPatientReport" id="repID2">Generate Report</a>
				                            </li>
				                             <li class="sub_menu" style="padding-left:25px;"><a href="RenderExecuteReport" id="repID3">Execute Report</a>
				                            </li>
				                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Diagnosis-based Reports</a>
				                            </li>
				                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Drug-based Reports</a>
				                            </li>
				                           </ul>
				                        </li> --%>
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
					</ul></li>

				<%
				} else if (form.getUserType().equals("accountant")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="ViewPatient">View
								Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS">Broadcast
								Messages</a></li>
					</ul></li>
				<li><a id="ivntID"><i class="fa fa-archive"></i>Inventory
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID4">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID5">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID6">Edit Drugs &
										Product</a></li>
							</ul></li>

						<li style="padding-left: 25px;"><a href="manageRoomType.jsp"
							id="ivntID7">Manage Room Types</a></li>

						<li style="padding-left: 25px;"><a
							href="manageOPDCharges.jsp" id="ivntID8">Manage OPD Charges</a></li>

						<li style="padding-left: 25px;"><a href="RenderIPDCharges"
							id="ivntID9">Manage OT Charges</a></li>

						<li style="padding-left: 25px;"><a
							href="RenderIPDConsultantCharges" id="ivntID10">Manage
								Consultant Charges</a></li>
						<li style="padding-left: 25px;"><a href="RenderRoomCharges"
							id="ivntID9">Manage Room Charges</a></li>

						<!-- <li style="padding-left:25px;"><a href="manageTest.jsp" id="ivntID11">Manage Test</a></li> -->

						<li style="padding-left: 25px;"><a id="ivntID12">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID13">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID14">Edit Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderRemoveStock" id="ivntID15">Remove Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID16">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID17">Check Stock</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureFrequency" id="ivntID18">Manage
								Frequency</a></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureInstruction" id="ivntID19">Manage
								Instruction</a></li>

					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a id="repID1">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID2">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID3">Credit Reports</a></li>
							</ul></li>
						<%--   <li style="padding-left:25px;"><a id="repID4">Inventory<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderStockReport" id="repID5">Stock Reports</a>
	                            </li>
	                           	<li class="sub_menu" style="padding-left:25px;"><a href="RenderSalesReport" id="repID6">Sales Reports</a>
	                            </li>
	                            <!-- <li class="sub_menu" style="padding-left:25px;"><a href="Render3CForm">Form 3C</a>
	                            </li> -->
	                           </ul>
	                        </li> --%>
						<!--   <li style="padding-left:25px;"><a href="medicalReport.jsp">Audit Reports</a> -->
						<!-- <li style="padding-left:25px;"><a href="medicalReport.jsp">Medical Reports</a></li>
                      <li style="padding-left:25px;"><a href="#">Referrals</a></li>
                      <li style="padding-left:25px;"><a href="#">Audits</a></li>
                      <li style="padding-left:25px;"><a href="#">Accounts</a></li> -->
					</ul></li>
				<%
				} else if (form.getUserType().equals("receptionist") || form.getUserType().equals("optician")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient">Add
								Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient">View
								Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS">Broadcast
								Messages</a></li>
					</ul></li>

				<%
				} else if (form.getUserType().equals("compounder") || form.getUserType().equals("dietician")
						|| form.getUserType().equals("billDesk")) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID1">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID1">Broadcast Messages</a></li>
					</ul></li>
				<li><a id="ivntID"><i class="fa fa-archive"></i>Inventory
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID4">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID5">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID6">Edit Drugs &
										Product</a></li>
							</ul></li>

						<li style="padding-left: 25px;"><a href="manageRoomType.jsp"
							id="ivntID7">Manage Room Types</a></li>

						<li style="padding-left: 25px;"><a
							href="manageOPDCharges.jsp" id="ivntID8">Manage OPD Charges</a></li>

						<li style="padding-left: 25px;"><a href="RenderIPDCharges"
							id="ivntID9">Manage OT Charges</a></li>

						<li style="padding-left: 25px;"><a
							href="RenderIPDConsultantCharges" id="ivntID10">Manage
								Consultant Charges</a></li>

						<li style="padding-left: 25px;"><a href="RenderRoomCharges"
							id="ivntID9">Manage Room Charges</a></li>

						<!-- <li style="padding-left:25px;"><a href="manageTest.jsp" id="ivntID11">Manage Test</a></li> -->

						<li style="padding-left: 25px;"><a id="ivntID12">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID13">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID14">Edit Stock</a></li>

								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID15">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID16">Check Stock</a></li>
							</ul></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureFrequency" id="ivntID17">Manage
								Frequency</a></li>
						<li style="padding-left: 25px;"><a
							href="RenderConfigureInstruction" id="ivntID18">Manage
								Instruction</a></li>

					</ul></li>

				<%
				} else if (form.getUserType().equals("surveyor")) {
				%>
				<li><a id="prID"><i class="fa fa-wheelchair"></i>Participant
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="prID1">Add Participant</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="prID2">View Participant</a></li>
						<!-- <li style="padding-left:25px;"><a href="RenderSendSMS">Broadcast Messages</a></li> -->
					</ul></li>
				<%
				} else if (form.getUserType().equals("pharmacist")) {
				%>
				<li><a onclick="windowOpen();" href="SearchPharmaPatients"><i
						class="fa fa-tachometer"></i>Dashboard</a>
				<li><a id="ivntID"><i class="fa fa-archive"></i>Manage
						Inventory<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="addSupplier.jsp"
							id="ivntID1">Manage Suppliers</a></li>
						<li style="padding-left: 25px;"><a href="addTax.jsp"
							id="ivntID2">Manage Taxes</a></li>
						<li style="padding-left: 25px;"><a href="addCategory.jsp"
							id="ivntID3">Manage Categories</a></li>
						<li style="padding-left: 25px;"><a id="ivntID19">Manage
								Drugs & Products<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddProduct" id="ivntID4">Add Drugs & Product</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editProductList.jsp" id="ivntID5">Edit Drugs &
										Product</a></li>
							</ul></li>
						<!--   <li style="padding-left:25px;"><a href="manageTest.jsp" id="ivntID10">Manage Test</a></li> -->
						<li style="padding-left: 25px;"><a id="ivntID11">Manage
								Stock<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderAddStock" id="ivntID12">Add Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="editStockList.jsp" id="ivntID13">Edit Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderRemoveStock" id="ivntID14">Remove Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderOrderStock" id="ivntID15">Order Stock</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderStockDetails" id="ivntID16">Check Stock</a>
							</ul></li>
					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
					</ul></li>
				<%
				} else if (form.getUserType().equals("clinician") && form.getPracticeID() == 29) {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="pmID1">Add Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID2">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID3">Broadcast Messages</a></li>
					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<%-- <li style="padding-left:25px;"><a id="repID1">Clinical Data<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderPatientReport" id="repID2">Generate Report</a>
	                            </li>
	                             <li class="sub_menu" style="padding-left:25px;"><a href="RenderExecuteReport" id="repID3">Execute Report</a>
	                            </li>
	                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Diagnosis-based Reports</a>
	                            </li>
	                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Drug-based Reports</a>
	                            </li>
	                           </ul>
	                        </li> --%>
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
						<%--   <li style="padding-left:25px;"><a id="repID7">Inventory<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderStockReport" id="repID8">Stock Reports</a>
	                            </li>
	                           	<li class="sub_menu" style="padding-left:25px;"><a href="RenderSalesReport" id="repID9">Sales Reports</a>
	                            </li>
	                            
	                           </ul>
	                        </li> --%>
						<!--   <li style="padding-left:25px;"><a href="medicalReport.jsp">Audit Reports</a> -->
						<!-- <li style="padding-left:25px;"><a href="medicalReport.jsp">Medical Reports</a></li>
                      <li style="padding-left:25px;"><a href="#">Referrals</a></li>
                      <li style="padding-left:25px;"><a href="#">Audits</a></li>
                      <li style="padding-left:25px;"><a href="#">Accounts</a></li> -->
					</ul></li>
				<li><a href="RenderOnGoingTimers" id="ogtID"><i
						class="fa fa-clock-o"></i>On-going Timers</a></li>
				<!-- New added -->

				<%
				} else {
				%>
				<li><a onclick="windowOpen();" id="dashboardID"><i
						class="fa fa-tachometer"></i>Dashboard</a></li>
				<li><a id="pmID"><i class="fa fa-wheelchair"></i>Patient
						Management<span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<li style="padding-left: 25px;"><a href="RenderAddPatient"
							id="pmID1">Add Patient</a></li>
						<!-- <li style="padding-left:25px;"><a href="EditPatientList">Edit Patient</a></li> -->
						<li style="padding-left: 25px;"><a href="ViewPatient"
							id="pmID2">View Patient</a></li>
						<li style="padding-left: 25px;"><a href="RenderSendSMS"
							id="pmID3">Broadcast Messages</a></li>
					</ul></li>
				<li><a id="repID"><i class="fa fa-file-text-o"></i>Reports<span
						class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
						<%-- <li style="padding-left:25px;"><a id="repID1">Clinical Data<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderPatientReport" id="repID2">Generate Report</a>
	                            </li>
	                             <li class="sub_menu" style="padding-left:25px;"><a href="RenderExecuteReport" id="repID3">Execute Report</a>
	                            </li>
	                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Diagnosis-based Reports</a>
	                            </li>
	                            <li class="sub_menu" style="padding-left:25px;"><a href="EditUserList">Drug-based Reports</a>
	                            </li>
	                           </ul>
	                        </li> --%>
						<li style="padding-left: 25px;"><a id="repID4">Billing
								data<span class="fa fa-chevron-down"></span>
						</a>
							<ul class="nav child_menu">
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderBillingReport" id="repID5">Billing Reports</a></li>
								<li class="sub_menu" style="padding-left: 25px;"><a
									href="RenderCreditReport" id="repID6">Credit Reports</a></li>
							</ul></li>
						<%--   <li style="padding-left:25px;"><a id="repID7">Inventory<span class="fa fa-chevron-down"></span></a>
	                          <ul class="nav child_menu">
	                            <li class="sub_menu" style="padding-left:25px;"><a href="RenderStockReport" id="repID8">Stock Reports</a>
	                            </li>
	                           	<li class="sub_menu" style="padding-left:25px;"><a href="RenderSalesReport" id="repID9">Sales Reports</a>
	                            </li>
	                            
	                           </ul>
	                        </li> --%>
						<!--   <li style="padding-left:25px;"><a href="medicalReport.jsp">Audit Reports</a> -->
						<!-- <li style="padding-left:25px;"><a href="medicalReport.jsp">Medical Reports</a></li>
                      <li style="padding-left:25px;"><a href="#">Referrals</a></li>
                      <li style="padding-left:25px;"><a href="#">Audits</a></li>
                      <li style="padding-left:25px;"><a href="#">Accounts</a></li> -->
					</ul></li>
				<%
				}
				%>
			</ul>
		</div>

	</div>
	<!-- /sidebar menu -->


</body>
</html>