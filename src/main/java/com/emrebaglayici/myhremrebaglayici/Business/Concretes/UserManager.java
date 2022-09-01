package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserCreateDto dto) {
        if (!dto.toUser().getName().equals("") && !dto.toUser().getRole().equals("")) {
            if (dto.toUser().getRole().equals(Role.CANDIDATES.getName()) ||
                    dto.toUser().getRole().equals(Role.HR.getName())) {
                log.info("User saved successfully : " + dto.toUser());
                this.userRepository.save(dto.toUser());
            } else {
                throw new NotFountException(Helper.ROLE_NOT_FOUND);
            }
        } else {
            throw new FillTheBlanksException(Helper.FILL_ALL_BLANKS);
        }
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> userOptional = userRepository.getUsersById(id);
        User user = userOptional.orElseThrow(() -> new NotFountException(Helper.USER_NOT_FOUND));
        log.info("User deleted successfully : " + user);
        this.userRepository.delete(user);
        return user;
    }

    @Override
    public User updateNameById(Long id, String name) {
        Optional<User> userOptional = userRepository.getUsersById(id);
        User user = userOptional.orElseThrow(() -> new NotFountException(Helper.USER_NOT_FOUND));
        user.setName(name);
        log.info("User name updated successfully : " + user.getName());
        return this.userRepository.save(user);
    }

    public Page<User> listUsers(Pageable page) {
        return userRepository.findAll(page);
    }
}
