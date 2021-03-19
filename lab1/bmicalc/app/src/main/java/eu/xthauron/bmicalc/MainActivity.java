package eu.xthauron.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_height, et_weight;
    private Button bt_result;
    private String s_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.s_result = getString(R.string.result);
        this.et_height = findViewById(R.id.et_height);
        this.et_weight = findViewById(R.id.et_weight);
        this.bt_result = findViewById(R.id.bt_result);
        this.et_height.addTextChangedListener(new TextListener());
        this.et_weight.addTextChangedListener(new TextListener());
    }

    private class TextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String s_height = et_height.getText().toString(), s_weight = et_weight.getText().toString(), category;
            if(s_height.isEmpty() || s_weight.isEmpty()) {
                bt_result.setVisibility(View.INVISIBLE);
                return;
            }else{
                Float height, weight, bmi;
                try {
                    height = Float.parseFloat(s_height) / 100;
                    weight = Float.parseFloat(s_weight);
                }catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    return;
                }
                bmi = weight / (height * height);
                category = getBMICategory(bmi);
                bt_result.setVisibility(View.VISIBLE);
                bt_result.setText(String.format(s_result, bmi, category));
            }

        }

        private String getBMICategory(double bmi) {
            if(bmi < 18.5) {
                return "Underweight";
            } else if(bmi <= 24.9) {
                return "Normal";
            } else if(bmi <= 29.9) {
                return "Overweight";
            } else {
                return "Obese";
            }
        }
    }
}