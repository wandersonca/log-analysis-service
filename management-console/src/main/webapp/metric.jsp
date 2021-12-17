<%@ include file="header.jsp" %>
<%@ page import="ec.finalproject.persistance.model.Metric" %>
<%@ page import="ec.finalproject.persistance.model.Application" %>
<%@ page import="java.util.ArrayList" %>
<div class="container mt-3">
    <h3>Metrics</h3>
    <form action="/management-console/metric" method="get" class="form-inline">
        <select class="form-select mb-2" id="application" name="appid" onchange="this.form.submit()">
            <option value=>Application: All</option>
            <% ArrayList<Application> appList = (ArrayList<Application>)
                    request.getAttribute("apps");
                    for (Application app : appList) {
                        if(app.getName().equals(request.getAttribute("appName"))) {
                            out.println("<option selected value="+app.getId()+">Application: "+app.getName()+"</option>");
                        } else {
                            out.println("<option value="+app.getId()+">Application: "+app.getName()+"</option>");
                        }
                    }%>
        </select>
    </form>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>ID</th>
                <th>Application</th>
                <th>Metric Name</th>
                <th>Log Level</th>
                <th>Regular Expression</th>
                <th>Dashboard</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<Metric> list = (ArrayList<Metric>) request.getAttribute("metrics");
                if (list.size() == 0) {
                    out.println("<tr><td colspan=\"6\">No Metrics Found</td></tr>");
                } else {
                    for (Metric metric : list) {
                    out.println("<tr class=\"align-middle\">"+
                        "<td>"+metric.getId()+"</td>"+
                        "<td>"+metric.getApplication().getName()+"</td>"+
                        "<td>"+metric.getName()+"</td>"+
                        "<td>"+metric.getLogLevel()+"</td>"+
                        "<td>"+metric.getMessageRegex()+"</td>"+
                        "<td><a href=/management-console/dashboard?metricid="+metric.getId()+">Dashboard</a></td>"+
                        "<td><button type=\"button\" class=\"btn btn-danger btn-sm\" onclick=deleteMetric("+metric.getId()+")>Delete</button></td>"+
                        "</tr>");
                    }
                }%>
        </tbody>
    </table>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
        Add Metric
    </button>
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
                            <% for (Application app : appList) {
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
<%@ include file="footer.jsp" %>