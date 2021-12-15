package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.exceptions.ValidationException;
import ru.itis.servletsapp.services.SignUpService;
import ru.itis.servletsapp.services.validation.ErrorEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.signUpService = (SignUpService) context.getAttribute("signUpService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign_up.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserForm form;
        try {
            form = UserForm.builder()
                    .firstName(request.getParameter("firstName"))
                    .lastName(request.getParameter("lastName"))
                    .email(request.getParameter("email"))
                    .gender(request.getParameter("gender"))
                    .description(request.getParameter("description"))
                    .password(request.getParameter("password"))
                    .age(Integer.valueOf(request.getParameter("age")))
                    .build();
        } catch (NumberFormatException e) {
            request.setAttribute("error", ErrorEntity.INVALID_REQUEST);
            System.out.println(ErrorEntity.INVALID_REQUEST.getMessage());
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }

        try {
            signUpService.signUp(form);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getEntity());
            System.out.println(e.getMessage());
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }


        response.sendRedirect("/sign-in");
    }
}
