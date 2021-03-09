class Game {
    fun start() {
        println("Welcome to the game!")
    }

    fun evaluateWinner(playerInput: PlayerInput, aiInput: AiInput) {
        val totalNumberOfOpenHands = playerInput.numberOfOpenHands + aiInput.numberOfOpenHands
        val prediction = playerInput.prediction
        if (totalNumberOfOpenHands != prediction)
            println("No winner")
    }
}