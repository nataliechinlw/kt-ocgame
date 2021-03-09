import kotlin.random.Random

class AiInput {
    val input: String = generateInput()
    fun generateInput(): String {
        val firstHand = if (Random.nextBoolean()) "O" else "C"
        val secondHand = if (Random.nextBoolean()) "O" else "C"
        return firstHand + secondHand
    }
}