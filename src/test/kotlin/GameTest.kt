import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction
import InputValidator.positiveInteger
import InputValidator.yesNo
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GameTest {
    private var testGame = spyk(Game(), recordPrivateCalls = true)

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(Session::class)
        every { Terminal.printMessage(any()) } just runs
        every { Terminal.getInput(::positiveInteger) } returns "1"
        every { Terminal.getInput(::yesNo) } returns "N"
    }

    @Test
    internal fun `should start with HUMAN player as predictor and no winner in a new Game`() {
        val newGame = Game()
        assertEquals(Player.HUMAN, newGame.currentPredictor())
        assertNull(newGame.winner)
    }

    @Test
    internal fun `should welcome and run game`() {
        every { testGame["runSession"]() } returns Unit
        every { Terminal.getInput() } returns "N"
        testGame.start()
        verifySequence {
            testGame.start()
            Terminal.printMessage("Welcome to the game!")
            Terminal.printMessage("What is your target score?")
            Terminal.getInput(::positiveInteger)
            testGame["runSession"]()
            Terminal.printMessage("Do you want to play again?")
            Terminal.getInput(any())
            Terminal.printMessage("Ok, bye!")
        }
    }

    @Test
    internal fun `should run new session if player wants to replay`() {
        every { testGame["runSession"]() } returns Unit
        every { Terminal.getInput(::yesNo) } returnsMany listOf("Y", "Y", "N")
        testGame.start()
        verify(exactly = 3) { testGame["runSession"]() }

    }

    @ParameterizedTest
    @ValueSource(ints = [2, 5, 10])
    internal fun `should run session twice`(targetScore: Int) {
        every { Terminal.getInput(::positiveInteger) } returns targetScore.toString()
        every { testGame["runSession"]() } answers {
            testGame.winner = Player.HUMAN
            Unit
        }

        testGame.start()
        verify(exactly = targetScore) {
            testGame["runSession"]()
        }
    }
}