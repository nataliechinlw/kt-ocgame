import kotlin.random.Random

class AiInput(isPredictor: Boolean = false) {
    val input: String
    val numberOfOpenHands: Int
    val prediction: Int? = if (isPredictor) Random.nextInt(0, 5) else null
    init {
        val handConfig = generateInput()
        numberOfOpenHands = Regex("O").findAll(handConfig).count()
        input = if (isPredictor) handConfig + prediction else handConfig
    }
    private fun generateInput(): String {
        val firstHand = if (Random.nextBoolean()) "O" else "C"
        val secondHand = if (Random.nextBoolean()) "O" else "C"
        return firstHand + secondHand
    }

    companion object {
        fun create(isPredictor: Boolean) = AiInput(isPredictor)
    }
}