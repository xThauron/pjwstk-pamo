package eu.xthauron.bmicalc.Activity;
import eu.xthauron.bmicalc.R;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import org.w3c.dom.Document;

public class ChartActivity extends AppCompatActivity {
    WebView wb_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        wb_chart = (WebView) findViewById(R.id.wb_chart);

        wb_chart.loadUrl("https://bmi-online.pl/wykres-bmi");
    }
}