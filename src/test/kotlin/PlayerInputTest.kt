import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class PlayerInputTest {
    private val testPlayer = PlayerInput()

    @Test
    internal fun `should count number of open hands`() {
        assertEquals(2, testPlayer.countHands("OO4"))
        assertEquals(1, testPlayer.countHands("CO4"))
        assertEquals(1, testPlayer.countHands("OC4"))
        assertEquals(0, testPlayer.countHands("CC4"))
    }

    @Test
    internal fun `should accept valid input`() {
        assertDoesNotThrow { testPlayer.verifyInput("CC4") }
    }

    @Test
    internal fun `should reject invalid inputs`() {
        assertThrows<Exception> { testPlayer.verifyInput("KO1") }
        assertThrows<Exception> { testPlayer.verifyInput("OT1") }
        assertThrows<Exception> { testPlayer.verifyInput("OO5") }
    }

    @Test
    internal fun `should get prediction`() {
        assertEquals(4, testPlayer.getPrediction("OO4"))
    }
}