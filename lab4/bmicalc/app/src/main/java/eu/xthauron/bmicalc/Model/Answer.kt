package eu.xthauron.bmicalc.Model

import java.io.Serializable

class Answer : Serializable {
    var id = 0
    var text: String? = null

    companion object {
        private const val serialVersionUID = 5150556261742181523L
    }
}