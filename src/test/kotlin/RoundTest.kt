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
        mockkObject(Player.HUMAN)
        mockkObject(Player.AI)
    }

    @Test
    internal fun `should run round in correct sequence`() {
        every { Terminal.getInput(any()) } returns "OO3"
        Round(Player.HUMAN, listOf(Player.AI,Player.HUMAN))

        verifyOrder {
            Player.AI.generateInput(Player.HUMAN)
            Player.HUMAN.generateInput(Player.HUMAN)
        }
    }

    @Nested
    inner class EvaluateWinnerTest {
        private val playerInput = mockkClass(PlayerInput::class)
        private val aiInput = mockkClass(AiInput::class)

        @BeforeEach
        internal fun setUp() {
            every { Player.AI.generateInput(any()) } returns aiInput
            every { Player.HUMAN.generateInput(any()) } returns playerInput
            every { playerInput.player } returns Player.HUMAN
            every { aiInput.player } returns Player.AI
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