package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class JoinController {

    @MessageMapping("/join")
    @SendTo("/topic/players")
    public List<String> join(String userId) {
        GameState.addPlayer(userId);
        GameState.updateActivity(userId);
        GameState.debug();
        return new ArrayList<>(GameState.players);
    }

    @MessageMapping("/syncTurn")
    @SendTo("/topic/turn")
    public String syncTurn() {
        return GameState.getCurrentDrawer();
    }
}
