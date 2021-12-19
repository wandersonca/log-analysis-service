package ca.wanderson.servlet;

import ca.wanderson.persistance.ApplicationDAO;
import ca.wanderson.persistance.MetricCountDAO;
import ca.wanderson.persistance.MetricDAO;
import ca.wanderson.persistance.model.MetricCount;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;

/**
 * Dashboard Servlet
 * @author Will Anderson
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(DashboardServlet.class);

    @EJB
    private ApplicationDAO applicationDAO;

    @EJB
    private MetricDAO metricDAO;

    @EJB
    private MetricCountDAO metricCountDAO;

    /**
     * Summarize the metrics by day.
     * @param metricCount The metric counts.
     * @return The metric map (by day) with a map of stats.
     */
    private Map<String, Map<String,String>> summarizeMetricsByDay(List<MetricCount> metricCounts) {
        Map<String, Map<String,String>> stats = new HashMap<String, Map<String,String>>();
        for(MetricCount metricCount: metricCounts) {
            String dateKey = (metricCount.getTimeInterval().getYear() + 1900) + "-" + metricCount.getTimeInterval().getMonth() + "-" + metricCount.getTimeInterval().getDate();
            if(!stats.containsKey(dateKey)){
                Map<String,String> stat = new HashMap<String,String>();
                int count =  metricCount.getCount();
                stat.put("count", Integer.toString(count));
                stat.put("hourly-average", Integer.toString(count/24));
                stats.put(dateKey, stat);
            } else {
                Map<String,String> stat = stats.get(dateKey);
                int existingCount = Integer.parseInt(stat.get("count"));
                int count =  metricCount.getCount();
                stat.put("count", Integer.toString(count + existingCount));
                stat.put("hourly-average", Integer.toString((count + existingCount)/24));
                stats.put(dateKey, stat);
            }
        }
        return stats;
    }

    /**
     * Renders the dashboard page.
     * @param request The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher resultView = request.getRequestDispatcher("dashboard.jsp");
        String metricId = request.getParameter("metricid");
        request.setAttribute("metrics", metricDAO.getMetrics());
        List<MetricCount> metricCounts;
        if (metricId != null && metricId.length() > 0) {
            request.setAttribute("metricName", metricDAO.getMetricById(Long.parseLong(metricId)).getName());
            metricCounts = metricCountDAO.getMetricCountsByMetricId(Long.parseLong(metricId));
        } else {
            metricCounts = metricCountDAO.getMetricCounts();
        }
        request.setAttribute("counts", metricCounts);
        request.setAttribute("stats", summarizeMetricsByDay(metricCounts));
        resultView.forward(request, response);
    }

    /**
     * Clears the metric counts.
     * @param request The request.
     * @param response The response.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        metricCountDAO.deleteAllMetricCounts();
    }
}
