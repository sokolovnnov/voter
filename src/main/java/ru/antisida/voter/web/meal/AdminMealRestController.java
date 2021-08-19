package ru.antisida.voter.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.service.MealService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/meal", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Meal> create(@RequestBody Meal meal) {
        Meal created = mealService.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/meal" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created); // fixme user
    }

    /*@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Meal update(@RequestBody Meal meal, @PathVariable("id") int id) {
        //fixme id для проверки ту ли сущность мы обновляем
        return mealService.update(meal);
    }
*/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        mealService.delete(id);
    }

    @GetMapping()
    public List<Meal> getAll() {
        return mealService.getAll();
    }

    @GetMapping("/date")
    public List<Meal> getAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        if (ld == null) ld = LocalDate.now();
        return mealService.getAllByDate(ld);
    }
    @GetMapping("/restaurant/{id}")
    public List<Meal> getAllByRestaurant(@PathVariable("id") int restaurantId) {
        return mealService.getAllByRestaurant(restaurantId);
    }
}
