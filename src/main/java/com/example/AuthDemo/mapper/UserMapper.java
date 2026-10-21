package com.example.AuthDemo.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.example.AuthDemo.dtos.Request.UserRegisterRequest;
import com.example.AuthDemo.dtos.Response.UserResponse;
import com.example.AuthDemo.entity.User;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping (target = "id", ignore = true)
    @Mapping (target = "password", ignore = true)
    @Mapping (target = "role", ignore = true)
    User toEntity(UserRegisterRequest req);
    UserResponse toResponse(User user);
}
