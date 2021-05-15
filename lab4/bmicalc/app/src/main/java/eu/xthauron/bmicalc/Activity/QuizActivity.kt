package eu.xthauron.bmicalc.Activity

import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
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
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.f_quiz, QuizQuestionFragment::class.java, bundle, "QUIZ_FRAGMENT")
                .commit()
    }

    @Throws(IOException::class)
    private fun loadJSONFromAssets(fileName: String): String {
        val json: String
        val `is`: InputStream = getAssets().open(fileName)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer, StandardCharsets.UTF_8)
        return json
    }

    @Override
    protected fun onStart() {
        super.onStart()
        val quizQuestionFragment: QuizQuestionFragment = getSupportFragmentManager()
                .findFragmentById(R.id.f_quiz) as QuizQuestionFragment
        if (quizQuestionFragment != null) {
            quizQuestionFragment.initializeQuiz()
        }
    }
}