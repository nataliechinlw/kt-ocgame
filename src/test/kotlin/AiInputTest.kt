import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

internal class AiInputTest {
    @RepeatedTest(10)
    internal fun `should generate number of open hands`() {
        val testInput = AiInput()
        val inputPattern = """[OC][OC]""".toRegex()

        assertTrue(inputPattern.matches(testInput.input))
        assertTrue(testInput.numberOfOpenHands < 3)
    }

    @RepeatedTest(10)
    internal fun `should generate number of open hands with prediction if predictor`() {
        val testInput = AiInput(true)
        val inputPattern = """[OC][OC][0-4]""".toRegex()

        assertTrue(inputPattern.matches(testInput.input))
        assertTrue(testInput.numberOfOpenHands < 3)
    }

    @Test
    internal fun `should not generate prediction if not predictor`() {
        val testInput = AiInput()

        assertNull(testInput.prediction)
    }

    @RepeatedTest(10)
    internal fun `should generate prediction if predictor`() {
        val testInput = AiInput(true)

        assertNotNull(testInput.prediction)
        assertTrue(testInput.prediction!! >= 0)
        assertTrue(testInput.prediction!! >= 0)
        assertTrue(testInput.prediction!! <= 4)
    }
}