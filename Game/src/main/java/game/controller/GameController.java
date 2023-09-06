package game.controller;

import game.model.GameState;
import game.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping
    public GameState home(HttpSession session){
        return gameService.routeAccess(session);
    }

    @GetMapping
    @RequestMapping("/{letter}")
    public GameState guess(@PathVariable("letter") Character letter, HttpSession session){
        return gameService.guess(letter, session);
    }

}
