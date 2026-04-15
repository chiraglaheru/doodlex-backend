package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class GuessController {

    @MessageMapping("/guess")
    @SendTo("/topic/guess")
    public String checkGuess(String msg) {

        // format: userId:guess
        String[] parts = msg.split(":");
        if (parts.length < 2) return "INVALID"; 

        String user = parts[0];
        String guess = parts[1];

        GameState.updateActivity(user); 

        if (user.equals(GameState.getCurrentDrawer())){
            return "IGNORE";
        }

        if (guess.equalsIgnoreCase(GameState.currentWord)) {

            GameState.scores.put(
                    user,
                    GameState.scores.getOrDefault(user,0)+10
            );

            GameState.nextTurn();
            String next = GameState.getCurrentDrawer();
            GameState.currentWord = "";

            return "CORRECT:" + user + ":" + guess + ":" + next;
        }

        return "WRONG:" + user + ":" + guess;
    }
}
