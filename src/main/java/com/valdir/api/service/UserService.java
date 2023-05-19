package com.valdir.api.service;

import com.valdir.api.domain.User;
import com.valdir.api.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO obj);
    User update(UserDTO obj);

    void delete(Integer id);
}
