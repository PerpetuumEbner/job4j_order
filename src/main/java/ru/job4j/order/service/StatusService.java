package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Status;
import ru.job4j.order.persistence.StatusPersist;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {
    private StatusPersist statusPersist;

    public Optional<Status> findById(int id) {
        return statusPersist.findById(id);
    }

    public List<Status> findByAll() {
        return (List<Status>) statusPersist.findAll();
    }
}