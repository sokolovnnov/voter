package ru.antisida.voter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.antisida.voter.model.User;
import ru.antisida.voter.to.UserTo;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserTo> toTos(List<User> users);

    User toEntity(UserTo userTo);

    UserTo toTo(User user);

    void updateEntity(UserTo source, @MappingTarget User target);
}
