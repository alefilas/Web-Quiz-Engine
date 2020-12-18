package engine.service;

import engine.entity.Completion;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.entity.User;
import engine.exceptions.NoAccessException;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletionRepository completedQuizRepository;

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Quiz> getAllQuizzes(Integer page) {

        PageRequest paging = PageRequest.of(page, 10);

        return quizRepository.findAll(paging);

    }

    @Override
    public Page<Completion> getCompletedQuizzes(Integer page) {

        PageRequest paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());

        return completedQuizRepository.findByUserId(getCurrentUser().getId(), paging);
    }

    @Override
    public Response getResponse(Long id, Quiz answer) {
        Quiz quiz = getQuizById(id);

        if (quiz.isCorrect(answer.getAnswer() == null ? List.of() : answer.getAnswer())) {

            Completion completedQuiz = new Completion();

            completedQuiz.setUser(getCurrentUser());
            completedQuiz.setCompletedAt();
            completedQuiz.setId(id);

            completedQuizRepository.save(completedQuiz);

            return Response.OK_RESPONSE;

        } else {
            return Response.WRONG_RESPONSE;
        }
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {


        quiz.setUser(getCurrentUser());
        quiz.setAnswer(Objects.requireNonNullElse(quiz.getAnswer(), List.of()));

        return quizRepository.save(quiz);
    }

    @Override
    public void deleteById(Long id, Principal principal) {

        checkRequest(principal, id);

        quizRepository.deleteById(id);

    }

    @Override
    public Quiz replaceById(Long id, Principal principal, Quiz quiz) {

        checkRequest(principal, id);

        quiz.setId(id);
        quiz.setUser(getCurrentUser());

        return quizRepository.save(quiz);
    }

    @Override
    public Quiz updateById(Long id, Principal principal, Quiz quiz) {

        checkRequest(principal, id);

        Quiz optionalQuiz = getQuizById(id);

        if (quiz.getAnswer() != null) {
            optionalQuiz.setAnswer(quiz.getAnswer());
        }

        if (quiz.getOptions() != null) {
            optionalQuiz.setOptions(quiz.getOptions());
        }

        if (quiz.getText() != null) {
            optionalQuiz.setText(quiz.getText());
        }

        if (quiz.getTitle() != null) {
            optionalQuiz.setTitle(quiz.getTitle());
        }

        return quizRepository.save(optionalQuiz);
    }



    private void checkRequest(Principal principal, Long id) {

        String email = getQuizById(id).getUser().getEmail();

        if (!email.equals(principal.getName())) {
            throw new NoAccessException();
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName());
    }
}
