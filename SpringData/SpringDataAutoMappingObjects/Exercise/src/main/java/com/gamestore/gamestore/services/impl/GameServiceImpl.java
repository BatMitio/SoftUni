package com.gamestore.gamestore.services.impl;

import com.gamestore.gamestore.dtos.GameAddDto;
import com.gamestore.gamestore.dtos.GameEditDto;
import com.gamestore.gamestore.entities.Game;
import com.gamestore.gamestore.repositories.GameRepository;
import com.gamestore.gamestore.services.GameService;
import com.gamestore.gamestore.services.UserService;
import com.gamestore.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepo;
    private ValidatorUtil validatorUtil;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public void setGameRepo(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    @Autowired
    public void setValidatorUtil(ValidatorUtil validatorUtil) {
        this.validatorUtil = validatorUtil;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String addGame(GameAddDto gameToAdd) {
        if(!userService.isLoggedUserIsAdmin()){
            return "Current logged user is not an admin";
        }
        StringBuilder sb = new StringBuilder();
        if(!validatorUtil.isValid(gameToAdd)){
            validatorUtil.violations(gameToAdd).forEach(e -> sb.append(e.getMessage()));
        } else {
            Game game = modelMapper.map(gameToAdd, Game.class);
            gameRepo.saveAndFlush(game);
            sb.append(String.format("Added %s", game.getTitle()));
        }

        return sb.toString().trim();
    }

    @Override
    public String editGame(String... values) {
        if(!userService.isLoggedUserIsAdmin()){
            return "Current logged user is not an admin";
        }
        Long id = Long.parseLong(values[0]);
        GameEditDto gameEdit = new GameEditDto();
        gameEdit.setId(id);
        for (int i = 1; i < values.length; i++) {
            String[] tokens = values[i].split("\\s*=\\s*");
            switch (tokens[0].toLowerCase()){
                case "title":
                    gameEdit.setTitle(tokens[1]);
                    break;
                case "price":
                    gameEdit.setPrice(new BigDecimal(tokens[1]));
                    break;
                case "size":
                    gameEdit.setSize(Double.parseDouble(tokens[1]));
                    break;
                case "trailer":
                    gameEdit.setTrailer(tokens[1]);
                    break;
                case "thumbnail":
                    gameEdit.setImageThumbnail(tokens[1]);
                    break;
                case "description":
                    gameEdit.setDescription(tokens[1]);
                    break;
            }
        }
        StringBuilder sb = new StringBuilder();
        if(!gameRepo.existsById(gameEdit.getId())){
            sb.append(String.format("No game with id: %d found!", gameEdit.getId()));
        }
        else {
                Game game = userService.;
                gameRepo.saveAndFlush(game);
                sb.append(String.format("Added %s", game.getTitle()));

        }
        return sb.toString().trim();
    }
}
