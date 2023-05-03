package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.job4j.order.dto.DishDTO;
import ru.job4j.order.dto.OrderDTO;
import ru.job4j.order.model.Dish;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.persistence.OrderPersist;
import ru.job4j.order.persistence.StatusPersist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderPersist orderPersist;

    private final StatusPersist statusPersist;

    private final ModelMapper modelMapper;

    public OrderDTO save(Order order) {
        Order orderDB = orderPersist.save(order);
        return convertToOrderDTO(order, orderDB);
    }

    public OrderDTO findById(int id) {
        Optional<Order> order = orderPersist.findById(id);
        OrderDTO result = new OrderDTO();
        if (order.isPresent()) {
            result = convertToOrderDTO(order.orElseThrow(), order.get());
        }
        return result;
    }

    public List<OrderDTO> findByAll() {
        List<Order> orderList = (List<Order>) orderPersist.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList) {
            convertToOrderDTO(order, order);
        }
        return orderDTOList;
    }

    public OrderDTO convertToOrderDTO(Order order, Order orderDB) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderDB.getId());
        Optional<Status> status = statusPersist.findById(order.getId());
        status.ifPresent(value -> orderDTO.setStatus(value.getState()));
        orderDTO.setDishes(convertToListDTO(order));
        return orderDTO;
    }

    public DishDTO convertToDishDT0(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }

    public List<DishDTO> convertToListDTO(Order order) {
        return order.getDishes().stream()
                .map(this::convertToDishDT0)
                .collect(Collectors.toList());
    }
}