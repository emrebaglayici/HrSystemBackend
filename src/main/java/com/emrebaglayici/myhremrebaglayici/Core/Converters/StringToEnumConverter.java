package com.emrebaglayici.myhremrebaglayici.Core.Converters;

import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Role;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import java.util.Locale;

@Component
public class StringToEnumConverter implements Converter<String, Role> {

    @Override
    public Role convert(String source) {
        try {
            return source.isEmpty() ? null : Role.valueOf(source.trim().toUpperCase(Locale.ROOT));
        } catch(Exception e) {
            return Role.HR;
        }
    }
}
