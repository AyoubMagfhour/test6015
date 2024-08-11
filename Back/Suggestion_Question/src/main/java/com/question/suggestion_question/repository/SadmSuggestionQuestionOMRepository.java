package com.question.suggestion_question.repository;

import com.question.suggestion_question.jpa.SadmSuggestionQuestionOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadmSuggestionQuestionOMRepository extends JpaRepository<SadmSuggestionQuestionOM, Long> {

    //sélectionner aléatoirement 4 questions de suggestion
    @Query(value = "SELECT * FROM schpgmv21sad.sadm_suggestion_question ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
    List<SadmSuggestionQuestionOM> findRandomQuestions();
}

