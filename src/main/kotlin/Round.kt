import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction

class Round(private val currentPredictor: PLAYER) {
    var winner: PLAYER?
    init {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        winner = evaluateWinner(playerInput, aiInput)
        printWinner(winner)
    }

    private fun askForInput(): PlayerInput {
        val message = when (currentPredictor) {
            PLAYER.HUMAN -> "You are the predictor, what is your input?"
            PLAYER.AI -> "AI is the predictor, what is your input?"
        }
        Terminal.printMessage(message)
        val inputValidator = if (currentPredictor == PLAYER.HUMAN) ::inputWithPrediction else ::inputWithoutPrediction
        return PlayerInput.create(Terminal.getInput(inputValidator), currentPredictor == PLAYER.HUMAN)
    }

    private fun generateAiInput(): AiInput {
        val aiInput = AiInput.create(currentPredictor == PLAYER.AI)
        Terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    fun evaluateWinner(playerInput: Input, aiInput: Input): PLAYER? {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = when (currentPredictor) {
            PLAYER.HUMAN -> playerInput.prediction
            PLAYER.AI -> aiInput.prediction
        }
        return if (totalNumberOfOpenHands != prediction) null else currentPredictor
    }

    fun printWinner(winner: PLAYER?) = Terminal.printMessage(
        when (winner) {
            PLAYER.HUMAN -> "You WIN!!"
            PLAYER.AI -> "AI WINS!!"
            null -> "No winner."
        }
    )
}