package com.question.suggestion_question.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "sadm_suggestion_question", schema = "schpgmv21sad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SadmSuggestionQuestionOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plsq_sfksuggestion_question")
    private Long plsqSfksuggestionQuestion;

    @Column(name = "plsq_sfkinstance")
    private Long plsqSfkinstance;

    @Column(name = "plsq_cquestion",columnDefinition = "TEXT")
    private String plsqCquestion;

    @Column(name = "com_cwho_create",columnDefinition = "TEXT")
    private String comCwhoCreate;

    @Column(name = "com_xwhen_create")
    private Date comXwhenCreate;

    @Column(name = "com_cwho_update",columnDefinition = "TEXT")
    private String comCwhoUpdate;

    @Column(name = "com_xwhen_update")
    private Date comXwhenUpdate;

    @Column(name = "com_sdesactive")
    private Integer comSdesactive;
}
