package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
        request.setAttribute("sessionUser", userDto);
        if (userId != null){
            request.setAttribute("user", userService.getUserById(Long.parseLong(userId)));
        }else {
            request.setAttribute("user", userDto);
        }

        request.getRequestDispatcher("/profile.ftl").forward(request, response);
    }
}
