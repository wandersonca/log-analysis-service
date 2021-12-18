package ca.wanderson.servlet;

import ca.wanderson.persistance.ApplicationDAO;
import ca.wanderson.persistance.model.Application;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Application Servlet
 * @author Will Anderson
 */
@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ApplicationDAO applicationDAO;

    /**
     * Renders the application page.
     * @param request The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("apps", applicationDAO.getApplications());
        RequestDispatcher resultView = request.getRequestDispatcher("application.jsp");
        resultView.forward(request, response);
    }

    /**
     * Creates a new application, then renders the application page.
     * @param request The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Application application = new Application();
        application.setName(request.getParameter("name"));
        application.setDescription(request.getParameter("description"));
        applicationDAO.saveApplication(application);
        doGet(request, response);
    }

    /**
     * Deletes an application, then renders the application page.
     * @param request The request.
     * @param response The response.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("appid"));
        applicationDAO.deleteApplication(id);
    }
}
