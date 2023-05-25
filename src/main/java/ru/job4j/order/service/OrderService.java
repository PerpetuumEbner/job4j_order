package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.dto.DishDTO;
import ru.job4j.order.dto.OrderDTO;
import ru.job4j.order.model.Dish;
import ru.job4j.order.model.Notification;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.persistence.DishAPIPersist;
import ru.job4j.order.persistence.OrderPersist;
import ru.job4j.order.persistence.StatusPersist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderPersist orderPersist;

    private final StatusPersist statusPersist;

    private final DishAPIPersist dishAPIPersist;

    private final ModelMapper modelMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "cooked_order")
    public void receiveOrder(OrderDTO orderDTO) {
        log.debug(orderDTO.toString());
        Optional<Order> order = orderPersist.findById(orderDTO.getId());
        List<Status> statusList = (List<Status>) statusPersist.findAll();
        if (order.isPresent()) {
            for (Status status : statusList) {
                if (status.getStatus().equals(orderDTO.getStatus())) {
                    order.get().setStatusId(status.getId());
                    orderPersist.save(order.get());
                    Notification notification = new Notification();
                    notification.setName(status.getStatus());
                    kafkaTemplate.send("notification", notification);
                }
            }
        }
    }

    public OrderDTO save(Order order) {
        Order orderDB = orderPersist.save(order);
        OrderDTO orderDTO = convertToOrderDTO(order, orderDB);
        kafkaTemplate.send("preorder", orderDTO);
        Optional<Status> status = statusPersist.findById(order.getStatusId());
        if (status.isPresent()) {
            Notification notification = new Notification();
            notification.setName(status.get().getStatus());
            kafkaTemplate.send("notification", notification);
        }
        return orderDTO;
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
        Optional<Status> status = statusPersist.findById(order.getStatusId());
        status.ifPresent(value -> orderDTO.setStatus(value.getStatus()));
        orderDTO.setDishes(convertToListDTO(order));
        return orderDTO;
    }

    public List<DishDTO> convertToListDTO(Order order) {
        List<DishDTO> collect = new ArrayList<>();
        for (Dish dish : order.getDishes()) {
            Dish dishConvert = dishAPIPersist.findById(dish.getId()).orElseThrow();
            DishDTO dishDTO = convertToDishDT0(dishConvert);
            collect.add(dishDTO);
        }
        return collect;
    }

    public DishDTO convertToDishDT0(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }
}