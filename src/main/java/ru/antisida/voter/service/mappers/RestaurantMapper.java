package ru.antisida.voter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.antisida.voter.model.Restaurant;
import ru.antisida.voter.to.RestaurantTo;

import java.util.List;

@Component
@Mapper (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant toEntity(RestaurantTo restaurantTo);

    RestaurantTo toTo(Restaurant restaurant);

    List<RestaurantTo> toTos (List<Restaurant> restaurants);

    void updateEntity(RestaurantTo source, @MappingTarget Restaurant target);
}
