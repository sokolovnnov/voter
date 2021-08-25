package ru.antisida.voter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.antisida.voter.model.User;
import ru.antisida.voter.to.UserCreateTo;

@Component
@Mapper
public interface UserCreateToMapper {

    UserCreateToMapper INSTANCE = Mappers.getMapper(UserCreateToMapper.class);

    User toEntity(UserCreateTo userTo);

    UserCreateTo toTo(User user);

    void updateEntity(UserCreateTo userCreateTo, @MappingTarget User target);
}
