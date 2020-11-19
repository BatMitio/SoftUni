package com.gamestore.gamestore.services.impl;

import com.gamestore.gamestore.dtos.UserDto;
import com.gamestore.gamestore.dtos.UserLogInDto;
import com.gamestore.gamestore.dtos.UserRegisterDto;
import com.gamestore.gamestore.entities.Game;
import com.gamestore.gamestore.entities.User;
import com.gamestore.gamestore.repositories.UserRepository;
import com.gamestore.gamestore.services.GameService;
import com.gamestore.gamestore.services.UserService;
import com.gamestore.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private ValidatorUtil validatorUtil;
    private UserRepository userRepo;
    private ModelMapper modelMapper;
    private UserDto loggedInUser;
    private GameService gameService;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setValidatorUtil(ValidatorUtil validatorUtil) {
        this.validatorUtil = validatorUtil;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Passwords don't match!");
        } else if (userRepo.getUserByEmail(userRegisterDto.getEmail()) != null) {
            System.out.println("User with this email already exists in the database.");
        } else if (!this.validatorUtil.isValid(userRegisterDto)) {
            this.validatorUtil.violations(userRegisterDto)
                    .forEach(e -> sb.append(String.format("%s%n", e.getMessage())));
        } else {
            User user = modelMapper.map(userRegisterDto, User.class);
            if (userRepo.count() == 0)
                user.setAdmin(true);
            else {
                user.setAdmin(false);
            }


            sb.append(String.format("%s was registered", user.getFullName()));
            this.userRepo.saveAndFlush(user);
        }

        return sb.toString().trim();
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepo.getUserByEmailAndPassword(email, password);
    }

    @Override
    public String loginUser(UserLogInDto userLogInDto) {
        User user = userRepo.getUserByEmailAndPassword(userLogInDto.getEmail(), userLogInDto.getPassword());

        if (user == null) {
            return "Incorrect username / password";
        }
        loggedInUser = modelMapper.map(user, UserDto.class);
        return String.format("Successfully logged in %s", loggedInUser.getFullName());
    }

    @Override
    public String logout() {
        if(loggedInUser == null)
            return "Cannot log out. No user was logged in.";
        String userName = loggedInUser.getFullName();
        loggedInUser = null;
        return String.format("User %s successfully logged out", userName);
    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    public boolean isLoggedUserIsAdmin() {
        return loggedInUser.isAdmin();
    }
}
