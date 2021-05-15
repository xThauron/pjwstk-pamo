package eu.xthauron.bmicalc.Activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import eu.xthauron.bmicalc.Activity.BmiActivity
import eu.xthauron.bmicalc.Activity.CaloriesActivity
import eu.xthauron.bmicalc.R

class MainActivity : AppCompatActivity() {
    private var menuBmiActivityButton: Button? = null
    private var menuCaloriesActivityButton: Button? = null
    private var menuEatActivityButton: Button? = null
    private var menuChartActivityButton: Button? = null
    private var menuQuizActivityButton: Button? = null
    private var intent: Intent? = null
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuBmiActivityButton = findViewById(R.id.menu_activity_bmi) as Button?
        menuCaloriesActivityButton = findViewById(R.id.menu_activity_calories) as Button?
        menuEatActivityButton = findViewById(R.id.menu_activity_eat) as Button?
        menuChartActivityButton = findViewById(R.id.menu_activity_chart) as Button?
        menuQuizActivityButton = findViewById(R.id.menu_activity_quiz) as Button?
        menuBmiActivityButton.setOnClickListener { e -> goToBmiActivity() }
        menuCaloriesActivityButton.setOnClickListener { e -> goToCaloriesActivity() }
        menuEatActivityButton.setOnClickListener { e -> goToEatActivity() }
        menuChartActivityButton.setOnClickListener { e -> goToChartActivity() }
        menuQuizActivityButton.setOnClickListener { e -> goToQuizActivity() }
    }

    private fun goToBmiActivity() {
        intent = Intent(this, BmiActivity::class.java)
        startActivity(intent)
    }

    private fun goToCaloriesActivity() {
        intent = Intent(this, CaloriesActivity::class.java)
        startActivity(intent)
    }

    private fun goToEatActivity() {
        intent = Intent(this, EatActivity::class.java)
        startActivity(intent)
    }

    private fun goToChartActivity() {
        intent = Intent(this, ChartActivity::class.java)
        startActivity(intent)
    }

    private fun goToQuizActivity() {
        intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }
}