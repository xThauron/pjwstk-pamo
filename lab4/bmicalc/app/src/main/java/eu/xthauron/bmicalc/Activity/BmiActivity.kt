package eu.xthauron.bmicalc.Activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import eu.xthauron.bmicalc.Enum.BmiCategory
import eu.xthauron.bmicalc.Enum.Gender
import eu.xthauron.bmicalc.R

class BmiActivity : AppCompatActivity() {
    private var et_height: EditText? = null
    private var et_weight: EditText? = null
    private var et_age: EditText? = null
    private var tv_result: TextView? = null
    private var rg_gender: RadioGroup? = null
    private var et_gender_male: RadioButton? = null
    private var et_gender_female: RadioButton? = null
    private var bt_result: Button? = null
    var sharedPreferences: SharedPreferences? = null
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
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
        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), Context.MODE_PRIVATE)
        if (sharedPreferences.contains(getString(R.string.key_bmi))) {
            et_height.setText(String.valueOf(sharedPreferences.getFloat(getString(R.string.key_height), 0)))
            et_weight.setText(String.valueOf(sharedPreferences.getFloat(getString(R.string.key_weight), 0)))
            et_age.setText(String.valueOf(sharedPreferences.getInt(getString(R.string.key_age), 0)))
            val genderPreference: String = sharedPreferences.getString(getString(R.string.key_gender), "")
            if (!genderPreference.isEmpty()) {
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
        @Override
        fun onClick(view: View?) {
            val s_height: String = et_height.getText().toString()
            val s_weight: String = et_weight.getText().toString()
            val s_age: String = et_age.getText().toString()
            val category: String
            val height: Float
            val weight: Float
            val bmi: Float
            val age: Int
            try {
                height = Float.parseFloat(s_height) / 100
                weight = Float.parseFloat(s_weight)
                age = Integer.parseInt(s_age)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                return
            }
            bmi = weight / (height * height)
            category = getBMICategory(bmi.toDouble()).toString()
            tv_result.setText(String.format(getString(R.string.result), bmi, category))
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
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
            val selectedGender: Int = rg_gender.getCheckedRadioButtonId()
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