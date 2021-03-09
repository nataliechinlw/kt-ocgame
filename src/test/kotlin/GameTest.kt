import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.PrintStream
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

internal class GameTest {
    private val testGame = Game()
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    private val playerInput = mock(PlayerInput::class.java)
    private val aiInput = mock(AiInput::class.java)

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    internal fun `should print welcome message`() {
        testGame.start()
        assertEquals("Welcome to the game!", outputStreamCaptor.toString().trim())
    }

    @Test
    internal fun `should evaluate no winner`() {
        `when`(playerInput.numberOfOpenHands).thenReturn(1)
        `when`(playerInput.prediction).thenReturn(1)
        `when`(aiInput.numberOfOpenHands).thenReturn(1)
        testGame.evaluateWinner(playerInput, aiInput)
        assertEquals("No winner", outputStreamCaptor.toString().trim())
    }
}