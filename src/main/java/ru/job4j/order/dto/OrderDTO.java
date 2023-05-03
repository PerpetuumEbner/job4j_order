package ru.job4j.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private int id;

    private String status;

    private List<DishDTO> dishes;
}