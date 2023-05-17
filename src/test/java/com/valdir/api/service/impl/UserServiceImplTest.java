package com.valdir.api.service.impl;

import com.valdir.api.domain.User;
import com.valdir.api.domain.dto.UserDTO;
import com.valdir.api.repository.UserRepository;
import com.valdir.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Gustavo";
    public static final String MAIL     = "gus@gmail.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        // cenário
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        // execução
        User response = service.findById(ID);

        // verificações
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        // cenário
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado!"));

        try{
            // execução
            service.findById(ID);
        } catch(Exception exception){
            // verificações
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Objeto não encontrado!", exception.getMessage());
        }

    }

    @Test
    void whenFindAllThenReturnAListOfUsers() {
        // cenário
        when(repository.findAll()).thenReturn(List.of(user, user, user));

        // execução
        List<User> response = service.findAll();

        // verificações
        assertNotNull(response);
        assertEquals(3, response.size());
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(MAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, MAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
    }
}