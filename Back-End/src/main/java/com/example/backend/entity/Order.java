package com.example.backend.entity;

import com.example.backend.dto.OrderItemsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Order {
    private String id;
    private String customer_id;
    private List<OrderItems> items;
    private double order_total;
    private double discount;
    private LocalDate date;
}

