package ru.job4j.order.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.order.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DishAPIService {
    @Value("http://localhost:8081/dishes/")
    private String url;

    private final RestTemplate client;

    public DishAPIService(RestTemplate client) {
        this.client = client;
    }

    public Optional<Dish> findById(int id) {
        return Optional.ofNullable(client.getForEntity(
                String.format("%s/findById?id=%s", url, id),
                Dish.class
        ).getBody());
    }

    public List<Dish> findAll() {
        return getList(String.format(
                "%s/findAll", url
        ));
    }

    private List<Dish> getList(String url) {
        List<Dish> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dish>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}