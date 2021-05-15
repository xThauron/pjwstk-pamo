package eu.xthauron.bmicalc.Model

import java.io.Serializable
import java.util.*

class Question : Serializable {
    var name: String? = null
    var answers: List<Answer> = ArrayList()
    var correctAnswerId = 0

    companion object {
        private const val serialVersionUID = 8351897667222850048L
    }
}