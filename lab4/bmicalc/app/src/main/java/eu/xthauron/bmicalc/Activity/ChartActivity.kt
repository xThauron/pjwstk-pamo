package eu.xthauron.bmicalc.Activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import eu.xthauron.bmicalc.R

class ChartActivity : AppCompatActivity() {
    var wb_chart: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        wb_chart = findViewById<View>(R.id.wb_chart) as WebView
        wb_chart!!.loadUrl("https://bmi-online.pl/wykres-bmi")
    }
}