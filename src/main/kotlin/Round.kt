class Round(private val currentPredictor: Player, players: List<Player> = listOf(Player.HUMAN, Player.AI)) {
    var winner: Player? = null

    init {
        val inputs = players.map{ it.generateInput(currentPredictor) }
        val prediction = inputs.find{ it.player == currentPredictor }!!.prediction
        winner = evaluateWinner(inputs, prediction!!)
        printWinner()
    }

    private fun evaluateWinner(inputs: List<Input>, prediction: Int): Player? {
        val totalNumberOfOpenHands = inputs.sumOf { it.numberOfOpenHands }
        return if (totalNumberOfOpenHands == prediction) currentPredictor else null
    }

    fun printWinner() = Terminal.printMessage(winner?.getWinnerMessage() ?: "No winner.")
}