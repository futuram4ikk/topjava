package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
     static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createResource(@RequestBody Meal meal) {
        Meal created = super.create(meal);

        URI uriOfMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfMeal).body(created);
    }

    @GetMapping(value = "/filter", produces = APPLICATION_JSON_VALUE)
    public List<MealWithExceed> filter
            (@RequestParam(value = "startDate", required = false)  LocalDate startDate,
             @RequestParam(value = "startTime", required = false)  LocalTime startTime,
             @RequestParam(value = "endDate", required = false)  LocalDate endDate,
             @RequestParam(value = "endTime", required = false)  LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}