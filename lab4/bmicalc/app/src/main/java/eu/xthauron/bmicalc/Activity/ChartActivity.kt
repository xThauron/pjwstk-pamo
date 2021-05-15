package eu.xthauron.bmicalc.Activity

import eu.xthauron.bmicalc.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import org.w3c.dom.Document

class ChartActivity : AppCompatActivity() {
    var wb_chart: WebView? = null
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        wb_chart = findViewById(R.id.wb_chart) as WebView?
        wb_chart.loadUrl("https://bmi-online.pl/wykres-bmi")
    }
}