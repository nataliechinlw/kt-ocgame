import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameTest {
    private val playerInput = mockkClass(PlayerInput::class)
    private val aiInput = mockkClass(AiInput::class)
    private val terminal = mockkClass(Terminal::class)

    private var testGame = Game(terminal)

    @BeforeEach
    internal fun setUp() {
        every { terminal.printMessage(any()) } just runs
    }

    @Test
    internal fun `should print welcome message`() {
        testGame.start()
        verify { terminal.printMessage("Welcome to the game!") }
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
        every { terminal.getInput() } returns "OO2"
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
}