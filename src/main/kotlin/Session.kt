class Session {
    private val players = listOf(Player.HUMAN, Player.AI)
    var winner: Player? = null
    var predictorQueue = players.toMutableList()
    fun currentPredictor() = predictorQueue.elementAt(0)

    fun run() {
        while (winner == null) {
            winner = Round(currentPredictor()).winner
            setNextPredictor()
        }    }

    fun setNextPredictor() {
        val nextPlayer = predictorQueue.removeAt(0)
        predictorQueue.add(nextPlayer)
    }
}