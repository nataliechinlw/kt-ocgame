import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction

const val ASK_FOR_INPUT = ", what is your input?"

enum class Player {
    HUMAN {
        override fun getWinnerMessage() = "You WIN!!"
        override fun getAskForInputMessage() = "You are the predictor$ASK_FOR_INPUT"
        override fun generateInput(currentPredictor: Player): PlayerInput {
            val message = currentPredictor.getAskForInputMessage()
            Terminal.printMessage(message)
            val inputValidator =
                if (currentPredictor == HUMAN) ::inputWithPrediction else ::inputWithoutPrediction
            return PlayerInput.create(Terminal.getInput(inputValidator), currentPredictor == HUMAN)
        }
    },
    AI {
        override fun getWinnerMessage() = "AI WINS!!"
        override fun getAskForInputMessage() = "AI is the predictor$ASK_FOR_INPUT"
    };

    abstract fun getWinnerMessage(): String
    abstract fun getAskForInputMessage(): String
    open fun generateInput(currentPredictor: Player): Input {
        return AiInput()
    }

}