import kotlin.random.Random

class AiInput {
    val prediction: Int? = null
    val input: String = generateInput()
    val numberOfOpenHands = Regex("O").findAll(input).count()
    private fun generateInput(): String {
        val firstHand = if (Random.nextBoolean()) "O" else "C"
        val secondHand = if (Random.nextBoolean()) "O" else "C"
        return firstHand + secondHand
    }
}