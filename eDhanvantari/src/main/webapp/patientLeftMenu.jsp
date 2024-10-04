<%@page import="com.edhanvantari.daoImpl.PatientDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.PatientDAOInf"%>
<%@page import="com.edhanvantari.service.eDhanvantariServiceImpl"%>
<%@page import="com.edhanvantari.service.eDhanvantariServiceInf"%>
<%@page import="com.edhanvantari.daoImpl.RegistrationDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.RegistrationDAOinf"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.edhanvantari.util.ConfigurationUtil"%>
<%@page import="com.edhanvantari.util.ActivityStatus"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.edhanvantari.daoImpl.LoginDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.LoginDAOInf"%>
<%@page import="com.edhanvantari.daoImpl.ClinicDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.ClinicDAOInf"%>
<%@page import="com.edhanvantari.util.ConfigXMLUtil"%>
<%@page import="com.edhanvantari.form.LoginForm"%>
<%@page import="com.edhanvantari.form.PatientForm"%>
<%@ taglib uri="/struts-tags" prefix="s" %>   
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
    
    
    <!-- For login message -->
    
    <%
	    LoginForm form = (LoginForm) session.getAttribute("USER");
		if(session.getAttribute("USER") == "" || session.getAttribute("USER") == null){
		String loginMessage = "Plase login using valid credentials";
		request.setAttribute("loginMessage",loginMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Pindex.jsp");
		dispatcher.forward(request,response);
		
		
	}
	%>

	<script type="text/javascript">
      function windowOpen(clinicID){
    	  document.location="RenderPatientVisitList";
    	  				
      }
    </script>
    
     <style type="text/css">
		    li{
				font-family: AVGARDM_2;
				font-size: 15px;
			}
   </style>
    

</head>

<body class="nav-md" >
  
            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <div class="navbar nav_title" style="border: 0; height:60px;">
			
					<div style="margin-top: -13%;">
		              <a href="http://www.edhanvantari.com" target="_blank" class="site_title" id="edhanLinkID" style="height:62px; background-color:white;">
		              <img src="images/Icon.png" height="50px" style="margin-left: 3px;" >
		              <img src="images/Text.png" height="15px" style="width: 120px; height:22%; margin-left: 8%;"></a>
		            </div>
		         </div>
                
              		 
                          
                <ul class="nav side-menu">
                 <li><a onClick="windowOpen();" id="dashboardID"><i class="fa fa-tachometer"></i>Dashboard</a></li>
                </ul>
              </div>
              </div>
</body>
</html>