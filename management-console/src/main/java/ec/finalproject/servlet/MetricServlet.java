package ec.finalproject.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import ec.finalproject.persistance.model.Metric;
import ec.finalproject.persistance.ApplicationRepository;
import ec.finalproject.persistance.MetricRepository;

@WebServlet("/metric")
public class MetricServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private MetricRepository metricRepository;

    @EJB
    private ApplicationRepository applicationRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("apps", applicationRepository.getApplications());
        request.setAttribute("metrics", metricRepository.getMetrics());
        RequestDispatcher resultView = request.getRequestDispatcher("metric.jsp");
        resultView.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Metric metric = new Metric();
        metric.setName(request.getParameter("name"));
        metric.setLogLevel(request.getParameter("logLevel"));
        metric.setMessageRegex(request.getParameter("regex"));
        Long appId = Long.parseLong(request.getParameter("application"));
        metric.setApplication(applicationRepository.getApplicationById(appId));
        metricRepository.saveMetric(metric);
        doGet(request, response);
    }
}
