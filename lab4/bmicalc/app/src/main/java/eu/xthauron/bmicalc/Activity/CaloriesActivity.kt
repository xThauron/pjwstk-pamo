package eu.xthauron.bmicalc.Activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import eu.xthauron.bmicalc.Enum.Gender
import eu.xthauron.bmicalc.R

class CaloriesActivity : AppCompatActivity() {
    private var r_calories: TextView? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)
        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_user_data), MODE_PRIVATE)
        r_calories = findViewById<View>(R.id.r_calories) as TextView
        r_calories!!.text = String.format(getString(R.string.calories_result), calories)
    }

    private val calories: Double
        get() {
            val weight: Float
            val height: Float
            val age: Int
            val gender: Gender
            height = sharedPreferences!!.getFloat(getString(R.string.key_height), 0f)
            weight = sharedPreferences!!.getFloat(getString(R.string.key_weight), 0f)
            age = sharedPreferences!!.getInt(getString(R.string.key_age), 0)
            gender =
                Gender.valueOf(sharedPreferences!!.getString(getString(R.string.key_gender), "")!!)
            if (gender == Gender.FEMALE) {
                return 655.1 + 9.567 * weight + 1.85 * height - 4.68 * age
            } else if (gender == Gender.MALE) {
                return 66.47 + 13.7 * weight + 5 * height - 6.76 * age
            }
            return 0.0
        }
}