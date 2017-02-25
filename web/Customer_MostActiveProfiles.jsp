
<%-- 
    Document   : Customer_MostActiveProfiles.jsp
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
                <a class="navbar-brand" href="CustomerDashboard.jsp">JSS Online Dating System </a>
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
                        <a href="CustomerDashboard.jsp"><i class="fa fa-fw fa-dashboard"></i> Home</a>
                    </li>
					<li>
                        <a href="CustomerPendingDate.jsp"><i class="fa fa-fw fa-dashboard"></i> Pending Dates</a>
                    </li>
					<li>
                        <a href="CustomerPastDate.jsp"><i class="fa fa-fw fa-dashboard"></i> Past Dates</a>
                    </li>
					<li>
                        <a href="Customer_Suggestion.jsp"><i class="fa fa-fw fa-dashboard"></i> Suggestion Date List</a>
                    </li>
					<li>
                        <a href="Customer_FavoriteList.jsp"><i class="fa fa-fw fa-dashboard"></i> Profile's Favorite List("Likes")</a>
                    </li>
					<li>
                        <a href="Customer_MostActiveProfiles.jsp"><i class="fa fa-fw fa-dashboard"></i> Most Active Profiles</a>
                    </li>
					<li>
                        <a href="Customer_MosHiglyRatedProfiles.jsp"><i class="fa fa-fw fa-dashboard"></i> Most Highly Rated Profiles</a>
                    </li>
					<li>
                        <a href="Customer_PopularGeoLocations.jsp"><i class="fa fa-fw fa-dashboard"></i> Popular geo-date locations</a>
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
                            Most Active Profiles
                            <small>Most Active Profiles Form</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="Customer_MostActiveProfiles.jsp">Dashboard</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
             

                    <div class="row">
                        <div class="col-lg-6">
                            <form action="MostActiveProfilesServlet" method="post">
                                <div class="form-group">
                                    <p>Click submit to see most active profiles</p> 
                                    <input type="hidden" name="login" value="${sessionScope.login}">

                                    <button type="submit" class="btn btn-default">Submit</button>

                                </div>
                            </form>
                            <div class="table-responsive">
                                <table border=1>
                                    ${param.message}
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- jQuery Version 1.11.0 -->
        <script src="js/jquery-1.11.0.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

    </body>

</html>
