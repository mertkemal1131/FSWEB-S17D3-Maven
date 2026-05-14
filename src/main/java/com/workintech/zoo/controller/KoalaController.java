package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private final Map<Integer, Koala> koalas = new HashMap<>();   // Integer

    @GetMapping
    public List<Koala> getAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable Integer id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala add(@RequestBody Koala koala) {
        if (koala.getId() == null) {
            throw new ZooException("Koala id cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala with id " + koala.getId() + " already exists", HttpStatus.BAD_REQUEST);
        }

        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable Integer id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable Integer id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}