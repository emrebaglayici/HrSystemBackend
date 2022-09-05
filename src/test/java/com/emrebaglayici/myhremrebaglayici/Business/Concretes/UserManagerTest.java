package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserManager underTest;


    @Test
    void listUsersTest() {
        Pageable pageable = PageRequest.of(1, 20, Sort.by("id"));
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "Emre", "Hr");
        User user2 = new User(2L, "Elif", "Candidates");
        userList.add(user);
        userList.add(user2);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(userList));
        assertEquals(2, underTest.listUsers(pageable).getContent().size());
        //done.
    }

    @Test
    void saveUserTest() {
        UserCreateDto user = new UserCreateDto();
        String name = "Emre";
        String role = "Hr";
        user.setName(name);
        user.setRole(role);
        underTest.saveUser(user);
        verify(userRepository, times(1)).save(user.toUser());
        //done.
    }

    @Test
    void deleteByIdTest(){
        Long id=1L;
        underTest.deleteById(id);
        verify(userRepository,times(1)).deleteById(id);
//        User user=User.builder()
//                        .id(1L)
//                                .name("Emre")
//                                        .role("Hr")
//                                                .build();
//        System.out.println(userRepository.save(user));
//        System.out.println(userRepository.findById(1L));
//        underTest.deleteById(1L);
//        UserCreateDto dto=new UserCreateDto();
//        dto.setRole("Hr");
//        dto.setName("Emre");
//
//        UserDto userDto=UserDto.builder()
//                        .id(1L)
//                                .name("Emre")
//                                        .role("Hr")
//                                                .build();
//        User user=userRepository.save(dto.toUser());
//        userRepository.save(user);
//        underTest.deleteById(userDto.getId());
//        verify(userRepository,times(1)).delete(dto.toUser());
    }

    @Test
    void updateNameByIdTest(){
        User user=userRepository.findById(1L).get();
        user.setName("Kamil");
        User userUpdated=userRepository.save(user);
        assertThat(userUpdated.getName()).isEqualTo("Kamil");
        underTest.updateNameById(1L,"Emre");
        assertNotNull(userRepository.findById(1L));
        verify(userRepository,times(1)).save(user);
    }

}
