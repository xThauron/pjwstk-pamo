package eu.xthauron.bmicalc.Fragment

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.List
import eu.xthauron.bmicalc.Model.Answer
import eu.xthauron.bmicalc.Model.Question
import eu.xthauron.bmicalc.Model.Quiz
import eu.xthauron.bmicalc.R

/**
 * A simple [Fragment] subclass.
 * Use the [QuizQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizQuestionFragment : Fragment() {
    private var button_next: Button? = null
    private var button_answer1: Button? = null
    private var button_answer2: Button? = null
    private var button_answer3: Button? = null
    private var button_answer4: Button? = null
    private var buttonAnswersList: List<Button>? = null
    private var tv_question_counter: TextView? = null
    private var tv_question_name: TextView? = null
    private var tv_question_results: TextView? = null
    private var result = 0
    private var questionCount = 0
    private var question: Question? = null
    private var correctAnswer: Answer? = null
    private val quizParam: String? = null
    private var quiz: Quiz? = null
    @Override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            quiz = getArguments().getSerializable(ARG_QUIZ) as Quiz
        }
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_quiz_question, container, false)
        tv_question_counter = view.findViewById(R.id.quiz_question_number) as TextView
        tv_question_results = view.findViewById(R.id.quiz_question_result) as TextView
        tv_question_name = view.findViewById(R.id.quiz_question) as TextView
        button_answer1 = view.findViewById(R.id.b_answer_1) as Button
        button_answer2 = view.findViewById(R.id.b_answer_2) as Button
        button_answer3 = view.findViewById(R.id.b_answer_3) as Button
        button_answer4 = view.findViewById(R.id.b_answer_4) as Button
        button_next = view.findViewById(R.id.btn_quiz_next) as Button
        buttonAnswersList = Arrays.asList(button_answer1, button_answer2, button_answer3, button_answer4)
        for (button in buttonAnswersList!!) {
            button.setOnClickListener(AnswerButtonListener())
        }
        button_next.setOnClickListener(NextQuestionListener())
        return view
    }

    fun initializeQuiz() {
        Collections.shuffle(quiz.getQuestions())
        result = 0
        questionCount = 0
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        button_next.setEnabled(false)
        question = quiz.getQuestions().get(questionCount)
        correctAnswer = question.getAnswers().get(question.getCorrectAnswerId())
        questionCount++
        tv_question_results.setText(null)
        tv_question_counter.setText(getString(R.string.t_question_number, questionCount, quiz.getQuestions().size()))
        tv_question_name.setText(question.getName())
        for (i in 0 until question.getAnswers().size()) {
            val answer: Answer = question.getAnswers().get(i)
            val answerButton: Button = buttonAnswersList!![i]
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            answerButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.purple_500))
            answerButton.setEnabled(true)
            answerButton.setText(answer.getText())
        }
    }

    private inner class AnswerButtonListener : View.OnClickListener {
        @Override
        fun onClick(view: View) {
            val answerButton: Button = view as Button
            val answerText: String = answerButton.getText().toString()
            if (answerText.equals(correctAnswer.getText())) {
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
            buttonAnswersList.forEach { buttonAnswer -> buttonAnswer.setEnabled(false) }
            answerButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            button_next.setEnabled(true)
        }
    }

    private fun colorizeCorrectAnswerButton() {
        buttonAnswersList.stream()
                .filter { b -> b.getText().toString().equals(correctAnswer.getText()) }
                .findFirst()
                .ifPresent { correctButton ->
                    correctButton.setBackgroundColor(Color.GREEN)
                    correctButton.setTextColor(Color.WHITE)
                }
    }

    private fun showQuizResult() {
        val bundle = Bundle()
        bundle.putInt("result", result)
        bundle.putInt("questionsCount", quiz.getQuestions().size())
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.f_quiz, QuizResultFragment::class.java, bundle, "QUIZ_RESULT_FRAGMENT")
                .addToBackStack(null)
                .commit()
    }

    private inner class NextQuestionListener : View.OnClickListener {
        @Override
        fun onClick(v: View) {
            val button: Button = v as Button
            if (questionCount == quiz.getQuestions().size()) {
                showQuizResult()
                button.setVisibility(View.GONE)
            }
            if (questionCount < quiz.getQuestions().size()) {
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
            fragment.setArguments(args)
            return fragment
        }
    }
}