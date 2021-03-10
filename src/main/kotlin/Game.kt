class Game(private val terminal: Terminal) {
    var currentPredictor: PLAYER = PLAYER.HUMAN

    fun start() {
        terminal.printMessage("Welcome to the game!")
        runRound()
    }

    fun evaluateWinner(playerInput: PlayerInput, aiInput: AiInput) {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = playerInput.prediction
        if (totalNumberOfOpenHands != prediction)
            terminal.printMessage("No winner")
        else
            terminal.printMessage("You WIN!!")
    }

    fun askForInput(): PlayerInput {
        terminal.printMessage("You are the predictor, what is your input?")
        return PlayerInput.createPlayerInput(terminal.getInput(), true)
    }

    fun generateAiInput(): AiInput {
        val aiInput = AiInput()
        terminal.printMessage("AI: ${aiInput.input}")
        return aiInput
    }

    fun runRound() {
        val playerInput = askForInput()
        val aiInput = generateAiInput()
        evaluateWinner(playerInput, aiInput)
    }
}

enum class PLAYER {
    HUMAN,
    AI
}