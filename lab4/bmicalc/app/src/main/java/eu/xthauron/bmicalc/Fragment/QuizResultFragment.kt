package eu.xthauron.bmicalc.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import eu.xthauron.bmicalc.Activity.MainActivity
import eu.xthauron.bmicalc.Activity.QuizActivity
import eu.xthauron.bmicalc.R

class QuizResultFragment : Fragment() {
    private var tv_result: TextView? = null
    private var button_menu: Button? = null
    private var intent: Intent? = null
    private var result = 0
    private var questionsCount = 0
    @Override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            result = getArguments().getInt(PARAM_RESULT)
            questionsCount = getArguments().getInt(PARAM_QUESTION_COUNT)
        }
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_quiz_result, container, false)
        tv_result = view.findViewById(R.id.quiz_result) as TextView
        button_menu = view.findViewById(R.id.button_menu) as Button
        tv_result.setText(getString(R.string.t_result, result, questionsCount))
        button_menu.setOnClickListener { e -> goToMenuActivity() }
        return view
    }

    private fun goToMenuActivity() {
        intent = Intent(this.getActivity(), MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val PARAM_RESULT = "result"
        private const val PARAM_QUESTION_COUNT = "questionsCount"
    }
}