package ru.antisida.voter.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.to.RestaurantTo;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestaurantTo restaurantTo, @PathVariable("id") int id) {
        restaurantService.update(restaurantTo, id);
    }

    @PostMapping
    public RestaurantTo create(@RequestBody RestaurantTo restaurantTo) {
        return restaurantService.create(restaurantTo);
    }
}
