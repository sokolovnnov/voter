package ru.antisida.voter.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.antisida.voter.service.MenuService;
import ru.antisida.voter.to.Menu;
import ru.antisida.voter.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE) //fixme URL
public class MenuRestController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return MenuUtil.toDateListMenu(menuService.getAllByDate(ld));
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public List<Menu> getAllByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        return MenuUtil.toRestaurantListMenu(menuService.getAllByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public Menu get(@PathVariable("restaurantId") int restaurantId,
                    @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld) {
        return MenuUtil.toMenu(menuService.getAllByRestaurantAndByDate(restaurantId, ld));
    }

}
