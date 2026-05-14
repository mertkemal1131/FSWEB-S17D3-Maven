package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private final Map<Integer, Kangaroo> kangaroos = new HashMap<>();   // Integer yaptık

    @GetMapping
    public List<Kangaroo> getAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable Integer id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo add(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() == null) {
            throw new ZooException("Kangaroo id cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Kangaroo with id " + kangaroo.getId() + " already exists", HttpStatus.BAD_REQUEST);
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable Integer id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable Integer id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}