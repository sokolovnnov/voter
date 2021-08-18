package ru.antisida.voter.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.service.RestaurantService;

@RestController
@RequestMapping(value = "/rest/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController {

    @Autowired
    private RestaurantService restaurantService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        restaurantService.update(restaurant, id);
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantService.create(restaurant);
    }
}
