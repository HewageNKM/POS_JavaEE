package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderItemsDTO {
    private String item_id;
    private String item_name;
    private double item_price;
    private int item_qty;
    private double item_total;
}
