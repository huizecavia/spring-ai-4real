package org.matthijs.spring_ai_4real.controller;

import jakarta.validation.Valid;
import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.Question;
import org.matthijs.spring_ai_4real.service.GameRulesService;
import org.matthijs.spring_ai_4real.service.VectordbService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final VectordbService vectordbService;
    private final GameRulesService gameRulesService;

    public ChatController(VectordbService vectordbService, GameRulesService gameRulesService) {
        this.vectordbService = vectordbService;
        this.gameRulesService = gameRulesService;
    }

    @GetMapping("/gamerules")
    public String getGameRules(@RequestParam String gameName) {
        return gameRulesService.getRulesFor(gameName, vectordbService.getVectorStore());
    }

    @PostMapping(path="/ask", produces="application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return gameRulesService.askQuestion(question, vectordbService.getVectorStore());
    }
}
