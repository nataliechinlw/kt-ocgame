import InputValidator.positiveInteger
import InputValidator.yesNo

class Game {
    private val players = listOf(Player.HUMAN, Player.AI)
    var winners = emptyList<Player>().toMutableList()
    var predictorQueue = players.toMutableList()
    fun currentPredictor() = predictorQueue.elementAt(0)

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        Terminal.printMessage("What is your target score?")
        val targetScore = Terminal.getInput(::positiveInteger).toInt()
        while (true) {
            winners = emptyList<Player>().toMutableList()
            while (winners.count { it == Player.HUMAN } < targetScore) {
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
        val session = Session()
        session.run()
        val winner = session.winner
        winner?.let { winners.add(it) }
        println(winners)
    }
}