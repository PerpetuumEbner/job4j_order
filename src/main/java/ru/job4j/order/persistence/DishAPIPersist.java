package ru.job4j.order.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.order.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DishAPIPersist {
    @Value("${api-url}")
    private String url;

    private final RestTemplate client;

    public DishAPIPersist(RestTemplate client) {
        this.client = client;
    }

    public Optional<Dish> findById(int id) {
        return Optional.ofNullable(client.getForEntity(
                String.format("%s/%s", url, id),
                Dish.class
        ).getBody());
    }

    public List<Dish> findAll() {
        return getList(String.format(
                "%s/", url
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