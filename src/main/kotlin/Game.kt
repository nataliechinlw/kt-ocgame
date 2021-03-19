import InputValidator.positiveInteger
import InputValidator.yesNo

class Game {
    private val players = listOf(Player.HUMAN, Player.AI)
    var winner: Player? = null
    var predictorQueue = players.toMutableList()
    fun currentPredictor() = predictorQueue.elementAt(0)

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        Terminal.printMessage("What is your target score?")
        val targetScore = Terminal.getInput(::positiveInteger).toInt()
        while (true) {
            for (i in 0 until targetScore) {
                runSession()
            }
            Terminal.printMessage("Do you want to play again?")
            val replayInput = Terminal.getInput(::yesNo)
            if (replayInput == "N")
                break
        }
        Terminal.printMessage("Ok, bye!")
    }

    private fun runSession() {
        winner = null
        resetPlayers()
        while (winner == null) {
            winner = Round(currentPredictor()).winner
            setNextPredictor()
        }
    }

    fun setNextPredictor() {
        val nextPlayer = predictorQueue.removeAt(0)
        predictorQueue.add(nextPlayer)
    }

    fun resetPlayers() {
        predictorQueue = players.toMutableList()
    }
}