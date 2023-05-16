package com.valdir.api.service;

import com.valdir.api.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findById(Integer id);

    List<User> findAll();
}
