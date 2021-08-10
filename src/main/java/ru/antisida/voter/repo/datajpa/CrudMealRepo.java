package ru.antisida.voter.repo.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepo extends JpaRepository<Meal, Integer> {

    @Query("SELECT m FROM Meal m ORDER BY m.date DESC, m.restaurant.name ASC")
    List<Meal> getAllOrdered();

    @Query("SELECT m FROM Meal m WHERE m.date =:date ORDER BY m.restaurant.name ASC ")
    List<Meal> findAllByDateOrderByRestaurant(@Param("date")LocalDate date);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId")
    List<Meal> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id =:id")
    int delete(@Param("id") int id);


}
