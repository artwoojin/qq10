package com.project.qq10.domain.mapper;

import com.project.qq10.domain.dto.UserDto;
import com.project.qq10.domain.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostToUser(UserDto.Post post);

    UserDto.Response userToUserResponse(User user);

    User userPatchToUser(UserDto.Patch patch);

}