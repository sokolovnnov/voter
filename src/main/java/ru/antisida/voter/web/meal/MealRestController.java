package ru.antisida.voter.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.antisida.voter.model.Meal;
import ru.antisida.voter.service.MealService;

@RestController
@RequestMapping(value = "/rest/meal", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {//fixme add logging to methods
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;

    @GetMapping("/{id}")
    public Meal get(@PathVariable("id") int id) {
        return mealService.get(id);
    }

}
