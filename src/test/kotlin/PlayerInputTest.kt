import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import org.mockito.Mockito.*

internal class PlayerInputTest {
    private val testPlayer = PlayerInput("OO", false)

    @Test
    internal fun `should count number of open hands`() {
        assertEquals(2, testPlayer.countOpenHands("OO4"))
        assertEquals(1, testPlayer.countOpenHands("CO4"))
        assertEquals(1, testPlayer.countOpenHands("OC4"))
        assertEquals(0, testPlayer.countOpenHands("CC4"))
    }

    @Test
    internal fun `should get prediction`() {
        assertEquals(4, testPlayer.getPrediction("OO4"))
    }

    @Test
    internal fun `should accept valid input with prediction`() {
        assertDoesNotThrow { testPlayer.verifyInputWithPrediction("CC4") }
    }

    @Test
    internal fun `should reject invalid inputs with prediction`() {
        assertThrows<Exception> { testPlayer.verifyInputWithPrediction("KO1") }
        assertThrows<Exception> { testPlayer.verifyInputWithPrediction("OT1") }
        assertThrows<Exception> { testPlayer.verifyInputWithPrediction("OO5") }
    }

    @Test
    internal fun `should accept valid input without prediction`() {
        assertDoesNotThrow { testPlayer.verifyInputWithoutPrediction("CC") }
        assertDoesNotThrow { testPlayer.verifyInputWithoutPrediction("CO") }
        assertDoesNotThrow { testPlayer.verifyInputWithoutPrediction("OC") }
        assertDoesNotThrow { testPlayer.verifyInputWithoutPrediction("OO") }
    }

    @Test
    internal fun `should reject invalid inputs without prediction`() {
        assertThrows<Exception> { testPlayer.verifyInputWithoutPrediction("KO") }
        assertThrows<Exception> { testPlayer.verifyInputWithoutPrediction("OT") }
        assertThrows<Exception> { testPlayer.verifyInputWithoutPrediction("OO1") }
    }

    @Test
    internal fun `should set number of open hands`() {
        val playerInput = PlayerInput("CC", false)
        assertEquals(0, playerInput.numberOfOpenHands)
        assertEquals(0, playerInput.prediction)
    }

    @Test
    internal fun `should set number of open hands and prediction`() {
        val playerInput = PlayerInput("CC4", true)
        assertEquals(0, playerInput.numberOfOpenHands)
        assertEquals(4, playerInput.prediction)
    }

    @Test
    internal fun `should create player input`() {
        val playerInput = PlayerInput.createPlayerInput("CC1", true)
        assertEquals(0, playerInput.numberOfOpenHands)
        assertEquals(1, playerInput.prediction)
    }
}