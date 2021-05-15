package eu.xthauron.bmicalc.Activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import eu.xthauron.bmicalc.Enum.BmiCategory
import eu.xthauron.bmicalc.Enum.Gender
import eu.xthauron.bmicalc.R

class BmiActivity : AppCompatActivity() {
    private lateinit var et_height: EditText
    private lateinit var et_weight: EditText
    private lateinit var et_age: EditText
    private lateinit var tv_result: TextView
    private lateinit var rg_gender: RadioGroup
    private lateinit var et_gender_male: RadioButton
    private lateinit var et_gender_female: RadioButton
    private lateinit var bt_result: Button
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        et_height = findViewById(R.id.et_height)
        et_weight = findViewById(R.id.et_weight)
        bt_result = findViewById(R.id.bt_result)
        tv_result = findViewById(R.id.tv_result)
        et_age = findViewById(R.id.et_age)
        rg_gender = findViewById(R.id.rg_gender)
        et_gender_female = findViewById(R.id.et_gender_female)
        et_gender_male = findViewById(R.id.et_gender_male)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), MODE_PRIVATE)
        if (sharedPreferences.contains(getString(R.string.key_bmi))) {
            et_height.setText(
                sharedPreferences.getFloat(getString(R.string.key_height), 0f).toString()
            )
            et_weight.setText(
                sharedPreferences.getFloat(getString(R.string.key_weight), 0f).toString()
            )
            et_age.setText(sharedPreferences.getInt(getString(R.string.key_age), 0).toString())
            val genderPreference = sharedPreferences.getString(getString(R.string.key_gender), "")
            if (genderPreference!!.isNotEmpty()) {
                val gender: Gender = Gender.valueOf(genderPreference)
                if (gender.equals(Gender.MALE)) {
                    et_gender_male.toggle()
                } else if (gender.equals(Gender.FEMALE)) {
                    et_gender_female.toggle()
                }
            }
        }
        bt_result.setOnClickListener(ResultButton())
    }

    private inner class ResultButton : View.OnClickListener {
        override fun onClick(view: View) {
            val s_height = et_height.text.toString()
            val s_weight = et_weight.text.toString()
            val s_age = et_age.text.toString()
            val category: String
            val height: Float
            val weight: Float
            val bmi: Float
            val age: Int
            try {
                height = s_height.toFloat() / 100
                weight = s_weight.toFloat()
                age = s_age.toInt()
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                return
            }
            bmi = weight / (height * height)
            category = getBMICategory(bmi.toDouble()).toString()
            tv_result.text = String.format(getString(R.string.result), bmi, category)
            val editor = sharedPreferences.edit()
            editor.putFloat(getString(R.string.key_height), height * 100)
            editor.putFloat(getString(R.string.key_weight), weight)
            editor.putFloat(getString(R.string.key_bmi), bmi)
            editor.putString(getString(R.string.key_bmiCategory), category)
            editor.putInt(getString(R.string.key_age), age)
            if (gender != null) {
                val gender: String = gender.toString()
                editor.putString(getString(R.string.key_gender), gender)
            }
            editor.apply()
        }
    }

    private val gender: Gender?
        private get() {
            val selectedGender = rg_gender.checkedRadioButtonId
            if (selectedGender == R.id.et_gender_male) {
                return Gender.MALE
            } else if (selectedGender == R.id.et_gender_female) {
                return Gender.FEMALE
            }
            return null
        }

    private fun getBMICategory(bmi: Double): BmiCategory {
        return if (bmi < 18.5) {
            BmiCategory.UNDERWEIGHT
        } else if (bmi <= 24.9) {
            BmiCategory.NORMAL
        } else if (bmi <= 29.9) {
            BmiCategory.OVERWEIGHT
        } else {
            BmiCategory.OBESE
        }
    }
}