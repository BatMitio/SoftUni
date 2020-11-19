package com.gamestore.gamestore.services;

import com.gamestore.gamestore.dtos.UserLogInDto;
import com.gamestore.gamestore.dtos.UserRegisterDto;
import com.gamestore.gamestore.entities.Game;
import com.gamestore.gamestore.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto);
    User getUserByEmailAndPassword(String email, String password);

    String loginUser(UserLogInDto userLogInDto);

    String logout();

    User findById();

    boolean isLoggedUserIsAdmin();

}
