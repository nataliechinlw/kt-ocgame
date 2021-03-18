import io.mockk.*
import org.junit.jupiter.api.BeforeEach
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
        mockkObject(Player.HUMAN)
    }

    @Test
    internal fun `should run round in correct sequence`() {
        every { Terminal.getInput(any()) } returns "OO3"
        Round(Player.HUMAN)

        verify {
            Player.HUMAN.generateInput(Player.HUMAN)
            AiInput.Companion.create(false)
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
            every { AiInput.Companion.create(any()) } returns aiInput
            every { Player.HUMAN.generateInput(Player.HUMAN) } returns playerInput
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

            assertEquals(Player.HUMAN, Round(Player.HUMAN).winner)
        }

        @Test
        internal fun `should evaluate no winner with AI predictor`() {
            every { Terminal.getInput(any()) } returns "OC1"
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 1

            assertNull(Round(Player.AI).winner)
        }

        @Test
        internal fun `should evaluate winner with AI predictor`() {
            every { Terminal.getInput(any()) } returns "OC1"
            every { playerInput.numberOfOpenHands } returns 1
            every { aiInput.numberOfOpenHands } returns 1
            every { aiInput.prediction } returns 2

            assertEquals(Player.AI, Round(Player.AI).winner)
        }
    }
}