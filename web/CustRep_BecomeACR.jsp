
<%-- 
    Document   : CustRep_RecordDates.jsp
    Created on : Dec 2, 2014, 11:27:08 PM
    Author     : Jason
--%>


<%@page import="DBWorks.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>JSS Dating Website</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body >

    <div  id="wrapper">

        <!-- Navigation -->
        <nav  class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="CustRepDashboard.jsp">JSS Online Dating System </a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>${sessionScope.login}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="index.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div  class="collapse navbar-collapse navbar-ex1-collapse" >
                <ul  style="height:700px; overflow-y: scroll;" class="nav navbar-nav side-nav">
                     <li>
                        <a href="CustRepDashboard.jsp"><i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
                    </li>
					<li>
                        <a href="CustRep_RecordDates.jsp"><i class="fa fa-fw fa-dashboard"></i> Record dates</a>
                    </li>
					<li>
                        <a href="CustRep_CustomerAdd.jsp"><i class="fa fa-fw fa-dashboard"></i> Add an Customer</a>
                    </li>
					<li>
                        <a href="CustRep_CustomerEdit.jsp"><i class="fa fa-fw fa-dashboard"></i> Edit an Customer</a>
                    </li>
					<li>
                        <a href="CustRep_CustomerDelete.jsp"><i class="fa fa-fw fa-dashboard"></i> Delete an Customer</a>
                    </li>
					<li>
                        <a href="CustRep_ProduceMailLists.jsp"><i class="fa fa-fw fa-dashboard"></i> Produce Mailing Lists</a>
                    </li>
										
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

     
                
                  
<div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Become a CR for a Pending Date
                            <small>Form</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="CustRepDashboard.jsp">Dashboard</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
               <div class="row">
                   
                </div>
                
                <div>
                   ${param.message}
                </div>  
                <div class="col-lg-6">
                    
           <form action="BecomeACRServlet" method="post">
                    <div class="form-group">
                        <label>Profile1</label>
                        <input class="form-control" name="profile1" >
                        <p class="help-block">e.g: Isabelle2014</p>

                        <label>Profile2</label>
                        <input class="form-control" name="profile2">
                        <p class="help-block">e.g: VazquezFromAlajuela</p>

                        <label>Your SSN</label>
                        <input class="form-control" name="crssn">
                        <p class="help-block">e.g: 222-22-2222</p>

                        <label>Comments</label>
                        <input class="form-control" name="comments">
                        <p class="help-block">Change from "Pending"</p>
                        
                        <input type="hidden" name="login" value="${sessionScope.login}">
                     
                        <button type="submit" class="btn btn-default">Submit</button>
                        <button type="reset" class="btn btn-default">Reset</button>
                                
                    </div>
                    <!--<input type="submit" value="Submit">-->
               </form>    
                   
                </div>
                
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery Version 1.11.0 -->
    <script src="js/jquery-1.11.0.js"></script>

    <!--
    <script>
    //Inserts text after submitting. Error checking not included.
    function added() {
        $("#submit-success").empty().append("Submission Testing");
        parent.scrollTo(0, 0);
    }
    $("#formId").submit(function(e){
            e.preventDefault();
        });
    </script>
    -->
    
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

</body>

</html>
