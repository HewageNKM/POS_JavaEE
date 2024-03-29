package com.example.backend.api;

import com.example.backend.dto.ItemDTO;
import com.example.backend.service.ServiceFactory;
import com.example.backend.service.impl.ItemServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.backend.service.ServiceFactory.ServiceType.ITEM;

@WebServlet(name = "ItemServlet", urlPatterns = "/item")
public class Item extends HttpServlet {
    private final ItemServiceImpl itemService = (ItemServiceImpl) ServiceFactory.getInstance().getService(ITEM);
    private final Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            boolean delete = itemService.delete(new ItemDTO(id, null, 0.0, 0));
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItemDTO itemDTO = gson.fromJson(req.getReader(), ItemDTO.class);
            boolean update = itemService.update(itemDTO);
            if (update) {
                resp.getWriter().println(HttpServletResponse.SC_OK);
            } else {
                resp.getWriter().println(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItemDTO itemDTO = gson.fromJson(req.getReader(), ItemDTO.class);

            boolean save = itemService.save(itemDTO);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String volume = req.getParameter("volume");
            switch (volume) {
                case "all":
                    try {
                        ArrayList<ItemDTO> all = itemService.findAll();
                        String json = gson.toJson(all);
                        resp.getWriter().println(json);
                    } catch (SQLException | NamingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "single":
                    try {
                        ItemDTO itemDTO = new ItemDTO();
                        itemDTO.setId(req.getParameter("id"));
                        ItemDTO item = itemService.findById(itemDTO);
                        if (item == null) {
                            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        }
                        String json = gson.toJson(item);
                        resp.getWriter().println(json);
                    } catch (SQLException | NamingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
