package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DrawController {

    @MessageMapping("/draw")
    @SendTo("/topic/draw")
    public String draw(String data) {
        System.out.println("DRAW DATA: " + data);
        return data;
    }
    @MessageMapping("/word")
    @SendTo("/topic/word")
    public String getWord() {
        return GameState.currentWord;
    }
}