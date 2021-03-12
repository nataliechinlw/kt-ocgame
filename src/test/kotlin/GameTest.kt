import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GameTest {
    private val playerInput = mockkClass(PlayerInput::class)
    private val aiInput = mockkClass(AiInput::class)
    private var testGame = spyk(Game())

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(PlayerInput::class)
        mockkObject(PlayerInput.Companion)
        mockkObject(AiInput.Companion)
        every { Terminal.printMessage(any()) } just runs
        every { Terminal.getInput() } returns "OO3"
        every { anyConstructed<PlayerInput>().numberOfOpenHands } returns 1
        every { anyConstructed<PlayerInput>().prediction } returns 1
    }

    @Test
    internal fun `should start with HUMAN player as predictor and no winner in a new Game`() {
        val newGame = Game()
        assertEquals(PLAYER.HUMAN, newGame.currentPredictor)
        assertNull(newGame.winner)
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
    internal fun `should welcome and run game`() {
        every { testGame["runSession"]() } returns Unit
        testGame.start()
        verifySequence {
            Terminal.printMessage("Welcome to the game!")
            testGame["runSession"]
        }
    }

    @Nested
    inner class EvaluateWinnerTest {
        @Test
        internal fun `should evaluate no winner with HUMAN predictor`() {
            testGame.currentPredictor = PLAYER.HUMAN
            every { playerInput.numberOfOpenHands } returns 1
            every { playerInput.prediction } returns 1
            every { aiInput.numberOfOpenHands } returns 1

            assertNull(testGame.evaluateWinner(playerInput, aiInput))
        }

        @Test
        internal fun `should evaluate winner with HUMAN predictor`() {
            testGame.currentPredictor = PLAYER.HUMAN
            every { playerInput.numberOfOpenHands } returns 1
            every { playerInput.prediction } returns 2
            every { aiInput.numberOfOpenHands } returns 1

            assertEquals(PLAYER.HUMAN, testGame.evaluateWinner(playerInput, aiInput))
        }

        @Test
        internal fun `should evaluate no winner with AI predictor`() {
            testGame.currentPredictor = PLAYER.AI
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 1

            assertNull(testGame.evaluateWinner(playerInput, aiInput))
        }

        @Test
        internal fun `should evaluate winner with AI predictor`() {
            testGame.currentPredictor = PLAYER.AI
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 2

            assertEquals(PLAYER.AI, testGame.evaluateWinner(playerInput, aiInput))
        }
    }

    @Test
    internal fun `should ask for input as predictor`() {
        every { Terminal.getInput() } returns "OO2"
        testGame.askForInput()
        verify {
            Terminal.printMessage("You are the predictor, what is your input?")
            Terminal.getInput()
            PlayerInput.Companion.create("OO2", true)
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
            PlayerInput.Companion.create("OO", false)
        }
    }

    @Test
    internal fun `should generate AI input not as predictor`() {
        testGame.currentPredictor = PLAYER.HUMAN
        testGame.generateAiInput()
        verify {
            AiInput.Companion.create(false)
            Terminal.printMessage(any())
        }
    }

    @Test
    internal fun `should generate AI input as predictor`() {
        testGame.currentPredictor = PLAYER.AI
        testGame.generateAiInput()
        verify {
            AiInput.Companion.create(true)
            Terminal.printMessage(any())
        }
    }

    @Test
    internal fun `should run round in correct sequence`() {
        testGame.runRound()
        verifySequence {
            testGame.runRound()
            testGame.askForInput()
            testGame.generateAiInput()
            testGame.evaluateWinner(any(), any())
            testGame.printWinner(any())
            testGame["setNextPredictor"]
        }
    }

    @Test
    internal fun `should keep running round until winner is found`() {
        every { testGame.evaluateWinner(any(), any()) } returnsMany listOf(null, null, PLAYER.HUMAN)
        every { Terminal.getInput() } returnsMany listOf("CC4", "CC", "OO3")
        testGame.start()
        verify(exactly = 3) { testGame.runRound() }
    }

    @Nested
    inner class PrintWinnerTest {
        @Test
        internal fun `should print no winner if null winner`() {
            testGame.printWinner(null)
            verify { Terminal.printMessage("No winner.") }
        }

        @Test
        internal fun `should print user winner if HUMAN winner`() {
            testGame.printWinner(PLAYER.HUMAN)
            verify { Terminal.printMessage("You WIN!!") }
        }

        @Test
        internal fun `should print AI winner if AI winner`() {
            testGame.printWinner(PLAYER.AI)
            verify { Terminal.printMessage("AI WINS!!") }
        }
    }
}