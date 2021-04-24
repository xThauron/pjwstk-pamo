package eu.xthauron.bmicalc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import eu.xthauron.bmicalc.Fragment.QuizQuestionFragment;
import eu.xthauron.bmicalc.Model.Quiz;
import eu.xthauron.bmicalc.R;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String json = "";
        try {
            json = loadJSONFromAssets("json/quiz.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        if(json.isEmpty()) return;
        Quiz quiz = null;
        try {
            quiz = mapper.readValue(json, Quiz.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("quiz", quiz);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.f_quiz, QuizQuestionFragment.class, bundle, "QUIZ_FRAGMENT")
                .commit();
    }

    private String loadJSONFromAssets(String fileName) throws IOException {
        String json;

        InputStream is = getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, StandardCharsets.UTF_8);

        return json;
    }

    @Override
    protected void onStart() {
        super.onStart();

        QuizQuestionFragment quizQuestionFragment = (QuizQuestionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.f_quiz);
        if (quizQuestionFragment != null) {
            quizQuestionFragment.initializeQuiz();
        }
    }
}