package eu.xthauron.bmicalc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import eu.xthauron.bmicalc.Activity.BmiActivity;
import eu.xthauron.bmicalc.Activity.CaloriesActivity;
import eu.xthauron.bmicalc.R;

public class MainActivity extends AppCompatActivity {
    private Button menuBmiActivityButton, menuCaloriesActivityButton, menuEatActivityButton, menuChartActivityButton, menuQuizActivityButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBmiActivityButton = (Button) findViewById(R.id.menu_activity_bmi);
        menuCaloriesActivityButton = (Button) findViewById(R.id.menu_activity_calories);
        menuEatActivityButton = (Button) findViewById(R.id.menu_activity_eat);
        menuChartActivityButton = (Button) findViewById(R.id.menu_activity_chart);
        menuQuizActivityButton = ( Button ) findViewById(R.id.menu_activity_quiz);

        menuBmiActivityButton.setOnClickListener(e -> goToBmiActivity());
        menuCaloriesActivityButton.setOnClickListener(e -> goToCaloriesActivity());
        menuEatActivityButton.setOnClickListener(e -> goToEatActivity());
        menuChartActivityButton.setOnClickListener(e -> goToChartActivity());
        menuQuizActivityButton.setOnClickListener(e -> goToQuizActivity());
    }

    private void goToBmiActivity() {
        intent = new Intent(this, BmiActivity.class);
        startActivity(intent);
    }

    private void goToCaloriesActivity() {
        intent = new Intent(this, CaloriesActivity.class);
        startActivity(intent);
    }

    private void goToEatActivity() {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }

    private void goToChartActivity() {
        intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }

    private void goToQuizActivity() {
        intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }
}