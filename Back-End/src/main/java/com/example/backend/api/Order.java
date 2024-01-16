package com.example.backend.api;

import com.example.backend.dto.OrderDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.OrderServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

import static com.example.backend.service.ServiceFactory.ServiceType.ORDER;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class Order extends HttpServlet {
    private final OrderServiceImpl orderService = (OrderServiceImpl) ServiceFactory.getInstance().getService(ORDER);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = gson.fromJson(req.getReader(), OrderDTO.class);
        try {
            boolean save = orderService.save(orderDTO);
            if (save) {
                resp.getWriter().println(HttpServletResponse.SC_CREATED);
            } else {
                resp.getWriter().println(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
