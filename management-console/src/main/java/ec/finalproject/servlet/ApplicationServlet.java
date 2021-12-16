package ec.finalproject.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import ec.finalproject.persistance.model.Application;
import ec.finalproject.persistance.ApplicationDAO;

@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ApplicationDAO ApplicationDAO;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("apps", ApplicationDAO.getApplications());
        RequestDispatcher resultView = request.getRequestDispatcher("application.jsp");
        resultView.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Application application = new Application();
        application.setName(request.getParameter("name"));
        application.setDescription(request.getParameter("description"));
        ApplicationDAO.saveApplication(application);
        doGet(request, response);
    }
}
