package eu.xthauron.bmicalc.Fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import eu.xthauron.bmicalc.Model.Answer
import eu.xthauron.bmicalc.Model.Question
import eu.xthauron.bmicalc.Model.Quiz
import eu.xthauron.bmicalc.R
import java.util.*
import java.util.Collections.shuffle
import java.util.function.Consumer

/**
 * A simple [Fragment] subclass.
 * Use the [QuizQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
public class QuizQuestionFragment : Fragment() {
    private lateinit var button_next: Button
    private lateinit var button_answer1: Button
    private lateinit var button_answer2: Button
    private lateinit var button_answer3: Button
    private lateinit var button_answer4: Button
    private lateinit var buttonAnswersList: List<Button>
    private lateinit var tv_question_counter: TextView
    private lateinit var tv_question_name: TextView
    private lateinit var tv_question_results: TextView
    private var result = 0
    private var questionCount = 0
    private lateinit var question: Question
    private lateinit var correctAnswer: Answer
    private val quizParam: String? = null
    private lateinit var quiz: Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            quiz = requireArguments().getSerializable(ARG_QUIZ) as Quiz
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_question, container, false)
        tv_question_counter = view.findViewById<View>(R.id.quiz_question_number) as TextView
        tv_question_results = view.findViewById<View>(R.id.quiz_question_result) as TextView
        tv_question_name = view.findViewById<View>(R.id.quiz_question) as TextView
        button_answer1 = view.findViewById<View>(R.id.b_answer_1) as Button
        button_answer2 = view.findViewById<View>(R.id.b_answer_2) as Button
        button_answer3 = view.findViewById<View>(R.id.b_answer_3) as Button
        button_answer4 = view.findViewById<View>(R.id.b_answer_4) as Button
        button_next = view.findViewById<View>(R.id.btn_quiz_next) as Button
        buttonAnswersList =
            listOf(button_answer1, button_answer2, button_answer3, button_answer4)
        for (button in buttonAnswersList) {
            button.setOnClickListener(AnswerButtonListener())
        }
        button_next.setOnClickListener(NextQuestionListener())
        return view
    }

    fun initializeQuiz() {
        shuffle(quiz.questions)
        result = 0
        questionCount = 0
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        button_next.isEnabled = false
        question = quiz.questions[questionCount]
        correctAnswer = question.answers[question!!.correctAnswerId]
        questionCount++
        tv_question_results.setText(null)
        tv_question_counter.text =
            getString(R.string.t_question_number, questionCount, quiz!!.questions.size)
        tv_question_name.text = question.name
        for (i in question.answers.indices) {
            val answer = question.answers[i]
            val answerButton = buttonAnswersList[i]
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            answerButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.purple_500
                )
            )
            answerButton.isEnabled = true
            answerButton.text = answer.text
        }
    }

    private inner class AnswerButtonListener : View.OnClickListener {
        override fun onClick(view: View) {
            val answerButton = view as Button
            val answerText = answerButton.text.toString()
            if (answerText == correctAnswer.text) {
                result++
                tv_question_results.setText(R.string.t_correct_answer)
                tv_question_results.setTextColor(Color.GREEN)
                answerButton.setBackgroundColor(Color.GREEN)
            } else {
                tv_question_results.setText(R.string.t_false_answer)
                tv_question_results.setTextColor(Color.RED)
                answerButton.setBackgroundColor(Color.RED)
                colorizeCorrectAnswerButton()
            }
            buttonAnswersList.forEach(Consumer { buttonAnswer: Button ->
                buttonAnswer.isEnabled = false
            })
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            button_next.isEnabled = true
        }
    }

    private fun colorizeCorrectAnswerButton() {
        buttonAnswersList.stream()
            .filter { b: Button ->
                b.text.toString() == correctAnswer!!.text
            }
            .findFirst()
            .ifPresent { correctButton: Button ->
                correctButton.setBackgroundColor(Color.GREEN)
                correctButton.setTextColor(Color.WHITE)
            }
    }

    private fun showQuizResult() {
        val bundle = Bundle()
        bundle.putInt("result", result)
        bundle.putInt("questionsCount", quiz.questions.size)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.f_quiz, QuizResultFragment::class.java, bundle, "QUIZ_RESULT_FRAGMENT")
            .addToBackStack(null)
            .commit()

    }

    private inner class NextQuestionListener : View.OnClickListener {
        override fun onClick(v: View) {
            val button = v as Button
            if (questionCount == quiz.questions.size) {
                showQuizResult()
                button.visibility = View.GONE
            }
            if (questionCount < quiz.questions.size) {
                loadNextQuestion()
            }
        }
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_QUIZ = "quiz"
        fun newInstance(quiz: Quiz?): QuizQuestionFragment {
            val fragment = QuizQuestionFragment()
            val args = Bundle()
            args.putSerializable(ARG_QUIZ, quiz)
            fragment.arguments = args
            return fragment
        }
    }
}
