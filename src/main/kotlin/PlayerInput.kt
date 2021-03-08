class PlayerInput() {
    fun countHands(input: String): Int {
        val numberInFirstHand = if (input[0] == 'O') 1 else 0
        val numberInSecondHand = if (input[1] == 'O') 1 else 0
        return numberInFirstHand + numberInSecondHand
    }
}