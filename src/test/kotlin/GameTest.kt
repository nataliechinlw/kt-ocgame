import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GameTest {
    private val playerInput = mockkClass(PlayerInput::class)
    private val aiInput = mockkClass(AiInput::class)
    private var testGame = spyk(Game())

    @BeforeEach
    internal fun setUp() {
        mockkObject(Terminal)
        mockkObject(PlayerInput.Companion)
        every { Terminal.printMessage(any()) } just runs
        every { Terminal.getInput() } returns "OO2"
    }

    @Test
    internal fun `should start with HUMAN player as predictor in a new Game`() {
        assertEquals(PLAYER.HUMAN, Game().currentPredictor)
    }

    @Test
    internal fun `should switch from HUMAN to AI as predictor after round`() {
        testGame.currentPredictor = PLAYER.HUMAN
        testGame.runRound()
        assertEquals(PLAYER.AI, testGame.currentPredictor)
    }

    @Test
    internal fun `should switch from AI to HUMAN as predictor after round`() {
        every { Terminal.getInput() } returns "OO"
        testGame.currentPredictor = PLAYER.AI
        testGame.runRound()
        assertEquals(PLAYER.HUMAN, testGame.currentPredictor)
    }

    @Test
    internal fun `should start game`() {
        testGame.start()
        verifyOrder {
            Terminal.printMessage("Welcome to the game!")
            testGame.runRound()
        }
    }

    @Test
    internal fun `should evaluate no winner`() {
        every { playerInput.numberOfOpenHands } returns 1
        every { playerInput.prediction } returns 1
        every { aiInput.numberOfOpenHands } returns 1
        testGame.evaluateWinner(playerInput, aiInput)
        verify { Terminal.printMessage("No winner") }
    }

    @Test
    internal fun `should evaluate winner`() {
        every { playerInput.numberOfOpenHands } returns 1
        every { playerInput.prediction } returns 2
        every { aiInput.numberOfOpenHands } returns 1
        testGame.evaluateWinner(playerInput, aiInput)
        verify { Terminal.printMessage("You WIN!!") }
    }

    @Test
    internal fun `should ask for input as predictor`() {
        every { Terminal.getInput() } returns "OO2"
        testGame.askForInput()
        verify {
            Terminal.printMessage("You are the predictor, what is your input?")
            Terminal.getInput()
            PlayerInput.Companion.createPlayerInput("OO2", true)
        }
    }

    @Test
    internal fun `should ask for input not as predictor`() {
        every { Terminal.getInput() } returns "OO"
        testGame.currentPredictor = PLAYER.AI
        testGame.askForInput()
        verify {
            Terminal.printMessage("AI is the predictor, what is your input?")
            Terminal.getInput()
            PlayerInput.Companion.createPlayerInput("OO", false)
        }
    }

    @Test
    internal fun `should generate AI input`() {
        testGame.generateAiInput()
        verify { Terminal.printMessage(any()) }
    }

    @Test
    internal fun `should run round in correct sequence`() {
        testGame.runRound()
        verifySequence {
            testGame.runRound()
            testGame.askForInput()
            testGame.generateAiInput()
            testGame.evaluateWinner(any(),any())
            testGame["setNextPredictor"]
        }
    }
}