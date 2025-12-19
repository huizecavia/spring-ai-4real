package org.matthijs.spring_ai_4real.controller;

import jakarta.validation.Valid;
import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.GipGptQuestion;
import org.matthijs.spring_ai_4real.model.Question;
import org.matthijs.spring_ai_4real.service.GameRulesService;
import org.matthijs.spring_ai_4real.service.GipGptService;
import org.matthijs.spring_ai_4real.service.VectordbService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final VectordbService vectordbService;
    private final GameRulesService gameRulesService;
    private final GipGptService gipGptService;

    public ChatController(VectordbService vectordbService, GameRulesService gameRulesService, GipGptService gipGptService) {
        this.vectordbService = vectordbService;
        this.gameRulesService = gameRulesService;
        this.gipGptService = gipGptService;
    }

    @PostMapping(path="/game", produces="application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return gameRulesService.askQuestion(question, vectordbService.getVectorStore("classpath*:/gameRules/*.txt"));
    }

    @PostMapping(path="/gipgpt", produces="application/json")
    public Answer askGipGpt(@RequestBody @Valid GipGptQuestion gipGptQuestion) {
        return gipGptService.askGipGpt(gipGptQuestion, vectordbService.getVectorStore("classpath*:/artikelen/*.txt"));
    }
}
