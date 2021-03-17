import InputValidator.yesNo

class Game {
    lateinit var currentPredictor: Player
    var winner: Player? = null
    private val players = listOf(Player.HUMAN, Player.AI).toMutableList()

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        while (true) {
            winner = null
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
            setNextPredictor()
            winner = Round(currentPredictor).winner
        }
    }

    fun setNextPredictor() {
        val nextPlayer = players.removeAt(0)
        currentPredictor = nextPlayer
        players.add(nextPlayer)
    }
}