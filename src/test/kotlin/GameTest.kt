import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class GameTest {
    private val playerInput = mock(PlayerInput::class.java)
    private val aiInput = mock(AiInput::class.java)
    private val terminal = mock(Terminal::class.java)

    private var testGame = Game(terminal)

    @Test
    internal fun `should print welcome message`() {
        testGame.start()
        verify(terminal).printMessage("Welcome to the game!")
    }

    @Test
    internal fun `should evaluate no winner`() {
        `when`(playerInput.numberOfOpenHands).thenReturn(1)
        `when`(playerInput.prediction).thenReturn(1)
        `when`(aiInput.numberOfOpenHands).thenReturn(1)
        testGame.evaluateWinner(playerInput, aiInput)
        verify(terminal).printMessage("No winner")
    }

    @Test
    internal fun `should evaluate winner`() {
        `when`(playerInput.numberOfOpenHands).thenReturn(1)
        `when`(playerInput.prediction).thenReturn(2)
        `when`(aiInput.numberOfOpenHands).thenReturn(1)
        testGame.evaluateWinner(playerInput, aiInput)
        verify(terminal).printMessage("You WIN!!")
    }

    @Test
    internal fun `should ask for input`() {
        `when`(playerInput.numberOfOpenHands).thenReturn(1)
        `when`(terminal.getInput()).thenReturn("OO2")
        testGame.askForInput()
        verify(terminal).printMessage("You are the predictor, what is your input?")
        verify(terminal).getInput()
    }
}