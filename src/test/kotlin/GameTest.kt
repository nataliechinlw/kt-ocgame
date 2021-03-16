import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GameTest {
    private var testGame = spyk(Game(), recordPrivateCalls = true)

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(Round::class)
        every { Terminal.printMessage(any()) } just runs
        every { Terminal.getInput(any()) } returns "N"
    }

    @Test
    internal fun `should start with HUMAN player as predictor and no winner in a new Game`() {
        val newGame = Game()
        assertEquals(Player.HUMAN, newGame.currentPredictor)
        assertNull(newGame.winner)
    }

    @Test
    internal fun `should switch from HUMAN to AI as predictor after round`() {
        testGame.currentPredictor = Player.HUMAN
        testGame.setNextPredictor()
        assertEquals(Player.AI, testGame.currentPredictor)
    }

    @Test
    internal fun `should switch from AI to HUMAN as predictor after round`() {
        testGame.currentPredictor = Player.AI
        testGame.setNextPredictor()
        assertEquals(Player.HUMAN, testGame.currentPredictor)
    }

    @Test
    internal fun `should welcome and run game`() {
        every { testGame["runSession"]() } returns Unit
        every { Terminal.getInput() } returns "N"
        testGame.start()
        verifySequence {
            Terminal.printMessage("Welcome to the game!")
            testGame["runSession"]
            Terminal.printMessage("Do you want to play again?")
            Terminal.getInput(any())
            Terminal.printMessage("Ok, bye!")
        }
    }

    @Test
    internal fun `should run new session if player wants to replay`() {
        every { testGame["runSession"]() } returns Unit
        every { Terminal.getInput(any()) } returnsMany listOf("Y", "Y", "N")
        testGame.start()
        verify(exactly = 3) { testGame["runSession"]() }

    }

    @Test
    internal fun `should keep running round until winner is found`() {
        every { anyConstructed<Round>().winner } returnsMany listOf(null, null, Player.HUMAN)
        every { Terminal.getInput(any()) } returnsMany listOf("OO2", "CC", "OO1", "N")
        testGame.start()
        verify(exactly = 3) { anyConstructed<Round>().winner }
    }
}