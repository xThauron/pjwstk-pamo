package eu.xthauron.bmicalc.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    private static final long serialVersionUID = 2788049829090491540L;

    private String name;
    private List<Question> questions = new ArrayList<>();
    private int result;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questionList) {
        this.questions = questionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
