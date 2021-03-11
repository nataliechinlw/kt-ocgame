class Game {
    var currentPredictor: PLAYER = PLAYER.HUMAN

    fun start() {
        Terminal.printMessage("Welcome to the game!")
        runRound()
    }

    fun evaluateWinner(playerInput: PlayerInput, aiInput: AiInput) {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = playerInput.prediction
        if (totalNumberOfOpenHands != prediction)
            Terminal.printMessage("No winner")
        else
            Terminal.printMessage("You WIN!!")
    }

    fun askForInput(): PlayerInput {
        val message = when (currentPredictor) {
            PLAYER.HUMAN -> "You are the predictor, what is your input?"
            PLAYER.AI -> "AI is the predictor, what is your input?"
        }
        Terminal.printMessage(message)
        return PlayerInput.createPlayerInput(Terminal.getInput(), currentPredictor == PLAYER.HUMAN)
    }

    fun generateAiInput(): AiInput {
        val aiInput = AiInput()
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