class Game(private val terminal: Terminal) {
    fun start() {
        terminal.printMessage("Welcome to the game!")
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
}