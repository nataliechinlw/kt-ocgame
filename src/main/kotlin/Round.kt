import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction

class Round(private val currentPredictor: Player) {
    var winner: Player?

    init {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        winner = evaluateWinner(playerInput, aiInput)
        printWinner(winner)
    }

    private fun askForInput(): PlayerInput {
        val message = when (currentPredictor) {
            Player.HUMAN -> "You are the predictor, what is your input?"
            Player.AI -> "AI is the predictor, what is your input?"
        }
        Terminal.printMessage(message)
        val inputValidator = if (currentPredictor == Player.HUMAN) ::inputWithPrediction else ::inputWithoutPrediction
        return PlayerInput.create(Terminal.getInput(inputValidator), currentPredictor == Player.HUMAN)
    }

    private fun generateAiInput(): AiInput {
        val aiInput = AiInput.create(currentPredictor == Player.AI)
        Terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    fun evaluateWinner(playerInput: Input, aiInput: Input): Player? {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = when (currentPredictor) {
            Player.HUMAN -> playerInput.prediction
            Player.AI -> aiInput.prediction
        }
        return if (totalNumberOfOpenHands != prediction) null else currentPredictor
    }

    fun printWinner(winner: Player?) =
        Terminal.printMessage(winner?.getWinnerMessage() ?: "No winner.")
}