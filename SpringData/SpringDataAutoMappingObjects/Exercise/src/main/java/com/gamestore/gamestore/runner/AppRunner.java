package com.gamestore.gamestore.runner;

import com.gamestore.gamestore.dtos.GameAddDto;
import com.gamestore.gamestore.dtos.UserLogInDto;
import com.gamestore.gamestore.dtos.UserRegisterDto;
import com.gamestore.gamestore.entities.Game;
import com.gamestore.gamestore.entities.User;
import com.gamestore.gamestore.services.GameService;
import com.gamestore.gamestore.services.OrderService;
import com.gamestore.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {
    private final UserService userService;
    private final OrderService orderService;
    private final GameService gameService;

    @Autowired
    public AppRunner(UserService userService, OrderService orderService, GameService gameService) {
        this.userService = userService;
        this.orderService = orderService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        System.out.println("Ne mi stigna vremeto sorka bate");
        while (running){
            String[] input = sc.nextLine().split("\\|");
            if(input[0].equals("RegisterUser")){
                System.out.println(userService.registerUser(new UserRegisterDto(input[1], input[2], input[3], input[4])));
            }
            else if (input[0].equals("LoginUser")){
                System.out.println(userService.loginUser(new UserLogInDto(input[1], input[2])));
            }
            else if(input[0].equals("Logout")){
                System.out.println(userService.logout());
                running = false;
            }
            else if(input[0].equals("AddGame")){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                System.out.println(gameService.addGame(new GameAddDto(
                        input[1], new BigDecimal(input[2]), Double.parseDouble(input[3]),
                        input[4], input[5], input[6], LocalDate.parse(input[7], formatter))));
            }
            else if(input[0].equals("EditGame")){
                System.out.println(gameService.editGame(Arrays.stream(input).skip(1).toArray(String[]::new)));
            }
        }
    }
}
