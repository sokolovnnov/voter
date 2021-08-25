package ru.antisida.voter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.antisida.voter.model.Vote;
import ru.antisida.voter.to.VoteTo;

import java.util.List;

@Component
@Mapper (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    @Mapping(target = "restaurant.id",   source = "restaurantId")
    @Mapping(target = "restaurant.name", source = "restaurantName")
    @Mapping(target = "user.id",         source = "userId")
    @Mapping(target = "user.name",       source = "userName")
    Vote toEntity(VoteTo voteTo);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    VoteTo toTo(Vote vote);

    List<VoteTo> toTos (List<Vote> votes);

    List<Vote> toListEntity (List<VoteTo> votes);

    void updateEntity(VoteTo source, @MappingTarget Vote target);
}
