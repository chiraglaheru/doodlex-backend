package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TurnController {

    @MessageMapping("/getTurn")
    @SendTo("/topic/turn")
    public String getTurn() {
        return GameState.getCurrentDrawer();
    }

    @MessageMapping("/nextTurn")
    @SendTo("/topic/turn")
    public String nextTurn() {
        GameState.nextTurn();
        return GameState.getCurrentDrawer();
    }
}