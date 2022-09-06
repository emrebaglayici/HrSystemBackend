package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationCheckManagerTest {
    @Mock
    private ApplicationRepository mockAppRepo;

    @InjectMocks
    private ApplicationCheckManager underTest;

    @Test
    void findById(){
        Application application=new Application(
                1L,3L,2L,10,"I am very good", LocalDateTime.now()
        );
        when(mockAppRepo.findById(application.getId())).thenReturn(Optional.of(application));
        underTest.findById(application.getId());
        verify(mockAppRepo).findById(application.getId());
    }
}
