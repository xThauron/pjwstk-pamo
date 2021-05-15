package eu.xthauron.bmicalc.Model

import java.io.Serializable
import java.util.ArrayList
import java.util.List

class Quiz : Serializable {
    var name: String? = null
    var questions: List<Question> = ArrayList()
    var result = 0

    companion object {
        private const val serialVersionUID = 2788049829090491540L
    }
}