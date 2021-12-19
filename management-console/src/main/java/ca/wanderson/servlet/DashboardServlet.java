package ca.wanderson.servlet;

import ca.wanderson.persistance.ApplicationDAO;
import ca.wanderson.persistance.MetricCountDAO;
import ca.wanderson.persistance.MetricDAO;
import ca.wanderson.persistance.model.MetricCount;
import java.io.IOException;
import java.util.List;
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
     * Renders the dashboard page.
     * @param request The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher resultView = request.getRequestDispatcher("dashboard.jsp");
        String metricId = request.getParameter("metricid");
        request.setAttribute("metrics", metricDAO.getMetrics());
        if (metricId != null && metricId.length() > 0) {
            request.setAttribute("metricName", metricDAO.getMetricById(Long.parseLong(metricId)).getName());
            request.setAttribute("counts", metricCountDAO.getMetricCountsByMetricId(Long.parseLong(metricId)));
        } else {
            List<MetricCount> metricCount = metricCountDAO.getMetricCounts();
            request.setAttribute("counts", metricCount);
        }
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
