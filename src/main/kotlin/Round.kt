class Round(private val currentPredictor: Player, players: List<Player> = listOf(Player.HUMAN, Player.AI)) {
    var winner: Player? = null

    init {
        val inputs = players.map { it.generateInput(currentPredictor) }
        val prediction = inputs.find { it.player == currentPredictor }!!.prediction
        evaluateWinner(inputs, prediction!!)
        printWinner()
    }

    private fun evaluateWinner(inputs: List<Input>, prediction: Int) {
        val totalNumberOfOpenHands = inputs.sumOf { it.numberOfOpenHands }
        winner = if (totalNumberOfOpenHands == prediction) currentPredictor else null
    }

    fun printWinner() = Terminal.printMessage(winner?.getWinnerMessage() ?: "No winner.")
}