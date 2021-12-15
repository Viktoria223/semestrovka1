package ru.itis.servletsapp.servlets;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
        System.out.println(userDto.getId());
        userService.deleteToken(userDto.getId());
        request.getSession(true).removeAttribute("user");
        response.sendRedirect("/sign-in");
    }
}
