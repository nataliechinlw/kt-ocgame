import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SessionTest {
    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(Round::class)
    }

    @Test
    internal fun `should keep running round until winner is found`() {
        every { anyConstructed<Round>().winner } returnsMany listOf(null, null, Player.HUMAN)
        every { Terminal.getInput(::inputWithPrediction) } returns "OO1"
        every { Terminal.getInput(::inputWithoutPrediction) } returns "CC"

        Session()
        verify(exactly = 3) { anyConstructed<Round>().winner }
    }

    @Test
    internal fun `should start with HUMAN as predictor`() {
        every { anyConstructed<Round>().winner } returns Player.HUMAN
        every { Terminal.getInput(::inputWithPrediction) } returns "OO1"

        assertEquals(Player.HUMAN, Session().currentPredictor())
    }
}