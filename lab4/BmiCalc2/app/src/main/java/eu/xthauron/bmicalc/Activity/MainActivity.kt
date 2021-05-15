package eu.xthauron.bmicalc.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import eu.xthauron.bmicalc.R

class MainActivity : AppCompatActivity() {
    private var menuBmiActivityButton: Button? = null
    private var menuCaloriesActivityButton: Button? = null
    private var menuEatActivityButton: Button? = null
    private var menuChartActivityButton: Button? = null
    private var menuQuizActivityButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuBmiActivityButton = findViewById<View>(R.id.menu_activity_bmi) as Button
        menuCaloriesActivityButton = findViewById<View>(R.id.menu_activity_calories) as Button
        menuEatActivityButton = findViewById<View>(R.id.menu_activity_eat) as Button
        menuChartActivityButton = findViewById<View>(R.id.menu_activity_chart) as Button
        menuQuizActivityButton = findViewById<View>(R.id.menu_activity_quiz) as Button
        menuBmiActivityButton!!.setOnClickListener { e: View? -> goToBmiActivity() }
        menuCaloriesActivityButton!!.setOnClickListener { e: View? -> goToCaloriesActivity() }
        menuEatActivityButton!!.setOnClickListener { e: View? -> goToEatActivity() }
        menuChartActivityButton!!.setOnClickListener { e: View? -> goToChartActivity() }
        menuQuizActivityButton!!.setOnClickListener { e: View? -> goToQuizActivity() }
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