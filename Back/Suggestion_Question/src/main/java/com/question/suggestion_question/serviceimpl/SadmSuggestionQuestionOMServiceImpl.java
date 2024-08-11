package com.question.suggestion_question.serviceimpl;

import com.question.suggestion_question.jpa.SadmSuggestionQuestionOM;
import com.question.suggestion_question.repository.SadmSuggestionQuestionOMRepository;
import com.question.suggestion_question.service.SadmSuggestionQuestionOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SadmSuggestionQuestionOMServiceImpl implements SadmSuggestionQuestionOMService {

    @Autowired
    private SadmSuggestionQuestionOMRepository repository;

    @Override
    public SadmSuggestionQuestionOM save(SadmSuggestionQuestionOM suggestionQuestion) {
        return repository.save(suggestionQuestion);
    }

    @Override
    public SadmSuggestionQuestionOM findById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public List<SadmSuggestionQuestionOM> findRandomQuestions() {
        return repository.findRandomQuestions();
    }

    @Override
    public void delete(Long id) {
        SadmSuggestionQuestionOM suggestion = findById(id);
        if (suggestion != null) {
            repository.delete(suggestion);
        }

    }

}
