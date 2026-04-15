package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class LeaderboardController {

    @MessageMapping("/leaderboard")
    @SendTo("/topic/leaderboard")
    public Map<String, Integer> getLeaderboard(String msg) {

        GameState.currentWord = "";
        return GameState.scores;
    }
}