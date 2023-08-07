package com.dhbinh.yummybites.base.security.service.mapper;


import com.dhbinh.yummybites.base.mapper.BaseMapper;
import com.dhbinh.yummybites.base.security.entity.Role;
import com.dhbinh.yummybites.base.security.entity.User;
import com.dhbinh.yummybites.base.security.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserDTO> {

    @Mapping(target = "password", ignore = true)
    @Mapping(expression = "java(mapRoleEnumToString(user.getRole()))", target = "role")
    UserDTO toDTO(User user);

    default String mapRoleEnumToString(Role role) {
        return role != null ? role.toString() : null;
    }
}