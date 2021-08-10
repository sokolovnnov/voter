package ru.antisida.voter.web;

import org.springframework.stereotype.Controller;
import ru.antisida.voter.service.MenuService;
import ru.antisida.voter.to.Menu;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    public List<Menu> getAllByDate(int userId, LocalDate ld){
        return menuService.getAllByDate(ld);
    }
}
