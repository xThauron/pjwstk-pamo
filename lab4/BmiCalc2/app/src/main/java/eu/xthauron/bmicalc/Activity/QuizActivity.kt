package eu.xthauron.bmicalc.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import eu.xthauron.bmicalc.Fragment.QuizQuestionFragment
import eu.xthauron.bmicalc.Model.Quiz
import eu.xthauron.bmicalc.R
import java.io.IOException
import java.nio.charset.StandardCharsets

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        var json = ""
        try {
            json = loadJSONFromAssets("json/quiz.json")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val mapper = ObjectMapper()
        if (json.isEmpty()) return
        var quiz: Quiz? = null
        try {
            quiz = mapper.readValue(json, Quiz::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val bundle = Bundle()
        bundle.putSerializable("quiz", quiz)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.f_quiz, QuizQuestionFragment::class.java, bundle, "QUIZ_FRAGMENT")
            .commit()
    }

    @Throws(IOException::class)
    private fun loadJSONFromAssets(fileName: String): String {
        val json: String
        val `is` = assets.open(fileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer, StandardCharsets.UTF_8)
        return json
    }

    override fun onStart() {
        super.onStart()
        val quizQuestionFragment = supportFragmentManager
            .findFragmentById(R.id.f_quiz) as QuizQuestionFragment?
        quizQuestionFragment?.initializeQuiz()
    }
}