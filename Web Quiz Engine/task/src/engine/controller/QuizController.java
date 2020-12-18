package engine.controller;

import engine.entity.Completion;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping(path = "/api/quizzes/{id}")
    private ResponseEntity<Quiz> getQuizById(@PathVariable long id) {
        Quiz quiz = quizService.getQuizById(id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quizzes")
    private ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam int page) {
        Page<Quiz> quizzes = quizService.getAllQuizzes(page);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quizzes/completed")
    private ResponseEntity<Page<Completion>> getCompletedQuizzes(@RequestParam int page) {
        Page<Completion> quizzes = quizService.getCompletedQuizzes(page);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes")
    private ResponseEntity<Quiz> saveQuiz(@Valid @RequestBody Quiz quiz) {
        Quiz savedQuiz = quizService.saveQuiz(quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.OK);
    }

    @PutMapping(path = "/api/quizzes/{id}")
    private ResponseEntity<Quiz> putQuizById(@Valid @RequestBody Quiz quiz,
                                             @PathVariable long id,
                                             Principal principal) {
        Quiz savedQuiz = quizService.replaceById(id, principal, quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.OK);
    }

    @PatchMapping(path = "/api/quizzes/{id}")
    private ResponseEntity<Quiz> patchQuizById(@RequestBody Quiz quiz,
                                             @PathVariable long id,
                                             Principal principal) {
        Quiz savedQuiz = quizService.updateById(id, principal, quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    private ResponseEntity<Void> deleteQuizById(@PathVariable long id, Principal principal) {
        quizService.deleteById(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    private Response getResponse(@RequestBody Quiz answer, @PathVariable long id) {
        return quizService.getResponse(id, answer);
    }
}
