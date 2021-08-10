package ru.antisida.voter.repo.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepo extends JpaRepository<Restaurant, Integer> {


    @Query("SELECT DISTINCT (r) FROM Restaurant r INNER JOIN r.meals m WHERE  m.date =:date")
    List<Restaurant> findActiveByDate(@Param("date") LocalDate ld);

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id =:id")
    int delete(@Param("id") int id);
}
