package eu.xthauron.bmicalc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import eu.xthauron.bmicalc.Enum.BmiCategory;
import eu.xthauron.bmicalc.Enum.Gender;
import eu.xthauron.bmicalc.R;

public class BmiActivity extends AppCompatActivity {

    private EditText et_height, et_weight, et_age;
    private TextView tv_result;
    private RadioGroup rg_gender;
    private RadioButton et_gender_male, et_gender_female;
    private Button bt_result;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        bt_result = findViewById(R.id.bt_result);
        tv_result = findViewById(R.id.tv_result);
        et_age = findViewById(R.id.et_age);
        rg_gender = findViewById(R.id.rg_gender);
        et_gender_female = findViewById(R.id.et_gender_female);
        et_gender_male = findViewById(R.id.et_gender_male);

        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), Context.MODE_PRIVATE);

        if(sharedPreferences.contains(getString(R.string.key_bmi))) {
            et_height.setText(String.valueOf(sharedPreferences.getFloat(getString(R.string.key_height), 0)));
            et_weight.setText(String.valueOf(sharedPreferences.getFloat(getString(R.string.key_weight), 0)));
            et_age.setText(String.valueOf(sharedPreferences.getInt(getString(R.string.key_age), 0)));

            String genderPreference = sharedPreferences.getString(getString(R.string.key_gender), "");
            if(!genderPreference.isEmpty()) {
                Gender gender = Gender.valueOf(genderPreference);
                if(gender.equals(Gender.MALE)) {
                    et_gender_male.toggle();
                } else if(gender.equals(Gender.FEMALE)) {
                    et_gender_female.toggle();
                }
            }
        }



        bt_result.setOnClickListener(new ResultButton());
    }

    private class ResultButton implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String s_height = et_height.getText().toString(), s_weight = et_weight.getText().toString(), s_age = et_age.getText().toString(), category;
            float height, weight, bmi;
            int age;
            try {
                height = Float.parseFloat(s_height) / 100;
                weight = Float.parseFloat(s_weight);
                age = Integer.parseInt(s_age);
            }catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                return;
            }
            bmi = weight / (height * height);
            category = getBMICategory(bmi).toString();
            tv_result.setText(String.format(getString(R.string.result), bmi, category));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(getString(R.string.key_height), height*100);
            editor.putFloat(getString(R.string.key_weight), weight);
            editor.putFloat(getString(R.string.key_bmi), bmi);
            editor.putString(getString(R.string.key_bmiCategory), category);
            editor.putInt(getString(R.string.key_age), age);

            if(getGender() != null) {
                String gender = getGender().toString();
                editor.putString(getString(R.string.key_gender), gender);
            }

            editor.apply();

        }
    }

    private Gender getGender() {
        int selectedGender = rg_gender.getCheckedRadioButtonId();
        if(selectedGender == R.id.et_gender_male) {
            return Gender.MALE;
        } else if(selectedGender == R.id.et_gender_female) {
            return Gender.FEMALE;
        }

        return null;
    }

    private BmiCategory getBMICategory(double bmi) {
        if(bmi < 18.5) {
            return BmiCategory.UNDERWEIGHT;
        } else if(bmi <= 24.9) {
            return BmiCategory.NORMAL;
        } else if(bmi <= 29.9) {
            return BmiCategory.OVERWEIGHT;
        } else {
            return BmiCategory.OBESE;
        }
    }
}