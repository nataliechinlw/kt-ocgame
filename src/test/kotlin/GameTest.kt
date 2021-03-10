import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GameTest {
    private val playerInput = mockkClass(PlayerInput::class)
    private val aiInput = mockkClass(AiInput::class)
    private val terminal = mockkClass(Terminal::class)

    private var testGame = spyk(Game(terminal))

    @BeforeEach
    internal fun setUp() {
        every { terminal.printMessage(any()) } just runs
        every { terminal.getInput() } returns "OO2"
    }

    @Test
    internal fun `should start with HUMAN player as predictor`() {
        assertEquals(PLAYER.HUMAN, testGame.currentPredictor)
    }

    @Test
    internal fun `should start game`() {
        testGame.start()
        verifyOrder {
            terminal.printMessage("Welcome to the game!")
            testGame.runRound()
        }
    }

    @Test
    internal fun `should evaluate no winner`() {
        every { playerInput.numberOfOpenHands } returns 1
        every { playerInput.prediction } returns 1
        every { aiInput.numberOfOpenHands } returns 1
        testGame.evaluateWinner(playerInput, aiInput)
        verify { terminal.printMessage("No winner") }
    }

    @Test
    internal fun `should evaluate winner`() {
        every { playerInput.numberOfOpenHands } returns 1
        every { playerInput.prediction } returns 2
        every { aiInput.numberOfOpenHands } returns 1
        testGame.evaluateWinner(playerInput, aiInput)
        verify { terminal.printMessage("You WIN!!") }
    }

    @Test
    internal fun `should ask for input`() {
        every { playerInput.numberOfOpenHands } returns 1
        testGame.askForInput()
        verify {
            terminal.printMessage("You are the predictor, what is your input?")
            terminal.getInput()
        }
    }

    @Test
    internal fun `should generate AI input`() {
        testGame.generateAiInput()
        verify { terminal.printMessage(any()) }
    }

    @Test
    internal fun `should run round in correct sequence`() {
        testGame.runRound()
        verifySequence {
            testGame.runRound()
            testGame.askForInput()
            testGame.generateAiInput()
            testGame.evaluateWinner(any(),any())
        }
    }
}