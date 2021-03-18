data class PlayerInput(override val input: String, val isPredictor: Boolean) : Input() {
    override val numberOfOpenHands = Regex("O").findAll(input).count()
    override val prediction = if (isPredictor) {
        input[2].toString().toInt()
    } else null

    companion object {
        fun create(input: String, isPredictor: Boolean) = PlayerInput(input, isPredictor)
    }
}