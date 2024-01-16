package com.example.backend.api;

import java.io.*;
import java.sql.SQLException;

import com.example.backend.dto.UserDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.LoginServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.NamingException;

import static com.example.backend.service.ServiceFactory.ServiceType.LOGIN;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    private final LoginServiceImpl loginService = (LoginServiceImpl) ServiceFactory.getInstance().getService(LOGIN);
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameter("userName")+request.getParameter("password"));
        String username = request.getParameter("userName").toLowerCase();
        String password = request.getParameter("password");
        UserDTO userDTO = new UserDTO(username, password);
        System.out.println(userDTO);
        try {
            UserDTO byId = loginService.findById(userDTO);
            if (byId != null) {
                System.out.println("byId = " + byId);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDTO userDTO = new UserDTO(username, password);
        System.out.println(userDTO);
        try {
            boolean save = loginService.save(userDTO);
            if (save) {
                response.sendRedirect("views/order.html");
            } else {
                response.sendRedirect("index.html");
            }
        } catch (SQLException | NamingException | IOException e) {
            e.printStackTrace();
        }
    }

}