class Game {
    var currentPredictor: PLAYER = PLAYER.HUMAN

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        runRound()
    }

    fun evaluateWinner(playerInput: PlayerInput, aiInput: AiInput) {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = when (currentPredictor) {
            PLAYER.HUMAN -> playerInput.prediction
            PLAYER.AI -> aiInput.prediction
        }
        if (totalNumberOfOpenHands != prediction)
            Terminal.printMessage("No winner")
        else
            Terminal.printMessage(
                when (currentPredictor) {
                    PLAYER.HUMAN -> "You WIN!!"
                    PLAYER.AI -> "AI WINS!!"
                }
            )
    }

    fun askForInput(): PlayerInput {
        val message = when (currentPredictor) {
            PLAYER.HUMAN -> "You are the predictor, what is your input?"
            PLAYER.AI -> "AI is the predictor, what is your input?"
        }
        Terminal.printMessage(message)
        return PlayerInput.create(Terminal.getInput(), currentPredictor == PLAYER.HUMAN)
    }

    fun generateAiInput(): AiInput {
        val aiInput = AiInput.create(currentPredictor == PLAYER.AI)
        Terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    fun runRound() {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        evaluateWinner(playerInput, aiInput)
        setNextPredictor()
    }

    private fun setNextPredictor() {
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