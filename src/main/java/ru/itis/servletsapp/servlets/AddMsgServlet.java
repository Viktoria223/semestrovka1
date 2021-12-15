package ru.itis.servletsapp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.servletsapp.dto.MsgDto;
import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.services.DialogService;
import ru.itis.servletsapp.services.MsgsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/add-msg")
public class AddMsgServlet extends HttpServlet {
    private MsgsService msgsService;
    private DialogService dialogService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) {
        msgsService = (MsgsService) config.getServletContext().getAttribute("msgsService");
        dialogService= (DialogService) config.getServletContext().getAttribute("dialogService");
        objectMapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
        Long dialogId = Long.parseLong(request.getParameter("dId"));
        MsgDto form = MsgDto.builder()
                .content(request.getParameter("content"))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .author(userDto)
                .dialogId(dialogId)
                .build();
        MsgDto createdMsg = msgsService.addMsg(form);
        dialogService.updateTime(dialogId,createdMsg.getCreatedAt());
        objectMapper.writeValue(response.getOutputStream(), createdMsg);
        response.setContentType("application/json");
    }
}
