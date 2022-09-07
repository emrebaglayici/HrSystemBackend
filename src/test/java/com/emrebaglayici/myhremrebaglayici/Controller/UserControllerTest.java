package com.emrebaglayici.myhremrebaglayici.Controller;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUser;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.UserController;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IUser iUser;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository mockRepository;

    @Test
    void createTest(){
        UserCreateDto dto=new UserCreateDto();
        dto.setName("Emre");
        dto.setRole("Hr");
        userController.create(dto);
        verify(iUser,times(1)).saveUser(dto);
    }

    @Test
    void listUsersTest(){
        Pageable pageable= PageRequest.of(1,20, Sort.by("id"));
        List<User> userList=new ArrayList<>();
        User user=new User(1L,"Emre","Hr");
        userList.add(user);
        when(iUser.listUsers(pageable)).thenReturn(new PageImpl<>(userList));
        assertEquals(1,userController.listUsers(pageable).getContent().size());
    }

    @Test
    void deleteTest(){
        User user=new User(1L,"Emre","Hr");
        ResponseEntity<Object> responseEntity= (ResponseEntity<Object>) userController.delete(user.getId());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateUserNameTest(){
        User user=new User(1L,"Emre", Role.HR.getName());
        mockRepository.save(user);
        when(iUser.updateNameById(user.getId(), user.getName())).thenReturn(user);
        userController.updateUserName(user.getId(), user.getName());
        verify(iUser,times(1)).updateNameById(user.getId(), user.getName());
    }
}
