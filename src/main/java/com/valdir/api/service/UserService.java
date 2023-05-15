package com.valdir.api.service;

import com.valdir.api.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findById(Integer id);
}
