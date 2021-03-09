import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

internal class AiInputTest {
    @RepeatedTest(10)
    internal fun `should generate number of open hands`() {
        val testInput = AiInput()
        val inputPattern = """[OC][OC]""".toRegex()

        assertTrue(inputPattern.matches(testInput.generateInput()))
    }
}