package com.example.pawfeed.websocket;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {

        // add player if new
        if (!GameState.players.contains(message.getUser())) {
            GameState.players.add(message.getUser());
        }

        return message;
    }

}