package com.example.backend.api;

import com.example.backend.dto.UserDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.LoginServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.backend.service.ServiceFactory.ServiceType.LOGIN;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    private final LoginServiceImpl loginService = (LoginServiceImpl) ServiceFactory.getInstance().getService(LOGIN);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("userName");
            String password = request.getParameter("password");
            UserDTO userDTO = new UserDTO(id, password);
            UserDTO byId = loginService.findById(userDTO);
            if (byId != null) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDTO userDTO = new Gson().fromJson(request.getReader(), UserDTO.class);
            boolean save = loginService.save(userDTO);
            if (save) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDTO userDTO = new Gson().fromJson(req.getReader(), UserDTO.class);
            boolean save = loginService.update(userDTO);
            if (save) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}