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

@WebServlet("/set-match")
public class AddMatchServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");

        String matchIdString = request.getParameter("likedId");
        String dislikeIdString = request.getParameter("disLikedId");

        if(matchIdString != null){
            Long matchId = Long.parseLong(matchIdString);
            userService.setMatch(userDto.getId(), matchId);
            if (userService.isInMatch(matchId, userDto.getId()))
                response.sendRedirect("/im?nSel="+matchId);
        }else if (dislikeIdString != null){
            Long dislikeId = Long.parseLong(dislikeIdString);
            userService.setDislike(userDto.getId(), dislikeId);
        }else
            response.sendError(400);
    }
}
