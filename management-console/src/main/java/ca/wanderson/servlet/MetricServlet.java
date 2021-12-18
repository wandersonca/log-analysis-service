package ca.wanderson.servlet;

import ca.wanderson.persistance.ApplicationDAO;
import ca.wanderson.persistance.MetricDAO;
import ca.wanderson.persistance.model.Metric;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Metric Servlet
 * @author Will Anderson
 */
@WebServlet("/metric")
public class MetricServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private MetricDAO MetricDAO;

    @EJB
    private ApplicationDAO ApplicationDAO;

    /**
     * Renders the metric page.
     * @param request The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("apps", ApplicationDAO.getApplications());
        String applicationId = request.getParameter("appid");
        if (applicationId != null && applicationId.length() > 0) {
            request.setAttribute("appName", ApplicationDAO.getApplicationById(Long.parseLong(applicationId)).getName());
            request.setAttribute("metrics", MetricDAO.getMetricsByApplicationId(Long.parseLong(applicationId)));
        } else {
            request.setAttribute("metrics", MetricDAO.getMetrics());
        }
        RequestDispatcher resultView = request.getRequestDispatcher("metric.jsp");
        resultView.forward(request, response);
    }

    /**
     * Creates a new metric, then renders the metric page.
     * @param request The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Metric metric = new Metric();
        metric.setName(request.getParameter("name"));
        metric.setLogLevel(request.getParameter("logLevel"));
        metric.setMessageRegex(request.getParameter("regex"));
        Long appId = Long.parseLong(request.getParameter("application"));
        metric.setApplication(ApplicationDAO.getApplicationById(appId));
        MetricDAO.saveMetric(metric);
        doGet(request, response);
    }

    /**
     * Deletes a new metric, then renders the metric page.
     * @param request The request.
     * @param response The response.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("metricid"));
        MetricDAO.deleteMetric(id);
    }
}
