package ru.antisida.voter.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.RestaurantService;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE) //fixme URL
public class MenuRestController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/menu")
    public List<Menu> getAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return restaurantService.getAllMenuByDate(ld);
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public List<Menu> getAllByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        return restaurantService.getAllMenuByRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public Menu getMenu(@PathVariable("restaurantId") int restaurantId,
                    @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return restaurantService.getMenu(restaurantId, ld);
    }

}
