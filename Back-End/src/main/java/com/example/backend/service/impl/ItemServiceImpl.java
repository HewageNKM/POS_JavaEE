package com.example.backend.service.impl;

import com.example.backend.dto.CustomerDTO;
import com.example.backend.dto.ItemDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import com.example.backend.service.interfaces.CustomerService;
import com.example.backend.service.interfaces.ItemService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemServiceImpl implements ItemService<ItemDTO> {

    @Override
    public boolean save(ItemDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null && dto.getName() != null && dto.getQty() != 0 && dto.getPrice() != 0.0) {
            Item item = new Item(dto.getId(), dto.getName(), dto.getPrice(),dto.getQty());
            System.out.println("item = " + item);
            return itemDAO.save(item);
        } else {
            return false;
        }
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, NamingException {
       if (dto.getId() != null && dto.getName() != null && dto.getQty() != 0 && dto.getPrice() != 0.0) {
            return itemDAO.update(new Item(dto.getId(), dto.getName(), dto.getPrice(),dto.getQty()));
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(ItemDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null) {
            return itemDAO.delete(new Item(dto.getId(), dto.getName(), dto.getPrice(),dto.getQty()));
        } else {
            return false;
        }
    }

    @Override
    public ItemDTO findById(ItemDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null) {
            Item item = itemDAO.findById(new Item(dto.getId(), dto.getName(), dto.getPrice(),dto.getQty()));
            if (item == null) {
                return null;
            }
            return new ItemDTO(item.getId(), item.getName(), item.getPrice(),item.getQty());
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<ItemDTO> findAll() throws SQLException, NamingException {
        ArrayList<Item> all = itemDAO.findAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getId(), item.getName(), item.getPrice(),item.getQty()));
        }
        return allItems;
    }
}
