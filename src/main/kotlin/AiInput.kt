import kotlin.random.Random

class AiInput(isPredictor: Boolean = false) : Input() {
    override val player = Player.AI
    override val prediction: Int? = if (isPredictor) Random.nextInt(0, 5) else null
    override val input: String = generateInput(isPredictor)
    override val numberOfOpenHands = Regex("O").findAll(input).count()

    private fun generateInput(isPredictor: Boolean): String {
        val firstHand = if (Random.nextBoolean()) "O" else "C"
        val secondHand = if (Random.nextBoolean()) "O" else "C"
        val handConfig = firstHand + secondHand
        return if (isPredictor) handConfig + prediction else handConfig
    }

    companion object {
        fun create(isPredictor: Boolean) = AiInput(isPredictor)
    }
}