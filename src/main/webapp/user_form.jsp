<%@ page import="org.example.repositories.UserRepository" %>
<%@ page import="org.example.repositories.RoleRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.entities.Role" %>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"/>
    <title>Users application</title>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Users</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">List</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <form action="${pageContext.request.contextPath}/user" method="post">
                <% RoleRepository roleRepository = (RoleRepository) application.getAttribute("roleRepository");
                    List<Role> roles = roleRepository.findAll();
                %>
                <p>Input first name
                    <label>
                        <input type="text" name="firstName"
                                <% if (request.getAttribute("firstName") != null) { %>
                               value="<%= request.getAttribute("firstName")%>"/>
                        <% } %>
                    </label></p>
                <p>Input last name
                    <label>
                        <input type="text" name="lastName"
                                <% if (request.getAttribute("lastName") != null) { %>
                               value="<%= request.getAttribute("lastName")%>"/>
                        <% } %>
                    </label></p>
                <p>Input nickname
                    <label>
                        <input type="text" name="nickname"
                                <% if (request.getAttribute("nickname") != null) { %>
                               value="<%= request.getAttribute("nickname")%>"/>
                        <% } %>
                    </label></p>
                <p>Input age
                    <label>
                        <input type="text" name="age"
                                <% if (request.getAttribute("age") != null) { %>
                               value="<%= request.getAttribute("age")%>"/>
                        <% } %>
                    </label></p>
                <% if (request.getAttribute("id") != null) { %>
                <p><input type="hidden" name="id" value="<%= request.getAttribute("id")%>"/></p>
                <% } %>
                <p>
                    <select name="select" size="3" multiple="multiple" tabindex="1">
                        <%
                            for (Role role : roles) {
                        %>
                        <option value="<%= role.getId()%>"><%= role.getRoleName()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </p>
                <p><input type="submit" name="submit" value="submit"/></p>
            </form>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>