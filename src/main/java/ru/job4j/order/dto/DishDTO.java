package ru.job4j.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishDTO {
    private String name;

    private String description;
}