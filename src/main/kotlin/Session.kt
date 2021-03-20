class Session {
    var winner: Player? = null
    init {
        while (winner == null) {
            winner = Round(Player.HUMAN).winner
        }
    }
}