import InputValidator.yesNo

class Game {
    var currentPredictor: Player = Player.HUMAN
    var winner: Player? = null
    private val players = listOf(Player.AI, Player.HUMAN).toMutableList()

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        while (true) {
            winner = null
            currentPredictor = Player.HUMAN
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
            winner = Round(currentPredictor).winner
            setNextPredictor()
        }
    }

    fun setNextPredictor() {
        val nextPlayer = players.removeAt(0)
        currentPredictor = nextPlayer
        players.add(nextPlayer)
    }
}