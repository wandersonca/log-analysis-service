<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="ec.finalproject.model.Application" %>
            <%@ page import="java.util.ArrayList" %>
                <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
                <html lang="en">

                <head>
                    <title>Bootstrap Example</title>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
                </head>

                <body>

                    <nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
                        <div class="container">
                            <a class="navbar-brand" href="#">Management Console</a>
                            <div class="collapse navbar-collapse">
                                <ul class="navbar-nav">
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">Applications</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">Metrics</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">Dashboards</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </nav>

                    <div class="container mt-3">
                        <h3>Currently Registered Applications</h3>
                        <p>
                            Add some description here.
                        </p>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                            Add Application
                        </button>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Application Name</th>
                                    <th>Description</th>
                                    <th>Metrics</th>
                                    <th>Dashboards</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% ArrayList<Application> list = (ArrayList<Application>)
                                        request.getAttribute("appList");
                                        for (Application app : list) {
                                        out.println("<tr>"+
                                            "<td>"+app.getId()+"</td>"+
                                            "<td>"+app.getName()+"</td>"+
                                            "<td>"+app.getDescription()+"</td>"+
                                            "<td>Metrics</td>"+
                                            "<td>Dashboards</td>"+
                                            "</tr>");
                                        }%>
                            </tbody>
                        </table>
                    </div>

                    <!-- Add Modal -->
                    <div class="modal fade" id="myModal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">Add Application</h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>

                                <!-- Modal body -->
                                <div class="modal-body">
                                    <form action="/management-console/Application" method="post">
                                        <div class="mb-3 mt-3">
                                            <label for="name" class="form-label">Application Name:</label>
                                            <input type="text" class="form-control" id="name"
                                                placeholder="Enter an application name..." name="name">
                                        </div>
                                        <div class="mb-3 mt-3">
                                            <label for="description" class="form-label">Application Description:</label>
                                            <textarea type="text" class="form-control" id="description"
                                                placeholder="Enter an application description..." name="description"
                                                rows=4></textarea>
                                        </div>
                                        <!-- Modal footer -->
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary"
                                                data-bs-dismiss="modal">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </body>

                </html>