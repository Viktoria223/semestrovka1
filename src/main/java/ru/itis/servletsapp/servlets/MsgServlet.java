package ru.itis.servletsapp.servlets;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.apache.logging.log4j.message.Message;
import ru.itis.servletsapp.dto.MsgDto;
import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.Dialog;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.DialogService;
import ru.itis.servletsapp.services.MsgsService;
import ru.itis.servletsapp.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet("/im")
public class MsgServlet extends HttpServlet {
    private UserService userService;
    private MsgsService msgsService;
    private DialogService dialogService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        msgsService = (MsgsService) config.getServletContext().getAttribute("msgsService");
        dialogService = (DialogService) config.getServletContext().getAttribute("dialogService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dialogId = req.getParameter("sel");
        String createDialog = req.getParameter("nSel");
        UserDto userDto = (UserDto) req.getSession(true).getAttribute("user");

        if (dialogId != null) {
            Long dialogIdL = Long.parseLong(dialogId);
            Dialog dialog = dialogService.getDialogById(dialogIdL);
            List<User> users = dialogService.getUsers(dialogIdL);
            if ((users.get(0).getId().equals(userDto.getId()) || users.get(1).getId().equals(userDto.getId())) && dialog != null) {
                redirectToExistDialog(req, resp, dialog, userDto, (users.get(users.size() - users.indexOf(User.from(userDto)) - 1)), msgsService.getByDialogId(dialogIdL));
            }
        } else if (createDialog != null) {
            Long pairId = Long.parseLong(createDialog);
            Optional<Dialog> optionalDialog = dialogService.alreadyExist(userDto.getId(), pairId);
            if (optionalDialog.isPresent()) {
                Dialog dialog = optionalDialog.get();
                redirectToExistDialog(req, resp, dialog, userDto, userService.getUserById(pairId), msgsService.getByDialogId(dialog.getId()));
            }else if (userService.isInMatch(pairId, userDto.getId())) {
                Dialog newDialog = Dialog.builder()
                        .user1(userDto.getId())
                        .user2(pairId)
                        .lastMsg(new Timestamp(System.currentTimeMillis()))
                        .build();
                newDialog = dialogService.createDialog(newDialog);
                req.setAttribute("dialog", newDialog);
                req.setAttribute("user", userDto);
                req.setAttribute("interloc", userService.getUserById(pairId));
                resp.sendRedirect("/im?sel=" + newDialog.getId());
            }
        } else {
            req.setAttribute("dialogs", dialogService.getDialogs(userDto.getId()));
            req.getRequestDispatcher("dialogs.ftl").forward(req, resp);
        }
    }

    private void redirectToExistDialog(HttpServletRequest req, HttpServletResponse resp, Dialog dialog, UserDto userDto, User interloc, List<MsgDto> messages) throws ServletException, IOException {
        req.setAttribute("dialog", dialog);
        req.setAttribute("user", userDto);
        req.setAttribute("interloc", interloc);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("messages.ftl").forward(req, resp);
    }
}
