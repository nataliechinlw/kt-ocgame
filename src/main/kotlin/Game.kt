import InputValidator.yesNo

class Game {
    var winner: Player? = null
    var players = listOf(Player.HUMAN, Player.AI).toMutableList()
    fun currentPredictor() = players.elementAt(0)

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        while (true) {
            winner = null
            resetPlayers()
            runSession()
            Terminal.printMessage("Do you want to play again?")
            val replayInput = Terminal.getInput(::yesNo)
            if (replayInput == "N")
                break
        }
        Terminal.printMessage("Ok, bye!")
    }

    private fun runSession() {
        while (winner == null) {
            winner = Round(currentPredictor()).winner
            setNextPredictor()
        }
    }

    fun setNextPredictor() {
        val nextPlayer = players.removeAt(0)
        players.add(nextPlayer)
    }

    fun resetPlayers() {
        players = listOf(Player.HUMAN, Player.AI).toMutableList()
    }
}