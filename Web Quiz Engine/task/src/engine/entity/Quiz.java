package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserId")
    protected User user;

    @NotBlank
    private String title;


    @NotBlank
    private String text;

    @ElementCollection
    @CollectionTable(
            name = "options",
            joinColumns = @JoinColumn(name = "QuizId")
    )
    @Column(name = "option")
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @ElementCollection
    @CollectionTable(
            name = "answers",
            joinColumns = @JoinColumn(name = "QuizId")
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    public Quiz() {
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrect(List<Integer> index) {
        return index.equals(answer);
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
