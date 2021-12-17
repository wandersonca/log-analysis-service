package ec.finalproject.servlet;

import ec.finalproject.persistance.ApplicationDAO;
import ec.finalproject.persistance.MetricCountDAO;
import ec.finalproject.persistance.MetricDAO;
import ec.finalproject.persistance.model.MetricCount;

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
            for(MetricCount mc : metricCount) {
                LOGGER.error("Metric Count: " + mc.getMetric().getName() + " " + mc.getCount());
            }
            request.setAttribute("counts", metricCount);
        }
        resultView.forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        metricCountDAO.deleteAllMetricCounts();
    }
}
