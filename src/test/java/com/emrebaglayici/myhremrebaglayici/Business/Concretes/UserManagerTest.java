package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @Mock
    private UserRepository mockRepository;

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
        when(mockRepository.findAll(pageable)).thenReturn(new PageImpl<>(userList));
        assertEquals(2, underTest.listUsers(pageable).getContent().size());
    }

    @Test
    void saveUserTest() {
        UserCreateDto user = new UserCreateDto();
        String name = "Emre";
        String role = "Hr";
        user.setName(name);
        user.setRole(role);
        underTest.saveUser(user);
        verify(mockRepository, times(1)).save(user.toUser());
    }

    @Test
    void deleteByIdTest(){
        User user=new User(1L,"Emre","Hr");
        when(mockRepository.findById(user.getId())).thenReturn(Optional.of(user));
        underTest.deleteById(1L);
        verify(mockRepository).deleteById(user.getId());
    }

    @Test
    void updateNameByIdTest(){
        User user=new User(1L,"Emre","Hr");
        mockRepository.save(user);
        when(mockRepository.findById(user.getId())).thenReturn(Optional.of(user));
        underTest.updateNameById(1L,"Kamil");
        verify(mockRepository,times(2)).save(user);
    }

}
