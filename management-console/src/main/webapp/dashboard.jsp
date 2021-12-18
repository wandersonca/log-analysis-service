<%@ include file="header.jsp" %>
<%@ page import="ca.wanderson.persistance.model.MetricCount" %>
<%@ page import="ca.wanderson.persistance.model.Metric" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<div class="container mt-3">
    <h3>Dashboard</h3>
    <form action="/management-console/dashboard" method="get" class="form-inline">
        <select class="form-select mb-2" id="metric" name="metricid" onchange="this.form.submit()">
            <option value=>Metric: All</option>
            <% ArrayList<Metric> metricList = (ArrayList<Metric>)
                    request.getAttribute("metrics");
                    for (Metric metric : metricList) {
                        if(metric.getName().equals(request.getAttribute("metricName"))) {
                            out.println("<option selected value="+metric.getId()+">Metric: "+metric.getName()+"</option>");
                        } else {
                            out.println("<option value="+metric.getId()+">Metric: "+metric.getName()+"</option>");
                        }
                    }%>
        </select>
    </form>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Application</th>
                <th>Metric</th>
                <th>Log Level</th>
                <th>Regex</th>
                <th>Time Interval</th>
                <th>Count</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<MetricCount> list = (ArrayList<MetricCount>) request.getAttribute("counts");
                    if(list.size() == 0){
                        out.println("<tr><td colspan=\"6\">No Collected Data</td></tr>"); 
                    } else {
                        for (MetricCount count : list) {
                            Date start =  new Date(count.getTimeInterval().getTime());
                            Date end = new Date(start.getTime() + 60000); // + 1 minute
                            out.println("<tr>"+
                                "<td>"+count.getMetric().getApplication().getName()+"</td>"+
                                "<td>"+count.getMetric().getName()+"</td>"+
                                "<td>"+count.getMetric().getLogLevel()+"</td>"+
                                "<td>"+count.getMetric().getMessageRegex()+"</td>"+
                                "<td>"+start+" - "+end+"</td>"+
                                "<td>"+count.getCount()+"</td>"+
                                "</tr>");
                        }
                    }%>
        </tbody>
    </table>
    <button type="button" class="btn btn-danger" onclick="clearDashboards()">
        Clear Data
    </button>
</div>
<%@ include file="footer.jsp" %>