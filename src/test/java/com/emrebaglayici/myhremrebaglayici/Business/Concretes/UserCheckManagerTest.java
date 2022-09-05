package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCheckManagerTest {

    @InjectMocks
    private UserRepository userRepository;

    @InjectMocks
    static private UserManager userManager;

    @Mock
    private UserCheckManager userCheckManager;


    @Test
    void shouldReturnOptionalUser() {

    }
}
