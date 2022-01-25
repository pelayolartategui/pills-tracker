package com.pills.pillstracker;

import com.pills.pillstracker.models.daos.User;
import com.pills.pillstracker.models.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserDtoUnitTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertUserEntityToUserDto_thenCorrect() {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setContactNumber("659142558");
        user.setPassword("strong_password");
        user.setFirstName("tester");
        user.setLastName("testerLastName");
        user.setUsername("test");

        UserDto userDto = modelMapper.map(user, UserDto.class);
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getContactNumber(), userDto.getContactNumber());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getUsername(), userDto.getUsername());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {

        UserDto userDto = new UserDto();
        userDto.setEmail("test@mail.com");
        userDto.setContactNumber("659142558");
        userDto.setPassword("strong_password");
        userDto.setFirstName("tester");
        userDto.setLastName("testerLastName");
        userDto.setUsername("test");

        User user = modelMapper.map(userDto, User.class);
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getContactNumber(), user.getContactNumber());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getUsername(), user.getUsername());
    }

}
