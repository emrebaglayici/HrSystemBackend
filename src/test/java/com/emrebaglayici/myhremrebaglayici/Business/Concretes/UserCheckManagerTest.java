package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCheckManagerTest {

    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private UserCheckManager underTest;

    @Test
    void getUserByIdTest(){
        User user=new User(1L,"Emre","Hr");
        when(mockRepository.findById(user.getId())).thenReturn(Optional.of(user));
        underTest.getUserById(user.getId());
        verify(mockRepository).findById(user.getId());
    }

}
