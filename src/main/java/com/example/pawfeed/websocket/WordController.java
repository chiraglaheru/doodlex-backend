package com.example.pawfeed.websocket;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class WordController {

    static List<String> words = Arrays.asList(
            "apple", "car", "house", "tree", "dog",
            "cat", "phone", "sun", "moon", "chair"
    );

    @MessageMapping("/getWords")
    @SendTo("/topic/words")
    public List<String> getWords(String msg) {
        Collections.shuffle(words);
        return words.subList(0, 3);
    }

    @MessageMapping("/chooseWord")
    @SendTo("/topic/game")
    public String chooseWord(String word) {
        GameState.currentWord = word;
        return word;
    }
}