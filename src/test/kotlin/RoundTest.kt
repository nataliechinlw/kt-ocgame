import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class RoundTest {
    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkObject(PlayerInput.Companion)
        mockkObject(AiInput.Companion)
    }

    @Disabled
    @Test
    internal fun `should run round in correct sequence`() {
        every { Terminal.getInput(any()) } returns "OO3"
        val round = spyk(Round(Player.HUMAN))
        verify {
            round["askForInput"]()
            round["generateAiInput"]()
            round.evaluateWinner(any(), any())
            round.printWinner()
        }
    }

    @Test
    internal fun `should ask for input as predictor`() {
        every { Terminal.getInput(any()) } returns "OO2"
        spyk(Round(Player.HUMAN))
        verify {
            Terminal.printMessage("You are the predictor, what is your input?")
            Terminal.getInput(any())
            PlayerInput.Companion.create("OO2", true)
        }
    }

    @Test
    internal fun `should ask for input not as predictor`() {
        every { Terminal.getInput(any()) } returns "OO"
        spyk(Round(Player.AI))
        verify {
            Terminal.printMessage("AI is the predictor, what is your input?")
            Terminal.getInput(any())
            PlayerInput.Companion.create("OO", false)
        }
    }

    @Test
    internal fun `should generate AI input not as predictor`() {
        every { Terminal.getInput(any()) } returns "OO2"
        spyk(Round(Player.HUMAN))
        verify {
            AiInput.Companion.create(false)
            Terminal.printMessage(any())
        }
    }

    @Test
    internal fun `should generate AI input as predictor`() {
        every { Terminal.getInput(any()) } returns "OO"
        spyk(Round(Player.AI))
        verify {
            AiInput.Companion.create(true)
            Terminal.printMessage(any())
        }
    }

    @Nested
    inner class EvaluateWinnerTest {
        private val playerInput = mockkClass(PlayerInput::class)
        private val aiInput = mockkClass(AiInput::class)

        @BeforeEach
        internal fun setUp() {
            every { aiInput.input } returns "Mock AI Input"
            every { PlayerInput.Companion.create(any(), any()) } returns playerInput
            every { AiInput.Companion.create(any()) } returns aiInput
        }

        @Test
        internal fun `should evaluate no winner with HUMAN predictor`() {
            every { Terminal.getInput(any()) } returns "OC1"
            every { playerInput.numberOfOpenHands } returns 1
            every { playerInput.prediction } returns 1
            every { aiInput.numberOfOpenHands } returns 1

            assertNull(Round(Player.HUMAN).winner)
        }

        @Test
        internal fun `should evaluate winner with HUMAN predictor`() {
            every { Terminal.getInput(any()) } returns "OC2"
            every { playerInput.numberOfOpenHands } returns 1
            every { playerInput.prediction } returns 2
            every { aiInput.numberOfOpenHands } returns 1

            assertEquals(Player.HUMAN, Round(Player.HUMAN).evaluateWinner(playerInput, aiInput))
        }

        @Test
        internal fun `should evaluate no winner with AI predictor`() {
            every { Terminal.getInput(any()) } returns "OC1"
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 1

            assertNull(Round(Player.AI).evaluateWinner(playerInput, aiInput))
        }

        @Test
        internal fun `should evaluate winner with AI predictor`() {
            every { Terminal.getInput(any()) } returns "OC1"
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 2

            assertEquals(Player.AI, Round(Player.AI).evaluateWinner(playerInput, aiInput))
        }
    }
}