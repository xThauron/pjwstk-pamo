package eu.xthauron.bmicalc.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.xthauron.bmicalc.Model.Answer;
import eu.xthauron.bmicalc.Model.Question;
import eu.xthauron.bmicalc.Model.Quiz;
import eu.xthauron.bmicalc.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizQuestionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUIZ = "quiz";

    private Button button_next, button_answer1, button_answer2, button_answer3, button_answer4;
    private List<Button> buttonAnswersList;
    private TextView tv_question_counter, tv_question_name, tv_question_results;
    private int result, questionCount;
    private Question question;
    private Answer correctAnswer;

    private String quizParam;
    private Quiz quiz;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    public static QuizQuestionFragment newInstance(Quiz quiz) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ, quiz);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quiz = (Quiz) getArguments().getSerializable(ARG_QUIZ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        tv_question_counter = (TextView) view.findViewById(R.id.quiz_question_number);
        tv_question_results = (TextView) view.findViewById(R.id.quiz_question_result);
        tv_question_name = (TextView) view.findViewById(R.id.quiz_question);

        button_answer1 = (Button) view.findViewById(R.id.b_answer_1);
        button_answer2 = (Button) view.findViewById(R.id.b_answer_2);
        button_answer3 = (Button) view.findViewById(R.id.b_answer_3);
        button_answer4 = (Button) view.findViewById(R.id.b_answer_4);
        button_next = (Button) view.findViewById(R.id.btn_quiz_next);

        buttonAnswersList = Arrays.asList(button_answer1, button_answer2, button_answer3, button_answer4);

        for(Button button : buttonAnswersList) {
            button.setOnClickListener(new AnswerButtonListener());
        }

        button_next.setOnClickListener(new NextQuestionListener());

        return view;
    }

    public void initializeQuiz() {
        Collections.shuffle(quiz.getQuestions());
        result = 0;
        questionCount = 0;
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        button_next.setEnabled(false);


        question = quiz.getQuestions().get(questionCount);
        correctAnswer = question.getAnswers().get(question.getCorrectAnswerId());
        questionCount++;

        tv_question_results.setText(null);

        tv_question_counter.setText(getString(R.string.t_question_number, questionCount, quiz.getQuestions().size()));
        tv_question_name.setText(question.getName());

        for (int i = 0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);
            Button answerButton = buttonAnswersList.get(i);
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black));
            answerButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.purple_500));
            answerButton.setEnabled(true);
            answerButton.setText(answer.getText());
        }
    }

    private class AnswerButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button answerButton = (Button) view;
            String answerText = answerButton.getText().toString();

            if (answerText.equals(correctAnswer.getText())) {
                result++;
                tv_question_results.setText(R.string.t_correct_answer);
                tv_question_results.setTextColor(Color.GREEN);
                answerButton.setBackgroundColor(Color.GREEN);
            } else {
                tv_question_results.setText(R.string.t_false_answer);
                tv_question_results.setTextColor(Color.RED);
                answerButton.setBackgroundColor(Color.RED);
                colorizeCorrectAnswerButton();
            }

            buttonAnswersList.forEach(buttonAnswer -> {
                buttonAnswer.setEnabled(false);
            });
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));

            button_next.setEnabled(true);
        }
    }

    private void colorizeCorrectAnswerButton() {
        buttonAnswersList.stream()
                .filter(b -> b.getText().toString().equals(correctAnswer.getText()))
                .findFirst()
                .ifPresent(correctButton -> {
                    correctButton.setBackgroundColor(Color.GREEN);
                    correctButton.setTextColor(Color.WHITE);
                });
    }

    private void showQuizResult() {
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        bundle.putInt("questionsCount", quiz.getQuestions().size());

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.f_quiz, QuizResultFragment.class, bundle, "QUIZ_RESULT_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }

    private class NextQuestionListener implements  View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button button = ((Button) v);

            if (questionCount == quiz.getQuestions().size()) {
                showQuizResult();
                button.setVisibility(View.GONE);
            }

            if (questionCount < quiz.getQuestions().size()) {
                loadNextQuestion();
            }
        }
    }
}