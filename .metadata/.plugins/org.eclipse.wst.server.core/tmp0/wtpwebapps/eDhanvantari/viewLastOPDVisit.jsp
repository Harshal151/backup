<%@page import="com.edhanvantari.daoImpl.LoginDAOImpl"%>
<%@page import="com.edhanvantari.daoInf.LoginDAOInf"%>
<%@page import="com.edhanvantari.util.ConfigurationUtil"%>
<%@page import="com.edhanvantari.util.ActivityStatus"%>
<%@page import="com.edhanvantari.util.ConfigXMLUtil"%>
<%@page import="com.edhanvantari.form.LoginForm"%>
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

    <title>Last OPD Visit List | E-Dhanvantari</title>

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
    
    <%
		LoginForm loginForm = (LoginForm) session.getAttribute("USER");
		System.out.println("User specialization from session :::: "+loginForm.getSpecailization());
		
		if(loginForm.getSpecailization() == null){
			loginForm.setSpecailization("admin");
		}
	%>
    
  </head>

  <body class="">
  
    <div class="container body">
      <div class="main_container">

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                <div class="page-title">
                      <div class="title_left">
                        <h3>LAST OPD VISIT DETAILS</h3>
                      </div>
                    </div>

                    <div class="ln_solid" style="margin-top:0px;"></div>
                    
                  <div class="x_content">
                  
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
		            
		            <h4 style="margin-bottom: 25px;text-decoration: underline;"><b>LAST VISIT DETAILS</b></h4>
		            
		            <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%" >
                      <thead>
                        <tr>
                          <th>Visit No</th>
                          <th>Visit Type</th>
                          <th>Visit Date</th>
                          <th>Visit Time From</th>
                          <th>Visit Time To</th>
                          <th>Diagnosis</th>
                          <th>Comments</th>
                        </tr>
                      </thead>
                      <tbody>
                        <s:iterator value="patientList" var="UserForm">
                        	<tr>
                        			<td><s:property value="visitNumber" /></td>
										
									<td><s:property value="visitType" /></td>
									
									<td><s:property value="visitDate" /></td>
									
									<td><s:property value="visitFromTime" /></td>
									
									<td><s:property value="visitToTime" /></td>
									
									<td><s:property value="diagnosis" /></td>
									
									<td><s:property value="medicalNotes" escapeHtml="true" /></td>

                        	</tr>
                        </s:iterator>
                      </tbody>
                    </table>
                    																																																																																						
					<div class="ln_solid"></div>
					
					<h4 style="margin-bottom: 25px;text-decoration: underline;"><b>LAST PRESCRIPTION DETAILS</b></h4>
					
					<table id="datatable-responsive1" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Trade Name</th>
                          <th>Dose</th>
                          <th>Frequency</th>
                          <th>No. of Pills</th>
                          <th>No. of Days</th>
                          <th>Comments</th>
                        </tr>
                      </thead>
                      <tbody>
                        <s:iterator value="prescriptionList" var="UserForm">
                        	<tr>
                        			<td style="font-size: 14px;text-align: center;"><s:property value="count" /></td>
						
									<td style="font-size: 14px;text-align: center;"><s:property value="tradeName"/></td>
					                            			
					                <td style="font-size: 14px;text-align: center;"><s:property value="dosage"/></td>
					                            			
					                <td style="font-size: 14px;text-align: center;"><s:property value="frequency"/></td>
					                            			
					                <td style="font-size: 14px;text-align: center;"><s:property value="productQuantity"/></td>
					                            			
					                <td style="font-size: 14px;text-align: center;"><s:property value="noOfDays"/></td>
					                            			
					                <td style="font-size: 14px;text-align: center;"><s:property value="comment"/></td>

                        	</tr>
                        </s:iterator>
                      </tbody>
                    </table>
					
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /page content -->

        
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
    <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>

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
    
    <!-- Datatables -->
    <script>
      $(document).ready(function() {

        $('#datatable-responsive').DataTable();
        $('#datatable-responsive1').DataTable();

      });
    </script>
    <!-- /Datatables -->
    
  </body>
</html>