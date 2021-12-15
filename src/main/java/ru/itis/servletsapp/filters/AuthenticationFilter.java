package ru.itis.servletsapp.filters;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.exceptions.ValidationException;
import ru.itis.servletsapp.services.SignInService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private SignInService signInService;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext context = filterConfig.getServletContext();
        this.signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().startsWith("/resources")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean isAuthenticated = false;
        boolean sessionExists = session != null;
        boolean isRequestOnAuthPage = request.getRequestURI().equals("/sign-in") ||
                request.getRequestURI().equals("/sign-up");

        if (sessionExists) {
            isAuthenticated = session.getAttribute("user") != null;
        }

        if(isAuthenticated == false && request.getCookies() != null) {
            Optional<Cookie> optionalTokenCookie = Arrays.stream(request.getCookies())
                    .filter(item -> item.getName().equals("token"))
                    .findFirst();
            if(optionalTokenCookie.isPresent()) {
                String token = optionalTokenCookie.get().getValue();
                try {
                    UserDto userDto = signInService.signIn(token);
                    session = request.getSession(true);
                    session.setAttribute("user", userDto);
                    isAuthenticated = true;
                } catch (ValidationException ignored) {}
            }
        }

        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
            filterChain.doFilter(request, response);
        } else if (isAuthenticated) {
            response.sendRedirect("/profile");
        } else {
            response.sendRedirect("/sign-in");
        }
    }

    @Override
    public void destroy() {}
}
