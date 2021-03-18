class Round(private val currentPredictor: Player) {
    var winner: Player? = null

    init {
        val playerInput = Player.HUMAN.generateInput(currentPredictor)
        val aiInput = generateAiInput()
        val prediction = when (currentPredictor) {
            Player.HUMAN -> playerInput.prediction
            Player.AI -> aiInput.prediction
        }
        winner = evaluateWinner(listOf(playerInput, aiInput), prediction!!)
        printWinner()
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