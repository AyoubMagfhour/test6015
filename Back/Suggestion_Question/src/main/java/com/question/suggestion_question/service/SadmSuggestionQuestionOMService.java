package com.question.suggestion_question.service;

import com.question.suggestion_question.jpa.SadmSuggestionQuestionOM;

import java.util.List;

public interface SadmSuggestionQuestionOMService {

    SadmSuggestionQuestionOM save(SadmSuggestionQuestionOM suggestionQuestion);
    SadmSuggestionQuestionOM findById(Long id);
    void delete(Long id);
    List<SadmSuggestionQuestionOM> findRandomQuestions();

}
