package com.example.backend.api;

import com.example.backend.dto.OrderDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.OrderServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.backend.service.ServiceFactory.ServiceType.ORDER;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class Order extends HttpServlet {
    private final OrderServiceImpl orderService = (OrderServiceImpl) ServiceFactory.getInstance().getService(ORDER);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String volume = req.getParameter("volume");
            switch (volume){
                case "all":
                    try {
                        resp.getWriter().println(gson.toJson(orderService.findAll()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "single":
                    String id = req.getParameter("id");
                    System.out.println(id);
                    ArrayList<Order> byId = orderService.findById(new OrderDTO(id, null, null, 0.0, 0));
                    System.out.println(byId);
                    if (byId != null) {
                        resp.getWriter().println(gson.toJson(byId));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    }
                    break;
            }
        }catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            OrderDTO orderDTO = gson.fromJson(req.getReader(), OrderDTO.class);
            boolean save = orderService.save(orderDTO);
            if (save) {
                resp.getWriter().println(HttpServletResponse.SC_CREATED);
            } else {
                resp.getWriter().println(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            boolean delete = orderService.delete(new OrderDTO(id, null, null, 0.0, 0));
            if (delete) {
                resp.getWriter().println(HttpServletResponse.SC_OK);
            } else {
                resp.getWriter().println(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }
}
