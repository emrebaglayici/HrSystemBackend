package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;

import java.util.Optional;

public interface IApplicationCheck {
    Optional<Application> findById(Long id);
}
