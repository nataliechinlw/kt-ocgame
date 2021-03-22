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
        every { Terminal.getInput(::inputWithPrediction) } returns "OO1"
    }

    @Test
    internal fun `should keep running round until winner is found`() {
        every { anyConstructed<Round>().winner } returnsMany listOf(null, null, Player.HUMAN)
        every { Terminal.getInput(::inputWithoutPrediction) } returns "CC"

        Session()
        verify(exactly = 3) { anyConstructed<Round>().winner }
    }

    @Test
    internal fun `should switch between AI and HUMAN as predictor after each round`() {
        every { anyConstructed<Round>().winner } returns Player.HUMAN
        val session = Session()
        assertEquals(Player.AI, session.currentPredictor())
        session.setNextPredictor()
        assertEquals(Player.HUMAN, session.currentPredictor())
        session.setNextPredictor()
        assertEquals(Player.AI, session.currentPredictor())
        session.setNextPredictor()
        assertEquals(Player.HUMAN, session.currentPredictor())
    }
}