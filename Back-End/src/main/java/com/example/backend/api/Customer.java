package com.example.backend.api;

import com.example.backend.dto.CustomerDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.CustomerServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.lang.model.element.Modifier;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static com.example.backend.service.ServiceFactory.ServiceType.CUSTOMER;

@WebServlet(name = "Customer", value = "/customer")
public class Customer extends HttpServlet {
    private final CustomerServiceImpl customerService = (CustomerServiceImpl) ServiceFactory.getInstance().getService(CUSTOMER);
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String volume = req.getParameter("volume");
            System.out.println("volume = " + volume);
            switch (volume){
                case "all":
                    try {
                        resp.getWriter().println(gson.toJson(customerService.findAll()));
                    } catch (SQLException | NamingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "single":
                        String id = req.getParameter("id");
                        System.out.println("CustomerID = " + id);
                        CustomerDTO byId = customerService.findById(new CustomerDTO(id, null, null, 0.0));
                        System.out.println("Customer = " + byId);
                        if (byId != null) {
                            resp.getWriter().println(gson.toJson(byId));
                        } else {
                            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        }
                    break;
            }
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CustomerDTO customerDTO = gson.fromJson(req.getReader(), CustomerDTO.class);
            System.out.println("customerDTO = " + customerDTO);
            boolean save = customerService.save(customerDTO);
            if (save) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException | NamingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CustomerDTO customerDTO = gson.fromJson(req.getReader(), CustomerDTO.class);
            boolean update = customerService.update(customerDTO);
            System.out.println("CustomerDTO = " + customerDTO);
            if (update) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException | NamingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("ID: " + req.getParameter("id"));
            boolean delete = customerService.delete(new CustomerDTO(req.getParameter("id"), null, null, 0.0));
            if (delete) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | NamingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }
}
