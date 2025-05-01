package hu.ait.studyguide.data.model

data class StudyContent(
    val summary: String,
    val notes: List<String>,
    val quiz: List<QuizQuestion>
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
