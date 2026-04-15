package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class GameController{
    public static String currentWord = "";
    @MessageMapping("/startGame")
    @SendTo("/topic/game")
    public String startGame(String word){
        GameState.currentWord = word;
        currentWord = word;
        return word;
    }
}