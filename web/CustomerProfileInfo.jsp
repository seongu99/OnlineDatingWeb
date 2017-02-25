<%-- 
    Document   : CustomerDashboard
    Created on : Nov 28, 2014, 7:04:49 PM
    Author     : seongu
--%>

<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
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

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
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
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
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
                        <a href="Customer_MosHiglyRatedProfiles.jsp"><i class="fa fa-fw fa-dashboard"></i> Most Higly Rated Profiles</a>
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
                            Profile Information
                            <small>${param.id}</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="CustomerDashboard.jsp">Dashboard</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <!--User's profile information displayed here in table-->
                                <table border=1>

                <%
                    String id = request.getParameter("id");
                    String query = "SELECT Age,M_F AS 'Gender',Hobbies,Height,Weight,HairColor FROM Profile WHERE ProfileID='" + id + "'";
                    java.sql.ResultSet rs = DBConnection.ExecQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                %>
                <TR>
                    <%
                    for (int i = 0; i < columnCount; i++) {%>
                    <TH> <%out.print(rsmd.getColumnLabel(i + 1));%> </TH>
                        <% }%>
                </TR>

                <%while (rs.next()) {%>
                <TR>
                    <%for (int i = 0; i < columnCount; i++) {%>
                    <TD><%out.print(rs.getString(i + 1));%> </TD>
                        <% }%>
                </TR>
                <%}
                %>
                                </table>
                                <br />
                <div>
                 ${param.likeResponse}       
                    <form action="LikeServlet" method="post" style="float:left">
                    <div class="form-group">
                        <input type="hidden" name="liker" value="${sessionScope.login}">
                        <input type="hidden" name="likee" value="${param.id}">
                        <input type="submit" value="Like" />
                                
                    </div>
                    </form>   
                  
                        
                        <%
                String query1 = "CREATE OR REPLACE VIEW ProfilesEmails AS SELECT R.ProfileID, " +
                "P.Email, P.SSN FROM Profile R, Person P WHERE P.SSN=R.OwnerSSN";    
                String query2 = "SELECT * FROM Date D, Profile R, ProfilesEmails E WHERE D.Profile1='" + id + 
                        "' AND D.Comments='Requested' AND D.Profile2=R.ProfileID AND R.OwnerSSN=E.SSN AND E.Email='" + session.getAttribute("login") + "'";
                    DBConnection.ExecQuery(query1);
                    rs = DBConnection.ExecQuery(query2);
                    if (rs.next()) {
                %>
                        <form action="AcceptDateServlet" method="post" style="float:left;margin-left: 10px;margin-right: 10px">
                    <div class="form-group">
                        <input type="hidden" name="requestor" value="${sessionScope.login}">
                        <input type="hidden" name="requestee" value="${param.id}">
                        <input type="submit" value="Accept Date" />
                                
                    </div>
                    </form> 
                        <form action="RejectDateServlet" method="post">
                    <div class="form-group">
                        <input type="hidden" name="requestor" value="${sessionScope.login}">
                        <input type="hidden" name="requestee" value="${param.id}">
                        <input type="submit" value="Reject Date" />
                                
                    </div>
                    </form> 
                 <%} else {%>
                        
                    <form action="RequestDateServlet" method="post" style="float:left;margin-left: 10px">
                    <div class="form-group">
                        <input type="hidden" name="requestor" value="${sessionScope.login}">
                        <input type="hidden" name="requestee" value="${param.id}">
                        <input type="submit" value="Request a date" />
                                
                    </div>
                    </form> 
                        
                  <%}%>      
                        
                     <br /><br /><br />
                    <form action="ReferralServlet" method="post">
                    <div class="form-group">
                        <label> Refer ${param.id} to:</label>
                        <input class="form-control" placeholder="Profile ID" name="profileC" >
                        <input type="hidden" name="profileA" value="${sessionScope.login}">
                        <input type="hidden" name="profileB" value="${param.id}">
                        <p class="help-block">e.g: JohnSmith123</p>

                        <button type="submit" class="btn btn-default">Submit</button>
                                
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

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
