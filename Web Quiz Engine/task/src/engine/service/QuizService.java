package engine.service;

import engine.entity.Completion;
import engine.entity.Quiz;
import engine.entity.Response;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface QuizService {

    Quiz getQuizById(Long id);

    Page<Quiz> getAllQuizzes(Integer page);

    Quiz saveQuiz(Quiz quiz);

    void deleteById(Long id, Principal principal);

    Quiz replaceById(Long id, Principal principal, Quiz quiz);

    Quiz updateById(Long id, Principal principal, Quiz quiz);

    Page<Completion> getCompletedQuizzes(Integer page);

    Response getResponse(Long id, Quiz answer);
}
