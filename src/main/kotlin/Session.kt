class Session {
    var winner: Player? = null
    fun currentPredictor() = Player.HUMAN

    init {
        while (winner == null) {
            winner = Round(currentPredictor()).winner
        }
    }
}