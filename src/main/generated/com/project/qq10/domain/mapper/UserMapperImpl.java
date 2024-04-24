package com.project.qq10.domain.mapper;

import com.project.qq10.domain.dto.UserDto;
import com.project.qq10.domain.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-24T13:14:29+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userPostToUser(UserDto.Post post) {
        if ( post == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( post.getUserId() );
        user.setUserName( post.getUserName() );
        user.setEmail( post.getEmail() );
        user.setPassword( post.getPassword() );
        user.setPhoneNumber( post.getPhoneNumber() );
        user.setAddress( post.getAddress() );

        return user;
    }

    @Override
    public UserDto.Response userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.Response.ResponseBuilder response = UserDto.Response.builder();

        response.userId( user.getUserId() );
        response.userName( user.getUserName() );
        response.email( user.getEmail() );
        response.password( user.getPassword() );
        response.phoneNumber( user.getPhoneNumber() );
        response.address( user.getAddress() );

        return response.build();
    }

    @Override
    public User userPatchToUser(UserDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( patch.getUserId() );
        user.setUserName( patch.getUserName() );
        user.setPassword( patch.getPassword() );
        user.setPhoneNumber( patch.getPhoneNumber() );
        user.setAddress( patch.getAddress() );

        return user;
    }
}
