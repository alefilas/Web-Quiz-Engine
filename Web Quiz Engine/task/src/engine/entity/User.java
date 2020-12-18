package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5)
    private String password;

    @Email(regexp = ".+@.+\\..+")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Quiz> quizzes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Completion> completedQuizzes;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Completion> getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(List<Completion> completedQuizzes) {
        this.completedQuizzes = completedQuizzes;
    }
}
