package ru.antisida.voter.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.to.Menu;
import ru.antisida.voter.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable("id") int id) {
        return restaurantService.get(id);
    }

    @GetMapping("/active")
    public List<RestaurantTo> getActiveByDate(
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return restaurantService.getActiveByDate(ld);
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/menu")
    public List<Menu> getAllMenuByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return restaurantService.getAllMenuByDate(ld);
    }

    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getAllMenuByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        return restaurantService.getAllMenuByRestaurant(restaurantId);
    }

    @GetMapping("/{restaurantId}/menu")
    public Menu getMenu(@PathVariable("restaurantId") int restaurantId,
                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return restaurantService.getMenu(restaurantId, ld);
    }
}
