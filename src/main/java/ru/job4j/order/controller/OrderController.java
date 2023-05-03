package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.order.dto.OrderDTO;
import ru.job4j.order.model.Order;
import ru.job4j.order.service.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> findAll() {
        return new ResponseEntity<>(orderService.findByAll(), HttpStatus.FOUND);
    }
}