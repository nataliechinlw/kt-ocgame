import io.mockk.unmockkAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class PlayerInputTest {
    private val testPlayer = PlayerInput("OO", false)

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
    }

    @Test
    internal fun `should count number of open hands`() {
        assertEquals(2, PlayerInput("OO4", true).numberOfOpenHands)
        assertEquals(1, PlayerInput("CO4", true).numberOfOpenHands)
        assertEquals(1, PlayerInput("OC4", true).numberOfOpenHands)
        assertEquals(0, PlayerInput("CC4", true).numberOfOpenHands)
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
        assertEquals(null, playerInput.prediction)
    }

    @Test
    internal fun `should set number of open hands and prediction`() {
        val playerInput = PlayerInput("CC4", true)
        assertEquals(0, playerInput.numberOfOpenHands)
        assertEquals(4, playerInput.prediction)
    }

    @Test
    internal fun `should create player input`() {
        val playerInput = PlayerInput.create("CC1", true)
        assertEquals(0, playerInput.numberOfOpenHands)
        assertEquals(1, playerInput.prediction)
    }
}