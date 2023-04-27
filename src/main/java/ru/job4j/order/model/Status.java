package ru.job4j.order.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String state;
}