const val ASK_FOR_INPUT = ", what is your input?"

enum class Player {
    HUMAN {
        override fun getWinnerMessage() = "You WIN!!"
        override fun getAskForInputMessage() = "You are the predictor$ASK_FOR_INPUT"
    },
    AI {
        override fun getWinnerMessage() = "AI WINS!!"
        override fun getAskForInputMessage() = "AI is the predictor$ASK_FOR_INPUT"
    };

    abstract fun getWinnerMessage(): String
    abstract fun getAskForInputMessage(): String
}