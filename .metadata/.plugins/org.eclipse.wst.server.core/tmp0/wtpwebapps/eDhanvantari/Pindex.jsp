<%@page import="com.edhanvantari.util.ConfigListenerUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="images/Icon.png">

    <title>Patient Login | E-Dhanvantari</title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    

    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
    
    <!-- For Back, forward and refresh button -->
    
    <script type="text/javascript">
    		window.menubar.visible = false;
        	window.toolbar.visible = false;
	</script>
	
	<!-- Ends -->

	<!-- For success and error messages (to remove . before messages) -->
	<style type="text/css">

		ul.actionMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		ul.errorMessage{
			list-style:none;
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		#sessionMsgID{
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}
		
		#loginMsgID{
			font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
		}

		#footer {
		  left: 0;
		  position: fixed;
		  right: 0;
		 
		}
		
		html,body{
		    height: 100%
		}
		
	</style>
	
	<style type="text/css">
	
	footer {
	    background: #F7F7F7;
	    margin-left: 0px;
	}
	
	.login {
	 	
	    background: #FFFFFF;
	   
	}
	
	.login_content h1:before, .login_content h1:after {
	    content: "";
	    height: 1px;
	    position: absolute;
	    top: 10px;
	    width: 25%;
	}
	</style>
	
<%
	String loginMessage = (String)request.getAttribute("loginMessage");
	if(loginMessage == "" || loginMessage == null){
		loginMessage = "";
	}

%>

<%
	String sessionTimeoutMessage = request.getParameter("message");
	if(sessionTimeoutMessage == "" || sessionTimeoutMessage == null){
		sessionTimeoutMessage = "";
	}
%>

<%
	String realPath = request.getServletContext().getRealPath("/");

    ConfigListenerUtil configUtil = new ConfigListenerUtil();
    
    String version = configUtil.getVersion(realPath);

%>
  </head>

  <body class="login">
  
  <!-- Forgot password modal -->
  
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	    <div class="modal-dialog modal-sm" >
	      <div class="modal-content" >
	
	        <div style="padding:20px;">
	          <center><font style="font-size:16px;">Please enter your registered email address below:</font></center>
	        </div>
	        <div class="app-cam" style="width:100%;margin:0px;">
	       <form action="ForgotPassEmail" method="POST" style="padding:20px;padding-top:0px;">
	       
	       <input type="text" class="text" placeholder="Enter your email address here" name="emailTo" required style="height:35px;width: 100%;color:black;font-size: 1em;outline: none;font-weight: 300;border: none;background:transparent;margin:0 0 1em 0;border-radius: 2px;-webkit-border-radius: 2px;-moz-border-radius: 2px;-o-border-radius: 2px;border:1px solid gray;">
	        
	        <center>
	 		
	 		<button class="btn btn-primary" style="height:40px; border-radius:2px;" type="button" data-dismiss="modal">Cancel</button>
	        
	        <input type="submit" class="btn btn-success " value="Submit">       
		
	         </center>
	         
	         </form>
	       </div>
	
	      </div>
	    </div>
	  </div>
  
  <!-- Ends -->
  
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <!-- <div class="login_wrapper">
        <div class="animate form login_form">
        
        <div align="center">
        	<img alt="eDhanvantari Logo" src="images/Logo_e-dhanvantari.png" style="height: 150px;" class="img-responsive">
         </div> -->
      <div class="container">
        <div class="col-md-9 col-sm-9 col-lg-9 col-xs-12"  align="center" style="padding-left: 0px;">
        
        <img alt="banner logo" src="images/ed-banner.jpg" class="img-responsive">
        
       		<!-- <img alt="kovid logo" src="images/kovid_logo.png" style="height: 150px;" class="img-responsive">
       		
       		<div class="clearfix"></div>
	         <div class="" style="padding-top: 45px;">
	         	<h2 style="font-size: 20px;"><b>Kovid Bioanalytics&reg;</b></h2>
	          	<a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;font-size: 15px;" target="_blank">www.kovidbioanalytics.com</a>
	        	<br><br>
	        	<p>6, Vijayshree, Swami Samarth Society, Shridharnagar, Dhankawadi, Pune 411043</p>
	        	<p><i class="fas fa-mobile-alt"></i>+91 942 168 4249</p>
	         </div> -->
       		
        </div> 
        <div class="col-md-3 col-sm-3 col-lg-3 col-xs-12"  align="center" style="padding-top: 0px;">
       
        	<a href="http://www.edhanvantari.com" style="text-decoration: underline;" target="_blank"><img alt="eDhanvantari Logo" src="images/RGB Original.png" style="height: 150px; margin-top:10%; " class="img-responsive"></a>
        	     <%-- <h2 style=" text-align:  center; padding-bottom: 0px; padding-top: 0px;margin-bottom: 0;margin-top:0;">eDhanvantari <%=version %></h2>
        <sub><b>CLINIC AT YOUR FINGERTIPS!!</b></sub> --%>
       
          <section class="login_content" style="padding-top:0px;margin-top:0px;max-width: 75%; ">
            <form action="PatientP" method="POST">
              <h4 style="color:#107B95; font-size:30px;" >Patient Login </h4> 
              
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
				<center>
					<font id="sessionMsgID" style="color: red;font-size:16px;"><%=sessionTimeoutMessage %></font>
				</center>
				<center>
					<font id="loginMsgID" style="color: red;font-size:16px;"><%=loginMessage %></font>
				</center>
              </div>
              
              <div style="margin-top:10px;">
                <input type="text" class="form-control" name="username" placeholder="Username" required="required" />
              </div>
              <div>
                <input type="password" class="form-control" name="password" placeholder="Password" required="required" />
              </div>
              <div>
              	<button type="submit" class="btn btn-default submit">Log in</button>
               <!--  <a class="reset_pass" href="#myModal" data-toggle="modal">Forget password?</a> -->
              </div>

              <div class="clearfix"></div>
              
              <div class="separator">
                
              </div>
              
            </form>
          </section>
        </div>
      </div>
    </div>
    
    <!-- footer content -->
       <footer id="" class="navbar-fixed-bottom hidden-xs hidden-sm">
         <div class="pull-center" align="center">
           Powered by <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">www.kovidbioanalytics.com</a>
         </div>
         <div class="clearfix"></div>
         <div class="pull-center" align="center">
           &copy;2019 <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">Kovid Bioanalytics&reg;</a>
         </div>
       </footer>
       <footer id="" style="position: relative !important;" class="navbar-fixed-bottom hidden-lg hidden-md">
         <div class="pull-center" align="center">
           Powered by <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">www.kovidbioanalytics.com</a>
         </div>
         <div class="clearfix"></div>
         <div class="pull-center" align="center">
           &copy;2019 <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">Kovid Bioanalytics&reg;</a>
         </div>
       </footer>
       <!-- /footer content -->
       
  </body>
</html>