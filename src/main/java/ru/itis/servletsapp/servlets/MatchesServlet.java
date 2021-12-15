package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
        User pair = userService.getPair(userDto.getId());

        if (pair != null)
        request.setAttribute("userPair",pair);

        request.getRequestDispatcher("matches.ftl").forward(request,response);
    }
}
