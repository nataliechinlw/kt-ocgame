import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction

class Round(private val currentPredictor: Player) {
    var winner: Player?

    init {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        val prediction = when (currentPredictor) {
            Player.HUMAN -> playerInput.prediction
            Player.AI -> aiInput.prediction
        }
        winner = evaluateWinner(listOf(playerInput, aiInput), prediction!!)
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

    private fun evaluateWinner(inputs: List<Input>, prediction: Int): Player? {
        val totalNumberOfOpenHands = inputs.sumOf { it.numberOfOpenHands }
        return if (totalNumberOfOpenHands == prediction) currentPredictor else null
    }

    fun printWinner() = Terminal.printMessage(winner?.getWinnerMessage() ?: "No winner.")
}