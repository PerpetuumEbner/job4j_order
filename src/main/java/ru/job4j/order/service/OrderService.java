package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.dto.OrderDTO;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.persistence.OrderPersist;
import ru.job4j.order.persistence.StatusPersist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderPersist orderPersist;

    private final StatusPersist statusPersist;

    public OrderDTO save(Order order) {
        Order orderDB = orderPersist.save(order);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderDB.getId());
        Optional<Status> status = statusPersist.findById(order.getId());
        status.ifPresent(value -> orderDTO.setStatus(value.getState()));
        return orderDTO;
    }

    public Optional<Order> findById(int id) {
        return orderPersist.findById(id);
    }

    public List<OrderDTO> findByAll() {
        List<Order> orderList = (List<Order>) orderPersist.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            Optional<Status> status = statusPersist.findById(order.getStatusId());
            status.ifPresent(value -> orderDTO.setStatus(value.getState()));
        }
        return orderDTOList;
    }
}