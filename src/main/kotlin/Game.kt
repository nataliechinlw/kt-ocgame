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
        while (winner == null)
            runRound()
    }

    fun evaluateWinner(playerInput: Input, aiInput: Input): PLAYER? {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = when (currentPredictor) {
            PLAYER.HUMAN -> playerInput.prediction
            PLAYER.AI -> aiInput.prediction
        }
        return if (totalNumberOfOpenHands != prediction) null else currentPredictor
    }

    fun askForInput(): PlayerInput {
        val message = when (currentPredictor) {
            PLAYER.HUMAN -> "You are the predictor, what is your input?"
            PLAYER.AI -> "AI is the predictor, what is your input?"
        }
        Terminal.printMessage(message)
        val inputValidator = if (currentPredictor == PLAYER.HUMAN) ::inputWithPrediction else :: inputWithoutPrediction
        return PlayerInput.create(Terminal.getInput(inputValidator), currentPredictor == PLAYER.HUMAN)
    }

    fun generateAiInput(): AiInput {
        val aiInput = AiInput.create(currentPredictor == PLAYER.AI)
        Terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    fun runRound() {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        winner = evaluateWinner(playerInput, aiInput)
        printWinner(winner)
        setNextPredictor()
    }

    fun printWinner(winner: PLAYER?) = Terminal.printMessage(
        when (winner) {
            PLAYER.HUMAN -> "You WIN!!"
            PLAYER.AI -> "AI WINS!!"
            null -> "No winner."
        }
    )

    private fun setNextPredictor() {
        currentPredictor = when (currentPredictor) {
            PLAYER.AI -> PLAYER.HUMAN
            PLAYER.HUMAN -> PLAYER.AI
        }
    }

    companion object InputValidator {
        fun yesNo(input: String) = "input should be either Y or N".takeIf { !Regex("[NY]").matches(input) }
        fun inputWithPrediction(input: String) =
            "correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand, followed by the prediction (0-4)".takeIf {
                !Regex("[OC][OC][0-4]").matches(input)
            }

        fun inputWithoutPrediction(input: String) =
            "correct input should be of the form CC, where the first two letters indicate [O]pen or [C]losed state for each hand".takeIf {
                !Regex("[OC][OC]").matches(input)
            }
    }
}

enum class PLAYER {
    HUMAN,
    AI
}