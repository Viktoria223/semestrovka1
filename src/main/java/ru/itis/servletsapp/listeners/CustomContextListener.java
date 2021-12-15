package ru.itis.servletsapp.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.servletsapp.dao.*;
import ru.itis.servletsapp.dao.impl.*;
import ru.itis.servletsapp.services.*;
import ru.itis.servletsapp.services.impl.*;
import ru.itis.servletsapp.services.validation.Validator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String DB_USERNAME;
        String DB_PASSWORD;
        String DB_URL;
        String DB_DRIVER;
        String IMAGES_STORAGE_PATH;
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Требуется файл properties");
        }
        DB_USERNAME = (String) properties.get("spring.datasource.username");
        DB_PASSWORD = (String) properties.get("spring.datasource.password");
        DB_URL = (String) properties.get("spring.datasource.url");
        DB_DRIVER = (String) properties.get("spring.datasource.driver-class-name");
        IMAGES_STORAGE_PATH = (String) properties.get("storage.images");

        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        MsgRepository msgRepository = new MsgRepositoryImpl(dataSource);
        DialogRepository dialogRepository = new DialogRepositoryImpl(dataSource);

        FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository, usersRepository);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        SignInService signInService = new SignInServiceImpl(usersRepository, passwordEncoder);
        Validator validator = new ValidatorImpl(usersRepository);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder, validator);
        UserService userService = new UserServiceImpl(usersRepository);
        MsgsService msgsService = new MsgsServiceImpl(msgRepository);
        DialogService dialogService = new DialogServiceImpl(dialogRepository);
        ObjectMapper objectMapper = new ObjectMapper();

        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("msgsService", msgsService);
        servletContext.setAttribute("dialogService",dialogService);
        servletContext.setAttribute("passwordEncoder", passwordEncoder);
        servletContext.setAttribute("objectMapper", objectMapper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
