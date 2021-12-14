<%@ include file="header.jsp" %>
    <%@ page import="ec.finalproject.persistance.model.Application" %>
        <%@ page import="java.util.ArrayList" %>
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
                                request.getAttribute("apps");
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
                            <form action="/management-console/application" method="post">
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
                                    <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Save</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            </body>

            </html>