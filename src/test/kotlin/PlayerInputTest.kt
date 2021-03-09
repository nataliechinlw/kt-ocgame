import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals

internal class PlayerInputTest {
    val testPlayer = PlayerInput()

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
}