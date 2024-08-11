package com.question.suggestion_question.controller;

import com.question.suggestion_question.jpa.SadmSuggestionQuestionOM;
import com.question.suggestion_question.service.SadmSuggestionQuestionOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestion-questions")
public class SadmSuggestionQuestionOMController {

    @Autowired
    private SadmSuggestionQuestionOMService suggestionQuestionService;

    @PostMapping
    public ResponseEntity<SadmSuggestionQuestionOM> createSuggestionQuestion(@RequestBody SadmSuggestionQuestionOM suggestionQuestion) {
        SadmSuggestionQuestionOM createdSuggestionQuestion = suggestionQuestionService.save(suggestionQuestion);
        return new ResponseEntity<>(createdSuggestionQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SadmSuggestionQuestionOM> getSuggestionQuestionById(@PathVariable("id") Long id) {
        SadmSuggestionQuestionOM suggestionQuestion = suggestionQuestionService.findById(id);
        if (suggestionQuestion != null) {
            return new ResponseEntity<>(suggestionQuestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuggestionQuestion(@PathVariable("id") Long id) {
        suggestionQuestionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/random")
    public ResponseEntity<List<SadmSuggestionQuestionOM>> getRandomQuestions() {
        List<SadmSuggestionQuestionOM> randomQuestions = suggestionQuestionService.findRandomQuestions();
        if (!randomQuestions.isEmpty()) {
            return new ResponseEntity<>(randomQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}