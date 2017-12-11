<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <!-- From http://getbootstrap.com/docs/3.3/getting-started -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap -->
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="container">
    <h2>Employees</h2>
    <!--Search Form -->
    <form action="/employee" method="get" id="seachEmployeeForm" role="form">
        <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
        <div class="form-group col-xs-5">
            <input type="text" name="employeeName" id="employeeName" class="form-control" required="true"
                   placeholder="Type the Name or Last Name of the employee"/>
        </div>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span> Search
        </button>
        <br/>
        <br/>
    </form>

    <!--Employees List-->
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <form action="/employee" method="post" id="employeeForm" role="form">
        <input type="hidden" id="idEmployee" name="idEmployee">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty employeeList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Last name</td>
                        <td>Birth date</td>
                        <td>Role</td>
                        <td>Department</td>
                        <td>E-mail</td>
                        <td>Show / Download</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="employee" items="${employeeList}">
                        <c:set var="classSuccess" value=""/>
                        <c:if test="${idEmployee == employee.id}">
                            <c:set var="classSuccess" value="info"/>
                        </c:if>
                        <tr class="${classSuccess}">
                            <td>
                                <a href="/employee?idEmployee=${employee.id}&searchAction=searchById">${employee.id}</a>
                            </td>
                            <td>${employee.name}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.birthDate}</td>
                            <td>${employee.role}</td>
                            <td>${employee.department}</td>
                            <td>${employee.email}</td>
                            <td>
                                <div>
                                    <a href="/file?idEmployee=${employee.id}&fileType=html&action=show" target="_blank">
                                        <span class="glyphicon glyphicon-search" data-toggle="tooltip" title="Show"/>
                                    </a>
                                    <a href="/file?idEmployee=${employee.id}&fileType=html">html</a>
                                </div>
                                <div>
                                    <a href="/file?idEmployee=${employee.id}&fileType=txt&action=show" target="_blank">
                                        <span class="glyphicon glyphicon-search" data-toggle="tooltip" title="Show"/>
                                    </a>
                                    <a href="/file?idEmployee=${employee.id}&fileType=txt">txt</a>
                                </div>
                                <div>
                                    <a href="/file?idEmployee=${employee.id}&fileType=xml&action=show" target="_blank">
                                        <span class="glyphicon glyphicon-search" data-toggle="tooltip" title="Show"/>
                                    </a>
                                    <a href="/file?idEmployee=${employee.id}&fileType=xml">xml</a>
                                </div>
                                <div>
                                    <a href="/file?idEmployee=${employee.id}&fileType=json&action=show" target="_blank">
                                        <span class="glyphicon glyphicon-search" data-toggle="tooltip" title="Show"/>
                                    </a>
                                    <a href="/file?idEmployee=${employee.id}&fileType=json">json</a>
                                </div>
                            </td>
                            <td><a href="#" id="remove"
                                   onclick="document.getElementById('action').value = 'remove';
                                            document.getElementById('idEmployee').value = '${employee.id}';
                                            document.getElementById('employeeForm').submit();">
                                <span class="glyphicon glyphicon-trash"/>
                            </a>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    No people found matching your search criteria
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <form action="jsp/new-employee.jsp">
        <br/>
        <button type="submit" class="btn btn-primary  btn-md">New employee</button>
    </form>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>

<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>
