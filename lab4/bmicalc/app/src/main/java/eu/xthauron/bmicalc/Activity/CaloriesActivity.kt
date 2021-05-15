package eu.xthauron.bmicalc.Activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text
import eu.xthauron.bmicalc.Enum.Gender
import eu.xthauron.bmicalc.R

class CaloriesActivity : AppCompatActivity() {
    private var r_calories: TextView? = null
    private var sharedPreferences: SharedPreferences? = null
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), Context.MODE_PRIVATE)
        r_calories = findViewById(R.id.r_calories) as TextView?
        r_calories.setText(String.format(getString(R.string.calories_result), calories))
    }

    private val calories: Double
        private get() {
            val weight: Float
            val height: Float
            val age: Int
            val gender: Gender
            height = sharedPreferences.getFloat(getString(R.string.key_height), 0)
            weight = sharedPreferences.getFloat(getString(R.string.key_weight), 0)
            age = sharedPreferences.getInt(getString(R.string.key_age), 0)
            gender = Gender.valueOf(sharedPreferences.getString(getString(R.string.key_gender), ""))
            if (gender.equals(Gender.FEMALE)) {
                return 655.1 + 9.567 * weight + 1.85 * height - 4.68 * age
            } else if (gender.equals(Gender.MALE)) {
                return 66.47 + 13.7 * weight + 5 * height - 6.76 * age
            }
            return 0
        }
}