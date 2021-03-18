import InputValidator.inputWithPrediction
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class PlayerTest {
    @BeforeEach
    internal fun setUp() {
        unmockkAll()
    }

    @ParameterizedTest
    @CsvSource("HUMAN, You WIN!!", "AI, AI WINS!!")
    internal fun `should print correct winner message given winning Player`(playerEnum: String, winnerMessage: String) {
        assertEquals(winnerMessage, Player.valueOf(playerEnum).getWinnerMessage())
    }

    @ParameterizedTest
    @CsvSource("HUMAN; You are the predictor, what is your input?", "AI; AI is the predictor, what is your input?", delimiter = ';')
    internal fun `should print correct ask for input message given current predicting Player`(playerEnum: String, askForInputMessage: String) {
        assertEquals(askForInputMessage, Player.valueOf(playerEnum).getAskForInputMessage())
    }

    @Test
    internal fun `should generate HUMAN input`() {
        mockkObject(Terminal)
        every { Terminal.getInput(any()) } returns "OO2"
        spyk(Player.HUMAN, recordPrivateCalls = true)

        assertEquals(
            PlayerInput("OO2", true),
            Player.HUMAN.generateInput(Player.HUMAN)
        )
        verify {
            Terminal.printMessage(any())
            Terminal.getInput(::inputWithPrediction)
        }
    }

    @Test
    internal fun `should generate AI input`() {
        assertEquals(AiInput().javaClass, Player.AI.generateInput(Player.AI).javaClass)
        assertNotNull(Player.AI.generateInput(Player.AI).prediction)
        assertNull(Player.AI.generateInput(Player.HUMAN).prediction)
    }
}