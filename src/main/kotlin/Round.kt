import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction

class Round(private val currentPredictor: Player) {
    var winner: Player?

    init {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        winner = evaluateWinner(playerInput, aiInput)
        printWinner()
    }

    private fun askForInput(): PlayerInput {
        val message = currentPredictor.getAskForInputMessage()
        Terminal.printMessage(message)
        val inputValidator = if (currentPredictor == Player.HUMAN) ::inputWithPrediction else ::inputWithoutPrediction
        return PlayerInput.create(Terminal.getInput(inputValidator), currentPredictor == Player.HUMAN)
    }

    private fun generateAiInput(): AiInput {
        val aiInput = AiInput.create(currentPredictor == Player.AI)
        Terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    private fun evaluateWinner(playerInput: Input, aiInput: Input): Player? {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = when (currentPredictor) {
            Player.HUMAN -> playerInput.prediction
            Player.AI -> aiInput.prediction
        }
        return if (totalNumberOfOpenHands != prediction) null else currentPredictor
    }

    fun printWinner() = Terminal.printMessage(winner?.getWinnerMessage() ?: "No winner.")
}