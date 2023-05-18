package com.valdir.api.service.impl;

import com.valdir.api.domain.User;
import com.valdir.api.domain.dto.UserDTO;
import com.valdir.api.repository.UserRepository;
import com.valdir.api.service.exceptions.DataIntegrityViolationException;
import com.valdir.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Gustavo";
    public static final String MAIL     = "gus@gmail.com";
    public static final String PASSWORD = "123";
    public static final String E_MAIL_J_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema.";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado!";

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
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            // execução
            service.findById(ID);
        } catch(Exception exception){
            // verificações
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, exception.getMessage());
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
    void whenCreateThenReturnSuccess() {
        // cenário
        when(repository.save(any())).thenReturn(user);

        // execução
        User response = service.create(userDTO);

        // verificações
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(ID + 1);
            service.create(userDTO);
        } catch(Exception exception){
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(E_MAIL_J_CADASTRADO_NO_SISTEMA, exception.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        // cenário
        when(repository.save(any())).thenReturn(user);

        // execução
        User response = service.update(userDTO);

        // verificações
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(ID + 1);
            service.update(userDTO);
        } catch(Exception exception){
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(E_MAIL_J_CADASTRADO_NO_SISTEMA, exception.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        // cenário
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        // não faz nada quando o delete do repo for chamado (pq retorna NULL)
        doNothing().when(repository).deleteById(anyInt());

        // execução
        service.delete(ID);

        // verifica qts vezes o repository foi chamado no método deleteById
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithException(){ // não encontra o id passado como parâmetro
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.delete(ID);
        } catch(Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME, MAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
    }
}