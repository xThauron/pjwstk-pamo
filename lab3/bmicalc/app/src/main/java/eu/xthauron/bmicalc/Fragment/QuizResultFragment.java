package eu.xthauron.bmicalc.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import eu.xthauron.bmicalc.Activity.MainActivity;
import eu.xthauron.bmicalc.Activity.QuizActivity;
import eu.xthauron.bmicalc.R;

public class QuizResultFragment extends Fragment {

    private static final String PARAM_RESULT = "result";
    private static final String PARAM_QUESTION_COUNT = "questionsCount";
    private TextView tv_result;
    private Button button_menu;
    private Intent intent;
    private int result;
    private int questionsCount;

    public QuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            result = getArguments().getInt(PARAM_RESULT);
            questionsCount = getArguments().getInt(PARAM_QUESTION_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        tv_result = (TextView) view.findViewById(R.id.quiz_result);
        button_menu = (Button) view.findViewById(R.id.button_menu);

        tv_result.setText(getString(R.string.t_result, result, questionsCount));
        button_menu.setOnClickListener(e -> goToMenuActivity());

        return view;
    }

    private void goToMenuActivity() {
        intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }
}