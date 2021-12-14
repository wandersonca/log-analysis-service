<%@ include file="header.jsp" %>
    <%@ page import="ec.finalproject.persistance.model.Metric" %>
        <%@ page import="ec.finalproject.persistance.model.Application" %>
            <%@ page import="java.util.ArrayList" %>
                <div class="container mt-3">
                    <h3>Metrics</h3>
                    <p>
                        Add some description here.
                    </p>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                        Add Metric
                    </button>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Application</th>
                                <th>Metric Name</th>
                                <th>Log Level</th>
                                <th>Regular Expression</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% ArrayList<Metric> list = (ArrayList<Metric>)
                                    request.getAttribute("metrics");
                                    for (Metric metric : list) {
                                    out.println("<tr>"+
                                        "<td>"+metric.getId()+"</td>"+
                                        "<td>"+metric.getApplication().getName()+"</td>"+
                                        "<td>"+metric.getName()+"</td>"+
                                        "<td>"+metric.getLogLevel()+"</td>"+
                                        "<td>"+metric.getMessageRegex()+"</td>"+
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
                                <form action="/management-console/metric" method="post">
                                    <div class="mb-3 mt-3">
                                        <label for="name" class="form-label">Metric Name:</label>
                                        <input type="text" class="form-control" id="name"
                                            placeholder="Enter an metric name..." name="name">
                                    </div>
                                    <div class="mb-3 mt-3">
                                        <label for="application" class="form-label">Pick an Application:</label>
                                        <select class="form-select" id="application" name="application">
                                            <% ArrayList<Application> appList = (ArrayList<Application>)
                                                    request.getAttribute("apps");
                                                    for (Application app : appList) {
                                                    out.print("<option value=");
                                                    out.print(app.getId());
                                                    out.print(">");
                                                        out.print(app.getName());
                                                        out.print("</option>");
                                                    }%>
                                        </select>
                                    </div>
                                    <div class="mb-3 mt-3">
                                        <label for="logLevel" class="form-label">Message Log Level:</label>
                                        <select class="form-select" id="logLevel" name="logLevel">
                                            <option>ALL</option>
                                            <option>DEBUG</option>
                                            <option>INFO</option>
                                            <option>WARN</option>
                                            <option>ERROR</option>
                                        </select>
                                    </div>
                                    <div class="mb-3 mt-3">
                                        <label for="regex" class="form-label">Message Regular Expression:</label>
                                        <input type="text" class="form-control" id="regex"
                                            placeholder="regular expression" name="regex">
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