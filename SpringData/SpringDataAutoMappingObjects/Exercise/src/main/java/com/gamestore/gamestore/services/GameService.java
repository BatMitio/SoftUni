package com.gamestore.gamestore.services;

import com.gamestore.gamestore.dtos.GameAddDto;
import com.gamestore.gamestore.dtos.GameEditDto;
import com.gamestore.gamestore.entities.Game;

public interface GameService {
    String addGame(GameAddDto game);
    String editGame(String... values);
}
