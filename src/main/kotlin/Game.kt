import InputValidator.yesNo

class Game {
    var currentPredictor: PLAYER = PLAYER.HUMAN
    var winner: PLAYER? = null

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        while (true) {
            winner = null
            currentPredictor = PLAYER.HUMAN
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
        currentPredictor = when (currentPredictor) {
            PLAYER.AI -> PLAYER.HUMAN
            PLAYER.HUMAN -> PLAYER.AI
        }
    }
}

enum class PLAYER {
    HUMAN,
    AI
}