enum class Player {
    HUMAN {
        override fun getWinnerMessage() = "You WIN!!"
    },
    AI {
        override fun getWinnerMessage() = "AI WINS!!"
    };

    abstract fun getWinnerMessage(): String
}