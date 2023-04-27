package ru.job4j.order.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.order.model.Order;

@Repository
public interface OrderPersist extends CrudRepository<Order, Integer> {
}