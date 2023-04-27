package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.order.dto.OrderDTO;
import ru.job4j.order.model.Order;
import ru.job4j.order.service.OrderService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> findAll() {
        return new ResponseEntity<>(orderService.findByAll(), HttpStatus.FOUND);
    }
}