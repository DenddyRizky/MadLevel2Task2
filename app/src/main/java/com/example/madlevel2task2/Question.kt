package com.example.madlevel2task2

data class Question(
    var question: String,
    var answer: String
)   {
    companion object{
        val QUESTIONS = arrayOf(
            "2 + 2 equals 4",
            "3 + 3 equals 9"
        )

        val ANSWERS = arrayOf(
            "true",
            "false"
        )
    }
}