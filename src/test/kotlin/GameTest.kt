import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction
import InputValidator.positiveInteger
import InputValidator.yesNo
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GameTest {
    private var testGame = spyk(Game(), recordPrivateCalls = true)

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(Round::class)
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
    internal fun `should switch between AI and HUMAN as predictor after each round`() {
        val newGame = Game()
        newGame.setNextPredictor()
        assertEquals(Player.AI, newGame.currentPredictor())
        newGame.setNextPredictor()
        assertEquals(Player.HUMAN, newGame.currentPredictor())
        newGame.setNextPredictor()
        assertEquals(Player.AI, newGame.currentPredictor())
        newGame.setNextPredictor()
        assertEquals(Player.HUMAN, newGame.currentPredictor())
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
            testGame.resetPlayers()
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

    @Test
    internal fun `should keep running round until winner is found`() {
        every { anyConstructed<Round>().winner } returnsMany listOf(null, null, Player.HUMAN)
        every { Terminal.getInput(::inputWithPrediction) } returns "OO1"
        every { Terminal.getInput(::inputWithoutPrediction) } returns "CC"
        testGame.start()
        verify(exactly = 3) { anyConstructed<Round>().winner }
    }

    @Test
    internal fun `should reset players`() {
        val game = Game()
        assertEquals(listOf(Player.HUMAN, Player.AI), game.predictorQueue)
        game.setNextPredictor()
        game.resetPlayers()
        assertEquals(listOf(Player.HUMAN, Player.AI), game.predictorQueue)
    }
}