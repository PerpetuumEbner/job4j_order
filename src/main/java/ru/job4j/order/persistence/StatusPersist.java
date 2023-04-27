package ru.job4j.order.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.order.model.Status;

@Repository
public interface StatusPersist extends CrudRepository<Status, Integer> {
}